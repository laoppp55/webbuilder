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
  int msgno = ParamUtil.getIntParameter(request, "msgno", 1);

  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String CName = StringUtil.gb2iso4View(column.getCName());

  IArticleManager articleMgr = ArticlePeer.getInstance();
  int total = articleMgr.getRePublishArticlesNum(columnID);
  List list = articleMgr.getRePublishArticles(columnID, start, range);
  int count = list.size();
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
              {
                  e.checked = document.Headers.allbox.checked;
              }
          }
      }

      function PreviewArticle(articleID)
      {
          window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=0,top=0,scrollbars");
      }

      function PreviewTemplate(templateID, isArticle, columnID)
      {
          window.open("../template/previewTemplate.jsp?column=" + columnID + "&template=" + templateID + "&isArticle=" + isArticle, "Preview", "width=800,height=600,left=0,top=0,scrollbars");
      }
  </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
  <%
    String[][] titlebars = {
            {"发布管理", ""},
            {"文章发布", "articles.jsp?column=" + columnID},
            {CName, ""}
    };
    String[][] operations = {
            {"未发布的文章", "articles.jsp?column=" + columnID},
            {"发布失败的文章", "publishfailed.jsp?column=" + columnID}
    };
  %>
  <%@ include file="../inc/titlebar.jsp" %>
  <%
    if (msgno == 0)
      out.println("<span class=cur>页面发布成功！</span>");
    else if (msgno == -1)
      out.println("<span class=cur>读取文章时出现错误！</span>");
    else if (msgno == -2)
      out.println("<span class=cur>读取模板时出现错误！</span>");
    else if (msgno == -3)
      out.println("<span class=cur>处理标记时出现错误！</span>");
    else if (msgno == -4)
      out.println("<span class=cur>读取样式文件时出现错误！</span>");
    else if (msgno == -9)
      out.println("<span class=cur>发布出现意外错误！</span>");

    if (count > 0) {
      out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
      out.println("<tr><td width=50% align=left class=line>");
      if (start - range >= 0)
        out.println("<a href=republish.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
      out.println("</td><td width=50% align=right class=line>");
      if (start + range < total) {
        int remain = ((start + range - total) < range) ? (total - start - range) : range;
        out.println(remain + "<a href=republish.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
      }
      out.println("</td></tr></table>");
    }
  %>

  <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <form method="Post" action="publish.jsp?source=1" name=Headers>
      <input type=hidden name=count value="<%=count%>">
      <input type=hidden name=column value="<%=columnID%>">
      <input type=hidden name=start value="<%=start%>">
      <input type=hidden name=range value="<%=range%>">
      <tr class=itm bgcolor="#dddddd" height=20>
        <td align=center width="10%">状态</td>
        <td align=center width="8%">操作</td>
        <td align=center width="35%">标题</td>
        <td align=center width="14%">所属栏目</td>
        <td align=center width="15%">修改时间</td>
        <td align=center width="10%">编辑</td>
        <td align=center width="8%">预览</td>
      </tr>
        <%
      for (int i = 0; i < count; i++) {
        Article article = (Article) list.get(i);

        int articleID = article.getID();         //文章ID或模板ID
        String editor = article.getEditor();     //文章或模板的编辑者
        int articleCID = article.getColumnID();
        boolean isown = true;
        if(articleCID != columnID) isown = false;
        String lastUpdated = article.getLastUpdated().toString().substring(0, 19); //文章或模板的最后修改时间
        int cid = article.getColumnID();         //文章或模板所属栏目ID
        String columnName = columnManager.getColumn(cid).getCName();  //当前栏目中文名称
        columnName = StringUtil.gb2iso4View(columnName);
        String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";

        int isArticleTemplate = 0;     //是文章模板还是栏目模板或首页模板
        String maintitle = StringUtil.gb2iso4View(article.getMainTitle());  //文章标题
        boolean isTemplate = article.getIsTemplate();    //是文章还是模板
        if (isTemplate) {
          int status = article.getStatus();      //是否为默认模板
          maintitle = article.getMainTitle();    //模板中文名称
          if (maintitle == null) maintitle = "";
          maintitle = StringUtil.gb2iso4View(maintitle);
          isArticleTemplate = article.getIsArticleTemplate();
          if (isArticleTemplate == 0) {
            if (status == 1)
              maintitle = "<font color=red>" + maintitle + "(默认栏目模板)</font>";
            else
              maintitle = "<font color=red>" + maintitle + "(栏目模板)</font>";
          } else if (isArticleTemplate == 2) {
            if (status == 1)
              maintitle = "<font color=red>" + maintitle + "(默认首页模板)</font>";
            else
              maintitle = "<font color=red>" + maintitle + "(首页模板)</font>";
          } else {
            maintitle = "<font color=red>" + maintitle + "(专题模板)</font>";
          }
        }

        out.println("<tr bgcolor=" + bgcolor + ">");
        out.println("<td align=center><img src=\"../images/button/yi.gif\" border=0 alt=\"准备发布\"></td>");
        out.println("<td align=center>选中");
        out.println("<input type=checkbox name=selected" + i + " value=\"" + articleID + "\">");
        out.println("<input type=hidden name=template" + i + " value=\"" + isTemplate + "\">");
        out.println("<input type=hidden name=column" + i + " value=\"" + cid + "\">");
        out.println("<input type=hidden name=isown"+i+" value="+isown+">");
        out.println("</td>");
        out.println("<td>" + maintitle + "</td>");
        out.println("<td align=center>" + columnName + "</td>");
        out.println("<td align=center>" + lastUpdated + "</td>");
        out.println("<td>&nbsp;" + editor + "</td>");
        if (isTemplate)
          out.println("<td align=center><a href=javascript:PreviewTemplate(" + articleID + "," + isArticleTemplate + "," + cid + ");>");
        else
          out.println("<td align=center><a href=javascript:PreviewArticle(" + articleID + ");>");
        out.println("<img src=\"../images/button/view.gif\" border=0></a></td>");
        out.println("</tr>");
      }
    %>
  </table>
  <br>
  <table border=0 cellPadding=0 cellSpacing=0 width="80%">
    <tr>
      <td align=center><input type=submit name=publish value="  重新发布  "></td>
      <td class=itm align=center><input type=checkbox name=allbox value="CheckAll" onClick="CA();">全部选中</td>
    </tr>
  </table>
  </form>
</center>

</body>
</html>