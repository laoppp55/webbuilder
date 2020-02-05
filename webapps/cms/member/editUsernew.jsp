<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ include file="../include/auth.jsp"%>
<%
    request.setCharacterEncoding("utf-8");
    if (!SecurityCheck.hasPermission(authToken, 54)) {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    int retcode = -2;
    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String userid = ParamUtil.getParameter(request, "userid");
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int currentPage = ParamUtil.getIntParameter(request, "pageno", 1);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int flag = ParamUtil.getIntParameter(request,"flag",0);
    int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);
    int s_orgid = ParamUtil.getIntParameter(request,"sorgid",0);

    User user = new User();
    IUserManager userMgr = UserPeer.getInstance();
    IRightsManager rightMgr = RightsPeer.getInstance();
    ApplicationContext appContext = SpringInit.getApplicationContext();

    List rightList = new ArrayList();           //赋给用户的权限列表
    List remainRightList = new ArrayList();     //未赋给用户的权限列表
    List rolesList = new ArrayList();

    //得到原始数据
    user = userMgr.getUser(userid, siteid);
    rightList = rightMgr.getGrantedUserRights(userid);
    remainRightList = rightMgr.getRemainRights(userid);
    rolesList = userMgr.getMemberRolesList(userid);

    String optionVals = null;
    OrganizationService organizationService = null;
    int orgid = 0;               //存储本次修改新选择的ORGID
    if (!doUpdate && appContext!=null) {
        organizationService = (OrganizationService)appContext.getBean("organizationService");
        optionVals = organizationService.getOrgOptionsByCustomerid(BigDecimal.valueOf(authToken.getSiteID()),user.getOrgid());
    }

    if (doUpdate) {
        List rightListOfUpdated = ParamUtil.getParameterValues(request, "rightList");
        List rolesListOfUpdated = ParamUtil.getParameterValues(request, "roles");
        int departmentarticles = ParamUtil.getIntParameter(request, "departmentarticle", 0);
        List departmentarticleslist = ParamUtil.getParameterValues(request, "drList");
        String nickname = ParamUtil.getParameter(request, "username");
        String emailaccount = ParamUtil.getParameter(request, "emailaccount");
        String emailpasswd = ParamUtil.getParameter(request, "emailpasswd");
        String email = ParamUtil.getParameter(request, "email");
        String phone = ParamUtil.getParameter(request, "phone");
        String mphone = ParamUtil.getParameter(request, "mphone");
        orgid = ParamUtil.getIntParameter(request,"org",0);
        System.out.println(s_orgid + "===" + orgid);
        String departmentarticleids = "";
        for (int i = 0; i < departmentarticleslist.size(); i++) {
            if (i == departmentarticleslist.size() - 1) {
                departmentarticleids += (String) departmentarticleslist.get(i);
            } else {
                departmentarticleids += (String) departmentarticleslist.get(i) + ",";
            }
        }
        List<Companyinfo> companyinfos = new ArrayList<Companyinfo>();
        List<Department> departments = new ArrayList<Department>();

        if (appContext!=null) {
            organizationService = (OrganizationService)appContext.getBean("organizationService");
            departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
            companyinfos = organizationService.getMainCompaniesByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
        }

        user.setID(userid);
        user.setSiteID(siteid);
        user.setNickName(nickname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setMobilePhone(mphone);
        user.setOrgid(orgid);
        if (departments.size()>=1) {
            user.setDepartment(departments.get(0).getCNAME());
            user.setDeptid(departments.get(0).getID().intValue());
        }else {
            user.setDepartment("");
            user.setDeptid(0);
        }
        if (companyinfos.size()>=1) {
            user.setCompany(companyinfos.get(0).getCOMPANYNAME());
            user.setCompanyid(companyinfos.get(0).getID().intValue());
        }else {
            user.setCompany("");
            user.setCompanyid(0);
        }
        user.setRightList(rightListOfUpdated);
        user.setRolesList(rolesListOfUpdated);
        user.setDepartmentarticlestype(departmentarticles);
        user.setDepartmentarticlesids(departmentarticleids);
        user.setEmailaccount(emailaccount);
        user.setEmailpasswd(emailpasswd);
        retcode = userMgr.update(user, username);
    }
%>

<html>
<head>
    <title>编辑用户属性</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <link rel="stylesheet" href="../design/css/layui.css">

    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
    <script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
    <SCRIPT LANGUAGE=javascript>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });

            var retcode = <%=retcode%>;
            var range = <%=range%>;
            var currentPage = <%=currentPage%>;
            var doSearchFlag = <%=doSearchFlag%>;
            var flag = <%=flag%>;
            var ascdesc = <%=ascdesc%>;
            var orgid = <%=orgid%>;
            if (retcode == 0) {
                alert("用户信息修改成功");
                window.opener.userInfoList(orgid,currentPage,doSearchFlag,flag,ascdesc);
                window.close();
            }
        });

        function checkinput(form) {
            if (!InputValid(form.username, 1, "string", 1, 2, 12, "用户姓名"))
                return false;

            var el = userForm.rightList;
            for (var i = el.options.length - 1; i >= 0; i--)
                el.options[i].selected = true;
            if (form.departmentarticle.value == 2)
            {
                var e2 = form.drList;
                if (e2.options.length == 0)
                {
                    alert("请选择审核的部门！");
                    return false;
                }
                for (var a = e2.options.length - 1; a >= 0; a--)
                {
                    e2.options[a].selected = true;
                }
            }
            return true;
        }

        var rights_sel = "";
        function selectList(srcList, desList) {
            seleflg = false;
            for (i = srcList.length - 1; i >= 0; i--) {
                if (srcList.options[i].selected == true) {
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
                alert("您没有选择权限，请选择！");
                return false;
            }
        }

        function selectdepartmentarticles(type) {
            userForm.departmentarticle.value = type;
            if (type == 2) {
                userForm.add1.disabled = false;
                userForm.delete1.disabled = false;
            } else {
                userForm.add1.disabled = true;
                userForm.delete1.disabled = true;
            }
        }
    </SCRIPT>
</head>

<body bgcolor="#cccccc">
<form name="userForm" method="post" onsubmit="return checkinput(this);">
    <input type="hidden" name="doUpdate" value="true">
    <input type="hidden" name="sorgid" value="<%=s_orgid%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="start" value="<%=currentPage%>">
    <input type="hidden" name="searchFlag" value="<%=doSearchFlag%>">
    <input type="hidden" name="flag" value="<%=flag%>">
    <input type="hidden" name="ascdesc" value="<%=ascdesc%>">
    <input type="hidden" name="departmentarticle" value="<%=user.getDepartmentarticlestype()%>">
    <input type="hidden" name="lw" value="">
    <input type="hidden" name="deptlw" value="">
    <table width="100%" border="0" align="center">
        <tr height="35">
            <td width="20%" align="right">用户名：</td>
            <td width="80%">&nbsp;<input name="userid" readonly value="<%=(user.getID()!=null)?user.getID():""%>"></td>
        </tr>
        <tr height="35">
            <td align="right">用户姓名：</td>
            <td>&nbsp;<input type="text" name="username" value="<%=(user.getNickName()!=null)?user.getNickName():""%>"></td>
        </tr>
        <tr height="35">
            <td align="right">手机号码：</td>
            <td>&nbsp;<input type="text" name="mphone" value="<%=(user.getMobilePhone()!=null)?user.getMobilePhone():""%>"></td>
        </tr>
        <tr height="35">
            <td align="right">办公电话：</td>
            <td>&nbsp;<input type="text" name="phone" value="<%=(user.getPhone()!=null)?user.getPhone():""%>"></td>
        </tr>
        <tr height="35">
            <td align="right">电子邮件：</td>
            <td>&nbsp;<input type="text" name="email" value="<%=(user.getEmail()!=null)?user.getEmail():""%>"></td>
        </tr>
        <tr>
            <td align="right">授权列表：</td>
            <td>
                <table width="75%" border="0" cellspacing=2 cellpadding=1>
                    <tr>
                        <td width="44%">备选权限<br>
                            <select name="leftList" size="9" multiple style="width:200px;font-size:9pt">
                                <%
                                    for (int i = 0; i < remainRightList.size(); i++) {
                                        int rightid = ((Rights) (remainRightList.get(i))).getRightID();
                                        String rightname = StringUtil.gb2iso4View(((Rights) (remainRightList.get(i))).getRightCName());
                                %>
                                <option value="<%=rightid%>">&nbsp;<%=rightname%>
                                </option>
                                <%}%>
                            </select>
                        </td>
                        <td valign="middle">
                            <input type="button" name="add" value=" > "
                                   onclick="return selectList(this.form.leftList,this.form.rightList)"><br><br>
                            <input type="button" name="delete" value=" < "
                                   onclick="return selectList(this.form.rightList,this.form.leftList)">
                        </td>
                        <td width="44%" valign="top" align="center">授与权限<br>
                            <select name="rightList" size="9" multiple style="width:200px;font-size:9pt">
                                <%
                                    for (int i = 0; i < rightList.size(); i++) {
                                        Rights right = (Rights)rightList.get(i);
                                        int rightid = right.getRightID();
                                        String rightname = StringUtil.gb2iso4View(((Rights) (rightList.get(i))).getRightCName());
                                %>
                                <option value="<%=rightid%>">&nbsp;<%=rightname%>
                                </option>
                                <%}%>
                            </select>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align=right>所在单位或部门：</td>
            <td>&nbsp;<select name="org" id="orgid" style="width:200px;font-size:9pt">
                <option value="0">请选择</option>
                <%=optionVals%>
            </select></td>
        </tr>
        <tr>
            <td colspan="2" height="40" align="center">
                <input type="submit" class=tine value="  保存  ">&nbsp;&nbsp;
                <input type="button" class=tine value="  取消  " onclick="window.close();">
            </td>
        </tr>
    </table>
</form>

</body>
</html>