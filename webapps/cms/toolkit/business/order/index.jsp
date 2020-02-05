<%@ page import="java.util.*,
                 java.text.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ include file="../../../include/auth.jsp"%>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = 20;
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", 0);
    int updateflag = ParamUtil.getIntParameter(request, "updateflag", 0);
    int status = ParamUtil.getIntParameter(request,"status",1);
    int siteid = authToken.getSiteID();
    int orgid = authToken.getOrgid();
    int company_orgid = authToken.getCompanyid();
    int dept_orgid = authToken.getDeptid();
    int companyid = 0;
    int deptid = 0;
    int org_level = 0;
    int rootOrgid = 0;
    int orgtype = 0;           //组织架构节点类型，1表示公司，0表示部门
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

    if (updateflag == 1) {
        String[] ordergo = request.getParameterValues("ordergo");
        long updateid = 0;
        int oldstatus = -1;
        for (int i = 0; i < ordergo.length; i++) {
            updateid = Long.parseLong(ordergo[i]);
            //oldstatus = orderMgr.getStatus(updateid);
            Order sorder = orderMgr.getAOrder(updateid);
            //int userid = sorder.getUserid();
            //int usescores = sorder.getUserscores();
            oldstatus = orderMgr.getStatus(updateid);
            orderMgr.updateStatus(updateid, 2, authToken.getUserID());
            //orderMgr.freeScores(userid,usescores);
        }
        response.sendRedirect("index.jsp");
    }

    if (updateflag == 2) {
        String[] ordergo = request.getParameterValues("ordergo");
        long updateid = 0;
        int oldstatus = -1;
        for (int i = 0; i < ordergo.length; i++) {
            updateid = Long.parseLong(ordergo[i]);
            oldstatus = orderMgr.getStatus(updateid);
            orderMgr.updateStatus(updateid, 6, authToken.getUserID());
        }
        response.sendRedirect("index.jsp");
    }

    Order order = new Order();
    List currentlist = new ArrayList();
    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;
    long orderid = 0;
    String startday = null;
    String endday = null;
    String porductname = "";
    String username = "";
    int sendway = 0;
    int orderflag = 0;
    String pphone = "";
    int user_id = 0;
    int source_id = 0;
    String jumpstr = "";
    int total_dynum = 0;

    if (searchflag == 1) {
        //porductname = ParamUtil.getParameter(request, "detailedname");
        //sendway = ParamUtil.getIntParameter(request, "sendway", -1);
        //orderflag = ParamUtil.getIntParameter(request, "orderflag", -1);
        orderid = ParamUtil.getLongParameter(request, "orderid", 0);
        username = ParamUtil.getParameter(request, "username");
        pphone = ParamUtil.getParameter(request, "phone");
        status = ParamUtil.getIntParameter(request, "status",1);
        startday = ParamUtil.getParameter(request, "searchtime1");
        endday = ParamUtil.getParameter(request, "searchtime2");
        //company_orgid = ParamUtil.getIntParameter(request, "_sel_n1",0);
        //dept_orgid = ParamUtil.getIntParameter(request, "_sel_n2",0);
        //user_id = ParamUtil.getIntParameter(request, "_sel_n3",0);
        //source_id = ParamUtil.getIntParameter(request, "_sel_n4",0);
        projcode = projcode=ParamUtil.getIntParameter(request, "projcode",100);

        String where_clause = "";

        //设置订单号查询条件
        if (orderid != 0) {
            where_clause = where_clause + "orderid=" + orderid;
            jumpstr = jumpstr + "&orderid=" + orderid;
        }

        //设置客户名字查询条件
        if (where_clause!="" && where_clause!=null) {
            if (username!="" && username!=null) {
                where_clause = where_clause +" and name='" + username + "'";
                jumpstr = jumpstr + "&username=" + username;
            }
        } else {
            if (username!=null && username!="") {
                where_clause = where_clause + "name='" + username + "'";
                jumpstr = jumpstr + "&username=" + username;
            }
        }

        //设置用户的联系电话为查询条件
        if (where_clause!="" && where_clause!=null) {
            if (pphone!="" && pphone!=null) {
                where_clause = where_clause +" and phone='" + pphone + "'";
                jumpstr = jumpstr + "&phone=" + pphone;
            }
        } else {
            if (pphone!="" && pphone!=null) {
                where_clause = where_clause + "phone='" + pphone + "'";
                jumpstr = jumpstr + "&phone=" + pphone;
            }
        }

        //设置状态查询条件
        if (where_clause != "" && where_clause!=null) {
            if (status == 0)
                where_clause = where_clause +" and (status=7 or status=8 or status=9)";
            else
                where_clause = where_clause +" and status=" + status;
            jumpstr = jumpstr + "&status=" + status;
        } else {
            if (status == 0)
                where_clause = where_clause + "(status=7 or status=8 or status=9)";
            else
                where_clause = where_clause +"status=" + status;
            jumpstr = jumpstr + "&status=" + status;
        }

        if ((endday != "") && (endday != null)) {
            endday = endday + " 23:59:59";
            jumpstr = jumpstr + "&searchtime2=" + endday;
        }
        if ((startday != "") && (startday != null)) {
            startday = startday + " 00:00:00";
            jumpstr = jumpstr + "&searchtime1=" + startday;
        }
        //设置报名项目查询条件
        if (where_clause!="" && where_clause!=null) {
           // if (projcode!="" && projcode!=null) {
                where_clause = where_clause +" and projcode='" + projcode + "'";
                jumpstr = jumpstr + "&projcode=" + projcode;
            //}
        } else {
            //if (projcode!=null && projcode!="") {
                where_clause = where_clause + "projcode='" + projcode + "'";
                jumpstr = jumpstr + "&projcode=" + projcode;
            //}
        }

      /*  companyinfos = organizationService.getMainCompaniesByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(company_orgid));
        if (companyinfos!=null && companyinfos.size()>0) {
            companyid = companyinfos.get(0).getID().intValue();
            jumpstr = jumpstr + "&_sel_n1=" + company_orgid;
        }

        departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(dept_orgid));
        if (departments!=null && departments.size()>0) {
            deptid = departments.get(0).getID().intValue();
            jumpstr = jumpstr + "&_sel_n2=" + dept_orgid;
        }

        jumpstr = jumpstr + "&_sel_n3=" + user_id;
        jumpstr = jumpstr + "&_sel_n4=" + source_id;*/




        jumpstr = jumpstr + "&searchflag=1";
        System.out.println(jumpstr);

        //startindex, range, siteid,status,whereClause,startday,endday,com_orgid,dept_orgid,uid,source
        currentlist = orderMgr.searchOrderList(startrow, range,siteid,status,where_clause,startday,endday,orgtype,companyid,deptid,user_id,source_id);
        rows = orderMgr.searchOrderNum(siteid,status,where_clause,startday,endday,orgtype,companyid,deptid,user_id,source_id);
        total_dynum = orderMgr.searchSubscribeOrderNum(siteid,status,where_clause,startday,endday,orgtype,companyid,deptid,user_id,source_id);
        if (rows < range) {
            totalpages = 1;
            currentpage = 1;
        } else {
            if (rows % range == 0)
                totalpages = rows / range;
            else
                totalpages = rows / range + 1;

            currentpage = startrow / range + 1;
        }
    } else {
        if (SecurityCheck.hasPermission(authToken, 54)){
            currentlist = orderMgr.getOrderList(startrow, range, siteid, status);
            rows = orderMgr.getTotalOrderNumByStatus(siteid, status);
            total_dynum = orderMgr.getTotalSubscribeNum(siteid, status);
        } else {
            if (organization!=null) {
                if (organization.getID().intValue()==rootOrgid) {
                    currentlist = orderMgr.getOrderList(startrow, range,siteid,status);
                    rows = orderMgr.getTotalOrderNumByStatus(siteid,status);
                    total_dynum = orderMgr.getTotalSubscribeNum(siteid, status);
                } else {
                    List<User> users = null;
                    if (orgtype == 1) {
                        if (companyinfos!=null) users = userManager.getUsersByCompanyid(siteid, companyinfos.get(0).getID().intValue());
                    }else {
                        if (departments!=null) users = userManager.getUsersByDeptid(siteid, departments.get(0).getID().intValue());
                    }
                    if (users!=null) {
                        currentlist = orderMgr.getOrderListByUsers(startrow, range,siteid,status,users);
                        rows = orderMgr.getOrderNumByStatusAndUsers(siteid,status,users);
                        total_dynum = orderMgr.getSubscribeNumByStatusAndUsers(siteid,status,users);
                    }
                }
            }
        }

        if (rows < range) {
            totalpages = 1;
            currentpage = 1;
        } else {
            if (rows % range == 0)
                totalpages = rows / range;
            else
                totalpages = rows / range + 1;
            currentpage = startrow / range + 1;
        }
    }
