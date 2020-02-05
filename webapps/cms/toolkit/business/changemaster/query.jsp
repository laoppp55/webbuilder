<%@page import="com.bizwink.cms.kings.changemaster.ChangeMaster,
                com.bizwink.cms.kings.changemaster.ChangeMasterPeer,
                com.bizwink.cms.kings.changemaster.IChangeMasterManager,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DateFormat" %>
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

    IChangeMasterManager chaMgr = ChangeMasterPeer.getInstance();
    List list = new ArrayList();
    List currentlist = new ArrayList();
    String sqlstr = "";
    if (seltype == 1) {
        sqlstr = "select * from tbl_changemaster where ChangeID like '@" + querystr + "@' and siteid = '" + siteid + "'";
    } else if (seltype == 2) {
        sqlstr = "select * from tbl_changemaster where ChangeDate like TO_DATE('"+ querystr +"','yyyy-mm-dd') and siteid = '" + siteid + "'";
    }
    //System.out.println("sql = " + sqlstr);
    //list = pmMgr.getAllProduct();
    currentlist = chaMgr.getCurrentQureyChangeList(sqlstr, startrow, range);

    //int row = 0;
    int rows;
    int totalpages = 0;
    int currentpage = 0;

    //row = currentlist.size();
    rows = chaMgr.getAllChangeMasterNum(siteid);

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
        function DelChange(id)
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
            form1.action = "changemaster.jsp";
            form1.submit();
        }
    </SCRIPT>
</HEAD>
<BODY>
<P align=right><FONT color=#0000ff><a href="changemaster.jsp">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <A href="add.jsp">添加新货单</A></FONT> <BR>

<FORM name=form1 action=query.jsp method=post>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD align=middle width="5%">编号</TD>
            <TD align=middle width="15%">变动单单号</TD>
            <TD align=middle width="8%">变动日期</TD>
            <TD align=middle width="10%">变动类别</TD>
            <TD align=middle width="9%">说明</TD>
            <TD align=middle width="5%">修改</TD>
            <TD align=middle width="5%">删除</TD>
        </TR>
        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    int id;
                    String changeid;
                    Timestamp changedate;
                    String changekind;
                    String description;
                    ChangeMaster cm = (ChangeMaster) currentlist.get(i);
                    id = cm.getId();
                    changeid = cm.getChangeID();
                    changedate = cm.getChangeDate();
                    changekind = Integer.toString(cm.getChangeKind());
                    description = cm.getDescription();
        %>
        <TR height=35>
            <TD align=middle width="3%"><%=i + 1%>
            </TD>
            <TD align=middle width="15%"><%=changeid == null ? "--" : changeid%>
            </TD>
            <TD align=middle width="8%"><%=changedate.toString().substring(0, 10)%>
            </TD>
            <TD align=middle width="10%"><%=changekind == null ? "--" : changekind%>
            </TD>
            <TD align=middle width="20%"><%=description == null ? "--" : description%>
            </TD>
            <TD align=middle width="5%"><A
                    href="edit.jsp?id=<%=id%>"><IMG
                    src="../images/dx.gif" border=0></A></TD>
            <TD align=middle width="5%">
                <A href="changemaster.jsp#" onclick="javascript:return DelChange(<%=id%>);">
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
                <option value="1">变动单单号</option>
                <option value="2">变动日期</option>
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
