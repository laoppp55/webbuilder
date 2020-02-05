package com.unittest;

import com.bizwink.cms.news.Column;
import com.bizwink.cms.util.FileUtil;
import com.transferBjrabData.Data;
import com.transferBjrabData.StatData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
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
        } else {
            workbook = new XSSFWorkbook();
        }

        return workbook;
    }


    public static void main(String[] args) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> departments = new ArrayList<>();
        List<Column> columns = new ArrayList<>();
        Column column = null;

        try{
            String tjsql = "select t.columnid,t.maintitle,t.source,t.createdate,t.editor from tbl_article t where t.columnid in (select id from tbl_column m start with m.id=? connect by m.parentid=prior m.id) and t.source=? and to_char(t.createdate,'yyyyMMdd')>='20190101' and to_char(t.createdate,'yyyyMMdd')<='20191231' and t.siteid=40 and t.status=1 and t.pubflag=0 and t.editor<>'石景山信用双公示' and t.editor<>'spider' order by t.createdate desc";

            //获取CMS的栏目目录、栏目ID等信息
            String t_dbip = "192.166.96.6";
            String t_username = "bjsjsdbadmin";
            String t_password = "qazwsxokm";
            String t_server = "oracle10g";
            conn = createConnection(t_dbip, t_username, t_password,t_server,1521,1);
            pstmt = conn.prepareStatement("select distinct unit from tbl_department");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                departments.add(rs.getString("unit"));
            }
            departments.add("工商联办公室");
            departments.add("石景山电子报");

            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("select t.id,t.cname from tbl_column t where t.siteid=40 and t.parentid=2");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                column = new Column();
                column.setID(rs.getInt("id"));
                column.setCName(rs.getString("cname"));
                columns.add(column);
            }
            rs.close();
            pstmt.close();

            StringBuffer articles = null;
            StatData statData = null;
            List<StatData> statDataList = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            for(int ii=0;ii<departments.size();ii++) {
                String source = departments.get(ii);
                for(int jj=0;jj<columns.size();jj++) {
                    articles = new StringBuffer();
                    int count =0;
                    column = columns.get(jj);
                    String columnname = column.getCName();
                    statData = new StatData();
                    pstmt = conn.prepareStatement(tjsql);
                    pstmt.setInt(1,column.getID());
                    pstmt.setString(2,source);
                    rs = pstmt.executeQuery();
                    while(rs.next()) {
                        count = count + 1;
                        articles.append(columnname + ",    " + rs.getString("maintitle") + ",   " + simpleDateFormat.format(rs.getTimestamp("createdate")) + "\r\n");
                        System.out.println(columnname + "," + rs.getString("maintitle"));
                    }
                    rs.close();
                    pstmt.close();
                    if (count>0) {
                        statData.setColumnname(columnname);
                        statData.setDepartment(source);
                        statData.setArticlecount(count);
                        statDataList.add(statData);
                        FileUtil.writeFile(articles,"C:\\data\\bjsjs\\" + columnname + "-" + source + ".txt");
                    }
                }
            }

            //写EXCEL文件
            int  numSheets = 0;
            Workbook workbook = getWeebWork(null);
            Sheet aSheet = workbook.createSheet("sheet0");
            for(int ii=0; ii<statDataList.size(); ii++) {
                statData = (StatData)statDataList.get(ii);
                Row row = aSheet.createRow(ii);
                row.createCell(0).setCellValue(statData.getDepartment());
                row.createCell(1).setCellValue(statData.getColumnname());
                row.createCell(2).setCellValue(statData.getArticlecount());
            }
            workbook.write(new FileOutputStream("C:\\data\\bjsjs\\bjsjs_stat.xlsx"));
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp) {

            }
        }

    }
}
