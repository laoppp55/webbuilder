<%@page import="java.util.*,
                java.io.*,
                com.bizwink.cms.security.*,
                com.bizwink.move.*,
                com.bizwink.cms.extendAttr.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.util.*"
                contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String username = authToken.getUserID();
  int siteid      = authToken.getSiteID();
  String sitename = authToken.getSitename();
  String appPath  = application.getRealPath("/");
  String columnIDlist = ParamUtil.getParameter(request, "columnIDlist");

  IMoveManager moveMgr = MovePeer.getInstance();

  //获得目的栏目id
  int aim = ParamUtil.getIntParameter(request,"aimcolumn", 0);

  String[] columnids = columnIDlist.split(",");
  for(int i=0;i<columnids.length;i++){
    if(aim == Integer.parseInt(columnids[i])){
      out.println("<script language=javascript>");
      out.println("alert(\"目的栏目与要移动栏目不能相同！\");");
      out.println("parent.window.close();");
      out.println("</script>");
    }
  }

  for(int i=0;i<columnids.length;i++){
    int getaimid = moveMgr.getParentID(Integer.parseInt(columnids[i]));
    if(getaimid == aim){
      out.println("<script language=javascript>");
      out.println("alert(\"要移动栏目已经在目的栏目下面！\");");
      out.println("parent.window.close();");
      out.println("</script>");
    }
  }

  for(int i=0;i<columnids.length;i++){
    int getaimid = moveMgr.getParentID(Integer.parseInt(columnids[i]));
    if(getaimid == 0){
      out.println("<script language=javascript>");
      out.println("alert(\"根栏目不能移动到其他栏目下面！\");");
      //out.println("return false;");
      out.println("parent.window.close();");
      out.println("</script>");
    }
  }

  MoveColumn moveColumn = new MoveColumn();
  moveColumn.setAimColumnID(aim);
  moveColumn.setOrgColumnID(columnIDlist);
  moveColumn.setUserName(username);
  moveColumn.setSiteID(siteid);
  moveColumn.setAppPath(appPath);
  moveColumn.setSiteName(sitename);
  moveColumn.run();
%>
<html>
<head>
<link rel=stylesheet type=text/css href="../style/global.css">
</head>
<body bgcolor="#CCCCCC">
<br><br><br><br>
<table align="center">
<tr>
<td align="center">
<font color="red">栏目迁移成功</font>
</td>
</tr>
</table>
<br>
<br>
<table align="center">
<tr>
<td align="center">
<input type="button" value="关 闭" onclick="parent.window.close();">
</td>
</tr>
</table>
</body>
</html>
