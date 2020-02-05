<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.other.*" contentType="text/html;charset=gbk"
%>
<%
  long bookid = ParamUtil.getLongParameter(request,"bookid",0);
  String picnum = ParamUtil.getParameter(request,"picnum");


  IOtherManager otherMgr = OtherPeer.getInstance();

  otherMgr.delPic(bookid,picnum);

  response.sendRedirect("uploadfiles.jsp?bookid="+bookid);
%>