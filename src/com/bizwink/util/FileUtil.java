package com.bizwink.util;

import com.bizwink.webtrend.IPInfos;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 17-9-28.
 */
public class FileUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static boolean writeTxtFile(String content,File fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream o=null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
            flag=true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            if(mm!=null){
                mm.close();
            }
        }
        return flag;
    }

    public static void writeExcel(List<IPInfos> dataList, int cloumnCount, String finalXlsxPath){
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            //第一步，创建一个workbook对应一个excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            //第二部，在workbook中创建一个sheet对应excel中的sheet
            HSSFSheet sheet = workbook.createSheet("独立IP分析");
            /**
             * 往Excel中写新数据
             */
            int totalrow = 0;
            for (int j = 0; j < dataList.size(); j++) {
                // 得到要插入的每一条记录
                IPInfos ipInfos = dataList.get(j);
                String ipaddr = ipInfos.getIpaddress();
                List<String> urls = ipInfos.getUrls().get(ipaddr);
                List<Date> accessDateTimes = ipInfos.getAccesstime().get(ipaddr);
                //for (int ii=totalrow; ii<urls.size(); ii++) {
                    //String url = urls.get(ii);
                    //Date accessDateTime = accessDateTimes.get(ii);
                    // 创建一行：从第二行开始，跳过属性列
                    Row row = sheet.createRow(j);
                    for (int k = 0; k <= columnNumCount; k++) {
                        // 在一行内循环
                        Cell first = row.createCell(0);
                        first.setCellValue(ipaddr);

                        Cell second = row.createCell(1);
                        second.setCellValue(String.valueOf(ipInfos.getAccessnum()));

                        //Cell third = row.createCell(2);
                        //third.setCellValue(url);
                    }
                }
                //totalrow = totalrow + urls.size();
            //}

            //将文件保存到指定的位置
            FileOutputStream fos = new FileOutputStream(finalXlsxPath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @param:in
     * @param:filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}
