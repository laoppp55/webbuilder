<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.xml.*,
                 com.bizwink.cms.viewFileManager.*"
         contentType="text/html;charset=gbk"
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

    List list = viewfileMgr.getViewFileC(siteID, 10);
%>

<html>
<head>
    <title>选择样式</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <script language="javascript" src="../js/mark.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        var retstr = "";
        function submit()
        {
            if (nextarticle.selectedIndex == 0)
            {
                alert("请选取样式文件！");
                return;
            }
            retstr = "yes";

            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = nextarticle.options[nextarticle.selectedIndex].value;
                window.close();
            } else {
                var returnvalue = document.getElementById("nextarticle").options[nextarticle.selectedIndex].value;
                var markname = "";
                <%if(type == 0){%>
                markname = "上一篇";
                returnvalue = "[TAG][PREV_ARTICLE]" + returnvalue + "[/PREV_ARTICLE][/TAG]";
                <%}else if(type == 1){%>
                markname = "下一篇";
                returnvalue = "[TAG][NEXT_ARTICLE]" + returnvalue + "[/NEXT_ARTICLE][/TAG]";
                <%}
                if(styleID > 0){%>
                window.opener.parent.UpdateHTML('content', returnvalue);
                top.close();
                <%}else{%>
                returnvalue = "<INPUT name='"+returnvalue+"' type=button value='["+markname+"]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
                window.opener.InsertHTML('content', returnvalue);
                top.close();
                <%}%>
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
<table width="96%" border="0" align="center">
    <tr height="30">
        <td>请选择样式：</td>
    </tr>
    <tr height="30">
        <td>
            样式文件：
            <select name="nextarticle" style="width:160" class=tine id="nextarticle">
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
            <input type="button" style="height:20;width:30;font-size:9pt" onclick="createStyle(10,0);" value="新建">
            <input type="button" style="height:20;width:30;font-size:9pt"
                    onclick="updateStyle(10,nextarticle.options[nextarticle.selectedIndex].value,0);" value="修改">
            <input type="button" style="height:20;width:30;font-size:9pt"
                    onclick="previewStyle(10,nextarticle.options[nextarticle.selectedIndex].value);" value="预览">
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