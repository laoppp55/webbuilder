package com.bizwink.cms.refers;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.CmsException;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.orderArticleListManager.orderArticleException;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Du Zhenqiang
 * Date: 2008-2-18
 * Time: 17:36:53
 */
public class RefersPeer implements IRefersManager {
    PoolServer cpool;

    public RefersPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IRefersManager getInstance() {
        return CmsServer.getInstance().getFactory().getRefersManager();
    }

    //文章的被引用信息
    private static String SQL_GET_REFERS_TO = "SELECT s.sitename,a.columnname FROM tbl_refers_article a,tbl_siteinfo s " +
            "WHERE a.siteid=s.siteid and a.scolumnid = ? AND a.articleid = ?";

    public String getRefersTo(int articleId, int columnId) throws CmsException {
        String refersTo = "";
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_TO);
            pstmt.setInt(1, columnId);
            pstmt.setInt(2, articleId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                refersTo = refersTo + "文章被引用到:" + StringUtil.gb2iso4View(rs.getString(1)) + "站点中" +
                        StringUtil.gb2iso4View(rs.getString(2)) + "\"\r\n";
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return refersTo;
    }

    //文章的被引用信息
    private static String SQL_GET_REFERSARTICLE_BY_COLOMN = "SELECT tsiteid,columnid,columnname FROM tbl_refers_article " +
                    "WHERE siteid=? and scolumnid = ? AND articleid = ? and artfrom=1";

    public List getRefersArticleByColumn(int articleId, int columnId,int siteid) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        List cls = new ArrayList();
        Refers refers = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERSARTICLE_BY_COLOMN);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2, columnId);
            pstmt.setInt(3, articleId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                refers = new Refers();
                refers.setId(rs.getInt("tsiteid"));
                refers.setColumnid(rs.getInt("columnid"));
                refers.setColumnname(rs.getString("columnname"));
                cls.add(refers);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return cls;
    }


    //文章引用来源信息
    private static String SQL_GET_REFERS_FROM = "SELECT s.sitename,c.cname FROM tbl_refers_article a ,tbl_column c," +
            "tbl_siteinfo s WHERE a.siteid=s.siteid and a.scolumnid = c.id and a.scolumnid = ? AND a.articleid = ? AND" +
            " a.columnid = ?";

    public String getRefersFrom(int sColumnId, int articleId, int columnId) throws CmsException {
        String refersFrom = "";
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_FROM);
            pstmt.setInt(1, sColumnId);
            pstmt.setInt(2, articleId);
            pstmt.setInt(3, columnId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                refersFrom = "文章引用自：" + StringUtil.gb2iso4View(rs.getString(1)) + "站点" + StringUtil.gb2iso4View(rs.getString(2));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return refersFrom;
    }

    private static final String SQL_GET_REFERS_ARTICLE_PUB = "select pubflag from tbl_refers_article where articleid = ?" +
            " and columnid = ? and tsiteid=?";

    public int getRefersArticlePubFlag(int articleId, int columnId, int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        int pubflag = 1;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_ARTICLE_PUB);
            pstmt.setInt(1, articleId);
            pstmt.setInt(2, columnId);
            pstmt.setInt(3, siteid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                pubflag = rs.getInt(1);

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return pubflag;
    }

    private static final String SQL_GET_REFERS_ARTICLE_USEARTICLE = "select useArticleType from tbl_refers_article where articleid = ?" +
            " and columnid = ?";

    public int getRefersArticleUseType(int articleId, int columnId) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int useArticleType = 0;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_ARTICLE_USEARTICLE);
            pstmt.setInt(1, articleId);
            pstmt.setInt(2, columnId);
            rs = pstmt.executeQuery();
            if (rs.next()) useArticleType = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return useArticleType;
    }

    private static final String SQL_UPDATE_REFERS_ARTICLE_PUB_BYCONTENT = "update tbl_refers_article set pubflag = ? where articleid = ?" +
            " and columnid = ? and usearticletype = ?";

    private static final String SQL_UPDATE_REFERS_ARTICLE_PUB_BYLINK = "update tbl_refers_article set pubflag = ? where articleid = ?" +
            " and usearticletype = ?";

    public void updateRefersArticlePubFlag(int articleId, int columnId, int pubflag,int refertype) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt;

        //System.out.println("articleid=" + articleId);
        //System.out.println("columnId=" + columnId);
        //System.out.println("pubflag=" + pubflag);

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (refertype == 1) {
                    pstmt = conn.prepareStatement(SQL_UPDATE_REFERS_ARTICLE_PUB_BYCONTENT);
                    pstmt.setInt(1, pubflag);
                    pstmt.setInt(2, articleId);
                    pstmt.setInt(3, columnId);
                    pstmt.setInt(4, refertype);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt = conn.prepareStatement(SQL_UPDATE_REFERS_ARTICLE_PUB_BYLINK);
                    pstmt.setInt(1, pubflag);
                    pstmt.setInt(2, articleId);
                    pstmt.setInt(3, refertype);
                    pstmt.executeUpdate();
                    pstmt.close();

                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final String SQL_REMOVE_REFERS_ARTICLE = "delete tbl_refers_article where articleid = ? and columnid = ?";

    public void remove(int articleId, int columnId) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_REMOVE_REFERS_ARTICLE);
                pstmt.setInt(1, articleId);
                pstmt.setInt(2, columnId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
