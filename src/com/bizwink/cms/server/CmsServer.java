package com.bizwink.cms.server;

import com.bizwink.cms.multimedia.MultimediaPeer;
import java.io.*;
import java.sql.*;
import javax.sql.*;

public class CmsServer implements ICmsConstants {
    final static boolean DEBUG = true;
    public static String sitesrootpath = "";                    //站点群的发布位置
    public static String downloadpath = "";
    public static String lang = "";

    // singleton instance
    private static CmsServer singleton = new CmsServer();

    // server status
    boolean isInitialized = false;
    public static Process process = null;

    // server properties
    FileProps props;

    // factory
    IFactory factory;
    PoolServer connPool;
    IndexServer indexer;
    MultimediaPeer multimedia;
    ArchiveServer archiver;

    private CmsServer() {
    }

    public static synchronized CmsServer getInstance() {
        singleton.init();
        return singleton;
    }

    public boolean getInit() {
        return isInitialized;
    }

    void initEnv() {
        System.out.println("获取环境设置参数" + System.currentTimeMillis());
        props = new FileProps("com/bizwink/cms/server/config.properties");
        System.out.println("获取环境设置参数结束" + System.currentTimeMillis());
    }

    void initDB() {
        System.out.println("建立数据库连接池" + System.currentTimeMillis());
        connPool = createConectionPool("main");
        System.out.println("建立数据库连接池结束" + System.currentTimeMillis());
    }

    void initMgr() {
        System.out.println("初始化所有工厂库模块" + System.currentTimeMillis());
        factory = new com.bizwink.cms.server.Factory(connPool);
        System.out.println("初始化所有工厂库模块结束" + System.currentTimeMillis());
    }

    void initMultimedia(){
        System.out.println("初始化视频文件转换模块" + System.currentTimeMillis());
        multimedia = createMultimedia("main");
        System.out.println("初始化视频文件转换模块结束" + System.currentTimeMillis());
    }

    MultimediaPeer createMultimedia(String name){
        MultimediaServer multimediapeer = new MultimediaServer();
        String logPath = props.getProperty(name + ".db.logPath");
        if (!isInitialized) multimediapeer.createMultimedia(logPath, connPool);

        return multimedia;
    }

    void initIndexServer() {
        indexer = createIndexer("main");
    }

    IndexServer createIndexer(String name) {
        IndexServer indexServer;
        indexServer = new IndexServer();

        String language = props.getProperty(name + ".db.language");
        String os = props.getProperty(name + ".os.type");
        String appServer = props.getProperty(name + ".appserver");
        String indexPath = props.getProperty(name + ".indexPath");
        String logPath = props.getProperty(name + ".db.logPath");

        if (!isInitialized) indexServer.createIndex(indexPath, logPath, os, language, appServer, connPool);

        return indexServer;
    }

    void initArchiveServer() {
        archiver = createArchiver("main");
    }

    ArchiveServer createArchiver(String name) {
        ArchiveServer archiveServer = new ArchiveServer();
        String logPath = props.getProperty(name + ".db.logPath");
        if (!isInitialized) archiveServer.createArchiver(logPath, connPool);
        return archiveServer;
    }

    public synchronized void init() {
        if (!isInitialized) {
            initEnv();                      //初始化环境变量
            initDB();                       //启动数据库连接池进程
            initMgr();
            //initIndexServer();              //自动索引进程
            initMultimedia();               //多媒体转换进程
            //initArchiveServer();          //自动归档进程
            isInitialized = true;
        }
    }

    public IFactory getFactory() {
        return factory;
    }

    PoolServer createConectionPool(String dbName) {
        int publishway = 0;
        PoolServer poolServer;

        String language = props.getProperty(dbName + ".db.language");
        String os = props.getProperty(dbName + ".os.type");
        String appServer = props.getProperty(dbName + ".appserver");
        String type = props.getProperty(dbName + ".db.type");
        String cpooltype = props.getProperty(dbName + ".db.cpool");
        String driver = props.getProperty(dbName + ".db.driver");
        String url = props.getProperty(dbName + ".db.url");
        String username = props.getProperty(dbName + ".db.username");
        String password = props.getProperty(dbName + ".db.password");
        int mincon = Integer.parseInt(props.getProperty(dbName + ".db.minconnect"));
        int maxcon = Integer.parseInt(props.getProperty(dbName + ".db.maxconnect"));
        double timeout = Double.parseDouble(props.getProperty(dbName + ".db.connectionTimeout"));
        String logPath = props.getProperty(dbName + ".db.logPath");
        String jndi = props.getProperty(dbName + ".db.jndi");
        String indexPath = props.getProperty(dbName + ".indexPath");
        String publish = props.getProperty(dbName + ".publishway");
        String customer = props.getProperty(dbName + ".customer");
        int threadpool = Integer.parseInt(props.getProperty(dbName + ".tpool.maxnum"));
        int writelog = Integer.parseInt(props.getProperty(dbName + ".writelong"));
        int uploadsize = Integer.parseInt(props.getProperty(dbName + ".uploadsize"));
        sitesrootpath = props.getProperty(dbName + ".sitesroot");
        lang = props.getProperty(dbName + ".os.lang");
        downloadpath = this.props.getProperty(dbName + ".downpath");

        System.out.println("lang===" + lang);

        if (customer == null) customer = "";
        if (publish != null && !publish.equals("")) publishway = Integer.parseInt(publish);

        if (cpooltype.equalsIgnoreCase("general"))
            poolServer = new GeneralPoolServer();       //Cms own connection cpool(value is general)
        else
            poolServer = new SystemPoolServer();        //AppServer connection cpool(value is system)

        poolServer.createPool(os, appServer, language, type, driver, url, username, password, mincon, maxcon, logPath,
                indexPath, timeout, jndi, publishway, customer, threadpool, writelog, uploadsize);
        return poolServer;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }

    public Connection getConnection() throws SQLException {
        return connPool.getConnection();
    }

    public void freeConnection(Connection conn) {
        connPool.freeConnection(conn);
    }

    public String getDBtype() {
        return connPool.getType();
    }

    public String getOStype() {
        return connPool.getOStype();
    }

    public String getAppServer() {
        return connPool.getAppServer();
    }

    public String getCustomer() {
        return connPool.getCustomer();
    }

    public int getThreadPool() {
        return connPool.getThreadPool();
    }

    public int getWriteLog() {
        return connPool.getWriteLog();
    }

    public int getUploadSize() {
        return connPool.getUploadSize();
    }
}
