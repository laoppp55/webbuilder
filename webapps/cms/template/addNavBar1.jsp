<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.viewFileManager.*"
                contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  int siteID = authToken.getSiteID();
  String str = ParamUtil.getParameter(request,"str");
  int type = ParamUtil.getIntParameter(request, "type", 0);
  IViewFileManager viewfileMgr = viewFilePeer.getInstance();
  ViewFile viewfile = new ViewFile();

  int styleID = 0;
  if (str != null && str.trim().length() > 0)
  {
    str = StringUtil.replace(str, "[", "<");
    str = StringUtil.replace(str, "]", ">");
    XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + str);
    styleID = Integer.parseInt(properties.getProperty(properties.getName()));
  }

  List list = viewfileMgr.getViewFileC(siteID, 2);
%>

<html>
<head>
<title>选择导航条</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css">
<meta http-equiv="Pragma" content="no-cache">
<script language="javascript" src="../js/mark.js"></script>
<SCRIPT LANGUAGE=JavaScript>
var retstr = "";
        function submit()
        {
            if (navbar.selectedIndex == 0)
            {
                alert("请选取样式文件！");
                return;
            }
            retstr = "yes";

            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = navbar.options[navbar.selectedIndex].value;
                window.close();
            } else {
                var markname = "";
                var returnvalue = "";
                <%if(type == 0){%>
                markname = "导航条";
                returnvalue = "[TAG][NAVBAR]" + document.getElementById("navbar").options[navbar.selectedIndex].value + "[/NAVBAR][/TAG]";
                <%}else{%>
                markname = "分页标记";
                returnvalue = "[TAG][PAGINATION]" + document.getElementById("navbar").options[navbar.selectedIndex].value + "[/PAGINATION][/TAG]";
                <%}
                if(str != null){
                %>
                window.opener.parent.UpdateHTML('content', returnvalue);
                top.close();
                <%}else{%>
                returnvalue = "<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
                window.opener.InsertHTML('content', returnvalue);
                top.close();
                <%}%>
            }
        }

function cancel()
{
  if (retstr == "")
    window.returnValue = "";
  window.close();
}
</SCRIPT>
</head>

<body bgcolor="#CCCCCC" onunload="cancel();">
<table width="96%" border="0" align="center">
  <tr height="30">
    <td>请选择自定义导航条样式：</td>
  </tr>
  <tr height="30">
    <td>
      列表类型：
      <select name="navbar" style="width:160" class=tine>
        <option value=0>选择样式文件</option>
        <%
          for (int i=0; i<list.size(); i++){
          viewfile = (ViewFile)list.get(i);
        %>
        <option value="<%=viewfile.getID()%>" <%if(styleID==viewfile.getID()){%>selected<%}%>><%=StringUtil.gb2iso4View(viewfile.getChineseName())%></option>
        <%}%>
      </select>
      <button style="height:20;width:30;font-size:9pt" onclick="createStyle(2,0);">新建</button>
      <button style="height:20;width:30;font-size:9pt" onclick="updateStyle(2,navbar.options[navbar.selectedIndex].value,0);">修改</button>
      <button style="height:20;width:30;font-size:9pt" onclick="previewStyle(2,navbar.options[navbar.selectedIndex].value);">预览</button>
    </td>
  </tr>
  <tr height="50">
    <td align=center>
      <input type="button" value=" 确定 " onclick="submit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" value=" 取消 " onclick="cancel();" class=tine>
    </td>
  </tr>
</table>

</body>
</html>