<%@ page import="java.util.*,
		             com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp" );
        return;
    }
    if (!SecurityCheck.hasPermission(authToken, 54))
    {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    IGroupManager groupMgr = GroupPeer.getInstance();
    int siteID = authToken.getSiteID();

    List groupList = groupMgr.getGroups(siteID);
    int groupCount = groupList.size();
%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script language="javascript">
        function createAttachType()
        {
            window.open("newAttachType.jsp","","top=100,left=100,width=1000,height=600");
        }

        function editAttachType(attachID)
        {
            window.open("editAttachType.jsp?ID="+attachID,"","top=100,left=100,width=360,height=360");
        }

        function grantAttachType(attachID)
        {
            window.open("grantAttachTypeToColumn.jsp?ID="+attchID,"","top=100,left=100,width=400,height=360");
        }
    </script>
</head>
<%
    String[][] titlebars = {
            { "文章附件类型管理", "" }
    };
    String [][] operations =
            {
                    {"<a href=javascript:createAttachType();>创建文章附件类型</a>", ""},
                    {"系统管理","index.jsp"},
            };
%>
<%@ include file="../inc/titlebar.jsp" %>
<br>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="90%" align=center>
    <tr bgcolor="#eeeeee" class=tine>
        <td width="40%" align=center>文章附件类型名称</td>
        <td width="30%" align=center>附件类型描述</td>
        <td align=center width="10%">修改</td>
        <td align=center width="10%">删除</td>
        <td align=center width="10%">关联栏目</td>
    </tr>

    <%
        for (int i=0; i<groupCount; i++)
        {
            Group group = (Group)groupList.get(i);
            int groupID = group.getGroupID();
            String groupName = StringUtil.gb2iso4View(group.getGroupName());
            String groupDesc = group.getGroupDesc();
            if (groupDesc == null) groupDesc = "";
    %>

    <tr bgcolor="#ffffff" class=line>
        <td>&nbsp;&nbsp;<%=groupName%></td>
        <td>&nbsp;&nbsp;<%=StringUtil.gb2iso4View(groupDesc)%></td>
        <td align="center"><input type="radio" name="props" onclick="editAttachType(<%=groupID%>);"></td>
        <td align="center"><input type="radio" name="props" onclick="location.href='removeAttachType.jsp?id=<%=groupID%>';"></td>
        <td align="center"><input type="radio" name="props" onclick="grantAttachType(<%=groupID%>);"></td>
    </tr>
    <% }  %>

</table>
</html>
