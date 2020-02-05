<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.log.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int siteID = ParamUtil.getIntParameter(request, "site", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);           //0-创建；1-修改；2-删除
    int fid = ParamUtil.getIntParameter(request, "fid", 0);             //跳转来源 1-groupUsersLog.jsp
    int groupId = ParamUtil.getIntParameter(request, "gid", 0);
    String editor = ParamUtil.getParameter(request, "user");
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", -1);
    int columnid = ParamUtil.getIntParameter(request, "columnid", 0);

    String where = " and 1=1";
    String bdate = "";
    String edate = "";
    if (searchflag == 1) {
        bdate = ParamUtil.getParameter(request, "bdate");
        if ((bdate != null) && (bdate.length() > 1) && (bdate.indexOf("-") > -1))
            where = where + " and createdate >='" + bdate + "'";
        edate = ParamUtil.getParameter(request, "edate");
        if ((edate != null) && (edate.length() > 1) && (edate.indexOf("-") > -1))
            where = where + " and createdate <='" + edate + "'";
        if(columnid != 0)
            where = where + " and columnid = " + columnid;
    }

    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;
    String doName = "";
    String referurl = "";
    if (fid == 1)
        referurl = "groupUsersLog.jsp?gid=" + groupId + "&sid=" + siteID + "&searchflag=" + searchflag + "&bdate=" + bdate + "&edate=" + edate + "&columnid=" + columnid;
    else
        referurl = "logInfo.jsp?siteID=" + siteID + "&searchflag=" + searchflag + "&bdate=" + bdate + "&edate=" + edate + "&columnid=" + columnid;

    if (flag == 1)
        doName = "创建";
    else if (flag == 2)
        doName = "修改";
    else if (flag == 3)
        doName = "删除";

    List list = new ArrayList();
    if (siteID > 0 && editor != null) {
        ILogManager logMgr = LogPeer.getInstance();
        try {
            list = logMgr.getEditorLogInfoGroupbyActTime(siteID, editor, flag, startrow, range, where);
            rows = logMgr.getEditorLogInfoNumGroupbyActTime(siteID, editor, flag, where);
        } catch (LogException e) {
            e.printStackTrace();
        }
    }

    if (rows < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (rows % range == 0)
            totalpages = rows / range;
        else
            totalpages = rows / range + 1;

        currentpage = startrow / range + 1;
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script type="text/javascript">
        function golistnew() {
            var srow = (document.all('jump').value - 1) * <%=range%>;
            logForm.action = "logdetail.jsp?site=<%=siteID%>&flag=<%=flag%>&fid=<%=fid%>&gid=<%=groupId%>&user=<%=editor%>&searchflag=<%=searchflag%>&bdate=<%=bdate%>&edate=<%=edate%>&columnid=<%=columnid%>&startrow=" + srow;
            logForm.submit();
        }
    </script>
</head>

<body>
<%
    String[][] titlebars = {
            {"系统管理", "../main.jsp"},
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
        <td><font class=line><%=doName%>文章详细列表</font></td>
    </tr>
</table>

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <form action="logdetail.jsp" method="post" name="logForm">
        <input type="hidden" name="site" value="<%=siteID%>">
        <input type="hidden" name="flag" value="<%=flag%>">
        <input type="hidden" name="user" value="<%=editor%>">
        <tr bgcolor="#eeeeee" class=tine>
            <td width="30%" align=center>帐号</td>
            <td width="20%" align=center>站点ID</td>
            <td width="20%" align=center><%=doName%>文章总数</td>
            <td width="30%" align=center>日期</td>
        </tr>
        <%
            for (int i = 0; i < list.size(); i++) {
                String info = (String) list.get(i);
                String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
        %>
        <tr bgcolor="<%=bgcolor%>" height=21>
            <td>&nbsp;&nbsp;<font color=red><b><%=editor%>
            </b></font></td>
            <td align=center><%=siteID%>
            </td>
            <td align=center><b><%=info.substring(11)%>
            </b></td>
            <td align=center><b>
                <a href="logDetailInfo.jsp?site=<%=siteID%>&flag=<%=flag%>&user=<%=editor%>&date=<%=info.substring(0, 10)%>&fid=<%=fid%>&gid=<%=groupId%>&searchflag=<%=searchflag%>&bdate=<%=bdate%>&edate=<%=edate%>&columnid=<%=columnid%>"><%=info.substring(0, 10)%></a>
            </b></td>
        </tr>
        <%}%>
        <tr>
            <td colspan=4 height=40 align=center>共有<%=rows%>条纪录&nbsp;&nbsp;总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
                &nbsp;&nbsp;&nbsp;&nbsp;
                <%
                    if ((startrow - range) >= 0) {
                %>
                [<a href="logdetail.jsp?startrow=<%=startrow-range%>&site=<%=siteID%>&flag=<%=flag%>&user=<%=editor%>&fid=<%=fid%>&gid=<%=groupId%>&searchflag=<%=searchflag%>&bdate=<%=bdate%>&edate=<%=edate%>&columnid=<%=columnid%>">上一页</a>]
                <%}%>
                <%
                    if ((startrow + range) < rows) {
                %>
                [<a href="logdetail.jsp?startrow=<%=startrow+range%>&site=<%=siteID%>&flag=<%=flag%>&user=<%=editor%>&fid=<%=fid%>&gid=<%=groupId%>&searchflag=<%=searchflag%>&bdate=<%=bdate%>&edate=<%=edate%>&columnid=<%=columnid%>">下一页</a>]
                <%
                    }
                    if (totalpages > 1) {
                %>
                &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                <a href="javascript:golistnew();">GO</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <%}%><a href="<%=referurl%>">返回</a></td>
        </tr>
    </form>
</table>
</body>
</html>