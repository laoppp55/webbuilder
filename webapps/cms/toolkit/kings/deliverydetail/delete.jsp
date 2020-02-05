<%@ page import="com.bizwink.cms.kings.deliverydetail.DeliveryDetailPeer" %>
<%@ page import="com.bizwink.cms.kings.deliverydetail.IDeliveryDetailManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String id = ParamUtil.getParameter(request, "deliveryid");
    IDeliveryDetailManager delsMgr = DeliveryDetailPeer.getInstance();
    delsMgr.deleteDeliveryDetail(id);
    response.sendRedirect("deliverydetails.jsp");
%>