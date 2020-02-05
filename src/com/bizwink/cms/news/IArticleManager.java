package com.bizwink.cms.news;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.tree.*;
import com.bizwink.cms.publishx.*;

public interface IArticleManager {
    void updatePubFlag(Article article) throws ArticleException;

    void updateIndexFlag(int articleID,int flag) throws ArticleException;

    int batchDel(String articleIDs) throws ArticleException;

    int batchAchive(int columnid,String articleIDs) throws ArticleException;

    int batchUnuse(int columnid,String articleIDs) throws ArticleException;

    void resetIndexFlag() throws ArticleException;

    int remove(int articleID, int siteID, String editor, int delwebflag) throws ArticleException;

    Article getArticle(int articleID) throws ArticleException;

    Article getOneArticleForPublish() throws ArticleException;

    Article getArticle(int articleID, String editor) throws ArticleException;

    public int getArticleProcessOfAudit(int ID) throws ArticleException;

    int getArticleColumnid(int ID, String editor) throws ArticleException;

    Article getArticle(int columnID, int sortID) throws ArticleException;

    int getArticleNum(int columnID, int noContent) throws ArticleException;

    Article getArticleInNum(int columnID, int artNum) throws ArticleException;

    Article getHeadLineArticle(int columnID) throws ArticleException;

    int getSearchArticleCount(String keyword) throws ArticleException;

    List getArticles(Tree colTree, int columnID, int noContent) throws ArticleException;

    List getArticles(int columnID, int startIndex, int numResults, int noContent) throws ArticleException;

    List getArticles(String cidString) throws ArticleException;

    List getOrderArticles(int startnum, int endnum, String cidString, String where, String orderby, int status, int articleNum,int defaultColumnID, boolean hasReferedArticle) throws ArticleException;

    List getOrderArticles(int startnum, int endnum, String cidString, String where, String orderby, int status,int articleNum, int defaultColumnID, String stype) throws ArticleException;

    List getArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    List getAllUsedArticles(int columnID, int startIndex, int numResults, int noContent) throws ArticleException;

    void getColumnURL(int columnID, StringBuffer buf) throws ArticleException;

    List getLinkSearchArticles(int columnID, int startIndex, int numResults, String keyword, int modelType) throws ArticleException;

    int getLinkSearchArticlesNum(int columnID, String keyword, int modelType) throws ArticleException;

    List getLinkArticles(int columnID, int startIndex, int numResults,int modelType) throws ArticleException;

    int getLinkArticlesNum(int columnID,int modelType) throws ArticleException;

    int getLinkForXuanImagesNum(int columnID, String content, int modeltype) throws ArticleException;

    List getLinkForXuanImages(int columnID, int startIndex, int numResults, String content, int modeltype) throws ArticleException;

    List getArticles(String cidString, String keyWords, int num) throws ArticleException;

    List getArticles(int columnID, int startIndex, int numResults, int noContent, int status) throws ArticleException;

    List getArticlesByFlag(int columnID, int startIndex, int numResults, int noContent, int flag) throws ArticleException;

    List getArticlesByPubFlag(int columnID, int startIndex, int numResults, int noContent, int flag) throws ArticleException;

    List getPublishArticles(int siteID) throws ArticleException;

    List getOneArticleRelatedArticles(int pageid,int pagetype) throws ArticleException;

    //移动系列文章到特定的栏目
    int moveArticlesToColumn(int scope,int oldcolumnID, int columnID, String articleIDs, int siteid, String appPath, String username) throws ArticleException;

    //复制系列文章到特定的栏目
    int copyArticlesToColumn(int scope,int oldcolumnID, int columnID, String articleIDs, int siteid, String appPath, String username) throws ArticleException;

    int checklock(Article article) throws ArticleException;

    void setPublishFailedStatus(int articleID) throws ArticleException;

    List getCArticles(Tree colTree, int columnID, int noContent, int start, int range) throws ArticleException;

    List getBackArticles(int columnID, String userID, int startIndex, int numResults, int type) throws ArticleException;

    int getBackArticlesNum(int columnID, String userID, int type) throws ArticleException;

    List getArticlesforPublishTopstories(int beginNum, int articleNum, String where) throws ArticleException;

    List getArticles(String columnIDs, int startnum, int numResults, boolean important, boolean lastest) throws ArticleException;

    String getColumnIDStr(int siteID) throws ArticleException;

    Article getNextArticle(int articleID, int columnID, int siteID, int type) throws ArticleException;

    String getArticleVersion(int articleID);

    int getNextOrder(int columnID) throws ColumnException;

    String getColumnIDs(int columnID);

    List getUnusedArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getUnusedArticlesNum(int columnID) throws ArticleException;

    List getArchiveArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getArchiveArticlesNum(int columnID) throws ArticleException;

    List getAuditArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getAuditArticlesNum(int columnID) throws ArticleException;

    List getMoveArticles(int columnID, int startIndex, int numResults, String content,int siteid) throws ArticleException;

    int getMoveArticlesNum(int columnID, String content,int siteid) throws ArticleException;

    List getUploadFiles(int columnID, int startIndex, int numResults, String editor) throws ArticleException;

    int getUploadFilesNum(int columnID, String editor) throws ArticleException;

    List getUnusedUploadFiles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getUnusedUploadFilesNum(int columnID) throws ArticleException;

    List getPublishFailArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getPublishFailArticlesNum(int columnID) throws ArticleException;

    int getArticleCountIncludeSubColumn(int columnID) throws ArticleException;

    List getAuditUploadFiles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getAuditUploadFilesNum(int columnID) throws ArticleException;

