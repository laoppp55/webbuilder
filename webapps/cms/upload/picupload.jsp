<%@ page import="java.io.*,
                 java.util.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.server.CmsServer"
         contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    int imgflag = authToken.getImgSaveFlag();

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();
    ISiteInfoManager siteInfoManager = SiteInfoPeer.getInstance();
    SiteInfo siteInfo = siteInfoManager.getSiteInfo(siteid);
    String content_pic_size = siteInfo.getContentpic();

    String fileDir = "";
    int isproduct =0;
    if (columnID>0) {
        Column column = columnMgr.getColumn(columnID);
        String the_column_content_pic_size = column.getContentpic();
        if (the_column_content_pic_size!=null) content_pic_size = the_column_content_pic_size;
        fileDir = column.getDirName();
        isproduct = column.getIsProduct();
    }else{
        fileDir = "/_prog/";
    }

    //获取为站点定义的图片的长度和宽度，或者为本栏目定义的图片的宽度和高度，系统根据该定义自动生成相应高度和宽度的图片
    //如果高度大于宽度，按高度缩放
    //如果宽度大于高度，按宽度进行缩放
    String content_pic_width = "";
    String content_pic_height = "";
    if (content_pic_size!= null) {
        int xposi = content_pic_size.toLowerCase().indexOf("x");
        if (xposi>-1) {
            content_pic_height = content_pic_size.substring(0,xposi);
            content_pic_width = content_pic_size.substring(xposi+1);
        }
    }

    String baseDir = application.getRealPath("/");
    String tempDir = StringUtil.replace(fileDir, "/", File.separator);
    String uploadaurl = "/webbuilder/sites/" + sitename + fileDir + "images/";

    String icon = "";
    String picname = "";
    int hostID = 0;
    String status = request.getParameter("status");
    if (status == null) {
        RandomStrg rstr = new RandomStrg();
        rstr.setCharset("a-z0-9");
        rstr.setLength(8);
        rstr.generateRandomObject();
        picname = "pic" + rstr.getRandom();
    } else {
        hostID = Integer.parseInt(request.getParameter("hostID"));
        icon = request.getParameter("picname1");
    }
    String addWaterMark = request.getParameter("addWaterMark");

    String aju = request.getParameter("aju");
    String alttext = request.getParameter("alttext");
    String memo = request.getParameter("memo");
    String bw = request.getParameter("bw");
    String hp = request.getParameter("hp");
    String vp = request.getParameter("vp");
    int width = ParamUtil.getIntParameter(request, "width", 0);
    int height = ParamUtil.getIntParameter(request, "height", 0);
    int maxwidth = ParamUtil.getIntParameter(request, "maxwidth", 0);
    int maxheight = ParamUtil.getIntParameter(request, "maxheight", 0);
    int isAddUrl = ParamUtil.getIntParameter(request, "url", 0);
    int send = ParamUtil.getIntParameter(request, "send", 0);
    int nextpage = ParamUtil.getIntParameter(request ,"nextpage", 0);
    int template_or_article_flag = ParamUtil.getIntParameter(request, "template_or_article", 0);

    //获取生成的小图片名称
    String title_pic = ParamUtil.getParameter(request, "title_pic");
    String vtitle_pic = ParamUtil.getParameter(request, "vtitle_pic");
    String special_pic = ParamUtil.getParameter(request, "special_pic");
    String prod_large_pic = ParamUtil.getParameter(request, "prod_large_pic");
    String prod_small_pic = ParamUtil.getParameter(request, "prod_small_pic");
    String content_pic = ParamUtil.getParameter(request, "content_pic");
    String ret_auto_generated_pic = "";
    if (title_pic != null) ret_auto_generated_pic = ret_auto_generated_pic + "&title_pic=" + title_pic;
    if (vtitle_pic != null) ret_auto_generated_pic = ret_auto_generated_pic + "&vtitle_pic=" + vtitle_pic;
    if (special_pic != null) ret_auto_generated_pic = ret_auto_generated_pic + "&special_pic=" + special_pic;
    if (prod_large_pic != null) ret_auto_generated_pic = ret_auto_generated_pic + "&prod_large_pic=" + prod_large_pic;
    if (prod_small_pic != null) ret_auto_generated_pic = ret_auto_generated_pic + "&prod_small_pic=" + prod_small_pic;
    //如果返回的内容图片名称content_pic不等于空，将content_pic的内容赋值给icon变量
    if (content_pic != null) {
        //int dot_posi = icon.indexOf(".");
        //icon = icon.substring(0,dot_posi) + content_pic + icon.substring(dot_posi);
        icon=content_pic;
        ret_auto_generated_pic = ret_auto_generated_pic + "&content_pic=" + content_pic;
    }

    if (memo != null) {
        memo = new String(memo.getBytes(), "utf-8");
        memo = StringUtil.iso2gbindb(memo);
    }
    if (alttext != null) {
        alttext = new String(alttext.getBytes(), "utf-8");
        alttext = StringUtil.iso2gbindb(alttext);
    }

    //获得所有发布主机列表
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
    String domainname = StringUtil.replace(sitename, "_", ".");
