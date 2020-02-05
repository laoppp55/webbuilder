<%@ page import="com.bizwink.cms.security.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
        if (authToken == null)
        {
            response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
            return;
        }
    int siteid = authToken.getSiteID();
    
    int startIndex = ParamUtil.getIntParameter(request, "startIndex", 0);
    int rowsInPage = ParamUtil.getIntParameter(request, "rowsInPage", 20);
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

    IUserManager userMgr = UserPeer.getInstance();
    List deptList = new ArrayList();
    deptList = userMgr.getDepartments(siteid);
%>
<html>
<head>
    <title>公司部门信息管理</title>
    <script type="text/javascript" language="javascript">
        function goPage(page, siteid, currPage, jumpstr) {
            var getpagenum = document.getElementById("num").value;
            window.location = "index.jsp?startIndex=" + ( getpagenum - 1) * page + "&siteid=" + siteid + "&currPage=" + currPage + jumpstr;
        }

    </script>
    <style type="text/css">
        td {
            font-size: 12px;
        }
    </style>
</head>
<body vLink=#000099 aLink=#cc0000 link=#000099 bgColor=#ffffff TOMARGIN="8">

<table cellSpacing=0 borderColorDark=#ffffec cellPadding=0 width="100%"
       borderColorLight=#5e5e00 border=1>
    <tr>
        
        <td align="center" width="50%"><a href="addDepartment.jsp?siteid=<%=siteid%>">添加部门信息</a></td>
        <td align="center">&nbsp;</td>
    </tr>
    <br>
    <br>
</table>
<br>
<br>
<table cellSpacing=0 borderColorDark=#ffffec cellPadding=0 width="100%"
       borderColorLight=#5e5e00 border=1>
    <form name="ListForm" action="index.jsp" method="post">
        <input type="hidden" name="startflag" value="1">
        <input type="hidden" name="siteid" value="<%=siteid%>">
        <tr class=itm bgColor=#dddddd height=20>
            <td align="center">部门名称</td>
            <td align="center">英文名称</td>
            <td align="center">电话</td>
            <td align="center">经理</td>
            <td align="center">副经理</td>
            <td align="center">主管领导</td>
            <td align="center">修改</td>
        </tr>
        <%
            for (int i = 0; i < deptList.size(); i++) {
                Department dept = (Department) deptList.get(i);
        %>
        <%if (i % 2 == 0) {%>
        <tr class=itm onmouseover="this.style.background='#CECEFF';"
            onmouseout="this.style.background='#ffffff';" bgColor=#ffffff height=25><%}else{%>
        <tr class=itm onmouseover="this.style.background='#CECEFF';"
            onmouseout="this.style.background='#eeeeee';" bgColor=#eeeeee height=25>
            <%}%>
            <td><%=dept.getCname() == null ? "&nbsp;" : StringUtil.gb2iso4View(dept.getCname())%>
            </td>
            <td><%=dept.getEname() == null ? "&nbsp;" : dept.getEname()%>
            </td>
            <td align="center"><%=dept.getTelephone() == null ? "&nbsp;" : dept.getTelephone()%>
            </td>
            <td align="center"><%=dept.getManager() == null ? "&nbsp;" : StringUtil.gb2iso4View(dept.getManager())%>
            </td>
            <td align="center"><%=dept.getVicemanager() == null ? "&nbsp;" : StringUtil.gb2iso4View(dept.getVicemanager())%>
            </td>
            <td align="center"><%=dept.getLeader() == null ? "&nbsp;" : StringUtil.gb2iso4View(dept.getLeader())%>
            </td>
            <td align="center"><a href="modifyDepartment.jsp?id=<%=dept.getId()%>&siteid=<%=siteid%>">修改</a>
            </td>

        </tr>
        <%
            }
        %>
</form>
</table>
</body>
</html>