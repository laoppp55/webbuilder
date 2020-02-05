package com.bizwink.error;
/**
 * MarkerPeer.java
 */

import java.util.*;
import java.lang.*;
import java.sql.*;

import com.bizwink.cms.server.*;

/**
 * Title:        Cms server
 * Description:  bizwink Cms Server
 * Copyright:    Copyright (c) 2003
 * Company:      bizwink software inc
 *
 * @author EricDu
 * @version 1.0
 */

public class ErrorPeer implements IErrorManager {
    PoolServer cpool;

    public ErrorPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IErrorManager getInstance() {
        return CmsServer.getInstance().getFactory().getErrorManager();
    }

    public List reportPerDay(String theDate, String id) throws ErrorException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Error perday;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from view_Bad where LOGDATE = ? and userid = ? ORDER BY num desc");
                pstmt.setString(1, theDate);
                pstmt.setString(2, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    perday = load(rs);
                    list.add(perday);
                }
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List getCurrentErrorList(String theDate, int startIndex, int numResult, String id) throws ErrorException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Error error;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from view_Bad where LOGDATE = ? and userid = ? ORDER BY num desc");
            pstmt.setString(1, theDate);
            pstmt.setString(2, id);
            ResultSet rs;

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                error = load(rs);
                list.add(error);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    Error load(ResultSet rs) throws SQLException {
        Error detail = new Error();
        try {
            detail.setUrlName(rs.getString("urlname"));
            detail.setCode(rs.getString("code"));
            detail.setNum(rs.getLong("num"));
            detail.setEName(rs.getString("error_enname"));
            detail.setCHName(rs.getString("error_chname"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }
}
