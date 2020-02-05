<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.bizwink.cms.register.resinConfig" %>
<%@ page import="com.bizwink.joincompany.Joincompany" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    String realPath = request.getRealPath("/");
    String getCode = ParamUtil.getParameter(request, "Codes");
    String getSessionCode = (String) session.getAttribute("randnum");
    int hcode = ParamUtil.getIntParameter(request, "hcode",0);
    String name = ParamUtil.getParameter(request, "name");
    String pass = ParamUtil.getParameter(request, "pass");
    String email = ParamUtil.getParameter(request, "email");
    String sitename = ParamUtil.getParameter(request, "sitename");
    if (sitename == null || sitename == "") {
        sitename = ParamUtil.getParameter(request, "coositename");
    }
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    session.removeAttribute("randnum");
    if (flag == 1) {
        String userID = null;
        String password = null;
        String extname = null;
        String SiteName = null;
        int pic = -1;
        int tcflag = -1;
        int wapflag = 0;
        int pubflag = -1;
        int cssjsdir= 0;
        if(getSessionCode.equals(getCode)){
            userID = ParamUtil.getParameter(request, "userid");
            password = ParamUtil.getParameter(request, "password1");
            extname = ParamUtil.getParameter(request, "extname");
            SiteName = ParamUtil.getParameter(request, "SiteName");
            pic = ParamUtil.getIntParameter(request, "pic", 0);
            cssjsdir = ParamUtil.getIntParameter(request, "cssjsdir", 0);
            tcflag = ParamUtil.getIntParameter(request, "tcflag", 0);
            wapflag = ParamUtil.getIntParameter(request, "wapflag", 0);
            pubflag = ParamUtil.getIntParameter(request, "pubflag", 0);


            IRegisterManager regmgr = RegisterPeer.getInstance();
            String ip = regmgr.getIPByCode(hcode);
            Register register = new Register();
             Joincompany join=(Joincompany)session.getAttribute("join");
            if(join==null)
            {

            }
            else{
                register.setJoinid(join.getJoinid());
                register.setJoincompanyid(join.getId());
            }
            System.out.println(""+register.getJoinid()+"   "+register.getJoincompanyid());

            register.setUserID(name);
            register.setUsername(name);
            register.setSiteName(sitename);
            register.setPassword(pass);
            register.setEmail(email);
            register.setExtName("shtml");

            register.setImagesDir(pic);
            register.setCssjsDir(cssjsdir);
            register.setTCFlag(tcflag);
            register.setWapFlag(wapflag);
            register.setPubFlag(pubflag);
            register.setBindFlag(1);
            resinConfig resinconfig = regmgr.getResinConfig();
            regmgr.insertcreate(register, ip, realPath);
            //IAuthManager authMgr = AuthPeer.getInstance();
            //Auth authToken = authMgr.getAuth(name, pass);
            //session.setAttribute("CmsAdmin", authToken);
            //session.setMaxInactiveInterval(3600);
            response.sendRedirect("/webbuilder/register/index.jsp");
            //regmgr.insertCoositeRegister(register, resinconfig, "");
        }else{
            out.write("<script type=text/javascript>alert('验证码不正确');window.location='/webbuilder/register/register.jsp';</script>");
        }
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>酷站用户注册</title>
    <link href="coositecss.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        <!--
        .STYLE1 {color: #FF0000}
        -->
    </style>
</head>
<script language="javascript">
var falg = false;
var sucess = "";
function ismail(mail)
{

    falg = (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
    if (falg)
    {
        sucess = "sucess";
    }
}
function selectsitename(val){
    if(val == 0){
        if (regform.name.value != "" && regform.name.value != null) {
            regform.sitename.value = "";
            regform.sitename.readOnly = true;
            regform.coositename.value = "";
            regform.coositename.value=regform.name.value + ".coosite.com";
        } else {
            alert("请先填写用户名");
        }
    }
    if(val == 1){
        regform.sitename.readOnly = false;
        regform.coositename.value = "";
    }
}
function tijiao()
{
    var name = document.getElementById("name").value;
    var pass = document.getElementById("pass").value;
    var confpass = document.getElementById("confpass").value;
    var email = document.getElementById("email").value;
    var yanzhengma = document.getElementById("Codes").value;
    var sitename = document.getElementById("sitename").value;
    if (name == "")
    {
        alert("用户名不能为空");
        return false;
    }
    if (name.length <= 3)
    {
        alert("用户名长度必须3位以上");
        return false;
    }
    var reg = /[^A-Za-z0-9_]/g


    if (reg.test(name))
    {
        alert("用户名格式不正确");
        return false;
    }

    if (window.ActiveXObject)
    {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest)
    {
        objXml = new XMLHttpRequest();

    }
    objXml.open("POST", "checkname.jsp?name=" + name, false);
    objXml.send(null);

    var retstr = objXml.responseText;
    if (retstr.indexOf("false") != -1) {
        alert("对不起，此用户名已经被注册过，请更换用户名");
        return false;
    }
    if (pass == "")
    {
        alert("密码不能为空");
        return false;
    }
    if(pass.length<6)
    {
        alert("密码不能6位");
        return false;
    }
    if (pass != confpass)
    {
        alert("俩次填写的密码不一致");
        return false;
    }
    if (email == "")
    {
        alert("邮箱不能为空");
        return false;
    }
    ismail(email);
    if (sucess == "")
    {
        alert("请填写正确的EMAIL地址");
        return false;
    }
    if (window.ActiveXObject)
    {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest)
    {
        objXml = new XMLHttpRequest();

    }
    objXml.open("POST", "checkemail.jsp?email=" + email, false);
    objXml.send(null);

    var retstr = objXml.responseText;

    if (retstr.indexOf("false") != -1) {
        alert("对不起，此邮箱已经被注册过，请更换邮箱");
        return false;
    }
    if(yanzhengma=="")
    {
        alert("验证码不正确");
        return false;
    }
    if(yanzhengma.length!=4)
    {
        alert("验证码不正确");
        return false;
    }
    var tongyi = document.getElementById("tongyi");

    if (!tongyi.checked)
    {
        alert("我已看过并同意条款才能通过");
        return false;
    }
    document.form.action = "register.jsp";
    document.form.submit();
}

</script>
<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="25">&nbsp;</td>
        <td width="223" align="left" valign="top"><img src="images/logo_331.jpg" width="217" height="84" vspace="10"/>
        </td>
        <td width="261" align="left" valign="top"><img src="images/Preview_331.jpg" width="261" height="152"/></td>
        <td width="491" align="left" valign="top">
            <table width="465" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="20" height="30">&nbsp;</td>
                    <td width="435">&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="right" valign="middle"><a href="#" class="inde"><br/>
                        设为首页 |</a><a href="#" class="inde"> 加为收藏 &nbsp;</a><a href="#"> </a></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="left" valign="top">
                        <img src="images/coosite_14.gif" width="48" height="17"/><img src="images/coosite_15.gif"
                                                                                      width="80" height="17"/><img
                            src="images/coosite_16.gif" width="80" height="17"/><img src="images/coosite_17.gif"/><img
                            src="images/coosite_18.gif" width="81" height="17"/><img src="images/coosite_19.gif"
                                                                                     width="78" height="17"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table width="688" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>
    <td align="left" valign="top"><img src="images/coosite_login_1.gif" width="688" height="79"/></td>
</tr>
<tr>
<td align="left" valign="top" background="images/coosite_login_2.gif">
<table width="688" border="0" cellspacing="0" cellpadding="0">
<form method="post" name="regform" onsubmit="return tijiao();">
<input type="hidden" value="1" name="flag">
<tr>
<td width="40">&nbsp;</td>
<td width="335" align="left" valign="top">
<table width="330" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td width="117" height="20" align="left">&nbsp;</td>
    <td width="213" align="left">&nbsp;</td>
</tr>
<tr>
    <td align="left" valign="top" class="grey_font">用户名：</td>
    <td align="left" valign="middle"><label>
        <input name="name" type="text" class="txt_inde" id="name" style="vertical-align:middle"/>
    </label></td>
</tr>
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" valign="top" class="grey_font">密 &nbsp;码：</td>
    <td align="left" valign="top"><label>
        <input name="pass" type="password" id="pass" class="txt_inde"/>
    </label></td>
</tr>
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" valign="top" class="grey_font">再次输入密码：</td>
    <td align="left" valign="top"><label>
        <input name="confpass" id="confpass" type="password" class="txt_inde"/>
    </label></td>
</tr>
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" class="grey_font"><input type="radio" name="domaintype" value="0" onclick="javascript:selectsitename(0);" />
        COOSITE域名</td>
    <td align="left" valign="top" class="grey_font"><input name="coositename" id="coositeid" type="text" class="txt_inde" readonly="true" /></td>
</tr>
<tr>
    <td align="left" valign="top" class="grey_font">&nbsp;</td>
    <td align="left" valign="top" class="grey_font">&nbsp;</td>
</tr>
<tr>
    <td align="left" class="grey_font"><input type="radio" name="domaintype"  checked="checked" value="1" onclick="javascript:selectsitename(1);">
        自定义域名</td>
    <td align="left" class="grey_font"><input name="sitename" id="sitenameid" type="text" class="txt_inde"/>
    </td>
</tr>
<!--tr>
    <td align="right" valign="top" class="grey_font">域&nbsp;&nbsp;名：</td>
    <td align="left" valign="top"><label>
        <input name="sitename" id="sitename" type="text" class="txt_inde"/>
    </label></td>
</tr-->
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" valign="top" class="grey_font">电子邮箱：</td>
    <td align="left" valign="top"><label>
        <input name="email" id="email" type="text" class="txt_inde"/>
    </label></td>
</tr>
<!--tr>
  <td height="19"></td>
  <td></td>
</tr-->
<!--tr>
  <td align="right" valign="bottom">支付方式：</td>
  <td align="left" valign="top">
      <label>
        <input type="radio" name="radiobutton" value="radiobutton" />
        在线支付</label>
      <label>
      <input type="radio" name="radiobutton" value="radiobutton" />
      支付宝</label>
  </td>
</tr-->
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" valign="top" class="grey_font">验证码：</td>
    <td align="left" valign="top"><label><INPUT
            class="txt_fact"
            id=Codes
            style="WIDTH: 72px"
            name=Codes>
        <img src="image.jsp" alt="验证码" align="middle" /></label>    </td>
</tr>
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" valign="middle" class="grey_font">&nbsp;</td>
    <td align="left" valign="top"><label>
        <input type="checkbox" id="tongyi" name="tongyi" value="checkbox"/>
        我已看过并同意《赢商动力协议》</label></td>
</tr>
<tr>
    <td height="19" align="left"></td>
    <td align="left"></td>
</tr>
<tr>
    <td align="left" valign="top">&nbsp;</td>
    <td align="left"><input type="image" img src="images/enter_331.jpg" width="62" height="27" /></td>
</tr>
<tr>
    <td align="left" valign="middle" class="grey_font">&nbsp;</td>
    <td align="left" valign="top"><label></label></td>
</tr>
</table>
</td>
<td width="313" align="left" valign="top"><br/>
    <img src="images/coosite_login_4.gif" width="268" height="156" hspace="20"/></td>
</tr>
</form>
</table>
</td>
</tr>
<tr>
    <td align="left" valign="top"><img src="images/coosite_login_3.gif" width="688" height="64"/></td>
</tr>
</table>
</body>
</html>
