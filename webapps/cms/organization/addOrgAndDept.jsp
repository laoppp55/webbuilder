<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-11-12
  Time: 下午6:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int errcode = 0;
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    int customer = authToken.getSiteID();
    int pid = ParamUtil.getIntParameter(request,"pid",0);
    String verifycode = ParamUtil.getParameter(request,"verifycode");
%>
<!DOCTYPE html>
<html>
<head>
    <title>增加组织-部门</title>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" type="text/css" href="../design/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../design/css/modules/layer/default/layer.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script type="text/javascript" src="../design/lay/modules/layer.js"></script>
    <script type="text/javascript">
        function doSave(){
            var pid = deptform.pid.value;
            var deptname = deptform.deptname.value;
            var shortname = deptform.shortname.value;
            var ename = deptform.ename.value;
            var deptcode = deptform.deptcode.value;
            var phone = deptform.phone.value;
            var mphone = deptform.mphone.value;
            var email = deptform.email.value;
            var contactor = deptform.contactor.value;
            var ind_rules = deptform.indexprefix.value;
            var ind_startnum = deptform.indexstartnum.value;

            if (deptname==null || deptname=="") {
                layer.alert('部门名称不能为空，请填写部门名称', {icon: 6});
                deptform.deptname.focus();
                return;
            } else {
                htmlobj = $.ajax({
                    url: "saveOrgAndDept.jsp",
                    type: 'post',
                    data: {
                        deptname: deptname,
                        shortname: shortname,
                        ename:ename,
                        deptcode: deptcode,
                        phone: phone,
                        mphone:mphone,
                        email: email,
                        contactor:contactor,
                        indrules:ind_rules,
                        indstartnum:ind_startnum,
                        pid: pid
                    },
                    dataType: 'json',
                    async: false,
                    success: function (data) {
                        window.opener.location.href="indexnew.jsp";
                        window.top.close();
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    }
                });
            }
        }

        function closewin(){
            window.top.close();
        }
    </script>
</head>
<body>
<div title="创建组织架构" style="width:900px;padding-top: 50px;">
    <form name="deptform" class="layui-form" action="">
        <div class="layui-form-item">
            <input type="hidden" name="pid" value="<%=pid%>">
            <label class="layui-form-label" style="width: 200px">部门名称：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="deptname" required  lay-verify="required" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门简称：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="shortname"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门英文名称：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="ename"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门编码：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="deptcode"  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门联系人：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="contactor" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">联系人手机：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="mphone" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门电话：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="phone" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">联系人邮件地址：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="email" required  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">文件索引号前缀：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="indexprefix" required  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">文件索引号起始值：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="indexstartnum" required  class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block" align="center">
                <button type="button" class="layui-btn" onclick="javascript:doSave();">立即提交</button>
                <button type="button" class="layui-btn layui-btn-primary" onclick="javascript:closewin();">关闭窗口</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
