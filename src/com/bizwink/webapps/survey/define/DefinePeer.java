package com.bizwink.webapps.survey.define;


import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DefinePeer implements IDefineManager {
    PoolServer cpool;

    public DefinePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IDefineManager getInstance() {
        return CmsServer.getInstance().getFactory().getDefineManager();
    }

    private static String GET_ALL_DEFINE_SURVEY = "select id,surveyname,notes,createtime,useflag from " +
            "su_survey where siteid = ? order by createtime desc";

    public List getAllDefineSurvey(int siteid, int startrow, int range) throws DefineException {
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEFINE_SURVEY);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Define define = loadsurvey(rs);
                list.add(define);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_DEFINE_SURVEY_NUM = "select count(id) from su_survey where siteid = ?";

    public int getAllDefineSurveyNum(int siteid) throws DefineException {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEFINE_SURVEY_NUM);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static String GET_ALL_DEFINE_QUESTIONS = "select id,sid,qname,qtype,qmust,nother,atype from " +
            "su_dquestion where sid = ? order by id desc";

    public List getAllDefineQuestionsBySID(int sid) throws DefineException {
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEFINE_QUESTIONS);
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Define define = loadquestion(rs);
                list.add(define);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_DEFINE_ANSWERS = "select id,qid,qanswer,picurl from " +
            "su_danswer where qid = ?";

    public List getAllDefineAnswersByQID(int qid) throws DefineException {
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEFINE_ANSWERS);
            pstmt.setInt(1, qid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Define define = loadanswer(rs);
                list.add(define);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_A_DEFINE_SURVEY = "select id,surveyname,notes,createtime,useflag from " +
            "su_survey where id = ?";

    public Define getADefineSurvey(int sid) throws DefineException {
        Connection conn = null;
        Define define = new Define();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_A_DEFINE_SURVEY);
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                define = loadsurvey(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return define;
    }

    private static String GET_A_DEFINE_QUESTION = "select id,sid,qname,qtype,qmust,nother,atype from su_dquestion where id = ?";

    public Define getADefineQuestion(int qid) throws DefineException {
        Connection conn = null;
        Define define = new Define();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_A_DEFINE_QUESTION);
            pstmt.setInt(1, qid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                define = loadquestion(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return define;
    }

    private static String GET_A_DEFINE_ANSWER = "select id,qid,qanswer,picurl from su_danswer where id = ?";

    public Define getADefineAnswer(int aid) throws DefineException {
        Connection conn = null;
        Define define = new Define();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_A_DEFINE_ANSWER);
            pstmt.setInt(1, aid);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                define = loadanswer(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return define;
    }

    private static String CREATE_DEFINE_SURVEY_FOR_ORACLE = "insert into su_survey(surveyname,notes,siteid,id) " +
            "values (?, ?, ?, ?)";

    private static String CREATE_DEFINE_SURVEY_FOR_MSSQL = "insert into su_survey(surveyname,notes,siteid) " +
            "values (?, ?, ?)";

    private static String CREATE_DEFINE_SURVEY_FOR_MYSQL = "insert into su_survey(surveyname,notes,siteid) " +
            "values (?, ?, ?)";

    public void createDefineSurvey(Define define) throws DefineException {
        Connection conn = null;
        int id = 0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                PreparedStatement pstmt = null;
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_SURVEY_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_SURVEY_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_DEFINE_SURVEY_FOR_MYSQL);
                pstmt.setString(1, define.getSurveyname());
                pstmt.setString(2, define.getNotes());
                pstmt.setInt(3, define.getSiteid());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(4, sequenceMgr.getSequenceNum("Survey"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String CREATE_DEFINE_QUESTION_FOR_ORACLE = "insert into su_dquestion(sid,qname,qtype,qmust,nother,atype,id) " +
            "values (?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_DEFINE_QUESTION_FOR_MSSQL = "insert into su_dquestion(sid,qname,qtype,qmust,nother,atype) " +
            "values (?, ?, ?, ?, ?, ?)";

    private static String CREATE_DEFINE_QUESTION_FOR_MYSQL = "insert into su_dquestion(sid,qname,qtype,qmust,nother,atype) " +
            "values (?, ?, ?, ?, ?, ?)";

    public void createDefineQuestion(Define define) throws DefineException {
        Connection conn = null;
        int id = 0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                PreparedStatement pstmt = null;
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_QUESTION_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_QUESTION_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_DEFINE_QUESTION_FOR_MYSQL);
                pstmt.setInt(1, define.getSid());
                pstmt.setString(2, define.getQname());
                pstmt.setInt(3, define.getQtype());
                pstmt.setInt(4, define.getQmust());
                pstmt.setInt(5, define.getNother());
                pstmt.setInt(6, define.getAtype());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(7, sequenceMgr.getSequenceNum("Dquestion"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }

                // pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                cpool.freeConnection(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String CREATE_DEFINE_ANSWER_FOR_ORACLE = "insert into su_danswer(qid,qanswer,picurl,id) " +
            "values (?, ?, ?, ?)";

    private static String CREATE_DEFINE_ANSWER_FOR_MSSQL = "insert into su_danswer(qid,qanswer,picurl,id) " +
            "values (?, ?, ?)";

    private static String CREATE_DEFINE_ANSWER_FOR_MYSQL = "insert into su_danswer(qid,qanswer,picurl,id) " +
            "values (?, ?, ?)";

    public void createDefineAnswer(Define define) throws DefineException {
        Connection conn = null;
        int id = 0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = null;
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_ANSWER_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_ANSWER_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_DEFINE_ANSWER_FOR_MYSQL);

                pstmt.setInt(1, define.getQid());
                pstmt.setString(2, define.getQanswer());
                pstmt.setString(3, define.getPicurl());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(4, sequenceMgr.getSequenceNum("Danswer"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }

                //pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String CREATE_DEFINE_PICANSWER_FOR_ORACLE = "insert into su_danswer(qid,picurl,id) " +
            "values (?, ?, ?)";

    private static String CREATE_DEFINE_PICANSWER_FOR_MSSQL = "insert into su_danswer(qid,picurl) " +
            "values (?, ?)";

    private static String CREATE_DEFINE_PICANSWER_FOR_MYSQL = "insert into su_danswer(qid,picurl) " +
            "values (?, ?)";

    public void createDefinePicAnswer(Define define) throws DefineException {
        Connection conn = null;
        int id = 0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                PreparedStatement pstmt = null;
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_PICANSWER_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_DEFINE_PICANSWER_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_DEFINE_PICANSWER_FOR_MYSQL);

                pstmt.setInt(1, define.getQid());
                pstmt.setString(2, define.getPicurl());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(3, sequenceMgr.getSequenceNum("Danswer"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String DELETE_DEFINE_SURVEY = "delete su_survey where id = ?";

    public void deleteDefineSurvey(int id) throws DefineException {
        Connection conn = null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(DELETE_DEFINE_SURVEY);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String DELETE_DEFINE_QUESTION = "delete su_dquestion where id = ?";

    public void deleteDefineQuestion(int qid) throws DefineException {
        Connection conn = null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(DELETE_DEFINE_QUESTION);
                pstmt.setInt(1, qid);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String DELETE_DEFINE_ANSWER = "delete su_danswer where id = ?";

    public void deleteDefineAnswer(int aid) throws DefineException {
        Connection conn = null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(DELETE_DEFINE_ANSWER);
                pstmt.setInt(1, aid);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_DEFINE_SURVEY = "update su_survey set surveyname = ?, notes = ? where id = ?";

    public void updateDefineSurvey(Define define) throws DefineException {
        Connection conn = null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(UPDATE_DEFINE_SURVEY);
                pstmt.setString(1, define.getSurveyname());
                pstmt.setString(2, define.getNotes());
                pstmt.setInt(3, define.getId());
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_DEFINE_QUESTION = "update su_dquestion set qname = ?, qtype = ?, qmust = ?, nother = ?, " +
            "atype = ? where id = ?";

    public void updateDefineQuestion(Define define) throws DefineException {
        Connection conn = null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(UPDATE_DEFINE_QUESTION);
                pstmt.setString(1, define.getQname());
                pstmt.setInt(2, define.getQtype());
                pstmt.setInt(3, define.getQmust());
                pstmt.setInt(4, define.getNother());
                pstmt.setInt(5, define.getAtype());
                pstmt.setInt(6, define.getQid());
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String UPDATE_DEFINE_ANSWER = "update su_danswer set qanswer = ? where id = ?";

    public void updateDefineAnswer(Define define) throws DefineException {
        Connection conn = null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(UPDATE_DEFINE_ANSWER);
                pstmt.setString(1, define.getQanswer());
                pstmt.setInt(2, define.getAid());
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Define loadsurvey(ResultSet rs) {
        Define define = new Define();

        try {
            define.setId(rs.getInt("id"));
            define.setSurveyname(rs.getString("surveyname"));
            define.setNotes(rs.getString("notes"));
            define.setCreatetime(rs.getTimestamp("createtime"));
            define.setUserflag(rs.getInt("useflag"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return define;
    }

    private Define loadquestion(ResultSet rs) {
        Define define = new Define();

        try {
            define.setQid(rs.getInt("id"));
            define.setSid(rs.getInt("sid"));
            define.setQname(rs.getString("qname"));
            define.setQtype(rs.getInt("qtype"));
            define.setQmust(rs.getInt("qmust"));
            define.setNother(rs.getInt("nother"));
            define.setAtype(rs.getInt("atype"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return define;
    }

    private Define loadanswer(ResultSet rs) {
        Define define = new Define();

        try {
            define.setAid(rs.getInt("id"));
            define.setQid(rs.getInt("qid"));
            define.setQanswer(rs.getString("qanswer"));
            define.setPicurl(rs.getString("picurl"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return define;
    }

    public int updateSurveyFlag(int id, int flag) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (flag == 0) {
                pstmt = conn.prepareStatement("update su_survey set useflag = 0 where id = ?");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }else{
                pstmt = conn.prepareStatement("update su_survey set useflag = 1 where id = ?");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                pstmt.close();

                /*pstmt = conn.prepareStatement("update su_survey set useflag = 0 where id <> ? and useflag = 1");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                pstmt.close();*/
                conn.commit();
            }


        }
        catch (Exception e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return code;
    }
    //get web view survey
    public int getWebViewSurvey(int siteid){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int id = 0;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from su_survey where useflag = 1 and siteid = ?");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e ){
            e.printStackTrace();
        }
        finally{
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return id;
    }

    //查询所有有效的调查信息
    private static String GET_ALL_DEFINE_SURVEY_FOR_MARK = "select id,surveyname,notes,createtime,useflag from " +
            "su_survey where siteid = ? and useflag = 1 order by createtime desc";

    public List getAllDefineSurveyForMark(int siteid) throws DefineException {
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEFINE_SURVEY_FOR_MARK);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Define define = loadsurvey(rs);
                list.add(define);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }
}
