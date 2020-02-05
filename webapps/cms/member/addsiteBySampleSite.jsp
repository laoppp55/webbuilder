<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp?url=member/createnewsite.jsp");
        return;
    }

    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }
%>

<html>
<head>
    <title>站点增加</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language="javascript">
        function check()
        {
            var pattern = /^([.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
            var SiteName = document.RegForm.SiteName.value.ltrim().rtrim()

            if (document.RegForm.userid.value.ltrim().rtrim() == "")
            {
                alert("用户登录帐号不能为空！");
                return false;
            }
            else if (/[^\x00-\xff]/g.test(document.RegForm.userid.value.ltrim().rtrim()))
            {
                alert("用户登录帐号不能含有汉字");
                return false;
            }
            else if (document.RegForm.password1.value == "")
            {
                alert("用户密码不能为空！");
                return false;
            }
            else if (document.RegForm.password2.value == "")
            {
                alert("确认密码不能为空！");
                return false;
            }
            else if (document.RegForm.password1.value != document.RegForm.password2.value)
            {
                alert("两次输入的密码不相同！");
                return false;
            }
            else if (document.RegForm.password1.value.length < 5)
            {
                alert("密码长度要求在5位或5位以上！");
                return false;
            }
            else if (SiteName == "")
            {
                alert("域名不能为空！");
                return false;
            }
            else if (SiteName.substring(0, 4).toLowerCase() == "http" || SiteName.indexOf(".") == -1)
            {
                alert("域名格式不正确！正确格式如：www.bizwink.com.cn");
                return false;
            }
            else
            {
                return true;
            }
        }

        function ltrim()
        {
            return this.replace(/ +/, "");
        }
        String.prototype.ltrim = ltrim;

        function rtrim()
        {
            return this.replace(/ +$/, "");
        }
        String.prototype.rtrim = rtrim;

        function IsNum(str)
        {
            var len = str.length;
            if (len == 0) return true;
            var bool = true;
            for (var i = 0; i < len; i++)
            {
                if (!(parseInt(str.substring(i, i + 1)) >= 0 && parseInt(str.substring(i, i + 1)) <= 9))
                {
                    bool = false;
                    break;
                }
            }
            if (!bool || str == "")
                return false;
            else
                return true;
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form method="post" action="createnewsite.jsp" name="RegForm" onsubmit="javascript:return check();">
    <input type=hidden name="doCreate" value="true">
    <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00
           align=center>
        <tr>
            <td colspan=2 width="100%" bgcolor="#006699" align="center" height="16"><font
                    color="#FFFFFF"><b>所有选项必须正确填写</b></font></td>
        </tr>
        <tr height=32>
            <td width="38%" align="right">用户名：</td>
            <td width="62%">&nbsp;<input type="text" name="userid" maxlength="20" size="15">
                <br><font color=red>(用于登录的帐号，由字母和数字组成)</font></td>
        </tr>
        <tr height=32>
            <td align="right">密&nbsp; 码：</td>
            <td>&nbsp;<input type="password" name="password1" size="15" maxlength="20">
                <br><font color=red>(用于登录的密码，要求5位以上)</font></td>
        </tr>
        <tr height=32>
            <td align="right">确认密码：</td>
            <td>&nbsp;<input type="password" name="password2" size="15" maxlength="20"></td>
        </tr>
        <tr height=32>
            <td align="right">域&nbsp; 名：</td>
            <td>&nbsp;<input maxlength="50" name="SiteName" size="30"></td>
        </tr>
        <tr height=32>
            <td align="right">样式脚本文件存储方式：</td>
            <td><input type="radio" value="0" name="cssjsdir" checked>根目录images下
                <input type="radio" value="1" name="cssjsdir">分别存储
            </td>
        </tr>
        <tr height=32>
            <td align="right">图像存储方式：</td>
            <td><input type="radio" value="0" name="pic" checked>根目录images下
                <input type="radio" value="1" name="pic">各栏目images下
            </td>
        </tr>
        <tr height=32>
            <td align="right">支持繁体：</td>
            <td><input type="radio" value="0" name="tcflag" checked>否&nbsp;
                <input type="radio" value="1" name="tcflag">是
            </td>
        </tr>
        <tr height=32>
            <td align="right">支持WAP：</td>
            <td><input type="radio" value="0" name="wapflag" checked>否&nbsp;
                <input type="radio" value="1" name="wapflag">是
            </td>
        </tr>
        <tr height=32>
            <td align="right">文章发布方式：</td>
            <td><input type="radio" value="0" name="pubflag" checked>手动&nbsp;
                <input type="radio" value="1" name="pubflag">自动
            </td>
        </tr>
        <tr height=32>
            <td align="right">首页扩展名：</td>
            <td>&nbsp;<select name=extname size=1 class=tine>
                <option value="html">html</option>
                <option value="htm">htm</option>
                <option value="jsp">jsp</option>
                <option value="asp">asp</option>
                <option value="shtm">shtm</option>
                <option value="shtml">shtml</option>
                <option value="php">php</option>
                <option value="wml">wml</option>
            </select>
            </td>
        </tr>
    </table>
    <p align="center"><input type="submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;
        <input type="button" value=" 取消 " onclick="window.close()"></p>
</form>

</body>
</html>