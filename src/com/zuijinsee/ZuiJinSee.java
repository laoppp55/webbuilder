package com.zuijinsee;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.processTag.ITagManager;
import com.bizwink.cms.processTag.TagManager;
import com.bizwink.cms.register.IRegisterManager;
import com.bizwink.cms.register.RegisterPeer;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.sitesetting.SiteInfo;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.extendAttr.ExtendAttrPeer;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-1-30
 * Time: 20:26:19
 * To change this template use File | Settings | File Templates.
 */
public class ZuiJinSee {
    public String replaceAllContent(String content, Article article,String the_sitename) {
        String style = "";
        if (article != null) {
            String dirname = article.getDirName();
            String maintitle = article.getMainTitle();
            Timestamp createdate = null;
            createdate = article.getCreateDate();
            int siteid=article.getSiteID();
            ISiteInfoManager sitepeer= SiteInfoPeer.getInstance();
            IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
            String sitename="";
            int the_siteid = 0;
            try{
                SiteInfo site=sitepeer.getSiteInfo(siteid);
                sitename=site.getDomainName();
                the_siteid = sitepeer.getSiteID(the_sitename);
            }catch(Exception e){
                e.printStackTrace();
                System.out.println(""+e.toString());
            }

            if (content.indexOf("<%%URL%%>") != -1) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String dateString = "";

                if (createdate == null) {

                } else {
                    dateString = formatter.format(createdate);
                }
                String url=dirname+dateString+"/"+article.getID()+".shtml";
                content = StringUtil.replace(content, "<%%URL%%>", url);

            }
            if (content.indexOf("<%%DATA%%>") != -1) {
                content = StringUtil.replace(content, "<%%DATA%%>",maintitle);

            }
            if (content.indexOf("<%%VICETITLE%%>") != -1) {
                content = StringUtil.replace(content, "<%%VICETITLE%%>",article.getViceTitle());

            }
            if (content.indexOf("<%%ASOURCE%%>") != -1) {
                content = StringUtil.replace(content, "<%%ASOURCE%%>",article.getSource());

            }
            //处理文章的相应下载
            if (content.indexOf("<%%DOWNFILE%%>") > -1) {
                String df = article.getDownfilename();
                DateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
                String ymd = format.format(article.getCreateDate());
                if (df != null)
                    content = StringUtil.replace(content, "<%%DOWNFILE%%>", dirname + ymd + File.separator + "download" + File.separator + df);
                else
                    content = StringUtil.replace(content, "<%%DOWNFILE%%>", "#");
            }
            if (style.indexOf("<%%AUTHOR%%>") > -1) {
                content = StringUtil.replace(content, "<%%AUTHOR%%>",article.getAuthor());

            }
            String articlepic = article.getArticlepic();
            if (content.indexOf("<%%ARTICLEPIC%%>") > -1 && articlepic != null) {
                if (!isConvert2Image("<%%ARTICLEPIC%%>", content))
                    articlepic = toImage(articlepic, sitename, article, siteid);
                content = StringUtil.replace(content, "<%%ARTICLEPIC%%>", articlepic);
                content = content.replaceAll("<%%ARTICLEPIC%%>", articlepic);
            }

            //文章路径
            if (content.indexOf("<%%ARTICLE_PATH%%>") > -1) {
                if (article.getSiteID() != the_siteid) {
                    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
                    try {
                        String art_sitename = siteMgr.getSiteInfo(article.getSiteID()).getDomainName();
                        content = StringUtil.replace(content,"<%%ARTICLE_PATH%%>", "http://" + art_sitename + article.getDirName()) ;
                    } catch (SiteInfoException exp) {
                        exp.printStackTrace();
                    }
                } else {
                    content = StringUtil.replace(content,"<%%ARTICLE_PATH%%>", "http://" + sitename + article.getDirName());
                }
            }

            //替换商品属性
            if (content.indexOf("<%%PRODUCT_") > -1) {
                if (content.indexOf("<%%PRODUCT_SALEPRICE%%>") > -1)
                    content = StringUtil.replace(content, "<%%PRODUCT_SALEPRICE%%>", String.valueOf(article.getSalePrice()));
                if (content.indexOf("<%%PRODUCT_INPRICE%%>") > -1)
                    content = StringUtil.replace(content, "<%%PRODUCT_INPRICE%%>", String.valueOf(article.getInPrice()));
                if (content.indexOf("<%%PRODUCT_MARKETPRICE%%>") > -1)
                    content = StringUtil.replace(content, "<%%PRODUCT_MARKETPRICE%%>", String.valueOf(article.getMarketPrice()));
                if (content.indexOf("<%%PRODUCT_WEIGHT%%>") > -1)
                    content = StringUtil.replace(content, "<%%PRODUCT_WEIGHT%%>", String.valueOf(article.getProductWeight()));
                if (content.indexOf("<%%PRODUCT_STOCK%%>") > -1)
                    content = StringUtil.replace(content, "<%%PRODUCT_STOCK%%>", String.valueOf(article.getStockNum()));
                if (content.indexOf("<%%PRODUCT_BRAND%%>") > -1) {
                    if (article.getBrand() != null)
                        content = StringUtil.replace(content, "<%%PRODUCT_BRAND%%>", article.getBrand());
                    else
                        content = StringUtil.replace(content, "<%%PRODUCT_BRAND%%>", "");
                }
                String picname = article.getProductPic();
                if (content.indexOf("<%%PRODUCT_PIC%%>") > -1 && picname != null) {
                    if (!isConvert2Image("<%%PRODUCT_PIC%%>", content))
                        picname = toImage(picname, sitename, article, siteid);
                    content = StringUtil.replace(content, "<%%PRODUCT_PIC%%>", picname);
                }
                picname = article.getProductBigPic();
                if (content.indexOf("<%%PRODUCT_BIGPIC%%>") > -1 && picname != null) {
                    if (!isConvert2Image("<%%PRODUCT_BIGPIC%%>", content))
                        picname = toImage(picname, sitename, article, siteid);
                    content = StringUtil.replace(content, "<%%PRODUCT_BIGPIC%%>", picname);
                }
            }
            //替换扩展属性
            if (content.indexOf("<%%") > -1 && content.indexOf("%%>") > -1) {
                List matchList = new ArrayList();
                Pattern p = Pattern.compile("\\<%%[^\\<\\>%]*%%\\>", Pattern.CASE_INSENSITIVE);
                Matcher matcher = p.matcher(content);
                while (matcher.find()) {
                    String matchStr = content.substring(matcher.start() + 3, matcher.end() - 3);
                    matchList.add(matchStr);
                }
                try{
                    matchList = extendMgr.getArticleExtendValue(article.getID(), matchList);
                }catch(Exception e){

                }
                for (int i = 0; i < matchList.size(); i++) {
                    String temp = (String) matchList.get(i);
                    String ename = temp.substring(0, temp.indexOf("="));
                    String value = temp.substring(temp.indexOf("=") + 1);


                    content = StringUtil.replace(content, "<%%" + ename + "%%>", value);
                }

                /*
                List attrList = extendMgr.getArticleAttr(article.getID());
                for (int i=0; i<attrList.size(); i++)
                {
                  ExtendAttr extend = (ExtendAttr)attrList.get(i);
                  String ename = extend.getEName();
                  for (int j=0; j<matchList.size(); j++)
                  {
                    if (ename.equalsIgnoreCase((String)matchList.get(j)))
                    {
                      String value = processAttrValue(extend, columnID, ename, "", sitename);
                      style = style.replaceAll("<%%" + ename + "%%>", value);
                      break;
                    }
                  }
                }
                */

                //去掉多余的扩展属性标记
                matcher = p.matcher(content);
                if (matcher.find()) {
                    //style = style.substring(0, matcher.start()) + style.substring(matcher.end());
                    content = matcher.replaceAll("");
                }
            }
            //  System.out.println("content="+content);
            return content;
        } else {
            return "";
        }
    }
    private boolean isConvert2Image(String mark, String content) {
        boolean is = false;
        content = StringUtil.replace(content, "<%%ARTICLE_PATH%%>", "");
        Pattern p = Pattern.compile("\\<img[^<>]*src\\s*=\\s*[^<>]*" + mark + "[^<>]*\\>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(content);
        if (matcher.find()) is = true;

        return is;
    }
    private String toImage(String filename, String sitename, Article article, int siteID) {
        if (filename != null) {
            if (filename.toLowerCase().indexOf(".gif") != -1 || filename.toLowerCase().indexOf(".jpg") != -1 ||
                    filename.toLowerCase().indexOf(".jpeg") != -1 || filename.toLowerCase().indexOf(".png") != -1 ||
                    filename.toLowerCase().indexOf(".bmp") != -1) {
                IColumnManager columnMgr = ColumnPeer.getInstance();
                try {
                    int otherSiteID = columnMgr.getColumn(article.getColumnID()).getSiteID();
                    if (siteID == otherSiteID) {
                        if (filename.trim().length() == 12)
                            filename = "<img src=" + article.getDirName() + "images/" + filename.trim() + " border=0>";
                        else if (filename.trim().length() == 15)
                            filename = "<img src=/webbuilder/sites/" + sitename + article.getDirName() + "images/" + filename.trim() + " border=0>";
                        else
                            filename = "<img src=" + filename.trim() + " border=0>";
                    } else {
                        IRegisterManager registerMgr = RegisterPeer.getInstance();
                        sitename = registerMgr.getSite(otherSiteID).getSiteName();
                        sitename = StringUtil.replace(sitename, "_", ".");
                        if (filename.trim().length() == 12)
                            filename = "<img src=" + article.getDirName() + "images/" + filename.trim() + " border=0>";
                        else if ((filename.trim().length() == 15))
                            filename = "<img src=http://" + sitename + article.getDirName() + "images/" + filename.trim() + " border=0>";
                        else
                            filename = "<img src=http://" + sitename + "/" + filename.trim() + " border=0>";
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            filename = "";
        }
        return filename;
    }

}
