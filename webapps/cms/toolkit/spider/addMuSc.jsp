<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int basicId=ParamUtil.getIntParameter(request,"basicId",-1);
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
        <form action="doAddMuSc.jsp" method="post">
            <input name=basicId id=basicId type="hidden" value="<%=basicId%>">
            <%
                int flag = ParamUtil.getIntParameter(request, "flag",-1);
                if (flag==1) {
            %>
            <input type="hidden"name=flag value=1>
            <tr>
                <td class=title colspan="2">添加特征码</td>
            </tr>
            <tr>
                <td align="right">起始特征码</td>
                <td><input name=st id=st type="text" size="100"></td>
            </tr>
            <tr>
                <td align="right">结束特征码</td>
                <td><input name=et id=ed type="text" size="100"></td>
            </tr>
            <tr>
                <td class=title5 align="center" colspan=2><input type="submit" name="submit" value="保存">
                    &nbsp;&nbsp;<input type="reset" name="reset" value="清空">
                </td>
            </tr>
            <tr>
                <td class=title5 colspan=2 align=right><a href="characterCode.jsp?basicId=<%=basicId%>">点击这里返回</a></td>
            </tr>

            <%

            } else if (flag==2) {%>
            <input type="hidden" name=flag value=2>
            <tr>
                <td class=title colspan="2">添加URL</td>
            </tr>
            <tr>
                <td align="right">URL地址</td>
                <td><input name=matchUrl id=matchUrl size=100></td>
            </tr>
            <tr>
                <td align=center colspan=2><input name="Submit" type="submit" id="Submit" value="保存">&nbsp;&nbsp;
                <input name="Reset" type="reset" id="Reset" value="清空"></td>
            </tr>
            <tr>
                <td class=title5 colspan=2 align="right"><a href="matchUrl.jsp?basicId=<%=basicId%>">点击这里返回</a></td>
            </tr>
            <%
            } else {%>
            <tr>
                <td class=title colspan=2><label>&nbsp;</label></td>
            </tr>
            <tr>
                <td colspan=2>&nbsp;</td>
            </tr>
            <tr>
                <td colspan=2 class=title5>&nbsp;</td>
            </tr>
            <%}%>
        </form>
    </tbody>
</table>
</body>
</html>