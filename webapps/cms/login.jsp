<%@ page import="com.bizwink.cms.server.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    String username = ParamUtil.getParameter(request, "username");
    String password = ParamUtil.getParameter(request, "password", true);
    String msg = ParamUtil.getParameter(request, "msg");
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");
    String redirect = request.getHeader("REFERER");

    if (redirect == null) redirect = "main.jsp";
    String errorMessage = "";
    int siteid = 0;
    CmsServer.getInstance().init();
    if (doLogin) {
        IAuthManager authMgr = AuthPeer.getInstance();
        try {
			            //获取用户端的IP地址
            String user_ip = null;
            if (request.getHeader("x-forwarded-for") == null) {
                user_ip = request.getRemoteAddr();
            } else {
                user_ip = request.getHeader("x-forwarded-for");
            }

            Auth authToken = authMgr.getAuth(username, password,user_ip);

            if (authToken!=null)  {                    //转向模板选择页面
                session.setAttribute("CmsAdmin", authToken);
                session.setMaxInactiveInterval(60*60*1000);
                siteid = authToken.getSiteID();
                response.sendRedirect("/webbuilder/index1.jsp");
            } else {
                response.sendRedirect("/webbuilder/index.jsp");
            }
        }
        catch (UnauthedException ue) {
            errorMessage = "用户名密码不符，重新输入!";
        }
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="style/global.css">
    <SCRIPT language=JavaScript1.2>
        function init()
        {
            document.logon.username.focus();
        }

        function formsubmit()
        {
            var logform=document.loginForm;

            if(logform.password.value.length < 8)
            {
                alert("您的口令长度小于8，密码的长度必须大于8");
                logform.password.focus();
                return false;
            }

            logform.action="/webbuilder/login.jsp";

            return true;
        }
    </SCRIPT>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"用户登录", ""}
    };
    String[][] operations = null;
%>
<%@ include file="inc/titlebar.jsp" %>
<p>
    <%if (msg != null) {%>&nbsp;&nbsp;<span class=cur><b><%//=msg%></b></span><%}%>
    <span class=cur><b>系统超时，请重新登录：</b></span>
</p>

<form action=login.jsp name=loginForm method=post  onsubmit="return(formsubmit());">
    <input type=hidden name=doLogin value=true>
    <input type=hidden name=redirect value="main.jsp">
    <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 height=280 width="100%">
        <tr>
            <td width="40%"></td>
            <td width="20%" class=cur>
                <%=errorMessage%>
                <table border=0 cellspacing=0 cellpadding=0>
                    <tr height=30>
                        <td align=right class=line>用户名：</td>
                        <td><input name=username size=15 maxlength=25 class=tine value="webbuilder"></td>
                    </tr>
                    <tr height=30>
                        <td align=right class=line>密&nbsp;&nbsp;码：</td>
                        <td><input type=password name=password size=15 maxlength=25 class=tine value="1"></td>
                    </tr>
                    <tr height=40>
                        <td colspan=2 align=center>
                            <input type=submit value=登录 class=line>&nbsp;&nbsp;
                            <input type=button value=帮助 class=line onclick="javascript:window.open('help/index.htm');">
                        </td>
                    </tr>
                </table>
            </td>
            <td width="40%"></td>
        </tr>
    </TABLE>
</form>

</body>
</html>