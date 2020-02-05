package com.bizwink.webapps.address;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-9
 * Time: 8:35:09
 * To change this template use File | Settings | File Templates.
 */
public class AddressPeer implements IAddressManager {
    PoolServer cpool;

    public AddressPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IAddressManager getInstance() {
        return CmsServer.getInstance().getFactory().getAddressManager();
    }


    //a获得所有省份 add by feixiang 2010-12-09
    public List getProvinceList(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from en_province");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Province province = new Province();
                province.setId(rs.getInt("id"));
                province.setProvincename(rs.getString("provname"));
                list.add(province);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //获得所有省份 add by feixiang 2010-12-09
    public List getProvinceListByCountry(String code){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from en_province where code=?");
            pstmt.setString(1,code);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Province province = new Province();
                province.setId(rs.getInt("id"));
                province.setProvincename(rs.getString("provname"));
                list.add(province);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //get city list by province name add by feixiang 2010-12-09
    public List getCityList(String provincename){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int provinceid =  0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from en_province where provname = ?");
            pstmt.setString(1,provincename);
            rs = pstmt.executeQuery();
            while(rs.next()){
                provinceid = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("select * from en_city where provid = ?");
            pstmt.setInt(1,provinceid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                City city = new City();
                city.setId(rs.getInt("id"));
                city.setProvid(rs.getInt("provid"));
                city.setCityname(rs.getString("cityname"));
                list.add(city);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //get city list by province name add by feixiang 2010-12-09
    public List getCityListByProvid(int provid){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int provinceid =  0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from en_city where provid = ?");
            pstmt.setInt(1,provid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                City city = new City();
                city.setId(rs.getInt("id"));
                city.setProvid(rs.getInt("provid"));
                city.setCityname(rs.getString("cityname"));
                list.add(city);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //get zone list by city name add by feixiang 2010-12-09
    public List getZoneList(String cityname){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int cityid =  0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from en_city where cityname = ?");
            pstmt.setString(1,cityname);
            rs = pstmt.executeQuery();
            while(rs.next()){
                cityid = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("select * from en_zone where cityid = ?");
            pstmt.setInt(1,cityid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Zone zone = new Zone();
                zone.setId(rs.getInt("id"));
                zone.setCityid(rs.getInt("cityid"));
                zone.setZonename(rs.getString("zonename"));
                list.add(zone);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    //get zone list by city name add by feixiang 2010-12-09
    public List getZoneListByCityid(int cityid){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from en_zone where cityid = ?");
            pstmt.setInt(1,cityid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Zone zone = new Zone();
                zone.setId(rs.getInt("id"));
                zone.setCityid(rs.getInt("cityid"));
                zone.setZonename(rs.getString("zonename"));
                list.add(zone);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }
}
