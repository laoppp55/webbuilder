<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!");
    }

    int isArticle = ParamUtil.getIntParameter(request, "isArticle", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int templateID = ParamUtil.getIntParameter(request, "template", 0);
    IPublishManager publishManager = PublishPeer.getInstance();

    int siteid      = authToken.getSiteID();
    int samsiteid   = authToken.getSamSiteid();
    int imgflag     = authToken.getImgSaveFlag();
    String sitename = authToken.getSitename();
    String appPath  = application.getRealPath("/");
    String baseURL  = "http://" + request.getHeader("host") + "/webbuilder/";

    String result = null;

    result = publishManager.PreviewTemplateForProgram(columnID,templateID,isArticle,siteid,samsiteid,baseURL,appPath,sitename,imgflag);

    if (result == "err")
        out.println(StringUtil.iso2gb("获取模板时数据库系统出现错误"));
    else if (result == "articleerror")
        out.println(StringUtil.iso2gb("获取文章时出现错误"));
    else
        out.println(StringUtil.gb2iso4View(result));
%>
<script language=javascript>document.title = "模板预览";</script>