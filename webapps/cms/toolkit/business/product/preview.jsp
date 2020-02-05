<%@ page import="java.sql.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!");
        return;
    }

    int siteID = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int imgFlag = authToken.getImgSaveFlag();
    String baseURL = "http://" + request.getHeader("host") + "/webbuilder/";
    String appPath = application.getRealPath("/");
    String sitename = authToken.getSitename();
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    IPublishManager publishManager = PublishPeer.getInstance();

    try {
        String result = publishManager.PreviewArticlePage(articleID, siteID,samsiteid, baseURL, appPath, sitename, imgFlag, columnID);

        if (result == "err1")
            out.println(StringUtil.iso2gb("该栏目没有文章模板，请为其设置模板"));
        else if (result == "err2")
            out.println(StringUtil.iso2gb("获取模板时数据库系统出现错误"));
        else if (result == "articleerror")
            out.println(StringUtil.iso2gb("获取商品时出现错误"));
        else
            out.println(StringUtil.gb2iso4View(result));
    }
    catch (PublishException e) {
        e.printStackTrace();
    }
%>