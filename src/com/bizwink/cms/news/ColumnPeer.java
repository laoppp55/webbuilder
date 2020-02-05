package com.bizwink.cms.news;

import com.bizwink.cms.publishx.Publish;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.util.*;
import com.bizwink.indexer.article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColumnPeer implements IColumnManager {
    PoolServer cpool;
    String rootPath;

    public ColumnPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public ColumnPeer(String rootPath) {
        this.rootPath = rootPath;
    }

    public static IColumnManager getInstance() {
        return CmsServer.getInstance().getFactory().getColumnManager();
    }

    private static final String SQL_GETCOLUMN = "SELECT id,siteid,dirname,orderid,parentid,ename,extname," +
            "createdate,lastupdated,cname,editor,isDefineAttr,XMLTemplate,IsAudited,ColumnDesc,IsProduct,islocation," +
            "IsPublishMore,LanguageType,contentshowtype,isrss,getrssarticletime,archivingrules,useArticleType,istype,userflag,publicflag,userlevel," +
            "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic " +
            "FROM tbl_column WHERE ID = ?";

    public Column getColumn(int ID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Column column = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETCOLUMN);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = new Column();
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
                //column.setXMLTemplate(rs.getString("XMLTemplate"));
                column.setXMLTemplate(DBUtil.getBigString(cpool.getType(), rs, "XMLTemplate"));
                column.setIsAudited(rs.getInt("IsAudited"));
                column.setDesc(rs.getString("ColumnDesc"));
                column.setIsProduct(rs.getInt("IsProduct"));
                column.setIsPosition(rs.getInt("islocation"));
                column.setIsPublishMoreArticleModel(rs.getInt("IsPublishMore"));
                column.setLanguageType(rs.getInt("LanguageType"));
                column.setContentShowType(rs.getInt("contentshowtype"));
                column.setRss(rs.getInt("isrss"));
                column.setGetRssArticleTime(rs.getInt("getrssarticletime"));
                column.setArchivingrules(rs.getInt("archivingrules"));
                column.setUseArticleType(rs.getInt("useArticleType"));
                column.setIsType(rs.getInt("istype"));
                column.setUserflag(rs.getInt("userflag"));
                column.setPublicflag(rs.getInt("publicflag"));
                column.setUserlevel(rs.getInt("userlevel"));
                column.setTitlepic(rs.getString("titlepic"));
                column.setVtitlepic(rs.getString("vtitlepic"));
                column.setSourcepic(rs.getString("sourcepic"));
                column.setAuthorpic(rs.getString("authorpic"));
                column.setContentpic(rs.getString("contentpic"));
                column.setSpecialpic(rs.getString("specialpic"));
                column.setProductpic(rs.getString("productpic"));
                column.setProductsmallpic(rs.getString("productsmallpic"));
                column.setMediasize(rs.getString("mediasize"));
                column.setMediapicsize(rs.getString("mediapicsize"));
                column.setTs_pic(rs.getString("ts_pic"));
                column.setS_pic(rs.getString("s_pic"));
                column.setMs_pic(rs.getString("ms_pic"));
                column.setM_pic(rs.getString("m_pic"));
                column.setMl_pic(rs.getString("ml_pic"));
                column.setL_pic(rs.getString("l_pic"));
                column.setTl_pic(rs.getString("tl_pic"));
            }
            rs.close();
            pstmt.close();
        } catch (Exception t) {
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
        return column;
    }

    public Column getColumn(int ID, Connection s_conn) throws ColumnException {
        PreparedStatement pstmt;
        ResultSet rs;
        Column column = null;

        try {
            pstmt = s_conn.prepareStatement(SQL_GETCOLUMN);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = new Column();

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
                //column.setXMLTemplate(rs.getString("XMLTemplate"));
                column.setXMLTemplate(DBUtil.getBigString(cpool.getType(), rs, "XMLTemplate"));
                column.setIsAudited(rs.getInt("IsAudited"));
                column.setDesc(rs.getString("ColumnDesc"));
                column.setIsProduct(rs.getInt("IsProduct"));
                column.setIsPosition(rs.getInt("islocation"));
                column.setIsPublishMoreArticleModel(rs.getInt("IsPublishMore"));
                column.setLanguageType(rs.getInt("LanguageType"));
                column.setContentShowType(rs.getInt("contentshowtype"));
                column.setRss(rs.getInt("isrss"));
                column.setGetRssArticleTime(rs.getInt("getrssarticletime"));
                column.setArchivingrules(rs.getInt("archivingrules"));
                column.setUseArticleType(rs.getInt("useArticleType"));
                column.setIsType(rs.getInt("istype"));
                column.setUserflag(rs.getInt("userflag"));
                column.setUserlevel(rs.getInt("userlevel"));
                column.setTitlepic(rs.getString("titlepic"));
                column.setVtitlepic(rs.getString("vtitlepic"));
                column.setSourcepic(rs.getString("sourcepic"));
                column.setAuthorpic(rs.getString("authorpic"));
                column.setContentpic(rs.getString("contentpic"));
                column.setSpecialpic(rs.getString("specialpic"));
                column.setProductpic(rs.getString("productpic"));
                column.setProductsmallpic(rs.getString("productsmallpic"));
                column.setTs_pic(rs.getString("ts_pic"));
                column.setS_pic(rs.getString("s_pic"));
                column.setMs_pic(rs.getString("ms_pic"));
                column.setM_pic(rs.getString("m_pic"));
                column.setMl_pic(rs.getString("ml_pic"));
                column.setL_pic(rs.getString("l_pic"));
                column.setTl_pic(rs.getString("tl_pic"));
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
        return column;
    }

    private static final String SQL_checkDualEnName =
            "SELECT id FROM tbl_column WHERE parentid=? AND ename=?";

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

    private static final String SQL_CREATECOLUMN_FOR_ORACLE =
            "INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname," +
                    "CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation," +
                    "IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag," +
                    "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic,ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATECOLUMN_FOR_MSSQL =
            "INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname," +
                    "CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation," +
                    "IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag," +
                    "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATECOLUMN_FOR_MYSQL =
            "INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname," +
                    "CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation," +
                    "IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag," +
                    "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int create(Column column) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String cname = column.getCName().trim();
        String desc = column.getDesc();
        int columnID = 0;
        Tree colTree;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        if (!column.getEditor().equalsIgnoreCase("admin"))
            colTree = TreeManager.getInstance().getSiteTree(column.getSiteID());
        else
            colTree = TreeManager.getInstance().getTree();

        if (column.getParentID() != colTree.getTreeRoot())
            column.setDirName(colTree.getDirName(colTree, column.getParentID()) + column.getEName() + "/");
        else
            column.setDirName("/" + column.getEName() + "/");

        int orderID = getNextOrder(column.getParentID());
        if (column.getParentID() == 0)                  //如果页面传过来的父栏目ID为零，设置父栏目为该站点的根
        {
            column.setParentID(colTree.getTreeRoot());
        }

        try {
            try {
                column.setOrderID(orderID);
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATECOLUMN_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATECOLUMN_FOR_MSSQL);
                else {
                    pstmt = conn.prepareStatement(SQL_CREATECOLUMN_FOR_MYSQL);
                }

                /*System.out.println("siteid=" + column.getSiteID());
                System.out.println("Dirname" + column.getDirName());
                System.out.println("OrderID=" + column.getOrderID());
                System.out.println("parentID=" + column.getParentID());
                System.out.println("Cname=" + column.getCName());
                System.out.println("Ename=" + column.getEName());
                System.out.println("Extname=" + column.getExtname());
                System.out.println("CreateDate=" + column.getCreateDate().toString());
                System.out.println("LastUpdated=" + column.getLastUpdated().toString());
                System.out.println("Editor=" + column.getEditor());
                System.out.println("isDefineAttr=" + column.getDefineAttr());
                System.out.println("XMLTemplate=" + column.getXMLTemplate());
                System.out.println("isAudited=" + column.getIsAudited());
                System.out.println("ColumnDesc=" + column.getDesc());
                System.out.println("IsProduct=" + column.getIsProduct());
                System.out.println("IsPublishMore=" + column.getIsPublishMoreArticleModel());
                System.out.println("LanguageType=" + column.getLanguageType());
                System.out.println("hasarticlemodel=" + column.getHasArticleModel());
                System.out.println("contentshowtype=" + column.getContentShowType());
                System.out.println("userflag=" + column.getUserflag());
                System.out.println("userlevel=" + column.getUserlevel());
                System.out.println("publicflag=" + column.getPublicflag());
                System.out.println("titlepic=" + column.getTitlepic());
                System.out.println("vtitlepic=" + column.getVtitlepic());
                System.out.println("sourcepic=" + column.getSourcepic());
                System.out.println("authorpic=" + column.getAuthorpic());
                System.out.println("contentpic=" + column.getContentpic());
                System.out.println("specialpic=" + column.getSpecialpic());
                System.out.println("productpic=" + column.getProductpic());
                System.out.println("productsmallpic=" + column.getProductsmallpic());
                System.out.println("ts_pic=" + column.getTs_pic());
                System.out.println("s_pic=" + column.getS_pic());
                System.out.println("ms_pic=" + column.getMs_pic());
                System.out.println("m_pic=" + column.getM_pic());
                System.out.println("ml_pic="+ column.getMl_pic());
                System.out.println("l_pic=" + column.getL_pic());
                System.out.println("tl_pic=" + column.getTl_pic());
                */
                pstmt.setInt(1, column.getSiteID());
                pstmt.setString(2, column.getDirName());
                pstmt.setInt(3, column.getOrderID());
                pstmt.setInt(4, column.getParentID());
                pstmt.setString(5, cname);
                pstmt.setString(6, column.getEName().trim());
                pstmt.setString(7, column.getExtname());
                pstmt.setTimestamp(8, column.getCreateDate());
                pstmt.setTimestamp(9, column.getLastUpdated());
                pstmt.setString(10, column.getEditor());
                pstmt.setInt(11, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 12, column.getXMLTemplate());
                else
                    pstmt.setNull(12, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(13, column.getIsAudited());
                pstmt.setString(14, desc);
                pstmt.setInt(15, column.getIsProduct());
                pstmt.setInt(16,column.getIsPosition());
                pstmt.setInt(17, column.getIsPublishMoreArticleModel());
                pstmt.setInt(18, column.getLanguageType());
                pstmt.setInt(19, 0);
                pstmt.setInt(20, column.getContentShowType());
                pstmt.setInt(21, column.getUserflag());
                pstmt.setInt(22, column.getUserlevel());
                pstmt.setInt(23, column.getPublicflag());
                pstmt.setString(24, column.getTitlepic());
                pstmt.setString(25, column.getVtitlepic());
                pstmt.setString(26, column.getSourcepic());
                pstmt.setString(27, column.getAuthorpic());
                pstmt.setString(28, column.getContentpic());
                pstmt.setString(29, column.getSpecialpic());
                pstmt.setString(30, column.getProductpic());
                pstmt.setString(31, column.getProductsmallpic());
                pstmt.setString(32,column.getMediasize());
                pstmt.setString(33,column.getMediapicsize());
                pstmt.setString(34, column.getTs_pic());
                pstmt.setString(35, column.getS_pic());
                pstmt.setString(36, column.getMs_pic());
                pstmt.setString(37, column.getM_pic());
                pstmt.setString(38, column.getMl_pic());
                pstmt.setString(39, column.getL_pic());
                pstmt.setString(40, column.getTl_pic());
                if (cpool.getType().equals("oracle")) {
                    columnID = sequnceMgr.getSequenceNum("Column");
                    pstmt.setInt(41,columnID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ColumnException("Database exception: create column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ColumnException("Database exception: can't rollback?");
        }

        return columnID;
    }

    public void create(Column column, List userid, List roleid) throws ColumnException {
        Connection conn = null;

        String cname = column.getCName().trim();
        String desc = column.getDesc();
        ResultSet rs = null;

        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Tree colTree;
        if (!column.getEditor().equalsIgnoreCase("admin"))
            colTree = TreeManager.getInstance().getSiteTree(column.getSiteID());
        else {
            colTree = TreeManager.getInstance().getTree();
        }
        if (column.getParentID() != colTree.getTreeRoot())
            column.setDirName(colTree.getDirName(colTree, column.getParentID()) + column.getEName() + "/");
        else {
            column.setDirName("/" + column.getEName() + "/");
        }
        int orderID = getNextOrder(column.getParentID());
        if (column.getParentID() == 0) {
            column.setParentID(colTree.getTreeRoot());
        }
        try {
            try {
                int id = 0;
                column.setOrderID(orderID);
                conn = this.cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement("SELECT ident_current('tbl_column')");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                if (this.cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement("INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname,CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation,IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                else if (this.cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement("INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname,CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation,IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                else {
                    pstmt = conn.prepareStatement("INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname,CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation,IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                }

                pstmt.setInt(1, column.getSiteID());
                pstmt.setString(2, column.getDirName());
                pstmt.setInt(3, column.getOrderID());
                pstmt.setInt(4, column.getParentID());
                pstmt.setString(5, cname);
                pstmt.setString(6, column.getEName().trim());
                pstmt.setString(7, column.getExtname());
                pstmt.setTimestamp(8, column.getCreateDate());
                pstmt.setTimestamp(9, column.getLastUpdated());
                pstmt.setString(10, column.getEditor());
                pstmt.setInt(11, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(this.cpool.getType(), pstmt, 12, column.getXMLTemplate());
                else
                    pstmt.setNull(12, -1);
                pstmt.setInt(13, column.getIsAudited());
                pstmt.setString(14, desc);
                pstmt.setInt(15, column.getIsProduct());
                pstmt.setInt(16,column.getIsPosition());
                pstmt.setInt(17, column.getIsPublishMoreArticleModel());
                pstmt.setInt(18, column.getLanguageType());
                pstmt.setInt(19, 0);
                pstmt.setInt(20, column.getContentShowType());
                pstmt.setInt(21, column.getUserflag());
                pstmt.setInt(22, column.getUserlevel());
                pstmt.setInt(23, column.getPublicflag());
                pstmt.setString(24, column.getTitlepic());
                pstmt.setString(25, column.getVtitlepic());
                pstmt.setString(26, column.getSourcepic());
                pstmt.setString(27, column.getAuthorpic());
                pstmt.setString(28, column.getContentpic());
                pstmt.setString(29, column.getSpecialpic());
                pstmt.setString(30, column.getProductpic());
                pstmt.setString(31, column.getProductsmallpic());
                pstmt.setString(32, column.getTs_pic());
                pstmt.setString(33, column.getS_pic());
                pstmt.setString(34, column.getMs_pic());
                pstmt.setString(35, column.getM_pic());
                pstmt.setString(36, column.getMl_pic());
                pstmt.setString(37, column.getL_pic());
                pstmt.setString(38, column.getTl_pic());
                if (this.cpool.getType().equals("oracle")) {
                    pstmt.setInt(39, sequnceMgr.getSequenceNum("Column"));
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (this.cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                String userSQL = "";

                if (this.cpool.getType().equals("oracle"))
                    userSQL = "INSERT INTO column_authorized_to_user (siteid,columnid,targetid,TYPE,id) VALUES(?,?,?,?,?)";
                else if (this.cpool.getType().equals("mssql"))
                    userSQL = "INSERT INTO column_authorized_to_user (siteid,columnid,targetid,TYPE) VALUES(?,?,?,?)";
                else {
                    userSQL = "INSERT INTO column_authorized_to_user (siteid,columnid,targetid,TYPE) VALUES(?,?,?,?)";
                }
                if (userid != null) {
                    for (int i = 0; i < userid.size(); ++i) {
                        pstmt = conn.prepareStatement(userSQL);
                        pstmt.setInt(1, column.getSiteID());
                        pstmt.setInt(2, id + 1);
                        pstmt.setString(3, (String) userid.get(i));
                        pstmt.setInt(4, 1);
                        if (this.cpool.getType().equals("oracle")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else if (this.cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else {
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                }
                if (roleid != null) {
                    for (int i = 0; i < roleid.size(); ++i) {
                        pstmt = conn.prepareStatement(userSQL);
                        pstmt.setInt(1, column.getSiteID());
                        pstmt.setInt(2, id + 1);
                        pstmt.setString(3, (String) roleid.get(i));
                        pstmt.setInt(4, 0);
                        if (this.cpool.getType().equals("oracle")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else if (this.cpool.getType().equals("mssql")) {
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
                throw new ColumnException("Database exception: create column failed.");
            } finally {
                if (conn != null)
                    try {
                        this.cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
            }
        } catch (Exception e) {
            throw new ColumnException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATECOLUMN = "UPDATE TBL_Column SET Cname = ?,Extname = ?,LastUpdated = ?,Editor = ?,OrderID = ?,isDefineAttr = ?," +
            "xmlTemplate = ?,ColumnDesc = ?,isAudited = ?,isProduct = ?,islocation=?,isPublishMore = ?,LanguageType = ?," +
            "contentshowtype = ?,isRss = ?,useArticleType = ?,istype = ?,userflag = ?,userlevel = ?,publicflag = ?," +
            "titlepic = ?, vtitlepic = ?,sourcepic = ?,authorpic = ?,contentpic =?,specialpic = ?,productpic = ?,productsmallpic = ?," +
            "ts_pic=?,s_pic=?,ms_pic=?,m_pic=?,ml_pic=?,l_pic=?,tl_pic=?,mediasize=?,mediapicsize=? " +
            "WHERE ID = ?";

    //edit for xuzheming at 2008.07.27
    public void update(Column column, int siteid) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            //获取本栏目下面所有子栏目
            //Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
            //node[] treeNodes = colTree.getAllNodes();
            //int[] subColumnIDs = getSubTreeColumnIDList(treeNodes,column.getID());

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN);

                pstmt.setString(1, StringUtil.iso2gb(column.getCName()));
                pstmt.setString(2, column.getExtname());
                pstmt.setTimestamp(3, column.getLastUpdated());
                pstmt.setString(4, StringUtil.iso2gb(column.getEditor()));
                pstmt.setInt(5, column.getOrderID());
                pstmt.setInt(6, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 7, column.getXMLTemplate());
                else
                    pstmt.setNull(7, java.sql.Types.LONGVARCHAR);
                pstmt.setString(8, column.getDesc());
                pstmt.setInt(9, column.getIsAudited());
                pstmt.setInt(10, column.getIsProduct());
                pstmt.setInt(11,column.getIsPosition());
                pstmt.setInt(12, column.getIsPublishMoreArticleModel());
                pstmt.setInt(13, column.getLanguageType());
                pstmt.setInt(14, column.getContentShowType());
                pstmt.setInt(15, column.getRss());
                pstmt.setInt(16, column.getUseArticleType());
                pstmt.setInt(17, column.getIsType());
                pstmt.setInt(18, column.getUserflag());
                pstmt.setInt(19, column.getUserlevel());
                pstmt.setInt(20, column.getPublicflag());
                pstmt.setString(21, column.getTitlepic());
                pstmt.setString(22, column.getVtitlepic());
                pstmt.setString(23, column.getSourcepic());
                pstmt.setString(24, column.getAuthorpic());
                pstmt.setString(25, column.getContentpic());
                pstmt.setString(26, column.getSpecialpic());
                pstmt.setString(27, column.getProductpic());
                pstmt.setString(28, column.getProductsmallpic());
                pstmt.setString(29, column.getTs_pic());
                pstmt.setString(30, column.getS_pic());
                pstmt.setString(31, column.getMs_pic());
                pstmt.setString(32, column.getM_pic());
                pstmt.setString(33, column.getMl_pic());
                pstmt.setString(34, column.getL_pic());
                pstmt.setString(35, column.getTl_pic());
                pstmt.setString(36,column.getMediasize());
                pstmt.setString(37,column.getMediapicsize());
                pstmt.setInt(38, column.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //如果本栏目扩展属性适用于本栏目的所有商品子栏目，修改本栏目所有商品子栏目的扩展属性
                /*String strSQL = "update tbl_column set xmlTemplate=? where isProduct=1 and id=?";
                pstmt = conn.prepareStatement(strSQL);
                for (int i = 1; i<=subColumnIDs[0]; i++) {
                    if (subColumnIDs[i] != column.getID()) {
                        if (column.getXMLTemplate() != null)
                            DBUtil.setBigString(cpool.getType(), pstmt, 1, column.getXMLTemplate());
                        else
                            pstmt.setNull(1, java.sql.Types.LONGVARCHAR);
                        pstmt.setInt(2,subColumnIDs[i]);
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();
                */

                //删除引用栏目关系
                String strSQL = "DELETE FROM tbl_refers_column WHERE tcid = ?";
                pstmt = conn.prepareStatement(strSQL);
                pstmt.setInt(1, column.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //删除文章引用关系
                //strSQL = "DELETE FROM tbl_refers_article WHERE columnid = ? AND tsiteid=? AND refers_column_status = 1";
                strSQL = "DELETE FROM tbl_refers_article WHERE columnid = ? AND tsiteid=?";
                pstmt = conn.prepareStatement(strSQL);
                pstmt.setInt(1, column.getID());
                pstmt.setInt(2, siteid);
                pstmt.executeUpdate();
                pstmt.close();

                List selectColumnIdsList = column.getSelectColumns();
                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                if (selectColumnIdsList != null) {
                    for (int i = 0; i < selectColumnIdsList.size(); i++) {
                        Column scolumn = (Column) selectColumnIdsList.get(i);

                        //将被引用的栏目写入到栏目引用表
                        if (cpool.getType().equals("oracle"))
                            strSQL = "INSERT INTO tbl_refers_column (ssiteid,scid,tsiteid,tcid,createdate,id) VALUES (?, ?, ?, ?, ?, ?)";
                        else if (cpool.getType().equals("mssql"))
                            strSQL = "INSERT INTO tbl_refers_column (ssiteid,scid,tsiteid,tcid,createdate) VALUES (?, ?, ?, ?, ?)";
                        else
                            strSQL = "INSERT INTO tbl_refers_column (ssiteid,scid,tsiteid,tcid,createdate,id) VALUES (?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(strSQL);
                        pstmt.setInt(1, scolumn.getSsiteid());
                        pstmt.setInt(2, scolumn.getScid());
                        pstmt.setInt(3, column.getSiteID());
                        pstmt.setInt(4, column.getID());
                        pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(6, sequnceMgr.getSequenceNum("Column"));
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else {
                            pstmt.executeUpdate();
                            pstmt.close();
                        }

                        //获得所有被引用栏目下的所有文章
                        int getusearticletype = column.getUseArticleType();
                        List sarticleList = new ArrayList();
                        strSQL = "SELECT a.id,C.id,C.cname,a.sortid,a.maintitle,a.createdate,a.status,a.pubflag,a.auditflag FROM tbl_article a,tbl_column C WHERE a.columnid = ? AND " +
                                "a.columnid = C.id AND a.siteid=? ";
                        pstmt = conn.prepareStatement(strSQL);
                        pstmt.setInt(1, scolumn.getScid());
                        pstmt.setInt(2, siteid);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            Article sarticle = new Article();
                            sarticle.setID(rs.getInt(1));
                            sarticle.setColumnID(rs.getInt(2));
                            sarticle.setEname(rs.getString(3));
                            sarticle.setSortID(rs.getInt(4));
                            sarticle.setMainTitle(rs.getString(5));
                            sarticle.setCreateDate(rs.getTimestamp(6));
                            sarticle.setStatus(rs.getInt(7));
                            sarticle.setPubFlag(rs.getInt(8));
                            sarticle.setAuditFlag(rs.getInt(9));
                            sarticleList.add(sarticle);
                        }
                        rs.close();
                        pstmt.close();

                        //将被引用栏目的所有文章写入到文章引用表
                        if (cpool.getType().equals("oracle"))
                            strSQL = "INSERT INTO tbl_refers_article (articleid,siteid,tsiteid,columnid,scolumnid,columnname,orders,createdate," +
                                    "title,status,pubflag,auditflag,usearticletype,refers_column_status,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        else if (cpool.getType().equals("mssql"))
                            strSQL = "INSERT INTO tbl_refers_article (articleid,siteid,tsiteid,columnid,scolumnid,columnname,orders,createdate," +
                                    "title,status,pubflag,auditflag,usearticletype,refers_column_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        else
                            strSQL = "INSERT INTO tbl_refers_article (articleid,siteid,tsiteid,columnid,scolumnid,columnname,orders,createdate," +
                                    "title,status,pubflag,auditflag,usearticletype,refers_column_status,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        List queueList = new ArrayList();
                        Publish publish = null;
                        for (int a = 0; a < sarticleList.size(); a++) {
                            Article sarticle = (Article) sarticleList.get(a);
                            pstmt = conn.prepareStatement(strSQL);
                            pstmt.setInt(1, sarticle.getID());
                            pstmt.setInt(2, column.getSiteID());
                            pstmt.setInt(3, siteid);
                            pstmt.setInt(4, column.getID());
                            pstmt.setInt(5, scolumn.getScid());
                            pstmt.setString(6, sarticle.getEname());
                            pstmt.setInt(7, sarticle.getSortID());
                            pstmt.setTimestamp(8, sarticle.getCreateDate());
                            pstmt.setString(9, sarticle.getMainTitle());
                            pstmt.setInt(10, sarticle.getStatus());
                            if (getusearticletype == 0)                               //链接引用
                                pstmt.setInt(11, sarticle.getPubFlag());
                            else                                                     //内容引用
                                pstmt.setInt(11, 1);
                            pstmt.setInt(12, sarticle.getAuditFlag());
                            pstmt.setInt(13, getusearticletype);
                            pstmt.setInt(14, 1);
                            if (cpool.getType().equals("oracle")) {
                                pstmt.setInt(15, sequnceMgr.getSequenceNum("Column"));
                                pstmt.executeUpdate();
                                pstmt.close();
                            } else if (cpool.getType().equals("mssql")) {
                                pstmt.executeUpdate();
                                pstmt.close();
                            } else {
                                pstmt.executeUpdate();
                                pstmt.close();
                            }

                            if (getusearticletype == 1) {                         //内容引用
                                publish = new Publish();
                                publish.setSiteID(siteid);
                                publish.setColumnID(column.getID());
                                publish.setTargetID(sarticle.getID());
                                publish.setTitle(sarticle.getMainTitle());
                                publish.setPriority(1);                                             //文章发布作业的优先级设置为1
                                publish.setPublishDate(sarticle.getPublishTime());
                                publish.setObjectType(1);
                                queueList.add(publish);
                            }
                        }

                        //如果是内容引用，将引用的文章写入发布队列
                        writePublishQueue.writeMoreArticleToPublishQueue(queueList,column.getSiteID(), column.getID(), cpool.getType(), pstmt, conn);
                    }

                    //向发布队列写入需要发布的栏目模板
                    writePublishQueue.writeTemplateToPublishQueue(column.getSiteID(), column.getID(), cpool.getType(), pstmt, conn);
                }

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ColumnException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ColumnException("Database exception: can't rollback?");
        }
    }

    public void update(Column column, int siteid, List userid, List roleid) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                conn = this.cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("UPDATE TBL_Column SET Cname = ?,Extname = ?,LastUpdated = ?,Editor = ?,OrderID = ?,isDefineAttr = ?,xmlTemplate = ?,ColumnDesc = ?,isAudited = ?,isProduct = ?,islocation=?,isPublishMore = ?,LanguageType = ?,contentshowtype = ?,isRss = ?,useArticleType = ?,istype = ?,userflag = ?,userlevel = ?,publicflag = ?,titlepic = ?, vtitlepic = ?,sourcepic = ?,authorpic = ?,contentpic =?,specialpic = ?,productpic = ?,productsmallpic = ?,ts_pic=?,s_pic=?,ms_pic=?,m_pic=?,ml_pic=?,l_pic=?,tl_pic=? WHERE ID = ?");

                pstmt.setString(1, StringUtil.iso2gb(column.getCName()));
                pstmt.setString(2, column.getExtname());
                pstmt.setTimestamp(3, column.getLastUpdated());
                pstmt.setString(4, StringUtil.iso2gb(column.getEditor()));
                pstmt.setInt(5, column.getOrderID());
                pstmt.setInt(6, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(this.cpool.getType(), pstmt, 7, column.getXMLTemplate());
                else
                    pstmt.setNull(7, -1);
                pstmt.setString(8, column.getDesc());
                pstmt.setInt(9, column.getIsAudited());
                pstmt.setInt(10, column.getIsProduct());
                pstmt.setInt(11,column.getIsPosition());
                pstmt.setInt(12, column.getIsPublishMoreArticleModel());
                pstmt.setInt(13, column.getLanguageType());
                pstmt.setInt(14, column.getContentShowType());
                pstmt.setInt(15, column.getRss());
                pstmt.setInt(16, column.getUseArticleType());
                pstmt.setInt(17, column.getIsType());
                pstmt.setInt(18, column.getUserflag());
                pstmt.setInt(19, column.getUserlevel());
                pstmt.setInt(20, column.getPublicflag());
                pstmt.setString(21, column.getTitlepic());
                pstmt.setString(22, column.getVtitlepic());
                pstmt.setString(23, column.getSourcepic());
                pstmt.setString(24, column.getAuthorpic());
                pstmt.setString(25, column.getContentpic());
                pstmt.setString(26, column.getSpecialpic());
                pstmt.setString(27, column.getProductpic());
                pstmt.setString(28, column.getProductsmallpic());
                pstmt.setString(29, column.getTs_pic());
                pstmt.setString(30, column.getS_pic());
                pstmt.setString(31, column.getMs_pic());
                pstmt.setString(32, column.getM_pic());
                pstmt.setString(33, column.getMl_pic());
                pstmt.setString(34, column.getL_pic());
                pstmt.setString(35, column.getTl_pic());
                pstmt.setInt(36, column.getID());
                pstmt.executeUpdate();
                pstmt.close();

                String userSQL = "";

                if (this.cpool.getType().equals("oracle"))
                    userSQL = "INSERT INTO column_authorized_to_user (siteid,columnid,targetid,TYPE,id) VALUES(?,?,?,?,?)";
                else if (this.cpool.getType().equals("mssql"))
                    userSQL = "INSERT INTO column_authorized_to_user (siteid,columnid,targetid,TYPE) VALUES(?,?,?,?)";
                else {
                    userSQL = "INSERT INTO column_authorized_to_user (siteid,columnid,targetid,TYPE) VALUES(?,?,?,?)";
                }
                if ((userid != null) || (roleid != null)) {
                    pstmt = conn.prepareStatement("DELETE FROM column_authorized_to_user WHERE siteid = ? AND columnid = ?");
                    pstmt.setInt(1, siteid);
                    pstmt.setInt(2, column.getID());
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                if (userid != null) {
                    for (int i = 0; i < userid.size(); ++i) {
                        pstmt = conn.prepareStatement(userSQL);
                        pstmt.setInt(1, siteid);
                        pstmt.setInt(2, column.getID());
                        pstmt.setString(3, (String) userid.get(i));
                        pstmt.setInt(4, 1);
                        if (this.cpool.getType().equals("oracle")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else if (this.cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else {
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                }

                if (roleid != null) {
                    for (int i = 0; i < roleid.size(); ++i) {
                        pstmt = conn.prepareStatement(userSQL);
                        pstmt.setInt(1, siteid);
                        pstmt.setInt(2, column.getID());
                        pstmt.setString(3, (String) roleid.get(i));
                        pstmt.setInt(4, 0);
                        if (this.cpool.getType().equals("oracle")) {
                            pstmt.executeUpdate();
                            pstmt.close();
                        } else if (this.cpool.getType().equals("mssql")) {
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
                throw new ColumnException("Database exception: update column failed.");
            } finally {
                if (conn != null)
                    try {
                        this.cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
            }
        } catch (Exception e) {
            throw new ColumnException("Database exception: can't rollback?");
        }
    }

    //获取子树下的所有子栏目ID
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
                if (treenodes[i].getLinkPointer() == columnID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treenodes[i].getId();
                }
            }
        } while (nodenum >= 1);

        cid[0] = j - 1;            //cid[0]元素保存找到的子节点的数目

        return cid;
    }

    public void updateColumnExtendAttr(Column column) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            String colids = "";
            if (column.getExtattrscope() == 1) {
                //获取本栏目下面所有子栏目
                Tree colTree = TreeManager.getInstance().getSiteTree(column.getSiteID());
                node[] treeNodes = colTree.getAllNodes();
                int[] subColumnIDs = getSubTreeColumnIDList(treeNodes, column.getID());
                for (int ii = 0; ii < subColumnIDs.length; ii++) {
                    if (subColumnIDs[ii] > 0) colids = colids + subColumnIDs[ii] + ",";
                }
                if (colids.length() > 0) colids = colids.substring(0, colids.length() - 1);
            }

            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (column.getExtattrscope() == 1) {
                pstmt = conn.prepareStatement("UPDATE TBL_Column SET isDefineAttr = ?,xmlTemplate = ? WHERE ID in (" + colids + ")");
                pstmt.setInt(1, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 2, column.getXMLTemplate());
                else
                    pstmt.setNull(2, java.sql.Types.LONGVARCHAR);
            }else {
                pstmt = conn.prepareStatement("UPDATE TBL_Column SET isDefineAttr = ?,xmlTemplate = ? WHERE ID = ?");
                pstmt.setInt(1, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 2, column.getXMLTemplate());
                else
                    pstmt.setNull(2, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(3, column.getID());
            }
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exp) {

            }
        } catch (SQLException exp) {

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

    private static final String SQL_UPDATE_COLUMN_RSS =
            "UPDATE TBL_Column SET getRssArticleTime = ? WHERE ID = ?";

    public void updateColumnRss(Column column) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_COLUMN_RSS);

                pstmt.setInt(1, column.getGetRssArticleTime());
                pstmt.setInt(2, column.getID());

                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ColumnException("Database exception: update column failed.");
            } finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ColumnException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_REMOVE_COLUMN = "DELETE FROM tbl_column WHERE ID = ? AND siteid = ?";

    private static final String SQL_REMOVE_TEMPLATE = "DELETE FROM tbl_template WHERE columnid = ? AND siteid=?";

    private static final String SQL_REMOVE_MEMBERS_RIGHTS = "DELETE FROM tbl_members_rights WHERE  columnid = ?";

    private static final String SQL_REMOVE_GROUP_RIGHTS = "DELETE FROM tbl_group_rights WHERE  columnid = ?";

    private static final String SQL_REMOVE_COLUMN_AUDIT_RULES = "DELETE FROM TBL_Column_Auditing_Rules WHERE  columnid = ?";

    public void remove(int ID, int siteID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_REMOVE_COLUMN);
                pstmt.setInt(1, ID);
                pstmt.setInt(2, siteID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_REMOVE_TEMPLATE);
                pstmt.setInt(1, ID);
                pstmt.setInt(2, siteID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_REMOVE_MEMBERS_RIGHTS);
                pstmt.setInt(1, ID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_REMOVE_GROUP_RIGHTS);
                pstmt.setInt(1, ID);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_REMOVE_COLUMN_AUDIT_RULES);
                pstmt.setInt(1, ID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ColumnException("Database exception: delete column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ColumnException("Database exception: can't rollback?");
        }
    }

    private static final String Get_SiteRoot_Column = "SELECT id,siteid,dirname,orderid,parentid,ename,extname," +
            "createdate,lastupdated,cname,editor,isDefineAttr,XMLTemplate,IsAudited,ColumnDesc,IsProduct,islocation," +
            "IsPublishMore,LanguageType,contentshowtype,isrss,getrssarticletime,archivingrules,useArticleType,publicflag,istype," +
            "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic " +
            "FROM tbl_column WHERE siteid = ? ORDER BY ID";

    public Column getSiteRootColumn(int siteid) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Column column = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(Get_SiteRoot_Column);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = load(rs);
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
        return column;
    }

    private static final String SQL_GETPARENTCOLUMNS = "SELECT id,siteid,dirname,orderid,parentid,ename,extname," +
            "createdate,lastupdated,cname,editor,isDefineAttr,XMLTemplate,IsAudited,ColumnDesc,IsProduct,islocation," +
            "IsPublishMore,LanguageType,contentshowtype,isrss,getrssarticletime,archivingrules,useArticleType,publicflag,istype" +
            "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic " +
            " tbl_column start WITH ID = ? CONNECT BY PRIOR parentid=id ORDER BY orderid";

    public List getParentColumns(int ID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Column column;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETPARENTCOLUMNS);
            pstmt.setInt(1,ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = load(rs);
                list.add(column);
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

    private static final String SQL_GETMAXORDER = "SELECT COUNT(ID) FROM tbl_column WHERE parentID=? ";

    int getNextOrder(int ID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int order = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETMAXORDER);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                order = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            order++;
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
        return order;
    }

    private static final String SQL_GET_ARTICLE_COUNT = "SELECT articlecount FROM tbl_column WHERE ID=? ";

    public int getArticleCount(int columnID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_COUNT);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
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
        return count;
    }

    String getColumnCode(int nextID, String parentCode) throws ColumnException {
        StringBuffer buf = new StringBuffer();
        String str = Integer.toString(nextID);
        if (parentCode != null) {
            switch (str.length()) {
                case 1:
                    buf.append(parentCode).append("00").append(str);
                    break;
                case 2:
                    buf.append(parentCode).append("0").append(str);
                    break;
                case 3:
                    buf.append(parentCode).append(str);
                    break;
            }
        } else {
            switch (str.length()) {
                case 1:
                    buf.append("00").append(str);
                    break;
                case 2:
                    buf.append("0").append(str);
                    break;
                case 3:
                    buf.append(str);
                    break;
            }
        }
        return buf.toString();
    }

    String getColumnURL(int columnID) {
        String DirName = null;
        if (columnID > -1) {
            IColumnManager columnManager = ColumnPeer.getInstance();
            try {
                Column column = columnManager.getColumn(columnID);
                DirName = column.getDirName();
            } catch (ColumnException e) {
                e.printStackTrace();
            }
        } else {
            DirName = "/";
        }
        return DirName;
    }

    private static final String SQL_GET_INDEX_EXTNAME =
            "SELECT ExtName FROM TBL_Column WHERE SiteID = ? AND ParentID = 0";

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
        return extname;
    }

    private static final String SQL_GET_COLUMN_EXTNAME =
            "SELECT ExtName FROM TBL_Column WHERE id = ?";

    public String getExtName(int columnId) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String extname = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COLUMN_EXTNAME);
            pstmt.setInt(1, columnId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                extname = rs.getString("ExtName");
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
        return extname;
    }

    private static final String SQL_GETCOLUMNS = "SELECT id,siteid,dirname,orderid,parentid,ename,extname," +
            "createdate,lastupdated,cname,editor,isDefineAttr,XMLTemplate,IsAudited,ColumnDesc,IsProduct,islocation," +
            "IsPublishMore,LanguageType,contentshowtype,isrss,getrssarticletime,archivingrules,useArticleType,publicflag,istype," +
            "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic " +
            "FROM tbl_column ORDER BY orderid";

    public List getColumns() throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Column column=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETCOLUMNS);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = load(rs);
                list.add(column);
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

    private static final String SQL_GETCOLUMNS_FORSITEID = "SELECT id,siteid,dirname,orderid,parentid,ename,extname," +
            "createdate,lastupdated,cname,editor,isDefineAttr,XMLTemplate,IsAudited,ColumnDesc,IsProduct,islocation," +
            "IsPublishMore,LanguageType,contentshowtype,isrss,getrssarticletime,archivingrules,useArticleType,publicflag,istype," +
            "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic " +
            "FROM tbl_column where siteid=? ORDER BY orderid";

    public List<Column> getColumnsForSite(int siteid) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        Column column=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETCOLUMNS_FORSITEID);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = load(rs);
                list.add(column);
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

    private static final String SQL_GET_COLUMN = "SELECT Cname,Dirname,ParentID FROM tbl_column WHERE ID = ?";

    public List getColumnNameforChinesePath(int columnID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            while (columnID > 0) {
                pstmt = conn.prepareStatement(SQL_GET_COLUMN);
                pstmt.setInt(1, columnID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    columnID = rs.getInt(3);
                    if (columnID == 0) break;
                    list.add(0, rs.getString(1) + "," + rs.getString(2));
                }
                rs.close();
                pstmt.close();
            }
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

    //获取文章的路径ID
    public String getCidpath(int columnID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String cidpath = "";
        String SQL_GET_COLUMN = "SELECT ParentID FROM tbl_column WHERE ID = ?";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COLUMN);
            while (columnID > 0) {
                cidpath = columnID + cidpath;
                pstmt.setInt(1, columnID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    columnID = rs.getInt(1);
                    if (columnID == 0) break;
                } else {
                    columnID = 0;
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

        return "c" + cidpath;
    }

    //add by Eric 2006-7-18
    private static final String SQL_GETSUBCOLUMNS = "SELECT id,siteid,dirname,orderid,parentid,ename,extname," +
            "createdate,lastupdated,cname,editor,isDefineAttr,XMLTemplate,IsAudited,ColumnDesc,IsProduct,islocation," +
            "IsPublishMore,LanguageType,contentshowtype,isrss,getrssarticletime,archivingrules,useArticleType,publicflag,istype" +
            "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic " +
            "FROM tbl_column WHERE parentid = ? ORDER BY orderid";

    public List getSubColumns(int ID) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Column column=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSUBCOLUMNS);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                column = load(rs);
                list.add(column);
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

    Column load(ResultSet rs) throws SQLException {
        Column column = null;
        try {
            column = new Column();
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
            //column.setXMLTemplate(rs.getString("XMLTemplate"));
            column.setXMLTemplate(DBUtil.getBigString(cpool.getType(), rs, "XMLTemplate"));
            column.setIsAudited(rs.getInt("IsAudited"));
            column.setDesc(rs.getString("ColumnDesc"));
            column.setIsProduct(rs.getInt("IsProduct"));
            column.setIsPosition(rs.getInt("islocation"));
            column.setIsPublishMoreArticleModel(rs.getInt("IsPublishMore"));
            column.setLanguageType(rs.getInt("LanguageType"));
            column.setContentShowType(rs.getInt("contentshowtype"));
            column.setRss(rs.getInt("isrss"));
            column.setGetRssArticleTime(rs.getInt("getrssarticletime"));
            column.setArchivingrules(rs.getInt("archivingrules"));
            column.setUseArticleType(rs.getInt("useArticleType"));
            column.setPublicflag(rs.getInt("publicflag"));
            column.setIsType(rs.getInt("istype"));

            column.setTitlepic(rs.getString("titlepic"));
            column.setVtitlepic(rs.getString("vtitlepic"));
            column.setSourcepic(rs.getString("sourcepic"));
            column.setAuthorpic(rs.getString("authorpic"));
            column.setContentpic(rs.getString("contentpic"));
            column.setSpecialpic(rs.getString("specialpic"));
            column.setProductpic(rs.getString("productpic"));
            column.setProductsmallpic(rs.getString("productsmallpic"));
            column.setTs_pic(rs.getString("ts_pic"));
            column.setS_pic(rs.getString("s_pic"));
            column.setMs_pic(rs.getString("ms_pic"));
            column.setM_pic(rs.getString("m_pic"));
            column.setMl_pic(rs.getString("ml_pic"));
            column.setL_pic(rs.getString("l_pic"));
            column.setTl_pic(rs.getString("tl_pic"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return column;
    }

    public Column getParentColumn(int columnid) throws ColumnException {
        Column column = new Column();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT ParentID FROM tbl_column WHERE ID = ?");
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                columnid = rs.getInt(1);
            } else {
                columnid = 0;
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT * FROM tbl_column WHERE ID = ?");
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = load(rs);
            } else {
                column = null;
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
        return column;
    }

    public Column getFirstColumn(int columnid) throws ColumnException {
        Column column = new Column();
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_column WHERE ID = ?");

            while (columnid > 0) {
                pstmt.setInt(1, columnid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    columnid = rs.getInt("parentid");
                    if (columnid == 0) {
                        break;
                    }
                    column = load(rs);
                } else {
                    columnid = 0;
                    column = null;
                }
            }
            if (rs != null) {
                rs.close();
            }
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
        return column;
    }

    //读取该分类下的文章ID by feixiang 2008-01-08
    public String getArticleIDForType(String columnIds, String ename, String type) {
        String ids = "";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT articleid FROM tbl_type_article WHERE columnid in" + columnIds + " AND valueid IN(" + type + ")");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ids = ids + rs.getInt(1) + ",";
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
        return ids;
    }

    //创建文章分类 by feixiang 2008-01-14
    public void createType(Producttype pro) {
        Connection conn = null;
        PreparedStatement pstmt;
        int typeID;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO tbl_type(id,columnid,parentid,referid,cname,ename) VALUES " +
                    "(?, ?, ?, ?, ?, ?)");
            if (cpool.getType().equals("oracle"))
                typeID = sequnceMgr.getSequenceNum("Type");
            else
                typeID = sequnceMgr.nextID("Type");
            pstmt.setInt(1, typeID);
            pstmt.setInt(2, pro.getColumnID());
            pstmt.setInt(3, pro.getParentid());
            pstmt.setInt(4, pro.getReferid());
            pstmt.setString(5, pro.getCname());
            pstmt.setString(6, pro.getEname());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

    //获取站点栏目下的所有文章一级分类 by feixiang 2008-01-14
    public List getAllTypeForColumn(String sql) {
        List typelist = new ArrayList();
        Producttype pro;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pro = new Producttype();
                pro.setId(rs.getInt("id"));
                pro.setColumnID(rs.getInt("columnid"));
                pro.setParentid(rs.getInt("parentid"));
                pro.setReferid(rs.getInt("referid"));
                pro.setCname(rs.getString("cname"));
                pro.setEname(rs.getString("ename"));
                typelist.add(pro);
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
        return typelist;
    }

    //创建二级文章分类
    public void createSecondType(Producttype pro) {
        Connection conn = null;
        PreparedStatement pstmt;

        int typeID;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("INSERT INTO tbl_type(id,columnid,parentid,referid,cname) VALUES(?,?,?,?,?)");
            if (cpool.getType().equals("oracle"))
                typeID = sequnceMgr.getSequenceNum("Type");
            else
                typeID = sequnceMgr.nextID("Type");
            pstmt.setInt(1, typeID);
            pstmt.setInt(2, pro.getColumnID());
            pstmt.setInt(3, pro.getParentid());
            pstmt.setInt(4, pro.getReferid());
            pstmt.setString(5, pro.getCname());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

    //修改分类名称 by feixiang 2008-01-14
    public void updateTypeCname(int id, String cname, String ename) {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE tbl_type SET cname = ?, ename = ? WHERE id = ?");
            pstmt.setString(1, cname);
            pstmt.setString(2, ename);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

    //删除分类 by feixiang 2008-01-14
    public void deleteTypeValue(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("DELETE FROM tbl_type WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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

    //读取继承的分类 by feixiang 2008-01-14
    public List getInheritanceType(int parentID) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        int referid = 0;
        int columnid = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT dirname FROM tbl_column WHERE id = ?");
            pstmt.setInt(1, parentID);
            rs = pstmt.executeQuery();
            String dirname = null;
            while (rs.next()) {
                dirname = rs.getString(1);
            }
            rs.close();
            pstmt.close();

            if (dirname != null && !dirname.equals("") && !dirname.equals("null")) {
                if (dirname.equals("/")) {
                    parentID = 0;
                }
            }

            pstmt = conn.prepareStatement("SELECT columnid,referid FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid <> 0");
            pstmt.setInt(1, parentID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                referid = rs.getInt("referid");
                columnid = rs.getInt("columnid");
            }
            rs.close();
            pstmt.close();
            //将该栏目下的自定义的分类加入到继承列表
            pstmt = conn.prepareStatement("SELECT * FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid = 0");
            pstmt.setInt(1, parentID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Producttype pro = new Producttype();
                pro.setId(rs.getInt("id"));
                pro.setColumnID(rs.getInt("columnid"));
                pro.setParentid(rs.getInt("parentid"));
                pro.setReferid(rs.getInt("referid"));
                pro.setCname(rs.getString("cname"));
                list.add(pro);
            }
            rs.close();
            pstmt.close();
            while (referid > 0) {

                //查找本栏目的继承的分类
                pstmt = conn.prepareStatement("SELECT columnid,referid FROM tbl_type WHERE id = ?");
                pstmt.setInt(1, referid);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    referid = rs.getInt("referid");
                    columnid = rs.getInt("columnid");
                }
                rs.close();
                pstmt.close();

                //将该栏目下的自定义的分类加入到继承列表
                pstmt = conn.prepareStatement("SELECT * FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid = 0");
                pstmt.setInt(1, columnid);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Producttype pro = new Producttype();
                    pro.setId(rs.getInt("id"));
                    pro.setColumnID(rs.getInt("columnid"));
                    pro.setParentid(rs.getInt("parentid"));
                    pro.setReferid(rs.getInt("referid"));
                    pro.setCname(rs.getString("cname"));
                    list.add(pro);
                }
                rs.close();
                pstmt.close();
            }
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

    //获得分类id  by feixiang 2008-01-14

    public int getTypeID(int columnID, int tid) {
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        boolean flag = false;
        boolean check = false;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT id FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid <> 0");
            pstmt.setInt(1, tid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                check = true;
                id = -1;
            }
            rs.close();
            pstmt.close();
            if (!check) {
                pstmt = conn.prepareStatement("SELECT id FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid <> 0");
                pstmt.setInt(1, columnID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    id = rs.getInt(1);
                    flag = true;
                }
                rs.close();
                pstmt.close();

                if (!flag) {
                    pstmt = conn.prepareStatement("SELECT dirname FROM tbl_column WHERE id = ?");
                    pstmt.setInt(1, columnID);
                    rs = pstmt.executeQuery();
                    String dirname = null;
                    while (rs.next()) {
                        dirname = rs.getString(1);
                    }
                    rs.close();
                    pstmt.close();

                    if (dirname != null && !dirname.equals("") && !dirname.equals("null")) {
                        if (dirname.equals("/")) {
                            columnID = 0;
                        }
                    }
                    pstmt = conn.prepareStatement("SELECT id FROM tbl_type WHERE columnid = ? AND parentid = 0");
                    pstmt.setInt(1, columnID);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        id = rs.getInt(1);
                        flag = true;
                    }
                    rs.close();
                    pstmt.close();
                }
            }
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
        return id;
    }

    //获取二级分类 by feixiang 2008-01-17
    public List getSecondType(int FirstTypeID) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_type WHERE parentid = ? AND referid = 0 ORDER BY createdate DESC");
            pstmt.setInt(1, FirstTypeID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Producttype pt = new Producttype();
                pt.setValueid(rs.getInt("id"));
                pt.setValues(rs.getString("cname"));
                list.add(pt);
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

    //判断文章分类是否存在 用于文章修改 by feixiang 2008-01-17
    public boolean checkArticleType(int articleID, int typeID) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_type_article WHERE articleid = ? AND valueid = ?");
            pstmt.setInt(1, articleID);
            pstmt.setInt(2, typeID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = true;
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
        return flag;
    }

    //获得分类名称 by feixiang 2008-01-24
    public String getTypeNames(String ids) {
        String str = "";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT cname FROM tbl_type WHERE id IN(" + ids + ")");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (str.equals("") || str == null) {
                    str = rs.getString("cname");
                } else {
                    str = str + ";" + rs.getString("cname");
                }
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
        return str;
    }

    //获取某文章的已定义的分类名称 by feixiang 2008-01-24
    public String getTypeNames(int articleID) {
        String str = "";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String ids = "";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT valueid FROM tbl_type_article WHERE articleid = " + articleID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (ids.equals("")) {
                    ids = String.valueOf(rs.getInt(1));
                } else {
                    ids = ids + "," + String.valueOf(rs.getInt(1));
                }
            }
            rs.close();
            pstmt.close();
            if (!ids.equals("")) {
                pstmt = conn.prepareStatement("SELECT cname FROM tbl_type WHERE id IN(" + ids + ")");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (str.equals("") || str == null) {
                        str = rs.getString("cname");
                    } else {
                        str = str + ";" + rs.getString("cname");
                    }
                }
                rs.close();
                pstmt.close();
            }
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
        return str;

    }

    //获取某文章的已定义的分类id by feixiang 2008-01-24
    public String getTypeIDs(int articleID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String ids = "";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT valueid FROM tbl_type_article WHERE articleid = " + articleID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (ids.equals("")) {
                    ids = String.valueOf(rs.getInt(1));
                } else {
                    ids = ids + "," + String.valueOf(rs.getInt(1));
                }
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
        return ids;

    }

    //读取继承的分类的栏目id by feixiang 2008-01-14
    public List getInheritanceTypeColumnIDs(int columnID) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        int referid = 0;
        int columnid = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT dirname FROM tbl_column WHERE id = ?");
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            String dirname = null;
            while (rs.next()) {
                dirname = rs.getString(1);
            }
            rs.close();
            pstmt.close();

            if (dirname != null && !dirname.equals("") && !dirname.equals("null")) {
                if (dirname.equals("/")) {
                    columnID = 0;
                }
            }

            //将自己栏目ID加入list
            list.add(String.valueOf(columnID));

            pstmt = conn.prepareStatement("SELECT columnid,referid FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid <> 0");
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                referid = rs.getInt("referid");
                columnid = rs.getInt("columnid");
                list.add(String.valueOf(columnid));
            }
            rs.close();
            pstmt.close();

            while (referid > 0) {

                //查找本栏目的继承的分类
                pstmt = conn.prepareStatement("SELECT columnid,referid FROM tbl_type WHERE id = ?");
                pstmt.setInt(1, referid);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    referid = rs.getInt("referid");
                    columnid = rs.getInt("columnid");
                    list.add(String.valueOf(columnid));
                }
                rs.close();
                pstmt.close();
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

    //获取某文章的已定义的分类 by feixiang 2008-01-28
    public List getTypes(int articleID) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Producttype pt = null;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT a.valueid,b.cname FROM tbl_type_article a,tbl_type b WHERE a.articleid = " + articleID + " AND b.id=a.valueid");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pt = new Producttype();
                pt.setValueid(rs.getInt(1));
                pt.setCname(rs.getString(2));
                list.add(pt);
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

    //获得分类 by feixiang 2008-01-28
    public List getTypes(String typeids) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Producttype pt = null;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT id,cname FROM tbl_type WHERE id IN(" + typeids + ")");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pt = new Producttype();
                pt.setValueid(rs.getInt(1));
                pt.setCname(rs.getString(2));
                list.add(pt);
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

    public void copyColumn(String cids, int targetColumnId) {
        String targetDirName = "";
        int siteId = 0;
        String editor = "";
        String extname = "";

        IColumnManager columnMgr = ColumnPeer.getInstance();
        try {
            Column column = columnMgr.getColumn(targetColumnId);
            targetDirName = column.getDirName();
            siteId = column.getSiteID();
            editor = column.getEditor();
            extname = column.getExtname();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        String[] cid = cids.trim().split(",");
        Tree colTree = TreeManager.getInstance().getCopyColumnTree(cids);
        Tree subTree = new Tree(colTree.getNodeNum(), "", Integer.parseInt(cid[0]), colTree.getAllNodes(), colTree.getPid());

        node[] treeNodes = subTree.getAllNodes();
        for (int i = 0; i < treeNodes.length; i++) {
            String dir = subTree.getCopyColumnDirName(subTree, treeNodes[i].getId());

            try {
                Column column = columnMgr.getColumn(treeNodes[i].getId());
                String newDirName = targetDirName + dir.substring(1);

                //获得父栏目ID
                String myDirName = newDirName.substring(0, (newDirName.substring(0, newDirName.length() - 1)).lastIndexOf("/") + 1);
                int parentId = 0;
                if (i == 0)
                    parentId = targetColumnId;
                else
                    parentId = getParentIdByDirName(myDirName, siteId);

                column.setDirName(newDirName);
                column.setParentID(parentId);
                column.setSiteID(siteId);
                column.setEditor(editor);
                column.setExtname(extname);
                column.setCName(StringUtil.gb2iso4View(column.getCName()));
                createCopyColumn(column);
            } catch (ColumnException e) {
                e.printStackTrace();
            }
        }
    }

    private int getParentIdByDirName(String dirname, int siteId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int id = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT id FROM tbl_column WHERE dirname = ? AND siteid = ?");
            pstmt.setString(1, dirname);
            pstmt.setInt(2, siteId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
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
        return id;
    }

    private void createCopyColumn(Column column) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATECOLUMN_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATECOLUMN_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATECOLUMN_FOR_MYSQL);
                pstmt.setInt(1, column.getSiteID());
                pstmt.setString(2, column.getDirName());
                pstmt.setInt(3, column.getOrderID());
                pstmt.setInt(4, column.getParentID());
                pstmt.setString(5, column.getCName().trim());
                pstmt.setString(6, column.getEName().trim());
                pstmt.setString(7, column.getExtname());
                pstmt.setTimestamp(8, column.getCreateDate());
                pstmt.setTimestamp(9, column.getLastUpdated());
                pstmt.setString(10, column.getEditor());
                pstmt.setInt(11, column.getDefineAttr());
                if (column.getXMLTemplate() != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 12, column.getXMLTemplate());
                else
                    pstmt.setNull(12, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(13, column.getIsAudited());
                pstmt.setString(14, column.getDesc());
                pstmt.setInt(15, column.getIsProduct());
                pstmt.setInt(16,column.getIsPosition());
                pstmt.setInt(17, column.getIsPublishMoreArticleModel());
                pstmt.setInt(18, column.getLanguageType());
                pstmt.setInt(19, 0);
                pstmt.setInt(20, column.getContentShowType());
                pstmt.setInt(21, column.getPublicflag());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(22, sequnceMgr.getSequenceNum("Column"));
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("oracle")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ColumnException("Database exception: create column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ColumnException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GET_REFERS_COLUMN = "SELECT scid,ssiteid FROM tbl_refers_column WHERE tcid = ?";

    //private static final String SQL_GET_REFERS_ARTICLE = "select distinct scolumnid from tbl_refers_article where columnid = ?";

    private static final String SQL_GET_REFERS_ARTICLE = "SELECT DISTINCT ra.scolumnid,C.siteid FROM tbl_refers_article ra,tbl_column C WHERE ra.scolumnid IN " +
            "(SELECT scolumnid FROM tbl_refers_article WHERE columnid = ? AND ra.refers_column_status = 1 AND ra.siteid = ?) AND ra.scolumnid = C.id";

    //edit by xuzheming 2008.07.28
    public List getRefersColumnIds(int columnId, int siteid) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        List columnList = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_COLUMN);
            pstmt.setInt(1, columnId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Column column = new Column();
                column.setScid(rs.getInt(1));
                column.setSsiteid(rs.getInt(2));
                list.add(column);
                columnList.add(String.valueOf(rs.getInt(1)));
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_REFERS_ARTICLE);
            pstmt.setInt(1, columnId);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Column columns = new Column();
                if (columnList.indexOf(String.valueOf(rs.getInt(1))) == -1) {
                    columns.setScid(rs.getInt(1));
                    columns.setSsiteid(rs.getInt(2));
                    list.add(columns);
                }
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

    private static final String SQL_GET_REFERS_COLUMN_PUBLISH = "SELECT scid,ssiteid FROM tbl_refers_column WHERE tcid = ?";

    private static final String SQL_GET_REFERS_ARTICLE_PUBLISH = "SELECT DISTINCT ra.scolumnid,C.siteid FROM tbl_refers_article ra,tbl_column C WHERE ra.scolumnid IN " +
            "(SELECT scolumnid FROM tbl_refers_article WHERE columnid = ? AND ra.siteid = ?) AND ra.scolumnid = C.id";

    //add by xuzheming 2008.07.28
    public List getRefersPublishiColumnIds(int columnId, int siteid) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        List columnList = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_COLUMN_PUBLISH);
            pstmt.setInt(1, columnId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Column column = new Column();
                column.setScid(rs.getInt(1));
                column.setSsiteid(rs.getInt(2));
                list.add(column);
                columnList.add(String.valueOf(rs.getInt(1)));
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_REFERS_ARTICLE_PUBLISH);
            pstmt.setInt(1, columnId);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Column columns = new Column();
                if (columnList.indexOf(String.valueOf(rs.getInt(1))) == -1) {
                    columns.setScid(rs.getInt(1));
                    columns.setSsiteid(rs.getInt(2));
                    list.add(columns);
                }
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

    private static final String SQL_GET_REFERS_EDITCOLUMN = "SELECT scid,ssiteid FROM tbl_refers_column WHERE tcid = ?";

    private static final String SQL_GET_REFERS_EDITARTICLE = "SELECT DISTINCT ra.scolumnid,C.siteid FROM tbl_refers_article ra,tbl_column C WHERE ra.scolumnid IN " +
            "(SELECT scolumnid FROM tbl_refers_article WHERE columnid = ?) AND ra.scolumnid = C.id";

    //add by xuzheming 2008.07.28
    public List getRefersColumnIds(int columnId) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        List columnList = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_EDITCOLUMN);
            pstmt.setInt(1, columnId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Column column = new Column();
                column.setScid(rs.getInt(1));
                column.setSsiteid(rs.getInt(2));
                list.add(column);
                columnList.add(String.valueOf(rs.getInt(1)));
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_REFERS_EDITARTICLE);
            pstmt.setInt(1, columnId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Column columns = new Column();
                if (columnList.indexOf(String.valueOf(rs.getInt(1))) == -1) {
                    columns.setScid(rs.getInt(1));
                    columns.setSsiteid(rs.getInt(2));
                    list.add(columns);
                }
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

    public List getRefersColumnIds(String columnIds) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt;
        List list = new ArrayList();
        if ((columnIds != null) && (columnIds.indexOf("(") > 0) && (columnIds.indexOf(")") > 0))
            columnIds = columnIds.substring(columnIds.indexOf("(") + 1, columnIds.indexOf(")"));

        String SQL_GET_REFERS_COLUMN = "SELECT scid,ssiteid FROM tbl_refers_column WHERE tcid IN (" + columnIds + ")";
        // System.out.println("SQL_GET_REFERS_COLUMN="+SQL_GET_REFERS_COLUMN);
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_REFERS_COLUMN);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Column column = new Column();
                column.setScid(rs.getInt(1));
                column.setSsiteid(rs.getInt(2));
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

    public List getSourceSiteIdAndCID(List selectColumnIds) throws ColumnException {
        List selectColumnIdsList = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        Column column;

        try {
            for (int i = 0; i < selectColumnIds.size(); i++) {
                column = columnMgr.getColumn(Integer.parseInt((String) selectColumnIds.get(i)));

                Column scolumn = new Column();
                scolumn.setSsiteid(column.getSiteID());
                scolumn.setScid(column.getID());
                selectColumnIdsList.add(scolumn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectColumnIdsList;
    }

    public String getPicsizeNotNullColumnForUp(int columnid,String picattrflag) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        String picsize = null;
        try {
            conn = cpool.getConnection();
            if (picattrflag.equalsIgnoreCase("content"))
                pstmt = conn.prepareStatement("SELECT id,parentid,contentpic FROM tbl_column WHERE id = ?");
            else if (picattrflag.equalsIgnoreCase("mtitle"))
                pstmt = conn.prepareStatement("SELECT id,parentid,titlepic FROM tbl_column WHERE id = ?");
            else if (picattrflag.equalsIgnoreCase("vtitle"))
                pstmt = conn.prepareStatement("SELECT id,parentid,vtitlepic FROM tbl_column WHERE id = ?");
            else if (picattrflag.equalsIgnoreCase("source"))
                pstmt = conn.prepareStatement("SELECT id,parentid,sourcepic FROM tbl_column WHERE id = ?");
            else if (picattrflag.equalsIgnoreCase("author"))
                pstmt = conn.prepareStatement("SELECT id,parentid,authorpic FROM tbl_column WHERE id = ?");
            else if (picattrflag.equalsIgnoreCase("special"))
                pstmt = conn.prepareStatement("SELECT id,parentid,specialpic FROM tbl_column WHERE id = ?");
            else if (picattrflag.equalsIgnoreCase("product"))
                pstmt = conn.prepareStatement("SELECT id,parentid,productpic FROM tbl_column WHERE id = ?");
            else
                pstmt = conn.prepareStatement("SELECT id,parentid,productsmallpic FROM tbl_column WHERE id = ?");

            while(picsize==null && columnid>0) {
                pstmt.setInt(1, columnid);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    if (picattrflag.equalsIgnoreCase("content"))
                        picsize = rs.getString("contentpic");
                    else if (picattrflag.equalsIgnoreCase("mtitle"))
                        picsize = rs.getString("titlepic");
                    else if (picattrflag.equalsIgnoreCase("vtitle"))
                        picsize = rs.getString("vtitlepic");
                    else if (picattrflag.equalsIgnoreCase("source"))
                        picsize = rs.getString("sourcepic");
                    else if (picattrflag.equalsIgnoreCase("author"))
                        picsize = rs.getString("authorpic");
                    else if (picattrflag.equalsIgnoreCase("special"))
                        picsize = rs.getString("specialpic");
                    else if (picattrflag.equalsIgnoreCase("product"))
                        picsize = rs.getString("productpic");
                    else
                        picsize = rs.getString("productsmallpic");
                }
                if (picsize == null) {
                    columnid = rs.getInt("parentid");
                    rs.close();
                }else {
                    rs.close();
                    break;
                }
            }
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


        return picsize;
    }

    //Add by Eric 2008-2-27 for get articles' top types
    public List getArticlesTopTypes(int columnId) throws ColumnException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        List typelist = new ArrayList();

        try {
            columnId = getDefineTypesColumnId(columnId);
            conn = cpool.getConnection();
            typelist = new ArrayList();
            pstmt = conn.prepareStatement("SELECT id, cname FROM tbl_type WHERE columnid = ? AND parentid = 0 AND referid = 0");
            pstmt.setInt(1, columnId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Producttype product = new Producttype();
                product.setId(rs.getInt("id"));
                product.setCname(rs.getString("cname"));
                typelist.add(product);
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
        return typelist;
    }

    private int getDefineTypesColumnId(int columnId) {
        Connection conn = null;
        PreparedStatement pstmt;
        boolean defineType = false;
        int referid = 0;
        List typelist = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT scid FROM tbl_refers_column WHERE scid = ? OR tcid = ?");
            pstmt.setInt(1, columnId);
            pstmt.setInt(2, columnId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                defineType = true;
                columnId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            if (defineType) {
                pstmt = conn.prepareStatement("SELECT referid FROM tbl_type WHERE columnid = ?");
                pstmt.setInt(1, columnId);
                rs = pstmt.executeQuery();
                if (rs.next())
                    referid = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (referid != 0) {
                    pstmt = conn.prepareStatement("SELECT id,columnid,referid FROM tbl_type");
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        Producttype product = new Producttype();
                        product.setId(rs.getInt("id"));
                        product.setColumnID(rs.getInt("columnid"));
                        product.setReferid(rs.getInt("referid"));
                        typelist.add(product);
                    }
                    rs.close();
                    pstmt.close();

                    while (referid != 0) {
                        for (int i = 0; i < typelist.size(); i++) {
                            Producttype product = (Producttype) typelist.get(i);
                            if (product.getId() == referid) {
                                referid = product.getReferid();
                                columnId = product.getColumnID();
                                break;
                            }
                        }
                    }
                }
            }
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
        return columnId;
    }

    public String getArticlesType(int columnId, int addlink, String selectTStr, int articleId, int siteID) {
        String dirname = "";
        String extname = "";
        String returnValue = "";

        Connection conn = null;
        PreparedStatement pstmt;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT columnid FROM tbl_type WHERE parentid=0 AND flag=1 AND columnid IN (SELECT id FROM tbl_column WHERE siteid=?)");
            pstmt.setInt(1, siteID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Column column = getColumn(rs.getInt(1));
                dirname = column.getDirName();
                extname = column.getExtname();

                pstmt = conn.prepareStatement("SELECT t.id,cname,t.ename,t.parentid FROM tbl_type t, tbl_type_article ta" +
                        " WHERE t.id = ta.valueid AND ta.articleid=? AND t.referid=0 AND t.parentid IN (" + selectTStr + ")");
                pstmt.setInt(1, articleId);
                rs = pstmt.executeQuery();

                if (addlink == 1) {
                    while (rs.next()) {
                        returnValue = returnValue + "<img height=\"7\" alt=\"\" width=\"4\" align=\"absMiddle\" src=\"" +
                                "/images/arrow-red-1.gif\" /> <a href=\"" + dirname + rs.getString("ename") + "." + extname +
                                "\" class=\"deep-blue-text\" target=_blank>" + StringUtil.gb2iso4View(rs.getString("cname")) + "</a>&nbsp;&nbsp;";
                    }
                } else {
                    returnValue = returnValue + "<img height=\"7\" alt=\"\" width=\"4\" align=\"absMiddle\" src=\"" +
                            "/images/arrow-red-1.gif\" /> " + StringUtil.gb2iso4View(rs.getString("cname")) + "&nbsp;&nbsp;";
                }
                rs.close();
                pstmt.close();
            }
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
        return returnValue;
    }

    public List getReferArticleTypesColumn(List selectTypesList) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();

            for (int i = 0; i < selectTypesList.size(); i++) {
                int id = Integer.parseInt((String) selectTypesList.get(i));
                pstmt = conn.prepareStatement("SELECT id FROM tbl_type WHERE columnid = ? AND parentid = 0");
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();

                while (rs.next())
                    list.add(String.valueOf(rs.getInt(1)));
                rs.close();
                pstmt.close();
            }
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

    public List getReferTypesColumnIds(int columnid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT DISTINCT(columnid) FROM tbl_type t WHERE id IN (SELECT referid FROM " +
                    "tbl_type WHERE columnid = ?)");
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();

            while (rs.next())
                list.add(String.valueOf(rs.getInt(1)));
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

    //for xuzheming at 2008.07.27   获取引用类型  如果usearticletype为0返回false,否则返回true

    private static String SQL_GETUSEARTICLETYPEVALUE = "SELECT usearticletype FROM tbl_refers_article WHERE columnid = ? AND articleid = ?";

    public boolean getUseArticleTypeValue(int cid, int scid, int aid, int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean checkvalue = false;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETUSEARTICLETYPEVALUE);
            pstmt.setInt(1, cid);
            pstmt.setInt(2, aid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) == 1)
                    checkvalue = true;
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
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
        return checkvalue;
    }

    String getHttpColumnUrl = "SELECT sitename FROM tbl_siteinfo WHERE siteid = (SELECT siteid FROM tbl_column WHERE id = ?)";

    public String getHTTPColumnURL(int columnID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String sitename = "";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(getHttpColumnUrl);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next())
                sitename = rs.getString(1);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
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
        return sitename;
    }

    private String SQL_GETSITEID = "SELECT siteid FROM tbl_column WHERE id = ?";

    public int getSiteId(int columnid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int siteid = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITEID);
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();
            if (rs.next())
                siteid = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
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
        return siteid;
    }

    //add by xuzheming at 2008.08.07

    private static String SQL_GETSITENAME = "SELECT siteid,sitename FROM tbl_siteinfo WHERE siteid = (SELECT siteid FROM tbl_column WHERE id = (SELECT columnid FROM tbl_article WHERE id = ?))";

    public SiteInfo getSiteName(int articleid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        SiteInfo siteinfo = new SiteInfo();
        String siteName = "";
        int siteid = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITENAME);
            pstmt.setInt(1, articleid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                siteid = rs.getInt(1);
                siteName = rs.getString(2);
            }
            rs.close();
            pstmt.close();
            if (siteName != null && siteName.length() > 0) {
                siteName = StringUtil.replace(siteName, ".", "_");
            }
            siteinfo.setSiteid(siteid);
            siteinfo.setDomainName(siteName);
        } catch (SQLException e) {
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
        return siteinfo;
    }

    //add by xuzheming at 2008.08.11
    private static String SQL_CHECKUSEARTICLETYPE = "SELECT id FROM tbl_refers_article WHERE articleid = ? AND columnid = ?";

    public boolean checkUseArticleType(int articleid, int columnid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean checkusearticletype = false;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_CHECKUSEARTICLETYPE);
            pstmt.setInt(1, articleid);
            pstmt.setInt(2, columnid);
            rs = pstmt.executeQuery();
            if (rs.next())
                checkusearticletype = true;
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
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
        return checkusearticletype;
    }

    // 2009 wangjian
    public Column getRootparentColumn(int siteid) {
        Column column = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = cpool.getConnection();
            String sql = "SELECT *FROM tbl_column WHERE siteid=" + siteid + "  AND  parentid=0";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = new Column();
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
                //column.setXMLTemplate(rs.getString("XMLTemplate"));
                column.setXMLTemplate(DBUtil.getBigString(cpool.getType(), rs, "XMLTemplate"));
                column.setIsAudited(rs.getInt("IsAudited"));
                column.setDesc(rs.getString("ColumnDesc"));
                column.setIsProduct(rs.getInt("IsProduct"));
                column.setIsPosition(rs.getInt("islocation"));
                column.setIsPublishMoreArticleModel(rs.getInt("IsPublishMore"));
                column.setLanguageType(rs.getInt("LanguageType"));
                column.setContentShowType(rs.getInt("contentshowtype"));
                column.setRss(rs.getInt("isrss"));
                column.setGetRssArticleTime(rs.getInt("getrssarticletime"));
                column.setArchivingrules(rs.getInt("archivingrules"));
                column.setUseArticleType(rs.getInt("useArticleType"));
                column.setIsType(rs.getInt("istype"));
                column.setUserflag(rs.getInt("userflag"));
                column.setUserlevel(rs.getInt("userlevel"));
                column.setPublicflag(rs.getInt("publicflag"));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (con != null) {
                cpool.freeConnection(con);
            }
        }
        return column;
    }

    public List getAuthorized(int siteid, int columnid) {
        List typelist = new ArrayList();

        Connection conn = null;
        try {
            conn = this.cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM column_authorized_to_user WHERE siteid = ? AND columnid = ?");
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Authorized aut = new Authorized();
                aut.setId(rs.getInt("id"));
                aut.setSiteid(rs.getInt("siteid"));
                aut.setColumnid(rs.getInt("columnid"));
                aut.setTargetid(rs.getString("targetid"));
                aut.setType(rs.getInt("type"));
                aut.setCreatedate(rs.getTimestamp("createdate"));
                typelist.add(aut);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    this.cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return typelist;
    }

    public List getAuthorizeds(int siteid, int articleid) {
        List typelist = new ArrayList();

        Connection conn = null;
        try {
            conn = this.cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM artilce_authorized_to_user WHERE siteid = ? AND articleid = ?");
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, articleid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Authorized aut = new Authorized();
                aut.setId(rs.getInt("id"));
                aut.setSiteid(rs.getInt("siteid"));
                aut.setArticleid(rs.getInt("articleid"));
                aut.setTargetid(rs.getString("targetid"));
                aut.setType(rs.getInt("type"));
                aut.setCreatedate(rs.getTimestamp("createdate"));
                typelist.add(aut);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    this.cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return typelist;
    }

}