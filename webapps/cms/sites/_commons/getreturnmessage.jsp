<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Order" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    long orderid = ParamUtil.getLongParameter(request,"orderid",0); //从支付接口返回参数中获得
    IOrderManager orderMgr = orderPeer.getInstance();
    if(orderid == 0){
        String orderids = (String)session.getAttribute("orderids");
        if(orderids != null && !orderids.equals("") && !orderids.equalsIgnoreCase("null"))
        orderid = Long.parseLong(orderids);
    }

    int code = orderMgr.updateOrderPayflag(orderid,1);

    response.sendRedirect("/");
%>