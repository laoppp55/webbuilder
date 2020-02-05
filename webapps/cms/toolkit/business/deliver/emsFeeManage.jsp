<%@ page import="java.util.*,
                 com.bizwink.cms.business.deliver.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if( authToken == null )
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  IDeliverManager deliverMgr = DeliverPeer.getInstance();

  int provID = ParamUtil.getIntParameter(request, "ID", 0);
  int corpID = ParamUtil.getIntParameter(request, "corpID", 0);
  boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
  if (doCreate)
  {
    List feeList = new ArrayList();
    int count = ParamUtil.getIntParameter(request, "count", 0);
    for (int i=0; i<count; i++)
    {
      int feeID = ParamUtil.getIntParameter(request, "fee"+i, 0);
      int zoneID = ParamUtil.getIntParameter(request, "zone"+i, 0);
      float shoufee = ParamUtil.getFloatParameter(request, "shoufee"+i, 0);
      float xufee = ParamUtil.getFloatParameter(request, "xufee"+i, 0);
      float kg20fee = ParamUtil.getFloatParameter(request, "kg20fee"+i, 0);
      float kg50fee = ParamUtil.getFloatParameter(request, "kg50fee"+i, 0);
      float kg100fee = ParamUtil.getFloatParameter(request, "kg100fee"+i, 0);
      float kg300fee = ParamUtil.getFloatParameter(request, "kg300fee"+i, 0);

      Deliver deliver = new Deliver();
      deliver.setExpID(corpID);
      deliver.setFeeID(feeID);
      deliver.setZoneID(zoneID);
      deliver.setShouFee(shoufee);
      deliver.setXuFee(xufee);
      deliver.setKg20Fee(kg20fee);
      deliver.setKg50Fee(kg50fee);
      deliver.setKg100Fee(kg100fee);
      deliver.setKg300Fee(kg300fee);

      feeList.add(deliver);
    }
    deliverMgr.createExpFee(feeList);
    response.sendRedirect("emsFeeManage.jsp?ID="+provID+"&corpID="+corpID);
  }

  Deliver deliver = new Deliver();
  deliver.setCityType(1);
  List provList = deliverMgr.getCityList(deliver);

  List feeList = new ArrayList();
  if (provID > 0)
  {
    deliver.setProvID(provID);
    deliver.setExpID(corpID);
    feeList = deliverMgr.getExpFeeList(deliver);
  }
%>

<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
</head>
<%
  String[][] titlebars = {
    { "送货方式管理", "" },
    { "快递公司", "" }
  };
  String[][] operations = {
    {"<a href=provManage.jsp>省市管理</a>", ""},
    {"<a href=postManage.jsp>邮政包裹</a>", ""}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>

<body>
<p align=center style="font-size:9pt"><b>快递公司资费管理</b></p>
<table cellSpacing=1 cellPadding=4 width="80%" align=center bgColor=#000000 border=0 align="center">
  <tr bgColor=#ffffff>
    <td>
<%
  for (int i=0; i<provList.size(); i++)
  {
    deliver = (Deliver)provList.get(i);
    int ID = deliver.getProvID();
    String provName = StringUtil.gb2iso4View(deliver.getProvName());
    out.println("<a href=emsFeeManage.jsp?ID=" + ID + "&corpID=" + corpID + ">" + provName + "</a>&nbsp;");
  }
%>
    </td>
  </tr>
</table>
<br>
<form action="emsFeeManage.jsp" method="POST">
<input type=hidden name=doCreate value=true>
<input type=hidden name=ID value="<%=provID%>">
<input type=hidden name=corpID value="<%=corpID%>">

<table cellSpacing=1 cellPadding=4 width="80%" align=center bgColor=#000000 border=0 align="center">
  <tr bgColor=#ffffff>
    <td align="center" width="20%" height="18">县市名称</td>
    <td align="center" width="13%" height="18">首重费用</td>
    <td align="center" width="13%" height="18">续重费用</td>
    <td align="center" width="13%" height="18">20kg以上</td>
    <td align="center" width="13%" height="18">50kg以上</td>
    <td align="center" width="14%" height="18">100kg以上</td>
    <td align="center" width="14%" height="18">300kg以上</td>
  </tr>
<%
  int j = 0;
  for (int i=0; i<feeList.size(); i++)
  {
    deliver = (Deliver)feeList.get(i);
    String cityName = deliver.getCityName();
    if (cityName != null && cityName.length() > 0)
    {
      out.println("<tr bgColor=#eeeeee height=20><td colspan=7><b>" + StringUtil.gb2iso4View(cityName) + "</b></td></tr>");
    }
    else
    {
      int zoneID = deliver.getZoneID();
      int feeID = deliver.getFeeID();
      String zoneName = StringUtil.gb2iso4View(deliver.getZoneName());

      out.println("<tr bgColor=#ffffff height=20>");
      out.println("<td><input type=hidden name='fee"+j+"' value='"+feeID+"'><input type=hidden name='zone"+j+"' value='"+zoneID+"'>"+zoneName+"</td>");
      out.println("<td><input name='shoufee"+j+"' size=12 value='"+deliver.getShouFee()+"'></td>");
      out.println("<td><input name='xufee"+j+"' size=12 value='"+deliver.getXuFee()+"'></td>");
      out.println("<td><input name='kg20fee"+j+"' size=12 value='"+deliver.getKg20Fee()+"'></td>");
      out.println("<td><input name='kg50fee"+j+"' size=12 value='"+deliver.getKg50Fee()+"'></td>");
      out.println("<td><input name='kg100fee"+j+"' size=12 value='"+deliver.getKg100Fee()+"'></td>");
      out.println("<td><input name='kg300fee"+j+"' size=12 value='"+deliver.getKg300Fee()+"'></td>");
      out.println("</tr>");
      j++;
    }
  }
%>
<input type=hidden name="count" value="<%=j%>">
</table>
<p align=center style="font-size:9pt"><input type=submit value="  确定  ">&nbsp;&nbsp;<input type=reset value="  重置  ">&nbsp;<a href=emsManage.jsp>返回</a></p>
</form>

</BODY>
</html>