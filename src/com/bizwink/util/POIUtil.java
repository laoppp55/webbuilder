package com.bizwink.util;

import com.bizwink.cms.tree.snode;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-5-20.
 */
public class POIUtil {
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

    //从EXCEL文件中获取物资分类列表信息
    public static List<snode> getInfolistFromExcel(String excelFilename) {
        int  numSheets = 0;
        Sheet aSheet = null;
        List<snode> messages = new ArrayList<snode>();
        try {
            Workbook workbook = POIUtil.getWeebWork(excelFilename);
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

                        //物资父类编码
                        if (cellNumOfRow == 0) {
                            thenode.setLinkPointer(buf.trim());
                        }

                        //物资分类代码
                        if (cellNumOfRow == 1) {
                            thenode.setId(buf.trim());
                        }

                        //物资名称
                        if (cellNumOfRow == 2) {
                            thenode.setChName(buf.trim());
                        }

                        //物资计量单位
                        if (cellNumOfRow == 3) {
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
                            thenode.setKeyword(buf.trim());
                        }
                    }

                    messages.add(thenode);
                }
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return GeneralMethod.orderItems(messages, "desc");
    }

}
