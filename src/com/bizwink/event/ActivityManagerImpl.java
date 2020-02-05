package com.bizwink.event;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

/**
 * Title:        CRM server
 * Description:  Internet Portal Server
 * Copyright:    Copyright (c) 2005
 * Company:
 *
 * @author Peter Song
 * @version 1.0
 */

public class ActivityManagerImpl implements ActivityManager {
    PoolServer cpool;

    public ActivityManagerImpl(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ActivityManager getInstance() {
        return CmsServer.getInstance().getFactory().getActivityManager();
    }

    private static final String SQL_GET_ACTIVITIES =
            "SELECT subject, type, duedate, assign, status, priority, ownerID, modifieddate, activityID " +
                    "FROM task WHERE deleted=0 and closed=0 and accountID=? " +
                    "UNION " +
                    "SELECT subject, type, duedate, assign, '' , '' , ownerID, modifieddate, activityID " +
                    "FROM event WHERE deleted=0 and trunc(duedate)>=trunc(SYSDATE) and accountID=?";

    private static final String SQL_GET_OLD_ACTIVITIES =
            "SELECT subject, type, duedate, assign, status, priority, ownerID, modifieddate, activityID " +
                    "FROM task WHERE deleted=0 and closed=1 and accountID=? " +
                    "UNION " +
                    "SELECT subject, type, duedate, assign, '' , '', ownerID, modifieddate, activityID " +
                    "FROM event WHERE deleted=0 and trunc(duedate)<trunc(SYSDATE) and accountID=?";

    public List getActivities(int belongType, int belongID, int closed, int order) throws ActivityException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String strSQL;
        if (closed == 0)
            strSQL = SQL_GET_ACTIVITIES;
        else
            strSQL = SQL_GET_OLD_ACTIVITIES;
        if (belongType == SalesConstants.OPPORTUNITY_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "opportunityID");
        else if (belongType == SalesConstants.CONTACT_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "contactID");
        else if (belongType == SalesConstants.LEAD_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "leadID");
        if (order < 0)
            strSQL += " order by " + Math.abs(order) + " desc";
        else
            strSQL += " order by " + order + " asc";

        List activities = new ArrayList();
        Activity activity;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, belongID);
            pstmt.setInt(2, belongID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                activity = load(rs);
                activities.add(activity);
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return activities;
    }

    public List getActivities(int belongType, int belongID, int start, int count, int closed, int order) throws ActivityException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String strSQL;
        if (closed == 0)
            strSQL = SQL_GET_ACTIVITIES;
        else
            strSQL = SQL_GET_OLD_ACTIVITIES;
        if (belongType == SalesConstants.OPPORTUNITY_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "opportunityID");
        else if (belongType == SalesConstants.CONTACT_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "contactID");
        else if (belongType == SalesConstants.LEAD_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "leadID");
        if (order < 0)
            strSQL += " order by " + Math.abs(order) + " desc";
        else
            strSQL += " order by " + order + " asc";

        List activities = new ArrayList();
        Activity activity;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, belongID);
            pstmt.setInt(2, belongID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < start; i++) {
                rs.next();
            }

            for (int i = 0; i < count; i++) {
                if (rs.next()) {
                    activity = load(rs);
                    activities.add(activity);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return activities;
    }

    private static final String SQL_GET_ACTIVITYCOUNT =
            "SELECT count(*) from " +
                    "(SELECT activityID,type FROM task WHERE deleted=0 and closed=0 and accountID=? " +
                    "UNION " +
                    "SELECT activityID,type FROM event WHERE deleted=0 and trunc(duedate)>=trunc(SYSDATE) and accountID=? )";

    private static final String SQL_GET_OLD_ACTIVITYCOUNT =
            "SELECT count(*) from " +
                    "(SELECT activityID,type FROM task WHERE deleted=0 and closed=1 and accountID=? " +
                    "UNION " +
                    "SELECT activityID,type FROM event WHERE deleted=0 and trunc(duedate)<trunc(SYSDATE) and accountID=? )";

    public int getActivityCount(int belongType, int belongID, int closed) throws ActivityException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        String strSQL;
        if (closed == 0)
            strSQL = SQL_GET_ACTIVITYCOUNT;
        else
            strSQL = SQL_GET_OLD_ACTIVITYCOUNT;

        if (belongType == SalesConstants.OPPORTUNITY_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "opportunityID");
        else if (belongType == SalesConstants.CONTACT_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "contactID");
        else if (belongType == SalesConstants.LEAD_TYPE)
            strSQL = StringUtil.replace(strSQL, "accountID", "leadID");

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, belongID);
            pstmt.setInt(2, belongID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivityException("Database exception: get activity count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    Activity load(ResultSet rs) throws SQLException {
        Activity activity = new ActivityImpl();
        try {
            activity.setSubject(StringUtil.gb2iso(rs.getString("subject")));
            activity.setPriority(StringUtil.gb2iso(rs.getString("priority")));
            activity.setStatus(StringUtil.gb2iso(rs.getString("status")));
            activity.setAssign(StringUtil.gb2iso(rs.getString("assign")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //other field must follow comments(long)
        activity.setID(rs.getInt("activityid"));
        activity.setType(rs.getInt("type"));
        activity.setOwnerID(rs.getInt("ownerid"));
        activity.setDuedate(rs.getTimestamp("duedate"));
        activity.setModifiedDate(rs.getTimestamp("modifieddate"));
        return activity;
    }
}