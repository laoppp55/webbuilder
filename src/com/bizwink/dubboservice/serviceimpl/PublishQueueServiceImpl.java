package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.PublishQueueService;
import com.bizwink.persistence.PublishQueueMapper;
import com.bizwink.po.PublishQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by petersong on 16-12-4.
 */
@Service
public class PublishQueueServiceImpl implements PublishQueueService{
    @Autowired
    private PublishQueueMapper publishQueueMapper;

    public int CreatePublishQueueJob(PublishQueue publishQueue) {
        return publishQueueMapper.insert(publishQueue);
    }

    public int CreatePublishQueueJobs(List<PublishQueue> publishQueues) {

        return 0;
    }

}
