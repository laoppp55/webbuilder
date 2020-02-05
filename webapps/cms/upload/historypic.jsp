<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.pic.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String sitename = authToken.getSitename();
    int pictype = ParamUtil.getIntParameter(request, "pictype", 0);
    String attrname = request.getParameter("attr");
    int siteID = authToken.getSiteID();
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 10);
    int type = ParamUtil.getIntParameter(request, "type", 0);
    String searchpicname = ParamUtil.getParameter(request, "picname");
    IPicManager picMgr = PicPeer.getInstance();
    Pic pic = new Pic();
    List currentlist = picMgr.getPicInfo(searchpicname, siteID, type, start, range);
    int row = picMgr.getPicInfoNum(searchpicname, siteID, type);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <title>历史图片</title>
</head>
<script type="text/javascript">
    function golist(r, pictype, attrname, searchname, type) {
        var bor = (r - 1) * <%=range%>;
        if (isNumber(r)) {
            window.location = "historypic.jsp?pictype=" + pictype + "&attr=" + attrname + "&start=" + bor + "&picname=" + searchname + "&type=" + type;
        }
    }

    function isNumber(num) {
        var strRef = "1234567890";
        for (i = 0; i < num.length; i++)
        {
            tempChar = num.substring(i, i + 1);
            if (strRef.indexOf(tempChar, 0) == -1) {
                alert("输入页码不正确！");
                return false;
            }
        }
        return true;
    }

    function ret(retval) {
        var editor = window.parent.opener.top.CKEDITOR.instances.content;
        editor.insertHtml(retval);
        top.close();
    }
    function ret1(retval, attrname) {
        if (attrname == "mt")
            window.parent.opener.top.document.getElementById("maintitle").value = retval;
        else if (attrname == "vt")
            window.parent.opener.top.document.getElementById("vicetitle").value = retval;
        else if (attrname == "au")
            window.parent.opener.top.document.getElementById("author").value = retval;
        else if (attrname == "sr")
            window.parent.opener.top.document.getElementById("source").value = retval;
        else if (attrname == "pic")
            window.parent.opener.top.document.getElementById("pic").value = retval;
        else if (attrname == "bigpic")
            window.parent.opener.top.document.getElementById("bigpic").value = retval;
        else if (attrname == "apic")
            window.parent.opener.top.document.getElementById("articlepic").value = retval;
        top.close();
    }
</script>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
            <%
            for (int i = 0; i < currentlist.size(); i++) {
                pic = (Pic) currentlist.get(i);
                String picname = pic.getPicname();
                String picurl = pic.getImgurl();
                String retval = "<IMG SRC=" + pic.getImgurl() + " border=0>";
                if(i % 5 == 0){
        %>
    <tr>
        <%}%>
        <td align="center" width="150">
            <%if (pictype == 0) {%>
            <a href="javascript:ret('<%=retval%>');"><img src="<%=picurl%>" border=1 width=140></a>
            <%} else {%>
            <a href="javascript:ret1('<%=picurl%>','<%=attrname%>');"><img src="<%=picurl%>" border=1 width=140></a>
            <%}%>
        </td>
        <%
            if (i % 5 == 4) {
        %>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
    <%
            }
        }

        if ((5 - (currentlist.size() % 5) - 1) > 0) {
            for (int i = 0; i < (5 - (i % 5) - 1); i++) {
    %>
    <td width="150">&nbsp;</td>
    <%
            }
            out.println("</tr><tr><td>&nbsp;</td></tr>");
        }

        if (row > 0) {
    %>
    <tr>
        <td colspan="5" align="center">
            <%if ((start - range) >= 0) {%>
            <a href="historypic.jsp?pictype=<%=pictype%>&attr=<%=attrname%>&start=<%=start-range%>&picname=<%=searchpicname%>&type=<%=type%>">上一页</a>&nbsp;&nbsp;
            <%}%>
            <%if (start + range < row) {%>
            <a href="historypic.jsp?pictype=<%=pictype%>&attr=<%=attrname%>&start=<%=start+range%>&picname=<%=searchpicname%>&type=<%=type%>">下一页</a>&nbsp;&nbsp;
            <%}%>
            &nbsp;&nbsp;
            到第<input type="text" name="jump" value="<%=(start+range)/10%>" size="2">页
            <a href="javascript:golist(document.all('jump').value,<%=pictype%>,'<%=attrname%>','<%=searchpicname%>',<%=type%>);">GO</a>
        </td>
    </tr>
    <%}%>
</table>
<hr>
<form name="form1" method="post" action="historypic.jsp">
    <input type="hidden" name="attr" value="<%=attrname%>">
    <input type="hidden" name="pictype" value="<%=pictype%>">
    <input type="hidden" name="type" value="<%=type%>">
    <table width="60%" border="0" cellspacing="0" cellpadding="0" align="center">
        <tr class=itm>
            <td align="left" height="24" colspan="2">
                图片搜索：
            </td>
        </tr>
        <tr class=itm>
            <!--td align="right" height="24">
                <input type="radio" name="type" value="0" <%//if(type==0){%>checked<%//}%>>本站点
            </td-->
            <!--td align="left" height="24">
                <input type="radio" name="type" value="1"
                                                           <%//if(type==1){%>checked<%//}%>>整个站点
            </td-->
            <td align="left" height="24">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;图片名称：<input type="text" name="picname" value="">
            </td>
        </tr>
        <tr class=itm>
            <td align="center" height="24" colspan="3">
                <input type="submit" name="Submit" value="搜索">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" name="button" value="关闭" onclick="top.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>