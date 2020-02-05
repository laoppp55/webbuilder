package com.bizwink.cms.toolkit.websites;

import com.bizwink.cms.toolkit.websites.*;

import java.util.List;


public interface IWebsiteManager {
    public WebsiteClass getCompanyClass(int id);

    void create(WebsiteClass companyclass);

    void remove(int id,int siteID) throws Exception;

    void update(WebsiteClass companyclass,int siteid);

    String getIndexExtName(int siteID) throws Exception;

    boolean duplicateEnName(int parentColumnID, String enName);

}

