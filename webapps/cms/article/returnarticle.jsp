<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
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

  int columnID  = ParamUtil.getIntParameter(request, "column", 0);
  int start = ParamUtil.getIntParameter(request,"start",0);
  int range = ParamUtil.getIntParameter(request,"range",20);
  String msg = ParamUtil.getParameter(request,"msg");

  IArticleManager articleMgr = ArticlePeer.getInstance();
  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String CName = StringUtil.gb2iso4View(column.getCName());
  int isDefineAttr = column.getDefineAttr();

  //退给作者的文章
  List articleList = articleMgr.getBackArticles(columnID,authToken.getUserID(),start,range,1);
  int total = articleMgr.getBackArticlesNum(columnID,authToken.getUserID(),1);
  int articleCount = articleList.size();
  System.out.println(total);
%>

<html>
<head>
  <title>退稿文章</title>
  <meta http-equiv=Content-Type content="text/html; charset=utf-8">
  <link rel=stylesheet type=text/css href="../style/global.css">
  <script language="javascript">
      function Preview(articleID)
      {
          window.open("previewarticle.jsp?article="+articleID,"Preview","width=800,height=600,left=5,top=5,scrollbars");
      }

      function Update(articleID)
      {
          <%if (isDefineAttr == 2){%>
          window.open("check.jsp?column=<%=columnID%>&article="+articleID+"&range=<%=range%>&start=<%=start%>&fromflag=r","Update","width=930,height=650,left=5,top=5,status,scrollbars");
          <%}else{%>
          window.open("editarticle.jsp?article="+articleID+"&range=<%=range%>&start=<%=start%>&fromflag=r","Update","width=930,height=650,left=5,top=5,status");
          <%}%>
      }
  </script>
  <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%	String[][] titlebars = {
        { "文章管理", "articlesmain.jsp" },
        { CName, "" }
};
  String[][] operations = {
          {"新稿", "articles.jsp?column="+columnID},
          {"在审", "auditarticle.jsp?column="+columnID},
          {"未用", "unusedarticle.jsp?column="+columnID},
          {"归档","archivearticle.jsp?column="+columnID}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
  if (msg != null) out.println("<span class=cur>"+msg+"</span>");
  if (articleCount > 0)
  {
    out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
    out.println("<tr><td width=50% align=left class=line>");
    if (start-range >= 0)
      out.println("<a href=returnarticle.jsp?column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
    out.println("</td><td width=50% align=right class=line>");
    if (start+range < total)
    {
      int remain = ((start+range-total)<range)?(total-start-range):range;
      out.println(remain+"<a href=returnarticle.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
    }
    out.println("</td></tr></table>");
  }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
  <tr class=itm bgcolor="#dddddd" height=20>
    <td align=center width="5%">状态</td>
    <td align=center width="40%">标题</td>
    <td align=center width="15%">发布时间</td>
    <td align=center width="5%">排序</td>
    <td align=center width="15%">编辑</td>
    <td align=center width="10%">预览</td>
    <td align=center width="10%">操作</td>
  </tr>
  <%
    for (int i=0; i<articleCount; i++)
    {
      Article article = (Article)articleList.get(i);

      int articleID = article.getID();
      String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
      String editor = article.getEditor();
      String createdate = article.getPublishTime().toString().substring(0,16);
      int sortId = article.getSortID();
      String bgcolor = (i%2==0)?"#ffffff":"#eeeeee";
  %>
  <tr bgcolor="<%=bgcolor%>" height=25>
    <td align=center><img src="../images/button/tui.gif" alt="退稿"> </td>
    <td><a href=javascript:Update(<%=articleID%>);><%= maintitle %></a></td>
    <td align=center><%= createdate %></td>
    <td><%= sortId %></td>
    <td><%= editor %></td>
    <td align=center><a href=javascript:Preview(<%=articleID%>);><img src="../images/button/view.gif" border=0></a></td>
    <td align=center>
      <a href=javascript:Update(<%=articleID%>);><img src="../images/button/edit.gif" align="bottom" border=0></a>
    </td>
  </tr>
  <%}%>
</table>
</BODY>
</html>
