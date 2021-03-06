/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._toolkit._survey;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.List;
import java.util.ArrayList;
import com.bizwink.webapps.survey.define.DefinePeer;
import com.bizwink.webapps.survey.define.IDefineManager;
import com.bizwink.cms.util.ParamUtil;
import com.bizwink.webapps.survey.define.Define;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.webapps.survey.define.DefineException;

public class _viewhsurvey__jsp extends com.caucho.jsp.JavaPage
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
    
  int sid = ParamUtil.getIntParameter(request, "sid", -1);

  IDefineManager defineMgr = DefinePeer.getInstance();
  List questionlist = new ArrayList();
  List answerlist = new ArrayList();

  if (sid != -1) {
    try {
      questionlist = defineMgr.getAllDefineQuestionsBySID(sid);
    } catch (DefineException e) {
      e.printStackTrace();
    }
  }

    out.write(_jsp_string1, 0, _jsp_string1.length);
    
      for(int i=0;i<questionlist.size();i++){
        Define define = (Define)questionlist.get(i);
        int qmust = define.getQmust();
        int qid = define.getQid();

        if(qmust == 2){
    
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((qid));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((qid));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((i+1));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    
        }
      }
    
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((sid));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((sid));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    
  if (questionlist.size() > 0) {
    for (int i = 0; i < questionlist.size(); i++) {
      Define qdefine = (Define) questionlist.get(i);
      int qid = qdefine.getQid();
      String qname = qdefine.getQname();
      int qtype = qdefine.getQtype();
      int nother = qdefine.getNother();
      int atype = qdefine.getAtype();

    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((StringUtil.iso2gb(qname)));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    
  try {
    answerlist = defineMgr.getAllDefineAnswersByQID(qid);
  } catch (DefineException e) {
    e.printStackTrace();
  }

    out.write(_jsp_string11, 0, _jsp_string11.length);
    
        if (answerlist != null) {
          for (int j = 0; j < answerlist.size(); j++) {
            Define adefine = (Define) answerlist.get(j);
            String qanswer = adefine.getQanswer();
            String picurl = adefine.getPicurl();

            if (atype != 1) {
              qanswer = picurl;
            }
      
    out.write(_jsp_string12, 0, _jsp_string12.length);
    if (qtype == 1) {
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((qid));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((StringUtil.iso2gb(qanswer)));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((answerlist.size()-1));
    out.write(',');
    out.print((nother));
    out.write(',');
    out.print((qid));
    out.write(',');
    out.print((j));
    out.write(',');
    out.print((qtype));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    if (atype == 1) {
    out.print((StringUtil.iso2gb(qanswer)));
    } else {
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((picurl));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((picurl));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    }
    out.write(_jsp_string20, 0, _jsp_string20.length);
    } else {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    out.print((qid));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((StringUtil.iso2gb(qanswer)));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((answerlist.size()-1));
    out.write(',');
    out.print((nother));
    out.write(',');
    out.print((qid));
    out.write(',');
    out.print((j));
    out.write(',');
    out.print((qtype));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    if (atype == 1) {
    out.print((StringUtil.iso2gb(qanswer)));
    } else {
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((picurl));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((picurl));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    }
    out.write(_jsp_string20, 0, _jsp_string20.length);
    
              }
            }
            if (nother == 1) {
          
    out.write(_jsp_string22, 0, _jsp_string22.length);
    out.print((qid));
    out.write(_jsp_string23, 0, _jsp_string23.length);
    out.print((qid));
    out.write(_jsp_string24, 0, _jsp_string24.length);
    
            }
          
    out.write(_jsp_string25, 0, _jsp_string25.length);
    
        }
      
    out.write(_jsp_string26, 0, _jsp_string26.length);
    
  }

    out.write(_jsp_string27, 0, _jsp_string27.length);
    
  } else {
    out.println("<script type=\"text/javascript\">");
    out.println("alert(\"\u8c03\u67e5\u5b9a\u4e49\u4e0d\u5b8c\u6574\uff0c\u8bf7\u5b8c\u6574\u5b9a\u4e49\u540e\u9884\u89c8\u8c03\u67e5\uff01\");");
    out.println("window.close();");
    out.println("</script>");
  }

    out.write(_jsp_string28, 0, _jsp_string28.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("toolkit/survey/viewHSurvey.jsp"), -2264409766931845538L, false);
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

  private final static char []_jsp_string20;
  private final static char []_jsp_string23;
  private final static char []_jsp_string18;
  private final static char []_jsp_string8;
  private final static char []_jsp_string15;
  private final static char []_jsp_string14;
  private final static char []_jsp_string6;
  private final static char []_jsp_string2;
  private final static char []_jsp_string12;
  private final static char []_jsp_string11;
  private final static char []_jsp_string25;
  private final static char []_jsp_string3;
  private final static char []_jsp_string4;
  private final static char []_jsp_string28;
  private final static char []_jsp_string21;
  private final static char []_jsp_string13;
  private final static char []_jsp_string26;
  private final static char []_jsp_string19;
  private final static char []_jsp_string27;
  private final static char []_jsp_string16;
  private final static char []_jsp_string17;
  private final static char []_jsp_string1;
  private final static char []_jsp_string9;
  private final static char []_jsp_string7;
  private final static char []_jsp_string10;
  private final static char []_jsp_string24;
  private final static char []_jsp_string5;
  private final static char []_jsp_string22;
  private final static char []_jsp_string0;
  static {
    _jsp_string20 = "&nbsp;&nbsp;\r\n          ".toCharArray();
    _jsp_string23 = "\">\r\n          <script type=\"text/javascript\">\r\n            answerForm.other".toCharArray();
    _jsp_string18 = "\"\r\n                                                                             target=_blank><img\r\n                src=\"../answerspic/".toCharArray();
    _jsp_string8 = "\">\r\n<table width=80% border=0 cellpadding=0 cellspacing=0 align='center' width=157 height=217>\r\n<tr>\r\n<td>\r\n<table border=\"0\" width=\"100%\" cellspacing=\"1\" cellpadding=3>\r\n".toCharArray();
    _jsp_string15 = "\"\r\n                 onClick=\"checkNum(".toCharArray();
    _jsp_string14 = "\" value=\"".toCharArray();
    _jsp_string6 = "\r\n\r\n      answerForm.submit();\r\n      return true;\r\n    }\r\n\r\n    function checkNum(listsize, nother, qid, which, qtype) {\r\n      var otherBtnName = \"answerForm.other\" + qid;\r\n      var o = eval(otherBtnName);\r\n      if (nother == 1) {\r\n        if (listsize == which) {\r\n          if (qtype == 1) {\r\n            o.disabled = 0;\r\n          } else {\r\n            if (eval(\"answerForm.answer\" + qid + \"[\" + listsize + \"]\").checked) {\r\n              o.disabled = 0;\r\n            } else {\r\n              o.disabled = 1;\r\n            }\r\n          }\r\n        } else {\r\n          o.disabled = 1;\r\n        }\r\n      }\r\n    }\r\n\r\n    function viewResult() {\r\n      window.open(\"viewResult.jsp?sid=".toCharArray();
    _jsp_string2 = "\r\n      var mustflag = false;\r\n      for (var i = 0; i < answerForm.answer".toCharArray();
    _jsp_string12 = "\r\n      <tr>\r\n        <td>\r\n          ".toCharArray();
    _jsp_string11 = "\r\n<tr height=35>\r\n  <td width=\"5%\" align=left>\r\n    <table border=0>\r\n      ".toCharArray();
    _jsp_string25 = "\r\n        </td>\r\n      </tr>\r\n      ".toCharArray();
    _jsp_string3 = ".length; i++) {\r\n        if (answerForm.answer".toCharArray();
    _jsp_string4 = "[i].checked) {\r\n          mustflag = true;\r\n          break;\r\n        }\r\n      }\r\n      if (!mustflag) {\r\n        alert(\"\u8bf7\u9009\u62e9\u95ee\u9898 ".toCharArray();
    _jsp_string28 = "\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</form>\r\n</center>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string21 = "\r\n          <input type=\"checkbox\" name=\"answer".toCharArray();
    _jsp_string13 = "\r\n          <input type=\"radio\" name=\"answer".toCharArray();
    _jsp_string26 = "\r\n    </table>\r\n  </td>\r\n</tr>\r\n".toCharArray();
    _jsp_string19 = "\"\r\n                alt=\"\" width=160 border=0></a>".toCharArray();
    _jsp_string27 = "\r\n<!--tr bgcolor=\"#ffffff\">\r\n  <td>\u7528\u6237\u59d3\u540d\uff1a<input type=\"text\" name=\"username\">&nbsp;&nbsp;\r\n    \u6027\u522b\uff1a<input type=\"radio\" name=\"gender\" value=\"\u7537\" checked>\u7537&nbsp;<input type=\"radio\" name=\"gender\" value=\"\u5973\">\u5973\r\n  </td>\r\n</tr>\r\n<tr bgcolor=\"#ffffff\">\r\n  <td>\u8054\u7cfb\u7535\u8bdd\uff1a<input type=\"text\" name=\"phone\"></td>\r\n</tr>\r\n<tr bgcolor=\"#ffffff\">\r\n  <td>\u7535\u5b50\u90ae\u4ef6\uff1a<input type=\"text\" name=\"email\"></td>\r\n</tr-->\r\n<tr>\r\n  <td align=center height=50><input type=\"button\" name=\"subbutton\" value=\"\u63d0   \u4ea4\" onclick=\"return check();\">&nbsp;\r\n    <input type=\"button\" name=\"surveybut\" value=\"\u67e5\u770b\u7ed3\u679c\" onclick=\"viewResult();\">&nbsp;\r\n  </td>\r\n</tr>\r\n".toCharArray();
    _jsp_string16 = ");\">\r\n          ".toCharArray();
    _jsp_string17 = "<a href=\"../answerspic/".toCharArray();
    _jsp_string1 = "\r\n<html>\r\n<head>\r\n  <title>\u8c03\u67e5\u9884\u89c8</title>\r\n  <link rel=stylesheet type=text/css href=style/global.css>\r\n  <script type=\"text/javascript\">\r\n    function check() {\r\n    ".toCharArray();
    _jsp_string9 = "\r\n<tr>\r\n  <td width=\"5%\" align=left>".toCharArray();
    _jsp_string7 = "\");\r\n    }\r\n  </script>\r\n</head>\r\n\r\n<body background=\"images/surveybg.gif\">\r\n<center>\r\n<form action=\"answer.jsp\" method=\"post\" name=\"answerForm\">\r\n<input type=\"hidden\" name=\"sid\" value=\"".toCharArray();
    _jsp_string10 = "\r\n  </td>\r\n</tr>\r\n".toCharArray();
    _jsp_string24 = ".disabled = 1;\r\n          </script>\r\n          ".toCharArray();
    _jsp_string5 = " \u7684\u7b54\u6848\uff01\");\r\n        return false;\r\n      }\r\n    ".toCharArray();
    _jsp_string22 = "\r\n          <input type=\"text\" name=\"other".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}
