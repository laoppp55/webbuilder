<%@page import="com.bizwink.cms.kings.purchasedetail.IPurchaseDetailManager,
                " contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.purchasedetail.PurchaseDetailPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    IPurchaseDetailManager pudMgr = PurchaseDetailPeer.getInstance();
    pudMgr.deletePurchaseDetailList(id);
    response.sendRedirect("purchasedetail.jsp");
%>