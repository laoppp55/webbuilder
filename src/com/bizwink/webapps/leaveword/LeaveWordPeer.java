package com.bizwink.webapps.leaveword;

import com.bizwink.cms.security.IUserManager;
import com.bizwink.cms.security.User;
import com.bizwink.cms.security.UserPeer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.util.exportWriter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveWordPeer implements IWordManager{
    PoolServer cpool;

    public LeaveWordPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IWordManager getInstance() {
        return (IWordManager) CmsServer.getInstance().getFactory().getWordManager();
    }

    private static final String INSERT_WORD_FOR_ORACLE = "INSERT INTO TBL_LEAVEWORD(siteid,code,sex,mphone,formid,userid,title,content,writedate,company,linkman,links" +
            ",zip,email,phone,auditflag,auditor,id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_WORD_FOR_MSSQL = "INSERT INTO TBL_LEAVEWORD(siteid,code,sex,mphone,formid,userid,title,content,writedate,company,linkman,links" +
            ",zip,email,phone,auditflag,auditor) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_WORD_FOR_MYSQL = "INSERT INTO TBL_LEAVEWORD(siteid,code,sex,mphone,formid,userid,title,content,writedate,company,linkman,links" +
            ",zip,email,phone,auditflag,auditor) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void insertWord(Word word) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                //生成用户问题编码
                Timestamp c = new Timestamp(System.currentTimeMillis());
                String s_ct = c.toString();
                int posi = s_ct.indexOf(" ");
                s_ct = s_ct.substring(0,posi).trim();
                s_ct = StringUtil.replace(s_ct,"-","");
                int lsh = sequenceMgr.getLSH_Num();
                String lsh_s = String.valueOf(lsh);
                if (lsh_s.length() == 1) {
                    lsh_s = "00" + lsh_s;
                } else if (lsh_s.length() == 2) {
                    lsh_s = "0" + lsh_s;
                }
                String code = word.getPrefix() + s_ct + lsh_s;

                /*System.out.println("siteid=" + word.getSiteid());
                System.out.println("code=" + code);
                System.out.println("Sex=" + word.getSex());
                System.out.println("Mphone=" + word.getMphone());
                System.out.println("Formid=" + word.getFormid());
                System.out.println("Userid=" + word.getUserid());
                System.out.println("Title=" + word.getTitle());
                System.out.println("Content=" + word.getContent());
                System.out.println("c=" + c);
                System.out.println("Company=" + word.getCompany());
                System.out.println("Linkman=" + word.getLinkman());
                System.out.println("Links=" + word.getLinks());
                System.out.println("Zip=" + word.getZip());
                System.out.println("Email=" + word.getEmail());
                System.out.println("Phone=" + word.getPhone());
                System.out.println("Auditflag=" + word.getAuditflag());
                System.out.println("Auditor=" + word.getAuditor());
                */

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(INSERT_WORD_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(INSERT_WORD_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(INSERT_WORD_FOR_MYSQL);
                pstmt.setInt(1, word.getSiteid());
                pstmt.setString(2,code);
                pstmt.setInt(3,word.getSex());
                pstmt.setString(4,word.getMphone());
                pstmt.setInt(5, word.getFormid());
                pstmt.setString(6,word.getUserid());
                pstmt.setString(7, word.getTitle());
                pstmt.setString(8, word.getContent());
                pstmt.setTimestamp(9, c);
                pstmt.setString(10, word.getCompany());
                pstmt.setString(11, word.getLinkman());
                pstmt.setString(12, word.getLinks());
                pstmt.setString(13, word.getZip());
                pstmt.setString(14, word.getEmail());
                pstmt.setString(15, word.getPhone());
                pstmt.setInt(16,word.getAuditflag());
                pstmt.setString(17,word.getAuditor());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(18, sequenceMgr.getSequenceNum("LeaveWord"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }

                pstmt.close();
                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String GET_WORD = "SELECT * FROM TBL_LEAVEWORD where siteid = ? order by id desc";

    public List getAllWord(String sql) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List list = new ArrayList();
        Word word = new Word();

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                word = load(rs);
                list.add(word);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return list;
    }

    //sdate--开始日期
    //edate--结束日期
    //keyword--关键字   查询标题，内容，生成的用户码
    //siteid--站点ID
    //lwid--留言板ID
    //infotype--信息类型 1已经回答 2未回答 3有效 4无效 5全部
    //usertype--0留言板管理员 1留言板部门管理员 2普通员工
    //department--查询信息的员工所在部门的ID
    //userid--查询用户的ID
    public List searchLW(String sdate,String edate,String keyword,int infotype,String department,String userid,int startindex,int range,int siteid,int lwid,int usertype) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List list = new ArrayList();
        Word word = new Word();
        String SEARCH_LW = "SELECT * FROM TBL_LEAVEWORD where siteid = ? and formid=? ";
        if (usertype == 1) SEARCH_LW = SEARCH_LW + " and departmentid=" + department;
        if (usertype == 2) SEARCH_LW = SEARCH_LW + " and departmentid=" + department + " and userid='" + userid + "'";
        if (infotype == 1) SEARCH_LW = SEARCH_LW + " and retcontent is not null";
        if (infotype == 2) SEARCH_LW = SEARCH_LW + " and retcontent is null";
        if (infotype == 3) SEARCH_LW = SEARCH_LW + " and valid = 0";
        if (infotype == 4) SEARCH_LW = SEARCH_LW + " and valid = 1";
        if (keyword != null && keyword !="") SEARCH_LW = SEARCH_LW + " and (title like '%" + keyword + "%' or  content like '%" + keyword + "%')";
        if (sdate != null && sdate != "") SEARCH_LW = SEARCH_LW + " and writedate > to_date('" + sdate + "','yyyy-MM-dd')";
        if (edate != null && edate != "") SEARCH_LW = SEARCH_LW + " and writedate < to_date('" + edate + "','yyyy-MM-dd')";

        SEARCH_LW = SEARCH_LW + " order by writedate desc";

        System.out.println(SEARCH_LW);

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SEARCH_LW);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,lwid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                word = load(rs);
                list.add(word);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return list;
    }

    //sdate--开始日期
    //edate--结束日期
    //keyword--关键字   查询标题，内容，生成的用户码
    //siteid--站点ID
    //lwid--留言板ID
    public List searchLWForWeb(String sdate,String edate,String keyword,int startindex,int range,int siteid,int lwid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List list = new ArrayList();
        Word word = new Word();
        String SEARCH_LW = "SELECT * FROM TBL_LEAVEWORD where siteid = ? and formid=? and flag=1 ";
        if (keyword != null && keyword !="") SEARCH_LW = SEARCH_LW + " and (title like '%" + keyword + "%' or  content like '%" + keyword + "%')";
        if (sdate != null && sdate != "") SEARCH_LW = SEARCH_LW + " and writedate > to_date('" + sdate + "','yyyy-MM-dd')";
        if (edate != null && edate != "") SEARCH_LW = SEARCH_LW + " and writedate < to_date('" + edate + "','yyyy-MM-dd')";

        SEARCH_LW = SEARCH_LW + " order by writedate desc";

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SEARCH_LW);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,lwid);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startindex; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                word = load(rs);
                list.add(word);
            }

            //while (rs.next()) {
            //    word = load(rs);
            //    list.add(word);
            //}

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return list;
    }

    //sdate--开始日期
    //edate--结束日期
    //keyword--关键字   查询标题，内容，生成的用户码
    //siteid--站点ID
    //lwid--留言板ID
    public int getCountsearchLWForWeb(String sdate,String edate,String keyword,int startindex,int range,int siteid,int lwid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count = 0;
        String SEARCH_LW = "SELECT count(id) FROM TBL_LEAVEWORD where siteid = ? and formid=?  and flag=1 ";
        if (keyword != null && keyword !="") SEARCH_LW = SEARCH_LW + " and (title like '%" + keyword + "%' or  content like '%" + keyword + "%')";
        if (sdate != null && sdate != "") SEARCH_LW = SEARCH_LW + " and writedate > to_date('" + sdate + "','yyyy-MM-dd')";
        if (edate != null && edate != "") SEARCH_LW = SEARCH_LW + " and writedate < to_date('" + edate + "','yyyy-MM-dd')";

        SEARCH_LW = SEARCH_LW + " order by writedate desc";

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SEARCH_LW);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,lwid);
            rs = pstmt.executeQuery();

            if (rs.next()) count = rs.getInt(1);

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return count;
    }

    private static final String GET_WORDS_AUTHORIZED = "select siteid,userid,lwid,lwrole,contenttype from tbl_member_authorized_resouce where siteid = ? and userid=?";

    public List getWordAuthorizedUser(String userid,int siteid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        authorizedform f1 = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_WORDS_AUTHORIZED);
            pstmt.setInt(1,siteid);
            pstmt.setString(2,userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                f1 = new authorizedform();
                f1.setUserid(userid);
                f1.setSiteid(siteid);
                f1.setLwid(rs.getInt("lwid"));
                f1.setLwrole(StringUtil.gb2iso4View(rs.getString("lwrole")));
                f1.setContenttyep(rs.getInt("contenttype"));
                list.add(f1);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs!=null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }
        return list;
    }

    public List getCurrentWord(int siteid, int formid, int auditflag,int startIndex, int numResult) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List list = new ArrayList();
        Word word = new Word();
        String sql = null;
        if (auditflag == 1)
            sql = "select * from TBL_LEAVEWORD where siteid = ? and formid=? and flag = 1" + " order by writedate desc";
        else
            sql = "select * from TBL_LEAVEWORD where siteid = ? and formid=?" + " order by writedate desc";

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,formid);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResult && rs.next(); i++) {
                word = load(rs);
                list.add(word);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return list;
    }

    public int getTotalWord(int siteid,int formid,int auditflag) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int total = 0;
        String sql = null;
        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            if (auditflag == 1)
                sql = "select count(id) from TBL_LEAVEWORD where siteid = ? and formid=? and flag = 1";
            else
                sql = "select count(id) from TBL_LEAVEWORD where siteid = ? and formid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,formid);
            rs = pstmt.executeQuery();
            if (rs.next()) total = rs.getInt(1);
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return total;
    }

    private static final String GET_A_WORD = "SELECT * FROM TBL_LEAVEWORD where id = ?";

    public Word getAWord(int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Word word = new Word();

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_WORD);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                word = load(rs);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return word;
    }


    private static final String GET_WORD_BYUSERID = "SELECT * FROM TBL_LEAVEWORD where userid = ? and siteid=? order by writedate desc";

    public Word getAWordByUserid(String userid,int siteid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Word word = null;

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_WORD_BYUSERID);
            pstmt.setString(1,userid);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                word = new Word();
                word = load(rs);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return word;
    }

    private static final String GET_ALL_RETCONTENT_FOR_WORD = "SELECT processor,content,retdate,createdate FROM tbl_leaveword_to_dept where lwid = ?";

    public Word getAWordIncludeAllRetContent(int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Word word = null;
        ResultSet rs = null;
        Anwser anwser = null;
        List anwsers = new ArrayList();
        IUserManager userMgr = UserPeer.getInstance();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_WORD);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                word = new Word();
                word = load(rs);
            }
            rs.close();
            pstmt.close();

            if (word!=null) {
                pstmt = conn.prepareStatement(GET_ALL_RETCONTENT_FOR_WORD);
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    anwser = new Anwser();
                    anwser.setContent(rs.getString("content"));
                    User user = userMgr.getUser(rs.getString("processor"));
                    anwser.setProcessor(user.getNickName());
                    anwser.setRetdate(rs.getTimestamp("retdate"));
                    anwser.setCreatedate(rs.getTimestamp("createdate"));
                    anwsers.add(anwser);
                }
                rs.close();
                pstmt.close();
                word.setAnwserfordept(anwsers);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs!=null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }
        return word;
    }

    private static final String GET_AWORD_FORDEPT = "select content from tbl_leaveword_to_dept where siteid=? and lwid = ? and formid=? and processor=?";

    public String getAWordForDept(int siteid, int formid,int lwid,String processor) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String content = null;
        ResultSet rs = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_AWORD_FORDEPT);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, lwid);
            pstmt.setInt(3, formid);
            pstmt.setString(4,processor);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                content = rs.getString("content");
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }
        return content;
    }

    private static final String GET_LEAVEWORD_VALID_REASON = "SELECT * FROM tbl_leaveword_valid_reason where siteid=?";

    public List getValidReson(int siteid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String content = null;
        List reason = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_LEAVEWORD_VALID_REASON);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                content = null;
                content = rs.getString("content");
                reason.add(content);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }

        return reason;
    }

    private static final String SET_LEAVEWORD_VALID_FLAG_TO_1 = "update tbl_leaveword set valid=1,validreason=? where id=? and siteid=?";

    private static final String SET_LEAVEWORD_VALID_FLAG_TO_0 = "update tbl_leaveword set valid=0,validreason=null where id=? and siteid=?";

    public boolean setValidForAWord(int siteid,int id,String reason,int validvalue) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean validflag = false;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (validvalue == 1) {
                pstmt = conn.prepareStatement(SET_LEAVEWORD_VALID_FLAG_TO_1);
                pstmt.setString(1,reason);
                pstmt.setInt(2,id);
                pstmt.setInt(3,siteid);
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                pstmt = conn.prepareStatement(SET_LEAVEWORD_VALID_FLAG_TO_0);
                pstmt.setInt(1,id);
                pstmt.setInt(2,siteid);
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
            validflag = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }

        return validflag;
    }

    public void deleteWord(int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from TBL_LEAVEWORD where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement("delete from tbl_leaveword_to_dept where lwid = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }
    }

    private Word load(ResultSet rs) {
        Word word = new Word();
        try {
            word.setId(rs.getInt("id"));
            word.setSiteid(rs.getInt("siteid"));
            word.setColumnid(rs.getInt("columnid"));
            word.setCode(rs.getString("code"));
            word.setTitle(rs.getString("title"));
            word.setContent(rs.getString("content"));
            word.setWritedate(rs.getTimestamp("writedate"));
            word.setCompany(rs.getString("company"));
            word.setLinkman(rs.getString("linkman"));
            word.setLinks(rs.getString("links"));
            word.setEmail(rs.getString("email"));
            word.setZip(rs.getString("zip"));
            word.setPhone(rs.getString("phone"));
            word.setFlag(rs.getInt("flag"));
            word.setUserid(rs.getString("userid"));
            word.setRetconent(rs.getString("retcontent"));
            word.setDepartmentid(rs.getInt("departmentid"));
            word.setAuditflag(rs.getInt("auditflag"));
            word.setAuditor(rs.getString("auditor"));
            word.setProcessor(rs.getString("processor"));
            word.setValid(rs.getInt("valid"));
            word.setValidreason(rs.getString("validreason"));
            word.setFinalflag(rs.getInt("finalflag"));
            word.setEndtouser(rs.getTimestamp("endtouser"));
            word.setDatefromdept(rs.getTimestamp("datefromdept"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return word;
    }

    //update flag by feixiang 2009-03-04
    public void updateFlag(int id, int flag) {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update TBL_LEAVEWORD set flag = ? where id = ?");
            pstmt.setInt(1, flag);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
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
    }

    //update by Vincent 2010-06-02 获取留言
    private static final String GET_MESSAGE = "select * from TBL_LEAVEWORD where SITEID = '1411' order by WRITEDATE desc";

    public List getMassage() throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Word word = new Word();
        List list = new ArrayList();
        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_MESSAGE);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                word = load(rs);
                list.add(word);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                System.out.println("Error in closing the pooled connection " + e.toString());
            }
        }
        return list;
    }

    //留言板管理员更新回复
    public void updateRetcontent(int id, String retcontent) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update TBL_LEAVEWORD set RETCONTENT = ?,datefromdept=?,finalflag=1 where id = ?");
            pstmt.setString(1,retcontent);
            pstmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            try{
                if(conn != null) cpool.freeConnection(conn);
            }catch(Exception e){
                System.out.println("Error in closing the pooled connection" + e.toString());
            }
        }
    }

    public void updateRetcontentForLWManager(int id, String retcontent,String username) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update TBL_LEAVEWORD set RETCONTENT = ?,datefromdept=?,finalflag=1,processor=?,endtouser=? where id = ?");
            pstmt.setString(1,retcontent);
            pstmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            pstmt.setString(3,username);
            pstmt.setTimestamp(4,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(5,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            try{
                if(conn != null) cpool.freeConnection(conn);
            }catch(Exception e){
                System.out.println("Error in closing the pooled connection" + e.toString());
            }
        }
    }

    //留言板部门管理员和其他人员更新回复
    public void updateRetcontentFromDept(int siteid,int formid,int lwid,String userid, String retcontent) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_leaveword_to_dept set content = ?,flag=1,retdate=? where lwid = ? and processor=? and formid=? and siteid=?");
            pstmt.setString(1,retcontent);
            pstmt.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3,lwid);
            pstmt.setString(4,userid);
            pstmt.setInt(5,formid);
            pstmt.setInt(6,siteid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            try{
                if (pstmt != null) pstmt.close();
                if(conn != null) cpool.freeConnection(conn);
            }catch(Exception e){
                System.out.println("Error in closing the pooled connection" + e.toString());
            }
        }
    }

    private static final String GET_ANWSER_BYUSERID = "select content from tbl_leaveword_to_dept where processor=? and lwid=?";

    public int haveAnwserFromEmployee(int id, String processor) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int anwser_count = 0;
        String content = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ANWSER_BYUSERID);
            pstmt.setString(1,processor);
            pstmt.setInt(2,id);
            rs  = pstmt.executeQuery();
            if (rs.next()) content = rs.getString("content");
            if (content != null) anwser_count = 1;
            rs.close();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return anwser_count;
    }

    //确认对用户的最终回答，并修改最终回复结果和发布标志位
    public void writeFinalResult(int id, String retcontent) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update TBL_LEAVEWORD set endtouser=?,finalflag=1 where id = ?");
            pstmt.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }
    }

    //确认对用户的最终回答，并修改最终回复结果和发布标志位
    public String exportContentForLWManagerOfDept(int siteid,int markid,String userid,String department,String sitename,String path) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null,pstmt1=null;
        ResultSet rs = null,rs1=null;
        String filename = null;
        Word word = null;
        List list = new ArrayList();
        List anwsers = null;
        Anwser anwser = null;
        int departmentid = Integer.parseInt(department);
        IUserManager userMgr = UserPeer.getInstance();

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id,title,content,retcontent,siteid,formid from tbl_leaveword where siteid=? and formid=? and departmentid=? order by writedate desc");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,markid);
            pstmt.setInt(3,departmentid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                anwser = null;
                anwsers = new ArrayList();
                word = new Word();
                word.setId(rs.getInt("id"));
                word.setContent(rs.getString("content"));
                word.setTitle(rs.getString("title"));
                word.setRetconent(rs.getString("retcontent"));
                word.setSiteid(rs.getInt("siteid"));
                word.setFormid(rs.getInt("formid"));

                //获取该问题在本部门的所有答案
                pstmt1 = conn.prepareStatement("select processor,content,retdate,createdate from tbl_leaveword_to_dept where lwid=? and siteid=? and formid=? and departmentid=? and flag=1 order by createdate desc");
                pstmt1.setInt(1,word.getId());
                pstmt1.setInt(2,siteid);
                pstmt1.setInt(3,markid);
                pstmt1.setInt(4,departmentid);
                rs1 = pstmt1.executeQuery();
                while(rs1.next()) {
                    anwser = new Anwser();
                    anwser.setContent(rs1.getString("content"));
                    User user = userMgr.getUser(rs1.getString("processor"));
                    anwser.setProcessor(user.getNickName());
                    anwser.setRetdate(rs1.getTimestamp("retdate"));
                    anwser.setCreatedate(rs1.getTimestamp("createdate"));
                    anwsers.add(anwser);
                }
                rs1.close();
                pstmt1.close();

                //如果该问题有答案，则将该问题导出，请领导批准׼
                if (anwsers != null) {
                    word.setAnwserfordept(anwsers);
                    list.add(word);
                }
            }
            rs.close();
            pstmt.close();

            exportWriter e = new exportWriter();
            filename = e.exportRTFContext(list,userid,sitename,path);
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return filename;
    }

    //ȷ确认对用户的最终回答，并修改最终回复结果和发布标志位
    public String exportContentForLWManager(int siteid,int markid,String userid,String sitename,String path) throws WordException{
        Connection conn = null;
        PreparedStatement pstmt = null,pstmt1=null;
        ResultSet rs = null,rs1=null;
        String filename = null;
        Word word = null;
        List list = new ArrayList();

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id,title,content,retcontent,siteid,formid,endtouser from tbl_leaveword where siteid=? and formid=? and processor=? and flag=0  order by writedate desc");
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,markid);
            pstmt.setString(3,userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                word = new Word();
                word.setId(rs.getInt("id"));
                word.setContent(rs.getString("content"));
                word.setTitle(rs.getString("title"));
                word.setRetconent(rs.getString("retcontent"));
                word.setSiteid(rs.getInt("siteid"));
                word.setFormid(rs.getInt("formid"));
                word.setUserid(userid);
                word.setEndtouser(rs.getTimestamp("endtouser"));
                list.add(word);
            }
            rs.close();
            pstmt.close();

            exportWriter e = new exportWriter();
            filename = e.exportRTFContextForLWManager(list,userid,sitename,path);
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            {
                try{
                    if (pstmt!=null) pstmt.close();
                    if(conn != null) cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }

        return filename;
    }

    public List getCurrentWord(String sql,int startIndex, int numResult) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        List list = new ArrayList();
        Word word = new Word();

        try {
            ResultSet rs = null;
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResult && rs.next(); i++) {
                word = load(rs);
                list.add(word);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
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
        return list;
    }

    //更新部门信息
    private String SQL_DEPARTURE_TO_DEPT_FOR_ORACLE="insert into tbl_leaveword_to_dept(siteid,departmentid,formid,lwid,processor,id) " +
            "values (?, ?, ?, ?, ?, ?)";

    private String SQL_DEPARTURE_TO_DEPT_FOR_MSSQL="insert into tbl_leaveword_to_dept(siteid,departmentid,formid,lwid,processor) " +
            "values (?, ?, ?, ?, ?)";

    private String SQL_DEPARTURE_TO_DEPT_FOR_MYSQL="insert into tbl_leaveword_to_dept(siteid,departmentid,formid,lwid,processor) " +
            "values (?, ?, ?, ?, ?)";

    public void updateDepartmentForLeaveWord(int siteid,int formid,int id,int departmentid,String processor)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_DEPARTURE_TO_DEPT_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_DEPARTURE_TO_DEPT_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_DEPARTURE_TO_DEPT_FOR_MYSQL);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,departmentid);
            pstmt.setInt(3,formid);
            pstmt.setInt(4,id);
            pstmt.setString(5,processor.trim());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(6, sequenceMgr.getSequenceNum("LeaveWord"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();

            pstmt = conn.prepareStatement("update TBL_LEAVEWORD set departmentid = ?,processor=? where id = ?");
            pstmt.setInt(1,departmentid) ;
            pstmt.setString(2,processor);
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    System.out.println("Error in closing the pooled connection" + e.toString());
                }
            }
        }
    }

    public boolean retWordToManager(String userid,int siteid,int lwid,int id,int managertype)  throws WordException {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String processor = "";

        try{
            conn = cpool.getConnection();
            //获取留言板的管理员用户名
            String deptno = null;
            if (managertype == 0) {
                pstmt = conn.prepareStatement("select userid,department from tbl_members where siteid = ? and userid=?");
                pstmt.setInt(1,siteid);
                pstmt.setString(2,userid);
                rs = pstmt.executeQuery();
                if (rs.next()) deptno = rs.getString("department");
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement("select a.siteid,a.userid,a.lwid,a.lwrole from tbl_member_authorized_resouce a,tbl_members b where a.userid=b.userid and b.department=? and a.siteid = ? and a.lwid=? and a.lwrole='���԰岿�Ź���Ա'");
                pstmt.setString(1,deptno);
                pstmt.setInt(2,siteid);
                pstmt.setInt(3,lwid);
                rs = pstmt.executeQuery();
                if (rs.next()) processor = rs.getString("userid");
                rs.close();
                pstmt.close();
            }

            conn.setAutoCommit(false);
            if (managertype == 0) {                          //普通员工回退信息给留言板部门管理员
                pstmt = conn.prepareStatement("update TBL_LEAVEWORD set processor=? where id = ?");
                pstmt.setString(1,processor);
                pstmt.setInt(2,id);
            } else {                                          //留言板部门管理员回退信息给留言板管理员
                pstmt = conn.prepareStatement("update TBL_LEAVEWORD set processor=?,departmentid=? where id = ?");
                pstmt.setNull(1,java.sql.Types.VARCHAR);
                pstmt.setNull(2,java.sql.Types.INTEGER);
                pstmt.setInt(3,id);
            }
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            success = true;
        }catch(Exception e){
            try{
                conn.rollback();
            }catch(Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            try{
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if(conn != null) cpool.freeConnection(conn);
            }catch(Exception e){
                System.out.println("Error in closing the pooled connection" + e.toString());
            }

        }

        return success;
    }


    //以下为留言无效的词条管理模块
    private static final String GET_ALL_CITIAO = "SELECT id,siteid,content FROM tbl_leaveword_valid_reason where siteid = ? order by id desc";

    public List getAllCitiaos(int siteid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        CiTiao citiao = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_CITIAO);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                citiao = new CiTiao();
                citiao.setId(rs.getInt("id"));
                citiao.setSiteid(rs.getInt("siteid"));
                citiao.setContent(rs.getString("content"));
                list.add(citiao);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if(rs!=null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String GET_A_CITIAO = "SELECT id,siteid,content FROM tbl_leaveword_valid_reason where siteid=? and id=?";

    public CiTiao getACitiao(int siteid,int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CiTiao citiao = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_CITIAO);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                citiao = new CiTiao();
                citiao.setId(rs.getInt("id"));
                citiao.setSiteid(rs.getInt("siteid"));
                citiao.setContent(rs.getString("content"));
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if(rs!=null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return citiao;
    }

    private static final String INSERT_CITIAO = "insert into tbl_leaveword_valid_reason (id,siteid,content) values(?,?,?)";

    public void insertCitiao(CiTiao citiao) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT_CITIAO);
            pstmt.setInt(1,sequenceMgr.getSequenceNum("LeaveWord"));
            pstmt.setInt(2,citiao.getSiteid());
            pstmt.setString(3,citiao.getContent());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String UPDATE_CITIAO = "update tbl_leaveword_valid_reason set content=? where siteid=? and id=?";

    public void updateCitiao(CiTiao citiao) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_CITIAO);
            pstmt.setString(1,citiao.getContent());
            pstmt.setInt(2,citiao.getSiteid());
            pstmt.setInt(3,citiao.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String DELETE_CITIAO = "delete from tbl_leaveword_valid_reason where siteid=? and id=?";

    public void deleteCitiao(int siteid,int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_CITIAO);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    //以下部分为留言分类管理部分
    private static final String INSERT_LW_COLUMN = "insert into tbl_leavewordclass(id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,summary,lwnum,createdate,lastupdated) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void insertColumn(Column column) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT_LW_COLUMN);
            pstmt.setInt(1,sequenceMgr.getSequenceNum("LeaveWord"));
            pstmt.setInt(2,column.getSiteid());
            pstmt.setInt(3,column.getParentid());
            pstmt.setInt(4,column.getOrderid());
            pstmt.setString(5,column.getCname());
            pstmt.setString(6,column.getEname());
            pstmt.setString(7,column.getDirname());
            pstmt.setString(8,column.getEditor());
            pstmt.setString(9,column.getExtname());
            pstmt.setString(10,column.getSummary());
            pstmt.setInt(11,column.getLwnum());
            pstmt.setTimestamp(12,column.getCreatedate());
            pstmt.setTimestamp(13,column.getLastupdated());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String GET_A_COLUMN = "SELECT id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,summary,lwnum,createdate,lastupdated " +
            "FROM tbl_leavewordclass where siteid=? and id=?";

    public Column getAColumn(int siteid,int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Column column = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_COLUMN);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                column = new Column();
                column.setId(rs.getInt("id"));
                column.setSiteid(rs.getInt("siteid"));
                column.setOrderid(rs.getInt("orderid"));
                column.setParentid(rs.getInt("parentid"));
                column.setCname(rs.getString("cname"));
                column.setEname(rs.getString("ename"));
                column.setDirname(rs.getString("dirname"));
                column.setEditor(rs.getString("editor"));
                column.setExtname(rs.getString("extname"));
                column.setSummary(rs.getString("summary"));
                column.setLwnum(rs.getInt("lwnum"));
                column.setCreatedate(rs.getTimestamp("createdate"));
                column.setLastupdated(rs.getTimestamp("lastupdated"));
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if(rs!=null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return column;
    }

    private static final String GET_A_COLUMN_NAME = "SELECT id,siteid,cname FROM tbl_leavewordclass where siteid=? and id=?";

    public String getAColumnName(int siteid,int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String columnname = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_A_COLUMN_NAME);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                columnname = rs.getString("cname");
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (rs!=null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return columnname;
    }

    private static final String GET_ALL_COLUMN = "SELECT id,siteid,parentid,orderid,cname,ename,dirname,editor,extname,summary,lwnum,createdate,lastupdated " +
            "FROM tbl_leavewordclass where siteid=? order by id desc";

    public List getALLColumn(int siteid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Column column = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_COLUMN);
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                column = new Column();
                column.setId(rs.getInt("id"));
                column.setSiteid(rs.getInt("siteid"));
                column.setOrderid(rs.getInt("orderid"));
                column.setParentid(rs.getInt("parentid"));
                column.setCname(rs.getString("cname"));
                column.setEname(rs.getString("ename"));
                column.setDirname(rs.getString("dirname"));
                column.setEditor(rs.getString("editor"));
                column.setExtname(rs.getString("extname"));
                column.setSummary(rs.getString("summary"));
                column.setLwnum(rs.getInt("lwnum"));
                column.setCreatedate(rs.getTimestamp("createdate"));
                column.setLastupdated(rs.getTimestamp("lastupdated"));
                list.add(column);
            }

            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if(rs!=null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String UPDATE_COLUMN = "update tbl_leavewordclass set cname=?,ename=?,editor=?,lastupdated=? where siteid=? and id=?";

    public void updateColumn(Column column) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_COLUMN);
            pstmt.setString(1,column.getCname());
            pstmt.setString(2,column.getEname());
            pstmt.setString(3,column.getEditor());
            pstmt.setTimestamp(4,column.getLastupdated());
            pstmt.setInt(5,column.getSiteid());
            pstmt.setInt(6,column.getId());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String UPDATE_COLUMN_FOR_ITEM = "update tbl_leaveword set columnid=? where siteid=? and id=?";

    public void updateColumnForItem(int siteid,int markid,int id,int columnid) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(UPDATE_COLUMN_FOR_ITEM);
            pstmt.setInt(1,columnid);
            pstmt.setInt(2,siteid);
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String DELETE_COLUMN = "delete from tbl_leavewordclass where siteid=? and id=?";

    public void deleteColumn(int siteid,int id) throws WordException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(DELETE_COLUMN);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }
}