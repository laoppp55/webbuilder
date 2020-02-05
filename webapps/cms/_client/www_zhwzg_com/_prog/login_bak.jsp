<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
    System.out.println("username=" + UserName);
    System.out.println("pass=" + Pass);
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
        if(fromurl.equals("undefined"))
        {
             fromurl="/index.shtml";  
        }
        if(fromurl.indexOf("register.jsp")!=-1)
        {
             fromurl="/index.shtml";  
         }
         if(fromurl.indexOf("login.jsp")!=-1)
         {
            fromurl="/index.shtml";     
         } 
        response.sendRedirect(fromurl + "?name="+ug.getMemberid());
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>珠海振国肿瘤康复医院</title><script src="/_sys_js/tanchuceng.js" type="text/javascript"></script>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link href="/css/wzgstyle.css" type="text/css" rel="stylesheet" />
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
    <body xmlns="">
        <table cellspacing="0" cellpadding="0" width="1000" align="center" background="/images/2010527wzg-bg.gif" border="0" style="background-repeat: repeat-y">
            <tbody>
                <tr>
                    <td valign="top" align="left" width="233">
                    <table cellspacing="0" cellpadding="0" width="233" border="0">
                        <tbody>
                            <tr>
                                <td valign="top" align="left"><img height="433" alt="" width="128" src="/images/2010527wag-logo2.jpg" /></td>
                                <td width="2"><img height="434" alt="" width="2" src="/images/2010527wzg-line2.gif" /></td>
                                <td valign="top" align="left" width="103"><%@include file="/www_shwzg_com/inc/menu.shtml" %></td>
                            </tr>
                        </tbody>
                    </table><%@include file="/www_shwzg_com/inc/expert.shtml" %></td>
                    <td valign="top" align="left" width="767"><%@include file="/www_shwzg_com/inc/weather.shtml" %><table cellspacing="0" cellpadding="0" width="767" border="0">
                        <tbody>
                            <tr>
                                <td valign="top" align="left"><img height="390" alt="" width="767" src="/images/2010527wag-flash1.jpg" /></td>
                            </tr>
                            <tr>
                                <td height="30">&nbsp;</td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing="0" cellpadding="0" width="767" border="0">
                        <tbody>
                            <tr>
                                <td><img height="1" alt="" width="13" src="/images/space.gif" /></td>
                                <td valign="bottom" align="left" height="18"><span class="titlered">登录</span> <span class="entitlered">Login</span></td>
                            </tr>
                            <tr>
                                <td width="13"><img height="1" alt="" width="13" src="/images/space.gif" /></td>
                                <td><img height="8" alt="" width="740" src="/images/2010527wag-tbg.jpg" /></td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellspacing="0" cellpadding="0" width="767" background="/images/2010527wag-tbg2.jpg" border="0" style="background-repeat: no-repeat">
                        <tbody>
                            <tr>
                                <td width="13"><img height="1" alt="" width="13" src="/images/space.gif" /></td>
                                <td valign="top" align="left" width="754" height="195">
                                <table cellspacing="0" cellpadding="0" width="734" border="0">
                                    <tbody>
                                        <tr>
                                            <td><img height="12" alt="" src="/images/space.gif" /></td>
                                        </tr>
                                        <tr>
                                            <td><table    class="biz_table">
        <table class="biz_table" border="0">
            <form action="/www_shwzg_com/_prog/login.jsp" method="post" onsubmit="return check();" name="loginForm">
                <tbody>
                    <tr>
                        <td>用户名：</td>
                        <td class="text14px" valign="middle" align="left" width="628"><input class="textbg" style="width: 200px" name="username" type="text" /></td>
                    </tr>
                    <tr>
                        <td>密&nbsp;&nbsp;码：</td>
                        <td valign="middle" align="left"><label><input class="textbg" type="password" style="width: 120px" name="password" /> </label></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right">
                        <div class="biz_table" align="center"><input type="image" src="/images/2010527wag-9.jpg" value="提交" /></div>
                        </td>
                    </tr>
                </tbody>
            </form>
        </table>
    </td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </tbody>
                    </table><%@include file="/www_shwzg_com/inc/low.shtml" %></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>