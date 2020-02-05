package com.bizwink.webapps.register;

import com.bizwink.util.OlympicMembers;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UregisterPeer implements IUregisterManager {
    PoolServer cpool;

    public UregisterPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IUregisterManager getInstance() {
        return (IUregisterManager) CmsServer.getInstance().getFactory().getUregisterManager();
    }

    //获取随机字符串
    public String getRandKeys( int intLength ) throws UregisterException{

        String retStr = "";
        String strTable = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for ( int i = 0; i < intLength; i++ ) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor( dblR );
                char c = strTable.charAt( intR );
                if ( ( '0' <= c ) && ( c <= '9' ) ) {
                    count++;
                }
                retStr += strTable.charAt( intR );
            }
            if ( count >= 2 ) {
                bDone = false;
            }
        } while ( bDone );

        return retStr;
    }
    public String getUsername(int id) throws UregisterException {
        String uname = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select memberid from en_userinfo where id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                uname = rs.getString(1);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return uname;
    }

    //新用户注册信息
    private static String INSER_TINFO_FOR_ORACLE = "insert into tbl_userinfo(siteid,memberid,username,password,usertype,linkman,country,province,city,street,address," +
            "postalcode,phone,fax,email,homepage,remark,sex,oicq,birthday,image,sign,mobilephone,company,department,idno,degree,nation,unitpcode,unitphone,stationtype,entitytype," +
            "callsign,stationaddr,opedegree,memo,opecode,scores,lockflag,createdate,id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String INSER_TINFO_FOR_MSSQL = "insert into tbl_userinfo(siteid,memberid,username,password,usertype,linkman,country,province,city,street,address," +
            "postalcode,phone,fax,email,homepage,remark,sex,oicq,birthday,image,sign,mobilephone,company,department,idno,degree,nation,unitpcode,unitphone,stationtype,entitytype," +
            "callsign,stationaddr,opedegree,memo,opecode,scores,lockflag,createdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);select SCOPE_IDENTITY()";
    private static String INSER_TINFO_FOR_MYSQL = "insert into tbl_userinfo(siteid,memberid,username,password,usertype,linkman,country,province,city,street,address," +
            "postalcode,phone,fax,email,homepage,remark,sex,oicq,birthday,image,sign,mobilephone,company,department,idno,degree,nation,unitpcode,unitphone,stationtype,entitytype,"+
            "callsign,stationaddr,opedegree,memo,opecode,scores,lockflag,createdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public int insert_Info(Uregister reg) throws UregisterException {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int flag = 0,eflag=0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            //检查个人注册表中是否存在该用户名
            pstmt = conn.prepareStatement("select memberid from tbl_userinfo where memberid = ? and siteid = ?");
            pstmt.setString(1, reg.getMemberid());
            pstmt.setInt(2,reg.getSiteid());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag  = 1;
            } else {
                flag = 0;
            }
            rs.close();

            //检查企业注册表中是否存在该用户名
            pstmt = conn.prepareStatement("select userid from tbl_rsbt_org where userid = ? and siteid = ?");
            pstmt.setString(1, reg.getMemberid());
            pstmt.setInt(2,reg.getSiteid());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                eflag  = 1;
            } else {
                eflag = 0;
            }
            rs.close();
            pstmt.close();

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(INSER_TINFO_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(INSER_TINFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(INSER_TINFO_FOR_MYSQL);

            /*String buf = reg.getSiteid() + ",'" +reg.getMemberid() + "','" + reg.getName() + "','" + reg.getPassword() + "','" + reg.getLinkman() + "','" + reg.getCountru() +
                    "','" + reg.getProvince() + "','" + reg.getCity() + "','" + reg.getZone() + "','" + reg.getAddress() + "','" + reg.getPostalcode() + "','" + reg.getPhoen() +
                    "','" + reg.getFax() + "','" + reg.getEmail() + "','" + reg.getHomepage() + "','" + reg.getMessage() + "','" + reg.getSex() + "','" + reg.getQq() + "','" +  reg.getBirthday() +
                    "','" + reg.getImage() + "','" + reg.getSign() + "','" + reg.getMobilephone() + "','" + reg.getUnit() + "','" + reg.getIdno() +
                    "','" + reg.getDegree() + "','" + reg.getNation() + "','" + reg.getUnitpostcode() + "','" + reg.getUnitphone() + "','" + reg.getStationtype() +
                    "','" + reg.getEntitytype() + "','" + reg.getCallsign() + "','" + reg.getStationaddress() + "','" + reg.getOpedegree() + "','" + reg.getBeizhu() +
                    "','" + reg.getOpecode() + "','2001-12-31'," + 100;

            System.out.println(buf);
            */

            if (flag == 0 && eflag == 0)  {
                pstmt.setInt(1, reg.getSiteid());
                pstmt.setString(2, reg.getMemberid());
                pstmt.setString(3, reg.getName());
                pstmt.setString(4, reg.getPassword());
                pstmt.setInt(5,reg.getUsertype());
                pstmt.setString(6, reg.getLinkman());
                pstmt.setString(7, reg.getCountru());
                pstmt.setString(8, reg.getProvince());
                pstmt.setString(9, reg.getCity());
                pstmt.setString(10, reg.getZone());
                pstmt.setString(11, reg.getAddress());
                pstmt.setString(12, reg.getPostalcode());
                pstmt.setString(13, reg.getPhoen());
                pstmt.setString(14, reg.getFax());
                pstmt.setString(15, reg.getEmail());
                pstmt.setString(16, reg.getHomepage());
                pstmt.setString(17, reg.getMessage());
                pstmt.setString(18, reg.getSex());
                pstmt.setString(19, reg.getQq());
                pstmt.setTimestamp(20, reg.getBirthdate());
                pstmt.setString(21, reg.getImage());
                pstmt.setString(22, reg.getSign());
                pstmt.setString(23, reg.getMobilephone());
                pstmt.setString(24, reg.getCompany());
                pstmt.setString(25, reg.getDepartment());
                pstmt.setString(26, reg.getIdno());
                pstmt.setString(27, reg.getDegree());
                pstmt.setString(28, reg.getNation());
                pstmt.setString(29, reg.getUnitpostcode());
                pstmt.setString(30, reg.getUnitphone());
                pstmt.setString(31, reg.getStationtype());
                pstmt.setString(32, reg.getEntitytype());
                pstmt.setString(33, reg.getCallsign());
                pstmt.setString(34, reg.getStationaddress());
                pstmt.setString(35, reg.getOpedegree());
                pstmt.setString(36, reg.getBeizhu());
                pstmt.setString(37, reg.getOpecode());
                pstmt.setInt(38,reg.getScores());
                pstmt.setInt(39,reg.getLockflag());
                pstmt.setTimestamp(40, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    code = sequenceMgr.getSequenceNum("Userreg");
                    pstmt.setInt(41, code);
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        code = rs.getInt(1);
                    }
                    rs.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取Mysql自增列的值id
                    pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                    rs = pstmt.executeQuery();
                    if (rs.next()) code=rs.getInt(1);
                    rs.close();
                }
                pstmt.close();
                conn.commit();
            } else {
                code = -2;             //用户已经存在
            }
        }
        catch (Exception e) {
            code = -1;
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

        return code;
    }

    //siteid   （新用户注册的所属站点ID）
    public int getSiteid(String sitename) throws UregisterException {
        int siteid =0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        String select_siteid = "select siteid from tbl_siteipinfo where sitename = ?  ";
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(select_siteid);
            pstmt.setString(1, sitename);
            rs = pstmt.executeQuery();
            while(rs.next()){
                siteid = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
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
        return siteid;
    }

    //  (新用户注册时检查该用户名是否存在)
    public int checkUserExist(String str,int siteid) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int flag = 0,eflag=0;
        try {
            conn = cpool.getConnection();
            //检查个人注册表中是否存在该用户名
            pstmt = conn.prepareStatement("select memberid from tbl_userinfo where memberid = ? and siteid = ?");
            pstmt.setString(1, str);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag  = 1;
            } else {
                flag = 0;
            }
            rs.close();

            //检查企业注册表中是否存在该用户名
            pstmt = conn.prepareStatement("select userid from tbl_rsbt_org where userid = ? and siteid = ?");
            pstmt.setString(1, str);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                eflag  = 1;
            } else {
                eflag = 0;
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

        return flag + eflag;
    }

    //（新用户注册时验证该邮箱是否已经存在）
    public int checkUserEmailExist(String str,int siteid) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int flag = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select email from tbl_userinfo where email = ? and siteid=?");
            pstmt.setString(1, str);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = 1;
            } else {
                flag = 0;
            }
            // System.out.print(flag+"=============="+"检查邮箱");
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
        return flag;
    }

    //检查旧密码是否正确 （修改密码）
    private static String GETPASSWORD = "select password from tbl_userinfo where memberid = ? and siteid=?";
    public int getPassword(String memberid, String password,int siteid) throws UregisterException {
        int code=0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GETPASSWORD);
            pstmt.setString(1, memberid);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    code = 1;
                    System.out.println(code);
                }
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return code;
    }

    //查询用户信息
    public Uregister getUserInfo(String name,int siteid) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        Uregister ureg = new Uregister();
        String select_siteid = "select id, memberid,password,email,username,linkman,city,country,postalcode,phone,fax,birthday from tbl_userinfo where memberid = ? and siteid=?";
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(select_siteid);
            pstmt.setString(1, name);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ureg.setEmail(rs.getString("email"));
                ureg.setMemberid(rs.getString("memberid"));
                ureg.setPassword(rs.getString("password"));
                ureg.setId(rs.getInt("id"));
                ureg.setName(rs.getString("username"));
                ureg.setLinkman(rs.getString("linkman"));
                ureg.setCity(rs.getString("city"));
                ureg.setCountru(rs.getString("country"));
                ureg.setPostalcode(rs.getString("postalcode"));
                ureg.setPhoen(rs.getString("phone"));
                ureg.setFax(rs.getString("fax"));
                ureg.setBirthday(rs.getString("birthday"));
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
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
        return ureg;
    }

    public int  Update_userinfo(Uregister ure,int siteid)  throws UregisterException{
        Connection conn = null;
        int code = 0;
        PreparedStatement pstmt;
        ResultSet rs = null;
        String UPDAAT_USERINFO = "update tbl_userinfo set username=?,password= ? ,linkman=?,country=?,province=?,city=?,street=?,address=?,postalcode=?,"+
                "phone=?,fax=?,email=?,homepage=?,remark=?,sex=?,oicq=?,birthday=?,image=?,sign=?,mobilephone=?,company=?,idno=?,degree=?,nation=?,"+
                "unitpcode=?,unitphone=?,stationtype=?,entitytype=?,callsign=?,stationaddr=?,opedegree=?,memo=?,opecode=? where memberid=? and siteid=?";
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDAAT_USERINFO);
            pstmt.setString(1, ure.getName());
            pstmt.setString(2, ure.getPassword());
            pstmt.setString(3, ure.getLinkman());
            pstmt.setString(4, ure.getCountru());
            pstmt.setString(5, ure.getProvince());
            pstmt.setString(6, ure.getCity());
            pstmt.setString(7, ure.getZone());
            pstmt.setString(8, ure.getAddress());
            pstmt.setString(9, ure.getPostalcode());
            pstmt.setString(10, ure.getPhoen());
            pstmt.setString(11, ure.getFax());
            pstmt.setString(12, ure.getEmail());
            pstmt.setString(13, ure.getHomepage());
            pstmt.setString(14, ure.getMessage());
            pstmt.setString(15, ure.getSex());
            pstmt.setString(16, ure.getQq());
            pstmt.setString(17, ure.getBirthday());
            pstmt.setString(18, ure.getImage());
            pstmt.setString(19, ure.getSign());
            pstmt.setString(20, ure.getMobilephone());
            pstmt.setString(21, ure.getUnit());
            pstmt.setString(22, ure.getIdno());
            pstmt.setString(23, ure.getDegree());
            pstmt.setString(24, ure.getNation());
            pstmt.setString(25, ure.getPhoen());
            pstmt.setString(26, ure.getUnitpostcode());
            pstmt.setString(27, ure.getUnitphone());
            pstmt.setString(28, ure.getStationtype());
            pstmt.setString(29, ure.getEntitytype());
            pstmt.setString(30, ure.getCallsign());
            pstmt.setString(31, ure.getStationaddress());
            pstmt.setString(32, ure.getOpedegree());
            pstmt.setString(33, ure.getBeizhu());
            pstmt.setString(34, ure.getOpecode());
            pstmt.setString(35, ure.getMemberid());
            pstmt.setInt(36,siteid);
            rs = pstmt.executeQuery();
            rs.close();
            pstmt.close();
            conn.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            code = 1;
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

        return code;
    }

    /**
     * 获取用户信息
     * username 用户邮箱
     */
    public Uregister getUserinfo(int flag,String username)   throws UregisterException
    {
        Uregister user;
        user = new Uregister();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "";
        String csql = "";
        if(flag == 1){
            sql = "SELECT id,memberid,password,phone,mobilephone,createdate,valid,email,oicq,usertype,province,city,scores,postalcode,address,username,remark,meilizhi FROM tbl_userinfo WHERE memberid=?";
            csql = "SELECT cityname FROM en_city WHERE id=?";
        }else if(flag ==2 ){
            sql = "SELECT id,memberid,password,phone,mobilephone,createdate,valid,email,oicq,usertype,province,city,scores,postalcode,address,username,remark,meilizhi FROM tbl_userinfo WHERE email=?";
            csql = "SELECT cityname FROM en_city WHERE id=?";
        }
        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                user.setId(rs.getInt(1));
                user.setMemberid(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setPhoen(rs.getString(4));
                user.setMobilephone(rs.getString(5));
                user.setCreatedate(rs.getTimestamp(6));
                user.setValid(rs.getInt(7));
                user.setEmail(rs.getString(8));
                user.setQq(rs.getString(9));
                user.setUsertype(rs.getInt(10));
                user.setProvince(rs.getString(11));
                user.setCity(rs.getString(12));
                user.setScores(rs.getInt(13));
                user.setPostalcode(rs.getString(14));
                user.setAddress(rs.getString(15));
                user.setName(rs.getString(16));
                user.setMessage(rs.getString(17));
                user.setMeilizhi(rs.getInt(18));
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    /**
     * 用户登录  并获取用户是否被激活和用户是否被可用
     */
    public Uregister getLogin(String username, String password)  throws UregisterException
    {
        Uregister user;
        user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String usql = "SELECT memberid,email,valid,lockflag FROM tbl_userinfo WHERE memberid=? and password=?";
        String esql = "SELECT memberid,email,valid,lockflag FROM tbl_userinfo WHERE email=? and password=?";
        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(usql);
            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());
            rs = pstmt.executeQuery();
            if(rs.next()){
                user = new Uregister();
                user.setMemberid(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setValid(rs.getInt(3));
                user.setLockflag(rs.getInt(4));
                if(user.getValid()==0 && user.getLockflag()==1){
                    Timestamp dblogindate = null;
                    int usergrade = 0;
                    int meilizhi = 0;
                    rs.close();
                    pstmt.close();
                    String loginsql = "SELECT scores,lastlogindate,meilizhi FROM tbl_userinfo WHERE memberid=?";
                    pstmt = conn.prepareStatement(loginsql);
                    pstmt.setString(1, username);
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        usergrade = rs.getInt(1);
                        dblogindate = rs.getTimestamp(2);
                        meilizhi = rs.getInt(3);
                    }
                    rs.close();
                    pstmt.close();
                    Timestamp logindate = new Timestamp(System.currentTimeMillis());
                    if(!logindate.equals(dblogindate)){
                        String upsql = "UPDATE tbl_userinfo SET scores=?,lastlogindate=?,meilizhi=? WHERE memberid=?";
                        pstmt = conn.prepareStatement(upsql);
                        pstmt.setInt(1, usergrade+2);
                        pstmt.setTimestamp(2, logindate);
                        pstmt.setInt(3, meilizhi+5);
                        pstmt.setString(4, username);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
            }else{
                rs.close();
                pstmt.close();
                pstmt = conn.prepareStatement(esql);
                pstmt.setString(1, username.trim());
                pstmt.setString(2, password.trim());
                rs = pstmt.executeQuery();
                if(rs.next()){
                    user = new Uregister();
                    user.setMemberid(rs.getString(1));
                    user.setEmail(rs.getString(2));
                    user.setValid(rs.getInt(3));
                    user.setLockflag(rs.getInt(4));
                    if(user.getValid()==0 && user.getLockflag()==1){
                        Timestamp dblogindate = null;
                        int usergrade = 0;
                        int meilizhi = 0;
                        rs.close();
                        pstmt.close();
                        String loginsql = "SELECT scores,lastlogindate,meilizhi FROM tbl_userinfo WHERE memberid=?";
                        pstmt = conn.prepareStatement(loginsql);
                        pstmt.setString(1, username);
                        rs = pstmt.executeQuery();
                        if(rs.next()){
                            usergrade = rs.getInt(1);
                            dblogindate = rs.getTimestamp(2);
                            meilizhi = rs.getInt(3);
                        }
                        rs.close();
                        pstmt.close();
                        Timestamp logindate = new Timestamp(System.currentTimeMillis());
                        if(!logindate.equals(dblogindate)){
                            String upsql = "UPDATE tbl_userinfo SET scores=?,lastlogindate=?,meilizhi=? WHERE memberid=?";
                            pstmt = conn.prepareStatement(upsql);
                            pstmt.setInt(1, usergrade+2);
                            pstmt.setTimestamp(2, logindate);
                            pstmt.setInt(3, meilizhi+5);
                            pstmt.setString(4, username);
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    public Uregister login(String usern, String passw,int siteid) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Uregister ug = new Uregister();;

        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement("select id,memberid,username,password,email,mobilephone,usertype,grade,lockflag from tbl_userinfo where (memberid= ? or email=?) and siteid=?");
            pstmt.setString(1, usern);
            pstmt.setString(2, usern);
            pstmt.setInt(3,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String password = rs.getString("password");
                //password = Encrypt.md5(password.getBytes());       //加密密码
                if(rs.getInt("lockflag") == 0 ) {
                    if (passw.trim().equalsIgnoreCase(password)) {
                        //ug = new Uregister();
                        ug.setId(rs.getInt("id"));
                        ug.setMemberid(rs.getString("memberid"));
                        ug.setName(rs.getString("username"));
                        ug.setGrade(rs.getString("grade"));
                        ug.setUsertype(rs.getInt("usertype"));                                //0表示个人用户
                        ug.setEmail(rs.getString("email"));
                        ug.setMobilephone(rs.getString("mobilephone"));
                        ug.setSiteid(siteid);
                        ug.setErrmsg("ok");
                    } else {
                        ug.setErrmsg("用户口令错！！！");
                    }
                } else {
                    ug.setErrmsg("用户被系统管理员锁定，暂时停止登录！！！");
                }
            } else {
                ug.setErrmsg("用户名错，用户不存在");
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

        return ug;
    }

    public Uregister elogin(String usern, String passw,int siteid) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Uregister ug= null;

        try {
            conn = cpool.getConnection();
            //如果个人用户不存在，判断是否存在企业用户
            pstmt = conn.prepareStatement("select id,userid,password,siteid from tbl_rsbt_org where userid= ? and siteid=?");
            pstmt.setString(1, usern);
            pstmt.setInt(2,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                //System.out.println("passw=" + passw);
                String password = rs.getString("password");
                //System.out.println("password=" + password);

                //北京无线局网站密码传输数据
                //password = Encrypt.md5(password.getBytes());       //加密密码
                if (passw.equalsIgnoreCase(password)) {
                    ug = new Uregister();
                    ug.setId(rs.getInt("id"));
                    ug.setMemberid(rs.getString("userid"));
                    ug.setGrade("1");
                    ug.setCompany_or_personal(1);                                //1表示个人用户
                    ug.setSiteid(siteid);
                }
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
        return ug;
    }

    //查找用户可用积分 add  by feixiang 2009-06-24
    public int getUserScores(int userid){
        int scores = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select scores from tbl_userinfo where id = ?");
            pstmt.setInt(1,userid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                scores = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return scores;
    }
    //获得积分兑换规则 by feixiang 2009-06-24
    public int getScoresForMoney(String sitename){
        int scores = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select scores from tbl_siteinfo where sitename = ?");
            pstmt.setString(1,sitename);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                scores = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return scores;
    }

    private static String GETSITENAME = "select sitename from tbl_siteinfo where siteid=?";
    public String getSitename(int siteid) throws UregisterException {
        String sitename = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GETSITENAME);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sitename = rs.getString("sitename");
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return sitename;
    }

    //10.9.8

    //判断用户是否存在
    public boolean userExist(String userid){
        int result;
        result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select count(id) from tbl_rsbt_org where userid=?";
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid.trim());
            rs = pstmt.executeQuery();
            if (rs.next())
                result = rs.getInt(1);
            pstmt = conn.prepareStatement("select count(id) from tbl_userinfo where memberid=?");
            pstmt.setString(1,userid.trim());
            rs = pstmt.executeQuery();
            if(rs.next())
                result = result + rs.getInt(1);
            rs.close();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            else
                System.out.println("数据库连接失败!");
        }
        return result > 0;
    }
    private static String GET_INSERT_RSBT = "insert into tbl_rsbt_org(id,guid,userid,siteid,password,org_gode,org_name,org_area_code,"+
            "org_sys_code,org_type,org_link_person,org_person_id,org_sup_code,org_addr,org_post,org_phone,org_mob_phone,org_fax,"+
            "org_bank,org_account_name,org_account,org_hostility,org_web_site,org_mail,createdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public int insertRegister(Uregister reg){
        int code = 0;
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt=conn.prepareStatement("select max(id) from tbl_rsbt_org");
            ResultSet res=pstmt.executeQuery();
            if(res.next())
            {
                id=res.getInt(1);
            }
            id++;
            res.close();
            pstmt.close();
            pstmt = conn.prepareStatement(GET_INSERT_RSBT);
            pstmt.setInt(1,id);
            pstmt.setString(2,reg.getGuid());
            pstmt.setString(3,reg.getMemberid());
            pstmt.setInt(4,reg.getSiteid());
            pstmt.setString(5,reg.getPassword());
            pstmt.setString(6,reg.getOrggode());
            pstmt.setString(7,reg.getOrgname());
            pstmt.setString(8,reg.getOrgareacode());
            pstmt.setString(9,reg.getOrgsyscode());
            pstmt.setString(10,reg.getOrgtype());
            pstmt.setString(11,reg.getOrglinkperson());
            pstmt.setString(12,reg.getOrgpersonid());
            pstmt.setString(13,reg.getOrgsupcode());
            pstmt.setString(14,reg.getOrgaddr());
            pstmt.setString(15,reg.getOrgpost());
            pstmt.setString(16,reg.getOrgphone());
            pstmt.setString(17,reg.getOrgmobphone());
            pstmt.setString(18,reg.getOrgfax());
            pstmt.setString(19,reg.getOrgbank());
            pstmt.setString(20,reg.getOrgaccountname());
            pstmt.setString(21,reg.getOrgaccount());
            pstmt.setInt(22,reg.getOrghostility());
            pstmt.setString(23,reg.getOrgwebsite());
            pstmt.setString(24,reg.getOrgmail());
            pstmt.setTimestamp(25,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally{
                if(conn != null){
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
        return code;
    }

    private static String GET_UPDATE_RSBT = "update tbl_rsbt_org set guid=?,userid=?,siteid=?,password=?,org_gode=?,org_name=?,org_area_code=?,"+
            "org_sys_code=?,org_type=?,org_link_person=?,org_person_id=?,org_sup_code=?,org_addr=?,org_post=?,org_phone=?,org_mob_phone=?,org_fax=?," +
            "org_bank=?,org_account_name=?,org_account=?,org_hostility=?,org_web_site=?,org_mail=?,createdate=? where id = ?";
    public void updateRsbt(Uregister reg, int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_UPDATE_RSBT);
            pstmt.setString(1,reg.getGuid());
            pstmt.setString(2,reg.getMemberid());
            pstmt.setInt(3,reg.getSiteid());
            pstmt.setString(4,reg.getPassword());
            pstmt.setString(5,reg.getOrggode());
            pstmt.setString(6,reg.getOrgname());
            pstmt.setString(7,reg.getOrgareacode());
            pstmt.setString(8,reg.getOrgsyscode());
            pstmt.setString(9,reg.getOrgtype());
            pstmt.setString(10,reg.getOrglinkperson());
            pstmt.setString(11,reg.getOrgpersonid());
            pstmt.setString(12,reg.getOrgsupcode());
            pstmt.setString(13,reg.getOrgaddr());
            pstmt.setString(14,reg.getOrgpost());
            pstmt.setString(15,reg.getOrgphone());
            pstmt.setString(16,reg.getOrgmobphone());
            pstmt.setString(17,reg.getOrgfax());
            pstmt.setString(18,reg.getOrgbank());
            pstmt.setString(19,reg.getOrgaccountname());
            pstmt.setString(20,reg.getOrgaccount());
            pstmt.setInt(21,reg.getOrghostility());
            pstmt.setString(22,reg.getOrgwebsite());
            pstmt.setString(23,reg.getOrgmail());
            pstmt.setTimestamp(24,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(25,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public void deleteRsbtList(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_rsbt_org where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String GET_ALL_RSBL = "select * from tbl_rsbt_org order by id desc";
    public List getAllRsbt(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_RSBL);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Uregister reg = new Uregister();
                reg.setId(rs.getInt("id"));
                reg.setGuid(rs.getString("guid"));
                reg.setMemberid(rs.getString("userid"));
                reg.setSiteid(rs.getInt("siteid"));
                reg.setPassword(rs.getString("password"));
                reg.setOrggode(rs.getString("org_gode"));
                reg.setOrgname(rs.getString("org_name"));
                reg.setOrgareacode(rs.getString("org_area_code"));
                reg.setOrgsyscode(rs.getString("org_sys_code"));
                reg.setOrgtype(rs.getString("org_type"));
                reg.setOrglinkperson(rs.getString("org_link_person"));
                reg.setOrgpersonid(rs.getString("org_person_id"));
                reg.setOrgsupcode(rs.getString("org_sup_code"));
                reg.setOrgaddr(rs.getString("org_addr"));
                reg.setOrgpost(rs.getString("org_post"));
                reg.setOrgphone(rs.getString("org_phone"));
                reg.setOrgmobphone(rs.getString("org_mob_phone"));
                reg.setOrgfax(rs.getString("org_fax"));
                reg.setOrgbank(rs.getString("org_bank"));
                reg.setOrgaccountname(rs.getString("org_account_name"));
                reg.setOrgaccount(rs.getString("org_account"));
                reg.setOrghostility(rs.getInt("org_hostility"));
                reg.setOrgwebsite(rs.getString("org_web_site"));
                reg.setOrgmail(rs.getString("org_mail"));
                reg.setCreatedate(rs.getTimestamp("createdate"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_RSBT_SITE = "SELECT * FROM TBL_RSBT_ORG where siteid=? order by id desc";
    public List getAllRsbt(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_RSBT_SITE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Uregister reg = new Uregister();
                reg.setId(rs.getInt("id"));
                reg.setGuid(rs.getString("guid"));
                reg.setMemberid(rs.getString("userid"));
                reg.setSiteid(rs.getInt("siteid"));
                reg.setPassword(rs.getString("password"));
                reg.setOrggode(rs.getString("org_gode"));
                reg.setOrgname(rs.getString("org_name"));
                reg.setOrgareacode(rs.getString("org_area_code"));
                reg.setOrgsyscode(rs.getString("org_sys_code"));
                reg.setOrgtype(rs.getString("org_type"));
                reg.setOrglinkperson(rs.getString("org_link_person"));
                reg.setOrgpersonid(rs.getString("org_person_id"));
                reg.setOrgsupcode(rs.getString("org_sup_code"));
                reg.setOrgaddr(rs.getString("org_addr"));
                reg.setOrgpost(rs.getString("org_post"));
                reg.setOrgphone(rs.getString("org_phone"));
                reg.setOrgmobphone(rs.getString("org_mob_phone"));
                reg.setOrgfax(rs.getString("org_fax"));
                reg.setOrgbank(rs.getString("org_bank"));
                reg.setOrgaccountname(rs.getString("org_account_name"));
                reg.setOrgaccount(rs.getString("org_account"));
                reg.setOrghostility(rs.getInt("org_hostility"));
                reg.setOrgwebsite(rs.getString("org_web_site"));
                reg.setOrgmail(rs.getString("org_mail"));
                reg.setCreatedate(rs.getTimestamp("createdate"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String GET_ALL_SUPPLIER_NUM = "select count(id) from TBL_RSBT_ORG where siteid = ?";

    public int getAllRsbtNum(int siteid){
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUPPLIER_NUM);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    public List getCurrentQueryRsbtList(int id,String sqlstr, int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");
        String sql = "and siteid=?";
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr + sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Uregister reg = loadsurvey(rs);
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_CURRENT_RSBT_LIST = "SELECT * FROM TBL_RSBT_ORG where siteid = ? order by id desc";
    public List getCurrentRsbtList(int siteid, int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_CURRENT_RSBT_LIST);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Uregister reg = loadsurvey(rs);
                list.add(reg);
            }//System.out.println("aaaaaaaaaa"+list);
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_BYID_RSBT = "select * from tbl_rsbt_org where id = ?";
    public Uregister getByIdrsbt(int id){
        Uregister reg = new Uregister();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_RSBT);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                reg.setId(rs.getInt("id"));
                reg.setGuid(rs.getString("guid"));
                reg.setMemberid(rs.getString("userid"));
                reg.setSiteid(rs.getInt("siteid"));
                reg.setPassword(rs.getString("password"));
                reg.setOrggode(rs.getString("org_gode"));
                reg.setOrgname(rs.getString("org_name"));
                reg.setOrgareacode(rs.getString("org_area_code"));
                reg.setOrgsyscode(rs.getString("org_sys_code"));
                reg.setOrgtype(rs.getString("org_type"));
                reg.setOrglinkperson(rs.getString("org_link_person"));
                reg.setOrgpersonid(rs.getString("org_person_id"));
                reg.setOrgsupcode(rs.getString("org_sup_code"));
                reg.setOrgaddr(rs.getString("org_addr"));
                reg.setOrgpost(rs.getString("org_post"));
                reg.setOrgphone(rs.getString("org_phone"));
                reg.setOrgmobphone(rs.getString("org_mob_phone"));
                reg.setOrgfax(rs.getString("org_fax"));
                reg.setOrgbank(rs.getString("org_bank"));
                reg.setOrgaccountname(rs.getString("org_account_name"));
                reg.setOrgaccount(rs.getString("org_account"));
                reg.setOrghostility(rs.getInt("org_hostility"));
                reg.setOrgwebsite(rs.getString("org_web_site"));
                reg.setOrgmail(rs.getString("org_mail"));
                reg.setCreatedate(rs.getTimestamp("createdate"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return reg;
    }

    public Uregister loadsurvey(ResultSet rs) {
        Uregister reg = new Uregister();
        try{
            reg.setId(rs.getInt("id"));
            reg.setGuid(rs.getString("guid"));
            reg.setMemberid(rs.getString("userid"));
            reg.setSiteid(rs.getInt("siteid"));
            reg.setPassword(rs.getString("password"));
            reg.setOrggode(rs.getString("org_gode"));
            reg.setOrgname(rs.getString("org_name"));
            reg.setOrgareacode(rs.getString("org_area_code"));
            reg.setOrgsyscode(rs.getString("org_sys_code"));
            reg.setOrgtype(rs.getString("org_type"));
            reg.setOrglinkperson(rs.getString("org_link_person"));
            reg.setOrgpersonid(rs.getString("org_person_id"));
            reg.setOrgsupcode(rs.getString("org_sup_code"));
            reg.setOrgaddr(rs.getString("org_addr"));
            reg.setOrgpost(rs.getString("org_post"));
            reg.setOrgphone(rs.getString("org_phone"));
            reg.setOrgmobphone(rs.getString("org_mob_phone"));
            reg.setOrgfax(rs.getString("org_fax"));
            reg.setOrgbank(rs.getString("org_bank"));
            reg.setOrgaccountname(rs.getString("org_account_name"));
            reg.setOrgaccount(rs.getString("org_account"));
            reg.setOrghostility(rs.getInt("org_hostility"));
            reg.setOrgwebsite(rs.getString("org_web_site"));
            reg.setOrgmail(rs.getString("org_mail"));
            reg.setCreatedate(rs.getTimestamp("createdate"));
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reg;
    }

    public List getAllOrg1(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_rsbt_org1");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Uregister reg = new Uregister();
                reg.setId(rs.getInt("id"));
                reg.setValue(rs.getInt("value"));
                reg.setName(rs.getString("name"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getAllOrg2(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_rsbt_org2");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Uregister reg = new Uregister();
                reg.setId(rs.getInt("id"));
                reg.setValue(rs.getInt("value"));
                reg.setName(rs.getString("name"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getAllOrg3(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_rsbt_org3");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Uregister reg = new Uregister();
                reg.setId(rs.getInt("id"));
                reg.setValue(rs.getInt("value"));
                reg.setName(rs.getString("name"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    //更新用户使用的积分 add by feixiang 2010-10-26
    public void updateUserScores(int scores,int userid)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_userinfo set scores = ? where id = ?");
            pstmt.setInt(1,scores);
            pstmt.setInt(2,userid);
            pstmt.executeUpdate();
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
        finally{
            if(conn != null)
            {
                cpool.freeConnection(conn);
            }
        }
    }

    public Uregister getUregister(int userid) {
        String sqlstr = "select * from TBL_USERINFO where id = ?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Uregister ureg = new Uregister();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ureg.setUsertype(rs.getInt("usertype"));
                ureg.setScores(rs.getInt("scores"));
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
                    e.printStackTrace();
                }
            }
        }
        return ureg;
    }

    //update user password add by feixiang 2010-12-18
    public int updateUserPassword(int userid,String password){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_userinfo set password = ? where id = ?");
            pstmt.setString(1,password);
            pstmt.setInt(2,userid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(Exception e){
            code = -1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    public int updateUserPassword(String username,String password){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_userinfo set password = ? where memberid = ?");
            pstmt.setString(1,password);
            pstmt.setString(2,username);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(Exception e){
            code = -1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    public OlympicMembers loginForOlympic(String usern, String passw) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        OlympicMembers ug= null;

        try {
            conn = cpool.getConnection();
            //如果个人用户不存在，判断是否存在企业用户
            //System.out.println("username=" + usern);
            //System.out.println("password=" + passw);
            pstmt = conn.prepareStatement("select username,password,name_cn,sex,birthdate,idnum,mobilephone from tbl_olympic_members where username=?");
            pstmt.setString(1, usern);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                //System.out.println("username=查到记录");
                String password = rs.getString("password");
                if (passw.equalsIgnoreCase(password) || password==null) {
                    //System.out.println("口令相同");
                    ug = new OlympicMembers();
                    ug.setUsername(rs.getString("username"));
                    ug.setPassword(rs.getString("password"));
                    ug.setName_cn(rs.getString("name_cn"));
                    ug.setSex(rs.getInt("sex"));                     //1表示女  0--表示男
                    ug.setBirthdate(rs.getTimestamp("birthdate"));
                    ug.setIdnum(rs.getString("idnum"));
                    ug.setMobilephone(rs.getString("mobilephone"));
                }
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
        return ug;
    }

    //查询用户信息
    public OlympicMembers getUserInfoForOlympic(String name) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        OlympicMembers ureg = new OlympicMembers();
        String select_siteid = "select username,password,name_py,name_cn,name_cy,prename,sex,birthdate," +
                "country,nationality,area,officetelephone,hometelephone,mobilephone,email,ethnic,placeofbirth,timeforjoincp," +
                "timeofstartwork,memberofcpc,idfromcountry,idnum,idtype,idvaliddate,contactpersonname,contactpersonrelation," +
                "contactpersonphone,yjcountry,yjprovince,yjcity,yjaddress,cjcountry,cjprovince,cjcity,cjaddress,education," +
                "age,company,title,address,postcode,deptOfOlympic,memberTypeOfOlympic,eduinfo,traninginfo,workinfo,startdate," +
                "enddate,titleInOlympic,skills,authflag,location,photo from tbl_olympic_members where username = ?";
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(select_siteid);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ureg.setUsername(rs.getString("username"));
                ureg.setPassword(rs.getString("password"));
                ureg.setName_py(rs.getString("name_py"));
                ureg.setName_cn(rs.getString("name_cn"));
                ureg.setName_cy(rs.getString("name_cy"));
                ureg.setPrename(rs.getString("prename"));
                ureg.setSex(rs.getInt("sex"));
                ureg.setBirthdate(rs.getTimestamp("birthdate"));
                ureg.setCountry(rs.getString("country"));
                ureg.setNationality(rs.getString("nationality"));
                ureg.setArea(rs.getString("area"));
                ureg.setOfficetelephone(rs.getString("officetelephone"));
                ureg.setHometelephone(rs.getString("hometelephone"));
                ureg.setMobilephone(rs.getString("mobilephone"));
                ureg.setEmail(rs.getString("email"));
                ureg.setEthnic(rs.getString("ethnic"));
                ureg.setPlaceofbirth(rs.getString("placeofbirth"));
                ureg.setTimeforjoincp(rs.getTimestamp("timeforjoincp"));
                ureg.setTimeofstartwork(rs.getTimestamp("timeofstartwork"));
                ureg.setMemberofcpc(rs.getInt("memberofcpc"));
                ureg.setIdfromcountry(rs.getString("idfromcountry"));
                ureg.setIdnum(rs.getString("idnum"));
                ureg.setIdtype(rs.getString("idtype"));
                ureg.setIdvaliddate(rs.getTimestamp("idvaliddate"));
                ureg.setContactpersonname(rs.getString("contactpersonname"));
                ureg.setContactpersonrelation(rs.getString("contactpersonrelation"));
                ureg.setContactpersonphone(rs.getString("contactpersonphone"));
                ureg.setYjcountry(rs.getString("yjcountry"));
                ureg.setYjprovince(rs.getString("yjprovince"));
                ureg.setYjcity(rs.getString("yjcity"));
                ureg.setYjaddress(rs.getString("yjaddress"));
                ureg.setCjcountry(rs.getString("cjcountry"));
                ureg.setCjprovince(rs.getString("cjprovince"));
                ureg.setCjcity(rs.getString("cjcity"));
                ureg.setCjaddress(rs.getString("cjaddress"));
                ureg.setEducation(rs.getInt("education"));
                ureg.setAge(rs.getInt("age"));
                ureg.setCompany(rs.getString("company"));
                ureg.setTitle(rs.getString("title"));
                ureg.setAddress(rs.getString("address"));
                ureg.setPostcode(rs.getString("postcode"));
                ureg.setDeptOfOlympic(rs.getString("deptOfOlympic"));
                ureg.setMemberTypeOfOlympic(rs.getString("memberTypeOfOlympic"));
                ureg.setEduinfo(rs.getString("eduinfo"));
                ureg.setTraninginfo(rs.getString("traninginfo"));
                ureg.setWorkinfo(rs.getString("workinfo"));
                ureg.setStartdate(rs.getTimestamp("startdate"));
                ureg.setEnddate(rs.getTimestamp("enddate"));
                ureg.setTitleInOlympic(rs.getString("titleInOlympic"));
                ureg.setSkills(rs.getString("skills"));
                ureg.setAuthflag(rs.getInt("authflag"));
                ureg.setLocation(rs.getString("location"));
                ureg.setPhoto(rs.getString("photo"));
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
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
        return ureg;
    }

    public int updateUserInfoForOlympic(OlympicMembers ureg) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int retcode = 0;
        /*String select_siteid = "update tbl_olympic_members set username=?,password=?,name_py=?,name_cn=?,name_cy=?,prename=?,sex=?,birthdate=?," +
                "country=?,nationality=?,area=?,officetelephone=?,hometelephone=?,mobilephone=?,email=?,ethnic=?,placeofbirth=?,timeforjoincp=?," +
                "timeofstartwork=?,memberofcpc=?,idfromcountry=?,idnum=?,idtype=?,idvaliddate=?,contactpersonname=?,contactpersonrelation=?," +
                "contactpersonphone=?,yjcountry=?,yjprovince=?,yjcity=?,yjaddress=?,cjcountry=?,cjprovince=?,cjcity=?,cjaddress=?,education=?," +
                "age=?,company=?,title=?,address=?,postcode=?,deptOfOlympic=?,memberTypeOfOlympic=?,eduinfo=?,traninginfo=?,workinfo=?,startdate=?," +
                "enddate=?,titleInOlympic=?,skills=?,authflag=?,location=? where idnum = ?";
        */

        String select_siteid = "update tbl_olympic_members set username=?,password=?,name_py=?,name_cn=?,sex=?,birthdate=?," +
                "nationality=?,officetelephone=?,hometelephone=?,mobilephone=?,email=?,ethnic=?,idnum=?,idtype=?,education=?," +
                "age=?,company=?,title=?,address=?,postcode=?,deptOfOlympic=?,memberTypeOfOlympic=?,startdate=?," +
                "enddate=?,titleInOlympic=?,authflag=?,location=? where idnum = ?";

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(select_siteid);
            pstmt.setString(1, ureg.getUsername());
            pstmt.setString(2, ureg.getPassword());
            pstmt.setString(3, ureg.getName_py());
            pstmt.setString(4, ureg.getName_cn());
            //pstmt.setString(5, ureg.getName_cy());
            //pstmt.setString(6, ureg.getPrename());
            pstmt.setInt(5, ureg.getSex());
            pstmt.setTimestamp(6, ureg.getBirthdate());
            //pstmt.setString(9, ureg.getCountry());
            pstmt.setString(7, ureg.getNationality());
            //pstmt.setString(11,ureg.getArea());
            pstmt.setString(8,ureg.getOfficetelephone());
            pstmt.setString(9,ureg.getHometelephone());
            pstmt.setString(10,ureg.getMobilephone());
            pstmt.setString(11,ureg.getEmail());
            pstmt.setString(12,ureg.getEthnic());
            //pstmt.setString(17,ureg.getPlaceofbirth());
            //pstmt.setTimestamp(18,ureg.getTimeforjoincp());
            //pstmt.setTimestamp(19,ureg.getTimeofstartwork());
            //pstmt.setInt(20, ureg.getMemberofcpc());
            //pstmt.setString(21,ureg.getIdfromcountry());
            pstmt.setString(13,ureg.getIdnum());
            pstmt.setString(14,ureg.getIdtype());
            //pstmt.setTimestamp(24,ureg.getIdvaliddate());
            //pstmt.setString(25,ureg.getContactpersonname());
            //pstmt.setString(26,ureg.getContactpersonrelation());
            //pstmt.setString(27,ureg.getContactpersonphone());
            //pstmt.setString(28,ureg.getYjcountry());
            //pstmt.setString(29,ureg.getYjprovince());
            //pstmt.setString(30,ureg.getYjcity());
            //pstmt.setString(31,ureg.getYjaddress());
            //pstmt.setString(32,ureg.getCjcountry());
            //pstmt.setString(33,ureg.getCjprovince());
            //pstmt.setString(34,ureg.getCjcity());
            //pstmt.setString(35,ureg.getCjaddress());
            pstmt.setInt(15, ureg.getEducation());
            pstmt.setInt(16,ureg.getAge());
            pstmt.setString(17,ureg.getCompany());
            pstmt.setString(18,ureg.getTitle());
            pstmt.setString(19,ureg.getAddress());
            pstmt.setString(20,ureg.getPostcode());
            pstmt.setString(21,ureg.getDeptOfOlympic());
            pstmt.setString(22,ureg.getMemberTypeOfOlympic());
            //pstmt.setString(44,ureg.getEduinfo());
            //pstmt.setString(45,ureg.getTraninginfo());
            //pstmt.setString(46,ureg.getWorkinfo());
            pstmt.setTimestamp(23,ureg.getStartdate());
            pstmt.setTimestamp(24,ureg.getEnddate());
            pstmt.setString(25,ureg.getTitleInOlympic());
            //pstmt.setString(50,ureg.getSkills());
            pstmt.setInt(26,ureg.getAuthflag());
            pstmt.setString(27,ureg.getLocation());
            pstmt.setString(28,ureg.getIdnum());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(Exception e){
            retcode = -1;
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
        return retcode;
    }

    public int createUserInfoForOlympic(OlympicMembers ureg) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int retcode = 0;
        String select_siteid = "insert into tbl_olympic_members(username,password,name_py,name_cn,name_cy,prename,sex,birthdate," +
                "country,nationality,area,officetelephone,hometelephone,mobilephone,email,ethnic,placeofbirth,timeforjoincp," +
                "timeofstartwork,memberofcpc,idfromcountry,idnum,idtype,idvaliddate,contactpersonname,contactpersonrelation," +
                "contactpersonphone,yjcountry,yjprovince,yjcity,yjaddress,cjcountry,cjprovince,cjcity,cjaddress,education," +
                "age,company,title,address,postcode,deptOfOlympic,memberTypeOfOlympic,eduinfo,traninginfo,workinfo,startdate,enddate," +
                "titleInOlympic,skills) into(?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(select_siteid);
            pstmt.setString(1, ureg.getUsername());
            pstmt.setString(2, ureg.getPassword());
            pstmt.setString(3, ureg.getName_py());
            pstmt.setString(4, ureg.getName_cn());
            pstmt.setString(5, ureg.getName_cy());
            pstmt.setString(6, ureg.getPrename());
            pstmt.setInt(7, ureg.getSex());
            pstmt.setTimestamp(8, ureg.getBirthdate());
            pstmt.setString(9, ureg.getCountry());
            pstmt.setString(10, ureg.getNationality());
            pstmt.setString(11,ureg.getArea());
            pstmt.setString(12,ureg.getOfficetelephone());
            pstmt.setString(13,ureg.getHometelephone());
            pstmt.setString(14,ureg.getMobilephone());
            pstmt.setString(15,ureg.getEmail());
            pstmt.setString(16,ureg.getEthnic());
            pstmt.setString(17,ureg.getPlaceofbirth());
            pstmt.setTimestamp(18,ureg.getTimeforjoincp());
            pstmt.setTimestamp(19,ureg.getTimeofstartwork());
            pstmt.setInt(20, ureg.getMemberofcpc());
            pstmt.setString(21,ureg.getIdfromcountry());
            pstmt.setString(22,ureg.getIdnum());
            pstmt.setString(23,ureg.getIdtype());
            pstmt.setTimestamp(24,ureg.getIdvaliddate());
            pstmt.setString(25,ureg.getContactpersonname());
            pstmt.setString(26,ureg.getContactpersonrelation());
            pstmt.setString(27,ureg.getContactpersonphone());
            pstmt.setString(28,ureg.getYjcountry());
            pstmt.setString(29,ureg.getYjprovince());
            pstmt.setString(30,ureg.getYjcity());
            pstmt.setString(31,ureg.getYjaddress());
            pstmt.setString(32,ureg.getCjcountry());
            pstmt.setString(33,ureg.getCjprovince());
            pstmt.setString(34,ureg.getCjcity());
            pstmt.setString(35,ureg.getCjaddress());
            pstmt.setInt(36, ureg.getEducation());
            pstmt.setInt(37,ureg.getAge());
            pstmt.setString(38,ureg.getCompany());
            pstmt.setString(39,ureg.getTitle());
            pstmt.setString(40,ureg.getAddress());
            pstmt.setString(41,ureg.getPostcode());
            pstmt.setString(42,ureg.getDeptOfOlympic());
            pstmt.setString(43,ureg.getMemberTypeOfOlympic());
            pstmt.setString(44,ureg.getEduinfo());
            pstmt.setString(45,ureg.getTraninginfo());
            pstmt.setString(46,ureg.getWorkinfo());
            pstmt.setTimestamp(47,ureg.getStartdate());
            pstmt.setTimestamp(48,ureg.getEnddate());
            pstmt.setString(49,ureg.getTitleInOlympic());
            pstmt.setString(50,ureg.getSkills());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(Exception e){
            retcode = -1;
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
        return retcode;
    }

    public List searchUserInfoForOlympic(OlympicMembers keyword,int startrow,int pagesize,int type) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        OlympicMembers ureg = null;
        List list = new ArrayList();
        String condition = "";

        if (type ==1) condition = condition + " talentflag = 1";

        if (keyword.getName_cn() != null && keyword.getName_cn() != "") {
            condition = condition + " and (name_cn='" + keyword.getName_cn() + "' OR name_py='" + keyword.getName_cn() + "')";
        }

        if (keyword.getIdnum() != null && keyword.getIdnum() != "") {
            condition = condition + " and idnum='" + keyword.getIdnum() + "'";
        }

        if (keyword.getDeptOfOlympic() != null && keyword.getDeptOfOlympic() != "") {
            condition = condition + " and location='" + keyword.getDeptOfOlympic() + "'";
        }

        if (condition.startsWith(" and")) condition = condition.substring(4);

        String SQL_SEARCH_USERINFO= "select * from (select A.*, ROWNUM RN from (select * from tbl_olympic_members where " + condition + ") A where ROWNUM < ?) where RN >= ?";

        //String select_siteid = "select * from tbl_olympic_members where " + condition;
        try{
            if (condition != null && condition!="") {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_SEARCH_USERINFO);
                pstmt.setInt(1, startrow+pagesize);
                pstmt.setInt(2, startrow);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    ureg = new OlympicMembers();
                    ureg.setUsername(rs.getString("username"));
                    ureg.setPassword(rs.getString("password"));
                    ureg.setName_py(rs.getString("name_py"));
                    ureg.setName_cn(rs.getString("name_cn"));
                    ureg.setName_cy(rs.getString("name_cy"));
                    ureg.setPrename(rs.getString("prename"));
                    ureg.setSex(rs.getInt("sex"));
                    ureg.setBirthdate(rs.getTimestamp("birthdate"));
                    ureg.setCountry(rs.getString("country"));
                    ureg.setNationality(rs.getString("nationality"));
                    ureg.setArea(rs.getString("area"));
                    ureg.setOfficetelephone(rs.getString("officetelephone"));
                    ureg.setHometelephone(rs.getString("hometelephone"));
                    ureg.setMobilephone(rs.getString("mobilephone"));
                    ureg.setEmail(rs.getString("email"));
                    ureg.setEthnic(rs.getString("ethnic"));
                    ureg.setPlaceofbirth(rs.getString("placeofbirth"));
                    ureg.setTimeforjoincp(rs.getTimestamp("timeforjoincp"));
                    ureg.setTimeofstartwork(rs.getTimestamp("timeofstartwork"));
                    ureg.setMemberofcpc(rs.getInt("memberofcpc"));
                    ureg.setIdfromcountry(rs.getString("idfromcountry"));
                    ureg.setIdnum(rs.getString("idnum"));
                    ureg.setIdtype(rs.getString("idtype"));
                    ureg.setIdvaliddate(rs.getTimestamp("idvaliddate"));
                    ureg.setContactpersonname(rs.getString("contactpersonname"));
                    ureg.setContactpersonrelation(rs.getString("contactpersonrelation"));
                    ureg.setContactpersonphone(rs.getString("contactpersonphone"));
                    ureg.setYjcountry(rs.getString("yjcountry"));
                    ureg.setYjprovince(rs.getString("yjprovince"));
                    ureg.setYjcity(rs.getString("yjcity"));
                    ureg.setYjaddress(rs.getString("yjaddress"));
                    ureg.setCjcountry(rs.getString("cjcountry"));
                    ureg.setCjprovince(rs.getString("cjprovince"));
                    ureg.setCjcity(rs.getString("cjcity"));
                    ureg.setCjaddress(rs.getString("cjaddress"));
                    ureg.setEducation(rs.getInt("education"));
                    ureg.setAge(rs.getInt("age"));
                    ureg.setCompany(rs.getString("company"));
                    ureg.setTitle(rs.getString("title"));
                    ureg.setAddress(rs.getString("address"));
                    ureg.setPostcode(rs.getString("postcode"));
                    ureg.setDeptOfOlympic(rs.getString("deptOfOlympic"));
                    ureg.setMemberTypeOfOlympic(rs.getString("memberTypeOfOlympic"));
                    ureg.setEduinfo(rs.getString("eduinfo"));
                    ureg.setTraninginfo(rs.getString("traninginfo"));
                    ureg.setWorkinfo(rs.getString("workinfo"));
                    ureg.setStartdate(rs.getTimestamp("startdate"));
                    ureg.setEnddate(rs.getTimestamp("enddate"));
                    ureg.setTitleInOlympic(rs.getString("titleInOlympic"));
                    ureg.setSkills(rs.getString("skills"));
                    ureg.setLocation(rs.getString("location"));
                    list.add(ureg);
                }
                rs.close();
                pstmt.close();
            }
        }
        catch(Exception e){
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
        return list;
    }

    public int searchUserCountForOlympic(OlympicMembers keyword,int type) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        int count = 0;
        String condition = "";

        if (type ==1) condition = condition + " talentflag = 1";

        if (keyword.getName_cn() != null && keyword.getName_cn() != "") {
            condition = condition + " and (name_cn='" + keyword.getName_cn() + "' OR name_py='" + keyword.getName_cn() + "')";
        }

        if (keyword.getIdnum() != null && keyword.getIdnum() != "") {
            condition = condition + " and idnum='" + keyword.getIdnum() + "'";
        }

        if (keyword.getDeptOfOlympic() != null && keyword.getDeptOfOlympic() != "") {
            condition = condition + " and location='" + keyword.getDeptOfOlympic() + "'";
        }

        if (condition.startsWith(" and")) condition = condition.substring(4);

        String SQL_SEARCH_USERCOUNT= "select count(*) from tbl_olympic_members where " + condition;
        System.out.println(SQL_SEARCH_USERCOUNT);

        //String select_siteid = "select * from tbl_olympic_members where " + condition;
        try{
            if (condition != null && condition!="") {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_SEARCH_USERCOUNT);
                rs = pstmt.executeQuery();
                if (rs.next()) count = rs.getInt(1);
                rs.close();
                pstmt.close();
            }
        }
        catch(Exception e){
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

        return count;
    }

    public List getUserInfoForOlympic(int startrow,int pagesize,int type) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        OlympicMembers ureg = null;
        List list = new ArrayList();
        String SQL_GET_ALL_USERINFO= "";

        if (type ==1)
            SQL_GET_ALL_USERINFO= "select * from (select A.*, ROWNUM RN from (select * from tbl_olympic_members where talentflag=1 and location is not null and company is not null) A where ROWNUM < ?) where RN >= ?";
        else
            SQL_GET_ALL_USERINFO= "select * from (select A.*, ROWNUM RN from (select * from tbl_olympic_members) A where ROWNUM < ?) where RN >= ?";

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_USERINFO);
            pstmt.setInt(1, startrow+pagesize);
            pstmt.setInt(2, startrow);
            rs = pstmt.executeQuery();
            while(rs.next()){
                ureg = new OlympicMembers();
                ureg.setUsername(rs.getString("username"));
                ureg.setPassword(rs.getString("password"));
                ureg.setName_py(rs.getString("name_py"));
                ureg.setName_cn(rs.getString("name_cn"));
                ureg.setName_cy(rs.getString("name_cy"));
                ureg.setPrename(rs.getString("prename"));
                ureg.setSex(rs.getInt("sex"));
                ureg.setBirthdate(rs.getTimestamp("birthdate"));
                ureg.setCountry(rs.getString("country"));
                ureg.setNationality(rs.getString("nationality"));
                ureg.setArea(rs.getString("area"));
                ureg.setOfficetelephone(rs.getString("officetelephone"));
                ureg.setHometelephone(rs.getString("hometelephone"));
                ureg.setMobilephone(rs.getString("mobilephone"));
                ureg.setEmail(rs.getString("email"));
                ureg.setEthnic(rs.getString("ethnic"));
                ureg.setPlaceofbirth(rs.getString("placeofbirth"));
                ureg.setTimeforjoincp(rs.getTimestamp("timeforjoincp"));
                ureg.setTimeofstartwork(rs.getTimestamp("timeofstartwork"));
                ureg.setMemberofcpc(rs.getInt("memberofcpc"));
                ureg.setIdfromcountry(rs.getString("idfromcountry"));
                ureg.setIdnum(rs.getString("idnum"));
                ureg.setIdtype(rs.getString("idtype"));
                ureg.setIdvaliddate(rs.getTimestamp("idvaliddate"));
                ureg.setContactpersonname(rs.getString("contactpersonname"));
                ureg.setContactpersonrelation(rs.getString("contactpersonrelation"));
                ureg.setContactpersonphone(rs.getString("contactpersonphone"));
                ureg.setYjcountry(rs.getString("yjcountry"));
                ureg.setYjprovince(rs.getString("yjprovince"));
                ureg.setYjcity(rs.getString("yjcity"));
                ureg.setYjaddress(rs.getString("yjaddress"));
                ureg.setCjcountry(rs.getString("cjcountry"));
                ureg.setCjprovince(rs.getString("cjprovince"));
                ureg.setCjcity(rs.getString("cjcity"));
                ureg.setCjaddress(rs.getString("cjaddress"));
                ureg.setEducation(rs.getInt("education"));
                ureg.setAge(rs.getInt("age"));
                ureg.setCompany(rs.getString("company"));
                ureg.setTitle(rs.getString("title"));
                ureg.setAddress(rs.getString("address"));
                ureg.setPostcode(rs.getString("postcode"));
                ureg.setDeptOfOlympic(rs.getString("deptOfOlympic"));
                ureg.setMemberTypeOfOlympic(rs.getString("memberTypeOfOlympic"));
                ureg.setEduinfo(rs.getString("eduinfo"));
                ureg.setTraninginfo(rs.getString("traninginfo"));
                ureg.setWorkinfo(rs.getString("workinfo"));
                ureg.setStartdate(rs.getTimestamp("startdate"));
                ureg.setEnddate(rs.getTimestamp("enddate"));
                ureg.setTitleInOlympic(rs.getString("titleInOlympic"));
                ureg.setSkills(rs.getString("skills"));
                ureg.setLocation(rs.getString("location"));
                list.add(ureg);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
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
        return list;
    }

    public int getUserTotalCountForOlympic(int type) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        int count = 0;
        String SQL_GET_ALLUSERS= "";

        if (type ==1)
            SQL_GET_ALLUSERS= "select count(*) from tbl_olympic_members where talentflag=1";
        else
            SQL_GET_ALLUSERS= "select count(*) from tbl_olympic_members";

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALLUSERS);
            rs = pstmt.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
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

        return count;
    }

    public List getDeptInfoForOlympic() throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        OlympicDept dept = null;
        List list = new ArrayList();

        String SQL_GET_ALL_DEPTINFO= "select engcode,cn_name from tbl_olympic_venue";

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_DEPTINFO);
            rs = pstmt.executeQuery();
            while(rs.next()){
                dept = new OlympicDept();
                dept.setChname(rs.getString("cn_name"));
                dept.setEnname(rs.getString("engcode"));
                list.add(dept);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
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
        return list;
    }

    public String getChineseDeptName(String engcode) throws UregisterException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        String cnname="";

        String SQL_GET_CHINESE_DEPT_NAME= "select cn_name from tbl_olympic_venue where engcode=?";

        try{
            int len = engcode.length();
            if (len == 3) {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_GET_CHINESE_DEPT_NAME);
                pstmt.setString(1,engcode);
                rs = pstmt.executeQuery();
                if (rs.next()){
                    cnname=rs.getString("cn_name");
                }
                rs.close();
                pstmt.close();
            } else {
                cnname=engcode;
            }
        }
        catch(Exception e){
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

        return cnname;
    }

    private static String GET_ROLE_EN_NAME = "select rolecat from tbl_role where roleid=?";

    public String getRole_English_NameByID(int roleid) throws UregisterException
    {
        String rolecat = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ROLE_EN_NAME);
            pstmt.setInt(1, roleid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rolecat = rs.getString("rolecat");
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    this.cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return rolecat;
    }
}
