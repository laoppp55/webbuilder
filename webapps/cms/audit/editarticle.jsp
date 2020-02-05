<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.extendAttr.*"
         contentType="text/html;charset=gbk"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean success = false;
    int siteId = authToken.getSiteID();
    String username = authToken.getUserID();
    IAuditManager auditMgr = AuditPeer.getInstance();
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();

    boolean doAudit = ParamUtil.getBooleanParameter(request, "doAudit");
    boolean doReturn = ParamUtil.getBooleanParameter(request, "doReturn");
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String act = ParamUtil.getParameter(request, "act");

    String prevAuditor = null;
    String comments = null;
    String editor = null;        //文章编辑者

    //读出文章
    IArticleManager articleManager = ArticlePeer.getInstance();
    Article article = articleManager.getArticle(articleID);
    int columnID = article.getColumnID();

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String columnName = StringUtil.gb2iso4View(column.getCName());
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();

    if (doReturn)     //退稿
    {
        int backtowho = ParamUtil.getIntParameter(request, "backtowho", -1);                //退稿给某人
        prevAuditor = StringUtil.iso2gb(ParamUtil.getParameter(request, "prevAuditor"));  //退稿给上一级审核者
        comments = StringUtil.iso2gb(ParamUtil.getParameter(request, "comments"));        //建议
        editor = StringUtil.iso2gb(ParamUtil.getParameter(request, "editor"));        //编辑

        auditMgr.updateAudit_Info("", articleID, "", 3,0);    //更新原来的审核信息为过期信息

        Audit audit = new Audit();
        audit.setArticleID(articleID);
        audit.setSign(username);
        audit.setComments(comments);

        if (backtowho == 0) {           //退稿给作者
            audit.setStatus(1);
            audit.setBackTo(editor);
        } else if (backtowho == 1) {   //退稿给上一级审核人
            audit.setStatus(2);
            audit.setBackTo(prevAuditor);
        }
        auditMgr.Create_Article_Audit_Info(audit,article.getProcessofaudit(),backtowho);
        success = true;
    }

    //审核文章
    if (doAudit) {
        int isAudit = ParamUtil.getIntParameter(request, "isAudit", -1);                  //是否通过审核
        int backtowho = ParamUtil.getIntParameter(request, "backtowho", -1);                //退稿给某人
        prevAuditor = StringUtil.iso2gb(ParamUtil.getParameter(request, "prevAuditor"));  //退稿给上一级审核者
        comments = StringUtil.iso2gb(ParamUtil.getParameter(request, "comments"));        //建议
        editor = StringUtil.iso2gb(ParamUtil.getParameter(request, "editor"));        //编辑

        Audit audit = new Audit();
        audit.setArticleID(articleID);
        audit.setEditor(username);
        audit.setSign(username);
        audit.setComments(comments);
        audit.setStatus(backtowho);
        if (backtowho == 0)
            audit.setBackTo(editor);
        else if (backtowho == 1)
            audit.setBackTo(prevAuditor);

        auditMgr.Auditing(audit, act, isAudit, siteId, columnID,article.getProcessofaudit());
        success = true;
    }

    if (success) {
        out.println("<script LANGUAGE=JavaScript>opener.history.go(0);window.close();</script>");
        return;
    }

    String relatedID = article.getRelatedArtID();
    String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
    String vicetitle = StringUtil.gb2iso4View(article.getViceTitle());
    String author = StringUtil.gb2iso4View(article.getAuthor());
    String summary = StringUtil.gb2iso4View(article.getSummary());
    String keyword = StringUtil.gb2iso4View(article.getKeyword());
    String source = StringUtil.gb2iso4View(article.getSource());
    editor = StringUtil.gb2iso4View(article.getEditor());
    int docLevel = article.getDocLevel();
    int status = article.getStatus();
    int subscriber = article.getSubscriber();
    int sortid = article.getSortID();
    String dates = article.getPublishTime().toString();
    String year = dates.substring(0, 4);
    int month = Integer.parseInt(dates.substring(5, 7));
    int day = Integer.parseInt(dates.substring(8, 10));
    int hour = Integer.parseInt(dates.substring(11, 13));
    int minute = Integer.parseInt(dates.substring(14, 16));

    //判断有无上一级审核人
    prevAuditor = auditMgr.queryPrev_Auditor("[" + username + "]", articleID,siteId);
    if (prevAuditor == null) prevAuditor = "";

    //如是修改,读出审核意见
    if ("update".equals(act)) comments = auditMgr.getComments(username, articleID);
%>

