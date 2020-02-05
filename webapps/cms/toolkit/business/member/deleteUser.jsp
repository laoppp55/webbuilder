<%@page import="com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Users.*" contentType="text/html;charset=gbk"
%>
<%@ include file="../../../include/auth.jsp"%>
<%
  int siteid = authToken.getSiteID();
  int userid = ParamUtil.getIntParameter(request, "userid",-1);
  IBUserManager buserMgr = buserPeer.getInstance();
  if(userid!=-1) buserMgr.delUser(userid,siteid);
  response.sendRedirect("index2.jsp");
%>