<%@ page import="java.io.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String sitename = authToken.getSitename();
    String path = ParamUtil.getParameter(request, "path");
    String filename = ParamUtil.getParameter(request, "filename");
    String appRoot = application.getRealPath("/");

    String filePath = "";
    if (path != null)
        filePath = appRoot + "sites" + File.separator + sitename + File.separator + "_templates" + File.separator + StringUtil.replace(path, "/", File.separator);
    else
        filePath = appRoot + "sites" + File.separator + sitename + File.separator + "_templates" + File.separator;

    if (filename != null && filename.length() > 0)    //删除
    {
        File file = new File(filePath + filename);
        if (file.exists()) file.delete();
        response.sendRedirect("listfile.jsp?column=" + columnID + "&path=" + path);
        return;
    }

    List fList = FileUtil.getFileListInPath(filePath);
    Column column = null;
    String CName = "";
    IColumnManager columnManager = ColumnPeer.getInstance();
    if (columnID>0) {
        column = columnManager.getColumn(columnID);
        CName = StringUtil.gb2iso4View(column.getCName());
    } else {
        CName = "程序模板";
    }

    if (path == null)
        path = "";
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<SCRIPT LANGUAGE=JavaScript>
    function subOK() {
        var strVal = selectModelFile.filename.value;
        if (strVal == "")
        {
            alert("模板文件名不能为空，请选择模板文件")
            return false;
        }
        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            window.parent.returnValue = selectModelFile.filepath.value + strVal;
            window.parent.close();
        } else {
            var returnvalue = selectModelFile.filepath.value + strVal;
            var objXml = new XMLHttpRequest();
            objXml.open("POST", "/webbuilder/template/getTemplateContent.jsp?path=" + returnvalue + "&column=<%=columnID%>", false);
            objXml.send(null);
            returnvalue = objXml.responseText;
            var oEditor = window.parent.opener.top.InsertHTML('content', returnvalue);
            top.close();
        }
    }
</SCRIPT>

<script language="javascript">
    function selectthis(para)
    {
        selectModelFile.filename.value = para.value;
    }

    function Preview(filename)
    {
        window.open("/webbuilder/sites/<%=sitename%>/_templates/<%=path%>" + filename, "", "");
    }

    function Delete(filename)
    {
        if (confirm("真的要删除该模板吗？"))
            window.location = "listfile.jsp?column=<%=columnID%>&path=<%=path%>&filename=" + filename;
    }
</script>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr>
        <td colspan=5> 当前所在栏目--->><font color=red><%=CName%>
        </font></td>
    </tr>
    <tr class=itm bgcolor='#dddddd' height=20>
        <td align=center>选中</td>
        <td align=center>模板文件名</td>
        <td align=center>预览</td>
        <td align=center>删除</td>
    </tr>
    <%
        for (int i = 0; i < fList.size(); i++) {
            filename = (String) fList.get(i);
            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
            out.println("<tr bgcolor=" + bgcolor + " height=25 onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "'\">");
            out.println("<td width='10%' align=center><input type=radio name=selectedLink onclick=javascript:selectthis(this) value=" + filename + "></td>");
            out.println("<td width='70%'>" + filename + "</td>");
            if (!filename.toLowerCase().endsWith(".jsp"))
                out.println("<td width='10%' align=center><a href=\"javascript:Preview('" + filename + "');\"><img src=../images/button/view.gif border=0></a></td>");
            else
                out.println("<td width='10%' align=center>&nbsp;</td>");
            out.println("<td width='10%' align=center><a href=\"javascript:Delete('" + filename + "');\"><img src=../images/button/del.gif border=0></a></td>");
            out.println("</tr>");
        }
    %>
</table>

<table cellpadding="1" cellspacing="2" border="0">
    <form name="selectModelFile">
        <tr>
            <td>
                <span style="font-size:9pt">选中的：</span>
                <input type="hidden" name="filepath" id="pathid" value="<%=filePath%>">
                <input type="text" name="filename" id="filenameid" size="40" readonly class=tine>
            </td>
        </tr>
    </form>
</table>
<br>
</BODY>
</html>