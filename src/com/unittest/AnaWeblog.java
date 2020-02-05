package com.unittest;

import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.FileUtil;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.webtrend.IPInfos;
import com.bizwink.webtrend.pageview;
import com.heaton.bot.startSpider;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 17-4-5.
 */
public class AnaWeblog {
    private static void AnaLogByWholeLogfile(int siteid,String startDate,String endDate,String logfile) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = DBUtil.createConnection("localhost", "bjsjsdbadmin", "qazwsxokm", "orcl11g", 1521, 1);
        String SEQ_PVDETAIL_ID = "select pv_detail_id.NEXTVAL from dual";

        String filename = logfile;
        FileWriter fw = null;
        BufferedWriter writer = null;
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar s_cal = Calendar.getInstance();
        Calendar e_cal = Calendar.getInstance();

        try {
            if (endDate == null) {
                s_cal.setTime(sdf.parse(startDate));
                e_cal.setTime(sdf.parse(startDate));
            } else {
                s_cal.setTime(sdf.parse(startDate));
                e_cal.setTime(sdf.parse(endDate));
                e_cal.add(Calendar.DAY_OF_MONTH,1);
            }
        } catch (ParseException pexp) {

        }

        sdf = new SimpleDateFormat("dd" + "/MMM/yyyy:HH:mm:ss Z",Locale.US);
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        String lineTxt = null;
        List pvs = null;
        Calendar calendar = Calendar.getInstance();
        while(s_cal.getTime().compareTo(e_cal.getTime())<=0){   //判断是否到结束日期
            pvs = new ArrayList();
            try{
                read = new InputStreamReader(new FileInputStream(new File(filename)));
                bufferedReader = new BufferedReader(read);
                while((lineTxt = bufferedReader.readLine()) != null){
                    String field[] = lineTxt.split(" ");
                    String strdate = field[3].substring(1) + " +0800";
                    java.util.Date date = null;
                    try {
                        date = sdf.parse(strdate);
                        calendar.setTime(date);
                    } catch (ParseException exp) {
                        exp.printStackTrace();
                    }

                    if ((field[5].equalsIgnoreCase("\"get") || field[5].equalsIgnoreCase("\"post") || field[5].equalsIgnoreCase("\"head")) && date != null) {     //每行分解出的项数大于6，执行下面的操作
                        if (s_cal.get(Calendar.YEAR)==calendar.get(Calendar.YEAR) && s_cal.get(Calendar.MONTH)==calendar.get(Calendar.MONTH) && s_cal.get(Calendar.DAY_OF_MONTH)==calendar.get(Calendar.DAY_OF_MONTH)){
                            if (field[6].toLowerCase().endsWith(".jsp") || field[6].toLowerCase().endsWith(".shtml")|| field[6].toLowerCase().endsWith(".html")
                                    || field[6].toLowerCase().endsWith(".htm")|| field[6].toLowerCase().endsWith(".action")
                                    || field[6].toLowerCase().endsWith(".pdf")|| field[6].toLowerCase().endsWith(".doc")|| field[6].toLowerCase().endsWith(".docx")
                                    || field[6].toLowerCase().endsWith(".ppt")|| field[6].toLowerCase().endsWith(".pptx")|| field[6].toLowerCase().endsWith(".xls")
                                    || field[6].toLowerCase().endsWith(".xlsx")|| field[6].toLowerCase().endsWith("/")) {
                                if (!field[6].toLowerCase().endsWith("VeriCode.do")) {
                                    pageview pv = null;
                                    boolean existurl = false;
                                    for(int i = 0; i<pvs.size(); i++) {
                                        pv = (pageview)pvs.get(i);
                                        if (pv.getUrl().equals(field[6])) {
                                            existurl = true;
                                            break;
                                        }
                                    }

                                    if (existurl) {
                                        int num = pv.getPv() + 1;
                                        pv.setPv(num);
                                    } else {
                                        pv = new pageview();
                                        int posi = field[6].lastIndexOf("/");
                                        if (posi > -1)
                                            pv.setFilename(field[6].substring(field[6].lastIndexOf("/") + 1));
                                        else
                                            pv.setFilename(field[6]);
                                        pv.setUrl(field[6]);
                                        pv.setPv(1);
                                        pv.setAccesstime(new Timestamp(date.getTime()));
                                        pvs.add(pv);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException ioexp) {
                ioexp.printStackTrace();
            }

            try {
                String strdate = s_cal.get(Calendar.YEAR) + "-" + (s_cal.get(Calendar.MONTH)+1) + "-" +s_cal.get(Calendar.DAY_OF_MONTH);
                File file = new File("C:\\data\\" + strdate + ".txt");
                fw = new FileWriter(file);
                writer = new BufferedWriter(fw);
                pageview pv = null;
                int totalpv = 0;
                for(int ii=0; ii<pvs.size(); ii++) {
                    pv = (pageview)pvs.get(ii);
                    totalpv = totalpv + pv.getPv();
                    writer.write(pv.getUrl() + "===" + pv.getPv());
                    writer.newLine();//换行
                    System.out.println(pv.getUrl() + "===" + pv.getPv());

                    int id = 0;
                    pstmt = conn.prepareStatement(SEQ_PVDETAIL_ID);
                    rs = pstmt.executeQuery();
                    if (rs.next()) id = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement("insert into tbl_pv_detail(id,siteid,urlname,pageview,logdate) values(?, ?, ?, ?, ?)");
                    pstmt.setInt(1,id);
                    pstmt.setInt(2,siteid);
                    pstmt.setString(3,pv.getUrl());
                    pstmt.setInt(4,pv.getPv());
                    pstmt.setDate(5,new Date(pv.getAccesstime().getTime()));
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                writer.write("total pv==" + totalpv);
                writer.flush();
                System.out.println("total pv==" + totalpv);
                s_cal.add(Calendar.DAY_OF_MONTH, 1);//进行当前日期月份加1
            } catch (IOException exp) {
                exp.printStackTrace();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
            finally{
                try {
                    writer.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void AnaLogByDirectory(int siteid,String logdir) {
        File logfiles = new File(logdir);
        String[] tt = logfiles.list();
        if (tt!=null) {
            for(int jj=0; jj<tt.length; jj++) {
                if (!logdir.endsWith(File.separator)) logdir = logdir + File.separator;
                String filename = logdir + tt[jj];
                FileWriter fw = null;
                BufferedWriter writer = null;
                DateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                sdf = new SimpleDateFormat("dd" + "/MMM/yyyy:HH:mm:ss Z",Locale.US);
                InputStreamReader read = null;
                BufferedReader bufferedReader = null;
                String lineTxt = null;
                List pvs = null;
                List<IPInfos> ipInfoses =  new ArrayList();
                Calendar calendar = Calendar.getInstance();
                pvs = new ArrayList();
                Map<String,List> urlsForIP = new HashMap();
                Map<String,List> accessDateTimes = new HashMap();
                try{
                    read = new InputStreamReader(new FileInputStream(new File(filename)));
                    bufferedReader = new BufferedReader(read);
                    while((lineTxt = bufferedReader.readLine()) != null){
                        String field[] = lineTxt.split(" ");
                        String strdate = field[3].substring(1) + " +0800";
                        java.util.Date date = null;
                        int errcode = 0;
                        try {
                            date = sdf.parse(strdate);
                            calendar.setTime(date);
                        } catch (ParseException exp) {
                            errcode = -1;
                        }

                        if (errcode==0) {
                            if ((field[5].equalsIgnoreCase("\"get") || field[5].equalsIgnoreCase("\"post") || field[5].equalsIgnoreCase("\"head")) && date != null) {     //每行分解出的项数大于6，执行下面的操作
                                if (field[6].toLowerCase().endsWith(".jsp") || field[6].toLowerCase().endsWith(".shtml")|| field[6].toLowerCase().endsWith(".html")
                                        || field[6].toLowerCase().endsWith(".htm")|| field[6].toLowerCase().endsWith(".action")
                                        || field[6].toLowerCase().endsWith(".pdf")|| field[6].toLowerCase().endsWith(".doc")|| field[6].toLowerCase().endsWith(".docx")
                                        || field[6].toLowerCase().endsWith(".ppt")|| field[6].toLowerCase().endsWith(".pptx")|| field[6].toLowerCase().endsWith(".xls")
                                        || field[6].toLowerCase().endsWith(".xlsx")|| field[6].toLowerCase().endsWith("/")) {
                                    if (!field[6].toLowerCase().endsWith("VeriCode.do")) {
                                        //分析PV
                                        pageview pv = null;
                                        boolean existurl = false;
                                        for(int i = 0; i<pvs.size(); i++) {
                                            pv = (pageview)pvs.get(i);
                                            if (pv.getUrl().equals(field[6])) {
                                                existurl = true;
                                                break;
                                            }
                                        }

                                        if (existurl) {
                                            int num = pv.getPv() + 1;
                                            pv.setPv(num);
                                        } else {
                                            pv = new pageview();
                                            int posi = field[6].lastIndexOf("/");
                                            if (posi > -1)
                                                pv.setFilename(field[6].substring(field[6].lastIndexOf("/") + 1));
                                            else
                                                pv.setFilename(field[6]);
                                            pv.setUrl(field[6]);
                                            pv.setPv(1);
                                            pv.setAccesstime(new Timestamp(date.getTime()));
                                            pvs.add(pv);
                                        }

                                        //分析独立IP信息
                                        boolean ipExistFlag = false;
                                        IPInfos ipInfos = null;
                                        int ipInfoses_index = 0;
                                        for(int ii=0; ii<ipInfoses.size(); ii++) {
                                            ipInfos = ipInfoses.get(ii);
                                            if (ipInfos.getIpaddress().equals(field[0])) {
                                                ipExistFlag = true;
                                                ipInfoses_index = ii;
                                                break;
                                            }
                                        }
                                        //如果这个IP地址已经在IP地址表中存在，增加该IP地址访问的URL和访问时间
                                        if (ipExistFlag == true) {
                                            //该IP地址访问次数增加1
                                            ipInfos.setAccessnum(ipInfos.getAccessnum()+1);
                                            //设置本次访问的URL地址
                                            List urls = urlsForIP.get(field[0]);
                                            urls.add(field[6]);
                                            urlsForIP.put(field[0], urls);
                                            ipInfos.setUrls(urlsForIP);
                                            //设置本次访问的时间
                                            List accessDateTime = accessDateTimes.get(field[0]);
                                            accessDateTime.add(date);
                                            accessDateTimes.put(field[0],accessDateTime);
                                            ipInfos.setAccesstime(accessDateTimes);
                                            //修改与IP相关的信息
                                            ipInfoses.set(ipInfoses_index,ipInfos);
                                        } else {
                                            //分配存储IP地址相关信息的对象
                                            ipInfos = new IPInfos();
                                            //设置IP地址
                                            ipInfos.setIpaddress(field[0]);
                                            //该IP地址访问次数增加1
                                            ipInfos.setAccessnum(1);
                                            //设置本次访问的URL地址
                                            List urls = new ArrayList();
                                            urls.add(field[6]);
                                            urlsForIP.put(field[0], urls);
                                            ipInfos.setUrls(urlsForIP);
                                            //设置本次访问的时间
                                            List accessDateTime = new ArrayList();
                                            accessDateTime.add(date);
                                            accessDateTimes.put(field[0],accessDateTime);
                                            ipInfos.setAccesstime(accessDateTimes);
                                            //增加与IP相关的信息
                                            ipInfoses.add(ipInfos);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException ioexp) {
                    ioexp.printStackTrace();
                }

                /*for(int ii=0;ii<ipInfoses.size();ii++) {
                    IPInfos ipInfos = ipInfoses.get(ii);
                    System.out.println(ipInfos.getIpaddress() + "==" + ipInfos.getAccessnum() + "==" + ipInfos.getAccesstime().get(ipInfos.getIpaddress()).size() + "==" +ipInfos.getUrls().get(ipInfos.getIpaddress()).size());
                }
                com.bizwink.util.FileUtil.writeExcel(ipInfoses,3,"c:\\data\\" + tt[jj] + ".xls");
                */

                PreparedStatement pstmt = null;
                ResultSet rs = null;
                Connection conn = DBUtil.createConnection("localhost", "bjsjsdbadmin", "qazwsxokm", "orcl11g", 1521, 1);
                String SEQ_PVDETAIL_ID = "select pv_detail_id.NEXTVAL from dual";

                try {
                    //File file = new File("C:\\ksflogresult\\" + tt[jj] + ".txt");
                    //fw = new FileWriter(file);
                    //writer = new BufferedWriter(fw);
                    conn.setAutoCommit(false);
                    pageview pv = null;
                    int totalpv = 0;
                    for(int ii=0; ii<pvs.size(); ii++) {
                        pv = (pageview)pvs.get(ii);
                        totalpv = totalpv + pv.getPv();
                        //writer.write(pv.getUrl() + "===" + pv.getPv());
                        //writer.newLine();//换行
                        //System.out.println(pv.getUrl() + "===" + pv.getPv());

                        int id = 0;
                        pstmt = conn.prepareStatement(SEQ_PVDETAIL_ID);
                        rs = pstmt.executeQuery();
                        if (rs.next()) id = rs.getInt(1);
                        rs.close();
                        pstmt.close();

                        pstmt = conn.prepareStatement("insert into tbl_pv_detail(id,siteid,urlname,pageview,logdate) values(?, ?, ?, ?, ?)");
                        pstmt.setInt(1,id);
                        pstmt.setInt(2,siteid);
                        pstmt.setString(3,pv.getUrl());
                        pstmt.setInt(4,pv.getPv());
                        pstmt.setDate(5,new Date(pv.getAccesstime().getTime()));
                        pstmt.executeUpdate();
                        pstmt.close();
                    }

                    //写入独立IP信息
                    for(int ii=0;ii<ipInfoses.size();ii++) {
                        IPInfos ipInfos = ipInfoses.get(ii);
                    }

                    //writer.write("total pv==" + totalpv);
                    //writer.flush();
                    //System.out.println("total pv==" + totalpv);
                } catch (SQLException exp) {
                    exp.printStackTrace();
                } //catch (IOException exp) {
                //  exp.printStackTrace();
                //}
                finally{
                    try {
                        //writer.close();
                        //fw.close();
                        conn.commit();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("目录不存在");
        }
    }

    private static String getDateFromLineinfo(String info) {
        int posi = info.indexOf("[");
        String tempTimestamp = null;
        if (posi>-1) tempTimestamp = info.substring(posi+1);
        posi = tempTimestamp.indexOf("]");
        if (posi>-1) tempTimestamp = tempTimestamp.substring(0,posi);
        posi = tempTimestamp.indexOf(" ");
        if (posi>-1) tempTimestamp = tempTimestamp.substring(0,posi);
        //22/Jan/2018:23:10:05
        posi = tempTimestamp.indexOf(":");
        String datestr = tempTimestamp.substring(0,posi);
        String[] ymd = datestr.split("/");
        String numeric_date = StringUtil.getNumricMonth(ymd[1]);

        return ymd[2] +"-" + numeric_date + "-" + ymd[0];
    }

    private static void splitLogFile(String path,String logfile) {
        File file = new File(logfile);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            StringBuffer content = null;
            String startDate = null;
            int count = 0;
            int startline = 0;
            int row = 0;
            while ((tempString = reader.readLine()) != null) {
                if (count==startline) {
                    startDate = getDateFromLineinfo(tempString);
                    content = new StringBuffer();
                    content.append(tempString + "\r\n");
                    count = count + 1;
                    row = 1;
                } else {
                    String theDate = getDateFromLineinfo(tempString);
                    if (theDate.equals(startDate)) {
                        content.append(tempString + "\r\n");
                        row = row + 1;
                        count = count + 1;
                        System.out.println(row + "==" + tempString);
                        if (row == 100000) {
                            FileUtil.appendWriteFile(content,path + startDate +".txt");      //每次读满10万行向文件写入数据
                            content = new StringBuffer();
                            row = 0;                                                          //每日文件读行数计数器初始化为0，重新统计读取行数
                        }
                    } else {
                        FileUtil.appendWriteFile(content,path + startDate +".txt");
                        startline = count;          //开始写新一天的文件
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static void main(String[] args) {
        int siteid=40;    //220康师傅   //221是现代农业信息网    //222石景山区政府
        AnaLogByWholeLogfile(siteid,"20200114","20200114","C:\\projects\\weblog\\testdata\\11\\20200115_access.log");
        //AnaLogByDirectory(siteid,"C:\\projects\\weblog\\testdata\\11");
        //splitLogFile("C:\\projects\\weblog\\testdata\\11\\","C:\\projects\\weblog\\testdata\\11\\20181230_access.log");
    }
}
