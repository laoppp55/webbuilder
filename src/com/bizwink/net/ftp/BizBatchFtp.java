package com.bizwink.net.ftp;

import com.bizwink.cms.sitesetting.FtpInfo;
import com.bizwink.cms.sitesetting.FtpSetting;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.cms.util.Copy;
import com.bizwink.cms.util.StringUtil;
import org.apache.regexp.RE;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by petersong on 17-2-4.
 */
public class BizBatchFtp {
    int rtnval = 0;
    com.bizwink.net.ftp.FTPClient ftp = null;

    private String mode = "ASCII";//other value is "BINARY"
    private String connMode = "PASV";//other value is "PASV"

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

    private int open(String host, String user, String password) {
        rtnval = 0;
        try {
            ftp = new com.bizwink.net.ftp.FTPClient(host, 21);
            ftp.setTimeout(20000);
            ftp.login(user, password);
            ftp.setTimeout(0);
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

    private int transfer(String localFileName, String fileDir, String remoteDocRoot) {
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
                    ftp.setType(FTPTransferType.BINARY);
                } else if (mode.equalsIgnoreCase("ASCII")) {
                    ftp.setType(FTPTransferType.ASCII);
                } else {
                    System.out.println("Unknown transfer type: " + mode);
                    return -21;
                }

                // PASV or ACTIVE ?
                if (connMode.equalsIgnoreCase("PASV")) {
                    ftp.setConnectMode(FTPConnectMode.PASV);
                } else if (connMode.equalsIgnoreCase("ACTIVE")) {
                    ftp.setConnectMode(FTPConnectMode.ACTIVE);
                } else {
                    System.out.println("Unknown connect mode: " + connMode);
                    return -22;
                }

                // change dir
                ftp.chdir("/");
                ftp.chdir(remoteDocRoot);

                //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                for (int i = 0; i < len; i++) {
                    if (remoteDir[i].length() > 0) {
                        ftp.mkdir(remoteDir[i]);
                        ftp.chdir(remoteDir[i]);
                    }
                }
                ftp.put(localFileName, realFileName);
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

    public int batchFtpFile(String pubsite, List localFileNames, int siteid, String fileDir, int big5flag) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        int errcode = 0;
        List list;
        try {
            list = FtpSetting.getInstance().getFtpInfos(siteid);
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
                System.out.println("open ftp operation!!!");
                if (errcode == 0)
                    errcode = transferMoreFile(localFileNames, fileDir, remoteDocRoot);
                close();
            }

            if (ftpInfo.getPublishway() == 1 || ftpInfo.getPublishway() == 2) {
                Copy copyHandle = new Copy();
                System.out.println("open copy operation!!!");
                errcode = copyHandle.copyMoreFiles(localFileNames, fileDir, remoteDocRoot);
            }
        }
        return errcode;
    }
}
