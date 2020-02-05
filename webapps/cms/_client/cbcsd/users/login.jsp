<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.security.Auth" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%
    boolean doLogin = ParamUtil.getBooleanParameter(request,"doLogin");
    String refer_url = request.getHeader("referer");
    int errcode = 0;

    System.out.println("doLogin==" + doLogin);

    if (doLogin) {
        refer_url = ParamUtil.getParameter(request,"referurl");
        String userid = filter.excludeHTMLCode(ParamUtil.getParameter(request,"username"));
        String passwd = filter.excludeHTMLCode(ParamUtil.getParameter(request,"pwd"));
        ApplicationContext appContext = SpringInit.getApplicationContext();
        String password = null;
        if (appContext!=null) {
            System.out.println("userid==" + userid);
            System.out.println("passwd==" + passwd);

            UsersService usersService = (UsersService)appContext.getBean("usersService");
            //获取用户信息
            Users us= usersService.getUserinfoByUserid(userid);
            if (us==null) {
                us= usersService.getUserinfoByEmail(userid);
                if (us == null)
                    us= usersService.getUserinfoByMphone(userid);
            }

            if (us == null) {
                errcode = -1;
            } else {
                try {
                    password = Encrypt.md5(passwd.getBytes());
                } catch (Exception e) {
                    errcode = -2;
                }
                if (password!=null) {
                    //用户口令错
                    if (!password.equalsIgnoreCase(us.getUSERPWD())) {
                        errcode = -3;
                    } else {
                        errcode = 1;
                        Auth auth = new Auth();
                        auth.setUid(us.getID().intValue());
                        auth.setSiteid(us.getSITEID().intValue());
                        auth.setUserid(us.getUSERID());
                        auth.setUsername(us.getNICKNAME());
                        auth.setUsertype(us.getUSERTYPE().intValue());
                        session.setAttribute("AuthInfo", auth);

                        //设置在gugulx.com的二级域中都可以访问的cookie
                        /*Gson gson = new Gson();
                        String jsondata = gson.toJson(auth);
                        SecurityUtil securityUtil = new SecurityUtil();
                        Cookie loginCookie = new Cookie("AuthInfo_cookie",securityUtil.encrypt(jsondata,null));
                        loginCookie.setDomain("cbcsd.org.cn");
                        loginCookie.setPath("/");
                        loginCookie.setMaxAge(-1);
                        response.addCookie(loginCookie);*/
						Cookie userCookie = new Cookie("username",us.getUSERID());
						userCookie.setPath("/");
						response.addCookie(userCookie);
                    }
                }
            }
        } else {
            System.out.println("环境初始化失败");
            errcode = -5;
        }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
    <title>全球治理和企业发展数据平台--用户登录</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <meta content="IE=8" http-equiv="X-UA-Compatible" />
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/css/child.css" />
    <script src="../js/jquery-1.10.2.min.js" type="text/javascript"></script>
    <script language="javascript">
        var falg = false;
        var sucess = "";
        var errcode = <%=errcode%>;

        $(document).ready(function(){
            if (errcode == -1 || errcode == -2 || errcode == -3) {
                $("#usermsg").html("用户名或者密码错");
                $("#usermsg").css({color:"red"});
            } else if (errcode == -5) {
                $("#usermsg").html("运行环境初始化错误，请联系客服人员");
                $("#usermsg").css({color:"red"});
            } else if (errcode > 0) {
                alert("用户登录成功，点击确认按钮关闭登录页面");
                window.location.href="/index.shtml";
            }
        });
    </script>
</head>
<body xmlns="">
<!--head begin-->
<div><!--#include virtual="/inc/top.shtml"--></div>
<div class="menu">
    <div class="menu_in"><!--#include virtual="/inc/menu.shtml"--></div>
</div>
<!--head end-->
<div class="containter">
    <div class="box">
        <div class="loction">当前位置： <a href="/">首页</a> &gt; <a href="#">登录</a></div>
        <div class="list_box">
            <div class="result">
                <form name="loginform" method="post" action="login.jsp" onsubmit="return tijiao(loginform)">
                    <input type="hidden" name="doLogin" value="true">
                    <div class="result_title">登录</div>
                    <div class="zhuce">
                        <div class="zhuce_1">
                            <div class="txt">用户名：</div>
                            <div class="input"><input name="username" type="text" /></div>
                        </div>
                        <div class="hint"><span class="red">忘记填写用户名</span></div>
                        <div class="zhuce_1">
                            <div class="txt">密 码：</div>
                            <div class="input"><input name="pwd" type="password" /></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_btn"><input type="submit" name="" value="登录" />&nbsp;&nbsp;<a href="findPwd.jsp">找回密码</a>&nbsp;&nbsp;<a href="userreg.jsp">注册</a></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="clear"></div><!--#include virtual="/inc/bottom.shtml"--></div>
</body>
</html>