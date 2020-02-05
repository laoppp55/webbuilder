<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.util.*" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.sql.Timestamp" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-3-25
  Time: 下午9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int errcode = 0;
    BigDecimal siteid = BigDecimal.valueOf(5971);
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        UsersService usersService = (UsersService)appContext.getBean("usersService");
        System.out.println("doCreate==" + doCreate);
        if (doCreate) {
            String userid = filter.excludeHTMLCode(ParamUtil.getParameter(request,"username"));
            String realname = filter.excludeHTMLCode(ParamUtil.getParameter(request, "realname"));
            String pwd = filter.excludeHTMLCode(ParamUtil.getParameter(request, "pwd"));
            String repwd = filter.excludeHTMLCode(ParamUtil.getParameter(request, "repwd"));
            String email = filter.excludeHTMLCode(ParamUtil.getParameter(request, "email"));
            String mphone = filter.excludeHTMLCode(ParamUtil.getParameter(request, "mphone"));
            String fax = filter.excludeHTMLCode(ParamUtil.getParameter(request, "fax"));
            String address = filter.excludeHTMLCode(ParamUtil.getParameter(request, "address"));
            String companyname = filter.excludeHTMLCode(ParamUtil.getParameter(request, "companyname"));
            String works = filter.excludeHTMLCode(ParamUtil.getParameter(request, "works"));
            List fromways = ParamUtil.getParameterValues(request,"fromway");
            boolean subscribe = ParamUtil.getBooleanParameter(request,"subcribe");
            String yzcode = filter.excludeHTMLCode(ParamUtil.getParameter(request, "yzcode"));
            boolean agreeflag = ParamUtil.getCheckboxParameter(request,"agreement");
            String yzcodeForSession = (String)session.getAttribute("randnum");

            System.out.println("userid==" + userid);
            System.out.println("realname==" + realname);
            System.out.println("pwd==" + pwd);
            System.out.println("repwd==" + repwd);
            System.out.println("email==" + email);
            System.out.println("mphone==" + mphone);
            System.out.println("fax==" + fax);
            System.out.println("address==" + address);
            System.out.println("companyname==" + companyname);
            System.out.println("works==" + works);
            System.out.println("subscribe==" + subscribe);
            System.out.println("yzcode==" + yzcode + "==yzcodefromsession==" + yzcodeForSession);
            System.out.println("agreeflag==" + agreeflag);


            String fromway_value = "";
            if (fromways!=null) {
                for(int ii=0; ii<fromways.size();ii++) {
                    fromway_value = fromway_value + fromways.get(ii) + ",";
                }
                if (fromway_value.length()>0) fromway_value = fromway_value.substring(0,fromway_value.length()-1);
            }

            System.out.println("fromway_value==" + fromway_value);

            if (agreeflag && pwd.equals(repwd) && yzcode.equals(yzcodeForSession)) {
                Users user = new Users();
                String password = Encrypt.md5(pwd.getBytes());
                user.setUSERID(userid);
                user.setUSERPWD(password);
                user.setSITEID(siteid);
                user.setEMAIL(email);
                user.setMPHONE(mphone);
                user.setPHONE(fax);
                user.setADDRESS(address);
                user.setCOMPANY(companyname);
                user.setAREA(works);
                user.setJIEDAO(fromway_value);
                user.setPREDEPOSIT(BigDecimal.valueOf((subscribe) ? 1 : 0));     //1定于，0不订阅
                user.setNICKNAME(realname);
                user.setCREATEDATE(new Timestamp(System.currentTimeMillis()));
                user.setUSERTYPE(BigDecimal.valueOf(5));
                boolean userExistFlag = usersService.checkName(siteid,userid);
                boolean emailExistFlag = usersService.checkEmail(siteid,email);
                boolean mphoneExistFlag = usersService.checkEmail(siteid,mphone);
                if (agreeflag && !userExistFlag && !emailExistFlag && !mphoneExistFlag && companyname!=null && email!=null && mphone!=null && userid!=null) {
                    errcode = usersService.addUser(user);
                    System.out.println("error code==" + errcode);
                } else {
                    errcode = -6;
                }
                //if (errcode == 1){
                //关闭本注册窗口
                //    out.println("<script language=javascript>window.opener = null;window.open('', '_self');window.close();</script>");
                //    return;
                //}
            } else {
                errcode = -4;
            }
        }
    } else {
        errcode = -5;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
<title>全球治理和企业发展数据平台--用户注册</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="IE=8" http-equiv="X-UA-Compatible" />
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/child.css" />
<script src="/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script language="javascript">
var falg = false;
var sucess = "";
var errcode = <%=errcode%>;

$(document).ready(function(){
    if (errcode == -1) {
        $("#usermsg").html("用户名已经存在");
        $("#usermsg").css({color:"red"});
    } else if (errcode == -2) {
        $("#emailmsg").html("电子邮件地址已经被使用");
        $("#emailmsg").css({color:"red"});
    } else if (errcode == -3) {
        $("#mphonemsg").html("手机号码被使用");
        $("#mphonemsg").css({color:"red"});
    } else if (errcode == -5) {
        $("#usermsg").html("运行环境初始化错误，请联系客服人员");
        $("#usermsg").css({color:"red"});
    } else if (errcode == -4) {
        $("#agreementmsg").html("未阅读协议或者是验证码输入错误");
        $("#agreementmsg").css({color:"red"});
    } else if (errcode == -5) {
        $("#usermsg").html("关键数据填写错误，或者没有填写，或者填写格式错误");
        $("#usermsg").css({color:"red"});
    } else if (errcode > 0) {
        alert("用户注册成功，点击确认按钮关闭注册页面");
        window.close();
        //window.location.href="/users/login.jsp";
    }
});

function ismail(mail) {
    falg = (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
    if (falg) {
        sucess = "sucess";
    }
}

function change_yzcodeimage() {
    $("#yzImageID").attr("src","/users/image.jsp?temp=" + Math.random());
}

function setMessage(ftype) {
    var name = regform.username.value;
    var email = regform.email.value;
    var mphone = regform.mphone.value;

    if (ftype=="usermsg") {
        //检查用户是否存在
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
        //检查电子邮件地址是否存在
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
        //检查移动电话号码是否存在
        $.post("checkcellphone.jsp",
                {
                    mphone:encodeURI(mphone)
                },
                function(data) {
                    if (data.indexOf("true") > -1) {
                        $("#mphonemsg").html("手机号码已经被注册过");
                        $("#mphonemsg").css({color:"red"});
                    } else {
                        $("#mphonemsg").html("手机号码可以使用");
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
    var companyname = form.companyname.value;
    var contactor = form.realname.value;
    var yzcode = form.yzcode.value;

    if (name == "") {
        alert("用户名不能为空");
        return false;
    }

    if (name.length <= 3) {
        alert("用户名长度必须3位以上");
        return false;
    }

    var reg = /[^A-Za-z0-9-]/g;
    if (reg.test(name)) {
        alert("用户名格式不正确");
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

    if (companyname=="") {
        alert("工作单位不能为空");
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

    if (mphone == "") {
        alert("请填写联系电话");
        return false;
    }

    if (mphone != "") {
        //var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;     //验证电话号码是否正确
        var filter = /^1[3|4|5|8][0-9]\d{4,8}$/;                                   //验证手机号码是否正确
        flag = filter.test(mphone);
        if (!flag) {
            alert("电话输入有误，请重新输入！");
            return false;
        }
    }

    var fromway = "";
    $("input:checkbox[name='fromway']:checked").each(function() {
        fromway = fromway + $(this).val() + " ";
    });

    var subscribe = $("input[name='subcribe']:checked").val();

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

    return true;
}
</script>
</head>
<body xmlns="">
<!--head begin-->
<!--div><#include virtual="/inc/top.shtml"></div>
<div class="menu">
    <div class="menu_in"><#include virtual="/inc/menu.shtml"></div>
</div-->
<!--head end-->
<div class="containter">
    <div class="box">
        <div class="loction">当前位置： <a href="/">首页</a> &gt; <a href="#">注册</a></div>
        <div class="list_box">
            <div class="result">
                <div class="result_title">注册</div>
                <div class="zhuce">
                    <form name="regform" method="post" action="userreg.jsp" onsubmit="return tijiao(regform)">
                        <input type="hidden" name="doCreate" value="true">
                        <div class="zhuce_1">
                            <div class="txt"><span>*</span> 用户名：</div>
                            <div class="input"><input name="username" type="text"  onblur="javascript:setMessage('usermsg')" /></div>
                        </div>
                        <div class="hint" id="usermsg"><span class="red">请填写用户名称</span></div>
                        <div class="zhuce_1">
                            <div class="txt"><span>*</span> 密 码：</div>
                            <div class="input"><input name="pwd" type="password" value=""/></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt"><span>*</span> 确认密码：</div>
                            <div class="input"><input name="repwd" type="password" value=""/></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt">姓 名：</div>
                            <div class="input"><input name="realname" type="text"/></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt"><span>*</span> 单位名称：</div>
                            <div class="input"><input name="companyname" type="text" /></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt">工作领域：</div>
                            <div class="input"><input name="works" type="text" /></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt"><span>*</span> Email：</div>
                            <div class="input"><input  name="email" type="text"  onblur="javascript:setMessage('emailmsg')" /></div>
                        </div>
                        <div class="hint" id="emailmsg"></div>
                        <div class="zhuce_1">
                            <div class="txt">通讯地址：</div>
                            <div class="input"><input name="address" type="text" /></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt">传 真：</div>
                            <div class="input"><input name="fax" type="text" /></div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt">电 话：</div>
                            <div class="input"><input name="mphone" type="text" onblur="javascript:setMessage('mphonemsg')"/></div>
                        </div>
                        <div class="hint" id="mphonemsg"></div>
                        <div class="zhuce_1">
                            <div class="txt">获知途径：</div>
                            <div class="choice_1">
                                <input type="checkbox" name="fromway" value="1" /> 朋友推荐 &nbsp;
                                <input type="checkbox" name="fromway" value="2" /> 网站链接 &nbsp;
                                <input type="checkbox" name="fromway" value="3" /> 搜索引擎 &nbsp;
                                <input type="checkbox" name="fromway" value="4" /> 其他途径 &nbsp;
                            </div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_1">
                            <div class="txt"><span>*</span>是否订阅CBCSD月度资讯：</div>
                            <div class="choice_1"><input type="radio" name="subcribe" value="1" />是&nbsp;
                                <input type="radio" name="subcribe" value="0" />否</div>
                        </div>
                        <div class="hint"></div>
                        <div class="zhuce_2">
                            <div class="txt">验证码<span>（*）</span>：</div>
                            <div class="input"><input  name="yzcode" type="text" /></div>
                            <div class="txt"><img src="image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/> <a href="javascript:change_yzcodeimage();">看不清，换一张</a></div>
                        </div>
                        <div class="choice_2">
                            <input type="checkbox" name="agreement" value="1" /> 请阅读
                            <a href="/register_tk.shtml" style="color:#257ed8" target="view_window">
                                《全球经济治理与企业发展数据平台使用条款》</a>，如您继续注册则表示已阅读并接受条款相关内容
                        </div>
                        <div class="hint" id="agreementmsg"></div>
                        <div class="zhuce_btn"><input type="submit" name="zhuce" value="注册" /></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!--div class="clear"></div><#include virtual="/inc/bottom.shtml"></div-->
</body>
</html>