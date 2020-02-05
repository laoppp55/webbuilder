<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*"
        contentType="text/html;charset=utf-8"
%>

<%
    request.setCharacterEncoding("utf-8");
    String username = ParamUtil.getParameter(request, "username");
    String password = ParamUtil.getParameter(request, "password", true);
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");
    String errorMessage = "&nbsp;";

    if (doLogin) {
        String yzcode = ParamUtil.getParameter(request, "yzmcode");
        String yzcode_from_session = (String)session.getAttribute("randnum");
        IAuthManager authMgr = AuthPeer.getInstance();
        try{
            //获取用户端的IP地址
            String user_ip = null;
            if (request.getHeader("x-forwarded-for") == null) {
                user_ip = request.getRemoteAddr();
            } else {
                user_ip = request.getHeader("x-forwarded-for");
            }

            String firstLoginPage = null;

            if(username.equals("admin"))
                firstLoginPage = "managesites.jsp";
            else
                firstLoginPage = "index1.jsp";

            Auth authToken = authMgr.getAuth(username, password,user_ip);
            if (yzcode!=null) {
                if (yzcode.equalsIgnoreCase(yzcode_from_session)) {
                    if (authToken != null) {
                        int siteid = authToken.getSiteID();
                        session.setAttribute("CmsAdmin", authToken);
                        session.setMaxInactiveInterval(60*60*1000);
                        int modelnum = authMgr.getTemplateNum(siteid);
                        if (modelnum == 0 && !username.equals("admin"))  {                    //转向模板选择页面
%>
<script type="text/javascript">
    var ret = confirm("选择已经存在的模板？");
    if (ret)
        window.location="register/webindex.jsp";
    else
    <%response.sendRedirect(firstLoginPage);%>
</script>
<%
                        } else                                                                  //转向登录成功页面
                            response.sendRedirect(firstLoginPage);
                    } else {
                        errorMessage = "<font color=red>登陆失败!请重新输入用户名和密码!</font>";
                    }
                } else
                    errorMessage = "<font color=red>验证码输入错，请重新输入验证码!</font>";
            } else {
                errorMessage = "<font color=red>验证码为空，请输入验证码!</font>";
            }
        } catch (UnauthedException e){
            errorMessage = "<font color=red>登陆失败!请重新输入用户名和密码!</font>";
        }
        doLogin = false;
    }
%>

<html>
<head>
    <title>内容管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet href="css/cms_css.css" style="text:css">
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            background-color: #c3c3c3;
        }
        -->
    </style>
    <script src="js/md5-min.js" type="text/javascript"></script>
    <script src="js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        function change_yzcodeimage() {
            $("#yzImageID").attr("src","image.jsp?temp=" + Math.random());
        }

        function tijiao() {
            var username = loginForm.username.value;
            if (username == ""){
                alert("用户名不能为空");
                return false;
            }

            if (username.length < 2){
                alert("用户名长度必须2位以上");
                return false;
            }

            var passwd = loginForm.password.value;
            if (passwd == ""){
                alert("用户口令不能为空");
                return false;
            }

            if (username!="admin") {
                /*if (passwd.length < 8){
                    alert("用户密码长度必须8位以上");
                    return false;
                }

                var regex = /[0-9]/;
                if(!regex.test(passwd)){
                    alert("密码必须包含数字");
                    return false;
                }

                regex = /[a-z]/;
                if(!regex.test(passwd)){
                    alert("密码必须包含小写字母");
                    return false;
                }

                regex = /[A-Z]/;
                if(!regex.test(passwd)){
                    alert("密码必须包含大写字母");
                    return false;
                }

                regex = /\W/;
                if(!regex.test(passwd)){
                    alert("密码必须包含特殊字符");
                    return false;
                }*/
            } else {
                if (passwd.length < 2){
                    alert("admin用户密码长度必须2位以上");
                    return false;
                }
            }

            //loginForm.password.value = hex_md5(passwd);
            //loginForm.username.value = hex_md5(username);
            //return true;

            //htmlobj=$.ajax({url:"getLoginStatus.jsp?uid=" + username,cache:false,async:false});
            statusval = 0;   //htmlobj.responseText;

            if (statusval == 0) {
                loginForm.password.value = hex_md5(passwd);
                return true;
            } else {
                alert("该账号已经在其他机器登录，请从其他机器退出");
                return false;
            }
        }
    </script>
</head>

<body  style="background-color:#f0f0f0">
<div class="login">
    <div class="login_left"><img src="images/login_1.jpg" width="373" height="489" /></div>
    <form action="index.jsp" name="loginForm" method="POST" onsubmit="return tijiao();">
        <input type=hidden name=doLogin value=true>
        <div class="login_right">
            <div class="login_tilte">登录</div>
            <div class="tips"><%=(errorMessage!=null)?errorMessage:""%></div>
            <div class="login_list">
                <div class="txt">用户名：</div>
                <div class="input"><input name="username" type="text" /></div>
            </div>
            <div class="login_list">
                <div class="txt">密码：</div>
                <div class="input"><input name="password" type="password" size="20" autocomplete="new-password"></div>
            </div>
            <div class="login_yzm">
                <div class="txt">验证码：</div>
                <div class="input"><input name="yzmcode" type="text" /></div>
                <span><img src="image.jsp" id="yzImageID" name="yzcodeimage" width="120" height="48" align="absmiddle"/></span>
                <span><a href="javascript:change_yzcodeimage();">看不清，换一张</a></span>
            </div>
            <div class="button">
                <input name="login" type="submit"  value="登录"/>
            </div>
        </div>
    </form>
</div>
<script language="javascript">document.forms[0].username.focus();</script>
</body>
</html>
