package com.bizwink.cms.security;

import java.math.BigDecimal;
import java.security.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import java.sql.*;
import java.util.regex.Pattern;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.po.Companyinfo;
import com.bizwink.po.Organization;
import com.bizwink.webapps.leaveword.authorizedform;

public class UserPeer implements IUserManager {
    PoolServer cpool;

    public UserPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IUserManager getInstance() {
        return CmsServer.getInstance().getFactory().getUserManager();
    }

    private static final String SQL_EXISTUSER = "SELECT UserID FROM TBL_Members WHERE UserID = ?";

    public boolean existUser(String userID, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean existFlag = false;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_EXISTUSER);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                existFlag = true;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
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
        return existFlag;
    }

    private static final String SQL_GETUSER = "SELECT * FROM tbl_members WHERE userID=? and siteid=?";

    public User getUser(String userID, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        User user = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSER);
            pstmt.setString(1, userID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = load(rs);
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
        return user;
    }

    private static final String SQL_GETUSER_BY_UID = "SELECT * FROM tbl_members WHERE ID=? and siteid=?";

    public User getUserByUID(int uid, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        User user = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSER_BY_UID);
            pstmt.setInt(1, uid);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = load(rs);
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
        return user;
    }

    private static final String SQL_GET_USER = "SELECT * FROM tbl_members WHERE userID=?";

    public User getUser(String userID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        User user = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USER);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = load(rs);
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
        return user;
    }

    private static final String SQL_GET_USERBYROLE = "SELECT userid FROM tbl_member_roles WHERE rolename=? and siteid=?";

    public String getUserByRole(String rolename,int deptid,int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String username = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USERBYROLE);
            pstmt.setString(1, rolename);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("userid");
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
        return username;
    }

    private static final String SQL_GET_ROLEBYUSER = "SELECT rolename FROM tbl_member_roles WHERE userid=? and siteid=?";

    public String getRoleByUser(String userid,int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String rolename = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ROLEBYUSER);
            pstmt.setString(1, userid);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rolename = rs.getString("rolename");
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
        return rolename;
    }

    private static final String SQL_GET_DEPTID_BYUSER = "SELECT department FROM tbl_members WHERE userid=? and siteid=?";

    public int getDeptidByUser(String userid,int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int deptid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_DEPTID_BYUSER);
            pstmt.setString(1, userid);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                deptid = Integer.parseInt(rs.getString("department"));
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
        return deptid;
    }

    private static final String SQL_GETPASSWORD = "SELECT userpwd FROM tbl_members WHERE userID=? and siteid=?";

    public String getPasswd(String userID, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String pass = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETPASSWORD);
            pstmt.setString(1, userID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pass = rs.getString("userpwd");
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

        return pass;
    }

    private static final String SQL_CREATEUSER_FOR_ORACLE =
            "INSERT INTO tbl_members(userid,mmuserid,userpwd,nickname,siteid,createarticles,editarticles,deletearticles,company,department,departmentarticlestype,departmentarticlesids,emailaccount,emailpasswd,orgid,companyid,deptid,createdate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATEUSER_FOR_MSSQL =
            "INSERT INTO tbl_members(userid,mmuserid,userpwd,nickname,siteid,createarticles,editarticles,deletearticles,company,department,departmentarticlestype,departmentarticlesids,emailaccount,emailpasswd,orgid,companyid,deptid,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATEUSER_FOR_MYSQL =
            "INSERT INTO tbl_members(userid,mmuserid,userpwd,nickname,siteid,createarticles,editarticles,deletearticles,company,department,departmentarticlestype,departmentarticlesids,emailaccount,emailpasswd,orgid,companyid,deptid,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UserCols = "INSERT INTO tbl_members_rights (userid,columnid,rightid) VALUES (?, ?, ?)";

    private static final String SQL_MEMBER_ROLES_FOR_ORACLE = "insert into tbl_member_roles(siteid,userid,rolename,rolelevel,deptid,id) values(?,?,?,?,?,?)";

    private static final String SQL_MEMBER_ROLES_FOR_MSSQL = "insert into tbl_member_roles(siteid,userid,rolename,rolelevel,deptid) values(?,?,?,?,?)";

    private static final String SQL_MEMBER_ROLES_FOR_MYSQL = "insert into tbl_member_roles(siteid,userid,rolename,rolelevel,deptid) values(?,?,?,?,?)";

    public int create(User user,String opuser) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null,pstmt1=null;
        ResultSet rs = null;
        int retcode = 0;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            try {
                //加密密码
                String password = Encrypt.md5(user.getPassword().getBytes());

                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (cpool.getType().equals("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATEUSER_FOR_ORACLE);
                else if (cpool.getType().equals("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATEUSER_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATEUSER_FOR_MYSQL);
                String nickname = StringUtil.gb2isoindb(user.getNickName());
                pstmt.setString(1, user.getID());
                pstmt.setString(2,Encrypt.md5(user.getID().getBytes()));
                pstmt.setString(3, password);
                pstmt.setString(4, nickname);
                pstmt.setInt(5, user.getSiteid());
                pstmt.setInt(6, 0);
                pstmt.setInt(7, 0);
                pstmt.setInt(8, 0);
                pstmt.setString(9,user.getCompany());
                pstmt.setString(10, user.getDepartment());
                pstmt.setInt(11,user.getDepartmentarticlestype());
                pstmt.setString(12,user.getDepartmentarticlesids());
                pstmt.setString(13,user.getEmailaccount());
                pstmt.setString(14,user.getEmailpasswd());
                pstmt.setInt(15, user.getOrgid());
                pstmt.setInt(16,user.getCompanyid());
                pstmt.setInt(17,user.getDeptid());
                pstmt.setTimestamp(18, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle"))
                    pstmt.setInt(19,sequnceMgr.getSequenceNum("Userreg"));
                pstmt.executeUpdate();
                pstmt.close();

                List rightList = user.getRightList();
                if (rightList != null) {
                    pstmt = conn.prepareStatement(SQL_UserCols);
                    for (int i = 0; i < rightList.size(); i++) {
                        int rightID = Integer.parseInt((String) rightList.get(i));
                        pstmt.setString(1, user.getID());
                        pstmt.setInt(2, 0);
                        pstmt.setInt(3, rightID);
                        pstmt.executeUpdate();
                    }
                    pstmt.close();
                }

                //用户角色
                List rolesList = user.getRolesList();
                if (rolesList != null) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_MEMBER_ROLES_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_MEMBER_ROLES_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_MEMBER_ROLES_FOR_MYSQL);
                    for (int i = 0; i < rolesList.size(); i++) {
                        String rolesname = (String) rolesList.get(i);
                        pstmt.setInt(1, user.getSiteid());
                        pstmt.setString(2, user.getID());
                        pstmt.setString(3, rolesname);
                        pstmt.setInt(4, 0);
                        pstmt.setInt(5, Integer.parseInt(user.getDepartment()));
                        if (cpool.getType().equals("oracle")) {
                            int rolesid = sequnceMgr.getSequenceNum("Roles");
                            pstmt.setInt(6, rolesid);
                            pstmt.executeUpdate();

                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();

                        } else {
                            pstmt.executeUpdate();

                        }
                    }
                    pstmt.close();
                }

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, user.getSiteID());
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, opuser);
                pstmt.setInt(5, 8);               //6删除用户 7修改用户 8创建用户 9禁止或者许可用户登录
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, "增加用户:" + user.getUserID());
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (Exception e) {
                retcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
            }
            finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            retcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return retcode;
    }

    private final static String SQL_INSERT_RIGHTS = "insert into tbl_members_rights (userid,columnID,rightid) values(?, ?, ?)";

    private final static String SQL_UPDATE_USERINFO = "UPDATE TBL_Members SET Nickname = ?,email=?,phone=?,mphone=?,company=?,department = ?,emailaccount = ?,emailpasswd = ?,departmentarticlestype = ?,departmentarticlesids = ?,orgid=?,companyid=?,deptid=? WHERE UserID = ?";

    private final static String SQL_DELETE_MEMBER_ROLES = "delete from tbl_member_roles where userid = ?";

    public int update(User user,String opuser) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int retcode = 0;
        IRightsManager rightMgr = RightsPeer.getInstance();

        try {
            try {
                //计算需要删除的权限
                String rightIDs = "";
                List rightList = user.getRightList();
                for (int i = 0; i < rightList.size(); i++)
                    rightIDs = rightIDs + rightList.get(i) + ",";

                String SQL_DELETE_RIGHTS = "delete from tbl_members_rights where UserID = ?";
                if (rightIDs.length() > 0) {
                    rightIDs = rightIDs.substring(0, rightIDs.length() - 1);
                    SQL_DELETE_RIGHTS += " and RightID NOT IN (" + rightIDs + ")";
                }

                //计算需要增加的权限
                List insertList = new ArrayList();
                if (rightList.size() > 0) {
                    rightIDs = ",";
                    List oldRightList = rightMgr.getGrantedUserRights(user.getID());
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
                pstmt.setString(1, user.getID());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_INSERT_RIGHTS);
                for (int i = 0; i < insertList.size(); i++) {
                    int rightID = Integer.parseInt((String) insertList.get(i));
                    pstmt.setString(1, user.getID());
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, rightID);
                    pstmt.executeUpdate();
                }
                pstmt.close();

                //删除用户角色
                pstmt = conn.prepareStatement(SQL_DELETE_MEMBER_ROLES);
                pstmt.setString(1,user.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //重新设置用户角色
                boolean lybrole_exist = false;
                boolean lyb_dept_role_exist = false;
                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                List rolesList = user.getRolesList();
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_MEMBER_ROLES_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_MEMBER_ROLES_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_MEMBER_ROLES_FOR_MYSQL);
                for (int i = 0; i < rolesList.size(); i++) {
                    String rolesname = (String) rolesList.get(i);
                    rolesname = rolesname.trim();
                    pstmt.setInt(1,user.getSiteid());
                    pstmt.setString(2,user.getUserID());
                    pstmt.setString(3,rolesname);
                    pstmt.setInt(4,0);
                    if (user.getDepartment() != null)
                        pstmt.setInt(5,Integer.parseInt(user.getDepartment()));
                    else
                        pstmt.setInt(5,0);
                    if (cpool.getType().equals("oracle")) {
                        int rolesid = sequnceMgr.getSequenceNum("Roles");
                        pstmt.setInt(6, rolesid);
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_UPDATE_USERINFO);
                pstmt.setString(1,user.getNickName());
                pstmt.setString(2,user.getEmail());
                pstmt.setString(3,user.getPhone());
                pstmt.setString(4,user.getMobilePhone());
                pstmt.setString(5,user.getCompany());
                pstmt.setString(6,user.getDepartment());
                pstmt.setString(7,user.getEmailaccount());
                pstmt.setString(8,user.getEmailpasswd());
                pstmt.setInt(9,user.getDepartmentarticlestype());
                pstmt.setString(10,user.getDepartmentarticlesids());
                pstmt.setInt(11,user.getOrgid());
                pstmt.setInt(12,user.getCompanyid());
                pstmt.setInt(13,user.getDeptid());
                pstmt.setString(14, user.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, user.getSiteID());
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, opuser);
                pstmt.setInt(5, 7);                         //6删除用户 7修改用户 8创建用户 9禁止或者许可用户登录
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, "修改用户:" + user.getUserID());
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (Exception e) {
                retcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user failed.");
            }
            finally {
                try {
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            retcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return retcode;
    }

    private static final String SQL_UPDATEPASSWORD = "UPDATE tbl_members set userpwd=? WHERE userID=? and siteid=?";

    public void updatePassword(String userID, int siteid, String password,String opuser) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                //加密密码
                password = Encrypt.md5(password.getBytes());

                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATEPASSWORD);
                pstmt.setString(1, password);
                pstmt.setString(2, userID);
                pstmt.setInt(3, siteid);
                pstmt.executeUpdate();
                pstmt.close();

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);
                ISequenceManager sequnceMgr = SequencePeer.getInstance();

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, opuser);
                pstmt.setInt(5, 1);
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, "修改用户" + userID  + "的口令");
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user password failed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
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

    //private static final String SQL_DELETEUSER = "DELETE FROM TBL_Members WHERE UserID = ?";

    private static final String SQL_DELETEUSER = "UPDATE TBL_Members set unused=0 WHERE UserID = ?";

    //private static final String SQL_DELETE_USER_FROMGROUP = "DELETE FROM TBL_Group_Members WHERE UserID = ?";

    //private static final String SQL_DELETE_USER_RIGHTS = "DELETE FROM TBL_Members_Rights WHERE UserID = ?";

    private static final String SQL_CREATE_LOG_FOR_ORACLE = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MSSQL = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MYSQL = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public int remove(User user,String opuser) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int retcode = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_DELETEUSER);
                pstmt.setString(1, user.getID());
                pstmt.executeUpdate();
                pstmt.close();

                /*pstmt = conn.prepareStatement(SQL_DELETE_USER_FROMGROUP);
                pstmt.setString(1, user.getID());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_DELETE_USER_RIGHTS);
                pstmt.setString(1, user.getID());
                pstmt.executeUpdate();
                pstmt.close();*/

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, user.getSiteID());
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, opuser);
                pstmt.setInt(5, 6);                     //6删除用户 7修改用户 8创建用户 9禁止或者许可用户登录
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, "删除用户:" + user.getUserID());
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (NullPointerException e) {
                retcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user failed.");
            }
            finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            retcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return retcode;
    }

    //批量删除用户
    public int batchDelUsers(int siteid,String uids,String opuser) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int retcode = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement("UPDATE TBL_Members set unused=0 WHERE siteid=? and id in (" + uids + ")");
                pstmt.setInt(1, siteid);
                pstmt.executeUpdate();
                pstmt.close();

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);
                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                String[] uid = uids.split(",");
                for(int ii=0; ii<uid.length; ii++) {
                    String userid = null;
                    pstmt = conn.prepareStatement("select userid from TBL_Members where id=?");
                    pstmt.setInt(1,Integer.parseInt(uid[ii]));
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) userid = rs.getString("userid");
                    rs.close();
                    pstmt.close();

                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                    pstmt.setInt(1, siteid);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, opuser);
                    pstmt.setInt(5, 6);          //6删除用户 7修改用户 8创建用户 9禁止或者许可用户登录
                    pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(7, "删除用户:" + userid);
                    pstmt.setDate(8, new Date(year, month, date));
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
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
            } catch (NullPointerException e) {
                retcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user failed.");
            }
            finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            retcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return retcode;
    }

    public int unloginUser(int siteid,String userid,String opuser,int unloginval) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int retcode = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (unloginval == 1)
                    unloginval = 0;
                else if (unloginval == 0)
                    unloginval = 1;
                pstmt = conn.prepareStatement("UPDATE TBL_Members set unlogin=? WHERE siteid=? and userid=?");
                pstmt.setInt(1, unloginval);
                pstmt.setInt(2,siteid);
                pstmt.setString(3,userid);
                pstmt.executeUpdate();
                pstmt.close();

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, opuser);
                pstmt.setInt(5, 9);                     //6删除用户 7修改用户 8创建用户 9禁止或者许可用户登录
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, "禁止或许可用户登录:" + userid);
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (NullPointerException e) {
                retcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user failed.");
            }
            finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            retcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return retcode;
    }

    public int batchUnloginUsers(int siteid,String uids,String opuser,String loginflag) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int retcode = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);
                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                String[] uid = uids.split(",");
                String[] loginflagvals = loginflag.split(",");
                for(int ii=0; ii<uid.length; ii++) {
                    int uidval = Integer.parseInt(uid[ii]);
                    //修改unligin的标示值，当前值为1则修改为0，当前值为0则修改值为1
                    int loginflagval = Integer.parseInt(loginflagvals[ii]);
                    if (loginflagval == 1)
                        loginflagval = 0;
                    else if (loginflagval == 0)
                        loginflagval = 1;
                    pstmt = conn.prepareStatement("UPDATE TBL_Members set unlogin=? WHERE siteid=? and id=?");
                    pstmt.setInt(1, loginflagval);
                    pstmt.setInt(2,siteid);
                    pstmt.setInt(3,uidval);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //创建LOG
                    String userid = null;
                    pstmt = conn.prepareStatement("select userid from TBL_Members where id=?");
                    pstmt.setInt(1,uidval);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) userid = rs.getString("userid");
                    rs.close();
                    pstmt.close();

                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                    pstmt.setInt(1, siteid);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, opuser);
                    pstmt.setInt(5, 9);            //6删除用户 7修改用户 8创建用户 9禁止或者许可用户登录
                    pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(7, "禁止或许可用户登录:" + userid);
                    pstmt.setDate(8, new Date(year, month, date));
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
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
            } catch (NullPointerException e) {
                retcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: delete user failed.");
            }
            finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            retcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return retcode;
    }


    private static final String SQL_GETUSERCOUNT = "SELECT count(userid) FROM tbl_members ";

    private static final String SQL_GETUSERCOUNT1 = "SELECT count(userid) FROM tbl_members  where siteid=?";

    public int getUserCount(int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            if (siteid != -1) {
                pstmt = conn.prepareStatement(SQL_GETUSERCOUNT1);
                pstmt.setInt(1, siteid);
            } else {
                pstmt = conn.prepareStatement(SQL_GETUSERCOUNT);
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user count failed.");
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    private static final String SQL_GETUSERS = "SELECT * FROM tbl_members ";

    private static final String SQL_GETUSERS1 = "SELECT * FROM tbl_members where usertype<5 and siteid=? order by createdate desc";

    public List getUsers(int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user=null;

        try {
            conn = cpool.getConnection();
            if (siteid == -1) {
                pstmt = conn.prepareStatement(SQL_GETUSERS);
            } else {
                pstmt = conn.prepareStatement(SQL_GETUSERS1);
                pstmt.setInt(1, siteid);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = load(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List getUsers(int startIndex, int numResults, int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user=null;

        try {
            conn = cpool.getConnection();
            if (siteid == -1) {
                pstmt = conn.prepareStatement(SQL_GETUSERS);
            } else {
                pstmt = conn.prepareStatement(SQL_GETUSERS1);
                pstmt.setInt(1, siteid);
            }
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    user = load(rs);
                    list.add(user);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static final String SQL_GETUSERS_BY_ORGID = "SELECT * FROM tbl_members where siteid=? and orgid=? and usertype=6";

    //SITEID：用户站点ID
    //ORGID：用户所在组织的ID
    public List getUsersByOrgid(int siteid,int orgid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSERS_BY_ORGID);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,orgid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = load(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static final String SQL_GETUSERS_BY_COMPANYID = "SELECT * FROM tbl_members where siteid=? and companyid=? and usertype=6";

    public List<User> getUsersByCompanyid(int siteid,int companyid)  throws CmsException{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSERS_BY_COMPANYID);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,companyid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = load(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static final String SQL_GETUSERS_BY_DEPTID = "SELECT * FROM tbl_members where siteid=? and deptid=? and usertype=6";

    public List<User> getUsersByDeptid(int siteid,int deptid)  throws CmsException{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSERS_BY_DEPTID);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,deptid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = load(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static final String SQL_GETGROUPS = "SELECT groupid FROM TBL_Group_Members WHERE userid = ?";

    public GroupSet getUserGroups(String userID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        GroupSet groupSet = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGROUPS);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            groupSet = new GroupSet();
            while (rs.next()) {
                Group group = new Group();
                group.setGroupID(rs.getInt(1));
                groupSet.add(group);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return groupSet;
    }

    private static final String SQL_DELETEGROUPS = "DELETE FROM TBL_Group_Members where UserID = ?";
    private static final String SQL_INSERTGROUPS = "INSERT INTO TBL_Group_Members (UserID, GroupID) VALUES(?, ?)";

    public void updateUserGroups(String userID, GroupSet groups)
            throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_DELETEGROUPS);
                conn.setAutoCommit(false);
                pstmt.setString(1, userID);
                pstmt.executeUpdate();
                pstmt.close();

                Iterator iter = groups.elements();
                while (iter.hasNext()) {
                    pstmt = conn.prepareStatement(SQL_INSERTGROUPS);
                    Group group = (Group) iter.next();

                    pstmt.setString(1, userID);
                    pstmt.setInt(2, group.getGroupID());

                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update permission failed.");
            }
            finally {
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

    private static final String SQL_GETALLGROUPS = "SELECT GroupID, GroupName, GroupDesc FROM tbl_group WHERE SiteID = ?";

    public GroupSet getAllGroups(int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        GroupSet groupSet = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETALLGROUPS);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            groupSet = new GroupSet();
            while (rs.next()) {
                Group group = new Group();
                group.setGroupID(rs.getInt(1));
                group.setGroupName(rs.getString(2));
                group.setGroupDesc(rs.getString(3));
                groupSet.add(group);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return groupSet;
    }

    User load(ResultSet rs) throws SQLException {
        User user = new User();
        user.setID(rs.getString("userid"));
        user.setUid(rs.getInt("id"));
        user.setUserID(rs.getString("userid"));
        user.setNickName(StringUtil.iso2gb(rs.getString("nickname")));
        user.setSiteid(rs.getInt("siteid"));
        user.setPhone(rs.getString("phone"));
        user.setMobilePhone(rs.getString("mphone"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setCompany(rs.getString("company"));
        user.setMyimage(rs.getString("myimage"));
        user.setDepartment(rs.getString("department"));
        user.setEmailpasswd(rs.getString("emailpasswd"));
        user.setEmailaccount(rs.getString("emailaccount"));
        user.setDepartmentarticlestype(rs.getInt("departmentarticlestype"));
        user.setDepartmentarticlesids(rs.getString("departmentarticlesids"));
        user.setTrypassnum(rs.getInt("trypassnum"));
        user.setTrypasstime(rs.getTimestamp("trypasstime"));
        user.setOrgid(rs.getInt("orgid"));
        user.setCompanyid(rs.getInt("companyid"));
        user.setDeptid(rs.getInt("deptid"));
        return user;
    }

    private static final String SQL_MODIFY_USERINFO = "update  tbl_members set nickname =?,mphone=?,address=?,email=? where userid = ?";

    public void updateUserInfo(User user) throws CmsException {
        //System.out.println("userid = " + user.getID());
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_MODIFY_USERINFO);
            pstmt.setString(1, user.getNickName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getID());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");

        } finally {
            try {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final String SQL_MODIFY_USER_HEADIMG = "update  tbl_members set myimage =? where userid = ?";

    public int updateUserHeadImg(int siteid,String imgfile,String userid,String opuser) throws CmsException {
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_MODIFY_USER_HEADIMG);
            pstmt.setString(1, imgfile);
            pstmt.setString(2, userid);
            pstmt.executeUpdate();
            pstmt.close();

            //创建LOG
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR) - 1900;
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);
            ISequenceManager sequnceMgr = SequencePeer.getInstance();

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, 0);
            pstmt.setString(4, opuser);
            pstmt.setInt(5, 1);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(7, "修改用户" + userid  + "的头像");
            pstmt.setDate(8, new Date(year, month, date));
            if (cpool.getType().equals("oracle")) {
                pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                pstmt.executeUpdate();
                pstmt.close();
            }else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        } finally {
            try {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return errcode;
    }

    private static final String SQL_MODIFY_USER_TYPE = "update  tbl_members set usertype =? where userid = ?";

    public int updateUserType(int siteid,int usertype,String userid,String opuser) throws CmsException {
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_MODIFY_USER_TYPE);
            pstmt.setInt(1, usertype);
            pstmt.setString(2, userid);
            pstmt.executeUpdate();
            pstmt.close();

            //创建LOG
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR) - 1900;
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);
            ISequenceManager sequnceMgr = SequencePeer.getInstance();

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, 0);
            pstmt.setString(4, opuser);
            pstmt.setInt(5, 1);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(7, "修改用户" + userid  + "的类型");
            pstmt.setDate(8, new Date(year, month, date));
            if (cpool.getType().equals("oracle")) {
                pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                pstmt.executeUpdate();
                pstmt.close();
            }else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();
            }

            conn.commit();
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        } finally {
            try {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return errcode;
    }

    //增加部门信息
    private static final String SQL_DEPARTMENT_FOR_ORACLE =
            "INSERT INTO tbl_department (cname, ename, telephone,manager,vicemanager,leader,siteid,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DEPARTMENT_FOR_MSSQL =
            "INSERT INTO tbl_department (cname, ename, telephone,manager,vicemanager,leader,siteid) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DEPARTMENT_FOR_MYSQL =
            "INSERT INTO tbl_department (cname, ename, telephone,manager,vicemanager,leader,siteid) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public void create(Department dept) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int deptID = 0;

        try {
            try {
                String cname = dept.getCname();
                if (cname != null) cname = StringUtil.gb2isoindb(cname);
                String manager = dept.getManager();
                if (manager != null) manager = StringUtil.gb2isoindb(manager);
                String vicemanager = dept.getVicemanager();
                if (vicemanager != null) vicemanager = StringUtil.gb2isoindb(vicemanager);
                String leader = dept.getLeader();
                if (leader != null) leader = StringUtil.gb2isoindb(leader);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_DEPARTMENT_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_DEPARTMENT_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_DEPARTMENT_FOR_MYSQL);

                pstmt.setString(1, cname);
                pstmt.setString(2, dept.getEname());
                pstmt.setString(3, dept.getTelephone());
                pstmt.setString(4, dept.getManager());
                pstmt.setString(5, dept.getVicemanager());
                pstmt.setString(6, dept.getLeader());
                pstmt.setInt(7, dept.getSiteid());
                if (cpool.getType().equals("oracle")) {
                    deptID = sequnceMgr.getSequenceNum("Department");
                    pstmt.setInt(8, deptID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create Group failed.");
            }
            finally {
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

    private static final String SQL_CREATEUSERBYCA = "INSERT INTO tbl_members(userid,userpwd,nickname,siteid,department,useridcode,departcode) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public void createByca(User user) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null,pstmt1=null;
        ResultSet rs = null;

        try {
            try {
                //加密密码
                String password = Encrypt.md5("123456".getBytes());

                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CREATEUSERBYCA);

                //String nickname = StringUtil.gb2isoindb(user.getNickName());
                pstmt.setString(1, user.getID());
                pstmt.setString(2, Encrypt.md5("123456".getBytes()));
                pstmt.setString(3, user.getID());
                pstmt.setInt(4, 40);
                pstmt.setString(5, user.getDepartment());
                pstmt.setString(6, user.getUserIdCode());
                pstmt.setString(7, user.getDepartCode());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
            }
            finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }


    private static final String UPDATE_DEPARTMENT = "UPDATE tbl_department set cname=?, ename=?, telephone=?,manager=?,vicemanager=?,leader=? WHERE id = ?";

    public void update(Department dept) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        String cname = dept.getCname();
        if (cname != null) cname = StringUtil.gb2isoindb(cname);
        String manager = dept.getManager();
        if (manager != null) manager = StringUtil.gb2isoindb(manager);
        String vicemanager = dept.getVicemanager();
        if (vicemanager != null) vicemanager = StringUtil.gb2isoindb(vicemanager);
        String leader = dept.getLeader();
        if (leader != null) leader = StringUtil.gb2isoindb(leader);

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(UPDATE_DEPARTMENT);
            pstmt.setString(1, cname);
            pstmt.setString(2, dept.getEname());
            pstmt.setString(3, dept.getTelephone());
            pstmt.setString(4, dept.getManager());
            pstmt.setString(5, dept.getVicemanager());
            pstmt.setString(6, dept.getLeader());
            pstmt.setInt(7, dept.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        } finally {
            try {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final String SQL_GETDEPARTMENTS = "SELECT * FROM tbl_department where siteid=?";

    public List getDepartments(int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Department dept;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETDEPARTMENTS);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                dept = loadDept(rs);
                list.add(dept);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }
    public List getUserInfoByDepartmentIds(String ids){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_members where department in ("+ids+")");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                user = load(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int deleteDepartment(String departCode) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_department where ename = ?");
            pstmt.setString(1,departCode);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(SQLException e)
        {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
            {
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    // 删除部门信息
    public int deleteDepartment(int id)
    {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_department where id = ?");
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(SQLException e)
        {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
            {
                cpool.freeConnection(conn);
            }
        }
        return code;
    }
    //get one department info
    //get one department info
    public Department getOneDepartmentInfoById(int id)
    {
        Department dept = null;
        Companyinfo companyinfo = null;
        Organization org = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_organization where id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                org = loadOrg(rs);
            }
            rs.close();
            pstmt.close();

            if (org != null) {
                if (org.getCOTYPE().intValue() == 0) {        //0表示是部门信息
                    pstmt = conn.prepareStatement("select * from tbl_department where orgid = ?");
                    pstmt.setInt(1,org.getID().intValue());
                    rs = pstmt.executeQuery();
                    if (rs.next()){
                        dept = loadDept(rs);
                    }
                    rs.close();
                    pstmt.close();
                } else {                                      //1表示是公司信息
                    pstmt = conn.prepareStatement("select * from tbl_companyinfo where orgid = ?");
                    pstmt.setInt(1,org.getID().intValue());
                    rs = pstmt.executeQuery();
                    if (rs.next()){
                        dept = new Department();
                        dept.setCname(rs.getString("companyname"));
                        dept.setId(rs.getInt("id"));
                        dept.setSiteid(rs.getInt("siteid"));
                    }
                    rs.close();
                    pstmt.close();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        } finally{
            if(conn!=null){
                cpool.freeConnection(conn);
            }
        }

        return dept;
    }

    public List getDepartments(String ids)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs;

        List list = new ArrayList();
        Department dept;

        try {
            conn = cpool.getConnection();
            if (ids != null) {
                if (!ids.isEmpty())
                    pstmt = conn.prepareStatement("select * from tbl_department where id in("+ids+")");
                else
                    pstmt = conn.prepareStatement("select * from tbl_department");
            } else {
                pstmt = conn.prepareStatement("select * from tbl_department");
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                dept = loadDept(rs);
                list.add(dept);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }
    public List getRemainDepartments(String ids,int siteID)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Department dept=null;

        try {
            conn = cpool.getConnection();
            if (ids!=null)
                if (!ids.isEmpty())
                    pstmt = conn.prepareStatement("select * from tbl_department where id not in("+ids+") and siteid = "+siteID);
                else
                    pstmt = conn.prepareStatement("select * from tbl_department where siteid = "+siteID);
            else
                pstmt = conn.prepareStatement("select * from tbl_department where siteid = "+siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                dept = loadDept(rs);
                list.add(dept);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    Department loadDept(ResultSet rs) throws SQLException {
        Department dept = new Department();
        dept.setId(rs.getInt("id"));
        dept.setCname(rs.getString("cname"));
        dept.setEname(rs.getString("ename"));
        dept.setTelephone(rs.getString("telephone"));
        dept.setManager(rs.getString("manager"));
        dept.setVicemanager(rs.getString("vicemanager"));
        dept.setLeader(rs.getString("leader"));
        return dept;
    }

    private static final String SQL_MANAGED_LEAVEWORD_BYUSERID = "select a.id,a.siteid,a.userid,b.nickname,a.lwid,a.lwname,a.lwrole from tbl_member_authorized_resouce a,tbl_members b " +
            "where a.siteid=? and a.lwid=? and a.userid=b.userid and b.department=? and a.lwrole=?";

    public List getUsersBYDepartment(String deptid,int siteID,int lwid,String rolename)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_MANAGED_LEAVEWORD_BYUSERID);
            pstmt.setInt(1,siteID);
            pstmt.setInt(2,lwid);
            pstmt.setString(3,deptid);
            pstmt.setString(4,rolename);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getString("userid"));
                user.setNickName(rs.getString("nickname"));
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static final String SQL_GET_EMPLOYEES_BYDEPTID = "select userid,nickname,department,siteid from tbl_members " +
            "where siteid=? and department=?";

    public List getEmployeesBYDepartmentID(int deptid,int siteID)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        User user = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_EMPLOYEES_BYDEPTID);
            pstmt.setInt(1,siteID);
            pstmt.setInt(2,deptid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getString("userid"));
                user.setNickName(rs.getString("nickname"));
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    //获得用户角色
    private static final String GET_MEMBER_ROLES = "select * from tbl_member_roles where userid = ? order by id desc";
    public List getMemberRolesList(String userid)
    {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_MEMBER_ROLES);
            pstmt.setString(1,userid);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                list.add(StringUtil.gb2iso4View(rs.getString("rolename")));
            }
            rs.close();
            pstmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getWebUsers(int siteid) throws CmsException
    {
        Connection conn = null;

        List list = new ArrayList();
        try
        {
            conn = this.cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT id,memberid,username,createdate FROM tbl_userinfo where siteid=? order by createdate desc");
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getString("memberid"));
                user.setNickName(rs.getString("username"));
                list.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    this.cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List getWebRoles() throws CmsException
    {
        Connection conn = null;

        List list = new ArrayList();
        try
        {
            conn = this.cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM tbl_role order by roleid asc");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("roleid"));
                role.setRolename(rs.getString("rolename"));
                role.setUserid(rs.getString("rolecat"));
                list.add(role);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    this.cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public User getByIduser(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        User user = new User();
        try {
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement("select username from tbl_userinfo where memberid=?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user.setNickName(rs.getString("username"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                this.cpool.freeConnection(conn);
            }
        }
        return user;
    }

    public Role getByIdrole(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        Role role = new Role();
        try {
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement("select rolename from tbl_role where rolecat=?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                role.setRolename(rs.getString("rolename"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                this.cpool.freeConnection(conn);
            }
        }
        return role;
    }

    public String getUserDepartmentName(String userid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String deptname = null;

        try {
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement("select unit from tbl_members t,tbl_department td where t.userid=? and t.departcode = td.ename");
            pstmt.setString(1, userid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) deptname = rs.getString("unit");
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                this.cpool.freeConnection(conn);
            }
        }
        return deptname;
    }


    private static final String SQL_CREATESPUSER = "INSERT INTO TBL_SPINFO" +
            "(ID,EMAIL,PASSWD,CREATEDATE,LOGINTIME,UPDATETIME,USERNAME,PHONE,MOBILE,yyzzfb,zzjgdm,swdjz,frsfz,khxkz)" +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public void createSP(User user) throws CmsException{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int spid = 0;
        try{
            //????????
            String password = Encrypt.md5(user.getPassword().getBytes());
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_CREATESPUSER);
            spid = sequnceMgr.getSequenceNum("SPINFOID");
            pstmt.setInt(1,spid);
            pstmt.setString(2,user.getEmail());
            pstmt.setString(3,password);
            pstmt.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
            //pstmt.setString(7,user.getUsername());
            pstmt.setString(8,user.getPhone());
            pstmt.setString(9,user.getMobilePhone());
            //pstmt.setString(10,user.getYyzhfb());
            //pstmt.setString(11,user.getZzjgdm());
            //pstmt.setString(12,user.getSwdjz());
            //pstmt.setString(13,user.getFrsfz());
            //pstmt.setString(14,user.getKhxkz());
            pstmt.executeUpdate();
            pstmt.close();


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                this.cpool.freeConnection(conn);
            }
        }
    }
    private static final String SQL_GETSPUSER = "SELECT ID,EMAIL,USERNAME,PHONE,MOBILE,yyzzfb,zzjgdm,swdjz,frsfz," +
            "khxkz,CREATEDATE,LOGINTIME,UPDATETIME FROM TBL_SPINFO ORDER BY CREATEDATE DESC";
    public List getSPUsers() throws CmsException{
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        User user;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSPUSER);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setID(String.valueOf(rs.getInt("ID")));
                user.setEmail(rs.getString("EMAIL"));
                //user.setUsername(rs.getString("USERNAME"));
                user.setPhone(rs.getString("PHONE"));
                user.setMobilePhone(rs.getString("MOBILE"));
                //user.setYyzhfb(rs.getString("yyzzfb"));
                //user.setZzjgdm(rs.getString("zzjgdm"));
                //user.setSwdjz(rs.getString("swdjz"));
                //user.setFrsfz(rs.getString("frsfz"));
                //user.setKhxkz(rs.getString("khxkz"));

                list.add(user);
            }
            rs.close();
            pstmt.close();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                this.cpool.freeConnection(conn);
            }
        }

        return list;
    }
    private static final String SQL_DELETE_SPUSER = "DELETE FROM TBL_SPINFO WHERE ID = ?";
    public void removesp(int id) throws CmsException,SQLException{
        Connection conn = null;
        PreparedStatement pstmt;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_DELETE_SPUSER);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
            conn.rollback();
        }finally {
            if(conn != null){
                this.cpool.freeConnection(conn);
            }
        }
    }

    //??????????
    private static final String SQL_UPDATE_SPPASSWORD = "UPDATE TBL_SPINFO SET PASSWD = ? WHERE ID = ?";

    public void updateSPPassword(String userID, String password) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                //????????
                password = Encrypt.md5(password.getBytes());

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_SPPASSWORD);
                pstmt.setString(1, password);
                pstmt.setInt(2, Integer.parseInt(userID));
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user password failed.");
            } catch (Exception e) {
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_EXISTSPUSER = "SELECT email FROM TBL_SPINFO WHERE email = ?";

    public boolean existSPUser(String email) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean existFlag = false;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_EXISTSPUSER);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                existFlag = true;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
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
        return existFlag;
    }

    //CREATEDATE,LOGINTIME,UPDATETIME,
    private static final String SQL_LOGINSP = "SELECT id,EMAIL,PASSWD,USERNAME,PHONE,MOBILE,yyzzfb,zzjgdm,swdjz,frsfz,khxkz  FROM TBL_SPINFO WHERE email = ? and PASSWD = ?";
    public User getLoginSp(String email, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        User user = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_LOGINSP);
            pstmt.setString(1, email);
            pstmt.setString(2,pwd);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setEmail(rs.getString("email"));
                user.setID(rs.getString("id"));
                user.setPassword(rs.getString("passwd"));
                //user.setUsername(rs.getString("username"));
                user.setPhone(rs.getString("phone"));
                user.setMobilePhone(rs.getString("mobile"));
                //user.setYyzhfb(rs.getString("yyzzfb"));
                //user.setZzjgdm(rs.getString("zzjgdm"));
                //user.setSwdjz(rs.getString("swdjz"));
                //user.setFrsfz(rs.getString("frsfz"));
                //user.setKhxkz(rs.getString("khxkz"));
            }
            rs.close();
            pstmt.close();
            pstmt = conn.prepareStatement("SELECT siteid,sitename from TBL_Siteinfo");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setSiteID(rs.getInt(1));
                //user.setUsername(rs.getString(2));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
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



        return user;
    }

    public boolean updatePwd(int spid, String pwd) {
        boolean bol = false;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update TBL_SPINFO set PASSWD = ? where id = ?");
            pstmt.setString(1,pwd);
            pstmt.setInt(2,spid);
            int i = pstmt.executeUpdate();
            if (i > 0) {
                bol = true;
            }
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
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
        return bol;
    }

    public void update_departbyUK(Department dept) throws CmsException {
        Connection conn = null;

        String cname = dept.getCname();
        if (cname != null) cname = StringUtil.gb2isoindb(cname);
        try {
            conn = this.cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE tbl_department set cname=?  WHERE ename=?");
            pstmt.setString(1, cname);
            pstmt.setString(2, dept.getEname());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        } finally {
            try {
                if (conn != null)
                    this.cpool.freeConnection(conn);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update_membersDepart(String ename, String useridcode) throws CmsException {
        Connection conn = null;
        try
        {
            conn = this.cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update tbl_members m set m.department=(select d.id from tbl_department d where d.ename=?) where m.useridcode=?");
            pstmt.setString(1, ename);
            pstmt.setString(2, useridcode);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");
        } finally {
            try {
                if (conn != null)
                    this.cpool.freeConnection(conn);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Department getOneDepartmentInfoByCode(String departCode) {
        Department dept = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_department where ename = ?");
            pstmt.setString(1, departCode);
            rs = pstmt.executeQuery();
            while(rs.next()){
                dept = loadDept(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally{
            if(conn!=null){
                cpool.freeConnection(conn);
            }
        }
        return dept;
    }

    private static final String SQL_GET_USER_BYIDCODE = "SELECT * FROM tbl_members WHERE useridcode=?";

    public User getUserByIdCode(String userIDcode) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        User user = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USER_BYIDCODE);
            pstmt.setString(1, userIDcode);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = load(rs);
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
        return user;
    }

    private static final String SQL_MODIFY_USERINFO_BYCA = "update  tbl_members set userid=?, nickname =?,department=?,departcode=? where useridcode = ?";

    public void updateUserInfoByca(User user) throws CmsException {
        //System.out.println("userid = " + user.getID());
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_MODIFY_USERINFO_BYCA);
            pstmt.setString(1, user.getID());
            pstmt.setString(2, user.getID());
            pstmt.setString(3, user.getDepartment());
            pstmt.setString(4, user.getDepartCode());
            pstmt.setString(5, user.getUserIdCode());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CmsException("Database exception: get user permission failed.");

        } finally {
            try {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int deleteUser(String userIdcode){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_members where useridcode = ?");
            pstmt.setString(1,userIdcode);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(SQLException e)
        {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
            {
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    Organization loadOrg(ResultSet rs) throws Exception {
        Organization organization = new Organization();
        try {
            organization.setID(BigDecimal.valueOf(rs.getInt("id")));
            organization.setPARENT(BigDecimal.valueOf(rs.getInt("parent")));
            organization.setCOTYPE(BigDecimal.valueOf(rs.getInt("cotype")));
            organization.setCUSTOMERID(BigDecimal.valueOf(rs.getInt("customerid")));
            organization.setNAME(rs.getString("name"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return organization;
    }

}
