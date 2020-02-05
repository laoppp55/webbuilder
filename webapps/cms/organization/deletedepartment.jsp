<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.IUserManager" %>
<%@ page import="com.bizwink.cms.security.UserPeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@page contentType="text/html;charset=utf-8"  %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken, 54))
	{
		response.sendRedirect("../error.jsp?message=无管理用户的权限");
		return;
	}
    int id = ParamUtil.getIntParameter(request,"id",0);
    IUserManager uMgr = UserPeer.getInstance();
    int code = uMgr.deleteDepartment(id);
   if(code == 0){
       out.print("<script language=\"javascript\">alert(\"删除成功！\");window.opener.history.go(0);window.close();</script>");
   }else{
       out.print("<script language=\"javascript\">alert(\"删除失败！\");window.opener.history.go(0);window.close();</script>");
   }
%>