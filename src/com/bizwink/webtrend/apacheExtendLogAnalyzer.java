package com.bizwink.webtrend;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-7
 * Time:  :13
 * To change this template use File | Settings | File Templates.
 */
public class apacheExtendLogAnalyzer {
    private String logpath;
    PoolServer cpool;

    apacheExtendLogAnalyzer(String logpath,PoolServer cpool) {
        this.logpath = logpath;
        this.cpool = cpool;
    }

    List anaTheLog() {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        List pvs = new ArrayList();
        ArrayList uip = new ArrayList();
        ArrayList sessions = new ArrayList();
        if (!logpath.endsWith(java.io.File.separator)) logpath = logpath + java.io.File.separator;
        File file = new File(logpath);
        String[] logfiles = file.list();
        int[] count = new int[logfiles.length];
        try {
            log log1 = new log();
            UserSession us = new UserSession();
            for(int i=0; i<logfiles.length; i++) {
                //int i=0;
                count[i] = 0;
                System.out.println(logpath + logfiles[i]);
                BufferedReader reader = new BufferedReader(new FileReader(logpath + logfiles[i]));
                String line = "";
                int linenum = 0;
                while((line=reader.readLine())!=null){
                    linenum = linenum + 1;
                    int posi = line.indexOf(" ");
                    String buf = line.substring(0,posi).trim();
                    log1.setServer_ip(buf);
                    //���ö��ֲ��Һ��������ΨһIP������
                    int key = quickFind(uip,getStringIpToLong(buf));

                    //System.out.println("linenum=" + linenum + "===" + buf);

                    //����������õ���Ŀ
                    line = line.substring(posi+1).trim();
                    posi = line.indexOf(" ");
                    line = line.substring(posi+1).trim();
                    posi = line.indexOf(" ");
                    line = line.substring(posi+1).trim();

                    //��ȡ����ʱ��
                    posi = line.indexOf(" ");
                    buf = line.substring(0,posi).trim();
                    buf = buf.substring(1);
                    //28/Jul/2011:00:00:00
                    DateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
                    try {
                        Date date = df.parse(buf + " +0800 ");
                        log1.setAccesstime(new Timestamp(date.getTime()));
                    } catch(ParseException exp) {
                        exp.printStackTrace();
                    }
                    line = line.substring(posi+1);

                    //���ʱ�������
                    posi = line.indexOf(" ");
                    buf = line.substring(0,posi).trim();
                    line = line.substring(posi+1);

                    //��ȡ���ʷ���
                    posi = line.indexOf(" ");
                    buf = line.substring(0,posi).trim();
                    buf = buf.substring(1);
                    log1.setMethod(buf);
                    line = line.substring(posi+1);

                    //��ȡ����url
                    posi = line.indexOf(" ");
                    buf = line.substring(0,posi).trim();
                    posi = buf.indexOf("?");
                    if (posi>-1) buf = buf.substring(0,posi);
                    if (buf.endsWith(".htm") || buf.endsWith(".html") || buf.endsWith(".shtml") || buf.endsWith("/") || buf.endsWith(".jsp")) {
                        log1.setUrl(buf);
                        pageview pv = null;
                        ArrayList url_iplist = null;
                        ArrayList logbyurl = null;
                        boolean existurl = false;
                        for(int jj = 0; jj<pvs.size(); jj++) {
                            pv = (pageview)pvs.get(jj);
                            if (pv.getUrl().equals(buf)) {
                                existurl = true;
                                break;
                            }
                        }

                        if (existurl) {
                            url_iplist = pv.getUniqueiplist();
                            key = quickFind(url_iplist,getStringIpToLong(log1.getServer_ip()));
                            logbyurl = pv.getLogsByURL();
                            logbyurl.add(log1);
                            pv.setLogsByURL(logbyurl);
                            int num = pv.getPv() + 1;
                            pv.setPv(num);
                        } else {
                            logbyurl = new ArrayList();                 //���水URL���ֵ�LOG
                            url_iplist = new ArrayList();
                            pv = new pageview();
                            url_iplist.add(getStringIpToLong(log1.getServer_ip()));
                            logbyurl.add(log1);
                            pv.setUniqueiplist(url_iplist);
                            pv.setLogsByURL(logbyurl);
                            pv.setUrl(buf);
                            pv.setPv(1);
                            pvs.add(pv);
                        }

                        count[i] = count[i] + 1;
                        /*String filename = "";
                        String dirname = "";
                        String extname = "";
                        int slash_posi = buf.lastIndexOf("/");
                        if (slash_posi > -1) {
                            filename = buf.substring(slash_posi+1);
                            dirname = buf.substring(0,slash_posi);
                        }
                        int dotposi = filename.lastIndexOf(".");
                        Article article = null;
                        if (dotposi > -1) {
                            filename = filename.substring(0,dotposi);
                            if(isNumeric(filename)) {
                                article = articleMgr.getArticle(Integer.parseInt(filename));
                            }
                            extname = filename.substring(dotposi);
                        }
                        if (dirname.startsWith("/big5"))
                            dirname = dirname.substring(5);

                        pageview pv = null;
                        boolean existurl = false;
                        for(int i = 0; i<pvs.size(); i++) {
                            pv = (pageview)pvs.get(i);
                            if (pv.getUrl().equals(buf)) {
                                existurl = true;
                                break;
                            }
                        }

                        if (existurl) {
                            int num = pv.getPv() + 1;
                            pv.setPv(num);
                        } else {
                            pv = new pageview();
                            pv.setFilename(filename);
                            if (article!= null)
                                pv.setCnname(StringUtil.gb2iso4View(article.getMainTitle()));
                            else
                                pv.setCnname("-");
                            pv.setDirname(dirname);
                            pv.setUrl(buf);
                            pv.setPv(1);
                            pvs.add(pv);
                        }*/
                    }
                    line = line.substring(posi+1).trim();

                    //��ȡHTTPЭ��汾
                    //System.out.println(line);
                    posi = line.indexOf(" ");
                    buf = line.substring(0,posi).trim();
                    buf = buf.substring(0,buf.length() -1);
                    log1.setHttpversion(buf);
                    line = line.substring(posi+1);

                    //��ȡHTTP״̬��
                    posi = line.indexOf(" ");
                    buf = line.substring(0,posi).trim();
                    if (!buf.equals("-"))
                        try {
                            if(isNumeric(buf))
                                log1.setCode(Integer.parseInt(buf));
                            else
                                log1.setCode(-1);
                        } catch (NumberFormatException numexp) {
                            log1.setCode(-1);
                        }
                    else
                        log1.setCode(0);
                    line = line.substring(posi+1);

                    //��ȡ������Ϣ�Ĵ�С
                    posi = line.indexOf(" ");
                    if (posi > -1) {
                        buf = line.substring(0,posi).trim();
                        if (!buf.equals("-"))
                            if(isNumeric(buf))
                                log1.setInfosize(Integer.parseInt(buf));
                            else
                                log1.setInfosize(-1);
                        else
                            log1.setInfosize(0);
                        line = line.substring(posi+1);
                    }

                    //��ȡrefer url
                    posi = line.indexOf(" ");
                    if (posi > -1) {
                        buf = line.substring(0,posi).trim();
                        log1.setRefer_url(buf);
                        line = line.substring(posi+1);
                    }
                    //ʣ����ϢΪ���������Ϣ
                    log1.setAgent(line);
                }
            }

            try {
                //PV���������Ϣ������ݿ�
                conn = cpool.getConnection();
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate,id) VALUES (?, ?, ?, ?, ?, ?)");
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate) VALUES (?, ?, ?, ?, ?)");
                else
                    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate) VALUES (?, ?, ?, ?, ?)");
                pageview pv = null;
                int siteid = 1;
                for(int i=0; i<pvs.size(); i++) {
                    pv = new pageview();
                    pv = (pageview)pvs.get(i);
                    pstmt.setInt(1,siteid);
                    pstmt.setString(2,pv.getUrl());
                    pstmt.setString(3,pv.getCnname());
                    pstmt.setInt(4,pv.getPv());
                    pstmt.setTimestamp(5,log1.getAccesstime());
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setInt(6, sequnceMgr.getSequenceNum("PVDetail"));
                    }
                    pstmt.executeUpdate();
                    System.out.println(pv.getUrl() + "---" + pv.getCnname() + "====" + pv.getPv());
                }
                pstmt.close();
                conn.commit();
                System.out.println("total pageview=" + pvs.size());
            } catch (SQLException exp) {
                exp.printStackTrace();
            }

            System.out.println("total uip" + "===" + uip.size());
            int totalpv = 0;
            for(int ii=0;ii<count.length;ii++) {
                totalpv = totalpv + count[ii];
            }
            System.out.println("total pv" + "===" + totalpv);
            for(int ii=0; ii<pvs.size();ii++) {
                pageview pv = (pageview)pvs.get(ii);
                //System.out.println(pv.getUrl() + "===" + pv.getPv() + "===uniuqeip==" + pv.getUniqueiplist().size() + "==logbyurl=" + pv.getLogsByURL().size());
                ArrayList tlogs = pv.getLogsByURL();
                for(int jj=0; jj<tlogs.size(); jj++)  {
                    log tlog = (log)tlogs.get(jj);
                    System.out.println(tlog.getServer_ip() + "==" + tlog.getAccesstime() + "===" + tlog.getUrl());
                }
            }

        } catch (IOException ioexp) {
            ioexp.printStackTrace();
        }

        return pvs;
    }

    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

    public long getStringIpToLong(String ip) {
        String[] ips = ip.split("[.]");
        long num =  16777216L*Long.parseLong(ips[0]) + 65536L*Long.parseLong(ips[1]) + 256*Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
        return num;
    }

    public String getLongIpToString(long ipLong) {

        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};
        long num = 0;
        StringBuffer ipInfo = new StringBuffer();
        for(int i=0;i<4;i++){
            num = (ipLong & mask[i])>>(i*8);
            if(i>0) ipInfo.insert(0,".");
            ipInfo.insert(0,Long.toString(num,10));
        }
        return ipInfo.toString();
    }

    //���ö��ֲ��ҿ��ٲ���IP
    public int quickFind(ArrayList uip,Long ip) {
        int low;
        int high;
        int mid = -1;

        if(uip == null)
            return -1;

        low = 0;
        high = uip.size() - 1;
        boolean existflag = false;

        // System.out.println("ip=" + getLongIpToString(ip) + "===" + ip);

        while(low <= high){
            mid = (low + high) / 2;
            Long middleValue = (Long)uip.get(mid);
            // System.out.println("mid " + mid + " mid value:" + middleValue);///
            if(ip.compareTo(middleValue) < 0){
                high = mid - 1;
            }else if(ip.compareTo(middleValue) > 0){
                low = mid + 1;
            }else if(ip.compareTo(middleValue) == 0){
                existflag = true;
                return mid;
            }
        }

        //���û���ҵ�Ҫ���ҵ�IP��ַ������uip�б��е�p_low��p_high֮���������µ�IP
        if (existflag == false) {
            if (uip.size() > 1) {
                Long tt = (Long)uip.get(mid);
                if (ip.compareTo(tt) > 0) {
                    uip.add(mid+1,ip);
                } else {
                    uip.add(mid,ip);
                }
            }
        }

        //ֱ����uip�м����һ�͵ڶ���Ԫ��
        if (uip.size() ==0) {
            uip.add(ip);
        } else if (uip.size() ==1) {
            Long tt = (Long)uip.get(0);
            if (tt.compareTo(ip) > 0)
                uip.add(0,ip);
            else if (tt.compareTo(ip) < 0)
                uip.add(ip);
        }

        /*System.out.println("uip.size=" + uip.size());
        for(int ii=0; ii<uip.size();ii++) {
            Long t = (Long)uip.get(ii);
            System.out.println("ip" +  "===" + t + "===" + getLongIpToString(t));
        }*/

        return -1;
    }

    private Connection createConnection(String ip, String username, String password, int flag) {
        Connection conn = null;
        String dbip = ip;
        String dbusername = username;
        String dbpassword = password;

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":1433", dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl", dbusername, dbpassword);

            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":3306/ecms", username, password);
            } else if (flag == 3){
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String strurl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=e:\\dataforonegoo\\wsjc10-08-02-1.mdb";
                conn = DriverManager.getConnection(strurl,username,password);

            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }
}
