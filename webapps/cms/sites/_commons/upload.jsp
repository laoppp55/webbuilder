<%@ page import="java.sql.*,
                 java.io.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*,
		         com.jspsmart.upload.*,
		         com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%  ////////////////////////////////
    String baseDir = request.getRealPath("/");
    String dir = baseDir + java.io.File.separator + "sitespic" + java.io.File.separator;
    boolean error = false;
    String filename = "";
    int siteid = 0;
    int startnum = 0;
    int doUpload = 0;

    SmartUpload mySmartUpload = new SmartUpload();
    mySmartUpload.initialize(this.getServletConfig(),request,response);
    com.jspsmart.upload.File  tmpFile = null;
    try {
        mySmartUpload.upload();
        siteid = Integer.parseInt(mySmartUpload.getRequest().getParameter("site"));
        startnum = Integer.parseInt(mySmartUpload.getRequest().getParameter("startnum"));
        doUpload = Integer.parseInt(mySmartUpload.getRequest().getParameter("doUpload"));
        tmpFile = mySmartUpload.getFiles().getFile(0);
        filename = tmpFile.getFileName();
        String picType = tmpFile.getContentType().substring(1);

        //简体文件
        java.io.File dirFile = new java.io.File( dir );
        if ( !dirFile.exists()) {
            dirFile.mkdirs();
        }
        mySmartUpload.save(dir);
    }catch ( Exception e ) {
        System.out.println(e.getMessage());
        error = true;
    }

    if(doUpload==1 && !error) {
        IRegisterManager regMgr = RegisterPeer.getInstance();
        regMgr.update_sitepic(siteid,filename);
    }
%>
<html>
<head>
    <script language="javascript">
        //function refreshlist() {
        //    opener.location.href="../member/siteManage.jsp?startnum=" + "<%=startnum%>";
        //    window.close();
        //}
    </script>
</head>
<body>
<form name="form1" action="do_uploadsitepic.jsp" method="post" enctype="multipart/form-data" onsubmit="return validate();">
    <input type="hidden" name="doUpload" value="1">
    <input type="hidden" name="site" value="<%=siteid%>">
    <input type="hidden" name="startnum" value="<%=startnum%>">
    <table align="center" width="90%" border=0>
        <tr height=30>
            <td>选择文件：<input type=file name="filename" size=31 onpropertychange="f(this)"></td>
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
