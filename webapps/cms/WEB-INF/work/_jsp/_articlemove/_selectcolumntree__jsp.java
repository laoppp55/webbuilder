/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._articlemove;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.security.Auth;
import com.bizwink.cms.util.ParamUtil;
import java.util.List;
import java.util.ArrayList;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.node;

public class _selectcolumntree__jsp extends com.caucho.jsp.JavaPage
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
    int sitetype = authToken.getSitetype();
    int articleid = ParamUtil.getIntParameter(request,"articleid",0);
    Tree colTree = null;

    if (sitetype == 0 || sitetype == 2) {                             //0\u8868\u793a\u81ea\u5df1\u521b\u5efa\u7684\u7f51\u7ad9\uff0c2\u8868\u793a\u5b8c\u6574\u62f7\u8d1d\u6a21\u677f\u7f51\u7ad9
        colTree = TreeManager.getInstance().getSiteTree(siteid);
    } else {                                                           //1\u8868\u793a\u5171\u4eab\u6a21\u677f\u7f51\u7ad9\u7684\u680f\u76ee\u548c\u6a21\u677f
        colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid,samsiteid);
    }

    //\u6784\u5efa\u680f\u76ee\u6811\u7684JSON\u6570\u636e
    StringBuffer buf = new StringBuffer();                        //\u5b58\u50a8\u751f\u6210\u7684\u83dc\u5355\u6811
    if (colTree.getNodeNum() > 1) {
        node[] treeNodes = colTree.getAllNodes();                     //\u83b7\u53d6\u8be5\u6811\u7684\u6240\u6709\u8282\u70b9
        List<Integer> pid = new ArrayList<Integer>();
        int currentID = 0;                                            //\u5f53\u524d\u6b63\u5728\u5904\u7406\u7684\u8282\u70b9
        int i = 0;                                                      //\u5faa\u73af\u53d8\u91cf
        int[] ordernum = new int[colTree.getNodeNum()];               //\u5f53\u524d\u8282\u70b9\u6240\u6709\u5b50\u8282\u70b9\u7684\u987a\u5e8f\u53f7
        int nodenum = 1;                                              //\u5f53\u524d\u88ab\u5904\u7406\u8282\u70b9\u7684\u521d\u59cb\u503c
        int subnodenum = 1;                                              //\u5224\u65ad\u5f53\u524d\u8282\u70b9\u662f\u5426\u6709\u5b50\u8282\u70b9
        int subnodenumOfParentNode = 0;

        pid.add(0);
        buf.append("[");
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

            //\u4ece\u5904\u7406\u7684\u8282\u70b9\u6570\u7ec4\u4e2d\u53d6\u51fa\u5f53\u524d\u6b63\u5728\u5904\u7406\u7684\u5143\u7d20\uff0c\u67e5\u627e\u8be5\u5143\u7d20\u4e0b\u7684\u5b50\u5143\u7d20
            //\u8bbe\u7f6e\u6240\u6709\u5b50\u8282\u70b9\u7684\u7236\u83dc\u5355\u540d\u79f0\uff0c\u8bbe\u7f6e\u6240\u6709\u5b50\u8282\u70b9\u7684\u5e8f\u5217\u53f7\uff0c\u628a\u6240\u6709\u7684\u5b50\u8282\u70b9\u5b58\u5165pid\u6570\u7ec4\u4e2d
            subnodenum = 0;
            nodenum = nodenum - 1;
            for (i = colTree.getNodeNum() - 1; i >= 0; i--) {
                if (treeNodes[i].getLinkPointer() == currentID) {
                    nodenum = nodenum + 1;
                    pid.add(0, treeNodes[i].getId());
                    ordernum[nodenum] = subnodenum;
                    subnodenum = subnodenum + 1;
                }
            }

            //\u5904\u7406\u5f53\u524d\u8282\u70b9
            if (currentID != 0) {
                //\u5f53\u524d\u8282\u70b9\u7684\u7236\u8282\u70b9\u7684\u5b50\u8282\u70b9\u6570\u91cf\u51cf1
                subnodenumOfParentNode = treeNodes[currentNode_ParentNodeIndex].getSubnodes() - 1;
                //\u4fee\u6539\u5f53\u524d\u8282\u70b9\u7236\u8282\u70b9\u672a\u5904\u7406\u7684\u5b50\u8282\u70b9\u7684\u6570\u91cf
                treeNodes[currentNode_ParentNodeIndex].setSubnodes(subnodenumOfParentNode);
                //\u8bbe\u7f6e\u5f53\u524d\u8282\u70b9\u5b50\u8282\u70b9\u7684\u6570\u91cf
                treeNodes[currentNodeIndex].setSubnodes(subnodenum);
                //\u5904\u7406\u5f53\u524d\u8282\u70b9\u6ca1\u6709\u5b50\u8282\u70b9\u7684\u60c5\u51b5
                if (subnodenum == 0) {
                    //\u5f53\u524d\u8282\u70b9\u662f\u7236\u8282\u70b9\u7684\u6700\u540e\u4e00\u4e2a\u8282\u70b9,\u5faa\u73af\u5411\u6811\u5f62\u7ed3\u6784\u7684\u4e0a\u5c42\u8fdb\u884c\u67e5\u627e\uff0c\u4e00\u76f4\u627e\u5230\u5b50\u8282\u70b9\u6570\u5c1a\u4e0d\u662f\u96f6\u7684\u8282\u70b9\uff0c\u4ece\u4e0d\u662f\u96f6\u7684\u8282\u70b9\u5411\u4e0b\u5199\u8282\u70b9\u7684\u7ed3\u675f\u90e8\u5206
                    if (subnodenumOfParentNode == 0) {
                        buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "}\r");
                        //\u5bfb\u627e\u5f53\u524d\u8282\u70b9\u5411\u4e0a\u7684\u8def\u5f84\u4e0a\u5b50\u8282\u70b9\u6570\u4e0d\u662f\u96f6\u7684\u8282\u70b9
                        int tnum = 0;
                        int level = 0;
                        while (tnum == 0 && treeNodes[currentNodeIndex].getLinkPointer() > 0) {
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
                        for (i = 0; i < level-1; i++) buf.append("]}\r");

                        buf.append("]},\r");
                    } else {                         //\u5f53\u524d\u8282\u70b9\u4e0d\u662f\u7236\u8282\u70b9\u7684\u6700\u540e\u4e00\u4e2a\u8282\u70b9
                        buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "},\r");
                    }
                } else {
                    if (treeNodes[currentNodeIndex].getLinkPointer()==0)
                        buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + ",spread: true," + "children:[\r");
                    else
                        buf.append("{name:'" + treeNodes[currentNodeIndex].getChName() + "',id:" + currentID + "," + "children:[\r");
                }
            }
        }

        //\u53bb\u6389\u5b57\u7b26\u4e32\u6700\u540e\u4e00\u4e2a\u591a\u4f59\u7684\u201c,\u201d\u5b57\u7b26\uff0c\u589e\u52a0json\u7684\u5173\u95ed\u5b57\u7b26\u201c];\u201d
        int posi = buf.lastIndexOf(",");
        if (posi>-1) {
            buf.delete(posi,buf.length());
            buf.append("\r]");
        }
    }

    out.write(_jsp_string1, 0, _jsp_string1.length);
    out.print((buf.toString()));
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("articlemove/selectColumnTree.jsp"), -7968954906871360396L, false);
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

  private final static char []_jsp_string2;
  private final static char []_jsp_string1;
  private final static char []_jsp_string0;
  static {
    _jsp_string2 = ";\r\n\r\n        function setFontCss(treeId, node) {\r\n            //\u5982\u679c\u8282\u70b9id==0\u5219\u662f\u7ea2\u8272\u5b57\u4f53\uff08\u8fd9\u91cc\u81ea\u5df1\u5199\u81ea\u5df1\u7684\u5224\u65ad\u903b\u8f91\u5c31\u53ef\u4ee5\u4e86\uff09\r\n            //if(node.level==0){\r\n            //    return {\"color\":\"red\"};\r\n            //}else{\r\n            return {\"color\":\"white\"};\r\n            //}\r\n        }\r\n\r\n        function setCheck() {\r\n            //var zTree = $.fn.zTree.getZTreeObj(\"treeId\"), type = { \"Y\":\"ps\", \"N\":\"ps\"};\r\n            var zTree = $.fn.zTree.getZTreeObj(\"treeId\"), type = { \"Y\":\"\", \"N\":\"\"};\r\n            zTree.setting.check.chkboxType = type;\r\n        }\r\n\r\n        function isEmpty(obj){\r\n            if(typeof obj == \"undefined\" || obj == null || obj == \"\"){\r\n                return true;\r\n            }else{\r\n                return false;\r\n            }\r\n        }\r\n\r\n        $(document).ready(function(){\r\n            layui.use('element', function(){\r\n                var element = layui.element;\r\n            });\r\n\r\n            $.fn.zTree.init($(\"#treeId\"), setting, zNodes);\r\n            setCheck();\r\n            var treeObj = $.fn.zTree.getZTreeObj(\"treeId\");\r\n            var nodes = treeObj.getNodes();\r\n            if (nodes.length>0) {\r\n                for (var i = 0; i < nodes.length; i++) {\r\n                    treeObj.expandNode(nodes[i], true, false, false);//\u9ed8\u8ba4\u5c55\u5f00\u7b2c\u4e00\u7ea7\u8282\u70b9\r\n                }\r\n            }\r\n        });\r\n\r\n        function onCheck(e, treeId, treeNode) {\r\n            //alert(\"[ \"+getTime()+\" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;\" + treeNode.name );\r\n            var zTree = $.fn.zTree.getZTreeObj(\"treeId\");\r\n            var node = zTree.getNodeByParam(\"id\",treeNode.id);\r\n            if (node.checked) {\r\n                window.opener.setupTargetColumn(treeNode.id,treeNode.name);\r\n                window.close();\r\n            }\r\n        }\r\n\r\n        function getTime() {\r\n            var now= new Date(),\r\n                h=now.getHours(),\r\n                m=now.getMinutes(),\r\n                s=now.getSeconds(),\r\n                ms=now.getMilliseconds();\r\n            return (h+\":\"+m+\":\"+s+ \" \" +ms);\r\n        }\r\n        //-->\r\n    </SCRIPT>\r\n</head>\r\n<body class=\"layui-layout-body\">\r\n<div class=\"layui-layout layui-layout-admin\">\r\n    <div class=\"layui-side layui-bg-black\" id=\"sidetreeid\" style=\"top: 0px;width: 600px;padding-left: 200px;\">\r\n        <div id=\"coltreeid\">\r\n            <ul id=\"treeId\" class=\"ztree\"></ul>\r\n        </div>\r\n    </div>\r\n</div>\r\n<script>\r\n    layui.use(['element', 'tree', 'laydate'], function(){\r\n        var $ = layui.jquery;\r\n        var laydate = layui.laydate;\r\n    });\r\n</script>\r\n</body>\r\n</html>".toCharArray();
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n    <title>\u7f51\u7ad9\u5185\u5bb9\u7ba1\u7406\u7cfb\u7edf--\u6587\u7ae0\u7ba1\u7406</title>\r\n    <link rel=\"stylesheet\" href=\"../css/cms_css.css\">\r\n    <link rel=\"stylesheet\" href=\"../css/jquery.msgbox.css\" type=\"text/css\" />\r\n    <link rel=\"stylesheet\" href=\"../design/css/layui.css\">\r\n    <link rel=\"stylesheet\" href=\"../css/zTreeStyle/zTreeStyle.css\">\r\n    <script type=\"text/javascript\" src=\"../js/jquery-1.12.4.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/json2.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.ztree.core-3.5.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../js/jquery.ztree.excheck-3.5.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"../design/layui.js\"></script>\r\n    <SCRIPT type=\"text/javascript\">\r\n        <!--\r\n        var setting = {\r\n            view: {\r\n                selectedMulti: true,\r\n                showLine:false,\r\n                fontCss:setFontCss\r\n            },\r\n            check: {\r\n                enable: true\r\n            },\r\n            data: {\r\n                simpleData: {\r\n                    enable: true\r\n                }\r\n            },\r\n            callback: {\r\n                //beforeCheck: beforeCheck,\r\n                onCheck: onCheck\r\n            }\r\n        };\r\n\r\n        var zNodes =".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}
