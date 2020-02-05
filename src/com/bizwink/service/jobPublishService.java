package com.bizwink.service;

import com.bizwink.cms.publishx.PublishServerManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by Administrator on 15-9-10.
 */

@Service
public class jobPublishService  extends QuartzJobBean {
    protected PrintWriter log=null;
    private String pid=null;
    boolean publishServerRunning = false;
    PublishServerManager publishServerManager;

    public void doPublish(String realpath) throws IOException{
        //实现你的业务逻辑
        if (publishServerRunning == false) {
            System.out.println("启动系统发布进程！！！；" + new Timestamp(System.currentTimeMillis()));
            try {
                log = new PrintWriter(new FileOutputStream("publish.log"), true);
            }
            catch (IOException e1) {
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

            publishServerRunning = true;
        }

        //定义一个新的线程，并启动该线程
        if(PublishServerManager.halted == true) {
            PublishServerManager.halted = false;
            publishServerManager = new PublishServerManager(realpath,"",log);
            publishServerManager.start();
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            this.doPublish("");
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
