<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.po.CmsTemplate" %>
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
<%
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    String getSessionCode = (String) session.getAttribute("randnum");
    int errcode = 0;

    if (doCreate) {
        String name = ParamUtil.getParameter(request, "username");
        String pass = ParamUtil.getParameter(request, "pwd");
        int tempno = ParamUtil.getIntParameter(request, "tempno",1);
        String email= ParamUtil.getParameter(request, "email");
        String contactor = ParamUtil.getParameter(request, "contactor");
        String mphone = ParamUtil.getParameter(request, "mphone");
        String address = ParamUtil.getParameter(request, "address");
        int copyright = ParamUtil.getIntParameter(request, "xieyi",0);
        String yzcode = ParamUtil.getParameter(request, "yzcode");
        String provid = ParamUtil.getParameter(request, "tprovince");
        String cityid = ParamUtil.getParameter(request, "tcity");
        String zoneid = ParamUtil.getParameter(request, "tzone");
        String townid = ParamUtil.getParameter(request, "ttown");
        String villageid = ParamUtil.getParameter(request, "tvillage");

        if (yzcode.equalsIgnoreCase(getSessionCode)) {
            Users user = new Users();
            user.setUSERID(name);
            user.setUSERPWD(pass);
            user.setEMAIL(email);
            user.setADDRESS(address);
            user.setMPHONE(mphone);
            user.setPROVINCE(provid);
            user.setCITY(cityid);
            user.setAREA(zoneid);
            user.setJIEDAO(townid);
            user.setSHEQU(villageid);
            user.setUSERTYPE(BigDecimal.valueOf(0));      //0企业用户

            try {
                List<Users> ulist = new ArrayList<Users>();
                ulist.add(user);

                //String url="http://localhost:8080/webbuilder/services/NjlUserWebService";
                String url="http://192.168.1.53/webbuilder/services/NjlUserWebService";
                Service serv = new Service();
                Call call = (Call)serv.createCall();

                QName qn = new QName("urn:NjlUserWebService","Users");
                call.registerTypeMapping(Users.class,qn,new BeanSerializerFactory(Users.class,qn),new BeanDeserializerFactory(Users.class,qn));
                call.setTargetEndpointAddress(new URL(url));
                call.setOperationName(new QName("NjlUserWebService", "registerNjlUser"));
                call.setReturnClass(ArrayList.class);
                call.addParameter("ulist", XMLType.XSD_ANYTYPE, ParameterMode.IN);
                call.addParameter("tempno", XMLType.XSD_ANYTYPE, ParameterMode.IN);
                call.addParameter("contactor",XMLType.XSD_ANYTYPE, ParameterMode.IN);
                call.addParameter("copyright",XMLType.XSD_ANYTYPE, ParameterMode.IN);
                List<String> list = (ArrayList)call.invoke(new Object[] {ulist,tempno,contactor,copyright});
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
            }
        } else {
            errcode = -1;
        }
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

        function selectTemplate() {
            window.open("case_zs.jsp", "selectmodel");
            //window.showModalDialog("case_zs.jsp", "selectmodel","dialogWidth:1200px;dialogHeight:800px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes");
        }

        function change_yzcodeimage() {
            $("#yzImageID").attr("src","/_members/image.jsp?temp=" + Math.random());
        }

        function setMessage(ftype) {
            var name = regform.username.value;
            var email = regform.email.value;

            if (ftype=="usermsg") {
                //检查用户是否存在
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
                //检查电子邮件地址是否存在
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
            var copyright = form.xieyi.value;
            var yzcode = form.yzcode.value;

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

            if (tempno <= 0) {
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

            if (contactor == "") {
                alert("请填写联系人姓名");
                return false;
            }

            if (mphone == "") {
                alert("请填写联系电话");
                return false;
            }

            if (mphone != "") {
                var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
                flag = filter.test(myphone);
                if (!flag) {
                    alert("电话输入有误，请重新输入！");
                    return false;
                }
            }

            if (yzcode == "") {
                alert("验证码不正确");
                return false;
            }

            if (yzcode.length != 4) {
                alert("验证码不正确");
                return false;
            }

            if (!copyright.checked) {
                alert("我已看过并同意条款才能通过");
                return false;
            }

            return false;
        }
    </script>
</head>

<body>
<div class="box">
    <div class="top">
        <div class="logo"><img src="images/logo.png" /></div>
        <div class="menu"><a href="/index.shtml">首页</a><a href="/list_njy.jsp">农家乐</a><a href="/list_czy.jsp">观光采摘园</a><a href="/list_jq.jsp">景区</a></div>
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
                    <div class="zc_bd_1">农家院名称<span>（*）</span>：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="" type="text" /></div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>

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
                        <select class="xzfl_2016">
                            <option value="农家院">农家院</option>
                            <option value="农家院">观光采摘园</option>
                            <option value="农家院">景区</option>
                        </select>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="zc_msc">&nbsp;</div>

                <div class="zc_bd">
                    <div class="zc_bd_1">网站模板<span>（*）</span>：</div>
                    <div class="zc_bd_4"><input class="zc_btn" name="tempname" type="text" value=""/><input name="tempno" type="hidden" value="0"></div>
                    <div class="zc_bd_5"><input name="" type="button"  value="选择" onclick="javascript:selectTemplate()"/></div>
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
                    <div class="zc_bd_2"><input class="zc_btn" name="mphone" type="text" /></div>
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
                    <div class="zc_bd_1">联系地址：</div>
                    <div class="zc_bd_2"><input class="zc_btn" name="address" type="text" /></div>
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
                <div class="zc_bd_3"><input name="xieyi" type="checkbox" value="" /> 已阅读并接受<span>《周边旅游行网使用协议》</span></div>
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
