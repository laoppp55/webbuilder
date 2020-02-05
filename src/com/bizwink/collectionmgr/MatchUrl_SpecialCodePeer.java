package com.bizwink.collectionmgr;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2007-11-20
 * Time: 14:05:03
 */
public class MatchUrl_SpecialCodePeer implements IMatchUrl_SpecialCodeManager {
    PoolServer cpool;

    public MatchUrl_SpecialCodePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IMatchUrl_SpecialCodeManager getInstance() {
        return CmsServer.getInstance().getFactory().getMatchUrl_SpecialCodeManager();
    }

    private final static String getSpCode = "select * from sp_special_code where starturlid=?";

    public List getSpCode(int basicId) {
        List spCodeList = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(getSpCode);
            pstmt.setInt(1, basicId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MatchUrl_SpecialCode musc = new MatchUrl_SpecialCode();
                musc.setSc_id(rs.getInt("id"));
                musc.setStartUrlId(rs.getInt("starturlid"));
                musc.setSt(rs.getString("st"));
                musc.setEt(rs.getString("et"));
                spCodeList.add(musc);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return spCodeList;
    }

    private final static String addSpCode = "insert into sp_special_code(starturlid,st,et,siteid,id)values(?, ?, ?, ?, ?)";

    public void addSpCode(int siteid,MatchUrl_SpecialCode musc) {
        Connection con = null;
        PreparedStatement pstmt=null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(addSpCode);
            pstmt.setInt(1, musc.getStartUrlId());
            pstmt.setString(2, musc.getSt());
            pstmt.setString(3, musc.getEt());
            pstmt.setInt(4,siteid);
            int iid = getMaxId("select max(id) from sp_special_code");
            System.out.println("iid=" + iid);
            pstmt.setInt(5,iid);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try{
                con.rollback();
            }catch(NullPointerException rollse){
                rollse.printStackTrace();
            }catch(SQLException rse){
                rse.printStackTrace();
            }se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    private final static String updateSpCode = "update sp_special_code set st=?,et=? where id=?";

    public void updateSpCode(MatchUrl_SpecialCode musc) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(updateSpCode);
            pstmt.setString(1, musc.getSt());
            pstmt.setString(2, musc.getEt());
            pstmt.setInt(3, musc.getSc_id());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try{
                con.rollback();
            }catch(NullPointerException rollse){
                rollse.printStackTrace();
            }catch(SQLException rse){
                rse.printStackTrace();
            }se.printStackTrace();
        } finally {
            if (con != null) try {
                if (!con.isClosed()) con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void delSpCode(int sp_id) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement("delete from sp_special_code where id=?");
            pstmt.setInt(1, sp_id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try{
                con.rollback();
            }catch(NullPointerException rollse){
                rollse.printStackTrace();
            }catch(SQLException rse){
                rse.printStackTrace();
            }se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    private final static String getMtUrl = "select * from sp_match_url where starturlid=?";

    public List getMtUrl(int basicId) {
        List mtUrlList = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(getMtUrl);
            pstmt.setInt(1, basicId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MatchUrl_SpecialCode mtUrl = new MatchUrl_SpecialCode();
                mtUrl.setMu_id(rs.getInt("id"));
                mtUrl.setStartUrlId(rs.getInt("starturlid"));
                mtUrl.setMatchUrl(rs.getString("matchurl"));
                mtUrlList.add(mtUrl);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return mtUrlList;
    }

    private final static String addMtUrl = "insert into sp_match_url(starturlid,matchurl,siteid,id)values(?, ?, ?, ?)";

    public void addMtUrl(int siteid,MatchUrl_SpecialCode musc) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(addMtUrl);
            pstmt.setInt(1, musc.getStartUrlId());
            pstmt.setString(2, musc.getMatchUrl());
            pstmt.setInt(3,siteid);
            pstmt.setInt(4,getMaxId("select max(id) from sp_match_url"));
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try{
                con.rollback();
            }catch(NullPointerException rollse){
                rollse.printStackTrace();
            }catch(SQLException rse){
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    private final static String updateMtUrl = "update sp_match_url set matchurl=? where id=?";

    public void updateMtUrl(MatchUrl_SpecialCode musc) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement(updateMtUrl);
            pstmt.setString(1, musc.getMatchUrl());
            pstmt.setInt(2, musc.getMu_id());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try{
                con.rollback();
            }catch(NullPointerException rollse){
                rollse.printStackTrace();
            }catch(SQLException rse){
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void delMtUrl(int mu_id) {
        Connection con = null;
        PreparedStatement pstmt;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(true);
            pstmt = con.prepareStatement("delete from sp_match_url where id=?");
            pstmt.setInt(1, mu_id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException se) {
            try{
                con.rollback();
            }catch(NullPointerException rollse){
                rollse.printStackTrace();
            }catch(SQLException rse){
                rse.printStackTrace();
            }
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public MatchUrl_SpecialCode getSc(int sc_id){
        Connection con=null;
        MatchUrl_SpecialCode sc=new MatchUrl_SpecialCode();
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            con=cpool.getConnection();
            pstmt=con.prepareStatement("select * from sp_special_code where id=?");
            pstmt.setInt(1,sc_id);
            rs=pstmt.executeQuery();
            if(rs.next()){
                sc.setSc_id(rs.getInt("id"));
                sc.setStartUrlId(rs.getInt("starturlid"));
                sc.setSt(rs.getString("st"));
                sc.setEt(rs.getString("et"));
            }
            rs.close();
            pstmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            if(con!=null)try{
                if(!con.isClosed())con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return sc;
    }
    public MatchUrl_SpecialCode getMu(int mu_id){
        MatchUrl_SpecialCode mu=new MatchUrl_SpecialCode();
        Connection con=null;
        PreparedStatement pstmt;
        ResultSet rs;
        try{
            con=cpool.getConnection();
            pstmt=con.prepareStatement("select * from sp_match_url where id=?");
            pstmt.setInt(1,mu_id);
            rs=pstmt.executeQuery();
            if(rs.next()){
                mu.setMu_id(rs.getInt("id"));
                mu.setStartUrlId(rs.getInt("starturlid"));
                mu.setMatchUrl(rs.getString("matchurl"));
            }
            rs.close();
            pstmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }finally{
            if(con!=null)try{
                if(!con.isClosed())con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return mu;
    }
    public int getMaxId(String sql){
        int maxId = 0;
        Connection con = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (con != null) try {
                if(!con.isClosed())con.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return maxId + 1;
    }
}
