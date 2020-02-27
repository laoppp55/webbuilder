package com.bizwink.dao;

import com.bizwink.po.Template;

/**
 * Created by petersong on 16-6-19.
 */
public interface TemplateService {
    int CreateTemplate(Template template);
    int UpdateTemplate(Template template);
    int DeleteTemplate(int templateid);
}
