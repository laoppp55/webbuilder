package com.bizwink.collectionmgr;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2007-11-8
 * Time: 17:54:14
 */
public class Basic_AttributesPeer implements IBasic_AttributesManager {
    PoolServer cpool;

    public Basic_AttributesPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IBasic_AttributesManager getInstance() {
        return CmsServer.getInstance().getFactory().getBasic_AttributesManager();
    }

    private final static String sql_getBasicAttr = "select ba.id as id,ba.keywordflag,sitename,starturl,urlnumber,loginflag," +
            "posturl,status,postdata,proxyflag,kr.tKeyword,kr.bKeyword,kr.tbRelation,ba.stopflag from sp_basic_attributes ba," +
            "sp_keyword_rules kr where ba.id=? and ba.id = kr.starturlid";

    public Basic_Attributes getBasic_Attributes(int basicId) {
        Basic_Attributes basicAttr = new Basic_Attributes();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(sql_getBasicAttr);
            pstmt.setInt(1, basicId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                basicAttr.setId(rs.getInt("id"));
                basicAttr.setSiteName(rs.getString("sitename"));
                basicAttr.setStartUrl(rs.getString("starturl"));
                basicAttr.setUrlNumber(rs.getInt("urlnumber"));
                basicAttr.setLoginflag(rs.getInt("loginflag"));
                basicAttr.setPosturl(rs.getString("posturl"));
                basicAttr.setStatus(rs.getString("status"));
                basicAttr.setPostdata(rs.getString("postdata"));
                basicAttr.setProxyflag(rs.getInt("proxyflag"));
                basicAttr.setKeywordflag(rs.getInt("keywordflag"));
                basicAttr.setStopflag(rs.getInt("stopflag"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return basicAttr;
    }

    public Basic_Attributes getKeywordsOfSite(int siteId) {
        Basic_Attributes keyword = null;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select * from sp_keyword_rules where starturlid = ?");
            pstmt.setInt(1, siteId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                keyword = new Basic_Attributes();
                keyword.setTKeyword(rs.getString("tkeyword"));
                keyword.setBKeyword(rs.getString("bkeyword"));
                keyword.setTbRelation(rs.getInt("tbrelation"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return keyword;
    }

    private final static String sql_updateBasicAttr = "update sp_basic_attributes set sitename=?,starturl=?," +
            "urlnumber=?,loginflag=?,posturl=?,status=?,postdata=?,proxyflag=?,keywordflag=? where id=?";

    private final static String SQL_DELETE_PROXY = "delete from sp_proxy where starturlid = ?";

    private final static String SQL_DELETE_BASICCOLUMNS = "delete from sp_basic_columns where basicid = ?";

    private final static String SQL_CREATE_BASICCOLUMNS = "insert into sp_basic_columns (basicid,classid,createdate,siteid,id) values (?, ?, ?, ?, ?)";

    private final static String SQL_UPDATE_SITE_KEYWORDS = "update sp_keyword_rules set tkeyword = ?,bkeyword = ?,tbrelation=?" +
            " where starturlid=?";

    /**
     * @param basicAttr ����
     */
    public void updateBasic_Attributes(int siteid,Basic_Attributes basicAttr, List basicColumn) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);

            if (basicAttr.getProxyflag() == 1) {
                pstmt = con.prepareStatement(SQL_DELETE_PROXY);
                pstmt.setInt(1, basicAttr.getId());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = con.prepareStatement(SQL_ADD_PROXY);
                pstmt.setInt(1, basicAttr.getId());
                pstmt.setString(2, basicAttr.getProxyurl());
                pstmt.setString(3, basicAttr.getProxyport());
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt = con.prepareStatement(SQL_DELETE_PROXY);
                pstmt.setInt(1, basicAttr.getId());
                pstmt.executeUpdate();
                pstmt.close();
            }

            if (basicAttr.getKeywordflag() == 1) {
                pstmt = con.prepareStatement(SQL_UPDATE_SITE_KEYWORDS);
                pstmt.setString(1, basicAttr.getTKeyword());
                pstmt.setString(2, basicAttr.getBKeyword());
                pstmt.setInt(3, basicAttr.getTbRelation());
                pstmt.setInt(4, basicAttr.getId());
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt = con.prepareStatement(SQL_UPDATE_SITE_KEYWORDS);
                pstmt.setString(1, null);
                pstmt.setString(2, null);
                pstmt.setInt(3, 2);
                pstmt.setInt(4, basicAttr.getId());
                pstmt.executeUpdate();
                pstmt.close();
            }

            pstmt = con.prepareStatement(sql_updateBasicAttr);
            pstmt.setString(1, basicAttr.getSiteName());
            pstmt.setString(2, basicAttr.getStartUrl());
            pstmt.setInt(3, basicAttr.getUrlNumber());
            pstmt.setInt(4, basicAttr.getLoginflag());
            pstmt.setString(5, basicAttr.getPosturl());
            pstmt.setString(6, "W");
            pstmt.setString(7, basicAttr.getPostdata());
            pstmt.setInt(8, basicAttr.getProxyflag());
            pstmt.setInt(9, basicAttr.getKeywordflag());
            pstmt.setInt(10, basicAttr.getId());
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement(SQL_DELETE_BASICCOLUMNS);
            pstmt.setInt(1, basicAttr.getId());
            pstmt.executeUpdate();
            pstmt.close();

            for (int i = 0; i < basicColumn.size(); i++) {
                int getclassid = (Integer.parseInt((String) basicColumn.get(i)));
                pstmt = con.prepareStatement(SQL_CREATE_BASICCOLUMNS);
                pstmt.setInt(1, basicAttr.getId());
                pstmt.setInt(2, getclassid);
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(4,siteid);
                pstmt.setInt(5,getMaxId("select max(id) from sp_basic_columns"));
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (SQLException se) {
            try {
                se.printStackTrace();
                con.rollback();
            } catch (NullPointerException rollse) {
                rollse.printStackTrace();
            } catch (SQLException rse) {
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if (!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private final static String sql_addBasicAttr = "insert into sp_basic_attributes(id,siteid,sitename,starturl,urlnumber" +
            ",loginflag,posturl,status,postdata,proxyflag,keywordflag,stopflag,createdate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String SQL_ADD_PROXY = "insert into sp_proxy(siteid,starturlid, proxyloginuser,proxyloginpass,proxyurl, proxyport,createdate,id) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";

    private final static String sql_createBasicColumn = "insert into sp_basic_columns (siteid,basicid,classid,createdate,id) values (?, ?, ?, ?, ?)";

    private final static String sql_insert_sp_keyword_rules = "insert into sp_keyword_rules (siteid,starturlid,Tkeyword,bkeyword," +
            "tbrelation,createdate,id) values (?, ?, ?, ?, ?, ?, ?)";

    public void addBasicAttr(int siteid,Basic_Attributes basicAttr, List basicColumn) {
        Connection con = null;
        PreparedStatement pstmt;
        int maxId = getMaxId("select max(id) from sp_basic_attributes");

        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);

            if (basicAttr.getProxyflag() == 1) {
                pstmt = con.prepareStatement(SQL_ADD_PROXY);
                pstmt.setInt(1,siteid);
                pstmt.setInt(2, maxId);
                pstmt.setString(3, basicAttr.getProxyloginuser());
                pstmt.setString(4, basicAttr.getProxyloginpass());
                pstmt.setString(5, basicAttr.getProxyurl());
                pstmt.setString(6, basicAttr.getProxyport());
                pstmt.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(8,getMaxId("select max(id) from sp_proxy"));
                pstmt.executeUpdate();
                pstmt.close();
            }

            pstmt = con.prepareStatement(sql_addBasicAttr);
            pstmt.setInt(1, maxId);
            pstmt.setInt(2,siteid);
            pstmt.setString(3, basicAttr.getSiteName());
            pstmt.setString(4, basicAttr.getStartUrl());
            pstmt.setInt(5, basicAttr.getUrlNumber());
            pstmt.setInt(6, basicAttr.getLoginflag());
            pstmt.setString(7, basicAttr.getPosturl());
            pstmt.setString(8, "W");
            pstmt.setString(9, basicAttr.getPostdata());
            pstmt.setInt(10, basicAttr.getProxyflag());
            pstmt.setInt(11, basicAttr.getKeywordflag());
            pstmt.setInt(12,1);
            pstmt.setTimestamp(13,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement(sql_insert_sp_keyword_rules);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2, maxId);
            pstmt.setString(3, basicAttr.getTKeyword());
            pstmt.setString(4, basicAttr.getBKeyword());
            pstmt.setInt(5, basicAttr.getTbRelation());
            pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(7, getMaxId("select max(id) from sp_keyword_rules"));
            pstmt.executeUpdate();
            pstmt.close();

            for (int i = 0; i < basicColumn.size(); i++) {
                int getclassid = (Integer.parseInt((String) basicColumn.get(i)));
                pstmt = con.prepareStatement(sql_createBasicColumn);
                pstmt.setInt(1,siteid);
                pstmt.setInt(2, maxId);
                pstmt.setInt(3, getclassid);
                pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                int iid = getMaxId("select max(id) from sp_basic_columns");
                pstmt.setInt(5,iid);
                pstmt.executeUpdate();
                pstmt.close();
            }
            //con.commit();
            //createTable(maxId);
        } catch (SQLException se) {
            try {
                con.rollback();
            } catch (NullPointerException rollse) {
                rollse.printStackTrace();
            } catch (SQLException rse) {
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if (!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /*private void createTable(int id) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("create table tbl_workload_" + Integer.toString(id) + "(Id number not null," +
                    "url varchar2(500),status varchar2(5),lastmodified  date,urltitle varchar2(500),urltype number," +
                    "thedate date)");
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try {
                con.rollback();
            } catch (NullPointerException rollse) {
                rollse.printStackTrace();
            } catch (SQLException rse) {
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }*/

    public int getMaxId() {
        int maxId = -1;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select max(id) from sp_basic_attributes");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return maxId + 1;
    }

    public int getMaxId(String sql) {
        int maxId = 0;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if (!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return maxId + 1;
    }

    private final static String sql_getSiteName = "select sitename  from sp_basic_attributes";

    public List getSiteNameList() {
        List siteNameList = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(sql_getSiteName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String siteName = rs.getString(1);
                siteNameList.add(siteName);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return siteNameList;
    }

    private final static String sql_getStartUrl = "select starturl from sp_basic_attributes where sitename=?";

    public List getStartUrlList(String siteName) {
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List startUrlList = new ArrayList();
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(sql_getStartUrl);
            pstmt.setString(1, siteName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String startUrl = rs.getString(1);
                startUrlList.add(startUrl);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return startUrlList;
    }

    public List getBasic_Attributes(int start, int range) {
        List baList = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select * from sp_basic_attributes order by id desc");
            rs = pstmt.executeQuery();
            for (int i = 0; i < start; i++) rs.next();
            for (int i = 0; rs.next() && i < range; i++) {
                Basic_Attributes ba = new Basic_Attributes();
                ba.setId(rs.getInt("id"));
                ba.setSiteName(rs.getString("sitename"));
                ba.setLoginflag(rs.getInt("loginflag"));
                ba.setProxyflag(rs.getInt("proxyflag"));
                ba.setStopflag(rs.getInt("stopflag"));
                baList.add(ba);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return baList;
    }

    public int getBasicId(String siteName) {
        int basicId = -1;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select id from sp_basic_attributes where sitename=?");
            pstmt.setString(1, siteName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                basicId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return basicId;
    }

    public void delBasicAttr(int id) {
        Connection con = null;
        PreparedStatement pstmt;

        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("delete from sp_basic_attributes where id=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement("delete from sp_basic_columns where basicid = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement("drop table tbl_workload_" + id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement("delete from sp_match_url where starturlid=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement("delete from sp_special_code where starturlid=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement("delete from sp_proxy where starturlid=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = con.prepareStatement("delete from sp_keyword_rules where starturlid=?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            con.commit();
        } catch (SQLException se) {
            se.printStackTrace();
            try {
                con.rollback();
            } catch (NullPointerException rollse) {
                rollse.printStackTrace();
            } catch (SQLException rse) {
                rse.printStackTrace();
            }
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public String getColumnName(int classid) {
        Connection con = null;
        String cname = "";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select cname from tbl_column where id=?");
            pstmt.setInt(1, classid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cname = rs.getString("cname");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return cname;
    }

    public List getColumnNames(int classid) {
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List columnList = new ArrayList();
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select c.id,c.cname from tbl_column c,sp_basic_columns b where c.id = b.classid and b.basicid=?");
            pstmt.setInt(1, classid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Basic_Attributes ba = new Basic_Attributes();
                ba.setClassId(rs.getInt(1));
                ba.setCname(rs.getString(2));
                columnList.add(ba);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return columnList;
    }

    public int getMaxColumn() {
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int columnNum = -1;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select count(id) from sp_basic_attributes");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                columnNum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if (!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return columnNum;
    }

    //����ץȡ��־λΪ1ʱ��ʾץȡ��Ϊ0ʱ��ʾ��ץȡ
    private static String SQL_UPDATESP_basic_attributes = "update sp_basic_attributes set stopflag=? where id=?";

    public void updateSpiderStopFlag(int id, int stopflag) {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATESP_basic_attributes);
                pstmt.setInt(1, stopflag);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String SQL_UPDATE_KEYWORDFLAG = "update sp_basic_attributes set keywordflag=? where id=?";

    public void updateBasicAttrKeywordFlag(int id, int flag) {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_UPDATE_KEYWORDFLAG);
            pstmt.setInt(1, flag);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public GlobalConfig getGlobalConfig() {
        GlobalConfig glbcfg = new GlobalConfig();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select * from sp_global");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                glbcfg.setInterval(rs.getInt("interval"));
                glbcfg.setProxyName(rs.getString("proxyname"));
                glbcfg.setProxyPort(rs.getString("proxyport"));
                glbcfg.setStartTime(rs.getTimestamp("starttime"));
                glbcfg.setProxyflag(rs.getInt("proxyflag"));
                glbcfg.setProxyloginflag(rs.getInt("proxyloginflag"));
                glbcfg.setProxyloginuser(rs.getString("proxyloginuser"));
                glbcfg.setProxyloginpass(rs.getString("proxyloginpass"));
                glbcfg.setSystemrun(rs.getInt("systemrun"));
                glbcfg.setKeywordflag(rs.getInt("keywordflag"));
                glbcfg.setTkeyword(rs.getString("tkeyword"));
                glbcfg.setBkeyword(rs.getString("bkeyword"));
                glbcfg.setTbrelation(rs.getInt("tbrelation"));
                glbcfg.setId(rs.getInt("id"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return glbcfg;
    }

    public void update_GlobalConfig(int siteid,GlobalConfig global) {
        if (check()) {
            update(siteid,global);
        } else {
            addGlobal(siteid,global);
        }
    }

    private final static String sql_addglobal = "insert into sp_global (id,siteid,starttime,interval,proxyname,proxyport,proxyflag," +
            "proxyloginflag, proxyloginuser, proxyloginpass,keywordflag,tkeyword,bkeyword,tbrelation,systemrun,createdate) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void addGlobal(int siteid,GlobalConfig global) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(sql_addglobal);
            int iid = getMaxId("select max(id) from sp_global");
            pstmt.setInt(1, iid);
            pstmt.setInt(2,siteid);
            pstmt.setTimestamp(3, global.getStartTime());
            pstmt.setInt(4, global.getInterval());
            pstmt.setString(5, global.getProxyName());
            pstmt.setString(6, global.getProxyPort());
            pstmt.setInt(7, global.getProxyflag());
            pstmt.setInt(8, global.getProxyloginflag());
            pstmt.setString(9, global.getProxyloginuser());
            pstmt.setString(10, global.getProxyloginpass());
            pstmt.setInt(11, global.getKeywordflag());
            pstmt.setString(12, global.getTkeyword());
            pstmt.setString(13, global.getBkeyword());
            pstmt.setInt(14, global.getTbrelation());
            pstmt.setInt(15,0);
            pstmt.setTimestamp(16,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try {
                con.rollback();
            } catch (NullPointerException rollse) {
                rollse.printStackTrace();
            } catch (SQLException rse) {
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if (!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private final static String sql_update_globalconfig = "update sp_global set starttime=?,interval=?,proxyname=?," +
            "proxyport=?,proxyflag=?,proxyloginflag=?,proxyloginuser=?,proxyloginpass=?,keywordflag=?,tkeyword=?,bkeyword=?,tbrelation=?,systemrun=? where Id=?";

    public void update(int siteid,GlobalConfig global) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(sql_update_globalconfig);
            pstmt.setTimestamp(1, global.getStartTime());
            pstmt.setInt(2, global.getInterval());
            pstmt.setString(3, global.getProxyName());
            pstmt.setString(4, global.getProxyPort());
            pstmt.setInt(5, global.getProxyflag());
            pstmt.setInt(6, global.getProxyloginflag());
            pstmt.setString(7, global.getProxyloginuser());
            pstmt.setString(8, global.getProxyloginpass());
            pstmt.setInt(9, global.getKeywordflag());
            pstmt.setString(10, global.getTkeyword());
            pstmt.setString(11, global.getBkeyword());
            pstmt.setInt(12, global.getTbrelation());
            pstmt.setInt(13,0);
            pstmt.setInt(14,global.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try {
                con.rollback();
            } catch (NullPointerException rollse) {
                rollse.printStackTrace();
            } catch (SQLException rse) {
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if (con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private boolean check() {
        boolean mark = false;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select * from sp_global");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                mark = true;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return mark;
    }

    public GlobalConfig getProxyConfigOfSite(int siteId) {
        GlobalConfig glbcfg = new GlobalConfig();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select * from sp_proxy where starturlid = ?");
            pstmt.setInt(1, siteId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                glbcfg.setProxyName(rs.getString("proxyurl"));
                glbcfg.setProxyPort(rs.getString("proxyport"));
                glbcfg.setProxyloginflag(rs.getInt("proxyloginflag"));
                glbcfg.setProxyloginuser(rs.getString("proxyloginuser"));
                glbcfg.setProxyloginpass(rs.getString("proxyloginpass"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return glbcfg;
    }

    public void updateSystemRun(int run) {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update sp_global set systemrun = ?");
            pstmt.setInt(1, run);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String SQL_GETGOLBALEKEYWORD = "select tkeyword,bkeyword,tbrelation from sp_global";

    public GlobalConfig getGlobalKeyword() {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        GlobalConfig global = new GlobalConfig();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETGOLBALEKEYWORD);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                global.setTkeyword(rs.getString("tkeyword"));
                global.setBkeyword(rs.getString("bkeyword"));
                global.setTbrelation(rs.getInt("tbrelation"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return global;
    }
}
