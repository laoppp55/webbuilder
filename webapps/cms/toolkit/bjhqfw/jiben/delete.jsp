<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.IJiBenManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.jiben.JiBenPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%

    int id = ParamUtil.getIntParameter(request,"id",-1);
    IJiBenManager jMgr = JiBenPeer.getInstance();
    jMgr.delJiBen(id);
    response.sendRedirect("index.jsp");

%>
