<%@ page import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.server.FileProps" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", 0);
    int updateflag = ParamUtil.getIntParameter(request, "updateflag", 0);
    String userid = "";
    int siteid = authToken.getSiteID();

    String jumpstr = "";

    IOrderManager orderMgr = orderPeer.getInstance();


    Order order = new Order();
    List list = new ArrayList();
    List currentlist = new ArrayList();
    int currentrows = 0;
    int totalrows = 0;
    int row = 0;
    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    if (searchflag == 0) {
        String sql = "select o.* from en_orders o where o.siteid = " + siteid + " and o.status = 0 order by o.createdate desc";
        list = orderMgr.getOrderList(startrow, 0, 0, sql);
        currentlist = orderMgr.getOrderList(startrow, range, 0, sql);
        row = currentlist.size();
        rows = list.size();
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

    int orderid = 0;
    String searchtime1 = "";
    String searchtime2 = "";
    String porductname = "";
    String username = "";
    int sendway = 0;
    int orderflag = 0;
    int status = 0;

    if (searchflag == 1) {
        orderid = ParamUtil.getIntParameter(request, "orderid", 0);
        searchtime1 = ParamUtil.getParameter(request, "searchtime1");
        searchtime2 = ParamUtil.getParameter(request, "searchtime2");
        porductname = ParamUtil.getParameter(request, "detailedname");
        username = ParamUtil.getParameter(request, "username");
        status = 0;
        sendway = ParamUtil.getIntParameter(request, "sendway", -1);
        orderflag = ParamUtil.getIntParameter(request, "orderflag", -1);
    }

    if (searchflag != 0) {
        jumpstr = "&searchflag=1";

        String sqlstr = "";
        if ((searchtime2 != "") && (searchtime2 != null)) searchtime2 = searchtime2 + " 23:59:59";
        if ((searchtime1 != "") && (searchtime1 != null)) searchtime1 = searchtime1 + " 00:00:00";

        sqlstr = "select o.* from en_orders o where o.siteid=" + siteid + " and o.status=0 and 1 = 1";

        if (orderid != 0) {
            if (sqlstr.endsWith("1 = 1")) {
                sqlstr = sqlstr + " and o.orderid=" + orderid;
            } else {
                sqlstr = sqlstr + " or o.orderid=" + orderid;
            }
            jumpstr = jumpstr + "&orderid=" + orderid;
        }

        FileProps fp = new FileProps("com/bizwink/cms/server/config.properties");
        String dbstr = fp.getProperty("main.db.type");
        jumpstr = "&searchflag=1";
        if ((searchtime1 != "") && (searchtime1 != null)) {
            if (dbstr.equals("oracle")) {
                if (sqlstr.endsWith("1 = 1")) {
                    sqlstr = sqlstr + " and o.createdate >= TO_DATE('" + searchtime1 + "', 'YYYY-MM-DD HH24:MI:SS')";
                } else {
                    sqlstr = sqlstr + " and o.createdate >= TO_DATE('" + searchtime1 + "', 'YYYY-MM-DD HH24:MI:SS')";
                }
            } else {
                if (sqlstr.endsWith("1 = 1")) {
                    sqlstr = sqlstr + " and o.createdate >= '" + searchtime1 + "'";
                } else {
                    sqlstr = sqlstr + " and o.createdate >= '" + searchtime1 + "'";
                }
            }

            jumpstr = jumpstr + "&searchtime1=" + searchtime1.substring(0, 10);
        }

        if ((searchtime2 != "") && (searchtime2 != null)) {
            if (dbstr.equals("oracle")) {
                if (sqlstr.endsWith("1 = 1")) {
                    sqlstr = sqlstr + " and o.createdate <= TO_DATE('" + searchtime2 + "', 'YYYY-MM-DD HH24:MI:SS')";
                } else {
                    sqlstr = sqlstr + " and o.createdate <= TO_DATE('" + searchtime2 + "', 'YYYY-MM-DD HH24:MI:SS')";
                }
            } else {
                if (sqlstr.endsWith("1 = 1")) {
                    sqlstr = sqlstr + " and o.createdate <= '" + searchtime2 + "'";
                } else {
                    sqlstr = sqlstr + " and o.createdate <= '" + searchtime2 + "'";
                }
            }
            jumpstr = jumpstr + "&searchtime2=" + searchtime2.substring(0, 10);
        }


        if (sendway != -1) {
            sqlstr = sqlstr + " and o.sendway = " + sendway;
            jumpstr = jumpstr + "&sendway=" + sendway;
        }
        if (orderflag != -1) {
            sqlstr = sqlstr + " and o.orderflag = " + orderflag;
            jumpstr = jumpstr + "&orderflag=" + orderflag;
        }
        if ((username != "") && (username != null)) {
            sqlstr = sqlstr + " and o.name like '@" + username + "@'";
            jumpstr = jumpstr + "&username=" + username;
        }


        sqlstr = sqlstr + " and orderid in (select distinct orderid from en_orders_detail";

        if ((porductname != "") && (porductname != null)) {
            sqlstr = sqlstr + " where detailedname like '@" + porductname + "@')";
            jumpstr = jumpstr + "&detailedname=" + porductname;
        } else {
            sqlstr = sqlstr + ")";
        }

        sqlstr = sqlstr + " order by o.createdate desc";
        sqlstr = sqlstr.replaceAll("@", "%");
        list = orderMgr.getOrderList(startrow, 0, status, sqlstr);
        currentlist = orderMgr.getOrderList(startrow, range, status, sqlstr);
        row = currentlist.size();
        rows = list.size();
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
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language="JavaScript" src="../include/setday.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script language="javascript">
        function golist(r) {
            window.location = "nouse.jsp?startrow=" + r;

        }

        function cancelorder(id, userid) {
            var val;
            val = confirm("你确定要作取消份订单吗？");
            if (val) {
                window.location = "cancelorder.jsp?orderid=" + id + "&userid=" + userid + "&flag=1";
            }
        }

        function gotosearch() {
            searchForm.action = "nouse.jsp";
            searchForm.submit();
        }

        function jumppage(r, str) {
            window.location = "nouse.jsp?startrow=" + r + str;

        }
        function CheckAll(form) {
            for (var i = 0; i < form.elements.length; i++) {
                var e = form.elements[i];
                if (e.name != 'chkAll')
                    e.checked = form.chkAll.checked;
            }
        }

        function check(form) {
            var flag = false;
            for (var i = 0; i < form.elements.length; i++) {
                if (form.elements[i].checked) {
                    flag = true;
                }
            }
            if (!flag) {
                alert("请选择已审核的定单！");
                return false;
            } else {
                var val;
                val = confirm("将选中定单状态改为已发货？");
                if (val)
                    return true;
                else
                    return false;
            }
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
            {"未审核", "index.jsp"}, {"", ""},
            {"已审核", "receive.jsp"}, {"", ""},
            {"已付款", "pay.jsp"}, {"", ""},
            {"已发货", "end.jsp"}, {"", ""},
            {"客户作废", "qorders.jsp"}, {"", ""},
            {"退货订单", "putback.jsp"}, {"", ""},
            {"员工作废", "nouse.jsp"}, {"", ""},
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="nouse.jsp" method="post" name="chuku">
<input type="hidden" name="updateflag" value="1">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
    <tr>
        <td>
            <table width="100%" border="0" cellpadding="0">
                <tr bgcolor="#F4F4F4" align="center">
                    <td class="moduleTitle"><font color="#48758C">员工作废订单列表</font></td>
                </tr>
                <tr bgcolor="#d4d4d4" align="right">
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="1">
                            <tr bgcolor="#FFFFFF">
                                <td align="center">订单号</td>
                                <td align="center">订单类型</td>
                                <td align="center">用户名 </td>
                                <td align="center">收件人姓名</td>
                                <td align="center">付款方式</td>
                                <td align="center">地区</td>
                                <td align="center">取货方式</td>
                                <td align="center">总费用(含邮费)</td>
                                <td align="center">创建日期</td>
                                <td align="center">备注</td>
                                <td>&nbsp;</td>
                            </tr>
                            <%
                                DecimalFormat df = new DecimalFormat();
                                df.applyPattern("0.00");
                                DecimalFormat df2 = new DecimalFormat();
                                df2.applyPattern("0");
                                String phone = "";
                                for (int i = 0; i < currentlist.size(); i++) {
                                    order = (Order) currentlist.get(i);
                                    String createdate = String.valueOf(order.getCreateDate());
                                    createdate = createdate == null ? "" : createdate.substring(0, 16);
                                    //userid = order.getUserid();
                                    if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
                                        phone = order.getPhone();
                                        phone = StringUtil.gb2iso4View(phone);
                                    } else {
                                        phone = orderMgr.getPhone(order.getOrderid());
                                        phone = StringUtil.gb2iso4View(phone);
                                    }
                                    username = order.getUserName() == null ? "--" : StringUtil.gb2iso4View(order.getUserName());
                            %>
                            <tr bgcolor="#FFFFFF">
                                <td align="center">
                                    <a href="chuku1.jsp?orderid=<%=order.getOrderid()%>"
                                       target=_blank><%=order.getOrderid()%></a>
                                </td>
                                <td align="center">
                                    <%if (order.getOrderFlag() == 0) {%>普通订单<%} else {%><font color="red">
                                    竞标订单</font><%}%>
                                </td>
                                <td align="center">
                                    <a href="userorders.jsp?searchflag=1&userid=<%=userid%>&showname=<%=username%>"
                                       target=_blank>
                                        <%=username%>
                                    </a>
                                </td>
                                <td align="center"><%=order.getName() == null ? "" : StringUtil.gb2iso4View(order.getName())%></td>
                                <td align="center"><%=orderMgr.getPayWay(order.getPayWay())%></td>
                                <td align="center"><%=StringUtil.gb2iso4View(orderMgr.getProvince(order.getProvince()))%></td>
                                <td align="center"><%=orderMgr.getSendWay(order.getSendWay())%></td>
                                <td align="center"><%=df.format(order.getTotalfee())%></td>
                                <td align="center"><%=createdate%></td>
                                <td align="center"><%=order.getNotes() == null ? "" : StringUtil.gb2iso4View(order.getNotes())%></td>
                                <td align="center"><a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank>订单状态</a>
                                </td>
                            </tr>
                            <%}%>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
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
            [<a href="nouse.jsp?startrow=<%=startrow-range%>">上一页</a>]
            <%
                }
                if ((startrow + range) < rows) {
            %>
            [<a href="nouse.jsp?startrow=<%=startrow+range%>">下一页</a>]
            <%
                }

                if (totalpages > 1) {
            %>
            &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
            <a href="#" onclick="golist((document.all('jump').value-1) * <%=range%>);">GO</a>
            <%
                }

            } else if (searchflag != 0) {
                if ((startrow - range) >= 0) {
            %>
            [<a href="nouse.jsp?startrow=<%=startrow-range%><%=jumpstr%>">上一页</a>]
            <%}%>
            <%
                if ((startrow + range) < rows) {
            %>
            [<a href="nouse.jsp?startrow=<%=startrow+range%><%=jumpstr%>">下一页</a>]
            <%
                }
                if (totalpages > 1) {
            %>
            &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
            <a href="#" onclick="jumppage((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
            <%
                    }
                }
            %>
        </td>
    </tr>
