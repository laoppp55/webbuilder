<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.modelManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=utf-8"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request, "column", 0);

  IModelManager modelManager = ModelPeer.getInstance();
  IColumnManager columnManager = ColumnPeer.getInstance();
  Column column = columnManager.getColumn(columnID);
  String cname = StringUtil.gb2iso4View(column.getCName());

  List templateList = modelManager.getArticleModels(columnID);
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css">
<script language="javascript">
function Preview(templateID, isArticle)
{
  window.open("previewTemplate.jsp?column=<%=columnID%>&template="+templateID+"&isArticle="+isArticle,"PreviewTemplate","width=800,height=600,left=0,top=0,scrollbars");
}

function SelectModel(modelID)
{
    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
    if (isMSIE) {
        window.returnValue = modelID;
        window.close();
    }else{
        window.parent.opener.document.getElementById("modelID").value = modelID;
        top.close();
    }
}
</script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
<tr class=tine><td colspan=8>当前栏目：<%=cname%></td></tr>
<tr bgcolor=#dddddd class=tine>
<td align=center width='20%'>中文名称</td>
<td align=center width='14%'>英文名称</td>
<td align=center width='14%'>模板类型</td>
<td align=center width='14%'>修改时间</td>
<td align=center width='14%'>编辑</td>
<td align=center width='8%'>默认</td>
<td align=center width='8%'>预览</td>
<td align=center width='8%'>选择</td>
</tr>
<%
  for (int i=0; i<templateList.size(); i++)
  {
    Model template = (Model)templateList.get(i);

    int templateID = template.getID();
    String editor = template.getEditor();
    String lastupdated = template.getLastupdated().toString().substring(0,10);
    String templateName = template.getChineseName();
    if (templateName == null) templateName = "";
    templateName = StringUtil.gb2iso4View(templateName);

    String bgcolor = (i%2==0) ? "#EEC000" : "#FFCC00";
    out.println("<tr bgcolor=" + bgcolor + " height=22>");
    out.println("<td>&nbsp;" + templateName + "</td>");
    out.println("<td>&nbsp;" + template.getTemplateName() + "</td>");
    out.println("<td align=center>文章模板</td>");
    out.println("<td align=center>" + lastupdated + "</td>");
    out.println("<td>&nbsp;" + editor + "</td>");
    if (template.getDefaultTemplate() == 1)
      out.println("<td align=center><img src='../images/tasklist.gif' align=bottom border=0></td>");
    else
      out.println("<td align=center>&nbsp;</td>");
    out.println("<td align=center><a href=javascript:Preview("+templateID+","+template.getIsArticle()+");><img src=../images/preview.gif border=0></a></td>");
    out.println("<td align=center><a href=javascript:SelectModel("+templateID+");><img src=../images/edit.gif border=0></a></td></tr>");
  }
%>
</table>
</center>
</BODY>
</html>
