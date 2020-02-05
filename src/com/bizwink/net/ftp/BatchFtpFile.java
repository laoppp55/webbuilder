package com.bizwink.net.ftp;

import java.io.*;
import java.util.*;

import org.apache.regexp.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.sitesetting.*;

public class BatchFtpFile {
    int rtnval = 0;
    it.sauronsoftware.ftp4j.FTPClient ftpClient = null;

    private int open(String host, String user, String password) {
        rtnval = 0;
        try {
            ftpClient = new it.sauronsoftware.ftp4j.FTPClient();
            ftpClient.setCharset("GBK");
            ftpClient.connect("192.168.1.54",21);
            ftpClient.login(user, password);
        } catch (Exception exp) {
            rtnval = -23;
            exp.printStackTrace();
        }

        return rtnval;
    }

    private int transfer(String localFileName, String fileDir, String remoteDocRoot) {
        File pubFile = new File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }

        try {
            String remotepath = fileDir;
            remotepath = StringUtil.replace(remotepath,java.io.File.separator,"/");
            if (remotepath.startsWith("/")) remotepath = remotepath.substring(1);
            if (remotepath.endsWith("/")) remotepath = remotepath.substring(0,remotepath.length()-1);
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(remotepath);
            int len = remoteDir.length;
            ftpClient.changeDirectory(remoteDocRoot);

            //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
            for (int i = 0; i < len; i++) {
                String[] tt = ftpClient.listNames();
                boolean exist_dir_flag = false;
                //如果目录存在进入到下一层目录
                for(int j=0;j<tt.length;j++) {
                    if (remoteDir[i].equalsIgnoreCase(tt[j])) {
                        ftpClient.changeDirectory(remoteDir[i]);
                        exist_dir_flag = true;
                        break;
                    }
                }

                //如果目录不存在，创建该目录并进入到该目录
                if (exist_dir_flag == false) {
                    ftpClient.createDirectory(remoteDir[i]);
                    ftpClient.changeDirectory(remoteDir[i]);
                }
            }

/*
            for (int i=0; i<len; i++){
                if (remoteDir[i].length() > 0){
                    boolean flag = ftpClient.changeDirectory(remoteDir[i]);
                    if (!flag) {
                        ftpClient.createDirectory(remoteDir[i]);
                        ftpClient.changeDirectory(remoteDir[i]);
                    } else {
                        ftpClient.changeDirectory(remoteDir[i]);
                    }
                }
            }
*/

            //String destinationFileName = localFileName.substring(localFileName.lastIndexOf(java.io.File.separator) + 1, localFileName.length());
            File fis = new File(localFileName);
            ftpClient.upload(fis);
        }
        catch (Exception ex) {
            rtnval = -23;
            System.out.println("Caught exception :1 " + ex.getMessage());
        }

        return rtnval;
    }

    private void close() {
        if (ftpClient != null) {
            try {
                ftpClient.disconnect(true); // close the ftp connection
            }
            catch (Exception e) {
                e.printStackTrace();
                rtnval = -25;
            }
            finally {
                ftpClient = null;
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

    public int ZipFtpFile(int hostID, List localFileNames, String siteIP,String ftpUser,String ftpPasswd,String remoteDocRoot,int publishWay, int big5flag) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        int errcode = 0;

        if (publishWay == 0) {
            errcode = open(siteIP, ftpUser, ftpPasswd);
            if (errcode == 0)  errcode = transferMoreFile(localFileNames, remoteDocRoot);
            close();
        }

        if (publishWay == 1 || publishWay == 2) {
            Copy copyHandle = new Copy();
            errcode = copyHandle.copyMoreFiles(localFileNames, remoteDocRoot);
        }

        return errcode;
    }

    public int ZipFtpFile(int hostID, List localFileNames, String siteIP,String ftpUser,String ftpPasswd,String remoteDocRoot,String fileDir,int publishWay, int big5flag) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        int errcode = 0;

        if (publishWay == 0) {
            errcode = open(siteIP, ftpUser, ftpPasswd);
            if (errcode == 0)  errcode = transferMoreFile(localFileNames,fileDir, remoteDocRoot);
            close();
        }

        if (publishWay == 1 || publishWay == 2) {
            Copy copyHandle = new Copy();
            errcode = copyHandle.copyMoreFiles(localFileNames,fileDir, remoteDocRoot);
        }

        return errcode;
    }

    public int batchFtpFile(int hostID, List localFileNames, int siteid, int big5flag) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        int errcode = 0;
        List list = new ArrayList();
        try {
            if (hostID == 0)
                list = FtpSetting.getInstance().getFtpInfos(siteid);
            else
                list.add(FtpSetting.getInstance().getFtpInfo(hostID));
        }
        catch (SiteInfoException e) {
            errcode = -31;
            e.printStackTrace();
            return errcode;
        }

        int len = list.size();
        for (int i = 0; i < len; i++) {
            FtpInfo ftpInfo = (FtpInfo) list.get(i);
            String siteIP = ftpInfo.getIp();
            String remoteDocRoot = ftpInfo.getDocpath();
            String ftpUser = ftpInfo.getFtpuser();
            String ftpPasswd = ftpInfo.getFtppwd();

            if (ftpInfo.getPublishway() == 0) {
                errcode = open(siteIP, ftpUser, ftpPasswd);
                if (errcode == 0)  errcode = transferMoreFile(localFileNames, remoteDocRoot);
                close();
            }

            if (ftpInfo.getPublishway() == 1 || ftpInfo.getPublishway() == 2) {
                Copy copyHandle = new Copy();
                errcode = copyHandle.copyMoreFiles(localFileNames, remoteDocRoot);
            }
        }

        return errcode;
    }

    /** *//**
     * 测试函数
     *  */
    public static void main(String[] args) {
        //BatchFtpFile fd = new BatchFtpFile();
        UnZip unZip = new UnZip();
        unZip.UnZip("C:\\Document\\Document.zip","C:\\Document\\11","petersong.coosite.com",28,0);
        //fd.open("116.90.87.164", "csinfo", "Zaq!2w3e4r");
        //fd.transfer("C:\\Document\\11\\易派客例会资料.docx","images","/sites/petersong");
        //fd.close();
    }
}