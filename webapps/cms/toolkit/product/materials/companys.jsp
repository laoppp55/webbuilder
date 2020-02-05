<%@ page import="java.util.*,
                 com.bizwink.cms.toolkit.companyinfo.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.server.CmsServer" contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.Map" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%@ page import="com.bizwink.cms.tree.*" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int msgno = ParamUtil.getIntParameter(request, "msgno", 1);
    boolean doSearch = ParamUtil.getBooleanParameter(request, "doSearch");
    int siteid=authToken.getSiteID();
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    List companyList = new ArrayList();
    int total = 0;
    String href = null;
    try {
        companyList = companyManager.getCompanyInfos(siteid,columnID, start, range, username,flag);
        total = companyManager.getCompanyInfos_num(siteid,columnID, username,0);
    } catch (Exception e) {
        e.printStackTrace();
    }

    int companyCount = companyList.size();
    int totalpages = 0;
    if (total > 0) {
        if (total % 20 == 0) {
            totalpages = total / 20;
        } else {
            totalpages = total / 20 + 1;
        }
    }
%>

<html>
<head>
    <title>企业分类列表</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../css/global.css">
    <script language="javascript" src="../js/setday.js"></script>
    <script language="javascript">
        function createCompany(columnID)
        {
            window.open("createcompany.jsp?column=" + columnID, "", "width=800,height=600,left=100,top=50,status,scrollbars");
        }

        function editCompany(columnID, companyID, range, start, from)
        {
            window.open("editcompany.jsp?column=" + columnID + "&company=" + companyID + "&range=" + range + "&start=" + start + "&fromflag=" + from, "", "width=935,height=650,left=5,top=5,status,scrollbars");
        }

        function upload(columnid,companyid)
        {
            window.open("upload.jsp?column="+columnid + "&id="+companyid, "UploadPics", "width=600,height=400,left=20,top=20,scrollbars");
        }

        function Preview(articleID, columnId)
        {
            window.open("preview.jsp?article=" + articleID + "&column=" + columnId, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
        }

        function checkNum(str)
        {
            var success = true;
            for (var i = 0; i < str.length; i++)
            {
                if (str.substring(i, i + 1) < '0' || str.substring(i, i + 1) > '9')
                {
                    success = false;
                    break;
                }
            }
            return success;
        }

        function search(item)
        {
            if (item == 'doclevel')
            {
                if (!checkNum(searchForm.doclevel1.value) || !checkNum(searchForm.doclevel2.value))
                {
                    alert("权重应为整数！");
                    return;
                }
            }

            searchForm.action = "articles.jsp?item=" + item;
            searchForm.submit();
        }

        function moreSearch()
        {
            if (searchDiv.style.display == "")
                searchDiv.style.display = "none";
            else
                searchDiv.style.display = "";
        }

        function CA()
        {
            for (var i = 0; i < document.form1.elements.length; i++)
            {
                var e = document.form1.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox')
                {
                    e.checked = document.form1.allbox.checked;
                }
            }
        }

        function WebEdit()
        {
            window.open("../webedit/index.jsp?column=<%=columnID%>&right=4", "WebEdit", "width=850,height=600,left=5,top=5,scrollbars");
        }

        function golist(r, id, type) {
            var bor = (r - 1) * 20;
            if (isNumber(r)) {
                window.location = "articles.jsp?start=" + bor + "&column=" + id + "&flag=" + type;
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
    </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"企业信息管理", ""}
    };

    String[][] operations = {
            {"<a href=javascript:createCompany(" + columnID + ")>新建</a>&nbsp;", ""},
            {"未用", "unusedarticle.jsp?column=" + columnID}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<table cellpadding=1 cellspacing=1 width=100% border=0>
    <tr>
        <td align=center class=cur>
            <%
                if (msgno == 0)
                    out.println("页面发布成功！");
                else if (msgno == -1)
                    out.println("某栏目没有设置索引页模板！");
                else if (msgno == -2)
                    out.println("写文件时出现错误！");
                else if (msgno == -3)
                    out.println("生成文章列表文件时出现错误！");
                else if (msgno == -4)
                    out.println("某栏目模板的文件名为空！");
                else if (msgno == 2)
                    out.println("更新RSS成功！");
            %>
        </td>
    </tr>
</table>
<%
    if (companyCount > 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0) {
            if (href == null)
                out.println("<a href=articles.jsp?flag=" + flag + "&column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
            else
                out.println("<a href=" + href + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        }
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            if (href == null)
                out.println(remain + "<a href=articles.jsp?flag=" + flag + "&column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
            else
                out.println(remain + "<a href=" + href + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
<form action="doArticleRSS.jsp" method=Post name=form1>
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=start value="<%=start%>">
<input type=hidden name=range value="<%=range%>">
<tr class=itm bgcolor="#dddddd" height=20>
    <td align=center width="4%">选择</td>
    <td align=center width="4%">状态</td>
    <td align=center width="23%"><a href="articles.jsp?flag=1&column=<%=columnID%>">标题</a></td>
    <td align=center width="11%"><a href="articles.jsp?flag=0&column=<%=columnID%>">发布时间</a></td>
    <td align=center width="10%">编辑人</td>
    <td align=center width="10%">上传</td>
    <td align=center width="6%">修改</td>
    <td align=center width="6%">删除</td>
</tr>
    <%
  for (int i = 0; i < companyCount; i++) {
    Companyinfo company = (Companyinfo) companyList.get(i);

    int companyID = company.getId();
    columnID = company.getCompanyclassid();    
    String maintitle = StringUtil.gb2iso4View(company.getCompanyname());
    int pubflag = company.getPublishflag();
    String publishtime = "&nbsp;";
    if (company.getLastupdated() != null) publishtime = company.getLastupdated().toString().substring(0, 16);

    //是否正在审核中,为空,则表示不在审核中或已审核完毕
    String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
    out.println("<tr bgcolor=" + bgcolor + " class=itm onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "';\" height=25><td align=center>");
    out.print("<input type=checkbox name=article value=" + companyID + ">");
    out.println("</td>");
              switch (pubflag) {
            case 0:
              out.println("<td align=center><img src=\"../images/yi.gif\" border=0 alt=\"已发布\"></td>");
              break;
            case 1:
                   out.println("<td align=center><img src=\"../images/xin.gif\" border=0 alt=\"新稿\"></td>");
              break;
            case 2:
              out.println("<td align=center><font color=red>发布中</font></td>");
              break;
            default:
              break;
          }
    out.println("</td>");

    out.println("<td><a href=javascript:editCompany(" + columnID + "," + companyID + "," + range + "," + start + ",'c');" + "'>" + maintitle + "</a></td>");

    out.println("<td align=center>" + publishtime + "</td>");
    out.println("<td align=center>&nbsp;</td>");
    out.println("<td align=center><a href=javascript:upload(" + columnID+ "," + companyID + ")>上传</></td>");
    out.println("<td align=center>");
    out.println("<a href=javascript:editCompany(" + columnID + "," + companyID + "," + range + "," + start + ",'c');><img src=\"../images/edit.gif\" alt=\"编辑文章\" align=bottom border=0></a>");
    out.println("</td>");
    out.println("<td align=center>");
        out.println("<a href=\"deleteCompany.jsp?column=" + columnID + "&company=" + companyID + "&start=" + start + "&range=" + range + "&fromflag=a\"><img src=\"../images/del.gif\" align=bottom border=0 alt=\"删除文章\"></a>");
    out.println("</td>");
    out.println("</tr>");
  }
%>
</table>
</form>
<form action="" method=Post name=searchForm>
    <input type=hidden name=doSearch value=true>
    <input type=hidden name=column value="<%=columnID%>">
    <table border="0" width="98%" cellspacing=0 cellpadding=0 align=center>
        <tr>
            <td width="40%">标&nbsp;&nbsp;题：&nbsp;&nbsp;<input name=maintitle size=30></td>
            <td width="20%"><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                   onclick="search('maintitle');">&nbsp;&nbsp;&nbsp;&nbsp;<a
                    href="javascript:moreSearch();">高级搜索</a>
            </td>
            <td width="40%">当前栏目的文章总数为：<%=total%>&nbsp;&nbsp;
                <%if (totalpages >= 1) {%>总<%=totalpages%>页&nbsp;第<%=(start + range) / 20%>页&nbsp;&nbsp;
                到第<input type="text" name="jump" value="<%=(start+range)/20%>" size="2">
                <a href="javascript:golist(document.all('jump').value,<%=columnID%>,<%=flag%>);">GO</a><%}%>
            </td>
        </tr>
        <tr>
            <td colspan=3></td>
        </tr>
    </table>
    <div id=searchDiv style="display:none">
        <table border="0" width="98%" cellspacing=0 cellpadding=0 align=center>
            <tr>
                <td width="40%">文章ID：&nbsp;&nbsp;<input name=id size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('id');"></td>
            </tr>
            <tr>
                <td width="40%">关键字：&nbsp;&nbsp;<input name=keyword size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('keyword');"></td>
            </tr>
            <tr>
                <td width="40%">副标题：&nbsp;&nbsp;<input name=vicetitle size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('vicetitle');"></td>
            </tr>
            <tr>
                <td width="40%">作&nbsp;&nbsp;者：&nbsp;&nbsp;<input name=author size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('author');"></td>
            </tr>
            <tr>
                <td width="40%">来&nbsp;&nbsp;源：&nbsp;&nbsp;<input name=source size=30></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('source');"></td>
            </tr>
            <tr>
                <td width="40%">发布日期：<input name=publishtime1 size=12 onfocus="setday(this)">&nbsp;到&nbsp;&nbsp;<input
                        name=publishtime2 size=12 onfocus="setday(this)"></td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('publishtime');"></td>
            </tr>
            <tr>
                <td width="40%">权重范围：<input name=doclevel1 size=12>&nbsp;到&nbsp;&nbsp;<input name=doclevel2 size=12>
                </td>
                <td width="60%" colspan=2><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 "
                                                 onclick="search('doclevel');"></td>
            </tr>
            <tr>
                <td width="40%">文章状态：<input type=radio name=status value=1>新稿
                    <input type=radio name=status value=2>已发布
                    <input type=radio name=status value=3>未用
                    <input type=radio name=status value=4>在审
                    <input type=radio name=status value=5>退稿
                </td>
                <td width="10%"><input style="height:20;width:40;font-size:9pt" type=button value=" 搜索 " onclick="search('status');"></td>
                <td width="50%"><input class=tine type=button value=" 组合搜索 " onclick="search('all');"></td>
            </tr>
        </table>
    </div>
</form>

</BODY>
</html>
