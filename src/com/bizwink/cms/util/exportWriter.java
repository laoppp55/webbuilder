package com.bizwink.cms.util;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-4-5
 * Time: 9:09:46
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import com.bizwink.webapps.leaveword.Anwser;
import com.bizwink.webapps.leaveword.Word;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;


public class exportWriter {
    // 设置cell编码解决中文高位字节截断
    private static short XLS_ENCODING = HSSFCell.ENCODING_UTF_16;

    // 定制浮点数格式
    private static String NUMBER_FORMAT = "#,##0.00";

    // 定制日期格式
    private static String DATE_FORMAT = "m/d/yy"; // "m/d/yy h:mm"

    private OutputStream out = null;

    private HSSFWorkbook workbook = null;

    private HSSFSheet sheet = null;

    private HSSFRow row = null;

    public exportWriter() {

    }

    /**
     * 初始化Excel
     *
     */
    public exportWriter(OutputStream out) {
        this.out = out;
        this.workbook = new HSSFWorkbook();
        this.sheet = workbook.createSheet();
    }

    /**
     * 导出Excel文件
     *
     * @throws IOException
     */
    public void export() throws FileNotFoundException, IOException {
        try {
            workbook.write(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new IOException(" 生成导出Excel文件出错!");
        } catch (IOException e) {
            throw new IOException(" 写入Excel文件出错! ");
        }
    }

    /**
     * 增加一行
     *
     * @param index
     *            行号
     */
    public void createRow(int index) {
        this.row = this.sheet.createRow(index);
    }

    /**
     * 获取单元格的值
     *
     * @param index
     *            列号
     */
    public String getCell(int index) {
        HSSFCell cell = this.row.getCell((short) index);
        String strExcelCell = "";
        if (cell != null) { // add this condition
            // judge
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    strExcelCell = "FORMULA ";
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC: {
                    strExcelCell = String.valueOf(cell.getNumericCellValue());
                }
                break;
                case HSSFCell.CELL_TYPE_STRING:
                    strExcelCell = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    strExcelCell = "";
                    break;
                default:
                    strExcelCell = "";
                    break;
            }
        }
        return strExcelCell;
    }

    /**
     * 设置单元格
     *
     * @param index
     *            列号
     * @param value
     *            单元格填充值
     */
    public void setCell(int index, int value) {
        HSSFCell cell = this.row.createCell((short) index);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellValue(value);
    }

    /**
     * 设置单元格
     *
     * @param index
     *            列号
     * @param value
     *            单元格填充值
     */
    public void setCell(int index, double value) {
        HSSFCell cell = this.row.createCell((short) index);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellValue(value);
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
        HSSFDataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
        cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
    }

    /**
     * 设置单元格
     *
     * @param index
     *            列号
     * @param value
     *            单元格填充值
     */
    public void setCell(int index, String value) {
        HSSFCell cell = this.row.createCell((short) index);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //cell.setEncoding(XLS_ENCODING);
        cell.setCellValue(value);
    }

    /**
     * 设置单元格
     *
     * @param index
     *            列号
     * @param value
     *            单元格填充值
     */
    public void setCell(int index, Calendar value) {
        HSSFCell cell = this.row.createCell((short) index);
        //cell.setEncoding(XLS_ENCODING);
        cell.setCellValue(value.getTime());
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
        cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
    }

    public String exportListToTextFile(List list,String userid,String path) {
        String filen = path + userid + System.currentTimeMillis() + ".txt";

        return filen;
    }

