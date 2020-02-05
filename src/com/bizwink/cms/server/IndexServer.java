package com.bizwink.cms.server;

import java.io.*;

import com.bizwink.indexer.*;

public class IndexServer implements iIndexServer {
    IndexDocument indexServer = null;
    PoolServer cpool;
    private String language;
    private String os;
    private String appserver;
    String indexPath;

    public void createIndex(String indexPath, String logPath, String os, String language, String appserver, PoolServer cpool) {
        this.language = language;
        this.os = os;
        this.appserver = appserver;
        this.cpool = cpool;
        this.indexPath = indexPath;

        try {
            System.out.println("启动索引服务");
            indexServer = new com.bizwink.indexer.IndexDocument(indexPath,logPath,os,language,appserver,cpool);
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
