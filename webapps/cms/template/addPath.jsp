<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.xml.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int type = ParamUtil.getIntParameter(request, "type", 0);
    String str = ParamUtil.getParameter(request, "str");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    ViewFile viewfile = new ViewFile();

    int styleID = 0;
    if (str != null && str.trim().length() > 0) {
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        styleID = Integer.parseInt(properties.getProperty(properties.getName()));
    }

    List list = viewfileMgr.getViewFileC(siteID, 8);
%>

<html>
<head>
    <title>中英文路径</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <base target='_self'>
    <script language="JavaScript" src="../js/jquery-1.12.4.js"></script>
    <script language="javascript" src="../js/mark.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        function submit() {
            if (listType.selectedIndex == 0) {
                alert("请选取样式文件！");
                return;
            }
            var returnvalue = document.getElementById("listType").options[listType.selectedIndex].value;
            var markname = "";
            var type = <%=type%>;
            var styleID = <%=styleID%>;
            if(type === 0){
                markname = "中文路径";
                returnvalue = "[TAG][CHINESE_PATH]" + returnvalue + "[/CHINESE_PATH][/TAG]";
            }else if(type === 1){
                markname = "英文路径";
                returnvalue = "[TAG][ENGLISH_PATH]" + returnvalue + "[/ENGLISH_PATH][/TAG]";
            }
            if(styleID == 0){
                returnvalue = "<INPUT data-name='" + returnvalue + "' name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
                window.parent.opener.InsertHTML(returnvalue);
            }else{
                window.parent.opener.UpdateHTML(returnvalue);
            }
            top.close();
        }
    </SCRIPT>
</head>

<body bgcolor="#CCCCCC">
<table width="96%" border="0" align=center>
    <tr height="30">
        <td>请选择中英文路径样式：</td>
    </tr>
    <tr height="30">
        <td>
            列表类型：
            <select name="listType" style="width:160" class=tine id="listType">
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
            <input type="button" value="新建" style="height:20px;width:50px;font-size:9pt" onclick="createStyle(8,0);">
            <input type="button" style="height:20px;width:50px;font-size:9pt" onclick="updateStyle(8,listType.options[listType.selectedIndex].value,0);" value="修改">
            <input type="button" style="height:20px;width:50px;font-size:9pt" onclick="previewStyle(8,listType.options[listType.selectedIndex].value);" value="预览">
        </td>
    </tr>
    <tr height="50">
        <td align=center>
            <input type="button" value=" 确定 " onclick="submit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value=" 取消 " onclick="window.close();" class=tine>
        </td>
    </tr>
</table>

</body>
</html>