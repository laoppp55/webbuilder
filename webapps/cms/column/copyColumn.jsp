<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.node" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    session.setAttribute("Current_URL",request.getRequestURI());
    int copyflag = ParamUtil.getIntParameter(request, "copyflag", 0);
    if (copyflag == 1) {
        int orgColumnId = ParamUtil.getIntParameter(request, "orgcolumnid", 0);
        int targetColumnId = ParamUtil.getIntParameter(request, "targetcolumnid", 0);
        if ((orgColumnId != 0) && (targetColumnId != 0)) {
            IColumnManager columnMgr = ColumnPeer.getInstance();
            Column column = columnMgr.getColumn(orgColumnId);
            int siteId = column.getSiteID();
            Tree colTree = TreeManager.getInstance().getSiteTree(siteId);
            node[] treenodes = colTree.getAllNodes();
            int[] cid = colTree.getSubTreeColumnIDList(treenodes, orgColumnId);

            String cids = "";
            for (int i = 0; i < cid[0] - 1; i++) {
                cids = cids + cid[i + 1] + ",";
            }
            cids = cids + cid[cid[0]];
            if (cids.length() > 0) {
                columnMgr.copyColumn(cids, targetColumnId);
            }
            response.sendRedirect("index.jsp");
        }
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<script language="javascript">
    function checkwhat()
    {
        var strVal = copycolumn.orgcolumnid.value;
        var strVal1 = copycolumn.targetcolumnid.value;

        if (strVal == "")
        {
            alert("源栏目不能为空，请选择被复制的栏目名称")
            return false;
        }

        if (strVal1 == "")
        {
            alert("目的栏目不能为空，请选择目的栏目名称")
            return false;
        }

        if (strVal == strVal1)
        {
            alert("目的栏目不能和源栏目相同")
            return false;
        }

        return true;
    }

    function cancelit()
    {
        window.location = "index.jsp";
    }

    function selectTColumn(from) {
        win = window.open("selectCopyColumnTree.jsp?f=" + from, "", "width=500,height=400");
        win.focus();
    }
</script>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"栏目拷贝", "copyColumn.jsp"}
    };
    String[][] operations = {
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form name="copycolumn" method="Post">
    <input type=hidden name=copyflag value=1>
    <center>
        <table cellpadding="1" cellspacing="1" border="0" width="50%">
            <tr>
                <td width="40%">原始栏目名称：<input id="orgcolumnname" name="orgcolumnname" size="22" readonly
                                              ondblclick="selectTColumn(0);">(请双击输入框选择栏目)
                    <input type=hidden id="orgcolumnid" name="orgcolumnid" size="5" readonly></td>
            </tr>
            <tr>
                <td width="40%">目的栏目名称：<input id="targetcolumnname" name="targetcolumnname" size="22" readonly
                                              ondblclick="selectTColumn(1);">(请双击输入框选择栏目)
                    <input type=hidden id="targetcolumnid" name="targetcolumnid" size="5" readonly></td>
                <td width="20%">
                    <input type=submit value=" 拷贝 " onclick="return checkwhat();">&nbsp;&nbsp;
                    <input type=button value=" 取消 " onclick="cancelit();">
                </td>
            </tr>
        </table>
    </center>
</form>
</BODY>
</html>