%>

<html>
<head>
    <title>上传图片及FLASH</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="/webbuilder/style/global.css">
    <script language="javascript">
        function selectsize(field){
            var retval = window.showModalDialog("selectimagesizeframe.jsp?column=<%=columnID%>&fname=" + field,window, "dialogWidth:20em; dialogHeight:20em;status:no");;
            if (retval != undefined) {
                if (field == "title_pic")
                    form1.title_pic.value = retval;
                else if (field == "vtitle_pic")
                    form1.vtitle_pic.value = retval;
                else if (field == "special_pic")
                    form1.special_pic.value = retval;
                else if (field == "prod_large_pic")
                    form1.prod_large_pic.value = retval;
                else if (field == "prod_small_pic")
                    form1.prod_small_pic.value = retval;
                else if (field == "content_pic")
                    form1.content_pic.value = retval;
            }
        }
        <%if (status != null){%>
        function selectpic(trueWidth, trueHeight)
        {
            var returnvalue;
            var image = "<%=uploadaurl + icon%>";
            var ext = image.substring(image.lastIndexOf(".")).toLowerCase();

            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp")
            {
                <%if (hostID > 0) {
                  FtpInfo ftpInfo = ftpsetMgr.getFtpInfo(hostID);
                  String docPath = ftpInfo.getDocpath();
                  if (docPath.length() > 0 && !docPath.startsWith("/")) docPath = "/" + docPath;
                  if (docPath.length() > 0 && docPath.endsWith("/")) docPath = docPath.substring(0,docPath.length()-1);
                  String href = "http://" + ftpInfo.getSiteName() + fileDir + "images/" + icon;
                %>
                returnvalue = "<IMG src=<%=href%> hspace=<%=hp%> vspace=<%=vp%> border=<%=bw%>";
                <%}else{%>
                returnvalue = "<IMG src=" + image + " hspace=<%=hp%> vspace=<%=vp%> border=<%=bw%>";
                <%}%>
                if ("<%=aju%>" != "")
                    returnvalue = returnvalue + " align='<%=aju%>' alt='<%=alttext%>'";
                else
                    returnvalue = returnvalue + " alt='<%=alttext%>'";

                if (trueWidth > <%=maxwidth%> && <%=maxwidth%> > 0)
                returnvalue = returnvalue + " width='<%=maxwidth%>'";
                if (trueHeight > <%=maxheight%> && <%=maxheight%> > 0)
                returnvalue = returnvalue + " height='<%=maxheight%>'";

            //if (<%=width%> > 0 && <%=height%> > 0)
            //    returnvalue = returnvalue + " width=<%=width%> height=<%=height%>>";
            //else if (<%=width%> > 0 && <%=height%> == 0)
            //    returnvalue = returnvalue + " width=<%=width%>>";
            //else if (<%=width%> == 0 && <%=height%> > 0)
            //    returnvalue = returnvalue + " height=<%=height%>>";
            //else
                returnvalue = returnvalue + ">";

                if (<%=isAddUrl%> == 1)
                <%
                    if (hostID > 0) {
                        FtpInfo ftpInfo = ftpsetMgr.getFtpInfo(hostID);
                        String docPath = ftpInfo.getDocpath();
                        if (docPath.length() > 0 && !docPath.startsWith("/")) docPath = "/" + docPath;
                        if (docPath.length() > 0 && docPath.endsWith("/")) docPath = docPath.substring(0,docPath.length()-1);
                        String href = "http://" + ftpInfo.getSiteName() + fileDir + "images/" + icon;
                %>
                returnvalue = "<a href='<%=href%>' target=_blank>" + returnvalue + "</a>";
                <%}else{%>
                returnvalue = "<a href='<%=fileDir%>images/<%=icon%>' target=_blank>" + returnvalue + "</a>";
                <%}%>
            else if (<%=nextpage%> == 1)
                returnvalue = "<a href='[TAG][NEXTPAGE][/NEXTPAGE][/TAG]'>" + returnvalue + "</a>";
                if ("<%=memo%>" != "")
                    returnvalue = returnvalue + "<br><br><%=memo%>";
            }
            else if (ext == ".swf")
            {
                returnvalue = "<OBJECT classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' height=<%=height%> width=<%=width%>>";
                returnvalue = returnvalue + "<PARAM NAME=movie VALUE='" + image + "'>";
                returnvalue = returnvalue + "<PARAM NAME=quality VALUE=autohigh>";
                returnvalue = returnvalue + "<PARAM NAME=play VALUE=-1>";
                returnvalue = returnvalue + "<EMBED src='" + image + "' quality=autohigh TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' height=<%=height%> width=<%=width%>></EMBED>";
                returnvalue = returnvalue + "</OBJECT>";
            }

            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue=returnvalue + "<%=ret_auto_generated_pic%>";
                window.close();
                return;
            }else{
                //window.parent.opener.top.InsertHTML('content', returnvalue + "<%=ret_auto_generated_pic%>");
                window.parent.opener.top.InsertHTML('content', returnvalue);
                top.close();
            }
        }
        <%}else{%>
        function validate()
        {
            var markObj = document.form1.addWM;
            if(eval(markObj).checked)
                document.form1.addWaterMark.value = 1;
            else
                document.form1.addWaterMark.value = 0;

            var filename = document.form1.sfilename.value;
            if (filename == "")  return false;

            if (filename.toLowerCase().indexOf("http://") > -1)
            {
                alert("您必须提供您硬盘上的图片上传！");
                return false;
            }
            if (filename.indexOf(".") > 0)
            {
                var ext = filename.substring(filename.lastIndexOf(".")).toLowerCase();
                if (ext != ".gif" && ext != ".jpg" && ext != ".jpeg" && ext != ".png" && ext != ".bmp" && ext != ".swf")
                {
                    alert("对不起，您只能上传 .gif .jpg .png .jpeg .bmp .swf 等图像及Flash文件！");
                    return false;
                }
                if (ext == ".swf" && (form1.width.value == "" || form1.height.value == ""))
                {
                    alert("您选择了上传FLASH文件，请给出FLASH的高度和宽度！");
                    return false;
                }
                return true;
            }
        }

        function getValue(obj){
            obj.select();
            form1.hostID.focus();
            return document.selection.createRange().text;
        }

        function f(obj){
            var val = obj.value;
            var filevalue=getValue(obj);
            var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
            var validate = false;
            if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
                d.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = filevalue;
                //alert(d.offsetWidth);
                //alert(d.offsetHeight);
                //d.style.width = d.offsetWidth;
                //d.style.height = d.offsetHeight;
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
        <%}%>
        function cal(){
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                top.close();
            }else{
                top.close();
            }
        }
    </script>
