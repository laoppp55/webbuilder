package com.bizwink.util;

import com.bizwink.cms.extendAttr.ExtendAttrException;
import com.bizwink.cms.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-13
 * Time: 下午7:31
 * To change this template use File | Settings | File Templates.
 */
public class ImpBBNdata {
    public static void main(String[] args) throws Exception {
        java.io.File excelfile = new java.io.File("D:\\leads\\首信\\BBN网商频道项目技术建议书模板\\商城商品说明\\用户名和密码.xls");
        try{
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelfile));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            System.out.println("workbook.getNumberOfSheets()=" + workbook.getNumberOfSheets());
            //for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
            int  numSheets = 0;
            HSSFSheet aSheet = workbook.getSheetAt(numSheets + 1);//获得一个sheet
            if (aSheet != null) {
                String name="";
                String tbuf = "";
                for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    bbndata data = new bbndata();
                    if (null != aSheet.getRow(rowNumOfSheet) && rowNumOfSheet>0) {
                        HSSFRow aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                        for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                            String buf = "";
                            if (null != aRow.getCell(cellNumOfRow)) {
                                HSSFCell aCell = aRow.getCell(cellNumOfRow);//获得列值
                                if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    buf = aCell.getStringCellValue();
                                    buf = buf.trim();
                                } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                                    buf = String.valueOf((long)aCell.getNumericCellValue());
                            }

                            if (cellNumOfRow == 12) {
                                data.setUsername(buf);
                            }

                            if (cellNumOfRow == 13) {
                                data.setPassword(buf);
                            }

                            if (cellNumOfRow == 14) {
                                data.setDatatype(23);
                            }
                        }

