<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.publish.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.io.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.images.resizeImage" %>
<%@ page import="java.util.Iterator" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    sitename = StringUtil.replace(sitename,".","_");
    String baseDir = request.getRealPath("/");
    String dir = baseDir + "sites" + java.io.File.separator + sitename + java.io.File.separator + "_company" + java.io.File.separator;
    int columnid = ParamUtil.getIntParameter(request,"column",0);
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();
    IPublishManager publishMgr = PublishPeer.getInstance();

    String companyname = null;
    String companyaddress = null;
    String companyphone = null;
    String companyfax = null;
    String companywebsite = null;
    String companyemail = null;
    String postcode = null;
    double companylatitude = 0.00000000d;
    double companylongitude = 0.00000000d;
    String summary = null;

    try {
        int startflag = ParamUtil.getIntParameter(request,"startflag",0);
        if (startflag == 1) {
            DiskFileUpload upload=new DiskFileUpload();
            List uploadlist=upload.parseRequest(request);
            Iterator iter=uploadlist.iterator();

            List picitems = new ArrayList();
            List mediaitems = new ArrayList();
            while (iter.hasNext()){
                FileItem  item=(FileItem)iter.next();
                if(!item.isFormField()){
                    String filename=item.getName();
                    int posi = filename.lastIndexOf(".");
                    String type = null;
                    if (posi > -1) {
                        type = filename.substring(posi).toLowerCase();
                        if (type.equals(".avi") || type.equals(".mpg") || type.equals(".wmv") || type.equals(".3gp") || type.equals(".mov") || type.equals(".mp4") || type.equals(".asf") || type.equals(".mpeg") || type.equals(".mpe")
                                || type.equals(".wmv9") || type.equals(".rm") || type.equals(".rmvb") || type.equals(".asx")) {
                            mediaitems.add(item);
                        } else if (type.equals(".jpg") || type.equals(".jpeg") || type.equals(".png") || type.equals(".bmp") || type.equals(".gif")){
                            picitems.add(item);
                        }
                    }
                } else {
                    String name = item.getFieldName();
                    if (name.equalsIgnoreCase("column")) columnid = Integer.parseInt(item.getString());
                    if (name.equalsIgnoreCase("companyname")) companyname = new String(item.getString().getBytes("iso-8859-1"), "GBK");
                    if (name.equalsIgnoreCase("companyaddress")) companyaddress = new String(item.getString().getBytes("iso-8859-1"), "GBK");
                    if (name.equalsIgnoreCase("companyphone")) companyphone = item.getString();
                    if (name.equalsIgnoreCase("companyfax")) companyfax = item.getString();
                    if (name.equalsIgnoreCase("companywebsite")) companywebsite = item.getString();
                    if (name.equalsIgnoreCase("companyemail")) companyemail = item.getString();
                    if (name.equalsIgnoreCase("postcode")) postcode = item.getString();
                    if (name.equalsIgnoreCase("companylatitude")) companylatitude = Float.parseFloat(item.getString());
                    if (name.equalsIgnoreCase("companylongitude")) companylongitude = Float.parseFloat(item.getString());
                    if (name.equalsIgnoreCase("summary")) summary = new String(item.getString().getBytes("iso-8859-1"), "GBK");
                }
            }

            Companyinfo company = new Companyinfo();
            company.setSiteid(siteid);
            company.setCompanyname(companyname);
            company.setCompanyaddress(companyaddress);
            company.setCompanyphone(companyphone);
            company.setCompanyfax(companyfax);
            company.setCompanywebsite(companywebsite);
            company.setCompanyemail(companyemail);
            company.setPostcode(postcode);
            company.setClassification(StringUtil.gb2iso4View(companyManager.getCompanyClass(columnid).getCname()));
            company.setCompanyclassid(columnid);
            company.setCompanylatitude(companylatitude);
            company.setCompanylongitude(companylongitude);
            company.setCompanypic(picitems);
            company.setCompanyMedia(mediaitems);
            company.setSummary(summary);
            retCompany retc = companyManager.addCompanyInfo(company,dir);          //retCompany类保存生成的ID和生成的每个多媒体文件的名字

            //保存多媒体文件
            List fname = retc.getList();
            for(int i=0; i<mediaitems.size(); i++){
                FileItem  item=(FileItem)mediaitems.get(i);
                String filename=item.getName();
                if(!filename.equals("")) {
                    filename=FilenameUtils.getName(filename);
                    int posi = filename.indexOf(".");
                    String ext = null;
                    if (posi>-1) ext = filename.substring(posi+1);
                    String newfilename = (String)fname.get(i);

                    String savefilename=dir + retc.getCompanyid() + java.io.File.separator + "images" + java.io.File.separator + newfilename;
                    java.io.File file = new java.io.File(dir + retc.getCompanyid() + java.io.File.separator + "images" + java.io.File.separator);

                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    java.io.File saveFilepath=new java.io.File(savefilename);
                    item.write(saveFilepath);
                }
            }

            //保存图片文件
            resizeImage resize_image = new resizeImage();
            String dirName = "/_company/" + retc.getCompanyid() + java.io.File.separator;
            for(int i=0; i<picitems.size(); i++) {
                FileItem item = (FileItem)picitems.get(i);
                String filename = item.getName();
                if (!filename.equals("")) {
                    filename=FilenameUtils.getName(filename);
                    String savefilename=dir + retc.getCompanyid() + java.io.File.separator + "images" + java.io.File.separator + filename;
                    java.io.File file = new java.io.File(dir + retc.getCompanyid() + java.io.File.separator + "images" + java.io.File.separator);

                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    java.io.File saveFilepath=new java.io.File(savefilename);
                    item.write(saveFilepath);

                    String s_filename = resize_image.createThumbnailBy_jmagick(siteid,username,"/_company/"+retc.getCompanyid() + "/",dir + java.io.File.separator + retc.getCompanyid() + java.io.File.separator + "images"  + java.io.File.separator + filename,"180X180","s");
                    publishMgr.publish(username, dir + java.io.File.separator + retc.getCompanyid() + java.io.File.separator + "images"  + java.io.File.separator + filename, siteid, "/_company/"+retc.getCompanyid() + "/images/", 0);
                }
            }
            response.sendRedirect(response.encodeRedirectURL("../companys/closewin.jsp?column=" + columnid + "&siteid=" + siteid + "&fromflag=c"));
            return;
        }
    }catch ( Exception e ) {
        e.printStackTrace();
    }
