package com.bizwink.cms.toolkit.websites;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebsiteInfoPeer implements IWebsiteManager {
    PoolServer cpool;

    public WebsiteInfoPeer(PoolServer cpool) {
        this.cpool = cpool;
    }
    public static IWebsiteManager getInstance() {
        return CmsServer.getInstance().getFactory().getWebsiteInfoPeer();
    }

    //向表中插入记录
    private static String SQL_INSERTSINOCOMPANYCLASS_FOR_ORACLE = "insert into tbl_websiteclass (siteid,parentid,orderid,cname,ename,summary,dirname,editor,extname,createdate,lastupdated,id) values (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String SQL_INSERTSINOCOMPANYCLASS_FOR_MSSQL = "insert into tbl_websiteclass (siteid,parentid,orderid,cname,ename,summary,dirname,editor,extname,createdate,lastupdated) values (?,?,?,?,?,?,?,?,?,?,?)";
    private static String SQL_INSERTSINOCOMPANYCLASS_FOR_MYSQL = "insert into tbl_websiteclass (siteid,parentid,orderid,cname,ename,summary,dirname,editor,extname,createdate,lastupdated) values (?,?,?,?,?,?,?,?,?,?,?)";

    public void create(WebsiteClass companyclass){
        Connection conn = null;
        PreparedStatement pstmt;
        int orderid = 0;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        String ctname = companyclass.getCname();
        String desc = companyclass.getDesc();

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select max(orderid) from tbl_websiteclass where siteid=? and parentid=?");
            pstmt.setInt(1,companyclass.getSiteid());
            pstmt.setInt(2,companyclass.getParentid());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) orderid = rs.getInt(1);
            rs.close();
            pstmt.close();

            if (ctname != null) ctname = StringUtil.gb2isoindb(ctname);
            if (desc != null) desc = StringUtil.gb2isoindb(desc);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANYCLASS_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANYCLASS_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANYCLASS_FOR_MYSQL);
            pstmt.setInt(1,companyclass.getSiteid());
            pstmt.setInt(2,companyclass.getParentid());
            pstmt.setInt(3,orderid+1);
            pstmt.setString(4,ctname);
            pstmt.setString(5,companyclass.getEname());
            pstmt.setString(6,companyclass.getDesc());
            pstmt.setString(7,companyclass.getDirname());
            pstmt.setString(8,companyclass.getEditor());
            pstmt.setString(9,companyclass.getExtname());
            pstmt.setTimestamp(10,companyclass.getCreatedate());
            pstmt.setTimestamp(11,companyclass.getLastupdated());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(12, sequnceMgr.getSequenceNum("CompanyInfo"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_DELETE_COMPANYCLASS = "delete FROM tbl_websiteclass WHERE siteid = ? AND id = ?";

    public void remove(int id,int siteID) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_DELETE_COMPANYCLASS);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
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
    }

    private static final String SQL_UPDATECOLUMN = "UPDATE tbl_websiteclass SET cname = ?,OrderID = ?,summary=?,Extname = ?,editor=?,LastUpdated = ? WHERE siteid=? and ID = ?";

    public void update(WebsiteClass companyclass,int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN);

                pstmt.setString(1, StringUtil.iso2gb (companyclass.getCname()));
                pstmt.setInt(2,companyclass.getOrderid());
                pstmt.setString(3, companyclass.getDesc());
                pstmt.setString(4, companyclass.getExtname());
                pstmt.setString(5, companyclass.getEditor());
                pstmt.setTimestamp(6,companyclass.getLastupdated());
                pstmt.setInt(7, siteid);
                pstmt.setInt(8, companyclass.getId());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
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
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public WebsiteClass getCompanyClass(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        WebsiteClass companyclass = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_websiteclass where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                companyclass = new WebsiteClass();
                companyclass = loadCompanyClass(rs);
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
                    e.printStackTrace();
                }
            }
        }
        return companyclass;
    }

    private static final String SQL_GET_INDEX_EXTNAME = "SELECT extname FROM tbl_websiteclass WHERE siteid = ? AND parentid = 0";

    public String getIndexExtName(int siteID) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String extname = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_INDEX_EXTNAME);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                extname = rs.getString("ExtName");
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
        return extname;
    }

    private static final String SQL_checkDualEnName = "SELECT id FROM tbl_websiteclass WHERE parentid=? and ename=?";

    public boolean duplicateEnName(int parentColumnID, String enName) {
        Connection conn = null;
        PreparedStatement pstmt;
        boolean existflag = false;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_checkDualEnName);
            pstmt.setInt(1, parentColumnID);
            pstmt.setString(2, enName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existflag = true;
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
        return existflag;
    }

    private WebsiteClass loadCompanyClass(ResultSet rs) throws Exception{
        WebsiteClass companyclass = new WebsiteClass();
        try{
            companyclass.setId(rs.getInt("id"));
            companyclass.setSiteid(rs.getInt("siteid"));
            companyclass.setParentid(rs.getInt("parentid"));
            companyclass.setOrderid(rs.getInt("orderid"));
            companyclass.setCname(rs.getString("cname"));
            companyclass.setEname(rs.getString("ename"));
            companyclass.setDirname(rs.getString("dirname"));
            companyclass.setEditor(rs.getString("editor"));
            companyclass.setExtname(rs.getString("extname"));
            companyclass.setDesc(rs.getString("summary"));
            companyclass.setCreatedate(rs.getTimestamp("createdate"));
            companyclass.setLastupdated(rs.getTimestamp("lastupdated"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return companyclass;
    }

}
