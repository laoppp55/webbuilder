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

    String fromflag = ParamUtil.getParameter(request, "from");
%>

<html>
<head>
    <script language=javascript>
        function closeme()
        {
            var fromtag = '<%=fromflag%>';
            if (fromtag=='attach') {
                opener.location.href = "/webbuilder/member/attachTypeManager.jsp"
            } else {
                opener.location.href = "/webbuilder/member/programsfortoolkit.jsp"
            }
            window.close();
        }
    </script>
</head>
<Body onload="javascript:closeme()">
</body>
</html>
