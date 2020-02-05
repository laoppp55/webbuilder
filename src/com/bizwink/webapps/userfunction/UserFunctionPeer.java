package com.bizwink.webapps.userfunction;

import com.bizwink.cms.extendAttr.ExtendAttrException;
import com.bizwink.cms.extendAttr.ExtendAttrPeer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.news.*;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.toolkit.companyinfo.Companyinfo;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-9
 * Time: 8:35:09
 * To change this template use File | Settings | File Templates.
 */
public class UserFunctionPeer implements IUserFunctionManager {
    PoolServer cpool;

    public UserFunctionPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IUserFunctionManager getInstance() {
        return CmsServer.getInstance().getFactory().getUserFunctionManager();
    }

    public int searchProductNumForStendersByComponent(int siteid,String keyword,int type) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int count = 0;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(pageid) from tbl_relatedartids where pagetype=0 and cname=?");
            pstmt.setString(1,keyword);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return count;
    }

    public List searchProductForStendersByComponent(int siteid,String keyword,int pagesize,int startrow,int type) {
        List results = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        Article article = null;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select pageid from tbl_relatedartids where pagetype=0 and cname=?");
            pstmt.setString(1,keyword);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                if (!rs.next()) break;
            }

            for (int i = 0; i < pagesize; i++) {
                if (rs.next()) {
                    article = new Article();
                    article.setID(rs.getInt("pageid"));
                    results.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return results;
    }

    //???????
    //ordertype = 0 ?????????
    //ordertype = 1 ?????
    //ordertype = 2 ???????
    public List orderProducts(int siteid,int columnid,int pagesize,int startrow,int ordertype) {
        List results = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String colids="";
        Article article = null;
        String orderby = "";

        try{
            conn = cpool.getConnection();
            colids = getColumnIDs(columnid);

            String ReferArticleSQL = "select articleid from tbl_refers_article where columnid in " + colids + " and siteid="+siteid;
            pstmt=conn.prepareStatement(ReferArticleSQL);
            rs = pstmt.executeQuery();
            String artid="";
            while (rs.next())
                artid += rs.getInt(1) + ",";
            rs.close();
            pstmt.close();
            if (artid.length() > 0) artid = artid.substring(0, artid.length() - 1);

            if (ordertype == 0)                            //??????????
                orderby = " order by maintitle";
            else if (ordertype == 1)                       //???????
                orderby = " order by saleprice desc";
            else                                           //???????
                orderby = " order by saleprice asc";

            //String sql = "select id,columnid,siteid,maintitle,summary,saleprice,dirname,articlepic,status from tbl_article where siteid=? and columnid in " + colids + orderby;
            String sql = "select id,columnid,siteid,maintitle,summary,saleprice,dirname,articlepic,status from tbl_article where siteid=? and (columnid in " + colids;
            if (artid.length() > 0)
                sql = sql +" or id in ("+ artid +"))" + orderby;
            else
                sql = sql + ")" + orderby;

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                if (!rs.next()) break;
            }

            for (int i = 0; i < pagesize; i++) {
                if (rs.next()) {
                    article = new Article();
                    article.setID(rs.getInt("id"));
                    article.setColumnID(rs.getInt("columnid"));
                    article.setSiteID(rs.getInt("siteid"));
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setSummary(rs.getString("summary"));
                    article.setSalePrice(rs.getFloat("saleprice"));
                    article.setDirName(rs.getString("dirname"));
                    article.setArticlepic(rs.getString("articlepic"));
                    article.setStatus(rs.getInt("status"));
                    results.add(article);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return results;
    }

    public int orderProductCount(int siteid,int columnid,int ordertype) {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String colids="";

        try{
            conn = cpool.getConnection();
            colids = getColumnIDs(columnid);

            String ReferArticleSQL = "select articleid from tbl_refers_article where columnid in " + colids + " and siteid="+siteid;
            pstmt=conn.prepareStatement(ReferArticleSQL);
            rs = pstmt.executeQuery();
            String artid="";
            while (rs.next())
                artid += rs.getInt(1) + ",";
            rs.close();
            pstmt.close();
            if (artid.length() > 0) artid = artid.substring(0, artid.length() - 1);

            String sql = "select count(id) from tbl_article where siteid=? and (columnid in " + colids;

            if (artid.length() > 0)
                sql = sql + " or id in (" + artid + "))";
            else
                sql = sql + ")";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return count;
    }

    private String getColumnIDs(int columnID) {
        IColumnManager columnMgr = ColumnPeer.getInstance();
        int siteID = 0;
        try {
            siteID = columnMgr.getColumn(columnID).getSiteID();
        } catch (ColumnException e) {
            e.printStackTrace();
        }
        //???????TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
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

    public String getColumnIDs(int siteID, int columnID) {
        String result = "(";
        Tree colTree = TreeManager.getInstance().getSiteTree(siteID);
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID);
        for (int i = 0; i < cid[0] - 1; ++i) {
            result = result + cid[(i + 1)] + ",";
        }
        result = result + cid[cid[0]] + ")";
        return result;
    }

    //???????????????????ID
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

        cid[0] = j - 1;            //cid[0]???????????????????

        return cid;
    }

    public List searchProductForStenders(int siteid,String keyword,int pagesize,int startrow) {
        List results = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        Article article = null;

        try{
            conn = cpool.getConnection();
            String columnids = "";
            pstmt = conn.prepareStatement("select id from tbl_column where siteid=? and isproduct=1");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                columnids = columnids + rs.getInt("id") + ",";
            }
            rs.close();
            pstmt.close();

            if (columnids != null && columnids!="") columnids = columnids.substring(0,columnids.length()-1);

            //String sqlstr = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select id,maintitle,vicetitle,summary,dirname,saleprice,pic,bigpic,articlepic,createdate,filename from tbl_article where siteid=2191 and maintitle like '%????%' and columnid in (45882,45906,45907,45908,45909,45910,45915,45917,45918,45919,45920,45921,45922,45938,45939,45940,45941,45942,45943,45944,45945,45946,45947,45948,45949,45950,45951,45952,45953,45954,45955,45956,45957,45958,45959,45960)) A WHERE ROWNUM <= 9) WHERE RN >= 0";

            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (select id,maintitle,vicetitle,summary,dirname,saleprice,pic,bigpic,articlepic,createdate,filename from tbl_article where siteid=? and maintitle like '%" +keyword +  "%' and columnid in (" + columnids + ")) A WHERE ROWNUM <= ?) WHERE RN >= ?");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,startrow + pagesize);
            pstmt.setInt(3,startrow);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setViceTitle(rs.getString("vicetitle"));
                article.setSummary(rs.getString("summary"));
                article.setDirName(rs.getString("dirname"));
                article.setSalePrice(rs.getFloat("saleprice"));
                article.setProductPic(rs.getString("pic"));
                article.setProductBigPic(rs.getString("bigpic"));
                article.setArticlepic(rs.getString("articlepic"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setFileName(rs.getString("filename"));
                results.add(article);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return results;
    }

    public int searchProductNumForStenders(int siteid,String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int count = 0;

        try{
            conn = cpool.getConnection();
            String columnids = "";
            pstmt = conn.prepareStatement("select id from tbl_column where siteid=? and isproduct=1");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                columnids = columnids + rs.getInt("id") + ",";
            }
            rs.close();
            pstmt.close();

            if (columnids != null && columnids!="") columnids = columnids.substring(0,columnids.length()-1);

            pstmt = conn.prepareStatement("select count(id) from tbl_article where siteid=? and maintitle like '%" + keyword + "%' and columnid in (" + columnids + ")");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return count;
    }

    public String genrateProductPageForStenders(int productid) {
        String buf = "";
        String buf1 = "";
        String buf2 = "";
        String m_pic = "";
        String dirName = "";
        String next_link = "#";
        String prev_link = "#";
        IArticleManager articleMgr = ArticlePeer.getInstance();
        try {
            Turnpic tpic = null;
            relatedArticle chenfen = null;
            Article article = articleMgr.getArticle(productid);                           //获取文章内容
            List list = articleMgr.getArticleTurnPic(productid);                          //获取文章图片列表
            List attechments = articleMgr.getOneArticleRelatedArticles(productid,0);      //获取文章附件
            Article notes = articleMgr.getArticle(article.getNotes());                    //获取文章说明信息

            Article next_article = articleMgr.getNextArticle(productid, article.getColumnID(),article.getSiteID(),1);       //上一篇文章
            Article prev_article = articleMgr.getNextArticle(productid, article.getColumnID(),article.getSiteID(),0);       //下一篇文章

            if (next_article!=null) {
                String next_createdate_path = next_article.getCreateDate().toString().substring(0, 10);
                next_createdate_path = next_createdate_path.replaceAll("-", "") + "/";
                dirName = next_article.getDirName();
                next_link = dirName + next_createdate_path + next_article.getID() + ".shtml";
            }
            if (prev_article!=null) {
                String prev_createdate_path = prev_article.getCreateDate().toString().substring(0, 10);
                prev_createdate_path = prev_createdate_path.replaceAll("-", "") + "/";
                dirName = prev_article.getDirName();
                prev_link = dirName + prev_createdate_path + prev_article.getID() + ".shtml";
            }

            //生成小图片索引列表
            for(int i=0; i<list.size(); i++) {
                tpic = new Turnpic();
                tpic = (Turnpic)list.get(i);
                String picname_s = tpic.getPicname();                                 //获取小图片名称
                int posi = picname_s.lastIndexOf(".");
                String picname_no_extname = picname_s.substring(0,posi);             //获取没有扩展名的图片名称
                String extname = picname_s.substring(posi);                          //图片扩展名称
                String picname_ts = picname_no_extname + "_ts" + extname;           //特小图片名称
                String picname_ss = picname_no_extname + "_s" + extname;            //小图片名称
                String picname_ms = picname_no_extname + "_ms" + extname;           //中小图片名称
                if (i==0) m_pic= picname_ms;
                String picname_m = picname_no_extname + "_m" + extname;             //中图片名称
                String picname_ml = picname_no_extname + "_ml" + extname;           //中大图片名称
                String picname_l = picname_no_extname + "_l" + extname;             //大图片名称
                String picname_tl = picname_no_extname + "_tl" + extname;           //特大图片名称
                buf1 = buf1 + "<div id=\"gallery\"><a href=\"" + picname_l +"\"><img src=\"" + picname_ts + "\" width=\"59\" height=\"41\" /></a></div>\r\n";
            }

            //获取成分内容
            for(int i=0; i<attechments.size(); i++) {
                chenfen = (relatedArticle)attechments.get(i);
                Article articleOfChengfen = articleMgr.getArticle(Integer.parseInt(chenfen.getFilename()));
                if (articleOfChengfen != null) {
                    String content = articleOfChengfen.getContent();
                    int posi = content.toLowerCase().indexOf("<body>");
                    if (posi>-1) content = content.substring(posi + "<body>".length());
                    posi = content.indexOf("</body>");
                    content = content.substring(0,posi);
                    if (i == 0)
                        buf2 = buf2 + "<DIV class=\"ingridiens\"><IMG style=\"CURSOR: pointer\" onclick=OpenIngridiens(" + articleOfChengfen.getID() + "); alt=" + articleOfChengfen.getMainTitle() + " src=\"" + articleOfChengfen.getDirName() + "images/" + articleOfChengfen.getViceTitle() + "\"> \r\n" +
                                "          <DIV id=\"ingridiens" + articleOfChengfen.getID() + "\">\r\n" +
                                "               <H3>" + articleOfChengfen.getMainTitle() + "</H3>\r\n" +
                                "               " + content + "\r\n" +
                                "           </DIV>\r\n" +
                                "      </DIV>\r\n";
                    else {
                        buf2 = buf2 +  "<DIV class=\"ingridiens\"><IMG style=\"CURSOR: pointer\" onclick=OpenIngridiens(" + articleOfChengfen.getID() + "); alt=\"" + articleOfChengfen.getMainTitle() + "\" src=\"" + articleOfChengfen.getDirName() + "images/" + articleOfChengfen.getViceTitle() + "\"> \r\n" +
                                "              <DIV id=\"ingridiens" + articleOfChengfen.getID() + "\" style=\"DISPLAY: none\">\r\n" +
                                "                   <H3>" + articleOfChengfen.getMainTitle() +  "</H3>\r\n" +
                                "                   " + content + "\r\n" +
                                "              </DIV>\r\n" +
                                "         </DIV>\r\n";

                    }
                }
            }


            if (article.getStatus() == 4) {                            //促销品
                String content = article.getContent();
                int posi = content.toLowerCase().indexOf("<body>");
                if (posi>-1) content = content.substring(posi + "<body>".length());
                posi = content.indexOf("</body>");
                content = content.substring(0,posi);

                String notes_content = notes.getContent();
                posi = notes_content.toLowerCase().indexOf("<body>");
                if (posi>-1) notes_content = notes_content.substring(posi + "<body>".length());
                posi = notes_content.indexOf("</body>");
                notes_content = notes_content.substring(0,posi);

                buf = "<table width=\"484\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" background:url(/images/sdl_bg8.gif) no-repeat; height:260px;\">" +
                        "   <tr>\r\n" +
                        "       <td width=\"236\" align=\"left\" valign=\"top\"><div class=\"leftcol\">\r\n" +
                        "         <div class=\"col_title\">" + article.getMainTitle() +"</div>\r\n" +
                        //"         <div style=\"left: 50%; margin-left: -68px; width: 136px; position: absolute\">\r\n" +
                        "         <div id=\"Layer2\"><img height=\"86\" alt=\"\" width=\"40\" src=\"/images/sdl_sale.png\" /></div>\r\n" +
                        //"         </div>\r\n" +
                        "         <div class=\"preview\"><img alt=\"Apricot body lotion\" src=\"" + m_pic + "\" width=\"205\" height=\"135\" /></div>\r\n" +
                        "         <div class=\"addClear\" id=\"thumbnailWrap\">\r\n" +
                        buf1 +
                        "         </div>\r\n" +
                        "     </td>\r\n" +
                        "              <td width=\"248\" align=\"left\" valign=\"top\">\r\n" +
                        "           <div class=\"txt_top\"><a href=\"" + next_link + "\"><img src=\"/images/next.png\" /></a> <a href=\"" + prev_link + "\"><img src=\"/images/pre.png\" /></a></div>\r\n" +
                        "     <div class=\"txt_content\">" + content + "</div>\r\n" +
                        "     <div class=\"txt_buy\">\r\n" +
                        "           <ul>\r\n" +
                        "            <li class=\"txtone\">价格：<br /><span>￥" + article.getSalePrice() + "</span> RMB /" + article.getBrand() + "</li>\r\n" +
                        "         <li class=\"txttwo\"><a href=\"#\" onclick=\"javascript:showDialog();\">购买方式</a></li>\r\n" +
                        "        </ul>\r\n" +
                        "     </div>\r\n" +
                        "     </td>\r\n" +
                        "            </tr>\r\n" +
                        "</table>\r\n" +
                        "\r\n" +
                        "<table width=\"484\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                        "            <tr>\r\n" +
                        "              <td>&nbsp;</td>\r\n" +
                        "            </tr>\r\n" +
                        "            <tr>\r\n" +
                        "              <td>\r\n" +
                        "     <DIV id=\"catItemDescription\">\r\n" +
                        "        <UL class=\"addClear\" id=\"tabNavigation\">\r\n" +
                        "         <LI class=\"active\"><A onclick=Loadtab(this); href=\"javascript:;\" rel=ingrid>主要成份</A> </LI>\r\n" +
                        "         <LI><A onclick=Loadtab(this); href=\"javascript:;\" rel=manual>使用说明</A> </LI>\r\n" +
                        "         </UL>\r\n" +
                        "         <DIV class=\"description TabedData\" id=\"ingrid\">\r\n" +
                        buf2 +
                        "         </DIV>\r\n" +
                        "         <DIV class=\"description hidden TabedData\" id=\"description\"></DIV>\r\n" +
                        "         <DIV class=\"description hidden TabedData\" id=\"manual\"><p>" + notes_content + "</p></DIV>\r\n" +
                        "     </DIV>\r\n" +
                        "    </td>\r\n" +
                        "   </tr>\r\n" +
                        "</table>";
            } else if (article.getStatus() == 5){                     //新品
                String content = article.getContent();
                int posi = content.toLowerCase().indexOf("<body>");
                if (posi>-1) content = content.substring(posi + "<body>".length());
                posi = content.indexOf("</body>");
                content = content.substring(0,posi);

                String notes_content = notes.getContent();
                posi = notes_content.toLowerCase().indexOf("<body>");
                if (posi>-1) notes_content = notes_content.substring(posi + "<body>".length());
                posi = notes_content.indexOf("</body>");
                notes_content = notes_content.substring(0,posi);

                buf = "<table width=\"484\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" background:url(/images/sdl_bg8.gif) no-repeat; height:260px;\">" +
                        "   <tr>\r\n" +
                        "       <td width=\"236\" align=\"left\" valign=\"top\"><div class=\"leftcol\">\r\n" +
                        "         <div class=\"col_title\">" + article.getMainTitle() +"</div>\r\n" +
                        //"         <div style=\"left: 50%; margin-left: -68px; width: 136px; position: absolute\">\r\n" +
                        "         <div id=\"Layer2\"><img height=\"86\" alt=\"\" width=\"40\" src=\"/images/newItem.png\" /></div>\r\n" +
                        //"         </div>\r\n" +
                        "         <div class=\"preview\"><img alt=\"Apricot body lotion\" src=\"" + m_pic + "\" width=\"205\" height=\"135\" /></div>\r\n" +
                        "         <div class=\"addClear\" id=\"thumbnailWrap\">\r\n" +
                        buf1 +
                        "         </div>\r\n" +
                        "     </td>\r\n" +
                        "              <td width=\"248\" align=\"left\" valign=\"top\">\r\n" +
                        "           <div class=\"txt_top\"><a href=\"" + next_link + "\"><img src=\"/images/next.png\" /></a> <a href=\"" + prev_link + "\"><img src=\"/images/pre.png\" /></a></div>\r\n" +
                        "     <div class=\"txt_content\">" + content + "</div>\r\n" +
                        "     <div class=\"txt_buy\">\r\n" +
                        "           <ul>\r\n" +
                        "            <li class=\"txtone\">价格：<br /><span>￥" + article.getSalePrice() + "</span> RMB /" + article.getBrand() + "</li>\r\n" +
                        "         <li class=\"txttwo\"><a href=\"#\" onclick=\"javascript:showDialog();\">购买方式</a></li>\r\n" +
                        "        </ul>\r\n" +
                        "     </div>\r\n" +
                        "     </td>\r\n" +
                        "            </tr>\r\n" +
                        "</table>\r\n" +
                        "\r\n" +
                        "<table width=\"484\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                        "            <tr>\r\n" +
                        "              <td>&nbsp;</td>\r\n" +
                        "            </tr>\r\n" +
                        "            <tr>\r\n" +
                        "              <td>\r\n" +
                        "     <DIV id=\"catItemDescription\">\r\n" +
                        "        <UL class=\"addClear\" id=\"tabNavigation\">\r\n" +
                        "         <LI class=\"active\"><A onclick=Loadtab(this); href=\"javascript:;\" rel=ingrid>主要成份</A> </LI>\r\n" +
                        "         <LI><A onclick=Loadtab(this); href=\"javascript:;\" rel=manual>使用说明</A> </LI>\r\n" +
                        "         </UL>\r\n" +
                        "         <DIV class=\"description TabedData\" id=\"ingrid\">\r\n" +
                        buf2 +
                        "         </DIV>\r\n" +
                        "         <DIV class=\"description hidden TabedData\" id=\"description\"></DIV>\r\n" +
                        "         <DIV class=\"description hidden TabedData\" id=\"manual\"><p>" + notes_content + "</p></DIV>\r\n" +
                        "     </DIV>\r\n" +
                        "    </td>\r\n" +
                        "   </tr>\r\n" +
                        "</table>";
            } else {
                String content = article.getContent();
                int posi = content.toLowerCase().indexOf("<body>");
                if (posi>-1) content = content.substring(posi + "<body>".length());
                posi = content.indexOf("</body>");
                content = content.substring(0,posi);

                String notes_content = notes.getContent();
                posi = notes_content.toLowerCase().indexOf("<body>");
                if (posi>-1) notes_content = notes_content.substring(posi + "<body>".length());
                posi = notes_content.indexOf("</body>");
                notes_content = notes_content.substring(0,posi);

                buf = "<table width=\"484\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\" background:url(/images/sdl_bg8.gif) no-repeat; height:260px;\">" +
                        "   <tr>\r\n" +
                        "       <td width=\"236\" align=\"left\" valign=\"top\"><div class=\"leftcol\">\r\n" +
                        "         <div class=\"col_title\">" + article.getMainTitle() +"</div>\r\n" +
                        "         <div class=\"preview\"><img alt=\"Apricot body lotion\" src=\"" + m_pic + "\" width=\"205\" height=\"135\" /></div>\r\n" +
                        "         <div class=\"addClear\" id=\"thumbnailWrap\">\r\n" +
                        buf1 +
                        "         </div>\r\n" +
                        "     </td>\r\n" +
                        "              <td width=\"248\" align=\"left\" valign=\"top\">\r\n" +
                        "           <div class=\"txt_top\"><a href=\"" + next_link + "\"><img src=\"/images/next.png\" /></a> <a href=\"" + prev_link + "\"><img src=\"/images/pre.png\" /></a></div>\r\n" +
                        "     <div class=\"txt_content\">" + content + "</div>\r\n" +
                        "     <div class=\"txt_buy\">\r\n" +
                        "           <ul>\r\n" +
                        "            <li class=\"txtone\">价格：<br /><span>￥" + article.getSalePrice() + "</span> RMB /" + article.getBrand() + "</li>\r\n" +
                        "         <li class=\"txttwo\"><a href=\"#\" onclick=\"javascript:showDialog();\">购买方式</a></li>\r\n" +
                        "        </ul>\r\n" +
                        "     </div>\r\n" +
                        "     </td>\r\n" +
                        "            </tr>\r\n" +
                        "</table>\r\n" +
                        "\r\n" +
                        "<table width=\"484\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                        "            <tr>\r\n" +
                        "              <td>&nbsp;</td>\r\n" +
                        "            </tr>\r\n" +
                        "            <tr>\r\n" +
                        "              <td>\r\n" +
                        "     <DIV id=\"catItemDescription\">\r\n" +
                        "        <UL class=\"addClear\" id=\"tabNavigation\">\r\n" +
                        "         <LI class=\"active\"><A onclick=Loadtab(this); href=\"javascript:;\" rel=ingrid>主要成份</A> </LI>\r\n" +
                        "         <LI><A onclick=Loadtab(this); href=\"javascript:;\" rel=manual>使用说明</A> </LI>\r\n" +
                        "         </UL>\r\n" +
                        "         <DIV class=\"description TabedData\" id=\"ingrid\">\r\n" +
                        buf2 +
                        "         </DIV>\r\n" +
                        "         <DIV class=\"description hidden TabedData\" id=\"description\"></DIV>\r\n" +
                        "         <DIV class=\"description hidden TabedData\" id=\"manual\"><p>" + notes_content + "</p></DIV>\r\n" +
                        "     </DIV>\r\n" +
                        "    </td>\r\n" +
                        "   </tr>\r\n" +
                        "</table>";
            }
        } catch (ArticleException exp) {
            exp.printStackTrace();
        }
        return buf;
    }

    //?????????????
    public Article lingLiangForChristByDay(java.sql.Timestamp thedate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        Article article = null;
        int totalnum = 0;
        String now = "";
        String month_s = "";
        String day_s = "";

        try{
            conn = cpool.getConnection();
            Calendar h = Calendar.getInstance();
            h.setTime(thedate);
            if (h.get(Calendar.MONTH) + 1 < 10)
                month_s = "0" + (h.get(Calendar.MONTH) + 1);
            else
                month_s = "" + (h.get(Calendar.MONTH) + 1);
            if (h.get(Calendar.DAY_OF_MONTH) < 10)
                day_s = "0" + h.get(Calendar.DAY_OF_MONTH);
            else
                day_s = "" + h.get(Calendar.DAY_OF_MONTH);

            now = h.get(Calendar.YEAR) + "-" + month_s + "-" + day_s;

            int articleid = 0;
            if (h.get(Calendar.MONTH) == 0 && Calendar.DAY_OF_MONTH == 1)  {  //?????????1??1??
                //????????????????????????????
                pstmt = conn.prepareStatement("select articleid from tbl_lingyan_byday where CONVERT(char, seldate, 23)=?");
                pstmt.setString(1, now);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    articleid = rs.getInt("articleid");
                }
                rs.close();
                pstmt.close();

                //???????????????????????????????????
                if(articleid == 0) {
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement("delete from tbl_lingyan_byday");
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                }
            } else {
                pstmt = conn.prepareStatement("select articleid from tbl_lingyan_byday where CONVERT(char, seldate, 23)='" + now + "'");
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    articleid = rs.getInt("articleid");
                }
                rs.close();
                pstmt.close();
            }

            if (articleid > 0) {
                pstmt = conn.prepareStatement("select id,maintitle,content from tbl_article where id=?");
                pstmt.setInt(1,articleid);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    article = new Article();
                    article.setID(articleid);
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
                }
                rs.close();
                pstmt.close();
            } else {
                //???????????????
                pstmt = conn.prepareStatement("select count(*) from tbl_article where columnid=54 and id not in (select articleid from tbl_lingyan_byday)");
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    totalnum = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                //?????????????????
                int recnum = generateRandom(1,totalnum);

                //?????????????????
                pstmt = conn.prepareStatement("select id,maintitle,content from(select top 1 id,maintitle,content from (select top " + recnum + " id,maintitle,content from tbl_article where columnid=54 and id not in (select articleid from tbl_lingyan_byday) order by id) t1 order by ID desc) t2 order by ID ");
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    articleid = rs.getInt("id");
                    article = new Article();
                    article.setID(articleid);
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
                }
                rs.close();
                pstmt.close();

                //???????????ID???tbl_lingyan_byday??
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("INSERT INTO tbl_lingyan_byday (articleid,seldate) VALUES (?, ?)");
                pstmt.setInt(1,articleid);
                pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return article;
    }

    private int generateRandom(int a, int b) {
        int temp = 0;
        try {
            if (a > b) {
                temp = new Random().nextInt(a - b);
                return temp + b;
            } else {
                temp = new Random().nextInt(b - a);
                return temp + a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp + a;
    }

    //?????????????
    public Article lingLiangForChristByWeek(java.sql.Timestamp thedate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        Article article = null;
        int totalnum = 0;

        try{
            conn = cpool.getConnection();
            Calendar h = Calendar.getInstance();
            h.setTime(thedate);
            int weeknum = h.get(Calendar.WEEK_OF_YEAR);
            int articleid = 0;
            if (weeknum == 1)  {
                pstmt = conn.prepareStatement("select articleid from tbl_linglang_byweek where weeknum=?");
                pstmt.setInt(1, weeknum);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    articleid = rs.getInt("articleid");
                }
                rs.close();
                pstmt.close();

                if(articleid == 0) {
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement("delete from tbl_linglang_byweek");
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                }
            } else {
                pstmt = conn.prepareStatement("select articleid from tbl_linglang_byweek where weeknum=? and articleid!=0");
                pstmt.setInt(1, weeknum);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    articleid = rs.getInt("articleid");
                }
                rs.close();
                pstmt.close();
            }

            System.out.println("weeknu=" + weeknum);
            System.out.println("articleid=" + articleid);


            if (articleid > 0) {
                pstmt = conn.prepareStatement("select id,maintitle,content from tbl_article where id=?");
                pstmt.setInt(1,articleid);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    article = new Article();
                    article.setID(articleid);
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
                }
                rs.close();
                pstmt.close();
            } else {
                pstmt = conn.prepareStatement("select count(*) from tbl_article where columnid=53 and id not in (select articleid from tbl_linglang_byweek where articleid!=0)");
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    totalnum = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                int recnum = generateRandom(1,totalnum);

                pstmt = conn.prepareStatement("select id,maintitle,content from(select top 1 id,maintitle,content from (select top " + recnum + " id,maintitle,content from tbl_article where columnid=53  and id not in (select articleid from tbl_linglang_byweek where articleid!=0) order by id) t1 order by ID desc) t2 order by ID ");
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    articleid = rs.getInt("id");
                    article = new Article();
                    article.setID(articleid);
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
                }
                rs.close();
                pstmt.close();

                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("INSERT INTO tbl_linglang_byweek (articleid,weeknum,createdate) VALUES (?, ?, ?)");
                pstmt.setInt(1,articleid);
                pstmt.setInt(2,weeknum);
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return article;
    }

    //????????????
    //flag=0 ????????
    //flag=1 ?????????
    public List searchBookForChrist(int flag,String keyword) {
        List results = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        Article article = null;

        try{
            conn = cpool.getConnection();

            if (flag == 0)
                pstmt = conn.prepareStatement("select id,columnid,maintitle,vicetitle,summary,dirname,saleprice,pic,bigpic,articlepic,createdate,filename from tbl_article where vicetitle=? and columnid in (58,59,60,61,62,63,64,65,66,67,68)");
            else
                pstmt = conn.prepareStatement("select id,columnid,maintitle,vicetitle,summary,dirname,saleprice,pic,bigpic,articlepic,createdate,filename from tbl_article where author=? and columnid in (58,59,60,61,62,63,64,65,66,67,68)");
            pstmt.setString(1,keyword);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setColumnID(rs.getInt("columnid"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setViceTitle(rs.getString("vicetitle"));
                article.setSummary(rs.getString("summary"));
                article.setDirName(rs.getString("dirname"));
                article.setSalePrice(rs.getFloat("saleprice"));
                article.setProductPic(rs.getString("pic"));
                article.setProductBigPic(rs.getString("bigpic"));
                article.setArticlepic(rs.getString("articlepic"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setFileName(rs.getString("filename"));
                results.add(article);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return results;
    }

    //???????????????????????????????
    public int getArticleCountByColumn(int columnid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int count = 0;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(id) from tbl_article where columnid=?");
            pstmt.setInt(1,columnid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return count;
    }

    //??????????????????????????
    public List getArticlesByColumn(int columnid,int startrow,int pagesize) {
        List articles = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        Article article = null;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from (select top " + pagesize + " * from (select top " + (startrow+pagesize-1) + " id,columnid,sortid,maintitle,dirname,createdate from tbl_article where columnid=? order by sortid asc) t1 order by sortid desc) t2 order by sortid asc");
            pstmt.setInt(1,columnid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setColumnID(rs.getInt("columnid"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setDirName(rs.getString("dirname"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                articles.add(article);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return articles;
    }

    //?????????????
    public List getArticlesByUser(int siteid, String cids, String memberid, String rolecat, int startrow, int pagesize) {
        List articles = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Article article = null;
        try
        {
            String SQL_GET_ARTICLES_BYUSER = "select * from (select top ? * from(SELECT top ? id,maintitle,vicetitle,summary,keyword,dirname,emptycontentflag,createdate,publishtime,lastupdated FROM tbl_article where siteid=? and columnid in " + cids + " and (id in (SELECT articleid FROM artilce_authorized_to_user where targetid=? or targetid=?)) order by createdate desc) as A order by publishtime asc) as B order by publishtime desc";

            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLES_BYUSER);
            pstmt.setInt(1, pagesize);
            pstmt.setInt(2, startrow + pagesize);
            pstmt.setInt(3, siteid);
            pstmt.setString(4, memberid);
            pstmt.setString(5, rolecat);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setViceTitle(rs.getString("vicetitle"));
                article.setSummary(rs.getString("summary"));
                article.setKeyword(rs.getString("keyword"));
                article.setDirName(rs.getString("dirname"));
                article.setNullContent(rs.getInt("emptycontentflag"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setPublishTime(rs.getTimestamp("publishtime"));
                article.setLastUpdated(rs.getTimestamp("lastupdated"));
                articles.add(article);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) this.cpool.freeConnection(conn);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return articles;
    }

    public int getArticlesCountByUser(int siteid, String cids, String memberid, String rolecat) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try
        {
            String SQL_GET_ARTICLES_COUNT_BYUSER = "SELECT count(id) FROM tbl_article where siteid=? and columnid in " + cids + " and (id in (SELECT articleid FROM artilce_authorized_to_user where targetid=? or targetid=?))";
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLES_COUNT_BYUSER);
            pstmt.setInt(1, siteid);
            pstmt.setString(2, memberid);
            pstmt.setString(3, rolecat);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) this.cpool.freeConnection(conn);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return count;
    }
    //?????????????

    //????????????????????????
    public String generateSecondLevelTree(int siteid, int parentcolumnid)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer buf = new StringBuffer();
        StringBuffer secondbuf = null;
        List firstlevel = new ArrayList();
        Column column = null;
        try
        {
            conn = this.cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT id,cname FROM tbl_column where siteid = ? and parentid = ?  order by orderID asc");
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, parentcolumnid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = new Column();
                column.setID(rs.getInt("id"));
                column.setCName(StringUtil.gb2iso4View(rs.getString("cname")));
                firstlevel.add(column);
            }
            rs.close();
            pstmt.close();

            buf.append("<ul id=\"subtree" + parentcolumnid + "\">\r\n");
            for (int i = 0; i < firstlevel.size(); ++i) {
                buf.append("<li>");
                column = (Column)firstlevel.get(i);
                buf.append("<img src=\"/images/contact_06.gif\" border=\"0\"> " + column.getCName() + "\r\n");
                int subnodes = 0;
                secondbuf = new StringBuffer();
                secondbuf.append("<ul>\r\n");
                pstmt = conn.prepareStatement("SELECT id,cname FROM tbl_column where siteid = ? and parentid = ?  order by orderID asc");
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, column.getID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    secondbuf.append("<li>");
                    secondbuf.append("&nbsp;&nbsp;&nbsp;-<a href=\"listarticle.jsp?column=" + rs.getInt("id") + "\" target=\"_blank\">" + StringUtil.gb2iso4View(rs.getString("cname")) + "</a>");
                    secondbuf.append("</li>\r\n");
                    subnodes += 1;
                }
                rs.close();
                pstmt.close();
                secondbuf.append("</ul>\r\n");
                if (subnodes == 0)
                    buf.append("</li>\r\n");
                else {
                    buf.append(secondbuf).append("</li>\r\n");
                }
            }
            buf.append("</ul>\r\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) this.cpool.freeConnection(conn);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return buf.toString();
    }

    public int saveUploadFile(int userflag, String filename, String username, int siteid, String fileDir, String maintitle, String vicetitle, String summary, String keyword, String source, int sortid, int year, int month, int day, int hour, int minute, int columnID, List userid, List roleid)
            throws Exception
    {
        int errcode = 0;
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        Timestamp publishtime = new Timestamp(year - 1900, month - 1, day, hour, minute, 0, 0);
        try {
            Article article = new Article();
            article.setMainTitle(maintitle);
            article.setViceTitle(vicetitle);
            article.setSummary(summary);
            article.setKeyword(keyword);
            article.setSource(source);
            article.setPublishTime(publishtime);
            article.setSortID(sortid);
            article.setFileName(filename);
            article.setColumnID(columnID);
            article.setEditor(username);
            article.setCreateDate(new Timestamp(System.currentTimeMillis()));
            article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            article.setAuditFlag(0);
            article.setPubFlag(0);
            article.setUserflag(userflag);
            article.setContent("");
            article.setNullContent(1);
            article.setDirName(fileDir);
            article.setSiteID(siteid);
            article.setReferArticleID(0);
            article.setJoinRSS(0);
            article.setClickNum(0);
            article.setViceDocLevel(0);
            article.setT1(0);
            article.setT2(0);
            article.setT3(0);
            article.setT4(0);
            article.setT5(0);

            List extendList = new ArrayList();
            extendMgr.create(extendList, null, article, null, null, null, null,null);
        }
        catch (ExtendAttrException e) {
            errcode = -3;
            System.out.println(e.getMessage());
        }
        return errcode;
    }

    public int updateUploadFile(int userflag, String filename, String username, int siteid, String fileDir, int articleid, String maintitle, String vicetitle, String summary, String keyword, String source, int sortid, int year, int month, int day, int hour, int minute, List userid, List roleid)
            throws Exception
    {
        int errcode = 0;
        IArticleManager articleMgr = ArticlePeer.getInstance();
        Article article = articleMgr.getArticle(articleid);
        Timestamp publishtime = new Timestamp(year - 1900, month - 1, day, hour, minute, 0, 0);
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        try
        {
            article = new Article();
            article.setMainTitle(maintitle);
            article.setViceTitle(vicetitle);
            article.setSummary(summary);
            article.setKeyword(keyword);
            article.setSource(source);
            article.setSortID(sortid);
            article.setFileName(filename);
            article.setEditor(username);
            article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            article.setAuditFlag(0);
            article.setPubFlag(0);
            article.setPublishTime(publishtime);
            article.setAuditFlag(0);
            article.setPubFlag(0);
            article.setContent("");
            article.setNullContent(1);
            article.setUserflag(userflag);
            article.setDirName(fileDir);
            article.setSiteID(siteid);
            article.setID(articleid);
            article.setCreateDate(new Timestamp(System.currentTimeMillis()));
            article.setT1(0);
            article.setT2(0);
            article.setT3(0);
            article.setT4(0);
            article.setT5(0);
            List extendList = new ArrayList();
            extendMgr.update(extendList, null, null, null, null, null, null, null);
        }
        catch (ExtendAttrException e) {
            errcode = -3;
            System.out.println(e.getMessage());
        }
        return errcode;
    }
}
