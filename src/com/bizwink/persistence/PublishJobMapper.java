package com.bizwink.persistence;

import com.bizwink.po.PublishJob;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 15-9-10.
 */
public interface PublishJobMapper {
    List<PublishJob> getArticlePublishJobs(Timestamp theTime);

    List<PublishJob> getTemplatePublishJobs(Timestamp theTime);

    List<PublishJob> getIncludePublishJobs(Timestamp theTime);
}
