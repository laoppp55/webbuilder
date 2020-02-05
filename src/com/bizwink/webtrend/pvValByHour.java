package com.bizwink.webtrend;

import org.apache.poi.hssf.usermodel.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-3
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public class pvValByHour {
    // 设置cell编码解决中文高位字节截断
    private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;

    // 定制浮点数格式
    private static String NUMBER_FORMAT = "#,##0.00";

    // 定制日期格式
    private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int pvnum = 0;
        try {
            conn = createConnection("localhost", "coosite", "qazwsxokm", 1);
            BufferedWriter bw = new BufferedWriter(new FileWriter("1.txt", true));
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet();
            HSSFDataFormat format = workbook.createDataFormat();
            HSSFRow row = sheet.createRow(( short ) 1 );
            HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints(( short ) 10 ); // 字体高度
            //font.setColor(HSSFFont.COLOR_RED); // 字体颜色
            font.setFontName( " 黑体 " ); // 字体
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
            font.setItalic( true ); // 是否使用斜体
//          font.setStrikeout(true); // 是否使用划线

            // 设置单元格类型
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
            cellStyle.setWrapText( true );

            // 添加单元格注释
            // 创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
            //HSSFPatriarch patr = sheet.createDrawingPatriarch();
            // 定义注释的大小和位置,详见文档
            //HSSFComment comment = patr.createComment( new HSSFClientAnchor( 0 , 0 , 0 , 0 , ( short ) 4 , 2 , ( short ) 6 , 5 ));
            // 设置注释内容
            //comment.setString( new HSSFRichTextString( " 可以在POI中添加注释！ " ));
            // 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
            //comment.setAuthor( " Xuys. " );

            //写入表头信息
            HSSFCell cell = row.createCell(( short ) 1);
            //HSSFRichTextString hssfString = new HSSFRichTextString( " Hello World! 欢迎来到这里!" );
            //cell.setCellValue("日期"); // 设置单元格内容
            //cell.setCellStyle(cellStyle); // 设置单元格样式
            //cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 指定单元格格式：数值、公式或字符串
            //cell.setCellComment(comment); // 添加注释

            // 格式化数据
            //row = sheet.createRow(( short ) 2 );
            //cell = row.createCell(( short ) 2 );
            //cell.setCellValue("时间段");
            //cell.setCellStyle(cellStyle);

            //row = sheet.createRow(( short ) 3 );
            //cell = row.createCell(( short ) 3 );
            //cell.setCellValue("浏览量");
            //cell.setCellStyle(cellStyle);

            for(int k=1;k<=31;k++) {
                String month_s = "";
                String sql = "";
                if (k<10)
                    month_s = "0" + k;
                else
                    month_s = String.valueOf(k);
                for(int i=0;i<24;i++) {
                    if (i<9)
                        sql = "select sum(pageview) from tbl_pv_detail where logdate between to_date('2012-10-" + month_s + " 0" + i + ":00:00','yyyy-mm-dd hh24:mi:ss') and to_date('2012-10-" + month_s + " 0" + (i+1) +":00:00','yyyy-mm-dd hh24:mi:ss')";
                    else if (i==9)
                        sql = "select sum(pageview) from tbl_pv_detail where logdate between to_date('2012-10-" + month_s + " 0" + i + ":00:00','yyyy-mm-dd hh24:mi:ss') and to_date('2012-10-" + month_s + " " + (i+1) +":00:00','yyyy-mm-dd hh24:mi:ss')";
                    else if (i==23)
                        sql = "select sum(pageview) from tbl_pv_detail where logdate between to_date('2012-10-" + month_s + " " + i + ":00:00','yyyy-mm-dd hh24:mi:ss') and to_date('2012-10-" + month_s + " " + i +":59:59','yyyy-mm-dd hh24:mi:ss')";
                    else
                        sql = "select sum(pageview) from tbl_pv_detail where logdate between to_date('2012-10-" + month_s + " " + i + ":00:00','yyyy-mm-dd hh24:mi:ss') and to_date('2012-10-" + month_s + " " + (i+1) +":00:00','yyyy-mm-dd hh24:mi:ss')";

                    //System.out.println(sql);
                    pstmt = conn.prepareStatement(sql);
                    rs = pstmt.executeQuery();
                    if (rs.next()) pvnum = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    bw.write("2012-10-" + month_s + ":" + i + "-" + (i + 1) + "   " + pvnum + "\r\n");
                    System.out.println("2012-10-" + month_s + ":" + i + "-" + (i + 1) + "   " + pvnum);

                    //创建EXCEL的行
                    row = sheet.createRow(( short ) i+2);

                    //第一列
                    cell = row.createCell(( short ) 0);
                    cell.setCellValue("2012-10-" + month_s);
                    cellStyle = workbook.createCellStyle();
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 指定单元格格式：数值、公式或字符串
                    cell.setCellStyle(cellStyle);

                    //第二列
                    cell = row.createCell(( short )1);
                    if (i+8<24)
                        cell.setCellValue((i+8) + "-" + (i + 9));
                    else
                        cell.setCellValue((i+8)%24 + "-" + (i + 9)%24);
                    cellStyle = workbook.createCellStyle();
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 指定单元格格式：数值、公式或字符串
                    cell.setCellStyle(cellStyle);

                    //第三列
                    cell = row.createCell(( short )2);
                    cell.setCellValue(String.valueOf(pvnum));
                    cellStyle = workbook.createCellStyle();
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 指定单元格格式：数值、公式或字符串
                    cell.setCellStyle(cellStyle);
                }

                //写EXCEL文件
                FileOutputStream fileOut = new FileOutputStream("201210" + month_s + ".xls");
                workbook.write(fileOut);
                fileOut.close();
            }
            bw.flush();
            bw.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }  finally {
            if (conn != null) {
                try {
                    //cpool.freeConnection(conn);
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

    }

    private static Connection createConnection(String ip, String username, String password, int flag) {
        Connection conn = null;
        String dbip = ip;
        String dbusername = username;
        String dbpassword = password;

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
                //conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:innocom", dbusername, dbpassword);
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl11g", dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":3306/ecms", username, password);
            } else if (flag == 3){
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String strurl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=e:\\dataforonegoo\\wsjc10-08-02-1.mdb";
                conn = DriverManager.getConnection(strurl,username,password);

            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

}
