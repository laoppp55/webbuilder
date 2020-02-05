<%@ page import="com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Message.*"
                 contentType="text/html;charset=gbk"
%>
<%@ include file="../../../include/auth.jsp"%>
<%
  int siteid = authToken.getSiteID();

  String userid = ParamUtil.getParameter(request, "userid");
  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
  IMessageManager messageMgr = messagePeer.getInstance();
  Message message = new Message();

  if(startflag == 1){

    String content = ParamUtil.getParameter(request, "content");
    message.setSiteID(siteid);
    message.setSend_User("系统管理员");
    message.setReceive_User(userid);
    message.setMessage(content);    
    messageMgr.newMessage(message);

    out.println("<script language=\"javascript\">");
    out.println("alert(\"信息发送成功！\");");
    out.println("window.close();");
    out.println("</script>");

  }
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript">
function check(frm){
  if((annoForm.content.value == "")||(annoForm.content.value == null)){
    alert("请输入消息内容后再发送！");
    return false;
  }
  return true;
}
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "消息管理", "index.jsp" },
              { "发送消息", "" }
          };

      String[][] operations = {
              { "发送消息", "sendmessage.jsp" },
              { "消息列表", "index.jsp" }
          };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form action="sendmessage.jsp" method="post" name="annoForm">
<input type="hidden" name="startflag" value="1">
<center>
<table width="600" border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">发送消息</font></td>
          </tr>

          <tr bgcolor="#d4d4d4" align="right">
            <td>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <tr bgcolor="#FFFFFF">
                    <td name="title">发送给：</td>
                      <td><input name="userid" type="text" value="<%=userid%>"> </td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td name="title">消息内容：</td>
                    <td>
                      <textarea rows="10" cols="70" name="content"></textarea>
                    </td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr align="center">
            <td><input type="submit" name="Submit" value="发送" onclick="javascript:return check(this.form);">
                &nbsp;
                <input name="button" type="reset" id="Reset" value="关闭" onclick="javascript:window.close();"></td>
          </tr>
        </table>
      </td>
</tr>
</table>
</center>
</form>
</center>
</body>
</html>
