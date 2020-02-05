<%@ page import="java.sql.*,
                 java.util.*,
                 java.io.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html>
<head>
    <title>定义表单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script language="javascript">
        function tijiao()
        {
            /*var name = document.getElementById("name").value;
            var pass = document.getElementById("pass").value;

            for(var i=0; i<regform.domaintype.length; i++) {
                if (regform.domaintype[i].checked) {
                    value = regform.domaintype[i].value;
                    break;
                }
            }

            if (value == 1) {
                if (sitename == null || sitename == "") {
                    alert("用户选择自定义域名，域名不能为空");
                    return false;
                }
            } else {
                sitename = document.getElementById("coositename").value;
            }*/
            window.returnValue = "hello world";
            window.close();
        }
    </script>
</head>
<body>
<table height="100%" width="100%">
    <form name="df">
        <tr>
            <td>
                <table cellspacing="0" cellpadding="0" width="80%" border="0" valign="top" align="center">
                    <tr>
                        <td>
                            <span fckLang="DlgFormName">Name</span><br>
                            <input style="WIDTH: 100%" type="text" name="thename" id="txtName">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span fckLang="DlgFormMethod">Method</span><br>
                            <select name="themethod" id="txtMethod">
                                <option value="get" selected>GET</option>
                                <option value="post">POST</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <input  type="button" name="ok" value="提交"  onclick="javascript:tijiao()">
                            <input  type="button" name="cancel" value="取消" onclick="javascript:window.close()">
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </form>
</table>
</body>
</html>
