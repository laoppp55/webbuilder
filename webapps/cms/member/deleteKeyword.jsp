<%@ page import="java.util.Calendar,
                 java.sql.Timestamp,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*,
                 java.util.List,
                 java.util.ArrayList"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  boolean error = false;
  int errorflag = 1;
  boolean doDelete = ParamUtil.getBooleanParameter(request, "doDelete");
  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int kid = ParamUtil.getIntParameter(request , "id", -1);

  if (doDelete)
  {
    if ((columnID == 0) || (kid == -1))
    {
      errorflag = 1;
      error = true;
    }

    if (!error)
    {
      IArticleManager articleMgr = ArticlePeer.getInstance();
      articleMgr.deleteColumnKeyword(kid);
      errorflag = 0;
      response.sendRedirect("keywordRight.jsp?column="+columnID+"&errorflag="+errorflag);
    }
  }
%>