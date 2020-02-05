package com.bizwink.cms.security;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

public class GroupPeer implements IGroupManager {
    PoolServer cpool;

    public GroupPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IGroupManager getInstance() {
        return CmsServer.getInstance().getFactory().getGroupManager();
    }

    private static final String SQL_GETGROUP =
            "SELECT groupid,groupname,groupdesc FROM tbl_group WHERE groupID=? and siteid=?";

    public Group getGroup(int groupid, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Group group = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUP);
            pstmt.setInt(1, groupid);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                group = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return group;
    }

    private static final String SQL_QUERYGROUPRIGHTS =
            "SELECT * FROM TBL_Group WHERE GroupID = ? AND ColumnID = ? AND RightID = ?";

    public boolean QueryGroupRights(String groupID, int columnID, String rightID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean exit = true;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_QUERYGROUPRIGHTS);
                pstmt.setString(1, groupID);
                pstmt.setInt(2, columnID);
                pstmt.setString(3, rightID);
                rs = pstmt.executeQuery();
                if (rs.next()) exit = true;
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in rolling back the pooled connection " + e.toString());
        }
        return exit;
    }

    private static final String SQL_CREATEGROUP_FOR_ORACLE =
            "INSERT INTO tbl_group (siteid, groupname, groupdesc, groupid) VALUES (?, ?, ?, ?)";

    private static final String SQL_CREATEGROUP_FOR_MSSQL =
            "INSERT INTO tbl_group (siteid,groupname, groupdesc) VALUES (?, ?, ?);select SCOPE_IDENTITY()";

    private static final String SQL_CREATEGROUP_FOR_MYSQL =
            "INSERT INTO tbl_group (siteid,groupname, groupdesc) VALUES (?, ?, ?)";

    private static final String SQL_GroupCols =
            "INSERT INTO tbl_group_rights (groupid, columnid, rightid) VALUES (?, ?, ?)";

    public void create(Group group) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int groupID = 0;

        try {
            try {
                String groupname = group.getGroupName();
                if (groupname != null) groupname = StringUtil.gb2isoindb(groupname);
                String groupdesc = group.getGroupDesc();
                if (groupdesc != null) groupdesc = StringUtil.gb2isoindb(groupdesc);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATEGROUP_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATEGROUP_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATEGROUP_FOR_MYSQL);

                pstmt.setInt(1, group.getSiteID());
                pstmt.setString(2, groupname);
                pstmt.setString(3, groupdesc);
                if (cpool.getType().equals("oracle")) {
                    groupID = sequnceMgr.getSequenceNum("Group");
                    pstmt.setInt(4, groupID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    ResultSet rs = pstmt.executeQuery();
                    if(rs.next()){
                        groupID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取Mysql自增列的值groupid
                    pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) groupID=rs.getInt(1);
                    rs.close();
                    pstmt.close();
                }

                pstmt = conn.prepareStatement(SQL_GroupCols);
                for (int i = 0; i < group.getRightList().size(); i++) {
                    pstmt.setInt(1, groupID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, Integer.parseInt((String) group.getRightList().get(i)));
                    pstmt.executeUpdate();
                }
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create Group failed.");
            }
            finally {
                //cpool.freeConnection(conn);
                try {
                    if (conn != null)

                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATEGROUP = "UPDATE tbl_group set groupdesc = ? WHERE groupID = ?";

    private static final String SQL_GroupAddRights = "INSERT INTO tbl_group_rights (groupid, columnid, rightid) VALUES (?, ?, ?)";

    public void update(Group group) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        int groupID = group.getGroupID();
        IRightsManager rightMgr = RightsPeer.getInstance();

        try {
            try {
                //计算需要删除的权限
                String rightIDs = "";
                List rightList = group.getRightList();
                for (int i = 0; i < rightList.size(); i++)
                    rightIDs = rightIDs + rightList.get(i) + ",";

                String SQL_DELETE_RIGHTS = "delete from tbl_group_rights where groupid = ?";
                if (rightIDs.length() > 0) {
                    rightIDs = rightIDs.substring(0, rightIDs.length() - 1);
                    SQL_DELETE_RIGHTS += " and RightID NOT IN (" + rightIDs + ")";
                }

                //计算需要增加的权限
                List insertList = new ArrayList();
                if (rightList.size() > 0) {
                    rightIDs = ",";
                    List oldRightList = rightMgr.getGrantedGroupRights(groupID, group.getSiteID());
                    for (int i = 0; i < oldRightList.size(); i++)
                        rightIDs = rightIDs + ((Rights) (oldRightList.get(i))).getRightID() + ",";
                    for (int i = 0; i < rightList.size(); i++) {
                        String rightID = (String) rightList.get(i);
                        if (rightIDs.indexOf("," + rightID + ",") == -1) insertList.add(rightID);
                    }
                }

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_DELETE_RIGHTS);
                pstmt.setInt(1, groupID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_GroupAddRights);
                for (int i = 0; i < insertList.size(); i++) {
                    pstmt.setInt(1, groupID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, Integer.parseInt((String) insertList.get(i)));
                    pstmt.executeUpdate();
                }
                pstmt.close();

                String groupdesc = group.getGroupDesc();
                if (groupdesc != null) groupdesc = StringUtil.gb2isoindb(groupdesc);

                pstmt = conn.prepareStatement(SQL_UPDATEGROUP);
                pstmt.setString(1, groupdesc);
                pstmt.setInt(2, groupID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update Group failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETE_GROUP =
            "DELETE FROM TBL_Group WHERE GroupID = ?";

    private static final String SQL_DELETE_GROUP_MEMBERS =
            "DELETE FROM TBL_Group_Members WHERE GroupID = ?";

    private static final String SQL_DELETE_GROUP_RIGHTS =
            "DELETE FROM TBL_Group_Rights WHERE GroupID = ?";

    public void remove(Group group) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_DELETE_GROUP);
                pstmt.setInt(1, group.getGroupID());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_DELETE_GROUP_MEMBERS);
                pstmt.setInt(1, group.getGroupID());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_DELETE_GROUP_RIGHTS);
                pstmt.setInt(1, group.getGroupID());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete Group failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELGROUP =
            "DELETE from TBL_GROUP WHERE GroupID = ?";

    public void DelGroup(Group group) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELGROUP);
                pstmt.setInt(1, group.getGroupID());
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete Group failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GETGROUPCOUNT =
            "SELECT count(groupid) FROM tbl_group ";

    public int getGroupCount() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPCOUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get group count failed.");
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETGROUPS = "SELECT DISTINCT GroupID, GroupName, GroupDesc FROM tbl_group WHERE SiteID = ?";

    public List getGroups(int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Group group;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPS);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                group = load(rs);
                list.add(group);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static String SQL_GET_GROUPS_LOG_INFO = "select sum(createarticles) as createarticles,sum(editarticles) as " +
            "editarticles,sum(deletearticles) as deletearticles from tbl_group_members g,tbl_members m where groupid=? " +
            "and g.userid=m.userid";

    /**
     * 获得某个站点下所有用户组的Log信息
     * Add by EricDu 2007-9-23
     *
     * @param siteID 站点id
     * @return 用户组Log List
     * @throws CmsException 异常
     */
    public List getGroupsLog(int siteID, String where) throws CmsException {
        String SQL_SEARCH_USERS_LOG_INFO_OF_GROUP = "select count(id),max(acttype) as acttype from tbl_log where editor" +
                " in (select userid from tbl_group_members where groupid=?) " + where + " group by acttype order by acttype";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Group group;
        String logInfo;

        List list = new ArrayList();
        List logList = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPS);

            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                group = load(rs);
                list.add(group);
            }
            rs.close();
            pstmt.close();

            for (int i = 0; i < list.size(); i++) {
                group = (Group) list.get(i);
                int groupid = group.getGroupID();

                /*if (where.equals("")) {
                    pstmt = conn.prepareStatement(SQL_GET_GROUPS_LOG_INFO);
                    pstmt.setInt(1, groupid);
                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        logInfo = group.getGroupID() + "@" + group.getGroupName() + "@" + group.getGroupDesc() + "@" +
                                rs.getInt("createarticles") + "@" + rs.getInt("editarticles") + "@" + rs.getInt("deletearticles");
                        logList.add(logInfo);
                    }
                    rs.close();
                    pstmt.close();
                } else*/ {
                    pstmt = conn.prepareStatement(SQL_SEARCH_USERS_LOG_INFO_OF_GROUP);
                    pstmt.setInt(1, groupid);
                    rs = pstmt.executeQuery();

                    logInfo = group.getGroupID() + "@" + group.getGroupName() + "@" + group.getGroupDesc() + "@";
                    while (rs.next()) {
                        logInfo = logInfo + rs.getInt(1) + "@";
                    }
                    if(logInfo.split("@").length < 6)
                        logInfo = logInfo + "0@0@0@";
                    logInfo = logInfo.substring(0, logInfo.length() - 1);
                    logList.add(logInfo);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return logList;
    }

    private static final String SQL_GET_USERS =
            "SELECT * FROM TBL_Members WHERE SiteID = ?";

    public List getGroup_Users(int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        User user;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USERS);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = load_user(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_USERS_GROUPS =
            "SELECT UserID FROM TBL_Group_Members WHERE GroupID = ?";

    public String[] getUsers_Groups(int groupID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String[] users = null;
        int size = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USERS_GROUPS);
            pstmt.setInt(1, groupID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                size++;
            }
            users = new String[size];
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                users[i] = rs.getString("UserID");
                i++;
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return users;
    }

    private static final String SQL_GETRIGHTID =
            "SELECT TOP 1 RightName FROM TBL_Right WHERE RightID = ?";

    public String getRightID(String RightID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String RightName = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETRIGHTID);
            pstmt.setString(1, RightID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RightName = rs.getString("RightName");
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return RightName;
    }

    private static final String SQL_GETGROUPS_NEW1 =
            "SELECT DISTINCT GroupID, GroupName, GroupDesc FROM TBL_Group WHERE GroupID = ?";

    public Group getGroups_New(String groupID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Group group = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPS_NEW1);
            pstmt.setString(1, groupID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                group = load_new(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return group;
    }

    private static final String SQL_GETGROUPS_NEW2 =
            "SELECT DISTINCT ColumnID FROM TBL_Group_Rights WHERE GroupID = ?";

    public List getGroupsColumn_New(String groupID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Group group;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPS_NEW2);
            pstmt.setString(1, groupID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                group = load_column(rs);
                list.add(group);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_GROUP_COLUMN_REMAIN_RIGHT =
            "SELECT RightID,RightName FROM TBL_Right WHERE RightID NOT IN (SELECT DISTINCT RightID " +
                    "FROM TBL_Group_Rights WHERE GroupID = ?)";

    public List getGroupsRight_Remain(int groupID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_GROUP_COLUMN_REMAIN_RIGHT);
            pstmt.setInt(1, groupID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load_right(rs));
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public List getGroups(int startIndex, int numResults) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Group group;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPS);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    group = load(rs);
                    list.add(group);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get Group permission failed.");
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_DELETE_GROUPS =
            "DELETE FROM TBL_Group_Members WHERE GroupID = ?";

    private static final String SQL_INSERT_GROUPS =
            "INSERT INTO TBL_Group_Members (UserID, GroupID) VALUES(?, ?)";

    public void update_UserGroups(String[] userID, int groupID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_DELETE_GROUPS);
                conn.setAutoCommit(false);
                pstmt.setInt(1, groupID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_INSERT_GROUPS);
                for (int i = 0; i < userID.length; i++) {
                    pstmt.setString(1, userID[i].trim());
                    pstmt.setInt(2, groupID);
                    pstmt.executeUpdate();
                }
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update permission failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    Group load(ResultSet rs) throws SQLException {
        Group group = new Group();
        group.setGroupID(rs.getInt("groupid"));
        try {
            group.setGroupName(rs.getString("groupname"));
            group.setGroupDesc(rs.getString("groupdesc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    Group load_new(ResultSet rs) throws SQLException {
        Group group = new Group();
        try {
            group.setGroupID(rs.getInt("GroupID"));
            group.setGroupName(rs.getString("GroupName"));
            group.setGroupDesc(rs.getString("GroupDesc"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    Group load_column(ResultSet rs) throws SQLException {
        Group group = new Group();
        try {
            //group.setGroupID(rs.getString("GroupID"));
            group.setColumnID(rs.getInt("ColumnID"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    Rights load_right(ResultSet rs) throws SQLException {
        Rights right = new Rights();
        try {
            right.setRightID(rs.getInt("RightID"));
            right.setRightCName(rs.getString("RightName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return right;
    }

    User load_user(ResultSet rs) throws SQLException {
        User user = new User();

        user.setID(rs.getString("UserID"));
        user.setNickName(rs.getString("NickName"));

        return user;
    }
}
