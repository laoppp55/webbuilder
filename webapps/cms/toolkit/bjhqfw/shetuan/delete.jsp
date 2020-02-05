<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.shetuan.ISheTuanManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.shetuan.SheTuanPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%

    int id = ParamUtil.getIntParameter(request,"id",-1);
    ISheTuanManager sMgr = SheTuanPeer.getInstance();
    sMgr.delShetuan(id);
    response.sendRedirect("index.jsp");

%>