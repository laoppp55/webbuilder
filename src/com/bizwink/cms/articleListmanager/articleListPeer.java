package com.bizwink.cms.articleListmanager;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.ArticleException;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.tree.*;

public class articleListPeer implements IArticleListManager
{
    PoolServer cpool;

    public articleListPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IArticleListManager getInstance() {
        return (IArticleListManager)CmsServer.getInstance().getFactory().getArticleListManager();
    }

    //判断采集的信息是否已经在数据库里面存在
    //siteid:本网站的站点ID
    //fromsiteid：信息来源网站
    //sarticleid：信息来源的文章ID
    public boolean existTheArticle(int siteid,int fromsiteid,String sarticleid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean  existflag = false;
        String SQL_GET_EXIST_ARTICLE = "SELECT id FROM tbl_article where siteid=? and fromsiteid=? and sarticleid=?";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_EXIST_ARTICLE);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,fromsiteid);
            pstmt.setString(3,sarticleid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                existflag = true;
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

        return existflag;
    }

    public List getArticleList(String articleIDs) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Article article=null;

        String SQL_GET_ARTICLES_LIST =
                "SELECT id,siteid,columnid,maintitle,summary,dirname,author,articlepic,pic,bigpic,defineurl,createdate,publishtime FROM tbl_article where id in (" + articleIDs + ") order by publishtime desc";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLES_LIST);
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

    public List getArticleList(int threadnum) throws ArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Article article=null;
        String SQL_GET_ARTICLES_LIST = "SELECT id,siteid,columnid,maintitle,summary,dirname,author,articlepic,pic,bigpic,defineurl,createdate,publishtime FROM tbl_article where columnid=31306 order by publishtime desc";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLES_LIST);
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

    Article load(ResultSet rs) throws Exception {
        Article article = new Article();
        try {
            article.setID(rs.getInt("id"));
            article.setSiteID(rs.getInt("siteid"));
            article.setColumnID(rs.getInt("columnid"));
            article.setMainTitle(rs.getString("maintitle"));
            article.setSummary(rs.getString("summary"));
            article.setDirName(rs.getString("dirname"));
            article.setAuthor(rs.getString("author"));
            article.setArticlepic(rs.getString("articlepic"));
            article.setProductBigPic(rs.getString("bigpic"));
            article.setProductPic(rs.getString("pic"));
            article.setOtherurl(rs.getString("defineurl"));
            article.setCreateDate(rs.getTimestamp("createdate"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }
}