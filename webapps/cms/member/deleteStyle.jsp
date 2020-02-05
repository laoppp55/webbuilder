<%@ page import="java.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.util.JSON" %>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null) {
    response.sendRedirect( "../login.jsp" );
    return;
  }

  //读出所有文章列表样式文件
  String username = authToken.getUserID();
  int type = ParamUtil.getIntParameter(request, "type", 0);
  IViewFileManager fileMgr = viewFilePeer.getInstance();
  int errcode = 0;

  if (type > 0 && username.equals("admin")) {
    int styleid = ParamUtil.getIntParameter(request, "styleid", 0);
    errcode = fileMgr.delete(styleid);
  }

  String jsondata = "{\"result\":" + errcode + "}";
  JSON.setPrintWriter(response,jsondata,"utf-8");
%>