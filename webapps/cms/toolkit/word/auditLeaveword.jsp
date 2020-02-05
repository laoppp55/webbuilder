<%@ page import="java.sql.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.xml.XMLProperties,
                 com.bizwink.cms.markManager.*,
                 com.bizwink.webapps.leaveword.*"
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
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    IMarkManager markMgr = markPeer.getInstance();

    boolean doAudit = ParamUtil.getBooleanParameter(request, "doAudit");
    boolean doReturn = ParamUtil.getBooleanParameter(request, "doReturn");
    int lwID = ParamUtil.getIntParameter(request, "id", 0);
    int formid = ParamUtil.getIntParameter(request, "markid", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String act = ParamUtil.getParameter(request, "act");
    String prevAuditor = null;
    String comments = null;
    String editor = null;        //文章编辑者

    String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(formid));
    str = StringUtil.replace(str, "[", "<");
    str = StringUtil.replace(str, "]", ">");
    str = StringUtil.replace(str, "{^", "[");
    str = StringUtil.replace(str, "^}", "]");
    //System.out.println(str);

    //获取审核规则定义信息
    XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
    String auditRule = properties.getProperty(properties.getName().concat(".AUDITRULE"));
    auditRule = auditRule.substring(0,auditRule.length() - 1);

    System.out.println("doReturn=" + doReturn);
    System.out.println("doAudit=" + doAudit);
    //读出文章
    if (doReturn)//退稿
    {
        int backtowho = ParamUtil.getIntParameter(request, "backtowho", -1);                //退稿给某人
        prevAuditor = StringUtil.iso2gb(ParamUtil.getParameter(request, "prevAuditor"));  //退稿给上一级审核者
        comments = StringUtil.iso2gb(ParamUtil.getParameter(request, "comments"));        //建议
        editor = StringUtil.iso2gb(ParamUtil.getParameter(request, "editor"));        //编辑

        auditMgr.updateAudit_Info("", lwID, "", 3,1);    //更新原来的审核信息为过期信息
        Audit audit = new Audit();
        audit.setArticleID(lwID);
        audit.setSign(username);
        audit.setComments(comments);
        audit.setAudittype(1);

        if (backtowho == 0)            //退稿给作者
        {
            audit.setStatus(1);
            audit.setBackTo(editor);
        } else if (backtowho == 1)    //退稿给上一级审核人
        {
            audit.setStatus(2);
            audit.setBackTo(prevAuditor);
        }
        auditMgr.Create_Article_Audit_Info(audit);
        success = true;
    }

    //审核文章
    if (doAudit) {
        int isAudit = ParamUtil.getIntParameter(request, "isAudit", -1);                     //是否通过审核
        int backtowho = ParamUtil.getIntParameter(request, "backtowho", -1);                 //退稿给某人
        prevAuditor = StringUtil.iso2gb(ParamUtil.getParameter(request, "prevAuditor"));     //退稿给上一级审核者
        comments = StringUtil.iso2gb(ParamUtil.getParameter(request, "comments"));           //建议
        editor = StringUtil.iso2gb(ParamUtil.getParameter(request, "editor"));               //编辑

        Audit audit = new Audit();
        audit.setArticleID(lwID);
        audit.setEditor(username);
        audit.setSign(username);
        audit.setComments(comments);
        audit.setAudittype(1);
        audit.setStatus(backtowho);
        if (backtowho == 0)
            audit.setBackTo(editor);
        else if (backtowho == 1)
            audit.setBackTo(prevAuditor);

        auditMgr.AuditingLeaveword(audit, auditRule,act, isAudit, siteId, formid);
        success = true;
    }

    if (success) {
        out.println("<script LANGUAGE=JavaScript>opener.history.go(0);window.close();</script>");
        return;
    }

    Word word = wordMgr.getAWord(lwID);
    String title = StringUtil.gb2iso4View(word.getTitle());
    String content = StringUtil.gb2iso4View(word.getContent());
    String retcontent = StringUtil.gb2iso4View(word.getRetcontent());
    String writedtae = word.getWritedate().toString();

    //判断有无上一级审核人
    prevAuditor = auditMgr.queryPrev_Auditor("[" + username + "]", lwID,siteId);
    if (prevAuditor == null) prevAuditor = "";

    //如是修改,读出审核意见
    if ("update".equals(act)) comments = auditMgr.getComments(username, lwID);
