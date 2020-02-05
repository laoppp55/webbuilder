<%@ page import="java.io.*,
                 java.util.*,
                 com.bizwink.upload.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=utf-8"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String username = authToken.getUserID();
  String sitename = authToken.getSitename();
  int siteid = authToken.getSiteID();
  int imgflag = authToken.getImgSaveFlag();

  String status = request.getParameter("status");
  String imgstr = ParamUtil.getParameter(request, "imgstr");
  int columnID  = ParamUtil.getIntParameter(request, "column", 0);
  IColumnManager columnMgr = ColumnPeer.getInstance();
  String fileDir = columnMgr.getColumn(columnID).getDirName();

  String baseDir = application.getRealPath("/");
  String tempDir = StringUtil.replace(fileDir, "/", File.separator);
  String dir = baseDir + "sites" + File.separator + sitename;
  String uploadaurl = "/webbuilder/sites/" + sitename + fileDir + "images/";

  String icon = "";
  String picname = "";
  if (status == null)
  {
    RandomStrg rstr = new RandomStrg();
    rstr.setCharset("a-z0-9");
    rstr.setLength(8);
    rstr.generateRandomObject();
    picname = "pic" + rstr.getRandom();
  }
  else
  {
    icon = request.getParameter("picname");
  }

  String aju = request.getParameter("aju");
  String alttext = request.getParameter("alttext");
  String bw = request.getParameter("bw");
  String hp = request.getParameter("hp");
  String vp = request.getParameter("vp");
  int width = ParamUtil.getIntParameter(request, "maxwidth", 0);
  int height = ParamUtil.getIntParameter(request, "maxheight", 0);

  Hashtable hash = new Hashtable();
  if (imgstr != null)
  {
    hash = StringUtil.getImageAttribute(imgstr);
    aju = hash.get("align").toString().toLowerCase();
    width = Integer.parseInt(hash.get("width").toString());
    height = Integer.parseInt(hash.get("height").toString());
    bw = hash.get("border").toString();
    hp = hash.get("hspace").toString();
    vp = hash.get("vspace").toString();
    alttext = hash.get("alt").toString();
    icon = hash.get("src").toString();
    if (icon.length() > 0) icon = icon.substring(icon.lastIndexOf("/") + 1);
  }
%>

<html>
<head>
<title>图片修改</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type="text/css" href="/webbuilder/style/global.css">
<script language="javascript">
<%if (status == null) {%>
function updatepic()
{
  var str;
  var image = "<%=uploadaurl + icon%>";
  var ext = image.substring(image.lastIndexOf(".")).toLowerCase();

  if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp")
  {
    str = "<IMG src=" + image;
    if (form1.hp.value != "") str = str + " hspace=" + form1.hp.value;
    if (form1.vp.value != "") str = str + " vspace=" + form1.vp.value;
    if (form1.bw.value != "") str = str + " border=" + form1.bw.value;
    if (form1.aju.value != "") str = str + " align=" + form1.aju.value;
    if (form1.alttext.value != "") str = str + " alt=\"" + form1.alttext.value + "\"";

    var width = form1.maxwidth.value;
    var height = form1.maxheight.value;
    if (width != "" && height != "")
      str = str + " width=" + width + " height=" + height + ">";
    else if (width != "" && height == "")
      str = str + " width=" + width + ">";
    else if (width == "" && height != "")
      str = str + " height=" + height + ">";
    else
      str = str + ">";
  }
  else if (ext == ".swf")
  {
    str = "<OBJECT classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' height="+height+" width="+width+">";
    str = str + "<PARAM NAME=movie VALUE='" + image + "'>";
    str = str + "<PARAM NAME=quality VALUE=autohigh>";
    str = str + "<EMBED src='" + image + "' quality=autohigh TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' height="+height+" width="+width+"></EMBED>";
    str = str + "</OBJECT>";
  }

  parent.window.returnValue = str;
  parent.window.close();
}

function cal()
{
  var validate = false;
  var filename = document.form1.sfilename.value;
  if (filename.indexOf(".") > 0)
  {
    var ext = filename.substring(filename.lastIndexOf(".")).toLowerCase();
    if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp")
    {
      frm.imgDiv.style.display = "";
      frm.flashDiv.style.display = "none";
      frm.previewImg.src = filename;
      validate = true;
    }
    else if (ext == ".swf")
    {
      var str = "<object classid=clsid:D27CDB6E-AE6D-11cf-96B8-444553540000 codebase=http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0 width=370 height=270>" +
                "<param name=movie value='" + filename + "'>" +
                "<param name=quality value=high>" +
                "<embed src='" + filename + "' quality=high pluginspage=http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash type=application/x-shockwave-flash width=370 height=270></embed>" +
                "</object>";
      frm.imgDiv.style.display = "none";
      frm.flashDiv.style.display = "";
      frm.flashDiv.innerHTML = str;
      validate = true;
    }
  }

  if (!validate)
  {
    alert("只能上传图像及FLASH文件！");
    frm.imgDiv.style.display = "none";
    frm.flashDiv.style.display = "none";
  }
}

function validate()
{
  var filename = document.form1.sfilename.value;
  if (filename == "")  return false;

  if (confirm("您确定要覆盖当前图象吗？"))
  {
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
      if (ext == ".swf" && (form1.maxwidth.value == "" || form1.maxheight.value == ""))
      {
        alert("您选择了上传FLASH文件，请给出FLASH的高度和宽度！");
        return false;
      }
      return true;
    }
  }
  else
  {
    return false;
  }
}
<%}%>

