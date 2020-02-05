<%@ page import="java.util.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.po.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.orderArticleListManager.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.ArticleService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();
    int imgflag = authToken.getImgSaveFlag();
    int option = authToken.getPublishFlag();
    String appPath = application.getRealPath("/");

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearch = ParamUtil.getBooleanParameter(request, "doSearch");
    String maintitle = ParamUtil.getParameter(request,"title");
    if (maintitle!=null) maintitle = filter.excludeHTMLCode(URLDecoder.decode(maintitle,"utf-8"));
    String vicetitle = ParamUtil.getParameter(request,"vicetitle");
    if (vicetitle!=null) vicetitle = filter.excludeHTMLCode(URLDecoder.decode(vicetitle,"utf-8"));
    String author = ParamUtil.getParameter(request,"author");
    if (author!=null) author = filter.excludeHTMLCode(URLDecoder.decode(author,"utf-8"));
    String source = ParamUtil.getParameter(request,"source");
    if (source!=null) source = filter.excludeHTMLCode(URLDecoder.decode(source,"utf-8"));
    String spubdate = ParamUtil.getParameter(request,"spubdate");
    if (spubdate!=null) spubdate = filter.excludeHTMLCode(URLDecoder.decode(spubdate,"utf-8"));
    String epubdate = ParamUtil.getParameter(request,"epubdate");
    if (epubdate!=null) epubdate = filter.excludeHTMLCode(URLDecoder.decode(epubdate,"utf-8"));

    if (SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 50)) {
        username = null;
    }

    int retcode = 1;
    List<com.bizwink.po.Article> articleList = new ArrayList<>();
    int totalCount=0;
    ArticleService articleService = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        articleService = (ArticleService) appContext.getBean("articleService");
        if (!doSearch) {
            totalCount = articleService.getArticlesCountByColumnidsForPublish(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),null,null);
        } else {
            totalCount = articleService.getSearchArticlesCountForPublish(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),null,maintitle,vicetitle,author,source, spubdate,epubdate,null, null);
        }
    }

    int count = 0;
    int pageno = 0;
    //每次发布range篇文章，计算需要发布的次数
    int extra = totalCount % range;
    if (extra>0)
        count = totalCount/range + 1;
    else
        count = totalCount/range;

    System.out.println("doSearch:" + doSearch);
    System.out.println("flag:" + flag);
    System.out.println("ascdesc:" + ascdesc);
    System.out.println("range:" + range);
    System.out.println("column:" + columnID);

    System.out.println("maintitle:" + maintitle);
    System.out.println("vicetitle:" + vicetitle);
    System.out.println("author:" + author);
    System.out.println("source:" + source);
    System.out.println("spubdate:" + spubdate);
    System.out.println("epubdate:" + epubdate);

    System.out.println("totalCount===" + totalCount);
    System.out.println("count===" + count);

    response.setHeader("Content-Type","application/x-javascript");
    if (articleService != null) {
        IPublishManager publishMgr = PublishPeer.getInstance();
        int publishArticleCount = 0;
        for (int ii = 0; ii < count; ii++) {
            if (!doSearch) {
                articleList = articleService.getArticlesInPageByColumnidsForPublish(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), null, BigDecimal.valueOf(100), BigDecimal.valueOf(ii * range), BigDecimal.valueOf((ii+1) * range), flag, ascdesc);
            } else {
                articleList = articleService.getSearchArticlesInPageForPublish(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(ii * range), BigDecimal.valueOf((ii + 1) * range), null, maintitle, vicetitle, author, source, spubdate, epubdate, BigDecimal.valueOf(100), null, BigDecimal.valueOf(flag), BigDecimal.valueOf(ascdesc));
            }
            System.out.println("ii===" + ii + "==" + articleList.size() + "==" + count);
            for(int jj=0; jj<articleList.size(); jj++) {
                Article article = (Article)articleList.get(jj);
                columnID = article.getCOLUMNID().intValue();
                int articleid = article.getID().intValue();
                boolean isown = (article.getIsown().intValue()==1)?true:false;
                retcode = publishMgr.CreateArticlePage(articleid,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,0);
                System.out.println("publish article:" + article.getMAINTITLE());
            }

            publishArticleCount = publishArticleCount + articleList.size();
            out.println(publishArticleCount + "/" + totalCount);
            out.flush();
            Thread.currentThread().sleep(500);
        }
    }

    System.out.println("complete publiahs article");

    /*IPublishManager publishMgr = PublishPeer.getInstance();
    IColumnManager columnMgr = ColumnPeer.getInstance();
    IOrderArticleListManager orderArticleMgr = orderArticleListPeer.getInstance();
    List publish_article_list = orderArticleMgr.getPublishArticlesInColumn(columnID,siteid);

    for (int i = 0; i < publish_article_list.size(); i++) {
        Article article = (Article)publish_article_list.get(i);
        int articleid = article.getID();
        if (articleid > 0) {
            boolean isTemplate = article.getIsTemplate();
            if (isTemplate) {
                columnID = article.getColumnID();
                int type = article.getIsArticleTemplate();
                if (type == 0 || type == 4) {        //发布栏目页面
                    retcode = publishMgr.CreateColPage(columnID, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, articleid);

                    //如果需要发布RSS，发布RSS
                    Column column = columnMgr.getColumn(columnID);
                    if (column.getRss() == 1) {
                        RssMaker rm = new RssMaker();
                        rm.createRss(siteid, columnID, column, appPath, username, sitename);
                    }
                } else if (type == 2 || type == 5) {        //发布首页
                    retcode = publishMgr.createHomePage(siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, articleid);
                } else if (type == 3 || type == 6) {        //发布专题页面和智能手机栏目页面
                    retcode = publishMgr.CreateColPage(columnID, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, articleid);
                }
            } else {       //发布文章
                columnID = article.getColumnID();
                boolean isown = article.isIsown();
                retcode = publishMgr.CreateArticlePage(articleid, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, isown, columnID);
            }
        }

        if (count<20)
            count = count + 1;
        else {
            out.println(count);
            out.flush();
            count = 0;
        }
    }

    String jsonData = null;
    if (retcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
    */
%>