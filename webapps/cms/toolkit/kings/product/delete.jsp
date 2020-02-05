<%@page import="com.bizwink.cms.kings.product.IProductSuManager,
                " contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.product.ProductSuPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    IProductSuManager productSuMgr = ProductSuPeer.getInstance();
    productSuMgr.deleteProductList(id);
    response.sendRedirect("product.jsp");
%>