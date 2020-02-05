package com.bizwink.cms.security;

import java.sql.Date;
import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.xml.*;
import com.bizwink.util.SpringInit;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

public class AuthPeer implements IAuthManager {
    PoolServer cpool;

    public AuthPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IAuthManager getInstance() {
        return CmsServer.getInstance().getFactory().getAuthManager();
    }

    private static final String SQL_DelAllNoActionUsers_FOR_ORACLE = "delete from bbs_online where ROUND(TO_NUMBER(sysdate - LASTACTIVETIME)* 24 * 60) > ?";

    private static final String SQL_DelAllNoActionUsers_FOR_MSSQL = "delete from bbs_online where DATEDIFF(n, LASTACTIVETIME, { fn NOW() }) > ?";

    //从用户登录状态表中删除用户登录的记录信息
    public int removeAllNoActionUsers(int dtime) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            //ApplicationContext appContext = SpringInit.getApplicationContext();
            //DataSource dataSource =(DataSource)appContext.getBean("myDataSource");
            conn = cpool.getConnection();
            //conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equals("oracle"))
                pstmt = conn.prepareStatement(SQL_DelAllNoActionUsers_FOR_ORACLE);
            else
                pstmt = conn.prepareStatement(SQL_DelAllNoActionUsers_FOR_MSSQL);
            pstmt.setInt(1, dtime);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return  -10;
        } finally {
            try {
                if (pstmt!=null) pstmt.close();
                try {
                    conn.close();
                } catch (SQLException exp){}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private static final String SQL_DelLoginStatusInfo = "delete from bbs_online where  username = ?";

    //从用户登录状态表中删除用户登录的记录信息
    public int removeUserLoginInfo(int siteid,String userid, String userip) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_DelLoginStatusInfo);
            pstmt.setString(1,userid);
            pstmt.executeUpdate();
            pstmt.close();

            //创建用户登录LOG
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
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, 0);
            pstmt.setInt(3, 0);
            pstmt.setString(4, userid);
            pstmt.setInt(5, 5);                               //5表示用户退出
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(7, "用户退出");
            pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            if (cpool.getType().equals("oracle")) {
                pstmt.setLong(9, sequenceMgr.getSequenceNum("Log"));
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
            e.printStackTrace();
            return  -10;
        } finally {
            try {
                if (pstmt!=null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    //从用户登录状态表中获取用户的登录状态信息
    private static final String SQL_GetLoginStatusInfo = "select loginstatus from bbs_online where username = ?";

    public int getUserLoginStatus(String userid, String userip) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int status = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GetLoginStatusInfo);
            pstmt.setString(1,userid);
            rs = pstmt.executeQuery();
            if (rs.next()) status = rs.getInt("loginstatus");
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return  -10;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt!=null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    private static final String SQL_UpdateLoginStatusInfo = "update bbs_online set lastactivetime=? where  username = ?";

    //修改用户的最后登录时间等信息
    public int updateUserLoginInfo(String userid, String userip) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UpdateLoginStatusInfo);
            pstmt.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            pstmt.setString(2,userid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return  -10;
        } finally {
            try {
                if (pstmt!=null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    private static final String SQL_GET_Members_Rights = "select distinct rightid from tbl_group_members a,tbl_group_rights b where a.groupid = b.groupid " +
            "and a.userid = ? union (select distinct rightid from tbl_members_rights where userid = ?)";

    private static final String SQL_AUTHORIZE ="SELECT userid,Userpwd,Nickname,SiteID,dflag,emailaccount,emailpasswd,trypassnum,trypasstime,orgid,companyid,deptid,company,department,unlogin,unused FROM TBL_Members WHERE userid = ? and unlogin=1 and unused=1 AND ((SiteID " +
            "IN (SELECT SiteID FROM TBL_SiteInfo WHERE BindFlag = 1)) OR (UserID = 'admin'))";

    private static final String SQL_UPDATE_TRYPASSINFO = "update TBL_Members set trypassnum=?,trypasstime=? where userid = ?";

    private static final String SQL_Site ="SELECT sitename,imagesdir,cssjsdir,tcflag,config,samsiteid,sitevalid,sitetype,sharetemplatenum,copycolumn,becopycolumn,pusharticle,movearticle FROM " +
            "TBL_Siteinfo WHERE siteid = ?";

    private static final String SQL_GetMemberRoles = "select rolename from tbl_member_roles where siteid=? and userid=?";

    private static final String SQL_UserLoginStatus = "insert into bbs_online(id,lyhid,username,logintime,loginnum,loginstatus,lastactivetime,ipaddress) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_ORACLE = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MSSQL = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MYSQL = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public Auth getAuth(String userid, String password,String userip) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        int siteid = -2;
        int imgflag = 0;
        int cssjsdir = 0;
        int publishflag = 0;
        int listShow = 0;
        int tagSite = 0;
        int copyColumn = 0;
        int beCopyColumn = 0;
        int pushArticle = 0;
        int moveArticle = 0;
        int samsiteid = 0;
        int sitetype = 1;
        int sitevalid = 0;
        int errcode = 0;
        int dflag= 0;
        int orgid = 0;
        int companyid = 0;
        int deptid = 0;
        String company = "";
        String department = "";
        int unlogin = 1;
        int unused = 1;
        String sitename = "";
        String configInfo = "";
        String nickname = "";
        int sharetemplatenum = 0;
        String emailaccount = "";
        String emailpasswd = "";
        int trypassnum = 0;
        Timestamp trypasstime = null;
        String getPassword = null;
        PermissionSet permissionSet = null;
        RolesSet roleSet = null;

        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        if (userid == null || password == null) {
            errcode = -1;
            throw new UnauthedException("username or password is null");
        }

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(SQL_AUTHORIZE);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                getPassword = rs.getString("userpwd");
                dflag=rs.getInt("dflag");
                siteid = rs.getInt("siteid");
                emailaccount = rs.getString("emailaccount");
                emailpasswd = rs.getString("emailpasswd");
                userid = rs.getString("userid");
                nickname = rs.getString("nickname");
                trypassnum = rs.getInt("trypassnum");
                trypasstime = rs.getTimestamp("trypasstime");
                orgid = rs.getInt("orgid");
                companyid = rs.getInt("companyid");
                deptid = rs.getInt("deptid");
                company = rs.getString("company");
                department = rs.getString("department");
                System.out.println("auth department==" + department);
                unlogin = rs.getInt("unlogin");
                unused = rs.getInt("unused");
            } else {
                errcode = -3;
                throw new UnauthedException("用户不存在");
            }
            rs.close();
            pstmt.close();

            if(unused == 0) errcode = -3;              //用户被删除，但是用户信息在数据库依然存在
            if(unlogin == 0) errcode = -6;              //用户被临时禁止登录

            if (trypassnum < 5) {
                if (!password.equalsIgnoreCase(getPassword)||dflag!=0) {
                    trypassnum = trypassnum + 1;
                    pstmt = conn.prepareStatement(SQL_UPDATE_TRYPASSINFO);
                    pstmt.setInt(1,trypassnum);
                    pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(3,userid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                    errcode = -4;
                    throw new UnauthedException("输入用户口令错");
                } else {
                    //用户在5次内输入了正确口令，清除错误登录次数设置
                    pstmt = conn.prepareStatement(SQL_UPDATE_TRYPASSINFO);
                    pstmt.setInt(1,0);
                    pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(3,userid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                }
            } else {
                Timestamp nowtime = new Timestamp(System.currentTimeMillis());
                long interval = (nowtime.getTime() - trypasstime.getTime())/1000;      //两个时间相差的秒数
                if (interval<1800) {
                    errcode = -5;
                    throw new UnauthedException("用户被锁定半个小时");
                } else {
                    pstmt = conn.prepareStatement(SQL_UPDATE_TRYPASSINFO);
                    pstmt.setInt(1,0);
                    pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(3,userid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                }
            }

            //记录用户已经登录的状态
            if (errcode == 0) {
                Timestamp logintime = new Timestamp(System.currentTimeMillis());
                pstmt = conn.prepareStatement(SQL_UserLoginStatus);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt.setInt(1, sequenceMgr.getSequenceNum("Userreg"));
                else
                    pstmt.setInt(1, sequenceMgr.nextID("Userreg"));
                pstmt.setInt(2, 0);                                                   //联谊会ID设置为0
                pstmt.setString(3, userid);                                           //登录用户的userid
                pstmt.setTimestamp(4, logintime);    //用户当前的登录时间
                pstmt.setInt(5,1);                                                   //用户第一次成功登录
                pstmt.setInt(6,1);                                                   //1表示用户已经登录，不能在其他机器再次登录 0表示用户没有登录
                pstmt.setTimestamp(7, logintime);                                    //登录用户的最后活动时间
                pstmt.setString(8,userip);
                pstmt.executeUpdate();
                pstmt.close();

                //创建用户登录LOG
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
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setString(4, userid);
                pstmt.setInt(5, 4);                               //4表示用户登录
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, "用户登录");
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequenceMgr.getSequenceNum("Log"));
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
            }

            if (!userid.equalsIgnoreCase("admin")) {
                pstmt = conn.prepareStatement(SQL_Site);
                pstmt.setInt(1, siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    sitename = rs.getString("sitename");
                    imgflag = rs.getInt("imagesdir");
                    cssjsdir = rs.getInt("cssjsdir");
                    publishflag = rs.getInt("tcflag");
                    samsiteid = rs.getInt("samsiteid");
                    sharetemplatenum = rs.getInt("sharetemplatenum");
                    configInfo = rs.getString("config");
                    copyColumn = rs.getInt("copycolumn");
                    beCopyColumn = rs.getInt("beCopyColumn");
                    pushArticle = rs.getInt("pushArticle");
                    moveArticle = rs.getInt("moveArticle");
                    sitevalid = rs.getInt("sitevalid");
                    sitetype = rs.getInt("sitetype");
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_GET_Members_Rights);
                pstmt.setString(1, userid);
                pstmt.setString(2, userid);
                rs = pstmt.executeQuery();
                permissionSet = new PermissionSet();
                int rightid;

                while (rs.next()) {
                    Permission permission = new Permission();
                    rightid = rs.getInt("rightid");
                    permission.setRightID(rightid);
                    List cList = getUserColumnID(userid, rightid);
                    permission.setColumnListOnRight(cList);
                    permissionSet.add(permission);
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_GetMemberRoles);
                pstmt.setInt(1, siteid);
                pstmt.setString(2, userid);
                rs = pstmt.executeQuery();
                roleSet = new RolesSet();
                while (rs.next()) {
                    Role role = new Role();
                    role.setRolename(rs.getString("rolename"));
                    roleSet.add(role);
                }
                rs.close();
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errcode = -5;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt!=null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
            }
        }

        sitename = StringUtil.replace(sitename, ".", "_");
        if (errcode == 0)
            return new Auth(
                    company,
                    department,
                    emailaccount,
                    emailpasswd,
                    userid,
                    nickname,
                    sitename,
                    siteid,
                    samsiteid,
                    sitetype,
                    sharetemplatenum,
                    imgflag,
                    cssjsdir,
                    publishflag,
                    listShow,
                    errcode,
                    tagSite,
                    copyColumn,
                    beCopyColumn,
                    pushArticle,
                    moveArticle,
                    orgid,
                    companyid,
                    deptid,
                    permissionSet,
                    roleSet);
        else
            return null;
    }


    public Auth getSjsAuth(String userid, String password) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int siteid = -2;
        int imgflag = 0;
        int cssjsdir = 0;
        int publishflag = 0;
        int listShow = 0;
        int tagSite = 0;
        int copyColumn = 0;
        int beCopyColumn = 0;
        int pushArticle = 0;
        int moveArticle = 0;
        int samsiteid = 0;
        int samsitetype = 1;
        int errcode = 0;
        int orgid = 0;
        int companyid = 0;
        int deptid=0;
        String sitename = "";
        String configInfo = "";
        int sharetemplatenum = 0;
        String emailaccount = "";
        String emailpasswd = "";
        String company = "";
        String departcode = "";
        String department = "";
        String nickname = "";
        PermissionSet permissionSet = null;
        RolesSet roleSet = null;

        if ((userid == null) || (password == null)) {
            errcode = -1;
            throw new UnauthedException("username or password is null");
        }
        try
        {
            conn = this.cpool.getConnection();
            try {
                password = Encrypt.md5(password.getBytes());
            }
            catch (Exception e) {
                errcode = -2;
                e.printStackTrace();
            }

            pstmt = conn.prepareStatement("SELECT userid,Userpwd,Nickname,SiteID,dflag,emailaccount,emailpasswd,company,departcode,orgid,companyid,deptid FROM TBL_Members WHERE useridcode = ? AND ((SiteID IN (SELECT SiteID FROM TBL_SiteInfo WHERE BindFlag = 1)) OR (useridcode = 'b7f6f4b4e0b01e428936ccddbdf4eaae'))");
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String getPassword = rs.getString("userpwd");
                int dflag = rs.getInt("dflag");
                siteid = rs.getInt("siteid");
                emailaccount = rs.getString("emailaccount");
                emailpasswd = rs.getString("emailpasswd");
                userid = rs.getString("userid");
                company = rs.getString("company");
                departcode = rs.getString("departcode");
                nickname = rs.getString("Nickname");
                deptid = rs.getInt("deptid");
                companyid = rs.getInt("companyid");
                orgid = rs.getInt("orgid");
            } else {
                errcode = -3;
                throw new UnauthedException(" no user name");
            }
            rs.close();
            pstmt.close();
            if (departcode != null) {
                pstmt = conn.prepareStatement("select unit from tbl_department where ename=?");
                pstmt.setString(1, departcode);
                rs = pstmt.executeQuery();
                if (rs.next()) department = rs.getString("unit");
                rs.close();
                pstmt.close();

                if ((department == null) || (department == "")) {
                    pstmt = conn.prepareStatement("select cname from tbl_department where ename=?");
                    pstmt.setString(1, departcode);
                    rs = pstmt.executeQuery();
                    if (rs.next()) department = rs.getString("cname");
                    rs.close();
                    pstmt.close();
                }
            }

            if (!userid.equalsIgnoreCase("admin")) {
                pstmt = conn.prepareStatement("SELECT sitename,imagesdir,cssjsdir,tcflag,config,samsiteid,sharetemplatenum,copycolumn,becopycolumn,pusharticle,movearticle FROM TBL_Siteinfo WHERE siteid = ?");
                pstmt.setInt(1, siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    sitename = rs.getString("sitename");
                    imgflag = rs.getInt("imagesdir");

                    cssjsdir = 1;
                    publishflag = rs.getInt("tcflag");
                    samsiteid = rs.getInt("samsiteid");
                    sharetemplatenum = rs.getInt("sharetemplatenum");
                    configInfo = rs.getString("config");
                    copyColumn = rs.getInt("copycolumn");
                    beCopyColumn = rs.getInt("beCopyColumn");
                    pushArticle = rs.getInt("pushArticle");
                    moveArticle = rs.getInt("moveArticle");
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement("SELECT sitevalid FROM TBL_Siteinfo WHERE siteid = ?");
                pstmt.setInt(1, samsiteid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    samsitetype = rs.getInt("sitevalid");
                }
                rs.close();
                pstmt.close();

                if ((configInfo != null) && (configInfo.length() > 0)) {
                    XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + configInfo);
                    listShow = Integer.parseInt(properties.getProperty("PublishListShow"));
                    tagSite = Integer.parseInt(properties.getProperty("GlobalTag"));
                }

                pstmt = conn.prepareStatement("select distinct rightid from tbl_group_members a,tbl_group_rights b where a.groupid = b.groupid and a.userid = ? union (select distinct rightid from tbl_members_rights where userid = ?)");
                pstmt.setString(1, userid);
                pstmt.setString(2, userid);
                rs = pstmt.executeQuery();
                permissionSet = new PermissionSet();

                while (rs.next()) {
                    Permission permission = new Permission();
                    int rightid = rs.getInt("rightid");
                    permission.setRightID(rightid);
                    List cList = getUserColumnID(userid, rightid);
                    permission.setColumnListOnRight(cList);
                    permissionSet.add(permission);
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement("select rolename from tbl_member_roles where siteid=? and userid=?");
                pstmt.setInt(1, siteid);
                pstmt.setString(2, userid);
                rs = pstmt.executeQuery();
                roleSet = new RolesSet();
                while (rs.next()) {
                    Role role = new Role();
                    role.setRolename(rs.getString("rolename"));
                    roleSet.add(role);
                }
                rs.close();
                pstmt.close();
            }
        } catch (Exception e) {
            errcode = -5;
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) this.cpool.freeConnection(conn);
            }
            catch (Exception e) { e.printStackTrace(); }

        }

        sitename = StringUtil.replace(sitename, ".", "_");
        if (errcode == 0) {
            return new Auth(
                    company,
                    department,
                    emailaccount,
                    emailpasswd,
                    userid,
                    nickname,
                    sitename,
                    siteid,
                    samsiteid,
                    samsitetype,
                    sharetemplatenum,
                    imgflag,
                    cssjsdir,
                    publishflag,
                    listShow,
                    errcode,
                    tagSite,
                    copyColumn,
                    beCopyColumn,
                    pushArticle,
                    moveArticle,
                    orgid,
                    companyid,
                    deptid,
                    permissionSet,
                    roleSet);
        }

        return null;
    }

    private static final String GET_Columns_For_User = "select distinct columnid from tbl_group_members a, tbl_group_rights b where " +
            "a.groupid = b.groupid and a.userid = ? and b.rightid = ? and columnid > 0 union " +
            "select distinct columnid from tbl_members_rights where userid = ? and rightid = ? and columnid > 0";

    private static final String GET_Columns = "select * from tbl_column where id = ?";

    private List getUserColumnID(String userid, int rightid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        PreparedStatement cpstmt=null;
        ResultSet rs=null, crs=null;

        int columnID;
        Column column = null;
        List cl = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_Columns_For_User);
            pstmt.setString(1, userid);
            pstmt.setInt(2, rightid);
            pstmt.setString(3, userid);
            pstmt.setInt(4, rightid);
            rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    columnID = rs.getInt("columnID");
                    cpstmt = conn.prepareStatement(GET_Columns);
                    cpstmt.setInt(1, columnID);
                    crs = cpstmt.executeQuery();
                    if (crs.next()) {
                        column = load(crs);
                    }
                    cl.add(column);
                    crs.close();
                    cpstmt.close();
                }
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cl;
    }

    private static final String SQL_GETModelS = "SELECT count(id) FROM TBL_Template where columnid in (select id from tbl_column where siteid = ?)";

    public int getTemplateNum(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int num=0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelS);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    public AuthForPerson getAuthForPerson(String userid, String password) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        int siteid = -2;

        if (userid == null || password == null) {
            throw new UnauthedException("username or password is null");
        }

        try {
            conn = cpool.getConnection();

            try {
                password = Encrypt.md5(password.getBytes());
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            pstmt = conn.prepareStatement(SQL_AUTHORIZE);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String getPassword = rs.getString("userpwd");
                int dflag=rs.getInt("dflag");
                siteid = rs.getInt("siteid");
                if (!password.equals(getPassword)||dflag!=0)
                    throw new UnauthedException(" password is not right");
            } else {
                throw new UnauthedException(" no user name");
            }
            rs.close();
            pstmt.close();
        }
        catch (UnauthedException e) {
            throw e;
        }
        catch (SQLException e) {
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

        return new AuthForPerson(userid,siteid);
    }

    public AuthForWeb getAuthForWeb(String usern, String passw,int siteid) throws UnauthedException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AuthForWeb ug= null;
        int userlevel = 0;
        int id = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id,memberid,password,grade from tbl_userinfo where memberid= ? and password = ? and siteid=? and lockflag=0");
            pstmt.setString(1, usern.trim());
            pstmt.setString(2, passw.trim());
            pstmt.setInt(3,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String getPassword = rs.getString("userpwd");
                if (!passw.equals(getPassword)) throw new UnauthedException(" password is not right");
                id = rs.getInt("id");
                userlevel = rs.getInt("grade");
            } else {
                throw new UnauthedException(" no user name");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new AuthForWeb(id,userlevel,usern,siteid);
    }

    private Column load(ResultSet rs) throws SQLException {
        Column column = new Column();

        column.setID(rs.getInt("ID"));
        column.setSiteID(rs.getInt("siteid"));
        column.setDirName(rs.getString("dirname"));
        column.setOrderID(rs.getInt("orderid"));
        column.setParentID(rs.getInt("parentid"));
        column.setEName(rs.getString("ename"));
        column.setCName(rs.getString("cname"));
        column.setIsAudited(rs.getInt("IsAudited"));
        column.setHasArticleModel(rs.getInt("hasArticleModel"));

        return column;
    }
}
