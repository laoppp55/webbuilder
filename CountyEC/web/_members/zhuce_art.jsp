<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.util.Encrypt" %>
<%@ page import="com.bizwink.util.filter" %>
<%
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    String getSessionCode = (String) session.getAttribute("randnum");
    int errcode = 0;

    if (doCreate) {
        Users user = new Users();
        String userid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "username"));
        String passwd = filter.excludeHTMLCode(ParamUtil.getParameter(request,"pwd"));
        String email= filter.excludeHTMLCode(ParamUtil.getParameter(request, "email"));
        String mphone = filter.excludeHTMLCode(ParamUtil.getParameter(request, "mphone"));
        boolean agreeflag = ParamUtil.getCheckboxParameter(request,"agreement");
        String yzcode = filter.excludeHTMLCode(ParamUtil.getParameter(request, "yzcode"));

        System.out.println("agreeflag===" + agreeflag);
        System.out.println(yzcode + "===" + getSessionCode);

        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            if (agreeflag && yzcode.equalsIgnoreCase(getSessionCode)) {
                user.setUSERID(userid);
                user.setUSERPWD(Encrypt.md5(passwd.getBytes()));
                user.setEMAIL(email);
                user.setMPHONE(mphone);
                user.setUSERTYPE(BigDecimal.valueOf(1));        //1代表普通用户    0-代表企业用户
                user.setSITEID(BigDecimal.valueOf(0));          //设置普通用户的SITEID为0
                user.setNICKNAME(userid);
                UsersService ds = (UsersService)appContext.getBean("UsersService");
                errcode = ds.CreateGeneralAccount(user);
            } else {
                errcode = -1;
            }
        } else {
            errcode = -2;
        }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>周边旅游行网--游客注册</title>
    <link href="css/zhuce.css" rel="stylesheet" type="text/css" />
    <script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script language="javascript">
        var falg = false;
        var sucess = "";
        var errcode = <%=errcode%>;

        $(document).ready(function(){
            if (errcode == -1) {
                $("#yzmmsg").html("验证码输入错误，请重新输入验证码");
                $("#yzmmsg").css({color:"red"});
            } else if (errcode == -2) {
                $("#yzmmsg").html("运行环境初始化错误，请联系客服人员");
                $("#yzmmsg").css({color:"red"});
            } else if (errcode > 0) {
                alert("用户注册成功，系统跳转登陆页面");
                window.location.href="/_members/login.jsp";
            }
        });

        function ismail(mail) {
            falg = (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
            if (falg) {
                sucess = "sucess";
            }
        }

        function change_yzcodeimage() {
            $("#yzImageID").attr("src","/_members/image.jsp?temp=" + Math.random());
        }

        function setMessage(ftype) {
            var name = regform.username.value;
            var email = regform.email.value;
            var mphone = regform.mphone.value;

            if (ftype=="usermsg") {
                //检查用户是否被注册
                $.post("checkname.jsp",{
                            username:encodeURI(name)
                        },
                        function(data) {
                            if (data.indexOf("true") > -1) {
                                $("#usermsg").html("此用户名已经被注册过，请更换用户名");
                                $("#usermsg").css({color:"red"});
                            } else {
                                $("#usermsg").html("用户名可以使用");
                                $("#usermsg").css({color:"green"});
                            }
                        }
                )
            } else if (ftype=="emailmsg") {
                //检查电子邮件地址是否被注册
                $.post("checkemail.jsp",
                        {
                            email:encodeURI(email)
                        },
                        function(data) {
                            if (data.indexOf("true") > -1) {
                                $("#emailmsg").html("电子邮件地址已经被注册过");
                                $("#emailmsg").css({color:"red"});
                            } else {
                                $("#emailmsg").html("电子邮件地址可以使用");
                                $("#emailmsg").css({color:"green"});
                            }
                        }
                )
            } else if (ftype=="mphonemsg") {
                //检查手机号码是否被注册
                $.post("checkcellphone.jsp",
                        {
                            mphone:encodeURI(mphone)
                        },
                        function(data) {
                            if (data.indexOf("true") > -1) {
                                $("#mphonemsg").html("该手机号码已经被注册过，请更改手机号码");
                                $("#mphonemsg").css({color:"red"});
                            } else {
                                $("#mphonemsg").html("该手机号码可以使用");
                                $("#mphonemsg").css({color:"green"});
                            }
                        }
                )
            }
        }

        function tijiao(form) {
            var name = form.username.value;
            var pass = form.pwd.value;
            var confpass = form.repwd.value;
            var email = form.email.value;
            var mphone = form.mphone.value;
            var yzcode = form.yzcode.value;

            if (name == "") {
                alert("用户名不能为空");
                return false;
            }

            if (name.length <= 6) {
                alert("用户名长度必须6位以上");
                return false;
            }

            var reg = /[^A-Za-z0-9-]/g;
            if (reg.test(name)) {
                alert("用户名格式不正确");
                return false;
            }

            var username_used = false;
            $.ajax({
                url : 'checkname.jsp',
                type : 'post',
                async: false,                        //使用同步的方式,true为异步方式
                data:{
                    username:encodeURI(name)
                },
                success : function(data){
                    if (data.indexOf("true") > -1) {
                        username_used = true;
                    }
                },
                fail:function(){
                }
            });

            if (username_used == true) {
                alert("此用户名已经被注册过，请更换用户名");
                return false;
            }

            if (pass == "") {
                alert("密码不能为空");
                return false;
            }
            if (pass.length < 6) {
                alert("密码不能6位");
                return false;
            }
            if (pass != confpass) {
                alert("俩次填写的密码不一致");
                return false;
            }

            if (email == "") {
                alert("邮箱不能为空");
                return false;
            }
            ismail(email);
            if (sucess == "") {
                alert("请填写正确的EMAIL地址");
                return false;
            }

            //检查电子邮件地址是否存在
            var email_used = false;
            $.ajax({
                url : 'checkemail.jsp',
                type : 'post',
                async: false,                        //使用同步的方式,true为异步方式
                data:{
                    email:encodeURI(email)
                },
                success : function(data){
                    if (data.indexOf("true") > -1) {
                        email_used = true;
                    }
                },
                fail:function(){
                }
            });

            if (email_used == true) {
                alert("电子邮件地址已经被注册过,请更换电子邮件地址")
                return false;
            }

            if (contactor == "") {
                alert("请填写联系人姓名");
                return false;
            }

            if (mphone == "") {
                alert("请填写手机号码");
                return false;
            }

            if (mphone != "") {
                //var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
                var filter=/^[1][3,4,5,7,8][0-9]{9}$/;
                flag = filter.test(mphone);
                if (!flag) {
                    alert("手机号码输入有误，请重新输入！");
                    return false;
                }
            }

            //检查手机号码是否被注册
            var mphone_used = false;
            $.ajax({
                url : 'checkcellphone.jsp',
                type : 'post',
                async: false,                        //使用同步的方式,true为异步方式
                data:{
                    mphone:encodeURI(mphone)
                },
                success : function(data){
                    if (data.indexOf("true") > -1) {
                        mphone_used = true;
                    }
                },
                fail:function(){
                }
            });

            if (mphone_used == true) {
                alert("手机号码已经被注册过了,请更换手机号码");
                return false;
            }

            if (yzcode == "") {
                alert("验证码不正确");
                return false;
            }

            if (yzcode.length != 4) {
                alert("验证码不正确");
                return false;
            }

            var agreement = "";
            $("input:checkbox[name='agreement']:checked").each(function() {
                agreement = $(this).val();
            });

            if (agreement=="") {
                alert("请阅读协议，并勾选我已看过并同意条款才能通过");
                return false;
            }

            return false;
        }
    </script>
</head>

<body>
<div class="box">
    <%@ include file="inc/top.shtml" %>
    <!-- top end  -->
    <div style="height:99px; width:954px;">&nbsp;</div>
    <div class="login_con1">
        <div class="login_con_33">
            <form name="regform" method="post" action="zhuce_art.jsp" onsubmit="return tijiao(regform)">
                <input type="hidden" name="doCreate" value="true">
                <div class="hyzc">欢迎注册</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">用 户 名：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="username" type="text"  onblur="javascript:setMessage('usermsg')" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="usermsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">密　　码：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="pwd" type="password" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">确认密码：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="repwd" type="password" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">邮箱地址：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="email" type="text"  onblur="javascript:setMessage('emailmsg')" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="emailmsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">手    机：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="mphone" type="text" onblur="javascript:setMessage('mphonemsg')" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="mphonemsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">验证码<span>（*）</span>：</div>
                    <div class="zc_bd_6"><input class="zc_btn" name="yzcode" type="text" /></div>
                    <div class="zc_bd_7"><img src="image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/> <a href="javascript:change_yzcodeimage();">看不清，换一张</a></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc"  id="yzmmsg">&nbsp;</div>
                <div class="zc_bd_3"><input name="agreement" type="checkbox" value="1" /> 已阅读并接受<span>《周边旅游行网使用协议》</span></div>
                <div class="zc_bd_3"><input type="image" img src="images/20150527_80.jpg" name="ok" /></div>
            </form>
        </div>
        <div class="login_con_44">
            <div class="yyzh">已有帐号，从这里</div>
            <div class="yyzh_1"><img src="images/20150527_86.jpg" /><img src="images/20150527_87.jpg" /><img src="images/20150527_88.jpg" /></div>
        </div>
        <div class="clear"></div>
    </div>
    <div style="height:52px; width:954px;">&nbsp;</div>
</div>
<!-- fonnter  -->
<%@ include file="inc/tail.shtml" %>
</body>
</html>
