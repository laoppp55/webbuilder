<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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

    List citiaos = wordMgr.getAllCitiaos(siteid);
%>
<html>
<head>
    <title>
        用户留言无效词条管理
    </title>
    <script language="javascript">
        function addNewCitiao() {
            window.open("addcitiao.jsp","addnewcolumnforlw","height=500,width=800");
        }

        function update(id) {
            window.open("editcitiao.jsp?id="+ id,"addnewcolumnforlw","height=500,width=800");
        }

        function del(id) {
            window.open("deletecitiao.jsp?id="+id,"addnewcolumnforlw","height=500,width=800");
        }
    </script>
 </head>
<body>
<table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
    <tr>
        <td>
            <a href="#" onclick="javascript:addNewCitiao()">增加新词条</a>        <a href="#" onclick="javascript:window.close()">退出</a>
        </td>
    </tr>
</table>

<table width="100%" border="1" cellpadding="3" cellspacing="1" class="css_002">
    <tr  bgcolor="#FFFFFF" class="css_001">
        <td width="10%" align="center">序列号</td>
        <td width="50%">词条内容</td>
        <td width="20%">修改</td>
        <td width="20%">删除</td>
    </tr>
    <%
        for(int i = 0; i < citiaos.size();i++){
            CiTiao citiao = (CiTiao)citiaos.get(i);
    %>
    <tr  bgcolor="#FFFFFF" class="css_001">
        <td align="left" width="10%"><input type="checkbox" name="ids" value="<%=citiao.getId()%>"></td>
        <td align="left" width="50%"><%=citiao.getContent() == null?"--":StringUtil.gb2iso4View(citiao.getContent())%></td>
        <td width="20%"><a href="#" onclick="javascript:update(<%=citiao.getId()%>);">修改</a></td>
        <td width="20%"><a href="#" onclick="javascript:del(<%=citiao.getId()%>);">删除</a></td>
    </tr>
    <%}%>
</table>
</body>
</html>