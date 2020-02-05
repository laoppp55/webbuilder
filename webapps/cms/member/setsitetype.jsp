<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
    int siteid = ParamUtil.getIntParameter(request, "site", 0);
    boolean doupdate = ParamUtil.getBooleanParameter(request,"doUpdate");
    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int doSearchFlag = ParamUtil.getIntParameter(request, "searchflag", 0);
    String userid = authToken.getUserID();
    SiteInfo siteInfo = siteMgr.getSiteInfo(siteid);
    String sitename = siteInfo.getDomainName();
    int sitetype = siteInfo.getValidFlag();
    int errcode = 0;
    if (doupdate == true) {
        sitetype = ParamUtil.getIntParameter(request,"stype",0);
        errcode = siteMgr.updatesitevalid(siteid,sitetype);
    }
%>
<html>
  <head>
      <title>设置站点类型</title>
      <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
      <link rel="stylesheet" type="text/css" href="../style/global.css">
      <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
      <script language="javascript">
          $(document).ready(function(){
              var errcode=<%=errcode%>;
              if (errcode === 1) {
                  window.opener.userInfoList(<%=range%>,<%=currentpage%>,<%=doSearchFlag%>);
                  window.close();
              }
          });
      </script>
  </head>
  <body>
  <form action="#" name="sitetype" method="post">
      <input type="hidden" name="doUpdate" value="true">
      <input type="hidden" name="sitename" value="<%=sitename%>">
      <input type="hidden" name="userid" value="<%=userid%>">
      <input type="hidden" name="siteid" value="<%=siteid%>">
      <input type="hidden" name="range" value="<%=range%>">
      <input type="hidden" name="pageno" value="<%=currentpage%>">
      <input type="hidden" name="searchflag" value="<%=doSearchFlag%>">
  <table cellpadding="0" border="0" width="100%">
      <tr>
          <td>请设置网站类型： </td>
          <td><%=sitename%></td>
      </tr>
      <tr><td colspan="2">
          <input type="radio" name="stype" id="stypeid0" value="1" <%=(sitetype == 1)?"checked":""%>>普通站点
          <input type="radio" name="stype" id="stypeid1" value="0" <%=(sitetype == 0)?"checked":""%>>样例站点
          <input type="radio" name="stype" id="stypeid2" value="2" <%=(sitetype == 2)?"checked":""%>>共享站点
      </td></tr>
      <tr><td><input type="submit" name="ok" value="提交"> </td><td><input type="button" name="cancel" value="返回" onclick="javascript:window.close();"> </td></tr>
  </table>
  </form>
  </body>
</html>