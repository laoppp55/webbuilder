package com.bizwink.cms.publishx;

import java.text.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;

import com.bizwink.cms.news.*;
import com.bizwink.cms.sitesetting.*;

public class autoPub extends HttpServlet implements Runnable {
    private Thread runner;
    boolean serverRunning = false;
    boolean publishServerRunning = false;
    String message;

    int info = 0;
    boolean pubflag = false;
    protected PrintWriter log;
    private String pid;

    public autoPub() {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            log = new PrintWriter(new FileOutputStream("c:\\publish.log"), true);
            // Can't open the requested file. Open the default file.
        }
        catch (IOException e1) {
            System.err.println("发布过程启动发生错误！！！");
            try {
                log = new PrintWriter(new FileOutputStream("DCB_" + System.currentTimeMillis() + ".log"), true);
            }
            catch (IOException e2) {
                throw new IOException("不能打开LOG文件");
            }
        }

        // Write the pid file (used to clean up dead/broken connection)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
        java.util.Date nowc = new java.util.Date();
        pid = formatter.format(nowc);

        BufferedWriter pidout = new BufferedWriter(new FileWriter("c:\\publish.log" + "pid"));
        pidout.write(pid);
        pidout.close();


        if (!serverRunning) {
            runner = new Thread(this);
            runner.start();

            message = "Publish Server Started on Resin,  Host ";
            serverRunning = true;
        } else {
            message = "Publish Server already Running";
        }

        System.out.println(message);
        return;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //控制发布过程停止或发布的进程，该进程接受来自客户端的命令，并根据客户端的命令
    //启动发布过程或停止发布过程
    public void run() {
        String realpath = "/usr/local/yidaba/resin-2.1.17/cms/webbuilder/";
        String username = "admin";

        try {

            HandleServer handleServer = null;
            handleServer = new HandleServer(username, realpath, log);
            handleServer.start();
            publishServerRunning = true;

        }
        catch (Exception e1) {
            try {
                log = new PrintWriter(new FileOutputStream("DCB_" + System.currentTimeMillis() + ".log"), true);
            }
            catch (IOException e2) {
            }
        }
    }

    class HandleServer extends Thread {
        protected boolean halted = false;
        job newjob = new job();
        protected publishWorker pool[];
        protected publishDone done = new publishDone();
        PrintWriter log;
        String realpath;
        String username;

        HandleServer(String realpath, String username, PrintWriter log) {
            super();
            this.log = log;
            this.realpath = realpath;
            this.username = username;
            try {
                Class types[] = {String.class, String.class};
                Constructor constructor = publishWorker.class.getConstructor(types);
                pool = new publishWorker[5];
                for (int i = 0; i < pool.length; i++) {
                    Object params[] = {username, realpath};
                    pool[i] = (publishWorker) constructor.newInstance(params);
                }
            } catch (Exception exp) {
            }
        }

        HandleServer(PrintWriter log) {
            super();
            this.log = log;

            try {
                Class types[] = {String.class, String.class};
                Constructor constructor = publishWorker.class.getConstructor(types);
                pool = new publishWorker[5];
                for (int i = 0; i < pool.length; i++) {
                    Object params[] = {username, realpath};
                    pool[i] = (publishWorker) constructor.newInstance(params);
                }
            } catch (Exception exp) {
            }
        }

        public void run() {
            if (halted)
                return;
            for (int i = 0; i < pool.length; i++) {
                System.out.println("pthread" + i);
                pool[i].setName("pthread" + i);
                pool[i].start();
            }

            try {
                done.waitBegin();
                done.waitDone();

                for (int i = 0; i < pool.length; i++) {
                    pool[i].interrupt();
                    pool[i].join();
                    pool[i] = null;
                }
            } catch (Exception e) {
            }
        }

        public publishDone getPublishDone() {
            return done;
        }

        public job getWorkload() {
            int siteid = 0;
            boolean first = true;
            SiteInfo siteinfo = null;
            int errcode = -1;
            job one_job = null;
            com.bizwink.cms.news.Article one_article = null;

            ISiteInfoManager siteManager = SiteInfoPeer.getInstance();
            IArticleManager articleManager = ArticlePeer.getInstance();

            try {
                one_article = articleManager.getOneArticleForPublish();
                if (one_article != null) {
                    one_job = new job();
                    one_job.setID(one_article.getID());
                    one_job.setColumnID(one_article.getColumnID());
                    one_job.setPublishTime(one_article.getPublishTime());
                    one_job.setMaintitle(one_article.getMainTitle());
                    one_job.setType(one_article.getStatus());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                siteManager = null;
                articleManager = null;
            }

            return one_job;
        }
    }
}