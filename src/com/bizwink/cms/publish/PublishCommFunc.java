package com.bizwink.cms.publish;

import java.io.*;

import com.bizwink.cms.extendAttr.ExtendAttrPeer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.processTag.*;
import com.bizwink.cms.xml.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.markManager.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.sitesetting.ISiteInfoManager;

public class PublishCommFunc {
    private static PublishCommFunc singleton = new PublishCommFunc();

    public static synchronized PublishCommFunc getInstance() {
        return singleton;
    }

    public int findNodeInTree(node[] arr, int nodeID) {
        int nodenum = 0;
        while (arr[nodenum].getId() != nodeID) {
            nodenum++;
        }
        return nodenum;
    }

    public String getDirName(Tree columnTree, int columnID) {
        node[] treeNodes = columnTree.getAllNodes();
        String dir = "";
        int nodenum = 0;
        int parentColumnID = 0;

        while (columnID != 0) {
            nodenum = findNodeInTree(treeNodes, columnID);
            dir = treeNodes[nodenum].getEnName() + java.io.File.separator + dir;
            parentColumnID = treeNodes[nodenum].getLinkPointer();
            columnID = parentColumnID;
        }
        return dir;
    }

    public String[] ProcessTag(String[] tag, int articleID, int columnID, String appPath, int siteID,int samsiteid, String sitename, int imgflag, int templateID, int modeltype, String username, boolean isPreview) {
        String tagHTML[] = new String[tag.length];
        mark mk = new mark();
        int mktype = 0;
        String attrName = null;
        ITagManager tagManager = TagManager.getInstance();
        IMarkManager markMgr = markPeer.getInstance();
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        String fragPath ="";

        String extpath = appPath + "sites" + File.separator + sitename + File.separator;
        int markID = 0;
        int inc_flag = 0;
        String xmlTemplate = extendMgr.getXMLTemplate(columnID);
        for (int i = 0; i < tagHTML.length; i++) {
            XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tag[i]);
            if (properties.getName().equals("MARKID")) {
                String name = properties.getProperty(properties.getName());
                if (name.indexOf("_") > -1)
                    markID = Integer.parseInt(name.substring(0, name.indexOf("_")));
                else{
                    markID = Integer.parseInt(name);
                }

                try {
                    mk = markMgr.getAMark(markID);
                    if (mk != null) {
                        inc_flag = mk.getInnerHTMLFlag();
                        tag[i] = mk.getContent();
                        mktype = mk.getMarkType();
                        if (tag[i].indexOf("[HTMLCODE]") >-1) {
                            tag[i] = StringUtil.replace(tag[i],"<","&lt;");
                            tag[i] = StringUtil.replace(tag[i],">","&gt;");
                        }
                        tag[i] = StringUtil.gb2iso4View(tag[i]);
                        tag[i] = StringUtil.replace(tag[i], "[", "<");
                        tag[i] = StringUtil.replace(tag[i], "]", ">");
                        tag[i] = StringUtil.replace(tag[i], "{^", "[");
                        tag[i] = StringUtil.replace(tag[i], "^}", "]");

                        fragPath =  appPath + "sites" + File.separator + sitename + File.separator + "includefile" + File.separator + markID;
                        //mktype=1     mktype=2     mktype=3    mktype=4
                        //mktype=5     mktype=6     mktype=7    代表公用的HTML片段    mktype=8
                        //mktype=9     mktype=10   文章中的图片特效标记               mktype=21
                        //mktype=22    mktype=50   视频播放标志
                        //mktype=11--20     mktype=23 mktype=24
                        //mktype=100--105
                        if ((mktype!=7 && mktype<10)|| mktype==12 || mktype==21 || mktype==22 || mktype==50) {
                            properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tag[i]);
                            attrName = properties.getName();
                        } else if (mktype==7){
                            attrName = "HTMLMARK";                                         //marktype=7代表公用的HTML片段
                        } else if (mktype==10){
                            attrName = "TURN_PIC";                                         //文章中的图片特效标记
                        } else if ((mktype>=11 && mktype<=20) || mktype==23 || mktype==24 || (mktype>=100 && mktype<=105)) {
                            attrName = "HTMLCODE";
                        }
                    } else {
                        System.out.println("标记" + markID + "的内容为空，或者标记不存在！！！");
                    }
                }
                catch (markException e) {
                    e.printStackTrace();
                }
            } else {
                attrName = properties.getName();
            }

