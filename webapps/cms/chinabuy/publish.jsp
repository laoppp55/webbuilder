<%@ page import="java.util.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int currentID = ParamUtil.getIntParameter(request, "column", 0);
    int count = ParamUtil.getIntParameter(request, "count", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int source = ParamUtil.getIntParameter(request, "source", 0);
    boolean isown = ParamUtil.getBooleanParameter(request, "isown");
    int retcode = 1;
    int columnID = 0;

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();

    int imgflag = authToken.getImgSaveFlag();
    int option = authToken.getPublishFlag();
    String appPath = application.getRealPath("/");
    String templateName = ParamUtil.getParameter(request, "templateName");
    if (templateName == null)
        templateName = "";
    else
        templateName = StringUtil.gb2iso(templateName);

    IPublishManager publishMgr = PublishPeer.getInstance();
    IColumnManager columnMgr = ColumnPeer.getInstance();
    IPublishWMLManager publishWmlMgr = PublishWMLPeer.getInstance();
    IArticleManager articleMgr = ArticlePeer.getInstance();

    for (int i = 0; i < count; i++) {
        int articleID = ParamUtil.getIntParameter(request, "selected" + i, 0);
        isown = ParamUtil.getBooleanParameter(request, "isown" + i);
        if (articleID > 0) {
            boolean isTemplate = ParamUtil.getBooleanParameter(request, "template" + i);
            if (isTemplate)    //文章发布页面提交 /publish/articles.jsp
            {                  //发布失败页面提交 /publish/publishfailed.jsp
                columnID = ParamUtil.getIntParameter(request, "column" + i, 0);
                int type = ParamUtil.getIntParameter(request, "type" + i, 0);
                if (type == 1) {
                    retcode = publishMgr.CreateArticlePage(articleID, siteid,samsiteid, appPath, sitename, username, imgflag, option);
                } else if (type == 0) {        //发布栏目页面
                    retcode = publishMgr.CreateColPage(columnID, siteid,samsiteid, appPath, sitename, username, imgflag, option, articleID);

                    //如果需要发布RSS，发布RSS
                    Column column = columnMgr.getColumn(currentID);
                    if (column.getRss() == 1) {
                        RssMaker rm = new RssMaker();
                        rm.createRss(siteid, currentID, column, appPath, username, sitename);
                    }
                } else if (type == 2) {        //发布WEB首页
                    retcode = publishMgr.createHomePage(siteid,samsiteid, appPath, sitename, username, imgflag, option, articleID);
                } else if (type == 3) {        //发布WEB专题页面
                    retcode = publishMgr.CreateColPage(columnID, siteid,samsiteid, appPath, sitename, username, imgflag, option, articleID);
                } else if (type == 4) {        //发布WAP栏目页面
                    retcode = publishWmlMgr.CreateColPageForWML(columnID, siteid, appPath, sitename, username, imgflag, option, articleID);
                }

                if (columnMgr.getColumn(columnID).getParentID() == 0) {
                    retcode = publishMgr.createHomePage(siteid,samsiteid, appPath, sitename, username, imgflag, option, articleID);
                } else {
                    retcode = publishMgr.CreateColPage(columnID, siteid,samsiteid, appPath, sitename, username, imgflag, option, articleID);
                }
            } else {       //发布文章                
                columnID = ParamUtil.getIntParameter(request, "column" + i, 0);
                retcode = publishMgr.CreateArticlePage(articleID, siteid,samsiteid, appPath, sitename, username, imgflag, option, isown, columnID);
            }
        }
    }

    if (source == 2)  //模板管理页面提交 /template/templates.jsp
    {
        int templateID = ParamUtil.getIntParameter(request, "template", 0);
        int modelType = ParamUtil.getIntParameter(request, "modelType", -1);
        columnID = ParamUtil.getIntParameter(request, "column", 0);
        System.out.println("columnID=" + columnID);
        if (modelType == 2) {    //发布首页
            retcode = publishMgr.createHomePage(siteid,samsiteid, appPath, sitename, username, imgflag, option, templateID);
        } else if (modelType == 0) {
            retcode = publishMgr.CreateColPage(currentID, siteid,samsiteid, appPath, sitename, username, imgflag, option, templateID);

            //如果需要发布RSS，发布RSS
            Column column = columnMgr.getColumn(currentID);
            if (column.getRss() == 1) {
                RssMaker rm = new RssMaker();
                rm.createRss(siteid, currentID, column, appPath, username, sitename);
            }
        } else if (modelType == 3) {
            retcode = publishMgr.CreateColPage(currentID, siteid,samsiteid, appPath, sitename, username, imgflag, option, templateID);

            //如果需要发布RSS，发布RSS
            Column column = columnMgr.getColumn(currentID);
            if (column.getRss() == 1) {
                RssMaker rm = new RssMaker();
                rm.createRss(siteid, currentID, column, appPath, username, sitename);
            }
        } else if (modelType == 4) {
            retcode = publishWmlMgr.CreateColPageForWML(columnID, siteid, appPath, sitename, username, imgflag, option, templateID);
        } else if(modelType >= 10) {
            retcode = publishMgr.CreateProgramPage(currentID,siteid,samsiteid,appPath,sitename,username,imgflag,option,templateID);
        }
    }

    if (source == 3)   //文章编辑管理页面提交 /article/article.jsp
    {
        int articleID = ParamUtil.getIntParameter(request, "article", 0);
        retcode = publishMgr.CreateArticlePage(articleID, siteid,samsiteid, appPath, sitename, username, imgflag, option, isown, currentID);
    }
    if (source == 1)         //重新发布
        response.sendRedirect("republish.jsp?column=" + currentID + "&msgno=" + retcode + "&start=" + start + "&range=" + range);
    else if (source == 2)    //发布模板
        if (currentID > 0)           //发布模板
            response.sendRedirect("../template/templates.jsp?column=" + currentID + "&msgno=" + retcode + "&start=" + start + "&range=" + range + "&templateName=" + templateName);
        else                        //发布程序模板
            response.sendRedirect("../template/templatesforprogram.jsp?column="+currentID+"&msgno="+retcode+"&start="+start+"&range="+range);
    else if (source == 3)    //发布文章
        response.sendRedirect("articles.jsp?column=" + currentID + "&msgno=" + retcode + "&start=" + start + "&range=" + range);
    else if(source == 4)     //发布失败的文章
        response.sendRedirect("publishfailed.jsp?column=" + currentID + "&msg=" + retcode + "&start=" + start + "&range=" + range);
    else                     //正常发布
        response.sendRedirect("articles.jsp?column=" + currentID + "&msgno=" + retcode + "&start=" + start + "&range=" + range);
%>