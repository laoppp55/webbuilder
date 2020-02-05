<%@ page import="java.util.*,
                 java.io.*,
                 java.sql.Timestamp,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.publish.*,
                 com.bizwink.upload.*,
                 com.bizwink.net.ftp.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>

<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect("../login.jsp");
    return;
  }
  if (!SecurityCheck.hasPermission(authToken, 54))
  {
    request.setAttribute("message", "无管理用户的权限");
    response.sendRedirect("editMember.jsp?user=" + authToken.getUserID());
    return;
  }

  int siteID = authToken.getSiteID();
  String path = request.getRealPath("/") + "sites" + File.separator +
                authToken.getSitename() + File.separator + "include" + File.separator;

  //发布该文件
  String name = ParamUtil.getParameter(request, "filename");
  if (name != null && name.trim().length() > 0)
  {
    IPublishManager publishMgr = PublishPeer.getInstance();
    int code = publishMgr.publish(authToken.getUserID(),path+name,siteID,"/include/",0);
    if (code == 0)
      response.sendRedirect("fragManage.jsp?msg=" + name + StringUtil.gb2iso("发布成功!"));
    else
      response.sendRedirect("fragManage.jsp?msg=" + name + StringUtil.gb2iso("发布失败!"));
    return;
  }

  //action=1下载所有包含文件或 action=2下载某一个包含文件
  int action = ParamUtil.getIntParameter(request, "action", 0);
  if (action == 1 || action == 2)
  {
    int count = 0;
    IPullContentManager pullContentMgr = PullContentPeer.getInstance();
    int pubWay = pullContentMgr.getPublishWay(siteID);

    if (pubWay == 0)    //FTP方式
    {
      PullContent pullContent = pullContentMgr.getSiteInfo(siteID);
      String siteIP = pullContent.getSiteIP();
      String username = pullContent.getFtpUser();
      String password = pullContent.getFtpPasswd();

      FTPClient ftp = new FTPClient(siteIP, 21);
      ftp.login(username, password);
      ftp.chdir("/");

      try
      {
        ftp.chdir("/include/");
      }
      catch (FTPException f)
      {
        if (f.getReplyCode() == 550)
          ftp.mkdir("/include/");
      }

      ftp.setType(FTPTransferType.ASCII);
      if (action == 1)
      {
        String[] filename = ftp.dir("/include/", true);
        for (int i=0; i<filename.length; i++)
        {
          String temp = filename[i].substring(0, filename[i].indexOf(" "));
          if (temp.equals("total")) continue;
          String fname = filename[i].substring(filename[i].lastIndexOf(" ")+1,filename[i].length());

          if (fname.indexOf(".") > 0)
          {
            String ext = fname.substring(fname.lastIndexOf(".")+1,fname.length()).toLowerCase();
            if (ext.equals("htm") || ext.equals("html") || ext.equals("shtm") ||
                ext.equals("shtml") || ext.equals("asp") || ext.equals("jsp") ||
                ext.equals("php") || ext.equals("inc") || ext.equals("js") ||
                ext.equals("css") || ext.equals("txt"))
            {
              ftp.get(path + fname, fname);
              count++;
            }
          }
        }
      }
      else
      {
        String file_name = ParamUtil.getParameter(request, "file_name");
        ftp.get(path + file_name, file_name);
        count++;
      }
      ftp.quit();
    }
    else    //本地方式
    {
      PullContent pullContent = pullContentMgr.getSiteInfo(siteID);
      String docPath = pullContent.getDocPath();
      if (!docPath.substring(docPath.length()-1,docPath.length()).equals(File.separator))
      {
        docPath = docPath + File.separator;
      }

      if (action == 1)
      {
        File file = new File(docPath+"include"+File.separator);
        if (file.exists())
        {
          count = file.listFiles().length;
          FileDeal.CopyDir(docPath+"include"+File.separator, path, 0);
        }
      }
      else
      {
        String file_name = ParamUtil.getParameter(request, "file_name");
        FileDeal.copy(docPath+"include"+File.separator+file_name, path+file_name, 0);
        count++;
      }
    }
    response.sendRedirect("fragManage.jsp?msg="+StringUtil.gb2iso("共下载了 ")+count+StringUtil.gb2iso(" 个文件！"));
    return;
  }

  //读出所有包含文件
  List list = new ArrayList();
  try
  {
    File file = new File(path);
    if (file.exists())
    {
      File[] files = file.listFiles();
      for (int i=0; i<files.length; i++)
      {
        if (files[i].isFile())
        {
          String filename = files[i].getName();
          if (filename.indexOf(".") > 0)
          {
            String extName = filename.substring(filename.indexOf(".")+1,filename.length());
            String[] arr = new String[4];
            arr[0] = filename;
            arr[1] = extName.toUpperCase();
            arr[2] = String.valueOf(new Timestamp(files[i].lastModified())).substring(0,19);
            arr[3] = String.valueOf(files[i].length());
            list.add(arr);
          }
        }
      }
    }
    else
    {
      file.mkdirs();
    }
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }

  String msg = ParamUtil.getParameter(request, "msg");
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language="javascript">
function CreateFile()
{
  window.open("createIncFile.jsp","CreateFile","width=700,height=500,left=50,top=50");
}

