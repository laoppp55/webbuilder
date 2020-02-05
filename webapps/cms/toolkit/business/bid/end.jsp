<%@ page import="java.io.*,
                 java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.bid.*,
                 com.booyee.search.*"
                 contentType="text/html;charset=gbk"
%>
<%
  String[] bid = request.getParameterValues("end");

  IBidManager bidMgr = BidPeer.getInstance();

  int id = 0;
  for(int i=0;i<bid.length;i++){
    id = Integer.parseInt(bid[i]);
    bidMgr.updateBidFlag(1,id);
  }

  response.sendRedirect("index.jsp");
%>