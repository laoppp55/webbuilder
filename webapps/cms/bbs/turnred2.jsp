<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bucid.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  int threadid = ParamUtil.getIntParameter(request, "threadid", 0);
  int forumid = ParamUtil.getIntParameter(request, "forumid", 0);
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();
  bbs = bbsMgr.getAThread(threadid);

  String threadname = bbs.getThreadName();
  threadname = threadname.substring(threadname.indexOf("<font color=red>")+16, threadname.indexOf("</font>")) ;

  String threadcontent = bbs.getThreadContent();
  bbsMgr.updateAThread(threadname,threadcontent,threadid);

  out.println("<script language=javascript>");
  out.println("opener.history.go(0);");
  out.println("window.location=\"thread.jsp?threadid="+threadid+"&forumid="+forumid+"\";");
  out.println("</script>");
%>

