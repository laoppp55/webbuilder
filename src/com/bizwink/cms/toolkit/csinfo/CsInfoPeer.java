package com.bizwink.cms.toolkit.csinfo;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jhon on 2016/11/16.
 */
public class CsInfoPeer implements ICsInfoManager {

    PoolServer cpool;

    public CsInfoPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ICsInfoManager getInstance() {
        return (ICsInfoManager) CmsServer.getInstance().getFactory().getCsInfoManager();
    }

    private static String INSERT_CSROOMINFO = "insert into CS_ROOM_INFO(" +
            "SITEID,ROOM_NAME,CATLOG_CODE,ROOM_TYPE,ROOM_NUM,ROOM_WAY,ROOM_SIZE,INIT_PRICE,BED_NUM,BED_TYPE,FLOOR,TOILET,TV,AIRCONDITIONER,BATHROOM,BEDCLOTHES,AMENITIES,SPECIALSERVICE,ID)" +
            " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?)";

    private static String INSERT_CSROOMPIC = "insert into CS_ROOM_PIC(ID,ROOM_ID,PIC_URL,DESCRIPTION,TITLE)values(csinfo_id.NEXTVAL, ?, ?, ?, ?)";


    public void insertCsinfo(CsInfo csinfo, List list) throws CsInfoException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ID = 0;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement("select csinfo_id.NEXTVAL from dual");
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ID = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
                System.out.println("ID=" + ID);

                pstmt = conn.prepareStatement(INSERT_CSROOMINFO);
                pstmt.setInt(1, csinfo.getSiteid());
                pstmt.setString(2, csinfo.getROOM_NAME());
                pstmt.setString(3, csinfo.getCATLOG_CODE());
                pstmt.setInt(4, csinfo.getROOM_TYPE());
                pstmt.setInt(5, csinfo.getROOM_NUM());
                pstmt.setString(6, csinfo.getROOM_WAY());
                pstmt.setInt(7, csinfo.getROOM_SIZE());
                pstmt.setFloat(8, csinfo.getINIT_PRICE());
                pstmt.setInt(9, csinfo.getBED_NUM());
                pstmt.setString(10, csinfo.getBED_TYPE());
                pstmt.setInt(11, csinfo.getFLOOR());
                pstmt.setInt(12, csinfo.getFLOOR());
                pstmt.setInt(13, csinfo.getTV());
                pstmt.setInt(14, csinfo.getAIRCONDITIONER());
                pstmt.setInt(15, csinfo.getBATHROOM());
                pstmt.setInt(16, csinfo.getBEDCLOTHES());
                pstmt.setInt(17, csinfo.getAMENITIES());
                pstmt.setInt(18, csinfo.getSPECIALSERVICE());
                pstmt.setInt(19, ID);
                pstmt.executeUpdate();
                pstmt.close();

                //入cs_room_pic表
                for (int i = 0; i < list.size(); i++) {
                    CsInfo csInfo = (CsInfo) list.get(i);
                    pstmt = conn.prepareStatement(INSERT_CSROOMPIC);
                    pstmt.setInt(1, ID);
                    pstmt.setString(2, csInfo.getRoom_pic_url());
                    pstmt.setString(3, csInfo.getRoom_pic_description());
                    pstmt.setString(4, csInfo.getRoom_pic_title());
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String GET_CSROOM_LIST = "select * from cs_room_info where siteid = ? order by id desc";

    public List getcsroomList(int siteID) throws CsInfoException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CsInfo csInfo = new CsInfo();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_CSROOM_LIST);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                csInfo = load(rs);
                list.add(csInfo);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_CSINFO_LIST = "select * from cs_room_info where siteid = ? order by id desc";

    public List getCurrentcsroomList(int siteID, int startrow, int range) throws CsInfoException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CsInfo csInfo = new CsInfo();
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_CSINFO_LIST);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++)
                rs.next();

            for (int i = 0; i < range && rs.next(); i++) {
                csInfo = load(rs);
                list.add(csInfo);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }


    private CsInfo load(ResultSet rs) {
        CsInfo csInfo = new CsInfo();
        try {
            csInfo.setId(rs.getInt("id"));
            csInfo.setSiteid(rs.getInt("siteid"));
            csInfo.setROOM_NAME(rs.getString("room_name"));
            csInfo.setCATLOG_CODE(rs.getString("CATLOG_CODE"));
            csInfo.setROOM_TYPE(rs.getInt("ROOM_TYPE"));
            csInfo.setROOM_NUM(rs.getInt("ROOM_NUM"));
            csInfo.setROOM_WAY(rs.getString("ROOM_WAY"));
            csInfo.setROOM_SIZE(rs.getInt("ROOM_SIZE"));
            csInfo.setINIT_PRICE(rs.getFloat("INIT_PRICE"));
            csInfo.setBED_NUM(rs.getInt("BED_NUM"));
            csInfo.setBED_TYPE(rs.getString("BED_TYPE"));
            csInfo.setFLOOR(rs.getInt("FLOOR"));
            csInfo.setTOILET(rs.getInt("TOILET"));
            csInfo.setTV(rs.getInt("TV"));
            csInfo.setAMENITIES(rs.getInt("AIRCONDITIONER"));
            csInfo.setBATHROOM(rs.getInt("BATHROOM"));
            csInfo.setBEDCLOTHES(rs.getInt("BEDCLOTHES"));
            csInfo.setAMENITIES(rs.getInt("AMENITIES"));
            csInfo.setSPECIALSERVICE(rs.getInt("SPECIALSERVICE"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csInfo;
    }

    public void deleteCsInfo(int id) throws CsInfoException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement("delete from cs_room_info where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }


    public void deleteCsInfoPic(int id) throws CsInfoException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement("delete from cs_room_pic where room_id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }
}