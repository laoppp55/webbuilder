package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.dubboservice.service.UserCenterService;
import com.bizwink.persistence.ArticleMapper;
import com.bizwink.persistence.TemplateMapper;
import com.bizwink.po.Article;
import com.bizwink.po.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by petersong on 16-3-5.
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {
    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public int CreateTemplate(Template template) {
       return templateMapper.insert(template);
    }

    public int CreateArticle(Article article) {
        return articleMapper.insert(article);
    }
}
