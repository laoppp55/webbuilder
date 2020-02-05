package com.bizwink.webapps.questions;

import java.util.*;
import java.sql.*;
import java.text.*;
import java.util.Date;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.webapps.register.*;


/**
 * Title:        CRM server
 * Description:  Internet Portal Server
 * Copyright:    Copyright (c) 2005
 * Company:
 *
 * @author Peter Song
 * @version 1.0
 */

public class questionManagerImpl implements IQuestionManager {
    PoolServer cpool;

    public questionManagerImpl(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IQuestionManager getInstance() {
        return (IQuestionManager) CmsServer.getInstance().getFactory().getQuestionManager();
    }
    /**
     * 改变fawu_wenti 表中anw_status的状态   表示该问题已经到期 不在接受回答
     * @param id
     * @throws wenbaException
     */
    public void changeanwStatus_wenti(int id) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_answerstatus ="update fawu_wenti set anw_status = 1 where id = "+id+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_answerstatus);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }
    /**
     * 提交回答时在用户信息表中记录回答总数   types=1  增加回答提问的数码（user_anwnum）
     *                                  type=2  增加被选为最佳回答的数目user_anwnum_ok）
     */
    // public void changeUseranwnum(int userid,int types) throws wenbaException {
    //    Connection conn = null;
    //       PreparedStatement pstmt;
    //        ResultSet rs;
    //        String SQL_UPDATECOLUMN_userinfo = "";
    //        try {
    //        	if(types==1){
    //       		SQL_UPDATECOLUMN_userinfo ="update userinfo set user_anwnum = user_anwnum+1 where user_id = "+userid+" "; 
    //       	}
    //        	if(types==2){
    //       		SQL_UPDATECOLUMN_userinfo ="update userinfo set user_anwnum_ok = user_anwnum_ok+1 where user_id = "+userid+" "; 
    //       	}
    //         try {
    //              conn = cpool.getConnection();
    //              conn.setAutoCommit(false);
    //              pstmt = conn.prepareStatement(SQL_UPDATECOLUMN_userinfo);
    //              pstmt.executeUpdate();
    //              pstmt.close();
    //              conn.commit();
    //          } catch (Exception e) {
    //              e.printStackTrace();
    //              conn.rollback();
    //              throw new wenbaException("Database exception: update column failed.");
    //        } finally {
    //             if (conn != null) {
    //                try {
    //                    cpool.freeConnection(conn);
    //                } catch (Exception e) {
    //                    System.out.println("Error in closing the pooled connection " + e.toString());
    //               }
    //           }
    //        }
    //    }
    //    catch (SQLException e) {
    //        throw new wenbaException("Database exception: can't rollback?");
    //    }
    //}
    /**
     * 改变 answernum （提问的答案数量每多一个回答加一）的值  
     * String SQL_UPDATECOLUMN_answer ="update fawu_wenti set status="+stat+" ,answernum = answernum+1 where id = "+id+" ";
     * int stat,
     *
     */
    public void changeQuestion(int id) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_UPDATECOLUMN_answer ="update fawu_wenti set answernum = answernum+1 where id = "+id+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN_answer);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }

    //改变提问的status（0未解决  1已解决 ）的值
    public void changeQuestionStatus(int id) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_UPDATECOLUMN_answer ="update  fawu_wenti set status=1 where  id = "+id+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN_answer);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }
    //改变回答的ans_status（1 表示该回答被选为最佳答案）的值
    public void changeAnwStatus(int id) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_UPDATECOLUMN_answer ="update  fawu_anwser set anw_status=1 where  id = "+id+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN_answer);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }


    /**
     * 得到投票数最多的答案的  id
     */
    public answer getOneansid(int ID){
        answer an = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            an = new answer();
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from  fawu_anwser where qid =? order by votenum desc ");
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            while(rs.next()){
                an.setId(rs.getInt("id"));
                count = count + 1;
                if (count >= 1) break;
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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
        return an;
    }
    /**
     * 更新回答的得票数目
     */

    public void changePinglunNum(int id) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_UPDATECOLUMN_pinglun ="update fawu_anwser set votenum = votenum+1 where id = "+id+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN_pinglun);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }


    /**
     *   查询显示首页一级分类列表及ID
     */
    private static final String SQL_SELECTcNAME = "SELECT id,cname FROM fawu_wenti_column where parentid=0";

    public List getCname() throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_SELECTcNAME);
            wenbaImpl wenba;
            while(rs.next()){
                wenba = new wenbaImpl();
                wenba.setCName(rs.getString("cname"));
                wenba.setID(rs.getInt("id"));
                list.add(wenba);
            }
            //System.out.print("cname="+ rs.getString("cname"));
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 提问问题保存入库
     *
     */
    private static final String SQL_INSERT_TIWEN = "INSERT INTO fawu_wenti(question,email,createdate,ipaddress,province,city,area,picpath,emailnotify,columnid,cname,xuanshang,id,status,title,username,userid,filepath,anw_status,dianjinum,user_id_huida) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public void addWenti(wenti wen)throws wenbaException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequeceManager = SequencePeer.getInstance();
        int nextid = sequeceManager.getSequenceNum("fawu");
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_INSERT_TIWEN);
            pstmt.setString(1, wen.getQuestion());
            pstmt.setString(2, wen.getEmail());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(4,wen.getIpaddress());
            pstmt.setString(5, wen.getProvince());
            pstmt.setString(6, wen.getCity());
            pstmt.setString(7, wen.getArea());
            pstmt.setString(8, wen.getPicpath());
            pstmt.setInt(9, wen.getEmailnotify());
            pstmt.setInt(10, wen.getColumnid());
            pstmt.setString(11, wen.getCname());
            pstmt.setInt(12, wen.getXuanshang());
            pstmt.setInt(13, nextid);
            pstmt.setInt(14, 0);
            pstmt.setString(15, wen.getTitles());
            pstmt.setString(16, wen.getUsername());
            pstmt.setInt(17, wen.getUserid());
            pstmt.setString(18, wen.getFilepath());
            pstmt.setInt(19, 0);
            pstmt.setInt(20, 0);
            pstmt.setString(21, wen.getUserid_hd());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        }catch (Throwable t) {
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
    }

    /**
     * 获取某个分类下面前10个最新问题,stats=0表示已经回答的问题，stats=1表示未回答的问题
     */
    public List getTop10Questions(int ID,int stats){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        try{
            conn = cpool.getConnection();

            if(ID==0){
                pstmt = conn.prepareStatement("select id from fawu_wenti where status=? order by createdate desc");
                pstmt.setInt(1, stats);
            }else{
                pstmt = conn.prepareStatement("select id from fawu_wenti where status=? and columnid = ? order by createdate desc");
                pstmt.setInt(1, stats);
                pstmt.setInt(2, ID);
            }
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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


    /**
     * 获取某个分类下所有未解决问题  status = 0 未解决
     */
    public List getAllQuestions0(int ID,int stats){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from fawu_wenti where status=? and columnid = ? order by createdate desc");
            pstmt.setInt(1, stats);
            pstmt.setInt(2, ID);
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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
    /**
     * 获取某个分类下面某个地区前10个最新问题,stats=0表示已经回答的问题，stats=1表示未回答的问题
     */
    public List getTop10Questions_difang(int ID,int stats,String pro){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from fawu_wenti where status="+stats+" and columnid = "+ID+" and province='"+pro+"' order by createdate desc");
            // pstmt.setInt(1, stats);
            //pstmt.setInt(2, ID);
            //pstmt.setString(3, pro);
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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
    /**
     * 获取某个地区下的所有最新问题(分页显示),stats=0表示已经回答的问题，stats=1表示未回答的问题
     */
    public List getProQuestions(int end,int begin,int stats,String prov,String keys,int id){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  status="+stats+" and province='"+prov+"' and columnid = "+id+" and title like '%"+keys+"%' order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setFilepath(rs.getString("filepath"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    //查询某个地区下的 最新提问问题的 总数 
    public List getProQuestionsPagenums(int stat,String pro) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select id from fawu_wenti where  status="+stat+"  and province='"+pro+"'";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 获取某个地区下的所有0回答问题(分页显示)
     */
    public List getProQuestion0ans(int end,int begin,String prov,String keys,int id){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where answernum = 0 and province='"+prov+"' and columnid="+id+" and title like '%"+keys+"%' order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setFilepath(rs.getString("filepath"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    //查询某个地区下的0回答提问问题的 总数 
    public List getProQuestionsPagenum(String pro) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select id from fawu_wenti where answernum=0  and province='"+pro+"'";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    /**
     * 根据关键字搜索某个分类下面前10个最新问题,stats=0表示已经回答的问题，stats=1表示未回答的问题
     */
    public List getTop10QuestionsSousuo(int ID,int stats,String skey){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id from fawu_wenti where status="+stats+" and columnid = "+ID+" and title like '%"+skey+"%' order by createdate desc");
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 获取某个分类下面前10个最新问题,答案个数是零
     */
    public List getTop10Questions0Answer(int ID) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            if(ID==0){
                pstmt = conn.prepareStatement("select id from fawu_wenti where answernum=0 order by createdate desc");
            }else{
                pstmt = conn.prepareStatement("select id from fawu_wenti where columnid = ? and answernum=0 order by createdate desc");
                pstmt.setInt(1, ID);
            }
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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
    /**
     * 获取某个分类下面某个地区前10个最新问题,答案个数是零
     */
    public List getpro10Questions0Answer(int ID ,String pro) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from fawu_wenti where columnid = ? and answernum=0 and province='"+pro+"' order by createdate desc");
            pstmt.setInt(1, ID);

            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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
    /**
     * 获取地区下的某个分类下面前10个最新问题,答案个数是零
     */
    public List getTop10Questions0Answer(int ID,String prov) {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from fawu_wenti where columnid = ? and province = ? and answernum=0  order by createdate desc");
            pstmt.setInt(1, ID);
            pstmt.setString(2, prov);
            rs = pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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
    /**
     *
     * 根据关键字搜索某个分类下面前10个最新问题,答案个数是零
     */
    public List getTop10Questions0AnswerSousuo(int ID,String sKey){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id from fawu_wenti where answernum=0 and columnid = "+ID+" and title like '%"+sKey+"%' order by createdate desc");
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    /**
     * 查询本周前十条记录记录（按积分排行查询）
     */
    public List getTop8QuestionsXuanshang(int ID){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            if(ID==0){
                rs = stmt.executeQuery("select id from fawu_wenti where createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by xuanshang desc");
            }else{
                rs = stmt.executeQuery("select id from fawu_wenti where columnid = "+ID+" and createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by xuanshang desc");
            }
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 查询本周前十条记录记录
     */
    public List getTop8QuestionsWenti(int ID){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            if(ID==0){
                rs = stmt.executeQuery("select id from fawu_wenti where createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by createdate desc");
            }else{
                rs = stmt.executeQuery("select id from fawu_wenti where columnid = "+ID+" and createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by createdate desc");
            }
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 10) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 按点击率查询问题ID
     */
    public List getQuestionsdianji(int ID){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            if(ID==0){
                rs = stmt.executeQuery("select id from fawu_wenti order by dianjinum desc");
            }else{
                rs = stmt.executeQuery("select id from fawu_wenti where columnid = "+ID+" order by dianjinum desc");
            }
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                count = count + 1;
                if (count >= 8) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 查询有图片的问题（点击率最高的）
     */
    public List getQuestions_pic(int ID){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            if(ID==0){
                rs = stmt.executeQuery("select id from fawu_wenti where picpath is not null order by dianjinum desc");
            }else{
                rs = stmt.executeQuery("select id from fawu_wenti where columnid = "+ID+" and picpath is not null order by dianjinum desc");
            }
            while(rs.next()){
                list.add(rs.getInt("id")+"");
                //count = count + 1;
                //if (count >= 8) break;
            }
            rs.close();
            stmt.close();

        }catch (Throwable t) {
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


    /**
     * 特定栏目下搜索与关键字相匹配的问题（status = 0 未解决问题  status = 1 已解决问题）
     */
    public List getSousuoQuestionsPid(int end,int begin,int ID,int stats,String keys,String pro){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = null;
        if(pro==null||pro==""||pro.equals(null)||pro.equals("")){
            SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  status="+stats+" and title like '%"+keys+"%' and columnid="+ID+"  order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";
        }else{
            SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  status="+stats+" and title like '%"+keys+"%' and columnid="+ID+" and province='"+pro+"' order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";
        }
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setFilepath(rs.getString("filepath"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    /**
     * 在某一地区下搜索特定栏目下与关键字相匹配的问题的总数(pro 为空时在特定栏目下与关键字相匹配的问题的总数)
     */
    public List getSousuoQuestionsPidnum(int ID,int stat,String keys,String pro) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = null;
        if(pro==null||pro.equals("")){
            SQL_WENTI_SHOW = "select id from fawu_wenti where status="+stat+" and columnid="+ID+" and title like '%"+keys+"%'";
        }else{
            SQL_WENTI_SHOW = "select id from fawu_wenti where status="+stat+" and columnid="+ID+" and title like '%"+keys+"%' and province='"+pro+"' ";
        }
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 在某一地区下某个栏目下搜索与关键字相匹配的0回答问题(pro=null时  在某个栏目下搜索与关键字相匹配的0回答问题)
     */
    public List getSousuoQuestionsPid0an(int end,int begin,int ID,String keys,String pro){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  answernum=0 and title like '%"+keys+"%' and columnid="+ID+" and province='"+pro+"' order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";
        String SQL_WENTI_SHOW1 = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  answernum=0 and title like '%"+keys+"%' and columnid="+ID+"  order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            if(pro==null||"".equals(pro)){
                rs = stmt.executeQuery(SQL_WENTI_SHOW1);
            }else{
                rs = stmt.executeQuery(SQL_WENTI_SHOW);
            }

            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setFilepath(rs.getString("filepath"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    /**
     * 在某一地区下某个栏目下搜索与关键字相匹配的问题的总数（pro为空时  搜索某个栏目下与关键字相匹配的问题的总数）
     */
    public List getSousuoQuestionsPidnum0an(int ID,String keys,String pro) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = null;
        if(pro==null||"".equals(pro)){
            SQL_WENTI_SHOW = "select id from fawu_wenti where answernum=0 and columnid="+ID+" and title like '%"+keys+"%' ";
        }else{
            SQL_WENTI_SHOW = "select id from fawu_wenti where answernum=0 and columnid="+ID+" and title like '%"+keys+"%' and province='"+pro+"' ";
        }
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 获取某个问题
     */
    public wenti getQuestion(int ID){
        wenti wt = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,userid,username,filepath,user_id_huida,email from fawu_wenti where id = ?");
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()){
                wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setUserid(rs.getInt("userid"));
                wt.setUsername(rs.getString("username"));
                wt.setFilepath(rs.getString("filepath"));
                wt.setEmail(rs.getString("email"));
                wt.setUserid_hd(rs.getString("user_id_huida"));
            }
            rs.close();
            pstmt.close();
        }catch (Throwable t) {
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

        return wt;
    }

    /**
     *获取某个栏目下的所有最新提问问题   status=0  是未解决问题  status=1 是已解决问题
     */
    public List getQuestions(int end,int begin,int stat,int ID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  status="+stat+" and columnid="+ID+" order by createdate desc) where rownum <= "+end+")where rn >="+begin+" ";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setFilepath(rs.getString("filepath"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     *获取某个栏目下的所有最新提问问题的总数（用于分页显示）   status=0  是未解决问题  status=1 是已解决问题
     */
    public List getConPage(int stat,int ID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select id from fawu_wenti where  status="+stat+" and columnid="+ID+"";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     *获取某个栏目下的所有0回答提问问题   
     */
    public List get0ansQuestions(int end,int begin,int ID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select * from(select rownum rn,id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from (select  id,columnid,cname,question,status,voteflag,xuanshang,answernum,source,createdate,ipaddress,creater,province,city,area,picpath,emailnotify,title,filepath from fawu_wenti where  columnid="+ID+" and answernum=0 order by createdate desc) where rownum <= "+end+")where rn >="+begin+" ";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                wt.setColumnid(rs.getInt("columnid"));
                wt.setCname(rs.getString("cname"));
                wt.setQuestion(rs.getString("question"));
                wt.setStatus(rs.getInt("status"));
                wt.setVoteflag(rs.getInt("voteflag"));
                wt.setXuanshang(rs.getInt("xuanshang"));
                wt.setAnwsernum(rs.getInt("answernum"));
                wt.setSource(rs.getString("source"));
                wt.setCreatedate(rs.getTimestamp("createdate"));
                wt.setIpaddress(rs.getString("ipaddress"));
                wt.setCreater(rs.getString("creater"));
                wt.setProvince(rs.getString("province"));
                wt.setCity(rs.getString("city"));
                wt.setArea(rs.getString("area"));
                wt.setPicpath(rs.getString("picpath"));
                wt.setEmailnotify(rs.getInt("emailnotify"));
                wt.setTitles(rs.getString("title"));
                wt.setFilepath(rs.getString("filepath"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     *获取某个栏目下的所有0回答提问问题   
     */
    public List get0ansQuestions_num(int ID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select id from fawu_wenti where answernum = 0 and columnid ="+ID+"";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt("id"));
                list.add(wt);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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

    /**
     * 根据ID显示问题的具体信息
     */
    public wenbaQuiz getQuestionContent(String ID) throws wenbaException{
        //List list = new ArrayList();
        wenbaQuiz wenbaquiz = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int Iid = Integer.parseInt(ID);
        String SQL_WENTI_SHOW_con = "SELECT id,columnid,question,status,xuanshang,answernum,ipaddress,createdate,picpath,province,city,area FROM fawu_wenti where id= "+Iid+" ";
        try{
            wenbaquiz = new wenbaQuiz();
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW_con);
            while(rs.next()){
                wenbaquiz = new wenbaQuiz();
                wenbaquiz.setId(rs.getInt("id"));
                wenbaquiz.setColumnid(rs.getInt("columnid"));
                wenbaquiz.setXuanshang(rs.getInt("xuanshang"));
                wenbaquiz.setAnswernum(rs.getInt("answernum"));
                wenbaquiz.setIpaddress(rs.getString("ipaddress"));
                wenbaquiz.setCreatedate(rs.getDate("createdate"));
                wenbaquiz.setQuestion(rs.getString("question"));
                wenbaquiz.setStatus(rs.getInt("status"));
                wenbaquiz.setPicpath(rs.getString("picpath"));
                wenbaquiz.setProvince(rs.getString("province"));
                wenbaquiz.setCity(rs.getString("city"));
                wenbaquiz.setArea(rs.getString("area"));
                //list.add(wenbaquiz);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
        return wenbaquiz;
    }
    /**
     * 添加回答
     */
    private static final String SQL_INSERT_ANSWER = "INSERT INTO fawu_anwser(id,qid,anwser,ipaddress,cankaoziliao,createdate,votenum,picpath,userid,username,betterans,anw_status) values(?,?,?,?,?,?,?,?,?,?,?,?)";
    public void addanswer(answer answers)throws wenbaException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequeceManager = SequencePeer.getInstance();
        int nextid = sequeceManager.getSequenceNum("fawu");
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_INSERT_ANSWER);
            pstmt.setInt(1, nextid);
            pstmt.setInt(2, answers.getQid());
            pstmt.setString(3, answers.getAnwser());
            pstmt.setString(4, answers.getIpaddress());
            pstmt.setString(5, answers.getCahkaoziliao());
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(7, answers.getVotenum());
            pstmt.setString(8, answers.getPicpath());
            pstmt.setInt(9, answers.getUserid());
            pstmt.setString(10, answers.getUsername());
            pstmt.setInt(11, 0);
            pstmt.setInt(12, 0);
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        }catch (Throwable t) {
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
    }

    /**
     *
     * 获得提问回答的得票数
     */
    public int getAnswerNum(String ID){
        int num = 0;
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int Iid = Integer.parseInt(ID);
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select votenum from fawu_anwser where  id = "+Iid+"");
            while(rs.next()){
                //list.add(rs.getInt("id"));
                num = rs.getInt("votenum");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
        return num;
    }

    /**
     * 根据问题得到问题回答的全部内容
     */
    public List getAnwserCon(int end,int begin,String ID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int Iid = Integer.parseInt(ID);
        String SQL_ANWSER_SHOW = "select * from(select rownum rn,id,qid,anwser,votenum,ipaddress,userid,username,fenshu,cankaoziliao,createdate,picpath from (select id,qid,anwser,votenum,ipaddress,userid,username,fenshu,cankaoziliao,createdate,picpath from fawu_anwser where qid="+Iid+" and betterans=0 and anw_status=0 order by createdate desc) where rownum <= "+end+")where rn >="+begin+"";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_ANWSER_SHOW);
            answer ans;
            while(rs.next()){
                ans = new answer();
                ans.setId(rs.getInt("id"));
                ans.setQid(rs.getInt("qid"));
                ans.setAnwser(rs.getString("anwser"));
                ans.setVotenum(rs.getInt("votenum"));
                ans.setIpaddress(rs.getString("ipaddress"));
                ans.setUserid(rs.getInt("userid"));
                ans.setUsername(rs.getString("username"));
                ans.setFenshu(rs.getInt("fenshu"));
                ans.setPicpath(rs.getString("picpath"));
                ans.setCahkaoziliao(rs.getString("cankaoziliao"));
                ans.setCreatedate(rs.getDate("createdate"));
                list.add(ans);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    //根据问题得到问题回答的总数(分页)
    public List getAnwserConnum(int ID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String SQL_ANWSER_SHOW = "select id from fawu_anwser where qid="+ID+" and anw_status=0";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_ANWSER_SHOW);
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
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
    //查询某个提问的最佳回答
    public answer getAnwserCon_zuijia(String ID) throws wenbaException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        answer ans = null;
        int Iid = Integer.parseInt(ID);
        String SQL_ANWSER_SHOW = "select * from fawu_anwser where qid="+Iid+" and anw_status=1";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_ANWSER_SHOW);
            ans = new answer();
            while(rs.next()){
                ans.setId(rs.getInt("id"));
                ans.setQid(rs.getInt("qid"));
                ans.setAnwser(rs.getString("anwser"));
                ans.setVotenum(rs.getInt("votenum"));
                ans.setIpaddress(rs.getString("ipaddress"));
                ans.setUserid(rs.getInt("userid"));
                ans.setUsername(rs.getString("username"));
                ans.setFenshu(rs.getInt("fenshu"));
                ans.setPicpath(rs.getString("picpath"));
                ans.setCahkaoziliao(rs.getString("cankaoziliao"));
                ans.setCreatedate(rs.getDate("createdate"));
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return ans;
    }
    /**
     * 用于添加投票的相关信息
     */
    private static final String SQL_INSERT_VOTE = "INSERT INTO fawu_vote(id,aid,userid) values(?,?,?)";
    public void addVote(vote vot)throws wenbaException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequeceManager = SequencePeer.getInstance();
        int nextid = sequeceManager.getSequenceNum("fawu");
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_INSERT_VOTE);
            pstmt.setInt(1, nextid);
            pstmt.setInt(2, vot.getAnwID());
            pstmt.setInt(3, vot.getUserID());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }
    /*
     * 
     * 查询投票回答的相关信息
     */

    public vote getVote(int ID) throws wenbaException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        vote vot = null;

        String SQL_VOTE_SHOW = "select * from fawu_vote where aid="+ID+" ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_VOTE_SHOW);
            vot = new vote();
            while(rs.next()){
                vot.setId(rs.getInt("id"));
                vot.setAnwID(rs.getInt("aid"));
                vot.setUserID(rs.getInt("userid"));
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return vot;
    }
    public ResultSet executequery(String sql)throws wenbaException{
        Connection conn = null;
        Statement stmt=null;
        ResultSet rs=null;
        try{
            stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=stmt.executeQuery(sql);
        }catch (Throwable t) {
            t.printStackTrace();
        }
        return rs;
    }

    public int executeupdate(String sql)throws wenbaException{
        Connection conn = null;
        Statement stmt=null;
        ResultSet rs=null;
        int afint = 0;
        try{
            stmt=conn.createStatement() ;
            afint=stmt.executeUpdate(sql);
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return afint;
    }


    //------------------------------------------------------->>>>>>>>>>>>>>>
    private static final String SQL_GETCOLUMN = "SELECT id,siteid,dirname,orderid,parentid,ename,cname,creater,status,createdate " +
            "FROM c WHERE ID = ?";

    public wenbaImpl getColumn(int ID) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        wenbaImpl column = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETCOLUMN);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                column = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return column;
    }

    private static final String SQL_checkDualEnName =
            "SELECT id FROM fawu_wenti_column WHERE parentid=? and ename=?";

    public boolean duplicateEnName(int parentColumnID, String enName) {
        Connection conn = null;
        PreparedStatement pstmt;
        boolean existflag = false;
        ResultSet rs;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_checkDualEnName);
            pstmt.setInt(1, parentColumnID);
            pstmt.setString(2, enName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existflag = true;
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
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return existflag;
    }


    private static final String SQL_UPDATECOLUMN =
            "UPDATE fawu_wenti_column SET Cname = ?,Extname = ?,LastUpdated = ?,Editor = ?,OrderID = ?,isDefineAttr = ?," +
                    "xmlTemplate = ?,ColumnDesc = ?,isAudited = ?,isProduct = ?,isPublishMore = ?,LanguageType = ?," +
                    "contentshowtype = ?,isRss = ?,useArticleType = ?,istype = ? WHERE ID = ?";

    //edit for xuzheming at 2008.07.27
    public void update(wenbaImpl column) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN);
                pstmt.setString(1, StringUtil.iso2gb(column.getCName()));
                pstmt.setInt(5, column.getOrderID());
                pstmt.setInt(17, column.getID());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }

    //    private static final String SQL_UPDATE_STOPFLAG =
    //            "DELETE FROM tbl_column WHERE ID = ? and siteid = ?";
    private static final String SQL_REMOVE_COLUMN =
            "DELETE FROM fawu_wenti_column WHERE ID = ? and siteid = ?";

    /*    private static final String SQL_REMOVE_WENTI =
                "DELETE FROM tbl_template WHERE columnID = (select columnid from tbl_template a,tbl_column b where a.columnid=? and b.siteid=? and a.columnid=b.id)";

        private static final String SQL_REMOVE_ANWSER =
                "DELETE FROM tbl_members_rights WHERE columnID = (select columnid from tbl_members_rights a,tbl_column b where a.columnid=? and b.siteid=? and a.columnid=b.id)";
    */
    public void remove(int ID, int siteID) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_REMOVE_COLUMN);
                pstmt.setInt(1, ID);
                pstmt.setInt(2, siteID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: delete column failed.");
            }
            finally {
                if (conn != null) {
                    try {
                        //conn.close();
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }


    private static final String SQL_GETMAXORDER = "SELECT count(ID) FROM fawu_wenti_column WHERE parentID=? ";

    int getNextOrder(int ID) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int order = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETMAXORDER);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                order = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            order++;
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return order;
    }
    //根据id查询栏目中文名
    public wenbaImpl getCnameId(int ID) throws wenbaException{
        wenbaImpl wenbai = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_WENTI_SHOW = "select cname from fawu_wenti_column where id = "+ID+"";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_WENTI_SHOW);
            while(rs.next()){
                wenbai = new wenbaImpl();
                wenbai.setCName(rs.getString("cname"));
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return wenbai;
    }

    public  List getUserIDList() throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userid = "select id from tbl_userinfo where zhuanjia !=1 order by scores desc";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userid);
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    /**
     * 根据用户ID 查询 用户每周回答提问的数量
     */

    public  List getUserhuidasu(int userID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userid = "select userid from fawu_anwser where userid="+userID+" and createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by createdate desc";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userid);
            while(rs.next()){
                list.add(rs.getInt("userid")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    /**
     * 根据用户ID 查询 用户每月回答提问的数量
     *
     *
     *public  List getUserhuidasu_month(int userID) throws wenbaException{
     *	List list = new ArrayList();
     *	Connection conn = null;
     *	Statement stmt = null;
     *	ResultSet rs = null;
     *	String SQL_userid = "select userid from fawu_anwser where userid="+userID+" and createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by createdate desc";
     *	try{
     *		conn = cpool.getConnection();
     *		stmt = conn.createStatement();
     *		rs = stmt.executeQuery(SQL_userid);
     *		while(rs.next()){
     *			list.add(rs.getInt("userid"));
     *		}
     *		rs.close();
     *		stmt.close();
     *	}catch (Throwable t) {
     *        t.printStackTrace();
     *    } finally {
     *        if (conn != null) {
     *            try {
     *                //conn.close();
     *                cpool.freeConnection(conn);
     *            } catch (Exception e) {
     *                System.out.println("Error in closing the pooled connection " + e.toString());
     *            }
     *       }
     *   }
     *	return list;
     *}
     */



    public List DateCon(){
        List list = new ArrayList();
        //获取当前系统时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String dates = sdf.format(date);
        ParsePosition pos = new ParsePosition(0);
        Date Dates1 = sdf.parse(dates,pos);

        //计算当前日期是星期几
        Calendar aCalendar=Calendar.getInstance();
        int x=aCalendar.get(Calendar.DAY_OF_WEEK);
        //每周的第一天（星期日） 进行判断
        if(x==2){
            aCalendar.add(Calendar.DAY_OF_MONTH, -2);
            //上一周结束时间（周六）
            String newdate1 = sdf.format(aCalendar.getTime());
            aCalendar.add(Calendar.DAY_OF_MONTH, -6);
            //上一周的开始时间（周日）
            String newdate2 = sdf.format(aCalendar.getTime());
            list.add(0, newdate1);
            list.add(1, newdate2);
        }
        return list;
    }

    /**
     * 根据用户ID 计算 用户上一周回答的问题数目（方法一）
     */
    public  List getUserAnwNum(int userID,String bedate,String enddate) throws wenbaException{
        List list = new ArrayList();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userid = "select id from fawu_anwser where userid="+userID+" and createdate <=to_date('"+bedate+" 12:00:00','yyyy-mm-dd hh24:mi:ss') and createdate >= to_date('"+enddate+" 01:00:00','yyyy-mm-dd hh24:mi:ss')";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userid);
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    /**
     * 根据用户ID 计算 用户上一周回答的问题数目（方法二）
     */
    public  List getUserAnwNum2(String userID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userid = "select id from fawu_anwser where userid="+userID+" and createdate between next_day(sysdate,1) -7  and next_day(sysdate,1)-0 order by createdate desc";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userid);
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    /**
     * 根据用户ID 计算 用户上一个月回答的问题数目
     */
    public  List getUserNum_month(String userID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userid = "select id from fawu_anwser where userid="+userID+" and to_char(createdate,'MM') = to_char(add_months(sysdate,-0),'MM')";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userid);
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    /**
     * 根据用户ID 计算 用户上一个月被评为最佳答案的回答数目
     */
    public  List getUserNum_month_Status(String userID) throws wenbaException{
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userid = "select id from fawu_anwser where userid="+userID+" and anw_status=1 and to_char(createdate,'MM') = to_char(add_months(sysdate,-0),'MM')";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userid);
            while(rs.next()){
                list.add(rs.getInt("id")+"");
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //将符合判断条件的用户设置为问吧专家
    public void changeUserinfo(String userid) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_answerstatus ="update tbl_userinfo set zhuanjia = 1 where id = "+userid+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_answerstatus);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }
    //查询问吧专家信息 select user_id,user_name,user_grade from userinfo where  wenba_zhuanjia=1
    public List getzhuangjian(){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        String SQL_userinfo_zhuanjia = "select id,memberid,scores,meilizhi,memo,image from tbl_userinfo where  zhuanjia=1 order by scores desc";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userinfo_zhuanjia);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Uregister us = new Uregister();
                us.setId(rs.getInt(1));
                us.setMemberid(rs.getString(2));
                us.setScores(rs.getInt(3));
                us.setMeilizhi(rs.getInt(4));
                us.setMemo(rs.getString(5));
                String imgname = rs.getString(6);
                if(imgname==null){
                    us.setImage("0.jpg");
                }else{
                    us.setImage(imgname);
                }
                list.add(us);
                count = count + 1;
                if (count >= 4) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    /**
     * 统计提问的点击率  每天同一IP地址的访客只计算一次点击率
     */
    public void Pageview(int id) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_UPDATECOLUMN_dianji ="update fawu_wenti set dianjinum = dianjinum+1 where id = "+id+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN_dianji);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }
    wenbaImpl load(ResultSet rs) throws SQLException {
        wenbaImpl column = new wenbaImpl();

        column.setID(rs.getInt("ID"));
        column.setSiteID(rs.getInt("siteid"));
        column.setDirName(rs.getString("dirname"));
        column.setOrderID(rs.getInt("orderid"));
        column.setParentID(rs.getInt("parentid"));
        column.setEName(rs.getString("ename"));
        column.setCName(rs.getString("cname"));

        return column;
    }

    //上传文件加分
    public void changuserinfo_grade(int userid) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_answerstatus ="update tbl_userinfo set scores = scores+10,meilizhi = meilizhi+15  where id = "+userid+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_answerstatus);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }

    /**
     * 问题的悬赏分数由最佳答案的提供者获得（悬赏分由系统分和用户悬赏分组成）   魅力值加 10 分 
     */
    public void changbestAans(int nums,int userid) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_userinfo_xuanshang ="update tbl_userinfo set scores = scores + "+nums+", meilizhi = meilizhi+10 where id = "+userid+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_userinfo_xuanshang);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }

    /**
     * 悬赏问题的悬赏分数出自问题提出者本身的积分
     */
    public void changuser_xuanshang(int nums,int userid) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_userinfo_xuanshang ="update tbl_userinfo set scores = scores - "+nums+" where id = "+userid+" ";

            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_userinfo_xuanshang);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }
    /**
     * 查询用户积分   是否可以使用悬赏提问模式（悬赏提问模式 悬赏分由用户悬赏和系统固定积分组成）
     */
    public Uregister jifen(int userID) throws wenbaException{
        Uregister user = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_userinfo = "select scores from tbl_userinfo where id = "+userID+"";

        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_userinfo);
            while(rs.next()){
                user = new Uregister();
                user.setScores(rs.getInt("scores"));
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return user;
    }

    //发帖提 积分加 5 分，魅力值加 8 分
    public void change_huida(int userid) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            String SQL_userinfo_xuanshang ="update tbl_userinfo set scores = scores + 5,meilizhi = meilizhi+8 where id = "+userid+" ";
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_userinfo_xuanshang);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: update column failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }
    //查询专家最近回答的五个问题
    public List anszj_5(int userid){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select id,columnid,cname,question,createdate,title from fawu_wenti where id in (select qid from fawu_anwser where userid = "+userid+") order by createdate desc";
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wen = new wenti();
                wen.setId(rs.getInt("id"));
                wen.setColumnid(rs.getInt("columnid"));
                wen.setCname(rs.getString("cname"));
                wen.setQuestion(rs.getString("question"));
                wen.setCreatedate(rs.getTimestamp("createdate"));
                wen.setTitles(rs.getString("title"));
                list.add(wen);
                count = count + 1;
                if (count >= 5) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    //
    public List info_zhuanjia(int userid){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select id,columnid,cname,question,createdate,title from fawu_wenti where id in (select qid from fawu_anwser where userid = 44) order by createdate desc";
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                wenti wen = new wenti();
                wen.setId(rs.getInt("id"));
                wen.setColumnid(rs.getInt("columnid"));
                wen.setCname(rs.getString("cname"));
                wen.setQuestion(rs.getString("question"));
                wen.setCreatedate(rs.getTimestamp("createdate"));
                wen.setTitles(rs.getString("title"));
                list.add(wen);
                count = count + 1;
                if (count >= 5) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //---------------------栏目搜索（与问吧无关）------------------------

    //查询总数
    public List Cms_con(String Keys,String culumnid){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select id, maintitle,dirname,createdate from tbl_article where maintitle like '%"+Keys+"%' and "+culumnid+" and pubflag=0 ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                ss.setMaintitle(rs.getString("maintitle"));
                ss.setDirname(rs.getString("dirname"));
                ss.setCreatedate(rs.getTimestamp("createdate"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    public List Cms_select_to(int end_no,int begin_no, String Keys,String culumnid){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select * from(select rownum rn, id,maintitle,dirname,createdate from (select id,maintitle,dirname,createdate"
                +" from tbl_article where maintitle like '%"+Keys+"%' and "+culumnid+" and pubflag=0 order by createdate desc)where rownum<="+end_no+")where rn >="+begin_no+" ";
        System.out.println("SQL_CMS_SOUSUO="+SQL_CMS_SOUSUO);
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                ss.setMaintitle(rs.getString("maintitle"));
                ss.setDirname(rs.getString("dirname"));
                ss.setCreatedate(rs.getTimestamp("createdate"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    //查询总数
    public List Cms_con_wenba(String Keys){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select id from fawu_wenti where title like '%"+Keys+"%' ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    //
    public List Cms_select_wenba(int end_no,int begin_no, String Keys){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select * from(select rownum rn,id,columnid,title,createdate,status,answernum from (select id,columnid,title,createdate,status,answernum from fawu_wenti "
                +" where title like '%"+Keys+"%'  order by createdate desc)where rownum<="+end_no+")where rn >="+begin_no+" ";
        System.out.println("SQL_CMS_SOUSUO="+SQL_CMS_SOUSUO);
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                ss.setMaintitle(rs.getString("title"));
                ss.setCid(rs.getInt("columnid"));
                ss.setCreatedate(rs.getTimestamp("createdate"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }


    //查询总数
    public List Cms_yanwen_con(String Keys,int cid){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select id from tbl_article where maintitle like '%"+Keys+"%' and columnid="+cid+" and pubflag=0 ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    public List Cms_yawen_select(int end_no,int begin_no, String Keys,int cid){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select * from(select rownum rn, id,maintitle,dirname,createdate from (select id,maintitle,dirname,createdate"
                +" from tbl_article where maintitle like '%"+Keys+"%' and columnid="+cid+" and pubflag=0 order by createdate desc)where rownum<="+end_no+")where rn >="+begin_no+" ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                ss.setMaintitle(rs.getString("maintitle"));
                ss.setDirname(rs.getString("dirname"));
                ss.setCreatedate(rs.getTimestamp("createdate"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }


    //全文检索查询总数
    public List Cms_qw_con(String Keys){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_qw = "select id from tbl_article where maintitle like '%"+Keys+"%' and pubflag=0 ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_qw);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    //全文检索
    public List Cms_qw_select(int end_no,int begin_no, String Keys){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String SQL_CMS_SOUSUO = "select * from(select rownum rn, id,maintitle,dirname,createdate from (select id,maintitle,dirname,createdate"
                +" from tbl_article where maintitle like '%"+Keys+"%' and pubflag=0 order by createdate desc)where rownum<="+end_no+")where rn >="+begin_no+" ";
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL_CMS_SOUSUO);
            wenbaQuiz wenbaquiz;
            while(rs.next()){
                Sousuo ss = new Sousuo();
                ss.setId(rs.getInt("id"));
                ss.setMaintitle(rs.getString("maintitle"));
                ss.setDirname(rs.getString("dirname"));
                ss.setCreatedate(rs.getTimestamp("createdate"));
                list.add(ss);
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public List getWenti(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT id,columnid,cname,question,createdate,ipaddress,province,city,area,picpath,emailnotify,title,email,filepath FROM fawu_wenti ORDER BY createdate desc";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt(1));
                wt.setColumnid(rs.getInt(2));
                wt.setCname(rs.getString(3));
                wt.setQuestion(rs.getString(4));
                wt.setCreatedate(rs.getTimestamp(5));
                wt.setIpaddress(rs.getString(6));
                wt.setProvince(rs.getString(7));
                wt.setCity(rs.getString(8));
                wt.setArea(rs.getString(9));
                wt.setPicpath(rs.getString(10));
                wt.setEmailnotify(rs.getInt(11));
                wt.setTitles(rs.getString(12));
                wt.setEmail(rs.getString(13));
                wt.setFilepath(rs.getString(14));
                list.add(wt);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }

        return list;
    }

    /**
     * 针对专家已解决
     */
    public List getyijiejue(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE status=1 AND user_id_huida<>0";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 针对专家零回答
     */
    public List getLingHuiDa(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE answernum=0 AND user_id_huida<>0";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 针对专家最新提问
     */
    public List getZuiXinTiWen(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE user_id_huida<>0 ORDER BY createdate desc";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 查询省份
     */
    public List getProvince()
    {
        List prolist;
        prolist = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT id,provname FROM en_province";
        try
        {
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                WenbaBean wb = new WenbaBean();
                wb.setProvinceid(rs.getString(1));
                wb.setProvince(rs.getString(2));
                prolist.add(wb);
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            else
                System.out.println("数据库连接失败!");
        }
        return prolist;
    }

    /**
     * 根据省份id查询对应的城市
     */
    public String getCity(int provinceid)
    {
        String citys;
        citys = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT id,cityname FROM en_city WHERE provid=?";
        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, provinceid);
            for(rs = pstmt.executeQuery(); rs.next();)
                if(citys.equals(""))
                    citys = rs.getInt(1) + "-" + rs.getString(2);
                else
                    citys = citys + "," + rs.getInt(1) + "-" + rs.getString(2);

            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    conn.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            else
                System.out.println("数据库连接失败!");
        }
        return citys;
    }

    /**
     * 用户最新提出问题
     * @return
     */
    public List getZXTW(int fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE status<>2 ");
            if(fenlei==0){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append("AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" AND columnid="+fenlei);
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 所有已解决的问题（包括有效的和过期的问题）
     * @return
     */
    public List getYJJ(int fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE status=1 AND answernum<>0");
            conn = cpool.getConnection();
            if(fenlei==0){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append("AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" AND columnid="+fenlei);
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 零回答问题（包括过期的和有效的）
     * @return
     */
    public List getLHD(int fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE answernum=0 AND status<>2 ");
            conn = cpool.getConnection();
            if(fenlei==0){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append("AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" AND columnid="+fenlei);
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 问吧检索
     * @param fenlei
     * @param province
     * @param keyword
     * @return
     */
    //----------------------------------------------------------------------------------























    public List getWenti(String fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,columnid,cname,question,createdate,ipaddress,province,city,area,picpath,emailnotify,title,email,filepath,answernum FROM fawu_wenti");
            if(fenlei.equals("")){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" WHERE anw_status=0 AND status=0 ORDER BY createdate desc");//isstop=1
                    }else{
                        sb.append(" WHERE title like'%"+keyword+"%' AND anw_status=0 AND status=0 ORDER BY createdate desc");//isstop=1
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" WHERE province='"+province+"' AND anw_status=0 AND status=0");
                    if(keyword.equals("")){
                        sb.append(" AND ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" WHERE cname='"+fenlei+"' AND anw_status=0 AND status=0");
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            System.out.println(sb.toString());
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                wenti wt = new wenti();
                wt.setId(rs.getInt(1));
                wt.setColumnid(rs.getInt(2));
                wt.setCname(rs.getString(3));
                wt.setQuestion(rs.getString(4));
                wt.setCreatedate(rs.getTimestamp(5));
                wt.setIpaddress(rs.getString(6));
                wt.setProvince(rs.getString(7));
                wt.setCity(rs.getString(8));
                wt.setArea(rs.getString(9));
                wt.setPicpath(rs.getString(10));
                wt.setEmailnotify(rs.getInt(11));
                wt.setTitles(rs.getString(12));
                wt.setEmail(rs.getString(13));
                wt.setFilepath(rs.getString(14));
                wt.setAnwsernum(rs.getInt(15));
                list.add(wt);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }

        return list;
    }

    /**
     * 针对专家零回答检索
     * @param fenlei
     * @param province
     * @param keyword
     * @return
     */
    public List getLingHuiDa(int fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE answernum=0 AND user_id_huida<>0 AND status<>2");
            if(fenlei==0){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append("AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" AND columnid="+fenlei);
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            //String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE answernum=0 AND user_id_huida<>0";
            System.out.println(sb.toString());
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 针对专家最新提问检索
     */
    public List getZuiXinTiWen(int fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE user_id_huida<>0 AND status<>2 ");
            //String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE user_id_huida<>0 ORDER BY createdate desc";
            if(fenlei==0){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append("AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" AND columnid="+fenlei);
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            System.out.println(sb.toString());
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 针对专家已解决问题检索
     * @param fenlei
     * @param province
     * @param keyword
     * @return
     */
    public List getyijiejue(int fenlei,String province,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE status=1 AND user_id_huida<>0");
            //String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE status=1 AND user_id_huida<>0";
            if(fenlei==0){
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1||province.indexOf("市")!=-1){
                        if(province.indexOf("省")!=-1)
                            province = province.substring(0,province.indexOf("省"));
                        if(province.indexOf("市")!=-1)
                            province = province.substring(0,province.indexOf("市"));
                    }
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append("AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }else{
                sb.append(" AND columnid="+fenlei);
                if(province.equals("")){
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }else{
                    if(province.indexOf("省")!=-1)
                        province = province.substring(0,province.indexOf("省"));
                    sb.append(" AND province='"+province+"'");
                    if(keyword.equals("")){
                        sb.append(" ORDER BY createdate desc");
                    }else{
                        sb.append(" AND title like'%"+keyword+"%' ORDER BY createdate desc");
                    }
                }
            }
            System.out.println(sb.toString());

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 某专家的详细信息
     * @param userid
     * @return
     */
    public Uregister getzhuanjiiainfo(int userid){
        Uregister user = new Uregister();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "select id,memberid,scores,meilizhi,memo from tbl_userinfo where  zhuanjia=1 AND id=? order by scores desc";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            if(rs.next()){
                user.setId(rs.getInt(1));
                user.setMemberid(rs.getString(2));
                user.setScores(rs.getInt(3));
                user.setMeilizhi(rs.getInt(4));
                user.setMemo(rs.getString(5));
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return user;
    }

    /**
     * 修改答人简介
     * @param userid
     * @param content
     * @return
     */
    public int setintroduct(int userid,String content){
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            String sql = "Update tbl_userinfo SET memo=? WHERE id=?";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.setInt(2, userid);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e){
            result = -1;
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    result = -2;
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }

        return result;
    }

    /**
     * 针对某专家的问题
     * @param: usrid
     * @return
     */
    public List getquestion(int userid){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title,status FROM fawu_wenti WHERE user_id_huida=? AND status<>2 ORDER BY status";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wb = new WenbaBean();
                wb.setId(rs.getInt(1));
                wb.setCname(rs.getString(2));
                wb.setXuanshang(rs.getInt(3));
                wb.setAnswernum(rs.getInt(4));
                wb.setCreatedate(rs.getDate(5));
                wb.setProvince(rs.getString(6));
                wb.setTitle(rs.getString(7));
                wb.setStatus(rs.getInt(8));
                list.add(wb);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }

        return list;
    }

    /**
     * 地方用户最新提出问题
     * @return
     */
    public List getdifangZXTW(String proname,int cid,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status,columnid FROM fawu_wenti WHERE status<>2 AND province='"+proname+"'");
            if(cid!=0){
                sb.append(" AND columnid="+cid);
                if(!keyword.equals("")){
                    sb.append(" AND title like'%"+keyword+"%'");
                }
            }
            sb.append(" ORDER BY createdate desc");

            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                wq.setColumnid(rs.getInt(9));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 地方所有已解决的问题（包括有效的和过期的问题）
     * @return
     */
    public List getdifangYJJ(String proname,int cid,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status,columnid FROM fawu_wenti WHERE status=1 AND answernum<>0 AND province='"+proname+"'");
            if(cid!=0){
                sb.append(" AND columnid="+cid);
                if(!keyword.equals("")){
                    sb.append(" AND title like'%"+keyword+"%'");
                }
            }
            sb.append(" ORDER BY createdate desc");
            System.out.println(sb.toString());
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                wq.setColumnid(rs.getInt(9));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 地方零回答问题（包括过期的和有效的）
     * @return
     */
    public List getdifangLHD(String proname,int cid,String keyword){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT id,cname,xuanshang,answernum,createdate,province,title,status,columnid FROM fawu_wenti WHERE answernum=0 AND status<>2 AND province='"+proname+"'");
            if(cid!=0){
                sb.append(" AND columnid="+cid);
                if(!keyword.equals("")){
                    sb.append(" AND title like'%"+keyword+"%'");
                }
            }
            sb.append(" ORDER BY createdate desc");
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wq = new WenbaBean();
                wq.setId(rs.getInt(1));
                wq.setCname(rs.getString(2));
                wq.setXuanshang(rs.getInt(3));
                wq.setAnswernum(rs.getInt(4));
                wq.setCreatedate(rs.getDate(5));
                wq.setProvince(rs.getString(6));
                wq.setTitle(rs.getString(7));
                wq.setStatus(rs.getInt(8));
                wq.setColumnid(rs.getInt(9));
                list.add(wq);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    public int edituserimg(int userid,String imgpath,String imgname){
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        //ResultSet rs = null;
        try{
            String sql = "UPDATE tbl_userinfo SET image=? WHERE id=?";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, imgname);
            pstmt.setInt(2, userid);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e){
            result = -2;
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    result = -2;
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }

        return result;
    }

    public List getTOP8DianJiShu(int ID){
        List list = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            conn = cpool.getConnection();
            stmt = conn.createStatement();
            if(ID==0){
                rs = stmt.executeQuery("select title,id,dianjinum from fawu_wenti where status<>2 AND createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by dianjinum desc");
            }else{
                rs = stmt.executeQuery("select title,id,dianjinum from fawu_wenti where status<>2 AND columnid = "+ID+" and createdate between next_day(sysdate,'星期日') - 7  and next_day(sysdate,'星期日') order by dianjinum desc");
            }
            while(rs.next()){
                WenbaBean wb = new WenbaBean();
                wb.setTitle(rs.getString(1));
                wb.setId(rs.getInt(2));
                wb.setFenshu(rs.getInt(3));
                list.add(wb);
                count = count + 1;
                if (count >= 8) break;
            }
            rs.close();
            stmt.close();
        }catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int weekgrade(int userid,int score){
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sundaytime = "";
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            java.util.Date date = c.getTime();
            String sundaysql = "SELECT sundaytime FROM tbl_userinfo WHERE id=?";
            String weekgradesql = "UPDATE tbl_userinfo SET weekscore=weekscore+"+score+" WHERE id=?";
            String nextsundaysql = "update tbl_userinfo set sundaytime = (select to_char(trunc(next_day(sysdate - 7, 1)+7 ),'yyyy-mm-dd') from dual),weekscore=0 where id=?";

            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sundaysql);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            if(rs.next()){
                sundaytime = rs.getString(1);
            }
            rs.close();
            pstmt.close();


            if(sundaytime==null||sundaytime.equals("null")||sundaytime.equals("")){//不在本周内
                pstmt = conn.prepareStatement(nextsundaysql);
                pstmt.setInt(1, userid);
                pstmt.executeUpdate();
                pstmt.close();
            }else{
                if(date.after(sdf.parse(sundaytime))){
                    pstmt = conn.prepareStatement(nextsundaysql);
                    pstmt.setInt(1, userid);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            pstmt = conn.prepareStatement(weekgradesql);
            pstmt.setInt(1, userid);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return result;
    }
    /*
     * 本周积分排行前8位
     */
    public List getTop8weekgrade(){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try{
            String sql = "SELECT id,memberid,scores,weekscore FROM tbl_userinfo WHERE sundaytime = (select to_char(trunc(next_day(sysdate - 7, 1)+7 ),'yyyy-mm-dd') from dual) AND weekscore>0 ORDER BY weekscore desc";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wb = new WenbaBean();
                wb.setUserid(rs.getInt(1));
                wb.setUsername(rs.getString(2));
                wb.setUsergrade(rs.getInt(3));
                wb.setWeekgrade(rs.getInt(4));
                list.add(wb);
                count++;
                if(count==8)
                    break;
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    public int answaddgrade(int userid,int grade){
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "UPDATE tbl_userinfo SET scores=scores+? WHERE id=?";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, grade);
            pstmt.setInt(2, userid);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e){
            result = -1;
            e.printStackTrace();
        }finally{
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    result = -2;
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return result;
    }

    /**
     *
     * @param questionid
     * @return 0修改成功  -1修改工程中有异常 -2关闭数据库异常 -3用户id为0  1此问题是过期问题
     */
    public int wentiguoqi(int questionid){
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int status = 0;//0 未解决  1 已解决
        int xuanshanggrade = 0;
        int userid = 0;
        int anwstatus = 1;
        String sundaytime = "";
        try{
            conn = cpool.getConnection();
            String statussql = "SELECT status,userid,anw_status,xuanshang FROM fawu_wenti WHERE id=?";
            String cutgradesql = "UPDATE tbl_userinfo SET scores=scores-? WHERE id=?";
            String anwstatussql = "UPDATE fawu_wenti SET anw_status=1 WHERE id=?";//0 未过期 1 过期

            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            java.util.Date date = c.getTime();
            String sundaysql = "SELECT sundaytime FROM tbl_userinfo WHERE id=?";
            String weekgradesql = "UPDATE tbl_userinfo SET weekscore=weekscore-? WHERE id=?";
            String nextsundaysql = "update tbl_userinfo set sundaytime = (select to_char(trunc(next_day(sysdate - 7, 1)+7 ),'yyyy-mm-dd') from dual),weekscore=0 where id=?";

            pstmt = conn.prepareStatement(statussql);
            pstmt.setInt(1, questionid);
            rs = pstmt.executeQuery();
            if(rs.next()){
                status = rs.getInt(1);
                userid = rs.getInt(2);
                anwstatus = rs.getInt(3);
                xuanshanggrade = rs.getInt(4);
                if(xuanshanggrade==0)
                    xuanshanggrade = 5;//普通提问要扣发帖加的5分
            }
            rs.close();
            pstmt.close();
            if(anwstatus==1)
                return 1;

            if(status==0){
                if(userid!=0){
                    pstmt = conn.prepareStatement(cutgradesql);
                    pstmt.setInt(1, xuanshanggrade);
                    pstmt.setInt(2, userid);
                    pstmt.executeUpdate();
                    pstmt.close();

                    pstmt = conn.prepareStatement(sundaysql);
                    pstmt.setInt(1, userid);
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        sundaytime = rs.getString(1);
                    }
                    rs.close();
                    pstmt.close();


                    if(sundaytime==null||sundaytime.equals("null")||sundaytime.equals("")){//不在本周内
                        //System.out.println("null");
                        pstmt = conn.prepareStatement(nextsundaysql);
                        pstmt.setInt(1, userid);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }else{
                        //System.out.println("not null");
                        if(date.after(sdf.parse(sundaytime))){
                            //System.out.println("nextsunday");
                            pstmt = conn.prepareStatement(nextsundaysql);
                            pstmt.setInt(1, userid);
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                    }
                    pstmt = conn.prepareStatement(weekgradesql);
                    pstmt.setInt(1, xuanshanggrade);
                    pstmt.setInt(2, userid);
                    pstmt.executeUpdate();
                    pstmt.close();
                }else{
                    System.out.println("用户id为0");
                    return -3;//用户id 为0
                }
            }

            pstmt = conn.prepareStatement(anwstatussql);
            pstmt.setInt(1, questionid);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e){
            result = -1;
            e.printStackTrace();
        }finally{
            if (conn != null) {
                try {
                    //conn.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    result = -2;
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return  result;
    }

    /**
     * 某人提出的所有问题
     */
    public List getpersonwenti(int userid){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT id,cname,xuanshang,answernum,createdate,province,title FROM fawu_wenti WHERE userid=? ORDER BY createdate desc";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wb = new WenbaBean();
                wb.setId(rs.getInt(1));
                wb.setCname(rs.getString(2));
                wb.setXuanshang(rs.getInt(3));
                wb.setAnswernum(rs.getInt(4));
                wb.setCreatedate(rs.getDate(5));
                wb.setProvince(rs.getString(6));
                wb.setTitle(rs.getString(7));
                list.add(wb);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    /**
     * 某人回答的问题
     */
    public List getpersonanwser(int userid){
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT DISTINCT q.id,q.cname,q.xuanshang,q.answernum,q.createdate,q.province,q.title FROM fawu_wenti q,fawu_anwser a WHERE q.id=a.qid AND a.userid=? ORDER BY createdate desc";
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                WenbaBean wb = new WenbaBean();
                wb.setId(rs.getInt(1));
                wb.setCname(rs.getString(2));
                wb.setXuanshang(rs.getInt(3));
                wb.setAnswernum(rs.getInt(4));
                wb.setCreatedate(rs.getDate(5));
                wb.setProvince(rs.getString(6));
                wb.setTitle(rs.getString(7));
                list.add(wb);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }
        return list;
    }

    public void setzhuanjia(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String idstr = "";//上个月回答问题的人的id
        String idstr1 = "";//回答数多于anwsernum 的id
        String idstr2 = "";//采纳率大于50%id
        int anwsernum = 2;

        try{
            conn = cpool.getConnection();
            String lastmonthidsssql="SELECT DISTINCT userid FROM fawu_anwser WHERE to_char(createdate,'MM') = to_char(add_months(sysdate,-1),'MM')";
            String monthhuidasql = "SELECT COUNT(id) FROM fawu_anwser WHERE to_char(createdate,'MM') = to_char(add_months(sysdate,-1),'MM') and userid=?";
            String monthzuijiasql = "SELECT COUNT(id) FROM fawu_anwser WHERE anw_status=1 AND to_char(createdate,'MM') = to_char(add_months(sysdate,-1),'MM') and userid=?";
            String updatesql = "UPDATE tbl_userinfo SET zhuanjia=0";
            String updateidsql = "UPDATE tbl_userinfo SET zhuanjia=1 WHERE id=?";
            pstmt = conn.prepareStatement(lastmonthidsssql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                if(idstr.equals("")){
                    idstr = idstr + String.valueOf(rs.getInt(1));
                }else{
                    idstr = idstr + "," + String.valueOf(rs.getInt(1));
                }
            }
            rs.close();
            pstmt.close();

            System.out.println("-----------------------上月回答问题的人的id:"+idstr);
            if(!idstr.equals("")){
                String[] ids = idstr.split(",");
                for(int i=0;i<ids.length;i++){
                    String id = ids[i];
                    pstmt = conn.prepareStatement(monthhuidasql);
                    pstmt.setInt(1, Integer.parseInt(id));
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        int result = rs.getInt(1);
                        if(result>anwsernum){//
                            if(idstr1.equals("")){
                                idstr1 = idstr1 + id;
                            }else{
                                idstr1 = idstr1 + "," + id;
                            }
                        }
                    }
                    rs.close();
                    pstmt.close();
                }

                System.out.println("-----------------------月回答问题数大于给定值的id存在:"+idstr1);
                if(!idstr1.equals("")){//月回答问题数大于给定值的id存在
                    String[] id1 = idstr1.split(",");
                    for(int i=0;i<id1.length;i++){
                        int anwsercount = 0;
                        int zuijiacount = 0;
                        String id = id1[i];
                        pstmt = conn.prepareStatement(monthhuidasql);
                        pstmt.setInt(1, Integer.parseInt(id));
                        rs = pstmt.executeQuery();
                        if(rs.next()){
                            anwsercount = rs.getInt(1);
                        }
                        rs.close();
                        pstmt.close();

                        pstmt = conn.prepareStatement(monthzuijiasql);
                        pstmt.setInt(1, Integer.parseInt(id));
                        rs = pstmt.executeQuery();
                        if(rs.next()){
                            zuijiacount = rs.getInt(1);
                        }
                        rs.close();
                        pstmt.close();


                        if(anwsercount!=0){
                            System.out.println("--------id:"+id+"/比率："+(float)zuijiacount/anwsercount);
                            if((float)zuijiacount/anwsercount>0.5){
                                if(idstr2.equals("")){
                                    idstr2 = id;
                                }else{
                                    idstr2 = idstr2 + "," + id;
                                }
                            }
                        }
                    }
                }
            }

            pstmt = conn.prepareStatement(updatesql);
            pstmt.executeUpdate();
            pstmt.close();

            if(!idstr2.equals("")){
                String[] ids = idstr2.split(",");
                for(int i=0;i<ids.length;i++){
                    String id = ids[i];
                    pstmt = conn.prepareStatement(updateidsql);
                    pstmt.setInt(1, Integer.parseInt(id));
                    pstmt.executeUpdate();
                    pstmt.close();
                }

            }


        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("连接数据库失败");
            }
        }

    }
}
