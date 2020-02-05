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
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=ϵͳ��ʱ�������µ�½!"));
    return;
  }

  int siteid = authToken.getSiteID();
  String userid = ParamUtil.getParameter(request,"userid");    //����Ȩ�˵�userid�����ǵ�¼�ߵ�userid
  StringBuffer buf = new StringBuffer();

  IColumnUserManager columnUserMgr = ColumnUserPeer.getInstance();
  List clist = columnUserMgr.getUserColumns(userid,siteid);

  Column column = null;
  for (int i=0; i<clist.size(); i++)
  {
    column = (Column)clist.get(i);
    int columnID = column.getID();
	  String cname = StringUtil.gb2iso4View(column.getCName());
    buf.append("menu.MTMAddItem(new MTMenuItem(\"<font class=line>"+cname+"</font>\",\"doGrantForUser.jsp?userid="+userid+"&column="+columnID+"&colChname="+cname+"\", \"cmsright\"));");
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
menu.MTMAddItem(new MTMenuItem("<font color='#FF3333'><%=userid%>(�û���)</font>","doGrantForUser.jsp?userid=<%=userid%>&column=0", "cmsright"));
<%=buf.toString()%>
</script>
<BODY onload="MTMStartMenu(true)" leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0">
</BODY></html>