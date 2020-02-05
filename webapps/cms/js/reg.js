function gel(a) {
    return document.getElementById ? document.getElementById(a) : null;
}
function gelstn(a) {
    return document.getElementsByTagName ? document.getElementsByTagName(a) : new Array();
}
function geln(a) {
    return document.getElementsByName ? document.getElementsByName(a) : new Array();
}
function $(a) {
    document.write(a);
}
function setfocus(a) {
    gel(a).className = "focus";
    gel(a).innerHTML = msg[a];
}
function setblur(a) {
    gel(a).className = "blur";
}
function fIsNumber(sV, sR) {
    var sTmp;
    if (sV.length == 0) {
        return (false);
    }
    for (var i = 0; i < sV.length; i++) {
        sTmp = sV.substring(i, i + 1);
        if (sR.indexOf(sTmp, 0) == -1) {
            return (false);
        }
    }
    return (true);
}
// 提示信息
var msg = new Array();
msg['info1'] = "用户名由小写字母\\中文\\数字组成";
msg['info2'] = "请点击左边的按钮检测您的用户名是否已经被其他人注册过了。";
msg['info3'] = "密码由6-20个字符组成，请使用英文字母加数字或符号的组合";
msg['info4'] = "请再输入一遍您上面输入的密码。";
msg['info5'] = "用户类型";
msg['info6'] = "请输入右边的数字";
msg['info8'] = "请填写有效的Email地址";
// 通过状态
var reg_1 = 0;
//用户名
var reg_2 = 0;
//确认注册用户id
var reg_3 = 0;
//密码
var reg_4 = 0;
//确认密码
var reg_5 = 0;
//用户类型
var reg_6 = 0;
//验证码
var reg_7 = 0;
//email
var reg_8 = 0;

var msg_username = "";
var msg_password = "";
var msg_password2 = "";
var msg_usertype = "";
var msg_authnum = "";
var msg_email = "";
var message = "";
var form = gel("form");
// 检测用户名是否已经存在
function check_user(x) {
    reg_1 = 0;
    var form = gel("form");
    if (form.UserName.value == "") {
        message = "<font color=red>请输入用户名！<font>";
        gel(x).innerHTML = message;
        gel(x).className = "fall";
        return false;
    }
    if (form.UserName.value.length < 5 || form.UserName.value.length > 20) {
        message = "<font color=red>用户名长度应该在5－20个字符之间，请重新输入用户名！</font>";
        gel(x).innerHTML = message;
        gel(x).className = "fall";
        return false;
    }
    message = "<font color=green>用户名格式正确！</font>";
    reg_1 = 1;
    gel(x).className = "true";
    //检测用户名是否存在
    reg_2 = 0;
    var form = gel("form");
    var username = form.username.value;
    //var usernames = gel("info1");
    if (reg_1 == 0 || username == "" || username == null) {
        gel("info1").innerHTML = "<font color=red>请先填写用户名！</font>";
        gel("info1").className = "fall";
        return;
    }
    message = "<font color=green>正在检测中，请稍候...</font>";
    gel("info1").innerHTML = message;
    gel("info1").className = "focus";
    //window.setTimeout('doCheckusername("' + username + '")', 300);

    if (window.XMLHttpRequest) {
        userreq = new XMLHttpRequest();
        userreq.onreadystatechange = processChech;
        userreq.open("POST", "/checkUsername.jsp?username=" + username, false);
        userreq.send(null);
    } else if (window.ActiveXObject) {
        
        userreq = new ActiveXObject("Microsoft.XMLHTTP");
        if (userreq) {
            userreq.onreadystatechange = processChech;
            userreq.open("POST", "/checkUsername.jsp?username=" + username, false);
            userreq.send();
        }
    }
    function processChech() {
        reg_2 = 0;
        if (userreq.readyState == 4) {
            if (userreq.status == 200) {
                gel("info1").innerHTML = userreq.responseText;
                if (userreq.responseText.indexOf("false") != -1) {
                    gel("info1").className = "true";
                    gel("info1").innerHTML = "<font color=green>该用户名还没有被注册！!</font>";
                    reg_2 = 1;
                } else {
                    reg_2 = 0;
                    gel("info1").className = "fall";
                    gel("info1").innerHTML = "<font color=red>该用户名已被注册！!</font>";
                    gel("info1").focus();
                }
            } else {
                gel("info1").innnerHTML = "<font color=green>用户名检查服务暂时不能使用，不过您可以继续申请！</font>";
                gel("info1").className = "true";
            }
        }
    }
    //gel(x).innerHTML = message;

}

