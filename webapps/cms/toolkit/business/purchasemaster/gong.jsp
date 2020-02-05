<%@page import="com.bizwink.cms.kings.supplier.ISupplierSuManager,
                com.bizwink.cms.kings.supplier.SupplierSuPeer,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.SessionUtil,
                java.util.ArrayList,
                java.util.List" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.kings.supplier.SupplierSu" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    ISupplierSuManager supplierMgr = SupplierSuPeer.getInstance();
    String querystr = ParamUtil.getParameter(request, "querystr");
    List list = new ArrayList();
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    if (startrow < 0) {
        startrow = 0;
    }

    String sqlstr = "select * from tbl_supplier where supplierid like '@" + querystr + "@' and siteid = '" + siteid + "'";    

    if(startflag == 1){
        list = supplierMgr.getCurrentQureySupplierList(sqlstr, startrow, range);
    }else{
        list = supplierMgr.getAllSupplier(siteid);
    }
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
<HEAD><TITLE>供应商信息</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="../images/common.css" type=text/css rel=stylesheet>
    <LINK href="../images/forum.css" type=text/css rel=stylesheet>
    <SCRIPT language=javascript>
        function check() {
            var radio  = document.getElementsByName("radio");
            for(var i = 0; i< radio.length;i++){
                if(radio[i].checked){
                    radio = radio[i].value;
                    //alert(radio);
                    window.returnValue = radio;
                    window.close();
                }
            }
        }

        function searchcheck() {
            if ((form1.querystr.value == null) || (form1.querystr.value == "")) {
                alert("请输入要查询的内容！");
                return false;
            }
            //form1.submit();
            return true;
        }        

        function goto()
        {
            form1.action = "purchasemaster.jsp";
            form1.submit();
        }
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff>
<FORM name=form1 action=gong.jsp method=post onsubmit="javascript:searchcheck();" >
        <INPUT type=hidden value=1 name=startflag>
    <BR>
    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=3 height=32>
                    <P align=center>请选择供应商</P></TD>
            </TR>
            <TR height=32>
                <TD align=center width=10% height=32>选择</TD>
                <TD align=center width=25% height=32>供应商编号</TD>
                <TD align=center width=65% height=32>供应商名称</TD>
            </TR>            
                        <%
                            for (int i = 0; i < list.size(); i++) {
                                SupplierSu ss = (SupplierSu) list.get(i);
                        %>
            <TR height=32>
                <TD align=center><input type="radio" name="radio" value="<%=ss.getSupplierid()%>" onclick="check();"></TD>
                <TD align=center>&nbsp;<%=ss.getSupplierid()%></TD>
                <TD align=left>&nbsp;<%=ss.getSuppliername()==null?"":ss.getSuppliername()%></TD>
            </TR><% } %>
            </TBODY>
        </TABLE><BR><BR>
         <p align=center>
    <TABLE>
        <TBODY>
        <TR>
            <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=rows%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                <%
                    if ((startrow - range) >= 0) {
                %>
                <a href="gong.jsp?startrow=0">第一页</a>
                <%}%>
                <%if ((startrow - range) >= 0) {%>
                <a href="gong.jsp?startrow=<%=startrow-range%>">上一页</a>
                <%}%>
                <%if ((startrow + range) < rows) {%>
                <A href="gong.jsp?startrow=<%=startrow+range%>">下一页</A>
                <%}%>
                <%if (currentpage != totalpages) {%>
                <A href="gong.jsp?startrow=<%=(totalpages-1)*range%>">最后一页</A>
                <%}%>
            </TD>
            <TD>&nbsp;</TD>
        </TR>
        </TBODY>
    </TABLE>
    <BR><BR>
            <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD>&nbsp;&nbsp;检索：供应商编号
                <input type="text" size=20 name="querystr">&nbsp;&nbsp;<input type="submit" value=" 查 询 "
                                                                              onclick="javascript:return searchcheck();">
               
            </TD>
        </tr>
        </tbody>
    </table>
<%--        <P align=center><INPUT onclick="javascript:return check();" type=submit value=" 确 认 " name=Ok>&nbsp;&nbsp;
            <INPUT onclick=javascript:window.close(); type=button value=返回列表 name=golist>
        </P>--%>
    </CENTER>
</FORM>
</BODY>
</HTML>
