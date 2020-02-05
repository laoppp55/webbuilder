<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
    String username = ParamUtil.getParameter(request,"username");
    String password = ParamUtil.getParameter(request,"passWord1");
    String email = ParamUtil.getParameter(request,"email");
    int docreate = ParamUtil.getIntParameter(request,"doCreate",0);
    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sitename = request.getServerName();
    int Siteid = regMgr.getSiteid(sitename);
    if (docreate == 1) {
        Uregister ure = new Uregister();
        ure.setMemberid(username);
        ure.setPassword(password);
        ure.setEmail(email);
        ure.setSiteid(Siteid);
        int code = regMgr.insert_Info(ure);
        System.out.println("code=" + code);
        if(code==0){
            response.sendRedirect("index.jsp?username="+username+"&siteid=" + Siteid);
            session.setAttribute("username",username);
        }else{
            out.println("<script   lanugage=\"javascript\">alert(\"注册失败！请重新注册！\");window.location=\"register.jsp\";</script>");
        }
    }
%>

<html>
<head>
<title>欢迎注册</title>
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
    objXmlc.open("POST", "checkUsername.jsp?username="+name + "&siteid=<%=Siteid%>", false);
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
        objXmlc.open("POST", "CheceEmail.jsp?email="+Email + "&siteid=<%=Siteid%>", false);
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
    objXmlc.open("POST", "CheceVerify.jsp?Verify="+verify, false);
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
    document.all.cod.src="drawImage.jsp";
}
</script>
</head>
<body>
<form name="form" id="form" method="POST" >
    <input type="hidden" name="startflag" value="1">
    <input type="hidden" name="doCreate" value="1">
    <table width="800" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td width="150" align="right">用&nbsp;户&nbsp;名:</td>
            <td width="300" align="left">&nbsp;&nbsp;
                <input id="username" name="username" type="text" value="请输入用户名" size="18" onFocus="if (value =='请输入用户名'){value =''}" onBlur="check_user()"/></td>
            <td align="left"><div id="u_mag"></div></td>
        </tr>
        <tr>
            <td align="right">邮&nbsp;&nbsp;&nbsp;&nbsp;箱:</td>
            <td align="left">&nbsp;&nbsp;
                <input id="email" name="email" type="text" size="18" onBlur="check_email()"/></td>
            <td align="left"><div id="e_mag"></div></td>
        </tr>
        <tr>
            <td align="right">密&nbsp;&nbsp;&nbsp;&nbsp;码:</td>
            <td align="left">&nbsp;&nbsp;
                <input name="passWord1" id="passWord1" type="password" size="19" onBlur="javascript:checkPassword1()"/></td>
            <td align="left"><div id="p_mag1"></div></td>
        </tr>
        <tr>
            <td align="right">确认密码:</td>
            <td align="left">&nbsp;&nbsp;
                <input name="passWord2" id="passWord2" type="password" size="19" onBlur="javascript:checkPassword2()"/></td>
            <td align="left"><div id="p_mag2"></div></td>
        </tr>
        <tr>
            <td align="right">验&nbsp;证&nbsp;码:</td>
            <td align="left">&nbsp;&nbsp;
                <input id="txtVerify" name="txtVerify" type="text" size="10"/>&nbsp;<img  name="cod" src="drawImage.jsp" border=1>&nbsp;&nbsp;<a href="#" onclick="javascript:shuaxin()">看不清楚?换下一张</a></td>
            <td align="left"><div id="v_mag"></div></td>
        </tr>
        <tr>
            <td align="right"><input type="button" value="注册"  onclick="javascript:check()"/></td>
            <td></td>
            <td></td>
        </tr>
    </table>
</form>
</body>
</html>
