package com.bizwink.net.sftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.*;
import org.apache.regexp.*;
import com.bizwink.cms.util.StringUtil;

public class FtpFileToDest
{
    private int port = 22;
    private ChannelSftp sftp = null;
    public FtpFileToDest() {

    }

    //siteIP,ftpUser,ftpPasswd,localFileName,localBaseDir,remoteDocRoot
    public int transfer(String host,String user,String password,String localFileName,String fileDir,String remoteDocRoot,int big5flag)
    {
        int rtnval =0;
        String remotepath = "";
        java.io.File pubFile = new java.io.File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }

        int pos1 = localFileName.lastIndexOf(".");
        String extname = localFileName.substring(pos1+1);
        if (extname!=null) extname = extname.toLowerCase();

        try {
            remotepath = remoteDocRoot + fileDir;
            remotepath = StringUtil.replace(remotepath,java.io.File.separator,"/");
            RE regExp = new RE("/");
            String[] remoteDir = regExp.split(remotepath);
            int len = remoteDir.length;

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
                sftp.cd("/");

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
                            System.out.println("dir===" + remoteDir[i]);
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
            } catch (IOException ex) {
                ex.printStackTrace();
                rtnval = -20;
                System.out.println("Caught exception :20 " + ex.getMessage());
            }  catch (JSchException ex) {
                ex.printStackTrace();
                rtnval = -21;
                System.out.println("Caught exception :21 " + ex.getMessage());
            }  catch (SftpException ex) {
                ex.printStackTrace();
                rtnval = -23;
                System.out.println("Caught exception :23 " + ex.getMessage());
            } finally {
                if (sftp != null) {
                    try {
                        sftp.getSession().disconnect();
                        sftp.quit();                      // close the ftp connection
                        sftp.disconnect();
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

    public int deleteRemoteFile(String host, String user, String password, String FileName, String remoteDocRoot) {
        int rtnval = 0;
        try {
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
                remoteDocRoot = StringUtil.replace(remoteDocRoot, java.io.File.separator, "/");
                sftp.rm(remoteDocRoot + FileName);
                //sftp.disconnect();
            } catch (Exception ex) {
                rtnval = -24;
                System.out.println("Caught exception:2 " + ex.getMessage());
            } finally {
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