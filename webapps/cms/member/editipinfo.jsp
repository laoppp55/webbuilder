<%@ page import="java.util.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp?url=member/editGroup.jsp" );
        return;
    }
    if (!SecurityCheck.hasPermission(authToken,54))
    {
        request.setAttribute("message","无系统管理的权限");
        response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
        return;
    }

    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    IFtpSetManager ftpMgr = FtpSetting.getInstance();
    FtpInfo ftpinfo = ftpMgr.getFtpInfo(ID);
%>

<html>
<head>
    <title>修改主机</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
    <script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>

    <script language="javascript">
        function check()
        {
            if (editForm.sitename.value == "")
            {
                alert("请填写主机名称！");
                return false;
            }
            else if (editForm.root.value == "")
            {
                alert("请填写发布目录！");
                return false;
            }
            else if (editForm.pubway[1].checked)
            {
                if (editForm.ip.value == "")
                {
                    alert("请填写发布地址！");
                    return false;
                }
                else if (editForm.ftp_username.value == "" || editForm.ftp_passwd.value == "")
                {
                    alert("请填写FTP用户名和密码！");
                    return false;
                }
            }
            return true;
        }

        function setftptype() {
            editForm.ip.disabled = 0;
            editForm.ftp_username.disabled = 0;
            editForm.ftp_passwd.disabled = 0;

            var ipaddr = editForm.ip.value;
            var username = editForm.ftp_username.value;
            var passwd = editForm.ftp_passwd.value;
            var ftp_type=$('input:radio[name="pubway"]:checked').val();

            $.post("getFTPDefaultWorkingDirectory.jsp",{
                        username:encodeURI(username),
                        passwd:encodeURI(passwd),
                        ip:encodeURI(ipaddr),
                        ftptype:encodeURI(ftp_type)
                    },
                    function(data) {
                        alert(data);
                        posi = data.indexOf("dir:");
                        data = data.substring(posi+"dir:".length)
                        editForm.root.value = data;
                    },
                    'string'
            )
        }

        function local_publish(){
            editForm.ip.value = "localhost";
            editForm.ftp_username.value = "";
            editForm.ftp_passwd.value = "";
            editForm.ip.disabled = 1;
            editForm.ftp_username.disabled = 1;
            editForm.ftp_passwd.disabled = 1;
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form method="post" action="createnewipinfo.jsp" name="editForm" onsubmit="javascript:return check();">
    <input type="hidden" name="doUpdate" value="true">
    <input type="hidden" name="ID" value="<%=ID%>">
    <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark="#ffffec" borderColorLight="#5e5e00" align=center>
        <tr>
            <td colspan=2 width="100%" bgcolor="#006699" align="center" height="16"><font color="#FFFFFF"><b>所有选项必须正确填写</b></font></td>
        </tr>
        <tr height=40>
            <td width="25%" align=right height=32>主机名称：</td>
            <td width="75%">&nbsp;<input name="sitename" value="<%=ftpinfo.getSiteName()%>" size=20></td>
        </tr>
        <tr height=40>
            <td align=right>发布形式：</td>
            <td>
                <input type="radio" name="pubway" value="1" <%=(ftpinfo.getPublishway()==1)?"checked":""%> onclick="local_publish();">本地发布
                <input type="radio" name="pubway" value="0" <%=(ftpinfo.getFtptype()==0 && ftpinfo.getPublishway()==0)?"checked":""%>  onclick="javascript:setftptype();">FTP发布
                <input type="radio" name="pubway" value="2" <%=(ftpinfo.getFtptype()==1 && ftpinfo.getPublishway()==0)?"checked":""%>  onclick="javascript:setftptype();">SFTP发布
            </td>
        </tr>
        <tr height=40>
            <td align=right>发布目录：</td>
            <td>&nbsp;<input name="root" size="50" value="<%=ftpinfo.getDocpath()%>"></td>
        </tr>
        <tr height=40>
            <td align=right>发布地址：</td>
            <td>&nbsp;<input name="ip" value="<%=ftpinfo.getIp()%>"></td>
        </tr>
        <tr height=40>
            <td align=right>FTP用户名：</td>
            <td>&nbsp;<input name="ftp_username" maxlength="50" value="<%=(ftpinfo.getFtpuser()==null)?"":ftpinfo.getFtpuser()%>"></td>
        </tr>
        <tr height=40>
            <td align=right>FTP密码：</td>
            <td>&nbsp;<input type="password" name="ftp_passwd" maxlength="20" value="<%=(ftpinfo.getFtppwd()==null)?"":ftpinfo.getFtppwd()%>"></td>
        </tr>
        <tr height=40>
            <td align=right>主机类型：</td>
            <td>
                <select name="status">
                    <option value=1 <%=(ftpinfo.getStatus()==1)?"selected":""%>>用于WEB发布</option>
                    <option value=0 <%=(ftpinfo.getStatus()==0)?"selected":""%>>用于图片/多媒体发布</option>
                    <option value=2 <%=(ftpinfo.getStatus()==2)?"selected":""%>>用于WAP发布</option>
                </select>
            </td>
            <!--td><input type="radio" name="status" value=1 <%if(ftpinfo.getStatus()==1){%>checked<%}%>>用于文章发布<input type="radio" name="status" value=0 <%if(ftpinfo.getStatus()==0){%>checked<%}%>>用于其它发布</td-->
        </tr>
    </table>

    <p align="center">
        <input type="submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;
        <input type="button" value=" 取消 " onclick="window.close()">
    </p>
</form>

</body>
</html>