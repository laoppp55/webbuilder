<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.news.*,
                com.bizwink.cms.server.*,
                com.bizwink.cms.tree.*,
                com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                com.bizwink.cms.business.Other.*" contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int siteid = authToken.getSiteID();
   IOtherManager otherMgr = otherPeer.getInstance();
   int gh_id = ParamUtil.getIntParameter(request,"gh_id",-1);
   if(gh_id != -1) otherMgr.delGH(gh_id);
   response.sendRedirect("index.jsp");
%>