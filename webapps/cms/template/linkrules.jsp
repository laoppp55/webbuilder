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

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>文章列表属性</title>
  <script language="javascript">
      function setlinkrules()
      {
          var rule="[TAG][LINK][COLUMNS]"+linkrule.cnames.value + "[/COLUMNS][COLUMNIDS]" + linkrule.cids.value +
              "[/COLUMNIDS][WEIGHT]" + linkrule.weight.value +"[/WEIGHT][/LINK][/TAG]";
          parent.window.opener.document.linkform.oHref.value = rule;
          parent.window.close();
      }
  </script>
</head>
<body>
<form name=linkrule>
  <input type="text" name=cnames value="">
  <input type="hidden" name=cids value="">
  <input type="text"  name = weight value="">
  <input type="button" name="ok"  value="确定" onclick=javascript:setlinkrules()>
  <input type="button" name="cancel" value="返回" onclick=javascript:parent.window.close()>
</form>
</body>
</html>