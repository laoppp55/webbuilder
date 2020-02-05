<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    int basicId = ParamUtil.getIntParameter(request, "basicId", -1);
    IMatchUrl_SpecialCodeManager iMuScMgr = MatchUrl_SpecialCodePeer.getInstance();
    MatchUrl_SpecialCode muSc = new MatchUrl_SpecialCode();
    System.out.println("basicId=" + basicId);
    if (flag == 1) {
        String st = ParamUtil.getParameter(request, "st");
        String et = ParamUtil.getParameter(request, "et");
        if (st != null && et != null) {
            muSc.setSt(st);
            muSc.setEt(et);
            muSc.setStartUrlId(basicId);
            iMuScMgr.addSpCode(siteid,muSc);
        }
        response.sendRedirect("characterCode.jsp?basicId=" + basicId);
    } else if (flag == 2) {
        String matchUrl = ParamUtil.getParameter(request, "matchUrl");
        if (matchUrl != null) {
            muSc.setMatchUrl(matchUrl);
            muSc.setStartUrlId(basicId);
            iMuScMgr.addMtUrl(siteid,muSc);
        }
        response.sendRedirect("matchUrl.jsp?basicId=" + basicId);
    } else {
        response.sendRedirect("index.jsp");
    }
%>