<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.po.Template" %>
<%@ page import="com.bizwink.service.TemplateService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    List<Template> templateList = new ArrayList<>();
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        TemplateService templateService = (TemplateService)appContext.getBean("templateService");
        templateList = templateService.getTemplatesByColumnid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(columnID));
    }

    Tree colTree = null;
    if (samsiteid == 0)
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    else
        colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
    sitename = StringUtil.replace(sitename,"_",".");
    String cname = colTree.getChineseDirForArticle(colTree,columnID,sitename);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    Map data = new HashMap<>();
    data.put("COLUMNNAME",cname);
    data.put("DATA",templateList);
    data.put("PUBLISHRIGHT",SecurityCheck.hasPermission(authToken, 6));              //用户是否有发布权限
    data.put("MODELRIGHT",SecurityCheck.hasPermission(authToken, 2));                //用户是否有模板管理权限
    data.put("ADMINRIGHT",SecurityCheck.hasPermission(authToken, 54));               //用户是否有站点管理员权限
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
