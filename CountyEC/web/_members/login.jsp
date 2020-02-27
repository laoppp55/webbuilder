<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="com.bizwink.dubboservice.service.SiteinfoService" %>
<%@ page import="com.bizwink.po.Siteinfo" %>
<%
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    String refer_url = request.getHeader("referer");

    int errcode = 0;

    if (doCreate) {
        refer_url = ParamUtil.getParameter(request,"referurl");
        String userid = filter.excludeHTMLCode(ParamUtil.getParameter(request,"userid"));
        String passwd = filter.excludeHTMLCode(ParamUtil.getParameter(request,"pwd"));
        ApplicationContext appContext = SpringInit.getApplicationContext();
        String password = null;
        if (appContext!=null) {
            UsersService ds = (UsersService)appContext.getBean("UsersService");
            SiteinfoService siteinfoService = (SiteinfoService)appContext.getBean("SiteinfoService");
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
                        Siteinfo siteinfo = siteinfoService.getSiteinfoByID(us.getSITEID());
                        errcode = 1;
                        Auth auth = new Auth();
                        auth.setUserid(us.getUSERID());
                        auth.setUsername(us.getNICKNAME());
                        if (siteinfo != null) {
                            if (siteinfo.getSAMSITEID().intValue() == 2991)
                                auth.setUsertype(1);                             //农家乐
                            else if (siteinfo.getSAMSITEID().intValue() == 2992)
                                auth.setUsertype(2);                             //采摘园
                            //else if (siteinfo.getSAMSITEID().intValue() == 2993)
                            //    auth.setUsertype(3);                             //景区
                            else if (siteinfo.getSAMSITEID().intValue() == 4592)
                                auth.setUsertype(4);                             //美丽乡村
                        }
                        else
                            auth.setUsertype(0);                             //普通游客
                        session.setAttribute("AuthInfo", auth);

                        //设置在gugulx.com的二级域中都可以访问的cookie
                        SecurityUtil securityUtil = new SecurityUtil();
                        Cookie loginCookie = new Cookie("AuthInfo_cookie",securityUtil.encrypt(us.getUSERID() + "-" + auth.getUsertype(),null));
                        loginCookie.setDomain("gugulx.com");
                        loginCookie.setPath("/");
                        loginCookie.setMaxAge(-1);
                        response.addCookie(loginCookie);

                        if (refer_url==null) {
                            response.sendRedirect(response.encodeRedirectURL("http://" + us.getUSERID() + ".gugulx.com"));
                        }else{
                            response.sendRedirect(response.encodeRedirectURL(refer_url));
                        }
                    }
                }
            }
        } else {
            System.out.println("环境初始化失败");
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
            if (errcode == -1 || errcode == -3){
                alert("用户名或者口令错");
            } else if(errcode == -2) {
                alert("MD5加密口令出错");
            } else if (errcode == -4) {
                alert("获取应用初始化环境错");
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
            <input type="hidden" name="referurl" value="<%=(refer_url==null)?"":refer_url%>">
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
