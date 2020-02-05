<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
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

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int start = ParamUtil.getIntParameter(request, "start", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  String msg = ParamUtil.getParameter(request, "msg");

  IArticleManager articleMgr = ArticlePeer.getInstance();
  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String CName = StringUtil.gb2iso4View(column.getCName());

  int total = articleMgr.getPublishedArticlesNum(columnID);
  List articleList = articleMgr.getPublishedArticles(columnID, start, range);
  int articleCount = articleList.size();
%>

<html>
<head>
  <title></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel=stylesheet type=text/css href="../style/global.css">
  <script language=javascript>
      function CA()
      {
          for (var i = 0; i < document.Headers.elements.length; i++)
          {
              var e = document.Headers.elements[i];
              if (e.name != 'allbox' && e.type == 'checkbox' && e.name != 'option')
                  e.checked = document.Headers.allbox.checked;
          }
      }

      function showthis(id)
      {
          var links = "auditarticle.jsp?article=" + parseInt(id);
          window.open(links, "temp");
      }

      function Preview(articleID)
      {
          window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
      }

      function editArticle(columnID, articleID, range, start, from)
      {
          window.open("../article/editarticle.jsp?article=" + articleID + "&range=" + range + "&start=" + start + "&fromflag=" + from, "", "width=935,height=650,left=5,top=5,status,scrollbars");
      }
  </script>
</head>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<center>
  <%
    String[][] titlebars = {
            {"发布管理", ""},
            {"文章发布", "articles.jsp?column=" + columnID},
            {CName, ""}
    };
    String[][] operations = {
            {"未发布的文章", "articles.jsp?column=" + columnID},
            {"重新发布", "republish.jsp?column=" + columnID}
    };
  %>
  <%@ include file="../inc/titlebar.jsp" %>
  <%
    if (msg != null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0) {
      out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
      out.println("<tr><td width=50% align=left class=line>");
      if (start - range >= 0)
        out.println("<a href=articles.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
      out.println("</td><td width=50% align=right class=line>");
      if (start + range < total) {
        int remain = ((start + range - total) < range) ? (total - start - range) : range;
        out.println(remain + "<a href=articles.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
      }
      out.println("</td></tr></table>");
    }
  %>

  <table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width='100%'>
    <tr class=itm bgcolor='#dddddd' height=25>
      <td align=center width="10%">操作<input type=hidden name=artcount value=<%=articleCount%>>
      <td align=center width="35%">标题</td>
      <td align=center width="8%">状态</td>
      <td align=center width='15%'>发布时间</td>
      <td align=center width="5%">排序</td>
      <td align=center width="10%">编辑</td>
      <td align=center width="8%">预览</td>
    </tr>
    <%
      for (int i = 0; i < articleCount; i++) {
        Article article = (Article) articleList.get(i);
        int articleID = article.getID();
        String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
        String editor = article.getEditor();
        int sortId = article.getSortID();

        String createdate = null;
        if (article.getPublishTime() == null)
          createdate = "已发表！";
        else
          createdate = article.getPublishTime().toString().substring(0, 16);

        String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
        out.println("<tr height=25 bgcolor=" + bgcolor + " onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "'\">");
        out.println("<td align=center><img src=\"../images/button/yi.gif\" border=0 alt=\"已经发布\"></td>");
        out.println("<td><a href=javascript:editArticle(" + columnID + "," + articleID + "," + range + "," + start + ",'p');>" + maintitle + "</a></td>");
        out.println("<td align=center><font color=green>已经发布</font></td>");

        out.println("<td align=center>" + createdate + "</td>");
        out.println("<td align=center>" + sortId + "</td>");
        out.println("<td>" + editor + "</td>");
        out.println("<td align=center>");
        out.println("<a href=javascript:Preview(" + articleID + ");><img src=\"../images/button/view.gif\" border=0></a>");
        out.println("</td></tr>");
      }
    %>
  </table>
</center>

</body>
</html>