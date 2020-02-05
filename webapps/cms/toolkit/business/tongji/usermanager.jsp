<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.search.*"
                 contentType="text/html;charset=gbk"%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  int startrow            = ParamUtil.getIntParameter(request, "startrow", 0);
  int range               = ParamUtil.getIntParameter(request, "range", 20);
  String userid = authToken.getUserID();
  int siteid = 1;

  ISearchManager searchMgr = SearchPeer.getInstance();
  int zhuce = searchMgr.getTongJi(0);
  int fangwen = searchMgr.getTongJi(1);
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
<meta http-equiv="Pragma" content="no-cache">
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<%
      String[][] titlebars = {
              { "首页", "" },
              { "统计管理", "" }
          };

      String[][] operations = {
              { "访问注册量", "usermanager.jsp" },
              { "城市统计", "city.jsp" },
              { "职业统计", "occupation.jsp" },
              { "学历统计", "education.jsp" },
              { "阅读兴趣统计", "read.jsp" },
              { "收藏兴趣统计", "save.jsp" }
      };
%>
<%@ include file="../inc/titlebar2.jsp" %>
  <table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF" width="80%">
    <tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">访问注册量统计</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <tr  bgcolor="#FFFFFF">
                  <td width="20%" align="center">用户访问量</td>
                  <td width="20%" align="center"><%=fangwen%></td>
                </tr>
                <tr  bgcolor="#FFFFFF">
                  <td width="20%" align="center">用户注册量</td>
                  <td width="20%" align="center"><%=zhuce%></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</center>
</body>
</html>
