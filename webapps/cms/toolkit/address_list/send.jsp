<%@page import="com.bizwink.cms.toolkit.addresslist.*,
                java.util.*,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.SessionUtil,
                java.sql.Timestamp" contentType="text/html;charset=GBK"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  String memberid = authToken.getUserID();

  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
  int id = ParamUtil.getIntParameter(request, "id", 0);
  String name = ParamUtil.getParameter(request, "name");
  String content = ParamUtil.getParameter(request, "content");
  String userid = ParamUtil.getParameter(request, "userid");

  if(startflag == 1){
    long nowtime = System.currentTimeMillis();
    IAddressListManager addresslistMgr = AddressListPeer.getInstance();
    AddressList addresslist = new AddressList();
    addresslist.setSender(memberid);
    addresslist.setReceiver(userid);
    addresslist.setContent(content);
    addresslist.setSendDate(nowtime);
    addresslistMgr.insertMessage(addresslist);
    out.println("<script language='javascript'>");
    out.println("window.close();");
    out.println("</script>");
  }
%>
<HTML><HEAD><TITLE>New Page 1</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<LINK href="images/common.css" type=text/css rel=stylesheet>
<LINK href="images/forum.css" type=text/css rel=stylesheet>
</head>
<body>
<form name="sendform" method="post" action="send.jsp">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="userid" value="<%=userid%>">
<table border="1" width="100%" bordercolorlight="#0000FF" cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF">
  <tr>
    <td width="25%">收信者：</td>
    <td width="75%"><%=name%></td>
  </tr>
  <tr>
    <td width="25%">内容：</td>
    <td width="75%"><textarea cols=50 rows=10 name="content"></textarea></td>
  </tr>
</table>
<br>
<center>
<table>
<tr>
<td><input type="submit" value=" 发送 "></td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td><input type="button" value=" 取消 " onclick="javascript:window.close();"></td>
</tr>
</table>
</center>
</form>
</body>
</html>