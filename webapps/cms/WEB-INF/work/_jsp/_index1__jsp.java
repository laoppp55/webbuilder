/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.security.Auth;
import com.bizwink.cms.security.SecurityCheck;
import com.bizwink.log.ILogManager;
import com.bizwink.log.LogPeer;
import com.bizwink.cms.news.Article;
import java.util.List;
import java.text.SimpleDateFormat;
import com.bizwink.log.PublishArticleListOfWeek;
import com.bizwink.log.PublishArticleCountOfDept;
import java.sql.Timestamp;
import java.util.Calendar;

public class _index1__jsp extends com.caucho.jsp.JavaPage
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

    String deptid = authToken.getDepartment();
    int siteid = authToken.getSiteID();
    String xdata = "[";
    String data = "[";
    String legendData = "[";
    String dataOfDept = "[";

    ILogManager logManager = LogPeer.getInstance();
    List<PublishArticleListOfWeek> articles = null;
    if (SecurityCheck.hasPermission(authToken,54))
        articles = logManager.getArticlesOfToday(siteid,null);
    else
        articles = logManager.getArticlesOfToday(siteid,deptid);

    //\u83b7\u53d6\u4ece\u4eca\u5929\u5f00\u59cb\u5f80\u524d\u4e00\u4e2a\u6708\u5404\u4e2a\u90e8\u95e8\u7684\u6587\u7ae0\u53d1\u5e03\u91cf\uff0c\u5e76\u8fdb\u884c\u5012\u5e8f\u6392\u5217
    List<PublishArticleCountOfDept> publishArticleCountOfDepts = logManager.getPublishArticleCountOFMonthOfDept(siteid);
    Timestamp today = new Timestamp(System.currentTimeMillis());
    Calendar start_cal = Calendar.getInstance();
    start_cal.setTimeInMillis(today.getTime());
    start_cal.add(Calendar.MONTH,-1);
    Timestamp starttime = new Timestamp(start_cal.getTimeInMillis());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    out.write(_jsp_string1, 0, _jsp_string1.length);
    pageContext.include("include/homepagemenu.jsp", false);out.write(_jsp_string2, 0, _jsp_string2.length);
     if (articles.size()>0) {
    out.write(_jsp_string3, 0, _jsp_string3.length);
    
                            int days = (articles.size()>3)?3:articles.size();
                            for(int ii=0;ii<days;ii++) {
                                PublishArticleListOfWeek article = articles.get(ii);
                                String createdate = article.getStrdate();
                                xdata = xdata + createdate + ",";
                                List<Article> tlist = article.getArticles();
                                int rows = tlist.size();
                                data = data + rows + ",";
                                Article article11 = tlist.get(0);
                        
    out.write(_jsp_string4, 0, _jsp_string4.length);
    out.print(((rows>2)?2:rows));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((createdate));
    out.write(_jsp_string6, 0, _jsp_string6.length);
    out.print(((rows>2)?2:rows));
    out.write(_jsp_string5, 0, _jsp_string5.length);
    out.print((article.getArticles().size()));
    out.write(_jsp_string7, 0, _jsp_string7.length);
    out.print((article11.getMainTitle()));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    for(int jj=1;jj<((rows>2)?2:rows);jj++) {
                            article11 = tlist.get(jj);
                        
    out.write(_jsp_string9, 0, _jsp_string9.length);
    out.print((article11.getMainTitle()));
    out.write(_jsp_string8, 0, _jsp_string8.length);
    }}
                            xdata = xdata.substring(0,xdata.length()-1) + "]";
                            data = data.substring(0,data.length()-1) + "]";
                            System.out.println(xdata);
                            System.out.println(data);
                        
    out.write(_jsp_string10, 0, _jsp_string10.length);
    }
    out.write(_jsp_string11, 0, _jsp_string11.length);
    out.print((simpleDateFormat.format(starttime) + "----" + simpleDateFormat.format(today)));
    out.write(_jsp_string12, 0, _jsp_string12.length);
    
                        int totalPublishArticleCount = 0;
                        for(int ii=0;ii<publishArticleCountOfDepts.size();ii++) {
                            PublishArticleCountOfDept publishArticleCountOfDept = publishArticleCountOfDepts.get(ii);
                            String deptname = publishArticleCountOfDept.getDeptname();
                            int publishArticleCount = publishArticleCountOfDept.getPublishArticleCount();
                            totalPublishArticleCount = totalPublishArticleCount + publishArticleCount;
                    
    out.write(_jsp_string13, 0, _jsp_string13.length);
    out.print((deptname));
    out.write(_jsp_string14, 0, _jsp_string14.length);
    out.print((publishArticleCount));
    out.write(_jsp_string15, 0, _jsp_string15.length);
    }
                        // {value: 50, name: '50%-\u5b66\u751f'},
                        for(int ii=0;ii<publishArticleCountOfDepts.size();ii++) {
                            PublishArticleCountOfDept publishArticleCountOfDept = publishArticleCountOfDepts.get(ii);
                            String deptname = publishArticleCountOfDept.getDeptname();
                            int publishArticleCount = publishArticleCountOfDept.getPublishArticleCount();
                            String percent = (double)Math.round( publishArticleCount/(double)totalPublishArticleCount*1000)/10+"%";
                            legendData = legendData + "'" + percent + deptname + "',";
                            dataOfDept = dataOfDept + "{value:" + publishArticleCount + ",name:'" + percent + "-" + deptname + "'},";
                        }
                        legendData = legendData.substring(0,legendData.length()-1) + "]";
                        dataOfDept = dataOfDept.substring(0,dataOfDept.length()-1) + "]";
                        System.out.println(legendData);
                        System.out.println(dataOfDept);
                    
    out.write(_jsp_string16, 0, _jsp_string16.length);
     if (articles.size()>0) {
    out.write(_jsp_string17, 0, _jsp_string17.length);
    out.print((xdata));
    out.write(_jsp_string18, 0, _jsp_string18.length);
    out.print((data));
    out.write(_jsp_string19, 0, _jsp_string19.length);
    }
    out.write(_jsp_string20, 0, _jsp_string20.length);
    out.print((legendData));
    out.write(_jsp_string21, 0, _jsp_string21.length);
    out.print((dataOfDept));
    out.write(_jsp_string22, 0, _jsp_string22.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("index1.jsp"), 1536823559690951015L, false);
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

  private final static char []_jsp_string21;
  private final static char []_jsp_string9;
  private final static char []_jsp_string8;
  private final static char []_jsp_string10;
  private final static char []_jsp_string6;
  private final static char []_jsp_string13;
  private final static char []_jsp_string12;
  private final static char []_jsp_string14;
  private final static char []_jsp_string7;
  private final static char []_jsp_string5;
  private final static char []_jsp_string4;
  private final static char []_jsp_string0;
  private final static char []_jsp_string20;
  private final static char []_jsp_string22;
  private final static char []_jsp_string17;
  private final static char []_jsp_string1;
  private final static char []_jsp_string3;
  private final static char []_jsp_string19;
  private final static char []_jsp_string15;
  private final static char []_jsp_string18;
  private final static char []_jsp_string11;
  private final static char []_jsp_string2;
  private final static char []_jsp_string16;
  static {
    _jsp_string21 = "\r\n        },//\u56fe\u4f8b\u5c5e\u6027\uff0c\u4ee5\u997c\u72b6\u56fe\u4e3a\u4f8b\uff0c\u7528\u6765\u8bf4\u660e\u997c\u72b6\u56fe\u6bcf\u4e2a\u6247\u533a\uff0cdata\u4e0e\u4e0b\u8fb9series\u4e2ddata\u76f8\u5339\u914d\r\n        graphic:{\r\n            type:'text',\r\n            left:'center',\r\n            top:'center',\r\n            style:{\r\n                text:'\u6587\u7ae0\u53d1\u5e03\u91cf\u90e8\u95e8\u6392\u884c\u7edf\u8ba1\\n'+'10', //\u4f7f\u7528\u201c+\u201d\u53ef\u4ee5\u4f7f\u6bcf\u884c\u6587\u5b57\u5c45\u4e2d\r\n                textAlign:'center',\r\n                font:'italic bolder 16px cursive',\r\n                fill:'#000',\r\n                width:30,\r\n                height:30\r\n            }\r\n        },//\u6b64\u4f8b\u997c\u72b6\u56fe\u4e3a\u5706\u73af\u4e2d\u5fc3\u6587\u5b57\u663e\u793a\u5c5e\u6027\uff0c\u8fd9\u662f\u4e00\u4e2a\u539f\u751f\u56fe\u5f62\u5143\u7d20\u7ec4\u4ef6\uff0c\u529f\u80fd\u5f88\u591a\r\n        series: [\r\n            {\r\n                name:'\u6587\u7ae0\u53d1\u5e03\u91cf\u7edf\u8ba1',//tooltip\u63d0\u793a\u6846\u4e2d\u663e\u793a\u5185\u5bb9\r\n                type: 'pie',//\u56fe\u5f62\u7c7b\u578b\uff0c\u5982\u997c\u72b6\u56fe\uff0c\u67f1\u72b6\u56fe\u7b49\r\n                radius: ['35%', '65%'],//\u997c\u56fe\u7684\u534a\u5f84\uff0c\u6570\u7ec4\u7684\u7b2c\u4e00\u9879\u662f\u5185\u534a\u5f84\uff0c\u7b2c\u4e8c\u9879\u662f\u5916\u534a\u5f84\u3002\u652f\u6301\u767e\u5206\u6bd4\uff0c\u672c\u4f8b\u8bbe\u7f6e\u6210\u73af\u5f62\u56fe\u3002\u5177\u4f53\u53ef\u4ee5\u770b\u6587\u6863\u6216\u6539\u53d8\u5176\u503c\u8bd5\u4e00\u8bd5\r\n                //roseType:'area',\u662f\u5426\u663e\u793a\u6210\u5357\u4e01\u683c\u5c14\u56fe\uff0c\u9ed8\u8ba4false\r\n                itemStyle: {\r\n                    normal:{\r\n                        label:{\r\n                            show:true,\r\n                            textStyle:{color:'#3c4858',fontSize:\"12\"},\r\n                            formatter:function(val){   //\u8ba9series \u4e2d\u7684\u6587\u5b57\u8fdb\u884c\u6362\u884c\r\n                                return val.name.split(\"-\").join(\"\\n\");}\r\n                        },//\u997c\u56fe\u56fe\u5f62\u4e0a\u7684\u6587\u672c\u6807\u7b7e\uff0c\u53ef\u7528\u4e8e\u8bf4\u660e\u56fe\u5f62\u7684\u4e00\u4e9b\u6570\u636e\u4fe1\u606f\uff0c\u6bd4\u5982\u503c\uff0c\u540d\u79f0\u7b49\u3002\u53ef\u4ee5\u4e0eitemStyle\u5c5e\u6027\u540c\u7ea7\uff0c\u5177\u4f53\u770b\u6587\u6863\r\n                        labelLine:{\r\n                            show:true,\r\n                            lineStyle:{color:'#3c4858'}\r\n                        }//\u7ebf\u6761\u989c\u8272\r\n                    },//\u57fa\u672c\u6837\u5f0f\r\n                    emphasis: {\r\n                        shadowBlur: 10,\r\n                        shadowOffsetX: 0,\r\n                        shadowColor: 'rgba(0, 0, 0, 0.5)',//\u9f20\u6807\u653e\u5728\u533a\u57df\u8fb9\u6846\u989c\u8272\r\n                        textColor:'#000'\r\n                    }//\u9f20\u6807\u653e\u5728\u5404\u4e2a\u533a\u57df\u7684\u6837\u5f0f\r\n                },\r\n                data: ".toCharArray();
    _jsp_string9 = "\r\n                        <tr style=\"height: 30px;background-color: #F7F7F7\">\r\n                            <td width=\"80%\">".toCharArray();
    _jsp_string8 = "</td>\r\n                        </tr>\r\n                        ".toCharArray();
    _jsp_string10 = "\r\n                    </table>\r\n                </div>\r\n                <div id=\"daypic\" style=\"padding-top:0px;padding-left: 550px;width:700px;height:250px;\"></div>\r\n            </div>\r\n            ".toCharArray();
    _jsp_string6 = "</td>\r\n                            <td width=\"10%\" rowspan=\"".toCharArray();
    _jsp_string13 = "\r\n                    <tr style=\"height: 30px;background-color: #F7F7F7\">\r\n                        <td width=\"50%\" align=\"center\">".toCharArray();
    _jsp_string12 = "\r\n                        </td>\r\n                    </tr>\r\n                    <tr style=\"height: 30px;background-color: #F7F7F7\">\r\n                        <td width=\"50%\" align=\"center\">\u90e8\u95e8\u540d\u79f0</td>\r\n                        <td width=\"50%\"  align=\"center\">\u4fe1\u606f\u53d1\u5e03\u91cf</td>\r\n                    </tr>\r\n                    ".toCharArray();
    _jsp_string14 = "</td>\r\n                        <td width=\"50%\"  align=\"center\">".toCharArray();
    _jsp_string7 = "</td>\r\n                            <td width=\"80%\">".toCharArray();
    _jsp_string5 = "\" align=\"center\">".toCharArray();
    _jsp_string4 = "\r\n                        <tr style=\"height: 30px;background-color: #F7F7F7\">\r\n                            <td width=\"10%\" rowspan=\"".toCharArray();
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string20 = "\r\n\r\n    var deptChart = echarts.init(document.getElementById('deptpic'));\r\n    optionOfDept = {\r\n        title:{\r\n            text:'\u6309\u7c7b\u578b\u7edf\u8ba1',\r\n            top:'bottom',\r\n            left:'center',\r\n            textStyle:{\r\n                fontSize: 12,\r\n                fontWeight: '',\r\n                color: '#333'\r\n            },\r\n        },//\u6807\u9898\r\n        tooltip: {\r\n            trigger: 'item',\r\n            formatter: \"{a} <br/>{b}: {c} ({d}%)\"\r\n        },//\u63d0\u793a\u6846\uff0c\u9f20\u6807\u60ac\u6d6e\u4ea4\u4e92\u65f6\u7684\u4fe1\u606f\u63d0\u793a\r\n        legend: {\r\n            show: false,\r\n            orient: 'vertical',\r\n            x: 'left',\r\n            data: ".toCharArray();
    _jsp_string22 = ",//\u6570\u636e\uff0c\u6570\u636e\u4e2d\u5176\u4ed6\u5c5e\u6027\uff0c\u67e5\u9605\u6587\u6863\r\n                color: ['#51CEC6','#FFB703','#5FA0FA'],//\u5404\u4e2a\u533a\u57df\u989c\u8272\r\n            },//\u6570\u7ec4\u4e2d\u4e00\u4e2a{}\u5143\u7d20\uff0c\u4e00\u4e2a\u56fe\uff0c\u4ee5\u6b64\u53ef\u4ee5\u505a\u51fa\u73af\u5f62\u56fe\r\n        ],//\u7cfb\u5217\u5217\u8868\r\n    };\r\n    deptChart.setOption(optionOfDept)\r\n</script>\r\n</body>\r\n</html>\r\n".toCharArray();
    _jsp_string17 = "\r\n    var myChart = echarts.init(document.getElementById('daypic'));\r\n    var option = {\r\n        tooltip: {},\r\n        xAxis: {\r\n            data: ".toCharArray();
    _jsp_string1 = "\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <title>\u7f51\u7ad9\u5185\u5bb9\u7ba1\u7406\u7cfb\u7edf</title>\r\n    <meta charset=\"utf-8\">\r\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\r\n    <link rel=\"stylesheet\" href=\"design/css/layui.css\">\r\n    <link rel=\"stylesheet\" href=\"css/mycss.css\">\r\n    <link rel=\"stylesheet\" href=\"css/cms_css.css\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/themes/default/easyui.css\">\r\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/themes/icon.css\">\r\n\r\n    <script type=\"text/javascript\" src=\"js/jquery-1.12.4.js\"></script>\r\n    <script type=\"text/javascript\" src=\"js/echarts.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"js/json2.js\"></script>\r\n    <script type=\"text/javascript\" src=\"design/layui.js\"></script>\r\n    <script type=\"text/javascript\" src=\"js/jquery.easyui.min.js\"></script>\r\n    <script type=\"text/javascript\" src=\"js/easyui-lang-zh_CN.js\"></script>\r\n\r\n    <!--\u5f15\u7528xtree-->\r\n    <script type=\"text/javascript\" src=\"design/layui-xtree.js\"></script>\r\n    <script>\r\n        $(document).ready(function(){\r\n            layui.use('element', function(){\r\n                var element = layui.element;\r\n            });\r\n        });\r\n        function goout() {\r\n            window.location.href = \"exit.jsp\";\r\n        }\r\n    </script>\r\n</head>\r\n<body class=\"layui-layout-body\">\r\n<div class=\"layui-layout layui-layout-admin\">\r\n    <div class=\"layui-header\">\r\n        <div class=\"layui-logo\">\u7f51\u7ad9\u5185\u5bb9\u7ba1\u7406\u7cfb\u7edf</div>\r\n        ".toCharArray();
    _jsp_string3 = "\r\n            <div id=\"deptbrowseid\" style=\"padding-bottom: 5px;\">\r\n                <div style=\"float: left\">\u6700\u8fd1\u4e09\u5929\u53d1\u5e03\u7684\u4fe1\u606f</div>  <div style=\"padding-left: 550px;\">&nbsp;<!--\u67e5\u770b\u66f4\u591a\u6392\u884c--></div>\r\n                <div style=\"float: left\">\r\n                    <table border=\"1\" width=\"100%\" align=\"center\">\r\n                        <tr style=\"height: 30px;background-color: #F7F7F7\">\r\n                            <td width=\"10%\" align=\"center\">\u53d1\u5e03\u65e5\u671f</td>\r\n                            <td width=\"10%\"  align=\"center\">\u53d1\u5e03\u6570\u91cf</td>\r\n                            <td width=\"80%\">\u6587\u7ae0\u6807\u9898</td>\r\n                        </tr>\r\n                        ".toCharArray();
    _jsp_string19 = "\r\n        }]\r\n    };\r\n    myChart.setOption(option);\r\n    ".toCharArray();
    _jsp_string15 = "</td>\r\n                    </tr>\r\n                    ".toCharArray();
    _jsp_string18 = "\r\n        },\r\n        yAxis: {\r\n            type: 'value',\r\n            name: '\u53d1\u5e03\u91cf',\r\n            // min: 0,\r\n            // max: 250,\r\n            // interval: 10, //\u95f4\u9694\r\n            minInterval: 1, //\u8bbe\u7f6e\u62101\u4fdd\u8bc1\u5750\u6807\u8f74\u5206\u5272\u523b\u5ea6\u663e\u793a\u6210\u6574\u6570\u3002\r\n            axisLabel: {\r\n                formatter: '{value}'\r\n            }\r\n        },\r\n        series: [{\r\n            name: '\u6700\u8fd17\u5929\u6587\u7ae0\u53d1\u5e03\u91cf',\r\n            type: 'line',\r\n            data: ".toCharArray();
    _jsp_string11 = "\r\n\r\n            <!--div id=\"deptpubid\" style=\"padding-bottom: 5px\"><img src=\"images/deptpub.png\"></div-->\r\n            <div id=\"deptpubofmonthid\" style=\"padding-bottom: 5px;width: 550px;float:left;\">\r\n                <table border=\"1\" width=\"100%\" align=\"center\">\r\n                    <tr style=\"height: 30px;background-color: #F7F7F7\">\r\n                        <td colspan=\"2\" align=\"center\">\r\n                            ".toCharArray();
    _jsp_string2 = "    </div>\r\n\r\n    <div class=\"layui-body\" style=\"left: 30px;top:80px;\">\r\n        <!-- \u5185\u5bb9\u4e3b\u4f53\u533a\u57df -->\r\n        <div id=\"contentid\">\r\n            <!--div id=\"searchtimeid\" style=\"padding-bottom: 20px\">\r\n                \u5f00\u59cb\u65f6\u95f4\uff1a<input type=text  class=\"easyui-datetimebox\" name=\"bdatetime\" value=\"\" style=\"width:200px\">\r\n                \u7ed3\u675f\u65f6\u95f4\uff1a<input type=text  class=\"easyui-datetimebox\" name=\"edatetime\" value=\"\" style=\"width:200px\">\r\n                          <input type=\"button\" name=\"queryPublisArticles\" value=\"\u67e5\u8be2\" class=\"easyui-linkbutton\">\r\n            </div-->\r\n\r\n\r\n            ".toCharArray();
    _jsp_string16 = "\r\n                </table>\r\n                <!--img src=\"images/1111.jpg\"-->\r\n            </div>\r\n            <div id=\"deptpic\" style=\"padding-left: 550px;padding-bottom: 10px;width:700px;height:400px;\"></div>\r\n        </div>\r\n    </div>\r\n\r\n    <div class=\"layui-footer\" style=\"left: 0px;\">\r\n    </div>\r\n</div>\r\n<script type=\"text/javascript\">\r\n    ".toCharArray();
  }
}