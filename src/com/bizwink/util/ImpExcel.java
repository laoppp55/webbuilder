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
 * Date: 2011-10-31
 * Time: 22:06:11
 * To change this template use File | Settings | File Templates.
 */
public class ImpExcel {
    public static void main(String[] args) throws Exception {
        //updateUsernameAndPassword();
        //java.io.File excelfile = new java.io.File("D:\\leads\\首信\\奥运之家数据\\用户数据\\新数据\\北京奥组委-18182.xls");
        java.io.File excelfile = new java.io.File("D:\\mybog.xls");
        try{
            FileWriter writer = new FileWriter("c:\\222.txt", true);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = null;

            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelfile));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            System.out.println("workbook.getNumberOfSheets()=" + workbook.getNumberOfSheets());
            //for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
            int  numSheets = 0;
            HSSFSheet aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                String name="";
                String tbuf = "";
                for (int rowNumOfSheet = 0; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    //String content_by_row="";
                    OlympicMembers data = new OlympicMembers();
                    if (null != aSheet.getRow(rowNumOfSheet) && rowNumOfSheet>1) {
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

                            if (cellNumOfRow == 1) {
                                int num = getUserNumByCNName(buf.trim());
                                System.out.println(buf + "===" + num);
                                if (num > 1) writer.write(buf + "====" + num +"\r\n");
                                //data.setUsername(buf);
                                //data.setRegnum(buf);
                            }

                            /*if (cellNumOfRow == 12) {
                                data.setMaincategory(buf);
                            }

                            if (cellNumOfRow == 13) {
                                data.setSubcategory(buf);
                            }

                            if (cellNumOfRow == 14) {
                                data.setMainfunc(buf);
                            }

                            if (cellNumOfRow == 17) {
                                data.setMembertype(buf);
                            }

                            if (cellNumOfRow == 18) {
                                //if (buf!=null && buf!="") tbuf = buf;
                                data.setName_py(buf);
                            }

                            if (cellNumOfRow == 19) {
                                if (buf!=null && buf!="") buf = StringUtil.replace(buf," ","");
                                data.setName_cn(buf);
                            }

                            if (cellNumOfRow == 15) {
                                if (buf.equals("M"))
                                    data.setSex(0);
                                else
                                    data.setSex(1);
                            }

                            if (cellNumOfRow == 20) {
                                data.setNationality(buf);
                            }

                            if (cellNumOfRow == 21) {
                                data.setIdtype(buf);
                            }

                            if (cellNumOfRow == 22) {
                                data.setPassword(buf);
                                data.setIdnum(buf);
                            }

                            if (cellNumOfRow == 23) {
                                //Timestamp appointTime=Timestamp.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(buf));
                                //System.out.println(buf);
                                String[] tb = buf.split("/");
                                //System.out.println(tb[2] + "-" + tb[1] +"-" + tb[0] + " 00:00:00.0");
                                Timestamp validDate=Timestamp.valueOf(tb[2] + "-" + tb[1] +"-" + tb[0] + " 00:00:00.0");
                                data.setIdvaliddate(validDate);
                            }

                            if (cellNumOfRow == 24) {
                                //Timestamp appointTime=Timestamp.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(buf));
                                //System.out.println(buf);
                                String[] tb = buf.split("/");
                                //System.out.println(tb[2] + "-" + tb[1] +"-" + tb[0] + " 00:00:00.0");
                                Timestamp birthDate=Timestamp.valueOf(tb[2] + "-" + tb[1] +"-" + tb[0] + " 00:00:00.0");
                                data.setBirthdate(birthDate);
                            }

                            if (cellNumOfRow == 25) {
                                data.setCountry(buf);
                            }

                            if (cellNumOfRow == 26) {
                                data.setYjcountry(buf);
                            }

                            if (cellNumOfRow == 27) {                 //国内人员永久居住城市
                                if (buf!=null && buf!="")
                                data.setYjcity(buf);
                            }

                            if (buf == null || buf =="") {           //国外人员永久居住城市
                                if (cellNumOfRow == 28) {
                                    if (buf!=null && buf!="")
                                    data.setYjcity(buf);
                                }
                            }

                            if (cellNumOfRow == 47) {                 //所在场馆
                                data.setLocation(buf);
                            }

                            if (cellNumOfRow == 49) {                //照片名称
                                data.setPhoto(buf);
                            }

                            if (cellNumOfRow == 51) {                //永久居住地址
                                data.setYjaddress(buf);
                            }

                            if (cellNumOfRow == 52) {                //现居住国家
                                data.setCjcountry(buf);
                            }

                            if (cellNumOfRow == 53) {                //现居住省市
                                data.setCjcity(buf);
                            }

                            if (cellNumOfRow == 54) {                //现居住详细地址
                                data.setCjaddress(buf);
                            }

                            //500人输入
                            if (cellNumOfRow == 0) {
                                data.setUsername(buf);
                                data.setPassword(buf);
                            } else if (cellNumOfRow == 1) {
                                name = buf.substring(0,1);
                                name = name + " " +buf.substring(1);
                                data.setName_cn(name);
                            } else if (cellNumOfRow == 2) {
                                if (buf.equals("男"))
                                    data.setSex(0);
                                else
                                    data.setSex(1);
                            } else if (cellNumOfRow == 3) {
                                int age =0;
                                if (buf != null && buf!="") age = Integer.parseInt(buf);
                                data.setAge(age);
                            } else if (cellNumOfRow == 4) {
                                data.setCompany(buf);
                            } else if (cellNumOfRow == 5) {
                                data.setAddress(buf);
                            } else if (cellNumOfRow == 6) {
                                data.setPostcode(buf);
                            } else if (cellNumOfRow == 7) {
                                System.out.println("mobilephone=" + buf);
                                if (buf.length() == 11)
                                    data.setMobilephone(buf);
                                else
                                    data.setOfficetelephone(buf);
                            } else if (cellNumOfRow == 8) {
                                data.setEmail(buf);
                            } else if (cellNumOfRow == 9) {
                                data.setDeptOfOlympic(buf);
                            } else if (cellNumOfRow == 10) {
                                data.setMemberTypeOfOlympic(buf);
                            }

                            //3000人输入
                            //if (cellNumOfRow == 0) {
                            //    data.setUsername(buf);
                            //    data.setPassword(buf);
                            //} else
                            if (cellNumOfRow == 1) {
                                name = buf.substring(0,1);
                                name = name + " " +buf.substring(1);
                                data.setName_cn(name);
                            } else if (cellNumOfRow == 5) {
                                data.setMobilephone(buf);
                            } else if (cellNumOfRow == 6) {
                                data.setEmail(buf);
                            }*/
                        }

                        //updateImageAndLocation(data.getIdnum(),data.getPhoto(),data.getLocation());
                        //updateCnName(data.getIdnum(),data.getName_cn());
                        //updateUsernameAndPassword(data.getIdnum());

                        /*System.out.println(data.getUsername() + "||" + data.getPassword() + "||" + data.getSex() + "||" + data.getAge() +
                                "||" + data.getName_cn() + "||" + data.getCompany() + "||" + data.getAddress() + "||" + data.getPostcode() +
                                "||" + data.getMobilephone() +"||" + data.getDeptOfOlympic() + "||" + data.getMemberTypeOfOlympic() +
                                "||" + data.getOfficetelephone() + "||" + data.getEmail());

                        int num = getNumOfUserByCNName(data.getName_cn(),data.getIdnum());
                        if (num>1) {
                            writer.write(data.getName_cn() + "====" + num +"\r\n");
                        } else if (num==0 && !data.getIdnum().equals("000000000000000000")) {
                            create(data);
                            //updateTalent(data);
                        }*/

                        //System.out.println(rowNumOfSheet + "====" + data.getLocation() + "---" + data.getName_cn() + "||" + data.getMobilephone());

                        //create(data);

                        /*List names = new ArrayList();
                        boolean name_exist = false;
                        for(int i=0; i<names.size(); i++) {
                            String ttt= (String)names.get(i);
                            if (ttt.equals(data.getIdnum())) {
                                name_exist = true;
                                break;
                            }
                        }

                        if (name_exist == false) {
                            String ttbuf = getNumOfUser(data.getIdnum());
                            if (ttbuf!=null && ttbuf!="") {
                                //update3000m(data);
                                writer.write(ttbuf + "\r\n");
                                System.out.println(rowNumOfSheet + "====" + ttbuf);
                            } else {
                                System.out.println(rowNumOfSheet + "=====" + data.getIdnum() + "===1");
                            }
                            names.add(data.getIdnum());
                        }

                        num = getNumOfUser(name);
                        if (num == 0) {
                            //增加用户信息
                            create500m(data);
                            writer.write(name + "====" + num + "\r\n");
                        } else if (num>0) {
                            //修改用户信息
                            update500m(data);
                        }

                        //if (rowNumOfSheet==1500) break;
                        */
                    }
                }
                writer.close();
            }
            //}
        }catch (IOException exp) {
            exp.printStackTrace();
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

    private static final String SQL_USERNUM_BY_NAMECN = "select count(*) from tbl_olympic_members where name_cn=?";

    static public int getUserNumByCNName(String name) throws ExtendAttrException {
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
                String t_username = "bj2008dbadmin";
                String t_password = "qazwsxokm";
                conn = createConnection(t_dbip, t_username, t_password,1);
                pstmt = conn.prepareStatement(SQL_USERNUM_BY_NAMECN);
                name = StringUtil.replace(name," ","");
                pstmt.setString(1,name);
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

    /**
     * 创建文章
     *
     * @param:extendList  扩展属性List
     * @param:attechments 文章附件List
     * @param:article     文章对象Article
     * @throws com.bizwink.cms.extendAttr.ExtendAttrException
     */
    //membertype:人员类型   新增加
    //maincategory:主类别  新增加
    //subcategory:子类别  新增加
    //mainfunc：主要职责  新增加
    //regnum:注册号  新增加
    private static final String SQL_CREATE_ARTICLE =
            "INSERT INTO tbl_olympic_members (username,password,name_py,name_cn,name_cy,prename,sex,birthdate,regnum,membertype,maincategory,subcategory,mainfunc," +
                    "country,nationality,area,officetelephone,hometelephone,mobilephone,email,ethnic,placeofbirth,timeforjoincp," +
                    "timeofstartwork,memberofcpc,idfromcountry,idnum,idtype,idvaliddate,contactpersonname,contactpersonrelation," +
                    "contactpersonphone,yjcountry,yjprovince,yjcity,yjaddress,cjcountry,cjprovince,cjcity,cjaddress,company,title,titleInOlympic,location,talentflag) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    static public void create(OlympicMembers data) throws ExtendAttrException {
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
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE);
                pstmt.setString(1, data.getUsername());
                pstmt.setString(2, data.getPassword());
                pstmt.setString(3, data.getName_py());
                pstmt.setString(4, data.getName_cn());
                pstmt.setString(5, data.getName_cy());
                pstmt.setString(6,data.getPrename());
                pstmt.setInt(7, data.getSex());
                pstmt.setTimestamp(8, data.getBirthdate());
                pstmt.setString(9, data.getRegnum());
                pstmt.setString(10, data.getMembertype());
                pstmt.setString(11, data.getMaincategory());
                pstmt.setString(12, data.getSubcategory());
                pstmt.setString(13, data.getMainfunc());
                pstmt.setString(14, data.getCountry());
                pstmt.setString(15, data.getNationality());
                pstmt.setString(16, data.getArea());
                pstmt.setString(17, data.getOfficetelephone());
                pstmt.setString(18, data.getHometelephone());
                pstmt.setString(19, data.getMobilephone());
                pstmt.setString(20, data.getEmail());
                pstmt.setString(21, data.getEthnic());
                pstmt.setString(22, data.getPlaceofbirth());
                pstmt.setTimestamp(23, data.getTimeforjoincp());
                pstmt.setTimestamp(24, data.getTimeofstartwork());
                pstmt.setInt(25, data.getMemberofcpc());
                pstmt.setString(26, data.getIdfromcountry());
                pstmt.setString(27, data.getIdnum());
                pstmt.setString(28, data.getIdtype());
                pstmt.setTimestamp(29, data.getIdvaliddate());
                pstmt.setString(30, data.getContactpersonname());
                pstmt.setString(31, data.getContactpersonrelation());
                pstmt.setString(32, data.getContactpersonphone());
                pstmt.setString(33, data.getYjcountry());
                pstmt.setString(34, data.getYjprovince());
                pstmt.setString(35, data.getYjcity());
                pstmt.setString(36, data.getYjaddress());
                pstmt.setString(37, data.getCjcountry());
                pstmt.setString(38, data.getCjprovince());
                pstmt.setString(39, data.getCjcity());
                pstmt.setString(40, data.getCjaddress());
                pstmt.setString(41, data.getCompany());
                pstmt.setString(42, data.getTitle());
                pstmt.setString(43, data.getTitleInOlympic());
                pstmt.setString(44, data.getLocation());
                pstmt.setInt(45, 1);
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
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl11g", dbusername, dbpassword);
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
