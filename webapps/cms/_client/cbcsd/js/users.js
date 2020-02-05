function doLogin() {
    var username = loginForm.userid.value;
    var passwd = loginForm.passwd.value;
    //检查用户是否存在
    jquery102.post("/users/doLogin.jsp",{
            userid:encodeURI(username),
            pwd:encodeURI(passwd),
            doLogin:true
        },
        function(data) {
            if (data.username!=null) {
                jquery102("#userInfos").html("欢迎你：" + data.username + "&nbsp;&nbsp;<a href='#' onclick='javascript:logoff();'>退出</a>");
                jquery102("#userInfos").css({color:"red"});
            } else {
                window.location.href="/users/login.jsp";
            }
        },
        "json"
    )
}

function doRegister(iHeight,iWidth) {
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;

    window.open("/users/userreg.jsp",'regwin', 'height=' + iHeight + ', width=' + iWidth + ', top='+iTop + ', left=' + iLeft + ', toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');
}

function logoff() {
    jquery102.post("/users/logoff.jsp",{},
        function(data) {
            if (data.indexOf("nologin") > -1) {
                var htmlcode = '<form name="loginForm">' +
                    '<input class="txt" name="userid" type="text" /> '+
                    '<input class="txt" type="password" name="passwd" /> '+
                    '<input class="btn" type="button" name="" onclick="javascript:doLogin();" value="登录" /> '+
                    '<input class="btn" type="button" onclick="javascript:doRegister(800,1000);" value="注册" />'+
                    '</form>';
                jquery102("#userInfos").html(htmlcode);
            }
        }
    )
}