// 检测密码
function checkpass1(x) {
    reg_3 = 0;
    var form = gel("form");
    var password1 = form.txtPassWord.value;
    var Condition1 = (password1.length < 6 || password1.length > 20);
    var Condition2 = (password1.indexOf("&") != -1 || password1.indexOf("%") != -1 || password1.indexOf("=") != -1 || password1.indexOf("+") != -1 || password1.indexOf("'") != -1);
    if (Condition1) {
        message = "<font color=red>密码长度必须为6-20个字符，请重新输入！</font>";
        gel(x).innerHTML = message;
        gel(x).className = "fall";
        return false;
    } else if (Condition2) {
        message = "<font color=red>密码中不允许含有&,%,=,+,'字符，请重新输入密码！</font>";
        gel(x).innerHTML = message;
        gel(x).className = "fall";
        return false;
    } else {
        message = "<font color=green>密码格式正确，请继续！</font>";
        gel(x).innerHTML = message;
        gel(x).className = "true";
        reg_3 = 1;
        return false;
    }
}

// 检测密码2
function checkpass2(x) {
    reg_4 = 0;
    var form = gel("form");
    var password1 = form.txtPassWord.value;
    var password2 = form.txtConfirmPW.value;
    var Condition3 = (password2 == "");
    var Condition4 = (password2 != password1);
    if (Condition3) {
        gel(x).innerHTML = "<font color=red>确认密码不能为空，请重新输入！</font>";
        gel(x).className = 'fall';
        return false;
    } else if (Condition4) {
        gel(x).innerHTML = "<font color=red>两次输入密码不相同，请重新输入确认密码！</font>";
        gel(x).className = 'fall';
        return false;
    } else {
        gel(x).innerHTML = "<font color=green>确认密码正确，请继续！</font>";
        gel(x).className = 'true';
        reg_4 = 1;
        return false;
    }
}

//email
function emailValidate(emailStr) {

    var checkTLD = 1;
    var knownDomsPat = /^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|mobi)$/;
    var emailPat = /^(.+)@(.+)$/;
    var specialChars = "\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
    var validChars = "\[^\\s" + specialChars + "\]";
    var quotedUser = "(\"[^\"]*\")";
    var ipDomainPat = /^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
    var atom = validChars + '+';
    var word = "(" + atom + "|" + quotedUser + ")";
    var userPat = new RegExp("^" + word + "(\\." + word + ")*$");
    var domainPat = new RegExp("^" + atom + "(\\." + atom + ")*$");
    var matchArray = emailStr.match(emailPat);

    if (matchArray == null) {
        return false;
    }

    var user = matchArray[1];
    var domain = matchArray[2];

    for (i = 0; i < user.length; i++) {
        if (user.charCodeAt(i) > 127) {
            return false;
        }
    }

    for (i = 0; i < domain.length; i++) {
        if (domain.charCodeAt(i) > 127) {
            return false;
        }
    }

    if (user.match(userPat) == null) {
        return false;
    }


    var IPArray = domain.match(ipDomainPat);
    if (IPArray != null) {
        // this is an IP address
        for (var i = 1; i <= 4; i++) {
            if (IPArray[i] > 255) {
                return false;
            }
        }
        return true;
    }

    // Domain is symbolic name.  Check if it's valid.

    var atomPat = new RegExp("^" + atom + "$");
    var domArr = domain.split(".");
    var len = domArr.length;
    for (i = 0; i < len; i++) {
        if (domArr[i].search(atomPat) == -1) {
            return false;
        }
    }

    if (checkTLD && domArr[domArr.length - 1].length != 2 && domArr[domArr.length - 1].search(knownDomsPat) == -1) {
        return false;
    }

    if (len < 2) {
        return false;
    }

    return true;
}

