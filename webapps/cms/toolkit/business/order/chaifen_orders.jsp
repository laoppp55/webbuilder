<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*"
         contentType="text/html;charset=gbk" %>
<%@ include file="../../../include/auth.jsp"%>
<%
    long orderid = ParamUtil.getLongParameter(request, "orderid", 0);
    int siteid = authToken.getSiteID();

    IOrderManager orderMgr = orderPeer.getInstance();
    Order order = orderMgr.getAOrder(orderid);
    List suppliers = orderMgr.getSupplierNumByOrder(orderid);
    for (int i=0; i<suppliers.size(); i++) {
        supplier sp = new supplier();
        sp = (supplier)suppliers.get(i);
        orderMgr.createNewOrderBySupplier()        
    }
%>