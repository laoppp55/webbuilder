package com.bizwink.wenba;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;

/**
 * Title:        CRM server
 * Description:  Internet Portal Server
 * Copyright:    Copyright (c) 2005
 * Company:
 *
 * @author Peter Song
 * @version 1.0
 */

public class wenbaManagerImpl implements IWenbaManager {
    PoolServer cpool;

    public wenbaManagerImpl(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IWenbaManager getInstance() {
        return (IWenbaManager) CmsServer.getInstance().getFactory().getWenbaManager();
    }

    private static final String SQL_GETCOLUMN = "SELECT id,siteid,dirname,orderid,parentid,ename,cname,creater,status,createdate " +
            "FROM fawu_wenti_column WHERE ID = ?";

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
                //  System.out.println("SQL_GETCOLUMN="+SQL_GETCOLUMN+ID+"*********************");
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
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return existflag;
    }

    private static final String SQL_CREATECOLUMN =
            "INSERT INTO fawu_wenti_column (siteID,Dirname,OrderID,parentID,Cname,Ename,status,creater,CreateDate,ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void create(wenbaImpl column) throws wenbaException {
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Tree colTree = TreeManager.getInstance().getWenbaTree();

        if (column.getParentID() != colTree.getTreeRoot())
            column.setDirName(colTree.getDirName(colTree, column.getParentID()) + column.getEName() + "/");
        else
            column.setDirName("/" + column.getEName() + "/");

        int orderID = getNextOrder(column.getParentID());
        if (column.getParentID() == 0)                  //如果页面传过来的父栏目ID为零，设置父栏目为该站点的根
        {
            column.setParentID(colTree.getTreeRoot());
        }

        try {
            try {
                column.setOrderID(orderID);
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_CREATECOLUMN);
                pstmt.setInt(1, column.getSiteID());
                pstmt.setString(2, column.getDirName());
                pstmt.setInt(3, column.getOrderID());
                pstmt.setInt(4, column.getParentID());
                pstmt.setString(5, column.getCName().trim());
                pstmt.setString(6, column.getEName().trim());
                pstmt.setInt(7, 0);
                pstmt.setString(8, "admin");
                pstmt.setTimestamp(9, column.getCreationDate());
                pstmt.setInt(10, sequnceMgr.nextID("wenba"));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new wenbaException("Database exception: create column failed.");
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
        catch (SQLException e) {
            throw new wenbaException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATECOLUMN =
            "UPDATE fawu_wenti_column SET Cname = ?,OrderID = ?" +
                    "" +
                    "WHERE ID = ?";

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
                //   System.out.println("SQL_UPDATECOLUMN="+SQL_UPDATECOLUMN);
                pstmt.setString(1, StringUtil.iso2gb(column.getCName()));
                pstmt.setInt(2, column.getOrderID());
                pstmt.setInt(3, column.getID());
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
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return order;
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

    public int count(int columnid) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select count(*) from fawu_wenti t where columnid= " + columnid);
            res = pstmt.executeQuery();
            if (res.next()) {
                i = res.getInt(1);
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }

        return i;
    }

    //SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from en_pinglun b order by id desc)  A  WHERE  ROWNUM  <=   "+(num+6)+" and tupian="+pic+" and status="+1+"  ) WHERE  RN  >   "+num+" ");
    public List getWenttiList(int ipage, int columnid) {
        List list = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        int num = 10 * (ipage - 1);
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from fawu_wenti  b order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and columnid=" + columnid + "  ) WHERE  RN  >   " + num + " ");
            ResultSet res = pstmt.executeQuery();
            //   System.out.println("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from fawu_wenti  b order by id desc)  A  WHERE  ROWNUM  <=   "+(num+6)+" and columnid="+columnid+"  ) WHERE  RN  >   "+num+" ");
            while (res.next()) {
                // System.out.println("aaaaaaaaaaaaaaaaaa");
                Wenti w = new Wenti();
                w.setId(res.getInt("id"));
                w.setColumnid(res.getInt("columnid"));
                w.setCname(res.getString("cname"));
                w.setQuestion(res.getString("question"));
                w.setStatus(res.getInt("status"));
                w.setVoteflag(res.getInt("voteflag"));
                w.setXuanshang(res.getInt("xuanshang"));
                w.setAnswernum(res.getInt("answernum"));
                w.setSource(res.getString("source"));
                w.setCreatedate(res.getString("createdate"));
                w.setIpaddress(res.getString("ipaddress"));
                w.setCreate(res.getString("creater"));
                w.setProvince(res.getString("province"));
                w.setCity(res.getString("city"));
                w.setArea(res.getString("area"));
                w.setEmailnotify(res.getInt("emailnotify"));
                w.setIstop(res.getInt("istop"));
                w.setUsername(res.getString("username"));
                list.add(w);
            }
            res.close();
            pstmt.close();


        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public int deleteQuestion(int id) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("delete from fawu_wenti where id=" + id);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();
            //并把答案也一起删除
            pstmt = con.prepareStatement("delete from fawu_anwser where qid=" + id);
            pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return i;
    }

