<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
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
    String sitename = authToken.getSitename();
    String username = authToken.getUserID();
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    String act = ParamUtil.getParameter(request, "act");
    boolean doAudit = ParamUtil.getBooleanParameter(request, "doAudit");
    boolean doReturn = ParamUtil.getBooleanParameter(request, "doReturn");

    IAuditManager auditMgr = AuditPeer.getInstance();
    IArticleManager articleManager = ArticlePeer.getInstance();
    Article article = articleManager.getArticle(articleID);
    int columnID = article.getColumnID();

    //退稿
    if (doReturn) {
        int backtowho = ParamUtil.getIntParameter(request, "backtowho", -1);                 //退稿给某人
        String sign = StringUtil.iso2gb(ParamUtil.getParameter(request, "sign"));         //签名
        String comments = StringUtil.iso2gb(ParamUtil.getParameter(request, "comments"));     //建议
        String editors = StringUtil.iso2gb(ParamUtil.getParameter(request, "editors"));         //编辑
        String prevAuditor = StringUtil.iso2gb(ParamUtil.getParameter(request, "prevAuditor"));  //退稿给上一级审核者

        auditMgr.updateAudit_Info("", articleID, "", 3);  //更新原来的审核信息为过期信息

        Audit audit = new Audit();
        audit.setArticleID(articleID);
        audit.setSign(sign);
        audit.setComments(comments);

        if (backtowho == 0)        //退稿给作者
        {
            audit.setStatus(1);
            audit.setBackTo(editors);
        } else if (backtowho == 1)    //退稿给上一级审核人
        {
            audit.setStatus(2);
            audit.setBackTo(prevAuditor);
        }
        auditMgr.Create_Article_Audit_Info(audit);
        success = true;
    }

    //审核
    if (doAudit) {
        int isAudit = ParamUtil.getIntParameter(request, "isAudit", -1);                  //是否通过审核
        int backtowho = ParamUtil.getIntParameter(request, "backtowho", -1);                 //退稿给某人
        String sign = StringUtil.iso2gb(ParamUtil.getParameter(request, "sign"));         //签名
        String comments = StringUtil.iso2gb(ParamUtil.getParameter(request, "comments"));     //建议
        String editors = StringUtil.iso2gb(ParamUtil.getParameter(request, "editors"));         //编辑
        String prevAuditor = StringUtil.iso2gb(ParamUtil.getParameter(request, "prevAuditor"));  //退稿给上一级审核者

        Audit audit = new Audit();
        audit.setArticleID(articleID);
        audit.setEditor(username);
        audit.setSign(sign);
        audit.setComments(comments);
        audit.setStatus(backtowho);
        audit.setBackTo(editors);
        audit.setAuditor(prevAuditor);

        auditMgr.Auditing(audit, act, isAudit, siteId, columnID);
        success = true;
    }

    if (success) {
        out.println("<script LANGUAGE=JavaScript>opener.history.go(0);window.close();</script>");
        return;
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String columnName = StringUtil.gb2iso4View(column.getCName());
    int isAudited = column.getIsAudited();

    String maintitle = StringUtil.gb2iso4View(article.getMainTitle());
    String vicetitle = StringUtil.gb2iso4View(article.getViceTitle());
    String summary = StringUtil.gb2iso4View(article.getSummary());
    String keyword = StringUtil.gb2iso4View(article.getKeyword());
    String fileName = article.getFileName();
    int using = article.getStatus();
    int subscriber = article.getSubscriber();
    int docLevel = article.getDocLevel();
    String source = StringUtil.gb2iso4View(article.getSource());
    int sortid = article.getSortID();
    String year = article.getPublishTime().toString().substring(0, 4);
    String month = article.getPublishTime().toString().substring(5, 7);
    String day = article.getPublishTime().toString().substring(8, 10);
    String hour = article.getPublishTime().toString().substring(11, 13);
    String minute = article.getPublishTime().toString().substring(14, 16);
    int m_year = Integer.parseInt(year);
    int m_month = Integer.parseInt(month);
    int m_day = Integer.parseInt(day);
    int m_hour = Integer.parseInt(hour);
    int m_minute = Integer.parseInt(minute);
    String editors = article.getEditor();

    String fileDir = column.getDirName();
    String columnURL = "/webbuilder/sites/" + sitename + fileDir + "download" + "/" + fileName;

    String editor = "[" + username + "]";
    String prevAuditor = auditMgr.queryPrev_Auditor(editor, articleID);
    if (prevAuditor == null) prevAuditor = "";

    //如是修改,读出审核意见
    String comments = "";
    if ("update".equals(act))
        comments = auditMgr.getComments(username, articleID);
%>

<html>
<head>
    <title></title>
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <SCRIPT LANGUAGE=JavaScript>
        function OpenItem()
        {
            backto.style.display = "";
            noBgcolor.bgColor = "#EEEEEE";
            document.uploadfile.backtoauthor.checked = true;
        <%if (prevAuditor.length() > 0){%>
            document.uploadfile.backtoauditor.checked = false;
        <%}%>
        }

        function CloseItem()
        {
            backto.style.display = "none";
            noBgcolor.bgColor = "";
            document.uploadfile.backtoauthor.checked = false;
        <%if (prevAuditor.length() > 0){%>
            document.uploadfile.backtoauditor.checked = false;
        <%}%>
        }
    </SCRIPT>
</head>

<body LANGUAGE="javascript" bgcolor="#FFFFFF">
<%
    String[][] titlebars = {
            {"文件上传管理", "index.jsp"},
            {columnName, ""}
    };
    String[][] operations = {
    };
%>

<%@ include file="../inc/titlebar.jsp" %>
<form method="post" action="edituploadfile.jsp" name=uploadfile>
<input type=hidden name=act value="<%=act%>">
<input type="hidden" name=column value="<%=columnID%>">
<input type="hidden" name=article value="<%=articleID%>">
<input type="hidden" name="range" value="<%=range%>">
<input type="hidden" name="start" value="<%=start%>">
<input type="hidden" name="editors" value="<%=editors%>">
<table width="100%" border="1" align="center" bgcolor="#FFFFFF">
<%
    if (act.equals("return")) {
        String[] result = auditMgr.getArticleInfo(articleID, username, 2);
        if (result != null) {
%>
<input type="hidden" name="doReturn" value="true">
<tr>
    <td class=line bgcolor="#CECFCE" colspan=2>
        <font color=red><b>退稿人签名：</b></font>
        <input class=tine size=16 value="<%=(result[0]!=null)?result[0]:""%>" disabled>&nbsp;&nbsp;
        <font color=red><b>退稿意见：</b></font>
        <input class=tine size=50 value="<%=(result[1]!=null)?StringUtil.gb2iso4View(result[1]):""%>" disabled>&nbsp;&nbsp;
        <font color=red><b>退稿时间：</b></font><%=(result[2] != null) ? result[2] : ""%>
    </td>
</tr>
<%}%>
<tr>
    <td class=line colspan=2>
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
                <td width="10%">&nbsp;<input type="text" name="sign" size="16" value="<%=username%>" maxlength=50
                                             readonly style="background-color: #DDDDDD"></td>
                <td width="10%"><p align="right"><font color=red><b>建议：</b></font></td>
                <td width="40%" rowspan="2">&nbsp;<input type=text name="comments" size=40 maxlength=500></td>
            </tr>
        </table>
    </td>
</tr>
<%} else {%>
<input type="hidden" name="doAudit" value="true">
<tr>
    <td colspan="2">
        <table border="0" width="100%" cellspacing="0" cellpadding="0">
            <tr height=31>
                <td width="9%" align=left><font color=blue>是否通过：</font></td>
                <td width="12%" align=left>
                    <table border="0" width="100%" cellspacing="0" cellpadding="0" height=31>
                        <tr>
                            <td>
                                <input type="radio" value="1" checked name="isAudit" onclick="javascript:CloseItem();">是
                            </td>
                            <td name=noBgcolor id=noBgcolor>
                                <input type="radio" value="0" name="isAudit" onclick="javascript:OpenItem();">否
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="9%"><p align="right"><font color=blue>签名：</font></td>
                <td width="12%">&nbsp;<input type="text" name="sign" size="16" maxlength=50 readonly
                                             value="<%=username%>" style="background-color: #DDDDDD"></td>
                <td width="8%"><p align="right"><font color=blue>建议：</font></td>
                <td width="50%" rowspan="2">&nbsp;<textarea rows="3" name="comments"
                                                            cols="50"><%=(comments == null) ? "" : comments%>
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
    <td colspan="2" bgcolor="#dddddd" height=40>
        <div align="center">
            <input type="submit" value="  确定  ">&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="  返回  " onclick="window.close();">
        </div>
    </td>
</tr>
<tr>
    <td width="50%" bgcolor="#FFFFFF">标题：
        <input type=text class=tine name=maintitle size=30 value='<%=(maintitle!=null)?maintitle:""%>' disabled>
    </td>
    <td width="50%">副标题：
        <input type=text class=tine name=vicetitle size=30 value='<%=(vicetitle!=null)?vicetitle:"" %>' disabled>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF">摘要：
        <input type=text class=tine name=summary size=30 value='<%=(summary!=null)?summary:""%>' disabled>
    </td>
    <td>关键字：
        <input type=text class=tine name=keyword size=30 value='<%=(keyword!=null)?keyword:""%>' disabled>
        (多个以;隔开)
    </td>
</tr>
<tr>
<td bgcolor="#FFFFFF">来源：
    <input type=text class=tine name=source size=30 value='<%=(source!=null)?source:""%>' disabled>
</td>
<td>发布日期:
<input class=tine type=text size=3 maxlength=4 name=year value=<%=m_year%> disabled>
年
<select name=month size=1 class=tine disabled>
    <option value=1 <%= (m_month == 1) ? "selected" : "" %>>1</option>
    <option value=2 <%= (m_month == 2) ? "selected" : "" %>>2</option>
    <option value=3 <%= (m_month == 3) ? "selected" : "" %>>3</option>
    <option value=4 <%= (m_month == 4) ? "selected" : "" %>>4</option>
    <option value=5 <%= (m_month == 5) ? "selected" : "" %>>5</option>
    <option value=6 <%= (m_month == 6) ? "selected" : "" %>>6</option>
    <option value=7 <%= (m_month == 7) ? "selected" : "" %>>7</option>
    <option value=8 <%= (m_month == 8) ? "selected" : "" %>>8</option>
    <option value=9 <%= (m_month == 9) ? "selected" : "" %>>9</option>
    <option value=10 <%= (m_month == 10) ? "selected" : "" %>>10</option>
    <option value=11 <%= (m_month == 11) ? "selected" : "" %>>11</option>
    <option value=12 <%= (m_month == 12) ? "selected" : "" %>>12</option>
</select>
月
<select name=day size=1 class=tine disabled>
    <option value=1 <%= (m_day == 1) ? "selected" : "" %>>1</option>
    <option value=2 <%= (m_day == 2) ? "selected" : "" %>>2</option>
    <option value=3 <%= (m_day == 3) ? "selected" : "" %>>3</option>
    <option value=4 <%= (m_day == 4) ? "selected" : "" %>>4</option>
    <option value=5 <%= (m_day == 5) ? "selected" : "" %>>5</option>
    <option value=6 <%= (m_day == 6) ? "selected" : "" %>>6</option>
    <option value=7 <%= (m_day == 7) ? "selected" : "" %>>7</option>
    <option value=8 <%= (m_day == 8) ? "selected" : "" %>>8</option>
    <option value=9 <%= (m_day == 9) ? "selected" : "" %>>9</option>
    <option value=10 <%= (m_day == 10) ? "selected" : "" %>>10</option>
    <option value=11 <%= (m_day == 11) ? "selected" : "" %>>11</option>
    <option value=12 <%= (m_day == 12) ? "selected" : "" %>>12</option>
    <option value=13 <%= (m_day == 13) ? "selected" : "" %>>13</option>
    <option value=14 <%= (m_day == 14) ? "selected" : "" %>>14</option>
    <option value=15 <%= (m_day == 15) ? "selected" : "" %>>15</option>
    <option value=16 <%= (m_day == 16) ? "selected" : "" %>>16</option>
    <option value=17 <%= (m_day == 17) ? "selected" : "" %>>17</option>
    <option value=18 <%= (m_day == 18) ? "selected" : "" %>>18</option>
    <option value=19 <%= (m_day == 19) ? "selected" : "" %>>19</option>
    <option value=20 <%= (m_day == 20) ? "selected" : "" %>>20</option>
    <option value=21 <%= (m_day == 21) ? "selected" : "" %>>21</option>
    <option value=22 <%= (m_day == 22) ? "selected" : "" %>>22</option>
    <option value=23 <%= (m_day == 23) ? "selected" : "" %>>23</option>
    <option value=24 <%= (m_day == 24) ? "selected" : "" %>>24</option>
    <option value=25 <%= (m_day == 25) ? "selected" : "" %>>25</option>
    <option value=26 <%= (m_day == 26) ? "selected" : "" %>>26</option>
    <option value=27 <%= (m_day == 27) ? "selected" : "" %>>27</option>
    <option value=28 <%= (m_day == 28) ? "selected" : "" %>>28</option>
    <option value=29 <%= (m_day == 29) ? "selected" : "" %>>29</option>
    <option value=30 <%= (m_day == 30) ? "selected" : "" %>>30</option>
    <option value=31 <%= (m_day == 31) ? "selected" : "" %>>31</option>
</select>
日
<select name=hour size=1 class=tine disabled>
    <option value=1 <%= (m_hour == 1) ? "selected" : "" %>>1</option>
    <option value=2 <%= (m_hour == 2) ? "selected" : "" %>>2</option>
    <option value=3 <%= (m_hour == 3) ? "selected" : "" %>>3</option>
    <option value=4 <%= (m_hour == 4) ? "selected" : "" %>>4</option>
    <option value=5 <%= (m_hour == 5) ? "selected" : "" %>>5</option>
    <option value=6 <%= (m_hour == 6) ? "selected" : "" %>>6</option>
    <option value=7 <%= (m_hour == 7) ? "selected" : "" %>>7</option>
    <option value=8 <%= (m_hour == 8) ? "selected" : "" %>>8</option>
    <option value=9 <%= (m_hour == 9) ? "selected" : "" %>>9</option>
    <option value=10 <%= (m_hour == 10) ? "selected" : "" %>>10</option>
    <option value=11 <%= (m_hour == 11) ? "selected" : "" %>>11</option>
    <option value=12 <%= (m_hour == 12) ? "selected" : "" %>>12</option>
    <option value=13 <%= (m_hour == 13) ? "selected" : "" %>>13</option>
    <option value=14 <%= (m_hour == 14) ? "selected" : "" %>>14</option>
    <option value=15 <%= (m_hour == 15) ? "selected" : "" %>>15</option>
    <option value=16 <%= (m_hour == 16) ? "selected" : "" %>>16</option>
    <option value=17 <%= (m_hour == 17) ? "selected" : "" %>>17</option>
    <option value=18 <%= (m_hour == 18) ? "selected" : "" %>>18</option>
    <option value=19 <%= (m_hour == 19) ? "selected" : "" %>>19</option>
    <option value=20 <%= (m_hour == 20) ? "selected" : "" %>>20</option>
    <option value=21 <%= (m_hour == 21) ? "selected" : "" %>>21</option>
    <option value=22 <%= (m_hour == 22) ? "selected" : "" %>>22</option>
    <option value=23 <%= (m_hour == 23) ? "selected" : "" %>>23</option>
</select>时
<select name=minute size=1 class=tine disabled>
    <option value=1 <%= (m_minute == 1) ? "selected" : "" %>>1</option>
    <option value=2 <%= (m_minute == 2) ? "selected" : "" %>>2</option>
    <option value=3 <%= (m_minute == 3) ? "selected" : "" %>>3</option>
    <option value=4 <%= (m_minute == 4) ? "selected" : "" %>>4</option>
    <option value=5 <%= (m_minute == 5) ? "selected" : "" %>>5</option>
    <option value=6 <%= (m_minute == 6) ? "selected" : "" %>>6</option>
    <option value=7 <%= (m_minute == 7) ? "selected" : "" %>>7</option>
    <option value=8 <%= (m_minute == 8) ? "selected" : "" %>>8</option>
    <option value=9 <%= (m_minute == 9) ? "selected" : "" %>>9</option>
    <option value=10 <%= (m_minute == 10) ? "selected" : "" %>>10</option>
    <option value=11 <%= (m_minute == 11) ? "selected" : "" %>>11</option>
    <option value=12 <%= (m_minute == 12) ? "selected" : "" %>>12</option>
    <option value=13 <%= (m_minute == 13) ? "selected" : "" %>>13</option>
    <option value=14 <%= (m_minute == 14) ? "selected" : "" %>>14</option>
    <option value=15 <%= (m_minute == 15) ? "selected" : "" %>>15</option>
    <option value=16 <%= (m_minute == 16) ? "selected" : "" %>>16</option>
    <option value=17 <%= (m_minute == 17) ? "selected" : "" %>>17</option>
    <option value=18 <%= (m_minute == 18) ? "selected" : "" %>>18</option>
    <option value=19 <%= (m_minute == 19) ? "selected" : "" %>>19</option>
    <option value=20 <%= (m_minute == 20) ? "selected" : "" %>>20</option>
    <option value=21 <%= (m_minute == 21) ? "selected" : "" %>>21</option>
    <option value=22 <%= (m_minute == 22) ? "selected" : "" %>>22</option>
    <option value=23 <%= (m_minute == 23) ? "selected" : "" %>>23</option>
    <option value=24 <%= (m_minute == 24) ? "selected" : "" %>>24</option>
    <option value=25 <%= (m_minute == 25) ? "selected" : "" %>>25</option>
    <option value=26 <%= (m_minute == 26) ? "selected" : "" %>>26</option>
    <option value=27 <%= (m_minute == 27) ? "selected" : "" %>>27</option>
    <option value=28 <%= (m_minute == 28) ? "selected" : "" %>>28</option>
    <option value=29 <%= (m_minute == 29) ? "selected" : "" %>>29</option>
    <option value=30 <%= (m_minute == 30) ? "selected" : "" %>>30</option>
    <option value=31 <%= (m_minute == 31) ? "selected" : "" %>>31</option>
    <option value=32 <%= (m_minute == 32) ? "selected" : "" %>>32</option>
    <option value=33 <%= (m_minute == 33) ? "selected" : "" %>>33</option>
    <option value=34 <%= (m_minute == 34) ? "selected" : "" %>>34</option>
    <option value=35 <%= (m_minute == 35) ? "selected" : "" %>>35</option>
    <option value=36 <%= (m_minute == 36) ? "selected" : "" %>>36</option>
    <option value=37 <%= (m_minute == 37) ? "selected" : "" %>>37</option>
    <option value=38 <%= (m_minute == 38) ? "selected" : "" %>>38</option>
    <option value=39 <%= (m_minute == 39) ? "selected" : "" %>>39</option>
    <option value=40 <%= (m_minute == 40) ? "selected" : "" %>>40</option>
    <option value=41 <%= (m_minute == 41) ? "selected" : "" %>>41</option>
    <option value=42 <%= (m_minute == 42) ? "selected" : "" %>>42</option>
    <option value=43 <%= (m_minute == 43) ? "selected" : "" %>>43</option>
    <option value=44 <%= (m_minute == 44) ? "selected" : "" %>>44</option>
    <option value=45 <%= (m_minute == 45) ? "selected" : "" %>>45</option>
    <option value=46 <%= (m_minute == 46) ? "selected" : "" %>>46</option>
    <option value=47 <%= (m_minute == 47) ? "selected" : "" %>>47</option>
    <option value=48 <%= (m_minute == 48) ? "selected" : "" %>>48</option>
    <option value=49 <%= (m_minute == 49) ? "selected" : "" %>>49</option>
    <option value=50 <%= (m_minute == 50) ? "selected" : "" %>>50</option>
    <option value=51 <%= (m_minute == 51) ? "selected" : "" %>>51</option>
    <option value=52 <%= (m_minute == 52) ? "selected" : "" %>>52</option>
    <option value=53 <%= (m_minute == 53) ? "selected" : "" %>>53</option>
    <option value=54 <%= (m_minute == 54) ? "selected" : "" %>>54</option>
    <option value=55 <%= (m_minute == 55) ? "selected" : "" %>>55</option>
    <option value=56 <%= (m_minute == 56) ? "selected" : "" %>>56</option>
    <option value=57 <%= (m_minute == 57) ? "selected" : "" %>>57</option>
    <option value=58 <%= (m_minute == 58) ? "selected" : "" %>>58</option>
    <option value=59 <%= (m_minute == 59) ? "selected" : "" %>>59</option>
    <option value=60 <%= (m_minute == 60) ? "selected" : "" %>>60</option>
</select>分
</td>
</tr>
<tr>
    <td width="100%" bgcolor="#dddddd" colspan="2"><font color="#FF0000"><b>权重</b></font>：
        <input type=text name="docLevel" value="<%=docLevel%>" size=4 maxlength="5" disabled>
        &nbsp;&nbsp;&nbsp;<b><font color="#FF0000">使用</font></b>：
        <input type=radio name="using" value=0 <%= (using==0)?"checked":"" %> disabled>否
        <input type=radio name="using" value=1 <%= (using==1)?"checked":"" %> disabled>是
        &nbsp;&nbsp;&nbsp;<b><font color="#FF0000">订阅</font></b>：
        <input type=radio name="subscriber" value=0 <%= (subscriber==0)?"checked":"" %> disabled>否
        <input type=radio name="subscriber" value=1 <%= (subscriber==1)?"checked":"" %> disabled>是
        &nbsp;&nbsp;&nbsp;<b><font color="#FF0000">审核</font></b>：&nbsp;<%if (isAudited == 1) {%>需要<%} else {%>不需要<%}%>
        &nbsp;&nbsp;&nbsp;排序：<input class=tine name=sortid size=10 value=<%=sortid %> disabled>
    </td>
</tr>
<tr>
    <td colspan="2" bgcolor="#FFFFFF" height="36">
        原上传文件为：<a href=<%=columnURL%> target=_blank><%=maintitle%>
    </a>
    </td>
</tr>
</table>
</form>

</body>
</html>