<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page contentType="text/html;charset=gbk" pageEncoding="gbk" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    Word word = new Word();
    int siteid = authToken.getSiteID();
    String userid = authToken.getUserID();
    int id = ParamUtil.getIntParameter(request,"id",0);
    int markid = ParamUtil.getIntParameter(request,"markid",0);
    int startrow = ParamUtil.getIntParameter(request,"startrow",-1);
    int range = ParamUtil.getIntParameter(request,"range",-1);
    int startflag = ParamUtil.getIntParameter(request,"doflag",-1);
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    word = wordMgr.getAWord(id);
    String content = wordMgr.getAWordForDept(siteid, markid,id,userid);
    if(startflag == 1){
        String  retcontent = ParamUtil.getParameter(request, "retcontent");
        wordMgr.updateRetcontentFromDept(siteid,markid,id,userid,retcontent);
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?startrow=" + startrow + "&range=" + range + "&lwid=" + markid));
        return;
    } %>
<html>
<head>
    <title>
        部门回复用户留言
    </title>
    <style type="text/css">
        TABLE {FONT-SIZE: 12px;word-break:break-all}
        A:link {text-decoration:none;line-height:20px;}
        A:visited {text-decoration:none;line-height:20px;}
        A:active {text-decoration:none;line-height:20px; font-weight:bold;}
        A:hover {text-decoration:none;line-height:20px;}
        .btn{
            background-color:transparent;  /* 背景色透明 */
            font-size:12px;
        }
    </style>
</head>
</html>
<body>
<form action="retcontentfromdept.jsp" method="post" name="form">
    <input type="hidden" name="startflag" value="1">
    <input type="hidden" name="id" value="<%=id%>">
    <input type="hidden" name="markid" value="<%=markid%>">
    <input type="hidden" name="startrow" value="<%=startrow%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="doflag" value="1">
    <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF">
        <tr>
            <td width="10%" align="center" valign="top" >
                留言标题：
            </td>

            <td align="left">
                <%=(word.getTitle()==null)?"":word.getTitle()%>
            </td>
        </tr>
        <tr>
            <td width="10%" align="center" valign="top" >
                留言内容：
            </td>

            <td align="left">
                <%=(word.getContent()==null)?"":word.getContent()%>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>

        <% if (content == null || content.equals("")){%>

        <tr>
            <td width="10%" align="center" valign="top" >
                回复内容：
            </td>
            <td>
                <textarea rows="5" cols="100" name="retcontent"></textarea>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="确认修改" name="sub" class="btn">
            </td>
            <td>
                <input type="button" value="关闭" name="cancel" class="btn" onclick="javascript:window.close()">
            </td>
        </tr>

        <%}else{%>
        <tr>
            <Td height="20"></Td>
        </tr>
        <tr>
            <td width="10%" align="center" valign="top" >
                修改回复：
            </td>
            <td>
                <textarea rows="5" cols="100" name="retcontent"><%=content%></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="确认修改" name="sub" class="btn">
            </td>
            <td>
                <input type="button" value="关闭" name="cancel" class="btn" onclick="javascript:window.close()">
            </td>
        </tr>
        <%}%>
    </table>
</form>
</body>
</html>