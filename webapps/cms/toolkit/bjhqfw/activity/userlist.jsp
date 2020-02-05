<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int activityid = ParamUtil.getIntParameter(request, "actid", 0);
    String title = ParamUtil.getParameter(request,"title");
    int siteid=39;
    IJiBenManager jMgr = JiBenPeer.getInstance();
    Activity activity = new Activity();
    List list = jMgr.getUsersByActivity(activityid,siteid,start,range);
    int totalnum = jMgr.getUsersCountByActivity(start,range);
    int totalpages = 0;
    int currentpage = 0;
    if (totalnum < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (totalnum % range == 0)
            totalpages = totalnum / range;
        else
            totalpages = totalnum / range + 1;

        currentpage = start / range + 1;
    }
%>
<html>
<head>
<title>报名参加活动用户信息管理</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
</style>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">活动名称--<%=title%></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="center">用户名称</td>
                    <td align="center">电子邮件</td>
                    <td align="center">电话</td>
                    <td align="center">手机</td>
                    <td align="center">通讯地址</td>
                    <td align="center">邮政编码</td>
                    <td align="center">报名时间</td>
                </tr>
                  <%
                        for(int i = 0; i < list.size(); i++){
                            activity = (Activity)list.get(i);
                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                    <td align="left"><%=activity.getUsername()== null?"--":activity.getUsername()%></td>
                    <td align="left"><%=activity.getEmail()== null?"--":activity.getEmail()%></td>
                    <td align="left"><%=activity.getPhone()== null?"--":activity.getPhone()%></td>
                    <td align="left"><%=activity.getMphone()== null?"--":activity.getMphone()%></td>
                    <td align="left"><%=activity.getAddress()== null?"--":activity.getAddress()%></td>
                    <td align="left"><%=activity.getPostcode()== null?"--":activity.getPostcode()%></td>
                    <td align="left"><%=activity.getCreatetime()==null?"--":activity.getCreatetime().toString()%></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
            <tr>
                <td align="right">
                 <a href="#" onclick="javascript:window.close()"> 关 闭</a>
                </td>
            </tr>
        </table>
                <p align=center>
                <TABLE>
                    <TBODY>
                    <TR>
                        <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=totalnum%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                            <%
                                if ((start - range) >= 0) {
                            %>
                            <a href="userlist.jsp?start=0">第一页</a>
                            <%}%>
                            <%if ((start - range) >= 0) {%>
                            <a href="userlist.jsp?start=<%=start-range%>">上一页</a>
                            <%}%>
                            <%if ((start + range) < totalnum) {%>
                            <A href="userlist.jsp?start=<%=start+range%>">下一页</A>
                            <%}%>
                            <%if (currentpage != totalpages) {%>
                            <A href="userlist.jsp?start=<%=(totalpages-1)*range%>">最后一页</A>
                            <%}%>
                        </TD>
                        <TD>&nbsp;</TD>
                    </TR>
                    </TBODY>
                </TABLE>
      </td>
</tr>
</table>

</center>
</body>
</html>