package com.bizwink.cms.server;

public interface iIndexServer {
    
    void createIndex(String indexPath, String logPath, String os, String language, String appserver, PoolServer cpool);

    String getLanguage();

    String getOStype();

    String getAppServer();
}
