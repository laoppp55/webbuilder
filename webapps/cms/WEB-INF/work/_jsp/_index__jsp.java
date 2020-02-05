/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;

public class _index__jsp extends com.caucho.jsp.JavaPage
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
    String username = ParamUtil.getParameter(request, "username");
    String password = ParamUtil.getParameter(request, "password", true);
    boolean doLogin = ParamUtil.getBooleanParameter(request, "doLogin");
    String errorMessage = "&nbsp;";

    if (doLogin) {
        String yzcode = ParamUtil.getParameter(request, "yzmcode");
        String yzcode_from_session = (String)session.getAttribute("randnum");
        IAuthManager authMgr = AuthPeer.getInstance();
        try{
            //\u83b7\u53d6\u7528\u6237\u7aef\u7684IP\u5730\u5740
            String user_ip = null;
            if (request.getHeader("x-forwarded-for") == null) {
                user_ip = request.getRemoteAddr();
            } else {
                user_ip = request.getHeader("x-forwarded-for");
            }

            String firstLoginPage = null;

            if(username.equals("admin"))
                firstLoginPage = "managesites.jsp";
            else
                firstLoginPage = "index1.jsp";

            Auth authToken = authMgr.getAuth(username, password,user_ip);
            if (yzcode!=null) {
                if (yzcode.equalsIgnoreCase(yzcode_from_session)) {
                    if (authToken != null) {
                        int siteid = authToken.getSiteID();
                        session.setAttribute("CmsAdmin", authToken);
                        session.setMaxInactiveInterval(60*60*1000);
                        int modelnum = authMgr.getTemplateNum(siteid);
                        if (modelnum == 0 && !username.equals("admin"))  {                    //\u8f6c\u5411\u6a21\u677f\u9009\u62e9\u9875\u9762

    out.write(_jsp_string1, 0, _jsp_string1.length);
    response.sendRedirect(firstLoginPage);
    out.write(_jsp_string2, 0, _jsp_string2.length);
    
                        } else                                                                  //\u8f6c\u5411\u767b\u5f55\u6210\u529f\u9875\u9762
                            response.sendRedirect(firstLoginPage);
                    } else {
                        errorMessage = "<font color=red>\u767b\u9646\u5931\u8d25!\u8bf7\u91cd\u65b0\u8f93\u5165\u7528\u6237\u540d\u548c\u5bc6\u7801!</font>";
                    }
                } else
                    errorMessage = "<font color=red>\u9a8c\u8bc1\u7801\u8f93\u5165\u9519\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u9a8c\u8bc1\u7801!</font>";
            } else {
                errorMessage = "<font color=red>\u9a8c\u8bc1\u7801\u4e3a\u7a7a\uff0c\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801!</font>";
            }
        } catch (UnauthedException e){
            errorMessage = "<font color=red>\u767b\u9646\u5931\u8d25!\u8bf7\u91cd\u65b0\u8f93\u5165\u7528\u6237\u540d\u548c\u5bc6\u7801!</font>";
        }
        doLogin = false;
    }

    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print(((errorMessage!=null)?errorMessage:""));
    out.write(_jsp_string4, 0, _jsp_string4.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("index.jsp"), -4481573025535003550L, false);
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

  private final static char []_jsp_string4;
  private final static char []_jsp_string1;
  private final static char []_jsp_string3;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  static {
    _jsp_string4 = "</div>\r\n            <div class=\"login_list\">\r\n                <div class=\"txt\">\u7528\u6237\u540d\uff1a</div>\r\n                <div class=\"input\"><input name=\"username\" type=\"text\" /></div>\r\n            </div>\r\n            <div class=\"login_list\">\r\n                <div class=\"txt\">\u5bc6\u7801\uff1a</div>\r\n                <div class=\"input\"><input name=\"password\" type=\"password\" size=\"20\" autocomplete=\"new-password\"></div>\r\n            </div>\r\n            <div class=\"login_yzm\">\r\n                <div class=\"txt\">\u9a8c\u8bc1\u7801\uff1a</div>\r\n                <div class=\"input\"><input name=\"yzmcode\" type=\"text\" /></div>\r\n                <span><img src=\"image.jsp\" id=\"yzImageID\" name=\"yzcodeimage\" width=\"120\" height=\"48\" align=\"absmiddle\"/></span>\r\n                <span><a href=\"javascript:change_yzcodeimage();\">\u770b\u4e0d\u6e05\uff0c\u6362\u4e00\u5f20</a></span>\r\n            </div>\r\n            <div class=\"button\">\r\n                <input name=\"login\" type=\"submit\"  value=\"\u767b\u5f55\"/>\r\n            </div>\r\n        </div>\r\n    </form>\r\n</div>\r\n<script language=\"javascript\">document.forms[0].username.focus();</script>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string1 = "\r\n<script type=\"text/javascript\">\r\n    var ret = confirm(\"\u9009\u62e9\u5df2\u7ecf\u5b58\u5728\u7684\u6a21\u677f\uff1f\");\r\n    if (ret)\r\n        window.location=\"register/webindex.jsp\";\r\n    else\r\n    ".toCharArray();
    _jsp_string3 = "\r\n\r\n<html>\r\n<head>\r\n    <title>\u5185\u5bb9\u7ba1\u7406\u7cfb\u7edf</title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet href=\"css/cms_css.css\" style=\"text:css\">\r\n    <style type=\"text/css\">\r\n        <!--\r\n        body {\r\n            margin-left: 0px;\r\n            margin-top: 0px;\r\n            margin-right: 0px;\r\n            margin-bottom: 0px;\r\n            background-color: #c3c3c3;\r\n        }\r\n        -->\r\n    </style>\r\n    <script src=\"js/md5-min.js\" type=\"text/javascript\"></script>\r\n    <script src=\"js/jquery-1.11.1.min.js\" type=\"text/javascript\"></script>\r\n    <script type=\"text/javascript\">\r\n        function change_yzcodeimage() {\r\n            $(\"#yzImageID\").attr(\"src\",\"image.jsp?temp=\" + Math.random());\r\n        }\r\n\r\n        function tijiao() {\r\n            var username = loginForm.username.value;\r\n            if (username == \"\"){\r\n                alert(\"\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a\");\r\n                return false;\r\n            }\r\n\r\n            if (username.length < 2){\r\n                alert(\"\u7528\u6237\u540d\u957f\u5ea6\u5fc5\u987b2\u4f4d\u4ee5\u4e0a\");\r\n                return false;\r\n            }\r\n\r\n            var passwd = loginForm.password.value;\r\n            if (passwd == \"\"){\r\n                alert(\"\u7528\u6237\u53e3\u4ee4\u4e0d\u80fd\u4e3a\u7a7a\");\r\n                return false;\r\n            }\r\n\r\n            if (username!=\"admin\") {\r\n                /*if (passwd.length < 8){\r\n                    alert(\"\u7528\u6237\u5bc6\u7801\u957f\u5ea6\u5fc5\u987b8\u4f4d\u4ee5\u4e0a\");\r\n                    return false;\r\n                }\r\n\r\n                var regex = /[0-9]/;\r\n                if(!regex.test(passwd)){\r\n                    alert(\"\u5bc6\u7801\u5fc5\u987b\u5305\u542b\u6570\u5b57\");\r\n                    return false;\r\n                }\r\n\r\n                regex = /[a-z]/;\r\n                if(!regex.test(passwd)){\r\n                    alert(\"\u5bc6\u7801\u5fc5\u987b\u5305\u542b\u5c0f\u5199\u5b57\u6bcd\");\r\n                    return false;\r\n                }\r\n\r\n                regex = /[A-Z]/;\r\n                if(!regex.test(passwd)){\r\n                    alert(\"\u5bc6\u7801\u5fc5\u987b\u5305\u542b\u5927\u5199\u5b57\u6bcd\");\r\n                    return false;\r\n                }\r\n\r\n                regex = /\\W/;\r\n                if(!regex.test(passwd)){\r\n                    alert(\"\u5bc6\u7801\u5fc5\u987b\u5305\u542b\u7279\u6b8a\u5b57\u7b26\");\r\n                    return false;\r\n                }*/\r\n            } else {\r\n                if (passwd.length < 2){\r\n                    alert(\"admin\u7528\u6237\u5bc6\u7801\u957f\u5ea6\u5fc5\u987b2\u4f4d\u4ee5\u4e0a\");\r\n                    return false;\r\n                }\r\n            }\r\n\r\n            //loginForm.password.value = hex_md5(passwd);\r\n            //loginForm.username.value = hex_md5(username);\r\n            //return true;\r\n\r\n            //htmlobj=$.ajax({url:\"getLoginStatus.jsp?uid=\" + username,cache:false,async:false});\r\n            statusval = 0;   //htmlobj.responseText;\r\n\r\n            if (statusval == 0) {\r\n                loginForm.password.value = hex_md5(passwd);\r\n                return true;\r\n            } else {\r\n                alert(\"\u8be5\u8d26\u53f7\u5df2\u7ecf\u5728\u5176\u4ed6\u673a\u5668\u767b\u5f55\uff0c\u8bf7\u4ece\u5176\u4ed6\u673a\u5668\u9000\u51fa\");\r\n                return false;\r\n            }\r\n        }\r\n    </script>\r\n</head>\r\n\r\n<body  style=\"background-color:#f0f0f0\">\r\n<div class=\"login\">\r\n    <div class=\"login_left\"><img src=\"images/login_1.jpg\" width=\"373\" height=\"489\" /></div>\r\n    <form action=\"index.jsp\" name=\"loginForm\" method=\"POST\" onsubmit=\"return tijiao();\">\r\n        <input type=hidden name=doLogin value=true>\r\n        <div class=\"login_right\">\r\n            <div class=\"login_tilte\">\u767b\u5f55</div>\r\n            <div class=\"tips\">".toCharArray();
    _jsp_string2 = "\r\n</script>\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}