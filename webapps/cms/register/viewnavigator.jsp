<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.viewFileManager.*,
                com.bizwink.cms.util.*"
        contentType="text/html;charset=gbk"
        %>

<%
    int ID = ParamUtil.getIntParameter(request, "id", 0);
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    navigator nv = viewfileMgr.getNavigator(ID);
    String content = "";
    String fcontent = "";
    content = StringUtil.gb2iso4View(nv.getContent());
    fcontent = StringUtil.gb2iso4View(nv.getfContent());
%>
<html>
<head>
    <title>网站导航预览</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<body>
<table border=0 width="100%">
    <tr>
        <td>
            <%=content%>
        </td>
    </tr>
</table>
<table border=0 width="100%">
    <tr>
        <td>
            <%=fcontent%>
        </td>
    </tr>
</table>
</body>
</html>