            try {
                if ("ARTICLE_LIST".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleList(1,markID,properties, articleID, columnID, siteID, sitename, imgflag, username, modeltype,fragPath, isPreview);
                } else if ("SUBARTICLE_LIST".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleList(2, markID,properties, articleID, columnID, siteID, sitename, imgflag, username, modeltype,fragPath, isPreview);
                } else if ("BROTHER_LIST".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleList(3, markID,properties, articleID, columnID, siteID, sitename, imgflag, username, modeltype,fragPath, isPreview);
                } else if ("RECOMMEND_LIST".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatRecommendArticleList(3, markID,properties, articleID, columnID, siteID, sitename, imgflag, username, modeltype,fragPath, isPreview);
                } else if ("ARTICLE_COUNT".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleCount(0, articleID, properties, columnID,siteID);
                } else if ("PLAYMEDIA".compareTo(attrName) == 0) {                   //视频播放标志
                    tagHTML[i] = tagManager.playMedia(0, articleID, properties, columnID,siteID);
                } else if ("SUBARTICLE_COUNT".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleCount(1, articleID, properties, columnID, siteID);
                } else if ("ARTICLE_CONTENT".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleContent(properties, articleID, columnID,templateID);
                } else if ("ARTICLE_MAINTITLE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleMainTitle(properties, articleID, sitename, siteID);
                } else if ("ARTICLEPIC".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticlePic(properties, articleID, sitename, siteID);
                } else if ("ARTICLE_VICETITLE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleViceTitle(properties, articleID, sitename, siteID);
                } else if ("ARTICLE_AUTHOR".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleAuthor(properties, articleID, sitename, siteID);
                } else if ("ARTICLE_PT".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticlePT(properties, articleID);
                } else if ("ARTICLE_PULISHDATE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticlePULISHDATE(properties, articleID, sitename, siteID);
                } else if ("ARTICLE_URL".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleURL(properties, articleID,sitename, siteID);
                } else if ("ARTICLE_SUMMARY".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleSummary(articleID);
                } else if ("ARTICLE_SOURCE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleSource(properties, articleID, sitename, siteID);
                } else if ("ARTICLE_INDEXNUM".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleIndexNum(articleID);
                } else if ("ARTICLE_KEYWORD".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleKeyword(articleID);
                } else if ("ARTICLESTATUS".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleStatus(articleID);
                } else if ("COLUMNNAME".compareTo(attrName) == 0 || "PARENT_COLUMNNAME".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatColumnName(properties, columnID);
                } else if ("COLUMN_LIST".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatColumnList(1, properties, sitename, siteID, columnID, username,modeltype,fragPath, isPreview);
                } else if ("SUBCOLUMN_LIST".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatColumnList(2, properties, sitename, siteID, columnID, username, modeltype,fragPath, isPreview);
                } else if ("CHINESE_PATH".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatChinesePath(properties, columnID, siteID,samsiteid, modeltype);
                } else if ("ENGLISH_PATH".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatEnglishPath(properties, columnID, siteID,samsiteid, modeltype);
                } else if ("RELATED_ARTICLE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.relatedArticleList(properties, articleID, siteID, sitename,columnID,0,0);
                    //tagHTML[i] = tagManager.relatedArticleList(properties, articleID, siteID, sitename,modeltype, columnID);
                } else if ("COMMEND_ARTICLE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.processCommendArticle(properties, siteID, columnID, username, sitename,modeltype, fragPath, isPreview);
                } else if ("TOP_STORIES".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.processTopStories(properties, siteID, columnID, username, sitename,modeltype, fragPath, isPreview);
                } else if ("PREV_ARTICLE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.processNextArticleLink(properties, articleID, columnID, siteID, 0);
                } else if ("NEXT_ARTICLE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.processNextArticleLink(properties, articleID, columnID, siteID, 1);
                } else if ("NAVBAR".compareTo(attrName) == 0) {
                    tagHTML[i] = tag[i];
                } else if ("ARTICLEID".compareTo(attrName) == 0) {
                    tagHTML[i] = String.valueOf(articleID);
                } else if ("ARTICLE_DOCLEVEL".compareTo(attrName) == 0) {
                    IArticleManager articleMgr = ArticlePeer.getInstance();
                    tagHTML[i] = String.valueOf(articleMgr.getArticle(articleID).getDocLevel());
                } else if ("COLUMNID".compareTo(attrName) == 0) {
                    tagHTML[i] = String.valueOf(columnID);
                } else if ("RELATEID".compareTo(attrName) == 0 || "RELATED_DATA".compareTo(attrName) == 0 ||
                        "RELATED_VICETITLE".compareTo(attrName) == 0 || "RELATED_SOURCE".compareTo(attrName) == 0 ||
                        "RELATED_SUMMARY".compareTo(attrName) == 0 || "RELATED_AUTHOR".compareTo(attrName) == 0 ||
                        "RELATED_URL".compareTo(attrName) == 0 || "PARENT_PATH".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatRelateArticleAttribute(articleID, attrName);
                } else if ("PRODUCT_SALEPRICE".compareTo(attrName) == 0 || "PRODUCT_INPRICE".compareTo(attrName) == 0 ||
                        "PRODUCT_MARKETPRICE".compareTo(attrName) == 0 || "PRODUCT_WEIGHT".compareTo(attrName) == 0 ||
                        "PRODUCT_STOCK".compareTo(attrName) == 0 || "PRODUCT_PIC".compareTo(attrName) == 0 ||
                        "PRODUCT_BIGPIC".compareTo(attrName) == 0 || "PRODUCT_BRAND".compareTo(attrName) == 0 ||
                        "PRODUCT_VIPPRICE".compareTo(attrName) == 0 || "PRODUCT_SCORE".compareTo(attrName) == 0 ||
                        "PRODUCT_VOUCHER".compareTo(attrName) == 0 || "ARTICLE_PATH".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatProductAttrbuite(articleID, attrName, sitename);
                } else if ("COMPANYNAME".compareTo(attrName) == 0 || "TELEPHONE".compareTo(attrName) == 0 ||
                        "EMAIL".compareTo(attrName) == 0 || "WEIBO".compareTo(attrName) == 0|| "QQ".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatCompanyAttrbuite(articleID, attrName, sitename,siteID);
                } else if ("ARTICLE_TYPE".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatArticleType(properties, articleID, siteID);
                } else if ("PAGINATION".compareTo(attrName) == 0) {
                    tagHTML[i] = tag[i];
                } else if ("COLUMNURL".compareTo(attrName) == 0) {
                    IArticleManager articleMgr = ArticlePeer.getInstance();
                    IColumnManager columnMgr = ColumnPeer.getInstance();
                    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
                    Article article = articleMgr.getArticle(articleID);
                    int the_article_siteid = 0;
                    if (article != null) {
                        the_article_siteid = article.getSiteID();
                    }
                    if (the_article_siteid != 0) {
                        if (the_article_siteid == siteID)   //是本站点的文章只返回本站点的路径
                            tagHTML[i] = columnMgr.getColumn(columnID).getDirName();
                        else {                              //不是本站点的文章，返回图片的绝对路径
                            String samsitename = siteMgr.getSiteInfo(the_article_siteid).getDomainName();
                            tagHTML[i] = "http://" + samsitename + columnMgr.getColumn(columnID).getDirName();
                        }
                    } else {
                        tagHTML[i] = columnMgr.getColumn(columnID).getDirName();
                    }
                } else if ("HTMLMARK".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.getHtmlMarkContent(markID,inc_flag,tag[i], sitename,siteID, columnID, username, fragPath);
                } else if ("LINK".compareTo(attrName) == 0)  {  //处理文章模板中的自定义属性
                    tagHTML[i] = tagManager.processLink(properties, articleID, sitename,modeltype);
                } else if ("PAGINATION".compareTo(attrName) == 0){
                    tagHTML[i] = tag[i];
                } else if ("TURN_PIC".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatProductTurnPicOne(tag[i],markID,articleID,sitename,isPreview);
                } else if ("HTMLCODE".compareTo(attrName) == 0) {
                    tagHTML[i] = tag[i];
                /*} else if ("SITE_LOGO".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatSitelogo(siteID);
                } else if ("SITE_BANNER".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatSitebanner(siteID);
                } else if ("SITE_MAIN_NAVIGATOR".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatMainNavigator(siteID);
                } else if ("SITE_SIDE_NAVIGATOR".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatSideNavigator(siteID);
                } else if ("COPY_RIGHT".compareTo(attrName) == 0) {
                    tagHTML[i] = tagManager.formatCopyright(siteID);*/
                }else if ("INCLUDE_FILE".compareTo(attrName) == 0) {  //包含文件
                    tagHTML[i] = tagManager.formatInclude(properties, siteID, columnID, username, sitename, fragPath,appPath, isPreview);
                }
                else if ("DEFINEINFO".compareTo(attrName) == 0) {  //调查信息
                    tagHTML[i] = tagManager.formatDefineInfo(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath) ;
                }
                else if ("LEAVEMESSAGEINFO".compareTo(attrName) == 0) {  //留言表单
                    tagHTML[i] = tagManager.formatLeavemessageInfo(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("LEAVEMESSAGELIST".compareTo(attrName) == 0) {  //留言列表
                    tagHTML[i] = tagManager.formatLeavemessageList(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("SHOPPINGCAR_RESULT".compareTo(attrName) == 0) {  //购物车
                    tagHTML[i] = tagManager.formatShoppongCarList(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("ORDER_RESULT".compareTo(attrName) == 0) {  //订单生成
                    tagHTML[i] = tagManager.formatOrderResult(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("ORDERSEARCH_RESULT".compareTo(attrName) == 0) {  //订单查询
                    tagHTML[i] = tagManager.formatOrderSearchResult(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("ORDEDETAIL_RESULT".compareTo(attrName) == 0) {  //订单详细
                    tagHTML[i] = tagManager.formatOrderDeatilResult(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("CLICKNUM".compareTo(attrName) == 0) {  //包含文件
                    tagHTML[i] = tagManager.formatArticleClickNum(properties, siteID, columnID, username, sitename, fragPath, isPreview, articleID);
                }
                else if ("LOGINFORM".compareTo(attrName) == 0) {  //登录表单
                    tagHTML[i] = tagManager.formatLoginForm(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("PROGRAMELOGINFORM".compareTo(attrName) == 0) {  //程序模板登录表单
                    tagHTML[i] = tagManager.formatLoginFormForProgram(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("LOGINDISPLAY".compareTo(attrName) == 0) {  //登录信息显示
                    tagHTML[i] = tagManager.formatLoginDisplay(properties, siteID, columnID, username, sitename, fragPath, isPreview, appPath,markID) ;
                }
                else if ("SEECOOKIE".compareTo(attrName) == 0) {
                    tagHTML[i] =tagManager.getSeeCookie(properties, siteID, extpath, articleID, columnID);
                }
                else if (attrName!=null){
                    tagHTML[i] = tagManager.processExtendAttribute(properties,xmlTemplate,articleID, columnID, sitename, tag[i]);
                } else {
                    tagHTML[i] = tag[i];
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return tagHTML;
    }
}