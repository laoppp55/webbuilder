/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.security.Auth;
import java.text.SimpleDateFormat;

public class _sitemanagenew__jsp extends com.caucho.jsp.JavaPage
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
    response.setContentType("text/html;charset=UTF-8");

    out.write(_jsp_string0, 0, _jsp_string0.length);
    
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }
    int siteid = authToken.getSiteID();

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.write(_jsp_string3, 0, _jsp_string3.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/siteManagenew.jsp"), 6095287160939072654L, false);
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

  private final static char []_jsp_string3;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  private final static char []_jsp_string1;
  static {
    _jsp_string3 = "var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            window.open(\"../upload/uploadsitepic.jsp?site=\"+siteid + \"&pageno=\" + currentpage + \"&range=\"+range + \"&searchflag=\" + doSearchFlag, \"\", \"top=\"+iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight);\r\n        }\r\n\r\n        function setupsite_to_template(siteid,validflag,resultnum,currentpage,doSearchFlag) {\r\n            var iWidth=600;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=400;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            window.open(\"setsitetype.jsp?site=\"+siteid + \"&pageno=\" + currentpage + \"&range=\"+resultnum + \"&searchflag=\" + doSearchFlag, \"\", \"top=\" + iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight);\r\n        }\r\n    </script>\r\n</head>\r\n<body class=\"layui-layout-body\">\r\n<div class=\"layui-layout layui-layout-admin\">\r\n    <div class=\"layui-header\">\r\n        <div class=\"layui-logo\">WCMS--\u7ad9\u70b9\u7ba1\u7406</div>\r\n        <ul class=\"layui-nav layui-layout-left\">\r\n            <li class=\"layui-nav-item\"><a href=\"../managesites.jsp\">\u7528\u6237\u7ba1\u7406</a></li>\r\n            <li class=\"layui-nav-item\"><a href=\"../member/siteManagenew.jsp\">\u7ad9\u70b9\u7ba1\u7406</a></li>\r\n            <li class=\"layui-nav-item\"><a href=\"../program/index.jsp\">\u7a0b\u5e8f\u7ba1\u7406</a></li>\r\n            <li class=\"layui-nav-item\"><a href=\"../member/listStylenew.jsp\">\u5217\u8868\u6837\u5f0f\u7ba1\u7406</a></li>\r\n            <!--li class=\"layui-nav-item\"><a href=\"\">\u6587\u7ae0\u79fb\u52a8</a></li-->\r\n            <li class=\"layui-nav-item\"><a href=\"../member/logManagenew.jsp\">\u5de5\u4f5c\u91cf\u7ba1\u7406</a></li>\r\n        </ul>\r\n        <ul class=\"layui-nav layui-layout-right\">\r\n            <li class=\"layui-nav-item\">\r\n                <dl class=\"layui-nav-child\">\r\n                    <dd><a href=\"\">\u57fa\u672c\u8d44\u6599</a></dd>\r\n                    <dd><a href=\"\">\u5b89\u5168\u8bbe\u7f6e</a></dd>\r\n                </dl>\r\n            </li>\r\n            <li class=\"layui-nav-item\"><a href=\"javascript:goout();\">\u9000\u4e86</a></li>\r\n        </ul>\r\n    </div>\r\n\r\n    <div class=\"layui-side layui-bg-black\" id=\"sidetreeid\" style=\"top: 60px;display: none;\">\r\n        <div id=\"treebox\" style=\"overflow-x:auto;\">\r\n            <ul id=\"mytree\"></ul>\r\n        </div>\r\n    </div>\r\n\r\n    <div class=\"layui-body\" style=\"left: 0px;top:80px;\">\r\n        <!-- \u5185\u5bb9\u4e3b\u4f53\u533a\u57df -->\r\n        <div id=\"sousuoid\" style=\"padding-top: 10px;padding-bottom: 10px;display: none;\">\r\n            <form name=\"searchForm\">\r\n                \u7ad9\u70b9\u540d\u79f0\uff1a<input type=\"text\" name=\"search\" id=\"searchcontentid\" class=\"sear_txt_1\" size=\"50\">\r\n                <input type=\"hidden\" name=\"range\" value=\"\">\r\n                <input type=\"hidden\" name=\"pageno\" value=\"\">\r\n                <input type=\"hidden\" name=\"searchflag\" value=\"\">\r\n                <input type=\"button\" name=\"dosearch\" id=\"searchid\" value=\"\u641c\u7d22\" onclick=\"javascript:userInfoList(15,1,0);\" class=\"sear_btn\"/>\r\n            </form>\r\n        </div>\r\n        <div id=\"addsiteid\" style=\"float: right\">\r\n            <input name=\"batchDel\" type=\"button\" value=\"\u589e\u52a0\u7ad9\u70b9\" class=\"button_bg_3\" onclick='javascript:create();'/>\r\n        </div>\r\n\r\n        <div id=\"contentid\">\r\n        </div>\r\n    </div>\r\n\r\n    <div class=\"layui-footer\" style=\"left: 0px;\">\r\n        \u7248\u6743\u4fe1\u606f\uff1a \u00a9 2003-2019 \u5317\u4eac\u6f6e\u8679\u4f1f\u4e1a\u79d1\u6280\u6709\u9650\u516c\u53f8\uff0c\u7248\u6743\u6240\u6709\u3002\r\n    </div>\r\n</div>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string2 = "e\\\" bgcolor=\\\"#f4f4f4\\\">\u8bbe\u4e3a\u6a21\u677f</td>\\n\" +\r\n                        \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u9996\u9875\u56fe</td>\\n\" +\r\n                        \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u53d1\u5e03\u4e3b\u673a</td>\\n\" +\r\n                        \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u4fee\u6539</td>\\n\" +\r\n                        \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u5220\u9664</td>\\n\" +\r\n                        \"                </tr>\\n\";\r\n                    for(var ii=0;ii<jsondata.DATA.length;ii++) {\r\n                        var siteid = jsondata.DATA[ii].siteid;\r\n                        var sitename = jsondata.DATA[ii].domainName;\r\n                        var pubflag = jsondata.DATA[ii].pubFlag;\r\n                        var tcflag = jsondata.DATA[ii].tcFlag;\r\n                        var validflag = jsondata.DATA[ii].validflag;\r\n                        var sitepic = jsondata.DATA[ii].sitepic\r\n                        var pubway = \"\";\r\n                        if (pubflag==0)\r\n                            pubway=\"\u624b\u52a8\u53d1\u5e03\";\r\n                        else\r\n                            pubway=\" \u81ea\u52a8\u53d1\u5e03\";\r\n\r\n                        var trad = \"\";                   //\u662f\u5426\u53d1\u5e03\u7e41\u4f53\u7248\r\n                        if (tcflag == 0)\r\n                            trad = \"\u4e0d\u53d1\u5e03\";\r\n                        else\r\n                            trad = \"\u53d1\u5e03\";\r\n\r\n                        var sitetype=\"\";\r\n                        if (validflag == 0)\r\n                            sitetype = \"<font color=\\\"red\\\">\u6a21\u677f\u7f51\u7ad9</font>\";\r\n                        else if (validflag == 1)\r\n                            sitetype = \"\u666e\u901a\u7f51\u7ad9\";\r\n                        else if (validflag == 2)\r\n                            sitetype = \"<font color=\\\"green\\\">\u5171\u4eab\u7f51\u7ad9</font>\";\r\n                        else\r\n                            sitetype = \"\u672a\u77e5\u7c7b\u578b\";\r\n\r\n                        var sitepicurl = \"\";\r\n                        if (sitepic != null)\r\n                            sitepicurl = \"<font color=\\\"red\\\">\u9996\u9875\u56fe</font>\";\r\n                        else\r\n                            sitepicurl = \"\u9996\u9875\u56fe\";\r\n\r\n                        htmldata = htmldata + \"                <tr bgcolor=\\\"#FFFFFF\\\" id=\\\"\" + siteid + \"\\\">\\n\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + sitename + \"</td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + pubway + \"</td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + trad + \"</td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\"><a href=\\\"javascript:setupsite_to_template(\" + siteid + \",\"+ validflag + \",\" + resultnum + \",\" + currentpage + \",\" + doSearchFlag + \")\\\">\" + sitetype + \"</a></td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\"><a href=\\\"javascript:uploadsitepic(\" + siteid + \",\" + resultnum + \",\" + currentpage + \",\" + doSearchFlag + \");\\\">\" + sitepicurl + \"</a></td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\"><input type=\\\"radio\\\" name=\\\"action\\\" onclick=\\\"javascript:siteIPManage(\" + siteid + \");\\\"></td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\"><input type=\\\"radio\\\" name=\\\"action\\\" onclick=\\\"javascript:edit(\" + siteid + \",\" + resultnum + \",\" + currentpage + \",\" + doSearchFlag + \");\\\"></td>\";\r\n                        htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\"><input type=\\\"radio\\\" name=\\\"action\\\" onclick=\\\"javascript:removeSite(\" + siteid + \",'\" + sitename + \"',\" + resultnum + \",\" + currentpage + \",\" + doSearchFlag + \");\\\"></td>\";\r\n                        htmldata = htmldata + \"</tr>\";\r\n                    }\r\n\r\n                    htmldata = htmldata + \"            </table>\\n\";\r\n                    htmldata = htmldata + \"         </form>\";\r\n                    htmldata = htmldata + \"        </td>\\n\" +\r\n                        \"    </tr>\\n\" +\r\n                        \"</table>\";\r\n\r\n                    htmldata = htmldata + \"<table width=\\\"100%\\\" border=\\\"0\\\" cellspacing=\\\"0\\\" cellpadding=\\\"0\\\">\";\r\n                    htmldata = htmldata + \"    <tr>\";\r\n                    htmldata = htmldata + \"    <td align='right'>\" + Pagination(totalNum,totalPage,pageNumLength,resultnum,currentpage,doSearchFlag)+ \"</td>\";\r\n                    htmldata = htmldata + \"    </tr>\";\r\n                    htmldata = htmldata + \"</table>\";\r\n                    $(\"#sousuoid\").css(\"display\",\"block\");\r\n                    $(\"#contentid\").html(htmldata);\r\n                },\r\n                error: function (jqXHR, textStatus, errorThrown) {\r\n                    alert(jqXHR.responseText);\r\n                    alert(jqXHR.status);\r\n                    alert(jqXHR.readyState);\r\n                    alert(jqXHR.statusText);\r\n                    alert(textStatus);\r\n                    alert(errorThrown);\r\n                }\r\n            });\r\n        }\r\n\r\n        function create() {\r\n            var range = searchForm.range.value;\r\n            var currentpage = searchForm.pageno.value;\r\n            var doSearchFlag = searchForm.searchflag.value;\r\n            var iWidth=1200;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=800;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            window.open(\"addsite.jsp?range=\" + range + \"&pageno=\" + currentpage + \"&searchflag=\" + doSearchFlag, \"\", \"top=\"+ iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function edit(siteid,range,currentpage,doSearchFlag) {\r\n            var iWidth=1200;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=800;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            window.open(\"editSite.jsp?siteid=\" + siteid + \"&range=\"+range + \"&pageno=\" + currentpage + \"&searchflag=\" + doSearchFlag, \"EditSiteWin\", \"top=\" + iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function removeSite(siteid,sitename,range,currentpage,doSearchFlag) {\r\n            var iWidth=1200;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=800;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            window.open(\"removeSite.jsp?siteid=\" + siteid + \"&dname=\"+sitename + \"&range=\"+range + \"&pageno=\" + currentpage + \"&searchflag=\" + doSearchFlag, \"\", \"top=\" + iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function siteIPManage(siteid) {\r\n            var iWidth=1200;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=800;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            window.open(\"siteIPManage.jsp?siteid=\" + siteid, \"\", \"top=\" + iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function uploadsitepic(siteid,range,currentpage,doSearchFlag) {\r\n            var iWidth=600;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=400;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            ".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <title>\u7f51\u7ad9\u5185\u5bb9\u7ba1\u7406\u7cfb\u7edf</title>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n    <link rel=\"stylesheet\" href=\"../design/css/layui.css\">\r\n    <link rel=\"stylesheet\" href=\"../css/mycss.css\">\r\n    <link rel=\"stylesheet\" href=\"../css/cms_css.css\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/themes/default/easyui.css\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/themes/icon.css\">\r\n\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.12.4.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/json2.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../design/layui.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.easyui.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/easyui-lang-zh_CN.js\"></script>\r\n\r\n    <!--\u5f15\u7528xtree-->\r\n    <script type=\"text/javascript\" src=\"../design/layui-xtree.js\"></script>\r\n    <script>\r\n        $(document).ready(function(){\r\n            layui.use('element', function(){\r\n                var element = layui.element;\r\n            });\r\n            userInfoList(15,1,-1);\r\n        });\r\n        function goout() {\r\n            window.location.href = \"../exit.jsp\";\r\n        }\r\n\r\n        function goSearchPage(range,currentpage,doSearchFlag) {\r\n            var currentPage = form.turnPage.value;\r\n            userInfoList(range,currentpage,doSearchFlag);\r\n            form.turnPage.value = currentPage;\r\n        }\r\n\r\n        function Pagination(totalNum,totalpages,pageNumLength,range,currentpage,doSearchFlag) {\r\n            var PaginationDiv = \"\";\r\n            if (totalpages <= 1)\r\n                PaginationDiv = \"<div class=\\\"page\\\" style=\\\"display: none\\\">\";\r\n            else\r\n                PaginationDiv = \"<div class=\\\"page\\\" style=\\\"display: block\\\">\";\r\n\r\n            if (currentpage<=1)\r\n                PaginationDiv = PaginationDiv + \"<span class=\\\"txtl\\\">\u7528\u6237\u603b\u6570\uff1a\" + totalNum + \"</span><span>\u7b2c1\u9875</span>\";\r\n            else {\r\n                PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:userInfoList(\" + range + \",\" + currentpage + \",\" + doSearchFlag + \");\\\">\u7b2c1\u9875</a>\";\r\n            }\r\n\r\n            PaginationDiv = PaginationDiv + \"<span>\u5171\" + totalpages + \"\u9875</span>\";\r\n\r\n            if (currentpage<=1)\r\n                PaginationDiv = PaginationDiv + \"<span>\u4e0a\u4e00\u9875</span>\";\r\n            else {\r\n                PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:userInfoList(\" + range + \",\" + (currentpage - 1) + \",\" + doSearchFlag + \");\\\">\u4e0a\u4e00\u9875</a>\";\r\n            }\r\n\r\n            var half_pageNumLength = parseInt(pageNumLength/2);\r\n            var startPageNum=0;\r\n            var endPageNum = 0;\r\n            if (currentpage<=half_pageNumLength)\r\n                startPageNum=0;\r\n            else {\r\n                startPageNum = parseInt(currentpage) - parseInt(half_pageNumLength);\r\n                if (startPageNum<=0) startPageNum = 0;\r\n            }\r\n\r\n            if (currentpage<half_pageNumLength) {\r\n                if (totalpages <= pageNumLength)\r\n                    endPageNum = totalpages;\r\n                else\r\n                    endPageNum = pageNumLength;\r\n            } else {\r\n                if (totalpages <= pageNumLength)\r\n                    endPageNum = totalpages;\r\n                else {\r\n                    endPageNum = parseInt(currentpage) + parseInt(half_pageNumLength);\r\n                    if (endPageNum>totalpages) endPageNum = totalpages;\r\n                }\r\n            }\r\n\r\n            for(var ii=startPageNum;ii<endPageNum;ii++) {\r\n                if ((ii + 1) === currentpage)\r\n                    PaginationDiv = PaginationDiv + \"<span class=\\\"cur\\\">\" + currentpage + \"</span>\";\r\n                else {\r\n                    PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:userInfoList(\" + range + \",\" + (ii + 1) + \",\" + doSearchFlag + \");\\\">\" + (ii + 1) +  \"</a>\";\r\n                }\r\n            }\r\n\r\n            if (currentpage<totalpages)\r\n                PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:userInfoList(\" + range + \",\" + (currentpage+1) + \",\" + doSearchFlag + \");\\\">\u4e0b\u4e00\u9875</a>\";\r\n            else\r\n                PaginationDiv = PaginationDiv + \"<span>\u4e0b\u4e00\u9875</span>\";\r\n\r\n            PaginationDiv = PaginationDiv + \"<span class=\\\"txtl\\\">\u8f6c\u5230\u7b2c</span>\";\r\n            PaginationDiv = PaginationDiv + \"<span class=\\\"select-pager\\\">\";\r\n            PaginationDiv = PaginationDiv + \"<form name=\\\"form\\\">\";\r\n\r\n            PaginationDiv = PaginationDiv + \"<select name=\\\"turnPage\\\" size=\\\"1\\\" onchange=\\\"javascript:goSearchPage(\" + range + \",'\" + currentpage + \",\" + doSearchFlag +  \");\\\">\";\r\n\r\n            for(var ii=0;ii<totalpages;ii++) {\r\n                if ((ii+1) === currentpage)\r\n                    PaginationDiv = PaginationDiv + \"<option value=\\\"\" + (ii+1) + \"\\\" selected>\" + (ii+1) +  \"</option>\";\r\n                else\r\n                    PaginationDiv = PaginationDiv + \"<option value=\\\"\" + (ii+1) + \"\\\">\" + (ii+1) + \"</option>\";\r\n            }\r\n\r\n            PaginationDiv = PaginationDiv + \"</select>\";\r\n            PaginationDiv = PaginationDiv + \"</form>\";\r\n            PaginationDiv = PaginationDiv + \"</span>\";\r\n            PaginationDiv = PaginationDiv + \"<span class=\\\"txtr\\\">\u9875</span>\";\r\n            PaginationDiv = PaginationDiv + \"</div>\";\r\n\r\n            return PaginationDiv;\r\n        }\r\n\r\n        function userInfoList(resultnum,currentpage,doSearchFlag) {\r\n            var searchcontent = $(\"#searchcontentid\").val();\r\n            var searchflag = $(\"input[name='namesearch']:checked\").val();\r\n            if (doSearchFlag==-1)\r\n                searchflag = -1;\r\n            else {\r\n                if (typeof(searchflag) == \"undefined\") alert(\"\u8bf7\u9009\u62e9\u67e5\u8be2\u65b9\u5f0f\");\r\n                if (searchcontent==null) alert(\"\u8bf7\u8f93\u5165\u67e5\u8be2\u5173\u952e\u5b57\");\r\n            }\r\n\r\n            var pageNumLength = 10;   //\u9875\u7801\u7684\u957f\u5ea6\u4e3a10\r\n            htmlobj=$.ajax({\r\n                url:\"../member/siteManage.jsp\",\r\n                type:\"POST\",\r\n                data:{\r\n                    resultnum:resultnum,\r\n                    pageno:currentpage,\r\n                    searchflag:searchflag,                              //\u5bf9\u90a3\u4e2a\u5b57\u6bb5\u6267\u884c\u68c0\u7d22\u64cd\u4f5c\r\n                    doSearch:doSearchFlag,                              //\u662f\u5426\u6267\u884c\u68c0\u7d22\u64cd\u4f5c\r\n                    search:searchcontent\r\n                },\r\n                dataType:'json',\r\n                async:false,\r\n                success:function(jsondata){\r\n                    var currentpage = jsondata.PAGENO;\r\n                    var doSearchFlag = jsondata.DOSEARCH;\r\n                    var range = jsondata.RANGE;\r\n\r\n                    searchForm.range.value=range;\r\n                    searchForm.pageno.value=currentpage;\r\n                    searchForm.searchflag.value=doSearchFlag;\r\n\r\n                    var totalNum = jsondata.COUNT;\r\n                    var extra = jsondata.COUNT % resultnum;\r\n                    var totalPage = 0;\r\n                    if (extra>0)\r\n                        totalPage = parseInt(jsondata.COUNT/resultnum) + 1;\r\n                    else\r\n                        totalPage = parseInt(jsondata.COUNT/resultnum);\r\n\r\n                    var htmldata = \"<table width=\\\"100%\\\" border=\\\"0\\\" cellspacing=\\\"10\\\" cellpadding=\\\"0\\\">\\n\";\r\n                    htmldata = htmldata + \"    <tr>\\n\" +\r\n                        \"        <td align=\\\"left\\\" valign=\\\"top\\\">\\n\" +\r\n                        \"          <form name=form1>\" +\r\n                        \"            <table id=\\\"articleDataGrid\\\" width=\\\"100%\\\" border=\\\"1\\\" cellspacing=\\\"1\\\" cellpadding=\\\"0\\\" bgcolor=\\\"#CCCCCC\\\" class=\\\"tab_list\\\">\\n\" +\r\n                        \"                <tr>\\n\" +\r\n                        \"                    <td width=\\\"20%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u7ad9\u70b9\u540d\u79f0</td>\\n\" +\r\n                        \"                    <td width=\\\"20%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u53d1\u5e03\u65b9\u5f0f</td>\\n\" +\r\n                        \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u7e41\u4f53\u7248</td>\\n\" +\r\n                        \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middl".toCharArray();
  }
}