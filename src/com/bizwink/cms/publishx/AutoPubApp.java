package com.bizwink.cms.publishx;

import java.text.*;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.reflect.*;

import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;
import com.bizwink.publishQueue.*;

public class AutoPubApp extends HttpServlet implements Runnable {
    private Thread runner;
    boolean serverRunning = false;
    boolean publishServerRunning = false;

    String host;
    int serverPort, port;
    int DEFAULT_PORT = 8902;
    String message;
    PoolServer cpool;

    ServerSocket listenSocket;
    Socket connection;
    InputStream inStream;
    DataInputStream inDataStream;
    OutputStream outStream;
    DataOutputStream outDataStream;

    int info = 0;
    boolean pubflag = false;
    protected PrintWriter log;
    private String pid;

    public AutoPubApp() {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InetAddress here = InetAddress.getLocalHost();
            host = here.getHostAddress();
        }
        catch (UnknownHostException e) {
            System.out.println("setHostPort error: ");
            e.printStackTrace();
        }

        port = getSocketPort();

        try {
            log = new PrintWriter(new FileOutputStream("publish.log"), true);
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

        BufferedWriter pidout = new BufferedWriter(new FileWriter("publish.log" + "pid"));
        pidout.write(pid);
        pidout.close();

        if (!serverRunning) {
            runner = new Thread(this);
            runner.start();

            message = "Publish Server Started on Resin,  Host " + host + ", port " + Integer.toString(port);
            serverRunning = true;
        } else {
            message = "Publish Server already Running";
        }

        if (serverRunning = true && info == 1) {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println(1);                      //系统正在运行
        } else if (serverRunning = true && info == 0) {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println(0);                      //系统挂起
        }

        System.out.println(message);
        return;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // Returns the socket port on which this servlet will listen.
    // A servlet can specify the port in three ways: by using the socketPort
    // init parameter, by setting the DEFAULT_PORT variable before calling
    // super.init(), or by overriding this method's implementation
    protected int getSocketPort() {
        try {
            //return Integer.parseInt(getInitParameter("socketPort"));
            return Integer.parseInt(CmsServer.getInstance().getProperty("main.os.port"));
        }
        catch (NumberFormatException e) {
            return DEFAULT_PORT;
        }
    }

    //控制发布过程停止或发布的进程，该进程接受来自客户端的命令，并根据客户端的命令
    //启动发布过程或停止发布过程
    public void run() {
        String realpath = null;
        String username = null;

        try {
            listenSocket = new ServerSocket(port);
            HandleServer handleServer = null;
            //HandleServer handleServer = new HandleServer(log);
            //handleServer.start();
            while (true) {
                Socket connection = listenSocket.accept();

                //读客户端的命令信息
                inStream = connection.getInputStream();
                inDataStream = new DataInputStream(new BufferedInputStream(inStream));
                info = inDataStream.readInt();
                realpath = inDataStream.readUTF();
                username = inDataStream.readUTF();

                if (publishServerRunning == false) {
                    //定义一个新的线程，并启动该线程
                    handleServer = new HandleServer(username, realpath, log);
                    handleServer.start();
                    publishServerRunning = true;
                }

                try {
                    //System.out.println("info=" + info);
                    if (info == 1) {
                        System.out.println("发布系统启动了");
                        if (publishServerRunning == true) {
                            handleServer.halted = false;
                        }
                        outStream = connection.getOutputStream();
                        outDataStream = new DataOutputStream(outStream);
                        outDataStream.writeUTF("发布系统启动了\r\n");
                        outDataStream.flush();
                    } else if (info == 0) {
                        System.out.println("发布系统挂起了");
                        if (handleServer.isAlive()) {
                            handleServer.halted = true;
                        }
                        outStream = connection.getOutputStream();
                        outDataStream = new DataOutputStream(outStream);
                        outDataStream.writeUTF("发布系统挂起了\r\n");
                        outDataStream.flush();
                    } else if (info == 2) {
                        publishServerRunning = false;
                        outStream = connection.getOutputStream();
                        outDataStream = new DataOutputStream(outStream);
                        outDataStream.writeUTF("发布系统停止了\r\n");
                        outDataStream.flush();
                        serverRunning = false;
                        outDataStream.close();
                        connection.close();
                        listenSocket.close();
                        break;
                    }
                }
                catch (IOException e1) {
                    log.close();
                    System.err.println("发布服务器运行错误");
                    try {
                        log = new PrintWriter(new FileOutputStream("DCB_" + System.currentTimeMillis() + ".log"), true);
                    }
                    catch (IOException e2) {
                        throw new IOException("不能打开LOG文件");
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("在" + port + "端口的连接被关闭");
        }
    }
}

class HandleServer extends Thread {
    static boolean halted = false;
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
            pool = new publishWorker[3];
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
            pool = new publishWorker[3];
            for (int i = 0; i < pool.length; i++) {
                Object params[] = {username, realpath};
                pool[i] = (publishWorker) constructor.newInstance(params);
            }
        } catch (Exception exp) {
        }
    }

    public void run() {
        if (halted) return;
        for (int i = 0; i < pool.length; i++) {
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
        job one_job = null;
        com.bizwink.publishQueue.Jobinfo one_article = null;
        IPublishQueueManager pqManager = PublishQueuePeer.getInstance();

        try {
            one_article = pqManager.getOneJob();
            if (one_article != null) {
                one_job = new job();
                one_job.setID(one_article.getID());
                one_job.setTargetID(one_article.getTargetID());
                one_job.setSiteID(one_article.getSiteID());
                one_job.setColumnID(one_article.getColumnID());
                one_job.setPublishTime(one_article.getPublishdate());
                one_job.setMaintitle(one_article.getTitle());
                one_job.setType(one_article.getType());
                one_job.setStatus(one_article.getStatus());
                //one_job.setReferArticleFlag(one_article.getReferedTargetId());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            pqManager = null;
        }

        return one_job;
    }
}