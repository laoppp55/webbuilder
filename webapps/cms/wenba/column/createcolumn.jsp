<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.wenba.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String editor = authToken.getUserID();
    IWenbaManager wenbaManager = wenbaManagerImpl.getInstance();
    wenbaImpl column = new wenbaImpl();

    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int parentID = ParamUtil.getIntParameter(request, "parentID", 0);

    boolean errors = false;
    boolean success = false;
    boolean errorCName = false;
    boolean errorEName = false;
    String CName = "";
    String EName = "";
    int orderid = 0;
    boolean dualName = false;

    if (doCreate) {
        CName = ParamUtil.getParameter(request, "CName");
        EName = ParamUtil.getParameter(request, "EName");
        orderid = ParamUtil.getIntParameter(request, "orderid", 1);

        if (CName == null) {
            errorCName = true;
            errors = true;
        }
        if (EName == null) {
            errorEName = true;
            errors = true;
        }
    }

    if (!errors && doCreate) {
        dualName = wenbaManager.duplicateEnName(parentID, EName);
        if (!dualName) {
            try {
                column.setSiteID(siteid);
                column.setParentID(parentID);
                column.setCName(StringUtil.gb2isoindb(CName));
                column.setEName(EName);
                column.setCreationDate(new Timestamp(System.currentTimeMillis())) ;
                column.setOrderID(orderid);
                column.setCreator(editor) ;
                wenbaManager.create(column);
                success = true;
            }
            catch (wenbaException uaee) {
                uaee.printStackTrace();
                errors = true;
            }
        }
    }

    if (success) {
        response.sendRedirect(response.encodeRedirectURL("index.jsp?rightid=1&msgno=0"));
        return;
    }

    column = wenbaManager.getColumn(parentID);
    String parentName = StringUtil.gb2iso4View(column.getCName());
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../../style/global.css>
    <script language=javascript>
        function check(frm)
        {
            if (frm.CName.value == "")
            {
                alert("栏目中文名称不能为空！");
                return false;
            }
            if (frm.CName.value.indexOf(",") > -1)
            {
                alert("栏目中文名称中不能含有逗号！");
                return false;
            }
            if (frm.EName.value == "")
            {
                alert("栏目英文名称不能为空！");
                return false;
            }
            else if (!checkEname(frm.EName.value))
            {
                alert("栏目英文名称不合法！应该由字母、数字及下划线组成！");
                return false;
            }
            return true;
        }

        function checkEname(str)
        {
            var regstr = /[^0-9a-zA-Z_]/gi;
            if (regstr.exec(str) != null)
                return false;
            else
                return true;
        }
    </script>
</head>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"新建栏目", ""}
    };
    String[][] operations = null;
%>
<%@ include file="../../inc/titlebar.jsp" %>
<p>
    <%
        if (!success && errors) {
            out.println("<p class=cur>");
            if (!success)
                out.println("栏目 " + EName + " 已经存在。请另换一个栏目名。");
            else
                out.println("输入有错误。请检查下面字段，再试。");
        }
    %>
</p>
<center>
    <form action="createcolumn.jsp" method="post" name="createForm" onsubmit="return check(this);">
        <input type="hidden" name="doCreate" value="true">
        <input type="hidden" name="parentID" value="<%=parentID%>">
        <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="60%">
            <tr height=20>
                <td align=right class=line>父栏目名：</td>
                <td class=tine>&nbsp;<%= parentName %>
                </td>
            </tr>
            <tr height=30>
                <td align=right><font class=line <%= (errorCName) ? (" color=\"#ff0000\"") : "" %>>栏目名称：</font></td>
                <td>&nbsp;<input class=tine name=CName size=30 value="<%= (CName!=null)?CName:"" %>">*</td>
            </tr>
            <tr height=30>
                <td align=right><font class=line <%= (errorEName) ? (" color=\"#ff0000\"") : "" %>>栏目目录：</font></td>
                <td>&nbsp;<input class=tine name=EName size=30 value="<%= (EName!=null)?EName:"" %>">*&nbsp;&nbsp;
                    <font color=red>目录由字母、数字及下划线组成</font>
                </td>
            </tr>
            <tr height=30>
                <td align=right class=line>栏目排序：</td>
                <td>&nbsp;<input class=tine name=orderid size=30 value="0">*</td>
            </tr>
        </table>
        <br>
        <input type=submit value="  保存  ">&nbsp;&nbsp;&nbsp;&nbsp;
        <input type=button value="  返回  " onclick="history.go(-1);">
    </form>
</center>

</BODY>
</html>
