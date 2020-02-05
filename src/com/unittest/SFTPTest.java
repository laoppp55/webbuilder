package com.unittest;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

import com.jcraft.jsch.ChannelSftp;

public class SFTPTest {

    public SFTPTool getSFTPChannel() {
        return new SFTPTool();
    }

    /**
     * @param args
     * @throws Exception
     * 康师傅数据库备份程序，将康师傅每日备份的数据库文件备份到116.90.87.233服务器
     */
    public static void main(String[] args) throws Exception {
        SFTPTest test = new SFTPTest();
        Map<String, String> sftpDetails = new HashMap<String, String>();
        // 设置主机ip，端口，用户名，密码
        sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "39.96.12.36");
        sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "pubuser");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "Zaq!2wsx");
        sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");

        SFTPTool channel = test.getSFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        String homedir = chSftp.getHome();

        System.out.println(homedir);

        //设置文件名称编码，解决中文文件名乱码问题
        Class cl = ChannelSftp.class;
        Field f =cl.getDeclaredField("server_version");
        f.setAccessible(true);
        f.set(chSftp, 2);
        chSftp.setFilenameEncoding("GBK");

        //获取昨天日期的字符串格式
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE,-1);
        //String yesterday = new SimpleDateFormat( "yyyy_MM_dd ").format(calendar.getTime());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String s_month = null;
        if (month<10)
            s_month="0" + String.valueOf(month+1);
        else
            s_month =String.valueOf(month+1);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String s_day = null;
        if (day<10)
            s_day = "0" + String.valueOf(day);
        else
            s_day = String.valueOf(day);
        List<String>  files = new ArrayList<String>();
        String bakfiledir=args[0];
        File backfiles = new File(bakfiledir);
        for(File tfile:backfiles.listFiles()) {
            if (tfile.isFile()) {
                String tt = tfile.getPath();
                //if (tt.contains(String.valueOf(year)+"_"+s_month+"_"+s_day)) {
                files.add(tt);
                //}
            }
        }
        String src = null; // 本地文件名
        String dst = null; // 目标文件名

        /**
         * 代码段1
         OutputStream out = chSftp.put(dst, ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
         byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
         int read;
         if (out != null) {
         InputStream is = new FileInputStream(src);
         do {
         read = is.read(buff, 0, buff.length);
         if (read > 0) {
         out.write(buff, 0, read);
         }
         out.flush();
         } while (read >= 0);
         }
         */

        for(int i=0; i<files.size(); i++) {
            src = (String)files.get(i);
            int posi = src.lastIndexOf(File.separator);
            String filename =  src.substring(posi+1);
            dst = homedir + "www/"+filename;
            System.out.println(dst);
            chSftp.put(src, dst, ChannelSftp.OVERWRITE); // 代码段2
        }
        chSftp.getSession().disconnect();
        chSftp.quit();                      // close the ftp connection
        chSftp.disconnect();
        channel.closeChannel();

    /*com.unittest.SFTPTest test = new com.unittest.SFTPTest();
    Map sftpDetails = new HashMap();

    sftpDetails.put("host", "116.90.87.233");
    sftpDetails.put("username", "ksfadmin");
    sftpDetails.put("password", "KsfItsm!23");
    sftpDetails.put("port", "22");

    com.unittest.SFTPTool channel = test.getSFTPChannel();
    ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
    String homedir = chSftp.getHome();

    Class cl = ChannelSftp.class;
    Field f = cl.getDeclaredField("server_version");
    f.setAccessible(true);
    f.set(chSftp, Integer.valueOf(2));
    chSftp.setFilenameEncoding("GBK");

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.add(5, -1);

    int year = calendar.get(1);
    int month = calendar.get(2);
    String s_month = null;
    if (month < 10)
      s_month = "0" + String.valueOf(month + 1);
    else
      s_month = String.valueOf(month + 1);
    int day = calendar.get(5);
    String s_day = null;
    if (day < 10)
      s_day = "0" + String.valueOf(day);
    else
      s_day = String.valueOf(day);
    List files = new ArrayList();
    String bakfiledir = args[0];
    File backfiles = new File(bakfiledir);
    for (File tfile : backfiles.listFiles()) {
      String tt = tfile.getPath();
      if (tt.contains(String.valueOf(year) + "_" + s_month + "_" + s_day)) {
        files.add(tt);
      }
    }
    String src = null;
    String dst = null;

    for (int i = 0; i < files.size(); i++) {
      src = (String)files.get(i);
      int posi = src.lastIndexOf(File.separator);
      String filename = src.substring(posi + 1);
      dst = homedir + "/db/" + filename;
      System.out.println(dst);
      chSftp.put(src, dst, 0);
    }

    chSftp.quit();
    channel.closeChannel();*/

    }
}