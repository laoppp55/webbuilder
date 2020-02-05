package com.bizwink.net.sftp;

import com.jcraft.jsch.*;
import java.util.Properties;

/**
 * Created by petersong on 17-2-4.
 */
public class SFTPDefaultWorkingDirectory {
    int rtnval = 0;
    private ChannelSftp sftp = null;

    private int open(String host, String user, String password,int port) {
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
        }  catch (JSchException ex) {
            rtnval = -21;
            System.out.println("Caught exception :21 " + ex.getMessage());
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

    public String getDefaultWorkingDirectory(String IP,String username,String passwd,int port) {
        int errcode = open(IP, username, passwd,port);
        String homedir = null;
        try {
            homedir = sftp.getHome();
        } catch (SftpException exp) {
            exp.printStackTrace();
        }
        close();

        return homedir;
    }
}
