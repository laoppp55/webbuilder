/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._toolkit._survey;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.ParamUtil;
import com.bizwink.webapps.survey.define.Define;
import java.util.ArrayList;
import java.util.List;
import com.bizwink.webapps.survey.define.DefinePeer;
import com.bizwink.webapps.survey.define.IDefineManager;
import com.bizwink.webapps.survey.define.DefineException;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.security.Auth;
import com.bizwink.cms.util.SessionUtil;

public class _addanswer__jsp extends com.caucho.jsp.JavaPage
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
    
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
    return;
  }
  int sid = ParamUtil.getIntParameter(request, "sid", -1);
  int qid = ParamUtil.getIntParameter(request, "qid", -1);
  int aid = ParamUtil.getIntParameter(request, "aid", -1);
  int startflag = ParamUtil.getIntParameter(request, "startflag", -1);

  IDefineManager defineMgr = DefinePeer.getInstance();
  Define define = new Define();

  List list = new ArrayList();
  String qname = "";
  String surveyname = "";
  String notes = "";
  String qanswer = "";
  int qtype = 1;
  int qmust = 1;

  if (startflag == 1) {
    qanswer = ParamUtil.getParameter(request, "qanswer");
    if (aid == -1) {
      try {
        define.setSid(sid);
        define.setQid(qid);
        define.setQanswer(qanswer);
        defineMgr.createDefineAnswer(define);

        response.sendRedirect("addAnswer.jsp?success=1&sid=" + sid + "&qid=" + qid);
        return;
      } catch (DefineException e) {
        e.printStackTrace();
      }
    } else {
      try {
        define.setSid(sid);
        define.setQid(qid);
        define.setAid(aid);
        define.setQanswer(qanswer);
        defineMgr.updateDefineAnswer(define);

        response.sendRedirect("addAnswer.jsp?success=2&sid=" + sid + "&qid=" + qid);
        return;
      } catch (DefineException e) {
        e.printStackTrace();
      }
    }
  } else {
    try {
      define = defineMgr.getADefineSurvey(sid);
      surveyname = define.getSurveyname();
      notes = define.getNotes();

      define = defineMgr.getADefineQuestion(qid);
      qname = define.getQname();
      qtype = define.getQtype();
      qmust = define.getQmust();

      list = defineMgr.getAllDefineAnswersByQID(qid);
      if (aid != -1) {
        define = defineMgr.getADefineAnswer(aid);
        qanswer = define.getQanswer();
      }
    } catch (DefineException e) {
      e.printStackTrace();
    }
  }

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((sid));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((qid));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((aid));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((sid));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((StringUtil.iso2gb(surveyname)));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((StringUtil.iso2gb(notes)));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((StringUtil.iso2gb(qname)));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    if (qtype == 1) {
    out.write(_jsp_string9, 0, _jsp_string9.length);
    } else if (qtype == 2) {
    out.write(_jsp_string10, 0, _jsp_string10.length);
    }
    out.write(_jsp_string11, 0, _jsp_string11.length);
    if (qmust == 1) {
    out.write(_jsp_string12, 0, _jsp_string12.length);
    } else if (qmust == 2) {
    out.write(_jsp_string13, 0, _jsp_string13.length);
    }
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((StringUtil.iso2gb(qanswer)));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    out.print((sid));
    out.write(_jsp_string16, 0, _jsp_string16.length);
    
                  for (int i = 0; i < list.size(); i++) {
                    define = (Define) list.get(i);
                    aid = define.getAid();
                    qanswer = define.getQanswer();
                
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((StringUtil.iso2gb(qanswer)));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((qid));
    out.write(',');
    out.print((sid));
    out.write(',');
    out.print((aid));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    out.print((qid));
    out.write(_jsp_string20, 0, _jsp_string20.length);
    out.print((sid));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    out.print((aid));
    out.write(_jsp_string22, 0, _jsp_string22.length);
    
                  }
                
    out.write(_jsp_string23, 0, _jsp_string23.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("toolkit/survey/addAnswer.jsp"), 7243024320029173058L, false);
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
  private final static char []_jsp_string10;
  private final static char []_jsp_string20;
  private final static char []_jsp_string15;
  private final static char []_jsp_string14;
  private final static char []_jsp_string17;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  private final static char []_jsp_string3;
  private final static char []_jsp_string8;
  private final static char []_jsp_string18;
  private final static char []_jsp_string19;
  private final static char []_jsp_string11;
  private final static char []_jsp_string6;
  private final static char []_jsp_string4;
  private final static char []_jsp_string7;
  private final static char []_jsp_string13;
  private final static char []_jsp_string22;
  private final static char []_jsp_string9;
  private final static char []_jsp_string16;
  private final static char []_jsp_string23;
  private final static char []_jsp_string12;
  private final static char []_jsp_string21;
  private final static char []_jsp_string5;
  static {
    _jsp_string1 = "\r\n<html>\r\n<head>\r\n  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"/>\r\n  <title>\u8c03\u67e5\u7ba1\u7406-\u7b54\u6848\u7ba1\u7406</title>\r\n  <style type=\"text/css\">\r\n    <!--\r\n    body {\r\n      margin-top: 0px;\r\n      margin-bottom: 0px;\r\n    }\r\n\r\n    -->\r\n  </style>\r\n  <link href=\"images/css.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n  <script type=\"text/javascript\">\r\n    function DeleteAnswer(qid, sid, aid)\r\n    {\r\n      var str = confirm(\"\u786e\u8ba4\u8981\u5220\u9664\u7b54\u6848\u5417\uff1f\");\r\n      if (str)\r\n        window.location = \"deleteAnswer.jsp?qid=\" + qid + \"&sid=\" + sid + \"&aid=\" + aid;\r\n    }\r\n  </script>\r\n</head>\r\n\r\n<body>\r\n<center>\r\n<table width=\"900\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"bian\">\r\n<tr>\r\n<td height=\"360\" align=\"center\" valign=\"top\">\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n<form method=\"POST\" action=\"addAnswer.jsp\" name=addQuestionForm>\r\n<input type=\"hidden\" name=\"startflag\" value=\"1\">\r\n<input type=\"hidden\" name=\"sid\" value=\"".toCharArray();
    _jsp_string10 = "\u591a\u9009".toCharArray();
    _jsp_string20 = "&sid=".toCharArray();
    _jsp_string15 = "\">\r\n        </td>\r\n      </tr>\r\n\r\n    </table>\r\n  </td>\r\n</tr>\r\n<tr>\r\n  <td height=\"1\" valign=\"top\" bgcolor=\"#898898\"></td>\r\n</tr>\r\n<tr>\r\n  <td height=\"80\" align=\"center\" valign=\"middle\">\r\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <td width=\"310\"></td>\r\n        <td width=\"52\"><input type=\"submit\" value=\"\u63d0  \u4ea4\" name=\"survey_submit\"></td>\r\n        <td width=\"60\"></td>\r\n        <td width=\"52\"><input type=\"reset\" value=\"\u91cd  \u5199\" name=\"survey_reset\"></td>\r\n        <td width=\"60\"></td>\r\n        <td width=\"52\"><input type=\"button\" value=\"\u8fd4  \u56de\" name=\"survey_back\"\r\n                     onclick=\"window.location='addQuestion.jsp?sid=".toCharArray();
    _jsp_string14 = "</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"160\" height=\"50\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u7b54&nbsp;&nbsp;&nbsp;&nbsp;\u6848\uff1a</td>\r\n        <td bgcolor=\"#898898\"></td>\r\n        <td align=\"left\" valign=\"middle\">\r\n          &nbsp;<input type=\"text\" name=\"qanswer\" size=80 value=\"".toCharArray();
    _jsp_string17 = "\r\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n        <tr>\r\n          <td width=\"600\" height=\"80\" align=\"center\" class=\"black12\">".toCharArray();
    _jsp_string2 = "\">\r\n<input type=\"hidden\" name=\"qid\" value=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string3 = "\">\r\n<input type=\"hidden\" name=\"aid\" value=\"".toCharArray();
    _jsp_string8 = "</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"160\" height=\"50\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u95ee\u9898\u7c7b\u578b\uff1a</td>\r\n        <td bgcolor=\"#898898\"></td>\r\n        <td align=\"left\" class=\"black12\">&nbsp;&nbsp;".toCharArray();
    _jsp_string18 = "</td>\r\n          <td width=\"1\" bgcolor=\"#898898\"></td>\r\n          <td width=\"297\" align=\"center\" class=\"black12\">\r\n            <a href=\"javascript:DeleteAnswer(".toCharArray();
    _jsp_string19 = ")\">\u5220\u9664</a>\r\n            <a href=\"addAnswer.jsp?qid=".toCharArray();
    _jsp_string11 = "</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"160\" height=\"50\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u662f\u5426\u5fc5\u586b\uff1a</td>\r\n        <td bgcolor=\"#898898\"></td>\r\n        <td align=\"left\" class=\"black12\">&nbsp;&nbsp;".toCharArray();
    _jsp_string6 = "</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td bgcolor=\"#898898\"></td>\r\n        <td bgcolor=\"#898898\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"160\" height=\"50\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u8c03\u67e5\u63cf\u8ff0\uff1a</td>\r\n        <td bgcolor=\"#898898\"></td>\r\n        <td align=\"left\" class=\"black12\">&nbsp;&nbsp;".toCharArray();
    _jsp_string4 = "\">\r\n<tr>\r\n  <td valign=\"top\">\r\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <td width=\"50\" height=\"40\" align=\"center\"><img src=\"images/qian_01.jpg\" width=\"30\" height=\"30\"/></td>\r\n        <td align=\"left\"><span class=\"black12c\">\u8c03\u67e5\u7ba1\u7406</span><span class=\"black12\">--\u7b54\u6848\u7ba1\u7406</span></td>\r\n        <td width=\"30\" align=\"center\"><img src=\"images/lv_01.jpg\" width=\"11\" height=\"7\"/></td>\r\n        <td width=\"37\" class=\"black12c\"><a href=\"addQuestion.jsp?sid=".toCharArray();
    _jsp_string7 = "</td>\r\n      </tr>\r\n      <tr>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        <td height=\"1\" bgcolor=\"#898898\"></td>\r\n      </tr>\r\n      <tr>\r\n        <td width=\"160\" height=\"50\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u95ee\u9898\u540d\u79f0\uff1a</td>\r\n        <td bgcolor=\"#898898\"></td>\r\n        <td align=\"left\" class=\"black12\">&nbsp;&nbsp;".toCharArray();
    _jsp_string13 = "\u5fc5\u586b".toCharArray();
    _jsp_string22 = "\">\u4fee\u6539</a></td>\r\n        </tr>\r\n        <tr>\r\n          <td width=\"1\" bgcolor=\"#898898\"></td>\r\n          <td width=\"1\" bgcolor=\"#898898\"></td>\r\n          <td width=\"1\" bgcolor=\"#898898\"></td>\r\n        </tr>\r\n      </table>\r\n      ".toCharArray();
    _jsp_string9 = "\u5355\u9009".toCharArray();
    _jsp_string16 = "';\"></td>\r\n        <td width=\"60\"></td>\r\n        <td width=\"95\"><input type=\"button\" value=\"\u8fd4\u56de\u7ba1\u7406\u9996\u9875\" name=\"survey_back\" onclick=\"window.location='index.jsp';\"></td>\r\n        <td width=\"310\"></td>\r\n      </tr>\r\n    </table>\r\n  </td>\r\n</tr>\r\n</form>\r\n</table>\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td>\r\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n        <tr>\r\n          <td height=\"1\" bgcolor=\"#898898\"></td>\r\n          <td bgcolor=\"#898898\"></td>\r\n          <td bgcolor=\"#898898\"></td>\r\n        </tr>\r\n        <tr>\r\n          <td width=\"600\" height=\"40\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u95ee\u9898</td>\r\n          <td width=\"1\" bgcolor=\"#898898\"></td>\r\n          <td align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u7ba1\u7406</td>\r\n        </tr>\r\n        <tr>\r\n          <td height=\"1\" bgcolor=\"#898898\"></td>\r\n          <td height=\"1\" bgcolor=\"#898898\"></td>\r\n          <td height=\"1\" bgcolor=\"#898898\"></td>\r\n        </tr>\r\n      </table>\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td>\r\n      ".toCharArray();
    _jsp_string23 = "\r\n    </td>\r\n  </tr>\r\n  <tr>\r\n    <td height=\"40\"></td>\r\n  </tr>\r\n</table>\r\n</td>\r\n</tr>\r\n</table>\r\n</center>\r\n</body>\r\n</html>\r\n\r\n".toCharArray();
    _jsp_string12 = "\u9009\u586b".toCharArray();
    _jsp_string21 = "&aid=".toCharArray();
    _jsp_string5 = "\">\u8fd4\u56de</a></td>\r\n      </tr>\r\n\r\n    </table>\r\n  </td>\r\n</tr>\r\n<tr>\r\n  <td height=\"1\" valign=\"top\" bgcolor=\"#898898\"></td>\r\n</tr>\r\n<tr>\r\n  <td valign=\"top\">\r\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n      <tr>\r\n        <td width=\"160\" height=\"50\" align=\"center\" bgcolor=\"#F6F5F0\" class=\"black12c\">\u8c03\u67e5\u540d\u79f0\uff1a</td>\r\n        <td width=\"1\" bgcolor=\"#898898\"></td>\r\n        <td align=\"left\" class=\"black12\">&nbsp;&nbsp;".toCharArray();
  }
}
