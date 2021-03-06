/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._template;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;

public class _listfile__jsp extends com.caucho.jsp.JavaPage
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

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String sitename = authToken.getSitename();
    String path = ParamUtil.getParameter(request, "path");
    String filename = ParamUtil.getParameter(request, "filename");
    String appRoot = application.getRealPath("/");

    String filePath = "";
    if (path != null)
        filePath = appRoot + "sites" + File.separator + sitename + File.separator + "_templates" + File.separator + StringUtil.replace(path, "/", File.separator);
    else
        filePath = appRoot + "sites" + File.separator + sitename + File.separator + "_templates" + File.separator;

    if (filename != null && filename.length() > 0)    //\u5220\u9664
    {
        File file = new File(filePath + filename);
        if (file.exists()) file.delete();
        response.sendRedirect("listfile.jsp?column=" + columnID + "&path=" + path);
        return;
    }

    List fList = FileUtil.getFileListInPath(filePath);
    Column column = null;
    String CName = "";
    IColumnManager columnManager = ColumnPeer.getInstance();
    if (columnID>0) {
        column = columnManager.getColumn(columnID);
        CName = StringUtil.gb2iso4View(column.getCName());
    } else {
        CName = "\u7a0b\u5e8f\u6a21\u677f";
    }

    if (path == null)
        path = "";

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((columnID));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((sitename));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((path));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((columnID));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((path));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((CName));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    
        for (int i = 0; i < fList.size(); i++) {
            filename = (String) fList.get(i);
            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
            out.println("<tr bgcolor=" + bgcolor + " height=25 onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "'\">");
            out.println("<td width='10%' align=center><input type=radio name=selectedLink onclick=javascript:selectthis(this) value=" + filename + "></td>");
            out.println("<td width='70%'>" + filename + "</td>");
            if (!filename.toLowerCase().endsWith(".jsp"))
                out.println("<td width='10%' align=center><a href=\"javascript:Preview('" + filename + "');\"><img src=../images/button/view.gif border=0></a></td>");
            else
                out.println("<td width='10%' align=center>&nbsp;</td>");
            out.println("<td width='10%' align=center><a href=\"javascript:Delete('" + filename + "');\"><img src=../images/button/del.gif border=0></a></td>");
            out.println("</tr>");
        }
    
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((filePath));
    out.write(_jsp_string9, 0, _jsp_string9.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("template/listfile.jsp"), 3824731035430763020L, false);
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
  private final static char []_jsp_string3;
  private final static char []_jsp_string7;
  private final static char []_jsp_string8;
  private final static char []_jsp_string5;
  private final static char []_jsp_string2;
  private final static char []_jsp_string9;
  private final static char []_jsp_string6;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  static {
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title></title>\r\n    <meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet type=text/css href=\"../style/global.css\">\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.12.4.js\"></script>\r\n\r\n    <SCRIPT LANGUAGE=JavaScript>\r\n        function subOK() {\r\n            var strVal = selectModelFile.filename.value;\r\n            if (strVal == \"\") {\r\n                alert(\"\u6a21\u677f\u6587\u4ef6\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u9009\u62e9\u6a21\u677f\u6587\u4ef6\")\r\n                return false;\r\n            }\r\n            var returnvalue = selectModelFile.filepath.value + strVal;\r\n\r\n            htmlobj=$.ajax({\r\n                url:\"/webbuilder/template/getTemplateContent.jsp?path=\" + returnvalue + \"&column=".toCharArray();
    _jsp_string3 = "/_templates/".toCharArray();
    _jsp_string7 = "\r\n        </font></td>\r\n    </tr>\r\n    <tr class=itm bgcolor='#dddddd' height=20>\r\n        <td align=center>\u9009\u4e2d</td>\r\n        <td align=center>\u6a21\u677f\u6587\u4ef6\u540d</td>\r\n        <td align=center>\u9884\u89c8</td>\r\n        <td align=center>\u5220\u9664</td>\r\n    </tr>\r\n    ".toCharArray();
    _jsp_string8 = "\r\n</table>\r\n\r\n<table cellpadding=\"1\" cellspacing=\"2\" border=\"0\">\r\n    <form name=\"selectModelFile\">\r\n        <tr>\r\n            <td>\r\n                <span style=\"font-size:9pt\">\u9009\u4e2d\u7684\uff1a</span>\r\n                <input type=\"hidden\" name=\"filepath\" value=\"".toCharArray();
    _jsp_string5 = "&path=".toCharArray();
    _jsp_string2 = "\",\r\n                dataType:'html',\r\n                async:false,\r\n                success:function(data){\r\n                    var oEditor = window.parent.opener.top.SetHTMLToEditor(data);\r\n                    top.close();\r\n                }\r\n            });\r\n        }\r\n\r\n        function selectthis(para)\r\n        {\r\n            selectModelFile.filename.value = para.value;\r\n        }\r\n\r\n        function Preview(filename)\r\n        {\r\n            window.open(\"/webbuilder/sites/".toCharArray();
    _jsp_string9 = "\">\r\n                <input name=\"filename\" size=\"40\" readonly class=tine>\r\n            </td>\r\n        </tr>\r\n    </form>\r\n</table>\r\n<br>\r\n<input type=\"button\" class=tine value=\"\u786e  \u5b9a\" onclick=\"subOK();\">&nbsp;&nbsp;&nbsp;&nbsp;\r\n<input type=\"button\" class=tine ONCLICK=\"top.close();\" value=\"\u8fd4  \u56de\">\r\n</BODY>\r\n</html>".toCharArray();
    _jsp_string6 = "&filename=\" + filename;\r\n        }\r\n    </script>\r\n</head>\r\n<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>\r\n<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>\r\n    <tr>\r\n        <td colspan=5> \u5f53\u524d\u6240\u5728\u680f\u76ee--->><font color=red>".toCharArray();
    _jsp_string4 = "\" + filename, \"\", \"\");\r\n        }\r\n\r\n        function Delete(filename)\r\n        {\r\n            if (confirm(\"\u771f\u7684\u8981\u5220\u9664\u8be5\u6a21\u677f\u5417\uff1f\"))\r\n                window.location = \"listfile.jsp?column=".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
