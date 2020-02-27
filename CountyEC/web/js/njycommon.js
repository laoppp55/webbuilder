$(function(){
    $.post("/_members/getSessionInfo.jsp",{},
        function(data) {
            if (data.indexOf("<uname>") > -1) {
                var sposi = data.indexOf("<uname>");
                var eposi = data.indexOf("</uname>");
                var ttbuf = data.substring(sposi+"<uname>".length,eposi);
                var posi = ttbuf.indexOf("-");
                var username = ttbuf.substring(0,posi);
                var usertype = ttbuf.substring(posi+1);
                $("#welcomeid").html(username + "，您好，欢迎来到周边旅游行网！");
                if (usertype == 1){         //农家乐
                    $("#funid").html("<a href=\"javascript:windowOpen('http://" + username + ".gugulx.com/_membercerter/fdzx.jsp','_blank')\">用户中心</a> | <a href=\"javascript:logoff();\">退出</a>");
                } else if (usertype == 2) {   //采摘园
                    $("#funid").html("<a href=\"javascript:windowOpen('http://" + username + ".gugulx.com/_membercerter/czzx.jsp','_blank')\">用户中心</a> | <a href=\"javascript:logoff();\">退出</a>");
                } else if (usertype == 3) {     //景区
                    $("#funid").html("<a href=\"javascript:windowOpen('http://www.gugulx.com/_membercerter/jqzx.jsp','_blank')\">用户中心</a> | <a href=\"javascript:logoff();\">退出</a>");
                } else if (usertype == 4) {     //美丽乡村
                    $("#funid").html("<a href=\"javascript:windowOpen('http://" + username + ".gugulx.com/_membercerter/mlxczx.jsp','_blank')\">用户中心</a> | <a href=\"javascript:logoff();\">退出</a>");
                } else {                        //个人游客
                    $("#funid").html("<a href=\"javascript:windowOpen('http://www.gugulx.com/_membercerter/grzx.jsp','_blank')\">用户中心</a> | <a href=\"javascript:logoff();\">退出</a>");
                }
            }
        }
    )
});

function logoff() {
    $.post("/_members/logoff.jsp",{},
        function(data) {
            if (data.indexOf("<logoutflag>") > -1) {
                $("#welcomeid").html("您好，欢迎来到周边旅游行网！");
                $("#funid").html("<a href=\"/_members/zhuce.jsp\">注册</a> | <a href=\"/_members/login.jsp\">登录</a>");
            }
        }
    )
}

function windowOpen(url,target){
    var a = document.createElement("a");
    a.setAttribute("href", url);
    if(target == null){
        target = '';
    }

    a.setAttribute("target", target);
    document.body.appendChild(a);

    if(a.click){
        a.click();
    }else{
        try{
            var evt = document.createEvent('Event');
            a.initEvent('click', true, true);
            a.dispatchEvent(evt);
        }catch(e){
            window.open(url);
        }
    }

    document.body.removeChild(a);
}
