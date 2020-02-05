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
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
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
    int status = ParamUtil.getIntParameter(request,"status",-1);

    if (SecurityCheck.hasPermission(authToken, 54) || SecurityCheck.hasPermission(authToken, 50)) {
        username = null;
    }

    List<Article> articleList = new ArrayList<>();
    int totalCount=0;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        ArticleService articleService = (ArticleService) appContext.getBean("articleService");
        if (!doSearch) {
            articleList = articleService.getArticlesInPageByColumnidsForPublish(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),BigDecimal.valueOf(auditflag),BigDecimal.valueOf(status),BigDecimal.valueOf((pageno-1) * range), BigDecimal.valueOf(pageno * range), flag, ascdesc);
            totalCount = articleService.getArticlesCountByColumnidsForPublish(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),BigDecimal.valueOf(auditflag),BigDecimal.valueOf(status));
        } else {
            articleList = articleService.getSearchArticlesInPageForPublish(username, BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),
                    BigDecimal.valueOf((pageno-1) * range), BigDecimal.valueOf(pageno*range),BigDecimal.valueOf(articleid),maintitle,vicetitle,author,source,
                    spubdate,epubdate,BigDecimal.valueOf(status),BigDecimal.valueOf(auditflag),BigDecimal.valueOf(flag), BigDecimal.valueOf(ascdesc));
            totalCount = articleService.getSearchArticlesCountForPublish(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnID),BigDecimal.valueOf(articleid),maintitle,
                    vicetitle,author,source, spubdate,epubdate,BigDecimal.valueOf(status),BigDecimal.valueOf(auditflag));
        }
    }

    IRefersManager refersMgr = RefersPeer.getInstance();
    IColumnManager columnMgr = ColumnPeer.getInstance();
    List<VoArticle> voArticleList = new ArrayList<>();
    for(int ii=0;ii<articleList.size();ii++) {
        Article article = articleList.get(ii);
        Column column = columnMgr.getColumn(article.getCOLUMNID().intValue());
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
        voArticle.setCOLUMNNAME(column.getCName());
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
        voArticle.setPROCESSOFAUDIT(article.getPROCESSOFAUDIT());
        voArticle.setSITEID(article.getSITEID());

        System.out.println("isown:" + article.getIsown());
        if (article.getIsown().intValue() == 1)
            voArticle.setIsown(true);
        else
            voArticle.setIsown(false);
        articleid = article.getID().intValue();

        try {
            int pubflag = 0;
            if(voArticle.isIsown())
                pubflag = article.getPUBFLAG();
            else
                pubflag = refersMgr.getRefersArticlePubFlag(articleid, columnID,siteid);
            voArticle.setPUBFLAG((short)pubflag);

            String refersFrom = refersMgr.getRefersFrom(article.getCOLUMNID().intValue(), articleid, columnID);
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
    data.put("STATUS",status);
    data.put("AUDIT",auditflag);
    data.put("RANGE",range);
    data.put("WRITERIGHT",SecurityCheck.hasPermission(authToken, 51));               //用户是否有写权限
    data.put("PUBLISHRIGHT",SecurityCheck.hasPermission(authToken, 6));              //用户是否有发布权限
    data.put("ADMINRIGHT",SecurityCheck.hasPermission(authToken, 54));               //用户是否有站点管理员权限
    data.put("MSGNO",msgno);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //System.out.println(jsondata);
    //System.out.println(articleList.size() + "==" + totalCount);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>
