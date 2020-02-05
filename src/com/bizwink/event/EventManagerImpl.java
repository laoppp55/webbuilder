package com.bizwink.event;

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

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

public class EventManagerImpl implements EventManager {
    PoolServer cpool;

    public EventManagerImpl(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static EventManager getInstance() {
        return CmsServer.getInstance().getFactory().getEventManager();
    }

    private static final String SQL_GETEVENT =
            "SELECT e.activityID, e.subject, e.location, e.duedate, e.assign, e.durationHour, e.durationMinute, " +
                    "e.isAllDay, e.leadID, e.accountID, e.opportunityID, e.contactID, l.name lead, a.name account, " +
                    "o.name opportunity, c.name contact,  e.comments, e.creationdate, e.modifieddate, e.ownerID,  " +
                    "e.creatorID, e.modifierID, u1.fullname creator, u2.fullname modifier FROM event e, account a, opportunity o, contact c, lead l, users u1, users u2 WHERE e.activityID=? " +
                    "and a.accountID(+)=e.accountID and o.opportunityID(+)=e.opportunityID and " +
                    "c.contactID(+)=e.contactID and l.leadID(+)=e.leadID and  e.creatorID=u1.userID and e.modifierID=u2.userID";


    public Event getEvent(int eventID) throws EventException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Event event = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETEVENT);
            pstmt.setInt(1, eventID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                event = loadDetail(rs);
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

        return event;
    }


    private static final String SQL_getEvents_Next7Days =
            "SELECT activityid,subject,comments,ownerid,duedate,assign,contactid,location  FROM event WHERE " +
                    "ownerid = ? and deleted = 0 AND TRUNC(duedate) <= TO_DATE(?,'YYYY-MM-DD') and " +
                    "TRUNC(duedate) >= TO_DATE(?,'YYYY-MM-DD') ";

