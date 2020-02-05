<%@page import="com.bizwink.move.*,
                com.bizwink.cms.news.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.sitesetting.*"
                contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int columnID = ParamUtil.getIntParameter(request,"column",0);
  boolean doMove = ParamUtil.getBooleanParameter(request, "doMove");
  IColumnManager columnMgr = ColumnPeer.getInstance();
  Column column = columnMgr.getColumn(columnID);
  int siteID = column.getSiteID();

  if (doMove)
  {
    int objectID = ParamUtil.getIntParameter(request,"objectID",0);
    int moveType = ParamUtil.getIntParameter(request,"action",0);
    int isMovePic = ParamUtil.getIntParameter(request,"pic",0);

    Column objColumn = columnMgr.getColumn(objectID);
    if (siteID == objColumn.getSiteID())
    {
      out.println("<script language=javascript>alert(\"不能在同一个站点内移动！\");history.go(-1);</script>");
      return;
    }
    if (column.getEName().equals("/"))
    {
      out.println("<script language=javascript>alert(\"要移动的栏目不能为首页！\");history.go(-1);</script>");
      return;
    }

    Move move = new Move();
    move.setAimColumnID(objectID);
    move.setOrgColumnID(columnID);
    move.setUserName(authToken.getUserID());
    move.setAppPath(application.getRealPath("/"));
    move.setMoveType(moveType);
    move.setIsMovePic(isMovePic);

    MoveOuterColumn moveOuterColumn = new MoveOuterColumn();
    moveOuterColumn.run(move);
  }

  ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
  IMoveManager moveMgr = MovePeer.getInstance();
  int allArticleNum = moveMgr.getArticlesNum(columnID);       //包括子栏目所有的文章
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css">
<SCRIPT LANGUAGE=JavaScript>
function check()
{
  if (parseInt(document.movecolumn.column.value) == 0)
  {
    alert("请选择一个要移动的栏目！");
    return false;
  }
  if (document.movecolumn.objectID.value == "")
  {
    alert("请选择目的栏目！");
    return false;
  }
  if (document.movecolumn.column.value == document.movecolumn.objectID.value)
  {
    alert("源栏目和目的栏目不能相同！");
    return false;
  }
  if (!document.movecolumn.action[0].checked && !document.movecolumn.action[1].checked)
  {
    alert("请选择要迁移的内容！");
    return false;
  }

  var ret = confirm("真的要移动吗？你确定？");
  if (ret)
    return true;
  else
    return false;
}
</SCRIPT>
</head>

<body>
<%
  String[][] titlebars = {
    { "栏目迁移", "" }
  };
  String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<form name="movecolumn" method="post" action="columns.jsp" onSubmit="javascript:return check();">
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=objectID value="">
<input type=hidden name=doMove value="true">
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="98%" height="85" align=center>
  <tr>
    <td width="100%" colspan="7" bgcolor="#666699" height="26"><font color="#FFFFFF">&nbsp;&nbsp;源栏目</font></td>
  </tr>
  <tr height="20">
    <td align=center width="14%">栏目名称</td>
    <td align=center width="15%">英文名称</td>
    <td align=center width="8%">栏目ID</td>
    <td align=center width="25%">栏目路径</td>
    <td align=center width="20%">站点名称</td>
    <td align=center width="9%">站点ID</td>
    <td align=center width="9%">文章数</td>
  </tr>
  <tr height="25">
    <td align=center><b><%=StringUtil.gb2iso4View(column.getCName())%></b></td>
    <td align=center><b><%=column.getEName()%></b></td>
    <td align=center><b><%=column.getID()%></b></td>
    <td align=center><b><%=column.getDirName()%></b></td>
    <td align=center><b><%=siteMgr.getSiteInfo(siteID).getDomainName()%></b></td>
    <td align=center><b><%=column.getSiteID()%></b></td>
    <td align=center><b><%=allArticleNum%></b></td>
  </tr>
</table>
<table align="center"><tr><td align="center" height=40><font color=red><b>
  <input type=radio name=action value=0>只迁移当前栏目的文章&nbsp;&nbsp;
  <input type=radio name=action value=1>迁移文章、栏目及子栏目&nbsp;&nbsp;
  <input type=checkbox name=pic value=1>是否迁移图片
  </b></font></td></tr>
</table>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="98%" height="85" align=center>
  <tr>
    <td width="100%" colspan="7" bgcolor="#666699" height="26"><font color="#FFFFFF">&nbsp;&nbsp;目的栏目</font></td>
  </tr>
  <tr height="20">
    <td align=center width="14%">栏目名称</td>
    <td align=center width="15%">英文名称</td>
    <td align=center width="8%">栏目ID</td>
    <td align=center width="25%">栏目路径</td>
    <td align=center width="20%">站点名称</td>
    <td align=center width="9%">站点ID</td>
    <td align=center width="9%">文章数</td>
  </tr>
  <tr height="25">
    <td align=center><b><div id=cname>&nbsp;</div></b></td>
    <td align=center><b><div id=ename>&nbsp;</div></b></td>
    <td align=center><b><div id=columnID>&nbsp;</div></b></td>
    <td align=center><b><div id=dirname>&nbsp;</div></b></td>
    <td align=center><b><div id=sitename>&nbsp;</div></b></td>
    <td align=center><b><div id=siteID>&nbsp;</div></b></td>
    <td align=center><b><div id=articlenum>&nbsp;</div></b></td>
  </tr>
</table>
<p align=center><input type="submit" value="    确定    "></p>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="98%" height="43" align=center>
  <tr>
    <td width="100%" bgcolor="#666699" height="26"><font color="#FFFFFF">&nbsp;&nbsp;注意事项</font></td>
  </tr>
  <tr>
    <td width="100%">
    <p style="line-height: 150%">
    &nbsp;* 不能将首页栏目迁移到其他栏目下；<br>
    &nbsp;* 不能将栏目迁移当前栏目或其子栏目下。
    </p>
    </td>
  </tr>
</table>
</form>

</body>
</html>