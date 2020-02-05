<%@ page import="java.io.*,
                 java.util.*,
                 com.bizwink.cms.publish.*,
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

  String filePath = ParamUtil.getParameter(request, "filePath");
  boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
  String content = "";

  if (doUpdate)
  {
    content = ParamUtil.getParameter(request, "content");
    boolean publish = ParamUtil.getBooleanParameter(request, "publish");
    try
    {
      PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
      pw.write(content);
      pw.close();

      if (publish)
      {
        int siteID = authToken.getSiteID();
        String siteName = authToken.getSitename();
        String username = authToken.getUserID();
        String dirName = filePath.substring(filePath.indexOf(siteName)+siteName.length(),filePath.lastIndexOf("/")+1);
        filePath = StringUtil.replace(filePath, "/", File.separator);

        IPublishManager publishMgr = PublishPeer.getInstance();
        publishMgr.publish(username,filePath,siteID,dirName,0);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    out.println("<script language=javascript>opener.history.go(0);window.close();</script>");
    return;
  }

  try
  {
    File file = new File(filePath);
    if (file.exists())
    {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line = null;
      while ((line=br.readLine()) != null)
        content = content + line + "\r\n";
      br.close();
    }
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
%>

<html>
<head>
  <title>文件编辑</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link REL="stylesheet" TYPE="text/css" HREF="../style/global.css">
  <script language=javascript>
      function check()
      {
          if (confirm("是否将文件发布到WEB服务器上？"))
              editForm.publish.value = "true";
          return true;
      }
  </script>
</head>

<body bgcolor="#cccccc">
<form action="editfile.jsp" method="post" name=editForm onsubmit="javascript:return check();">
  <input type="hidden" name="doUpdate" value="true">
  <input type="hidden" name="filePath" value="<%=filePath%>">
  <input type="hidden" name="publish" value="false">
  <table border=0 width="100%">
    <tr>
      <td align=center>
        <textarea rows="24" name="content" cols="90" class=tine><%=content%></textarea>
      </td>
    </tr>
    <tr height=40>
      <td align=center>
        <input type="submit" value="  保存  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="  关闭  " class=tine onclick="window.close();">
      </td>
    </tr>
  </table>
</form>

</body>
</html>