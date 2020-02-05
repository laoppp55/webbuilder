<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int id = ParamUtil.getIntParameter(request,"id",0);
    int flag = ParamUtil.getIntParameter(request,"flag",-1);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        IDefineManager fMgr = DefinePeer.getInstance();
        int code = fMgr.updateSurveyFlag(id,flag);
    }
    response.sendRedirect("index.jsp");
%>