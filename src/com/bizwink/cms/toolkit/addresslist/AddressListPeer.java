package com.bizwink.cms.toolkit.addresslist;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

import java.util.*;
import java.sql.*;

/**
 * An implementation of user database peer using instantdb, an embedded java
 * SQL database.
 */

public class AddressListPeer implements IAddressListManager {
    PoolServer cpool;

    public AddressListPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IAddressListManager getInstance() {
        return (IAddressListManager) CmsServer.getInstance().getFactory().getAddressListManager();
    }

    private static String GET_ALL_ADDRESS_LIST = "select * from tbl_userinfos where memberid = ? order by id desc";

    public List getAddressList(String memberid) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_ADDRESS_LIST);
            pstmt.setString(1, memberid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                addresslist = load(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentAddressList(String memberid, int startrow, int range) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_ADDRESS_LIST);
            pstmt.setString(1, memberid);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                addresslist = load(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_SYS_ADDRESS_LIST = "select * from tbl_members where siteid = ?";
    public List getSysAddressList(int siteid) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_SYS_ADDRESS_LIST);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                addresslist = load_sys(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentSysAddressList(int startrow, int range, int siteid) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_SYS_ADDRESS_LIST);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                addresslist = load_sys(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_MESSAGE_LIST = "select * from bbs_message where receiver = ? order by id desc";
    public List getMessageList(String receiver) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_MESSAGE_LIST);
            pstmt.setString(1, receiver);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                addresslist = load_msg(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentMessageList(String receiver, int startrow, int range) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_MESSAGE_LIST);
            pstmt.setString(1, receiver);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                addresslist = load_msg(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_MESSAGE_LIST1 = "select * from bbs_message where sender = ? order by id desc";
    public List getMessageList1(String sender) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_MESSAGE_LIST1);
            pstmt.setString(1, sender);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                addresslist = load_msg(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentMessageList1(String sender, int startrow, int range) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_MESSAGE_LIST1);
            pstmt.setString(1, sender);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                addresslist = load_msg(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_A_ADDRESSLIST = "select * from tbl_userinfos where id = ?";

    public AddressList getA_AddressList(int id) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_ADDRESSLIST);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                addresslist = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return addresslist;
    }

    private static String INSERT_ADDRESSLIST_FOR_ORACLE = "insert into tbl_userinfos(username,corporation,address,postcode,mobilephone,phone,fax,email,createtime,memberid,id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String INSERT_ADDRESSLIST_FOR_MSSQL = "insert into tbl_userinfos(username,corporation,address,postcode,mobilephone,phone,fax,email,createtime,memberid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String INSERT_ADDRESSLIST_FOR_MYSQL = "insert into tbl_userinfos(username,corporation,address,postcode,mobilephone,phone,fax,email,createtime,memberid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void insertAddressList(AddressList addresslist) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(INSERT_ADDRESSLIST_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(INSERT_ADDRESSLIST_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(INSERT_ADDRESSLIST_FOR_MYSQL);
                pstmt.setString(1, addresslist.getName());
                pstmt.setString(2, addresslist.getCorporation());
                pstmt.setString(3, addresslist.getAddress());
                pstmt.setString(4, addresslist.getPostCode());
                pstmt.setString(5, addresslist.getMobilephone());
                pstmt.setString(6, addresslist.getPhone());
                pstmt.setString(7, addresslist.getFax());
                pstmt.setString(8, addresslist.getEmail());
                pstmt.setLong(9, addresslist.getWriteDate());
                pstmt.setString(10, addresslist.getMemberID());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(11, seqMgr.getSequenceNum("AddressInfo"));
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
                    cpool.freeConnection(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String INSERT_MESSAGE_FOR_ORACLE = "insert into bbs_message(sender,receiver,content,senddate,id) values (?, ?, ?, ?, ?)";
    private static String INSERT_MESSAGE_FOR_MSSQL = "insert into bbs_message(sender,receiver,content,senddate) values (?, ?, ?, ?)";
    private static String INSERT_MESSAGE_FOR_MYSQL = "insert into bbs_message(sender,receiver,content,senddate) values (?, ?, ?, ?)";

    public void insertMessage(AddressList addresslist) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            try {
                ISequenceManager sqMgr = SequencePeer.getInstance();
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(INSERT_MESSAGE_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(INSERT_MESSAGE_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(INSERT_MESSAGE_FOR_MYSQL);
                pstmt.setString(1, addresslist.getSender());
                pstmt.setString(2, addresslist.getReceiver());
                pstmt.setString(3, addresslist.getContent());
                pstmt.setLong(4, addresslist.getSendDate());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(5, sqMgr.getSequenceNum("Message"));
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
                    cpool.freeConnection(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_ADDRESSLIST = "update tbl_userinfos set username = ?, corporation = ?, address = ?, postcode = ?, mobilephone = ?, phone = ?, fax = ?, email = ? where id = ?";

    public void updateAddressList(AddressList addresslist, int id) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(UPDATE_ADDRESSLIST);
                pstmt.setString(1, addresslist.getName());
                pstmt.setString(2, addresslist.getCorporation());
                pstmt.setString(3, addresslist.getAddress());
                pstmt.setString(4, addresslist.getPostCode());
                pstmt.setString(5, addresslist.getMobilephone());
                pstmt.setString(6, addresslist.getPhone());
                pstmt.setString(7, addresslist.getFax());
                pstmt.setString(8, addresslist.getEmail());
                pstmt.setInt(9, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAddressList(int id) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_userinfos where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public void deleteMessage(int id) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from bbs_message where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public List getSearchAddressList(String sqlstr) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                addresslist = load(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentSearchAddressList(String sqlstr, int startrow, int range) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AddressList addresslist = new AddressList();
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                addresslist = load(rs);
                list.add(addresslist);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public Timestamp getNewReadDate(String receiver) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long getdate = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select readdate from tbl_members where userid = ?");
            pstmt.setString(1, receiver);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                getdate = rs.getLong("readdate");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return new Timestamp(getdate);
    }

    public Timestamp getNewMessageDate(String receiver) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long getdate = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select senddate from bbs_message where receiver = ? order by senddate desc");
            pstmt.setString(1, receiver);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                getdate = rs.getLong("senddate");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return new Timestamp(getdate);
    }

    public void updateMessageDate(String receiver) throws AddressListException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        long nowtime = System.currentTimeMillis();
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement("update tbl_members set readdate = ? where userid = ?");
                pstmt.setLong(1, nowtime);
                pstmt.setString(2, receiver);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private AddressList load(ResultSet rs) {
        AddressList addresslist = new AddressList();
        try {
            addresslist.setID(rs.getInt("id"));
            addresslist.setName(rs.getString("username"));
            addresslist.setEmail(rs.getString("email"));
            addresslist.setPhone(rs.getString("phone"));
            addresslist.setCorporation(rs.getString("corporation"));
            addresslist.setWriteDate(rs.getLong("createtime"));
            addresslist.setAddress(rs.getString("address"));
            addresslist.setFax(rs.getString("fax"));
            addresslist.setMobilephone(rs.getString("mobilephone"));
            addresslist.setPostCode(rs.getString("postcode"));
            addresslist.setMemberID(rs.getString("memberid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresslist;
    }

    private AddressList load_sys(ResultSet rs) {
        AddressList addresslist = new AddressList();
        try {
            addresslist.setID(rs.getInt("id"));
            addresslist.setMemberID(rs.getString("userid"));
            addresslist.setName(rs.getString("nickname"));
            addresslist.setEmail(rs.getString("email"));
            addresslist.setPhone(rs.getString("phone"));
            addresslist.setMobilephone(rs.getString("mobilephone"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresslist;
    }

    private AddressList load_msg(ResultSet rs) {
        AddressList addresslist = new AddressList();
        try {
            addresslist.setID(rs.getInt("id"));
            addresslist.setSender(rs.getString("sender")) ;
            addresslist.setReceiver(rs.getString("receiver")) ;
            addresslist.setContent(rs.getString("content")) ;
            addresslist.setSendDate(rs.getLong("senddate") ) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresslist;
    }
}
