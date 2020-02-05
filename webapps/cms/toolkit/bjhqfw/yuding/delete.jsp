<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.IYuDingManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDingPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    int id = ParamUtil.getIntParameter(request,"id",-1);
    IYuDingManager yMgr = YuDingPeer.getInstance();
    yMgr.delYuDing(id);
    response.sendRedirect("index.jsp");
%>