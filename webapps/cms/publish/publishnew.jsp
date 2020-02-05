<%@ page import="java.util.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    int sitetype = authToken.getSitetype();
    int samsiteid = authToken.getSamSiteid();
    int imgflag = authToken.getImgSaveFlag();
    int option = authToken.getPublishFlag();

    int retcode = 1;
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int source = ParamUtil.getIntParameter(request, "source", 0);
    boolean isown = ParamUtil.getBooleanParameter(request, "isown");
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String appPath = application.getRealPath("/");

    IPublishManager publishMgr = PublishPeer.getInstance();
    IColumnManager columnMgr = ColumnPeer.getInstance();

    //从文章发布模块提交的文章发布
    if (source == 0) {
        String selArticleIds = ParamUtil.getParameter(request,"articles");
        String[] articleids = selArticleIds.split(",");
        for (int i = 0; i < articleids.length; i++) {
            int articleID = Integer.parseInt(articleids[i]);
            isown = ParamUtil.getBooleanParameter(request, "isown" + articleID);
            if (articleID > 0) {
                boolean isTemplate = ParamUtil.getBooleanParameter(request, "template" + articleID);
                if (isTemplate) {    //文章发布页面提交 /publish/articles.jsp    发布失败页面提交 /publish/publishfailed.jsp
                    columnID = ParamUtil.getIntParameter(request, "column" + articleID, 0);
                    int type = ParamUtil.getIntParameter(request, "type" + articleID, 0);
                    if (type == 1) {                           //1--发布文章，如果栏目设置多模板发布标志，有一个文章模板发布几套信息
                        retcode = publishMgr.CreateArticlePage(articleID,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,0);
                    } else if (type == 0 || type == 4) {        //0--发布PC栏目页面  4--发布手机栏目模板
                        retcode = publishMgr.CreateColPage(columnID, siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, option, articleID);
                        //如果需要发布RSS，发布RSS
                        Column column = columnMgr.getColumn(columnID);
                        if (column.getRss() == 1) {
                            RssMaker rm = new RssMaker();
                            rm.createRss(siteid, columnID, column, appPath, username, sitename);
                        }
                    } else if (type == 2 || type == 5) {        //2--发布PC首页模板   5--发布手机首页模板
                        retcode = publishMgr.createHomePage(siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, option, articleID);
                    } else if (type == 3 || type == 6) {        //3--发布PC专题页面，6--智能手机专题页面
                        retcode = publishMgr.CreateColPage(columnID, siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, option, articleID);
                    }
                } else {       //发布文章
                    columnID = ParamUtil.getIntParameter(request, "column" + articleID, 0);
                    retcode = publishMgr.CreateArticlePage(articleID,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,0);
                }
            }
        }
    } else if (source == 2) {  //模板管理页面提交 /template/templates.jsp
        int templateID = ParamUtil.getIntParameter(request, "template", 0);
        int modelType = ParamUtil.getIntParameter(request, "modelType", -1);
        columnID = ParamUtil.getIntParameter(request, "column", 0);

        if (modelType == 2 || modelType == 5) {    //发布首页或者手机首页模板
            retcode = publishMgr.createHomePage(siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, templateID);
        } else if (modelType == 0 || modelType == 4) {
            retcode = publishMgr.CreateColPage(columnID, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, templateID);
            //如果需要发布RSS，发布RSS
            Column column = columnMgr.getColumn(columnID);
            if (column.getRss() == 1) {
                RssMaker rm = new RssMaker();
                rm.createRss(siteid, columnID, column, appPath, username, sitename);
            }
        } else if (modelType == 3 || modelType == 6) {      //发布专题模板
            retcode = publishMgr.CreateColPage(columnID, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, templateID);

            //如果需要发布RSS，发布RSS
            Column column = columnMgr.getColumn(columnID);
            if (column.getRss() == 1) {
                RssMaker rm = new RssMaker();
                rm.createRss(siteid, columnID, column, appPath, username, sitename);
            }
        } else if(modelType >= 10) {
            retcode = publishMgr.CreateProgramPage(columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,templateID);
        }
    } else if (source == 3) {    //文章管理页面提交的文章发布操作
        int articleID = ParamUtil.getIntParameter(request, "article", 0);
        retcode = publishMgr.CreateArticlePage(articleID,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,1);
    }

    String jsonData = null;
    if (retcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>
