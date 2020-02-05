<%@ page import="java.util.*,
		 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*"
                 contentType="text/html;charset=gbk"
%>

<%
    	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    	if (authToken == null)
    	{
        	response.sendRedirect( "../login.jsp?url=member/createnewsite.jsp" );
        	return;
    	}

	if (!SecurityCheck.hasPermission(authToken, 54))
	{
		request.setAttribute("message","无系统管理的权限");
		response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
		return;
	}

	boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
	boolean error = false;
	int siteID = authToken.getSiteID();
	int pubflag = -1;

	IRegisterManager regMgr = RegisterPeer.getInstance();

	if (doCreate)
	{
		pubflag = ParamUtil.getIntParameter(request, "pubflag", -1);
		if (pubflag == -1)
		{
			error = true;
		}
	}

	if (doCreate && !error)
	{
		try
		{
			regMgr.update_pubflag(siteID, pubflag);
			out.println("<script language=javascript>");
			out.println("window.close();");
			out.println("</script>");
			return;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			out.println("出现意外！请关闭后重试！");
			return;
		}
	}

	//读取当前站点文章发布方式
	pubflag = regMgr.query_pubflag(siteID);
%>

<html>
<head>
<title>设置文章发布方式</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
</head>

<body bgcolor="#CCCCCC">
<br>
<form method="post" action="pubflag.jsp" name="form1">
<input type=hidden name="doCreate" value="true">
<table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 align=center>
  <tr>
    <td width="100%" bgcolor="#006699" align="right" height="20" colspan="2">
      <p align="center"><font color="#FFFFFF"><b>设置文章发布方式</b></font></td>
  </tr>
  <tr>
    <td width="50%" align="center" height="30"><input type="radio" name="pubflag" value="0" <%if(pubflag==0){%>checked<%}%>>&nbsp;手动发布</td>
    <td width="50%" align="center" height="30"><input type="radio" name="pubflag" value="1" <%if(pubflag==1){%>checked<%}%>>&nbsp;自动发布</td>
  </tr>
</table>
<p align="center"><input type="submit" value="修 改" name="Submit">&nbsp;&nbsp;&nbsp;
   <input type="button" value="取 消" name="Reset" onclick="window.close()"></p>
</form>

</body>
</html>
