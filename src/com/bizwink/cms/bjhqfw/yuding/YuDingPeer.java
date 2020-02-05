package com.bizwink.cms.bjhqfw.yuding;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class YuDingPeer implements IYuDingManager{
    PoolServer cpool;

    public YuDingPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IYuDingManager getInstance() {
        return CmsServer.getInstance().getFactory().getYuDingManager();
    }

    public List getAllYuDing(Timestamp ksdate,Timestamp jsdate,int startIndex,int range){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        String sqlstr = "select id,ydperson,jbxinxiid,khdate,jsdate,flag,shperson,shdate from tbl_yuding where khdate >= ? and khdate <= ?";

        //if(sql != null){
        //    sql = sql.replaceAll("@", "%");
        //}

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setTimestamp(1,ksdate);
            pstmt.setTimestamp(2,jsdate);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++){
                rs.next();
            }
            for(int i = 0; i < range && rs.next(); i++){
                YuDing yd = new YuDing();
                yd.setId(rs.getInt("id"));
                yd.setYdperson(rs.getString("ydperson"));
                yd.setJbxinxiid(rs.getString("jbxinxiid"));
                yd.setKhdate(rs.getTimestamp("khdate"));
                yd.setJsdate(rs.getTimestamp("jsdate"));
                yd.setFlag(rs.getInt("flag"));
                yd.setShperson(rs.getString("shperson"));
                yd.setShdate(rs.getTimestamp("shdate"));
                list.add(yd);
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

    public List getAllYuDing(String sql,int startIndex,int range){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        //String sqlstr = "select id,ydperson,jbxinxiid,khdate,jsdate,flag,shperson,shdate from tbl_yuding where khdate >= ? and khdate <= ?";

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
                YuDing yd = new YuDing();
                yd.setId(rs.getInt("id"));
                yd.setYdperson(rs.getString("ydperson"));
                yd.setJbxinxiid(rs.getString("jbxinxiid"));
                yd.setKhdate(rs.getTimestamp("khdate"));
                yd.setJsdate(rs.getTimestamp("jsdate"));
                yd.setFlag(rs.getInt("flag"));
                yd.setShperson(rs.getString("shperson"));
                yd.setShdate(rs.getTimestamp("shdate"));
                list.add(yd);
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

    public int getAllYuDingNum(String sql){
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

    public int createYuDing(YuDing yd){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            int id = sMgr.getSequenceNum("Yuding");
            pstmt = conn.prepareStatement("insert into tbl_yuding(id,ydperson,jbxinxiid,khdate,jsdate) values(?,?,?,?,?)");
            pstmt.setInt(1,id);
            pstmt.setString(2,yd.getYdperson());
            pstmt.setString(3,yd.getJbxinxiid());
            pstmt.setTimestamp(4,yd.getKhdate());
            pstmt.setTimestamp(5,yd.getJsdate());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            code = -1;
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

    public YuDing getByIdYuDing(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        YuDing yd = new YuDing();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_yuding where id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                yd.setYdperson(rs.getString("ydperson"));
                yd.setJbxinxiid(rs.getString("jbxinxiid"));
                yd.setKhdate(rs.getTimestamp("khdate"));
                yd.setJsdate(rs.getTimestamp("jsdate"));
                yd.setFlag(rs.getInt("flag"));
                yd.setShperson(rs.getString("shperson"));
                yd.setShdate(rs.getTimestamp("shdate"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null)
                cpool.freeConnection(conn);
        }
        return yd;
    }

    public void updateYuDing(YuDing yd,int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_yuding set ydperson = ?,jbxinxiid = ?,khdate = ?, jsdate = ? where id = ?");
            pstmt.setString(1,yd.getYdperson());
            pstmt.setString(2,yd.getJbxinxiid());
            pstmt.setTimestamp(3,yd.getKhdate());
            pstmt.setTimestamp(4,yd.getJsdate());
            pstmt.setInt(5, id);
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

    public int updateYuDing(YuDing yd){
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_yuding set ydperson = ?,jbxinxiid = ?,khdate = ?, jsdate = ?,flag = ?,shperson = ?,shdate = ? where id = ?");
            pstmt.setString(1,yd.getYdperson());
            pstmt.setString(2,yd.getJbxinxiid());
            pstmt.setTimestamp(3,yd.getKhdate());
            pstmt.setTimestamp(4,yd.getJsdate());
            pstmt.setInt(5,yd.getFlag());
            pstmt.setString(6,yd.getShperson());
            pstmt.setTimestamp(7,yd.getShdate());
            pstmt.setInt(8, yd.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            errcode = -1;
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

        return errcode;
    }

    //审核会议
    public void shenheYuDing(YuDing yd,int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_yuding set flag = ?,shperson = ?,shdate = ? where id = ?");
            pstmt.setInt(1,yd.getFlag());
            pstmt.setString(2,yd.getShperson());
            pstmt.setTimestamp(3,yd.getShdate());
            pstmt.setInt(4, id);
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

    public void delYuDing(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_yuding where id = ?");
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

    public int delYuDing(YuDing yd){
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_yuding where id = ?");
            pstmt.setInt(1,yd.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            errcode = -1;
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }

        return errcode;
    }

    //审核
    public void updateshenheYuDing(YuDing yd, int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_yuding set flag=?,shperson=?,shdate=? where id=?");
            pstmt.setInt(1, yd.getFlag());
            pstmt.setString(2, yd.getShperson());
            pstmt.setTimestamp(3,yd.getShdate());
            pstmt.setInt(4, id);
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

    //private String COUNT_SQL = "select count(id) from tbl_yuding where jbxinxiid = ? and khdate < to_date(? ,'yyyy-mm-dd hh24:mi:ss')" +
    //private String COUNT_SQL = "select count(id) from tbl_yuding where jbxinxiid = ? and" +
    //        "(khdate < ? and jsdate > ?) or (khdate < ? and jsdate > ?)";
    //"and jsdate > to_date(? ,'yyyy-mm-dd hh24:mi:ss') or khdate < to_date(? ,'yyyy-mm-dd hh24:mi:ss')" +
    //"and jsdate > to_date(? ,'yyyy-mm-dd hh24:mi:ss')";

    //    "khdate < to_date(? ,'yyyy-mm-dd hh24:mi:ss')" +
    //    "and jsdate > to_date(? ,'yyyy-mm-dd hh24:mi:ss') or khdate < to_date(? ,'yyyy-mm-dd hh24:mi:ss')" +
    //    "and jsdate > to_date(? ,'yyyy-mm-dd hh24:mi:ss')";

    private String COUNT_SQL = "select count(id) from tbl_yuding where jbxinxiid = ? and khdate > ? and jsdate < ?";

    //检查是否预定某会议室
    public int getCountYuDing(String jbxinxiid,Timestamp ksdate,Timestamp jsdate){
        int count = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(COUNT_SQL);
            pstmt.setString(1,jbxinxiid);
            pstmt.setTimestamp(2,ksdate);
            pstmt.setTimestamp(3,jsdate);
            rs = pstmt.executeQuery();
            if(rs.next())
                count = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            count = -1;
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return count;
    }
}
