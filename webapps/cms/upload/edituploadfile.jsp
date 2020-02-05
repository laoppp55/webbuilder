<%@ page import="java.io.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    String username = authToken.getUserID();
    String company = authToken.getDepartment();

    String maintitle = null;
    String vicetitle = null;
    String summary = null;
    String keyword = null;
    String source = null;
    int using = 1;
    int subscriber = 0;
    int docLevel = 0;
    int sortid = 0;
    int m_year = 0;
    int m_month = 0;
    int m_day = 0;
    int m_hour = 0;
    int m_minute = 0;

    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    String status = ParamUtil.getParameter(request, "status");
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    int arttypeval = ParamUtil.getIntParameter(request, "arttype", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");

    IAuditManager auditMgr = AuditPeer.getInstance();
    IArticleManager articleManager = ArticlePeer.getInstance();
    Article article = articleManager.getArticle(articleID);
    IColumnManager columnManager = ColumnPeer.getInstance();

    int columnID = article.getColumnID();
    Column column = columnManager.getColumn(columnID);
    String dirname = column.getDirName();
    String columnName = StringUtil.gb2iso4View(column.getCName());
    int isAudited = column.getIsAudited();
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isPosition = column.getIsPosition();                                   //是否包含地理位置信息

    maintitle = StringUtil.gb2iso4View(article.getMainTitle());
    vicetitle = StringUtil.gb2iso4View(article.getViceTitle());
    summary = StringUtil.gb2iso4View(article.getSummary());
    keyword = StringUtil.gb2iso4View(article.getKeyword());
    String fileName = article.getFileName();
    using = article.getStatus();
    subscriber = article.getSubscriber();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal_today = Calendar.getInstance();
    String d = formatter.format(cal_today.getTime());

    //文章状态
    docLevel = article.getDocLevel();
    source = StringUtil.gb2iso4View(article.getSource());
    sortid = article.getSortID();
    String year = article.getPublishTime().toString().substring(0, 4);
    String month = article.getPublishTime().toString().substring(5, 7);
    String day = article.getPublishTime().toString().substring(8, 10);
    String hour = article.getPublishTime().toString().substring(11, 13);
    String minute = article.getPublishTime().toString().substring(14, 16);
    String ymd = year + month + day;

    m_year = Integer.parseInt(year);
    m_month = Integer.parseInt(month);
    m_day = Integer.parseInt(day);
    m_hour = Integer.parseInt(hour);
    m_minute = Integer.parseInt(minute);

    String fileDir = column.getDirName();
    String baseDir = application.getRealPath("/");
    String tempDir = StringUtil.replace(fileDir, "/", File.separator);
    String dir = baseDir + "sites" + File.separator + sitename;

    String[] result = auditMgr.getArticleInfo(articleID, username, 1);

    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();

    out.println("<script>");
    if (status!=null) {
        out.println("window.opener.articlesInfoList(" + columnID + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + "," + "null" + "," + "null" + "," + "null" + "," + arttypeval + ");");
        out.println("alert('" + "上传文件修改成功！！！" +"');");
        //out.println("window.opener.updateUploadFileListData(" + columnID + "," + articleID + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ");");
        out.println("window.close();");
    }
    out.println("</script>");

%>

<html>
<head>
    <title></title>
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script language=JavaScript1.2 src="../js/functions.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        function opencalendar(baseURL){
            openPopup(baseURL, "小日历", 350, 300, "width=300,height=180,toolbar=no,status=no,directories=no,menubar=no,resizable=yes,scrollable=no", true);
        }

        function upload_attrpic_onclick(attrname){
            window.open("upload.jsp?column=<%=columnID%>&attr=" + attrname, "", 'width=400,height=400,left=200,top=200');
        }

        function check(){
            if (uploadfile.maintitle.value == ""){
                alert("文件标题不能为空！");
                return false;
            }else{
                return true;
            }
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
<form method="post" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&dir=<%=dir%>" name="uploadfile"
      enctype="multipart/form-data" onsubmit="javascript:return check();">
    <input type="hidden" name="doUpdate" value="true">
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name=isDefine value=<%=isDefine%>>
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/edituploadfile.jsp">
    <input type="hidden" name="article" value="<%=articleID%>">
    <input type="hidden" name="filename" value="<%=fileName%>">
    <input type="hidden" name="status" value="update">
    <input type="hidden" name="baseDir" value="<%=baseDir%>">
    <input type="hidden" name="fileDir" value="<%=tempDir%>">
    <input type="hidden" name="sitename" value="<%=sitename%>">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="fromflag" value="file">
    <input type="hidden" name="tcflag" value="<%=authToken.getPublishFlag()%>">
    <input type=hidden name=range value="<%=range%>">
    <input type=hidden name=start value="<%=currentPage%>">
    <input type=hidden name=flag value="<%=flag%>">
    <input type=hidden name=ascdesc value="<%=ascdesc%>">
    <input type=hidden name=arttype value="<%=arttypeval%>">
    <input type=hidden name=searchFlag value="<%=doSearchFlag%>">

    <table width="100%" border="1" align="center" bgcolor="#dddddd">
        <%if (result != null) {%>
        <tr>
            <td class=line colspan=2>
                <font color=red><b>退稿人签名：</b></font>
                <input class=tine size=16 value="<%=(result[0]!=null)?result[0]:""%>" disabled>&nbsp;&nbsp;
                <font color=red><b>退稿意见：</b></font>
                <input class=tine size=50 value="<%=(result[1]!=null)?StringUtil.gb2iso4View(result[1]):""%>" disabled>&nbsp;&nbsp;
                <font color=red><b>退稿时间：</b></font><%=(result[2] != null) ? result[2] : ""%>
            </td>
        </tr>
        <%}%>
        <tr>
            <td width="50%">标题：<input class=tine name=maintitle size=40 value="<%=(maintitle!=null)?maintitle:""%>">
            </td>
            <td width="50%">副标题：<input class=tine name=vicetitle size=40 value="<%=(vicetitle!=null)?vicetitle:""%>">
            </td>
        </tr>
        <tr>
            <td>摘要：<input class=tine name=summary size=40 value="<%=(summary!=null)?summary:""%>"></td>
            <td>关键字：<input class=tine name=keyword size=40 value="<%=(keyword!=null)?keyword:""%>">(多个以;隔开)</td>
        </tr>
        <tr>
            <td>来源：<input class=tine name=source size=40 value='<%=(source!=null)?source:""%>'></td>
            <td>发布日期：
                <input class=tine size=3 maxlength=4 name=year value=<%=m_year%>>年
                <select name=month size=1 class=tine>
                    <%for (int i = 1; i < 13; i++) {%>
                    <option value=<%=i%> <%=(m_month == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>月
                <select name=day size=1 class=tine>
                    <%for (int i = 1; i < 32; i++) {%>
                    <option value=<%=i%> <%=(m_day == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>日
                <select name=hour size=1 class=tine>
                    <%for (int i = 1; i < 24; i++) {%>
                    <option value=<%=i%> <%=(m_hour == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>时
                <select name=minute size=1 class=tine>
                    <%for (int i = 1; i < 61; i++) {%>
                    <option value=<%=i%> <%=(m_minute == i) ? "selected" : ""%>><%=i%>
                    </option>
                    <%}%>
                </select>分
                <input type=hidden name=sd value="">
                <a href="JavaScript:opencalendar('calendar.jsp?form=uploadfile&ip=sd&d=<%=d%>')"
                   onclick="setLastMousePosition(event)" tabindex="3"><img src="../images/date_picker.gif" border="0"
                                                                           width="34" height="21" align="absmiddle"
                                                                           border=0></a>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <b><font color="#FF0000">主权重</font></b>：<input name="doclevel" value="<%=docLevel%>" size=6
                                                               maxlength="5">&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">排序</font></b>：<input class=tine name=sortid size=6 value="<%=sortid%>">&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">使用</font></b>：<input type=radio name="using"
                                                              value=0 <%=(using==0)?"checked":""%>>否<input type=radio
                                                                                                           name="using"
                                                                                                           value=1 <%=(using==1)?"checked":""%>>是&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">订阅</font></b>：<input type=radio name="subscriber"
                                                              value=0 <%= (subscriber==0)?"checked":"" %>>否<input
                    type=radio name="subscriber" value=1 <%=(subscriber==1)?"checked":""%>>是&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">审核</font></b>：<%if (isAudited == 1) {%>需要<%} else {%>不需要<%}%>
            </td>
        </tr>
        <tr>
            <td colspan="2" bgcolor="#FFFFFF" height="36">
                原上传文件为：
                <font color=red><a
                        href="/webbuilder/sites/<%=sitename%><%=dirname%><%=ymd%>/download/<%=fileName%>"><%=fileName%>
                </a></font>
            </td>
        </tr>

        <%if (isDefine == 1) {%>
        <%if ((isProduct == 0 && isPosition==0) || (isProduct == 1 && isPosition==1)) {%>
        <tr bgcolor="#eeeeee">
                <%} else { %>
        <tr bgcolor="#ffffff">
            <%}%>
            <td class=line colspan="2"><%=extendMgr.getExtendAttrForArticle(company,username,columnID, articleID)%>
            </td>
        </tr>
        <%}%>

        <tr bgcolor="#eeeeee">
            <td height="36">简体文件：<input type=file id="sfilename" size=40 name="sfilename"></td>
            <td height="36">下载：<input type=file id="downsfilename" size=40 name="downsfilename"></td>
        </tr>
        <tr>
            <td height="36">繁体文件：<input type=file id="tfilename" size=40 name="tfilename"></td>
            <td height="36">下载：<input type=file id="downtfilename" size=40 name="downtfilename"></td>
        </tr>
        <tr>
            <td colspan="2" align="center" height=60>
                <input type="submit" value="  修改  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  返回  " class=tine onclick="window.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>