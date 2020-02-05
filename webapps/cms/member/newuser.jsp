<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ include file="../include/auth.jsp"%>
<%
    if (!SecurityCheck.hasPermission(authToken, 54)) {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    boolean error = false;
    int siteID = authToken.getSiteID();
    String username = authToken.getUserID();

    int orgid = ParamUtil.getIntParameter(request,"sorgid",0);
    int currentpage = ParamUtil.getIntParameter(request,"pageno",0);
    int doSearchFlag = ParamUtil.getIntParameter(request,"searchFlag",0);
    int flag = ParamUtil.getIntParameter(request,"flag",0);
    int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);

    String userid    = ParamUtil.getParameter(request,"userid");
    String nickname  = ParamUtil.getParameter(request,"username");
    String password  = ParamUtil.getParameter(request,"passwd");
    List rightList   = ParamUtil.getParameterValues(request,"rightList");
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    ApplicationContext appContext = SpringInit.getApplicationContext();

    if (doCreate) {
        if (userid == null || password == null) error = true;
    }

    IRightsManager rightsManager = RightsPeer.getInstance();
    rightList = rightsManager.getRights();       //获取所有权限
    IUserManager uMgr = UserPeer.getInstance();
    List departmentList = uMgr.getDepartments(siteID);

    List<Organization> organizations =null;
    String optionVals = null;
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService) appContext.getBean("organizationService");
        optionVals = organizationService.getOrgOptionsByCustomerid(BigDecimal.valueOf(authToken.getSiteID()), -1);
    }
%>

