<%@ page import="java.util.Calendar,
                 java.sql.Timestamp,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*,
                 java.util.List,
                 java.util.ArrayList" contentType="text/html;charset=GBK"%>

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
    int createflag = ParamUtil.getIntParameter(request, "createflag", -1);
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

            akeyword.setSiteid(siteid);
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
            akeyword.setKeyword(StringUtil.gb2isoindb(keyword));
            akeyword.setUrl(StringUtil.gb2isoindb(url));
            articleMgr.updateColumnKeyword(akeyword);
            errormsg = "修改成功！";
            kid = -1;
        }
    }

    if(createflag == 1)
        errormsg = "发布成功！";
    else if(createflag == 0){
        errormsg = "发布失败！";
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
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="../style/global.css">    
    <script language="javascript">
        function EditKeyword(id, cid) {
            window.location = "keywordRight.jsp?id=" + id + "&column=" + cid;
        }

        function DeleteKeyword(id, cid, kname) {
            var val;
            val = confirm("确定要删除关键字 " + kname + " 吗？");
            if (val) {
                window.location = "deleteKeyword.jsp?&id=" + id + "&column=" + cid + "&doDelete=true";
            }
        }

        function check(kid) {
            if (kid == -1) {
                keyform.action = "keywordRight.jsp?column=<%=columnID%>&doUpdate=true";
            } else {
                keyform.action = "keywordRight.jsp?column=<%=columnID%>&doEdit=true&id=" + kid;
            }
        }

        function addKeywordUrl() {
            var winStr = "addKeywordUrl.jsp";
            var retval = showModalDialog(winStr, "addKeywordUrl", "font-family:Verdana; font-size:12; dialogWidth:55em; dialogHeight:35em; status=no");
            if (retval != "" && retval != undefined) {
                document.keyform.url.value = retval;
            }
        }
    </script>
</head>
<%
    String[][] titlebars = {
            {"关键字管理", ""},
            {cname, ""}
    };
    String[][] operations = {
            {"系统管理", "javascript:parent.location='index.jsp'"}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<BODY>
<br>

<p align=center style="font-size:9pt"><font color=red><%=errormsg%>
</font></p>

<form method="POST" action="keywordRight.jsp?column=<%=columnID%>" name="keyform">
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='70%'
           align=center>
        <tr bgcolor="#dddddd">
            <td width="100%" height="25">
                <p align="center"><b>关键字链接定义</b></p>
            </td>
        </tr>
        <%
            String keyword = "";
            String url = "";
            if ((columnID > 0) && (kid > 0)) {
                IArticleManager articleMgr = ArticlePeer.getInstance();
                ArticleKeyword akeyword = articleMgr.getOneArticleKeyword(columnID, kid);
                keyword = akeyword.getKeyword();
                keyword = StringUtil.gb2iso4View(keyword);
                url = akeyword.getUrl();
                url = StringUtil.gb2iso4View(url);
            }
        %>
        <tr>
            <td width="100%" height="52">
                <p align="center">文章关键字：<input type="text" name="keyword" size=14 maxlength=40
                                               <%if((kid>0)&&(!doEdit)){%>value="<%=keyword%>"<%}%>>&nbsp;
                    链接地址：<input type="text" name="url" size=60 maxlength=100
                                <%if((kid>0)&&(!doEdit)){%>value="<%=url%>"<%}%>
                                <%if(columnID!=0){%>onDblClick="addKeywordUrl();"<%}%>>
                    <%if (kid > 0) {%>
                    <input type="submit" value="  修改  " name="submit1" <%if(columnID==0){%>disabled<%}%>
                           onclick="javascript:return check(<%=kid%>);">
                    <%} else {%>
                    <input type="submit" value="  增加  " name="submit1" <%if(columnID==0){%>disabled<%}%>
                           onclick="javascript:return check(<%=kid%>);">
                    <%}%>
                </p>
            </td>
        </tr>
    </table>
</form>
<p>
    <%


      if(columnID > 0){
        List list = new ArrayList();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        int start = ParamUtil.getIntParameter(request,"start",0);
        int range = ParamUtil.getIntParameter(request,"range",20);

        list = articleMgr.getArticleKeywords(columnID, start, range, 0, isRootColumnID, siteid);
        int total = articleMgr.getArticleKeywordsNum(columnID, isRootColumnID, siteid);
        int keywordCount = list.size();

        if (keywordCount > 0)
        {
          out.println("<table cellpadding=1 cellspacing=1 width=70% border=0 align=center>");
          out.println("<tr><td width=50% align=left class=line>");
          if (start - range >= 0)
          {
            out.println("<a href=keywordRight.jsp?&column="+columnID+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
          }
          out.println("</td><td width=50% align=right class=line>");
          if (start + range < total)
          {
            int remain = ((start+range-total)<range)?(total-start-range):range;
            out.println(remain+"<a href=keywordRight.jsp?column="+columnID+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
           }
          out.println("</td></tr></table>");
        }


    %>
<form action="createKeywordXML.jsp" name="keywordform">
<input type="hidden" name="column" value="<%=columnID%>">
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="70%" align=center>
    <tr bgcolor="#eeeeee" class=tine>
        <td width="5%" align=center>编号</td>
        <td width="10%" align=center>所在栏目</td>
        <td width="15%" align=center>关键字</td>
        <td width="40%" align=center>链接地址</td>
        <td width="5%" align=center>修改</td>
        <td width="5%" align=center>删除</td>
    </tr>
    <%
        for (int i = 0; i < list.size(); i++) {
            ArticleKeyword akeyword = (ArticleKeyword) list.get(i);
            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
            keyword = akeyword.getKeyword();
            keyword = StringUtil.gb2iso4View(keyword);
            url = akeyword.getUrl();
            int id = akeyword.getId();
            int cid = akeyword.getColumnid();
            column = columnMgr.getColumn(cid);
            cname = column.getCName();
    %>
    <tr bgcolor="<%=bgcolor%>" class=itm onmouseover="this.style.background='#CECEFF';" onmouseout="this.style.background='<%=bgcolor%>'" height=25>
        <td align=center><font color=red><%=start + i + 1%>
        </font></td>
        <td>&nbsp;&nbsp;<%=StringUtil.gb2iso4View(cname)%>
        </td>
        <td align=center><%=keyword%>
        </td>
        <td align=center><%=url == null ? "" : url%>
        </td>
        <td align=center><a href="javascript:EditKeyword(<%=id%>,<%=columnID%>);"><img src="../images/edit.gif"
                                                                                       border=0></a></td>
        <td align=center><a href="javascript:DeleteKeyword(<%=id%>,<%=columnID%>,'<%=keyword%>');"><img
                src="../images/del.gif" border=0></a></td>
    </tr>
    <%
            }
        }
    %>
</table>
<br>
<table cellpadding="0" cellspacing="0" border="0" width="70%" align=center>
    <tr width="100%">
        <td align="center"><input type="submit" name="pubbutton" value="  发布  "></td>
    </tr>
</table>
</form>
</p>
</BODY>
</html>