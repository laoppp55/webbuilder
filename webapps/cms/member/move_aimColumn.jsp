<%@page import="java.util.*,
                java.io.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.extendAttr.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.util.*"
                contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String columnlist = ParamUtil.getParameter(request, "columnlist");
  String columnIDlist = ParamUtil.getParameter(request, "columnIDlist");
  String str = ParamUtil.getParameter(request,"str");

  String[] columnsName = null;
  String[] columnsID = null;

  int columnID = ParamUtil.getIntParameter(request,"column",0);
  String rootPath = request.getRealPath("/");
  String siteName = authToken.getSitename();
%>
<html>
<head>
<link rel=stylesheet type=text/css href="../style/global.css">
<SCRIPT LANGUAGE=JavaScript>
function delItem()
{
  var select = false;
  for (var i=0; i<orgForm.selectedColumn.length; i++)
  {
    if (orgForm.selectedColumn[i].selected)
    {
      select = true;
      break;
    }
  }
  if (select)
  {
    for (var i=0; i<orgForm.selectedColumn.length; i++)
    {
      if (orgForm.selectedColumn[i].selected)
        orgForm.selectedColumn[i] = null;
    }
  }
  else
  {
    alert("请选择栏目！");
  }
}

function check(){
  var aimcolumn;
  if (orgForm.selectedColumn.length == 1 )
  {
    aimcolumn = orgForm.selectedColumn.options[0].value;
    orgForm.action="move.jsp?aimcolumn="+aimcolumn;
    orgForm.submit();
    return true;
  }else if(orgForm.selectedColumn.length > 1){
    alert("请选择一个目的栏目！");
    return false;
  }else{
    alert("没选择目的栏目，请选择一个目的栏目！");
    return false;
  }
  return ture;
}
</SCRIPT>
</head>
<body bgcolor="#CCCCCC">
<br>
<form name="orgForm" method="post" action="move.jsp" onSubmit="javascript:return check();">
<input type="hidden" name="columnIDlist" value="<%=columnIDlist%>">
<table>
<tr>
  <td>您要移动的栏目是：<%=columnlist%></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
  <td>    请选择目的栏目：
          <select name="selectedColumn" style="width:200" size="2" language="javascript">
          <%if (str != null){
          for (int i=0; i<columnsID.length; i++){
          if (!columnsName[i].equals("*")){%>
          <option value="<%=columnsID[i]%>"><%=StringUtil.gb2iso4View(columnsName[i])%></option>
          <%}}}%>
          </select>
          <input type="button" name="delete" value="删除" onclick="delItem();">
  </td>
</tr>
</table><br><br>
<table align="center">
  <tr>
    <td>
      <input type="submit" value="移 动">&nbsp;&nbsp;<input type="button" value="取 消" name="Reset" onclick="parent.window.close()">
    </td>
  </tr>
</table>
</form>
</body>
</html>