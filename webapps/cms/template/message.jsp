<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
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

  int errcode = ParamUtil.getIntParameter(request,"retcode",0);
%>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>确认信息</title>
<link rel=stylesheet type=text/css href=style/global.css></head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    switch(errcode) {
      case 0:
        out.println("选择模板成功");
        break;
      case -1:
        out.println("选择模板失败");
        break;
      case -2:
        out.println("选择模板失败");
        break;
      case -3:
        out.println("选择模板失败");
        break;
      case -4:
        out.println("选择模板失败");
        break;
      default:
        return;
    }
%>
</body>
</html>
