<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.modelManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String username = authToken.getUserID();
    IModelManager modelManager = ModelPeer.getInstance();

    int columnID = ParamUtil.getIntParameter(request, "column", 0);          //目的栏目
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int errcode = 0;

    if (doCreate) {
        int templateID = ParamUtil.getIntParameter(request, "template", 0);
        Model model = modelManager.getModel(templateID);
        String cname = model.getChineseName();
        if (cname != null) cname = StringUtil.gb2iso4View(cname);

        model.setColumnID(columnID);
        model.setCreatedate(new Timestamp(System.currentTimeMillis()));
        model.setLastupdated(new Timestamp(System.currentTimeMillis()));
        model.setEditor(username);
        model.setCreator(username);
        model.setLockstatus(0);
        model.setReferModelID(templateID);
        model.setChineseName(cname);
        model.setDefaultTemplate(0);

        errcode = modelManager.Create(model,siteID,samsiteid,0);
    }

    String jsonData = null;
    if (errcode == 1)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");
%>