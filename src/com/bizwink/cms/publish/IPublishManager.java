package com.bizwink.cms.publish;

import com.bizwink.cms.news.Column;
import com.bizwink.cms.server.*;

public interface IPublishManager {

    int createHomePage(int siteid,int sitetype,int samsiteid, String appPath, String sitename, String username, int imgflag, int option, int templateID) throws PublishException;

    int CreateColPage(int columnID, int siteid,int sitetype,int samsiteid, String appPath, String sitename, String username, int imgflag, int option, int templateID) throws PublishException;

    int CreateArticlePage(int ID,int columnId,int siteid,int sitetype,int samsiteid, String appPath, String sitename, String username, int imgflag, int big5flag,boolean isown,int from) throws PublishException;

    int CreateProgramPage(int columnID,int siteid,int sitetype,int samsiteid,String appPath,String sitename,String username,int imgflag,int option,int templateID) throws PublishException;

    int publish(String username, String url, int siteid, String baseDir, int big5flag) throws PublishException;

    int publish(String username, String localFileName, int siteid, String fileDir, int big5flag, int hostID);

    String PreviewArticlePage(int ID, int siteid,int samsiteid, String baseURL, String appPath, String sitename, int imgflag, int columnId) throws PublishException;    //预览即将发布的文章

    String PreviewTemplateForProgram(int columnID,int modelID,int isArticle,int siteid,int samsiteid,String baseURL,String appPath,String sitename,int imgflag) throws PublishException;

    String generateNavBarForProgram(int navbarID,String filename);

    PoolServer getPool();

    Publish getSiteInfo(int siteid);

    String getSiteName(int siteid);

    int getSiteID(int articleid);

    int getSiteID(int columnid, int flag);

    int getSiteID(int parentid, int flag1, int flag2);

    int getPubFlag(int siteid);

    String getUserName(int siteid);

    String getCName(int columnid);

    int getImageFlag(int siteid);

    public String getExtName(int columnID);

    public String getDirName(int columnid);

    String PreviewTemplate(int columnID, int templateID, int isArticle, int siteid,int samsiteid, String baseURL, String appPath, String sitename, int imgflag) throws PublishException;

    void writeLog(String sitename, String rootPath, String logLine);

    int CreateMenuTreePage(int columnID, int siteid, String appPath, String sitename, String username, int option, int templateID) throws PublishException;

    public boolean checkArticleContentRefers(int articleid, int columnid);

    public int generatePage(String resultStr, String filename, String username, int siteid, int samsiteid, String sitename, String fileDir, int big5flag, int languageType);
}