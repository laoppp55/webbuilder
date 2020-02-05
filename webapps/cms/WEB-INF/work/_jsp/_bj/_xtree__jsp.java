/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._bj;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.security.Auth;
import com.bizwink.cms.util.ParamUtil;
import com.bizwink.po.Column;
import org.springframework.context.ApplicationContext;
import com.bizwink.util.SpringInit;
import com.bizwink.service.ColumnService;
import java.math.BigDecimal;
import java.util.List;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.security.SecurityCheck;
import java.util.ArrayList;
import com.bizwink.cms.security.PermissionSet;
import java.util.Iterator;
import com.bizwink.cms.security.Permission;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.news.ColumnTree;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.util.FileUtil;

public class _xtree__jsp extends com.caucho.jsp.JavaPage
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
  int samsiteid = authToken.getSamSiteid();
  String userid = authToken.getUserID();
  int sitetype = authToken.getSitetype();
  int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
  String tbuf = null;
  Tree colTree = null;

  Column rootColumn = null;
  ApplicationContext appContext = SpringInit.getApplicationContext();
  if (appContext!=null) {
    ColumnService columnService = (ColumnService)appContext.getBean("columnService");
    rootColumn = columnService.getRootColumnBySiteid(BigDecimal.valueOf(siteid));
  }
  int rootColumnID = rootColumn.getID().intValue();
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
    } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
      //\u7ad9\u70b9\u7ba1\u7406\u5458
      colTree = TreeManager.getInstance().getSiteTree(siteid);
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
    } else if (!userid.equalsIgnoreCase("admin") && SecurityCheck.hasPermission(authToken, 54)) {
      //\u7ad9\u70b9\u7ba1\u7406\u5458
      colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
    }
  }

  //\u6784\u5efa\u680f\u76ee\u6811\u7684JSON\u6570\u636e
  StringBuffer buf = new StringBuffer();                        //\u5b58\u50a8\u751f\u6210\u7684\u83dc\u5355\u6811
  if (colTree.getNodeNum() > 1) {
    node[] treeNodes = colTree.getAllNodes();                     //\u83b7\u53d6\u8be5\u6811\u7684\u6240\u6709\u8282\u70b9
    List<Integer> pid = new ArrayList<Integer>();
    //int pid[] = new int[colTree.getNodeNum()];                    //\u904d\u5386\u6811\u6240\u9700\u8981\u7684\u8282\u70b9\u6570\u7ec4\uff0c\u5b58\u50a8\u5f53\u524d\u672a\u5904\u7406\u7684\u8282\u70b9
    int currentID = 0;                                            //\u5f53\u524d\u6b63\u5728\u5904\u7406\u7684\u8282\u70b9
    int i = 0;                                                      //\u5faa\u73af\u53d8\u91cf
    int[] ordernum = new int[colTree.getNodeNum()];               //\u5f53\u524d\u8282\u70b9\u6240\u6709\u5b50\u8282\u70b9\u7684\u987a\u5e8f\u53f7
    int nodenum = 1;                                              //\u5f53\u524d\u88ab\u5904\u7406\u8282\u70b9\u7684\u521d\u59cb\u503c
    int subnodenum = 1;                                              //\u5224\u65ad\u5f53\u524d\u8282\u70b9\u662f\u5426\u6709\u5b50\u8282\u70b9
    int subnodenumOfParentNode = 0;

    pid.add(0);
    buf.append("    var json = [");
    while(pid.size()>0) {
      //\u5904\u7406\u5f53\u524d\u8282\u70b9
      currentID = pid.get(0);
      pid.remove(0);
      int currentNodeIndex = 0;
      int currentNode_ParentNodeIndex = 0;
      //\u83b7\u53d6\u5f53\u524d\u8282\u70b9
      for (i = 0; i < colTree.getNodeNum(); i++) {
        if (treeNodes[i].getId() == currentID) {
          currentNodeIndex = i;
          break;
        }
      }

      //\u83b7\u53d6\u5f53\u524d\u8282\u70b9\u7684\u7236\u8282\u70b9
      for (i = 0; i < colTree.getNodeNum(); i++) {
        if (treeNodes[i].getId() == treeNodes[currentNodeIndex].getLinkPointer()) {
          currentNode_ParentNodeIndex = i;
          break;
        }
      }

      if (currentID != 0) {
        subnodenumOfParentNode = treeNodes[currentNode_ParentNodeIndex].getSubnodes()-1;
        treeNodes[currentNode_ParentNodeIndex].setSubnodes(subnodenumOfParentNode);
        buf.append("{title:\"" + treeNodes[currentNodeIndex].getChName() + "\",value:\"" + currentID + "\",data:[");
      }

      //\u4ece\u5904\u7406\u7684\u8282\u70b9\u6570\u7ec4\u4e2d\u53d6\u51fa\u5f53\u524d\u6b63\u5728\u5904\u7406\u7684\u5143\u7d20\uff0c\u67e5\u627e\u8be5\u5143\u7d20\u4e0b\u7684\u5b50\u5143\u7d20
      //\u8bbe\u7f6e\u6240\u6709\u5b50\u8282\u70b9\u7684\u7236\u83dc\u5355\u540d\u79f0\uff0c\u8bbe\u7f6e\u6240\u6709\u5b50\u8282\u70b9\u7684\u5e8f\u5217\u53f7\uff0c\u628a\u6240\u6709\u7684\u5b50\u8282\u70b9\u5b58\u5165pid\u6570\u7ec4\u4e2d
      subnodenum = 0;
      nodenum = nodenum - 1;
      for (i = colTree.getNodeNum()-1; i >= 0 ; i--) {
        if (treeNodes[i].getLinkPointer() == currentID) {
          nodenum = nodenum + 1;
          pid.add(0,treeNodes[i].getId());
          ordernum[nodenum] = subnodenum;
          subnodenum = subnodenum + 1;
        }
      }

      //\u8bbe\u7f6e\u5f53\u524d\u8282\u70b9\u7684\u5b50\u8282\u70b9\u6570\u91cf
      treeNodes[currentNodeIndex].setSubnodes(subnodenum);
      if (subnodenum == 0) {
        //\u5f53\u524d\u8282\u70b9\u662f\u7236\u8282\u70b9\u7684\u6700\u540e\u4e00\u4e2a\u8282\u70b9,\u5faa\u73af\u5411\u6811\u5f62\u7ed3\u6784\u7684\u4e0a\u5c42\u8fdb\u884c\u67e5\u627e\uff0c\u4e00\u76f4\u627e\u5230\u5b50\u8282\u70b9\u6570\u5c1a\u4e0d\u662f\u96f6\u7684\u8282\u70b9\uff0c\u4ece\u4e0d\u662f\u96f6\u7684\u8282\u70b9\u5411\u4e0b\u5199\u8282\u70b9\u7684\u7ed3\u675f\u90e8\u5206
        if (subnodenumOfParentNode==0) {
          //\u5bfb\u627e\u5f53\u524d\u8282\u70b9\u5411\u4e0a\u7684\u8def\u5f84\u4e0a\u5b50\u8282\u70b9\u6570\u4e0d\u662f\u96f6\u7684\u8282\u70b9
          int tnum = 0;
          int level = 0;
          while(tnum == 0 && treeNodes[currentNodeIndex].getLinkPointer()>0) {
            for (i = 0; i < colTree.getNodeNum(); i++) {
              if (treeNodes[i].getId() == treeNodes[currentNodeIndex].getLinkPointer()) {
                tnum = treeNodes[i].getSubnodes();
                if (tnum == 0) {
                  level = level + 1;
                  currentNodeIndex = i;
                  break;
                }
              }
            }
          }

          //\u4ece\u5f53\u524d\u8282\u70b9\u5230\u6839\u8282\u70b9\u7684\u8def\u5f84\u4e0a\u67e5\u627e\u5230\u7b2c\u4e00\u4e2a\u672a\u5904\u7406\u5b50\u8282\u70b9\u6570\u4e0d\u4e3a\u96f6\u7684\u8282\u70b9\uff0c\u8f93\u51fa\u76f8\u5e94\u6df1\u5ea6\u4e2a\u6570\u7684\u201c]}\u201d\u5b57\u7b26\u4e32
          for(i=0; i<level; i++) buf.append("]}\r");

          buf.append("]},\r");
        } else {                         //\u5f53\u524d\u8282\u70b9\u4e0d\u662f\u7236\u8282\u70b9\u7684\u6700\u540e\u4e00\u4e2a\u8282\u70b9
          buf.append("]},\r");
        }
      } else {
        buf.append("\r");
      }
    }
    //\u53bb\u6389\u5b57\u7b26\u4e32\u6700\u540e\u4e00\u4e2a\u591a\u4f59\u7684\u201c,\u201d\u5b57\u7b26\uff0c\u589e\u52a0json\u7684\u5173\u95ed\u5b57\u7b26\u201c];\u201d
    int posi = buf.lastIndexOf(",");
    if (posi>-1) {
      buf.delete(posi,buf.length());
      buf.append("\r];");
    }
  }

  FileUtil.writeFile(buf,"c:\\data\\11111.txt");
  System.out.println(buf.toString());

    out.write(_jsp_string1, 0, _jsp_string1.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("bj/xtree.jsp"), 8491278474111994200L, false);
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
  static {
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n  <meta charset=\"utf-8\">\r\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n  <title>layout \u540e\u53f0\u5927\u5e03\u5c40 - Layui</title>\r\n  <link rel=\"stylesheet\" href=\"../design/css/layui.css\">\r\n  <link rel=\"stylesheet\" href=\"mycss.css\">\r\n</head>\r\n<body class=\"layui-layout-body\">\r\n<div class=\"layui-layout layui-layout-admin\">\r\n  <div class=\"layui-header\">\r\n    <div class=\"layui-logo\">layui \u540e\u53f0\u5e03\u5c40</div>\r\n    <ul class=\"layui-nav layui-layout-left\">\r\n      <li class=\"layui-nav-item\"><a href=\"\">\u63a7\u5236\u53f0</a></li>\r\n      <li class=\"layui-nav-item\"><a href=\"\">\u5546\u54c1\u7ba1\u7406</a></li>\r\n      <li class=\"layui-nav-item\"><a href=\"\">\u7528\u6237</a></li>\r\n      <li class=\"layui-nav-item\">\r\n        <a href=\"javascript:;\">\u5176\u5b83\u7cfb\u7edf</a>\r\n        <dl class=\"layui-nav-child\">\r\n          <dd><a href=\"\">\u90ae\u4ef6\u7ba1\u7406</a></dd>\r\n          <dd><a href=\"\">\u6d88\u606f\u7ba1\u7406</a></dd>\r\n          <dd><a href=\"\">\u6388\u6743\u7ba1\u7406</a></dd>\r\n        </dl>\r\n      </li>\r\n    </ul>\r\n    <ul class=\"layui-nav layui-layout-right\">\r\n      <li class=\"layui-nav-item\">\r\n        <a href=\"javascript:;\">\r\n          <img src=\"http://t.cn/RCzsdCq\" class=\"layui-nav-img\">\r\n          \u8d24\u5fc3\r\n        </a>\r\n        <dl class=\"layui-nav-child\">\r\n          <dd><a href=\"\">\u57fa\u672c\u8d44\u6599</a></dd>\r\n          <dd><a href=\"\">\u5b89\u5168\u8bbe\u7f6e</a></dd>\r\n        </dl>\r\n      </li>\r\n      <li class=\"layui-nav-item\"><a href=\"\">\u9000\u4e86</a></li>\r\n    </ul>\r\n  </div>\r\n\r\n  <div class=\"layui-side layui-bg-black\"  style=\"top: 50px\">\r\n    <form class=\"layui-form\">\r\n    <div class=\"layui-side-scroll\">\r\n        <div id=\"xtree1\" class=\"xtree_contianer\"></div>\r\n    </div>\r\n    </form>\r\n  </div>\r\n\r\n  <div class=\"layui-body\">\r\n    <!-- \u5185\u5bb9\u4e3b\u4f53\u533a\u57df -->\r\n    <div style=\"padding: 15px;\">\u5185\u5bb9\u4e3b\u4f53\u533a\u57df<span cass=\u201dicon\u201d>&#xeb80;</span></div>\r\n  </div>\r\n\r\n  <div class=\"layui-footer\">\r\n    <!-- \u5e95\u90e8\u56fa\u5b9a\u533a\u57df -->\r\n    \u00a9 layui.com - \u5e95\u90e8\u56fa\u5b9a\u533a\u57df\r\n  </div>\r\n</div>\r\n<script src=\"../design/layui.js\"></script>\r\n<!--\u5f15\u7528xtree-->\r\n<script src=\"../design/layui-xtree.js\"></script>\r\n<script>\r\n    //\u4e00\u5957json\u6570\u636e\u4e0b\u9762\u4f1a\u4f7f\u7528\uff0c\u540c\u65f6\u4f9b\u4f60\u53c2\u8003\r\n    var json = [\r\n        {title:\"www.bucgdx.com\",value:\"41\",data:[\r\n            {title:\"\u5b66\u6821\u7b80\u4ecb\",value:\"42\",data:[\r\n                {title:\"\u5b66\u6821\u6982\u51b5\",value:\"58\",data:[]},\r\n                {title:\"\u7ec4\u7ec7\u673a\u6784\",value:\"59\",data:[]},\r\n                {title:\"\u5b66\u6821\u8363\u8a89\",value:\"60\",data:[]},\r\n                {title:\"\u5b66\u6821\u8d44\u8d28\",value:\"61\",data:[]},\r\n                {title:\"\u5b66\u6821\u9886\u5bfc\",value:\"62\",data:[]},\r\n                {title:\"\u62db\u8058\u4fe1\u606f\",value:\"63\",data:[]}\r\n            ]},\r\n            {title:\"\u901a\u77e5\u901a\u544a\",value:\"43\",data:[\r\n                {title:\"\u901a\u77e5\",value:\"64\",data:[]},\r\n                {title:\"\u516c\u544a\",value:\"65\",data:[]}\r\n            ]},\r\n            {title:\"\u65b0\u95fb\u4e2d\u5fc3\",value:\"44\",data:[\r\n                {title:\"\u5b66\u6821\u65b0\u95fb\",value:\"45\",data:[]},\r\n                {title:\"\u57f9\u8bad\u65b0\u95fb\",value:\"46\",data:[]},\r\n                {title:\"\u884c\u4e1a\u65b0\u95fb\",value:\"66\",data:[]},\r\n                {title:\"\u6211\u7684\u65b0\u95fb\",value:\"143\",data:[]}\r\n            ]},\r\n            {title:\"\u515a\u6821\u6559\u80b2\",value:\"47\",data:[\r\n                {title:\"\u4f01\u4e1a\u9886\u5bfc\u5e72\u90e8\u57f9\u8bad\",value:\"48\",data:[]},\r\n                {title:\"\u515a\u5458\u53d1\u5c55\u5bf9\u8c61\u57f9\u8bad\",value:\"49\",data:[]},\r\n                {title:\"\u5165\u515a\u79ef\u6781\u5206\u5b50\u57f9\u8bad\",value:\"50\",data:[]}\r\n            ]},\r\n            {title:\"\u57ce\u5efa\u5546\u5b66\u9662\",value:\"51\",data:[\r\n                {title:\"\u4f01\u4e1a\u7d27\u7f3a\u4eba\u624d\u57f9\u8bad\",value:\"67\",data:[]},\r\n                {title:\"\u7ee7\u7eed\u6559\u80b2\u57f9\u8bad\",value:\"68\",data:[]}\r\n            ]},\r\n            {title:\"\u65b0\u6280\u672f\u63a8\u5e7f\",value:\"52\",data:[\r\n                {title:\"\u201c\u56db\u65b0\u201d\u6280\u672f\u63a8\u5e7f\",value:\"69\",data:[]},\r\n                {title:\"BIM\u57f9\u8bad\",value:\"70\",data:[]}\r\n            ]},\r\n            {title:\"\u6210\u4eba\u5b66\u5386\u6559\u80b2\",value:\"53\",data:[\r\n                {title:\"\u4e2d\u592e\u515a\u6821\u7814\u7a76\u751f\u73ed\",value:\"71\",data:[]},\r\n                {title:\"\u5965\u9e4f\u5b66\u5386\u6559\u80b2\",value:\"72\",data:[]},\r\n                {title:\"\u5176\u4ed6\u5b66\u5386\u6559\u80b2\",value:\"73\",data:[]}\r\n            ]},\r\n            {title:\"\u884c\u4e1a\u57f9\u8bad\",value:\"54\",data:[\r\n                {title:\"\u6ce8\u518c\u7c7b\u57f9\u8bad\",value:\"74\",data:[\r\n                    {title:\"\u4e00\u7ea7\u6ce8\u518c\u5efa\u9020\u5e08\",value:\"78\",data:[\r\n                        {title:\"\u5efa\u7b51\u5de5\u7a0b\",value:\"111\",data:[]},\r\n                        {title:\"\u516c\u8def\u5de5\u7a0b\",value:\"112\",data:[]},\r\n                        {title:\"\u94c1\u8def\u5de5\u7a0b\",value:\"113\",data:[]},\r\n                        {title:\"\u6c11\u822a\u673a\u573a\u5de5\u7a0b\",value:\"114\",data:[]},\r\n                        {title:\"\u6e2f\u53e3\u4e0e\u822a\u9053\u5de5\u7a0b\",value:\"115\",data:[]},\r\n                        {title:\"\u6c34\u5229\u6c34\u7535\u5de5\u7a0b\",value:\"116\",data:[]},\r\n                        {title:\"\u77ff\u4e1a\u5de5\u7a0b\",value:\"117\",data:[]},\r\n                        {title:\"\u5e02\u653f\u516c\u7528\u5de5\u7a0b\",value:\"118\",data:[]},\r\n                        {title:\"\u901a\u4fe1\u4e0e\u5e7f\u7535\u5de5\u7a0b\",value:\"119\",data:[]},\r\n                        {title:\"\u673a\u7535\u5de5\u7a0b\",value:\"120\",data:[]}\r\n                    ]},\r\n                    {title:\"\u4e8c\u7ea7\u6ce8\u518c\u5efa\u9020\u5e08\",value:\"79\",data:[\r\n                        {title:\"\u5efa\u7b51\u5de5\u7a0b\",value:\"121\",data:[]},\r\n                        {title:\"\u516c\u8def\u5de5\u7a0b\",value:\"122\",data:[]}\r\n                    ]},\r\n                    {title:\"\u4e00\u7ea7\u6ce8\u518c\u9020\u4ef7\u5e08\",value:\"80\",data:[]},\r\n                    {title:\"\u4e8c\u7ea7\u6ce8\u518c\u9020\u4ef7\u5e08\",value:\"81\",data:[]},\r\n                    {title:\"\u5176\u5b83\",value:\"82\",data:[]}\r\n                ]},\r\n                {title:\"\u5b89\u5168\u4e09\u7c7b\u4eba\u5458\u57f9\u8bad\",value:\"75\",data:[]},\r\n                {title:\"\u73b0\u573a\u7ba1\u7406\u4eba\u5458\u57f9\u8bad\",value:\"76\",data:[\r\n                    {title:\"\u65bd\u5de5\u5458\",value:\"83\",data:[]},\r\n                    {title:\"\u8d28\u68c0\u5458\",value:\"84\",data:[]},\r\n                    {title:\"\u6750\u6599\u5458\",value:\"85\",data:[]},\r\n                    {title:\"\u8d44\u6599\u5458\",value:\"86\",data:[]},\r\n                    {title:\"\u6d4b\u91cf\u9a8c\u7ebf\u5458\",value:\"87\",data:[]},\r\n                    {title:\"\u52b3\u52a8\u529b\u7ba1\u7406\u5458\",value:\"88\",data:[]},\r\n                    {title:\"\u8bd5\u9a8c\u5458\",value:\"89\",data:[]},\r\n                    {title:\"\u673a\u68b0\u5458\",value:\"90\",data:[]},\r\n                    {title:\"\u76d1\u7406\u5458\",value:\"91\",data:[]},\r\n                    {title:\"\u9884\u7b97\u5458\",value:\"92\",data:[]}\r\n                ]},\r\n                {title:\"\u73b0\u573a\u64cd\u4f5c\u4eba\u5458\u57f9\u8bad\",value:\"77\",data:[\r\n                    {title:\"\u6280\u672f\u5de5\u4eba\",value:\"93\",data:[\r\n                        {title:\"\u6728\u5de5\",value:\"95\",data:[]},\r\n                        {title:\"\u62b9\u7070\u5de5\",value:\"96\",data:[]},\r\n                        {title:\"\u7ba1\u9053\u5de5\",value:\"97\",data:[]},\r\n                        {title:\"\u5de5\u7a0b\u7535\u6c14\u8bbe\u5907\u5b89\u88c5\u8c03\u8bd5\u5de5\",value:\"98\",data:[]},\r\n                        {title:\"\u5efa\u7b51\u6cb9\u6f06\u5de5\",value:\"99\",data:[]},\r\n                        {title:\"\u6d4b\u91cf\u653e\u7ebf\u5de5\",value:\"100\",data:[]},\r\n                        {title:\"\u74e6\u5de5\",value:\"101\",data:[]},\r\n                        {title:\"\u94a2\u7b4b\u5de5\",value:\"102\",data:[]}\r\n                    ]},\r\n                    {title:\"\u7279\u79cd\u4f5c\u4e1a\u64cd\u4f5c\u4eba\u5458\",value:\"94\",data:[\r\n                        {title:\"\u5854\u5f0f\u8d77\u91cd\u673a\u53f8\u673a\",value:\"103\",data:[]},\r\n                        {title:\"\u65bd\u5de5\u5916\u68af\u53f8\u673a\",value:\"104\",data:[]},\r\n                        {title:\"\u4fe1\u53f7\u53f8\u7d22\u5de5\",value:\"105\",data:[]},\r\n                        {title:\"\u5efa\u7b51\u67b6\u5b50\u5de5P\",value:\"106\",data:[]},\r\n                        {title:\"\u5efa\u7b51\u8d77\u91cd\u673a\u68b0\u5b89\u88c5\u62c6\u5378\u5de5\",value:\"107\",data:[]},\r\n                        {title:\"\u5efa\u7b51\u7535\u5de5\",value:\"108\",data:[]},\r\n                        {title:\"\u6709\u9650\u7a7a\u95f4\u4f5c\u4e1a\",value:\"109\",data:[]},\r\n                        {title:\"\u7535\u710a\u5de5\",value:\"110\",data:[]}\r\n                    ]}\r\n                ]}\r\n            ]},\r\n            {title:\"\u8054\u7cfb\u6211\u4eec\",value:\"55\",data:[]},\r\n            {title:\"\u76f8\u5173\u94fe\u63a5\",value:\"56\",data:[]},\r\n            {title:\"\u5408\u4f5c\u4f19\u4f34\",value:\"57\",data:[]},\r\n            {title:\"\u6d4b\u8bd5\u4fee\u6539\u680f\u76ee\",value:\"123\",data:[]},\r\n            {title:\"\u5305\u542b\u6587\u4ef6\",value:\"161\",data:[]}\r\n        ]}\r\n    ];\r\n\r\n    //layui\u7684form\u6a21\u5757\u662f\u5fc5\u987b\u7684,\u7528\u4e8e\u652f\u6301xtree\u6a21\u5757\r\n    layui.use(['form'], function () {\r\n        var form = layui.form;\r\n\r\n        //1\u3001\u6700\u57fa\u7840\u7684\u7528\u6cd5 - \u76f4\u63a5\u7ed1\u5b9ajson\r\n        var xtree1 = new layuiXtree({\r\n            elem: 'xtree1'   //(\u5fc5\u586b) \u653e\u7f6extree\u7684\u5bb9\u5668\uff0c\u6837\u5f0f\u53c2\u7167 .xtree_contianer\r\n            , form: form     //(\u5fc5\u586b) layui \u7684 from\r\n            , data: json     //(\u5fc5\u586b) json\u6570\u636e\r\n        });\r\n\r\n        /*\r\n        //2\u3001\u6700\u57fa\u7840\u7684\u7528\u6cd5 - \u5f02\u6b65\u52a0\u8f7d\r\n        var xtree2 = new layuiXtree({\r\n            elem: 'xtree2'                  //(\u5fc5\u586b) \u653e\u7f6extree\u7684\u5bb9\u5668\uff0c\u6837\u5f0f\u53c2\u7167 .xtree_contianer\r\n            , form: form                    //(\u5fc5\u586b) layui \u7684 from\r\n            , data: 'server/xtreedata.ashx' //(\u5fc5\u586b) \u6570\u636e\u63a5\u53e3\uff0c\u9700\u8981\u8fd4\u56de\u4ee5\u4e0a\u7ed3\u6784\u7684json\u5b57\u7b26\u4e32\r\n        });\r\n\r\n        //3\u3001\u6700\u5b8c\u6574\u7684\u53c2\u6570\u7528\u6cd5\r\n        var xtree3 = new layuiXtree({\r\n            elem: 'xtree3'                  //\u5fc5\u586b\u4e09\u5144\u5f1f\u4e4b\u8001\u5927\r\n            , form: form                    //\u5fc5\u586b\u4e09\u5144\u5f1f\u4e4b\u8fd9\u624d\u662f\u771f\u8001\u5927\r\n            , data: 'server/xtreedata2.ashx' //\u5fc5\u586b\u4e09\u5144\u5f1f\u4e4b\u8fd9\u4e5f\u7b97\u662f\u8001\u5927\r\n            , isopen: false  //\u52a0\u8f7d\u5b8c\u6bd5\u540e\u7684\u5c55\u5f00\u72b6\u6001\uff0c\u9ed8\u8ba4\u503c\uff1atrue\r\n            , ckall: true    //\u542f\u7528\u5168\u9009\u529f\u80fd\uff0c\u9ed8\u8ba4\u503c\uff1afalse\r\n            , ckallback: function () { } //\u5168\u9009\u6846\u72b6\u6001\u6539\u53d8\u540e\u6267\u884c\u7684\u56de\u8c03\u51fd\u6570\r\n            , icon: {        //\u4e09\u79cd\u56fe\u6807\u6837\u5f0f\uff0c\u66f4\u6539\u51e0\u4e2a\u90fd\u53ef\u4ee5\uff0c\u7528\u7684\u662flayui\u7684\u56fe\u6807\r\n                open".toCharArray();
    _jsp_string2 = ": \"&#xe7a0;\"       //\u8282\u70b9\u6253\u5f00\u7684\u56fe\u6807\r\n                , close: \"&#xe622;\"    //\u8282\u70b9\u5173\u95ed\u7684\u56fe\u6807\r\n                , end: \"&#xe621;\"      //\u672b\u5c3e\u8282\u70b9\u7684\u56fe\u6807\r\n            }\r\n            , color: {       //\u4e09\u79cd\u56fe\u6807\u989c\u8272\uff0c\u72ec\u7acb\u914d\u8272\uff0c\u66f4\u6539\u51e0\u4e2a\u90fd\u53ef\u4ee5\r\n                open: \"#EE9A00\"        //\u8282\u70b9\u56fe\u6807\u6253\u5f00\u7684\u989c\u8272\r\n                , close: \"#EEC591\"     //\u8282\u70b9\u56fe\u6807\u5173\u95ed\u7684\u989c\u8272\r\n                , end: \"#828282\"       //\u672b\u7ea7\u8282\u70b9\u56fe\u6807\u7684\u989c\u8272\r\n            }\r\n            , click: function (data) {  //\u8282\u70b9\u9009\u4e2d\u72b6\u6001\u6539\u53d8\u4e8b\u4ef6\u76d1\u542c\uff0c\u5168\u9009\u6846\u6709\u81ea\u5df1\u7684\u76d1\u542c\u4e8b\u4ef6\r\n                console.log(data.elem); //\u5f97\u5230checkbox\u539f\u59cbDOM\u5bf9\u8c61\r\n                console.log(data.elem.checked); //\u5f00\u5173\u662f\u5426\u5f00\u542f\uff0ctrue\u6216\u8005false\r\n                console.log(data.value); //\u5f00\u5173value\u503c\uff0c\u4e5f\u53ef\u4ee5\u901a\u8fc7data.elem.value\u5f97\u5230\r\n                console.log(data.othis); //\u5f97\u5230\u7f8e\u5316\u540e\u7684DOM\u5bf9\u8c61\r\n            }\r\n        });*/\r\n\r\n        //\u63d0\u4f9b\u7684\u65b9\u6cd5\u4eec\r\n        //\u83b7\u53d6\u5168\u90e8[\u9009\u4e2d\u7684][\u672b\u7ea7\u8282\u70b9]\u539fcheckbox DOM\u5bf9\u8c61\uff0c\u8fd4\u56deArray\r\n        document.getElementById('btn1').onclick = function () {\r\n            var oCks = xtree3.GetChecked(); //\u8fd9\u662f\u65b9\u6cd5\r\n            for (var i = 0; i < oCks.length; i++) {\r\n                console.log(oCks[i].value);\r\n            }\r\n        }\r\n\r\n        //\u83b7\u53d6\u5168\u90e8\u539fcheckbox DOM\u5bf9\u8c61\uff0c\u8fd4\u56deArray\r\n        document.getElementById('btn2').onclick = function () {\r\n            var oCks = xtree3.GetAllCheckBox(); //\u8fd9\u662f\u65b9\u6cd5\r\n            for (var i = 0; i < oCks.length; i++) {\r\n                console.log(oCks[i].value);\r\n            }\r\n        }\r\n\r\n        //\u66f4\u65b0\u6570\u636e\uff0c\u91cd\u65b0\u6e32\u67d3\r\n        document.getElementById('btn3').onclick = function () {\r\n            xtree3.render();\r\n        }\r\n\r\n        //\u901a\u8fc7value\u503c\u627e\u7236\u7ea7DOM\u5bf9\u8c61\uff0c\u9876\u7ea7\u8282\u70b9\u4e0e\u9519\u8bef\u503c\u5747\u8fd4\u56denull\r\n        document.getElementById('btn4').onclick = function () {\r\n            var oCks = xtree3.GetParent(document.getElementById('txt1').value);  //\u8fd9\u662f\u65b9\u6cd5\r\n            if (oCks != null) { //\u5982\u679c\u8fd4\u56de\u7684\u5bf9\u8c61\u4e0d\u4e3anull\uff0c\u5219\u83b7\u53d6\u5230\u7236\u8282\u70b9\u4e86\uff0c\u5982\u679c\u4e3anull\uff0c\u8bf4\u660e\u8fd9\u4e2a\u503c\u5bf9\u5e94\u7684\u8282\u70b9\u662f\u4e00\u7ea7\u8282\u70b9\u6216\u662f\u503c\u9519\u8bef\r\n                console.log(oCks.value);\r\n            }\r\n            else {\r\n                console.log('\u65e0\u7236\u7ea7\u8282\u70b9\u6216value\u503c\u9519\u8bef');\r\n            }\r\n        }\r\n    });\r\n\r\n    //JavaScript\u4ee3\u7801\u533a\u57df\r\n    layui.use('element', function(){\r\n        var element = layui.element;\r\n    });\r\n\r\n    //Demo\r\n    //layui.use(['tree', 'layer'], function(){\r\n    /*layui.use(['element', 'tree', 'layer'], function(){\r\n        var layer = layui.layer\r\n            ,$ = layui.jquery;\r\n        var element = layui.element;\r\n        var jsonStr;\r\n        var url = \"r?wf_num=T_bhlayui_001\";\r\n\r\n        //\u6dfb\u52a0\u9009\u4e2d\u989c\u8272\u663e\u793a\r\n        $(\"body\").on(\"mousedown\", \".layui-tree a\", function() {\r\n            if(!$(this).siblings('ul').length) {\r\n                $(\".layui-tree a\").removeClass('active');\r\n                $(this).find('cite').parent().addClass('active');\r\n            }\r\n        });\r\n\r\n        layui.tree({\r\n            elem: '#demo1' //\u6307\u5b9a\u5143\u7d20\r\n            ,skin: 'sidebar'\r\n            //,skin: 'shihuang'\r\n            ,target: '_blank' //\u662f\u5426\u65b0\u9009\u9879\u5361\u6253\u5f00\uff08\u6bd4\u5982\u8282\u70b9\u8fd4\u56dehref\u624d\u6709\u6548\uff09\r\n            ,click: function(item){ //\u70b9\u51fb\u8282\u70b9\u56de\u8c03\r\n                layer.msg('\u5f53\u524d\u8282\u540d\u79f0\uff1a'+ item.name + '<br>\u5168\u90e8\u53c2\u6570\uff1a'+ JSON.stringify(item));\r\n                console.log(item);\r\n            }\r\n            ,nodes: [ //\u8282\u70b9\r\n                { name: '\u5e38\u7528\u6587\u4ef6\u5939',id: 1,alias: 'changyong',children: [\r\n                    {name: '\u6240\u6709\u672a\u8bfb\uff08\u8bbe\u7f6e\u8df3\u8f6c\uff09',id: 11,href: 'http://www.layui.com/',alias: 'weidu'},\r\n                    {name: '\u7f6e\u9876\u90ae\u4ef6',id: 12},\r\n                    {name: '\u6807\u7b7e\u90ae\u4ef6',id: 13}\r\n                ]},\r\n                {name: '\u6211\u7684\u90ae\u7bb1',id: 2,spread: true,children: [\r\n                    {name: 'QQ\u90ae\u7bb1',id: 21,spread: true,children: [\r\n                        {name: '\u6536\u4ef6\u7bb1',id: 211,children: [\r\n                            {name: '\u6240\u6709\u672a\u8bfb',id: 2111},\r\n                            {name: '\u7f6e\u9876\u90ae\u4ef6',id: 2112},\r\n                            {name: '\u6807\u7b7e\u90ae\u4ef6',id: 2113}\r\n                        ]},\r\n                        {name: '\u5df2\u53d1\u51fa\u7684\u90ae\u4ef6',id: 212},\r\n                        {name: '\u5783\u573e\u90ae\u4ef6',id: 213}\r\n                    ]},\r\n                    {name: '\u963f\u91cc\u4e91\u90ae',id: 22,children: [\r\n                        {name: '\u6536\u4ef6\u7bb1',id: 221},\r\n                        {name: '\u5df2\u53d1\u51fa\u7684\u90ae\u4ef6',id: 222},\r\n                        {name: '\u5783\u573e\u90ae\u4ef6',id: 223}\r\n                    ]}\r\n                ]},\r\n                {name: '\u6536\u85cf\u5939',id: 3,alias: 'changyong',children: [\r\n                    {name: '\u7231\u60c5\u52a8\u4f5c\u7247',id: 31,alias: 'love'},\r\n                    {name: '\u6280\u672f\u6808',id: 12,children: [\r\n                        {name: '\u524d\u7aef',id: 121},\r\n                        {name: '\u5168\u7aef',id: 122}\r\n                    ]}\r\n                ]}\r\n            ]\r\n        });\r\n\r\n\r\n        //\u751f\u6210\u4e00\u4e2a\u6a21\u62df\u6811\r\n        /*var createTree = function(node, start){\r\n          node = node || function(){\r\n            var arr = [];\r\n            for(var i = 1; i < 10; i++){\r\n              arr.push({\r\n                name: i.toString().replace(/(\\d)/, '$1$1$1$1$1$1$1$1$1')\r\n              });\r\n            }\r\n            return arr;\r\n          }();\r\n          start = start || 1;\r\n          layui.each(node, function(index, item){\r\n            if(start < 10 && index < 9){\r\n              var child = [\r\n                {\r\n                  name: (1 + index + start).toString().replace(/(\\d)/, '$1$1$1$1$1$1$1$1$1')\r\n                }\r\n              ];\r\n              node[index].children = child;\r\n              createTree(child, index + start + 1);\r\n            }\r\n          });\r\n          return node;\r\n        };\r\n\r\n        layui.tree({\r\n          elem: '#demo2' //\u6307\u5b9a\u5143\u7d20\r\n          ,nodes: createTree()\r\n        });\r\n\r\n    });*/\r\n</script>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}