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

public class _addarticleattechmentright__jsp extends com.caucho.jsp.JavaPage
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
    
    //\u8bf7\u4e3a\u7a0b\u5e8f\u6dfb\u52a0\u5fc5\u8981\u7684\u6ce8\u91ca
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    String msg = ParamUtil.getParameter(request, "msg");

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((columnID));
    out.write(_jsp_string2, 0, _jsp_string2.length);
    out.print((articleID));
    out.write(_jsp_string3, 0, _jsp_string3.length);
    out.print((columnID));
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print((articleID));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    
    if (msg!=null) out.println("<span class=cur>" + msg + "</span>");

    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print((CName));
    out.write(_jsp_string7, 0, _jsp_string7.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("article/addArticleAttechmentRight.jsp"), 5496939344079712914L, false);
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

  private final static char []_jsp_string7;
  private final static char []_jsp_string5;
  private final static char []_jsp_string3;
  private final static char []_jsp_string6;
  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  static {
    _jsp_string7 = "</font></td>\r\n    </tr>\r\n    <tr class=itm bgcolor=\"#dddddd\">\r\n        <td align=center width=\"5%\">\u9009\u4e2d</td>\r\n        <td align=center width=\"20%\">\u6807\u9898</td>\r\n        <td align=center width=\"40%\">\u6982\u8ff0</td>\r\n        <td align=center width=\"15%\">\u4e0a\u4f20\u65f6\u95f4</td>\r\n        <td align=center width=\"10%\">\u4fee\u6539</td>\r\n        <td align=center width=\"10%\">\u5220\u9664</td>\r\n    </tr>\r\n</table>\r\n<p align=center>\r\n<form enctype=\"multipart/form-data\" id=\"form1\">\r\n    <table border=\"0\" cellspace=\"0\">\r\n        <tr>\r\n            <td>\u9644\u4ef6\u540d\u79f0\uff1a</td>\r\n            <td><input type=\"text\" name=\"cname\" value=\"\" size=\"100\"></td>\r\n        </tr>\r\n        <tr>\r\n            <td>\u9644\u4ef6\u4ecb\u7ecd\uff1a</td>\r\n            <td><input type=\"text\" name=\"brief\" value=\"\" size=\"100\"></td>\r\n        </tr>\r\n        <tr>\r\n            <td>\u9644    \u4ef6\uff1a</td>\r\n            <td><input type=\"file\" name=\"attfile\" value=\"\"></td>\r\n        </tr>\r\n        <tr>\r\n            <td><input type=button value=\"  \u4e0a\u4f20  \" onclick=\"uploadAttechment();\"></td>\r\n            <td>    <input type=button value=\"  \u8fd4\u56de  \" onclick=\"top.close();\">            </td>\r\n        </tr>\r\n    </table>\r\n</form>\r\n</p>\r\n</BODY>\r\n</html>".toCharArray();
    _jsp_string5 = "\r\n            };\r\n\r\n            var ajaxFormOption = {\r\n                type: \"post\",                                                     //\u63d0\u4ea4\u65b9\u5f0f\r\n                dataType: \"json\",                                                //\u6570\u636e\u7c7b\u578b\r\n                data: myData,                                                      //\u81ea\u5b9a\u4e49\u6570\u636e\u53c2\u6570\uff0c\u89c6\u60c5\u51b5\u6dfb\u52a0\r\n                url: \"../upload/uploadAttechment.jsp?doUpload=true\",       //\u8bf7\u6c42url\r\n                success: function (data) {                                         //\u63d0\u4ea4\u6210\u529f\u7684\u56de\u8c03\u51fd\u6570\r\n                    var s_data = top.opener.document.createForm.attechments.value;\r\n                    if (s_data==null || s_data==\"\")\r\n                        top.opener.document.createForm.attechments.value = JSON.stringify(data);\r\n                    else\r\n                        top.opener.document.createForm.attechments.value=top.opener.document.createForm.attechments.value + \",\" + JSON.stringify(data);\r\n                    if (data.cname!=null && data.cname!=\"\" && data.filename!=null)\r\n                        CreateRow(data.cname,data.summary,data.createdate);\r\n                }\r\n            };\r\n\r\n            //\u4e0d\u9700\u8981submit\u6309\u94ae\uff0c\u53ef\u4ee5\u662f\u4efb\u4f55\u5143\u7d20\u7684click\u4e8b\u4ef6\r\n            $(\"#form1\").ajaxSubmit(ajaxFormOption);\r\n        }\r\n\r\n        function CreateRow(cname,summary,createdate) {\r\n            var $table = $('#table1');\r\n            var rows = $(\"#table1 tr\").length;\r\n            var tr=\"<tr><td><input type='checkbox' name='a'\" + rows + \"></td><td>\" + cname + \"</td><td>\" + summary +\r\n                    \"</td><td>\" + createdate + \"</td><td>\u4fee\u6539</td><td><a href='#' onclick='javascript:DeleteRow(\" + rows + \");'>\u5220\u9664</td></tr>\";\r\n            $table.append(tr);\r\n        }\r\n\r\n        function DeleteRow(row) {\r\n            var s_json = top.opener.document.createForm.attechments.value;\r\n            var json_data =\"{\\\"rows\\\":[\" +  top.opener.document.createForm.attechments.value + \"]}\";\r\n            var mydata = jQuery.parseJSON(json_data);\r\n            var result_data = \"\";\r\n            for(var rr=0;rr<mydata.rows.length;rr++){\r\n                if ((rr+1) != (row-1)) {\r\n                    result_data = result_data + JSON.stringify(mydata.rows[rr]) + \",\";\r\n                }\r\n            }\r\n            if (result_data.length>0) result_data = result_data.substring(0,result_data.length-1);\r\n            top.opener.document.createForm.attechments.value = result_data;\r\n            alert(result_data);\r\n            $(\"#table1 tr\").eq(row).remove();\r\n        }\r\n\r\n\r\n    </script>\r\n</head>\r\n<BODY BGCOLOR=\"#ffffff\" LINK=\"#000099\" ALINK=\"#cc0000\" VLINK=\"#000099\" TOMARGIN=8>\r\n".toCharArray();
    _jsp_string3 = "\r\n                },\r\n                async:false,\r\n                cache:false,\r\n                success:function(data){\r\n                    var json_data = \"\";\r\n                    if(typeof(data.errcode)==\"undefined\"){                 //\u7528\u4e8e\u6587\u7ae0\u6709\u9644\u4ef6\uff0c\u4e14\u662f\u4fee\u6539\u6587\u7ae0\u7684\u60c5\u51b5\r\n                        var s_json = top.opener.document.createForm.attechments.value;\r\n                        if (s_json==null || s_json==\"\") {\r\n                            //\u7b2c\u4e00\u6b21\u8fdb\u5165\u8be5\u9875\u9762\uff0c\u5c06\u6570\u636e\u5e93\u4e2d\u7684\u503c\u8d4b\u7ed9opener\u7684\u9690\u542b\u5b57\u6bb5\r\n                            var val_in_db = JSON.stringify(data);\r\n                            top.opener.document.createForm.attechments.value = val_in_db.substring(1,val_in_db.length-1);\r\n                            json_data =\"{\\\"rows\\\":[\" +  top.opener.document.createForm.attechments.value + \"]}\";\r\n                        }else\r\n                            json_data =\"{\\\"rows\\\":[\" +  top.opener.document.createForm.attechments.value + \"]}\";\r\n                        if (s_json!=null) {\r\n                            var mydata = jQuery.parseJSON(json_data);\r\n                            for(var rr=0;rr<mydata.rows.length;rr++){\r\n                                CreateRow(mydata.rows[rr].cname,mydata.rows[rr].summary,mydata.rows[rr].createdate);\r\n                            }\r\n                        }\r\n                    } else {                                                 //\u7528\u4e8e\u521b\u5efa\u6587\u7ae0\uff0c\u6216\u8005\u7f16\u8f91\u6587\u7ae0\u7b2c\u4e00\u6b21\u589e\u52a0\u9644\u4ef6\r\n                        var s_json = top.opener.document.createForm.attechments.value;\r\n                        json_data =\"{\\\"rows\\\":[\" +  top.opener.document.createForm.attechments.value + \"]}\";\r\n                        if (s_json!=null) {\r\n                            var mydata = jQuery.parseJSON(json_data);\r\n                            for(var rr=0;rr<mydata.rows.length;rr++){\r\n                                CreateRow(mydata.rows[rr].cname,mydata.rows[rr].summary,mydata.rows[rr].createdate);\r\n                            }\r\n                        }\r\n                    }\r\n                }\r\n            });\r\n        });\r\n\r\n        function uploadAttechment(){\r\n            var myData = {\r\n                \"column\":".toCharArray();
    _jsp_string6 = "\r\n<table  id=\"table1\" border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=\"100%\">\r\n    <tr>\r\n        <td colspan=6>\u5f53\u524d\u6240\u5728\u680f\u76ee-->><font color=red>".toCharArray();
    _jsp_string1 = "\r\n\r\n<html>\r\n<head>\r\n    <title></title>\r\n    <meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\r\n    <link rel=stylesheet type=text/css href=\"../style/global.css\">\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.11.1.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.form.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/json2.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/json_parse.js\"></script>\r\n    <script language=\"JavaScript\">\r\n        $(document).ready(function(){     //\u521d\u59cb\u5316ztree\u5bf9\u8c61\r\n            htmlobj=$.ajax({\r\n                url:\"getAttechments.jsp\",\r\n                type:'post',\r\n                dataType:'json',\r\n                data:{\r\n                    colid:".toCharArray();
    _jsp_string2 = ",\r\n                    article:".toCharArray();
    _jsp_string4 = ",\r\n                \"article\":".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}
