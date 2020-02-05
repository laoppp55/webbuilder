package com.bizwink.log;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.sql.Date;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.server.*;
import com.bizwink.cms.security.User;
import com.bizwink.cms.util.FileUtil;

public class LogPeer implements ILogManager {
    PoolServer cpool;

    public LogPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ILogManager getInstance() {
        return CmsServer.getInstance().getFactory().getLogManager();
    }

    public List getLoginfoInPage(int range,int currentpage,int siteid,List<Integer> columnids,List<Integer> deptids,List<String> editors,String sdatetime,String edatetime,String keyword,int optype) throws LogException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Log log = null;
        List<Log> result = new ArrayList<>();
        try {
            String where = "siteid=?";

            //设置栏目检索条件
            if (columnids!=null) {
                where = where + " AND (";
                for(int ii=0; ii<columnids.size()-1; ii++) {
                    int colid = columnids.get(ii);
                    where  = where + "columnid=" + colid + " OR ";
                }
                if (columnids.size()>=1) where  = where + "columnid=" + columnids.get(columnids.size()-1);
                where = where + ")";
            }

            //设置部门检索条件
            if (deptids!=null) {
                where = where + " AND (";
                for(int ii=0; ii<deptids.size()-1; ii++) {
                    int dept = deptids.get(ii);
                    where = where + "deptid=" + dept + " OR ";
                }
                if (deptids.size()>=1) where = where + "deptid=" + deptids.get(deptids.size()-1);
                where = where + ")";
            }

            //设置网站编辑人员检索条件
            if (editors!=null) {
                where = where + " AND (";
                for(int ii=0; ii<editors.size()-1; ii++) {
                    String editor = editors.get(ii);
                    where = where + "editor='" + editor + "' OR ";
                }
                if (editors.size()>=1) where = where + "editor='" + editors.get(editors.size()-1) + "'";
                where = where + ")";
            }

            //设置开始时间检索条件
            if (sdatetime != null && sdatetime!="") where = where + " AND to_char(acttime,'yyyy-MM-dd hh24:mi:ss')>='" + sdatetime + "'";

            //设置结束时间检索条件
            if (edatetime != null && edatetime!="") where = where + " AND to_char(acttime,'yyyy-MM-dd hh24:mi:ss')<='" + edatetime + "'";

            //设置关键字标题检索条件
            if(keyword!=null && keyword!="") where = where + " AND maintitle like '%" + keyword + "%'";

            //设置操作条件
            if(optype>0) where = where  + " AND acttype=" + optype;

            String result_sql = "SELECT * FROM (SELECT tt.*, ROWNUM AS rowno FROM (select id,siteid,columnid,articleid,editor,acttime,acttype,maintitle,orgid,companyid,deptid from tbl_log where " + where + " order by acttime desc ) tt WHERE ROWNUM <= ?) table_alias WHERE table_alias.rowno >= ?";

            System.out.println(result_sql);

            //设置查询结果开始记录和结束记录
            int startrow = (currentpage-1)*range;
            int endrow = currentpage*range;

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(result_sql);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2,endrow);
            pstmt.setInt(3,startrow);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                log = new Log();
                log.setID(rs.getInt("id"));
                log.setSiteID(rs.getInt("siteid"));
                log.setColumnid(rs.getInt("columnid"));
                log.setArticleID(rs.getInt("articleid"));
                log.setEditor(rs.getString("editor"));
                log.setActTime(rs.getTimestamp("acttime"));
                log.setActType(rs.getInt("acttype"));
                log.setMaintitle(rs.getString("maintitle"));
                log.setOrgid(rs.getInt("orgid"));
                log.setColumnid(rs.getInt("companyid"));
                log.setDeptid(rs.getInt("deptid"));
                result.add(log);
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

