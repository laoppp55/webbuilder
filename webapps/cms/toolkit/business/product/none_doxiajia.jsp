<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
  String[] messages = request.getParameterValues("xiajia");

  ISearchManager searchMgr = SearchPeer.getInstance();

  long bookid = 0;
  for(int i=0;i<messages.length;i++){
    bookid = Integer.parseInt(messages[i]);
    searchMgr.updateShowFlag(bookid,0);
  }

  response.sendRedirect("xiajia.jsp");
%>