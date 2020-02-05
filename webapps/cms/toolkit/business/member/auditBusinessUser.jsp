<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"%>
<%@ include file="../../../include/auth.jsp"%>
<%
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);

    if (!SecurityCheck.hasPermission(authToken, 54)){
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int uid = ParamUtil.getIntParameter(request,"uid",0);    //被授权人的userid，不是登录者的userid
    int startrow = ParamUtil.getIntParameter(request,"startrow",0);
    IUserManager       userMgr  = UserPeer.getInstance();
    //得到原始数据
    User user = userMgr.getUserByUID(uid,siteid);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>审核业务员用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/table.css"/>
    <link rel="stylesheet" type="text/css" href="../../../css/uploadify.css">
    <script type="text/javascript" src="../js/check.js"></script>
    <script type="text/javascript" src="../../../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../../../js/jquery.uploadify.js"></script>
    <script type="text/javascript" src="../../../js/jquery.form.js"></script>
    <SCRIPT LANGUAGE=javascript>
        function doSubmit() {
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

            $("#auditFormID").ajaxSubmit({
                url: 'saveUploadImg.jsp?doCreate=true',          //设置post提交到的页面
                type: "post",                                        //设置表单以post方法提交
                dataType: "json",                                   //设置返回值类型为文本
                cache:false,
                async:false,
                success: function (data) {
                    alert(data.error.errcode + "==" + data.url);
                    alert(data.error.modelname);
                    alert(data.error.errmsg);
                    //$("#myimgid").attr('src',data.url);
                    //$("#fileToUpload")[0].value = "";
                },
                xhr:function(){
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

            return false;
        }

        //侦查附件上传情况
        //通过事件对象侦查
        //该匿名函数表达式大概0.05-0.1秒执行一次
        // console.log(evt.loaded);  //已经上传大小情况
        //evt.total; 附件总大小
        function onprogress(evt){
            var loaded = evt.loaded;
            var tot = evt.total;
            var per = Math.floor(100*loaded/tot);  //已经上传的百分比
            var son =  document.getElementById('progressBar');
            son.innerHTML = per+"%";
            son.style.width=per+"%";
        }

        $(document).ready(function(){
            $('#fileToUpload').uploadify( {
                'swf' : '/webbuilder/css/images/uploadify.swf',                        //上传按钮的图片，默认是这个flash文件
                'uploader' : 'saveUploadImg.jsp?uid=<%=uid%>&doCreate=true',                                      //上传所处理的服务器
                'cancelImg' : '/webbuilder/css/images/uploadify-cancel.png',         //取消图片
                'method':'post',
                'folder' : '/UploadFile',                                         //上传后，所保存文件的路径
                'queueID' : 'fileQueue',                                          //上传显示进度条的那个div
                'fileTypeDesc'   : 'jpg;png',
                'fileTypeExts'   : '*.jpg;*.png', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
                'height'  : 20,
                'width'   : 100,
                'buttonText' : '请选择文件',
                //'onUploadComplete': function(file){
                //    alert('The file'+file.name+'finished processing!')
                //},                                                                   //每个文件上传成功后的函数
                progressData : 'percentage',
                'auto' : false,
                'multi' : true,
                'onSelect':function(file){
                    alert("文件"+file.name+"被选择了！");
                },
                onUploadSuccess : function(file,data,response) {                    //上传完成时触发（每个文件触发一次）
                    alert( 'id: ' + file.id
                        + ' - 索引: ' + file.index
                        + ' - 文件名: ' + file.name
                        + ' - 文件大小: ' + file.size
                        + ' - 类型: ' + file.type
                        + ' - 创建日期: ' + file.creationdate
                        + ' - 修改日期: ' + file.modificationdate
                        + ' - 文件状态: ' + file.filestatus
                        + ' - 服务器端消息: ' + data
                        + ' - 是否上传成功: ' + response);
                },
                //'onQueueComplete' : function(queueData) {
                //	alert(queueData.filesQueued + 'files were successfully!')
                //},                                                                      //当队列中的所有文件上传成功后，弹出共有多少个文件上传成功
                //'onDisable' : function() {
                //    alert('uploadify is disable');
                //},//在调用disable方法时候触发
                //'onCancel':function(){alert('你取消了文件上传')}
                //'onUploadStart' : function(file) {//在调用上传前触发
                //alert('The file ' + file.name + ' is being uploaded.')}
                'onError' : function(errorType,errObj) {
                    alert('The error was: ' + errObj.info)
                }
            });
        });

        function audituserinfo() {
            htmlobj=$.ajax({
                url: 'updateUserType.jsp?doUpdate=true',          //设置post提交到的页面
                data:{
                    uid:<%=uid%>
                },
                type: "post",                                        //设置表单以post方法提交
                dataType: "json",                                   //设置返回值类型为文本
                cache:false,
                async:false,
                success: function (data) {
                    if (data.msg=="SUCESS" && data.errcode==0) {
                        alert("<%=(user.getNickName()!=null)?user.getNickName():""%>" + "审核通过！！！");
                        opener.window.location = "index3.jsp?startrow=<%=startrow%>";
                        window.close();
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    alert(jqXHR.readyState);
                    alert(jqXHR.statusText);
                    alert(textStatus);
                    alert(errorThrown);
                }
            });
        }

        function window_close() {
            parent.window.close();
        }
    </SCRIPT>
</head>
<body>
<div align="center">
    <form name="auditform" id="auditFormID" enctype="multipart/form-data">
        <div align="left">
            <table width="100%" class="table">
                <tr>
                    <td width="30%" align="right"><font size="2">电子邮件：</font></td>
                    <td><font size="2"><%=(user.getEmail()!=null)?user.getEmail():""%></font></td>
                </tr>
                <tr>
                    <td width="30%" align="right"><font size="2">用户姓名：</font></td>
                    <td><font size="2"><%=(user.getNickName()!=null)?StringUtil.gb2iso4View(user.getNickName()):""%></font></td>
                </tr>
                <tr>
                    <td width="30%" align="right"><font size="2">用户电话：</font></td>
                    <td><font size="2"><%=(user.getPhone()!=null)?user.getPhone():""%></font></td>
                </tr>
                <tr>
                    <td width="30%" align="right"><font size="2">用户手机：</font></td>
                    <td><font size="2"><%=(user.getMobilePhone()!=null)?user.getMobilePhone():""%></font></td>
                </tr>
                <tr>
                    <td width="30%" align="right"><font size="2">选择头像：</font></td>
                    <td>
                        <div class="progress" id="progressHide">
                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;" id="progressBar">
                                <span class="sr-only"></span>
                            </div>
                        </div>
                        <div id="fileQueue"></div>
                        <div>
                            <table>
                                <tr>
                                    <td>
                                        <input type="file" name="myheadimg" id="fileToUpload" multiple="true" value="请选择JPG或者PNG图片"/>
                                        <%if (user.getMyimage() != null) {%>
                                        <img src="/webbuilder/sites/<%=sitename%><%=user.getMyimage()%>">
                                        <%}%>
                                    </td>
                                    <td>
                                        <a href="javascript:$('#fileToUpload').uploadify('upload', '*')">开始上传</a>
                                    </td>
                                    <td>
                                        <a href="javascript:$('#fileToUpload').uploadify('cancel', '*')">取消上传</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" height="32">
                        <div align="center">
                            <a href="javascript:audituserinfo();">审核</a>
                            <a href="javascript:window_close();">返回</a>
                            <!--input type="button" name="adduser" value="修改" onclick="javascript:saveAndUploadTask();">&nbsp;&nbsp;
                            <input type="button" name="cancel" value="取消" onclick="javascript:window_close();"-->
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
</body>
</html>
