<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="org.json.JSONArray" %>

<%
  request.setCharacterEncoding("utf-8");
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null) {
    response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
    return;
  }
  if (!SecurityCheck.hasPermission(authToken, 54)) {
    response.sendRedirect("../error.jsp?message=无管理用户的权限");
    return;
  }

  int siteID = authToken.getSiteID();
  String userid = ParamUtil.getParameter(request,"userid");
  int rightID = ParamUtil.getIntParameter(request,"rightID",0);
  boolean doGrant = ParamUtil.getBooleanParameter(request,"doGrant");
  IRightsManager rightsManager = RightsPeer.getInstance();
  IColumnUserManager columnUserMgr = ColumnUserPeer.getInstance();
  int errcode = 1;

  if (doGrant) {
    List columnList = null;
    String grantinfo  = URLDecoder.decode(ParamUtil.getParameter(request, "infos"),"utf-8");
    JSONArray jsonArray = new JSONArray("[" + grantinfo + "]");
    for(int ii=0; ii<jsonArray.length(); ii++) {
      int rightid = (int)jsonArray.getJSONObject(ii).get("rightid");
      JSONArray colArray = jsonArray.getJSONObject(ii).getJSONArray("columns");
      columnList = new ArrayList();
      for(int jj=0; jj<colArray.length(); jj++) {
        columnList.add(colArray.getJSONObject(jj).get("columnid"));
      }
      errcode = rightsManager.grantToColumns(userid,rightid,columnList,siteID);                  //定义某个权限的栏目树结构
    }

    Map data = new HashMap();
    data.put("ERRCODE", errcode);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response, jsondata, "utf-8");
  } else {
    List columnList = new ArrayList();
    if (rightID > 0) columnList = columnUserMgr.getUserColsFromTBL_Members_Rights(userid, rightID);
    Map data = new HashMap();
    data.put("DOGRANT", doGrant);
    data.put("COLUMNS", columnList);
    data.put("RIGHTID", rightID);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response, jsondata, "utf-8");
  }
%>