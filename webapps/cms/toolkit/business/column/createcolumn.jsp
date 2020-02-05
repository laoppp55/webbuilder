<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
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

  //int siteid = authToken.getSiteID();
  int siteid = 1;
  IColumnManager columnManager = ColumnPeer.getInstance();
  boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
  int parentID     = ParamUtil.getIntParameter(request, "parentID", 0);
  String Code      = ParamUtil.getParameter(request, "code");

  boolean         errors = false;
  boolean        success = false;
  boolean     errorCName = false;
  boolean     errorEName = false;
  String           CName = "";
  String           EName = "";
  String     extfilename = "";
  int           activeID = 1;
  int            orderid = 0;
  int              msgno = 0;
  boolean       dualName = false;

  if (doCreate)
  {
    CName        = ParamUtil.getParameter(request, "CName");
    EName        = ParamUtil.getParameter(request, "EName");
    extfilename  = ParamUtil.getParameter(request, "extfilename");
    activeID     = ParamUtil.getIntParameter(request, "activeID", 1);
    orderid      = ParamUtil.getIntParameter(request, "orderid", 1);
    if (CName == null)
    {
      errorCName = true;
      errors = true;
    }
    if (EName == null)
    {
      errorEName = true;
      errors = true;
    }
  }

  if (!errors && doCreate)
  {
    dualName = columnManager.duplicateEnName(parentID,EName);
    if (!dualName)
    {
      try
      {
        Column column = new Column();
        column.setSiteID(siteid);
        column.setCode( Code );
        column.setParentID( parentID );
        column.setCName( CName );
        column.setEName( EName );
        column.setExtname(extfilename);
        column.setActiveID( activeID );
        column.setCreateDate(new Timestamp(System.currentTimeMillis()));
        column.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        String editor = authToken.getUserID();
        column.setOrderID(orderid);
        column.setEditor( editor );
        columnManager.create(column);
        success = true;
      }
      catch (ColumnException uaee)
      {
        uaee.printStackTrace();
        errors = true;
      }
    }
  }

  if (success)
  {
    response.sendRedirect(response.encodeRedirectURL("index.jsp?msgno=0"));
    return;
  }

  String parentName = "网站首页";
  if (parentID > 0)
  {
    Column parentColumn = columnManager.getColumn(parentID);
    if (parentColumn != null)
    {
      parentName = StringUtil.gb2iso4View(parentColumn.getCName());
    }
  }
  else
  {
    Column parentColumn = columnManager.getSiteRootColumn(siteid);
    if (parentColumn != null)
    {
      parentName = StringUtil.gb2iso4View(parentColumn.getDirName());
    }
  }

  //获得首页文件扩展名
  String extname = columnManager.getIndexExtName(siteid);
%>

<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href=../style/global.css>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
	     { "栏目管理", "index.jsp" },
	     { "新建栏目", "" }
	 };
	 String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>
<%
  if (!success && errors)
  {
    out.println("<p class=cur>");
    if (!success)
      out.println("栏目 "+EName+" 已经存在。请另换一个栏目名。");
    else
      out.println("输入有错误。请检查下面字段，再试。");
  }
%>
</p>
<center>
<form action="createcolumn.jsp" method="post" name="createForm">
  <input type="hidden" name="doCreate" value="true">
  <input type="hidden" name="parentID" value="<%=parentID%>">
  <input type="hidden" name="code" value="<%=Code==null?"":Code%>">
  <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=100%>
    <tr height=20>
      <td align=left class=line colspan=2>栏目信息</td>
    </tr>
    <tr height=30>
      <td align=right class=line>父栏目名：</td>
      <td class=tine>&nbsp;<%= parentName %></td>
    </tr>
    <tr height=30>
      <td align=right><font class=line <%= (errorCName)?(" color=\"#ff0000\""):"" %>>栏目名称：</font></td>
      <td>&nbsp;<input class=tine name=CName size=30 value="<%= (CName!=null)?CName:"" %>">*</td>
    </tr>
    <tr height=30>
      <td align=right><font class=line <%= (errorEName)?(" color=\"#ff0000\""):"" %>>栏目目录：</font></td>
      <td>&nbsp;<input class=tine name=EName size=30 value="<%= (EName!=null)?EName:"" %>">*</td>
    </tr>
    <tr height=30>
      <td align=right class=line>栏目排序：</td>
      <td>&nbsp;<input class=tine name=orderid size=30 value="<%= orderid %>">*</td>
    </tr>
    <tr height=30>
      <td class=line align=right>页面文件扩展名：</td>
      <td class=tine>&nbsp;<select name=extfilename size=1 class=tine style="width:100">
      <option value="html"  <%if(extname.equalsIgnoreCase("html")){%>selected<%}%>>html</option>
      <option value="htm"   <%if(extname.equalsIgnoreCase("htm")){%>selected<%}%>>htm</option>
      <option value="jsp"   <%if(extname.equalsIgnoreCase("jsp")){%>selected<%}%>>jsp</option>
      <option value="asp"   <%if(extname.equalsIgnoreCase("asp")){%>selected<%}%>>asp</option>
      <option value="shtm"  <%if(extname.equalsIgnoreCase("shtm")){%>selected<%}%>>shtm</option>
      <option value="shtml" <%if(extname.equalsIgnoreCase("shtml")){%>selected<%}%>>shtml</option>
      <option value="php"   <%if(extname.equalsIgnoreCase("php")){%>selected<%}%>>php</option>
      </select></td>
    </tr>
    <tr height=30>
      <td class=line align=right>是否有效：</td>
      <td class=tine>&nbsp;<input type=radio <%= (activeID==1)? "checked":""  %> name="activeID"  value="1">是&nbsp;&nbsp;
        <input type=radio <%= (activeID==0)? "checked":"" %> name="activeID" value="0">否
      </td>
    </tr>
</table>
<br>
<input type=image src=../images/button_add.gif>&nbsp;&nbsp;
<input type=image src=../images/button_cancel.gif onclick="location.href='index.jsp';return false;">
</form>
</center>

</BODY>
</html>
