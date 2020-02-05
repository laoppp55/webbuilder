<%@ page import = "java.util.*,
                   com.bizwink.cms.util.*,
                   com.bizwink.cms.audit.*,
                   com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if( authToken == null )
    {
        response.sendRedirect( "login.jsp" );
        return;
    }

    String emailaccount= authToken.getEmailaccount();
    String emailpasswd = authToken.getEmailpasswd();

%>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=style/global.css>
    <script language="javascript" src="extjs/ext-3.3.1/adapter/ext/ext-base.js"></script>
    <script language="javascript" src="extjs/ext-3.3.1/ext-all.js"></script>
    <SCRIPT language=JavaScript1.2>
     function init()
     {
        document.logon.username.focus();
     }

    function formsubmit()
    {
        var logform=document.logon;

        if(logform.username.value.length == 0)
        {
                alert("请填写您的信箱名，谢谢！");
                logform.username.focus();
                return false;
        }
        if(logform.password.value.length == 0)
        {
                alert("请填写您的口令，谢谢！");
                logform.password.focus();
                return false;
        }
        //if(logform.sslchk.checked)
        //{
        //        logform.action="https://mail.cpip.net.cn/cgi-bin/eqwebmail";
        //}
        //else
        //{
        logform.action="http://mail.cpip.net.cn/cgi-bin/eqwebmail";
        //}
        return true;
    }
    </SCRIPT>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#000000">
        <td height="2"></td>
    </tr>
</table>

<%
    String[][] titlebars = {
            { "首页", "" }
    };
    String[][] operations=null;
%>
<%@ include file="inc/titlebar.jsp" %>
<table width="172" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="images/index_19.jpg" width="172" height="32"></td>
    </tr>
</table>
<table width="172" height="125" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" valign="middle" background="images/index_23.jpg">
            <form method="post" name="logon" onsubmit="return(formsubmit());">
               <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="55" height="30" align="center" valign="middle" class="STYLE2">用户名：</td>
                        <td>
                            <input style="COLOR: #008080; BACKGROUND-COLOR: #ffffff" size="14" name="username" value=<%=emailaccount%> />
                            <input type="hidden" name="logindomain" value="capinfo.com.cn">
                        </td>
                    </tr>
                    <tr>
                        <td width="55" height="30" align="center" valign="middle" class="STYLE2">密　码：</td>
                        <td>
                            <input name="password" type="password" style="COLOR: #008080; BACKGROUND-COLOR: #ffffff" value="" size="14"  value="<%=emailpasswd%>" />
                            <input name="sameip" value="" type="hidden">
                        </td>
                    </tr>
                    <tr>
                        <td height="30" colspan="2" align="center" valign="middle"><input type="submit" onClick="return formsubmit();"  name="Submit" value="登录">
                            <input name="close" type="reset" value="取消" >
                        </td>
                    </tr>
                </table>
            </form>
            <table width="96%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="left" valign="middle"><script language="javascript">formsubmit();</script></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table width="172" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="images/index_25.jpg" width="172" height="8"></td>
    </tr>
 </table> 
</body>
</html>