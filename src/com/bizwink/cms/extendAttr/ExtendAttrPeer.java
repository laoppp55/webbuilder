package com.bizwink.cms.extendAttr;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;
import java.util.regex.*;

import com.bizwink.cms.audit.Audit;
import com.bizwink.cms.audit.AuditPeer;
import com.bizwink.cms.audit.IAuditManager;
import com.bizwink.cms.modelManager.Model;
import com.bizwink.cms.multimedia.Attechment;
import com.bizwink.cms.multimedia.IMultimediaManager;
import com.bizwink.cms.multimedia.Multimedia;
import com.bizwink.cms.multimedia.MultimediaPeer;
import com.bizwink.cms.news.*;
import com.bizwink.cms.security.IUserManager;
import com.bizwink.cms.security.UserPeer;
import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.xml.*;
import com.bizwink.cms.publishx.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.markManager.markPeer;
import com.bizwink.cms.markManager.IMarkManager;
import com.bizwink.images.Uploadimage;
import com.bizwink.util.JSON_Str_To_ObjArray;
import com.bizwink.util.MD5Util;
import com.bizwink.util.zTreeNodeObj;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.w3c.dom.*;
import org.jdom.input.*;

public class ExtendAttrPeer implements IExtendAttrManager {
    PoolServer cpool;

    public ExtendAttrPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IExtendAttrManager getInstance() {
        return CmsServer.getInstance().getFactory().getExtendAttrManager();
    }

    private static final String SQL_GET_EXTEND_ATTRLIST = "SELECT ID,ArticleID,Type,EName,NumericValue,StringValue,FloatValue,TextValue " +
            "FROM TBL_Article_ExtendAttr WHERE ArticleID = ?";

