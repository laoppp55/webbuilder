<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.xml.IFormManager" %>
<%@ page import="com.xml.FormPeer" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
     int id= ParamUtil.getIntParameter(request,"id",-1);
     String tablename=ParamUtil.getParameter(request,"tablename");
     IFormManager formpeer= FormPeer.getInstance();
     
     if(id>-1)
     {
         formpeer.deleteTableForm(id,tablename);
     }
%>