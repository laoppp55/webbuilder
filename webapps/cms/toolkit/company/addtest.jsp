<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.jspsmart.upload.File" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.jspsmart.upload.Files" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%

    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    String filename = "";
    int startflag = 0;
    String companyname = "";
    String companyaddress = "";
    String companyphone = "";
    String companyfax = "";
    String companywebsite = "";
    String companyemail = "";
    String postcode = "";
    String classification = "";
    String companylatitude= "";
    String companylongitude= "";
    String companygooglecode ="";
    String companypic = "";
    String summary = "";
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    if(doCreate){
        File tmpFile = null;
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
                response.sendRedirect("addtest.jsp");
            }
            startflag = Integer.parseInt(mySmartUpload.getRequest().getParameter("startflag"));
            companyname = mySmartUpload.getRequest().getParameter("companyname");
            companyaddress = mySmartUpload.getRequest().getParameter("companyaddress");
            companyphone = mySmartUpload.getRequest().getParameter("companyphone");
            companyfax = mySmartUpload.getRequest().getParameter("companyfax");
            companywebsite = mySmartUpload.getRequest().getParameter("companywebsite");
            companyemail = mySmartUpload.getRequest().getParameter("companyemail");
            postcode = mySmartUpload.getRequest().getParameter("postcode");
            classification = mySmartUpload.getRequest().getParameter("classification");
            companylatitude = mySmartUpload.getRequest().getParameter("companylatitude");
            companylongitude = mySmartUpload.getRequest().getParameter("companylongitude");
            companygooglecode = mySmartUpload.getRequest().getParameter("companygooglecode");
            summary = mySmartUpload.getRequest().getParameter("summary");
            
            Files uploadFiles = mySmartUpload.getFiles();
            for(int i = 0; i<uploadFiles.getCount(); i++){
                File tempFile = mySmartUpload.getFiles().getFile(i);
                if (!tempFile.isMissing()){
                    
                    filename = tempFile.getFileName();
                    String ext = tempFile.getFileExt();
                    String newfilename = String.valueOf(System.currentTimeMillis()) + "_" + i + "." + ext;
                    companypic = newfilename;
                    String uploadPath = application.getRealPath("/") + java.io.File.separator + "sites\\"+ sitename +"\\companypic" + java.io.File.separator;
                    java.io.File file = new java.io.File(uploadPath);
                  
                     if (!file.exists()) {
                        file.mkdirs();
                    }
                    tempFile.saveAs(uploadPath + newfilename);
                }
                mySmartUpload.stop();
            }

    } catch(Exception e){
            System.out.println(e.toString());
        }

    if (startflag == 1) {

        Companyinfo company = new Companyinfo();
        company.setSiteid(siteid);
        company.setCompanyname(companyname);
        company.setCompanyaddress(companyaddress);
        company.setCompanyphone(companyphone);
        company.setCompanyfax(companyfax);
        company.setCompanywebsite(companywebsite);
        company.setCompanyemail(companyemail);
        company.setPostcode(postcode);
        company.setClassification(classification);
        company.setCompanylatitude(companylatitude);
        company.setCompanylongitude(companylongitude);
        company.setCompanygooglecode(companygooglecode);
        company.setCompanypic(companypic);
        company.setSummary(summary);

        ICompanyinfoManager comMgr = CompanyinfoPeer.getInstance();
        comMgr.addCompanyInfo(company);
        response.sendRedirect("index.jsp?siteid=" + siteid);
    }
 }
