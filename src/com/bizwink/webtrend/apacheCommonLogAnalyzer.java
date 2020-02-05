package com.bizwink.webtrend;

import com.bizwink.cms.news.*;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-31
 * Time: 16:43:06
 * To change this template use File | Settings | File Templates.
 */
public class apacheCommonLogAnalyzer {
    private String logpath;
    PoolServer cpool;

    apacheCommonLogAnalyzer(String logpath,PoolServer cpool) {
        this.logpath = logpath;
        this.cpool = cpool;
    }

    public List anaTheLog() {
        Connection conn = null;
        PreparedStatement pstmt=null;

        List pvs = new ArrayList();
        ArrayList uip = new ArrayList();
        if (!logpath.endsWith(java.io.File.separator)) logpath = logpath + java.io.File.separator;
        File file = new File(logpath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(logpath));
            String line = "";
            log log1 = new log();
            while((line=reader.readLine())!=null){
                int posi = line.indexOf(" ");
                String buf = line.substring(0,posi).trim();
                log1.setServer_ip(buf);

                //
                line = line.substring(posi+1).trim();
                posi = line.indexOf(" ");
                line = line.substring(posi+1).trim();
                posi = line.indexOf(" ");
                line = line.substring(posi+1).trim();

                //
                posi = line.indexOf(" ");
                buf = line.substring(0,posi).trim();
                buf = buf.substring(1);
                //28/Jul/2011:00:00:00
                DateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z",Locale.US);
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
                if ((buf.indexOf(".shtml")>-1 || buf.endsWith("/") || buf.indexOf(".jsp")>-1)) {
                    log1.setUrl(buf);
                    String filename = "";
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
                        pv.setAccesstime(log1.getAccesstime());
                        pvs.add(pv);
                    }
                }
                line = line.substring(posi+1).trim();

                //��ȡHTTPЭ��汾
                //System.out.println(line);
                posi = line.indexOf(" ");
                if (posi > -1) {
                    buf = line.substring(0,posi).trim();
                    buf = buf.substring(0,buf.length() -1);
                    log1.setHttpversion(buf);
                    line = line.substring(posi+1);
                }

                //��ȡHTTP״̬��
                posi = line.indexOf(" ");
                if (posi>-1) {
                    buf = line.substring(0,posi).trim();
                    if (!buf.equals("-"))
                        if(isNumeric(buf))
                            log1.setCode(Integer.parseInt(buf));
                        else
                            log1.setCode(-1);
                    else
                        log1.setCode(0);
                    line = line.substring(posi+1);
                }

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

            try {
                //ISequenceManager sequnceMgr = SequencePeer.getInstance();
                //conn = cpool.getConnection();
                conn = createConnection("210.73.87.113", "bj2008dbadmin", "qazwsxokm", 1);

                //if (cpool.getType().equalsIgnoreCase("oracle"))
                //pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate,id) VALUES (?, ?, ?, ?, ?, ?)");
                //else if (cpool.getType().equalsIgnoreCase("mssql"))
                //    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate) VALUES (?, ?, ?, ?, ?)");
                //else
                //    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate) VALUES (?, ?, ?, ?, ?)");

                String SEQ_PVDETAIL_ID = "select pv_detail_id.NEXTVAL from dual";
                pageview pv = null;
                int siteid = 2;
                int nextID = 0;
                for(int i=0; i<pvs.size(); i++) {
                    try {
                        pstmt = conn.prepareStatement(SEQ_PVDETAIL_ID);
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) nextID = rs.getInt(1);
                        rs.close();
                        pstmt.close();

                        pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate,id) VALUES (?, ?, ?, ?, ?, ?)");
                        pv = new pageview();
                        pv = (pageview)pvs.get(i);
                        pstmt.setInt(1,siteid);
                        pstmt.setString(2,pv.getUrl());
                        pstmt.setString(3,pv.getCnname());
                        pstmt.setInt(4,pv.getPv());
                        pstmt.setTimestamp(5,pv.getAccesstime());
                        pstmt.setInt(6, nextID);
                        pstmt.executeUpdate();
                        System.out.println(nextID + "---" + pv.getUrl() + "---" + pv.getCnname() + "====" + pv.getPv());
                        pstmt.close();
                    } catch (SQLException exp1) {
                        if (pstmt!=null) pstmt.close();
                        exp1.printStackTrace();
                    }
                }
                conn.commit();
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
            System.out.println("total pageview=" + pvs.size());

        } catch (IOException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp2) {

            }
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

                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:ispdb", dbusername, dbpassword);

                //conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl10g", dbusername, dbpassword);

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
