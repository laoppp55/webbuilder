package com.bizwink.stockinfo;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.sql.*;
import java.lang.*;
import java.text.DateFormat;

import com.bizwink.cms.util.StringUtil;
import com.bizwink.images.*;

class SpiderServlet {
    public static void main(String[] args) throws Exception {
        String tempStr;
        String url;
        String value;
        String name;
        String nameid;
        String danwei;
        String[][] stock = new String[16][2];
        int posi;
        int i;
        boolean existflag;

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

        //history 600028.SS
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("h")) {
                HistoryStock his_stock = new HistoryStock();
                his_stock.getHistiryStockInfo(args[1]);
                System.out.println("Fishined");
            }
        } else {
            //com.bizwink.server.FileProps props = new com.bizwink.server.FileProps("com/bizwink/server/config.properties");
            String klinepath = "";   //props.getProperty("main.db.k_line_path");
            Calendar cal = Calendar.getInstance();
            ISpiderManager spiderMgr = SpiderPeer.getInstance();
            String stockcode;
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
            java.text.SimpleDateFormat hh_f = new java.text.SimpleDateFormat("HH");
            java.text.SimpleDateFormat mm_f = new java.text.SimpleDateFormat("mm");

            while (true) {
                cal.setTime(new java.util.Date());
                String datestr = dateFormat.format(cal.getTime());
                posi = datestr.indexOf("-");
                datestr = datestr.substring(posi + 1);
                int hour = Integer.parseInt(hh_f.format(cal.getTime()));
                int minutes = hour * 60 + Integer.parseInt(mm_f.format(cal.getTime()));
                String weekday = cal.getTime().toString().substring(0, 3);
                List stockbaseinfos = spiderMgr.getSstockbaseInfo();
                System.out.println("开始工作，共有" + stockbaseinfos.size() + "个作业");
                for (int j = 0; j < stockbaseinfos.size(); j++) {
                    boolean getflag = false;
                    stockBaseinfo sbi = (stockBaseinfo) stockbaseinfos.get(j);
                    System.out.println(sbi.getTicker());
                    //String date_fromdb = spiderMgr.getMaxDate(sbi.getTicker());

                    //判断是否处于非交易日，例如中国的春节、十一、五一等节假日
                    if (sbi.getJysName().equalsIgnoreCase("上海交易所") || sbi.getJysName().equalsIgnoreCase("深圳交易所")) {
                        if (!datestr.equalsIgnoreCase("5-1") && !datestr.equalsIgnoreCase("5-2") &&
                                !datestr.equalsIgnoreCase("5-3") && !datestr.equalsIgnoreCase("5-4") &&
                                !datestr.equalsIgnoreCase("5-5") && !datestr.equalsIgnoreCase("5-6") &&
                                !datestr.equalsIgnoreCase("5-7") && !datestr.equalsIgnoreCase("10-1") &&
                                !datestr.equalsIgnoreCase("10-2") && !datestr.equalsIgnoreCase("10-3") &&
                                !datestr.equalsIgnoreCase("10-4") && !datestr.equalsIgnoreCase("10-5") &&
                                !datestr.equalsIgnoreCase("10-6") && !datestr.equalsIgnoreCase("10-7") &&
                                !weekday.equalsIgnoreCase("Sat") && !weekday.equalsIgnoreCase("Sun") &&
                                ((minutes >= 9 * 60 + 30 && minutes < 11 * 60 + 30) || (hour >= 13 && hour <= 15))) {
                            //清除昨日的数据,判断从数据库中返回的日期是否与从系统获取的时期相等。
                            if (minutes >= 9 * 60 + 30 && minutes <= 9 * 60 + 31)
                                spiderMgr.deleteYesterdayData(sbi.getTicker());
                            getflag = true;
                        }
                    } else if (sbi.getJysName().equalsIgnoreCase("香港交易所")) {
                        if (!datestr.equalsIgnoreCase("5-1") && !datestr.equalsIgnoreCase("5-2") &&
                                !datestr.equalsIgnoreCase("5-3") && !datestr.equalsIgnoreCase("10-1") &&
                                !datestr.equalsIgnoreCase("10-2") && !datestr.equalsIgnoreCase("10-3") &&
                                !weekday.equalsIgnoreCase("Sat") && !weekday.equalsIgnoreCase("Sun") &&
                                (hour >= 9 && hour <= 16)) {
                            //清除昨日的数据,判断从数据库中返回的日期是否与从系统获取的时期相等。
                            if (minutes >= 9 * 60 && minutes <= 9 * 60 + 1)
                                spiderMgr.deleteYesterdayData(sbi.getTicker());
                            getflag = true;
                        }
                    } else
                    if (sbi.getJysName().equalsIgnoreCase("纽约股票交易所") || sbi.getJysName().equalsIgnoreCase("纳斯达克交易所")) {
                        //清除昨日的数据,判断从数据库中返回的日期是否与从系统获取的时期相等。
                        if (!weekday.equalsIgnoreCase("Sat") && !weekday.equalsIgnoreCase("Sun")) {
                            if (minutes >= 21 * 60 + 55 && minutes <= 21 * 60 + 56) {
                                spiderMgr.deleteYesterdayData(sbi.getTicker());
                            }
                            if (minutes >= 21 * 60 + 55 && minutes < 24 * 60 || (hour >= 0 && hour <= 4)) {
                                getflag = true;
                            }
                        } else if (weekday.equalsIgnoreCase("Sat")) {
                            if ((hour >= 0 && hour <= 4)) {
                                getflag = true;
                            }
                        }
                    }

                    if (getflag) {
                        try {
                            if (sbi.getJysName().equalsIgnoreCase("上海交易所") || sbi.getJysName().equalsIgnoreCase("深圳交易所"))
                                url = "http://cn.finance.yahoo.com/q?s=" + sbi.getTicker();
                            else
                                url = "http://finance.yahoo.com/q?s=" + sbi.getTicker();
                            name = sbi.getJysName();
                            nameid = sbi.getTicker();
                            danwei = sbi.getCurrency();
                            stockcode = sbi.getTicker();

                            URL myurl = new URL(url);
                            InputStream result = (InputStream) myurl.getContent();
                            BufferedReader in = new BufferedReader(new InputStreamReader(result));

                            i = 0;
                            List list = new ArrayList();
                            StringBuffer buf = new StringBuffer();

                            //将调用url的所有返回信息做成一个字符串
                            while (((tempStr = in.readLine()) != null)) {
                                buf.append(tempStr);
                            }

                            String resultStr = buf.toString();
                            String str;

                            if (sbi.getJysName().equalsIgnoreCase("上海交易所") || sbi.getJysName().equalsIgnoreCase("深圳交易所")) {
                                posi = resultStr.indexOf("<!--start:bd1 content-->");
                                str = resultStr.substring(posi + "<!--start:bd1 content-->".length());
                                posi = str.indexOf("<!--end:bd1 content-->");
                                str = str.substring(0, posi);

                                //获取股票交易的时间                                
                                posi = str.indexOf("<h1><em>");
                                str = str.substring(posi + 8);
                                posi = str.indexOf("</em>");
                                stock[1][1] = str.substring(0, posi);
                                //获取最新价格                                
                                posi = str.indexOf("最新价: ");
                                str = str.substring(posi + "最新价:".length());
                                posi = str.indexOf("</strong>");
                                stock[0][1] = str.substring(0, posi);
                                posi = stock[0][1].lastIndexOf(">");
                                stock[0][1] = stock[0][1].substring(posi + 1);
                                //获取股票价格的变化范围

                                posi = str.indexOf("</span></h1>");
                                stock[2][1] = str.substring(0, posi);
                                posi = stock[2][1].lastIndexOf(">");
                                stock[2][1] = stock[2][1].substring(posi + 1);
                                posi = stock[2][1].indexOf("(");
                                stock[2][1] = stock[2][1].substring(0, posi).trim();
                                //获取其他数据

                                posi = str.indexOf("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                str = str.substring(posi + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">".length());
                                posi = str.indexOf("</table>");
                                str = str.substring(0, posi);
                                String tempbuf = str;
                                String mstr;
                                Pattern p = Pattern.compile("<[^>]*>");
                                java.util.regex.Matcher matcher = p.matcher(str);
                                while (matcher.find()) {
                                    mstr = str.substring(matcher.start(), matcher.end());
                                    tempbuf = StringUtil.replace(tempbuf, mstr, "");
                                }
                                str = tempbuf.trim();
                                //获取今开盘数据
                                posi = str.indexOf("今开盘:");
                                str = str.substring(posi + "今开盘:".length());
                                posi = str.indexOf("前收盘:");
                                stock[4][1] = str.substring(0, posi).trim();
                                //获取前收盘数据
                                str = str.substring(posi + "前收盘:".length());
                                posi = str.indexOf("最高价:");
                                stock[3][1] = str.substring(0, posi).trim();
                                //获取前最高价数据
                                str = str.substring(posi + "最高价:".length());
                                posi = str.indexOf("最低价:");
                                stock[8][1] = str.substring(0, posi).trim();
                                //获取前最低价数据
                                str = str.substring(posi + "最低价:".length());
                                posi = str.indexOf("换手率:");
                                stock[8][1] = stock[8][1] + "-" + str.substring(0, posi).trim();
                                //获取成交量数据
                                posi = str.indexOf("成交量:");
                                str = str.substring(posi + "成交量:".length());
                                posi = str.indexOf("流通市值:");
                                stock[10][1] = str.substring(0, posi).trim();
                                posi = stock[10][1].indexOf("(");
                                stock[10][1] = stock[10][1].substring(0, posi).trim();

                            } else {
                                //将含有"<td"和"</td>"的字符串添加到list中去
                                while (resultStr.indexOf("<td") != -1) {
                                    resultStr = resultStr.substring(resultStr.indexOf("<td"), resultStr.length());
                                    str = resultStr.substring(resultStr.indexOf("<td"), resultStr.indexOf("</td>") + 5);
                                    list.add(str);
                                    resultStr = resultStr.substring(resultStr.indexOf("</td>") + 5, resultStr.length());
                                }
                                //将list中的所有字符串通过正则表达式进行分析
                                for (int m = 0; m < list.size(); m++) {
                                    if ((((String) list.get(m)).indexOf("yfnc_tabledata1") != -1)) {
                                        Pattern p;
                                        java.util.regex.Matcher matcher1;
                                        java.util.regex.Matcher matcher2;

                                        p = Pattern.compile("<td\\s+class\\s*=\\s*\"yfnc_tabledata1\\s*\">.*</td>");
                                        matcher1 = p.matcher((String) list.get(m));

                                        while ((matcher1.find()) && (i < 16)) {
                                            value = ((String) list.get(m)).substring(matcher1.start() + 28, matcher1.end() - 5);
                                            p = Pattern.compile("<big>\\s*<b>.*</b>\\s*</big>");
                                            matcher2 = p.matcher(value);
                                            if (matcher2.find())
                                                value = value.substring(matcher2.start() + 8, matcher2.end() - 10);
                                            stock[i][1] = value;
                                            i = i + 1;
                                        }
                                    }
                                }
                            }

                            //判断数据库中最后记录与当前获取的记录是否相同
                            existflag = false;
                            if (!existflag)
//                                existflag=new IndexerSearcher().isStockExsit(sbi.getTicker(),stock); //
                                //existflag = spiderMgr.getExist(sbi.getTicker(), stock);
                            //如果数据库中的最后记录与当前获取的记录不同，则向数据库插入新的记录
                            if ((!existflag)) {
                                spiderMgr.insertStock(name, nameid, danwei, stock);
                            }

                            Timestamp time = new Timestamp(System.currentTimeMillis());
                            int hours = time.getHours();
                            int minute = time.getMinutes();
                            int maxid;

                            //写上海股市的股票
                            if (name.equalsIgnoreCase("上海交易所") || name.equalsIgnoreCase("深圳交易所")) {
                                if ((hours == 15) && (minute > 0) && (minute < 5)) {
                                    existflag = spiderMgr.getClosePriceExist(stockcode);
//                                    existflag=new IndexerSearcher().isStockExsit(stockcode);   //check copy
                                    if (!existflag) {
//                                        new IndexerSearcher().addStockIndex(stockcode,stock); //add index
                                        
                                        maxid = spiderMgr.getMaxClosePriceID(stockcode) + 1;
                                        spiderMgr.insertSS_ClosePrice(stock, stockcode, maxid);
                                        FileOutputStream day_k_line = new FileOutputStream(klinepath + File.separator + "d_" + stockcode + ".jpg");
                                        FileOutputStream smallday_k_line = new FileOutputStream(klinepath + File.separator + "small_d_" + stockcode + ".jpg");
                                        FileOutputStream week_k_line = new FileOutputStream(klinepath + File.separator + "w_" + stockcode + ".jpg");
                                        FileOutputStream month_k_line = new FileOutputStream(klinepath + File.separator + "m_" + stockcode + ".jpg");
                                        StockGraphProducer producer = new StockGraphProducer();
                                        producer.createDayK_LineImage(day_k_line, sbi.getTicker());
                                        producer.createSmallDayK_LineImage(smallday_k_line, sbi.getTicker());
                                        producer.createWeekK_LineImage(week_k_line, sbi.getTicker());
                                        producer.createMonthK_LineImage(month_k_line, sbi.getTicker());
                                        day_k_line.close();
                                        smallday_k_line.close();
                                        week_k_line.close();
                                        month_k_line.close();
                                        System.out.println(sbi.getTicker() + "的K线图的生成");
                                    }
                                }
                            } else
                            if (name.equalsIgnoreCase("纽约股票交易所") || sbi.getJysName().equalsIgnoreCase("纳斯达克交易所")) {
                                if ((hours == 4) && (minute > 15) && (minute < 20)) {
                                    existflag = spiderMgr.getClosePriceExist(stockcode);
//                                    existflag=new IndexerSearcher().isStockExsit(stockcode);
                                    if (!existflag) {
                                        //existflag=new IndexerSearcher().isStockExsit(stockcode);   //check copy

                                        maxid = spiderMgr.getMaxClosePriceID(stockcode) + 1;
                                        spiderMgr.insertClosePrice(stock, stockcode, maxid);
                                        FileOutputStream day_k_line = new FileOutputStream(klinepath + File.separator + "d_" + stockcode + ".jpg");
                                        FileOutputStream smallday_k_line = new FileOutputStream(klinepath + File.separator + "small_d_" + stockcode + ".jpg");
                                        FileOutputStream week_k_line = new FileOutputStream(klinepath + File.separator + "w_" + stockcode + ".jpg");
                                        FileOutputStream month_k_line = new FileOutputStream(klinepath + File.separator + "m_" + stockcode + ".jpg");
                                        StockGraphProducer producer = new StockGraphProducer();
                                        producer.createDayK_LineImage(day_k_line, sbi.getTicker());
                                        producer.createSmallDayK_LineImage(smallday_k_line, sbi.getTicker());
                                        producer.createWeekK_LineImage(week_k_line, sbi.getTicker());
                                        producer.createMonthK_LineImage(month_k_line, sbi.getTicker());
                                        day_k_line.close();
                                        smallday_k_line.close();
                                        week_k_line.close();
                                        month_k_line.close();
                                        System.out.println(sbi.getTicker() + "的K线图的生成");
                                    }
                                }
                            } else if (name.equalsIgnoreCase("香港交易所")) {
                                if ((hours == 16) && (minute > 15) && (minute < 20)) {
                                    existflag = spiderMgr.getClosePriceExist(stockcode);
//                                    existflag=new IndexerSearcher().isStockExsit(stockcode);
                                    if (!existflag) {
                                       // existflag=new IndexerSearcher().isStockExsit(stockcode);   //check copy


                                        maxid = spiderMgr.getMaxClosePriceID(stockcode) + 1;
                                        spiderMgr.insertClosePrice(stock, stockcode, maxid);
                                        FileOutputStream day_k_line = new FileOutputStream(klinepath + File.separator + "d_" + stockcode + ".jpg");
                                        FileOutputStream smallday_k_line = new FileOutputStream(klinepath + File.separator + "small_d_" + stockcode + ".jpg");
                                        FileOutputStream week_k_line = new FileOutputStream(klinepath + File.separator + "w_" + stockcode + ".jpg");
                                        FileOutputStream month_k_line = new FileOutputStream(klinepath + File.separator + "m_" + stockcode + ".jpg");
                                        StockGraphProducer producer = new StockGraphProducer();
                                        producer.createDayK_LineImage(day_k_line, sbi.getTicker());
                                        producer.createSmallDayK_LineImage(smallday_k_line, sbi.getTicker());
                                        producer.createWeekK_LineImage(week_k_line, sbi.getTicker());
                                        producer.createMonthK_LineImage(month_k_line, sbi.getTicker());
                                        day_k_line.close();
                                        smallday_k_line.close();
                                        week_k_line.close();
                                        month_k_line.close();
                                        System.out.println(sbi.getTicker() + "的K线图的生成");
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    System.out.println("sleeping...");
                    Thread.sleep(1000 * 20);                     //延迟1分钟后重新抓取股票数据
                } catch (Exception e) {
                    System.out.println("Sleep falied!");
                }
            }
        }
    }
}