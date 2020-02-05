<%@ page import="com.bjca.idm.datasynchronize.SynchronizeInfo" %>
<%@ page contentType="text/html; charset=GB2312"%>
<%
    System.out.println("123456");
    SynchronizeInfo synchronizeInfo=new SynchronizeInfo();
    synchronizeInfo.doGet(request,response);
%>