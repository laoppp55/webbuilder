<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.*" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    String username = ParamUtil.getParameter(request, "username");
    String passwd = ParamUtil.getParameter(request, "passwd1");
    String name = ParamUtil.getParameter(request, "name");
    String phone = ParamUtil.getParameter(request, "phone");
    String company = ParamUtil.getParameter(request, "company");
    int usertype = ParamUtil.getIntParameter(request, "usertype", 0);
    if (startflag == 1) {
        Uregister reg = new Uregister();
        IUregisterManager regMgr = UregisterPeer.getInstance();
        reg.setName(username);
        regMgr.insertRegister(reg);
%>
<script type="text/javascript">
    alert("注册成功！");
</script>
<%
        response.sendRedirect("denglu.jsp");
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>注册页面</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" type="text/javascript">
        String.prototype.length2 = function() {
            var cArr = this.match(/[^\x00-\xff]/ig);
            return this.length + (cArr == null ? 0 : cArr.length);
        }
        function checkname() {
            var userid = document.form1.userid.value;
            if (userid.length2() < 3 || userid.length2() > 15) {
                alert('输入的用户名长度应该在3-15位，请重新填写用户名！');
                return false;
            }
            var date = new Date();
            var xmlhttp;
            if (window.ActiveXObject)
            {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest)
            {
                xmlhttp = new XMLHttpRequest();
            }
            xmlhttp.open("POST", "checkname.jsp?userid=" + userid, false);
            xmlhttp.send(null);
            var retstr = xmlhttp.responseText.replace(/\s/ig, '');
            //var retstr = xmlhttp.responseText;
            if (retstr != "success") {
                document.form1.nameflag.value = "1";
                document.getElementById("usernameflag").innerHTML = "<font color=red>" + retstr + "</font>";
                return false;
            } else if (retstr == "success") {
                document.form1.nameflag.value = "0";
                document.getElementById("usernameflag").innerHTML = "<font color=green>" + "有效的用户名" + "</font>";
            }
        }

        function check() {
            if (document.form1.nameflag.value == "1") {
                alert('您输入的用户名无效！');
                return false;
            }
            if (document.form1.userid.value.length2() < 3 || document.form1.userid.value.length2() > 15) {
                alert('输入的用户名长度应该在3-15位，请重新填写用户名！');
                return false;
            }
            if (document.form1.userid.value == "") {
                alert('请输入用户名！');
                return false;
            } else if (document.form1.userid.value.indexOf("@") != -1) {
                alert('用户名不能含有@,请重新输入用户名！');
                return false;
            }

            if (document.form1.passwd1.value == "") {
                alert('密码不能为空!');
                return false;
            } else {
                if (document.form1.passwd1.value.length < 6 || document.form1.passwd1.value.length > 16) {
                    alert('密码长度与要求不相符合！');
                    return false;
                }
            }
            if (document.form1.passwd2.value != document.form1.passwd1.value) {
                alert('确认密码与密码不相同,请重新输入确认密码!');
                return false;
            }
            if (document.form1.name.value == "") {
                alert('请填写姓名！');
                return false;
            }
            if (document.form1.phone.value == "") {
                alert('请填写联系电话！');
                return false;
            }
            if (document.form1.company.value == "") {
                alert('请填写所在单位！');
                return false;
            }
            document.form1.action = "zhuce.jsp";
            document.form1.submit();
        }


    </script>
</head>

<body>
<form name=form1 method="post">
    <input type="hidden" name="nameflag" value="0"/>
    <input type="hidden" name="startflag" value="1"/>
    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center" valign="top">
                <table width="700" border="0" cellpadding="0" cellspacing="6" class="paddingtrlb">
                    <tr>
                        <td height="30" align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
                        <td align="left" valign="top" bgcolor="#FFFFFF" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="30" colspan="3" align="center" valign="top" bgcolor="#FFFFFF" class="fonttitle">企业用户注册&nbsp; </td>
                    </tr>
                    <tr>
                        <td width="44%" align="right" valign="middle" bgcolor="#FFFFFF">用户ID：</td>
                        <td width="56%" height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="userid" type="text" class="textborder" onblur="checkname()"/>
                        </label></td>
                        <td width="60%" align="left">
                            <div style="width:300px" id="usernameflag">请输入您要使用的用户名</div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">密码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="passwd1" type="password" class="textborder"/>
                        </label></td>
                        <td align="left">密码由6-16个字符组成，请使用英文字母、数字或符号。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">确认密码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="passwd2" type="password" class="textborder"/>
                        </label></td>
                        <td align="left">请再输入一遍您上面输入的密码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构代码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_gode" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入组织机构代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构名称：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_name" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入组织机构名称。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">地区代码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_area_code" type="text" value="110000" class="textborder"/>
                        </label></td>
                        <td align="left">请输入地区代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">系统/行业代码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <select name="org_sys_code">
                                <option value="-1" selected>选择系统/行业代码</option>
                            </select>
                        </label></td>
                        <td align="left">请输入系统/行业代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">单位类型：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <select name="org_type">
                                <option value="-1" selected>选择单位类型</option>
                            </select>
                        </label></td>
                        <td align="left">请输入单位类型。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">单位联系人：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_link_person" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入单位联系人。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">联系人身份证号码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_person_id" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入联系人身份证号码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">上级组织机构：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_sup_code" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">        请输入地区代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构地址：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_addr" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入组织机构地址。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构邮编：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_post" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入组织机构邮编。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">联系电话：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_phone" type="text" class="textborder"/>
                        </label></td>
                        <td align="left">请输入联系电话。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">手机号码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_phone   " type="text" class="textborder"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构传真：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_fax" type="text" class="textborder"/>
                        </label></td>
                    </tr>                    
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">开户银行：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_bank" type="text" class="textborder"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">账户名称：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_account_name" type="text" class="textborder"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">银行账号：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_account" type="text" class="textborder"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">设台单位性质：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <select name="org_hostility">
                                <option value="-1" selected>选择设台单位性质</option>
                            </select>
                        </label></td>
                        <td align="left">请输入设台单位性质。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">网址：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_web_site" type="text" class="textborder"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">电子邮箱：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_mail" type="text" class="textborder"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="bottom" bgcolor="#FFFFFF">&nbsp;</td>
                        <td height="25" align="left" valign="bottom" bgcolor="#FFFFFF" colspan="2"><label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" name="Submit" value="确认" onclick="check()"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td height="30" align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
                        <td align="left" valign="top" bgcolor="#FFFFFF" colspan="2">&nbsp;</td>
                    </tr>
                </table>
            </td>
            <td>&nbsp;</td>
        </tr>
    </table>
</form>
</body>
</html>
