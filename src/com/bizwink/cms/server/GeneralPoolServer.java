package com.bizwink.cms.server;

import java.io.*;
import java.sql.*;

public class GeneralPoolServer implements PoolServer {
    //private static ConnectionPool connPool = null;
    private DbConnectionBroker connPool = null;
    private String type;
    private String language;
    private String os;
    private String appserver;
    private String indexPath;
    private int publishway;
    private String customer;
    private int threadpool;
    private int writelog;
    private int uploadsize;

    public void createPool(String os, String appserver, String language, String type, String driver, String url,
                           String username, String password, int minconn, int maxconn, String logPath, String indexPath,
                           double timeout, String jndi, int publishway, String customer, int threadpool, int writelog,
                           int uploadsize) {
        this.type = type;
        this.language = language;
        this.os = os;
        this.appserver = appserver;
        this.indexPath = indexPath;
        this.publishway = publishway;
        this.customer = customer;
        this.threadpool = threadpool;
        this.writelog = writelog;
        this.uploadsize = uploadsize;

        try {
            connPool = new DbConnectionBroker(language, type, driver, url, username, password, minconn, maxconn, logPath, timeout);
            //connPool = new ConnectionPool(language, type, driver, url, username, password, minconn, maxconn, logPath, timeout);
        }
        catch (IOException ioe) {
            System.err.println("Error starting ConnectionDefaultPool: " + ioe);
            ioe.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return connPool.getConnection();
    }

    public String freeConnection(Connection conn) {
        return connPool.freeConnection(conn);
    }

    public String getType() {
        return type;
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

    public String getIndexPath() {
        return indexPath;
    }

    public int getPublishWay() {
        return publishway;
    }

    public String getCustomer() {
        return customer;
    }

    public int getThreadPool() {
        return threadpool;
    }

    public int getWriteLog() {
        return writelog;
    }

    public int getUploadSize() {
        return uploadsize;
    }
}