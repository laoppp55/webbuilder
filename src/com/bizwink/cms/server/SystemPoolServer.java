package com.bizwink.cms.server;

import com.bizwink.util.SpringInit;
import org.springframework.context.ApplicationContext;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;

public class SystemPoolServer implements PoolServer {
    private DataSource connPool = null;
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
                           String username, String password, int minconn, int maxconn, String logPath,
                           String indexPath, double timeout, String jndi, int publishway, String customer,
                           int threadpool, int writelog, int uploadsize) {
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

      //  ApplicationContext appContext = SpringInit.getApplicationContext();
        //connPool = (DataSource) appContext.getBean("myDataSource");

        try {
            if (jndi == null || jndi.trim().length() == 0) jndi = "webbuilder";
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            connPool = (DataSource) env.lookup("jdbc/" + jndi);
            if (connPool == null) System.out.println("NOT FOUND JNDI NAME");
        }
        catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        return connPool.getConnection();
    }

    public String freeConnection(Connection conn) {
        try {
            if (conn != null) conn.close();
        }
        catch (SQLException e) {
            System.out.println("Error in closing the pooled connection " + e.toString());
        }
        return "conn";
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
