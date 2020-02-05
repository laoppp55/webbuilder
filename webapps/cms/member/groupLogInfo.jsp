<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.log.LogPeer" %>
<%@ page import="com.bizwink.log.ILogManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@ page import="com.bizwink.cms.server.FileProps" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
    if (!SecurityCheck.hasPermission(authToken, 54)) {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    int siteID = ParamUtil.getIntParameter(request, "siteID", 0);
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", -1);

    String where = "";
    String bdate = "";
    String edate = "";
     FileProps fp = new FileProps("com/bizwink/cms/server/config.properties");
        String dbstr = fp.getProperty("main.db.type");
    if (searchflag == 1) {
        where = " and 1=1";
        bdate = ParamUtil.getParameter(request, "bdate");
        if ((bdate != null) && (bdate.length() > 1) && (bdate.indexOf("-") > -1)){
            bdate += " 00:00:00";
            if (dbstr.equals("oracle")) {
                  where = where + " and createdate >= TO_DATE('"+bdate+"', 'YYYY-MM-DD HH24:MI:SS')";
            }else{
                where = where + " and createdate >='" + bdate + "'";
            }
        }
        edate = ParamUtil.getParameter(request, "edate");
        if ((edate != null) && (edate.length() > 1) && (edate.indexOf("-") > -1)){
            edate += " 23:59:59";
            if (dbstr.equals("oracle")) {
                  where = where + " and createdate <= TO_DATE('"+edate+"', 'YYYY-MM-DD HH24:MI:SS')";
            }else{
                where = where + " and createdate <='" + edate + "'";
            }
        }
    }

    IGroupManager groupMgr = GroupPeer.getInstance();
    List groupList = groupMgr.getGroupsLog(siteID, where);
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script language="javascript" src="../js/setday.js"></script>
</head>

<body>
<%
    String[][] titlebars = {
            {"LOG记录", ""}
    };
    String[][] operations = {
            {"组列表", "groupLogInfo.jsp?siteID=" + siteID},
            {"所有用户列表", "logInfo.jsp?siteID=" + siteID}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<table border=0 width="100%">
    <tr>
        <td><font class=line>用户组列表</font></td>
    </tr>
</table>

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <tr bgcolor="#eeeeee" class=tine>
        <td width="20%" align=center>组名</td>
        <td width="20%" align=center>描述</td>
        <td width="15%" align=center>创建文章</td>
        <td width="15%" align=center>修改文章</td>
        <td width="15%" align=center>删除文章</td>
        <td width="15%" align=center>查看详细</td>
    </tr>
    <%
        for (int i = 0; i < groupList.size(); i++) {
            String groupLogInfo = (String) groupList.get(i);
            String[] groupLogs = groupLogInfo.split("@");
            String groupName = StringUtil.gb2iso4View(groupLogs[1]);
            String groupDesc = groupLogs[2];
            groupDesc = groupDesc == null ? "" : StringUtil.gb2iso4View(groupDesc);

            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
    %>
    <tr bgcolor="<%=bgcolor%>" height=22>
        <td>&nbsp;&nbsp;<font color=red><b><%=groupName%>
        </b></font></td>
        <td>&nbsp;&nbsp;<%=groupDesc%>
        </td>
        <td align=center><b><%=groupLogs[3]%>
        </b></td>
        <td align=center><b><%=groupLogs[4]%>
        </b></td>
        <td align=center><b><%=groupLogs[5]%>
        </b></td>
        <td align="center"><a href="groupUsersLog.jsp?gid=<%=groupLogs[0]%>&sid=<%=siteID%>&searchflag=<%=searchflag%>&bdate=<%=bdate%>&edate=<%=edate%>">查看详细</a></td>
    </tr>
    <%}%>
</table>
<br><br>
<table border="0" cellpadding="0" cellspacing="0" width="99%" align="center">
    <form action="groupLogInfo.jsp" method="post" name="logform">
        <input type="hidden" name="siteID" value="<%=siteID%>">
        <input type="hidden" name="searchflag" value="1">
        <tr bgcolor="#eeeeee" height="30">
            <td>&nbsp;&nbsp;搜索：</td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>&nbsp;&nbsp;开始日期：<input type="text" name="bdate" readonly onfocus="setday(this)" size=10>
                ——
                结束日期：<input type="text" name="edate" readonly onfocus="setday(this)" size=10>
                <input type="submit" value="搜索">
            </td>
        </tr>
    </form>
</table>
</body>
</html>