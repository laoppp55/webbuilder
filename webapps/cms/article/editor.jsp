<%@page import="com.bizwink.cms.security.*,
                com.bizwink.cms.util.*"
        contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String ename = ParamUtil.getParameter(request, "ename");
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    if(startflag == 1){
        String content = ParamUtil.getParameter(request, "content");
        content = content.substring(content.toLowerCase().indexOf("<body"), content.toLowerCase().indexOf("</body>"));
        content = content.substring(content.indexOf(">") + 1, content.length());
        content = content.replaceAll("\r", "");
        content = content.replaceAll("\n", "");
        content = content.replaceAll("\'","&apos;");
        if(content != null) {
            content = content.trim();
            content = content.replace("\\","\\\\");
        }
%>
<script language=javascript>
    window.opener.createForm.<%=ename%>.value='<%=content%>';
    window.close();
</script>
<%
    }
%>

<html>
<head>
    <title>HTML编辑器</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/editor.css">
    <script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
    <script LANGUAGE="JavaScript" SRC="../toolbars/dhtmled.js"></script>
</head>
<body>
<form action="editor.jsp" method="post" name="createForm">
    <input type=hidden name=startflag value=1>
    <input type=hidden name=ename value="<%=ename%>">
    <%
        String[][] titlebars = {
                { "编辑器", "" }
        };
        String[][] operations = {
                { "<input type=submit value='  保存  '>", "" },
                { "<input type=button value='  取消  ' onclick=window.close();>", "" }
        };
    %>
    <%@ include file="../inc/titlebar.jsp" %>

    <table border="0" width="100%"><tr><td>
        <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"></textarea>
        <script type="text/javascript">
            var oFCKeditor = new FCKeditor('content') ;
            oFCKeditor.BasePath = "../fckeditor/" ;
            oFCKeditor.Height = 550;
            oFCKeditor.ToolbarSet = "ArticleDefault" ;
            oFCKeditor.ReplaceTextarea();
        </script>
    </td></tr></table>
</form>
<script language=javascript>
    var str = opener.createForm.<%=ename%>.value;
    var p = -1;
    if ((p=str.toLowerCase().indexOf("<body")) > -1) {
        str = str.substring(p + 5);
        str = str.substring(str.indexOf(">") + 1);
    }
    if ((p=str.toLowerCase().indexOf("</body>")) > -1) {
        str = str.substring(0, p);
    }

    while(str.indexOf("&apos;") != -1)
        str = str.replace("&apos;","'");

    str = "<html>\n<head>\n</head>\n<body>\n" + str + "</body>\n</html>";

    document.createForm.content.value = str;
</script>

</body>
</html>