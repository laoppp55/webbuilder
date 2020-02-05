package com.bizwink.cms.util;

import java.sql.*;
import java.util.Calendar;

public class CMSLogUtil {
    private static final String SQL_CREATE_LOG_FOR_ORACLE = "INSERT INTO TBL_Log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate,id) VALUES (?, " +
            "?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MSSQL = "INSERT INTO TBL_Log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, " +
            "?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MYSQL = "INSERT INTO TBL_Log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, " +
            "?, ?, ?, ?, ?, ?, ?)";

    public static int writeLogToDB(int siteID,int columnID,int articleID,String maintitle,String editor,String dbtype,Connection conn) {
        int errcode = 0;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR) - 1900;
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);

            if (dbtype.equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
            else if (dbtype.equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, columnID);
            pstmt.setInt(3, articleID);
            pstmt.setString(4, editor);
            pstmt.setInt(5, 3);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(7, maintitle);
            pstmt.setDate(8, new Date(year, month, date));
            if (dbtype.equals("oracle")) {
                pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                pstmt.executeUpdate();
            } else if (dbtype.equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();
        } catch (SQLException sqlexp) {
            errcode = -4;
            sqlexp.printStackTrace();
        }

        return errcode;
    }
}
