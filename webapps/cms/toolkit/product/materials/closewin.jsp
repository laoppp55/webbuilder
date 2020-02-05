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

  int articleID = ParamUtil.getIntParameter(request, "id", 0);
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int start = ParamUtil.getIntParameter(request, "start", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  String from = ParamUtil.getParameter(request, "fromflag");

  //if (from == null) from = "";
  //if (from.equalsIgnoreCase("a") || from.equalsIgnoreCase("c")) {
  //  IArticleManager articleMgr = ArticlePeer.getInstance();
  //  articleMgr.updatecancle(articleID);
  //}
%>

<html>
<head>
  <script language=javascript>
    function closeme()
    {
      opener.location.href = "companys.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>";
      window.close();
    }
  </script>
</head>

<Body onload="javascript:closeme()">
</body>
</html>
