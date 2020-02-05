package com.transferChinadrtvData;

import com.bizwink.cms.modelManager.Model;
import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.refers.Refers;
import com.bizwink.cms.security.Rights;
import com.bizwink.cms.security.User;
import com.bizwink.cms.util.*;
import org.apache.poi.hssf.usermodel.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

public class transferBjsjsData {
    private static Connection createConnection(String ip,String port,String dbinstance,String username,String password, int flag) {
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
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port + ":" + dbinstance, dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    private static void testDBConnection() {
        Connection s_conn = null,t_conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            int flag = 0;
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement("select * from tbl_article t where to_char(createdate,'yyyy-MM-dd')>'2019-08-17' order by t.createdate desc");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString("maintitle"));
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int retcode = 0;

        //测试数据库连接
        //testDBConnection();

        //在新版数据库创建栏目信息
        //retcode = createNewColumn();

        //更新新版网站的模板信息，只导入分站模板，不导入主站模板
        //retcode = importTemplatesForFZ();

        //导入引用文章数据
        //retcode = importReferArticles();

        //导入权限
        //retcode = importUserRights();

        //导入文章到新版网站数据库
        //retcode = importArticles();

        //修改文章内容
        //retcode = updateArticle(12795054);

        //闯红灯、红绿灯 、交通信号灯 、斑马线 、人行横道 、过街天桥 、地铁 、不文明、垃圾、垃圾分类、ETC欠费、ETC、欠费、Etc欠费、Etc、etc、etc欠费、物业欠费、物业、养犬、狗、养狗、遛狗、遛犬、犬、信访、拆迁
        //找出信用石景山栏目包含上述关键词的文章
        //List<Article> articles = getArticlesForKeywords();
        //List<Article> articles = getArticlesForIncludePicAndFile();

        //到索引号规则和索引号初始值

    }

    private static void DaoIndexInfo() {
        Connection s_conn = null, t_conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

    }

