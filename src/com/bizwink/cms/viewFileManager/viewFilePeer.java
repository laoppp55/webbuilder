package com.bizwink.cms.viewFileManager;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class viewFilePeer implements IViewFileManager {
    PoolServer cpool;

    public viewFilePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IViewFileManager getInstance() {
        return CmsServer.getInstance().getFactory().getViewFileManager();
    }

    private static final String SQL_CREATEVIEW_FOR_ORACLE =
            "INSERT INTO tbl_viewfile (siteid,type,chinesename,editor,lockflag,notes," +
                    "content,createdate,updatedate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATEVIEW_FOR_MSSQL =
            "INSERT INTO tbl_viewfile (siteid,type,chinesename,editor,lockflag,notes," +
                    "content,createdate,updatedate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATEVIEW_FOR_MYSQL =
            "INSERT INTO tbl_viewfile (siteid,type,chinesename,editor,lockflag,notes," +
                    "content,createdate,updatedate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int create(ViewFile vf) throws viewFileException {
        int ID = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            try {
                String cname = vf.getChineseName();
                if (cname != null) cname = StringUtil.gb2isoindb(cname);
                String notes = vf.getNotes();
                if (notes != null) notes = StringUtil.gb2isoindb(notes);
                String content = vf.getContent();
                if (content != null) content = StringUtil.gb2isoindb(content);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATEVIEW_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATEVIEW_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATEVIEW_FOR_MYSQL);

                pstmt.setInt(1, vf.getSiteID());
                pstmt.setInt(2, vf.getType());
                pstmt.setString(3, cname);
                pstmt.setString(4, vf.getEditor());
                pstmt.setInt(5, 0);
                pstmt.setString(6, notes);
                if (content != null)
                    DBUtil.setBigString(cpool.getType(), pstmt, 7, content);
                else
                    pstmt.setNull(7, java.sql.Types.LONGVARCHAR);
                pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equalsIgnoreCase("oracle")) {
                    ID = sequnceMgr.getSequenceNum("ViewFile");
                    pstmt.setInt(10, ID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new viewFileException("Database exception: create viewfile failed.");
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
            throw new viewFileException("Database exception: can't rollback?");
        }
        return ID;
    }

    private static String UPDATE_VIEWFILE =
            "update tbl_viewfile set editor = ?,content = ?,chinesename = ?,notes = ?,updatedate = ? where id = ?";

    public void update(ViewFile vf) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            String cname = vf.getChineseName();
            if (cname != null) cname = StringUtil.gb2isoindb(cname);
            String notes = vf.getNotes();
            if (notes != null) notes = StringUtil.gb2isoindb(notes);
            String content = vf.getContent();
            if (content != null) content = StringUtil.gb2isoindb(content);

            //System.out.println(vf.getID());
            //System.out.println(content);

            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            System.out.println("welcome to get a connection!!!!");
            pstmt = conn.prepareStatement(UPDATE_VIEWFILE);
            pstmt.setString(1, vf.getEditor());
            if (content != null)
                DBUtil.setBigString(cpool.getType(), pstmt, 2, content);
            else
                pstmt.setNull(2, java.sql.Types.LONGVARCHAR);
            pstmt.setString(3, cname);
            pstmt.setString(4, notes);
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(6, vf.getID());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String DELETE_VIEW = "delete tbl_viewfile where id = ?";

    public int delete(int id) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 1;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(DELETE_VIEW);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (NullPointerException e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
            }
            finally {
                try {
                    if (conn != null) {
                        cpool.freeConnection(conn);
                    }
                } catch (Exception e) {
                    errcode = -1;
                    System.out.println("close the connection failed in createUser method");
                }
            }
        }
        catch (Exception ee) {
            errcode = -1;
            ee.printStackTrace();
        }

        return errcode;
    }

    private static String GETALLVIEWFILE =
            "select * from tbl_viewfile where siteID = ?";

    public List getViewFileList(int siteID, int startIndex, int numResult) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GETALLVIEWFILE);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                list.add(load_noContent(rs));
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String GET_VIEWFILENUM =
            "select count(*) from tbl_viewfile where siteid = ?";

    public int getViewFileNUM(int siteID) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;
        int count = 0;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_VIEWFILENUM);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    private static String GETAVIEWFILE = "select * from tbl_viewfile where id = ?";

    public ViewFile getAViewFile(int id) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;
        ViewFile viewfile = new ViewFile();
        ResultSet rs;

        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement(GETAVIEWFILE);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                viewfile = load(rs);
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return viewfile;
    }

    public List getViewFileC(int siteid, int type) throws viewFileException {
        List list = new ArrayList();
        ViewFile viewfile;
        String sqlstr;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        sqlstr = "select ID,siteID,Type,Chinesename,Editor,Lockflag,Notes,Createdate,Updatedate " +
                "from tbl_viewfile where (siteid = ? OR siteid=-1) and type = ? order by Chinesename";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, type);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                viewfile = load_noContent(rs);
                list.add(viewfile);
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

    private ViewFile load(ResultSet rs) {
        ViewFile viewfile = new ViewFile();
        try {
            viewfile.setID(rs.getInt("ID"));
            viewfile.setSiteID(rs.getInt("siteID"));
            viewfile.setType(rs.getInt("Type"));
            viewfile.setContent(rs.getString("Content"));
            viewfile.setChineseName(rs.getString("Chinesename"));
            viewfile.setEditor(rs.getString("Editor"));
            viewfile.setLockFlag(rs.getInt("Lockflag"));
            viewfile.setNotes(rs.getString("Notes"));
            viewfile.setCreateDate(rs.getTimestamp("Createdate"));
            viewfile.setLastUpdated(rs.getTimestamp("Updatedate"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return viewfile;
    }

    private ViewFile load_noContent(ResultSet rs) {
        ViewFile viewfile = new ViewFile();

        try {
            viewfile.setID(rs.getInt("ID"));
            viewfile.setSiteID(rs.getInt("siteID"));
            viewfile.setType(rs.getInt("Type"));
            viewfile.setChineseName(rs.getString("Chinesename"));
            viewfile.setEditor(rs.getString("Editor"));
            viewfile.setLockFlag(rs.getInt("Lockflag"));
            viewfile.setNotes(rs.getString("Notes"));
            viewfile.setCreateDate(rs.getTimestamp("Createdate"));
            viewfile.setLastUpdated(rs.getTimestamp("Updatedate"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return viewfile;
    }

    private static final String SQL_CREATE_NAVIGATOR_FOR_ORACLE =
            "INSERT INTO tbl_navigator (cname,content,fcontent,notes,createdate,id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_NAVIGATOR_FOR_MSSQL =
            "INSERT INTO tbl_navigator (cname,content,fcontent,notes,createdate) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_NAVIGATOR_FOR_MYSQL =
            "INSERT INTO tbl_navigator (cname,content,fcontent,notes,createdate,id) VALUES (?, ?, ?, ?, ?, ?)";

    public int createNavigator(navigator nv) throws viewFileException {
        int ID = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            try {
                String cname = nv.getName();
                if (cname != null) cname = StringUtil.gb2isoindb(cname);
                String notes = nv.getNotes();
                if (notes != null) notes = StringUtil.gb2isoindb(notes);
                String content = nv.getContent();
                if (content != null) content = StringUtil.gb2isoindb(content);
                String fcontent = nv.getfContent();
                if (fcontent != null) fcontent = StringUtil.gb2isoindb(fcontent);

                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_NAVIGATOR_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_NAVIGATOR_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_NAVIGATOR_FOR_MYSQL);

                pstmt.setString(1, cname);
                pstmt.setString(2, content);
                pstmt.setString(3, fcontent);
                pstmt.setString(4, notes);
                pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equalsIgnoreCase("oracle")) {
                    ID = sequnceMgr.getSequenceNum("Navigator");
                    pstmt.setInt(6, ID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else  if (cpool.getType().equalsIgnoreCase("mssql")) {
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                conn.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new viewFileException("Database exception: create viewfile failed.");
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
            throw new viewFileException("Database exception: can't rollback?");
        }
        return ID;
    }

    private static String GET_ALL_NAVIGATOR = "select id,cname,notes,createdate from tbl_navigator";

    public List getNavigatorList(int startIndex, int numResult) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_NAVIGATOR);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++)
                rs.next();

            for (int i = 0; i < numResult && rs.next(); i++) {
                list.add(load_Navigator_noContent(rs));
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String GET_NAVIGATOR_COUNT = "select count(id) from tbl_navigator";

    public int getNavigatorCount() throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_NAVIGATOR_COUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) count = rs.getInt(1);
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    private static String GET_NAVIGATOR = "select cname,content,fcontent,notes,createdate,id from tbl_navigator where id = ?";

    public navigator getNavigator(int id) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;
        navigator nv = new navigator();
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_NAVIGATOR);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                nv = loadNavigator(rs);
            }
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return nv;
    }

    private static String UPDATE_NAVIGATOR =
            "update tbl_navigator set content = ?,fcontent = ?,notes = ?,cname = ? where id = ?";

    public void update(navigator nv) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            String cname = nv.getName();
            if (cname != null) cname = StringUtil.gb2isoindb(cname);
            String notes = nv.getNotes();
            if (notes != null) notes = StringUtil.gb2isoindb(notes);
            String content = nv.getContent();
            if (content != null) content = StringUtil.gb2isoindb(content);
            String fcontent = nv.getfContent();
            if (fcontent != null) fcontent = StringUtil.gb2isoindb(fcontent);

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(UPDATE_NAVIGATOR);
            pstmt.setString(1, content);
            pstmt.setString(2, fcontent);
            pstmt.setString(3, notes);
            pstmt.setString(4, cname);
            pstmt.setInt(5, nv.getID());
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String DELETE_NAVIGATOR ="delete tbl_navigator where id = ?";

    public void deleteNavigator(int id) throws viewFileException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(DELETE_NAVIGATOR);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
            }
            finally {
                try {
                    if (conn != null) {
                        cpool.freeConnection(conn);
                    }
                }
                catch (Exception e) {
                    System.out.println("close the connection failed in createUser method");
                }
            }
        }
        catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private navigator loadNavigator(ResultSet rs) {
        navigator nv = new navigator();
        try {
            nv.setID(rs.getInt("ID"));
            nv.setName(rs.getString("cname"));
            nv.setNotes(rs.getString("Notes"));
            nv.setContent(rs.getString("content"));
            nv.setfContent(rs.getString("fcontent"));
            nv.setCreateDate(rs.getTimestamp("Createdate"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

    private navigator load_Navigator_noContent(ResultSet rs) {
        navigator nv = new navigator();

        try {
            nv.setID(rs.getInt("ID"));
            nv.setName(rs.getString("cname"));
            nv.setNotes(rs.getString("Notes"));
            nv.setCreateDate(rs.getTimestamp("Createdate"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }

}