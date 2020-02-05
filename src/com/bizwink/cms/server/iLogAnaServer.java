package com.bizwink.cms.server;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-31
 * Time: 19:28:30
 * To change this template use File | Settings | File Templates.
 */
public interface iLogAnaServer {
    void createAnaServer(String indexPath, String logPath, String os, String language, String appserver, PoolServer cpool);
    
    String getLanguage();

    String getOStype();

    String getAppServer();
}