function checkemail(x) {
    var form = gel("form");
    var getemail = form.txtEmail.value;
    if (!emailValidate(form.txtEmail.value)) {
        gel(x).innerHTML = "<font color=red>请填写正确的电子邮件地址</font>";
        gel(x).className = 'fall';
        reg_1 = 0;
        return false;
    } else {
        //gel(x).innerHTML = "<font color=green>电子邮件地址正确，请继续！</font>";
        gel(x).className = 'true';
        reg_1 = 1;
        if (window.XMLHttpRequest) {
            emailreq = new XMLHttpRequest();
            emailreq.onreadystatechange = processEmail;
            emailreq.open("POST", "/checkEmail.jsp?email=" + getemail, false);
            emailreq.send(null);
        } else if (window.ActiveXObject) {
            emailreq = new ActiveXObject("Microsoft.XMLHTTP");
            if (emailreq) {
                emailreq.onreadystatechange = processEmail;
                emailreq.open("POST", "/checkEmail.jsp?email=" + getemail, false);
                emailreq.send();
            }
        }
        return false;
    }
    function processEmail() {
        if (emailreq.readyState == 4) {
            if (emailreq.status == 200) {
                var checkemail = emailreq.responseText;
                if (checkemail.indexOf("true") != -1) {
                    gel("info8").innerHTML = "<font color=red>电子邮件已注册过，请重新输入！</font>";
                    gel("info8").className = 'fall';
                    reg_8 = 0;
                } else {
                    gel("info8").innerHTML = "<font color=green>电子邮件没有被注册过！</font>";
                    gel("info8").className = 'true';
                    reg_8 = 1;
                }
            } else {
                gel("info8").innnerHTML = "<font color=green>email检查服务暂时不能使用，不过您可以继续申请！</font>";
                gel("info8").className = "true";
            }
        }

    }
}

//检测用户类型
function check_usertype(x) {
    reg_5 = 0;
    var form = gel("form");
    var gettype = 0;
    for (var i = 0; i < form.usertype.length; i++) {
        if (document.form.usertype[i].checked) {
            gettype += 1;
        }
    }
    if (gettype == 0) {
        gel(x).innerHTML = "用户类别为空，请重新选择！";
        gel(x).className = 'fall';
        return false;
    } else {
        gel(x).innerHTML = "用户类别已选择，请继续！";
        gel(x).className = 'true';
        reg_5 = 1;
        return false;
    }
}

//检测认证码
function check_tag(x) {
    var form = gel("form");
    var getcode = form.txtVerify.value;
    if (getcode == "") {
        gel(x).innerHTML = "<font color=red>请输入验证码</font>";
        gel(x).className = 'fall';
        return false;
    } else if (form.txtVerify.value.length != 4) {
        gel(x).innerHTML = "<font color=red>验证码不正确</font>";
        gel(x).className = 'fall';
        return false;
    } else {
        gel(x).innerHTML = "<font color=green>验证码格式正确!</font>";
        gel(x).className = 'true';
        reg_6 = 1;
        return true;
    }
}

//服务条款
function check_service(x) {
    reg_7 = 0;
    var form = gel("form");
    if (form.service.checked == false) {
        gel(x).innerHTML = "你必须同意服务条款才能完成注册。";
        gel(x).className = 'fall';
    } else {
        gel(x).innerHTML = "";
        gel(x).className = 'none';
        reg_7 = 1;
    }
}

