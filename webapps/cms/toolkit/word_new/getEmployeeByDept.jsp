<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,"
          contentType="text/html;charset=gbk"
        %>
<%@ page import="java.util.List" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int deptid = ParamUtil.getIntParameter(request,"dept",0);
    int lwid = ParamUtil.getIntParameter(request,"lwid",0);
    IUserManager userMgr = UserPeer.getInstance();
    List users = userMgr.getUsersBYDepartment(String.valueOf(deptid),siteid,lwid,"留言板部门管理员");

    String users_str = "";
    for (int i=0; i<users.size(); i++) {
        User user = new User();
        user = (User)users.get(i);
        users_str = users_str + user.getNickName() + ":" + user.getUserID()+ ";";
    }
    if (users_str != null && users_str!="") {
        users_str = users_str.substring(0,users_str.length() - 1);
    }
    out.write(users_str);
%>
