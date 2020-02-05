<%@ page import="java.sql.*,
		 java.util.*,
		 java.text.*,
		 com.bizwink.cms.news.*,
		 com.bizwink.cms.security.*,
		 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
   int columnID  = ParamUtil.getIntParameter(request, "column", 0);
   int start = ParamUtil.getIntParameter(request,"start",0);
   int range = ParamUtil.getIntParameter(request,"range",20);
   String msg = ParamUtil.getParameter(request,"msg");

   IColumnManager columnManager = ColumnPeer.getInstance();
   Column column = columnManager.getColumn(columnID);
   String CName = column.getCName();


%>
<html><head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css"></head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%	////////////////////
	String[][] titlebars = {
	        { "文章管理", "articlesmain.jsp" },
	        { CName, "" }
	};
	String[][] operations = {
	        { "发表文章", "createarticle.jsp?column="+columnID }
	};
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
	// get an list of articles
	IArticleManager articleMgr = ArticlePeer.getInstance();
	int total = articleMgr.getArticleCount(columnID,100);
	List articleList = null;
	int articleCount = 0;
	articleList = articleMgr.getArticles(columnID, start, range);
	articleCount = articleList.size();
	if( msg != null ) {
	   out.println("<span class=cur>"+msg+"</span>");
    }

    if( (articleCount>0) ) {
      out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
      out.println("<tr><td width=50% align=left class=line>");
      if( (start-range) >= 0 ) {
         out.println("<a href=articles.jsp?column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
      } else {
         out.println("&nbsp;");
      }
      out.println("</td><td width=50% align=right class=line>");
      if( (start+range) < total ) {
	      int remain = ((start+range-total)<range)?(total-start-range):range;
	      out.println(remain+"<a href=articles.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
      } else {
        out.println("&nbsp;");
      }
      out.println("</td></tr></table>");
   }

//Modify by zhangyong on 5/28

   Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
if ( authToken != null && authToken.getPermissionSet().contains("audit") ) {
%>
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr class= itm bgcolor='#dddddd'>
    <td align=center widtd="10%">状态</td>
    <td align=center widtd="50%">标题</td>
    <td align=center widtd="10%">编辑</td>
    <td align=center widtd="10%">修改</td>
    <td align=center width="10%">审核</td>
    <td align=center widtd="10%">删除</td></tr>
    <%
       for ( int i=0; i<articleCount; i++) {
	  Article article = (Article)articleList.get(i);
	      int articleID = article.getID();
	      String maintitle = article.getMainTitle();
	  String editor = article.getEditor();
	  String bgcolor = (i%2==0)?"#ffffcc":"#eeeeee";
	  int  status = article.getPubFlag();
    %>
    <tr bgcolor=<%=bgcolor%> class=itm>
    <% if (status == 0) {
	out.println("<td align=center>新稿</td>");
    }else {
	out.println("<td align=center><font color=red>已审核</font></td>");
    }%>
    <td><%= maintitle %></td>
    <td><%= editor %></td>
    <td align=center><a href="editarticle.jsp?article=<%=articleID%>">
    <img src="../images/edit.gif" align="bottom" border=0></a></td>
    <td align=center><a href="auditarticle.jsp?article=<%=articleID%>">
    <img src="../images/edit.gif" align="bottom" border=0></a></td>
    <td align=center><a href="removearticle.jsp?article=<%=articleID%>">
    <img src="../images/del.gif" align="bottom" border=0></a></td></tr>
    <%
       }
    %>
    </table>
<%} else { %>
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr class= itm bgcolor='#dddddd'>
    <td align=center widtd="15%">状态</td>
    <td align=center widtd="50%">标题</td>
    <td align=center widtd="15%">编辑</td>
    <td align=center widtd="10%">修改</td>
    <td align=center widtd="10%">删除</td></tr>
    <%
       for ( int i=0; i<articleCount; i++) {
	  Article article = (Article)articleList.get(i);
	      int articleID = article.getID();
	      String maintitle = article.getMainTitle();
	  String editor = article.getEditor();
	  String bgcolor = (i%2==0)?"#ffffcc":"#eeeeee";
    %>
    <tr bgcolor=<%=bgcolor%> class=itm>
    <td align=center>新稿</td>
    <td><%= maintitle %></td>
    <td><%= editor %></td>
    <td align=center><a href="editarticle.jsp?article=<%=articleID%>">
    <img src="../images/edit.gif" align="bottom" border=0></a></td>
    <td align=center><a href="removearticle.jsp?article=<%=articleID%>">
    <img src="../images/del.gif" align="bottom" border=0></a></td></tr>
    <%
       }
    %>
    </table>
<%} %>
</BODY></html>