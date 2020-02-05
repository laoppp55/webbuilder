package com.bizwink.dubboservice.service;

import com.bizwink.po.PublishQueue;

import java.util.List;

/**
 * Created by petersong on 16-12-4.
 */
public interface PublishQueueService {
    int CreatePublishQueueJob(PublishQueue publishQueue);

    int CreatePublishQueueJobs(List<PublishQueue> publishQueues);
}

