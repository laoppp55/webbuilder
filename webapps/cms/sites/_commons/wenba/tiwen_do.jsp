<%@ page import="java.util.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.webapps.questions.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.InetAddress"%>
<%@ page import="java.net.*" %>
<%
    wenti wen = new wenti();
    IQuestionManager iwenba = questionManagerImpl.getInstance();
    com.jspsmart.upload.SmartUpload uploader = new com.jspsmart.upload.SmartUpload();
    uploader.initialize(pageContext);
    uploader.upload();
    //图片
    com.jspsmart.upload.File uploadedFile = uploader.getFiles()
            .getFile(0);
    //文件
    //com.jspsmart.upload.File uploadedFile1 = uploader.getFiles()
    //		.getFile(1);
    String file_name = uploader.getFiles().getFile(0).getFileName();
    String pathUrl1;
    if (file_name == null || file_name.equals("")) {
        pathUrl1 = null;
    } else {
        String rName = String.valueOf(Math.abs(Math
                .round(Math.random() * 10)
                * (new Date().getTime()) * 10)
                + new Date().getTime())
                + "." + uploader.getFiles().getFile(0).getFileExt();
        pathUrl1 = "/wenba/uploadImage/wenti/" + rName;
        uploadedFile.saveAs(pathUrl1);
    }

    //文件

    //String file_name_file = uploader.getFiles().getFile(1)
    //		.getFileName();
    //String path_file;
    //if (file_name_file == null || file_name_file.equals("")) {
    //	path_file = null;
    //} else {
    //	String rName_file = String.valueOf(Math.abs(Math.round(Math
    //			.random() * 10)
    //			* (new Date().getTime()) * 10)
    //			+ new Date().getTime())
    //			+ "." + uploader.getFiles().getFile(1).getFileExt();
    //	path_file = "/wenba/uploadFile/" + rName_file;
    //	uploadedFile1.saveAs(path_file);
    //}
    com.jspsmart.upload.Request myRequest = uploader.getRequest();
%>


<%
    String Type = myRequest.getParameter("types");
    String id_hd = (String)myRequest.getParameter("USERID_HD");
    if(id_hd==null){
        id_hd = "0"; //没有向指定专家提问时  id_hd 值为零
    }
    int emailflag = 0;
    //boolean EMAILFLAG = ParamUtil.getCheckboxParameter(request,
    //"Emailnotify");//邮件通知
    emailflag = Integer.parseInt(request.getParameter("emailflag"));
    //if (EMAILFLAG == true) {
    //emailflag = 1;
    //}
    String Title = (String) myRequest.getParameter("titles"); //问题标题
    String QUESTION = (String) myRequest.getParameter("Question"); //问题内容
    //判断问题分类是否更改
    int COLUMNID;
    String cname = null; //问题所属的分类ID
    String Cname = (String) myRequest.getParameter("fenlei"); //问题所属分类中文名称、
    if (Cname == null || Cname.equals("")) {
        COLUMNID = Integer.parseInt((String) myRequest
                .getParameter("CLLUMNIDS"));
        wenbaImpl wenbai = null;
        wenbai = iwenba.getCnameId(COLUMNID);
        cname = wenbai.getCName();
    } else {
        String[] value = Cname.split(",");
        COLUMNID = Integer.parseInt(value[1]);
        cname = value[0];
    }
    String UserName = (String) myRequest.getParameter("userName");
    int UserID = Integer.parseInt((String) myRequest
            .getParameter("userId"));
    String Ipaddress = request.getRemoteAddr(); //IP地址
    String PROVINCE = (String) myRequest.getParameter("Select1"); //省份
    String CITY = (String) myRequest.getParameter("Select2"); //城市
    String AREA = (String) myRequest.getParameter("Select3"); //区
    String Email = (String) myRequest.getParameter("emails"); //邮箱地址
    String xuanshangflag = (String) myRequest.getParameter("xstw");
    int zxs = 0;
    if(xuanshangflag.equals("1")){
        int xuanshang = Integer.parseInt((String) myRequest
                .getParameter("xsjf"));
        zxs = xuanshang;
    }
    //默认悬赏积分



    wen.setTitles(Title);
    wen.setUserid_hd(id_hd);
    wen.setQuestion(StringUtil.gb2iso4View(QUESTION));
    wen.setColumnid(COLUMNID);
    wen.setCname(StringUtil.gb2iso4View(cname));
    wen.setIpaddress(Ipaddress);
    wen.setProvince(StringUtil.gb2iso4View(PROVINCE));
    wen.setCity(StringUtil.gb2iso4View(CITY));
    wen.setArea(StringUtil.gb2iso4View(AREA));
    wen.setEmail(Email);
    wen.setXuanshang(zxs);
    wen.setEmailnotify(emailflag);
    wen.setPicpath(pathUrl1);
    wen.setFilepath(null);
    wen.setUsername(UserName);
    wen.setUserid(UserID);
    //if (path_file != null) {//文件上传加分
    //	iwenba.changuserinfo_grade(UserID);
    //}
    if (wen != null) {
        iwenba.addWenti(wen);//问题入库
        iwenba.weekgrade(UserID,5);//周积分统计
        iwenba.change_huida(UserID);//
        response.sendRedirect("/wenba/wenba.jsp?cid=" + COLUMNID);
        //if(Type=="woyaowen"||Type.equals("woyaowen")){
        ///	response.sendRedirect("/wenba/wenba.jsp?cid=" + COLUMNID);
        //}
        //if(Type=="diqu"||Type.equals("diqu")){
        //	response.sendRedirect("/wenba/wenba_diqu.jsp?cid=" +COLUMNID+ "&pro=" + URLEncoder.encode(PROVINCE));
        //}

    }
%>

<html>
<head></head>
<body>
<table>
    <tr>
        <td><%=Title%></td>
    </tr>
    <tr>
        <td><%=QUESTION%></td>
    </tr>
    <tr>
        <td><%=COLUMNID%></td>
    </tr>
</table>
</body>
</html>