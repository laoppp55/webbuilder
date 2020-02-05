package com.bizwink.cms.genflv;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-27
 * Time: 8:39:59
 * To change this template use File | Settings | File Templates.
 */
public class rungenflv  extends HttpServlet implements Runnable{
    private Thread runner;
    boolean serverRunning = false;
    boolean publishServerRunning = false;
    String message;

    int info = 0;
    boolean pubflag = false;
    protected PrintWriter log;
    private String pid;

    public rungenflv() {

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

/*        try {
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
*/
    }

}
