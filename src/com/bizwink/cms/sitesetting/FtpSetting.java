package com.bizwink.cms.sitesetting;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class FtpSetting implements IFtpSetManager {
    PoolServer cpool;

    public FtpSetting(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IFtpSetManager getInstance() {
        return CmsServer.getInstance().getFactory().getFtpSetManager();
    }

    private static final String SQL_CREATESITE_FOR_ORACLE = "INSERT INTO tbl_siteipinfo (siteid,siteip,docpath,ftpuser," +
            "ftppasswd,publishway,Status,ftptype,SiteName,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATESITE_FOR_MSSQL = "INSERT INTO tbl_siteipinfo (siteid,siteip,docpath,ftpuser," +
            "ftppasswd,publishway,Status,ftptype,SiteName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATESITE_FOR_MYSQL = "INSERT INTO tbl_siteipinfo (siteid,siteip,docpath,ftpuser," +
            "ftppasswd,publishway,Status,ftptype,SiteName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void create(FtpInfo ftpInfo) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATESITE_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATESITE_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATESITE_FOR_MYSQL);
                pstmt.setInt(1, ftpInfo.getSiteid());
                pstmt.setString(2, ftpInfo.getIp());
                pstmt.setString(3, ftpInfo.getDocpath());
                pstmt.setString(4, ftpInfo.getFtpuser());
                pstmt.setString(5, ftpInfo.getFtppwd());
                pstmt.setInt(6, ftpInfo.getPublishway());
                pstmt.setInt(7, ftpInfo.getStatus());
                pstmt.setInt(8,ftpInfo.getFtptype());
                pstmt.setString(9, ftpInfo.getSiteName());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(10, sequenceMgr.nextID("SiteIPInfo"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new SiteInfoException("Database exception: create One Siteinfomation failed.");
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
            throw new SiteInfoException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATESITEINFO =
            "update tbl_siteipinfo set siteIP=?,sitename=?,docpath=?,ftpuser=?,ftppasswd=?,ftptype=?,publishway=?,status=? where ID=?";

    public void update(FtpInfo ftpInfo) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATESITEINFO);

                pstmt.setString(1, ftpInfo.getIp());
                pstmt.setString(2, ftpInfo.getSiteName());
                pstmt.setString(3, ftpInfo.getDocpath());
                pstmt.setString(4, ftpInfo.getFtpuser());
                pstmt.setString(5, ftpInfo.getFtppwd());
                pstmt.setInt(6,ftpInfo.getFtptype());
                pstmt.setInt(7, ftpInfo.getPublishway());
                pstmt.setInt(8, ftpInfo.getStatus());
                pstmt.setInt(9, ftpInfo.getID());

                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new SiteInfoException("Database exception: update SiteInfo failed.");
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
            throw new SiteInfoException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETEINFO = "delete from tbl_siteipinfo where ID = ?";

    public void remove(int ID) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_DELETEINFO);
                pstmt.setInt(1, ID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new SiteInfoException("Database exception: delete SiteInfo failed.");
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
            throw new SiteInfoException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GETSITE = "SELECT * from tbl_siteipinfo where ID = ?";

    public FtpInfo getFtpInfo(int ID) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        FtpInfo ftpInfo = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITE);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ftpInfo = load(rs);
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
        return ftpInfo;
    }

    private static final String SQL_GETDOMAINSERVER = "SELECT * FROM TBL_SiteIPInfo WHERE SiteID = ? AND Status = 1";

    public List getFtpInfos(int siteID) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new LinkedList();
        FtpInfo ftpInfo;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETDOMAINSERVER);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ftpInfo = load(rs);
                list.add(ftpInfo);
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

    private static final String SQL_GET_OTHER_DOMAINSERVER = "SELECT * FROM TBL_SiteIPInfo WHERE SiteID = ? AND (Status = 2 OR Status = 0)";

    public List getOtherFtpInfos(int siteID) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new LinkedList();
        FtpInfo ftpInfo;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_OTHER_DOMAINSERVER);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ftpInfo = load(rs);
                list.add(ftpInfo);
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

    //读取某站点指定SITEIP的站点信息
    public List getFtpInfos(int siteID, String siteIPs[]) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new LinkedList();
        FtpInfo ftpInfo;

        String siteIPstr = "";
        if (siteIPs != null) {
            for (int i = 0; i < siteIPs.length; i++)
                siteIPstr = siteIPstr + "'" + siteIPs[i] + "',";
            if (siteIPstr.length() > 0)
                siteIPstr = siteIPstr.substring(0, siteIPstr.length() - 1);
        }

        String SQL_GET_DOMAIN_SERVER = "SELECT * FROM TBL_SiteIPInfo WHERE SiteID = " + siteID + " AND SiteIP IN (" + siteIPstr + ")";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_DOMAIN_SERVER);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ftpInfo = load(rs);
                list.add(ftpInfo);
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

    private static final String SQL_GETSITES =  "SELECT * from tbl_siteipinfo WHERE siteID = ? ORDER BY Status DESC";

    public List getFtpInfoList(int siteID) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new LinkedList();
        FtpInfo ftpInfo;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETSITES);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ftpInfo = load(rs);
                list.add(ftpInfo);
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

    private static final String SQL_GetSitesByStatus = "SELECT * from tbl_siteipinfo WHERE siteID = ? AND Status = ? ORDER BY Status DESC";

    public List getFtpInfoListForWML(int siteID, int status) throws SiteInfoException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new LinkedList();
        FtpInfo ftpInfo;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GetSitesByStatus);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, status);                 //status=2 is for WAP publish
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ftpInfo = load(rs);
                list.add(ftpInfo);
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

    FtpInfo load(ResultSet rs) throws SQLException {
        FtpInfo ftpInfo = new FtpInfo();
        ftpInfo.setID(rs.getInt("ID"));
        ftpInfo.setIp(rs.getString("siteIP"));
        ftpInfo.setSiteName(rs.getString("sitename"));
        ftpInfo.setDocpath(rs.getString("docpath"));
        ftpInfo.setFtpuser(rs.getString("ftpuser"));
        ftpInfo.setFtppwd(rs.getString("ftppasswd"));
        ftpInfo.setFtptype(rs.getInt("ftptype"));
        ftpInfo.setSiteid(rs.getInt("siteid"));
        ftpInfo.setPublishway(rs.getInt("publishway"));
        ftpInfo.setStatus(rs.getInt("Status"));
        return ftpInfo;
    }
}