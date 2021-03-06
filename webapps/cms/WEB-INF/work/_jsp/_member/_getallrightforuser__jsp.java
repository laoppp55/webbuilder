/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.bizwink.util.JSON;
import java.util.Map;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.news.Column;

public class _getallrightforuser__jsp extends com.caucho.jsp.JavaPage
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
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null) {
    response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
    return;
  }
  if (!SecurityCheck.hasPermission(authToken, 54)) {
    response.sendRedirect("../error.jsp?message=\u65e0\u7ba1\u7406\u7528\u6237\u7684\u6743\u9650");
    return;
  }

  int siteid = authToken.getSiteID();
  String userid = ParamUtil.getParameter(request,"userid");
  IRightsManager rightsManager = RightsPeer.getInstance();
  IColumnManager columnManager = ColumnPeer.getInstance();

  List rightsList = rightsManager.getRights(userid);
  List<Map> data = new ArrayList<>();
  Map colAndRight = null;
  for(int ii=0; ii<rightsList.size(); ii++) {
    colAndRight = new HashMap();
    Rights right = (Rights)rightsList.get(ii);
    int rightid = right.getRightID();
    if (rightid<50) {       //\u5fc5\u987b\u662f\u4e0e\u680f\u76ee\u76f8\u5173\u7684\u6743\u9650\u6267\u884c\u5982\u4e0b\u64cd\u4f5c\uff0c\u4e0e\u680f\u76ee\u4e0d\u76f8\u5173\u7684\u6743\u9650\u4e0d\u6267\u884c\u5982\u4e0b\u64cd\u4f5c
      colAndRight.put("rightid", rightid);
      List<Integer> colids = rightsManager.getColumnsByRightidAndUserid(siteid, userid, rightid);
      List<Column> columns = new ArrayList<>();
      for (int jj = 0; jj < colids.size(); jj++) {
        int columnid = colids.get(jj);
        if (columnid>0) {
          Column column = columnManager.getColumn(columnid);
          columns.add(column);
        }
      }
      colAndRight.put("columns", columns);
      data.add(colAndRight);
    }
  }

  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
  String jsondata = gson.toJson(data);
  System.out.println(jsondata);
  JSON.setPrintWriter(response, jsondata, "utf-8");

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/getAllRightForUser.jsp"), -4590203157116993700L, false);
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
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}
