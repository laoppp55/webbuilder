<%@ page import="com.bizwink.util.SessionUtil" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.usercenter.security.Auth" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="com.bizwink.dubboservice.service.Users.UsersService" %>
<%@ page import="com.bizwink.dubboservice.service.ArticleService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-6-19
  Time: 下午10:05
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    if (cookie_value != null) {
        int posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
    } else {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
        if (authToken != null) {
            SessionUtil.removeUserAuthorization(response, session);
        }
    }

    Users user = null;
    int siteid = 0;
    int samsiteid = ParamUtil.getIntParameter(request,"samsiteid",0);
    int columnid = ParamUtil.getIntParameter(request,"colid",0);
    if (username != null) {
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            ArticleService articleService = (ArticleService)appContext.getBean("ArticleService");
            UsersService usersService = (UsersService)appContext.getBean("UsersService");
            user = usersService.getByUserId(username);
            siteid = user.getSITEID().intValue();
        }

    } else {
        response.sendRedirect(response.encodeRedirectURL("error.jsp"));
    }
%>
<html>
<head>
    <title>基本注册资料修改</title>
    <script type="text/javascript" language="javascript">
        function uploadHeadPortrait() {
            var filename = $("#fileToUpload").val();
            if (filename == "") {
                alert("请选择上传的图片文件！");
                return false;
            }

            if (filename.toLowerCase().indexOf(".jpeg") == -1 && filename.toLowerCase().indexOf(".jpg")==-1 && filename.toLowerCase().indexOf(".png")==-1) {
                alert("上传文件必须是jpg格式或者是png格式的图片文件！");
                return false;
            }

            var bar = $('.bar');
            var percent = $('.percent');
            var status = $('#status');
            $("#form1").ajaxSubmit({
                url: 'saveUploadImg.jsp?doCreate=true',          //设置post提交到的页面
                type: "post",                                        //设置表单以post方法提交
                dataType: "json",                                   //设置返回值类型为文本
                data:{
                    siteid:<%=siteid%>,                              //站点ID
                    imgtype:'himg'                                  //图片类型，himg表示是头像
                },
                success: function (data) {
                    //alert(data.error.errcode + "==" + data.url);
                    //alert(data.error.modelname);
                    alert(data.error.errmsg);
                    $("#myimgid").attr('src',data.url);
                    $("#fileToUpload")[0].value = "";
                },
                xhr: function(){
                    var xhr = $.ajaxSettings.xhr();
                    if(onprogress && xhr.upload) {
                        xhr.upload.addEventListener("progress" , onprogress, false);
                        return xhr;
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    /*弹出jqXHR对象的信息*/
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    alert(jqXHR.readyState);
                    alert(jqXHR.statusText);
                    /*弹出其他两个参数的信息*/
                    alert(textStatus);
                    alert(errorThrown);
                }
            });
        }

        function onprogress(evt){
            //侦查附件上传情况
            //通过事件对象侦查
            //该匿名函数表达式大概0.05-0.1秒执行一次
            // console.log(evt.loaded);  //已经上传大小情况
            //evt.total; 附件总大小
            var loaded = evt.loaded;
            var tot = evt.total;
            var per = Math.floor(100*loaded/tot);  //已经上传的百分比
            var son =  document.getElementById('progressBar');
            son.innerHTML = per+"%";
            son.style.width=per+"%";
        }

        function updateinfo(type) {
            var val = "";
            var myname = "";
            var samsiteid = <%=samsiteid%>;
            if (samsiteid==2991)
                myname="农家院";
            else if (samsiteid==2992)
                myname="采摘园";
            else if (samsiteid==4592)
                myname="美丽乡村";
            else if (samsiteid==2993)
                myname="景区";

            if (type=="mphone")
                val = $("input[ name='mphone']").val();
            else if (type=="email")
                val = $("input[ name='email']").val();
            else if (type=="njyname")
                val = $("input[ name='njyname']").val();
            else if (type=="contactor")
                val = $("input[ name='contactor']").val();
            else if (type=="address")
                val = $("input[ name='address']").val();
            else if (type=="pass")
                val = $("input[ name='pass']").val();

            $.ajax({
                url : 'updateBaseinfo.jsp',
                type : 'post',
                async: false,                        //使用同步的方式,true为异步方式
                data:{
                    type:type,
                    info:encodeURI(val)
                },
                success : function(data){
                    if (data.indexOf("true") > -1) {
                        if (type=="mphone")
                            alert("用户手机号码修改成功");
                        else if (type=="email")
                            alert("用户电子邮件地址修改成功");
                        else if (type=="njyname")
                            alert(myname + "名称修改成功");
                        else if (type=="contactor")
                            alert("联系人信息修改成功");
                        else if (type=="address")
                            alert(myname + "详细地址信息修改成功");
                        else if (type=="pass")
                            alert("用户口令修改成功");
                    } else {
                        alert("修改" + val + "信息失败，可能" + val + "已经被注册");
                    }
                },
                fail:function(){
                }
            });
        }
    </script>
</head>
<body>
<div class="grzx_1">
    <div class="grzx_1_bt">房东资料</div>
    <div class="grzx_1_sub">&nbsp;</div>
    <div class="clear"></div>
</div>
<div class="grzx_2">
    <form action="saveheadimg.jsp" method="post" enctype="multipart/form-data" id="form1">
        <div class="grzx_1_0">
            <%if (samsiteid==2991) {%>
                院主头像：
            <%} else if (samsiteid==4592) {%>
                村长头像：
            <%} else if (samsiteid==2992) {%>
                园长头像：
            <%} else if (samsiteid==2993) {%>
                景区头像：
            <%}%>
        </div>
        <div class="grzx_1_1" style="padding-top:30px;">头像：</div>
        <div class="grzx_1_2" id="myheadid">
            <img id="myimgid" src=<%=(user.getMYIMAGE()==null)?"http://www.gugulx.com/images/20150527_8.png":user.getMYIMAGE()%> />
        </div>
        <div id="headimg">
            <div>
                <input type="file" name="myheadimg" id="fileToUpload" value="请选择JPG或者PNG图片"/>
            </div>
            <div class="progress" id="progressHide">
                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60"
                     aria-valuemin="0" aria-valuemax="100" style="width: 0%;" id="progressBar">
                    <span class="sr-only"></span>
                </div>
            </div>
        </div>
        <div class="grzx_1_3" style="padding-top:30px;">
            <a href="#" onclick= "javascript:uploadHeadPortrait();">修改头像</a>
        </div>
        <div class="grzx_1_4" style="padding-top:30px;">(上传真实头像，增加信任感)</div>
        <div class="clear"></div>
    </form>
</div>
<div class="grzx_2">
    <div class="grzx_1_0">联系方式：</div>
    <div class="grzx_1_1"><span>*</span>联系人手机号：</div>
    <div class="grzx_1_2"><input name="mphone" type="text" class="grzx_btn3" value="<%=(user.getMPHONE()==null)?"":user.getMPHONE()%>" /></div>
    <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('mphone');">修改</a></div>
    <div class="grzx_1_4">&nbsp;</div>
    <div class="clear"></div>
    <div class="grzx_1_1"><span>*</span>联系人邮箱：</div>
    <div class="grzx_1_2"><input name="email" type="text" class="grzx_btn3" value="<%=(user.getEMAIL()==null)?"":user.getEMAIL()%>" /></div>
    <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('email');">修改</a></div>
    <div class="grzx_1_4">&nbsp;</div>
    <div class="clear"></div>
</div>
<div class="grzx_2">
    <div class="grzx_1_0">
        <%if (samsiteid==2991) {%>
            农家院信息：
        <%} else if (samsiteid==4592) {%>
            美丽乡村信息：
        <%} else if (samsiteid==2992) {%>
            采摘园信息：
        <%} else if (samsiteid==2993) {%>
            景区信息：
        <%}%>
    </div>
    <%if (samsiteid==2991) {%>
    <div class="grzx_1_1">农家院名称：</div>
    <%} else if (samsiteid==4592) {%>
    <div class="grzx_1_1">美丽乡村名称：</div>
    <%} else if (samsiteid==2992) {%>
    <div class="grzx_1_1">采摘园名称：</div>
    <%} else if (samsiteid==2993) {%>
    <div class="top_sub_1">景区名称：</div>
    <%}%>
    <div class="grzx_1_2"><input name="njyname" type="text" class="grzx_btn3" value="<%=user.getCOMPANY()%>" /></div>
    <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('njyname');">修改</a></div>
    <%if (samsiteid==2991) {%>
    <div class="grzx_1_4">（农家院名称）</div>
    <%} else if (samsiteid==4592) {%>
    <div class="grzx_1_4">（美丽乡村名称）</div>
    <%} else if (samsiteid==2992) {%>
    <div class="grzx_1_4">（采摘园名称）</div>
    <%} else if (samsiteid==2993) {%>
    <div class="grzx_1_4">（景区名称）</div>
    <%}%>
    <div class="clear"></div>
    <!--div class="grzx_1_1">会员名：</div>
    <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="<%=user.getUSERID()%>" /></div>
    <div class="grzx_1_3"><a href="#">修改</a></div>
    <div class="grzx_1_4">（会员名可做登录名）</div-->
    <div class="grzx_1_1"><span>*</span>联系人姓名：</div>
    <div class="grzx_1_2"><input name="contactor" type="text" class="grzx_btn3" value="<%=user.getNICKNAME()%>" /></div>
    <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('contactor');">修改</a></div>
    <div class="grzx_1_4">（联系人姓名）</div>
    <div class="clear"></div>
    <!--div class="grzx_1_1"><span>*</span>证件号码：</div>
    <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="135406198211301628" /></div>
    <div class="grzx_1_3"><a href="#">修改</a></div>
    <div class="grzx_1_4">（证件号码，下单更加快捷方便）</div>
    <div class="grzx_1_1"><span>*</span>性别：</div>
    <div class="grzx_1_2"><input name="" type="text" class="grzx_btn3" value="女" /></div>
    <div class="grzx_1_3"><a href="#">修改</a></div>
    <div class="grzx_1_4">（修改自己的性别）</div>
    <div class="clear"></div-->
    <%if (samsiteid==2991) {%>
    <div class="grzx_1_1">农家院地址：</div>
    <%} else if (samsiteid==4592) {%>
    <div class="grzx_1_1">美丽乡村地址：</div>
    <%} else if (samsiteid==2992) {%>
    <div class="grzx_1_1">采摘园地址：</div>
    <%} else if (samsiteid==2993) {%>
    <div class="top_sub_1">景区地址：</div>
    <%}%>
    <div class="grzx_1_2"><input name="address" type="text" class="grzx_btn3" value="<%=user.getADDRESS()%>" /></div>
    <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('address');">修改</a></div>
    <div class="grzx_1_4">（自己的地址，有机会获得更多惊喜）</div>
    <div class="clear"></div>
</div>
<div class="grzx_2">
    <div class="grzx_1_0">修改密码：</div>
    <div class="grzx_1_1"><span>*</span>密码：</div>
    <div class="grzx_1_2"><input name="pwd" type="password" class="grzx_btn3" value="" /></div>
    <div class="grzx_1_3"><a href="#" onclick="javascript:updateinfo('pass');">修改</a></div>
    <div class="grzx_1_4">&nbsp;</div>
    <div class="clear"></div>
</div>
<div class="grzx_2"><input class="grzx_btn" name="" type="button" /><input class="grzx_btn111" name="" type="button" /></div>
</body>
</html>
