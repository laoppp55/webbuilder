<%@ page import="java.io.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.markManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int siteID = authToken.getSiteID();
    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = null;
    String CName = null;
    if (columnID>0) {
        column = columnManager.getColumn(columnID);
        CName = StringUtil.gb2iso4View(column.getCName());
    } else {
        CName = "程序模板";
    }
    List list = new ArrayList();
    IMarkManager markMgr = markPeer.getInstance();
    mark mymark = new mark();
    list = markMgr.listAllMarks(siteID, columnID);
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>

<script language="javascript">
    function insmark() {
        if (selectmark.selectname.markid == "")
        {
            alert("请选择您要插入的标记")
            return false;
        }
        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            window.parent.returnValue = '[TAG][MARKID]' + selectmark.selectname.markid + '_<%=columnID%>[/MARKID][/TAG][TYPE]' +
                                        selectmark.selectname.marktype + '[/TYPE]';
            window.parent.close();
        }else{
            var returnvalue = '[TAG][MARKID]' + document.getElementById("selectname").markid + '_<%=columnID%>[/MARKID][/TAG]';
            var markname = document.getElementById("selectname").typename;
            returnvalue = "<INPUT name='"+returnvalue+"' type=button value='["+markname+"]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
            window.parent.opener.InsertHTML("content",returnvalue);
            top.close();
        }
    }

    function selectthis(cname, mid, type, typename) {
        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            selectmark.selectname.value = cname;
            selectmark.selectname.markid = mid;
            selectmark.selectname.marktype = type;
            selectmark.selectname.typename = typename;
        }else{
            document.getElementById("selectname").markid = mid;
            document.getElementById("selectname").value = cname;
            document.getElementById("selectname").marktype = type;
            document.getElementById("selectname").typename = typename;
        }
    }
</script>
<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr>
        <td colspan=5> 当前所在栏目--->><font color=red><%=CName%>
        </font></td>
    </tr>
    <tr class=itm bgcolor='#dddddd'>
        <td align=center>标记中文名</td>
        <td align=center>标记类型名</td>
        <td align=center>建立日期</td>
    </tr>
    <%
        String typename = "", cname = "", datestr = "";
        for (int i = 0; i < list.size(); i++) {
            mymark = (mark) list.get(i);
            if (mymark.getMarkType() == 1) {
                typename = "文章列表";
            } else if (mymark.getMarkType() == 2) {
                typename = "栏目列表";
            } else if (mymark.getMarkType() == 3) {
                typename = "热点文章";
            } else if (mymark.getMarkType() == 4) {
                typename = "相关文章";
            } else if (mymark.getMarkType() == 5) {
                typename = "子文章列表";
            } else if (mymark.getMarkType() == 6) {
                typename = "HTML碎片";
            }

            //System.out.println("type=" + mymark.getMarkType());

            cname = mymark.getChineseName() == null ? "--" : StringUtil.gb2iso4View(mymark.getChineseName());
            datestr = mymark.getCreateDate() == null ? "--" : mymark.getCreateDate().toString().substring(0, 15);
            String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
            out.println("<tr bgcolor=" + bgcolor + "class=itm>");
            out.println("<td align=center><a href=\"javascript:selectthis('"+cname+"',"+mymark.getID()+","+mymark.getMarkType()+",'"+typename+"')\">" + cname + "</a></td>");
            out.println("<td id=tname"+mymark.getID()+">" + typename + "</td>");
            out.println("<td  align=center>" + datestr + "</td>");
            out.println("</tr>");
        }
    %>
</table>

<table cellpadding="1" cellspacing="2" border="0">
    <form name="selectmark">
        <tr>
            <td>
                <span style="font-size:9pt">选中的：</span>
                <input id="selectname" name="selectname" markid="" size="40" readonly class=tine>
            </td>
        </tr>
    </form>
</table>
<br>
<input type="button" ID=Myok onclick="insmark()" class=tine value="确 定">
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" class=tine ONCLICK="top.close();" value="返 回">
</BODY>
</html>
