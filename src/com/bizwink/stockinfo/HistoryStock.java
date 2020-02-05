package com.bizwink.stockinfo;

import com.bizwink.cms.util.*;
import com.bizwink.images.*;
import com.bizwink.cms.server.*;
import com.heaton.bot.HTTPSocket;

import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.io.*;
import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: xuzheming
 * Date: 2007-4-23
 * Time: 11:36:02
 * To change this template use File | Settings | File Templates.
 */
public class HistoryStock {

    public int id = 0;

    public HistoryStock(int id) {
        this.id = id;
    }

    public HistoryStock() {

    }

    public void getHistiryStockInfo(String stockcode) {
        String tempStr;
        String url;
        String stock[][] = new String[16][2];
        int posi;
        int i = 0;
        int j;

        stock[0][0] = "Last Trade:";
        stock[1][0] = "Trade Time:";
        stock[2][0] = "Change:";
        stock[3][0] = "Prev Close:";
        stock[4][0] = "Open:";
        stock[5][0] = "Bid:";
        stock[6][0] = "Ask:";
        stock[7][0] = "1y Target Est:";
        stock[8][0] = "Day's Range:";
        stock[9][0] = "52wk Range:";
        stock[10][0] = "Volume:";
        stock[11][0] = "Avg Vol (3m):";
        stock[12][0] = "Market Cap:";
        stock[13][0] = "P/E (ttm):";
        stock[14][0] = "EPS (ttm):";
        stock[15][0] = "Div & Yield:";

        //删除原来的历史数据
        deleteHistory(stockcode);

        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
        String klinepath = props.getProperty("main.db.k_line_path");
        String driver = props.getProperty("main.db.driver");
        String dburl = props.getProperty("main.db.url");
        String userid = props.getProperty("main.db.username");
        String passwd = props.getProperty("main.db.password");

        List list = new ArrayList();
        StringBuffer buf = null;
        InputStream result = null;
        BufferedReader in = null;
        String resultStr = null;
        History history = new History();
        int httpcode = 0;
        String nexturl = "http://finance.yahoo.com/q/hp?s=" + stockcode;
        while (nexturl != null && nexturl != "") {
            httpcode = 0;
            try {
                System.out.println(nexturl);
                buf = new StringBuffer();
                HTTPSocket http = new HTTPSocket();
                //http.addProxyAuthHeader();
                http.setTimeout(30000);
                http.send(nexturl, null);
                resultStr = http.getBody();
                buf.append(resultStr);
            } catch (IOException ioexp) {
                ioexp.printStackTrace();
                httpcode = -1;
            }

            if (httpcode == 0) {
                String content = "";
                String navbar = "";
                Pattern p = null;
                StringBuffer tbuf = new StringBuffer();
                posi = buf.indexOf("<table class=\"yfnc_datamodoutline1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
                tbuf.append(buf.substring(posi + "<table class=\"yfnc_datamodoutline1\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">".length()));
                posi = tbuf.indexOf("<table border=\"0\" cellpadding=\"2\" cellspacing=\"1\" width=\"100%\">");
                tbuf.replace(0,tbuf.length(),tbuf.substring(posi + "<table border=\"0\" cellpadding=\"2\" cellspacing=\"1\" width=\"100%\">".length()));
                posi = tbuf.indexOf("<table border=\"0\" cellpadding=\"2\" cellspacing=\"1\" width=\"100%\">");
                content = tbuf.substring(0,posi);
                navbar = tbuf.substring(posi + "<table border=\"0\" cellpadding=\"2\" cellspacing=\"1\" width=\"100%\">".length());
                posi = navbar.indexOf("</table>");
                navbar = navbar.substring(0,posi);
                posi = content.indexOf("</table>");
                content = content.substring(0,posi);
                content = StringUtil.replace(content, "</tr>", "");
                p = Pattern.compile("<tr>");
                String[] datarow = p.split(content);
                for (j = 0; j < datarow.length; j++) {
                    insertStockData(history.getId(), stockcode, datarow[j]);
                }

                p = Pattern.compile("<a[^<>]*href[^>]*>Next</a>");
                java.util.regex.Matcher matcher1 = null;
                matcher1 = p.matcher(navbar);
                String tempbuf = "";
                if (matcher1.find()) {
                    tempbuf = navbar.substring(matcher1.start(), matcher1.end());
                }
                System.out.println(tempbuf);
                nexturl = "";
                posi = tempbuf.lastIndexOf("\">Next</a>");
                if (posi > -1) {
                    nexturl = tempbuf.substring(0, posi);
                    posi = tempbuf.lastIndexOf("href=\"");
                    if (posi > -1)
                        nexturl = "http://finance.yahoo.com" + StringUtil.replace(nexturl.substring(posi + 6), "&amp;", "&");
                }
            }
        }

        //声成K线图
        try {
            FileOutputStream day_k_line = new FileOutputStream(klinepath + File.separator  + stockcode + "_day.jpg");
            FileOutputStream week_k_line = new FileOutputStream(klinepath + File.separator + stockcode + "_week.jpg");
            //FileOutputStream month_k_line = new FileOutputStream(klinepath + File.separator  + stockcode + "_month.jpg");
            StockGraphProducer producer = new StockGraphProducer();
            producer.createDayK_LineImage(day_k_line, stockcode);
            producer.createWeekK_LineImage(week_k_line, stockcode);
            //producer.createMonthK_LineImage(month_k_line, stockcode);
            day_k_line.close();
            week_k_line.close();
            //month_k_line.close();
        } catch (IOException ioexp) {
            ioexp.printStackTrace();
        }
    }

    public void getAllHistiryStockInfo() {
        String tempStr;
        String url;
        String stock[][] = new String[16][2];
        int posi;
        int i = 0;
        int j;
        String stockcode="";

        stock[0][0] = "Last Trade:";
        stock[1][0] = "Trade Time:";
        stock[2][0] = "Change:";
        stock[3][0] = "Prev Close:";
        stock[4][0] = "Open:";
        stock[5][0] = "Bid:";
        stock[6][0] = "Ask:";
        stock[7][0] = "1y Target Est:";
        stock[8][0] = "Day's Range:";
        stock[9][0] = "52wk Range:";
        stock[10][0] = "Volume:";
        stock[11][0] = "Avg Vol (3m):";
        stock[12][0] = "Market Cap:";
        stock[13][0] = "P/E (ttm):";
        stock[14][0] = "EPS (ttm):";
        stock[15][0] = "Div & Yield:";

        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
        String klinepath = props.getProperty("main.db.k_line_path");
        String driver = props.getProperty("main.db.driver");
        String dburl = props.getProperty("main.db.url");
        String userid = props.getProperty("main.db.username");
        String passwd = props.getProperty("main.db.password");

        List list = new ArrayList();
        StringBuffer buf = new StringBuffer();
        InputStream result = null;
        BufferedReader in = null;
        String resultStr = null;
        History history = new History();
        int httpcode = 0;

        //删除原来的历史数据
        ISpiderManager spiderMgr = SpiderPeer.getInstance();
        List stockbaseinfos = null;
        try {
            stockbaseinfos = spiderMgr.getSstockbaseInfo();
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        for (int num=0; num<stockbaseinfos.size(); num++) {
            stockBaseinfo sb=new stockBaseinfo();
            sb = (stockBaseinfo)stockbaseinfos.get(num);
            stockcode= sb.getTicker();
            int posii=stockcode.indexOf(">");
            if(posii>-1) stockcode=stockcode.substring(posii+1);
            deleteHistory(stockcode);
            String nexturl = "http://finance.yahoo.com/q/hp?s=" + stockcode;
            while (nexturl != null && nexturl != "") {
                httpcode = 0;
                try {
                    HTTPSocket http = new HTTPSocket();
                    //http.addProxyAuthHeader();
                    http.setTimeout(30000);
                    http.send(nexturl, null);
                    resultStr = http.getBody();
                    buf.append(resultStr);
                } catch (IOException ioexp) {
                    ioexp.printStackTrace();
                    httpcode = -1;
                }

                if (httpcode == 0) {
                    String tempbuf = "";
                    Pattern p = null;
                    StringBuffer tbuf = new StringBuffer();
                    posi = buf.indexOf("<table border=\"0\" cellpadding=\"3\" cellspacing=\"1\" width=\"100%\">");
                    tbuf.append(buf.substring(posi + "<table border=\"0\" cellpadding=\"3\" cellspacing=\"1\"width=\"100%\">".length()));
                    posi = tbuf.indexOf("</table>");
                    buf = new StringBuffer();
                    buf.append(tbuf.substring(0, posi));
                    tempbuf = buf.toString();
                    tempbuf = StringUtil.replace(tempbuf, "</tr>", "");
                    p = Pattern.compile("<tr>");

                    String[] datarow = p.split(tempbuf);
                    try {
                        for (j = 0; j < datarow.length; j++) {
                            insertStockData(history.getId(), stockcode, datarow[j]);
                        }
                    } catch (java.lang.NumberFormatException exp) {
                        exp.printStackTrace();
                        break;
                    }

                    p = Pattern.compile("<(\\s*)a(\\s*)href[^>]*>Next</a>");
                    java.util.regex.Matcher matcher1 = null;
                    matcher1 = p.matcher(resultStr);
                    if (matcher1.find()) {
                        tempbuf = resultStr.substring(matcher1.start(), matcher1.end());
                    }

                    nexturl = "";
                    posi = tempbuf.lastIndexOf("\">Next</a>");
                    if (posi > -1) {
                        nexturl = tempbuf.substring(0, posi);
                        posi = tempbuf.lastIndexOf("href=\"");
                        if (posi > -1)
                            nexturl = "http://finance.yahoo.com" + StringUtil.replace(nexturl.substring(posi + 6), "&amp;", "&");
                    }
                }
            }

            //声成K线图
            try {
                FileOutputStream day_k_line = new FileOutputStream(klinepath + File.separator  + stockcode + "_day.jpg");
                FileOutputStream week_k_line = new FileOutputStream(klinepath + File.separator + stockcode + "_week.jpg");
                //FileOutputStream month_k_line = new FileOutputStream(klinepath + File.separator  + stockcode + "_month.jpg");
                StockGraphProducer producer = new StockGraphProducer();
                producer.createDayK_LineImage(day_k_line, stockcode);
                producer.createWeekK_LineImage(week_k_line, stockcode);
                //producer.createMonthK_LineImage(month_k_line, stockcode);
                day_k_line.close();
                week_k_line.close();
                //month_k_line.close();
            } catch (IOException ioexp) {
                ioexp.printStackTrace();
            }
        }
    }

    private static Connection getConnection(String driver, String url, String userid, String passwd) {
        Connection conn = null;
        try {
            Class.forName(driver);
            String dburl = url;
            String dbuser = userid;
            String dbpass = passwd;
            conn = DriverManager.getConnection(dburl, dbuser, dbpass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static String SQL_GETHISTORYSTOCKINFO = "select id,stocknum,hisurl from stock_location where status=1 and hisstatus=2 and id=?";

    public static List getHistoryStorck(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
            String driver = props.getProperty("main.db.driver");
            String dburl = props.getProperty("main.db.url");
            String userid = props.getProperty("main.db.username");
            String passwd = props.getProperty("main.db.password");
            conn = getConnection(driver, dburl, userid, passwd);
            pstmt = conn.prepareStatement(SQL_GETHISTORYSTOCKINFO);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                History history = new History();
                history.setId(rs.getInt(1));
                history.setStocknum(rs.getString(2));
                history.setHisurl(rs.getString(3));
                list.add(history);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

    private static String PREV_CLOSE_PRICE = "insert into stock_price(id, pre_close_price, max_price, min_price," +
            "close_price, exchanges, thechange, gid, stockcode,thedate, status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static void insertStockData(int gid, String stockcode, String stockstr) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        float m_open = 0;
        float m_close = 0;
        float m_maxPrice = 0.00f;
        float m_minPrice = 0.00f;
        long m_exchanges = 0;
        int m_year = 0;
        int m_month = 0;
        int m_day = 0;
        String s_year = null;
        String s_month = null;
        String s_day = null;

        Pattern p = Pattern.compile("<(\\s*)td[^>]*>", Pattern.CASE_INSENSITIVE);
        String buf[] = p.split(stockstr);
        if (buf.length > 3) {
            int posi = buf[0].indexOf("</td>");
            posi = buf[1].indexOf("</td>");
            if (posi > 0) {
                buf[1] = buf[1].substring(0, posi);
                posi = buf[1].lastIndexOf(",");
                if (posi > 0) {
                    s_year = buf[1].substring(posi + 1).trim();
                    buf[1] = buf[1].substring(0, posi);
                    posi = buf[1].indexOf(" ");
                    s_month = buf[1].substring(0, posi).trim();
                    s_day = buf[1].substring(posi + 1).trim();
                    if (s_month.equalsIgnoreCase("Dec")) {
                        m_month = 11;
                    } else if (s_month.equalsIgnoreCase("Nov")) {
                        m_month = 10;
                    } else if (s_month.equalsIgnoreCase("Oct")) {
                        m_month = 9;
                    } else if (s_month.equalsIgnoreCase("Sep")) {
                        m_month = 8;
                    } else if (s_month.equalsIgnoreCase("Aug")) {
                        m_month = 7;
                    } else if (s_month.equalsIgnoreCase("Jul")) {
                        m_month = 6;
                    } else if (s_month.equalsIgnoreCase("Jun")) {
                        m_month = 5;
                    } else if (s_month.equalsIgnoreCase("May")) {
                        m_month = 4;
                    } else if (s_month.equalsIgnoreCase("Apr")) {
                        m_month = 3;
                    } else if (s_month.equalsIgnoreCase("Mar")) {
                        m_month = 2;
                    } else if (s_month.equalsIgnoreCase("Feb")) {
                        m_month = 1;
                    } else if (s_month.equalsIgnoreCase("Jan")) {
                        m_month = 0;
                    }
                    m_day = Integer.parseInt(s_day);
                    m_year = Integer.parseInt(s_year);
                }
            }

            posi = buf[2].indexOf("</td>");
            if (posi > 0 && buf[2].indexOf("Open") == -1) {
                buf[2] = buf[2].substring(0, posi);
                buf[2] = StringUtil.replace(buf[2],",","");
                m_open = Float.parseFloat(buf[2]);
            }

            posi = buf[3].indexOf("</td>");
            if (posi > 0 && buf[3].indexOf("High") == -1) {
                buf[3] = buf[3].substring(0, posi);
                buf[3] = StringUtil.replace(buf[3],",","");
                m_maxPrice = Float.parseFloat(buf[3]);
            }

            posi = buf[4].indexOf("</td>");
            if (posi > 0 && buf[4].indexOf("Low") == -1) {
                buf[4] = buf[4].substring(0, posi);
                buf[4] = StringUtil.replace(buf[4],",","");
                m_minPrice = Float.parseFloat(buf[4]);
            }

            posi = buf[5].indexOf("</td>");
            if (posi > 0 && buf[5].indexOf("Close") == -1) {
                buf[5] = buf[5].substring(0, posi);
                buf[5] = StringUtil.replace(buf[5],",","");
                m_close = Float.parseFloat(buf[5]);
            }

            posi = buf[6].indexOf("</td>");
            if (posi > 0 && buf[6].indexOf("Volume") == -1) {
                buf[6] = buf[6].substring(0, posi);
                buf[6] = StringUtil.replace(buf[6], ",", "");
                m_exchanges = Long.parseLong(buf[6]);
            }

            /*
            System.out.println(getMaxClosePriceID() + 1);
            System.out.println("m_open=" + m_open);
            System.out.println("m_maxPrice=" + m_maxPrice);
            System.out.println("m_minPrice=" + m_minPrice);
            System.out.println("m_close=" + m_close);
            System.out.println("m_exchanges=" + m_exchanges);
            System.out.println("m_maxPrice - m_minPrice=" + (m_maxPrice - m_minPrice));
            System.out.println("m_year = " + m_year);
            System.out.println("m_month = " + m_month);
            System.out.println("m_day = " + m_day);
            */
            if (buf[6].indexOf("Volume") == -1) {
                try {
                    Date date = new Date(m_year-1900, m_month, m_day);
                    date = new Date(date.getTime());
                    System.out.println(date + " = " + m_close);
                    com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
                    String driver = props.getProperty("main.db.driver");
                    String dburl = props.getProperty("main.db.url");
                    String userid = props.getProperty("main.db.username");
                    String passwd = props.getProperty("main.db.password");
                    conn = getConnection(driver, dburl, userid, passwd);
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement(PREV_CLOSE_PRICE);
                    pstmt.setInt(1, getMaxClosePriceID() + 1);
                    pstmt.setFloat(2, m_open);
                    pstmt.setFloat(3, m_maxPrice);
                    pstmt.setFloat(4, m_minPrice);
                    pstmt.setFloat(5, m_close);
                    pstmt.setLong(6, m_exchanges);
                    pstmt.setDouble(7, m_maxPrice - m_minPrice);
                    pstmt.setInt(8, gid);
                    pstmt.setString(9, stockcode);
                    pstmt.setDate(10, date);
                    pstmt.setInt(11, 0);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                } catch (Exception e) {
                    try {
                        conn.rollback();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                } finally {
                    if (conn != null)
                        try {
                            conn.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Close the connection failed!");
                        }
                }
            }
        }
    }

    private static int getMaxClosePriceID() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = 0;
        try {
            com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
            String driver = props.getProperty("main.db.driver");
            String dburl = props.getProperty("main.db.url");
            String userid = props.getProperty("main.db.username");
            String passwd = props.getProperty("main.db.password");
            conn = getConnection(driver, dburl, userid, passwd);
            pstmt = conn.prepareStatement("select max(id) as maxid from stock_price");
            rs = pstmt.executeQuery();
            if (rs.next())
                id = rs.getInt("maxid");
            else
                id = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Close the connection failed");
                }
        }
        return id;
    }

    //delete history data

    private static String SQL_DELETEHISTORYDATA = "delete from stock_price where stockcode = ?";

    private void deleteHistory(String stockcode) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            try {
                com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
                String driver = props.getProperty("main.db.driver");
                String dburl = props.getProperty("main.db.url");
                String userid = props.getProperty("main.db.username");
                String passwd = props.getProperty("main.db.password");
                conn = getConnection(driver, dburl, userid, passwd);
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELETEHISTORYDATA);
                pstmt.setString(1, stockcode);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update actionflag

    private static String SQL_UPDATEACTIONFLAG = "update stock_location set actionflag = 0 where id=?";

    public void updateActionFlag(int gid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            try {
                com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
                String driver = props.getProperty("main.db.driver");
                String dburl = props.getProperty("main.db.url");
                String userid = props.getProperty("main.db.username");
                String passwd = props.getProperty("main.db.password");
                conn = getConnection(driver, dburl, userid, passwd);
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATEACTIONFLAG);
                pstmt.setInt(1, gid);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
            finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

