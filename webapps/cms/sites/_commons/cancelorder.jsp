<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@page contentType="text/html;charset=GBK"  %>
<%
    long orderid = ParamUtil.getLongParameter(request,"orderid",-1);
    IOrderManager oMgr = orderPeer.getInstance();
    oMgr.cancelOrder(orderid);
    String fromurl = request.getHeader("REFERER") ;
    response.sendRedirect(fromurl);
%>