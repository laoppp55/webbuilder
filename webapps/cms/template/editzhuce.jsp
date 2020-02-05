<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.security.*,
                 org.jdom.*,
                 com.bizwink.cms.util.*,
                 org.jdom.input.*,
                 java.io.*"
         contentType="text/html;charset=utf-8"
        %>
<%@ page import="com.bizwink.cms.extendZhuce.ExtendZhuce" %>
<%@ page import="com.bizwink.cms.extendZhuce.IExtendZhuceManager" %>
<%@ page import="com.bizwink.cms.extendZhuce.ExtendZhucePeer" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int id = ParamUtil.getIntParameter(request, "id", 0);
    int siteid = authToken.getSiteID();
    String errormsg = "";
    boolean error = false;
    int columnID = ParamUtil.getIntParameter(request, "ID", 0);
    int from = ParamUtil.getIntParameter(request, "from", 0);   //from=1来自首页
    String act = ParamUtil.getParameter(request, "act");
    IColumnManager columnMgr = ColumnPeer.getInstance();


    //增加
    if (act != null && act.equals("doCreate")) {
        int width = 0;
        int height = 0;
        String cname = ParamUtil.getParameter(request, "cname");
        String ename = ParamUtil.getParameter(request, "ename");
        String type = ParamUtil.getParameter(request, "type");
        if (type.equals("文件上传")) {
            width = ParamUtil.getIntParameter(request, "width", 0);
            height = ParamUtil.getIntParameter(request, "height", 0);
        }

        if (cname == null || cname.trim().length() == 0 || ename == null || ename.trim().length() == 0) {
            error = true;
            errormsg = "<p align=center><font color=red><b>创建扩展属性出错！</b></font></p>";
        }


        if (!error) {
            ExtendZhuce exz = new ExtendZhuce();
            IExtendZhuceManager extendzhuceMgr = ExtendZhucePeer.getInstance();
            exz.setSiteid(siteid);
            exz.setCname(cname);
            exz.setEname(ename);
            exz.setType(type);
            id = extendzhuceMgr.insertExtendZhuce(exz);
        }
        //response.sendRedirect("editattr.jsp?act=doNothing&from="+from+"&ID="+columnID);
        out.println("<script language=javascript>var kuozhan='" + type + "," + cname + "," + ename + "," + id + "';var isMSIE = (navigator.appName == \"Microsoft Internet Explorer\");\n" +
                "            if (isMSIE) {\n" +
                "                window.returnValue = kuozhan;\n" +
                "                window.close();\n" +
                "            } else {\n" +
                "                window.parent.opener.top.InsertHTML('content',kuozhan);\n" +
                "                top.close();\n" +
                "            }window.close();</script>");
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <title>扩展属性</title>
    <script language=javascript>
        function check(frm)
        {
            if (frm.cname.value == "")
            {
                alert("扩展属性中文名称不能为空！");
                return false;
                cname.focus();
            }
            if (frm.ename.value == "")
            {
                alert("扩展属性英文名称不能为空！");
                return false;
                ename.focus();
            }
            if (!checkEname(frm.ename.value))
            {
                alert("请输入正确的英文名称！");
                return false;
                ename.focus();
            }
            return true;
        }

        function checkEname(ename)
        {
            var retstr = false;
            var regstr = /[^a-zA-Z0-9_]/gi;
            if (regstr.exec(ename) == null)
            {
                retstr = true;
            }
            return retstr;
        }

        function control()
        {
            if (document.all("type").value == "文件上传")
                picattr.style.display = "";
            else
                picattr.style.display = "none";
        }
    </script>
</head>
<base target="_self"/>
<body>
<form name="attr" method="POST" action="editzhuce.jsp?from=<%=from%>&ID=<%=columnID%>"
      onsubmit="javascript:return check(this);">
    <input type=hidden name=act value=doCreate>
    <input type=hidden name=id value=<%=id%>
    <table border="0" width="580" align="center">
        <tr>
            <td><%=errormsg%>
            </td>
        </tr>
        <tr height=80>
            <td width="100%">
                        <table border=0 cellspacing=2 cellpadding=2 width="100%">
        <tr>
            <td width="60%">
                中文名称：<input name="cname" size="15" class=tine>
                英文名称：<input name="ename" size="15" class=tine></td>
            <td width="40%">
                <div style="display:none" id=picattr>
                    <b>宽</b>：<input name="width" size="4" class=tine>
                    <b>高</b>：<input name="height" size="4" class=tine></div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                控件类型：<select size="1" name="type" class=tine style="width:102" onchange="control();">
                <option value="单行文本">单行文本</option>
                <option value="滚动文本">滚动文本</option>
                <option value="文件上传">文件上传</option>
                <option value="单选按钮">单选按钮</option>
                <option value="多选按钮">多选按钮</option>
            </select></td>
        </tr>
    </table>
    </td>
    </tr>

    <tr height=40>
        <td align=left>
            <input type="checkbox" name="attrscope" id="attrscopeid" value="0">适用本栏目下所有商品栏目
            <input type="submit" value="   保存   " name="submit" class=tine>&nbsp;&nbsp;
            <input type="button" value="   关闭   " name="close" class=tine onclick="window.close();">
        </td>
    </tr>
    <tr>
        <td><font color="red"><b>注意：请点击“保存”按钮，不要直接关闭！</b></font></td>
    </tr>
    </table>
</form>
</body>
</html>