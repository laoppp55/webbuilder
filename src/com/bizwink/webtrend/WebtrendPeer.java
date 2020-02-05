package com.bizwink.webtrend;
/**
 * MarkerPeer.java
 */

import java.util.*;
import java.lang.*;

import java.sql.*;

import com.bizwink.cms.server.*;

/**
 * Title:        Cms server
 * Description:  bizwink Cms Server
 * Copyright:    Copyright (c) 2000
 * Company:      bizwink software inc
 *
 * @author peter song
 * @version 1.0
 */

public class WebtrendPeer implements IWebtrendManager {
    PoolServer cpool;

    public WebtrendPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IWebtrendManager getInstance() {
        return CmsServer.getInstance().getFactory().getWebtrendManager();
    }

    private static final String pvTop50 = "select * from log_detail where userid = ? ORDER BY cast(pageview as int) desc";

    public List reportTop50pv(String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend detail;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(pvTop50);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    detail = load(rs);
                    list.add(detail);
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List search(String fromdate, String todate, String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("SELECT urlname, SUM(cast(pageview AS int)) as pv, SUM(cast(usersession AS int)) as us FROM log_detail WHERE LOGDATE >= ? AND LOGDATE <= ? AND userid = ? GROUP BY urlname ORDER BY pv DESC");

                pstmt.setString(1, fromdate);
                pstmt.setString(2, todate);
                pstmt.setString(3, id);
                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadurl(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //search channel items of the user defined from the log_subconclusion
    public List searchchannelitem(String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_subconclusion where USID = ? ");
                pstmt.setString(1, id);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loaditem(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //search userip by the userid from the log_userip
    public List searchcountryid(String id, String date) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select sum(cast(ucount as int)),countryid from view_detail where uuserid = ? and ulogdate = ? group by countryid");
                pstmt.setString(1, id);
                pstmt.setString(2, date);

                ResultSet rs;
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    srch = loadcountryid(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //search provinceid by the userid from the view_detail
    public List searchprovinceid(String id, String date) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select sum(cast(ucount as int)),provinceid from view_detail where uuserid = ? and ulogdate = ? group by provinceid");
                pstmt.setString(1, id);
                pstmt.setString(2, date);

                ResultSet rs;
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    srch = loadprovinceid(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //search ip of the user from the log_ip
    public List searchip(String ip) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_ip where beginip <= ? and endip >= ? ");
                pstmt.setString(1, ip);
                pstmt.setString(2, ip);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadip(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //search url from the log_detail
    public List searchurl(String url, String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_detail where urlname = ? and userid = ? ORDER BY logdate desc");
                pstmt.setString(1, url);
                pstmt.setString(2, id);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = load(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List searchurl(String url, String id, int flag) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_detail where urlname = ? and userid = ? ORDER BY cast(pageview as int) desc");
                pstmt.setString(1, url);
                pstmt.setString(2, id);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = load(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List searchurl(String url, String id, int flag, int tfalg) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_detail where urlname = ? and userid = ? ORDER BY cast(usersession as int) desc");
                pstmt.setString(1, url);
                pstmt.setString(2, id);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = load(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //search country from log_country
    public Webtrend searchcountry(int i) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        Webtrend srch = new Webtrend();

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_country where cid = ? ");
                pstmt.setInt(1, i);

                ResultSet rs;
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    srch = loadcountry(rs);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return srch;
    }

    //search city from log_city
    public Webtrend searchcity(int i) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        Webtrend srch = new Webtrend();

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_city where cityid = ? ");
                pstmt.setInt(1, i);

                ResultSet rs;
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    srch = loadcity(rs);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return srch;
    }

    //search conclusion the log_conclusion
    public List searchconclusion(String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_conclusion where USERID = ? ORDER BY logdate desc");
                pstmt.setString(1, id);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadconclusion(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List searchconclusion(String id, int i) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_conclusion where USERID = ? ORDER BY cast(TOTAL_PAGEVIEW as int) desc");
                pstmt.setString(1, id);

                ResultSet rs;

                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadconclusion(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List searchconclusion(String id, int i, int j) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_conclusion where USERID = ? ORDER BY cast(TOTAL_USERSESSION as int) desc");
                pstmt.setString(1, id);

                ResultSet rs;
                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadconclusion(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List searchconclusion(String id, int i, int j, int k) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_conclusion where USERID = ? ORDER BY AVER_SESSIONTIME desc");
                pstmt.setString(1, id);

                ResultSet rs;
                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadconclusion(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List searchconclusion(String id, int i, int j, int k, int m) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_conclusion where USERID = ? ORDER BY cast(UNI_USER as int) desc");
                pstmt.setString(1, id);

                ResultSet rs;
                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadconclusion(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();

            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }


    public List searchconclusion(String id, String begindate, String enddate) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_conclusion where USERID = ? and logdate >= ? and logdate <= ? ORDER BY logdate desc");
                pstmt.setString(1, id);
                pstmt.setString(2, begindate);
                pstmt.setString(3, enddate);

                ResultSet rs;
                for (rs = pstmt.executeQuery(); rs.next();) {
                    srch = loadconclusion(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }


    //search channel pageview from log_detail
    public List searchchannelurl(String id, String date) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend srch;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select sum(cast(pageview as int)) as pv,ch_name from view_channel where USERID = ? and logdate = ? group by ch_name ORDER BY pv desc");
                pstmt.setString(1, id);
                pstmt.setString(2, date);

                ResultSet rs;
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    srch = loadchannel(rs);
                    list.add(srch);
                }
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List reportPerDay(String theDate, String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend perday;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select * from log_detail where LOGDATE = ? and userid = ? ORDER BY cast(pageview as int) desc");
                pstmt.setString(1, theDate);
                pstmt.setString(2, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    perday = load(rs);
                    list.add(perday);
                }
                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    private static final String allpv = "select * from log_detail where userid = ? ORDER BY cast(pageview as int) desc";

    public List reportAll(String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend detail;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(allpv);
                pstmt.setString(1, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    detail = load(rs);
                    list.add(detail);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
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
            System.out.println(e.getMessage());
        }
        return list;
    }

    //for pagination
    public List getWebtrendList(int startIndex, int numResult, String id) throws WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from log_detail where userid = ? order by cast(pageview as int) desc");
            pstmt.setString(1, id);
            ResultSet rs;

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                webtrend = load(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public List getCurrentWebtrendList(String theDate, int startIndex, int numResult, String id) throws WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from log_detail where  LOGDATE = ? and userid = ? order by cast(pageview as int) desc");
            pstmt.setString(1, theDate);
            pstmt.setString(2, id);
            ResultSet rs;

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                webtrend = load(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public List getsearchCurrentWebtrendList(String fromdate, String todate, int startIndex, int numResult, String id) throws WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT urlname, SUM(cast(pageview AS int)) as pv, SUM(cast(usersession AS int)) as us FROM log_detail WHERE LOGDATE >= ? AND LOGDATE <= ? AND userid = ? GROUP BY urlname ORDER BY pv DESC");
            pstmt.setString(1, fromdate);
            pstmt.setString(2, todate);
            pstmt.setString(3, id);
            ResultSet rs;

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                webtrend = loadurl(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    //add 2003-08-16 by EricDu
    public List reportrefer(String date, String id) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT SUM(counter) as cou, urlname, domainname FROM log_refer WHERE referdate = ? AND userid = ? GROUP BY domainname, urlname");
            pstmt.setString(1, date);
            pstmt.setString(2, id);
            ResultSet rs;

            rs = pstmt.executeQuery();
            while (rs.next()) {
                webtrend = loadrefer(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public List getsearchCurrentReferList(int startIndex, int numResult, String date, String id) throws WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT SUM(counter) as cou, urlname, domainname FROM log_refer WHERE referdate = ? AND userid = ? GROUP BY domainname, urlname");
            pstmt.setString(1, date);
            pstmt.setString(2, id);
            ResultSet rs;

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                webtrend = loadrefer(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public List reportreferdetail(String date, String id, String domain, String url) throws com.bizwink.webtrend.WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT urlname,upurlname,counter,domainname FROM log_refer WHERE referdate = ? AND userid = ? AND domainname = ? AND urlname = ? ");
            pstmt.setString(1, date);
            pstmt.setString(2, id);
            pstmt.setString(3, domain);
            pstmt.setString(4, url);
            //pstmt.setString(3, domainname);
            ResultSet rs;

            rs = pstmt.executeQuery();

            while (rs.next()) {
                webtrend = loadreferdetail(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public List getsearchCurrentReferListDetail(int startIndex, int numResult, String date, String id, String domain, String url) throws WebtrendException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        Webtrend webtrend;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT urlname,upurlname,counter,domainname FROM log_refer WHERE referdate = ? AND userid = ? AND domainname = ? AND urlname = ?");
            pstmt.setString(1, date);
            pstmt.setString(2, id);
            pstmt.setString(3, domain);
            pstmt.setString(4, url);
            ResultSet rs;

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                webtrend = loadreferdetail(rs);
                list.add(webtrend);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing pool connection".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    Webtrend loadrefer(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setUCount(rs.getString("cou"));
            detail.setUrlName(rs.getString("urlname"));
            detail.setDomainName(rs.getString("domainname"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }

    Webtrend loadreferdetail(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setUrlName(rs.getString("urlname"));
            detail.setUpUrlName(rs.getString("upurlname"));
            detail.setUCount(rs.getString("counter"));
            detail.setDomainName(rs.getString("domainname"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }

    Webtrend loadconclusion(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setUserID(rs.getString("userid"));
            detail.setDomainName(rs.getString("domainname"));
            detail.setTotalPageView(rs.getString("total_pageview"));
            detail.setTotalUsersession(rs.getString("total_usersession"));
            detail.setAverUserTime(rs.getString("aver_sessiontime"));
            detail.setUniqueUser(rs.getString("UNI_USER"));
            detail.setLogDate(rs.getString("logdate"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }

    Webtrend load(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setPageView(rs.getString("pageview"));
            detail.setLogDate(rs.getString("logdate"));
            detail.setUrlName(rs.getString("urlname"));
            detail.setUsersession(rs.getString("usersession"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }

    Webtrend loadchannel(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setPageView(rs.getString(1));
            detail.setCh_Name(rs.getString(2));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }


    Webtrend loadurl(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setUrlName(rs.getString(1));
            detail.setPV(rs.getInt(2));
            detail.setUS(rs.getInt(3));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }

    Webtrend loadrs(ResultSet rs)
            throws SQLException {
        Webtrend webtrend = new Webtrend();
        webtrend.setUrlName(rs.getString("urlname"));
        webtrend.setPageView(rs.getString("pageview"));
        webtrend.setUsersession(rs.getString("usersession"));
        return webtrend;
    }

    Webtrend loadcountry(ResultSet rs)
            throws SQLException {
        Webtrend webtrend = new Webtrend();
        webtrend.setCID(rs.getString("cid"));
        webtrend.setCtry(rs.getString("ctry"));
        return webtrend;
    }

    Webtrend loadcity(ResultSet rs)
            throws SQLException {
        Webtrend webtrend = new Webtrend();
        webtrend.setCityID(rs.getString("cityid"));
        webtrend.setCityName(rs.getString("cityname"));
        return webtrend;
    }

    Webtrend loaditem(ResultSet rs) throws SQLException {
        Webtrend detail = new Webtrend();
        try {
            detail.setUsID(rs.getString("usid"));
            detail.setItem_Name(rs.getString("ITEM_NAME"));
            detail.setCh_Name(rs.getString("ch_name"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return detail;
    }

    Webtrend loadNetInfo(ResultSet rs) throws SQLException {
        Webtrend webtrend = new Webtrend();
        try {
            webtrend.setUID(rs.getString("uid"));
            webtrend.setNDomainName(rs.getString("domainname"));
            webtrend.setNIpAddress(rs.getString("ipaddress"));
            webtrend.setFtpName(rs.getString("ftpusername"));
            webtrend.setFtpPassword(rs.getString("ftppassword"));
            webtrend.setOrg_Log_Path(rs.getString("orglogpath"));
            webtrend.setLog_Type(rs.getString("logtype"));
            webtrend.setReq_Type(rs.getString("reqtype"));
            webtrend.setRej_Type(rs.getString("rejtype"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return webtrend;
    }

    Webtrend loadip(ResultSet rs) throws SQLException {
        Webtrend webtrend = new Webtrend();
        try {
            webtrend.setBeginIP(rs.getString("beginip"));
            webtrend.setEndIP(rs.getString("endip"));
            webtrend.setContinent(rs.getString("continent"));
            webtrend.setCity(rs.getString("city"));
            webtrend.setCountryID(rs.getInt("countryid"));
            webtrend.setProvinceID(rs.getInt("provinceid"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return webtrend;
    }

    Webtrend loadcountryid(ResultSet rs) throws SQLException {
        Webtrend webtrend = new Webtrend();
        try {
            webtrend.setUCount(rs.getString(1));
            webtrend.setIDCountry(rs.getInt(2));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return webtrend;
    }

    Webtrend loadprovinceid(ResultSet rs) throws SQLException {
        Webtrend webtrend = new Webtrend();
        try {
            webtrend.setUCount(rs.getString(1));
            webtrend.setProvinceID(rs.getInt(2));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return webtrend;
    }
}