    public int updateQuestionstatus(int id, int status) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("update fawu_wenti set status=" + status + "   where id=" + id);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                }
            }
        }
        return i;
    }

    public List getAnswer(int id) {
        List list = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select * from fawu_anwser where qid=" + id);
            res = pstmt.executeQuery();
            while (res.next()) {
                Answer a = new Answer();
                a.setId(res.getInt("id"));
                a.setQid(res.getInt("qid"));
                a.setAnwser(res.getString("anwser"));
                a.setVotenum(res.getInt("votenum"));
                a.setIpaddress(res.getString("ipaddress"));
                a.setUserid(res.getInt("userid"));
                a.setUsername(res.getString("username"));
                a.setCreatedate(res.getString("createdate"));
                a.setFenshu(res.getInt("fenshu"));
                a.setCankaoziliao(res.getString("cankaoziliao"));
                list.add(a);
            }
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public String getWenti(int id) {
        String wenti = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select *from fawu_wenti where id=" + id);
            res = pstmt.executeQuery();
            if (res.next()) {
                wenti = res.getString("question");
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return wenti;
    }

    public int deleteAnswer(int id,int qid,String username,int userid) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("delete from fawu_anwser where id=" + id);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();

            con.setAutoCommit(false);
            String sql="update userinfo set user_grade=(select user_grade-3 from userinfo where user_name='"+username+"') where user_name='"+username+"'";

            pstmt=con.prepareStatement(sql);
            i= pstmt.executeUpdate();
            pstmt.close();
            con.commit();

            con.setAutoCommit(false);
            sql="update fawu_wenti set answernum=(select answernum-1 from fawu_wenti where id="+qid+") where id="+qid;
            pstmt=con.prepareStatement(sql);
            i= pstmt.executeUpdate();
            pstmt.close();
            con.commit();
            System.out.println("sql="+sql);
            con.setAutoCommit(false);
            sql="update userinfo set meilizhi=(select meilizhi-4 from userinfo where user_name='"+username+"') where user_name='"+username+"'";
            pstmt=con.prepareStatement(sql);
            i= pstmt.executeUpdate();
            pstmt.close();
            con.commit();
            weekgrade(userid,-5);

        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return i;
    }

    //用户表查看
    public List getRegister(int ipage, int itype) {
        List list = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        int num = 10 * (ipage - 1);
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + "  ) WHERE  RN  >   " + num + " ");
            ResultSet res = pstmt.executeQuery();
            //   System.out.println("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and itype="+itype+"  ) WHERE  RN  >   " + num + " ");
            //   System.out.println("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from fawu_wenti  b order by id desc)  A  WHERE  ROWNUM  <=   "+(num+6)+" and columnid="+columnid+"  ) WHERE  RN  >   "+num+" ");
            while (res.next()) {

                Register reg = new Register();
                reg.setIuserid(res.getInt("user_id"));

                reg.setSUSERNAME(res.getString("user_name"));

                reg.setSPHONE(res.getString("user_phone"));
                reg.setUser_flag(res.getInt("user_flag"));
                reg.setISEX(res.getString("COMPANY_ADDRESS"));//企业地址
                reg.setSEMAIL(res.getString("user_email"));
                reg.setUser_province(res.getInt("User_province"));
                reg.setUser_city(res.getInt("user_city"));
                reg.setSMOBILEPHONE(res.getString("user_tel"));

                reg.setSBIRTHDAY(res.getString("USER_DUTY"));   //用户职位

                reg.setIFLAG(res.getInt("USER_STATE"));

                reg.setITYPE(res.getInt("USER_TYPE"));

                reg.setIGRADE(res.getInt("USER_GRADE"));    //用户积分

                reg.setIEMONEY(res.getInt("USER_EMONEY"));

                reg.setSAGE(res.getString("COMPANY_NAME"));    //企业名称

                reg.setSREALNAME(res.getString("real_name")); //所在部门
                reg.setIsvip(res.getInt("isvip"));
                reg.setCreatedate(res.getString("user_create_date"));
                // reg.setBeizhu(res.getString("beizhu"));
                // reg.setRmb(res.getString("rmb"));
                list.add(reg);

            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public int getCountRegister(int itype) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            pstmt = con.prepareStatement("select count(*) from userinfo where USER_TYPE=" + itype);

            res = pstmt.executeQuery();
            while (res.next()) {
                i = res.getInt(1);
                System.out.println("count=" + i);
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (Exception e) {
                }
            }
        }
        return i;
    }

    public int updateRegister(int id, int iemoney) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("update userinfo set USER_EMONEY=" + iemoney + "  where user_id=" + id);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return i;
    }

    public int deleteRegister(int id) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("delete from userinfo where user_id=" + id);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return i;
    }

    public int updateRegisterStatus(int id, int status) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("update userinfo set USER_STATE=" + status + "  where user_id=" + id);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        return i;
    }

    public Register getVip(int id) {
        Register reg = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            con = cpool.getConnection();
            String sql = "select *from userinfo where user_id=" + id;
            pstmt = con.prepareStatement(sql);
            res = pstmt.executeQuery();
            if (res.next()) {
                reg = new Register();
                reg.setIsvip(res.getInt("isvip"));
                reg.setVipmoney(res.getString("vipmoney"));
                reg.setVipcreatedate(res.getString("vipcreatedate"));
                reg.setViplastdate(res.getString("viplastdate"));
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return reg;
    }

    public int updateVip(int id, Register reg) {
        int i = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();
            con.setAutoCommit(false);
            String sql = "update userinfo set vipmoney='" + reg.getVipmoney() + "' , isvip=" + reg.getIsvip() + " ,vipcreatedate='" + reg.getVipcreatedate() + "' , viplastdate='" + reg.getViplastdate() + "' where user_id=" + id;
            pstmt = con.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return i;
    }

    //问题放在首页
    public int updateIstop(int id, int istop, int columnid) {
        int i = 0;
        int count = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = cpool.getConnection();

            String sql = "select count(*) from fawu_wenti where istop=1 and columnid=" + columnid;
            //   System.out.println("sql="+sql);
            pstmt = con.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                count = res.getInt(1);
            }
            res.close();
            pstmt.close();
            // System.out.println("sql="+sql);
            con.setAutoCommit(false);
            if (count < 3) {

                sql = "update fawu_wenti  set istop=" + istop + " where id=" + id;
                pstmt = con.prepareStatement(sql);
                i = pstmt.executeUpdate();
                pstmt.close();
                con.commit();
            }
            if(istop==0)
            {
                sql = "update fawu_wenti  set istop=" + istop + " where id=" + id;
                pstmt = con.prepareStatement(sql);
                i = pstmt.executeUpdate();
                pstmt.close();
                con.commit();
            }

        } catch (Exception e) {
            System.out.println(""+e.toString());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {

                }
            }
        }
        return i;
    }
    public int insertMedia_Tblpicture( Picture pic)
    {
        int i=0;
        String sql="";
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        sql="select max(id) as id from TBL_PICTURE";
        try{
            int id=0;
            con=cpool.getConnection();

            pstmt=con.prepareStatement(sql);
            System.out.println(sql);
            res=pstmt.executeQuery();

            if(res.next()){

                id=res.getInt("id");


                id++;
            }
            res.close();
            pstmt.close();

            con.setAutoCommit(false);
            sql="insert into TBL_PICTURE(id ,width,height,picsize,picname,imgurl,type,articleid) values("+id+",10,20,20,?,'no',1,?)";
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,pic.getPicname());
            pstmt.setInt(2,pic.getArticleid());

            i= pstmt.executeUpdate();
            pstmt.close();
            con.commit();

        }catch(Exception e){

        }
        finally{
            if(con!=null)
            {
                try{
                    con.close();
                }catch(Exception e){

                }
            }
        }

        return i;
    }
    public Picture getMediaName(int articleid)
    {
        Picture pic=null;
        int i=0;
        String sql="";
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        sql="select  from TBL_PICTURE where type=1";
        try{
            int id=0;
            con=cpool.getConnection();

            pstmt=con.prepareStatement(sql);
            System.out.println(sql);
            res=pstmt.executeQuery();

            if(res.next()){
                pic.setPicname(res.getString("picname"));
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    con.close();
                }catch(Exception e){

                }
            }
        }
        return pic;
    }
    //插入E充直表
    public int E_addinfo(int usrid,int money,int xinmoney,String beizhu,String rmb,String payway)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        String  sql="select max(id) as id from  e_addinfo";
        try{
            int id=0;
            con=cpool.getConnection();

            pstmt=con.prepareStatement(sql);
            System.out.println(sql);
            res=pstmt.executeQuery();

            if(res.next()){

                id=res.getInt("id");


                id++;
            }
            res.close();
            pstmt.close();

            con.setAutoCommit(false);
            sql="insert into e_addinfo(id,user_id,add_type,ADD_EMONEY,ADD_BEFORE_EMONEY,ADD_AFTER_EMONEY,beizhu,rmb,payway)values("+id+","+usrid+",1,"+money+","+xinmoney+","+(money+xinmoney)+" ,'"+beizhu+"', '"+rmb+"','"+payway+"')";
            pstmt=con.prepareStatement(sql);
            i= pstmt.executeUpdate();
            pstmt.close();
            con.commit();
            System.out.println("sql="+sql);
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    con.close();
                }catch(Exception e){

                }
            }
        }
        return i;
    }

    //删除 用户答案时修改周积分
    public int weekgrade(int userid,int grade){
        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sundaytime = "";
        try{
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            java.util.Date date = c.getTime();
            String sundaysql = "SELECT sundaytime FROM userinfo WHERE user_id=?";
            String weekgradesql = "UPDATE userinfo SET weekgrade=weekgrade+"+grade+" WHERE user_id=?";
            String nextsundaysql = "update userinfo set sundaytime = (select to_char(trunc(next_day(sysdate - 7, 1)+7 ),'yyyy-mm-dd') from dual),weekgrade=0 where user_id=?";

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
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return result;
    }
    //用户查询
    public List getSearchRegister(int ipage, int itype,int searchtype,String keyword) {
        List list = new ArrayList();
        Connection con = null;
        PreparedStatement pstmt = null;
        int num = 10 * (ipage - 1);
        try {
            // searchtype=1查询名字 searchtype=2查询电子邮件 searchtype=3 查询公司名称 4 查询 vip用户 5 查询 时间
            con = cpool.getConnection();
            String sql="";
            if(searchtype==1)
            {
                sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + " and user_name like '%"+keyword+"%'  ) WHERE  RN  >   " + num + " ";
            }
            if(searchtype==2)
            {
                sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + " and user_email like '%"+keyword+"%'  ) WHERE  RN  >   " + num + " ";
            }
            if(searchtype==3)
            {
                sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + " and company_name like '%"+keyword+"%'  ) WHERE  RN  >   " + num + " ";
            }
            if(searchtype==4)
            {
                sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + " and isvip=1  ) WHERE  RN  >   " + num + " ";
            }
            if(searchtype==5)
            {
                sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + " and user_create_date >='"+keyword+"'  ) WHERE  RN  >   " + num + " ";
            }
            if(searchtype==6)
            {
                sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by user_id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and USER_TYPE=" + itype + " and user_province in (select id from en_province where provname like '%"+keyword+"%')  ) WHERE  RN  >   " + num + " ";
            }
            pstmt = con.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            //   System.out.println("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from userinfo  b order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 10) + " and itype="+itype+"  ) WHERE  RN  >   " + num + " ");
            //   System.out.println("SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from fawu_wenti  b order by id desc)  A  WHERE  ROWNUM  <=   "+(num+6)+" and columnid="+columnid+"  ) WHERE  RN  >   "+num+" ");
            while (res.next()) {

                Register reg = new Register();
                reg.setIuserid(res.getInt("user_id"));

                reg.setSUSERNAME(res.getString("user_name"));

                reg.setSPHONE(res.getString("user_phone"));
                reg.setUser_flag(res.getInt("user_flag"));
                reg.setISEX(res.getString("COMPANY_ADDRESS"));//企业地址
                reg.setSEMAIL(res.getString("user_email"));
                reg.setUser_province(res.getInt("User_province"));
                reg.setUser_city(res.getInt("user_city"));
                reg.setSMOBILEPHONE(res.getString("user_tel"));

                reg.setSBIRTHDAY(res.getString("USER_DUTY"));   //用户职位

                reg.setIFLAG(res.getInt("USER_STATE"));

                reg.setITYPE(res.getInt("USER_TYPE"));

                reg.setIGRADE(res.getInt("USER_GRADE"));    //用户积分

                reg.setIEMONEY(res.getInt("USER_EMONEY"));

                reg.setSAGE(res.getString("COMPANY_NAME"));    //企业名称

                reg.setSREALNAME(res.getString("real_name")); //所在部门
                reg.setIsvip(res.getInt("isvip"));
                reg.setCreatedate(res.getString("user_create_date"));
                //  reg.setBeizhu(res.getString("beizhu"));
                //   reg.setRmb(res.getString("rmb"));
                list.add(reg);

            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }
    public int countSearchRegister(int itype,int searchtype,String keyword)
    {
        int count=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="";
            if(searchtype==1)
            {
                sql="select count(*) from userinfo where USER_TYPE=" + itype + " and user_name like '%"+keyword+"%'";
            }
            if(searchtype==2)
            {
                sql="select count(*) from userinfo where USER_TYPE=" + itype + " and user_email like '%"+keyword+"%'";
            }
            if(searchtype==3)
            {
                sql="select count(*) from userinfo where USER_TYPE=" + itype + " and company_name like '%"+keyword+"%'";
            }
            if(searchtype==4)
            {
                sql="select count(*) from userinfo where USER_TYPE=" + itype + " and isvip=1";
            }
            if(searchtype==5)
            {
                sql="select count(*) from userinfo where USER_TYPE=" + itype + " and   user_create_date >='"+keyword+"'";
            }
            if(searchtype==6)
            {
                sql="select count(*) from userinfo where USER_TYPE=" + itype + " and user_province in (select id from en_province where provname like '%"+keyword+"%') ";
            }
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                count=res.getInt(1);
            }
            res.close();
            pstmt.close();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    con.close();
                }catch(Exception e){
                    System.out.println(""+e.toString());
                }
            }
        }
        return count;
    }
    //  手动激活后台用户
    public int updateUser_flag(int id,int user_flag)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            con.setAutoCommit(false);
            String sql="update userinfo set user_flag="+user_flag +" where user_id="+id;
            pstmt=con.prepareStatement(sql);
            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    con.close();
                } catch(Exception e){

                }
            }
        }
        return i;
    }
    public List getE_chongzhijilu(int userid)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select * from e_addinfo where user_id= "+userid+"  order by add_date desc";
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                EMoney moey=new EMoney();
                moey.setId(res.getInt("id"));
                moey.setRMB(res.getString("rmb"));
                moey.setBeizhu(res.getString("beizhu"));
                moey.setADD_EMONEY(res.getInt("add_emoney"));
                moey.setADD_BEFORE_EMONEY(res.getInt("add_before_emoney"));
                moey.setADD_AFTER_EMONEY(res.getInt("add_after_emoney"));
                moey.setAdd_date(res.getString("add_date"));
                moey.setPayway(res.getString("payway"));
                list.add(moey);
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        } finally{
            if(con!=null)
            {   try{
                con.close();
            }catch(Exception e){

            }
            }
        }
        return list;
    }
}