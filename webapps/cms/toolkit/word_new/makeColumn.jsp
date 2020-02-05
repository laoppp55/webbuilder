<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.ArrayList" %>
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
    int id = ParamUtil.getIntParameter(request,"id",0);
    int markid = ParamUtil.getIntParameter(request,"markid",0);
    int startrow = ParamUtil.getIntParameter(request,"startrow",-1);
    int range = ParamUtil.getIntParameter(request,"range",-1);
    int startflag = ParamUtil.getIntParameter(request,"doflag",-1);
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    List columns = null;
    word = wordMgr.getAWord(id);
    if(startflag == 1){
        int columnid = ParamUtil.getIntParameter(request, "columnname",0);
        wordMgr.updateColumnForItem(siteid,markid,id,columnid);
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?startrow=" + startrow + "&range=" + range + "&lwid=" + markid));
        return;
    } else {
        columns = new ArrayList();
        columns = wordMgr.getALLColumn(siteid);
    }
%>
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
<form action="makeColumn.jsp" method="post" name="form">
    <input type="hidden" name="startflag" value="1">
    <input type="hidden" name="id" value="<%=id%>">
    <input type="hidden" name="markid" value="<%=markid%>">
    <input type="hidden" name="startrow" value="<%=startrow%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="doflag" value="1">
    <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF">
        <tr>
            <td width="20%" align="right" valign="top" >
                留言内容：
            </td>

            <td align="left">
                <%=word.getContent()%>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>
        <tr>
            <td width="20%" align="right" valign="top" >
                回复内容：
            </td>

            <td align="left" >
                <%=(word.getRetcontent()!=null)?word.getRetcontent():""%>
            </td>
        </tr>
        <tr>
            <Td height="20"></Td>
        </tr>
        <tr>
            <td width="20%" align="right" valign="top" >
                选择信息分类：
            </td>
            <td>
                <select name="columnname" id="columnnameid" style="width:150">
                    <option value=-1>请选择</option>
                    <%
                        for(int i=0; i<columns.size(); i++) {
                            com.bizwink.webapps.leaveword.Column column = new com.bizwink.webapps.leaveword.Column();
                            column = (com.bizwink.webapps.leaveword.Column)columns.get(i);
                            out.print("<option value=" + column.getId() + ">" + column.getCname() + "</option>");
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td  align="right">
                <input type="submit" value="确认修改" name="sub" class="btn">
            </td>
            <td  align="left">
                <input type="button" value="关闭" name="cancel" class="btn" onclick="javascript:window.close()">
            </td>
        </tr>
    </table>
</form>
</body>
</html>