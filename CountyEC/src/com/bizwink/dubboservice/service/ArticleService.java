package com.bizwink.dubboservice.service;

import com.bizwink.po.Article;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by petersong on 16-6-19.
 */
public interface ArticleService {
    List<Article>  getArticles(BigDecimal siteid, BigDecimal columnid);

    List<Article> getArticlesByPage(BigDecimal siteid,BigDecimal columnid,int startrow,int pagesize);

    int countArticles(BigDecimal siteid,BigDecimal columnid);

    Article getArticle(BigDecimal articleid);

    int CreateArticle(Article record);

    int UpdateArticle(Article record);

    int RemoveArticle(BigDecimal articleid);
}
