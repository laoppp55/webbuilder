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
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.po.Department" %>
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
    int siteid = authToken.getSiteID();
    int orgid = ParamUtil.getIntParameter(request,"orgid",0);
    String verifycode = ParamUtil.getParameter(request,"verifycode");

    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<Department> departments = new ArrayList<Department>();
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
    }

%>
<!DOCTYPE html>
<html>
<head>
    <title>修改组织架构信息-部门</title>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" type="text/css" href="../design/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../design/css/modules/layer/default/layer.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script type="text/javascript" src="../design/lay/modules/layer.js"></script>
    <script type="text/javascript">
        function doUpdate(){
            var id = deptform.id.value;
            var orgid = deptform.orgid.value;
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

            htmlobj=$.ajax({
                url:"updateOrgAndDept.jsp",
                type:'post',
                data:{
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
                    orgid: orgid,
                    id:  id
                },
                dataType:'json',
                async:false,
                success:function(data){
                    alert("信息修改成功");
                    window.opener.location.href="indexnew.jsp";
                    window.top.close();
                },
                error:function(XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);
                    alert(XMLHttpRequest.readyState);
                    alert(textStatus);
                }
            });
        }

        function closewin(){
            window.top.close();
        }
    </script>
</head>
<body>
<div title="创建组织架构" style="width:900px;">
    <form name="deptform" class="layui-form" action="">
        <input type="hidden" name="orgid" value="<%=orgid%>" />
        <input type="hidden" name="id" value="<%=departments.get(0).getID().intValue()%>" />
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门名称：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="deptname" required  value="<%=departments.get(0).getCNAME()%>" lay-verify="required" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门简称：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="shortname" value="<%=(departments.get(0).getSHORTNAME()!=null)?departments.get(0).getSHORTNAME():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门英文名称：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="ename" value="<%=(departments.get(0).getENAME()!=null)?departments.get(0).getENAME():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门编码：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="deptcode" value="<%=(departments.get(0).getDepartcode()!=null)?departments.get(0).getDepartcode():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门联系人：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="contactor" value="<%=(departments.get(0).getLEADER()!=null)?departments.get(0).getLEADER():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">联系人手机：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="mphone" value="<%=(departments.get(0).getMphone()!=null)?departments.get(0).getMphone():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">部门电话：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="phone" value="<%=(departments.get(0).getTELEPHONE()!=null)?departments.get(0).getTELEPHONE():""%>" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">邮件地址：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="email" value="<%=(departments.get(0).getEMAIL()!=null)?departments.get(0).getEMAIL():""%>" required  class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">文件索引号前缀：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="indexprefix" value="<%=(departments.get(0).getIndexrules()!=null)?departments.get(0).getIndexrules():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label" style="width: 200px">文件索引号起始值：</label>
            <div class="layui-input-block" style="margin-left: 250px">
                <input type="text" name="indexstartnum" value="<%=(departments.get(0).getIndexnum()!=0)?departments.get(0).getIndexnum():""%>" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block" align="center">
                <button type="button" class="layui-btn" onclick="javascript:doUpdate();">立即提交</button>
                <button type="button" class="layui-btn layui-btn-primary" onclick="javascript:closewin();">关闭窗口</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
