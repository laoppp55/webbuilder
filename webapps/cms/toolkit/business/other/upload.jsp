<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.jspsmart.upload.*,
                 com.booyee.other.*"
                 contentType="text/html;charset=gbk"
%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
      response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
      return;
    }

    String userid = authToken.getUserID();
    int siteid = 1;

    String filename = "";
    String ext = "";

    IOtherManager otherMgr = OtherPeer.getInstance();

    String appDir = request.getRealPath("/");
    if( appDir.endsWith( java.io.File.separator ) )
        appDir = appDir.substring( 0,appDir.lastIndexOf( java.io.File.separator ) );
    appDir = appDir.substring(0,appDir.indexOf("admin"));
    String imgdir = appDir + java.io.File.separator + "uploadfiles";

    SmartUpload mySmartUpload = new SmartUpload();
    mySmartUpload.initialize(this.getServletConfig(),request,response);
    com.jspsmart.upload.File  tmpFile = null;

    try {
            mySmartUpload.upload();
            tmpFile = mySmartUpload.getFiles().getFile(0);

            filename = tmpFile.getFileName();
            filename = "uploadfiles" + java.io.File.separator + filename;

            String picType = tmpFile.getContentType().substring(1);
            java.io.File dirFile = new java.io.File( imgdir );

            if ( !dirFile.exists()) {
                dirFile.mkdirs();
            }

            mySmartUpload.save( imgdir );

            //存入数据库
            otherMgr.updatePicture(filename);

            out.println("<script language=\"javascript\">");
            out.println("alert(\"图片上传成功！\")");
            out.println("window.close();");
            out.println("</script>");
    }catch ( Exception e ) {

      e.printStackTrace();
    }

%>
<html>
<body>
OK
</body>
</html>


