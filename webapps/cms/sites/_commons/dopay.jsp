<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Order" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    long orderid = ParamUtil.getLongParameter(request,"orderid",0);
    IOrderManager orderMgr = orderPeer.getInstance();
    if(orderid == 0){
        String orderids = (String)session.getAttribute("orderids");
        orderid = Long.parseLong(orderids);
    }

    Order order = orderMgr.getAOrder(orderid);

    if(order != null){
        SendWay payway = orderMgr.getASendWayInfo(order.getPayWay());
        if(payway != null){
            String cname = payway.getCname() == null?"": StringUtil.gb2iso4View(payway.getCname());
            if(cname.equals("Ö§¸¶±¦")){
                response.sendRedirect("/_commons/zfb.html");
            }
            else {
                response.sendRedirect("/_commons/bankpay.html");
            }
        }
    }
%>