<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ include file="../../../include/auth.jsp"%>
<%
    int status = 0;
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    int siteid = authToken.getSiteID();
    int orgid = authToken.getOrgid();
    int company_orgid = authToken.getCompanyid();
    int dept_orgid = authToken.getDeptid();
    int companyid = 0;
    int deptid = 0;
    int org_level = 0;
    int rootOrgid = 0;
    int orgtype = 0;           //组织架构节点类型，1表示公司，0表示部门
    int source_id = 0;
    long orderid = 0;
    String startday = null;
    String endday = null;
    int projcode=ParamUtil.getIntParameter(request, "projcode",100);

    IOrderManager orderMgr = orderPeer.getInstance();
    IUserManager userManager = UserPeer.getInstance();
    ApplicationContext appContext = SpringInit.getApplicationContext();
    Organization organization =null;
    Organization rootOrganization = null;
    List<Companyinfo> companyinfos = null;
    List<Department> departments = null;
    OrganizationService organizationService = null;
    if (appContext!=null) {
        organizationService = (OrganizationService)appContext.getBean("organizationService");
        rootOrganization = organizationService.getRootOrganization();
        rootOrgid = rootOrganization.getID().intValue();
        organization = organizationService.getAOrganization(BigDecimal.valueOf(orgid));
        if (organization!=null) {
            org_level = organization.getLLEVEL().intValue();
            orgtype = organization.getCOTYPE().intValue();
            companyinfos = organizationService.getMainCompaniesByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
            if (companyinfos!=null)
                if (companyinfos.size()>0) companyid = companyinfos.get(0).getID().intValue();
            departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteid),organization.getID());
            if (departments!=null)
                if (departments.size()>0) deptid = departments.get(0).getID().intValue();
        }
    }

    if (startflag == 1) {
        status = ParamUtil.getIntParameter(request, "status",8);
        startday = ParamUtil.getParameter(request, "searchtime1");
        endday = ParamUtil.getParameter(request, "searchtime2");
        /*company_orgid = ParamUtil.getIntParameter(request, "_sel_n1",0);
        dept_orgid = ParamUtil.getIntParameter(request, "_sel_n2",0);
        source_id = ParamUtil.getIntParameter(request, "_sel_n4",0);*/
        projcode=ParamUtil.getIntParameter(request, "projcode",100);

        String where_clause = "";
        //设置状态查询条件
        if (where_clause != "" && where_clause!=null) {
            if (status == 0)
                where_clause = where_clause +" and (o.status=7 or o.status=8 or o.status=9)";
            else
                where_clause = where_clause +" and o.status=" + status;
        } else {
            if (status == 0)
                where_clause = where_clause + "(o.status=7 or o.status=8 or o.status=9)";
            else
                where_clause = where_clause +"o.status=" + status;
        }
        //设置报名项目查询条件
        if (where_clause!="" && where_clause!=null) {
            where_clause = where_clause +" and projcode='" + projcode + "'";

        } else {
            where_clause = where_clause + "projcode='" + projcode + "'";
        }

        if ((endday != "") && (endday != null)) {
            endday = endday + " 23:59:59";
        }
        if ((startday != "") && (startday != null)) {
            startday = startday + " 00:00:00";
        }

        companyinfos = organizationService.getMainCompaniesByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(company_orgid));
        if (companyinfos!=null && companyinfos.size()>0) {
            companyid = companyinfos.get(0).getID().intValue();
        }

        departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(dept_orgid));
        if (departments!=null && departments.size()>0) {
            deptid = departments.get(0).getID().intValue();
        }

        //String whereClause,String startday,String endday,int siteid,int comid,int deptid,int source,int orgtype
        List<Order> list = orderMgr.getOrderListToExcel(where_clause,startday,endday,siteid,companyid,deptid,source_id,orgtype);
        for(int ii=0; ii<list.size(); ii++) {
            Order order = list.get(ii);
        }

        if(list.size()>0) {
            String path = application.getRealPath("/");
            String downloadurl = "";
            int flag = ParamUtil.getIntParameter(request, "flag", -1);
            if (flag == 0) { //导出订单
                downloadurl = ExportOrderToExcel.ExportOrders(list, path);
            } else if (flag == 1) {  //导出符合方正系统要求的订单
                downloadurl = ExportOrderToExcel.ExportOrdersForFangzheng(list, path);
            } else {
                System.out.println("flag未取到值");
            }
            if (downloadurl != null) {
                //新建一个SmartUpload对象
                SmartUpload su = new SmartUpload();
                //初始化
                su.initialize(pageContext);
                // 设定contentDisposition为null以禁止浏览器自动打开文件
                su.setContentDisposition(null);
                su.downloadFile(path + downloadurl);
            }
        }else{
            out.println("<script lanugage=\"javascript\">alert('无数据');</script>");
        }
    }
