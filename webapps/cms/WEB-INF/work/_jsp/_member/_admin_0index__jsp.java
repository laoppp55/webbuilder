/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.audit.*;
import com.bizwink.cms.register.*;
import com.bizwink.cms.util.*;
import java.util.Map;
import com.google.gson.Gson;
import com.bizwink.util.JSON;
import com.google.gson.GsonBuilder;
import com.bizwink.vo.VoUser;

public class _admin_0index__jsp extends com.caucho.jsp.JavaPage
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
        response.sendRedirect( "../login.jsp" );
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message","\u65e0\u7cfb\u7edf\u7ba1\u7406\u5458\u7684\u6743\u9650");
        response.sendRedirect("../index.jsp");
        return;
    }

    int resultnum = ParamUtil.getIntParameter(request, "resultnum", 20);
    int pageno = ParamUtil.getIntParameter(request, "pageno", 0);
    String keyword = ParamUtil.getParameter(request, "search");
    int searchflag=ParamUtil.getIntParameter(request,"searchflag",-1);
    int doSearchFlag=ParamUtil.getIntParameter(request,"doSearch",-1);

    IRegisterManager registerMgr = RegisterPeer.getInstance();
    IAuditManager auditMgr = AuditPeer.getInstance();
    List userList = new ArrayList();
    int userCount = 0;
    int startnum = 0 ;
    startnum = (pageno-1)*resultnum;

    if(searchflag == -1){
        userCount = auditMgr.getUsersCount("");
        userList = auditMgr.getUsers(resultnum,startnum,"");
    }else{
        if(searchflag==1) {
            userCount = auditMgr.getUsersCount(keyword);
            userList = auditMgr.getUsers(resultnum,startnum,keyword);
        }
        if(searchflag==2) {
            userCount = auditMgr.getSitenameCount(keyword);
            userList = auditMgr.getSitename(resultnum,startnum,keyword);
        }
        if(searchflag==3) {
            userCount = auditMgr.getNicknameCount(keyword);
            userList = auditMgr.getNickname(resultnum,startnum,keyword);
        }
        if(searchflag==4) {
            userCount = auditMgr.getEmailCount(keyword);
            userList = auditMgr.getEmail(resultnum,startnum,keyword);
        }
    }

    List<VoUser> voUserList = new ArrayList<>();
    if(searchflag==2) {
        for (int i = 0; i < userList.size(); i++) {
            String sitename = "&nbsp;";
            VoUser voUser = new VoUser();
            Register user = (Register) userList.get(i);
            sitename = user.getSiteName();
            int siteID = user.getSiteID();
            if (user != null) {
                User users = auditMgr.getUserOne(siteID);
                if (users != null) {
                    voUser.setUSERID(users.getID());
                    voUser.setNICKNAME(StringUtil.gb2iso4View(users.getNickName()));
                    voUser.setEMAIL(users.getEmail());
                    voUser.setPHONE(users.getPhone());
                    voUser.setSITENAME(sitename);
                    voUserList.add(voUser);
                }
            }
        }
    } else {
        for(int ii=0; ii<userList.size(); ii++) {
            User user = (User)userList.get(ii);
            int siteID = user.getSiteID();
            String userID = user.getUserID();
            String sitename = "&nbsp;";
            VoUser voUser = new VoUser();
            voUser.setUSERID(user.getUserID());
            voUser.setNICKNAME(StringUtil.gb2iso4View(user.getNickName()));
            voUser.setEMAIL(user.getEmail());
            voUser.setPHONE(user.getPhone());
            if (!userID.equalsIgnoreCase("admin") ) {
                Register reg = registerMgr.getSite(siteID);
                if(reg != null) sitename = reg.getSiteName();
            }
            voUser.setSITENAME(sitename);
            voUserList.add(voUser);
        }
    }

    Map data = new HashMap<>();
    data.put("DOSEARCH",doSearchFlag);
    data.put("COUNT",userCount);
    data.put("DATA",voUserList);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //System.out.println(jsondata);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/admin_index.jsp"), 5928737063652567251L, false);
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
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}
