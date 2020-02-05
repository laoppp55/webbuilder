<%@ page import="java.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.util.Map" %>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect( "../login.jsp" );
    return;
  }

  //读出所有文章列表样式文件
  int siteID = authToken.getSiteID();
  String username = authToken.getUserID();
  int type = ParamUtil.getIntParameter(request, "type", 0);
  IViewFileManager fileMgr = viewFilePeer.getInstance();

  List list = new ArrayList();
  if (type > 0 && username.equals("admin")) {
    list = fileMgr.getViewFileC(siteID, type);
  }

  Map result = new HashMap<>();
  result.put("DATA",list);
  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
  String jsondata = gson.toJson(result);
  System.out.println(jsondata);
  JSON.setPrintWriter(response,jsondata,"utf-8");
%>