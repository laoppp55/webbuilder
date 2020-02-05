<%@ page import="java.util.Calendar,
                 java.sql.Timestamp,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*,
                 java.util.List,
                 java.util.ArrayList"
         contentType="text/html;charset=utf-8"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String errormsg = "";
    int errorflag = ParamUtil.getIntParameter(request, "errorflag", -1);
    if (errorflag == 0)
        errormsg = "删除成功！";
    else if (errorflag == 1)
        errormsg = "删除失败！";

    boolean error = false;
    IColumnManager columnMgr = ColumnPeer.getInstance();
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int kid = ParamUtil.getIntParameter(request, "id", -1);
    boolean doEdit = ParamUtil.getBooleanParameter(request, "doEdit");
    Column column = new Column();
    column = columnMgr.getSiteRootColumn(authToken.getSiteID());
    boolean isRootColumnID = columnID == column.getID() ? true : false;

    if ((doUpdate) && (!doEdit)) {
        String keyword = ParamUtil.getParameter(request, "keyword");
        String url = ParamUtil.getParameter(request, "url");

        if (columnID == 0 || keyword == null || url == null || keyword.equals("") || url.equals("")) {
            errormsg = "添加失败！";
            error = true;
        }

        if (!error) {
            ArticleKeyword akeyword = new ArticleKeyword();
            IArticleManager articleMgr = ArticlePeer.getInstance();

            akeyword.setColumnid(columnID);
            akeyword.setKeyword(keyword);
            akeyword.setUrl(url);
            articleMgr.insertColumnKeyword(akeyword);
            errormsg = "添加成功！";
        }
    }

    if ((doEdit) && (!doUpdate)) {
        String keyword = ParamUtil.getParameter(request, "keyword");
        String url = ParamUtil.getParameter(request, "url");

        if (columnID == 0 || keyword == null || url == null || keyword.equals("") || url.equals("") || (kid == -1)) {
            errormsg = "修改失败！";
            error = true;
        }

        if (!error) {
            ArticleKeyword akeyword = new ArticleKeyword();
            IArticleManager articleMgr = ArticlePeer.getInstance();

            akeyword.setId(kid);
            akeyword.setColumnid(columnID);
            akeyword.setKeyword(keyword);
            akeyword.setUrl(url);
            articleMgr.updateColumnKeyword(akeyword);
            errormsg = "修改成功！";
            kid = -1;
        }
    }

    String cname = "";
    if (columnID > 0) {
        cname = columnMgr.getColumn(columnID).getCName();
        cname = StringUtil.gb2iso4View(cname);
    }
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script language="javascript">
        function SelectKeyword(len)
        {
            var msgstr = "";
            var result = "";

            for (var e = 0; e < selname.length; e++) {
                if (selname.elements[e].checked == true) {
                    msgstr = selname.elements[e].value;
                    posi = msgstr.indexOf(",");
                    value = msgstr.substring(0, posi);
                    text = msgstr.substring(posi + 1);
                    result = result + "a" + value + "-" + text + "\r\n";
                }
            }

            window.returnValue = result;
            top.close();
        }
    </script>
</head>

<BODY>
<br>
<table border=0>
    <tr>
        <td>
            当前所在栏目-->><font color=red><%=cname%>
        </font></td>
    </tr>
</table>
<center>
    <%
        List list = new ArrayList();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        int start = ParamUtil.getIntParameter(request, "start", 0);
        int range = ParamUtil.getIntParameter(request, "range", 20);
        int total = 0;

        if (columnID > 0) {
            list = articleMgr.getArticleKeywords(columnID, start, range, 0, isRootColumnID, siteid);
            total = articleMgr.getArticleKeywordsNum(columnID, isRootColumnID, siteid);
        } else {
            list = articleMgr.getArticleKeywords(columnID, start, range, 0, true, siteid);
            total = articleMgr.getArticleKeywordsNum(columnID, true, siteid);
        }
        int keywordCount = list.size();

        if (keywordCount > 0) {
            out.println("<table cellpadding=1 cellspacing=1 width=98% border=0 align=center>");
            out.println("<tr><td width=50% align=left class=line>");
            if (start - range >= 0) {
                out.println("<a href=keywordRight.jsp?&column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
            }
            out.println("</td><td width=50% align=right class=line>");
            if (start + range < total) {
                int remain = ((start + range - total) < range) ? (total - start - range) : range;
                out.println(remain + "<a href=keywordRight.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
            }
            out.println("</td></tr></table>");
        }
    %>
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="98%"
           align=center>
        <form name=selname>
            <tr bgcolor="#eeeeee" class=tine>
                <td width="5%" align=center>选择</td>
                <td width="10%" align=center>所在栏目</td>
                <td width="15%" align=center>关键字</td>
                <td width="40%" align=center>链接地址</td>
            </tr>
            <%
                String keyword = "";
                String url = "";
                for (int i = 0; i < list.size(); i++) {
                    ArticleKeyword akeyword = (ArticleKeyword) list.get(i);
                    String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
                    keyword = akeyword.getKeyword();
                    keyword = StringUtil.gb2iso4View(keyword);
                    url = akeyword.getUrl();
                    int id = akeyword.getId();
                    int cid = akeyword.getColumnid();
                    column = columnMgr.getColumn(cid);
                    cname = column.getCName();
            %>
            <tr bgcolor="<%=bgcolor%>" height=22 class=itm>
                <td align=center><input type=checkbox name=keywordID<%=i%> value='<%=id%>,<%=keyword%>'></td>
                <td>&nbsp;&nbsp;<%=StringUtil.gb2iso4View(cname)%>
                </td>
                <td align=center><%=keyword%>
                </td>
                <td align=center><%=url == null ? "" : url%>
                </td>
            </tr>
            <%
                }
            %>
        </form>
    </table>
    <br><br>
    <input type=button value="  确定  " onclick="SelectKeyword(<%=list.size()%>);" <%if(list.size()==0){%>disabled<%}%>>&nbsp;&nbsp;
    <input type=button value="  取消  " onclick="top.close();">
</center>
</BODY>
</html>
