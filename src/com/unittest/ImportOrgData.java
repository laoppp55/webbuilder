package com.unittest;

import com.bizwink.po.Department;
import com.bizwink.po.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportOrgData {
    private static Connection createConnection(String ip, String username, String password, String server, int port, int flag) {
        Connection conn = null;
        String dbip = "";
        String dbusername = "";
        String dbpassword = "";

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;

            System.out.println("dbip=" + dbip);
            System.out.println("server=" + server);
            System.out.println("dbusername=" + dbusername);
            System.out.println("dbpassword=" + dbpassword);

            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":" + port, dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port + ":"+server, dbusername, dbpassword);
            } else {
                Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":" + port + "/" + server + "?characterEncoding=utf-8", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    /**
     *  * @param args
     *  */
    public static void main(String[] args) throws Exception {
        //ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //DataSource dataSource = (DataSource) ctx.getBean("myDataSource");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Users> usersList = new ArrayList<>();

        try{
            String t_dbip = "192.166.96.6";
            String t_username = "bjsjsdbadmin";
            String t_password = "qazwsxokm";
            String t_server = "oracle10g";
            conn = createConnection(t_dbip, t_username, t_password,t_server,1521,1);
            //获取每个用户的ORGID、DEPTID和所属公司ID
            pstmt = conn.prepareStatement("select tm.userid,tm.departcode,td.ename,td.cname,td.orgid,td.id from tbl_members tm,tbl_dept td where td.departcode=tm.departcode");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Users user = new Users();
                user.setUSERID(rs.getString("userid"));
                user.setORGID(BigDecimal.valueOf(rs.getInt("orgid")));
                user.setDEPTID(BigDecimal.valueOf(rs.getInt("id")));
                user.setCOMPANYID(BigDecimal.valueOf(8));
                usersList.add(user);
            }
            rs.close();
            pstmt.close();

            //设置用户的ORGID、DEPTID和所属公司ID
            conn.setAutoCommit(false);
            for(int ii=0;ii<usersList.size();ii++) {
                Users user = usersList.get(ii);
                System.out.println(user.getUSERID() + "=" + user.getORGID().intValue() + "=" + user.getDEPTID().intValue() + "=" + user.getCOMPANYID());
                pstmt = conn.prepareStatement("update tbl_members t set t.orgid=?,t.deptid=?,t.companyid=8 where t.userid=?");
                pstmt.setInt(1,user.getORGID().intValue());
                pstmt.setInt(2,user.getDEPTID().intValue());
                pstmt.setString(3,user.getUSERID());
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp) {

            }
        }

        /*try{
            String tjsql = "select t.columnid,t.maintitle,t.source,t.createdate,t.editor from tbl_article t where t.columnid in (select id from tbl_column m start with m.id=? connect by m.parentid=prior m.id) and t.source=? and to_char(t.createdate,'yyyyMMdd')>='20190101' and to_char(t.createdate,'yyyyMMdd')<='20191231' and t.siteid=40 and t.status=1 and t.pubflag=0 and t.editor<>'石景山信用双公示' and t.editor<>'spider' order by t.createdate desc";

            //获取CMS的栏目目录、栏目ID等信息
            String t_dbip = "localhost";
            String t_username = "bjsjsdbadmin";
            String t_password = "qazwsxokm";
            String t_server = "orcl11g";
            conn = createConnection(t_dbip, t_username, t_password,t_server,1521,1);
            pstmt = conn.prepareStatement("select distinct unit from tbl_department");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                departments.add(rs.getString("unit"));
            }
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            List<Department> kjdepts = null;
            for (int ii=0;ii<departments.size();ii++) {
                //获取科级单位列表，在TBL_DEPT表中生成科级单位，在组织架构表中加入科级单位
                kjdepts = new ArrayList<>();
                pstmt = conn.prepareStatement("select * from tbl_department where unit=? and siteid=40");
                pstmt.setString(1,departments.get(ii));
                rs = pstmt.executeQuery();
                while(rs.next()) {
                    Department department = new Department();
                    department.setID(BigDecimal.valueOf(rs.getInt("id")));
                    department.setSITEID(BigDecimal.valueOf(rs.getInt("siteid")));
                    department.setCOMPANYID(BigDecimal.valueOf(rs.getInt("companyid")));
                    department.setORGID(BigDecimal.valueOf(rs.getInt("orgid")));
                    department.setENAME(rs.getString("ename"));
                    department.setCNAME(rs.getString("cname"));
                    department.setDepartcode(rs.getString("ename"));
                    department.setLEADER(rs.getString("leader"));
                    department.setMANAGER(rs.getString("manager"));
                    department.setVICEMANAGER(rs.getString("vicemanager"));
                    department.setTELEPHONE(rs.getString("telephone"));
                    department.setEMAIL(rs.getString("email"));
                    department.setSHORTNAME(rs.getString("shortname"));
                    department.setIndexrules(rs.getString("indexrules"));
                    department.setIndexnum(rs.getInt("indexnum"));
                    department.setCREATEDATE(rs.getTimestamp("createdate"));
                    department.setLASTUPDATE(rs.getTimestamp("lastupdate"));
                    department.setCREATEUSER(rs.getString("createuser"));
                    kjdepts.add(department);
                }
                rs.close();
                pstmt.close();

                //获取组织架构表中的父栏目的ID
                int parentid = 0;
                String name = null;
                pstmt = conn.prepareStatement("select id,name from tbl_organization t where t.name=? and t.CUSTOMERID=40");
                pstmt.setString(1,departments.get(ii));
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    parentid = rs.getInt("id");
                    name = rs.getString("name");
                }
                rs.close();
                pstmt.close();

                if (parentid==0) parentid=812;

                //将科级单位名称插入到组织机构表中
                for(int jj=0;jj<kjdepts.size();jj++) {
                    Department department = kjdepts.get(jj);
                    //获取组织架构表的插入行记录的主键
                    int orgid = 0;
                    pstmt = conn.prepareStatement("select TBL_USERINFOS_ID.NEXTVAL from dual");
                    rs = pstmt.executeQuery();
                    if (rs.next()) orgid = rs.getInt(1);
                    rs.close();
                    pstmt.close();
                    pstmt = conn.prepareStatement(" insert into TBL_ORGANIZATION (PARENT, COTYPE,STATUS, NAME, ENAME, ORDERID,CUSTOMERID," +
                            "LLEVEL, ORGCODE, ISLEAF,LASTUPDATE, CREATEDATE, CREATEUSER, UPDATEUSER,ID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pstmt.setInt(1,parentid);
                    pstmt.setInt(2,0);
                    pstmt.setInt(3,1);
                    pstmt.setString(4,department.getCNAME());
                    pstmt.setString(5,department.getCNAME());
                    pstmt.setInt(6,jj+1);
                    pstmt.setInt(7,40);
                    pstmt.setInt(8,2);
                    pstmt.setString(9,department.getDepartcode());
                    pstmt.setInt(10,0);
                    pstmt.setTimestamp(11,new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(12,new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(13,"sjsadmin");
                    pstmt.setString(14,"sjsadmin");
                    pstmt.setInt(15,orgid);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取部门表的主键ID
                    int deptid =0;
                    pstmt = conn.prepareStatement("select DEPARTMENT_ID.NEXTVAL from dual");
                    rs = pstmt.executeQuery();
                    if (rs.next()) deptid = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    //插入部门表的行数据
                    pstmt = conn.prepareStatement("insert into TBL_DEPT (SITEID, ORGID, COMPANYID, CNAME,SHORTNAME,ENAME, EMAIL, " +
                            "TELEPHONE, MANAGER,VICEMANAGER, LEADER, LASTUPDATE, CREATEDATE,CREATEUSER,indexrules,indexnum,departcode,id) " +
                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pstmt.setInt(1,40);
                    pstmt.setInt(2,orgid);
                    pstmt.setInt(3,8);
                    pstmt.setString(4,department.getCNAME());
                    pstmt.setString(5,department.getCNAME());
                    pstmt.setString(6,department.getDepartcode());
                    pstmt.setString(7,department.getEMAIL());
                    pstmt.setString(8,department.getTELEPHONE());
                    pstmt.setString(9,department.getMANAGER());
                    pstmt.setString(10,department.getVICEMANAGER());
                    pstmt.setString(11,department.getLEADER());
                    pstmt.setTimestamp(12,new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(13,new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(14,"sjsadmin");
                    pstmt.setString(15,department.getIndexrules());
                    pstmt.setInt(16,department.getIndexnum());
                    pstmt.setString(17,department.getDepartcode());
                    pstmt.setInt(18,deptid);
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp) {

            }
        }*/
    }
}
