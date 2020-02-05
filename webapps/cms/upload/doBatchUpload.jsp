<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="utf-8"%>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%@ page import="com.bizwink.upload.RandomStrg" %>
<%@ page import="com.bizwink.cms.server.CmsServer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="com.bizwink.cms.extendAttr.*" %>
<%@ page import="com.bizwink.cms.sitesetting.*" %>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    //System.out.println(sitename);

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();


    String fileDir = "";
    if (columnID>0) {
        Column column = columnMgr.getColumn(columnID);
        fileDir = column.getDirName();
    }else
        fileDir = "/_prog/";

    String baseDir = application.getRealPath("/");
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    //List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
    FtpInfo ftpInfo = ftpsetMgr.getFtpInfo(1);
    String docPath = ftpInfo.getDocpath();

    List piclist = (List) session.getAttribute("batch_pic");
    if (piclist == null) piclist = new ArrayList();

    int articleid = ParamUtil.getIntParameter(request,"articleid",0);
    IArticleManager aMgr = ArticlePeer.getInstance();
    IExtendAttrManager eaMgr = ExtendAttrPeer.getInstance();


    //这个处理文件实现了对图片的压缩
    SmartUpload mySmartUpload = new SmartUpload();
    long file_size_max = 4000000;
    String fileName2;//文件名
    String ext;//文件扩展名
    String testVar;
    //String url = "upload/images/";//应保证在根目录中有此目录的存在
    //初始化
    mySmartUpload.initialize(pageContext);
    //只允许上载此类文件
    try {
        //支持上载文件的后缀名
        //mySmartUpload.setAllowedFilesList("jpg,gif");
        //mySmartUpload.setAllowedFilesList("jpg,gif,jpeg,png");
        //不支持制定的后缀
        mySmartUpload.setDeniedFilesList("exe");

        //上载文件
       mySmartUpload.upload();//不指定编码的upload()方法
        //mySmartUpload.upload("utf-8");//指定编码的upload()方法
    } catch (Exception e) {
        out.print("<script type=\"text/javascript\">");
        out.print("window.alert(\"只允许上传.jpg和.gif类型图片文件\");");
        out.print("window.location=window.location");
        out.print("</script>");
    }
    try {
        for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
            com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(i);
            if (myFile.isMissing()) {
                out.print("<script type=\"text/javascript\">");
                out.print("window.alert(\"请先选择要上传的文件\");");
                out.print("window.location=window.location");
                out.print("</script>");
            } else {
                String myFileName = myFile.getFileName();//取得上载的文件的文件名
                ext = myFile.getFileExt();//取得后缀名
                if (!(ext.length() > 0)) {
                    out.println("**************myFileName的名称是：" + myFileName);
                }

                int file_size = myFile.getSize();//取得文件的大小
                String saveUrl = baseDir + "sites/" + sitename + fileDir + "images/";//文件保存路径
                String dirUrl = docPath+fileDir+"images/"; //文件发布路径
                //System.out.println(dirUrl);
                //System.out.println(saveUrl);
                File file = new File(saveUrl);
                //如果文件夹不存在则创建
                if (!file.exists() && !file.isDirectory()) {
                    System.out.println("//不存在");
                    file.mkdirs();
                }
                File file1 = new File(dirUrl);
                //如果文件夹不存在则创建
                if (!file1.exists() && !file1.isDirectory()) {
                    System.out.println("//不存在");
                    file1.mkdirs();
                }
                if (file_size < file_size_max) {
                    //更改文件名
                    RandomStrg rstr = new RandomStrg();
                    rstr.setCharset("a-z0-9");
                    rstr.setLength(8);
                    rstr.generateRandomObject();
                    String filename = "pic" + rstr.getRandom() + "." + ext;
                    saveUrl += filename;
                    myFile.saveAs(saveUrl, mySmartUpload.SAVE_PHYSICAL);
                    dirUrl+=filename;
                    myFile.saveAs(dirUrl, mySmartUpload.SAVE_PHYSICAL);
                    Turnpic tpic = new Turnpic();
                    tpic.setPicname(filename);
                    tpic.setNotes(mySmartUpload.getRequest().getParameter("notes"));
                    if(articleid > 0){
                        int code= eaMgr.insertTurnPic(tpic,articleid);
                        if(code == 0) {
                            out.print("<script type=\"text/javascript\">");
                            out.print("window.location=window.location");
                            out.print("</script>");
                        }
                    }else{
                        piclist.add(tpic);
                        session.setAttribute("batch_pic", piclist);
                    }

               /*     //上传完成，开始生成缩略图
                    java.io.File file = new java.io.File(saveUrl);//读入刚才上传的文件
                    out.println("ext=" + ext);
                    String newUrl = saveUrl + fileName + "_min." + ext;//新的缩略图保存地址

                    java.awt.Image src = javax.imageio.ImageIO.read(file);//构造Image对象
                    float tagSize = 200;
                    int old_w = src.getWidth(null);//得到原图宽
                    int old_h = src.getHeight(null);//得到原图高
                    int new_w = 0;
                    int new_h = 0;
                    int tempSize;//设置临时大小
                    float tempDouble;

                    if (old_w > old_h) {
                        tempDouble = old_w / tagSize;
                    } else {
                        tempDouble = old_h / tagSize;
                    }
                    new_w = Math.round(old_w / tempDouble);
                    new_h = Math.round(old_h / tempDouble);

                    BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
                    tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null);//绘制缩小后的图
                    FileOutputStream newImage = new FileOutputStream(newUrl);//输出到文件流
                   // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newImage);
                    //encoder.encode(tag);//近JPEG编码
                    newImage.close();
                    */

                } else {
                    out.print("<script type=\"text/javascript\">");
                    out.print("window.alert(\"上传文件大小不能超过\"+(file_size_max/1000)+\"K\");");
                    out.print("window.location=window.location");
                    out.print("</script>");
                }
            }
         }

    } catch (Exception e) {
        e.printStackTrace();

    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>处理上传图片的JSP</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script language="javascript">
        function sub() {
            var ipts = document.getElementById('form').getElementsByTagName('input'),
                    arr = [];
            var picname = "";
            for (var i = 0; i < ipts.length; i++) {
                iptName = ipts[i].getAttribute('name');
                if (iptName.indexOf("picname") >= 0) {
                    picname = picname + ipts[i].value;
                }
                if (iptName.indexOf("pics") >= 0) {
                    if( ipts[i].value==null || ipts[i].value==""){
                        ipts[i].value= "null";
                    }
                    picname = picname + "#" + ipts[i].value+"@";
                }
               /* if (iptName.indexOf("sortpic") >= 0) {
                    picname = picname + "#" + ipts[i].value + "@";
                }*/
            }

            var retval = picname;
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = retval;
                window.close();
                return;
            } else {
                window.parent.opener.top.InsertHTML('content', retval);
                top.close();

            }
        }
        function update() {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.close();
                return;
            } else {
                top.close();

            }
        }
        function cal(){
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                top.close();
            }else{
                top.close();
            }
        }

        function disp_pic(pname) {
            var val = pname;
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;
            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                alert("/webbuilder/sites/"+form1.sitename.value + pname);
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = "/webbuilder/sites/"+form1.sitename.value + pname;
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
    <table align="center" width="100%" border=0>
    <div>
        <form action="upload/doBatchUpload.jsp?column=<%=columnID%>&articleid=<%=articleid%>" method="post" enctype="multipart/form-data"
              name="form1">
            <table class="tableStyle" align="center" border="1">
                <tr>
                    <td class="tdStyle">
                        请选择上传文件
                        <input type="file" name="file1" />
                    </td>
                    <td class="tdStyle">
                        图片描述
                        <input type="text" name="notes" />
                    </td>
                </tr>
                <tr>
                    <td class="tdStyle" colspan="2">
                        <input type="submit" name="submit" value="上传" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
    </table>


    <%
        //List olist = new ArrayList();
        List olist = (List)session.getAttribute("batch_pic");
        if(olist==null)  olist = new ArrayList();
        IArticleManager aMgr1 = ArticlePeer.getInstance();
        List list = aMgr.getArticleTurnPic(articleid);
        int columnID1 = ParamUtil.getIntParameter(request,"column",0);
    %>
    <table width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <form name="picattrForm" id="form" method="post" action = "upload/doBatchUpload.jsp?status=update&column=<%=columnID%>&articleid=<%=articleid%>" enctype="multipart/form-data">
                        <!--input type="hidden" name="status" value="update"-->
                        <!--input type="text" name="upStr" id="upStr" value="1123"-->
                        <tr bgcolor="#F4F4F4" align="center">
                            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">批量上传图片</td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="right">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="center" bgcolor="#FFFFFF">图片路径</td>
                                        <td align="center" width="40%">图片描述</td>
                                        <!--td align="center" width="20%">上传文件</td-->
                                        <!--td align="center" width="15%">序号</td-->
                                        <td align="center" width="15%">删除</td>
                                        <!--td align="center" width="10%">修改</td-->
                                    </tr>
                                    <%
                                        int items = list.size();
                                        if(list != null){
                                            for(int i = 0; i < list.size();i++){
                                                Turnpic tpic = (Turnpic)list.get(i);
                                                String mediaurl = tpic.getMediaurl();
                                                if (mediaurl != null) {
                                                    int posi = mediaurl.lastIndexOf("/");
                                                    mediaurl = mediaurl.substring(posi+1);
                                                }
                                    %>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" bgcolor="#FFFFFF"><input type="hidden" name="picnames<%=i%>" value="<%=tpic.getPicname()%>"><a href="#" onclick="javascript:disp_pic('<%=tpic.getPicname()%>')"><%=tpic.getPicname()==null?"":tpic.getPicname()%></a></td>
                                        <td width="30%"><input type="hidden" name="articlePicID<%=i%>" value="<%=tpic.getId()%>"><input name="pics<%=i%>" type="text" value="<%=tpic.getNotes()==null?"": StringUtil.gb2iso4View(tpic.getNotes())%>" size="30"></td>
                                        <!--td width="30%"><%=(mediaurl==null)?"":mediaurl%><input name="file<%=i%>" type="file" value="" size="30"></td-->
                                        <!--td width="15%"><input name="sortpic<%=i%>" type="text" value="<%=tpic.getSortid()==0?"": tpic.getSortid()%>" size="2"></td-->
                                        <td width="15%"><a href="upload/deleteBatchPic.jsp?sflag=1&picid=<%=tpic.getId()%>&url=<%=tpic.getPicname()%>&articleid=<%=articleid%>&column=<%=columnID%>">删除</a></td>
                                    </tr>
                                    <%}}%>
                                    <%
                                        if(olist != null){
                                            for(int i = 0; i < olist.size();i++){
                                                Turnpic tpic = (Turnpic)olist.get(i);
                                                String mediaurl = tpic.getMediaurl();
                                                if (mediaurl != null) {
                                                    int posi = mediaurl.lastIndexOf("/");
                                                    mediaurl = mediaurl.substring(posi+1);
                                                }
                                    %>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" bgcolor="#FFFFFF"><input type="hidden"  name="picname<%=(i+items)%>" value="<%=tpic.getPicname()%>"><a href="#" onclick="javascript:disp_pic('<%=tpic.getPicname()%>')"><%=tpic.getPicname()==null?"":tpic.getPicname()%></a></td>
                                        <td><input type="hidden" name="articlePicID<%=(i+items)%>" value="<%=tpic.getId()%>"><input name="pics<%=(i+items)%>" type="text" value="<%=tpic.getNotes()==null?"": StringUtil.gb2iso4View(tpic.getNotes())%>" size="30"></td>
                                        <!--td width="30%"><%=(mediaurl==null)?"":mediaurl%><input name="file<%=(i+items)%>" type="file" value="" size="30"></td-->
                                        <!--td width="15%"><input name="sortpic<%=(i+items)%>" type="text" value="<%=tpic.getSortid()==0?"": tpic.getSortid()%>"  size="2"></td-->
                                        <td width="15%"><a href="upload/deleteBatchPic.jsp?sflag=0&picid=<%=(i+items)%>&url=<%=tpic.getPicname()%>&articleid=<%=articleid%>&column=<%=columnID%>">删除</a></td>
                                    </tr>
                                    <%}}%>
                                    <tr align="right">
                                        <td colspan="4">
                                            <center>
                                                <%
                                                    if(articleid>0){
                                                %>
                                                 <input type="button" value="关闭" class=tine onclick="update()">&nbsp;&nbsp;&nbsp;&nbsp;
                                                <%
                                                    }else{
                                                %>
                                                <input type="button" value="  确定  " class=tine onClick="sub();">&nbsp;&nbsp;&nbsp;&nbsp;
                                                <input type="button" value="  取消  " class=tine onClick="cal();"></center>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <!--td colspan="5"><input type="submit" name="updatebtn" value="修改" /></td-->
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </form>
                </table>
            </td>
        </tr>
    </table>

<body>
</body>
</html>
