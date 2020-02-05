package com.bizwink.cms.business.Product;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class productPeer implements IProductManager {
    PoolServer cpool;

    public productPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IProductManager getInstance() {
        return CmsServer.getInstance().getFactory().getProductManager();
    }


    public List getProductList() throws ProductException {
        List list = new ArrayList();
        Product product;
        String sqlstr = "select * from tbl_article";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                product = load(rs);
                list.add(product);
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

    public Product getAProduct(int productid) throws ProductException {
        Product product = new Product();
        String sqlstr = "select * from tbl_article where id=?";
        String get_suppliername = "select company from tbl_members where siteid=?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int siteid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, productid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                product = load(rs);
                siteid = product.getSiteID();
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(get_suppliername);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                product.setSuppliername(rs.getString("company"));
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
        return product;
    }

    Product load(ResultSet rs) throws SQLException {
        Product article = new Product();
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
            article.setInPrice(rs.getFloat("InPrice"));
            article.setMarketPrice(rs.getFloat("MarketPrice"));
            article.setBrand(rs.getString("Brand"));
            article.setProductPic(rs.getString("Pic"));
            article.setProductBigPic(rs.getString("BigPic"));
            article.setProductWeight(rs.getInt("Weight"));
            article.setStockNum(rs.getInt("StockNum"));
            article.setScores(rs.getInt("score"));
            article.setVoucher(rs.getInt("voucher"));
            article.setSiteID(rs.getInt("siteid"));
        }
        catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return article;
    }

    //check status by feixiang 2009-03-13
    private static String CHECK_STATUS = "select status from tbl_article where id = ?";

    public int checkStatus(int pid) throws ProductException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int status = 1;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(CHECK_STATUS);
            pstmt.setInt(1, pid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                status = rs.getInt("status");
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
        return status;
    }

    //检查BBN的商品卡的库存
    private static String CHECK_STOCK_FOR_BBN = "select count(*) from tbl_bbn_product where cardtype = ? and flag=0";

    public int checkStockForBBN(int pid) throws ProductException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(CHECK_STOCK_FOR_BBN);
            pstmt.setInt(1, pid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
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
        return count;
    }
}
