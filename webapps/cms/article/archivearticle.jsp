<%@page import="java.sql.*,
                java.util.*,
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

  List articleList = articleMgr.getArchiveArticles(columnID,start,range);
  int total = articleMgr.getArchiveArticlesNum(columnID);
  int articleCount = articleList.size();
%>

<html>
<head>
  <title>未用文章</title>
  <meta http-equiv=Content-Type content="text/html; charset=utf-8">
  <link rel=stylesheet type=text/css href="../style/global.css">
  <script language="javascript">
      function Preview(para)
      {
          window.open(para, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
      }

      function Update(articleID)
      {
          <%if (isDefineAttr == 2){%>
          window.open("check.jsp?column=<%=columnID%>&article="+articleID+"&range=<%=range%>&start=<%=start%>&fromflag=u","Update","width=930,height=650,left=5,top=5,status,scrollbars");
          <%}else{%>
          window.open("editarticle.jsp?article="+articleID+"&range=<%=range%>&start=<%=start%>&fromflag=h","Update","width=930,height=650,left=5,top=5,status");
          <%}%>
      }
  </script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
  String[][] titlebars = {
          { "文章管理", "articlesmain.jsp" },
          { CName, "" }
  };
  String[][] operations = {
          {"新稿", "articles.jsp?column="+columnID},
          {"退稿", "returnarticle.jsp?column="+columnID},
          {"在审", "auditarticle.jsp?column="+columnID},
          {"未用", "unusedarticle.jsp?column="+columnID}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
  if (msg != null) out.println("<span class=cur>"+msg+"</span>");
  if (articleCount > 0)
  {
    out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
    out.println("<tr><td width=50% align=left class=line>");
    if (start - range >= 0)
      out.println("<a href=archivearticle.jsp?column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
    out.println("</td><td width=50% align=right class=line>");
    if (start + range < total)
    {
      int remain = ((start+range-total)<range)?(total-start-range):range;
      out.println(remain+"<a href=archivearticle.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
    }
    out.println("</td></tr></table>");
  }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
  <tr class=itm bgcolor="#dddddd" height=20>
    <td align=center width="5%">状态</td>
    <td align=center width="35%">标题</td>
    <td align=center width="15%">发布时间</td>
    <td align=center width="5%">排序</td>
    <td align=center width="13%">编辑</td>
    <td align=center width="9%">预览</td>
    <td align=center width="9%">操作</td>
    <td align=center width="9%">删除</td>
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
    <td align=center><font color=gray>归档</font></td>
    <td><a href=javascript:Update(<%=articleID%>);><%= maintitle %></a></td>
    <td align=center><%= createdate %></td>
    <td align=center><%= sortId %></td>
    <td><%= editor %></td>
    <td align=center><a href=javascript:Preview(previewarticle.jsp?article=<%=articleID%>);><img src="../images/button/view.gif" border=0></a></td>
    <td align=center><a href=javascript:Update(<%=articleID%>);><img src="../images/button/edit.gif" align="bottom" border=0></a></td>
    <td align=center><a href="removearticle.jsp?article=<%=articleID%>&range=<%=range%>&start=<%=start%>&fromflag=u"><img src="../images/button/del.gif" align="bottom" border=0></a></td>
  </tr>
  <%}%>
</table>
</BODY>
</html>
