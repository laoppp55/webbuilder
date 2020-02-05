<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.viewFileManager.viewFilePeer" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.viewFileManager.IViewFileManager" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="com.bizwink.cms.viewFileManager.ViewFile" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 10);

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    IArticleManager articleMgr = ArticlePeer.getInstance();
    List articleList = articleMgr.getCommendArticleForColumn(columnID, start, range);
    int total = articleMgr.getCommendArticleForColumnNum(columnID);
    int articleCount = articleList.size();

    int siteID = authToken.getSiteID();
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    int flags = ParamUtil.getIntParameter(request, "flags", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    IMarkManager markMgr = markPeer.getInstance();

    int listType = 0;
    String notes;
    String cname;
    int articleNum = 0;
    String order = "0,0";
    List ids = new ArrayList();
    int selectway = 0;

    if (doCreate) {
        cname = "推荐文章列表";
        notes = "notes";
        listType = ParamUtil.getIntParameter(request, "listType", 0);
        String content = ParamUtil.getParameter(request, "contents");
        String relatedCID = "()";
        int newcolumnid = ParamUtil.getIntParameter(request, "column", 0);

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(newcolumnid);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setMarkType(3);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(0);
        mark.setFormatFileNum(listType);
        mark.setRelatedColumnID(relatedCID);

        if (markID > 0)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);
        //对选中的文章排序
        String viewer = request.getHeader("user-agent");   
        if (viewer.toLowerCase().indexOf("gecko") == -1)
            if (flags == 1) {
                out.println("<script>top.close();</script>");
            } else {
                out.println("<script>window.returnValue=\"[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]\";top.close();</script>");
            }
        else {
            if (flags == 1) {
                out.println("<script>top.close();</script>");
            }else{
                String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
                out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + cname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                        "window.parent.parent.opener.InsertHTML('content',returnvalue);top.close();</script>");
            }
        }
        return;
    }

    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        listType = Integer.parseInt(properties.getProperty(properties.getName().concat(".LISTTYPE")));


        selectway = Integer.parseInt(properties.getProperty(properties.getName().concat(".SELECTWAY")));
        int columnid = Integer.parseInt(properties.getProperty(properties.getName().concat(".COLUMNID")));
        if (selectway == 0) {
            String articleIDs = properties.getProperty(properties.getName().concat(".ARTICLE.ID"));
            ids = articleMgr.getArticleMainTitle(articleIDs, columnid);
        } else {
            articleNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLENUM")));
            order = properties.getProperty(properties.getName().concat(".ORDER"));
        }

        int innerFlag = Integer.parseInt(properties.getProperty(properties.getName().concat(".INNERHTMLFLAG")));
        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null) notes = "";
    }
    String[] orderx = order.split(",");
    List list = viewfileMgr.getViewFileC(authToken.getSiteID(), 4);
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/stytle.css">
    <script language="javascript" src="../js/mark.js"></script>
    <script language="javascript">
        function Preview(articleID)
        {
            window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=5,top=5,scrollbars");
        }

        function selects(articleID, maintitle)
        {
            var el = document.getElementById('selectedArticle');
            var optionLen = el.options.length;

            i = 0;
            var endFlag = true;
            while (i < optionLen && endFlag)
            {
                if (el.options[i].value == articleID)
                    endFlag = false;
                i++;
            }

            if (endFlag)
                document.getElementById('selectedArticle').options.add(new Option(maintitle, articleID, false, false));
        }
        function orders(articleID, orders, columnid, start, range, markID) {
            window.location = "updateOrders.jsp?startflag=1&articleID=" + articleID + "&orders=" + orders + "&column=" + columnid + "&start=" + start + "&range=" + range + "&mark=" + markID;
        }
        function updatetitle(articleID, maintitle, columnid, start, range, markID) {
            var titleval = document.getElementById("maintitle" + maintitle).value;
            window.location = "updateOrders.jsp?startflag=2&articleID=" + articleID + "&title=" + titleval + "&column=" + columnid + "&start=" + start + "&range=" + range + "&mark=" + markID;
        }
        function deletearticle(articleID, columnid, start, range, markID) {
            window.location = "deletearticle.jsp?startflag=1&articleID=" + articleID + "&column=" + columnid + "&start=" + start + "&range=" + range + "&mark=" + markID;
        }
        function selectArticleList1()
        {
            if (markForm.selectArtList[0].checked)
            {
                markForm.selectedArticle.disabled = 0;
                markForm.delete1.disabled = 0;
                markForm.artNum.disabled = 1;
                markForm.order1.disabled = 1;
                markForm.order2.disabled = 1;
            }
            if (markForm.selectArtList[1].checked)
            {
                markForm.selectedArticle.disabled = 1;
                markForm.delete1.disabled = 1;
                markForm.artNum.disabled = 0;
                markForm.order1.disabled = 0;
                markForm.order2.disabled = 0;
            }
        }
        function goo(i)
        {
            var si = markForm.selectedArticle.selectedIndex;
            var ops = markForm.selectedArticle.options;
            if (si + i >= 0 && ops[si + i] && ops[si]) {
                ops[si + i].swapNode(ops[si]);
            }
        }
    </script>
