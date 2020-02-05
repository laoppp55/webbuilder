<%@ page import="com.bizwink.cms.kings.deliverymaster.DeliveryMasterPeer" %>
<%@ page import="com.bizwink.cms.kings.deliverymaster.IDeliveryMasterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@page contentType="text/htnl;charset=GBK" %>
<%
    String id = ParamUtil.getParameter(request, "deliveryid");
    IDeliveryMasterManager delMgr = DeliveryMasterPeer.getInstance();
    delMgr.deleteDeliveryMasters(id);
    response.sendRedirect("deliverymaster.jsp");

%>