    public String exportRTFContext(List list,String userid,String sitename,String path)throws DocumentException,IOException
    {
        path =  path + java.io.File.separator +  "sites" + java.io.File.separator + sitename + java.io.File.separator + "downanwser" + java.io.File.separator + userid + java.io.File.separator;
        java.io.File fp = new java.io.File(path);
        if (!fp.exists()) fp.mkdirs();
        String filename = System.currentTimeMillis() + ".doc";
        String filen = path + filename;

        Document document = new Document(PageSize.A4);
        RtfWriter2.getInstance(document, new FileOutputStream(filen));
        document.open();

        //设置中文字体
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //标题字体风格
        Font titleFont= new Font(bfChinese, 12, Font.BOLD);

        //正文字体风格
        Font contextFont= new Font(bfChinese, 10, Font.NORMAL);
        Paragraph title = new Paragraph("北京无线电管理局网站用户留言");
        //设置标题格式对齐方式
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(titleFont);
        document.add(title);

        for(int i=0; i<list.size(); i++) {
            Word word = (Word)list.get(i);

            String ititle = word.getTitle();
            Paragraph context = new Paragraph("问题标题：" + ititle);
            Font ititleFont= new Font(bfChinese, 10, Font.BOLD);
            //正文格式左对齐
            context.setAlignment(Element.ALIGN_LEFT);
            context.setFont(ititleFont);
            //离上一段落（标题）空的行数
            context.setSpacingBefore(20);
            //设置第一行空的列数
            context.setFirstLineIndent(20);
            document.add(context);

            String contextString = word.getContent();
            context = new Paragraph("    问题内容：" + contextString);
            //正文格式左对齐
            context.setAlignment(Element.ALIGN_LEFT);
            context.setFont(contextFont);
            //离上一段落（标题）空的行数
            context.setSpacingBefore(20);
            //设置第一行空的列数
            context.setFirstLineIndent(20);
            document.add(context);

            String retcontent = "";
            List anwsers = word.getAnwserfordept();
            System.out.println("export writer size=" + anwsers.size());
            if (anwsers.size() > 0) {
                Anwser anwser = new Anwser();
                anwser = (Anwser)anwsers.get(0);
                context = new Paragraph("    问题答案：" + anwser.getContent() + "（" + anwser.getRetdate().toString() + "）"  + "  " + anwser.getProcessor());
                //正文格式左对齐
                context.setAlignment(Element.ALIGN_LEFT);
                context.setFont(contextFont);
                //离上一段落（标题）空的行数
                context.setSpacingBefore(20);
                //设置第一行空的列数
                context.setFirstLineIndent(20);
                document.add(context);
                for(int j=1; j<anwsers.size(); j++) {
                    anwser = new Anwser();
                    anwser = (Anwser)anwsers.get(j);
                    retcontent = retcontent + anwser.getProcessor() + "：" + anwser.getContent() + "（" + anwser.getRetdate().toString() + "）";
                    context = new Paragraph("              " + retcontent);
                    //正文格式左对齐
                    context.setAlignment(Element.ALIGN_LEFT);
                    context.setFont(contextFont);
                    //离上一段落（标题）空的行数
                    context.setSpacingBefore(20);
                    //设置第一行空的列数
                    context.setFirstLineIndent(20);
                    document.add(context);
                }
            }
        }

        //在表格末尾添加图片
        // Image png=Image.getInstance("d:\\duck.jpg");
        // document.add(png);
        document.close();

        return filename;
    }

    public String exportRTFContextForLWManager(List list,String userid,String sitename,String path)throws DocumentException,IOException
    {
        path =  path + java.io.File.separator +  "sites" + java.io.File.separator + sitename + java.io.File.separator + "downanwser" + java.io.File.separator + userid + java.io.File.separator;
        java.io.File fp = new java.io.File(path);
        if (!fp.exists()) fp.mkdirs();
        String filename = System.currentTimeMillis() + ".doc";
        String filen = path + filename;

        Document document = new Document(PageSize.A4);
        RtfWriter2.getInstance(document, new FileOutputStream(filen));
        document.open();

        //设置中文字体
        BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //标题字体风格
        Font titleFont= new Font(bfChinese, 12, Font.BOLD);

        //正文字体风格
        Font contextFont= new Font(bfChinese, 10, Font.NORMAL);
        Paragraph title = new Paragraph("北京无线电管理局网站用户留言");
        //设置标题格式对齐方式
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(titleFont);
        document.add(title);

        for(int i=0; i<list.size(); i++) {
            Word word = (Word)list.get(i);

            String ititle = word.getTitle();
            Paragraph context = new Paragraph("问题标题：" + ititle);
            Font ititleFont= new Font(bfChinese, 10, Font.BOLD);
            //正文格式左对齐
            context.setAlignment(Element.ALIGN_LEFT);
            context.setFont(ititleFont);
            //离上一段落（标题）空的行数
            context.setSpacingBefore(20);
            //设置第一行空的列数
            context.setFirstLineIndent(20);
            document.add(context);

            String contextString = word.getContent();
            context = new Paragraph("    问题内容：" + contextString);
            //正文格式左对齐
            context.setAlignment(Element.ALIGN_LEFT);
            context.setFont(contextFont);
            //离上一段落（标题）空的行数
            context.setSpacingBefore(20);
            //设置第一行空的列数
            context.setFirstLineIndent(20);
            document.add(context);

            String retcontent = word.getRetcontent();
            context = new Paragraph("    问题答案：" + retcontent + "：" + word.getUserid() + "（" + word.getEndtouser().toString() + "）");
            //正文格式左对齐
            context.setAlignment(Element.ALIGN_LEFT);
            context.setFont(contextFont);
            //离上一段落（标题）空的行数
            context.setSpacingBefore(20);
            //设置第一行空的列数
            context.setFirstLineIndent(20);
            document.add(context);
        }

        //在表格末尾添加图片
        // Image png=Image.getInstance("d:\\duck.jpg");
        // document.add(png);
        document.close();

        return filename;
    }

