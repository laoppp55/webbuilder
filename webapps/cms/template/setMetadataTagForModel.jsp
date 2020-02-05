<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/7
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
%>
<html>
<head>
    <title>设置模板页页头标签</title>
    <style type="text/css">
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            font-size: 12px;
        }
    </style>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script>
        //〈meta name=“SiteName” content=“中国政府网”〉
        //〈meta name=“SiteDomain”content=“www.gov.cn”〉
        //〈meta name=“SiteIDCode” content=“bm01000001”〉
        //〈meta name=“ColumnName” content=“政策”〉
        //〈meta name=“ColumnDescription” content=“中国政府网政策栏目发布中央和地方政府制定的法规，政策文件，中共中央有关文件，国务院公报，政府白皮书，政府信息公开，政策解读等。提供法律法规和已发布的文件的查询功能”〉
        //〈meta name=“ColumnKeywords” content=“国务院文件，行政法规，部门规章，中央文件，政府白皮书，国务院公报，政策专辑”〉
        //〈meta name=“ColumnType” content=“政策文件”〉
        $(document).ready(function(){
            var editor = window.opener.CKEDITOR.instances.content;
            var data = editor.getData();
            var reg_for_sitename = /<\s*meta\s*name=\s*"SiteName"\s*content\s*=\s*[^>]*>/;
            var reg_for_domainname = /<\s*meta\s*name=\s*"SiteDomain"\s*content\s*=\s*[^>]*>/;
            var reg_for_sitecode = /<\s*meta\s*name=\s*"SiteIDCode"\s*content\s*=\s*[^>]*>/;
            var reg_for_columnname = /<\s*meta\s*name=\s*"ColumnName"\s*content\s*=\s*[^>]*>/;
            var reg_for_summary = /<\s*meta\s*name=\s*"ColumnDescription"\s*content\s*=\s*[^>]*>/;
            var reg_for_keyword = /<\s*meta\s*name=\s*"ColumnKeywords"\s*content\s*=\s*[^>]*>/;
            var reg_for_columntype = /<\s*meta\s*name\s*=\s*"ColumnType"\s*content\s*=\s*[^>]*>/;

            var posi = 0;
            var buf = null;
            var r = data.match(reg_for_columntype);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.coltype.value=buf;
            }

            var r = data.match(reg_for_keyword);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.colkw.value = buf;
            }

            var r = data.match(reg_for_summary);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.summary.value = buf;
            }

            var r = data.match(reg_for_columnname);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.colname.value=buf;
            }

            var r = data.match(reg_for_sitecode);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.sitecode.value = buf;
            }

            var r = data.match(reg_for_domainname);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.domainname.value = buf;
            }

            var r = data.match(reg_for_sitename);
            if (r != null) {
                posi = r[0].lastIndexOf("=");
                buf = r[0].substring(posi+1,r[0].length-1);
                if (buf[0]=="\"") buf = buf.substr(1);
                if (buf[buf.length-1] == "\"")buf = buf.substr(0,buf.length-1);
                metaform.sitename.value = buf;
            }
        });

        function tijiao() {
            var sitename = metaform.sitename.value;
            var domainname = metaform.domainname.value;
            var sitecode = metaform.sitecode.value;
            var columnname = metaform.colname.value;
            var summary = metaform.summary.value;
            var keyword = metaform.colkw.value;
            var columntype = metaform.coltype.value;

            var errorcode = 0;

            if (sitename === null || sitename === "") {
                alert("站点名称不能为空，请输入站点名称");
                errorcode = -1;
                return;
            }

            if (domainname===null || domainname==="") {
                alert("站点域名不能为空，请输入站点域名");
                errorcode = -1;
                return;
            }

            if (sitecode===null || sitecode==="") {
                alert("站点编码不能为空，请输入站点编码");
                errorcode = -1;
                return;
            }

            if (columnname===null || columnname==="") {
                alert("栏目名称不能为空，请输入栏目名称");
                errorcode = -1;
                return;
            }

            if (summary===null || summary==="") {
                alert("栏目描述不能为空，请输入栏目描述");
                errorcode = -1;
                return;
            }

            if (keyword===null || keyword==="") {
                alert("栏目关键字不能为空，请输入栏目关键字");
                errorcode = -1;
                return;
            }

            if (columntype===null || columntype==="") {
                alert("栏目类型描述不能为空，请输入栏目类型描述");
                errorcode = -1;
                return;
            }

            if(errorcode === 0) {
                var editor = window.opener.CKEDITOR.instances.content;
                var data = editor.getData();
                var reg_for_sitename = /<\s*meta\s*name=\s*"SiteName"\s*content\s*=\s*[^>]*>/;
                var reg_for_domainname = /<\s*meta\s*name=\s*"SiteDomain"\s*content\s*=\s*[^>]*>/;
                var reg_for_sitecode = /<\s*meta\s*name=\s*"SiteIDCode"\s*content\s*=\s*[^>]*>/;
                var reg_for_columnname = /<\s*meta\s*name=\s*"ColumnName"\s*content\s*=\s*[^>]*>/;
                var reg_for_summary = /<\s*meta\s*name=\s*"ColumnDescription"\s*content\s*=\s*[^>]*>/;
                var reg_for_keyword = /<\s*meta\s*name=\s*"ColumnKeywords"\s*content\s*=\s*[^>]*>/;
                var reg_for_columntype = /<\s*meta\s*name=\s*"ColumnType"\s*content\s*=\s*[^>]*>/;

                //找到<title>标签所在的位置
                var reg = /<title>[^>]*<\/title>/;
                var title_tag = data.match(reg);

                if (title_tag == null)
                    alert("页面标题元素为空，请为页面设置标题元素");
                else {
                    var r = data.match(reg_for_columntype);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"ColumnType\" content=\"" + columntype + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"ColumnType\" content=\"" + columntype + "\">");
                    }

                    r = data.match(reg_for_keyword);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"ColumnKeywords\" content=\"" + keyword + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"ColumnKeywords\" content=\"" + keyword + "\">");
                    }

                    r = data.match(reg_for_summary);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"ColumnDescription\" content=\"" + summary + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"ColumnDescription\" content=\"" + summary + "\">");
                    }

                    r = data.match(reg_for_columnname);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"ColumnName\" content=\"" + columnname + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"ColumnName\" content=\"" + columnname + "\">");
                    }

                    r = data.match(reg_for_sitecode);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"SiteIDCode\" content=\"" + sitecode + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"SiteIDCode\" content=\"" + sitecode + "\">");
                    }

                    r = data.match(reg_for_domainname);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"SiteDomain\" content=\"" + domainname + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"SiteDomain\" content=\"" + domainname + "\">");
                    }

                    r = data.match(reg_for_sitename);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"SiteName\" content=\"" + sitename + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"SiteName\" content=\"" + sitename + "\">");
                    }

                    if (editor.mode == 'wysiwyg') {
                        editor.setData(data);
                    } else {
                        alert('你必须处于可视化模式才能进行内容编辑!');
                        return;
                    }
                    window.close();
                }
            }
        }
    </script>
