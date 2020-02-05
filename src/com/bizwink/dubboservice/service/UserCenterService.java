package com.bizwink.dubboservice.service;

import com.bizwink.po.Article;
import com.bizwink.po.Template;

/**
 * Created by petersong on 16-3-5.
 */
public interface UserCenterService {
    public int CreateTemplate(Template template);

    public int CreateArticle(Article article);
}
