package com.bizwink.webapps.usercenter;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import java.sql.*;
import java.util.*;

public class UsercenterPeer implements IUsercenterManager {

    PoolServer cpool;

    public UsercenterPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IUsercenterManager getInstance() {
        return (IUsercenterManager) CmsServer.getInstance().getFactory().getUsercenterManager();
    }

    public List getArticleList(String username,int siteid,int columnid,int startrow,int pagesize,int infotype){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Article article;

        try {
            conn = cpool.getConnection();
            String strSQL = "";
            if (cpool.getType().equalsIgnoreCase("oracle"))
                strSQL = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT ID,maintitle,vicetitle,editor,columnid,vicedoclevel,status,lockstatus,lockeditor,emptycontentflag," +
                        "doclevel,pubflag,auditflag,publishtime,dirname,isJoinRSS,referID,LastUpdated,sortid,multimediatype,filename,notearticleid FROM tbl_article " +
                        "WHERE columnID = ? and siteid=? and status=1  and editor=? order by publishtime desc) A WHERE ROWNUM <= ?) WHERE RN >= ?";
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                strSQL = "select top ? * from tbl_article where (id not in (select top ? id from tbl_article order by id))  and columnid=?  and siteid=? and status=1  and editor=? order by publishtime desc";
            else
                strSQL = "select * from tbl_article where id>=(select id from tbl_article where columnid=? and siteid=? and status=1 and editor=? order by publishtime desc  limit ?,1) limit ?";

            //System.out.println("strSQL="+strSQL);
            //System.out.println("editor="+username);
            //System.out.println("startIndex="+startrow);
            //System.out.println("numResults="+pagesize);
            //System.out.println("columnID="+columnid);

            pstmt = conn.prepareStatement(strSQL);
            if (cpool.getType().equalsIgnoreCase("oracle")) {
                pstmt.setInt(1,columnid);
                pstmt.setInt(2,siteid);
                pstmt.setString(3,username);
                pstmt.setInt(4,startrow + pagesize);
                pstmt.setInt(5,startrow);
            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                pstmt.setInt(1,pagesize);
                pstmt.setInt(2,startrow);
                pstmt.setInt(3,columnid);
                pstmt.setInt(4,siteid);
                pstmt.setString(5,username);
            } else {
                pstmt.setInt(1,columnid);
                pstmt.setInt(2,siteid);
                pstmt.setString(3,username);
                pstmt.setInt(2,startrow);
                pstmt.setInt(3,pagesize);
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

    public int getArticleCont(String username,int siteid,int columnid,int infotype){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            String strSQL = "SELECT count(*) FROM tbl_article WHERE columnID = ? and siteid=? and status=1  and editor=?";

            pstmt = conn.prepareStatement(strSQL);
            pstmt.setInt(1,columnid);
            pstmt.setInt(2,siteid);
            pstmt.setString(3,username);
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
}