package com.bizwink.cms.articleListmanager;

import java.util.*;

import com.bizwink.cms.news.ArticleException;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.audit.*;

public interface IArticleListManager
{
    List getArticleList(String articleIDs) throws ArticleException;

    List getArticleList(int threadnum) throws ArticleException;

    //判断在数据库是否存在谋篇文章
    boolean existTheArticle(int siteid,int fromsiteid,String sarticleid);
}