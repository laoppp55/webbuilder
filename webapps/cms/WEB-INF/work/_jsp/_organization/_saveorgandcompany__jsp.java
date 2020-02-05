/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._organization;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import com.bizwink.cms.util.SessionUtil;
import com.bizwink.cms.security.Auth;
import com.bizwink.cms.util.ParamUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import java.util.Iterator;
import com.bizwink.po.Companyinfo;
import java.util.List;
import java.util.ArrayList;
import com.bizwink.util.SpringInit;
import org.springframework.context.ApplicationContext;
import com.bizwink.service.OrganizationService;
import java.util.Map;
import com.bizwink.error.ErrorMessage;
import com.google.gson.Gson;
import java.net.URLDecoder;

public class _saveorgandcompany__jsp extends com.caucho.jsp.JavaPage
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
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=\u7cfb\u7edf\u8d85\u65f6\uff0c\u8bf7\u91cd\u65b0\u767b\u9646!"));
        return;
    }

    request.setCharacterEncoding("utf-8");

    String jsondata = URLDecoder.decode(ParamUtil.getParameter(request,"compinfos"),"utf-8");
    System.out.println(jsondata);
    int pid = ParamUtil.getIntParameter(request,"pid",0);
    List<Companyinfo> comps = new ArrayList<Companyinfo>();
    String mphone = null;             //\u516c\u53f8\u8054\u7cfb\u4eba\u624b\u673a\u53f7\u7801
    String email = null;              //\u516c\u53f8\u8054\u7cfb\u4eba\u7535\u5b50\u90ae\u4ef6
    String compname = null;           //\u516c\u53f8\u540d\u79f0
    String shortcompname = null;      //\u516c\u53f8\u7b80\u79f0
    String compcode = null;           //\u516c\u53f8\u5185\u90e8\u7f16\u7801
    String legal = null;              //\u516c\u53f8\u8d1f\u8d23\u4eba\u59d3\u540d
    String tprovince = null;          //\u516c\u53f8\u6240\u5728\u7701
    String tcity = null;              //\u516c\u53f8\u6240\u5728\u5e02
    String tcounty = null;            //\u516c\u53f8\u6240\u5728\u53bf
    String address = null;            //\u516c\u53f8\u8be6\u7ec6\u5730\u5740
    String postcode = "";             //\u516c\u53f8\u90ae\u653f\u7f16\u7801
    String contactor = "";            //\u516c\u53f8\u8054\u7cfb\u4eba
    String phone = "";                //\u516c\u53f8\u8054\u7cfb\u7535\u8bdd\u53f7\u7801
    String engcompname = "";          //\u516c\u53f8\u82f1\u6587\u540d\u79f0
    String creditcode = "";           //\u516c\u53f8\u7edf\u4e00\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801
    String comptype = "";             //\u516c\u53f8\u7c7b\u578b

    //\u4fdd\u5b58\u521b\u5efa\u7ec4\u7ec7\u67b6\u6784\u4fe1\u606f\u8fd4\u56de\u7684\u7ed3\u679c\uff0c\u6210\u529f\u521b\u5efa\u8fd4\u56de\u521b\u5efa\u7684\u7ec4\u7ec7\u67b6\u6784\u4fe1\u606f\uff0c\u672a\u6210\u529f\u521b\u5efa\u8fd4\u56de\u5931\u8d25\u7684\u539f\u56e0\uff0c\u5931\u8d25\u539f\u56e0\u4fdd\u5b58\u5728ErrorMessage\u5bf9\u8c61\u4e2d
    Map result = null;

    if (jsondata!=null) {
        JSONObject jsStr = JSONObject.fromObject(jsondata);
        JSONArray jsonArray = jsStr.getJSONArray("rows");
        Iterator<Object> it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            mphone = ob.getString("mphone");
            email = ob.getString("email");
            compname = ob.getString("compname");
            shortcompname = ob.getString("shortcompname");
            compcode = ob.getString("compcode");
            legal = ob.getString("legal");
            tprovince = ob.getString("tprovince");
            tcity = ob.getString("tcity");
            tcounty = ob.getString("tcounty");
            address = ob.getString("address");
            postcode = ob.getString("postcode");
            contactor = ob.getString("contactor");
            phone = ob.getString("phone");
            engcompname = ob.getString("engcompname");
            creditcode = ob.getString("creditcode");
            comptype = ob.getString("comptype");

            Companyinfo companyinfo = new Companyinfo();
            companyinfo.setCOMPANYNAME(compname);
            companyinfo.setMPHONE(mphone);
            companyinfo.setLEGAL(legal);
            companyinfo.setCOMPANYEMAIL(email);
            companyinfo.setCOMPANYADDRESS(address);
            companyinfo.setCOMPCODE(compcode);
            companyinfo.setCOMPSHORTNAME(shortcompname);
            companyinfo.setPROVINCE(tprovince);
            companyinfo.setCITY(tcity);
            companyinfo.setCOUNTRY(tcounty);
            companyinfo.setPOSTCODE(postcode);
            companyinfo.setCONTACTOR(contactor);
            companyinfo.setCOMPANYPHONE(phone);
            companyinfo.setCOMPENGNAME(engcompname);
            companyinfo.setCOMPCREDITCODE(creditcode);
            companyinfo.setCOMPTYPE(comptype);

            comps.add(companyinfo);
        }
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
            result = organizationService.saveOrganizationAndCompany(comps,authToken.getSiteID(),authToken.getUserID(),0,pid);
        }
    }

    Gson gson = new Gson();
    if(result !=null) {
        ErrorMessage errorMessage = (ErrorMessage)result.get("error");
        if (errorMessage.getErrcode()==0) {
            String json_result = gson.toJson(result);
            System.out.println(json_result);
            out.print(json_result);
        } else {
            String json_result = gson.toJson(errorMessage);
            System.out.println(json_result);
            out.print(json_result);
        }
    } else {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(-4);
        errorMessage.setErrmsg("\u672a\u77e5\u7684\u9519\u8bef\uff0c\u6ca1\u6709\u8fd4\u56de\u521b\u5efa\u7684\u7ec4\u7ec7\u67b6\u6784\u7ed3\u679c\u6570\u636e");
        errorMessage.setModelname("\u521b\u5efa\u7ec4\u7ec7\u67b6\u6784\u6a21\u5757");
        String json_result = gson.toJson(errorMessage);
        System.out.println(json_result);
        out.print(json_result);
    }
    out.flush();

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("organization/saveOrgAndCompany.jsp"), -6370799392049298977L, false);
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

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
  }
}
