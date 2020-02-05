<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp?url=member/createnewsite.jsp");
        return;
    }

    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int doSearchFlag = ParamUtil.getIntParameter(request, "searchflag", 0);
%>

<html>
<head>
    <title>站点增加</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script src="../js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script language="javascript">
        function check() {
            var pattern = /^([.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
            var SiteName = document.RegForm.SiteName.value.ltrim().rtrim()
            var username = document.RegForm.userid.value.ltrim().rtrim();
            var companyname = document.RegForm.companyname.value.ltrim().rtrim();
            var contactor = document.RegForm.contactor.value.ltrim().rtrim();
            var mphone = document.RegForm.mphone.value.ltrim().rtrim();

            if (companyname=="") {
                alert("公司名称不能为空！");
                return false;
            }

            if (contactor=="") {
                alert("公司联系人不能为空！");
                return false;
            }

            if (mphone=="") {
                alert("公司联系人电话不能为空！");
                return false;
            }

            if (username == ""){
                alert("用户登录帐号不能为空！");
                return false;
            } else {
                if (username.length <= 3){
                    alert("用户名长度必须3位以上");
                    return false;
                }

                htmlobj=$.ajax({url:"checkUserExist.jsp?uid=" + username,cache:false,async:false});
                statusval = htmlobj.responseText;
                if (statusval == 1) {
                    alert("该账号已经存在，请更换登录账号");
                    return false;
                }

                if (/[^\x00-\xff]/g.test(document.RegForm.userid.value.ltrim().rtrim())){
                    alert("用户登录帐号不能含有汉字");
                    return false;
                }
            }

            if (document.RegForm.password1.value == ""){
                alert("用户密码不能为空！");
                return false;
            } else {
                var regex = /[0-9]/;
                if(!regex.test(passwd)){
                    alert("密码必须包含数字");
                    return false;
                }

                regex = /[a-z]/;
                if(!regex.test(passwd)){
                    alert("密码必须包含小写字母");
                    return false;
                }

                regex = /[A-Z]/;
                if(!regex.test(passwd)){
                    alert("密码必须包含大写字母");
                    return false;
                }

                regex = /\W/;
                if(!regex.test(passwd)){
                    alert("密码必须包含特殊字符");
                    return false;
                }
            }

            if (document.RegForm.password2.value == ""){
                alert("确认密码不能为空！");
                return false;
            }

            if (document.RegForm.password1.value != document.RegForm.password2.value)
            {
                alert("两次输入的密码不相同！");
                return false;
            }

            if (document.RegForm.password1.value.length < 8)
            {
                alert("密码长度要求在8位或8位以上！");
                return false;
            }

            if (SiteName == ""){
                alert("域名不能为空！");
                return false;
            }
            if (SiteName.substring(0, 4).toLowerCase() == "http" || SiteName.indexOf(".") == -1){
                alert("域名格式不正确！正确格式如：www.bizwink.com.cn");
                return false;
            }

            if(document.RegForm.titlepicheight.value != ""){
                var titlepicheight =  document.RegForm.titlepicheight.value;
                if( !IsNum(titlepicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.titlepicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.titlepicwidth.value != ""){
                var titlepicwidth =  document.RegForm.titlepicwidth.value;
                if( !IsNum(titlepicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.titlepicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.vtitlepicheight.value != ""){
                var vtitlepicheight =  document.RegForm.vtitlepicheight.value;
                if( !IsNum(vtitlepicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.vtitlepicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.vtitlepicwidth.value != ""){
                var vtitlepicwidth =  document.RegForm.vtitlepicwidth.value;
                if( !IsNum(vtitlepicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.vtitlepicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.sourcepicheight.value != ""){
                var sourcepicheight =  document.RegForm.sourcepicheight.value;
                if( !IsNum(sourcepicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.sourcepicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.sourcepicwidth.value != ""){
                var sourcepicwidth =  document.RegForm.sourcepicwidth.value;
                if( !IsNum(sourcepicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.sourcepicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.authorpicheight.value != ""){
                var authorpicheight =  document.RegForm.authorpicheight.value;
                if( !IsNum(authorpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.authorpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.authorpicwidth.value != ""){
                var authorpicwidth =  document.RegForm.authorpicwidth.value;
                if( !IsNum(authorpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.authorpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.contentpicheight.value != ""){
                var contentpicheight =  document.RegForm.contentpicheight.value;
                if( !IsNum(contentpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.contentpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.contentpicwidth.value != ""){
                var contentpicwidth =  document.RegForm.contentpicwidth.value;
                if( !IsNum(contentpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.contentpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.specialpicheight.value != ""){
                var specialpicheight =  document.RegForm.specialpicheight.value;
                if( !IsNum(specialpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.specialpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.specialpicwidth.value != ""){
                var specialpicwidth =  document.RegForm.specialpicwidth.value;
                if( !IsNum(specialpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.specialpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.productpicheight.value != ""){
                var productpicheight =  document.RegForm.productpicheight.value;
                if( !IsNum(productpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.productpicwidth.value != ""){
                var productpicwidth =  document.RegForm.productpicwidth.value;
                if( !IsNum(productpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.productsmallpicheight.value != ""){
                var productsmallpicheight =  document.RegForm.productsmallpicheight.value;
                if( !IsNum(productsmallpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productsmallpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.productsmallpicwidth.value != ""){
                var productsmallpicwidth =  document.RegForm.productsmallpicwidth.value;
                if( !IsNum(productsmallpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productsmallpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.mediaheight.value != ""){
                var productpicheight =  document.RegForm.mediaheight.value;
                if( !IsNum(productpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediaheight.focus();
                    return false;
                }
            }

            if(document.RegForm.mediawidth.value != ""){
                var productpicwidth =  document.RegForm.mediawidth.value;
                if( !IsNum(productpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediawidth.focus();
                    return false;
                }
            }

            if(document.RegForm.mediapicheight.value != ""){
                var productsmallpicheight =  document.RegForm.mediapicheight.value;
                if( !IsNum(productsmallpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediapicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.mediapicwidth.value != ""){
                var productsmallpicwidth =  document.RegForm.mediapicwidth.value;
                if( !IsNum(productsmallpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediapicwidth.focus();
                    return false;
                }
            }

            return true;
        }

        function ltrim()
        {
            return this.replace(/ +/, "");
        }
        String.prototype.ltrim = ltrim;

        function rtrim()
        {
            return this.replace(/ +$/, "");
        }
        String.prototype.rtrim = rtrim;

        function IsNum(str)
        {
            var len = str.length;
            if (len == 0) return true;
            var bool = true;
            for (var i = 0; i < len; i++)
            {
                if (!(parseInt(str.substring(i, i + 1)) >= 0 && parseInt(str.substring(i, i + 1)) <= 9))
                {
                    bool = false;
                    break;
                }
            }
            if (!bool || str == "")
                return false;
            else
                return true;
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<form method="post" action="createnewsite.jsp" name="RegForm" onsubmit="javascript:return check();">
    <input type=hidden name="doCreate" value="true">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="pageno" value="<%=currentpage%>">
    <input type="hidden" name="searchflag" value="<%=doSearchFlag%>">
    <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00
           align=center>
        <tr>
            <td colspan=2 width="100%" bgcolor="#006699" align="center" height="16"><font
                    color="#FFFFFF"><b>所有选项必须正确填写</b></font></td>
        </tr>
        <tr height=32>
            <td width="38%" align="right">用户名：</td>
            <td width="62%">&nbsp;<input type="text" name="userid" maxlength="20" size="15" style="width: 156px">
                <br><font color=red>(用于登录的帐号，由字母和数字组成)</font></td>
        </tr>
        <tr height=32>
            <td align="right">密&nbsp; 码：</td>
            <td>&nbsp;<input type="password" name="password1" size="15" maxlength="20" style="width: 158px">
                <br><font color=red>(用于登录的密码，要求5位以上)</font></td>
        </tr>
        <tr height=32>
            <td align="right">确认密码：</td>
            <td>&nbsp;<input type="password" name="password2" size="15" maxlength="20" style="width: 155px"></td>
        </tr>
        <tr height=32>
            <td align="right">域&nbsp; 名：</td>
            <td>&nbsp;<input maxlength="50" name="SiteName" size="30" style="width: 239px">&nbsp;&nbsp;<font color=red>(*)</font></td>
        </tr>
        <tr height=32>
            <td width="38%" align="right">公司名称：</td>
            <td width="62%">&nbsp;<input type="text" name="companyname" maxlength="20" size="15" style="width: 156px">&nbsp;&nbsp;<font color=red>(*)</font></td>
        </tr>
        <tr height=32>
            <td width="38%" align="right">联系人：</td>
            <td width="62%">&nbsp;<input type="text" name="contactor" maxlength="20" size="15" style="width: 156px">&nbsp;&nbsp;<font color=red>(*)</font></td>
        </tr>
        <tr height=32>
            <td width="38%" align="right">手机号码：</td>
            <td width="62%">&nbsp;<input type="text" name="mphone" maxlength="20" size="15" style="width: 156px">&nbsp;&nbsp;<font color=red>(*)</font></td>
        </tr>
        <tr height=32>
            <td align="right">样式脚本文件存储方式：</td>
            <td>
                <input type="radio" value="0" name="cssjsdir">根图片目录（存储在/images目录）
                <input type="radio" value="1" name="cssjsdir" checked>根独立目录(存储在/js目录和/css目录)
                <input type="radio" value="2" name="cssjsdir">各栏目独立目录
            </td>
        </tr>
        <tr height=32>
            <td align="right">图像存储方式：</td>
            <td><input type="radio" value="0" name="pic" checked>根目录保存
                <input type="radio" value="1" name="pic">各栏目目录保存
            </td>
        </tr>
        <tr height=32>
            <td align="right">页面编码：</td>
            <td>&nbsp;<select name=encoding size=1 class=tine>
                <option value="1">UTF-8</option>
                <option value="2">GB2312</option>
                <option value="3">GBK</option>
            </select>
            </td>
        </tr>
        <tr height=32>
            <td align="right">支持繁体：</td>
            <td><input type="radio" value="0" name="tcflag" checked>否&nbsp;
                <input type="radio" value="1" name="tcflag">是
            </td>
        </tr>
        <tr height=32>
            <td align="right">文章发布方式：</td>
            <td><input type="radio" value="0" name="pubflag" checked>手动&nbsp;
                <input type="radio" value="1" name="pubflag">自动
            </td>
        </tr>
        <tr height=32>
            <td align="right">首页扩展名：</td>
            <td>&nbsp;<select name=extname size=1 class=tine>
                <option value="shtml">shtml</option>
                <option value="shtm">shtm</option>
                <option value="php">php</option>
                <option value="html">html</option>
                <option value="htm">htm</option>
                <option value="jsp">jsp</option>
                <option value="asp">asp</option>
            </select>
            </td>
        </tr>
        <tr height=32>
            <td align="right">标题图片大小：</td>
            <td>&nbsp;高度：<input name="titlepicheight" type="text" size="3">px &nbsp; 宽度：<input name="titlepicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">副标题图片大小：</td>
            <td>&nbsp;高度：<input name="vtitlepicheight" type="text" size="3">px &nbsp; 宽度：<input name="vtitlepicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">来源图片大小：</td>
            <td>&nbsp;高度：<input name="sourcepicheight" type="text" size="3">px &nbsp; 宽度：<input name="sourcepicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">作者图片大小：</td>
            <td>&nbsp;高度：<input name="authorpicheight" type="text" size="3">px &nbsp; 宽度：<input name="authorpicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">内容图片大小：</td>
            <td>&nbsp;高度：<input name="contentpicheight" type="text" size="3">px &nbsp; 宽度：<input name="contentpicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">特效图片大小：</td>
            <td>&nbsp;高度：<input name="specialpicheight" type="text" size="3">px &nbsp; 宽度：<input name="specialpicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">商品大图片大小：</td>
            <td>&nbsp;高度：<input name="productpicheight" type="text" size="3">px &nbsp; 宽度：<input name="productpicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=32>
            <td align="right">商品小图片大小：</td>
            <td>&nbsp;高度：<input name="productsmallpicheight" type="text" size="3">px &nbsp; 宽度：<input name="productsmallpicwidth" type="text" size="3">px</td>
        </tr>
        <tr height=20 id="mediafile_id">
            <td align=right class=line>视频文件:</td>
            <td class=tine>&nbsp;高度：<input name="mediaheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="mediawidth" type="text" size="3" value="">px
            </td>
        </tr>
        <tr height=20 id="mediapic_id">
            <td align=right class=line>视频缩略图:</td>
            <td class=tine>&nbsp;高度：<input name="mediapicheight" type="text" size="3" value="">px &nbsp; 宽度：<input name="mediapicwidth" type="text" size="3" value="">px
            </td>
        </tr>
    </table>
    <p align="center"><input type="submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;
        <input type="button" value=" 取消 " onclick="window.close()"></p>
</form>

</body>
</html>