<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.tree.TreeManager" %>
<%@ page import="com.bizwink.cms.tree.Tree" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid=authToken.getSiteID();
    String username = authToken.getUserID();
    int siteId = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int samsiteid = authToken.getSamSiteid();

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");
    boolean doAudit = ParamUtil.getBooleanParameter(request, "doAudit");
    IArticleManager articleMgr = ArticlePeer.getInstance();

    if (doAudit) {
        IAuditManager auditMgr = AuditPeer.getInstance();
        List aidList = ParamUtil.getParameterValues(request, "article");
        if (aidList.size() > 0) {
            int act = ParamUtil.getIntParameter(request, "act", 0);
            for (int i = 0; i < aidList.size(); i++) {
                int articleID = Integer.parseInt((String) aidList.get(i));
                Audit audit = new Audit();
                audit.setArticleID(articleID);
                audit.setEditor(username);
                audit.setSign(username);
                audit.setComments("");
                audit.setStatus(0);
                audit.setBackTo(ParamUtil.getParameter(request, "editor" + articleID));
                auditMgr.Auditing(audit, "add", act, siteId, columnID,articleMgr.getArticleProcessOfAudit(articleID));
            }
        }
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    int parentColumnID = column.getParentID();
    String CName = StringUtil.gb2iso4View(column.getCName());

    List articleList = articleMgr.getAuditArticlesFiles(username,columnID, start, range,siteid);
    int total = articleMgr.getAuditArticlesFilesNum(username,columnID,siteid);
    int articleCount = articleList.size();

    Tree colTree = null;
    if (samsiteid == 0)
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    else
        colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
    sitename = StringUtil.replace(sitename,"_",".");
    String cname = colTree.getChineseDirForArticle(colTree,columnID,sitename);
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language=javascript>
        function Preview(articleID)
        {
            window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
        }

        function CA()
        {
            for (var i = 0; i < auditForm.elements.length; i++)
            {
                var e = auditForm.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox' && e.name != 'option')
                {
                    e.checked = auditForm.allbox.checked;
                }
            }
        }

        function window_confirm(articleID, range, start, isFile)
        {
            var retval = confirm("您已审过该篇文章，您是要修改吗？");
            if (retval)
            {
                if (isFile == 0)
                    window.open("editarticle.jsp?article=" + articleID + "&range=" + range + "&start=" + start + "&act=update", "Update", "width=900,height=680,left=5,top=5,scrollbars");
                else
                    window.open("edituploadfile.jsp?article=" + articleID + "&range=" + range + "&start=" + start + "&act=update", "Update", "width=800,height=600,left=5,top=5,scrollbars");
            }
        }

        function Update(articleID, isFile)
        {
            if (isFile == 0)
                window.open("editarticle.jsp?article=" + articleID + "&range=<%=range%>&start=<%=start%>&act=add", "Update", "width=900,height=680,left=5,top=5,scrollbars");
            else
                window.open("edituploadfile.jsp?article=" + articleID + "&range=<%=range%>&start=<%=start%>&act=add", "Update", "width=800,height=600,left=5,top=5,scrollbars");
        }
    </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"文章审核", ""},
            {StringUtil.gb2iso4View(cname), ""}
    };
    String[][] operations = {
            {"退稿", "returnarticle.jsp?column=" + columnID}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
    if (msg != null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=articles.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=articles.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <form name=auditForm method=Post action="articles.jsp">
        <input type=hidden name=doAudit value=true>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=act>
        <tr class=itm bgcolor="#dddddd" height=20>
            <td align=center width="5%">选中</td>
            <td align=center width="5%">状态</td>
            <td align=center width="49%">标题</td>
            <td align=center width="16%">发布时间</td>
            <td align=center width="5%">排序</td>
            <td align=center width="10%">编辑</td>
            <td align=center width="5%">预览</td>
            <td align=center width="5%">操作</td>
        </tr>
        <%

          //判断当前用户是否能审核此篇文章
          IAuditManager auditMgr = AuditPeer.getInstance();
          String userID = authToken.getUserID().toString().toLowerCase().trim();
          userID = "[" + userID + "]";

          for (int i = 0; i < articleCount; i++) {
            Article article = (Article) articleList.get(i);

            int articleID = article.getID();
            String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
            String editor = article.getEditor();
            String createdate = article.getPublishTime().toString().substring(0, 19);
            int sortId = article.getSortID();
            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";

            //判断该用户是否已审过该文章
            //int isAudit = auditMgr.Query_User_Article_Audit(userID, articleID);

            //查询某用户对某篇文章是否有审核权限
            //boolean isAction = auditMgr.query_User_isAuditing(userID, articleID);

            //System.out.println("isAudit=" + isAudit);
            //System.out.println("isAction=" + isAction);

            //if ((isAudit == 0 && isAction) || isAudit == 1) {
            //if (isAction) {
        %>
        <tr bgcolor="<%=bgcolor%>" height=25>
            <td align=center><input type=checkbox name=article value="<%=articleID%>"></td>
            <td align=center><img src="../images/button/shen.gif" alt="在审文章"></td>
            <td><%=maintitle%>
            </td>
            <td align=center><%=createdate%>
            </td>
            <td align=center><%=sortId%>
            </td>
            <td align=center><%=editor%><input type=hidden name=editor<%=articleID%> value="<%=editor%>"></td>
            <td align=center><a href=javascript:Preview(<%=articleID%>);><img src="../images/button/view.gif" border=0></a>
            </td>
            <td align=center>
                <%
                    if (SecurityCheck.hasPermission(authToken, 5) || SecurityCheck.hasPermission(authToken, 54)) {
                    //if (isAudit == 0 && isAction) {
                        if (article.getNullContent() == 0)
                            out.println("<a href=\"javascript:Update(" + articleID + ", 0);\">");
                        else
                            out.println("<a href=\"javascript:Update(" + articleID + ", 1);\">");
                    //}
                    //if (isAudit == 1) {
                    //    if (article.getNullContent() == 0)
                    //        out.println("<a href=\"javascript:window_confirm(" + articleID + "," + range + "," + start + ",0);\">");
                    //    else
                    //        out.println("<a href=\"javascript:window_confirm(" + articleID + "," + range + "," + start + ",1);\">");
                    //}
                    }
                %>
                <img src="../images/button/edit.gif" align="bottom" border=0>
            </td>
        </tr>
        <%

            //}
          }

        %>
</table>
<table border=0 width="100%">
    <tr>
        <td>
            &nbsp;<input type=checkbox name=allbox value="CheckAll" onClick="CA();">全部选中&nbsp;&nbsp;
            <input type=button value=" 审核通过 " onclick="auditForm.act.value='1';auditForm.submit();">&nbsp;&nbsp;
            <input type=button value=" 退给编辑 " onclick="auditForm.act.value='0';auditForm.submit();">
        </td>
    </tr>
    </form>
</table>

</BODY>
</html>