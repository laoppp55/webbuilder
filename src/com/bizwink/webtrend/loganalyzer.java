package com.bizwink.webtrend;

import com.bizwink.calendar.SmartDate;
import com.bizwink.calendar.SmartDateImpl;
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
public class loganalyzer  implements Runnable {
    private Thread runner;
    private String logFileString;
    private PrintWriter log;
    private String pid;
    PoolServer cpool;
    String logfilename;

    public loganalyzer() throws IOException {

    }

    public loganalyzer(String logfile) throws IOException {
        this.logfilename = logfile;
    }

    public loganalyzer(String logPath, PoolServer cpool) throws IOException {
        this.cpool = cpool;

        try {
            if (logPath!= null) {
                int posi = logPath.lastIndexOf(File.separator);
                if (posi>-1) logPath = logPath.substring(0,posi+1);
                this.logFileString = logPath;
                log = new PrintWriter(new FileOutputStream(logFileString + "logana_server.log"), true);
            }
        }
        catch (IOException e1) {
            System.err.println("Warning:Indexer could not open \""
                    + logFileString + "\" to write log to. Make sure that your Java " +
                    "process has permission to write to the file and that the directory exists."
            );
            try {
                log = new PrintWriter(new FileOutputStream("DCB_" + System.currentTimeMillis() + ".log"), true
                );
            }
            catch (IOException e2) {
                throw new IOException("Can't open any log file");
            }
        }

        // Write the pid file (used to clean up dead/broken connection)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
        java.util.Date nowc = new java.util.Date();
        pid = formatter.format(nowc);

        BufferedWriter pidout = new BufferedWriter(new FileWriter(logFileString + "pid"));
        pidout.write(pid);
        pidout.close();

        log.println("Starting Multimedia:");
        log.println("-----------------------------------------");
        log.println(pidout.toString());

        runner = new Thread(this);
        runner.start();
    }

