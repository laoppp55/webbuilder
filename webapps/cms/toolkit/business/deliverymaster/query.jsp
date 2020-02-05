<%@page import="com.bizwink.cms.kings.deliverymaster.DeliveryMaster,
                com.bizwink.cms.kings.deliverymaster.DeliveryMasterPeer,
                com.bizwink.cms.kings.deliverymaster.IDeliveryMasterManager,
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
    String querystr = ParamUtil.getParameter(request, "querystr");
    int seltype = ParamUtil.getIntParameter(request, "seltype", 1);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    if (startrow < 0) {
        startrow = 0;
    }

    IDeliveryMasterManager delMgr = DeliveryMasterPeer.getInstance();
    List list = new ArrayList();
    List currentlist = new ArrayList();
    String sqlstr = "";
    if (seltype == 1) {
        sqlstr = "select * from tbl_deliverymaster where DeliveryID like '@" + querystr + "@' and siteid = '" + siteid + "'";
    } else if (seltype == 2) {
        sqlstr = "select * from tbl_deliverymaster where deliveryaddress like '@" + querystr + "@' and siteid = '" + siteid + "'";
    }

    currentlist = delMgr.getCurrentQureyDeliveryMasterList(sqlstr, startrow, range);

    int rows;
    int totalpages = 0;
    int currentpage = 0;

    rows = delMgr.getAllDeliveryMasterNum(siteid);

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
        function DelDelivery(deliveryid)
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
        function goto() {
            form1.action = "deliverymaster.jsp";
            form1.submit();
        }
    </SCRIPT>
</HEAD>
<BODY>
<P align=right><FONT color=#0000ff><a href="deliverymaster.jsp">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <A href="add.jsp">添加新货单</A></FONT> <BR>

<FORM name=form1 action=query.jsp method=post>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD align=middle width="5%">编号</TD>
            <TD align=middle width="15%">出货单单号</TD>
            <TD align=middle width="8%">出货日期</TD>
            <TD align=middle width="10%">出货单属性</TD>
            <TD align=middle width="9%">送货地址</TD>
            <TD align=middle width="5%">浏览</TD>
            <TD align=middle width="5%">修改</TD>
            <TD align=middle width="5%">删除</TD>
        </TR>
        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    int id = 0;
                    String deliveryid;
                    Timestamp deliverydate;
                    String deliveryproperty;
                    String deliveryaddress;
                    DeliveryMaster dm = (DeliveryMaster) currentlist.get(i);
                    deliveryid = dm.getDeliveryID();
                    deliverydate = dm.getDeliveryDate();
                    deliveryproperty = dm.getDeliveryProperty();
                    deliveryaddress = dm.getDeliveryAddress();
        %>
        <TR height=35>
            <TD align=middle width="3%"><%=i + 1%>
            </TD>
            <TD align=middle width="15%"><%=deliveryid == null ? "--" : deliveryid%>
            </TD>
            <TD align=middle width="8%"><%=deliverydate.toString().substring(0, 10)%>
            </TD>
            <TD align=middle width="10%"><%if(deliveryproperty != null){if(deliveryproperty.equals("1")){
                %>出货<%}else{%>出货退出<%}}%>
            </TD>
            <TD align=middle width="20%"><%=deliveryaddress == null ? "--" : deliveryaddress%>
            </TD>
            <TD align=middle width="5%"><A
                    href="scan.jsp?id=<%=deliveryid%>"><IMG
                    src="../images/preview.gif" border=0></A></TD>
            <TD align=middle width="5%"><A
                    href="edit.jsp?deliveryid=<%=deliveryid%>"><IMG
                    src="../images/dx.gif" border=0></A></TD>
            <TD align=middle width="5%">
                <A href="deliverymaster.jsp#" onclick="javascript:return DelDelivery('<%=deliveryid%>');">
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
                <option value="1">出货单单号</option>
                <option value="2">送货地址</option>
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
