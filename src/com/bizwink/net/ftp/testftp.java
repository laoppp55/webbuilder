package com.bizwink.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.bizwink.cms.util.StringUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
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
public class testftp {
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
    public void connectServer(String server, String user, String password,String path) {
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(server);
            ftpClient.login(user, password);
            path= StringUtil.replace(path,java.io.File.separator,"/");
            if (path.length() != 0) {
                boolean flag = ftpClient.changeWorkingDirectory(path);
                if (flag) {
                    System.out.println("set working directory successful !!!");
                }else{
                    ftpClient.makeDirectory(path);
                    ftpClient.changeWorkingDirectory(path);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
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
            //log.info(" disconnect success !!! ");
        }
        catch (IOException e) {
            //log.info(" not disconnect !!! ");
            //log.error(e.getMessage());
        }
    }

    /** */
    /**
     * 上传本地文件到服务器
     */
    public void upload(String uploadFileName) {
        try {
            String destinationFileName = uploadFileName.substring(uploadFileName.lastIndexOf(java.io.File.separator) + 1, uploadFileName.length());
            System.out.println(destinationFileName);
            String extname = destinationFileName.substring(destinationFileName.lastIndexOf(".") + 1);
            if (extname!=null) extname = extname.toLowerCase();
            if (extname.equals("xls")||extname.equals("pptx")||extname.equals("ppt")||extname.equals("docx")||extname.equals("doc") ||
                    extname.equals("flv")|| extname.equals("mp4")|| extname.equals("pdf")||extname.equals("rar")||extname.equals("zip"))
            {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            } else if (extname.equals("jpg")||extname.equals("jpeg")||extname.equals("gif")|| extname.equals("png")||extname.equals("bmp")){
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            } else {
                ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
            }

            FileInputStream fis = new FileInputStream(uploadFileName);
            boolean flag = ftpClient.storeFile(destinationFileName, fis);
            // 关闭文件流
            fis.close();
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

    /** */
    /**
     * 从服务器上删除文件
     */
    public void delete(String filename) {
        try {
            ftpClient.deleteFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** *//**
     * 测试函数
     *  */
    public static void main(String[] args) {
        //localFileName=/data/coosite/webbuilder/sites/huiyuan_coosite_com/case/20140530/190007.shtml
        //fileDir=/case/20140530/
        //remoteDocRoot=/usr/local/coosite/sites/huiyuan

        FtpFileToDest fd1 = new FtpFileToDest();
        fd1.transfer("192.168.1.54","csinfo","Zaq!2w3e4r","/data/coosite/webbuilder/WEB-INF/server-config.wsdd","/case/20140530","/sites/laoppp10",0);
        //testftp fd = new testftp();
        //fd.connectServer("116.90.87.164", "csinfo", "Zaq!2w3e4r","/usr/local/coosite/sites/huiyuan");
        //fd.upload("C:\\Apache2.2\\INSTALL.txt");
        //fd.download();
        //fd.closeConnect();
    }

}
