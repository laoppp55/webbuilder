package com.bizwink.cms.orderArticleListManager;

import java.util.*;

public interface IOrderArticleListManager {
    
    List getArticles(int columnID, int startIndex, int numResults, int flag, String editor, String selectColumns,int siteid) throws orderArticleException;

    List getArticlesByPage(int columnID, int startIndex, int numResults, int flag, String editor, String selectColumns,int siteid) throws orderArticleException;

    List getArticlesByPage1(int columnID, int startIndex, int numResults, int flag, String editor, String selectColumns,int siteid,int ascdesc) throws orderArticleException;

    List searchArticles(int columnID, String item, String value, String editor, int startIndex, int numResults,int siteid,int flag,int ascdesc) throws orderArticleException;

    int searchArticlesCount(int columnID, String item, String value, String editor,int siteid) throws orderArticleException;

    int getArticleNum(int columnID, String editor, String selectColumns,int siteid) throws orderArticleException;

    List getRecommendArticleList(int startnum,int endnum,int markID,int siteID) throws orderArticleException;

    //int getFirstArticleidForPage(int columnid,int pagenum,int pagesize) throws orderArticleException;

    int getFirstArticleidByDocLevel(int columnid,int doclevel) throws orderArticleException;

    List getPublishArticlesInColumn(int columnID,int siteid) throws orderArticleException;

    List getPublishArticlesByEditor(String editor,int columnID, int startIndex, int numResults, int listShow, int siteid, int samsiteid) throws orderArticleException;

    int getPublishArticlesNumByEditor(String editor,int columnID, int siteid, int samsiteid) throws  orderArticleException;
}