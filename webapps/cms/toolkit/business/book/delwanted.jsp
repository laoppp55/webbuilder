<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.news.*,
                com.bizwink.cms.server.*,
                com.bizwink.cms.tree.*,
                com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
   ISearchManager searchMgr = SearchPeer.getInstance();
   int wantedid;
   String[] wantedids       = request.getParameterValues("delwanted");

    for(int i=0;i<wantedids.length;i++){
      wantedid = Integer.parseInt(wantedids[i]);
      searchMgr.delWantedBooks(wantedid);
    }
   response.sendRedirect("yuding.jsp");
%>