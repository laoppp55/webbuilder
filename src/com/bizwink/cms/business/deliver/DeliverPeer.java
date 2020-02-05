package com.bizwink.cms.business.deliver;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;

public class DeliverPeer implements IDeliverManager {
    PoolServer cpool;

    public DeliverPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IDeliverManager getInstance() {
        return CmsServer.getInstance().getFactory().getDeliverManager();
    }

    private static final String SQL_CREATE_PROVINCE =
            "INSERT INTO EN_Province (ProvName,OrderID,EmsFee) VALUES (?, ?, ?)";

    private static final String SQL_CREATE_CITY =
            "INSERT INTO EN_City (ProvID, CityName, OrderID) VALUES (?, ?, ?)";

    private static final String SQL_CREATE_ZONE =
            "INSERT INTO EN_Zone (CityID, ZoneName, OrderID) VALUES (?, ?, ?)";

    public void create(Deliver deliver) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                int orderID = 0;
                String SQL_SELECT_MAX_ORDERID = "SELECT MAX(ORDERID) FROM";
                if (deliver.getCityType() == 1)
                    SQL_SELECT_MAX_ORDERID = SQL_SELECT_MAX_ORDERID + " EN_Province";
                else if (deliver.getCityType() == 2)
                    SQL_SELECT_MAX_ORDERID = SQL_SELECT_MAX_ORDERID + " EN_City WHERE ProvID = " + deliver.getProvID();
                else
                    SQL_SELECT_MAX_ORDERID = SQL_SELECT_MAX_ORDERID + " EN_Zone WHERE CityID = " + deliver.getCityID();
                pstmt = conn.prepareStatement(SQL_SELECT_MAX_ORDERID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    orderID = rs.getInt(1);
                }
                orderID++;
                deliver.setOrderID(orderID);
                rs.close();
                pstmt.close();

                if (deliver.getCityType() == 1) {
                    pstmt = conn.prepareStatement(SQL_CREATE_PROVINCE);
                    pstmt.setString(1, deliver.getProvName());
                    pstmt.setInt(2, deliver.getOrderID());
                    pstmt.setFloat(3, deliver.getEmsFee());
                } else if (deliver.getCityType() == 2) {
                    pstmt = conn.prepareStatement(SQL_CREATE_CITY);
                    pstmt.setInt(1, deliver.getProvID());
                    pstmt.setString(2, deliver.getCityName());
                    pstmt.setInt(3, deliver.getOrderID());
                } else {
                    pstmt = conn.prepareStatement(SQL_CREATE_ZONE);
                    pstmt.setInt(1, deliver.getCityID());
                    pstmt.setString(2, deliver.getZoneName());
                    pstmt.setInt(3, deliver.getOrderID());
                }
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATE_PROVINCE =
            "UPDATE EN_Province SET ProvName = ?,EmsFee = ?,OrderID = ? WHERE ID = ?";

    private static final String SQL_UPDATE_CITY =
            "UPDATE EN_City SET CityName = ?,OrderID = ? WHERE ID = ?";

    private static final String SQL_UPDATE_ZONE =
            "UPDATE EN_Zone SET ZoneName = ?,OrderID = ? WHERE ID = ?";

    //更新栏目审核规则
    public void update(Deliver deliver) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (deliver.getCityType() == 1) {
                    pstmt = conn.prepareStatement(SQL_UPDATE_PROVINCE);
                    pstmt.setString(1, deliver.getProvName());
                    pstmt.setFloat(2, deliver.getEmsFee());
                    pstmt.setInt(3, deliver.getOrderID());
                    pstmt.setInt(4, deliver.getProvID());
                } else if (deliver.getCityType() == 2) {
                    pstmt = conn.prepareStatement(SQL_UPDATE_CITY);
                    pstmt.setString(1, deliver.getCityName());
                    pstmt.setInt(2, deliver.getOrderID());
                    pstmt.setInt(3, deliver.getCityID());
                } else {
                    pstmt = conn.prepareStatement(SQL_UPDATE_ZONE);
                    pstmt.setString(1, deliver.getZoneName());
                    pstmt.setInt(2, deliver.getOrderID());
                    pstmt.setInt(3, deliver.getZoneID());
                }
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETE_PROVINCE = "DELETE FROM EN_Province WHERE ID = ?";
    private static final String SQL_DELETE_CITY = "DELETE FROM EN_City WHERE ID = ?";
    private static final String SQL_DELETE_ZONE = "DELETE FROM EN_Zone WHERE ID = ?";

