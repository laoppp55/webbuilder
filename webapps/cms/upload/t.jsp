<%@ page import="java.io.*,
                 com.jspsmart.upload.*,
                 java.net.URLDecoder" contentType="text/html;charset=utf-8"%>
<%
    String baseDir = application.getRealPath("/");
    baseDir = baseDir + "sites"+java.io.File.separator;

    try{
        SmartUpload upload=null;
        upload = new SmartUpload();
        upload.initialize(this.getServletConfig(), request, response);
        upload.upload();

        //其他数据保存入数据库
        //"myname", "aa我是一个测试cc123";
        //"username", "petersong 宋向前"
        //"password", "password 口令"
        //"status", "status 状态"
        //"lon","23.345678"
        //"lat","145.234567"
        //"do","dddddd"}
        //"company","北京市朝阳区北苑路18号院"
        //"siteid",String.valueOf(siteid)
        String saveDirectory = "/data/coosite/";
        // 限制上传之档案大小为 5 MB
        int maxPostSize = 5 * 1024 * 1024 ;

        String rname = upload.getRequest().getParameter("myname");
        String username = upload.getRequest().getParameter("username");
        String password = upload.getRequest().getParameter("password");
        String status = upload.getRequest().getParameter("status");
        String lon = upload.getRequest().getParameter("lon");
        String lat = upload.getRequest().getParameter("lat");
        //String domathed = upload.getRequest().getParameter("description");
        String companyname = upload.getRequest().getParameter("company");
        String columnname = upload.getRequest().getParameter("column");
        String siteid = upload.getRequest().getParameter("siteid");
        String desc = upload.getRequest().getParameter("description");

        //获取上传的文件并保存上传文件
        com.jspsmart.upload.Files uploadfiles = upload.getFiles();
        System.out.println("count=" + uploadfiles.getCount());
        if(uploadfiles.getCount()>0){
            for(int ii=0; ii<uploadfiles.getCount(); ii++) {
                com.jspsmart.upload.File tempfile = uploadfiles.getFile(ii);
                if(!tempfile.isMissing()){
                    java.io.File tfile = new java.io.File(baseDir + "_TEMPUPFILE");
                    System.out.println(baseDir + "_TEMPUPFILE");
                    if(!tfile.exists())tfile.mkdirs();
                    String filename = URLDecoder.decode(tempfile.getFileName(), "utf-8");
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    System.out.println("file name=" + filename);
                    String saveit = tfile.getPath()+java.io.File.separator + filename;
                    tempfile.saveAs(saveit);
                    //upload.save(saveDirectory);
                }
            }
        }

        System.out.println("rname=" + URLDecoder.decode(rname,"utf-8"));
        System.out.println("username=" + URLDecoder.decode(username,"utf-8"));
        System.out.println("password=" + URLDecoder.decode(password,"utf-8"));

        System.out.println("status=" + URLDecoder.decode(status,"utf-8"));
        System.out.println("lat=" + lat);
        System.out.println("lon=" + lon);
        System.out.println("companyname=" + URLDecoder.decode(companyname,"utf-8"));
        System.out.println("desc=" + ((desc!=null)?URLDecoder.decode(desc,"utf-8"):""));
        System.out.println("siteid=" + siteid);
        System.out.println("column=" + ((columnname!=null)?URLDecoder.decode(columnname,"utf-8"):""));
        //System.out.println("classid=" + classid);

/*
        ICompanyinfoManager compMgr = CompanyinfoPeer.getInstance();
        int classid = compMgr.getClassidByName(columnname,Integer.parseInt(siteid));
        Companyinfo company = new Companyinfo();


        company.setCompanylatitude(Double.valueOf(lat));
        company.setCompanylongitude(Double.valueOf(lon));
        company.setCompanyname(companyname);
        company.setSummary(desc);
        company.setSiteid(Integer.valueOf(siteid));
        company.setCompanyclassid(classid);
        company.setClassification(columnname);

        compMgr.addCompanyInfo(company,"");
*/

        out.write("OK");
        out.flush();
    }catch(IOException e){
        e.printStackTrace();
    }

%>

<html>
<head>
    <title></title>
    <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <SCRIPT LANGUAGE=JavaScript>
        function CreateInput(){
            var input = document.createElement("input");
            input.type = "file";
            input.id = "inputText";
            input.name = "myupload";
            //input.value="E:\\gzam\\images\\00118.jpg";
            input.className = "TextStyle";
            input.style.width = "300px";
            input.style.height = "16px";
            input.style.lineHeight = "16px";
            input.style.border = "1px solid #006699";
            input.style.font = "12px 'Microsoft Sans Serif'";
            input.style.padding = "2px";
            input.style.color = "#006699";
            document.getElementById("showText").appendChild(input);
            eval("myupload").focus();
            var wshshell = new ActiveXObject("WScript.Shell");
            wshshell.sendKeys("E:\\gzam\\images\\00118.jpg");
        }

        function check()
        {
            if (uploadfile.maintitle.value == "")
            {
                alert("文件标题不能为空！");
                return false;
            }
            else if (uploadfile.sfilename.value == "")
            {
                alert("请选择文件！");
                return false;
            }
            else
            {
                return true;
            }
        }
    </SCRIPT>
</head>

<body  onload="CreateInput()">
<form method="post" action="t.jsp?doupload=1" name=uploadfile id="ufid" enctype="multipart/form-data" onsubmit="javascript:return check();">
    <input type="hidden" name="status" value="save">
    <input type="hidden" name="baseDir" value="<%=baseDir%>">
    <input type="hidden" name="fromflag" value="file">

    <table width="100%" border="0" align="center">
        <tr>
            <td colspan="2" height="36">图片文件：<div id="showText"></div></td>
        </tr>
        <tr><td>注:请上传图片的 <font color=red><b>ZIP</b></font> 压缩包文件</td></tr>
        <tr>
            <td colspan="2" align="center" height=60>
                <input type="submit" value="  上传  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  取消  " class=tine onclick="window.close();">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
