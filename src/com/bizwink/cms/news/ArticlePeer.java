package com.bizwink.cms.news;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.sql.Date;

import com.bizwink.cms.audit.AuditPeer;
import com.bizwink.cms.audit.IAuditManager;
import com.bizwink.cms.security.IUserManager;
import com.bizwink.cms.security.User;
import com.bizwink.cms.security.UserPeer;
import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.PublishQueuePeer;
import com.bizwink.upload.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.publishx.Publish;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.sitesetting.*;
import com.heaton.bot.siteinfo;


public class ArticlePeer implements IArticleManager {
    PoolServer cpool;

    public ArticlePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IArticleManager getInstance() {
        return CmsServer.getInstance().getFactory().getArticleManager();
    }

    private static final String SQL_GETARTICLE =
            "SELECT  maintitle,vicetitle,source,summary,keyword,content,author,emptycontentflag,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor,dirname," +
                    "filename,publishtime,RelatedArtID,saleprice,vipprice,inprice,marketprice,score,voucher,Brand,pic,bigpic,Weight,stocknum,ViceDocLevel," +
                    "referID,modelID,articlepic,urltype,defineurl,beidate,salesnum,notearticleid,mediafile,processofaudit,indexnum,siteid FROM tbl_article WHERE id = ?";

    //从文章数据库中获取一篇文章
    public Article getArticle(int ID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Article article = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLE);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return article;
    }

    public int getArticleProcessOfAudit(int ID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int processofaudit = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select processofaudit from tbl_article where id=?");
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next())  processofaudit = rs.getInt("processofaudit");
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return processofaudit;
    }

    public int getArticleColumnid(int ID, String editor) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int columnid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLE);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                columnid = rs.getInt("columnid");
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return columnid;
    }

    public Article getArticle(int ID, String editor) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Article article = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLE);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = load(rs);
            }
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_article set lockstatus=1,lockeditor='" + editor + "' where id=" + ID);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return article;
    }

    private static final String SQL_GetArticleByWeight =
            "SELECT maintitle,vicetitle,source,summary,keyword,author,emptycontentflag,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor,dirname," +
                    "filename,publishtime,RelatedArtID,urltype,defineurl,notearticleid,siteid FROM tbl_article WHERE columnID = ? and doclevel = ? and status = 1";

    public Article getArticleByWeight(int columnID, int weight, int type) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GetArticleByWeight);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, weight);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = loadNoContent(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return article;
    }

    private static final String SQL_GET_ONE_ARTICLE_RELATED_ARTICLES_LIST =
            "SELECT ID,cname,pageid,pagetype,contenttype,filename,summary,editor,createdate FROM tbl_relatedartids where pageid = ? and pagetype=?";

    //用于选择某篇文章的相关性文章或相关栏目  Peter Song 2006.3.4
    public List getOneArticleRelatedArticles(int pageid, int pagetype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ONE_ARTICLE_RELATED_ARTICLES_LIST);
            pstmt.setInt(1, pageid);
            pstmt.setInt(2, pagetype);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                relatedArticle article = loadRelatedArticle(rs);
                list.add(article);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_ONE_ARTICLE_RELATED_ARTICLES =
            "SELECT articleid,columnid,columnname,title FROM tbl_refers_article where articleid = ?";

    //用于选择某篇文章被推荐的栏目
    public List getRelatedArticles(int articleID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ONE_ARTICLE_RELATED_ARTICLES);
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                relatedArticle article = new relatedArticle();
                article.setJointedID(rs.getInt("columnid"));
                article.setChineseName(rs.getString("columnname"));
                article.setTitle(rs.getString("title"));
                list.add(article);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static final String SQL_GETARTICLE2 =
            "SELECT maintitle,vicetitle,source,summary,keyword,author,emptycontentflag,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor,dirname," +
                    "filename,publishtime,RelatedArtID,urltype,defineurl,notearticleid,siteid FROM tbl_article WHERE columnID = ? and sortid = ? and status = 1";

    public Article getArticle(int columnID, int sortID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLE2);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, sortID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = loadNoContent(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return article;
    }

    private static final String SQL_GETARTICLE_IN_NUM =
            "SELECT id,emptycontentflag,filename FROM tbl_article WHERE columnID = ? and status = 1";

    public Article getArticleInNum(int columnID, int artNum) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLE_IN_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < artNum - 1; i++) {
                rs.next();
            }

            if (rs.next()) {
                article = new Article();
                article.setID(rs.getInt(1));
                article.setNullContent(rs.getInt(2));
                article.setFileName(rs.getString(3));
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return article;
    }

    private static final String SQL_GET_ARTICLEVERSION = "SELECT articleversion FROM tbl_article WHERE ID = ?";

    public String getArticleVersion(int articleID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String version = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLEVERSION);
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                version = rs.getString(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return version;
    }

    public Article getNextArticle(int articleID, int columnID, int siteID, int type) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article = null;

        String SQL_GET_ARTICLE_IN_COLUMN;

        if (type == 1)      //下一篇
        {
            SQL_GET_ARTICLE_IN_COLUMN =
                    "SELECT ID,MainTitle,ColumnID,DirName,urltype,defineurl,createdate FROM TBL_Article WHERE ID > ? AND " +
                            "(Status = 1 OR Status = 2) AND AuditFlag = 0 AND ColumnID = ? ORDER BY ID";
        } else                //上一篇
        {
            SQL_GET_ARTICLE_IN_COLUMN =
                    "SELECT ID,MainTitle,ColumnID,DirName,urltype,defineurl,createdate  FROM TBL_Article WHERE ID < ? AND " +
                            "(Status = 1 OR Status = 2) AND AuditFlag = 0 AND ColumnID = ? ORDER BY ID DESC";
        }

        try {
            conn = cpool.getConnection();

            //从当前栏目寻找
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_IN_COLUMN);
            pstmt.setInt(1, articleID);
            pstmt.setInt(2, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("ID"));
                article.setMainTitle(rs.getString("MainTitle"));
                article.setColumnID(rs.getInt("ColumnID"));
                article.setDirName(rs.getString("Dirname"));
                article.setUrltype(rs.getInt("urltype"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setCreateDate(rs.getTimestamp("createDate"));
            }
            rs.close();
            pstmt.close();

            if (article == null) {
                //找第一篇
                String sql = "select ID,MainTitle,ColumnID,DirName,urltype,defineurl,createdate  FROM TBL_Article WHERE " +
                        "(Status = 1 OR Status = 2) AND AuditFlag = 0 AND ColumnID = ? order by id";
                if (type == 0) sql += " desc";

                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, columnID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    article = new Article();
                    article.setID(rs.getInt("ID"));
                    article.setMainTitle(rs.getString("MainTitle"));
                    article.setColumnID(rs.getInt("ColumnID"));
                    article.setDirName(rs.getString("Dirname"));
                    article.setUrltype(rs.getInt("urltype"));
                    article.setOtherurl(rs.getString("defineurl"));
                    article.setCreateDate(rs.getTimestamp("createDate"));
                }
                rs.close();
                pstmt.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {

                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return article;
    }

    private static final String SQL_GETHeadLineARTICLE =
            "SELECT maintitle,editor,id,columnid,sortid,doclevel,emptycontentflag,pubflag,auditflag,auditor,filename," +
                    "publishtime,urltype,defineurl,siteid FROM tbl_article " +
                    "WHERE (columnID = ?)and(status = 0)and(doclevel=1) " +
                    "ORDER BY publishtime desc";

    public Article getHeadLineArticle(int ID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETHeadLineARTICLE);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                article = loadBriefContent(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return article;
    }

    public int checklock(Article article) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int check = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_article where id = ? and lockstatus = 1");
            pstmt.setInt(1, article.getID());
            rs = pstmt.executeQuery();
            if (rs.next()) check = 1;
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return check;
    }

    private static final String SQL_UpdatePubFlag = "UPDATE tbl_article SET pubflag = ?,isPublished = ? WHERE ID = ?";

    public void updatePubFlag(Article article) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
        SiteInfo siteinfo = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            //修改文章发布状态信息
            pstmt = conn.prepareStatement(SQL_UpdatePubFlag);
            pstmt.setInt(1, article.getPubFlag());
            pstmt.setInt(2, article.getIsPublished());
            pstmt.setInt(3, article.getID());
            pstmt.executeUpdate();
            pstmt.close();

            //查看文章的索引标志位，如果索引标志位为1表示文章曾经被索引过，需要重新建立索引
            int indexflag = 0;
            pstmt = conn.prepareStatement("select indexflag from tbl_article where id=?");
            pstmt.setInt(1, article.getID());
            rs = pstmt.executeQuery();
            if (rs.next()) indexflag=rs.getInt("indexflag");
            rs.close();
            pstmt.close();

            if (indexflag == 1) {
                siteinfo = siteManager.getSiteInfo(article.getSiteID());
                if (siteinfo!= null) {
                    pstmt = conn.prepareStatement("insert into tbl_deleted_article (siteid,columnid,articleid,sitename,acttype) values(?,?,?,?,?)");
                    pstmt.setInt(1,article.getSiteID());
                    pstmt.setInt(2,article.getColumnID());
                    pstmt.setInt(3,article.getID());
                    pstmt.setString(4, siteinfo.getDomainName());
                    pstmt.setInt(5,1);                                 //文章被索引了，需要修改索引
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch(SiteInfoException siteinfoexp) {
            siteinfoexp.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_REMOVEARTICLE = "DELETE FROM TBL_Article WHERE ID = ?";

    private static final String SQL_DEL_REFERS_ARTICLE ="DELETE FROM tbl_refers_article WHERE ArticleID = ?";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,creator,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,creator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,creator) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int remove(int ID, int siteID, String editor, int delwebflag) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 0;
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
        IExtendAttrManager extendAttrManager = ExtendAttrPeer.getInstance();
        SiteInfo siteinfo = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            Article article = getArticle(ID);
            int columnID = article.getColumnID();
            String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
            siteinfo = siteManager.getSiteInfo(siteID);

            //删除该篇文章
            pstmt = conn.prepareStatement(SQL_REMOVEARTICLE);
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
            pstmt.close();

            //删除被其它栏目引用的该篇文章
            pstmt = conn.prepareStatement(SQL_DEL_REFERS_ARTICLE);
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
            pstmt.close();

            //被删除的文章ID进入全文索引删除表
            pstmt = conn.prepareStatement("insert into tbl_deleted_article (siteid,columnid,articleid,sitename,acttype) values(?,?,?,?,?)");
            pstmt.setInt(1,siteID);
            pstmt.setInt(2,columnID);
            pstmt.setInt(3,ID);
            pstmt.setString(4,siteinfo.getDomainName());
            pstmt.setInt(5,0);
            pstmt.executeUpdate();
            pstmt.close();

            //删除远程WEB服务器上的文件
            errcode = doDeleteFile(siteID,columnID,article);

            //设置与删除文章相关的模板发布标志位
            if (errcode == 0) errcode = extendAttrManager.setTemplatePublishFlag(siteID,columnID,ID,editor);

            //创建LOG
            if (errcode==0) errcode = CMSLogUtil.writeLogToDB(siteID,columnID,ID,maintitle,editor,cpool.getType(),conn);
            conn.commit();
        } catch (SQLException e) {
            errcode = -2;
            e.printStackTrace();
        } catch (SiteInfoException siteexp) {
            errcode = -3;
            siteexp.printStackTrace();
        } catch (ExtendAttrException exp) {
            errcode = -4;
            exp.printStackTrace();
        } finally {
            try {
                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return errcode;
    }

    private int doDeleteFile(int siteID,int columnID,Article article) {
        int ID = article.getID();
        String createdate_path = article.getCreateDate().toString().substring(0, 10);
        createdate_path = createdate_path.replaceAll("-", "") + "/";
        Column column = null;
        int errcode = 0;

        IFtpSetManager siteMgr = FtpSetting.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        List list = null;
        try {
            column = columnMgr.getColumn(columnID);
            list = siteMgr.getFtpInfos(siteID);
        }
        catch (SiteInfoException e) {
            errcode = -10;
            e.printStackTrace();
        } catch (ColumnException colexp) {
            errcode = -9;
            colexp.printStackTrace();
        }
        if (errcode == 0) {
            int len = list.size();
            for (int i = 0; i < len; i++) {
                FtpInfo ftpInfo = (FtpInfo) list.get(i);
                String siteIP = ftpInfo.getIp();
                String remoteDocRoot = ftpInfo.getDocpath();
                String ftpUser = ftpInfo.getFtpuser();
                String ftpPasswd = ftpInfo.getFtppwd();

                int emptycontentflag = article.getNullContent();
                if (emptycontentflag == 0) {     //删除发布的文章页面
                    String fileName = column.getDirName() + createdate_path + ID + "." + column.getExtname();
                    if (ftpInfo.getPublishway() == 1) {
                        //删除已经发布到本地的文件
                        String pathname = remoteDocRoot + fileName;
                        pathname = StringUtil.replace(pathname, "/", java.io.File.separator);
                        File df = new File(pathname);
                        if (df.exists()) {
                            df.delete();
                        }
                    } else {
                        if (ftpInfo.getFtptype() == 0) {
                            com.bizwink.net.ftp.FtpFileToDest ftpHandle = new com.bizwink.net.ftp.FtpFileToDest();
                            ftpHandle.deleteRemoteFile(siteIP, ftpUser, ftpPasswd, fileName, remoteDocRoot);
                        } else {
                            com.bizwink.net.sftp.FtpFileToDest ftpHandle = new com.bizwink.net.sftp.FtpFileToDest();
                            ftpHandle.deleteRemoteFile(siteIP, ftpUser, ftpPasswd, fileName, remoteDocRoot);
                        }
                    }
                } else {    //删除上传的文件
                    String fileName = column.getDirName() + createdate_path + "download/" + article.getFileName();
                    if (ftpInfo.getPublishway() == 1) {
                        //删除已经发布到本地的文件
                        String pathname = remoteDocRoot + fileName;
                        pathname = StringUtil.replace(pathname, "/", java.io.File.separator);
                        File df = new File(pathname);
                        if (df.exists()) {
                            df.delete();
                        }
                    } else {
                        if (ftpInfo.getFtptype() == 0) {
                            com.bizwink.net.ftp.FtpFileToDest ftpHandle = new com.bizwink.net.ftp.FtpFileToDest();
                            ftpHandle.deleteRemoteFile(siteIP, ftpUser, ftpPasswd, fileName, remoteDocRoot);
                        } else {
                            com.bizwink.net.sftp.FtpFileToDest ftpHandle = new com.bizwink.net.sftp.FtpFileToDest();
                            ftpHandle.deleteRemoteFile(siteIP, ftpUser, ftpPasswd, fileName, remoteDocRoot);
                        }
                    }
                }
            }
        }

        return errcode;
    }

    private List getReferArticleColumnIds(int columnId) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        String SQL_GET_REFERS_ARTICLE_COLUMN_IDS = "SELECT tcid,tsiteid FROM tbl_refers_column WHERE scid = ?";
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_ARTICLE_COLUMN_IDS);
            pstmt.setInt(1, columnId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Column column = new Column();
                column.setID(rs.getInt(1));
                column.setSiteID(rs.getInt(2));
                list.add(column);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_ARTICLE_EXTENDATTR = "SELECT * FROM TBL_Article_ExtendAttr WHERE ArticleID = ?";

    private boolean copyImageToColumn(int columnID, int oldcolumnID, String articleIDs, String appPath, String username, int siteid) {
        IArticleManager articleMgr = ArticlePeer.getInstance();
        IPublishManager publishMgr = PublishPeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();

            Column column = columnMgr.getColumn(oldcolumnID);
            String oldDirName = StringUtil.replace(column.getDirName(), "/", File.separator);
            String dirName = columnMgr.getColumn(columnID).getDirName();
            String newDirName = StringUtil.replace(dirName, "/", File.separator);
            int isDefine = column.getDefineAttr();

            //如果标题，副标题等为图片，则拷贝图片到新栏目的IMAGES中
            List imgList = new ArrayList();
            List fileList = new ArrayList();
            String articlesID[] = articleIDs.split(",");

            for (int i = 0; i < articlesID.length; i++) {
                int articleID = Integer.parseInt(articlesID[i]);
                Article article = articleMgr.getArticle(articleID);
                String mainTitle = article.getMainTitle();
                String viceTitle = article.getViceTitle();
                String author = article.getAuthor();
                String source = article.getSource();
                String articlepic = article.getArticlepic();
                String bigpic = article.getProductBigPic();
                String pic = article.getProductPic();


                if (isImage(mainTitle)) imgList.add(mainTitle.trim());
                if (isImage(viceTitle)) imgList.add(viceTitle.trim());
                if (isImage(author)) imgList.add(author.trim());
                if (isImage(source)) imgList.add(source.trim());
                if (isImage(articlepic)) imgList.add(articlepic.trim());
                if (isImage(pic)) imgList.add(pic.trim());
                if (isImage(bigpic)) imgList.add(bigpic.trim());

                if (article.getNullContent() == 1 && article.getFileName() != null)
                    fileList.add(article.getFileName());

                //有扩展属性，则拷贝将扩展属性中的图片
                if (isDefine == 1 && article.getNullContent() == 0) {
                    pstmt = conn.prepareStatement(SQL_GET_ARTICLE_EXTENDATTR);
                    pstmt.setInt(1, articleID);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String value = null;
                        if (rs.getInt("type") == 1)
                            value = rs.getString("StringValue");
                        else if (rs.getInt("type") == 3)
                            value = rs.getString("TextValue");
                        if (isImage(value)) imgList.add(value.trim());
                    }
                    rs.close();
                    pstmt.close();
                }
            }

            //拷贝图片，并上传到发布主机
            for (int i = 0; i < imgList.size(); i++) {
                String filename = (String) imgList.get(i);
                String oldImgPath = appPath + oldDirName + "images" + File.separator + filename;
                File oldImgFile = new File(oldImgPath);
                if (oldImgFile.exists()) {
                    String newImgPath = appPath + newDirName + "images" + File.separator;
                    File newImgFile = new File(newImgPath);
                    if (!newImgFile.exists()) newImgFile.mkdirs();
                    FileDeal.copy(oldImgPath, newImgPath + filename, 0);
                    publishMgr.publish(username, newImgPath + filename, siteid, dirName + "images/", 0);
                }
            }

            //拷贝上传文件，并上传到发布主机
            for (int i = 0; i < fileList.size(); i++) {
                String filename = (String) fileList.get(i);
                String oldFilePath = appPath + oldDirName + filename;
                File oldFile = new File(oldFilePath);
                if (oldFile.exists()) {
                    String newFilePath = appPath + newDirName;
                    File newFile = new File(newFilePath);
                    if (!newFile.exists()) newFile.mkdirs();
                    FileDeal.copy(oldFilePath, newFilePath + filename, 0);
                    publishMgr.publish(username, newFilePath + filename, siteid, dirName + "download/", 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    private boolean isImage(String filename) {
        boolean is = false;
        if (filename != null && filename.trim().length() > 0) {
            filename = filename.toLowerCase();
            if (filename.indexOf(".gif") > -1 || filename.indexOf(".jpg") > -1 || filename.indexOf(".bmp") > -1 || filename.indexOf(".png") > -1 || filename.indexOf(".jpeg") > -1)
                is = true;
        }
        return is;
    }

    public int moveArticlesToColumn(int scope,int oldcolumnID, int columnID, String articleIDs, int siteid, String appPath, String username) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
        String dirName = colTree.getDirName(colTree, columnID);
        String SQL_MOVEARTICLESTOCOLUMN = null;
        int errcode = 0;

        if (scope == 0)
            SQL_MOVEARTICLESTOCOLUMN = "UPDATE TBL_Article SET dirname=?,ColumnID=?,pubFlag=1 WHERE (pubflag=0 OR pubflag=1) AND ID IN (" + articleIDs + ")";
        else
            SQL_MOVEARTICLESTOCOLUMN = "UPDATE TBL_Article SET dirname=?,ColumnID=?,pubFlag=1 WHERE (pubflag=0 OR pubflag=1) AND columnID=" + oldcolumnID;

        try {
            try {
                boolean success = copyImageToColumn(columnID, oldcolumnID, articleIDs, appPath, username, siteid);

                if (success) {
                    conn = cpool.getConnection();
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement(SQL_MOVEARTICLESTOCOLUMN);
                    pstmt.setString(1,dirName);
                    pstmt.setInt(2,columnID);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                } else {
                    errcode = -3;
                }
            } catch (NullPointerException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: move article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        errcode = -1;
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            errcode = -1;
            throw new ArticleException("Database exception: can't rollback?");
        }

        return errcode;
    }

    public int copyArticlesToColumn(int scope,int oldcolumnID, int columnID, String articleIDs, int siteid, String appPath, String username) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 0;

        Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
        String dirName = colTree.getDirName(colTree, columnID);
        String SQL_GETCOPYARTICLES = null;
        if (scope == 0)
            SQL_GETCOPYARTICLES = "select * from TBL_Article WHERE  id IN (" + articleIDs + ")";
        else
            SQL_GETCOPYARTICLES = "select * from TBL_Article WHERE columnID = " + oldcolumnID;

        try {
            boolean success = copyImageToColumn(columnID, oldcolumnID, articleIDs, appPath, username, siteid);
            if (success) {
                Article article=null;
                conn = cpool.getConnection();

                pstmt = conn.prepareStatement(SQL_GETCOPYARTICLES);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = load(rs);
                    article.setColumnID(columnID);
                    article.setPubFlag(1);
                    article.setSiteID(siteid);
                    article.setDirName(dirName);
                    article.setEditor(article.getEditor());
                    article.setPublishTime(new Timestamp(System.currentTimeMillis()));

                    if (!cpool.getType().equals("oracle")) {
                        article.setMainTitle(StringUtil.iso2gbindb(article.getMainTitle()));
                        if (article.getViceTitle() != null)
                            article.setViceTitle(StringUtil.iso2gbindb(article.getViceTitle()));
                        if (article.getSource() != null)
                            article.setSource(StringUtil.iso2gbindb(article.getSource()));
                        if (article.getSummary() != null)
                            article.setSummary(StringUtil.iso2gbindb(article.getSummary()));
                        if (article.getKeyword() != null)
                            article.setKeyword(StringUtil.iso2gbindb(article.getKeyword()));
                        if (article.getAuthor() != null)
                            article.setAuthor(StringUtil.iso2gbindb(article.getAuthor()));
                        if (article.getContent() != null)
                            article.setContent(StringUtil.iso2gbindb(article.getContent()));
                    }

                    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
                    List extendList = new ArrayList();
                    //attrList,attechmentList,article,pubcolumns,recommends,uploadimages,auditlist,mmfiles
                    extendMgr.create(extendList, null, article, null, null,null,null,null);
                }
                rs.close();
                pstmt.close();
            } else {
                errcode = -3;
            }
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
            throw new ArticleException("Database exception: move article failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    errcode = -1;
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return errcode;
    }

    //获取子树下的所有子栏目ID
    private int[] getSubTreeColumnIDList(node[] treenodes, int columnID) {
        int[] cid = new int[treenodes.length + 1];
        int[] pid = new int[treenodes.length];
        int nodenum = 1;
        int i;
        int j = 1;
        pid[1] = columnID;

        do {
            columnID = pid[nodenum];
            cid[j] = columnID;
            j = j + 1;
            nodenum = nodenum - 1;
            for (i = 0; i < treenodes.length; i++) {
                if (treenodes[i] != null) {
                    if (treenodes[i].getLinkPointer() == columnID) {
                        nodenum = nodenum + 1;
                        pid[nodenum] = treenodes[i].getId();
                    }
                }
            }
        } while (nodenum >= 1);

        cid[0] = j - 1;            //cid[0]元素保存找到的子节点的数目

        return cid;
    }

    //为 共享站点写方法
    public String sharegetColumnIDs(int columnID, int siteid, int samsiteid) {
        IColumnManager columnMgr = ColumnPeer.getInstance();
        int siteID = 0;
        try {
            siteID = columnMgr.getColumn(columnID).getSiteID();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        //共享站点TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
        String result = "(";
        Tree colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid, samsiteid);

        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID);
        for (int i = 0; i < cid[0] - 1; i++) {
            result = result + cid[i + 1] + ",";
        }
        result = result + cid[cid[0]] + ")";

        return result;
    }

    public String getColumnIDs(int columnID) {
        IColumnManager columnMgr = ColumnPeer.getInstance();
        int siteID = 0;
        try {
            siteID = columnMgr.getColumn(columnID).getSiteID();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        //共享站点TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
        String result = "(";
        Tree colTree = TreeManager.getInstance().getSiteTree(siteID);
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID);
        for (int i = 0; i < cid[0] - 1; i++) {
            result = result + cid[i + 1] + ",";
        }
        result = result + cid[cid[0]] + ")";

        return result;
    }

    public List getArticles(Tree colTree, int columnID, int noContent) throws ArticleException {
        String cidStr = "(";
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID);

        for (int i = 0; i < cid[0]; i++) {
            cidStr = cidStr + cid[i + 1] + ",";
        }
        cidStr = cidStr + cid[cid[0]] + ")";

        return getArticles(cidStr, noContent);
    }

    public List getCArticles(Tree colTree, int columnID, int noContent, int start, int range) throws ArticleException {
        String cidStr = "(";
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID);

        for (int i = 0; i < cid[0]; i++) {
            cidStr = cidStr + cid[i + 1] + ",";
        }
        cidStr = cidStr + cid[cid[0]] + ")";

        return getArticles(cidStr, noContent, start, range);
    }

    //根据内容是否为空，选择文章
    private List getArticles(String cidString, int noContent) {
        String SQLStr = "";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        if (noContent == 0)    //emptycontentflag=1表示文章内容为空
        {
            SQLStr = "SELECT * FROM tbl_article where columnid in " + cidString +
                    " and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 1)  //emptycontentflag=0表示文章内容非空
        {
            SQLStr = "SELECT * FROM tbl_article where columnid in " + cidString +
                    " and emptycontentflag=0 order by publishtime desc";
        } else if (noContent == 2)  //不考虑文章内容是否为空，取出所有文章
        {
            SQLStr = "SELECT * FROM tbl_article where columnid in " + cidString +
                    " order by publishtime desc";
        }

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                article = load(rs);
                list.add(article);
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //根据内容是否为空，选择文章
    private List getArticles(String cidString, int noContent, int start, int range) {
        String SQLStr = "";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        if (noContent == 0)    //emptycontentflag=1表示文章内容为空
        {
            SQLStr = "SELECT * FROM tbl_article where columnid in " + cidString +
                    " and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 1)  //emptycontentflag=0表示文章内容非空
        {
            SQLStr = "SELECT * FROM tbl_article where columnid in " + cidString +
                    " and emptycontentflag=0 order by publishtime desc";
        } else if (noContent == 2)  //不考虑文章内容是否为空，取出所有文章
        {
            SQLStr = "SELECT * FROM tbl_article where columnid in " + cidString +
                    " order by publishtime desc";
        }

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();

            for (int i = 0; i < start; i++) {
                rs.next();
            }

            for (int i = 0; i < range; i++) {
                if (rs.next()) {
                    article = load(rs);
                    list.add(article);
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //选择某个栏目下的所有文章，不管内容是否为空
    public List getOrderArticles(String cidString, int order) throws ArticleException {
        String SQLStr = "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,editor,id,columnid," +
                "sortid,doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor," +
                "dirname,filename,publishtime,RelatedArtID,urltype,defineurl,clicknum,notearticleid,siteid FROM tbl_article where status = 1 and " +
                "auditflag=0 and columnid in " + cidString + "order by publishtime desc";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        if (order == 0)
            SQLStr = "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor," +
                    "dirname,filename,publishtime,RelatedArtID,urltype,defineurl,clicknum,notearticleid,siteid FROM tbl_article where status = 1 and " +
                    "auditflag =0 and columnid in " + cidString + "order by publishtime desc";
        if (order == 1)
            SQLStr = "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor," +
                    "dirname,filename,publishtime,RelatedArtID,urltype,defineurl,clicknum,notearticleid,siteid FROM tbl_article where status = 1 and " +
                    "auditflag=0 and columnid in " + cidString + "order by publishtime";
        if (order == 2)
            SQLStr = "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor," +
                    "dirname,filename,publishtime,RelatedArtID,urltype,defineurl,clicknum,notearticleid,siteid FROM tbl_article where status = 1 and " +
                    "auditflag=0 and columnid in " + cidString + "order by sortid asc,publishtime desc";
        if (order == 3)
            SQLStr = "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor," +
                    "dirname,filename,publishtime,RelatedArtID,urltype,defineurl,clicknum,notearticleid,siteid FROM tbl_article where status = 1 and " +
                    "auditflag=0 and columnid in " + cidString + "order by sortid desc,publishtime desc";

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                article = loadNoContent(rs);
                list.add(article);
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    public List getArticles4PublishTopStories(String articleids) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,id,columnid,sortid,status,");
            SQL.append("doclevel,dirname,filename,publishtime,RelatedArtID,referID,articlepic,createdate,urltype,defineurl,downfilename,siteid" +
                    " FROM tbl_article where status = 1 ");
            SQL.append("and auditflag = 0 and ID in (" + articleids + ") order by publishtime desc");

            pstmt = conn.prepareStatement(SQL.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load4Publish(rs));
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {

                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public List getOrderArticles(int startnum, int endnum, String cidString, String where, String orderby, int status,
                                 int articleNum, int defaultColumnID, boolean hasReferedArticle) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        int archive = status;

        try {
            conn = cpool.getConnection();
            String aidString = "";
            List referedArticleColumnid = new ArrayList();                    //用于记录是否被引用的文章
            mapOfReferedArticleColumn mapAC = null;                           //引用文章的ID和引用这篇文章的栏目的栏目ID之间的关系
            cidString = formatOracleClause(cidString);

            //获取引用栏目的文章ID
            String SQL_GET_ARTICLEID_IN_OTHERCOLUMN = "SELECT ArticleID,columnid FROM TBL_Refers_Article where " + cidString + " and pubflag = 0";

            pstmt = conn.prepareStatement(SQL_GET_ARTICLEID_IN_OTHERCOLUMN);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                aidString = aidString + rs.getInt(1) + ",";
                mapAC = new mapOfReferedArticleColumn();
                mapAC.setArticleID(rs.getInt("ArticleID"));
                mapAC.setColumnID(rs.getInt("columnid"));
                referedArticleColumnid.add(mapAC);
            }
            rs.close();
            pstmt.close();

            if (aidString.length() > 0) {
                aidString = aidString.substring(0, aidString.length() - 1);
                aidString = formatOracleClause(aidString, 0);
            } else {
                aidString = "(0)";
            }

            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,id,columnid,sortid,status,");
            SQL.append("doclevel,dirname,filename,publishtime,RelatedArtID,referID,articlepic,createdate,urltype,defineurl,clicknum,downfilename,siteid" +
                    " FROM tbl_article where ");
            if (archive == 0)          //列表不包括归档文章
                SQL.append("(status =1 OR status=4 OR status=5 OR status=6) ");
            else if (archive == 1)    //列出所有文章
                SQL.append("(status =1 OR status=4 OR status=5 OR status=2 OR status=6) ");
            else                      //只列出归档文章
                SQL.append("(status=2) ");
            SQL.append("and auditflag = 0 and (isPublished = 1 or emptycontentflag = 1) ");
            SQL.append("and auditflag = 0 ");
            SQL.append(" and " + cidString + " " + where + " or ID in (" + aidString + ")" + orderby);

            IColumnManager cMgr = ColumnPeer.getInstance();
            pstmt = conn.prepareStatement(SQL.toString());
            rs = pstmt.executeQuery();

            if (endnum > 0) {
                if ((endnum - startnum + 1) < articleNum)
                    articleNum = endnum - startnum + 1;

                if (articleNum > 0) {
                    for (int i = 1; i < startnum; i++) {
                        rs.next();
                    }
                    for (int i = 0; i < articleNum && rs.next(); i++) {
                        int targetCId = getTargetCID(rs.getInt("id"), referedArticleColumnid);
                        if (targetCId == 0) targetCId = rs.getInt("columnid");
                        list.add(load4Publish(rs,targetCId,(i+1)));
                    }
                }
            } else {
                //add by feixiang 2009-01-02 解决单页文章列表文章从第几篇到第几篇不填的时候的默认值
                if (articleNum > 0) {
                    int i = 0;
                    while (rs.next() && i < articleNum) {
                        i++;
                        int targetCId = getTargetCID(rs.getInt("id"), referedArticleColumnid);
                        if (targetCId == 0) targetCId = rs.getInt("columnid");
                        list.add(load4Publish(rs, targetCId,i));
                    }
                } else {
                    int i = 0;
                    while (rs.next()) {
                        i++;
                        int targetCId = getTargetCID(rs.getInt("id"), referedArticleColumnid);
                        if (targetCId == 0) targetCId = rs.getInt("columnid");
                        list.add(load4Publish(rs, targetCId,i));
                    }
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    //stype 同一栏目文章的二次分类,获取按新分类统计的文章列表
    public List getOrderArticles(int startnum, int endnum, String cidString, String where, String orderby, int status,
                                 int articleNum, int defaultColumnID, String articleids) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        int archive = status;

        try {
            conn = cpool.getConnection();
            String aidString = "";
            List referedArticleColumnid = new ArrayList();
            mapOfReferedArticleColumn mapAC = null;
            cidString = formatOracleClause(cidString);

            //获取引用栏目的文章ID
            String SQL_GET_ARTICLEID_IN_OTHERCOLUMN =
                    "SELECT ArticleID,columnid FROM TBL_Refers_Article where " + cidString + " and pubflag = 0";

            pstmt = conn.prepareStatement(SQL_GET_ARTICLEID_IN_OTHERCOLUMN);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                aidString = aidString + rs.getInt(1) + ",";
                mapAC = new mapOfReferedArticleColumn();
                mapAC.setArticleID(rs.getInt("ArticleID"));
                mapAC.setColumnID(rs.getInt("columnid"));
                referedArticleColumnid.add(mapAC);
            }
            rs.close();
            pstmt.close();

            if (aidString.length() > 0) {
                aidString = aidString.substring(0, aidString.length() - 1);
                aidString = formatOracleClause(aidString, 0);
            } else {
                aidString = "(0)";
            }

            StringBuffer SQL = new StringBuffer();
            SQL.append("SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,id,columnid,sortid,status,");
            SQL.append("doclevel,dirname,filename,publishtime,RelatedArtID,referID,articlepic,createdate,urltype,defineurl,downfilename,siteid" +
                    " FROM tbl_article where ");
            if (archive == 0)          //列表不包括归档文章
                SQL.append("(status = 1 OR status=4 OR status=5) ");
            else if (archive == 1)    //列出所有文章
                SQL.append("(status = 1 OR status=4 OR status=5 or status=2) ");
            else                      //只列出归档文章
                SQL.append("(status=2) ");

            SQL.append("and auditflag = 0 and (isPublished = 1 or emptycontentflag = 1) ");
            SQL.append(" and " + cidString + " " + where + " or ID in " + aidString + " and pubflag = 0" + orderby);
            IColumnManager cMgr = ColumnPeer.getInstance();
            pstmt = conn.prepareStatement(SQL.toString());
            rs = pstmt.executeQuery();

            if (endnum > 0) {
                if ((endnum - startnum + 1) < articleNum)
                    articleNum = endnum - startnum + 1;

                if (articleNum > 0) {
                    for (int i = 1; i < startnum; i++) {
                        rs.next();
                    }
                    for (int i = 0; i < articleNum && rs.next(); i++) {
                        int targetCId = getTargetCID(rs.getInt("id"), referedArticleColumnid);
                        if (targetCId == 0) targetCId = rs.getInt("columnid");
                        list.add(load4Publish(rs, targetCId,(i+1)));
                    }
                }
            } else {
                int i =0;
                while (rs.next()) {
                    i++;
                    int targetCId = getTargetCID(rs.getInt("id"), referedArticleColumnid);
                    if (targetCId == 0) targetCId = rs.getInt("columnid");
                    list.add(load4Publish(rs, targetCId,i));
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private int getTargetCID(int articleId, List alist) {
        int targetColumnId = 0;
        mapOfReferedArticleColumn mapAC = new mapOfReferedArticleColumn();

        for (int i = 0; i < alist.size(); i++) {
            mapAC = (mapOfReferedArticleColumn) alist.get(i);
            if (mapAC.getArticleID() == articleId) {
                targetColumnId = mapAC.getColumnID();
                break;
            }
        }

        return targetColumnId;
    }

    public int getOrderArticlesCount(String cidString, String where, int siteid) throws ArticleException {
        String SQLStr = "SELECT count(*) FROM TBL_Article where status = 1 and auditflag = 0 and siteid=" + siteid + " and ColumnID IN " + cidString + where;
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getArticles(String cidString) throws ArticleException {
        String SQLStr = "SELECT * FROM tbl_article where status = 1 and columnid in " + cidString + "order by publishtime desc";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Article article=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                article = load(rs);
                list.add(article);
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    //获得栏目下所有文章的id(某个栏目下所有文章的移动或拷贝 /articlemove/moveok.jsp /articlemove/copyok.jsp)
    public String getArticlesIdofColumn(int columnid) throws ArticleException {
        String SQLStr = "SELECT id FROM tbl_article where columnid = ?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String articleIds = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                articleIds = articleIds + rs.getInt(1) + ",";
            }
            rs.close();
            pstmt.close();

            if ((articleIds.indexOf(",") != -1)) {
                articleIds = articleIds.substring(0, articleIds.length() - 1);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return articleIds;
    }

    public int getArticleNum(int columnID, int noContent) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String cidString = formatOracleClause(getColumnIDs(columnID));  //emptycontentflag=0表示文章内容非空
        String strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE PubFlag = 1 AND " + cidString + " AND emptycontentflag=0 and status=1";

        if (noContent == 0)    //emptycontentflag=1表示文章内容为空
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE PubFlag = 1 AND " + cidString + " AND emptycontentflag=1";
        } else if (noContent == 2)   //取出某个栏目下已经发布的文章
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE PubFlag = 1 AND " + cidString;
        } else if (noContent == 3)    //取出某个栏目下正在使用的文章
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE Status = 1 AND ColumnID = " + columnID;
        } else if (noContent == 4)   //为文章管理
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE EmptyContentFlag = 0 AND ColumnID = " + columnID;
        } else if (noContent == 5)   //为文章移动
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE ColumnID = " + columnID + " AND Status = 1 AND emptycontentflag = 0";
        } else if (noContent == 11)     //emptycontentflag=0表示文章内容非空
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE " + cidString + " AND emptycontentflag=0 AND AuditFlag = 1";
        } else if (noContent == 12)   //为文章管理
        {
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE EmptyContentFlag = 0 AND " + cidString;
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            if (noContent == 1)  //publish
            {
                //获取模板数量
                strSQL = "SELECT COUNT(ID) FROM TBL_Template WHERE " + cidString + " AND Status = 0 AND IsArticle IN (0,2)";
                if (noContent == 3) {
                    strSQL = "SELECT COUNT(ID) FROM TBL_Template WHERE ColumnID = " + columnID + " AND IsArticle IN (0,2)";
                }

                pstmt = conn.prepareStatement(strSQL);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = count + rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //usearticletype=1 表示引用是内容引用
    //usetypearticle=0 表示引用是连接引用
    public List getNewPublishArticles(int columnID, int startIndex, int numResults, int listShow, int siteid, int samsiteid,int sitetype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        List tempList = new ArrayList();
        Article article=null;

        //判断是否需要显示共享网站的信息
        int mssflag = 0;
        String sites_condition = "";
        try {
            conn = cpool.getConnection();
            String strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }


        //定义站点选择条件
        if (mssflag == 1) {
            sites_condition = "(a.siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or a.siteid=" + siteid + ")";
        } else {
            sites_condition = "a.siteid=" + siteid;
        }

        String cidString = "";
        if (sitetype == 0) {                                       //独立站点
            cidString = formatOracleClause(getColumnIDs(columnID));
        } else {                                                   //共享站点
            cidString = formatOracleClause(getColumnIDs(siteid, samsiteid, columnID));
        }

        //读取本栏目和它的所有子栏目的文章
        String strSQL = "SELECT ID,ColumnID,MainTitle,emptycontentflag,Editor,LastUpdated,viceTitle,dirName,urltype,defineurl,siteid " +
                "FROM TBL_Article a WHERE (status = 1 OR status=5 OR status=4 OR status=6) AND auditflag = 0 and emptycontentflag = 0 AND " + sites_condition + " and pubflag = 1 AND " +
                cidString + " ORDER BY PublishTime DESC";

        //读取本栏目和它的所有子栏目的模板
        String strSQL1 = "SELECT ID,ColumnID,IsArticle,Editor,LastUpdated,DefaultTemplate,ChName,siteid FROM TBL_Template a WHERE Status = 0 AND " + sites_condition +
                " AND IsArticle IN (0,2,3,4,5,6) AND " + cidString + " ORDER BY LastUpdated DESC";

        //读取本栏目和它的所有子栏目引用的文章
        String strSQL2 = "SELECT a.ID,b.ColumnID,b.scolumnid,a.MainTitle,a.emptycontentflag,a.Editor,a.LastUpdated,a.viceTitle,a.dirName," +
                "a.urltype,a.defineurl,a.siteid from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and b.pubflag=1 AND " + sites_condition +
                " AND b.usearticletype=1 AND b." + cidString + " ORDER BY a.createdate DESC";

        try {
            if (listShow == 0){            //先文章后模板
                //获取本栏目自己待发布的文章
                pstmt = conn.prepareStatement(strSQL);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 0);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目引用的文章
                pstmt = conn.prepareStatement(strSQL2);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 2);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目需要发布的模板
                pstmt = conn.prepareStatement(strSQL1);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, true, 1);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();
            } else {
                //获取本栏目需要发布的模板
                pstmt = conn.prepareStatement(strSQL1);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, true, 1);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目自己待发布的文章
                pstmt = conn.prepareStatement(strSQL);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 0);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目引用的文章
                pstmt = conn.prepareStatement(strSQL2);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 1);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();
            }

            for (int i = startIndex; i < startIndex + numResults; i++) {
                if (i > tempList.size() - 1) break;
                article = (Article) tempList.get(i);
                list.add(article);
            }

        } catch (Exception t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    //文章发布总数(/publish/articles.jsp)
    //usearticletype=1 表示引用是内容引用
    //usetypearticle=0 表示引用是连接引用
    public int getNewPublishArticlesNum(int columnID, int siteid, int samsiteid,int sitetype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        String cidString = "";//formatOracleClause(getColumnIDs(columnID));

        //判断是否需要显示共享网站的信息
        int mssflag = 0;
        String sites_condition = "";
        try {
            conn = cpool.getConnection();
            String strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        //定义站点选择条件
        if (mssflag == 1) {
            sites_condition = "(a.siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or a.siteid=" + siteid + ")";
        } else {
            sites_condition = "a.siteid=" + siteid;
        }

        if (samsiteid > 0) {
            cidString = formatOracleClause(getColumnIDs(siteid, samsiteid, columnID));
        } else {
            cidString = formatOracleClause(getColumnIDs(columnID));
        }

        String strSQL_forArticle =
                "SELECT COUNT(id) FROM TBL_Article a WHERE (status = 1 OR status=4 OR status=5 OR status=6) AND AuditFlag = 0 " +
                        "AND PubFlag = 1 AND " + cidString + " and " + sites_condition + "  AND EmptyContentFlag = 0";

        //读取模板
        String strSQL_forTemplate =
                "SELECT COUNT(id) FROM TBL_Template a WHERE Status = 0 AND " + sites_condition + " AND IsArticle IN (0,2,3,4,5,6) AND "  + cidString;

        //读取本栏目和它的所有子栏目引用的文章
        String strSQL2 = "SELECT count(a.id) from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and " + sites_condition + " AND b.pubflag=1 " +
                "AND b.usearticletype=1 AND b." + cidString;

        try {
            //conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL_forArticle);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(strSQL_forTemplate);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(strSQL2);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return count;
    }

    //为自动发布获取本站点所有需要发布的文章
    private static final String SQL_GET_ONE_ARTICLE_FOR_PUBLISH =
            "SELECT * FROM (SELECT ID,MainTitle,PublishTime,ColumnID FROM tbl_article where Status=1 AND " +
                    "EmptycontentFlag=0 AND PubFlag=1 AND AuditFlag=0) WHERE ROWNUM <= 1 ORDER BY ROWNUM ASC";

    //读取本站点引用的所有文章
    //private static final  String GET_REFERS_ARTICLE = "SELECT * FROM (SELECT a.ID,b.ColumnID,b.scolumnid,a.MainTitle,a.PublishTime,a.dirName," +
    //        "a.urltype,a.defineurl from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and b.pubflag=1 " +
    //        "AND b.usearticletype=1 AND b.siteid=? ORDER BY a.createdate DESC)  WHERE ROWNUM <= 1";

    private static final String GET_REFERS_ARTICLE = "SELECT articleid,ColumnID,scolumnid,title,createdate" +
            " from tbl_refers_article  WHERE pubflag=1 AND usearticletype=1 ORDER BY createdate DESC";

    private static final String UPDATE_REFERS_ARTICLE = "UPDATE tbl_refers_article SET PubFlag = 0 WHERE articleid = ? " +
            "and columnid=?";

    //获取某个站点需要发布的所有栏目页面
    private static final String SQL_GET_MODEL_FORAUTOPUB =
            "select id,columnid,isarticle,chname from tbl_template where isarticle != 1 and status = 0";

    private static final String SQL_UPDATE_MODEL_FORAUTOPUB = "UPDATE TBL_Template SET Status = 1 WHERE ID = ?";

    synchronized public Article getOneArticleForPublish() throws ArticleException {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article one_article = null;
        int jobflag = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_GET_ONE_ARTICLE_FOR_PUBLISH);     //得到所有符合条件的需要发布的文章
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    jobflag = 1;
                    one_article = new Article();
                    one_article.setID(rs.getInt("ID"));
                    one_article.setMainTitle(rs.getString("maintitle"));
                    one_article.setPublishTime(rs.getTimestamp("publishtime"));
                    one_article.setColumnID(rs.getInt("columnid"));
                    one_article.setStatus(1);
                    one_article.setReferedTargetId(0);                                   //栏目自有的文章的发布作业
                }
                rs.close();
                pstmt.close();

                if (jobflag == 0) {
                    pstmt = conn.prepareStatement(GET_REFERS_ARTICLE);                //获取本栏目引用的文章发布作业
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        jobflag = 2;
                        one_article = new Article();
                        one_article.setID(rs.getInt("articleid"));
                        one_article.setMainTitle(rs.getString("title"));
                        one_article.setPublishTime(rs.getTimestamp("createdate"));
                        one_article.setColumnID(rs.getInt("columnid"));
                        one_article.setStatus(1);
                        one_article.setReferedTargetId(1);                                   //栏目自有的文章
                    }
                    rs.close();
                    pstmt.close();
                }

                if (jobflag == 0) {
                    pstmt = conn.prepareStatement(SQL_GET_MODEL_FORAUTOPUB);     //得到所有符合条件的需要发布的文章
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        jobflag = 3;
                        one_article = new Article();
                        one_article.setID(rs.getInt("ID"));
                        one_article.setMainTitle(rs.getString("chname"));
                        one_article.setPublishTime(new Timestamp(System.currentTimeMillis()));
                        one_article.setColumnID(rs.getInt("columnid"));
                        one_article.setStatus(rs.getInt("isarticle"));
                    }
                    rs.close();
                    pstmt.close();
                }

                if (one_article != null) {
                    if (jobflag == 1) {
                        pstmt = conn.prepareStatement(SQL_UPDATE_AUTOPUB_ARTICLES);  //更新文章的发布标志位为发布中
                        int articleID = one_article.getID();
                        pstmt.setInt(1, articleID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (jobflag == 2) {
                        pstmt = conn.prepareStatement(UPDATE_REFERS_ARTICLE);  //更新引用文章的发布标志位为发布中
                        int articleID = one_article.getID();
                        pstmt.setInt(1, articleID);
                        pstmt.setInt(2, one_article.getColumnID());
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (jobflag == 3) {
                        pstmt = conn.prepareStatement(SQL_UPDATE_MODEL_FORAUTOPUB);  //更新模板的发布标志位为发布中
                        int articleID = one_article.getID();
                        pstmt.setInt(1, articleID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update article failed.");
            }
            finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new ArticleException("Database exception: can't rollback?");
        }

        return one_article;
    }

    public List getArticles(int columnID, int startIndex, int numResults, int noContent) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                        "auditflag,publishtime,auditor,filename,urltype,defineurl,siteid FROM tbl_article where status = 1 " +
                        "and " + cidString + "and emptycontentflag=0 and (auditflag = 0 or auditflag = 1) order by publishtime desc";

        if (noContent == 0)   //emptycontentflag=1表示文章内容为空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel," +
                    "pubflag,auditflag,publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article " +
                    "where status=1 and " + cidString + " and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 1)    //emptycontentflag=0表示文章内容非空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                    "auditflag,publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article where status = 1" +
                    " and " + cidString + " and emptycontentflag=0 " +
                    "and (auditflag = 0 or auditflag = 1)order by publishtime desc";
        } else if (noContent == 2) {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                    "auditflag,publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article where status=1 " +
                    "and " + cidString + " order by publishtime desc";
        } else if (noContent == 11)   //emptycontentflag=0表示文章内容非空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel," +
                    "pubflag,auditflag,publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article " +
                    "where status=1 and " + cidString + " and emptycontentflag=0 and auditflag = 1 order by publishtime desc";
        }

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    public List getAllUsedArticles(int columnID, int startIndex, int numResults, int noContent) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL = "SELECT * FROM tbl_article where status=1 and " + cidString + " and emptycontentflag=0 order by publishtime desc";

        if (noContent == 0) {             //emptycontentflag=1内容为空
            strSQL = "SELECT * FROM tbl_article where status=1 and " + cidString + " and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 1) {       //emptycontentflag=0内容非空
            strSQL = "SELECT * FROM tbl_article where status=1 and " + cidString + " and emptycontentflag=0 order by publishtime desc";
        } else if (noContent == 2) {        //不考虑文章内容是否为空，取出某个栏目下的所有文章
            strSQL = "SELECT * FROM tbl_article where status=1 and " + cidString + " order by publishtime desc";
        }

        List list = new ArrayList();
        Article article=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = load(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    //by Hujingyu to divide the articles on 2003-02-15
    public int getArticleNum(int columnID, int noContent, int status) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL = "SELECT count(ID) FROM tbl_article where (status=?) and " + cidString + " and emptycontentflag=0";

        if (noContent == 0) {          //emptycontentflag=1表示文章内容为空
            strSQL = "SELECT count(ID) FROM tbl_article where (status=?) and " + cidString + " and emptycontentflag=1 ";
        } else if (noContent == 1) {    //emptycontentflag=0表示文章内容非空
            strSQL = "SELECT count(ID) FROM tbl_article where (status=?) and " + cidString + " and emptycontentflag=0";
        } else if (noContent == 2) {     //不考虑文章内容是否为空，取出该栏目下的所有文章
            strSQL = "SELECT count(ID) FROM tbl_article where (status=?) and " + cidString;
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, status);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //文件上传列表(/upload/listuploadfiles.jsp)
    public List getUploadFiles(int columnID, int startIndex, int numResults, String editor) throws ArticleException {
        String SQL_GET_UPLOAD_FILES =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel," +
                        "pubflag,auditflag,publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article where status = 1 " +
                        "and auditflag = 0 and columnid = ? and emptycontentflag = 1 order by publishtime desc";
        if (editor.length() > 0)
            SQL_GET_UPLOAD_FILES =
                    "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                            "publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article where status = 1 and auditflag = 0 and " +
                            "editor = '" + editor + "' and columnid = ? and emptycontentflag = 1 order by publishtime desc";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UPLOAD_FILES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //文件上传总数(/upload/listuploadfiles.jsp)
    public int getUploadFilesNum(int columnID, String editor) throws ArticleException {
        String SQL_GET_UPLOAD_FILES_NUM =
                "SELECT Count(*) FROM tbl_article where status = 1 and auditflag = 0 and columnID = ? and emptycontentflag = 1";
        if (editor.length() > 0)
            SQL_GET_UPLOAD_FILES_NUM =
                    "SELECT Count(*) FROM tbl_article where status = 1 and auditflag = 0 " +
                            "and editor = '" + editor + "' and columnID = ? and emptycontentflag = 1";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UPLOAD_FILES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_UNUSEDUPLOAD_FILES =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel," +
                    "pubflag,auditflag,publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article " +
                    "where status=0 and auditflag = 0 and columnid = ? and emptycontentflag=1 order by publishtime desc";

    //上传文件未用列表(/upload/unuseduploadfiles.jsp)
    public List getUnusedUploadFiles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UNUSEDUPLOAD_FILES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_UNUSEDUPLOAD_FILES_NUM =
            "SELECT Count(*) FROM tbl_article where status = 0 and auditflag = 0 and columnID = ? and emptycontentflag = 1";

    //上传文件未用总数(/upload/unuseduploadfiles.jsp)
    public int getUnusedUploadFilesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UNUSEDUPLOAD_FILES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_AUDITUPLOAD_FILES =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                    "publishtime,Auditor,filename,urltype,defineurl,siteid FROM tbl_article where status = 1 and auditflag = 1 " +
                    "and columnid = ? and emptycontentflag = 1 order by publishtime desc";

    //文件上传审核列表(/upload/audituploadfiles.jsp)
    public List getAuditUploadFiles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_AUDITUPLOAD_FILES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_AUDITUPLOAD_FILES_NUM =
            "SELECT Count(*) FROM tbl_article where status = 1 and auditflag = 1 and columnID = ? and emptycontentflag = 1";

    //审核文件上传总数(/upload/audituploadfiles.jsp)
    public int getAuditUploadFilesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_AUDITUPLOAD_FILES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //移动文章列表(/articlemove/listarticle.jsp)
    public List getMoveArticles(int columnID, int startIndex, int numResults, String content, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;
        String strSQL="";
        String editor="";

        String SQL_GET_ARTICLES_FOR_MOVE = "SELECT maintitle,emptycontentflag,editor,id,columnID,sortID,lockstatus,lockeditor,doclevel," +
                "pubflag,auditflag,auditor,filename,publishtime,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article " +
                "where AuditFlag = 0 AND Status = 1 and siteid=" + siteid + " and columnID = ? ";
        if (content != null && content.trim().length() > 0 && !content.equalsIgnoreCase("null"))
            SQL_GET_ARTICLES_FOR_MOVE += "and maintitle like '%" + content + "%' ";
        SQL_GET_ARTICLES_FOR_MOVE += "order by publishtime desc";

        if (editor.length() > 0) {
            if (cpool.getType().equalsIgnoreCase("oracle"))
                strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                        "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename FROM tbl_article " +
                        "WHERE (columnID = ? and siteid="+ siteid +" and status=1  and editor='" + editor + "')" + " order by publishtime desc) A WHERE ROWNUM <= ?) WHERE RN >= ?";
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?  and siteid="+ siteid +" and status=1  and editor='" + editor + "' order by publishtime desc";
            else {
                strSQL = "select ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                        "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename  from tbl_article "+
                        "where columnid=? and siteid=" + siteid + " and status=1 and editor='" + editor + "' order by publishtime desc" + " limit ?,?";
            }
        }
        else  {
            if (cpool.getType().equalsIgnoreCase("oracle"))
                strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                        "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename FROM tbl_article WHERE columnID = ? and siteid=" + siteid + " and status=1 order by publishtime desc" + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?   and siteid="+ siteid +" and status=1 order by publishtime desc";
            else {
                strSQL = "select ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                        "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename  from tbl_article "+
                        "where columnid=? and siteid=" + siteid + " and status=1 order by publishtime desc" + " limit ?,?";
            }
        }


        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLES_FOR_MOVE);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadArticleList(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //移动文章总数(/articlemove/listarticle.jsp)
    public int getMoveArticlesNum(int columnID, String content, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String SQL_GET_MOVE_ARTICLE_NUM =
                "SELECT COUNT(ID) FROM TBL_Article WHERE ColumnID = ? AND AuditFlag = 0 AND Status = 1 ";
        if (content != null && content.trim().length() > 0 && !content.equalsIgnoreCase("null"))
            SQL_GET_MOVE_ARTICLE_NUM += "and maintitle like '%" + content + "%'";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_MOVE_ARTICLE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getArticles(int columnID, int startIndex, int numResults, int noContent, int status) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid," +
                        "lockstatus,lockeditor,doclevel,pubflag,auditflag,auditor,filename," +
                        "publishtime,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article where status = ? and " +
                        cidString + " and emptycontentflag = 0 order by publishtime desc";

        if (noContent == 0)     //emptycontentflag=1表示内容字段为空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid," +
                    "lockstatus,lockeditor,doclevel,pubflag,auditflag,auditor,filename," +
                    "publishtime,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article where status = ? and " +
                    cidString + " and and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 2)    //取出所有文章，不考虑内容字段是否为空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel," +
                    "pubflag,auditflag,lockstatus,lockeditor,auditor,filename,publishtime,status,viceTitle,dirName,urltype,defineurl,createdate " +
                    "FROM tbl_article where (status = ?) and " + cidString + " order by publishtime desc";
        } else if (noContent == 4)    //为文章管理
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,filename,publishtime,lockstatus," +
                    "lockeditor,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article where columnid = " +
                    columnID + " and emptycontentflag = 0 order by publishtime desc";
        } else if (noContent == 5)    //为文章移动列表服务
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid," +
                    "lockstatus,lockeditor,doclevel,pubflag,auditflag,auditor,filename," +
                    "publishtime,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article where status = ? and columnid = " +
                    columnID + " and emptycontentflag = 0 order by publishtime desc";
        }

        try {
            conn = cpool.getConnection();
            if (noContent == 4) {
                pstmt = conn.prepareStatement(strSQL);
            } else {
                pstmt = conn.prepareStatement(strSQL);
                pstmt.setInt(1, status);
            }

            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadArticleList(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_UNUSEDARTICLES_NUM =
            "SELECT COUNT(*) FROM TBL_Article where Status = 0 and columnID = ? and emptycontentflag = 0";

    //未用文章总数(unusedarticle.jsp)
    public int getUnusedArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UNUSEDARTICLES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_UNUSEDARTICLES_LIST =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,lockstatus,lockeditor,doclevel," +
                    "pubflag,auditflag,auditor,filename,publishtime,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article " +
                    "where Status = 0 and columnID = ? order by publishtime desc";

    //未用文章列表(unusedarticle.jsp)
    public List getUnusedArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_UNUSEDARTICLES_LIST);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadArticleList(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_ARCHIVEARTICLES_NUM =
            "SELECT COUNT(*) FROM TBL_Article where Status = 2 and columnID = ?";

    //归档文章总数(archivearticle.jsp)
    public int getArchiveArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARCHIVEARTICLES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_ARCHIVEARTICLES_LIST =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,lockstatus,lockeditor,doclevel," +
                    "pubflag,auditflag,auditor,filename,publishtime,status,viceTitle,dirName,urltype,defineurl,createdate FROM tbl_article " +
                    "where Status = 2 and columnID = ? order by publishtime desc";

    //归档文章列表(archivearticle.jsp)
    public List getArchiveArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARCHIVEARTICLES_LIST);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadArticleList(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int getArticleNumByFlag(int columnID, int noContent, int flag) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL = "SELECT count(ID) FROM tbl_article where (auditflag=?) and " + cidString + " and emptycontentflag=0";

        if (noContent == 0) {                         //emptycontentflag=1表示文章内容为空
            strSQL = "SELECT count(ID) FROM tbl_article where (auditflag=?) and " + cidString + " and emptycontentflag=1 ";
        } else if (noContent == 1) {                   //emptycontentflag=0表示文章内容非空
            strSQL = "SELECT count(ID) FROM tbl_article where (auditflag=?) and " + cidString + " and emptycontentflag=0";
        } else if (noContent == 2) {                   //不考虑文章内容是否为空，取出该栏目下的所有文章
            strSQL = "SELECT count(ID) FROM tbl_article where (auditflag=?) and " + cidString;
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, flag);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getArticlesByFlag(int columnID, int startIndex, int numResults, int noContent, int flag) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                        "auditflag,auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where " +
                        "(auditflag = ?) and status = 1 and pubflag = 1 and " +
                        cidString + " and emptycontentflag=0 order by publishtime desc";

        if (noContent == 0)       //emptycontentflag=1表示内容为空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                    "auditflag,auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where " +
                    "(auditflag=?) and status=1 and pubflag=1 and " +
                    cidString + " and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 1)     //emptycontentflag=0表示内容非空
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                    "auditflag,auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where " +
                    "(auditflag = ?) and status = 1 and pubflag = 1 and " +
                    cidString + " and emptycontentflag=0 order by publishtime desc";
        } else if (noContent == 2)    //不考虑内容是否为空，取出该栏目下的所有文章
        {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                    "auditflag,auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where " +
                    "(auditflag=?) and status=1 and pubflag=1 and " +
                    cidString + " order by publishtime desc";
        }

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, flag);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    private static final String SQL_GET_AUDIT_ARTICLES_LIST =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                    "auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where auditflag = 1 and status = 1 " +
                    "and columnID = ? order by publishtime desc";

    //待审核文章列表(/article/auditarticle.jsp)
    public List getAuditArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_AUDIT_ARTICLES_LIST);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_AUDIT_ARTICLES_NUM =
            "SELECT COUNT(*) FROM tbl_article where auditflag = 1 and status = 1 and columnID = ?";

    //待审核文章列表(/article/auditarticle.jsp)
    public int getAuditArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_AUDIT_ARTICLES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_TOPSTORIES_ARTICLES_LIST =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                    "auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where auditflag = 0 and status = 1 " +
                    "and columnID = ? and siteid=? order by publishtime desc";

    //为热点文章服务的文章列表(/templatex/articleList.jsp)
    public List getTopStoriesArticles(int columnID, int startIndex, int numResults, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_TOPSTORIES_ARTICLES_LIST);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    article.setIsown(true);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();

            String cidString = formatOracleClause(getColumnIDs(columnID));
            String selectColumns = "";
            IColumnManager columnManager = ColumnPeer.getInstance();
            if (cidString.indexOf(" ") == -1) {
                cidString = null;
            }
            List selectColumnsList = columnManager.getRefersColumnIds(cidString);
            for (int i = 0; i < selectColumnsList.size(); i++) {
                Column scolumn = (Column) selectColumnsList.get(i);
                selectColumns = selectColumns + scolumn.getScid() + ",";
            }
            if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 0))
                selectColumns = selectColumns.substring(0, selectColumns.length() - 1);

            String ids = "";
            if ((selectColumns != null) && (!selectColumns.equals("") && (selectColumns.length() > 0))) {
                int listsize = list.size();
                if (20 - listsize > 0) {
                    String newSQL = "SELECT count(id) FROM tbl_article where auditflag = 0 and status = 1 and columnID = " + columnID;
                    int getarticlenum = 0;
                    pstmt = conn.prepareStatement(newSQL);
                    rs = pstmt.executeQuery();
                    if (rs.next())
                        getarticlenum = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    newSQL = "select articleid from tbl_refers_article where scolumnid in (" + selectColumns + ") and status=1 and " +
                            "auditflag=0";
                    pstmt = conn.prepareStatement(newSQL);
                    rs = pstmt.executeQuery();
                    if (startIndex < getarticlenum) {
                        for (int i = 0; i < 20 - listsize; i++) {
                            if (rs.next())
                                ids += rs.getInt(1) + ",";
                            else
                                break;
                        }
                    } else {
                        for (int i = 0; i < startIndex - getarticlenum; i++) {
                            rs.next();
                        }
                        for (int i = 0; i < numResults; i++) {
                            if (rs.next())
                                ids += rs.getInt(1) + ",";
                            else
                                break;
                        }
                    }
                    rs.close();
                    pstmt.close();

                    if (ids.length() > 1) {
                        if (ids.endsWith(","))
                            ids = ids.substring(0, ids.length() - 1);
                    } else {
                        ids = "0";
                    }

                    newSQL = "select maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                            "auditor,publishtime,filename,urltype,defineurl,siteid from tbl_article where id in (" + ids + ") " +
                            "order by publishtime desc";

                    pstmt = conn.prepareStatement(newSQL);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        article = loadBriefContent(rs);
                        article.setIsown(false);
                        list.add(article);
                    }
                    rs.close();
                    pstmt.close();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_TOPSTORIES_ARTICLES_NUM =
            "SELECT COUNT(id) FROM tbl_article where auditflag = 0 and status = 1 and columnID = ? and siteid=?";

    //为热点文章服务的文章列表(/templatex/articleList.jsp)
    public int getTopStoriesArticlesNum(int columnID, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_TOPSTORIES_ARTICLES_NUM);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            String cidString = formatOracleClause(getColumnIDs(columnID));
            String selectColumns = "";
            IColumnManager columnManager = ColumnPeer.getInstance();
            List selectColumnsList = columnManager.getRefersColumnIds(cidString);
            for (int i = 0; i < selectColumnsList.size(); i++) {
                Column scolumn = (Column) selectColumnsList.get(i);
                selectColumns = selectColumns + scolumn.getScid() + ",";
            }
            if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 0))
                selectColumns = selectColumns.substring(0, selectColumns.length() - 1);

            if ((selectColumns != null) && (!selectColumns.equals("") && (selectColumns.length() > 0))) {
                String newSQL = "select count(id) from tbl_refers_article where scolumnid in (" + selectColumns + ") and" +
                        " status=1 and auditflag=0";

                pstmt = conn.prepareStatement(newSQL);
                rs = pstmt.executeQuery();
                if (rs.next())
                    count = count + rs.getInt(1);
                rs.close();
                pstmt.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_RELATED_ARTICLES_LIST =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                    "auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where status = 1 and columnID = ?" +
                    " and siteid=? order by publishtime desc";

    //用于选择相关性文章的文章列表(/article/addRelatedArticleRight.jsp)
    public List getRelatedArticles(int columnID, int startIndex, int numResults, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_RELATED_ARTICLES_LIST);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_RELATED_ARTICLES_NUM =
            "SELECT COUNT(*) FROM tbl_article where status = 1 and columnID = ? and siteid=?";

    //用于选择相关性文章的文章总数(/article/addRelatedArticleRight.jsp)
    public int getRelatedArticlesNum(int columnID, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_RELATED_ARTICLES_NUM);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_REFER_ARTICLES_LIST =
            "SELECT maintitle,emptycontentflag,editor,id,columnid,publishtime,urltype,defineurl FROM tbl_article " +
                    "where status = 1 and auditflag=0 and columnID = ? and referID = 0 order by publishtime desc";

    //用于选择相文章引用的文章列表(/article/referArticleRight.jsp)
    public List getReferArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFER_ARTICLES_LIST);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = new Article();
                    article.setID(rs.getInt("id"));
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setNullContent(rs.getInt("emptycontentflag"));
                    article.setEditor(rs.getString("editor"));
                    article.setColumnID(rs.getInt("columnID"));
                    article.setPublishTime(rs.getTimestamp("publishtime"));
                    article.setUrltype(rs.getInt("urltype"));
                    article.setOtherurl(rs.getString("defineurl"));
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {

                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static final String SQL_GET_REFER_ARTICLES_NUM =
            "SELECT COUNT(id) FROM tbl_article where status = 1 and auditflag=0 and columnID = ? and referID = 0";

    //用于选择文章应用的文章总数(/article/referArticleRight.jsp)
    public int getReferArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFER_ARTICLES_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static String SQL_GET_AUDIT_ARTICLESFILES_LIST = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
            "auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where auditflag = 1 and status = 1 " +
            "and columnID = ? and siteid=? and processofaudit=? order by publishtime desc";


    //待审核文章及文件列表(/audit/articles.jsp)
    public List getAuditArticlesFiles(String auditor, int columnID, int startIndex, int numResults, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Article article=null;
        auditor= "[" + auditor + "]";
        IAuditManager auditMgr = AuditPeer.getInstance();

        //读出用户的审核范围
        IUserManager uMgr = UserPeer.getInstance();
        User user = null;
        List audits = null;
        String current_subrules = null;
        int audit_step = 0;
        try {
            audits = auditMgr.getAuditSubRules(columnID);
            //查看当前审核者是处在审核流程中的第几个审核人
            for (int ii=0; ii<audits.size(); ii++) {
                current_subrules = (String)audits.get(ii);
                if (current_subrules.indexOf(auditor) > -1) {
                    audit_step = ii;
                    break;
                }
            }
            user = uMgr.getUser(auditor);
        } catch (CmsException e) {
        }

        if (user != null && user.getDepartmentarticlestype() == 1) {
            //用户可以审核本部门的文章
            List userList = uMgr.getUserInfoByDepartmentIds(user.getDepartment());
            String userids = "";
            for (int i = 0; i < userList.size(); i++) {
                User u = (User) userList.get(i);
                if (u != null) {
                    if (i == userList.size() - 1) {
                        userids += u.getUserID();
                    } else {
                        userids += u.getUserID() + "','";
                    }
                }
            }
            SQL_GET_AUDIT_ARTICLESFILES_LIST = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                    "auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where auditflag = 1 and status = 1 " +
                    "and columnID = ? and siteid=? and editor in('" + userids + "') and processofaudit=? order by publishtime desc";
        } else if (user != null && user.getDepartmentarticlestype() == 2) {
            //用户可以审核指定部门的文章
            List userList = uMgr.getUserInfoByDepartmentIds(user.getDepartmentarticlesids());
            String userids = "";
            for (int i = 0; i < userList.size(); i++) {
                User u = (User) userList.get(i);
                if (u != null) {
                    if (i == userList.size() - 1) {
                        userids += u.getUserID();
                    } else {
                        userids += u.getUserID() + "','";
                    }
                }
            }
            SQL_GET_AUDIT_ARTICLESFILES_LIST = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                    "auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where auditflag = 1 and status = 1 " +
                    "and columnID = ? and siteid=? and editor in('" + userids + "') and processofaudit=? order by publishtime desc";
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_AUDIT_ARTICLESFILES_LIST);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            pstmt.setInt(3, audit_step);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_AUDIT_ARTICLESFILES_NUM =
            "SELECT COUNT(*) FROM tbl_article where auditflag = 1 and status = 1 and columnID = ? and siteid=? and auditor=?";

    //待审核文章及文件总数(/audit/articles.jsp)
    public int getAuditArticlesFilesNum(String auditor, int columnID, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;
        auditor = "[" + auditor + "]";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_AUDIT_ARTICLESFILES_NUM);

            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            pstmt.setString(3, auditor);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //退稿文章列表
    public List getBackArticles(int columnID, String userID, int startIndex, int numResults, int type) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        String SQL_GET_BACK_ARTICLE = "";
        if (type == 0)                            //退给审核人的文章及文件
        {
            SQL_GET_BACK_ARTICLE =
                    "SELECT DISTINCT a.MainTitle,a.emptycontentflag,a.Editor,a.ID,a.ColumnID,a.SortID,a.DocLevel," +
                            "a.PubFlag,a.AuditFlag,a.Auditor,a.PublishTime,a.filename,a.urltype,a.defineurl,a.siteid FROM " +
                            "TBL_Article a,TBL_Article_Auditing_Info " +
                            "b WHERE a.ID = b.ArticleID AND a.Editor <> b.BackTo AND b.BackTo = ? AND a.AuditFlag = 2 AND " +
                            "a.Status = 1 AND a.PubFlag = 1 AND a.ColumnID = " + columnID + " AND b.Status = 2 ORDER BY a.PublishTime DESC";
        } else if (type == 1)                        //退给作者的文章EmptyContentFlag=0
        {
            SQL_GET_BACK_ARTICLE =
                    "SELECT DISTINCT a.MainTitle,a.emptycontentflag,a.Editor,a.ID,a.ColumnID,a.SortID," +
                            "a.DocLevel,a.PubFlag,a.AuditFlag,a.Auditor,a.PublishTime,a.filename,a.urltype,a.defineurl,a.siteid FROM TBL_Article a," +
                            "TBL_Article_Auditing_Info b WHERE a.ID = b.ArticleID AND a.Editor = b.BackTo " +
                            "AND b.BackTo = ? AND a.AuditFlag = 2 AND a.Status = 1 AND a.PubFlag = 1 " +
                            "AND a.ColumnID = " + columnID + " AND b.Status = 1 ORDER BY a.PublishTime DESC";
        } else if (type == 2)                        //退给作者的文件EmptyContentFlag=1
        {
            SQL_GET_BACK_ARTICLE =
                    "SELECT DISTINCT a.MainTitle,a.emptycontentflag,a.Editor,a.ID,a.ColumnID,a.SortID," +
                            "a.DocLevel,a.PubFlag,a.AuditFlag,a.Auditor,a.PublishTime,a.filename,a.urltype,a.defineurl,a.siteid FROM " +
                            "TBL_Article a,TBL_Article_Auditing_Info b WHERE a.ID = b.ArticleID " +
                            "AND a.Editor = b.BackTo AND b.BackTo = ? AND a.AuditFlag = 2 AND " +
                            "a.Status = 1 AND a.PubFlag = 1 AND a.ColumnID = " + columnID +
                            " AND a.EmptyContentFlag = 1 AND b.Status = 1 ORDER BY a.PublishTime DESC";
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_BACK_ARTICLE);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //退稿文章总数
    public int getBackArticlesNum(int columnID, String userID, int type) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String SQL_GET_BACK_ARTICLE_NUM = "";
        if (type == 0)                        //退给审核人的文章及文件
        {
            SQL_GET_BACK_ARTICLE_NUM =
                    "SELECT COUNT(DISTINCT a.id) AS count FROM TBL_Article a,TBL_Article_Auditing_Info " +
                            "b WHERE a.ID = b.ArticleID AND a.Editor <> b.BackTo AND b.BackTo = ? AND a.AuditFlag = 2 " +
                            "AND a.Status = 1 AND a.PubFlag = 1 AND a.ColumnID = " + columnID + " AND b.Status = 2";
        } else if (type == 1)                   //退给作者的文章 EmptyContentFlag = 0
        {
            SQL_GET_BACK_ARTICLE_NUM =
                    "SELECT COUNT(DISTINCT a.id) FROM TBL_Article a,TBL_Article_Auditing_Info " +
                            "b WHERE a.ID = b.ArticleID AND a.Editor = b.BackTo AND b.BackTo = ? AND a.AuditFlag = 2 " +
                            "AND a.Status = 1 AND a.PubFlag = 1 AND a.ColumnID = " + columnID + " AND b.Status = 1";
        } else if (type == 2)                   //退给作者的文件 EmptyContentFlag = 1
        {
            SQL_GET_BACK_ARTICLE_NUM =
                    "SELECT COUNT(DISTINCT a.id) FROM TBL_Article a,TBL_Article_Auditing_Info " +
                            "b WHERE a.ID = b.ArticleID AND a.Editor = b.BackTo AND b.BackTo = ? AND a.AuditFlag = 2 " +
                            "AND a.Status = 1 AND a.PubFlag = 1 AND a.ColumnID = " + columnID + " AND a.EmptyContentFlag = 1 AND b.Status = 1";
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_BACK_ARTICLE_NUM);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public int getArticleNumByPubFlag(int columnID, int noContent, int flag) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL = "SELECT count(ID) FROM tbl_article where (pubflag=?) and " + cidString + " and emptycontentflag=0";

        if (noContent == 0) {              //emptycontentflag=1表示取出内容为空的文章
            strSQL = "SELECT count(ID) FROM tbl_article where (pubflag=?) and " + cidString + " and emptycontentflag=1 ";
        } else if (noContent == 1) {        //emptycontentflag=0表示取出内容非空的文章
            strSQL = "SELECT count(ID) FROM tbl_article where (pubflag=?) and " + cidString + " and emptycontentflag=0";
        } else if (noContent == 2) {         //不考虑内容是否为空，取出某个栏目下的所有文章
            strSQL = "SELECT count(ID) FROM tbl_article where (pubflag=?) and " + cidString;
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, flag);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getArticlesByPubFlag(int columnID, int startIndex, int numResults, int noContent, int flag) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String strSQL =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag,auditor," +
                        "publishtime,filename,urltype,defineurl,siteid FROM tbl_article where (pubflag=?) and " + cidString +
                        " and emptycontentflag=0 order by publishtime desc";


        if (noContent == 0) {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag,auditor," +
                    "publishtime,filename,urltype,defineurl,siteid FROM tbl_article where (pubflag=?) and " + cidString + " and emptycontentflag=1 order by publishtime desc";
        } else if (noContent == 1) {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag,auditor," +
                    "publishtime,filename,urltype,defineurl,siteid FROM tbl_article where (pubflag=?) and " + cidString + " and emptycontentflag=0 order by publishtime desc";
        } else if (noContent == 2) {
            strSQL = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag,auditor," +
                    "publishtime,filename,urltype,defineurl,siteid FROM tbl_article where (pubflag=?) and " + cidString + " order by publishtime desc";
        }

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, flag);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //发布失败文章总数(/publish/publishfailed.jsp)
    public int getPublishFailArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        if (columnID > 0) {
            String cidString = formatOracleClause(getColumnIDs(columnID));
            String SQL_GET_PUBLISHFAIL_ARTICLES =
                    "SELECT COUNT(*) FROM tbl_article where pubflag = 2 and " + cidString + " and emptycontentflag = 0";
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_GET_PUBLISHFAIL_ARTICLES);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        return count;
    }

    //发布失败文章列表(/publish/publishfailed.jsp)
    public List getPublishFailArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String SQL_GET_PUBLISHFAIL_ARTICLES =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                        "auditflag,auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where pubflag = 2 and " +
                        cidString + " and emptycontentflag = 0 order by publishtime desc";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_PUBLISHFAIL_ARTICLES);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //为自动发布获取本站点所有需要发布的文章
    private static final String SQL_GET_AUTOPUB_ARTICLES =
            "SELECT a.ID,a.MainTitle,a.PublishTime,a.ColumnID FROM TBL_Article a,TBL_Column b " +
                    "WHERE a.Status=1 AND a.EmptycontentFlag=0 AND a.PubFlag=1 AND a.AuditFlag=0 " +
                    "AND a.ColumnID=b.ID AND b.SiteID=?";

    private static final String SQL_UPDATE_AUTOPUB_ARTICLES = "UPDATE TBL_Article SET PubFlag = 0 WHERE ID = ?";

    //读取本站点引用的所有文章
    private static final String strSQL2 = "SELECT a.ID,b.ColumnID,b.scolumnid,a.MainTitle,a.PublishTime,a.dirName," +
            "a.urltype,a.defineurl from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and b.pubflag=1 " +
            "AND b.usearticletype=1 AND b.siteid=? ORDER BY a.createdate DESC";

    private static final String SQL_UPDATE_REFERS_ARTICLES = "UPDATE tbl_refers_article SET PubFlag = 0 WHERE articleid = ? " +
            "and columnid=? and siteid=?";

    private static final String SQL_INSERT_AUTOPUB_ARTICLES = "INSERT INTO TBL_Publish_Queue (ID, SiteID, Type) VALUES (?, ?, 1)";

    public List getPublishArticles(int siteID) throws ArticleException {
        List list = new ArrayList();
        List rlist = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int articleID;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_GET_AUTOPUB_ARTICLES);     //得到所有符合条件的需要发布的文章
                pstmt.setInt(1, siteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    list.add(loadForAutopub(rs, 0));
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_UPDATE_AUTOPUB_ARTICLES);  //更新文章为发布失败
                for (int i = 0; i < list.size(); i++) {
                    articleID = ((Article) list.get(i)).getID();
                    pstmt.setInt(1, articleID);
                    pstmt.executeUpdate();
                }
                pstmt.close();

                pstmt = conn.prepareStatement(strSQL2);       //获取本站点引用的所有需要更新发布状态的文章
                pstmt.setInt(1, siteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    rlist.add(loadForAutopub(rs, 1));
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_UPDATE_REFERS_ARTICLES);  //更新本站点引用文章的发布状态
                for (int i = 0; i < rlist.size(); i++) {
                    Article article = (Article) rlist.get(i);
                    articleID = article.getID();
                    pstmt.setInt(1, articleID);
                    pstmt.setInt(2, article.getColumnID());
                    pstmt.setInt(3, siteID);
                    pstmt.executeUpdate();
                }
                pstmt.close();

                //合并本站点待发布的文章和本站点引用的待发布的文章
                for (int i = 0; i < rlist.size(); i++) {
                    Article article = (Article) rlist.get(i);
                    list.add(article);
                }

                pstmt = conn.prepareStatement(SQL_INSERT_AUTOPUB_ARTICLES);  //文章进入发布队列
                for (int i = 0; i < list.size(); i++) {
                    articleID = ((Article) list.get(i)).getID();
                    pstmt.setInt(1, articleID);
                    pstmt.setInt(2, siteID);
                    pstmt.executeUpdate();
                }
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }
        return list;
    }

    public List getArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        String cidString = formatOracleClause(getColumnIDs(columnID));
        String SQL_GETARTICLES =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag,auditor," +
                        "publishtime,filename,urltype,defineurl,siteid FROM tbl_article WHERE status=0 and " + cidString +
                        " order by publishtime desc";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    //读取当前栏目文章
    private static final String SQL_GET_REPUBLISH_ARTICLES =
            "SELECT ID,ColumnID,MainTitle,Editor,LastUpdated,urltype,defineurl,siteid FROM TBL_Article WHERE (status = 1 OR status=4 OR status=5 OR status=6) " +
                    "AND AuditFlag = 0 AND Emptycontentflag = 0 AND ColumnID = ? Order BY PublishTime DESC";

    //读取当前栏目的栏目模板
    private static final String SQL_GET_REPUBLISH_TEMPLATES =
            "SELECT ID,ColumnID,IsArticle,Editor,LastUpdated,DefaultTemplate,ChName,siteid FROM " +
                    "TBL_Template WHERE IsArticle IN (0,2,3) AND ColumnID = ? ORDER BY LastUpdated DESC";

    //用于重新发布的文章及模板列表(/publish/republish.jsp)
    public List getRePublishArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        List tempList = new ArrayList();
        Article article=null;

        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement(SQL_GET_REPUBLISH_TEMPLATES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadBriefContent(rs, true, 1);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_REPUBLISH_ARTICLES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadBriefContent(rs, false, 0);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            for (int i = startIndex; i < startIndex + numResults; i++) {
                if (i > tempList.size() - 1) break;
                article = (Article) tempList.get(i);
                list.add(article);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_REPUBLISH_ARTICLE_NUM =
            "SELECT COUNT(*) FROM TBL_Article WHERE Status = 1 AND AuditFlag = 0 AND Emptycontentflag = 0 AND ColumnID = ?";

    private static final String SQL_GET_REPUBLISH_TEMPLATE_NUM =
            "SELECT COUNT(*) FROM TBL_Template WHERE ColumnID = ? AND IsArticle IN (0,2,3)";

    //用于重新发布的文章及模板总数(/publish/republish.jsp)
    public int getRePublishArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REPUBLISH_ARTICLE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_REPUBLISH_TEMPLATE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getLinkArticles(int columnID, int startIndex, int numResults,int modeltype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        List tempList = new ArrayList();
        Article article=null;

        try {
            conn = cpool.getConnection();

            //获取引用栏目的文章ID
            String SQL_GET_ARTICLEID_IN_OTHERCOLUMN = "SELECT ArticleID,columnid FROM TBL_Refers_Article where columnid=? and pubflag = 0";

            String aidString = "";
            List referedArticleColumnid = new ArrayList();                    //用于记录是否被引用的文章
            mapOfReferedArticleColumn mapAC = null;                           //引用文章的ID和引用这篇文章的栏目的栏目ID之间的关系

            pstmt = conn.prepareStatement(SQL_GET_ARTICLEID_IN_OTHERCOLUMN);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                aidString = aidString + rs.getInt(1) + ",";
                mapAC = new mapOfReferedArticleColumn();
                mapAC.setArticleID(rs.getInt("ArticleID"));
                mapAC.setColumnID(rs.getInt("columnid"));
                referedArticleColumnid.add(mapAC);
            }
            rs.close();
            pstmt.close();

            if (aidString.length() > 0) {
                aidString = aidString.substring(0, aidString.length() - 1);
                aidString = "(" + formatOracleClause(aidString, 0) + ")";
            } else {
                aidString = "(0)";
            }

            //读取当前栏目的栏目模板
            String SQL_GET_LINK_TEMPLATES = "SELECT ID,ColumnID,IsArticle,Editor,LastUpdated,DefaultTemplate," +
                    "ChName,TemplateName,siteid FROM TBL_Template WHERE IsArticle IN (0,2,3) AND " +
                    "ColumnID = ? ORDER BY LastUpdated DESC";

            //读取当前栏目文章
            String SQL_GET_LINK_ARTICLES = "SELECT ID,ColumnID,MainTitle,Emptycontentflag,Editor,LastUpdated,FileName,CreateDate,urltype,defineurl,siteid FROM " +
                    "TBL_Article WHERE Status = 1 AND ColumnID = ? OR id in " + aidString + " Order BY PublishTime DESC";

            pstmt = conn.prepareStatement(SQL_GET_LINK_ARTICLES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadLinkedArticle(rs, false);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_LINK_TEMPLATES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadLinkedArticle(rs, true);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            for (int i = startIndex; i < startIndex + numResults; i++) {
                if (i > tempList.size() - 1) break;
                article = (Article) tempList.get(i);
                list.add(article);
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //用于链接的文章及模板总数(/template/listarticle.jsp)
    public int getLinkArticlesNum(int columnID,int modeltype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        try {
            conn = cpool.getConnection();

            //获取引用栏目的文章ID
            String SQL_GET_ARTICLEID_IN_OTHERCOLUMN = "SELECT ArticleID,columnid FROM TBL_Refers_Article where columnid=? and pubflag = 0";

            String aidString = "";
            List referedArticleColumnid = new ArrayList();                    //用于记录是否被引用的文章
            mapOfReferedArticleColumn mapAC = null;                           //引用文章的ID和引用这篇文章的栏目的栏目ID之间的关系

            pstmt = conn.prepareStatement(SQL_GET_ARTICLEID_IN_OTHERCOLUMN);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                aidString = aidString + rs.getInt(1) + ",";
                mapAC = new mapOfReferedArticleColumn();
                mapAC.setArticleID(rs.getInt("ArticleID"));
                mapAC.setColumnID(rs.getInt("columnid"));
                referedArticleColumnid.add(mapAC);
            }
            rs.close();
            pstmt.close();

            if (aidString.length() > 0) {
                aidString = aidString.substring(0, aidString.length() - 1);
                aidString = "(" + formatOracleClause(aidString, 0) + ")";
            } else {
                aidString = "(0)";
            }

            String SQL_GET_LINK_TEMPLATE_NUM = "SELECT COUNT(*) FROM TBL_Template WHERE ColumnID = ? AND IsArticle IN (0,2,3)";

            String SQL_GET_LINK_ARTICLE_NUM =
                    "SELECT COUNT(id) FROM TBL_Article WHERE Status = 1 AND ColumnID = ? OR id in " + aidString;

            pstmt = conn.prepareStatement(SQL_GET_LINK_ARTICLE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_LINK_TEMPLATE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getLinkSearchArticles(int columnID, int startIndex, int numResults, String keyword, int modelType) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        List tempList = new ArrayList();
        Article article=null;

        try {
            conn = cpool.getConnection();

            //获取引用栏目的文章ID
            String SQL_GET_ARTICLEID_IN_OTHERCOLUMN = "SELECT ArticleID,columnid FROM TBL_Refers_Article where columnid=? and pubflag = 0";

            String aidString = "";
            List referedArticleColumnid = new ArrayList();                    //用于记录是否被引用的文章
            mapOfReferedArticleColumn mapAC = null;                           //引用文章的ID和引用这篇文章的栏目的栏目ID之间的关系

            pstmt = conn.prepareStatement(SQL_GET_ARTICLEID_IN_OTHERCOLUMN);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                aidString = aidString + rs.getInt(1) + ",";
                mapAC = new mapOfReferedArticleColumn();
                mapAC.setArticleID(rs.getInt("ArticleID"));
                mapAC.setColumnID(rs.getInt("columnid"));
                referedArticleColumnid.add(mapAC);
            }
            rs.close();
            pstmt.close();

            if (aidString.length() > 0) {
                aidString = aidString.substring(0, aidString.length() - 1);
                aidString = "(" + formatOracleClause(aidString, 0) + ")";
            } else {
                aidString = "(0)";
            }

            //读取当前栏目的栏目模板
            String SQL_GET_LINK_TEMPLATES = "SELECT ID,ColumnID,IsArticle,Editor,LastUpdated,DefaultTemplate," +
                    "ChName,TemplateName,siteid FROM TBL_Template WHERE IsArticle IN (0,2,3) AND " +
                    "ColumnID = ? ORDER BY LastUpdated DESC";

            //读取当前栏目文章
            String SQL_GET_LINK_ARTICLES = "SELECT ID,ColumnID,MainTitle,Emptycontentflag,Editor,LastUpdated,FileName,CreateDate,urltype,defineurl,siteid FROM " +
                    "TBL_Article WHERE Status = 1 AND maintitle like '%" + keyword + "%'AND ColumnID = ? OR id in " + aidString + " Order BY PublishTime DESC";

            pstmt = conn.prepareStatement(SQL_GET_LINK_ARTICLES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadLinkedArticle(rs, false);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_LINK_TEMPLATES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadLinkedArticle(rs, true);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            for (int i = startIndex; i < startIndex + numResults; i++) {
                if (i > tempList.size() - 1) break;
                article = (Article) tempList.get(i);
                list.add(article);
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int getLinkSearchArticlesNum(int columnID, String keyword, int modelType) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        try {
            conn = cpool.getConnection();

            //获取引用栏目的文章ID
            String SQL_GET_ARTICLEID_IN_OTHERCOLUMN = "SELECT ArticleID,columnid FROM TBL_Refers_Article where columnid=? and pubflag = 0";

            String aidString = "";
            List referedArticleColumnid = new ArrayList();                    //用于记录是否被引用的文章
            mapOfReferedArticleColumn mapAC = null;                           //引用文章的ID和引用这篇文章的栏目的栏目ID之间的关系

            pstmt = conn.prepareStatement(SQL_GET_ARTICLEID_IN_OTHERCOLUMN);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                aidString = aidString + rs.getInt(1) + ",";
                mapAC = new mapOfReferedArticleColumn();
                mapAC.setArticleID(rs.getInt("ArticleID"));
                mapAC.setColumnID(rs.getInt("columnid"));
                referedArticleColumnid.add(mapAC);
            }
            rs.close();
            pstmt.close();

            if (aidString.length() > 0) {
                aidString = aidString.substring(0, aidString.length() - 1);
                aidString = "(" + formatOracleClause(aidString, 0) + ")";
            } else {
                aidString = "(0)";
            }

            String SQL_GET_LINK_TEMPLATE_NUM = "SELECT COUNT(*) FROM TBL_Template WHERE ColumnID = ? AND IsArticle IN (0,2,3)";

            String SQL_GET_LINK_ARTICLE_NUM = "SELECT COUNT(id) FROM TBL_Article WHERE Status = 1 AND maintitle like '%" + keyword + "%' AND ColumnID = ? OR id in " + aidString;

            pstmt = conn.prepareStatement(SQL_GET_LINK_ARTICLE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_LINK_TEMPLATE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getLinkForXuanImages(int columnID, int startIndex, int numResults, String content, int modeltype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        List tempList = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();

            //读取当前栏目文章
            String SQL_GET_LINK_ARTICLES = "SELECT ID,ColumnID,MainTitle,Emptycontentflag,Editor,LastUpdated,FileName,CreateDate,urltype,defineurl,siteid FROM " +
                    "TBL_Article WHERE Status = 1 and emptycontentflag=0 AND ColumnID = ? Order BY PublishTime DESC";

            pstmt = conn.prepareStatement(SQL_GET_LINK_ARTICLES);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadLinkedArticle(rs, false);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            for (int i = startIndex; i < startIndex + numResults; i++) {
                if (i > tempList.size() - 1) break;
                article = (Article) tempList.get(i);
                list.add(article);
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //用于链接的文章及模板总数(/template/listarticle.jsp)
    public int getLinkForXuanImagesNum(int columnID, String content, int modeltype) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();

            String SQL_GET_LINK_ARTICLE_NUM =
                    "SELECT COUNT(id) FROM TBL_Article WHERE Status = 1 and emptycontentflag=0 and ColumnID = ?";

            pstmt = conn.prepareStatement(SQL_GET_LINK_ARTICLE_NUM);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public int getArticleCountIncludeSubColumn(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            String cidString = formatOracleClause(getColumnIDs(columnID));

            final String SQL_GETColArtNum =
                    "SELECT count(ID) FROM tbl_article where status = 1 and " + cidString + " and emptycontentflag = 0";

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETColArtNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public String getColumnIDs(int siteid, int samsiteid, int columnID) {
        String result = "(";
        Tree colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid, samsiteid);
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID, samsiteid);
        for (int i = 0; i < cid[0] - 1; i++) {
            result = result + cid[i + 1] + ",";
        }
        result = result + cid[cid[0]] + ")";

        return result;
    }

    private int[] getSubTreeColumnIDList(node[] treenodes, int columnID, int samsiteid) {
        int[] cid = new int[treenodes.length + 1];
        int[] pid = new int[treenodes.length];
        int nodenum = 1;
        int i;
        int j = 1;

        pid[1] = columnID;

        do {
            columnID = pid[nodenum];
            cid[j] = columnID;
            j = j + 1;
            nodenum = nodenum - 1;

            for (i = 1; i < treenodes.length - 1; i++) {
                if (treenodes[i].getLinkPointer() == columnID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treenodes[i].getId();
                }
            }
        } while (nodenum >= 1);

        cid[0] = j - 1;            //cid[0]元素保存找到的子节点的数目

        return cid;
    }

    public int getArticleCountIncludeSubColumnBySamsite(int siteid, int samsiteid, int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            String cidString = formatOracleClause(getColumnIDs(siteid, samsiteid, columnID));

            final String SQL_GETColArtNum =
                    "SELECT count(ID) FROM tbl_article where status = 1 and " + cidString + " and emptycontentflag = 0";

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETColArtNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public String formatOracleClause(String cidString, int flag) {
        StringBuffer sb = new StringBuffer();

        if (cpool.getType().equalsIgnoreCase("oracle")) {
            String cid[] = cidString.substring(1, cidString.length() - 1).split(",");
            if (cid.length >= 1000) {
                int item = cid.length / 1000;
                if (cid.length % 1000 > 0) item++;

                for (int i = 0; i < item; i++) {
                    StringBuffer s = new StringBuffer();
                    for (int j = i * 1000; j < (i + 1) * 1000; j++) {
                        if (j == cid.length - 1) break;
                        s.append(cid[j] + ",");
                    }

                    String temp = s.toString();
                    if (temp.length() > 0) temp = temp.substring(0, temp.length() - 1);
                    sb.append(" OR ID IN (" + temp + ")");
                }

                String temp = sb.toString();
                sb = new StringBuffer();
                if (temp.length() > 0) temp = "(" + temp.trim().substring(2) + ")";
                sb.append(temp);
            } else {
                sb.append(cidString);
            }
        } else {
            sb.append(cidString);
        }

        return sb.toString();
    }

    public String formatOracleClause(String cidString) {
        StringBuffer sb = new StringBuffer();

        if (cpool.getType().equalsIgnoreCase("oracle")) {

            String cid[] = cidString.substring(1, cidString.length() - 1).split(",");
            if (cid.length >= 1000) {
                int item = cid.length / 1000;
                if (cid.length % 1000 > 0) item++;

                for (int i = 0; i < item; i++) {
                    StringBuffer s = new StringBuffer();
                    for (int j = i * 1000; j < (i + 1) * 1000; j++) {
                        if (j == cid.length - 1) break;
                        s.append(cid[j] + ",");
                    }

                    String temp = s.toString();
                    if (temp.length() > 0) temp = temp.substring(0, temp.length() - 1);
                    sb.append(" OR columnID IN (" + temp + ")");
                }

                String temp = sb.toString();
                sb = new StringBuffer();
                if (temp.length() > 0) temp = "(" + temp.trim().substring(2) + ")";
                sb.append(temp);
            } else {
                sb.append("columnID IN " + cidString);
            }

        } else {
            sb.append("columnID IN " + cidString);
        }

        return sb.toString();
    }

    private static final String SQL_GETARTICLECOUNT =
            "SELECT count(ID) FROM tbl_article where (status =0)and(columnID in (select id from tbl_column start with id = ? " +
                    "connect by prior id=parentid)) ";

    public int getArticleCount(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLECOUNT);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETARTICLECOUNT1 =
            "SELECT count(ID) FROM tbl_article where (sysdate-createdate<=?)" +
                    "and(status =0)and(columnID  = ?)";

    public int getArticleCount(int columnID, int days) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLECOUNT1);
            pstmt.setInt(1, days - 1);
            pstmt.setInt(2, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETARTICLECOUNT2 =
            "SELECT count(ID) FROM tbl_article where (PartnerID=?)and(sysdate-createdate<=?)" +
                    "and(status =0)and(columnID  = ?)";

    public int getArticleCount(int columnID, int partnerID, int days) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLECOUNT2);
            pstmt.setInt(3, columnID);
            pstmt.setInt(1, partnerID);
            pstmt.setInt(2, days - 1);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETSEARCHCOUNT =
            "SELECT count(*) FROM tbl_article WHERE status =0 and ( maintitle like ? or contains(content, ?) >0 ) ";

    public int getSearchArticleCount(String keyword) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        keyword = StringUtil.iso2gb(keyword);
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSEARCHCOUNT);
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, keyword);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ArticleException("Database exception: get article count failed.");
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETMAXORDER =
            "SELECT MAX(sortID) FROM tbl_article WHERE columnID = ?";

    public int getNextOrder(int columnID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int order = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETMAXORDER);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                order = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return order + 1;
    }

    private static final String SQL_GETMINORDER =
            "SELECT MIN(sortID) FROM tbl_article WHERE columnID=? ";

    int getLastOrder(int columnID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int order = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETMINORDER);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                order = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return order - 1;
    }

    //for related article list
    public List getArticles(String cidString, String keyWords, int num) throws ArticleException {
        String SQLStr =
                "SELECT id FROM tbl_article where columnid in " + cidString + " and (" + keyWords + ") " +
                        "and (status=1 or status=2) and auditflag=0 order by id desc";

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();

        try {
            int i = 0;
            String ids = "";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();
            while (rs.next() && i < num) {
                ids += rs.getInt(1) + ",";
                i++;
            }
            rs.close();
            pstmt.close();

            if (ids.length() > 0)
                ids = ids.substring(0, ids.length() - 1);
            else
                ids = "0";

            SQLStr =
                    "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author," +
                            "id,columnid,sortid,status,doclevel,dirname,filename,publishtime,RelatedArtID,referID,articlepic," +
                            "createdate,urltype,defineurl,downfilename,siteid FROM tbl_article where id IN (" + ids + ") order by id desc";
            pstmt = conn.prepareStatement(SQLStr);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load4Publish(rs));
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {

                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static final String SQL_GET_COLUMNID_STR = "SELECT ID FROM TBL_Column WHERE SiteID = ?";

    public String getColumnIDStr(int siteID) throws ArticleException {
        String str = "";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COLUMNID_STR);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                str = str + String.valueOf(rs.getInt(1)) + ",";
            }

            if (str.length() > 0) {
                str = str.substring(0, str.length() - 1);
                str = "(" + str + ")";
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return str;
    }

    public void getColumnURL(int columnID, StringBuffer buf) {
        String columnEName = null;
        int parentid = 0;
        IColumnManager columnManager = ColumnPeer.getInstance();
        try {
            Column column = columnManager.getColumn(columnID);
            parentid = column.getParentID();
            columnEName = column.getEName().trim();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        IColumnTree columnTree = ColumnTree.getInstance();
        if (columnTree.isRoot(columnID)) {
            buf.insert(0, "/");
            buf.insert(0, columnEName);
        } else {
            buf.insert(0, "/");
            buf.insert(0, columnEName);
            getColumnURL(parentid, buf);
        }
    }

    public int updatecancle(int articleID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 0;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_article set LockStatus=0,LockEditor='' where ID=?");
                pstmt.setInt(1, articleID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update lockstatus failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }

        return errcode;
    }

    public void updateIndexFlag(int articleID,int flag) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_article set indexflag=? where ID=?");
                pstmt.setInt(1,flag);
                pstmt.setInt(2, articleID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update lockstatus failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }
    }

    public void resetIndexFlag() throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_article set indexflag=0");
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update lockstatus failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }
    }

    public void setPublishFailedStatus(int articleID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_article set PubFlag = 2 where ID = ?");
                pstmt.setInt(1, articleID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update lockstatus failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }
    }

    public List getArticlesforPublishTopstories(int beginNum, int articleNum, String where) throws ArticleException {
        String sql =
                "SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword,author,editor,id," +
                        "columnid,sortid,doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber," +
                        "lockstatus,lockeditor,dirname,filename,publishtime,RelatedArtID,urltype,defineurl,clicknum,notearticleid,siteid FROM tbl_article " + where;

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for (int i = 0; i < beginNum - 1; i++)
                rs.next();

            for (int i = 0; i < articleNum; i++) {
                if (rs.next())
                    list.add(loadNoContent(rs));
                else
                    break;
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " +
                            e.toString());
                }
            }
        }
        return list;
    }

    public List getArticles(String columnIDs, int startnum, int numResults, boolean important, boolean lastest) throws ArticleException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT maintitle,emptycontentflag,vicetitle,source,summary,keyword," +
                "author,editor,id,columnid,sortid,doclevel,pubflag,auditflag,auditor," +
                "createdate,lastupdated,status,subscriber,lockstatus,lockeditor," +
                "dirname,filename,publishtime,RelatedArtID,urltype,defineurl,notearticleid,siteid FROM tbl_article WHERE status=1 and columnid in");

        sb.append(columnIDs);

        if (important) {
            sb.append(" and doclevel >= 1");
        }
        if (lastest) {
            sb.append(" order by publishtime desc");
        }

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Article article;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            for (int i = 0; i < startnum - 1; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadNoContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " +
                            e.toString());
                }
            }
        }
        return list;
    }

    //文章归档
    public void PigeonholeArticle(int columnID, int achieve, Timestamp bdate, Timestamp tdate, int includeSubCol,
                                  int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        String SQL_PIGEONHOLE_ARTICLE = "UPDATE TBL_Article SET Status = 2 WHERE Status = 1";

        if (includeSubCol == 0) {
            SQL_PIGEONHOLE_ARTICLE = SQL_PIGEONHOLE_ARTICLE + " AND ColumnID = ?";
        } else {
            Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
            node[] treeNodes = colTree.getAllNodes();
            int[] subColumnIDs = (new Tree()).getSubTreeColumnIDList(treeNodes, columnID);

            String allColumnID = "(";
            if (subColumnIDs != null) {
                for (int i = 1; i < subColumnIDs.length; i++) {
                    if (subColumnIDs[i] > 0)
                        allColumnID = allColumnID + subColumnIDs[i] + ",";
                }
                if (allColumnID.indexOf(",") != -1)
                    allColumnID = allColumnID.substring(0, allColumnID.length() - 1);
            }
            allColumnID = allColumnID + ")";

            SQL_PIGEONHOLE_ARTICLE = SQL_PIGEONHOLE_ARTICLE + " AND ColumnID in " + allColumnID;
        }

        if (achieve == 0) {
            SQL_PIGEONHOLE_ARTICLE = SQL_PIGEONHOLE_ARTICLE + " AND CreateDate < ?";
        } else if (achieve == 1) {
            SQL_PIGEONHOLE_ARTICLE = SQL_PIGEONHOLE_ARTICLE + " AND CreateDate > ? AND CreateDate < ?";
        }

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_PIGEONHOLE_ARTICLE);
                pstmt.setInt(1, columnID);
                if (achieve == 0) {
                    pstmt.setTimestamp(2, bdate);
                } else if (achieve == 1) {
                    pstmt.setTimestamp(2, bdate);
                    pstmt.setTimestamp(3, tdate);
                }
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: create article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }
    }

    //文章归档
    public void PigeonholeArticle(int columnID, int achieve, int includeSubCol, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;

        String SQL_SET_PIGEONHOLE_ARTICLE_RULES = "UPDATE TBL_Column SET archivingrules = ? WHERE 1 = 1 and siteid = ?";
        if (includeSubCol == 0) {
            SQL_SET_PIGEONHOLE_ARTICLE_RULES = SQL_SET_PIGEONHOLE_ARTICLE_RULES + " AND id = ?";
        } else {
            Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
            node[] treeNodes = colTree.getAllNodes();
            int[] subColumnIDs = (new Tree()).getSubTreeColumnIDList(treeNodes, columnID);

            String allColumnID = "(";
            if (subColumnIDs != null) {
                for (int i = 1; i < subColumnIDs.length; i++) {
                    if (subColumnIDs[i] > 0)
                        allColumnID = allColumnID + subColumnIDs[i] + ",";
                }
                if (allColumnID.indexOf(",") != -1)
                    allColumnID = allColumnID.substring(0, allColumnID.length() - 1);
            }
            allColumnID = allColumnID + ")";

            SQL_SET_PIGEONHOLE_ARTICLE_RULES = SQL_SET_PIGEONHOLE_ARTICLE_RULES + " AND id in " + allColumnID;
        }

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_SET_PIGEONHOLE_ARTICLE_RULES);
                pstmt.setInt(1, achieve);
                pstmt.setInt(2, siteid);
                if (includeSubCol == 0) {
                    pstmt.setInt(3, columnID);
                }
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: create article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }
    }

    public int batchDel(String articleIDs) throws ArticleException {
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        String SQL_DEL_ARTICLES = "delete from tbl_article WHERE ID IN (" + articleIDs + ")";

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DEL_ARTICLES);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            errcode = -2;
            throw new ArticleException("Database exception: can't rollback?");
        }

        return errcode;
    }

    public int batchAchive(int columnid,String articleIDs) throws ArticleException {
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        //status=2的文章是归档状态的文章
        String SQL_ACHIVE_ARTICLES = "update tbl_article  set status=2 WHERE ID IN (" + articleIDs + ")";

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_ACHIVE_ARTICLES);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            errcode = -2;
            throw new ArticleException("Database exception: can't rollback?");
        }

        return errcode;
    }

    public int batchUnuse(int columnid,String articleIDs) throws ArticleException {
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        //status=0的文章是未用状态的文章
        String SQL_UNUSE_ARTICLES = "update tbl_article  set status=0 WHERE ID IN (" + articleIDs + ")";

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UNUSE_ARTICLES);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            errcode = -2;
            throw new ArticleException("Database exception: can't rollback?");
        }

        return errcode;
    }

    public int updateRSS(String articleIDs, String allArticleIds) throws ArticleException {
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String SQL_UPDATE_ARTICLE_RSS0 = "UPDATE tbl_article SET IsJoinRSS = 0 WHERE ID IN (" + allArticleIds + ")";
        String SQL_UPDATE_ARTICLE_RSS1 = "UPDATE tbl_article SET IsJoinRSS = 1 WHERE ID IN (" + articleIDs + ")";

        System.out.println("allArticleIds==" + allArticleIds);
        System.out.println("articleIDs==" + articleIDs);

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_RSS0);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_RSS1);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ArticleException("Database exception: update article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ArticleException("Database exception: can't rollback?");
        }

        return errcode;
    }

    //根据文章ID获取文章的标题
    public List getArticleMainTitle(String articleIDs) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List articleList = new ArrayList();
        String SQL_GET_ARTICLE_MAINTITLE = "SELECT MainTitle FROM tbl_article WHERE id = ?";

        try {
            conn = cpool.getConnection();
            String aids[] = articleIDs.split(",");
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_MAINTITLE);
            for (int i = 0; i < aids.length; i++) {
                pstmt.setInt(1, Integer.parseInt(aids[i]));
                rs = pstmt.executeQuery();
                if (rs.next()) articleList.add(aids[i] + "|" + rs.getString(1));
                rs.close();
            }
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return articleList;
    }

    //获得被推荐到某个栏目下所有文章的标题
    public List getArticleMainTitle(String articleIDs, int columnid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List articleList = new ArrayList();
        String SQL_GET_ARTICLE_MAINTITLE = "SELECT title FROM tbl_referred_article WHERE articleid = ? and columnid = ?";

        try {
            conn = cpool.getConnection();
            String aids[] = articleIDs.split(",");
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_MAINTITLE);
            for (int i = 0; i < aids.length; i++) {
                pstmt.setInt(1, Integer.parseInt(aids[i]));
                pstmt.setInt(2, columnid);
                rs = pstmt.executeQuery();
                if (rs.next()) articleList.add(aids[i] + "|" + rs.getString(1));
                rs.close();
            }
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return articleList;
    }

    //edit by Eric 2006-7-16
    private static String INSERT_COLUMN_KEYWORD_FOR_ORACLE = "insert into tbl_article_keyword(siteid,columnid,keyword,url,id) " +
            "values(?, ?, ?, ?, ?)";

    private static String INSERT_COLUMN_KEYWORD_FOR_MSSQL = "insert into tbl_article_keyword(siteid,columnid,keyword,url) " +
            "values(?, ?, ?, ?)";

    private static String INSERT_COLUMN_KEYWORD_FOR_MYSQL = "insert into tbl_article_keyword(siteid,columnid,keyword,url) " +
            "values(?, ?, ?, ?)";

    public void insertColumnKeyword(ArticleKeyword keyword) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        SequencePeer sq = new SequencePeer(cpool);

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                //pstmt = conn.prepareStatement(INSERT_COLUMN_KEYWORD);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(INSERT_COLUMN_KEYWORD_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(INSERT_COLUMN_KEYWORD_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(INSERT_COLUMN_KEYWORD_FOR_MYSQL);
                pstmt.setInt(1, keyword.getSiteid());
                pstmt.setInt(2, keyword.getColumnid());
                pstmt.setString(3, keyword.getKeyword());
                pstmt.setString(4, keyword.getUrl());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(5, sq.getSequenceNum("Keyword"));
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_COLUMN_KEYWORD = "update tbl_article_keyword set keyword=?,url=? where id=? and columnid=?";

    public void updateColumnKeyword(ArticleKeyword keyword) {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(UPDATE_COLUMN_KEYWORD);
                pstmt.setString(1, keyword.getKeyword());
                pstmt.setString(2, keyword.getUrl());
                pstmt.setInt(3, keyword.getId());
                pstmt.setInt(4, keyword.getColumnid());
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String DELETE_COLUMN_KEYWORD = "delete tbl_article_keyword where id=?";

    public void deleteColumnKeyword(int id) {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(DELETE_COLUMN_KEYWORD);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //flag=0 获得栏目下部分文章keyword, flag=1 获得栏目下所有文章的keyword
    public List getArticleKeywords(int columnid, int start, int range, int flag, boolean isRootColumnID, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        ResultSet rs;
        ArticleKeyword articlekeyword;

        Tree colTree;
        colTree = TreeManager.getInstance().getSiteTree(siteid);
        node[] treeNodes = colTree.getAllNodes();
        String allColumnID = "(";
        for (int i = 0; i < treeNodes.length; i++) {
            allColumnID = allColumnID + treeNodes[i].getId() + ",";
        }
        if (allColumnID.indexOf(",") != -1)
            allColumnID = allColumnID.substring(0, allColumnID.length() - 1) + ")";

        String GET_SITEARTICLE_KEYWORDS = "select id,columnid,keyword,url from tbl_article_keyword where columnid in " + allColumnID + " order by id";
        String GET_ARTICLE_KEYWORDS = "select id,columnid,keyword,url from tbl_article_keyword where columnid = ?";

        try {
            conn = cpool.getConnection();
            if (isRootColumnID) {
                pstmt = conn.prepareStatement(GET_SITEARTICLE_KEYWORDS);
            } else {
                pstmt = conn.prepareStatement(GET_ARTICLE_KEYWORDS);
                pstmt.setInt(1, columnid);
            }
            rs = pstmt.executeQuery();

            if (flag == 0) {
                for (int i = 0; i < start; i++) {
                    rs.next();
                }
                for (int i = 0; i < range && rs.next(); i++) {
                    articlekeyword = loadArticleKeyword(rs);
                    list.add(articlekeyword);
                }
            } else if (flag == 1) {
                while (rs.next()) {
                    articlekeyword = loadArticleKeyword(rs);
                    list.add(articlekeyword);
                }
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String GET_ONE_KEYWORD = "select id,columnid,keyword,url from tbl_article_keyword where columnid = ? and id = ?";

    public ArticleKeyword getOneArticleKeyword(int columnid, int id) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        ArticleKeyword articlekeyword = new ArticleKeyword();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ONE_KEYWORD);
            pstmt.setInt(1, columnid);
            pstmt.setInt(2, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                articlekeyword = loadArticleKeyword(rs);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return articlekeyword;
    }

    private static String GET_ONE_KEYWORD_URL = "select url from tbl_article_keyword where keyword = ?";

    public String getOneArticleKeywordURL(String keyword) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String url = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ONE_KEYWORD_URL);
            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                url = rs.getString(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return url;
    }

    public int getArticleKeywordsNum(int columnid, boolean isRootColumnID, int siteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        int keywordsnum = 0;
        ResultSet rs;

        Tree colTree;
        colTree = TreeManager.getInstance().getSiteTree(siteid);
        node[] treeNodes = colTree.getAllNodes();
        String allColumnID = "(";
        for (int i = 0; i < treeNodes.length; i++) {
            allColumnID = allColumnID + treeNodes[i].getId() + ",";
        }
        if (allColumnID.indexOf(",") != -1)
            allColumnID = allColumnID.substring(0, allColumnID.length() - 1) + ")";

        String GET_SITEARTICLE_KEYWORDS_NUM = "select count(id) from tbl_article_keyword where columnid in " + allColumnID;
        String GET_ARTICLE_KEYWORDS_NUM = "select count(id) from tbl_article_keyword where columnid = ?";

        try {
            conn = cpool.getConnection();
            if (isRootColumnID) {
                pstmt = conn.prepareStatement(GET_SITEARTICLE_KEYWORDS_NUM);
            } else {
                pstmt = conn.prepareStatement(GET_ARTICLE_KEYWORDS_NUM);
                pstmt.setInt(1, columnid);
            }
            rs = pstmt.executeQuery();

            if (rs.next()) {
                keywordsnum = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return keywordsnum;
    }

    //Add by Eric 2006-9-30 获得已经发布的文章(/publish/publish.jsp)
    public List getPublishedArticles(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String SQL_GET_PUBLISHFAIL_ARTICLES =
                "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag," +
                        "auditflag,auditor,publishtime,filename,urltype,defineurl,siteid FROM tbl_article where pubflag = 0 and " +
                        cidString + " and emptycontentflag = 0 order by publishtime desc";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_PUBLISHFAIL_ARTICLES);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //已经发布的文章数量(/publish/published.jsp)
    public int getPublishedArticlesNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        String cidString = formatOracleClause(getColumnIDs(columnID));
        String SQL_GET_PUBLISHFAIL_ARTICLES =
                "SELECT COUNT(*) FROM tbl_article where pubflag = 0 and " + cidString + " and emptycontentflag = 0";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_PUBLISHFAIL_ARTICLES);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //获得所有需要发布成Rss的文章  Eric 2007-8-2
    public List getPubRssArticles(int columnID, Column column) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;
        int getrssarticletime = column.getGetRssArticleTime();

        String columnids = getColumnIDs(columnID);

        String SQL_GET_PUB_RSS_ARTICLES_LIST = "SELECT maintitle,emptycontentflag,editor,id,columnid,sortid,lockstatus,createdate," +
                "lockeditor,doclevel,pubflag,auditflag,auditor,filename,publishtime,status,viceTitle,dirName,urltype,defineurl FROM " +
                //"tbl_article where isjoinrss = 1 and (ispublished = 1 or emptycontentflag = 1) and" +
                "tbl_article where isjoinrss = 1 and" +
                " columnID in " + columnids;

        if (getrssarticletime > 0) {
            Timestamp rsstime = new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * getrssarticletime);
            SQL_GET_PUB_RSS_ARTICLES_LIST = SQL_GET_PUB_RSS_ARTICLES_LIST + " and publishtime > '" + rsstime + "'";
        } else if (getrssarticletime == 0) {
            Date nowdate = new Date(System.currentTimeMillis());
            SQL_GET_PUB_RSS_ARTICLES_LIST = SQL_GET_PUB_RSS_ARTICLES_LIST + " and publishtime >= '" + nowdate +
                    " 00:00:01' and publishtime <= '" + nowdate + " 23:59:59'";
        }
        SQL_GET_PUB_RSS_ARTICLES_LIST = SQL_GET_PUB_RSS_ARTICLES_LIST + " order by publishtime desc";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_PUB_RSS_ARTICLES_LIST);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadArticleList(rs);
                list.add(article);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //获取该栏目下推荐文章
    public int getCommendArticleForColumnNum(int columnID) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(articleid) from tbl_referred_article where columnid = ?");
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //获取该栏目下推荐文章
    public List getCommendArticleForColumn(int columnID, int startIndex, int numResults) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select a.orders,a.title,b.emptycontentflag,b.editor,b.id,b.columnid,b.sortid," +
                    "b.doclevel,b.pubflag,b.auditflag,b.auditor,b.publishtime,b.filename,b.urltype,b.defineurl from " +
                    "tbl_referred_article a,tbl_article b where a.columnid = ? and b.auditflag = 0 and b.status = 1 and " +
                    "a.articleid = b.id order by a.createdate desc");
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadRecommendArticle(rs);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //更新推荐文章的顺序
    public void updateOrders(int articleID, int columnid, int orders) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_referred_article set orders = ? where articleid = ? and columnid = ?");
            pstmt.setInt(1, orders);
            pstmt.setInt(2, articleID);
            pstmt.setInt(3, columnid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateArticleContent(int id, String content) {
        Connection conn = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("update tbl_article set content = ? where id = ?");
            if (content != null)
                DBUtil.setBigString(cpool.getType(), pstmt, 1, content);
            else
                pstmt.setNull(1, java.sql.Types.LONGVARCHAR);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    //更新推荐文章的标题
    public void updateTitle(int articleID, int columnid, String title) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_referred_article set title = ? where articleid = ? and columnid = ?");
            pstmt.setString(1, title);
            pstmt.setInt(2, articleID);
            pstmt.setInt(3, columnid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    //更新推荐文章的标题
    public void updateStatus(int articleID, int status) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_article set status = ? where id = ?");
            pstmt.setInt(1, status);
            pstmt.setInt(2, articleID);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }


    //删除推荐的文章
    public void deleteCommendArticle(int id, int columnid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_referred_article where articleid = ? and columnid = ?");
            pstmt.setInt(1, id);
            pstmt.setInt(2, columnid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List getArticlesforPublishCommendArticle(String where) throws ArticleException {
        String sql = "SELECT b.title,a.emptycontentflag,a.vicetitle,a.source,a.summary,a.keyword,a.author,a.editor,a.id," +
                "a.columnid,a.sortid,a.doclevel,a.pubflag,a.auditflag,a.auditor,a.createdate,a.lastupdated,a.status,a.subscriber," +
                "a.lockstatus,a.lockeditor,a.dirname,a.filename,a.publishtime,a.RelatedArtID,a.urltype,a.defineurl,a.notearticleid FROM" +
                " tbl_article a,tbl_referred_article b " + where;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next())
                list.add(loadNoContentCommendArticle(rs));

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " +
                            e.toString());
                }
            }
        }
        return list;
    }

    public List getArticlesforPublishCommendArticle(int articleNum, String where) throws ArticleException {
        String sql = "SELECT b.title,a.emptycontentflag,a.vicetitle,a.source,a.summary,a.keyword,a.author,a.editor,a.id," +
                "a.columnid,a.sortid,a.doclevel,a.pubflag,a.auditflag,a.auditor,a.createdate,a.lastupdated,a.status,a.subscriber," +
                "a.lockstatus,a.lockeditor,a.dirname,a.filename,a.publishtime,a.RelatedArtID,a.urltype,a.defineurl,a.notearticleid FROM " +
                "tbl_article a,tbl_referred_article b " + where;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            for (int i = 0; i < articleNum; i++) {
                if (rs.next())
                    list.add(loadNoContentCommendArticle(rs));
                else
                    break;
            }

            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " +
                            e.toString());
                }
            }
        }
        return list;
    }

    //创建文章轮换图片信息 by feixiang 2008-11-25
    public List getArticleTurnPic(int articleID) {
        List list = new ArrayList();
        Turnpic tpic = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_turnpic where articleid = ?");
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tpic = new Turnpic();
                tpic.setId(rs.getInt("id"));
                tpic.setArticleid(rs.getInt("articleid"));
                tpic.setPicname(rs.getString("picname"));
                tpic.setCreatedate(rs.getTimestamp("createdate"));
                tpic.setNotes(rs.getString("notes"));
                tpic.setSortid(rs.getInt("sortid"));
                tpic.setMediaurl(rs.getString("mediaurl"));
                list.add(tpic);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //delete article turn pic by feixiang 2008-12-02
    public int deleteArticleTurnpic(int id) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_turnpic where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();

            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }

    //updata turn_pic info  by feixiang 2008-02-22
    public void updataTurnPicInfo(Turnpic tpic) {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_turnpic set picname = ?,notes = ? where id = ?");
            pstmt.setString(1, tpic.getPicname());
            pstmt.setString(2, tpic.getNotes());
            pstmt.setInt(3, tpic.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public void updataMoreTurnPicInfo(List tpics) {
        Connection conn = null;
        PreparedStatement pstmt;
        Turnpic tpic = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            for (int i=0; i<tpics.size(); i++) {
                tpic = new Turnpic();
                tpic = (Turnpic)tpics.get(i);

                if (tpic.getId() > 0) {
                    if (tpic.getMediaurl() != null) {      //用户上传了文件，并修改属性值
                        pstmt = conn.prepareStatement("update tbl_turnpic set notes=?,mediaurl=?,sortid=? where id = ?");
                        pstmt.setString(1, tpic.getNotes());
                        pstmt.setString(2,tpic.getMediaurl());
                        pstmt.setInt(3, tpic.getSortid());
                        pstmt.setInt(4, tpic.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else {                                //用户没有上传文件，只修改属性值
                        pstmt = conn.prepareStatement("update tbl_turnpic set notes=?,sortid=? where id = ?");
                        pstmt.setString(1, tpic.getNotes());
                        pstmt.setInt(2, tpic.getSortid());
                        pstmt.setInt(3, tpic.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                } else {
                    pstmt = conn.prepareStatement("insert into tbl_turnpic(articleid,picname,notes,mediaurl,sortid,createdate) values(?,?,?,?,?,?)");
                    pstmt.setInt(1,tpic.getArticleid());
                    pstmt.setString(2,tpic.getPicname());
                    pstmt.setString(3, tpic.getNotes());
                    pstmt.setString(4,tpic.getMediaurl());
                    pstmt.setInt(5, tpic.getSortid());
                    pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        }
        catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    //get a turn pic by feixiang 2009-02-22
    public Turnpic getAArticleTurnPic(int id) {
        Turnpic tpic = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_turnpic where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tpic = new Turnpic();
                tpic.setId(rs.getInt("id"));
                tpic.setArticleid(rs.getInt("articleid"));
                tpic.setPicname(rs.getString("picname"));
                tpic.setCreatedate(rs.getTimestamp("createdate"));
                tpic.setNotes(rs.getString("notes"));
                tpic.setSortid(rs.getInt("sortid"));
                tpic.setMediaurl(rs.getString("mediaurl"));
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return tpic;
    }

    Article loadNoContentCommendArticle(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setMainTitle(rs.getString("title"));
            article.setViceTitle(rs.getString("vicetitle"));
            article.setSource(rs.getString("source"));
            article.setSummary(rs.getString("summary"));
            article.setKeyword(rs.getString("keyword"));
            article.setAuthor(rs.getString("author"));
            article.setEditor(rs.getString("editor"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setCreateDate(rs.getTimestamp("createDate"));
            article.setLastUpdated(rs.getTimestamp("lastUpdated"));

            //文章状态信息
            article.setStatus(rs.getInt("status"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setAuditFlag(rs.getInt("auditflag"));
            article.setSubscriber(rs.getInt("subscriber"));
            article.setNullContent(rs.getInt("emptycontentflag"));

            article.setLockStatus(rs.getInt("lockstatus"));
            article.setLockEditor(rs.getString("lockeditor"));

            article.setDirName(rs.getString("dirName"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setAuditor(rs.getString("auditor"));
            article.setRelatedArtID(rs.getString("RelatedArtID"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setUrltype(rs.getInt("urltype"));
            article.setNotes(rs.getInt("notearticleid"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return article;
    }

    Article loadRecommendArticle(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setOrders(rs.getInt("orders"));
            article.setMainTitle(rs.getString("title"));
            article.setEditor(rs.getString("editor"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setAuditFlag(rs.getInt("auditflag"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setNullContent(rs.getInt("emptycontentflag"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setAuditor(rs.getString("auditor"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setUrltype(rs.getInt("urltype"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return article;
    }

    Article load(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setMainTitle(rs.getString("maintitle"));
            article.setViceTitle(rs.getString("vicetitle"));
            article.setSource(rs.getString("source"));
            article.setSummary(rs.getString("summary"));
            article.setKeyword(rs.getString("keyword"));
            article.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
            article.setAuthor(rs.getString("author"));
            article.setEditor(rs.getString("editor"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setCreateDate(rs.getTimestamp("createDate"));
            article.setLastUpdated(rs.getTimestamp("lastUpdated"));

            article.setStatus(rs.getInt("status"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setAuditFlag(rs.getInt("auditflag"));
            article.setSubscriber(rs.getInt("subscriber"));
            article.setNullContent(rs.getInt("emptycontentflag"));

            article.setLockStatus(rs.getInt("lockstatus"));
            article.setLockEditor(rs.getString("lockeditor"));

            article.setDirName(rs.getString("dirName"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setAuditor(rs.getString("auditor"));
            article.setRelatedArtID(rs.getString("RelatedArtID"));

            article.setSalePrice(rs.getFloat("SalePrice"));
            article.setVIPPrice(rs.getFloat("vipprice"));
            article.setInPrice(rs.getFloat("InPrice"));
            article.setMarketPrice(rs.getFloat("MarketPrice"));
            article.setScore(rs.getInt("score"));
            article.setVoucher(rs.getInt("voucher"));
            article.setBrand(rs.getString("Brand"));
            article.setProductPic(rs.getString("Pic"));
            article.setProductBigPic(rs.getString("BigPic"));
            article.setProductWeight(rs.getFloat("Weight"));
            article.setStockNum(rs.getInt("StockNum"));
            article.setViceDocLevel(rs.getInt("ViceDocLevel"));
            article.setReferArticleID(rs.getInt("referID"));
            article.setModelID(rs.getInt("modelID"));
            article.setArticlepic(rs.getString("articlepic"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setBbdate(rs.getDate("beidate"));
            article.setSalesnum(rs.getInt("salesnum"));
            article.setNotes(rs.getInt("notearticleid"));
            article.setMediafile(rs.getString("mediafile"));
            article.setProcessofaudit(rs.getInt("processofaudit"));
            article.setIndexnum(rs.getString("indexnum"));
            article.setSiteID(rs.getInt("siteid"));
            // article.setClickNum(rs.getInt("clicknum"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return article;
    }

    Article loadNoContent(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setMainTitle(rs.getString("maintitle"));
            article.setViceTitle(rs.getString("vicetitle"));
            article.setSource(rs.getString("source"));
            article.setSummary(rs.getString("summary"));
            article.setKeyword(rs.getString("keyword"));
            article.setAuthor(rs.getString("author"));
            article.setEditor(rs.getString("editor"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setCreateDate(rs.getTimestamp("createDate"));
            article.setLastUpdated(rs.getTimestamp("lastUpdated"));

            //文章状态信息
            article.setStatus(rs.getInt("status"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setAuditFlag(rs.getInt("auditflag"));
            article.setSubscriber(rs.getInt("subscriber"));
            article.setNullContent(rs.getInt("emptycontentflag"));

            article.setLockStatus(rs.getInt("lockstatus"));
            article.setLockEditor(rs.getString("lockeditor"));

            article.setDirName(rs.getString("dirName"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setAuditor(rs.getString("auditor"));
            article.setRelatedArtID(rs.getString("RelatedArtID"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setClickNum(rs.getInt("clicknum"));
            article.setSiteID(rs.getInt("siteid"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return article;
    }

    Article loadBriefContent(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setMainTitle(rs.getString("maintitle"));
            article.setEditor(rs.getString("editor"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setAuditFlag(rs.getInt("auditflag"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setNullContent(rs.getInt("emptycontentflag"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setAuditor(rs.getString("auditor"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setSiteID(rs.getInt("siteid"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return article;
    }

    Article load4Publish(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            String maintitle = rs.getString("maintitle");
            if (maintitle!=null) maintitle=StringUtil.replace(maintitle,"—","&mdash;");
            String vicetitle = rs.getString("vicetitle");
            if (vicetitle!=null) vicetitle=StringUtil.replace(vicetitle,"—","&mdash;");
            String summary = rs.getString("summary");
            if (summary!=null) summary=StringUtil.replace(summary,"—","&mdash;");
            String keyword = rs.getString("keyword");
            if (keyword!=null) keyword=StringUtil.replace(keyword,"—","&mdash;");
            String source = rs.getString("source");
            if (source!=null) source=StringUtil.replace(source,"—","&mdash;");
            String author = rs.getString("author");
            if (author!=null) author=StringUtil.replace(author,"—","&mdash;");

            article.setMainTitle(rs.getString("maintitle"));
            article.setNullContent(rs.getInt("emptycontentflag"));
            article.setViceTitle(rs.getString("vicetitle"));
            article.setSource(rs.getString("source"));
            article.setSummary(rs.getString("summary"));
            article.setKeyword(rs.getString("keyword"));
            article.setAuthor(rs.getString("author"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setStatus(rs.getInt("status"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setDirName(rs.getString("dirName"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setRelatedArtID(rs.getString("RelatedArtID"));
            article.setReferArticleID(rs.getInt("referID"));
            article.setArticlepic(rs.getString("articlepic"));
            article.setCreateDate(rs.getTimestamp("createdate"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setDownfilename(rs.getString("downfilename"));
            article.setReferedTargetId(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    Article load4Publish(ResultSet rs, int referedTargetId,int articlenum) throws Exception {
        Article article = new Article();
        try {
            String maintitle = rs.getString("maintitle");
            if (maintitle!=null) maintitle=StringUtil.replace(maintitle,"—","&mdash;");
            String vicetitle = rs.getString("vicetitle");
            if (vicetitle!=null) vicetitle=StringUtil.replace(vicetitle,"—","&mdash;");
            String summary = rs.getString("summary");
            if (summary!=null) summary=StringUtil.replace(summary,"—","&mdash;");
            String keyword = rs.getString("keyword");
            if (keyword!=null) keyword=StringUtil.replace(keyword,"—","&mdash;");
            String source = rs.getString("source");
            if (source!=null) source=StringUtil.replace(source,"—","&mdash;");
            String author = rs.getString("author");
            if (author!=null) author=StringUtil.replace(author,"—","&mdash;");
            article.setArticlenum(articlenum);
            article.setMainTitle(rs.getString("maintitle"));
            article.setNullContent(rs.getInt("emptycontentflag"));
            article.setViceTitle(rs.getString("vicetitle"));
            article.setSource(rs.getString("source"));
            article.setSummary(rs.getString("summary"));
            article.setKeyword(rs.getString("keyword"));
            article.setAuthor(rs.getString("author"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setStatus(rs.getInt("status"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setDirName(rs.getString("dirName"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setRelatedArtID(rs.getString("RelatedArtID"));
            article.setReferArticleID(rs.getInt("referID"));
            article.setArticlepic(rs.getString("articlepic"));
            article.setCreateDate(rs.getTimestamp("createdate"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setReferedTargetId(referedTargetId);
            article.setClickNum(rs.getInt("clicknum"));
            article.setSiteID(rs.getInt("siteid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    Article loadArticleList(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setMainTitle(rs.getString("maintitle"));
            article.setEditor(rs.getString("editor"));
            article.setID(rs.getInt("id"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setAuditFlag(rs.getInt("auditflag"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setNullContent(rs.getInt("emptycontentflag"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setAuditor(rs.getString("auditor"));
            article.setLockStatus(rs.getInt("lockstatus"));
            article.setLockEditor(rs.getString("lockeditor"));
            article.setStatus(rs.getInt("status"));
            article.setViceTitle(rs.getString("viceTitle"));
            article.setDirName(rs.getString("dirName"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setCreateDate(rs.getTimestamp("createdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    //如果是模板 refflag=1
    //如果是栏目自己的文章 refflag=0
    //如果是栏目引用的文章 refflag=2
    Article loadBriefContent(ResultSet rs, boolean isTemplate, int refflag) throws Exception {
        Article article = new Article();
        try {
            article.setID(rs.getInt("ID"));
            article.setColumnID(rs.getInt("ColumnID"));
            article.setEditor(rs.getString("Editor"));
            article.setLastUpdated(rs.getTimestamp("LastUpdated"));
            article.setIsTemplate(isTemplate);
            if (isTemplate) {
                article.setMainTitle(rs.getString("ChName"));
                article.setIsArticleTemplate(rs.getInt("IsArticle"));
                article.setStatus(rs.getInt("DefaultTemplate"));
            } else {
                if (refflag == 2) {
                    article.setIsown(false);
                    article.setMainTitle(rs.getString("MainTitle") + "<font color=red>(引用)</font>");
                } else {
                    article.setIsown(true);
                    article.setMainTitle(rs.getString("MainTitle"));
                }
                article.setUrltype(rs.getInt("urltype"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setSiteID(rs.getInt("siteid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    Article loadLinkedArticle(ResultSet rs, boolean isTemplate) throws Exception {
        Article article = new Article();
        try {
            article.setID(rs.getInt("ID"));
            article.setColumnID(rs.getInt("ColumnID"));
            article.setEditor(rs.getString("Editor"));
            article.setLastUpdated(rs.getTimestamp("LastUpdated"));
            article.setIsTemplate(isTemplate);
            if (isTemplate) {
                article.setMainTitle(rs.getString("ChName"));
                article.setIsArticleTemplate(rs.getInt("IsArticle"));
                article.setStatus(rs.getInt("DefaultTemplate"));
                article.setFileName(rs.getString("TemplateName"));
            } else {
                article.setMainTitle(rs.getString("MainTitle"));
                article.setNullContent(rs.getInt("Emptycontentflag"));
                article.setFileName(rs.getString("FileName"));
                article.setCreateDate(rs.getTimestamp("CreateDate"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setUrltype(rs.getInt("urltype"));
            }
            article.setSiteID(rs.getInt("siteid"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    //flag=0 表示本栏目的文章
    //flag=1 表示引用的文章
    Article loadForAutopub(ResultSet rs, int flag) throws Exception {
        Article article = new Article();
        try {
            article.setID(rs.getInt("id"));
            article.setMainTitle(rs.getString("maintitle"));
            article.setColumnID(rs.getInt("columnid"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setReferedTargetId(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    relatedArticle loadRelatedArticle(ResultSet rs) throws Exception {
        relatedArticle article = new relatedArticle();
        try {
            article.setId(rs.getInt("id"));
            article.setPageid(rs.getInt("pageid"));
            article.setPagetype(rs.getInt("pagetype"));
            article.setContenttype(rs.getInt("contenttype"));
            article.setFilename(rs.getString("filename"));
            article.setSummary(rs.getString("summary"));
            article.setEditor(rs.getString("editor"));
            article.setCname(rs.getString("cname"));
            article.setCreateDate(rs.getTimestamp("createdate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    ArticleKeyword loadArticleKeyword(ResultSet rs) throws Exception {
        ArticleKeyword articlekeyword = new ArticleKeyword();
        try {
            articlekeyword.setId(rs.getInt("id"));
            articlekeyword.setColumnid(rs.getInt("columnid"));
            articlekeyword.setKeyword(rs.getString("keyword"));
            articlekeyword.setUrl(rs.getString("url"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articlekeyword;
    }
//为共享服务热点文章列表


    //为热点文章服务的文章列表(/templatex/articleList.jsp)
    public int sharegetTopStoriesArticlesNum(int columnID, int siteid, int samsiteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_TOPSTORIES_ARTICLES_NUM);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            String cidString = formatOracleClause(sharegetColumnIDs(columnID, siteid, samsiteid));
            String selectColumns = "";
            IColumnManager columnManager = ColumnPeer.getInstance();
            List selectColumnsList = columnManager.getRefersColumnIds(cidString);
            for (int i = 0; i < selectColumnsList.size(); i++) {
                Column scolumn = (Column) selectColumnsList.get(i);
                selectColumns = selectColumns + scolumn.getScid() + ",";
            }
            if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 0))
                selectColumns = selectColumns.substring(0, selectColumns.length() - 1);

            if ((selectColumns != null) && (!selectColumns.equals("") && (selectColumns.length() > 0))) {
                String newSQL = "select count(id) from tbl_refers_article where scolumnid in (" + selectColumns + ") and" +
                        " status=1 and auditflag=0";

                pstmt = conn.prepareStatement(newSQL);
                rs = pstmt.executeQuery();
                if (rs.next())
                    count = count + rs.getInt(1);
                rs.close();
                pstmt.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List sharegetTopStoriesArticles(int columnID, int startIndex, int numResults, int siteid, int samsiteid) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_TOPSTORIES_ARTICLES_LIST);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    article = loadBriefContent(rs);
                    article.setIsown(true);
                    list.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
            String cidString = formatOracleClause(sharegetColumnIDs(columnID, siteid, samsiteid));
            String selectColumns = "";
            IColumnManager columnManager = ColumnPeer.getInstance();
            if (cidString.indexOf(" ") == -1) {
                cidString = null;
            }
            List selectColumnsList = columnManager.getRefersColumnIds(cidString);
            for (int i = 0; i < selectColumnsList.size(); i++) {
                Column scolumn = (Column) selectColumnsList.get(i);
                selectColumns = selectColumns + scolumn.getScid() + ",";
            }
            if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 0))
                selectColumns = selectColumns.substring(0, selectColumns.length() - 1);

            String ids = "";
            if ((selectColumns != null) && (!selectColumns.equals("") && (selectColumns.length() > 0))) {
                int listsize = list.size();
                if (20 - listsize > 0) {
                    String newSQL = "SELECT count(id) FROM tbl_article where auditflag = 0 and status = 1 and columnID = " + columnID;
                    int getarticlenum = 0;
                    pstmt = conn.prepareStatement(newSQL);
                    rs = pstmt.executeQuery();
                    if (rs.next())
                        getarticlenum = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    newSQL = "select articleid from tbl_refers_article where scolumnid in (" + selectColumns + ") and status=1 and " +
                            "auditflag=0";
                    pstmt = conn.prepareStatement(newSQL);
                    rs = pstmt.executeQuery();
                    if (startIndex < getarticlenum) {
                        for (int i = 0; i < 20 - listsize; i++) {
                            if (rs.next())
                                ids += rs.getInt(1) + ",";
                            else
                                break;
                        }
                    } else {
                        for (int i = 0; i < startIndex - getarticlenum; i++) {
                            rs.next();
                        }
                        for (int i = 0; i < numResults; i++) {
                            if (rs.next())
                                ids += rs.getInt(1) + ",";
                            else
                                break;
                        }
                    }
                    rs.close();
                    pstmt.close();

                    if (ids.length() > 1) {
                        if (ids.endsWith(","))
                            ids = ids.substring(0, ids.length() - 1);
                    } else {
                        ids = "0";
                    }

                    newSQL = "select maintitle,emptycontentflag,editor,id,columnid,sortid,doclevel,pubflag,auditflag," +
                            "auditor,publishtime,filename,urltype,defineurl,siteid from tbl_article where id in (" + ids + ") " +
                            "order by publishtime desc";

                    pstmt = conn.prepareStatement(newSQL);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        article = loadBriefContent(rs);
                        article.setIsown(false);
                        list.add(article);
                    }
                    rs.close();
                    pstmt.close();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //by Vincent 2010-07-15 根据文章id获得轮换效果标志位

    public int getchangepic(int articleid) {
        String SQL_GETARTICLECHANGEPIC = "select changepic from tbl_article where id = ?  ";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETARTICLECHANGEPIC);
            pstmt.setInt(1, articleid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("changepic");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public void createTender(Tender tender) throws ArticleException{
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int t=0;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt=conn.prepareStatement("select id from tbl_tender where fileid=? and userid=?");
            pstmt.setInt(1,tender.getFileid());
            pstmt.setInt(2,tender.getUserid());
            rs = pstmt.executeQuery();
            while(rs.next()){
                t = rs.getInt("id");
            }
            rs.close();
            pstmt.close();
            if(t > 0){
                System.out.println("已存在");
            }else {
                pstmt = conn.prepareStatement("insert into tbl_tender (id,articleid,userid,username,depttitle,createdate,fileid)values(?,?,?,?,?,?,?)");
                pstmt.setInt(1, sequnceMgr.getSequenceNum("Article"));
                pstmt.setInt(2, tender.getArticleid());
                pstmt.setInt(3, tender.getUserid());
                pstmt.setString(4, tender.getName());
                pstmt.setString(5, tender.getDepttitle());
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(7,tender.getFileid());
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public Multimedia getFile(int id) throws ArticleException{
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Multimedia multimedia = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select t.id,t.siteid,t.articleid,t.dirname,t.filepath,t.oldfilename,t.newfilename,t.encodeflag,t.createdate,t.infotype from TBL_MULTIMEDIA t where t.id=?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                multimedia = new Multimedia();
                multimedia.setID(rs.getInt("id"));
                multimedia.setSiteid(rs.getInt("siteid"));
                multimedia.setArticleid(rs.getInt("articleid"));
                multimedia.setDirname(rs.getString("dirname"));
                multimedia.setFilepath(rs.getString("filepath"));
                multimedia.setOldfilename(rs.getString("oldfilename"));
                multimedia.setNewfilename(rs.getString("newfilename"));
                multimedia.setENCODEFLAG(rs.getInt("encodeflag"));
                multimedia.setCREATEDATE(rs.getTimestamp("createdate"));
                multimedia.setINFOTYPE(rs.getInt("infotype"));
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return multimedia;
    }


    public List getTenderbyid(int articleid, int startrow, int range) throws ArticleException{
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Tender tender;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select t.id,t.articleid,t.userid,t.username,t.depttitle,t.createdate from tbl_tender t where t.articleid = ? order by t.createdate desc ");
            pstmt.setInt(1, articleid);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range; i++) {
                if (rs.next()) {
                    tender = new Tender();
                    tender.setId(rs.getInt("id"));
                    tender.setUserid(rs.getInt("userid"));
                    tender.setArticleid(rs.getInt("articleid"));
                    tender.setName(rs.getString("username"));
                    tender.setDepttitle(rs.getString("depttitle"));
                    tender.setCreateDate(rs.getTimestamp("createdate"));
                    list.add(tender);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;

    }


    public int getTenderArticleNum(int columnID) throws ArticleException {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(a.articleid) c  from  (select count(articleid) c,articleid from tbl_tender group by articleid) a left join (select t.maintitle,t.id from tbl_article t order by t.createdate desc) b on b.id = a.articleid ");
            // pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("c");
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }


    public List getTenderArticles(int startrow, int range)  throws ArticleException{
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Tender tender;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select a.c,b.maintitle,a.articleid,b.publishtime from  (select count(articleid) c,articleid from tbl_tender group by articleid) a left join (select  t.publishtime,t.maintitle,t.id from tbl_article t order by t.createdate desc) b on b.id = a.articleid");

            rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range; i++) {
                if (rs.next()) {
                    tender = new Tender();
                    tender.setMaintitle(rs.getString("maintitle"));
                    tender.setNum(rs.getInt("c"));
                    tender.setArticleid(rs.getInt("articleid"));
                    tender.setCreateDate(rs.getTimestamp("publishtime"));
                    list.add(tender);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getSjs_log(String searchtime1,String searchtime2) throws ArticleException{
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Tender tender;
        String str ="select x.cname cname,count(x.cname) c  from\n" +
                "(select b.cname cname,b.department department from\n" +
                "(select l.editor from tbl_sjs_log l where l.operationtype=1";
        if ((searchtime1 != "") && (searchtime1 != null)) {
            str = str + "and l.createdate >= TO_DATE('"+searchtime1+"', 'YYYY-MM-DD')";
        }
        if ((searchtime2 != "") && (searchtime2 != null)) {
            str = str + "and l.createdate <= TO_DATE('"+searchtime2+"', 'YYYY-MM-DD')";
        }
        str=str+") a left join \n" +
                "(select m.userid userid,m.department,d.cname cname from tbl_members m,tbl_department d where m.department=d.id) b\n" +
                "on a.editor = b.userid) x where x.cname is not null group by x.cname";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(str);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tender = new Tender();
                tender.setMaintitle(rs.getString("cname"));
                tender.setNum(rs.getInt("c"));
                list.add(tender);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}