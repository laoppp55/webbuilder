<%@ page import="java.sql.*,
                 java.io.*,
	         com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
		 com.jspsmart.upload.*,
                 com.bizwink.cms.tree.*,
				 com.bizwink.cms.publish.*,
		 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%  ////////////////////////////////
    // Retreive parameters
   Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
   if( authToken == null ) {
     response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
     return;
   }
    String username = authToken.getUserID();

    Tree colTree = TreeManager.getInstance().getTree();
    IArticleManager articleMgr = ArticlePeer.getInstance();

    int columnID = ParamUtil.getIntParameter(request,"column",0);
    boolean doUpload = ParamUtil.getBooleanParameter(request,"doUpload");
    String author = authToken.getUserID();
    String baseURL = authToken.getURL();
    String baseDir = authToken.getPath();
    String tempDir = colTree.getDirName(colTree,columnID);
    tempDir = StringUtil.replace(tempDir,"/",java.io.File.separator);
    String dir = baseDir + tempDir+"download" + java.io.File.separator;
    String tcdir = baseDir + java.io.File.separator + "tc" + tempDir + "download" + java.io.File.separator;
    String filename = null;
    String ext = null;
    boolean error = false;
    boolean noContent = true;

    SmartUpload mySmartUpload = new SmartUpload();
    mySmartUpload.initialize(this.getServletConfig(),request,response);
    com.jspsmart.upload.File  tmpFile = null;
    try {
        mySmartUpload.upload();
		tmpFile = mySmartUpload.getFiles().getFile(0);
		String picType = tmpFile.getContentType().substring(1);

        //保存简体文件
		java.io.File dirFile = new java.io.File( dir );
		if ( !dirFile.exists()) {
		    dirFile.mkdirs();
		}
		mySmartUpload.save( dir);

        //保存繁体文件
        dirFile = new java.io.File(tcdir);
        if ( !dirFile.exists()) {
            dirFile.mkdirs();
        }
        mySmartUpload.save( tcdir);

		filename = tmpFile.getFileName();
/*****************************************************************************************
* this added by zhangyong used to distribute upload file to remote
*/
        String localFileName = tempDir+"download"+ java.io.File.separator + filename;
        String tcLocalFileName =  java.io.File.separator + "tc" + tempDir + "download" + java.io.File.separator+filename;

        IPublishManager publishManager = PublishPeer.getInstance();
		try {
            //发布简体文件
			int errcode = publishManager.publish(username,localFileName,baseURL,baseDir,0);
			if (errcode != 0)  error = true;

            //发布繁体文件
            errcode = publishManager.publish(username,tcLocalFileName,baseURL,baseDir,0);
            if (errcode != 0) error = true;
        }
		catch( PublishException e ) {
				response.sendRedirect(response.encodeRedirectURL("error.jsp?&msg=文章发布失败!"));
		}
//************************************************************************************/

    }catch ( Exception e ) {
      System.out.println(e.getMessage());
      error = true;
    }

    //保存数据进入数据库
    String maintitle  = ParamUtil.getParameter(request,"maintitle");
    String vicetitle  = ParamUtil.getParameter(request,"vicetitle");
    String summary  = ParamUtil.getParameter(request,"summary");
    String keyword  = ParamUtil.getParameter(request,"keyword");
    String source  = ParamUtil.getParameter(request,"source");
    int doclevel  = ParamUtil.getIntParameter(request,"doclevel",0);
    int sortid  = ParamUtil.getIntParameter(request,"sortid",0);
    String year = ParamUtil.getParameter(request,"year");
    String month = ParamUtil.getParameter(request,"month");
    String day = ParamUtil.getParameter(request,"day");
    String hour = ParamUtil.getParameter(request,"hour");

    if(doUpload && !error) {
        try {
            Article article = new Article();
            article.setMainTitle(maintitle);
            article.setViceTitle(vicetitle);
            article.setSummary(summary);
            article.setKeyword(keyword);
            article.setSource(source);
            if (year==null) {
                article.setPublishTime(new Timestamp(System.currentTimeMillis()));
            }else{
                 article.setPublishTime(Timestamp.valueOf(year+"-"+month+"-"+day+" "+hour+":"+ 0 +":00.000000000"));
            }
            article.setDocLevel(doclevel);
            article.setSortID(sortid);
            article.setFileName(filename);
            article.setColumnID(columnID);
            article.setEditor(author);
            article.setCreateDate(new Timestamp(System.currentTimeMillis()));
            article.setLastUpdated(new Timestamp(System.currentTimeMillis()));

            //在上传文件管理中未使用的字段
            article.setAuditFlag(0);
            article.setPubFlag(0);
            article.setStatus(1);
            article.setContent("");
            article.setDirName("");
            articleMgr.create(article);
            articleMgr.addChEnPath(article);
        } catch (ArticleException e) {
            System.out.println(e.getMessage());
        }
    }
%>
<html>
<head>
<script language="javascript">
function refreshlist() {
   opener.location.href="listuploadfiles.jsp?column=" + "<%=columnID%>";
    window.close();
}
</script>
</head>
<body onload="javascript:refreshlist()">
</body>
