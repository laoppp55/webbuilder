<%@page import="com.bizwink.cms.kings.changedetail.ChangeDetailPeer,
                " contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.changedetail.IChangeDetailManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
    String id = ParamUtil.getParameter(request, "changeid");
    System.out.println("aaa  = " + id);
    IChangeDetailManager pudMgr = ChangeDetailPeer.getInstance();
    pudMgr.deleteChangeDetail(id);
    response.sendRedirect("changedetail.jsp");
%>