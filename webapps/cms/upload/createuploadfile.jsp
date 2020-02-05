<%@ page import="java.io.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    String company = authToken.getDepartment();
    int columnID  = ParamUtil.getIntParameter(request, "column", 0);
    int articleid = ParamUtil.getIntParameter(request, "articleid", 0);
    int year = ParamUtil.getIntParameter(request, "year", 0);
    int month = ParamUtil.getIntParameter(request, "month", 0);
    int day = ParamUtil.getIntParameter(request, "day", 0);
    int hour = ParamUtil.getIntParameter(request, "hour", 0);
    int minute = ParamUtil.getIntParameter(request, "minute", 0);
    int doclevel = ParamUtil.getIntParameter(request, "doclevel", 0);
    String maintitle = ParamUtil.getParameter(request,"maintitle");
    String status = ParamUtil.getParameter(request, "status");
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    int arttypeval = ParamUtil.getIntParameter(request, "arttype", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");


    System.out.println("articleid==" + articleid);
    System.out.println("status==" + status);
    System.out.println("range==" + range);
    System.out.println("currentPage==" + currentPage);
    System.out.println("flag==" + flag);
    System.out.println("ascdesc==" + ascdesc);
    System.out.println("doSearchFlag==" + doSearchFlag);

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String fileDir = column.getDirName();
    String CName = StringUtil.gb2iso4View(column.getCName());
    int isAudited = column.getIsAudited();
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isPosition = column.getIsPosition();                                   //是否包含地理位置信息

    String baseDir = application.getRealPath("/");
    String tempDir = StringUtil.replace(fileDir, "/", File.separator);
    String dir = baseDir + "sites" + File.separator + sitename;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal_today = Calendar.getInstance();

    int m_day = cal_today.get(Calendar.DAY_OF_MONTH);
    int m_month = cal_today.get(Calendar.MONTH) + 1;
    int m_hour = cal_today.get(Calendar.HOUR_OF_DAY);
    int m_minute = cal_today.get(Calendar.MINUTE);
    int m_year = cal_today.get(Calendar.YEAR);
    String d = formatter.format(cal_today.getTime());

    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    out.println("<script>");
    if (articleid>0) {
        out.println("window.opener.articlesInfoList(" + columnID + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + "," + "null" + "," + "null" + "," + "null" + "," + arttypeval + ");");
        out.println("alert('" + "文件上传成功！！！" +"');");
        //out.println("window.opener.createUploadFileListData(" + columnID + "," + articleid + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ");");
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
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <SCRIPT LANGUAGE=JavaScript>
        function opencalendar(baseURL){
            openPopup(baseURL, "小日历", 350, 300, "width=250,height=125,toolbar=no,status=no,directories=no,menubar=no,resizable=yes,scrollable=no", true);
        }

        function upload_attrpic_onclick(attrname){
            window.open("upload.jsp?column=<%=columnID%>&attr=" + attrname, "", 'width=400,height=400,left=200,top=200');
        }

        function check(){
            if (uploadfile.maintitle.value == "") {
                alert("文件标题不能为空！");
                return false;
            } else if (uploadfile.sfilename.value == "") {
                alert("请选择文件！");
                return false;
            } else {
                return true;
            }
        }
    </SCRIPT>
</head>

<body>
<%
    String[][] titlebars = {
            { "文件上传", "index.jsp" },
            { CName, "" }
    };
    String[][] operations = {
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<form method="post" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&sitename=<%=sitename%>" name=uploadfile enctype="multipart/form-data" onsubmit="javascript:return check();">
    <input type="hidden" name=column value=<%=columnID%>>
    <input type="hidden" name=isDefine value=<%=isDefine%>>
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/createuploadfile.jsp">
    <input type="hidden" name="status" value="save">
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
    <table border="0">
        <tr>
            <td>录入上传文件的信息</td>
        </tr>
    </table>
    <table border=1 borderColorDark="#ffffe0c" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
        <tr bgcolor="#eeeeee">
            <td width="40%">标题：<input class=tine name=maintitle size=30></td>
            <td width="60%">副标题：<input class=tine name=vicetitle size=30></td>
        </tr>
        <tr>
            <td>摘要：<input class=tine name=summary size=30></td>
            <td>关键字：<input class=tine name=keyword size=30>(以;隔开) </td>
        </tr>
        <tr bgcolor="#eeeeee">
            <td>来源：<input class=tine name=source size=30></td>
            <td>发布日期：
                <input  class=tine type=text size=3 maxlength=4 name=year value=<%=m_year%>>年
                <select name=month size=1 class=tine>
                    <%for(int i=1; i<13; i++){%><option value=<%=i%> <%=(m_month==i)?"selected":""%>><%=i%></option><%}%>
                </select>月
                <select name=day size=1 class=tine>
                    <%for(int i=1; i<32; i++){%><option value=<%=i%> <%=(m_day==i)?"selected":""%>><%=i%></option><%}%>
                </select>日
                <select name=hour size=1 class=tine>
                    <%for(int i=1; i<24; i++){%><option value=<%=i%> <%=(m_hour==i)?"selected":""%>><%=i%></option><%}%>
                </select>时
                <select name=minute size=1 class=tine>
                    <%for(int i=1; i<61; i++){%><option value=<%=i%> <%=(m_minute==i)?"selected":""%>><%=i%></option><%}%>
                </select>分
                <input type=hidden name=sd value="">
                <a href="JavaScript:opencalendar('calendar.jsp?form=uploadfile&ip=sd&d=<%=d%>')" onclick="setLastMousePosition(event)" tabindex="3"><img src="../images/date_picker.gif" border="0" width="34" height="21" align="absmiddle" border=0></a>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <b><font color="#FF0000">主权重</font></b>：<input name="doclevel" value="0" size=4>&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">排序</font></b>：<input class=tine name=sortid size=5 value=0>&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">使用</font></b>：<input type=radio name="using" value="0">否<input type=radio  checked name="using" value="1">是&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">订阅</font></b>：<input type=radio name="subscriber" value="0">否<input type=radio  checked name="subscriber" value="1">是&nbsp;&nbsp;&nbsp;
                <b><font color="#FF0000">审核</font></b>：&nbsp;<%if(isAudited==1){%>需要<%}else{%>不需要<%}%>
            </td>
        </tr>
        <%if (isDefine == 1) {%>
        <%if ((isProduct == 0 && isPosition==0) || (isProduct == 1 && isPosition==1)) {%>
        <tr bgcolor="#eeeeee">
                <%} else { %>
        <tr bgcolor="#ffffff">
            <%}%>
            <td class=line colspan="2"><%=extendMgr.getExtendAttrForArticle(company,username,columnID, 0)%>
            </td>
        </tr>
        <%}%>
        <tr bgcolor="#eeeeee">
            <td height="36">简体文件：<input type=file id="sfilename" size=40 name="sfilename" value="browse"></td>
            <td height="36">下载：<input type=file id="downsfilename" size=40 name="downsfilename" value="browse"></td>
        </tr>
        <tr>
            <td height="36">繁体文件：<input type=file id="tfilename" size=40 name="tfilename" value="browse"></td>
            <td height="36">下载：<input type=file id="downtfilename" size=40 name="downtfilename" value="browse"></td>
        </tr>
        <tr bgcolor="#eeeeee">
            <td colspan="2" align="center" height=60>
                <input type="submit" value="  上传  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  返回  " class=tine onclick="window.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>