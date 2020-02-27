package com.bizwink.dao;

import com.bizwink.po.Article;

/**
 * Created by petersong on 16-6-19.
 */
public interface ArticleService {
    int CreateArticle(Article article);
    int UpdateArticle(Article article);
    int DeleteArticle(int articleid);
}
