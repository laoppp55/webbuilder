<%@ page import="com.bizwink.cms.modelManager.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    // get parameters
    int siteid    = authToken.getSiteID();
    int ID        = ParamUtil.getIntParameter(request,"template", 0);
    int columnID  = ParamUtil.getIntParameter(request,"column", 0);
    int modelType = ParamUtil.getIntParameter(request,"isArticle", 0);
    boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");
    int errcode = 0;
    if( doDelete) {
        try {
            IModelManager  modelManager= ModelPeer.getInstance();
            modelManager.Remove(ID,siteid);
        } catch( ModelException e ) {
            errcode = -1;
            System.out.println( e.getMessage() );
        }
    }

    String jsonData = null;
    if (errcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>
