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
        //<meta name=“SiteName” content=“中国政府网”>
        //<meta name=“SiteDomain”content=“www.gov.cn”>
        //<meta name=“SiteIDCode” content=“bm01000001”>
        //<meta name=“ColumnName” content=“要闻”>
        //<meta name=“ColumnType” content=“工作动态”>
        //<meta name=“ArticleTitle” content=“今天的国务院常务会议定了这3件大事”>
        //<meta name=“PubDate” content=“2017—04—12 21∶37”>
        //<meta name=“ContentSource” content=“中国政府网”>
        //<meta name=“Keywords” content=“国务院常务会，医疗联合体，中小学，幼儿园，安全风险防控，统计法”>
        //<meta name=“Author”content=“陆茜”>
        //<meta name=“Description” content=“部署推进医疗联合体建设，部署加强中小学幼儿园安全风险防控体系建设，通过《中华人民共和国统计法实施条例（草案）》。4月12日的国务院常务会定了这3件大事，会上，李克强总理对这些工作作出了哪些部署？”>
        //<meta name=“Url”content=“www.gov.cn/xinwen/2017—04/12/content_5185257.htm”>
        $(document).ready(function(){
            var editor = window.opener.CKEDITOR.instances.content;
            var data = editor.getData();
            var reg_for_sitename = /<\s*meta\s*name=\s*"SiteName"\s*content\s*=\s*[^>]*>/;
            var reg_for_domainname = /<\s*meta\s*name=\s*"SiteDomain"\s*content\s*=\s*[^>]*>/;
            var reg_for_sitecode = /<\s*meta\s*name=\s*"SiteIDCode"\s*content\s*=\s*[^>]*>/;
            var reg_for_columnname = /<\s*meta\s*name=\s*"ColumnName"\s*content\s*=\s*[^>]*>/;
            var reg_for_columntype = /<\s*meta\s*name\s*=\s*"ColumnType"\s*content\s*=\s*[^>]*>/;

            var reg_for_title = /<\s*meta\s*name\s*=\s*"ArticleTitle"\s*content\s*=\s*[^>]*>/;
            var reg_for_pubdate = /<\s*meta\s*name\s*=\s*"PubDate"\s*content\s*=\s*[^>]*>/;
            var reg_for_keywords = /<\s*meta\s*name\s*=\s*"Keywords"\s*content\s*=\s*[^>]*>/;
            var reg_for_author = /<\s*meta\s*name\s*=\s*"Author"\s*content\s*=\s*[^>]*>/;
            var reg_for_disc = /<\s*meta\s*name\s*=\s*"Description"\s*content\s*=\s*[^>]*>/;
            var reg_for_url = /<\s*meta\s*name\s*=\s*"Url"\s*content\s*=\s*[^>]*>/;

            var r = data.match(reg_for_url);
            if (r == null) $("#ckurlid").attr("checked",false);
            r = data.match(reg_for_author);
            if (r == null) $("#ckauthorid").attr("checked",false);
            r = data.match(reg_for_pubdate);
            if (r == null) $("#ckpubdateid").attr("checked",false);
            r = data.match(reg_for_disc);
            if (r == null) $("#cksummaryid").attr("checked",false);
            r = data.match(reg_for_keywords);
            if (r == null) $("#kwid").attr("checked",false);
            r = data.match(reg_for_title);
            if (r == null) $("#titleid").attr("checked",false);

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
            var columntype = metaform.coltype.value;

            var errorcode = 0;
            if (sitename===null || sitename==="") {
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
                var reg_for_columntype = /<\s*meta\s*name=\s*"ColumnType"\s*content\s*=\s*[^>]*>/;

                var reg_for_title = /<\s*meta\s*name\s*=\s*"ArticleTitle"\s*content\s*=\s*[^>]*>/;
                var reg_for_source = /<\s*meta\s*name\s*=\s*"ContentSource"\s*content\s*=\s*[^>]*>/;
                var reg_for_keywords = /<\s*meta\s*name\s*=\s*"Keywords"\s*content\s*=\s*[^>]*>/;
                var reg_for_disc = /<\s*meta\s*name\s*=\s*"Description"\s*content\s*=\s*[^>]*>/;
                var reg_for_pubdate = /<\s*meta\s*name\s*=\s*"PubDate"\s*content\s*=\s*[^>]*>/;
                var reg_for_author = /<\s*meta\s*name\s*=\s*"Author"\s*content\s*=\s*[^>]*>/;
                var reg_for_url = /<\s*meta\s*name\s*=\s*"Url"\s*content\s*=\s*[^>]*>/;

                //找到<title>标签所在的位置
                var reg = /<title>[^>]*<\/title>/;
                var title_tag = data.match(reg);

                if (title_tag == null)
                    alert("页面标题元素为空，请为页面设置标题元素");
                else {
                    var r = data.match(reg_for_url);
                    var check = $("#ckurlid").is(":checked");
                    if (check) {
                        if (r==null) {
                            var posi = data.indexOf(title_tag);
                            var buf_l = data.substring(0,posi+title_tag[0].length);
                            var buf_r = data.substring(posi+title_tag[0].length);
                            data = buf_l + "<meta name=\"Url\" content=\"[TAG][ARTICLE_URL][/ARTICLE_URL][/TAG]\">" + buf_r;
                        }
                    } else {
                        if (r!=null) {
                            data = data.replace(r[0],"");
                        }
                    }

                    r = data.match(reg_for_author);
                    check = $("#ckauthorid").is(":checked");
                    if (check) {
                        if (r==null) {
                            var posi = data.indexOf(title_tag);
                            var buf_l = data.substring(0,posi+title_tag[0].length);
                            var buf_r = data.substring(posi+title_tag[0].length);
                            data = buf_l + "<meta name=\"Author\" content=\"[TAG][ARTICLE_AUTHOR][/ARTICLE_AUTHOR][/TAG]\">" + buf_r;
                        }
                    } else {
                        if (r!=null) {
                            data = data.replace(r[0],"");
                        }
                    }

                    r = data.match(reg_for_pubdate);
                    check = $("#ckpubdateid").is(":checked");
                    if (check) {
                        if (r==null) {
                            var posi = data.indexOf(title_tag);
                            var buf_l = data.substring(0,posi+title_tag[0].length);
                            var buf_r = data.substring(posi+title_tag[0].length);
                            data = buf_l + "<meta name=\"PubDate\" content=\"[TAG][ARTICLE_PULISHDATE][/ARTICLE_PULISHDATE][/TAG]\">" + buf_r;
                        }
                    } else {
                        if (r!=null) {
                            data = data.replace(r[0],"");
                        }
                    }

                    r = data.match(reg_for_disc);
                    check = $("#cksummaryid").is(":checked");
                    if (check) {
                        if (r==null) {
                            var posi = data.indexOf(title_tag);
                            var buf_l = data.substring(0,posi+title_tag[0].length);
                            var buf_r = data.substring(posi+title_tag[0].length);
                            data = buf_l + "<meta name=\"Description\" content=\"[TAG][ARTICLE_SUMMARY][/ARTICLE_SUMMARY][/TAG]\">" + buf_r;
                        }
                    } else {
                        if (r!=null) {
                            data = data.replace(r[0],"");
                        }
                    }

                    r = data.match(reg_for_keywords);
                    check = $("#kwid").is(":checked");
                    if (check) {
                        if (r==null) {
                            var posi = data.indexOf(title_tag);
                            var buf_l = data.substring(0,posi+title_tag[0].length);
                            var buf_r = data.substring(posi+title_tag[0].length);
                            data = buf_l + "<meta name=\"Keywords\" content=\"[TAG][ARTICLE_KEYWORD][/ARTICLE_KEYWORD][/TAG]\">" + buf_r;
                        }
                    } else {
                        if (r!=null) {
                            data = data.replace(r[0],"");
                        }
                    }

                    r = data.match(reg_for_source);
                    if (r==null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"ContentSource\" content=\"[TAG][ARTICLE_SOURCE][/ARTICLE_SOURCE][/TAG]\">" + buf_r;
                    }

                    r = data.match(reg_for_title);
                    check = $("#titleid").is(":checked");
                    if (check) {
                        if (r==null) {
                            var posi = data.indexOf(title_tag);
                            var buf_l = data.substring(0,posi+title_tag[0].length);
                            var buf_r = data.substring(posi+title_tag[0].length);
                            data = buf_l + "<meta name=\"ArticleTitle\" content=\"[TAG][ARTICLE_MAINTITLE][/ARTICLE_MAINTITLE][/TAG]\">" + buf_r;
                        }
                    } else {
                        if (r!=null) {
                            data = data.replace(r[0],"");
                        }
                    }

                    r = data.match(reg_for_columntype);
                    if (r == null) {
                        var posi = data.indexOf(title_tag);
                        var buf_l = data.substring(0,posi+title_tag[0].length);
                        var buf_r = data.substring(posi+title_tag[0].length);
                        data = buf_l + "<meta name=\"ColumnType\" content=\"" + columntype + "\">" + buf_r;
                    } else{
                        data = data.replace(r[0],"<meta name=\"ColumnType\" content=\"" + columntype + "\">");
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
<form name="metaform">
    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="5">
        <tr>
            <td width="20%" align="right">站点名称：</td>
            <td align="left">
                <input type="text" name="sitename" id="sitenameid" size="50"/>
            </td>
            <td width="20%" align="right">站点域名：</td>
            <td align="left">
                <input type="text" name="domainname" id="domainnameid" size="50"/>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">站点编码：</td>
            <td align="left">
                <input type="text" name="sitecode" id="sitecodeid" size="50"/>
            </td>
            <td width="20%" align="right">栏目名称：</td>
            <td align="left">
                <input type="text" name="colname" id="colnameid" size="50"/>
            </td>
        </tr>
        <tr>
            <td width="20%" align="right">栏目类型：</td>
            <td align="left">
                <input type="text" name="coltype" id="coltypeid" size="50"/>
            </td>
            <td width="20%"></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td width="5%"  align="right"><input type="checkbox" name="title" id="titleid" checked/></td>
            <td>文章标题</td>
            <td width="5%" height="40"  align="right"><input type="checkbox" name="source" id="cksource" checked disabled/></td>
            <td>内容来源</td>
        </tr>
        <tr>
            <td width="5%"  align="right"><input type="checkbox" name="keyword" id="kwid" checked/></td>
            <td>文章关键词</td>
            <td width="5%"  align="right"><input type="checkbox" name="summary" id="cksummaryid" checked/></td>
            <td>描述</td>
        </tr>
        <tr>
            <td width="5%" align="right"><input type="checkbox" name="pubdate" id="ckpubdateid" checked/></td>
            <td>发布时间</td>
            <td width="5%" height="40" align="right"><input type="checkbox" name="author" id="ckauthorid" checked/></td>
            <td>作者</td>
        </tr>
        <tr>
            <td width="5%" height="40" align="right"><input type="checkbox" name="url" id="ckurlid" checked/></td>
            <td>文章网址</td>
            <td width="5%" height="40" align="right"></td>
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
</body>
</html>
