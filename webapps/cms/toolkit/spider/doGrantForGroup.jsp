<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    int groupID = ParamUtil.getIntParameter(request, "groupID", 0);
    int rightID = ParamUtil.getIntParameter(request, "rightID", 0);
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <LINK href="/webbuilder/style/global.css" type=text/css rel=stylesheet>
    <script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
    <SCRIPT LANGUAGE=javascript>
        function checkFrm(frm)
        {
            if (frm.columnList.length > 0)
            {
                var el = form1.columnList;
                var getColumnHtml = "";
                for (var i = el.options.length - 1; i >= 0; i--) {
                    el.options[i].selected = true;
                    getColumnHtml = getColumnHtml + "<input name=selectcolumns type=checkbox checked value="+el.options[i].value+">"+el.options[i].text;
                }
                window.parent.opener.document.getElementById("columns").innerHTML = getColumnHtml;
                top.close();
            }
        }

        function delElement()
        {
            var el = form1.columnList;
            for (var i = el.options.length - 1; i >= 0; i--) {
                if (el.options[i].selected) {
                    el.options[i] = null;
                }
            }
            if (form1.columnList.length == 0) {
                document.getElementById("columnSubmit").disabled = true;
                document.getElementById("delColumn").disabled = true;
            }
        }

        function selectRight()
        {
            var rightID = form1.rightList.value;
            window.location = "doGrantForGroup.jsp?groupID=<%=groupID%>&rightID=" + rightID;
        }
    </SCRIPT>
</head>

<body bgcolor="#cccccc">
<form name="form1" method="post">
    <input type=hidden name=doGrant value=true>
    <input type=hidden name=groupID value="<%=groupID%>">
    <input type=hidden name=rightID value="<%=rightID%>">
    <table width="90%" border="0" align=center>
        <tr>
            <td>
                <table width="100%" border=0 cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="60%" valign="top">发布到栏目：<br>
                            <select name="columnList" size="15" style="width:180" multiple>
                            </select>
                        </td>
                        <td width="50%" align="left" valign="top"><br><input type=button onclick="delElement();"
                                                                             value="删除" class=tine id="delColumn"
                                                                             disabled></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td height="40" align="left">
                <input type="button" value=" 确定 " class=tine onclick="return checkFrm(this.form);" id="columnSubmit"
                       <%if(rightID==0){%>disabled<%}%>>&nbsp;&nbsp;
                <input type="button" value=" 返回 " class=tine onclick="parent.window.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