</table>
</center>
</form>

<center>
    <table width="90%" border="0" cellpadding="0">
        <tr>
            <td background="images/dot-line.gif"></td>
        </tr>
        <tr bgcolor="#d4d4d4" align="right">
            <td bgcolor="#DCE4E7">
                <form name="searchForm" method="post" action="nouse.jsp">
                    <input type="hidden" name="searchflag" value="1">
                    <input type="hidden" name="range" value=<%=range%>>
                    <table width="100%" border="0" cellpadding="2">
                        <tr>
                            <td class="txt"><font color="#59697B"><strong>查询订单</strong></font></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellpadding="3" cellspacing="1">
                        <tr bgcolor="#FFFFFF">
                            <td width="20%" valign="bottom" class="txt">订单号：</td>
                            <td width="30%" valign="top" class="txt">
                                <input type="text" name="orderid" size="15">
                                <font color="#FF0000">&nbsp; </font></td>
                            <td class="txt">&nbsp;</td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt">日期</td>
                            <td colspan="2" bgcolor="#FFFFFF" class="txt"> 从(开始日期)
                                <input type="text" size="10" name="searchtime1" onfocus="setday(this)" readonly>
                                到(结束日期)
                                <input type="text" size="10" name="searchtime2" onfocus="setday(this)" readonly>
                            </td>
                        </tr>
                        <!--<tr bgcolor="#FFFFFF">-->
                        <!--<td valign="bottom" class="txt">产品名称(未完成)：</td>-->
                        <!--<td colspan="2" class="txt">-->
                        <!--<input type="text" name="porductname" size="40">-->
                        <!--</td>-->
                        <!--</tr>-->
                        <tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt">收货人姓名：</td>
                            <td colspan="2" class="txt">
                                <input type="text" name="username" size="20">
                            </td>
                        </tr>
                        <!--<tr bgcolor="#FFFFFF">-->
                        <!--<td valign="bottom" class="txt">订单类型：</td>-->
                        <!--<td colspan="2" class="txt">-->
                        <!--<select name="orderflag">-->
                        <!--<option value="-1">请选择</option>-->
                        <!--<option value="0">普通订单</option>-->
                        <!--<option value="1">竞标订单</option>-->
                        <!--</select>-->
                        <!--</td>-->
                        <!--</tr>-->
                        <!--<tr bgcolor="#FFFFFF">-->
                        <!--<td valign="bottom" class="txt">取货方式(未完成)：</td>-->
                        <!--<td colspan="2" class="txt">-->
                        <!--<select name="sendway">-->
                        <!--<option value="-1">请选择</option>-->
                        <!--<option value="0">普通邮寄</option>-->
                        <!--<option value="1">EMS</option>-->
                        <!--<option value="2">用户自取</option>-->
                        <!--</select>-->
                        <!--</td>-->
                        <!--</tr>-->
                        <tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt"></td>
                            <td colspan="2" class="txt">
                                <input type="hidden" name="status" value="0">
                            </td>
                        </tr>
                        <tr><td colspan=3 align="center" valign="center"><input type=button value="查询"
                                                                                onclick="javascript:gotosearch();"></td>
                        </tr>
                    </table>
                </form>
            </td></tr></table>
</center>
</center>
</body>
</html>