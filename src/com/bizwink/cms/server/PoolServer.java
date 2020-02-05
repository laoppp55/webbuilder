package com.bizwink.cms.server;

import java.sql.*;

public interface PoolServer {
    void createPool(String os, String appserver, String language, String type, String driver,
                    String url, String username, String password, int minconn, int maxconn,
                    String logPath, String indexPath, double timeout, String jndi, int publishway,
                    String customer, int threadpool, int writelog, int uploadsize);

    String getLanguage();

    String getType();

    String getOStype();

    String getAppServer();

    String getIndexPath();

    String getCustomer();

    int getPublishWay();

    int getThreadPool();

    int getWriteLog();

    int getUploadSize();

    String freeConnection(Connection conn);

    Connection getConnection() throws SQLException;
}
