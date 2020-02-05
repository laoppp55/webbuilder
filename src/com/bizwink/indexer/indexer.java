package com.bizwink.indexer;

import java.io.*;
import java.net.URI;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.bizwink.cms.news.*;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.cms.util.StringUtil;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class indexer {
    private DBDocument dbdocument; // create object of DBDocument for index DB

    public indexer() throws IOException {

    }

    public static void main(String[] args) {

        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");

        String indexPath = props.getProperty("main.indexPath");
        String dbtype = props.getProperty("main.db.type");
        String dbdriver = props.getProperty("main.db.driver");
        String dburl = props.getProperty("main.db.url");
        String username = props.getProperty("main.db.username");
        String passwd = props.getProperty("main.db.password");

        if(!indexPath.endsWith("\\") || !indexPath.endsWith("/"))
            indexPath = indexPath + File.separator;

        try {
            indexer myindexer = new indexer();
            myindexer.getRecordForCreateIndexFromDababase(dbdriver, dburl, username, passwd, dbtype, indexPath);
        }
        catch (IOException ioexp) {
            ioexp.printStackTrace();
        }
    }

    private static Connection createConnection(String dbdriver, String dburl, String dbusername, String dbpassword, String type) {
        Connection conn = null;
        if (type.equals("oracle")) {
            try {
                Class.forName(dbdriver);
                java.util.Properties prop = new java.util.Properties();
                prop.put("user", dbusername);
                prop.put("password", dbpassword);
                conn = DriverManager.getConnection(dburl, prop);
            }
            catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
            catch (SQLException sqlexp) {
                sqlexp.printStackTrace();
            }
        } else if (type.equals("mssql") || type.equals("sybase")) {
            try {
                Class.forName(dbdriver);
                java.util.Properties prop = new java.util.Properties();
                prop.put("user", dbusername);
                prop.put("password", dbpassword);
                prop.put("weblogic.codeset", "GBK");
                conn = DriverManager.getConnection(dburl, prop);
            }
            catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            }
            catch (SQLException sqlexp) {
                sqlexp.printStackTrace();
            }
        }
        return conn;
    }

    public void getRecordForCreateIndexFromDababase(String dbdriver, String dburl, String dbusername, String dbpassword, String dbtype, String indexPath) {
        Connection conn = null;
        int count = 0;
        IndexWriter writer=null; // new index being built

        int articleid=0;
        int sortid = 0;
        String maintitle = null;
        String keyword = null;
        String summary = null;
        String content = null;
        Date start = new Date();
        String lastupdate = null;
        long publishtime = 0l;
        String filename = null;
        String dirname = null;
        Timestamp createdate=null;

        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
        try {
            String s_indexPath = indexPath;
            String totalIndexPath = indexPath;
            List sites = siteManager.getAllSiteInfo();
            for (int si=0; si<sites.size(); si++) {
                SiteInfo asite = (SiteInfo)sites.get(si);
                if (s_indexPath.endsWith(File.separator))
                    indexPath = s_indexPath + StringUtil.replace(asite.getDomainName(),".","_");
                else
                    indexPath = s_indexPath + File.separator + StringUtil.replace(asite.getDomainName(),".","_");
                //System.out.println("indexPath = " + indexPath);
                try {
                    File file = new File(indexPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    //Path path = Paths.get(new URI(indexPath));
                    //Directory directory = new SimpleFSDirectory(path);
                    Directory directory = new SimpleFSDirectory(new File(indexPath));
                    //Analyzer analyzer = new SmartChineseAnalyzer();
                    Analyzer analyzer = new IKAnalyzer();
                    // 配置索引
                    writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_45,analyzer));
                } catch (Exception ioexp) {
                    ioexp.printStackTrace();
                }

                if (writer != null) {
                    IColumnManager columnManager = ColumnPeer.getInstance();
                    IArticleManager articleManager = ArticlePeer.getInstance();

                    String sqlstr = "select id,columnid from tbl_article where indexflag=0 and columnid in (select id from tbl_column where siteid=?)";

                    PreparedStatement pstmt = null;
                    ResultSet rs = null;

                    try {
                        conn = createConnection(dbdriver, dburl, dbusername, dbpassword, dbtype);
                        pstmt = conn.prepareStatement(sqlstr);
                        pstmt.setInt(1,asite.getSiteid());
                        rs = pstmt.executeQuery();
                        conn.setAutoCommit(true);
                        List artlist = new ArrayList();
                        article one = null;
                        while (rs.next()) {
                            one = new article();
                            one.setID(rs.getInt("id"));
                            artlist.add(one);
                        }
                        rs.close();
                        pstmt.close();

                        for(int i=0; i<artlist.size(); i++) {
                            //根据用户需要，修改将要建立索引的子段
                            //同时需要修改DBDocument.java中的Document()方法
                            one = (article)artlist.get(i);

                            Article article = articleManager.getArticle(one.getID());
                            Column column = columnManager.getColumn(article.getColumnID());
                            String article_class = columnManager.getCidpath(article.getColumnID());
                            System.out.println(article.getMainTitle() + "======" + column.getCName());
                            if (article != null && column != null) {
                                articleid = article.getID();
                                content = article.getContent();
                                maintitle = article.getMainTitle();
                                summary = article.getSummary();
                                keyword = article.getKeyword();
                                lastupdate = article.getLastUpdated().toString();
                                publishtime = article.getPublishTime().getTime();
                                filename = article.getFileName();

                                createdate=article.getCreateDate();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                String dateString = formatter.format(createdate);

                                dirname = column.getDirName();
                                int siteid = asite.getSiteid();
                                int columnid = column.getID();
                                if (asite != null) {
                                    String sitename = asite.getDomainName();
                                    writer.addDocument(dbdocument.Document(siteid,sitename,dbtype,article_class,article,dirname,dateString));
                                }
                                System.out.println( maintitle+" 创建时间 createdate="+dateString);
                                articleManager.updateIndexFlag(articleid,1);
                                count = count + 1;
                            }
                        }
                        conn.close();
                        //writer.optimize();
                        writer.close();

                    } catch (SQLException sqlexp) {
                        sqlexp.printStackTrace();
                    } catch (ArticleException aexp) {

                    } catch (ColumnException colexp) {

                    } catch (CorruptIndexException ciexp) {

                    } catch (IOException ioexp) {

                    }
                }
            }
        } catch (SiteInfoException sexp) {
            sexp.printStackTrace();
        }

        if (count > 0)
            System.out.println(" 增加 "+count+" 文章到索引文件");
        else
            System.out.println(" 没有文章被加入到索引文件");
    }
}
