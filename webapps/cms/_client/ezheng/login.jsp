<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>

<%
    String UserName = (String)request.getParameter("username");
    String Pass = (String)request.getParameter("password");
    String target_url = ParamUtil.getParameter(request,"refererurl");

    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
   IFeedbackManager feedMgr = FeedbackPeer.getInstance();
   int siteid = feedMgr.getSiteID(sitename);
    int Siteid = siteid;
    String fromurl = "";
    if (target_url == null || target_url == "") {
        fromurl = request.getHeader("REFERER") ;
        System.out.println("fromurl=" + fromurl);
    } else {
        fromurl = target_url;
    }

    Uregister ug = regMgr.login(UserName,Pass,Siteid);
    if(ug != null){
        //out.write("用户名密码正确!");
        session.setAttribute("UserLogin",ug);
        response.sendRedirect(fromurl + "?name="+ug.getMemberid());
    }
%>
<html>
    <head>
        <title>我的电子商务网站</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <meta content="bzwink" name="author" />
        <meta content="商品销售" name="description" />
        <meta content="化妆品、十自绣、首饰、工艺品" name="keywords" />
        <link href="/css/link.css" rel="stylesheet" />
        <script type="text/JavaScript">
        function check(){
            var userName = document.loginForm.username.value;
            var passWord = document.loginForm.password.value;
            if(userName==""){
                alert("请输入用户名！");
                return false;
            }
            if(passWord == ""){
                alert("请输入密码！");
                return false;
            }

            return true;
        }

        function zhuce(){
            var url = "register.jsp";
            window.location.href = url;
        }
    </script>
<style type="text/css">
<!--.biz_table{ border:1 dashed null;
 } 
.biz_table td{ font-size:12px; color:#000000; font-family:宋体 ; text-align:left;
}
.biz_table input{ font-size:18px;  size:12px;

}
biz_table img{ border:0;
}
-->
</style></head>
    <body leftmargin="0" topmargin="0" width="100%" marginwidth="0" marginheight="0">
        <%@include file="/www_chinabuy360_cn/include/top.shtml" %>
        <table cellspacing="0" cellpadding="0" width="1006" border="0">
            <tbody>
                <tr>
                    <td width="6" bgcolor="#ececec">&nbsp;</td>
                    <td width="32"><img alt="" src="/images/logo_leftgap02.gif" /></td>
                    <td align="center" width="220"><img height="23" alt="" width="210" border="0" src="/images/category_view.gif" /></td>
                    <td width="740" bgcolor="#ececec">
                    <table cellspacing="0" cellpadding="0" width="730" bgcolor="#ececec" border="0">
                        <tbody>
                            <tr>
                                <td width="10"><img height="30" alt="" width="10" border="0" src="/images/keyword_leftgap.gif" /></td>
                                <td width="730"><img height="10" alt="" width="2" align="absMiddle" border="0" src="/images/notice_bullet.gif" />&nbsp;<font style="FONT-SIZE: 12px"><strong>用户登录</strong></font></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td width="8" bgcolor="#ececec"></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr height="10">
                    <td></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" width="960" align="center" border="0">
            <tbody>
                <tr>
                    <td valign="top" width="230"></td>
                    <td valign="top" align="center" width="730">
                    <table cellspacing="1" cellpadding="1" width="200" summary="" border="1">
                        <tbody>
                            <tr>
                                <td><table  class="biz_table" border='0'>
<form name="loginForm" method="post" action="/www_chinabuy360_cn/_prog/login.jsp" onsubmit="return check();">
<input type="hidden" name="doLogin" value="true">
<input type="hidden" name="refererurl" value="<%=fromurl%>">
<TR><TD>用户名：</TD>
<TD><INPUT type="text" name=username value=""> </TD></TR>
<TR><TD>密&nbsp;&nbsp;码：</TD>
<TD><INPUT type="password" name="password"></TD></TR>
<TR><TD>&nbsp;</TD><TD align="right">
<DIV align="center" class="biz_table"><input type="submit" src="/images/login_go.gif" value="提交" /></DIV></TD></TR></form></TABLE>
<TABLE cellSpacing="0" cellPadding="5" width="100%" bgColor="#ffffff" border="0" class="biz_table">
<TR><TD><A href="/www_chinabuy360_cn/_prog/register.jsp" target="_blank">
注册新用户</A></TD>
<TD><a href=#>忘记密码</a></TD></TR></TABLE>
</td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr height="30">
                    <td></td>
                </tr>
            </tbody>
        </table>
        <%@include file="/www_chinabuy360_cn/include/low.shtml" %>
    </body>
</html>