    public boolean exportData(List datas,String path) {
        boolean success = false;
        System.out.println("file path=" + path);
        System.out.println(" 开始导出Excel文件 ");

        File f = new File(path + "qt.xls");
        exportWriter e = new exportWriter();
        try {
            e = new exportWriter(new FileOutputStream(f));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        e.createRow(0);
        e.setCell(0, "序号");
        e.setCell(1, "企业名称");
        e.setCell(2, "项目名称");
        e.setCell(3, "组织单位名");
        e.setCell(4, "区县科技主管部门");
        e.setCell(5, "是否留学人员创办企业");
        e.setCell(6, "是否软件类项目");
        e.setCell(7, "是否高技术服务业");
        e.setCell(8, "是否大学生创业专项");
        e.setCell(9, "是否技术转移类");
        e.setCell(10, "起始时间");
        e.setCell(11, "完成时间");
        e.setCell(12, "所属领域");
        e.setCell(13, "员工总数");
        e.setCell(14, "缴税(万元)");
        e.setCell(15, "创汇(万元)");
        e.setCell(16, "上年研究开发经费(万元)");
        e.setCell(17, "生产种类");
        e.setCell(18, "项目所涉及知识产权归属情况");
        e.setCell(19, "技术来源单位名称");
        e.setCell(20, "产权归属及获得方式");
        e.setCell(21, "科技计划项目类别");
        e.setCell(22, "本技术来自的项目名称");
        e.setCell(23, "企业获得质量认证体系证书");
        e.setCell(24, "项目获得国家相关行业许可证");
        e.setCell(25, "项目获得专利证书");
        e.setCell(26, "项目获得技术产品鉴定证书");
        e.setCell(27, "项目所处阶段");
        e.setCell(28, "项目产品销售情况");
        e.setCell(29, "产品形态");
        e.setCell(30, "因本项目新增就业人数");
        e.setCell(31, "本项目产品累计净利润(万元)");
        e.setCell(32, "本项目产品累计销售收入(万元)");
        e.setCell(33, "本项目产品累计缴税总额(万元)");
        e.setCell(34, "生产方式");
        e.setCell(35, "项目完成时所处阶段");
        e.setCell(36, "项目产品销售情况");
        e.setCell(37, "产品化拟执行的质量标准类型");
        e.setCell(38, "近期项目完成的投资金额(万元)");
        e.setCell(39, "固定资产投资(万元)");
        e.setCell(40, "流动资金投资(万元)");
        e.setCell(41, "企业自筹(万元)");
        e.setCell(42, "银行贷款(万元)");
        e.setCell(43, "财政拨款(万元)");
        e.setCell(44, "其中地方政府资助(万元)");
        e.setCell(45, "其中国家创新基金资助(万元)");
        e.setCell(46, "企业资产规模(万元)");
        e.setCell(47, "企业年销售收入(万元)");
        e.setCell(48, "企业人员总数");

        /*for (int i=0; i<datas.size(); i++) {
            Prj p = (Prj) datas.get(i);
            e.createRow(i + 1);
            e.setCell(0, i);
            e.setCell(1, p.getEpr_name() == null ? "":p.getEpr_name());
            e.setCell(2, p.getPrj_name() == null ? "":p.getPrj_name());
            e.setCell(3, p.getOrname() == null ? "":p.getOrname());
            e.setCell(4, p.getTo_name() == null ? "":p.getTo_name());
            e.setCell(5, p.getIs_liuxue() == null ? "":p.getIs_liuxue());
            e.setCell(6, p.getIs_software() == null ? "":p.getIs_software());
            e.setCell(7, p.getIs_gjsfw() == null ? "":p.getIs_gjsfw());
            e.setCell(8, "");
            e.setCell(9, "");
            e.setCell(10, p.getPrj_begin_time() == null?"":p.getPrj_begin_time().toString());
            e.setCell(11, p.getPrj_end_time() == null ? "" : p.getPrj_end_time().toString());
            e.setCell(12, p.getPrj_domain1() + p.getPrj_domain2() + p.getPrj_domain3());
            e.setCell(13, p.getWorkers());
            e.setCell(14, p.getFin_pay_tax());
            e.setCell(15, p.getFin_foreign_income());
            e.setCell(16, p.getEpri_payout_lastyear());
            e.setCell(17, "");
            e.setCell(18, "");
            e.setCell(19, p.getPrj_intellectual_property());
            e.setCell(20, p.getTs_project_name()!=null?p.getTs_project_name():"");
            e.setCell(21, "");
            e.setCell(22, p.getLicence()!=null?p.getLicence():"");
            e.setCell(23, p.getAccept()!=null?p.getAccept():"");
            e.setCell(24, p.getPatent()!=null?p.getPatent():"");
            e.setCell(25, p.getJudge()!=null?p.getJudge():"");
            e.setCell(26, p.getPpap_phase());
            e.setCell(27, p.getPpap_sal());
            e.setCell(28, p.getPpc_modal());
            e.setCell(29, p.getFin_new_worker());
            e.setCell(30, p.getFin_net_profit());
            e.setCell(31, p.getFin_sale_income());
            e.setCell(32, p.getFin_pay_tax());
            e.setCell(33, "");
            e.setCell(34, "");
            e.setCell(35, p.getPpap_phase());
            e.setCell(36, p.getPpap_sal());
            e.setCell(37, p.getQualitystandard()!=null?p.getQualitystandard():"");
            e.setCell(38, "近期项目完成的投资金额(万元)");
            e.setCell(39, "固定资产投资(万元)");
            e.setCell(40, "流动资金投资(万元)");
            e.setCell(41, p.getFin_epr_selfinvest());
            e.setCell(42, p.getFin_bank_loan());
            e.setCell(43, "");
            e.setCell(44, p.getFin_local_innofund());
            e.setCell(45, p.getFin_nation_innofund());
            e.setCell(46, p.getEf_asset());
            e.setCell(47, p.getEf_income());
            e.setCell(48, p.getWorkers());
        }*/

        try {
            e.export();
            success = true;
            System.out.println(" 导出Excel文件[成功] ");
        } catch (IOException ex) {
            System.out.println(" 导出Excel文件[失败] ");
            ex.printStackTrace();
        }

        return success;
    }

    public String toUtf8String(String s)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255)
            {
                sb.append(c);
            }
            else
            {
                byte[] b;
                try
                {
                    b = Character.toString(c).getBytes("utf-8");
                }
                catch (Exception ex)
                {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++)
                {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /*public static void main(String[] args) {
        System.out.println(" 开始导出Excel文件 ");

        File f = new File("C:\\qt.xls");
        excelWriter e = new excelWriter();

        try {
            e = new excelWriter(new FileOutputStream(f));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        e.createRow(0);
        e.setCell(0, "试题编码 ");
        e.setCell(1, "题型");
        e.setCell(2, "分值");
        e.setCell(3, "难度");
        e.setCell(4, "级别");
        e.setCell(5, "知识点");

        e.createRow(1);
        e.setCell(0, "t1");
        e.setCell(1, 1);
        e.setCell(2, 3.0);
        e.setCell(3, 1);
        e.setCell(4, "重要");
        e.setCell(5, "专业");

        try {
            e.export();
            System.out.println(" 导出Excel文件[成功] ");
        } catch (IOException ex) {
            System.out.println(" 导出Excel文件[失败] ");
            ex.printStackTrace();
        }
    }*/
}
