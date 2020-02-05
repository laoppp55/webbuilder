<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.service.ArticleService" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int articleid = ParamUtil.getIntParameter(request,"article",0);
    ApplicationContext appContext = SpringInit.getApplicationContext();
    Article article = null;
    if (appContext!=null) {
        ArticleService articleService = (ArticleService) appContext.getBean("articleService");
        article = articleService.getArticleByID(BigDecimal.valueOf(articleid));
    }

    int errcode = 0;
    if (article==null) {
        article = new Article();
        errcode = -1;
    }

    Map data = new HashMap();
    data.put("DATA",article);
    data.put("ERRCODE",errcode);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>