</head>
<body topmargin=0 leftmargin=4 scroll="no">
<span class="tabstyleb"><b>选择推荐文章</b></span>
<%
    if (articleCount >= 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=commendarticle_2.jsp?mark=" + markID + "&column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=commendarticle_2.jsp?mark=" + markID + "&column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>

<table width="100%" border=0 cellpadding=2 cellspacing=1 bgcolor="#CCCCCC">
    <tr bgcolor="#F5F5F5">
        <td colspan=6>当前所在栏目：<font color=red><%=CName%>
        </font></td>
    </tr>
    <tr bgcolor="#F5F5F5">
        <td align=center width="10%">选中</td>
        <td align=center width="15%">顺序</td>
        <td align=center width="40%">标题</td>
        <td align=center width="15%">发布时间</td>
        <td align=center width="10%">预览</td>
        <td align=center width="10%">删除</td>
    </tr>
    <form name="form_1" method="post" action="">
        <%
            for (int i = 0; i < articleList.size(); i++) {
                Article article = (Article) articleList.get(i);
                int articleID = article.getID();
                int orders = article.getOrders();
                String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
                String createdate = article.getPublishTime().toString().substring(0, 10);
                String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
                if (maintitle != null) {
                    maintitle = StringUtil.replace(maintitle, "'", "`");
                    maintitle = StringUtil.replace(maintitle, "\"", "&quot;");
                }
                out.println("<tr bgcolor=" + bgcolor + ">");
                out.println("<td align=center><input type=button class=tine value=add onclick=\"selects(" + articleID + ",'" + maintitle + "');\"></td>");
                out.println("<td align=center><input type=text name=orders" + i + " class=tine value=\"" + orders + "\" size=2>&nbsp;&nbsp;<input type=button class=tine value=更新 onclick=\"orders(" + articleID + ",document.all('orders" + i + "').value," + columnID + "," + start + "," + range + "," + markID + ");\"></td>");
                out.println("<td><input type=text id=maintitle" + i + " name=\"maintitle" + i + "\" class=tine value=\"" + maintitle + "\">&nbsp;&nbsp;<input type=button class=tine value=更新 onclick=\"updatetitle(" + articleID + "," + i + "," + columnID + "," + start + "," + range + "," + markID + ");\"></td>");
                out.println("<td align=center>" + createdate + "</td>");
                out.println("<td align=center>");
                if (article.getNullContent() == 0)
                    out.println("<a href=\"javascript:Preview(" + articleID + ");\"><img src='../images/preview.gif' border=0></a>");
                else
                    out.println("&nbsp;");
                out.println("</td>");
                out.println("<td align=center>");
                out.println("<a href=\"javascript:deletearticle(" + articleID + "," + columnID + "," + start + "," + range + "," + markID + ");\">删除</a>");
                out.println("</td></tr>");
            }
        %>
    </form>
</table>
<form action="commendarticle_2.jsp" method="POST" name=markForm>
    <input type=hidden name=doCreate value=true>
    <input type=hidden name=column>
    <input type=hidden name=mark value="<%=markID%>">
    <input type=hidden name=flags value="<%=flags%>">
    <input type=hidden name=contents>
    <table width="100%" border=0 cellpadding=4 cellspacing=1 bgcolor="#CCCCCC">
        <tr bgcolor="#F5F5F5">
            <td width="40%"><input type="radio" name="selectArtList" value="0" <%if(selectway==0){%>checked<%}%>
                                   onclick="selectArticleList1();">选中的文章：<br>
                <select name="selectedArticle" id="selectedArticle" style="width:260" size="4">
                    <%
                        for (int i = 0; i < ids.size(); i++) {
                            String temp = (String) ids.get(i);
                            out.println("<option value=\"" + temp.substring(0, temp.indexOf("|")) + "\">" + StringUtil.gb2iso4View(temp.substring(temp.indexOf("|") + 1)) + "</option>");
                        }
                    %>
                </select>

            </td>
            <td width="40%"><br>
                <input type=button value=上移 onclick="goo(-1)" style="height:20;width:30;font-size:9pt"><br>
                <input type="button" name="delete1" value="删除" onclick="delArticleItem();"
                       style="height:20;width:30;font-size:9pt"> <br>
                <input type=button value=下移 onclick="goo(+1)" style="height:20;width:30;font-size:9pt">
            </td>
            <td width="230%">
            </td>
        </tr>
    </table>
    <table width="100%" border=0 cellpadding=4 cellspacing=1 bgcolor="#CCCCCC">
        <tr bgcolor="#F5F5F5">
            <td width="18%"><input type="radio" name="selectArtList" value="1" <%if(selectway==1){%>checked<%}%>
                                   onclick="selectArticleList1();">
                选择<input name="artNum" value="<%=articleNum%>" size=2>篇文章
            </td>
            <td width="32%">
                排序：<select id="order1" style="font-size:9pt;width:70" class=tine>
                <option value=0>第一选择</option>
                <option value=1 <%if(orderx[0].equals("1")){%>selected<%}%>>时间逆序</option>
                <option value=2 <%if(orderx[0].equals("2")){%>selected<%}%>>时间顺序</option>
                <option value=3 <%if(orderx[0].equals("3")){%>selected<%}%>>序号逆序</option>
                <option value=4 <%if(orderx[0].equals("4")){%>selected<%}%>>序号顺序</option>
            </select>
                <select id="order2" style="font-size:9pt;width:70" class=tine>
                    <option value=0>第二选择</option>
                    <option value=1 <%if(orderx[1].equals("1")){%>selected<%}%>>序号逆序</option>
                    <option value=2 <%if(orderx[1].equals("2")){%>selected<%}%>>序号顺序</option>
                    <option value=3 <%if(orderx[1].equals("3")){%>selected<%}%>>时间逆序</option>
                    <option value=4 <%if(orderx[1].equals("4")){%>selected<%}%>>时间顺序</option>
                </select>
            </td>
            <td width="50%">
                <table width="100%" border=0>
                    <tr height=30>
                        <td>列表样式：
                            <select name="listType" style="font-size:9pt;width:150">
                                <option value=0>请选择样式文件</option>
                                <%
                                    for (int i = 0; i < list.size(); i++) {
                                        ViewFile viewfile = (ViewFile) list.get(i);
                                %>
                                <option value="<%=viewfile.getID()%>"
                                        <%if(viewfile.getID()==listType){%>selected<%}%>><%=StringUtil.gb2iso4View(viewfile.getChineseName())%>
                                </option>
                                <%}%>
                            </select>
                            <button style="height:20;width:30;font-size:9pt" onclick="createStyle(4,<%=columnID%>)">新建
                            </button>
                            <button style="height:20;width:30;font-size:9pt"
                                    onclick="updateStyle(4,listType.options[listType.selectedIndex].value,<%=columnID%>)">
                                修改
                            </button>
                            <button style="height:20;width:30;font-size:9pt"
                                    onclick="previewStyle(4,listType.options[listType.selectedIndex].value)">预览
                            </button>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <table width="100%" border=0 cellpadding=4 cellspacing=1 bgcolor="#CCCCCC">
        <tr bgcolor="#F5F5F5" height=40>
            <td align="center" colspan=2>
                <input type="button" value="  确定  " onclick="createCommendArticle(0,<%=columnID%>);">&nbsp;&nbsp;
                <input type="button" value="  取消  " onclick="top.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>