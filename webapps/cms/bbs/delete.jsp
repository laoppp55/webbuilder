<%@page import="com.bizwink.cms.util.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  int threadid = ParamUtil.getIntParameter(request, "threadid", 0);
  int forumid = ParamUtil.getIntParameter(request, "forumid", 0);
  int answerid = ParamUtil.getIntParameter(request, "answerid", 0);
  IBBSManager bbsMgr = BBSPeer.getInstance();
  if(answerid == 0){
    bbsMgr.deleteThread(threadid);
    out.println("<script language=javascript>");
    out.println("opener.history.go(0);");
    out.println("window.close();");
    out.println("</script>");
  }else{
    bbsMgr.deleteThread(answerid);
    out.println("<script language=javascript>");
    out.println("window.location=\"thread.jsp?threadid="+threadid+"&forumid="+forumid+"\";");
    out.println("</script>");
  }
%>

