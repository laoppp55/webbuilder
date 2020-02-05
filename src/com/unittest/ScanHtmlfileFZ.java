package com.unittest;

import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.FileUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-5-27.
 */
public class ScanHtmlfileFZ {
    public  static Connection createConnection(String dbip, String dbusername, String dbpassword, String dbname, int port, int flag) {
        Connection conn = null;

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":" + port, dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port  + ":"+dbname, dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":" + port + "/" + dbname + "?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        return conn;
    }

    public static void main(String args[]) {
        //扫描数据库
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = createConnection("192.166.96.6", "webbuildercms", "bizwinkcms", "orACLE10G", 1521, 1);
        String ids = "";
        int count = 0;

        try {
            pstmt = conn.prepareStatement("select id,maintitle,summary,content from tbl_article");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("maintitle");
                String summary = rs.getString("summary");
                if(summary==null) summary="";
                String content = DBUtil.getBigString("oracle", rs, "content");
                if (content.indexOf("陈刚")>-1 || content.indexOf("鲁炜")>-1 || content.indexOf("苏荣")>-1 || content.indexOf("令计划")>-1 || content.indexOf("令计")>-1 || content.indexOf("李士祥")>-1||
                        content.indexOf("士祥")>-1 || content.indexOf("孙政才")>-1|| content.indexOf("政才")>-1 || content.indexOf("周永康")>-1|| content.indexOf("永康")>-1 || content.indexOf("郭伯雄")>-1||
                        content.indexOf("伯雄")>-1 || content.indexOf("薄熙来")>-1|| content.indexOf("熙来")>-1 || content.indexOf("徐才厚")>-1|| content.indexOf("才厚")>-1 || content.indexOf("锡文")>-1 ||
                        content.indexOf("吕锡文")>-1 ||
                        summary.indexOf("陈刚")>-1 || summary.indexOf("鲁炜")>-1 || summary.indexOf("苏荣")>-1 || summary.indexOf("令计划")>-1 || summary.indexOf("令计")>-1 || summary.indexOf("李士祥")>-1||
                        summary.indexOf("士祥")>-1 || summary.indexOf("孙政才")>-1|| summary.indexOf("政才")>-1 || summary.indexOf("周永康")>-1|| summary.indexOf("永康")>-1 || summary.indexOf("郭伯雄")>-1||
                        summary.indexOf("伯雄")>-1 || summary.indexOf("薄熙来")>-1|| summary.indexOf("熙来")>-1 || summary.indexOf("徐才厚")>-1|| summary.indexOf("才厚")>-1 || summary.indexOf("锡文")>-1 ||
                        summary.indexOf("吕锡文")>-1 ||
                        title.indexOf("陈刚")>-1 || title.indexOf("鲁炜")>-1 || title.indexOf("苏荣")>-1 || title.indexOf("令计划")>-1 || title.indexOf("令计")>-1 || title.indexOf("李士祥")>-1||
                        title.indexOf("士祥")>-1 || title.indexOf("孙政才")>-1|| title.indexOf("政才")>-1 || title.indexOf("周永康")>-1|| title.indexOf("永康")>-1 || title.indexOf("郭伯雄")>-1||
                        title.indexOf("伯雄")>-1 || title.indexOf("薄熙来")>-1|| title.indexOf("熙来")>-1 || title.indexOf("徐才厚")>-1|| title.indexOf("才厚")>-1 || title.indexOf("锡文")>-1 ||
                        title.indexOf("吕锡文")>-1){
                    ids = ids + rs.getInt("id")+",";
                }
                count = count + 1;
                System.out.println(count + "==title==" + title);
            }
            rs.close();
            pstmt.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException exp) {

            }
        }


        String content = "";
        int totalfile = 0;
        List<String> result_ddrk = new ArrayList();
        List<String> result_dd = new ArrayList();
        try {
            String encoding="utf-8";
            //String filePath = "D:\\shouxin\\bjsjs_web\\bjsjs_xxw";
            String filePath = "D:\\sjsfzweb";
            //String filePath = "C:\\data\\sjslgbj";

            int num = 1;
            List<String> files = new ArrayList();
            files.add(filePath);
            while (files.size()>0) {
                num = num -1;
                filePath = files.get(num);
                files.remove(num);
                File file=new File(filePath);
                String[] fname = file.list();
                if (fname!=null) {
                    for(int ii=0;ii<fname.length;ii++) {
                        String tfilename = file.getPath() + File.separator + fname[ii];
                        File tfile = new File(tfilename);
                        if(tfile.isFile()&&fname[ii].endsWith(".shtml")){ //判断文件是否存在
                            InputStreamReader read = new InputStreamReader( new FileInputStream(tfile),encoding);//考虑到编码格式
                            BufferedReader bufferedReader = new BufferedReader(read);
                            String lineTxt = null;
                            content = "";
                            while((lineTxt = bufferedReader.readLine()) != null){
                                content = content + lineTxt;
                            }
                            read.close();
                            if (content.indexOf("陈刚")>-1 ||
                                    content.indexOf("鲁炜")>-1 ||
                                    content.indexOf("苏荣")>-1 ||
                                    content.indexOf("令计划")>-1 ||
                                    content.indexOf("令计")>-1 ||
                                    content.indexOf("李士祥")>-1||
                                    content.indexOf("士祥")>-1 ||
                                    content.indexOf("孙政才")>-1||
                                    content.indexOf("政才")>-1 ||
                                    content.indexOf("周永康")>-1||
                                    content.indexOf("永康")>-1 ||
                                    content.indexOf("郭伯雄")>-1||
                                    content.indexOf("伯雄")>-1 ||
                                    content.indexOf("薄熙来")>-1||
                                    content.indexOf("熙来")>-1 ||
                                    content.indexOf("徐才厚")>-1||
                                    content.indexOf("才厚")>-1 ||
                                    content.indexOf("锡文")>-1 ||
                                    content.indexOf("吕锡文")>-1){
                                result_ddrk.add(tfilename);
                            }
                            totalfile = totalfile + 1;
                            System.out.println(tfilename);
                        }else if (tfile.isDirectory()){
                            if (!fname[ii].startsWith("www")) {
                                files.add(tfilename);
                                num = num +1;
                            }
                        }
                    }
                }
            }

            System.out.println("共扫描文件：" + totalfile);


            //File file = new File("D:\\shouxin\\deleteFileBackup\\deleted.txt");
            File file = new File("D:\\deletedbackup\\deleted.txt");
            //File file = new File("D:\\shouxin\\bjsjs_web\\ddrk.txt");
            FileWriter fw = null;
            BufferedWriter writer = null;
            try {
                fw = new FileWriter(file);
                writer = new BufferedWriter(fw);
                for(int jj=0; jj<result_ddrk.size(); jj++) {
                    writer.write(result_ddrk.get(jj));
                    writer.newLine();    //换行
                }
                writer.flush();

                //String backup_root = "D:\\shouxin\\deleteFileBackup";
                String backup_root = "D:\\deletedbackup";
                //String backup_root = "C:\\data\\backup";

                //List<String> lines = FileUtil.readFileByLines("D:\\shouxin\\deleteFileBackup\\deleted.txt");
                List<String> lines = FileUtil.readFileByLines("D:\\deletedbackup\\deleted.txt");
                for(int jj=0; jj<lines.size(); jj++) {
                    String full_filename = lines.get(jj);
                    int posi = full_filename.lastIndexOf(File.separator);
                    String filename = backup_root + full_filename.substring(posi);
                    System.out.println(full_filename + "==" + filename);
                    List<String> contents = FileUtil.readFileByEncoding(full_filename,"utf-8");          //读出原始文件的内容
                    StringBuffer buf = new StringBuffer();
                    for (int kk=0; kk<contents.size(); kk++) {
                        buf.append(contents.get(kk));
                    }
                    FileUtil.writeFile(buf,filename);
                    File df = new File(full_filename);
                    df.delete();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    writer.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("ids===" + ids);
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
}
