<%@ page import="com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int articleID   = ParamUtil.getIntParameter(request,"article", 0);
  boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");
  IArticleManager articleManager = ArticlePeer.getInstance();
  Article article= articleManager.getArticle(articleID);
  int columnID = article.getColumnID();
  boolean success = false;
  int msgno = -1;
  String editor = authToken.getUserID();

  int siteid = authToken.getSiteID();
  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String columnName = StringUtil.gb2iso4View(column.getCName());

  if (doDelete)
  {
    try
    {
      articleManager.remove(articleID,siteid,editor,1);
      success = true;
      msgno = 0;
    }
    catch (ArticleException e)
    {
      e.printStackTrace();
      msgno = 1;
    }
  }

  if (success)
  {
    response.sendRedirect(response.encodeRedirectURL("listuploadfiles.jsp?column=" + columnID + "&msgno=" + msgno));
    return;
  }
%>
<html><head>
  <title></title>
  <meta http-equiv=Content-Type content="text/html; charset=utf-8">
  <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
  String[][] titlebars = {
          {"文件管理", "articlesmain.jsp" },
          {columnName, "listuploadfiles.jsp?column="+columnID },
          {"删除文件", ""}
  };
  String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>

<p class=line>删除文件 <%= StringUtil.gb2iso4View(article.getMainTitle()) %> ?<p>
<ul class=cur>警告: 操作将删除此文件，您真的想删除吗?</ul>
<form action=removefile.jsp name=deleteForm>
  <input type=hidden name=doDelete value=true>
  <input type=hidden name=article value=<%= articleID %>>
  <input type=image src=../images/button_dele.gif onclick="document.all.deleteForm.submit()">
  &nbsp;
  <input type=image src=../images/button_cancel.gif  onclick="javascript:history.back();return false;">
</form>
</body></html>