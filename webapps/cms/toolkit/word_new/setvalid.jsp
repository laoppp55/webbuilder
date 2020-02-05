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
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int id = ParamUtil.getIntParameter(request,"id",0);
    int lwid = ParamUtil.getIntParameter(request,"lwid",0);
    int doflag = ParamUtil.getIntParameter(request,"doflag",0);
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    String title = null;
    String content = null;
    
    if (doflag == 1) {
        String reason = ParamUtil.getParameter(request,"valid");
        int validvalue = ParamUtil.getIntParameter(request,"validflag",0);
        System.out.println("validvalue=" + validvalue);
        boolean validflag = wordMgr.setValidForAWord(siteid,id,reason,validvalue);
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
    <title>设置用户留言的有效性</title>
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

<form action="setvalid.jsp" name="validform" method="post">
    <input type="hidden" name="lwid" value="<%=lwid%>">
    <input type="hidden" name="id" value="<%=id%>">
    <input type="hidden" name="startrow" value="<%=startrow%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="doflag" value="1">
    <table>
        <tr><td>留言标题：</td><td><%=title%></td></tr>
        <tr><td>留言内容：</td><td><%=content%></td></tr>
        <tr><td colspan="2">    <select name="valid" id="validid">
            <option value ="0">请选择</option>
            <%
                for(int i=0; i<valid.size(); i++) {
                    String itemcontent = (String)valid.get(i);
            %>
            <option value ="<%=i%>"><%=itemcontent%></option>
            <%}%>
        </select>
        </td></tr>
        <tr><td><input type="radio" name="validflag" value="0" <%=(word.getValid() == 0)?"checked":""%>>有效</td><td><input type="radio" name="validflag" value="1" <%=(word.getValid() == 1)?"checked":""%>>无效</td></tr>
        <tr><td><input type="image" src="/webbuilder/sites/images/buttons/1284261358390.gif" value="确认"></td><td><input type="image" src="/webbuilder/sites/images/buttons/1284463162171.jpg" value="取消" onclick="javascript:window.close()"></td></tr>
    </table>
</form>
</body>
</html>