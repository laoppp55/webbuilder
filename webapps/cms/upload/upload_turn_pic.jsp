<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.news.*" %>
<%@ page import="com.bizwink.cms.sitesetting.*" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    String contentpic_size = null;
    String tpicsiz = null;
    String vtpicsize = null;
    String sourcepicsize = null;
    String authorpicsize = null;
    String lbpicsize = null;
    String bigpicsize = null;
    String smallpicsize = null;
    IColumnManager columnMgr = ColumnPeer.getInstance();
    ISiteInfoManager siteInfoMgr = SiteInfoPeer.getInstance();

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    Column column = columnMgr.getColumn(columnID);
    SiteInfo siteinfo = siteInfoMgr.getSiteInfo(siteid);
    if (siteinfo.getContentpic()!= null) contentpic_size = siteinfo.getContentpic();
    if (column.getContentpic()!=null) contentpic_size = column.getContentpic();
    if (siteinfo.getTitlepic() != null) tpicsiz = siteinfo.getTitlepic();
    if (column.getTitlepic() != null) tpicsiz = column.getTitlepic();
    if (siteinfo.getVtitlepic()!=null) vtpicsize = siteinfo.getVtitlepic();
    if (column.getVtitlepic()!=null) vtpicsize = column.getVtitlepic();
    if (siteinfo.getSourcepic()!=null) sourcepicsize = siteinfo.getSourcepic();
    if (column.getSourcepic()!=null) sourcepicsize = column.getSourcepic();
    if (siteinfo.getAuthorpic()!=null) authorpicsize = siteinfo.getAuthorpic();
    if (column.getAuthorpic()!=null) authorpicsize = column.getAuthorpic();
    if (siteinfo.getSpecialpic()!=null) lbpicsize = siteinfo.getSpecialpic();
    if (column.getSpecialpic()!=null) lbpicsize = column.getSpecialpic();
    if (siteinfo.getProductpic()!=null) bigpicsize = siteinfo.getProductpic();
    if (column.getProductpic()!=null) bigpicsize = column.getProductpic();
    if (siteinfo.getProductsmallpic()!=null) smallpicsize = siteinfo.getProductsmallpic();
    if (column.getProductsmallpic()!=null) smallpicsize = column.getProductsmallpic();
    int articlePicID = ParamUtil.getIntParameter(request, "article", 0);
    int articleid = ParamUtil.getIntParameter(request,"articleid",0);
    String onotes = null;

    //获得所有发布主机列表
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
%>

<html>
<head>
<title>上传文件</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css">
<script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript" src="../js/json2.js"></script>
<script type="text/javascript" src="../js/json_parse.js"></script>

