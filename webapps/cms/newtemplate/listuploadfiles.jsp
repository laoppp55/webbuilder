<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.systmeTemplate.*,
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

	int columnID = ParamUtil.getIntParameter(request,"column",0);
        int templateColumnID = ParamUtil.getIntParameter(request,"templateCID",0);
	int start = ParamUtil.getIntParameter(request,"start",0);
	int range = ParamUtil.getIntParameter(request,"range",20);
	String msg = ParamUtil.getParameter(request,"msg");

  	ISystemTemplateManager systemtemplateManager = systemTemplatePeer.getInstance();
  	SystemTemplate systemtemplate = systemtemplateManager.getSystemTemplateType(templateColumnID);
  	String CName = "";
  	if(systemtemplate.getCName() != null){
	  	CName = StringUtil.gb2iso4View(systemtemplate.getCName());
  	}
  	String dirname = systemtemplate.getDirName();

	int total = systemtemplateManager.getSystemTemplateNum(templateColumnID);
	List systemTemplateList = systemtemplateManager.getSystemTemplates(templateColumnID, start, range);
	int systemTemplateCount = systemTemplateList.size();
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language=javascript>
function viewTemplate(id)
{
    win = window.open("../systemtemplates/preview.jsp?dirname=<%=dirname%>&systid=" + id,"sysmodel","scrollbars=yes,status=yes,menubar=yes,resizable=yes,toolbar=yes");
}
</script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
	String[][] titlebars = {
		{ "文件上传管理", "index.jsp" },
		{ CName, "" }
	};
	String[][] operations = {};
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
	if (msg != null)	out.println("<span class=cur>" + msg + "</span>");

	if (systemTemplateCount > 0)
	{
		out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
		out.println("<tr><td width=50% align=left class=line>");
		if (start - range >= 0)
			out.println("<a href=listuploadfiles.jsp?column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
		out.println("</td><td width=50% align=right class=line>");
		if (start + range < total)
		{
			int remain = ((start+range-total)<range)?(total-start-range):range;
			out.println(remain+"<a href=listuploadfiles.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
		}
		out.println("</td></tr></table>");
	}
%>
<form action="selectSystemModel.jsp" name="selectModel" action="post">
          <input type=hidden name=count value=<%=systemTemplateCount%>>
          <input type=hidden name=column value=<%=columnID%>>
          <input type=hidden name=templateCID value=<%=templateColumnID%>>
          <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
          <tr class=itm bgcolor="#dddddd">
               <td align=center width="36%">标题</td>
               <td align=center width="20%">发布时间</td>
               <td align=center width="10%">排序</td>
               <td align=center width="10%">编辑</td>
               <td align=center width="12%">预览</td>
               <td align=center width="12%">选择</td>
          </tr>
<%
	for (int i=0; i<systemTemplateCount; i++)
	{
		systemtemplate = (SystemTemplate)systemTemplateList.get(i);

		int systemtemplateID = systemtemplate.getID();
		String maintitle = StringUtil.gb2iso4View(systemtemplate.getCName());
		String editor = StringUtil.gb2iso4View(systemtemplate.getEditor());
		String createdate = systemtemplate.getCreateDate().toString().substring(0,16);
		int sortId = systemtemplate.getOrderID();
		String bgcolor = (i%2==0)?"#ffffcc":"#eeeeee";
%>
  		<tr bgcolor="<%=bgcolor%>" class=itm height=20>
    		<td>&nbsp;&nbsp;<%=maintitle%></td>
    		<td align=center><%=createdate%></td>
    		<td align=center><%=sortId%></td>
    		<td align=center><%=editor%></td>
                <td align=center><a href=javascript:viewTemplate(<%=systemtemplateID%>)><img src="../images/preview.gif" border=0></a></td>
    		<td align=center>选中<input type=checkbox name=selected<%=i%>  value=<%=systemtemplateID%> ></td>
  		</tr>
<%}%>
                </table>
                <br>
                <table border=0 cellPadding=0 cellSpacing=0 width="80%">
                  <tr>
                    <td align=center><input type="submit" name="selectModel" value="确定"></td>
                    <td align=center><input type="button" name="cancel" value="返回" onclick="javascript:window.parent.close()"></td>
                  </tr>
                </table>
</form>
</BODY>
</html>