                        System.out.println(rowNumOfSheet + "||" + data.getUsername() + "||" + data.getPassword() + "||" + data.getDatatype());
                        create(data);
                    }
                }
            }
        }catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    /**
     * 创建文章
     *
     * @param:extendList  扩展属性List
     * @param:attechments 文章附件List
     * @param:article     文章对象Article
     * @throws com.bizwink.cms.extendAttr.ExtendAttrException
     */
    private static final String SQL_CREATE_DATA = "INSERT INTO tbl_bbn_product (username,passwd,price,cardtype,createdate,updatedate,id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    static public void create(bbndata data) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int id = 0;

        try {
            try {
                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                conn.setAutoCommit(false);

                //获取最大的ID并增加1
                pstmt = conn.prepareStatement("select max(id) from tbl_bbn_product");
                rs = pstmt.executeQuery();
                if (rs.next()) id =rs.getInt(1) + 1;
                rs.close();
                pstmt.close();

                //增加数据
                pstmt = conn.prepareStatement(SQL_CREATE_DATA);
                pstmt.setString(1, data.getUsername());
                pstmt.setString(2, data.getPassword());
                pstmt.setFloat(3, 120.0f);
                pstmt.setInt(4,data.getDatatype());
                pstmt.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
                pstmt.setTimestamp(6,new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(7,id);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_USER_NUM_BY_NAMECN = "select count(*) from tbl_olympic_members where name_cn=? and idnum=?";

    static public int getNumOfUserByCNName(String name,String idnum) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        String tbuf="";
        int num = 0;
        List list = new ArrayList();
        OlympicMembers m=new OlympicMembers();
        try {
            try {
                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                pstmt = conn.prepareStatement(SQL_USER_NUM_BY_NAMECN);
                name = StringUtil.replace(name," ","");
                pstmt.setString(1,name);
                pstmt.setString(2,idnum);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    num = rs.getInt(1);
                    System.out.println("name=" + name + "======" + num);
                }
                rs.close();
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }

        return num;
    }

    private static final String SQL_USER_NUM = "select username,name_cn,idnum from tbl_olympic_members where idnum=?";

    static public String getNumOfUser(String idnum) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        String tbuf="";
        int num = 0;
        List list = new ArrayList();
        OlympicMembers m=new OlympicMembers();
        try {
            try {
                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                pstmt = conn.prepareStatement(SQL_USER_NUM);
                pstmt.setString(1,idnum);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    m=new OlympicMembers();
                    m.setUsername(rs.getString("username"));
                    m.setName_cn(rs.getString("name_cn"));
                    m.setIdnum(rs.getString("idnum"));
                    list.add(m);
                    num = num + 1;
                    tbuf = tbuf + rs.getString("idnum") + "---------" + rs.getString("name_cn") + ",";
                }
                rs.close();
                pstmt.close();

                if (list.size()>1) {
                    conn.setAutoCommit(false);
                    m=new OlympicMembers();
                    m = (OlympicMembers)list.get(0);
                    String nm = m.getUsername();
                    String id_num = m.getIdnum();
                    pstmt = conn.prepareStatement("delete from tbl_olympic_members where username!=? and idnum=?");
                    pstmt.setString(1,nm);
                    pstmt.setString(2,id_num);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }

        if (num>1)
            return tbuf;
        else
            return "";
    }


    private static final String SQL_UPDATE_Talent = "update tbl_olympic_members set location=?,titleInOlympic=?,mobilephone=?,company=?,title=?,talentflag=1  where name_cn=?";

    static public int updateTalent(OlympicMembers data) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int num = 0;
        try {
            String t_dbip = "localhost";
            String t_username = "beijing2008";
            String t_password = "qazwsxokm";
            int row = 0;
            conn = createConnection(t_dbip, t_username, t_password,1);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UPDATE_Talent);
            pstmt.setString(1,data.getLocation());
            pstmt.setString(2,data.getTitleInOlympic());
            pstmt.setString(3,data.getMobilephone());
            pstmt.setString(4,data.getCompany());
            pstmt.setString(5,data.getTitle());
            pstmt.setString(6,data.getName_cn());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exp1) {
                exp1.printStackTrace();
            }
            throw new ExtendAttrException("Database exception: create article failed.");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return num;
    }

    private static final String SQL_UPDATE_CNNAME = "update tbl_olympic_members set name_cn=? where idnum=?";

    static public int updateCnName(String idnum,String name) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int num = 0;
        try {
            String t_dbip = "localhost";
            String t_username = "beijing2008";
            String t_password = "qazwsxokm";
            int row = 0;

            System.out.println("row=" + row  + "-----idnum=" + idnum);

            conn = createConnection(t_dbip, t_username, t_password,1);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UPDATE_CNNAME);
            pstmt.setString(1,name);
            pstmt.setString(2,idnum);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exp1) {
                exp1.printStackTrace();
            }
            throw new ExtendAttrException("Database exception: create article failed.");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return num;
    }

    private static final String SQL_UPDATE_IMAGE_AND_LOC_BY_IDNUM = "update tbl_olympic_members set photo=?,location=? where idnum=?";

    static public int updateImageAndLocation(String idnum,String imgname,String location) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int num = 0;
        try {
            String t_dbip = "localhost";
            String t_username = "beijing2008";
            String t_password = "qazwsxokm";
            int row = 0;

            System.out.println("row=" + row  + "-----idnum=" + idnum);

            conn = createConnection(t_dbip, t_username, t_password,1);
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UPDATE_IMAGE_AND_LOC_BY_IDNUM);
            pstmt.setString(1,imgname);
            pstmt.setString(2,location);
            pstmt.setString(3,idnum);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exp1) {
                exp1.printStackTrace();
            }
            throw new ExtendAttrException("Database exception: create article failed.");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return num;
    }

    private static final String SQL_UPDATE_USERNAME_AND_PASSWORD_BY_IDNUM = "update tbl_olympic_members set username=?,password=? where idnum=?";

    static public int updateUsernameAndPassword(String idnum) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int num = 0;
        try {
            String t_dbip = "localhost";
            String t_username = "beijing2008";
            String t_password = "qazwsxokm";
            int row = 0;
            if (idnum!=null && idnum!="" && !idnum.equalsIgnoreCase("110225196811250312")) {
                System.out.println("row=" + row  + "-----idnum=" + idnum);
                conn = createConnection(t_dbip, t_username, t_password,1);
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_USERNAME_AND_PASSWORD_BY_IDNUM);
                pstmt.setString(1,idnum);
                pstmt.setString(2,idnum);
                pstmt.setString(3,idnum);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException exp1) {
                exp1.printStackTrace();
            }
            throw new ExtendAttrException("Database exception: create article failed.");
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return num;
    }


    private static final String SQL_CREATE_ARTICLE_500M =
            "INSERT INTO tbl_olympic_members (username,password,name_cn,sex,age,company,title,address,postcode,officetelephone,mobilephone,email,deptOfOlympic,memberTypeOfOlympic) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    static public void create500m(OlympicMembers data) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        try {
            try {

                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                conn.setAutoCommit(false);

                //增加文章
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_500M);
                pstmt.setString(1, data.getUsername());
                pstmt.setString(2, data.getPassword());
                pstmt.setString(3, data.getName_cn());
                pstmt.setInt(4, data.getSex());
                pstmt.setInt(5,data.getAge());
                pstmt.setString(6, data.getCompany());
                pstmt.setString(7, data.getTitle());
                pstmt.setString(8, data.getAddress());
                pstmt.setString(9, data.getPostcode());
                pstmt.setString(10,data.getOfficetelephone());
                pstmt.setString(11,data.getMobilephone());
                pstmt.setString(12,data.getEmail());
                pstmt.setString(13, data.getDeptOfOlympic());
                pstmt.setString(14, data.getMemberTypeOfOlympic());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATE_ARTICLE_500M = "update tbl_olympic_members set company=?,title=?,address=?,postcode=?,officetelephone=?,mobilephone=?,email=?,deptOfOlympic=?,memberTypeOfOlympic=? where name_cn=? ";

    static public void update500m(OlympicMembers data) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        try {
            try {

                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                conn.setAutoCommit(false);

                //修改内容
                pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_500M);
                pstmt.setString(1, data.getCompany());
                pstmt.setString(2, data.getTitle());
                pstmt.setString(3, data.getAddress());
                pstmt.setString(4, data.getPostcode());
                pstmt.setString(5, data.getOfficetelephone());
                pstmt.setString(6, data.getMobilephone());
                pstmt.setString(7, data.getEmail());
                pstmt.setString(8, data.getDeptOfOlympic());
                pstmt.setString(9, data.getMemberTypeOfOlympic());
                pstmt.setString(10,data.getName_cn());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATE_ARTICLE_3000M_MP = "update tbl_olympic_members set mobilephone=? where name_cn=? ";

    private static final String SQL_UPDATE_ARTICLE_3000M_EMAIL = "update tbl_olympic_members set email=? where name_cn=? ";

    static public void update3000m(OlympicMembers data) throws ExtendAttrException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        try {
            try {

                String t_dbip = "localhost";
                String t_username = "beijing2008";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                conn.setAutoCommit(false);

                //修改手机号码
                if (data.getMobilephone() != null && data.getMobilephone() != "") {
                    pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_3000M_MP);
                    pstmt.setString(1, data.getMobilephone());
                    pstmt.setString(2,data.getName_cn());
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //修改电子邮件地址
                if (data.getEmail() != null && data.getEmail()!="") {
                    pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_3000M_EMAIL);
                    pstmt.setString(1, data.getEmail());
                    pstmt.setString(2,data.getName_cn());
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ExtendAttrException("Database exception: create article failed.");
            } finally {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new ExtendAttrException("Database exception: can't rollback?");
        }
    }

    private static Connection createConnection(String ip, String username, String password, int flag) {
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
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl10g", dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

}
