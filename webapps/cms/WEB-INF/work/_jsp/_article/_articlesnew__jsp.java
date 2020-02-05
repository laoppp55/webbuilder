/*
 * JSP generated by Resin-4.0.58 (built Fri, 24 Aug 2018 01:23:14 PDT)
 */

package _jsp._article;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.tree.*;
import com.google.gson.*;
import com.bizwink.cms.util.*;
import com.bizwink.util.SpringInit;
import org.springframework.context.ApplicationContext;
import com.bizwink.util.JSON;
import com.bizwink.po.Article;
import com.bizwink.service.ArticleService;
import java.math.BigDecimal;
import java.util.Map;
import java.net.URLDecoder;
import com.bizwink.vo.VoArticle;
import com.bizwink.cms.refers.RefersPeer;
import com.bizwink.cms.refers.IRefersManager;
import com.bizwink.cms.orderArticleListManager.orderArticleException;
import com.bizwink.service.DeptService;
import com.bizwink.po.Department;
import com.bizwink.service.OrganizationService;
import com.bizwink.po.Organization;

public class _articlesnew__jsp extends com.caucho.jsp.JavaPage
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

    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String sitename = authToken.getSitename();
    String username = authToken.getUserID();
    int rightid = ParamUtil.getIntParameter(request,"right",0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int pageno = ParamUtil.getIntParameter(request, "page", 1);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int msgno = ParamUtil.getIntParameter(request, "msgno", 1);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearch = ParamUtil.getBooleanParameter(request, "doSearch");
    int auditflag = ParamUtil.getIntParameter(request, "audit", 0);                       //\u67e5\u8be2\u5f85\u5ba1\u7a3f\u4ef6\u548c\u9000\u56de\u7a3f\u4ef6\uff0c\u503c\u4e3a0\u4ee3\u8868\u4e0d\u9700\u8981\u5ba1\u6838\u7684\u7a3f\u4ef6\u6216\u8005\u5ba1\u6838\u901a\u8fc7\u7684\u7a3f\u4ef6
    int articleid = ParamUtil.getIntParameter(request,"articleid",0);
    String maintitle = ParamUtil.getParameter(request,"title");
    if (maintitle!=null) maintitle = filter.excludeHTMLCode(URLDecoder.decode(maintitle,"utf-8"));
    String vicetitle = ParamUtil.getParameter(request,"vicetitle");
    if (vicetitle!=null) vicetitle = filter.excludeHTMLCode(URLDecoder.decode(vicetitle,"utf-8"));
    String author = ParamUtil.getParameter(request,"author");
    if (author!=null) author = filter.excludeHTMLCode(URLDecoder.decode(author,"utf-8"));
    String source = ParamUtil.getParameter(request,"source");
    if (source!=null) source = filter.excludeHTMLCode(URLDecoder.decode(source,"utf-8"));
    String spubdate = ParamUtil.getParameter(request,"spubdate");
    if (spubdate!=null) spubdate = filter.excludeHTMLCode(URLDecoder.decode(spubdate,"utf-8"));
    String epubdate = ParamUtil.getParameter(request,"epubdate");
    if (epubdate!=null) epubdate = filter.excludeHTMLCode(URLDecoder.decode(epubdate,"utf-8"));
    int arttypeval = ParamUtil.getIntParameter(request,"arttype",-1);

    //System.out.println("\u7ad9\u70b9\u7ba1\u7406\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,54));
    //System.out.println("\u8bfb\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,50));
    //System.out.println("\u5199\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,51));
    //System.out.println("\u5220\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,52));
    //System.out.println("\u8bfb\u90e8\u95e8\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,63));
    //System.out.println("\u4fee\u6539\u90e8\u95e8\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,64));
    //System.out.println("\u5220\u9664\u90e8\u95e8\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,65));
    //System.out.println("\u53d1\u5e03\u90e8\u95e8\u6587\u7ae0\u6743\u9650\uff1a"+SecurityCheck.hasPermission(authToken,66));

    if (SecurityCheck.hasPermission(authToken,54) || SecurityCheck.hasPermission(authToken,50)|| SecurityCheck.hasPermission(authToken,51)|| SecurityCheck.hasPermission(authToken,52)) {
        username = null;
    }

    String deptname = null;
    boolean hasDeptRight = false;
    if(SecurityCheck.hasPermission(authToken,63) || SecurityCheck.hasPermission(authToken,64)|| SecurityCheck.hasPermission(authToken,65)|| SecurityCheck.hasPermission(authToken,66)) {
        //\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u7528\u6237\u6240\u5c5e\u90e8\u95e8
        int deptid = authToken.getDeptid();
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
            DeptService deptService = (DeptService)appContext.getBean("deptService");
            Department department = deptService.getDepartmentByDeptID(BigDecimal.valueOf(deptid));
            BigDecimal orgid = department.getORGID();
            Organization organization = organizationService.getAOrganization(orgid);
            Organization rootOrg = organizationService.getRootOrganization(BigDecimal.valueOf(siteid));
            while (organization.getPARENT().intValue()!=rootOrg.getID().intValue()) {
                organization = organizationService.getAOrganization(organization.getPARENT());
            }
            List<Department> departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteid),organization.getID());
            deptname = departments.get(0).getCNAME();
        }
        hasDeptRight = true;
    }

    List<Article> articleList = new ArrayList<>();
    int totalCount=0;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        ArticleService articleService = (ArticleService) appContext.getBean("articleService");
        if (!doSearch) {
            if (!hasDeptRight) {
                //\u7528\u6237\u6ca1\u6709\u8bbe\u7f6e\u5904\u7406\u90e8\u95e8\u6587\u7ae0\u7684\u6743\u9650\uff0c\u7528\u6237\u540d\u4e3a\u7a7a\u8868\u793a\u53ef\u4ee5\u5904\u7406\u6240\u6709\u6587\u7ae0\uff0c\u7528\u6237\u540d\u4e0d\u4e3a\u7a7a\uff0c\u8868\u793a\u53ea\u80fd\u5904\u7406\u81ea\u5df1\u7684\u6587\u7ae0
                articleList = articleService.getArticlesInPageByColumnids(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval), BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), flag, ascdesc);
                totalCount = articleService.getArticlesCountByColumnids(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval));
            } else {
                //\u7528\u6237\u8bbe\u7f6e\u4e86\u90e8\u95e8\u6587\u7ae0\u5904\u7406\u6743\u9650\uff0c\u7528\u6237\u53ef\u4ee5\u5904\u7406\u672c\u680f\u76ee\u4e0b\u6240\u6709\u672c\u90e8\u95e8\u7f16\u8f91\u5f55\u5165\u7684\u6587\u7ae0
                articleList = articleService.getArticlesInPageByDeptAndColumnids(deptname, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval), BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), flag, ascdesc);
                totalCount = articleService.getArticlesCountByDeptAndColumnids(deptname, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval));
            }
        } else {
            if (!hasDeptRight) {
                //\u7528\u6237\u6ca1\u6709\u8bbe\u7f6e\u5904\u7406\u90e8\u95e8\u6587\u7ae0\u7684\u6743\u9650\uff0c\u7528\u6237\u540d\u4e3a\u7a7a\u8868\u793a\u53ef\u4ee5\u67e5\u8be2\u6240\u6709\u6587\u7ae0\uff0c\u7528\u6237\u540d\u4e0d\u4e3a\u7a7a\uff0c\u8868\u793a\u53ea\u80fd\u67e5\u8be2\u81ea\u5df1\u7684\u6587\u7ae0
                articleList = articleService.getSearchArticlesInPage(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),
                        BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), BigDecimal.valueOf(articleid), maintitle, vicetitle, author, source,
                        spubdate, epubdate, BigDecimal.valueOf(arttypeval), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(flag), BigDecimal.valueOf(ascdesc));
                totalCount = articleService.getSearchArticlesCount(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(articleid), maintitle,
                        vicetitle, author, source, spubdate, epubdate, BigDecimal.valueOf(arttypeval), BigDecimal.valueOf(auditflag));
            } else {
                //\u7528\u6237\u8bbe\u7f6e\u4e86\u90e8\u95e8\u6587\u7ae0\u5904\u7406\u6743\u9650\uff0c\u7528\u6237\u53ef\u4ee5\u67e5\u8be2\u672c\u680f\u76ee\u4e0b\u6240\u6709\u672c\u90e8\u95e8\u7f16\u8f91\u5f55\u5165\u7684\u6587\u7ae0
                articleList = articleService.getSearchArticlesInPageByDept(deptname, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),
                        BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), BigDecimal.valueOf(articleid), maintitle, vicetitle, author, source,
                        spubdate, epubdate, BigDecimal.valueOf(arttypeval), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(flag), BigDecimal.valueOf(ascdesc));
                totalCount = articleService.getSearchArticlesCountByDept(deptname, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(articleid), maintitle,
                        vicetitle, author, source, spubdate, epubdate, BigDecimal.valueOf(arttypeval), BigDecimal.valueOf(auditflag));
            }
        }
    }

    IRefersManager refersMgr = RefersPeer.getInstance();
    List<VoArticle> voArticleList = new ArrayList<>();
    for(int ii=0;ii<articleList.size();ii++) {
        Article article = articleList.get(ii);
        VoArticle voArticle = new VoArticle();
        voArticle.setID(article.getID());
        voArticle.setMAINTITLE(article.getMAINTITLE());
        voArticle.setVICETITLE(article.getVICETITLE());
        voArticle.setSOURCE(article.getSOURCE());
        voArticle.setSUMMARY(article.getSUMMARY());
        voArticle.setKEYWORD(article.getKEYWORD());
        voArticle.setAUTHOR(article.getAUTHOR());
        voArticle.setEDITOR(article.getEDITOR());
        voArticle.setCOLUMNID(article.getCOLUMNID());
        voArticle.setSORTID(article.getSORTID());
        voArticle.setCREATEDATE(article.getCREATEDATE());
        voArticle.setLASTUPDATED(article.getLASTUPDATED());
        voArticle.setSTATUS(article.getSTATUS());
        voArticle.setDOCLEVEL(article.getDOCLEVEL());
        voArticle.setAUDITFLAG(article.getAUDITFLAG());
        voArticle.setSUBSCRIBER(article.getSUBSCRIBER());
        voArticle.setEMPTYCONTENTFLAG(article.getEMPTYCONTENTFLAG());
        voArticle.setLOCKSTATUS(article.getLOCKSTATUS());
        voArticle.setLOCKEDITOR(article.getLOCKEDITOR());
        voArticle.setDIRNAME(article.getDIRNAME());
        voArticle.setFILENAME(article.getFILENAME());
        voArticle.setPUBLISHTIME(article.getPUBLISHTIME());
        voArticle.setAUDITOR(article.getAUDITOR());
        voArticle.setRELATEDARTID(article.getRELATEDARTID());
        voArticle.setSALEPRICE(article.getSALEPRICE());
        voArticle.setVIPPRICE(article.getVIPPRICE());
        voArticle.setINPRICE(article.getINPRICE());
        voArticle.setMARKETPRICE(article.getMARKETPRICE());
        voArticle.setSCORE(article.getSCORE());
        voArticle.setVOUCHER(article.getVOUCHER());
        voArticle.setBRAND(article.getBRAND());
        voArticle.setBIGPIC(article.getBIGPIC());
        voArticle.setPIC(article.getPIC());
        voArticle.setWEIGHT(article.getWEIGHT());
        voArticle.setSTOCKNUM(article.getSTOCKNUM());
        voArticle.setVICEDOCLEVEL(article.getVICEDOCLEVEL());
        voArticle.setREFERID(article.getREFERID());
        voArticle.setMODELID(article.getMODELID());
        voArticle.setARTICLEPIC(article.getARTICLEPIC());
        voArticle.setURLTYPE(article.getURLTYPE());
        voArticle.setISJOINRSS(article.getISJOINRSS());
        voArticle.setDEFINEURL(article.getDEFINEURL());
        voArticle.setBEIDATE(article.getBEIDATE());
        voArticle.setSALESNUM(article.getSALESNUM());
        voArticle.setNOTEARTICLEID(article.getNOTEARTICLEID());
        voArticle.setMEDIAFILE(article.getMEDIAFILE());
        voArticle.setMULTIMEDIATYPE(article.getMULTIMEDIATYPE());
        voArticle.setPROCESSOFAUDIT(article.getPROCESSOFAUDIT());
        voArticle.setSITEID(article.getSITEID());
        if (article.getIsown().intValue() == 1)
            voArticle.setIsown(true);
        else
            voArticle.setIsown(false);
        int articleCID = article.getCOLUMNID().intValue();
        articleid = article.getID().intValue();

        try {
            int pubflag = 0;
            if(voArticle.isIsown())
                pubflag = article.getPUBFLAG();
            else
                pubflag = refersMgr.getRefersArticlePubFlag(articleid, columnID,siteid);
            voArticle.setPUBFLAG((short)pubflag);

            String refersFrom = refersMgr.getRefersFrom(articleCID, articleid, columnID);
            voArticle.setRefersFrom(refersFrom);

            String refersTo = refersMgr.getRefersTo(articleid, columnID);
            voArticle.setRefersTo(refersTo);

            int useArticleType = refersMgr.getRefersArticleUseType(articleid, columnID);
            voArticle.setUseArticleType(useArticleType);
        } catch (orderArticleException e) {
            e.printStackTrace();
        }

        voArticleList.add(voArticle);
    }

    Tree colTree = null;
    String userid = authToken.getUserID();
    int sitetype = authToken.getSitetype();
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

    sitename = StringUtil.replace(sitename,"_",".");
    String cname = colTree.getChineseDirForArticle(colTree,columnID,sitename);
    Map data = new HashMap();
    data.put("ARTICLES",voArticleList);
    data.put("TOTALCOUNT",totalCount);
    data.put("COLUMNNAME",cname);
    data.put("FLAG",flag);
    data.put("ASCDESC",ascdesc);
    data.put("DOSEARCH",doSearch);
    data.put("TITLE",maintitle);
    data.put("VICETITLE",vicetitle);
    data.put("AUTHOR",author);
    data.put("SOURCE",source);
    data.put("ARTICLEID",articleid);
    data.put("SPUBDATE",spubdate);
    data.put("EPUBDATE",epubdate);
    data.put("ARTTYPE",arttypeval);
    data.put("AUDIT",auditflag);
    data.put("RANGE",range);
    data.put("DEPTREADRIGHT",SecurityCheck.hasPermission(authToken, 63));               //\u7528\u6237\u662f\u5426\u6709\u8bfb\u672c\u90e8\u95e8\u5176\u4ed6\u4eba\u6587\u7ae0\u6743\u9650
    data.put("DEPTWRITERIGHT",SecurityCheck.hasPermission(authToken, 64));              //\u7528\u6237\u662f\u5426\u6709\u5199\u672c\u90e8\u95e8\u5176\u4ed6\u4eba\u6587\u7ae0\u6743\u9650
    data.put("DEPTDELETERIGHT",SecurityCheck.hasPermission(authToken, 65));             //\u7528\u6237\u662f\u5426\u6709\u5220\u672c\u90e8\u95e8\u5176\u4ed6\u4eba\u6587\u7ae0\u6743\u9650
    data.put("DEPTPULISHRIGHT",SecurityCheck.hasPermission(authToken, 66));             //\u7528\u6237\u662f\u5426\u6709\u53d1\u5e03\u672c\u90e8\u95e8\u5176\u4ed6\u4eba\u6587\u7ae0\u6743\u9650
    data.put("WRITERIGHT",SecurityCheck.hasPermission(authToken, 51));                   //\u7528\u6237\u662f\u5426\u6709\u5199\u6743\u9650
    data.put("PUBLISHRIGHT",SecurityCheck.hasPermission(authToken, 6));                 //\u7528\u6237\u662f\u5426\u6709\u53d1\u5e03\u6240\u6709\u6587\u7ae0\u6743\u9650
    data.put("PUBLISHMYRIGHT",SecurityCheck.hasPermission(authToken, 8));               //\u7528\u6237\u662f\u5426\u6709\u53d1\u5e03\u81ea\u5df1\u6587\u7ae0\u6743\u9650
    data.put("ADMINRIGHT",SecurityCheck.hasPermission(authToken, 54));                  //\u7528\u6237\u662f\u5426\u6709\u7ad9\u70b9\u7ba1\u7406\u5458\u6743\u9650
    data.put("MSGNO",msgno);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //FileUtil.writeFile(new StringBuffer(jsondata),"c:\\data\\ttttt.txt");
    //System.out.println(jsondata);
    //System.out.println(articleList.size() + "==" + totalCount);
    JSON.setPrintWriter(response,jsondata,"utf-8");

    out.write(_jsp_string1, 0, _jsp_string1.length);
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("article/articlesnew.jsp"), -1869000789872872885L, false);
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
  private final static char []_jsp_string1;
  static {
    _jsp_string0 = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n".toCharArray();
    _jsp_string1 = "\r\n".toCharArray();
  }
}
