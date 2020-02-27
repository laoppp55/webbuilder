package com.bizwink.net.ftp;

import java.io.*;
import java.util.*;

import com.bizwink.util.Copy;
import freemarker.template.utility.StringUtil;
import org.apache.commons.net.ftp.*;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.regexp.*;

public class BatchFtpFile {
    int rtnval = 0;
    FTPClient ftp = null;
    com.bizwink.net.ftp.FTPClient ftp11 = null;

    private String mode = "ASCII";//other value is "BINARY"
    //private String connMode = "ACTIVE";//other value is "PASV"
    private String connMode = "PASV";//other value is "PASV"

    private int open(String host, String user, String password) {
        rtnval = 0;
        try {
            ftp = new org.apache.commons.net.ftp.FTPClient();
            ftp.connect(host);
            ftp.login(user, password);
        }
        catch (IOException ex) {
            rtnval = -23;
            System.out.println("Caught exception :1 " + ex.getMessage() + " connect remote host failed");
        }

        return rtnval;
    }

    private int transfer(String localFileName, String fileDir, String remoteDocRoot) {
        File pubFile = new File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }

        String tempFileName = StringUtil.replace(localFileName, "/", File.separator);
        int pos = tempFileName.lastIndexOf(File.separator);
        String realFileName = localFileName.substring(pos + 1);

        int pos1 = localFileName.lastIndexOf(".");
        String extname = localFileName.substring(pos1+1);
        if (extname!=null) extname = extname.toLowerCase();
        rtnval = 0;

