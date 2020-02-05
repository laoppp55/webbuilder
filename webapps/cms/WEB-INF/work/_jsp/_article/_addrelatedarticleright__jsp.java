/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._article;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;

public class _addrelatedarticleright__jsp extends com.caucho.jsp.JavaPage
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
    int siteid=authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    if(columnID==-1)
    {
        columnID=0;
    }
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");
    int from = ParamUtil.getIntParameter(request, "from", 0);
    int param = ParamUtil.getIntParameter(request, "param", -1);

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    IArticleManager articleMgr = ArticlePeer.getInstance();
    int total = articleMgr.getRelatedArticlesNum(columnID,siteid);
    List articleList = articleMgr.getRelatedArticles(columnID, start, range,siteid);
    int articleCount = articleList.size();


    out.write(_jsp_string1, 0, _jsp_string1.length);
    if(from == 0){
    out.write(_jsp_string2, 0, _jsp_string2.length);
    }else{
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((param));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    }
    out.write(_jsp_string5, 0, _jsp_string5.length);
    
    if (msg != null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=addRelatedArticleRight.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=addRelatedArticleRight.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }

    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((CName));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    
            for (int i = 0; i < articleCount; i++) {
                Article article = (Article) articleList.get(i);

                int articleID = article.getID();       //\u6587\u7ae0ID\u6216\u6a21\u677fID
                String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
                String lastUpdated = article.getPublishTime().toString().substring(0, 10); //\u6587\u7ae0\u6216\u6a21\u677f\u7684\u6700\u540e\u4fee\u6539\u65f6\u95f4
                String maintitle = StringUtil.gb2iso4View(article.getMainTitle());

                out.println("<tr bgcolor=" + bgcolor + "class=itm>");
                out.println("<td align=center><input type=radio id=articleID name=articleID value='" + articleID + "," + CName + "," + maintitle + "'></td>");
                out.println("<td>" + articleID + "</td>");
                out.println("<td>" + maintitle + "</td>");
                out.println("<td>" + lastUpdated + "</td>");
                out.println("<td align=center>");
                if (article.getNullContent() == 0)
                    out.println("<a href=javascript:PreviewArticle(" + articleID + ");><img src=../images/preview.gif border=0></a>");
                else
                    out.println("<img src=../images/preview.gif border=0>");
                out.println("</td></tr>");
            }
        
    out.write(_jsp_string8, 0, _jsp_string8.length);
    out.print((articleCount));
    out.write(_jsp_string9, 0, _jsp_string9.length);
    if(articleCount==0){
    out.write(_jsp_string10, 0, _jsp_string10.length);
    }
    out.write(_jsp_string11, 0, _jsp_string11.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("article/addRelatedArticleRight.jsp"), -3110968839766136504L, false);
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
  private final static char []_jsp_string3;
  private final static char []_jsp_string9;
  private final static char []_jsp_string5;
  private final static char []_jsp_string6;
  private final static char []_jsp_string11;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  private final static char []_jsp_string4;
  private final static char []_jsp_string7;
  private final static char []_jsp_string8;
  private final static char []_jsp_string0;
  static {
    _jsp_string10 = "disabled".toCharArray();
    _jsp_string3 = "\r\n    function SelectArticle(len) {\r\n        var aid;\r\n        if (len == 1) {\r\n            if (!document.getElementById('articleID').checked)\r\n                return;\r\n            else\r\n                aid = document.getElementById('articleID').value;\r\n        } else if (len > 1) {\r\n            var i = 0;\r\n            var isSelected = false;\r\n\r\n            for(var i=0;i<len;i++){\r\n                if(document.getElementsByName('articleID')[i].checked){\r\n                    aid = document.getElementsByName('articleID')[i].value;\r\n                    isSelected = true;\r\n                    break;\r\n                }\r\n            }\r\n            if (!isSelected) return;\r\n        }\r\n\r\n        var param = ".toCharArray();
    _jsp_string9 = ");\" ".toCharArray();
    _jsp_string5 = "\r\n    function cal() {\r\n        top.close();\r\n    }\r\n</script>\r\n\r\n<BODY BGCOLOR=\"#ffffff\" LINK=\"#000099\" ALINK=\"#cc0000\" VLINK=\"#000099\" TOMARGIN=8>\r\n".toCharArray();
    _jsp_string6 = "\r\n<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=\"100%\">\r\n    <form action=\"\" name=\"myform\">\r\n        <tr>\r\n            <td colspan=5>\u5f53\u524d\u6240\u5728\u680f\u76ee-->><font color=red>".toCharArray();
    _jsp_string11 = ">&nbsp;&nbsp;\r\n    <input type=button value=\"  \u53d6\u6d88  \" onclick=\"cal();\">\r\n</p>\r\n</BODY>\r\n</html>".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title></title>\r\n    <meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet type=text/css href=\"../style/global.css\">\r\n</head>\r\n<script language=\"JavaScript\">\r\n    function PreviewArticle(articleID) {\r\n        window.open(\"../article/preview.jsp?article=\" + articleID, \"Preview\", \"width=800,height=600,left=0,top=0,scrollbars\");\r\n    }\r\n\r\n    ".toCharArray();
    _jsp_string2 = "\r\n    function SelectArticle(len) {\r\n        var aid = document.forms[\"myform\"].articleID;\r\n        if (len == 1) {\r\n            var retval = aid.value;\r\n            window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"articleID\").innerHTML = retval.substring(0, retval.indexOf(\",\"));\r\n            window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"cname\").innerHTML = retval.substring(retval.indexOf(\",\") + 1, retval.lastIndexOf(\",\"));\r\n            window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"maintitle\").innerHTML = retval.substring(retval.lastIndexOf(\",\") + 1);\r\n            window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"article\").value = retval.substring(0, retval.indexOf(\",\"));\r\n        } else if (len > 1) {\r\n            var i = 0;\r\n            var isSelected = false;\r\n            for (i = 0; i < len; i++) {\r\n                if (aid[i].checked) {\r\n                    isSelected = true;\r\n                    break;\r\n                }\r\n            }\r\n            if (!isSelected)\r\n                return;\r\n            else {\r\n                var retval = aid[i].value;\r\n                window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"articleID\").innerHTML = retval.substring(0, retval.indexOf(\",\"));\r\n                window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"cname\").innerHTML = retval.substring(retval.indexOf(\",\") + 1, retval.lastIndexOf(\",\"));\r\n                window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"maintitle\").innerHTML = retval.substring(retval.lastIndexOf(\",\") + 1);\r\n                window.parent.opener.top.frames[\"cmsright\"].document.getElementById(\"article\").value = retval.substring(0, retval.indexOf(\",\"));\r\n            }\r\n        }\r\n        top.close();\r\n    }\r\n    ".toCharArray();
    _jsp_string4 = ";\r\n        alert(aid);\r\n        if(param == 0)\r\n            window.parent.opener.document.getElementById('docLevel').value = aid.substring(0, aid.indexOf(\",\"));\r\n        else if(param == 1)\r\n            window.parent.opener.document.getElementById('viceDocLevel').value = aid.substring(0, aid.indexOf(\",\"));\r\n        else if(param == 2)\r\n            window.parent.opener.document.getElementById('relatedID').value = aid.substring(0, aid.indexOf(\",\"));\r\n\r\n        top.close();\r\n    }\r\n    ".toCharArray();
    _jsp_string7 = "\r\n            </font></td>\r\n        </tr>\r\n        <tr class=itm bgcolor=\"#dddddd\">\r\n            <td align=center width=\"7%\">\u9009\u4e2d</td>\r\n            <td align=center width=\"8%\">\u5e8f\u53f7</td>\r\n            <td align=center width=\"60%\">\u6807\u9898</td>\r\n            <td align=center width=\"15%\">\u4fee\u6539\u65f6\u95f4</td>\r\n            <td align=center width=\"10%\">\u9884\u89c8</td>\r\n        </tr>\r\n        ".toCharArray();
    _jsp_string8 = "\r\n    </form>\r\n</table>\r\n\r\n<p align=center>\r\n    <input type=button value=\"  \u786e\u5b9a  \" onclick=\"SelectArticle(".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
