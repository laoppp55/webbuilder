<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.jspsmart.upload.File" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    boolean error = true;
    String pic1 = "";
    String pic2 = "";
    String pic3 = "";
    String pic4 = "";
    String pic5 = "";
    String pic6 = "";
    String pics1 = "";
    String pics2 = "";
    String pics3 = "";
    String pics4 = "";
    String pics5 = "";
    String pics6 = "";
    String address1 = "";
    String address2 = "";
    String address3 = "";
    String address4 = "";
    String address5 = "";
    String address6 = "";
    String date = "8";
    String filename = "";
    String uploadPath = "";
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    if(doCreate){
        com.jspsmart.upload.File tmpFile = null;
        try {
            //实例化上载bean
            SmartUpload mySmartUpload = new SmartUpload();
            //初始化
            mySmartUpload.initialize(pageContext);
            //设置上载的最大值
            mySmartUpload.setMaxFileSize(50 * 1024 * 1024);
            try {
                //上载文件
                mySmartUpload.upload();
            } catch (Exception e) {
                response.sendRedirect("addnotice.jsp");
            }
            //startflag = Integer.parseInt(mySmartUpload.getRequest().getParameter("startflag"));
/*            pic1 = mySmartUpload.getRequest().getParameter("pic1");
            pic2 = mySmartUpload.getRequest().getParameter("pic2");
            pic3 = mySmartUpload.getRequest().getParameter("pic3");
            pic4 = mySmartUpload.getRequest().getParameter("pic4");
            pic5 = mySmartUpload.getRequest().getParameter("pic5");
            pic6 = mySmartUpload.getRequest().getParameter("pic6");*/
            address1 = mySmartUpload.getRequest().getParameter("address1");
            address2 = mySmartUpload.getRequest().getParameter("address2");
            address3 = mySmartUpload.getRequest().getParameter("address3");
            address4 = mySmartUpload.getRequest().getParameter("address4");
            address5 = mySmartUpload.getRequest().getParameter("address5");
            address6 = mySmartUpload.getRequest().getParameter("address6");
            date = mySmartUpload.getRequest().getParameter("date");
            Files uploadFiles = mySmartUpload.getFiles();
            for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
                File tempFile = mySmartUpload.getFiles().getFile(i);
                if (!tempFile.isMissing()) {
                    filename = tempFile.getFileName();
                    String ext = tempFile.getFileExt();
                    if(i == 0){
                        pic1 = filename;
                        pics1 = filename.substring(0,filename.lastIndexOf("."));
                    }
                    if(i == 1){
                        pic2 = filename;
                        pics2 = filename.substring(0,filename.lastIndexOf("."));
                    }
                    if(i == 2){
                        pic3 = filename;
                        pics3 = filename.substring(0,filename.lastIndexOf("."));
                    }
                    if(i == 3){
                        pic4 = filename;
                        pics4 = filename.substring(0,filename.lastIndexOf("."));
                    }
                    if(i == 4){
                        pic5 = filename;
                        pics5 = filename.substring(0,filename.lastIndexOf("."));
                    }
                    if(i == 5){
                        pic6 = filename;
                        pics6 = filename.substring(0,filename.lastIndexOf("."));
                    }
                    String newfilename = String.valueOf(System.currentTimeMillis()) + "." + ext;
                    uploadPath = application.getRealPath("/") + java.io.File.separator + "ksfflash" + java.io.File.separator;
                    java.io.File file = new java.io.File(uploadPath);
                    //System.out.println("upload = " + uploadPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }

                    tempFile.saveAs(uploadPath + filename);
                }
            }
            mySmartUpload.stop();
        } catch (Exception e) {
            System.out.println(e.toString());
            error = true;
        }
        java.io.File file = new java.io.File(uploadPath+"data.xml");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream testfile = new FileOutputStream(uploadPath+"data.xml");   //清空文件内容
        testfile.write(new String("").getBytes());
        /*String read;
        FileReader filereader;
        BufferedReader bufread;
        String readStr = "";
        try{
            filereader = new FileReader(uploadPath+"data.xml");
            bufread = new BufferedReader(filereader);
            try{
                while((read = bufread.readLine()) !=null){
                    readStr = readStr + read + "\r\n";
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("readStr = " + readStr);*/
        String filein = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\r\n" +
                "<data>\r\n" + "\t<section>\r\n" + "\t\t<image>"+ pic1 +"</image>\r\n" +
                "\t\t<name>" + pics1 + "</name>\r\n" + "\t\t<adurl>" + address1 + "</adurl>\r\n" +
                "\r\n" + "\t</section>\r\n" + "\t<section>\r\n" + "\t\t<image>" + pic2 + "</image>\r\n" +
                "\t\t<name>" + pics2 + "</name>\r\n" + "\t\t<adurl>" + address2 + "</adurl>\r\n" + "\r\n" +
                "\t</section>\r\n" + "\t<section>\r\n" + "\t\t<image>" + pic3 + "</image>\r\n" +
                "\t\t<name>" + pics3 + "</name>\r\n" + "\t\t<adurl>" + address3 + "</adurl>\r\n" + "\r\n" +
                "\t</section>\r\n" + "\t<section>\r\n" + "\t\t<image>" + pic4 + "</image>\r\n" +
                "\t\t<name>" + pics4 + "</name>\r\n" + "\t\t<adurl>" + address4 + "</adurl>\r\n" + "\r\n" +
                "\t</section>\r\n" + "\t<section>\r\n" + "\t\t<image>" + pic5 + "</image>\r\n" +
                "\t\t<name>" + pics5 + "</name>\r\n" + "\t\t<adurl>" + address5 + "</adurl>\r\n" + "\r\n" +
                "\t</section>\r\n" + "\t<section>\r\n" + "\t\t<image>" + pic6 + "</image>\r\n" +
                "\t\t<name>" + pics6 + "</name>\r\n" + "\t\t<adurl>" + address6 + "</adurl>\r\n" + "\r\n" +
                "\t</section>\r\n" + "\t<Interval>" + date + "</Interval>\r\n" + "</data>\r\n";
        RandomAccessFile mm = null;
        try{
            mm = new RandomAccessFile(file,"rw");
            mm.writeBytes(filein);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            if(mm != null){
                mm.close();
            }
        }
        out.println("<script   lanugage=\"javascript\">alert(\"添加成功！\");window.location=\"index.jsp\";</script>");
    }
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<script language="JavaScript" src="include/setday.js" ></script>
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
.TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
.FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
.FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
.FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
</style>
    <script language="javascript">
        function f(theimg) {
            var val = theimg.value;
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;

            if (ext == ".jpg") {
                /*d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = theimg.value;
                d.style.width = d.offsetWidth;
                d.style.height = d.offsetHeight;
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';*/
                validate = true;
            }
            else
            {
                if (!validate)
                {
                    alert("只能上传jpg格式图片！");
                }
            }
        }
        function searchcheck() {
            if(document.form1.pic1.value == ""){
                alert("请选择图片路径！");
                return false;
            }
            if(document.form1.pic2.value == ""){
                alert("请选择图片路径！");
                return false;
            }
            if(document.form1.pic3.value == ""){
                alert("请选择图片路径！");
                return false;
            }
            if(document.form1.pic4.value == ""){
                alert("请选择图片路径！");
                return false;
            }
            if(document.form1.pic5.value == ""){
                alert("请选择图片路径！");
                return false;
            }
            if(document.form1.pic6.value == ""){
                alert("请选择图片路径！");
                return false;
            }
            if(document.form1.address1.value == ""){
                alert("请输入链接地址！");
                return false;
            }
            if(document.form1.address2.value == ""){
                alert("请输入链接地址！");
                return false;
            }
            if(document.form1.address3.value == ""){
                alert("请输入链接地址！");
                return false;
            }
            if(document.form1.address4.value == ""){
                alert("请输入链接地址！");
                return false;
            }
            if(document.form1.address5.value == ""){
                alert("请输入链接地址！");
                return false;
            }
            if(document.form1.address6.value == ""){
                alert("请输入链接地址！");
                return false;
            }
            if(document.form1.date.value == ""){
                alert("请输入时间！");
                return false;
            }
            document.form1.submit();
            return true;
        }
  </script>