<html>
<head>
    <title>审核文章</title>
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <script LANGUAGE="JavaScript">
        function OpenItem()
        {
            backto.style.display = "";
            noBgcolor.bgColor = "#EEEEEE";
            document.createForm.backtoauthor.checked = true;
        <%if (prevAuditor.length() > 0){%>
            document.createForm.backtoauditor.checked = false;
        <%}%>
        }

        function CloseItem()
        {
            backto.style.display = "none";
            noBgcolor.bgColor = "";
            document.createForm.backtoauthor.checked = false;
        <%if (prevAuditor.length() > 0){%>
            document.createForm.backtoauditor.checked = false;
        <%}%>
        }
    </script>
</head>
<body>
<%
    String[][] titlebars = {
            {"文章管理", "articlesmain.jsp"},
            {columnName, "articles.jsp?column=" + columnID},
            {"审核文章", ""}
    };
    String[][] operations = {
            {"<a href='javascript:createForm.submit();'><img src='../images/button_modi.gif' border=0></a>", ""},
            {"<a href='javascript:window.close();'><img src='../images/button_cancel.gif' border=0></a>", ""}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>

<form action="editarticle.jsp" method="post" name=createForm>
<input type="hidden" name=act value="<%=act%>">
<input type="hidden" name=editor value="<%=editor%>">
<input type="hidden" name="article" value="<%=articleID%>">
<input type="hidden" name="range" value="<%=range%>">
<input type="hidden" name="start" value="<%=start%>">
<table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
<%
    if (act.equals("return")) {
        String[] result = auditMgr.getArticleInfo(articleID, username, 2);
        if (result != null) {
%>
<input type="hidden" name="doReturn" value="true">
<tr>
    <td class=line bgcolor="#CECFCE">
        <font color=red><b>退稿人签名：</b></font>
        <input class=tine size=16 value="<%=(result[0]!=null)?result[0]:""%>" disabled>&nbsp;&nbsp;
        <font color=red><b>退稿意见：</b></font>
        <input class=tine size=50 value="<%=(result[1]!=null)?StringUtil.gb2iso4View(result[1]):""%>" disabled>&nbsp;&nbsp;
        <font color=red><b>退稿时间：</b></font><%=(result[2] != null) ? result[2] : ""%>
    </td>
</tr>
<%}%>
<tr>
    <td class=line>
        <table border="0" width="100%" cellspacing="0" cellpadding="0">
            <tr height=31>
                <td width="30%" align=left><font color=red><b>退稿给：</b></font>
                    <input type="radio" value="0" name="backtowho" id="backtoauthor" checked>编辑&nbsp;&nbsp;
                    <%if (prevAuditor.length() > 0) {%>
                    <input type=hidden name="prevAuditor" value="<%=prevAuditor%>">
                    <input type="radio" value="1" name="backtowho" id="backtoauditor">上一级审核人(<b><%=prevAuditor%>
                </b>)
                    <%}%>
                </td>
                <td width="10%"><p align="right"><font color=red><b>签名：</b></font></td>
                <td width="10%">&nbsp;<input size="16" class=tine value="<%=username%>" disabled></td>
                <td width="10%"><p align="right"><font color=red><b>建议：</b></font></td>
                <td width="40%" rowspan="2">&nbsp;<input name="comments" size=40 maxlength=500></td>
            </tr>
        </table>
    </td>
</tr>
<%} else {%>
<input type="hidden" name="doAudit" value="true">
<tr>
    <td class=line>
        <table border="0" width="100%" cellspacing="0" cellpadding="0">
            <tr height=31>
                <td width="9%" align=left><font color=blue>是否通过：</font></td>
                <td width="12%" align=left>
                    <table border="0" width="100%" cellspacing="0" cellpadding="0" height=31>
                        <tr>
                            <td><input type="radio" value="1" checked name="isAudit" onclick="javascript:CloseItem();">是
                            </td>
                            <td name=noBgcolor id=noBgcolor><input type="radio" value="0" name="isAudit"
                                                                   onclick="javascript:OpenItem();">否
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="9%"><p align="right"><font color=blue>签名：</font></td>
                <td width="12%">&nbsp;<input size="16" class=tine disabled value="<%=username%>"></td>
                <td width="8%"><p align="right"><font color=blue>建议：</font></td>
                <td width="50%" rowspan="2">&nbsp;<textarea rows="3" name="comments" cols="68"
                                                            class=tine><%=(comments == null) ? "" : StringUtil.gb2iso4View(comments)%>
                </textarea></td>
            </tr>
            <tr height=31>
                <td width="50%" colspan="5">
                    <div id=backto style="display:none">
                        <table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor=#EEEEEE height=31>
                            <tr>
                                <td>
                                    <font color=red><b>退给：</b></font>&nbsp;
                                    <input type="radio" value="0" name="backtowho" id="backtoauthor">编辑&nbsp;&nbsp;
                                    <%if (prevAuditor.length() > 0) {%>
                                    <input type=hidden name="prevAuditor" value="<%=prevAuditor%>">
                                    <input type="radio" value="1" name="backtowho"
                                           id="backtoauditor">上一级审核人(<b><%=prevAuditor%>
                                </b>)
                                    <%}%>
                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
    </td>
</tr>
<%}%>
<tr>
    <td class=line>
        标题： <input class=tine name=maintitle size=20 value="<%=maintitle%>" disabled>
        副标题：<input class=tine name=vicetitle size=20 value="<%=(vicetitle!=null)?vicetitle:""%>" disabled>
        作者： <input class=tine name=author size=10 value="<%=(author!=null)?author:""%>" disabled>
        来源： <input class=tine name=source size=10 value="<%=(source!=null)?source:""%>" disabled>
        摘要： <input class=tine name=summary size=35 value="<%=(summary!=null)?summary:""%>" disabled>
    </td>
</tr>
<%if (isProduct == 1) {%>
<tr bgcolor="#eeeeee">
    <td class=line>
        售价：<input class=tine name=saleprice size=6 value="<%=article.getSalePrice()%>" disabled>
        进价：<input class=tine name=inprice size=6 value="<%=article.getInPrice()%>" disabled>
        市场价：<input class=tine name=marketprice size=6 value="<%=article.getMarketPrice()%>" disabled>
        库存：<input class=tine name=stocknum size=6 value="<%=article.getStockNum()%>" disabled>
        厂商：<input class=tine name=brand size=12
                  value="<%=(article.getBrand()==null)?"":StringUtil.gb2iso4View(article.getBrand())%>" disabled>
        重量(克)：<input class=tine name=weight size=5 value="<%=article.getProductWeight()%>" disabled>
        图片：<input class=tine name=pic size=12 value="<%=(article.getProductPic()==null)?"":article.getProductPic()%>"
                  disabled>
        大图片：<input class=tine name=bigpic size=12
                   value="<%=(article.getProductBigPic()==null)?"":article.getProductBigPic()%>" disabled>
    </td>
</tr>
<%}%>
<%if (isDefine == 1) {%>
<tr bgcolor="#eeeeee">
    <td class=line><%=extendMgr.getExtendAttrForAudit(columnID, articleID)%>
    </td>
</tr>
<%}%>
<tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
    <td class=line>
        <font color="#FF0000"><b>关键字(;):</b></font><input class=tine name=keyword size=10
                                                          value="<%=(keyword!=null)?keyword:""%>" disabled>
        <font color="#FF0000"><b>排序:</b></font><input class=tine name=sortid size=4 value="<%=sortid%>" disabled>
        <font color="#FF0000"><b>权重:</b></font><input class=tine name=docLevel size=4 value="<%=docLevel%>"
                                                      maxlength="5" disabled>
        <font color="#FF0000"><b>相关:</b></font><input name="relatedID" size="4" onclick="AddRelatedArticleID();"
                                                      value="<%=(relatedID==null)?"":relatedID%>" disabled>
        <font color="#FF0000"><b>使用:</b></font><input type=radio <%=(status==1)?"checked":""%> name="status" value="1"
                                                      disabled>是<input type=radio <%=(status==0)?"checked":""%>
                                                                       name="status" value="0" disabled>否
        <font color="#FF0000"><b>订阅:</b></font><input type=radio <%=(subscriber==1)?"checked":""%> name="subscriber"
                                                      value="1" disabled>是<input
            type=radio <%=(subscriber==0)?"checked":""%> name="subscriber" value="0" disabled>否
        <font color="#FF0000"><b>日期:</b></font><input class=tine type=text size=3 maxlength=4 name=year
                                                      value="<%=(year!=null)?year:""%>" disabled>年
        <select name=month size=1 class=tine disabled>
            <%for (int i = 1; i < 13; i++) {%>
            <option value=<%=i%> <%=(month == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>月
        <select name=day size=1 class=tine disabled>
            <%for (int i = 1; i < 32; i++) {%>
            <option value=<%=i%> <%=(day == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>日
        <select name=hour size=1 class=tine disabled>
            <%for (int i = 1; i < 24; i++) {%>
            <option value=<%=i%> <%=(hour == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>时
        <select name=minute size=1 class=tine disabled>
            <%for (int i = 1; i < 61; i++) {%>
            <option value=<%=i%> <%=(minute == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>分
    </td>
</tr>
<tr>
    <td>
        <iframe src="getContent.jsp?article=<%=articleID%>" width="100%" height="400" marginwidth="2" marginheight="2"
                scrolling="auto" frameborder="1"></iframe>
    </td>
</tr>
</table>
</form>
</body>
</html>
