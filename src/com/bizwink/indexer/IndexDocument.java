package com.bizwink.indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.*;
import java.nio.channels.OverlappingFileLockException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.text.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.cms.util.StringUtil;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexDocument implements Runnable {
    private Thread runner;
    private IndexWriter writer;		        // new index being built
    private DBDocument dbdocument;                // create object of DBDocument for index DB
    private String indexPath,logFileString;
    private PrintWriter log;
    private String pid;
    private boolean available=true;
    PoolServer cpool;
    private String os;
    private String dbtype;
    private String language;
    private String appserver;
    public static boolean runner_flag = false;

    public IndexDocument(String indexPath,String logPath,String os,String language,String appserver,PoolServer cpool) throws IOException{
        this.indexPath = indexPath;
        this.cpool = cpool;
        this.appserver = appserver;
        this.language = language;
        this.os = os;
        this.dbtype = cpool.getType();

        try {
            if (logPath!= null) {
                int posi = logPath.lastIndexOf(File.separator);
                if (posi>-1) logPath = logPath.substring(0,posi+1);
                this.logFileString = logPath;
                log = new PrintWriter(new FileOutputStream(logFileString + "index_server.log"), true);
            }
        }
        catch (IOException e1) {
            System.err.println("Warning:Indexer could not open \""
                    + logFileString + "\" to write log to. Make sure that your Java " +
                    "process has permission to write to the file and that the directory exists."
            );
            try {
                log = new PrintWriter(new FileOutputStream("DCB_" +
                        System.currentTimeMillis() + ".log"), true
                );
            }
            catch (IOException e2) {
                throw new IOException("Can't open any log file");
            }
        }

        // Write the pid file (used to clean up dead/broken connection)
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
        java.util.Date nowc = new java.util.Date();
        pid = formatter.format(nowc);

        BufferedWriter pidout = new BufferedWriter(new FileWriter(logFileString + "pid"));
        pidout.write(pid);
        pidout.close();

        log.println("Starting Indexer:");
        log.println("-----------------------------------------");

        // Fire up the background housekeeping thread
        if (runner_flag == false) {
            runner = new Thread(this);
            runner.start();
            runner_flag = true;
        }
    }

    public void run() {
        String s_indexPath = indexPath;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();
        IArticleManager articleManager = ArticlePeer.getInstance();

        while(true) {
            String maintitle = null;
            Timestamp createdate=null;
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            List sites = null;

            try {
                sites = siteManager.getAllSiteInfo();
            } catch (SiteInfoException siteexp) {
                siteexp.printStackTrace();
            }

            if (sites != null) {
                for (int si=0; si<sites.size(); si++) {
                    int count = 0;
                    SiteInfo asite = (SiteInfo)sites.get(si);
                    //if (asite.getSiteid()!=40 && asite.getSiteid()!=199) {      //主站和新闻网站不在这里进行索引
                    if (s_indexPath.endsWith(File.separator))
                        indexPath = s_indexPath + StringUtil.replace(asite.getDomainName(),".","_");
                    else
                        indexPath = s_indexPath + File.separator + StringUtil.replace(asite.getDomainName(),".","_");


                    FSDirectory fsdir=null;
                    try {
                        File file = new File(indexPath);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        // 配置索引
                        //Path path = Paths.get(new URI(indexPath));
                        //Directory directory = new SimpleFSDirectory(path);
                        //Analyzer analyzer = new SmartChineseAnalyzer();
                        Analyzer analyzer = new IKAnalyzer();
                        Directory directory = new SimpleFSDirectory(new File(indexPath));
                        writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_45,analyzer));

                        // 删除所有document
                        //writer.deleteAll();
                    } catch (Exception ioexp) {
                        System.out.println("打开索引的写操作："+ioexp.getMessage());
                    }

                    String sqlstr = "";
                    int times = 0;
                    boolean loopflag = true;
                    while (loopflag) {
                        List artlist = new ArrayList();
                        article one = null;
                        try {
                            conn = cpool.getConnection();
                            if (cpool.getType().equalsIgnoreCase("oracle")) {
                                sqlstr = "SELECT id,columnid FROM (SELECT A.*, ROWNUM RN FROM (SELECT id,columnid FROM tbl_article where indexflag=0 and auditflag=0 and pubflag=0 and (status=1 or status=4 or status=5 or status=6) and siteid=?) A WHERE ROWNUM < 100) WHERE RN >= 0";
                                pstmt = conn.prepareStatement(sqlstr);
                                pstmt.setInt(1,asite.getSiteid());
                            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                                sqlstr = "select top 100 id,columnid,siteid from tbl_article where (id not in (select top 100 id from tbl_article order by id)) and indexflag=0 and auditflag=0 and pubflag=0 and (status=1 or status=4 or status=5 or status=6) and siteid=?";
                                pstmt = conn.prepareStatement(sqlstr);
                                pstmt.setInt(1,asite.getSiteid());
                            } else {
                                sqlstr = "select id,columnid from tbl_article where siteid=" +  asite.getSiteid() + "  and auditflag=0 and pubflag=0 and (status=1 or status=4 or status=5 or status=6) and indexflag=0 and 0 limit 100";
                                pstmt = conn.prepareStatement(sqlstr);
                            }
                            rs = pstmt.executeQuery();
                            while (rs.next()) {
                                one = new article();
                                one.setID(rs.getInt("id"));
                                artlist.add(one);
                            }
                            times = times + 1;
                        } catch (SQLException sqlexp) {
                            sqlexp.printStackTrace();
                        } finally {
                            try {
                                if (rs!=null) rs.close();
                                if (pstmt!=null) pstmt.close();
                                cpool.freeConnection(conn);
                            } catch (SQLException exp1) {}
                        }

                        if (artlist.size() > 0) {
                            for(int i=0; i<artlist.size(); i++) {
                                //根据用户需要，修改将要建立索引的子段
                                //同时需要修改DBDocument.java中的Document()方法
                                int indexflag = 0;
                                one = (article)artlist.get(i);
                                Article article = null;
                                Column column = null;
                                String article_class ="";
                                try {
                                    article = articleManager.getArticle(one.getID());
                                    column = columnManager.getColumn(article.getColumnID());
                                    article_class =columnManager.getCidpath(article.getColumnID());
                                } catch (ArticleException aexp) {
                                    System.out.println(maintitle + "获取文章内容出现错误");
                                } catch (ColumnException colexp) {
                                    System.out.println(maintitle + "获取栏目内容出现错误");
                                }

                                try {
                                    if (article != null) {
                                        if (column != null) {
                                            String dirname =column.getDirName();
                                            if (dbtype.equalsIgnoreCase("mssql")) {
                                                maintitle = StringUtil.gb2iso4View(article.getMainTitle());
                                            } else if(dbtype.equalsIgnoreCase("mysql")) {
                                                maintitle = article.getMainTitle();
                                            } else {
                                                maintitle = article.getMainTitle();
                                            }

                                            createdate=article.getCreateDate();
                                            if (createdate != null) {
                                                String dateString = formatter.format(createdate);
                                                int siteid = asite.getSiteid();
                                                if (asite != null) {
                                                    String sitename = asite.getDomainName();
                                                    writer.addDocument(dbdocument.Document(siteid,sitename,dbtype,article_class,article,dirname,dateString));
                                                    System.out.println( maintitle + " 创建时间：" +dateString);
                                                    indexflag = 1;
                                                    count = count + 1;
                                                } else {
                                                    indexflag = 3;
                                                    System.out.println(maintitle + "：文章不属于任何站点，无法进行索引");
                                                }
                                            } else {
                                                indexflag = 5;
                                                System.out.println("该篇文章没有创建时间："+one.getID());
                                            }
                                        } else {
                                            indexflag = 2;
                                            System.out.println(maintitle + "：文章不属于任何栏目，无法进行索引");
                                        }
                                    } else {
                                        System.out.println("该篇文章不存在："+one.getID());
                                    }
                                } catch (CorruptIndexException ciexp) {
                                    System.out.println(maintitle + "：索引文件被破坏，结束索引的创建");
                                    loopflag = false;
                                    si = si - 1;
                                    break;
                                } catch (IOException ioexp) {
                                    indexflag = 4;
                                    System.out.println(maintitle + "：索引读写出现错误，无法进行索引");
                                }

                                try {
                                    articleManager.updateIndexFlag(article.getID(),indexflag);
                                } catch (ArticleException exp) {
                                    System.out.println(maintitle + "修改文章的索引标识位出现错误");
                                }
                            }
                        } else {
                            loopflag = false;                              //完成一个站点的索引创建
                        }
                    }

                    //修改该站点被删文件或者被修改文件的索引
                    List del_articles = new ArrayList();
                    //获取需要修改或删除的文章ID列表
                    try {
                        conn = cpool.getConnection();
                        pstmt = conn.prepareStatement("select articleid,columnid,siteid,sitename,acttype from tbl_deleted_article where siteid=? and acttype=0");
                        pstmt.setInt(1,asite.getSiteid());
                        rs = pstmt.executeQuery();
                        article del_article = null;
                        while (rs.next()) {
                            del_article = new article();
                            del_article.setID(rs.getInt("articleid"));
                            del_article.setSiteid(rs.getInt("siteid"));
                            del_article.setColumnid(rs.getInt("columnid"));
                            del_article.setActtype(rs.getInt("acttype"));
                            del_article.setSitename(rs.getString("sitename"));
                            del_articles.add(del_article);
                        }
                        rs.close();
                        pstmt.close();


                        for(int i=0; i<del_articles.size(); i++) {
                            article thearticle = (article)del_articles.get(i);
                            //从索引中删除索引内容
                            try{
                                Term term = new Term("id", String.valueOf(thearticle.getID()));
                                if (writer!=null) writer.deleteDocuments(term);
                            } catch (OverlappingFileLockException exp11) {
                                System.out.println("ExceptionFilePath:" + indexPath);
                            }

                            //如果是修改了文章，需要将修改后的文章内容加入到索引
                            if (thearticle.getActtype()==1) {
                                Article update_article = articleManager.getArticle(thearticle.getID());
                                Column column = columnManager.getColumn(update_article.getColumnID());
                                if (update_article != null) {
                                    if (column != null) {
                                        String dirname =column.getDirName();
                                        String article_class =columnManager.getCidpath(update_article.getColumnID());
                                        if (dbtype.equalsIgnoreCase("mssql")) {
                                            maintitle = StringUtil.gb2iso4View(update_article.getMainTitle());
                                        } else if(dbtype.equalsIgnoreCase("mysql")) {
                                            maintitle = update_article.getMainTitle();
                                        } else {
                                            maintitle = update_article.getMainTitle();
                                        }

                                        createdate=update_article.getCreateDate();
                                        if (createdate != null) {
                                            String dateString = formatter.format(createdate);
                                            int siteid = asite.getSiteid();
                                            if (asite != null) {
                                                String sitename = asite.getDomainName();
                                                writer.addDocument(dbdocument.Document(siteid,sitename,dbtype,article_class,update_article,dirname,dateString));
                                                System.out.println( maintitle + " 修改时间：" +dateString);
                                                articleManager.updateIndexFlag(update_article.getID(),1);
                                                count = count + 1;
                                            } else {
                                                articleManager.updateIndexFlag(update_article.getID(),3);
                                                System.out.println(maintitle + "：文章不属于任何站点，无法进行索引");
                                            }
                                        }
                                    } else {
                                        articleManager.updateIndexFlag(update_article.getID(),2);
                                        System.out.println(maintitle + "：文章不属于任何栏目，无法进行索引");
                                    }
                                }
                            }

                            pstmt = conn.prepareStatement("delete from tbl_deleted_article where articleid=? and acttype=0");
                            pstmt.setInt(1,thearticle.getID());
                            pstmt.executeUpdate();
                            pstmt.close();
                            conn.commit();
                        }
                    } catch (SQLException sqlexp) {
                        sqlexp.printStackTrace();
                    } catch (IOException ioexp) {
                        ioexp.printStackTrace();
                    } catch(ArticleException articleExp) {
                        articleExp.printStackTrace();
                    } catch (ColumnException colExp) {
                        colExp.printStackTrace();
                    } finally {
                        try {
                            if (rs!=null) rs.close();
                            if (pstmt!=null) pstmt.close();
                            cpool.freeConnection(conn);
                        } catch (SQLException exp1) {}
                        cpool.freeConnection(conn);
                    }

                    try {
                        if (writer!=null)  writer.close();
                        if (count > 0)  System.out.println(asite.getDomainName() + " 增加 "+count+" 文章到索引文件");
                    }catch (IOException exp) {
                        System.out.println("关闭索引时出现IO问题！！！");
                    }
                    // }
                }
            }

            //系统睡眠半个小时，然后开始重新扫描文章，对新文章建立索引
            try {
                System.out.println("系统睡眠10分钟，重新开始检查是否有文章需要进行索引！！！");
                Thread.sleep(1000*10*60);
            } catch(InterruptedException e) {
                System.out.println("抛出异常中断问题！！！");
            }

        }
    }

    public static String getBigString(String type, ResultSet rs, String fieldName) throws Exception
    {
        String content = null;
        if (type.equalsIgnoreCase("oracle"))
            content = getOracleBigString(rs, fieldName);
        else if (type.equalsIgnoreCase("mssql") || type.equalsIgnoreCase("sybase"))
            content = getSQLServerBigString(rs, fieldName);
        else
            content = getOracleBigString(rs, fieldName);

        return content;
    }

    private static String getSQLServerBigString(ResultSet rs, String fieldName) throws Exception
    {
        return rs.getString(fieldName);
    }

    private static String getOracleBigString(ResultSet rs, String fieldName) throws Exception
    {
        String content = "";
        if (rs.getCharacterStream(fieldName) != null)
        {
            BufferedReader is = new BufferedReader(rs.getCharacterStream(fieldName));
            char[] buffer = new char[1000];
            int length = 0;
            while ((length = is.read(buffer)) != -1)
            {
                String str = new String(buffer,0,length);
                content = content + str;
            }
            is.close();
        }
        return content;
    }
}
