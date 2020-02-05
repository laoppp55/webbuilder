<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="com.bizwink.po.Users" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-1-30
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        UsersService usersService = (UsersService)appContext.getBean("usersService");
        String userid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "userid"));
        //获取用户信息
        Users us= usersService.getUserinfoByUserid(userid);
        //如果用户信息不为空，调用发送邮件程序
        if (us != null) {

        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
    <title>全球治理和企业发展数据平台</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <meta content="IE=8" http-equiv="X-UA-Compatible" />
    <link rel="stylesheet" type="text/css" href="/css/common.css" />
    <link rel="stylesheet" type="text/css" href="/css/child.css" />
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
        <div class="loction">当前位置： <a href="/">首页</a> &gt; <a href="#">找回密码</a></div>
        <div class="list_box">
            <div class="result">
                <form name="theform" method="post" action="findPwd.jsp" onsubmit="return tijiao(theform)">
                    <input type="hidden" name="doFind" value="true">
                    <div class="result_title">找回密码</div>
                    <div class="zhuce">
                        <div class="zhuce_1">
                            <div class="txt">输入登录账号：</div>
                            <div class="input"><input name="userid" type="text" /></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_btn"><input type="submit" name="ok" value="找回密码" /></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="clear"></div><!--#include virtual="/inc/bottom.shtml"--></div>
</body>
</html>