<%@ page import="java.util.*,
                 java.net.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.server.CmsServer,
                 com.bizwink.cms.security.*"
                 contentType="text/html;charset=gbk"
%>
<jsp:useBean id="task" scope="session"
    class="com.bizwink.cms.publishx.TaskBean"/>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp?url=member/editGroup.jsp" );
    return;
  }
  if (authToken.getUserID().compareToIgnoreCase("admin") != 0)
  {
    request.setAttribute("message","无系统管理员的权限");
    response.sendRedirect("../index.jsp");
    return;
  }

  String rootPath = request.getRealPath("/");

  String msg="";
  if (request.getParameter("start")!=null)
  {
    String dc =  ParamUtil.getParameter(request, "dc");
    int num = ParamUtil.getIntParameter(request, "num",1);
    int type = ParamUtil.getIntParameter(request, "type",0);
    int sleep=0;
    switch (type)
    {
      case 0:
        sleep = num * 1000;
        break;
      case 1:
        sleep = num * 60 * 1000;
        break;
      case 2:
        sleep = num * 60 * 60 * 1000;
        break;
      default:
        msg = "Set the num .pls!";
    }
    if (dc!=null)
    {
      Date date = new Date(dc+" 00:00:00");
      task.setEndDate(date);
      task.setSleep(sleep);
      task.setRootPath(rootPath);
      task.setRunning(true);
      new Thread(task).start();
    }
    else
    {
      msg = "Set the date.pls!";
    }
  }
  if (request.getParameter("stop")!=null)
  {
    task.setRunning(false);
  }
  if (request.getParameter("exit")!=null)
  {
    out.println("the thread is exit!");
  }
%>

<html>
<head>
<title>发布事务管理</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
</head>
<body>
<%
  String[][] titlebars = {{"> 事务管理", ""}};
  String[][] operations = {};
%>
<%@ include file="../inc/titlebar.jsp" %>
<center><br>
<FORM name="demoform" action="siteAffair.jsp" method="Post">
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=80%>
<tr bgcolor="#eeeeee" class=tine>
<td colspan=2 align=center>设置事务<font color=red><%=msg%></font></td>
</tr>
<tr bgcolor="#eeeeee" class=tine>
<td>
&nbsp;到这个时间为止: &nbsp;<input name="dc" value="" size="11">
<a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.demoform.dc);return false;" HIDEFOCUS>
<img name="popcal" align="absmiddle" src="calbtn.gif" width="34" height="22" border="0" alt=""></a>
&nbsp;&nbsp;
&nbsp;频率：每<input name="num" value="1" size="5">
<select name=type><option value=>秒钟</option><option value=1>分钟</option><option value=2>小时</option></select></td>
</tr>
<tr bgcolor="#eeeeee" class=tine>
<td align=center colspan=2>
<% if (task.isRunning()) { %>
<input type=submit name=stop value='停  止'>
<%} else {%>
<input type=submit name=start value='启  动'>
<%}%>
</td>
</tr></table>
</FORM>
</center>
</body>
</html>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
</iframe>
