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
<%@ page import="com.bizwink.cms.sitesetting.FtpSetting" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpInfo" %>
<%@ page import="com.bizwink.cms.sitesetting.IFtpSetManager" %>
<%@ page import="com.bizwink.upload.MultipartFormHandle" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
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
    String filename = request.getParameter("picname1");
    String prod_large_pic = ParamUtil.getParameter(request,"prod_large_pic");
    String prod_small_pic = ParamUtil.getParameter(request,"prod_small_pic");
    int spicflag = ParamUtil.getIntParameter(request,"spic",0);
    String status = request.getParameter("status");
    columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();
    String fileDir = columnMgr.getColumn(columnID).getDirName();
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
    String attrname = request.getParameter("attr");
    //获得所有发布主机列表
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
    if (status != null) {
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
            com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/publish/siteconfig.properties");
            String sinolube = "http://" + props.getProperty("main" + ".site.sinolube");
            String sinolubeen = "http://" + props.getProperty("main" + ".site.sinolubeen");            
            if (siteid == 3) {
                tempDir = tempDir.substring(34, tempDir.length());
                filename = sinolube + tempDir + filename;
            }
            if (siteid == 4) {
                tempDir = tempDir.substring(34, tempDir.length());
                filename = sinolubeen + tempDir + filename;
            }
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
        else if (attrname == "pic") {
            <% if (spicflag>0) {%>
               window.opener.document.getElementById('pic').value = "<%=prod_small_pic%>";
               window.opener.document.getElementById('bigpic').value = "<%=prod_large_pic%>";
            <%} else {%>
               window.opener.document.getElementById('pic').value = filename;
            <%}%>
        }
        else if (attrname == "bigpic") {
            <% if (spicflag>0) {%>
               window.opener.document.getElementById('pic').value = "<%=prod_small_pic%>";
               window.opener.document.getElementById('bigpic').value = "<%=prod_large_pic%>";
            <%} else {%>
               window.opener.document.getElementById('bigpic').value = filename;
            <%}%>
        }
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

        function f(theimg) {
            var val = theimg.value;
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;

            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = theimg.value;
                d.style.width = d.offsetWidth;
                d.style.height = d.offsetHeight;
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
<form name="form1" action="<%=request.getContextPath()%>/multipartformserv?dir=<%=dir%>" method="post"
      enctype="multipart/form-data" onSubmit="return validate();">
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/upload_zhanqu.jsp">
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
    <table align="center" width="90%" border=0>
        <tr height=30>
            <td>选择文件：<input type=file name="filename" size=31 onpropertychange="f(this)"></td>
        </tr>
        <tr height=30>
            <td>选择主机：<select name="hostID" size=1 style="width:190;font-size:9pt">
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

                生成小图片：
                <select name="spic" size=1 style="width:80;font-size:9pt">
                    <option value="0" selected>没有选择</option>
                    <option value="1">150X120</option>
                    <option value="2">300X240</option>
                    <option value="3">600X450</option>
                </select>
            </td>
        </tr>
        <tr height=30>
            <td>图片描述：<input type=text name="notes" size=31></td>
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
