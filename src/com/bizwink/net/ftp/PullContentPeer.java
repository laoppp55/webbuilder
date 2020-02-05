package com.bizwink.net.ftp;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.server.*;

public class PullContentPeer implements IPullContentManager{
    PoolServer cpool;

    static List list = new ArrayList();
    static String filepath = "";
    static String localpath = "";
    static String[] filename;

    public PullContentPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IPullContentManager getInstance() {
        return CmsServer.getInstance().getFactory().getPullContentManager();
    }

    private static final String GET_SITE = "SELECT siteid,SiteIP,DocPath,FtpUser,FtpPasswd from TBL_SiteIPinfo WHERE SiteID = ?";
    public PullContent getSiteInfo(int siteid){
      Connection conn = null;
      PreparedStatement pstmt;
      ResultSet rs;
      PullContent pullcontent = null;

      try {
          conn = cpool.getConnection();
          pstmt = conn.prepareStatement(GET_SITE);
          pstmt.setInt(1, siteid);
          rs = pstmt.executeQuery();

          while(rs.next()) {
              pullcontent = load(rs);
          }

          rs.close();
          pstmt.close();
      } catch (Throwable t) {
          t.printStackTrace();
      } finally {
          if (conn != null) {
              try {
                   // close the pooled connection
                  cpool.freeConnection(conn);
              } catch (Exception e) {
                  System.out.println( "Error in closing the pooled connection "+e.toString());
              }
          }
      }
      return pullcontent;
    }

    private static final String GET_PUBLISH_WAY = "SELECT siteid,PublishWay from TBL_SiteIPinfo WHERE SiteID = ?";
    public int getPublishWay(int siteid){
      Connection conn = null;
      PreparedStatement pstmt;
      ResultSet rs;
      int publishway = 0;

      try {
          conn = cpool.getConnection();
          pstmt = conn.prepareStatement(GET_PUBLISH_WAY);
          pstmt.setInt(1, siteid);
          rs = pstmt.executeQuery();

          while(rs.next()) {
              publishway = rs.getInt("publishway");
          }

          rs.close();
          pstmt.close();
      } catch (Throwable t) {
          t.printStackTrace();
      } finally {
          if (conn != null) {
              try {
                   // close the pooled connection
                  cpool.freeConnection(conn);
              } catch (Exception e) {
                  System.out.println( "Error in closing the pooled connection "+e.toString());
              }
          }
      }
      return publishway;
    }

    private static final String GET_SITENAME = "SELECT siteid,SiteName from TBL_Siteinfo WHERE SiteID = ?";
    public String getSiteName(int siteid){
      Connection conn = null;
      PreparedStatement pstmt;
      ResultSet rs;
      String sitename = "";

      try {
          conn = cpool.getConnection();
          pstmt = conn.prepareStatement(GET_SITENAME);
          pstmt.setInt(1, siteid);
          rs = pstmt.executeQuery();

          while(rs.next()) {
              sitename = rs.getString("sitename");
          }

          rs.close();
          pstmt.close();
      } catch (Throwable t) {
          t.printStackTrace();
      } finally {
          if (conn != null) {
              try {
                   // close the pooled connection
                  cpool.freeConnection(conn);
              } catch (Exception e) {
                  System.out.println( "Error in closing the pooled connection "+e.toString());
              }
          }
      }
      return sitename;
    }

    PullContent load(ResultSet rs) throws SQLException {
      PullContent pullcontent = new PullContent();
      try {
        pullcontent.setSiteID(rs.getInt("SiteID"));
        pullcontent.setSiteIP(rs.getString("SiteIP"));
        pullcontent.setDocPath(rs.getString("docpath"));
        pullcontent.setFtpUser(rs.getString("ftpuser"));
        pullcontent.setFtpPasswd(rs.getString("ftppasswd"));

      } catch (Exception e) {
        System.out.println(e.toString());
        e.printStackTrace();
      }
      return pullcontent;
    }

}
