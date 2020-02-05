<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.SecurityCheck" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
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
    int infotype = ParamUtil.getIntParameter(request,"infotype",0);
    int orgid = ParamUtil.getIntParameter(request,"orgid",0);
    ApplicationContext appContext = SpringInit.getApplicationContext();
    int retcode = 0;
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        retcode = organizationService.deletOrganization(BigDecimal.valueOf(orgid));
    }
    //IUserManager uMgr = UserPeer.getInstance();
    //int code = uMgr.deleteDepartment(id);
   if(retcode >0 ){
       out.print("<script language=\"javascript\">alert(\"删除成功！\");window.opener.history.go(0);window.close();</script>");
   }else{
       out.print("<script language=\"javascript\">alert(\"删除失败！\");window.opener.history.go(0);window.close();</script>");
   }
%>