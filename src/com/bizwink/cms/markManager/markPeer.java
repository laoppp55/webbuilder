package com.bizwink.cms.markManager;

import java.util.*;
import java.sql.*;
import java.util.regex.*;

import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.processTag.*;
import com.bizwink.cms.xml.XMLProperties;

public class markPeer implements IMarkManager {
    PoolServer cpool;

    public markPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IMarkManager getInstance() {
        return CmsServer.getInstance().getFactory().getMarkManager();
    }

    private static final String SQL_CREATE_MARK_FOR_ORACLE = "INSERT INTO TBL_Mark (ColumnID,SiteID,Content,Marktype,Chinesename,Notes,LockFlag,PubFlag," +
            "InnerhtmlFlag,FormatFilenum,RelatedColumnID,CreateDate,UpdateDate,PublishTime,ID) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_MARK_FOR_MSSQL = "INSERT INTO TBL_Mark (ColumnID,SiteID,Content,Marktype,Chinesename,Notes,LockFlag,PubFlag," +
            "InnerhtmlFlag,FormatFilenum,RelatedColumnID,CreateDate,UpdateDate,PublishTime) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);select SCOPE_IDENTITY();";

    private static final String SQL_CREATE_MARK_FOR_MYSQL = "INSERT INTO TBL_Mark (ColumnID,SiteID,Content,Marktype,Chinesename,Notes,LockFlag,PubFlag," +
            "InnerhtmlFlag,FormatFilenum,RelatedColumnID,CreateDate,UpdateDate,PublishTime) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_MARKCOLUMN_FOR_ORACLE = "INSERT INTO tbl_markcolumn (MID,CID,SiteID,ID) VALUES (?, ?, ?, ?)";

    private static final String SQL_CREATE_MARKCOLUMN_FOR_MSSQL = "INSERT INTO tbl_markcolumn (MID,CID,SiteID) VALUES (?, ?, ?)";

    private static final String SQL_CREATE_MARKCOLUMN_FOR_MYSQL = "INSERT INTO tbl_markcolumn (MID,CID,SiteID) VALUES (?, ?, ?)";

    public int Create(mark mark) throws markException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Tree colTree = TreeManager.getInstance().getSiteTree(mark.getSiteID());
        int markID = 1;

        try {
            try {
                String content = mark.getContent();
                if (content != null) {
                    content = StringUtil.gb2isoindb(content);
                }

                String cname = mark.getChineseName();
                if (cname != null) cname = StringUtil.gb2isoindb(cname);
                String notes = mark.getNotes();
                if (notes != null) notes = StringUtil.gb2isoindb(notes);
                Timestamp now = new Timestamp(System.currentTimeMillis());

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_MARK_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_MARK_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_MARK_FOR_MYSQL);

                pstmt.setInt(1, mark.getColumnID());
                pstmt.setInt(2, mark.getSiteID());
                if (content != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 3, content);
                else
                    pstmt.setNull(3, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(4, mark.getMarkType());
                pstmt.setString(5, cname);
                pstmt.setString(6, notes);
                pstmt.setInt(7, 0);
                pstmt.setInt(8, 0);
                pstmt.setInt(9, mark.getInnerHTMLFlag());
                pstmt.setInt(10, mark.getFormatFileNum());
                pstmt.setString(11, mark.getRelatedColumnID());
                pstmt.setTimestamp(12, now);
                pstmt.setTimestamp(13, now);
                pstmt.setTimestamp(14, now);
                if (cpool.getType().equals("oracle")) {
                    markID = sequnceMgr.getSequenceNum("Mark");
                    pstmt.setInt(15, markID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    ResultSet rs = pstmt.executeQuery();
                    if(rs.next()){
                        markID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();

                    pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) markID=rs.getInt(1);
                    rs.close();
                    pstmt.close();
                }

                if (content!=null && content!="") {
                    content = StringUtil.gb2iso4View(content);
                    content = StringUtil.replace(content, "[", "<");
                    content = StringUtil.replace(content, "]", ">");
                    content = StringUtil.replace(content, "{^", "[");
                    content = StringUtil.replace(content, "^}", "]");
                    content = StringUtil.replace(content,"<!<CDATA<","<![CDATA[");
                    content = StringUtil.replace(content,">>>","]]>");

                    System.out.println("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);

                    XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
                    String tagName = properties.getName();
                    String columnIds = properties.getProperty(tagName.concat(".COLUMNIDS"));
                    if (columnIds!=null && columnIds!="") {
                        columnIds = columnIds.substring(1,columnIds.length()-1);
                        String[] colids = columnIds.split(",");

                        //写入MARK与栏目之间的关系
                        if (colids!=null) {
                            for(int ii=0; ii<colids.length; ii++) {
                                int posi = colids[ii].indexOf("-");
                                int[] subColumnIDs = null;
                                if (posi>-1) {
                                    String subclsFlag = colids[ii].substring(posi+1).trim();
                                    int topcolid = Integer.parseInt(colids[ii].substring(0,posi));
                                    if (subclsFlag.equalsIgnoreCase("getAllSubArticle")) {
                                        subColumnIDs = colTree.getSubTreeColumnIDList(colTree.getAllNodes(),topcolid);
                                    }
                                } else {
                                    subColumnIDs = new int[2];     //定义两个元素的数组，第一个元素存放子元素的个数，第二个元素以后存放实际获取的元素列表
                                    subColumnIDs[0] = 1;
                                    subColumnIDs[1] = Integer.parseInt(colids[ii]);
                                }
                                if (cpool.getType().equalsIgnoreCase("oracle")) {
                                    pstmt = conn.prepareStatement(SQL_CREATE_MARKCOLUMN_FOR_ORACLE);
                                    for(int jj=1; jj<=subColumnIDs[0]; jj++) {
                                        pstmt.setInt(1, markID);
                                        pstmt.setInt(2, subColumnIDs[jj]);
                                        pstmt.setInt(3, mark.getSiteID());
                                        pstmt.setInt(4, sequnceMgr.getSequenceNum("SelfDefine"));
                                        pstmt.executeUpdate();
                                    }
                                    pstmt.close();
                                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                                    pstmt = conn.prepareStatement(SQL_CREATE_MARKCOLUMN_FOR_MSSQL);
                                    for(int jj=1; jj<=subColumnIDs[0]; jj++) {
                                        pstmt.setInt(1, markID);
                                        pstmt.setInt(2, subColumnIDs[jj]);
                                        pstmt.setInt(3, mark.getSiteID());
                                        pstmt.executeUpdate();
                                    }
                                    pstmt.close();
                                } else {
                                    pstmt = conn.prepareStatement(SQL_CREATE_MARKCOLUMN_FOR_MYSQL);
                                    for(int jj=1; jj<=subColumnIDs[0]; jj++) {
                                        pstmt.setInt(1, markID);
                                        pstmt.setInt(2, subColumnIDs[jj]);
                                        pstmt.setInt(3, mark.getSiteID());
                                        pstmt.executeUpdate();
                                    }
                                    pstmt.close();
                                }
                            }
                        }
                    }
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new markException("Database exception: create mark failed.");
            } finally {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new markException("Database exception: can't rollback?");
        }
        return markID;
    }

    private static final String SQL_UPDATE_MARK =
            "UPDATE TBL_Mark SET Content = ?,Chinesename = ?,Notes = ?,LockEditor = ?,LockFlag = ?," +
                    "InnerHtmlFlag = ?,FormatFileNum = ?,UpdateDate = ?,RelatedColumnID = ? WHERE ID = ?";

    public void Update(mark mark) throws markException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                String content = mark.getContent();
                if (content != null) {
                    content = StringUtil.gb2isoindb(content);
                    //content = StringUtil.replace(content,"[%%markid%%]",String.valueOf(mark.getID()));
                }
                String cname = mark.getChineseName();
                if (cname != null) cname = StringUtil.gb2isoindb(cname);
                String notes = mark.getNotes();
                if (notes != null) notes = StringUtil.gb2isoindb(notes);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_MARK);
                if (content != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 1, content);
                else
                    pstmt.setNull(1, java.sql.Types.LONGVARCHAR);
                //pstmt.setString(1, content);
                pstmt.setString(2, cname);
                pstmt.setString(3, notes);
                pstmt.setString(4, "");
                pstmt.setInt(5, 0);
                pstmt.setInt(6, mark.getInnerHTMLFlag());
                pstmt.setInt(7, mark.getFormatFileNum());
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(9, mark.getRelatedColumnID());
                pstmt.setInt(10, mark.getID());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new markException("Database exception: update mark failed.");
            }
            finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new markException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GET_MARK = "SELECT * FROM TBL_Mark WHERE ID = ?";

    public mark getAMark(int markID) throws markException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        mark mark = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_MARK);
            pstmt.setInt(1, markID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                mark = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
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
        return mark;
    }

    private static final String SQL_GET_ARTICLENUM_FOR_ARTICLELIST = "select articlenum from tbl_mark where id = ?";

    public int getArticlenumForArticleListMark(int markID) throws markException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int num =0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLENUM_FOR_ARTICLELIST);
            pstmt.setInt(1, markID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                num = rs.getInt("articlenum");
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
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
        return num;
    }

