<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.shetuan.ISheTuanManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.shetuan.SheTuanPeer" %>
<%@ page import="com.bizwink.cms.bjhqfw.shetuan.SheTuan" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    String stname = ParamUtil.getParameter(request,"stname");
    String lianxiren = ParamUtil.getParameter(request,"lianxiren");
    String phone = ParamUtil.getParameter(request,"phone");
    String email = ParamUtil.getParameter(request,"email");
    String username = ParamUtil.getParameter(request,"username");
    String passwd = ParamUtil.getParameter(request,"passwd");

    if(startflag == 1){
        ISheTuanManager sMgr = SheTuanPeer.getInstance();
        SheTuan st = new SheTuan();
        st.setStname(stname);
        st.setLianxiren(lianxiren);
        st.setPhone(phone);
        st.setEmail(email);
        st.setUsername(username);
        st.setPasswd(passwd);
        sMgr.createSheTuan(st);
        response.sendRedirect("index.jsp");
    }

%>
<html>
<head>
    <title>社团用户管理</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <meta http-equiv="Pragma" content="no-cache">
    <style type="text/css">
        TABLE {FONT-SIZE: 12px;word-break:break-all}
        BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
        A:link {text-decoration:none;line-height:20px;}
        A:visited {text-decoration:none;line-height:20px;}
        A:active {text-decoration:none;line-height:20px; font-weight:bold;}
        A:hover {text-decoration:none;line-height:20px;}
    </style>
    <script language="javascript">
        function check(){
            if(document.form.stname.value == ""){
                alert("请输入社团名称！");
                return false;
            }
            if(document.form.lianxiren.value == ""){
                alert("请输入联系人！");
                return false;
            }
            if(document.form.phone.value == ""){
                alert("请输入电话！");
                return false;
            }

            /*if(document.form.email.value == ""){
                alert("请输入邮箱！");
                return false;
            }else{
                var strm = document.form.email.value   //提交mail地址的文本框
                //var regm = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;//验证Mail的正则表达式,^[a-zA-Z0-9_-]:开头必须为字母,下划线,数字,
                var regm = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;//验证Mail的正则表达式,包括国外邮箱,
                if (!strm.match(regm) && strm!="")
                {
                    alert("邮箱地址格式错误或含有非法字符!\n请检查！");
                    document.form.email.select();
                    return false;
                }
            }*/

            var username = document.form.username.value;
            if(username == "" || username == null){
                alert("请输入用户名！");
                return false;
            } else {
                var objXmlc;
                var ref = window.location.href;
                if (window.ActiveXObject){
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                }else if (window.XMLHttpRequest){
                    objXmlc = new XMLHttpRequest();
                }

                objXmlc.open("POST", "/_commons/checkShetuanUsername.jsp?username="+username,false);
                objXmlc.send(null);
                var res = objXmlc.responseText;
                var re = res.split('-');              
                if(re[0]==0){
                    var message = "<font color=red>用户名可以使用！！<font>";
                    document.getElementById("p_register_user").innerHTML = message;
                }if(re[0]==1){
                    alert("用户名已经被注册不可以使用！！");
                    var message = "<font color=red>用户名已经被注册不可以使用！！<font>";
                    document.getElementById("p_register_user").innerHTML = message;
                    return false;
                }
            }

            if(document.form.passwd.value == ""){
                alert("请输入用户口令！");
                return false;
            }else{
                if(document.form.passwd.value.length < 6 || document.form.passwd.value.length > 16){
                    alert("密码长度与要求不相符合！");
                    return false;
                }
            }
            if(document.form.passwd.value != document.form.passwd1.value){
                alert("俩次输入的密码不相同，请重新输入！");
                return false;
            }
            document.form.action = "create.jsp";
            document.form.submit();
        }
    </script>
</head>
<body>
<form name="form" action="" method="post">
    <input type="hidden" name="startflag" value="1">
    <center>
        <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
            <tr>
                <td align="center">
                    <table width="70%" border="0" cellpadding="0">
                        <tr bgcolor="#F4F4F4" align="center">
                            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">增加社团用户（<b>红色星号表示该项为必填项</b>）</td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="center">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">社团名称：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="text" name="stname" value=""> <font color=red>*</font></td>
                                        <td align="left"></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">联系人：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="text" name="lianxiren" value=""> <font color=red>*</font></td>
                                        <td align="left"></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">电话：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="text" name="phone" value=""> <font color=red>*</font></td>
                                        <td align="left"></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">邮件：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="text" name="email" value=""></td>
                                        <td align="left"></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">用户名：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="text" name="username" value=""> <font color=red>*</font></td>
                                        <td align="left"><span id="p_register_user"></span></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">用户口令：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="password" name="passwd" value=""> <font color=red>*</font></td>
                                        <td align="left"></td>
                                    </tr>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="right">确认用户口令：&nbsp;</td>
                                        <td align="left" width="50%">&nbsp;<input type="password" name="passwd1" value=""> <font color=red>*</font></td>
                                        <td align="left"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td align="center">
                                <input type="button" name="ok" value=" 注 册 " onclick="check()">
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="button" name="ok" value=" 返 回 " onclick=javascript:history.go(-1);>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    </center>
</form>
</body>
</html>