<%@ page import="com.bizwink.upload.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
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
  int big5flag = authToken.getPublishFlag();
  int columnID  = ParamUtil.getIntParameter(request, "column", 0);
  int languageType = ParamUtil.getIntParameter(request, "language", 0);
  int globalflag   = ParamUtil.getIntParameter(request, "global", 0);
  int gsiteid =ParamUtil.getIntParameter(request, "gsid", 0);
  int usegc = ParamUtil.getIntParameter(request, "ugc", 0);

  String baseDir = application.getRealPath("/");
  String dir = baseDir + "sites" + java.io.File.separator + sitename;
%>

<HTML>
<HEAD>
<TITLE>上传新模板</TITLE>
<link rel=stylesheet type="text/css" href="/webbuilder/style/global.css">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<script type="text/javascript">
function validate()
{
  if ((uploadfile.sfilename.value == "" || uploadfile.sfilename.value == null) &&
      (uploadfile.htmlfilename.value == "" || uploadfile.htmlfilename.value == null) &&
      (uploadfile.cssfilename.value == "" || uploadfile.cssfilename.value == null) &&
      (uploadfile.scriptfilename.value == "" || uploadfile.scriptfilename.value == null))
  {
    alert("请选择要上传文件");
    uploadfile.htmlfilename.focus();
    return false;
  }
  return true;
}

function openwin(filename) {
  window.open(filename);
}
</SCRIPT>
</HEAD>
<BODY>
<form method="post" action="<%=request.getContextPath()%>/multipartformserv?siteid=<%=siteid%>&sitename=<%=sitename%>" name=uploadfile enctype="multipart/form-data" onSubmit="return validate();">
<input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>" value="/upload/do_modeluploadforprogram.jsp">
<input type="hidden" name="column"    value="<%=columnID%>">
<input type="hidden" name="language"    value="<%=languageType%>">
<input type="hidden" name="global"    value="<%=globalflag%>">
<input type="hidden" name="gsid"    value="<%=gsiteid%>">
<input type="hidden" name="ugc"    value="<%=usegc%>">
<input type="hidden" name="baseDir"   value="<%=baseDir%>">
<input type="hidden" name="fileDir"   value="_programs">
<input type="hidden" name="sitename"  value="<%=sitename%>">
<input type="hidden" name="siteid"    value="<%=siteid%>">
<input type="hidden" name="username"  value="<%=username%>">
<input type="hidden" name="imgflag"   value="<%=imgflag%>">
<input type="hidden" name="big5flag"  value="<%=big5flag%>">
<input type="hidden" name="fromflag"  value="model">
<TABLE CELLSPACING=10 align=center width="100%">
  <tr>
    <td><li><b>支持可以上传的文件包括HTML文件，文件扩展名可以是.html、.htm、.jsp、.asp、.php、.shtml、.shtm</b><br>
        <li><b>支持的压缩文件包为ZIP格式的压缩文件包，其他压缩格式不支持，压缩包中最好不要包含中文文件名，否则可能会
        出现错误</b><br>
        <li><b>支持的图象格式包括.bmp、.jpg、.jpeg、.gif、.png</b><br>
        <li><b>本站点默认的模板图象存储位置为“站点根目录”</b>
    </td>
  </tr>
  <tr>
    <td>模板HTML文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=htmlfilename name=htmlfilename size=25></td>
  </tr>
  <tr>
    <td>上传CSS文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=cssfilename name=cssfilename size=25></td>
  </tr>
  <tr>
    <td>上传脚本文件：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT type=file ID=scriptfilename name=scriptfilename size=25></td>
  </tr>
  <tr>
    <td>模板简体图象包/图象：<INPUT type=file ID=sfilename name=sfilename size=25></td>
  </tr>
  <TR height=40>
    <TD align=center>&nbsp;&nbsp;
    </TD>
  </TR>
  <TR height=40>
    <TD align=center>
      <input type="submit" name=ok value="  确定  " class=tine>&nbsp;&nbsp;
      <input type="button" name=Cancel onclick="parent.window.close();" value="  取消  " class=tine>
    </TD>
  </TR>
</TABLE>
</FORM>

</BODY>
</HTML>