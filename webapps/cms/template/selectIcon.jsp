<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String sitename = authToken.getSitename();
    String dot_sitename = StringUtil.replace(sitename,"_",".");
    String appRoot = application.getRealPath("/");
    String filePath = appRoot + "sites" +  File.separator + "images"  +  File.separator + "buttons"  +  File.separator;

    List fList = FileUtil.getFileListInPath(filePath);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>选择小图标</title>
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <SCRIPT LANGUAGE=JavaScript>
        function subOK() {
            var strVal = selectIconFile.filename.value;
            if (strVal == "")
            {
                alert("小图标文件名不能为空，请选择图标文件")
                return false;
            }
            window.returnValue = strVal;
            window.close();
        }

        function selectthis(para)
        {
            selectIconFile.filename.value = para.value;
        }
    </SCRIPT>
</head>
<body>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr class=itm bgcolor='#dddddd' height=20>
        <td align=center>选中</td>
        <td align=center>图标名称</td>
    </tr>
    <%
        for (int i = 0; i < fList.size(); i++) {
            String filename = (String) fList.get(i);
            System.out.println(filename);
            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
            out.println("<tr bgcolor=" + bgcolor + " height=25 onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "'\">");
            out.println("<td width='10%' align=center><input type=radio name=selectedLink onclick=javascript:selectthis(this) value=" + filename + "></td>");
            out.println("<td width='70%'><img src=\"/_sys_images/buttons/" +filename + "\" /></td>");
            out.println("</tr>");
        }
    %>
</table>

<table cellpadding="1" cellspacing="2" border="0">
    <form name="selectIconFile">
        <tr>
            <td>
                <span style="font-size:9pt">选中：</span>
                <input name="filename" size="40" readonly class=tine>
            </td>
        </tr>
    </form>
</table>
<br>
<input type="button" class=tine value="确  定" onclick="subOK();">&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" class=tine ONCLICK="top.close();" value="返  回">
</body>
</html>
