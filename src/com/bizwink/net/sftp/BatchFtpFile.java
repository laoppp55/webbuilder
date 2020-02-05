package com.bizwink.net.sftp;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import com.jcraft.jsch.*;
import org.apache.regexp.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.sitesetting.*;

public class BatchFtpFile {
    int rtnval = 0;
    private int port = 22;
    private ChannelSftp sftp = null;

    private int open(String host, String user, String password) {
        rtnval = 0;
        try {
            JSch jsch = new JSch();
            jsch.getSession(user, host, port);
            Session sshSession = jsch.getSession(user, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            //设置文件名称编码，解决中文文件名乱码问题
            Class cl = ChannelSftp.class;
            Field f =cl.getDeclaredField("server_version");
            f.setAccessible(true);
            f.set(sftp, 2);
            sftp.setFilenameEncoding("GBK");
        }  catch (JSchException ex) {
            rtnval = -21;
            System.out.println("Caught exception :21 " + ex.getMessage());
        } catch (SftpException exp) {
            exp.printStackTrace();
        } catch (NoSuchFieldException exp) {
            exp.printStackTrace();
        } catch (IllegalAccessException exp) {
            exp.printStackTrace();
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
            remotepath = StringUtil.replace(remotepath,java.io.File.separator,"/");
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(remotepath);
            int len = remoteDir.length;

            try {
                //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                //sftp.cd("/");
                sftp.cd(remoteDocRoot);
                boolean bcreated = false;
                boolean bparent = false;
                //在这儿测试远成的目录是否存在，如果不存在，则创建，并进入该层目录
                for (int i=0; i<len; i++) {
                    if (remoteDir[i].length() > 0) {
                        try {
                            sftp.cd(remoteDir[i]);
                            bcreated = true;
                        } catch (Exception e) {
                            bcreated = false;
                        }

                        if(!bcreated){
                            sftp.mkdir(remoteDir[i]);
                            sftp.cd(remoteDir[i]);
                            bcreated = true;
                        }
                    }
                }

                String destinationFileName = localFileName.substring(localFileName.lastIndexOf(java.io.File.separator) + 1, localFileName.length());
                FileInputStream fis = new FileInputStream(localFileName);
                sftp.put(fis,destinationFileName);
                // 关闭文件流
                fis.close();
            }
            catch (Exception ex) {
                rtnval = -23;
                ex.printStackTrace();
            }
        }
        catch (org.apache.regexp.RESyntaxException e) {
            System.out.println(e.getMessage());
            rtnval = -26;
        }
        return rtnval;
    }

    private void close() {
        if (sftp != null) {
            try {
                sftp.getSession().disconnect();
                sftp.quit();                      // close the ftp connection
                sftp.disconnect();
            }
            catch (Exception e) {
                e.printStackTrace();
                rtnval = -25;
            }
            finally {
                sftp = null;
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
                if (errcode == 0)
                    errcode = transferMoreFile(localFileNames, remoteDocRoot);
                close();
            }

            if (ftpInfo.getPublishway() == 1 || ftpInfo.getPublishway() == 2) {
                Copy copyHandle = new Copy();
                errcode = copyHandle.copyMoreFiles(localFileNames, remoteDocRoot);
            }
        }

        close();
        return errcode;
    }

    public int ZipFtpFile(int hostID, List localFileNames, String siteIP,String ftpUser,String ftpPasswd,String remoteDocRoot,int publishWay,int big5flag) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        int errcode = 0;

        if (publishWay == 0) {
            errcode = open(siteIP, ftpUser, ftpPasswd);
            if (errcode == 0) errcode = transferMoreFile(localFileNames, remoteDocRoot);
            close();
        }

        if (publishWay == 1 || publishWay == 2) {
            Copy copyHandle = new Copy();
            errcode = copyHandle.copyMoreFiles(localFileNames, remoteDocRoot);
        }

        return errcode;
    }
}