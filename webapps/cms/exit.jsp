<%@ page import="com.bizwink.cms.util.ISequenceManager" %>
<%@ page import="com.bizwink.cms.util.SequencePeer" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.security.AuthPeer" %>
<%@ page import="com.bizwink.cms.security.IAuthManager" %>
<%@ page  contentType="text/html;charset=gbk"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    } else {
        String userid =null;
        int siteid = authToken.getSiteID();
        userid = authToken.getUserID();

        //获取用户端的IP地址
        String user_ip = null;
        if (request.getHeader("x-forwarded-for") == null) {
            user_ip = request.getRemoteAddr();
        } else {
            user_ip = request.getHeader("x-forwarded-for");
        }

        session.removeAttribute("CmsAdmin");
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        int id = sequenceMgr.getLSH_Num();
        IAuthManager authMgr = AuthPeer.getInstance();
        authMgr.removeUserLoginInfo(siteid,userid,user_ip);
    }

    //response.sendRedirect("/webbuilder/register/index.jsp");
    response.sendRedirect("/webbuilder/index.jsp");
%>


