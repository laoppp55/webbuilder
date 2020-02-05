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
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    String doaction = ParamUtil.getParameter(request, "doaction");
    String str = ParamUtil.getParameter(request, "str");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    int styleID = 0;
    int maxnum = 0;
    if (str != null && str.trim().length() > 0) {
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        String content = properties.getProperty(properties.getName().concat(".MARKID"));
        int posi = content.indexOf("_");
        styleID = Integer.parseInt(content.substring(0,posi));
        maxnum = Integer.parseInt(content.substring(posi+1));

    }
    List list = viewfileMgr.getViewFileC(siteID, 1);
%>

<html>
<head>
    <title>最近浏览文章标记</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <base target='_self'>
    <script language="JavaScript" src="../js/jquery-1.12.4.js"></script>
    <script language="javascript" src="../js/mark.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        function tijiao() {
            var doact = zxform.doaction.value;
            var getnum=zxform.getnum.value;
            if (zxform.listType.selectedIndex == 0) {
                alert("请选取样式文件！");
                return;
            }
            if ( getnum== 0) {
                alert("请填写文章数");
                return;
            }

            var markname = "最近浏览";
            var returnvalue = "[TAG][SEECOOKIE][MARKID]" + zxform.listType.options[zxform.listType.selectedIndex].value+"_"+getnum+"[/MARKID][/SEECOOKIE][/TAG]";
            if (doact==='e')
                window.parent.opener.UpdateHTML(returnvalue);
            else {
                returnvalue = "<INPUT data-name='" + returnvalue + "' name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
                window.parent.opener.InsertHTML(returnvalue);
            }
            window.close();
        }
    </SCRIPT>
</head>

<body bgcolor="#CCCCCC">
<form name="zxform">
    <input type="hidden" name="doaction" value="<%=doaction%>">
    <table width="96%" border="0" align=center>
        <tr height="30">
            <td>请选择中英文路径样式：</td>
        </tr>
        <tr height="30">
            <td>
                最近浏览最大显示数： <input type="text" size="4" name="getnum" value="<%=(maxnum==0)?"":maxnum%>"> &nbsp;&nbsp;&nbsp;
                列表类型：  <select name="listType" style="width:160px" class=tine id="listType">
                <option value=0>选择样式文件</option>
                <%
                    ViewFile viewfile = new ViewFile();
                    for (int i = 0; i < list.size(); i++) {
                        viewfile = (ViewFile) list.get(i);
                %>
                <option value="<%=viewfile.getID()%>" <%if (styleID == viewfile.getID()) {%> selected<%}%>><%=
                StringUtil.gb2iso4View(viewfile.getChineseName())%>
                </option>
                <%}%>
            </select>
                <input type="button" value="新建" style="height:20px;width:50px;font-size:9pt" onclick="createStyle(1,0);">
                <input type="button" style="height:20px;width:50px;font-size:9pt"
                       onclick="updateStyle(1,listType.options[listType.selectedIndex].value,0);" value="修改">
                <input type="button" style="height:20px;width:50px;font-size:9pt"
                       onclick="previewStyle(1,listType.options[listType.selectedIndex].value);" value="预览">
            </td>
        </tr>
        <tr height="50">
            <td align=center>
                <input type="button" value=" 确定 " onclick="javascript:tijiao();">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onclick="window.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>