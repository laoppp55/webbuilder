<%@page import="java.io.*,
                java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.news.*,
                com.bizwink.cms.publish.*,
                com.bizwink.cms.sitesetting.*,
                com.jspsmart.upload.*"
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
  int siteID = authToken.getSiteID();
  boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
  int columnID = ParamUtil.getIntParameter(request, "column", 0);

  IColumnManager columnMgr = ColumnPeer.getInstance();
  Column column = columnMgr.getColumn(columnID);
  String dirName = column.getDirName();

  if (doCreate)
  {
    SmartUpload mySmartUpload = new SmartUpload();
    mySmartUpload.initialize(this.getServletConfig(), request, response);
    mySmartUpload.upload();
    mySmartUpload.setCharset("gbk");

    int from = Integer.parseInt(mySmartUpload.getRequest().getParameter("from"));
    int hostID = Integer.parseInt(mySmartUpload.getRequest().getParameter("hostID"));

    String filename = null;
    if (from == 1)
    {
      com.jspsmart.upload.Files uploadFiles = mySmartUpload.getFiles();
      com.jspsmart.upload.File tempFile = uploadFiles.getFile(0);
      filename = tempFile.getFileName();

      String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename +
              java.io.File.separator + "download" + java.io.File.separator;

      java.io.File file = new java.io.File(uploadPath);
      if (!file.exists()) file.mkdirs();
      mySmartUpload.save(uploadPath);

      //发布到WEB服务器
      String objDir = dirName + "download/";
      IPublishManager publishMgr = PublishPeer.getInstance();

      int retcode = 0;
      if (hostID > 0)
        retcode = publishMgr.publish(username, uploadPath+filename, siteID, objDir, 0, hostID);
      else
        retcode = publishMgr.publish(username, uploadPath+filename, siteID, objDir, 0);

      if (retcode != 0)
      {
        out.println("向WEB服务器发布文件失败！");
        return;
      }
      filename = dirName + "download/" + filename;
    }
    else
    {
      filename = mySmartUpload.getRequest().getParameter("path");
    }

    String width = mySmartUpload.getRequest().getParameter("width");
    String height = mySmartUpload.getRequest().getParameter("height");
    String songname = mySmartUpload.getRequest().getParameter("songname");
    int auto = Integer.parseInt(mySmartUpload.getRequest().getParameter("auto"));
    if (songname != null && songname.trim().length() > 0)
    {
      songname = StringUtil.replace(songname,"[","");
      songname = StringUtil.replace(songname,"]","");
    }

    out.println("<script language=javascript>");
    out.println("var retval = \"[TAG][MEDIA][SONGNAME]"+songname+"[/SONGNAME][PATH]"+filename+"[/PATH][SIZE]"+width+","+height+"[/SIZE][AUTO]"+auto+"[/AUTO][/MEDIA][/TAG]\";");
    out.println("window.opener.tbContentElement.DOM.selection.createRange().pasteHTML(retval);");
    out.println("window.close();");
    out.println("</script>");
    return;
  }

  //读出当前站点所有发布主机
  IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
  List siteIPList = ftpsetMgr.getOtherFtpInfos(siteID);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
<title>多媒体文件上传</title>
<script language=javascript>
function check()
{
  if (uploadForm.songname.value == "")
  {
    alert("请填写歌曲名称！");
    uploadForm.songname.focus();
    return false;
  }
  else if (uploadForm.from[0].checked && (uploadForm.path.value == "" || uploadForm.path.value == "http://"))
  {
    alert("请填写文件路径！");
    uploadForm.path.focus();
    return false;
  }
  else if (uploadForm.from[1].checked && uploadForm.filename.value == "")
  {
    alert("请选择上传文件！");
    uploadForm.filename.focus();
    return false;
  }
  else if (uploadForm.width.value == "")
  {
    alert("请填写播放窗口宽度！");
    uploadForm.width.focus();
    return false;
  }
  else if (uploadForm.height.value == "")
  {
    alert("请填写播放窗口高度！");
    uploadForm.height.focus();
    return false;
  }
  else
  {
    var filename = uploadForm.path.value;
    if (uploadForm.from[1].checked)
      filename = uploadForm.filename.value;
    if (filename.indexOf(".") > 0)
    {
      var ext = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
      if (ext=="asf" || ext=="wmv" || ext=="mpg" || ext=="avi" || ext=="mpeg" || ext=="mpg" || ext=="dat" || ext=="rm" || ext=="rmvb")
        uploadForm.height.value = "350";
    }
    return true;
  }
}
</script>
</head>

<body bgcolor="#cccccc">
<FORM METHOD=POST ACTION="upload_audio.jsp?column=<%=columnID%>&doCreate=true" NAME="uploadForm" onsubmit="return check();" enctype="multipart/form-data">
<table width="100%" align=center border=0>
  <tr height=25>
    <td width="25%" align=right>歌曲名称：</td>
    <td width="75%"><input name="songname" size=30></td>
  </tr>
  <tr height=25>
    <td align=right><input type=radio name=from value=0 checked>外站引用：</td>
    <td><input name="path" size=30 value="http://"></td>
  </tr>
  <tr height=25>
    <td align=right><input type=radio name=from value=1>本站上传：</td>
    <td><input type=file name="filename" size=30></td>
  </tr>
  <tr height=25>
    <td align=right>窗口大小：</td>
    <td>宽&nbsp;<input name="width" size=6 value="381">&nbsp;&nbsp;&nbsp;高&nbsp;<input name="height" size=6 value="36"></td>
  </tr>
  <tr height=25>
    <td align=right>播放模式：</td>
    <td><input type=radio name=auto value=1 checked>自动播放&nbsp;&nbsp;&nbsp;<input type=radio name=auto value=0>手动播放</td>
  </tr>
  <tr height=25>
    <td align=right>发布主机：</td>
    <td><select name=hostID style="width:184" size=1><option value="0" selected>默认发布主机</option>
      <%for (int i=0; i<siteIPList.size(); i++){
        FtpInfo ftpInfo = (FtpInfo)siteIPList.get(i);
      %>
      <option value="<%=ftpInfo.getID()%>"><%=ftpInfo.getIp()%></option>
      <%}%>
      </select>
    </td>
  </tr>
  <tr height=40>
    <td align=center colspan=2>
      <input type=submit value="  上传  ">&nbsp;&nbsp;&nbsp;&nbsp;<input type=button value="  取消  " onclick="window.close();">
    </td>
  </tr>
</table>
</FORM>

</body>
</html>
