/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.security.*;

public class _removeipinfo__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html;charset=gbk");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null)
	{
		response.sendRedirect( "../login.jsp?url=member/removeRight.jsp" );
		return;
	}
	if (!SecurityCheck.hasPermission(authToken,54))
	{
		request.setAttribute("message","\u65e0\u7cfb\u7edf\u7ba1\u7406\u7684\u6743\u9650");
		response.sendRedirect("editMember.jsp?user="+authToken.getUserID());
		return;
	}

	IFtpSetManager ftpMgr = FtpSetting.getInstance();
	int ID = ParamUtil.getIntParameter(request,"ID",0);
	boolean doDelete = ParamUtil.getBooleanParameter(request,"doDelete");
	FtpInfo ftpInfo = ftpMgr.getFtpInfo(ID);

	if (doDelete)
	{
		ftpMgr.remove(ID);
		response.sendRedirect("siteIPManage.jsp?siteID="+ftpInfo.getSiteid());
		return;
	}

    out.write(_jsp_string1, 0, _jsp_string1.length);
    
	String[][] titlebars = {
		{"\u5220\u9664\u4e3b\u673a", "" }
	};
	String [][] operations = null;

    out.write(_jsp_string2, 0, _jsp_string2.length);
    
if( titlebars != null ) {
    out.println("<table class=line width=100% border=0 cellspacing=0 cellpadding=0>");
    out.println("<tr bgcolor=#003366><td height=2 colspan=2></td></tr>");
    out.println("<tr><td width=50% class=line>");
    int i=0;
    while( titlebars.length > 1 && i < (titlebars.length-1) ) {
	     out.println("<a href=\"javascript:parent.document.location='index.jsp'\" target=_blank>");
	     out.println(titlebars[i][0] +"</a>&gt;");
	     i++;
    }
    if( titlebars.length >= 1 ) {
        out.println(titlebars[i][0]);
    }
    out.println("</td><td width=50% align=right class=line>");
    if (operations != null) {
        i=0;
        while( operations.length > 0 && i < operations.length ) {
            if(operations[i][1] != "")
                out.println("<a href="+ operations[i][1] +">"+ operations[i][0] +"</a>&nbsp;");
            else
                out.println(operations[i][0]);
	    i++;
        }
    }
    out.println("</td></tr>");
    out.println("<tr bgcolor=#003366><td colspan=2 height=2></td>");
    out.println("</tr></table>");
}

    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((ftpInfo.getIp()));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((ID));
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/removeipinfo.jsp"), 8853565255618542579L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("inc/titlebar.jsp"), 1416346678249567651L, false);
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
  private final static char []_jsp_string3;
  private final static char []_jsp_string1;
  private final static char []_jsp_string5;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  static {
    _jsp_string4 = "</b>&nbsp;\u53d1\u5e03\u4e3b\u673a\u4fe1\u606f\u5417\uff1f\r\n<p>\r\n\r\n<form action=\"removeipinfo.jsp\" name=\"deleteForm\">\r\n<input type=\"hidden\" name=\"doDelete\" value=\"true\">\r\n<input type=\"hidden\" name=\"ID\" value=\"".toCharArray();
    _jsp_string3 = "\r\n\r\n<p>\r\n\u786e\u4fe1\u8981\u5220\u9664&nbsp;<b>".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"../style/global.css\">\r\n</head>\r\n".toCharArray();
    _jsp_string5 = "\">\r\n<input type=\"submit\" value=\" \u786e\u5b9a \">&nbsp;&nbsp;&nbsp;\r\n<input type=\"button\" value=\" \u53d6\u6d88 \" onclick=\"history.go(-1);\">\r\n</form>\r\n\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string2 = "\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}