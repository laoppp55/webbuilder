<%@ page import="java.sql.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*,
		 com.jspsmart.upload.*,
		 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%  ////////////////////////////////
    // Retreive parameters
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    String baseDir = request.getRealPath("/");
    String dir = baseDir + java.io.File.separator + "sitespic" + java.io.File.separator;
    int errcode = 0;
    String filename = "";
    int siteid = 0;
    int currentpage = 0;
    int range = 0;
    int doSearchFlag = 0;
    int doUpload = 0;

    SmartUpload mySmartUpload = new SmartUpload();
    mySmartUpload.initialize(this.getServletConfig(),request,response);
    com.jspsmart.upload.File  tmpFile = null;
    try {
        mySmartUpload.upload();
        siteid = Integer.parseInt(mySmartUpload.getRequest().getParameter("site"));
        currentpage = Integer.parseInt(mySmartUpload.getRequest().getParameter("pageno"));
        range = Integer.parseInt(mySmartUpload.getRequest().getParameter("range"));
        doSearchFlag = Integer.parseInt(mySmartUpload.getRequest().getParameter("searchflag"));
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
        errcode = -1;
    }

    if(doUpload==1 && errcode==0) {
        IRegisterManager regMgr = RegisterPeer.getInstance();
        regMgr.update_sitepic(siteid,filename);
    }
%>
<html>
<head>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script language="javascript">
        $(document).ready(function(){
            var doupload=<%=doUpload%>;
            var errcode = <%=errcode%>;
            if (errcode === 0 && doupload==1) {
                window.opener.userInfoList(<%=range%>,<%=currentpage%>,<%=doSearchFlag%>);
                window.close();
            } else {
                window.close();
            }
        });
    </script>
</head>
<body>
</body>
</html>