        try {
            //String remotepath = remoteDocRoot + fileDir;
            String remotepath = fileDir;
            remotepath = StringUtil.replace(remotepath,java.io.File.separator,"/");
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(remotepath);
            int len = remoteDir.length;

            try {
                if (extname.equals("xls")||extname.equals("pptx")||extname.equals("ppt")||extname.equals("docx")||extname.equals("doc") ||
                        extname.equals("flv")|| extname.equals("mp4")|| extname.equals("pdf")||extname.equals("rar")||extname.equals("zip"))
                {
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                } else if (extname.equals("jpg")||extname.equals("jpeg")||extname.equals("gif")|| extname.equals("png")||extname.equals("bmp")){
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                } else {
                    ftp.setFileType(FTP.ASCII_FILE_TYPE);
                }

                //ftp.changeWorkingDirectory("/");
                ftp.changeWorkingDirectory(remoteDocRoot);
                //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                for (int i=0; i<len; i++)
                {
                    if (remoteDir[i].length() > 0)
                    {
                        boolean flag = ftp.changeWorkingDirectory(remoteDir[i]);
                        if (!flag) {
                            ftp.makeDirectory(remoteDir[i]);
                            ftp.changeWorkingDirectory(remoteDir[i]);
                        } else {
                            ftp.changeWorkingDirectory(remoteDir[i]);
                        }
                    }
                }

                String destinationFileName = localFileName.substring(localFileName.lastIndexOf(java.io.File.separator) + 1, localFileName.length());
                FileInputStream fis = new FileInputStream(localFileName);
                boolean flag = ftp.storeFile(destinationFileName, fis);
                // 关闭文件流
                fis.close();
            }
            catch (IOException ex) {
                rtnval = -23;
                System.out.println("Caught exception :1 " + ex.getMessage());
            }
        }
        catch (org.apache.regexp.RESyntaxException e) {
            System.out.println(e.getMessage());
            rtnval = -26;
        }
        return rtnval;
    }

    private void close() {
        if (ftp != null) {
            try {
                ftp.quit(); // close the ftp connection
            }
            catch (Exception e) {
                e.printStackTrace();
                rtnval = -25;
            }
            finally {
                ftp = null;
            }
        }
    }

    private int open11(String host, String user, String password) {
        rtnval = 0;
        try {
            ftp11 = new com.bizwink.net.ftp.FTPClient(host, 21);
            ftp11.setTimeout(20000);
            ftp11.login(user, password);
            ftp11.setTimeout(0);
        }
        catch (IOException ex) {
            rtnval = -23;
            System.out.println("Caught exception :1 " + ex.getMessage() + " connect remote host failed");
        }
        catch (FTPException ex) {
            rtnval = -24;
            System.out.println("Caught exception:2 " + ex.getMessage() + " connect remote host failed");
        }
        return rtnval;
    }

    private int transfer11(String localFileName, String fileDir, String remoteDocRoot) {
        File pubFile = new File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }

        String tempFileName = StringUtil.replace(localFileName, "/", File.separator);
        int pos = tempFileName.lastIndexOf(File.separator);
        String realFileName = localFileName.substring(pos + 1);

        int pos1 = localFileName.lastIndexOf(".");
        String fileExt = localFileName.substring(pos1 + 1);
        if (fileExt != null) {
            fileExt = fileExt.toUpperCase();
            if (!(fileExt.equals("PHP") || fileExt.equals("ASP") || fileExt.equals("JSP") || fileExt.equals("HTML") || fileExt.equals("HTM") || fileExt.equals("TXT"))) {
                this.mode = "BINARY";
            }
        }
        rtnval = 0;

        try {
            fileDir = StringUtil.replace(fileDir, File.separator, "/");
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(fileDir);
            int len = remoteDir.length;

            try {
                // binary transfer
                if (mode.equalsIgnoreCase("BINARY")) {
                    ftp11.setType(FTPTransferType.BINARY);
                } else if (mode.equalsIgnoreCase("ASCII")) {
                    ftp11.setType(FTPTransferType.ASCII);
                } else {
                    System.out.println("Unknown transfer type: " + mode);
                    return -21;
                }

                // PASV or ACTIVE ?
                if (connMode.equalsIgnoreCase("PASV")) {
                    ftp11.setConnectMode(FTPConnectMode.PASV);
                } else if (connMode.equalsIgnoreCase("ACTIVE")) {
                    ftp11.setConnectMode(FTPConnectMode.ACTIVE);
                } else {
                    System.out.println("Unknown connect mode: " + connMode);
                    return -22;
                }

                // change dir
                ftp11.chdir("/");
                ftp11.chdir(remoteDocRoot);

                //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                for (int i = 0; i < len; i++) {
                    if (remoteDir[i].length() > 0) {
                        ftp11.mkdir(remoteDir[i]);
                        ftp11.chdir(remoteDir[i]);
                    }
                }
                ftp11.put(localFileName, realFileName);
            }
            catch (IOException ex) {
                rtnval = -23;
                System.out.println("Caught exception :1 " + ex.getMessage());
            }
            catch (FTPException ex) {
                rtnval = -24;
                System.out.println("Caught exception:2 " + ex.getMessage());
            }
        }
        catch (org.apache.regexp.RESyntaxException e) {
            System.out.println(e.getMessage());
            rtnval = -26;
        }
        return rtnval;
    }

    private void close11() {
        if (ftp != null) {
            try {
                ftp.quit(); // close the ftp connection
            }
            catch (Exception e) {
                e.printStackTrace();
                rtnval = -25;
            }
            finally {
                ftp = null;
            }
        }
    }

    //修改FTP的软件包，以下程序不需要改变
    private int transferMoreFile(List localFileNames, String remoteDocRoot) {
        if (localFileNames == null) {
            return -1;
        }
        int fileNum = localFileNames.size();
        if (fileNum < 1) {
            return -1;
        }

        int retCode = 0;
        for (int i = 0; i < fileNum; i++) {
            String temp = (String) localFileNames.get(i);
            String filePath = temp.substring(0, temp.lastIndexOf(","));
            String fileDir = temp.substring(temp.lastIndexOf(",") + 1);
            retCode = transfer(filePath, fileDir, remoteDocRoot);
            if (retCode != 0) {
                return retCode;
            }
        }
        return retCode;
    }

    private int transferMoreFile(List localFileNames, String fileDir, String remoteDocRoot) {
        if (localFileNames == null) {
            return -1;
        }
        int fileNum = localFileNames.size();
        if (fileNum < 1) {
            return -1;
        }

        int retCode = 0;
        for (int i = 0; i < fileNum; i++) {
            String sfilename = (String) localFileNames.get(i);
            retCode = transfer(sfilename, fileDir, remoteDocRoot);
            if (retCode != 0) {
                return retCode;
            }
        }
        return retCode;
    }
}