<%@page import="com.bizwink.cms.kings.purchasemaster.IPurchaseMasterManager,
                " contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.purchasemaster.PurchaseMasterPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    String purchaseid = ParamUtil.getParameter(request, "purchaseid");
    System.out.println("purchaseid = " + purchaseid + "id" + id);
    IPurchaseMasterManager pumMgr = PurchaseMasterPeer.getInstance();
    pumMgr.deletePurchaseList(id,purchaseid);
    response.sendRedirect("purchasemaster.jsp");
%>