    public Vector getEvents_Next7Days(int ownerID, String today) throws Exception {

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date tmpDate = s.parse(today);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        String tomorrow = s.format(cal.getTime());
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector events = new Vector();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getEvents_Next7Days);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, tomorrow);
            pstmt.setString(3, today);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Event e = load(rs);
                events.addElement(e);
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

        return events;
    }

    private static final String SQL_getEvents_ByMonth =
            "SELECT activityid,subject,comments,ownerid,duedate,assign,contactid,location  FROM event WHERE " +
                    "ownerid = ? and deleted = 0 AND TRUNC(duedate,'MONTH') = TRUNC(TO_DATE(?,'YYYY-MM-DD'),'MONTH')";

    public Vector getEvents_ByMonth(int ownerID, String dueDate) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector events = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getEvents_ByMonth);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, dueDate);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Event e = load(rs);
                events.addElement(e);
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return events;
    }

    private static final String SQL_getEventsByDate =
            "SELECT activityid,subject,comments,ownerid,duedate,assign,contactid, location FROM event WHERE " +
                    "ownerid = ? and deleted = 0 AND TRUNC(duedate) = TO_DATE(?,'YYYY-MM-DD')";

    public Vector getEventsByDate(int ownerID, String dueDate) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector events = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getEventsByDate);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, dueDate);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Event e = load(rs);
                events.addElement(e);
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

        return events;
    }


    public Vector getEvents_Tomorrow(int ownerID, String today) {
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
        Vector events = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_getEventsByDate);
            pstmt.setInt(1, ownerID);
            pstmt.setString(2, tomorrow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Event e = load(rs);
                events.addElement(e);
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

        return events;
    }

    private static final String SQL_GETEVENTS =
            "SELECT e.type, e.subject, e.location,e.dueDate, e.assign, e.durationHour,e.durationMinute,priority,e.status, e.accountID, e.contactID," +
                    "e.ownerID,e.location, u1.fullname creator, u2.fullname modifier, u3.fullname owner  FROM event e,users u1,users u2, users u3 where owner = ? " +
                    " and  e.creatorID=u1.userID and e.modifierID=u2.userID and e.ownerID = u3.userID";


    public Vector getEvents(int ownerID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Vector events = new Vector();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETEVENTS);
            pstmt.setInt(1, ownerID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                events.addElement(loadDetail(rs));
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

        return events;
    }


    private static final String SQL_CREATEEVENT =
            "INSERT INTO event(activityID, subject, location, dueDate, assign, durationHour, durationMinute, " +
                    "isAllDay, leadID, accountID, opportunityID, contactID, comments, ownerID, creatorID," +
                    "modifierID, creationdate, modifieddate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void create(Event event) throws EventException {
        Connection conn = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int eventID = sequnceMgr.nextID("Event");
        event.setID(eventID);

        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CREATEEVENT);

                String subject = StringUtil.iso2gb(event.getSubject());
                String location = StringUtil.iso2gb(event.getLocation());
                String comments = StringUtil.iso2gb(event.getComments());
                String assign = StringUtil.iso2gb(event.getAssign());

                pstmt.setInt(1, eventID);
                pstmt.setString(2, subject);
                pstmt.setString(3, location);
                pstmt.setTimestamp(4, event.getDuedate());
                pstmt.setString(5, assign);
                pstmt.setInt(6, event.getDurationHour());
                pstmt.setInt(7, event.getDurationMinute());
                pstmt.setInt(8, event.getIsAllDay());
                if (event.getLeadID() != 0)
                    pstmt.setInt(9, event.getLeadID());
                else
                    pstmt.setNull(9, java.sql.Types.INTEGER);
                if (event.getAccountID() != 0)
                    pstmt.setInt(10, event.getAccountID());
                else
                    pstmt.setNull(10, java.sql.Types.INTEGER);
                if (event.getOpportunityID() != 0)
                    pstmt.setInt(11, event.getOpportunityID());
                else
                    pstmt.setNull(11, java.sql.Types.INTEGER);
                if (event.getContactID() != 0)
                    pstmt.setInt(12, event.getContactID());
                else
                    pstmt.setNull(12, java.sql.Types.INTEGER);
                pstmt.setString(13, comments);
                pstmt.setInt(14, event.getOwnerID());
                pstmt.setInt(15, event.getCreatorID());
                pstmt.setInt(16, event.getModifierID());
                pstmt.setTimestamp(17, event.getCreationDate());
                pstmt.setTimestamp(18, event.getModifiedDate());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new EventException("Database exception: create event failed.");
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
            throw new EventException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATEEVENT =
            "UPDATE event SET ownerID=?, subject=?, location=?, duedate=?, durationHour=?, " +
                    "durationMinute=?, isAllDay=?, leadID=?, accountID=?, opportunityID=?, contactID=?, " +
                    "comments=?, modifierID=?, modifieddate=?,assign=? WHERE activityID=? ";

    public void update(Event event) throws EventException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATEEVENT);

                String subject = StringUtil.iso2gb(event.getSubject());
                String assign = StringUtil.iso2gb(event.getAssign());
                String location = StringUtil.iso2gb(event.getLocation());
                String comments = StringUtil.iso2gb(event.getComments());

                pstmt.setInt(1, event.getOwnerID());
                pstmt.setString(2, subject);
                pstmt.setString(3, location);
                pstmt.setTimestamp(4, event.getDuedate());
                pstmt.setInt(5, event.getDurationHour());
                pstmt.setInt(6, event.getDurationMinute());
                pstmt.setInt(7, event.getIsAllDay());
                if (event.getLeadID() != 0)
                    pstmt.setInt(8, event.getLeadID());
                else
                    pstmt.setNull(8, java.sql.Types.INTEGER);
                if (event.getAccountID() != 0)
                    pstmt.setInt(9, event.getAccountID());
                else
                    pstmt.setNull(9, java.sql.Types.INTEGER);
                if (event.getOpportunityID() != 0)
                    pstmt.setInt(10, event.getOpportunityID());
                else
                    pstmt.setNull(10, java.sql.Types.INTEGER);
                if (event.getContactID() != 0)
                    pstmt.setInt(11, event.getContactID());
                else
                    pstmt.setNull(11, java.sql.Types.INTEGER);
                pstmt.setString(12, comments);
                pstmt.setInt(13, event.getModifierID());
                pstmt.setTimestamp(14, event.getModifiedDate());
                pstmt.setString(15, assign);
                pstmt.setInt(16, event.getID());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new EventException("Database exception: update event failed.");
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
            throw new EventException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETEEVENT =
            "UPDATE event SET deleted=1 WHERE activityID=? ";

    public void delete(Auth authToken, int eventID) throws EventException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELETEEVENT);

                pstmt.setInt(1, eventID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new EventException("Database exception: delete event failed.");
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
            throw new EventException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_REMOVEEVENT =
            "DELETE FROM event WHERE activityID=? ";

    public void remove(int eventID) throws EventException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_REMOVEEVENT);

                pstmt.setInt(1, eventID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new EventException("Database exception: remove event failed.");
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
            throw new EventException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_CONVERTLEADEVENT =
            "UPDATE event SET leadID=?, accountID=?, contactID=?, opportunityID=? " +
                    "WHERE leadID=? ";

    public void convertLeadEvent(int leadID, int accountID, int contactID, int oppID) throws EventException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CONVERTLEADEVENT);

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
                throw new EventException("Database exception: convert lead event failed.");
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
            throw new EventException("Database exception: can't rollback?");
        }
    }

    Event loadDetail(ResultSet rs) throws SQLException {
        Event event = new EventImpl();
        try {
            event.setComments(StringUtil.gb2iso(rs.getString("comments")));
            event.setSubject(StringUtil.gb2iso(rs.getString("subject")));
            event.setAssign(StringUtil.gb2iso(rs.getString("assign")));
            event.setLocation(StringUtil.gb2iso(rs.getString("location")));
            event.setLeadName(StringUtil.gb2iso(rs.getString("lead")));
            event.setAccountName(StringUtil.gb2iso(rs.getString("account")));
            event.setOpportunityName(StringUtil.gb2iso(rs.getString("opportunity")));
            event.setContactName(StringUtil.gb2iso(rs.getString("contact")));
            event.setCreator(StringUtil.gb2iso(rs.getString("creator")));
            event.setModifier(StringUtil.gb2iso(rs.getString("modifier")));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //other field must follow comments(long)
        event.setID(rs.getInt("activityid"));
        event.setOwnerID(rs.getInt("ownerid"));
        event.setDuedate(rs.getTimestamp("duedate"));
        event.setDurationHour(rs.getInt("durationHour"));
        event.setDurationMinute(rs.getInt("durationMinute"));
        event.setIsAllDay(rs.getInt("isallday"));
        event.setLeadID(rs.getInt("leadid"));
        event.setAccountID(rs.getInt("accountid"));
        event.setOpportunityID(rs.getInt("opportunityid"));
        event.setContactID(rs.getInt("contactid"));
        event.setCreatorID(rs.getInt("creatorID"));
        event.setModifierID(rs.getInt("modifierID"));
        event.setCreationDate(rs.getTimestamp("creationDate"));
        event.setModifiedDate(rs.getTimestamp("modifiedDate"));
        return event;
    }

    Event load(ResultSet rs) throws SQLException {
        Event event = new EventImpl();
        try {
            event.setComments(StringUtil.gb2iso(rs.getString("comments")));
            event.setSubject(StringUtil.gb2iso(rs.getString("subject")));
            event.setAssign(StringUtil.gb2iso(rs.getString("assign")));
            event.setLocation(StringUtil.gb2iso(rs.getString("location")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //other field must follow comments(long)
        event.setID(rs.getInt("activityid"));
        event.setOwnerID(rs.getInt("ownerid"));
        event.setDuedate(rs.getTimestamp("duedate"));
        event.setContactID(rs.getInt("contactid"));
        return event;
    }
}