</head>
<body>
<center>
<form name="form1" method="post" action="index.jsp?doCreate=true" enctype="multipart/form-data">
<table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">康师傅首页flash管理</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="15%">第一张图</td>
                  <td width="35%" align="center" bgcolor="#FFFFFF"><INPUT type="file" size=20 name=pic1 onpropertychange="f(this)"></td>
                  <td align="center" width="15%">链接地址</td>
                    <td align="center" width="35%"><input type="text" name="address1" size="40"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="15%">第二张图</td>
                  <td width="35%" align="center" bgcolor="#FFFFFF"><INPUT type="file" size=20 name=pic2 onpropertychange="f(this)"></td>
                  <td align="center" width="15%">链接地址</td>
                    <td align="center" width="35%"><input type="text" name="address2" size="40"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="15%">第三张图</td>
                  <td width="35%" align="center" bgcolor="#FFFFFF"><INPUT type="file" size=20 name=pic3 onpropertychange="f(this)"></td>
                  <td align="center" width="15%">链接地址</td>
                    <td align="center" width="35%"><input type="text" name="address3" size="40"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="15%">第四张图</td>
                  <td width="35%" align="center" bgcolor="#FFFFFF"><INPUT type="file" size=20 name=pic4 onpropertychange="f(this)"></td>
                  <td align="center" width="15%">链接地址</td>
                    <td align="center" width="35%"><input type="text" name="address4" size="40"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="15%">第五张图</td>
                  <td width="35%" align="center" bgcolor="#FFFFFF"><INPUT type="file" size=20 name=pic5 onpropertychange="f(this)"></td>
                  <td align="center" width="15%">链接地址</td>
                    <td align="center" width="35%"><input type="text" name="address5" size="40"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="15%">第六张图</td>
                  <td width="35%" align="center" bgcolor="#FFFFFF"><INPUT type="file" size=20 name=pic6 onpropertychange="f(this)"></td>
                  <td align="center" width="15%">链接地址</td>
                    <td align="center" width="35%"><input type="text" name="address6" size="40"></td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001"><td align="center" width="100%" colspan="4">时间&nbsp;&nbsp;&nbsp;<input type="text" name="date" size="3">&nbsp;秒</td>
                </tr>
                <tr  bgcolor="#FFFFFF" class="css_001">
                </tr>
               </table>
            </td>
          </tr>
        </table> <br>
          <table width="100%"><tr><td align="center"><a href="#" onclick="searchcheck()">上 传</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="javascript:history.go(-1);">取 消</a></td></tr></table>
      </td>
</tr>
</table>
</form>
</center>
</body>
</html>