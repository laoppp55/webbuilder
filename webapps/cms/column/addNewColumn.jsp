<%@ page import="java.sql.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    session.setAttribute("Current_URL", request.getRequestURI());
    int siteid = authToken.getSiteID();
    String editor = authToken.getUserID();
    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = new Column();
    Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
    int rootID = colTree.getTreeRoot();

    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int parentID = ParamUtil.getIntParameter(request, "parentID", 0);

    boolean errors = false;
    boolean success = false;
    boolean errorCName = false;
    boolean errorEName = false;
    String CName = "";
    String EName = "";
    String extfilename = "";
    int orderid = 0;
    int isAudited = 0;
    int isProduct = 0;
    int isLocation = 0;
    int publicflag = 0;
    int isPublishMore = 0;
    int languageType = 0;
    boolean dualName = false;
    String desc = "";
    int contentshowtype = 0;
    int userflag = 0;
    int userlevel = 0;
    String titlepic = "";
    String vtitlepic = "";
    String sourcepic = "";
    String authorpic = "";
    String contentpic = "";
    String specialpic = "";
    String productpic = "";
    String productsmallpic = "";
    String xmlTemplate = "";
    int isDefineAttr = 0;
    int extattrscope = 0;

    if (doCreate) {
        CName = ParamUtil.getParameter(request, "CName");
        EName = ParamUtil.getParameter(request, "EName");
        extfilename = ParamUtil.getParameter(request, "extfilename");
        orderid = ParamUtil.getIntParameter(request, "orderid", 1);
        desc = ParamUtil.getParameter(request, "desc");
        isAudited = ParamUtil.getIntParameter(request, "isAudited", 0);
        isProduct = ParamUtil.getIntParameter(request, "isProduct", 0);
        isLocation = ParamUtil.getIntParameter(request, "islocation", 0);
        isPublishMore = ParamUtil.getIntParameter(request, "isPublishMore", 0);
        languageType = ParamUtil.getIntParameter(request, "languageType", 0);
        contentshowtype = ParamUtil.getIntParameter(request, "showtype", 0);
        userflag = ParamUtil.getIntParameter(request,"userflag",0);
        publicflag = ParamUtil.getIntParameter(request,"publicflag",0);
        xmlTemplate = ParamUtil.getParameter(request, "xmlTemplate");
        isDefineAttr = ParamUtil.getIntParameter(request, "isDefineAttr", 0);
        extattrscope = ParamUtil.getIntParameter(request, "extattrscope", 0);

        if(ParamUtil.getParameter(request,"titlepicheight")!=null && ParamUtil.getParameter(request,"titlepicwidth")!=null){
            titlepic = ParamUtil.getParameter(request,"titlepicheight") + "X" + ParamUtil.getParameter(request,"titlepicwidth");
        }
        if(ParamUtil.getParameter(request,"vtitlepicheight") != null &&  ParamUtil.getParameter(request,"vtitlepicwidth")!= null){
            vtitlepic = ParamUtil.getParameter(request,"vtitlepicheight") + "X" + ParamUtil.getParameter(request,"vtitlepicwidth");
        }
        if(ParamUtil.getParameter(request,"sourcepicheight") != null && ParamUtil.getParameter(request,"sourcepicwidth") != null){
            sourcepic = ParamUtil.getParameter(request,"sourcepicheight") + "X" + ParamUtil.getParameter(request,"sourcepicwidth");
        }
        if(ParamUtil.getParameter(request,"authorpicheight") != null && ParamUtil.getParameter(request,"authorpicwidth") != null){
            authorpic = ParamUtil.getParameter(request,"authorpicheight") + "X" + ParamUtil.getParameter(request,"authorpicwidth");
        }
        if(ParamUtil.getParameter(request,"contentpicheight")!=null && ParamUtil.getParameter(request,"contentpicwidth")!=null){
            contentpic = ParamUtil.getParameter(request,"contentpicheight") + "X" + ParamUtil.getParameter(request,"contentpicwidth");
        }
        if(ParamUtil.getParameter(request,"specialpicheight") != null && ParamUtil.getParameter(request,"specialpicwidth") != null){
            specialpic = ParamUtil.getParameter(request,"specialpicheight") + "X" + ParamUtil.getParameter(request,"specialpicwidth");
        }
        if(ParamUtil.getParameter(request,"productpicheight") != null && ParamUtil.getParameter(request,"productpicwidth") != null) {
            productpic = ParamUtil.getParameter(request,"productpicheight") + "X" + ParamUtil.getParameter(request,"productpicwidth");
        }
        if(ParamUtil.getParameter(request,"productsmallpicheight") != null && ParamUtil.getParameter(request,"productsmallpicwidth") !=null){
            productsmallpic = ParamUtil.getParameter(request,"productsmallpicheight") + "X" + ParamUtil.getParameter(request,"productsmallpicwidth");
        }

        if(userflag == 1){
            userlevel = ParamUtil.getIntParameter(request,"userlevel",0);
        }

        if (CName == null) {
            errorCName = true;
            errors = true;
        }
        if (EName == null) {
            errorEName = true;
            errors = true;
        }
        if (desc != null)
            desc = desc.trim();
        else
            desc = "";
    }

    if (!errors && doCreate) {
        dualName = columnManager.duplicateEnName(parentID, EName);
        if (!dualName) {
            try {
                column.setSiteID(siteid);
                column.setParentID(parentID);
                column.setCName(StringUtil.gb2isoindb(CName));
                column.setEName(EName);
                column.setExtname(extfilename);
                column.setCreateDate(new Timestamp(System.currentTimeMillis()));
                column.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                column.setOrderID(orderid);
                column.setEditor(editor);
                column.setDesc(StringUtil.gb2isoindb(desc));
                column.setDefineAttr(isDefineAttr);
                column.setXMLTemplate(xmlTemplate);
                column.setExtattrscope(extattrscope);
                column.setIsAudited(isAudited);
                column.setIsProduct(isProduct);
                column.setIsPosition(isLocation);
                column.setIsPublishMoreArticleModel(isPublishMore);
                column.setLanguageType(languageType);
                column.setContentShowType(contentshowtype);
                column.setUserflag(userflag);
                column.setPublicflag(publicflag);
                column.setUserlevel(userlevel);
                column.setTitlepic(titlepic);
                column.setVtitlepic(vtitlepic);
                column.setSourcepic(sourcepic);
                column.setAuthorpic(authorpic);
                column.setContentpic(contentpic);
                column.setSpecialpic(specialpic);
                column.setProductpic(productpic);
                column.setProductsmallpic(productsmallpic);

                columnManager.create(column);
                success = true;
            }  catch (ColumnException uaee) {
                uaee.printStackTrace();
                errors = true;
            }
        }
    }
%>
