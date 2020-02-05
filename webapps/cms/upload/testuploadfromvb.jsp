<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.io.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.bizwink.cms.util.*"%>
<%@ page contentType="text/html;charset=utf-8" %>

<%
    //Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    //if (authToken == null) {
    //    System.out.println("session 已经丢失");
    //    response.getWriter().println("hello word\r\n") ;
    //response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    //return;
    //}

    int start = ParamUtil.getIntParameter(request,"startflag",0);

    System.out.println(start);
    if (start == 1) {
        try
        {
            DiskFileUpload upload=new DiskFileUpload();
            List uploadlist=upload.parseRequest(request);

            System.out.println(uploadlist.size());
            Iterator iter=uploadlist.iterator();
            while(iter.hasNext())
            {
                FileItem  item=(FileItem)iter.next();
                System.out.println(item.isFormField());
                if(!item.isFormField())
                {
                    String filename=item.getName();
                    filename=FilenameUtils.getName(filename);
                    if(!filename.equals(""))
                    {
                        System.out.println("start"+filename);
                        String savepath="D:\\"+filename;
                        System.out.println("savepath is "+savepath);
                        File saveFilepath=new File(savepath);
                        item.write(saveFilepath);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("程序发生错误，抛出异常为 "+ex.getMessage());
        }
    }
%>
<html>
<head>
    <title>测试大文件上传</title>
</head>
<body>
<form action="testuploadfromvb.jsp?startflag=1" name="one" enctype="multipart/form-data" method="post">
    <input name="startflag" type="hidden" value="1">
    <p align="center">文件上传
        <input type="File" name="fileupload" value="upload" />
        <input type="submit" value="上传">
        <input type="reset" value="取消">
    </p>
</form>
</body>
</html>