</head>
<body>
<div align="center">
    <form name="metaform">
        <table width="100%" border="0" cellpadding="0" cellspacing="5">
            <tr>
                <td width="20%" align="right">站点名称：</td>
                <td align="left" width="30%">
                    <input type="text" name="sitename" id="sitenameid" size="50"/>
                </td>
                <td width="20%" align="right">站点域名：</td>
                <td align="left" width="30%">
                    <input type="text" name="domainname" id="domainnameid"  size="50" /></td>
            </tr>
            <tr>
                <td width="20%" align="right">站点编码：</td>
                <td align="left">
                    <input type="text" name="sitecode" id="sitecodeid"  size="50" />
                </td>
                <td width="20%" align="right">栏目名称：</td>
                <td align="left">
                    <input type="text" name="colname" id="colnameid"  size="50" /></td>
            </tr>
            <tr>
                <td width="20%" align="right">栏目描述：</td>
                <td align="left">
                    <input type="text" name="summary" id="summaryid"  size="50" />
                </td>
                <td width="20%" align="right">栏目关键词：</td>
                <td align="left"><input type="text" name="colkw" id="colkwid" size="50" /></td>
            </tr>
            <tr>
                <td width="20%" align="right">栏目类型：</td>
                <td align="left">
                    <input type="text" name="coltype" id="coltypeid" size="50" />
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td height="40" colspan="4" align="center" valign="middle">
                    <input type="button" name="doSave" id="doSaveid" value="提交" onclick="javascript:tijiao();"/>
                    <input type="button" name="doCancel" id="doCancelid" value="取消" onclick="javascript:window.close();"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
