<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.program.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    IProgramManager programMgr = ProgramPeer.getInstance();
    List list = programMgr.getAllBriefOfPrograms();

    Map data = new HashMap<>();
    data.put("DATA",list);
    Gson gson = new GsonBuilder().serializeNulls().create();
    //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");

%>