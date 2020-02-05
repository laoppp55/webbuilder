package com.bizwink.collectionmgr;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2007-11-9
 * Time: 13:07:01
 */
public interface IBasic_AttributesManager {

    Basic_Attributes getBasic_Attributes(int basicId);

    void updateBasic_Attributes(int siteid,Basic_Attributes basicAttr, List basicColumn);

    void addBasicAttr(int siteid,Basic_Attributes basicAttr, List basicColumn);

    List getSiteNameList();

    List getStartUrlList(String siteName);

    List getBasic_Attributes(int start, int range);

    int getMaxId();

    int getBasicId(String siteName);

    void delBasicAttr(int id);

    String getColumnName(int classid);

    int getMaxColumn();

    Basic_Attributes getKeywordsOfSite(int siteId);

    List getColumnNames(int classid);

    void updateSpiderStopFlag(int id, int stopflag);

    void updateBasicAttrKeywordFlag(int id, int flag);

    void update_GlobalConfig(int siteid,GlobalConfig global);

    void addGlobal(int siteid,GlobalConfig global);

    GlobalConfig getGlobalConfig();

    GlobalConfig getProxyConfigOfSite(int siteId);

    void updateSystemRun(int run);

    GlobalConfig getGlobalKeyword();
}
