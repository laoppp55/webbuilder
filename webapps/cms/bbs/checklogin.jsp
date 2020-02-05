<%@page import="com.bizwink.cms.util.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  String username = (String)session.getAttribute("userid");
  int forumid = ParamUtil.getIntParameter(request, "forumid", 0);

  if((username =="")||(username == null))
    response.sendRedirect("posttopic1.jsp?forumid="+forumid);
  else
    response.sendRedirect("posttopic2.jsp?forumid="+forumid);
%>

