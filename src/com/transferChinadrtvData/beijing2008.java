package com.transferChinadrtvData;

import com.bizwink.cms.extendAttr.ExtendAttr;
import com.bizwink.cms.extendAttr.ExtendAttrException;
import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.ArticleException;
import com.bizwink.cms.news.ArticlePeer;
import com.bizwink.cms.news.IArticleManager;
import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-4-11
 * Time: 21:38:50
 * To change this template use File | Settings | File Templates.
 */
public class beijing2008 {
    //copySamSite(int sampleSiteID, int siteid,String userid,String sitename,String apppath)
    static public void main(String args[])
    {
        int siteid = 39;
        String sitename="www.beijing2008.cn";
        String userid="admin2008";
        List articles = new ArrayList();
        Article article = null;
        try {
            articles = getArticle("E:\\leads\\beijing2008\\cn\\cn\\boda-activity\\activities-2010\\cooperation\\");
            for (int i=0; i<articles.size(); i++) {
                article = (Article)articles.get(i);
                article.setColumnID(46);
                article.setSiteID(39);
                article.setStatus(1);

                article.setDirName("/Hot_Event/activities_2010/cooperation/");
                create(null,null,article,null,null);
                System.out.println(article.getMainTitle());
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    //从文章数据库中获取一篇文章
    public static List getArticle(String path) throws ArticleException {
        Article article = null;
        List articles = new ArrayList();

        try {
            String encoding="utf-8";
            FileInputStream fis = null;
            int orderid = 0;

            //从网页导入大型专题
            java.io.File file = new java.io.File(path);
            String[] files = file.list();
            for(int i=0; i<files.length; i++) {
                if (files[i].indexOf("index") == -1 && files[i].indexOf("activities-2010") == -1) {
                    article = new Article();
                    String filename = path + files[i];
                    StringBuffer tempBuf = new StringBuffer();
                    fis = new FileInputStream(filename);
                    InputStreamReader isr = new InputStreamReader(fis, encoding);
                    Reader in = new BufferedReader(isr);
                    int ch;
                    while ((ch = in.read()) > -1) {
                        tempBuf.append((char) ch);
                    }
                    in.close();
                    isr.close();
                    fis.close();
                    String str = tempBuf.toString();
                    int posi = str.indexOf("<div class=\"newCont clear\">");
                    str = str.substring(posi + "<div class=\"newCont clear\">".length());
                    posi = str.indexOf("</div>");
                    String title_and_publishtime = str.substring(0,posi);
                    str = str.substring(posi+ "</div>".length());
                    posi = str.indexOf("<div class=\"lineWS\"></div>");
                    str = str.substring(0,posi);
                    posi = str.lastIndexOf("</div>");
                    str = str.substring(0,posi);
                    str = chulitu(str);

                    posi = title_and_publishtime.indexOf("</h1>");
                    String title = title_and_publishtime.substring(0,posi);
                    title = StringUtil.replace(title,"<h1>","");
                    title = title.trim();
                    String publishtime = title_and_publishtime.substring(posi + "</h1>".length());
                    publishtime = StringUtil.replace(publishtime,"<div class=\"time\">日期：","");
                    publishtime = publishtime.trim();
                    article.setMainTitle(title);
                    article.setContent(str);
                    article.setEditor("admin2008");
                    article.setCreateDate(Timestamp.valueOf(publishtime));
                    article.setLastUpdated(Timestamp.valueOf(publishtime));
                    article.setPublishTime(Timestamp.valueOf(publishtime));
                    orderid = orderid + 1;
                    article.setOrders(orderid);
                    articles.add(article);
                }
            }

            //从页面导入活动信息


            //从网页导入图片内容
            /*java.io.File file = new java.io.File("E:\\leads\\beijing2008\\cn\\cn\\boda-pic\\beijing\\");
            String[] files = file.list();
            for(int i=0; i<files.length; i++) {
                if (files[i].indexOf("index") == -1) {
                    article = new Article();
                    String filename = "E:\\leads\\beijing2008\\cn\\cn\\boda-pic\\beijing\\" + files[i];
                    StringBuffer tempBuf = new StringBuffer();
                    fis = new FileInputStream(filename);
                    InputStreamReader isr = new InputStreamReader(fis, encoding);
                    Reader in = new BufferedReader(isr);
                    int ch;
                    while ((ch = in.read()) > -1) {
                        tempBuf.append((char) ch);
                    }
                    in.close();
                    isr.close();
                    fis.close();
                    String str = tempBuf.toString();
                    int posi = str.indexOf("<div class=\"newCont clear\">");
                    str = str.substring(posi + "<div class=\"newCont clear\">".length());
                    posi = str.indexOf("</div>");
                    String title_and_publishtime = str.substring(0,posi);
                    str = str.substring(posi+ "</div>".length());
                    posi = str.indexOf("<div class=\"lineWS\"></div>");
                    str = str.substring(0,posi);
                    posi = str.lastIndexOf("</div>");
                    str = str.substring(0,posi);
                    str = chulitu(str);

                    posi = title_and_publishtime.indexOf("</h1>");
                    String title = title_and_publishtime.substring(0,posi);
                    title = StringUtil.replace(title,"<h1>","");
                    title = title.trim();
                    String publishtime = title_and_publishtime.substring(posi + "</h1>".length());
                    publishtime = StringUtil.replace(publishtime,"<div class=\"time\">日期：","");
                    publishtime = publishtime.trim();
                    article.setMainTitle(title);
                    article.setContent(str);
                    article.setEditor("admin2008");
                    article.setCreateDate(Timestamp.valueOf(publishtime));
                    article.setLastUpdated(Timestamp.valueOf(publishtime));
                    article.setPublishTime(Timestamp.valueOf(publishtime));
                    orderid = orderid + 1;
                    article.setOrders(orderid);
                    articles.add(article);
                }
            }

            //从网页导入新闻
            /*for(int i=20; i >= 0; i--) {
                StringBuffer tempBuf = new StringBuffer();
                fis = new FileInputStream(path + "index_" + i + ".shtml");
                InputStreamReader isr = new InputStreamReader(fis, encoding);
                Reader in = new BufferedReader(isr);
                int ch;
                while ((ch = in.read()) > -1) {
                    tempBuf.append((char) ch);
                }
                in.close();
                isr.close();
                fis.close();
                String str = tempBuf.toString();
                Pattern p = Pattern.compile("<li>",Pattern.CASE_INSENSITIVE);
                if (i != 0) {
                    int posi = str.indexOf("<div class=\"f12list\">");
                    str = str.substring(posi + "<div class=\"f12list\">".length());
                    posi = str.indexOf("<ul>");
                    str = str.substring(posi + "<ul>".length());
                    posi = str.indexOf("</ul>");
                    str = str.substring(0,posi);
                    str = StringUtil.replace(str,"&#8226;&nbsp;","");
                    String[] tb = p.split(str);
                    for (int j=0; j<tb.length; j++) {
                        tb[j] = StringUtil.replace(tb[j],"</li>","");
                        posi = tb[j].indexOf(">");
                        String filename = null;
                        if (posi > -1) {
                            filename = tb[j].substring(0,posi);
                            filename = StringUtil.replace(filename,"<a href=http://www.beijing2008.cn","");
                            filename = StringUtil.replace(filename,"target=\"_blank\"","").trim();
                            String tbuf = tb[j].substring(posi + 1);
                            posi = tbuf.indexOf("</a>");
                            if (posi>-1) {
                                article = new Article();
                                orderid = orderid + 1;
                                String title = tbuf.substring(0,posi);
                                article.setFileName(filename);
                                article.setMainTitle(title);
                                article.setOrders(orderid);
                                String publishtime = tbuf.substring(posi + "</a>".length());
                                publishtime = StringUtil.replace(publishtime,"<span>","");
                                publishtime = StringUtil.replace(publishtime,"</span>","");
                                publishtime = StringUtil.replace(publishtime,"(","");
                                publishtime = StringUtil.replace(publishtime,")","");

                                posi = filename.lastIndexOf("/");
                                filename = filename.substring(posi+1);
                                fis = new FileInputStream(path + filename);
                                isr = new InputStreamReader(fis, encoding);
                                in = new BufferedReader(isr);
                                tempBuf = new StringBuffer();
                                while ((ch = in.read()) > -1) {
                                    tempBuf.append((char) ch);
                                }
                                in.close();
                                isr.close();
                                fis.close();

                                str = tempBuf.toString();
                                posi = str.indexOf("<div class=\"cont\" id=\"newsContent\">");
                                str = str.substring(posi + "<div class=\"cont\" id=\"newsContent\">".length());
                                posi = str.indexOf("</div>");
                                str = str.substring(0,posi);

                                str = chulitu(str);

                                //System.out.println(str);
                                article.setContent(str);
                                article.setEditor("admin2008");
                                java.io.File f = new java.io.File(path + filename);
                                long lastupdate = f.lastModified();
                                article.setLastUpdated(new Timestamp(lastupdate));
                                article.setCreateDate(new Timestamp(lastupdate));
                                article.setPublishTime(new Timestamp(lastupdate));
                                articles.add(article);

                                //System.out.println(title + "=====" + path + filename + "====" + publishtime + "\r\n");
                            }
                        }
                    }
                    //System.out.println("=====================" + i + "================\r\n");
                }
            }*/

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
        }

        return articles;
    }

    private static String chulitu(String str) {
        String buf = str;
        Pattern p = Pattern.compile("src=\"[^<>\"]*\"",Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(buf);
        String matchStr;
        String pic_path = "/webbuilder/sites/www_beijing2008_cn/Hot_Event/activities_2010/cooperation/images/";
        String target_pic_file = "E:\\leads\\beijing2008\\cn\\cms\\sites\\www_beijing2008_cn\\Hot_Event\\activities_2010\\cooperation\\images\\";
        while (matcher.find()) {
            matchStr = buf.substring(matcher.start(), matcher.end() - 1);
            int posi = matchStr.lastIndexOf("/");
            String picname = matchStr.substring(posi+1);
            String pic_url = pic_path + picname;
            posi = matchStr.indexOf("\"");
            String old_url = matchStr.substring(posi + 1);
            str = StringUtil.replace(str,old_url,pic_url);

            String filename = StringUtil.replace(matchStr,"src=\"http://pic.data.itc.cn","");
            filename = StringUtil.replace(filename,"/",java.io.File.separator);
            filename = "E:\\leads\\beijing2008\\photocdn\\photocdn" + filename;
            posi = filename.lastIndexOf(java.io.File.separator);
            String sFile = filename.substring(posi+1);
            String tpic_file = target_pic_file + sFile;
            try {
                FileInputStream is = new FileInputStream(filename);
                BufferedInputStream bis = new BufferedInputStream(is);
                FileOutputStream fos = new FileOutputStream(tpic_file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int c;
                while ((c = bis.read()) != -1) {
                    bos.write((byte) c);
                }
                bos.close();
                fos.close();
            } catch (IOException exp) {
                exp.printStackTrace();
            }
            //System.out.println(filename + "====" + sFile);
        }

        return str;
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

    private static final String SQL_CREATE_ARTICLE =
            "INSERT INTO TBL_Article (ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,DirName,Publishtime,SalePrice,InPrice,MarketPrice,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,ID,articlepic,urltype," +
                    "defineurl,siteid) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
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
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int articleID;
        int extendID;
        int columnID = article.getColumnID();


        IArticleManager articleMgr = ArticlePeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            try {
                String maintitle = article.getMainTitle();
                //if (maintitle != null) maintitle = StringUtil.gb2isoindb(maintitle);
                String vicetitle = article.getViceTitle();
                //if (vicetitle != null) vicetitle = StringUtil.gb2isoindb(vicetitle);
                String summary = article.getSummary();
                //if (summary != null) summary = StringUtil.gb2isoindb(summary);
                String keyword = article.getKeyword();
                //if (keyword != null) keyword = StringUtil.gb2isoindb(keyword);
                String source = article.getSource();
                //if (source != null) source = StringUtil.gb2isoindb(source);
                String author = article.getAuthor();
                //if (author != null) author = StringUtil.gb2isoindb(author);
                String content = article.getContent();
                //if (content != null) content = StringUtil.gb2isoindb(content);
                String brand = article.getBrand();
                //if (brand != null) brand = StringUtil.gb2isoindb(brand);
                String filename = article.getFileName();
                //if (filename != null) filename = StringUtil.gb2isoindb(filename);

                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                String t_server = "orcl11g";
                t_conn = createConnection(t_dbip, t_username, t_password,t_server, 1);
                t_conn.setAutoCommit(false);

                String dirname = null;
                pstmt = t_conn.prepareStatement("select * from tbl_column where id=" + article.getColumnID());
                rs = pstmt.executeQuery();
                if (rs.next())  dirname =rs.getString("dirname");
                rs.close();
                pstmt.close();

                //增加文章
                pstmt = t_conn.prepareStatement(SQL_CREATE_ARTICLE);
                pstmt.setInt(1, article.getColumnID());
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
                pstmt.setString(21, dirname);
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
                pstmt.setInt(44,article.getSiteID());
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

                        pstmt.setInt(1, article.getID());
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
                article.setAuthor(StringUtil.iso2gbindb(rs.getString("author")));
            } else {
                article.setMainTitle(rs.getString("maintitle"));
                article.setViceTitle(rs.getString("vicetitle"));
                article.setSource(rs.getString("source"));
                article.setSummary(rs.getString("summary"));
                article.setKeyword(rs.getString("keyword"));
                article.setContent(DBUtil.getBigString("mssql", rs, "content"));
                article.setAuthor(rs.getString("author"));
            }
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
}
