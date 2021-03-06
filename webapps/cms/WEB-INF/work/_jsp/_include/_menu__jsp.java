/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._include;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.security.SecurityCheck;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.security.Auth;

public class _menu__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html;charset=UTF-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    String username = authToken.getNickname();

    out.write(_jsp_string1, 0, _jsp_string1.length);
    if (((authToken != null) && SecurityCheck.hasPermission(authToken, 1)) || SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string2, 0, _jsp_string2.length);
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    if (((authToken != null) && SecurityCheck.hasPermission(authToken, 2)) || SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string4, 0, _jsp_string4.length);
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    if (((authToken != null) && SecurityCheck.hasPermission(authToken, 3)) || SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string5, 0, _jsp_string5.length);
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    if (((authToken != null) && SecurityCheck.hasPermission(authToken, 7)) || SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string6, 0, _jsp_string6.length);
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    if (((authToken != null) && SecurityCheck.hasPermission(authToken, 6)) || SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 8)) {
    out.write(_jsp_string3, 0, _jsp_string3.length);
    if (SecurityCheck.hasPermission(authToken, 6) || SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string7, 0, _jsp_string7.length);
    } else {
    out.write(_jsp_string8, 0, _jsp_string8.length);
    }}
    out.write(_jsp_string9, 0, _jsp_string9.length);
    if (authToken != null && SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string10, 0, _jsp_string10.length);
    }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    if (authToken != null && SecurityCheck.hasPermission(authToken, 54)) {
    out.write(_jsp_string11, 0, _jsp_string11.length);
    }
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((username));
    out.write(_jsp_string13, 0, _jsp_string13.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("include/menu.jsp"), -7784965585235609822L, false);
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
  private final static char []_jsp_string1;
  private final static char []_jsp_string12;
  private final static char []_jsp_string5;
  private final static char []_jsp_string0;
  private final static char []_jsp_string9;
  private final static char []_jsp_string8;
  private final static char []_jsp_string3;
  private final static char []_jsp_string4;
  private final static char []_jsp_string11;
  private final static char []_jsp_string13;
  private final static char []_jsp_string6;
  private final static char []_jsp_string7;
  private final static char []_jsp_string10;
  static {
    _jsp_string2 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../column/index_new.jsp?rightid=1\">\u680f\u76ee\u7ba1\u7406</a></li>\r\n    ".toCharArray();
    _jsp_string1 = "\r\n<script>\r\n    function goout() {\r\n        window.location.href = \"../exit.jsp\";\r\n    }\r\n</script>\r\n<ul class=\"layui-nav layui-layout-left\">\r\n    ".toCharArray();
    _jsp_string12 = "\r\n</ul>\r\n<ul class=\"layui-nav layui-layout-right\">\r\n    <li class=\"layui-nav-item\">\r\n        <a href=\"javascript:;\">\r\n            <img src=\"/webbuilder/images/help.gif\" class=\"layui-nav-img\">\r\n            \u5e2e\u52a9\r\n        </a>\r\n        <dl class=\"layui-nav-child\">\r\n            <dd><a href=\"\">\u57fa\u672c\u8d44\u6599</a></dd>\r\n            <dd><a href=\"\">\u5b89\u5168\u8bbe\u7f6e</a></dd>\r\n        </dl>\r\n    </li>\r\n    <li class=\"layui-nav-item\"><a href=\"javascript:goout();\">\u6b22\u8fce<font color=\"red\">".toCharArray();
    _jsp_string5 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../article/indexnew.jsp?rightid=3\">\u6587\u7ae0\u7ba1\u7406</a></li>\r\n    ".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string9 = "\r\n    <!--li class=\"layui-nav-item\"><a href=\"javascript:;\">\u7cfb\u7edf\u8bbe\u7f6e</a></li-->\r\n    ".toCharArray();
    _jsp_string8 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../publish/indexnew.jsp?rightid=8\">\u6587\u7ae0\u53d1\u5e03</a></li>\r\n    ".toCharArray();
    _jsp_string3 = "\r\n    ".toCharArray();
    _jsp_string4 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../template/indexnew.jsp?rightid=2\">\u6a21\u677f\u7ba1\u7406</a></li>\r\n    ".toCharArray();
    _jsp_string11 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../toolkit/index.jsp\">\u5de5\u5177\u7bb1</a></li>\r\n    ".toCharArray();
    _jsp_string13 = "</font>&nbsp;&nbsp;\u9000\u51fa</a></li>\r\n</ul>\r\n".toCharArray();
    _jsp_string6 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../articlemove/indexnew.jsp?rightid=7\">\u6587\u7ae0\u8fc1\u79fb</a></li>\r\n    ".toCharArray();
    _jsp_string7 = "\r\n    <li class=\"layui-nav-item\"><a href=\"../publish/indexnew.jsp?rightid=6\">\u6587\u7ae0\u53d1\u5e03</a></li>\r\n    ".toCharArray();
    _jsp_string10 = "\r\n    <li class=\"layui-nav-item\">\r\n        <a href=\"javascript:;\">\u7cfb\u7edf\u8bbe\u7f6e</a>\r\n        <dl class=\"layui-nav-child\">\r\n            <dd><a href=\"../member/indexnew.jsp\">\u7528\u6237\u7ba1\u7406</a></dd>\r\n            <dd><a href=\"../organization/indexnew.jsp\">\u7ec4\u7ec7\u67b6\u6784\u7ba1\u7406</a></dd>\r\n            <!--d><a href=\"../member/index.jsp\">\u7528\u6237\u7ba1\u7406(\u65e7)</a></dd>\r\n            <dd><a href=\"../roles/index.jsp\">\u89d2\u8272\u7ba1\u7406</a></dd>\r\n            <dd><a href=\"../rights/index.jsp\">\u6743\u9650\u7ba1\u7406</a></dd-->\r\n        </dl>\r\n    </li>\r\n    ".toCharArray();
  }
}
