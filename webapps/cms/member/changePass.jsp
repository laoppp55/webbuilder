<%@ page import="com.bizwink.cms.util.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*"
         contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp?url=member/removeMember.jsp" );
    return;
  }

  String userID = authToken.getUserID();
  boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
  boolean error = false;
  String pass = null;

  if (doUpdate)
  {
    pass = ParamUtil.getParameter(request, "pass1");
    if (userID == null || pass == null)  error = true;
  }

  if (doUpdate && !error)
  {
    try
    {
      IAuditManager auditMgr = AuditPeer.getInstance();
      auditMgr.updatePassword(userID, pass);
      response.sendRedirect("../main.jsp");
      return;
    }
    catch (CmsException ue)
    {
      ue.printStackTrace();
    }
  }
%>

<html>
<head>
  <title></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="../style/global.css">
</head>

<body>
<%
  String[][] titlebars = {
  };
  String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p class=tine>&nbsp;&nbsp;&nbsp;修改用户 <b><%=userID%></b>&nbsp;的密码<p>

<form action="changePass.jsp" name="updateForm" onsubmit="javascript:return CheckPass();">
  <input type="hidden" name="doUpdate" value="true">
  <input type="hidden" name="userid" value="<%=userID%>">
  <font class=tine>
    &nbsp;&nbsp;
    请输入新密码：&nbsp;&nbsp;<input type="password" name="pass1" size=12>
    <br><br>
    &nbsp;&nbsp;
    请再次输入密码：<input type="password" name="pass2" size=12>
    <br><br><br>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="  确定  ">&nbsp;&nbsp;
    <input type="button" name="cancel" value="  取消  " onclick="location.href='../main.jsp';return false;">
  </font>
</form>
<script language="JavaScript">
    function CheckPass()
    {
        if (updateForm.pass1.value == "" || updateForm.pass2.value == "")
        {
            alert("密码不能为空！");
            return false;
        }
        else if (updateForm.pass1.value != updateForm.pass2.value)
        {
            alert("两次输入的密码不相同！");
            return false;
        }
        else
        {
            return true;
        }
    }
</script>

</body>
</html>
