package com.bizwink.cms.register;

import com.bizwink.cms.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.extendAttr.ExtendAttrPeer;
import com.bizwink.cms.extendAttr.ExtendAttrException;
import com.bizwink.cms.extendAttr.ExtendAttr;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.publishx.Publish;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-3-13
 * Time: 17:32:54
 * To change this template use File | Settings | File Templates.
 */
public class transferArticle {
    public transferArticle() {

    }

    //copySamSite(int sampleSiteID, int siteid,String userid,String sitename,String apppath)
    static public void main(String args[])
    {
        int sampleSiteID = 4;
        int siteid = 33;
        String sitename="industry.yidaba.com";
        String userid="industry";
        String apppath="D:\\webbuilder_for_coosite\\webapps\\cms\\";
        try {
            copyArticle(sampleSiteID,siteid,userid,sitename,apppath);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private static Connection createConnection(String ip, String username, String password, String server,int flag) {
        Connection conn = null;
        String dbip = "";
        String dbusername = "";
        String dbpassword = "";

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;

            //System.out.println("dbip=" + dbip);
            //System.out.println("server=" + server);
            //System.out.println("dbusername=" + dbusername);
            //System.out.println("dbpassword=" + dbpassword);

            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":1433", dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:"+server, dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    private static final String SQL_GETCOLUMNS = "select id,siteid,ename" +
            " from tbl_column where siteid=? order by orderid";

    static public List getColumns(int samsiteid) throws ColumnException {
        Connection s_conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Column column;

        try {
            String s_dbip = "127.0.0.1";
            String s_username = "yidaba";
            String s_password = "yidaba";
            String s_server = "orcl9";
            s_conn = createConnection(s_dbip, s_username, s_password,s_server, 1);

            pstmt = s_conn.prepareStatement(SQL_GETCOLUMNS);
            pstmt.setInt(1,samsiteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = loadc(rs);
                list.add(column);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (s_conn != null) {
                try {
                    s_conn.close();
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GET_NEW_COLUMNID = "select id,siteid,ename" +
            " from tbl_column where siteid=? and ename=? order by orderid";

    static public int getNewColumnID(int siteid,String columnname) throws ColumnException {
        Connection t_conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int newcolumnid=0;

        try {
            String t_dbip = "127.0.0.1";
            String t_username = "sinopec2008";
            String t_password = "sinopec2008";
            String t_server = "orcl9";
            t_conn = createConnection(t_dbip, t_username, t_password,t_server, 1);

            pstmt = t_conn.prepareStatement(SQL_GET_NEW_COLUMNID);
            pstmt.setInt(1,siteid);
            pstmt.setString(2,columnname);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                newcolumnid = rs.getInt("id");
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (t_conn != null) {
                try {
                    t_conn.close();
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return newcolumnid;
    }

    public static boolean copyArticle(int sampleSiteID, int siteid,String userid,String sitename,String apppath) throws CmsException {
        boolean succeed = false;

        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        Article article = new Article();
        Column column = null;
        List arts = new ArrayList();
        List columns = null;
        try {
            columns = getColumns(sampleSiteID);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        for(int i=0; i<columns.size(); i++) {
            column = (Column)columns.get(i);
            System.out.println(column.getID());
            int n_colid = 0;
            try {
                arts = getArticle(column.getID());
                n_colid = getNewColumnID(siteid,column.getEName());
            } catch (Exception exp) {
                exp.printStackTrace();
            }

            for(int j=0; j<arts.size(); j++) {
                article = (Article)arts.get(j);
                article.setColumnID(n_colid);
                article.setSiteID(siteid);
                try {
                    create(null,null,article,null,null);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }

                System.out.println(article.getMainTitle());
            }
        }
        System.out.println("网站拷贝完毕!!!!!!");

        return succeed;
    }

    private static final String SQL_GETARTICLE =
            "SELECT  maintitle,vicetitle,source,summary,keyword,content,author,emptycontentflag,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor,dirname," +
                    "filename,publishtime,RelatedArtID,saleprice,inprice,marketprice,Brand,pic,bigpic,Weight,stocknum,ViceDocLevel" +
                    " FROM tbl_article WHERE columnid = ?";

    //从文章数据库中获取一篇文章
    public static List getArticle(int colID) throws ArticleException {
        Connection s_conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Article article = null;
        List articles = new ArrayList();

        try {
            String s_dbip = "127.0.0.1";
            String s_username = "yidaba";
            String s_password = "yidaba";
            String s_server = "orcl9";
            s_conn = createConnection(s_dbip, s_username, s_password,s_server, 1);

            pstmt = s_conn.prepareStatement(SQL_GETARTICLE);
            pstmt.setInt(1, colID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = load(rs,"oracle");
                articles.add(article);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (s_conn != null) {
                try {
                    s_conn.close();
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return articles;
    }

    private static final String SQL_CREATE_ARTICLE =
            "INSERT INTO TBL_Article (ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,DirName,Publishtime,SalePrice,InPrice,MarketPrice,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,ID,articlepic,urltype," +
                    "defineurl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_ARTICLE_EXTEND_ATTR =
            "INSERT INTO TBL_Article_ExtendAttr(ArticleID,Ename,Type,TextValue," +
                    "StringValue,NumericValue,FloatValue,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * 创建文章
     *
     * @param extendList  扩展属性List
     * @param attechments 文章附件List
     * @param article     文章对象Article
     * @throws com.bizwink.cms.extendAttr.ExtendAttrException
     */
    static public void create(List extendList, List attechments, Article article, List ptypelist, List turnlist) throws ExtendAttrException {
        Connection t_conn = null;
        PreparedStatement pstmt;

        int articleID;
        int extendID;
        int columnID = article.getColumnID();


        IColumnManager columnMgr = ColumnPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            Column column = columnMgr.getColumn(columnID);
            article.setDirName(column.getDirName());
            if (article.getSortID() == 0)
                article.setSortID(articleMgr.getNextOrder(columnID));
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

                String t_dbip = "127.0.0.1";
                String t_username = "sinopec2008";
                String t_password = "sinopec2008";
                String t_server = "orcl9";
                t_conn = createConnection(t_dbip, t_username, t_password,t_server, 1);
                t_conn.setAutoCommit(false);

                //增加文章
                pstmt = t_conn.prepareStatement(SQL_CREATE_ARTICLE);
                pstmt.setInt(1, columnID);
                pstmt.setInt(2, article.getSortID());
                pstmt.setString(3, maintitle);
                pstmt.setString(4, vicetitle);
                pstmt.setString(5, summary);
                pstmt.setString(6, keyword);
                pstmt.setString(7, source);
                if (content != null)
                    DBUtil.setBigString("oracle", pstmt, 8, content);
                else
                    pstmt.setNull(8, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(9, article.getNullContent());
                pstmt.setString(10, author);
                pstmt.setTimestamp(11, article.getCreateDate());
                pstmt.setTimestamp(12, article.getLastUpdated());
                pstmt.setString(13, filename);
                pstmt.setString(14,article.getDownfilename());
                pstmt.setInt(15, article.getDocLevel());
                pstmt.setInt(16, article.getPubFlag());
                pstmt.setInt(17, article.getStatus());
                pstmt.setInt(18, article.getAuditFlag());
                pstmt.setInt(19, article.getSubscriber());
                pstmt.setString(20, article.getEditor());
                pstmt.setString(21, article.getDirName());
                pstmt.setTimestamp(22, article.getPublishTime());
                pstmt.setFloat(23, article.getSalePrice());
                pstmt.setFloat(24, article.getInPrice());
                pstmt.setFloat(25, article.getMarketPrice());
                pstmt.setFloat(26, article.getProductWeight());
                pstmt.setInt(27, article.getStockNum());
                pstmt.setString(28, brand);
                pstmt.setString(29, article.getProductPic());
                pstmt.setString(30, article.getProductBigPic());
                pstmt.setString(31, article.getRelatedArtID());
                pstmt.setInt(32, 0);
                pstmt.setInt(33, 0);
                pstmt.setInt(34, 0);
                pstmt.setInt(35, article.getViceDocLevel());
                pstmt.setInt(36, 0);
                pstmt.setInt(37, 0);
                pstmt.setInt(38, article.getReferArticleID());
                pstmt.setInt(39, article.getModelID());
                articleID = sequnceMgr.getSequenceNum("Article");
                pstmt.setInt(40, articleID);
                pstmt.setString(41, article.getArticlepic());
                pstmt.setInt(42, article.getUrltype());
                pstmt.setString(43, article.getOtherurl());
                pstmt.executeUpdate();
                pstmt.close();

                //增加扩展属性
                if (extendList != null) {
                    pstmt = t_conn.prepareStatement(SQL_CREATE_ARTICLE_EXTEND_ATTR);
                    for (int i = 0; i < extendList.size(); i++) {
                        ExtendAttr extendAttr = (ExtendAttr) extendList.get(i);

                        String textvalue = extendAttr.getTextValue();
                        if (textvalue != null) textvalue = StringUtil.gb2isoindb(textvalue);
                        String stringvalue = extendAttr.getStringValue();
                        if (stringvalue != null) stringvalue = StringUtil.gb2isoindb(stringvalue);

                        pstmt.setInt(1, articleID);
                        pstmt.setString(2, extendAttr.getEName());
                        pstmt.setInt(3, extendAttr.getDataType());
                        if (textvalue != null)
                            DBUtil.setBigString("oracle", pstmt, 4, textvalue);
                        else
                            pstmt.setNull(4, java.sql.Types.LONGVARCHAR);
                        pstmt.setString(5, stringvalue);
                        pstmt.setInt(6, extendAttr.getNumericValue());
                        pstmt.setFloat(7, extendAttr.getFloatValue());
                        extendID = sequnceMgr.getSequenceNum("ArticleExtendAttr");
                        pstmt.setInt(8, extendID);
                        pstmt.executeUpdate();
                    }
                    pstmt.close();
                }

                t_conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                t_conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    t_conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }
    }


    static Article load(ResultSet rs,String dbtype) throws Exception {
        Article article = new Article();
        try {
            if (dbtype.equals("mssql")) {
                article.setMainTitle(StringUtil.iso2gbindb(rs.getString("maintitle")));
                article.setViceTitle(StringUtil.iso2gbindb(rs.getString("vicetitle")));
                article.setSource(StringUtil.iso2gbindb(rs.getString("source")));
                article.setSummary(StringUtil.iso2gbindb(rs.getString("summary")));
                article.setKeyword(StringUtil.iso2gbindb(rs.getString("keyword")));
                article.setContent(StringUtil.iso2gbindb(DBUtil.getBigString(dbtype, rs, "content")));
            } else {
                article.setMainTitle(rs.getString("maintitle"));
                article.setViceTitle(rs.getString("vicetitle"));
                article.setSource(rs.getString("source"));
                article.setSummary(rs.getString("summary"));
                article.setKeyword(rs.getString("keyword"));
                article.setContent(DBUtil.getBigString("mssql", rs, "content"));
            }
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
            article.setInPrice(rs.getFloat("InPrice"));
            article.setMarketPrice(rs.getFloat("MarketPrice"));
            if (dbtype.equals("mssql"))
                article.setBrand(StringUtil.iso2gbindb(rs.getString("Brand")));
            else
                article.setBrand(rs.getString("Brand"));
            article.setProductPic(rs.getString("Pic"));
            article.setProductBigPic(rs.getString("BigPic"));
            article.setProductWeight(rs.getInt("Weight"));
            article.setStockNum(rs.getInt("StockNum"));
            article.setViceDocLevel(rs.getInt("ViceDocLevel"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return article;
    }

    static Column loadc(ResultSet rs) throws SQLException {
        Column column = new Column();

        column.setID(rs.getInt("ID"));
        column.setSiteID(rs.getInt("siteid"));
        //column.setDirName(rs.getString("dirname"));
        //column.setOrderID(rs.getInt("orderid"));
        //column.setParentID(rs.getInt("parentid"));
        column.setEName(rs.getString("ename"));
        /*column.setExtname(rs.getString("extname"));
        column.setCreateDate(rs.getTimestamp("createdate"));
        column.setLastUpdated(rs.getTimestamp("lastupdated"));
        column.setCName(rs.getString("cname"));
        column.setEditor(rs.getString("editor"));
        column.setDefineAttr(rs.getInt("isDefineAttr"));
        column.setXMLTemplate(rs.getString("XMLTemplate"));
        column.setIsAudited(rs.getInt("IsAudited"));
        column.setDesc(rs.getString("ColumnDesc"));
        column.setIsProduct(rs.getInt("IsProduct"));
        column.setIsPublishMoreArticleModel(rs.getInt("IsPublishMore"));
        column.setLanguageType(rs.getInt("LanguageType"));
        column.setContentShowType(rs.getInt("contentshowtype"));
        column.setRss(rs.getInt("isrss"));
        column.setGetRssArticleTime(rs.getInt("getrssarticletime"));
        column.setArchivingrules(rs.getInt("archivingrules"));
        column.setUseArticleType(rs.getInt("useArticleType"));
        column.setIsType(rs.getInt("istype"));
        */
        return column;
    }

}
