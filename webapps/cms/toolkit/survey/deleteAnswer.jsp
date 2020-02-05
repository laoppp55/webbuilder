<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page contentType="text/html;charset=GBK" %>

<%

    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int qid = ParamUtil.getIntParameter(request, "qid", -1);
    int sid = ParamUtil.getIntParameter(request, "sid", -1);
    int aid = ParamUtil.getIntParameter(request, "aid", -1);

    if (aid != -1) {
        IDefineManager defineMgr = DefinePeer.getInstance();

        try {
            defineMgr.deleteDefineAnswer(aid);

            response.sendRedirect("addAnswer.jsp?success=1&sid=" + sid + "&qid=" + qid);
            return;
        } catch (DefineException e) {
            e.printStackTrace();
        }
    } else {
        response.sendRedirect("addAnswer.jsp?success=2&sid=" + sid + "&qid=" + qid);
    }
%>