<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.security.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp?url=member/removeRight.jsp" );
    return;
  }
  if (authToken.getUserID().compareToIgnoreCase("admin") != 0)
  {
    request.setAttribute("message","无系统管理员的权限");
    response.sendRedirect("../index.jsp");
    return;
  }

  int siteid = ParamUtil.getIntParameter(request, "siteid", 0);
  int type = ParamUtil.getIntParameter(request, "type", 0);
  String dname = ParamUtil.getParameter(request, "dname");
  boolean doPause = ParamUtil.getBooleanParameter(request,"doPause");
  boolean error = (siteid == -1);

  if (doPause && !error)
  {
    IRegisterManager regMgr = RegisterPeer.getInstance();
    try
    {
      if (type == 1)          //暂停站点
      {
        regMgr.pause(siteid);
      }
      else if (type == 2)     //开放站点
      {
        regMgr.start(siteid);
      }
      else if (type == 3)     //激活新站点
      {
        regMgr.start_new(siteid);            //数据入库,发送EMAIL
        regMgr.copyIconToCMS(siteid, dname, request.getRealPath("/"));      //拷贝文章列表有关文件到CMS服务器
      }
      response.sendRedirect("siteManage.jsp");
      return;
    }
    catch (Exception e)
    {
      error = true;
    }
  }
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
  String str = "激活站点";
  if (type == 1)
    str = "暂停站点";

  String[][] titlebars = {
    { "", "siteManage.jsp" },
    { str, "" }
  };
  String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>
&nbsp;&nbsp;确信要<%if(type==2 || type==3){%>激活<%}else{%>暂停<%}%>站点&nbsp;<b><%=dname%></b>&nbsp;吗？
<p>

<form action="pauseSite.jsp" name="pauseForm">
<input type="hidden" name="doPause" value="true">
<input type="hidden" name="siteid" value="<%=siteid%>">
<input type="hidden" name="type" value="<%=type%>">
<input type="hidden" name="dname" value="<%=dname%>">
&nbsp;&nbsp;<input type="submit" value=" 确定 ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%if (type == 3){%>
<input type="button" name="cancel" value=" 取消 " style="font-weight:bold;" onclick="location.href='onlineManage.jsp';return false;">
<%}else{%>
<input type="button" name="cancel" value=" 取消 " style="font-weight:bold;" onclick="location.href='siteManage.jsp';return false;">
<%}%>
</form>

<script language="JavaScript" type="text/javascript">
<!--
document.pauseForm.cancel.focus();
//-->
</script>

</body>
</html>
