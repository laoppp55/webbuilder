package com.bizwink.event;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.security.*;

/**
 * Title:        CRM server
 * Description:  Internet Portal Server
 * Copyright:    Copyright (c) 2005
 * Company:
 *
 * @author Peter Song
 * @version 1.0
 */

public class TaskManagerImpl implements TaskManager {
    PoolServer cpool;

    public TaskManagerImpl(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static TaskManager getInstance() {
        return (TaskManager) CmsServer.getInstance().getFactory().getTaskManager();
    }

    private static final String SQL_getTasks_AllOpen =
            "SELECT activityid, subject,priority,status,comments,ownerid,duedate,assign FROM task WHERE ownerid=? and deleted = 0";

    public Vector getTasks_AllOpen(int ownerID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_AllOpen);
            pstmt.setInt(1, ownerID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }

    private static final String SQL_getTasks_Today =
            "SELECT activityid, subject,priority,status,comments,ownerid,duedate,assign FROM task WHERE ownerid=? AND deleted = 0" +
                    " AND TRUNC(duedate) = TRUNC(sysdate)";

    public Vector getTasks_Today(int ownerID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_Today);
            pstmt.setInt(1, ownerID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }

    private static final String SQL_getTasks_OverDue =
            "SELECT activityid, subject,priority,status,comments,ownerid,duedate,assign FROM task WHERE ownerid=? AND deleted = 0" +
                    " AND TRUNC(duedate) < TRUNC(sysdate)";

    public Vector getTasks_OverDue(int ownerID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_OverDue);
            pstmt.setInt(1, ownerID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }


    public Vector getTasks_By_Date(int ownerID, String date) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_Tomorrow);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, date);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }


    private static final String SQL_getTasks_Tomorrow =
            "SELECT activityid, subject,priority,status,comments,ownerid,duedate,assign FROM task WHERE ownerid=? AND deleted = 0" +
                    " AND TRUNC(duedate) = TO_DATE(?,'YYYY-MM-DD')";


    public Vector getTasks_Tomorrow(int ownerID, String today) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if (today != null) {
            try {
                cal.setTime((java.util.Date) s.parseObject(today));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        cal.add(Calendar.DAY_OF_MONTH, 1);

        String tomorrow = s.format(cal.getTime());
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_Tomorrow);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, tomorrow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;

    }

    private static final String SQL_getTasks_ThisMonth =
            "SELECT activityid, subject,priority,status,comments,ownerid,duedate,assign FROM task WHERE ownerid=? AND deleted = 0" +
                    " AND TRUNC(duedate,'MONTH') = TRUNC(TO_DATE(?,'YYYY-MM-DD'),'MONTH')";

    public Vector getTasks_ThisMonth(int ownerID, String dueDate) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_ThisMonth);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, dueDate);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }


    private static final String SQL_getTasks_Next7days =
            "SELECT activityid, subject,priority,status,comments,ownerid,assign," +
                    " duedate FROM task WHERE ownerid=? AND deleted = 0 AND TRUNC(duedate) between " +
                    "TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?,'YYYY-MM-DD')";

    public Vector getTask_Next7Days(int ownerID, String today) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if (today != null) {
            try {
                cal.setTime((java.util.Date) s.parseObject(today));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String tomorrow = s.format(cal.getTime());

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector tasks = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getTasks_Next7days);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, today);
            pstmt.setString(3, tomorrow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tasks.addElement(load(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return tasks;
    }

    private static final String SQL_GETTASK =
            "SELECT t.activityID, t.subject, t.duedate, t.assign, t.priority, t.status," +
                    "t.leadID, t.accountID, t.opportunityID, t.contactID,  l.name lead, a.name account, " +
                    "o.name opportunity, c.name contact, t.comments, t.creationdate, t.modifieddate, t.ownerID, t.creatorID," +
                    "t.modifierID,u1.fullname creator, u2.fullname modifier FROM task t, account a, opportunity o, contact c, lead l,users u1, users u2 WHERE t.activityID=? " +
                    "and a.accountID(+)=t.accountID and o.opportunityID(+)=t.opportunityID and " +
                    "c.contactID(+)=t.contactID and l.leadID(+)=t.leadID and t.creatorID=u1.userID and t.modifierID=u2.userID";

    public Task getTask(int taskID) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Task task = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETTASK);
            pstmt.setInt(1, taskID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                task = loadDetail(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn); // close the pooled connection
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return task;
    }

    private static final String SQL_CREATETASK =
            "INSERT INTO task(activityID, subject, dueDate, assign, priority, status, closed," +
                    "leadID, accountID, opportunityID, contactID, comments, ownerID, creatorID, modifierID," +
                    "creationdate, modifieddate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void create(Task task) throws TaskException {
        Connection conn = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int taskID = sequnceMgr.nextID("Task");
        task.setID(taskID);

        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CREATETASK);

                String subject = StringUtil.iso2gb(task.getSubject());
                String assign = StringUtil.iso2gb(task.getAssign());
                String priority = StringUtil.iso2gb(task.getPriority());
                String status = StringUtil.iso2gb(task.getStatus());
                String comments = StringUtil.iso2gb(task.getComments());
                if (status.equals(SalesConstants.taskStatuses[2]))
                    task.setClosed(1);
                else
                    task.setClosed(0);

                pstmt.setInt(1, taskID);
                pstmt.setString(2, subject);
                pstmt.setTimestamp(3, task.getDuedate());
                pstmt.setString(4, assign);
                pstmt.setString(5, priority);
                pstmt.setString(6, status);
                pstmt.setInt(7, task.getClosed());
                if (task.getLeadID() != 0)
                    pstmt.setInt(8, task.getLeadID());
                else
                    pstmt.setNull(8, java.sql.Types.INTEGER);
                if (task.getAccountID() != 0)
                    pstmt.setInt(9, task.getAccountID());
                else
                    pstmt.setNull(9, java.sql.Types.INTEGER);
                if (task.getOpportunityID() != 0)
                    pstmt.setInt(10, task.getOpportunityID());
                else
                    pstmt.setNull(10, java.sql.Types.INTEGER);
                if (task.getContactID() != 0)
                    pstmt.setInt(11, task.getContactID());
                else
                    pstmt.setNull(11, java.sql.Types.INTEGER);
                pstmt.setString(12, comments);
                pstmt.setInt(13, task.getOwnerID());
                pstmt.setInt(14, task.getCreatorID());
                pstmt.setInt(15, task.getModifierID());
                pstmt.setTimestamp(16, task.getCreationDate());
                pstmt.setTimestamp(17, task.getModifiedDate());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new TaskException("Database exception: create task failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn); // close the pooled connection
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new TaskException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATETASK =
            "UPDATE task SET ownerID=?, subject=?, duedate=?, priority=?, status=?, closed=?," +
                    "leadID=?, accountID=?, opportunityID=?, contactID=?, comments=?, modifierID=?," +
                    "modifieddate=?, assign=? WHERE activityID=? ";

    public void update(Task task) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATETASK);

                String subject = StringUtil.iso2gb(task.getSubject());
                String assign = StringUtil.iso2gb(task.getAssign());
                String priority = StringUtil.iso2gb(task.getPriority());
                String status = StringUtil.iso2gb(task.getStatus());
                String comments = StringUtil.iso2gb(task.getComments());
                if (status.equals(SalesConstants.taskStatuses[2]))
                    task.setClosed(1);
                else
                    task.setClosed(0);

                pstmt.setInt(1, task.getOwnerID());
                pstmt.setString(2, subject);
                pstmt.setTimestamp(3, task.getDuedate());
                pstmt.setString(4, priority);
                pstmt.setString(5, status);
                pstmt.setInt(6, task.getClosed());
                if (task.getLeadID() != 0)
                    pstmt.setInt(7, task.getLeadID());
                else
                    pstmt.setNull(7, java.sql.Types.INTEGER);
                if (task.getAccountID() != 0)
                    pstmt.setInt(8, task.getAccountID());
                else
                    pstmt.setNull(8, java.sql.Types.INTEGER);
                if (task.getOpportunityID() != 0)
                    pstmt.setInt(9, task.getOpportunityID());
                else
                    pstmt.setNull(9, java.sql.Types.INTEGER);
                if (task.getContactID() != 0)
                    pstmt.setInt(10, task.getContactID());
                else
                    pstmt.setNull(10, java.sql.Types.INTEGER);
                pstmt.setString(11, comments);
                pstmt.setInt(12, task.getModifierID());
                pstmt.setTimestamp(13, task.getModifiedDate());
                pstmt.setString(14, assign);
                pstmt.setInt(15, task.getID());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new TaskException("Database exception: update task failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn); // close the pooled connection
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new TaskException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_CONVERTLEADTASK =
            "UPDATE task SET leadID=?, accountID=?, contactID=?, opportunityID=? " +
                    "WHERE leadID=? ";

    public void convertLeadTask(int leadID, int accountID, int contactID, int oppID) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CONVERTLEADTASK);

                pstmt.setNull(1, java.sql.Types.INTEGER);
                if (accountID != 0)
                    pstmt.setInt(2, accountID);
                else
                    pstmt.setNull(2, java.sql.Types.INTEGER);
                if (contactID != 0)
                    pstmt.setInt(3, contactID);
                else
                    pstmt.setNull(3, java.sql.Types.INTEGER);
                if (oppID != 0)
                    pstmt.setInt(4, oppID);
                else
                    pstmt.setNull(4, java.sql.Types.INTEGER);
                pstmt.setInt(5, leadID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new TaskException("Database exception: convert lead task failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn); // close the pooled connection
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new TaskException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETETASK =
            "UPDATE task SET deleted=1 WHERE activityID=? ";

    public void delete(Auth authToken, int taskID) throws TaskException {
        Task task = getTask(taskID);

        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELETETASK);

                pstmt.setInt(1, taskID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new TaskException("Database exception: delete task failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn); // close the pooled connection
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new TaskException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_REMOVETASK =
            "DELETE FROM task WHERE activityID=? ";

    public void remove(int taskID) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_REMOVETASK);

                pstmt.setInt(1, taskID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new TaskException("Database exception: remove task failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn); // close the pooled connection
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new TaskException("Database exception: can't rollback?");
        }
    }

    Task loadDetail(ResultSet rs) throws SQLException {
        Task task = new TaskImpl();
        try {
            task.setComments(StringUtil.gb2iso(rs.getString("comments")));
            task.setSubject(StringUtil.gb2iso(rs.getString("subject")));
            task.setAssign(StringUtil.gb2iso(rs.getString("assign")));
            task.setPriority(StringUtil.gb2iso(rs.getString("priority")));
            task.setStatus(StringUtil.gb2iso(rs.getString("status")));
            task.setLeadName(StringUtil.gb2iso(rs.getString("lead")));
            task.setAccountName(StringUtil.gb2iso(rs.getString("account")));
            task.setOpportunityName(StringUtil.gb2iso(rs.getString("opportunity")));
            task.setContactName(StringUtil.gb2iso(rs.getString("contact")));
            task.setCreator(StringUtil.gb2iso(rs.getString("creator")));
            task.setModifier(StringUtil.gb2iso(rs.getString("modifier")));


        } catch (Exception e) {
            e.printStackTrace();
        }

        //other field must follow comments(long)
        task.setID(rs.getInt("activityid"));
        task.setOwnerID(rs.getInt("ownerid"));
        task.setDuedate(rs.getTimestamp("duedate"));
        task.setLeadID(rs.getInt("leadid"));
        task.setAccountID(rs.getInt("accountid"));
        task.setOpportunityID(rs.getInt("opportunityid"));
        task.setContactID(rs.getInt("contactid"));
        task.setCreatorID(rs.getInt("creatorID"));
        task.setModifierID(rs.getInt("modifierID"));
        task.setCreationDate(rs.getTimestamp("creationDate"));
        task.setModifiedDate(rs.getTimestamp("modifiedDate"));
        return task;
    }

    Task load(ResultSet rs) throws SQLException {
        Task task = new TaskImpl();
        try {
            task.setComments(StringUtil.gb2iso(rs.getString("comments")));
            task.setSubject(StringUtil.gb2iso(rs.getString("subject")));
            task.setPriority(StringUtil.gb2iso(rs.getString("priority")));
            task.setStatus(StringUtil.gb2iso(rs.getString("status")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //other field must follow comments(long)
        task.setID(rs.getInt("activityid"));
        task.setOwnerID(rs.getInt("ownerid"));
        task.setDuedate(rs.getTimestamp("duedate"));
        return task;
    }
}