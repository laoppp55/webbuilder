<%@page import="java.util.*,
    		java.io.*,
    		java.sql.Timestamp,
    		com.bizwink.cms.modelManager.*,
    		com.bizwink.cms.tree.*,
                com.bizwink.cms.news.*,
    		com.bizwink.cms.security.*,
    		com.bizwink.cms.util.*"
    		contentType="text/html;charset=gbk"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int pages = ParamUtil.getIntParameter(request, "pages", 1);
    int range = 10;    //每页显示的条数

    boolean error = false;
    boolean success = false;

    String editor = authToken.getUserID();
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int columnID = ParamUtil.getIntParameter(request, "columnID", 0);
    String dir1 = ParamUtil.getParameter(request, "dir1");
    String dir2 = ParamUtil.getParameter(request, "dir2");
    String filename = null;

    if (columnID == 0)
    {
    	out.println("参数有误，请重新操作！");
    	error = true;
    	return;
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    TemplatePeer tempMgr = new TemplatePeer();

    //组合PATH
    String path = request.getRealPath("/");
    path = path + "cmstemplates" + File.separator;
    if (dir1 != null)
    {
      path = path + dir1;
    }
    if (dir2 != null)
    {
      path = path + File.separator + dir2;
    }
    path = StringUtil.iso2gb(path);

    Tree colTree = null;
    String tempDir = "";
    colTree = TreeManager.getInstance().getSiteTree(authToken.getSiteID());

    if (columnID == colTree.getTreeRoot())
    {
      tempDir ="/";
    }
    else
    {
      tempDir = colTree.getDirName(colTree, columnID);
    }

    //添加模板
    if (doCreate)
    {
    	filename = ParamUtil.getParameter(request, "Item");
    	if (filename == null)
    	{
    	    error = true;
    	}
    }

    if (doCreate && !error)
    {
      tempMgr.create(editor,
                     path + File.separator + filename,
                     request.getRealPath("/"),
                     authToken.getSitename(),
                     tempDir,
                     authToken.getImgSaveFlag(),
                     authToken.getSiteID());

      success = true;
    }

    if (success)
    {
      out.println("<script language=javascript>");
      out.println("parent.window.close();");
      out.println("</script>");
      return;
    }

    //读出当前文件夹下的模板文件
    String arr[][] = tempMgr.getTemplates(path);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css">
<title></title>
<script src="../js/template.js"></script>
</head>

<body>
<form method="POST" action="right.jsp" name="form1" onsubmit="javascript:return Select_Template();">
<input type="hidden" name="doCreate" value="true">
<input type="hidden" name="dir1" value="<%=dir1%>">
<input type="hidden" name="dir2" value="<%=dir2%>">
<input type="hidden" name="columnID" value="<%=columnID%>">

<table class=line width="100%" border=0 cellspacing=0 cellpadding=0>
  <tr bgcolor=#003366>
    <td height=2 colspan=2></td>
  </tr>
  <tr>
    <td width="50%" class=line><a href="javascript:parent.document.location='index.jsp'">模板管理</a>&nbsp;&gt;&nbsp;选择系统模板</td>
    <td width="50%" class=line align=right>当前栏目：<%=CName%></td>
  </tr>
  <tr bgcolor=#003366>
    <td colspan=2 height=2></td>
  </tr>
</table>
<%
    if (arr != null)
    {
    	if (arr.length > range)
    	{
    	    out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
    	    out.println("<tr><td width=50% align=left class=line>");
	    if (pages > 1)
	    	out.println("<a href=right.jsp?columnID="+columnID+"&dir1="+dir1+"&dir2="+dir2+"&pages="+String.valueOf(pages-1)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+(pages-1)*range);
	    else
	    	out.println("&nbsp;");
            out.println("</td><td width=50% align=right class=line>");
            if (arr.length > pages * range)
            	out.println(arr.length-pages*range+"<a href=right.jsp?columnID="+columnID+"&dir1="+dir1+"&dir2="+dir2+"&pages="+String.valueOf(pages+1)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
            else
            	out.println("&nbsp;");
            out.println("</td></tr></table>");
    	}
    }
%>
<table border="0" width="100%">
    <tr>
      <td width="100%" valign=top>
        <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
          <tr height=25>
            <td width="25%" bgcolor="#DEDFDE" align="center">模板名称</td>
            <td width="25%" bgcolor="#DEDFDE" align="center">模板类别</td>
            <td width="20%" bgcolor="#DEDFDE" align="center">更新时间</td>
            <td width="15%" bgcolor="#DEDFDE" align="center">预&nbsp;览</td>
            <td width="15%" bgcolor="#DEDFDE" align="center">选&nbsp;择</td>
          </tr>
          <%
          if (arr != null)
          {
              int start = (pages-1) * range;
	      int total = range + start;
  	      if (arr.length < total)
	      {
		  total = arr.length;
  	      }

  	      for (int i=start; i<total; i++)
              {
          %>
          <tr>
            <td bgcolor="#EFC300" align="center" height="16"><%=arr[i][0]%></td>
            <td bgcolor="#EFC300" align="center" height="16"><%=(dir1==null)?"&nbsp;":dir1%><%=(dir2==null)?"":" - "+dir2%></td>
            <td bgcolor="#EFC300" align="center" height="16"><%=arr[i][1]%></td>
    <%
    String temp = StringUtil.gb2iso(arr[i][0]);
    temp = temp.substring(temp.lastIndexOf(".") + 1, temp.length());

    if (temp.compareToIgnoreCase("htm")  == 0 ||
        temp.compareToIgnoreCase("html") == 0 ||
        temp.compareToIgnoreCase("shtm") == 0 ||
        temp.compareToIgnoreCase("shtml")== 0)
    {
    %>
            <td bgcolor="#EFC300" align="center" height="16"><a href="/webbuilder/cmstemplates/<%=(dir1==null)?"":dir1+"/"%><%=(dir2==null)?"":dir2+"/"%><%=(arr[i][0]==null)?"":arr[i][0]%>" target=_blank><img border="0" src="../images/preview.gif" width="15" height="15" border=0></a></td>
    <%}else{%>
    	    <td bgcolor="#EFC300" align="center" height="16">&nbsp;</td>
    <%}%>
            <td bgcolor="#EFC300" align="center" height="16"><input type="radio" value="<%=arr[i][0]%>" name="Item"></td>
          </tr>
          <%}}%>
       </table>
       <br><br>
       <p align=center>
       <input type="submit" value=" 确 定 " name="Select" <%if(arr==null){%>disabled<%}else if(arr.length==0){%>disabled<%}%>>&nbsp;&nbsp;&nbsp;
       <input type="button" value=" 返 回 " name="GoBack" onclick="parent.window.close();">
       </p>
      </td>
    </tr>
</table>
</form>

</body>
</html>
