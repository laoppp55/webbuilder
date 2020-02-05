<%@page import="com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSu,
                com.bizwink.cms.kings.supplier.SupplierSuPeer,
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
    //System.out.println("==========="+siteid);
    String querystr = ParamUtil.getParameter(request, "querystr");
    int seltype = ParamUtil.getIntParameter(request, "seltype", 1);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    if (startrow < 0) {
        startrow = 0;
    }

    ISupplierSuManager supplierMgr = SupplierSuPeer.getInstance();
    List list = new ArrayList();
    List currentlist = new ArrayList();
    String sqlstr = "";
    if (seltype == 1) {
        sqlstr = "select * from tbl_supplier where SupplierName like '@" + querystr + "@' and siteid = '" + siteid + "'";
    } else if (seltype == 2) {
        sqlstr = "select * from tbl_supplier where SupplierID like '@" + querystr + "@' and siteid = '" + siteid + "'";
    }

    list = supplierMgr.getAllSupplier();
    currentlist = supplierMgr.getCurrentQureySupplierList(sqlstr, startrow, range);

    //int row = 0;
    int rows;
    int totalpages = 0;
    int currentpage = 0;

    //row = currentlist.size();
    rows = supplierMgr.getAllSupplierSuNum(siteid);

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
<HEAD><TITLE>供应商数据</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function DelSupplier(id)
        {
            var bln = confirm("真的要删除吗？");
            if (bln)
            {
                window.location = "delete.jsp?id=" + id;
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
        function goto() {
            form1.action = "supplier.jsp";
            form1.submit();
        }
    </SCRIPT>
</HEAD>
<BODY>
<P align=right><FONT color=#0000ff><A
        href=""></A></FONT> <BR>

<FORM name=form1 action=query.jsp method=post>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD align=middle width="3%">编号</TD>
            <TD align=middle width="5%">供货商编号</TD>
            <TD align=middle width="15%">供货商名称</TD>
            <TD align=middle width="8%">负责人</TD>
            <TD align=middle width="10%">联络电话1</TD>
            <TD align=middle width="9%">传真号</TD>
            <TD align=middle width="20%">公司地址</TD>
            <TD align=middle width="5%">浏览</TD>
            <TD align=middle width="5%">修改</TD>
            <TD align=middle width="5%">删除</TD>
        </TR>
        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    //for(int i=0;i<list.size();i++){
                    int id = 0;
                    String suppliername = "";
                    String owner = "";
                    String phone1 = "";
                    String fax = "";
                    String companyaddress = "";
                    // SupplierSu ss = (SupplierSu)list.get(i);
                    SupplierSu ss = (SupplierSu) currentlist.get(i);
                    id = ss.getSupplierid();
                    suppliername = ss.getSuppliername();
                    owner = ss.getOwner();
                    phone1 = Integer.toString(ss.getPhone1());
                    fax = Integer.toString(ss.getFax());
                    companyaddress = ss.getCompanyaddress();
        %>
        <TR height=35>
            <TD align=middle width="3%"><%=i + 1%>
            </TD>
            <TD align=middle width="5%"><%=id%>
            </TD>
            <TD align=middle width="15%"><%=suppliername == null ? "--" : suppliername%>
            </TD>
            <TD align=middle width="8%"><%=owner == null ? "--" : owner%>
            </TD>
            <TD align=middle width="10%"><%=phone1 == null ? "--" : phone1%>
            </TD>
            <TD align=middle width="9%"><%=fax == null ? "--" : fax%>
            </TD>
            <TD align=middle width="20%"><%=companyaddress == null ? "--" : companyaddress%>
            </TD>
            <TD align=middle width="5%"><A
                    href="scan.jsp?id=<%=id%>"><IMG
                    src="../images/preview.gif" border=0></A></TD>
            <TD align=middle width="5%"><A
                    href="edit.jsp?id=<%=id%>"><IMG
                    src="../images/dx.gif" border=0></A></TD>
            <TD align=middle width="5%">
                <A href="supplier.jsp#" onclick="javascript:return DelSupplier(<%=id%>);">
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
        <BR><BR>

    <p align=right>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD>&nbsp;&nbsp;检索：<select name="seltype">
                <option value="1">供应商名称</option>
                <option value="2">供应商编号</option>
            </select>
                <input type="text" size=40 name="querystr">&nbsp;&nbsp;<input type="button" value=" 查 询 "
                                                                              onclick="javascript:return searchcheck();">
                &nbsp;&nbsp;<INPUT onclick=javascript:goto(); type=button value=返回列表>
            </TD>
        </tr>
        </tbody>
    </table>

</FORM>
</BODY>
</HTML>
