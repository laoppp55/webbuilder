package com.bizwink.cms.security;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.news.*;

public class ColumnUserPeer implements IColumnUserManager {
    PoolServer cpool;

    public ColumnUserPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IColumnUserManager getInstance() {
        return CmsServer.getInstance().getFactory().getColumnUserManager();
    }

    private static final String SQL_GET_USER_COLUMNS =
            "SELECT distinct a.ColumnID,b.Cname FROM TBL_Members_Rights a,TBL_Column b " +
                    "WHERE a.UserID = ? and a.RightID = ? and a.ColumnID > 0 and a.columnID = b.ID";

    public List getUserColsFromTBL_Members_Rights(String userID, int rightID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        ColumnUser columnUser;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_USER_COLUMNS);
            pstmt.setString(1, userID);
            pstmt.setInt(2, rightID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                columnUser = loadColumn(rs);
                list.add(columnUser);
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

    private static final String GET_Columns_For_User =
            "select distinct columnid from tbl_group_members a, tbl_group_rights b, tbl_group c " +
                    "where c.siteid = ? and a.userid = ? union (select distinct columnid from " +
                    "tbl_members a,tbl_members_rights b where a.siteid = ? and b.userid = ? and a.userid = b.userid)";

    public List getUserColumns(String userid, int siteid) throws UnauthedException {
        if (userid == null) {
            throw new UnauthedException("username is null");
        }

        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_Columns_For_User);
            pstmt.setInt(1, siteid);
            pstmt.setString(2, userid);
            pstmt.setInt(3, siteid);
            pstmt.setString(4, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int columnID = rs.getInt(1);
                if (columnID > 0) {
                    try {
                        list.add(columnMgr.getColumn(columnID));
                    }
                    catch (ColumnException ex) {
                        ex.printStackTrace();
                    }
                }
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
                    System.out.println(
                            "Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String GET_Columns_For_Group =
            "select distinct columnid from tbl_group_rights b, tbl_group a where " +
                    "a.siteid = ? and a.groupid = ? and a.groupid = b.groupid";

    public List getGroupColumns(int gid, int siteid) throws UnauthedException {
        if (gid == 0) {
            throw new UnauthedException("group number is null");
        }


        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_Columns_For_Group);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, gid);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                try {
                    Column column = columnMgr.getColumn(rs.getInt("columnid"));
                    list.add(column);
                } catch (ColumnException ex) {
                    ex.printStackTrace();
                }
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
                    System.out.println(
                            "Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    User loadUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setID(rs.getString("userid"));
        try {
            user.setNickName(StringUtil.gb2iso(rs.getString("nickname")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setMobilePhone(rs.getString("mobilephone"));
        return user;
    }

    ColumnUser loadColumn(ResultSet rs) throws SQLException {
        ColumnUser columnUser = new ColumnUser();
        try {
            columnUser.setColumnCname(rs.getString("cname"));
            columnUser.setColumnID(rs.getInt("columnid"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return columnUser;
    }
}