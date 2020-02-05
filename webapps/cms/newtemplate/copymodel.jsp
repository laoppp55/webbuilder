<%@ page import="java.util.*,
                 java.io.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.systmeTemplate.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  { 
          response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
          return;
  }
  String rootpath = request.getRealPath("/");
  String domain = authToken.getSitename();
  int siteid = authToken.getSiteID();
  int imgflag = authToken.getImgSaveFlag();
  String username = authToken.getUserID();

  String img = ParamUtil.getParameter(request, "model");

  img = StringUtil.replace(img,"@",java.io.File.separator);
  int modelid = Integer.parseInt(img.substring(0,img.indexOf("$")));
  String aimdir = img.substring(img.indexOf("$")+1,img.lastIndexOf("$"));
  String filename = img.substring(img.lastIndexOf("$")+1,img.length());

  String dirname = aimdir;
  aimdir = "/webbuilder/sites/" + domain + "/" + "_templates" + aimdir;
  aimdir = StringUtil.replace(aimdir,"/",java.io.File.separator);

  ISystemTemplateManager systemtemplateMgr = systemTemplatePeer.getInstance();
  SystemTemplate systemtemplate = new SystemTemplate();
  systemtemplateMgr.create(aimdir,filename,rootpath,domain,dirname,imgflag,siteid,username);

  //删除模板中的.jpg和.zip文件
  String delfile = rootpath + aimdir + filename.substring(0,filename.indexOf(".")) + ".jpg";
  File file = new File(delfile);
  if (file.exists())
  {
    file.delete();
  }

  delfile = rootpath + aimdir + filename.substring(0,filename.indexOf(".")) + ".zip";
  file = new File(delfile);
  if (file.exists())
  {
    file.delete();
  }

  out.println("<script language=\"javascript\">");
  out.println("alert(\"已完成。请选择使用模板，使用您选择的系统模板！\");");
  out.println("parent.window.close();");
  out.println("</script>");
%>