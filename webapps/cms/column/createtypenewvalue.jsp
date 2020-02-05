<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.Producttype" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String errormsg = "";
    boolean error = false;
    int columnID = ParamUtil.getIntParameter(request, "columnID", 0);
    int typeid = ParamUtil.getIntParameter(request, "typeid", 0);
    String act = ParamUtil.getParameter(request, "act");
    session.setAttribute("Current_URL",request.getRequestURI());
    IColumnManager columnMgr = ColumnPeer.getInstance();

    //增加
    if (act != null && act.equals("doCreate")) {
        String cname = ParamUtil.getParameter(request, "cname");
        String ename = ParamUtil.getParameter(request, "ename");
        if (cname == null || cname.trim().length() == 0 || ename == null || ename.trim().length() == 0) {
            error = true;
            errormsg = "<p align=center><font color=red><b>创建分类值出错！</b></font></p>";
        }

        if (!error) {
            Producttype pro = new Producttype();
            pro.setCname(cname);
            pro.setEname(ename);
            pro.setColumnID(columnID);
            pro.setParentid(typeid);
            pro.setReferid(0);
            columnMgr.createType(pro);
        }
        response.sendRedirect("createtypenewvalue.jsp?act=doNothing&typeid=" + typeid + "&columnID=" + columnID);
    }

    //修改
    if (act != null && act.equals("doUpdate")) {
        String cname = ParamUtil.getParameter(request, "cname");
        String ename = ParamUtil.getParameter(request, "ename");
        int id = ParamUtil.getIntParameter(request, "id", 0);
        if (cname == null || cname.trim().length() == 0 || ename == null || ename.trim().length() == 0) {
            error = true;
            errormsg = "<p align=center><font color=red><b>修改分类出错！</b></font></p>";
        }

        if (!error) {
            columnMgr.updateTypeCname(id, cname, ename);
        }
        response.sendRedirect("createtypenewvalue.jsp?act=doNothing&typeid=" + typeid + "&columnID=" + columnID);
    }

    //删除
    if (act != null && act.equals("doDelete")) {
        int id = ParamUtil.getIntParameter(request, "id", 0);
        columnMgr.deleteTypeValue(id);
        response.sendRedirect("createtypenewvalue.jsp?act=doNothing&typeid=" + typeid + "&columnID=" + columnID);
    }
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <title>添加子分类</title>
</head>
<script type="text/javascript">
    function check(frm)
    {
        if (frm.cname.value == "")
        {
            alert("分类值中文名称不能为空！");
            return false;
            cname.focus();
        }
        return true;
    }
    function Save()
    {
        window.location = "createtypenewvalue.jsp?act=doSave&columnID=<%=columnID%>";
    }
    function Update(i, id)
    {
        var vcname = document.all("cname" + i).value;
        var vename = document.all("ename" + i).value;
        if ((vcname == "") || (vename == ""))
        {
            return;
        }
        window.location = "createtypenewvalue.jsp?id=" + id + "&cname=" + vcname + "&ename=" + vename + "&typeid=<%=typeid%>&act=doUpdate&columnID=<%=columnID%>";
    }
    function addvalue(id, columnid) {
        window.open("createtypenewvalue.jsp?typeid=" + id + "&columnID=" + columnid, "", "width=600,height=400,left=200,right=200,scrollbars,status");
    }
</script>

<body>
<table border="0" width="580" align="center">
    <tr>
        <td><%=errormsg%>
        </td>
    </tr>
    <tr height=80>
        <td width="100%">
            <form method="POST" action="createtypenewvalue.jsp?columnID=<%=columnID%>"
                  onsubmit="javascript:return check(this);">
                <input type=hidden name=act value=doCreate>
                <input type=hidden name=columnID value="<%=columnID%>">
                <input type=hidden name=typeid value="<%=typeid%>">
                <table border=0 cellspacing=2 cellpadding=2 width="100%">
                    <tr>
                        <td width="40%">
                            分类中文名称：<input name="cname" size="15" class=tine>
                        </td>
                        <td width="60%">
                            分类英文名称：<input name="ename" size="15" class=tine>
                            <input type="submit" value="  提交  " name="submit" class=tine>
                        </td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
    <tr>
        <td width="100%">
            <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
                <tr>
                    <td width="20%" align="center"><B>分类中文名称</B></td>
                    <td width="20%" align="center"><B>分类英文名称</B></td>
                    <td width="20%" align="center"><B>操作</B></td>
                    <td width="20%" align="center"><B>添加子分类</B></td>
                </tr>
                <%
                    List vlist = columnMgr.getAllTypeForColumn("select * from tbl_type where parentid = " + typeid + " and columnid = " + columnID + " order by createdate desc");
                    for (int i = 0; i < vlist.size(); i++) {
                        Producttype pv = (Producttype) vlist.get(i);
                        String cname = StringUtil.gb2iso4View(pv.getCname());
                        String ename = pv.getEname();
                %>
                <tr>
                    <td align="center"><input name=cname<%=i%> value="<%=cname%>" size=14 class=tine></td>
                    <td align="center"><input name=ename<%=i%> value="<%=ename%>" size=14 class=tine></td>
                    <td align="center">
                        <a href="createtypenewvalue.jsp?id=<%=pv.getId()%>&typeid=<%=typeid%>&act=doDelete&columnID=<%=columnID%>">删除</a>&nbsp;
                        <a href="javascript:Update(<%=i%>,<%=pv.getId()%>);">修改</a>
                    </td>
                    <td align=center><input type=radio onclick="javascript:addvalue(<%=pv.getId()%>,<%=columnID%>);">
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        </td>
    </tr>
    <tr height=40>
        <td align=center>
            <input type="button" value="   关闭   " name="close" class=tine onclick="window.close();">
        </td>
    </tr>
</table>

</body>
</html>