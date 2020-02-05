<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.pic.IPicManager" %>
<%@ page import="com.bizwink.cms.pic.Pic" %>
<%@ page import="com.bizwink.cms.pic.PicPeer" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.upload.RandomStrg" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.upload.MultipartFormHandle" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.sitesetting.*" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();

    String dir = "";
    String tempDir = "";
    int columnID = 0;
    String baseDir = application.getRealPath("/");
    String attrname = request.getParameter("attr");
    String filename = request.getParameter("picname1");
    String status = request.getParameter("status");
    columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String fileDir = column.getDirName();
    String mtitle_picwh = column.getTitlepic();
    String vtitle_picwh = column.getVtitlepic();
    String source_picwh = column.getSourcepic();
    String author_picwh = column.getAuthorpic();
    String special_picwh = column.getSpecialpic();
    String big_picwh = column.getProductpic();
    String small_picwh = column.getProductsmallpic();

    ISiteInfoManager siteInfoManager = SiteInfoPeer.getInstance();
    SiteInfo siteInfo = siteInfoManager.getSiteInfo(siteid);
    if (mtitle_picwh==null) mtitle_picwh = siteInfo.getTitlepic();
    if (vtitle_picwh==null) vtitle_picwh = siteInfo.getVtitlepic();
    if (source_picwh==null) source_picwh = siteInfo.getSourcepic();
    if (author_picwh==null) author_picwh = siteInfo.getAuthorpic();
    if (special_picwh==null) special_picwh = siteInfo.getSpecialpic();
    if (big_picwh==null) big_picwh = siteInfo.getProductpic();
    if (small_picwh==null) small_picwh = siteInfo.getProductsmallpic();

    int hostID = 0;
    if (status == null) {
        RandomStrg rstr = new RandomStrg();
        rstr.setCharset("a-z0-9");
        rstr.setLength(8);
        rstr.generateRandomObject();
        filename = "pic" + rstr.getRandom();

        tempDir = StringUtil.replace(fileDir, "/", File.separator);
        dir = baseDir + "sites" + File.separator + sitename;
    } else {
        tempDir = "/webbuilder/sites/" + sitename + fileDir + "images/";
    }

    List picList = new ArrayList();
    //获得所有发布主机列表
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
    if (status != null) {
        if (attrname.equalsIgnoreCase("mt")) {
            filename = request.getParameter("title_pic");
        } else if (attrname.equalsIgnoreCase("vt")) {
            filename = request.getParameter("vtitle_pic");
        } else if (attrname.equalsIgnoreCase("au")) {
            filename = request.getParameter("author_pic");
        } else if (attrname.equalsIgnoreCase("sr")) {
            filename = request.getParameter("souecr_pic");
        } else if (attrname.equalsIgnoreCase("pic")) {
            filename = request.getParameter("prod_small_pic");
        } else if (attrname.equalsIgnoreCase("bigpic")) {
            filename = request.getParameter("prod_large_pic");
        } else if (attrname.equalsIgnoreCase("apic")) {
            filename = request.getParameter("special_pic");
        }
        String notes = request.getParameter("notes");
        if (notes != null) {
            notes = new String(notes.getBytes(), "utf-8");
            notes = StringUtil.iso2gbindb(notes);
        }

        if (hostID > 0) {
            FtpInfo ftpInfo = ftpsetMgr.getFtpInfo(hostID);
            String docPath = ftpInfo.getDocpath();
            if (docPath.length() > 0 && !docPath.startsWith("/"))
                docPath = "/" + docPath;
            if (docPath.length() > 0 && docPath.endsWith("/"))
                docPath = docPath.substring(0, docPath.length() - 1);
            filename = "http://" + ftpInfo.getSiteName() + docPath + fileDir + "images/" + filename;
            Pic pic = new Pic();
            pic.setSiteid(siteid);
            pic.setColumnid(columnID);
            pic.setWidth(0);
            pic.setHeight(0);
            pic.setPicsize(0);
            pic.setImgurl(filename);
            pic.setPicname(filename);
            pic.setNotes(notes);
            picList.add(pic);
        } else {
            Pic pic = new Pic();
            pic.setSiteid(siteid);
            pic.setColumnid(columnID);
            pic.setWidth(0);
            pic.setHeight(0);
            pic.setPicsize(0);
            pic.setImgurl(tempDir + filename);

            pic.setPicname(filename);
            pic.setNotes(notes);
            picList.add(pic);
        }
    }
    //图片信息入库

    IPicManager picMgr = PicPeer.getInstance();
    picMgr.createPic(picList);
%>

