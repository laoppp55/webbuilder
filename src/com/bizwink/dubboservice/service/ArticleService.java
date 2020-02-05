package com.bizwink.dubboservice.service;

import com.bizwink.po.Article;
import com.bizwink.po.ArticleExtendattr;
import com.bizwink.po.Template;
import com.bizwink.po.Turpic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-6-19.
 */
public interface ArticleService {
    List<Article>  getArticles(BigDecimal siteid,BigDecimal columnid);

    List<Article> getArticlesByPage(BigDecimal siteid,BigDecimal columnid,int startrow,int pagesize);

    List<Article> getArticleByUseridAndStatus(String userid,BigDecimal status);

    int countArticles(BigDecimal siteid,BigDecimal columnid);

    Article getArticle(BigDecimal articleid);

    Article getArticleByUseridAndArticleid(String userid,BigDecimal articleid);

    int CreateArticle(Article record);

    int CreateArticleWithExtendAttr(Article article,List<ArticleExtendattr> extendattrs,List<Turpic> turnpics,List<Template> templates);

    int UpdateArticle(Article record);

    int UpdateArticleStatus(BigDecimal status,BigDecimal articleid);

    int RemoveArticle(BigDecimal articleid);

    List<Article> getArticleList(Map<String, Object> param);

    List<Article>  getArticleListbyCreator(Map<String, Object> param);

    List<Article> getOrderByBuyname(Map<String, Object> param);

    String getXMLTemplate(BigDecimal columnid);
}
