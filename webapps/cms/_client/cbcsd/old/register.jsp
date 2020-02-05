<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.*" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%
    String company=ParamUtil.getParameter(request,"company");
    String username = ParamUtil.getParameter(request,"username");
    String department=ParamUtil.getParameter(request,"department");
    String email = ParamUtil.getParameter(request,"email");
    String password = ParamUtil.getParameter(request,"passWord1");
    int docreate = ParamUtil.getIntParameter(request,"doCreate",0);

    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    sitename = "www.cbcsd.org.cn";
    int siteid = feedMgr.getSiteID(sitename);
    if (docreate == 1) {
        Uregister ure = new Uregister();
        ure.setMemberid(username);
        ure.setPassword(password);
        ure.setEmail(email);
        ure.setSiteid(siteid);
        ure.setCompany(company);
        ure.setDepartment(department);
        ure.setLockflag(2);

        int code = regMgr.insert_Info(ure);
        if(code>0){
            out.println("<script   lanugage=\"javascript\">alert(\"注册成功,请等候审核！\");window.location=\"/index.shtml\";</script>");
            //response.sendRedirect("/index.shtml");
            //Uregister ug = regMgr.login(username,password,siteid);
            //session.setAttribute("username",username);
        }else{
            out.println("<script   lanugage=\"javascript\">alert(\"注册失败！请重新注册！\");window.location=\"register.jsp\";</script>");
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
    <title>CBCSD</title>
    <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <link rel="stylesheet" type="text/css" href="/css/login.css" />
    <script type="text/javascript"  src="/js/jquery.min.js" ></script>
    <script type="text/javascript"  src="/js/jquery.jcarousel.pack.js" ></script>
    <script type="text/javascript"  src="/js/menu.js" ></script>
    <script type="text/JavaScript">
        function check_user() {
            var name = document.all.username.value;
            if(name ==""){
                var message = "<font color=red>请输入用户名！<font>";
                document.getElementById("u_mag").innerHTML = message;
                return false;
            }if(name.length <5||name.lenght >20){
                var message = "<font color=red>用户名长度为5-20个字符！<font>";
                document.getElementById("u_mag").innerHTML = message;
                return false;
            }else{
                var objXmlc;
                var ref = window.location.href;
                if (window.ActiveXObject){
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                }else if (window.XMLHttpRequest){
                    objXmlc = new XMLHttpRequest();
                }
                objXmlc.open("POST", "/_commons/checkUsername.jsp?username="+name + "&siteid=39", false);
                objXmlc.send(null);
                alert(objXmlc.responseText);
                var res = objXmlc.responseText;
                var re = res.split('-');
                var retstrs = re[0];
                if(retstrs==0){
                    var message = "<font color=red>用户名可以使用！！<font>";
                    document.getElementById("u_mag").innerHTML = message;
                }if(retstrs==1){
                    var message = "<font color=red>用户名已经被注册不可以使用！！<font>";
                    document.getElementById("u_mag").innerHTML = message;
                    document.all.username.value ="";
                    return false;
                }
            }
        }
        function check_email() {
            var Email = document.all.email.value;
            if(Email ==""){
                var message = "<font color=red>请输入邮箱地址！<font>";
                document.getElementById("e_mag").innerHTML = message;
                return false;
            }else{
                var objXmlc;
                var ref = window.location.href;
                if (window.ActiveXObject){
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                }else if (window.XMLHttpRequest){
                    objXmlc = new XMLHttpRequest();
                }
                objXmlc.open("POST", "/_commons/CheceEmail.jsp?email="+Email + "&siteid=1411", false);
                objXmlc.send(null);
                var res = objXmlc.responseText;
                var re = res.split('-');
                var retstrs = re[0];
                if(retstrs==0){
                    var message = "<font color=red>邮箱可以使用！！<font>";
                    document.getElementById("e_mag").innerHTML = message;
                }if(retstrs==1){
                    var message = "<font color=red>邮箱已经被注册不可以使用！！<font>";
                    document.getElementById("e_mag").innerHTML = message;
                    document.all.email.value ="";
                    return false;
                }
            }
        }
        function checkPassword1(){
            var passWord1 = document.getElementById("passWord1").value;
            if(passWord1 == ""){
                document.getElementById("p_mag1").innerHTML = "<font color=red>密码不能为空！</font>";
                return false;
            }if(passWord1.length<5 ||passWord1.length>20){
                document.getElementById("p_mag1").innerHTML = "<font color=red>密码长度为6至20个字符！</font>";
                document.getElementById("passWord1").value="";
                return false;
            }
        }
        function checkPassword2(){
            var passWord1 = document.getElementById("passWord1").value;
            var psaaWord2 = document.getElementById("passWord2").value;
            if(passWord1!=psaaWord2){
                document.getElementById("p_mag1").innerHTML = "<font color=red>两次输入的密码不一致，请重新输入！</font>";
                document.getElementById("passWord1").value="";
                document.getElementById("passWord2").value="";
                return false;
            }else{
                document.getElementById("p_mag1").innerHTML = "<font color=red>两次输入的密码一致！</font>";
            }
        }
        function check(){
            var name = document.getElementById("username").value;
            var email = document.getElementById("email").value;
            var pass = document.getElementById("passWord1").value;
            var yanzheng = document.getElementById("txtVerify").value;
            var verify = document.getElementById("txtVerify").value;
            if(name == ""){
                alert("用户名不能为空");
                return false;
            }if(email ==""){
                alert("邮箱不能为空");
                return false;
            }if(pass ==""){
                alert("密码不能为空");
                return false;
            }if(yanzheng ==""){
                alert("验证码不能为空");
                return false;
            }else{
                var objXmlc;
                if (window.ActiveXObject){
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                }else if (window.XMLHttpRequest){
                    objXmlc = new XMLHttpRequest();
                }
                objXmlc.open("POST", "/_commons/CheceVerify.jsp?Verify="+verify, false);
                objXmlc.send(null);
                var res = objXmlc.responseText;
                var re = res.split('-');
                var retstrs = re[0];
                if(retstrs==0){
                    var message = "<font color=red>验证码错误！！<font>";
                    document.getElementById("v_mag").innerHTML = message;
                    document.all.txtVerify.value ="";
                    //shuaxin();
                    return false;
                }
            }

            document.all.form.action = "register.jsp";
            document.all.form.submit();
        }

        function shuaxin(){
            var verify=document.getElementById('safecode');
            verify.setAttribute('src','/_commons/drawImage.jsp?dumy='+Math.random());
        }
    </script>
</head>
<body xmlns="">
<div class="main">
    <div class="box"><%@include file="/inc/head.shtml" %><div class="banner"><img alt="" src="/images/banner61.jpg" /></div>
        <div class="con">
            <div class="path"><span>当前位置：</span><a href="/">首页</a></div>
            <div class="bigmain">
                <div class="left">
                    <form id="Regformid" method="post" action="register.jsp?formtype=0" onSubmit="return check();" name="Regform">
                        <input type="hidden" name="startflag" value="1" />&nbsp;
                        <input type="hidden" name="doCreate" value="1" />
                        <input type="hidden" name="siteid" value="39" />

                        <div class="title"><img alt="" src="/images/loginone1.jpg" /></div>
                        <div class="tabone">
                            <div class="tab_title">公司名称：</div>
                            <div class="tab_input"><input name="company" type="text" /></div>
                            <div class="tab_grey"></div>
                            <div class="tab_red"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">姓名：</div>
                            <div class="tab_input"><input name="username" id="username" onBlur="check_user()" onFocus="if (value =='请输入用户名'){value =''}" name="username" value="请输入用户名" type="text" /></div>
                            <div class="tab_grey" id="u_mag"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">部门：</div>
                            <div class="tab_input"><input name="department" type="text" /></div>
                            <div class="tab_grey"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">邮箱：</div>
                            <div class="tab_input"><input id="email" onBlur="check_email()" name="email" type="text" /></div>
                            <div class="tab_grey" id="e_mag"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">登录密码：</div>
                            <div class="tab_input"><input id="passWord1" type="password" onBlur="javascript:checkPassword1()" name="passWord1" /></div>
                            <div class="tab_grey" id="p_mag1"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">再次确认密码：</div>
                            <div class="tab_input"><input id="passWord2" type="password" onBlur="javascript:checkPassword2()" name="passWord2" /></div>
                            <div class="tab_grey" id="p_mag2"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">验证码：</div>
                            <div class="tab_input"><input id="txtVerify" name="txtVerify" type="text" />&nbsp;<img alt="" border="1" name="safecode" src="/_commons/drawImage.jsp" />&nbsp;&nbsp;<a  href="javascript:shuaxin();"><span class="titlered">看不清楚?换下一张</span></a></div>
                            <div class="tab_grey" id="v_mag"></div>
                        </div>
                        <div class="button"><input type="image" src="/images/button_login.jpg" value="注册" /></div>
                    </form>
                </div>
                <div class="right">
                    <form id="loginid" method="post" action="login.jsp" name="loinform">
                        <input type="hidden" name="doLogin" value="true" />
                        <input type="hidden" name="siteid" value="39" />
                        <div class="title"><img alt="" src="/images/loginone2.jpg" /></div>
                        <div class="tabone">
                            <div class="tab_title">公司名称：</div>
                            <div class="tab_input"><input name="comp" type="text" /></div>
                            <div class="tab_grey"></div>
                            <div class="tab_red"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">姓名：</div>
                            <div class="tab_input"><input name="userid" type="text" /></div>
                            <div class="tab_grey"></div>
                        </div>
                        <div class="tabone">
                            <div class="tab_title">登录密码：</div>
                            <div class="tab_input"><input id="passwd" type="password" name="passwd" /></div>
                            <div class="tab_grey"></div>
                        </div>
                        <div class="button"><input type="image" src="/images/loginone3.jpg" value="登录" /></div>
                    </form>
                </div>
            </div>
        </div><%@include file="/inc/tail.shtml" %><div class="clear"></div>
    </div>
</div>
<div class="bg-bottom"></div>
<p></p>
</body>
</html>