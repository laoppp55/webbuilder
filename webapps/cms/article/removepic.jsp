<%@ page import="com.bizwink.cms.news.*,
		 com.bizwink.cms.pic.*,
        com.bizwink.cms.security.*,
		 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%	////////////////////////////////

	// get parameters
	int picID   = ParamUtil.getIntParameter(request,"pic", 0);

	boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");

	// global error variables
	String errorMessage = "";

	boolean noPicSpecified = (picID == 0);
	boolean errors = (noPicSpecified);

	IPicManager picManager = PicPeer.getInstance();
	Pic pic = picManager.getTurpics(picID);
	int columnID = pic.getColumnID();

	Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
	if ( authToken == null){response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!");}
	if ( authToken != null && !SecurityCheck.hasPermission(authToken,"pic") ) {
		response.sendRedirect(
				response.encodeRedirectURL("pics.jsp?column="+columnID+"&msg=You have not right to access!")
		);
		return;
	}

	IColumnManager columnManager = ColumnPeer.getInstance();
	Column column = columnManager.getColumn(columnID);
	String columnName = column.getCName();

	// delete user if specified
	if( doDelete && !errors ) {
		String message = "";
		try {
			picManager.remove(picID);
			message = "删除图片完毕!";
		}
		catch( PicException e ) {
			System.err.println( e );
			message = "图片未找到";
		}
		request.setAttribute("message",message);
		response.sendRedirect("pics.jsp?column="+columnID);
		return;
	}

%>

<html><head>
	<title></title>
	<meta http-equiv=Content-Type content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
	String[][] titlebars = {
			{ "新闻发布", "picsmain.jsp" },
			{ columnName, "pics.jsp?column="+columnID },
			{"删除文章", ""}
	};
	String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>

<p class=line>删除文章 <b><%= pic.getCName() %> </b>?<p>
<ul class=cur>警告: 操作将删除此文章，您真的想删除吗?</ul>
<form action="removepic.jsp" name="deleteForm">
	<input type="hidden" name="doDelete" value="true">
	<input type=image src=../images/button_dele.gif onclick="document.all.deleteForm.submit()">&nbsp;
	<input type=image src=../images/button_cancel.gif onclick="javascript:history.back()">
</form>
</body></html>