<html>
<head>
    <title>创建新用户</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" type="text/css" href="../design/css/layui.css">
    <link rel="stylesheet" type="text/css" href="../css/cms_css.css">
    <link rel="stylesheet" type="text/css" href="../design/css/modules/layer/default/layer.css">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/jquery.form.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script type="text/javascript" src="../design/lay/modules/layer.js"></script>
    <script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
    <SCRIPT LANGUAGE=javascript>
        function saveinfo() {
            if (!InputValid(userForm.userid, 1, "string", 0, 0, 0,"用户名"))
                return (false);
            if (!validpassword(userForm.passwd.value, userForm.repasswd.value ))
                return (false);
            if (!InputValid(userForm.realname, 1, "string", 1, 2, 12,"用户姓名"))
                return (false);

            var el = userForm.rightList;
            for (var i=el.options.length-1; i>=0; i--) {
                el.options[i].selected = true;
            }

            //保存本FORM的信息
            var userid = userForm.userid.value;
            var passwd = userForm.passwd.value;
            var realname = userForm.realname.value;
            var mphone = userForm.mphone.value;
            var phone = userForm.phone.value;
            var email = userForm.email.value;
            var orgid = $("#orgid").val();
            var rightids = "";
            $("#userFormID option:selected").each(function(){
                //alert($(this).text() + "==" + $(this).val());
                rightids = rightids + $(this).val() + ",";
            });
            if (rightids.length>0) rightids = rightids.substring(0,rightids.length-1);

            //分页和检索条件信息
            var currentPage = userForm.pageno.value;
            var doSearchFlag = userForm.searchFlag.value;
            var flag = userForm.flag.value;
            var ascdesc = userForm.ascdesc.value;

            var ajaxFormOption = {
                url: "createUser.jsp",                                           //请求url
                type: "post",                                                     //提交方式
                data:{
                    doCreate:true,
                    userid:userid,
                    passwd:passwd,
                    realname:realname,
                    mphone:mphone,
                    phone:phone,
                    email:email,
                    orgid:orgid,
                    rightids:rightids
                },
                dataType:'json',
                async:false,
                success:function(jsondata,status,xhr){
                    if (jsondata.ERRCODE === 0) {
                        window.opener.userInfoList(orgid,currentPage,doSearchFlag,flag,ascdesc);
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
            };

            //不需要submit按钮，可以是任何元素的click事件
            $("#userFormID").ajaxSubmit(ajaxFormOption);
        }

        var rights_sel = "";
        function selectList(srcList,desList) {
            seleflg = false;
            for(i = srcList.length - 1; i >= 0; i--) {
                if(srcList.options[i].selected == true) {
                    var oOption = document.createElement("OPTION");
                    oOption.value = srcList.options[i].value;
                    oOption.text = srcList.options[i].text;
                    desList.add(oOption);
                    srcList.options[i] = null;
                    seleflag = true;
                } else {
                    continue;
                }
            }
            if (!seleflag) {
                alert ("您没有选择权限，请选择！");
                return false;
            }
        }
    </SCRIPT>
</head>
<body bgcolor="#cccccc">
<form name="userForm" method="post" id="userFormID">
    <input type="hidden" name="doCreate" value="true">
    <input type="hidden" name="sorgid" value="<%=orgid%>">
    <input type="hidden" name="pageno" value="<%=currentpage%>">
    <input type="hidden" name="searchFlag" value="<%=doSearchFlag%>">
    <input type="hidden" name="flag" value="<%=flag%>">
    <input type="hidden" name="ascdesc" value="<%=ascdesc%>">
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr style="height: 50px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td align="right" valign="middle">用户名：</td>
            <td align="left" valign="middle"><input type="text" name="userid" required  lay-verify="required" value="" class="new_page_input_1" autocomplete="off"></td>
            <td align="right" valign="middle">密码：</td>
            <td align="left" valign="middle"><input type="password" name="passwd" class="new_page_input_1"  autocomplete="new-password"></td>
        </tr>
        <tr style="height: 10px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td align="right" valign="middle">确认密码：</td>
            <td align="left" valign="middle"><input type="password" name="repasswd" class="new_page_input_1"  autocomplete="new-password"></td>
            <td align="right" valign="middle">真实姓名：</td>
            <td align="left" valign="middle"><input type="text" name="realname" class="new_page_input_1"  autocomplete="off"></td>
        </tr>
        <tr style="height: 10px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td align="right" valign="middle">手机号码：</td>
            <td align="left" valign="middle"><input type="text" name="mphone" value="" class="new_page_input_1"  autocomplete="off"></td>
            <td align="right" valign="middle">办公电话：</td>
            <td align="left" valign="middle"><input type="text" name="phone" value="" class="new_page_input_1"  autocomplete="off"></td>
        </tr>
        <tr style="height: 10px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td align="right" valign="middle">电子邮件：</td>
            <td align="left" valign="middle"><input type="text" name="email" value="" class="new_page_input_1"  autocomplete="off"></td>
            <td align="left" valign="middle"></td>
            <td align="left" valign="middle"></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="10" cellpadding="0">
        <tr style="height: 10px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td width="16%">&nbsp;</td>
            <td width="13%">备选权限</td>
            <td width="30%"></td>
            <td width="41%">授予权限</td>
        </tr>
        <tr style="height: 10px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td align="right" valign="middle">授权列表：</td>
            <td>
                <select name="leftList" size="9" multiple style="width:200px;font-size:9pt">
                    <%for (int i=0; i<rightList.size(); i++) {
                        int rightid = ((Rights)(rightList.get(i))).getRightID();
                        String rightname =  StringUtil.gb2iso4View(((Rights)(rightList.get(i))).getRightCName());
                    %>
                    <option value="<%=rightid%>">&nbsp;<%=rightname%></option>
                    <%}%>
                </select>
            </td>
            <td align="center" valign="middle"><p>
                <input type="button" name="add" value=" > " onclick="return selectList(this.form.leftList,this.form.rightList)"><br><br>
                <input type="button" name="delete" value=" < " onclick="return selectList(this.form.rightList,this.form.leftList)">
            </td>
            <td>
                <select name="rightList" id="choicedRightID" size="9" multiple style="width:200px;font-size:9pt"></select>
            </td>
        </tr>
        <tr style="height: 10px;">
            <td colspan="4"></td>
        </tr>
        <tr>
            <td align=right>单位或部门：</td>
            <td>&nbsp;<select name="org" id="orgid" style="width:200px;font-size:9pt">
                <option value="0">请选择</option>
                <%=optionVals%>
            </select> </td>
        </tr>
        <tr style="height: 50px;">
            <td colspan="4"></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="20" cellpadding="0">
        <tr>
            <td align="right" valign="middle"><input type="button" name="button3" id="button3" value="保存" class="button_bg_2" onclick="javascript:saveinfo();"></td>
            <td align="left" valign="middle"><input type="button" name="button4" id="button4" value="取消" class="button_bg_2" onclick="window.close();"/></td>
        </tr>
    </table>
</form>
</body>
</html>