package com.bizwink.webapps.security;

import java.io.*;
import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

/**
 * An implementation of auth database peer using instantdb, an embedded java
 * SQL database.
 *
 * @author	Xiaobin Xiang
 */

public class webAuthPeer implements IWebAuthManager {
  PoolServer cpool;

  /**
   * DATABASE QUERIES *
   */
  private static final String SQL_AUTHORIZE =
          "SELECT userpwd, nickname, siteid FROM tbl_members WHERE userID=?";

  private static final String SQL_Site =
          "SELECT sitename,imagesdir,showarticleflag FROM TBL_Siteinfo WHERE siteid = ?";

  public webAuthPeer(PoolServer cpool) {
    this.cpool = cpool;
  }

  public static IWebAuthManager getInstance() {
    return (IWebAuthManager) CmsServer.getInstance().getFactory().getWebAuthManager();
  }

  private static final String SQL_GET_Members_Rights =
          "select distinct rightid from tbl_group_members inner join tbl_group_rights " +
          "on tbl_group_members.groupid = tbl_group_rights.groupid " +
          "where tbl_group_members.userid = ?" +
          "union (select distinct rightid from tbl_members_rights where userid =?)";

  public webAuth getAuth(String userid, String password) throws webUnauthedException {
    int siteid = -2;
    String sitename = "";
    int imgflag = 0;
    int artflag = 0;
    if (userid == null || password == null) {
      throw new webUnauthedException("username or password is null");
    }

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String nickname = null;
    webPermissionSet permissionSet = null;

    try {
      conn = cpool.getConnection();

      pstmt = conn.prepareStatement(SQL_AUTHORIZE);
      pstmt.setString(1, userid);
      rs = pstmt.executeQuery();
      //check user's name and password
      if (rs.next()) {
        String getPassword = rs.getString("userpwd");
        nickname = rs.getString("nickname");
        siteid = rs.getInt("siteid");
        if (!password.equals(getPassword)) {
          throw new webUnauthedException(" password is not right");
        }
      } else {
        throw new webUnauthedException(" no user name");
      }
      rs.close();
      pstmt.close();

      //get the siteid for the userid
      if (!userid.equalsIgnoreCase("admin")) {
        pstmt = conn.prepareStatement(SQL_Site);
        pstmt.setInt(1, siteid);
        rs = pstmt.executeQuery();
        rs.next();
        sitename = rs.getString("sitename");
        imgflag = rs.getInt("imagesdir");
        artflag = rs.getInt("showarticleflag");
        rs.close();
        pstmt.close();
      }

      if (!userid.equalsIgnoreCase("admin")) {
        //get user's operate right and save it to permissionset
        pstmt = conn.prepareStatement(SQL_GET_Members_Rights);
        pstmt.setString(1, userid);
        pstmt.setString(2, userid);
        rs = pstmt.executeQuery();
        permissionSet = new webPermissionSet();
        int rightid = 0;
        while (rs.next()) {
          List cList = new ArrayList();
          webPermission permission = new webPermission();
          rightid = rs.getInt("rightid");
          permission.setRightID(rightid);
          permissionSet.add(permission);
        }
        rs.close();
        pstmt.close();
      }

    } catch (webUnauthedException e) {
      throw e;
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (conn != null) {
        try {
          cpool.freeConnection(conn);; // close the pooled connection
        } catch (Exception e) {
          System.out.println("Error in closing the pooled connection " + e.toString());
        }
      }
    }
    return new webAuth(userid, sitename, siteid, imgflag, artflag, permissionSet);
  }

  public webAuth getAnonymousAuth() {
    return new webAuth(null, null, -2, 0, 0, null);
  }
}
