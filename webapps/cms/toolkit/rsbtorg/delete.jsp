<%@page import="com.bizwink.webapps.register.*,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    IUregisterManager regMgr = UregisterPeer.getInstance();
    regMgr.deleteRsbtList(id);
    response.sendRedirect("index.jsp");
%>