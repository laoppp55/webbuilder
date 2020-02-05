<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../../include/auth.jsp"%>
<%
    IOrderManager orderMgr = orderPeer.getInstance();
    orderMgr.updteOldOrderPayStatus();
%>
<html>
<head>
    <title></title>
</head>
<body>
OK
</body>
</html>
