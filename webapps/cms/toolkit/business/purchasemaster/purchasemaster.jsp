<%@page import="com.bizwink.cms.kings.purchasemaster.IPurchaseMasterManager,
                com.bizwink.cms.kings.purchasemaster.PurchaseMaster,
                com.bizwink.cms.kings.purchasemaster.PurchaseMasterPeer,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    //System.out.println("==========="+siteid);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    if (startrow < 0) {
        startrow = 0;
    }

    IPurchaseMasterManager pmMgr = PurchaseMasterPeer.getInstance();
    List list = new ArrayList();
    List currentlist = new ArrayList();

    //list = pmMgr.getAllProduct();
    currentlist = pmMgr.getCurrentProductList(siteid, startrow, range);

    //int row = 0;
    int rows;
    int totalpages = 0;
    int currentpage = 0;

    //row = currentlist.size();
    rows = pmMgr.getAllPurchaseMasterNum(siteid);

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
<HEAD><TITLE>进货单</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function DelSupplier(id,purchaseid)
        {
            var bln = confirm("真的要删除吗？");
            if (bln)
            {
                window.location = "delete.jsp?id=" + id +"&purchaseid=" + purchaseid;
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
<P align=right><FONT color=#0000ff><a href="#" onclick=javascript:history.go(-1);>返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <A href="add.jsp">添加新货单</A></FONT> <BR>

<FORM name=form1 action=query.jsp method=post>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD align=middle width="5%">编号</TD>
            <TD align=middle width="15%">进货单单号</TD>
            <TD align=middle width="8%">进货日期</TD>
            <TD align=middle width="10%">供应商编号</TD>
            <TD align=middle width="20%">发票号码</TD>
            <TD align=middle width="9%">总计金额</TD>
            <TD align=middle width="5%">浏览</TD>
            <TD align=middle width="5%">修改</TD>
            <TD align=middle width="5%">删除</TD>
        </TR>
        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    //for(int i=0;i<list.size();i++){
                    int id = 0;
                    Timestamp purchasedate;
                    String supplierid;
                    String invoiceno;
                    String amount;
                    String purchaseid;
                    PurchaseMaster pm = (PurchaseMaster) currentlist.get(i);
                    id = pm.getId();
                    purchaseid = pm.getPurchaseID();
                    purchasedate = pm.getPurchaseDate();
                    supplierid = pm.getSupplierID();
                    invoiceno = Integer.toString(pm.getInvoiceNo());
                    amount = Integer.toString(pm.getAmount());
        %>
        <TR height=35>
            <TD align=middle width="3%"><%=i + 1%>
            </TD>
            <TD align=middle width="15%"><%=purchaseid == null?"--":purchaseid%>
            </TD>
            <TD align=middle width="8%"><%=purchasedate.toString().substring(0, 10)%>
            </TD>
            <TD align=middle width="10%"><%=supplierid == null ? "--" : supplierid%>
            </TD>
            <TD align=middle width="20%"><%=invoiceno == null ? "--" : invoiceno%>
            </TD>
            <TD align=middle width="9%"><%=amount == null ? "--" : amount%>
            </TD>
            <TD align=middle width="5%"><A
                    href="scan.jsp?id=<%=purchaseid%>"><IMG
                    src="../images/preview.gif" border=0></A></TD>
            <TD align=middle width="5%"><A
                    href="edit.jsp?id=<%=purchaseid%>"><IMG
                    src="../images/dx.gif" border=0></A></TD>
            <TD align=middle width="5%">
                <A href="purchasemaster.jsp#" onclick="javascript:return DelSupplier(<%=purchaseid%>,'<%=purchaseid%>');">
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
                <a href="purchasemaster.jsp?startrow=0">第一页</a>
                <%}%>
                <%if ((startrow - range) >= 0) {%>
                <a href="purchasemaster.jsp?startrow=<%=startrow-range%>">上一页</a>
                <%}%>
                <%if ((startrow + range) < rows) {%>
                <A href="purchasemaster.jsp?startrow=<%=startrow+range%>">下一页</A>
                <%}%>
                <%if (currentpage != totalpages) {%>
                <A href="purchasemaster.jsp?startrow=<%=(totalpages-1)*range%>">最后一页</A>
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
                <option value="1">进货单单号</option>
                <option value="2">供货商编号</option>
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
