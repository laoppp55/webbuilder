package com.heaton.bot;

import java.sql.*;
import java.util.List;

public class SaveContent {

    public SaveContent() {
    }

    private static String insertContentSQL = "INSERT INTO TBL_Article (ColumnID,MainTitle,Content," +
            "Emptycontentflag,CreateDate,Lastupdated,FileName,DirName,editor,status,doclevel,pubflag," +
            "auditflag,subscriber,lockstatus,ispublished,indexflag,Publishtime,ID) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_GETCOLUMN = "SELECT dirname FROM tbl_column WHERE ID = ?";

    synchronized public String save(List columns, String title, String content, Timestamp date, String url, int status) {
        Connection conn = null;
        PreparedStatement pstmt;
        String dirname = "";

        int nextID = 0;
        if (title == null || title.equals("")) title = "新闻";
        if (content == null || content.equals("")) content = "";
        try {
            conn = Server.createConnection();

            pstmt = conn.prepareStatement("select max(id) from tbl_article");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) nextID = rs.getInt(1) + 1;
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GETCOLUMN);
            pstmt.setInt(1, 11);                     //栏目ID=11,通过全文检索，最终获取文章应该保存的栏目ID
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dirname = rs.getString(1);
            }
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(insertContentSQL);
            pstmt.setInt(1, 11);                     //栏目ID=11,通过全文检索，最终获取文章应该保存的栏目ID
            pstmt.setString(2, StringUtil.iso2gb(title));                //文章标题
            if (content != null)
                //DBUtil.setBigString("oracle", insertContent, 3, content);
                pstmt.setString(3, StringUtil.iso2gb(content));
            else
                pstmt.setNull(3, java.sql.Types.LONGVARCHAR);      //文章内容
            pstmt.setInt(4, 0);                       //内容不为空
            pstmt.setTimestamp(5, date);                //文章创建时间
            pstmt.setTimestamp(6, date);                //文章发布时间
            pstmt.setString(7, "");                  //文章URL
            pstmt.setString(8, dirname);             //文章的路径
            pstmt.setString(9, "spider");             //文章的路径
            pstmt.setInt(10, status);                      //文章状态
            pstmt.setInt(11, 0);                      //文章权重
            pstmt.setInt(12, 1);                      //文章发布标识位
            pstmt.setInt(13, 0);                      //不需要审核
            pstmt.setInt(14, 1);                      //文章被订阅
            pstmt.setInt(15, 0);                      //文章是否被锁定
            pstmt.setInt(16, 0);                      //文章是否被发布过
            pstmt.setInt(17, 0);                      //文章是否被加入索引
            pstmt.setTimestamp(18, date);               //文章的发布时间
            pstmt.setInt(19, nextID);                 //文章ID
            pstmt.executeUpdate();

            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.logException("SQL Error: ", e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

