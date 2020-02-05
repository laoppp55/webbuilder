package com.bizwink.cms.orderArticleListManager;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.news.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.util.StringUtil;

public class orderArticleListPeer implements IOrderArticleListManager {
    PoolServer cpool;

    public orderArticleListPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IOrderArticleListManager getInstance() {
        return CmsServer.getInstance().getFactory().getOrderArticleListManager();
    }

    public List getArticles(int columnID, int startIndex, int numResults, int flag, String editor, String selectColumns,int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Article article=null;

        String sortstr = "";
        if (flag == 0)
            sortstr = " order by publishtime desc";
        else if (flag == 1)
            sortstr = " order by maintitle desc";
        else if (flag == 2)
            sortstr = " order by doclevel desc";
        else if (flag == 3)
            sortstr = " order by vicedoclevel desc";

        try {
            conn = cpool.getConnection();
            String ids = "";
            String strSQL = "";

            String ReferArticleSQL = "";
            ReferArticleSQL = "select articleid from tbl_refers_article where columnid = " + columnID+" and siteid="+siteid;
            pstmt=conn.prepareStatement(ReferArticleSQL);
            rs = pstmt.executeQuery();
            String artid="";
            while (rs.next())
                artid += rs.getInt(1) + ",";
            rs.close();
            pstmt.close();
            if (artid.length() > 0)
                artid = artid.substring(0, artid.length() - 1);

            IArticleManager articleMgr = ArticlePeer.getInstance();

            if(artid.equals(""))
            {
                if (editor.length() > 0)
                    strSQL = "SELECT id FROM tbl_article WHERE (columnID = ? and siteid="+siteid+" and status=1  and editor='" + editor + "')" + sortstr;
                else
                    strSQL = "SELECT id FROM tbl_article WHERE columnID = ? and siteid="+siteid+" and status=1" + sortstr;
            }
            else{
                artid = articleMgr.formatOracleClause(artid,0);
                if (editor.length() > 0)
                    strSQL = "SELECT id FROM tbl_article WHERE (columnID = ? and status=1  and siteid="+siteid+" and editor='" + editor + "') or (id in ("+artid+"))" + sortstr;
                else
                    strSQL = "SELECT id FROM tbl_article WHERE (columnID = ? and siteid="+siteid+" and status=1) or (id in ("+artid+"))" + sortstr;
            }

            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next())
                    ids += rs.getInt(1) + ",";
                else
                    break;
            }
            rs.close();
            pstmt.close();

            if (ids.length() > 0)
                ids = ids.substring(0, ids.length() - 1);
            else
                ids = "0";

            strSQL = "SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                    "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,filename,notearticleid FROM tbl_article " +
                    "where  siteid="+siteid+" and id in (" + ids + ")" + sortstr;

