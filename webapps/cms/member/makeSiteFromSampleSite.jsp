<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp" );
        return;
    }

    String userID = authToken.getUserID();
    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    String apppath = request.getRealPath("/");
    int samsiteid  = ParamUtil.getIntParameter(request, "samsiteid",0);
    //表示成功地从例子站点生成网站
    boolean retcode = false;

    if (samsiteid != 0) {
        IRegisterManager regMgr = RegisterPeer.getInstance();
        retcode = regMgr.copySamSite(samsiteid,siteid,userID,sitename,apppath);
    } else {
        retcode = true;
    }

    System.out.println("retcode=" + retcode);

    if (retcode == false) {
        response.sendRedirect( "/webbuilder/index1.jsp" );
    } else {
        response.sendRedirect( "/webbuilder/error.jsp" );
    }
%>
