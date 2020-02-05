<%@page import="java.sql.*,
    		java.util.*,
    		java.io.*,
    		com.bizwink.cms.modelManager.*,
    		com.bizwink.cms.news.*,
    		com.bizwink.cms.security.*,
    		com.bizwink.cms.util.*"
    		contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String editor = authToken.getUserID();

    int columnID  = ParamUtil.getIntParameter(request, "column", 0);

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    //读出模板列表
    String dir = "";

    IModelManager modelMgr = ModelPeer.getInstance();
    String content = modelMgr.getCmsTemplates();
%>
<html>
<head>
<title>选择模板</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css"> 
<script language="JavaScript"> 
<!-- 
if (window.Event)  
document.captureEvents(Event.MOUSEUP);  

function nocontextmenu()  
{ 
event.cancelBubble = true 
event.returnValue = false; 

return false; 
} 

function norightclick(e)  
{ 
if (window.Event)  
{ 
if (e.which == 2 || e.which == 3) 
return false; 
} 
else 
if (event.button == 2 || event.button == 3) 
{ 
event.cancelBubble = true 
event.returnValue = false; 
return false; 
} 

} 

document.oncontextmenu = nocontextmenu; // for IE5+ 
document.onmousedown = norightclick; // for all others 
//--> 
</script> 
</head>

<body bgcolor="#FFFFFF">
<div align="center">
  <center>
  <br>
  <table border="0" width="95%">
    <tr>
      <td width="25%" bgcolor="#C0C0C0">
      <%=StringUtil.gb2iso(content)%>
      </td>
      <td width="2%">&nbsp; </td>
      <td width="73%" valign=top>
      <form method="POST" action="--WEBBOT-SELF--">
        <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
          <tr height=25>
            <td width="30%" bgcolor="#DEDFDE" align="center">模板名称</td>
            <td width="30%" bgcolor="#DEDFDE" align="center">模板类别</td>
            <td width="20%" bgcolor="#DEDFDE" align="center">预览</td>
            <td width="20%" bgcolor="#DEDFDE" align="center">选择</td>
          </tr>
          <tr>
            <td bgcolor="#EFC300" align="center" height="16">　</td>
            <td bgcolor="#EFC300" align="center" height="16">　</td>
            <td bgcolor="#EFC300" align="center" height="16"><img border="0" src="../images/preview.gif" width="15" height="15"></td>
            <td bgcolor="#EFC300" align="center" height="16"><input type="radio" value="1" name="Item"></td>
          </tr>
       </table>
       <p align=center>
       <input type="submit" value=" 确 定 " name="Select">&nbsp;&nbsp;&nbsp;
       <input type="button" value=" 返 回 " name="GoBack" onclick="window.close()">
       </p>
      </form>
      </td>
    </tr>
  </table>
  </center>
</div>

</body>
</html>
