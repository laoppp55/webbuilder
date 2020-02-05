package com.bizwink.cms.util;

import java.io.*;
import java.sql.*;

public class DBUtil {
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

    public  static Connection createConnection(String dbip, String dbusername, String dbpassword, String dbname,int port,int flag) {
        Connection conn = null;

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":" + port, dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port  + ":"+dbname, dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":" + port + "/" + dbname + "?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return conn;
    }
}