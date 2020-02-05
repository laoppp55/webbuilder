package com.bizwink.webapps.usercenter;

import java.util.List;

public interface IUsercenterManager{
    List getArticleList(String username,int siteid,int columnid,int startrow,int pagesize,int infotype);

    int getArticleCont(String username,int siteid,int columnid,int infotype);
}