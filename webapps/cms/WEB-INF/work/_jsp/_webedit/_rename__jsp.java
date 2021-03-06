/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._webedit;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.io.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.webedit.*;

public class _rename__jsp extends com.caucho.jsp.JavaPage
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
    
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
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

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((filePath));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((filename));
    out.write(_jsp_string3, 0, _jsp_string3.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("webedit/rename.jsp"), -5251339835002795837L, false);
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

  private final static char []_jsp_string2;
  private final static char []_jsp_string3;
  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  static {
    _jsp_string2 = "\">\r\n  <input type=\"hidden\" name=\"publish\" value=\"false\">\r\n  <table border=0 width=\"80%\" align=center>\r\n    <tr>\r\n      <td height=\"50\">\r\n        \u6587\u4ef6\u540d\uff1a<input name=\"filename\" size=\"20\" value=\"".toCharArray();
    _jsp_string3 = "\" class=tine>\r\n      </td>\r\n    </tr>\r\n    <tr>\r\n      <td height=\"50\">\r\n        <input type=\"submit\" value=\"  \u4fdd\u5b58  \" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;\r\n        <input type=\"button\" value=\"  \u5173\u95ed  \" class=tine onclick=\"window.close();\">\r\n      </td>\r\n    </tr>\r\n  </table>\r\n</form>\r\n\r\n</body>\r\n</html>".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n  <link REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"../style/global.css\">\r\n  <title>\u6587\u4ef6\u6539\u540d</title>\r\n  <script language=javascript>\r\n      function check()\r\n      {\r\n          if (editForm.filename.value == \"\")\r\n              return false;\r\n          else if (confirm(\"\u662f\u5426\u5c06\u6587\u4ef6\u53d1\u5e03\u5230WEB\u670d\u52a1\u5668\u4e0a\uff1f\"))\r\n              editForm.publish.value = \"true\";\r\n          return true;\r\n      }\r\n  </script>\r\n</head>\r\n\r\n<body bgcolor=\"#cccccc\">\r\n<form method=\"POST\" action=\"rename.jsp\" name=editForm onsubmit=\"javascript:return check();\">\r\n  <input type=\"hidden\" name=\"doUpdate\" value=\"true\">\r\n  <input type=\"hidden\" name=\"filePath\" value=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
