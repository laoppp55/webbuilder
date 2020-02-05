package com.bizwink.cms.news;

import com.bizwink.cms.sitesetting.SiteInfo;

import java.util.List;
import java.sql.Connection;

public interface IColumnManager {
    int create(Column column) throws ColumnException;

    void create(Column column, List userid, List roleid) throws ColumnException;

    void update(Column column,int siteid) throws ColumnException;

    void update(Column column, int siteid, List userid, List roleid) throws ColumnException;

    boolean duplicateEnName(int parentColumnID, String enName) throws ColumnException;

    void remove(int ID, int siteid) throws ColumnException;

    Column getColumn(int ID) throws ColumnException;

    String getPicsizeNotNullColumnForUp(int columnid,String picattrflag)  throws ColumnException;      //从栏目树向上寻找某个图片大小属性定义不为空的栏目

    Column getColumn(int ID, Connection conn) throws ColumnException;

    Column getSiteRootColumn(int siteid) throws ColumnException;

    List getParentColumns(int ID) throws ColumnException;

    List getColumns() throws ColumnException;

    List<Column> getColumnsForSite(int siteid) throws ColumnException;

    String getIndexExtName(int siteID) throws Exception;

    void updateColumnExtendAttr(Column column) throws ColumnException;

    List getColumnNameforChinesePath(int columnID) throws ColumnException;

    //add by Eric 2006-7-18
    List getSubColumns(int ID) throws ColumnException;

    //add by Eric 2007-7-23
    Column getFirstColumn(int columnid) throws ColumnException;

    Column getParentColumn(int columnid) throws ColumnException;

    String getCidpath(int columnID) throws ColumnException;

    //add by Eric 2007-8-2 for pub rss
    void updateColumnRss(Column column) throws ColumnException;

    String getExtName(int columnId) throws Exception;

    //add by feixiang 2008-01-08
    String getArticleIDForType(String columnIds, String ename, String type);

    //add by feixiang 2008-01-14
    void createType(Producttype pro);

    //add by feixiang 2008-01-14
    List getAllTypeForColumn(String sql);

    //add by feixiang 2008-01-14
    void createSecondType(Producttype pro);

    //add by feixiang 2008-01-14
    void updateTypeCname(int id, String cname, String ename);

    //add by feixiang 2008-01-14
    void deleteTypeValue(int id);

    //add by feixiang 2008-01-14
    List getInheritanceType(int parentID);

    //add by feixiang 2008-01-14
    int getTypeID(int columnID, int tid);

    //add by feixiang 2008-01-17
    List getSecondType(int FirstTypeID);

    //add by feixiang 2008-01-17
    boolean checkArticleType(int articleID, int typeID);

    //add by feixiang 2008-01-24
    String getTypeNames(String ids);

    //add by feixiang 2008-01-24
    String getTypeNames(int articleID);

    //add by feixiang 2008-01-24
    String getTypeIDs(int articleID);

    //add by feixiang 2008-01-28
    List getInheritanceTypeColumnIDs(int columnID);

    //add by feixiang 2008-01-28
    List getTypes(int articleID);

    //add by feixiang 2008-01-28
    List getTypes(String typeids);

    //Add by EricDu  2008-1-25
    void copyColumn(String cids, int targetColumnId);

    //Add by EricDu 2008-2-13
    List getRefersColumnIds(int columnId, int siteid) throws ColumnException;

    //Add by xuzheming 2008.07.28
    List getRefersColumnIds(int columnId) throws ColumnException;

    //Add by xuzheming 2008.07.31
    List getRefersPublishiColumnIds(int columnId, int siteid) throws ColumnException;

    //Add by xuzheming 2008.08.07
    SiteInfo getSiteName(int articleid);

    List getSourceSiteIdAndCID(List selectColumnIds) throws ColumnException;

    //Add by Eric 2008-2-27 for get articles' top types
    List getArticlesTopTypes(int columnId) throws ColumnException;

    String getArticlesType(int columnId, int addlink, String selectTStr, int articleId, int siteID);

    List getReferArticleTypesColumn(List selectTypesList);

    List getReferTypesColumnIds(int columnid);

    List getRefersColumnIds(String columnIds) throws ColumnException;

    //add by xuzheming 2008.7.27 for 文章引用类型
    boolean getUseArticleTypeValue(int cid, int aid, int scid, int siteid);

    //add by xuzheming 2008.7.28 for 栏目SITENAME
    String getHTTPColumnURL(int columnID);

    int getSiteId(int columnid);

    boolean checkUseArticleType(int articleid, int columnid);

    int getArticleCount(int columnID) throws ColumnException;

    Column getRootparentColumn(int siteid);

    List getAuthorized(int siteid, int columnid);

    List getAuthorizeds(int siteid, int articleid);
}