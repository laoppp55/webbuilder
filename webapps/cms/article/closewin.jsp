<%@ page import="com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
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
  boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
  int flag = ParamUtil.getIntParameter(request,"flag",0);
  int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);
  String from = ParamUtil.getParameter(request, "fromflag");

  if (from == null) from = "";
  int errcode = 0;
  if (from.equalsIgnoreCase("a") || from.equalsIgnoreCase("c")) {
    IArticleManager articleMgr = ArticlePeer.getInstance();
    errcode = articleMgr.updatecancle(articleID);
  }
%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <script language=javascript>
      function closeme() {
          <%if (from.equalsIgnoreCase("c")){%>
          opener.location.href = "indexnew.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>&searchFlag=<%=doSearchFlag%>&flag=<%=flag%>&ascdesc=<%=ascdesc%>";
          <%} else if (from.equalsIgnoreCase("a")){%>
          opener.location.href = "auditarticle.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>"
          <%} else if (from.equalsIgnoreCase("r")){%>
          opener.location.href = "returnarticle.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>"
          <%} else if (from.equalsIgnoreCase("u")){%>
          opener.location.href = "unusedarticle.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>"
          <%} else if (from.equalsIgnoreCase("p")){%>
          opener.location.href = "../publish/published.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>"
          <%} else if (from.equalsIgnoreCase("h")) {%>
          opener.location.href = "../article/archivearticle.jsp?column=<%=columnID%>&start=<%=start%>&range=<%=range%>"
          <%}%>
          window.close();
      }
  </script>
</head>

<Body onload="javascript:closeme()">
</body>
</html>
