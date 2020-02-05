<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.security.*" contentType="text/html;charset=gbk"
%>

<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp?url=member/removeRight.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken,54))
	{
		request.setAttribute("message","无系统管理的权限");
		response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
		return;
	}

	IFtpSetManager ftpMgr = FtpSetting.getInstance();
	int ID = ParamUtil.getIntParameter(request,"ID",0);
	boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");
	FtpInfo ftpInfo = ftpMgr.getFtpInfo(ID);

	if (doDelete)
	{
		ftpMgr.remove(ID);
		response.sendRedirect("siteIPManage.jsp?siteID="+ftpInfo.getSiteid());
		return;
	}
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
	String[][] titlebars = {
		{"删除主机", "" }
	};
	String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>
确信要删除&nbsp;<b><%=ftpInfo.getIp()%></b>&nbsp;发布主机信息吗？
<p>

<form action="removeipinfo.jsp" name="deleteForm">
<input type="hidden" name="doDelete" value="true">
<input type="hidden" name="ID" value="<%=ID%>">
<input type="submit" value=" 确定 ">&nbsp;&nbsp;&nbsp;
<input type="button" value=" 取消 " onclick="history.go(-1);">
</form>

</body>
</html>
