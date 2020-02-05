<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
     int articleid= ParamUtil.getIntParameter(request,"articleid",-1);
     int columnid=ParamUtil.getIntParameter(request,"columnid",-1);
     int siteid=authToken.getSiteID();
     int flag=ParamUtil.getIntParameter(request,"flag",-1);
     if(flag!=-1)
     {

     }
%>
<script></script>
<form method="post" name="form">
    <input type="hidden" name="flag" value="0">
    <input type="radio" value="0" name="type">
    <input type="button" onclick="">
</form>