%>
<html>
<head>
    <title>公司添加页面</title>
    <style type="text/css">
        td {
            font-size: 12px
        }
    </style>
    <script type="text/javascript">
        function check() {
            var companyname = document.getElementById("companyname").value;
            if (companyname == "") {
                alert("公司名称不能为空");
                document.getElementById("companyname").focus();
                return false;
            }

            var companyaddress = document.getElementById("companyaddress").value;
            if (companyaddress == "") {
                alert("公司地址不能为空");
                document.getElementById("companyaddress").focus();
                return false;
            }

            var companyphone = document.getElementById("companyphone").value;
            if (companyphone == "") {
                alert("公司电话不能为空");
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

            return true;
        }

        function openmap() {
            //alert("hello word!!!");
            window.open("selectpoint.jsp?column=<%=columnid%>", "", "width=820,height=680,left=100,top=50,status,scrollbars");
        }
    </script>
</head>
<body>
<center>
    <table>
        <form name="addForm" action="createcompany.jsp?startflag=1" method="post" enctype="multipart/form-data" onsubmit="javascript:return check();">
            <input type="hidden" name="startflag" value="1">
            <input type="hidden" name="column" value="<%=columnid%>">
            <tr>
                <td>企业名称:</td>
                <td><input type="text" name="companyname" size="30" ></td>
            </tr>
            <tr>
                <td>地址:</td>
                <td><input type="text" name="companyaddress" size="30"></td>
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
                <td>北纬:</td>
                <td><input type="text" name="companylatitude" size="30" readonly="true">&nbsp;&nbsp;
                    <input type="button" name="point" value="选择经纬度" onclick="javascript:openmap();"></td>
            </tr>
            <tr>
                <td>东经:</td>
                <td><input type="text" name="companylongitude" size="30" readonly="true"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic1" size="30"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic2" size="30"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic3" size="30"></td>
            </tr>
            <tr>
                <td>图片或多媒体文件:</td>
                <td><input type="file" name="companypic4" size="30"></td>
            </tr>
            <tr>
                <td>简介或多媒体文件:</td>
                <td><textarea rows="5" cols="50" name="summary"></textarea></td>
            </tr>
            <tr>
                <td align="left"><input type="button" value="返回" onclick="javascript:window.close();"></td>
                <td align="center"><input type="submit" value="提交"></td>
            </tr>
        </form>
    </table>
</center>
</body>
</html>