    public void delete(Deliver deliver) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (deliver.getCityType() == 1) {
                    pstmt = conn.prepareStatement(SQL_DELETE_PROVINCE);
                    pstmt.setInt(1, deliver.getProvID());
                } else if (deliver.getCityType() == 2) {
                    pstmt = conn.prepareStatement(SQL_DELETE_CITY);
                    pstmt.setInt(1, deliver.getCityID());
                } else {
                    pstmt = conn.prepareStatement(SQL_DELETE_ZONE);
                    pstmt.setInt(1, deliver.getZoneID());
                }
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GET_PROVINCE = "SELECT * FROM EN_Province ORDER BY OrderID";
    private static final String SQL_GET_CITY = "SELECT * FROM EN_City WHERE ProvID = ? ORDER BY OrderID";
    private static final String SQL_GET_ZONE = "SELECT * FROM EN_Zone WHERE CityID = ? ORDER BY OrderID";

    public List getCityList(Deliver deliver) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();

            if (deliver.getCityType() == 1) {
                pstmt = conn.prepareStatement(SQL_GET_PROVINCE);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    deliver = new Deliver();
                    deliver.setProvID(rs.getInt("ID"));
                    deliver.setProvName(rs.getString("ProvName"));
                    deliver.setOrderID(rs.getInt("OrderID"));
                    deliver.setEmsFee(rs.getFloat("EmsFee"));
                    list.add(deliver);
                }
            } else if (deliver.getCityType() == 2) {
                pstmt = conn.prepareStatement(SQL_GET_CITY);
                pstmt.setInt(1, deliver.getProvID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    deliver = new Deliver();
                    deliver.setCityID(rs.getInt("ID"));
                    deliver.setCityName(rs.getString("CityName"));
                    deliver.setOrderID(rs.getInt("OrderID"));
                    list.add(deliver);
                }
            } else {
                pstmt = conn.prepareStatement(SQL_GET_ZONE);
                pstmt.setInt(1, deliver.getCityID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    deliver = new Deliver();
                    deliver.setZoneID(rs.getInt("ID"));
                    deliver.setZoneName(rs.getString("ZoneName"));
                    deliver.setOrderID(rs.getInt("OrderID"));
                    list.add(deliver);
                }
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

    private static final String SQL_GET_ZONE_POSTFEE ="SELECT a.ID as ZoneID,a.ZoneName,b.ID as FeeID,b.pFee,b.kFee,b.uFee,b.oFee FROM " +
            "EN_Zone a left join EN_PostFee b ON a.ID = b.ZoneID WHERE a.CityID = ? ORDER BY a.OrderID";

    public List getCityFeeList(Deliver deliver) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            deliver.setCityType(2);
            List cityList = getCityList(deliver);

            for (int i = 0; i < cityList.size(); i++) {
                deliver = (Deliver) cityList.get(i);
                String cityName = deliver.getCityName();
                deliver.setCityName(cityName);
                list.add(deliver);

                pstmt = conn.prepareStatement(SQL_GET_ZONE_POSTFEE);
                pstmt.setInt(1, deliver.getCityID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    deliver = new Deliver();

                    deliver.setZoneID(rs.getInt("ZoneID"));
                    deliver.setZoneName(rs.getString("ZoneName"));
                    deliver.setFeeID(rs.getInt("FeeID"));
                    deliver.setPFee(rs.getFloat("pFee"));
                    deliver.setKFee(rs.getFloat("kFee"));
                    deliver.setUFee(rs.getFloat("uFee"));
                    deliver.setOFee(rs.getFloat("oFee"));

                    list.add(deliver);
                }
                rs.close();
                pstmt.close();
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

    private static final String SQL_CREATE_FEE = "INSERT INTO EN_PostFee(ProvID,ZoneID,pFee,kFee,uFee,oFee) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_FEE = "UPDATE EN_PostFee set pFee = ?,kFee = ?,uFee = ?,oFee = ? WHERE ID = ?";

    public void createFee(List feeList) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                for (int i = 0; i < feeList.size(); i++) {
                    Deliver deliver = (Deliver) feeList.get(i);
                    int feeID = deliver.getFeeID();
                    if (feeID == 0) {
                        pstmt = conn.prepareStatement(SQL_CREATE_FEE);
                        pstmt.setInt(1, deliver.getProvID());
                        pstmt.setInt(2, deliver.getZoneID());
                        pstmt.setFloat(3, deliver.getPFee());
                        pstmt.setFloat(4, deliver.getKFee());
                        pstmt.setFloat(5, deliver.getUFee());
                        pstmt.setFloat(6, deliver.getOFee());
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (feeID > 0) {
                        pstmt = conn.prepareStatement(SQL_UPDATE_FEE);
                        pstmt.setFloat(1, deliver.getPFee());
                        pstmt.setFloat(2, deliver.getKFee());
                        pstmt.setFloat(3, deliver.getUFee());
                        pstmt.setFloat(4, deliver.getOFee());
                        pstmt.setInt(5, feeID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_CREATE_EXP_CORP = "INSERT INTO EN_ExpCorp (ExpName) VALUES (?)";

    public void createExpCorp(String expName) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_CREATE_EXP_CORP);
                pstmt.setString(1, expName);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATE_EXP_CORP = "UPDATE EN_ExpCorp SET ExpName = ? WHERE ID = ?";

    public void updateExpCorp(int expID, String expName) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_EXP_CORP);
                pstmt.setString(1, expName);
                pstmt.setInt(2, expID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_DELETE_EXP_CORP = "DELETE FROM EN_ExpCorp WHERE ID = ?";

    public void deleteExpCorp(int expID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_DELETE_EXP_CORP);
                pstmt.setInt(1, expID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GET_EXP_CORP = "SELECT * FROM EN_ExpCorp";

    public List getExpCorpList() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement(SQL_GET_EXP_CORP);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("ID") + "," + rs.getString("ExpName"));
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

    private static final String SQL_CREATE_EXPRESS_FEE = "INSERT INTO EN_ExpFee (ExpID,ZoneID,shouFee,xuFee,kg20Fee,kg50Fee,kg100Fee,kg300Fee) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_EXPRESS_FEE = "UPDATE EN_ExpFee set shouFee = ?,xuFee = ?,kg20Fee = ?,kg50Fee = ?,kg100Fee = ?,kg300Fee = ? WHERE ID = ?";

    public void createExpFee(List feeList) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                for (int i = 0; i < feeList.size(); i++) {
                    Deliver deliver = (Deliver) feeList.get(i);
                    int feeID = deliver.getFeeID();
                    if (feeID == 0) {
                        pstmt = conn.prepareStatement(SQL_CREATE_EXPRESS_FEE);
                        pstmt.setInt(1, deliver.getExpID());
                        pstmt.setInt(2, deliver.getZoneID());
                        pstmt.setFloat(3, deliver.getShouFee());
                        pstmt.setFloat(4, deliver.getXuFee());
                        pstmt.setFloat(5, deliver.getKg20Fee());
                        pstmt.setFloat(6, deliver.getKg50Fee());
                        pstmt.setFloat(7, deliver.getKg100Fee());
                        pstmt.setFloat(8, deliver.getKg300Fee());
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (feeID > 0) {
                        pstmt = conn.prepareStatement(SQL_UPDATE_EXPRESS_FEE);
                        pstmt.setFloat(1, deliver.getShouFee());
                        pstmt.setFloat(2, deliver.getXuFee());
                        pstmt.setFloat(3, deliver.getKg20Fee());
                        pstmt.setFloat(4, deliver.getKg50Fee());
                        pstmt.setFloat(5, deliver.getKg100Fee());
                        pstmt.setFloat(6, deliver.getKg300Fee());
                        pstmt.setInt(7, feeID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
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
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GET_ZONE_EXPRESS_FEE ="SELECT a.ID as ZoneID,a.ZoneName,b.ID as FeeID,b.shouFee,b.xuFee,b.kg20Fee,b.kg50Fee,b.kg100Fee,b.kg300Fee " +
            "FROM EN_Zone a left join EN_ExpFee b ON a.ID = b.ZoneID AND b.ExpID = ? WHERE a.CityID = ? ORDER BY a.OrderID";

    public List getExpFeeList(Deliver deliver) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        int expID = deliver.getExpID();

        try {
            conn = cpool.getConnection();

            deliver.setCityType(2);
            List cityList = getCityList(deliver);

            for (int i = 0; i < cityList.size(); i++) {
                deliver = (Deliver) cityList.get(i);
                String cityName = deliver.getCityName();
                deliver.setCityName(cityName);
                list.add(deliver);

                pstmt = conn.prepareStatement(SQL_GET_ZONE_EXPRESS_FEE);
                pstmt.setInt(1, expID);
                pstmt.setInt(2, deliver.getCityID());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    deliver = new Deliver();

                    deliver.setZoneID(rs.getInt("ZoneID"));
                    deliver.setZoneName(rs.getString("ZoneName"));
                    deliver.setFeeID(rs.getInt("FeeID"));
                    deliver.setShouFee(rs.getFloat("shouFee"));
                    deliver.setXuFee(rs.getFloat("xuFee"));
                    deliver.setKg20Fee(rs.getFloat("kg20Fee"));
                    deliver.setKg50Fee(rs.getFloat("kg50Fee"));
                    deliver.setKg100Fee(rs.getFloat("kg100Fee"));
                    deliver.setKg300Fee(rs.getFloat("kg300Fee"));

                    list.add(deliver);
                }
                rs.close();
                pstmt.close();
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
}
