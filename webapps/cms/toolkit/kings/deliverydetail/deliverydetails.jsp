<%@page import="com.bizwink.cms.kings.deliverydetail.DeliveryDetail,
                com.bizwink.cms.kings.deliverydetail.DeliveryDetailPeer,
                com.bizwink.cms.kings.deliverydetail.IDeliveryDetailManager,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    if (startrow < 0) {
        startrow = 0;
    }

    IDeliveryDetailManager delMgr = DeliveryDetailPeer.getInstance();
    List list = new ArrayList();
    List currentlist = new ArrayList();

    currentlist = delMgr.getCurrentDelivertyDetailList(siteid, startrow, range);

    int rows;
    int totalpages = 0;
    int currentpage = 0;

    rows = delMgr.getAllDeliveryDetailNum(siteid);

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
%>
<HTML>
<HEAD><TITLE>出货单明细表</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function DelDeliverys(deliveryid)
        {
            var bln = confirm("真的要删除吗？");
            if (bln)
            {
                window.location = "delete.jsp?deliveryid=" + deliveryid;
            }
        }

        function searchcheck() {
            if ((form1.querystr.value == null) || (form1.querystr.value == "")) {
                alert("请输入要查询的内容！");
                return false;
            }
            form1.submit();
            return true;
        }
    </SCRIPT>
</HEAD>
<BODY>
<P align=right><FONT color=#0000ff><a href="../supplier/index.jsp">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <A href="add.jsp">添加新出货单明细</A></FONT> <BR>

<FORM name=form1 action=query.jsp method=post>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD align=middle width="5%">编号</TD>
            <TD align=middle width="15%">出货单单号</TD>
            <TD align=middle width="8%">商品编号</TD>
            <TD align=middle width="10%">数量</TD>
            <TD align=middle width="9%">单价</TD>
            <TD align=middle width="9%">金额</TD>
            <TD align=middle width="5%">修改</TD>
            <TD align=middle width="5%">删除</TD>
        </TR>
        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    int id = 0;
                    String deliveryid;
                    String productid;
                    String salesquantity;
                    String salesunitprice;
                    String salesamount;
                    DeliveryDetail dd = (DeliveryDetail) currentlist.get(i);
                    deliveryid = dd.getDeliveryID();
                    productid = Integer.toString(dd.getProductID());
                    salesquantity = Integer.toString(dd.getSalesQuantity());
                    salesunitprice = Integer.toString(dd.getSalesUnitPrice());
                    salesamount = Integer.toString(dd.getSalesAmount());
        %>
        <TR height=35>
            <TD align=middle width="3%"><%=i + 1%>
            </TD>
            <TD align=middle width="15%"><%=deliveryid == null ? "--" : deliveryid%>
            </TD>
            <TD align=middle width="8%"><%=productid == null ? "--" : productid%>
            </TD>
            <TD align=middle width="10%"><%=salesquantity == null ? "--" : salesquantity%>
            </TD>
            <TD align=middle width="20%"><%=salesunitprice == null ? "--" : salesunitprice%>
            </TD>
            <TD align=middle width="20%"><%=salesamount == null ? "--" : salesamount%>
            </TD>
            <TD align=middle width="5%"><A
                    href="edit.jsp?deliveryid=<%=deliveryid%>"><IMG
                    src="../images/dx.gif" border=0></A></TD>
            <TD align=middle width="5%">
                <A href="deliverydetails.jsp#" onclick="javascript:return DelDeliverys('<%=deliveryid%>');">
                    <IMG src="../images/del.gif" border=0></A></TD>
        </TR>
        <%
                }
            }
        %>
        </TBODY>
    </TABLE>
    <BR>

    <p align=center>
    <TABLE>
        <TBODY>
        <TR>
            <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=rows%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                <%
                    if ((startrow - range) >= 0) {
                %>
                <a href="deliverydetails.jsp?startrow=0">第一页</a>
                <%}%>
                <%if ((startrow - range) >= 0) {%>
                <a href="deliverydetails.jsp?startrow=<%=startrow-range%>">上一页</a>
                <%}%>
                <%if ((startrow + range) < rows) {%>
                <A href="deliverydetails.jsp?startrow=<%=startrow+range%>">下一页</A>
                <%}%>
                <%if (currentpage != totalpages) {%>
                <A href="deliverydetails.jsp?startrow=<%=(totalpages-1)*range%>">最后一页</A>
                <%}%>
            </TD>
            <TD>&nbsp;</TD>
        </TR>
        </TBODY>
    </TABLE>
    <BR><BR>

    <p align=right>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD>&nbsp;&nbsp;检索：<select name="seltype">
                <option value="1">出货单单号</option>
                <option value="2">商品编号</option>
            </select>
                <input type="text" size=40 name="querystr">&nbsp;&nbsp;<input type="button" value=" 查 询 "
                                                                              onclick="javascript:return searchcheck();">
            </TD>
        </tr>
        </tbody>
    </table>

</FORM>
</BODY>
</HTML>