<script language="Javascript">
    function validate(){
        <%if(articlePicID<=0){%>
        if (document.form1.filename.value == "")
        {
            alert("请选择文件！");
            return false;
        }
        return true;
        <%}else{%>
        if (document.form1.filename.value == ""){
            var notes = document.form1.notes.value;
            document.form1.action = "edit_upload_turn_pic.jsp?editflag=1&article=<%=articlePicID%>&notes="+notes;
            document.form1.submit();
        }else{
            return true;
        }
        <%}%>
    }

    function getValue(id){
        id.select();
        id.blur();
        return document.selection.createRange().text;
    }

    function f(theimg) {
        var val = theimg.value;
        var fielvalue=getValue(theimg);
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
        else if (ext == ".swf" || ext ==".zip")
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

    function cal() {
        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            var retval = uploadpicforarticle.picinfos.value;
            window.opener.document.createForm.picinfos.value = uploadpicforarticle.picinfos.value;
            //alert(window.opener.document.createForm.picinfos.value);
            //window.returnValue = "";
            top.close();
        } else {
            top.close();
        }
    }

    function disp_pic(pname) {
        var val = pname;
        var ext = val.substring(val.lastIndexOf(".")).toLowerCase();
        var validate = false;
        if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp") {
            //alert("/webbuilder/sites/"+form1.sitename.value + pname);
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

<script type="text/javascript">
    $(document).ready(function(){
        //tbl_turnpic   每篇文章相关的上传图片信息保存在该表中
        htmlobj=$.ajax({
            url:"../article/getImagesByArticle.jsp",
            data:{
                article:<%=articleid%>
            },
            dataType:'json',
            async:false,
            success:function(data){
                //alert(data.length);
                var jj = 0;
                for(jj=0; jj < data.length; jj++) {

                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                /*弹出jqXHR对象的信息*/
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                /*弹出其他两个参数的信息*/
                alert(textStatus);
                alert(errorThrown);
            }
        });
    });

    function upload_turn_pic(id){
        window.open("../upload/upload_turn_pic.jsp?id=<%=articleid%>&column=<%=columnID%>&article="+id, "", 'width=400,height=400,left=200,top=200');
    }

    function doupload() {
        var filename = $("#fileToUpload").val();
        if (filename == "") {
            alert("请选择上传的图片文件！");
            return false;
        }

        if (filename.toLowerCase().indexOf(".jpeg") == -1 && filename.toLowerCase().indexOf(".jpg")==-1 && filename.toLowerCase().indexOf(".png")==-1) {
            alert("上传文件必须是jpg格式或者是png格式的图片文件！");
            return false;
        }

        var columnid = form1.column.value;
        var bar = $('.bar');
        var percent = $('.percent');
        var status = $('#status');
        $("#form1").ajaxSubmit({
            url: 'saveUploadImg.jsp?doCreate=true',          //设置post提交到的页面
            type: "post",                                        //设置表单以post方法提交
            dataType: "json",                                   //设置返回值类型为文本
            success: function (data) {
                //var str = '{ "name": "张三", "sex": "男" }';
                //var obj = eval('(' + str + ')');
                //alert(obj.toJSONString());
                var smallimages = JSON.stringify(data.data.smallimages);
                var infos = uploadpicforarticle.picinfos.value;
                if (infos!="" && infos!=null) {
                    var jsonObj = eval('(' + infos + ')');
                    var t_info = '{"imageno":' + data.data.imageno + ',"brief":"' + data.data.brief + '","filepath":"' + data.data.filepath +
                            '","smallimages":' + smallimages + '}';
                    var t_info_jsonobj = eval('(' + t_info + ')');
                    jsonObj.push(t_info_jsonobj);
                   // alert(JSON.stringify(jsonObj));
                    uploadpicforarticle.picinfos.value = JSON.stringify(jsonObj);
                    uploadpicforarticle.imgno.value = parseInt(uploadpicforarticle.imgno.value) + parseInt(1);
                } else {
                    infos = '[{"imageno":0,"brief":"' + data.data.brief + '","filepath":"' + data.data.filepath +
                            '","smallimages":' + smallimages + '}]';
                    uploadpicforarticle.picinfos.value = infos;
                    uploadpicforarticle.imgno.value = parseInt(uploadpicforarticle.imgno.value) + parseInt(1);
                }

                var trHTML = "<tr><td>" + data.data.filepath + "</td>" + "<td>" + data.data.brief + "</td>" +
                        "<td>&nbsp;</td>" + "<td>" + data.data.imageno + "</td>" +
                        "<td>" + "删除" + "</td>" + "<td>" + "修改" + "</tr>";
                $("#picmsg").append(trHTML);
                $("#fileToUpload")[0].value = "";
            },
            xhr: function(){
                var xhr = $.ajaxSettings.xhr();
                if(onprogress && xhr.upload) {
                    xhr.upload.addEventListener("progress" , onprogress, false);
                    return xhr;
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                /*弹出jqXHR对象的信息*/
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                /*弹出其他两个参数的信息*/
                alert(textStatus);
                alert(errorThrown);
            }
        });
    }
</script>
</head>

<body bgcolor="#cccccc">
<table align="center" width="100%" border=0>
    <form name="uploadpicforarticle" enctype="multipart/form-data" id="form1">
        <input type="hidden" name="status" value="save">
        <input type="hidden" name="imgno" value="0">
        <input type="hidden" name="picinfos" value="">
        <input type="hidden" name="column" value="<%=columnID%>">
        <input type="hidden" name="fromflag" value="article">
        <input type="hidden" name="tcflag" value="<%=authToken.getPublishFlag()%>">
        <input type="hidden" name="article" value="<%=articlePicID%>">
        <input type="hidden" name="articleid" value="<%=articleid%>">
        <tr>
            <td width="50%">选择文件：<input type=file name="filename" size=31 id="fileToUpload" onpropertychange="f(this)"></td>
            <td width="50%">选择主机： <select name="hostID" size=1 style="width:150;font-size:9pt">
                <option value="0">默认发布主机</option>
                <%
                    for (int i = 0; i < siteList.size(); i++) {
                        FtpInfo ftpinfo = (FtpInfo) siteList.get(i);
                        if (ftpinfo.getStatus() == 0) {
                            String siteName = ftpinfo.getSiteName();
                            if (siteName == null || siteName.length() == 0) siteName = sitename;
                %>
                <option value="<%=ftpinfo.getID()%>" selected><%=siteName%>
                </option>
                <%
                        }
                    }
                %></select>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <table>
                    <tr>
                        <td>
                            内容图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="contentpicsize" size="9" value="<%=(contentpic_size!=null)?contentpic_size:""%>" readonly>
                            <input type="checkbox" name="c_checkbox" checked>
                        </td>
                        <td>
                            标题图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="tpicsize" size="9" value="<%=(tpicsiz!=null)?tpicsiz:""%>" readonly>
                            <input type="checkbox" name="t_checkbox" <%=(tpicsiz!=null)?"checked":""%>>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            副标题图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="vtpicsize" size="9" value="<%=(vtpicsize!=null)?vtpicsize:""%>" readonly>
                            <input type="checkbox" name="vt_checkbox" <%=(vtpicsize!=null)?"checked":""%>>
                        </td>
                        <td>
                            来源图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="sourcepicsize" size="9" value="<%=(sourcepicsize!=null)?sourcepicsize:""%>" readonly>
                            <input type="checkbox" name="s_checkbox" <%=(sourcepicsize!=null)?"checked":""%>>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            作者图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="authorpicsize" size="9" value="<%=(authorpicsize!=null)?authorpicsize:""%>" readonly>
                            <input type="checkbox" name="a_checkbox" <%=(authorpicsize!=null)?"checked":""%>>
                        </td>
                        <td>
                            轮播图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="lbpicsize" size="9" value="<%=(lbpicsize!=null)?lbpicsize:""%>" readonly>
                            <input type="checkbox" name="lb_checkbox" <%=(lbpicsize!=null)?"checked":""%>>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            大图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="bigpicsize" size="9" value="<%=(bigpicsize!=null)?bigpicsize:""%>" readonly>
                            <input type="checkbox" name="big_checkbox" <%=(bigpicsize!=null)?"checked":""%>>
                        </td>
                        <td>
                            小图片尺寸：
                        </td>
                        <td>
                            <input type="text" name="smallpicsize" size="9" value="<%=(smallpicsize!=null)?smallpicsize:""%>" readonly>
                            <input type="checkbox" name="small_checkbox" <%=(smallpicsize!=null)?"checked":""%>>
                        </td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td>
                            图片描述：
                            <textarea name="notes" rows="6" style="width: 314px"><%=onotes==null?"":StringUtil.gb2iso4View(onotes)%></textarea><!--input type=text name="notes" size=31-->
                        </td>
                    </tr>
                    <tr>
                        <td height="30">
                            <div class="progress" id="progressHide">
                                <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60"
                                     aria-valuemin="0" aria-valuemax="100" style="width: 0%;" id="progressBar">
                                    <span class="sr-only"></span>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <input type="button" value="  上传  " class=tine onclick="javascript:doupload();" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="  返回  " class=tine  onClick="cal();">
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <h1 id=d style="border:1px solid black;filter : progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);WIDTH: 400px; HEIGHT: 250px">
                </h1>
            </td>
        </tr>
    </form>
</table>
<table width="100%" border="0" cellpadding="0">
    <tr bgcolor="#F4F4F4" align="center">
        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">文章附属图片</td>
    </tr>
    <tr bgcolor="#d4d4d4" align="right">
        <td>
            <table id="picmsg" width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                    <td width="20%" align="center" bgcolor="#FFFFFF">图片路径</td>
                    <td align="center" width="30%">图片描述</td>
                    <td align="center" width="20%">上传文件</td>
                    <td align="center" width="10%">序号</td>
                    <td align="center" width="10%">删除</td>
                    <td align="center" width="10%">修改</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>