<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page contentType="text/html;charset=gbk" pageEncoding="gbk" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    Word word = new Word();
    String userid = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int id = ParamUtil.getIntParameter(request,"id",0);
    int markid = ParamUtil.getIntParameter(request,"markid",0);
    int startrow = ParamUtil.getIntParameter(request,"startrow",-1);
    int range = ParamUtil.getIntParameter(request,"range",-1);
    int startflag = ParamUtil.getIntParameter(request,"doflag",-1);
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    word = wordMgr.getAWordIncludeAllRetContent(id);
    Word word1 = wordMgr.getAWord(id);
    String retcontent = "";
    String editcontent = "";
    List anwsers = word.getAnwserfordept();
    Anwser anwser = null;

    for (int i=0; i<anwsers.size(); i++) {
        anwser = new Anwser();
        anwser = (Anwser)anwsers.get(i);
        String t_content = (anwser.getContent()==null)?"":anwser.getContent();
        String t_thedate = (anwser.getRetdate()==null)?"":anwser.getRetdate().toString();
        if (t_content != "" && t_content!=null) {
            retcontent = retcontent + anwser.getProcessor() + "：" + t_content + "（" + t_thedate + "）<br />";
            editcontent = editcontent + t_content;
        }
    }

    //如果说留言板管理员已经综合别人的回答给出了答案，则在编辑区域显示留言板管理员的最终答案
    if (word1.getRetcontent() != null && word1.getRetcontent() != "") editcontent = word1.getRetcontent();
    if (retcontent == "") retcontent = editcontent;

    String userrole = "";
    List fl = wordMgr.getWordAuthorizedUser(userid,siteid);
    for (int i =0; i<fl.size(); i++) {
        authorizedform f1 = (authorizedform)fl.get(i);
        if (f1.getLwid() == markid) {
            userrole = f1.getLwrole();
            break;
        }
    }

    if(startflag == 1){
        retcontent = ParamUtil.getParameter(request, "retcontent");
        if(userrole.equalsIgnoreCase("留言板管理员"))
            wordMgr.updateRetcontentForLWManager(id,retcontent,userid);
        else
            wordMgr.updateRetcontent(id,retcontent);
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?startrow=" + startrow + "&range=" + range + "&lwid=" + markid));
        return;
    } %>
<html>
<head>
    <title>
        回复用户留言
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
<form action="retcontent.jsp" method="post" name="form">
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
                <%=StringUtil.gb2iso4View(word.getTitle())%>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>

        <tr>
            <td width="10%" align="center" valign="top" >
                留言内容：
            </td>

            <td align="left">
                <%=StringUtil.gb2iso4View(word.getContent())%>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>

        <% if (retcontent == null){%>

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
            <td width="10%" align="center" valign="top" >
                回复内容：
            </td>

            <td align="left" >
                <%=StringUtil.gb2iso4View(retcontent)%>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>
        <tr>
            <td width="10%" align="center" valign="top" >
                修改回复：
            </td>
            <td>
                <textarea rows="5" cols="100" name="retcontent"><%=StringUtil.gb2iso4View(editcontent)%></textarea>
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