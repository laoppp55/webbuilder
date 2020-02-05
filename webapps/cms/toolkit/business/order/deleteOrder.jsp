<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.OrderException" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="../../../include/auth.jsp"%>
<%
    int siteid = authToken.getSiteID();
    long orderid = ParamUtil.getLongParameter(request, "orderid", -1);

    if (orderid != -1) {
        IOrderManager orderMgr = orderPeer.getInstance();
        try {
            orderMgr.deleteOrder(siteid,orderid);
        } catch (OrderException e) {
            e.printStackTrace();
        }
    }
    response.sendRedirect("index.jsp");
%>