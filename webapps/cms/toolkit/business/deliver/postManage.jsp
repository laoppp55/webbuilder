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
  boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
  if (doCreate)
  {
    List feeList = new ArrayList();
    int count = ParamUtil.getIntParameter(request, "count", 0);
    for (int i=0; i<count; i++)
    {
      int feeID = ParamUtil.getIntParameter(request, "fee"+i, 0);
      int zoneID = ParamUtil.getIntParameter(request, "zone"+i, 0);
      float pfee = ParamUtil.getFloatParameter(request, "pfee"+i, 0);
      float kfee = ParamUtil.getFloatParameter(request, "kfee"+i, 0);
      float ufee = ParamUtil.getFloatParameter(request, "ufee"+i, 0);
      float ofee = ParamUtil.getFloatParameter(request, "ofee"+i, 0);

      Deliver deliver = new Deliver();
      deliver.setFeeID(feeID);
      deliver.setZoneID(zoneID);
      deliver.setPFee(pfee);
      deliver.setKFee(kfee);
      deliver.setUFee(ufee);
      deliver.setOFee(ofee);
      deliver.setProvID(provID);
      feeList.add(deliver);
    }
    deliverMgr.createFee(feeList);
    response.sendRedirect("postManage.jsp?ID="+provID);
  }

  Deliver deliver = new Deliver();
  deliver.setCityType(1);
  List provList = deliverMgr.getCityList(deliver);

  List feeList = new ArrayList();
  if (provID > 0)
  {
    deliver.setProvID(provID);
    feeList = deliverMgr.getCityFeeList(deliver);
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
    { "邮政包裹", "" }
  };
  String[][] operations = {
    {"<a href=provManage.jsp>省市管理</a>", ""},
    {"<a href=emsManage.jsp>快递公司</a>", ""}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>

<body>
<p align=center style="font-size:9pt"><b>邮政包裹资费管理</b></p>
<table cellSpacing=1 cellPadding=4 width="80%" align=center bgColor=#000000 border=0 align="center">
  <tr bgColor=#ffffff>
    <td>
<%
  for (int i=0; i<provList.size(); i++)
  {
    deliver = (Deliver)provList.get(i);
    int ID = deliver.getProvID();
    String provName = StringUtil.gb2iso4View(deliver.getProvName());
    out.println("<a href=postManage.jsp?ID=" + ID + ">" + provName + "</a>&nbsp;");
  }
%>
    </td>
  </tr>
</table>
<br>
<form action="postManage.jsp" method="POST">
<input type=hidden name=doCreate value=true>
<input type=hidden name=ID value="<%=provID%>">
<table cellSpacing=1 cellPadding=4 width="80%" align=center bgColor=#000000 border=0 align="center">
  <tr bgColor=#ffffff>
    <td align="center" width="20%" height="18">县市名称</td>
    <td align="center" width="20%" height="18">普通包裹</td>
    <td align="center" width="20%" height="18">快递包裹</td>
    <td align="center" width="20%" height="18">每500克(5000克内)</td>
    <td align="center" width="20%" height="18">每500克(5000克外)</td>
  </tr>
<%
  int j = 0;
  for (int i=0; i<feeList.size(); i++)
  {
    deliver = (Deliver)feeList.get(i);
    String cityName = deliver.getCityName();
    if (cityName != null && cityName.length() > 0)
    {
      out.println("<tr bgColor=#eeeeee height=20><td colspan=5><b>" + StringUtil.gb2iso4View(cityName) + "</b></td></tr>");
    }
    else
    {
      int zoneID = deliver.getZoneID();
      int feeID = deliver.getFeeID();
      String zoneName = StringUtil.gb2iso4View(deliver.getZoneName());

      out.println("<tr bgColor=#ffffff height=20>");
      out.println("<td><input type=hidden name='fee"+j+"' value='"+feeID+"'><input type=hidden name='zone"+j+"' value='"+zoneID+"'>"+zoneName+"</td>");
      out.println("<td><input name='pfee"+j+"' size=12 value='"+deliver.getPFee()+"'></td>");
      out.println("<td><input name='kfee"+j+"' size=12 value='"+deliver.getKFee()+"'></td>");
      out.println("<td><input name='ufee"+j+"' size=12 value='"+deliver.getUFee()+"'></td>");
      out.println("<td><input name='ofee"+j+"' size=12 value='"+deliver.getOFee()+"'></td>");
      out.println("</tr>");
      j++;
    }
  }
%>
<input type=hidden name="count" value="<%=j%>">
</table>
<p align=center><input type=submit value="  确定  ">&nbsp;&nbsp;<input type=reset value="  重置  "></p>
</form>

</BODY>
</html>