    private static List<Article> getArticlesForIncludePicAndFile() {
        Connection s_conn = null, t_conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Article article = null;
        List<Article> articleList = new ArrayList<>();
        List<Article> wtArticleList = new ArrayList<>();
        String getArticlesByColumndsSQL = "select id,columnid,dirname,maintitle,content,urltype,defineurl,summary,source,createdate from tbl_article t where t.siteid=40 and t.pubflag=0 and t.status=1 and t.columnid not in (select id from tbl_column m start with m.id=5 connect by m.parentid=prior m.id)";
        int count = 0;

        Pattern p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|doc|ppt|zip|rar|docx|pptx|pdf|xls|xlsx)\")|('[^',]*\\.(gif|jpg|jpeg|png|doc|ppt|zip|rar|docx|pptx|pdf|xls|xlsx)')",Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = null;
        String matchStr=null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuffer buf  = new StringBuffer();
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "bjsjsdbadmin";
            String s_password = "qazwsxokm";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement(getArticlesByColumndsSQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setColumnID(rs.getInt("columnid"));
                String maintitle = rs.getString("maintitle");
                String content = DBUtil.getBigString("oracle",rs,"content");
                String summary = rs.getString("summary");
                String source = rs.getString("source");
                article.setMainTitle(maintitle);
                article.setContent(content);
                article.setSummary(summary);
                article.setSource(source);
                article.setUrltype(rs.getInt("urltype"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setDirName(rs.getString("dirname"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                matcher = p.matcher(content);
                String s_content = content;
                boolean existflag = false;
                String uploadinfo = "";
                while (matcher.find()) {
                    matchStr = content.substring(matcher.start(), matcher.end());
                    uploadinfo = uploadinfo + matchStr + ",";
                    s_content = StringUtil.replace(s_content,matchStr,"");
                    existflag = true;
                }
                if (uploadinfo.length()>0) uploadinfo = uploadinfo.substring(0,uploadinfo.length()-1);
                if (existflag == true) {
                    System.out.println("有图片和附件");
                    articleList.add(article);
                    buf.append(maintitle + "==" + "http://www.bjsjs.gov.cn" + article.getDirName() + simpleDateFormat.format(article.getCreateDate()) + File.separator + article.getID() + ".shtml--"+ uploadinfo + "\r\n");
                }
                count = count + 1;
                System.out.println(count + "==" +maintitle + "");
            }
            FileUtil.writeFile(buf,"c:\\fileAndPic.txt");
            rs.close();
            pstmt.close();

            /*String web_root = "D:\\shouxin\\bjsjs_web\\bjsjs_xxw";
            String target_dir = "D:\\shouxin\\deleteFileBackup\\sjsxy";
            StringBuffer tbuf = new StringBuffer();
            for(int ii=0;ii<wtArticleList.size();ii++) {
                article = wtArticleList.get(ii);
                //处理文件，进行文件备份
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String s_dir = web_root + StringUtil.replace(article.getDirName(),"/",File.separator) + simpleDateFormat.format(article.getCreateDate()) + File.separator;
                String filedir = StringUtil.replace(article.getDirName(),"/",File.separator) + simpleDateFormat.format(article.getCreateDate()) + File.separator;
                System.out.println(article.getMainTitle());
                System.out.println(s_dir + article.getID() + ".shtml");
                if (article.getUrltype() == 0) {
                    tbuf.append(article.getMainTitle() + "==" + s_dir + article.getID() + ".shtml" + "\r\n");
                    Copy copy = new Copy();
                    copy.copyFile(s_dir + article.getID() + ".shtml",target_dir,filedir);
                    File file = new File(s_dir + article.getID() + ".shtml");
                    if (file.exists()) file.delete();
                }else
                    tbuf.append(article.getMainTitle() + "==" + article.getOtherurl() +"\r\n");
            }
            FileUtil.writeFile(tbuf,"c:\\wt.txt");

            //修改文章在数据中的状态，将之修改为未用状态
            s_conn.setAutoCommit(false);
            pstmt = s_conn.prepareStatement("update tbl_article set status=0 where id=?");
            for(int ii=0;ii<wtArticleList.size();ii++) {
                article = wtArticleList.get(ii);
                pstmt.setInt(1,article.getID());
                pstmt.executeUpdate();
            }
            pstmt.close();*/
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.commit();
                s_conn.close();
            } catch (SQLException exp) { }
        }

        return articleList;
    }

    private static void writeExcel() {
        //写EXCEL文件
        String filename="c:\\wt.txt";
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个Excel的工作表，可以指定工作表的名字
        HSSFSheet sheet = workbook.createSheet("文件信息");
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 20 * 256);
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
        cell.setCellValue("文章标题"); // 添加内容至单元格

        cell = row.createCell((short)2);
        //定义单元格为字符类型，也可以指定为日期类型、数字类型
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(cellStyle); // 为单元格设置格式
        cell.setCellValue("文章ＵＲＬ"); // 添加内容至单元格

        List<String> lines  = FileUtil.readFileByLines(filename);
        for(int ii=0; ii<lines.size(); ii++) {
            rowNum = ii + 1;
            String line = lines.get(ii);
            int posi = line.indexOf("==");
            //System.out.println(line + "====" +posi);
            String title = line.substring(0,posi);
            String url = line.substring(posi+2);
            System.out.println(title + ":" +url);

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
            cell.setCellValue(title); // 添加内容至单元格

            cell = row.createCell((short) 2);
            //定义单元格为字符类型，也可以指定为日期类型、数字类型
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(cellStyle1); // 为单元格设置格式
            cell.setCellValue(url); // 添加内容至单元格
        }

        try {
            String fileName = "c:\\wturl.xls";
            File file = new File(fileName);// 创建excel文件对象
            FileOutputStream fOut = null;
            // 新建一输出文件流
            fOut = new FileOutputStream(file);
            // 将创建的内容写到指定的Excel文件中
            workbook.write(fOut);
            fOut.flush();
            fOut.close();// 操作结束，关闭文件
            System.out.println("Excel文件创建成功！\nExcel文件的存放路径为：" + file.getAbsolutePath());
        }catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    private static List<Article> getArticlesForKeywords() {
        Connection s_conn = null, t_conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Article article = null;
        List<Article> articleList = new ArrayList<>();
        List<Article> wtArticleList = new ArrayList<>();
        String getArticlesByColumndsSQL = "select t.id,t.columnid,tc.dirname,t.maintitle,t.content,t.urltype,t.defineurl,t.summary,t.source,t.createdate from tbl_article t,tbl_column tc  where t.columnid=tc.id and t.columnid in (2861,2862,2881,2884,2885,2886,2882,2887,2901,2883,4101)";
        int count = 0;
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "bjsjsdbadmin";
            String s_password = "qazwsxokm";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement(getArticlesByColumndsSQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setColumnID(rs.getInt("columnid"));
                String maintitle = rs.getString("maintitle");
                String content = DBUtil.getBigString("oracle",rs,"content");
                String summary = rs.getString("summary");
                String source = rs.getString("source");
                article.setMainTitle(maintitle);
                article.setContent(content);
                article.setSummary(summary);
                article.setSource(source);
                article.setUrltype(rs.getInt("urltype"));
                article.setOtherurl(rs.getString("defineurl"));
                article.setDirName(rs.getString("dirname"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                articleList.add(article);

                //闯红灯、红绿灯 、交通信号灯 、斑马线 、人行横道 、过街天桥 、地铁 、不文明、垃圾、垃圾分类、ETC欠费、ETC、欠费、Etc欠费、Etc、etc、etc欠费、物业欠费、物业、养犬、狗、养狗、遛狗、遛犬、犬、信访、拆迁
                boolean titleFlag = maintitle.indexOf("闯红灯")>-1 || maintitle.indexOf("红绿灯")>-1 || maintitle.indexOf("交通信号灯")>-1 || maintitle.indexOf("斑马线")>-1
                        || maintitle.indexOf("人行横道")>-1 || maintitle.indexOf("过街天桥")>-1 || maintitle.indexOf("地铁")>-1 || maintitle.indexOf("不文明")>-1
                        || maintitle.indexOf("垃圾")>-1 || maintitle.indexOf("垃圾分类")>-1 || maintitle.indexOf("ETC欠费")>-1 || maintitle.indexOf("ETC")>-1
                        || maintitle.indexOf("欠费")>-1 || maintitle.indexOf("Etc欠费")>-1 || maintitle.indexOf("Etc")>-1 || maintitle.indexOf("etc")>-1
                        || maintitle.indexOf("etc欠费")>-1|| maintitle.indexOf("物业欠费")>-1|| maintitle.indexOf("物业")>-1|| maintitle.indexOf("养犬")>-1
                        || maintitle.indexOf("狗")>-1|| maintitle.indexOf("养狗")>-1|| maintitle.indexOf("遛狗")>-1|| maintitle.indexOf("遛犬")>-1
                        || maintitle.indexOf("犬")>-1|| maintitle.indexOf("信访")>-1|| maintitle.indexOf("拆迁")>-1;

                boolean contantFlag = content.indexOf("闯红灯")>-1 || content.indexOf("红绿灯")>-1 || content.indexOf("交通信号灯")>-1 || content.indexOf("斑马线")>-1
                        || content.indexOf("人行横道")>-1 || content.indexOf("过街天桥")>-1 || content.indexOf("地铁")>-1 || content.indexOf("不文明")>-1
                        || content.indexOf("垃圾")>-1 || content.indexOf("垃圾分类")>-1 || content.indexOf("ETC欠费")>-1 || content.indexOf("ETC")>-1
                        || content.indexOf("欠费")>-1 || content.indexOf("Etc欠费")>-1 || content.indexOf("Etc")>-1 || content.indexOf("etc")>-1
                        || content.indexOf("etc欠费")>-1|| content.indexOf("物业欠费")>-1|| content.indexOf("物业")>-1|| content.indexOf("养犬")>-1
                        || content.indexOf("狗")>-1|| content.indexOf("养狗")>-1|| content.indexOf("遛狗")>-1|| content.indexOf("遛犬")>-1
                        || content.indexOf("犬")>-1|| content.indexOf("信访")>-1|| content.indexOf("拆迁")>-1;
                if (contantFlag || titleFlag) {
                    wtArticleList.add(article);
                }

                count = count + 1;
                System.out.println(count + "==" +maintitle);
            }
            System.out.print("wt:" + wtArticleList.size());
            rs.close();
            pstmt.close();

            String web_root = "D:\\shouxin\\bjsjs_web\\bjsjs_xxw";
            String target_dir = "D:\\shouxin\\deleteFileBackup\\sjsxy";
            StringBuffer tbuf = new StringBuffer();
            for(int ii=0;ii<wtArticleList.size();ii++) {
                article = wtArticleList.get(ii);
                //处理文件，进行文件备份
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                String s_dir = web_root + StringUtil.replace(article.getDirName(),"/",File.separator) + simpleDateFormat.format(article.getCreateDate()) + File.separator;
                String filedir = StringUtil.replace(article.getDirName(),"/",File.separator) + simpleDateFormat.format(article.getCreateDate()) + File.separator;
                System.out.println(article.getMainTitle());
                System.out.println(s_dir + article.getID() + ".shtml");
                if (article.getUrltype() == 0) {
                    tbuf.append(article.getMainTitle() + "==" + s_dir + article.getID() + ".shtml" + "\r\n");
                    Copy copy = new Copy();
                    copy.copyFile(s_dir + article.getID() + ".shtml",target_dir,filedir);
                    File file = new File(s_dir + article.getID() + ".shtml");
                    if (file.exists()) file.delete();
                }else
                    tbuf.append(article.getMainTitle() + "==" + article.getOtherurl() +"\r\n");
            }
            FileUtil.writeFile(tbuf,"c:\\wt.txt");

            //修改文章在数据中的状态，将之修改为未用状态
            s_conn.setAutoCommit(false);
            pstmt = s_conn.prepareStatement("update tbl_article set status=0 where id=?");
            for(int ii=0;ii<wtArticleList.size();ii++) {
                article = wtArticleList.get(ii);
                pstmt.setInt(1,article.getID());
                pstmt.executeUpdate();
            }
            pstmt.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.commit();
                s_conn.close();
            } catch (SQLException exp) { }
        }

        return articleList;
    }

    private static final String SQL_CREATEUSER_FOR_ORACLE =
            "INSERT INTO tbl_members(userid,mmuserid,userpwd,nickname,siteid,createarticles,editarticles,deletearticles,company,department,departmentarticlestype,departmentarticlesids,emailaccount,emailpasswd,orgid,companyid,deptid,createdate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static int importMembers() {
        Connection s_conn = null, t_conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        List<User> userList = new ArrayList<>();
        String userinfo_SQL = "select * from tbl_memebers  where userid in ('文旅局行业管理科','区委办档案管理科','经信局信息化科','教委法规科','市场局法制科','卫健委钟锐','区文化市场综合执法大队'," +
                "'医保局法制监督与待遇保障科','集体资产监管办行政办公室','住建委法制信访科','区城管执法局法制科','新闻出版局','园林局规划审批科','统计局执法队','老山街道综合行政执法队','鲁谷街道综合执法队'," +
                "'五里坨街道综合行政执法队','八角街道城管分队','金顶街街道综合行政执法队','苹果园街道城管执法队','八角街道城管分队')";
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement(userinfo_SQL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setID(rs.getString("userid"));
                user.setUid(rs.getInt("id"));
                user.setUserID(rs.getString("userid"));
                user.setNickName(StringUtil.iso2gb(rs.getString("nickname")));
                user.setSiteid(rs.getInt("siteid"));
                user.setPhone(rs.getString("phone"));
                user.setMobilePhone(rs.getString("mphone"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setCompany(rs.getString("company"));
                user.setMyimage(rs.getString("myimage"));
                user.setDepartment(rs.getString("department"));
                user.setEmailpasswd(rs.getString("emailpasswd"));
                user.setEmailaccount(rs.getString("emailaccount"));
                user.setDepartmentarticlestype(rs.getInt("departmentarticlestype"));
                user.setDepartmentarticlesids(rs.getString("departmentarticlesids"));
                user.setTrypassnum(rs.getInt("trypassnum"));
                user.setTrypasstime(rs.getTimestamp("trypasstime"));
                user.setOrgid(rs.getInt("orgid"));
                user.setCompanyid(rs.getInt("companyid"));
                user.setDeptid(rs.getInt("deptid"));
                userList.add(user);
            }
            rs.close();
            pstmt.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.close();
            } catch (SQLException exp) { }
        }

        //目的数据库地址、用户名、密码、数据库实例名等信息
        String t_dbip = "192.166.96.6";
        String t_username = "bjsjsdbadmin";
        String t_password = "qazwsxokm";
        String t_port = "1521";
        String t_dbinstance = "oracle10g";
        t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
        int count = 0;
        try {
            t_conn.setAutoCommit(false);
        }
        catch (SQLException exp) {}

        for(int ii=0; ii<userList.size();ii++) {
            user = userList.get(ii);
            String userid = user.getUserID();
            try {
                //判断用户权限是否已经存在
                pstmt = t_conn.prepareStatement("select count(id) from tbl_members where userid=?");
                pstmt.setString(1, userid);
                rs = pstmt.executeQuery();
                if (rs.next()) count = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (count == 0) {
                    pstmt = t_conn.prepareStatement(SQL_CREATEUSER_FOR_ORACLE);
                    pstmt.setString(1, user.getUserID());
                    pstmt.setString(2, Encrypt.md5(user.getUserID().getBytes()));
                    pstmt.setString(3, user.getPassword());
                    pstmt.setString(4, user.getNickName());
                    pstmt.setInt(5, user.getSiteid());
                    pstmt.setInt(6, 0);
                    pstmt.setInt(7, 0);
                    pstmt.setInt(8, 0);
                    pstmt.setString(9,user.getCompany());
                    pstmt.setString(10, user.getDepartment());
                    pstmt.setInt(11,user.getDepartmentarticlestype());
                    pstmt.setString(12,user.getDepartmentarticlesids());
                    pstmt.setString(13,user.getEmailaccount());
                    pstmt.setString(14,user.getEmailpasswd());
                    pstmt.setInt(15, user.getOrgid());
                    pstmt.setInt(16,user.getCompanyid());
                    pstmt.setInt(17,user.getDeptid());
                    pstmt.setTimestamp(18, new Timestamp(System.currentTimeMillis()));
                    pstmt.setInt(19,user.getUid());
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println("增加用户 ==" +  userid);
                }
            } catch (Exception exp) {
                exp.printStackTrace();

            }
        }

        try {
            t_conn.commit();
        } catch (SQLException exp) {
        } finally {
            try{
                t_conn.close();
            } catch (SQLException exp) {
            }
        }

        return 0;
    }


