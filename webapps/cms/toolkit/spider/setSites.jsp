<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int basicId = ParamUtil.getIntParameter(request, "basicId", -1);
    int pageid = ParamUtil.getIntParameter(request,"pageid", -1);
    int stopflag = ParamUtil.getIntParameter(request, "sf", -1);

    IBasic_AttributesManager baMgr = Basic_AttributesPeer.getInstance();    
    if (stopflag != -1)
        baMgr.updateSpiderStopFlag(basicId, stopflag);

    response.sendRedirect("index.jsp?currentPage="+pageid);
%>
