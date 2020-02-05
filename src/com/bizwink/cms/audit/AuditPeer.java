package com.bizwink.cms.audit;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.extendAttr.ExtendAttrException;
import com.bizwink.cms.news.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.util.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.publish.Publish;
import com.bizwink.cms.register.Register;
import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.PublishQueuePeer;
import org.apache.regexp.*;

public class AuditPeer implements IAuditManager {
    PoolServer cpool;

    public AuditPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IAuditManager getInstance() {
        return CmsServer.getInstance().getFactory().getAuditManager();
    }

    private static final String SQL_CREATE_AUDIT_RULES_FOR_ORACLE = "INSERT INTO TBL_Column_Auditing_Rules(ColumnID,Column_Audit_Rule," +
            "Creator,CreateDate,LastUpdated,Editor,audittype,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_RULES_FOR_MSSQL = "INSERT INTO TBL_Column_Auditing_Rules(ColumnID,Column_Audit_Rule," +
            "Creator,CreateDate,LastUpdated,Editor,audittype) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_RULES_FOR_MYSQL = "INSERT INTO TBL_Column_Auditing_Rules(ColumnID,Column_Audit_Rule," +
            "Creator,CreateDate,LastUpdated,Editor,audittype) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_PROCESS_FOR_ORACLE = "INSERT INTO tbl_column_auditing_process(rulesid,subrules_by_or," +
            "columnid,Creator,Editor,CreateDate,LastUpdated,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_PROCESS_FOR_MSSQL = "INSERT INTO tbl_column_auditing_process(rulesid,subrules_by_or," +
            "columnid,Creator,Editor,CreateDate,LastUpdated) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_PROCESS_FOR_MYSQL = "INSERT INTO tbl_column_auditing_process(rulesid,subrules_by_or," +
            "columnid,Creator,Editor,CreateDate,LastUpdated) VALUES (?, ?, ?, ?, ?, ?, ?)";

    //创建栏目审核规则
    public void create(Audit audit) throws CmsException {
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Connection conn = null;
        PreparedStatement pstmt=null;
        int next_ruleid = 0;
        String audit_Rules = StringUtil.gb2isoindb(audit.getAuditRules());

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            //写审核规则表
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_RULES_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_RULES_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_RULES_FOR_MYSQL);
            pstmt.setInt(1, audit.getColumnID());
            pstmt.setString(2,audit_Rules);
            pstmt.setString(3, audit.getCreator());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(6, audit.getCreator());
            pstmt.setInt(7, audit.getAudittype());
            if (cpool.getType().equals("oracle")) {
                next_ruleid = sequnceMgr.nextID("ColumnAuditRule");
                pstmt.setInt(8, next_ruleid);
                pstmt.executeUpdate();
                pstmt.close();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();
            }

            //写审核进程表
            String[] sub_audit_rules = audit_Rules.split("AND");
            for (int ii=0; ii<sub_audit_rules.length; ii++) {
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_PROCESS_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_PROCESS_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_PROCESS_FOR_MYSQL);
                pstmt.setInt(1, next_ruleid);
                pstmt.setString(2, sub_audit_rules[ii]);
                pstmt.setInt(3, audit.getColumnID());
                pstmt.setString(4, audit.getCreator());
                pstmt.setString(5, audit.getCreator());
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    int next_subruleid = sequnceMgr.getSequenceNum("Comment");
                    pstmt.setInt(8, next_subruleid);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exp1) {

            }
            throw new CmsException("Database exception: create user failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_UPDATE_AUDIT_RULES =
            "UPDATE TBL_Column_Auditing_Rules SET Column_Audit_Rule=?,LastUpdated=?,Editor=?,audittype=? WHERE columnID=?";

    //更新栏目审核规则
    public void update(Audit audit) throws CmsException {
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Connection conn = null;
        PreparedStatement pstmt=null;
        String audit_Rules = StringUtil.gb2isoindb(audit.getAuditRules());

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UPDATE_AUDIT_RULES);
            pstmt.setString(1, audit_Rules);
            pstmt.setTimestamp(2, audit.getLastUpdated());
            pstmt.setString(3, audit.getEditor());
            pstmt.setInt(4, audit.getAudittype());
            pstmt.setInt(5, audit.getColumnID());
            pstmt.executeUpdate();
            pstmt.close();

            //删除审核进程表中记录的审核进程信息
            pstmt = conn.prepareStatement("delete from tbl_column_auditing_process where rulesid=?");
            pstmt.setInt(1,audit.getID());
            pstmt.executeUpdate();
            pstmt.close();

            //重新写审核进程表
            String[] sub_audit_rules = audit_Rules.split("AND");
            for (int ii=0; ii<sub_audit_rules.length; ii++) {
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_PROCESS_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_PROCESS_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_PROCESS_FOR_MYSQL);
                pstmt.setInt(1, audit.getID());
                pstmt.setString(2, sub_audit_rules[ii]);
                pstmt.setInt(3, audit.getColumnID());
                pstmt.setString(4, audit.getCreator());
                pstmt.setString(5, audit.getCreator());
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    int next_subruleid = sequnceMgr.getSequenceNum("Comment");
                    pstmt.setInt(8, next_subruleid);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try{
                conn.rollback();
            } catch (SQLException exp){

            }
            throw new CmsException("Database exception: update user failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_DEL_AUDIT_RULES =
            "DELETE TBL_Column_Auditing_Rules WHERE ID = ?";

    //删除栏目审核规则
    public void delete(int ID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DEL_AUDIT_RULES);
                pstmt.setInt(1, ID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user failed.");
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

    private static final String SQL_GET_RIGHTS_USERS =
            "SELECT DISTINCT UserID FROM TBL_Members_Rights WHERE ColumnID = ? AND RightID = 5 " +
                    "UNION SELECT UserID FROM TBL_Group_Members WHERE GroupID IN (SELECT GroupID FROM " +
                    "TBL_Group_Rights WHERE ColumnID = ? AND RightID = 5)";

    //查询某栏目上拥有<文章审核>权限的所有用户
    public List getUsers_hasAuditRight(int columnID, int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_RIGHTS_USERS);
            pstmt.setInt(1, columnID);
            //pstmt.setInt(2, siteID);
            pstmt.setInt(2, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getString(1));
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

    private static final String SQL_GET_USERS =
            "SELECT DISTINCT SiteID,UserID,NickName FROM TBL_Members WHERE ((UserID IN " +
                    "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN " +
                    "(SELECT UserID FROM TBL_Group_Members WHERE GroupID IN (SELECT GroupID " +
                    "FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public List getUsers() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        User user;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USERS);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                user = loaduser(rs);
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

    private static final String SQL_GET_ARTICLE =
            "SELECT Sign,Comments,CreateDate FROM TBL_Article_Auditing_Info " +
                    "WHERE ArticleID = ? AND BackTo = ? AND Status = ? ORDER BY CreateDate DESC";

    //某退稿文章的退稿信息
    public String[] getArticleInfo(int articleID, String userID, int type) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String[] result = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE);
            pstmt.setInt(1, articleID);
            pstmt.setString(2, userID);
            pstmt.setInt(3, type);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new String[3];
                result[0] = rs.getString("Sign");
                result[1] = rs.getString("Comments");
                result[2] = (rs.getTimestamp("CreateDate")).toString().substring(0, 19);
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
        return result;
    }

    private static final String SQL_GET_BACK_ARTICLE =
            "SELECT a.ID,a.ColumnID,a.MainTitle,a.Editor,a.auditflag,b.Sign,b.Comments,b.CreateDate " +
                    "FROM TBL_Article a,TBL_Article_Auditing_Info b WHERE b.BackTo = ? AND " +
                    "a.AuditFlag = 2 AND a.Status = 1 AND a.ID = b.ArticleID AND b.Status <> 0";

    //查询退给某用户的文章
    public List getBack_Articles(String userID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Audit audit;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_BACK_ARTICLE);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                audit = load_articles(rs);
                list.add(audit);
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

    private static final String SQL_GET_AUDIT_ARTICLE =
            "SELECT ID,ColumnID,MainTitle,Editor,Auditor FROM TBL_Article WHERE ColumnID IN " +
                    "(SELECT ColumnID FROM TBL_Members_Rights WHERE UserID = ? AND RightID = 5) AND AuditFlag = 1";

    private static final String SQL_GET_AUDIT_RULES =
            "SELECT DISTINCT ColumnID,Column_Audit_Rule FROM TBL_Column_Auditing_Rules WHERE " +
                    "ColumnID IN (SELECT ID FROM TBL_Column WHERE SiteID = ?)";

    //查询某用户需审核的文章
    public List getArticles_NeedAudit(String userID, int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Audit audit;
        //用户角色
        List userRoles = getUserRoles(userID.substring(1, userID.length() - 1));
        //最大优先
        String roles = "";
        if (userRoles.size() > 0) {
            roles = "[" + (String) userRoles.get(0) + "]";
        }
        //读出用户的审核范围
        IUserManager uMgr = UserPeer.getInstance();
        User user = uMgr.getUser(userID.substring(1, userID.length() - 1));

        try {
            conn = cpool.getConnection();
            //读出该用户可以审核的所有文章
            if (user != null && user.getDepartmentarticlestype() == 1) {
                //用户可以审核本部门的文章
                List userList = uMgr.getUserInfoByDepartmentIds(user.getDepartment());
                String userids = "";
                for(int i = 0; i < userList.size();i++){
                    User u = (User)userList.get(i);
                    if(u !=null)
                    {
                        if(i == userList.size() - 1)
                        {
                            userids += u.getUserID();
                        }
                        else{
                            userids += u.getUserID() + ",";
                        }
                    }
                }
                pstmt = conn.prepareStatement("SELECT ID,ColumnID,MainTitle,Editor,Auditor FROM TBL_Article WHERE ColumnID IN " +
                        "(SELECT ColumnID FROM TBL_Members_Rights WHERE UserID = '"+userID.substring(1, userID.length() - 1)+"' AND RightID = 5) AND AuditFlag = 1 and Editor in('"+userids+"')");

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auditor = rs.getString("Auditor");

                    if (auditor == null) auditor = "";
                    if (auditor.indexOf(userID) == -1 || auditor.indexOf(roles) > -1) {
                        audit = load_article(rs);
                        list.add(audit);
                    }
                }
                rs.close();
                pstmt.close();
            } else if (user != null && user.getDepartmentarticlestype() == 2) {
                //用户可以审核指定部门的文章
                List userList = uMgr.getUserInfoByDepartmentIds(user.getDepartmentarticlesids());
                String userids = "";
                for(int i = 0; i < userList.size();i++){
                    User u = (User)userList.get(i);
                    if(u !=null)
                    {
                        if(i == userList.size() - 1)
                        {
                            userids += u.getUserID();
                        }
                        else{
                            userids += u.getUserID() + ",";
                        }
                    }
                }
                pstmt = conn.prepareStatement("SELECT ID,ColumnID,MainTitle,Editor,Auditor FROM TBL_Article WHERE ColumnID IN(SELECT ColumnID FROM TBL_Members_Rights WHERE UserID = '"+userID.substring(1, userID.length() - 1)+"' AND RightID = 5) AND AuditFlag = 1 and Editor in('"+userids+"')");

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auditor = rs.getString("Auditor");

                    if (auditor == null) auditor = "";
                    if (auditor.indexOf(userID) == -1 || auditor.indexOf(roles) > -1) {
                        audit = load_article(rs);
                        list.add(audit);
                    }
                }
                rs.close();
                pstmt.close();
            } else {

                pstmt = conn.prepareStatement(SQL_GET_AUDIT_ARTICLE);
                pstmt.setString(1, userID.substring(1, userID.length() - 1));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String auditor = rs.getString("Auditor");

                    if (auditor == null) auditor = "";
                    if (auditor.indexOf(userID) == -1 || auditor.indexOf(roles) > -1) {
                        audit = load_article(rs);
                        list.add(audit);
                    }
                }
                rs.close();
                pstmt.close();
            }


            //读出该用户参与审核的所有栏目
            List columnIDList = new ArrayList();
            pstmt = conn.prepareStatement(SQL_GET_AUDIT_RULES);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String rule = rs.getString("Column_Audit_Rule");
                if (rule == null) rule = "";

                if (rule.indexOf(userID) > -1 || rule.indexOf(roles) > -1) {
                    columnIDList.add(String.valueOf(rs.getInt("ColumnID")));
                }
            }
            rs.close();
            pstmt.close();

            //根据以上获得的数据，得到用户需要审核的所有文章
            List tempList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                audit = (Audit) list.get(i);
                for (int j = 0; j < columnIDList.size(); j++) {
                    if (audit.getColumnID() == Integer.parseInt((String) columnIDList.get(j))) {
                        tempList.add(audit);
                        break;
                    }
                }
            }
            //再继续分析，得到目前需要该用户审核的文章
            list = new ArrayList();
            for (int i = 0; i < tempList.size(); i++) {

                audit = (Audit) tempList.get(i);
                int articleID = audit.getID();


                if (query_User_isAuditing(userID, articleID))     //该用户是否现在就能审核某文章
                {
                    list.add(audit);
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
        return list;
    }

    //查询某用户对某篇文章是否有审核权限
    public boolean query_User_isAuditing(String userID, int articleID) {
        boolean query = false;
        //用户角色
        List userRoles = getUserRoles(userID.substring(1, userID.length() - 1));
        //最大优先
        String roles = "";
        if (userRoles.size() > 0)  roles = "[" + (String) userRoles.get(0) + "]";

        try {
            String audit_Rules = getColumnAuditRules(articleID);
            if (audit_Rules == null || userID == null || userID.trim().length() == 0 || audit_Rules.trim().length() == 0) {
                return false;
            }

            //分析规则及以及审核过的用户。来判断当前用户是否需参加审核
            RE regOR = new RE(" OR ");
            if (audit_Rules.indexOf(userID) != -1 || audit_Rules.indexOf(roles) > -1)        //是否包含当前用户
            {
                if (audit_Rules.indexOf(" OR ") == -1)      //没有OR
                {
                    query = query_RulesString(userID, articleID, audit_Rules);   //是否有AND
                } else {       //有OR的情况
                    String rules[] = regOR.split(audit_Rules);
                    int k = -1;
                    for (int i = 0; i < rules.length; i++) {
                        if (rules[i].trim().indexOf(userID) != -1 || rules[i].trim().indexOf(roles) > -1) {
                            k = i;
                            break;
                        }
                    }

                    String rule = rules[k].trim();
                    query = query_RulesString(userID, articleID, rule);          //是否有AND
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return query;
    }

    private static final String SQL_GET_ARTICLE_ID =
            "SELECT ID FROM TBL_Article WHERE ColumnID = ? " +
                    "AND MainTitle = ? AND Editor = ? ORDER BY CreateDate DESC";

    //查询刚入库的文章ID
    public int getArticleID(int columnID, String maintitle, String editor) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int articleID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_ID);

            pstmt.setInt(1, columnID);
            pstmt.setString(2, maintitle);
            pstmt.setString(3, editor);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                articleID = rs.getInt("ID");
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
        return articleID;
    }

    private static final String SQL_GET_COMMENTS =
            "SELECT Comments FROM TBL_Article_Auditing_Info WHERE " +
                    "Sign = ? AND ArticleID = ? AND Status = 3 ORDER BY CreateDate DESC";

    //查询审核意见
    public String getComments(String userID, int articleID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String comments = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COMMENTS);
            pstmt.setString(1, userID);
            pstmt.setInt(2, articleID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                comments = rs.getString("Comments");
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

        return comments;
    }

    private static final String SQL_GET_COLUMN_CNAME = "SELECT Cname FROM TBL_Column WHERE ID = ?";

    //查询指定栏目ID的栏目中文名
    public String getColumn_Cname(int columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        String Cname = null;

        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement(SQL_GET_COLUMN_CNAME);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cname = rs.getString("Cname");
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

        return Cname;
    }

    private static final String SQL_GET_ALL_COLUMN_RULES = "SELECT * FROM TBL_Column_Auditing_Rules WHERE ColumnID = ?";

    //查询某栏目的审核规则
    public Audit getAuditRules(int columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Audit audit = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_COLUMN_RULES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                audit = load(rs);
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
        return audit;
    }

    private static final String SQL_GET_COLUMN_SUBRULES = "SELECT SUBRULES_BY_OR FROM tbl_column_auditing_process WHERE ColumnID = ?";

    //查询某栏目的审核规则
    public List getAuditSubRules(int columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<String> audits = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COLUMN_SUBRULES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                audits.add(rs.getString("SUBRULES_BY_OR"));
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
        return audits;
    }

    private static final String SQL_QUERY_AUDIT_RULES = "SELECT * FROM TBL_Column_Auditing_Rules WHERE ColumnID = ?";

    //查询某栏目是否有审核规则
    public boolean queryAuditRules(int columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean exit = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_QUERY_AUDIT_RULES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                exit = true;
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
        return exit;
    }

    private static final String SQL_QUERY_COLUMN_AUDIT_RULE =
            "select * from TBL_Article where AuditFlag = 1 and Auditor is not null and rtrim(ltrim(Auditor)) <> '' and ColumnID = ?";

    //查询某栏目的规则是否在应用之中
    public boolean queryColumn_isAudited(int columnID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean exit = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_QUERY_COLUMN_AUDIT_RULE);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                exit = true;
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
        return exit;
    }

    //查询某审核人的上一级审核人
    public String queryPrev_Auditor(String userID, int articleID,int siteid) throws CmsException {
        String auditor = "";
        int type = getColumnAuditRulesType(articleID);

        try {
            String audit_Rules = getColumnAuditRules(articleID);
            IUserManager userMgr = UserPeer.getInstance();
            if (audit_Rules != null && audit_Rules.length() != 0) {
                if (type == 0)   {                         //按用户审核
                    if (audit_Rules.indexOf(userID) != -1) {
                        int i = audit_Rules.indexOf(userID);
                        if (i != 0 && audit_Rules.substring(i - 3, i - 1).compareTo("OR") != 0) {
                            if (audit_Rules.substring(i - 4, i - 1).compareTo("AND") == 0) {
                                auditor = audit_Rules.substring(0, i - 5);
                                int j1 = auditor.lastIndexOf("[");
                                int j2 = auditor.lastIndexOf("]");
                                auditor = auditor.substring(j1, j2 + 1);
                                if (auditor != null && auditor.length() > 2) {
                                    auditor = auditor.trim().substring(1, auditor.length() - 1);
                                }
                            }
                        }
                    }
                } else {                                  //按角色定义进行审核
                    String userid = userID.substring(1,userID.length() - 1);
                    int deptid = userMgr.getDeptidByUser(userid ,siteid);
                    String current_user_role ="[" + userMgr.getRoleByUser(userid,siteid) + "]";
                    int posi = audit_Rules.indexOf(current_user_role);
                    String prev_user_role = null;
                    if (posi > -1) {
                        prev_user_role=audit_Rules.substring(0,posi).trim();
                        if (prev_user_role.endsWith("AND")) {
                            prev_user_role = prev_user_role.substring(0,prev_user_role.length() - 3).trim();
                            prev_user_role = prev_user_role.substring(1,prev_user_role.length() - 1);          //去掉左右的中括号
                            auditor = userMgr.getUserByRole(prev_user_role,deptid,siteid);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return auditor;
    }

    private static final String SQL_QUERY_ARTICLE_COLUMNID = "SELECT ColumnID FROM TBL_Article WHERE ID = ?";

    //查询某文章所属的栏目ID
    public int Query_Article_Column(int articleID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int columnID = -1;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_QUERY_ARTICLE_COLUMNID);
                pstmt.setInt(1, articleID);

                rs = pstmt.executeQuery();

                if (rs.next()) {
                    columnID = rs.getInt("ColumnID");
                }
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
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

        return columnID;
    }

    private static final String SQL_USER_ARTICLE_AUDIT = "SELECT Auditor FROM TBL_Article WHERE ID = ?";

    //判断某用户是否已审过某文章 0 没审过
    public int Query_User_Article_Audit(String userID, int articleID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int queryresult = 0;
        String users;
        //用户角色
        List userRoles = getUserRoles(userID.substring(1, userID.length() - 1));
        //最大优先
        String roles = "";
        if (userRoles.size() > 0) {
            roles = "[" + (String) userRoles.get(0) + "]";
        }
        int type = getColumnAuditRulesType(articleID);
        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_USER_ARTICLE_AUDIT);
                pstmt.setInt(1, articleID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    users = rs.getString("Auditor");
                    System.out.println("users=" + users);
                    System.out.println("userID=" + userID);
                    if (users != null) {
                        if (type == 0) {
                            if (users.indexOf(userID) != -1) {
                                int i = users.indexOf(userID);  //起始长度
                                int j = users.length();         //字符串总长度
                                int k = userID.length();        //用户名长度
                                if (i + k == j)
                                    queryresult = 1;
                                else
                                    queryresult = 2;
                            }
                        } else {
                            if (users.indexOf(roles) != -1) {
                                int i = users.indexOf(roles);  //起始长度
                                int j = users.length();         //字符串总长度
                                int k = roles.length();        //用户名长度
                                if (i + k == j)
                                    queryresult = 1;
                                else
                                    queryresult = 2;
                            }
                        }
                    }
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
        return queryresult;
    }

    //查询某用户是否已审过某文章
    private boolean Query_User_Article(String userID, int articleID, String audit_rules) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean query = false;
        //最大优先
        List userRoles = getUserRoles(userID.substring(1, userID.length() - 1));
        String roles = "";
        if (userRoles.size() > 0) {
            roles = "[" + (String) userRoles.get(0) + "]";
        }
        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_USER_ARTICLE_AUDIT);
                pstmt.setInt(1, articleID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    String auditor = rs.getString("Auditor");
                    if (auditor != null) {
                        if (auditor.indexOf(userID) != -1 || auditor.indexOf(roles) > -1) {
                            query = true;
                        }
                    }
                }
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
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
        return query;
    }

    private static final String SQL_QUERY_ARTICLE_COLUMN = "SELECT ColumnID FROM TBL_Article WHERE ID = ?";

    private static final String SQL_QUERY_COLUMN_RULES = "SELECT Column_Audit_Rule FROM TBL_Column_Auditing_Rules WHERE ColumnID = ?";

    //查询某文章所属栏目的审核规则
    private String getColumnAuditRules(int articleID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String audit_Rules = "";
        int columnID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_QUERY_ARTICLE_COLUMN);
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                columnID = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_QUERY_COLUMN_RULES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                audit_Rules = rs.getString(1);
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
        return audit_Rules;
    }

    //分析审核规则字符串(有无AND的情况)
    private boolean query_RulesString(String userID, int articleID, String audit_Rules) {
        boolean query = false;

        //用户角色
        List userRoles = getUserRoles(userID.substring(1, userID.length() - 1));
        //最大优先
        String roles = "";
        if (userRoles.size() > 0) {
            roles = "[" + (String) userRoles.get(0) + "]";
        }

        try {
            RE regAND = new RE(" AND ");
            if (audit_Rules.indexOf(" AND ") == -1)       //no AND
            {

                if (audit_Rules.compareTo(userID) == 0 || audit_Rules.compareTo(roles) == 0)     //是否等于当前用户
                {
                    query = true;
                }
            } else      //have AND
            {

                String user[] = regAND.split(audit_Rules);
                int j = -1;
                for (int i = 0; i < user.length; i++) {
                    if (user[i].trim().compareTo(userID) == 0 || user[i].trim().compareTo(roles) > -1) {
                        j = i;
                        break;
                    }
                }

                //判断其前面的用户是否已审过
                boolean isAudit = true;
                for (int i = 0; i < j; i++) {
                    if (!Query_User_Article(user[i].trim(), articleID, audit_Rules)) {
                        isAudit = false;
                        break;
                    }
                }
                if (isAudit) query = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return query;
    }

    private static final String SQL_UPDATE_AUDITFLAG = "UPDATE TBL_Article SET AuditFlag = ?,Auditor = ? WHERE ID = ?";

    //修改审核状态
    public void upateAuditFlag(int auditFlag, String auditor, int articleID, int siteId, int columnId) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (auditFlag == 0)            //当前文章全部审核完毕
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDITFLAG);
                    pstmt.setInt(1, auditFlag);
                    pstmt.setString(2, "");
                    pstmt.setInt(3, articleID);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //修改与该篇文章相关联的 栏目模板 的发布标志位
                    Publish publish;
                    String ids = "";

                    /*String SQL_Select_Model = "select id from tbl_template where isarticle <> 1 and status = 1 and columnID" +
                            " in (select id from tbl_column where siteid=?) and (relatedcolumnid like '%," + columnId + ",%' " +
                            "or relatedcolumnid like '%(" + columnId + ",%' or relatedcolumnid like '%," + columnId + ")%' or " +
                            "relatedcolumnid like '%(" + columnId + ")%')";*/
                    String SQL_Select_Model = "select id from tbl_template where isarticle <> 1 and siteid=? and (relatedcolumnid like '%," + columnId + ",%' " +
                            "or relatedcolumnid like '%(" + columnId + ",%' or relatedcolumnid like '%," + columnId + ")%' or " +
                            "relatedcolumnid like '%(" + columnId + ")%')";


                    pstmt = conn.prepareStatement(SQL_Select_Model);
                    pstmt.setInt(1, siteId);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        ids += rs.getString(1) + ",";
                    }
                    rs.close();
                    pstmt.close();

                    if (ids.length() > 0) {
                        ids = ids.substring(0, ids.length() - 1);
                        String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                        pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                } else if (auditFlag == 1)       //当前文章还未审核完毕
                {
                    /*String auditors = "";
                    pstmt = conn.prepareStatement(SQL_USER_ARTICLE_AUDIT);
                    pstmt.setInt(1, articleID);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        auditors = rs.getString("Auditor");
                    }
                    rs.close();
                    pstmt.close();

                    if (auditors == null) auditors = "";
                    auditors = auditors.trim() + auditor;
                    */

                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDITFLAG);
                    pstmt.setInt(1, auditFlag);
                    pstmt.setString(2, auditor);
                    pstmt.setInt(3, articleID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else                          //退稿
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDITFLAG);
                    pstmt.setInt(1, auditFlag);
                    pstmt.setString(2, "");
                    pstmt.setInt(3, articleID);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user failed.");
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
            e.printStackTrace();
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATE_AUDITFLAG_FOR_LEAVEWORD = "UPDATE TBL_Leaveword SET AuditFlag = ?,Auditor = ? WHERE ID = ?";

    //修改审核状态
    public void upateAuditFlagOfLeaveword(int auditFlag, String auditor, int lwID, int siteId, int formid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        System.out.println("auditFlag=" + auditFlag);
        System.out.println("auditor=" + auditor);
        System.out.println("lwID=" + lwID);

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (auditFlag == 0)            //当前文章全部审核完毕
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDITFLAG_FOR_LEAVEWORD);
                    pstmt.setInt(1, auditFlag);
                    pstmt.setString(2, "");
                    pstmt.setInt(3, lwID);
                    pstmt.executeUpdate();
                    pstmt.close();

                } else if (auditFlag == 1)       //当前文章还未审核完毕
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDITFLAG_FOR_LEAVEWORD);
                    pstmt.setInt(1, auditFlag);
                    pstmt.setString(2, auditor);
                    pstmt.setInt(3, lwID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else                          //退稿
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDITFLAG_FOR_LEAVEWORD);
                    pstmt.setInt(1, auditFlag);
                    pstmt.setString(2, "");
                    pstmt.setInt(3, lwID);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user failed.");
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
            e.printStackTrace();
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    //CMS文章的审核过程，包括退稿及审核通过
    public void Auditing(Audit audit, String act, int isAudit, int siteId, int columnId,int processofaudit) {
        int articleID = audit.getArticleID();
        String editor = audit.getEditor();
        String sign = audit.getSign();
        String comments = audit.getComments();
        int backtowho = audit.getStatus();
        String backto = audit.getBackTo();

        try {

            if (act.compareTo("add") == 0){    //增加审核
                if (isAudit == 1){    //通过审核
                    int stat = 0;                          //0表示信息过期
                    String userID = "[" + editor + "]";
                    boolean isAllAudited = queryArticle_isAllAudited(userID, articleID);  //加上本次审核后,判断是否全部都审核完
                    if (isAllAudited) {
                        //修改TBL_Article表,auditflag=已审完,清空auditor
                        upateAuditFlag(0, "", articleID, siteId, columnId);                //0表示通过，清空auditor
                        //寻找需要发布的作业，并将发布作业送入到发布队列中
                        addJobToPublishQueue(articleID, siteId, columnId);
                        //更新原来的审核信息为过期信息
                        updateAudit_Info("", articleID, "", 3,0);          //3表示将该篇文章所有审核信息全部置为过期
                    } else {
                        //获取下一级审核人信息
                        userID = getNextAuditor(editor, articleID,siteId);
                        //修改TBL_Article表中的auditor
                        upateAuditFlag(1, userID, articleID, siteId, columnId);            //1表示审核将继续,记住auditor
                        stat = 3;
                    }

                    //添加签名,建议等到审核信息表
                    audit = new Audit();
                    audit.setArticleID(articleID);
                    audit.setSign(sign);
                    audit.setComments(comments);
                    audit.setAudittype(0);
                    audit.setStatus(stat);
                    audit.setBackTo("");
                    Create_Article_Audit_Info(audit,0,0);
                } else {        //退稿
                    //更新原来的审核信息为过期信息
                    updateAudit_Info("", articleID, "", 3,0);             //3表示将该篇文章所有审核信息全部置为过期
                    int stat = 1;                                         //1表示退给编辑
                    if (backtowho == 1) stat = 2;                         //如是上一级审核人 2表示退给上级审核人
                    //修改auditflag=退稿,清除auditor
                    upateAuditFlag(2, "", articleID, siteId, columnId);   //2表示退稿,并清空auditor
                    //添加签名,建议等到审核信息表
                    audit = new Audit();
                    audit.setArticleID(articleID);
                    audit.setSign(sign);
                    audit.setComments(comments);
                    audit.setAudittype(0);
                    audit.setStatus(stat);
                    audit.setBackTo(backto);
                    Create_Article_Audit_Info(audit,processofaudit,stat);
                }
            } else if (act.compareTo("update") == 0)  //修改审核信息，只能由原来的通过到现在的退稿，除非只是修改comments
            {
                //通过审核
                if (isAudit == 1) {
                    //更新审核意见
                    updateAudit_Info(sign, articleID, comments, 1,0);    //如果继续通过，则只能修改comments  1表示修改某条审核信息
                } else {   //退稿
                    //更新原来的审核信息为过期信息
                    updateAudit_Info(sign, articleID, "", 2,0);
                    int stat = 1;                                         //1表示退给编辑
                    if (backtowho == 1) stat = 2;                         //如是上一级审核人 2表示退给上级审核人
                    //修改auditflag=退稿,清除auditor
                    upateAuditFlag(2, "", articleID, siteId, columnId);

                    //添加签名,建议等到审核信息表
                    audit = new Audit();
                    audit.setArticleID(articleID);
                    audit.setSign(sign);
                    audit.setComments(comments);
                    audit.setAudittype(0);
                    audit.setStatus(stat);
                    audit.setBackTo(backto);
                    Create_Article_Audit_Info(audit,processofaudit,stat);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //留言板的审核过程，包括退稿及审核通过
    public void AuditingLeaveword(Audit audit,String auditRule, String act, int isAudit, int siteId, int formId) {
        int lwID = audit.getArticleID();
        String editor = audit.getEditor();
        String sign = audit.getSign();
        String comments = audit.getComments();
        int backtowho = audit.getStatus();
        String backto = audit.getBackTo();

        try {
            if (act.compareTo("add") == 0) {    //增加审核
                if (isAudit == 1) {   //通过审核
                    int stat = 0;                          //0表示信息过期
                    String userID = "<" + editor + ">";
                    boolean isAllAudited = queryLeaveword_isAllAudited(userID,auditRule,lwID);  //加上本次审核后,判断是否全部都审核完
                    if (isAllAudited) {
                        //修改TBL_Leaveword表,auditflag=已审完,清空auditor
                        upateAuditFlagOfLeaveword(0, "", lwID, siteId, formId);                 //0表示通过，清空auditor
                        //更新原来的审核信息为过期信息
                        updateAudit_Info("", lwID, "", 3,1);                                      //3表示将该篇文章所有审核信息全部置为过期
                    } else {
                        //获取下一级审核人信息
                        userID = getNextAuditorForLeaveword(auditRule,userID, lwID,siteId);
                        //修改TBL_Leaveword表中的auditor
                        upateAuditFlagOfLeaveword(1, userID, lwID, siteId, formId);             //1表示审核将继续,记住auditor
                        stat = 3;
                    }

                    //添加签名,建议等到审核信息表
                    audit = new Audit();
                    audit.setArticleID(lwID);
                    audit.setSign(sign);
                    audit.setComments(comments);
                    audit.setAudittype(1);
                    audit.setStatus(stat);
                    audit.setBackTo("");
                    Create_Article_Audit_Info(audit,0,0);
                } else {        //退稿
                    //更新原来的审核信息为过期信息
                    updateAudit_Info("", lwID, "", 3,1);             //3表示将该篇文章所有审核信息全部置为过期
                    int stat = 1;                                         //1表示退给编辑
                    if (backtowho == 1) stat = 2;                         //如是上一级审核人 2表示退给上级审核人

                    //修改auditflag=退稿,清除auditor
                    upateAuditFlagOfLeaveword(2, "", lwID, siteId, formId);                   //2表示退稿,并清空auditor

                    //添加签名,建议等到审核信息表
                    audit = new Audit();
                    audit.setArticleID(lwID);
                    audit.setSign(sign);
                    audit.setAudittype(1);
                    audit.setComments(comments);
                    audit.setStatus(stat);
                    audit.setBackTo(backto);
                    Create_Article_Audit_Info(audit,0,stat);
                }
            } else if (act.compareTo("update") == 0)  //修改审核信息，只能由原来的通过到现在的退稿，除非只是修改comments
            {
                //通过审核
                if (isAudit == 1) {
                    //更新审核意见
                    updateAudit_Info(sign, lwID, comments, 1,1);    //如果继续通过，则只能修改comments  1表示修改某条审核信息
                } else    //退稿
                {
                    //更新原来的审核信息为过期信息
                    updateAudit_Info(sign, lwID, "", 2,1);
                    int stat = 1;                                         //1表示退给编辑
                    if (backtowho == 1) stat = 2;                         //如是上一级审核人 2表示退给上级审核人

                    //修改auditflag=退稿,清除auditor
                    upateAuditFlagOfLeaveword(2, "", lwID, siteId, formId);

                    //添加签名,建议等到审核信息表
                    audit = new Audit();
                    audit.setArticleID(lwID);
                    audit.setSign(sign);
                    audit.setAudittype(1);
                    audit.setComments(comments);
                    audit.setStatus(stat);
                    audit.setBackTo(backto);
                    Create_Article_Audit_Info(audit,0,stat);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询某文章的下一级审核人
    private String getNextAuditor(String userID, int articleID,int siteID) throws CmsException {
        String auditor = "";
        String audit_Rules = getColumnAuditRules(articleID);
        int type = getColumnAuditRulesType(articleID);
        if (audit_Rules != null) {
            if (type == 0)//按用户审核
            {
                int posi = audit_Rules.indexOf(userID);
                auditor = audit_Rules.substring(posi + " AND ".length()).trim();
                posi = auditor.indexOf(" ");
                if (posi > -1) auditor = auditor.substring(0, posi);
                //auditor = userID;
            } else {
                //按角色审核 获取下一个角色值
                IUserManager userMgr = UserPeer.getInstance();
                int deptid = userMgr.getDeptidByUser(userID,siteID);
                String current_user_role = "[" + userMgr.getRoleByUser(userID,siteID) + "]";
                int posi = audit_Rules.indexOf(current_user_role);
                String next_user_role = "";
                if (posi > -1) next_user_role = audit_Rules.substring(posi+current_user_role.length()).trim();
                posi = next_user_role.indexOf("AND");
                if (posi > -1) next_user_role = next_user_role.substring(posi + 3).trim();
                posi = next_user_role.indexOf(" ");
                if (posi > -1) next_user_role = next_user_role.substring(0,posi).trim();

                //获取下一个角色值对应的拥护ID
                if (next_user_role.startsWith("[部门领导]")) auditor= "[" + userMgr.getUserByRole("部门领导",deptid,siteID) + "]";          //获取部门领导用户ID
                if (next_user_role.startsWith("[主管领导]")) auditor= "[" + userMgr.getUserByRole("主管领导",deptid,siteID) + "]";          //获取主管领导用户ID
            }
        }

        return auditor;
    }

    //查询某留言的下一级审核人
    private String getNextAuditorForLeaveword(String audit_Rules,String userID, int articleID,int siteID) throws CmsException {
        String auditor = "";
        int type = 0;
        if (audit_Rules.indexOf("部门领导")>-1 || audit_Rules.indexOf("主管领导")>-1 ) type=1;

        if (audit_Rules != null) {
            System.out.println("audit_Rules=" + audit_Rules);
            if (type == 0)//按用户审核
            {
                System.out.println("userid=" + userID);
                int posi = audit_Rules.indexOf(userID);
                auditor = audit_Rules.substring(posi).trim();
                System.out.println("auditor=" + auditor);
                posi = auditor.indexOf(" ");
                if (posi > -1) auditor = auditor.substring(0, posi);
                //auditor = userID;
            } else {
                //按角色审核 获取下一个角色值
                IUserManager userMgr = UserPeer.getInstance();
                int deptid = userMgr.getDeptidByUser(userID,siteID);
                String current_user_role = "[" + userMgr.getRoleByUser(userID,siteID) + "]";
                int posi = audit_Rules.indexOf(current_user_role);
                String next_user_role = "";
                if (posi > -1) next_user_role = audit_Rules.substring(posi+current_user_role.length()).trim();
                posi = next_user_role.indexOf("AND");
                if (posi > -1) next_user_role = next_user_role.substring(posi + 3).trim();
                posi = next_user_role.indexOf(" ");
                if (posi > -1) next_user_role = next_user_role.substring(0,posi).trim();

                //获取下一个角色值对应的用户ID
                if (next_user_role.startsWith("[部门领导]")) auditor= "[" + userMgr.getUserByRole("部门领导",deptid,siteID) + "]";          //获取部门领导用户ID
                if (next_user_role.startsWith("[主管领导]")) auditor= "[" + userMgr.getUserByRole("主管领导",deptid,siteID) + "]";          //获取主管领导用户ID
            }
        }

        return auditor;
    }

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private void addJobToPublishQueue(int articleID, int siteID, int columnID) {
        List queueList = new ArrayList();
        com.bizwink.cms.publishx.Publish publish;
        String ids = "";
        SiteInfo siteinfo = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        IPublishQueueManager queueMgr = PublishQueuePeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        //修改引用该篇文章的站点需要发布的栏目模板的发布标志位
        try {
            siteinfo = siteMgr.getSiteInfo(siteID);
            queueList.clear();
            Article article = articleMgr.getArticle(articleID);
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            ids = "";

            //文章不需要审核，将需要发布的信息加入到信息发布队列
            if (article.getAuditFlag() == 0) {
                //修改与该篇文章相关联的 栏目模板 的发布标志位
                String SQL_Select_Model = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 and siteid=? and (relatedcolumnid like '%," + columnID + ",%' or relatedcolumnid like '%(" + columnID + ",%' or relatedcolumnid like '%," + columnID + ")%' or relatedcolumnid like '%(" + columnID + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                if (siteinfo.getSamsiteid() != 0)
                    pstmt.setInt(1, siteinfo.getSamsiteid());
                else
                    pstmt.setInt(1, siteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (cpool.getPublishWay() == 1) {
                        publish = new com.bizwink.cms.publishx.Publish();
                        publish.setSiteID(siteID);
                        publish.setTargetID(rs.getInt("id"));
                        publish.setColumnID(rs.getInt("columnid"));
                        publish.setTitle(rs.getString("chname"));
                        publish.setPriority(2);                      //栏目模板发布作业的优先级设置为2
                        publish.setPublishDate(article.getPublishTime());
                        if (rs.getInt(("isarticle")) == 0 || rs.getInt(("isarticle")) == 3)   //栏目模板和专题模板
                            publish.setObjectType(3);
                        else if (rs.getInt(("isarticle")) == 1)                              //首页模板
                            publish.setObjectType(2);
                        queueList.add(publish);
                    }
                    ids += rs.getString(1) + ",";
                }
                rs.close();
                pstmt.close();

                if (ids.length() > 0) {
                    ids = ids.substring(0, ids.length() - 1);
                    String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                    pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //将需要发布的文章和模板加入到发布队列
                if (cpool.getPublishWay() == 1 && article.getStatus() == 1 && article.getAuditFlag() == 0) {
                    //加入当前文章
                    publish = new com.bizwink.cms.publishx.Publish();
                    publish.setSiteID(siteID);
                    publish.setColumnID(columnID);
                    publish.setTargetID(articleID);
                    publish.setTitle(article.getMainTitle());
                    publish.setPriority(1);                      //文章发布作业的优先级设置为1
                    publish.setPublishDate(article.getPublishTime());
                    publish.setObjectType(1);
                    queueList.add(publish);

                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (com.bizwink.cms.publishx.Publish) queueList.get(i);
                        boolean existjob = queueMgr.existTheJobInQueue(siteID, columnID, publish.getTargetID(), publish.getObjectType());
                        if (existjob == false) {
                            pstmt.setInt(1, siteID);
                            pstmt.setInt(2, columnID);
                            pstmt.setInt(3, publish.getTargetID());
                            pstmt.setInt(4, publish.getObjectType());
                            pstmt.setInt(5, 1);
                            pstmt.setTimestamp(6, now);
                            pstmt.setTimestamp(7, publish.getPublishDate());
                            pstmt.setString(8, "");
                            pstmt.setString(9, publish.getTitle());
                            pstmt.setInt(10, publish.getPriority());
                            if (cpool.getType().equals("oracle")) {
                                pstmt.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                                pstmt.executeUpdate();
                            } else if (cpool.getType().equals("mssql")) {
                                pstmt.executeUpdate();
                            } else {
                                pstmt.executeUpdate();
                            }
                        }
                    }
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } finally {
            try {
                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final String SQL_CREATE_AUDIT_INFO_FOR_ORACLE = "INSERT INTO TBL_Article_Auditing_Info (ArticleID,Sign,Comments,messagetype," +
            "Status,BackTo,CreateDate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_INFO_FOR_MSSQL = "INSERT INTO TBL_Article_Auditing_Info (ArticleID,Sign,Comments,messagetype," +
            "Status,BackTo,CreateDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_AUDIT_INFO_FOR_MYSQL = "INSERT INTO TBL_Article_Auditing_Info (ArticleID,Sign,Comments,messagetype," +
            "Status,BackTo,CreateDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_ARTICLE_PROCESSOFAUDIT = "update tbl_article set processofaudit=? where id=?";

    //创建文章审核信息
    public void Create_Article_Audit_Info(Audit audit,int processofaudit,int backtowho) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                String sign = audit.getSign();
                if (sign != null) sign = StringUtil.gb2isoindb(sign);
                String comments = audit.getComments();
                if (comments != null) comments = StringUtil.gb2isoindb(comments);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_INFO_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_INFO_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_AUDIT_INFO_FOR_MYSQL);
                pstmt.setInt(1, audit.getArticleID());
                pstmt.setString(2, sign);
                pstmt.setString(3, comments);
                pstmt.setInt(4, audit.getAudittype());
                pstmt.setInt(5, audit.getStatus());
                pstmt.setString(6, audit.getBackTo());
                pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(8, sequnceMgr.nextID("ArticleAuditInfo"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
                pstmt.close();

                //修改文正的审核进度标识位
                pstmt = conn.prepareStatement(UPDATE_ARTICLE_PROCESSOFAUDIT);
                if (backtowho == 0)
                    pstmt.setInt(1,0);         //返回给文章作者
                else {                        //否则返回给前一级审核者
                    if (processofaudit <= 0)
                        processofaudit = 0;
                    else
                        processofaudit = processofaudit - 1;
                    pstmt.setInt(1,processofaudit);
                }
                pstmt.setInt(2, audit.getArticleID());
                pstmt.executeUpdate();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("数据库异常，不能创建审核信息。");
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

    private static final String SQL_QUERY_AUDIT_INFO = "SELECT ID FROM TBL_Article_Auditing_Info WHERE ArticleID = ? AND messagetype=? AND Sign = ? AND Status <> 0 ORDER BY CreateDate DESC";

    private static final String SQL_UPDATE_AUDIT_INFO1 = "UPDATE TBL_Article_Auditing_Info SET Comments = ? WHERE ID = ?";

    private static final String SQL_UPDATE_AUDIT_INFO2 = "UPDATE TBL_Article_Auditing_Info SET Status = 0 WHERE ID = ?";

    private static final String SQL_UPDATE_AUDIT_INFO3 = "UPDATE TBL_Article_Auditing_Info SET Status = 0 WHERE ArticleID = ? AND messagetype=?";

    //修改文章或留言的审核信息
    public void updateAudit_Info(String userID, int articleID, String comments, int flag,int messagetype) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                int ID = 0;
                pstmt = conn.prepareStatement(SQL_QUERY_AUDIT_INFO);
                pstmt.setInt(1, articleID);
                pstmt.setInt(2, messagetype);
                pstmt.setString(3, userID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    ID = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                if (flag == 1)    //更新审核建议
                {
                    if (ID > 0) {
                        if (comments != null) comments = StringUtil.gb2isoindb(comments);
                        pstmt = conn.prepareStatement(SQL_UPDATE_AUDIT_INFO1);
                        pstmt.setString(1, comments);
                        pstmt.setInt(2, ID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                } else if (flag == 2)    //更新审核信息为过期信息
                {
                    if (ID > 0) {
                        pstmt = conn.prepareStatement(SQL_UPDATE_AUDIT_INFO2);
                        pstmt.setInt(1, ID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                } else if (flag == 3)    //更新某文章所有审核信息为过期信息
                {
                    pstmt = conn.prepareStatement(SQL_UPDATE_AUDIT_INFO3);
                    pstmt.setInt(1, articleID);
                    pstmt.setInt(2, messagetype);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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

    //查询某文章是否已全部审核完毕
    public boolean queryArticle_isAllAudited(String userID, int articleID) throws CmsException {
        boolean query = false;
        String audit_Rules = getColumnAuditRules(articleID);
        int type = getColumnAuditRulesType(articleID);
        //用户角色 角色最大优先
        List rolesList = getUserRoles(userID.substring(1, userID.length() - 1));
        String roles = "";
        if (rolesList.size() > 0) {
            roles = "[" + (String) rolesList.get(0) + "]";
        }
        if (audit_Rules != null) {
            int i = audit_Rules.indexOf(userID);
            int j = userID.length();
            int k = audit_Rules.length();

            if (type == 0)//按用户审核
            {
                if (i != -1) {
                    if (i != -1) {
                        if (i == 0) {
                            if (k == j)
                                query = true;
                            else if (audit_Rules.substring(j, j + 4).compareTo(" OR ") == 0)
                                query = true;
                        } else {
                            if (i == k - j)
                                query = true;
                            else if (audit_Rules.substring(i + j, i + j + 4).compareTo(" OR ") == 0)
                                query = true;
                        }
                    }
                }
            } else {
                //按角色审核
                i = audit_Rules.indexOf(roles);
                j = roles.length();
                k = audit_Rules.length();
                if (i != -1) {
                    if (i != -1) {
                        if (i == 0) {
                            if (k == j)
                                query = true;
                            else if (audit_Rules.substring(j, j + 4).compareTo(" OR ") == 0)
                                query = true;
                        } else {
                            if (i == k - j)
                                query = true;
                            else if (audit_Rules.substring(i + j, i + j + 4).compareTo(" OR ") == 0)
                                query = true;
                        }
                    }
                }
            }
        }

        return query;
    }

    //查询用户留言信息是否已全部审核完毕
    public boolean queryLeaveword_isAllAudited(String userID, String audit_Rules,int lwID) throws CmsException {
        boolean query = false;
        System.out.println("userid=" + userID);
        int type = 0;
        if (audit_Rules.indexOf("部门领导")>-1 || audit_Rules.indexOf("主管领导")>-1 ) type=1;
        //用户角色 角色最大优先
        List rolesList = getUserRoles(userID.substring(1, userID.length() - 1));
        String roles = "";
        if (rolesList.size() > 0) {
            roles = "[" + (String) rolesList.get(0) + "]";
        }
        if (audit_Rules != null) {
            int i = audit_Rules.indexOf(userID);
            int j = userID.length();
            int k = audit_Rules.length();

            if (type == 0)//按用户审核
            {
                if (i != -1) {
                    if (i != -1) {
                        if (i == 0) {
                            if (k == j)
                                query = true;
                            else if (audit_Rules.substring(j, j + 4).compareTo(" OR ") == 0)
                                query = true;
                        } else {
                            if (i == k - j)
                                query = true;
                            else if (audit_Rules.substring(i + j, i + j + 4).compareTo(" OR ") == 0)
                                query = true;
                        }
                    }
                }
            } else {
                //按角色审核
                i = audit_Rules.indexOf(roles);
                j = roles.length();
                k = audit_Rules.length();
                if (i != -1) {
                    if (i != -1) {
                        if (i == 0) {
                            if (k == j)
                                query = true;
                            else if (audit_Rules.substring(j, j + 4).compareTo(" OR ") == 0)
                                query = true;
                        } else {
                            if (i == k - j)
                                query = true;
                            else if (audit_Rules.substring(i + j, i + j + 4).compareTo(" OR ") == 0)
                                query = true;
                        }
                    }
                }
            }
        }

        return query;
    }

    //修改用户密码
    private static final String SQL_UPDATE_PASSWORD = "UPDATE TBL_Members SET UserPWD = ? WHERE UserID = ?";

    public int updatePassword(String userID, String password) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 0;

        try {
            try {
                //加密密码
                password = Encrypt.md5(password.getBytes());

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_PASSWORD);
                pstmt.setString(1, password);
                pstmt.setString(2, userID);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user password failed.");
            } catch (Exception e) {
                errcode = -2;
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            errcode = -3;
            throw new CmsException("Database exception: can't rollback?");
        }

        return errcode;
    }

    private static final String SQL_GET_USERSCOUNT = "SELECT count(*) FROM TBL_Members WHERE ((UserID IN " +
            "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
            "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public int getUsersCount(String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String SQL_GET_USERSCOUNT_SEARCH = "SELECT count(*) FROM TBL_Members WHERE (((UserID IN (SELECT UserID FROM TBL_Members_Rights WHERE RightID " +
                "= 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)) and userid like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_GET_USERSCOUNT);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERSCOUNT_SEARCH);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static final String SQL_GET_USERSBY = "SELECT DISTINCT SiteID,UserID,NickName,Email,MPhone FROM TBL_Members WHERE ((UserID IN " +
            "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
            "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public List getUsers(int result, int startrow, String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user = null;
        int count = 0;

        String SQL_GET_USERS_SEARCH = "SELECT DISTINCT SiteID,UserID,NickName,Email,MPhone FROM TBL_Members WHERE (((UserID IN " +
                "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
                "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)) and userid like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            System.out.println("startrow==" + startrow);

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_GET_USERSBY);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    count= count + 1;
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loaduser(rs);
                    list.add(user);
                    count= count + 1;
                }
                System.out.println("count==" + count);
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERS_SEARCH);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loaduser(rs);
                    list.add(user);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static final String SQL_SITENAMECOUNT = "select count(*) from TBL_SiteInfo";

    //查询所有站点的webmaster用户及admin
    public int getSitenameCount(String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String SQL_GET_USERSCOUNT_SEARCH = "select count(*) from TBL_SiteInfo where sitename like '%" + searchstr + "%' ";


        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_SITENAMECOUNT);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERSCOUNT_SEARCH);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    // 站点查询
    private String SQL_SITENAME = "select * from TBL_SiteInfo ";

    public List getSitename(int result, int startrow, String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Register user = null;

        String SQL_GET_USERS_SEARCH = "select *from TBL_SiteInfo where sitename like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_SITENAME);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loadregister(rs);
                    list.add(user);
                }

            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERS_SEARCH);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loadregister(rs);
                    list.add(user);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;

    }

    private static final String SQL_GET_NICK_NAMESCOUNT = "SELECT count(DISTINCT SiteID) FROM TBL_Members WHERE ((UserID IN " +
            "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
            "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public int getNicknameCount(String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String SQL_GET_USERSCOUNT_SEARCH = "SELECT count(DISTINCT SiteID) FROM TBL_Members WHERE (((UserID IN " +
                "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
                "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)) and nickname like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_GET_NICK_NAMESCOUNT);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERSCOUNT_SEARCH);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }


    private static final String SQL_GET_NICKNAMESBY = "SELECT DISTINCT SiteID,UserID,NickName,Email,MPhone FROM TBL_Members WHERE ((UserID IN " +
            "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
            "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public List getNickname(int result, int startrow, String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        User user = null;

        String SQL_GET_USERS_SEARCH = "SELECT DISTINCT SiteID,UserID,NickName,Email,MPhone FROM TBL_Members WHERE (((UserID IN " +
                "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
                "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)) and nickname like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_GET_NICKNAMESBY);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loaduser(rs);
                    list.add(user);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERS_SEARCH);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loaduser(rs);
                    list.add(user);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //2008 8-21 wj
    private static final String SQL_GET_EMAILSCOUNT = "SELECT count(DISTINCT SiteID) FROM TBL_Members WHERE ((UserID IN " +
            "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
            "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public int getEmailCount(String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String SQL_GET_USERSCOUNT_SEARCH = "SELECT count(DISTINCT SiteID) FROM TBL_Members WHERE (((UserID IN " +
                "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
                "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)) and email like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_GET_EMAILSCOUNT);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERSCOUNT_SEARCH);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static final String SQL_GET_EMAILSBY = "SELECT DISTINCT SiteID,UserID,NickName,Email,MPhone FROM TBL_Members WHERE ((UserID IN " +
            "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
            "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)";

    //查询所有站点的webmaster用户及admin
    public List getEmail(int result, int startrow, String searchstr) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        User user = null;

        String SQL_GET_USERS_SEARCH = "SELECT DISTINCT SiteID,UserID,NickName,Email,MPhone FROM TBL_Members WHERE (((UserID IN " +
                "(SELECT UserID FROM TBL_Members_Rights WHERE RightID = 54) OR UserID IN (SELECT UserID FROM TBL_Group_Members " +
                "WHERE GroupID IN (SELECT GroupID FROM TBL_Group_Rights WHERE RightID = 54)))) OR (SiteID = -1)) and email like '%" + searchstr + "%'";

        try {
            conn = cpool.getConnection();

            if (searchstr.equals("")) {
                pstmt = conn.prepareStatement(SQL_GET_EMAILSBY);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loaduser(rs);
                    list.add(user);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_USERS_SEARCH);
                rs = pstmt.executeQuery();
                for (int i = 0; i < startrow; i++) {
                    rs.next();
                }
                for (int i = 0; i < result && rs.next(); i++) {
                    user = loaduser(rs);
                    list.add(user);
                }
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static final String SQL_GET_SITE = "SELECT * FROM tbl_members WHERE SiteID = ?";

    public User getUserOne(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        User register = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_SITE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            if (rs.next())
                register = loaduser(rs);

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return register;

    }

    Audit load(ResultSet rs) throws SQLException {
        Audit audit = new Audit();
        audit.setID(rs.getInt("ID"));
        audit.setColumnID(rs.getInt("ColumnID"));
        audit.setAuditRules(rs.getString("Column_Audit_Rule"));
        audit.setCreateDate(rs.getTimestamp("CreateDate"));
        return audit;
    }

    Audit load_audit_info(ResultSet rs) throws SQLException {
        Audit audit = new Audit();
        audit.setID(rs.getInt("ID"));
        audit.setArticleID(rs.getInt("ArticleID"));
        audit.setSign(rs.getString("Sign"));
        audit.setComments(rs.getString("Comments"));
        audit.setStatus(rs.getInt("Status"));
        audit.setBackTo(rs.getString("BackTo"));
        audit.setCreateDate(rs.getTimestamp("CreateDate"));
        return audit;
    }

    Audit load_article(ResultSet rs) throws SQLException {
        Audit audit = new Audit();
        audit.setID(rs.getInt("ID"));
        audit.setColumnID(rs.getInt("ColumnID"));
        audit.setMainTitle(rs.getString("MainTitle"));
        audit.setEditor(rs.getString("Editor"));
        return audit;
    }

    Audit load_articles(ResultSet rs) throws SQLException {
        Audit audit = new Audit();
        audit.setID(rs.getInt("ID"));
        audit.setColumnID(rs.getInt("ColumnID"));
        audit.setMainTitle(rs.getString("MainTitle"));
        audit.setEditor(rs.getString("Editor"));
        audit.setSign(rs.getString("Sign"));
        audit.setComments(rs.getString("Comments"));
        audit.setCreateDate(rs.getTimestamp("CreateDate"));
        return audit;
    }

    User loaduser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setID(rs.getString("UserID"));
        user.setNickName(StringUtil.iso2gb(rs.getString("NickName")));
        user.setEmail(rs.getString("Email"));
        user.setPhone(rs.getString("MPhone"));
        user.setSiteID(rs.getInt("SiteID"));

        return user;
    }

    public Register loadregister(ResultSet rs) throws CmsException {
        Register register = new Register();

        try {
            register.setSiteID(rs.getInt("SiteID"));
            register.setUserID(rs.getString("UserID"));
            register.setSiteName(rs.getString("SiteName"));
            //register.setEmail(rs.getString("Email"));
            register.setImagesDir(rs.getInt("ImagesDir"));
            register.setTCFlag(rs.getInt("tcFlag"));
            register.setPubFlag(rs.getInt("PubFlag"));
            //register.setExtName(rs.getString("ExtName"));
            register.setCreateDate(rs.getTimestamp("CreateDate"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return register;
    }

    //查找用户角色
    private List getUserRoles(String userid) {
        List list = new ArrayList();
        IUserManager userMgr = UserPeer.getInstance();
        list = userMgr.getMemberRolesList(userid);
        return list;
    }

    private static final String SQL_QUERY_COLUMN_RULES_TYPE = "SELECT audittype FROM TBL_Column_Auditing_Rules WHERE ColumnID = ?";

    //查询某文章所属栏目的审核规则
    private int getColumnAuditRulesType(int articleID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int type = 0;
        int columnID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_QUERY_ARTICLE_COLUMN);
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                columnID = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_QUERY_COLUMN_RULES_TYPE);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                type = rs.getInt(1);
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
        return type;
    }
}