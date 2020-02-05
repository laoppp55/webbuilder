<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.bid.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
  long bookid = ParamUtil.getLongParameter(request, "bookid", 0);

  ISearchManager searchMgr = SearchPeer.getInstance();
  IBidManager bidMgr = BidPeer.getInstance();

  searchMgr.updateShowFlag(bookid, 1);
  bidMgr.deleteBid(bookid);
  response.sendRedirect("add.jsp");
%>