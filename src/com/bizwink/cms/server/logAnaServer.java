package com.bizwink.cms.server;

import com.bizwink.webtrend.loganalyzer;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-31
 * Time: 19:22:58
 * To change this template use File | Settings | File Templates.
 */
public class logAnaServer  implements iLogAnaServer {
    loganalyzer logAnaServer = null;
    PoolServer cpool;
    private String language;
    private String os;
    private String appserver;
    String indexPath;

    public void createAnaServer(String indexPath, String logPath, String os, String language, String appserver, PoolServer cpool) {
        this.language = language;
        this.os = os;
        this.appserver = appserver;
        this.cpool = cpool;
        this.indexPath = indexPath;

        try {
            System.out.println("启动LOG分析服务");
            logAnaServer = new loganalyzer(logPath,cpool);
        }
        catch (IOException ioe) {
            System.err.println("Error starting Index Server: " + ioe);
            ioe.printStackTrace();
        }
    }

    public String getLanguage() {
        return language;
    }

    public String getOStype() {
        return os;
    }

    public String getAppServer() {
        return appserver;
    }

}
