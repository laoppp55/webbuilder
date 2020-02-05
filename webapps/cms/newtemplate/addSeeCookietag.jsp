<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.xml.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    String str = ParamUtil.getParameter(request, "str");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    ViewFile viewfile = new ViewFile();
    System.out.println("markid="+markID);
    int styleID = 0;
    if (str != null && str.trim().length() > 0) {
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        styleID = Integer.parseInt(properties.getProperty(properties.getName()));
    }
    styleID=markID;
    List list = viewfileMgr.getViewFileC(siteID, 1);
%>

<html>
<head>
    <title>中英文路径</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <base target='_self'>
    <script language="javascript" src="../js/mark.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        var retstr = "";
        function submit()
        {
            var getnum=document.getElementById("getnum").value;
            if (listType.selectedIndex == 0)
            {
                alert("请选取样式文件！");
                return;
            }
            if ( getnum== 0)
            {
                alert("请填写文章数");
                return;
            }
            retstr = "yes";

            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
             //   alert(listType.options[listType.selectedIndex].value);
                window.returnValue = "[TAG][SEECOOKIE][MARKID]"+listType.options[listType.selectedIndex].value+"_"+getnum+"[/MARKID][/SEECOOKIE][/TAG]";
                window.close();
            
            }
        }

        function cancel()
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                window.close();
            }else{
                top.close();
            }
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

        最近浏览最大显示数： <input type="text" size="4" name="getnum"> &nbsp;&nbsp;&nbsp; 列表类型：  <select name="listType" style="width:160" class=tine id="listType">
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
            <input type="button" value="新建" style="height:20;width:30;font-size:9pt" onclick="createStyle(1,0);">
            <input type="button" style="height:20;width:30;font-size:9pt"
                    onclick="updateStyle(1,listType.options[listType.selectedIndex].value,0);" value="修改">
            <input type="button" style="height:20;width:30;font-size:9pt"
                    onclick="previewStyle(1,listType.options[listType.selectedIndex].value);" value="预览">
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