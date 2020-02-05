package com.chinabuyregister;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.register.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.news.*;
import com.bizwink.cms.viewFileManager.ViewFile;
import com.bizwink.cms.markManager.mark;
import com.bizwink.cms.modelManager.Model;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.upload.RandomStrg;
import com.bizwink.upload.FileDeal;

import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;

import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-10-16
 * Time: 14:19:49
 * To change this template use File | Settings | File Templates.
 */
public class ChinaBuyRegiterPeer implements IChinaBuyRegiterManager {
    PoolServer cpool;

    public ChinaBuyRegiterPeer(PoolServer cpool) {
        this.cpool = cpool;
    }
    public static IChinaBuyRegiterManager getInstance() {
        return CmsServer.getInstance().getFactory().getChinaBuyRegiterManager();
    }
    public List getChinabuyIndex(int columnid,int num)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select *from tbl_article where columnid="+columnid+" and rownum<="+num;
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                Article article=load(res);
                list.add(article);
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return list;
    }
    public Column getLikeCnameColumn(String cname,int siteid)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        Column column=null;
        try{
            con=cpool.getConnection();
            String sql="select *from tbl_column where cname like '"+cname+"' and siteid="+siteid;
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                column=load(res,1);

            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return column;
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
            article.setProductWeight(rs.getInt("Weight"));
            article.setStockNum(rs.getInt("StockNum"));
            article.setViceDocLevel(rs.getInt("ViceDocLevel"));
            article.setReferArticleID(rs.getInt("referID"));
            article.setModelID(rs.getInt("modelID"));
            article.setArticlepic(rs.getString("articlepic"));
            article.setUrltype(rs.getInt("urltype"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setSiteID(rs.getInt("siteid"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return article;
    }
    Column load(ResultSet rs,int columnid) throws Exception {
        Column column = new Column();

        column.setID(rs.getInt("ID"));
        column.setSiteID(rs.getInt("siteid"));
        column.setDirName(rs.getString("dirname"));
        column.setOrderID(rs.getInt("orderid"));
        column.setParentID(rs.getInt("parentid"));
        column.setEName(rs.getString("ename"));
        column.setExtname(rs.getString("extname"));
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

        return column;
    }
    //分页
    public List page(int ipage, int pic) {
        List list = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        int num = 10 * (ipage - 1);
        try {
            con = cpool.getConnection();
            String sql="select top 10 *from hu_pinglun where   status=1 and articleid="+pic+" and ( id not in (select top "+num+" id from hu_pinglun  ORDER   BY   id desc ))  ORDER   BY   id desc ";
            sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from tbl_article  b order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and columnid="+pic+"  ) WHERE  RN  >   " + num + " ";
            pstmt = con.prepareStatement(sql);
            System.out.println(sql);
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                Article article=load(res);
                list.add(article);
            }
            res.close();
            pstmt.close();


        } catch (Exception e) {
            System.out.println(""+e.toString());
        }
        finally {
            if (con != null) {
                try {
                    cpool.freeConnection(con);
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public int count(int pic) {
        int count = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select count(*) from tbl_article where   columnid=" + pic);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                count = res.getInt(1);
            }
            res.close();
            pstmt.close();

        } catch (Exception e) {
        }
        finally {
            if (con != null) {
                try {
                    if (con != null) {
                        cpool.freeConnection(con);
                    }
                } catch (Exception e) {
                }
            }
        }
        return count;
    }
    public Register getReg(int siteid)
    {
        Register reg=null;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select * from tbl_members where siteid="+siteid;
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                reg=new Register();
                reg.setCompany(res.getString("company"));
                reg.setMphone(res.getString("mphone"));
                reg.setAddress(res.getString("address"));

            }
            res.close();
            pstmt.close();
        }catch(Exception e){

        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){

                }
            }
        }
        return reg;
    }
    private static final String SQL_GETARTICLE =
            "SELECT  maintitle,vicetitle,source,summary,keyword,content,author,emptycontentflag,editor,id,columnid,sortid," +
                    "doclevel,pubflag,auditflag,auditor,createdate,lastupdated,status,subscriber,lockstatus,lockeditor,dirname," +
                    "filename,publishtime,RelatedArtID,saleprice,vipprice,inprice,marketprice,score,voucher,Brand,pic,bigpic,Weight,stocknum,ViceDocLevel," +
                    "referID,modelID,articlepic,urltype,defineurl,siteid FROM tbl_article WHERE id = ?";

    //从文章数据库中获取一篇文章
    public Article getArticle(int ID)  {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
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

}
