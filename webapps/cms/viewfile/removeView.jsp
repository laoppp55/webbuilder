<%@ page import="com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.security.*"
		 contentType="text/html;charset=utf-8"
%>

<%	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp?url=member/removeMember.jsp" );
		return;
	}

	if (!SecurityCheck.hasPermission(authToken, 54))
	{
		request.setAttribute("message","无管理用户的权限");
		response.sendRedirect("../error.jsp?message=无管理用户的权限");
		return;
	}

	int id = ParamUtil.getIntParameter(request, "id", 0);
	String viewname = ParamUtil.getParameter(request, "viewname");
	boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");

	if (doDelete)
	{
		IViewFileManager viewfileMgr = viewFilePeer.getInstance();
		try
		{
			viewfileMgr.delete(id);
			response.sendRedirect("index.jsp");
			return;
		}
		catch (viewFileException ue)
		{
			ue.printStackTrace();
		}
	}
%>

<html>
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>

<%
	String[][] titlebars = {
			{ "", "index.jsp" },
			{ "删除视窗", "" }
	};
	String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>

	&nbsp;&nbsp;删除视窗 <b><%=viewname%></b>?

<p>

<form action="removeView.jsp" name="deleteForm">
	<input type="hidden" name="doDelete" value="true">
	<input type="hidden" name="id" value="<%=id%>">
	&nbsp;&nbsp;
	<input type="submit" value="确  定">&nbsp;&nbsp;
	<input type="submit" name="cancel" value="取  消" style="font-weight:bold;" onclick="location.href='index.jsp';return false;">
</form>

<script language="JavaScript" type="text/javascript">
    <!--
    document.deleteForm.cancel.focus();
    //-->
</script>

</body>
</html>
