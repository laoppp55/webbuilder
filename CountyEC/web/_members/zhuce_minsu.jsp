<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="org.apache.axis.client.Service" %>
<%@ page import="org.apache.axis.client.Call" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="org.apache.axis.encoding.ser.BeanSerializerFactory" %>
<%@ page import="org.apache.axis.encoding.ser.BeanDeserializerFactory" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.axis.encoding.XMLType" %>
<%@ page import="javax.xml.rpc.ParameterMode" %>
<%@ page import="java.rmi.RemoteException" %>
<%@ page import="javax.xml.rpc.ServiceException" %>
<%@ page import="java.net.MalformedURLException" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="com.bizwink.util.StringUtil" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.po.Siteinfo" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="net.sf.json.JSONObject" %>
<%
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    String getSessionCode = (String) session.getAttribute("randnum");
    int errcode = 0;

    if (doCreate) {
        String name = filter.excludeHTMLCode(ParamUtil.getParameter(request, "username"));
        String pass = filter.excludeHTMLCode(ParamUtil.getParameter(request, "pwd"));
        int samsiteid = ParamUtil.getIntParameter(request, "infotype", 1);
        int tempno = ParamUtil.getIntParameter(request, "tempno", 1);
        String email= filter.excludeHTMLCode(ParamUtil.getParameter(request, "email"));
        String contactor = filter.excludeHTMLCode(ParamUtil.getParameter(request, "contactor"));
        String njyname = filter.excludeHTMLCode(ParamUtil.getParameter(request, "njyname"));
        String mphone = filter.excludeHTMLCode(ParamUtil.getParameter(request, "mphone"));
        String address = filter.excludeHTMLCode(ParamUtil.getParameter(request, "address"));
        boolean agreeflag = ParamUtil.getCheckboxParameter(request,"agreement");
        String yzcode = filter.excludeHTMLCode(ParamUtil.getParameter(request, "yzcode"));
        String provid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "tprovince"));
        String cityid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "tcity"));
        String zoneid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "tzone"));
        String townid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "ttown"));
        String villageid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "tvillage"));

        System.out.println("agreeflag==" + agreeflag);
        System.out.println(yzcode + "==" + getSessionCode);
        ErrorMessage errorMessage = null;

        if (agreeflag && yzcode.equalsIgnoreCase(getSessionCode)) {
            Users user = new Users();
            user.setUSERID(name);
            user.setUSERPWD(pass);
            user.setEMAIL(email);
            user.setADDRESS(address);
            user.setCOMPANY(njyname);
            user.setMPHONE(mphone);
            user.setPROVINCE(provid);
            user.setCITY(cityid);
            user.setAREA(zoneid);
            user.setJIEDAO(townid);
            user.setSHEQU(villageid);
            user.setUSERTYPE(BigDecimal.valueOf(0));      //0企业用户
            List<Users> ulist = new ArrayList<Users>();
            ulist.add(user);

            ApplicationContext appContext = SpringInit.getApplicationContext();
            String jsondata = null;
            if (appContext!=null) {
                UsersService ds = (UsersService)appContext.getBean("UsersService");
                jsondata = ds.RegisterNjlUserInfo(ulist,tempno,contactor,samsiteid);
                JSONObject jsonObject=JSONObject.fromObject(jsondata);
                errorMessage = (ErrorMessage)JSONObject.toBean(jsonObject, ErrorMessage.class);
                System.out.println(errorMessage.getModelname() + "==" + errorMessage.getErrmsg() + "==" + errorMessage.getErrcode());
            } else {
                errcode = -2;
            }

            if (errorMessage.getErrcode() == 1)
                errcode = 1;
            else {
                System.out.println("生成站点为空");
                errcode = -3;
            }

            /*
            try {
                //String url="http://localhost:8080/webbuilder/services/NjlUserWebService";
                String url="http://192.168.1.53/webbuilder/services/NjlUserWebService";
                Service serv = new Service();
                Call call = (Call)serv.createCall();

                QName qn = new QName("urn:NjlUserWebService","Users");
                call.registerTypeMapping(Users.class, qn, new BeanSerializerFactory(Users.class, qn), new BeanDeserializerFactory(Users.class, qn));
                call.setTargetEndpointAddress(new URL(url));
                call.setOperationName(new QName("NjlUserWebService", "registerNjlUser"));
                call.setReturnClass(ArrayList.class);
                call.addParameter("ulist", XMLType.XSD_ANYTYPE, ParameterMode.IN);
                call.addParameter("tempno", XMLType.XSD_ANYTYPE, ParameterMode.IN);
                call.addParameter("contactor",XMLType.XSD_ANYTYPE, ParameterMode.IN);
                call.addParameter("samsiteid",XMLType.XSD_ANYTYPE, ParameterMode.IN);
                List<String> list = (ArrayList)call.invoke(new Object[] {ulist,tempno,contactor,samsiteid});
                errcode = list.size();
            } catch(RemoteException e) {
                e.printStackTrace();
                errcode = -2;
            } catch(ServiceException exp1) {
                exp1.printStackTrace();
                errcode = -2;
            } catch (MalformedURLException exp2) {
                exp2.printStackTrace();
                errcode = -2;
            }*/
        } else {
            errcode = -1;
        }

        System.out.println("test errcode===" + errcode);
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>周边旅游行网</title>
<link href="css/zhuce_minsu.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script language="javascript">
var falg = false;
var sucess = "";
var errcode = <%=errcode%>;

