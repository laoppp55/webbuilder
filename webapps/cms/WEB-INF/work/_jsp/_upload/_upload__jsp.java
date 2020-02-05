/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._upload;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.pic.IPicManager;
import com.bizwink.cms.pic.Pic;
import com.bizwink.cms.pic.PicPeer;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.util.ParamUtil;
import com.bizwink.cms.security.Auth;
import com.bizwink.upload.RandomStrg;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import com.bizwink.upload.MultipartFormHandle;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.sitesetting.*;

public class _upload__jsp extends com.caucho.jsp.JavaPage
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

    String dir = "";
    String tempDir = "";
    int columnID = 0;
    String baseDir = application.getRealPath("/");
    String attrname = request.getParameter("attr");
    String filename = request.getParameter("picname1");
    String status = request.getParameter("status");
    columnID = ParamUtil.getIntParameter(request, "column", 0);
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String fileDir = column.getDirName();
    String mtitle_picwh = column.getTitlepic();
    String vtitle_picwh = column.getVtitlepic();
    String source_picwh = column.getSourcepic();
    String author_picwh = column.getAuthorpic();
    String special_picwh = column.getSpecialpic();
    String big_picwh = column.getProductpic();
    String small_picwh = column.getProductsmallpic();

    ISiteInfoManager siteInfoManager = SiteInfoPeer.getInstance();
    SiteInfo siteInfo = siteInfoManager.getSiteInfo(siteid);
    if (mtitle_picwh==null) mtitle_picwh = siteInfo.getTitlepic();
    if (vtitle_picwh==null) vtitle_picwh = siteInfo.getVtitlepic();
    if (source_picwh==null) source_picwh = siteInfo.getSourcepic();
    if (author_picwh==null) author_picwh = siteInfo.getAuthorpic();
    if (special_picwh==null) special_picwh = siteInfo.getSpecialpic();
    if (big_picwh==null) big_picwh = siteInfo.getProductpic();
    if (small_picwh==null) small_picwh = siteInfo.getProductsmallpic();

    int hostID = 0;
    if (status == null) {
        RandomStrg rstr = new RandomStrg();
        rstr.setCharset("a-z0-9");
        rstr.setLength(8);
        rstr.generateRandomObject();
        filename = "pic" + rstr.getRandom();

        tempDir = StringUtil.replace(fileDir, "/", File.separator);
        dir = baseDir + "sites" + File.separator + sitename;
    } else {
        tempDir = "/webbuilder/sites/" + sitename + fileDir + "images/";
    }

    List picList = new ArrayList();
    //\u83b7\u5f97\u6240\u6709\u53d1\u5e03\u4e3b\u673a\u5217\u8868
    IFtpSetManager ftpsetMgr = FtpSetting.getInstance();
    List siteList = ftpsetMgr.getOtherFtpInfos(siteid);
    if (status != null) {
        if (attrname.equalsIgnoreCase("mt")) {
            filename = request.getParameter("title_pic");
        } else if (attrname.equalsIgnoreCase("vt")) {
            filename = request.getParameter("vtitle_pic");
        } else if (attrname.equalsIgnoreCase("au")) {
            filename = request.getParameter("author_pic");
        } else if (attrname.equalsIgnoreCase("sr")) {
            filename = request.getParameter("souecr_pic");
        } else if (attrname.equalsIgnoreCase("pic")) {
            filename = request.getParameter("prod_small_pic");
        } else if (attrname.equalsIgnoreCase("bigpic")) {
            filename = request.getParameter("prod_large_pic");
        } else if (attrname.equalsIgnoreCase("apic")) {
            filename = request.getParameter("special_pic");
        }
        String notes = request.getParameter("notes");
        if (notes != null) {
            notes = new String(notes.getBytes(), "utf-8");
            notes = StringUtil.iso2gbindb(notes);
        }

        if (hostID > 0) {
            FtpInfo ftpInfo = ftpsetMgr.getFtpInfo(hostID);
            String docPath = ftpInfo.getDocpath();
            if (docPath.length() > 0 && !docPath.startsWith("/"))
                docPath = "/" + docPath;
            if (docPath.length() > 0 && docPath.endsWith("/"))
                docPath = docPath.substring(0, docPath.length() - 1);
            filename = "http://" + ftpInfo.getSiteName() + docPath + fileDir + "images/" + filename;
            Pic pic = new Pic();
            pic.setSiteid(siteid);
            pic.setColumnid(columnID);
            pic.setWidth(0);
            pic.setHeight(0);
            pic.setPicsize(0);
            pic.setImgurl(filename);
            pic.setPicname(filename);
            pic.setNotes(notes);
            picList.add(pic);
        } else {
            Pic pic = new Pic();
            pic.setSiteid(siteid);
            pic.setColumnid(columnID);
            pic.setWidth(0);
            pic.setHeight(0);
            pic.setPicsize(0);
            pic.setImgurl(tempDir + filename);

            pic.setPicname(filename);
            pic.setNotes(notes);
            picList.add(pic);
        }
    }
    //\u56fe\u7247\u4fe1\u606f\u5165\u5e93

    IPicManager picMgr = PicPeer.getInstance();
    picMgr.createPic(picList);

    out.write(_jsp_string1, 0, _jsp_string1.length);
    if (status != null){
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((attrname));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((filename));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((attrname));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    }
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((request.getContextPath()));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((siteid));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((sitename));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((MultipartFormHandle.FORWARDNAME));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((filename));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((columnID));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((baseDir));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((tempDir));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((sitename));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((siteid));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    out.print((username));
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((attrname));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((authToken.getPublishFlag()));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    if (attrname.equalsIgnoreCase("mt")) {
    out.write(_jsp_string20, 0, _jsp_string20.length);
    out.print(((mtitle_picwh==null)?"":mtitle_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    } else if (attrname.equalsIgnoreCase("vt")) {
    out.write(_jsp_string22, 0, _jsp_string22.length);
    out.print(((vtitle_picwh==null)?"":vtitle_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    } else if (attrname.equalsIgnoreCase("au")) {
    out.write(_jsp_string23, 0, _jsp_string23.length);
    out.print(((author_picwh==null)?"":author_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    } else if (attrname.equalsIgnoreCase("sr")) {
    out.write(_jsp_string24, 0, _jsp_string24.length);
    out.print(((source_picwh==null)?"":source_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    } else if (attrname.equalsIgnoreCase("pic")) {
    out.write(_jsp_string25, 0, _jsp_string25.length);
    out.print(((small_picwh==null)?"":small_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    } else if (attrname.equalsIgnoreCase("bigpic")) {
    out.write(_jsp_string26, 0, _jsp_string26.length);
    out.print(((big_picwh==null)?"":big_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    } else if (attrname.equalsIgnoreCase("apic")) {
    out.write(_jsp_string27, 0, _jsp_string27.length);
    out.print(((special_picwh==null)?"":special_picwh));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string28, 0, _jsp_string28.length);
    
                    for (int i = 0; i < siteList.size(); i++) {
                        FtpInfo ftpinfo = (FtpInfo) siteList.get(i);
                        if (ftpinfo.getStatus() == 0) {
                            String siteName = ftpinfo.getSiteName();
                            if (siteName == null || siteName.length() == 0) siteName = sitename;
                
    out.write(_jsp_string29, 0, _jsp_string29.length);
    out.print((ftpinfo.getID()));
    out.write(_jsp_string30, 0, _jsp_string30.length);
    out.print((siteName));
    out.write(_jsp_string31, 0, _jsp_string31.length);
    
                        }
                    }
                
    out.write(_jsp_string32, 0, _jsp_string32.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("upload/upload.jsp"), -4740978523030721969L, false);
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

  private final static char []_jsp_string29;
  private final static char []_jsp_string23;
  private final static char []_jsp_string16;
  private final static char []_jsp_string14;
  private final static char []_jsp_string25;
  private final static char []_jsp_string6;
  private final static char []_jsp_string5;
  private final static char []_jsp_string20;
  private final static char []_jsp_string30;
  private final static char []_jsp_string21;
  private final static char []_jsp_string12;
  private final static char []_jsp_string24;
  private final static char []_jsp_string1;
  private final static char []_jsp_string8;
  private final static char []_jsp_string10;
  private final static char []_jsp_string13;
  private final static char []_jsp_string22;
  private final static char []_jsp_string7;
  private final static char []_jsp_string18;
  private final static char []_jsp_string27;
  private final static char []_jsp_string9;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  private final static char []_jsp_string32;
  private final static char []_jsp_string3;
  private final static char []_jsp_string17;
  private final static char []_jsp_string26;
  private final static char []_jsp_string28;
  private final static char []_jsp_string11;
  private final static char []_jsp_string19;
  private final static char []_jsp_string2;
  private final static char []_jsp_string15;
  private final static char []_jsp_string31;
  static {
    _jsp_string29 = "\r\n                <option value=\"".toCharArray();
    _jsp_string23 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u4f5c\u8005\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"author_pic\" size=10 value=\"".toCharArray();
    _jsp_string16 = "\">\r\n    <input type=\"hidden\" name=\"username\" value=\"".toCharArray();
    _jsp_string14 = "\">\r\n    <input type=\"hidden\" name=\"sitename\" value=\"".toCharArray();
    _jsp_string25 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u5c0f\u56fe\u7247\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"prod_small_pic\" size=10 value=\"".toCharArray();
    _jsp_string6 = "\r\n\r\n        function validate()\r\n        {\r\n            if (document.form1.filename.value == \"\")\r\n            {\r\n                alert(\"\u8bf7\u9009\u62e9\u6587\u4ef6\uff01\");\r\n                return false;\r\n            }\r\n            return true;\r\n        }\r\n\r\n        function getValue(id){\r\n            id.select();\r\n            id.blur();\r\n            return document.selection.createRange().text;\r\n        }\r\n\r\n        function f(theimg) {\r\n            var val = theimg.value;\r\n            var filevalue=getValue(theimg);\r\n            var ext = val.substring(val.lastIndexOf(\".\")).toLowerCase();\r\n            var validate = false;\r\n\r\n            if (ext == \".gif\" || ext == \".jpg\" || ext == \".jpeg\" || ext == \".png\" || ext == \".bmp\") {\r\n                d.filters.item(\"DXImageTransform.Microsoft.AlphaImageLoader\").src = filevalue;\r\n                //d.style.width = d.offsetWidth;\r\n                //d.style.height = d.offsetHeight;\r\n                if (d.offsetWidth<400)\r\n                    d.style.width = d.offsetWidth;\r\n                else\r\n                    d.style.width = 400;\r\n                if (d.offsetHeight<280)\r\n                    d.style.height = d.offsetHeight;\r\n                else\r\n                    d.style.height = 280;\r\n                d.filters.item(\"DXImageTransform.Microsoft.AlphaImageLoader\").sizingMethod = 'scale';\r\n                validate = true;\r\n            }\r\n            else if (ext == \".swf\")\r\n            {\r\n                validate = true;\r\n            }\r\n            else\r\n            {\r\n                if (!validate)\r\n                {\r\n                    alert(\"\u53ea\u80fd\u4e0a\u4f20\u56fe\u50cf\u53caFLASH\u6587\u4ef6\uff01\");\r\n                }\r\n            }\r\n        }\r\n\r\n        function cal() {\r\n            var isMSIE = (navigator.appName == \"Microsoft Internet Explorer\");\r\n            if (isMSIE) {\r\n                window.returnValue = \"\";\r\n                top.close();\r\n            } else {\r\n                top.close();\r\n            }\r\n        }\r\n    </script>\r\n</head>\r\n\r\n<body bgcolor=\"#cccccc\">\r\n<form name=\"form1\" action=\"".toCharArray();
    _jsp_string5 = "').value = filename;\r\n\r\n        var isMSIE = (navigator.appName == \"Microsoft Internet Explorer\");\r\n        if (isMSIE) {\r\n            window.returnValue = \"\";\r\n            top.close();\r\n        } else {\r\n            top.close();\r\n        }\r\n        ".toCharArray();
    _jsp_string20 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u6807\u9898\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"title_pic\" size=10 value=\"".toCharArray();
    _jsp_string30 = "\" selected>".toCharArray();
    _jsp_string21 = "\" readonly=\"true\"></td>\r\n        </tr>\r\n        ".toCharArray();
    _jsp_string12 = "\">\r\n    <input type=\"hidden\" name=\"baseDir\" value=\"".toCharArray();
    _jsp_string24 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u6765\u6e90\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"source_pic\" size=10 value=\"".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title>\u4e0a\u4f20\u6587\u4ef6</title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet type=text/css href=\"../style/global.css\">\r\n    <script language=\"Javascript\">\r\n        ".toCharArray();
    _jsp_string8 = "&sitename=".toCharArray();
    _jsp_string10 = "\" value=\"/upload/upload.jsp\">\r\n    <input type=\"hidden\" name=\"status\" value=\"save\">\r\n    <input type=\"hidden\" name=\"picname\" value=\"".toCharArray();
    _jsp_string13 = "\">\r\n    <input type=\"hidden\" name=\"fileDir\" value=\"".toCharArray();
    _jsp_string22 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u526f\u6807\u9898\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"vtitle_pic\" size=10 value=\"".toCharArray();
    _jsp_string7 = "/multipartformserv?siteid=".toCharArray();
    _jsp_string18 = "\">\r\n    <input type=\"hidden\" name=\"fromflag\" value=\"article\">\r\n    <input type=\"hidden\" name=\"tcflag\" value=\"".toCharArray();
    _jsp_string27 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u6587\u7ae0\u7279\u6548\u56fe\u7247\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"special_pic\" size=10 value=\"".toCharArray();
    _jsp_string9 = "\" method=\"post\"\r\n      enctype=\"multipart/form-data\" onSubmit=\"return validate();\">\r\n    <input type=\"hidden\" name=\"".toCharArray();
    _jsp_string4 = "\";\r\n\r\n        if (attrname == \"mt\")\r\n            window.opener.document.getElementById('maintitle').value = filename;\r\n        else if (attrname == \"vt\")\r\n            window.opener.document.getElementById('vicetitle').value = filename;\r\n        else if (attrname == \"au\")\r\n            window.opener.document.getElementById('author').value = filename;\r\n        else if (attrname == \"sr\")\r\n            window.opener.document.getElementById('source').value = filename;\r\n        else if (attrname == \"createDesc\")\r\n            window.opener.document.getElementById('desc').value = filename;\r\n        else if (attrname == \"updateDesc\")\r\n            window.opener.document.getElementById('desc').value = filename;\r\n        else if (attrname == \"pic\")\r\n            window.opener.document.getElementById('pic').value = filename;\r\n        else if (attrname == \"bigpic\")\r\n            window.opener.document.getElementById('bigpic').value = filename;\r\n        else if (attrname == \"apic\")\r\n            window.opener.document.getElementById('articlepic').value = filename;\r\n        else\r\n            window.opener.document.getElementById('".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string32 = "</select>\r\n            </td>\r\n        </tr>\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\u63cf\u8ff0\uff1a<input type=text name=\"notes\" size=45></td>\r\n        </tr>\r\n        <tr height=35>\r\n            <td align=left>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n                <input type=\"submit\" value=\"  \u4e0a\u4f20  \" class=tine>&nbsp;&nbsp;\r\n                <input type=\"button\" value=\"  \u53d6\u6d88  \" class=tine onClick=\"cal();\">\r\n            </td>\r\n        </tr>\r\n        <tr>\r\n            <td valign=\"top\" bgcolor=\"#ffffff\">\r\n                <!--<iframe src=\"showImage.html\" width=\"400\" marginwidth=\"0\" height=\"280\" marginheight=\"0\" scrolling=\"auto\" frameborder=\"1\" name=frm></iframe>-->\r\n                <h1 id=d\r\n                    style=\"border:0px solid black;filter : progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);WIDTH: 400px; HEIGHT: 280px\">\r\n                </h1>\r\n            </td>\r\n        </tr>\r\n    </table>\r\n\r\n</form>\r\n\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string3 = "\";\r\n        var filename = \"".toCharArray();
    _jsp_string17 = "\">\r\n    <input type=\"hidden\" name=\"attr\" value=\"".toCharArray();
    _jsp_string26 = "\r\n        <tr height=30>\r\n            <td>\u56fe\u7247\uff08\u5927\u56fe\u7247\uff09\u957f\u5ea6\u548c\u5bbd\u5ea6\u8bbe\u7f6e\uff08WxH\uff09\uff1a<input type=\"text\" name=\"prod_large_pic\" size=10 value=\"".toCharArray();
    _jsp_string28 = "\r\n\r\n        <tr height=30>\r\n            <td>\u9009\u62e9\u4e3b\u673a\uff1a<select name=\"hostID\" size=1 style=\"width:250;font-size:9pt\">\r\n                <option value=\"0\">\u9ed8\u8ba4\u53d1\u5e03\u4e3b\u673a</option>\r\n                ".toCharArray();
    _jsp_string11 = "\">\r\n    <input type=\"hidden\" name=\"column\" value=\"".toCharArray();
    _jsp_string19 = "\">\r\n    <table align=\"center\" width=\"100%\" border=0>\r\n        <tr height=30>\r\n            <td>\u9009\u62e9\u6587\u4ef6\uff1a<input type=file name=\"filename\" size=31 onpropertychange=\"f(this)\"></td>\r\n        </tr>\r\n\r\n        ".toCharArray();
    _jsp_string2 = "\r\n        var attrname = \"".toCharArray();
    _jsp_string15 = "\">\r\n    <input type=\"hidden\" name=\"siteid\" value=\"".toCharArray();
    _jsp_string31 = "\r\n                </option>\r\n                ".toCharArray();
  }
}
