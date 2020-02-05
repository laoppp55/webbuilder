package com.bizwink.publishQueue;

import com.bizwink.cms.news.Article;

import java.util.*;

public interface IPublishQueueManager {

    Jobinfo getOneJob() throws PublishQueueException;

    void updateJobStatus(int jobid,int status) throws PublishQueueException;

    void insertJobs(Article article) throws PublishQueueException;

    void removeJob(int jobid,int jobtype,int taskid) throws PublishQueueException;

    void setupJobErrorMsg(int jobid,int jobtype,int errcode) throws PublishQueueException;

    boolean existTheJobInQueue(int siteid,int columnid,int targetid,int type)  throws PublishQueueException;
}