package com.bizwink.log;

import com.bizwink.cms.news.Article;

import java.util.List;

public class PublishArticleListOfWeek {
    private String strdate;
    private List<Article>  articles;

    public String getStrdate() {
        return strdate;
    }

    public void setStrdate(String strdate) {
        this.strdate = strdate;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
