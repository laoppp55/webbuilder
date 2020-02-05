package com.bizwink.cms.sitesetting;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.server.*;

public class SiteInfoPeer implements ISiteInfoManager {
    PoolServer cpool;

    public SiteInfoPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ISiteInfoManager getInstance() {
        return CmsServer.getInstance().getFactory().getSiteInfoManager();
    }

    private static final String SQL_GETSITE = "SELECT * from tbl_siteinfo where siteid = ?";

    public SiteInfo getSiteInfo(int siteid) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        SiteInfo siteInfo = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                siteInfo = load(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return siteInfo;
    }

    private static final String SQL_GETSITEID = "SELECT siteid from tbl_siteinfo where sitename = ?";

    public int getSiteID(String sitename) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int siteid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITEID);
            pstmt.setString(1, sitename);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                siteid = rs.getInt("siteid") ;
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            siteid = -1;
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    siteid = -1;
                    e.printStackTrace();
                }
        }

        return siteid;
    }

    private static final String SQL_GETSITE_ENCODING = "SELECT encodeflag from tbl_siteinfo where siteid = ?";

    public int getSiteEncoding(int siteid) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int encoding = 1;   //默认UTF-8

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITE_ENCODING);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                encoding = rs.getInt(1) ;
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return encoding;
    }

    private static final String SQL_GETSITE_ATTRIBUTE = "SELECT sitelogo,banner,navigator from tbl_siteinfo where siteid = ?";

    public String getSiteStringAttribute(int siteid,String attribute_name) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String attribute_value = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITE_ATTRIBUTE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                attribute_value = rs.getString(attribute_name);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return attribute_value;
    }

    public int getSiteIntAttribute(int siteid,String attribute_name) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int attribute_value = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITE_ATTRIBUTE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                attribute_value = rs.getInt(attribute_name);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return attribute_value;
    }

    public List getPicSize(int siteid) throws SiteInfoException {
        List list = new LinkedList();

        return list;
    }

    private static final String SQL_GET_COLUMNID = "SELECT ColumnID from tbl_article where id = ?";

    private static final String SQL_GET_SITEID = "SELECT SiteID from tbl_column where id = ?";

    private static final String SQL_GET_SITENAME = "SELECT SiteName from tbl_siteinfo where siteid = ?";

    public String getSiteName(int articleID) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int columnID = 0;
        int siteID = 0;
        String siteName = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COLUMNID);
            pstmt.setInt(1, articleID);
            rs = pstmt.executeQuery();
            if (rs.next()) columnID = rs.getInt(1);
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_SITEID);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) siteID = rs.getInt(1);
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_SITENAME);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            if (rs.next()) siteName = rs.getString(1);
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            try {

                cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return siteName;
    }

    private static final String SQL_GETALLSITES = "SELECT * FROM TBL_SiteInfo ORDER BY BindFlag ASC,CreateDate DESC";

    public List getAllSiteInfo() throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        List list = new LinkedList();
        SiteInfo siteInfo;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETALLSITES);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

    private static final String SQL_GET_TOP8_SITE =
            "SELECT * FROM (SELECT * FROM tbl_siteinfo where sitetype=? and sitevalid=0 and siteid<>? ORDER BY createdate DESC) WHERE ROWNUM<=8";

    public List getTop8SiteInfo(int sitetype,int current_siteid) throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo = null;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_TOP8_SITE);
            pstmt.setInt(1,sitetype);
            pstmt.setInt(2,current_siteid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int getAllSearchSiteInfoNum(String search) throws SiteInfoException
    {
        String sql = "SELECT count(siteid) FROM TBL_SiteInfo WHERE (BindFlag = 1 OR BindFlag = 2) and sitename like '%"+search+"%' ORDER BY BindFlag ASC,CreateDate DESC";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        int countnum = 0;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                countnum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return countnum;
    }

    public int getAllSearchSiteInfoNum(String search,int sitetype,int current_siteid) throws SiteInfoException
    {
        String sql = "SELECT count(siteid) FROM TBL_SiteInfo WHERE (BindFlag = 1 OR " +
                "BindFlag = 2) and sitename like '%"+search+"%' and sitetype=? and (sitevalid=0 OR sitevalid=2) and siteid<>? ORDER BY BindFlag ASC,CreateDate DESC";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        int countnum = 0;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,sitetype);
            pstmt.setInt(2,current_siteid);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                countnum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return countnum;
    }

    private static final String SQL_GETALLSITE1 = "SELECT * FROM TBL_SiteInfo WHERE (BindFlag = 1 OR BindFlag = 2)  ORDER BY BindFlag ASC,CreateDate DESC";

    public List getAllSiteInfo(int resultnum,int startnum) throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo = null;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETALLSITE1);
            rs = pstmt.executeQuery();

            for(int i=0;i<startnum;i++){
                rs.next();
            }

            for(int i=0;i<resultnum && rs.next();i++)
            {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static final String SQL_GET_ALL_SAMSITE = "SELECT * FROM TBL_SiteInfo WHERE (BindFlag = 1 OR BindFlag = 2) and sitevalid=0 ORDER BY BindFlag ASC,CreateDate DESC";

    public List getAllSamSiteInfo(int resultnum,int startnum) throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo = null;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_SAMSITE);
            rs = pstmt.executeQuery();

            for(int i=0;i<startnum;i++){
                rs.next();
            }

            for(int i=0;i<resultnum && rs.next();i++)
            {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static final String SQL_GETALLSITE2 = "SELECT * FROM TBL_SiteInfo WHERE (BindFlag = 1 OR BindFlag = 2) and sitetype=? and (sitevalid=0 OR sitevalid=2) and siteid<>? ORDER BY BindFlag ASC,CreateDate DESC";

    public List getAllSiteInfo(int resultnum,int startnum,int sitetype,int current_siteid) throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo = null;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETALLSITE2);
            pstmt.setInt(1,sitetype);
            pstmt.setInt(2,current_siteid);
            rs = pstmt.executeQuery();

            for(int i=0;i<startnum;i++){
                rs.next();
            }

            for(int i=0;i<resultnum && rs.next();i++)
            {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getAllSearchSiteInfo(int resultnum,int startnum,String search) throws SiteInfoException
    {
        String sql = "SELECT * FROM TBL_SiteInfo WHERE (BindFlag = 1 OR BindFlag = 2) and sitename like '%"+search+"%' ORDER BY BindFlag ASC,CreateDate DESC";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo = null;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            for(int i=0;i<startnum;i++){
                rs.next();
            }

            for(int i=0;i<resultnum && rs.next();i++)
            {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getAllSearchSiteInfo(int resultnum,int startnum,String search,int sitetype,int current_siteid) throws SiteInfoException
    {
        String sql = "SELECT * FROM TBL_SiteInfo WHERE (BindFlag = 1 OR " +
                "BindFlag = 2) and sitename like '%"+search+"%' and sitetype=? and (sitevalid=0 OR sitevalid=2) and siteid<>? ORDER BY BindFlag ASC,CreateDate DESC";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo = null;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,sitetype);
            pstmt.setInt(2,current_siteid);
            rs = pstmt.executeQuery();

            for(int i=0;i<startnum;i++){
                rs.next();
            }

            for(int i=0;i<resultnum && rs.next();i++)
            {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static final String SQL_GETSPECIALSITE = "SELECT * FROM TBL_SiteInfo WHERE PubFlag = 1";

    public List getSpecialSiteInfo() throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new LinkedList();
        SiteInfo siteInfo;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSPECIALSITE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                siteInfo = load(rs);
                list.add(siteInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return list;
    }

    private static final String SQL_UPDATEVALID = "UPDATE TBL_SiteInfo SET sitevalid=? WHERE siteid = ?";

    private static final String SQL_UPDATE_SHAREFLAG = "UPDATE TBL_Column SET shareflag=? WHERE siteid = ?";

    public int updatesitevalid(int siteid,int valid) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 1;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_UPDATEVALID);
            pstmt.setInt(1,valid);
            pstmt.setInt(2,siteid);
            pstmt.executeUpdate();
            pstmt.close();

            if (valid == 2) {
                pstmt = conn.prepareStatement(SQL_UPDATE_SHAREFLAG);
                pstmt.setInt(1,1);
                pstmt.setInt(2,siteid);
                pstmt.executeUpdate();
                pstmt.close();
            }else {
                pstmt = conn.prepareStatement(SQL_UPDATE_SHAREFLAG);
                pstmt.setInt(1,0);
                pstmt.setInt(2,siteid);
                pstmt.executeUpdate();
                pstmt.close();
            }
        }
        catch (Throwable t) {
            errcode = -1;
            t.printStackTrace();
        }
        finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        return errcode;
    }

    private static final String SQL_DELETEINFO = "delete from tbl_siteinfo where siteid=?";

    //获取该站点下的所有栏目
    private static final String SQL_GETSITECOLUMN = "select id from tbl_column where siteid=?";

    //删除该站点下的所有栏目
    private static final String SQL_DELETECOLUMN = "delete from tbl_column where siteid=?";

    //删除该站点下的所有主机的ftp方式
    private static final String SQL_DELETEFTPINFO = "delete from tbl_siteipinfo where siteid=?";

    //删除该站点下的所有组下的用户
    private static final String SQL_DELETEGROUPMEMB = "delete  TBL_Group_Members where groupid in (select groupid from tbl_group where siteid=?)";
    //删除该站点下的所有组的权限
    private static final String SQL_DELETEGROUPRIGHT = "delete TBL_Group_rights where groupid in (select groupid from tbl_group where siteid=?)";
    //删除该站点下的所有组
    private static final String SQL_DELETEGROUP = "delete from tbl_group where siteid=?";

    //删除该站点下的用户权限
    private static final String SQL_DELETEMEMRIGHT = "delete  TBL_members_rights where userid in (select userid from tbl_members where siteid=?)";
    //删除该站点下的所有用户
    private static final String SQL_DELETEMEM = "delete from TBL_members where siteid=?";

    //删除模板中的标记
    private static final String SQL_SELETE_MARK = "delete from tbl_mark where siteid =?";

    public int remove(int siteid) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 1;

        //准备删除栏目相关的审核信息
        String SQL_DELETEAU = "delete from tbl_column_auditing_rules where columnid in ";
        //准备删除栏目相关的模版
        String SQL_DELETETEMPLATE = "delete from tbl_template where columnid in ";
        //准备删除该站点的文章
        String SQL_DELETETEARTICLE = "delete from tbl_article where columnid in ";

        try {
            try {
                String columns = "(";
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                //获取该站点下的栏目id
                pstmt = conn.prepareStatement(SQL_GETSITECOLUMN);
                pstmt.setInt(1, siteid);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    columns += rs.getInt("id") + ",";
                }
                columns = columns.substring(0, columns.lastIndexOf(",")) + ")";
                pstmt.close();

                //删除站点信息
                removeitem(conn, pstmt, SQL_DELETEINFO, siteid);

                //删除该站点下的栏目
                removeitem(conn, pstmt, SQL_DELETECOLUMN, siteid);

                //删除该站点下的Mark
                removeitem(conn, pstmt, SQL_SELETE_MARK, siteid);

                //删除该站点下的所有主机的ftp方式
                removeitem(conn, pstmt, SQL_DELETEFTPINFO, siteid);

                //删除该站点下的所有组下的用户
                removeitem(conn, pstmt, SQL_DELETEGROUPMEMB, siteid);

                //删除该站点下的所有组的权限
                removeitem(conn, pstmt, SQL_DELETEGROUPRIGHT, siteid);

                //删除该站点下的所有组
                removeitem(conn, pstmt, SQL_DELETEGROUP, siteid);

                //删除该站点下的用户权限
                removeitem(conn, pstmt, SQL_DELETEMEMRIGHT, siteid);

                //删除该站点下的所有用户
                removeitem(conn, pstmt, SQL_DELETEMEM, siteid);

                //准备删除栏目相关的审核信息
                SQL_DELETEAU += columns;
                pstmt = conn.prepareStatement(SQL_DELETEAU);
                pstmt.executeUpdate();
                pstmt.close();

                //准备删除栏目相关的模版
                SQL_DELETETEMPLATE += columns;
                pstmt = conn.prepareStatement(SQL_DELETETEMPLATE);
                pstmt.executeUpdate();
                pstmt.close();

                //准备删除该站点的文章
                SQL_DELETETEARTICLE += columns;
                pstmt = conn.prepareStatement(SQL_DELETETEARTICLE);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new SiteInfoException("Database exception: delete SiteInfo failed.");
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            errcode = -1;
            throw new SiteInfoException("Database exception: can't rollback?");
        }

        return errcode;
    }

    /*
    * 获取所有绑定及暂停的站点
    */
    private static final String SQL_GETALLSITE ="SELECT count(siteid) FROM TBL_SiteInfo WHERE BindFlag = 1 OR BindFlag = 2";

    public int getAllSiteInfoNum() throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;

        int countnum = 0;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETALLSITE);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                countnum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return countnum;
    }

    private static final String SQL_GET_ALL_SAMPLE_SITE = "SELECT count(siteid) FROM TBL_SiteInfo WHERE (BindFlag = 1 OR " +
            "BindFlag = 2) and sitetype=? and siteid<>? and (sitevalid=0 OR sitevalid=2) ORDER BY BindFlag ASC,CreateDate DESC";

    public int getAllSiteInfoNum(int sitetype,int current_siteid) throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;

        int countnum = 0;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALL_SAMPLE_SITE);
            pstmt.setInt(1,sitetype);
            pstmt.setInt(2,current_siteid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                countnum = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return countnum;
    }

    private static final String SQL_GET_SHARESITE_FLAG = "SELECT managesharesite FROM tbl_siteinfo where siteid=?";

    public int getShareSiteflag(int siteid) throws SiteInfoException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        int flag = 0;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_SHARESITE_FLAG);
            pstmt.setInt(1,siteid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
            {
                flag = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return flag;
    }

    void removeitem(Connection conn, PreparedStatement pstmt, String sql, int siteid) throws SQLException {
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, siteid);
        pstmt.executeUpdate();
        pstmt.close();
    }

    SiteInfo load(ResultSet rs) throws SQLException {
        SiteInfo siteInfo = new SiteInfo();
        siteInfo.setSiteid(rs.getInt("siteid"));
        siteInfo.setDomainName(rs.getString("sitename"));
        siteInfo.setImgeDir(rs.getInt("imagesdir"));
        siteInfo.setCssjsDir(rs.getInt("cssjsdir"));
        siteInfo.setTcFlag(rs.getInt("tcflag"));
        siteInfo.setBindFlag(rs.getInt("BindFlag"));
        siteInfo.setPubFlag(rs.getInt("PubFlag"));
        siteInfo.setDomainPic(rs.getString("sitepic"));
        siteInfo.setValidFlag(rs.getInt("sitevalid"));
        siteInfo.setSitetype(rs.getInt("sitetype"));
        siteInfo.setSamsiteid(rs.getInt("samsiteid"));
        siteInfo.setCopyright(rs.getString("copyright"));
        siteInfo.setTempnum(rs.getInt("sharetemplatenum"));
        siteInfo.setTitlepic(rs.getString("titlepic"));
        siteInfo.setVtitlepic(rs.getString("vtitlepic"));
        siteInfo.setSourcepic(rs.getString("sourcepic"));
        siteInfo.setAuthorpic(rs.getString("authorpic"));
        siteInfo.setContentpic(rs.getString("contentpic"));
        siteInfo.setSpecialpic(rs.getString("specialpic"));
        siteInfo.setProductpic(rs.getString("productpic"));
        siteInfo.setProductsmallpic(rs.getString("productsmallpic"));
        siteInfo.setTs_pic(rs.getString("ts_pic"));
        siteInfo.setS_pic(rs.getString("s_pic"));
        siteInfo.setMs_pic(rs.getString("ms_pic"));
        siteInfo.setM_pic(rs.getString("m_pic"));
        siteInfo.setMl_pic(rs.getString("ml_pic"));
        siteInfo.setL_pic(rs.getString("l_pic"));
        siteInfo.setTl_pic(rs.getString("tl_pic"));

        //System.out.println(rs.getString("sitepic"));
        return siteInfo;
    }
}