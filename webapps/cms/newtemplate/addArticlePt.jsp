<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String str = ParamUtil.getParameter(request, "str");
    String datestyle = "";
    String timestyle = "";
    if (str != null) {
        str = str.substring(str.indexOf("[ARTICLE_PT]") + 12, str.indexOf("[/ARTICLE_PT]"));
        datestyle = str.substring(0, str.indexOf("|")).toUpperCase();
        timestyle = str.substring(str.indexOf("|") + 1).toUpperCase();
    }
%>

<html>
<head>
    <title>日期/时间样式</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <SCRIPT LANGUAGE=JavaScript>
        var retstr = "";
        function submit()
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            retstr = "yes";
            if (isMSIE) {
                window.returnValue = "[TAG][ARTICLE_PT]" + datestyle.value + "|" + timestyle.value + "[/ARTICLE_PT][/TAG]";
                window.close();
            } else {
                var markname = "发布时间";
                var returnvalue = document.getElementById("datestyle").value + "|" + document.getElementById("timestyle").value;
                returnvalue = "[TAG][ARTICLE_PT]" + returnvalue + "[/ARTICLE_PT][/TAG]";
            <%if (str != null) {%>
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
        <td>日期样式：
            <select name=datestyle style="width:140" class=tine>
                <option value="yyyy-MM-dd" <%if(datestyle.equals("YYYY-MM-DD")){%>selected<%}%>>YYYY-MM-DD</option>
                <option value="yyyyy/MM/dd" <%if(datestyle.equals("YYYY/MM/DD")){%>selected<%}%>>YYYY/MM/DD</option>
                <option value="MM/dd/yyyy" <%if(datestyle.equals("MM/DD/YYYY")){%>selected<%}%>>MM/DD/YYYY</option>
                <option value="MM-dd-yyyy" <%if(datestyle.equals("MM-DD-YYYY")){%>selected<%}%>>MM-DD-YYYY</option>
                <option value="yyyy年MM月dd日" <%if(datestyle.equals("YYYY年MM月DD日")){%>selected<%}%>>YYYY年MM月DD日</option>
                <option value="MM月dd日" <%if(datestyle.equals("MM月DD日")){%>selected<%}%>>MM月DD日</option>
                <option value="dd日" <%if(datestyle.equals("DD日")){%>selected<%}%>>DD日</option>
            </select>
        </td>
        <td>时间样式：
            <select name=timestyle style="width:120" class=tine>
                <option value="" <%if(timestyle.equals("")){%>selected<%}%>>无时间</option>
                <option value="HH:mm:ss" <%if(timestyle.equals("HH:MM:SS")){%>selected<%}%>>HH:MM:SS</option>
                <option value="HH:mm" <%if(timestyle.equals("HH:MM")){%>selected<%}%>>HH:MM</option>
                <option value="HH" <%if(timestyle.equals("HH")){%>selected<%}%>>HH</option>
            </select>
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