function EditFile(filename)
{
  window.open("editIncFile.jsp?filename="+filename,"EditFile","width=700,height=500,left=50,top=50");
}

function PublishFile(filename)
{
  var retval = confirm("您确定要发布吗？");
  if (retval)
    window.location = "fragManage.jsp?filename="+filename;
}

function getAllFile()
{
  var retval = confirm("您确定要下载所有包含文件吗？");
  if (retval)
    window.location = "fragManage.jsp?action=1";
}

function getFile(filename)
{
  var retval = confirm("您确定要下载该文件吗？");
  if (retval)
    window.location = "fragManage.jsp?action=2&file_name="+filename;
}
</script>
</head>

<body>
<%
  String[][] titlebars = {
    { "系统设置", "../main.jsp" },
    { "包含文件", "" }
  };
  String[][] operations = {
    {"创建包含文件",  "javascript:CreateFile();"},
    {"样式文件", "listStyle.jsp"},
    {"CSS管理", "cssManage.jsp"},
    {"系统管理", "index.jsp"}
  };
%>
<%@ include file="../inc/titlebar.jsp" %>
<table border=0 width="100%"><tr><td width="50%"><font class=line>包含文件列表</font></td><td width="50%">
<%if (msg != null) out.println("<span class=cur>"+msg+"</span>");%></td></tr></table>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
<tr bgcolor="#eeeeee" class=tine>
   <td width="5%" align=center>编号</td>
   <td width="20%" align=center><b>文件名称</b></td>
   <td width="15%" align=center>文件类型</td>
   <td width="15%" align=center>文件大小</td>
   <td width="20%" align=center>修改日期</td>
   <td width="8%" align=center>修改</td>
   <td width="8%" align=center>发布</td>
   <td width="9%" align=center>下载</td>
</tr>
<%
  for (int i=0; i<list.size(); i++)
  {
    String[] arr = (String[])list.get(i);
    String bgcolor = (i%2==0)?"#ffffcc":"#eeeeee";
%>
<tr bgcolor="<%=bgcolor%>" height=22>
   <td align=center><font color=red><%=i+1%></font></td>
   <td>&nbsp;&nbsp;<%=arr[0]%></td>
   <td align=center><%=arr[1]%>&nbsp;类型</td>
   <td align=center><%=arr[3]%>&nbsp;Bytes</td>
   <td align=center><%=arr[2]%></td>
   <td align=center><a href="javascript:EditFile('<%=arr[0]%>');"><img src="../images/edit.gif" border=0></a></td>
   <td align=center><a href="javascript:PublishFile('<%=arr[0]%>');"><img src="../images/edit.gif" border=0></a></td>
   <td align=center><a href="javascript:getFile('<%=arr[0]%>');"><img src="../images/edit.gif" border=0></a></td>
</tr>
<%}%>
<tr><td colspan=8 align=right height=40><input type=button name=getFile value=下载所有包含文件 onclick="getAllFile();">&nbsp;</td></tr>
</table>
</body>
</html>