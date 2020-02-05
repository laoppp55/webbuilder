<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="java.io.File" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.io.FilenameUtils" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="org.apache.commons.fileupload.DiskFileUpload" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-11
  Time: 下午3:25
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int encodeflag = 0;
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteID = authToken.getSiteID();

    System.out.println("params===" + request.getQueryString());

    Enumeration enu=request.getParameterNames();
    while(enu.hasMoreElements()){
        String paraName=(String)enu.nextElement();
        System.out.println(paraName+": "+request.getParameter(paraName));
    }

    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    //判断是从工具棒上传视频文件还是从视频输入框上传视频文件
    int fromflag = ParamUtil.getIntParameter(request, "fromflag", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    if (columnID == 0) {
        String idstr = (String) session.getAttribute("createtemplate_columnid");
        if ((idstr != null) && (!idstr.equals("null")) && (!idstr.equals("")))
            columnID = Integer.parseInt(idstr);
    }

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String dirName = column.getDirName();
    String domain = StringUtil.replace(sitename, "_", ".");
    System.out.println("fromflag=" + fromflag);
    ErrorMessage errorMessage = new ErrorMessage();

    try{
        String returnvalue = "";
        String newfilename = "";
        String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename + dirName + "images" + java.io.File.separator;
        uploadPath = StringUtil.replace(uploadPath, "/", java.io.File.separator);
        java.io.File file = new java.io.File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        DiskFileUpload upload=new DiskFileUpload();
        List uploadlist=upload.parseRequest(request);

        System.out.println(uploadlist.size());
        Iterator iter=uploadlist.iterator();
        if (iter.hasNext()) {
            FileItem  item=(FileItem)iter.next();
            //System.out.println(item.isFormField());
            if(!item.isFormField()) {
                String filename=item.getName();
                if(!filename.equals("")) {
                    filename= FilenameUtils.getName(filename);
                    int posi = filename.lastIndexOf(".");
                    String ext = null;
                    if (posi>-1) ext = filename.substring(posi+1);

                    if (!ext.equalsIgnoreCase("flv"))
                        newfilename = username + String.valueOf(System.currentTimeMillis()) + "." + ext;
                    else
                        newfilename =username + String.valueOf(System.currentTimeMillis()) + ".flv";

                    String savepath=uploadPath + newfilename;
                    File saveFilepath=new File(savepath);
                    item.write(saveFilepath);
                    String newname = newfilename.substring(0,newfilename.lastIndexOf(".")) + ".flv";
                    String filepath = "sites/" + sitename + dirName +"images/"+newname;

                    //如果上传的是FLV格式的文件，则直接将该文件发布到WEB服务器
                    if (ext.equalsIgnoreCase("flv")) {
                        IPublishManager publishMgr = PublishPeer.getInstance();
                        int errcode1 = publishMgr.publish(username, savepath, siteID, dirName + "images" + File.separator, 0);
                    }

                    errorMessage.setErrmsg("上传多媒体文件成功");
                    errorMessage.setErrcode(0);
                    errorMessage.setModelname("多媒体文件上传");

                        /*if (fromflag == 0) {    //从工具棒上传视频文件
                            returnvalue = returnvalue + "[TAG][MEDIA][FILENAME]" + filepath + "[/FILENAME][/MEDIA][/TAG]";
                            out.println("<script language=javascript>");
                            out.println("window.opener.top.FCKeditorAPI.GetInstance(\"content\").InsertHtml(\"" + returnvalue + "\");");
                            out.println("window.opener.top.document.createForm.mediafilename.value=window.opener.top.document.createForm.mediafilename.value + '" + newname + ":" + filename + "|';");
                            out.println("top.close();");
                            out.println("</script>");
                        } else {
                            out.println("<script language=javascript>");
                            out.println("window.opener.top.document.createForm.mediaid.value='" + newname + "'");
                            out.println("window.opener.top.document.createForm.mediafilename.value=window.opener.top.document.createForm.mediafilename.value + '" + newname + ":" + filename + "|';");
                            out.println("top.close();");
                            out.println("</script>");
                        }*/
                } else {
                    errorMessage.setErrmsg("上传文件名为空，上传文件失败");
                    errorMessage.setErrcode(-1);
                    errorMessage.setModelname("多媒体文件上传");
                }
            }
        }
    }catch(Exception ex){
        errorMessage.setErrmsg("上传多媒体文件报错");
        errorMessage.setErrcode(-2);
        errorMessage.setModelname("多媒体文件上传");
        System.out.println("程序发生错误，抛出异常为 "+ex.getMessage());
    }
%>
