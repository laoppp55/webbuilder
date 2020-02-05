<%@ page import="com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int lwid = ParamUtil.getIntParameter(request, "lwid", 0);
  int start = ParamUtil.getIntParameter(request, "start", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
%>

<html>
<head>
  <script language=javascript>
    function closeme()
    {
      opener.location.href = "index1.jsp?markid=<%=lwid%>&startrow=<%=start%>&range=<%=range%>";
      window.close();
    }
  </script>
</head>

<Body onload="javascript:closeme()">
</body>
</html>
