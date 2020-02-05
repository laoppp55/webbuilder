<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
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
    String userid = authToken.getUserID();
    int id = ParamUtil.getIntParameter(request,"id",0);
    boolean docreate = ParamUtil.getBooleanParameter(request,"doCreate");
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    CiTiao citiao = null;
    if(docreate) {
        citiao = new CiTiao();
        String summary = ParamUtil.getParameter(request,"summary");
        citiao.setSiteid(siteid);
        citiao.setId(id);
        citiao.setContent(summary);
        wordMgr.updateCitiao(citiao);
        out.write("<script language=\"javascript\">alert(\"提交成功！\");window.opener.location='citiaos.jsp';window.close();</script>");
        return;
    }
    citiao = wordMgr.getACitiao(siteid,id);
%>
<html>
<head>
    <title>
        修改留言分类信息
    </title>
</head>
<body>
<table width="100%" border="1" cellpadding="3" cellspacing="1" class="css_002">
    <form name="columnForm" method="post" action="editcitiao.jsp">
        <input type="hidden" name="doCreate" value="true">
        <input type="hidden" name="id" value="<%=id%>">
        <tr>
            <td align="center" width="30%">词条内容</td>
            <td><input type="text" name="summary" value="<%=(citiao.getContent()!=null)?citiao.getContent():""%>" size="50" maxlength="100"></td>
        </tr>
        <tr>
            <td align="center"><input type="submit" name="ok" value="确认"></td>
            <td><input type="button" name="cancel" value="返回" onclick="javascript:window.close()"></td>
        </tr>
    </form>
</table>
</body>
</html>