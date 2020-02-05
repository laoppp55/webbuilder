<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    IWordManager wordMgr = LeaveWordPeer.getInstance();

    List columns = wordMgr.getALLColumn(siteid);
%>
<html>
<head>
    <title>
        用户留言分类管理
    </title>
    <script language="javascript">
        function addNewColumn() {
            window.open("addcolumn.jsp","addnewcolumnforlw","height=500,width=800");
        }

        function update(id) {
            window.open("editcolumn.jsp?id="+ id,"addnewcolumnforlw","height=500,width=800");
        }

        function del(id) {
            window.open("deletecolumn.jsp?id="+id,"addnewcolumnforlw","height=500,width=800");
        }
    </script>
</head>
<body>
<table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
    <tr>
        <td>
            <a href="#" onclick="javascript:addNewColumn()">增加新分类</a>                   <a href="#" onclick="javascript:window.close()">退出</a>
        </td>
    </tr>
</table>

<table width="100%" border="1" cellpadding="3" cellspacing="1" class="css_002">
    <tr  bgcolor="#FFFFFF" class="css_001">
        <td width="10%" align="center">序列号</td>
        <td align="center" width="15%">中文名称</td>
        <td align="center" width="15%">英文名称</td>
        <td align="center" width="10%">创建人</td>
        <td align="center" width="15%">创建时间</td>
        <td align="center" width="15%">修改时间</td>
        <td align="center" width="10%">修改</td>
        <td align="center" width="10%">删除</td>
    </tr>
    <%
        for(int i = 0; i < columns.size();i++){
            Column column = (Column)columns.get(i);
    %>
    <tr  bgcolor="#FFFFFF" class="css_001">
        <td align="left" width="10%"><input type="checkbox" name="ids" value="<%=column.getId()%>"></td>
        <td align="left" width="15%"><%=column.getCname() == null?"--":StringUtil.gb2iso4View(column.getCname())%></td>
        <td align="left" width="10%"><%=column.getEname() == null?"--":column.getEname()%></td>
        <td align="left" width="10%"><%=column.getEditor() == null?"--":column.getEditor()%></td>
        <td align="left" width="15%"><%=column.getCreatedate() == null?"--":column.getCreatedate().toString()%></td>
        <td align="left" width="15%"><%=column.getLastupdated() == null?"--":column.getLastupdated().toString()%></td>
        <td align="center" width="10%"><a href="#" onclick="javascript:update(<%=column.getId()%>);">修改</a></td>
        <td align="center" width="10%"><a href="#" onclick="javascript:del(<%=column.getId()%>);">删除</a></td>
    </tr>
    <%}%>
</table>
</body>
</html>