            pstmt = conn.prepareStatement(strSQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadArticleList(rs);
                list.add(article);
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
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public List getArticlesByPage(int columnID, int startIndex, int numResults, int flag, String editor, String selectColumns,int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Article article=null;

        String sortstr = "";
        if (flag == 0)
            sortstr = " order by publishtime desc";
        else if (flag == 1)
            sortstr = " order by maintitle desc";
        else if (flag == 2)
            sortstr = " order by doclevel desc";
        else if (flag == 3)
            sortstr = " order by vicedoclevel desc";
        else
            sortstr = " order by id desc";

        try {
            conn = cpool.getConnection();
            String ids = "";
            String strSQL = "";

            String ReferArticleSQL = "select articleid from tbl_refers_article where columnid = " + columnID+" and siteid="+siteid;
            pstmt=conn.prepareStatement(ReferArticleSQL);
            rs = pstmt.executeQuery();
            String artid="";
            while (rs.next())
                artid += rs.getInt(1) + ",";
            rs.close();
            pstmt.close();
            if (artid.length() > 0) artid = artid.substring(0, artid.length() - 1);

            int mssflag = 0;
            strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt=conn.prepareStatement(strSQL);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();

            String sites_condition = "";
            if (mssflag ==1) {
                sites_condition = "(siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or siteid=" + siteid + ")";
            }  else {
                sites_condition = "siteid=" + siteid;
            }

            IArticleManager articleMgr = ArticlePeer.getInstance();
            if(artid.equals(""))
            {
                if (editor.length() > 0) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE (columnID = ? and "+ sites_condition +" and status=1  and editor='" + editor + "')" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?  and "+ sites_condition +" and status=1  and editor='" + editor + "' " +sortstr;
                        //select * from (select TOP 10 * FROM (SELECT TOP 300 * from tbl_article ORDER BY createdate desc ) as aSysTable   ORDER BY createdate asc) as bSysTable ORDER BY createdate desc
                    else {
                        strSQL = "select * from tbl_article where id>=(select id from tbl_article where columnid=? and " + sites_condition + " and status=1 and editor='" + editor + "' "+sortstr + " limit ?,1) limit ?";
                    }
                }
                else  {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE columnID = ? and " + sites_condition + " and status=1" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?   and "+ sites_condition +" and status=1 " + sortstr;
                        //select * from (select TOP 10 * FROM (SELECT TOP 300 * from tbl_article ORDER BY createdate desc ) as aSysTable   ORDER BY createdate asc) as bSysTable ORDER BY createdate desc
                    else {
                        strSQL = "select * from tbl_article where id>=(select id from tbl_article where columnid=? and " + sites_condition + " and status=1 " + sortstr + " limit ?,1) limit ?";
                    }
                }
            }
            else{
                artid = articleMgr.formatOracleClause(artid,0);
                if (editor.length() > 0) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                " doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE (columnID = ? and status=1  and " + sites_condition + " and editor='" + editor + "') or (id in ("+artid+"))" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and (columnid=? or id in(" +artid + ")) and "+ sites_condition +" and status=1  and editor='" + editor + "' " + sortstr;
                        //select * from (select TOP 10 * FROM (SELECT TOP 300 * from tbl_article ORDER BY createdate desc ) as aSysTable   ORDER BY createdate asc) as bSysTable ORDER BY createdate desc
                    else {
                        strSQL = "select * from tbl_article where id>=(select id from tbl_article where (columnid=? or id in (" + artid + ")) and " + sites_condition + " and status=1 and editor='" + editor + "' "+sortstr + " limit ?,1) limit ?";
                    }
                } else {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE (columnID = ? and " + sites_condition + " and status=1) or (id in ("+artid+"))" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id)) and (columnid=? or id in ("+artid+")) and "+ sites_condition +" and status=1 " + sortstr;
                        //select * from (select TOP 10 * FROM (SELECT TOP 300 * from tbl_article ORDER BY createdate desc ) as aSysTable   ORDER BY createdate asc) as bSysTable ORDER BY createdate desc
                    else {
                        strSQL = "select * from tbl_article where id>=(select id from tbl_article where (columnid=? or id in (" + artid + ")) and " + sites_condition + " and status=1 " + sortstr + " limit ?,1) limit ?";
                    }
                }
            }

            pstmt = conn.prepareStatement(strSQL);
            if (cpool.getType().equalsIgnoreCase("oracle")) {
                pstmt.setInt(1, columnID);
                pstmt.setInt(2,startIndex + numResults);
                pstmt.setInt(3,startIndex);
            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                pstmt.setInt(1,numResults);
                pstmt.setInt(2,startIndex);
                pstmt.setInt(3,columnID);
            } else {
                pstmt.setInt(1,columnID);
                pstmt.setInt(2,startIndex);
                pstmt.setInt(3,numResults);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadArticleList(rs);
                list.add(article);
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
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public List getArticlesByPage1(int columnID, int startIndex, int numResults, int flag, String editor, String selectColumns,int siteid,int ascdesc) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new ArrayList();
        Article article=null;

        String sortstr = "";
        if (flag == 0)
            if (ascdesc == 0)
                sortstr = " order by id desc";
            else
                sortstr = " order by id asc";
        else if (flag == 1)
            if (ascdesc == 0)
                sortstr = " order by maintitle desc";
            else
                sortstr = " order by maintitle asc";
        else if (flag == 2)
            if (ascdesc == 0)
                sortstr = " order by doclevel desc";
            else
                sortstr = " order by doclevel asc";
        else if (flag == 3)
            if (ascdesc == 0)
                sortstr = " order by vicedoclevel desc";
            else
                sortstr = " order by vicedoclevel asc";
        else if (flag == 4){
            if (ascdesc == 0)
                sortstr = " order by publishtime desc";
            else
                sortstr = " order by publishtime asc";
        }  if (flag == 5){
            if (ascdesc == 0)
                sortstr = " order by lastupdated desc";
            else
                sortstr = " order by lastupdated asc";
        }

        try {
            conn = cpool.getConnection();
            String ids = "";
            String strSQL = "";

            String ReferArticleSQL = "select articleid from tbl_refers_article where columnid = " + columnID+" and tsiteid="+siteid;
            pstmt=conn.prepareStatement(ReferArticleSQL);
            rs = pstmt.executeQuery();
            String artid="";
            while (rs.next())
                artid += rs.getInt(1) + ",";
            rs.close();
            pstmt.close();
            if (artid.length() > 0) artid = artid.substring(0, artid.length() - 1);

            //获取是否是共享站点的标志位信息
            int mssflag = 0;
            strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt=conn.prepareStatement(strSQL);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();

            //设置检索条件
            String sites_condition = "";
            if (mssflag ==1) {
                sites_condition = "(siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or siteid=" + siteid + ")";
            }  else {
                sites_condition = "siteid=" + siteid;
            }

            IArticleManager articleMgr = ArticlePeer.getInstance();
            if(artid.equals("")) {
                if (editor.length() > 0) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE (columnID = ? and status<>2 and "+ sites_condition +"  and editor='" + editor + "')" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        //strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?  and "+ sites_condition +"  and editor='" + editor + "' " +sortstr;
                        strSQL = "select * from (select top ? * from(select top ? * from tbl_article where columnid=? and status<>2 and " + sites_condition + " and editor= '"  + editor + "'order by publishtime desc) as A order by publishtime asc) as B order by publishtime desc";
                    else {
                        strSQL = "select ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid  from tbl_article "+
                                "where columnid=? and status<>2 and " + sites_condition + " and editor='" + editor + "' "+sortstr + " limit ?,?";
                    }
                } else  {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE columnID = ? and status<>2 and " + sites_condition + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        //strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?   and "+ sites_condition  + sortstr;
                        strSQL = "select * from (select top ? * from(select top ? * from tbl_article where columnid=? and status<>2 and " + sites_condition + " order by publishtime desc) as A order by publishtime asc) as B order by publishtime desc";
                    else {
                        strSQL = "select ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid  from tbl_article "+
                                "where columnid=? and status<>2 and " + sites_condition  + sortstr + " limit ?,?";
                    }
                }
            } else {
                artid = articleMgr.formatOracleClause(artid,0);
                if (editor.length() > 0) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                " doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE (columnID = ? and status<>2 and " + sites_condition + " and editor='" + editor + "') or (id in ("+artid+"))" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        //strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and (columnid=? or id in(" +artid + ")) and "+ sites_condition +"  and editor='" + editor + "' " + sortstr;
                        strSQL = "select * from (select top ? * from(select top ? * from tbl_article where (columnid=? or id in("+ artid + ")) and status<>2 and " + sites_condition + " and editor= '"  + editor + "'order by publishtime desc) as A order by publishtime asc) as B order by publishtime desc";
                    else {
                        strSQL = "select ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid  from tbl_article " +
                                "where (columnid=? or id in (" + artid + ")) and status<>2 and " + sites_condition + " and editor='" + editor + "' "+sortstr + " limit ?,?";
                    }
                } else {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article WHERE (columnID = ? and status<>2 and " + sites_condition + ") or (id in ("+artid+"))" + sortstr + ") A WHERE ROWNUM <= ?) WHERE RN >= ?";
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        //strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id)) and (columnid=? or id in ("+artid+")) and "+ sites_condition + sortstr;
                        strSQL = "select * from (select top ? * from(select top ? * from tbl_article where (columnid=? or id in("+ artid + ")) and status<>2 and " + sites_condition + " order by publishtime desc) as A order by publishtime asc) as B order by publishtime desc";
                    else {
                        strSQL = "select ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                                "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid from tbl_article " +
                                "where (columnid=? or id in (" + artid + ")) and status<>2 and " + sites_condition  + sortstr + " limit ?,?";
                    }
                }
            }

             //System.out.println("strSQL==" + strSQL);

            pstmt = conn.prepareStatement(strSQL);
            if (cpool.getType().equalsIgnoreCase("oracle")) {
                pstmt.setInt(1, columnID);
                pstmt.setInt(2,startIndex + numResults);
                pstmt.setInt(3,startIndex);
            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                pstmt.setInt(1,numResults);
                pstmt.setInt(2,startIndex + numResults);
                pstmt.setInt(3,columnID);
            } else {
                pstmt.setInt(1,columnID);
                pstmt.setInt(2,startIndex);
                pstmt.setInt(3,numResults);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadArticleList1(rs);
                list.add(article);
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
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int getArticleNum(int columnID, String editor, String selectColumns,int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;
        int countRefer_aid = 0;

        try {
            conn = cpool.getConnection();
            String ids = "";
            String strSQL = "";

            String ReferArticleSQL = "select count(articleid) from tbl_refers_article where columnid = " + columnID+" and siteid="+siteid;
            pstmt=conn.prepareStatement(ReferArticleSQL);
            rs = pstmt.executeQuery();
            if (rs.next())
                countRefer_aid= rs.getInt(1);
            rs.close();
            pstmt.close();

            int mssflag = 0;
            strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt=conn.prepareStatement(strSQL);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();

            String sites_condition = "";
            if (mssflag ==1) {
                sites_condition = "(siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or siteid=" + siteid + ")";
            }  else {
                sites_condition = "siteid=" + siteid;
            }

            strSQL = "SELECT count(id) FROM tbl_article WHERE columnID = ? and status<>2 and " + sites_condition;
            if (editor.length() > 0) {
                strSQL = strSQL + " and editor='" + editor +"'";
            }

            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) count=rs.getInt(1);

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new orderArticleException("Database exception: get article count failed.");
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
        return count + countRefer_aid;
    }

    public int getAllArticleNum(int columnID, String editor, String selectColumns,int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        IArticleManager articleMgr = ArticlePeer.getInstance();
        String cidString = articleMgr.formatOracleClause(articleMgr.getColumnIDs(columnID));
        String strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE siteid="+siteid+"  and   " + cidString;

        if (editor.length() > 0)
            strSQL = "SELECT COUNT(ID) FROM TBL_Article WHERE  siteid="+siteid+"  and  " + cidString + " AND Editor = '" + editor + "'";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            if (selectColumns != null && selectColumns.length() > 0) {
                pstmt = conn.prepareStatement("select count(id) from tbl_refers_article where columnid = ? and siteid="+siteid+" and " + cidString);
                pstmt.setInt(1, columnID);
                rs = pstmt.executeQuery();
                if (rs.next())
                    count = count + rs.getInt(1);
                rs.close();
                pstmt.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new orderArticleException("Database exception: get article count failed.");
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
        return count;
    }

    public List searchArticles(int columnID, String item, String value, String editor, int startIndex, int numResults,int siteid,int flag,int ascdesc) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();

        IArticleManager articleMgr = ArticlePeer.getInstance();
        String cidString = articleMgr.formatOracleClause(articleMgr.getColumnIDs(columnID));

        StringBuffer strSQL = new StringBuffer();
        if (editor.equalsIgnoreCase("*"))
            strSQL.append("SELECT id FROM TBL_Article where " + cidString + " and siteid="+siteid);
        else
            strSQL.append("SELECT id FROM TBL_Article where  editor='" + editor.trim() + "' and " + cidString + " and siteid="+siteid);

        if (item.equals("publishtime")) {
            String where = "";
            String publishtime1 = value.substring(0, value.indexOf(","));
            String publishtime2 = value.substring(value.indexOf(",") + 1);
            if (publishtime1.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + " and publishtime >= TO_DATE('" + publishtime1 + "','YYYY-MM-DD') ";
                else
                    where = where + " and publishtime >= '" + publishtime1 + "'";
            }
            if (publishtime2.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + " and publishtime <= TO_DATE('" + publishtime2 + "','YYYY-MM-DD') ";
                else
                    where = where + " and publishtime <= '" + publishtime2 + "'";
            }
            if (where.trim().length() > 0) {
                where = where.trim();
                where = where.substring(0, where.length() - 3);
            }
            strSQL.append(where);
        } else if (item.equals("status")) {
            if (value.equals("1")) strSQL.append(" and status=1 and auditflag=0 and pubflag=1");    //
            if (value.equals("2")) strSQL.append(" and status=1 and auditflag=0 and pubflag=0");    //
            if (value.equals("3")) strSQL.append(" and status=0 and auditflag=0");                    //
            if (value.equals("4")) strSQL.append(" and status=1 and auditflag=1");                    //
            if (value.equals("5")) strSQL.append(" and auditflag=2");                                   //
        } else if (item.equals("id")) {
            strSQL.append( " and " + item + "=" + value);
        } else {
            strSQL.append( " and " + item + " like '%" + value  + "%' ");
        }

        String sortstr = "";
        if (flag == 0)
            if (ascdesc == 0)
                sortstr = " order by id desc";
            else
                sortstr = " order by id asc";
        else if (flag == 1)
            if (ascdesc == 0)
                sortstr = " order by maintitle desc";
            else
                sortstr = " order by maintitle asc";
        else if (flag == 2)
            if (ascdesc == 0)
                sortstr = " order by doclevel desc";
            else
                sortstr = " order by doclevel asc";
        else if (flag == 3)
            if (ascdesc == 0)
                sortstr = " order by vicedoclevel desc";
            else
                sortstr = " order by vicedoclevel asc";
        else if (flag == 4){
            if (ascdesc == 0)
                sortstr = " order by publishtime desc";
            else
                sortstr = " order by publishtime asc";
        }  if (flag == 5){
            if (ascdesc == 0)
                sortstr = " order by lastupdated desc";
            else
                sortstr = " order by lastupdated asc";
        }

        strSQL.append(sortstr);

        System.out.println(strSQL.toString());

        try {
            String ids = "";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL.toString());
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next())
                    ids += rs.getInt(1) + ",";
                else
                    break;
            }
            rs.close();
            pstmt.close();

            if (ids.length() > 0)
                ids = ids.substring(0, ids.length() - 1);
            else
                ids = "0";

            String SQL = "SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor," +
                    "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,emptycontentflag,vicedoclevel,filename,notearticleid FROM tbl_article where id " +
                    "in (" + ids + ") and siteid="+siteid+" order by publishtime desc";

            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(loadArticleList(rs));
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public int searchArticlesCount(int columnID, String item, String value, String editor, int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        IArticleManager articleMgr = ArticlePeer.getInstance();
        String cidString = articleMgr.formatOracleClause(articleMgr.getColumnIDs(columnID));

        StringBuffer strSQL = new StringBuffer();

        if (editor.equalsIgnoreCase("*"))
            strSQL.append("SELECT count(id) FROM TBL_Article where " + cidString + " and siteid="+siteid);
        else
            strSQL.append("SELECT count(id) FROM TBL_Article where  editor='" + editor.trim() + "' and " + cidString + " and siteid="+siteid);

        if (item.equals("publishtime")) {
            String where = "";
            String publishtime1 = value.substring(0, value.indexOf(","));
            String publishtime2 = value.substring(value.indexOf(",") + 1);
            if (publishtime1.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + " and publishtime >= TO_DATE('" + publishtime1 + "','YYYY-MM-DD') ";
                else
                    where = where + " and publishtime >= '" + publishtime1 + "'";
            }
            if (publishtime2.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + " and publishtime <= TO_DATE('" + publishtime2 + "','YYYY-MM-DD') ";
                else
                    where = where + " and publishtime <= '" + publishtime2 + "'";
            }
            if (where.trim().length() > 0) {
                where = where.trim();
                where = where.substring(0, where.length() - 3);
            }
            strSQL.append(where);
        } else if (item.equals("status")) {
            if (value.equals("1")) strSQL.append(" and status=1 and auditflag=0 and pubflag=1");       //
            if (value.equals("2")) strSQL.append(" and status=1 and auditflag=0 and pubflag=0");       //
            if (value.equals("3")) strSQL.append(" and status=0 and auditflag=0");                       //
            if (value.equals("4")) strSQL.append(" and status=1 and auditflag=1");                       //
            if (value.equals("5")) strSQL.append(" and auditflag=2");                                      //
        } else if (item.equals("id")) {
            strSQL.append( " and " + item + "=" + value);
        } else {
            strSQL.append( " and " + item + " like '%" + value  + "%' ");
        }

        System.out.println("search count==" + strSQL.toString());

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
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
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    //文章发布总数(/publish/articles.jsp)
    //usearticletype=1 表示引用是内容引用
    //usetypearticle=0 表示引用是连接引用
    public int getPublishArticlesNumByEditor(String editor,int columnID, int siteid, int samsiteid) throws  orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;
        String cidString = "";//formatOracleClause(getColumnIDs(columnID));

        //判断是否需要显示共享网站的信息
        int mssflag = 0;
        String sites_condition = "";
        try {
            conn = cpool.getConnection();
            String strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        //定义站点选择条件
        if (mssflag == 1) {
            sites_condition = "(siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or siteid=" + siteid + ")";
        } else {
            sites_condition = "siteid=" + siteid;
        }

        if (samsiteid > 0) {
            cidString = StringUtil.formatOracleClause(getColumnIDs(siteid, samsiteid, columnID));
        } else {
            cidString = StringUtil.formatOracleClause(getColumnIDs(columnID));
        }

        String strSQL_forArticle =
                "SELECT COUNT(id) FROM TBL_Article WHERE editor='" + editor.trim() + "' AND  (status = 1 OR status=4 OR status=5) AND AuditFlag = 0 " +
                        "AND PubFlag = 1 AND " + cidString + " and " + sites_condition + "  AND EmptyContentFlag = 0";

        //读取模板
        String strSQL_forTemplate =
                "SELECT COUNT(id) FROM TBL_Template WHERE Status = 0 AND IsArticle IN (0,2,3) AND " + cidString;

        //读取本栏目和它的所有子栏目引用的文章
        String strSQL2 = "SELECT count(a.id) from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and b.pubflag=1 " +
                "AND b.usearticletype=1 AND b." + cidString;

        try {
            //conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL_forArticle);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(strSQL_forTemplate);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(strSQL2);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = count + rs.getInt(1);
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

    //usearticletype=1 表示引用是内容引用
    //usetypearticle=0 表示引用是连接引用
    public List getPublishArticlesByEditor(String editor,int columnID, int startIndex, int numResults, int listShow, int siteid, int samsiteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List list = new ArrayList();
        List tempList = new ArrayList();
        Article article=null;

        //判断是否需要显示共享网站的信息
        int mssflag = 0;
        String sites_condition = "";
        try {
            conn = cpool.getConnection();
            String strSQL = "select managesharesite from tbl_siteinfo where siteid=?";
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) mssflag = rs.getInt("managesharesite");
            rs.close();
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
        //定义站点选择条件
        if (mssflag == 1) {
            sites_condition = "(siteid in (select siteid from tbl_siteinfo where samsiteid=" + siteid + ") or siteid=" + siteid + ")";
        } else {
            sites_condition = "siteid=" + siteid;
        }

        String cidString = ""; //formatOracleClause(getColumnIDs(columnID));
        if (samsiteid > 0) {
            cidString = StringUtil.formatOracleClause(getColumnIDs(siteid, samsiteid, columnID));
        } else {
            cidString = StringUtil.formatOracleClause(getColumnIDs(columnID));
        }
        //读取本栏目和它的所有子栏目的文章
        String strSQL = "SELECT ID,ColumnID,MainTitle,emptycontentflag,Editor,LastUpdated,viceTitle,dirName,urltype,defineurl,siteid " +
                "FROM TBL_Article WHERE editor='" + editor.trim() + "' AND (status = 1 OR status=4 OR status=5) AND auditflag = 0 and emptycontentflag = 0 AND " + sites_condition + " and pubflag = 1 AND " +
                cidString + " ORDER BY PublishTime DESC";

        //读取本栏目和它的所有子栏目的模板
        String strSQL1 = "SELECT ID,ColumnID,IsArticle,Editor,LastUpdated,DefaultTemplate,ChName,siteid FROM TBL_Template WHERE Status = 0 " +
                "AND IsArticle IN (0,2,3,4) AND " + cidString + " ORDER BY LastUpdated DESC";

        //读取本栏目和它的所有子栏目引用的文章
        String strSQL2 = "SELECT a.ID,b.ColumnID,b.scolumnid,a.MainTitle,a.emptycontentflag,a.Editor,a.LastUpdated,a.viceTitle,a.dirName," +
                "a.urltype,a.defineurl,a.siteid from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and b.pubflag=1 " +
                "AND b.usearticletype=1 AND b." + cidString + " ORDER BY a.createdate DESC";

        try {
            if (listShow == 0)            //先文章后模板
            {
                //获取本栏目自己待发布的文章
                pstmt = conn.prepareStatement(strSQL);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 0);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目引用的文章
                pstmt = conn.prepareStatement(strSQL2);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 2);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目需要发布的模板
                pstmt = conn.prepareStatement(strSQL1);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, true, 1);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();
            } else {
                //获取本栏目需要发布的模板
                pstmt = conn.prepareStatement(strSQL1);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, true, 1);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目自己待发布的文章
                pstmt = conn.prepareStatement(strSQL);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 0);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();

                //获取本栏目引用的文章
                pstmt = conn.prepareStatement(strSQL2);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = loadBriefContent(rs, false, 1);
                    tempList.add(article);
                }
                rs.close();
                pstmt.close();
            }

            for (int i = startIndex; i < startIndex + numResults; i++) {
                if (i > tempList.size() - 1) break;
                article = (Article) tempList.get(i);
                list.add(article);
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

    public String getColumnIDs(int columnID) {
        IColumnManager columnMgr = ColumnPeer.getInstance();
        int siteID = 0;
        try {
            siteID = columnMgr.getColumn(columnID).getSiteID();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        //共享站点TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
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

    public String getColumnIDs(int siteID,int columnID) {
        //共享站点TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
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
                if (treenodes[i] != null) {
                    if (treenodes[i].getLinkPointer() == columnID) {
                        nodenum = nodenum + 1;
                        pid[nodenum] = treenodes[i].getId();
                    }
                }
            }
        } while (nodenum >= 1);

        cid[0] = j - 1;            //cid[0]元素保存找到的子节点的数目

        return cid;
    }

    public String getColumnIDs(int siteid, int samsiteid, int columnID) {
        String result = "(";
        Tree colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid, samsiteid);
        node[] treenodes = colTree.getAllNodes();
        int[] cid = getSubTreeColumnIDList(treenodes, columnID, samsiteid);
        for (int i = 0; i < cid[0] - 1; i++) {
            result = result + cid[i + 1] + ",";
        }
        result = result + cid[cid[0]] + ")";

        return result;
    }

    private int[] getSubTreeColumnIDList(node[] treenodes, int columnID, int samsiteid) {
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

            for (i = 1; i < treenodes.length - 1; i++) {
                if (treenodes[i].getLinkPointer() == columnID) {
                    nodenum = nodenum + 1;
                    pid[nodenum] = treenodes[i].getId();
                }
            }
        } while (nodenum >= 1);

        cid[0] = j - 1;            //cid[0]元素保存找到的子节点的数目

        return cid;
    }


    public int getFirstArticleidForPage(int columnid,int pagenum,int pagesize) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String strSQL = "";
        int articleid = 0;
        int recnum=pagenum*pagesize;

        if (cpool.getType().equalsIgnoreCase("oracle"))
            strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,publishtime FROM tbl_article WHERE columnID = " + columnid + " order by publishtime desc) A WHERE ROWNUM <= " + (recnum+1) + ") WHERE RN >= " + recnum;
        else if (cpool.getType().equalsIgnoreCase("mssql"))
            strSQL = "select * from (select top " + pagesize + " * from(select top " + (pagenum+1)*pagesize + " id,publishtime from tbl_article where columnid=" + columnid + " order by publishtime desc) as A order by publishtime asc) as B order by publishtime desc";
        else {
            strSQL = "select ID,publishtime from tbl_article where columnid=" + columnid + " order by publishtime desc limit " + (pagenum+1)*pagesize + ",1";
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                articleid = rs.getInt(1);
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
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return articleid;
    }

    public int getFirstArticleidByDocLevel(int columnid,int doclevel) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String strSQL = "";
        int articleid = 0;

        strSQL = "SELECT ID FROM tbl_article WHERE columnID = ? and doclevel=?";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1,columnid);
            pstmt.setInt(2,doclevel);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                articleid = rs.getInt(1);
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
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return articleid;
    }

    //获取某个栏目下所有待发布的文章和模板列表
    public List getPublishArticlesInColumn(int columnID,int siteid) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List tempList = new ArrayList();
        Article article=null;

        String subnodes = getColumnIDs(siteid,columnID);

        String strSQL = "SELECT ID,ColumnID,MainTitle,emptycontentflag,Editor,LastUpdated,viceTitle,dirName,urltype,defineurl,siteid " +
                "FROM TBL_Article WHERE (status = 1 OR status=4 OR status=5 OR status=6) AND auditflag = 0 and emptycontentflag = 0 and pubflag = 1 AND columnid in " + subnodes;

        String strSQL1 = "SELECT ID,ColumnID,IsArticle,Editor,LastUpdated,DefaultTemplate,ChName,siteid FROM TBL_Template WHERE Status = 0 " +
                "AND IsArticle IN (0,2,3,4) AND columnid in " + subnodes ;

        String strSQL2 = "SELECT a.ID,b.ColumnID,b.scolumnid,a.MainTitle,a.emptycontentflag,a.Editor,a.LastUpdated,a.viceTitle,a.dirName," +
                "a.urltype,a.defineurl,a.siteid from tbl_article a,tbl_refers_article b WHERE a.id=b.articleid and b.pubflag=1 " +
                "AND b.usearticletype=1 AND b.columnid in " + subnodes;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            //pstmt.setInt(1,columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadBriefContent(rs, false, 0);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(strSQL2);
            //pstmt.setInt(1,columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadBriefContent(rs, false, 2);
                tempList.add(article);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(strSQL1);
            //pstmt.setInt(1,columnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = loadBriefContent(rs, true, 1);
                tempList.add(article);
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

        return tempList;
    }

    //获取推荐文章列表
    public List getRecommendArticleList(int startnum,int endnum,int markID,int siteID) throws orderArticleException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List tempList = new ArrayList();
        Article article=null;

        String strSQL = "SELECT ta.id,ta.columnid,ta.siteid,ta.maintitle,ta.vicetitle,ta.source,ta.summary,ta.keyword,ta.author,ta.emptycontentflag,ta.editor," +
                "ta.lastupdated,ta.dirname,ta.urltype,ta.filename,ta.createdate,ta.defineurl,ta.downfilename,ta.articlepic,ta.changepic,ta.pic,ta.bigpic,ta.mediafile,ta.publishtime,ta.createdate,ta.lastupdated " +
                "FROM tbl_article ta,tbl_recommend_article tr WHERE ta.siteid=? and ta.id=tr.articleid and tr.markid=? and (ta.status = 1 OR ta.status=4 OR ta.status=5 OR ta.status=6) AND ta.auditflag = 0 and ta.pubflag = 0 order by ta.publishtime desc";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1,siteID);
            pstmt.setInt(2,markID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setColumnID(rs.getInt("columnid"));
                article.setSiteID(rs.getInt("siteid"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setViceTitle(rs.getString("vicetitle"));
                article.setSource(rs.getString("source"));
                article.setSummary(rs.getString("summary"));
                article.setKeyword(rs.getString("keyword"));
                article.setAuthor(rs.getString("author"));
                article.setNullContent(rs.getInt("emptycontentflag"));
                article.setEditor(rs.getString("editor"));
                article.setLastUpdated(rs.getTimestamp("lastupdated"));
                article.setDirName(rs.getString("dirname"));
                article.setUrltype(rs.getInt("urltype"));
                article.setFileName(rs.getString("filename"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setDownfilename(rs.getString("downfilename"));
                article.setArticlepic(rs.getString("articlepic"));
                article.setChangepic(rs.getInt("changepic"));
                article.setProductPic(rs.getString("pic"));
                article.setProductBigPic(rs.getString("bigpic"));
                article.setMediafile(rs.getString("mediafile"));
                article.setPublishTime(rs.getTimestamp("publishtime"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setLastUpdated(rs.getTimestamp("lastupdated"));
                tempList.add(article);
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

        return tempList;
    }

    //如果是模板 refflag=1
    //如果是栏目自己的文章 refflag=0
    //如果是栏目引用的文章 refflag=2
    Article loadBriefContent(ResultSet rs, boolean isTemplate, int refflag) throws Exception {
        Article article = new Article();
        try {
            article.setID(rs.getInt("ID"));
            article.setColumnID(rs.getInt("ColumnID"));
            article.setEditor(rs.getString("Editor"));
            article.setLastUpdated(rs.getTimestamp("LastUpdated"));
            article.setIsTemplate(isTemplate);
            if (isTemplate) {
                article.setMainTitle(rs.getString("ChName"));
                article.setIsArticleTemplate(rs.getInt("IsArticle"));
                article.setStatus(rs.getInt("DefaultTemplate"));
            } else {
                if (refflag == 2) {
                    article.setIsown(false);
                    article.setMainTitle(rs.getString("MainTitle") + "<font color=red>(引用)</font>");
                } else {
                    article.setIsown(true);
                    article.setMainTitle(rs.getString("MainTitle"));
                }
                article.setUrltype(rs.getInt("urltype"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setSiteID(rs.getInt("siteid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    Article loadArticleList(ResultSet rs) throws SQLException {
        Article article = new Article();
        try {
            article.setID(rs.getInt("ID"));
            article.setMainTitle(rs.getString("MainTitle"));
            article.setViceTitle(rs.getString("ViceTitle"));
            article.setNullContent(rs.getInt("Emptycontentflag"));
            article.setEditor(rs.getString("editor"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setViceDocLevel(rs.getInt("vicedoclevel"));
            article.setStatus(rs.getInt("Status"));
            article.setLockStatus(rs.getInt("lockstatus"));
            article.setLockEditor(rs.getString("lockeditor"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setAuditFlag(rs.getInt("auditflag"));
            //article.setAuditor(rs.getString("auditor"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setLastUpdated(rs.getTimestamp("LastUpdated"));
            //article.setCreateDate(rs.getTimestamp("CreateDate"));
            article.setDirName(rs.getString("DirName"));
            article.setJoinRSS(rs.getInt("isJoinRSS"));
            article.setReferArticleID(rs.getInt("referID"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    Article loadArticleList1(ResultSet rs) throws SQLException {
        Article article = new Article();
        try {
            article.setID(rs.getInt("ID"));
            article.setMainTitle(rs.getString("MainTitle"));
            article.setViceTitle(rs.getString("ViceTitle"));
            article.setNullContent(rs.getInt("Emptycontentflag"));
            article.setEditor(rs.getString("editor"));
            article.setColumnID(rs.getInt("columnid"));
            article.setSortID(rs.getInt("sortid"));
            article.setViceDocLevel(rs.getInt("vicedoclevel"));
            article.setStatus(rs.getInt("Status"));
            article.setLockStatus(rs.getInt("lockstatus"));
            article.setLockEditor(rs.getString("lockeditor"));
            article.setDocLevel(rs.getInt("doclevel"));
            article.setPubFlag(rs.getInt("pubflag"));
            article.setAuditFlag(rs.getInt("auditflag"));
            //article.setAuditor(rs.getString("auditor"));
            article.setFileName(rs.getString("fileName"));
            article.setPublishTime(rs.getTimestamp("publishtime"));
            article.setLastUpdated(rs.getTimestamp("LastUpdated"));
            //article.setCreateDate(rs.getTimestamp("CreateDate"));
            article.setDirName(rs.getString("DirName"));
            article.setJoinRSS(rs.getInt("isJoinRSS"));
            article.setReferArticleID(rs.getInt("referID"));
            article.setMultimediatype(rs.getInt("multimediatype"));
            article.setNotes(rs.getInt("notearticleid"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }
}