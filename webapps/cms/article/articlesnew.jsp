<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.tree.*,
                 com.google.gson.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.bizwink.po.Article" %>
<%@ page import="com.bizwink.service.ArticleService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.bizwink.vo.VoArticle" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.orderArticleListManager.orderArticleException" %>
<%@ page import="com.bizwink.service.DeptService" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Organization" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
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
    int auditflag = ParamUtil.getIntParameter(request, "audit", 0);                       //查询待审稿件和退回稿件，值为0代表不需要审核的稿件或者审核通过的稿件
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

    //System.out.println("站点管理权限："+SecurityCheck.hasPermission(authToken,54));
    //System.out.println("读文章权限："+SecurityCheck.hasPermission(authToken,50));
    //System.out.println("写文章权限："+SecurityCheck.hasPermission(authToken,51));
    //System.out.println("删文章权限："+SecurityCheck.hasPermission(authToken,52));
    //System.out.println("读部门文章权限："+SecurityCheck.hasPermission(authToken,63));
    //System.out.println("修改部门文章权限："+SecurityCheck.hasPermission(authToken,64));
    //System.out.println("删除部门文章权限："+SecurityCheck.hasPermission(authToken,65));
    //System.out.println("发布部门文章权限："+SecurityCheck.hasPermission(authToken,66));

    if (SecurityCheck.hasPermission(authToken,54) || SecurityCheck.hasPermission(authToken,50)|| SecurityCheck.hasPermission(authToken,51)|| SecurityCheck.hasPermission(authToken,52)) {
        username = null;
    }

    String deptname = null;
    boolean hasDeptRight = false;
    if(SecurityCheck.hasPermission(authToken,63) || SecurityCheck.hasPermission(authToken,64)|| SecurityCheck.hasPermission(authToken,65)|| SecurityCheck.hasPermission(authToken,66)) {
        //获取当前登录用户所属部门
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
                //用户没有设置处理部门文章的权限，用户名为空表示可以处理所有文章，用户名不为空，表示只能处理自己的文章
                articleList = articleService.getArticlesInPageByColumnids(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval), BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), flag, ascdesc);
                totalCount = articleService.getArticlesCountByColumnids(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval));
            } else {
                //用户设置了部门文章处理权限，用户可以处理本栏目下所有本部门编辑录入的文章
                articleList = articleService.getArticlesInPageByDeptAndColumnids(deptname, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval), BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), flag, ascdesc);
                totalCount = articleService.getArticlesCountByDeptAndColumnids(deptname, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(arttypeval));
            }
        } else {
            if (!hasDeptRight) {
                //用户没有设置处理部门文章的权限，用户名为空表示可以查询所有文章，用户名不为空，表示只能查询自己的文章
                articleList = articleService.getSearchArticlesInPage(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),
                        BigDecimal.valueOf((pageno - 1) * range), BigDecimal.valueOf(pageno * range), BigDecimal.valueOf(articleid), maintitle, vicetitle, author, source,
                        spubdate, epubdate, BigDecimal.valueOf(arttypeval), BigDecimal.valueOf(auditflag), BigDecimal.valueOf(flag), BigDecimal.valueOf(ascdesc));
                totalCount = articleService.getSearchArticlesCount(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID), BigDecimal.valueOf(articleid), maintitle,
                        vicetitle, author, source, spubdate, epubdate, BigDecimal.valueOf(arttypeval), BigDecimal.valueOf(auditflag));
            } else {
                //用户设置了部门文章处理权限，用户可以查询本栏目下所有本部门编辑录入的文章
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
    if (sitetype == 0 || sitetype == 2) {                             //0表示自己创建的网站，2表示完整拷贝模板网站
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
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
            //站点管理员
            colTree = TreeManager.getInstance().getSiteTree(siteid);
        }
    } else {                                                           //1表示共享模板网站的栏目和模板
        if (!userid.equalsIgnoreCase("admin") && !SecurityCheck.hasPermission(authToken, 54)) {
            //普通用户
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
            //站点管理员
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
    data.put("DEPTREADRIGHT",SecurityCheck.hasPermission(authToken, 63));               //用户是否有读本部门其他人文章权限
    data.put("DEPTWRITERIGHT",SecurityCheck.hasPermission(authToken, 64));              //用户是否有写本部门其他人文章权限
    data.put("DEPTDELETERIGHT",SecurityCheck.hasPermission(authToken, 65));             //用户是否有删本部门其他人文章权限
    data.put("DEPTPULISHRIGHT",SecurityCheck.hasPermission(authToken, 66));             //用户是否有发布本部门其他人文章权限
    data.put("WRITERIGHT",SecurityCheck.hasPermission(authToken, 51));                   //用户是否有写权限
    data.put("PUBLISHRIGHT",SecurityCheck.hasPermission(authToken, 6));                 //用户是否有发布所有文章权限
    data.put("PUBLISHMYRIGHT",SecurityCheck.hasPermission(authToken, 8));               //用户是否有发布自己文章权限
    data.put("ADMINRIGHT",SecurityCheck.hasPermission(authToken, 54));                  //用户是否有站点管理员权限
    data.put("MSGNO",msgno);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //FileUtil.writeFile(new StringBuffer(jsondata),"c:\\data\\ttttt.txt");
    //System.out.println(jsondata);
    //System.out.println(articleList.size() + "==" + totalCount);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
