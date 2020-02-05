<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int m = ParamUtil.getIntParameter(request, "m", 0);

    IBasic_AttributesManager baMgr = Basic_AttributesPeer.getInstance();
    GlobalConfig global = baMgr.getGlobalConfig();
    int systemrun = global.getSystemrun();
%>
<html>
<head>
    <title>系统状态</title>
    <LINK href="../css/manager.css" type=text/css rel=stylesheet>
</head>
<body bgcolor="#ffffff">
<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center">
    <tr>
        <td><strong>系统运行状态</strong></td>
    </tr>
    <tr>
        <td>
            <%
                if (m == 0) {
                    if (systemrun == 0) {
            %>
            <table cellpadding="0" cellspacing="0" border="0" align="center" width="50%" bgcolor="#ffffff">
                <tr width="100%" height="50">
                    <td align="right"><img src="../images/1.gif" border="0" alt="" valign="middle"></td>
                    <td align="left">&nbsp;&nbsp;系统停止运行</td>
                </tr>
            </table>
            <%
            } else if (systemrun == 1) {
            %>
            <table cellpadding="0" cellspacing="0" border="0" align="center" width="50%" bgcolor="#ffffff">
                <tr width="100%" height="50">
                    <td align="right"><img src="../images/2.gif" border="0" alt="" valign="middle"></td>
                    <td align="left">&nbsp;&nbsp;系统运行正常</td>
                </tr>
            </table>
            <%
                }
            } else {
            %>
            <table cellpadding="0" cellspacing="0" border="0" align="center" width="50%" bgcolor="#ffffff">
                <tr width="100%" height="50">
                    <td align="right">&nbsp;</td>
                    <td align="left">&nbsp;&nbsp;系统重新启动</td>
                </tr>
            </table>
            <%
                }
            %>
        </td>
    </tr>
</table>
<br><br><br><br>
<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center">
    <tr>
        <td><strong>启动控制</strong></td>
    </tr>
    <tr>
        <td>
            <table cellpadding="0" cellspacing="0" border="0" width="50%" bgcolor="#ffffff" align="center">
                <tr width="100%" height="50">
                    <td width="33%" align="center"><%if (systemrun == 0) {%><a
                            href="updateSystemRun.jsp?run=1"><img src="../images/start.gif" border="0"
                                                                  alt=""></a><%} else {%><img
                            src="../images/start1.gif" border="0" alt=""><%}%></td>
                    <td width="33%" align="center"><%if (systemrun == 1) {%><a
                            href="updateSystemRun.jsp?run=0"><img src="../images/stop.gif" border="0"
                                                                  alt=""></a><%} else {%><img
                            src="../images/stop1.gif" border="0" alt=""><%}%></td>
                    <td width="33%" align="center"><a href="updateSystemRun.jsp?run=2"><img
                            src="../images/3.gif" border="0" alt=""></a></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!--
<table>
    <tr>
        <td>状态:</td>
        <td> </td>
    </tr>
    <tr>
        <td><a href="updateSpider.jsp?">启动索引程序</a></td>
        <td><a href="updateSpider.jsp?run='-h'">重建索引</a></td>
    </tr>
    <tr></tr>
    <tr>
        <td><a href="updateSpider.jsp?run=stop">停止程序</a></td>
        <td></td>
    </tr>
</table>
-->
</body>
</html>