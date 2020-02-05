package com.bizwink.cms.sjswsbs;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.util.SpringInit;
import org.springframework.context.ApplicationContext;
import sun.security.util.Resources;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2013-9-24
 * Time: 16:52:48
 * To change this template use File | Settings | File Templates.
 */
public class WsbsPeer implements IWsbsManager{

    PoolServer cpool;

    public WsbsPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IWsbsManager getInstance() {
        return CmsServer.getInstance().getFactory().getWsbsManager();
    }

    private static Connection createConnection(String ip,String port,String dbinstance,String username,String password, int flag) {
        Connection conn = null;
        String dbip = "";
        String dbusername = "";
        String dbpassword = "";

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":1433", dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port + ":" + dbinstance, dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    public void updateIndexflag() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            if (conn != null) {
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("select t.maintitle,t.id,t.createdate from TBL_ARTICLE t where to_char(t.createdate,'yyyy-MM-dd')>'2019-08-01' and t.indexflag=2 and t.siteid=40 and t.pubflag=0");
                rs = pstmt.executeQuery();
                while(rs.next()) {
                    System.out.println(rs.getInt("id") + "==" + rs.getString("maintitle") + "==" + rs.getTimestamp("createdate").toString());
                }
                rs.close();;
                pstmt.close();

                pstmt = conn.prepareStatement("update TBL_ARTICLE t set t.indexflag=0 where to_char(t.createdate,'yyyy-MM-dd')>'2019-08-01' and t.indexflag=2 and t.siteid=40 and t.pubflag=0");
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp){}
        }
    }

    private static String GET_CURRENT_WSBS_LIST = "SELECT * FROM tbl_sjs_wsbs order by id desc";

    public List getCurrentWsbsList(int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();

        try {
            ApplicationContext appContext = SpringInit.getApplicationContext();
            DataSource dataSource =(DataSource)appContext.getBean("myDataSource");
            //conn = cpool.getConnection();
            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_CURRENT_WSBS_LIST);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                WsbsEntity ss = loadsurvey(rs);
                list.add(ss);
            }//System.out.println("aaaaaaaaaa"+list);
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp){}
        }
        return list;
    }

    private static String GET_ALL_WSBS_NUM = "select count(id) from tbl_sjs_wsbs";

    public int getAllWsbsNum() {
        Connection conn = null;
        int count = 0;

        try {
            ApplicationContext appContext = SpringInit.getApplicationContext();
            DataSource dataSource =(DataSource)appContext.getBean("myDataSource");
            //conn = cpool.getConnection();
            conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_WSBS_NUM);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp){}
        }
        return count;
    }

    public int getAllWsbsNum(String sqlstr) {
        Connection conn = null;
        int count = 0;
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
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

    public List getCurrentQureyWsbsList(String sqlstr, int startrow, int range) {
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            //pstmt.setString(1, sqlstr);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                WsbsEntity wsbs = loadsurvey(rs);
                list.add(wsbs);
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

    private static String GET_BYID_WSBS = "select * from tbl_sjs_wsbs where id=?";

    public WsbsEntity getByIdwsbs(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        WsbsEntity wsbs = new WsbsEntity();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_WSBS);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                wsbs = loadsurvey(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return wsbs;
    }

    private static String GET_BYID_WSBS_GIST = "select * from tbl_sjs_wsbs_gist where id=?";
    //ͨ��ID��ѯ

    public BasisEntity getByIdwsbsgist(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        BasisEntity basis = new BasisEntity();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_WSBS_GIST);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                basis.setId(rs.getInt("id"));
                basis.setName(rs.getString("name"));
                basis.setWsbsid(rs.getInt("wsbsid"));
                basis.setContent(rs.getString("content"));
                basis.setStandby(rs.getString("standby"));
                basis.setCategory(rs.getInt("category"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return basis;
    }

    public List getByIdGist(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_sjs_wsbs_gist where wsbsid=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                BasisEntity basis = new BasisEntity();
                basis.setId(rs.getInt("id"));
                basis.setWsbsid(rs.getInt("wsbsid"));
                basis.setName(rs.getString("name"));
                basis.setContent(rs.getString("content"));
                basis.setStandby(rs.getString("standby"));
                basis.setCategory(rs.getInt("category"));
                list.add(basis);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private WsbsEntity loadsurvey(ResultSet rs) {
        WsbsEntity wsbs = new WsbsEntity();

        try {
            wsbs.setID(rs.getInt("id"));
            wsbs.setCatgid(rs.getInt("catgid"));
            wsbs.setDepid(rs.getInt("depid"));
            wsbs.setServiceobject(rs.getString("serviceobject"));
            wsbs.setName(rs.getString("name"));
            wsbs.setBasis(rs.getString("basis"));
            wsbs.setChargestandard(rs.getString("chargestandard"));
            wsbs.setTimelimited(rs.getString("timelimited"));
            wsbs.setTimelimit(rs.getString("timelimit"));
            wsbs.setOrgnization(rs.getString("orgnization"));
            wsbs.setWorkaddress(rs.getString("workaddress"));
            wsbs.setRidingroute(rs.getString("ridingroute"));
            wsbs.setRelatephone(rs.getString("relatephone"));
            wsbs.setMemo(rs.getString("memo"));
            wsbs.setWorkprocedure(rs.getString("workprocedure"));
            wsbs.setLink_zxbl(rs.getString("link_zxbl"));
            wsbs.setLink_jgfk(rs.getString("link_jgfk"));
            wsbs.setLink_zxzx(rs.getString("link_zxzx"));
            wsbs.setStandby(rs.getString("standby"));
            wsbs.setCoun(rs.getString("coun"));
            wsbs.setStuff(rs.getString("stuff"));
            wsbs.setItem_condition(rs.getString("item_condition"));
            wsbs.setItem_times(rs.getString("item_times"));
            wsbs.setCode(rs.getString("code"));
            wsbs.setClassified(rs.getString("classified"));
            wsbs.setMain(rs.getString("main"));
            wsbs.setJurisdiction(rs.getString("jurisdiction"));
            wsbs.setAnswer(rs.getString("answer"));
            wsbs.setSupervision_telephone(rs.getString("supervision_telephone"));
            wsbs.setJob_description(rs.getString("job_description"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wsbs;
    }

    private static String GET_BYID_CATG = "select * from tbl_sjs_catg where id=?";
    //ͨ��ID��ѯ     tbl_sjs_catg

    public CatgEntity getByIdcatg(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        CatgEntity catg = new CatgEntity();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_CATG);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                catg.setId(rs.getInt("id"));
                catg.setName(rs.getString("name"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return catg;
    }

    private static String GET_ALL_Catg_LIST = "SELECT * FROM tbl_sjs_catg where category=? or category=? order by sortnum asc";
    //��ѯ����

    public List getAllCatgEntity(int a,int b) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_Catg_LIST);
            pstmt.setInt(1, a);
            pstmt.setInt(2, b);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CatgEntity catg = new CatgEntity();
                catg.setId(rs.getInt("id"));
                catg.setName(rs.getString("name"));
                list.add(catg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String GET_INSERT_WSBS = "insert into tbl_sjs_wsbs(id,catgid,depid,serviceobject,name,basis,chargestandard,timelimited,timelimit,orgnization,workaddress,ridingroute,relatephone,memo,workprocedure,link_zxbl,link_jgfk,link_zxzx,standby,coun,stuff,item_condition,item_times,code,classified,main,jurisdiction,answer,supervision_telephone,job_description) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    //������ϰ�����Ϣ����

    public int insertWsbs(WsbsEntity wsbs,List list) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        int id = seqMgr.getSequenceNum("SJSWSBSSEQ");
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_INSERT_WSBS);
            pstmt.setInt(1, id);
            pstmt.setInt(2,wsbs.getCatgid());
            pstmt.setInt(3,wsbs.getDepid());
            pstmt.setString(4,wsbs.getServiceobject());
            pstmt.setString(5,wsbs.getName());
            pstmt.setString(6,wsbs.getBasis());
            pstmt.setString(7,wsbs.getChargestandard());
            pstmt.setString(8,wsbs.getTimelimited());
            pstmt.setString(9,wsbs.getTimelimit());
            pstmt.setString(10,wsbs.getOrgnization());
            pstmt.setString(11,wsbs.getWorkaddress());
            pstmt.setString(12,wsbs.getRidingroute());
            pstmt.setString(13,wsbs.getRelatephone());
            pstmt.setString(14,wsbs.getMemo());
            pstmt.setString(15,wsbs.getWorkprocedure());
            pstmt.setString(16,wsbs.getLink_zxbl());
            pstmt.setString(17,wsbs.getLink_jgfk());
            pstmt.setString(18,wsbs.getLink_zxzx());
            pstmt.setString(19,new Timestamp(System.currentTimeMillis()).toString().substring(0,19));//¼������
            pstmt.setString(20,wsbs.getCoun());
            pstmt.setString(21,wsbs.getStuff());
            pstmt.setString(22,wsbs.getItem_condition());
            pstmt.setString(23,wsbs.getItem_times());
            pstmt.setString(24,wsbs.getCode());
            pstmt.setString(25,wsbs.getClassified());
            pstmt.setString(26,wsbs.getMain());
            pstmt.setString(27,wsbs.getJurisdiction());
            pstmt.setString(28,wsbs.getAnswer());
            pstmt.setString(29,wsbs.getSupervision_telephone());
            pstmt.setString(30,wsbs.getJob_description());
            pstmt.executeUpdate();
            pstmt.close();
            if(list != null){
                for(int i = 0; i < list.size(); i++){
                    BasisEntity basisentity = (BasisEntity)list.get(i);
                    if(basisentity.getName()== null || basisentity.getName().equals("")){}
                    else{
                        pstmt = conn.prepareStatement("insert into tbl_sjs_wsbs_gist(id,wsbsid,name,content,standby,category) values(?,?,?,?,?,?)");
                        pstmt.setInt(1,seqMgr.getSequenceNum("SJSWSBSSEQ"));
                        pstmt.setInt(2,id);
                        pstmt.setString(3,basisentity.getName());
                        pstmt.setString(4,basisentity.getContent());
                        pstmt.setString(5,new Timestamp(System.currentTimeMillis()).toString().substring(0,19));
                        pstmt.setInt(6,basisentity.getCategory());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            code = 1;
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
        return code;
    }

    //������ϰ�����Ϣ����

    public int insertWsbsGist(int id,List list) {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if(list != null){
                for(int i = 0; i < list.size(); i++){
                    BasisEntity basisentity = (BasisEntity)list.get(i);
                    if(basisentity.getName()== null || basisentity.getName().equals("")){}
                    else{
                        pstmt = conn.prepareStatement("insert into tbl_sjs_wsbs_gist(id,wsbsid,name,content,standby,category) values(?,?,?,?,?,?)");
                        pstmt.setInt(1,seqMgr.getSequenceNum("SJSWSBSSEQ"));
                        pstmt.setInt(2,id);
                        pstmt.setString(3,basisentity.getName());
                        pstmt.setString(4,basisentity.getContent());
                        pstmt.setString(5,new Timestamp(System.currentTimeMillis()).toString().substring(0,19));
                        pstmt.setInt(6,basisentity.getCategory());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            code = 1;
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
        return code;
    }

    private static String GET_UPDATE_WSBS = "update tbl_sjs_wsbs set catgid=?,depid=?,serviceobject=?,name=?,basis=?,chargestandard=?,timelimited=?,timelimit=?,orgnization=?,workaddress=?,ridingroute=?,relatephone=?,memo=?,workprocedure=?,link_zxbl=?,link_jgfk=?,link_zxzx=?,coun=?,stuff=?,item_condition=?,item_times=?,code=?,classified=?,main=?,jurisdiction=?,answer=?,supervision_telephone=?,job_description=?,standby=? where id=?";
    //�޸����ϰ�����Ϣ

    public void updateWsbs(WsbsEntity wsbs,List list) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_UPDATE_WSBS);
            //pstmt.setInt(1, id);
            pstmt.setInt(1,wsbs.getCatgid());
            pstmt.setInt(2,wsbs.getDepid());
            pstmt.setString(3,wsbs.getServiceobject());
            pstmt.setString(4,wsbs.getName());
            pstmt.setString(5,wsbs.getBasis());
            pstmt.setString(6,wsbs.getChargestandard());
            pstmt.setString(7,wsbs.getTimelimited());
            pstmt.setString(8,wsbs.getTimelimit());
            pstmt.setString(9,wsbs.getOrgnization());
            pstmt.setString(10,wsbs.getWorkaddress());
            pstmt.setString(11,wsbs.getRidingroute());
            pstmt.setString(12,wsbs.getRelatephone());
            pstmt.setString(13,wsbs.getMemo());
            pstmt.setString(14,wsbs.getWorkprocedure());
            pstmt.setString(15,wsbs.getLink_zxbl());
            pstmt.setString(16,wsbs.getLink_jgfk());
            pstmt.setString(17,wsbs.getLink_zxzx());
            //pstmt.setString(18,new Timestamp(System.currentTimeMillis()).toString().substring(0,19));//¼������
            pstmt.setString(18,wsbs.getCoun());
            pstmt.setString(19,wsbs.getStuff());
            pstmt.setString(20,wsbs.getItem_condition());
            pstmt.setString(21,wsbs.getItem_times());
            pstmt.setString(22,wsbs.getCode());
            pstmt.setString(23,wsbs.getClassified());
            pstmt.setString(24,wsbs.getMain());
            pstmt.setString(25,wsbs.getJurisdiction());
            pstmt.setString(26,wsbs.getAnswer());
            pstmt.setString(27,wsbs.getSupervision_telephone());
            pstmt.setString(28,wsbs.getJob_description());
            pstmt.setString(29,wsbs.getStandby());
            pstmt.setInt(30, wsbs.getID());
            pstmt.executeUpdate();
            pstmt.close();
            /*if(list != null){
                for(int i = 0; i < list.size(); i++){
                    BasisEntity basisentity = (BasisEntity)list.get(i);
                    if(basisentity.getName()== null || basisentity.getName().equals("")){
                        pstmt = conn.prepareStatement("delete from tbl_sjs_wsbs_gist where id = ?");
                        pstmt.setInt(1,basisentity.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }else{                                          
                        pstmt = conn.prepareStatement("update tbl_sjs_wsbs_gist set name=?,content=?,category=? where id=?");
                        pstmt.setString(1,basisentity.getName());
                        pstmt.setString(2,basisentity.getContent());
                        pstmt.setInt(3,basisentity.getCategory());
                        pstmt.setInt(4,basisentity.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
            }*/
            conn.commit();
        } catch (SQLException e) {
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

    public void updateWsbsGist(List list) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if(list != null){
                for(int i = 0; i < list.size(); i++){
                    BasisEntity basisentity = (BasisEntity)list.get(i);
                    if(basisentity.getName()== null || basisentity.getName().equals("")){
                        pstmt = conn.prepareStatement("delete from tbl_sjs_wsbs_gist where id = ?");
                        pstmt.setInt(1,basisentity.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }else{
                        pstmt = conn.prepareStatement("update tbl_sjs_wsbs_gist set name=?,content=?,category=? where id=?");
                        pstmt.setString(1,basisentity.getName());
                        pstmt.setString(2,basisentity.getContent());
                        pstmt.setInt(3,basisentity.getCategory());
                        pstmt.setInt(4,basisentity.getId());
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
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

    public void deleteWsbs(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_sjs_wsbs where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = conn.prepareStatement("delete from tbl_sjs_wsbs_gist where wsbsid = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public void deleteWsbsGist(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_sjs_wsbs_gist where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public String  getConfig(){
        String picUrl = "";
        Properties prop = new Properties();
        InputStream in =null;
        try {
            ClassLoader loader = Resources.class.getClassLoader();
            in = getClass().getResourceAsStream("/picurl.conf");
            prop.load(in);
            Set keyValue = prop.keySet();
            for (Iterator it = keyValue.iterator(); it.hasNext();) {
                String key = (String) it.next();
                if (key.equals("picUrl")) {
                    picUrl = (String) prop.get(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IO error,can not find picurl.conf!");
        }
        return picUrl;
    }

    //打黑举报

    private static String GET_CURRENT_DHJB_LIST = "SELECT * FROM tbl_report_gangdom_message order by id desc";

    public List getCurrentdhjbList(int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_CURRENT_DHJB_LIST);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                ReportGangdom reportGangdom = loadreportGangdom(rs);
                list.add(reportGangdom);
            }//System.out.println("aaaaaaaaaa"+list);
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


    private static String GET_ALL_DHJB_NUM = "select count(id) from tbl_report_gangdom_message";

    public int getAlldhjbNum() {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DHJB_NUM);
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

    public int getAlldhjbNum(String sqlstr) {
        Connection conn = null;
        int count = 0;
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
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

    public List getCurrentQureydhjbList(String sqlstr, int startrow, int range) {
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            //pstmt.setString(1, sqlstr);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                ReportGangdom reportGangdom = loadreportGangdom(rs);
                list.add(reportGangdom);
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

    private static String GET_BYID_DHJB = "select * from tbl_report_gangdom_message where id=?";

    public ReportGangdom getByIddhjb(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        ReportGangdom reportGangdom = new ReportGangdom();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_DHJB);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reportGangdom = loadreportGangdom(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return reportGangdom;
    }




    public void deletedhjb(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_report_gangdom_message where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }


    private ReportGangdom loadreportGangdom(ResultSet rs) {
        ReportGangdom reportGangdom = new ReportGangdom();

        try {
            reportGangdom.setId(rs.getInt("id"));
            reportGangdom.setJbrname(rs.getString("jbrname"));
            reportGangdom.setSex(rs.getShort("sex"));
            reportGangdom.setIdcardno(rs.getString("idcardno"));
            reportGangdom.setTelphone(rs.getString("telphone"));
            reportGangdom.setAddress(rs.getString("address"));
            reportGangdom.setPostcode(rs.getString("postcode"));
            reportGangdom.setReportedname(rs.getString("reportedname"));
            reportGangdom.setEpithet(rs.getString("epithet"));
            reportGangdom.setRpaddress(rs.getString("rpaddress"));
            reportGangdom.setRpidcardno(rs.getString("rpidcardno"));
            reportGangdom.setProvince(rs.getString("province"));
            reportGangdom.setCity(rs.getString("city"));
            reportGangdom.setCounty(rs.getString("county"));
            reportGangdom.setReportedcontent(rs.getString("reportedcontent"));
            reportGangdom.setGzname(rs.getString("gzname"));
            reportGangdom.setUnittitle(rs.getString("unittitle"));
            reportGangdom.setUnlevel(rs.getString("unlevel"));
            reportGangdom.setGzreportedcontent(rs.getString("gzreportedcontent"));
            reportGangdom.setCreatedate(rs.getTimestamp("createdate"));
            reportGangdom.setIpadress(rs.getString("ipadress"));
            reportGangdom.setYanzhengmsg(rs.getString("yanzhengmsg"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportGangdom;
    }

    //纪委打黑举报

    private static String GET_CURRENT_JWDH_LIST = "SELECT * FROM tbl_report_gangdom_messages order by id desc";

    public List getCurrentjwdhList(int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_CURRENT_JWDH_LIST);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                ReportGangdom reportGangdom = loadreportGangdoms(rs);
                list.add(reportGangdom);
            };
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


    private static String GET_ALL_JWDH_NUM = "select count(id) from tbl_report_gangdom_messages";

    public int getAlljwdhNum() {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_JWDH_NUM);
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

    public int getAlljwdhNum(String sqlstr) {
        Connection conn = null;
        int count = 0;
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
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

    public List getCurrentQureyjwdhList(String sqlstr, int startrow, int range) {
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            //pstmt.setString(1, sqlstr);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                ReportGangdom reportGangdom = loadreportGangdoms(rs);
                list.add(reportGangdom);
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

    private static String GET_BYID_JWJB = "select * from tbl_report_gangdom_messages where id=?";

    public ReportGangdom getByIdjwdh(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        ReportGangdom reportGangdom = new ReportGangdom();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_JWJB);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reportGangdom = loadreportGangdoms(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return reportGangdom;
    }




    public void deletejwdh(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_report_gangdom_messages where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }


    public void updatejwdh(int id,int auditflag){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_report_gangdom_messages set auditflag=?  where id = ?");
            pstmt.setInt(1, auditflag);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }




    private ReportGangdom loadreportGangdoms(ResultSet rs) {
        ReportGangdom reportGangdom = new ReportGangdom();

        try {
            reportGangdom.setId(rs.getInt("id"));
            reportGangdom.setJbrname(rs.getString("jbrname"));
            reportGangdom.setIdcardno(rs.getString("idcardno"));
            reportGangdom.setAddress(rs.getString("address"));
            reportGangdom.setReportedname(rs.getString("reportedname"));
            reportGangdom.setCounty(rs.getString("county"));
            reportGangdom.setReportedcontent(rs.getString("reportedcontent"));
            reportGangdom.setCreatedate(rs.getTimestamp("createdate"));
            reportGangdom.setIpadress(rs.getString("ipadress"));
            reportGangdom.setYanzhengmsg(rs.getString("yanzhengmsg"));
            reportGangdom.setJbrlink(rs.getString("jbrlink"));
            reportGangdom.setJbrpolitical(rs.getString("jbrpolitical"));
            reportGangdom.setJbrlevel(rs.getString("jbrlevel"));
            reportGangdom.setDepartment(rs.getString("department"));
            reportGangdom.setRpJob(rs.getString("rpJob"));
            reportGangdom.setRplevel(rs.getString("rplevel"));
            reportGangdom.setRepmaintitle(rs.getString("repmaintitle"));
            reportGangdom.setRepclass(rs.getString("repclass"));
            reportGangdom.setRepclasses(rs.getString("repclasses"));
            reportGangdom.setAuditflag(rs.getShort("auditflag"));
            reportGangdom.setFilename(rs.getString("filename"));
            reportGangdom.setSearchmsg(rs.getString("searchmsg"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportGangdom;
    }


    //政民互动 我要写信


    private static String GET_LETTER_NUM = "select count(id) from tbl_letter where statusflag=?";

    public int getLetterCount(int status) {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_LETTER_NUM);
            pstmt.setInt(1,status);
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

    public int getLetterCount(String sqlstr) {
        Connection conn = null;
        int count = 0;
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
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


    private static String GET_CURRENT_LETTER_LIST = "SELECT * FROM tbl_letter where statusflag=? order by createdate desc";

    public List getCurrentLetterList(int startrow, int range,int status){
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_CURRENT_LETTER_LIST);
            pstmt.setInt(1,status);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Letter letter = loadLetter(rs);
                list.add(letter);
            };
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

    public List getCurrentQureyLetterList(String sqlstr, int startrow, int range) {
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            //pstmt.setString(1, sqlstr);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Letter letter = loadLetter(rs);
                list.add(letter);
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

    private static String GET_LETTER_BY_ID = "select * from tbl_letter where id=?";

    public Letter getLetterById(int id) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Letter letter = new Letter();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_LETTER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                letter = loadLetter(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return letter;
    }




    public void deleteLetter(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("delete from tbl_letter where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }


    public void updateLetter(int id,Letter letter){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_letter set replycontent=?,replytime=?,statusflag=?,selectedflag=?,newflag=?,department=?  where id = ?");
            pstmt.setString(1, letter.getReplycontent());
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setBigDecimal(3, letter.getStatusflag());
            pstmt.setBigDecimal(4,letter.getSelectedflag());
            pstmt.setBigDecimal(5,letter.getNewflag());
            pstmt.setString(6,letter.getDepartment());
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }




    private Letter loadLetter(ResultSet rs) {
        Letter letter = new Letter();

        try {
            letter.setId((rs.getBigDecimal("id")));
            letter.setTitle(rs.getString("title"));
            letter.setContent(rs.getString("content"));
            letter.setFileupload(rs.getString("fileupload"));
            letter.setDeptid(rs.getBigDecimal("deptid"));
            letter.setCategorycode(rs.getBigDecimal("categorycode"));
            letter.setPublishflag(rs.getBigDecimal("publishflag"));
            letter.setCreatedate(rs.getTimestamp("createdate"));
            letter.setReplycontent(rs.getString("replycontent"));
            letter.setReplytime(rs.getTimestamp("replytime"));
            letter.setStatusflag(rs.getBigDecimal("statusflag"));
            letter.setSelectedflag(rs.getBigDecimal("selectedflag"));
            letter.setNewflag(rs.getBigDecimal("newflag"));
            letter.setSearchmsg(rs.getString("searchmsg"));
            letter.setLinkman(rs.getString("linkman"));
            letter.setPhone(rs.getString("phone"));
            letter.setDepartment(rs.getString("department"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return letter;
    }


    public String getDepartment(int depid){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String cname="";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select cname from tbl_department where id=?");
            pstmt.setInt(1, depid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cname = rs.getString("cname");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return cname;
    }

    public String getCategory(int Categoryid){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String category="";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select typename from tbl_letter_type where id=?");
            pstmt.setInt(1, Categoryid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                category = rs.getString("typename");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return category;
    }

}
