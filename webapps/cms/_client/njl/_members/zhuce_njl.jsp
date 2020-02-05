<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.register.IRegisterManager" %>
<%@ page import="com.bizwink.cms.register.RegisterPeer" %>
<%@ page import="com.bizwink.cms.register.Register" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page contentType="text/html;charset=GBK" %>
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
        int sam_site_type = 0;
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

        String SHARETEMPLATENUM = mySmartUpload.getRequest().getParameter("SHARETEMPLATENUM");
        int sharetemplatenum = 0;
        if (SHARETEMPLATENUM != null) {
            sharetemplatenum = Integer.parseInt(SHARETEMPLATENUM);
        }

        //获取实例站点ID和实例站点名称
        String s_sam_site = mySmartUpload.getRequest().getParameter("samsite");
        String samsitename = "";
        if (s_sam_site!=null) {
            ISiteInfoManager sitemgr = SiteInfoPeer.getInstance();
            sam_site = Integer.parseInt(s_sam_site);
            SiteInfo siteinfo = sitemgr.getSiteInfo(sam_site);
            samsitename = siteinfo.getDomainName();
        }
        //获取实例站点类型 ,判断是共享样例网站结构，还是拷贝样例网站结构
        String s_sam_site_type = mySmartUpload.getRequest().getParameter("samsitetype");
        if (s_sam_site_type!=null) sam_site_type = Integer.parseInt(s_sam_site_type);

        if (getSessionCode.equals(getCode)) {
            String uploadPath = realPath + "sites" + java.io.File.separator + StringUtil.replace(sitename, ".", "_") + java.io.File.separator + "images" + java.io.File.separator;
            java.io.File file = new java.io.File(uploadPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            mySmartUpload.save(uploadPath);

            com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
            com.jspsmart.upload.File logoFile = uploadFiles.getFile(0);
            com.jspsmart.upload.File bannerFile = uploadFiles.getFile(1);

            IRegisterManager regmgr = RegisterPeer.getInstance();
            //String ip = regmgr.getIPByCode();
            Register register = new Register();
            register.setUserID(name);
            register.setUsername(username);
            register.setMphone(myphone);
            register.setAddress(myaddress);
            register.setSiteName(sitename);
            register.setLogo(logoFile.getFileName());
            register.setBanner(bannerFile.getFileName());
            register.setNavigator(nav);
            if (sam_site_type == 0)
                //如果是拷贝的样例网站，则设置样例网站的站点ID为0，与独立注册的网站一样。
                register.setSamsite(0);
            else
                //如果是共享样例网站，则保存共享样例网站的ID到samsite字段
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
            register.setSharetemplatenum(sharetemplatenum);
            register.setCopyright(copyright);
            int siteid = regmgr.insertcreate(register, realPath);
            if (siteid > 0) {
                if (sam_site_type == 0) {
                    regmgr.copySamSite(sam_site, siteid, name, sitename, realPath);
                    response.sendRedirect("/webbuilder/register/index.jsp");
                } else {
                    System.out.println("create a shared web site,shared the sam_site website");
                    samsitename = StringUtil.replace(samsitename, ".", "_");
                    sitename = StringUtil.replace(sitename, ".", "_");
                    regmgr.copy_CSS_AND_SCRIPT_ToCMS_AND_WEB(username,samsitename,siteid, sitename, realPath);
                    response.sendRedirect("/webbuilder/register/index.jsp");
                }
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
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
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
    //window.open("../register/webindex.jsp","selectmodel","top=0,left=0,width=800,height=600");
    window.open("../register/websamindex.jsp", "selectmodel");
}

function changecoositename() {
    for (var i = 0; i < regform.domaintype.length; i++) {
        if (regform.domaintype[i].checked) {
            value = regform.domaintype[i].value;
            break;
        }
    }

    if (value == 0) {
        regform.coositename.value = regform.name.value + ".coosite.com";
        regform.sitename.value = "";
        regform.sitename.readOnly = true;
    }
}

function selectsitename(val) {
    if (val == 0) {
        if (regform.name.value != "" && regform.name.value != null) {
            regform.sitename.value = "";
            regform.sitename.readOnly = true;
            regform.coositename.value = "";
            regform.coositename.value = regform.name.value + ".coosite.com";
        } else {
            alert("请先填写用户名");
        }
    }
    if (val == 1) {
        regform.sitename.readOnly = false;
        regform.coositename.value = "";
    }
}
function tijiao() {
    var name = document.getElementById("name").value;
    var pass = document.getElementById("pass").value;
    var confpass = document.getElementById("confpass").value;
    var email = document.getElementById("email").value;
    var yanzhengma = document.getElementById("Codes").value;
    var sitename = document.getElementById("sitename").value;
    var samsitename = document.getElementById("sitetemplateid").value;
    var samsiteid = document.getElementById("samsiteid").value;
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

    if (value == 1) {
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
        //检查电子邮件地址是否存在
        objXml.open("POST", "checkhost.jsp?host=" + sitename, false);
        objXml.send(null);
        var retstr = objXml.responseText;

        if (retstr.indexOf("false") != -1) {
            alert("对不起，此网站域名已经被注册过，请更换域名");
            return false;
        }
    } else {
        sitename = document.getElementById("coositename").value;
    }

    //alert(sitename);
     if (sitename.indexOf("coosite.com") != -1) {
         if ((samsitename == "") || (samsiteid= "")) {
             alert("必须选择网站模板！！！");
             return false;
         }
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
            <form method="post" name="regform" action="register.jsp?flag=1" enctype="multipart/form-data"
                  onsubmit="return tijiao();">
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
                                <td align="left" class="grey_font"><input type="radio" name="domaintype" value="0"
                                                                          onclick="javascript:selectsitename(0);"/>
                                    COOSITE域名
                                </td>
                                <td align="left" valign="top" class="grey_font"><input name="coositename" id="coositeid"
                                                                                       type="text" class="txt_inde"
                                                                                       readonly="true"/></td>
                            </tr>
                            <tr>
                                <td align="left" valign="top" class="grey_font">&nbsp;</td>
                                <td align="left" valign="top" class="grey_font">&nbsp;</td>
                            </tr>
                            <tr>
                                <td align="left" class="grey_font"><input type="radio" name="domaintype"
                                                                          checked="checked" value="1"
                                                                          onclick="javascript:selectsitename(1);">
                                    自定义域名<font color="red">(*)</font></td>
                                <td align="left" class="grey_font"><input name="sitename" id="sitenameid" type="text"
                                                                          class="txt_inde"/>
                                </td>
                            </tr>
                            <tr>
                                <td height="19" align="left"></td>
                                <td align="left"></td>
                            </tr>
                            <tr>
                                <td align="left" height="19" class="grey_font">站点LOGO：</td>
                                <td align="left" valign="top">
                                    <input type="file" name="logofile" id="logofileid" class="txt_logo_banner"/>
                                </td>
                            </tr>
                            <tr>
                                <td height="19" align="left"></td>
                                <td align="left"></td>
                            </tr>
                            <tr>
                                <td align="left" height="19" class="grey_font">形象图标：</td>
                                <td align="left" valign="top">
                                    <input type="file" name="bannerfile" id="bannerfileid" class="txt_logo_banner"/>
                                </td>
                            </tr>
                            <!--tr>
                                <td align="left" height="19" class="grey_font">导航样式：</td>
                                <td align="left" valign="top">
                                    <input name="mnavgator" id="mnavgatorid" type="text" class="txt_choice" readonly="true"/>
                                    <input name="navgator" id="navgatorid" type="hidden" class="txt_choice" readonly="true"/>
                                    <input type="button" name="selnav" id="selnavid" value="选择" onclick="javascript:sel_navigator()" />
                                </td>
                            </tr-->
                            <tr>
                                <td height="19" align="left"></td>
                                <td align="left"></td>
                            </tr>
                            <tr>
                                <td align="left" height="19" class="grey_font">网站模板：</td>
                                <td align="left" valign="top">
                                    <input name="sitetemplate" id="sitetemplateid" type="text" readonly="true"/>
                                    <input name="samsite" id="samsiteid" type="hidden" value="" readonly="true"/>
                                    <input name="samsitetemplate_no" id="samsitetemplate_no_id" value="" type="hidden" readonly="true"/>
                                    <input type="button" name="seltamplate" id="seltemplateid" value="选择" onclick="javascript:sel_template()"/>
                                    <input name="samsitetype" id="samsitetypeid" type="radio" value="1" checked="true"/>共享模板网站
                                    <input name="samsitetype" id="samsitetypeid1" type="radio" value="0"/> 拷贝模板网站
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
