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

public class _logmanagenew__jsp extends com.caucho.jsp.JavaPage
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/logManagenew.jsp"), -92114519912373911L, false);
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
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  private final static char []_jsp_string3;
  static {
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <title>\u7f51\u7ad9\u5185\u5bb9\u7ba1\u7406\u7cfb\u7edf</title>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n    <link rel=\"stylesheet\" href=\"../design/css/layui.css\">\r\n    <link rel=\"stylesheet\" href=\"../css/mycss.css\">\r\n    <link rel=\"stylesheet\" href=\"../css/cms_css.css\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/themes/default/easyui.css\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"../css/themes/icon.css\">\r\n\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.12.4.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/json2.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../design/layui.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.easyui.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/easyui-lang-zh_CN.js\"></script>\r\n\r\n    <!--\u5f15\u7528xtree-->\r\n    <script type=\"text/javascript\" src=\"../design/layui-xtree.js\"></script>\r\n    <script>\r\n        $(document).ready(function(){\r\n            //programInfoList();\r\n        });\r\n        function goout() {\r\n            window.location.href = \"../exit.jsp\";\r\n        }\r\n\r\n        function goSearchPage(range) {\r\n            var currentPage = form.turnPage.value;\r\n            articlesInfoList(range,currentPage);\r\n            form.turnPage.value = currentPage;\r\n        }\r\n\r\n        function Pagination(totalArticleNum,totalpages,curentpage,range,pageNumLength) {\r\n            var PaginationDiv = \"\";\r\n            if (totalpages <= 1)\r\n                PaginationDiv = \"<div class=\\\"page\\\" style=\\\"display: none\\\">\";\r\n            else\r\n                PaginationDiv = \"<div class=\\\"page\\\" style=\\\"display: block\\\">\";\r\n\r\n            if (curentpage<=1)\r\n                PaginationDiv = PaginationDiv + \"<span class=\\\"txtl\\\">\u5f53\u524d\u680f\u76ee\u7684\u6587\u7ae0\u603b\u6570\uff1a\" + totalArticleNum + \"</span><span>\u7b2c1\u9875</span>\";\r\n            else {\r\n                PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:logInfoList(\" + range + \",1\" + \");\\\">\u7b2c1\u9875</a>\";\r\n            }\r\n\r\n            PaginationDiv = PaginationDiv + \"<span>\u5171\" + totalpages + \"\u9875</span>\";\r\n\r\n            if (curentpage<=1)\r\n                PaginationDiv = PaginationDiv + \"<span>\u4e0a\u4e00\u9875</span>\";\r\n            else {\r\n                PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:logInfoList(\" + range + \",\" + (curentpage - 1) + \");\\\">\u4e0a\u4e00\u9875</a>\";\r\n            }\r\n\r\n            var half_pageNumLength = parseInt(pageNumLength/2);\r\n            var startPageNum=0;\r\n            var endPageNum = 0;\r\n            if (curentpage<=half_pageNumLength)\r\n                startPageNum=0;\r\n            else {\r\n                startPageNum = parseInt(curentpage) - parseInt(half_pageNumLength);\r\n                if (startPageNum<=0) startPageNum = 0;\r\n            }\r\n\r\n            if (curentpage<half_pageNumLength) {\r\n                if (totalpages <= pageNumLength)\r\n                    endPageNum = totalpages;\r\n                else\r\n                    endPageNum = pageNumLength;\r\n            } else {\r\n                if (totalpages <= pageNumLength)\r\n                    endPageNum = totalpages;\r\n                else {\r\n                    endPageNum = parseInt(curentpage) + parseInt(half_pageNumLength);\r\n                    if (endPageNum>totalpages) endPageNum = totalpages;\r\n                }\r\n            }\r\n\r\n            for(var ii=startPageNum;ii<endPageNum;ii++) {\r\n                if ((ii + 1) === curentpage)\r\n                    PaginationDiv = PaginationDiv + \"<span class=\\\"cur\\\">\" + curentpage + \"</span>\";\r\n                else {\r\n                    PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:logInfoList(\" + range + \",\" + (ii + 1) + \");\\\">\" + (ii + 1) + \"</a>\";\r\n                }\r\n            }\r\n\r\n            if (curentpage<totalpages)\r\n                PaginationDiv = PaginationDiv + \"<a href=\\\"javascript:logInfoList(\" + range + \",\" + (curentpage+1) + \");\\\">\u4e0b\u4e00\u9875</a>\";\r\n            else\r\n                PaginationDiv = PaginationDiv + \"<span>\u4e0b\u4e00\u9875</span>\";\r\n\r\n            PaginationDiv = PaginationDiv + \"<span class=\\\"txtl\\\">\u8f6c\u5230\u7b2c</span>\";\r\n            PaginationDiv = PaginationDiv + \"<span class=\\\"select-pager\\\">\";\r\n            PaginationDiv = PaginationDiv + \"<form name=\\\"form\\\">\";\r\n\r\n            PaginationDiv = PaginationDiv + \"<select name=\\\"turnPage\\\" size=\\\"1\\\" onchange=\\\"javascript:goSearchPage(\" + range + \");\\\">\";\r\n\r\n            for(var ii=0;ii<totalpages;ii++) {\r\n                if ((ii+1) === curentpage)\r\n                    PaginationDiv = PaginationDiv + \"<option value=\\\"\" + (ii+1) + \"\\\" selected>\" + (ii+1) +  \"</option>\";\r\n                else\r\n                    PaginationDiv = PaginationDiv + \"<option value=\\\"\" + (ii+1) + \"\\\">\" + (ii+1) + \"</option>\";\r\n            }\r\n\r\n            PaginationDiv = PaginationDiv + \"</select>\";\r\n            PaginationDiv = PaginationDiv + \"</form>\";\r\n            PaginationDiv = PaginationDiv + \"</span>\";\r\n            PaginationDiv = PaginationDiv + \"<span class=\\\"txtr\\\">\u9875</span>\";\r\n            PaginationDiv = PaginationDiv + \"</div>\";\r\n\r\n            return PaginationDiv;\r\n        }\r\n\r\n        function logInfoList(range,currentpage) {\r\n            var siteid = 0;\r\n            var sitename = searchForm.domainname.value;\r\n            if (typeof(sitename) == \"undefined\" || sitename==null || sitename==\"\") {\r\n                alert(\"\u8bf7\u8f93\u5165\u57df\u540d\uff01\uff01\uff01\");\r\n                return;\r\n            } else {\r\n                siteid = getsiteid(sitename);\r\n            }\r\n            var title = searchForm.title.value;\r\n            var spubdate = searchForm.spubdate.value;\r\n            var epubdate = searchForm.epubdate.value;\r\n            var operation = searchForm.op.value;                           //1\u589e\u52a0\u6587\u7ae0 2\u4fee\u6539\u6587\u7ae0 3\u5220\u9664\u6587\u7ae0\r\n            var editor = searchForm.author.value;\r\n            var columns = searchForm.column.value;\r\n            var depts = searchForm.dept.value;\r\n\r\n            if (siteid>0) {\r\n                htmlobj = $.ajax({\r\n                    url: \"logInfo.jsp\",\r\n                    type: \"POST\",\r\n                    data: {\r\n                        range:range,\r\n                        pageno:currentpage,\r\n                        siteid: siteid,\r\n                        spubdate:encodeURIComponent(spubdate),\r\n                        epubdate:encodeURIComponent(epubdate),\r\n                        op:operation,\r\n                        editor:editor,\r\n                        columns:columns,\r\n                        depts:depts,\r\n                        title:encodeURIComponent(title)\r\n                    },\r\n                    dataType: 'json',\r\n                    async: false,\r\n                    success: function (jsondata) {\r\n                        var range = jsondata.RANGE;\r\n                        var currentpage = jsondata.PAGENO;\r\n                        var extra = jsondata.COUNT % range;\r\n                        var totalPage = 0;\r\n                        if (extra>0)\r\n                            totalPage = parseInt(jsondata.COUNT/range) + 1;\r\n                        else\r\n                            totalPage = jsondata.COUNT/range;\r\n\r\n                        var htmldata = \"<table width=\\\"100%\\\" border=\\\"0\\\" cellspacing=\\\"10\\\" cellpadding=\\\"0\\\">\\n\";\r\n                        htmldata = htmldata + \"    <tr>\\n\" +\r\n                            \"        <td align=\\\"left\\\" valign=\\\"top\\\">\\n\" +\r\n                            \"          <form name=form1>\" +\r\n                            \"            <table id=\\\"articleDataGrid\\\" width=\\\"100%\\\" border=\\\"1\\\" cellspacing=\\\"1\\\" cellpadding=\\\"0\\\" bgcolor=\\\"#CCCCCC\\\" class=\\\"tab_list\\\">\\n\" +\r\n                            \"                <tr>\\n\" +\r\n                            \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u7528\u6237\u8d26\u53f7</td>\\n\" +\r\n                            \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u7528\u6237\u540d</td>\\n\" +\r\n                            \"                    <td width=\\\"30%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u5185\u5bb9\u63cf\u8ff0</td>\\n\" +\r\n                            \"                    <td width=\\\"20%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u64cd".toCharArray();
    _jsp_string2 = "\u4f5c\u65f6\u95f4</td>\\n\" +\r\n                            \"                    <td width=\\\"20%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u64cd\u4f5c\u7c7b\u578b</td>\\n\" +\r\n                            \"                    <td width=\\\"10%\\\" align=\\\"center\\\" valign=\\\"middle\\\" bgcolor=\\\"#f4f4f4\\\">\u6240\u5c5e\u90e8\u95e8</td>\\n\" +\r\n                            \"                </tr>\\n\";\r\n                        for(var ii=0;ii<jsondata.DATA.length;ii++) {\r\n                            var logid = jsondata.DATA[ii].ID;\r\n                            var userid = jsondata.DATA[ii].editor;\r\n                            var nickname = jsondata.DATA[ii].editor;\r\n                            var acttime = jsondata.DATA[ii].actTime;\r\n                            var acttype = jsondata.DATA[ii].actType;\r\n                            var department = jsondata.DATA[ii].deptid;\r\n                            var maintitle = jsondata.DATA[ii].maintitle;\r\n\r\n                            var actname = \"\";\r\n                            switch (acttype) {\r\n                                case 1:\r\n                                    actname = \"\u589e\u52a0\u6587\u7ae0\";\r\n                                    break;\r\n                                case 2:\r\n                                    actname = \"\u4fee\u6539\u6587\u7ae0\";\r\n                                    break;\r\n                                case 3:\r\n                                    actname = \"\u5220\u9664\u6587\u7ae0\";\r\n                                    break;\r\n                                case 4:\r\n                                    actname = \"\u53d1\u5e03\u6587\u7ae0\";\r\n                                    break;\r\n                                case 5:\r\n                                    actname = \"\u5ba1\u6838\u6587\u7ae0\";\r\n                                    break;\r\n                                case 6:\r\n                                    actname = \"\u589e\u52a0\u6a21\u677f\";\r\n                                    break;\r\n                                case 7:\r\n                                    actname = \"\u4fee\u6539\u6a21\u677f\";\r\n                                    break;\r\n                                case 8:\r\n                                    actname = \"\u5220\u9664\u6a21\u677f\";\r\n                                    break;\r\n                                case 9:\r\n                                    actname = \"\u53d1\u5e03\u6a21\u677f\";\r\n                                    break;\r\n                                case 10:\r\n                                    actname = \"\u767b\u5f55\u7cfb\u7edf\";\r\n                                    break;\r\n                                case 11:\r\n                                    actname = \"\u521b\u5efa\u7528\u6237\";\r\n                                    break;\r\n                                case 12:\r\n                                    actname = \"\u4fee\u6539\u7528\u6237\";\r\n                                    break;\r\n                                case 13:\r\n                                    actname = \"\u5220\u9664\u7528\u6237\";\r\n                                    break;\r\n                                case 14:\r\n                                    actname = \"\u4fee\u6539\u767b\u5f55\u5bc6\u7801\";\r\n                                    break;\r\n                                case 15:\r\n                                    actname = \"\u9000\u51fa\u7cfb\u7edf\";\r\n                                    break;\r\n                            }\r\n\r\n                            htmldata = htmldata + \"                <tr bgcolor=\\\"#FFFFFF\\\" id=\\\"\" + logid + \"\\\">\\n\";\r\n                            htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + userid + \"</td>\";\r\n                            htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + nickname + \"</td>\";\r\n                            htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + maintitle + \"</td>\";\r\n                            htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + acttime + \"</td>\";\r\n                            htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + actname + \"</td>\";\r\n                            htmldata = htmldata + \"<td align=\\\"center\\\" valign=\\\"middle\\\">\" + \"</td>\";\r\n                            htmldata = htmldata + \"</tr>\";\r\n                        }\r\n\r\n                        htmldata = htmldata + \"            </table>\\n\";\r\n                        htmldata = htmldata + \"         </form>\";\r\n                        htmldata = htmldata + \"        </td>\\n\" +\r\n                            \"    </tr>\\n\" +\r\n                            \"</table>\";\r\n\r\n                        htmldata = htmldata + \"<table width=\\\"100%\\\" border=\\\"0\\\" cellspacing=\\\"0\\\" cellpadding=\\\"0\\\">\";\r\n                        htmldata = htmldata + \"    <tr>\";\r\n                        htmldata = htmldata + \"    <td align='right'>\" + Pagination(jsondata.COUNT,totalPage,currentpage,range,10)+ \"</td>\";\r\n                        htmldata = htmldata + \"    </tr>\";\r\n                        htmldata = htmldata + \"</table>\";\r\n\r\n                        $(\"#contentid\").html(htmldata);\r\n                    },\r\n                    error: function (jqXHR, textStatus, errorThrown) {\r\n                        alert(jqXHR.responseText);\r\n                        alert(jqXHR.status);\r\n                        alert(jqXHR.readyState);\r\n                        alert(jqXHR.statusText);\r\n                        alert(textStatus);\r\n                        alert(errorThrown);\r\n                    }\r\n                });\r\n            }\r\n        }\r\n\r\n        function getColumns() {\r\n            var sitename = searchForm.domainname.value;\r\n            alert(\"hello columns==\" + sitename);\r\n            var iWidth=800;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=600;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            //window.open(\"paddprogram.jsp\",\"\u589e\u52a0\u7a0b\u5e8f\u6a21\u5757\",\"top=\"+ iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function getDepts() {\r\n            var sitename = searchForm.domainname.value;\r\n            alert(\"hello depts==\" + sitename);\r\n            var iWidth=800;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=600;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            //window.open(\"pedit.jsp?id=\" + progid,\"\u4fee\u6539\u7a0b\u5e8f\u6a21\u5757\",\"top=\" + iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function getEditors() {\r\n            var sitename = searchForm.domainname.value;\r\n            alert(\"hello editors==\" + sitename);\r\n            var iWidth=800;                                                 //\u5f39\u51fa\u7a97\u53e3\u7684\u5bbd\u5ea6;\r\n            var iHeight=600;                                                //\u5f39\u51fa\u7a97\u53e3\u7684\u9ad8\u5ea6;\r\n            var iTop = (window.screen.availHeight-30-iHeight)/2;        //\u83b7\u5f97\u7a97\u53e3\u7684\u5782\u76f4\u4f4d\u7f6e;\r\n            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //\u83b7\u5f97\u7a97\u53e3\u7684\u6c34\u5e73\u4f4d\u7f6e;\r\n            //window.open(\"premove.jsp?id=\" + progid,\"\u5220\u9664\u7a0b\u5e8f\u6a21\u5757\",\"top=\" + iTop + \",left=\" + iLeft + \",width=\" + iWidth + \",height=\" + iHeight + \",resizable=yes,scrollbars=yes\");\r\n        }\r\n\r\n        function getsiteid(domainname) {\r\n            var siteid = 0;\r\n            htmlobj=$.ajax({\r\n                url:\"getSiteidBySitename.jsp\",\r\n                type:\"POST\",\r\n                data:{\r\n                    sitename:domainname\r\n                },\r\n                dataType:'json',\r\n                async:false,\r\n                success:function(jsondata){\r\n                    siteid = jsondata.SITEID;\r\n                },\r\n                error: function (jqXHR, textStatus, errorThrown) {\r\n                    alert(jqXHR.responseText);\r\n                    alert(jqXHR.status);\r\n                    alert(jqXHR.readyState);\r\n                    alert(jqXHR.statusText);\r\n                    alert(textStatus);\r\n                    alert(errorThrown);\r\n                }\r\n            });\r\n\r\n            return siteid;\r\n        }\r\n    </script>\r\n</head>\r\n<body class=\"layui-layout-bod".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string3 = "y\">\r\n<div class=\"layui-layout layui-layout-admin\">\r\n    <div class=\"layui-header\">\r\n        <div class=\"layui-logo\">WCMS--\u7ad9\u70b9\u7ba1\u7406</div>\r\n        <ul class=\"layui-nav layui-layout-left\">\r\n            <li class=\"layui-nav-item\"><a href=\"../managesites.jsp\">\u7528\u6237\u7ba1\u7406</a></li>\r\n            <li class=\"layui-nav-item\"><a href=\"../member/siteManagenew.jsp\">\u7ad9\u70b9\u7ba1\u7406</a></li>\r\n            <li class=\"layui-nav-item\"><a href=\"../program/index.jsp\">\u7a0b\u5e8f\u7ba1\u7406</a></li>\r\n            <li class=\"layui-nav-item\"><a href=\"../member/listStylenew.jsp\">\u5217\u8868\u6837\u5f0f\u7ba1\u7406</a></li>\r\n            <!--li class=\"layui-nav-item\"><a href=\"\">\u6587\u7ae0\u79fb\u52a8</a></li-->\r\n            <li class=\"layui-nav-item\"><a href=\"../member/logManagenew.jsp\">\u5de5\u4f5c\u91cf\u7ba1\u7406</a></li>\r\n        </ul>\r\n        <ul class=\"layui-nav layui-layout-right\">\r\n            <li class=\"layui-nav-item\">\r\n                <dl class=\"layui-nav-child\">\r\n                    <dd><a href=\"\">\u57fa\u672c\u8d44\u6599</a></dd>\r\n                    <dd><a href=\"\">\u5b89\u5168\u8bbe\u7f6e</a></dd>\r\n                </dl>\r\n            </li>\r\n            <li class=\"layui-nav-item\"><a href=\"javascript:goout();\">\u9000\u4e86</a></li>\r\n        </ul>\r\n    </div>\r\n\r\n    <div class=\"layui-body\" style=\"left: 0px;top:65px;\">\r\n        <!--div id=\"maincontent\" class=\"location\" style=\"padding-bottom: 5px;display: none;\"-->\r\n        <div id=\"maincontent\" class=\"location\" style=\"padding-bottom: 5px;\">\r\n            <div>\u5de5\u4f5c\u91cf\u7edf\u8ba1\u5206\u6790 ></div>\r\n            <div style=\"padding-top: 10px;padding-bottom: 10px;\">\r\n                <form name=\"searchForm\">\r\n                    \u7ad9\u70b9\u57df\u540d\uff1a<input type=\"text\" name=\"domainname\" id=\"domainnameid\" value=\"\"  class=\"sear_txt_1\" />\r\n                    \u7ad9\u70b9\u680f\u76ee\uff1a<input type=\"text\" name=\"column\" id=\"columnid\" value=\"\" placeholder=\"\u53cc\u51fb\u9009\u62e9\u680f\u76ee\"  class=\"sear_txt_1\" ondblclick=\"javascript:getColumns();\"/>\r\n                    \u6807\u9898\uff1a<input type=\"text\" name=\"title\" id=\"titleid\" value=\"\"  class=\"sear_txt_1\" />\r\n                    \u90e8\u95e8\uff1a<input type=\"text\" name=\"dept\" id=\"deptid\" value=\"\" placeholder=\"\u53cc\u51fb\u9009\u62e9\u90e8\u95e8\" class=\"sear_txt_1\" ondblclick=\"javascript:getDepts();\"/>\r\n                    \u6587\u7ae0\u4f5c\u8005\uff1a<input type=\"text\" name=\"author\" id=\"authorid\" value=\"\" placeholder=\"\u53cc\u51fb\u9009\u62e9\u4f5c\u8005\" class=\"sear_txt_1\" ondblclick=\"javascript:getEditors();\"/>\r\n                    \u53d1\u5e03\u65e5\u671f\uff1a<input type=\"text\" name=\"spubdate\" id=\"sdate\" class=\"datetime_txt_1\" />\u5230\r\n                    <input type=\"text\" name=\"epubdate\" id=\"edate\" class=\"datetime_txt_1\" />\r\n                    \u64cd\u4f5c\uff1a<select name=\"op\" id=\"opid\" class=\"sear_txt_1\">\r\n                    <option value=\"0\">\u6240\u6709</option>\r\n                    <option value=\"1\">\u589e\u52a0\u6587\u7ae0</option>\r\n                    <option value=\"2\">\u4fee\u6539\u6587\u7ae0</option>\r\n                    <option value=\"3\">\u5220\u9664\u6587\u7ae0</option>\r\n                    <option value=\"4\">\u53d1\u5e03\u6587\u7ae0</option>\r\n                    <option value=\"5\">\u5ba1\u6838\u6587\u7ae0</option>\r\n                    <option value=\"6\">\u589e\u52a0\u6a21\u677f</option>\r\n                    <option value=\"7\">\u4fee\u6539\u6a21\u677f</option>\r\n                    <option value=\"8\">\u5220\u9664\u6a21\u677f</option>\r\n                    <option value=\"9\">\u53d1\u5e03\u6a21\u677f</option>\r\n                    <option value=\"10\">\u767b\u5f55\u7cfb\u7edf</option>\r\n                    <option value=\"11\">\u521b\u5efa\u7528\u6237</option>\r\n                    <option value=\"12\">\u4fee\u6539\u7528\u6237</option>\r\n                    <option value=\"13\">\u5220\u9664\u7528\u6237</option>\r\n                    <option value=\"14\">\u4fee\u6539\u767b\u5f55\u5bc6\u7801</option>\r\n                </select>\r\n                    <input type=\"button\" name=\"search\" id=\"searchid\" value=\"\u641c\u7d22\" onclick=\"javascript:logInfoList(20,1);\" class=\"sear_btn\"/>\r\n                </form>\r\n            </div>\r\n        </div>\r\n\r\n        <!-- \u5185\u5bb9\u4e3b\u4f53\u533a\u57df -->\r\n        <div id=\"contentid\" style=\"left: 0px;padding-top:30px;\"></div>\r\n    </div>\r\n\r\n    <div class=\"layui-footer\" style=\"left: 0px;\">\r\n        \u7248\u6743\u4fe1\u606f\uff1a \u00a9 2003-2019 \u5317\u4eac\u6f6e\u8679\u4f1f\u4e1a\u79d1\u6280\u6709\u9650\u516c\u53f8\uff0c\u7248\u6743\u6240\u6709\u3002\r\n    </div>\r\n</div>\r\n<script>\r\n    layui.use(['element', 'tree', 'laydate'], function(){\r\n        var laydate = layui.laydate;\r\n        laydate.render({\r\n            elem: '#sdate', //\u6307\u5b9a\u5143\u7d20\r\n            type:\"datetime\"\r\n        });\r\n\r\n        laydate.render({\r\n            elem: '#edate', //\u6307\u5b9a\u5143\u7d20\r\n            type:\"datetime\"\r\n        });\r\n    });\r\n</script>\r\n</body>\r\n</html>\r\n".toCharArray();
  }
}
