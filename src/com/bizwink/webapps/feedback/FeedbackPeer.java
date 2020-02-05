package com.bizwink.webapps.feedback;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class FeedbackPeer implements IFeedbackManager {

    PoolServer cpool;

    public FeedbackPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IFeedbackManager getInstance() {
        return (IFeedbackManager) CmsServer.getInstance().getFactory().getFeedbackManager();
    }

    //create feedback info by feixiang 2009-02-26
    private static String CREATE_FEEDBACK_INFO_FOR_ORACLE = "insert into tbl_feedback(userid,email,phone,title,content,flag,siteid,id)" +
            " values(?,?,?,?,?,?,?,?)";

    private static String CREATE_FEEDBACK_INFO_FOR_MSSQL = "insert into tbl_feedback(userid,email,phone,title,content,flag,siteid)" +
            " values(?,?,?,?,?,?,?)";

    private static String CREATE_FEEDBACK_INFO_FOR_MYSQL = "insert into tbl_feedback(userid,email,phone,title,content,flag,siteid)" +
            " values(?,?,?,?,?,?,?)";

    private static final String SQL_GET_SITE_ID = "SELECT SiteID FROM TBL_SiteInfo WHERE SiteName = ? ORDER BY CreateDate DESC";

    public int createFeedbackInfo(FeedBack fd) {
        int id = 0;
        int code = 1;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_FEEDBACK_INFO_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_FEEDBACK_INFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_FEEDBACK_INFO_FOR_MYSQL);
            pstmt.setString(1, fd.getUserid());
            pstmt.setString(2, fd.getEmail());
            pstmt.setString(3, fd.getPhone());
            pstmt.setString(4, fd.getTitle());
            pstmt.setString(5, fd.getContent());
            pstmt.setInt(6, 0);
            pstmt.setInt(7, fd.getSiteid());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(8, sequenceMgr.getSequenceNum("FeedBack"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }

            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }

    //get a feedback info by feixiang 2009-02-26
    private static String GET_A_FEEDBACK_INFO = "SELECT * from tbl_feedback where id = ?";

    public FeedBack getAFeedbackInfo(int id) {
        FeedBack feedback = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_FEEDBACK_INFO);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                feedback = load_feedback(rs);
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
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return feedback;
    }

    //delete a feedback info by feixiang 2009-02-26
    private static String DELETE_A_FEEDBACK_INFO = "delete from tbl_feedback where id = ?";

    public int deleteAFeedbackInfo(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_A_FEEDBACK_INFO);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }

    //answer the feedback info by feixiang 2009-2-26

    private static String ANSWER_FEEDBACK_INFO = "update tbl_feedback set answer = ?,answertime = ? where id = ?";

    public int answerAFeedbackInfo(FeedBack feedback) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(ANSWER_FEEDBACK_INFO);
            pstmt.setString(1, feedback.getAnswer());
            pstmt.setTimestamp(2, feedback.getAnswertime());
            pstmt.setInt(3, feedback.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }

    //update feedback flag by feixiang 2009-02-26
    private static String UPDATE_FLAG = "update tbl_feedback set flag = ? where id = ?";

    public int answerAFeedbackInfoFlag(int flag, int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_FLAG);
            pstmt.setInt(1, flag);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }

    //get all feedback info by feiaxiang 2009-02-26
    public List getAllFeedbackInfo(int start, int range, String sql, String bgntime, String endtime) {
        List list = new ArrayList();
        FeedBack feedback = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            if (sql == null || sql.equals("") || sql.equals("null")) {
                sql = "select * from tbl_feedback order by createtime desc";
            } else {
                sql = sql.replaceAll("@", "%");
                if (bgntime != null && !bgntime.equals("null") && !bgntime.equals("")) {
                    if (cpool.getType().equals("oracle")) {
                        sql = sql + " and createtime >= TO_DATE('" + bgntime + " 00:00:00', 'YYYY-MM-DD HH24:MI:SS')";
                    } else {
                        sql = sql + " and createtime >= '" + bgntime + " 00:00:00'";
                    }
                }
                if (endtime != null && !endtime.equals("null") && !endtime.equals("")) {
                    if (cpool.getType().equals("oracle")) {
                        sql = sql + " and createtime <= TO_DATE('" + endtime + " 23:23:59', 'YYYY-MM-DD HH24:MI:SS')";
                    } else {
                        sql = sql + " and createtime <= '" + endtime + " 23:23:59'";
                    }
                }

                sql = sql + " order by createtime desc";
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for (int i = 0; i < start; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                feedback = load_feedback(rs);
                list.add(feedback);
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
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //get all feedback info number by feixiang 2009-02-26
    public int getAllFeedbackInfoNum(String sql, String bgntime, String endtime) {
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            if (sql == null || sql.equals("") || sql.equals("null")) {
                sql = "select count(id) from tbl_feedback";
            } else {
                sql = sql.replaceAll("@", "%");
                sql = sql.replaceAll("@", "%");
                if (bgntime != null && !bgntime.equals("null") && !bgntime.equals("")) {
                    if (cpool.getType().equals("oracle")) {
                        sql = sql + " and createtime >= TO_DATE('" + bgntime + " 00:00:00', 'YYYY-MM-DD HH24:MI:SS')";
                    } else {
                        sql = sql + " and createtime >= '" + bgntime + " 00:00:00'";
                    }
                }
                if (endtime != null && !endtime.equals("null") && !endtime.equals("")) {
                    if (cpool.getType().equals("oracle")) {
                        sql = sql + " and createtime <= TO_DATE('" + endtime + " 23:23:59', 'YYYY-MM-DD HH24:MI:SS')";
                    } else {
                        sql = sql + " and createtime <= '" + endtime + " 23:23:59'";
                    }
                }
            }
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
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
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return num;
    }

    private FeedBack load_feedback(ResultSet rs) {
        FeedBack feedback = new FeedBack();
        try {
            feedback.setAnswer(rs.getString("answer"));
            feedback.setAnswertime(rs.getTimestamp("answertime"));
            feedback.setContent(rs.getString("content"));
            feedback.setCreatetime(rs.getTimestamp("createtime"));
            feedback.setEmail(rs.getString("email"));
            feedback.setFlag(rs.getInt("flag"));
            feedback.setId(rs.getInt("id"));
            feedback.setPhone(rs.getString("phone"));
            feedback.setSiteid(rs.getInt("siteid"));
            feedback.setTitle(rs.getString("title"));
            feedback.setUserid(rs.getString("userid"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedback;
    }

    //get siteid by feixiang 2009-03-04
    public int getSiteID(String sitename){
        int siteid = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_SITE_ID);
            pstmt.setString(1,sitename);
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
        finally{
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return siteid;
    }
}