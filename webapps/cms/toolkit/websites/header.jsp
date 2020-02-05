<%@ page import="java.sql.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        return;
    }
    String userid = authToken.getUserID();
    int samsiteid = authToken.getSamSiteid();
    int samsitetype = authToken.getSamSitetype();
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="css/global.css">
    <!--link rel=stylesheet type=text/css href="css/cms.css"-->

    <script language=javascript>
        function go()
        {
            parent.document.location = "exit.jsp";
        }

        function publish()
        {
            window.open("autoPublish/publish.jsp", "SelectRight", "top=10,left=10,width=190,height=80,resizable=no,scrollbars=no,status=no,toolbar=no,menubar=no,location=no");
        }

        <%if (authToken != null && !userid.equalsIgnoreCase("admin")){%>
        window.status = "<%="当前用户"%> [<%=userid%>]    <%="当前站点"%>[<%=authToken.getSitename()%>]";
        <%}%>
    </script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#999999">
    <tr bgcolor="#999999" height="60">
        <td width="2%">&nbsp;</td>
        <td width="85%">
            <table width="620" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <%
                        if (userid.compareToIgnoreCase("admin") != 0) {
                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 1)) || SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=columns/index.jsp?rightid=1 target=main>网站地址分类管理</a></td>");
                            }  else {
                                 out.println("j");
                            }

                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 2)) || SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=template/index.jsp?rightid=2 target=main>网站信息管理</a></td>");
                            }  else {
                                 out.println("h");
                            }
                        }
                    %>
                </tr>
            </table>
        </td>
        <td width="13%">
            <a href="javascript:top.window.close();"><img src="images/exit.gif" border="0"></a>
        </td>
    </tr>
</table>
</body>
</html>