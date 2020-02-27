<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-3-11
  Time: 下午10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    int errcode = 0;
    Users user = null;
    int siteid = 0;
    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            String pwd = null;
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            user = usersService.getByUserId(username);
            siteid = user.getSITEID().intValue();
        }
    } else {
        response.sendRedirect("/_members/login.jsp");
    }
%>

<html>
<head>
    <title>文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <script type="text/javascript" src="http://www.gugulx.com/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="http://www.gugulx.com/js/jquery.form.js"></script>
    <script type="text/javascript" src="http://www.gugulx.com/js/jquery.uploadify.v2.1.4.js"></script>
    <script language=javascript>
        $(function () {
            $("#btnsubmit").click(function () {
                var filename = $("#fileToUpload").val();
                if (filename == "") {
                    alert("请选择上传文件！");
                    return false;
                }

                if (filename.indexOf(".doc") == -1 && filename.indexOf(".docx")==-1) {
                    alert("上传文件必须是WORD文件！");
                    return false;
                }

                var getvalflag = false;
                var retval = "";
                var ajaxFormOption = {
                    type: "post",                                                     //提交方式
                    dataType: "html",                                                //数据类型
                    data: {
                        siteid:<%=siteid%>
                    },                                                      //自定义数据参数，视情况添加
                    async: false,                                                     //使用同步的方式,true为异步方式
                    url: "saveUploadImg.jsp?doCreate=true",                       //请求url
                    success: function (data) {                                         //提交成功的回调函数
                        window.returnValue=data;
                        window.close();
                    }
                };

                //不需要submit按钮，可以是任何元素的click事件
                $("#form1").ajaxSubmit(ajaxFormOption);

                if (getvalflag == false)
                    return false;
                else {
                    window.returnValue=retval;
                    window.close();
                }
            });
        });
    </script>
</head>
<body bgcolor="#cccccc">
<form enctype="multipart/form-data" id="form1">
    <table width="100%" align=center border=0>
        <tr height=20>
            <td width="20%" align=right>上传文件：</td>
            <td width="80%"><input type=file id="fileToUpload" name="file1" size=30></td>
        </tr>
        <tr height=40>
            <td align=center colspan=2>
                <input type="button" id="btnsubmit" value="  上传  ">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" id="btncancel" value="  取消  "   onclick="top.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
