/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._articlemove;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.File;
import com.bizwink.cms.news.*;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;
import com.google.gson.GsonBuilder;
import com.bizwink.util.JSON;
import com.google.gson.Gson;
import java.util.Map;

public class _copyok__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html;charset=GBK");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();

    int scopeval = ParamUtil.getIntParameter(request, "scope", 0);                 //0\u62f7\u8d1d\u9009\u62e9\u7684\u6587\u7ae0\u5230\u76ee\u7684\u680f\u76ee\uff0c1\u62f7\u8d1d\u672c\u680f\u76ee\u4e0b\u7684\u6240\u6709\u6587\u7ae0\u5230\u76ee\u7684\u680f\u76ee
    int tcolumnID = ParamUtil.getIntParameter(request, "tcolumn", 0);              //\u6587\u7ae0\u8981\u88ab\u62f7\u8d1d\u7684\u76ee\u7684\u680f\u76eeID
    int columnID = ParamUtil.getIntParameter(request, "column", 0);                //\u6587\u7ae0\u539f\u6765\u6240\u5728\u680f\u76eeID
    int execute = ParamUtil.getIntParameter(request, "execute", 0);
    String selArticleIds = ParamUtil.getParameter(request,"articles");
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int pageno = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request, "searchFlag");
    int errcode = 0;
    String appPath = application.getRealPath("/") + "sites" + File.separator + authToken.getSitename();

    try {
        IArticleManager articleMgr = ArticlePeer.getInstance();
        if(execute == 0) {
            IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
            boolean isSame = extendMgr.querySameExtendAttr(columnID, tcolumnID);   //\u5224\u65ad\u539f\u680f\u76ee\u4e0e\u76ee\u6807\u680f\u76ee\u7684\u5c5e\u6027\u5b9a\u4e49\u662f\u5426\u76f8\u540c\uff0c\u76f8\u540c\u6267\u884c\u6587\u7ae0\u62f7\u8d1d\u64cd\u4f5c\uff0c\u4e0d\u76f8\u540c\u5219\u4e0d\u6267\u884c\u62f7\u8d1d\u64cd\u4f5c
            if (isSame)
                errcode = articleMgr.copyArticlesToColumn(scopeval,columnID, tcolumnID, selArticleIds, siteid, appPath, username);
            else
                errcode = 2;                                                        //\u6587\u7ae0\u6240\u5728\u539f\u680f\u76ee\u4e0e\u76ee\u7684\u680f\u76ee\u5b9a\u4e49\u7684\u6587\u7ae0\u5c5e\u6027\u4e0d\u540c\uff0c\u4e0d\u80fd\u6267\u884c\u6587\u7ae0\u62f7\u8d1d\u64cd\u4f5c
        } else {                                                                    //\u4e0d\u8003\u8651\u539f\u680f\u76ee\u4e0e\u76ee\u6807\u680f\u76ee\u7684\u5c5e\u6027\u5b9a\u4e49\u662f\u5426\u76f8\u540c\uff0c\u90fd\u6267\u884c\u6587\u7ae0\u62f7\u8d1d\u64cd\u4f5c\uff0c\u53ef\u80fd\u4e22\u5931\u6587\u7ae0\u7684\u90e8\u5206\u5c5e\u6027\u5b9a\u4e49
            errcode = articleMgr.copyArticlesToColumn(scopeval,columnID, tcolumnID, selArticleIds, siteid, appPath, username);
        }
    } catch (ArticleException e) {
        errcode = -1;
        e.printStackTrace();
    }

    Map data = new HashMap<>();
    data.put("COLUMNID",columnID);
    data.put("ERRCODE",errcode);
    data.put("PAGENO",pageno);
    data.put("RANGE",range);
    data.put("FLAG",flag);
    data.put("ASCDESC",ascdesc);
    data.put("SEARCHFLAG",doSearchFlag);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("articlemove/copyok.jsp"), -4076961696495114180L, false);
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

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}
