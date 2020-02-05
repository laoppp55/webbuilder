<%@page contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.userfunction.*" %>
<%
    int productid = ParamUtil.getIntParameter(request,"productid",0);
    IUserFunctionManager ufMgr = UserFunctionPeer.getInstance();
    String buf = ufMgr.genrateProductPageForStenders(productid);
    System.out.println(buf);
    out.write(buf);
%>
