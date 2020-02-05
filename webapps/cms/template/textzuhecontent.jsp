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
    int type = ParamUtil.getIntParameter(request, "order", 0);
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


%>

<html>
<head>
    <title>中英文路径</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <base target='_self'>
    <script language="javascript" src="../js/mark.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        var retstr = "";
        function submit() {
            var cname=document.getElementById("cname").value;
            window.dialogArguments.document.getElementById("usermodelnewsadd").value=window.dialogArguments.document.getElementById("usermodelnewsadd").value+"_"+<%=type%>;
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue ="[CHINESENAME]"+cname+"[/CHINESENAME][ORDER]"+window.dialogArguments.document.getElementById("usermodelnewsadd").value+"[/ORDER]";
                window.close();
            }
        }
    </SCRIPT>
</head>

<body bgcolor="#CCCCCC">
<table width="96%" border="0" align=center>
    <tr height="30">
        <td>
           中文名字：&nbsp;&nbsp;<input type="text" name="cname" id="cname" size="5">
        </td>
    </tr>
    <tr height="50">
        <td align=center>
            <input type="button" value=" 确定 " onclick="submit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value=" 取消 " onclick="javascript:window.close();" class=tine>
        </td>
    </tr>
</table>

</body>
</html>