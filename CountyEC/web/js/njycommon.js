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
                $("#welcomeid").html(username + "�����ã���ӭ�����ܱ�����������");
                if (usertype == 1){         //ũ����
                    $("#funid").html("<a href=\"javascript:windowOpen('http://" + username + ".gugulx.com/_membercerter/fdzx.jsp','_blank')\">�û�����</a> | <a href=\"javascript:logoff();\">�˳�</a>");
                } else if (usertype == 2) {   //��ժ԰
                    $("#funid").html("<a href=\"javascript:windowOpen('http://" + username + ".gugulx.com/_membercerter/czzx.jsp','_blank')\">�û�����</a> | <a href=\"javascript:logoff();\">�˳�</a>");
                } else if (usertype == 3) {     //����
                    $("#funid").html("<a href=\"javascript:windowOpen('http://www.gugulx.com/_membercerter/jqzx.jsp','_blank')\">�û�����</a> | <a href=\"javascript:logoff();\">�˳�</a>");
                } else if (usertype == 4) {     //�������
                    $("#funid").html("<a href=\"javascript:windowOpen('http://" + username + ".gugulx.com/_membercerter/mlxczx.jsp','_blank')\">�û�����</a> | <a href=\"javascript:logoff();\">�˳�</a>");
                } else {                        //�����ο�
                    $("#funid").html("<a href=\"javascript:windowOpen('http://www.gugulx.com/_membercerter/grzx.jsp','_blank')\">�û�����</a> | <a href=\"javascript:logoff();\">�˳�</a>");
                }
            }
        }
    )
});

function logoff() {
    $.post("/_members/logoff.jsp",{},
        function(data) {
            if (data.indexOf("<logoutflag>") > -1) {
                $("#welcomeid").html("���ã���ӭ�����ܱ�����������");
                $("#funid").html("<a href=\"/_members/zhuce.jsp\">ע��</a> | <a href=\"/_members/login.jsp\">��¼</a>");
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