<%if (status != null) {%>
function selectpic()
{
  var returnvalue;
  var image = "<%=uploadaurl + icon%>";
  var ext = image.substring(image.lastIndexOf(".")).toLowerCase();

  if (ext == ".gif" || ext == ".jpg" || ext == ".jpeg" || ext == ".png" || ext == ".bmp")
  {
    returnvalue = "<IMG src=" + image + " hspace=<%=hp%> vspace=<%=vp%> border=<%=bw%>";
    if ("<%=aju%>" != "")
      returnvalue = returnvalue + " align='<%=aju%>' alt='<%=alttext%>'";
    else
      returnvalue = returnvalue + " alt='<%=alttext%>'";

    if (<%=width%> > 0 && <%=height%> > 0)
      returnvalue = returnvalue + " width=<%=width%> height=<%=height%>>";
    else if (<%=width%> > 0 && <%=height%> == 0)
      returnvalue = returnvalue + " width=<%=width%>>";
    else if (<%=width%> == 0 && <%=height%> > 0)
      returnvalue = returnvalue + " height=<%=height%>>";
    else
      returnvalue = returnvalue + ">";
  }
  else if (ext == ".swf")
  {
    returnvalue = "<OBJECT classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' height=<%=height%> width=<%=width%>>";
    returnvalue = returnvalue + "<PARAM NAME=movie VALUE='" + image + "'>";
    returnvalue = returnvalue + "<PARAM NAME=quality VALUE=autohigh>";
    returnvalue = returnvalue + "<EMBED src='" + image + "' quality=autohigh TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' height=<%=height%> width=<%=width%>></EMBED>";
    returnvalue = returnvalue + "</OBJECT>";
  }

  parent.window.returnValue = returnvalue;
  parent.window.close();
}
<%}%>
</script>
</head>

<body>
<%
  if (status != null)
  {
    out.println("<center>");
    String picurl = uploadaurl + icon;
    String ext = icon.substring(icon.lastIndexOf(".")).toLowerCase();

    //显示上传的图片
    if (ext.equals(".gif") || ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".png") || ext.equals(".bmp"))
    {
      out.println("<img src=" + picurl + " border=0>");
    }
    else if (ext.equals(".swf"))
    {
%>
<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0" width="<%=width%>" height="<%=height%>">
<param name="movie" value="<%=picurl%>">
<param name="quality" value=autohigh>
<embed src="<%=picurl%>" quality=high pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" width="<%=width%>" height="<%=height%>">
</embed>
</object>
<%}%>
<font color=red class=itm><br><b>如果你能正常显示上面的图片或FLASH，表示上传成功。</font></b><br><br>
<input type="button" value="  确定  " class=tine onClick="selectpic();">&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="  取消  " class=tine onClick="window.close();">
</center>

