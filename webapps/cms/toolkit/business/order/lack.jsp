<%@ page import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.business.Order.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.server.FileProps"%>
<%@ include file="../../../include/auth.jsp"%>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", 0);
    int updateflag = ParamUtil.getIntParameter(request, "updateflag", 0);
    int userid = 0;
    int scores = 0;
    int siteid = authToken.getSiteID();

    String jumpstr = "";

    IOrderManager orderMgr = orderPeer.getInstance();

    if (updateflag == 1) {
        String[] ordergo = request.getParameterValues("ordergo");
        long updateid = 0;
        for (int i = 0; i < ordergo.length; i++) {
            updateid = Long.parseLong(ordergo[i]);
            orderMgr.updateStatus(updateid, 1, authToken.getUserID());

            //订单状态改为发货后，将收款信息自动存入收款表（默认为收到款后发货）
            //orderMgr.addReceiveMoney(updateid, authToken.getUserID());
            //更新用户积分
            /*Order a_ord = new Order();
            a_ord = orderMgr.getAOrder(updateid);
            userid = a_ord.getUserid();
            Scores sco = new Scores();
            IBUserManager bMgr = buserPeer.getInstance();
            sco = bMgr.getOrderScores(updateid);
            scores = sco.getOrderscores();
            bMgr.updateScores(userid, scores);*/
        }
        response.sendRedirect("lack.jsp");
    }
    if (updateflag == 2) {
        String[] ordergo = request.getParameterValues("ordergo");
        long updateid = 0;
        for (int i = 0; i < ordergo.length; i++) {
            updateid = Long.parseLong(ordergo[i]);
            orderMgr.updateStatus(updateid, 2, authToken.getUserID());

            //订单状态改为发货后，将收款信息自动存入收款表（默认为收到款后发货）
            //orderMgr.addReceiveMoney(updateid, authToken.getUserID());
        }
        response.sendRedirect("lack.jsp");
    }

    Order order = new Order();
    List list = new ArrayList();
    List currentlist = new ArrayList();
    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    if (searchflag == 0) {
        String sql = "select o.* from tbl_orders o where o.siteid = " + siteid + " and o.status=6 and nouse = 1 order by o.createdate desc";
        list = orderMgr.getOrderList(startrow, 0, 6, sql);
        currentlist = orderMgr.getOrderList(startrow, range, 6, sql);
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

    long orderid = 0;
    String searchtime1 = "";
    String searchtime2 = "";
    String porductname = "";
    String username = "";
    int sendway = 0;
    int orderflag = 0;
    int status = -1;

    if (searchflag == 1) {
        orderid = ParamUtil.getLongParameter(request, "orderid", 0);
        searchtime1 = ParamUtil.getParameter(request, "searchtime1");
        searchtime2 = ParamUtil.getParameter(request, "searchtime2");
        porductname = ParamUtil.getParameter(request, "detailedname");
        username = ParamUtil.getParameter(request, "username");
        status = 6;
        sendway = ParamUtil.getIntParameter(request, "sendway", -1);
        orderflag = ParamUtil.getIntParameter(request, "orderflag", -1);
    }

    if (searchflag != 0) {
        jumpstr = "&searchflag=1";

        String sqlstr = "";
        if ((searchtime2 != "") && (searchtime2 != null)) searchtime2 = searchtime2 + " 23:59:59";
        if ((searchtime1 != "") && (searchtime1 != null)) searchtime1 = searchtime1 + " 00:00:00";

        sqlstr = "select o.* from tbl_orders o where o.status = 6 and o.siteid = " + siteid+" and 1 = 1";

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


        sqlstr = sqlstr + " and orderid in (select distinct orderid from tbl_orders_detail";

        if ((porductname != "") && (porductname != null)) {
            sqlstr = sqlstr + " where detailedname like '@" + porductname + "@')";
            jumpstr = jumpstr + "&detailedname=" + porductname;
        } else {
            sqlstr = sqlstr + ")";
        }

        sqlstr = sqlstr + " order by o.createdate desc";
        sqlstr = sqlstr.replaceAll("@", "%");
        list = orderMgr.getOrderList(startrow, 0, 6, sqlstr);
        currentlist = orderMgr.getOrderList(startrow, range, 6, sqlstr);
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
            window.location = "lack.jsp?startrow=" + r;

        }

        function cancelorder(id, userid) {
            var val;
            val = confirm("你确定要作废这份订单吗？");
            if (val) {
                window.location = "cancelorder.jsp?orderid=" + id + "&userid=" + userid + "&flag=1";
            }
        }

        function gotosearch() {
            searchForm.action = "lack.jsp";
            searchForm.submit();
        }

        function jumppage(r, str) {
            window.location = "lack.jsp?startrow=" + r + str;

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
                    val = confirm("将选中定单状态改为处理中？");
                    if (val) {
                        form.action = "lack.jsp?updateflag=1";
                        form.submit();
                    }
                } else if (tflag == 2) {
                    val = confirm("将选中定单状态改为发货？");
                    if (val) {
                        form.action = "lack.jsp?updateflag=2";
                        form.submit();
                    }
                }
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
            {"处理中", "index.jsp"}, {"", ""},
            {"缺货", "lack.jsp"}, {"", ""},
            {"已发货", "receive.jsp"}, {"", ""},
            {"已完成", "end.jsp"}, {"", ""},
            {"退货订单", "putback.jsp"}, {"", ""},
            {"拒收订单", "refuse.jsp"}, {"", ""},
            {"客户取消", "qorders.jsp"}, {"", ""},
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="" method="post" name="chuku">
<center>
<table border="0" cellspacing="0" cellpadding="4" bgcolor="#FFFFFF">
<tr>
<td>
<table width="100%" border="0" cellpadding="0">
<tr bgcolor="#F4F4F4" align="center">
    <td class="moduleTitle"><font color="#48758C">缺货订单列表</font></td>
</tr>
<tr bgcolor="#d4d4d4" align="right">
<td>
<table width="100%" border="0" cellpadding="2" cellspacing="1">
    <tr bgcolor="#FFFFFF">
        <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td align="center">订单号</td>
        <td align="center">收件人姓名</td>
        <td align="center">收件人地址</td>
        <td align="center">收件人邮编</td>
        <td align="center">收件人电话</td>
        <td align="center">付款方式</td>
        <td align="center">取货方式</td>
        <td align="center">订单状态</td>
        <td align="center">总费用(含邮费)</td>
        <td align="center">邮寄费用</td>
        <td align="center">使用积分</td>
        <td align="center">创建日期</td>
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
            //createdate = createdate == null ? "" : createdate.substring(0, 16);
            userid = order.getUserid();
            if ((order.getPhone() != null) && (!"".equals(order.getPhone()))) {
                phone = order.getPhone();
                phone = StringUtil.gb2iso4View(phone);
            } else {
                phone = orderMgr.getPhone(orderid);
                phone = StringUtil.gb2iso4View(phone);
            }
            Fee fee = new Fee();
        fee = orderMgr.getAFeeInfo(order.getSendWay());
        SendWay payway = orderMgr.getASendWayInfo(order.getPayWay());
    %>
    <tr bgcolor="#FFFFFF">
        <td align="center">
            <input type="checkbox" name="ordergo" value="<%=order.getOrderid()%>">
        </td>
        <td align="center">
            <a href="chuku1.jsp?orderid=<%=order.getOrderid()%>" target=_blank><%=order.getOrderid()%></a>
        </td>
        <!--<td align="center">-->
        <%--<%if (order.getOrderFlag() == 0) {%>普通订单<%} else {%><font color="red">竞标订单</font><%}%>--%>
        <!--</td>-->
        <!--<td align="center">-->
        <%--<a href="userorders.jsp?searchflag=1&userid=<%=userid%>&showname=<%=username%>" target=_blank>--%>
        <%--<%=username%>--%>
        <!--</a>-->
        <!--</td>-->
        <td align="center"><%=order.getName() == null ? "" : StringUtil.gb2iso4View(order.getName())%></td>
        <td align="center"><%=order.getAddress() == null ? "" : StringUtil.gb2iso4View(order.getAddress())%></td>
        <td align="center"><%=order.getPostcode() == null ? "" : StringUtil.gb2iso4View(order.getPostcode())%></td>
        <td align="center"><%=phone%></td>
        <td align="center"><%if (fee == null) {%>同城送货<%}else{%><%=fee.getCname()==null?"--":StringUtil.gb2iso4View(fee.getCname())%>
        <%}%></td>
        <td align="center">
            <font color="red">
                <%if (order.getNouse() == 0) {%>客户取消<%
            } else {
                if (order.getStatus() == 1) {
            %>处理中<%
            } else {
                if (order.getStatus() == 2) {
            %>发货<%
            } else {
                if (order.getStatus() == 3) {
            %>退货<%
            } else {
                if (order.getStatus() == 4) {
            %>完成<%
            } else {
                if (order.getStatus() == 5) {
            %>拒收<%
            } else {
                if (order.getStatus() == 6) {
            %>缺货<%
            } else {
                if (order.getStatus() == 7) {
            %><font color="#FF0000">缺货</font><%
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            %></font></td>
        <td align="center"><%=df.format(order.getPayfee())%></td>
        <td align="center"><%=df2.format(order.getDeliveryfee())%></td>
        <td align="center"><%=order.getUserscores()%></td>
        <td align="center"><%=createdate%></td>
        <td align="center"><a href="tostatus.jsp?orderid=<%=order.getOrderid()%>" target=_blank>订单状态</a></td>
    </tr>
    <%}%>
</table>
</td>
</tr>
</table>
</td>
</tr>
<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="chkAll" value="on"
                                       onclick="javascript:CheckAll(this.form);">全部选中</td></tr>
</table>
<input type="button" value="处理中" onclick="javascript:return check(this.form,1);" name="up1">   <input type="button"
                                                                                                      value="发货"
                                                                                                      onclick="javascript:return check(this.form,2);"
                                                                                                      name="up2">
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
            [<a href="lack.jsp?startrow=<%=startrow-range%>">上一页</a>]
            <%
                }
                if ((startrow + range) < rows) {
            %>
            [<a href="lack.jsp?startrow=<%=startrow+range%>">下一页</a>]
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
            [<a href="lack.jsp?startrow=<%=startrow-range%><%=jumpstr%>">上一页</a>]
            <%}%>
            <%
                if ((startrow + range) < rows) {
            %>
            [<a href="lack.jsp?startrow=<%=startrow+range%><%=jumpstr%>">下一页</a>]
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
                <form name="searchForm" method="post" action="lack.jsp">
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
                        <!--<tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt">产品名称(未完成)：</td>
                            <td colspan="2" class="txt">
                                <input type="text" name="porductname" size="40">
                            </td>
                        </tr>-->
                        <tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt">收货人姓名：</td>
                            <td colspan="2" class="txt">
                                <input type="text" name="username" size="20">
                            </td>
                        </tr>
                        <!--<tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt">订单类型：</td>
                            <td colspan="2" class="txt">
                                <select name="orderflag">
                                    <option value="-1">请选择</option>
                                    <option value="0">普通订单</option>
                                    <option value="1">竞标订单</option>
                                </select>
                            </td>
                        </tr>-->
                        <!--<tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt">取货方式(未完成)：</td>
                            <td colspan="2" class="txt">
                                <select name="sendway">
                                    <option value="-1">请选择</option>
                                    <option value="0">普通邮寄</option>
                                    <option value="1">EMS</option>
                                    <option value="2">用户自取</option>
                                </select>
                            </td>
                        </tr>-->
                        <tr bgcolor="#FFFFFF">
                            <td valign="bottom" class="txt"></td>
                            <td colspan="2" class="txt">
                                <input type="hidden" name="status" value="2">
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