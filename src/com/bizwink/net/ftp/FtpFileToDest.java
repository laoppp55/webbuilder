package com.bizwink.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.bizwink.cms.util.StringUtil;
import org.apache.regexp.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpFileToDest
{
    private String mode = "ASCII";//other value is "BINARY"
    //private String connMode = "ACTIVE";//other value is "PASV"
    private String connMode = "PASV";//other value is "PASV"

    public FtpFileToDest() {

    }

    //siteIP,ftpUser,ftpPasswd,localFileName,localBaseDir,remoteDocRoot
    public int transfer11(String host,String user,String password,String localFileName,String fileDir,String remoteDocRoot,int big5flag)
    {
        int rtnval =0;
        String remotepath = "";
        java.io.File pubFile = new java.io.File(localFileName);
        if (!pubFile.exists())
        {
            return -1;
        }

        int pos1 = localFileName.lastIndexOf(".");
        String extname = localFileName.substring(pos1+1);
        if (extname!=null) extname = extname.toLowerCase();

        try
        {
            remotepath = remoteDocRoot + StringUtil.replace(fileDir,"\\","/");
            remotepath = StringUtil.replace(remotepath,java.io.File.separator,"/");
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(remotepath);
            int len = remoteDir.length;
            FTPClient ftp = null;

            try
            {
                ftp = new org.apache.commons.net.ftp.FTPClient();
                ftp.connect(host);
                ftp.login(user, password);
                if (extname.equals("xls")||extname.equals("pptx")||extname.equals("ppt")||extname.equals("docx")||extname.equals("doc") ||
                        extname.equals("flv")|| extname.equals("mp4")|| extname.equals("pdf")||extname.equals("rar")||extname.equals("zip"))
                {
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                } else if (extname.equals("jpg")||extname.equals("jpeg")||extname.equals("gif")|| extname.equals("png")||extname.equals("bmp")){
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);
                } else {
                    ftp.setFileType(FTP.ASCII_FILE_TYPE);
                }

                ftp.changeWorkingDirectory("/");
                //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                for (int i=0; i<len; i++) {
                    if (remoteDir[i].length() > 0){
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
                ftp.pasv();
                boolean flag = ftp.storeFile(destinationFileName, fis);
                ftp.disconnect();
                fis.close();
            } catch (IOException ex) {
                rtnval = -23;
                System.out.println("Caught exception :1 " + ex.getMessage());
            } finally {
                if (ftp != null) {
                    try {
                        ftp.quit(); // close the ftp connection
                    } catch (Exception e) {
                        e.printStackTrace();
                        rtnval=-25;
                    }
                }
            }
        } catch (org.apache.regexp.RESyntaxException e) {
            System.out.println(e.getMessage());
            rtnval =-26;
        }

        return rtnval;
    }

    public int deleteRemoteFile11(String host, String user, String password, String FileName, String remoteDocRoot) {
        int rtnval = 0;
        try {
            testftp fd = new testftp();
            try {
                fd.connectServer(host, user, password,remoteDocRoot);
                remoteDocRoot = StringUtil.replace(remoteDocRoot, java.io.File.separator, "/");
                fd.delete(remoteDocRoot + FileName);

                //ftp = new FTPClient(host, 21);
                //ftp.setTimeout(20000);
                //ftp.login(user, password);
                //ftp.setTimeout(0);

                //remoteDocRoot = StringUtil.replace(remoteDocRoot, java.io.File.separator, "/");
                //ftp.delete(remoteDocRoot + FileName);
            } catch (Exception ex) {
                rtnval = -24;
                System.out.println("Caught exception:2 " + ex.getMessage());
            } finally {
                if (fd != null) {
                    try {
                        fd.closeConnect(); // close the ftp connection
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        rtnval = -25;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            rtnval = -26;
        }

        return rtnval;
    }

    //siteIP,ftpUser,ftpPasswd,localFileName,localBaseDir,remoteDocRoot
    public int transfer(String host, String user, String password, String localFileName, String fileDir, String remoteDocRoot, int big5flag) {
        localFileName = StringUtil.replace(localFileName, "/", File.separator);
        localFileName = StringUtil.replace(localFileName, "\\", File.separator);
        fileDir = StringUtil.replace(fileDir, "/", File.separator);
        fileDir = StringUtil.replace(fileDir, "\\", File.separator);
        java.io.File pubFile = new java.io.File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }

        int pos = localFileName.lastIndexOf(java.io.File.separator);
        String realFileName = localFileName.substring(pos + 1);

        int pos1 = localFileName.lastIndexOf(".");
        String fileExt = localFileName.substring(pos1 + 1);
        if (fileExt != null) {
            fileExt = fileExt.toUpperCase();
            if (!(fileExt.equals("PHP") || fileExt.equals("ASP") || fileExt.equals("JSP") || fileExt.equals("HTML") || fileExt.equals("HTM") || fileExt.equals("TXT"))) {
                this.mode = "BINARY";
            }
        }
        int rtnval = 0;

        try {
            fileDir = StringUtil.replace(remoteDocRoot + fileDir, java.io.File.separator, "/");
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(fileDir);
            int len = remoteDir.length;

            com.bizwink.net.ftp.FTPClient ftp = null;
            try {
                ftp = new com.bizwink.net.ftp.FTPClient(host, 21);
                ftp.setTimeout(20000);
                ftp.login(user, password);
                ftp.setTimeout(0);

                // binary transfer
                if (mode.equalsIgnoreCase("BINARY")) {
                    ftp.setType(FTPTransferType.BINARY);
                } else if (mode.equalsIgnoreCase("ASCII")) {
                    ftp.setType(FTPTransferType.ASCII);
                } else {
                    System.out.println("Unknown transfer type: " + mode);
                    return -21;
                }

                // PASV or active?
                if (connMode.equalsIgnoreCase("PASV")) {
                    ftp.setConnectMode(FTPConnectMode.PASV);
                } else if (connMode.equalsIgnoreCase("ACTIVE")) {
                    ftp.setConnectMode(FTPConnectMode.ACTIVE);
                } else {
                    System.out.println("Unknown connect mode: " + connMode);
                    return -22;
                }

                //ftp to upload the simpified file
                ftp.chdir("/");
                //ftp.chdir(remoteDocRoot);
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
            finally {
                if (ftp != null) {
                    try {
                        ftp.quit(); // close the ftp connection
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        rtnval = -25;
                    }
                }
            }
        }
        catch (org.apache.regexp.RESyntaxException e) {
            System.out.println(e.getMessage());
            rtnval = -26;
        }

        return rtnval;
    }

    public int deleteRemoteFile(String host, String user, String password, String FileName, String remoteDocRoot) {
        int rtnval = 0;
        try {
            com.bizwink.net.ftp.FTPClient ftp = null;
            try {
                ftp = new com.bizwink.net.ftp.FTPClient(host, 21);
                ftp.setTimeout(20000);
                ftp.login(user, password);
                ftp.setTimeout(0);

                remoteDocRoot = StringUtil.replace(remoteDocRoot, java.io.File.separator, "/");
                ftp.delete(remoteDocRoot + FileName);
            }
            catch (IOException ex) {
                rtnval = -23;
                System.out.println("Caught exception :1 " + ex.getMessage());
            }
            catch (FTPException ex) {
                rtnval = -24;
                System.out.println("Caught exception:2 " + ex.getMessage());
            }
            finally {
                if (ftp != null) {
                    try {
                        ftp.quit(); // close the ftp connection
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        rtnval = -25;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            rtnval = -26;
        }

        return rtnval;
    }
}