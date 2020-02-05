package com.transferBjrabData;

import com.bizwink.cms.security.User;
import com.bizwink.cms.tree.snode;
import com.bizwink.util.MD5Util;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class transgerDzll {
    public transgerDzll() {

    }

    private static Connection createConnection(String ip, String username, String password, String server,int port,int flag) {
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

    public static Workbook getWeebWork(String filename) throws IOException {
        Workbook workbook=null;
        if(null!=filename){
            String fileType=filename.substring(filename.lastIndexOf("."),filename.length());
            FileInputStream fileStream = new FileInputStream(new File(filename));
            if(".xls".equals(fileType.trim().toLowerCase())){
                workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
            }else if(".xlsx".equals(fileType.trim().toLowerCase())){
                workbook = new XSSFWorkbook(fileStream);//创建 Excel 2007 工作簿对象
            }
        }

        return workbook;
    }

    public static List getInfolistFromExcel(String excelFilename) {
        int  numSheets = 0;
        Sheet aSheet = null;
        List messages = new ArrayList();
        Gwcase gwcase = null;
        try {
            Workbook workbook = getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    gwcase = new Gwcase();
                    for (short cellNumOfRow = 0; cellNumOfRow <= aRow.getLastCellNum(); cellNumOfRow++) {
                        String buf = "";
                        if (null != aRow.getCell(cellNumOfRow)) {
                            Cell aCell = aRow.getCell(cellNumOfRow);//获得列值
                            if (aCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                buf = aCell.getStringCellValue();
                                buf = buf.trim();
                            } else if (aCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
                                buf = String.valueOf((long)aCell.getNumericCellValue());
                        }

                        //供应商名称
                        if (cellNumOfRow == 0) {
                            gwcase.setUserid(buf.trim());
                            //System.out.println(gwcase.getUserid());
                        }


                        //经营物资
                        if (cellNumOfRow == 1) {
                            gwcase.setNickname(buf.trim());
                            //System.out.println(gwcase.getNickname());
                        }

                        //站点名称
                        if (cellNumOfRow == 2) {
                            gwcase.setDepartment(buf.trim());
                            //System.out.println(gwcase.getDepartment());
                        }

                        //物资计量单位
                        if (cellNumOfRow == 3) {
                            gwcase.setEname(buf.trim());
                            //System.out.println(gwcase.getEname());
                        }

                        //分类描述
                        if (cellNumOfRow == 4) {
                            gwcase.setDepartcode(buf.trim());
                            //System.out.println(gwcase.getDepartcode());
                        }

                        //关键字名称
                        if (cellNumOfRow == 5) {
                            gwcase.setCname(buf.trim());
                            //System.out.println(gwcase.getCname());
                        }

                        //关键字说明
                        if (cellNumOfRow == 6) {
                            String tdata = buf.trim();
                            if (tdata!=null && tdata!="") gwcase.setOrgid(Integer.parseInt(tdata));
                            //System.out.println(gwcase.getOrgid());
                        }

                        if (cellNumOfRow == 7) {
                            String tdata = buf.trim();
                            if (tdata!=null && tdata!="") gwcase.setCompanyid(Integer.parseInt(tdata));
                            //System.out.println(gwcase.getCompanyid());
                        }

                        if (cellNumOfRow == 8) {
                            String tdata = buf.trim();
                            if (tdata!=null && tdata!="") gwcase.setDeptid(Integer.parseInt(tdata));
                            //System.out.println(gwcase.getDeptid());
                        }
                    }
                    messages.add(gwcase);

                }

                //messages列表数据进行逆序排序
                //List messages_bysort = orderItems(messages, "desc");
                //ClassTree in_tree = getClassesTreeByNodes(messages_bysort);
            }

            //aSheet = workbook.getSheetAt(1);//获得一个sheet
            //for(int ii=0; ii<messages.size(); ii++) {
            //    snode thenode = (snode)messages.get(ii);
            //    Row row = aSheet.createRow(ii);
            //    row.createCell(0).setCellValue(thenode.getChName());
            //    row.createCell(1).setCellValue(thenode.getDesc());
            //    row.createCell(2).setCellValue(thenode.getEnName());
            //}
            //workbook.write(new FileOutputStream("c:\\data\\t1.xlsx"));
        } catch (IOException exp) {
        }


        return messages;
    }

    public static void main(String[] args)
    {
        Connection conn = null;
        PreparedStatement pstmt=null,pstmt1=null;
        ResultSet rs = null,rs1=null;
        List<Integer> artids = new ArrayList();

        //String excelfile = "C:\\website\\sjcmsnew\\userAndDept.xlsx";
        List<Gwcase> data = new ArrayList<>();     //getInfolistFromExcel(excelfile);
        try
        {
            //获取CMS的栏目目录、栏目ID等信息
            String t_dbip = "localhost";
            String t_username = "bjsjsdbadmin";
            String t_password = "qazwsxokm";
            String t_server = "orcl11g";

            conn = createConnection(t_dbip, t_username, t_password,t_server,1521,1);
            pstmt = conn.prepareStatement("select t.userid,t.departcode,td.ename,td.cname,td.indexrules,td.indexnum,t.department,td.id,t.deptid from tbl_members t,tbl_department td where t.departcode=td.ename");

            /*conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_members set orgid=?,companyid=?,deptid=? where userid=?");
            for(int ii=0; ii<data.size(); ii++) {
                Gwcase gwcase = data.get(ii);
                pstmt.setInt(1,gwcase.getOrgid());
                pstmt.setInt(2,gwcase.getCompanyid());
                pstmt.setInt(3,gwcase.getDeptid());
                pstmt.setString(4,gwcase.getUserid());
                pstmt.executeUpdate();
                System.out.println(gwcase.getUserid() + "=" + gwcase.getEname() + "=" + gwcase.getOrgid() + "=" + gwcase.getCompanyid() + "=" + gwcase.getDeptid());
            }
            pstmt.close();

            /*List<User> users = new ArrayList<>();
            pstmt = conn.prepareStatement("select id,userid from tbl_members where id is null");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String userid = rs.getString("userid");
                User user = new User();
                user.setUid(rs.getInt("id"));
                user.setUserID(rs.getString("userid"));
                users.add(user);
            }
            rs.close();
            pstmt.close();

            for(int ii=0;ii<users.size(); ii++) {
                pstmt = conn.prepareStatement("select tbl_userreg_id.NEXTVAL from dual");
                rs = pstmt.executeQuery();
                int uid = 0;
                if (rs.next()) uid = rs.getInt(1);
                rs.close();
                pstmt.close();

                User user = users.get(ii);
                System.out.println("userid==" + user.getUserID());
                pstmt = conn.prepareStatement("update tbl_members set id=? where userid=?");
                pstmt.setInt(1,uid);
                pstmt.setString(2,user.getUserID());
                pstmt.executeUpdate();
                pstmt.close();
            }*/
            conn.commit();
            conn.close();
        } catch (SQLException exp1) {
            exp1.printStackTrace();
        }
    }
}

