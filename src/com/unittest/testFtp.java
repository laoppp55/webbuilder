package com.unittest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bizwink.cms.util.Encrypt;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
//import common.Logger;

/** */

/**
 * <p>Title: FtpHandle</p>
 * <p/>
 * <p>Description: ftp客户端类(使用org.apache.commons.net.ftp包),方便</p>
 * <p/>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p/>
 * <p>Company: wri</p>
 *
 * @author javatang
 * @version 1.0
 */
public class testFtp {
    //private Logger log = Logger.getLogger(testftp.class);
    //  ftp客户端
    FTPClient ftpClient;
    //  文件列表
    FTPFile[] fileList;

    /** *//** */
    /** */
    /**
     * @server：服务器名字
     * @user：用户名
     * @password：密码
     * @path：服务器上的路径
     */
    public void connectServer(String server, String user, String password,
                              String path) {
        try {
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("gbk");
            ftpClient.connect(server);
            ftpClient.login(user, password);
            String temp = ftpClient.printWorkingDirectory();

            System.out.println("login success !!!=" + temp);
            if (path.length() != 0) {
                boolean flag = ftpClient.changeWorkingDirectory(path);
                if (flag) {
                    System.out.println("set working directory successful !!!");
                }else{//如果目录不存在则建立目录并进入改目录
                    ftpClient.makeDirectory(path);
                    ftpClient.changeWorkingDirectory(path);
                }
            }
        }
        catch (IOException e) {
            System.out.println("not login !!!");
        }
    }

    /** *//** */
    /** */
    /**
     * 关闭连接
     */
    public void closeConnect() {
        try {
            ftpClient.disconnect();
        }
        catch (IOException e) {
        }
    }

    /** */
    /**
     * 上传本地文件到服务器
     */
    public void upload(String uploadFileName) {
        try {
            File uploadFile = new File(uploadFileName);
            File[] fileList = uploadFile.listFiles();
            if(fileList==null){
                System.out.println("目录内容为空");
                return;
            }

            for (int i = 0; i < fileList.length; i++) {
                System.out.println("file==" + fileList[i]);
                FileInputStream fis = new FileInputStream(fileList[i]);
                String destinationFileName = fileList[i].getName();
                boolean flag = ftpClient.storeFile(destinationFileName, fis);
                fis.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println(" not upload !!!");
        }
    }

    /** */
    /**
     * 从服务器下载文件到本地
     */
    public void download() {
        try {
            fileList = ftpClient.listFiles();
            ftpClient.makeDirectory(" zjp "); // 在服务器上创建目录(测试用,可删除)
            ftpClient.removeDirectory(" zjp "); // 在服务器上删除词此目录,注意该目录下为空(测试用,可删除)
            for (int i = 0; i < fileList.length; i++) {
                String name = fileList[i].getName();
                File temp_file = new File(" c:\\" + " temp_ " + name);
                File dest_file = new File(" c:\\" + name);
                FileOutputStream fos = new FileOutputStream(temp_file);
                // 从服务器上下载文件
                boolean flag = ftpClient.retrieveFile(name, fos);
                // 关闭文件流
                fos.close();
                if (flag) {
                    // 本地rename,前提是先关闭文件流
                    temp_file.renameTo(dest_file);
                    //log.info(" download success !!! ");
                }
            }
        }
        catch (IOException e) {
            //log.info(" not download !!! ");
            //log.error(e.getMessage());
        }
    }

    /** *//**
     * 测试函数
     *  */
    public static void main(String[] args) {
        String pwd = "Song1966!23";
        try {
            String password = Encrypt.md5(pwd.getBytes());
            System.out.println("passwd==" + password);
        } catch(Exception exp) {

        }
        //testFtp fd = new testFtp();
        //fd.connectServer("116.90.87.164", "csinfo", "Zaq!2w3e4r","/sites/petersong/images");
        //fd.upload("C:\\Document\\11\\");
        //fd.download();
        //fd.closeConnect();
    }
}