$(document).ready(function(){
    if (errcode == -1) {
        $("#yzmmsg").html("验证码输入错误，请重新输入验证码");
        $("#yzmmsg").css({color:"red"});
    } else if (errcode == -2) {
        $("#yzmmsg").html("运行环境初始化错误，请联系客服人员");
        $("#yzmmsg").css({color:"red"});
    } else if (errcode == -3) {
        $("#yzmmsg").html("站点生成失败");
        $("#yzmmsg").css({color:"red"});
    } else if (errcode > 0) {
        alert("用户注册成功，系统跳转登陆页面");
        window.location.href="/_members/login.jsp";
    }

    htmlobj=$.ajax({
        url:"getProvinces.jsp",
        dataType:'json',
        async:false,
        success:function(data){
            //alert(data.length);
            //alert(data[0].ID + ":" + data[0].PROVNAME + ":" + data[0].CODE);
            var selObj = $("#province");
            var jj = 0;
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].PROVNAME;
                selObj.append("<option value=\""+value+"\">"+text+"</option>");
            }
        }
    });
});

function selcitys(val) {
    htmlobj=$.ajax({
        url:"getCitys.jsp?provinceid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var selObj = $("#city");
            var jj = 0;

            //清空原来的搜有值
            var selOpt = $("#city option");
            selOpt.remove();

            //增加新的OPTION的值
            selObj.append("<option value=\"01\">请选择市</option>");
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].CITYNAME;
                selObj.append("<option value=\""+value+"\">"+text+"</option>");
            }
        }
    });
    //alert(htmlobj.responseText);

    //清空区县选择
    $("#zone option").remove();
    $("#zone").append("<option value=\"01\">请选择区县</option>");

    //清空乡镇选择
    $("#town option").remove();
    $("#town").append("<option value=\"01\">请选择乡镇</option>");

    //清空村选择
    $("#village option").remove();
    $("#village").append("<option value=\"01\">请选择村</option>");
}

function selzones(val) {
    htmlobj=$.ajax({
        url:"getZones.jsp?cityid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            //清空原来的搜有值
            var selOpt = $("#zone option");
            selOpt.remove();

            //增加新的OPTION的值
            var selObj = $("#zone");
            var jj = 0;
            selObj.append("<option value=\"01\">请选择区县</option>");
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].ZONENAME;
                selObj.append("<option value=\""+value+"\">"+text+"</option>");
            }
        }
    });
    //alert(htmlobj.responseText);

    //清空乡镇选择
    $("#town option").remove();
    $("#town").append("<option value=\"01\">请选择乡镇</option>");

    //清空村选择
    $("#village option").remove();
    $("#village").append("<option value=\"01\">请选择村</option>");
}

