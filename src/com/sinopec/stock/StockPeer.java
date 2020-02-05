package com.sinopec.stock;

import com.bizwink.cms.server.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-3-5
 * Time: 16:52:01
 */
public class StockPeer implements IStockManager {

    PoolServer cpool;

    public StockPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IStockManager getInstance() {
        return CmsServer.getInstance().getFactory().getStockManager();
    }

    private String SQL_GETSTOCK = "SELECT id,name,last_trade,change,OPEN_MONEY,PREV_CLOSE FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM " +
            "tbl_stock where name=? order by id desc) A WHERE ROWNUM <= 1) WHERE RN >= 0";

    public List getStockNameInfo(int idflag) {
        String name = "";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List nameList = new ArrayList();
        try {
            switch(idflag){
                case 1:
                    name = "上海交易所";
                    break;
                case 2:
                    name = "香港交易所";
                    break;
                case 3:
                    name = "纽约股票交易所";
                    break;
            }
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSTOCK);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getInt("id"));
                stock.setName(rs.getString("name"));
                stock.setLast_trade(rs.getString("last_trade"));
                stock.setChange(rs.getString("change"));
                stock.setOpenprice(rs.getString("OPEN_MONEY"));
                stock.setCloseprice(rs.getString("PREV_CLOSE"));
                nameList.add(stock);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
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
        return nameList;
    }

    public int getStockpriceCount(Date StartDate, Date EndDate, String stockcode)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;
        String sql_final = "SELECT count(ID) FROM STOCK_PRICE where 1=1 ";
        ResultSet rs = null;
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and stockcode=?");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')>=TO_CHAR(?,'yyyy-mm-dd')");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')<=TO_CHAR(?,'yyyy-mm-dd')");
        try
        {
            conn = cpool.getConnection();
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
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return count;
    }

    public List getStockprices(Date StartDate, Date EndDate, String stockCode, int startIndex, int numResults)
    {
        Connection conn = null;
        String sql_final = "SELECT ID, PRE_CLOSE_PRICE, MAX_PRICE, MIN_PRICE, CLOSE_PRICE, EXCHANGES, THECHANGE, THEDATE, stockcode FROM STOCK_PRICE WHERE ";
        PreparedStatement pstmt;
        ResultSet rs;
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" stockcode=?");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')>=TO_CHAR(?,'yyyy-mm-dd')");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')<=TO_CHAR(?,'yyyy-mm-dd') order by thedate desc");

        System.out.println(sql_final);

        List list = new ArrayList();
        Stock Stockprice;
        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql_final);
            pstmt.setString(1, stockCode);
            pstmt.setDate(2, StartDate);
            pstmt.setDate(3, EndDate);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++)
                rs.next();

            for(int i = 0; i < numResults && rs.next(); i++)
            {
                Stockprice = load(rs);
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
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public int getStockpriceCountByWeeks(Date StartDate, Date EndDate, String stockcode)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
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

/*        String sql_final = "SELECT count(ID) FROM STOCK_PRICE where 1=1 ";
        ResultSet rs = null;
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and stockcode=?");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')>=TO_CHAR(?,'yyyy-mm-dd')");
        sql_final = String.valueOf(String.valueOf(sql_final)).concat(" and TO_CHAR(THEDATE,'yyyy-mm-dd')<=TO_CHAR(?,'yyyy-mm-dd')");
        try
        {
            conn = cpool.getConnection();
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
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }*/
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
        //ID, PRE_CLOSE_PRICE, MAX_PRICE, MIN_PRICE, CLOSE_PRICE, EXCHANGES, THECHANGE, THEDATE, stockcode
        String sql_1 = "SELECT PRE_CLOSE_PRICE FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ? order by thedate desc";
        String sql_exchanges = "SELECT EXCHANGES FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ? order by thedate desc";
        String sql_5 = "SELECT CLOSE_PRICE FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ?  order by thedate desc";
        String MaxAndMinValueByWeek = "SELECT max(MAX_PRICE),min(MIN_PRICE) FROM STOCK_PRICE where stockcode = ? and " +
                "to_char(thedate,'yyyy-mm-dd')>= to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') and " +
                "to_char(thedate,'yyyy-mm-dd')<=to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') order by thedate desc";

        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Stock stockprice = null;
        try
        {
            conn = cpool.getConnection();
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
                        stockprice.setPrecloseprice(String.valueOf(rs.getFloat("pre_close_price")));
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

                    //获取每个周最后一天的成交量
                    pstmt = conn.prepareStatement(sql_exchanges);
                    pstmt.setString(1, stockCode);
                    pstmt.setString(2, end_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setExchange(rs.getString("EXCHANGES")); ;
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
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
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
        String sql_exchanges = "SELECT EXCHANGES FROM STOCK_PRICE where stockcode = ? and TO_CHAR(THEDATE,'yyyy-mm-dd') = ? order by thedate desc";
        String MaxAndMinValueByMonth = "SELECT max(MAX_PRICE),min(MIN_PRICE) FROM STOCK_PRICE where stockcode = ? and " +
                "to_char(thedate,'yyyy-mm-dd')>= to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') and " +
                "to_char(thedate,'yyyy-mm-dd')<=to_char(to_date(?,'yyyy-mm-dd'),'yyyy-mm-dd') order by thedate desc";

        ResultSet rs = null;
        List list = new ArrayList();
        Stock stockprice = null;
        try
        {
            //System.out.println("stockcode=" + stockcode);
            conn = cpool.getConnection();
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
                        stockprice.setPrecloseprice(String.valueOf(rs.getFloat("pre_close_price")));
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

                    //获取每个月最后一天的交易量
                    pstmt = conn.prepareStatement(sql_exchanges);
                    pstmt.setString(1, stockcode);
                    pstmt.setString(2, end_day);
                    rs = pstmt.executeQuery();
                    if(rs.next()) {
                        stockprice.setExchange(rs.getString("exchanges"));
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
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
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
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql_final);
            pstmt.setString(1, stockCode);
            pstmt.setDate(2, StartDate);
            pstmt.setDate(3, EndDate);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++)
                rs.next();

            for(int i = 0; i < numResults && rs.next(); i++)
            {
                Stockprice = load(rs);
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
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return list;
    }

    public Stock getClosePrice(String stockcode)
    {
        Connection conn = null;
        Stock stockprice = null;
        try
        {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT ID, PRE_CLOSE_PRICE, MAX_PRICE, MIN_PRICE, CLOSE_PRICE, EXCHANGES,  THECHANGE, THEDATE, stockcode FROM STOCK_PRICE WHERE stockcode = ? ORDER BY THEDATE DESC");
            pstmt.setString(1, stockcode);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                stockprice = load(rs);
            rs.close();
            pstmt.close();
        }
        catch(Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    System.out.println("Error in closing the pooled connection ".concat(String.valueOf(String.valueOf(e.toString()))));
                }
        }
        return stockprice;
    }

    Stock load(ResultSet rs) throws SQLException
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

    private String getMonthName(int mon) {

        switch(mon) {
            case 1:
                return "JANUARY";
            case 2:
                return "FEBRUARY";
            case 3:
                return "MARCH";
            case 4:
                return "APRIL";
            case 5:
                return "MAY";
            case 6:
                return "JUNE";
            case 7:
                return "JULY";
            case 8:
                return "AUGUST";
            case 9:
                return "SEPTEMBER";
            case 10:
                return "OCTOBER";
            case 11:
                return "NOVEMBER";
            case 12:
                return "DECEMBER";
            default :
                return "JANUARY";
        }
    }
}
