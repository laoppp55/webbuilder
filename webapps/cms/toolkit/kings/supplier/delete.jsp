<%@page import="com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSuPeer,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    ISupplierSuManager supplierSuMgr = SupplierSuPeer.getInstance();
    supplierSuMgr.deleteSupplierList(id);
    response.sendRedirect("supplier.jsp");
%>