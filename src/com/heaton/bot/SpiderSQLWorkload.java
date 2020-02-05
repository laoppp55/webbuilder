package com.heaton.bot;

import java.util.*;
import java.sql.*;
import java.net.*;

/**
 * This class uses a JDBC database
 * to store a spider workload.
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class SpiderSQLWorkload implements IWorkloadStorable {

    String dbdriver;
    String dburl;
    String dbuser;
    String dbpasswd;

    /**
     * Create a new SQL workload store and
     * connect to a database.
     *
     * @param : driver The JDBC driver to use.
     * @param : source The driver source name.
     * @exception java.sql.SQLException
     * @exception java.lang.ClassNotFoundException
     */
    public SpiderSQLWorkload() {

    }

    public SpiderSQLWorkload(String driver, String source,String dbuser,String dbpassword) throws SQLException, ClassNotFoundException
    {
        this.dbdriver = driver;
        this.dburl = source;
        this.dbuser = dbuser;
        this.dbpasswd = dbpassword;
    }

    /**
     * Call this method to request a URL
     * to process. This method will return
     * a WAITING URL and mark it as RUNNING.
     *
     * @return The URL that was assigned.
     */

    synchronized public jobinfo assignWorkload(int siteid)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        String url = null;
        int id = 0;
        jobinfo ji = null;

        String SQL_GET_JOB = "SELECT id,url,urltitle FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_workload_" + siteid +
                " where status='W' order by id asc) A WHERE ROWNUM<=1) WHERE RN >=0";

        String SQL_SET_JOB_STATUS = "UPDATE tbl_Workload_" + siteid +" SET Status = ? WHERE id = ?";

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);

            //获取一个作业
            pstmt = conn.prepareStatement(SQL_GET_JOB);
            rs = pstmt.executeQuery();
            if (!rs.next())
                return null;
            else {
                ji = new jobinfo();
                ji.setID(rs.getInt("id"));
                ji.setJoburl(rs.getString("URL"));
                ji.setTitle(rs.getString("urltitle"));
            }
            rs.close();
            pstmt.close();

            //修改作业的状态为正在运行状态
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_SET_JOB_STATUS);
            pstmt.setString(1,"R");
            pstmt.setInt(2, ji.getID());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e);
        } catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }
            rs = null;
            pstmt = null;
            conn = null;
        }

        return ji;
    }

    /*
    *获取当前的站点ID
    */
    private static final String SQL_GET_SITEINFO = "SELECT id FROM sp_basic_attributes WHERE Status = 'W'";

    synchronized public int getSiteID()
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int siteid = 0;

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);

            //获取一个作业
            pstmt = conn.prepareStatement(SQL_GET_SITEINFO);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                siteid = rs.getInt("id");
            }
            rs.close();
            pstmt.close();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e);
        } catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            rs = null;
            pstmt = null;
            conn = null;
        }

        return siteid;
    }

    synchronized public String[] getKeywords(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String[] keywords = null;
        String SQL_GET_KEYWORDS = "SELECT tkeyword,bkeyword,tbrelation from sp_keyword_rules where starturlid = ?";

        try {
            conn = Server.createConnection();

            pstmt = conn.prepareStatement(SQL_GET_KEYWORDS);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                keywords = new String[3];
                keywords[0] = String.valueOf(rs.getInt(3));
                keywords[1] = rs.getString(1);
                keywords[2] = rs.getString(2);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            Log.logException("SQL Error: ", e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return keywords;
    }

    /*
    *获取当前的站点ID
    */

    private static final String SQL_GET_SITESID = "SELECT id,sitename,starturl,classid,matchurl,status,starttag,endtag," +
            "loginurl,loginuser,loginpasswd,posturl,postdata FROM sp_basic_attributes where status='W'  order by id asc";

    synchronized public List getSites()
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;

        List sites = new ArrayList();
        siteinfo sf = new siteinfo();

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);

            //获取一个作业
            pstmt = conn.prepareStatement(SQL_GET_SITESID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sf = load_for_id(rs);
                sites.add(sf);
            }
            rs.close();
            pstmt.close();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e);
        } catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            rs = null;
            pstmt = null;
            conn = null;
        }

        return sites;
    }

    private static final String SQL_GET_SITE = "SELECT id,siteid,sitename,starturl,status," +
            "posturl,postdata FROM sp_basic_attributes where status='W'  order by id asc";

    private static final String SQL_GET_SITE_S_E_TAGS = "SELECT id,st,et FROM sp_special_code where starturlid=?";

    private static final String SQL_GET_SITE_MATCHURLS = "SELECT id,matchurl FROM sp_match_url where starturlid=?";

    private static final String SQL_GET_SITE_COLUMNS = "SELECT id,classid FROM sp_basic_columns where basicid=?";

    private static final String SQL_UPDATE_SITEINFO = "update sp_basic_attributes set Status = ? where id = ?";

    synchronized public siteinfo getSite()
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        siteinfo sf = null;
        List tags = new ArrayList();
        List matchurls = new ArrayList();
        List columns = new ArrayList();

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);

            //获取一个开始作业
            pstmt = conn.prepareStatement(SQL_GET_SITE);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sf = new siteinfo();
                sf = load_for_id(rs);
            }
            rs.close();
            pstmt.close();

            //修改开始作业的状态
            if (sf != null) {
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_SITEINFO);
                pstmt.setString(1,"R");
                pstmt.setInt(2,sf.getID());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();

                //获取该站点的匹配标记
                pstmt = conn.prepareStatement(SQL_GET_SITE_S_E_TAGS);
                pstmt.setInt(1,sf.getID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    StartEndTag tag = new  StartEndTag();
                    tag.setStarttag(rs.getString("st"));
                    tag.setEndtag(rs.getString("et"));
                    tags.add(tag);
                }
                rs.close();
                pstmt.close();
                sf.setTags(tags);

                //获取该站点的匹配URL
                pstmt = conn.prepareStatement(SQL_GET_SITE_MATCHURLS);
                pstmt.setInt(1,sf.getID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    matchurls.add(rs.getString("matchurl"));
                }
                rs.close();
                pstmt.close();
                sf.setMatchurls(matchurls);

                //获取信息存放的栏目，最好根据标题进行全文检索决定信息存放在那个具体栏目中
                pstmt = conn.prepareStatement(SQL_GET_SITE_COLUMNS);
                pstmt.setInt(1,sf.getID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    columns.add(rs.getInt("classid"));
                }
                rs.close();
                pstmt.close();
                sf.setColumns(columns);
            }
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e);
        } catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            rs = null;
            pstmt = null;
            conn = null;
        }

        return sf;
    }

    //修改站点的状态
    synchronized public int updateSiteStatus(int siteid,String status)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int retcode = 0;

        //System.out.println(siteid + "===" + status);

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(SQL_UPDATE_SITEINFO);
            pstmt.setString(1,status);
            pstmt.setInt(2,siteid);
            pstmt.executeUpdate();
            pstmt.close();

            conn.commit();
        } catch ( SQLException e ) {
            retcode = -1;
            Log.logException("SQL Error: ",e);
        } catch (ClassNotFoundException exp) {
            retcode = -2;
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            pstmt = null;
            conn = null;
        }

        return retcode;
    }

    private static final String SQL_GET_PROXY = "SELECT id,proxyname,proxyport from sp_global";

    synchronized public proxySetup getProxySetting()
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;

        List sites = new ArrayList();
        proxySetup proxy = new proxySetup();

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);

            //获取一个作业
            pstmt = conn.prepareStatement(SQL_GET_PROXY);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                proxy = load_for_proxy(rs);
            }
            rs.close();
            pstmt.close();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e);
        } catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            rs = null;
            pstmt = null;
            conn = null;
        }

        return proxy;
    }

    /**
     * Add a new URL to the workload, and
     * assign it a status of WAITING.
     *
     * @param url The URL to be added.
     */
    synchronized public void addWorkload(String url,int siteid)
    {
        if ( getURLStatus(url,siteid)!=UNKNOWN )
            return;
        setStatus(url,WAITING,siteid);
    }

    /**
     * Called to mark this URL as either
     * COMPLETE or ERROR.
     *
     * @param url The URL to complete.
     * @param error true - assign this workload a status of ERROR.
     * false - assign this workload a status of COMPLETE.
     */
    synchronized public void completeWorkload(String url,int urlid,boolean error,int siteid,int urltype)
    {
        if ( error )
            setCompleteStatus(url,urlid,ERROR,siteid,urltype);
        else
            setCompleteStatus(url,urlid,COMPLETE,siteid,urltype);
    }

    /**
     * This is an internal method used to set the status
     * of a given URL. This method will create a record
     * for the URL of one does not currently exist.
     *
     * @param : url The URL to set the status for.
     * @param : status What status to set.
     */
    //prepSetStatus1 connection.prepareStatement(

    synchronized public void batchAddWorkload(List urls,int siteid)
    {
        Connection conn = null;
        PreparedStatement pstmt=null,pstmt1=null;
        ResultSet rs = null;
        URL url = null;
        URLInfo urlinfo = new URLInfo();
        int count = 0;
        int totalcount = 0;

        Timestamp datetime = new Timestamp(System.currentTimeMillis());
        int year = 1900 + datetime.getYear();
        int month = datetime.getMonth() + 1;
        int day = datetime.getDate();
        String thedate = "";

        if (month<10)
            thedate = thedate + year +"-0" + month;
        else
            thedate = thedate + year +"-" + month;

        if (day < 10)
            thedate = thedate + "-0" + day;
        else
            thedate = thedate + "-" + day;

        String SQL_GET_TOTAL_NUMBER =  "SELECT count(*) as qty FROM tbl_Workload_" + siteid + " WHERE TO_CHAR(thedate,'yyyy-mm-dd') = ?";
        String SQL_GET_JOB_NUMBER =  "SELECT count(*) as qty FROM tbl_Workload_" + siteid +" WHERE URL = ?";
        String SEQ_NEXT_SPIDER_ID = "select spider_id.NEXTVAL from dual";
        String SQL_ADD_JOB = "INSERT INTO tbl_Workload_" + siteid + "(id,URL,urltitle,Status,thedate) VALUES (?, ?, ?, ?, ?)";

        try {
            // System.out.println("开始加入地址信息");
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            conn.setAutoCommit(false);

            for(int i=0; i<urls.size(); i++) {
                count = 0;
                urlinfo = (URLInfo)urls.get(i);

                //获取作业的总数量
                pstmt1 = conn.prepareStatement(SQL_GET_TOTAL_NUMBER);
                pstmt1.setString(1,thedate);
                rs = pstmt1.executeQuery();
                if (rs.next()) totalcount = rs.getInt("qty");
                rs.close();
                pstmt1.close();

                //获取作业的数量
                pstmt1 = conn.prepareStatement(SQL_GET_JOB_NUMBER);
                pstmt1.setString(1,urlinfo.getURLInfo().toString());
                rs = pstmt1.executeQuery();
                if (rs.next()) count = rs.getInt("qty");
                rs.close();
                pstmt1.close();

                //System.out.println(url.toString() + "(" + count + ")");
                int nextID = 0;
                if ( count<1 && totalcount<5000) {
                    //if ( count<1) {
                    pstmt = conn.prepareStatement(SEQ_NEXT_SPIDER_ID);
                    rs = pstmt.executeQuery();
                    if (rs.next()) nextID = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_ADD_JOB);
                    pstmt.setInt(1,nextID);
                    pstmt.setString(2,urlinfo.getURLInfo().toString());
                    pstmt.setString(3,urlinfo.getTitle());
                    pstmt.setString(4,"W");
                    pstmt.setTimestamp(5,datetime);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        }finally {
            try {
                if ( conn!=null )
                    conn.close();
            } catch ( Exception e ) {}
            rs=null;
            pstmt = null;
            pstmt1= null;
            conn = null;
        }
    }

    /**
     * This is an internal method used to set the status
     * of a given URL. This method will create a record
     * for the URL of one does not currently exist.
     *
     * @param url The URL to set the status for.
     * @param status What status to set.
     */
    protected void setStatus(String url,char status,int siteid)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int count = 0;

        String SQL_GET_JOB_NUMBER =  "SELECT count(*) as qty FROM tbl_Workload_" + siteid +" WHERE URL = ?";
        String SEQ_NEXT_SPIDER_ID = "select spider_id.NEXTVAL from dual";
        String SQL_ADD_JOB = "INSERT INTO tbl_Workload_" + siteid + "(id,URL,Status) VALUES (?,?,?)";

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            conn.setAutoCommit(false);
            //获取作业的数量
            pstmt = conn.prepareStatement(SQL_GET_JOB_NUMBER);
            pstmt.setString(1,url.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt("qty");
            rs.close();
            pstmt.close();

            // Create one
            int nextID = 0;
            if ( count<1 ) {
                pstmt = conn.prepareStatement(SEQ_NEXT_SPIDER_ID);
                rs = pstmt.executeQuery();
                if (rs.next()) nextID = rs.getInt(1);
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_ADD_JOB);
                pstmt.setInt(1,nextID);
                pstmt.setString(2,url.toString());
                pstmt.setString(3,"W");
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        }
        finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }
            rs = null;
            pstmt = null;
            conn = null;
        }
    }

    protected void setCompleteStatus(String url,int urlid,char status,int siteid,int urltype)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        //ResultSet rs = null;

        String SQL_SET_JOB_STATUS = "UPDATE tbl_Workload_" + siteid +" SET Status = ?,urltype=? WHERE id = ?";

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            conn.setAutoCommit(false);

            //System.out.println(url + "===" + (new Character(status)).toString());

            pstmt = conn.prepareStatement(SQL_SET_JOB_STATUS);
            pstmt.setString(1,(new Character(status)).toString());
            pstmt.setInt(2,urltype);
            pstmt.setInt(3,urlid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            pstmt = null;
            conn = null;
        }
    }

    public void setURL_LastUpdate(int urlid,int year,int month,int day,int siteid)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        //ResultSet rs = null;
        //Timestamp lu = new Timestamp(new java.sql.Date(year,month,day).getTime());
        Timestamp lu = new Timestamp(year-1900,month-1,day,0,0,0,0);

        String SQL_SET_URL_LASTUPDATE = "UPDATE tbl_Workload_" + siteid + " SET lastmodified = ? WHERE id = ?";

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            conn.setAutoCommit(false);

            //System.out.println(url + "===" + (new Character(status)).toString());

            pstmt = conn.prepareStatement(SQL_SET_URL_LASTUPDATE);
            pstmt.setTimestamp(1,lu);
            pstmt.setInt(2,urlid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }

            pstmt = null;
            conn = null;
        }
    }

    /**
     * Get the status of a URL.
     *
     * @param url Returns either RUNNING, ERROR
     * WAITING, or COMPLETE. If the URL
     * does not exist in the database,
     * the value of UNKNOWN is returned.
     * @return Returns either RUNNING,ERROR,
     * WAITING,COMPLETE or UNKNOWN.
     */

    synchronized public char getURLStatus(String url,int siteid)
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        char status = UNKNOWN;
        String SQL_GET_JOB_STATUS = "SELECT Status FROM tbl_Workload_" + siteid + " WHERE URL = ?";
        // System.out.println(SQL_GET_JOB_STATUS);

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);

            pstmt = conn.prepareStatement(SQL_GET_JOB_STATUS);
            pstmt.setString(1,url);
            rs = pstmt.executeQuery();
            if (rs.next()) status = rs.getString("Status").charAt(0);
            pstmt.close();
            rs.close();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: ",e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {
            }
            rs = null;
            pstmt = null;
            conn = null;
        }

        return status;
    }

    /**
     * Clear the contents of the workload store.
     */
    private static final String SQL_GET_SITES = "SELECT id,siteid,sitename,starturl,status,posturl,postdata FROM sp_basic_attributes";

    private static final String SQL_UPDATE_SITES = "update sp_basic_attributes set Status = ?";

    synchronized public void clear()
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int sid = 0;

        List sites = new ArrayList();
        siteinfo sf = new siteinfo();
        String SQL_DELETE_JOBS = "DELETE FROM tbl_Workload";

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            pstmt = conn.prepareStatement(SQL_GET_SITES);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sf = load_for_id(rs);
                sites.add(sf);
            }
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            for (int i=0;i<sites.size(); i++) {
                sf = null;
                sf = (siteinfo)sites.get(i);
                sid = sf.getID();
                SQL_DELETE_JOBS = "DELETE FROM tbl_Workload_" + sf.getID() + " WHERE urltype <> 1";
                //System.out.println(SQL_DELETE_JOBS);
                pstmt = conn.prepareStatement(SQL_DELETE_JOBS);
                pstmt.executeUpdate();
                pstmt.close();
            }

            pstmt = conn.prepareStatement(SQL_UPDATE_SITES);
            pstmt.setString(1,"W");
            pstmt.executeUpdate();
            pstmt.close();

            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: " + sid,e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            sites = null;
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {}

            rs = null;
            pstmt = null;
            conn = null;
        }
    }

    synchronized public void clearIndexURL()
    {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int sid = 0;

        List sites = new ArrayList();
        siteinfo sf = new siteinfo();
        String SQL_DELETE_JOBS = "DELETE FROM tbl_Workload";

        try {
            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl,dbuser,dbpasswd);
            pstmt = conn.prepareStatement(SQL_GET_SITES);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                sf = load_for_id(rs);
                sites.add(sf);
            }
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            for (int i=0;i<sites.size(); i++) {
                sf = null;
                sf = (siteinfo)sites.get(i);
                sid = sf.getID();
                SQL_DELETE_JOBS = "DELETE FROM tbl_Workload WHERE urltype <> 1";
                pstmt = conn.prepareStatement(SQL_DELETE_JOBS);
                pstmt.executeUpdate();
                pstmt.close();
            }

            pstmt = conn.prepareStatement(SQL_UPDATE_SITES);
            pstmt.setString(1,"W");
            pstmt.executeUpdate();
            pstmt.close();

            conn.commit();
        } catch ( SQLException e ) {
            Log.logException("SQL Error: " + sid,e );
        }  catch (ClassNotFoundException exp) {
            Log.logException("驱动程序没有找到",exp);
        } finally {
            sites = null;
            try {
                if ( conn!=null ) conn.close();
            } catch ( Exception e ) {}

            rs = null;
            pstmt = null;
            conn = null;
        }
    }

    siteinfo load(ResultSet rs) throws SQLException
    {
        siteinfo article = new siteinfo();
        try
        {
            article.setID(rs.getInt("ID"));
            article.setSitename(rs.getString("sitename"));
            article.setStarturl(rs.getString("starturl"));
            article.setGetdepth(rs.getInt("getdepath"));
            article.setChadepth(rs.getInt("chadepath"));
            article.setUrlnumber(rs.getInt("urlnumber"));
            article.setMatchurl(rs.getString("matchurl"));
            article.setNomatchurl(rs.getString("nomatchurl"));
            article.setFlashcookieurl(rs.getString("flashcookieurl"));
            article.setStarttag(rs.getString("starttag"));
            article.setEndtag(rs.getString("endtag"));
            article.setStatus(rs.getString("status"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return article;
    }

    siteinfo load_for_id(ResultSet rs) throws SQLException
    {
        siteinfo article = new siteinfo();
        try
        {
            article.setID(rs.getInt("ID"));
            article.setSiteid(rs.getInt("siteid"));
            article.setStarturl(rs.getString("starturl"));
            article.setSitename(rs.getString("sitename"));
            article.setStatus(rs.getString("status"));
            article.setPostURL(rs.getString("posturl"));
            article.setPostData(rs.getString("postdata"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return article;
    }

    proxySetup load_for_proxy(ResultSet rs) throws SQLException
    {
        proxySetup proxy = new proxySetup();
        try
        {
            proxy.setID(rs.getInt("ID"));
            proxy.setProxyHost(rs.getString("proxyname"));
            proxy.setProxyPort(rs.getString("proxyport"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return proxy;
    }
}