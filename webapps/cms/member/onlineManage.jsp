<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
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

  IRegisterManager regMgr = RegisterPeer.getInstance();
  List siteList = regMgr.getNotBindSite();
  int siteCount = siteList.size();
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language="javascript">
function edit_window(param)
{
  window.open("editSite.jsp?"+param,"","top=0,left=0,width=500,height=640,resizable=no,status=no,toolbar=no,menubar=no,location=no");
}
</script>
</head>
<body>
<%
  String[][] titlebars = {
    { "", "../main.jsp" },
    { "在线申请管理", "" }
  };
  String [][] operations = {
    {"站点管理","siteManage.jsp"}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<font class=line>站点列表</font>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=100%>
<tr bgcolor="#eeeeee" class=tine>
<td width="18%" align=center class="listHeader"><b>站点域名</b></td>
<td width="30%" align=center>企业名称</td>
<td width="13%" align=center>图像存储</td>
<td width="15%" align=center>繁体</td>
<td width="8%" align=center>修改</td>
<td width="8%" align=center>删除</td>
<td width="8%" align=center>激活</td>
</tr>
<%
  for (int i=0; i<siteCount; i++)
  {
    Register register = (Register)siteList.get(i);

    int SiteID = register.getSiteID();
    String SiteName = register.getSiteName();
    int imgflag = register.getImagesDir();
    int tcflag = register.getTCFlag();
    String CorpName = register.getCorpName();
    CorpName = StringUtil.gb2iso4View(CorpName);
%>
<tr bgcolor="#ffffff" class=line>
<td align=left>&nbsp;&nbsp;<b><%=SiteName%></b></td>
<td align=left>&nbsp;&nbsp;<b><%=(CorpName==null)?"":CorpName%></b></td>
<td align=center><%if(imgflag==0){%>根目录Images下<%}else if(imgflag==1){%>各栏目Images下<%}else{%>&nbsp;<%}%></td>
<td align=center><%if(tcflag==0){%>有<%}else if(tcflag==1){%>无<%}else{%>&nbsp;<%}%></td-->
<td align="center"><input type="radio" name="action" onclick='edit_window("siteid=<%=SiteID%>")'></td>
<td align="center"><input type="radio" name="action" onclick="location.href='removeSite.jsp?type=3&siteid=<%=SiteID%>&dname=<%=SiteName%>';"></td>
<td align="center"><input type="radio" name="action" onclick="location.href='pauseSite.jsp?type=3&siteid=<%=SiteID%>&dname=<%=SiteName%>';"></td>
</tr>
<% }%>
</body>
</table>
</html>