<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2010-7-20
  Time: 15:56:05
  To change this template use File | Settings | File Templates.gg
--%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="java.util.List" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
      if (authToken == null) {
          response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
          return;
      }

     int articleid = ParamUtil.getIntParameter(request,"articleid",0);
     int columnID = ParamUtil.getIntParameter(request,"columnid",0);
     int sortid = ParamUtil.getIntParameter(request,"sortid",0);
     String  notes=  ParamUtil.getParameter(request,"notes");

     List list = (List) session.getAttribute("turn_pic");
     if(list!=null){
         for(int i = 0 ; i < list.size(); i++){
             Turnpic tpic = (Turnpic)list.get(i);
             if( sortid == i){
                 tpic.setNotes(notes);
             }
         }
      session.setAttribute("turn_pic",list);
      response.sendRedirect(response.encodeRedirectURL("upload_turn_pic.jsp?column="  +  columnID+ "&articleid=" + articleid));
     }



%>
<html>
  <head><title>上传图片修改页面</title></head>
  <body></body>
</html>