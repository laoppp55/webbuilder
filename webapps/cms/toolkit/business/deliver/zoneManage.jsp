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

  int cityID = ParamUtil.getIntParameter(request, "cityID", 0);
  IDeliverManager deliverMgr = DeliverPeer.getInstance();
  Deliver deliver = new Deliver();
  deliver.setCityType(3);
  deliver.setCityID(cityID);

  String act = ParamUtil.getParameter(request, "act");
  if (act == null)  act = "";
  if (act.equals("doCreate"))
  {
    String zoneName = ParamUtil.getParameter(request, "zoneName");
    if (zoneName != null && zoneName.length() > 0)
    {
      deliver.setZoneName(zoneName);
      deliverMgr.create(deliver);
    }
  }
  else if (act.equals("doUpdate"))
  {
    int zoneID = ParamUtil.getIntParameter(request, "ID", 0);
    String zoneName = ParamUtil.getParameter(request, "zoneName");
    if (zoneName != null && zoneName.length() > 0)
    {
      int orderID = ParamUtil.getIntParameter(request, "orderID", 0);
      deliver.setZoneID(zoneID);
      deliver.setZoneName(zoneName);
      deliver.setOrderID(orderID);
      deliverMgr.update(deliver);
    }
  }
  else if (act.equals("doDelete"))
  {
    int zoneID = ParamUtil.getIntParameter(request, "ID", 0);
    deliver.setZoneID(zoneID);
    deliverMgr.delete(deliver);
  }
  if (act.length() > 0)
  {
    response.sendRedirect("zoneManage.jsp?cityID="+cityID);
  }

  List list = deliverMgr.getCityList(deliver);
%>

<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language=javascript>
function Delete(zoneID)
{
  var ret = confirm("真的要删除？");
  if (ret)
    window.location = "zoneManage.jsp?ID="+zoneID+"&act=doDelete&cityID=<%=cityID%>";
}

function Update(zoneID)
{
  var zoneName = document.all("zone" + zoneID).value;
  var orderID = document.all("order" + zoneID).value;
  window.location = "zoneManage.jsp?ID="+zoneID+"&cityID=<%=cityID%>&act=doUpdate&zoneName="+zoneName+"&orderID="+orderID;
}
</script>
</head>
<%
  String[][] titlebars = {
    { "送货方式管理", "" },
    { "省市管理", "" }
  };
  String[][] operations = {
    {"<a href=postManage.jsp>邮政包裹</a>", ""},
    {"<a href=emsManage.jsp>快递公司</a>", ""}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<body>
<p align=center style="font-size:9pt"><b>县市管理</b></p>
<TABLE cellSpacing=1 cellPadding=0 width="70%" align=center bgColor=#000000 border=0 align="center">
    <tr bgColor=#ffffff>
      <td align="center" width="20%" height="18">县市ID</td>
      <td align="center" width="30%" height="18">县市名称</td>
      <td align="center" width="20%" height="18">序号</td>
      <td align="center" width="30%" height="18">操作</td>
    </tr>
<%
  for (int i=0; i<list.size(); i++) {
    deliver = (Deliver)list.get(i);
    String color = (i % 2 == 0) ? "eeeeee" : "ffffff";
    int ID = deliver.getZoneID();
    int orderID = deliver.getOrderID();
    String zoneName = StringUtil.gb2iso4View(deliver.getZoneName());
%>
    <tr bgColor=#<%=color%> height="26">
      <td align="center"><%=ID%></td>
      <td align="center"><input type=text name="zone<%=ID%>" size=16 value="<%=zoneName%>"></td>
      <td align="center"><input type=text name="order<%=ID%>" size=16 value="<%=orderID%>"></td>
      <td align="center"><a href="javascript:Update(<%=ID%>);">修改</a>&nbsp;&nbsp;&nbsp;<a href="javascript:Delete(<%=ID%>);">删除</a></td>
    </tr>
<%}%>
    <tr bgColor=#ffffff>
      <td colspan="5" height="60" align="center">
      <form action="zoneManage.jsp" method="Post" name=createForm>
      <input type=hidden name=act value=doCreate>
      <input type=hidden name=cityID value=<%=cityID%>>
      <input type=text name=zoneName size=20>&nbsp;&nbsp;<input type=submit value="  添加  ">
      &nbsp;&nbsp;&nbsp;&nbsp;<a href="provManage.jsp">返回</a>
      </form>
      </td>
    </tr>
</table>
</BODY>
</html>