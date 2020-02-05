<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-15
  Time: 下午10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean errors = false;
    boolean success = false;
    boolean errorCName = false;
    boolean errorEName = false;
    String CName = "";
    String EName = "";

    int parentID = ParamUtil.getIntParameter(request, "parentID", 0);
    session.setAttribute("Current_URL", request.getRequestURI());
    int siteid = authToken.getSiteID();
    Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
    int rootID = colTree.getTreeRoot();

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = new Column();
    column = columnManager.getColumn(parentID);
    CName = column.getCName();
    EName = column.getEName();
    String parentName = StringUtil.gb2iso4View(column.getCName());

    //获得首页文件扩展名
    String extname = columnManager.getIndexExtName(siteid);

%>
<html>
<head>
    <title></title>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language=javascript>
        function editAttr(){
            var bln = confirm("您要继承父栏目的扩展属性吗？");
            if (!bln)
                window.open("editnewattr.jsp", "", "width=1000,height=600,left=200,right=200,scrollbars,status");
        }

        function check(frm)
        {
            var cname = frm.CName.value;
            if (cname == "")
            {
                alert("栏目中文名称不能为空！");
                return false;
            }
            if (cname.indexOf(",") > -1)
            {
                alert("栏目中文名称中不能含有逗号！");
                return false;
            }
            var ename = frm.EName.value;
            if (ename == "")
            {
                alert("栏目英文名称不能为空！");
                return false;
            }
            else if (!checkEname(ename))
            {
                alert("栏目英文名称不合法！应该由字母、数字及下划线组成！");
                return false;
            }

            if (frm.userflag[1].checked) {
                var num = frm.userlevel.value;
                if (!isNumber(num)) {
                    alert("用户级别必须是整数！");
                    frm.userlevel.focus();
                    return false;
                }
            }
            if(frm.titlepicheight.value != ""){
                if( !isNumber(frm.titlepicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.titlepicheight.focus();
                    return false;
                }
            }
            if(frm.titlepicwidth.value != ""){
                if( !isNumber(frm.titlepicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.titlepicwidth.focus();
                    return false;
                }
            }
            if(frm.vtitlepicheight.value != ""){
                if( !isNumber(frm.vtitlepicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.vtitlepicheight.focus();
                    return false;
                }
            }
            if(frm.vtitlepicheight.value != ""){
                if( !isNumber(frm.vtitlepicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.vtitlepicheight.focus();
                    return false;
                }
            }
            if(frm.sourcepicheight.value != ""){
                if( !isNumber(frm.sourcepicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.sourcepicheight.focus();
                    return false;
                }
            }
            if(frm.sourcepicwidth.value != ""){
                if( !isNumber(frm.sourcepicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.sourcepicwidth.focus();
                    return false;
                }
            }
            if(frm.authorpicheight.value != ""){
                if( !isNumber(frm.authorpicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.authorpicheight.focus();
                    return false;
                }
            }
            if(frm.authorpicwidth.value != ""){
                if( !isNumber(frm.authorpicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.authorpicwidth.focus();
                    return false;
                }
            }
            if(frm.contentpicheight.value != ""){
                if( !isNumber(frm.contentpicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.contentpicheight.focus();
                    return false;
                }
            }
            if(frm.contentpicwidth.value != ""){
                if( !isNumber(frm.contentpicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.contentpicwidth.focus();
                    return false;
                }
            }
            if(frm.specialpicheight.value != ""){
                if( !isNumber(frm.specialpicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.specialpicheight.focus();
                    return false;
                }
            }
            if(frm.specialpicwidth.value != ""){
                if( !isNumber(frm.specialpicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.specialpicwidth.focus();
                    return false;
                }
            }
            if(frm.productpicheight.value != ""){
                if( !isNumber(frm.productpicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.titlepicheight.focus();
                    return false;
                }
            }
            if(frm.productpicwidth.value != ""){
                if( !isNumber(frm.productpicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.productpicwidth.focus();
                    return false;
                }
            }
            if(frm.productsmallpicheight.value != ""){
                if( !isNumber(frm.productsmallpicheight.value)){
                    alert("请输入正整数 如:36");
                    frm.productsmallpicheight.focus();
                    return false;
                }
            }
            if(frm.productsmallpicwidth.value != ""){
                if( !isNumber(frm.productsmallpicwidth.value)){
                    alert("请输入正整数 如:36");
                    frm.productsmallpicwidth.focus();
                    return false;
                }
            }

            $.post("/column/addNewColumn.jsp",{
                        columnid:colid,
                        content:content,
                        topicname:topicname,
                        cname:cname,
                        siteid:siteid
                    },
                    function(data) {
                        //var value=data.errcode;
                        //var text=data.errmsg;
                        //var modelname = data.modelname;
                        alert(value +"===" + text + "==="+modelname);
                    },
                    "json"
            )
        }

        function isNumber(num) {
            strRef = "1234567890";
            for (i = 0; i < num.length; i++) {
                tempChar = num.substring(i, i + 1);
                if (strRef.indexOf(tempChar, 0) == -1) {
                    return false;
                }
            }
            return true;
        }

        function checkEname(str) {
            var regstr = /[^0-9a-zA-Z_]/gi;
            if (regstr.exec(str) != null)
                return false;
            else
                return true;
        }

        function Upload() {
            window.open("../upload/upload.jsp?column=<%=rootID%>&attr=createDesc", "Upload", "width=400,height=200,left=200,top=200");
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
<%@ include file="../inc/titlebar.jsp" %>
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
    <form action="addNewColumn.jsp" method="post" name="createForm" onsubmit="return check(this);">
        <input type="hidden" name="doCreate" value="true">
        <input type="hidden" name="parentID" value="<%=parentID%>">
        <input type=hidden name="xmlTemplate" value="">
        <input type=hidden name="extattrscope" value="0">
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
            <tr height=30>
                <td class=line align=right>文件扩展名：</td>
                <td class=tine>&nbsp;<select name=extfilename size=1 class=tine style="width:100">
                    <option value="html" <%if(extname.equalsIgnoreCase("html")){%>selected<%}%>>html</option>
                    <option value="htm" <%if(extname.equalsIgnoreCase("htm")){%>selected<%}%>>htm</option>
                    <option value="jsp" <%if(extname.equalsIgnoreCase("jsp")){%>selected<%}%>>jsp</option>
                    <option value="asp" <%if(extname.equalsIgnoreCase("asp")){%>selected<%}%>>asp</option>
                    <option value="shtm" <%if(extname.equalsIgnoreCase("shtm")){%>selected<%}%>>shtm</option>
                    <option value="shtml" <%if(extname.equalsIgnoreCase("shtml")){%>selected<%}%>>shtml</option>
                    <option value="php" <%if(extname.equalsIgnoreCase("php")){%>selected<%}%>>php</option>
                    <option value="wml" <%if(extname.equalsIgnoreCase("wml")){%>selected<%}%>>wml</option>
                </select></td>
            </tr>
            <tr height=30>
                <td class=line align=right>栏目语言类型：</td>
                <td class=tine>&nbsp;<select name=languageType size=1 class=tine style="width:100">
                    <option value="0" selected>简体中文</option>
                    <option value="1">繁体中文</option>
                    <option value="2">日文</option>
                </select>
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>展示形式：</td>
                <td class=tine>&nbsp;<input type="radio" name="showtype" value="0" checked> WEB
                    <input type="radio" name="showtype" value="1"> WAP和WEB
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>是否需要审核：</td>
                <td class=tine>&nbsp;<input type=radio name="isAudited" checked value="0">否&nbsp;&nbsp;
                    <input type=radio name="isAudited" value="1">是
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>是否需要扩展属性：</td>
                <td class=tine>&nbsp;<input type=radio name="isDefineAttr" value="0" checked>否&nbsp;&nbsp;
                    <input type=radio name="isDefineAttr" value="1" onclick="editAttr();">是
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>是否接受网站注册用户输入信息：</td>
                <td class=tine>&nbsp;<input type=radio name="publicflag" checked value="0">否&nbsp;&nbsp;
                    <input type=radio name="publicflag" value="1">是
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>地理位置信息：</td>
                <td class=tine>&nbsp;<input type=radio name="islocation" checked value="0">否&nbsp;&nbsp;
                    <input type=radio name="islocation" value="1">是
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>发布多个文章模板：</td>
                <td class=tine>&nbsp;<input type=radio name="isPublishMore" checked value="0">否&nbsp;&nbsp;
                    <input type=radio name="isPublishMore" value="1">是
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>栏目属性：</td>
                <td class=tine>
                    &nbsp;<input type=radio name="isProduct" value="0" checked>文章
                    <input type=radio name="isProduct" value="1">商品
                </td>
            </tr>
            <tr height=30>
                <td class=line align=right>栏目文章权限控制：</td>
                <td class=tine>
                    &nbsp;<input type=radio name="userflag" value="0" onclick='javascript:userlevel.readOnly=true;'
                                 checked>所有用户可见
                    <input type=radio name="userflag" value="1" onclick='javascript:userlevel.readOnly=false;'>只有注册用户可见&nbsp;&nbsp;
                    用户级别：
                    <select name="userlevel" id="userlevel">
                        <option value="-1">---请选择---</option>
                        <option value="0">普通用户</option>
                        <option value="1">内部用户</option>
                        <option value="2">企业用户</option>
                        <option value="3">VIP用户</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class=line align=right>栏目描述：</td>
                <td class=tine>&nbsp;<textarea class=tine rows="4" name="desc" cols="40"></textarea>
                    <a href="javascript:Upload();">图片</a>
                </td>
            </tr>

            <tr height=20>
                <td align=right class=line>标题图片大小：</td>
                <td class=tine>&nbsp;高度：<input name="titlepicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="titlepicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>副标题图片大小：</td>
                <td class=tine>&nbsp;高度：<input name="vtitlepicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="vtitlepicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>来源图片大小：</td>
                <td class=tine>&nbsp;高度：<input name="sourcepicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="sourcepicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>作者图片大小：</td>
                <td class=tine>&nbsp;高度：<input name="authorpicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="authorpicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>内容图片大小：</td>
                <td class=tine>&nbsp;高度：<input name="contentpicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="contentpicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>文章特效图片大小：</td>
                <td class=tine>&nbsp;高度：<input name="specialpicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="specialpicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>商品大图片大小:</td>
                <td class=tine>&nbsp;高度：<input name="productpicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="productpicwidth" type="text" size="3" value="">px
                </td>
            </tr>
            <tr height=20>
                <td align=right class=line>商品小图片大小:</td>
                <td class=tine>&nbsp;高度：<input name="productsmallpicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="productsmallpicwidth" type="text" size="3" value="">px
                </td>
            </tr>

            <!--tr height=32>
                <td align="right">附图特小图片大小：</td>
                <td>&nbsp;高度：<input name="ts_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="ts_pic_w" type="text" size="3">px</td>
            </tr>
            <tr height=32>
                <td align="right">附图小图片大小：</td>
                <td>&nbsp;高度：<input name="s_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="s_pic_w" type="text" size="3">px</td>
            </tr>
            <tr height=32>
                <td align="right">附图中小图片大小：</td>
                <td>&nbsp;高度：<input name="ms_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="ms_pic_w" type="text" size="3">px</td>
            </tr>
            <tr height=32>
                <td align="right">附图中图片大小：</td>
                <td>&nbsp;高度：<input name="m_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="m_pic_w" type="text" size="3">px</td>
            </tr>
            <tr height=32>
                <td align="right">附图中大图片大小：</td>
                <td>&nbsp;高度：<input name="ml_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="ml_pic_w" type="text" size="3">px</td>
            </tr>
            <tr height=32>
                <td align="right">附图大图片大小：</td>
                <td>&nbsp;高度：<input name="l_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="l_pic_w" type="text" size="3">px</td>
            </tr>
            <tr height=32>
                <td align="right">附图特大图片大小：</td>
                <td>&nbsp;高度：<input name="tl_pic_h" type="text" size="3">px &nbsp; 宽度：<input name="tl_pic_w" type="text" size="3">px</td>
            </tr-->

        </table>
        <br>
        <input type=submit value="  保存  ">&nbsp;&nbsp;&nbsp;&nbsp;
        <input type=button value="  返回  " onclick="javascript:window.close();">
    </form>
</center>
</body>
</html>
