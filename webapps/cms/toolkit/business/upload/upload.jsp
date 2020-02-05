<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.jspsmart.upload.*,
                 com.booyee.bookincome.*"
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
    int num = ParamUtil.getIntParameter(request, "num", 0);
    long bookid = ParamUtil.getLongParameter(request, "bookid", 0);
    String filename = "";
    String ext = "";

    IBookincomeManager bookincomeMgr = BookincomePeer.getInstance();

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
            if(num == 1){
              bookincomeMgr.updateBookPic(bookid,filename,1);
            }else if(num == 2){
              bookincomeMgr.updateBookPic(bookid,filename,2);
            }else if(num == 3){
              bookincomeMgr.updateBookPic(bookid,filename,3);
            }else if(num == 4){
              bookincomeMgr.updateBookPic(bookid,filename,4);
            }else if(num == 5){
              bookincomeMgr.updateBookPic(bookid,filename,5);
            }else if(num == 6){
              bookincomeMgr.updateBookPic(bookid,filename,6);
            }else if(num == 7){
              bookincomeMgr.updateBookPic(bookid,filename,7);
            }else if(num == 8){
              bookincomeMgr.updateBookPic(bookid,filename,8);
            }else if(num == 9){
              bookincomeMgr.updateBookPic(bookid,filename,9);
            }else if(num == 10){
              bookincomeMgr.updateBookPic(bookid,filename,10);
            }

            out.println("<script language=\"javascript\">");
            out.println("alert(\"图片"+num+"上传成功！\")");
            out.println("opener.history.go(0);");
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


