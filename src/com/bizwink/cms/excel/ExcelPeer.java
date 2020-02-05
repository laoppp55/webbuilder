package com.bizwink.cms.excel;

import java.sql.*;

import com.bizwink.cms.server.*;

public class ExcelPeer implements IExcelManager {
    PoolServer cpool;

    public ExcelPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IExcelManager getInstance() {
        return CmsServer.getInstance().getFactory().getExcelManager();
    }

    public void executeSQL(String sql) throws ExcelException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
