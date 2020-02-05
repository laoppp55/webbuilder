/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.ParamUtil;
import com.bizwink.cms.util.SessionUtil;
import java.util.List;
import com.bizwink.log.LogPeer;
import com.bizwink.log.ILogManager;
import java.util.ArrayList;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.security.*;
import com.bizwink.cms.server.FileProps;

public class _grouploginfo__jsp extends com.caucho.jsp.JavaPage
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
        response.sendRedirect("../login.jsp");
        return;
    }
    if (!SecurityCheck.hasPermission(authToken, 54)) {
        response.sendRedirect("../error.jsp?message=\u65e0\u7ba1\u7406\u7528\u6237\u7684\u6743\u9650");
        return;
    }

    int siteID = ParamUtil.getIntParameter(request, "siteID", 0);
    int searchflag = ParamUtil.getIntParameter(request, "searchflag", -1);

    String where = "";
    String bdate = "";
    String edate = "";
     FileProps fp = new FileProps("com/bizwink/cms/server/config.properties");
        String dbstr = fp.getProperty("main.db.type");
    if (searchflag == 1) {
        where = " and 1=1";
        bdate = ParamUtil.getParameter(request, "bdate");
        if ((bdate != null) && (bdate.length() > 1) && (bdate.indexOf("-") > -1)){
            bdate += " 00:00:00";
            if (dbstr.equals("oracle")) {
                  where = where + " and createdate >= TO_DATE('"+bdate+"', 'YYYY-MM-DD HH24:MI:SS')";
            }else{
                where = where + " and createdate >='" + bdate + "'";
            }
        }
        edate = ParamUtil.getParameter(request, "edate");
        if ((edate != null) && (edate.length() > 1) && (edate.indexOf("-") > -1)){
            edate += " 23:59:59";
            if (dbstr.equals("oracle")) {
                  where = where + " and createdate <= TO_DATE('"+edate+"', 'YYYY-MM-DD HH24:MI:SS')";
            }else{
                where = where + " and createdate <='" + edate + "'";
            }
        }
    }

    IGroupManager groupMgr = GroupPeer.getInstance();
    List groupList = groupMgr.getGroupsLog(siteID, where);

    out.write(_jsp_string1, 0, _jsp_string1.length);
    
    String[][] titlebars = {
            {"LOG\u8bb0\u5f55", ""}
    };
    String[][] operations = {
            {"\u7ec4\u5217\u8868", "groupLogInfo.jsp?siteID=" + siteID},
            {"\u6240\u6709\u7528\u6237\u5217\u8868", "logInfo.jsp?siteID=" + siteID}
    };

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
    
        for (int i = 0; i < groupList.size(); i++) {
            String groupLogInfo = (String) groupList.get(i);
            String[] groupLogs = groupLogInfo.split("@");
            String groupName = StringUtil.gb2iso4View(groupLogs[1]);
            String groupDesc = groupLogs[2];
            groupDesc = groupDesc == null ? "" : StringUtil.gb2iso4View(groupDesc);

            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
    
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((bgcolor));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((groupName));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((groupDesc));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((groupLogs[3]));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((groupLogs[4]));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((groupLogs[5]));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((groupLogs[0]));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((siteID));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((searchflag));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((bdate));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((edate));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    }
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((siteID));
    out.write(_jsp_string16, 0, _jsp_string16.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/groupLogInfo.jsp"), 5497644611270227710L, false);
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

  private final static char []_jsp_string10;
  private final static char []_jsp_string1;
  private final static char []_jsp_string6;
  private final static char []_jsp_string7;
  private final static char []_jsp_string15;
  private final static char []_jsp_string13;
  private final static char []_jsp_string9;
  private final static char []_jsp_string12;
  private final static char []_jsp_string0;
  private final static char []_jsp_string3;
  private final static char []_jsp_string4;
  private final static char []_jsp_string2;
  private final static char []_jsp_string14;
  private final static char []_jsp_string11;
  private final static char []_jsp_string16;
  private final static char []_jsp_string8;
  private final static char []_jsp_string5;
  static {
    _jsp_string10 = "&sid=".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title></title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"../style/global.css\">\r\n    <script language=\"javascript\" src=\"../js/setday.js\"></script>\r\n</head>\r\n\r\n<body>\r\n".toCharArray();
    _jsp_string6 = "\r\n        </b></font></td>\r\n        <td>&nbsp;&nbsp;".toCharArray();
    _jsp_string7 = "\r\n        </td>\r\n        <td align=center><b>".toCharArray();
    _jsp_string15 = "\r\n</table>\r\n<br><br>\r\n<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\" align=\"center\">\r\n    <form action=\"groupLogInfo.jsp\" method=\"post\" name=\"logform\">\r\n        <input type=\"hidden\" name=\"siteID\" value=\"".toCharArray();
    _jsp_string13 = "&edate=".toCharArray();
    _jsp_string9 = "\r\n        </b></td>\r\n        <td align=\"center\"><a href=\"groupUsersLog.jsp?gid=".toCharArray();
    _jsp_string12 = "&bdate=".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string3 = "\r\n\r\n\r\n<table border=0 width=\"100%\">\r\n    <tr>\r\n        <td><font class=line>\u7528\u6237\u7ec4\u5217\u8868</font></td>\r\n    </tr>\r\n</table>\r\n\r\n<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=\"100%\">\r\n    <tr bgcolor=\"#eeeeee\" class=tine>\r\n        <td width=\"20%\" align=center>\u7ec4\u540d</td>\r\n        <td width=\"20%\" align=center>\u63cf\u8ff0</td>\r\n        <td width=\"15%\" align=center>\u521b\u5efa\u6587\u7ae0</td>\r\n        <td width=\"15%\" align=center>\u4fee\u6539\u6587\u7ae0</td>\r\n        <td width=\"15%\" align=center>\u5220\u9664\u6587\u7ae0</td>\r\n        <td width=\"15%\" align=center>\u67e5\u770b\u8be6\u7ec6</td>\r\n    </tr>\r\n    ".toCharArray();
    _jsp_string4 = "\r\n    <tr bgcolor=\"".toCharArray();
    _jsp_string2 = "\r\n".toCharArray();
    _jsp_string14 = "\">\u67e5\u770b\u8be6\u7ec6</a></td>\r\n    </tr>\r\n    ".toCharArray();
    _jsp_string11 = "&searchflag=".toCharArray();
    _jsp_string16 = "\">\r\n        <input type=\"hidden\" name=\"searchflag\" value=\"1\">\r\n        <tr bgcolor=\"#eeeeee\" height=\"30\">\r\n            <td>&nbsp;&nbsp;\u641c\u7d22\uff1a</td>\r\n        </tr>\r\n        <tr>\r\n            <td>&nbsp;</td>\r\n        </tr>\r\n        <tr>\r\n            <td>&nbsp;&nbsp;\u5f00\u59cb\u65e5\u671f\uff1a<input type=\"text\" name=\"bdate\" readonly onfocus=\"setday(this)\" size=10>\r\n                \u2014\u2014\r\n                \u7ed3\u675f\u65e5\u671f\uff1a<input type=\"text\" name=\"edate\" readonly onfocus=\"setday(this)\" size=10>\r\n                <input type=\"submit\" value=\"\u641c\u7d22\">\r\n            </td>\r\n        </tr>\r\n    </form>\r\n</table>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string8 = "\r\n        </b></td>\r\n        <td align=center><b>".toCharArray();
    _jsp_string5 = "\" height=22>\r\n        <td>&nbsp;&nbsp;<font color=red><b>".toCharArray();
  }
}