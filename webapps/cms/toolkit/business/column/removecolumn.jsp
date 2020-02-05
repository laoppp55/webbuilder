<%@ page import="java.sql.*,
    		 com.bizwink.cms.news.*,
    		 com.bizwink.cms.security.*,
		 com.bizwink.cms.util.*"
		 contentType="text/html;charset=gbk"%>
<%
        Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
        if( authToken == null ) {
            response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
            return;
        }

	// get parameters
	int ID   = ParamUtil.getIntParameter(request,"ID", 0);
        int siteid = authToken.getSiteID();
	boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");

	// global error variables
	String errorMessage = "";

	boolean noColumnSpecified = (ID == 0);
	boolean errors = (noColumnSpecified);

	IColumnManager columnManager = ColumnPeer.getInstance();

	// delete user if specified
	if( doDelete && !errors )
	{
      	    String message = "";
      	    try
      	    {
		//删除该栏目
		columnManager.remove(ID,siteid);

		message = "删除栏目完毕!";
	    }
	    catch( ColumnException e )
	    {
		System.err.println( e );
		message = "栏目未找到";
	    }


	    request.setAttribute("message",message);
	    response.sendRedirect("index.jsp");
	    return;
	}
    	Column column = columnManager.getColumn(ID);

%>
<html><head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
	String[][] titlebars = {
	        { "栏目管理", "index.jsp" },
	        { "删除栏目", "" }
	};
	String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p class=line>
删除栏目 <b><%=StringUtil.gb2iso4View(column.getCName())%> </b>?
<p>
<ul class=cur>警告: 此操作将永久性的删除该栏目及所属的文章、模板及审核规则，您真的想删除吗?</ul>
<form action="removecolumn.jsp" name="deleteForm">
<input type=hidden name=doDelete value="true">
<input type=hidden name=ID value="<%= ID %>">
<input type=image src=../images/button_dele.gif onclick="document.all.deleteForm.submit()">
&nbsp;
<input type=image src=../images/button_cancel.gif onclick="location.href='index.jsp';return false;">
</form>
</body></html>