function seltowns(val) {
    htmlobj=$.ajax({
        url:"getTowns.jsp?zoneid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var selObj = $("#town");
            var jj = 0;

            //清空原来的搜有值
            var selOpt = $("#town option");
            selOpt.remove();

            //增加新的OPTION的值
            selObj.append("<option value=\"01\">请选择乡镇</option>");
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].TOWNNAME;
                selObj.append("<option value=\""+value+"\">"+text+"</option>");
            }
        }
    });
    //alert(htmlobj.responseText);

    //清空村选择
    $("#village option").remove();
    $("#village").append("<option value=\"01\">请选择村</option>");
}

function selvillages(val) {
    htmlobj=$.ajax({
        url:"getVillages.jsp?townid=" + val,
        dataType:'json',
        async:false,
        success:function(data){
            var selObj = $("#village");
            var jj = 0;

            //清空原来的搜有值
            var selOpt = $("#village option");
            selOpt.remove();

            //增加新的OPTION的值
            selObj.append("<option value=\"01\">请选择村</option>");
            for(jj=0; jj < data.length; jj++) {
                var value=data[jj].CODE;
                var text=data[jj].VILLAGENAME;
                selObj.append("<option value=\""+value+"\">"+text+"</option>");
            }
        }
    });
    //alert(htmlobj.responseText);
}

