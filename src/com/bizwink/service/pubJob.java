package com.bizwink.service;

import com.bizwink.cms.publishx.PublishServerManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 15-5-26.
 */

@Service
public class pubJob extends QuartzJobBean {
    protected PrintWriter log=null;
    private String pid=null;
    boolean publishServerRunning = false;

    public void doPublish() throws IOException{
        //实现你的业务逻辑
        //if (publishServerRunning == false) {
            System.out.println("启动系统发布进程！！！；" + new Timestamp(System.currentTimeMillis()));
            try {
                log = new PrintWriter(new FileOutputStream("publish.log"), true);
            }
            catch (IOException e1) {
                try {
                    log = new PrintWriter(new FileOutputStream("DCB_" + System.currentTimeMillis() + ".log"), true);
                }
                catch (IOException e2) {
                    System.out.println("不能打开LOG文件");
                    throw new IOException("不能打开LOG文件");
                }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss a zzz");
            java.util.Date nowc = new java.util.Date();
            pid = formatter.format(nowc);

            BufferedWriter pidout = new BufferedWriter(new FileWriter("publish.log" + "pid"));
            pidout.write(pid);
            pidout.close();

            PublishServerManager publishServerManager = null;

            //定义一个新的线程，并启动该线程
            publishServerManager = new PublishServerManager(log);
            publishServerManager.start();
            publishServerRunning = true;
        //}
    }

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        try{
            this.doPublish();
        } catch(IOException exp) {

        }
    }
}
