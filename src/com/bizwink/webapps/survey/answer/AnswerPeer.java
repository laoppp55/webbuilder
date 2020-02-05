package com.bizwink.webapps.survey.answer;


import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AnswerPeer implements IAnswerManager {
    PoolServer cpool;

    public AnswerPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IAnswerManager getInstance() {
        return CmsServer.getInstance().getFactory().getAnswerManager();
    }

    private static String CREATE_ANSWER_USER = "insert into su_userinfo(username,gender,phone,email,ip) values (?, ?, ?, " +
            "?, ?);select scope_identity();";

    public int createAnswerUser(Answer answer) throws AnswerException {
        Connection conn = null;
        int userid = -1;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstmt = conn.prepareStatement(CREATE_ANSWER_USER);
                pstmt.setString(1, answer.getUsername());
                pstmt.setString(2, answer.getGender());
                pstmt.setString(3, answer.getPhone());
                pstmt.setString(4, answer.getEmail());
                pstmt.setString(5, answer.getIp());

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    userid = rs.getInt(1);
                }

                rs.close();
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
        return userid;
    }

    private static String CREATE_USER_ANSWERS_FOR_ORACLE = "insert into su_answer(userid, sid, qid, answers, other, id) values (?, ?, ?, ?, ?, ?)";

    private static String CREATE_USER_ANSWERS_FOR_MSSQL = "insert into su_answer(userid, sid, qid, answers, other) values (?, ?, ?, ?, ?)";

    private static String CREATE_USER_ANSWERS_FOR_MYSQL = "insert into su_answer(userid, sid, qid, answers, other) values (?, ?, ?, ?, ?)";

    public void createUserAnswers(int sid, int qid, int uid, String[] answers, int nother, String other) throws AnswerException {
        Connection conn = null;
        int id = 0;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                if (answers != null) {
                    for (int i = 0; i < answers.length; i++) {
                        PreparedStatement pstmt = null;
                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            pstmt = conn.prepareStatement(CREATE_USER_ANSWERS_FOR_ORACLE);
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            pstmt = conn.prepareStatement(CREATE_USER_ANSWERS_FOR_MSSQL);
                        else
                            pstmt = conn.prepareStatement(CREATE_USER_ANSWERS_FOR_MYSQL);

                        pstmt.setInt(1, uid);
                        pstmt.setInt(2, sid);
                        pstmt.setInt(3, qid);
                        pstmt.setString(4, filter.excludeHTMLCode(answers[i]));
                        if ((nother == 1) && (i == answers.length - 1)) {
                            pstmt.setString(5, other);
                        } else {
                            pstmt.setString(5, "");
                        }
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(6, sequenceMgr.getSequenceNum("Answer"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                        pstmt.close();
                    }
                }
                conn.commit();
                //}
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

    private static String GET_SURVEY_USERS_COUNT = "select count(id) from su_answer where sid = ?";

    public int getSurveyUsersCount(int sid) throws AnswerException {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_SURVEY_USERS_COUNT);
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static String GET_A_SURVEY_USERS_COUNT = "select count(uid) from su_answer where qid = ?";

    public int getASurveyUsersCount(int qid) throws AnswerException {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_A_SURVEY_USERS_COUNT);
            pstmt.setInt(1, qid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static String GET_A_QUESTION_USERS_COUNT = "select count(uid) from su_answer where answers = ? and qid = ?";

    public int getAQuestionUsersCount(String answers,int qid) throws AnswerException {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_A_QUESTION_USERS_COUNT);
            pstmt.setString(1, answers);
            pstmt.setInt(2,qid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    //收集用户信息 add by feixiang 2010-09-02
    private static String CREATE_USERINFO_FOR_ORACLE = "insert into su_defineuserinfo(sid,username,phone,age,email,id) values(?,?,?,?,?,?)";
    private static String CREATE_USERINFO_FOR_MSSQL = "insert into su_defineuserinfo(sid,username,phone,age,email) values(?,?,?,?,?)";
    private static String CREATE_USERINFO_FOR_MYSQL = "insert into su_defineuserinfo(sid,username,phone,age,email) values(?,?,?,?,?)";

    public void createUserinfoForDefine(Answer answer) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_USERINFO_FOR_ORACLE);
            else if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_USERINFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_USERINFO_FOR_MYSQL);
            pstmt.setInt(1, answer.getSid());
            pstmt.setString(2, answer.getUsername());
            pstmt.setString(3, answer.getPhone());
            pstmt.setString(4, answer.getAge());
            pstmt.setString(5, answer.getEmail());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(6, sequenceMgr.getSequenceNum("Defineuserinfo"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }
    //查询用户信息 add by feixiang 2010-09-06
    public List getUserinfo(int defineid)
    {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from su_defineuserinfo where sid = ? order by createdate desc");
            pstmt.setInt(1,defineid);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                Answer a = new Answer();
                a.setId(rs.getInt("id"));
                a.setSid(rs.getInt("sid"));
                a.setUsername(rs.getString("username"));
                a.setPhone(rs.getString("phone"));
                a.setAge(rs.getString("age"));
                a.setEmail(rs.getString("email"));
                a.setDatetime(rs.getTimestamp("createdate"));
                list.add(a);
            }
        }
        catch(SQLException e){
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
