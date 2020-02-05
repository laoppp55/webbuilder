package com.chinabuyregister;

import com.bizwink.cms.register.Register;
import com.bizwink.cms.register.resinConfig;
import com.bizwink.cms.util.CmsException;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.news.Article;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-10-16
 * Time: 14:20:06
 * To change this template use File | Settings | File Templates.
 */
public interface IChinaBuyRegiterManager {
    public List getChinabuyIndex(int columnid,int num);
    public Column getLikeCnameColumn(String cname,int siteid);
     public List page(int ipage, int pic) ;
    public int count(int pic);
    public Register getReg(int siteid);
    public Article getArticle(int ID);

}
