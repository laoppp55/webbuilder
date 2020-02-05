<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">



<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%
    String username = ParamUtil.getParameter(request,"username");
    String password = ParamUtil.getParameter(request,"passWord1");
    String email = ParamUtil.getParameter(request,"email");
    int docreate = ParamUtil.getIntParameter(request,"doCreate",0);

    String address=ParamUtil.getParameter(request,"address");
    String phone=ParamUtil.getParameter(request,"phone");
    String mphone=ParamUtil.getParameter(request,"mphone");
    String zip=ParamUtil.getParameter(request,"zip");
    String realname=ParamUtil.getParameter(request,"realname");

    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
   IFeedbackManager feedMgr = FeedbackPeer.getInstance();
   int siteid = feedMgr.getSiteID(sitename);
    int Siteid = siteid;
    if (docreate == 1) {
        Uregister ure = new Uregister();
        ure.setMemberid(username);
        ure.setPassword(password);
        ure.setEmail(email);
        ure.setSiteid(Siteid);
        
        ure.setPostalcode(zip);
        ure.setPhoen(phone);
        ure.setMobilephone(mphone);
        ure.setAddress(address);
         ure.setName(realname);

        int code = regMgr.insert_Info(ure);
        System.out.println("code=" + code);
        if(code==0){
            response.sendRedirect("login.jsp");
            Uregister ug = regMgr.login(username,password,Siteid);
            session.setAttribute("username",username);
            IOrderManager oMgr = orderPeer.getInstance();
            AddressInfo addressinfo = new AddressInfo();
             addressinfo.setAddress(address);
             addressinfo.setCity("");
             int id=-1;
             addressinfo.setId(id);
             addressinfo.setMobile(mphone);
             addressinfo.setName(realname);
             addressinfo.setPhone(phone);
             addressinfo.setProvinces("");
             addressinfo.setUserid(ug.getId());
             addressinfo.setZip(zip);
             addressinfo.setZone("");
             oMgr.createAddressInfo(addressinfo);


        }else{
            out.println("<script   lanugage=\"javascript\">alert(\"注册失败！请重新注册！\");window.location=\"register.jsp\";</script>");
        }
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>珠海振国肿瘤康复医院</title><script src="/_sys_js/tanchuceng.js" type="text/javascript"></script>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link href="/css/wzgstyle.css" type="text/css" rel="stylesheet" /><script src="/_sys_js/tanchuceng.js" type="text/javascript"></script><script type="text/JavaScript">
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
    objXmlc.open("POST", "/_commons/checkUsername.jsp?username="+name + "&siteid=1411", false);
    objXmlc.send(null);
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
		//alert(res);
    var re = res.split('-');
    var retstrs = re[0];
    if(retstrs==0){
        var message = "<font color=red>验证码错误！！<font>";
        document.getElementById("v_mag").innerHTML = message;
        document.all.txtVerify.value ="";
        shuaxin();
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
</script><style type="text/css">
<!--.biz_table{ border:1 dashed null;
 } 
.biz_table td{ font-size:12px; color:#000000; font-family:宋体 ; text-align:left;
}
.biz_table input{ font-size:18px;  size:12px;

}
biz_table img{ border:0;
}
--></style>
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
    objXmlc.open("POST", "/_commons/checkUsername.jsp?username="+name + "&siteid=<%=Siteid%>", false);
    objXmlc.send(null);
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
        objXmlc.open("POST", "/_commons/CheceEmail.jsp?email="+Email + "&siteid=<%=Siteid%>", false);
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
		//alert(res);
    var re = res.split('-');
    var retstrs = re[0];
    if(retstrs==0){
        var message = "<font color=red>验证码错误！！<font>";
        document.getElementById("v_mag").innerHTML = message;
        document.all.txtVerify.value ="";
        shuaxin();
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
<style type="text/css">
<!--.biz_table{ border:1 dashed null;
 } 
.biz_table td{ font-size:12px; color:#000000; font-family:12 ; text-align:left;
}
.biz_table input{ font-size:12px;  size:12px;

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
                                <td valign="top" align="left" width="103"><%@include file="/www_zhwzg_com/inc/menu.shtml" %></td>
                            </tr>
                        </tbody>
                    </table><%@include file="/www_zhwzg_com/inc/expert.shtml" %></td>
                    <td valign="top" align="left" width="767"><%@include file="/www_zhwzg_com/inc/weather.shtml" %><table cellspacing="0" cellpadding="0" width="767" border="0">
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
                                <td valign="bottom" align="left" height="18"><span class="titlered">注册</span> <span class="entitlered">Registration</span></td>
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
        <form id="form" method="post" name="form">
            <input type="hidden" name="startflag" value="1" />&nbsp;<input type="hidden" name="doCreate" value="1" />
            <table width="100%" border="0">
                <tbody>
                    <tr>
                        <td valign="middle" align="right" height="30">用&nbsp;户&nbsp;名：</td>
                        <td><input class="textbg" id="username" onBlur="check_user()" onFocus="if (value =='请输入用户名'){value =''}" name="username" value="请输入用户名" type="text" /></td>
                        <td align="left">
                        <div id="u_mag"></div>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
                        <td><input class="textbg" id="email" onBlur="check_email()" name="email" type="text" /></td>
                        <td align="left">
                        <div id="e_mag"></div>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
                        <td><input class="textbg" id="passWord1" type="password" onBlur="javascript:checkPassword1()" name="passWord1" /></td>
                        <td align="left">
                        <div id="p_mag1"></div>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">确认密码：</td>
                        <td><input class="textbg" id="passWord2" type="password" onBlur="javascript:checkPassword2()" name="passWord2" /></td>
                        <td align="left">
                        <div id="p_mag2"></div>
                        </td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">真实姓名：</td>
                        <td><input class="textbg" name="realname" type="text" /></td>
                        <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">地址：</td>
                        <td><input class="textbg" name="address" type="text" /></td>
                        <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">邮政编码：</td>
                        <td><input class="textbg" name="zip" type="text" /></td>
                        <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">固定电话：</td>
                        <td><input class="textbg" name="phone" type="text" /></td>
                        <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">移动电话：</td>
                        <td><input class="textbg" name="mphone" type="text" /></td>
                        <td align="left">&nbsp;</td>
                    </tr>
                    <tr>
                        <td valign="middle" align="right" height="30">验&nbsp;证&nbsp;码：</td>
                        <td><input class="textbg" id="txtVerify" name="txtVerify" type="text" />&nbsp;<img alt="" border="1" name="cod" src="/_commons/drawImage.jsp" />&nbsp;&nbsp;<a onclick="javascript:shuaxin()" href="#"><span class="titlered">看不清楚?换下一张</span></a></td>
                        <td align="left">
                        <div id="v_mag"></div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="button" onclick="javascript:check()" value="注册" /></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </tbody>
                    </table><%@include file="/www_zhwzg_com/inc/low.shtml" %></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
