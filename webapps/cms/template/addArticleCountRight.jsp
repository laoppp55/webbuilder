<%@page import="java.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.security.*"
                contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.cms.server.CmsServer" %>

<%
  request.setCharacterEncoding("utf-8");
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String str = ParamUtil.getParameter(request, "str");
  String columnIDs = "";
  String columns = "";
  String powerrange1 = "";
  String powerrange2 = "";
  String timerange1 = "";
  String timerange2 = "";
  String orderrange1 = "";
  String orderrange2 = "";

  if (str != null && str.trim().length() > 0)
  {
    str = StringUtil.replace(str, "[", "<");
    str = StringUtil.replace(str, "]", ">");
    System.out.println(str);
    XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + str);
    columnIDs = properties.getProperty(properties.getName().concat(".COLUMNIDS"));
    columns = properties.getProperty(properties.getName().concat(".COLUMNS"));
    columns = StringUtil.gb2isoindb(columns);
    String time_range = properties.getProperty(properties.getName().concat(".TIME_RANGE"));
    String power_range = properties.getProperty(properties.getName().concat(".POWER_RANGE"));
    String order_range = properties.getProperty(properties.getName().concat(".ORDER_RANGE"));

    if (time_range!= null && time_range.trim().length() > 0)
    {
      timerange1 = time_range.substring(0, time_range.indexOf(","));
      timerange2 = time_range.substring(time_range.indexOf(",") + 1);
    }
    if (power_range!= null && power_range.trim().length() > 0)
    {
      powerrange1 = power_range.substring(0, power_range.indexOf(","));
      powerrange2 = power_range.substring(power_range.indexOf(",") + 1);
    }
    if (order_range!= null && order_range.trim().length() > 0)
    {
      orderrange1 = order_range.substring(0, order_range.indexOf(","));
      orderrange2 = order_range.substring(order_range.indexOf(",") + 1);
    }
  }
%>

<html>
<head>
<title></title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css">
<script language="javascript" src="../js/setday.js"></script>
<script language="javascript" src="../js/mark.js"></script>
</head>

<body bgcolor="#CCCCCC">
<form name="markForm">
<table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
  <tr>
    <td>
      <br><b><font color="#0000FF">选取范围：</font></b>
      <table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="99%">
        <tr>
          <td width="100%">
            <table border="0" width="100%" cellspacing=0 cellpadding=0>
              <tr height=35>
                <td width="100%">
                  时间<input class=tine name=time1 size=9 onfocus="setday(this)" value="<%=timerange1%>">-<input class=tine name=time2 size=9 onfocus="setday(this)" value="<%=timerange2%>">
                  权重<input class=tine name=power1 size=5 value="<%=powerrange1%>">-<input class=tine name=power2 size=5 value="<%=powerrange2%>">
                  序号<input class=tine name=num1 size=5 value="<%=orderrange1%>">-<input class=tine name=num2 size=5 value="<%=orderrange2%>">
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br><b><font color="#0000FF">选取栏目：</font></b>
      <table border=1 bordercolordark="#ffffec" bordercolorlight="#5e5e00" cellpadding=0 cellspacing=0 width="99%">
        <tr>
          <td>
            <table border="0" width="100%" cellspacing=2 cellpadding=2>
              <tr>
                <td width="73%">
                  <select id="selectedColumn" name="selectedColumn" style="width:280px" size="8" language="javascript" onDblClick="defineAttr();" class=tine>
                  <%
                    if (str != null && str.trim().length() > 0)
                    {
                      if (columnIDs != null && columnIDs.length() > 0)
                        columnIDs = columnIDs.substring(1, columnIDs.length()-1);
                      String[] cnames = columns.split(",");
                      String[] cids = columnIDs.split(",");
                      for (int i=0; i<cids.length; i++) {
                        if (!cnames[i].equals("*")) {
                  %>
                  <option value="<%=cids[i]%>"><%=cnames[i]%></option>
                  <%}}}%>
                  </select>
                </td>
                <td width="27%"><input type="button" name="delete" value="删除" onclick="delItem();" style="height:20px;width:50px;font-size:9pt"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td align="center" height=50>
      <input type="button" value="  确定  " onclick="createArticleCountMark(0);">&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" value="  取消  " onClick="parent.window.close();">
    </td>
  </tr>
</table>
</form>
</body>
</html>