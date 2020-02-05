package com.bizwink.cms.publishx;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.PublishQueuePeer;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;

/**
 * Created by petersong on 15-11-4.
 */
public class PublishServerManager  extends Thread{
    public static boolean halted = true;
    protected PublishWorkerBySpring pool[];
    static protected publishDone done = new publishDone();
    static PrintWriter log=null;
    String realpath=null;
    String username=null;

    public PublishServerManager(String realpath, String username, PrintWriter log) {
        super();
        this.log = log;
        this.realpath = realpath;
        this.username = username;
        try {
            Class types[] = {String.class, String.class};
            Constructor constructor = PublishWorkerBySpring.class.getConstructor(types);
            pool = new PublishWorkerBySpring[10];
            for (int i = 0; i < pool.length; i++) {
                Object params[] = {username, realpath};
                pool[i] = (PublishWorkerBySpring) constructor.newInstance(params);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public PublishServerManager(PrintWriter log) {
        super();
        this.log = log;

        try {
            Class types[] = {String.class, String.class};
            Constructor constructor = PublishWorkerBySpring.class.getConstructor(types);
            pool = new PublishWorkerBySpring[10];
            for (int i = 0; i < pool.length; i++) {
                Object params[] = {username, realpath};
                pool[i] = (PublishWorkerBySpring) constructor.newInstance(params);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
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
            halt();
            for (int i = 0; i < pool.length; i++) {
                pool[i].interrupt();
                pool[i].join();
                pool[i] = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static publishDone getPublishDone() {
        return done;
    }

    synchronized public void halt() {
        halted = true;
        System.out.println("在发布队列中没有发布作业，等待新的发布作业！！！");
        notifyAll();
    }

    public static job getWorkload() {
        job one_job = null;
        com.bizwink.publishQueue.Jobinfo one_article = null;
        IPublishQueueManager pqManager = PublishQueuePeer.getInstance();

        try {
            //String autopubcondition =   CmsServer.getInstance().getProperty("autopubcondition");
            one_article = pqManager.getOneJob();
            if (one_article != null) {
                System.out.println("找到待发布的作业：" + one_article.getTitle());
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
            } else {
                System.out.println("没有待处理的作业，发布进程继续等待!!!!");
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
