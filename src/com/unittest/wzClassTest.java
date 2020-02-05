package com.unittest;

import com.bizwink.cms.tree.BizTreeManager;
import com.bizwink.cms.tree.ClassTree;
import com.bizwink.cms.tree.snode;
import com.bizwink.persistence.CmClassMapper;
import com.bizwink.po.CmClass;
import com.bizwink.po.WzClass;
import com.bizwink.service.MaterialcodeService;
import com.bizwink.util.DeReplication;
import com.bizwink.util.GeneralMethod;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 17-3-11.
 */
public class wzClassTest {
    public static ClassTree classTree;
    private static DataSource connPool;
    /**
     *
     * @Title: getWeebWork
     * @Description: TODO(根据传入的文件名获取工作簿对象(Workbook))
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWeebWork(String filename) throws IOException{
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

    //获取物资分类（种类）的供应商列表
    public static void getSuppListByMidclass(String classid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int rownum = 0;
        int i = 0;
        int k = 0;
        String treeRoot;
        String parentID;
        int nodenum = 0;
        int ordernum;

        try{
            List<String> supps = new ArrayList<String>();
            conn = connPool.getConnection();
            pstmt = conn.prepareStatement("select distinct t.c_supplier from t_suppprodclass t where t.c_strclasscode like '"+ classid + "%'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                supps.add(String.valueOf(rs.getInt(1)));
                System.out.println(String.valueOf(rs.getInt(1)));
            }
            rs.close();
            pstmt.close();

            FileOutputStream out=new FileOutputStream(new File("C:\\Document\\长城产品\\供应商门户\\" + classid + ".csv"),true); //如果追加方式用true
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:\\Document\\长城产品\\供应商门户\\supplier.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            int  numSheets = 0;

            HSSFSheet aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                int linenum = 0;
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    HSSFRow aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    String tbuf = "";
                    String cname = "";
                    String weburl = "";
                    String email = "";
                    boolean flag = false;
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

                        //企业的编码
                        if (cellNumOfRow == 0) {
                            for (int ii=0; ii<supps.size();ii++) {
                                String suppcode = supps.get(ii).trim();
                                if (suppcode.equals(buf)) {
                                    tbuf = buf;
                                    flag = true;
                                }
                            }
                        }

                        //企业名字
                        if (cellNumOfRow == 3) {
                            if (flag==true)
                                tbuf = tbuf + "," + buf;
                        }

                        //企业的WEBURL
                        if (cellNumOfRow == 9) {
                            if (flag==true)
                                tbuf = tbuf + "," + buf;
                        }

                        //企业的电子邮件地址
                        if (cellNumOfRow == 10) {
                            if (flag==true)
                                tbuf = tbuf + "," + buf + "\r\n";
                        }
                    }

                    if (flag == true) {
                        out.write(tbuf.toString().getBytes("gbk"));//注意需要转换对应的字符集
                    }
                }
                out.close();
            }
        } catch (IOException exp) {
        } catch (SQLException sqlexp){
        } finally {
            try {
                if (rs!=null) rs.close();
                if (pstmt!=null) pstmt.close();
                conn.close();
            } catch (SQLException exp1) {}
        }
    }

    //获取供应商官方网站地址或者电子邮件地址
    public static void getSuppliersWebAddress() {
        String str = null;
        List<String> qiye = new ArrayList<String>();
        try{
            FileReader in=new FileReader(new File("C:\\Document\\长城产品\\供应商门户\\online_supplier1.csv")); //如果追加方式用true
            BufferedReader br = new BufferedReader(in);
            while((str = br.readLine()) != null) {
                qiye.add(str);
            }

            FileOutputStream out=new FileOutputStream(new File("C:\\Document\\长城产品\\供应商门户\\online_suppliers.csv"),true); //如果追加方式用true
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:\\Document\\长城产品\\供应商门户\\supplier.xls"));
            HSSFWorkbook workbook = new HSSFWorkbook(fs);
            int  numSheets = 0;

            HSSFSheet aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int ii = 0; ii<qiye.size(); ii++) {
                    String qiye_name = qiye.get(ii);
                    for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                        HSSFRow aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                        boolean flag = false;
                        String weburl = null;
                        String email = null;
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

                            //企业名字
                            if (cellNumOfRow == 3) {
                                //System.out.println(qiye_name + "==" + buf);
                                if (buf.equalsIgnoreCase(qiye_name))
                                    flag = true;
                            }

                            //企业的电子邮件地址
                            if (cellNumOfRow == 9) {
                                if (flag==true) {
                                    email = buf;
                                }
                            }

                            //企业的WEBURL
                            if (cellNumOfRow == 10) {
                                if (flag==true)
                                    weburl = buf;
                            }
                        }

                        if (flag == true) {
                            out.write((qiye_name + "," + weburl + "," + email + "\r\n").getBytes("gbk"));//注意需要转换对应的字符集
                            break;
                        }
                    }
                }
                out.close();
            }
        } catch (IOException exp) {
        }
    }

    //获取物资分类（种类）的供应商列表
    public static int writeTemplate(List<String> items) {
        int  numSheets = 0;
        String excelFilename = "C:\\Document\\长城产品\\通用物资采购电商平台\\分类模板.xls";
        try{
            Workbook workbook = getWeebWork(excelFilename);
            Sheet aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            Row row=aSheet.getRow(0);
            if (aSheet != null) {
                for (int ii = 0; ii<items.size(); ii++) {
                    String tbuf = items.get(ii);
                    String[] tt_item = tbuf.split(",");
                    row=aSheet.createRow((short)(aSheet.getLastRowNum()+1)); //在现有行号后追加数据
                    row.createCell(0).setCellValue(tt_item[1]); //设置第一个（从0开始）单元格的数据
                    row.createCell(1).setCellValue(tt_item[0]); //设置第二个（从0开始）单元格的数据
                    if (tt_item[0].length() == 2) {
                        row.createCell(2).setCellValue(""); //设置第二个（从0开始）单元格的数据
                    } else if (tt_item[0].length() == 4) {
                        row.createCell(2).setCellValue(tt_item[0].substring(0,2)); //设置第二个（从0开始）单元格的数据
                    } else if (tt_item[0].length() == 6) {
                        row.createCell(2).setCellValue(tt_item[0].substring(0,4)); //设置第二个（从0开始）单元格的数据
                    } else if (tt_item[0].length() == 8) {
                        row.createCell(2).setCellValue(tt_item[0].substring(0,6)); //设置第二个（从0开始）单元格的数据
                    }
                    // if (!tt_item[2].isEmpty() && tt_item[2]!="null" && tt_item[2] != null && tt_item[2]!="") {
                    if (tt_item[2].indexOf("null")>-1) {
                        row.createCell(3).setCellValue("");
                    } else
                        row.createCell(3).setCellValue(tt_item[2]); //设置第二个（从0开始）单元格的数据
                    //} else
                    //    row.createCell(3).setCellValue(""); //设置第二个（从0开始）单元格的数据
                }

                FileOutputStream out=new FileOutputStream(excelFilename);
                out.flush();
                workbook.write(out);
                out.close();
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        return 0;
    }

    //获取物资分类（种类）的供应商列表
    public static int readTemplate(String excelFilename) {
        int  numSheets = 0;
        Sheet aSheet = null;
        try {
            Workbook workbook = getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    boolean flag = false;
                    String weburl = null;
                    String email = null;
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
                    }
                }
            }
        } catch (IOException exp) {
        }


        return 0;
    }

    //从EXCEL文件中获取物资分类列表信息
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

                        //物资父类编码
                        if (cellNumOfRow == 0) {
                            thenode.setLinkPointer(buf.trim());
                        }

                        //物资编码
                        if (cellNumOfRow == 1) {
                            thenode.setId(buf.trim());
                        }

                        //物资名称
                        if (cellNumOfRow == 2) {
                            //System.out.println(buf);
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
                            thenode.setKeywordnote(buf.trim());
                        }
                    }
                    //           System.out.println(thenode.getId() + "===" + thenode.getChName());

                    messages.add(thenode);
                }

                //messages列表数据进行逆序排序
                //List messages_bysort = orderItems(messages, "desc");
                //ClassTree in_tree = getClassesTreeByNodes(messages_bysort);
            }
        } catch (IOException exp) {
        }

        return GeneralMethod.orderItems(messages, "desc");
    }

    public static List getInfolistFromDB(int customerid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List messages = new ArrayList();
        snode node = null;

        try{
            conn = connPool.getConnection();
            //pstmt = conn.prepareStatement("select * from t_prodclass t order by t.C_STRCLASSCODE desc");
            pstmt = conn.prepareStatement("select t.*, t.rowid from tbl_wzclass t where t.customerid=? and t.delflag=? order by t.orderid desc");
            pstmt.setInt(1,customerid);
            pstmt.setInt(2,0);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                node = new snode();
                node.setId(rs.getString("CODE"));
                if (rs.getString("PCODE") == null)
                    node.setLinkPointer("");
                else
                    node.setLinkPointer(rs.getString("PCODE"));
                node.setChName(rs.getString("NAME"));
                node.setUnit(rs.getString("UNIT"));
                node.setOrderNum(rs.getInt("ORDERID"));
                node.setDesc(rs.getString("BRIEF"));
                node.setHasArticleModel(rs.getInt("VALID"));
                messages.add(node);
            }
            rs.close();
            pstmt.close();

        } catch (SQLException sqlexp){
            sqlexp.printStackTrace();
        } finally {
            try {
                if (rs!=null) rs.close();
                if (pstmt!=null) pstmt.close();
                conn.close();
            } catch (SQLException exp1) {}
        }

        return messages;
    }

    public static DeReplication deReplication(List messages) {
        DeReplication drresult = new DeReplication();
        List result = new ArrayList();
        List<String> ids = new ArrayList<String>();
        List errors = new ArrayList();
        Iterator it=messages.iterator();
        while(it.hasNext()){
            snode a=(snode)it.next();
            if(ids.contains(a.getId())){
                it.remove();
                a.setAudited(2);        //EXCEL文件中重复的节点
                errors.add(a);
            } else{
                if (a.getLinkPointer()!=null && a.getLinkPointer()!="")
                    if (a.getId().startsWith(a.getLinkPointer())) {
                        if (a.getChName() != null && a.getChName() != "") {
                            if (a.getChName().length()<200) {
                                if(a.getId().length()<16) {
                                    if (a.getLinkPointer().length()<20) {
                                        result.add(a);
                                        ids.add(a.getId());
                                    } else {
                                        it.remove();
                                        a.setAudited(7);         //本节点分类编码的父类编码超长
                                        errors.add(a);
                                    }
                                } else {
                                    it.remove();
                                    a.setAudited(6);         //本节点分类编码超长
                                    errors.add(a);
                                }
                            } else {
                                it.remove();
                                a.setAudited(5);         //本节点中文名称超长
                                errors.add(a);
                            }
                        } else {
                            it.remove();
                            a.setAudited(4);         //本节点中文名称为空
                            errors.add(a);
                        }
                    } else {                       //本节点的父节点ID错误
                        it.remove();
                        a.setAudited(3);
                        errors.add(a);
                    }
                else {                            //第一层节点
                    result.add(a);
                    ids.add(a.getId());
                }
            }
        }

        drresult.setErrors(errors);
        drresult.setMessages(result);

        return drresult;
    }


    private static String[] getFieldCodes(String wzflcodes) {
        String[] ff = new String[5];
        if (!wzflcodes.isEmpty()) {
            ff[0] = wzflcodes.substring(0,2);

            if (wzflcodes.length()>=4)
                ff[1] = wzflcodes.substring(2,4);

            if (wzflcodes.length()>=6)
                ff[2] = wzflcodes.substring(4,6);

            if (wzflcodes.length()>=8)
                ff[3] = wzflcodes.substring(6,8);

            if (wzflcodes.length()>=10)
                ff[4] = wzflcodes.substring(8,10);
        }
        return ff;
    }

    public static void impClsInfo(MaterialcodeService mcodeservice,String infoFile,String code,String name) {
        List messages_bysort = getInfolistFromExcel(infoFile);
        //处理一级分类
        WzClass cmClass = new WzClass();
        List<WzClass> cls_list = new ArrayList<WzClass>();
        cmClass.setCODE(code);
        cmClass.setNAME(name);
        cmClass.setPCODE("");
        // cmClass.setUNIT(p.getUnit());
        // cmClass.setBRIEF(p.getDesc());
        cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
        cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
        cmClass.setCREATECOMP(BigDecimal.valueOf(11));
        cmClass.setCREATOR("123l");
        cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
        cmClass.setEDITOR("123l");
        cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
        mcodeservice.insertWzClassInfo(cmClass);


        //处理二级分类
        for(int ii=0; ii<messages_bysort.size(); ii++) {
            snode node = (snode)messages_bysort.get(ii);
            String[] wz_field_codes = getFieldCodes(node.getLinkPointer());
            if(cls_list.size()==0) {
                cmClass = new WzClass();
                cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1]);
                if (node.getChName()!=null)
                    cmClass.setNAME(node.getChName());
                else
                    cmClass.setNAME("the name");
                cmClass.setPCODE(wz_field_codes[0]);
                cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                cmClass.setCREATOR("123l");
                cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                cmClass.setEDITOR("123l");
                cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                cls_list.add(cmClass);
            } else {
                boolean wzcls_existflag = false;
                for (int jj=0;jj<cls_list.size();jj++) {
                    WzClass t_cmClass = cls_list.get(jj);
                    if (node.getLinkPointer().startsWith(t_cmClass.getCODE())) {
                        wzcls_existflag = true;
                        break;
                    }
                }
                //如果改行在临时缓存cls_list中不存在，将改行信息保存到该临时缓存中
                if (wzcls_existflag == false) {
                    cmClass = new WzClass();
                    cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1]);
                    if (node.getChName()!=null)
                        cmClass.setNAME(node.getChName());
                    else
                        cmClass.setNAME("the name");
                    cmClass.setPCODE(wz_field_codes[0]);
                    cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                    cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                    cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                    cmClass.setCREATOR("123l");
                    cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                    cmClass.setEDITOR("123l");
                    cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                    cls_list.add(cmClass);
                }
            }
        }

        //将第二级分类信息保存到数据库
        for(int ii=0;ii<cls_list.size();ii++) {
            cmClass = cls_list.get(ii);
            System.out.println(cmClass.getCODE() + "==" + cmClass.getPCODE() + "==" +cmClass.getNAME());
            if (!cmClass.getNAME().isEmpty() && !cmClass.getCODE().isEmpty())
                mcodeservice.insertWzClassInfo(cmClass);
        }


        //处理三级分类
        cls_list = new ArrayList<WzClass>();
        for(int ii=0; ii<messages_bysort.size(); ii++) {
            snode node = (snode)messages_bysort.get(ii);
            String[] wz_field_codes = getFieldCodes(node.getLinkPointer());
            if(cls_list.size()==0) {
                cmClass = new WzClass();
                cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2]);
                if (node.getUnit()!=null)
                    cmClass.setNAME(node.getUnit());
                else
                    cmClass.setNAME("the name");
                cmClass.setPCODE(wz_field_codes[0] + wz_field_codes[1]);
                cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                cmClass.setCREATOR("123l");
                cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                cmClass.setEDITOR("123l");
                cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                cls_list.add(cmClass);
            } else {
                boolean wzcls_existflag = false;
                for (int jj=0;jj<cls_list.size();jj++) {
                    WzClass t_cmClass = cls_list.get(jj);
                    if (node.getLinkPointer().startsWith(t_cmClass.getCODE())) {
                        wzcls_existflag = true;
                        break;
                    }
                }
                //如果改行在临时缓存cls_list中不存在，将改行信息保存到该临时缓存中
                if (wzcls_existflag == false) {
                    cmClass = new WzClass();
                    cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2]);
                    if (node.getUnit()!=null)
                        cmClass.setNAME(node.getUnit());
                    else
                        cmClass.setNAME("the name");
                    cmClass.setPCODE(wz_field_codes[0] + wz_field_codes[1]);
                    cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                    cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                    cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                    cmClass.setCREATOR("123l");
                    cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                    cmClass.setEDITOR("123l");
                    cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                    cls_list.add(cmClass);
                }
            }
        }
        //将第三级分类信息保存到数据库
        for(int ii=0;ii<cls_list.size();ii++) {
            cmClass = cls_list.get(ii);
            System.out.println(cmClass.getCODE() + "==" + cmClass.getPCODE() + "==" +cmClass.getNAME());
            if (!cmClass.getNAME().isEmpty() && !cmClass.getCODE().isEmpty())
                mcodeservice.insertWzClassInfo(cmClass);
        }

        //处理四级分类
        cls_list = new ArrayList<WzClass>();
        for(int ii=0; ii<messages_bysort.size(); ii++) {
            snode node = (snode)messages_bysort.get(ii);
            if(node.getLinkPointer().length()>=8) {
                String[] wz_field_codes = getFieldCodes(node.getLinkPointer());
                if(cls_list.size()==0) {
                    cmClass = new WzClass();
                    cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2] + wz_field_codes[3]);
                    if (node.getDesc()!=null)
                        cmClass.setNAME(node.getDesc());
                    else
                        cmClass.setNAME("the name");
                    cmClass.setPCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2]);
                    cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                    cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                    cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                    cmClass.setCREATOR("123l");
                    cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                    cmClass.setEDITOR("123l");
                    cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                    cls_list.add(cmClass);
                } else {
                    boolean wzcls_existflag = false;
                    for (int jj=0;jj<cls_list.size();jj++) {
                        WzClass t_cmClass = cls_list.get(jj);
                        if (node.getLinkPointer().startsWith(t_cmClass.getCODE())) {
                            wzcls_existflag = true;
                            break;
                        }
                    }
                    //如果改行在临时缓存cls_list中不存在，将改行信息保存到该临时缓存中
                    if (wzcls_existflag == false) {
                        cmClass = new WzClass();
                        cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2] + wz_field_codes[3]);
                        if (node.getDesc()!=null)
                            cmClass.setNAME(node.getDesc());
                        else
                            cmClass.setNAME("the name");
                        cmClass.setPCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2]);
                        cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                        cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                        cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                        cmClass.setCREATOR("123l");
                        cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                        cmClass.setEDITOR("123l");
                        cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                        cls_list.add(cmClass);
                    }
                }
            }
        }
        //将第四级分类信息保存到数据库
        for(int ii=0;ii<cls_list.size();ii++) {
            cmClass = cls_list.get(ii);
            System.out.println(cmClass.getCODE() + "==" + cmClass.getPCODE() + "==" +cmClass.getNAME());
            if (!cmClass.getNAME().isEmpty() && !cmClass.getCODE().isEmpty())
                mcodeservice.insertWzClassInfo(cmClass);
        }

        //处理五级分类
        cls_list = new ArrayList<WzClass>();
        for(int ii=0; ii<messages_bysort.size(); ii++) {
            snode node = (snode)messages_bysort.get(ii);
            if(node.getLinkPointer().length()>=10) {
                String[] wz_field_codes = getFieldCodes(node.getLinkPointer());
                if(cls_list.size()==0) {
                    cmClass = new WzClass();
                    cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2] + wz_field_codes[3] + wz_field_codes[4]);
                    if(node.getKeyword()!=null)
                        cmClass.setNAME(node.getKeyword());
                    else
                        cmClass.setNAME("the name");
                    cmClass.setPCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2] + wz_field_codes[3]);
                    cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                    cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                    cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                    cmClass.setCREATOR("123l");
                    cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                    cmClass.setEDITOR("123l");
                    cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                    cls_list.add(cmClass);
                } else {
                    boolean wzcls_existflag = false;
                    for (int jj=0;jj<cls_list.size();jj++) {
                        WzClass t_cmClass = cls_list.get(jj);
                        if (node.getLinkPointer().startsWith(t_cmClass.getCODE())) {
                            wzcls_existflag = true;
                            break;
                        }
                    }
                    //如果改行在临时缓存cls_list中不存在，将改行信息保存到该临时缓存中
                    if (wzcls_existflag == false) {
                        cmClass = new WzClass();
                        cmClass.setCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2] + wz_field_codes[3] + wz_field_codes[4]);
                        if(node.getKeyword()!=null)
                            cmClass.setNAME(node.getKeyword());
                        else
                            cmClass.setNAME("the name");
                        cmClass.setPCODE(wz_field_codes[0] + wz_field_codes[1] + wz_field_codes[2] + wz_field_codes[3]);
                        cmClass.setCUSTOMERID(BigDecimal.valueOf(0));
                        cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                        cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                        cmClass.setCREATOR("123l");
                        cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                        cmClass.setEDITOR("123l");
                        cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                        cls_list.add(cmClass);
                    }
                }
            }
        }
        //将第五级分类信息保存到数据库
        for(int ii=0;ii<cls_list.size();ii++) {
            cmClass = cls_list.get(ii);
            System.out.println(cmClass.getCODE() + "==" + cmClass.getPCODE() + "==" +cmClass.getNAME());
            if (!cmClass.getNAME().isEmpty() && !cmClass.getCODE().isEmpty())
                mcodeservice.insertWzClassInfo(cmClass);
        }
    }

    /**
     * 获取字符串拼音的第一个字母
     * @param chinese
     * @return
     */
    public static String ToFirstChar(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();  //转为单个字符
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    /**
     * 汉字转为拼音
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    //从EXCEL文件中获取物资分类列表信息
    public static List getClsAttrs(String excelFilename) {
        int  numSheets = 0;
        Sheet aSheet = null;
        List messages = new ArrayList();
        try {
            Workbook workbook = getWeebWork(excelFilename);
            aSheet = workbook.getSheetAt(numSheets);//获得一个sheet
            if (aSheet != null) {
                for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet.getLastRowNum(); rowNumOfSheet++) {
                    Row aRow = aSheet.getRow(rowNumOfSheet); //获得一个行
                    ClsAttrs attr = new ClsAttrs();
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

                        //小类名称
                        if (cellNumOfRow == 0) {
                            attr.setClsname(buf.trim());
                        }

                        //模板名称
                        if (cellNumOfRow == 1) {
                            attr.setTemplatename(buf.trim());
                        }

                        //特征量序号
                        if (cellNumOfRow == 2) {
                            attr.setOrderno(Integer.parseInt(buf.trim()));
                        }

                        //特征量名称
                        if (cellNumOfRow == 3) {
                            attr.setTzname(buf.trim());
                        }

                        //前置符号
                        if (cellNumOfRow == 4) {
                            attr.setPrefix(buf.trim());
                        }

                        //前置是否可以为空
                        if (cellNumOfRow == 5) {
                            attr.setPrefixallownull(buf.trim());
                        }

                        //特征量是否必填
                        if (cellNumOfRow == 6) {
                            attr.setNeedflag(buf.trim());
                        }
                    }
                    //           System.out.println(thenode.getId() + "===" + thenode.getChName());

                    messages.add(attr);
                }

                //messages列表数据进行逆序排序
                //List messages_bysort = orderItems(messages, "desc");
                //ClassTree in_tree = getClassesTreeByNodes(messages_bysort);
            }
        } catch (IOException exp) {
        }

        return GeneralMethod.orderItems(messages, "desc");
    }

    public static void main(String[] args) {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
       // connPool = (DataSource) ctx.getBean("myDataSource");
        //MaterialcodeService mcodeservice = (MaterialcodeService)ctx.getBean("materialcodeService");

        //getSuppliersWebAddress();                                   //获取供应商的WEB地址信息列表
        //getSuppListByMidclass("2319");                             //获取某个中类的供应商列表
        //String excelFilename = "C:\\Document\\长城产品\\通用物资采购电商平台\\物资编码\\长城物资分类编码\\01能源矿产427\\整理参数类目汇总表\\0-100\\01一号无烟煤.xlsx";
        //List<snode> errors = new ArrayList<snode>();
        //readTemplate(excelFilename);
        String pinyin = ToPinyin("宋向前");
        System.out.println("pinyin===" + pinyin);
     /*   long currentTime = System.currentTimeMillis();
        List messageFromDB = getInfolistFromDB(11);
        classTree = BizTreeManager.getClassesTreeByNodes(messageFromDB);
        //List<String> items =classTree.makeTreeTable("xx/xx/xx");
        //int retcode = writeTemplate(items);
        List messages_bysort = getInfolistFromExcel(excelFilename);

        //发现编码重复的记录、中文名超长、中文名称为空、编码超长、父编码超长的记录
        DeReplication result = deReplication(messages_bysort);
        errors = result.getErrors();
        List<snode> messages = result.getMessages();

        //查找在EXCEL生成的树中没有节点、在数据库也没有父节点的记录
        ClassTree in_tree = BizTreeManager.getClassesTreeByNodes(messages);
        for (int ii=0;ii<messages.size();ii++) {
            snode p = (snode)messages.get(ii);
            if (in_tree.findNodeinfoInTree(p.getId()) == null)
                if(classTree.findNodeinfoInTree(p.getLinkPointer()) == null) {
                    p.setAudited(1);
                    errors.add(p);
                }
        }

        //查找同层同名的记录及其子记录


        //查找EXCEL形成的节点树中的节点与数据库中节点树的节点是否存在重复，将重复的节点保存到errors列表中，重复节点不为错，标识为警告色
        List double_items = BizTreeManager.compareBetweenTree(classTree, in_tree);
        for(int ii=0;ii<double_items.size();ii++) {
            snode p = (snode)double_items.get(ii);
            p.setAudited(0);
            errors.add(p);
        }

        List<String> errors_ids = new ArrayList<String>();
        for(int ii=0;ii<errors.size();ii++) {
            snode p = (snode)errors.get(ii);
            errors_ids.add(p.getId());
            System.out.println(p.getId() + "==" + p.getChName() + "==" + p.getLinkPointer() + "==" + p.getAudited());
        }

        for(int ii=0;ii<messages.size();ii++) {
            snode p = (snode)messages.get(ii);
            if (!errors_ids.contains(p.getId())){
                WzClass cmClass = new WzClass();
                cmClass.setCODE(p.getId());
                cmClass.setNAME(p.getChName());
                if (p.getLinkPointer() != null)
                    cmClass.setPCODE(p.getLinkPointer());
                else
                    cmClass.setPCODE("");
                cmClass.setUNIT(p.getUnit());
                cmClass.setBRIEF(p.getDesc());
                cmClass.setCUSTOMERID(BigDecimal.valueOf(11));
                cmClass.setCREATEDATE(new Date(System.currentTimeMillis()));
                cmClass.setCREATECOMP(BigDecimal.valueOf(11));
                cmClass.setCREATOR("123l");
                cmClass.setLASTUPDATE(new Date(System.currentTimeMillis()));
                cmClass.setEDITOR("123l");
                cmClass.setUPDATECOMP(BigDecimal.valueOf(11));
                mcodeservice.insertWzClassInfo(cmClass);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - currentTime);
        System.out.println(in_tree.findNodeInTree("010116"));
        System.out.println(in_tree.isLeaf("010116"));
        */
    }
}
