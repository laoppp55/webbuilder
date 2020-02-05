package com.bizwink.cms.bjhqfw.shetuan;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.*;
import java.util.*;


public class SheTuanPeer implements ISheTuanManager{
    PoolServer cpool;

    public SheTuanPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ISheTuanManager getInstance() {
        return CmsServer.getInstance().getFactory().getSheTuanManager();
    }

    public List getAllSheTuan(String sql,int startIndex,int range){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();

        if(sql != null){
            sql = sql.replaceAll("@", "%");
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++){
                rs.next();
            }
            for(int i = 0; i < range && rs.next(); i++){
                SheTuan st = new SheTuan();
                st.setId(rs.getInt("id"));
                st.setStname(rs.getString("stname"));
                st.setLianxiren(rs.getString("lianxiren"));
                st.setPhone(rs.getString("phone"));
                st.setEmail(rs.getString("email"));
                st.setUsername(rs.getString("username"));
                st.setPasswd(rs.getString("passwd"));
                st.setCreatedate(rs.getTimestamp("createdate"));
                list.add(st);
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

    public int getAllSheTuanNum(String sql){
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        if(sql != null){
            sql = sql.replaceAll("@","%");
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public int createSheTuan(SheTuan st){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into tbl_shetuan(id,stname,lianxiren,phone,email,username,passwd,createdate) values(?,?,?,?,?,?,?,?)");
            pstmt.setInt(1,sMgr.getSequenceNum("Department"));
            pstmt.setString(2,st.getStname());
            pstmt.setString(3,st.getLianxiren());
            pstmt.setString(4,st.getPhone());
            pstmt.setString(5,st.getEmail());
            pstmt.setString(6,st.getUsername());
            pstmt.setString(7,st.getPasswd());
            pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    public SheTuan getByIdSheTuan(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        SheTuan st = new SheTuan();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_shetuan where id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                st.setStname(rs.getString("stname"));
                st.setLianxiren(rs.getString("lianxiren"));
                st.setPhone(rs.getString("phone"));
                st.setEmail(rs.getString("email"));
                st.setUsername(rs.getString("username"));
                st.setPasswd(rs.getString("passwd"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null)
                cpool.freeConnection(conn);
        }
        return st;
    }

    public int checkSheTuanUserExist(String username){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int existflag = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_shetuan where username = ?");
            pstmt.setString(1,username);
            rs = pstmt.executeQuery();
            if (rs.next()){
                existflag = 1;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null)
                cpool.freeConnection(conn);
        }

        return existflag;
    }


    public void updateSheTuan(SheTuan st,int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_shetuan set stname = ?,lianxiren = ?,phone = ?,email = ?,username = ?,passwd = ? where id = ?");
            pstmt.setString(1,st.getStname());
            pstmt.setString(2,st.getLianxiren());
            pstmt.setString(3,st.getPhone());
            pstmt.setString(4,st.getEmail());
            pstmt.setString(5,st.getUsername());
            pstmt.setString(6,st.getPasswd());
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
    }

    public void delShetuan(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_shetuan where id = ?");
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
    }

    //系统用户登录
    public SheTuan getSysLogin(String userid,String passwd)
    {
        SheTuan st = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_shetuan where username = ? and passwd = ?");
            pstmt.setString(1,userid);
            pstmt.setString(2,passwd);
            rs = pstmt.executeQuery();
            while(rs.next()){
                st = new SheTuan();
                st.setId(rs.getInt("id"));
                st.setStname(rs.getString("stname"));
                st.setLianxiren(rs.getString("lianxiren"));
                st.setPhone(rs.getString("phone"));
                st.setEmail(rs.getString("email"));
                st.setUsername(rs.getString("username"));
                st.setPasswd(rs.getString("passwd"));
                st.setCreatedate(rs.getTimestamp("createdate"));
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
        return st;
    }


}




























