/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._template;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.modelManager.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.modelManager.history.ModelHistory;
import com.bizwink.cms.server.CmsServer;

public class _edittemplate__jsp extends com.caucho.jsp.JavaPage
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
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    boolean errors = false;
    boolean success = false;
    String username = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String sitename = authToken.getSitename();
    String rootPath = application.getRealPath("/");

    int tempnum=ParamUtil.getIntParameter(request,"tempnum",0);
    long historyID = ParamUtil.getLongParameter(request, "historyID", 0);
    int ID = ParamUtil.getIntParameter(request, "template", 0);
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    int modelType = ParamUtil.getIntParameter(request, "modelType", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int isIncluded = ParamUtil.getIntParameter(request, "isIncluded", 0);
    String content = ParamUtil.getParameter(request, "content");
    String cname = ParamUtil.getParameter(request, "cname");
    String modelname = ParamUtil.getParameter(request, "modelname");
    String modelnameBak = ParamUtil.getParameter(request, "modelnameBak");
    boolean publish = ParamUtil.getBooleanParameter(request, "publish");
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));

    IModelManager modelManager = ModelPeer.getInstance();
    Model model=null;

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String columnName = StringUtil.gb2iso4View(column.getCName());

    if (doUpdate) {
        try {
            if (modelname == null || modelname.length() < 1) {
                out.println("\u6a21\u677f\u6587\u4ef6\u540d\u4e0d\u80fd\u4e3a\u7a7a\uff01\u8bf7<a href=javascript:history.go(-1);>\u8fd4\u56de</a>");
                return;
            } else if (!modelnameBak.equalsIgnoreCase(modelname)) {
                if (modelManager.hasSameModelName(siteid,columnID, modelname)) {
                    out.println("\u6a21\u677f\u6587\u4ef6\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff01\u8bf7<a href=javascript:history.go(-1);>\u8fd4\u56de</a>");
                    return;
                }
            }

            int posi = -1;
            while ((posi = content.toLowerCase().indexOf("<cmstextarea")) > -1)
                content = content.substring(0, posi) + "<textarea" + content.substring(posi + 12);
            while ((posi = content.toLowerCase().indexOf("</cmstextarea>")) > -1)
                content = content.substring(0, posi) + "</textarea>" + content.substring(posi + 14);

            model = new Model();
            model.setColumnID(columnID);
            model.setID(ID);
            model.setIsArticle(modelType);
            model.setContent(content);
            model.setEditor(username);
            model.setLastupdated(new Timestamp(System.currentTimeMillis()));
            model.setLockstatus(0);
            model.setChineseName(cname);
            model.setTemplateName(modelname);
            model.setRealPath(rootPath);
            model.setSiteName(sitename);
            model.setPublishArticle(publish);
            model.setIncluded(isIncluded);
            model.setTempnum(tempnum);
            modelManager.Update(model, siteid,samsiteid,0);
            success = true;
        } catch (ModelException e) {
            e.printStackTrace();
            errors = true;
        }
    }

    if (success) {
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id=" + ID + "&column=" + columnID + "&rightid=" + rightid));
        return;
    }

    ModelHistory history = new ModelHistory(rootPath);
    List historylist = history.getModelList(sitename, ID);

    model = modelManager.getModel(ID, username);

    if (historyID > 0) {
        content = history.getModel(ID, historyID, sitename);
        model.setContent(content);
    }
    modelType = model.getIsArticle();
    content = model.getContent();


    int posi = -1;
    while ((posi = content.toLowerCase().indexOf("<textarea")) > -1)
        content = content.substring(0, posi) + "<cmstextarea" + content.substring(posi + 9);
    while ((posi = content.toLowerCase().indexOf("</textarea>")) > -1)
        content = content.substring(0, posi) + "</cmstextarea>" + content.substring(posi + 11);
    content = StringUtil.gb2iso4View(content);
    cname = StringUtil.gb2iso4View(model.getChineseName());
    modelname = model.getTemplateName();
    isIncluded = model.getIncluded();

