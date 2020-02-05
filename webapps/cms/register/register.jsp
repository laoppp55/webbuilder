<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    String realPath = request.getRealPath("/");
    String getSessionCode = (String) session.getAttribute("randnum");
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    session.removeAttribute("randnum");
    //System.out.println("getSessionCode="+getSessionCode);
    if (flag == 1) {
        int pic = 0;
        int tcflag = 0;
        int wapflag = 0;
        int pubflag = 0;
        int cssjsdir = 0;
        int nav = 0;
        int sam_site = 0;
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.initialize(this.getServletConfig(), request, response);
        mySmartUpload.upload();
        String getCode = mySmartUpload.getRequest().getParameter("Codes");
        // System.out.println("getCode=" + getCode);
        String name = mySmartUpload.getRequest().getParameter("name");
        String pass = mySmartUpload.getRequest().getParameter("pass");
        String email = mySmartUpload.getRequest().getParameter("email");
        String username = mySmartUpload.getRequest().getParameter("username");
        // System.out.println("username="+username);
        String myphone = mySmartUpload.getRequest().getParameter("myphone");
        String myaddress = mySmartUpload.getRequest().getParameter("myaddress");
        String sitename = mySmartUpload.getRequest().getParameter("sitename");
        if (sitename==null) {
            sitename = mySmartUpload.getRequest().getParameter("coositename");
        }
        String copyright = mySmartUpload.getRequest().getParameter("copyright");

        String samsitetemplate = mySmartUpload.getRequest().getParameter("samsitetemplate");
        int samsitetemplatenum = 0;
        if (samsitetemplate != null) {
            samsitetemplatenum = Integer.parseInt(samsitetemplate);
        }

        //获取实例站点ID和实例站点名称
        String s_sam_site = mySmartUpload.getRequest().getParameter("samsiteid");
        if (s_sam_site!=null) {
            ISiteInfoManager sitemgr = SiteInfoPeer.getInstance();
            sam_site = Integer.parseInt(s_sam_site);
            SiteInfo siteinfo = sitemgr.getSiteInfo(sam_site);
        }

        if (getSessionCode.equals(getCode)) {
            String uploadPath = realPath + "sites" + java.io.File.separator + StringUtil.replace(sitename, ".", "_") + java.io.File.separator + "images" + java.io.File.separator;
            java.io.File file = new java.io.File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            mySmartUpload.save(uploadPath);

            //com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
            //com.jspsmart.upload.File logoFile = uploadFiles.getFile(0);
            //com.jspsmart.upload.File bannerFile = uploadFiles.getFile(1);

            IRegisterManager regmgr = RegisterPeer.getInstance();
            //String ip = regmgr.getIPByCode();
            Register register = new Register();
            register.setUserID(name);
            register.setUsername(username);
            register.setMphone(myphone);
            register.setAddress(myaddress);
            register.setSiteName(sitename);
            register.setNavigator(nav);
            register.setSamsite(sam_site);
            register.setPassword(pass);
            register.setEmail(email);
            register.setExtName("shtml");
            register.setImagesDir(pic);
            register.setCssjsDir(cssjsdir);
            register.setTCFlag(tcflag);
            register.setWapFlag(wapflag);
            register.setPubFlag(pubflag);
            register.setBindFlag(1);
            register.setSharetemplatenum(samsitetemplatenum);
            register.setCopyright(copyright);
            int siteid = regmgr.insertcreate(register, realPath);
            if (siteid > 0) {
                regmgr.copySamSite(sam_site, siteid, name, sitename, realPath);
                response.sendRedirect("/webbuilder/register/index.jsp");
            } else {
                out.write("<script type=text/javascript>alert('站点注册失败！！！');window.location='/webbuilder/register/register.jsp';</script>");
            }
        } else {
            out.write("<script type=text/javascript>alert('验证码不正确');window.location='/webbuilder/register/register.jsp';</script>");
        }
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>酷站用户注册</title>
    <link href="coositecss.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        <!--
        .STYLE1 {
            color: #FF0000
        }

        -->
    </style>
</head>
<script language="javascript">
    var falg = false;
    var sucess = "";
    function ismail(mail) {

        falg = (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
        if (falg) {
            sucess = "sucess";
        }
    }

    function sel_navigator() {
        window.open("../register/listnavigator.jsp", "selectnavigator");
    }

    function sel_template() {
        window.open("../register/websamindex.jsp", "selectmodel");
    }

    function changecoositename() {
        regform.coositename.value = regform.name.value + ".coosite.com";
        regform.sitename.value = "";
    }

    function tijiao() {
        var name = document.getElementById("name").value;
        var pass = document.getElementById("pass").value;
        var confpass = document.getElementById("confpass").value;
        var email = document.getElementById("email").value;
        var yanzhengma = document.getElementById("Codes").value;
        var samsitename = document.getElementById("samsitename").value;
        var samsiteid = document.getElementById("samsiteid").value;
        var samsitetemplate = document.getElementById("samsitetemplate").value;
        var sitename = document.getElementById("coositename").value;
        var copyright = document.getElementById("copyright").value;
        var username = document.getElementById("username").value;
        var myphone = document.getElementById("myphone").value;

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

        if (window.ActiveXObject) {
            objXml = new ActiveXObject("Microsoft.XMLHTTP");
        }
        else if (window.XMLHttpRequest) {
            objXml = new XMLHttpRequest();

        }
        objXml.open("POST", "checkname.jsp?name=" + name, false);
        objXml.send(null);

        var retstr = objXml.responseText;
        if (retstr.indexOf("false") != -1) {
            alert("对不起，此用户名已经被注册过，请更换用户名");
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

        for (var i = 0; i < regform.domaintype.length; i++) {
            if (regform.domaintype[i].checked) {
                value = regform.domaintype[i].value;
                break;
            }
        }

        if (sitename == null || sitename == "") {
            alert("用户选择自定义域名，域名不能为空");
            return false;
        }
        var reg = /[^A-Za-z0-9-.]/g;
        if (reg.test(sitename)) {
            alert("域名只能包括数字、字符和中划线");
            return false;
        }
        dotposi = sitename.indexOf(".");
        if (dotposi <= 0 || dotposi == sitename.length - 1) {
            alert("域名必须至少包含一个分隔符，并且分隔符不能是第一个字符和最后一个字符");
            return false;
        }
        //检查域名是否已经被使用
        objXml.open("POST", "checkhost.jsp?host=" + sitename, false);
        objXml.send(null);
        var retstr = objXml.responseText;

        if (retstr.indexOf("false") != -1) {
            alert("对不起，此网站域名已经被注册过，请更换域名");
            return false;
        }

        if ((samsitename == "") || (samsiteid= "")) {
            alert("必须选择网站模板！！！");
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
        objXml.open("POST", "checkemail.jsp?email=" + email, false);
        objXml.send(null);
        var retstr = objXml.responseText;

        if (retstr.indexOf("false") != -1) {
            alert("对不起，此邮箱已经被注册过，请更换邮箱");
            return false;
        }
        /* if(copyright=="")
         {
         alert("copyright不能为空");
         return false;
         }   */
        if (username == "") {
            alert("请填写联系人姓名");
            return false;
        }
        if (myphone == "") {
            alert("请填写联系电话");
            return false;
        }
        if (myphone != "") {
            var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
            flag = filter.test(myphone);
            if (!flag) {
                alert("电话输入有误，请重新输入！");
                return false;
            }
        }

        if (yanzhengma == "") {
            alert("验证码不正确");
            return false;
        }
        if (yanzhengma.length != 4) {
            alert("验证码不正确");
            return false;
        }
        var tongyi = document.getElementById("tongyi");

        if (!tongyi.checked) {
            alert("我已看过并同意条款才能通过");
            return false;
        }
        //document.form.action = "register.jsp?flag=1";
        //document.form.submit();

        return true;
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
                        <table>
                            <tr>
                                <td><a href="index.jsp"><img src="images/coosite_14.gif" border="0"/></a></td>
                                <td><a href="/webbuilder/register/about.html"><img src="images/coosite_15.gif"
                                                                                   border="0"/></a></td>
                                <td><a href="/webbuilder/joincompany/login.jsp"><img src="images/coosite_16.gif"
                                                                                     border="0"/></a></td>
                                <td><a href="/webbuilder/register/webpubindex.jsp"><img src="images/coosite_17.gif"
                                                                                        border="0"/></a></td>
                                <td><a href="#"><img src="images/coosite_18.gif" border="0"/></a></td>
                                <td><a href="/webbuilder/register/gettouch.html"><img src="images/coosite_19.gif"
                                                                                      border="0"/></a></td>
                            </tr>
                        </table>
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
                <form method="post" name="regform" action="register.jsp?flag=1" enctype="multipart/form-data" onsubmit="return tijiao();">
                    <tr>
                        <input type="hidden" value="0" name="SHARETEMPLATENUM">
                        <td width="40">&nbsp;</td>
                        <td width="335" align="left" valign="top">
                            <table width="330" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="117" height="20" align="left">带<font color="red">(*)</font>为必填项</td>
                                    <td width="213" align="left">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">用户名<font color="red">(*)</font>：</td>
                                    <td align="left" valign="middle"><label>
                                        <input name="name" type="text" class="txt_inde" id="name"
                                               style="vertical-align:middle" onblur="javascript:changecoositename()"/>
                                    </label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">密 &nbsp;码<font color="red">(*)</font>：
                                    </td>
                                    <td align="left" valign="top"><label>
                                        <input name="pass" type="password" id="pass" class="txt_inde"/>
                                    </label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">再次输入密码<font color="red">(*)</font>：</td>
                                    <td align="left" valign="top"><label>
                                        <input name="confpass" id="confpass" type="password" class="txt_inde"/>
                                    </label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" class="grey_font">域&nbsp;名<font color="red">(*)</font>：</td>
                                    <td align="left" valign="top" class="grey_font">
                                        <input name="coositename" id="coositeid" type="text" class="txt_inde" />
                                    </td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" height="19" class="grey_font">选择网站模板：</td>
                                    <td align="left" valign="top">
                                        <input type="text" name="samsitename" id="samsitenameid" readonly="true"/>
                                        <input type="hidden" name="samsiteid" id="samsiteidid" value=""/>
                                        <input type="hidden" name="samsitetemplate" id="samsitetemplateid" value="0"/>
                                        <input type="button" name="seltamplate" id="seltemplateid" value="选择" onclick="javascript:sel_template()"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">电子邮箱<font color="red">(*)</font>：</td>
                                    <td align="left" valign="top"><label>
                                        <input name="email" id="email" type="text" class="txt_inde"/>
                                    </label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">联系人<font color="red">(*)</font>：</td>
                                    <td align="left" valign="top">
                                        <input name="username" id="username" type="text" class="txt_inde"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">联系电话<font color="red">(*)</font>：</td>
                                    <td align="left" valign="top">
                                        <input name="myphone" id="myphone" type="text" class="txt_inde"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">联系地址：</td>
                                    <td align="left" valign="top">
                                        <input name="myaddress" id="myaddress" type="text" class="txt_inde"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">Copyright：</td>
                                    <td align="left" valign="top"><label>
                                        <textarea name="copyright" id="copyright"></textarea>
                                    </label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="top" class="grey_font">验证码<font color="red">(*)</font>：</td>
                                    <td align="left" valign="top"><label>
                                        <INPUT type="text" class="txt_fact" id=Codes style="WIDTH: 72px" name=Codes>
                                        <img src="image.jsp" alt="验证码" align="middle"/></label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="middle" class="grey_font">&nbsp;</td>
                                    <td align="left" valign="top"><label>
                                        <input type="checkbox" id="tongyi" name="tongyi" value="checkbox"/>
                                        我已看过并同意《盈商动力协议》</label></td>
                                </tr>
                                <tr>
                                    <td height="19" align="left"></td>
                                    <td align="left"></td>
                                </tr>
                                <tr align="right">
                                    <td><input type="image" img src="images/ok_331.jpg" width="62" height="27"/></td>
                                    <td><a href="index.jsp"><img src="images/ancel_331.jpg" width="62" height="27"
                                                                 border="0"/></a></td>
                                </tr>
                                <tr>
                                    <td align="left" valign="middle" class="grey_font">&nbsp;</td>
                                    <td align="left" valign="top"></td>
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
