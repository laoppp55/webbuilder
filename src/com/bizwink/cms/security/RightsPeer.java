package com.bizwink.cms.security;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.news.*;

public class RightsPeer implements IRightsManager {
    PoolServer cpool;

    public RightsPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IRightsManager getInstance() {
        return CmsServer.getInstance().getFactory().getRightsManager();
    }

    private static final String SQL_GETRIGHT =
            "SELECT rightid,RIGHTNAME FROM TBL_RIGHT WHERE rightID=?";

    public Rights getRight(int rightID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Rights right = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETRIGHT);
            pstmt.setInt(1, rightID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                right = load(rs);
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
        return right;
    }

    private static final String SQL_GETRIGHTCOUNT =
            "SELECT count(rightid) FROM TBL_RIGHT ";

    public int getRightCount() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETRIGHTCOUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get right count failed.");
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

    //获取权限表中与用户相关的权限    used by peter song at 8,6,2003
    private static final String SQL_GET_UrRights = "SELECT RightID,RightName FROM TBL_Right where RightID >= 50";

    public List getUrRights() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UrRights);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //获取权限表中与栏目相关的权限
    private static final String SQL_GET_CrRights = "SELECT RightID,RightName FROM TBL_Right where RightID < 50";

    public List getCrRights() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_CrRights);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //获取权限表中所有的权限
    private static final String SQL_GETRIGHTS =
            "SELECT RightID,RightName FROM TBL_Right";

    public List getRights() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Rights right;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETRIGHTS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //执行与用户相关的授权操作，删除用户原有的权限，授予用户新的权限.
    private static final String SQL_Del_OldUserRights =
            "delete from TBL_Members_Rights where userid=? and rightid>=50";

    private static final String SQL_Grant_NewUserRights =
            "INSERT INTO TBL_Members_Rights (userid,columnid,rightid) VALUES (?, ?, ?)";

    public int grantToUser(String userid, List rlist) throws CmsException {
        int succNum = -1;

        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_Del_OldUserRights);
                pstmt.setString(1, userid);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_Grant_NewUserRights);
                for (int i = 0; i < rlist.size(); i++) {
                    pstmt.setString(1, userid);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, Integer.parseInt((String) rlist.get(i)));
                    pstmt.executeUpdate();
                }
                pstmt.close();

                conn.commit();
                succNum = 0;
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: insert into  TBL_Members_Rights failed.");
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

        return succNum;
    }

    private static final String SQL_Grant_NewColRights = "INSERT INTO TBL_Members_Rights (userid,columnid,rightid) VALUES (?, ?, ?)";

    public int grantToColumns(String userID, int rightID, List columnList, int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        IColumnUserManager columnUserMgr = ColumnUserPeer.getInstance();
        int errcode = 0;

        try {
            //计算需要删除的栏目
            String SQL_Del_Columns = "delete from TBL_Members_Rights where UserID=? and rightID=? and columnID > 0";
            String columnIDs = "";
            for (int i = 0; i < columnList.size(); i++)
                columnIDs += columnList.get(i) + ",";
            if (columnIDs.length() > 0) {
                columnIDs = columnIDs.substring(0, columnIDs.length() - 1);
                SQL_Del_Columns += " and ColumnID NOT IN (" + columnIDs + ")";
            }

            //计算需要增加的栏目
            List insertList = new ArrayList();
            if (columnList.size() > 0) {
                columnIDs = ",";
                List oldColumnList = columnUserMgr.getUserColsFromTBL_Members_Rights(userID, rightID);
                for (int i = 0; i < oldColumnList.size(); i++)
                    columnIDs = columnIDs + ((ColumnUser) (oldColumnList.get(i))).getColumnID() + ",";
                for (int i = 0; i < columnList.size(); i++) {
                    int columnID =  (int)columnList.get(i);
                    if (columnIDs.indexOf("," + columnID + ",") == -1) insertList.add(columnID);
                }
            }

            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(SQL_Del_Columns);
            pstmt.setString(1, userID);
            pstmt.setInt(2, rightID);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_Grant_NewColRights);
            for (int i = 0; i < insertList.size(); i++) {
                pstmt.setString(1, userID);
                pstmt.setInt(2,(int)insertList.get(i));
                pstmt.setInt(3,rightID);
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();

            //分析现有栏目树，剔除掉子栏目
            String cids = "";
            columnList = columnUserMgr.getUserColsFromTBL_Members_Rights(userID, rightID);
            columnList = processColumnIDList(columnList, siteID);
            for (int i = 0; i < columnList.size(); i++)
                cids = cids + columnList.get(i) + ",";

            if (cids.length() > 0) {
                cids = "(" + cids.substring(0, cids.length() - 1) + ")";
                String sql = "delete from tbl_members_rights where userid = ? and rightid = ? and columnid > 0 and columnid not in " + cids;
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userID);
                pstmt.setInt(2, rightID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
            throw new CmsException("Database exception: insert into  TBL_Members_Rights failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return errcode;
    }

    //剔除栏目树中的子栏目
    private List processColumnIDList(List columnList, int siteID) {
        List columnIDList = new ArrayList();

        try {
            Tree colTree = TreeManager.getInstance().getSiteTree(siteID);
            int webRootID = colTree.getTreeRoot();
            for (int i = 0; i < columnList.size(); i++) {
                int columnID = ((ColumnUser) (columnList.get(i))).getColumnID();
                if (columnID == webRootID) {
                    columnIDList.add(String.valueOf(columnID));
                    return columnIDList;
                }
            }

            String cids = ",";
            IArticleManager articleMgr = ArticlePeer.getInstance();

            for (int i = 0; i < columnList.size(); i++) {
                int columnID = ((ColumnUser) (columnList.get(i))).getColumnID();
                String subCids = articleMgr.getColumnIDs(columnID);
                subCids = subCids.substring(1, subCids.length() - 1);
                if (subCids.indexOf(",") > -1) {
                    subCids = subCids.substring(subCids.indexOf(",") + 1);
                    cids = cids + subCids + ",";
                }
            }

            for (int i = 0; i < columnList.size(); i++) {
                int columnID = ((ColumnUser) (columnList.get(i))).getColumnID();
                if (cids.indexOf("," + columnID + ",") == -1) columnIDList.add(String.valueOf(columnID));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return columnIDList;
    }

    //执行与栏目相关的授权操作，删除栏目上原有的权限，授予用户在该栏目上新的权限.
    private static final String Group_SQL_Del_OldColRights =
            "delete from TBL_Group_Rights where groupid=? and columnid=?";

    private static final String Group_SQL_Grant_NewColRights =
            "INSERT INTO TBL_Group_Rights (groupid,columnid,rightid) VALUES (?, ?, ?)";

    public int grantGroupToColumns(int groupid, int columnid, List rlist) throws CmsException {
        int succNum = -1;

        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(Group_SQL_Del_OldColRights);
                pstmt.setInt(1, groupid);
                pstmt.setInt(2, columnid);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(Group_SQL_Grant_NewColRights);
                for (int i = 0; i < rlist.size(); i++) {
                    pstmt.setInt(1, groupid);
                    pstmt.setInt(2, columnid);
                    pstmt.setInt(3, Integer.parseInt((String) rlist.get(i)));
                    pstmt.executeUpdate();
                }
                pstmt.close();

                conn.commit();
                succNum = 0;
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: insert into  TBL_Members_Rights failed.");
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

        return succNum;
    }

    private static final String SQL_Grant_NewGroupRights =
            "INSERT INTO TBL_Group_Rights (groupid,columnid,rightid) VALUES (?, ?, ?)";

    public void grantToGroup(int groupID, int rightID, List columnList, int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                //计算需要删除的栏目
                String SQL_Del_Columns = "delete from TBL_Group_Rights where groupid=? and rightID=? and columnID > 0";
                String columnIDs = "";
                for (int i = 0; i < columnList.size(); i++)
                    columnIDs += columnList.get(i) + ",";
                if (columnIDs.length() > 0) {
                    columnIDs = columnIDs.substring(0, columnIDs.length() - 1);
                    SQL_Del_Columns += " and ColumnID NOT IN (" + columnIDs + ")";
                }

                //计算需要增加的栏目
                List insertList = new ArrayList();
                if (columnList.size() > 0) {
                    columnIDs = ",";
                    List oldColumnList = getGroupColumnRight(groupID, rightID);
                    for (int i = 0; i < oldColumnList.size(); i++)
                        columnIDs = columnIDs + ((ColumnUser) (oldColumnList.get(i))).getColumnID() + ",";
                    for (int i = 0; i < columnList.size(); i++) {
                        String columnID = (String) columnList.get(i);
                        if (columnIDs.indexOf("," + columnID + ",") == -1) insertList.add(columnID);
                    }
                }

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_Del_Columns);
                pstmt.setInt(1, groupID);
                pstmt.setInt(2, rightID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_Grant_NewGroupRights);
                for (int i = 0; i < insertList.size(); i++) {
                    pstmt.setInt(1, groupID);
                    pstmt.setInt(2, Integer.parseInt((String) insertList.get(i)));
                    pstmt.setInt(3, rightID);
                    pstmt.executeUpdate();
                }
                pstmt.close();
                conn.commit();

                //分析现有栏目树，剔除掉子栏目
                String cids = "";
                columnList = getGroupColumnRight(groupID, rightID);
                columnList = processColumnIDList(columnList, siteID);
                for (int i = 0; i < columnList.size(); i++)
                    cids = cids + columnList.get(i) + ",";

                if (cids.length() > 0) {
                    cids = "(" + cids.substring(0, cids.length() - 1) + ")";
                    String sql = "delete from TBL_Group_Rights where groupID = ? and rightid = ? and columnid > 0 and columnid not in " + cids;
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, groupID);
                    pstmt.setInt(2, rightID);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: insert into  TBL_Members_Rights failed.");
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

    //获取用户在某个栏目上的授权
    private static final String SQL_GET_USER_COLUMN_RIGHT = "select a.rightid,a.userid,a.columnid,b.rightname " +
            "from tbl_members_rights a,tbl_right b where a.userid=? and a.columnid=? and a.rightid=b.rightid and a.rightid > 0";

    public List getUserColumnRight(String userID, int columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USER_COLUMN_RIGHT);
            pstmt.setString(1, userID);
            pstmt.setInt(2, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_GrantedUser_Rights = "SELECT DISTINCT a.RightID,b.RightName FROM TBL_Members_Rights a,TBL_Right b " +
            "WHERE a.UserID = ? AND a.RightID = b.RightID";

    public List getGrantedUserRights(String userID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_GrantedUser_Rights);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //获取用户组在某个栏目上的授权
    private static final String SQL_GET_GROUP_COLUMN_RIGHT = "select DISTINCT a.columnid,b.cname from tbl_group_rights a,tbl_column b where " +
            "a.groupid = ? and a.columnid = b.ID and a.rightid = ? and a.columnid > 0";

    public List getGroupColumnRight(int groupID, int rightID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        ColumnUser column=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_GROUP_COLUMN_RIGHT);
            pstmt.setInt(1, groupID);
            pstmt.setInt(2, rightID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = new ColumnUser();
                column.setColumnID(rs.getInt("ColumnID"));
                column.setColumnCname(rs.getString("CName"));
                list.add(column);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_GrantedGroup_Rights = "SELECT DISTINCT a.RightID,b.rightname FROM TBL_Group_Rights a,tbl_right b,tbl_group c WHERE " +
            "a.groupid = ? AND c.siteid = ? and a.rightid = b.rightid and c.groupid=a.groupid";

    public List getGrantedGroupRights(int groupid, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_GrantedGroup_Rights);
            pstmt.setInt(1, groupid);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_QUERYCOLUMNS_ByRIGHTID = "SELECT columnid FROM TBL_Members_Rights WHERE UserID = ? and RightID = ? and RightID<50";

    public List getColumnsByRightidAndUserid(int siteid,String userid,int rightID) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<Integer> columns = new ArrayList<>();
        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_QUERYCOLUMNS_ByRIGHTID);
                pstmt.setString(1, userid);
                pstmt.setInt(2, rightID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    columns.add(rs.getInt("columnid"));
                }
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

        return columns;
    }

    private static final String SQL_QUERYRIGHTS = "SELECT * FROM TBL_Members_Rights WHERE UserID = ? and ColumnID = ? and RightID = ?";

    public boolean QueryRights(String userID, int columnID, String rightID) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean exit = false;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_QUERYRIGHTS);
                pstmt.setString(1, userID);
                pstmt.setInt(2, columnID);
                pstmt.setString(3, rightID);
                rs = pstmt.executeQuery();
                if (rs.next()) exit = true;
                rs.close();
                pstmt.close();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
            }
            finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error in rolling back the pooled connection " + e.toString());
        }
        return exit;
    }

    //查询某用户该栏目有无指定的权限
    private static final String SQL_QUERY_RIGHTS_ON_THIS_COLUMN = "SELECT ColumnID FROM TBL_Members_Rights " +
            "WHERE UserID = ? AND RightID = ? AND ColumnID = ? " +
            "UNION " +
            "SELECT ColumnID FROM TBL_Group WHERE GroupID IN " +
            "(SELECT GroupID FROM TBL_MemberGroup WHERE UserID = ?) " +
            "AND " +
            "RightID = ? AND ColumnID = ?";

    //查询某用户该栏目的父栏目有无指定权限
    private static final String SQL_PARENT_COLUMNID_OF_THIS_COLUMN = "SELECT ParentID FROM TBL_Column WHERE ID = ? AND ParentID <> 0";

    public boolean hasRightOnThisColumn(String userID, String rightID, int columnID) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean exit = false;
        int colID = -1;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_QUERY_RIGHTS_ON_THIS_COLUMN);
                pstmt.setString(1, userID);
                pstmt.setString(2, rightID);
                pstmt.setInt(3, columnID);
                pstmt.setString(4, userID);
                pstmt.setString(5, rightID);
                pstmt.setInt(6, columnID);
                rs = pstmt.executeQuery();
                if (rs.next())
                    exit = true;
                rs.close();
                pstmt.close();

                //查询某用户该栏目的父栏目有无指定权限
                pstmt = conn.prepareStatement(SQL_PARENT_COLUMNID_OF_THIS_COLUMN);
                pstmt.setInt(1, columnID);
                rs = pstmt.executeQuery();
                if (rs.next())
                    colID = rs.getInt("ParentID");
                rs.close();
                pstmt.close();

                if (colID != -1) {
                    pstmt = conn.prepareStatement(SQL_QUERY_RIGHTS_ON_THIS_COLUMN);
                    pstmt.setString(1, userID);
                    pstmt.setString(2, rightID);
                    pstmt.setInt(3, colID);
                    pstmt.setString(4, userID);
                    pstmt.setString(5, rightID);
                    pstmt.setInt(6, colID);
                    rs = pstmt.executeQuery();
                    if (rs.next())
                        exit = true;
                    rs.close();
                    pstmt.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
            }
            finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("Error in rolling back the pooled connection " + e.toString());
        }
        return exit;
    }

    private static final String SQL_CREATERIGHTS = "INSERT INTO TBL_Members_Rights(UserID,ColumnID,RightID) VALUES (?, ?, ?)";

    public void CreateUserRights(Rights right) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CREATERIGHTS);
                pstmt.setString(1, right.getUserID());
                pstmt.setInt(2, right.getColumnID());
                pstmt.setInt(3, right.getRightID());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
            }
            finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETEUSERRIGHT = "DELETE FROM TBL_Members_Rights WHERE userID = ?";

    public void withDrawGrant(String userid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELETEUSERRIGHT);
                pstmt.setString(1, userid);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user Rights from TBL_Members_Rights failed.");
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

    public void DelUserRights(String userID, String column_Right, String columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        String SQL_DELUSERRIGHTS = "DELETE FROM TBL_Members_Rights WHERE UserID ='" + userID + "' AND ColumnID IN (" + columnID + ") AND " +
                "RTRIM(LTRIM(STR(ColumnID) + ':' + RightID)) NOT IN (" + column_Right + ")";
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELUSERRIGHTS);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user Rights from TBL_Members_Rights failed.");
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

    public void DelGroupRights(String groupID, String column_Right, String columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        String SQL_DELGROUPRIGHTS = "DELETE FROM TBL_Group WHERE GroupID ='" + groupID + "' AND ColumnID IN (" + columnID + ") AND " +
                "RTRIM(LTRIM(STR(ColumnID) + ':' + RightID)) NOT IN (" + column_Right + ")";
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELGROUPRIGHTS);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user Rights from TBL_Members_Rights failed.");
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

    public void DelUserColumns(String userID, String columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        String SQL_DELUSERRIGHTS = "DELETE FROM TBL_Members_Rights WHERE UserID ='" + userID + "' " +
                "AND ColumnID NOT IN (" + columnID + ") ";
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELUSERRIGHTS);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user Rights from TBL_Members_Rights failed.");
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

    public void DelGroupColumns(String groupID, String columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        String SQL_DELGROUPRIGHTS = "DELETE FROM TBL_Group WHERE GroupID ='" + groupID + "' " +
                "AND ColumnID NOT IN (" + columnID + ") ";
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELGROUPRIGHTS);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user Rights from TBL_Members_Rights failed.");
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

    //取得该用户本身的权限和所属组的权限,进行叠加
    private static final String SQL_GETUSERRIGHTS = "SELECT RIGHTID, RIGHTNAME FROM TBL_RIGHT " +
            "WHERE RIGHTID IN " +
            "(SELECT DISTINCT RightID FROM TBL_Members_Rights WHERE " +
            "UserID = ? AND RightID IS NOT NULL " +
            "UNION " +
            "SELECT DISTINCT RightID FROM TBL_Group WHERE GroupID IN " +
            "(SELECT GroupID FROM tbl_group_members WHERE UserID = ?) " +
            "AND RightID IS NOT NULL)";

    public List getRights(String uid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSERRIGHTS);
            pstmt.setString(1, uid);
            pstmt.setString(2, uid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_REMAIN_RIGHTS = "SELECT RIGHTID,RIGHTNAME FROM TBL_RIGHT WHERE RIGHTID NOT IN (SELECT DISTINCT RIGHTID FROM TBL_MEMBERS_RIGHTS WHERE USERID = ?)";

    public List getRemainRights(String userID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Rights right=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REMAIN_RIGHTS);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                right = load(rs);
                list.add(right);
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

    Rights load(ResultSet rs) throws SQLException {
        Rights right = new Rights();
        try {
            right.setRightID(rs.getInt("RightID"));
            right.setRightCName(rs.getString("RightName"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return right;
    }
}