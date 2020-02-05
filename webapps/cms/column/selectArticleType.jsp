<%@ page import="com.bizwink.cms.news.Column" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    
    int ID = ParamUtil.getIntParameter(request, "ID", 0);

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(ID);

    String selectColumns = "";
    try {
        List selectColumnsList = columnMgr.getRefersColumnIds(ID);
        for (int i = 0; i < selectColumnsList.size(); i++) {
            Column scolumn = (Column) selectColumnsList.get(i);
            selectColumns = selectColumns + scolumn.getScid() + ",";
        }

        if(selectColumns.length() > 0)
            selectColumns = selectColumns.substring(0, selectColumns.length()-1);
    } catch (Exception e) {
        e.printStackTrace();
    }

    String[] selectCID = null;
    if((selectColumns != null) && (selectColumns.length()>0)) selectCID = selectColumns.split(",");
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <LINK href="../style/global.css" type=text/css rel=stylesheet>
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
                    getColumnHtml = getColumnHtml + "<input name=selecttypes type=checkbox checked value="+el.options[i].value+">"+el.options[i].text;
                }
                window.parent.opener.document.getElementById("types").innerHTML = getColumnHtml;
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
    </SCRIPT>
</head>

<body bgcolor="#cccccc">
<form name="form1" method="post">
    <input type=hidden name=doSelect value=true>
    <table width="90%" border="0" align=center>
        <tr>
            <td>
                <table width="100%" border=0 cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="60%" valign="top">被引用分类的栏目：<br>
                            <select name="columnList" size="15" style="width:180" multiple>
                                <%
                                    if(selectCID != null){
                                        for(int i=0;i<selectCID.length;i++){
                                            column = columnMgr.getColumn(Integer.parseInt(selectCID[i]));
                                            String cname = StringUtil.gb2iso4View(column.getCName());
                                            int cid = column.getID();
                                %>
                                    <option value="<%=cid%>"><%=cname%></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </td>
                        <td width="50%" align="left" valign="top"><br><input type=button onclick="delElement();"
                                                                             value="删除" class=tine id="delColumn"
                                                                             <%if(selectCID == null){%>disabled<%}%>></td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td height="40" align="left">
                <input type="button" value=" 确定 " class=tine onclick="return checkFrm(this.form);" id="columnSubmit"
                       <%if(selectCID == null){%>disabled<%}%>>&nbsp;&nbsp;
                <input type="button" value=" 返回 " class=tine onclick="parent.window.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
