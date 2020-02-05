<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    String refer_url = request.getHeader("referer");
    int errcode = 0;

    if (doCreate) {
        refer_url = ParamUtil.getParameter(request,"referurl");
        String userid = ParamUtil.getParameter(request,"userid");
        String passwd = ParamUtil.getParameter(request,"pwd");
        ApplicationContext appContext = SpringInit.getApplicationContext();
        String password = null;
        if (appContext!=null) {
            UsersService ds = (UsersService)appContext.getBean("UsersService");
            Users us= ds.getByUserId(userid);
            //用户不存在
            if (us==null) {
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
                        auth.setUserid(us.getUSERID());
                        auth.setUsername(us.getNICKNAME());
                        auth.setUsertype(us.getUSERTYPE().intValue());
                        session.setAttribute("AuthInfo", auth);
                        response.sendRedirect(response.encodeRedirectURL(refer_url));
                    }
                }
            }
        } else {
            errcode = -4;
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>周边旅游行网</title>
    <link href="css/login.css" rel="stylesheet" type="text/css" />
    <script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script language="javascript">
        var errcode = <%=errcode%>;
        $(function (){
          if (errcode == -1){
             alert("user is not existing");
          } else if(errcode == -2) {
              alert("MD5 passwd error");
          } else if (errcode == -3) {
              alert("password id error");
          } else if (errcode == -4) {
              alert("can't to get the application context");
          }
        })
    </script>
</head>
<body>
<div class="box">
    <%@ include file="inc/top.shtml" %>
    <!-- top end  -->
    <div style="height:129px; width:954px;">&nbsp;</div>
    <div class="login_con">
        <form nam="loginform" action="login.jsp" method="post">
            <input type="hidden" name="doCreate" value="true">
            <input type="hidden" name="referurl" value="<%=refer_url%>">
            <div class="login_con_1"><img src="images/20150527_77.jpg" /></div>
            <div class="login_con_2">
                <div class="dl_1">
                    <div class="dl_1_1">登录</div>
                    <div class="dl_1_2">您还不是周边旅游行网会员？<a href="/_members/zhuce.jsp"><span>免费注册</span></a></div>
                </div>
                <div class="dl_2">
                    <div class="dl_1_1">账号</div>
                    <div class="dl_2_1"><input class="login_btn" name="userid" type="text" value=""/></div>
                    <div class="clear"></div>
                </div>
                <div class="dl_2">
                    <div class="dl_1_1">密码</div>
                    <div class="dl_2_1"><input class="login_btn" name="pwd" type="password" value=""/></div>
                    <div class="clear"></div>
                </div>
                <div class="dl_2">
                    <div class="dl_2_2"><input name="autolog" type="checkbox" value="1" /> 下次自动登录</div>
                    <div class="dl_2_3"><a href="findpwd.jsp">忘记密码？</a></div>
                    <div class="clear"></div>
                </div>
                <div class="dl_2">
                    <div class="dl_1_1" style="margin-top:10px;">&nbsp;</div>
                    <div style="margin-top:10px;"><input type="image" src="images/20150527_79.jpg" width="309" height="47" /></div>
                    <div class="clear"></div>
                </div>
            </div>
        </form>
        <div class="clear"></div>
    </div>
    <div style="height:153px; width:954px;">&nbsp;</div>
</div>
<!-- fonnter  -->
<%@ include file="inc/tail.shtml" %>
</body>
</html>
