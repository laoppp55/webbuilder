<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.xml.*,
                 com.bizwink.cms.viewFileManager.*"
         contentType="text/html;charset=utf-8"
%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int type = ParamUtil.getIntParameter(request, "type", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String str = ParamUtil.getParameter(request, "str");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    ViewFile viewfile = new ViewFile();

    int styleID = 0;
    if (str != null && str.trim().length() > 0) {
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + str);
        String navType_And_Columnid = properties.getProperty(properties.getName());
        int posi = navType_And_Columnid.indexOf("-");
        String navType_s = "";
        if (posi > -1)
            navType_s = navType_And_Columnid.substring(0,posi);
        else
            navType_s = navType_And_Columnid;
        styleID = Integer.parseInt(navType_s);;
    }

    List list = viewfileMgr.getViewFileC(siteID, 2);
%>

<html>
<head>
    <title>选择导航条</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <base target='_self'>
    <script language="JavaScript" src="../js/jquery-1.12.4.js"></script>
    <script language="javascript" src="../js/mark.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        function tijiao() {
            if (navbar.selectedIndex == 0) {
                alert("请选取样式文件！");
                return;
            }
            var markname = "";
            var returnvalue = "";
            var type=<%=type%>;
            var str = "<%=(str==null)?"":str%>";
            if(type == 0){
                markname = "导航条";
                returnvalue = "[TAG][NAVBAR]" + document.getElementById("navbar").options[navbar.selectedIndex].value + "[/NAVBAR][/TAG]";
            }else{
                markname = "分页标记";
                returnvalue = "[TAG][PAGINATION]" + document.getElementById("navbar").options[navbar.selectedIndex].value + "[/PAGINATION][/TAG]";
            }

            if(str != ""){
                window.parent.opener.UpdateHTML(returnvalue);
            }else{
                returnvalue = "<INPUT data-name='" + returnvalue + "' name='" + returnvalue+"' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
                window.parent.opener.InsertHTML(returnvalue);
            }

            window.close();
        }
    </SCRIPT>
</head>

<body bgcolor="#CCCCCC">
<table width="96%" border="0" align="center">
    <tr height="30">
        <td>请选择自定义导航条样式：</td>
    </tr>
    <tr height="30">
        <td>
            列表类型：
            <select id="navbar" name="navbar" style="width:160px" class=tine>
                <option value=0>选择样式文件</option>
                <%
                    for (int i = 0; i < list.size(); i++) {
                        viewfile = (ViewFile) list.get(i);
                %>
                <option value="<%=viewfile.getID()%>" <%if (styleID == viewfile.getID()) {%> selected<%}%>><%=
                StringUtil.gb2iso4View(viewfile.getChineseName())%>
                </option>
                <%}%>
            </select>
            <input type="button" onclick="createStyle(2,<%=columnID%>);" value="新建" style="height:20px;width:50px;font-size:9pt">
            <input type="button" onclick="updateStyle(2,navbar.options[navbar.selectedIndex].value,<%=columnID%>);" value="修改" style="height:20px;width:50px;font-size:9pt">
            <input type="button" onclick="previewStyle(2,navbar.options[navbar.selectedIndex].value);" value="预览" style="height:20px;width:50px;font-size:9pt">
        </td>
    </tr>
    <tr height="50">
        <td align=center>
            <input type="button" value=" 确定 " onclick="tijiao();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value=" 取消 " onclick="window.close();" class=tine>
        </td>
    </tr>
</table>

</body>
</html>