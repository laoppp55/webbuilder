package com.bizwink.cms.processTag;

import com.bizwink.cms.xml.*;
import com.bizwink.cms.news.*;

public interface ITagManager {
    String formatArticleList(int markType, int markID,XMLProperties properties, int articleID, int columnID, int siteid, String sitename, int imgflag, String username,int modeltype, String fragPath, boolean isPreview) throws TagException;

    String formatRecommendArticleList(int markType,int markID,XMLProperties properties, int articleID, int columnID, int siteid, String sitename, int imgflag, String username, int modeltype,String fragPath, boolean isPreview) throws TagException;

    Article thearticle(int articleID) throws TagException;

    String formatArticleContent(XMLProperties properties, int articleID, int columnID,int modelID) throws TagException;

    String formatHTMLCODE(String content,int markID) throws TagException;

    String playMedia(int type, int articleID, XMLProperties properties, int columnID,int siteid);

    String formatArticleMainTitle(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    String formatArticleViceTitle(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    String formatArticleAuthor(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    String formatCompanyAttrbuite(int articleID, String attrName, String sitename,int siteID)  throws TagException;

    String formatArticlePT(XMLProperties properties, int articleID) throws TagException;

    String formatArticleURL(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    String formatArticlePULISHDATE(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    String formatArticleSummary(int articleID) throws TagException;

    String formatArticleStatus(int articleID) throws TagException;

    String formatArticleSource(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    String formatColumnList(int flag, XMLProperties properties, String sitename, int siteid, int columnID, String username, int modeltype,String fragPath, boolean isPreview) throws TagException;

    String formatChinesePath(XMLProperties properties, int columnID, int siteid,int samsiteid, int modeltype) throws TagException;

    String formatEnglishPath(XMLProperties properties, int columnID, int siteid, int samsiteid,int modeltype) throws TagException;

    String relatedArticleList(XMLProperties properties, int articleID, int siteid, String sitename, int modeltype,int columnID) throws TagException;

    String relatedArticleList(XMLProperties properties, int articleID, int siteid, String sitename, int modeltype,int columnID, int sflag) throws TagException;

    String processTopStories(XMLProperties properties, int siteID, int columnID, String username, String sitename, int modeltype,String fragPath, boolean isPreview) throws TagException;

    int getColumnNum(String content);

    String[] getColumnString(String content);

    String processExtendAttribute(XMLProperties properties,String xmlTemplate,int articleID, int columnID, String sitename, String xml) throws TagException;

    String formatProductAttrbuite(int articleID, String attrName, String siteName) throws TagException;

    //add by Eric on 2004-10-26 for ad
    String formatADVPosition(XMLProperties properties, int columnID, int siteid) throws TagException;

    String formatArticleCount(int type, int articleID, XMLProperties properties, int columnID,int siteid) throws TagException;

    String formatProductTurnPic(int articleID,int pictype)  throws TagException;

    String formatRelateArticleAttribute(int articleID, String attrName) throws TagException;

    String formatColumnName(XMLProperties properties, int columnID) throws TagException;

    String formatArticleKeyword(int articleID) throws TagException;

    String formatArticleIndexNum (int articleID) throws TagException;

    String getSubTreeColumnIDs(int siteid,String columnIDs);

    String getHtmlMarkContent(int markid,int inc_flag,String content,String sitename,int siteID,int columnID,String fragPath,String username) throws TagException;

    String processLink(XMLProperties properties, int articleID, String sitename,int modeltype) throws TagException;

    String processNextArticleLink(XMLProperties properties, int articleID, int columnID, int siteID, int type) throws TagException;

    //Add by EricDu 2007-8-13
    String formatArticlePic(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException;

    //Add by Eric 2007-9-19 处理文章推荐标记
    String processCommendArticle(XMLProperties properties, int siteID, int columnID, String username, String sitename,int modeltype, String fragPath, boolean isPreview) throws TagException;

    //Add by Eric 2008-2-27
    String formatArticleType(XMLProperties properties, int articleId, int siteID) throws TagException;

    //程序模板开始
    String formatSurvey(XMLProperties properties,String extpath) throws TagException;

    String formatCounter(XMLProperties properties,int siteid) throws TagException;

    String formatUserLogin(XMLProperties properties,int siteid,String tag_content) throws TagException;

    String formatCalendar(XMLProperties properties,int siteid,String extpath) throws TagException;

    String formatRedisplayForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;

    String formatShoppingcarForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;

    String formatSearchForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;

    String formatGenerateOrderForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;

    String formatOrderSearchForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;

    String formatUserRegisterForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;

    String formatUserLoginForProgram(XMLProperties properties,int siteID,String extpath)  throws TagException;
    //程序模板结束

    String formatSitelogo(int siteid) throws TagException;

    String formatSitebanner(int siteid) throws TagException;

    String formatMainNavigator(int siteid) throws TagException;

    String formatSideNavigator(int siteid) throws TagException;

    public String formatCopyright(int siteid) throws TagException;

    public String formatInclude(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath,String appPath, boolean isPreview) throws TagException;

    String formatArticleClickNum(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview,int articleID) throws TagException;
    String getSeeCookie(XMLProperties properties,int siteID,String extpath,int articleid,int columnid);

    //by Vincent
    String formatProductTurnPicOne(String tagcontent,int markID,int articleID,String sitename, boolean isPreview) throws TagException;

    String formatDefineInfo(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath) throws TagException ;

    String formatLeavemessageInfo(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath,int markID) throws TagException ;

    String formatLeavemessageList(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException ;

    String formatShoppongCarList(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException ;

    String formatOrderResult(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException ;

    String formatLoginForm(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException;

    String formatLoginDisplay(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException;

    String formatOrderSearchResult(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException;

    String formatOrderDeatilResult(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException;

    String formatLoginFormForProgram(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException;
}
