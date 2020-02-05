<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int siteid = authToken.getSiteID();
  int gid = ParamUtil.getIntParameter(request,"groupid",0);

  IColumnUserManager columnUserMgr = ColumnUserPeer.getInstance();
  IGroupManager groupMgr = GroupPeer.getInstance();
  Group group = groupMgr.getGroup(gid,siteid);

  StringBuffer buf = new StringBuffer();
  List clist = columnUserMgr.getGroupColumns(gid,siteid);
  int columnID = 0;

  Column column = null;
  for (int i=0; i<clist.size(); i++)
  {
    column = (Column)clist.get(i);
    columnID = column.getID();
    buf.append("menu.MTMAddItem(new MTMenuItem(\"<font class=line>" +StringUtil.gb2iso4View(column.getCName()) + "</font>\",\"doGrantForGroup.jsp?groupid=" + gid + "&column=" + columnID + "&colChname=" + StringUtil.gb2iso4View(column.getCName()) + "\", \"cmsright\"));");
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title></title>
<link rel=stylesheet type="text/css" href="../style/global.css">
</head>
<script type="text/javascript" src="../js/mtmcode.js"></script>
<script type="text/javascript">
var needDrag = 0;
var menu = null;
menu = new MTMenu();
menu.MTMAddItem(new MTMenuItem("<font color='#FF3333'><%=group.getGroupName()%>(用户组名)</font>","doGrantForGroup.jsp?groupid=<%=gid%>&column=0", "cmsright"));
<%=buf.toString()%>
</script>
<BODY onload="MTMStartMenu(true)" leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0">
</BODY></html>