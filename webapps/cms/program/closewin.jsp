<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
%>

<html>
<head>
  <meta http-equiv=Content-Type content="text/html; charset=utf-8">
  <script language=javascript>
      function closeme()
      {
          opener.location.href = "/webbuilder/program/pmanager.jsp"
          window.close();
      }
  </script>
</head>
<Body onload="javascript:closeme()">
</body>
</html>