%>
<!doctype html>
<html>
<head>
    <title>将订单导出EXCEL文件</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <!--link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script-->


    <link href="../../../css/jquery-ui.css" rel="stylesheet" type="text/css" />
    <script language="javascript" src="../../../js/jquery-1.12.4.js" type="text/javascript"></script>
    <script language="javascript" src="../../../js/jquery-ui.js" type="text/javascript"></script>
    <!--script language="JavaScript" src="../include/setday.js"></script-->
    <script language="JavaScript">
        function sub(v){
            var searchtime1 = document.getElementById("searchtime1").value;
            var searchtime2 = document.getElementById("searchtime2").value;
            if(searchtime1 == null || searchtime1 == ""){
                alert("请选择开始时间");
                return false;
            }
            if(searchtime2 == null || searchtime2 == ""){
                alert("请选择截止时间");
                return false;
            }
            document.addForm.flag.value=v;
            document.addForm.submit();
            //return true;
        }
        function closewin() {
            window.close();
        }
    </script>

</head>
<body bgcolor="#FFFFFF">
<center><br>
    <form action="exportOrderToExcel.jsp" method="post" name="addForm">
        <input type="hidden" name="startflag" value="1">
        <input type="hidden" name="flag" value="-1">
        <table width="100%" border="0" cellpadding="0">
            <tr bgcolor="#F4F4F4" align="center">
                <td class="moduleTitle"><font color="#48758C">导出指定状态的订单</font></td>
            </tr>

            <tr bgcolor="#d4d4d4" align="right">
                <td>
                    <table width="100%" border="0" cellpadding="2" cellspacing="1">
                        <tr bgcolor="#FFFFFF">
                            <td width="10%">付款日期</td>
                            <td width="40%" valign="top"> 从
                                <input type="text" size="20" name="searchtime1" id="begindateid" readonly>
                                到
                                <input type="text" size="20" name="searchtime2" id="enddateid" readonly>
                            </td>
                            <td width="15%" align="center" class="txt">

                                <div id=l1>
                                    <select name="projcode" id="projcode">
                                        <option value=100 <%=(projcode==100)?"selected":""%>>请选择项目</option>
                                        <option value=101 <%=(projcode==101)?"selected":""%>>一级注册建造师</option>
                                        <option value=102 <%=(projcode==102)?"selected":""%>>二级注册建造师</option>
                                        <option value=103 <%=(projcode==103)?"selected":""%>>一级注册造价师</option>
                                        <option value=104 <%=(projcode==104)?"selected":""%>>二级注册造价师</option>
                                        <option value=105 <%=(projcode==105)?"selected":""%>>现场管理人员</option>
                                        <option value=106 <%=(projcode==106)?"selected":""%>>现场操作人员</option>
                                        <option value=106 <%=(projcode==107)?"selected":""%>>安全三类人员</option>
                                        <option value=106 <%=(projcode==108)?"selected":""%>>其他</option>
                                    </select>
                                </div>
                            </td>

                            <%--<td width="15%" align="center" class="txt">
                                <%if (SecurityCheck.hasPermission(authToken,54)) {%>
                                <div id=l4>
                                    <select name="_sel_n4" id="_sel_id4" style="width: 80px;"> <!--onchange="javascript:setSecondOptions(this);"-->
                                        <option value=0 <%=(source_id==0)?"selected":""%>>订单来源</option>
                                        <option value=1 <%=(source_id==1)?"selected":""%>>全部</option>
                                        <option value=2 <%=(source_id==2)?"selected":""%>>网站</option>
                                        <option value=3 <%=(source_id==3)?"selected":""%>>公众号</option>
                                    </select>
                                </div>
                                <%} else if(organization!=null) {
                                    if (organization.getID().intValue() == rootOrgid) {%>
                                <div id=l4>
                                    <select name="_sel_n4" id="_sel_id4" style="width: 80px;"> <!--onchange="javascript:setSecondOptions(this);"-->
                                        <option value=0 <%=(source_id==0)?"selected":""%>>订单来源</option>
                                        <option value=1 <%=(source_id==1)?"selected":""%>>全部</option>
                                        <option value=2 <%=(source_id==2)?"selected":""%>>网站</option>
                                        <option value=3 <%=(source_id==3)?"selected":""%>>公众号</option>
                                    </select>
                                </div>
                                <%}}%>
                            </td>--%>
                            <td align="center">
                                <select name="status">
                                    <option value="0" <%=(status==0)?"selected":""%>>所有订单</option>
                                    <option value=8 <%=(status==8)?"selected":""%>>已付款</option>
                                    <option value=7 <%=(status==7)?"selected":""%>>等待付款</option>
                                    <option value=9 <%=(status==9)?"selected":""%>>超时取消</option>

                                    <!--option value="_" selected>所有订单</option>
                                    <option value="0">新订单</option>
                                    <option value="1">处理中</option>
                                    <option value="2">发货</option>
                                    <option value="3">退货</option>
                                    <option value="4">完成</option>
                                    <option value="5">拒收</option>
                                    <option value="6">缺货</option>
                                    <option value="7">等待客户付款</option>
                                    <option value="8">已付款</option-->
                                </select>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
        </table>
        <p align="center">
            <input type="button" name="tt" value="导出全数据订单" onclick="return sub(0);">
         <%--   <input type="button" name="Ok" value="导出方正格式订单" onclick="return sub(1);">&nbsp;&nbsp;--%>
            <input type="button" name="close" value="关闭" onclick="javascript:closewin();">
        </p>
    </form>
    <p>&nbsp; </p>
