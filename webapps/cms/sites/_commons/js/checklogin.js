function checkLoginForm(){
    var userName = document.loginForm.username.value;
    var passWord = document.loginForm.password.value;
    if(userName==""){
        alert("请输入用户名！");
        return false;
    }
    if(passWord == ""){
        alert("请输入密码！");
        return false;
    }

    return true;
}

var req;
function getXMLHTTPObj()
{
    var C = null;
    try{
        C = new ActiveXObject("Msxml2.XMLHTTP");
    } catch(e) {
        try {
            C = new ActiveXObject("Microsoft.XMLHTTP");
        } catch(sc) {
            C = null;
        }
    }

    if( !C && typeof XMLHttpRequest != "undefined" )
    {
        C = new XMLHttpRequest();
    }

    return C;
}

function sendRequest(params) {
    alert(params);
    req = getXMLHTTPObj();
    if (req) {
        req.onreadystatechange=onReadyStateChange;
        req.Open("post","/_commons/Userlogin.jsp?"+params,true);
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(params);
    }
}

function onReadyStateChange(){
    var ready=req.readyState;
    var data=null;
    alert("hello word");
    if (ready ==4) {
        data = req.responseText;
        alert(data);
        if (data.indexOf("nologin") == -1) {
            document.getElementById("biz_user_login").style.display="block";
            document.getElementById("biz_user_login").innerHTML = data;
            document.getElementById("biz_user_login_form").style.display="none";
        }
    }
}

function checklogin() {
    req = getXMLHTTPObj();
    if (req) {
        req.onreadystatechange=onReadyStateChange;
        req.open("POST", "/_commons/checklogin.jsp");
        req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        req.send(null);
    }
}