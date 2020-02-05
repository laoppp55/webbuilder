<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.Map,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String editor = authToken.getUserID();
  if (SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 50)) {
    editor = "";
  }

  int columnID = ParamUtil.getIntParameter(request, "column", 0);
  int start = ParamUtil.getIntParameter(request, "start", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);
  String msg = ParamUtil.getParameter(request, "msg");

  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String CName = StringUtil.gb2iso4View(column.getCName());

  IArticleManager articleMgr = ArticlePeer.getInstance();
  int total = articleMgr.getUploadFilesNum(columnID, editor);
  List articleList = articleMgr.getUploadFiles(columnID, start, range, editor);
  int articleCount = articleList.size();
%>

<html>
<head>
  <title></title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="../style/global.css">
  <SCRIPT LANGUAGE=JavaScript>
    function Preview(articleID)
    {
      window.open("../article/Preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
    }

    function WebEdit()
    {
      window.open("../webedit/index.jsp?column=<%=columnID%>&right=4", "WebEdit", "width=850,height=600,left=5,top=5,scrollbars");
    }
  </SCRIPT>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
  String[][] titlebars = {
          {"文件管理", "index.jsp"},
          {CName, ""}
  };
  String[][] operations = {
          {"图片上传", "uploadpics.jsp?column=" + columnID},
          {"新建", "createuploadfile.jsp?column=" + columnID},
          {"未用", "unuseduploadfile.jsp?column=" + columnID},
          {"审核", "audituploadfiles.jsp?column=" + columnID},
          {"退稿", "backuploadfiles.jsp?column=" + columnID},
          {"<a href=javascript:WebEdit();>文件夹管理</a>", ""}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
  if (msg != null) out.println("<span class=cur>" + msg + "</span>");
  if (articleCount > 0) {
    out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
    out.println("<tr><td width=50% align=left class=line>");
    if (start - range >= 0)
      out.println("<a href=listuploadfiles.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
    out.println("</td><td width=50% align=right class=line>");
    if (start + range < total) {
      int remain = ((start + range - total) < range) ? (total - start - range) : range;
      out.println(remain + "<a href=listuploadfiles.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
    }
    out.println("</td></tr></table>");
  }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
  <tr class=itm bgcolor="#dddddd" height=20>
    <td align=center width="7%">类型</td>
    <td align=center width="31%">标题</td>
    <td align=center width="20%">发布时间</td>
    <td align=center width="7%">主权重</td>
    <td align=center width="7%">排序</td>
    <td align=center width="7%">编辑</td>
    <td align=center width="7%">预览</td>
    <td align=center width="7%">修改</td>
    <td align=center width="7%">删除</td>
  </tr>
  <%
    for (int i = 0; i < articleCount; i++) {
      Article article = (Article) articleList.get(i);

      int articleID = article.getID();
      String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
      editor = StringUtil.gb2iso4View(article.getEditor());
      String createdate = article.getPublishTime().toString().substring(0, 16);
      int sortId = article.getSortID();
      int doclevel = article.getDocLevel();

      String filename = article.getFileName();
      String ext = ".txt";
      if ((filename != null) && (filename.indexOf(".") != -1)) {
        ext = filename.substring(filename.lastIndexOf(".") + 1);
      }
      Map map = new Map();
      ext = map.getFileType(ext);

      String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";

      out.println("<tr bgcolor=" + bgcolor + " class=itm height=25 onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "'\">");
  %>
  <td align=center><img src="../images/button/<%=ext%>" alt=""></td>
  <td>&nbsp;&nbsp;<a href="edituploadfile.jsp?article=<%=articleID%>"><%=maintitle%></a></td>
  <td align=center><%=createdate%></td>
  <td align=center><%=doclevel%></td>
  <td align=center><%=sortId%></td>
  <td align=center><%=editor%></td>
  <td align=center><a href=javascript:Preview(<%=articleID%>);><img src="../images/button/view.gif" border=0></a></td>
  <%if (SecurityCheck.hasPermission(authToken, 51) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID())) {%>
  <td align=center><a href="edituploadfile.jsp?article=<%=articleID%>"><img src="../images/button/edit.gif" align="bottom"
                                                                            border=0></a></td>
  <%} else {%>
  <td align=center>&nbsp;</td>
  <%}%>
  <%if (SecurityCheck.hasPermission(authToken, 52) || SecurityCheck.hasPermission(authToken, 54) || editor.equalsIgnoreCase(authToken.getUserID())) {%>
  <td align=center><a href="removefile.jsp?article=<%=articleID%>"><img src="../images/button/del.gif" align="bottom" border=0></a>
  </td>
  <%} else {%>
  <td align=center>&nbsp;</td>
  <%}%>
</tr>
<%}%>
</table>

</BODY>
</html>