%>
<html>
<head>
    <title>订单管理</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language="JavaScript" src="../include/setday.js"></script>
    <script language="JavaScript" src="../../../js/jquery-1.11.1.min.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script language="javascript">
        function golist(r) {
            window.location = "index.jsp?startrow=" + r;
        }

        function gotosearch() {
            searchForm.action = "index.jsp";
            searchForm.submit();
        }

        function jumppage(r, str) {
            window.location = "index.jsp?startrow=" + r + str;

        }

        function CheckAll(form) {
            for (var i = 0; i < form.elements.length; i++) {
                var e = form.elements[i];
                if (e.name != 'chkAll')
                    e.checked = form.chkAll.checked;
            }
        }

        function check(form, tflag) {
            var flag = false;
            for (var i = 0; i < form.elements.length; i++) {
                if (form.elements[i].checked) {
                    flag = true;
                }
            }
            if (!flag) {
                alert("请选择定单！");
                return false;
            } else {
                var val;
                if (tflag == 1) {
                    val = confirm("将选中定单状态改为发货？");
                    if (val) {
                        form.action = "index.jsp?updateflag=1";
                        form.submit();
                    }
                } else if (tflag == 2) {
                    val = confirm("将选中定单状态改为缺货？");
                    if (val) {
                        form.action = "index.jsp?updateflag=2";
                        form.submit();
                    }
                }
            }
        }

        //function delOrder(orderid) {
        //    var val;
        //    val = confirm("确定要删除订单吗？");
        //    if (val) {
        //        window.location = "deleteOrder.jsp?orderid=" + orderid;
        //    }
        //}

        function searchPayinfo(orderid,payway,startrow) {
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("searchInfoFromPaycenter.jsp?orderid=" + orderid + "&payway=" + payway + "&start=" + startrow, "", "width=" + iWidth +",height=" + iHeight +",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function dispOrderDetail(orderid) {
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open("orderDetail.jsp?orderid=" + orderid, "", "width=" + iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function exportOrders(){
            var iWidth=1200;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open('exportOrderToExcel.jsp', '', 'height=' + iHeight + ', width=' + iWidth + ', top=' + iTop + ', left=' + iLeft + ', toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=yes');
        }

        function balanceAccount(){
            var iWidth=1200;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open('balanceAccount.jsp', '', 'height=' + iHeight + ', width=' + iWidth + ', top=' + iTop + ', left=' + iLeft + ', toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=yes');
        }
    </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
        <%
        String[][] titlebars = {
                {"首页", ""},
                {"订单管理", ""}
        };


        String[][] operations = {
                //{"处理中", "index.jsp"}, {"", ""},
                //{"缺货", "lack.jsp"}, {"", ""},
                //{"已发货", "receive.jsp"}, {"", ""},
                //{"已完成", "end.jsp"}, {"", ""},
                //{"退货订单", "putback.jsp"}, {"", ""},
                //{"拒收订单", "refuse.jsp"}, {"", ""},
                //{"客户取消", "qorders.jsp"}, {"", ""},
        };
    %>
    <%@ include file="../inc/titlebar.jsp" %>
    <table width="100%" border="0" cellpadding="0">
        <tr>
            <td background="images/dot-line.gif"></td>
        </tr>
        <tr bgcolor="#d4d4d4" align="right">
            <td bgcolor="#DCE4E7">
                <form name="searchForm" method="post" action="index.jsp">
                    <input type="hidden" name="searchflag" value="1">
                    <input type="hidden" name="range" value=<%=range%>>
                    <table width="100%" border="0" cellpadding="3" cellspacing="1">
                        <tr>
                            <td colspan="12" class="txt"><font color="#59697B"><strong>查询处理</strong></font></td>
                            <td colspan="2" align="right" class="txt">
                                <input type=button value="查询" onclick="javascript:gotosearch();">
                                <input type=button value="导出" onclick="javascript:exportOrders();">
                                <!--input type=button value="对账" onclick="javascript:balanceAccount();"-->
                            </td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                            <td width="5%" align="right" class="txt">订单号：</td>
                            <td width="10%" align="left" class="txt">
                                <input type="text" name="orderid" size="15" value="<%=(orderid==0)?"":orderid%>">
                            </td>
                            <td width="6%" align="right" class="txt">姓名：</td>
                            <td width="5%" align="left" class="txt">
                                <input type="text" name="username" size="5" value="<%=(username==null)?"":username%>">
                            </td>
                            <td width="4%" align="right" class="txt">电话：</td>
                            <td width="8%" align="left" class="txt">
                                <input type="text" name="phone" size="11" value="<%=(pphone==null)?"":pphone%>">
                            </td>
                            <td width="6%" align="right" class="txt">订单状态：</td>
                            <td width="6%" align="left" class="txt">
                                <select name="status">
                                    <option value=8 <%=(status==8)?"selected":""%>>已付款</option>
                                    <option value=7 <%=(status==7)?"selected":""%>>等待付款</option>
                                    <option value=9 <%=(status==9)?"selected":""%>>超时取消</option>
                                    <option value="0" <%=(status==0)?"selected":""%>>所有订单</option>
                                    <!--option value=1>处理中</option>
                                    <option value=2>发货</option>
                                    <option value=3>退货</option>
                                    <option value=4>完成</option>
                                    <option value=5>拒收</option>
                                    <option value=6>缺货</option-->
                                </select>
                            </td>
                            <td width="6%" align="right" class="txt">支付日期</td>
                            <td width="15%" align="left" bgcolor="#FFFFFF" class="txt">
                                <input type="text" size="10" name="searchtime1" value="<%=(startday==null)?"":startday%>" onfocus="setday(this)" readonly>
                                到
                                <input type="text" size="10" name="searchtime2" value="<%=(endday==null)?"":endday%>" onfocus="setday(this)" readonly>
                            </td>
                            <td>

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

                        </tr>
                    </table>
                </form>
            </td>
        </tr>
    </table>
    <form action="index.jsp" method="post" name="chuku">
        <input type="hidden" name="updateflag" value="1">
        <table border="0" width="100%" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0">
                        <tr bgcolor="#F4F4F4" align="center">
                            <td class="moduleTitle"><font color="#48758C">处理中订单列表</font></td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="right">
                            <td>
                                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                                    <tr bgcolor="#FFFFFF">
                                        <td align="center" width="5%">用户ID</td>
                                        <td align="center" width="7%">订单号</td>
                                        <td align="center" width="7%">姓名</td>
                                        <td align="center" width="9%">地址</td>
                                        <td align="center" width="7%">邮编</td>
                                        <td align="center" width="5%">电话</td>
                                        <td align="center" width="5%">付款方式</td>
                                        <td align="center" width="5%">订单状态</td>
                                        <td align="center" width="7%">总费用</td>
                                        <td align="center" width="5%">报名项目</td>
                                        <!--td align="center" width="5%">报纸份数</td-->
                                        <td align="center" width="7%">报名方式</td>
                                        <!--td align="center" width="7%">起订日期</td-->
                                        <td align="center" width="9%">创建日期</td>
                                        <td align="center" width="10%">第三方支付查询</td>
                                    </tr>
                                    <%
                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        DecimalFormat df = new DecimalFormat();
                                        df.applyPattern("0.00");
                                        DecimalFormat df2 = new DecimalFormat();
                                        df2.applyPattern("0");
                                        String phone = "";
                                        String address = null;
                                        String zip = null;
                                        String subscribeType = null;

                                        for (int i = 0; i < currentlist.size(); i++) {
                                            order = (Order) currentlist.get(i);
                                            //List<Order> orderDetails = orderMgr.getDetail(order.getOrderid());
                                            AddressInfo addressInfo = orderMgr.getAAddresInfoForOrder(order.getOrderid());
                                            if (addressInfo!=null) {
                                                address = addressInfo.getAddress();
                                                zip = addressInfo.getZip();
                                            }
                                            String createdate = String.valueOf(order.getCreateDate());
                                            //createdate =createdate==null?"":String.valueOf(createdate).substring(0, 16);
                                            int payway = order.getPayWay();
                                            if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
                                                phone = order.getPhone();
                                                phone = StringUtil.gb2iso4View(phone);
                                            } else {
                                                phone = orderMgr.getPhone(order.getOrderid());
                                                phone = StringUtil.gb2iso4View(phone);
                                            }

                                            String province = order.getProvince()==null?"--":StringUtil.gb2iso4View(order.getProvince());
                                            String city = order.getCity()==null?"--":StringUtil.gb2iso4View(order.getCity());
                                            Fee fee = new Fee();
                                            fee = orderMgr.getAFeeInfo(order.getSendWay());


                                            String projname = order.getProjname();

                                    %>
                                    <tr bgcolor="#FFFFFF">
                                        <td align="center">
                                            <%=order.getName()%>
                                        </td>
                                        <td align="center">
                                            <a href="#" onclick="dispOrderDetail(<%=order.getOrderid()%>);"><%=order.getOrderid()%>
                                            </a>
                                        </td>
                                        <td align="center"><%=order.getName() == null ? "" : StringUtil.gb2iso4View(order.getName())%>
                                        </td>
                                        <!--td align="center"><!%=province%>&nbsp;&nbsp;<!%=city%></td-->
                                        <td align="center"><%=address == null ? "" : StringUtil.gb2iso4View(address)%>
                                        </td>
                                        <td align="center"><%=zip == null ? "" : StringUtil.gb2iso4View(zip)%>
                                        </td>
                                        <td align="center"><%=phone%>
                                        </td>
                                        <td align="center"><%if(order.getPayWay() == 0){%>微信支付<%}else{%><%=order.getPayWay()==1?"支付宝":((order.getPayWay()==2)?"银行支付":"货到付款")%><%}%>
                                        </td>
                                        <td align="center">
                                            <%if (order.getNouse() == 0) {%>
                                            <a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank><font color="red">客户取消</font></a>
                                            <%} else {
                                                if (order.getStatus() == 8) {%>
                                            <a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank><font color="green">已付款</font></a>
                                            <%} else if (order.getStatus() == 7){
                                                Calendar now_cal = Calendar.getInstance();
                                                now_cal.setTime(new Date(System.currentTimeMillis()));
                                                Calendar order_create_cal = Calendar.getInstance();
                                                order_create_cal.setTime(order.getCreateDate());
                                                order_create_cal.add(Calendar.DAY_OF_MONTH,1);      //订单创建时间增加1天
                                               /* if (now_cal.after(order_create_cal)) {
                                                    orderMgr.updateStatus(order.getOrderid(),9,username);
                                                    status7and9_brief = "用户超时未付款";
                                                } else
                                                    status7and9_brief = "等待用户付款";*/
                                            %>
                                            <a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank><font color="red">等待用户付款</font></a>
                                            <%}  else if (order.getStatus() == 9) {%>
                                            <a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank><font color="#FF0000">用户超时未付款</font></a>
                                            <%} else if (order.getStatus() == 4){%>
                                            <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">完成</font></a>
                                            <%} else {%>
                                            未知
                                            <%}}%>

                                            <!--%if (order.getStatus() == 1) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">处理中</font></a>
                                            < %} else if (order.getStatus() == 2) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">发货</font></a>
                                            < %} else if (order.getStatus() == 3) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">退货</font></a>
                                            < %} else if (order.getStatus() == 4) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">完成</font></a>
                                            < %} else if (order.getStatus() == 5) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">拒收</font></a>
                                            < %} else if (order.getStatus() == 6) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">缺货</font></a>
                                            < %} else if (order.getStatus() == 7) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="#FF0000">等待客户付款</font></a>
                                            < %} else if (order.getStatus() == 9) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="#FF0000">用户超时未付款</font></a>
                                            <  %} else if (order.getStatus() == 8) {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="green">已付款</font></a>
                                            < %} else {%>
                                                <a href="tostatus.jsp?orderid=< %=order.getOrderid()%>" target=_blank><font color="red">新订单</font></a>
                                            < %}
                                        }%-->
                                        </td>
                                        <td align="center"><%=df.format(order.getPayfee())%></td>
                                        <td align="center"><%=(projname!=null)?projname:""%></td>
                                      <%--  <td align="center"><%=dynum%></td>--%>
                                        <td align="center"><%=(subscribeType!=null)?subscribeType:""%></td>
                                       <%-- <td align="center"><%=(serviceStartDate!=null)?serviceStartDate:""%></td>--%>
                                        <td align="center"><%=order.getCreateDate().toString().substring(0,19)%></td>
                                        <!--td align="center"><a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank>订单状态</a></td-->
                                        <td align="center"><a href="#" onclick="javascript:searchPayinfo(<%=order.getOrderid()%>,<%=payway%>,<%=startrow%>)">第三方支付查询</a></td>
                                        <!--%if (order.getStatus() == 0) {%>
                                        <!--td align="center"><a href="#" onclick="delOrder(<!%=order.getOrderid()%>)">删除</a></td>
                                        <td align="center">&nbsp;</td>
                                        <!%} else {%>
                                        <td align="center">&nbsp;</td>
                                        <!%}%-->
                                    </tr>
                                    <%}%>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>订单总数：<%=rows%>    <%--订阅报纸的总份数：<%=total_dynum%>--%></td>
            </tr>
        </table>
        <%--<input type="submit" value="发货" onclick="javascript:return check(this.form);">--%>
        <!--input type="button" value="发货" onclick="javascript:return check(this.form,1);" name="up1">
        <input type="button" value="缺货" onclick="javascript:return check(this.form,2);" name="up2"-->
        <table>
            <tr valign="bottom">
                <td>
                    总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td>
                    <%
                        if (searchflag == 0) {
                            if ((startrow - range) >= 0) {
                    %>
                    [<a href="index.jsp?startrow=0">第一页</a>][<a href="index.jsp?startrow=<%=startrow-range%>">上一页</a>]
                    <%
                        }
                        if ((startrow + range) < rows) {
                    %>
                    [<a href="index.jsp?startrow=<%=startrow+range%>">下一页</a>][<a href="index.jsp?startrow=<%=(totalpages-1)*range%>">最后页</a>]
                    <%
                        }

                        if (totalpages > 1) {
                    %>
                    &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                    <a href="#" onclick="golist((document.all('jump').value-1) * <%=range%>);">GO
                    </a>
                    <%
                        }

                    } else if (searchflag == 1) {
                        if ((startrow - range) >= 0) {
                    %>
                    [<a href="index.jsp?startrow=<%=range%><%=jumpstr%>">第一页</a>][<a href="index.jsp?startrow=<%=startrow-range%><%=jumpstr%>">上一页</a>]
                    <%}%>
                    <%
                        if ((startrow + range) < rows) {
                    %>
                    [<a href="index.jsp?startrow=<%=startrow+range%><%=jumpstr%>">下一页</a>][<a href="index.jsp?startrow=<%=(totalpages-1)*range%><%=jumpstr%>">最后页</a>]
                    <%
                        }
                        if (totalpages > 1) {
                    %>
                    &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                    <a href="#" onclick="jumppage((document.all('jump').value-1)*<%=range%>,'<%=jumpstr%>');">GO
                    </a>
                    <%
                            }
                        }
                    %>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>