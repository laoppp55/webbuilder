package com.bizwink.cms.toolkit.task;

import com.bizwink.cms.server.*;

import java.util.*;
import java.sql.*;

/**
 * An implementation of user database peer using instantdb, an embedded java
 * SQL database.
 */

public class TaskPeer implements ITaskManager {
    PoolServer cpool;

    public TaskPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ITaskManager getInstance() {
        return CmsServer.getInstance().getFactory().getTaskManager();
    }

    private static String GET_ALL_TASK = "select * from tbl_task where memberid = ? order by activityid desc";

    public List getTask(String memberid) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Task task;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_TASK);
            pstmt.setString(1, memberid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                task = load(rs);
                list.add(task);
            }
            rs.close();
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
        return list;
    }

    public List getCurrentTask(String memberid, int startrow, int range) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Task task;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_TASK);
            pstmt.setString(1, memberid);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                task = load(rs);
                list.add(task);
            }
            rs.close();
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
        return list;
    }

    private static String GET_A_TASK = "select * from tbl_task where activityid = ?";

    public Task getA_Task(int id) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Task task = new Task();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_TASK);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                task = load(rs);
            }
            rs.close();
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
        return task;
    }

    private static String INSERT_TASK = "insert into tbl_task(subject,begindate,enddate,memberid,comments,creationdate,modifieddate) values (?, ?, ?, ?, ?, ?, ?)";

    public void insertTask(Task task) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(INSERT_TASK);
                pstmt.setString(1, task.getSubject());
                pstmt.setTimestamp(2, task.getBeginDate());
                pstmt.setTimestamp(3, task.getEndDate());
                pstmt.setString(4, task.getMemberID());
                pstmt.setString(5, task.getComments());
                pstmt.setTimestamp(6, task.getCreationDate());
                pstmt.setTimestamp(7, task.getModifiedDate());
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_TASK = "update tbl_task set subject = ?, begindate = ?, enddate = ?, comments = ?, modifieddate = ? where activityid = ?";

    public void updateTask(Task task, int id) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(UPDATE_TASK);
                pstmt.setString(1, task.getSubject());
                pstmt.setTimestamp(2, task.getBeginDate());
                pstmt.setTimestamp(3, task.getEndDate());
                pstmt.setString(4, task.getComments());
                pstmt.setTimestamp(5, task.getModifiedDate());
                pstmt.setInt(6, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_TASK_FLAG = "update tbl_task set status = ? where activityid = ?";

    public void updateTaskFlag(int id, int status) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(UPDATE_TASK_FLAG);
                pstmt.setInt(1, status);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) throws TaskException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_task where activityid = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();
            conn.commit();
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

    private Task load(ResultSet rs) {
        Task task = new Task();
        try {
            task.setActivityID(rs.getInt("activityid"));
            task.setSubject(rs.getString("subject"));
            task.setMemberID(rs.getString("memberid"));
            task.setBeginDate(rs.getTimestamp("begindate"));
            task.setEndDate(rs.getTimestamp("enddate"));
            task.setStatus(rs.getInt("status"));
            task.setComments(rs.getString("comments"));
            task.setCreationDate(rs.getTimestamp("creationdate"));
            task.setModifiedDate(rs.getTimestamp("modifieddate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }
}
