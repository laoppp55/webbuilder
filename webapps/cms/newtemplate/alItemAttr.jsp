<%@ page import="java.sql.*,com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%
  String itemText = ParamUtil.getParameter(request,"item");
  String cname = itemText;
  int p = 0;
  if ((p=itemText.indexOf("-getAllSubArticle")) > -1)
  {
    cname = itemText.substring(0, p);
  }
%>

<html>
<head>
<title>定义文章列表属性</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type="text/css" href="../style/global.css">
<SCRIPT LANGUAGE=JavaScript FOR=Ok EVENT=onclick>
var attrVal = "<%=itemText%>";
var val = 0;

//是否显示子栏目列表  0--显示  1--不显示
for (i=0; i<2; i++)
{
  if (subCList[i].checked)  val = subCList[i].value;
}
if (val == 0 && attrVal.indexOf("-getAllSubArticle") == -1)
  attrVal = attrVal + "-getAllSubArticle";
else if (val == 1 && attrVal.indexOf("-getAllSubArticle") > -1)
  attrVal = "<%=cname%>";

window.returnValue = attrVal;
window.close();
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript FOR=Cancel EVENT=onclick>
var attrVal = "<%=itemText%>";
window.returnValue = attrVal;
window.close();
</SCRIPT>
</head>

<BODY bgcolor="#cccccc">
<table width="100%" border="0" align="center">
  <tr height="20">
    <td>栏目名称：<%=cname%></td>
  </tr>
  <tr height="25">
    <td>是否获取所有子栏目的文章：<input type="radio" name="subCList" value="0">是&nbsp;&nbsp;<input type="radio" name="subCList" value="1">否</td>
  </tr>
  <tr height="30">
    <td align="center">
      <input type="button" name="Ok" value=" 确定 " class=tine>&nbsp;&nbsp;<input type="button" name="Cancel" value=" 取消 " class=tine>
    </td>
  </tr>
</table>

</BODY>
</html>

<SCRIPT LANGUAGE=JavaScript>
var attrVal = "<%=itemText%>";
if (attrVal.indexOf("-getAllSubArticle") > -1)
  subCList[0].checked = true;
else
  subCList[1].checked = true;
</SCRIPT>