    public List getArticleAttr(int articleID) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        ExtendAttr extendAttr;
        List attrList = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_EXTEND_ATTRLIST);
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                extendAttr = load(rs);
                attrList.add(extendAttr);
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
        return attrList;
    }

    private static final String SQL_GET_EXTEND_ATTRVALUE = "SELECT Type,StringValue,NumericValue,FloatValue,TextValue " +
            "FROM TBL_Article_ExtendAttr WHERE ArticleID = ? AND EName = ?";

    public List getArticleExtendValue(int articleID, List attrList) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_EXTEND_ATTRVALUE);
            for (int i = 0; i < attrList.size(); i++) {
                String ename = (String) attrList.get(i);
                pstmt.setInt(1, articleID);
                pstmt.setString(2, ename);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    int type = rs.getInt("Type");
                    String value;
                    if (type == 1)
                        value = rs.getString("StringValue");
                    else if (type == 2)
                        value = String.valueOf(rs.getInt("NumericValue"));
                    else if (type == 4)
                        value = String.valueOf(rs.getFloat("FloatValue"));
                    else
                        value = rs.getString("TextValue");

                    if (value == null || value.equals("0") || value.equals("0.0")) value = "";
                    attrList.set(i, ename + "=" + value);
                } else {
                    attrList.set(i, ename + "=");
                }
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
        return attrList;
    }

    public List getRecommendMarks(int articleid,int columnid,int siteid) throws ExtendAttrException {
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        relatedArticle relatedArticle = null;
        List marks = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id,siteid,columnid,modelid,markid,markname from tbl_recommend_article where siteid=? and columnid=? and articleid=? ");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,columnid);
            pstmt.setInt(3,articleid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                relatedArticle = new relatedArticle();
                relatedArticle.setPageid(rs.getInt("modelid"));
                relatedArticle.setJointedID(rs.getInt("markid"));
                relatedArticle.setChineseName(rs.getString("markname"));
                marks.add(relatedArticle);
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
        return marks;
    }

    //将录入的文章插入文章引用表
    private static final String SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE = "insert into tbl_refers_article(articleid,siteid,tsiteid,columnid," +
            "scolumnid,columnname,createdate,title,status,pubflag,auditflag,useArticleType,artfrom,editor,refereddate,id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_REFERS_ARTICLE_FOR_MSSQL = "insert into tbl_refers_article(articleid,siteid,tsiteid,columnid," +
            "scolumnid,columnname,createdate,title,status,pubflag,auditflag,useArticleType,artfrom,editor,refereddate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_REFERS_ARTICLE_FOR_MYSQL = "insert into tbl_refers_article(articleid,siteid,tsiteid,columnid," +
            "scolumnid,columnname,createdate,title,status,pubflag,auditflag,useArticleType,artfrom,editor,refereddate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int addRelatedArticles(List relArticle,String editor, int articleID,int siteid) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        int errcode = 0;
        int exist_flag = 0;

        try {
            conn = cpool.getConnection();
            //加入推荐文章
            relatedArticle relatearticle=null;
            Article article = articleMgr.getArticle(articleID);
            for (int i = 0; i < relArticle.size(); i++) {
                relatedArticle refersArticle = (relatedArticle)relArticle.get(i);
                Column column = columnMgr.getColumn(refersArticle.getJointedID());

                pstmt = conn.prepareStatement("select count(id) from tbl_refers_article where columnid=? and articleid=? and siteid=?");
                pstmt.setInt(1,refersArticle.getJointedID());
                pstmt.setInt(2,articleID);
                pstmt.setInt(3,siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) exist_flag = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (exist_flag == 0) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MYSQL);
                    pstmt.setInt(1, articleID);
                    pstmt.setInt(2, siteid);
                    pstmt.setInt(3, siteid);
                    pstmt.setInt(4, refersArticle.getJointedID());
                    pstmt.setInt(5, article.getColumnID());
                    pstmt.setString(6, StringUtil.gb2iso4View(column.getCName()));
                    pstmt.setTimestamp(7, refersArticle.getCreateDate());
                    pstmt.setString(8, StringUtil.gb2iso4View(refersArticle.getTitle()));
                    pstmt.setInt(9, article.getStatus());
                    pstmt.setInt(10, article.getPubFlag());
                    pstmt.setInt(11, article.getAuditFlag());
                    pstmt.setInt(12, refersArticle.getContenttype());
                    pstmt.setInt(13, 2);
                    pstmt.setString(14,editor);
                    pstmt.setTimestamp(15,new Timestamp(System.currentTimeMillis()));
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setLong(16, sequnceMgr.getSequenceNum("RefersArticle"));
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else {
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                } else
                    errcode = 100;
            }
        } catch (Exception e) {
            errcode = -1;
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

        return errcode;
    }

    private static final String SQL_CREATE_ARTICLE_FOR_ORACLE =
            "INSERT INTO TBL_Article (siteid,ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,topflag,redflag,boldflag,Auditflag,Subscriber," +
                    "Editor,creator,DirName,Publishtime,SalePrice,vipprice,InPrice,MarketPrice,score,voucher,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,articlepic,urltype," +
                    "defineurl,t1,t2,t3,t4,t5,deptid,beidate,changepic,auditor,notearticleid,fromsiteid,sarticleid,mediafile,sign,indexnum,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_ARTICLE_FOR_MSSQL =
            "INSERT INTO TBL_Article (siteid,ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,topflag,redflag,boldflag,Auditflag,Subscriber," +
                    "Editor,creator,DirName,Publishtime,SalePrice,vipprice,InPrice,MarketPrice,score,voucher,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,articlepic,urltype," +
                    "defineurl,t1,t2,t3,t4,t5,deptid,beidate,changepic,auditor,notearticleid,fromsiteid,sarticleid,mediafile,sign,indexnum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?);select SCOPE_IDENTITY();";

    private static final String SQL_CREATE_ARTICLE_FOR_MYSQL =
            "INSERT INTO TBL_Article (siteid,ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,topflag,redflag,boldflag,Auditflag,Subscriber," +
                    "Editor,creator,DirName,Publishtime,SalePrice,vipprice,InPrice,MarketPrice,score,voucher,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,articlepic,urltype," +
                    "defineurl,t1,t2,t3,t4,t5,deptid,beidate,changepic,auditor,notearticleid,fromsiteid,sarticleid,mediafile,sign,indexnum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_ORACLE = "INSERT INTO TBL_Article_ExtendAttr(ArticleID,Ename,Type," +
            "StringValue,NumericValue,FloatValue,ID,TextValue) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_MSSQL = "INSERT INTO TBL_Article_ExtendAttr(ArticleID,Ename,Type," +
            "StringValue,NumericValue,FloatValue,TextValue) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_MYSQL = "INSERT INTO TBL_Article_ExtendAttr(ArticleID,Ename,Type," +
            "StringValue,NumericValue,FloatValue,TextValue) VALUES (?, ?, ?, ?, ?, ?, ?)";


    private static final String SQL_CREATE_LOG_FOR_ORACLE = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MSSQL = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_LOG_FOR_MYSQL = "INSERT INTO tbl_log (SiteID,ColumnID,ArticleID,Editor,ActType,ActTime,MainTitle,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE = "INSERT INTO tbl_new_publish_queue (" +
            "SiteID,columnid,TargetID,Type,Status,CreateDate,PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_MULT_INFO_FOR_ORACLE = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename," +
            "encodeflag,infotype,createdate,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_MULT_INFO_FOR_MSSQL = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename,encodeflag,infotype,createdate) values(?,?,?,?,?,?,?,?,?)";

    private static final String CREATE_MULT_INFO_FOR_MYSQL = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename,encodeflag,infotype,createdate) values(?,?,?,?,?,?,?,?,?)";

    /**
     * 创建文章
     *
     * @param extendList  扩展属性List
     * @param attechments 文章附件List
     * @param article     文章对象Article
     * @param auditlist    审核意见List
     * @throws ExtendAttrException
     */
    public int create(List extendList, List attechments, Article article, List pubcolumns,List recommends, List<Uploadimage> turnlist,List auditlist,List mmfiles) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String auditor = "";
        String audit_rules = null;
        int articleID=0;
        int columnID = article.getColumnID();
        int siteID = article.getSiteID();

        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        IAuditManager auditMgr = AuditPeer.getInstance();
        IUserManager userMgr = UserPeer.getInstance();
        SiteInfo siteinfo = null;
        try {
            Column column = columnMgr.getColumn(columnID);
            if (column.getIsAudited() ==1) {
                Audit audit = auditMgr.getAuditRules(columnID);
                if (audit != null) {
                    audit_rules = audit.getAuditRules();
                    int posi = audit_rules.indexOf(" ");
                    if (posi > -1)
                        auditor = audit_rules.substring(0,posi);
                    else
                        auditor = audit_rules;
                    //获取部门领导用户ID
                    if (auditor.startsWith("[部门领导]")) {
                        int deptid = userMgr.getDeptidByUser(article.getEditor(),siteID);
                        auditor="[" + userMgr.getUserByRole("部门领导",deptid,siteID) + "]";
                    }
                    //获取主管领导用户ID
                    if (auditor.startsWith("[主管领导]")) {
                        int deptid = userMgr.getDeptidByUser(article.getEditor(),siteID);
                        auditor= "[" + userMgr.getUserByRole("主管领导",deptid,siteID) + "]";
                    }
                }
            }
            article.setDirName(column.getDirName());
            if (article.getSortID() == 0) article.setSortID(articleMgr.getNextOrder(columnID));
            siteinfo = siteMgr.getSiteInfo(siteID);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            try {
                String maintitle = article.getMainTitle();
                if (maintitle != null) maintitle = StringUtil.gb2isoindb(maintitle);
                String vicetitle = article.getViceTitle();
                if (vicetitle != null) vicetitle = StringUtil.gb2isoindb(vicetitle);
                String summary = article.getSummary();
                if (summary != null) summary = StringUtil.gb2isoindb(summary);
                String keyword = article.getKeyword();
                if (keyword != null) keyword = StringUtil.gb2isoindb(keyword);
                String source = article.getSource();
                if (source != null) source = StringUtil.gb2isoindb(source);
                String author = article.getAuthor();
                if (author != null) author = StringUtil.gb2isoindb(author);
                String content = article.getContent();
                if (content != null) content = StringUtil.gb2isoindb(content);
                String brand = article.getBrand();
                if (brand != null) brand = StringUtil.gb2isoindb(brand);
                String filename = article.getFileName();
                if (filename != null) filename = StringUtil.gb2isoindb(filename);
                String mediafile = article.getMediafile();
                if (mediafile!=null) mediafile = StringUtil.gb2isoindb(mediafile);
                String  signdata = MD5Util.MD5Encode(maintitle + UUID.randomUUID().toString(),"utf-8");

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                //增加文章
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_MYSQL);
                pstmt.setInt(1, article.getSiteID());
                pstmt.setInt(2, columnID);
                pstmt.setInt(3, article.getSortID());
                pstmt.setString(4, maintitle);
                pstmt.setString(5, vicetitle);
                pstmt.setString(6, summary);
                pstmt.setString(7, keyword);
                pstmt.setString(8, source);
                if (content != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 9, content);
                else
                    pstmt.setNull(9, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(10, article.getNullContent());
                pstmt.setString(11, author);
                pstmt.setTimestamp(12, article.getCreateDate());
                pstmt.setTimestamp(13, article.getLastUpdated());
                pstmt.setString(14, filename);
                pstmt.setString(15,article.getDownfilename());
                pstmt.setInt(16, article.getDocLevel());
                pstmt.setInt(17, article.getPubFlag());
                pstmt.setInt(18, article.getStatus());
                pstmt.setInt(19,article.getTopflag());
                pstmt.setInt(20,article.getRedflag());
                pstmt.setInt(21,article.getBoldflag());
                pstmt.setInt(22, article.getAuditFlag());
                pstmt.setInt(23, article.getSubscriber());
                pstmt.setString(24, article.getEditor());
                pstmt.setString(25, article.getCreator());
                pstmt.setString(26, article.getDirName());
                pstmt.setTimestamp(27, article.getPublishTime());
                pstmt.setFloat(28, article.getSalePrice());
                pstmt.setFloat(29, article.getVIPPrice());
                pstmt.setFloat(30, article.getInPrice());
                pstmt.setFloat(31, article.getMarketPrice());
                pstmt.setInt(32, article.getScore());
                pstmt.setInt(33, article.getVoucher());
                pstmt.setFloat(34, article.getProductWeight());
                pstmt.setInt(35, article.getStockNum());
                pstmt.setString(36, brand);
                pstmt.setString(37, article.getProductPic());
                pstmt.setString(38, article.getProductBigPic());
                pstmt.setString(39, article.getRelatedArtID());
                pstmt.setInt(40, 0);
                pstmt.setInt(41, 0);
                pstmt.setInt(42, 0);
                pstmt.setInt(43, article.getViceDocLevel());
                pstmt.setInt(44, 0);
                pstmt.setInt(45, 0);
                pstmt.setInt(46, article.getReferArticleID());
                pstmt.setInt(47, article.getModelID());
                pstmt.setString(48, article.getArticlepic());
                pstmt.setInt(49, article.getUrltype());
                pstmt.setString(50, article.getOtherurl());
                pstmt.setInt(51, article.getT1());
                pstmt.setInt(52, article.getT2());
                pstmt.setInt(53, article.getT3());
                pstmt.setInt(54, article.getT4());
                pstmt.setInt(55, article.getT5());
                pstmt.setString(56,article.getDeptid());
                pstmt.setDate(57,article.getBbdate());
                pstmt.setInt(58,article.getChangepic());
                pstmt.setString(59,auditor);
                pstmt.setInt(60,article.getNotes());
                pstmt.setInt(61,article.getFromsiteid());
                pstmt.setString(62,article.getSarticleid());
                pstmt.setString(63,mediafile);
                pstmt.setString(64,signdata);
                pstmt.setString(65,article.getIndexnum());
                if (cpool.getType().equals("oracle")) {
                    articleID = sequnceMgr.getSequenceNum("Article");
                    pstmt.setInt(66, articleID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        articleID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取Mysql自增列的值id
                    pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                    rs = pstmt.executeQuery();
                    if (rs.next()) articleID=rs.getInt(1);
                    rs.close();
                    pstmt.close();
                }

                //增加扩展属性
                if (extendList != null) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_MYSQL);
                    for (int i = 0; i < extendList.size(); i++) {
                        ExtendAttr extendAttr = (ExtendAttr) extendList.get(i);

                        String textvalue = extendAttr.getTextValue();
                        if (textvalue != null) textvalue = StringUtil.gb2isoindb(textvalue);
                        String stringvalue = extendAttr.getStringValue();
                        if (stringvalue != null) stringvalue = StringUtil.gb2isoindb(stringvalue);

                        pstmt.setInt(1, articleID);
                        pstmt.setString(2, extendAttr.getEName());
                        pstmt.setInt(3, extendAttr.getDataType());
                        pstmt.setString(4, stringvalue);
                        pstmt.setInt(5, extendAttr.getNumericValue());
                        pstmt.setFloat(6, extendAttr.getFloatValue());
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(7, sequnceMgr.getSequenceNum("ArticleExtendAttr"));
                            if (textvalue != null)
                                DBUtil.setBigString(cpool.getType(), pstmt, 8, textvalue);
                            else
                                pstmt.setNull(8, java.sql.Types.LONGVARCHAR);
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            if (textvalue != null)
                                DBUtil.setBigString(cpool.getType(), pstmt, 7, textvalue);
                            else
                                pstmt.setNull(7, java.sql.Types.LONGVARCHAR);
                            pstmt.executeUpdate();
                        } else {
                            if (textvalue != null)
                                DBUtil.setBigString(cpool.getType(), pstmt, 7, textvalue);
                            else
                                pstmt.setNull(7, java.sql.Types.LONGVARCHAR);
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //设置文章推荐目标位置（标记）信息
                //List queueList = new ArrayList();          //发布作业队列
                Publish publish=null;                      //发布作业
                if (recommends!=null) {
                    String recommend_SQL = null;
                    if (recommends.size()>0) {
                        //获取标记所在的模板列表
                        List t_recommends = new ArrayList();
                        for(int ii=0;ii<recommends.size();ii++) {
                            relatedArticle relatedArticle = (relatedArticle) recommends.get(ii);
                            t_recommends.add(relatedArticle);
                        }

                        //向tbl_recommend_article表中保存信息
                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            recommend_SQL = "insert into tbl_recommend_article(siteid,columnid,modelid,markid,markname,articleid,createdate,lastupdate,id) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            recommend_SQL = "insert into tbl_recommend_article(siteid,columnid,modelid,markid,markname,articleid,createdate,lastupdate) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?)";
                        else
                            recommend_SQL = "insert into tbl_recommend_article(siteid,columnid,modelid,markid,markname,articleid,createdate,lastupdate) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(recommend_SQL);
                        for(int ii=0;ii<t_recommends.size();ii++) {
                            relatedArticle relatedArticle = (relatedArticle)t_recommends.get(ii);
                            pstmt.setInt(1,article.getSiteID());
                            pstmt.setInt(2,article.getColumnID());
                            pstmt.setInt(3,relatedArticle.getPageid());
                            pstmt.setInt(4,relatedArticle.getJointedID());
                            pstmt.setString(5,relatedArticle.getChineseName());
                            pstmt.setInt(6,articleID);
                            pstmt.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
                            pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
                            if (cpool.getType().equals("oracle")) {
                                pstmt.setInt(9,sequnceMgr.getSequenceNum("Article"));
                            }
                            pstmt.executeUpdate();
                        }
                        pstmt.close();
                    }
                }

                //加入文章附件到文章附件表
                if(attechments != null && attechments.size() > 0){
                    String attechments_SQL = "";
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        attechments_SQL = "insert into tbl_relatedartids(cname,pageid,pagetype,contenttype,filename,summary,editor,createdate,lastupdate,dirname,content,id) " +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        attechments_SQL = "insert into tbl_relatedartids(cname,pageid,pagetype,contenttype,filename,summary,editor,createdate,lastupdate,dirname,content) " +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    else
                        attechments_SQL = "insert into tbl_relatedartids(cname,pageid,pagetype,contenttype,filename,summary,editor,createdate,lastupdate,dirname,content) " +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(attechments_SQL);
                    for(int i = 0; i < attechments.size(); i++){
                        Attechment attechment = (Attechment)attechments.get(i);
                        pstmt.setString(1,attechment.getCname());
                        pstmt.setInt(2, articleID);
                        pstmt.setInt(3,0);
                        pstmt.setInt(4,5);
                        pstmt.setString(5, attechment.getFilename());
                        pstmt.setString(6,attechment.getSummary());
                        pstmt.setString(7, attechment.getEditor());
                        pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
                        pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                        pstmt.setString(10,attechment.getDirname());
                        pstmt.setString(11,attechment.getContent());
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(12,sequnceMgr.getSequenceNum("Attechment"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //加入文章轮换图片信息 by feixiang 2008-11-24
                if(turnlist != null && turnlist.size() > 0){
                    String turnpic_SQL = "";
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        turnpic_SQL = "insert into tbl_turnpic(articleid,sortid,picname,mediaurl,createdate,notes,id) values(?, ?, ?, ?, ?, ?, ?)";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        turnpic_SQL = "insert into tbl_turnpic(articleid,sortid,picname,mediaurl,createdate,notes) values(?, ?, ?, ?, ?, ?)";
                    else
                        turnpic_SQL = "insert into tbl_turnpic(articleid,sortid,picname,mediaurl,createdate,notes) values(?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(turnpic_SQL);
                    for(int i = 0; i < turnlist.size(); i++){
                        List<Map> smallpictures = turnlist.get(i).getSmallimages();

                        Uploadimage tpic = (Uploadimage)turnlist.get(i);
                        pstmt.setInt(1,articleID);
                        pstmt.setInt(2,tpic.getImageno());
                        pstmt.setString(3, tpic.getFilepath());
                        pstmt.setString(4,tpic.getFilepath());
                        pstmt.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
                        pstmt.setString(6,StringUtil.gb2isoindb(tpic.getBrief()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(7,sequnceMgr.getSequenceNum("TurnPic"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //加入多媒体文件的入库信息
                if(mmfiles != null && mmfiles.size() > 0){
                    if (cpool.getType().equalsIgnoreCase("oracle")) {
                        pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_ORACLE);
                    } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                        pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MSSQL);
                    }else {
                        pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MYSQL);
                    }
                    for(int i = 0; i < mmfiles.size(); i++){
                        Multimedia mult = (Multimedia)mmfiles.get(i);
                        pstmt.setInt(1,mult.getSiteid());
                        pstmt.setInt(2,articleID);
                        pstmt.setString(3,mult.getDirname());
                        pstmt.setString(4,mult.getFilepath());
                        pstmt.setString(5,mult.getOldfilename());
                        pstmt.setString(6,mult.getNewfilename());
                        pstmt.setInt(7,mult.getEncodeflag());
                        pstmt.setInt(8,0);
                        pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(10, sequnceMgr.getSequenceNum("Multimedia"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, siteID);
                pstmt.setInt(2, columnID);
                pstmt.setInt(3, articleID);
                pstmt.setString(4, article.getEditor());
                pstmt.setInt(5, 1);
                pstmt.setTimestamp(6, article.getCreateDate());
                pstmt.setString(7, maintitle);
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //将录入的文章插入文章引用表,此篇文章可以由其他栏目引用
                List columnIdsList = getReferArticleColumnIds(article.getColumnID(),pubcolumns);
                String ids = "";
                if ((columnIdsList != null) && (columnIdsList.size() > 0)) {
                    for (int i = 0; i < columnIdsList.size(); i++) {
                        Column refersArticle = (Column) columnIdsList.get(i);
                        Column column = columnMgr.getColumn(refersArticle.getID());
                        int targetsiteid = column.getSiteID();

                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE);
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MSSQL);
                        else
                            pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MYSQL);
                        pstmt.setInt(1, articleID);
                        pstmt.setInt(2, article.getSiteID());
                        pstmt.setInt(3, targetsiteid);
                        pstmt.setInt(4, column.getID());
                        pstmt.setInt(5, article.getColumnID());
                        pstmt.setString(6, StringUtil.gb2iso4View(column.getCName()));
                        pstmt.setTimestamp(7, article.getCreateDate());
                        pstmt.setString(8, maintitle);
                        pstmt.setInt(9, article.getStatus());
                        pstmt.setInt(10, article.getPubFlag());
                        pstmt.setInt(11, article.getAuditFlag());
                        pstmt.setInt(12, column.getUseArticleType());
                        if (refersArticle.getArtfrom() == 0) {
                            pstmt.setInt(13,0);                       //根据栏目定义的引用关系将文章传送到引用栏目
                        }else{
                            pstmt.setInt(13,1);                       //文章录入时定义的文章引用欢喜
                        }
                        pstmt.setString(14,article.getEditor());
                        pstmt.setTimestamp(15,new Timestamp(System.currentTimeMillis()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setLong(16, sequnceMgr.getSequenceNum("RefersArticle"));
                            pstmt.executeUpdate();
                            pstmt.close();
                        }else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else {
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                }
                conn.commit();
            } catch (Exception e) {
                articleID = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            articleID = -2;
            throw new ExtendAttrException("Database exception: can't rollback?");
        }

        return articleID;
    }

    public int setTemplatePublishFlag(int siteID,int columnID,int ID,String editor) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        List queueList = new ArrayList();
        Publish publish = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String ids = "";
        int errcode = 0;
        IColumnManager columnMgr = ColumnPeer.getInstance();
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        SiteInfo siteinfo = null;

        try {
            //修改与该篇文章相关联的栏目模板的发布标志位
            siteinfo = siteManager.getSiteInfo(siteID);
            int sitetype = siteinfo.getSitetype();
            int site_root_columnid = columnMgr.getSiteRootColumn(siteID).getID();
            conn = cpool.getConnection();
            if (sitetype == 1) {
                int tempnum = siteinfo.getTempnum();
                String SQL_Select_Model = "select id,columnid,chname from tbl_template where isarticle <> 1 and " +
                        "siteid=? and tempnum=? and (relatedcolumnid like '%," + columnID +
                        ",%' or relatedcolumnid like '%(" + columnID + ",%' or relatedcolumnid like '%," + columnID + ")%' " +
                        "or relatedcolumnid like '%(" + columnID + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                pstmt.setInt(1, siteinfo.getSamsiteid());
                pstmt.setInt(2, tempnum);
            } else {
                String SQL_Select_Model = "select id,columnid,chname from tbl_template where isarticle <> 1 and " +
                        "siteid=? and (relatedcolumnid like '%," + columnID +
                        ",%' or relatedcolumnid like '%(" + columnID + ",%' or relatedcolumnid like '%," + columnID + ")%' " +
                        "or relatedcolumnid like '%(" + columnID + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                pstmt.setInt(1, siteID);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (cpool.getPublishWay() == 1) {
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setTargetID(rs.getInt("id"));
                    if (sitetype == 1)
                        publish.setColumnID(site_root_columnid);
                    else
                        publish.setColumnID(rs.getInt("columnid"));
                    publish.setTitle(rs.getString("chname"));
                    publish.setObjectType(2);
                    queueList.add(publish);
                }
                ids += rs.getString(1) + ",";
            }
            rs.close();
            pstmt.close();

            if (ids.length() > 0) {
                ids = ids.substring(0, ids.length() - 1);
                String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                pstmt.executeUpdate();
                pstmt.close();
            }

            //修改引用文章导致需要重新发布的模板
            List columnIdsList = getReferArticleColumnIds(columnID);
            String Update_Other_Site_TemplatePubflag = null;
            ids = "";
            for (int i = 0; i < columnIdsList.size(); i++) {
                Column refersArticle = (Column) columnIdsList.get(i);
                int ReferColumnID = refersArticle.getID();
                Update_Other_Site_TemplatePubflag = "select id,columnid,chname from tbl_template where isarticle <> 1 and siteid=? and " +
                        "(relatedcolumnid like '%," + ReferColumnID + ",%' or relatedcolumnid like '%(" + ReferColumnID + ",%' " +
                        "or relatedcolumnid like '%," + ReferColumnID + ")%' or relatedcolumnid like '%(" + ReferColumnID + ")%')";

                pstmt = conn.prepareStatement(Update_Other_Site_TemplatePubflag);
                pstmt.setInt(1, refersArticle.getSiteID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (cpool.getPublishWay() == 1) {
                        publish = new Publish();
                        publish.setSiteID(refersArticle.getSiteID());
                        publish.setTargetID(rs.getInt("id"));
                        publish.setColumnID(rs.getInt("columnid"));
                        publish.setTitle(rs.getString("chname"));
                        publish.setObjectType(2);
                        queueList.add(publish);
                    }
                    ids += rs.getString(1) + ",";
                }
                rs.close();
                pstmt.close();
            }

            if (ids.length() > 0) {
                ids = ids.substring(0, ids.length() - 1);
                String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                pstmt.executeUpdate();
                pstmt.close();
            }

            //从发布队列中删除该该篇文章的发布记录
            pstmt = conn.prepareStatement("delete from tbl_new_publish_queue where targetid=?");
            pstmt.setInt(1, ID);
            pstmt.executeUpdate();
            pstmt.close();

            //将需要发布的模板加入到发布队列
            if (cpool.getPublishWay() == 1) {
                PreparedStatement pstmt1 = null;
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                else
                    pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                for (int i = 0; i < queueList.size(); i++) {
                    publish = (Publish) queueList.get(i);
                    boolean exist_onejob = false;
                    String SQL_GetOneJob = "select * from tbl_new_publish_queue where siteid=? and columnid=? and targetid=? and type=?";
                    pstmt = conn.prepareStatement(SQL_GetOneJob);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, columnID);
                    pstmt.setInt(3, publish.getTargetID());
                    pstmt.setInt(4, publish.getObjectType());
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        exist_onejob = true;
                    }
                    rs.close();
                    pstmt.close();

                    if (exist_onejob == false) {
                        //SiteID,columnid,TargetID,Type,Status,CreateDate,PublishDate,UniqueID,title,PRIORITY,ID
                        pstmt1.setInt(1, siteID);
                        pstmt1.setInt(2, columnID);
                        pstmt1.setInt(3, publish.getTargetID());
                        pstmt1.setInt(4, publish.getObjectType());
                        pstmt1.setInt(5, 1);
                        pstmt1.setTimestamp(6, now);
                        pstmt1.setTimestamp(7, now);
                        pstmt1.setString(8, "");
                        pstmt1.setString(9, StringUtil.iso2gbindb(publish.getTitle()));
                        pstmt1.setInt(10, publish.getPriority());
                        if (cpool.getType().equals("oracle")) {
                            pstmt1.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                            pstmt1.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt1.executeUpdate();
                        } else {
                            pstmt1.executeUpdate();
                        }
                    }
                }
                pstmt1.close();
            }
        } catch (SQLException sqlexp) {
            errcode = -8;
            sqlexp.printStackTrace();
        } catch (ColumnException colexp) {
            errcode = -7;
            colexp.printStackTrace();
        } catch (SiteInfoException siteexp) {
            errcode = -6;
            siteexp.printStackTrace();
        } catch (ExtendAttrException extexp) {
            errcode = -5;
            extexp.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return errcode;
    }


    public int savePublishModelsIntoPublishQueue(int siteid,int columnid,int articleid,String editor) throws ExtendAttrException{
        int errcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        List<Publish> queueList = new ArrayList<>();
        Publish publish = null;
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            //记录需要发布模板，将需要发布的模板送入发布队列
            /*pstmt = conn.prepareStatement("select t.*, t.rowid from tbl_template t where t.id in (select tid from tbl_templatemark tm where tm.mid=?)");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                publish = new Publish();
                publish.setSiteID(siteid);
                publish.setTargetID(rs.getInt("id"));
                publish.setColumnID(rs.getInt("columnid"));
                publish.setTitle(rs.getString("chname"));
                publish.setPriority(2);                                                 //栏目模板发布作业的优先级设置为2
                publish.setPublishDate(new Timestamp(System.currentTimeMillis()));
                if (rs.getInt(("isarticle")) == 0 || rs.getInt(("isarticle")) == 3)   //栏目模板和专题模板
                    publish.setObjectType(3);
                else if (rs.getInt(("isarticle")) == 1)                              //首页模板
                    publish.setObjectType(2);
                queueList.add(publish);
            }
            rs.close();
            pstmt.close();*/

            //获取引用该篇文章需要发布的栏目模板
            List columnIdsList = getReferArticleColumnIds(columnid,null);
            String Update_Other_Site_TemplatePubflag = null;
            for (int i = 0; i < columnIdsList.size(); i++) {
                Column refersArticle = (Column) columnIdsList.get(i);
                int ReferColumnID = refersArticle.getID();
                Update_Other_Site_TemplatePubflag = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 " +
                        "and siteid=? and " +
                        "(relatedcolumnid like '%," + ReferColumnID + ",%' or relatedcolumnid like '%(" + ReferColumnID + ",%' " +
                        "or relatedcolumnid like '%," + ReferColumnID + ")%' or relatedcolumnid like '%(" + ReferColumnID + ")%')";
                pstmt = conn.prepareStatement(Update_Other_Site_TemplatePubflag);
                pstmt.setInt(1, refersArticle.getSiteID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    publish = new Publish();
                    publish.setSiteID(siteid);
                    publish.setTargetID(rs.getInt("id"));
                    publish.setColumnID(rs.getInt("columnid"));
                    publish.setTitle(rs.getString("chname"));
                    publish.setPriority(2);                                                 //栏目模板发布作业的优先级设置为2
                    publish.setPublishDate(new Timestamp(System.currentTimeMillis()));
                    if (rs.getInt(("isarticle")) == 0 || rs.getInt(("isarticle")) == 3)   //栏目模板和专题模板
                        publish.setObjectType(3);
                    else if (rs.getInt(("isarticle")) == 2)                              //首页模板
                        publish.setObjectType(2);
                    queueList.add(publish);
                }
                rs.close();
                pstmt.close();
            }

            //获取文章所在栏目需要发布的栏目模板
            SiteInfo siteInfo = siteMgr.getSiteInfo(siteid);
            int sitetype = siteInfo.getSitetype();
            String SQL_Select_Model = null;
            if (sitetype == 1) {                          //引用模板网站
                int site_root_columnid = columnManager.getSiteRootColumn(siteid).getID();
                SQL_Select_Model = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 and siteid=? and tempnum=? and (relatedcolumnid like '%," + columnid + ",%' or relatedcolumnid like '%(" + columnid + ",%' or relatedcolumnid like '%," + columnid + ")%' or relatedcolumnid like '%(" + columnid + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                int tempnum = siteInfo.getTempnum();
                pstmt.setInt(1, siteInfo.getSamsiteid());
                pstmt.setInt(2,tempnum);
            } else {                                      //自建网站或者拷贝模板网站
                SQL_Select_Model = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 and siteid=? and (relatedcolumnid like '%," + columnid + ",%' or relatedcolumnid like '%(" + columnid + ",%' or relatedcolumnid like '%," + columnid + ")%' or relatedcolumnid like '%(" + columnid + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                pstmt.setInt(1, siteid);
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                publish = new Publish();
                publish.setSiteID(siteid);
                publish.setTargetID(rs.getInt("id"));
                publish.setColumnID(rs.getInt("columnid"));
                publish.setTitle(rs.getString("chname"));
                publish.setPriority(2);                                                 //栏目模板发布作业的优先级设置为2
                publish.setPublishDate(new Timestamp(System.currentTimeMillis()));
                if (rs.getInt(("isarticle")) == 0 || rs.getInt(("isarticle")) == 3)   //栏目模板和专题模板
                    publish.setObjectType(3);
                else if (rs.getInt(("isarticle")) == 2)                              //首页模板
                    publish.setObjectType(2);
                queueList.add(publish);
            }
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            for (int i = 0; i < queueList.size(); i++) {
                publish = (Publish) queueList.get(i);
                boolean exist_onejob = false;
                String SQL_GetOneJob = "select id from tbl_new_publish_queue where siteid=? and columnid=? and targetid=? and type=? and status!=0";
                pstmt=conn.prepareStatement(SQL_GetOneJob);
                pstmt.setInt(1,siteid);
                pstmt.setInt(2,publish.getColumnID());
                pstmt.setInt(3,publish.getTargetID());
                pstmt.setInt(4,publish.getObjectType());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    exist_onejob = true;
                }
                rs.close();
                pstmt.close();

                if (exist_onejob == false) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    pstmt.setInt(1, siteid);
                    pstmt.setInt(2, publish.getColumnID());
                    pstmt.setInt(3, publish.getTargetID());
                    pstmt.setInt(4, publish.getObjectType());
                    pstmt.setInt(5, 1);
                    pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(7, publish.getPublishDate());
                    pstmt.setString(8, "");
                    pstmt.setString(9,publish.getTitle());
                    pstmt.setInt(10,publish.getPriority());
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (Exception e) {
            errcode = -1;
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

        return errcode;
    }

    public List getNeedPublishModelsByArticleID(int siteid,int columnid,int articleid,String editor) throws ExtendAttrException{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        List list = new ArrayList();
        Model model = null;
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();

        try {
            conn = cpool.getConnection();
            //获取引用该篇文章需要发布的栏目模板
            List columnIdsList = getReferArticleColumnIds(columnid,null);
            System.out.println("size:" + columnIdsList.size());
            String Update_Other_Site_TemplatePubflag = null;
            for (int i = 0; i < columnIdsList.size(); i++) {
                Column refersArticle = (Column) columnIdsList.get(i);
                int ReferColumnID = refersArticle.getID();
                Update_Other_Site_TemplatePubflag = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 " +
                        "and siteid=? and " +
                        "(relatedcolumnid like '%," + ReferColumnID + ",%' or relatedcolumnid like '%(" + ReferColumnID + ",%' " +
                        "or relatedcolumnid like '%," + ReferColumnID + ")%' or relatedcolumnid like '%(" + ReferColumnID + ")%')";
                pstmt = conn.prepareStatement(Update_Other_Site_TemplatePubflag);
                pstmt.setInt(1, refersArticle.getSiteID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    model = new Model();
                    model.setChineseName(rs.getString("chname"));
                    model.setID(rs.getInt("id"));
                    model.setColumnID(rs.getInt("columnid"));
                    model.setIsArticle(rs.getInt("isarticle"));
                    list.add(model);
                }
                rs.close();
                pstmt.close();
            }

            //获取文章所在栏目需要发布的栏目模板
            SiteInfo siteInfo = siteMgr.getSiteInfo(siteid);
            int sitetype = siteInfo.getSitetype();
            String SQL_Select_Model = null;
            if (sitetype == 1) {                          //引用模板网站
                int site_root_columnid = columnManager.getSiteRootColumn(siteid).getID();
                SQL_Select_Model = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 and siteid=? and tempnum=? and (relatedcolumnid like '%," + columnid + ",%' or relatedcolumnid like '%(" + columnid + ",%' or relatedcolumnid like '%," + columnid + ")%' or relatedcolumnid like '%(" + columnid + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                int tempnum = siteInfo.getTempnum();
                pstmt.setInt(1, siteInfo.getSamsiteid());
                pstmt.setInt(2,tempnum);
            } else {                                      //自建网站或者拷贝模板网站
                SQL_Select_Model = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 and siteid=? and (relatedcolumnid like '%," + columnid + ",%' or relatedcolumnid like '%(" + columnid + ",%' or relatedcolumnid like '%," + columnid + ")%' or relatedcolumnid like '%(" + columnid + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                pstmt.setInt(1, siteid);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                model = new Model();
                model.setChineseName(rs.getString("chname"));
                model.setID(rs.getInt("id"));
                model.setColumnID(rs.getInt("columnid"));
                model.setIsArticle(rs.getInt("isarticle"));
                list.add(model);
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

    private List getReferArticleColumnIds(int columnId,List pubcolumns) throws ExtendAttrException {
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
                column.setArtfrom(0);
                list.add(column);
            }

            //判断从文章录入处定义的引用关系与栏目定义时定义的引用关系是否重复
            //如果重复则忽略文章录入处定义的引用关系
            List tlist = new ArrayList();
            if (pubcolumns != null) {
                for(int i=0; i<pubcolumns.size();i++) {
                    Column column = new Column();
                    relatedArticle ra = (relatedArticle)pubcolumns.get(i);
                    boolean exist = false;
                    for (int j=0; j<list.size(); j++) {
                        Column c1 = (Column)list.get(j);
                        if (c1.getID() == ra.getJointedID())
                        {
                            exist = true;
                            break;
                        }
                    }
                    if (exist == false) {
                        column.setID(ra.getJointedID());
                        column.setArtfrom(1);
                        tlist.add(column);
                    }
                }
            }
            rs.close();
            pstmt.close();

            /*for(int i=0; i<tlist.size(); i++) {
                Column c1 = (Column)tlist.get(i);
                list.add(c1);
            } */
            list.addAll(tlist);
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

    //单篇文章引用，插入tbl_refers_article表 useArtileType=0 引用链接地址 useArtileType=1 引用文章内容
    public void referArticle(List<Integer> articleList,String editor,int tcid, int tsiteID, int useArtileType) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            //articleid,siteid,tsiteid,columnid,scolumnid,columnname,createdate,title,status,pubflag,auditflag,useArticleType,artfrom,id
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MYSQL);

            for (int i = 0; i < articleList.size(); i++) {
                int articleID = articleList.get(i);
                System.out.println("tsiteid==" + tsiteID + "==tcid==" + tcid + "==articleID==" + articleID);
                Article refersArticle = articleMgr.getArticle(articleID);

                //文章内容引用，设置文章状态为新稿状态
                if (useArtileType == 1) {
                    refersArticle.setStatus(1);
                    refersArticle.setPubFlag(1);
                }

                Column column = columnMgr.getColumn(tcid);
                pstmt.setInt(1, refersArticle.getID());
                pstmt.setInt(2, refersArticle.getSiteID());
                pstmt.setInt(3, tsiteID);
                pstmt.setInt(4, tcid);
                pstmt.setInt(5, refersArticle.getColumnID());
                pstmt.setString(6, StringUtil.gb2iso4View(column.getCName()));
                pstmt.setTimestamp(7, refersArticle.getCreateDate());
                pstmt.setString(8, StringUtil.gb2iso4View(refersArticle.getMainTitle()));
                pstmt.setInt(9, refersArticle.getStatus());
                pstmt.setInt(10, refersArticle.getPubFlag());
                pstmt.setInt(11, refersArticle.getAuditFlag());
                pstmt.setInt(12, useArtileType);
                pstmt.setInt(13, 0);
                pstmt.setString(14,editor);
                pstmt.setTimestamp(15,new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    int referArticleID = sequnceMgr.getSequenceNum("RefersArticle");
                    pstmt.setInt(16, referArticleID);
                }
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ColumnException e) {
            e.printStackTrace();
        } catch (ArticleException e) {
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

    /*private static final String SQL_UPDATE_ARTICLE =
            "UPDATE TBL_Article SET MainTitle = ?,ViceTitle = ?,Source = ?,Content = ?,Author = ?," +
                    "Summary = ?,Keyword = ?,LastUpdated = ?,Doclevel = ?,Pubflag = ?,Status = ?,topflag = ?,redflag = ?,boldflag = ?,AuditFlag = ?," +
                    "Subscriber = ?,Editor = ?,SortID = ?,PublishTime = ?,LockStatus = ?,LockEditor = ?," +
                    "SalePrice = ?,vipprice = ?,InPrice = ?,MarketPrice = ?,score = ?,voucher = ?,Weight = ?,StockNum = ?,Brand = ?,Pic = ?,BigPic = ?," +
                    "RelatedArtID = ?,FileName = ?,downfilename=?, indexFlag = ?,ViceDocLevel = ?,ModelID = ?,articlepic = ?,urltype = ?," +
                    "defineurl = ? ,beidate=? ,changepic=?,auditor=?,notearticleid=?,mediafile=?,sign=? WHERE ID = ?";*/

    private static final String SQL_UPDATE_ARTICLE =
            "UPDATE TBL_Article SET MainTitle = ?,ViceTitle = ?,Source = ?,Content = ?,Author = ?," +
                    "Summary = ?,Keyword = ?,LastUpdated = ?,Doclevel = ?,Pubflag = ?,Status = ?,topflag = ?,redflag = ?,boldflag = ?,AuditFlag = ?," +
                    "Subscriber = ?,SortID = ?,PublishTime = ?,LockStatus = ?,LockEditor = ?," +
                    "SalePrice = ?,vipprice = ?,InPrice = ?,MarketPrice = ?,score = ?,voucher = ?,Weight = ?,StockNum = ?,Brand = ?,Pic = ?,BigPic = ?," +
                    "RelatedArtID = ?,FileName = ?,downfilename=?, indexFlag = ?,ViceDocLevel = ?,ModelID = ?,articlepic = ?,urltype = ?," +
                    "defineurl = ? ,beidate=? ,changepic=?,auditor=?,notearticleid=?,mediafile=?,sign=? WHERE ID = ?";

    private static final String SQL_DELETE_ARTICLE_EXTEND_ATTR = "DELETE FROM TBL_Article_ExtendAttr WHERE ArticleID = ?";

    private static final String DELETE_ALL_REFERS_ARTICLE = "delete from tbl_refers_article where articleid=? and columnid=? and siteid=?";

    public int update(List extendList, List attechments,Article article, List pubcolumns,List recommends,List turnlist,List auditlist,List mmfiles) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int articleID = article.getID();
        int siteID = article.getSiteID();
        int columnID = article.getColumnID();
        String auditor = "";
        String audit_rules = null;
        int errcode = 0;

        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IAuditManager auditMgr = AuditPeer.getInstance();
        IUserManager userMgr = UserPeer.getInstance();
        IMultimediaManager multimediaManager = MultimediaPeer.getInstance();
        SiteInfo siteinfo = null;
        try {
            Column column = columnMgr.getColumn(columnID);
            siteinfo = siteMgr.getSiteInfo(siteID);
            if (column.getIsAudited() ==1) {
                Audit audit_for_the_column = auditMgr.getAuditRules(columnID);
                if (audit_for_the_column != null) {
                    audit_rules = audit_for_the_column.getAuditRules();
                    int posi = audit_rules.indexOf(" ");
                    if (posi > -1)
                        auditor = audit_rules.substring(0,posi);
                    else
                        auditor = audit_rules;
                    //获取部门领导用户ID
                    if (auditor.startsWith("[部门领导]")) {
                        int deptid = userMgr.getDeptidByUser(article.getEditor(),siteID);
                        auditor="[" +userMgr.getUserByRole("部门领导",deptid,siteID) + "]";
                    }
                    //获取主管领导用户ID
                    if (auditor.startsWith("[主管领导]")) {
                        int deptid = userMgr.getDeptidByUser(article.getEditor(),siteID);
                        auditor="[" + userMgr.getUserByRole("主管领导",deptid,siteID) + "]";
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            try {
                String maintitle = article.getMainTitle();
                if (maintitle != null) maintitle = StringUtil.gb2isoindb(maintitle);
                //maintitle = new String(maintitle.getBytes("iso-8859-1"), "gbk");
                String vicetitle = article.getViceTitle();
                if (vicetitle != null) vicetitle = StringUtil.gb2isoindb(vicetitle);
                //vicetitle = new String(vicetitle.getBytes("iso-8859-1"), "gbk");
                String summary = article.getSummary();
                if (summary != null) summary = StringUtil.gb2isoindb(summary);
                //summary = new String(summary.getBytes("iso-8859-1"), "gbk");
                String keyword = article.getKeyword();
                if (keyword != null) keyword = StringUtil.gb2isoindb(keyword);
                //keyword = new String(keyword.getBytes("iso-8859-1"), "gbk");
                String source = article.getSource();
                if (source != null) source = StringUtil.gb2isoindb(source);
                //source = new String(source.getBytes("iso-8859-1"), "gbk");
                String author = article.getAuthor();
                if (author != null) author = StringUtil.gb2isoindb(author);
                //author = new String(author.getBytes("iso-8859-1"), "gbk");
                String content = article.getContent();
                if (content != null) content = StringUtil.gb2isoindb(content);
                //content = new String(content.getBytes("iso-8859-1"), "gbk");
                String brand = article.getBrand();
                if (brand != null) brand = StringUtil.gb2isoindb(brand);
                //brand = new String(brand.getBytes("iso-8859-1"), "gbk");
                String mediafile = article.getMediafile();
                if (mediafile != null) mediafile = StringUtil.gb2isoindb(mediafile);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE);

                pstmt.setString(1, maintitle);
                pstmt.setString(2, vicetitle);
                pstmt.setString(3, source);
                if (content != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 4, content);
                else
                    pstmt.setNull(4, java.sql.Types.LONGVARCHAR);
                pstmt.setString(5, author);
                pstmt.setString(6, summary);
                pstmt.setString(7, keyword);
                pstmt.setTimestamp(8, article.getLastUpdated());
                pstmt.setInt(9, article.getDocLevel());
                pstmt.setInt(10, 1);
                pstmt.setInt(11, article.getStatus());
                pstmt.setInt(12, article.getTopflag());
                pstmt.setInt(13, article.getRedflag());
                pstmt.setInt(14, article.getBoldflag());
                pstmt.setInt(15, article.getAuditFlag());
                pstmt.setInt(16, article.getSubscriber());
                //pstmt.setString(17, article.getEditor());
                pstmt.setInt(17, article.getSortID());
                pstmt.setTimestamp(18, article.getPublishTime());
                pstmt.setInt(19, 0);                            //修改锁定标志
                pstmt.setString(20, "");                        //修改锁定者
                pstmt.setFloat(21, article.getSalePrice());
                pstmt.setFloat(22, article.getVIPPrice());
                pstmt.setFloat(23, article.getInPrice());
                pstmt.setFloat(24, article.getMarketPrice());
                pstmt.setInt(25, article.getScore());
                pstmt.setInt(26, article.getVoucher());
                pstmt.setFloat(27, article.getProductWeight());
                pstmt.setInt(28, article.getStockNum());
                pstmt.setString(29, brand);
                pstmt.setString(30, article.getProductPic());
                pstmt.setString(31, article.getProductBigPic());
                pstmt.setString(32, article.getRelatedArtID());
                pstmt.setString(33, article.getFileName());
                pstmt.setString(34, article.getDownfilename());
                pstmt.setInt(35, 3);                            //设置文章被索引的标志位为2--文章需要重新被索引
                pstmt.setInt(36, article.getViceDocLevel());
                pstmt.setInt(37, article.getModelID());
                pstmt.setString(38, article.getArticlepic());
                pstmt.setInt(39, article.getUrltype());
                pstmt.setString(40, article.getOtherurl());
                pstmt.setDate(41, article.getBbdate());
                pstmt.setInt(42, article.getChangepic());
                pstmt.setString(43, auditor);
                pstmt.setInt(44,article.getNotes());
                pstmt.setString(45,mediafile);
                pstmt.setString(46,MD5Util.MD5Encode(maintitle + String.valueOf(articleID), "utf-8"));
                pstmt.setInt(47,articleID);
                pstmt.executeUpdate();
                pstmt.close();

                //删除该篇文章原来的所有扩展属性
                pstmt = conn.prepareStatement(SQL_DELETE_ARTICLE_EXTEND_ATTR);
                pstmt.setInt(1, articleID);
                pstmt.executeUpdate();
                pstmt.close();

                //增加新扩展属性值
                if (extendList !=null) {
                    for (int i = 0; i < extendList.size(); i++) {
                        ExtendAttr extendAttr = (ExtendAttr) extendList.get(i);
                        String textvalue = extendAttr.getTextValue();
                        if (textvalue != null) textvalue = StringUtil.gb2isoindb(textvalue);
                        String stringvalue = extendAttr.getStringValue();
                        if (stringvalue != null) stringvalue = StringUtil.gb2isoindb(stringvalue);
                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_ORACLE);
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_MSSQL);
                        else
                            pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR_FOR_MYSQL);
                        pstmt.setInt(1, articleID);
                        pstmt.setString(2, extendAttr.getEName());
                        pstmt.setInt(3, extendAttr.getDataType());
                        pstmt.setString(4, stringvalue);
                        pstmt.setInt(5, extendAttr.getNumericValue());
                        pstmt.setFloat(6, extendAttr.getFloatValue());
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(7, sequnceMgr.getSequenceNum("ArticleExtendAttr"));
                            if (textvalue != null) {
                                DBUtil.setBigString(cpool.getType(), pstmt, 8, textvalue);
                            }else
                                pstmt.setNull(8, java.sql.Types.LONGVARCHAR);
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            if (textvalue != null)
                                DBUtil.setBigString(cpool.getType(), pstmt, 7, textvalue);
                            else
                                pstmt.setNull(7, java.sql.Types.LONGVARCHAR);
                            pstmt.executeUpdate();
                        } else {
                            if (textvalue != null)
                                DBUtil.setBigString(cpool.getType(), pstmt, 7, textvalue);
                            else
                                pstmt.setNull(7, java.sql.Types.LONGVARCHAR);
                            pstmt.executeUpdate();
                        }
                        pstmt.close();
                    }
                }

                //删除该篇文章原来的所有附件信息
                pstmt = conn.prepareStatement("delete from tbl_relatedartids where pageid=?");
                pstmt.setInt(1, articleID);
                pstmt.executeUpdate();
                pstmt.close();

                if(attechments != null && attechments.size() > 0){
                    String attechments_SQL = "";
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        attechments_SQL = "insert into tbl_relatedartids(cname,pageid,pagetype,contenttype,filename,summary,editor,createdate,lastupdate,dirname,content,id) " +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        attechments_SQL = "insert into tbl_relatedartids(cname,pageid,pagetype,contenttype,filename,summary,editor,createdate,lastupdate,dirname,content) " +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    else
                        attechments_SQL = "insert into tbl_relatedartids(cname,pageid,pagetype,contenttype,filename,summary,editor,createdate,lastupdate,dirname,content) " +
                                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(attechments_SQL);
                    for(int i = 0; i < attechments.size(); i++){
                        Attechment attechment = (Attechment)attechments.get(i);
                        pstmt.setString(1,attechment.getCname());
                        pstmt.setInt(2, articleID);
                        pstmt.setInt(3,0);
                        pstmt.setInt(4,5);
                        pstmt.setString(5, attechment.getFilename());
                        pstmt.setString(6,attechment.getSummary());
                        pstmt.setString(7, attechment.getEditor());
                        pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
                        pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                        pstmt.setString(10,attechment.getDirname());
                        pstmt.setString(11,attechment.getContent());
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(12,sequnceMgr.getSequenceNum("Attechment"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //设置文章推荐目标位置（标记）信息
                //删除原来这篇文章的推荐信息
                pstmt = conn.prepareStatement("delete from tbl_recommend_article where siteid=? and columnid=? and articleid=? ");
                pstmt.setInt(1,siteID);
                pstmt.setInt(2,columnID);
                pstmt.setInt(3,articleID);
                pstmt.executeUpdate();
                pstmt.close();

                if (recommends!=null) {
                    String recommend_SQL = null;
                    if (recommends.size()>0) {
                        List marks = new ArrayList();
                        pstmt = conn.prepareStatement("select id,siteid,columnid,modelid,markid,markname from tbl_recommend_article where siteid=? and columnid=? and articleid=? ");
                        pstmt.setInt(1,siteID);
                        pstmt.setInt(2,columnID);
                        pstmt.setInt(3,articleID);
                        ResultSet rs = pstmt.executeQuery();
                        while(rs.next()){
                            relatedArticle relatedArticle = new relatedArticle();
                            relatedArticle.setPageid(rs.getInt("modelid"));
                            relatedArticle.setJointedID(rs.getInt("markid"));
                            relatedArticle.setChineseName(rs.getString("markname"));
                            marks.add(relatedArticle);
                        }
                        rs.close();
                        pstmt.close();

                        //获取标记所在的模板列表
                        List t_recommends = new ArrayList();
                        for(int ii=0;ii<recommends.size();ii++) {
                            relatedArticle relatedArticle = (relatedArticle) recommends.get(ii);
                            if (!t_recommends.contains(relatedArticle)) t_recommends.add(relatedArticle);
                        }

                        //向tbl_recommend_article表中保存信息
                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            recommend_SQL = "insert into tbl_recommend_article(siteid,columnid,modelid,markid,markname,articleid,createdate,lastupdate,id) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            recommend_SQL = "insert into tbl_recommend_article(siteid,columnid,modelid,markid,markname,articleid,createdate,lastupdate) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?)";
                        else
                            recommend_SQL = "insert into tbl_recommend_article(siteid,columnid,modelid,markid,markname,articleid,createdate,lastupdate) " +
                                    "values(?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(recommend_SQL);
                        for(int ii=0;ii<t_recommends.size();ii++) {
                            relatedArticle relatedArticle = (relatedArticle)t_recommends.get(ii);
                            pstmt.setInt(1,article.getSiteID());
                            pstmt.setInt(2,article.getColumnID());
                            pstmt.setInt(3,relatedArticle.getPageid());
                            pstmt.setInt(4,relatedArticle.getJointedID());
                            pstmt.setString(5,relatedArticle.getChineseName());
                            pstmt.setInt(6,articleID);
                            pstmt.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
                            pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
                            if (cpool.getType().equals("oracle")) {
                                pstmt.setInt(9,sequnceMgr.getSequenceNum("Article"));
                            }
                            pstmt.executeUpdate();
                        }
                        pstmt.close();
                    }
                }

                //删除原来的文章轮换图片并增加新的文章图片轮换 by feixiang 2008-11-24
                if(turnlist != null && turnlist.size() > 0){
                    int turnid = 0;
                    String turnpic_SQL = "";
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        turnpic_SQL = "insert into tbl_turnpic(articleid,picname,createdate,notes,id) values(?,?,?,?,?)";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        turnpic_SQL = "insert into tbl_turnpic(articleid,picname,createdate,notes) values(?,?,?,?)";
                    else
                        turnpic_SQL = "insert into tbl_turnpic(articleid,picname,createdate,notes,id) values(?,?,?,?,?)";
                    pstmt = conn.prepareStatement(turnpic_SQL);
                    for(int i = 0; i < turnlist.size(); i++){
                        Turnpic tpic = (Turnpic)turnlist.get(i);
                        pstmt.setInt(1,articleID);
                        pstmt.setString(2,tpic.getPicname());
                        pstmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
                        pstmt.setString(4,StringUtil.gb2isoindb(tpic.getNotes()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(5,sequnceMgr.getSequenceNum("TurnPic"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //加入多媒体文件的入库信息
                if(mmfiles != null && mmfiles.size() > 0){
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MYSQL);
                    for(int i = 0; i < mmfiles.size(); i++){
                        Multimedia mult = (Multimedia)mmfiles.get(i);
                        pstmt.setInt(1,mult.getSiteid());
                        pstmt.setInt(2,articleID);
                        pstmt.setString(3,mult.getDirname());
                        pstmt.setString(4,mult.getFilepath());
                        pstmt.setString(5,mult.getOldfilename());
                        pstmt.setString(6,mult.getNewfilename());
                        pstmt.setInt(7,mult.getEncodeflag());
                        pstmt.setInt(8,0);
                        pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(10, sequnceMgr.getSequenceNum("Multimedia"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }

                //创建LOG
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR) - 1900;
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DATE);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
                pstmt.setInt(1, siteID);
                pstmt.setInt(2, columnID);
                pstmt.setInt(3, articleID);
                pstmt.setString(4, article.getEditor());
                pstmt.setInt(5, 2);
                pstmt.setTimestamp(6, article.getCreateDate());
                pstmt.setString(7, maintitle);
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    int logid = sequnceMgr.getSequenceNum("Log");
                    pstmt.setLong(9, logid);
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //删除原来文章录入时定义的文章引用关系
                pstmt = conn.prepareStatement(DELETE_ALL_REFERS_ARTICLE);
                pstmt.setInt(1,articleID);
                pstmt.setInt(2, columnID);
                pstmt.setInt(3, siteID);
                pstmt.executeUpdate();
                pstmt.close();

                //将录入的文章插入文章引用表,此篇文章可以由其他栏目引用
                List columnIdsList = getReferArticleColumnIds(article.getColumnID(),pubcolumns);
                String ids = "";
                ResultSet rs=null;
                if ((columnIdsList != null) && (columnIdsList.size() > 0)) {
                    for (int i = 0; i < columnIdsList.size(); i++) {
                        Column refersArticle = (Column)columnIdsList.get(i);
                        Column column = columnMgr.getColumn(refersArticle.getID());
                        int targetsiteid = column.getSiteID();

                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE);
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MSSQL);
                        else
                            pstmt = conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_MYSQL);
                        pstmt.setInt(1, articleID);
                        pstmt.setInt(2, article.getSiteID());
                        pstmt.setInt(3, targetsiteid);
                        pstmt.setInt(4, column.getID());
                        pstmt.setInt(5, article.getColumnID());
                        pstmt.setString(6, StringUtil.gb2iso4View(column.getCName()));
                        pstmt.setTimestamp(7, article.getCreateDate());
                        pstmt.setString(8, maintitle);
                        pstmt.setInt(9, article.getStatus());
                        pstmt.setInt(10, article.getPubFlag());
                        pstmt.setInt(11, article.getAuditFlag());
                        pstmt.setInt(12, column.getUseArticleType());
                        pstmt.setInt(13,1);                       //文章录入时定义的文章引用欢喜
                        pstmt.setString(14,article.getEditor());
                        pstmt.setTimestamp(15,new Timestamp(System.currentTimeMillis()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setLong(16, sequnceMgr.getSequenceNum("RefersArticle"));
                            pstmt.executeUpdate();
                            pstmt.close();
                        }else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else {
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                }
                conn.commit();
            } catch (Exception e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
            } finally {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            errcode = -2;
            throw new ExtendAttrException("Database exception: can't rollback?");
        }

        return errcode;
    }

    public String PrintXML(Node node, StringBuffer content) throws ExtendAttrException {
        int type = node.getNodeType();
        switch (type) {
            //根节点
            case Node.DOCUMENT_NODE: {
                content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                PrintXML(((org.w3c.dom.Document) node).getDocumentElement(), content);
                break;
            }

            //带属性的节点
            case Node.ELEMENT_NODE: {
                content.append("<");
                content.append(node.getNodeName());
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Node attr = attrs.item(i);
                    try {
                        content.append(" " + attr.getNodeName() +
                                "=\"" + new String((attr.getNodeValue()).getBytes("GBK"), "iso-8859-1") + "\"");
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                content.append(">");

                NodeList children = node.getChildNodes();
                if (children != null) {
                    int len = children.getLength();
                    for (int i = 0; i < len; i++)
                        PrintXML(children.item(i), content);
                }

                break;
            }

            // handle entity reference nodes
            case Node.ENTITY_REFERENCE_NODE: {
                content.append("&");
                content.append(node.getNodeName());
                content.append(";");
                break;
            }

            //CDATA部分
            case Node.CDATA_SECTION_NODE: {
                content.append("<![CDATA[");
                try {
                    content.append(new String(node.getNodeValue().getBytes("GBK"), "iso-8859-1"));
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                content.append("]]>");
                break;
            }

            //节点值
            case Node.TEXT_NODE: {
                try {
                    content.append(new String(node.getNodeValue().getBytes("GBK"), "iso-8859-1"));
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            }

            //处理指令
            case Node.PROCESSING_INSTRUCTION_NODE: {
                content.append("<?");
                content.append(node.getNodeName());
                String data = node.getNodeValue();
                {
                    content.append(" ");
                    try {
                        content.append(new String(data.getBytes("GBK"), "iso-8859-1"));
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                content.append("?>");
                break;
            }
        }

        if (type == Node.ELEMENT_NODE) {
            //content.append("\n");
            content.append("</");
            content.append(node.getNodeName());
            content.append(">");
        }
        return content.toString();
    }

    //获得某栏目所有主属性和扩展属性(为模板服务)
    public List getAttrForTemplate(int columnID) throws ExtendAttrException {
        List attrList = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            attrList.add("ARTICLE_MAINTITLE,文章标题");
            attrList.add("ARTICLE_VICETITLE,副标题");
            attrList.add("ARTICLE_CONTENT,文章内容");
            attrList.add("ARTICLE_AUTHOR,文章作者");
            attrList.add("ARTICLE_SOURCE,文章来源");
            attrList.add("ARTICLE_KEYWORD,文章关键字");
            attrList.add("ARTICLE_INDEXNUM,文章索引号");
            attrList.add("ARTICLE_DOWNLOAD,文章附件");
            attrList.add("INCLUDE_FILE,加入包含文件");
            attrList.add("CLICKNUM,文章点击数");
            //attrList.add("IMGZUHECONTENT,图片显示");
            //attrList.add("TEXTZUHECONTENT,前台录入文字组合内容");
            Column column = columnMgr.getColumn(columnID);
            int isDefine = column.getDefineAttr();
            int isProduct = column.getIsProduct();

            if (isProduct == 1) {
                attrList.add("PRODUCT_SALEPRICE,商品售价");
                attrList.add("PRODUCT_VIPPRICE,VIP售价");
                attrList.add("PRODUCT_INPRICE,商品进价");
                attrList.add("PRODUCT_MARKETPRICE,商品市场价");
                attrList.add("PRODUCT_SCORE,积分");
                attrList.add("PRODUCT_VOUCHER,消费卷");
                attrList.add("PRODUCT_STOCK,商品库存");
                attrList.add("PRODUCT_WEIGHT,商品重量");
                attrList.add("PRODUCT_BRAND,商品生产商");
                attrList.add("PRODUCT_PIC,商品小图片");
                attrList.add("PRODUCT_BIGPIC,商品大图片");
            }

            if (isDefine == 1) {
                String xmlTemplate = getXMLTemplate(columnID);
                if (xmlTemplate != null && xmlTemplate.length() > 0) {
                    SAXBuilder builder = new SAXBuilder();
                    Reader in = new StringReader(xmlTemplate);
                    org.jdom.Document doc = builder.build(in);
                    List nodeList = doc.getRootElement().getChildren();
                    for (int i = 0; i < nodeList.size(); i++) {
                        org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                        attrList.add(e.getName() + "," + e.getText());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return attrList;
    }

    public List getEnglishAttrForTemplate(int columnID) throws ExtendAttrException {
        List attrList = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            attrList.add("ARTICLE_MAINTITLE,MainTitle");
            attrList.add("ARTICLE_VICETITLE,ViceTitle");
            attrList.add("ARTICLE_CONTENT,Content");
            attrList.add("ARTICLE_AUTHOR,Author");
            attrList.add("ARTICLE_SOURCE,Source");
            attrList.add("IMGZUHECONTENT,前台录入图片组合内容");
            attrList.add("TEXTZUHECONTENT,前台录入文字组合内容");
            Column column = columnMgr.getColumn(columnID);
            int isDefine = column.getDefineAttr();
            int isProduct = column.getIsProduct();

            if (isProduct == 1) {
                attrList.add("PRODUCT_SALEPRICE,SalePrice");
                attrList.add("PRODUCT_INPRICE,InPrice");
                attrList.add("PRODUCT_MARKETPRICE,MarketPrice");
                attrList.add("PRODUCT_STOCK,Stock");
                attrList.add("PRODUCT_WEIGHT,Weight");
                attrList.add("PRODUCT_BRAND,Brand");
                attrList.add("PRODUCT_PIC,SmallPicture");
                attrList.add("PRODUCT_BIGPIC,BigPicture");
            }

            if (isDefine == 1) {
                String xmlTemplate = getXMLTemplate(columnID);
                if (xmlTemplate != null && xmlTemplate.length() > 0) {
                    SAXBuilder builder = new SAXBuilder();
                    Reader in = new StringReader(xmlTemplate);
                    org.jdom.Document doc = builder.build(in);
                    List nodeList = doc.getRootElement().getChildren();
                    for (int i = 0; i < nodeList.size(); i++) {
                        org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                        attrList.add(e.getName() + "," + StringUtil.gb2iso4View(e.getText()));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return attrList;
    }

    //获得某栏目所有扩展属性(为模板服务)
    public String getExtendAttrEName(int columnID) throws ExtendAttrException {
        StringBuffer extend = new StringBuffer();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        if (columnID > 0) {
            try {
                Column column = columnMgr.getColumn(columnID);
                if (column.getDefineAttr() == 1) {
                    String xmlTemplate = getXMLTemplate(columnID);
                    if (xmlTemplate != null && xmlTemplate.length() > 0) {
                        SAXBuilder builder = new SAXBuilder();
                        Reader in = new StringReader(xmlTemplate);
                        org.jdom.Document doc = builder.build(in);
                        List nodeList = doc.getRootElement().getChildren();
                        for (int i = 0; i < nodeList.size(); i++) {
                            org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                            extend.append(e.getName() + ",");
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return extend.length() > 0 ? extend.toString().substring(0, extend.length() - 1) : "";
    }

    //获得某栏目所有主属性和扩展属性(为文章列表标记服务)
    public List getAttrForStyle(int columnID) throws ExtendAttrException {
        List attrList = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            attrList.add("<a href=<%%URL%%>><%%DATA%%></a>,文章标题（URL）");
            attrList.add("<%%DATA%%>,文章标题");
            attrList.add("<%%FULLTITLE%%>,文章完整标题");
            attrList.add("<a href=<%%URL%%>><%%VICETITLE%%></a>,副标题（URL）");
            attrList.add("<%%VICETITLE%%>,副标题");
            attrList.add("<%%AUTHOR%%>,文章作者");
            attrList.add("<%%ASOURCE%%>,文章来源");
            attrList.add("<a href=<%%URL%%>><%%ASUMMARY%%></a>,文章摘要（URL）");
            attrList.add("<%%ASUMMARY%%>,文章摘要");
            attrList.add("<%%PT%%>,文章日期");
            attrList.add("<%%YEAR%%>-<%%MONTH%%>-<%%DAY%%> <%%HOUR%%>:<%%MINUTE%%>:<%%SECOND%%>,发布时间");
            attrList.add("<%%ARTICLEPIC%%>,文章图片");
            attrList.add("<%%ARTICLEID%%>,文章ID");
            attrList.add("<%%ARTICLE_PATH%%>,文章路径");
            attrList.add("<%%DOCLEVEL%%>,文章权重");
            attrList.add("<%%CLICKNUM%%>,文章点击数");
            attrList.add("<%%ARTICLE_STATUS%%>,文章状态");
            attrList.add("<a href=<%%DOWNFILE%%>>下载</a>,相关下载（URL）");
            attrList.add("<%%DOWNFILE%%>,相关下载");
            attrList.add("<%%sitename%%>,站点名称");
            attrList.add("<%%COLUMNID%%>,栏目ID");
            attrList.add("<%%COLUMNNAME%%>,栏目名称");
            attrList.add("<a href=<%%COLUMNURL%%>></a>,栏目URL");
            attrList.add("<%%PARENTCOLUMNNAME%%>,父目名称");
            attrList.add("<%%FIRSTCOLUMNID%%>,一级栏目ID");
            attrList.add("<%%FIRSTCOLUMNNAME%%>,一级目名称");
            attrList.add("<a href=<%%FIRSTCOLUMNURL%%>></a>,一级目URL");
            attrList.add("<%%NEW%%>,新文章标记");
            attrList.add("<a href=<%%RELATED_URL%%>></a>,父文章URL");
            attrList.add("<%%RELATED_ID%%>,父文章ID");
            attrList.add("<%%RELATED_DATA%%>,父文章标题");
            attrList.add("<%%RELATED_VICETITLE%%>,父文章副标题");
            attrList.add("<%%RELATED_SOURCE%%>,父文章来源");
            attrList.add("<%%RELATED_SUMMARY%%>,父文章摘要");
            attrList.add("<%%RELATED_AUTHOR%%>,父文章作者");
            attrList.add("<%%ENGLISH_MONTH%%>,英文月份");
            attrList.add("<%%BUYCAR%%>,加入购物车");
            //-------------------------chongyan  begin
            attrList.add("<%%POPLAYERS%%>,弹出图层");
            attrList.add("<!--BEGIN-->,分列开始标记");
            attrList.add("<!--END-->,分列结束标记");
            attrList.add("<%%PRODUCT_PIC%%>,商品小图片");
            attrList.add("<%%PRODUCT_BIGPIC%%>,商品大图片");

            //-----------------------------   end
            Column column = columnMgr.getColumn(columnID);
            int isDefine = column.getDefineAttr();
            int isProduct = column.getIsProduct();

            if (isProduct == 1) {
                attrList.add("<%%PRODUCT_SALEPRICE%%>,商品售价");
                attrList.add("<%%PRODUCT_VIPPRICE%%>,VIP售价");
                attrList.add("<%%PRODUCT_INPRICE%%>,商品进价");
                attrList.add("<%%PRODUCT_MARKETPRICE%%>,商品市场价");
                attrList.add("<%%PRODUCT_STOCK%%>,商品库存");
                attrList.add("<%%PRODUCT_WEIGHT%%>,商品重量");
                attrList.add("<%%PRODUCT_BRAND%%>,商品生产商");
            }

            if (isDefine == 1) {
                String xmlTemplate = getXMLTemplate(columnID);
                if (xmlTemplate != null && xmlTemplate.length() > 0) {
                    SAXBuilder builder = new SAXBuilder();
                    Reader in = new StringReader(xmlTemplate);
                    org.jdom.Document doc = builder.build(in);

                    List nodeList = doc.getRootElement().getChildren();
                    for (int i = 0; i < nodeList.size(); i++) {
                        org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                        attrList.add("<%%" + e.getName() + "%%>," + StringUtil.gb2iso4View(e.getText()));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return attrList;
    }

    //获得某栏目所有扩展属性(为文章列表标记中的显示字数服务)
    public List getExtendAttrForMark(int columnID, XMLProperties properties, String mark) throws ExtendAttrException {
        List attrList = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            Column column = columnMgr.getColumn(columnID);
            if (column != null) {
                int isDefine = column.getDefineAttr();
                if (isDefine == 1) {
                    String xmlTemplate = getXMLTemplate(columnID);
                    if (xmlTemplate != null && xmlTemplate.length() > 0) {
                        SAXBuilder builder = new SAXBuilder();
                        Reader in = new StringReader(xmlTemplate);
                        org.jdom.Document doc = builder.build(in);

                        List nodeList = doc.getRootElement().getChildren();
                        for (int i = 0; i < nodeList.size(); i++) {
                            org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                            String CName = StringUtil.gb2iso4View(e.getText());
                            if (properties != null && mark != null && mark.indexOf("<" + e.getName() + ">") > -1)
                                attrList.add(CName + "<input name=\"" + e.getName() + "\" value=\"" + properties.getProperty(properties.getName().concat("." + e.getName())) + "\" size=4 maxlength=3>");
                            else
                                attrList.add(CName + "<input name=\"" + e.getName() + "\" value=0 size=4 maxlength=3>");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return attrList;
    }

    //判断两个栏目的扩展属性是否相同
    public boolean querySameExtendAttr(int fromColumnID, int toColumnID) throws ExtendAttrException {
        boolean same = true;
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            Column column1 = columnMgr.getColumn(fromColumnID);
            int isDefine1 = column1.getDefineAttr();
            Column column2 = columnMgr.getColumn(toColumnID);
            int isDefine2 = column2.getDefineAttr();

            if (isDefine1 == 0 && isDefine2 == 0) {
                return true;
            }

            if (isDefine1 == 1 && isDefine2 == 1)   //比较扩展属性是否相同
            {
                String xmlTemplate1 = getXMLTemplate(fromColumnID);
                String xmlTemplate2 = getXMLTemplate(toColumnID);

                if (xmlTemplate1 != null && xmlTemplate2 != null && xmlTemplate1.trim().length() > 0 && xmlTemplate2.trim().length() > 0) {
                    SAXBuilder builder = new SAXBuilder();
                    Reader in1 = new StringReader(xmlTemplate1);
                    Reader in2 = new StringReader(xmlTemplate2);
                    org.jdom.Document doc1 = builder.build(in1);
                    org.jdom.Document doc2 = builder.build(in2);

                    List nodeList1 = doc1.getRootElement().getChildren();
                    List nodeList2 = doc2.getRootElement().getChildren();
                    if (nodeList1.size() == 0 && nodeList2.size() == 0) {
                        return true;
                    }

                    if (nodeList1.size() == nodeList2.size()) {
                        String[] arr1 = new String[nodeList1.size()];
                        String[] arr2 = new String[nodeList2.size()];

                        for (int i = 0; i < nodeList1.size(); i++) {
                            org.jdom.Element e1 = (org.jdom.Element) nodeList1.get(i);
                            org.jdom.Element e2 = (org.jdom.Element) nodeList2.get(i);
                            arr1[i] = e1.getName() + e1.getAttributeValue("datatype");
                            arr2[i] = e2.getName() + e2.getAttributeValue("datatype");
                        }

                        Arrays.sort(arr1);
                        Arrays.sort(arr2);

                        for (int i = 0; i < arr1.length; i++) {
                            if (!arr1[i].equalsIgnoreCase(arr2[i])) {
                                same = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return same;
    }

    public String getXMLTemplate(int columnID) {
        String xmlTemplate = null;
        IColumnManager columnMgr = ColumnPeer.getInstance();
        Column column=null;

        try {
            int parentColumnID;
            int siteID = columnMgr.getColumn(columnID).getSiteID();
            Tree columnTree = TreeManager.getInstance().getSiteTree(siteID);
            node[] treeNodes = columnTree.getAllNodes();

            while (columnID != 0) {
                column = columnMgr.getColumn(columnID);
                xmlTemplate = column.getXMLTemplate();
                int isDefine = column.getDefineAttr();
                if (isDefine == 1 && xmlTemplate != null && xmlTemplate.trim().length() > 0) break;

                int nodenum = 0;
                while (treeNodes[nodenum].getId() != columnID) {
                    nodenum++;
                }
                parentColumnID = treeNodes[nodenum].getLinkPointer();
                columnID = parentColumnID;
            }
        }
        catch (ColumnException e) {
            e.printStackTrace();
        }
        return xmlTemplate;
    }

    public String generateIndexNumOfBjsjs(String company,String username,int columnID) throws ExtendAttrException {
        //生成文章索引号
        String index_str = null;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        try {
            String prefix = null;
            int num = 0;
            int deptid = 0;
            conn=cpool.getConnection();
            pstmt = conn.prepareStatement("select t.userid,t.departcode,td.ename,td.cname,td.indexrules,td.indexnum,t.department,td.id,t.deptid from tbl_members t,tbl_department td where t.departcode=td.ename and t.userid=?");
            pstmt.setString(1,username);
            rs =pstmt.executeQuery();
            if (rs.next()) {
                prefix = rs.getString("indexrules");
                num = rs.getInt("indexnum");
                deptid = rs.getInt("id");
            }
            rs.close();
            pstmt.close();
            //11H032/ZK-2018-100028
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            //流水号长度不足6位，在流水号前面补0补足到长度为6位
            String s_num = String.valueOf(num+1);
            if (s_num.length()<6) {
                for(int ii=0;ii<(6-s_num.length());ii++) {
                    s_num = "0" + s_num;
                }
            }
            if (prefix!=null) {
                index_str = "11" + prefix + "/ZK" + "-" + simpleDateFormat.format(now) + "-" + s_num;
                //tbl_department表的流水号字段增加1
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_department set indexnum=? where id=?");
                pstmt.setInt(1,num+1);
                pstmt.setInt(2,deptid);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        } finally {
            cpool.freeConnection(conn);
        }

        return index_str;
    }

    public String getExtendAttrForArticle(String company,String username,int columnID, int articleID) throws ExtendAttrException {
        StringBuffer content = new StringBuffer();
        Hashtable hash = new Hashtable();
        if (articleID > 0) {
            List attrList = getArticleAttr(articleID);
            for (int i = 0; i < attrList.size(); i++) {
                String value = "";
                ExtendAttr extend = (ExtendAttr) attrList.get(i);
                if (extend.getDataType() == 1 && extend.getStringValue() != null)
                    value = StringUtil.gb2iso4View(extend.getStringValue());
                else if (extend.getDataType() == 2)
                    value = String.valueOf(extend.getNumericValue());
                else if (extend.getDataType() == 3 && extend.getTextValue() != null)
                    value = StringUtil.gb2iso4View(extend.getTextValue());
                else if (extend.getDataType() == 4)
                    value = String.valueOf(extend.getFloatValue());

                if (value != null) value = StringUtil.replace(value, "\"", "&quot;");
                if (value != null) value = StringUtil.replace(value, "\'", "&apos;");
                hash.put(extend.getEName(), value);
            }
        }

        StringBuffer scripts = new StringBuffer();
        String xmlTemplate = getXMLTemplate(columnID);
        if (xmlTemplate != null && xmlTemplate.trim().length() > 0) {
            List textList = new ArrayList();
            List areaList = new ArrayList();
            SAXBuilder builder = new SAXBuilder();

            try {
                Reader in = new StringReader(xmlTemplate);
                org.jdom.Document doc = builder.build(in);

                List nodeList = doc.getRootElement().getChildren();
                for (int i = 0; i < nodeList.size(); i++) {
                    org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                    int type = Integer.parseInt(e.getAttributeValue("type"));

                    ExtendAttr extend = new ExtendAttr();
                    extend.setCName(e.getText());
                    extend.setEName(e.getName());
                    extend.setControlType(type);
                    extend.setDefaultValue(e.getAttributeValue("defaultval"));
                    extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));

                    //1单行文本，2滚动多行文本，3上传文件，4富文本编辑器，5下拉列表，6日期与时间
                    if (type == 1 || type == 3 ||type == 5 || type == 6)
                        textList.add(extend);
                    else
                        areaList.add(extend);
                }

                int item_num = textList.size()  + areaList.size();
                int itemNumOfLine = 4;
                if (item_num > 0) {
                    content.append("<table border=0 width=\"100%\">\r\n");
                    int count = itemNumOfLine - textList.size() % itemNumOfLine;
                    if (textList.size() % itemNumOfLine == 0) count = 0;

                    for (int i = 0; i < textList.size() + count; i++) {
                        if (i % itemNumOfLine == 0) content.append("<tr height=24>\r\n");
                        if (i < textList.size()) {
                            ExtendAttr extend = (ExtendAttr) textList.get(i);
                            String type = "";
                            if (extend.getControlType() == 3) type = "(<a href=javascript:upload_attrpic_onclick(\"" + extend.getEName() + "\")>图</a>)";
                            String cname = StringUtil.gb2iso4View(extend.getCName());

                            //读出当前属性的值
                            String value = "";
                            if (articleID > 0 && hash.get(extend.getEName()) != null) value = hash.get(extend.getEName()).toString();
                            if (extend.getControlType() == 6)               //日期和时间输入框
                                content.append("<td width=\"33%\" align=\"left\">" + cname + type + "：<input  class=\"easyui-datetimebox\" name=" + extend.getEName() + " size=\"25\" value=\"" + value + "\"></td>\r\n");
                            else if (extend.getControlType() == 5)  {       //下拉列表输入
                                String option_key_values = extend.getDefaultValue();
                                JSONObject jsStr = JSONObject.fromObject(option_key_values);
                                int listtype = jsStr.getInt("type");
                                JSONArray jsonArray = jsStr.getJSONArray("data");
                                List<zTreeNodeObj> zTreeNodeObjs = JSON_Str_To_ObjArray.Transfer_JsonStr_To_ObjArray(jsonArray);
                                List typeOptions = JSON_Str_To_ObjArray.genOptionsStr(zTreeNodeObjs);
                                if (listtype == 0) {        //单选列表
                                    content.append("<td width=\"33%\" align=\"left\">" + cname + type + ":");
                                    content.append("<select size=\"1\" id=\"selid" + i + "\" name=\"" + extend.getEName() + "\" class=\"tine\" style=\"width:200\">\n");
                                    content.append("<option value=\"-1\">请选择</option>");
                                    String tempbuf="";
                                    for (int ii=0;ii<typeOptions.size();ii++) {
                                        tempbuf = tempbuf + typeOptions.get(ii);
                                    }
                                    String[] ss = tempbuf.split("\r\n");
                                    for(int ii=1; ii<ss.length; ii++) {
                                        String tbuf = ss[ii];
                                        int posi = tbuf.indexOf("|");
                                        String text = tbuf.substring(0,posi);
                                        String keyval = tbuf.substring(posi+1);
                                        if (keyval.equalsIgnoreCase(value))
                                            content.append("<option value=\"" + keyval + "\"" + " selected>" + text + "</option>");
                                        else
                                            content.append("<option value=\"" + keyval + "\">" + text + "</option>");
                                    }
                                    content.append("</select>\r\n");
                                    content.append("</td>\r\n");
                                } else {                   //多选列表
                                    int itemnum = jsonArray.size();
                                    String textValues = listElement.getTextValue(zTreeNodeObjs,value);
                                    content.append("<td width=\"33%\" align=\"left\"><a href=\"#\" onclick=javascript:getMoreType(" + columnID + "," + itemnum + ",\"" + extend.getEName() +"\");>" + cname + type + "</a>");
                                    content.append("：<input type=\"text\" class=tine name=\"" + extend.getEName() + "txt\" size=30 value=\"" + textValues + "\" readonly=\"readonly\">");
                                    content.append("<input type=\"hidden\" class=tine name=\"" + extend.getEName() + "\" size=30 value=\"" + value + "\" readonly=\"readonly\">");
                                    content.append("</td>\r\n");
                                }
                            } else {                                         //单行文本输入，可以是字符型、数字和小数

                                content.append("<td width=\"33%\" align=\"left\">" + cname + type + "：<input class=tine name=" + extend.getEName() + " size=30 value=\"" + value + "\"></td>\r\n");
                            }
                            //组合JAVASCRIPT，只控制文本框的数据类型，整型及小数型
                            if (extend.getControlType() == 1 && (extend.getDataType() == 2 || extend.getDataType() == 4)) {
                                scripts.append("var ret = true;\r\n");
                                scripts.append("if (frm." + extend.getEName() + ".value != \"\")\r\n{\r\n");
                                if (extend.getDataType() == 1) {
                                    scripts.append("var regstr = /[^0-9]/gi;\r\n");
                                    scripts.append("if (regstr.exec(str) != null)\r\n{\r\n");
                                    scripts.append("alert(" + cname + "请输入整型数据)\r\n");
                                } else {
                                    scripts.append("var regstr = /[^0-9.]/gi;\r\n");
                                    scripts.append("if (regstr.exec(frm." + extend.getEName() + ".value) != null)\r\n{\r\n");
                                    scripts.append("alert(" + cname + "请输入小数型数据)\r\n");
                                }
                                scripts.append("frm." + extend.getEName() + ".focus();\r\n");
                                scripts.append("ret = false;\r\n");
                                scripts.append("}}\r\n");
                                scripts.append("return ret;\r\n");
                            } else {
                                scripts.append("return true;\r\n");
                            }
                        } else {
                            content.append("<td width=\"33%\"></td>");
                        }
                        if ((i + 1) % itemNumOfLine == 0) content.append("</tr>\r\n");
                    }

                    count = 1;
                    if (areaList.size() % itemNumOfLine == 0) count = 0;
                    //content.append("<table border=0 width=\"100%\">\r\n");
                    for (int i = 0; i < areaList.size() + count; i++) {
                        if (i % itemNumOfLine == 0) content.append("<tr>\r\n");
                        if (i < areaList.size()) {
                            ExtendAttr extend = (ExtendAttr) areaList.get(i);
                            String cname = StringUtil.gb2iso4View(extend.getCName());

                            //读出当前属性的值
                            String value = "";
                            if (articleID > 0 && hash.get(extend.getEName()) != null)
                                value = hash.get(extend.getEName()).toString();

                            if (extend.getControlType() == 4) {
                                content.append("<td width=\"33%\">" + cname + "：<input type=button value=编辑 onclick=Editor('" +extend.getEName() + "');>\r\n"+  "<br><textarea class=tine rows=10 cols=60 name=" + extend.getEName() + ">" + value + "</textarea>\r\n");
                            } else {
                                content.append("<td width=\"33%\">" + cname + "：<br><textarea class=tine rows=11 cols=60 name=" + extend.getEName() + ">" + value + "</textarea>\r\n");
                            }
                            content.append("</td>\r\n");
                        } else {
                            content.append("<td width=\"33%\"></td>");
                        }
                        if ((i + 1) % itemNumOfLine == 0) content.append("</tr>\r\n");
                    }
                    content.append("</table>");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (scripts.length() == 0) scripts.append("return true;\r\n");
        content.append("<script language=javascript>\r\nfunction checkExtendAttr(frm)\r\n{\r\n" + scripts.toString() + "}\r\n</script>\r\n");
        return content.toString();
    }

    public String getExtendAttrForAudit(int columnID, int articleID) throws ExtendAttrException {
        StringBuffer content = new StringBuffer();

        Hashtable hash = new Hashtable();
        if (articleID > 0) {
            List attrList = getArticleAttr(articleID);
            for (int i = 0; i < attrList.size(); i++) {
                ExtendAttr extend = (ExtendAttr) attrList.get(i);
                if (extend.getDataType() == 1 && extend.getStringValue() != null)
                    hash.put(extend.getEName(), StringUtil.gb2iso4View(extend.getStringValue()));
                else if (extend.getDataType() == 2)
                    hash.put(extend.getEName(), String.valueOf(extend.getNumericValue()));
                else if (extend.getDataType() == 3 && extend.getTextValue() != null)
                    hash.put(extend.getEName(), extend.getTextValue());
                else if (extend.getDataType() == 4)
                    hash.put(extend.getEName(), String.valueOf(extend.getFloatValue()));
            }
        }

        String xmlTemplate = getXMLTemplate(columnID);
        if (xmlTemplate != null && xmlTemplate.trim().length() > 0) {
            List textList = new ArrayList();
            List areaList = new ArrayList();
            SAXBuilder builder = new SAXBuilder();

            try {
                Reader in = new StringReader(xmlTemplate);
                org.jdom.Document doc = builder.build(in);

                List nodeList = doc.getRootElement().getChildren();
                for (int i = 0; i < nodeList.size(); i++) {
                    org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                    int type = Integer.parseInt(e.getAttributeValue("type"));

                    ExtendAttr extend = new ExtendAttr();
                    extend.setCName(e.getText());
                    extend.setEName(e.getName());
                    extend.setControlType(type);
                    extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));

                    if (type == 1 || type == 3)
                        textList.add(extend);
                    else
                        areaList.add(extend);
                }

                if (textList.size() + areaList.size() > 0) {
                    content.append("<table border=0 width=\"100%\">\r\n");
                    int count = 4 - textList.size() % 4;
                    if (textList.size() % 4 == 0) count = 0;

                    for (int i = 0; i < textList.size() + count; i++) {
                        if (i % 4 == 0) content.append("<tr height=24>\r\n");
                        if (i < textList.size()) {
                            ExtendAttr extend = (ExtendAttr) textList.get(i);
                            String cname = StringUtil.gb2iso4View(extend.getCName());

                            //读出当前属性的值
                            String value = "";
                            if (articleID > 0 && hash.get(extend.getEName()) != null) {
                                value = hash.get(extend.getEName()).toString();
                            }
                            content.append("<td width=\"25%\">" + cname + "：<input class=tine name=" + extend.getEName() + " size=16 value=\"" + value + "\" disabled></td>\r\n");
                        } else {
                            content.append("<td width=\"25%\"></td>");
                        }
                        if ((i + 1) % 4 == 0) content.append("</tr>\r\n");
                    }

                    count = 1;
                    if (areaList.size() % 2 == 0) count = 0;
                    for (int i = 0; i < areaList.size() + count; i++) {
                        if (i % 2 == 0) content.append("<tr>\r\n");
                        if (i < areaList.size()) {
                            ExtendAttr extend = (ExtendAttr) areaList.get(i);
                            String cname = StringUtil.gb2iso4View(extend.getCName());

                            //读出当前属性的值
                            String value = "";
                            if (articleID > 0 && hash.get(extend.getEName()) != null)
                                value = StringUtil.gb2iso4View(hash.get(extend.getEName()).toString());

                            content.append("<td colspan=2 width=\"50%\">" + cname + "：<br><textarea class=tine rows=6 cols=60 name=" + extend.getEName() + " disabled>" + value + "</textarea>\r\n");
                            content.append("</td>\r\n");
                        } else {
                            content.append("<td colspan=2 width=\"50%\"></td>");
                        }
                        if ((i + 1) % 2 == 0) content.append("</tr>\r\n");
                    }
                    content.append("</table>\r\n");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }

    private String processContent(int referID, String content) {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();

        try {
            if (referID > 0) {
                Pattern p = null;
                Matcher m = null;
                if (content.indexOf("/webbuilder/sites/") != -1) {
                    p = Pattern.compile("<img [^<>]*/webbuilder/sites/[^<>]*>", Pattern.CASE_INSENSITIVE);
                    m = p.matcher(content);
                    while (m.find()) {
                        String imgs = content.substring(m.start(), m.end());
                        String left = imgs.substring(0, imgs.toLowerCase().indexOf("/webbuilder/sites/") + 18);
                        String right = imgs.substring(imgs.toLowerCase().indexOf("/webbuilder/sites/") + 18);
                        String sitename = right.substring(0, right.indexOf("/"));
                        sitename = StringUtil.replace(sitename, "_", ".");
                        left = left.substring(0, left.length() - 6) + "http://";
                        imgs = left + sitename + right.substring(sitename.length());
                        content = content.substring(0, m.start()) + imgs + content.substring(m.end());
                        m = p.matcher(content);
                    }
                } else {
                    p = Pattern.compile("<img [^<>]*sites/[^<>]*>", Pattern.CASE_INSENSITIVE);
                    m = p.matcher(content);
                    while (m.find()) {
                        String imgs = content.substring(m.start(), m.end());
                        String left = imgs.substring(0, imgs.toLowerCase().indexOf("sites/") + 6);
                        String right = imgs.substring(imgs.toLowerCase().indexOf("sites/") + 6);
                        String sitename = right.substring(0, right.indexOf("/"));
                        sitename = StringUtil.replace(sitename, "_", ".");
                        left = left.substring(0, left.length() - 6) + "http://";
                        imgs = left + sitename + right.substring(sitename.length());
                        content = content.substring(0, m.start()) + imgs + content.substring(m.end());
                        m = p.matcher(content);
                    }
                }

                int startP = 0;
                String a;
                List list = new ArrayList();
                String sitename = siteMgr.getSiteName(referID);
                p = Pattern.compile("<a [^<>]*href[^<>]*>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(content);
                while (m.find()) {
                    list.add(content.substring(startP, m.start()));
                    list.add(content.substring(m.start(), m.end()));
                    startP = m.end();
                }
                list.add(content.substring(startP));

                for (int i = 0; i < (list.size() - 1) / 2; i++) {
                    a = (String) list.get(2 * i + 1);
                    if (a.toLowerCase().indexOf("http://") == -1) {
                        Pattern p1 = Pattern.compile("href\\s*=\\s*\"[^\"]*\"", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(a);
                        Pattern p2 = Pattern.compile("href\\s*=\\s*'[^']*'", Pattern.CASE_INSENSITIVE);
                        Matcher m2 = p2.matcher(a);
                        if (m1.find()) {
                            String href = a.substring(m1.start(), m1.end());
                            href = href.substring(href.indexOf("\"") + 1, href.length() - 1);
                            a = StringUtil.replace(a, href, "http://" + sitename + href);
                        } else if (m2.find()) {
                            String href = a.substring(m1.start(), m1.end());
                            href = href.substring(href.indexOf("'") + 1, href.length() - 1);
                            a = StringUtil.replace(a, href, "http://" + sitename + href);
                        } else {
                            int posi = a.toLowerCase().indexOf("href");
                            String left = a.substring(0, posi + 4);
                            String right = a.substring(posi + 4);
                            int posi1 = right.indexOf("=");
                            a = left + right.substring(0, posi1 + 1) + "http://" + sitename + right.substring(posi1 + 1);
                        }
                        list.set(2 * i + 1, a);
                    }
                }

                content = "";
                for (int i = 0; i < list.size(); i++) content += list.get(i);

                if (cpool.getCustomer().equals("linktone")) {
                    if (content.indexOf("/inc/index.jsp?/") > -1)
                        content = StringUtil.replace(content, "/inc/index.jsp?/", "/inc/index.jsp?http://" + sitename + "/");

                    if (content.indexOf("javascript:Send('/") > -1)
                        content = StringUtil.replace(content, "javascript:Send('/", "javascript:Send('http://" + sitename + "/");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    ExtendAttr load(ResultSet rs) throws SQLException {
        ExtendAttr extendAttr = new ExtendAttr();

        try {
            extendAttr.setID(rs.getInt("ID"));
            extendAttr.setArticleID(rs.getInt("ArticleID"));
            extendAttr.setDataType(rs.getInt("Type"));
            extendAttr.setEName(rs.getString("EName"));
            extendAttr.setNumericValue(rs.getInt("NumericValue"));
            extendAttr.setStringValue(rs.getString("StringValue"));
            extendAttr.setFloatValue(rs.getFloat("FloatValue"));
            extendAttr.setTextValue(DBUtil.getBigString(cpool.getType(), rs, "TextValue"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return extendAttr;
    }
    //wangjian
    public void articlelist(int articleID,Article article)
    {
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            conn=cpool.getConnection();
            conn.setAutoCommit(false);
            ISequenceManager sequnceMgr = SequencePeer.getInstance();

            String sql="insert into tbl_article_articlelist(id,articleid,markid,fontcolor,fontziti,istop) values(?,?,?,?,?,?)";


            String markids=article.getMarkid();
            String markidstr[]=null;
            String markid[]=null;
            markidstr=markids.split(",");
            for(int i=0;i<markidstr.length;i++)
            {
                markid=markidstr[i].split("-");

                for(int j=0;j<markid.length;j++)
                {
                    if(markid[j].matches("[0-9]*"))
                    {
                        pstmt=conn.prepareStatement(sql);
                        int   articlelistID = sequnceMgr.getSequenceNum("tblarticlelist");
                        pstmt.setInt(1,articlelistID);
                        pstmt.setInt(2,articleID);
                        int mkid=0;
                        try{
                            mkid=Integer.parseInt(markid[j]);
                        }catch(Exception e){
                            mkid=-1;
                        }
                        pstmt.setInt(3,mkid);
                        if(markidstr[i].indexOf("red")!=-1)
                        {
                            String fontcolor=markidstr[i].substring(markidstr[i].indexOf("red"),markidstr[i].indexOf("red")+3);
                            pstmt.setString(4,fontcolor);
                        }else{
                            pstmt.setString(4,"");
                        }
                        if(markidstr[i].indexOf("b")!=-1)
                        {
                            String fontb=markidstr[i].substring(markidstr[i].indexOf("b"),markidstr[i].indexOf("b")+1);
                            pstmt.setString(5,fontb);
                        } else{
                            pstmt.setString(5,"");
                        }
                        if(markidstr[i].indexOf("top")!=-1)
                        {
                            String fonttop=markidstr[i].substring(markidstr[i].indexOf("top"),markidstr[i].indexOf("top")+3);
                            pstmt.setString(6,fonttop);
                        }  else{
                            pstmt.setString(6,"");
                        }
                        pstmt.executeUpdate();
                        conn.commit();
                        pstmt.close();
                    }
                }
            }
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(conn!=null)
            {
                cpool.freeConnection(conn);
            }
        }
    }
    //wangjian update articlearticlelist
    public void articlelistupdate(int articleID,Article article)
    {
        Connection conn=null;
        PreparedStatement pstmt=null;
        try{
            conn=cpool.getConnection();
            conn.setAutoCommit(false);
            ISequenceManager sequnceMgr = SequencePeer.getInstance();

            String sql="insert into tbl_article_articlelist(id,articleid,markid,fontcolor,fontziti,istop) values(?,?,?,?,?,?)";

            String updatesql="update tbl_article_articlelist set markid=?, fontcolor=?,fontziti=?,istop=? where articleid="+articleID+"";
            IMarkManager markMgr = markPeer.getInstance();
            List list= markMgr.getArticleListQuery(articleID);


            String markids=article.getMarkid();
            String markidstr[]=null;
            String markid[]=null;
            markidstr=markids.split(",");
            //  获得的===数据库查到的 修改   >的插入和修改  <修改的删除
            if(markidstr.length==list.size())
            {
                for(int i=0;i<markidstr.length;i++)
                {
                    markid=markidstr[i].split("-");

                    for(int j=0;j<markid.length;j++)
                    {
                        if(markid[j].matches("[0-9]*"))
                        {
                            pstmt=conn.prepareStatement(updatesql);
                            int mkid=0;
                            try{
                                mkid=Integer.parseInt(markid[j]);
                            }catch(Exception e){
                                mkid=-1;
                            }

                            pstmt.setInt(1,mkid);
                            if(markidstr[i].indexOf("red")!=-1)
                            {
                                String fontcolor=markidstr[i].substring(markidstr[i].indexOf("red"),markidstr[i].indexOf("red")+3);
                                pstmt.setString(4,fontcolor);
                            }else{
                                pstmt.setString(4,"");
                            }
                            if(markidstr[i].indexOf("b")!=-1)
                            {
                                String fontb=markidstr[i].substring(markidstr[i].indexOf("b"),markidstr[i].indexOf("b")+1);
                                pstmt.setString(5,fontb);
                            } else{
                                pstmt.setString(5,"");
                            }
                            if(markidstr[i].indexOf("top")!=-1)
                            {
                                String fonttop=markidstr[i].substring(markidstr[i].indexOf("top"),markidstr[i].indexOf("top")+3);
                                pstmt.setString(6,fonttop);
                            }  else{
                                pstmt.setString(6,"");
                            }
                            pstmt.executeUpdate();
                            conn.commit();
                            pstmt.close();
                        }
                    }
                }
            }
            for(int i=0;i<markidstr.length;i++)
            {
                markid=markidstr[i].split("-");

                for(int j=0;j<markid.length;j++)
                {
                    if(markid[j].matches("[0-9]*"))
                    {
                        pstmt=conn.prepareStatement(sql);
                        int   articlelistID = sequnceMgr.getSequenceNum("tblarticlelist");
                        pstmt.setInt(1,articlelistID);
                        pstmt.setInt(2,articleID);
                        int mkid=0;
                        try{
                            mkid=Integer.parseInt(markid[j]);
                        }catch(Exception e){
                            mkid=-1;
                        }
                        pstmt.setInt(3,mkid);
                        if(markidstr[i].indexOf("red")!=-1)
                        {
                            String fontcolor=markidstr[i].substring(markidstr[i].indexOf("red"),markidstr[i].indexOf("red")+3);
                            pstmt.setString(4,fontcolor);
                        }else{
                            pstmt.setString(4,"");
                        }
                        if(markidstr[i].indexOf("b")!=-1)
                        {
                            String fontb=markidstr[i].substring(markidstr[i].indexOf("b"),markidstr[i].indexOf("b")+1);
                            pstmt.setString(5,fontb);
                        } else{
                            pstmt.setString(5,"");
                        }
                        if(markidstr[i].indexOf("top")!=-1)
                        {
                            String fonttop=markidstr[i].substring(markidstr[i].indexOf("top"),markidstr[i].indexOf("top")+3);
                            pstmt.setString(6,fonttop);
                        }  else{
                            pstmt.setString(6,"");
                        }
                        pstmt.executeUpdate();
                        conn.commit();
                        pstmt.close();
                    }
                }
            }
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(conn!=null)
            {
                cpool.freeConnection(conn);
            }
        }
    }
    //根据文章摸版生成对应的扩展属性
    public String getArticleTemplateExtendAttrForArticle(int columnID, int articleID,ExtendAttr ext) throws ExtendAttrException {
        StringBuffer content = new StringBuffer();

        Hashtable hash = new Hashtable();
        if (articleID > 0) {
            List attrList = getArticleAttr(articleID);
            for (int i = 0; i < attrList.size(); i++) {
                String value = "";
                ExtendAttr extend = (ExtendAttr) attrList.get(i);
                if (extend.getDataType() == 1 && extend.getStringValue() != null)
                    value = StringUtil.gb2iso4View(extend.getStringValue());
                else if (extend.getDataType() == 2)
                    value = String.valueOf(extend.getNumericValue());
                else if (extend.getDataType() == 3 && extend.getTextValue() != null)
                    value = StringUtil.gb2iso4View(extend.getTextValue());
                else if (extend.getDataType() == 4)
                    value = String.valueOf(extend.getFloatValue());

                if (value != null) value = StringUtil.replace(value, "\"", "&quot;");
                if (value != null) value = StringUtil.replace(value, "\'", "&apos;");
                hash.put(extend.getEName(), value);
            }
        }

        StringBuffer scripts = new StringBuffer();
        String xmlTemplate = getXMLTemplate(columnID);
        if (xmlTemplate != null && xmlTemplate.trim().length() > 0) {
            List textList = new ArrayList();
            List areaList = new ArrayList();
            SAXBuilder builder = new SAXBuilder();

            try {
                Reader in = new StringReader(xmlTemplate);
                org.jdom.Document doc = builder.build(in);

                List nodeList = doc.getRootElement().getChildren();

                for (int i = 0; i < nodeList.size(); i++) {

                    org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                    if(ext.getEName().equals(e.getName())){
                        int type = Integer.parseInt(e.getAttributeValue("type"));

                        ExtendAttr extend = new ExtendAttr();
                        extend.setCName(e.getText());
                        extend.setEName(e.getName());
                        extend.setControlType(type);
                        extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));

                        if (type == 1 || type == 3)
                            textList.add(extend);
                        else
                            areaList.add(extend);
                    }
                }

                if (textList.size() + areaList.size() > 0) {
                    content.append("<table border=0 width=\"100%\">\r\n");
                    int count = 4 - textList.size() % 4;
                    if (textList.size() % 4 == 0) count = 0;

                    for (int i = 0; i < textList.size() + count; i++) {
                        if (i % 4 == 0) content.append("<tr height=24>\r\n");
                        if (i < textList.size()) {
                            ExtendAttr extend = (ExtendAttr) textList.get(i);
                            String type = "";
                            if (extend.getControlType() == 3)
                                type = "(<a href=javascript:upload_attrpic_onclick(\"" + extend.getEName() + "\")>图</a>)";
                            String cname = StringUtil.gb2iso4View(extend.getCName());

                            //读出当前属性的值
                            String value = "";
                            if (articleID > 0 && hash.get(extend.getEName()) != null)
                                value = hash.get(extend.getEName()).toString();

                            content.append("<td width=\"25%\">" + cname + type + "：<input class=tine name=" + extend.getEName() + " size=16 value=\"" + value + "\"></td>\r\n");

                            //组合JAVASCRIPT，只控制文本框的数据类型，整型及小数型
                            if (extend.getControlType() == 1 && (extend.getDataType() == 2 || extend.getDataType() == 4)) {
                                scripts.append("var ret = true;\r\n");
                                scripts.append("if (frm." + extend.getEName() + ".value != \"\")\r\n{\r\n");
                                if (extend.getDataType() == 1) {
                                    scripts.append("var regstr = /[^0-9]/gi;\r\n");
                                    scripts.append("if (regstr.exec(str) != null)\r\n{\r\n");
                                    scripts.append("alert(" + cname + "应为整型数据！)\r\n");
                                } else {
                                    scripts.append("var regstr = /[^0-9.]/gi;\r\n");
                                    scripts.append("if (regstr.exec(frm." + extend.getEName() + ".value) != null)\r\n{\r\n");
                                    scripts.append("alert(" + cname + "应为小数型数据！)\r\n");
                                }
                                scripts.append("frm." + extend.getEName() + ".focus();\r\n");
                                scripts.append("ret = false;\r\n");
                                scripts.append("}}\r\n");
                                scripts.append("return ret;\r\n");
                            } else {
                                scripts.append("return true;\r\n");
                            }
                        } else {
                            content.append("<td width=\"25%\"></td>");
                        }
                        if ((i + 1) % 4 == 0) content.append("</tr>\r\n");
                    }

                    count = 1;
                    if (areaList.size() % 2 == 0) count = 0;
                    for (int i = 0; i < areaList.size() + count; i++) {
                        if (i % 2 == 0) content.append("<tr>\r\n");
                        if (i < areaList.size()) {
                            ExtendAttr extend = (ExtendAttr) areaList.get(i);
                            String cname = StringUtil.gb2iso4View(extend.getCName());

                            //读出当前属性的值
                            String value = "";
                            if (articleID > 0 && hash.get(extend.getEName()) != null)
                                value = hash.get(extend.getEName()).toString();

                            content.append("<td colspan=2 width=\"50%\">" + cname + "：<br><textarea class=tine rows=6 cols=60 name=" + extend.getEName() + ">" + value + "</textarea>\r\n");
                            if (extend.getControlType() == 4)
                                content.append("<input type=button class=tine value=编辑 onclick=Editor('" + extend.getEName() + "');>\r\n");
                            content.append("</td>\r\n");
                        } else {
                            content.append("<td colspan=2 width=\"50%\"></td>");
                        }
                        if ((i + 1) % 2 == 0) content.append("</tr>\r\n");
                    }
                    content.append("</table>\r\n");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (scripts.length() == 0) scripts.append("return true;\r\n");
        content.append("<script language=javascript>\r\nfunction checkExtendAttr(frm)\r\n{\r\n" + scripts.toString() + "}\r\n</script>\r\n");
        return content.toString();
    }


    //读取审核信息
    public List getArticleAudit(int articleid){
        List auditlist = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Audit audit;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from  tbl_article_auditing_info where articleid = ? order by createdate desc");
            pstmt.setInt(1,articleid);
            rs = pstmt.executeQuery();
            while (rs.next()){
                audit = new Audit();
                audit.setID(rs.getInt("id"));
                audit.setSign(rs.getString("sign"));
                audit.setComments(rs.getString("comments"));
                audit.setCreateDate(rs.getTimestamp("createdate"));
                auditlist.add(audit);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return auditlist;
    }

    public int deleteaudit(int id){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_article_auditing_info where id = ?");
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();

            }
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
        return code;
    }

    public int updataMoreTurnPicInfo(List tpics,int articleid){
        int code=0;
        Connection conn = null;
        PreparedStatement pstmt;
        Turnpic tpic = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
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
                        System.out.println("id=" + tpic.getId());
                        System.out.println("sortid=" + tpic.getSortid());
                        System.out.println("desc=" + tpic.getNotes());


                        pstmt = conn.prepareStatement("update tbl_turnpic set notes=?,sortid=? where id = ?");
                        pstmt.setString(1, tpic.getNotes());
                        pstmt.setInt(2, tpic.getSortid());
                        pstmt.setInt(3, tpic.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                } else {
                    pstmt = conn.prepareStatement("insert into tbl_turnpic(articleid,picname,notes,mediaurl,sortid,createdate,id) values(?,?,?,?,?,?,?)");
                    pstmt.setInt(1,articleid);
                    pstmt.setString(2, tpic.getPicname());
                    pstmt.setString(3, tpic.getNotes());
                    pstmt.setString(4,tpic.getMediaurl());
                    pstmt.setInt(5, tpic.getSortid());
                    pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    pstmt.setInt(7,sequnceMgr.getSequenceNum("TurnPic"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
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

    public  int insertTurnPic(Turnpic tpic,int articleid){
        int code=0;
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into tbl_turnpic(articleid,picname,notes,mediaurl,sortid,createdate,id) values(?,?,?,?,?,?,?)");
            pstmt.setInt(1,articleid);
            pstmt.setString(2, tpic.getPicname());
            pstmt.setString(3, tpic.getNotes());
            pstmt.setString(4,tpic.getMediaurl());
            pstmt.setInt(5, tpic.getSortid());
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(7,sequnceMgr.getSequenceNum("TurnPic"));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            code = 1;
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e){
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return  code;
    }

    public void batchDel(String articleIDs,String username) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        SiteInfo siteinfo = null;
        List<Article> articles = new ArrayList();
        List<Integer> colids = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (articleIDs.length() > 0) {
                pstmt = conn.prepareStatement("select id,columnid,siteid,maintitle,EMPTYCONTENTFLAG,CREATEDATE,FILENAME from tbl_article where id in (" + articleIDs + ")");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Article article = new Article();
                    article.setSiteID(rs.getInt("siteid"));
                    article.setColumnID(rs.getInt("columnid"));
                    article.setNullContent(rs.getInt("EMPTYCONTENTFLAG"));
                    article.setCreateDate(rs.getTimestamp("CREATEDATE"));
                    article.setFileName(rs.getString("FILENAME"));
                    article.setMainTitle(rs.getString("maintitle"));
                    articles.add(article);
                }
                rs.close();
                pstmt.close();
                siteinfo = siteManager.getSiteInfo(articles.get(0).getSiteID());
            }

            for(int ii=0; ii<articles.size(); ii++) {
                Article article = articles.get(ii);

                //删除该文章的分类信息 by feixiang 2008-01-17
                pstmt = conn.prepareStatement("delete from tbl_type_article where articleid = ?");
                pstmt.setInt(1,article.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //删除被其它栏目引用的该篇文章
                pstmt = conn.prepareStatement("DELETE FROM tbl_refers_article WHERE ArticleID = ?");
                pstmt.setInt(1, article.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //从发布队列中删除该该篇文章的发布记录
                pstmt = conn.prepareStatement("delete from tbl_new_publish_queue where targetid=?");
                pstmt.setInt(1,article.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //被删除的文章ID进入全文索引删除表
                if (siteinfo != null) {
                    pstmt = conn.prepareStatement("insert into tbl_deleted_article (siteid,columnid,articleid,sitename,acttype) values(?,?,?,?,?)");
                    pstmt.setInt(1,article.getSiteID());
                    pstmt.setInt(2,article.getColumnID());
                    pstmt.setInt(3,article.getID());
                    pstmt.setString(4,siteinfo.getDomainName());
                    pstmt.setInt(5,0);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //判断这篇文章的栏目ID与其他文章的栏目ID是否相同，如果不同则增加这个栏目ID到栏目ID队列
                int columnid = article.getColumnID();
                boolean columnid_exist_flag = false;
                for(int jj=0;jj<colids.size();jj++) {
                    int colid = colids.get(jj).intValue();
                    if (columnid == colid) {
                        columnid_exist_flag = true;
                        break;
                    }
                }
                if (columnid_exist_flag == false) colids.add(columnid);
            }

            //删除这些将要被删除的文章
            pstmt = conn.prepareStatement("delete from tbl_article WHERE ID IN (" + articleIDs + ")");
            pstmt.executeUpdate();
            pstmt.close();

            //删除远程WEB服务器上的文件
            for(int ii=0; ii<articles.size(); ii++) {
                Article article = articles.get(ii);
                if (article != null) {
                    String createdate_path = article.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "") + "/";
                    Column column = columnMgr.getColumn(article.getColumnID());
                    IFtpSetManager siteMgr = FtpSetting.getInstance();
                    List list = siteMgr.getFtpInfos(article.getSiteID());
                    int len = list.size();
                    for (int i = 0; i < len; i++) {
                        FtpInfo ftpInfo = (FtpInfo) list.get(i);
                        String siteIP = ftpInfo.getIp();
                        String remoteDocRoot = ftpInfo.getDocpath();
                        String ftpUser = ftpInfo.getFtpuser();
                        String ftpPasswd = ftpInfo.getFtppwd();

                        if (article.getNullContent() == 0) {     //删除发布的文章页面
                            String fileName = column.getDirName() + createdate_path + article.getID() + "." + column.getExtname();
                            System.out.println("fileName = " +fileName + "==" + ftpInfo.getPublishway());
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
                            String fileName = column.getDirName() + createdate_path +  "download/" + article.getFileName();
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
            }

            //创建发布队列信息
            List queueList = new ArrayList();
            Publish publish = null;
            Timestamp now = new Timestamp(System.currentTimeMillis());
            String ids = "";

            for(int ii=0;ii<colids.size();ii++) {
                //修改与该篇文章相关联的栏目模板的发布标志位
                int sitetype = siteinfo.getSitetype();
                int columnID = colids.get(ii);
                int site_root_columnid = columnMgr.getSiteRootColumn(siteinfo.getSiteid()).getID();
                if (sitetype==1) {
                    int tempnum = siteinfo.getTempnum();
                    String SQL_Select_Model = "select id,columnid,chname from tbl_template where isarticle <> 1 and " +
                            "siteid=? and tempnum=? and (relatedcolumnid like '%," + columnID +
                            ",%' or relatedcolumnid like '%(" + columnID + ",%' or relatedcolumnid like '%," + columnID + ")%' " +
                            "or relatedcolumnid like '%(" + columnID + ")%')";
                    pstmt = conn.prepareStatement(SQL_Select_Model);
                    pstmt.setInt(1, siteinfo.getSamsiteid());
                    pstmt.setInt(2,tempnum);
                }else{
                    String SQL_Select_Model = "select id,columnid,chname from tbl_template where isarticle <> 1 and " +
                            "siteid=? and (relatedcolumnid like '%," + columnID +
                            ",%' or relatedcolumnid like '%(" + columnID + ",%' or relatedcolumnid like '%," + columnID + ")%' " +
                            "or relatedcolumnid like '%(" + columnID + ")%')";
                    pstmt = conn.prepareStatement(SQL_Select_Model);
                    pstmt.setInt(1, siteinfo.getSiteid());
                }
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (cpool.getPublishWay() == 1) {
                        publish = new Publish();
                        publish.setSiteID(siteinfo.getSiteid());
                        publish.setTargetID(rs.getInt("id"));
                        if (sitetype ==1)
                            publish.setColumnID(site_root_columnid);
                        else
                            publish.setColumnID(rs.getInt("columnid"));
                        publish.setTitle(rs.getString("chname"));
                        publish.setObjectType(2);
                        queueList.add(publish);
                    }
                    ids += rs.getString(1) + ",";
                }
                rs.close();
                pstmt.close();

                if (ids.length() > 0) {
                    ids = ids.substring(0, ids.length() - 1);
                    String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                    pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //修改引用文章导致需要重新发布的模板
                List columnIdsList = getReferArticleColumnIds(columnID);
                String Update_Other_Site_TemplatePubflag = null;
                ids = "";
                for (int i = 0; i < columnIdsList.size(); i++) {
                    Column refersArticle = (Column) columnIdsList.get(i);
                    int ReferColumnID = refersArticle.getID();
                    Update_Other_Site_TemplatePubflag = "select id,columnid,chname from tbl_template where isarticle <> 1 and siteid=? and " +
                            "(relatedcolumnid like '%," + ReferColumnID + ",%' or relatedcolumnid like '%(" + ReferColumnID + ",%' " +
                            "or relatedcolumnid like '%," + ReferColumnID + ")%' or relatedcolumnid like '%(" + ReferColumnID + ")%')";

                    pstmt = conn.prepareStatement(Update_Other_Site_TemplatePubflag);
                    pstmt.setInt(1, refersArticle.getSiteID());
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        if (cpool.getPublishWay() == 1) {
                            publish = new Publish();
                            publish.setSiteID(refersArticle.getSiteID());
                            publish.setTargetID(rs.getInt("id"));
                            publish.setColumnID(rs.getInt("columnid"));
                            publish.setTitle(rs.getString("chname"));
                            publish.setPriority(2);
                            publish.setObjectType(2);
                            queueList.add(publish);
                        }
                        ids += rs.getString(1) + ",";
                    }
                    rs.close();
                    pstmt.close();
                }

                if (ids.length() > 0) {
                    ids = ids.substring(0, ids.length() - 1);
                    String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                    pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            //将需要发布的模板加入到发布队列
            if (cpool.getPublishWay() == 1) {
                PreparedStatement pstmt1 = null;
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                else
                    pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                for (int i = 0; i < queueList.size(); i++) {
                    publish = (Publish) queueList.get(i);
                    boolean exist_onejob = false;
                    String SQL_GetOneJob = "select * from tbl_new_publish_queue where siteid=? and columnid=? and targetid=? and type=?";
                    pstmt=conn.prepareStatement(SQL_GetOneJob);
                    pstmt.setInt(1,publish.getSiteID());
                    pstmt.setInt(2,publish.getColumnID());
                    pstmt.setInt(3,publish.getTargetID());
                    pstmt.setInt(4,publish.getObjectType());
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        exist_onejob = true;
                    }
                    rs.close();
                    pstmt.close();

                    //SiteID,columnid,TargetID,Type,Status,CreateDate,PublishDate,UniqueID,title,PRIORITY,ID

                    if (exist_onejob == false) {
                        pstmt1.setInt(1,publish.getSiteID());
                        pstmt1.setInt(2,publish.getColumnID());
                        pstmt1.setInt(3, publish.getTargetID());
                        pstmt1.setInt(4, publish.getObjectType());
                        pstmt1.setInt(5, 1);
                        pstmt1.setTimestamp(6, now);
                        pstmt1.setTimestamp(7, now);
                        pstmt1.setString(8, "");
                        pstmt1.setString(9, StringUtil.iso2gbindb(publish.getTitle()));
                        pstmt1.setInt(10,publish.getPriority());
                        if (cpool.getType().equals("oracle")) {
                            pstmt1.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                            pstmt1.addBatch();
                            //pstmt1.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt1.addBatch();
                            //pstmt1.executeUpdate();
                        } else {
                            pstmt1.addBatch();
                            //pstmt1.executeUpdate();
                        }
                    }
                }
                pstmt1.executeBatch();
                pstmt1.close();
            }

            //创建LOG
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR) - 1900;
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_LOG_FOR_MYSQL);
            for(int ii=0; ii<articles.size(); ii++) {
                Article article = articles.get(ii);
                pstmt.setInt(1, article.getSiteID());
                pstmt.setInt(2, article.getColumnID());
                pstmt.setInt(3, article.getID());
                pstmt.setString(4, username);
                pstmt.setInt(5, 3);
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, article.getMainTitle());
                pstmt.setDate(8, new Date(year, month, date));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setLong(9, sequnceMgr.getSequenceNum("Log"));
                    pstmt.addBatch();
                    //pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.addBatch();
                    //pstmt.executeUpdate();
                } else {
                    pstmt.addBatch();
                    //pstmt.executeUpdate();
                }
            }
            pstmt.executeBatch();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch(SQLException exp) { }
        } catch (ColumnException exp) {
            exp.printStackTrace();
        } catch (SiteInfoException exp) {
            exp.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }
}
