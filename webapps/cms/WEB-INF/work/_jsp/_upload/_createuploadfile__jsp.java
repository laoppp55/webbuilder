/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._upload;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.security.*;
import com.bizwink.upload.*;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.util.*;

public class _createuploadfile__jsp extends com.caucho.jsp.JavaPage
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
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    int columnID  = ParamUtil.getIntParameter(request, "column", 0);
    int articleid = ParamUtil.getIntParameter(request, "articleid", 0);
    int year = ParamUtil.getIntParameter(request, "year", 0);
    int month = ParamUtil.getIntParameter(request, "month", 0);
    int day = ParamUtil.getIntParameter(request, "day", 0);
    int hour = ParamUtil.getIntParameter(request, "hour", 0);
    int minute = ParamUtil.getIntParameter(request, "minute", 0);
    int doclevel = ParamUtil.getIntParameter(request, "doclevel", 0);
    String maintitle = ParamUtil.getParameter(request,"maintitle");
    String status = ParamUtil.getParameter(request, "status");
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    int arttypeval = ParamUtil.getIntParameter(request, "arttype", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");


    System.out.println("articleid==" + articleid);
    System.out.println("status==" + status);
    System.out.println("range==" + range);
    System.out.println("currentPage==" + currentPage);
    System.out.println("flag==" + flag);
    System.out.println("ascdesc==" + ascdesc);
    System.out.println("doSearchFlag==" + doSearchFlag);

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String fileDir = column.getDirName();
    String CName = StringUtil.gb2iso4View(column.getCName());
    int isAudited = column.getIsAudited();
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isPosition = column.getIsPosition();                                   //\u662f\u5426\u5305\u542b\u5730\u7406\u4f4d\u7f6e\u4fe1\u606f

    String baseDir = application.getRealPath("/");
    String tempDir = StringUtil.replace(fileDir, "/", File.separator);
    String dir = baseDir + "sites" + File.separator + sitename;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal_today = Calendar.getInstance();

    int m_day = cal_today.get(Calendar.DAY_OF_MONTH);
    int m_month = cal_today.get(Calendar.MONTH) + 1;
    int m_hour = cal_today.get(Calendar.HOUR_OF_DAY);
    int m_minute = cal_today.get(Calendar.MINUTE);
    int m_year = cal_today.get(Calendar.YEAR);
    String d = formatter.format(cal_today.getTime());

    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    out.println("<script>");
    if (articleid>0) {
        out.println("window.opener.articlesInfoList(" + columnID + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + "," + "null" + "," + "null" + "," + "null" + "," + arttypeval + ");");
        out.println("alert('" + "\u6587\u4ef6\u4e0a\u4f20\u6210\u529f\uff01\uff01\uff01" +"');");
        //out.println("window.opener.createUploadFileListData(" + columnID + "," + articleid + "," + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ");");
        out.println("window.close();");
    }
    out.println("</script>");

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((columnID));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    
    String[][] titlebars = {
            { "\u6587\u4ef6\u4e0a\u4f20", "index.jsp" },
            { CName, "" }
    };
    String[][] operations = {
    };

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
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
    out.print((request.getContextPath()));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((siteid));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((sitename));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((columnID));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((isDefine));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((MultipartFormHandle.FORWARDNAME));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((baseDir));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((tempDir));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((sitename));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((siteid));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((username));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((authToken.getPublishFlag()));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((range));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    out.print((currentPage));
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((flag));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((ascdesc));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    out.print((arttypeval));
    out.write(_jsp_string20, 0, _jsp_string20.length);
    out.print((doSearchFlag));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    out.print((m_year));
    out.write(_jsp_string22, 0, _jsp_string22.length);
    for(int i=1; i<13; i++){
    out.write(_jsp_string23, 0, _jsp_string23.length);
    out.print((i));
    out.write(' ');
    out.print(((m_month==i)?"selected":""));
    out.write('>');
    out.print((i));
    out.write(_jsp_string24, 0, _jsp_string24.length);
    }
    out.write(_jsp_string25, 0, _jsp_string25.length);
    for(int i=1; i<32; i++){
    out.write(_jsp_string23, 0, _jsp_string23.length);
    out.print((i));
    out.write(' ');
    out.print(((m_day==i)?"selected":""));
    out.write('>');
    out.print((i));
    out.write(_jsp_string24, 0, _jsp_string24.length);
    }
    out.write(_jsp_string26, 0, _jsp_string26.length);
    for(int i=1; i<24; i++){
    out.write(_jsp_string23, 0, _jsp_string23.length);
    out.print((i));
    out.write(' ');
    out.print(((m_hour==i)?"selected":""));
    out.write('>');
    out.print((i));
    out.write(_jsp_string24, 0, _jsp_string24.length);
    }
    out.write(_jsp_string27, 0, _jsp_string27.length);
    for(int i=1; i<61; i++){
    out.write(_jsp_string23, 0, _jsp_string23.length);
    out.print((i));
    out.write(' ');
    out.print(((m_minute==i)?"selected":""));
    out.write('>');
    out.print((i));
    out.write(_jsp_string24, 0, _jsp_string24.length);
    }
    out.write(_jsp_string28, 0, _jsp_string28.length);
    out.print((d));
    out.write(_jsp_string29, 0, _jsp_string29.length);
    if(isAudited==1){
    out.write(_jsp_string30, 0, _jsp_string30.length);
    }else{
    out.write(_jsp_string31, 0, _jsp_string31.length);
    }
    out.write(_jsp_string32, 0, _jsp_string32.length);
    if (isDefine == 1) {
    out.write(_jsp_string33, 0, _jsp_string33.length);
    if ((isProduct == 0 && isPosition==0) || (isProduct == 1 && isPosition==1)) {
    out.write(_jsp_string34, 0, _jsp_string34.length);
    } else { 
    out.write(_jsp_string35, 0, _jsp_string35.length);
    }
    out.write(_jsp_string36, 0, _jsp_string36.length);
    out.print((extendMgr.getExtendAttrForArticle(columnID, 0)));
    out.write(_jsp_string32, 0, _jsp_string32.length);
    }
    out.write(_jsp_string37, 0, _jsp_string37.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("upload/createuploadfile.jsp"), 1191139850392239582L, false);
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

  private final static char []_jsp_string27;
  private final static char []_jsp_string37;
  private final static char []_jsp_string20;
  private final static char []_jsp_string29;
  private final static char []_jsp_string21;
  private final static char []_jsp_string13;
  private final static char []_jsp_string11;
  private final static char []_jsp_string23;
  private final static char []_jsp_string14;
  private final static char []_jsp_string30;
  private final static char []_jsp_string9;
  private final static char []_jsp_string18;
  private final static char []_jsp_string25;
  private final static char []_jsp_string33;
  private final static char []_jsp_string36;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  private final static char []_jsp_string5;
  private final static char []_jsp_string17;
  private final static char []_jsp_string15;
  private final static char []_jsp_string8;
  private final static char []_jsp_string22;
  private final static char []_jsp_string10;
  private final static char []_jsp_string3;
  private final static char []_jsp_string16;
  private final static char []_jsp_string4;
  private final static char []_jsp_string19;
  private final static char []_jsp_string31;
  private final static char []_jsp_string34;
  private final static char []_jsp_string26;
  private final static char []_jsp_string32;
  private final static char []_jsp_string7;
  private final static char []_jsp_string24;
  private final static char []_jsp_string0;
  private final static char []_jsp_string35;
  private final static char []_jsp_string28;
  private final static char []_jsp_string6;
  private final static char []_jsp_string12;
  static {
    _jsp_string27 = "\r\n                </select>\u65f6\r\n                <select name=minute size=1 class=tine>\r\n                    ".toCharArray();
    _jsp_string37 = "\r\n        <tr bgcolor=\"#eeeeee\">\r\n            <td height=\"36\">\u7b80\u4f53\u6587\u4ef6\uff1a<input type=file id=\"sfilename\" size=40 name=\"sfilename\" value=\"browse\"></td>\r\n            <td height=\"36\">\u4e0b\u8f7d\uff1a<input type=file id=\"downsfilename\" size=40 name=\"downsfilename\" value=\"browse\"></td>\r\n        </tr>\r\n        <tr>\r\n            <td height=\"36\">\u7e41\u4f53\u6587\u4ef6\uff1a<input type=file id=\"tfilename\" size=40 name=\"tfilename\" value=\"browse\"></td>\r\n            <td height=\"36\">\u4e0b\u8f7d\uff1a<input type=file id=\"downtfilename\" size=40 name=\"downtfilename\" value=\"browse\"></td>\r\n        </tr>\r\n        <tr bgcolor=\"#eeeeee\">\r\n            <td colspan=\"2\" align=\"center\" height=60>\r\n                <input type=\"submit\" value=\"  \u4e0a\u4f20  \" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;\r\n                <input type=\"button\" value=\"  \u8fd4\u56de  \" class=tine onclick=\"window.close();\">\r\n            </td>\r\n        </tr>\r\n    </table>\r\n</form>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string20 = "\">\r\n    <input type=hidden name=searchFlag value=\"".toCharArray();
    _jsp_string29 = "')\" onclick=\"setLastMousePosition(event)\" tabindex=\"3\"><img src=\"../images/date_picker.gif\" border=\"0\" width=\"34\" height=\"21\" align=\"absmiddle\" border=0></a>\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td colspan=\"2\">\r\n                <b><font color=\"#FF0000\">\u4e3b\u6743\u91cd</font></b>\uff1a<input name=\"doclevel\" value=\"0\" size=4>&nbsp;&nbsp;&nbsp;\r\n                <b><font color=\"#FF0000\">\u6392\u5e8f</font></b>\uff1a<input class=tine name=sortid size=5 value=0>&nbsp;&nbsp;&nbsp;\r\n                <b><font color=\"#FF0000\">\u4f7f\u7528</font></b>\uff1a<input type=radio name=\"using\" value=\"0\">\u5426<input type=radio  checked name=\"using\" value=\"1\">\u662f&nbsp;&nbsp;&nbsp;\r\n                <b><font color=\"#FF0000\">\u8ba2\u9605</font></b>\uff1a<input type=radio name=\"subscriber\" value=\"0\">\u5426<input type=radio  checked name=\"subscriber\" value=\"1\">\u662f&nbsp;&nbsp;&nbsp;\r\n                <b><font color=\"#FF0000\">\u5ba1\u6838</font></b>\uff1a&nbsp;".toCharArray();
    _jsp_string21 = "\">\r\n    <table border=\"0\">\r\n        <tr>\r\n            <td>\u5f55\u5165\u4e0a\u4f20\u6587\u4ef6\u7684\u4fe1\u606f</td>\r\n        </tr>\r\n    </table>\r\n    <table border=1 borderColorDark=\"#ffffe0c\" borderColorLight=\"#5e5e00\" cellPadding=0 cellSpacing=0 width=\"100%\">\r\n        <tr bgcolor=\"#eeeeee\">\r\n            <td width=\"40%\">\u6807\u9898\uff1a<input class=tine name=maintitle size=30></td>\r\n            <td width=\"60%\">\u526f\u6807\u9898\uff1a<input class=tine name=vicetitle size=30></td>\r\n        </tr>\r\n        <tr>\r\n            <td>\u6458\u8981\uff1a<input class=tine name=summary size=30></td>\r\n            <td>\u5173\u952e\u5b57\uff1a<input class=tine name=keyword size=30>(\u4ee5;\u9694\u5f00) </td>\r\n        </tr>\r\n        <tr bgcolor=\"#eeeeee\">\r\n            <td>\u6765\u6e90\uff1a<input class=tine name=source size=30></td>\r\n            <td>\u53d1\u5e03\u65e5\u671f\uff1a\r\n                <input  class=tine type=text size=3 maxlength=4 name=year value=".toCharArray();
    _jsp_string13 = "\">\r\n    <input type=\"hidden\" name=\"username\" value=\"".toCharArray();
    _jsp_string11 = "\">\r\n    <input type=\"hidden\" name=\"sitename\" value=\"".toCharArray();
    _jsp_string23 = "<option value=".toCharArray();
    _jsp_string14 = "\">\r\n    <input type=\"hidden\" name=\"fromflag\" value=\"file\">\r\n    <input type=\"hidden\" name=\"tcflag\" value=\"".toCharArray();
    _jsp_string30 = "\u9700\u8981".toCharArray();
    _jsp_string9 = "\" value=\"/upload/createuploadfile.jsp\">\r\n    <input type=\"hidden\" name=\"status\" value=\"save\">\r\n    <input type=\"hidden\" name=\"baseDir\" value=\"".toCharArray();
    _jsp_string18 = "\">\r\n    <input type=hidden name=ascdesc value=\"".toCharArray();
    _jsp_string25 = "\r\n                </select>\u6708\r\n                <select name=day size=1 class=tine>\r\n                    ".toCharArray();
    _jsp_string33 = "\r\n        ".toCharArray();
    _jsp_string36 = "\r\n            <td class=line colspan=\"2\">".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title></title>\r\n    <link REL=\"stylesheet\" TYPE=\"text/css\" HREF=\"../style/global.css\">\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n    <script language=JavaScript1.2 src=\"../js/functions.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.12.4.js\"></script>\r\n    <SCRIPT LANGUAGE=JavaScript>\r\n        function opencalendar(baseURL){\r\n            openPopup(baseURL, \"\u5c0f\u65e5\u5386\", 350, 300, \"width=250,height=125,toolbar=no,status=no,directories=no,menubar=no,resizable=yes,scrollable=no\", true);\r\n        }\r\n\r\n        function upload_attrpic_onclick(attrname){\r\n            window.open(\"upload.jsp?column=".toCharArray();
    _jsp_string2 = "&attr=\" + attrname, \"\", 'width=400,height=400,left=200,top=200');\r\n        }\r\n\r\n        function check(){\r\n            if (uploadfile.maintitle.value == \"\") {\r\n                alert(\"\u6587\u4ef6\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01\");\r\n                return false;\r\n            } else if (uploadfile.sfilename.value == \"\") {\r\n                alert(\"\u8bf7\u9009\u62e9\u6587\u4ef6\uff01\");\r\n                return false;\r\n            } else {\r\n                return true;\r\n            }\r\n        }\r\n    </SCRIPT>\r\n</head>\r\n\r\n<body>\r\n".toCharArray();
    _jsp_string5 = "&sitename=".toCharArray();
    _jsp_string17 = "\">\r\n    <input type=hidden name=flag value=\"".toCharArray();
    _jsp_string15 = "\">\r\n    <input type=hidden name=range value=\"".toCharArray();
    _jsp_string8 = ">\r\n    <input type=\"hidden\" name=\"".toCharArray();
    _jsp_string22 = ">\u5e74\r\n                <select name=month size=1 class=tine>\r\n                    ".toCharArray();
    _jsp_string10 = "\">\r\n    <input type=\"hidden\" name=\"fileDir\" value=\"".toCharArray();
    _jsp_string3 = "\r\n\r\n<form method=\"post\" action=\"".toCharArray();
    _jsp_string16 = "\">\r\n    <input type=hidden name=start value=\"".toCharArray();
    _jsp_string4 = "/multipartformserv?siteid=".toCharArray();
    _jsp_string19 = "\">\r\n    <input type=hidden name=arttype value=\"".toCharArray();
    _jsp_string31 = "\u4e0d\u9700\u8981".toCharArray();
    _jsp_string34 = "\r\n        <tr bgcolor=\"#eeeeee\">\r\n                ".toCharArray();
    _jsp_string26 = "\r\n                </select>\u65e5\r\n                <select name=hour size=1 class=tine>\r\n                    ".toCharArray();
    _jsp_string32 = "\r\n            </td>\r\n        </tr>\r\n        ".toCharArray();
    _jsp_string7 = ">\r\n    <input type=\"hidden\" name=isDefine value=".toCharArray();
    _jsp_string24 = "</option>".toCharArray();
    _jsp_string0 = "\r\n".toCharArray();
    _jsp_string35 = "\r\n        <tr bgcolor=\"#ffffff\">\r\n            ".toCharArray();
    _jsp_string28 = "\r\n                </select>\u5206\r\n                <input type=hidden name=sd value=\"\">\r\n                <a href=\"JavaScript:opencalendar('calendar.jsp?form=uploadfile&ip=sd&d=".toCharArray();
    _jsp_string6 = "\" name=uploadfile enctype=\"multipart/form-data\" onsubmit=\"javascript:return check();\">\r\n    <input type=\"hidden\" name=column value=".toCharArray();
    _jsp_string12 = "\">\r\n    <input type=\"hidden\" name=\"siteid\" value=\"".toCharArray();
  }
}
