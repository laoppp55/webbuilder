<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.csinfo.CsInfo" %>
<%@ page import="com.bizwink.cms.toolkit.csinfo.ICsInfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.csinfo.CsInfoPeer" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="java.io.File" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.UUID" %>
<%@ page import="com.bizwink.cms.sitesetting.IFtpSetManager" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpInfo" %>
<%@ page import="com.bizwink.cms.sitesetting.FtpSetting" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=GBK" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String sitename = authToken.getSitename();
    String baseDir = application.getRealPath("/");
    int siteID = authToken.getSiteID();
    String userID = authToken.getUserID();
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();

    List piclist = (List) session.getAttribute("upload_pic");
    if (piclist == null) piclist = new ArrayList();

   // System.out.println(userID);
   // System.out.println(siteID);
    SmartUpload upload = new SmartUpload();
    upload.initialize(this.getServletConfig(), request, response);
    upload.upload();

    String startflag =  upload.getRequest().getParameter("startflag");
    String ROOM_NAME = upload.getRequest().getParameter("ROOM_NAME");
    String CATLOG_CODE = upload.getRequest().getParameter("CATLOG_CODE");
    String ROOM_TYPE = upload.getRequest().getParameter("ROOM_TYPE");
    String ROOM_NUM = upload.getRequest().getParameter("ROOM_NUM");
    String ROOM_WAY = upload.getRequest().getParameter("ROOM_WAY");
    String ROOM_SIZE = upload.getRequest().getParameter("ROOM_SIZE");
    String INIT_PRICE = upload.getRequest().getParameter("INIT_PRICE");
    String BED_NUM = upload.getRequest().getParameter("BED_NUM");
    String BED_TYPE = upload.getRequest().getParameter("BED_TYPE");
    String FLOOR = upload.getRequest().getParameter("FLOOR");
    String TOILET = upload.getRequest().getParameter("TOILET");
    String TV = upload.getRequest().getParameter("TV");
    String AIRCONDITIONER = upload.getRequest().getParameter("AIRCONDITIONER");
    String BATHROOM = upload.getRequest().getParameter("BATHROOM");
    String BEDCLOTHES = upload.getRequest().getParameter("BEDCLOTHES");
    String AMENITIES = upload.getRequest().getParameter("AMENITIES");
    String SPECIALSERVICE = upload.getRequest().getParameter("SPECIALSERVICE");

    System.out.println("startflag="+startflag);




    if(startflag!=null&&startflag.equals("1")){
        //获取上传的文件并保存上传文件
        com.jspsmart.upload.Files uploadfiles = upload.getFiles();
        String[] upfiles = new String[uploadfiles.getCount()];
        if(uploadfiles.getCount()>0){
            for(int ii=0; ii<uploadfiles.getCount(); ii++) {
                com.jspsmart.upload.File tempfile = uploadfiles.getFile(ii);
                if(!tempfile.isMissing()){
                    //java.io.File tfile = new java.io.File(baseDir + "images/");
                    //if(!tfile.exists()) tfile.mkdirs();
                    String filename = URLDecoder.decode(tempfile.getFileName(), "utf-8");
                    filename = filename.substring(filename.lastIndexOf(File.separator)+1);
                    int posi = filename.lastIndexOf(".");
                    String ext_name = "";
                    if (posi>-1) ext_name = filename.substring(posi);
                    String uuid = UUID.randomUUID().toString();
                    uuid = uuid.replaceAll("-", "");
                    String myfilename = uuid + ext_name;
                    upfiles[ii] = myfilename;
                    //tempfile.saveAs(saveit);
                    //upload.save(baseDir + column.getDIRNAME().replace("/", File.separator) +"images/");

                    String saveUrl = baseDir + "sites/" + sitename  + "/images/";//文件保存路径
                    java.io.File tfile = new java.io.File(saveUrl);
                    if(!tfile.exists()) tfile.mkdirs();
                    tempfile.saveAs(saveUrl+java.io.File.separator + myfilename,upload.SAVE_PHYSICAL);

                    String saveit = tfile.getPath()+java.io.File.separator + myfilename;
                    System.out.println("original name=" + tempfile.getFileName());
                    System.out.println("full file name=" + saveit);

                   /* List siteList = ftpsetMgr.getFtpInfoList(siteID);
                    for(int j=0; j < siteList.size();j++){
                        FtpInfo ftpInfo = (FtpInfo)siteList.get(j);
                        String docPath = ftpInfo.getDocpath();
                        String dirUrl = docPath+"/images/"; //文件发布路径
                        System.out.println("docPath="+docPath);
                        tempfile.saveAs(dirUrl+java.io.File.separator + myfilename,upload.SAVE_PHYSICAL);
                    }*/
                    CsInfo csInfo = new CsInfo();
                    csInfo.setRoom_pic_url(myfilename);
                    piclist.add(csInfo);
                    session.setAttribute("upload_pic", piclist);

                }
            }
        }

        //入库
        CsInfo csinfo= new CsInfo();
        ICsInfoManager csInfoMgr = CsInfoPeer.getInstance();
        //long nowtime = System.currentTimeMillis();
        csinfo.setSiteid(siteID);
        csinfo.setROOM_NAME(ROOM_NAME);
        csinfo.setCATLOG_CODE(CATLOG_CODE);
        csinfo.setROOM_TYPE(Integer.valueOf(ROOM_TYPE));
        csinfo.setBED_NUM(Integer.valueOf(ROOM_NUM));
        csinfo.setROOM_WAY(ROOM_WAY);
        csinfo.setROOM_SIZE(Integer.valueOf(ROOM_SIZE));
        csinfo.setINIT_PRICE(Integer.valueOf(INIT_PRICE));
        csinfo.setBED_NUM(Integer.valueOf(BED_NUM));
        csinfo.setBED_TYPE(BED_TYPE);
        csinfo.setFLOOR(Integer.valueOf(FLOOR));
        csinfo.setTOILET(Integer.valueOf(TOILET));
        csinfo.setTV(Integer.valueOf(TV));
        csinfo.setAIRCONDITIONER(Integer.valueOf(AIRCONDITIONER));
        csinfo.setBATHROOM(Integer.valueOf(BATHROOM));
        csinfo.setBEDCLOTHES(Integer.valueOf(BEDCLOTHES));
        csinfo.setAMENITIES(Integer.valueOf(AMENITIES));
        csinfo.setSPECIALSERVICE(Integer.valueOf(SPECIALSERVICE));

        csInfoMgr.insertCsinfo(csinfo,piclist);
        session.removeAttribute("upload_pic");
        response.sendRedirect("index.jsp");
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加房型信息</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <SCRIPT language=javascript>
        function check()
        {
            return true;
        }

        //下面用于多图片上传预览功能
        function setImagePreviews() {
            var docObj = document.getElementById("doc");
            var dd = document.getElementById("dd");
            dd.innerHTML = "";
            var fileList = docObj.files;
            for (var i = 0; i < fileList.length; i++) {
                dd.innerHTML += "<div style=\'float:left\'><img id='img" + i + "'  /> </div>";
                var imgObjPreview = document.getElementById("img"+i);
                if (docObj.files &&docObj.files[i]) {

                    //火狐下，直接设img属性
                    imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = '150px';
                    imgObjPreview.style.height = '180px';
                    //imgObjPreview.src = docObj.files[0].getAsDataURL();
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                    imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
                }
                else {
                    //IE下，使用滤镜
                    docObj.select();
                    var imgSrc = document.selection.createRange().text;
                    alert(imgSrc);
                    var localImagId = document.getElementById("img" + i);
                    //必须设置初始大小
                    localImagId.style.width = "150px";
                    localImagId.style.height = "180px";
                    //图片异常的捕捉，防止用户修改后缀来伪造图片
                    try {
                        localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                    }
                    catch (e) {
                        alert("您上传的图片格式不正确，请重新选择!");
                        return false;
                    }
                    imgObjPreview.style.display = 'none';
                    document.selection.empty();
                }
            }
            return true;
        }
        function goto()
        {
            form1.action="index.jsp";
            form1.submit();
        }

</script>
        </head>
<BODY bgColor=#ffffff>
<CENTER>
<FORM name=form1 action=addcsinfo.jsp method=post enctype="multipart/form-data"  onsubmit="check()">
    <INPUT type="hidden" name="startflag" value=1 >

        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=1>
            <TBODY>
            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>添加房型信息</P></TD></TR>
            <TR height=32>
                <TD align=right width=30% height=32>房间名称：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT name=ROOM_NAME> <FONT
                        color=red>*</FONT> </TD></TR>
            <TR height=32>
                <TD align=right>房间编号：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=CATLOG_CODE>
                </TD></TR>
            <TR height=32>
                <TD align=right>房间类型：</TD>
                <TD align=left>&nbsp;<INPUT size=50 name=ROOM_TYPE> </TD></TR>
            <TR height=32>
                <TD align=right>房间户型：</TD>
                <TD align=left>&nbsp;<INPUT size=13 name=ROOM_NUM>
                </TD></TR>
            <TR height=32>
                <TD align=right>房间朝向：</TD>
                <TD align=left>&nbsp;<INPUT name=ROOM_WAY> </TD></TR>
            <TR height=32>
                <TD align=right>房间面积：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=ROOM_SIZE>
                </TD></TR>
            <TR height=32>
                <TD align=right>房间价格：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=INIT_PRICE> </TD></TR>
            <TR height=32>
                <TD align=right>床位数：</TD>
                <TD align=left>&nbsp;<INPUT size=30 name=BED_NUM> </TD></TR>
            <TR height=32>
                <TD align=right>床位类型：</TD>
                <TD align=left>&nbsp;<INPUT size=30 name=BED_TYPE> </TD></TR>
            <TR height=32>
                <TD align=right>所在层数：</TD>
                <TD align=left>&nbsp;<INPUT size=30 name=FLOOR> </TD></TR>
            <TR height=32>
                <TD align=right>是否有卫生间：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=TOILET value="1" checked>有<INPUT type="radio" size=30 name=TOILET value="0">没有 </TD></TR>
            <TR height=32>
                <TD align=right>是否有电视：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=TV value="1" checked>有<INPUT type="radio" size=30 name=TV value="0">没有 </TD></TR>
            <TR height=32>
                <TD align=right>是否有空调：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=AIRCONDITIONER value="1" checked>有<INPUT type="radio" size=30 name=AIRCONDITIONER value="0">没有</TD></TR>
            <TR height=32>
                <TD align=right>是否有洗澡间：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=BATHROOM value="1" checked>有<INPUT type="radio" size=30 name=BATHROOM value="0">没有</TD></TR>
            <TR height=32>
                <TD align=right>是否有被褥：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=BEDCLOTHES value="1" checked>有<INPUT type="radio" size=30 name=BEDCLOTHES value="0">没有</TD></TR>
            <TR height=32>
                <TD align=right>是否有娱乐场所：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=AMENITIES value="1" checked>有<INPUT type="radio" size=30 name=AMENITIES value="0">没有</TD></TR>
            <TR height=32>
                <TD align=right>是否有特别业务：</TD>
                <TD align=left>&nbsp;<INPUT type="radio" size=30 name=SPECIALSERVICE value="1" checked>有<INPUT type="radio" size=30 name=SPECIALSERVICE value="0">没有 </TD></TR>
            <TR height=32>
                <TD align=right>是否：</TD>
                <TD align=left>&nbsp;<INPUT size=30 name=DELFLAG> </TD></TR>
            <TR height=32>
                <TD align=right>添加房间图片:</TD>
                <TD align=left><input type="file"  name="img" id="doc" style="width:150px;" multiple="multiple"  onchange="javascript:setImagePreviews();" accept="image/*" /></TD>
            </TR>
            <TR height=32>
                <TD colspan="2"><div id="dd" style=" width:990px;"></div></TD>
            </TR>
            <TR>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
            </TR></TBODY></TABLE>
        <P align=center><INPUT type=submit value=" 确 认 " name=Ok>&nbsp;&nbsp;
            <INPUT onclick=javascript:goto(); type=button value=返回列表 name=golist>
        </P>
</FORM>
</CENTER>
</BODY>
</html>
