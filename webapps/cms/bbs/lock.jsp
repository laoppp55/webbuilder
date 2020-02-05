<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  int threadid = ParamUtil.getIntParameter(request, "threadid", 0);
  int forumid = ParamUtil.getIntParameter(request, "forumid", 0);
  IBBSManager bbsMgr = BBSPeer.getInstance();
  bbsMgr.updateFlag(threadid, 4);

  out.println("<script language=javascript>");
  out.println("opener.history.go(0);");
  out.println("window.location=\"thread.jsp?threadid="+threadid+"&forumid="+forumid+"\";");
  out.println("</script>");
%>

