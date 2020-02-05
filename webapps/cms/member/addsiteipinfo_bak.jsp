<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.register.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp?url=member/createnewsite.jsp" );
        return;
    }

    int siteID = ParamUtil.getIntParameter(request, "siteid", 0);
    IRegisterManager registerMgr = RegisterPeer.getInstance();
    String sitename = registerMgr.getSite(siteID).getSiteName();
%>

<html>
<head>
    <title>增加主机</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
    <script language="javascript">
        function remote_publish()
        {
            addForm.ip.disabled = 0;
            addForm.ip.value ="";
            addForm.ftp_username.disabled = 0;
            addForm.ftp_passwd.disabled = 0;
        }

        function local_publish()
        {
            addForm.ip.value = "localhost";
            addForm.ftp_username.value = "";
            addForm.ftp_passwd.value = "";
            addForm.ip.disabled = 1;
            addForm.ftp_username.disabled = 1;
            addForm.ftp_passwd.disabled = 1;
        }

        function check()
        {
            if (addForm.sitename.value == "")
            {
                alert("请填写主机名称！");
                return false;
            }
            else if (addForm.root.value == "")
            {
                alert("请填写发布目录！");
                return false;
            }
            else if (addForm.pubway[1].checked)
            {
                if (addForm.ip.value == "")
                {
                    alert("请填写发布地址！");
                    return false;
                }
                else if (addForm.ftp_username.value == "" || addForm.ftp_passwd.value == "")
                {
                    alert("请填写FTP用户名和密码！");
                    return false;
                }
            }
            return true;
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form method="post" action="createnewipinfo.jsp" name="addForm" onsubmit="return check();">
    <input type="hidden" name="doCreate" value="true">
    <input type="hidden" name="siteID" value="<%=siteID%>">
    <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00 align=center>
        <tr>
            <td colspan=2 width="100%" bgcolor="#006699" align="center" height="16"><font color="#FFFFFF"><b>所有选项必须正确填写</b></font></td>
        </tr>
        <tr height=32>
            <td width="25%" align=right>主机名称：</td>
            <td width="75%">&nbsp;<input name="sitename" size=20 value="<%=sitename%>"></td>
        </tr>
        <tr height=32>
            <td align=right>发布形式：</td>
            <td>
                <input type="radio" name="pubway" value="1" onclick="local_publish();" checked>本地发布
                <input type="radio" name="pubway" value="0" onclick="remote_publish();">FTP发布
            </td>
        </tr>
        <tr height=32>
            <td align=right>发布目录：</td>
            <td>&nbsp;<input name="root" maxlength="500" size="20"></td>
        </tr>
        <tr height=32>
            <td align=right>发布地址：</td>
            <td>&nbsp;<input name="ip" disabled value="localhost"></td>
        </tr>
        <tr height=32>
            <td align=right>FTP用户名：</td>
            <td>&nbsp;<input name="ftp_username" maxlength="50" disabled></td>
        </tr>
        <tr height=32>
            <td align=right>FTP密码：</td>
            <td>&nbsp;<input type="password" name="ftp_passwd" maxlength="20" disabled></td>
        </tr>
        <tr height=32>
            <td align=right>主机类型：</td>
            <td>
                <select name="status" style="width: 135; height: 251">
                    <option value=1>用于WEB发布</option>
                    <option value=0>用于图片/多媒体发布</option>
                    <option value=2>用于WAP发布</option>
                </select>
            </td>
            <!--input type="radio" name="status" value=1 checked>用于文章发布<input type="radio" name="status" value=0>用于其它发布</td-->
        </tr>
    </table>

    <p align="center">
        <input type="submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;
        <input type="button" value=" 取消 " onclick="window.close();">
    </p>
</form>

</body>
</html>
