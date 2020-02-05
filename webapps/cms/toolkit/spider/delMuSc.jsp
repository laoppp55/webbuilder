<%@ page import="com.bizwink.cms.util.*"%>
<%@ page import="com.bizwink.collectionmgr.*"%>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int sc_id= ParamUtil.getIntParameter(request,"sc_id",-1);
    int mu_id=ParamUtil.getIntParameter(request,"mu_id",-1);
    int basicId=ParamUtil.getIntParameter(request,"basicId",-1);
    System.out.println("**sc_id:"+sc_id+" **mu_id:"+mu_id+" basicId:"+basicId);
    IMatchUrl_SpecialCodeManager iMuScMgr= MatchUrl_SpecialCodePeer.getInstance();
    if(sc_id!=-1){
        iMuScMgr.delSpCode(sc_id);
        response.sendRedirect("characterCode.jsp?basicId="+basicId);
    }
    if(mu_id!=-1){
        iMuScMgr.delMtUrl(mu_id);
        response.sendRedirect("matchUrl.jsp?basicId="+basicId);
    }else{
        response.sendRedirect("index.jsp");
    }
%>