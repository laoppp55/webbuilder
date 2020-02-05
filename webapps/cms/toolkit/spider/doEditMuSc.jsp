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

    String flag = ParamUtil.getParameter(request, "flag");
    int basicId = ParamUtil.getIntParameter(request, "basicId", -1);
    IMatchUrl_SpecialCodeManager iMuScMgr = MatchUrl_SpecialCodePeer.getInstance();
    if (flag.equals("sc")) {
        String st = ParamUtil.getParameter(request, "st");
        String et = ParamUtil.getParameter(request, "et");
        int sc_id = ParamUtil.getIntParameter(request, "sc_id", -1);
        if (st != null && et != null) {
            MatchUrl_SpecialCode sc = new MatchUrl_SpecialCode();
            sc.setEt(et);
            sc.setSt(st);
            sc.setSc_id(sc_id);
            iMuScMgr.updateSpCode(sc);
        }
        response.sendRedirect("characterCode.jsp?basicId=" + basicId);

    } else if (flag.equals("mu")) {
        String matchUrl = ParamUtil.getParameter(request, "matchUrl");
        int mu_id = ParamUtil.getIntParameter(request, "mu_id", -1);
        if (matchUrl != null) {
            MatchUrl_SpecialCode mu = new MatchUrl_SpecialCode();
            mu.setMatchUrl(matchUrl);
            mu.setMu_id(mu_id);
            iMuScMgr.updateMtUrl(mu);
        }
        response.sendRedirect("matchUrl.jsp?basicId=" + basicId);
    } else {
        response.sendRedirect("index.jsp");
    }
%>