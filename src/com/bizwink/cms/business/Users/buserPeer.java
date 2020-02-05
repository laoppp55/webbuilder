package com.bizwink.cms.business.Users;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.Encrypt;

public class buserPeer implements IBUserManager {
    PoolServer cpool;

    public buserPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IBUserManager getInstance() {
        return CmsServer.getInstance().getFactory().getBUserManager();
    }

    public void updateUserInfo(BUser buser) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        String sqlstr = "update tbl_members set userid=?,email=?,sex=?,occupation=?,mphone=?,address=?,postcode=?," +
                "education=?,phone=? where userid=?";

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setString(1, buser.getUserName());
                pstmt.setString(2, buser.getEmail());
                pstmt.setInt(3, buser.getSex());
                pstmt.setString(4, buser.getOccupation());
                pstmt.setString(5, buser.getMobilePhone());
                pstmt.setString(6, buser.getAddress());
                pstmt.setString(7, buser.getPostCode());
                pstmt.setInt(8, buser.getEducation());
                pstmt.setString(9, buser.getPhone());
                pstmt.setString(10, buser.getUserID());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public BUser getAUsers(String userid,int siteid) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        BUser buser = new BUser();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_members where siteid=? and userid = ?");
            pstmt.setInt(1, siteid);
            pstmt.setString(2, userid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                buser = load_user(rs);
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
        return buser;
    }

    public List getBusinessUserList(int siteid,int startIndex, int range, String sqlstr) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs=null;
        BUser buser=null;

