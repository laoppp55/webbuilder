package com.bizwink.cms.refers;

import com.bizwink.cms.util.CmsException;
import com.bizwink.cms.orderArticleListManager.orderArticleException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Du Zhenqiang
 * Date: 2008-2-18
 * Time: 17:37:03
 */
public interface IRefersManager {

    String getRefersTo(int articleId, int columnId) throws CmsException;

    List getRefersArticleByColumn(int articleId, int columnId,int siteid) throws CmsException;
    
    String getRefersFrom(int sColumnId, int articleId, int columnId) throws CmsException;

    int getRefersArticlePubFlag(int articleId, int columnId,int siteid) throws orderArticleException;

    void updateRefersArticlePubFlag(int articleId, int columnId, int pubflag,int refertype) throws orderArticleException;

    void remove(int articleId, int columnId) throws orderArticleException;

    int getRefersArticleUseType(int articleId, int columnId) throws orderArticleException;
}
