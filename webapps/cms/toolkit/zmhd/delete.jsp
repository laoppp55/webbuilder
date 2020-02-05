<%@page import="com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    IWsbsManager wsbsMgr = WsbsPeer.getInstance();
    wsbsMgr.deleteLetter(id);
    response.sendRedirect("index.jsp");
%>