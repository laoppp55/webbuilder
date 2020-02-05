/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._template;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.security.*;
import com.bizwink.upload.*;
import com.jspsmart.upload.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.util.*;

public class _styleimageright__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = null;

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html;charset=utf-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    int siteID = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int type = ParamUtil.getIntParameter(request, "type", 0);

    String imgPath = application.getRealPath("/") + "sites" + java.io.File.separator +
            sitename + java.io.File.separator + "_sys_ListImages" + java.io.File.separator;
    java.io.File file = new java.io.File(imgPath);
    if (!file.exists()) {
        file.mkdirs();
    }

    boolean doUpload = ParamUtil.getBooleanParameter(request, "doUpload");
    if (doUpload) {
        SmartUpload upload = new SmartUpload();
        upload.initialize(pageContext);
        upload.upload();

        RandomStrg random = new RandomStrg();
        random.setCharset("0-9");
        random.setLength(6);
        random.generateRandomObject();
        String filename = random.getRandom();

        File uploadFile = upload.getFiles().getFile(0);
        String ext = uploadFile.getFileExt();
        String filePath = imgPath + filename + "." + ext;
        uploadFile.saveAs(filePath);

        IPublishManager publishMgr = PublishPeer.getInstance();
        publishMgr.publish(username, filePath, siteID, "/_sys_ListImages/", 0);

        String viewer = request.getHeader("user-agent");
        out.println("<script language=javascript>");
        if (viewer.toLowerCase().indexOf("gecko") == -1) {
            out.println("window.returnValue = \"<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + "." + ext + " border=0>\";");
            out.println("window.close();");
        } else {
            out.println("var returnvalue = \"<IMG src=/webbuilder/sites/<" + "%%sitename%%" + ">/_sys_ListImages/" + filename + "."
                    + ext + " border=0>\";" +
                    "window.parent.opener.top.frames[\"cmsright\"].storeCaret(window.parent.opener.top.frames[\"cmsright\"]." +
                    "document.getElementById('content'));window.parent.opener.top.frames[\"cmsright\"].insertAtCaret(window." +
                    "parent.opener.top.frames[\"cmsright\"].document.getElementById('content'),returnvalue);top.close();");
            out.println("top.close();");
        }
        out.println("</script>");
        return;
    }

    out.write(_jsp_string1, 0, _jsp_string1.length);
    if(type == 0){
    out.write(_jsp_string2, 0, _jsp_string2.length);
    }else if(type == 6){
    out.write(_jsp_string3, 0, _jsp_string3.length);
    }
    out.write(_jsp_string4, 0, _jsp_string4.length);
    
                int j = 1;
                java.io.File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String filename = files[i].getName();
                        if (filename.indexOf(".") > -1) {
                            String extname = filename.substring(filename.indexOf(".") + 1, filename.length()).toLowerCase();
                            if (extname.equals("gif") || extname.equals("jpg") || extname.equals("jpeg") || extname.equals("bmp") || extname.equals("pcx"))
                                out.println("<a href=# onclick=SelectPic('" + filename + "');><img src=/webbuilder/sites/" + sitename + "/_sys_ListImages/" + filename + " border=1 width=20 height=20></a>&nbsp;");
                            if (j % 12 == 0) out.println("<br>");
                            j++;
                        }
                    }
                }
            
    out.write(_jsp_string5, 0, _jsp_string5.length);
  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != 6165118203484593848L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("template/styleImageRight.jsp"), -4920968928081108818L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
  }

  final static class TagState {

    void release()
    {
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }

  private final static char []_jsp_string5;
  private final static char []_jsp_string3;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  static {
    _jsp_string5 = "\r\n        </td>\r\n    </tr>\r\n</table>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string3 = "\r\n                    var returnvalue = \"<IMG src=/webbuilder/sites/<\" + \"%%sitename%%\" + \">/_sys_ListImages/\" + filename + \" border=0>\";\r\n                    window.parent.opener.storeCaret(window.parent.opener.document.getElementById('content'));\r\n                    window.parent.opener.insertAtCaret(window.parent.opener.document.getElementById('content'), returnvalue);\r\n                    top.close();\r\n                ".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title>\u5217\u8868\u56fe\u6807</title>\r\n    <meta http-equiv=\"Pragma\" content=\"no-cache\">\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet type=text/css href=\"../style/global.css\">\r\n    <script language=javascript>\r\n        function check()\r\n        {\r\n            var filename = document.uploadForm.filename.value;\r\n            if (filename == \"\")\r\n            {\r\n                return false;\r\n            }\r\n            else\r\n            {\r\n                var ext = filename.substring(filename.lastIndexOf(\".\") + 1);\r\n                if (ext != \"gif\" && ext != \"jpg\" && ext != \"jpeg\" && ext != \"bmp\" && ext != \"png\")\r\n                {\r\n                    alert(\"\u56fe\u7247\u6587\u4ef6\u6269\u5c55\u540d\u5e94\u4e3a.gif .jpg .jpeg .bmp .png\uff01\");\r\n                    return false;\r\n                }\r\n            }\r\n            return true;\r\n        }\r\n\r\n        function SelectPic(filename)\r\n        {\r\n            var isMSIE = (navigator.appName == \"Microsoft Internet Explorer\");\r\n            if (isMSIE) {\r\n                window.returnValue = \"<IMG src=/webbuilder/sites/<\" + \"%%sitename%%\" + \">/_sys_ListImages/\" + filename + \" border=0>\";\r\n                window.close();\r\n            } else {\r\n                ".toCharArray();
    _jsp_string2 = "\r\n                    var returnvalue = \"<IMG src=/webbuilder/sites/<\" + \"%%sitename%%\" + \">/_sys_ListImages/\" + filename + \" border=0>\";                    \r\n                    window.parent.opener.top.frames[\"cmsright\"].storeCaret(window.parent.opener.top.frames[\"cmsright\"].document.getElementById('content'));\r\n                    window.parent.opener.top.frames[\"cmsright\"].insertAtCaret(window.parent.opener.top.frames[\"cmsright\"].document.getElementById('content'), returnvalue);\r\n                    top.close();\r\n                ".toCharArray();
    _jsp_string4 = "\r\n            }\r\n        }\r\n    </script>\r\n</head>\r\n<body bgcolor=\"#CCCCCC\">\r\n<table border=\"1\" width=\"98%\" align=center borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=2 cellSpacing=0>\r\n    <tr height=50>\r\n        <td align=center><br>\r\n\r\n            <form action=\"styleImageRight.jsp?doUpload=true\" method=\"POST\" name=\"uploadForm\"\r\n                  enctype=\"multipart/form-data\" onsubmit=\"return check();\">\r\n                <input type=\"file\" name=\"filename\" size=\"20\">&nbsp;&nbsp;\r\n                <input type=submit value=\" \u4e0a\u4f20 \">&nbsp;&nbsp;\r\n                <input type=button value=\" \u53d6\u6d88 \" onclick=\"top.close();\">\r\n            </form>\r\n        </td>\r\n    </tr>\r\n    <tr>\r\n        <td>\r\n            ".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
