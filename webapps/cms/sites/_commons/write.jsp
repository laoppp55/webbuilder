<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 java.sql.Date,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.webapps.register.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK" %>

<%
    Uregister ug = (Uregister)session.getAttribute("UserLogin");
    String sitename = request.getServerName();
    String underline_sitename = StringUtil.replace(sitename,".","_");
    if (ug == null) {
        response.sendRedirect( "/" + underline_sitename + "/_prog/login.jsp");
        return;
    }

    int siteid = ug.getSiteid();
    String username = ug.getMemberid();
    String fromurl = ParamUtil.getParameter(request, "fromurl") ;
    if (fromurl == null) fromurl = request.getHeader("REFERER");
    //System.out.println("fromurl=" + fromurl);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String maintitle = null, vicetitle = null, content = null, summary = null,
            keyword = null, source = null, year = null,month = null, day = null,
            hour = null, minute = null, author = null;
    boolean errorTitle = false;
    boolean errorContent = false;
    boolean errors = false;
    boolean success = false;
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

    if (doCreate) {
        maintitle = ParamUtil.getParameter(request, "maintitle");
        maintitle = (maintitle != null) ? maintitle.trim() : maintitle;
        vicetitle = ParamUtil.getParameter(request, "vicetitle");
        content = ParamUtil.getParameter(request, "content");
        author = ParamUtil.getParameter(request, "author");
        summary = ParamUtil.getParameter(request, "summary");
        keyword = ParamUtil.getParameter(request, "keyword");
        source = ParamUtil.getParameter(request, "source");
        year = ParamUtil.getParameter(request, "year");
        month = ParamUtil.getParameter(request, "month");
        day = ParamUtil.getParameter(request, "day");
        hour = ParamUtil.getParameter(request, "hour");
        minute = ParamUtil.getParameter(request, "minute");

        if (content != null) {
            content = StringUtil.replace(content, "cmstextarea", "textarea").trim();
        }
        if (maintitle == null) errorTitle = true;
        if (content == null) errorContent = true;
        errors = errorTitle || errorContent;
    }

    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    Article article = new Article();
    if (!errors && doCreate) {
        try {
            article.setSiteID(siteid);
            article.setColumnID(columnID);
            article.setMainTitle(maintitle);
            article.setViceTitle(vicetitle);
            article.setContent(content);
            article.setAuthor(author);
            article.setSummary(summary);
            article.setKeyword(keyword);
            article.setPubFlag(1);
            article.setSource(source);
            article.setEditor(username);
            Timestamp create_date = new Timestamp(System.currentTimeMillis());
            article.setCreateDate(create_date);
            article.setLastUpdated(create_date);
            article.setDocLevel(0);
            article.setViceDocLevel(0);
            article.setAuditFlag(1);
            article.setStatus(0);
            article.setModelID(0);
            article.setSubscriber(0);
            article.setUrltype(0);
            article.setNullContent(0);
            article.setT1(0);
            article.setT2(0);
            article.setT3(0);
            article.setT4(0);
            article.setT5(0);

            if (year == null)
                article.setPublishTime(new Timestamp(System.currentTimeMillis()));
            else {
                Timestamp publishtime = new Timestamp(Integer.parseInt(year)-1900, Integer.parseInt(month)-1,Integer.parseInt(day),Integer.parseInt(hour),Integer.parseInt(minute),0,0);
                article.setPublishTime(publishtime);
            }

            //文章轮换图片
            List turnpic = (List)session.getAttribute("turn_pic");
            extendMgr.create(null, null, article, null,turnpic);
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            errors = true;
        }
    }

    if (success) {
        response.sendRedirect(response.encodeRedirectURL(fromurl + "?column=" + columnID));
        return;
    }
%>

<html>
<head>
    <title>创建文章</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link REL="stylesheet" TYPE="text/css" HREF="/_sys_css/editor.css">
    <script language="JavaScript" src="/_sys_js/setday.js"></script>
    <script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
    <script LANGUAGE="JavaScript" SRC="../toolbars/dhtmled.js"></script>
    <script language=javascript>
        function cal() {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                top.close();
            } else {
                top.close();
            }
        }

        function create(createForm)
        {
            if (createForm.maintitle.value == "")
            {
                alert("主标题不能为空！");
                createForm.maintitle.focus();
                return;
            }
            createForm.submit();
        }

        function upload_attrpic_onclick(attrname)
        {
            window.open("/_commons/upload.jsp?column=<%=columnID%>&attr=" + attrname, "", 'width=400,height=400,left=200,top=200');
        }

    </script>
</head>
<body>
<form action=write.jsp method=post name=createForm>
    <input type=hidden name=doCreate value="true">
    <input type=hidden name=filename>
    <input type=hidden name=fromurl value="<%=fromurl%>">
    <input type=hidden name=modelSourceCodeFlag value=0>
    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr height=40>
            <td width="25%" align=right>
                <input class=tine type=button value="  保存  " onclick="create(createForm);">&nbsp;&nbsp;
                <input class=tine type=button value="  取消  " onclick="cal();">&nbsp;&nbsp;
            </td>
        </tr>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr>
            <td colspan=3 height=4></td>
        </tr>
    </table>
    <table border=1 borderColorDark="#ffffe0c" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
        <tr bgcolor="#eeeeee">
            <td class=line>
                标题：(<a href="javascript:upload_attrpic_onclick('mt');">图</a>)<input class=tine name=maintitle id="maintitle" size=36 onDblClick="history('mt');" title="双击鼠标左键即可选择历史图片">
                副标题：(<a href="javascript:upload_attrpic_onclick('vt')">图</a>)<input class=tine name=vicetitle  id="vicetitle" size=20 onDblClick="history('vt');" title="双击鼠标左键即可选择历史图片">
                作者：(<a href="javascript:upload_attrpic_onclick('au')">图</a>)<input class=tine name=author id="author" size=10 onDblClick="history('au');" title="双击鼠标左键即可选择历史图片">
                来源：(<a href="javascript:upload_attrpic_onclick('sr')">图</a>)<input class=tine name=source id="source" size=10 onDblClick="history('sr');" title="双击鼠标左键即可选择历史图片">
            </td>
        </tr>
        <tr bgcolor="#ffffff">
            <td class=line>
                摘要：<textarea cols="100" rows="2" id="summaryID" name="summary"></textarea>
                <font color="#FF0000"><b>关键字(;)</b></font><input class=tine name=keyword size=30>
            </td>
        </tr>

        <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
            <td>
                <input type=button name=articletype id=turnpic size=30 value="上传文章轮换图片" onclick="javascript:upload_turn_pic();">&nbsp;<a href="turnpic.jsp?column=0"  class="tine" target="_blank">查看已上传图片</a></td>
        </tr>
    </table>
    <table border="0" width="100%">
        <tr>
            <td>
                <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"></textarea>
                <script type="text/javascript">
                    var oFCKeditor = new FCKeditor('content') ;
                    oFCKeditor.BasePath = "../fckeditor/";
                    oFCKeditor.Height = 520;
                    oFCKeditor.ToolbarSet = "ArticleDefault";
                    oFCKeditor.ReplaceTextarea();
                </script>
            </td>
        </tr>
    </table>
</form>
</body>
</html>