//检测表单数据完整性
function fCheck() {
    //info1		用户名
    //info2		用户名检测
    //info3		密码
    //info4		二次密码
    //info5     用户类型
    //info6  	验证码
    //info7  	服务条款

    checkuser('info1');
    checkUsername('info1');
    checkpasswd1('info3');
    checkpasswd2('info4');
    //checkusertype('info5');
    checktag('info6');
    //checkservice('info7');
    checkemails('info8');
    var form = gel("form");
    function checkuser(x) {
        reg_1 = 0;
        //gel("info1").className = "blur noback";
        //gel("info1").innerHTML = "";

        var form = gel("form");
        if (form.UserName.value == "") {
            message = "<font color=red>请输入用户名！</font>";
            gel(x).innerHTML = message;
            gel(x).className = "fall";
            return false;
        }
        else if (form.UserName.value.length < 5 || form.UserName.value.length > 20) {
            message = "<font color=red>用户名长度应该在5－20个字符之间，请重新输入用户名！</font>";
            gel(x).innerHTML = message;
            gel(x).className = "fall";
            return false;
        }
        /*else if (fIsNumber(form.UserName.value.charAt(0), "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ") != 1) {
            message = "用户名只能以字母开头，请重新输入用户名！";
            gel(x).innerHTML = message;
            gel(x).className = "fall";
            return false;
        }
        else if (fIsNumber(form.UserName.value, "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-") != 1) {
            message = "用户名应该是数字、字母、下划线、连字符号，不允许出现汉字、空格、点等其他字符，请重新输入用户名！";
            gel(x).innerHTML = message;
            gel(x).className = "fall";
            return false;
        }*/
        else {
            message = "<font color=green>用户名格式正确！</font>";
            reg_1 = 1;
            gel(x).className = "true";
            gel(x).innerHTML = message;
        }
    }

    function checkUsername(x) {
        reg_2 = 0;
        var form = gel("form");
        var username = form.username.value;
        //var usernames = gel("info1");
        if (reg_1 == 0 || username == "" || username == null) {
            gel(x).innerHTML = "<font color=red>请先填写用户名！</font>";
            gel(x).className = "fall";
            return;
        }
        message = "<font color=green>正在检测中，请稍候...</font>";
        gel(x).innerHTML = message;
        gel(x).className = "focus";
        //window.setTimeout('doCheckusername("' + username + '")', 300);

        if (window.XMLHttpRequest) {
            userreq = new XMLHttpRequest();
            userreq.onreadystatechange = processChech;
            userreq.open("POST", "/checkUsername.jsp?username=" + username, false);
            userreq.send(null);
        } else if (window.ActiveXObject) {
            userreq = new ActiveXObject("Microsoft.XMLHTTP");
            if (userreq) {
                userreq.onreadystatechange = processChech;
                userreq.open("POST", "/checkUsername.jsp?username=" + username, false);
                userreq.send();
            }
        }
    }

    // 检测密码
    function checkpasswd1(x) {
        reg_3 = 0;
        var form = gel("form");
        var password1 = form.txtPassWord.value;
        var Condition1 = (password1.length < 6 || password1.length > 20);
        var Condition2 = (password1.indexOf("&") != -1 || password1.indexOf("%") != -1 || password1.indexOf("=") != -1 || password1.indexOf("+") != -1 || password1.indexOf("'") != -1);
        if (Condition1) {
            message = "<font color=red>密码长度必须为6-20个字符，请重新输入！</font>";
            gel(x).innerHTML = message;
            gel(x).className = "fall";
            return false;
        } else if (Condition2) {
            message = "<font color=red>密码中不允许含有&,%,=,+,'字符，请重新输入密码！</font>";
            gel(x).innerHTML = message;
            gel(x).className = "fall";
            return false;
        } else {
            message = "<font color=green>密码格式正确，请继续！</font>";
            gel(x).innerHTML = message;
            gel(x).className = "true";
            reg_3 = 1;
            return false;
        }
    }

    // 检测密码2
    function checkpasswd2(x) {
        reg_4 = 0;
        var form = gel("form");
        var password1 = form.txtPassWord.value;
        var password2 = form.txtConfirmPW.value;
        var Condition3 = (password2 == "");
        var Condition4 = (password2 != password1);
        if (Condition3) {
            gel(x).innerHTML = "<font color=red>确认密码不能为空，请重新输入！</font>";
            gel(x).className = 'fall';
            return false;
        } else if (Condition4) {
            gel(x).innerHTML = "<font color=red>两次输入密码不相同，请重新输入确认密码！</font>";
            gel(x).className = 'fall';
            return false;
        } else {
            gel(x).innerHTML = "<font color=green>确认密码正确，请继续！</font>";
            gel(x).className = 'true';
            reg_4 = 1;
            return false;
        }
    }

    //email
    function emailValidate(emailStr) {

        var checkTLD = 1;
        var knownDomsPat = /^(com|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|mobi)$/;
        var emailPat = /^(.+)@(.+)$/;
        var specialChars = "\\(\\)><@,;:\\\\\\\"\\.\\[\\]";
        var validChars = "\[^\\s" + specialChars + "\]";
        var quotedUser = "(\"[^\"]*\")";
        var ipDomainPat = /^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/;
        var atom = validChars + '+';
        var word = "(" + atom + "|" + quotedUser + ")";
        var userPat = new RegExp("^" + word + "(\\." + word + ")*$");
        var domainPat = new RegExp("^" + atom + "(\\." + atom + ")*$");
        var matchArray = emailStr.match(emailPat);

        if (matchArray == null) {
            return false;
        }

        var user = matchArray[1];
        var domain = matchArray[2];

        for (i = 0; i < user.length; i++) {
            if (user.charCodeAt(i) > 127) {
                return false;
            }
        }

        for (i = 0; i < domain.length; i++) {
            if (domain.charCodeAt(i) > 127) {
                return false;
            }
        }

        if (user.match(userPat) == null) {
            return false;
        }


        var IPArray = domain.match(ipDomainPat);
        if (IPArray != null) {
            // this is an IP address
            for (var i = 1; i <= 4; i++) {
                if (IPArray[i] > 255) {
                    return false;
                }
            }
            return true;
        }

        // Domain is symbolic name.  Check if it's valid.

        var atomPat = new RegExp("^" + atom + "$");
        var domArr = domain.split(".");
        var len = domArr.length;
        for (i = 0; i < len; i++) {
            if (domArr[i].search(atomPat) == -1) {
                return false;
            }
        }

        if (checkTLD && domArr[domArr.length - 1].length != 2 && domArr[domArr.length - 1].search(knownDomsPat) == -1) {
            return false;
        }

        if (len < 2) {
            return false;
        }

        return true;
    }

    function checkemails(x) {
        var form = gel("form");
        var getemail = form.txtEmail.value;
        if (!emailValidate(form.txtEmail.value)) {
            gel(x).innerHTML = "<font color=red>请填写正确的电子邮件地址</font>";
            gel(x).className = 'fall';
            reg_1 = 0;
            return false;
        }
        /*else{
            gel("info8").className = 'true';
            reg_8 = 1;
        }*/
        else {
            if (window.XMLHttpRequest) {
                emailreq = new XMLHttpRequest();
                emailreq.onreadystatechange = processEmail;
                emailreq.open("POST", "/checkEmail.jsp?email=" + getemail, false);
                emailreq.send(null);
            } else if (window.ActiveXObject) {
                emailreq = new ActiveXObject("Microsoft.XMLHTTP");
                if (emailreq) {
                    emailreq.onreadystatechange = processEmail;
                    emailreq.open("POST", "/checkEmail.jsp?email=" + getemail, false);
                    emailreq.send();
                }
            }
            return false;
        }
    }

    function processEmail() {
        if (emailreq.readyState == 4) {
            if (emailreq.status == 200) {
                var checkemail = emailreq.responseText;
                if (checkemail.indexOf("true") != -1) {
                    gel("info8").innerHTML = "<font color=red>电子邮件已经被人被注册过，请重新输入！</font>";
                    gel("info8").className = 'fall';
                    reg_8 = 0;
                } else {
                    gel("info8").innerHTML = "<font color=green>电子邮件没有被注册过！</font>";
                    gel("info8").className = 'true';
                    reg_8 = 1;
                }
            } else {
                gel("info8").innnerHTML = "<font color=green>email检查服务暂时不能使用，不过您可以继续申请！</font>";
                gel("info8").className = "true";
            }
        }

    }


    //检测用户类型
    /*function checkusertype(x) {
        reg_5 = 0;
        var form = gel("form");
        var gettype = 0;
        for (var i = 0; i < form.usertype.length; i++) {
            if (document.form.usertype[i].checked) {
                gettype += 1;
            }
        }
        if (gettype == 0) {
            gel(x).innerHTML = "用户类别为空，请重新选择！";
            gel(x).className = 'fall';
            return false;
        } else {
            gel(x).innerHTML = "用户类别已选择，请继续！";
            gel(x).className = 'true';
            reg_5 = 1;
            return false;
        }
    }*/

    //检测认证码
    function checktag(x) {
        var form = gel("form");
        var getcode = form.txtVerify.value;
        if (getcode == "") {
            gel(x).innerHTML = "<font color=red>请输入验证码</font>";
            gel(x).className = 'fall';
            return false;
        }
        /*else if (form.txtVerify.value.length != 4) {
            gel(x).innerHTML = "验证码不正确，如果验证码不清楚，请点击刷新重新获取验证码。";
            gel(x).className = 'fall';
            return false;
        } else {
            message = "正在检测中，请稍候...";
            gel(x).innerHTML = message;
            gel(x).className = "focus";
            //window.setTimeout('doCheckcode("' + getcode + '")', 500);

            if (window.XMLHttpRequest) {
                req = new XMLHttpRequest();
                req.onreadystatechange = processChechs;
                req.open("POST", "/checkcode.jsp?code=" + getcode, false);
                req.send(null);
            } else if (window.ActiveXObject) {
                req = new ActiveXObject("Microsoft.XMLHTTP");
                if (req) {
                    req.onreadystatechange = processChechs;
                    req.open("POST", "/checkcode.jsp?code=" + getcode, false);
                    req.send();
                }
            }
        }*/
    }

    //服务条款
    /*function checkservice(x) {
        reg_7 = 0;
        var form = gel("form");
        if (form.service.checked == false) {
            gel(x).innerHTML = "你必须同意服务条款才能完成注册。";
            gel(x).className = 'fall';
        } else {
            gel(x).innerHTML = "";
            gel(x).className = 'none';
            reg_7 = 1;
        }
    }*/

    function processChech() {
        reg_2 = 0;
        if (userreq.readyState == 4) {
            if (userreq.status == 200) {
                gel("info1").innerHTML = userreq.responseText;
                if (userreq.responseText.indexOf("false") != -1) {
                    gel("info1").className = "true";
                    gel("info1").innerHTML = "<font color=green>该用户名还没有被注册！!</font>";
                    reg_2 = 1;
                } else {
                    reg_2 = 0;
                    gel("info1").className = "fall";
                    gel("info1").innerHTML = "<font color=red>该用户名已被注册！!</font>";
                    gel("info1").focus();
                }
            } else {
                gel("info1").innnerHTML = "<font color=green>用户名检查服务暂时不能使用，不过您可以继续申请！</font>";
                gel("info1").className = "true";
            }
        }
    }
    /*function processChechs() {
        reg_6 = 0;
        if (req.readyState == 4) {
            if (req.status == 200) {
                gel("info6").innerHTML = req.responseText;
                if (req.responseText.indexOf("验证码正确") != -1) {
                    gel("info6").className = "true";
                    gel("info6").innerHTML = "验证码格式正确!";
                    reg_6 = 1;
                } else {
                    reg_6 = 0;
                    gel("info6").className = "fall";
                    gel("info6").focus();
                }
            } else {
                gel("info6").innnerHTML = "验证码检查服务暂时不能使用，不过您可以继续申请！";
                gel("info6").className = "true";
            }
        }
    }*/
    if (reg_1 == 0) {
        gel("info1").className = 'fall';
        return false;
    }
    else if (reg_2 == 0) {
        gel("info1").className = 'fall';
        return false;
    }
    else if (reg_3 == 0) {
        gel("info3").className = 'fall';
        return false;
    } else if (reg_4 == 0) {
        gel("info4").className = 'fall';
        return false;
    }
    /*else if (reg_5 == 0) {
        gel("info5").className = 'fall';
        return false;
    } */
    else if (reg_6 == 0) {
        gel("info6").className = 'fall';
        return false;
    }
    /*else if (reg_7 == 0) {
        gel("info7").className = 'fall';
        return false;
    }*/
    else if (reg_8 == 0) {
        gel("info8").className = 'fall';
        return false;
    } else {
        var b = document.getElementById("subInfo");
        b.disabled = 1;
        return true;
    }
}

