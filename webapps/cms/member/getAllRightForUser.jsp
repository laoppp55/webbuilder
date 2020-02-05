<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
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

  int siteid = authToken.getSiteID();
  String userid = ParamUtil.getParameter(request,"userid");
  IRightsManager rightsManager = RightsPeer.getInstance();
  IColumnManager columnManager = ColumnPeer.getInstance();

  List rightsList = rightsManager.getRights(userid);
  List<Map> data = new ArrayList<>();
  Map colAndRight = null;
  for(int ii=0; ii<rightsList.size(); ii++) {
    colAndRight = new HashMap();
    Rights right = (Rights)rightsList.get(ii);
    int rightid = right.getRightID();
    if (rightid<50) {       //必须是与栏目相关的权限执行如下操作，与栏目不相关的权限不执行如下操作
      colAndRight.put("rightid", rightid);
      List<Integer> colids = rightsManager.getColumnsByRightidAndUserid(siteid, userid, rightid);
      List<Column> columns = new ArrayList<>();
      for (int jj = 0; jj < colids.size(); jj++) {
        int columnid = colids.get(jj);
        if (columnid>0) {
          Column column = columnManager.getColumn(columnid);
          columns.add(column);
        }
      }
      colAndRight.put("columns", columns);
      data.add(colAndRight);
    }
  }

  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
  String jsondata = gson.toJson(data);
  System.out.println(jsondata);
  JSON.setPrintWriter(response, jsondata, "utf-8");
%>