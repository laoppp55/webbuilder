<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="java.util.List" %>
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
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int id = ParamUtil.getIntParameter(request,"id",0);
    int lwid = ParamUtil.getIntParameter(request,"lwid",0);
    int doflag = ParamUtil.getIntParameter(request,"doflag",0);

    IWordManager wordMgr = LeaveWordPeer.getInstance();
    String userrole = "";
    List fl = wordMgr.getWordAuthorizedUser(userid,siteid);
    for (int i =0; i<fl.size(); i++) {
        authorizedform f1 = (authorizedform)fl.get(i);
        if (f1.getLwid() == lwid) {
            userrole = f1.getLwrole();
            break;
        }
    }

    String title = null;
    String content = null;

    if (doflag == 1) {
        boolean validflag = false;
        if (userrole.equalsIgnoreCase("留言板部门管理员")) {
            validflag = wordMgr.retWordToManager(userid,siteid,lwid,id,1);            //留言板部门管理员回退信息给留言板管理员
        } else {
            validflag = wordMgr.retWordToManager(userid,siteid,lwid,id,0);            //普通员工回退信息给留言板部门管理员
        }
        if (validflag == true) {
            response.sendRedirect(response.encodeRedirectURL("closewin.jsp?startrow=" + startrow + "&range=" + range + "&lwid=" + lwid));
            return;
        }
    }

    //获取用户留言无效的词条信息
    List valid = wordMgr.getValidReson(siteid);
    Word word = wordMgr.getAWord(id);

%>
<html>
<head>
    <title>返回用户留言给管理员</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <meta http-equiv="Pragma" content="no-cache">
</head>
<body>
<%
    if (word != null) {
        title = word.getTitle();
        content = word.getContent();
    }
%>

<form action="rettomanager.jsp" name="mform" method="post">
    <input type="hidden" name="lwid" value="<%=lwid%>">
    <input type="hidden" name="id" value="<%=id%>">
    <input type="hidden" name="startrow" value="<%=startrow%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="doflag" value="1">
    <table>
        <tr><td>留言标题：</td><td><%=title%></td></tr>
        <tr><td>留言内容：</td><td><%=content%></td></tr>
        <tr><td colspan="2">是否回退给信息给管理员？</td></tr>
        <tr><td><input type="image" src="/webbuilder/sites/images/buttons/1284261358390.gif" value="确认"></td><td><input type="image" src="/webbuilder/sites/images/buttons/1284463162171.jpg" value="取消" onclick="javascript:window.close()"></td></tr>
    </table>
</form>
</body>
</html>