</center>
<script language="javascript">
    $(document).ready(function(){
        $.datepicker.regional['zh-CN'] = {
            clearText: '清除',
            clearStatus: '清除已选日期',
            closeText: '关闭',
            closeStatus: '不改变当前选择',
            prevText: '上月',
            prevStatus: '显示上月',
            prevBigText: 'Prev',
            prevBigStatus: '显示上一年',
            nextText: '下月',
            nextStatus: '显示下月',
            nextBigText: 'Next',
            nextBigStatus: '显示下一年',
            currentText: '今天',
            currentStatus: '显示本月',
            monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
            monthNamesShort: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],
            monthStatus: '选择月份',
            yearStatus: '选择年份',
            weekHeader: '周',
            weekStatus: '年内周次',
            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
            dayNamesMin: ['日','一','二','三','四','五','六'],
            dayStatus: '设置 DD 为一周起始',
            dateStatus: '选择 m月 d日, DD',
            dateFormat: 'yy-mm-dd',
            firstDay: 1,
            initStatus: '请选择日期',
            isRTL: false};
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);

        $("#begindateid").datepicker({
            dateFormat: 'yy-mm-dd',
            showOtherMonths: true,
            selectOtherMonths: true,
            showButtonPanel: true,
            showOn: "both",
            buttonImageOnly: true,
            // buttonImage: "/css/images/icon_calendar.gif",
            buttonText: "",
            changeMonth: true,
            changeYear: true
        });

        $("#enddateid").datepicker({
            showOtherMonths: true,
            selectOtherMonths: true,
            showButtonPanel: true,
            showOn: "both",
            buttonImageOnly: true,
            // buttonImage: "/css/images/icon_calendar.gif",
            buttonText: "",
            changeMonth: true,
            changeYear: true
        });
    })
</script>
</body>
</html>