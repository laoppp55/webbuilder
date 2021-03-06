<%@ page import="java.sql.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String userid = authToken.getUserID();
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="style/global.css">
    <link rel=stylesheet type=text/css href="style/cms.css">

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
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFAE08">
    <tr bgcolor="#FFAE08" height="60">
        <td width="2%">&nbsp;</td>
        <td width="85%">
            <table width="620" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <%
                        if (userid.compareToIgnoreCase("admin") != 0) {
                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 1)) || SecurityCheck.hasPermission(authToken, 54)) {
                                //out.println("<td><a href=column/index.jsp?rightid=1 target=main><img src='images/lmsz.gif' border='0'></a></td>");
                                out.println("<td><a href=column/index_new.jsp?rightid=1 target=main><img src='images/lmsz.gif' border='0'></a></td>");
                            }

                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 2)) || SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=template/index.jsp?rightid=2 target=main><img src='images/mjgl.gif' border='0'></a></td>");
                            }

                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 3)) || SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=article/index.jsp?rightid=3 target=main><img src='images/wzbj.gif' border='0'></a></td>");
                            }

                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 7)) || SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=articlemove/index.jsp?rightid=7 target=main><img src='images/ydwz.gif' border='0'></a></td>");
                            }

                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 5)) || SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=audit/index.jsp?rightid=5 target=main><img src='images/wzsh.gif' border='0'></a></td>");
                            }

                            if (((authToken != null) && SecurityCheck.hasPermission(authToken, 6)) || SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 8)) {
                                if (SecurityCheck.hasPermission(authToken, 6) || SecurityCheck.hasPermission(authToken, 54))
                                    out.println("<td><a href=publish/index.jsp?rightid=6 target=main><img src='images/wzfb.gif' border='0'></a></td>");
                                else
                                    out.println("<td><a href=publish/index.jsp?rightid=8 target=main><img src='images/wzfb.gif' border='0'></a></td>");
                            }

                            //if (((authToken != null) && SecurityCheck.hasPermission(authToken, 7)) || SecurityCheck.hasPermission(authToken, 54)) {
                            //out.println("<td><a href=toolkit/business/member/index2.jsp target=main><img src='images/ydwz.gif' border='0'></a></td>");
                            //}

                            //if (((authToken != null) && SecurityCheck.hasPermission(authToken, 5)) || SecurityCheck.hasPermission(authToken, 54)) {
                            //out.println("<td><a href=toolkit/business/order/index.jsp target=main><img src='images/wzsh.gif' border='0'></a></td>");
                            //}

                            if ((authToken != null) && !SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=member/changePass.jsp target=main><img src='images/xgma.gif' border='0'></a></td>");
                            }

                            if ((authToken != null) && SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=member/index.jsp target=main><img src='images/xtsz.gif' border='0'></a></td>");
                            }

                            //if ((authToken != null) && SecurityCheck.hasPermission(authToken, 54)) {
                            //    out.println("<td><a href=toolkit/business/index1.jsp target=_parent><img src='images/dzsw.jpg' border='0'></a></td>");
                            //}

                            if (authToken != null && SecurityCheck.hasPermission(authToken, 54)) {
                                out.println("<td><a href=toolkit/index.jsp target=main><img src='images/gjx.gif' border='0'></a></td>");
                            }
                        } else {
                            out.println("<td><a href=member/admin_index.jsp target=main><img src='images/yhgl.gif' border='0'></a></td>");
                            out.println("<td><a href=member/siteManage.jsp target=main><img src='images/zdgl.gif' border='0'></a></td>");
                            out.println("<td><a href=adminx/autoPub.jsp target=main><img src='images/swgl.gif' border='0'></a></td>");
                            out.println("<td><a href=program/pmanager.jsp target=main><img src='images/join2009572.jpg' border='0'></a></td>");
                            out.println("<td><a href=member/listStyle.jsp target=main><img src='images/yswj.gif' border='0'></a></td>");
                            out.println("<td><a href=movecolumn/index.jsp target=main><img src='images/lmqy.gif' border='0'></a></td>");
                            out.println("<td><a href=member/logManage.jsp target=main><img src='images/gzl.gif' border='0'></a></td>");
                            //out.println("<td><a href=joincompany/list.jsp target=main>加盟商管理</a></td>");
                            //out.println("<td><a href=navigator/listNavigator.jsp target=main>导航条管理</a></td>");
                        }
                    %>
                </tr>
            </table>
        </td>
        <td width="13%">
            <a href="help/index.htm" target=_blank><img src="images/help.gif" border="0"></a>&nbsp;
            <a href="javascript:go();"><img src="images/exit.gif" border="0"></a>
        </td>
    </tr>
</table>
</body>
</html>