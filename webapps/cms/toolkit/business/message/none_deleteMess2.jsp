<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.message.*" contentType="text/html;charset=gbk"
%>
<%
  String[] messages = request.getParameterValues("delMessage");

  IMessageManager messageMgr = MessagePeer.getInstance();

  int id = 0;
  for(int i=0;i<messages.length;i++){
    id = Integer.parseInt(messages[i]);
    messageMgr.deleteMessage(id);
  }

  response.sendRedirect("index2.jsp");
%>