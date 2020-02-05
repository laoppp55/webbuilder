package com.bizwink.webservice;

import com.bizwink.publishQueue.IPublishQueueManager;
import com.bizwink.publishQueue.Jobinfo;
import com.bizwink.publishQueue.PublishQueueException;
import com.bizwink.publishQueue.PublishQueuePeer;

/**
 * Created by Administrator on 18-4-6.
 */
public class MasterkongService {
    private int requestCount = 0;

    public String getName(String name) {
        IPublishQueueManager publishQueueManager = PublishQueuePeer.getInstance();
        Jobinfo jobinfo = null;
        try {
            jobinfo = publishQueueManager.getOneJob();
        } catch (PublishQueueException exp) {

        }

        //String path = getWebInfPath();
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path + "/WEB-INF/applicationContext.xml");
        //OrganizationService organizationService = applicationContext.getBean(OrganizationService.class);
        //ApplicationContext applicationContext = ContextLoaderListener.getCurrentWebApplicationContext();
        //if (applicationContext != null) {
        //    OrganizationService organizationService = applicationContext.getBean(OrganizationService.class);
        //List ll = organizationService.getColumnList();
        //} else {
        //    System.out.println("applicationContext get failed" );
        //}
        requestCount++;
        System.out.println("requestCount=" + requestCount);
        System.out.println("Received:" + name + "===" + jobinfo.getTitle());

        return "Hello " + name + "===" + jobinfo.getTitle();
    }

    public String addBiddinInfo(int siteid,String sitename,int columnid,String columnname,int articleid,String maintitle,String articleurl,int operationtype,String editor) {
        int errcode = 0;

        return null;
    }
}
