<%@page import="com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.markManager.*,
                com.bizwink.cms.xml.*"
        contentType="text/html;charset=utf-8"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>
<html>
<head>
    <base target="_self" >
    <title>设置登录用户类别</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function setupusertype()
        {
            var retval = "";
            if (userlogintype.uid[0].checked == true) {
                retval = retval + "<input type=\"radio\" name=\"uid\" value=\"0\">普通用户";
            }
            if (userlogintype.uid[1].checked == true) {
                retval = retval + "<input type=\"radio\" name=\"uid\" value=\"1\">企业用户";
            }
            if (userlogintype.uid[2].checked == true) {
                retval = retval + "<input type=\"radio\" name=\"uid\" value=\"2\">内部用户";
            }
            if (userlogintype.uid[3].checked == true) {
                retval = retval + "<input type=\"radio\" name=\"uid\" value=\"3\">VIP用户";
            }
            parent.window.returnValue = retval;
            top.close();
        }
    </script>
</head>

<body>
<table>
    <tr>
        <td height="30" >
            &nbsp;
        </td>
    </tr>
</table>
<form name="userlogintype">
    <table cellspacing ="0" cellpadding="0">
        <tr>
            <td colspan="2" >
                <input type="checkbox" name="uid" value="0">普通用户
                <input type="checkbox" name="uid" value="1">企业用户
                <input type="checkbox" name="uid" value="2">内部用户
                <input type="checkbox" name="uid" value="3">VIP用户
            </td>
        </tr>
        <tr>
            <td height="30" >
                &nbsp;
            </td>
        </tr>
        <tr>
            <td>
                <input type="button" name="ok" value="提交" onclick="javascript:setupusertype();">
            </td>
            <td>
                <input type="button" name="cancel" value="返回" onclick="javascript:top.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>