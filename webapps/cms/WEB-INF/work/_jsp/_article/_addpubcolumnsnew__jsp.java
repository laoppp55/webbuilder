/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._article;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.news.Column;
import java.util.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.util.*;

public class _addpubcolumnsnew__jsp extends com.caucho.jsp.JavaPage
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

    String userid = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int sitetype = authToken.getSitetype();
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    Tree colTree = null;
    String tbuf = null;

    if (sitetype == 0 || sitetype == 2) {                             //0\u8868\u793a\u81ea\u5df1\u521b\u5efa\u7684\u7f51\u7ad9\uff0c2\u8868\u793a\u5b8c\u6574\u62f7\u8d1d\u6a21\u677f\u7f51\u7ad9
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //\u666e\u901a\u7528\u6237
            List clist = new ArrayList();
            PermissionSet p_set = authToken.getPermissionSet();
            Iterator iter1 = p_set.elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
            node[] treeNodes = colTree.getAllNodes();
            if (colTree.getNodeNum()> 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<colTree.getNodeNum();ii++){
                    if (ii<colTree.getNodeNum()-1) {
                        if (treeNodes[ii].getLinkPointer()==0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\"},\r\n";
                    }else {
                        if (treeNodes[ii].getLinkPointer() == 0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\",url:\"#\"" + ",open:\"true\"" + "}\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //\u7ad9\u70b9\u7ba1\u7406\u5458
            colTree = TreeManager.getInstance().getSiteTree(siteid);
            node[] treeNodes = colTree.getAllNodes();
            if (colTree.getNodeNum()> 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<colTree.getNodeNum();ii++){
                    if (ii<colTree.getNodeNum()-1) {
                        if (treeNodes[ii].getLinkPointer()==0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\"},\r\n";
                    }else {
                        if (treeNodes[ii].getLinkPointer() == 0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\",url:\"#\"" + ",open:\"true\"" + "}\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        }
    } else {                                                           //1\u8868\u793a\u5171\u4eab\u6a21\u677f\u7f51\u7ad9\u7684\u680f\u76ee\u548c\u6a21\u677f
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //\u666e\u901a\u7528\u6237
            List clist = new ArrayList();
            PermissionSet p_set = authToken.getPermissionSet();
            Iterator iter1 = p_set.elements();
            while (iter1.hasNext()) {
                Permission permission = (Permission) iter1.next();
                if (rightid == permission.getRightID()) {
                    clist = permission.getColumnListOnRight();
                    break;
                }
            }
            colTree = TreeManager.getInstance().getUserTree(userid, siteid, clist,rightid);
            if (clist.size() > 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<clist.size();ii++){
                    Column column = (Column)clist.get(ii);
                    if (ii<clist.size()-1) {
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"},\r\n";
                    }else{
                        if (column.getParentID()==0)
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\",url:\"#\"" + ",open:\"true\"" +"}\r\n";
                        else
                            tbuf = tbuf + "{id:" +column.getID() + ",pId:"+ column.getParentID() + ",name:\"" + column.getCName() +"\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
            //\u7ad9\u70b9\u7ba1\u7406\u5458
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
            node[] treeNodes = colTree.getAllNodes();
            if (colTree.getNodeNum()> 0) {
                tbuf = "[\r\n";
                for(int ii=0;ii<colTree.getNodeNum();ii++){
                    if (ii<colTree.getNodeNum()-1) {
                        if (treeNodes[ii].getLinkPointer()==0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\",url:\"\"" + ",open:\"true\"" +"},\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:"+ treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() +"\"},\r\n";
                    }else {
                        if (treeNodes[ii].getLinkPointer() == 0)
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\",url:\"#\"" + ",open:\"true\"" + "}\r\n";
                        else
                            tbuf = tbuf + "{id:" + treeNodes[ii].getId() + ",pId:" + treeNodes[ii].getLinkPointer() + ",name:\"" + treeNodes[ii].getChName() + "\"}\r\n";
                    }
                }
                tbuf = tbuf + "]";
            }
        }
    }

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((tbuf));
    out.write(_jsp_string2, 0, _jsp_string2.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("article/addPubColumnsnew.jsp"), 7173948319677705600L, false);
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
  private final static char []_jsp_string0;
  private final static char []_jsp_string2;
  static {
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<HTML>\r\n<HEAD>\r\n    <TITLE> ZTREE DEMO - checkbox</TITLE>\r\n    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\r\n    <link rel=\"stylesheet\" href=\"../css/zTree.css\" type=\"text/css\">\r\n    <link rel=\"stylesheet\" href=\"../css/zTreeStyle/zTreeStyle.css\" type=\"text/css\">\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.11.1.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.ztree.core-3.5.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.ztree.excheck-3.5.min.js\"></script>\r\n    <!--\r\n    <script type=\"text/javascript\" src=\"../../../js/jquery.ztree.exedit.js\"></script>\r\n    -->\r\n    <SCRIPT type=\"text/javascript\">\r\n        <!--\r\n        var setting = {\r\n            check: {\r\n                enable: true\r\n            },\r\n            data: {\r\n                simpleData: {\r\n                    enable: true\r\n                }\r\n            }\r\n        };\r\n\r\n        var zNodes = ".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
    _jsp_string2 = ";\r\n        var code;\r\n\r\n        function setCheck() {\r\n            var zTree = $.fn.zTree.getZTreeObj(\"treeDemo\"),\r\n                py = $(\"#py\").attr(\"checked\")? \"p\":\"\",\r\n                sy = $(\"#sy\").attr(\"checked\")? \"s\":\"\",\r\n                pn = $(\"#pn\").attr(\"checked\")? \"p\":\"\",\r\n                sn = $(\"#sn\").attr(\"checked\")? \"s\":\"\",\r\n                type = { \"Y\":py + sy, \"N\":pn + sn};\r\n            zTree.setting.check.chkboxType = type;\r\n            showCode('setting.check.chkboxType = { \"Y\" : \"' + type.Y + '\", \"N\" : \"' + type.N + '\" };');\r\n        }\r\n        function showCode(str) {\r\n            if (!code) code = $(\"#code\");\r\n            code.empty();\r\n            code.append(\"<li>\"+str+\"</li>\");\r\n        }\r\n\r\n        $(document).ready(function(){\r\n            $.fn.zTree.init($(\"#treeDemo\"), setting, zNodes);\r\n            setCheck();\r\n            $(\"#py\").bind(\"change\", setCheck);\r\n            $(\"#sy\").bind(\"change\", setCheck);\r\n            $(\"#pn\").bind(\"change\", setCheck);\r\n            $(\"#sn\").bind(\"change\", setCheck);\r\n        });\r\n\r\n        function save() {\r\n            alert(\"hello word\");\r\n            window.close();\r\n        }\r\n        //-->\r\n    </SCRIPT>\r\n</HEAD>\r\n\r\n<BODY>\r\n<h1>\u8bf7\u9009\u62e9\u53d1\u5e03\u7684\u76ee\u6807\u680f\u76ee</h1>\r\n<div class=\"content_wrap\">\r\n    <div class=\"zTreeDemoBackground left\">\r\n        <ul id=\"treeDemo\" class=\"ztree\"></ul>\r\n\r\n        <div align=\"center\" style=\"margin-top: 20px;\">\r\n            <input type=\"button\" name=\"save\" value=\"\u786e\u8ba4\" onclick=\"javascript:save();\"/>\r\n            <input type=\"button\" name=\"cancel\" value=\"\u8fd4\u56de\" onclick=\"javascript:window.close();\"/>\r\n        </div>\r\n    </div>\r\n    <div class=\"right\">\r\n        <ul class=\"info\">\r\n            <li class=\"title\">\r\n                <ul class=\"list\">\r\n                    <li>\r\n                        <p>\r\n                            <input type=\"checkbox\" id=\"py\" class=\"checkbox first\" checked /><!--span>\u5173\u8054\u7236</span-->\r\n                            <input type=\"checkbox\" id=\"sy\" class=\"checkbox first\" checked /><!--span>\u5173\u8054\u5b50</span><br/-->\r\n                            <input type=\"checkbox\" id=\"pn\" class=\"checkbox first\" checked /><!--span>\u5173\u8054\u7236</span-->\r\n                            <input type=\"checkbox\" id=\"sn\" class=\"checkbox first\" checked /><!--span>\u5173\u8054\u5b50</span><br/-->\r\n                        </p>\r\n                    </li>\r\n                </ul>\r\n            </li>\r\n        </ul>\r\n    </div>\r\n</div>\r\n</BODY>\r\n</HTML>".toCharArray();
  }
}
