package com.bizwink.cms.bjhqfw.jiben;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JiBenPeer implements IJiBenManager {
    PoolServer cpool;

    public JiBenPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IJiBenManager getInstance() {
        return CmsServer.getInstance().getFactory().getJiBenManager();
    }

    public List getAllListJiBen(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_jiben order by id asc");
            rs = pstmt.executeQuery();
            while(rs.next()){
                JiBen jb = new JiBen();
                jb.setId(rs.getInt("id"));
                jb.setMeetname(rs.getString("meetname"));
                jb.setMeetmax(rs.getString("meetmax"));
                jb.setMeetroot(rs.getString("meetroot"));
                list.add(jb);
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

    public List getAllJiBen(String sql,int startIndex,int range){
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
                JiBen jb = new JiBen();
                jb.setId(rs.getInt("id"));
                jb.setMeetname(rs.getString("meetname"));
                jb.setMeetmax(rs.getString("meetmax"));
                jb.setMeetroot(rs.getString("meetroot"));
                list.add(jb);
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

    public int getAllJiBenNum(String sql){
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
        } finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return num;
    }

    public int createJiBen(JiBen jb){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into tbl_jiben(id,meetname,meetmax,meetroot) values(?,?,?,?)");
            pstmt.setInt(1,sMgr.getSequenceNum("Meetroom"));
            pstmt.setString(2,jb.getMeetname());
            pstmt.setString(3,jb.getMeetmax());
            pstmt.setString(4,jb.getMeetroot());
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

    public JiBen getByIdJiBen(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        JiBen jb = new JiBen();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_jiben where id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                jb.setMeetname(rs.getString("meetname"));
                jb.setMeetmax(rs.getString("meetmax"));
                jb.setMeetroot(rs.getString("meetroot"));
                jb.setId(id);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null)
                cpool.freeConnection(conn);
        }
        return jb;
    }

    public void updateJiBen(JiBen jb,int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_jiben set meetname = ?,meetmax = ?,meetroot = ? where id = ?");
            pstmt.setString(1,jb.getMeetname());
            pstmt.setString(2,jb.getMeetmax());
            pstmt.setString(3,jb.getMeetroot());
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

    public void delJiBen(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_jiben where id = ?");
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

    private static final String SQL_INSERT_ACTIVITY = "insert into tbl_join_activity(siteid,activiid,userid,username,"+
            "email,phone,mphone,address,postcode,company,title,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int createActivity(Activity activity){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_INSERT_ACTIVITY);
            pstmt.setInt(1,activity.getSiteid());
            pstmt.setInt(2,activity.getActiviid());
            pstmt.setString(3,activity.getUserid());
            pstmt.setString(4,activity.getUsername());
            pstmt.setString(5,activity.getEmail());
            pstmt.setString(6,activity.getPhone());
            pstmt.setString(7,activity.getMphone());
            pstmt.setString(8,activity.getAddress());
            pstmt.setString(9,activity.getPostcode());
            pstmt.setString(10,activity.getCompany());
            pstmt.setString(11,activity.getTitle());
            pstmt.setInt(12,sMgr.getSequenceNum("Activity"));
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
            code = -1;
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    private static final String SQL_GET_USER_JOIN_STATUS = "select id from tbl_join_activity where activiid=? and userid=?";

    public int existInActivityByUserid(String userid,int activityid){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USER_JOIN_STATUS);
            pstmt.setInt(1,activityid);
            pstmt.setString(2,userid);
            rs = pstmt.executeQuery();
            if (rs.next()) code = 1;
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            code = -1;
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    public List getActivitys(int start, int range){
        List list = new ArrayList();
        Activity activity = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_activitys order by createtime desc");
            rs = pstmt.executeQuery();

            for(int i = 0; i < start; i++){
                rs.next();
            }

            for(int i = 0; i < range && rs.next(); i++){
                activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setTitle(rs.getString("title"));
                activity.setCreatetime(rs.getTimestamp("createtime"));
                list.add(activity);
            }

            /*while (rs.next()) {
                activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setTitle(rs.getString("title"));
                activity.setCreatetime(rs.getTimestamp("createtime"));
                list.add(activity);
            }*/
            rs.close();
            pstmt.close();
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

        return list;
    }

    public int getActivitysCount(){
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(id) from tbl_activitys");
            rs = pstmt.executeQuery();
            if (rs.next()) {
               count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
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

        return count;
    }

    public List getUsersByActivity(int activityid,int siteid,int start, int range){
        List list = new ArrayList();
        Activity activity = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id,username,email,phone,mphone,address,postcode,company,createtime from tbl_join_activity where siteid=? and activiid=? order by createtime desc");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,activityid);
            rs = pstmt.executeQuery();
            for(int i = 0; i < start; i++){
                rs.next();
            }

            for(int i = 0; i < range && rs.next(); i++){
                activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setUsername(rs.getString("username"));
                activity.setEmail(rs.getString("email"));
                activity.setPhone(rs.getString("phone"));
                activity.setMphone(rs.getString("mphone"));
                activity.setAddress(rs.getString("address"));
                activity.setPostcode(rs.getString("postcode"));
                activity.setCompany(rs.getString("company"));
                activity.setCreatetime(rs.getTimestamp("createtime"));
                list.add(activity);
            }

           /* while (rs.next()) {
                activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setTitle(rs.getString("title"));
                activity.setCreatetime(rs.getTimestamp("createtime"));
                list.add(activity);
            }*/
            rs.close();
            pstmt.close();
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

        return list;
    }

    public int getUsersCountByActivity(int activityid,int siteid){
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(id) from tbl_join_activity where siteid=? and activiid=?");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,activityid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
               count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
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

        return count;
    }
}
