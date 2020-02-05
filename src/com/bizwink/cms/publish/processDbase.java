package com.bizwink.cms.publish;

/**
 * <p>Title: BW-WebBuilder</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: Beijing Bizwink SoftwareInc</p>
 * @author Peter Song
 * @version 1.0
 */

import java.lang.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class processDbase {
    PoolServer cpool;

    public processDbase(PoolServer cpool) {
        this.cpool = cpool;
    }

    private static final String InsertPublishedArticle = "insert INTO TBL_PublishedArticle (ID,publishername,url,publishstatus,publishtime)" +
            "VALUES (?, ?, ?, ?, ?)";

    public int savePublishedArticle(String url, String username, Timestamp pt, int publishStatus) {
        int errcode = 0;
        Connection conn = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        int publishedArticleID;
        if (cpool.getType().equalsIgnoreCase("mssql"))
            publishedArticleID = sequnceMgr.nextID("PublishedArticle");
        else
            publishedArticleID = sequnceMgr.getSequenceNum("PublishedArticle");

        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(InsertPublishedArticle);

                pstmt.setInt(1, publishedArticleID);       //编号
                pstmt.setString(2, username);               //发布文章者的姓名
                pstmt.setString(3, url);                    //文章的URL
                pstmt.setInt(4, publishStatus);             //文章的发布状态  0：文章已经发布  1：文章需要发布  2：由于FTP问题未能发布
                pstmt.setTimestamp(5, pt);                  //文章将在什么时候被发布

                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                errcode = -1;
            } finally {
                if (conn != null) {
                    try {
                        // close the pooled connection
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errcode = -3;
        }

        return errcode;
    }

    private static final String UpdatePublishedArticle = "UPDATE TBL_PublishedArticle " +
            "SET publishstatus = ?,publishtime = ?,publishername = ? WHERE url = ?";

    public int updatePublshstatus(String url, String username, Timestamp pt, int status) {                   //修改被发布文章的状态
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(UpdatePublishedArticle);

                pstmt.setInt(1, status);               //发布文章者的姓名
                pstmt.setTimestamp(2, pt);                    //文章的发布状态  0：文章已经发布  1：文章需要发布  2：由于FTP问题未能发布
                pstmt.setString(3, username);                  //文章将在什么时候被发布
                pstmt.setString(4, url);

                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                errcode = -1;
            } finally {
                if (conn != null) {
                    try {
                        // close the pooled connection
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errcode = -3;
        }

        return errcode;
    }
}