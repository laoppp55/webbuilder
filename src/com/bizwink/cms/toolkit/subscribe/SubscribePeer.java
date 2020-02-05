package com.bizwink.cms.toolkit.subscribe;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-2-25
 * Time: 17:19:51
 * To change this template use File | Settings | File Templates.
 */
public class SubscribePeer implements ISubscribeManager {
    PoolServer cpool;

    public SubscribePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ISubscribeManager getInstance() {
        return CmsServer.getInstance().getFactory().getSubscribeManager();
    }

    //邮件定阅插入记录
    private static String SQL_INSERSUBSCRIBE = "insert into sino_subscribe (id,email,subflag) values (?,?,?)";

    public void addSubscribe(Subscribe subscribe) {
        Connection conn = null;
        PreparedStatement pstmt;
        String seqFlag = "Subscribe";
        ISequenceManager seqMgr = SequencePeer.getInstance();

        int id = seqMgr.getSequenceNum(seqFlag);
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_INSERSUBSCRIBE);
            pstmt.setInt(1, id);
            pstmt.setString(2, subscribe.getEmail());
            pstmt.setInt(3, subscribe.getSubflag());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        } catch (SQLException e) {
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

    //取消定阅更新标志位
    private static String SQL_UPDATESUBFLAG = "update sino_subscribe set subflag=? where email=?";

    public void updateSubFlag(Subscribe subscribe) {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATESUBFLAG);
                pstmt.setInt(1, subscribe.getSubflag());
                pstmt.setString(2, subscribe.getEmail());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //验证是否有此信息
    private static String SQL_CHECKEMAIL = "select email from sino_subscribe where email=?";

    public boolean checkEmail(String email) {
        Connection conn = null;
        PreparedStatement pstmt;
        boolean flag = false;
        ResultSet rs;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CHECKEMAIL);
                pstmt.setString(1, email);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    flag = true;
                }
                rs.close();
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //根据email得到标志位
    private static String SQL_GETSUBFLAG = "select subflag from sino_subscribe where email=?";

    public int getSubFlag(String email) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int flag = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSUBFLAG);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
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
        return flag;
    }
}
