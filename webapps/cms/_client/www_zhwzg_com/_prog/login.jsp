<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    String username = ParamUtil.getParameter(request, "username");
    String password = ParamUtil.getParameter(request, "password", true);
    String msg = ParamUtil.getParameter(request, "msg");
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");
    String redirect = request.getHeader("REFERER");
    int usertype = ParamUtil.getIntParameter(request, "usertype", 1);

    if (redirect == null) redirect = "main.jsp";
    String errorMessage = "";
    int siteid = 1492;
    if (doLogin) {
        IUregisterManager uMgr = UregisterPeer.getInstance();
        try {
            Uregister uregister = uMgr.login(username, password, siteid);
            if (uregister != null) {                    //转向模板选择页面
                if (username.equals("振国人")) {
                    session.setAttribute("UserLogin", uregister);
                    response.sendRedirect("/nbjl/index.shtml");
                } else {
                    System.out.println("login success!!!==" + uregister.getMemberid());
                    session.setAttribute("UserLogin", uregister);
                    response.sendRedirect("/index.shtml");
                }
            } else {
                response.sendRedirect("/www_zhwzg_com/_prog/login.jsp");
            }
        } catch (Exception ue) {
            errorMessage = "用户名密码不符，重新输入!";
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>内部登录</title>
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            font-family: "新宋体";
            font-size: 14px;
            background-color: #FFFFFF;
        }

        ul li {
            float: left;
            line-height: 32px;
        }

        ul .lititle {
            width: 500px;
            list-style-type: square;
        }

        ul .litime {
            width: 200px;
            text-align: right;
            list-style-type: none;
        }

        a:link {
            text-decoration: none;
            color: #000000;
        }

        a:hover {
            text-decoration: none;
            color: #000000;
        }

        a:visited {
            text-decoration: none;
            color: #000000;
        }

        a:hover {
            text-decoration: none;
            color: #000000;
        }

        -->
    </style>
</head>

<body>
<div align="center">
    <table width="750" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td height="60" align="center" valign="bottom" style="font-size:28px; font-weight:bold; line-height:30px;">
                振国内部交流
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td align="center" valign="middle">
                <form action=login.jsp name=loginForm method=post onsubmit="return check();">
                    <input type=hidden name=doLogin value=true>
                    <input type="hidden" name="siteid" value="1492">
                    <table width="400" border="0" cellspacing="5" cellpadding="0">
                        <tr>
                            <td width="101" height="50" align="right" valign="middle">用户名：</td>
                            <td width="284" align="left" valign="middle"><label>
                                <input type="text" name="username"
                                       style="border:1px solid #CCCCCC; width:240px; height:20px; font-size:14px;padding-top:4px;background-color:#FFFFFF;"/>
                            </label></td>
                        </tr>
                        <tr>
                            <td height="50" align="right" valign="middle">密 码：</td>
                            <td align="left" valign="middle">
                                <label>
                                    <input type="password" name="password"
                                           style="border:1px solid #CCCCCC; width:240px; height:20px; font-size:14px;padding-top:4px;background-color:#FFFFFF;"/>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="14">&nbsp;</td>
                            <td align="left" valign="middle"><label>
                                <input type="submit" name="Submit" value="登录"
                                       style="width:66px; height:32px; font-size:14px;"/>
                            </label></td>
                        </tr>
                    </table>

            </td>
        </tr>
    </table>
</div>
</body>
</html>
