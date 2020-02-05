<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.multimedia.IMultimediaManager" %>
<%@ page import="com.bizwink.cms.multimedia.MultimediaPeer" %>
<%@ page import="com.bizwink.cms.multimedia.Attechment" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-6-3
  Time: 上午10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //请为程序添加必要的注释
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int articleID = ParamUtil.getIntParameter(request,"article",0);
    IMultimediaManager multimediaManager = MultimediaPeer.getInstance();
    List<Attechment> attechments = multimediaManager.getAttechments(articleID);

    Gson gson = new Gson();
    String jsondata=null;
    if (attechments.size() > 0){
        jsondata = gson.toJson(attechments);
        out.print(jsondata);
        out.flush();
    } else {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(-1);
        errorMessage.setErrmsg("向WEB服务器发布文件失败！");
        errorMessage.setModelname("uplodAttechment页面模块");
        jsondata = gson.toJson(errorMessage);
        out.print(jsondata);
        out.flush();
    }
    return;
%>