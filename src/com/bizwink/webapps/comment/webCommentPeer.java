package com.bizwink.webapps.comment;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;

public class webCommentPeer implements IWebCommentManager {
    public static final String MANAGER_NAME = "CommentManager";
    PoolServer cpool;

    public webCommentPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IWebCommentManager getInstance() {
        return CmsServer.getInstance().getFactory().getWebCommentManager();
    }

    private static final String SQL_INSERT_COMMENT_FOR_ORACLE =
            "insert into TBL_comment (NAME,LINK,CONTENT,IP,ABOUT,CREATEDATE,siteid,userid,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_COMMENT_FOR_MSSQL =
            "insert into TBL_comment (NAME,LINK,CONTENT,IP,ABOUT,CREATEDATE,siteid,userid) values(?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_COMMENT_FOR_MYSQL =
            "insert into TBL_comment (NAME,LINK,CONTENT,IP,ABOUT,CREATEDATE,siteid,userid) values(?, ?, ?, ?, ?, ?, ?, ?)";

    public int createComment(webComment comment) throws webCommentException {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        int id = 0;

        try {
            try {
                conn = cpool.getConnection();
                String name = comment.getName();
                String link = comment.getLink();
                String content = comment.getContent();

                conn.setAutoCommit(false);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_INSERT_COMMENT_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_INSERT_COMMENT_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_INSERT_COMMENT_FOR_MYSQL);
                pstmt.setString(1, name);
                pstmt.setString(2, link);
                pstmt.setString(3, content);
                pstmt.setString(4, comment.getIP());
                pstmt.setInt(5, comment.getAbout());
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(7, comment.getSiteid());
                pstmt.setString(8,comment.getUsrid());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(9, sequenceMgr.getSequenceNum("Comment"));
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
                e.printStackTrace();
                conn.rollback();
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    //get all comments by feixiang 2009-03-06
    public List getAllcommentInfo(String sql, int start, int range){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
             for (int i = 0; i < start; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                webComment comment = load(rs);
                list.add(comment);
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
        return list;
    }

    //get all comment number by feixiang 2009-03-06
    public int getAllCommentNum(String sql){
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                num = rs.getInt(1);
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
        return num;
    }

    //delete a comment by feixiang 2009-03-07
    private static String DELETE_A_COMMENT_INFO = "delete from tbl_comment where id = ?";
    public int deleteACommentInfo(int id){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try{
             conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_A_COMMENT_INFO);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch(Exception e){
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
        return code;
    }
    private webComment load(ResultSet rs) {
        webComment comment = new webComment();
        try {
            comment.setAbout(rs.getInt("ABOUT"));
            comment.setName(rs.getString("NAME"));
            comment.setLink(rs.getString("LINK"));
            comment.setContent(rs.getString("CONTENT"));
            comment.setIP(rs.getString("IP"));
            comment.setCreateDate(rs.getTimestamp("CREATEDATE"));
            comment.setId(rs.getInt("ID"));
            comment.setSiteid(rs.getInt("siteid"));
            comment.setUsrid(rs.getString("userid"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return comment;
    }
}
