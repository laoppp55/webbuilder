package com.bizwink.cms.publishx;

import java.sql.*;
import java.io.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.PublishQueuePeer;

public class publishWorker extends Thread {
    job job = null;
    SiteInfo siteinfo = null;
    String realpath;
    String username;
    protected HandleServer pserver;
    PrintWriter log;

    public publishWorker() {

    }

    public publishWorker(String username, String realpath) {
        this.username = username;
        this.realpath = realpath;
    }

    public publishWorker(String username, String realpath, job job, SiteInfo siteinfo) {
        this.realpath = realpath;
        this.username = username;
        this.job = job;
        this.siteinfo = siteinfo;
    }

    public void run() {
        //job job = null;
        int errcode = 0;
        int count = 0;
        pserver = new HandleServer(log);

        for (; ;) {
            job = pserver.getWorkload();
            if (job == null) {
                //停止5分钟后，寻找新的作业
                System.out.println("进程停止等待1分钟，查看是否有新作业！！！");
                try {
                    Thread.sleep(1000 * 60);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //return;
            } else {
                if (!pserver.halted) {
                    count = count + 1;
                    pserver.getPublishDone().workerBegin();
                    processWorkload(count);
                    pserver.getPublishDone().workerEnd();
                    //停止1秒钟后，寻找新的作业
                    try {
                        Thread.sleep(1000*10);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void processWorkload(int count) {
        int errcode = 0;
        IPublishManager publishManager = PublishPeer.getInstance();
        IPublishQueueManager pqManager = PublishQueuePeer.getInstance();
        ISiteInfoManager siteManager = SiteInfoPeer.getInstance();

        int columnid = job.getColumnID();
        int siteid = job.getSiteID();
        int samsiteid = 0;
        int sitetype = 0;
        String sitename = null;
        SiteInfo siteinfo = null;
        int imgflag = 0;
        int big5flag = 0;
        try {
            siteinfo = siteManager.getSiteInfo(siteid);
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        if (siteinfo != null) {
            sitename = siteinfo.getDomainName();
            samsiteid = siteinfo.getSamsiteid();
            sitetype =siteinfo.getSitetype();
            sitename = StringUtil.replace(sitename, ".", "_");
            imgflag = siteinfo.getImgeDir();
            big5flag = siteinfo.getTcFlag();
            realpath = StringUtil.replace(realpath, "\r\n", "");

            //根据作业的类型调用不同的发布程序
            try {
                switch (job.getType()) {
                    case 1:
                        Timestamp curtime = new Timestamp(System.currentTimeMillis());
                        //if (curtime.compareTo(job.getPublishTime()) > 0) {
                        if (job.getReferArticleFlag() == 0)  {
                            errcode = publishManager.CreateArticlePage(job.getTargetID(),job.getColumnID(),siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag,true,0) ;
                        } else {
                            errcode = publishManager.CreateArticlePage(job.getTargetID(),job.getColumnID(),siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag,false,0) ;
                        }
                        break;
                    case 3:
                    case 0:
                        errcode = publishManager.CreateColPage(job.getColumnID(), siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag, job.getTargetID());
                        break;
                    case 2:
                        errcode = publishManager.createHomePage(siteid,sitetype,samsiteid, realpath, sitename, username, imgflag, big5flag, job.getTargetID());
                        break;
                }

                if(job.getType() > 10)
                    errcode = publishManager.CreateProgramPage(job.getColumnID(),siteid,sitetype,samsiteid,realpath,sitename,username,imgflag,big5flag, job.getTargetID());

                if (errcode == 0) {                                //发布内容正常结束或者被发布的内容在数据库中已经不存在
                    pqManager.removeJob(job.getID(),job.getType(),job.getTargetID());
                    System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（成功）");
                } else {
                    if (errcode == -2) {
                        System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（需要设置文章模板）");
                    } else if (errcode == -3){
                        System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（栏目已经被删除）");
                    } else {
                        System.out.println(this.getName() + "(" + count + ")发布作业===" + job.getMaintitle() + "====所属栏目（"+job.getColumnID() +"--"+job.getTargetID()+"）（发布出错）");
                    }
                    pqManager.setupJobErrorMsg(job.getID(),job.getType(),errcode);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                publishManager = null;
                pqManager = null;
                //columnManager = null;
                siteManager = null;
            }
        }
    }
}

