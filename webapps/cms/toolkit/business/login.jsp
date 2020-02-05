<%@ page import="com.bizwink.cms.server.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
    // get parameters
    String username = ParamUtil.getParameter(request,"username");
    String password = ParamUtil.getParameter(request,"password",true);
    String redirect = ParamUtil.getParameter(request,"url");
    boolean doLogin = ParamUtil.getBooleanParameter(request,"doLogin");

    // check redirect string
    if( redirect == null )
    {
        redirect = "main.jsp";
    }

    String errorMessage = "";

    CmsServer.getInstance().init();

    if( doLogin )
    {
        IAuthManager authMgr = AuthPeer.getInstance();
        try
        {
            Auth authToken = authMgr.getAuth( username, password );
            session.setAttribute("CmsAdmin", authToken);
            response.sendRedirect(redirect);
        }
        catch( UnauthedException ue )
        {
            errorMessage = "用户名密码不符，重新输入!";
        }
    }
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link rel="stylesheet" type="text/css" href="style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
        { "首页", "main.jsp" },
        { "用户登录", "" }
    };
    String[][] operations=null;
%>
<%@ include file="inc/titlebar.jsp" %>
<p>
<%
    //String msg = StringUtil.gb2iso(ParamUtil.getParameter(request,"msg"));
    String msg = ParamUtil.getParameter(request,"msg");
    if( msg != null ) {
%>
        &nbsp;&nbsp;<span class=cur><b><%= msg %></b></span>
<%
    }
%>
</p>
<form action=login.jsp name=loginForm method=post>
  <input type=hidden name=doLogin value=true>
  <input type=hidden name=redirect value="main.jsp">

  <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 height=280>
     <tr>
     	<td width=49%><br></td>
     	<td width=2% class=cur><%= errorMessage %>
            <table border=0 cellspacing=0 cellpadding=0>
                  <tr height=30>
                        <td align=right nowrap class=line>用户名:&nbsp;</td>
                        <td><input name=username size=15 maxlength=25 class=tine></td>
                  </tr>
		  <tr height=30>
		        <td align=right nowrap class=line>密码:&nbsp;</td>
			<td><input type=password name=password size=15 maxlength=20 class=tine></td>
		  </tr>
		  <tr height=40>
		  	<td colspan=2 align=center>
			      <input type=submit value=登录 class=line>&nbsp;
			      <input type=button value=帮助 class=line>
			</td>
		  </tr>
	    </table>
	</td>
	<td width=49%><br></td>
     </tr>
  </TABLE>

</form>

</body>
</html>