<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.heaton.bot.startSpider" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.server.CmsServer" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int run = ParamUtil.getIntParameter(request, "run", -1);

    if (run != -1) {
        IBasic_AttributesManager baMgr = Basic_AttributesPeer.getInstance();
        if(run != 2)
            baMgr.updateSystemRun(run);
        else
            baMgr.updateSystemRun(1);

        if((run == 1)||(run == 2)){
            startSpider sp = new startSpider("");
            //sp.startRun();
            sp.start();
        }
    }
    if(run == 2)
        response.sendRedirect("autoSpider.jsp?m=1");
    else
        response.sendRedirect("autoSpider.jsp");
%>