</head>

<body>
<%
    if (status != null) {
        out.println("<center>");
        String picurl = uploadaurl + icon;
        String ext = icon.substring(icon.lastIndexOf(".")).toLowerCase();
        String fileSize = "";
        int trueHeight = 0;
        int trueWidth = 0;
        List picList = new ArrayList();
        File file = new File(baseDir + "sites" + File.separator + sitename + tempDir + "images" + File.separator + icon);
        if (file.exists()) fileSize = String.valueOf(file.length() / 1000) + "." + String.valueOf(file.length() % 1000) + "K 字节";
        String notes = request.getParameter("notes");
        if(notes != null){
            notes = new String(notes.getBytes(), "utf-8");
            notes = StringUtil.iso2gbindb(notes);
        }

        //显示上传的图片
        if (ext.equals(".gif") || ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png") || ext.equals(".bmp"))
        {
            /*Pic picattr = new Pic();
            String imgPath = dir + tempDir + "images" + File.separator + icon;
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                BufferedImage Bi = ImageIO.read(imgFile);
                trueHeight = Bi.getHeight();
                trueWidth = Bi.getWidth();
                resizeImage imgtool = new resizeImage();
                picattr = imgtool.getImageAttributesBy_jmagick(imgPath);

                Pic pic = new Pic();
                pic.setSiteid(siteid);
                pic.setColumnid(columnID);
                pic.setWidth(picattr.getWidth());
                pic.setHeight(picattr.getHeight());
                pic.setCammer(picattr.getCammer());
                pic.setCammertype(picattr.getCammertype());
                pic.setLatf(picattr.getLatf());
                pic.setLngf(picattr.getLngf());
                pic.setPicsize(Integer.parseInt(String.valueOf(file.length())));
                pic.setImgurl(picurl);
                pic.setPicname(file.getName());
                pic.setNotes(notes);
                picList.add(pic);
                IPicManager picMgr = PicPeer.getInstance();
                picMgr.createPic(picList);
            }*/
            out.println("<img src=" + picurl + " border=0 onDblclick='selectpic(" + trueWidth + "," + trueHeight + ");' title='双击可选择'>");
        } else if (ext.equals(".swf")) {
%>
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
        codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0"
        width="<%=width%>" height="<%=height%>">
    <param name="movie" value="<%=picurl%>">
    <param name="quality" value=autohigh>
    <embed src=<%=picurl%> quality=high
           pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"
           type="application/x-shockwave-flash" width="<%=width%>" height="<%=height%>">
    </embed>
</object>
<%}%>
<font color=red class=itm><br><b>如果你能正常显示上面的图片或FLASH，表示上传成功。<br><br>（文件大小：<%=fileSize%>）</font></b><br><br>
<input type="button" value="  确定  " class=tine onClick="selectpic(<%=trueWidth%>,<%=trueHeight%>);">&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="  取消  " class=tine onClick="cal();">
</center>

<%} else {%>
<form name="form1" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&sitename=<%=sitename%>" method="post"
      enctype="multipart/form-data" onSubmit="return validate();">
    <input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/picupload.jsp">
    <table align="center" width="100%">
        <tr>
            <td>
                简体图片：<input type="file" name="sfilename" size="25" id="imgid" class=form onpropertychange="f(this)">&nbsp;&nbsp;
                选择主机：<select name="hostID" size=1 style="width:150;font-size:9pt"><option value="0">默认发布主机</option>
                <%
                    for (int i = 0; i < siteList.size(); i++) {
                        FtpInfo ftpinfo = (FtpInfo) siteList.get(i);
                        if (ftpinfo.getStatus() == 0) {
                            String siteName = ftpinfo.getSiteName();
                            if (siteName == null || siteName.length() == 0) siteName = sitename;
                %>
                <option value="<%=ftpinfo.getID()%>" selected><%=siteName%></option>
                <%
                        }
                    }
                %>
            </select>
            </td>
        </tr>
        <tr>
            <td>
                繁体图片：<input type=file name="tfilename" size=25 class=form>
            </td>
        </tr>
        <%if (template_or_article_flag == 1) {%>
        <tr>
            <td>
                标题图： <input type="text" name="title_pic" size="6" ondblclick="javascript:selectsize('title_pic')">
                副标题图：<input type="text" name="vtitle_pic" size="6" ondblclick="javascript:selectsize('vtitle_pic')">
                特效图： <input type="text" name="special_pic" size="6" ondblclick="javascript:selectsize('special_pic')">
                内容图： <input type="text" name="content_pic" size="6" value="<%=(content_pic_height!="" && content_pic_width!="")?(content_pic_width + "x" + content_pic_height):""%>" ondblclick="javascript:selectsize('content_pic')">
            </td>
        </tr>
        <%if (isproduct == 1) {%>
        <tr>
            <td>
                商品大图：<input type="text" name="prod_large_pic" size="6" ondblclick="javascript:selectsize('prod_large_pic')">
                商品小图：<input type="text" name="prod_small_pic" size="6" ondblclick="javascript:selectsize('prod_small_pic')">
            </td>
        </tr>
        <%}%>
        <%}%>
        <tr>
            <td>
                图片描述：<input type=text name="notes" size=25>&nbsp;&nbsp;
                <input type="checkbox" name="addWM" value="1">添加水印&nbsp;&nbsp;
                <input type=submit value="  上传  " class=tine>&nbsp;&nbsp;
                <input type="button" value="  取消  " onClick="cal();" class=tine>
            </td>
        </tr>
        <tr>
            <TD>
                <table width="100%" border="1" cellspacing=1 cellpadding=1>
                    <tr height=20>
                        <td width="30%">图片布局</td>
                        <td width="70%">图片预览</td>
                    </tr>
                    <tr>
                        <td valign=top>
                            <table border=0 width="100%" cellspacing=1 cellpadding=1>
                                <tr>
                                    <td>是否加链接：<input type=checkbox name=url value=1></td>
                                </tr>
                                <TR>
                                    <TD>替换文字：<input name="alttext" size="12"></TD>
                                </TR>
                                <TR>
                                    <TD>说明文字：<input name="memo" size="12"></TD>
                                </TR>
                                <tr>
                                    <td>对齐方式：<select name="aju" size="1" style="width:95">
                                        <option value="" selected>不设置</option>
                                        <option value="left">左</option>
                                        <option value="right">右</option>
                                        <option value="top">顶部</option>
                                        <option value="center">中</option>
                                        <option value="bottom">底部</option>
                                        <option value="absMiddle">绝对中间</option>
                                        <option value="absBottom">绝对底部</option>
                                        <option value="textTop">文本顶部</option>
                                        <option value="basicline">基线</option>
                                    </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>最大宽度：<input name="<%=MultipartFormHandle.MAXWIDTHPARAMNAME%>" size="12"></td>
                                </tr>
                                <tr>
                                    <td>最大高度：<input name="<%=MultipartFormHandle.MAXHEIGHTPARAMNAME%>" size="12"></td>
                                </tr>
                                <tr>
                                    <td>图像宽度：<input name="width" size="12" value="<%=content_pic_width%>"></td>
                                </tr>
                                <tr>
                                    <td>图像高度：<input name="height" size="12" value="<%=content_pic_height%>"></td>
                                </tr>
                                <tr>
                                    <td>边框宽度：<input name="bw" size="12" value=0></td>
                                </tr>
                                <tr>
                                    <td>水平间隔：<input name="hp" size="12" value=0></td>
                                </tr>
                                <tr>
                                    <td>垂直间隔：<input name="vp" size="12" value=0></td>
                                </tr>
                            </table>
                        </td>
                        <td valign="top">
                            <!--iframe src="showImage.html" width="400" marginwidth="0" height="280" marginheight="0"
                          scrolling="auto" frameborder="1" name=frm></iframe-->
                            <div id=d style="border:0px solid black;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);WIDTH: 400px; HEIGHT: 280px"></div>
                        </td>
                    </tr>
                </table>
            </TD>
        </TR>
    </table>
    <input type="hidden" name="status" value="save">
    <input type="hidden" name="picname" value="<%=picname%>">
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name="fileDir" value="<%=tempDir%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="imgflag" value="<%=imgflag%>">
    <input type="hidden" name="fromflag" value="picture">
    <input type="hidden" name="addWaterMark" value="">
    <input type="hidden" name="tcflag" value="<%=authToken.getPublishFlag()%>">
</form>
<%}%>

</body>
</html>