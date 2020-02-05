package com.bizwink.webapps.userfunction;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.toolkit.companyinfo.Companyinfo;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-9
 * Time: 8:34:48
 * To change this template use File | Settings | File Templates.
 */
public interface IUserFunctionManager {
    String genrateProductPageForStenders(int productid);

    int searchProductNumForStenders(int siteid,String keyword);

    List searchProductForStenders(int siteid,String keyword,int pagesize,int startrow);

    int searchProductNumForStendersByComponent(int siteid,String keyword,int type);

    List searchProductForStendersByComponent(int siteid,String component,int pagesize,int startrow,int type);

    List orderProducts(int siteid,int columnid,int pagesize,int startrow,int ordertype);

    int orderProductCount(int siteid,int columnid,int ordertype);

    Article lingLiangForChristByDay(java.sql.Timestamp thedate);

    Article lingLiangForChristByWeek(java.sql.Timestamp thedate);

    List getArticlesByColumn(int columnid,int startrow,int pagesize);

    int getArticleCountByColumn(int columnid);

    List searchBookForChrist(int flag,String keyword);

    //康师傅新闻中心栏目
    String getColumnIDs(int siteID, int columnID);

    List getArticlesByUser(int siteid, String cids, String memberid, String rolecat, int startrow, int pagesize);

    int getArticlesCountByUser(int siteid, String cids, String memberid, String rolecat);

    String generateSecondLevelTree(int siteid, int parentcolumnid);

    int saveUploadFile(int userflag, String filename, String username, int siteid, String fileDir, String maintitle, String vicetitle, String summary, String keyword, String source, int sortid, int year, int month, int day, int hour, int minute, int columnID, List userid, List roleid) throws Exception;

    int updateUploadFile(int userflag, String filename, String username, int siteid, String fileDir, int articleid, String maintitle, String vicetitle, String summary, String keyword, String source, int sortid, int year, int month, int day, int hour, int minute, List userid, List roleid) throws Exception;
}
