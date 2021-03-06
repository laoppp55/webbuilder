/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._publish;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;
import com.bizwink.util.JSON;

public class _publishnew__jsp extends com.caucho.jsp.JavaPage
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
    
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int siteid = authToken.getSiteID();
    int sitetype = authToken.getSitetype();
    int samsiteid = authToken.getSamSiteid();
    int imgflag = authToken.getImgSaveFlag();
    int option = authToken.getPublishFlag();

    int retcode = 1;
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int source = ParamUtil.getIntParameter(request, "source", 0);
    boolean isown = ParamUtil.getBooleanParameter(request, "isown");
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String appPath = application.getRealPath("/");

    IPublishManager publishMgr = PublishPeer.getInstance();
    IColumnManager columnMgr = ColumnPeer.getInstance();

    //\u4ece\u6587\u7ae0\u53d1\u5e03\u6a21\u5757\u63d0\u4ea4\u7684\u6587\u7ae0\u53d1\u5e03
    if (source == 0) {
        String selArticleIds = ParamUtil.getParameter(request,"articles");
        String[] articleids = selArticleIds.split(",");
        for (int i = 0; i < articleids.length; i++) {
            int articleID = Integer.parseInt(articleids[i]);
            isown = ParamUtil.getBooleanParameter(request, "isown" + articleID);
            if (articleID > 0) {
                boolean isTemplate = ParamUtil.getBooleanParameter(request, "template" + articleID);
                if (isTemplate) {    //\u6587\u7ae0\u53d1\u5e03\u9875\u9762\u63d0\u4ea4 /publish/articles.jsp    \u53d1\u5e03\u5931\u8d25\u9875\u9762\u63d0\u4ea4 /publish/publishfailed.jsp
                    columnID = ParamUtil.getIntParameter(request, "column" + articleID, 0);
                    int type = ParamUtil.getIntParameter(request, "type" + articleID, 0);
                    if (type == 1) {                           //1--\u53d1\u5e03\u6587\u7ae0\uff0c\u5982\u679c\u680f\u76ee\u8bbe\u7f6e\u591a\u6a21\u677f\u53d1\u5e03\u6807\u5fd7\uff0c\u6709\u4e00\u4e2a\u6587\u7ae0\u6a21\u677f\u53d1\u5e03\u51e0\u5957\u4fe1\u606f
                        retcode = publishMgr.CreateArticlePage(articleID,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,0);
                    } else if (type == 0 || type == 4) {        //0--\u53d1\u5e03PC\u680f\u76ee\u9875\u9762  4--\u53d1\u5e03\u624b\u673a\u680f\u76ee\u6a21\u677f
                        retcode = publishMgr.CreateColPage(columnID, siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, option, articleID);
                        //\u5982\u679c\u9700\u8981\u53d1\u5e03RSS\uff0c\u53d1\u5e03RSS
                        Column column = columnMgr.getColumn(columnID);
                        if (column.getRss() == 1) {
                            RssMaker rm = new RssMaker();
                            rm.createRss(siteid, columnID, column, appPath, username, sitename);
                        }
                    } else if (type == 2 || type == 5) {        //2--\u53d1\u5e03PC\u9996\u9875\u6a21\u677f   5--\u53d1\u5e03\u624b\u673a\u9996\u9875\u6a21\u677f
                        retcode = publishMgr.createHomePage(siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, option, articleID);
                    } else if (type == 3 || type == 6) {        //3--\u53d1\u5e03PC\u4e13\u9898\u9875\u9762\uff0c6--\u667a\u80fd\u624b\u673a\u4e13\u9898\u9875\u9762
                        retcode = publishMgr.CreateColPage(columnID, siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, option, articleID);
                    }
                } else {       //\u53d1\u5e03\u6587\u7ae0
                    columnID = ParamUtil.getIntParameter(request, "column" + articleID, 0);
                    retcode = publishMgr.CreateArticlePage(articleID,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,0);
                }
            }
        }
    } else if (source == 2) {  //\u6a21\u677f\u7ba1\u7406\u9875\u9762\u63d0\u4ea4 /template/templates.jsp
        int templateID = ParamUtil.getIntParameter(request, "template", 0);
        int modelType = ParamUtil.getIntParameter(request, "modelType", -1);
        columnID = ParamUtil.getIntParameter(request, "column", 0);

        if (modelType == 2 || modelType == 5) {    //\u53d1\u5e03\u9996\u9875\u6216\u8005\u624b\u673a\u9996\u9875\u6a21\u677f
            retcode = publishMgr.createHomePage(siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, templateID);
        } else if (modelType == 0 || modelType == 4) {
            retcode = publishMgr.CreateColPage(columnID, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, templateID);
            //\u5982\u679c\u9700\u8981\u53d1\u5e03RSS\uff0c\u53d1\u5e03RSS
            Column column = columnMgr.getColumn(columnID);
            if (column.getRss() == 1) {
                RssMaker rm = new RssMaker();
                rm.createRss(siteid, columnID, column, appPath, username, sitename);
            }
        } else if (modelType == 3 || modelType == 6) {      //\u53d1\u5e03\u4e13\u9898\u6a21\u677f
            retcode = publishMgr.CreateColPage(columnID, siteid,sitetype,samsiteid, appPath, sitename, username, imgflag, option, templateID);

            //\u5982\u679c\u9700\u8981\u53d1\u5e03RSS\uff0c\u53d1\u5e03RSS
            Column column = columnMgr.getColumn(columnID);
            if (column.getRss() == 1) {
                RssMaker rm = new RssMaker();
                rm.createRss(siteid, columnID, column, appPath, username, sitename);
            }
        } else if(modelType >= 10) {
            retcode = publishMgr.CreateProgramPage(columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,templateID);
        }
    } else if (source == 3) {    //\u6587\u7ae0\u7ba1\u7406\u9875\u9762\u63d0\u4ea4\u7684\u6587\u7ae0\u53d1\u5e03\u64cd\u4f5c
        int articleID = ParamUtil.getIntParameter(request, "article", 0);
        retcode = publishMgr.CreateArticlePage(articleID,columnID,siteid,sitetype,samsiteid,appPath,sitename,username,imgflag,option,isown,1);
    }

    String jsonData = null;
    if (retcode == 0)
        jsonData =  "{\"result\":\"true\"}";
    else
        jsonData = "{\"result\":\"false\"}";
    JSON.setPrintWriter(response, jsonData,"utf-8");

    out.write(_jsp_string1, 0, _jsp_string1.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("publish/publishnew.jsp"), 4838588253937971072L, false);
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

  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  static {
    _jsp_string1 = "\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