    private static int updateArticle(int articleid) {
        Connection s_conn = null, t_conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String maintitle= null;
        String content = null;
        int id =0;
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement("select id,maintitle,content from tbl_article where id=?");
            pstmt.setInt(1,articleid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
                maintitle = rs.getString("maintitle");
                content = DBUtil.getBigString("oracle", rs, "content");
            }
            rs.close();
            pstmt.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.close();
            } catch (SQLException exp) { }
        }

        //目的数据库地址、用户名、密码、数据库实例名等信息
        String t_dbip = "192.166.96.6";
        String t_username = "bjsjsdbadmin";
        String t_password = "qazwsxokm";
        String t_port = "1521";
        String t_dbinstance = "oracle10g";
        t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
        try {
            if (articleid == id) {
                t_conn.setAutoCommit(false);
                pstmt = t_conn.prepareStatement("update tbl_article set maintitle=?,content=? where id=?");
                pstmt.setString(1,maintitle);
                if (content != null)
                    DBUtil.setBigString("oracle", pstmt, 2, content);
                else
                    pstmt.setNull(2, java.sql.Types.LONGVARCHAR);
                pstmt.setInt(3, id);
                pstmt.executeUpdate();
                pstmt.close();
                t_conn.commit();

                System.out.println("修改文章：" + maintitle);
            }
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                t_conn.close();
            } catch (SQLException exp) {}
        }

        return 0;
    }
    private static int importUserRights() {
        Connection s_conn = null,t_conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Rights> sRights = new ArrayList<>();
        int count = 0;
        StringBuffer tbuf = new StringBuffer();
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement("select userid,columnid,rightid from tbl_members_rights");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Rights rights = new Rights();
                rights.setUserID(rs.getString("userid"));
                rights.setColumnID(rs.getInt("columnid"));
                rights.setRightID(rs.getInt("rightid"));
                sRights.add(rights);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.close();
            } catch (SQLException exp) { }
        }

        //目的数据库地址、用户名、密码、数据库实例名等信息
        String t_dbip = "192.166.96.6";
        String t_username = "bjsjsdbadmin";
        String t_password = "qazwsxokm";
        String t_port = "1521";
        String t_dbinstance = "oracle10g";
        t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
        count = 0;
        try {
            t_conn.setAutoCommit(false);
        }
        catch (SQLException exp) {}
        for(int ii=0; ii<sRights.size();ii++) {
            Rights rights = sRights.get(ii);
            String userid = rights.getUserID();
            int columnid = rights.getColumnID();
            int rightid = rights.getRightID();
            try {
                //判断用户权限是否已经存在
                int rightCount = 0;
                pstmt = t_conn.prepareStatement("select count(id) from tbl_members_rights where userid=? and columnid=? and rightid=?");
                pstmt.setString(1, userid);
                pstmt.setInt(2,columnid);
                pstmt.setInt(3,rightid);
                rs = pstmt.executeQuery();
                if (rs.next()) rightCount = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (rightCount == 0) {
                    pstmt = t_conn.prepareStatement("INSERT INTO tbl_members_rights (userid,columnid,rightid) values(?, ?, ?)");
                    pstmt.setString(1,userid);
                    pstmt.setInt(2,columnid);
                    pstmt.setInt(3,rightid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println("增加用户权限 ==" +  userid + "==" + columnid + "==" + rightid);
                }
            } catch (SQLException exp) {

            }
        }

        try {
            t_conn.commit();
        } catch (SQLException exp) {
        } finally {
            try{
                t_conn.close();
            } catch (SQLException exp) {
            }
        }

        return 0;
    }

    private static final String SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE = "insert into tbl_refers_article(articleid,siteid,columnid,scolumnid," +
            "columnname,orders,createDate,title,status,pubflag,auditflag,usearticletype,REFERS_COLUMN_STATUS,tsiteid,artfrom,id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static int importReferArticles() {
        Connection s_conn = null,t_conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Refers> s_articles = new ArrayList<>();
        int count = 0;
        StringBuffer tbuf = new StringBuffer();

        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip, s_port, s_dbinstance, s_username, s_password, 1);
            pstmt = s_conn.prepareStatement("select id,articleid,siteid,columnid,scolumnid,columnname,orders,createDate,title,status,pubflag,auditflag,usearticletype,REFERS_COLUMN_STATUS,tsiteid,artfrom from tbl_refers_article t where t.siteid=40");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Refers refers = new Refers();
                int articleid = rs.getInt("articleid");
                refers.setId(rs.getInt("id"));
                refers.setArticleid(articleid);
                refers.setSiteid(rs.getInt("siteid"));
                refers.setColumnid(rs.getInt("columnid"));
                refers.setScolumnid(rs.getInt("scolumnid"));
                refers.setColumnname(rs.getString("columnname"));
                refers.setOrders(rs.getInt("orders"));
                refers.setCreatedate(rs.getTimestamp("createDate"));
                refers.setTitle(rs.getString("title"));
                refers.setStatus(rs.getInt("status"));
                refers.setPubflag(rs.getInt("pubflag"));
                refers.setAuditflag(rs.getInt("auditflag"));
                refers.setUsearticletype(rs.getInt("usearticletype"));
                refers.setREFERS_COLUMN_STATUS(rs.getInt("REFERS_COLUMN_STATUS"));
                refers.setTsiteid(rs.getInt("tsiteid"));
                refers.setArtfrom(rs.getInt("artfrom"));
                count = count + 1;
                s_articles.add(refers);
                System.out.println("refers==" + articleid + "==" + refers.getTitle());
            }
            rs.close();;
            pstmt.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        }  finally {
            try {
                s_conn.close();
            } catch (SQLException exp) { }
        }

        //目的数据库地址、用户名、密码、数据库实例名等信息
        String t_dbip = "192.166.96.6";
        String t_username = "bjsjsdbadmin";
        String t_password = "qazwsxokm";
        String t_port = "1521";
        String t_dbinstance = "oracle10g";
        t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
        count = 0;
        try {
            t_conn.setAutoCommit(false);
        }
        catch (SQLException exp) {}

        for(int ii=0; ii<s_articles.size();ii++) {
            Refers refers = s_articles.get(ii);
            int id = refers.getId();
            int articleid = refers.getArticleid();
            int columnid = refers.getColumnid();
            try {
                //判断引用文章是否已经存在
                int artcilecount = 0;
                pstmt = t_conn.prepareStatement("select count(id) from tbl_refers_article where id=?");
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();
                if (rs.next()) artcilecount = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (artcilecount == 0) {
                    int existFlag = 0;
                    pstmt = t_conn.prepareStatement("select count(id) from tbl_refers_article where id=?");
                    pstmt.setInt(1, articleid);
                    rs = pstmt.executeQuery();
                    if (rs.next()) existFlag = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    /*if (existFlag>0) {
                        pstmt = t_conn.prepareStatement("select tbl_refers_article_id.NEXTVAL from dual");
                        rs = pstmt.executeQuery();
                        if (rs.next()) id = rs.getInt(1);
                        rs.close();
                        pstmt.close();
                    }*/

                    if (existFlag>0) System.out.println("existFlag==" + existFlag + "==" + id);

                    //articleid,siteid,columnid,scolumnid,columnname,orders,createDate,title,status,pubflag,auditflag,usearticletype,REFERS_COLUMN_STATUS,tsiteid,artfrom,id
                    pstmt = t_conn.prepareStatement(SQL_INSERT_REFERS_ARTICLE_FOR_ORACLE);
                    pstmt.setInt(1, articleid);
                    pstmt.setInt(2, refers.getSiteid());
                    pstmt.setInt(3, columnid);
                    pstmt.setInt(4, refers.getScolumnid());
                    pstmt.setString(5, refers.getColumnname());
                    pstmt.setInt(6, refers.getOrders());
                    pstmt.setTimestamp(7, refers.getCreatedate());
                    pstmt.setString(8, refers.getTitle());
                    pstmt.setInt(9, refers.getStatus());
                    pstmt.setInt(10, refers.getPubflag());
                    pstmt.setInt(11, refers.getAuditflag());
                    pstmt.setInt(12, refers.getUsearticletype());
                    pstmt.setInt(13, refers.getREFERS_COLUMN_STATUS());
                    pstmt.setInt(14,refers.getTsiteid());
                    pstmt.setInt(15,refers.getArtfrom());
                    pstmt.setInt(16, id);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println("增加refers==" + articleid + "==" + refers.getTitle());
                } else {
                    System.out.println("文章已经存在==" + articleid + "==" + refers.getTitle());
                }
            } catch (SQLException exp) {
                exp.printStackTrace();
                tbuf.append("增加refers失败==" + articleid + "==" + refers.getTitle());
                System.out.println("增加文章失败：" + articleid + "==" + refers.getTitle());
            }
        }

        FileUtil.writeFile(tbuf,"c:\\tttt.txt");

        try {
            t_conn.commit();
        } catch (SQLException exp) {
        } finally {
            try{
                t_conn.close();
            } catch (SQLException exp) {
            }
        }

        return 0;
    }

    private static final String SQL_CREATE_ARTICLE_FOR_ORACLE = "INSERT INTO TBL_Article (siteid,ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
            "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,topflag,redflag,boldflag,Auditflag,Subscriber," +
            "Editor,creator,DirName,Publishtime,SalePrice,vipprice,InPrice,MarketPrice,score,voucher,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
            "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,articlepic,urltype," +
            "defineurl,t1,t2,t3,t4,t5,deptid,beidate,changepic,auditor,notearticleid,fromsiteid,sarticleid,mediafile,sign,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static int importArticles() {
        Connection s_conn = null,t_conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Article> s_articles = new ArrayList<>();
        int count = 0;

        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            //String s_dbip = "localhost";
            //String s_username = "sjsdbadmin";
            //String s_password = "qazwsxokm";
            //String s_port = "1521";
            //String s_dbinstance = "orcl11g";
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip,s_port,s_dbinstance,s_username, s_password,1);
            pstmt = s_conn.prepareStatement("select * from TBL_ARTICLE t where t.siteid=40 and t.columnid=3130 and t.editor like '%区发改委%'");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Article article = new Article();
                try {
                    article.setID(rs.getInt("id"));
                    article.setColumnID(rs.getInt("columnid"));
                    article.setSiteID(rs.getInt("siteid"));
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setSortID(rs.getInt("sortid"));
                    article.setViceTitle(rs.getString("vicetitle"));
                    article.setSummary(rs.getString("summary"));
                    article.setKeyword(rs.getString("keyword"));
                    article.setSource(rs.getString("source"));
                    article.setContent(DBUtil.getBigString("oracle", rs, "content"));
                    article.setNullContent(rs.getInt("emptycontentflag"));
                    article.setStatus(rs.getInt("status"));
                    article.setPubFlag(rs.getInt("pubflag"));
                    article.setSource(rs.getString("source"));
                    article.setAuthor(rs.getString("author"));
                    article.setDirName(rs.getString("dirName"));
                    article.setFileName(rs.getString("fileName"));
                    article.setEditor(rs.getString("editor"));
                    article.setDocLevel(rs.getInt("doclevel"));
                    article.setViceDocLevel(rs.getInt("ViceDocLevel"));
                    article.setAuditFlag(rs.getInt("auditflag"));
                    article.setSubscriber(rs.getInt("subscriber"));
                    article.setLockStatus(rs.getInt("lockstatus"));
                    article.setLockEditor(rs.getString("lockeditor"));
                    article.setAuditor(rs.getString("auditor"));
                    article.setIsPublished(rs.getInt("ISPUBLISHED"));
                    article.setRelatedArtID(rs.getString("RelatedArtID"));
                    article.setIndexflag(rs.getInt("INDEXFLAG"));
                    article.setJoinRSS(rs.getInt("ISJOINRSS"));
                    article.setReferArticleID(rs.getInt("referID"));
                    article.setMultimediatype(rs.getInt("MULTIMEDIATYPE"));
                    article.setModelID(rs.getInt("modelID"));
                    article.setChangepic(rs.getInt("CHANGEPIC"));
                    article.setArticlepic(rs.getString("articlepic"));
                    article.setSalePrice(rs.getFloat("SalePrice"));
                    article.setVIPPrice(rs.getFloat("vipprice"));
                    article.setInPrice(rs.getFloat("InPrice"));
                    article.setMarketPrice(rs.getFloat("MarketPrice"));
                    article.setScore(rs.getInt("score"));
                    article.setVoucher(rs.getInt("voucher"));
                    article.setBrand(rs.getString("Brand"));
                    article.setProductPic(rs.getString("Pic"));
                    article.setProductBigPic(rs.getString("BigPic"));
                    article.setProductWeight(rs.getFloat("Weight"));
                    article.setStockNum(rs.getInt("StockNum"));
                    article.setSalesnum(rs.getInt("salesnum"));
                    article.setClickNum(rs.getInt("CLICKNUM"));
                    article.setUrltype(rs.getInt("urltype"));
                    article.setOtherurl(rs.getString("defineurl"));
                    article.setBbdate(rs.getDate("beidate"));
                    article.setNotes(rs.getInt("notearticleid"));
                    article.setMediafile(rs.getString("mediafile"));
                    article.setProcessofaudit(rs.getInt("processofaudit"));
                    article.setDownfilename(rs.getString("DOWNFILENAME"));
                    article.setFromsiteid(rs.getInt("FROMSITEID"));
                    article.setSarticleid(rs.getString("SARTICLEID"));
                    article.setDeptid(rs.getString("DEPTID"));
                    article.setMediafile(rs.getString("MEDIAFILE"));
                    article.setCreator(rs.getString("CREATOR"));
                    article.setPublishTime(rs.getTimestamp("publishtime"));
                    article.setCreateDate(rs.getTimestamp("createDate"));
                    article.setLastUpdated(rs.getTimestamp("lastUpdated"));
                    s_articles.add(article);
                    count = count + 1;
                    System.out.println(count + "===" + rs.getString("maintitle") + "==" + rs.getInt("columnid") + rs.getInt("id"));
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            rs.close();
            pstmt.close();
            System.out.println("导出文章总数量：" + s_articles.size());
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.close();
            } catch (SQLException exp) {

            }
        }

        //目的数据库地址、用户名、密码、数据库实例名等信息
        StringBuffer tbuf = new StringBuffer();
        String t_dbip = "192.166.96.6";
        String t_username = "bjsjsdbadmin";
        String t_password = "qazwsxokm";
        String t_port = "1521";
        String t_dbinstance = "oracle10g";
        t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
        count = 0;
        try {
            t_conn.setAutoCommit(false);
        }
        catch (SQLException exp) {}
        for(int ii=0; ii<s_articles.size();ii++) {
            Article article = s_articles.get(ii);
            count = count + 1;
            int columnCount = 0;
            String name = article.getMainTitle();
            int articleid = article.getID();
            int siteid = article.getSiteID();
            int columnid = article.getColumnID();
            String source = article.getSource();

            try {
                //判断网站栏目是否存在
                pstmt = t_conn.prepareStatement("select count(id) from tbl_column where id=? and siteid=?");
                pstmt.setInt(1, columnid);
                pstmt.setInt(2, siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) columnCount = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (columnCount > 0) {
                    //System.out.println("该栏目存在：" + columnid + "===" + name);
                    //tbuf.append(articleid + "==" + name + "==" + columnid + "\r\n");
                    //判断文章在数据库中是否存在
                    int articleCount = 0;
                    pstmt = t_conn.prepareStatement("select count(id) from tbl_article where maintitle=? and publishtime=? and siteid=? and columnid=?");
                    pstmt.setString(1, name);
                    pstmt.setTimestamp(2,article.getPublishTime());
                    pstmt.setInt(3, siteid);
                    pstmt.setInt(4, columnid);
                    rs = pstmt.executeQuery();
                    if (rs.next()) articleCount = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    //栏目存在，但是文章不存在，增加该篇文章
                    if (articleCount == 0) {
                        int existFlag = 0;
                        pstmt = t_conn.prepareStatement("select count(id) from tbl_article where id=?");
                        pstmt.setInt(1, articleid);
                        rs = pstmt.executeQuery();
                        if (rs.next()) existFlag = rs.getInt(1);
                        rs.close();
                        pstmt.close();

                        System.out.println("existFlag==" + existFlag);

                        if (existFlag>0) {
                            pstmt = t_conn.prepareStatement("select tbl_article_id.NEXTVAL from dual");
                            rs = pstmt.executeQuery();
                            if (rs.next()) articleid = rs.getInt(1);
                            rs.close();
                            pstmt.close();
                        }

                        pstmt = t_conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_ORACLE);
                        pstmt.setInt(1, article.getSiteID());
                        pstmt.setInt(2, columnid);
                        pstmt.setInt(3, article.getSortID());
                        pstmt.setString(4, article.getMainTitle());
                        pstmt.setString(5, article.getViceTitle());
                        pstmt.setString(6, article.getSummary());
                        pstmt.setString(7, article.getKeyword());
                        pstmt.setString(8, article.getSource());
                        if (article.getContent() != null)
                            DBUtil.setBigString("oracle", pstmt, 9, article.getContent());
                        else
                            pstmt.setNull(9, java.sql.Types.LONGVARCHAR);
                        pstmt.setInt(10, article.getNullContent());
                        pstmt.setString(11, article.getAuthor());
                        pstmt.setTimestamp(12, article.getCreateDate());
                        pstmt.setTimestamp(13, article.getLastUpdated());
                        pstmt.setString(14, article.getFileName());
                        pstmt.setString(15, article.getDownfilename());
                        pstmt.setInt(16, article.getDocLevel());
                        pstmt.setInt(17, article.getPubFlag());
                        pstmt.setInt(18, article.getStatus());
                        pstmt.setInt(19, article.getTopflag());
                        pstmt.setInt(20, article.getRedflag());
                        pstmt.setInt(21, article.getBoldflag());
                        pstmt.setInt(22, article.getAuditFlag());
                        pstmt.setInt(23, article.getSubscriber());
                        pstmt.setString(24, article.getEditor());
                        pstmt.setString(25, article.getCreator());
                        pstmt.setString(26, article.getDirName());
                        pstmt.setTimestamp(27, article.getPublishTime());
                        pstmt.setFloat(28, article.getSalePrice());
                        pstmt.setFloat(29, article.getVIPPrice());
                        pstmt.setFloat(30, article.getInPrice());
                        pstmt.setFloat(31, article.getMarketPrice());
                        pstmt.setInt(32, article.getScore());
                        pstmt.setInt(33, article.getVoucher());
                        pstmt.setFloat(34, article.getProductWeight());
                        pstmt.setInt(35, article.getStockNum());
                        pstmt.setString(36, article.getBrand());
                        pstmt.setString(37, article.getProductPic());
                        pstmt.setString(38, article.getProductBigPic());
                        pstmt.setString(39, article.getRelatedArtID());
                        pstmt.setInt(40, article.getLockStatus());
                        pstmt.setInt(41, article.getIsPublished());
                        pstmt.setInt(42, article.getIndexflag());
                        pstmt.setInt(43, article.getViceDocLevel());
                        pstmt.setInt(44, article.getJoinRSS());
                        pstmt.setInt(45, article.getClickNum());
                        pstmt.setInt(46, article.getReferArticleID());
                        pstmt.setInt(47, article.getModelID());
                        pstmt.setString(48, article.getArticlepic());
                        pstmt.setInt(49, article.getUrltype());
                        pstmt.setString(50, article.getOtherurl());
                        pstmt.setInt(51, article.getT1());
                        pstmt.setInt(52, article.getT2());
                        pstmt.setInt(53, article.getT3());
                        pstmt.setInt(54, article.getT4());
                        pstmt.setInt(55, article.getT5());
                        pstmt.setString(56, article.getDeptid());
                        pstmt.setDate(57, article.getBbdate());
                        pstmt.setInt(58, article.getChangepic());
                        pstmt.setString(59, article.getAuditor());
                        pstmt.setInt(60, article.getNotes());
                        pstmt.setInt(61, article.getFromsiteid());
                        pstmt.setString(62, article.getSarticleid());
                        pstmt.setString(63, article.getMediafile());
                        pstmt.setString(64, "");
                        pstmt.setInt(65, articleid);
                        pstmt.executeUpdate();
                        pstmt.close();
                        System.out.println("增加文章：" + article.getMainTitle() + "==" + article.getID() + "==" + columnid);
                    } //else {
                    //  System.out.println("该篇文章已经存在该栏目：" + name + "===" + articleid);
                    ///}
                } else {
                    System.out.println("该栏目不存在，请创建该栏目：" + columnid + "===" + name);
                    tbuf.append("该栏目不存在，请创建该栏目：" + columnid + "===" + name + "\r\n");
                }
            } catch (SQLException exp) {
                exp.printStackTrace();
                tbuf.append("增加文章失败：" + article.getID() + "==" + article.getMainTitle());
                System.out.println("增加文章失败：" + article.getID() + "==" + article.getMainTitle());
            }
        }

        FileUtil.writeFile(tbuf,"c:\\tttt.txt");

        System.out.println("修改文章完毕");

        try {
            t_conn.commit();
        } catch (SQLException exp) {
        } finally {
            try{
                t_conn.close();
            } catch (SQLException exp) {
            }
        }

        return 0;
    }

    private static final String SQL_CREATE_MODEL_FOR_ORACLE = "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Content,Createdate,Lastupdated,Editor," +
            "Creator,LockStatus,RelatedColumnID,Status,ModelVersion,TemplateName,ChName,ReferModelID," +
            "defaultTemplate,isIncluded,tempnum,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static final String SQL_UPDATE_MODEL = "UPDATE TBL_Template SET ColumnID=?,IsArticle=?,Content=?,Lastupdated=?,Editor=?,LockStatus=?," +
            "LockEditor=?,RelatedColumnid=?,Status=?,ModelVersion=?,ChName=?,TemplateName=?,isIncluded=? ,tempnum=? WHERE ID=?";

    private static int importTemplatesForFZ() {
        Connection s_conn = null,t_conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Model> s_models = new ArrayList<>();
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            //String s_dbip = "localhost";
            //String s_username = "sjsdbadmin";
            //String s_password = "qazwsxokm";
            //String s_port = "1521";
            //String s_dbinstance = "orcl11g";
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip,s_port,s_dbinstance,s_username, s_password,1);
            pstmt = s_conn.prepareStatement("select * from tbl_template t where t.siteid!=40 and to_char(createdate,'yyyy-MM-dd')>'2018-11-26' order by t.createdate desc");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Model model = new Model();
                try {
                    model.setID(rs.getInt("id"));
                    model.setSiteID(rs.getInt("siteid"));
                    model.setColumnID(rs.getInt("COLUMNID"));
                    model.setIsArticle(rs.getInt("ISARTICLE"));
                    model.setContent(DBUtil.getBigString("oracle", rs, "content"));
                    model.setCreatedate(rs.getTimestamp("CREATEDATE"));
                    model.setLastupdated(rs.getTimestamp("LASTUPDATED"));
                    model.setEditor(rs.getString("EDITOR"));
                    model.setCreator(rs.getString("CREATOR"));
                    model.setRelatedColumnIDs(rs.getString("RELATEDCOLUMNID"));
                    model.setLockstatus(rs.getInt("LOCKSTATUS"));
                    model.setLockEditor(rs.getString("LOCKEDITOR"));
                    model.setChineseName(rs.getString("CHNAME"));
                    model.setDefaultTemplate(rs.getInt("DEFAULTTEMPLATE"));
                    model.setTemplateName(rs.getString("TEMPLATENAME"));
                    model.setReferModelID(rs.getInt("REFERMODELID"));
                    model.setIncluded(rs.getInt("ISINCLUDED"));
                    model.setTempnum(rs.getInt("TEMPNUM"));
                    s_models.add(model);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
                //System.out.println(rs.getString("CHNAME") + "==" + rs.getTimestamp("createdate").toString());
            }
            rs.close();
            pstmt.close();

            //源数据库地址、用户名、密码、数据库实例名等信息
            String t_dbip = "localhost";
            String t_username = "bjsjsdbadmin";
            String t_password = "qazwsxokm";
            String t_port = "1521";
            //String t_dbinstance = "orcl11g";
            String t_dbinstance = "orcl";
            t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
            t_conn.setAutoCommit(false);
            for(int ii=0; ii<s_models.size();ii++) {
                Model model = s_models.get(ii);
                int count = 0;
                int columnCount = 0;
                String name = model.getChineseName();
                int modelid = model.getID();
                int siteid = model.getSiteID();
                int columnid = model.getColumnID();

                //判断网站栏目是否存在
                pstmt = t_conn.prepareStatement("select count(id) from tbl_column where id=? and siteid=?");
                pstmt.setInt(1, columnid);
                pstmt.setInt(2, siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) columnCount = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (columnCount > 0) {
                    //判断模板在数据库中是否存在
                    int t_siteid = 0;
                    pstmt = t_conn.prepareStatement("select id,siteid from tbl_template where id=?");
                    pstmt.setInt(1, modelid);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        t_siteid = rs.getInt("siteid");
                    };
                    rs.close();
                    pstmt.close();


                    //栏目存在，但是模板不存在，则增加该栏目模板
                    if (t_siteid==0) {
                        pstmt = t_conn.prepareStatement(SQL_CREATE_MODEL_FOR_ORACLE);
                        pstmt.setInt(1, model.getSiteID());
                        pstmt.setInt(2, model.getColumnID());
                        pstmt.setInt(3, model.getIsArticle());
                        if (model.getReferModelID() == 0)
                            DBUtil.setBigString("oracle", pstmt, 4, model.getContent());
                        else
                            pstmt.setString(4, "");
                        pstmt.setTimestamp(5, model.getCreatedate());
                        pstmt.setTimestamp(6, model.getLastupdated());
                        pstmt.setString(7, model.getEditor());
                        pstmt.setString(8, model.getCreator());
                        pstmt.setInt(9, model.getLockStatus());
                        pstmt.setString(10, model.getRelatedColumnIDs());
                        pstmt.setInt(11, 0);             //模板页面的状态设置为0,模板需要发布
                        pstmt.setInt(12, 0);             //模板为第一个版本，版本号为零
                        pstmt.setString(13, model.getTemplateName());
                        pstmt.setString(14, model.getChineseName());
                        pstmt.setInt(15, model.getReferModelID());
                        pstmt.setInt(16, 0);
                        pstmt.setInt(17, model.getIncluded());
                        pstmt.setInt(18,model.getTempnum());
                        pstmt.setInt(19, model.getID());
                        System.out.println("增加模板：" + model.getChineseName() + ":" + model.getID());
                        pstmt.executeUpdate();
                    } else if (t_siteid==siteid){
                        //该模板在数据库中已经存在，根据最后修改的时间戳判断该模板是否被修改过，如果该模板被修改过则更新该模板;
                        pstmt = t_conn.prepareStatement("select * from tbl_template where id=?");
                        pstmt.setInt(1,modelid);
                        rs = pstmt.executeQuery();
                        Timestamp lastupdate = null;
                        if (rs.next()) lastupdate = rs.getTimestamp("lastupdated");
                        rs.close();
                        pstmt.close();

                        if (lastupdate.before(model.getLastupdated())) {
                            pstmt = t_conn.prepareStatement(SQL_UPDATE_MODEL);
                            pstmt.setInt(1, model.getColumnID());
                            pstmt.setInt(2, model.getIsArticle());
                            DBUtil.setBigString("oracle", pstmt, 3, model.getContent());
                            pstmt.setTimestamp(4, model.getLastupdated());
                            pstmt.setString(5, model.getEditor());
                            pstmt.setInt(6, model.getLockStatus());
                            pstmt.setString(7, "");
                            pstmt.setString(8, model.getRelatedColumnIDs());
                            pstmt.setInt(9, 0);                               //模板页面的状态设置为0，模板需要发布
                            pstmt.setInt(10, 0);                              //模板为第一个版本，版本号为零
                            pstmt.setString(11, model.getChineseName());
                            pstmt.setString(12, model.getTemplateName());
                            pstmt.setInt(13, model.getIncluded());
                            pstmt.setInt(14, model.getTempnum());
                            pstmt.setInt(15, model.getID());
                            pstmt.executeUpdate();
                        } else {
                            System.out.println("模板内容是最新内容，不需要修改："+ name);
                        }
                    } else {
                        System.out.println("其他站中存在该模板："+ t_siteid + "==" + name + "==" + modelid);
                    }
                } else {
                    System.out.println("该栏目不存在，请创建该栏目："+columnid + name);
                }
            }
            pstmt.close();
            t_conn.commit();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.close();
                t_conn.close();
            } catch (SQLException exp) {

            }
        }
        return 0;
    }


    private static final String SQL_CREATECOLUMN_FOR_ORACLE =
            "INSERT INTO tbl_column (siteID,Dirname,OrderID,parentID,Cname,Ename,Extname," +
                    "CreateDate,LastUpdated,Editor,isDefineAttr,XMLTemplate,isAudited,ColumnDesc,IsProduct,islocation," +
                    "IsPublishMore,LanguageType,hasarticlemodel,contentshowtype,userflag,userlevel,publicflag," +
                    "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize,ts_pic,s_pic,ms_pic,m_pic,ml_pic,l_pic,tl_pic,ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static int createNewColumn() {
        Connection s_conn = null,t_conn=null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Column> s_columns = new ArrayList<>();
        try {
            //源数据库地址、用户名、密码、数据库实例名等信息
            //String s_dbip = "localhost";
            //String s_username = "sjsdbadmin";
            //String s_password = "qazwsxokm";
            //String s_port = "1521";
            //String s_dbinstance = "orcl11g";
            String s_dbip = "192.166.96.6";
            String s_username = "webbuildercms";
            String s_password = "bizwinkcms";
            String s_port = "1521";
            String s_dbinstance = "oracle10g";
            s_conn = createConnection(s_dbip,s_port,s_dbinstance,s_username, s_password,1);
            pstmt = s_conn.prepareStatement("select * from tbl_column where to_char(createdate,'yyyy-MM-dd')>'2019-06-10' order by createdate desc");
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Column column = new Column();
                column.setID(rs.getInt("id"));
                column.setSiteID(rs.getInt("siteid"));
                column.setParentID(rs.getInt("PARENTID"));
                column.setOrderID(rs.getInt("ORDERID"));
                column.setCName(rs.getString("CNAME"));
                column.setEName(rs.getString("ENAME"));
                column.setDirName(rs.getString("DIRNAME"));
                column.setEditor(rs.getString("EDITOR"));
                column.setExtname(rs.getString("EXTNAME"));
                column.setCreateDate(rs.getTimestamp("CREATEDATE"));
                column.setLastUpdated(rs.getTimestamp("LASTUPDATED"));
                column.setDefineAttr(rs.getInt("ISDEFINEATTR"));
                column.setHasArticleModel(rs.getInt("HASARTICLEMODEL"));
                column.setIsAudited(rs.getInt("ISAUDITED"));
                column.setIsProduct(rs.getInt("ISPRODUCT"));
                column.setIsPublishMoreArticleModel(rs.getInt("ISPUBLISHMORE"));
                column.setLanguageType(rs.getInt("LANGUAGETYPE"));
                column.setDesc(rs.getString("COLUMNDESC"));
                column.setXMLTemplate(rs.getString("XMLTEMPLATE"));
                column.setContentShowType(rs.getInt("CONTENTSHOWTYPE"));
                column.setRss(rs.getInt("ISRSS"));
                column.setGetRssArticleTime(rs.getInt("GETRSSARTICLETIME"));
                column.setArchivingrules(rs.getInt("ARCHIVINGRULES"));
                column.setUseArticleType(rs.getInt("USEARTICLETYPE"));
                column.setIsType(rs.getInt("ISTYPE"));
                column.setPublicflag(rs.getInt("PUBLICFLAG"));
                column.setUserflag(rs.getInt("USERFLAG"));
                column.setUserlevel(rs.getInt("USERLEVEL"));
                column.setArticlecount(rs.getInt("ARTICLECOUNT"));
                column.setTitlepic(rs.getString("TITLEPIC"));
                column.setVtitlepic(rs.getString("VTITLEPIC"));
                column.setSourcepic(rs.getString("SOURCEPIC"));
                column.setAuthorpic(rs.getString("AUTHORPIC"));
                column.setContentpic(rs.getString("CONTENTPIC"));
                column.setSpecialpic(rs.getString("SPECIALPIC"));
                column.setProductpic(rs.getString("PRODUCTPIC"));
                column.setProductsmallpic(rs.getString("PRODUCTSMALLPIC"));
                column.setTs_pic(rs.getString("TS_PIC"));
                column.setS_pic(rs.getString("S_PIC"));
                column.setMs_pic(rs.getString("MS_PIC"));
                column.setM_pic(rs.getString("M_PIC"));
                column.setMl_pic(rs.getString("ML_PIC"));
                column.setL_pic(rs.getString("L_PIC"));
                column.setTl_pic(rs.getString("TL_PIC"));
                column.setIsPosition(rs.getInt("ISLOCATION"));
                // column.setMediasize(rs.getString("MEDIASIZE"));
                // column.setMediapicsize(rs.getString("MEDIAPICSIZE"));
                s_columns.add(column);
                //System.out.println(rs.getString("cname") + "==" + rs.getTimestamp("createdate").toString());
            }
            rs.close();
            pstmt.close();

            //源数据库地址、用户名、密码、数据库实例名等信息
            String t_dbip = "localhost";
            String t_username = "bjsjsdbadmin";
            String t_password = "qazwsxokm";
            String t_port = "1521";
            //String t_dbinstance = "orcl11g";
            String t_dbinstance = "orcl";
            t_conn = createConnection(t_dbip,t_port,t_dbinstance,t_username, t_password,1);
            t_conn.setAutoCommit(false);
            for(int ii=0; ii<s_columns.size();ii++) {
                Column column = s_columns.get(ii);
                int count = 0;
                String name = column.getCName();
                int columnid = column.getID();
                int siteid = column.getSiteID();
                pstmt=t_conn.prepareStatement("select count(id) from tbl_column where id=? and siteid=? and cname=?");
                pstmt.setInt(1,columnid);
                pstmt.setInt(2,siteid);
                pstmt.setString(3,name);
                rs= pstmt.executeQuery();
                if (rs.next()) count = rs.getInt(1);
                rs.close();
                pstmt.close();

                if (count == 0) {
                    pstmt = t_conn.prepareStatement(SQL_CREATECOLUMN_FOR_ORACLE);
                    pstmt.setInt(1, column.getSiteID());
                    pstmt.setString(2, column.getDirName());
                    pstmt.setInt(3, column.getOrderID());
                    pstmt.setInt(4, column.getParentID());
                    pstmt.setString(5, column.getCName());
                    pstmt.setString(6, column.getEName().trim());
                    pstmt.setString(7, column.getExtname());
                    pstmt.setTimestamp(8, column.getCreateDate());
                    pstmt.setTimestamp(9, column.getLastUpdated());
                    pstmt.setString(10, column.getEditor());
                    pstmt.setInt(11, column.getDefineAttr());
                    if (column.getXMLTemplate() != null)
                        DBUtil.setBigString("oracle", pstmt, 12, column.getXMLTemplate());
                    else
                        pstmt.setNull(12, java.sql.Types.LONGVARCHAR);
                    pstmt.setInt(13, column.getIsAudited());
                    pstmt.setString(14, column.getDesc());
                    pstmt.setInt(15, column.getIsProduct());
                    pstmt.setInt(16, column.getIsPosition());
                    pstmt.setInt(17, column.getIsPublishMoreArticleModel());
                    pstmt.setInt(18, column.getLanguageType());
                    pstmt.setInt(19, 0);
                    pstmt.setInt(20, column.getContentShowType());
                    pstmt.setInt(21, column.getUserflag());
                    pstmt.setInt(22, column.getUserlevel());
                    pstmt.setInt(23, column.getPublicflag());
                    pstmt.setString(24, column.getTitlepic());
                    pstmt.setString(25, column.getVtitlepic());
                    pstmt.setString(26, column.getSourcepic());
                    pstmt.setString(27, column.getAuthorpic());
                    pstmt.setString(28, column.getContentpic());
                    pstmt.setString(29, column.getSpecialpic());
                    pstmt.setString(30, column.getProductpic());
                    pstmt.setString(31, column.getProductsmallpic());
                    pstmt.setString(32, column.getMediasize());
                    pstmt.setString(33, column.getMediapicsize());
                    pstmt.setString(34, column.getTs_pic());
                    pstmt.setString(35, column.getS_pic());
                    pstmt.setString(36, column.getMs_pic());
                    pstmt.setString(37, column.getM_pic());
                    pstmt.setString(38, column.getMl_pic());
                    pstmt.setString(39, column.getL_pic());
                    pstmt.setString(40, column.getTl_pic());
                    pstmt.setInt(41, column.getID());
                    System.out.println(name + "==" + columnid);
                    pstmt.executeUpdate();
                } else {
                    System.out.println(name + ":栏目已经存在");
                }
            }
            pstmt.close();
            t_conn.commit();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            try {
                s_conn.close();
                t_conn.close();
            } catch (SQLException exp) {

            }
        }

        return 0;
    }

    private static List getData(String path) {
        List list = new ArrayList();
        try {
            int nLineCount = 0;//行数
            //File file = new File("d:\\dataforyr\\test1.txt");
            File file = new File(path);
            BufferedReader in = new BufferedReader(new FileReader(file));
            String strLine = "";

            while ((strLine = in.readLine()) != null) {
                nLineCount++;
                String getnum[] = strLine.split(",");
                list.add(getnum);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    private static int createZone(List list) {
        String dbip = "localhost";
        String username = "webcms";
        String password = "1qaz2wsx";
        int flag = 0;
        Connection conn = createConnection(dbip,"1521","orcl11g", username, password, 2);
        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                String[] getnum = (String[]) list.get(i);
                conn.setAutoCommit(false);
                System.out.println("getnum[2]="+getnum[2]);
                pstmt = conn.prepareStatement("insert into en_zone(id,cityid,zonename,orderid) values(?,?,?,?)");
                pstmt.setInt(1,Integer.parseInt(getnum[0]));
                pstmt.setInt(2,Integer.parseInt(getnum[1]));
                pstmt.setString(3,getnum[2]);
                pstmt.setInt(4,Integer.parseInt(getnum[3]));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            //conn.commit();

        }
        catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private static int createProv(List list) {
        String dbip = "localhost";
        String username = "webcms";
        String password = "1qaz2wsx";
        int flag = 0;
        Connection conn = createConnection(dbip,"1521","orcl11g",username, password, 2);
        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                String[] getnum = (String[]) list.get(i);
                conn.setAutoCommit(false);
                System.out.println("getnum[2]="+getnum[2]);
                pstmt = conn.prepareStatement("insert into en_province(id,ProvName,orderid,EmsFee) values(?,?,?,?)");
                pstmt.setInt(1,Integer.parseInt(getnum[0]));
                pstmt.setString(2,getnum[1]);
                pstmt.setInt(3,Integer.parseInt(getnum[2]));
                pstmt.setInt(4,Integer.parseInt(getnum[3]));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            //conn.commit();

        }
        catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private static int createCity(List list) {
        String dbip = "localhost";
        String username = "webcms";
        String password = "1qaz2wsx";
        int flag = 0;
        Connection conn = createConnection(dbip,"1521","orcl11g",username, password, 2);
        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                String[] getnum = (String[]) list.get(i);
                conn.setAutoCommit(false);
                System.out.println("getnum[2]="+getnum[2]);
                pstmt = conn.prepareStatement("insert into en_city(id,ProvID,CityName,orderid) values(?,?,?,?)");
                pstmt.setInt(1,Integer.parseInt(getnum[0]));
                pstmt.setInt(2,Integer.parseInt(getnum[1]));
                pstmt.setString(3,getnum[2]);
                pstmt.setInt(4,Integer.parseInt(getnum[3]));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            //conn.commit();

        }
        catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}