//AJAX check
function checkUsername(x) {
    reg_2 = 0;
    var form = gel("form");
    var username = form.userid.value;
    //var usernames = gel("info1");
    if (reg_1 == 0 || username == "" || username == null) {
        gel(x).innerHTML = "请先填写用户名！";
        gel(x).className = "fall";
        return;
    }
    message = "正在检测中，请稍候...";
    gel(x).innerHTML = message;
    gel(x).className = "focus";
    window.setTimeout('doCheck("' + username + '")', 500);
}

function doCheck(username) {
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
        req.onreadystatechange = processChech;
        req.open("POST", "/check.jsp?username=" + username, true);
        req.send(null);
    } else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
        if (req) {
            req.onreadystatechange = processChech;
            req.open("POST", "/check.jsp?username=" + username, true);
            req.send();
        }
    }
}

function processChech() {
    reg_2 = 0;
    if (req.readyState == 4) {
        var content = gel("info2");
        if (req.status == 200) {
            content.innerHTML = req.responseText;
            if (req.responseText.indexOf("还没有被注册") != -1) {
                content.className = "true";
                reg_2 = 1;
            } else {
                reg_2 = 0;
                content.className = "fall";
                gel("UserName").focus();
            }
        } else {
            content.innnerHTML = "用户名检查服务暂时不能使用，不过您可以继续申请！";
            content.className = "true";
        }
    }
}

function fGetCode() {
    gel('imgCount').src = "../image.jsp";
}


function checkOldValue() {

    if (b_username) {
        gel("UserName").value = b_username;
        gel("info1").className = 'true';
        gel("info1").innerHTML = "用户名格式正确，请继续！";
    }

}

window.onload = function() {

    if (msg_authnum.length > 1) {
        gel("info5").innerHTML = msg_authnum;
        gel("info5").className = "fall";
        reg_7 = 0;
    }

    if (msg_username.length > 1) {
        gel("info1").className = "blur";
        gel("info1").innerHTML = msg['info1'];
        gel("info2").innerHTML = msg_username;
        gel("info2").className = "fall";
    }
    try {
        checkOldValue();
    } catch(e) {
    }
}
