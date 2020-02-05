<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }

    int id = ParamUtil.getIntParameter(request,"id",0);
    int markid = ParamUtil.getIntParameter(request,"markid",0);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        IWordManager wordMgr = LeaveWordPeer.getInstance();
        wordMgr.deleteWord(id);
    }
    response.sendRedirect("index1.jsp?markid="+markid);
    %>
