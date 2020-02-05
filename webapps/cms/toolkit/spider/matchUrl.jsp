<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int basicId = ParamUtil.getIntParameter(request, "basicId", -1);
    IMatchUrl_SpecialCodeManager iMuScMgr = MatchUrl_SpecialCodePeer.getInstance();
    List muList = iMuScMgr.getMtUrl(basicId);
%>
<html>
<head>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="css/manager.css" type=text/css rel=stylesheet>
    <META content="MSHTML 6.00.6000.16525" name=GENERATOR>
</head>
<body>
<table class=tableBorder cellspacing="1" cellpadding="3" border="0" width="95%" align="center">
    <tbody>
        <form action="#">
            <tr>
                <td class=title colspan="3">站点匹配的URL</td>
            </tr>
            <TR>
                <td width="10%" align="center"><strong>URL序列号</strong></td>
                <td align="center"><strong>Url</strong></td>
                <td width="10%" align="center"><strong>操作</strong></td>
            </tr>
            <%
                for (int i = 0; i < muList.size(); i++) {
                    MatchUrl_SpecialCode mu;
                    mu = (MatchUrl_SpecialCode) muList.get(i);
            %>
            <TR onmouseover='this.style.backgroundColor="#E0EAF5"'
                onmouseout='this.style.backgroundColor=""' bgColor=#f0f4fa>
                <td><%=mu.getMu_id()%>
                </td>
                <td><%=mu.getMatchUrl()==null?"":mu.getMatchUrl()%>
                </td>
                <td align="center"><a href="delMuSc.jsp?mu_id=<%=mu.getMu_id()%>&basicId=<%=basicId%>"onClick="return confirm('确定要删除吗？');">删除</a>&nbsp;|&nbsp;
                    <a href="editMuSc.jsp?mu_id=<%=mu.getMu_id()%>&basicId=<%=basicId%>">修改</a>
                </td>
            </tr>
            <%}%>
            <tr>
                <td class=title5 align="right" colspan="3"><a href="addMuSc.jsp?flag=2&basicId=<%=basicId%>">点击这里添加</a>&nbsp;|&nbsp;
                <a href="index.jsp">点击这里返回</a></td>
            </tr>
        </form>
    </tbody>
</table>
</body>
</html>