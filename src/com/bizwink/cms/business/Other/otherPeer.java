package com.bizwink.cms.business.Other;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.business.Product.*;

public class otherPeer implements IOtherManager {
    PoolServer cpool;

    public otherPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IOtherManager getInstance() {
        return CmsServer.getInstance().getFactory().getOtherManager();
    }

    public List getGHList(int startindex, int range, String sql) throws OtherException {
        List list = new ArrayList();
        Other other;
        String sqlstr = "select * from en_gonghuo";
        if (sql != null && !"".equals(sql)) sqlstr = sql;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();

            if (range == 0) {
                while (rs.next()) {
                    other = load_gh(rs);
                    list.add(other);
                }
            } else {
                for (int i = 0; i < startindex; i++)
                    rs.next();
                for (int i = 0; i < range && rs.next(); i++) {
                    other = load_gh(rs);
                    list.add(other);
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

    private Other load_gh(ResultSet rs) {
        Other other = new Other();
        try {
            other.setGH_ID(rs.getInt("id"));
            other.setGH_Email(rs.getString("email"));
            other.setGH_Kind(rs.getString("kind"));
            other.setGH_Lianxiren(rs.getString("lianxiren"));
            other.setGH_Notes(rs.getString("notes"));
            other.setGH_Postcode(rs.getString("postcode"));
            other.setGH_Phone(rs.getString("phone"));
            other.setGH_Name(rs.getString("name"));
            other.setGH_Adderss(rs.getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return other;
    }

    public void newGH(Other other) throws OtherException {
        String sqlstr = "insert into en_gonghuo(id,kind,email,lianxiren,notes,postcode,phone,name,address)" +
                " values( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt;
        int maxid = this.getMaxGongHuoID();

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, maxid);
                pstmt.setString(2, other.getGH_Kind());
                pstmt.setString(3, other.getGH_Email());
                pstmt.setString(4, other.getGH_Lianxiren());
                pstmt.setString(5, other.getGH_Notes());
                pstmt.setString(6, other.getGH_Postcode());
                pstmt.setString(7, other.getGH_Phone());
                pstmt.setString(8, other.getGH_Name());
                pstmt.setString(9, other.getGH_Address());
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
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getMaxGongHuoID() {
        Connection conn = null;
        PreparedStatement pstmt;
        int maxid = 0;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select max(id) as id from en_gonghuo");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maxid = rs.getInt(1) + 1;
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
        return maxid;
    }

    public void delGH(int id) throws OtherException {
        String sqlstr = "delete from en_gonghuo where id=?";
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, id);
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
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getAccountList(String simname, int kind, int flag, int startindex, int range) throws OtherException {
        List list = new ArrayList();
        Other other;
        String sqlstr = "select orderid,totalfee,name from tbl_orders";
        if (kind == 1) sqlstr = sqlstr + " where orderid = ? ";
        sqlstr = sqlstr + " order by createdate desc";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int orderid;
        try {
            orderid = Integer.parseInt(simname);
        } catch (Exception e) {
            orderid = 0;
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);

            // System.out.println("kind=" + kind);

            if (kind == 1) {
                pstmt.setInt(1, orderid);
            }
            rs = pstmt.executeQuery();

            if (range == 0) {
                while (rs.next()) {
                    other = load_CountMoney(rs);
                    list.add(other);
                }
            } else {
                for (int i = 0; i < startindex; i++)
                    rs.next();
                for (int i = 0; i < range && rs.next(); i++) {
                    other = load_CountMoney(rs);
                    list.add(other);
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

    private Other load_CountMoney(ResultSet rs) {
        Other other = new Other();
        try {
            other.setTotalFee(rs.getFloat("totalfee"));
            other.setNumber(rs.getLong("orderid"));

            other.setUserName(rs.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return other;
    }

    public List getReceiveMoneyList(int kind, String what1, String what2, String userid, int startindex, int range) throws OtherException {
        List list = new ArrayList();
        ReceiveMoney remoney;
        String sqlstr = "select u.username,r.* from en_receivemoney r,tbl_userinfo u" +
                " where u.username=r.userid";
        if ((kind == 1) && (what1.indexOf("1999") == -1) && (what2.indexOf("9999") == -1))
            sqlstr = sqlstr + " and r.createdate>=to_date('" + what1 + "','YYYY-MM-DD HH24:MI:SS') and r.createdate <=to_date('" + what2 + "','YYYY-MM-DD HH24:MI:SS')";
        if (kind == 2)
            sqlstr = sqlstr + " and r.jing_ban_ren like '%" + what1 + "%' ";
        if (kind == 3) {
            if (!"".equals(what1))
                sqlstr = sqlstr + " and r.number>=" + String.valueOf(what1);
            if (!"".equals(what2))
                sqlstr = sqlstr + " and r.number<=" + String.valueOf(what2);
        }
        if (kind == 4)
            sqlstr = sqlstr + " and r.payer like '%" + what1 + "%' ";
        if (kind == 5)
            sqlstr = sqlstr + " and u.username like '%" + what1 + "%' ";
        if (!userid.equals(""))
            sqlstr = sqlstr + " and u.username='" + String.valueOf(userid) + "'";
        sqlstr = sqlstr + " order by r.createdate desc";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (range == 0) {
                while (rs.next()) {
                    remoney = load_ReceiveMoney(rs);
                    list.add(remoney);
                }
            } else {
                for (int i = 0; i < startindex; i++)
                    rs.next();
                for (int i = 0; i < range && rs.next(); i++) {
                    remoney = load_ReceiveMoney(rs);
                    list.add(remoney);
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

    private ReceiveMoney load_ReceiveMoney(ResultSet rs) {
        ReceiveMoney remoney = new ReceiveMoney();
        try {
            remoney.setID(rs.getInt("id"));
            remoney.setDescribe(rs.getString("brief"));
            remoney.setPayer(rs.getString("payer"));
            remoney.setPayway(rs.getInt("payway"));
            remoney.setJingBanRen(rs.getString("jing_ban_ren"));
            remoney.setNumber(rs.getFloat("nnumber"));
            remoney.setUnit(rs.getString("unit"));
            remoney.setCreatedate(rs.getTimestamp("createdate"));
            remoney.setOrderid(rs.getInt("orderid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remoney;
    }

    public ReceiveMoney getAReceiveMoney(int orderid) throws OtherException {
        ReceiveMoney remoney = new ReceiveMoney();
        String sqlstr = "select r.* from en_receivemoney r where orderid = ?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                remoney = load_ReceiveMoney(rs);
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
        return remoney;
    }

    public ReceiveMoney getAReceiveMoney(String userid) throws OtherException {
        ReceiveMoney remoney = new ReceiveMoney();
        String sqlstr = "select u.username,r.* from en_receivemoney r,tbl_userinfo u where u.username=r.userid " +
                "and r.userid=?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                remoney = load_ReceiveMoney(rs);
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
        return remoney;
    }

    public void newReceiveMoney(ReceiveMoney remoney) throws OtherException {
        String sqlstr = "insert into en_receivemoney(id,payer,payway,jing_ban_ren,nnumber,unit,brief,createdate,orderid) " +
                " values(?,?,?,?,?,?,?,?,?)";
        Timestamp thistime = new Timestamp(System.currentTimeMillis());
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, sequenceMgr.getSequenceNum("ReceiveMoney"));
                pstmt.setString(2, remoney.getPayer());
                pstmt.setInt(3, remoney.getPayway());
                pstmt.setString(4, remoney.getJingBanRen());
                pstmt.setFloat(5, remoney.getNumber());
                pstmt.setString(6, remoney.getUnit());
                pstmt.setString(7, remoney.getDescribe());
                pstmt.setTimestamp(9, thistime);
                pstmt.setInt(10, remoney.getOrderid());
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
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReceiveMoney(ReceiveMoney remoney) throws OtherException {
        String sqlstr = "update en_receivemoney set payer=?,payway=?,jing_ban_ren=?,nnumber=?,unit=?,brief=? where orderid=?";
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setString(1, remoney.getPayer());
                pstmt.setInt(2, remoney.getPayway());
                pstmt.setString(3, remoney.getJingBanRen());
                pstmt.setFloat(4, remoney.getNumber());
                pstmt.setString(5, remoney.getUnit());
                pstmt.setString(6, remoney.getDescribe());
                pstmt.setInt(7, remoney.getOrderid());
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
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List getUserRegTimes(long begint, long endt, int kind) throws OtherException {
        String sqlstr = "select username,createdate from tbl_userinfo where createdate>=? and createdate<=? order by createdate asc";
        List list = new ArrayList();
        List retlist = new ArrayList();
        Tongji tongji;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setTimestamp(1, new Timestamp(begint));
            pstmt.setTimestamp(2, new Timestamp(endt));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tongji = load_UserRegTimes(rs);
                list.add(tongji);
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

        int i = 0;
        int counter = 0;
        long tmpdate;
        long oneday = 24 * 3600 * 1000;
        long begint2 = begint + oneday * 7;
        Timestamp begintime = new Timestamp(begint);
        int year = begintime.getYear();
        int month = begintime.getMonth();
        int tmpyear;
        int tmpmonth;
        while (begint < endt && i < list.size()) {
            if (kind == 1) {
                tongji = (Tongji) list.get(i);
                tmpdate = tongji.getCreatedate().getTime();
                if (tmpdate < begint2) {
                    if (tmpdate >= begint)
                        counter++;
                } else {
                    tongji = new Tongji();
                    tongji.setBegintime(new Timestamp(begint));
                    tongji.setEndtime(new Timestamp(begint2));
                    tongji.setCounter(counter);
                    begint = begint + oneday * 7;
                    begint2 = begint2 + oneday * 7;
                    counter = 0;
                    retlist.add(tongji);
                    continue;
                }
            } else if (kind == 2) {
                tongji = (Tongji) list.get(i);
                tmpyear = tongji.getCreatedate().getYear();
                tmpmonth = tongji.getCreatedate().getMonth();
                if (tmpyear == year && tmpmonth == month) {
                    counter++;
                } else {
                    tongji = new Tongji();
                    tongji.setBegintime(new Timestamp(begint));
                    tongji.setCounter(counter);
                    counter = 0;
                    month++;
                    if (month == 12) {
                        year++;
                        month = 0;
                    }
                    begintime.setYear(year);
                    begintime.setMonth(month);
                    begint = begintime.getTime();
                    tongji.setEndtime(new Timestamp(begint));
                    retlist.add(tongji);
                    continue;
                }
            }
            i++;
            if (i == list.size()) {
                if (kind == 1) {
                    tongji = new Tongji();
                    tongji.setBegintime(new Timestamp(begint));
                    tongji.setEndtime(new Timestamp(begint2));
                    tongji.setCounter(counter);
                    retlist.add(tongji);
                }
                if (kind == 2) {
                    tongji = new Tongji();
                    tongji.setBegintime(new Timestamp(begint));
                    tongji.setCounter(counter);
                    month++;
                    if (month == 12) {
                        year++;
                        month = 0;
                    }
                    begintime.setYear(year);
                    begintime.setMonth(month);
                    begint = begintime.getTime();
                    tongji.setEndtime(new Timestamp(begint));
                    retlist.add(tongji);
                }
            }
        }
        return retlist;
    }

    private Tongji load_UserRegTimes(ResultSet rs) {
        Tongji tongji = new Tongji();
        try {
            tongji.setUserID(rs.getString("username"));
            tongji.setCreatedate(rs.getTimestamp("createdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongji;
    }

    public List getOrderNums(long begint, long endt, int kind) throws SQLException {
        List retlist = new ArrayList();
        List orderlist = new ArrayList();
        List rmlist = new ArrayList();
        Tongji otongji = new Tongji();  // class tongji for ordernums
        Tongji rtongji = new Tongji();  // class tongji for receivemoneys
        Tongji tongji;   // for add to the return list
        if (kind == 2) {
            Timestamp tmpdate = new Timestamp(endt);
            int tempy = tmpdate.getYear();
            int tempm = tmpdate.getMonth();
            tempm++;
            if (tempm == 12) {
                tempm = 0;
                tempy++;
            }
            tmpdate.setYear(tempy);
            tmpdate.setMonth(tempm);
            endt = tmpdate.getTime();
        }

        try {
            orderlist = getOrderNums(begint, endt);
            rmlist = getReceiveMoneys(begint, endt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;  // for ordernums
        int t = 0;  // for receivemoneys
        int onumber = 0;
        float omoney = 0;
        float rmoney = 0;
        long tmpdate = 0;
        long oneday = 24 * 3600 * 1000;
        long begint2 = begint + oneday * 7;
        Timestamp begintime = new Timestamp(begint);
        int year = begintime.getYear();
        int month = begintime.getMonth();
        int tmpyear = 0;
        int tmpmonth = 0;
        while (begint < endt) {
            if (kind == 1) {
                if (i < orderlist.size()) {
                    otongji = (Tongji) orderlist.get(i);
                    tmpdate = otongji.getCreatedate().getTime();
                }
                if (tmpdate < begint2 && i < orderlist.size()) {
                    if (tmpdate >= begint) {
                        onumber++;
                        omoney += otongji.getTotalMoney();
                    }
                    i++;
                } else {
                    if (t < rmlist.size()) {
                        rtongji = (Tongji) rmlist.get(t);
                        tmpdate = rtongji.getCreatedate().getTime();
                    }
                    if (tmpdate < begint2 && t < rmlist.size()) {
                        if (tmpdate >= begint) {
                            rmoney += rtongji.getReceiveMoney();
                        }
                        t++;
                    } else {
                        tongji = new Tongji();
                        tongji.setBegintime(new Timestamp(begint));
                        tongji.setEndtime(new Timestamp(endt));
                        tongji.setTotalMoney(omoney);
                        tongji.setOrderNum(onumber);
                        tongji.setReceiveMoney(rmoney);
                        omoney = 0;
                        onumber = 0;
                        rmoney = 0;
                        begint = begint + oneday * 7;
                        begint2 = begint2 + oneday * 7;
                        retlist.add(tongji);
                    }
                }
            } else if (kind == 2) {
                if (i != orderlist.size()) {
                    otongji = (Tongji) orderlist.get(i);
                    tmpyear = otongji.getCreatedate().getYear();
                    tmpmonth = otongji.getCreatedate().getMonth();
                }
                if (tmpyear == year && tmpmonth == month && i != orderlist.size()) {
                    onumber++;
                    omoney += otongji.getTotalMoney();
                    i++;
                } else {
                    if (t < rmlist.size()) {
                        rtongji = (Tongji) rmlist.get(t);
                        tmpyear = rtongji.getCreatedate().getYear();
                        tmpmonth = rtongji.getCreatedate().getMonth();
                    }
                    if (tmpyear == year && tmpmonth == month && t < rmlist.size()) {
                        rmoney += rtongji.getReceiveMoney();
                        t++;
                    } else {
                        tongji = new Tongji();
                        tongji.setBegintime(new Timestamp(begint));
                        tongji.setOrderNum(onumber);
                        tongji.setTotalMoney(omoney);
                        tongji.setReceiveMoney(rmoney);
                        rmoney = 0;
                        omoney = 0;
                        onumber = 0;
                        month++;
                        if (month == 12) {
                            year++;
                            month = 0;
                        }
                        begintime.setYear(year);
                        begintime.setMonth(month);
                        begint = begintime.getTime();
                        tongji.setEndtime(new Timestamp(begint));
                        retlist.add(tongji);
                    }
                }
            }
        }
        return retlist;
    }

    private List getOrderNums(long begint, long endt) {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Tongji tongji;
        String sqlstr = "select totalfee,createdate from tbl_orders where createdate>=? " +
                " and createdate<=? order by createdate asc";
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setTimestamp(1, new Timestamp(begint));
            pstmt.setTimestamp(2, new Timestamp(endt));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tongji = load_OrderNums(rs);
                list.add(tongji);
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
        return list;
    }

    private Tongji load_OrderNums(ResultSet rs) {
        Tongji tongji = new Tongji();
        try {
            tongji.setTotalMoney(rs.getFloat("totalfee"));
            tongji.setCreatedate(rs.getTimestamp("createdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongji;
    }

    private List getReceiveMoneys(long begint, long endt) {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Tongji tongji;
        String sqlstr = "select nnumber,createdate from en_receivemoney where createdate>=? " +
                " and createdate<=? order by createdate asc";
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setTimestamp(1, new Timestamp(begint));
            pstmt.setTimestamp(2, new Timestamp(endt));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tongji = load_ReceiveMoneys(rs);
                list.add(tongji);
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
        return list;
    }

    private Tongji load_ReceiveMoneys(ResultSet rs) {
        Tongji tongji = new Tongji();
        try {
            tongji.setReceiveMoney(rs.getFloat("nnumber"));
            tongji.setCreatedate(rs.getTimestamp("createdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tongji;
    }

    public List getApplicationList(int kind, int userid, String username, int startindex, int range) throws OtherException {      //kind 为: 1 包修申请   2 更换申请   3 退货申请  4 处理完毕,包括批准未批准
        //userid 为0是搜索全部或username查找,有id时为单id搜索
        if (username == null || "null".equals(username)) username = "";
        String sqlstr = "select a.*,u.username,p.maintitle productname " +
                " from en_applicationmanage a,tbl_userinfo u,tbl_article p " +
                " where a.userid=u.username and a.productid=p.id";
        if (kind == 1) sqlstr = sqlstr + " and a.repairflag=1";
        if (kind == 2) sqlstr = sqlstr + " and a.exchangeflag=1";
        if (kind == 3) sqlstr = sqlstr + " and a.backflag=1";
        if (kind == 4) sqlstr = sqlstr + " and (a.repairflag<>1 and a.exchangeflag<>1 and a.backflag<>1)";
        if (userid == 0)
            sqlstr = sqlstr + " and u.username like '%" + username + "%'";
        else
            sqlstr = sqlstr + " and a.userid=" + String.valueOf(userid);
        if (kind == 4)
            sqlstr = sqlstr + " order by a.dealdate desc";
        else
            sqlstr = sqlstr + " order by a.createdate desc";
        List list = new ArrayList();
        Application appli;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (range == 0) {
                while (rs.next()) {
                    appli = load_Application(rs);
                    list.add(appli);
                }
            } else {
                for (int i = 0; i < startindex; i++)
                    rs.next();
                for (int i = 0; i < range && rs.next(); i++) {
                    appli = load_Application(rs);
                    list.add(appli);
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

    private Application load_Application(ResultSet rs) {
        Application appli = new Application();
        try {
            appli.setID(rs.getInt("id"));
            appli.setBackFlag(rs.getInt("backflag"));
            appli.setExchangeFlag(rs.getInt("exchangeflag"));
            appli.setRegpairFlag(rs.getInt("repairflag"));
            appli.setNotes(rs.getString("notes"));
            appli.setOrderID(rs.getInt("orderid"));
            appli.setProductID(rs.getInt("productid"));
            appli.setProductName(rs.getString("productname"));
            appli.setUserID(rs.getInt("userid"));
            appli.setUserName(rs.getString("username"));
            appli.setCreatedate(rs.getTimestamp("createdate"));
            appli.setDealdate(rs.getTimestamp("dealdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appli;
    }

    public void makeDealed(int id, int kind, int status) throws OtherException {
        String sqlstr = "update en_applicationmanage set ";
        if (kind == 1) sqlstr = sqlstr + " repairflag=?";
        if (kind == 2) sqlstr = sqlstr + " exchangeflag=?";
        if (kind == 3) sqlstr = sqlstr + " backflag=?";
        sqlstr = sqlstr + " where id=?";
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, status);
                pstmt.setInt(2, id);
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

    public int getApplicationNum(int kind, int status, int userid, String username) throws OtherException {//kind 为: 1 保修申请   2 更换申请   3 退货申请  4 处理完毕,包括批准未批准
        int num = 0;
        String sqlstr = "select count(id) from en_applicationmanage a,tbl_userinfo u where ";
        if (kind == 1) sqlstr = sqlstr + " a.repairflag=" + String.valueOf(status);
        if (kind == 2) sqlstr = sqlstr + " a.exchangeflag=" + String.valueOf(status);
        if (kind == 3) sqlstr = sqlstr + " a.backflag=" + String.valueOf(status);
        if (kind == 4) sqlstr = sqlstr + "a.repairflag<>1 and a.exchangeflag<>1 and a.backflag<>1";
        sqlstr = sqlstr + " and a.userid=u.username ";
        if (userid == 0)
            sqlstr = sqlstr + " and u.username like '%" + username + "%'";
        else
            sqlstr = sqlstr + " and a.userid=" + String.valueOf(userid);

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            if (rs.next())
                num = rs.getInt(1);
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
        return num;
    }

    public List getCountProduct() throws SQLException {
        List list = new ArrayList();
        Product product;
        String sqlstr = "select p.articleid,p.shortname,p.createdate from en_saleproduct p, " +
                " tbl_orders_detail d where p.articleid=d.productid";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                product = load_ProductFew(rs);
                list.add(product);
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

    private Product load_ProductFew(ResultSet rs) {
        Product product = new Product();
        try {
            product.setID(rs.getInt("articleid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean checkPayMoney(long orderid) throws SQLException {
        boolean payflag = false;
        String sqlstr = "select id from en_receivemoney where orderid = ?";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setFloat(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                payflag = true;
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
        return payflag;
    }

    public float getPayMoney(long orderid) throws SQLException {
        float paymoney = 0;
        String sqlstr = "select nnumber from en_receivemoney where orderid = ?";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setFloat(1, orderid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                paymoney = rs.getFloat("nnumber");
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
        return paymoney;
    }
}
