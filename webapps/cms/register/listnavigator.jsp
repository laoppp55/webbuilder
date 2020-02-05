<%@ page import="java.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>

<%
    //读出所有文章列表样式文件
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int listcount = 0;
    int total = 0;
    IViewFileManager fileMgr = viewFilePeer.getInstance();

    List list = new ArrayList();
    list = fileMgr.getNavigatorList(start, range);
    listcount = list.size();
    total = fileMgr.getNavigatorCount();
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script language="javascript">
        function PreviewNavigator(id)
        {
            window.open("../register/viewnavigator.jsp?id="+id,"PreviewNavigator","width=800,height=600,left=150,top=225,scrollbars=yes");
        }

        function select_navigator(name) {
            var value=0;
            for(var i=0; i<selnav.navradio.length; i++) {
                if (selnav.navradio[i].checked) {
                    value = selnav.navradio[i].value;
                    break;
                }
            }

            //alert(value);
            if (value==0) {
                alert("请选择网站导航");
                return false;
            } else {
                alert(value);
                opener.document.regform.navgator.value = value;
                opener.document.regform.mnavgator.value = name;
                window.close();
            }
        }

    </script>
</head>

<body>
<%
    String[][] titlebars = {
            { "网站导航条", "" }
    };

    String str1 = "网站导航条";
    String[][] operations = {
            {str1, "javascript:CreateNavigator();"}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<br>
<%
    if (listcount > 0)
    {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% class=line>");
        if (start - range >= 0)
            out.println("<a href=listNavigator.jsp?&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total)
        {
            int remain = ((start+range-total)<range)?(total-start-range):range;
            out.println(remain+"<a href=listNavigator.jsp?range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="80%" align=center>
    <form name="selnav">
        <tr bgcolor="#eeeeee" class=tine>
            <td width="10%"  align=center>选择</td>
            <td width="15%"  align=center>编号</td>
            <td width="20%" align=center><b>中文名称</b></td>
            <td width="32%" align=center>注释</td>
            <td width="15%" align=center>修改日期</td>
            <td width="8%" align=center>预览</td>
        </tr>
        <%
            for (int i=0; i<list.size(); i++)
            {
                navigator file = (navigator)list.get(i);
                String bgcolor = (i%2==0)?"#ffffff":"#eeeeee";
        %>
        <tr bgcolor="<%=bgcolor%>" class=itm onmouseover="this.style.background='#CECEFF';" onmouseout="this.style.background='<%=bgcolor%>'" height=25>
            <td align=center>
                <input type="radio" name="navradio"  onclick="javascript:select_navigator('<%=file.getName()%>')"  value=<%=file.getID()%> />
            </td>
            <td align=center><font color=red><%=file.getID()%></font></td>
            <td>&nbsp;&nbsp;<%=StringUtil.gb2iso4View(file.getName())%></td>
            <td>&nbsp;&nbsp;<%=StringUtil.gb2iso4View(file.getNotes())%></td>
            <td align=center><%=file.getCreateDate().toString().substring(0,10)%></td>
            <td align=center><a href="javascript:PreviewNavigator(<%=file.getID()%>);"><img src="../images/button/view.gif" border=0></a></td>
        </tr>
        <%}%>
    </form>
</table>
</body>
</html>