%>

<html>
<head>
    <title>审核文章</title>
    <link REL="stylesheet" TYPE="text/css" HREF="../../style/global.css">
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
            {"审核用户留言回复信息", ""}
    };
    String[][] operations = {
            {"<a href='javascript:createForm.submit();'><img src='../../images/button_modi.gif' border=0></a>", ""},
            {"<a href='javascript:window.close();'><img src='../../images/button_cancel.gif' border=0></a>", ""}
    };
%>
<%@ include file="../../inc/titlebar.jsp" %>

<form action="auditLeaveword.jsp" method="post" name=createForm>
    <input type="hidden" name="act" value="<%=act%>">
    <input type="hidden" name="markid" value="<%=formid%>">
    <input type="hidden" name="editor" value="<%=editor%>">
    <input type="hidden" name="id" value="<%=lwID%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="start" value="<%=start%>">
    <table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
        <%
            if (act.equals("return")) {
                String[] result = auditMgr.getArticleInfo(lwID, username, 2);
                if (result != null) {
        %>
        <input type="hidden" name="doReturn" value="true">
        <tr>
            <td class=line bgcolor="#CECFCE">
                <font color=red><b>退稿人签名：</b></font>
                <input class=tine size=16 value="<%=(result[0]!=null)?result[0]:""%>" disabled>&nbsp;&nbsp;
                <font color=red><b>退稿意见：</b></font>
                <input class=tine size=50 value="<%=(result[1]!=null)?StringUtil.gb2iso4View(result[1]):""%>" disabled>&nbsp;&nbsp;
                <font color=red><b>退稿时间：</b></font><%=(result[2] != null) ? result[2] : ""%>    </td>
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
                            <%}%>                </td>
                        <td width="10%"><p align="right"><font color=red><b>签名：</b></font></td>
                        <td width="10%">&nbsp;<input size="16" class=tine value="<%=username%>" disabled></td>
                        <td width="10%"><p align="right"><font color=red><b>建议：</b></font></td>
                        <td width="40%" rowspan="2">&nbsp;<input name="comments" size=40 maxlength=500></td>
                    </tr>
                </table>    </td>
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
                                    <td><input type="radio" value="1" checked name="isAudit" onClick="javascript:CloseItem();">是                            </td>
                                    <td name=noBgcolor id=noBgcolor><input type="radio" value="0" name="isAudit"
                                                                           onclick="javascript:OpenItem();">否                            </td>
                                </tr>
                            </table>                </td>
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
                                            <%}%>                                </td>
                                    </tr>
                                </table>
                            </div>                </td>
                    </tr>
                </table>    </td>
        </tr>
        <%}%>
        <tr>
            <td height="26" class=line>留言标题：
                <input class=tine name=maintitle size=50 value="<%=title%>" readonly="true">
                &nbsp;&nbsp;&nbsp;留言时间：
                <input name="writedate" type="text" value="<%=writedtae%>" readonly="true" class=tine size=35></td>
        </tr>
        <tr>
            <td height="26" class=line>留言内容：
                <label>
                    <textarea name="content" id="contentid" cols="100" rows="10" readonly="true"><%=(content==null)?"":content%></textarea>
                </label></td>
        </tr>
        <tr>
            <td height="26" class=line>回复内容：
                <textarea name="retcontent" id="retcontentid" cols="100" rows="10" readonly="true"><%=(retcontent==null)?"":retcontent%></textarea></td>
        </tr>
    </table>
</form>
</body>
</html>
