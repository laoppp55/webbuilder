<%@ page import="java.util.*,com.jspsmart.upload.*,com.bizwink.wenba.*" contentType="text/html;charset=GBK"%>
<%
	String userid = request.getParameter("userid")==null?"0":request.getParameter("userid");
	System.out.println("userid:"+userid);
  	SmartUpload uploader = new com.jspsmart.upload.SmartUpload();
  	uploader.initialize(pageContext);
  	uploader.upload();
  	//图片
  	File uploadedFile = uploader.getFiles().getFile(0);
  	String file_name = uploadedFile.getFileName();
  	System.out.println("filename:"+file_name);
  	String pathUrl1;
  	String rName = "";
  	if (file_name == null || file_name.equals("")) {
  		pathUrl1 = null;
  	} else {
  		rName = userid
  				+ "." + uploader.getFiles().getFile(0).getFileExt();
  		pathUrl1 = application.getRealPath("/") + "wenba" + java.io.File.separator + "images" + java.io.File.separator ;
  		System.out.println("rName:"+rName);
  		System.out.println("path:"+pathUrl1);
  		java.io.File dirFile = new java.io.File( pathUrl1 );
		if ( !dirFile.exists()) {
		    dirFile.mkdirs();
		}
		pathUrl1 = pathUrl1 + rName;
        uploadedFile.saveAs(pathUrl1);
  	}
  	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
    int result = 0;
    result = iwenba.edituserimg(Integer.parseInt(userid),pathUrl1,rName);
    if(result==0){
    	out.print("<script type=\"text/javascript\">");
		out.print("alert('上传图片成功');");
		out.print("window.history.back();");
		out.print("</script>");
    }else{
    	out.print("<script type=\"text/javascript\">");
		out.print("alert('上传图片失败!');");
		out.print("window.history.back();");
		out.print("</script>");
    }
  %>


