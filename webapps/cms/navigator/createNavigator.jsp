<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp" );
        return;
    }

    boolean doCreate     = ParamUtil.getBooleanParameter(request,"doCreate");
    boolean success = false;

    IViewFileManager vfManager = viewFilePeer.getInstance();

    String notes = "";
    String content = "";
    String fcontent = "";
    String cname = null;
    navigator nv = new navigator();

    if (doCreate)
    {
        notes       =  ParamUtil.getParameter(request, "notes");
        content     =  ParamUtil.getParameter(request, "content");
        fcontent    =  ParamUtil.getParameter(request, "fcontent");
        cname       =  ParamUtil.getParameter(request, "cname");
        try {
            nv.setName(cname);
            nv.setNotes(notes);
            nv.setContent(content);
            nv.setfContent(fcontent);
            vfManager.createNavigator(nv);
            success = true;
        } catch (viewFileException vfe) {
            success = false;
            throw new viewFileException("" + vfe.getMessage());
        }
    }

    if (success)
    {
        out.println("<script language=javascript>");
        out.println("opener.history.go(0);");
        out.println("window.close();");
        out.println("</script>");
        return;
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>增加网站导航条</title>
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language="javascript">
        function check(){
            if((addnav.cname.value == null)||(addnav.cname.value == "")){
                alert("请输入网站导航的名称！");
                return false;
            }
            if((addnav.content.value == null)||(addnav.content.value == "")){
                alert("请输入网站导航的内容");
                return false;
            }
            return true;
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form action="createNavigator.jsp" method="post" name="addnav">
    <input type=hidden name=doCreate value="true">
    <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 height="446" align=center>
        <tr>
            <td width="21%" align="right" height="20"><b>导航条名称：</b></td>
            <td width="79%" height="20">&nbsp;<input type="text" name="cname"></td>
        </tr>
        <tr>
            <td width="21%" bgcolor="#EFEFEF" align="right" height="20"><b>注释：</b></td>
            <td width="79%" bgcolor="#EFEFEF" height="20">&nbsp;<input type="text" name="notes" size="50"></td>
        </tr>
        <tr>
            <td width="21%" align="right" height="20"><b>主导航内容：</b></td>
            <td width="79%" height="150">&nbsp;<textarea name="content" cols=66 rows=10 WRAP="soft"></textarea></td>
        </tr>
        <tr>
            <td width="21%" align="right" height="20"><b>辅助导航内容：</b></td>
            <td width="79%" height="150">&nbsp;<textarea name="fcontent" cols=66 rows=10 WRAP="soft"></textarea></td>
        </tr>
    </table>
    <p align=center>
        <input type="submit" name="add" value="增加" onclick="javascript:return check();">&nbsp;&nbsp;
        <input type="button" name="cancel" value="取消" onclick="javascript:window.close()">
    </p>
</form>

</body>
</html>
