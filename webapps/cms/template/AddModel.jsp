<%@page import="java.sql.*,
    java.util.*,
    com.bizwink.cms.tree.*,
    com.bizwink.cms.news.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.modelManager.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String editor = authToken.getUserID();

    int columnID  = ParamUtil.getIntParameter(request, "column", 0);

    IModelManager modelMgr = ModelPeer.getInstance();
    List filelist = modelMgr.getInitModelName(columnID);

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

<STYLE TYPE="text/css">
 BODY   {margin-left:10; font-family:Verdana; font-size:12; background:menu}
 BUTTON {width:5em}
 P      {text-align:center}
 TABLE  {cursor:hand}
</STYLE>

<SCRIPT LANGUAGE=JavaScript FOR=Myok EVENT=onclick>
<!--
  window.returnValue = fileName.value;
  window.close();
// -->
</SCRIPT>

<SCRIPT LANGUAGE=JavaScript>
 function selectthis(para){
   fileName.value = para.value;
 }
 </SCRIPT>

</head>

<body bgcolor="#FFFFFF">
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr>
<%
    if (filelist.size() != 0)
          out.print("<td colspan=5> 当前所在栏目--->" + StringUtil.gb2iso4View(CName) + "<font color=red></font></td>");
    else
          out.print("<td colspan=5> 当前所在栏目--->" + StringUtil.gb2iso4View(CName) + "(当前栏目没有模板，请上传新的模板)<font color=red></font></td>");
%>
    </tr>
    <tr class= itm bgcolor='#dddddd'>
    <td align=center widtd="15%">选中该链接 </td>
    <td align=center widtd="50%">模板文件名称</td>
    <td align=center widtd="35%">预览</td>
    </tr>
<%
    int totalSize = filelist.size();
    for(int i=0; i<totalSize; i++) {
        String bgcolor = (i%2==0)?"#ffffcc":"#eeeeee";
        out.print("<tr bgcolor=" + bgcolor + "class=itm>");
        out.print("<td align=center><input type=radio name=selectedLink onclick=javascript:selectthis(this) value=" + (String)filelist.get(i) + "></td>");
        out.print("<td>" + (String)filelist.get(i) + "</td>");
        out.print("<td></td>");
        out.print("</tr>");
    }
%>
    </tr>
    </table>
<p>
    <input type="text" name="fileName" value="">
</p>
  <p>
<BUTTON ID=Myok TYPE=SUBMIT>OK</BUTTON>
<BUTTON ONCLICK="window.close();">Cancel</BUTTON>
  </p>
</body>
</html>
