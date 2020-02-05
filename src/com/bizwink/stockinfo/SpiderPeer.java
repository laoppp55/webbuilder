package com.bizwink.stockinfo;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.util.*;
import com.bizwink.util.DBUtil;
import com.heaton.bot.StringUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * An implementation of user database peer using instantdb, an embedded java
 * SQL database.
 */

public class SpiderPeer implements ISpiderManager {
    PoolServer cpool;

    public SpiderPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ISpiderManager getInstance() {
        return (ISpiderManager) CmsServer.getInstance().getFactory().getSpiderManager();
    }

    private static String INSERT_STOCK_INFO = "insert into tbl_stock(name,last_trade,trade_time,change," +
            "Prev_Close,open_money,target_est,day_range," +
            "wk_range,volume,Avg_Vol,Market_Cap,p_e,eps,Div_Yield,datetime,nameid,danwei,id)" +
            " values( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /*private static String INSERT_STOCK_INFO = "insert into tbl_stock(name,last_trade,trade_time,change," +
            "Prev_Close,open_money,id)" +
            " values( ?, ?, ?, ?, ?, ?, ?)";
    */

    private static String GET_MAX_ID = "select max(id) from tbl_stock";

    public void insertStock(String name, String nameid, String danwei, String[][] stock) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        //格式化stock[1][1]时间字符串为交易时间
        //TO-DO the format
        /*
        Timestamp lasttradetime = format_the_lasttradetime(stock[1][1])
         */

        Timestamp datetime = new Timestamp(System.currentTimeMillis());
        int year = 2000 + datetime.getYear() - 100;
        int month = datetime.getMonth() + 1;
        int day = datetime.getDate();
        int hour = datetime.getHours();
        int minute = datetime.getMinutes();
        int second = datetime.getSeconds();
        java.sql.Timestamp thedate = new java.sql.Timestamp(new java.util.Date(year, month, day, hour, minute, second).getTime());
        String date_str = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + day;
        ResultSet rs = null;
        int id = 0;
        java.sql.Date date = new java.sql.Date(year-1900, month-1, day);

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                pstmt = conn.prepareStatement(GET_MAX_ID);
                rs = pstmt.executeQuery();
                if (rs.next()) id = rs.getInt(1) + 1;
                rs.close();
                pstmt.close();

                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(INSERT_STOCK_INFO);
                pstmt.setString(1, name);
                pstmt.setString(2, stock[0][1]);
                //pstmt.setString(3, date_str);
                pstmt.setTimestamp(3, datetime);
                pstmt.setString(4, stock[2][1]);
                pstmt.setString(5, stock[3][1]);
                pstmt.setString(6, stock[4][1]);
                pstmt.setString(7, stock[7][1]);
                pstmt.setString(8, stock[8][1]);
                pstmt.setString(9, stock[9][1]);
                pstmt.setString(10, stock[10][1]);
                pstmt.setString(11, stock[11][1]);
                pstmt.setString(12, stock[12][1]);
                pstmt.setString(13, stock[13][1]);
                pstmt.setString(14, stock[14][1]);
                pstmt.setString(15, stock[15][1]);
                pstmt.setDate(16, date);
                pstmt.setString(17, nameid);
                pstmt.setString(18, danwei);
                pstmt.setInt(19, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String UPDATE_STOCK_INFO = "update tbl_stock set last_trade = ?, trade_time = ?," +
            "change = ?, Prev_Close = ?, open_money = ?, bid = ?," +
            "ask = ?, target_est = ?, day_range = ?, wk_range = ?," +
            "volume = ?, Avg_Vol = ?, Market_Cap = ?, p_e = ?, eps = ?," +
            "Div_Yield = ?, datetime = ? where nameid = ?";

    public void updateStock(String[][] stock, String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        Timestamp datetime = new Timestamp(System.currentTimeMillis());
        int year = datetime.getYear();
        int month = datetime.getMonth();
        int day = datetime.getDate();
        java.sql.Date date = new java.sql.Date(year, month, day);

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(UPDATE_STOCK_INFO);
                pstmt.setString(1, stock[0][1]);

                pstmt.setString(2, stock[1][1]);
                pstmt.setTimestamp(3, datetime);
                pstmt.setString(4, stock[3][1]);
                pstmt.setString(5, stock[4][1]);
                pstmt.setString(6, stock[5][1]);
                pstmt.setString(7, stock[6][1]);
                pstmt.setString(8, stock[7][1]);
                pstmt.setString(9, stock[8][1]);
                pstmt.setString(10, stock[9][1]);
                pstmt.setString(11, stock[10][1]);
                pstmt.setString(12, stock[11][1]);
                pstmt.setString(13, stock[12][1]);
                pstmt.setString(14, stock[13][1]);
                pstmt.setString(15, stock[14][1]);
                pstmt.setString(16, stock[15][1]);
                pstmt.setDate(17, date);
                pstmt.setString(18, stockcode);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.out.println("Close the connection failed!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String GET_SPIDER_INFO = "select * from tbl_stock";

    public List getSpiderInfo() throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        StockInfo stock = new StockInfo();

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(GET_SPIDER_INFO);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    stock = load(rs);
                    list.add(stock);
                }

                rs.close();
                pstmt.close();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.out.println("Close the connection failed!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String GET_STOCK_INFO_BYCODE = "select * from tbl_stock where nameid = ? order by trade_time desc";

    public List getSpiderInfo(String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        StockInfo stock = new StockInfo();

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(GET_STOCK_INFO_BYCODE);
                pstmt.setString(1, stockcode);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    stock = load(rs);
                    list.add(stock);
                }

                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.out.println("Close the connection failed!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getStockpriceCount(Date StartDate, Date EndDate, String stockcode)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;
        String sql_final = "SELECT count(ID) FROM STOCK_PRICE where stockcode=? and thedate>=? and thedate<=?";
        ResultSet rs = null;
        try
        {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;
            pstmt = conn.prepareStatement(sql_final);
            pstmt.setString(1, stockcode);
            pstmt.setDate(2, StartDate);
            pstmt.setDate(3, EndDate);
            rs = pstmt.executeQuery();
            if(rs.next())
                count = rs.getInt(1);
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public long getStockpriceFormOraAndMysql(String stockcode)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String ticker=null;
        StockInfo stockInfo = null;
        String sql_final = "SELECT stockcode FROM stock_baseinfo";
        ResultSet rs = null;
        long startTime = 0;
        long endTime = 0;
        try
        {
            startTime = System.currentTimeMillis();
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;

            //conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql_final);
            rs = pstmt.executeQuery();
            if(rs.next())
                ticker = rs.getString("stockcode");
            pstmt.close();
            rs.close();

            pstmt = conn.prepareStatement("select pre_close_price from stock_price where stockcode='SOHU' limit 1,100");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                stockInfo = new StockInfo();
                stockInfo.setPrev_close(rs.getInt("pre_close_price"));
            }
            pstmt.close();
            rs.close();
            endTime = System.currentTimeMillis();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return endTime - startTime;
    }

    public List getStockprices(Date StartDate, Date EndDate, String stockCode, int startIndex, int numResults)
    {
        Connection conn = null;
        String sql_final = "SELECT ID, PRE_CLOSE_PRICE, MAX_PRICE, MIN_PRICE, CLOSE_PRICE, EXCHANGES, THECHANGE, THEDATE, stockcode FROM STOCK_PRICE WHERE " +
                "stockcode=? and thedate>=? and thedate<=?  order by thedate desc";
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        //sql_final = String.valueOf(String.valueOf(sql_final)).concat(" stockcode=?");
        //sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')>=TO_CHAR(?,'yyyy-mm-dd')");
        //sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')<=TO_CHAR(?,'yyyy-mm-dd') order by thedate desc");

        //System.out.println(sql_final);

        List list = new ArrayList();
        Stock Stockprice;
        try
        {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;

            pstmt = conn.prepareStatement(sql_final);
            pstmt.setString(1, stockCode);
            pstmt.setDate(2, StartDate);
            pstmt.setDate(3, EndDate);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++)
                rs.next();

            for(int i = 0; i < numResults && rs.next(); i++)
            {
                Stockprice = loadStockPrice(rs);
                list.add(Stockprice);
            }

            rs.close();
            pstmt.close();
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public int getStockpriceCountByWeeks(Date StartDate, Date EndDate, String stockcode)
    {
        int count = 0;
        long days = 0;
        if(EndDate.getTime() > System.currentTimeMillis()) {
            days = System.currentTimeMillis()-StartDate.getTime();
        } else {
            days = EndDate.getTime() - StartDate.getTime();
        }
        days = days/1000;  //转化为秒
        days = days/60;    //转化为分
        days = days/60;    //转化为小时
        days = days/24;    //转化为天
        //System.out.println("days = " + days);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s_date = formatter.format(StartDate);
        if(EndDate.getTime() > System.currentTimeMillis()) {
            days = System.currentTimeMillis()-StartDate.getTime();
        } else {
            days = EndDate.getTime() - StartDate.getTime();
        }
        int posi = -1;
        String s_year=null;
        String s_month=null;
        String s_day=null;
        posi = s_date.indexOf("-");
        if (posi > -1)
        {
            s_year = s_date.substring(0,posi);
            s_date = s_date.substring(posi + 1);
            posi = s_date.indexOf("-");
            if (posi > -1) {
                s_month = s_date.substring(0,posi);
                s_day = s_date.substring(posi + 1);
            }
        }

        //System.out.println("s_year = " + Integer.parseInt(s_year));
        //System.out.println("s_month = " + Integer.parseInt(s_month));
        //System.out.println("s_day = " + Integer.parseInt(s_day));
        //System.out.println("startDate = " + formatter.format(StartDate));
        //System.out.println("endDate = " + formatter.format(EndDate));

        Calendar cal = null;
        switch(Integer.parseInt(s_month)) {
            case 1:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.JANUARY, Integer.parseInt(s_day));
            case 2:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.FEBRUARY, Integer.parseInt(s_day));
            case 3:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.MARCH, Integer.parseInt(s_day));
            case 4:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.APRIL, Integer.parseInt(s_day));
            case 5:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.MAY, Integer.parseInt(s_day));
            case 6:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.JUNE, Integer.parseInt(s_day));
            case 7:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.JULY, Integer.parseInt(s_day));
            case 8:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.AUGUST, Integer.parseInt(s_day));
            case 9:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.SEPTEMBER, Integer.parseInt(s_day));
            case 10:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.OCTOBER, Integer.parseInt(s_day));
            case 11:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.NOVEMBER, Integer.parseInt(s_day));
            case 12:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.DECEMBER, Integer.parseInt(s_day));
        }

        //int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);    // 6=Friday
        //System.out.println("dayOfWeek=" + dayOfWeek);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        Calendar cal_m = Calendar.getInstance();
        cal_m.setTime(StartDate);
        cal_m.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        Calendar cal_f = Calendar.getInstance();
        cal_f.setTime(StartDate);
        cal_f.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.FRIDAY);
        int weeks = (int)days/7;
        weeks w = new weeks();
        List wl = new ArrayList();
        cal_f.add(GregorianCalendar.DAY_OF_MONTH,-7);
        cal_m.add(GregorianCalendar.DAY_OF_MONTH,7);

        //System.out.println("weeks=" + weeks);
        for (int i=0; i <weeks; i++) {
            // Go to the next Friday by adding 7 days.
            cal_f.add(GregorianCalendar.DAY_OF_MONTH,7);
            String datestr = dateFormat.format(cal_f.getTime());
            int yposi =datestr.indexOf("年");
            int year = Integer.parseInt(datestr.substring(0,yposi));
            int mposi =datestr.indexOf("月");
            int month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            int dposi = datestr.indexOf("日");
            int day = Integer.parseInt(datestr.substring(mposi+1,dposi));
            //System.out.println("Friday=" + year +"-" + month + "-" + day);
            w = new weeks();
            w.setFridayYear(year);
            w.setFridayMonth(month);
            w.setFridayDay(day);

            // Go to the next Monday by adding 7 days.
            cal_m.add(GregorianCalendar.DAY_OF_MONTH,7);
            datestr = dateFormat.format(cal_m.getTime());
            yposi =datestr.indexOf("年");
            year = Integer.parseInt(datestr.substring(0,yposi));
            mposi =datestr.indexOf("月");
            month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            dposi = datestr.indexOf("日");
            day = Integer.parseInt(datestr.substring(mposi+1,dposi));
            //System.out.println("Monday=" + year +"-" + month + "-" + day);
            w.setMondayYear(year);
            w.setMondayMonth(month);
            w.setMondayDay(day);
            wl.add(w);
        }

        return count;
    }

    public List getStockpricesByWeeks(Date StartDate, Date EndDate, String stockCode, int startIndex, int numResults)
    {
        Connection conn = null;

        long days = 0;
        if(EndDate.getTime() > System.currentTimeMillis()) {
            days = System.currentTimeMillis()-StartDate.getTime();
        } else {
            days = EndDate.getTime() - StartDate.getTime();
        }
        days = days/1000;  //转化为秒
        days = days/60;    //转化为分
        days = days/60;    //转化为小时
        days = days/24;    //转化为天
        //System.out.println("days = " + days);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s_date = formatter.format(StartDate);
        String e_date = formatter.format(EndDate);
        int posi = -1;
        String s_year=null;
        String s_month=null;
        String s_day=null;
        posi = s_date.indexOf("-");
        if (posi > -1)
        {
            s_year = s_date.substring(0,posi);
            s_date = s_date.substring(posi + 1);
            posi = s_date.indexOf("-");
            if (posi > -1) {
                s_month = s_date.substring(0,posi);
                s_day = s_date.substring(posi + 1);
            }
        }

        //System.out.println("s_year = " + Integer.parseInt(s_year));
        //System.out.println("s_month = " + Integer.parseInt(s_month));
        //System.out.println("s_day = " + Integer.parseInt(s_day));
        //System.out.println("startDate = " + formatter.format(StartDate));
        //System.out.println("endDate = " + formatter.format(EndDate));

        Calendar cal = null;
        switch(Integer.parseInt(s_month)) {
            case 1:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.JANUARY, Integer.parseInt(s_day));
            case 2:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.FEBRUARY, Integer.parseInt(s_day));
            case 3:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.MARCH, Integer.parseInt(s_day));
            case 4:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.APRIL, Integer.parseInt(s_day));
            case 5:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.MAY, Integer.parseInt(s_day));
            case 6:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.JUNE, Integer.parseInt(s_day));
            case 7:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.JULY, Integer.parseInt(s_day));
            case 8:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.AUGUST, Integer.parseInt(s_day));
            case 9:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.SEPTEMBER, Integer.parseInt(s_day));
            case 10:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.OCTOBER, Integer.parseInt(s_day));
            case 11:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.NOVEMBER, Integer.parseInt(s_day));
            case 12:
                cal = new GregorianCalendar(Integer.parseInt(s_year) , Calendar.DECEMBER, Integer.parseInt(s_day));
        }

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);    // 6=Friday
        //System.out.println("dayOfWeek=" + dayOfWeek);

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        Calendar cal_m = Calendar.getInstance();
        cal_m.setTime(StartDate);
        cal_m.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        Calendar cal_f = Calendar.getInstance();
        cal_f.setTime(StartDate);
        cal_f.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.FRIDAY);
        int weeks = (int)days/7;
        weeks w = new weeks();
        List wl = new ArrayList();
        cal_f.add(GregorianCalendar.DAY_OF_MONTH,-7);
        cal_m.add(GregorianCalendar.DAY_OF_MONTH,-7);

        //System.out.println("weeks=" + weeks);
        for (int i=0; i <weeks; i++) {
            // Go to the next Friday by adding 7 days.
            cal_m.add(GregorianCalendar.DAY_OF_MONTH,7);
            String datestr = dateFormat.format(cal_m.getTime());
            int yposi =datestr.indexOf("年");
            int year = Integer.parseInt(datestr.substring(0,yposi));
            int mposi =datestr.indexOf("月");
            int month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            int dposi = datestr.indexOf("日");
            int day = Integer.parseInt(datestr.substring(mposi+1,dposi));
            //System.out.println("Monday=" + year +"-" + month + "-" + day);
            w = new weeks();
            w.setMondayYear(year) ;
            w.setMondayMonth(month) ;
            w.setMondayDay(day) ;

            // Go to the next Monday by adding 7 days.
            cal_f.add(GregorianCalendar.DAY_OF_MONTH,7);
            datestr = dateFormat.format(cal_f.getTime());
            yposi =datestr.indexOf("年");
            year = Integer.parseInt(datestr.substring(0,yposi));
            mposi =datestr.indexOf("月");
            month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            dposi = datestr.indexOf("日");
            day = Integer.parseInt(datestr.substring(mposi+1,dposi));
            //System.out.println("Monday=" + year +"-" + month + "-" + day);
            w.setFridayYear(year);
            w.setFridayMonth(month);
            w.setFridayDay(day);
            wl.add(w);
        }

        String getStartDayOfWeek = "select TO_CHAR(min(thedate),'yyyy-mm-dd') from stock_price where stockcode = ? and " +
                "to_char(thedate,'yyyy-mm-dd')>= to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') and " +
                "to_char(thedate,'yyyy-mm-dd')<=to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd')";
        String getEndDayOfWeek = "select TO_CHAR(max(thedate),'yyyy-mm-dd') from stock_price where stockcode = ? and " +
                "to_char(thedate,'yyyy-mm-dd')>= to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') and " +
                "to_char(thedate,'yyyy-mm-dd')<=to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd')";
        String sql_1 = "SELECT PRE_CLOSE_PRICE FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ? order by thedate desc";
        String sql_5 = "SELECT CLOSE_PRICE FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ?  order by thedate desc";
        String MaxAndMinValueByWeek = "SELECT max(MAX_PRICE),min(MIN_PRICE) FROM STOCK_PRICE where stockcode = ? and " +
                "to_char(thedate,'yyyy-mm-dd')>= to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') and " +
                "to_char(thedate,'yyyy-mm-dd')<=to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') order by thedate desc";

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Stock stockprice = null;
        try
        {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;

            for (int i=wl.size()-1; i>0; i--) {
                String monday_month = null;
                String monday_day = null;
                String friday_month = null;
                String friday_day = null;
                stockprice = new Stock();
                w = (weeks)wl.get(i);
                if (w.getMondayMonth()<10)
                    monday_month = "0" + w.getMondayMonth();
                else
                    monday_month = "" + w.getMondayMonth();

                if (w.getMondayDay() < 10)
                    monday_day = "0" + w.getMondayDay();
                else
                    monday_day = "" + w.getMondayDay();

                if (w.getFridayMonth()<10)
                    friday_month = "0" + w.getFridayMonth();
                else
                    friday_month = "" + w.getFridayMonth();

                if (w.getFridayDay() < 10)
                    friday_day = "0" + w.getFridayDay();
                else
                    friday_day = "" + w.getFridayDay();

                String start_day = w.getMondayYear() + "-" + monday_month + "-" + monday_day;
                String end_day = w.getFridayYear() + "-" + friday_month + "-" + friday_day;
                //System.out.println("start_day=" + start_day);
                //System.out.println("end_day=" + end_day);
                if (start_day != null && end_day != null) {
                    //设置日期
                    stockprice.setThedate(Date.valueOf(start_day));
                    //设置股票代码
                    stockprice.setName(stockCode);

                    //获取在数据库中第一天的日期
                    pstmt = conn.prepareStatement(getStartDayOfWeek);
                    pstmt.setString(1, stockCode);
                    pstmt.setString(2, start_day);
                    pstmt.setString(3, end_day);
                    rs = pstmt.executeQuery();
                    if (rs.next())  start_day = rs.getString(1);
                    rs.close();
                    pstmt.close();

                    //获取数据库中每个月最后一天的日期
                    pstmt = conn.prepareStatement(getEndDayOfWeek);
                    pstmt.setString(1, stockCode);
                    pstmt.setString(2, start_day);
                    pstmt.setString(3, end_day);
                    rs = pstmt.executeQuery();
                    if (rs.next())  end_day = rs.getString(1);
                    rs.close();
                    pstmt.close();

                    //获取每个周第一天的开盘价
                    pstmt = conn.prepareStatement(sql_1);
                    pstmt.setString(1, stockCode);
                    pstmt.setString(2, start_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setOpenprice(String.valueOf(rs.getFloat("pre_close_price")));
                    }
                    rs.close();
                    pstmt.close();

                    //获取每个周最后一天的收盘价格
                    pstmt = conn.prepareStatement(sql_5);
                    pstmt.setString(1, stockCode);
                    pstmt.setString(2, end_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setCloseprice(String.valueOf(rs.getFloat("CLOSE_PRICE"))) ;
                    }
                    rs.close();
                    pstmt.close();

                    //获取每个周的最高价和最低价
                    pstmt = conn.prepareStatement(MaxAndMinValueByWeek);
                    pstmt.setString(1, stockCode);
                    pstmt.setString(2, start_day);
                    pstmt.setString(3, end_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setMaxprice(String.valueOf(rs.getFloat(1)));
                        stockprice.setMinprice(String.valueOf(rs.getFloat(2)));
                        //System.out.println("date=" + mons.getEndYear() + "-" + mons.getEndMonth() + " maxPrice=" + rs.getFloat(1) + "  minPrice=" + rs.getFloat(2));
                    }
                    rs.close();
                    pstmt.close();
                    list.add(stockprice);
                }
            }
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //public int getStockpriceCountByMonths(Date StartDate, Date EndDate, String stockcode)
    public List getStockpriceCountByMonths(Date StartDate, Date EndDate, String stockcode)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        months mons = null;
        List ml = new ArrayList();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s_date = formatter.format(StartDate);
        String e_date = null;
        if (EndDate.getTime() > System.currentTimeMillis())
            e_date = formatter.format(new java.util.Date(System.currentTimeMillis()));
        else
            e_date = formatter.format(EndDate);
        int posi = -1;
        String s_year=null;
        String s_month=null;
        String s_day=null;
        posi = s_date.indexOf("-");
        int sn_year = 0;
        int sn_month = 0;
        int sn_day = 0;

        if (posi > -1)
        {
            s_year = s_date.substring(0,posi);
            sn_year = Integer.parseInt(s_year);
            s_date = s_date.substring(posi + 1);
            posi = s_date.indexOf("-");
            if (posi > -1) {
                s_month = s_date.substring(0,posi);
                s_day = s_date.substring(posi + 1);
                sn_month = Integer.parseInt(s_month)-1;
                sn_day = Integer.parseInt(s_day);
            }
        }

        String e_year=null;
        String e_month=null;
        String e_day=null;
        posi = e_date.indexOf("-");
        int en_year = 0;
        int en_month = 0;
        int en_day = 0;

        if (posi > -1)
        {
            e_year = e_date.substring(0,posi);
            en_year = Integer.parseInt(e_year);
            e_date = e_date.substring(posi + 1);
            posi = e_date.indexOf("-");
            if (posi > -1) {
                e_month = e_date.substring(0,posi);
                e_day = e_date.substring(posi + 1);
                en_month = Integer.parseInt(e_month);
                en_day = Integer.parseInt(e_day);
            }
        }

        Calendar   objCalendarDate1   =   Calendar.getInstance();
        if (sn_month==2) {
            if (sn_year % 4 == 0) {
                objCalendarDate1.set(sn_year,sn_month,29);
            } else {
                objCalendarDate1.set(sn_year,sn_month,28);
            }
        } else {
            if (sn_month ==1 || sn_month == 3 || sn_month ==5 ||  sn_month ==7 || sn_month ==8 || sn_month ==10 || sn_month ==12) {
                objCalendarDate1.set(sn_year,sn_month,31);
            } else {
                objCalendarDate1.set(sn_year,sn_month,30);
            }
        }

        Calendar   objCalendarDate2   =   Calendar.getInstance();
        if (en_month==2) {
            if (en_year % 4 == 0) {
                objCalendarDate2.set(en_year,en_month,29);
            } else {
                objCalendarDate2.set(en_year,en_month,28);
            }
        } else {
            if (en_month ==1 || en_month == 3 || en_month ==5 ||  en_month ==7 || en_month ==8 || en_month ==10 || en_month ==12) {
                objCalendarDate2.set(en_year,en_month,31);
            } else {
                objCalendarDate2.set(en_year,en_month,30);
            }
        }

        int month = 0;
        while(!(objCalendarDate1.get(Calendar.YEAR) == objCalendarDate2.get(Calendar.YEAR) && objCalendarDate1.get(Calendar.MONTH) == objCalendarDate2.get(Calendar.MONTH))) {
            mons = new months();
            month = objCalendarDate1.get(Calendar.MONTH) + 1;
            mons.setStartYear(objCalendarDate1.get(Calendar.YEAR));
            mons.setStartMonth(month);
            mons.setStartDay(1);
            mons.setEndYear(objCalendarDate1.get(Calendar.YEAR));
            mons.setEndMonth(month);
            if (month == 2) {
                if (objCalendarDate1.get(Calendar.YEAR) % 4 ==0) {
                    mons.setEndDay(29);
                } else {
                    mons.setEndDay(28);
                }
            } else if (month ==1 || month == 3 || month ==5 ||  month ==7 || month ==8 || month ==10 || month ==12) {
                mons.setEndDay(31);
            } else {
                mons.setEndDay(30);
            }
            //System.out.println("year=" + mons.getEndYear() + "  month=" + mons.getEndMonth() + "  day=" + mons.getEndDay());
            ml.add(mons);
            objCalendarDate1.add(Calendar.MONTH,1) ;
        }

        int count = 0;
        String getStartDayOfMonth = "select TO_CHAR(min(thedate),'yyyy-mm-dd') from stock_price where TO_CHAR(THEDATE,'yyyy-mm')=?";
        String getEndDayOfMonth = "select TO_CHAR(max(thedate),'yyyy-mm-dd') from stock_price where TO_CHAR(THEDATE,'yyyy-mm')=?";
        String sql_1 = "SELECT PRE_CLOSE_PRICE FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ? order by thedate desc";
        String sql_30 = "SELECT CLOSE_PRICE FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ?  order by thedate desc";
        String MaxAndMinValueByMonth = "SELECT max(MAX_PRICE),min(MIN_PRICE) FROM STOCK_PRICE where stockcode = ? and " +
                "to_char(thedate,'yyyy-mm-dd')>= to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') and " +
                "to_char(thedate,'yyyy-mm-dd')<=to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') order by thedate desc";

        ResultSet rs = null;
        List list = new ArrayList();
        Stock stockprice = null;
        try
        {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;
            for (int i=ml.size()-1; i>0; i--) {
                stockprice = new Stock();
                mons = (months)ml.get(i);
                //获取每个月的开盘价格
                if (mons.getStartMonth() < 10)
                    s_month = "0" + mons.getStartMonth();
                else
                    s_month = "" + mons.getStartMonth();

                //获取在数据库中第一天的日期
                String start_day = mons.getStartYear() + "-" + s_month + "-" + "01";
                pstmt = conn.prepareStatement(getStartDayOfMonth);
                pstmt.setString(1, mons.getStartYear()  + "-" + s_month);
                rs = pstmt.executeQuery();
                if (rs.next())  start_day = rs.getString(1);
                rs.close();
                pstmt.close();

                //获取数据库中每个月最后一天的日期
                if (mons.getEndMonth() < 10)
                    e_month = "0" + mons.getEndMonth();
                else
                    e_month = "" + mons.getEndMonth();
                String end_day = mons.getEndYear() + "-" + e_month + "-" + mons.getEndDay();
                pstmt = conn.prepareStatement(getEndDayOfMonth);
                pstmt.setString(1, mons.getEndYear() + "-" + e_month);
                rs = pstmt.executeQuery();
                if (rs.next())  end_day = rs.getString(1);
                rs.close();
                pstmt.close();

                if (start_day != null && end_day != null) {
                    //设置日期
                    stockprice.setThedate(Date.valueOf(start_day));
                    //设置股票代码
                    stockprice.setName(stockcode);
                    //获取每个月第一天的开盘价
                    pstmt = conn.prepareStatement(sql_1);
                    pstmt.setString(1, stockcode);
                    pstmt.setString(2, start_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setOpenprice(String.valueOf(rs.getFloat("pre_close_price")));
                        //System.out.println("startday=" + start_day + "  start_price=" + rs.getFloat("pre_close_price"));
                    }
                    rs.close();
                    pstmt.close();

                    //获取每个月最后一天的收盘价格
                    pstmt = conn.prepareStatement(sql_30);
                    pstmt.setString(1, stockcode);
                    pstmt.setString(2, end_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setCloseprice(String.valueOf(rs.getFloat("CLOSE_PRICE"))) ;
                        //System.out.println("endday=" + end_day + "  close_price=" + rs.getFloat("CLOSE_PRICE"));
                    }
                    rs.close();
                    pstmt.close();

                    //获取每个月的最高价和最低价
                    pstmt = conn.prepareStatement(MaxAndMinValueByMonth);
                    pstmt.setString(1, stockcode);
                    pstmt.setString(2, start_day);
                    pstmt.setString(3, end_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setMaxprice(String.valueOf(rs.getFloat(1)));
                        stockprice.setMinprice(String.valueOf(rs.getFloat(2)));
                        //System.out.println("date=" + mons.getEndYear() + "-" + mons.getEndMonth() + " maxPrice=" + rs.getFloat(1) + "  minPrice=" + rs.getFloat(2));
                    }
                    rs.close();
                    pstmt.close();

                    list.add(stockprice);
                    count = count + 1;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public List getStockpricesByMonths(Date StartDate, Date EndDate, String stockCode, int startIndex, int numResults)
    {
        Connection conn = null;
        String sql_final = "SELECT ID, PRE_CLOSE_PRICE, MAX_PRICE, MIN_PRICE, CLOSE_PRICE, EXCHANGES, THECHANGE, THEDATE, stockcode FROM STOCK_PRICE";
        PreparedStatement pstmt;
        ResultSet rs;

        months mons = null;
        List ml = new ArrayList();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s_date = formatter.format(StartDate);
        String e_date = null;
        if (EndDate.getTime() > System.currentTimeMillis())
            e_date = formatter.format(new java.util.Date(System.currentTimeMillis()));
        else
            e_date = formatter.format(EndDate);
        int posi = -1;
        String s_year=null;
        String s_month=null;
        String s_day=null;
        posi = s_date.indexOf("-");
        int sn_year = 0;
        int sn_month = 0;
        int sn_day = 0;

        if (posi > -1)
        {
            s_year = s_date.substring(0,posi);
            sn_year = Integer.parseInt(s_year);
            s_date = s_date.substring(posi + 1);
            posi = s_date.indexOf("-");
            if (posi > -1) {
                s_month = s_date.substring(0,posi);
                s_day = s_date.substring(posi + 1);
                sn_month = Integer.parseInt(s_month)-1;
                sn_day = Integer.parseInt(s_day);
            }
        }

        String e_year=null;
        String e_month=null;
        String e_day=null;
        posi = e_date.indexOf("-");
        int en_year = 0;
        int en_month = 0;
        int en_day = 0;

        if (posi > -1)
        {
            e_year = e_date.substring(0,posi);
            en_year = Integer.parseInt(e_year);
            e_date = e_date.substring(posi + 1);
            posi = e_date.indexOf("-");
            if (posi > -1) {
                e_month = e_date.substring(0,posi);
                e_day = e_date.substring(posi + 1);
                en_month = Integer.parseInt(e_month);
                en_day = Integer.parseInt(e_day);
            }
        }

        Calendar   objCalendarDate1   =   Calendar.getInstance();
        if (sn_month==2) {
            if (sn_year % 4 == 0) {
                objCalendarDate1.set(sn_year,sn_month,29);
            } else {
                objCalendarDate1.set(sn_year,sn_month,28);
            }
        } else {
            if (sn_month ==1 || sn_month == 3 || sn_month ==5 ||  sn_month ==7 || sn_month ==8 || sn_month ==10 || sn_month ==12) {
                objCalendarDate1.set(sn_year,sn_month,31);
            } else {
                objCalendarDate1.set(sn_year,sn_month,30);
            }
        }

        Calendar   objCalendarDate2   =   Calendar.getInstance();
        if (en_month==2) {
            if (en_year % 4 == 0) {
                objCalendarDate2.set(en_year,en_month,29);
            } else {
                objCalendarDate2.set(en_year,en_month,28);
            }
        } else {
            if (en_month ==1 || en_month == 3 || en_month ==5 ||  en_month ==7 || en_month ==8 || en_month ==10 || en_month ==12) {
                objCalendarDate2.set(en_year,en_month,31);
            } else {
                objCalendarDate2.set(en_year,en_month,30);
            }
        }

        int month = 0;
        while(!(objCalendarDate1.get(Calendar.YEAR) == objCalendarDate2.get(Calendar.YEAR) && objCalendarDate1.get(Calendar.MONTH) == objCalendarDate2.get(Calendar.MONTH))) {
            mons = new months();
            month = objCalendarDate1.get(Calendar.MONTH) + 1;
            mons.setStartYear(objCalendarDate1.get(Calendar.YEAR));
            mons.setStartMonth(month);
            mons.setStartDay(1);
            mons.setEndYear(objCalendarDate1.get(Calendar.YEAR));
            mons.setEndMonth(month);
            if (month == 2) {
                if (objCalendarDate1.get(Calendar.YEAR) % 4 ==0) {
                    mons.setEndDay(29);
                } else {
                    mons.setEndDay(28);
                }
            } else if (month ==1 || month == 3 || month ==5 ||  month ==7 || month ==8 || month ==10 || month ==12) {
                mons.setEndDay(31);
            } else {
                mons.setEndDay(30);
            }
            System.out.println("year=" + mons.getEndYear() + "  month=" + mons.getEndMonth() + "  day=" + mons.getEndDay());
            ml.add(mons);
            objCalendarDate1.add(Calendar.MONTH,1) ;
        }

        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and stockcode=?");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')>=TO_CHAR(?,'yyyy-mm-dd')");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')<=TO_CHAR(?,'yyyy-mm-dd') ");
        List list = new ArrayList();
        Stock Stockprice;
        try
        {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();

            pstmt = conn.prepareStatement(sql_final);
            //pstmt.setString(1,stockCode);
            //pstmt.setDate(2, StartDate);
            //pstmt.setDate(3, EndDate);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++)
                rs.next();

            for(int i = 0; i < numResults && rs.next(); i++)
            {
                Stockprice = loadStockPrice(rs);
                list.add(Stockprice);
            }

            rs.close();
            pstmt.close();
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    public StockInfo getSpiderInfoBycode(String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StockInfo stock = null;

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(GET_STOCK_INFO_BYCODE);
                pstmt.setString(1, stockcode);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    stock = new StockInfo();
                    stock = load(rs);
                }

                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.out.println("Close the connection failed!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stock;
    }

    //private static String GET_STOCK_BASEINFO = "select id,name,stockcode,currency,companyname from stock_baseinfo where stopflag=1";

    private static String GET_STOCK_BASEINFO = "select id,name,stockcode,currency,companyname from stock_baseinfo";


    public List getSstockbaseInfo() throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        stockBaseinfo stock_baseinfo = new stockBaseinfo();

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(GET_STOCK_BASEINFO);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    stock_baseinfo = load_stockBaseinfo(rs);
                    list.add(stock_baseinfo);
                }

                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        System.out.println("Close the connection failed!");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static String IS_EXIST = "select * from tbl_stock where nameid = ? order by trade_time desc";

    public boolean getExist(String stockcode, StockInfo stock) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StockInfo stockdb = new StockInfo();
        boolean existflag = false;

        try {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;
            pstmt = conn.prepareStatement(IS_EXIST);
            pstmt.setString(1, stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                stockdb = load(rs);
            }
            rs.close();
            pstmt.close();

            if (stock.getVolume()!=stockdb.getVolume() || stock.getLast_trade()!=stockdb.getLast_trade())
                existflag = false;
            else
                existflag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return existflag;
    }

    private static String INSERT_OIL_INFO = "insert into sn_oil(id,name,unit,price,change,createdate) values(?, ?, ?, ?, ?, ?)";

    private static String GET_OIL_MAX_ID = "select max(id) from sn_oil";

    public void insertOilDate(crudeoil co, Date date) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        //Date datetime = new Date(System.currentTimeMillis());
        ResultSet rs = null;
        int id = 0;

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;

                pstmt = conn.prepareStatement(GET_OIL_MAX_ID);
                rs = pstmt.executeQuery();
                if (rs.next()) id = rs.getInt(1) + 1;
                rs.close();
                pstmt.close();

                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(INSERT_OIL_INFO);
                pstmt.setInt(1, id);
                pstmt.setString(2, co.getName());
                pstmt.setString(3, co.getUnit());
                pstmt.setFloat(4, co.getCprice());
                pstmt.setFloat(5, co.getChange());
                pstmt.setDate(6, date);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String INSERT_STOCKCODE = "insert into stock_baseinfo(id,name,stockcode,currency,companyname) values(?, ?, ?, ?, ?)";
    private static String GET_STOCKCODE_MAX_ID = "select max(id) from stock_baseinfo";

    public void insertStockcode(String companyname,String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        //Date datetime = new Date(System.currentTimeMillis());
        ResultSet rs = null;
        int id = 0;
        String name="";
        String currency="";

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;

                pstmt = conn.prepareStatement(GET_STOCKCODE_MAX_ID);
                rs = pstmt.executeQuery();
                if (rs.next()) id = rs.getInt(1) + 1;
                rs.close();
                pstmt.close();

                if (stockcode.endsWith(".SS")) {
                    name = "上海交易所";
                    currency="人民币";
                } else if (stockcode.endsWith(".SZ")) {
                    name = "深圳交易所";
                    currency="人民币";
                } else if (stockcode.endsWith(".HK")) {
                    name = "香港交易所";
                    currency="港币";
                } else {
                    name = "纽约股票交易所";
                    currency="美元";
                }

                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(INSERT_STOCKCODE);
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, stockcode);
                pstmt.setString(4, currency);
                pstmt.setString(5, companyname);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String UPDATE_OIL_INFO = "update sn_oil set price = ?,change = ? where name = ? and createdate = ?";

    public void updateOilDate(crudeoil co, Date date) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        //Date datetime = new Date(System.currentTimeMillis());

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(UPDATE_OIL_INFO);
                pstmt.setFloat(1, co.getCprice());
                pstmt.setFloat(2, co.getChange());
                pstmt.setString(3, co.getName());
                pstmt.setDate(4, date);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String Oil_EXIST = "select * from sn_oil where name = ? and TO_CHAR(createdate,'yyyy-mm-dd') = ?";

    public boolean getOilExist(String oilname, Date date) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean existflag = false;

        //Date datetime = new Date(System.currentTimeMillis());

        try {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;

            pstmt = conn.prepareStatement(Oil_EXIST);
            pstmt.setString(1, oilname);
            pstmt.setString(2, String.valueOf(date));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                existflag = true;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(oilname + "  " + existflag);
        return existflag;
    }

    private StockInfo load(ResultSet rs) throws SpiderException {
        StockInfo stockindb = new StockInfo();
        try {
            stockindb.setName(rs.getString("name"));
            stockindb.setLast_trade(rs.getFloat("last_trade"));
            stockindb.setTrade_time(rs.getTimestamp("trade_time"));
            stockindb.setThechange(rs.getFloat("thechange"));
            stockindb.setPrev_close(rs.getFloat("prev_close"));
            stockindb.setOpen_money(rs.getFloat("open_money"));
            stockindb.setBid(rs.getFloat("bid"));
            stockindb.setAsk(rs.getFloat("ask"));
            stockindb.setTarget_est(rs.getFloat("target_est"));
            stockindb.setDay_range_low(rs.getFloat("day_range_low"));
            stockindb.setDay_range_high(rs.getFloat("day_range_high"));
            stockindb.setWk52_range_low(rs.getFloat("52wk_range_low"));
            stockindb.setWk52_range_high(rs.getFloat("52wk_range_high"));
            stockindb.setVolume(rs.getInt("volume"));
            stockindb.setM3_avg_vol(rs.getInt("3m_avg_vol"));
            stockindb.setMarket_cap(rs.getString("market_cap"));
            stockindb.setP_e(rs.getFloat("p_e"));
            stockindb.setEps(rs.getFloat("eps"));
            stockindb.setDiv_yield(rs.getString("div_yield"));
            stockindb.setThedate(rs.getTimestamp("thedate"));
            stockindb.setNameid(rs.getString("nameid"));
            stockindb.setDanwei(rs.getString("danwei"));
        } catch (Exception e) {
            System.out.println("Load failed");
            e.printStackTrace();
        }
        return stockindb;
    }

    private stockBaseinfo load_stockBaseinfo(ResultSet rs) throws SpiderException {
        stockBaseinfo sbi = new stockBaseinfo();
        try {
            sbi.setID(rs.getInt("id"));
            sbi.setJysName(rs.getString("name"));
            sbi.setTicker(rs.getString("stockcode"));
            sbi.setCurrency(rs.getString("currency"));
            sbi.setCompanyname(rs.getString("companyname"));
        } catch (Exception e) {
            System.out.println("Load failed");
            e.printStackTrace();
        }

        return sbi;
    }

    Stock loadStockPrice(ResultSet rs) throws SQLException
    {
        Stock stockprice = new Stock();
        try
        {
            stockprice.setPrecloseprice(rs.getString("PRE_CLOSE_PRICE"));
            stockprice.setMaxprice(rs.getString("MAX_PRICE"));
            stockprice.setMinprice(rs.getString("MIN_PRICE"));
            stockprice.setCloseprice(rs.getString("CLOSE_PRICE"));
            stockprice.setChange(rs.getString("THECHANGE"));
            stockprice.setId(rs.getInt("id"));
            stockprice.setExchange(rs.getString("exchanges"));
            stockprice.setThedate(rs.getDate("thedate"));
            stockprice.setName(rs.getString("stockcode"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return stockprice;
    }

    public boolean getClosePriceExist(String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Calendar cal = Calendar.getInstance();

        boolean existflag = true;
        cal.setTime(new java.util.Date());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
        String date_str = dateFormat.format(cal.getTime());
        String CLOSE_PRICE_EXIST = "select count(id) from stock_price where thedate=to_date('20" + date_str + "','yyyy-mm-dd') and stockcode=?";

        System.out.println("CLOSE_PRICE_EXIST = " + CLOSE_PRICE_EXIST);

        try {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(CLOSE_PRICE_EXIST);
            pstmt.setString(1, stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt(1));
                if (rs.getInt(1) > 0) existflag = false;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return existflag;
    }

    //Add by Eric on 2004/02/07 for insert prev_close_price
    private final String PREV_CLOSE_PRICE = "insert into stock_price(id,pre_close_price,max_price,min_price," +
            "close_price,thechange,exchanges,gid,stockcode,thedate,status) values " +
            "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void insertClosePrice(String[][] stock, String stockcode, int maxid) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("YYYY-MM-DD");

        String day_range = stock[8][1];
        int posi = day_range.indexOf(" ");
        String low = day_range.substring(0, posi);
        posi = day_range.indexOf("-") + 1;
        String heigh = day_range.substring(posi, day_range.length());

        boolean existflag = false;
        Timestamp datetime = new Timestamp(System.currentTimeMillis());
        int year = datetime.getYear();
        int month = datetime.getMonth();
        int day = datetime.getDate();
        java.sql.Date date = new java.sql.Date(year, month, day);
        stock[11][1] = StringUtil.replace(stock[11][1], ",", "");

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(PREV_CLOSE_PRICE);
                pstmt.setInt(1, maxid);
                if (stock[4][1].equalsIgnoreCase("N/A"))
                    pstmt.setFloat(2, 0.00f);
                else
                    pstmt.setFloat(2, Float.parseFloat(stock[4][1]));
                if (heigh.equalsIgnoreCase("N/A"))
                    pstmt.setFloat(3, 0.00f);
                else
                    pstmt.setFloat(3, Float.parseFloat(heigh.trim()));
                if (low.equalsIgnoreCase("N/A"))
                    pstmt.setFloat(4, 0.00f);
                else
                    pstmt.setFloat(4, Float.parseFloat(low.trim()));
                if (stock[0][1].equalsIgnoreCase("N/A"))
                    pstmt.setFloat(5, 0.00f);
                else
                    pstmt.setFloat(5, Float.parseFloat(stock[0][1]));
                //if (stock[3][1].equalsIgnoreCase("N/A"))
                //   pstmt.setFloat(6, 0.00f);
                //else
                pstmt.setFloat(6, Float.parseFloat(stock[0][1]) - Float.parseFloat(stock[3][1]));
                if (stock[11][1].equalsIgnoreCase("N/A"))
                    pstmt.setFloat(7, 0.00f);
                else {
                    stock[10][1] = StringUtil.replace(stock[10][1], ",", "");
                    pstmt.setInt(7, Integer.parseInt(stock[10][1]));
                }
                pstmt.setInt(8, 0);
                pstmt.setString(9, stockcode);
                pstmt.setDate(10, date);
                pstmt.setInt(11, 0);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insertSS_ClosePrice(String[][] stock, String stockcode, int maxid) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("YYYY-MM-DD");

        String day_range = stock[8][1];
        int posi = day_range.indexOf("-");
        String high = day_range.substring(0, posi);
        posi = day_range.indexOf("-") + 1;
        String low = day_range.substring(posi, day_range.length());

        boolean existflag = false;
        Timestamp datetime = new Timestamp(System.currentTimeMillis());
        int year = datetime.getYear();
        int month = datetime.getMonth();
        int day = datetime.getDate();
        java.sql.Date date = new java.sql.Date(year, month, day);
        stock[10][1] = StringUtil.replace(stock[10][1], ",", "");

        try {
            try {
                DBUtil dbUtil = new DBUtil();
                conn = dbUtil.getConn();;
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(PREV_CLOSE_PRICE);
                pstmt.setInt(1, maxid);
                //前收盘
                if (stock[3][1].equalsIgnoreCase("N/A"))
                    pstmt.setFloat(2, 0.00f);
                else
                    pstmt.setFloat(2, Float.parseFloat(stock[3][1]));
                //最高价
                if (high.equalsIgnoreCase("N/A"))
                    pstmt.setFloat(3, 0.00f);
                else
                    pstmt.setFloat(3, Float.parseFloat(high));
                //最低价
                if (low.equalsIgnoreCase("N/A"))
                    pstmt.setFloat(4, 0.00f);
                else
                    pstmt.setFloat(4, Float.parseFloat(low));
                //当前价
                if (stock[0][1].equalsIgnoreCase("N/A"))
                    pstmt.setFloat(5, 0.00f);
                else
                    pstmt.setFloat(5, Float.parseFloat(stock[0][1]));
                //变化量
                /*if (stock[2][1] != null) {
                    if (stock[2][1].equalsIgnoreCase("N/A"))
                        pstmt.setFloat(6, 0.00f);
                    else {
                        String ch = stock[2][1];
                        //ch = ch.substring(ch.indexOf("<b style="));
                        ch = ch.substring(ch.indexOf("<b style=")+26);
                        ch = ch.substring(0, ch.indexOf("</b>"));
                        pstmt.setFloat(6, Float.parseFloat(ch.trim()));
                    }
                }else{
                    pstmt.setFloat(6, 0.00f);
                }*/
                pstmt.setFloat(6, Float.parseFloat(high) - Float.parseFloat(low.trim()));
                //成交量
                if (stock[10][1].equalsIgnoreCase("N/A"))
                    pstmt.setFloat(7, 0.00f);
                else {
                    stock[10][1] = StringUtil.replace(stock[10][1], ",", "");
                    pstmt.setInt(7, Integer.parseInt(stock[10][1]));
                }
                pstmt.setInt(8, 0);
                pstmt.setString(9, stockcode);
                pstmt.setDate(10, date);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Close the connection failed!");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private final String GET_MAX_DATE = "select datetime from tbl_stock where nameid=?";

    public String getMaxDate(String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String datestr = null;
        Calendar cal = Calendar.getInstance();

        try {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;

            pstmt = conn.prepareStatement(GET_MAX_DATE);
            pstmt.setString(1, stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                java.util.Date mydate = rs.getDate("datetime");
                cal.setTime(mydate);
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
                datestr = dateFormat.format(cal.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Close the connection failed");
                }
            }
        }

        return datestr;
    }

    private final String DEL_YESTERDAY_DATA = "delete from tbl_stock where nameid=?";

    public void deleteYesterdayData(String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(DEL_YESTERDAY_DATA);
            pstmt.setString(1, stockcode);
            pstmt.executeUpdate();

            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Close the connection failed");
                }
            }
        }
    }

    private final String GET_MAX_CLOSEPRICEID = "select max(id) as maxid from stock_price where stockcode=?";

    public int getMaxClosePriceID(String stockcode) throws SpiderException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = 0;
        try {
            DBUtil dbUtil = new DBUtil();
            conn = dbUtil.getConn();;
            pstmt = conn.prepareStatement(GET_MAX_CLOSEPRICEID);
            pstmt.setString(1, stockcode);
            rs = pstmt.executeQuery();
            if (rs.next())
                id = rs.getInt("maxid");
            else
                id = 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Close the connection failed");
                }
            }
        }
        return id;
    }
}
