package com.bizwink.archiver;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;

public class ArchiveArticles implements Runnable {
    private Thread runner;
    private String logFileString;
    private PrintWriter log;
    private String pid;
    PoolServer cpool;

    public ArchiveArticles(String logPath, PoolServer cpool) throws IOException {
        this.cpool = cpool;
        this.logFileString = logPath;

        try {
            log = new PrintWriter(new FileOutputStream(logFileString), true);
        }
        catch (IOException e1) {
            System.err.println("Warning:Indexer could not open \""
                    + logFileString + "\" to write log to. Make sure that your Java " +
                    "process has permission to write to the file and that the directory exists."
            );
            try {
                log = new PrintWriter(new FileOutputStream("DCB_" +
                        System.currentTimeMillis() + ".log"), true
                );
            }
            catch (IOException e2) {
                throw new IOException("Can't open any log file");
            }
        }

        // Write the pid file (used to clean up dead/broken connection)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
        java.util.Date nowc = new java.util.Date();
        pid = formatter.format(nowc);

        BufferedWriter pidout = new BufferedWriter(new FileWriter(logFileString + "pid"));
        pidout.write(pid);
        pidout.close();

        log.println("Starting Archiver:");
        log.println("-----------------------------------------");
        log.println(pidout.toString());

        // Fire up the background housekeeping thread
        runner = new Thread(this);
        runner.start();

    }

    public void run() {
        Connection conn = null;
        boolean doFlag = true;
        while (doFlag) {
            int count = 0;

            try {
                //获得所有设定了自动归档规则的栏目
                String SQL_GET_ARCHIVE_COLUMNS = "select id,archivingrules from tbl_column where archivingrules > 1";
                //获得某个栏目下所有满足归档规则的文章
                String SQL_GET_ARCHIVE_ARTICLES = "select id,columnid,maintitle from tbl_article where columnid = ? and createdate < ? and status = 1";
                //更新某个栏目下所有满足归档规则的文章标志，使其处于归档状态
                String SQL_UPDATE_STATUS = "update tbl_article set status = 2 where columnid = ? and createdate < ? and status = 1";

                List archiveList = new ArrayList();
                PreparedStatement pstmt;
                ResultSet rs;

                try {
                    conn = cpool.getConnection();

                    List setRulesColumns = new ArrayList();
                    pstmt = conn.prepareStatement(SQL_GET_ARCHIVE_COLUMNS);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        Column column = new Column();
                        column.setID(rs.getInt("id"));
                        column.setArchivingrules(rs.getInt("archivingrules"));
                        setRulesColumns.add(column);
                    }
                    rs.close();
                    pstmt.close();

                    for (int i = 0; i < setRulesColumns.size(); i++) {
                        Column column = (Column) setRulesColumns.get(i);
                        int columnid = column.getID();
                        int archivingrules = column.getArchivingrules();
                        Timestamp archivingtime = null;
                        if (archivingrules == 2)
                            archivingtime = new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 30L);
                        else if (archivingrules == 3)
                            archivingtime = new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7L);
                        else if (archivingrules == 4)
                            archivingtime = new Timestamp(System.currentTimeMillis() - 1000 * 60 * 60 * 24L);

                        conn.setAutoCommit(true);
                        pstmt = conn.prepareStatement(SQL_GET_ARCHIVE_ARTICLES);
                        pstmt.setInt(1, columnid);
                        pstmt.setTimestamp(2, archivingtime);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            Article article = new Article();
                            article.setID(rs.getInt("id"));
                            article.setColumnID(rs.getInt("columnid"));
                            article.setMainTitle(rs.getString("maintitle"));
                            archiveList.add(article);
                            count++;
                        }
                        rs.close();
                        pstmt.close();

                        conn.setAutoCommit(false);
                        pstmt = conn.prepareStatement(SQL_UPDATE_STATUS);
                        pstmt.setInt(1, columnid);
                        pstmt.setTimestamp(2, archivingtime);
                        pstmt.executeUpdate();
                        pstmt.close();
                        conn.commit();
                    }
                } catch (Exception e) {
                    doFlag = false;
                } finally {
                    if (conn != null) {
                        try {

                            cpool.freeConnection(conn);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                //启动归档的进程
                try {
                    Thread workerRunner = new ArchiveWorker(archiveList, cpool);
                    workerRunner.start();
                } catch (IOException ioexp) {
                    ioexp.printStackTrace();
                }

                if (count > 0) System.out.println(" 归档 " + count + " 篇文章完成");
            } catch (Exception e) {
                System.out.println("Archive the articles error in ArchiveArticles 149.");
                e.printStackTrace();
            }

            try {
                //Thread.sleep(1000 * 60 * 60 * 12);
                Thread.sleep(1000 * 5);
            }  // Wait 0.5 days for next cycle
            catch (InterruptedException e) {
                return;
            }
        }
    }
}