        List list = new ArrayList();
        if(cpool.getType().equals("oracle"))
            sqlstr = "select * from (select a.*, ROWNUM RN  from( select * from tbl_members where usertype=5 and siteid=" + siteid + " order by createdate desc) a) where RN between ? and ?";
        else if (cpool.getType().equals("mssql")) {
            sqlstr = "select * from tbl_members where (usertype=6 or usertype=7) and siteid=" + siteid + " order by createdate desc";
        } else {
            sqlstr = "select * from tbl_members where (usertype=6 or usertype=7) and siteid=" + siteid + " order by createdate desc limit ?,?";
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1,startIndex);
            pstmt.setInt(2,range);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                buser = load_user(rs);
                list.add(buser);
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
        return list;
    }

    public List getBUserList(int siteid,int startIndex, int range, String sqlstr) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs=null;
        BUser buser=null;

        List list = new ArrayList();
        if(cpool.getType().equals("oracle"))
            sqlstr = "select * from (select a.*, ROWNUM RN  from( select * from tbl_members where usertype=5 and siteid=" + siteid + " order by createdate desc) a) where RN between ? and ?";
        else if (cpool.getType().equals("mssql")) {
            sqlstr = "select * from tbl_members where usertype=5 and siteid=" + siteid + " order by createdate desc";
        } else {
            sqlstr = "select * from tbl_members where usertype=5 and siteid=" + siteid + " order by createdate desc limit ?,?";
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1,startIndex);
            pstmt.setInt(2,range);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                buser = load_user(rs);
                list.add(buser);
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
        return list;
    }

    public int getTotalUsers(int siteid,int flag) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int userCount = 0;

        List list = new ArrayList();
        String sqlstr = "select count(*) from tbl_members where usertype=5 and siteid=" + siteid;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (rs.next()) userCount = rs.getInt(1);
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
        return userCount;
    }

    public int getTotalBusinessUsers(int siteid,int flag) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int userCount = 0;

        List list = new ArrayList();
        String sqlstr = "select count(*) from tbl_members where (usertype=6 or usertype=7) and siteid=" + siteid;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (rs.next()) userCount = rs.getInt(1);
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
        return userCount;
    }

    BUser load_user(ResultSet rs) throws BUserException {
        BUser buser = new BUser();
        try {
            buser.setID(rs.getInt("id"));
            buser.setUserID(rs.getString("userid"));
            buser.setUserName(rs.getString("nickname"));
            // buser.setPassword(rs.getString("password"));
            buser.setSex(rs.getInt("sex"));
            buser.setAddress(rs.getString("address"));
            buser.setPostCode(rs.getString("postcode"));
            buser.setProvince(rs.getInt("province"));
            buser.setEmail(rs.getString("email"));
            buser.setLevel(rs.getInt("usertype"));
            buser.setScore(rs.getInt("score"));
            buser.setMobilePhone(rs.getString("mphone"));
            buser.setPhone(rs.getString("phone"));
            buser.setLockFlag(rs.getInt("dflag"));
            buser.setCreateDate(rs.getTimestamp("createdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buser;
    }

    public void updateLockFlag(int flag, String userid,int siteid) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt;
        //System.out.println("memberid=" + userid);
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_members set dflag = ? where siteid=? and userid = ?");
                pstmt.setInt(1, flag);
                pstmt.setInt(2, siteid);
                pstmt.setString(3, userid);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String pass, String userid) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt;
        String sqlstr = "update tbl_userinfo set password = ? where userid=?";
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setString(1, pass);
                pstmt.setString(2, userid);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updatePassword(String pass, String userid,int siteid) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 0;
        String sqlstr = "update tbl_members set userpwd = ? where userid=? and siteid=?";
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setString(1, Encrypt.md5(pass.getBytes()));
                pstmt.setString(2, userid);
                pstmt.setInt(3,siteid);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                errcode = -1;
                conn.rollback();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return errcode;
    }

    public void delUser(int userid,int siteid) throws BUserException {
        String sqlstr = "delete from tbl_members where siteid=? and id=?";
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, userid);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List searchUserList(int siteid,int startIndex,int pagesize,BUser user) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        BUser buser=null;
        String sqlstr = null;
        String search_where_clause = "";

        if (user.getUserID()!=null) search_where_clause = search_where_clause + " and userid like '%" + user.getUserID() + "%'";
        if (user.getRealName()!=null) search_where_clause = search_where_clause + " and nickname like '%" + user.getRealName() + "%'";
        if (user.getAddress()!=null) search_where_clause = search_where_clause + " and address like '%" + user.getAddress() + "%'";
        if (user.getEmail()!=null) search_where_clause = search_where_clause + " and email like '%" + user.getEmail() + "%'";
        if (user.getSex()!=-1) search_where_clause = search_where_clause + " and sex=" + user.getSex();
        if (user.getOccupation()!=null) search_where_clause = search_where_clause + " and occupation like '%" + user.getOccupation() + "%'";
        if (user.getEducation()!=-1) search_where_clause = search_where_clause + " and education=" + user.getEducation();
        if (user.getMobilePhone()!=null) search_where_clause = search_where_clause + " and mphone='" + user.getMobilePhone() + "'";

        List list = new ArrayList();
        if(cpool.getType().equals("oracle"))
            sqlstr = "select * from (select a.*, ROWNUM RN  from( select * from tbl_members where usertype=5 and siteid=" + siteid + search_where_clause + " order by createdate desc) a) where RN between ? and ?";
        else if (cpool.getType().equals("mssql")) {
            sqlstr = "select * from tbl_members where usertype=5 and siteid=" + siteid + search_where_clause + " order by createdate desc";
        } else {
            sqlstr = "select * from tbl_members where usertype=5 and siteid=" + siteid + search_where_clause + " order by createdate desc limit ?,?";
        }

        System.out.println("search SQL=" + sqlstr);

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1,startIndex);
            pstmt.setInt(2,pagesize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                buser = load_user(rs);
                list.add(buser);
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
        return list;
    }

    public int searchUserListCount(int siteid,BUser user) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        BUser buser=null;
        String sqlstr = null;
        String search_where_clause = "";
        int count = 0;

        if (user.getUserID()!=null) search_where_clause = search_where_clause + " and userid like '%" + user.getUserID() + "%'";
        if (user.getRealName()!=null) search_where_clause = search_where_clause + " and nickname like '%" + user.getRealName() + "%'";
        if (user.getAddress()!=null) search_where_clause = search_where_clause + " and address like '%" + user.getAddress() + "%'";
        if (user.getEmail()!=null) search_where_clause = search_where_clause + " and email like '%" + user.getEmail() + "%'";
        if (user.getSex()!=-1) search_where_clause = search_where_clause + " and sex=" + user.getSex();
        if (user.getOccupation()!=null) search_where_clause = search_where_clause + " and occupation like '%" + user.getOccupation() + "%'";
        if (user.getEducation()!=-1) search_where_clause = search_where_clause + " and education=" + user.getEducation();
        if (user.getMobilePhone()!=null) search_where_clause = search_where_clause + " and mphone='" + user.getMobilePhone() + "'";

        List list = new ArrayList();
        if(cpool.getType().equals("oracle"))
            sqlstr = "select count(id) from tbl_members where usertype=5 and siteid=" + siteid + search_where_clause;
        else if (cpool.getType().equals("mssql")) {
            sqlstr = "select count(id) from tbl_members where usertype=5 and siteid=" + siteid + search_where_clause;
        } else {
            sqlstr = "select count(id) from tbl_members where usertype=5 and siteid=" + siteid + search_where_clause;
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
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
        return count;
    }

    public List searchBusinessUserList(int siteid,int startIndex,int pagesize,BUser user) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        BUser buser=null;
        String sqlstr = null;
        String search_where_clause = "";

        if (user.getUserID()!=null) search_where_clause = search_where_clause + " and userid like '%" + user.getUserID() + "%'";
        if (user.getRealName()!=null) search_where_clause = search_where_clause + " and nickname like '%" + user.getRealName() + "%'";
        if (user.getAddress()!=null) search_where_clause = search_where_clause + " and address like '%" + user.getAddress() + "%'";
        if (user.getEmail()!=null) search_where_clause = search_where_clause + " and email like '%" + user.getEmail() + "%'";
        if (user.getSex()!=-1) search_where_clause = search_where_clause + " and sex=" + user.getSex();
        if (user.getOccupation()!=null) search_where_clause = search_where_clause + " and occupation like '%" + user.getOccupation() + "%'";
        if (user.getEducation()!=-1) search_where_clause = search_where_clause + " and education=" + user.getEducation();

        List list = new ArrayList();
        if(cpool.getType().equals("oracle"))
            sqlstr = "select * from (select a.*, ROWNUM RN  from( select * from tbl_members where （usertype=7 or usertype=6） and siteid=" + siteid + search_where_clause + " order by createdate desc) a) where RN between ? and ?";
        else if (cpool.getType().equals("mssql")) {
            sqlstr = "select * from tbl_members where （usertype=7 or usertype=6） and siteid=" + siteid + search_where_clause + " order by createdate desc";
        } else {
            sqlstr = "select * from tbl_members where （usertype=7 or usertype=6） and siteid=" + siteid + search_where_clause + " order by createdate desc limit ?,?";
        }

        System.out.println("search SQL=" + sqlstr);

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1,startIndex);
            pstmt.setInt(2,pagesize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                buser = load_user(rs);
                list.add(buser);
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
        return list;
    }

    public int searchBusinessUserListCount(int siteid,BUser user) throws BUserException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        BUser buser=null;
        String sqlstr = null;
        String search_where_clause = "";
        int count = 0;

        if (user.getUserID()!=null) search_where_clause = search_where_clause + " and userid like '%" + user.getUserID() + "%'";
        if (user.getRealName()!=null) search_where_clause = search_where_clause + " and nickname like '%" + user.getRealName() + "%'";
        if (user.getAddress()!=null) search_where_clause = search_where_clause + " and address like '%" + user.getAddress() + "%'";
        if (user.getEmail()!=null) search_where_clause = search_where_clause + " and email like '%" + user.getEmail() + "%'";
        if (user.getSex()!=-1) search_where_clause = search_where_clause + " and sex=" + user.getSex();
        if (user.getOccupation()!=null) search_where_clause = search_where_clause + " and occupation like '%" + user.getOccupation() + "%'";
        if (user.getEducation()!=-1) search_where_clause = search_where_clause + " and education=" + user.getEducation();

        List list = new ArrayList();
        if(cpool.getType().equals("oracle"))
            sqlstr = "select count(id) from tbl_members where （usertype=7 or usertype=6） and siteid=" + siteid + search_where_clause;
        else if (cpool.getType().equals("mssql")) {
            sqlstr = "select count(id) from tbl_members where （usertype=7 or usertype=6） and siteid=" + siteid + search_where_clause;
        } else {
            sqlstr = "select count(id) from tbl_members where （usertype=7 or usertype=6） and siteid=" + siteid + search_where_clause;
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
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
        return count;
    }
}