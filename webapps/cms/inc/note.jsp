<%@ page import = "java.util.*,
                   java.sql.*,
                   com.bizwink.cms.util.*,
                   com.bizwink.cms.audit.*,
                   com.bizwink.cms.security.*"
    		       contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect("../login.jsp");
    return;
  }

  int siteID = authToken.getSiteID();
  int param = ParamUtil.getIntParameter(request, "param", 0);
  IAuditManager auditMgr = AuditPeer.getInstance();
  String userID = authToken.getUserID().toString().toLowerCase().trim();
  String user_ID = "[" + userID + "]";

  List backList  = null;
  List auditList = null;
  int  backCount = 0;
  int auditCount = 0;
%>

<html>
<head>
<title>通知</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
.td {font-size:12px}
</style>
</head>

<body>
<table border="1" width="100%" cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="#C0C0C0" height="92">
<%
//有退稿
if (param == 1 || param == 3)
{
  backList = auditMgr.getBack_Articles(userID);
  backCount = backList.size();
%>
  <tr>
    <td width="100%" bgcolor="#3366FF" height="20">
    	<font color="#FFFFFF" style="font-size:14.2px;font-family:宋体;font-weight:bold">&nbsp;&nbsp;有文章退给您：</font>
    </td>
  </tr>
<%
  for (int i=0; i<backCount; i++)
  {
    Audit audit = (Audit)backList.get(i);

    int ID = audit.getID();
    String title  = audit.getMainTitle();
    String column = auditMgr.getColumn_Cname(audit.getColumnID());
    String editor = audit.getEditor();
    String sign = audit.getSign();
    String comments = audit.getComments();
    Timestamp createDate = audit.getCreateDate();
%>
   <tr><td width="100%" height="22">
  	<table border="0" width="100%" cellpadding="2" cellspacing="0">
  		<tr bgcolor="#eeeeee"><td width="18%" height=18 class=td align=right>编号：</td>  <td width="82%" class=td height=18><%=ID%></td></tr>
  		<tr bgcolor="#FFFFFF"><td width="18%" height=18 class=td align=right>标题：</td>  <td width="82%" class=td height=18><%=StringUtil.gb2iso4View(title)%></td></tr>
  		<tr bgcolor="#eeeeee"><td width="18%" height=18 class=td align=right>栏目：</td>  <td width="82%" class=td height=18><%=StringUtil.gb2iso4View(column)%></td></tr>
  		<tr bgcolor="#FFFFFF"><td width="18%" height=18 class=td align=right>编辑：</td>  <td width="82%" class=td height=18><%=editor%></td></tr>
  		<tr bgcolor="#eeeeee"><td width="18%" height=18 class=td align=right>审核人：</td><td width="82%" class=td height=18><%=(sign==null)?"":StringUtil.gb2iso4View(sign)%></td></tr>
  		<tr bgcolor="#FFFFFF"><td width="18%" height=18 class=td align=right>意见：</td>  <td width="82%" class=td height=18><%=(comments==null)?"":StringUtil.gb2iso4View(comments)%></td></tr>
  		<tr bgcolor="#eeeeee"><td width="18%" height=18 class=td align=right>时间：</td>  <td width="82%" class=td height=18><%=createDate.toString().substring(0, 19)%></td></tr>
  	</table>
  	</td></tr>
<%
  }
}
//有需审核的文章
if (param == 2 || param == 3)
{
  auditList = auditMgr.getArticles_NeedAudit(user_ID, siteID);
  auditCount = auditList.size();
%>
  <tr>
    <td width="100%" height="20" bgcolor="#3366FF">
    	<font color="#FFFFFF"  style="font-size:14.2px;font-family:宋体;font-weight:bold">&nbsp;&nbsp;有文章需要您审核：</font>
    </td>
  </tr>
<%
  for (int i=0; i<auditCount; i++)
  {
    Audit audit = (Audit)auditList.get(i);

    int ID = audit.getID();
    String title  = audit.getMainTitle();
    String column = auditMgr.getColumn_Cname(audit.getColumnID());
    String editor = audit.getEditor();
%>
  	<tr><td width="100%" height="22">
  	<table border="0" width="100%" cellpadding="2" cellspacing="0">
  		<tr bgcolor="#eeeeee"><td width="15%" height=18 class=td align=center>编号：</td><td width="85%" class=td height=18><%=ID%></td></tr>
  		<tr bgcolor="#FFFFFF"><td width="15%" height=18 class=td align=center>标题：</td><td width="85%" class=td height=18><%=StringUtil.gb2iso4View(title)%></td></tr>
  		<tr bgcolor="#eeeeee"><td width="15%" height=18 class=td align=center>栏目：</td><td width="85%" class=td height=18><%=StringUtil.gb2iso4View(column)%></td></tr>
  		<tr bgcolor="#FFFFFF"><td width="15%" height=18 class=td align=center>编辑：</td><td width="85%" class=td height=18><%=editor%></td></tr>
  	</table>
  	</td></tr>
<%
  }
}
%>
</table>
<p>
<center><input type=button name=close_button class=td value="关  闭" onclick="javascript:window.close();"></center>

</body>
</html>
