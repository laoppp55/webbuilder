<%@page import="com.bizwink.cms.modelManager.*,
    		com.bizwink.cms.security.*,
    		com.bizwink.cms.util.*"
        contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request, "columnID", 0);
  String path = request.getRealPath("/");

  //读出模板列表
  TemplatePeer tempMgr = new TemplatePeer();
  String content = tempMgr.getTemplatesTree(columnID, path);
%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel=stylesheet type=text/css href="../style/global.css">
  <title></title>
  <script src="../js/template.js"></script>
</head>

<body bgcolor="#DEDFDE">

<form action="" method="POST" name=form1>
  <table border=0 width="95%" align=center>
    <tr>
      <td width="100%" align=center>
        <%=content%>
      </td>
    </tr>
  </table>
</form>

</body>
</html>
