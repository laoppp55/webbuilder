package com.bizwink.cms.news;

import java.util.*;

public interface IArticleKeywordManager
{
    List getAllListKeyLink(int siteid, int columnid) throws ArticleException;

    boolean createKeyLinkXML(String sitename, ArticleKeyword articleKeyword, String rootPath, String dirname);
}
