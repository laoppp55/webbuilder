package com.bizwink.dubboservice;

import com.bizwink.cms.util.StringUtil;
import com.jolbox.bonecp.BoneCPDataSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 18-4-21.
 */
public class DBConnUtil {
    public Connection getConn() {
        BoneCPDataSource dataSource = (BoneCPDataSource)dubboService.context.getBean("myDataSource");
        Connection conn= null;
        try {
            conn= dataSource.getConnection();
        }catch(SQLException exp) {
            exp.printStackTrace();
        }

        return conn;
    }

    public static void setBigString(String type, PreparedStatement pstmt, int index, String content) throws SQLException {
        if (content == null || content.length() == 0) {
            pstmt.setNull(index, java.sql.Types.VARCHAR);
            return;
        }

        if (type.equalsIgnoreCase("oracle"))
            setOracleBigString(pstmt, index, content);
        else if (type.equalsIgnoreCase("mssql") || type.equalsIgnoreCase("sybase"))
            setSQLServerBigString(pstmt, index, content);
        else                                            //defalut, change if have new database
            setOracleBigString(pstmt, index, content);
    }

    private static void setOracleBigString(PreparedStatement pstmt, int index, String content) throws SQLException {
        String strText = StringUtil.iso2gb(content);
        pstmt.setCharacterStream(index, new StringReader(strText), strText.length());
    }

    private static void setSQLServerBigString(PreparedStatement pstmt, int index, String content) throws SQLException {
        pstmt.setString(index, StringUtil.iso2gb(content));
    }

    public static String getBigString(String type, ResultSet rs, String fieldName) throws Exception {
        String content = null;
        if (type.equalsIgnoreCase("oracle"))
            content = getOracleBigString(rs, fieldName);
        else if (type.equalsIgnoreCase("mssql") || type.equalsIgnoreCase("sybase"))
            content = getSQLServerBigString(rs, fieldName);
        else
            content = getOracleBigString(rs, fieldName);

        return content;
    }

    private static String getSQLServerBigString(ResultSet rs, String fieldName) throws Exception {
        return rs.getString(fieldName);
    }

    private static String getOracleBigString(ResultSet rs, String fieldName) throws Exception {
        String content = "";
        if (rs.getCharacterStream(fieldName) != null) {
            BufferedReader is = new BufferedReader(rs.getCharacterStream(fieldName));
            char[] buffer = new char[1000];
            int length = 0;
            while ((length = is.read(buffer)) != -1) {
                String str = new String(buffer, 0, length);
                content = content + str;
            }
            is.close();
        }
        return content;
    }


    private static final String SEQ_NEXT_ARTICLE_ID = "select tbl_article_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_TEMPLATE_ID = "select tbl_template_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_PUBLISH_QUEUE_ID = "select tbl_new_publish_queue_id.NEXTVAL from dual";

    public long getMainKeyID(String keyflag) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        long nextID = 0l;

        try {
            DBConnUtil dbUtil = new DBConnUtil();
            conn = dbUtil.getConn();
            if (keyflag.equalsIgnoreCase("article"))
                pstmt = conn.prepareStatement(SEQ_NEXT_ARTICLE_ID);
            else if (keyflag.equalsIgnoreCase("template"))
                pstmt = conn.prepareStatement(SEQ_NEXT_TEMPLATE_ID);
            else if (keyflag.equalsIgnoreCase("PublishQueue"))
                pstmt = conn.prepareStatement(SEQ_NEXT_PUBLISH_QUEUE_ID);
            rs = pstmt.executeQuery();
            if (rs.next()) nextID = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch(SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException sexp) {
                sexp.printStackTrace();
            }
        }

        return nextID;
    }
}