//\u8bfb\u51fa\u6587\u7ae0\u6269\u5c55\u5c5e\u6027\uff0c\u7528\u4e8e\u6587\u7ae0\u6a21\u677f\u7684\u6807\u8bb0
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    List attrList = extendMgr.getAttrForTemplate(columnID);

    attrList.add(0, "NO_SELECT,\u9009\u62e9\u6807\u8bb0");
    attrList.add("ARTICLEPIC,\u6587\u7ae0\u56fe\u7247");
    attrList.add("ARTICLE_SUMMARY,\u6587\u7ae0\u6982\u8ff0");
    attrList.add("ARTICLE_MEDIA,\u89c6\u9891\u64ad\u653e");
    attrList.add("ARTICLE_PT,\u53d1\u5e03\u65f6\u95f4");
    attrList.add("ARTICLE_LIST,\u6587\u7ae0\u5217\u8868");
    attrList.add("RELATED_ARTICLE,\u76f8\u5173\u6587\u7ae0");
    attrList.add("TOP_STORIES,\u70ed\u70b9\u6587\u7ae0");
    attrList.add("COLUMN_LIST,\u680f\u76ee\u5217\u8868");
    attrList.add("ARTICLE_PATH,\u6587\u7ae0\u8def\u5f84");
    attrList.add("CHINESE_PATH,\u4e2d\u6587\u8def\u5f84");
    attrList.add("ENGLISH_PATH,\u82f1\u6587\u8def\u5f84");
    attrList.add("NAVBAR,\u5bfc\u822a\u6761");
    attrList.add("SUBARTICLE_LIST,\u5b50\u6587\u7ae0\u5217\u8868");
    attrList.add("BROTHER_LIST,\u5144\u5f1f\u6587\u7ae0\u5217\u8868");
    attrList.add("SUBCOLUMN_LIST,\u5b50\u680f\u76ee\u5217\u8868");
    attrList.add("ARTICLE_COUNT,\u6587\u7ae0\u6761\u6570");
    attrList.add("SUBARTICLE_COUNT,\u5b50\u6587\u7ae0\u6761\u6570");
    attrList.add("ARTICLEID,\u6587\u7ae0ID");
    attrList.add("ARTICLESTATUS,\u6587\u7ae0\u72b6\u6001");
    attrList.add("COLUMNID,\u680f\u76eeID");
    attrList.add("RELATEID,\u7236\u6587\u7ae0ID");
    attrList.add("COLUMNNAME,\u680f\u76ee\u540d\u79f0");
    attrList.add("PARENT_COLUMNNAME,\u7236\u680f\u76ee\u540d\u79f0");
    attrList.add("COLUMNURL,\u680f\u76eeURL");
    attrList.add("PARENT_PATH,\u7236\u6587\u7ae0\u8def\u5f84");
    attrList.add("ARTICLE_DOCLEVEL,\u6587\u7ae0\u6743\u91cd");
    attrList.add("PAGINATION,\u5206\u9875\u6807\u8bb0");
    attrList.add("PREV_ARTICLE,\u4e0a\u4e00\u7bc7");
    attrList.add("NEXT_ARTICLE,\u4e0b\u4e00\u7bc7");
    //attrList.add("ARTICLE_TYPE,\u6587\u7ae0\u5206\u7c7b");
    attrList.add("COMPANYNAME,\u516c\u53f8\u540d\u79f0");
    attrList.add("TELEPHONE,\u516c\u53f8\u8054\u7cfb\u7535\u8bdd");
    attrList.add("EMAIL,\u516c\u53f8\u90ae\u4ef6");
    attrList.add("WEIBO,\u516c\u53f8\u5fae\u535a");
    attrList.add("SEARCH,\u68c0\u7d22\u8868\u5355");
    attrList.add("ORDERSEARCH_RESULT,\u8ba2\u5355\u67e5\u8be2\u8868\u5355");
    attrList.add("LOGIN_FORM,\u7528\u6237\u767b\u5f55\u8868\u5355");
    attrList.add("DEFINEINFO,\u8c03\u67e5\u8868\u5355");
    attrList.add("ARTICLE_COMMENT,\u6587\u7ae0\u8bc4\u8bba\u8868\u5355");
    attrList.add("TURN_PIC,\u6587\u7ae0\u9644\u56fe\u5217\u8868");
    attrList.add("QQ,QQ\u53f7\u7801");
    attrList.add("INCLUDE_FILE,\u52a0\u5165\u5305\u542b\u6587\u4ef6");
    attrList.add("SEECOOKIE,\u6700\u8fd1\u6d4f\u89c8");
    attrList.add("TURNPIC,\u56fe\u7247\u8f6e\u6362\u6548\u679c");
    attrList.add("LEAVEMESSAGE,\u7528\u6237\u7559\u8a00\u8868\u5355");
    attrList.add("LEAVEMESSAGELIST,\u7528\u6237\u7559\u8a00\u5217\u8868");
    attrList.add("USER_LOGIN_DISPLAY,\u7528\u6237\u767b\u5f55\u663e\u793a");
    attrList.add("USED_MARK,\u9009\u62e9\u5df2\u6709\u6807\u8bb0");

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((attrList.size() - 1));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    for (int i=0; i<attrList.size(); i++){
              String temp = (String)attrList.get(i);
              String name = temp.substring(0, temp.indexOf(","));
              String value = temp.substring(temp.indexOf(",") + 1);
              out.println("optionsname[1]["+i+"] =\""+value+"\";\r\n");
              out.println("optionsvalue[1]["+i+"] =\""+name+"\";\r\n");
            }
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((ID));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((columnID));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((modelType));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((columnID));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((modelType));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((modelname));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((ID));
    out.write(_jsp_string10, 0, _jsp_string10.length);
    out.print((columnID));
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((username));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    out.print((username));
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((request.getRequestURL().toString()+"-"+siteid));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((columnName));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    
                if (!success && errors) {
                    out.println("<span class=cur>\u4fee\u6539\u6a21\u677f\u5931\u8d25\uff0c\u8bf7\u91cd\u65b0\u518d\u8bd5\u3002</span>");
                }
            
    out.write(_jsp_string16, 0, _jsp_string16.length);
    out.print((ID));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((columnID));
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((rightid));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    //=columnID
    out.write(_jsp_string19, 0, _jsp_string19.length);
    if (column.getParentID() == 0) {
    out.write(_jsp_string20, 0, _jsp_string20.length);
    if (modelType == 2) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string22, 0, _jsp_string22.length);
    if (modelType == 5) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string23, 0, _jsp_string23.length);
    }else{
    out.write(_jsp_string24, 0, _jsp_string24.length);
    if (modelType == 0) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string25, 0, _jsp_string25.length);
    if (modelType == 4) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string26, 0, _jsp_string26.length);
    }
    out.write(_jsp_string27, 0, _jsp_string27.length);
    if (modelType == 1) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string28, 0, _jsp_string28.length);
    if (modelType == 3) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string29, 0, _jsp_string29.length);
    if (modelType == 6) {
    out.write(_jsp_string21, 0, _jsp_string21.length);
    }
    out.write(_jsp_string30, 0, _jsp_string30.length);
    out.print((cname));
    out.write(_jsp_string31, 0, _jsp_string31.length);
    out.print((modelname));
    out.write(_jsp_string32, 0, _jsp_string32.length);
    for (int i = historylist.size() - 1; i > -1; i--) {
    out.write(_jsp_string33, 0, _jsp_string33.length);
    out.print((historylist.get(i)));
    out.write(_jsp_string34, 0, _jsp_string34.length);
    out.print((
                    (new Timestamp(Long.parseLong((String) historylist.get(i)))).toString().substring(0, 19)));
    out.write(_jsp_string35, 0, _jsp_string35.length);
    }
    out.write(_jsp_string36, 0, _jsp_string36.length);
    out.print((columnID));
    out.write(_jsp_string37, 0, _jsp_string37.length);
    out.print((sitename));
    out.write(_jsp_string38, 0, _jsp_string38.length);
    if(isIncluded==1){
    out.write(_jsp_string39, 0, _jsp_string39.length);
    }
    out.write(_jsp_string40, 0, _jsp_string40.length);
    out.print((model.getTempnum()));
    out.write(_jsp_string41, 0, _jsp_string41.length);
    out.print((content));
    out.write(_jsp_string42, 0, _jsp_string42.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("template/edittemplate.jsp"), -2073730182991576980L, false);
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

  private final static char []_jsp_string33;
  private final static char []_jsp_string18;
  private final static char []_jsp_string3;
  private final static char []_jsp_string6;
  private final static char []_jsp_string41;
  private final static char []_jsp_string22;
  private final static char []_jsp_string19;
  private final static char []_jsp_string34;
  private final static char []_jsp_string7;
  private final static char []_jsp_string26;
  private final static char []_jsp_string31;
  private final static char []_jsp_string21;
  private final static char []_jsp_string4;
  private final static char []_jsp_string2;
  private final static char []_jsp_string29;
  private final static char []_jsp_string11;
  private final static char []_jsp_string32;
  private final static char []_jsp_string25;
  private final static char []_jsp_string36;
  private final static char []_jsp_string12;
  private final static char []_jsp_string24;
  private final static char []_jsp_string17;
  private final static char []_jsp_string10;
  private final static char []_jsp_string27;
  private final static char []_jsp_string13;
  private final static char []_jsp_string0;
  private final static char []_jsp_string40;
  private final static char []_jsp_string20;
  private final static char []_jsp_string38;
  private final static char []_jsp_string14;
  private final static char []_jsp_string15;
  private final static char []_jsp_string30;
  private final static char []_jsp_string37;
  private final static char []_jsp_string39;
  private final static char []_jsp_string23;
  private final static char []_jsp_string42;
  private final static char []_jsp_string28;
  private final static char []_jsp_string16;
  private final static char []_jsp_string1;
  private final static char []_jsp_string9;
  private final static char []_jsp_string8;
  private final static char []_jsp_string35;
  private final static char []_jsp_string5;
  static {
    _jsp_string33 = "\r\n                <option value=\"".toCharArray();
    _jsp_string18 = "';\">&nbsp;&nbsp;\r\n            </td>\r\n        </tr>\r\n        <tr bgcolor=#003366>\r\n            <td colspan=3 height=2></td>\r\n        </tr>\r\n        <tr>\r\n            <td colspan=3 height=4></td>\r\n        </tr>\r\n    </table>\r\n\r\n\r\n    <table width=\"100%\" border=0 cellspacing=0 cellpadding=0>\r\n        <tr>\r\n            <td>\r\n                <!--input type=radio name=insertTemplat value=0 onclick=\"javascript:Add_Template_onclick(".toCharArray();
    _jsp_string3 = "\r\n\r\n            if (param == 0 || param == 2 || param == 3 || para == 4 || param == 5 || param == 6)\r\n                param = 0;\r\n            else\r\n                param = 1;\r\n\r\n            document.getElementById(\"MarkName\").options.length=0;\r\n            for (x = 0; x <= selectlens[param]; x++)\r\n            {\r\n                document.getElementById(\"MarkName\").options.add(new Option(optionsname[param][x],optionsvalue[param][x]));\r\n            }\r\n        }\r\n\r\n        function historyback(historyID) {\r\n            if (historyID > 0 && confirm(\"\u662f\u5426\u771f\u7684\u8981\u6062\u590d\u5386\u53f2\u6a21\u677f\uff1f\"))\r\n                window.location = \"edittemplate.jsp?template=".toCharArray();
    _jsp_string6 = "&rightid=2&historyID=\" + historyID;\r\n            else\r\n                createForm.historyID[0].selected = true;\r\n        }\r\n\r\n        function saveModel() {\r\n            var modeltype = document.createForm.modelType.value;\r\n            if (modeltype==1) {\r\n                var ret = confirm(\"\u8981\u91cd\u65b0\u53d1\u5e03\u6240\u6709\u5957\u7528\u8be5\u6587\u7ae0\u6a21\u677f\u7684\u6587\u7ae0\u5417\uff1f\");\r\n                if (ret) {\r\n                    document.createForm.publish.value = \"true\";\r\n                    edit_src(createForm);\r\n                } else {\r\n                    window.close();\r\n                }\r\n            } else\r\n                edit_src(createForm);\r\n        }\r\n    </script>\r\n</head>\r\n\r\n<body>\r\n<form action=\"edittemplate.jsp\" method=\"post\" name=\"createForm\">\r\n    <input type=hidden name=\"backidx\">\r\n    <input type=hidden name=\"textvalues\" value=\"\">   <!--\u8868\u5355\u5b9a\u4e49\u7684XML\u8868\u793a-->\r\n    <input type=hidden name=\"publish\" value=\"false\">\r\n    <input type=hidden name=\"doUpdate\" value=true>\r\n    <input type=hidden name=\"column\" value=\"".toCharArray();
    _jsp_string41 = "\">\r\n            </td>\r\n        </tr>\r\n        <tr style=\"height: 100%\">\r\n            <td id=\"selhtmlcode\" valign=\"top\"><input type=\"hidden\" name=\"hcode\" id=\"htmlcodeid\" value=\"\"></td>\r\n        </tr>\r\n        <tr>\r\n            <td ID=bottomofFld></td>\r\n        </tr>\r\n    </table>\r\n\r\n    <table border=\"0\" width=\"100%\">\r\n        <tr>\r\n            <td>\r\n                <textarea id=\"content\" name=\"content\" style=\"WIDTH: 100%; HEIGHT: 1000px\">".toCharArray();
    _jsp_string22 = ">\u9996\u9875\u6a21\u677f</option>\r\n                <option value=5 ".toCharArray();
    _jsp_string19 = ");\">\u88c5\u5165\u6a21\u677f-->\r\n                &nbsp;\u6a21\u677f\u7c7b\u578b\uff1a<select name=modelType onchange=\"checkoption(1);\">\r\n                ".toCharArray();
    _jsp_string34 = "\">".toCharArray();
    _jsp_string7 = "\">\r\n    <input type=hidden name=\"modetype\" value=\"".toCharArray();
    _jsp_string26 = ">\u624b\u673a\u680f\u76ee\u6a21\u677f</option>\r\n                ".toCharArray();
    _jsp_string31 = "\">\r\n                &nbsp;\u6a21\u677f\u6587\u4ef6\u540d\uff1a<input name=\"modelname\" size=15 value=\"".toCharArray();
    _jsp_string21 = " selected".toCharArray();
    _jsp_string4 = "&column=".toCharArray();
    _jsp_string2 = ";\r\n            var optionsname = new Array();\r\n            var optionsvalue = new Array();\r\n\r\n            optionsname[0] = new Array();\r\n            optionsname[0][0] = \"\u9009\u62e9\u6807\u8bb0\";\r\n            optionsname[0][1] = \"\u6587\u7ae0\u5217\u8868\";\r\n            optionsname[0][2] = \"\u63a8\u8350\u6587\u7ae0\u5217\u8868\";\r\n            optionsname[0][3] = \"\u5b50\u6587\u7ae0\u5217\u8868\";\r\n            optionsname[0][4] = \"\u5144\u5f1f\u6587\u7ae0\u5217\u8868\";\r\n            optionsname[0][5] = \"\u70ed\u70b9\u6587\u7ae0\";\r\n            optionsname[0][6] = \"\u76f8\u5173\u6587\u7ae0\";\r\n            optionsname[0][7] = \"\u680f\u76ee\u5217\u8868\";\r\n            optionsname[0][8] = \"\u5b50\u680f\u76ee\u5217\u8868\";\r\n            optionsname[0][9] = \"\u4e2d\u6587\u8def\u5f84\";\r\n            optionsname[0][10] = \"\u82f1\u6587\u8def\u5f84\";\r\n            optionsname[0][11] = \"\u6587\u7ae0\u6761\u6570\";\r\n            optionsname[0][12] = \"\u5b50\u6587\u7ae0\u6761\u6570\";\r\n            optionsname[0][13] = \"\u5bfc\u822a\u6761\";\r\n            optionsname[0][14] = \"\u680f\u76ee\u540d\u79f0\";\r\n            optionsname[0][15] = \"\u7236\u680f\u76ee\u540d\u79f0\";\r\n            optionsname[0][16] = \"\u680f\u76eeURL\";\r\n            optionsname[0][17] = \"\u516c\u53f8\u540d\u79f0\";\r\n            optionsname[0][18] = \"\u516c\u53f8\u8054\u7cfb\u7535\u8bdd\";\r\n            optionsname[0][19] = \"\u516c\u53f8\u90ae\u4ef6\";\r\n            optionsname[0][20] = \"\u516c\u53f8\u5fae\u535a\";\r\n            optionsname[0][21] = \"\u68c0\u7d22\u8868\u5355\";\r\n            optionsname[0][22] = \"\u8ba2\u5355\u67e5\u8be2\u8868\u5355\";\r\n            optionsname[0][23] = \"\u7528\u6237\u767b\u5f55\u8868\u5355\";\r\n            optionsname[0][24] = \"\u8c03\u67e5\u8868\u5355\";\r\n            //optionsname[0][20] =\"\u65e5\u5386\";\r\n            optionsname[0][25] = \"\u56fe\u7247\u7279\u6548\";\r\n            optionsname[0][26] = \"QQ\u53f7\u7801\";\r\n            optionsname[0][27] = \"\u52a0\u5165\u5305\u542b\u6587\u4ef6\";\r\n            optionsname[0][28] = \"\u6700\u8fd1\u6d4f\u89c8\";\r\n            optionsname[0][29] = \"\u56fe\u7247\u8f6e\u6362\u6548\u679c\";\r\n            optionsname[0][30] = \"\u7528\u6237\u7559\u8a00\u8868\u5355\";\r\n            optionsname[0][31] = \"\u7528\u6237\u7559\u8a00\u5217\u8868\";\r\n            optionsname[0][32] = \"\u7528\u6237\u767b\u5f55\u663e\u793a\";\r\n            optionsname[0][33] = \"\u9009\u62e9\u5df2\u6709\u6807\u8bb0\";\r\n\r\n            optionsvalue[0] = new Array();\r\n            optionsvalue[0][0] = \"NO_SELECT\";\r\n            optionsvalue[0][1] = \"ARTICLE_LIST\";\r\n            optionsvalue[0][2] = \"COMMEND_ARTICLE\";\r\n            optionsvalue[0][3] = \"SUBARTICLE_LIST\";\r\n            optionsvalue[0][4] = \"BROTHER_LIST\";\r\n            optionsvalue[0][5] = \"TOP_STORIES\";\r\n            optionsvalue[0][6] = \"RELATED_ARTICLE\";\r\n            optionsvalue[0][7] = \"COLUMN_LIST\";\r\n            optionsvalue[0][8] = \"SUBCOLUMN_LIST\";\r\n            optionsvalue[0][9] = \"CHINESE_PATH\";\r\n            optionsvalue[0][10] = \"ENGLISH_PATH\";\r\n            optionsvalue[0][11] = \"ARTICLE_COUNT\";\r\n            optionsvalue[0][12] = \"SUBARTICLE_COUNT\";\r\n            optionsvalue[0][13] = \"NAVBAR\";\r\n            optionsvalue[0][14] = \"COLUMNNAME\";\r\n            optionsvalue[0][15] = \"PARENT_COLUMNNAME\";\r\n            optionsvalue[0][16] = \"COLUMNURL\";\r\n            optionsvalue[0][17] = \"COMPANYNAME\";\r\n            optionsvalue[0][18] = \"TELEPHONE\";\r\n            optionsvalue[0][19] = \"EMAIL\";\r\n            optionsvalue[0][20] = \"WEIBO\";\r\n            optionsvalue[0][21] = \"SEARCH_FORM\";\r\n            optionsvalue[0][22] = \"ORDERSEARCH_RESULT\";\r\n            optionsvalue[0][23] = \"LOGIN_FORM\";\r\n            optionsvalue[0][24] = \"DEFINEINFO_FORM\";\r\n            optionsvalue[0][25] = \"XUAN_IMAGES\";\r\n            optionsvalue[0][26] = \"QQ\";\r\n            optionsvalue[0][27] = \"INCLUDE_FILE\";\r\n            optionsvalue[0][28] = \"SEECOOKIE\";\r\n            optionsvalue[0][29] = \"TURNPIC\";\r\n            optionsvalue[0][30] = \"LEAVEMESSAGE\";\r\n            optionsvalue[0][31] = \"LEAVEMESSAGELIST\";\r\n            optionsvalue[0][32] = \"USER_LOGIN_DISPLAY\";\r\n            optionsvalue[0][33] = \"USED_MARK\";\r\n\r\n            optionsname[1] = new Array();\r\n            optionsvalue[1] = new Array();\r\n\r\n            ".toCharArray();
    _jsp_string29 = ">\u4e13\u9898\u6a21\u677f</option>\r\n                <option value=6 ".toCharArray();
    _jsp_string11 = "\">\r\n    <input type=hidden name=\"modelSourceCodeFlag\" value=0>\r\n    <input type=hidden name=\"template_or_article_flag\" value=\"0\">\r\n    <input type=hidden name=\"editor\" value=\"".toCharArray();
    _jsp_string32 = "\">\r\n                &nbsp;<select class=tine name=\"historyID\" style=\"width:114px\" onchange=\"historyback(this.value);\">\r\n                <option value=\"0\">\u9009\u62e9\u5386\u53f2\u6a21\u677f</option>\r\n                ".toCharArray();
    _jsp_string25 = ">\u680f\u76ee\u6a21\u677f</option>\r\n                <option value=4 ".toCharArray();
    _jsp_string36 = "\r\n            </select>\r\n                &nbsp;<select ID=\"MarkName\" onchange=\"return MarkName_Add(".toCharArray();
    _jsp_string12 = "\">\r\n    <input type=hidden name=\"username\" value=\"".toCharArray();
    _jsp_string24 = "\r\n                <option value=0 ".toCharArray();
    _jsp_string17 = "&rightid=".toCharArray();
    _jsp_string10 = "\">\r\n    <input type=hidden name=\"columnCode\" value=\"".toCharArray();
    _jsp_string27 = "\r\n                <option value=1 ".toCharArray();
    _jsp_string13 = "\">\r\n    <input type=hidden name=\"tempURL\" value=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n<!--%@ taglib uri=\"/FCKeditor\" prefix=\"FCK\" %-->\r\n".toCharArray();
    _jsp_string40 = ">\u5305\u542b\u6587\u4ef6&nbsp;\u6a21\u7248\u53f7<input type=text name=\"tempnum\" size=\"3\" value=\"".toCharArray();
    _jsp_string20 = "\r\n                <option value=2 ".toCharArray();
    _jsp_string38 = "')\"></select>\r\n                &nbsp;<input type=checkbox name=\"isIncluded\" value=1 ".toCharArray();
    _jsp_string14 = "\">\r\n    <input type=hidden name=\"usermodelnewsadd\" id=\"usermodelnewsadd\" >\r\n\r\n    <table width=\"100%\" border=0 cellspacing=0 cellpadding=0>\r\n        <tr bgcolor=#003366>\r\n            <td colspan=3 height=2></td>\r\n        </tr>\r\n        <tr height=30>\r\n            <td width=\"30%\">\u5f53\u524d\u680f\u76ee&nbsp;>&nbsp;".toCharArray();
    _jsp_string15 = "\r\n            </td>\r\n            <td width=\"40%\">".toCharArray();
    _jsp_string30 = ">\u624b\u673a\u4e13\u9898\u6a21\u677f</option>\r\n            </select>\r\n                &nbsp;\u6a21\u677f\u4e2d\u6587\u540d\u79f0\uff1a<input name=\"cname\" size=15 value=\"".toCharArray();
    _jsp_string37 = ",'".toCharArray();
    _jsp_string39 = " checked".toCharArray();
    _jsp_string23 = ">\u624b\u673a\u9996\u9875\u6a21\u677f</option>\r\n                ".toCharArray();
    _jsp_string42 = "</textarea>\r\n                <script type=\"text/javascript\">\r\n                    CKEDITOR.replace('content',{allowedContent: true});\r\n                </script>\r\n            </td>\r\n        </tr>\r\n    </table>\r\n</form>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string28 = ">\u6587\u7ae0\u6a21\u677f</option>\r\n                <option value=3 ".toCharArray();
    _jsp_string16 = "</td>\r\n            <td width=\"30%\" align=right>\r\n                <input class=tine type=button value=\"  \u7f16\u8f91  \" name=\"editbutton\" onclick=\"editMarkInfo();\">&nbsp;&nbsp;\r\n                <input class=tine type=button value=\"  \u4fdd\u5b58  \" name=\"savebutton\" onclick=\"saveModel();\">&nbsp;&nbsp;\r\n                <input class=tine type=button value=\"  \u53d6\u6d88  \" onclick=\"window.location='closewin.jsp?id=".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title>\u4fee\u6539\u6a21\u677f</title>\r\n    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet type=\"text/css\" href=\"../style/editor.css\">\r\n    <script type=\"text/javascript\" src=\"../toolbars/btnclick.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.12.4.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../ckeditor/ckeditor.js\"></script>\r\n    <script language=\"javascript\">\r\n        $(document).ready(function() {\r\n            checkoption(0);\r\n        });\r\n\r\n        function checkoption(id) {\r\n            var para;\r\n            for (var i = 0; i < document.createForm.modelType.length; i++) {\r\n                if (document.createForm.modelType[i].selected)\r\n                    para = document.createForm.modelType[i].value;\r\n            }\r\n\r\n            var param = parseInt(para);\r\n            if(id==1){\r\n                if (param == 0)\r\n                    document.createForm.cname.value = \"\u680f\u76ee\u6a21\u677f\";\r\n                else if (param == 1)\r\n                    document.createForm.cname.value = \"\u6587\u7ae0\u6a21\u677f\";\r\n                else if (param == 2)\r\n                    document.createForm.cname.value = \"\u9996\u9875\u6a21\u677f\";\r\n                else if (param == 3)\r\n                    document.createForm.cname.value = \"\u4e13\u9898\u6a21\u677f\";\r\n                else if (param == 4)\r\n                    document.createForm.cname.value = \"\u624b\u673a\u680f\u76ee\u6a21\u677f\";\r\n                else if (param == 5)\r\n                    document.createForm.cname.value = \"\u624b\u673a\u9996\u9875\u6a21\u677f\";\r\n                else if (param == 6)\r\n                    document.createForm.cname.value = \"\u624b\u673a\u4e13\u9898\u6a21\u677f\";\r\n            }\r\n\r\n            var selectlens = new Array;\r\n            selectlens[0] = 33;\r\n            selectlens[1] = ".toCharArray();
    _jsp_string9 = "\">\r\n    <input type=hidden name=\"template\" value=\"".toCharArray();
    _jsp_string8 = "\">\r\n    <input type=hidden name=\"modelnameBak\" value=\"".toCharArray();
    _jsp_string35 = "\r\n                </option>\r\n                ".toCharArray();
    _jsp_string5 = "&isArticle=".toCharArray();
  }
}
