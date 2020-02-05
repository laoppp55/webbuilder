<%@ page import="java.io.*,
		 com.bizwink.cms.security.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp" );
    return;
  }
  if (!SecurityCheck.hasPermission(authToken,54))
  {
    request.setAttribute("message","无管理用户的权限");
    response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
    return;
  }

  String msg = null;
  String sitename = authToken.getSitename();
  String path = ParamUtil.getParameter(request, "path");

  String dirName = path.substring(path.indexOf(sitename)+sitename.length(), path.length());
  dirName = StringUtil.replace(dirName, File.separator, "/");

  path = StringUtil.replace(path, "/", File.separator);
  boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

  if (doCreate)
  {
    try
    {
      String filename = ParamUtil.getParameter(request, "filename");
      String content = ParamUtil.getParameter(request, "content");
      int pubflag = ParamUtil.getIntParameter(request, "pubflag", 0);
      if (filename!=null && filename.trim().length()>0 && content!=null && content.trim().length()>0)
      {
        File file = new File(path+filename);
        if (!file.exists())
        {
          PrintWriter pw = new PrintWriter(new FileOutputStream(path+filename));
          pw.write(content.trim());
          pw.close();

          if (pubflag == 1)
          {
            IPublishManager publishMgr = PublishPeer.getInstance();
            publishMgr.publish(authToken.getUserID(),path+filename,authToken.getSiteID(),dirName,0);
          }

          out.println("<script language=javascript>");
          out.println("opener.history.go(0);");
          out.println("window.close();");
          out.println("</script>");
          return;
        }
        else
        {
          msg = "<span class=line>文件重名</span>";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
%>

<html>
<head>
  <title>碎片管理--创建CSS文件</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="../style/global.css">
  <script language="javascript">
      function check()
      {
          var name = document.createForm.filename.value;
          var extname = name.substring(name.indexOf(".")+1, name.length);
          if (name == "")
          {
              alert("文件名不能为空！");
              return false;
          }
          if (extname.toLowerCase() != "css")
          {
              alert("文件名格式不正确！");
              return false;
          }
          if (document.createForm.content.value == "")
          {
              alert("CSS文件内容不能为空！");
              return false;
          }
          return true;
      }
  </script>
</head>

<body bgcolor="#CCCCCC">
<form method="POST" action="createCssFile.jsp" name="createForm" onsubmit="javascript:return check();">
  <input type=hidden name="doCreate" value="true">
  <input type=hidden name="path" value="<%=path%>">
  <%if (msg != null) out.println(msg);%>
  <table border="0" width="100%">
    <tr height=50>
      <td width="10%" align=center>文件名称：</td>
      <td width="90%">
        <input type="text" name="filename" size="30">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="checkbox" name="pubflag" value="1" checked> 需要发布
      </td>
    </tr>
    <tr>
      <td align=center valign=top>文件内容：</td>
      <td><textarea rows="25" name="content" cols="82"></textarea></td>
    </tr>
    <tr height="40">
      <td width="100%" colspan="2" align=center>
        <input type="submit" value="  保存  " name="submit">&nbsp;&nbsp;&nbsp;
        <input type="button" value="  取消  " name="cancel" onclick="javascript:window.close();">
      </td>
    </tr>
  </table>
</form>

</body>
</html>