    private static final String SQL_UPDATE_ARTICLENUM_FOR_ARTICLELIST = "update tbl_mark set articlenum=? where id = ?";

    public int updateArticlenumForArticleListMark(int markID,int artnum) throws markException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode =0;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLENUM_FOR_ARTICLELIST);
            pstmt.setInt(1,artnum);
            pstmt.setInt(2, markID);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Throwable t) {
            t.printStackTrace();
            errcode = -1;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            }
            catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }
        return errcode;
    }

    public List listAllMarks(int siteid, int columnid) throws markException {
        if (columnid <= 0) return this.listAllMarks(siteid);
        List list = new ArrayList();
        String sqlstr = "select * from tbl_mark where siteid=? and columnid=? and marktype<10 order by marktype";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load(rs));
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

    public List listAllMarks(int siteid) throws markException {
        List list = new ArrayList();
        String sqlstr = "select * from tbl_mark where siteid=? and marktype<10 order by marktype";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load(rs));
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

    public List getMarksByType(int siteid,int marktype) throws markException {
        List list = new ArrayList();
        String sqlstr = "select * from tbl_mark where siteid=? and marktype=?";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, marktype);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load(rs));
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

    private static final String SQL_GET_MARK_CONTENT = "SELECT Content FROM TBL_Mark WHERE ID = ?";

    public String getAMarkContent(int markID) throws markException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String content = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_MARK_CONTENT);
            pstmt.setInt(1, markID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                content = rs.getString(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
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
        return content;
    }

    public String getRelatedColumnIDs(int siteid,Tree colTree,int columnID, String content) throws markException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        String columnIDs = "";
        if (content == null || content.trim().length() == 0) return "()";
        // ITagManager tagManager = TagManager.getInstance();

        try {
            String markIDs = "";
            Pattern p = Pattern.compile("\\[TAG\\]\\[MARKID\\][0-9|_]*\\[/MARKID\\]\\[/TAG\\]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(content);
            while (m.find()) {
                String tag = content.substring(m.start(), m.end());
                markIDs += tag.substring(tag.indexOf("[MARKID]") + 8, tag.indexOf("_")) + ",";
            }

            if (markIDs.length() > 0) {
                markIDs = markIDs.substring(0, markIDs.length() - 1);
                String SQL_GET_MARK_RELATED_COLUMNIDS = "SELECT MarkType,RelatedColumnID FROM TBL_Mark WHERE ID IN (" + markIDs + ")";

                try {
                    conn = cpool.getConnection();
                    pstmt = conn.prepareStatement(SQL_GET_MARK_RELATED_COLUMNIDS);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int markType = rs.getInt(1);
                        if (markType == 1 || markType == 3 || markType == 5) {
                            String relatedCIDs = rs.getString(2);
                            if (relatedCIDs != null && relatedCIDs.length() > 2) {
                                relatedCIDs = relatedCIDs.substring(1, relatedCIDs.length() - 1);
                                if (relatedCIDs.equals("0")) relatedCIDs = String.valueOf(columnID);
                                if (relatedCIDs.indexOf("-getAllSubArticle") > 0)   //?滻 -getAllSubArticle
                                {
                                    relatedCIDs = getSubTreeColumnIDs(siteid,colTree,relatedCIDs);
                                }
                                columnIDs += relatedCIDs + ",";
                            }
                        }
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
            }

            if (columnIDs.length() > 0) {
                columnIDs = columnIDs.substring(0, columnIDs.length() - 1);
                String[] cid = columnIDs.split(",");
                columnIDs = "";
                for (int i = 0; i < cid.length; i++) {
                    boolean same = false;
                    for (int j = 0; j < i; j++) {
                        if (cid[i].equals(cid[j])) {
                            same = true;
                            break;
                        }
                    }
                    if (!same) columnIDs += cid[i] + ",";
                }
                columnIDs = columnIDs.substring(0, columnIDs.length() - 1);
            }
            columnIDs = "(" + columnIDs + ")";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnIDs;
    }

    private String getSubTreeColumnIDs(int siteid,Tree colTree,String columnIDs) {
        //Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
        node[] treenodes = colTree.getAllNodes();

        String arrID[] = columnIDs.split(",");
        columnIDs = "";
        for (int k = 0; k < arrID.length; k++) {
            int p;
            if ((p = arrID[k].indexOf("-getAllSubArticle")) > -1) {
                int columnID = Integer.parseInt(arrID[k].substring(0, p));
                int[] cid = new int[treenodes.length + 1];
                int[] pid = new int[treenodes.length];
                int nodeNum = 1;
                int i;
                int j = 1;
                pid[1] = columnID;

                do {
                    columnID = pid[nodeNum];
                    cid[j] = columnID;
                    j = j + 1;
                    nodeNum = nodeNum - 1;
                    for (i = 0; i < treenodes.length; i++) {
                        if (treenodes[i]!=null) {
                            if (treenodes[i].getLinkPointer() == columnID) {
                                nodeNum = nodeNum + 1;
                                pid[nodeNum] = treenodes[i].getId();
                            }
                        }
                    }
                } while (nodeNum >= 1);
                cid[0] = j - 1;

                for (int m = 0; m < cid[0]; m++) {
                    columnIDs = columnIDs + "," + cid[m + 1];
                }
            } else {
                columnIDs = columnIDs + "," + arrID[k];
            }
        }
        return columnIDs.substring(1);
    }


    private static final String SQL_GET_MARKID = "SELECT ID FROM TBL_Mark WHERE templateID = ? AND orderNum = ?";

    public int getMarkID(int templateID, int orderNum) throws markException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int markID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_MARKID);
            pstmt.setInt(1, templateID);
            pstmt.setInt(2, orderNum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                markID = rs.getInt(1);
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
        return markID;
    }

    public String getMarkCode(String markChineseName) throws markException{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String markcode = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select markcode from tbl_markdict where chinesename=?");
            pstmt.setString(1,markChineseName);
            rs = pstmt.executeQuery();
            if (rs.next()) markcode = rs.getString("markcode");
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

        return markcode;
    }

    mark load(ResultSet rs) throws SQLException {
        mark mark = new mark();
        try {
            mark.setID(rs.getInt("ID"));
            mark.setColumnID(rs.getInt("ColumnID"));
            mark.setSiteID(rs.getInt("SiteID"));
            mark.setContent(DBUtil.getBigString(cpool.getType(), rs, "Content"));
            mark.setMarkType(rs.getInt("MarkType"));
            mark.setNotes(rs.getString("Notes"));
            mark.setLockEditor(rs.getString("LockEditor"));
            mark.setLockFlag(rs.getInt("LockFlag"));
            mark.setPubFlag(rs.getInt("PubFlag"));
            mark.setInnerHTMLFlag(rs.getInt("InnerHTMLFlag"));
            mark.setFormatFileNum(rs.getInt("FormatFileNum"));
            mark.setCreateDate(rs.getTimestamp("CreateDate"));
            mark.setUpdateDate(rs.getTimestamp("UpdateDate"));
            mark.setPublishTime(rs.getTimestamp("PublishTime"));
            mark.setChinesename(StringUtil.gb2iso4View(rs.getString("ChineseName")));
            mark.setRelatedColumnID(rs.getString("RelatedColumnID"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return mark;
    }
    //by wangjian 091222
    public List getArticleListMark(int siteid,int columnid)
    {
        List list = new ArrayList();
        String sqlstr = "select * from tbl_mark where siteid=? and columnid=? and( marktype=1 or marktype=5 or marktype=8)";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2,columnid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(load(rs));
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
    public List getArticleListUpdate(int id)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="update tbl_article_articlelist set markid=?,fontcolor=?,fontziti=?,istop=? where id="+id;
            pstmt=con.prepareStatement(sql);


            pstmt.close();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){

                }
            }
        }
        return list;
    }
    public List getArticleListQuery(int articleid)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select * from tbl_article_articlelist where articleid="+articleid;
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                ArticleMark am=new ArticleMark();
                am.setId(res.getInt("id"));
                am.setArticleid(res.getInt("articleid"));
                am.setMarkid(res.getInt("markid"));
                am.setFontcolor(res.getString("fontcolor"));
                am.setFontziti(res.getString("fontziti"));
                am.setIstop(res.getString("istop"));
                list.add(am);
            }
            res.close();
            pstmt.close();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){

                }
            }
        }
        return list;
    }
}
