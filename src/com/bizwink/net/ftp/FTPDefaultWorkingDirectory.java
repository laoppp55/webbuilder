package com.bizwink.net.ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

/**
 * Created by petersong on 17-2-4.
 */
public class FTPDefaultWorkingDirectory {
    //private Logger log = Logger.getLogger(testftp.class);
    //  ftp客户端
    FTPClient ftpClient;

    public String getDefaultWorkingDirectory(String IP,String username,String passwd,int port) {
        String homedir = null;
        try {
            ftpClient = new org.apache.commons.net.ftp.FTPClient();
            ftpClient.connect(IP);
            ftpClient.login(username, passwd);
            ftpClient.setDefaultPort(port);
            homedir = ftpClient.printWorkingDirectory();
            ftpClient.quit();
        }
        catch (IOException e) {
            System.out.println("not login !!!");
        }

        return homedir;
    }
}
