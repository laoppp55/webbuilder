package com.bizwink.cms.business.Message;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class messagePeer implements IMessageManager {
    PoolServer cpool;

    public messagePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IMessageManager getInstance() {
        return CmsServer.getInstance().getFactory().getMessageManager();
    }


    public List getMessageList(int flag, int userid, int kind, String search, int siteid, int startindex, int range) throws MessageException {  //flag为:1后台发;0前台发(供系统区分管理员消息)    userid为-1时为后台管理员;    kind为: 0 收件箱 1发件箱
        // kind为2时是收藏夹列表,此时查询en_savemessage表,列出userid的收藏
        List list = new ArrayList();
        Message message;
        String sqlstr = "";
        if (CmsServer.getInstance().getDBtype().equalsIgnoreCase("mssql"))
            sqlstr = "select u.username receiver,u2.username sender,m.* from en_message m left outer join tbl_userinfo u " +
                    " on u.username=m.receive_user left outer join tbl_userinfo u2 on m.send_user=u2.username " +
                    " where m.siteid=" + String.valueOf(siteid) + " and m.flag=" + String.valueOf(flag);
        if (CmsServer.getInstance().getDBtype().equalsIgnoreCase("oracle"))
            sqlstr = "select u.username receiver,u2.username sender,m.* from en_message m,tbl_userinfo u,tbl_userinfo u2 " +
                    "where m.receive_user=u.username(+) and m.send_user=u.username(+) " +
                    " and m.siteid=" + String.valueOf(siteid) + " and m.flag=" + String.valueOf(flag);
        if (kind == 0)
            sqlstr = sqlstr + " and m.receive_user='" + String.valueOf(userid) + "' and m.receive_del=0";
        if (kind == 1)
            sqlstr = sqlstr + " and m.send_user='" + String.valueOf(userid) + "' and m.send_del=0";

        if (search != null && !"".equals(search)) {
            if (kind == 0)
                sqlstr = sqlstr + " and u2.username like '%" + search + "%'";
            if (kind == 1)
                sqlstr = sqlstr + " and u.username like '%" + search + "%'";
        }
        sqlstr = sqlstr + " order by m.senddate desc";

        if (kind == 2) {
            sqlstr = "select * from en_savemessage where userid=" + String.valueOf(userid) +
                    " and siteid=" + String.valueOf(siteid) + " order by createdate desc";
        }

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (range == 0) {
                while (rs.next()) {
                    if (kind != 2)
                        message = load_message(rs);
                    else
                        message = load_savemessage(rs);
                    list.add(message);
                }
            } else {
                for (int i = 0; i < startindex; i++)
                    rs.next();
                for (int i = 0; i < range && rs.next(); i++) {
                    if (kind != 2)
                        message = load_message(rs);
                    else
                        message = load_savemessage(rs);
                    list.add(message);
                }
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

    private Message load_message(ResultSet rs) throws MessageException {
        Message message = new Message();
        try {
            message.setFlag(rs.getInt("flag"));
            message.setID(rs.getInt("id"));
            message.setMessage(rs.getString("message"));
            message.setReceive_User(rs.getString("receive_user"));
            message.setReceiveDel(rs.getInt("receive_del"));
            message.setSend_User(rs.getString("send_user"));
            message.setSendDate(rs.getTimestamp("senddate"));
            message.setSendDel(rs.getInt("send_del"));
            message.setSiteID(rs.getInt("siteid"));
            message.setReceiverName(rs.getString("receiver"));
            message.setSenderName(rs.getString("sender"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    private Message load_savemessage(ResultSet rs) throws MessageException {
        Message message = new Message();
        try {
            message.setID(rs.getInt("id"));
            message.setUserID(rs.getInt("userid"));
            message.setMessage(rs.getString("message"));
            message.setReceive_User(rs.getString("receive_user"));
            message.setSend_User(rs.getString("send_user"));
            message.setSendDate(rs.getTimestamp("senddate"));
            message.setSiteID(rs.getInt("siteid"));
            message.setReceiverName(rs.getString("receiver"));
            message.setSenderName(rs.getString("sender"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public void updateDeleteFlag(int id, int kind, int siteid) throws MessageException { //kind为: 1 收件箱删除  2 发件箱删除    实际上为修改删除标志位
        //kind为: 3 收藏夹删除  删除en_savemessage表内容
        String sqlstr = "";
        if (kind == 1)
            sqlstr = "update en_message set receive_del=1 where id=? and siteid=?";
        else if (kind == 2)
            sqlstr = "update en_message set send_del=1 where id=? and siteid=?";
        else if (kind == 3)
            sqlstr = "delete en_message where id=? and siteid=?";
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, id);
                pstmt.setInt(2, siteid);
                pstmt.executeUpdate();
                pstmt.close();
                if (kind == 1 || kind == 2)
                    RealDelete(id, siteid, conn, pstmt);  //删除无用记录
                conn.commit();
            } catch (Exception e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void RealDelete(int id, int siteid, Connection conn, PreparedStatement pstmt) throws SQLException {
        String sqlstr = "delete from en_message where id=? and siteid=? and send_del=1 and receive_del=1";
        pstmt = conn.prepareStatement(sqlstr);
        pstmt.setInt(1, id);
        pstmt.setInt(2, siteid);
        pstmt.executeUpdate();
        pstmt.close();
    }


    public void newMessage(Message message) throws MessageException {
        String sqlstr = "";
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    sqlstr = "insert into en_message(siteid,send_user,receive_user,message,senddate,send_del,receive_del,flag,id) values(?,?,?,?,?,?,?,?,?)";
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    sqlstr = "insert into en_message(siteid,send_user,receive_user,message,senddate,send_del,receive_del,flag) values(?,?,?,?,?,?,?,?)";
                else
                    sqlstr = "insert into en_message(siteid,send_user,receive_user,message,senddate,send_del,receive_del,flag) values(?,?,?,?,?,?,?,?)";
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, message.getSiteID());
                pstmt.setString(2, message.getSend_User());
                pstmt.setString(3, message.getReceive_User());
                pstmt.setString(4, message.getMessage());
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(6, message.getSendDel());
                pstmt.setInt(7, message.getReceiveDel());
                pstmt.setInt(8, message.getFlag());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(9, sequenceMgr.getSequenceNum("Message"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newSaveMessage(int id, int userid, int siteid) throws MessageException {
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        String sqlstr = "";
        if (CmsServer.getInstance().getDBtype().equalsIgnoreCase("mssql"))
            sqlstr = "insert into en_savemessage(siteid,message,sender,receiver,send_user,receive_user" +
                    ",senddate,createdate,userid) select m.siteid,m.message,u1.username sender," +
                    "u2.username receiver,m.send_user,m.receive_user,m.senddate,?,? from en_message m" +
                    " left join tbl_userinfo u1 on u1.username=m.send_user left join tbl_userinfo u2" +
                    " on u2.username=m.receive_user where m.id=? and m.siteid=?";
        if (CmsServer.getInstance().getDBtype().equalsIgnoreCase("oracle"))
            sqlstr = "insert into en_savemessage(id,siteid,message,sender,receiver,send_user,receive_user" +
                    ",senddate,createdate,userid) select " + String.valueOf(sequenceMgr.nextID("en_savemessage")) +
                    ",m.siteid,m.message,u1.username sender,u2.username receiver,m.send_user," +
                    "m.receive_user,m.senddate,?,? from en_message m, tbl_userinfo u1,tbl_userinfo u2 " +
                    " where m.send_user=u1.username(+) and m.receive_user=u2.username(+) and m.id=? and m.siteid=?";
        Timestamp thistime = new Timestamp(System.currentTimeMillis());

        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setTimestamp(1, thistime);
                pstmt.setInt(2, userid);
                pstmt.setInt(3, id);
                pstmt.setInt(4, siteid);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
