package com.bizwink.cms.util;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.publishx.Publish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: petersong
 * Date: 14-5-18
 * Time: 下午8:58
 * To change this template use File | Settings | File Templates.
 */
public class writePublishQueue {

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,TYPE,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,TYPE,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,TYPE,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static void writeTemplateToPublishQueue(int siteid,int columnID,String dbtype,PreparedStatement pstmt,Connection conn) throws Exception {
        //向发布队列写入需要发布的栏目模板
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        List queueList = new ArrayList();
        Publish publish=null;
        String ids = "";
        String SQL_Select_Model = "SELECT id,columnid,chname,isarticle FROM tbl_template WHERE isarticle <> 1 AND siteid=? AND (relatedcolumnid LIKE '%," + columnID + ",%' OR relatedcolumnid LIKE '%(" + columnID + ",%' OR relatedcolumnid LIKE '%," + columnID + ")%' OR relatedcolumnid LIKE '%(" + columnID + ")%')";
        pstmt = conn.prepareStatement(SQL_Select_Model);
        pstmt.setInt(1, siteid);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
                publish = new Publish();
                publish.setSiteID(siteid);
                publish.setTargetID(rs.getInt("id"));
                publish.setColumnID(rs.getInt("columnid"));
                publish.setTitle(rs.getString("chname"));
                publish.setPriority(2);                                                      //栏目模板发布作业的优先级设置为2
                publish.setPublishDate(new Timestamp(System.currentTimeMillis()));
                if (rs.getInt(("isarticle")) == 0 || rs.getInt(("isarticle")) == 3)        //栏目模板和专题模板
                    publish.setObjectType(3);
                else if (rs.getInt(("isarticle")) == 1)                                   //首页模板
                    publish.setObjectType(2);
                queueList.add(publish);
            ids += rs.getString(1) + ",";
        }
        rs.close();
        pstmt.close();

        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
            String SQL_Update_ModelPubflag = "UPDATE tbl_template SET status = 0 WHERE id IN (" + ids + ")";
            pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
            pstmt.executeUpdate();
            pstmt.close();
        }

        if (dbtype.equalsIgnoreCase("oracle"))
            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
        else if (dbtype.equalsIgnoreCase("mssql"))
            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
        else
            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
        for (int i = 0; i < queueList.size(); i++) {
            publish = (Publish) queueList.get(i);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnID);
            pstmt.setInt(3, publish.getTargetID());
            pstmt.setInt(4, publish.getObjectType());
            pstmt.setInt(5, 1);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(7, publish.getPublishDate());
            pstmt.setString(8, "");
            pstmt.setString(9, publish.getTitle());
            pstmt.setInt(10, publish.getPriority());
            if (dbtype.equals("oracle")) {
                pstmt.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                pstmt.executeUpdate();
            } else if (dbtype.equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
        }
        pstmt.close();
    }

    public static void writeArticleToPublishQueue(int siteid,int columnID,Article article,String dbtype,PreparedStatement pstmt,Connection conn) throws Exception {

    }

    public static void writeMoreArticleToPublishQueue(List joblist,int siteid,int columnID,String dbtype,PreparedStatement pstmt,Connection conn) throws Exception {
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        if (dbtype.equalsIgnoreCase("oracle"))
            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
        else if (dbtype.equalsIgnoreCase("mssql"))
            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
        else
            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
        for (int i = 0; i < joblist.size(); i++) {
            Publish publish = (Publish) joblist.get(i);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnID);
            pstmt.setInt(3, publish.getTargetID());
            pstmt.setInt(4, publish.getObjectType());
            pstmt.setInt(5, 1);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(7, publish.getPublishDate());
            pstmt.setString(8, "");
            pstmt.setString(9, publish.getTitle());
            pstmt.setInt(10,publish.getPriority());
            if (dbtype.equals("oracle")) {
                pstmt.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                pstmt.executeUpdate();
            } else if (dbtype.equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
        }
        pstmt.close();
    }
}
