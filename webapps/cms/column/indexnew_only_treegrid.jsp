<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/treegrid-dnd.js"></script>
    <script type="text/javascript">
        var w = document.documentElement.scrollWidth || document.body.scrollWidth;
        var h = document.documentElement.scrollHeight || document.body.scrollHeight;
    </script>
</head>
<body>
<div style="margin:20px 0;"></div>
<script type="text/javascript">
    document.write('<div class="easyui-layout" style="width:'+ w + 'px;height:' + h + 'px;">');
</script>
<!--div class="easyui-layout" style="width:700px;height:350px;"-->
<div id="p" data-options="region:'west'" title="West" style="width:30%;padding:0px">
    <iframe src="columntreenew.jsp?rightid=<%=rightid%>" height="100%"></iframe>
</div>
<div data-options="region:'center'" title="Center">
    <iframe src="columns.jsp?rightid=<%=rightid%>" width="100%" height="100%"></iframe>
</div>
</div>
</body>
<!--frameset cols=160,* border=0 frameborder=0 framespacing=0>
    <frame src="columntreenew.jsp?rightid=<%=rightid%>" name=cmsleft scrolling=auto marginheight=0 marginwidth=0>
    <frameset frameborder=0 framespacing=0 border=0 cols=* rows=0,*>
        <frame src="columntree.jsp?rightid=<%=rightid%>" name=cmsleft scrolling=auto marginheight=0 marginwidth=0>
        <frame marginwidth=5 marginheight=5 src="menu.html" name=menu scrolling=auto frameborder=0>
    </frameset>
    <frame src=columns.jsp?rightid=1 name=cmsright scrolling=auto marginheight=0 marginwidth=5>
</frameset-->
</html>