        return result;
    }

    public int getCountLoginfo(int siteid,List<Integer> columnids,List<Integer> deptids,List<String> editors,String sdatetime,String edatetime,String keyword,int optype)  throws LogException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int countrow = 0;
        try {
            String where = "siteid=?";

            //设置栏目检索条件
            if (columnids!=null) {
                where = where + " AND (";
                for(int ii=0; ii<columnids.size()-1; ii++) {
                    int colid = columnids.get(ii);
                    where  = where + "columnid=" + colid + " OR ";
                }
                if (columnids.size()>=1) where  = where + "columnid=" + columnids.get(columnids.size()-1);
                where = where + ")";
            }

            //设置部门检索条件
            if (deptids!=null) {
                where = where + " AND (";
                for(int ii=0; ii<deptids.size()-1; ii++) {
                    int dept = deptids.get(ii);
                    where = where + "deptid=" + dept + " OR ";
                }
                if (deptids.size()>=1) where = where + "deptid=" + deptids.get(deptids.size()-1);
                where = where + ")";
            }

            //设置网站编辑人员检索条件
            if (editors!=null) {
                where = where + " AND (";
                for(int ii=0; ii<editors.size()-1; ii++) {
                    String editor = editors.get(ii);
                    where = where + "editor='" + editor + "' OR ";
                }
                if (editors.size()>=1) where = where + "editor='" + editors.get(editors.size()-1) + "'";
                where = where + ")";
            }

            //设置开始时间检索条件
            if (sdatetime != null && sdatetime!="") where = where + " AND to_char(acttime,'yyyy-MM-dd hh24:mi:ss')>='" + sdatetime + "'";

            //设置结束时间检索条件
            if (edatetime != null && edatetime!="") where = where + " AND to_char(acttime,'yyyy-MM-dd hh24:mi:ss')<='" + edatetime + "'";

            //设置关键字标题检索条件
            if(keyword!=null && keyword!="") where = where + " AND maintitle like '%" + keyword + "%'";

            //设置操作条件
            if(optype>0) where = where  + " AND acttype=" + optype;

            String result_sql = "select count(id) from tbl_log where " + where;
            System.out.println(result_sql);
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(result_sql);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) countrow = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            countrow = -1;
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    countrow = -1;
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return countrow;
    }

    private static final String SQL_GET_EDITOR_INFO =
            "SELECT DISTINCT a.Editor,b.NickName FROM TBL_Log a,TBL_Members b WHERE a.SiteID = ? AND a.Editor = b.UserID";

    private static final String SQL_GET_EDITOR_ACTINFO = "select createarticles,editarticles,deletearticles from tbl_members" +
            " where userid = ?";

    public List getEditorLogInfo(int siteID, String where) throws LogException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List editList = new ArrayList();
        List tempList = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_EDITOR_INFO);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tempList.add(rs.getString(1) + ":" + rs.getString(2));
            }
            rs.close();
            pstmt.close();

            for (int i = 0; i < tempList.size(); i++) {
                int createCount = 0;
                int updateCount = 0;
                int deleteCount = 0;
                String editor = (String) tempList.get(i);
                String nickname = editor.substring(editor.indexOf(":") + 1);
                editor = editor.substring(0, editor.indexOf(":"));

                /*if (where.equals(" and 1=1")) {
                    pstmt = conn.prepareStatement(SQL_GET_EDITOR_ACTINFO);
                    pstmt.setString(1, editor);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        createCount = rs.getInt(1);
                        updateCount = rs.getInt(2);
                        deleteCount = rs.getInt(3);
                    }
                    rs.close();
                    pstmt.close();

                    editList.add(editor + ":" + nickname + ":" + createCount + ":" + updateCount + ":" + deleteCount);
                } else */
                {
                    String SQL_SEARCH_USERS_LOG_INFO = "select count(id),max(acttype) as acttype from tbl_log " +
                            "where editor = ?" + where + " group by editor,acttype order by acttype";

                    pstmt = conn.prepareStatement(SQL_SEARCH_USERS_LOG_INFO);
                    pstmt.setString(1, editor);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int acttype = rs.getInt("acttype");
                        if (acttype == 1)
                            createCount = rs.getInt(1);
                        else if (acttype == 2)
                            updateCount = rs.getInt(1);
                        else if (acttype == 3)
                            deleteCount = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();

                    editList.add(editor + ":" + nickname + ":" + createCount + ":" + updateCount + ":" + deleteCount);
                }
            }
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
        return editList;
    }

    private static String SQL_GET_USERS_LOG_INFO_OF_GROUP = "select userid,nickname,createarticles,editarticles," +
            "deletearticles from tbl_members where userid in (select userid from tbl_group_members where groupid=?)";


    /**
     * 获得某个用户组下所有用户的Log信息
     * Add by EricDu 2007-9-23
     *
     * @param groupId 用户组的id
     * @return 用户Log信息的List
     * @throws LogException 异常
     */
    public List getGroupsEditorLogInfo(int groupId) throws LogException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List logList = new ArrayList();
        List tempList = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USERS_LOG_INFO_OF_GROUP);
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                /*User user = new User();
                user.setID(rs.getString("userid"));
                user.setNickName(rs.getString("nickname"));
                user.setCreatearticles(rs.getInt("createarticles"));
                user.setEditarticles(rs.getInt("editarticles"));
                user.setDeletearticles(rs.getInt("deletearticles"));

                logList.add(user);*/
                tempList.add(rs.getString(1) + ":" + rs.getString(2));
            }
            rs.close();
            pstmt.close();
            for (int i = 0; i < tempList.size(); i++) {
                int createCount = 0;
                int updateCount = 0;
                int deleteCount = 0;
                String editor = (String) tempList.get(i);
                String nickname = editor.substring(editor.indexOf(":") + 1);
                editor = editor.substring(0, editor.indexOf(":"));


                {
                    String SQL_SEARCH_USERS_LOG_INFO = "select count(id),max(acttype) as acttype from tbl_log " +
                            "where editor = ? group by editor,acttype order by acttype";

                    pstmt = conn.prepareStatement(SQL_SEARCH_USERS_LOG_INFO);
                    pstmt.setString(1, editor);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int acttype = rs.getInt("acttype");
                        if (acttype == 1)
                            createCount = rs.getInt(1);
                        else if (acttype == 2)
                            updateCount = rs.getInt(1);
                        else if (acttype == 3)
                            deleteCount = rs.getInt(1);


                    }
                    rs.close();
                    pstmt.close();
                    User user = new User();
                    user.setID(editor);
                    user.setNickName(nickname);
                    user.setCreatearticles(createCount);
                    user.setEditarticles(updateCount);
                    user.setDeletearticles(deleteCount);

                    logList.add(user);
                    /*editList.add(editor + ":" + nickname + ":" + createCount + ":" + updateCount + ":" + deleteCount);*/
                }
            }
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
        return logList;
    }

    /**
     * 查询某个用户组下某段时间内所有用户的Log信息
     * Add by EricDu 2007-9-23
     *
     * @param groupId 用户组的id
     * @param where   条件
     * @return 用户Log信息的List
     * @throws LogException 异常
     */
    public List getGroupsEditorLogInfo(int groupId, String where) throws LogException {

        String SQL_SEARCH_USERS_LOG_INFO_OF_GROUP = "select count(id),max(acttype) as acttype from tbl_log where editor" +
                " = ?" + where + " group by editor,acttype order by acttype";

        String SQL_GET_ALL_USERS_OF_GROUP = "select g.userid,m.nickname from tbl_group_members g,tbl_members m where groupid=? and g.userid=m.userid";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List logList = new ArrayList();
        List userList = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_USERS_OF_GROUP);
            pstmt.setInt(1, groupId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setID(rs.getString("userid"));
                user.setNickName(rs.getString("nickname"));
                userList.add(user);
            }
            rs.close();
            pstmt.close();

            for (int i = 0; i < userList.size(); i++) {
                User user = (User) userList.get(i);
                pstmt = conn.prepareStatement(SQL_SEARCH_USERS_LOG_INFO_OF_GROUP);
                pstmt.setString(1, user.getID());

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int acttype = rs.getInt("acttype");
                    if (acttype == 1)
                        user.setCreatearticles(rs.getInt(1));
                    else if (acttype == 2)
                        user.setEditarticles(rs.getInt(1));
                    else if (acttype == 3)
                        user.setDeletearticles(rs.getInt(1));
                }
                logList.add(user);

                rs.close();
                pstmt.close();
            }
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
        return logList;
    }

    public List getEditorLogInfoGroupbyActTime(int siteID, String editor, int flag, int startrow, int range, String where)
            throws LogException {

        String SQL_GET_LOGINFO_GROUPBY_CREATEDATE = "SELECT count(id),createdate FROM TBL_Log WHERE SiteID = ? AND " +
                "ActType = ? AND Editor = ?" + where + " group by createdate order by createdate desc";

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List editList = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_LOGINFO_GROUPBY_CREATEDATE);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, flag);
            pstmt.setString(3, editor);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                editList.add(rs.getDate(2) + ":" + rs.getInt(1));
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
        return editList;
    }

    public int getEditorLogInfoNumGroupbyActTime(int siteID, String editor, int flag, String where) throws LogException {

        String SQL_GET_LOGINFONUM_GROUPBY_CREATEDATE =
                "SELECT count(distinct createdate) FROM TBL_Log WHERE SiteID = ? AND ActType = ? AND Editor = ?" + where;

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int rowsnum = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_LOGINFONUM_GROUPBY_CREATEDATE);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, flag);
            pstmt.setString(3, editor);
            rs = pstmt.executeQuery();
            if (rs.next())
                rowsnum = rs.getInt(1);

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
        return rowsnum;
    }

    private static final String SQL_GET_DETAIL_LOGINFONUM_GROUPBY_CREATEDATE =
            "SELECT count(id) FROM TBL_Log WHERE SiteID = ? AND ActType = ? AND Editor = ? and createdate = ?";

    public int getEditorDetailLogInfoNumGroupbyActTime(int siteID, String editor, int flag, String date) throws LogException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int rowsnum = 0;

        int year = Integer.parseInt(date.substring(0, 4)) - 1900;
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"))) - 1;
        int d = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1));
        Date actDate = new Date(year, month, d);

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_DETAIL_LOGINFONUM_GROUPBY_CREATEDATE);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, flag);
            pstmt.setString(3, editor);
            pstmt.setDate(4, actDate);
            rs = pstmt.executeQuery();
            if (rs.next())
                rowsnum = rs.getInt(1);

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
        return rowsnum;
    }

    private static final String SQL_CREATE_KEYWORD_LOG = "INSERT INTO tbl_searchword (siteid,ip,keyword,createdate) VALUES (?, ?, ?, ?)";

    public int LogSearchKeyword(int siteID, String userip, String keyword) throws LogException {
        int retcode = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_CREATE_KEYWORD_LOG);
            pstmt.setInt(1, siteID);
            pstmt.setString(2, userip);
            pstmt.setString(3, keyword);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Throwable t) {
            retcode = -10;
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

        return  retcode;
    }

    private static final String SQL_GET_DETAIL_LOGINFO_GROUPBY_CREATEDATE = "SELECT articleid,editor,maintitle,acttime" +
            " FROM TBL_Log WHERE SiteID = ? AND ActType = ? AND Editor = ? and createdate = ? order by id desc";

    public List getEditorDetailLogInfoGroupbyActTime(int siteID, String editor, int flag, int startrow, int range, String date)
            throws LogException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List editList = new ArrayList();

        int year = Integer.parseInt(date.substring(0, 4)) - 1900;
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"))) - 1;
        int d = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1));
        Date actDate = new Date(year, month, d);

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_DETAIL_LOGINFO_GROUPBY_CREATEDATE);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, flag);
            pstmt.setString(3, editor);
            pstmt.setDate(4, actDate);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                Log log = new Log();
                log.setArticleID(rs.getInt("articleid"));
                log.setMaintitle(rs.getString("maintitle"));
                log.setActTime(rs.getTimestamp("acttime"));
                log.setEditor(rs.getString("editor"));
                editList.add(log);
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
        return editList;
    }

    public List<PublishArticleListOfWeek> getArticlesOfToday(int siteid,String deptname) throws LogException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<PublishArticleListOfWeek> articles = new ArrayList<>();
        PublishArticleListOfWeek articleList = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        Calendar start_cal = Calendar.getInstance();
        start_cal.setTimeInMillis(today.getTime());
        start_cal.add(Calendar.WEEK_OF_MONTH,-1);
        Timestamp starttime = new Timestamp(start_cal.getTimeInMillis());

        try {
            conn = cpool.getConnection();
            if (deptname == null) {
                pstmt = conn.prepareStatement("select id,maintitle,createdate,filename from tbl_article where siteid=? and to_char(createdate,'yyyyMMdd')>=? and to_char(createdate,'yyyyMMdd')<=? order by createdate desc");
                pstmt.setInt(1,siteid);
                pstmt.setString(2,simpleDateFormat.format(starttime));
                pstmt.setString(3,simpleDateFormat.format(today));
            }else {
                pstmt = conn.prepareStatement("select id,maintitle,createdate,filename from tbl_article where siteid=? and source=? and to_char(createdate,'yyyyMMdd')>=? and to_char(createdate,'yyyyMMdd')<=?  order by createdate desc");
                pstmt.setInt(1,siteid);
                pstmt.setString(2,deptname);
                pstmt.setString(3,simpleDateFormat.format(starttime));
                pstmt.setString(4,simpleDateFormat.format(today));
            }
            rs = pstmt.executeQuery();
            List<Article> list = new ArrayList<>();
            while (rs.next()) {
                Article article = new Article();
                article.setID(rs.getInt("id"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setFileName(rs.getString("filename"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                list.add(article);
            }
            rs.close();
            pstmt.close();

            //按日期分组
            Calendar today_cal = Calendar.getInstance();
            today_cal.setTimeInMillis(today.getTime());
            List<Article> tlist = null;                                    //存储某一天的文章信息
            while(today_cal.getTime().after(start_cal.getTime())){         //判断是否到结束日期 {
                Timestamp thedate = new Timestamp(today_cal.getTimeInMillis());
                String thedate_str = simpleDateFormat.format(thedate);
                tlist = new ArrayList<>();
                for(int ii=0;ii<list.size();ii++) {
                    Article article = list.get(ii);
                    String createDate = simpleDateFormat.format(article.getCreateDate());
                    if (createDate.equalsIgnoreCase(thedate_str)) {
                        tlist.add(article);
                    }
                }

                if (tlist.size()>0) {
                    articleList = new PublishArticleListOfWeek();
                    articleList.setStrdate(simpleDateFormat.format(thedate));
                    articleList.setArticles(tlist);
                    articles.add(articleList);
                }
                today_cal.add(Calendar.DAY_OF_MONTH,-1);     //日期减一天
            }
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
        return articles;
    }

    public List<PublishArticleCountOfDept> getPublishArticleCountOFMonthOfDept(int siteid) throws LogException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        PublishArticleCountOfDept publishArticleCountOfDept = null;
        int count = 0;
        List<PublishArticleCountOfDept> publishArticleCountOfDepts = new ArrayList<>();
        try {
            Timestamp today = new Timestamp(System.currentTimeMillis());
            Calendar start_cal = Calendar.getInstance();
            start_cal.setTimeInMillis(today.getTime());
            start_cal.add(Calendar.MONTH,-1);
            Timestamp starttime = new Timestamp(start_cal.getTimeInMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select t.source,count(t.id) as d1 from tbl_article t where t.siteid=? and t.source is not null and to_char(t.createdate,'yyyyMMdd')>=? and to_char(t.createdate,'yyyyMMdd')<=? group by t.source order by d1 desc");
            pstmt.setInt(1,siteid);
            pstmt.setString(2,simpleDateFormat.format(starttime));
            pstmt.setString(3,simpleDateFormat.format(today));
            rs = pstmt.executeQuery();
            while (rs.next()) {
                publishArticleCountOfDept = new PublishArticleCountOfDept();
                publishArticleCountOfDept.setDeptname(rs.getString("source"));
                publishArticleCountOfDept.setPublishArticleCount(rs.getInt("d1"));
                publishArticleCountOfDepts.add(publishArticleCountOfDept);
                count = count + 1;
                if (count>10) break;
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

        return publishArticleCountOfDepts;
    }

    public LogColumnTree getLogColumnTree(int siteid)  throws LogException{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        LogColumnTree logColumnTree = new LogColumnTree();
        List<Column> columnList = new ArrayList<>();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select t.id,t.parentid,t.cname from tbl_column t where t.siteid=? order by t.id asc");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Column column = new Column();
                column.setID(rs.getInt("id"));
                column.setParentID(rs.getInt("parentid"));
                column.setCName(rs.getString("cname"));
                columnList.add(column);
            }
            rs.close();
            pstmt.close();


            //设置栏目树的根节点和节点数量
            PublishArticleCountOfColumn[] columnTreeNode = new PublishArticleCountOfColumn[columnList.size()];
            logColumnTree.setNodeNum(columnList.size());
            List<Column> tcolList = new ArrayList<>();
            tcolList.add(columnList.get(0));
            int nodeNum = 1;
            int arrIndexNum = 0;
            while(tcolList.size()>0) {
                Column column = tcolList.get(0);
                columnTreeNode[arrIndexNum] = new PublishArticleCountOfColumn();
                columnTreeNode[arrIndexNum].setColumnid(column.getID());
                columnTreeNode[arrIndexNum].setPcolumnid(column.getParentID());
                columnTreeNode[arrIndexNum].setColname(column.getCName());
                tcolList.remove(0);
                nodeNum = nodeNum-1;
                int subNodeNum = 0;
                for(int ii=0;ii<columnList.size();ii++) {
                    if (columnList.get(ii).getParentID()==column.getID()) {
                        tcolList.add(columnList.get(ii));
                        nodeNum = nodeNum + 1;
                        subNodeNum = subNodeNum + 1;
                    }
                }

                //设置当前节点的子节点数目
                columnTreeNode[arrIndexNum].setSubcolnum(subNodeNum);

                //设置当前节点在树型结构中的层次
                if (columnTreeNode[arrIndexNum].getPcolumnid()==0)
                    columnTreeNode[arrIndexNum].setLevel(0);
                else {
                    //找到当前节点的父节点，父节点的层级数加1就是当前节点的层级数
                    int ii=0;
                    for(ii=0;ii<arrIndexNum;ii++) {
                        if (columnTreeNode[ii].getColumnid() == column.getParentID()) break;
                    }
                    columnTreeNode[arrIndexNum].setLevel(columnTreeNode[ii].getLevel() + 1);
                }

                arrIndexNum++;
            }

            logColumnTree.setPublishArticleCountOfColumns(columnTreeNode);
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

        return logColumnTree;
    }

    public LogColumnTree getWorkingByCol(int siteid,LogColumnTree logColumnTree)  throws LogException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            Calendar start_cal = Calendar.getInstance();
            Calendar today_cal = Calendar.getInstance();
            //设置今天的时间
            today_cal.setTimeInMillis(System.currentTimeMillis());
            //设置开始时间为今天
            start_cal.setTimeInMillis(System.currentTimeMillis());
            //开始时间设置为三个月前今天的时间
            start_cal.set(Calendar.MONTH,-3);
            Timestamp today = new Timestamp(today_cal.getTimeInMillis());
            Timestamp startdate = new Timestamp(start_cal.getTimeInMillis());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String today_str = simpleDateFormat.format(today);
            String startdate_str = simpleDateFormat.format(startdate);
            conn = cpool.getConnection();
            PublishArticleCountOfColumn[] columnTreeNode = logColumnTree.getPublishArticleCountOfColumns();
            for(int ii=0; ii<columnTreeNode.length;ii++) {
                if (columnTreeNode[ii]!=null) {
                    int columnid = columnTreeNode[ii].getColumnid();
                    pstmt = conn.prepareStatement("select count(id) from tbl_article where siteid=? and columnid=? and to_char(createdate,'yyyyMMdd')>=? and to_char(createdate,'yyyyMMdd')<?");
                    pstmt.setInt(1, siteid);
                    pstmt.setInt(2, columnid);
                    pstmt.setString(3, startdate_str);
                    pstmt.setString(4, today_str);
                    rs = pstmt.executeQuery();
                    if (rs.next()) columnTreeNode[ii].setArticlecount(rs.getInt(1));
                    rs.close();
                    pstmt.close();
                }
            }
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
        return logColumnTree;
    }
}