package com.bizwink.cms.toolkit.searchword;


import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchWordPeer implements ISearchWordManager {

    PoolServer cpool;

    public SearchWordPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ISearchWordManager getInstance() {
        return (ISearchWordManager) CmsServer.getInstance().getFactory().getSearchWordManager();
    }

    public List getAllsearchWord(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_searchword order by id asc");
            rs = pstmt.executeQuery();
            while(rs.next()){
                SearchWord searchWord = new SearchWord();
                searchWord=load(rs);
                list.add(searchWord);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }


    public List getAllsearchWord(String sql,int startIndex,int range){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();

        if(sql != null){
            sql = sql.replaceAll("@", "%");
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for(int i = 0; i < startIndex; i++){
                rs.next();
            }
            for(int i = 0; i < range && rs.next(); i++){
                SearchWord searchWord = new SearchWord();
                searchWord=load(rs);
                list.add(searchWord);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int getsearchWordCount(String sql){
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        if(sql != null){
            sql = sql.replaceAll("@","%");
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return num;
    }

    public int createSearchWord(SearchWord searchWord){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into tbl_searchword(id,cname,ename,sname,num,hotflag,tabooflag) values(?,?,?,?,?,?,?)");
            pstmt.setInt(1,sMgr.getSequenceNum("SEARCHWORDID"));
            pstmt.setString(2,searchWord.getCname());
            pstmt.setString(3,searchWord.getEname());
            pstmt.setString(4,searchWord.getSname());
            pstmt.setInt(5,1);
            pstmt.setInt(6,searchWord.getHotflag());
            pstmt.setInt(7,searchWord.getTabooflag());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }

    public SearchWord getSearchWordById(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        SearchWord searchWord = new SearchWord();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_searchword where id = ?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                searchWord=load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(conn != null)
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
        }
        return searchWord;
    }


    public SearchWord getSearchWordByCname(String cname){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        SearchWord searchWord = new SearchWord();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_searchword where cname = ?");
            pstmt.setString(1, cname);
            rs = pstmt.executeQuery();
            while(rs.next()){
                searchWord=load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(conn != null)
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
        }
        return searchWord;
    }

    public void updateSearchWord(SearchWord searchWord,int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_searchword set cname = ?,ename = ?,sname = ?,hotflag=?,tabooflag=? where id = ?");
            pstmt.setString(1,searchWord.getCname());
            pstmt.setString(2,searchWord.getEname());
            pstmt.setString(3,searchWord.getSname());
            pstmt.setInt(4,searchWord.getHotflag());
            pstmt.setInt(5,searchWord.getTabooflag());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public void delSearchWord(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_searchword where id = ?");
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    SearchWord load(ResultSet rs) throws Exception {
        SearchWord searchWord = new SearchWord();
        try {
            searchWord.setId(rs.getInt("id"));
            searchWord.setCname(rs.getString("cname"));
            searchWord.setEname(rs.getString("ename"));
            searchWord.setSname(rs.getString("sname"));
            searchWord.setNum(rs.getInt("num"));
            searchWord.setHotflag(rs.getInt("hotflag"));
            searchWord.setTabooflag(rs.getInt("tabooflag"));
        }catch (Exception e) {
             System.out.println(e.toString());
             e.printStackTrace();
        }
        return searchWord;
    }
}
