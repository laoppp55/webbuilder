<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp" );
    return;
  }
  if (authToken.getUserID().compareToIgnoreCase("admin") != 0)
  {
    request.setAttribute("message","无系统管理员的权限");
    response.sendRedirect("../index.jsp");
    return;
  }
System.out.println(authToken.getSiteID());
  int siteID = -1;
  int start = ParamUtil.getIntParameter(request, "start", 0);
  int range = ParamUtil.getIntParameter(request, "range", 20);

  IViewFileManager viewfileMgr = viewFilePeer.getInstance();
  List list = viewfileMgr.getViewFileList(siteID, start, range);
  int total = viewfileMgr.getViewFileNUM(siteID);
  int count = list.size();
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language="javascript">
function Create()
{
  window.open("addviewfile.jsp","","top=0,left=0,width=500,height=640,resizable=no,status=no,toolbar=no,menubar=no,location=no");
}

function Edit(id)
{
  window.open("editviewfile.jsp?id="+id,"","top=0,left=0,width=500,height=640,resizable=no,status=no,toolbar=no,menubar=no,location=no");
}

function Delete(id)
{

}
</script>
</head>
<%
  String[][] titlebars = {
    { "", "../main.jsp" },
    { "样式文件管理", "" }
  };

  String [][] operations = {
    {"<a href=javascript:Create();>创建样式文件</a>", "" }
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<font class=line>样式文件列表</font>
<%
  if (count > 0)
  {
    out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
    out.println("<tr><td width=50% align=left class=line>");
    if (start - range >= 0)
      out.println("<a href=index.jsp?range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
    out.println("</td><td width=50% align=right class=line>");
    if (start + range < total)
    {
      int remain = ((start+range-total)<range)?(total-start-range):range;
      out.println(remain+"<a href=index.jsp?range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
    }
    out.println("</td></tr></table>");
  }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=100%>
  <tr bgcolor="#eeeeee" class=tine>
    <td width="10%" align=center class="listHeader"><b>样式文件类型</b></td>
    <td width="17%" align=center>样式文件名称</td>
    <td width="23%" align=center>样式文件注释</td>
    <td width="15%" align=center>创建日期</td>
    <td width="15%" align=center>更新日期</td>
    <td width="10%" align=center>修改</td>
    <td width="10%" align=center>删除</td>
  </tr>
<%
  for (int i=0; i<count; i++)
  {
    ViewFile viewfile = (ViewFile)list.get(i);
    int type = viewfile.getType();

    String viewtypestr = "";
    if (type == 1)
      viewtypestr = "文章列表";
    else if (type == 2)
      viewtypestr = "导航条";
    else if (type == 3)
      viewtypestr = "相关文章";
    else if (type == 4)
      viewtypestr = "热点文章";
    else if (type == 5)
      viewtypestr = "已阅读标题";
    else if (type == 6)
      viewtypestr = "栏目列表";
    else if (type == 7)
      viewtypestr = "新文章样式";
    else if (type == 8)
      viewtypestr = "路径样式";
%>
  <tr bgcolor="#ffffff" class=line>
    <td>&nbsp;&nbsp;<b><%=viewtypestr%></b></td>
    <td align=center><%=viewfile.getChineseName()%></td>
    <td align=center><%=viewfile.getNotes()%></td>
    <td align=center><%=viewfile.getCreateDate().toString().substring(0,10)%></td>
    <td align=center><%=viewfile.getLastUpdated().toString().substring(0,10)%></td>
    <td align="center"><input type="radio" name="action" onclick="Edit(<%=viewfile.getID()%>);"></td>
    <td align="center"><input type="radio" name="action" onclick="Delete(<%=viewfile.getID()%>);"></td>
  </tr>
<%}%>
</table>

</body>
</html>