<html>
<head>
    <title>上传文件</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="Javascript">
        <%if (status != null){%>
        var attrname = "<%=attrname%>";
        var filename = "<%=filename%>";

        if (attrname == "mt")
            window.opener.document.getElementById('maintitle').value = filename;
        else if (attrname == "vt")
            window.opener.document.getElementById('vicetitle').value = filename;
        else if (attrname == "au")
            window.opener.document.getElementById('author').value = filename;
        else if (attrname == "sr")
            window.opener.document.getElementById('source').value = filename;
        else if (attrname == "createDesc")
            window.opener.document.getElementById('desc').value = filename;
        else if (attrname == "updateDesc")
            window.opener.document.getElementById('desc').value = filename;
        else if (attrname == "pic")
            window.opener.document.getElementById('pic').value = filename;
        else if (attrname == "bigpic")
            window.opener.document.getElementById('bigpic').value = filename;
        else if (attrname == "apic")
            window.opener.document.getElementById('articlepic').value = filename;
        else
            window.opener.document.getElementById('<%=attrname%>').value = filename;

        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            window.returnValue = "";
            top.close();
        } else {
            top.close();
        }
        <%}%>

        function validate()
        {
            if (document.form1.filename.value == "")
            {
                alert("请选择文件！");
                return false;
            }
            return true;
        }

        function getValue(id){
            id.select();
            id.blur();
            return document.selection.createRange().text;
        }

        function f(theimg) {
            var val = theimg.value;
            var filevalue=getValue(theimg);
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;

            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = filevalue;
                //d.style.width = d.offsetWidth;
                //d.style.height = d.offsetHeight;
                if (d.offsetWidth<400)
                    d.style.width = d.offsetWidth;
                else
                    d.style.width = 400;
                if (d.offsetHeight<280)
                    d.style.height = d.offsetHeight;
                else
                    d.style.height = 280;
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';
                validate = true;
            }
            else if (ext == ".swf")
            {
                validate = true;
            }
            else
            {
                if (!validate)
                {
                    alert("只能上传图像及FLASH文件！");
                }
            }
        }

        function cal() {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                top.close();
            } else {
                top.close();
            }
        }
    </script>
</head>

<body bgcolor="#cccccc">
<form name="form1" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&sitename=<%=sitename%>" method="post"
      enctype="multipart/form-data" onSubmit="return validate();">
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/upload.jsp">
    <input type="hidden" name="status" value="save">
    <input type="hidden" name="picname" value="<%=filename%>">
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name="baseDir" value="<%=baseDir%>">
    <input type="hidden" name="fileDir" value="<%=tempDir%>">
    <input type="hidden" name="sitename" value="<%=sitename%>">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="attr" value="<%=attrname%>">
    <input type="hidden" name="fromflag" value="article">
    <input type="hidden" name="tcflag" value="<%=authToken.getPublishFlag()%>">
    <table align="center" width="100%" border=0>
        <tr height=30>
            <td>选择文件：<input type=file name="filename" size=31 onpropertychange="f(this)"></td>
        </tr>

        <%if (attrname.equalsIgnoreCase("mt")) {%>
        <tr height=30>
            <td>图片（标题）长度和宽度设置（WxH）：<input type="text" name="title_pic" size=10 value="<%=(mtitle_picwh==null)?"":mtitle_picwh%>" readonly="true"></td>
        </tr>
        <%} else if (attrname.equalsIgnoreCase("vt")) {%>
        <tr height=30>
            <td>图片（副标题）长度和宽度设置（WxH）：<input type="text" name="vtitle_pic" size=10 value="<%=(vtitle_picwh==null)?"":vtitle_picwh%>" readonly="true"></td>
        </tr>
        <%} else if (attrname.equalsIgnoreCase("au")) {%>
        <tr height=30>
            <td>图片（作者）长度和宽度设置（WxH）：<input type="text" name="author_pic" size=10 value="<%=(author_picwh==null)?"":author_picwh%>" readonly="true"></td>
        </tr>
        <%} else if (attrname.equalsIgnoreCase("sr")) {%>
        <tr height=30>
            <td>图片（来源）长度和宽度设置（WxH）：<input type="text" name="source_pic" size=10 value="<%=(source_picwh==null)?"":source_picwh%>" readonly="true"></td>
        </tr>
        <%} else if (attrname.equalsIgnoreCase("pic")) {%>
        <tr height=30>
            <td>图片（小图片）长度和宽度设置（WxH）：<input type="text" name="prod_small_pic" size=10 value="<%=(small_picwh==null)?"":small_picwh%>" readonly="true"></td>
        </tr>
        <%} else if (attrname.equalsIgnoreCase("bigpic")) {%>
        <tr height=30>
            <td>图片（大图片）长度和宽度设置（WxH）：<input type="text" name="prod_large_pic" size=10 value="<%=(big_picwh==null)?"":big_picwh%>" readonly="true"></td>
        </tr>
        <%} else if (attrname.equalsIgnoreCase("apic")) {%>
        <tr height=30>
            <td>图片（文章特效图片）长度和宽度设置（WxH）：<input type="text" name="special_pic" size=10 value="<%=(special_picwh==null)?"":special_picwh%>" readonly="true"></td>
        </tr>
        <%}%>

        <tr height=30>
            <td>选择主机：<select name="hostID" size=1 style="width:250;font-size:9pt">
                <option value="0">默认发布主机</option>
                <%
                    for (int i = 0; i < siteList.size(); i++) {
                        FtpInfo ftpinfo = (FtpInfo) siteList.get(i);
                        if (ftpinfo.getStatus() == 0) {
                            String siteName = ftpinfo.getSiteName();
                            if (siteName == null || siteName.length() == 0) siteName = sitename;
                %>
                <option value="<%=ftpinfo.getID()%>" selected><%=siteName%>
                </option>
                <%
                        }
                    }
                %></select>
            </td>
        </tr>
        <tr height=30>
            <td>图片描述：<input type=text name="notes" size=45></td>
        </tr>
        <tr height=35>
            <td align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="submit" value="  上传  " class=tine>&nbsp;&nbsp;
                <input type="button" value="  取消  " class=tine onClick="cal();">
            </td>
        </tr>
        <tr>
            <td valign="top" bgcolor="#ffffff">
                <!--<iframe src="showImage.html" width="400" marginwidth="0" height="280" marginheight="0" scrolling="auto" frameborder="1" name=frm></iframe>-->
                <h1 id=d
                    style="border:0px solid black;filter : progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);WIDTH: 400px; HEIGHT: 280px">
                </h1>
            </td>
        </tr>
    </table>

</form>

</body>
</html>
