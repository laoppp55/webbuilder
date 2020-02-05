package com.unittest;

import com.bizwink.cms.util.FileUtil;
import com.heaton.bot.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ChemData {
    /**
     *  * @param args
     *  */
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx=null;
        ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        DataSource dataSource =(DataSource)ctx.getBean("myDataSource");
        String dataFilename = "C:\\Document\\leads\\企业官网汇\\整理后的内容\\8-48.txt";

        //返回所有分类信息
         List<CompClassinfo> compClassinfos = getClassinfo(dataSource);

        List<String> datas = FileUtil.readFileByEncoding(dataFilename,"gbk");
        List<String> companies = new ArrayList<String>();
        String tbuf = "";
        String products = "";
        for(int ii=0; ii<datas.size(); ii++) {
            if (datas.get(ii).trim().length() > 0){
                if (datas.get(ii).indexOf("主要产品") > -1 || products.length()>0) {
                    products = products + datas.get(ii);
                } else
                    tbuf = tbuf + datas.get(ii);
            }else {
                companies.add(tbuf + StringUtil.replace(products,"\r\n",""));
                tbuf = "";
                products = "";
            }
        }

        CompData compData = null;
        List<CompData> compDataList = new ArrayList<CompData>();
        for(int ii=0; ii<companies.size(); ii++) {
            compData = new CompData();
            List<String> theprods = null;
            tbuf = companies.get(ii);
            String[] infos = tbuf.split("\r\n");
            for(int jj=0; jj<infos.length; jj++){
                //冒号分割信息
                int colon_posi = infos[jj].indexOf("：");
                if (colon_posi==-1) {
                    colon_posi = infos[jj].indexOf(":");
                }
                if (colon_posi == -1 && infos[jj].indexOf("有进出口权")==-1 && infos[jj].indexOf("主要产品")==-1)
                    compData.setName(infos[jj]);
                else if (infos[jj].indexOf("主要产品")>0) {
                    String[] prods = null;
                    int semicolon_posi = infos[jj].indexOf("；");      //判断是否按中文分号分割
                    if (semicolon_posi>-1)
                        prods = infos[jj].split("；");           //按中文分号分割
                    else {
                        prods = infos[jj].split(";");            //按英文分号分割
                    }
                    theprods = new ArrayList<String>();
                    for(int kk=0; kk<prods.length; kk++) {
                        int mainprod_posi = prods[kk].indexOf("【主要产品】");
                        if (mainprod_posi>-1)
                            theprods.add(prods[kk].substring(mainprod_posi + "【主要产品】".length()));
                        else
                            theprods.add(prods[kk]);
                    }
                    compData.setProducts(theprods);
                } else if (infos[jj].indexOf("地址")>0) {
                    compData.setAddress(infos[jj]);
                } else if (infos[jj].indexOf("电话")>0) {
                    compData.setTelephone(infos[jj]);
                } else if (infos[jj].indexOf("传真")>0) {
                    compData.setFax(infos[jj]);
                } else if (infos[jj].indexOf("固定资产")>0) {
                    String assets = StringUtil.replace(infos[jj],",","");
                    assets = StringUtil.replace(infos[jj],"，","");
                    assets = StringUtil.replace(infos[jj],"千元","");
                    compData.setAssets(Float.parseFloat(assets));
                } else if (infos[jj].indexOf("职工人数")>0) {
                    String staffs = StringUtil.replace(infos[jj],"人","");
                    compData.setStaffs(Integer.parseInt(staffs));
                } else if (infos[jj].indexOf("经济类型")>0) {
                    compData.setComptype(0);
                } else if (infos[jj].indexOf("有进出口权")>0) {
                    compData.setImpexp(1);
                } else if (infos[jj].indexOf("法人代表")>0) {
                    compData.setLegal(infos[jj]);
                } else if (infos[jj].indexOf("网址")>0) {
                    compData.setLegal(infos[jj]);
                } else if (infos[jj].indexOf("Email")>0 || infos[jj].indexOf("E-mail")>0) {
                    compData.setEmail(infos[jj]);
                } else if (infos[jj].indexOf("供销电话")>0) {
                    compData.setSupplyAndSellPhone(infos[jj]);
                } else if (infos[jj].indexOf("供销传真")>0) {
                    compData.setSupplyAndSellFax(infos[jj]);
                } else if (infos[jj].indexOf("企业规模")>0) {
                    compData.setCompscale(1);
                } else if (infos[jj].indexOf("产值")>0) {
                    compData.setCompscale(1);
                } else if (infos[jj].indexOf("销售收入")>0) {
                    compData.setCompscale(1);
                }
            }
            compDataList.add(compData);
        }

        //进行产品分词，将分词结果写入EXCEL表
        // 创建一个新的HSSFWorkbook对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个Excel的工作表，可以指定工作表的名字
        HSSFSheet sheet = workbook.createSheet("产品分类对照");
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);

        // 创建字体，红色、粗体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFFont.COLOR_RED);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 创建字体，黑色、非粗体
        HSSFFont font1 = workbook.createFont();
        font1.setColor(HSSFFont.COLOR_NORMAL);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        // 创建单元格的格式，如居中、左对齐等
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平方向上居中对齐

        // 垂直方向上居中对齐
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setFont(font); // 设置字体

        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle1.setFont(font1);

        // 下面将建立一个4行3列的表。第一行为表头。
        int rowNum = 0;// 行标
        int colNum = 0;// 列标
        // 建立表头信息
        HSSFRow row = sheet.createRow((short) rowNum); // 在索引0的位置创建行
        row.setHeight((short)500);
        HSSFCell cell = null; // 单元格
        //在当前行的colNum列上创建单元格
        cell = row.createCell((short)0);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("序号"); // 添加内容至单元格

        cell = row.createCell((short)1);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("公司"); // 添加内容至单元格

        cell = row.createCell((short)2);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("产品名称"); // 添加内容至单元格

        cell = row.createCell((short)3);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("产品分词表"); // 添加内容至单元格

        cell = row.createCell((short)4);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("二字以上分词表"); // 添加内容至单元格

        cell = row.createCell((short)5);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("分类代码"); // 添加内容至单元格

        cell = row.createCell((short)6);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("分类名称"); // 添加内容至单元格

        rowNum++;

        for(int jj=0; jj<compDataList.size(); jj++) {
            System.out.println(compDataList.get(jj).getName());
            List<String> theprods = compDataList.get(jj).getProducts();
            if (theprods!=null) {
                for (int kk = 0; kk < theprods.size(); kk++) {
                    System.out.println(StringUtil.replace(theprods.get(kk)," ",""));
                    List<String> words = splitWord(StringUtil.replace(theprods.get(kk)," ",""), true);     // 显示拆分结果
                    String keywords = "";
                    String dz_keywords = "";
                    for (int n = 0; n < words.size(); n++) {
                        if (words.get(n).length()>1) {
                            if (!words.get(n).startsWith("0") && !words.get(n).startsWith("1") && !words.get(n).startsWith("2") && !words.get(n).startsWith("3") && !words.get(n).startsWith("4")
                                    && !words.get(n).startsWith("5") && !words.get(n).startsWith("6") && !words.get(n).startsWith("7") && !words.get(n).startsWith("8")
                                    && !words.get(n).startsWith("9") && !words.get(n).startsWith("一") && !words.get(n).startsWith("二") && !words.get(n).startsWith("三")
                                    && !words.get(n).startsWith("四") && !words.get(n).startsWith("五") && !words.get(n).startsWith("六") && !words.get(n).startsWith("七")
                                    && !words.get(n).startsWith("八") && !words.get(n).startsWith("九") && !words.get(n).startsWith("十")  && !words.get(n).startsWith("甲基")
                                    && !words.get(n).startsWith("八"))
                            dz_keywords = dz_keywords + words.get(n) + ",";
                        }
                        keywords = keywords + words.get(n) + ",";
                    }
                    if (keywords.length() > 0) keywords = keywords.substring(0, keywords.length() - 1);
                    if (dz_keywords.length() > 0) dz_keywords = dz_keywords.substring(0, dz_keywords.length() - 1);

                    row = sheet.createRow((short) rowNum);                                        // 在索引rowNum的位置创建行
                    cell = row.createCell((short) 0);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(rowNum); // 添加内容至单元格

                    cell = row.createCell((short) 1);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(compDataList.get(jj).getName()); // 添加内容至单元格

                    cell = row.createCell((short) 2);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(theprods.get(kk)); // 添加内容至单元格

                    cell = row.createCell((short) 3);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(keywords); // 添加内容至单元格

                    cell = row.createCell((short) 4);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(dz_keywords); // 添加内容至单元格

                    List<CompClassinfo> aa = getMatchingClassinfo(compClassinfos,dz_keywords);
                    String codeOfClass = "";
                    String nameOfClass = "";
                    int count = 0;
                    for(int ii=0; ii<aa.size() && count<5; ii++) {
                        codeOfClass = codeOfClass + aa.get(ii).getEname() + ",";
                        nameOfClass = nameOfClass + aa.get(ii).getCname() + ",";
                        count++;
                    }
                    if (codeOfClass.length()>0) codeOfClass = codeOfClass.substring(0,codeOfClass.length()-1);
                    if (nameOfClass.length()>0) nameOfClass = nameOfClass.substring(0,nameOfClass.length()-1);

                    System.out.println("codeOfClass==" + codeOfClass);
                    System.out.println("nameOfClass==" + nameOfClass);


                    cell = row.createCell((short) 5);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(codeOfClass); // 添加内容至单元格

                    cell = row.createCell((short) 6);
                    //定义单元格为字符类型，也可以指定为日期类型、数字类型
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle1); // 为单元格设置格式
                    cell.setCellValue(nameOfClass); // 添加内容至单元格
                    rowNum++;
                }
            } else {
                row = sheet.createRow((short) rowNum);                                        // 在索引rowNum的位置创建行
                cell = row.createCell((short) 0);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(rowNum); // 添加内容至单元格

                cell = row.createCell((short) 1);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(compDataList.get(jj).getName()); // 添加内容至单元格

                cell = row.createCell((short) 2);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(""); // 添加内容至单元格

                cell = row.createCell((short) 3);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(""); // 添加内容至单元格

                cell = row.createCell((short) 4);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(""); // 添加内容至单元格

                cell = row.createCell((short) 5);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(""); // 添加内容至单元格

                cell = row.createCell((short) 6);
                //定义单元格为字符类型，也可以指定为日期类型、数字类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle1); // 为单元格设置格式
                cell.setCellValue(""); // 添加内容至单元格
                rowNum++;
            }
        }

        String fileName = "c:\\ExcelExamWrite.xls";
        File file = new File(fileName);// 创建excel文件对象
        FileOutputStream fOut = null;
        // 新建一输出文件流
        fOut = new FileOutputStream(file);
        // 将创建的内容写到指定的Excel文件中
        workbook.write(fOut);
        fOut.flush();
        fOut.close();// 操作结束，关闭文件
        System.out.println("Excel文件创建成功！\nExcel文件的存放路径为：" + file.getAbsolutePath());
    }

    /**
     * 查看IKAnalyzer 分词器是如何将一个完整的词组进行分词的
     *
     * @param text
     * @param isMaxWordLength
     */
    public static List<String> splitWord(String text, boolean isMaxWordLength) {
        List<String> keywords = null;
        try {
            /* 创建分词对象 */
            // 遍历分词数据
            keywords = new ArrayList<String>();
            Configuration configuration = DefaultConfig.getInstance();
            configuration.setUseSmart(true);
            StringReader reader = new StringReader(text);
            IKSegmenter ik = new IKSegmenter(reader, configuration);
            Lexeme lexeme = null;
            while ((lexeme = ik.next()) != null) {
                keywords.add(lexeme.getLexemeText());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywords;
    }


    private static List<CompClassinfo> getClassinfo(DataSource dataSource) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CompClassinfo> compClassinfos = new ArrayList<CompClassinfo>();
        CompClassinfo compClassinfo = null;

        try{
            conn = (Connection) dataSource.getConnection();
            pstmt = conn.prepareStatement("select cname,ename from TBL_COMPANYCLASS");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                compClassinfo = new CompClassinfo();
                compClassinfo.setCname(rs.getString("cname"));
                compClassinfo.setEname(rs.getString("ename"));
                compClassinfos.add(compClassinfo);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp) {

            }
        }

        return compClassinfos;
    }

    private static List<CompClassinfo> getMatchingClassinfo(List<CompClassinfo> compClassinfos,String dz_keywords) {
        List<CompClassinfo>  aa = new ArrayList<CompClassinfo>();

        String[] keywords = dz_keywords.split(",");
        for(int ii=0; ii<compClassinfos.size(); ii++) {
             CompClassinfo compClassinfo = compClassinfos.get(ii);
             String keyword  = "";
             for (int jj=0; jj<keywords.length; jj++) {
                 keyword = keyword + keywords[jj];
             }
            if (compClassinfo.getCname().indexOf(keyword)>-1) {
                if (!aa.contains(compClassinfo)) aa.add(compClassinfo);
            }
        }

        return aa;
    }
}