    List getAuditArticlesFiles(String auditor,int columnID, int startIndex, int numResults,int siteid) throws ArticleException;

    int getAuditArticlesFilesNum(String auditor,int columnID,int siteid) throws ArticleException;

    List getRelatedArticles(int columnID, int startIndex, int numResults,int siteid) throws ArticleException;

    int getRelatedArticlesNum(int columnID,int siteid) throws ArticleException;

    List getTopStoriesArticles(int columnID, int startIndex, int numResults,int siteid) throws ArticleException;

    int getTopStoriesArticlesNum(int columnID,int siteid) throws ArticleException;

    List getRePublishArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getRePublishArticlesNum(int columnID) throws ArticleException;

    int getOrderArticlesCount(String cidString, String where,int siteid) throws ArticleException;

    String formatOracleClause(String cidString);

    String formatOracleClause(String cidString, int flag);

    int updatecancle(int articleID) throws ArticleException;

    void PigeonholeArticle(int columnID, int achieve, Timestamp bdate, Timestamp tdate, int includeSubCol, int siteid) throws ArticleException;

    int updateRSS(String articleIDs, String allArticleIds) throws ArticleException;

    List getArticleMainTitle(String articleIDs) throws ArticleException;

    List getReferArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    int getReferArticlesNum(int columnID) throws ArticleException;

    List getArticles4PublishTopStories(String articleids) throws ArticleException;

    //按主权重、次权重或关键字取出文章
    Article getArticleByWeight(int columnID, int weight, int type) throws ArticleException;

    //Add by Eric 2006-7-16 for ArticleKeyword
    public void insertColumnKeyword(ArticleKeyword keyword);

    public List getArticleKeywords(int columnid, int start, int range, int flag, boolean isRootColumnID, int siteid) throws ArticleException;

    public int getArticleKeywordsNum(int columnid, boolean isRootColumnID, int siteid) throws ArticleException;

    public ArticleKeyword getOneArticleKeyword(int columnid, int id) throws ArticleException;

    public void updateColumnKeyword(ArticleKeyword keyword);

    public void deleteColumnKeyword(int id);

    public String getOneArticleKeywordURL(String keyword) throws ArticleException;

    //Add by Eric 2006-9-30 获得已经发布的文章
    public List getPublishedArticles(int columnID, int startIndex, int numResults) throws ArticleException;

    public int getPublishedArticlesNum(int columnID) throws ArticleException;

    //Add by Eric 2007-8-2 获得需要发布成RSS的文章
    List getPubRssArticles(int columnID, Column column) throws ArticleException;

    //Add by Eric 2007-8-29 更新自动归档规则
    void PigeonholeArticle(int columnID, int achieve, int includeSubCol, int siteid) throws ArticleException;

    //Add by Eric 2007-8-30 获得某个栏目下所有文章id的字符串
    String getArticlesIdofColumn(int columnid) throws ArticleException;

    //Add by Eric 2007-9-18 获得文章被推荐的栏目
    List getRelatedArticles(int articleID) throws ArticleException;

    //Add by Eric 2007-9-19 推荐文章
    int getCommendArticleForColumnNum(int columnID) throws ArticleException;

    List getCommendArticleForColumn(int columnID, int startIndex, int numResults) throws ArticleException;

    List getArticleMainTitle(String articleIDs,int columnid) throws ArticleException;

    void updateOrders(int articleID, int columnid, int orders) throws ArticleException;

    void updateTitle(int articleID, int columnid, String title) throws ArticleException;

    void deleteCommendArticle(int id, int columnid) throws ArticleException;

    List getArticlesforPublishCommendArticle(int articleNum, String where) throws ArticleException;

    List getArticlesforPublishCommendArticle(String where) throws ArticleException;

    //add by xzm 2007-12-12 文章发布列表修改
    List getNewPublishArticles(int columnID, int startIndex, int numResults, int listShow,int siteid,int samsiteid,int sitetype) throws ArticleException;

    int getNewPublishArticlesNum(int columnID,int siteid,int samsiteid,int sitetype) throws ArticleException;

    //Add by Eric 2008-3-28
    void updateArticleContent(int id, String content);

    public List getArticleTurnPic(int articleID);

    public void updataTurnPicInfo(Turnpic tpic);

    public void updataMoreTurnPicInfo(List tpics);

    public Turnpic getAArticleTurnPic(int id);

    public int deleteArticleTurnpic(int id);

    public int sharegetTopStoriesArticlesNum(int columnID,int siteid,int samsiteid) throws ArticleException ;

    public String sharegetColumnIDs(int columnID,int siteid,int samsiteid);

    public List sharegetTopStoriesArticles(int columnID, int startIndex, int numResults,int siteid,int samsiteid) throws ArticleException;

    int getArticleCountIncludeSubColumnBySamsite(int siteid,int samsiteid,int columnID) throws ArticleException;

    void updateStatus(int articleID, int status) throws ArticleException;

    //List getPublishArticles(String cidString) throws ArticleException;

    //List getPublishArticles(int columnID, int startIndex, int numResults, int listShow) throws ArticleException;

    //int getPublishArticlesNum(int columnID) throws ArticleException;

    //by Vincent 2010-07-15 根据文章id获得轮换效果标志位
    public int getchangepic(int articleid);

    void createTender(Tender tender) throws ArticleException;

    Multimedia getFile(int id) throws ArticleException;

    List getTenderbyid(int articleid, int startrow, int range) throws ArticleException;

    int getTenderArticleNum(int columnid) throws ArticleException;

    List getTenderArticles(int startrow, int range)  throws ArticleException;

    List getSjs_log(String searchtime1,String searchtime2) throws ArticleException;
}