package com.bizwink.cms.toolkit.workday;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WorkdayPeer implements IWorkdayManager {
    PoolServer cpool;

    public WorkdayPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IWorkdayManager getInstance() {
        return CmsServer.getInstance().getFactory().getWorkdayPeer();
    }

    //创建工作日
    private static String CREATE_WORKDAY_INFO = "insert into tbl_workdayinfo(days,siteid,worddayflag) values(?,?,?)";
    private static String UPDATE_WORKDAY_INFO = "UPDATE tbl_workdayinfo set worddayflag = ? where siteid = ? and days = ?";

    public void createWorkdayInfo(String Month, List workDaysList) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            //查询是否已经有本月数据
            boolean flag = false;
            pstmt = conn.prepareStatement("select * from tbl_workdayinfo where days like '%" + Month + "%'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = true;
            }
            rs.close();
            pstmt.close();
            conn.setAutoCommit(false);
            for (int i = 0; i < workDaysList.size(); i++) {
                WorkDay workday = (WorkDay) workDaysList.get(i);
                if (workday != null) {
                    String day = "";
                    if (i + 1 < 10) {
                        day = "0" + String.valueOf((i + 1));
                    } else {
                        day = String.valueOf((i + 1));
                    }
                    String days = Month + "-" + day;
                    if (!flag) {
                        pstmt = conn.prepareStatement(CREATE_WORKDAY_INFO);
                        pstmt.setString(1, days);
                        pstmt.setInt(2, workday.getSiteid());
                        pstmt.setInt(3, workday.getWorkdaysflag());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                    else
                    {
                        pstmt = conn.prepareStatement(UPDATE_WORKDAY_INFO);

                        pstmt.setInt(1,workday.getWorkdaysflag());
                        pstmt.setInt(2,workday.getSiteid());
                        pstmt.setString(3,days);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
            }
            conn.commit();
        }
        catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    //获得某月的信息
    public List getWorkDaysInfoForMonth(String month, int siteid) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_workdayinfo where days like '%" + month + "%' and siteid = "+siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WorkDay workday = new WorkDay();
                workday.setSiteid(rs.getInt("siteid"));
                workday.setWorkdaysflag(rs.getInt("worddayflag"));
                workday.setDays(rs.getString("days"));
                list.add(workday);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }
}