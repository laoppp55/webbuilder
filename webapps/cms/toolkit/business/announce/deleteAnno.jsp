<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.announce.*" contentType="text/html;charset=gbk"
%>
<%
  String[] messages = request.getParameterValues("delAnnounce");

  IAnnounceManager announceMgr = AnnouncePeer.getInstance();

  int id = 0;
  for(int i=0;i<messages.length;i++){
    id = Integer.parseInt(messages[i]);
    announceMgr.deleteAnnounce(id);
  }

  response.sendRedirect("index.jsp");
%>