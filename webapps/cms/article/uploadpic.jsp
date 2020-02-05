<%@ page import="com.bizwink.cms.security.*,
		 com.jspsmart.upload.*,
		 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%	////////////////////////////////
    // Retreive parameters
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
   if( authToken == null ) {
     response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
     return;
   }
   if ( authToken != null && !SecurityCheck.hasPermission(authToken,"article")) {
	    return;
    }
    String dir = (authToken==null)?"":authToken.getPath();
    dir = dir + java.io.File.separator + "images" + java.io.File.separator + "upload" + java.io.File.separator;
    //dir = dir + java.io.File.separator + "images" + java.io.File.separator;
    out.println(dir);
    // check for errors
    String fileName = null, ext = null;
    fileName = ParamUtil.getParameter(request, "fileUpload");
    out.println(fileName);
    SmartUpload mySmartUpload = new SmartUpload();
    // Initialization
    mySmartUpload.initialize(this.getServletConfig(),request,response);
    // Upload
    com.jspsmart.upload.File  tmpFile = null;
    try {
                mySmartUpload.upload();
		tmpFile = mySmartUpload.getFiles().getFile(0);
		String picType = tmpFile.getContentType().substring(1);

		if(!("image/gif".equals(picType)||"image/pjpeg".equals(picType ) )) {
		    return;
                }

		ext = tmpFile.getFileExt();
		ext = ext.toLowerCase();
		if(! ("gif".equals(ext)||"jpeg".equals(ext)||"jpg".equals(ext)) ) 	{
		    return;
		}

		java.io.File dirFile = new java.io.File( dir );
		if ( !dirFile.exists()) {
		    dirFile.mkdirs();
		}

		mySmartUpload.save( dir);

		fileName = tmpFile.getFileName();
	    }catch ( Exception e ) {
		    e.printStackTrace();
		    return;
	    }
//	    response.sendRedirect(
//		   response.encodeRedirectURL("http://www.shebhome.com")
//	);

%>