function ismail(mail) {
    falg = (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
    if (falg) {
        sucess = "sucess";
    }
}

function setname() {
    var infot = regform.infotype.value;
    if (infot == 2991) {
        $("#njylabel").html("农家院名称<span>（*）</span>：");
        $("#village").show();
        regform.tempname.value="";
        regform.tempno.value=0;
    } else if (infot == 4592) {
        $("#njylabel").html("乡村名称<span>（*）</span>：");
        $("#village").val("01");
        $("#village").hide();
        regform.tempname.value="";
        regform.tempno.value=0;
    } else if (infot == 2992) {
        $("#njylabel").html("采摘园名称<span>（*）</span>：");
        $("#village").show();
        regform.tempname.value="";
        regform.tempno.value=0;
    }
    //else if (infot == 2993)
    //    $("#njylabel").html("景区名称<span>（*）</span>：");

}

function selectTemplate() {
    window.open("case_zs.jsp?samsitetype="+regform.infotype.value, "selectmodel");
    //window.showModalDialog("case_zs.jsp", "selectmodel","dialogWidth:1200px;dialogHeight:800px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes");
}

function change_yzcodeimage() {
    $("#yzImageID").attr("src","/_members/image.jsp?temp=" + Math.random());
}

function setMessage(ftype) {
    var name = regform.username.value;
    var email = regform.email.value;
    var mphone = regform.mphone.value;

    if (ftype=="usermsg") {
        //检查用户是否被注册
        $.post("checkname.jsp",{
                    username:encodeURI(name)
                },
                function(data) {
                    if (data.indexOf("true") > -1) {
                        $("#usermsg").html("此用户名已经被注册过，请更换用户名");
                        $("#usermsg").css({color:"red"});
                    } else {
                        $("#usermsg").html("用户名可以使用");
                        $("#usermsg").css({color:"green"});
                    }
                }
        )
    } else if (ftype=="emailmsg") {
        //检查电子邮件地址是否被注册
        $.post("checkemail.jsp",
                {
                    email:encodeURI(email)
                },
                function(data) {
                    if (data.indexOf("true") > -1) {
                        $("#emailmsg").html("电子邮件地址已经被注册过");
                        $("#emailmsg").css({color:"red"});
                    } else {
                        $("#emailmsg").html("电子邮件地址可以使用");
                        $("#emailmsg").css({color:"green"});
                    }
                }
        )
    } else if (ftype=="mphonemsg") {
        //检查手机号码是否被注册
        $.post("checkcellphone.jsp",
                {
                    mphone:encodeURI(mphone)
                },
                function(data) {
                    if (data.indexOf("true") > -1) {
                        $("#mphonemsg").html("该手机号码已经被注册过，请更改手机号码");
                        $("#mphonemsg").css({color:"red"});
                    } else {
                        $("#mphonemsg").html("该手机号码可以使用");
                        $("#mphonemsg").css({color:"green"});
                    }
                }
        )
    }
}

function tijiao(form) {
    var name = form.username.value;
    var pass = form.pwd.value;
    var confpass = form.repwd.value;
    var tempno = form.tempno.value;
    var email = form.email.value;
    var contactor = form.contactor.value;
    var mphone = form.mphone.value;
    var address = form.address.value;
    var njyname = form.njyname.value;
    var yzcode = form.yzcode.value;
    var prov = form.tprovince.value;
    var city = form.tcity.value;
    var zone = form.tzone.value;
    var town = form.ttown.value;
    var village = form.tvillage.value;
    var infotype = regform.infotype.value;

    if (name == "") {
        alert("用户名不能为空");
        return false;
    }

    if (name.length <= 6) {
        alert("用户名长度必须6位以上");
        return false;
    }

    var reg = /[^A-Za-z0-9-]/g;
    if (reg.test(name)) {
        alert("用户名格式不正确");
        return false;
    }

    var username_used = false;
    $.ajax({
        url : 'checkname.jsp',
        type : 'post',
        async: false,                        //使用同步的方式,true为异步方式
        data:{
            username:encodeURI(name)
        },
        success : function(data){
            if (data.indexOf("true") > -1) {
                username_used = true;
            }
        },
        fail:function(){
        }
    });

    if (username_used == true) {
        alert("此用户名已经被注册过，请更换用户名");
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

    if (njyname == "") {
        alert("农家院/美丽乡村/采摘园/景区的中文名称不能为空！！！");
        return false;
    }

    if (address == "") {
        alert("农家院/美丽乡村/采摘园/景区的地址不能为空！！！");
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
    var email_used = false;
    $.ajax({
        url : 'checkemail.jsp',
        type : 'post',
        async: false,                        //使用同步的方式,true为异步方式
        data:{
            email:encodeURI(email)
        },
        success : function(data){
            if (data.indexOf("true") > -1) {
                email_used = true;
            }
        },
        fail:function(){
        }
    });

    if (email_used == true) {
        alert("电子邮件地址已经被注册过,请更换电子邮件地址")
        return false;
    }

    if (contactor == "") {
        alert("请填写联系人姓名");
        return false;
    }

    if (mphone == "") {
        alert("请填写联系人手机号码");
        return false;
    }

    if (mphone != "") {
        //var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
        var filter=/^[1][3,4,5,7,8][0-9]{9}$/;
        flag = filter.test(mphone);
        if (!flag) {
            alert("手机号码输入有误，请重新输入！");
            return false;
        }
    }

    //检查手机号码是否被注册
    var mphone_used = false;
    $.ajax({
        url : 'checkcellphone.jsp',
        type : 'post',
        async: false,                        //使用同步的方式,true为异步方式
        data:{
            mphone:encodeURI(mphone)
        },
        success : function(data){
            if (data.indexOf("true") > -1) {
                mphone_used = true;
            }
        },
        fail:function(){
        }
    });

    if (mphone_used == true) {
        alert("手机号码已经被注册过了,请更换手机号码");
        return false;
    }

    if (prov == "01") {
        alert("请选择省份信息！");
        return false;
    }

    if (city == "01") {
        alert("请选择所在的城市信息！");
        return false;
    }

    if (zone == "01") {
        alert("请选择所在的区县信息！");
        return false;
    }

    if (town == "01") {
        alert("请选择所在的乡镇信息！");
        return false;
    }

    if (village == "01" && infotype != 4592) {
        alert("请选择所在的村庄信息！");
        return false;
    }

    if (tempno <= 0) {
        alert("必须选择网站模板！！！");
        return false;
    }

    if (yzcode == "") {
        alert("验证码不能为空");
        return false;
    }

    if (yzcode.length != 4) {
        alert("验证码不正确");
        return false;
    }

    var agreement = "";
    $("input:checkbox[name='agreement']:checked").each(function() {
        agreement = $(this).val();
    });

    if (agreement=="") {
        alert("请阅读协议，并勾选我已看过并同意条款才能通过");
        return false;
    }

    return true;
}
</script>
</head>

<body>
<div class="box">
    <div class="top">
        <div class="logo"><img src="images/logo.png" /></div>
        <div class="menu"><a href="/">首页</a><a href="/njl.shtml?type=njy">农家乐</a><a href="/mlxc.shtml?type=mlxc">美丽乡村</a><a href="/czy.shtml?type=czy">采摘园</a><a href="/list.shtml?type=jq">景点景区</a></div>
        <div class="login"><a href="/_members/zhuce.jsp">注册</a> | <a href="/_members/login.jsp">登录</a></div>
        <div class="clear"></div>
    </div>
    <!-- top end  -->
    <div style="height:99px; width:954px;">&nbsp;</div>
    <div class="login_con1">
        <form name="regform" method="post" action="zhuce_minsu.jsp" onsubmit="return tijiao(regform)">
            <input type="hidden" name="doCreate" value="true">
            <div class="login_con_33">
                <div class="hyzc">民宿注册</div>
                <div class="gaze_1229"> 带<span>（*）</span>为必填项</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">用 户 名<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="username" type="text" onblur="javascript:setMessage('usermsg')" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="usermsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">密　　码<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="pwd" type="password" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="pwdmsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">再次输入密码<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="repwd" type="password" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="repwdmsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">分类选择<span>（*）</span>：</div>
                    <div class="zc_bd_4">
                        <select name="infotype" id="infotypeid" class="xzfl_2016" onchange="javascript:setname();">
                            <option value="2991">农家院</option>
                            <option value="4592">美丽乡村</option>
                            <option value="2992">采摘园</option>
                            <!--option value="2993">景区</option-->
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1" id="njylabel">农家院名称<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="njyname" type="text" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <!--  20160118 start   -->
                <div class="zc_bd">
                    <div class="zc_bd_1">请选择<span>（*）</span>：</div>
                    <div class="zc_bd_2">
                        <select id="province" name="tprovince" style="margin-right:6px;" onchange="javascript:selcitys(this.value)">
                            <option value="01">请选择省</option>
                        </select>
                        <select id="city" name="tcity" style="margin-right:6px;" onchange="javascript:selzones(this.value)">
                            <option value="01">请选择市</option>
                        </select>
                        <select id="zone" name="tzone" style="margin-right:6px;" onchange="javascript:seltowns(this.value)">
                            <option value="01">请选择县</option>
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="zc_bd" style="margin-top:5px;">
                    <div class="zc_bd_1">&nbsp;</div>
                    <div class="zc_bd_2">
                        <select id="town" name="ttown" style="margin-right:6px;" onchange="javascript:selvillages(this.value)">
                            <option value="01">请选择乡镇</option>
                        </select>
                        <select id="village" name="tvillage" style="margin-right:6px;">
                            <option value="01">请选择村</option>
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">详细地址<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="address" type="text" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">电子邮箱<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="email" type="text"  onblur="javascript:setMessage('emailmsg')"/></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="emailmsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">联系人<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="contactor" type="text" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">联系手机<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="mphone" type="text"  onblur="javascript:setMessage('mphonemsg')" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc" id="mphonemsg">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">网站模板<span>（*）</span>：</div>
                    <div class="zc_bd_4">
                        <input class="zc_btn" name="tempname" type="text" value=""/>
                        <input name="tempno" type="hidden" value="0">
                    </div>
                    <div class="zc_bd_5">
                        <input name="" type="button"  value="选择" onclick="javascript:selectTemplate()"/>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd">
                    <div class="zc_bd_1">验证码<span>（*）</span>：</div>
                    <div class="zc_bd_6"><input class="zc_btn" name="yzcode" type="text" /></div>
                    <div class="zc_bd_7"><img src="image.jsp" id="yzImageID" name="yzcodeimage" align="absmiddle"/> <a href="javascript:change_yzcodeimage();">看不清，换一张</a></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>
                <div class="zc_bd_3"><input name="agreement" type="checkbox" value="1" /> 已阅读并接受<span>《周边旅游行网使用协议》</span></div>
                <div class="zc_bd_3"><input type="image" src="images/20150527_81.jpg" name="tijiao"/></div>
            </div>
        </form>
        <div class="login_con_44">
            <div class="yyzh">已有帐号，从这里<input name="" type="button" class="again_login" value="登录" /></div>
            <div class="yyzh_1"><img src="images/20150527_86.jpg" /><img src="images/20150527_87.jpg" /><img src="images/20150527_88.jpg" /></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!-- fonnter  -->
<%@ include file="inc/tail.shtml" %>
</body>
</html>
