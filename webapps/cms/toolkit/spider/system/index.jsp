<%@ page import="com.bizwink.system.SystemInfo" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    SystemInfo system = new SystemInfo();
    String[] disks = system.getAllDisksOfComputer();
%>
<html>
<head>
    <title>系统状态</title>
    <LINK href="../css/manager.css" type=text/css rel=stylesheet>
</head>
<body>
<table cellpadding="0" cellspacing="0" border="0" width="95%" align="center">
    <tr>
        <td><strong>硬盘使用情况</strong></td>
    </tr>
    <%
        String diskSize = "";
        long diskUsedPercent = 0;
        long tdWidth = 0;
        long tdWidth2 = 0;

        if (disks != null) {
            for (int i = 0; i < disks.length; i++) {
                //diskSize = system.getDiskTotalSpace(disks[i]) / (1024 * 1024) + "M";
                if (!diskSize.equals("0M")) {
                    //diskUsedPercent = system.getDiskUsedPercent(disks[i]);
                    tdWidth = 500 * diskUsedPercent / 100;
                    tdWidth2 = 500 - tdWidth;

    %>
    <tr height="40">
        <td>
            <table cellpadding="0" cellspacing="0" border="0" align="left" width="90%">
                <tr>
                    <td width="15%"><%=disks[i]%>&nbsp;&nbsp;<%=diskSize%>
                    </td>
                    <td>
                        <table bordercolorlight="#000000" cellspacing="0" cellpadding="1" bordercolordark="#FFFFFF"
                               border="1">
                            <tr>
                                <td>
                                    <table cellpadding="0" cellspacing="0" border="0" width="500">
                                        <tr width="100%">
                                            <td width="<%=tdWidth%>" align="center" bgcolor="green"><font
                                                    color="white"><%=diskUsedPercent%>%</font></td>
                                            <td width="<%=tdWidth2%>" bgcolor="white">&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <%
                }
            }
        }
    %>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <%
        long memoryPercent = system.getMemoryUsedPercent();
        tdWidth = 500 * memoryPercent / 100;
        tdWidth2 = 500 - tdWidth;
    %>
    <tr width="100%">
        <td>
            <table cellpadding="0" cellspacing="0" border="0" align="left" width="90%">
                <tr>
                    <td width="15%"><strong>内存使用情况</strong>
                    </td>
                    <td>
                        <table bordercolorlight="#000000" cellspacing="0" cellpadding="1" bordercolordark="#FFFFFF"
                               border="1">
                            <tr>
                                <td>
                                    <table cellpadding="0" cellspacing="0" border="0" width="500">
                                        <tr width="100%">
                                            <td width="<%=tdWidth%>" align="center" bgcolor="yellow"><font
                                                    color="#000000"><%=memoryPercent%>%</font></td>
                                            <td width="<%=tdWidth2%>" bgcolor="white">&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>