<%}else{%>
<form name="form1" action="<%=request.getContextPath()%>/multipartformserv?dir=<%=dir%>" method="post" enctype="multipart/form-data" onSubmit="return validate();">
<input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/editimage.jsp">
<table align="center" width="596">
    <tr>
      <td>简体图片：<input type=file name="sfilename" size=30 class=form onchange="javascript:cal()">&nbsp;&nbsp;<input type=submit value="  上传  " class=tine></td>
    </tr>
    <tr>
      <td>繁体图片：<input type=file name="tfilename" size=30 class=form>&nbsp;&nbsp;<input type="button" value="  取消  " onClick="window.close();" class=tine></td>
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
              <table border=0 width="100%" cellspacing=2 cellpadding=2>
              <TR height=30>
                <TD>替换文字：<input name="alttext" size="12" value="<%=alttext%>"></TD>
              </TR>
              <tr height=30>
                <td>对齐方式：<select name="aju" size="1" style="width:95">
                  <option value="" selected>不设置</option>
                  <option value="left" <%if(aju.equals("left")){%>selected<%}%>>左</option>
                  <option value="right" <%if(aju.equals("right")){%>selected<%}%>>右</option>
                  <option value="top" <%if(aju.equals("top")){%>selected<%}%>>顶部</option>
                  <option value="center" <%if(aju.equals("center")){%>selected<%}%>>中</option>
                  <option value="bottom" <%if(aju.equals("bottom")){%>selected<%}%>>底部</option>
                  <option value="absMiddle" <%if(aju.equals("absMiddle")){%>selected<%}%>>绝对中间</option>
                  <option value="absBottom" <%if(aju.equals("absBottom")){%>selected<%}%>>绝对底部</option>
                  <option value="textTop" <%if(aju.equals("textTop")){%>selected<%}%>>文本顶部</option>
                  <option value="basicline" <%if(aju.equals("basicline")){%>selected<%}%>>基线</option>
                  </select>
                </td>
              </tr>
              <tr height=30>
                <td>图像宽度：<input name="<%=MultipartFormHandle.MAXWIDTHPARAMNAME%>" size="12" value="<%=(width==0)?"":String.valueOf(width)%>"></td>
              </tr>
              <tr height=30>
                <td>图像高度：<input name="<%=MultipartFormHandle.MAXHEIGHTPARAMNAME%>" size="12" value="<%=(height==0)?"":String.valueOf(height)%>"></td>
              </tr>
              <tr height=30>
                <td>边框宽度：<input name="bw" size="12" value="<%=bw%>"></td>
              </tr>
              <tr height=30>
                <td>水平间隔：<input name="hp" size="12" value="<%=hp%>"></td>
              </tr>
              <tr height=30>
                <td>垂直间隔：<input name="vp" size="12" value="<%=vp%>"></td>
              </tr>
              <tr><td height=40 align=center><input type=button value="  修改  " onclick="updatepic();" class=tine></td></tr>
              </table>
            </td>
            <td valign="top">
              <iframe src="showImage.html" width="400" marginwidth="0" height="280" marginheight="0" scrolling="auto" frameborder="1" name=frm></iframe>
            </td>
          </tr>
        </table>
      </TD>
    </TR>
</table>
<input type="hidden" name="status" value="save">
<input type="hidden" name="picname" value="<%=picname%>">
<input type="hidden" name="column" value="<%=columnID%>">
<input type="hidden" name="baseDir" value="<%=baseDir%>">
<input type="hidden" name="fileDir" value="<%=tempDir%>">
<input type="hidden" name="sitename" value="<%=sitename%>">
<input type="hidden" name="siteid" value="<%=siteid%>">
<input type="hidden" name="username" value="<%=username%>">
<input type="hidden" name="imgflag" value="<%=imgflag%>">
<input type="hidden" name="fromflag" value="picture">
</form>
<%}%>

</body>
</html>