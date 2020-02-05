package com.unittest;

import com.bizwink.cms.tree.snode;
import com.bizwink.util.GeneralMethod;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 18-9-13.
 */
public class readInfoFromExcel {
    /**
     *
     * @Title: getWeebWork
     * @Description: TODO(根据传入的文件名获取工作簿对象(Workbook))
     * @param filename
     * @return
     * @throws java.io.IOException
     */
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
        try {
            Workbook workbook = getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    snode thenode = new snode();
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
                        if (cellNumOfRow == 2) {
                            thenode.setChName(buf.trim());
//                                System.out.println(thenode.getChName());
                        }


                        //经营物资
                        if (cellNumOfRow == 7) {
                            thenode.setDesc(buf.trim());
                            if (thenode.getDesc().indexOf("阀门")>0)
                                System.out.println(thenode.getChName());
                        }

                        //站点名称
                        if (cellNumOfRow == 10) {
                            //System.out.println(buf);
                            thenode.setEnName(buf.trim());
                        }

                        //物资计量单位
                        /*if (cellNumOfRow == 3) {
                            thenode.setUnit(buf.trim());
                        }

                        //分类描述
                        if (cellNumOfRow == 4) {
                            thenode.setDesc(buf.trim());
                        }

                        //关键字名称
                        if (cellNumOfRow == 5) {
                            thenode.setKeyword(buf.trim());
                        }

                        //关键字说明
                        if (cellNumOfRow == 6) {
                            thenode.setKeywordnote(buf.trim());
                        }*/
                    }

                    //           System.out.println(thenode.getId() + "===" + thenode.getChName());

                    if (thenode.getDesc().indexOf("阀门")>0)
                        messages.add(thenode);
                }

                //messages列表数据进行逆序排序
                //List messages_bysort = orderItems(messages, "desc");
                //ClassTree in_tree = getClassesTreeByNodes(messages_bysort);
            }

            aSheet = workbook.getSheetAt(1);//获得一个sheet
            for(int ii=0; ii<messages.size(); ii++) {
                snode thenode = (snode)messages.get(ii);
                Row row = aSheet.createRow(ii);
                row.createCell(0).setCellValue(thenode.getChName());
                row.createCell(1).setCellValue(thenode.getDesc());
                row.createCell(2).setCellValue(thenode.getEnName());
            }
            workbook.write(new FileOutputStream("c:\\data\\t1.xlsx"));
        } catch (IOException exp) {
        }


        return messages;
    }

    public static void main(String[] args) {
        String excelFilename = "C:\\data\\9.6.xlsx";
        List messages_bysort = getInfolistFromExcel(excelFilename);

    }
}