    public void run() {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int logtype = 0;       //0--IIS???LOG,1--IIS???LOG 2-APACHE???LOG 3-APACHE???LOG
        //logtype = 4;
        //logtype = 2;

        List pvs = new ArrayList();
       // IArticleManager articleMgr = ArticlePeer.getInstance();
       // IColumnManager columnMgr = ColumnPeer.getInstance();
       // ISequenceManager sequnceMgr = SequencePeer.getInstance();
        //while (true) {
        try {
            if (logtype == 0) {
                File dirs = new File("E:\\ksflog\\11");
                File[]  files = dirs.listFiles();
                FileWriter fw = new FileWriter("E:\\ksflog\\1.txt");
                for (int ii=0;ii<files.length;ii++) {
                    String logfilename = files[ii].getParent() + java.io.File.separator + files[ii].getName();
                    System.out.println(logfilename);
                    BufferedReader reader = new BufferedReader(new FileReader(logfilename));
                    String line = "";
                    log log1 = new log();
                    pvs = new ArrayList();
                    int ll = 0;
                    while((line=reader.readLine())!=null){
                        ll = ll + 1;
                        int posi = line.indexOf(" ");
                        String buf = line.substring(0,posi).trim();
                        log1.setServer_ip(buf);

                        line = line.substring(posi+1).trim();
                        posi = line.indexOf(" ");
                        line = line.substring(posi+1).trim();
                        posi = line.indexOf(" ");
                        line = line.substring(posi+1).trim();

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

                        posi = line.indexOf(" ");
                        buf = line.substring(0,posi).trim();
                        line = line.substring(posi+1);

                        //回去请求方法
                        posi = line.indexOf(" ");
                        buf = line.substring(0,posi).trim();
                        buf = buf.substring(1);
                        log1.setMethod(buf);

                        //获取请求的URL
                        line = line.substring(posi+1);
                        posi = line.indexOf(" ");
                        String request_url = line.substring(0,posi).trim();

                        //获取HTTP协议的版本号
                        line = line.substring(posi+1).trim();
                        posi = line.indexOf(" ");
                        if (posi>-1) {
                            buf = line.substring(0,posi).trim();
                            buf = buf.substring(0,buf.length() -1);
                            log1.setHttpversion(buf);
                            line = line.substring(posi+1);
                        }

                        //获取返回码
                        posi = line.indexOf(" ");
                        if (posi>-1) {
                            buf = line.substring(0,posi).trim();
                            if (!buf.equals("-"))
                                if(isNumeric(buf))
                                    try {
                                        log1.setCode(Integer.parseInt(buf));
                                    } catch(NumberFormatException numexp) {

                                    }
                                else
                                    log1.setCode(-1);
                            else
                                log1.setCode(0);
                            line = line.substring(posi+1);
                        }

                        //去掉URL问号后面的参数
                        int q_posi = request_url.indexOf("?");
                        if (q_posi>-1) request_url = request_url.substring(0, q_posi);
                        boolean tj_url_condition = request_url.indexOf(".asp") == -1 && request_url.indexOf(".swf") == -1 && request_url.indexOf(".htc") == -1 && request_url.indexOf(".gif") == -1 && request_url.indexOf(".png") == -1 && request_url.indexOf(".jpg") == -1 && request_url.indexOf(".css") == -1 && request_url.indexOf(".js") == -1 && request_url.indexOf(".jpeg") == -1 && request_url.indexOf(".ico") == -1 && request_url.indexOf(".swf") == -1;
                        //处理请求的URL，设置请求URL的PV值
                        if (tj_url_condition && log1.getCode()==200) {
                            System.out.println(ll + "====" + request_url);
                            if (request_url.equals("/contactus/")) {
                                fw.write(log1.getServer_ip() +  "      " + log1.getAccesstime() + "\r\n");
                            }

                            log1.setUrl(request_url);
                            String filename = "";
                            String dirname = "";
                            String extname = "";
                            int slash_posi = request_url.lastIndexOf("/");
                            if (slash_posi > -1) {
                                filename = request_url.substring(slash_posi+1);
                                dirname = request_url.substring(0,slash_posi);
                            }
                            int dotposi = filename.lastIndexOf(".");
                            Article article = null;
                            if (dotposi > -1) {
                                filename = filename.substring(0,dotposi);
                                if(isNumeric(filename)) {
                                    //article = articleMgr.getArticle(Integer.parseInt(filename));
                                }
                                extname = filename.substring(dotposi);
                            }
                            if (dirname.startsWith("/big5"))
                                dirname = dirname.substring(5);

                            pageview pv = null;
                            boolean existurl = false;
                            for(int i = 0; i<pvs.size(); i++) {
                                pv = (pageview)pvs.get(i);
                                if (pv.getUrl().equals(request_url)) {
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
                                pv.setAccesstime(log1.getAccesstime());

                                pv.setUrl(request_url);
                                pv.setPv(1);
                                pvs.add(pv);
                            }
                        }

                        posi = line.indexOf(" ");
                        if (posi > -1) {
                            buf = line.substring(0,posi).trim();
                            if (!buf.equals("-"))
                                if(isNumeric(buf))
                                    try {
                                        log1.setInfosize(Integer.parseInt(buf));
                                    } catch(NumberFormatException numexp) {

                                    }
                                else
                                    log1.setInfosize(-1);
                            else
                                log1.setInfosize(0);
                            line = line.substring(posi+1);
                        }

                        //refer url
                        posi = line.indexOf(" ");
                        if (posi>-1) {
                            buf = line.substring(0,posi).trim();
                            log1.setRefer_url(buf);
                            line = line.substring(posi+1);
                        }

                        log1.setAgent(line);
                    }

                    //conn = cpool.getConnection();
                    conn = createConnection("localhost", "db221admin", "qazwsxokm", 1);
                    conn.setAutoCommit(false);
                    //if (cpool.getType().equalsIgnoreCase("oracle"))
                    //pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate,id) VALUES (?, ?, ?, ?, ?, ?)");
                    //else if (cpool.getType().equalsIgnoreCase("mssql"))
                    //    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate) VALUES (?, ?, ?, ?, ?)");
                    //else
                    //    pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate) VALUES (?, ?, ?, ?, ?)");
                    pageview pv = null;
                    int siteid = 2;
                    int nextID = 0;
                    int pvnum = 0;
                    String SEQ_PVDETAIL_ID = "select pv_detail_id.NEXTVAL from dual";
                    for(int i=0; i<pvs.size(); i++) {
                        pstmt = conn.prepareStatement(SEQ_PVDETAIL_ID);
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) nextID = rs.getInt(1);
                        rs.close();
                        pstmt.close();

                        pstmt = conn.prepareStatement("INSERT INTO tbl_pv_detail(siteid,urlname,url_cn_name,pageview,logdate,id) VALUES (?, ?, ?, ?, ?, ?)");
                        pv = new pageview();
                        pv = (pageview)pvs.get(i);
                        System.out.println(pv.getUrl() + "---" + pv.getCnname() + "====" + pv.getPv());
                        if (pv.getUrl().length()<2000) {
                            pstmt.setInt(1,siteid);
                            pstmt.setString(2,pv.getUrl());
                            pstmt.setString(3,pv.getCnname());
                            pstmt.setInt(4,pv.getPv());
                            pvnum = pvnum + pv.getPv();
                            pstmt.setTimestamp(5,pv.getAccesstime());
                            //if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(6, nextID);
                            //}
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                    conn.commit();
                    System.out.println(logfilename + "===total pageview=" + pvnum);
                }

                fw.flush();
                fw.close();

            } else if (logtype == 4) {
                apacheExtendLogAnalyzer apacheExAnalyzer = new apacheExtendLogAnalyzer("c:\\20120301",cpool);
                apacheExAnalyzer.anaTheLog();
            } else if (logtype == 2) {
                java.util.Date thedate = new java.util.Date();
                SmartDate smartDay = new SmartDateImpl();
                thedate = smartDay.addDay(thedate,-1);
                Calendar tmpCal = Calendar.getInstance();
                tmpCal.setTime(thedate);
                String tbuf = "";
                if (logfilename == null) {
                    if ((tmpCal.get(Calendar.MONTH)+1)<10)
                        tbuf = String.valueOf(tmpCal.get(Calendar.YEAR)) + "0" + String.valueOf(tmpCal.get(Calendar.MONTH)+1);    // + String.valueOf(tmpCal.get(Calendar.DAY_OF_MONTH));
                    else
                        tbuf = String.valueOf(tmpCal.get(Calendar.YEAR)) + String.valueOf(tmpCal.get(Calendar.MONTH)+1);           // + String.valueOf(tmpCal.get(Calendar.DAY_OF_MONTH));

                    if (tmpCal.get(Calendar.DAY_OF_MONTH)<10) {
                        tbuf = tbuf + "0" + tmpCal.get(Calendar.DAY_OF_MONTH);
                    } else {
                        tbuf = tbuf + tmpCal.get(Calendar.DAY_OF_MONTH);
                    }

                    System.out.println("date=" + tbuf);
                    apacheCommonLogAnalyzer apacheCommonAnalyzer = new apacheCommonLogAnalyzer("/data/logs/"+tbuf+".log",cpool);
                    apacheCommonAnalyzer.anaTheLog();
                } else {
                    apacheCommonLogAnalyzer apacheCommonAnalyzer = new apacheCommonLogAnalyzer(logfilename,cpool);
                    apacheCommonAnalyzer.anaTheLog();
                }
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }  finally {
            if (conn != null) {
                try {
                    //cpool.freeConnection(conn);
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        /*try {
            //?24��??????????
            Thread.sleep(1000 * 60 * 60 * 24);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //}
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
                //conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:innocom", dbusername, dbpassword);
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl11g", dbusername, dbpassword);
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
