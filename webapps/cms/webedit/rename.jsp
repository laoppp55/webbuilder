<%@ page import="java.io.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.webedit.*"
         contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }

  String filePath = ParamUtil.getParameter(request, "filePath");
  boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
  String filename = "";

  if (doUpdate)
  {
    filename = ParamUtil.getParameter(request, "filename");
    boolean publish = ParamUtil.getBooleanParameter(request, "publish");
    try
    {
      String path = filePath;
      filePath = StringUtil.replace(filePath, "/", File.separator);
      File oldFile = new File(filePath);
      if (oldFile.exists())
      {
        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
        File newFile = new File(filePath + filename);
        oldFile.renameTo(newFile);
      }

      if (publish)
      {
        int siteID = authToken.getSiteID();
        String siteName = authToken.getSitename();

        IWebEditManager webeditMgr = WebEditPeer.getInstance();
        webeditMgr.RenameFile(path,filename,siteName,siteID);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    out.println("<script language=javascript>opener.history.go(0);window.close();</script>");
    return;
  }

  filename = filePath.substring(filePath.lastIndexOf("/") + 1);
%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
  <title>文件改名</title>
  <script language=javascript>
      function check()
      {
          if (editForm.filename.value == "")
              return false;
          else if (confirm("是否将文件发布到WEB服务器上？"))
              editForm.publish.value = "true";
          return true;
      }
  </script>
</head>

<body bgcolor="#cccccc">
<form method="POST" action="rename.jsp" name=editForm onsubmit="javascript:return check();">
  <input type="hidden" name="doUpdate" value="true">
  <input type="hidden" name="filePath" value="<%=filePath%>">
  <input type="hidden" name="publish" value="false">
  <table border=0 width="80%" align=center>
    <tr>
      <td height="50">
        文件名：<input name="filename" size="20" value="<%=filename%>" class=tine>
      </td>
    </tr>
    <tr>
      <td height="50">
        <input type="submit" value="  保存  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="  关闭  " class=tine onclick="window.close();">
      </td>
    </tr>
  </table>
</form>

</body>
</html>