%>
<html>
<head>
    <title>企业添加页面</title>
    <style type="text/css">
        td {
            font-size: 12px
        }
    </style>
    <script type="text/javascript">
        function check() {
            var companyname = document.getElementById("companyname").value;
            if (companyname == "") {
                alert("企业名称不能为空");
                document.getElementById("companyname").focus();
                return false;
            }

            var companyaddress = document.getElementById("companyaddress").value;
            if (companyaddress == "") {
                alert("地址不能为空");
                document.getElementById("companyaddress").focus();
                return false;
            }

            var companyphone = document.getElementById("companyphone").value;
            if (companyphone == "") {
                alert("电话不能为空");
                document.getElementById("companyphone").focus();
                return false;
            }


            if (companyphone != "") {
                var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
                flag = filter.test(companyphone);
                if (!flag) {
                    alert("电话号码有误，请重新输入！");
                    document.getElementById("companyphone").focus();
                    return false;
                }
            }



            var companyemail = document.getElementById("companyemail").value;
            if (companyemail != "") {
                var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                flag = reg.test(companyemail);
                if (!flag) {
                    alert("邮箱格式不正确！");
                    document.getElementById("companyemail").focus();
                    return false;
                }
            }

            var postcode = document.getElementById("postcode").value;
            if (postcode != "") {   //邮政编码判断
                var pattern = /^[0-9]{6}$/;
                flag = pattern.test(postcode);
                if (!flag) {
                    alert("非法的邮政编码！")
                    document.getElementById("postcode").focus();
                    return false;
                }
            }
             //纬度
            var companylatitude = document.getElementById("companylatitude").value;
            if(companylatitude != "")
            {
                var latitude = /^-?(?:90(?:\.0{1,5})?|(?:[1-8]?\d(?:\.\d{1,5})?))$/;
                flag = latitude.test(companylatitude);
                if(!flag){
                    alert("纬度输入有误，请重新输入！");
                    document.getElementById("companylatitude").focus();
                    return false;
                }
            }
           //经度
            var companylongitude = document.getElementById("companylongitude").value;
            if(companylongitude!=""){
                var  longitude = /^-?(?:(?:180(?:\.0{1,5})?)|(?:(?:(?:1[0-7]\d)|(?:[1-9]?\d))(?:\.\d{1,5})?))$/;
                flag =  longitude.test(companylongitude);
                if(!flag){
                    alert("经度输入有误，请重新输入！");
                    document.getElementById("companylongitude").focus();
                    return false;
                }
            }

             var companypic = document.getElementById("companypic").value;
            if( companypic!= "")
            {
                var ext = companypic.substring(companypic.lastIndexOf(".")).toLowerCase();
                var validate = false;

                if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = fielvalue;
                if (d.offsetWidth<400)
                    d.style.width = d.offsetWidth;
                else
                    d.style.width = 400;
                if (d.offsetHeight<280)
                    d.style.height = d.offsetHeight;
                else
                    d.style.height = 280;
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';
                validate = true;
            }
            else if (ext == ".swf")
            {
                validate = true;
            }
            else
            {
                if (!validate)
                {
                    alert("只能上传图像及FLASH文件！");
                    return false;
                }
            }
            }
        }
          function getValue(id){
            id.select();
            return document.selection.createRange().text;
        }

         function f(theimg) {
            var val = theimg.value;
           var fielvalue = getValue(theimg);
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;

            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = fielvalue;
                 if (d.offsetWidth<400)
                    d.style.width = d.offsetWidth;
                else
                    d.style.width = 400;
                if (d.offsetHeight<280)
                    d.style.height = d.offsetHeight;
                else
                    d.style.height = 280;
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod = 'scale';
                validate = true;
            }
            else if (ext == ".swf")
            {
                validate = true;
            }
            else
            {
                if (!validate)
                {
                    alert("只能上传图像及FLASH文件！");
                }
            }
        }
       
    </script>
</head>
<body>
<center>
<table align="center"  border=0>
<form name="addForm" action="addtest.jsp?doCreate=true" method="post" enctype="multipart/form-data" onsubmit="javascript:return check();">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="siteid" value="<%=siteid%>">
<tr>
    <td>企业名称:</td>
    <td><input type="text" name="companyname" size="30" ></td>
    <td>上传企业图片:</td>
             <td><input type="file" name="companypic" size="30"  onpropertychange="f(this)"></td>
</tr>
<tr>
    <td>地址:</td>
    <td><input type="text" name="companyaddress" size="30"></td>
    <td style="width: 450px" rowspan="10" colspan="2">
                <h1 id=d style="border:1px solid black;filter : progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);WIDTH: 450px; HEIGHT: 280px">
                </h1> </td>
</tr>
<tr>
    <td>电话:</td>
    <td><input type="text" name="companyphone" size="30"></td>
</tr>
<tr>
    <td>传真:</td>
    <td><input type="text" name="companyfax" size="30"></td>
</tr>
<tr>
    <td>网址:</td>
    <td><input type="text" name="companywebsite" size="30"></td>
</tr>
<tr>
    <td>邮箱:</td>
    <td><input type="text" name="companyemail" size="30"></td>
</tr>
<tr>
    <td>邮编:</td>
    <td><input type="text" name="postcode"size="30" ></td>
</tr>
<tr>
    <td>分类:</td>
    <td><input type="text" name="classification" size="30"></td>
</tr>
<tr>
    <td>北纬:</td>
    <td><input type="text" name="companylatitude" size="30" value="0.0">如:36.0000</td>
</tr>
<tr>
    <td>东经:</td>
    <td><input type="text" name="companylongitude" size="30" value="0.0">如:36.0000</td>
</tr>
<tr>
    <td>Google码:</td>
    <td><input type="text" name="companygooglecode" size="30"></td>
</tr>
<tr>
    <td>简介:</td>
    <td><textarea rows="5" cols="50" name="summary"></textarea></td>
</tr>
<tr>
    <td align="left" ><a href="index.jsp?siteid=<%=siteid%>">返回列表</a></td>
    <td align="right" ><input type="submit" value="提交信息"></td>
</tr>
</form>
</table>
</center>
</body>
</html>