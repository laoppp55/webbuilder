<%@ page import="com.bizwink.cms.util.ParamUtil" %>

<%@ page import="com.bizwink.cms.modelManager.IModelManager" %>
<%@ page import="com.bizwink.cms.modelManager.ModelPeer" %>
<%@ page import="com.bizwink.cms.modelManager.Model" %>
<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 org.jdom.*,
                 org.jdom.input.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK" %>
<%@ page import="com.usermodelnews.IUserModelNewsManager" %>
<%@ page import="com.usermodelnews.UserModelNewsPeer" %>
<%@ page import="com.bizwink.cms.audit.AuditPeer" %>
<%@ page import="com.bizwink.cms.audit.IAuditManager" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int columnid = ParamUtil.getIntParameter(request, "columnid", -1);
    int flag = ParamUtil.getIntParameter(request, "flag", -1);
    int columnID = columnid;
    IColumnManager columnManager = ColumnPeer.getInstance();
    IModelManager modelpeer = ModelPeer.getInstance();
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    IUserModelNewsManager umnpeer = UserModelNewsPeer.getInstance();
    IArticleManager articleManager = ArticlePeer.getInstance();
    IAuditManager auditMgr = AuditPeer.getInstance();
    IRefersManager referManager = RefersPeer.getInstance();
    List relArticle = new ArrayList();
    List ptypelist = new ArrayList();
    boolean errorTitle = false;
    boolean errorContent = false;
    boolean errors = false;
    boolean success = false;
    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    //先查找摸版 如果没有 找父蓝木摸版
    int articleid=ParamUtil.getIntParameter(request,"articleid",-1);
    Model model = null;

    Article getarticle = articleManager.getArticle(articleid, username);
    int parentcolumnid = -1;

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String from = ParamUtil.getParameter(request, "fromflag");


    //获得栏目信息
    //  IColumnManager columnManager = ColumnPeer.getInstance();
    //扩展属性

    Column column = columnManager.getColumn(columnID);
    // System.out.println("column="+column+"     columnID="+columnID);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isAudited = column.getIsAudited();
    //List attechmentArticle = new ArrayList();
    List pubcolumns = new ArrayList();
    String maintitle = null, vicetitle = null, contents = null, summary = null, keyword = null, source = null, year = null,
            month = null, articlepic = null, day = null, hour = null, minute = null, author = null, brand = null,
            pic = null, bigpic = null, relatedID = null, filename = null, otherurl = null,modelcontent=null;
    int docLevel = 0, viceLevel = 0, sortid = 0, status = 0, subscriber = 0, weight = 0, stocknum = 0, modelID = 0, urltype = 0, score = 0, voucher = 0;
    float saleprice = 0, inprice = 0, marketprice = 0, istype = 0, vipprice = 0;

    try {
        do {
            model = modelpeer.getArticleModel(columnid);
            Column columns = columnManager.getParentColumn(columnid);
            columnid = columns.getID();
            //System.out.println("columnid="+columnid+"   model="+model);
        } while (model == null);
        modelcontent = model.getContent();
        //----------------------------------------------------

        String  content="";
        if (doCreate) {
            // System.out.println("vvvvvvvvvvvvvvvvvvv");
            maintitle = ParamUtil.getParameter(request, "maintitle");
            maintitle = (maintitle != null) ? maintitle.trim() : maintitle;
            vicetitle = ParamUtil.getParameter(request, "vicetitle");
            content = ParamUtil.getParameter(request, "content");
            author = ParamUtil.getParameter(request, "author");
            summary = ParamUtil.getParameter(request, "summary");
            keyword = ParamUtil.getParameter(request, "keyword");
            source = ParamUtil.getParameter(request, "source");
            relatedID = ParamUtil.getParameter(request, "relatedID");
            docLevel = ParamUtil.getIntParameter(request, "docLevel", 0);
            viceLevel = ParamUtil.getIntParameter(request, "viceDocLevel", 0);
            status = ParamUtil.getIntParameter(request, "status", 1);
            subscriber = ParamUtil.getIntParameter(request, "subscriber", 1);
            sortid = ParamUtil.getIntParameter(request, "sortid", 0);
            year = ParamUtil.getParameter(request, "year");
            month = ParamUtil.getParameter(request, "month");
            day = ParamUtil.getParameter(request, "day");
            hour = ParamUtil.getParameter(request, "hour");
            minute = ParamUtil.getParameter(request, "minute");
            filename = ParamUtil.getParameter(request, "filename");
            modelID = ParamUtil.getIntParameter(request, "modelID", 0);
            //attechmentArticle = ParamUtil.getSelectParameterAllNamesAndValues(request, "attechment");
            pubcolumns = ParamUtil.getSelectParameterAllNamesAndValues(request, "pubcolumns");
            articlepic = ParamUtil.getParameter(request, "articlepic");
            urltype = ParamUtil.getIntParameter(request, "url", 0);
            otherurl = ParamUtil.getParameter(request, "aurl");
            relArticle = (List) session.getAttribute("rlist");
            //beidate=ParamUtil.getParameter(request,"beidate");
            if (isProduct == 1) {
                brand = ParamUtil.getParameter(request, "brand");
                pic = ParamUtil.getParameter(request, "pic");
                bigpic = ParamUtil.getParameter(request, "bigpic");
                weight = ParamUtil.getIntParameter(request, "weight", 0);
                stocknum = ParamUtil.getIntParameter(request, "stocknum", 0);
                saleprice = ParamUtil.getFloatParameter(request, "saleprice", 0);
                inprice = ParamUtil.getFloatParameter(request, "inprice", 0);
                marketprice = ParamUtil.getFloatParameter(request, "marketprice", 0);
                vipprice = ParamUtil.getFloatParameter(request, "vipprice", 0);
                score = ParamUtil.getIntParameter(request, "score", 0);
                voucher = ParamUtil.getIntParameter(request, "voucher", 0);
            }
            //文章分类
            ptypelist = (List)session.getAttribute("articletypeids");


            //-------------------------------组合content字段----------------------------------------------
            List listnewscontent= umnpeer.getImgAllContent(modelcontent);
            content="";
            for(int i=0;i<listnewscontent.size();i++)
            {
                String newcontent=(String)listnewscontent.get(i);
                if(modelcontent.indexOf(newcontent)!=-1)
                {
                    String str=newcontent.substring(newcontent.indexOf("[CHINESENAME]")+"[CHINESENAME]".length(),newcontent.indexOf("[/CHINESENAME]"));
                    String width=newcontent.substring(newcontent.indexOf("[WIDTH]")+"[WIDTH]".length(),newcontent.indexOf("[/WIDTH]"));
                    String height=newcontent.substring(newcontent.indexOf("[HEIGHT]")+"[HEIGHT]".length(),newcontent.indexOf("[/HEIGHT]"));

                    String modelcontentstr="";
                    String orderstr=newcontent.substring(newcontent.indexOf("[ORDER]")+"[ORDER]".length(),newcontent.indexOf("[/ORDER]"));
                    modelcontentstr=modelcontentstr+ParamUtil.getParameter(request,orderstr);

                    content=content+"<!--begin>[TAGA][IMGZUHECONTENT][CHINESENAME]"+str+"[/CHINESENAME][WIDTH]"+width+"[/WIDTH][HEIGHT]"+height+"[/HEIGHT][ORDER]"+orderstr+"[/ORDER][SRC]"+modelcontentstr+"[/SRC][ORDER]"+orderstr+"[/ORDER][/IMGZUHECONTENT][/TAGA]<end-->";

                    //System.out.println("<TAG><IMGZUHECONTENT><CHINESENAME>"+str+"</CHINESENAME><WIDTH>"+width+"</WIDTH><HEIGHT>"+height+"</HEIGHT><ORDER>"+orderstr+"</ORDER><SRC>"+modelcontentstr+"</SRC></IMGZUHECONTENT></TAG>");
                }
            }


            List listtextnewscontent= umnpeer.getTextAllContent(modelcontent);
            for(int i=0;i<listtextnewscontent.size();i++)
            {
                String newcontent=(String)listtextnewscontent.get(i);
                if(modelcontent.indexOf(newcontent)!=-1)
                {
                    // System.out.println(""+modelcontent);
                    String str=newcontent.substring(newcontent.indexOf("[CHINESENAME]")+"[CHINESENAME]".length(),newcontent.indexOf("[/CHINESENAME]"));
                    // String width=newcontent.substring(newcontent.indexOf("[WIDTH]")+"[WIDTH]".length(),newcontent.indexOf("[/WIDTH]"));
                    // String height=newcontent.substring(newcontent.indexOf("[HEIGHT]")+"[HEIGHT]".length(),newcontent.indexOf("[/HEIGHT]"));

                    //  System.out.println("str="+str+  newcontent+"    0000");
                    String orderstr=newcontent.substring(newcontent.indexOf("[ORDER]")+"[ORDER]".length(),newcontent.indexOf("[/ORDER]"));
                    String modelcontentstr="";

                    modelcontentstr=modelcontentstr+ParamUtil.getParameter(request,orderstr);

                    content=content+"<!--begin>[TAGA][TEXTZUHECONTENT][CHINESENAME]"+str+"[/CHINESENAME][ORDER]"+orderstr+"[/ORDER][AREA]"+modelcontentstr+"[/AREA][ORDER]"+orderstr+"[/ORDER][/TEXTZUHECONTENT][/TAGA]<end-->";

                }
            }

            //---------------------------------------------------------------------------------------------


            if (content != null) {
                content = StringUtil.replace(content, "cmstextarea", "textarea").trim();

            }else{
                content="";
            }
            if (maintitle == null) errorTitle = true;
            if (content == null) errorContent = true;
            errors = errorTitle || errorContent;
        }


        Article article = new Article();
        //   System.out.println("aaaaaaaaaaaaaaaaa"+errors);
        if (!errors && doCreate) {

            article.setID(articleid);
            article.setSiteID(siteid);
            article.setColumnID(columnID);
            article.setMainTitle(maintitle);
            article.setViceTitle(vicetitle);
            article.setContent(content);
            article.setAuthor(author);
            article.setSummary(summary);
            article.setKeyword(keyword);
            article.setSortID(sortid);
            article.setRelatedArtID(relatedID);
            article.setPubFlag(1);
            article.setSource(source);
            article.setEditor(username);
            Timestamp create_date = new Timestamp(System.currentTimeMillis());
            article.setCreateDate(create_date);
            article.setLastUpdated(create_date);
            article.setDocLevel(docLevel);
            article.setViceDocLevel(viceLevel);
            article.setAuditFlag(isAudited);
            article.setStatus(status);
            article.setModelID(modelID);
            article.setSubscriber(subscriber);
            article.setUrltype(urltype);
            article.setOtherurl(otherurl);
            article.setFileName(filename);
            if (filename != null)
                article.setNullContent(1);
            else
                article.setNullContent(0);
            article.setArticlepic(articlepic);
            article.setT1(0);
            article.setT2(0);
            article.setT3(0);
            article.setT4(0);
            article.setT5(0);
            if (isProduct == 1) {
                article.setSalePrice(saleprice);
                article.setInPrice(inprice);
                article.setMarketPrice(marketprice);
                article.setBrand(brand);
                article.setProductPic(pic);
                article.setProductBigPic(bigpic);
                article.setProductWeight(weight);
                article.setStockNum(stocknum);
                article.setVIPPrice(vipprice);
                article.setScore(score);
                article.setVoucher(voucher);
            }

            if (year == null)
                article.setPublishTime(new Timestamp(System.currentTimeMillis()));
            else {
                Timestamp publishtime = new Timestamp(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute), 0, 0);
                article.setPublishTime(publishtime);
            }

            List attrList = new ArrayList();
            if (isDefine == 1) {
                String xmlTemplate = extendMgr.getXMLTemplate(columnid);
                if (xmlTemplate != null && xmlTemplate.trim().length() > 0) {
                    SAXBuilder builder = new SAXBuilder();
                    Reader in = new StringReader(xmlTemplate);
                    Document doc = builder.build(in);
                    List nodeList = doc.getRootElement().getChildren();
                    for (int i = 0; i < nodeList.size(); i++) {
                        Element e = (Element) nodeList.get(i);
                        String ename = e.getName();
                        String value = ParamUtil.getParameter(request, ename);
                        if (value != null) value = value.replaceAll("&apos;", "\'");

                        if (value != null && value.trim().length() > 0) {
                            ExtendAttr extend = new ExtendAttr();
                            int dataType = Integer.parseInt(e.getAttributeValue("datatype"));

                            extend.setEName(ename);
                            extend.setDataType(dataType);
                            if (dataType == 1)
                                extend.setStringValue(value);
                            else if (dataType == 2)
                                extend.setNumericValue(Integer.parseInt(value));
                            else if (dataType == 3)
                                extend.setTextValue(value);
                            else if (dataType == 4)
                                extend.setFloatValue(Float.parseFloat(value));
                            attrList.add(extend);
                        }
                    }
                }
            }
            //文章分类
            //List ptypelist = (List)session.getAttribute("articletypeids");

            //文章轮换图片
            List turnpic = (List) session.getAttribute("turn_pic");
            extendMgr.update(attrList, null, relArticle, article,ptypelist, turnpic);
            //如果该篇文章为退稿文章，则将以前的审核信息更新为过期信息
            if (article.getAuditFlag() == 2) {
                auditMgr.updateAudit_Info("", articleid, "", 0);
            }
            success = true;
        }
        //attechmentArticle   附件列表
        //pubcolumns          文章需要发布的栏目列表
        if (success) {
            session.removeAttribute("articletypeids");
            session.removeAttribute("turn_pic");
            response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id=" + articleid + "&column=" + columnID + "&start=" + start + "&range=" + range + "&fromflag=" + from));
            return;
        }
        //System.out.println("bbbbbbbbbbbbbbbbbbbb"+doUpdate);
        if (!doUpdate) {

            maintitle = StringUtil.gb2iso4View(getarticle.getMainTitle());
            vicetitle = StringUtil.gb2iso4View(getarticle.getViceTitle());
            content = StringUtil.gb2iso4View(getarticle.getContent());
            content = StringUtil.replace(content, "textarea", "cmstextarea").trim();
            author = StringUtil.gb2iso4View(getarticle.getAuthor());
            summary = StringUtil.gb2iso4View(getarticle.getSummary());
            keyword = StringUtil.gb2iso4View(getarticle.getKeyword());
            filename = getarticle.getFileName();
            docLevel = getarticle.getDocLevel();
            viceLevel = getarticle.getViceDocLevel();
            status = getarticle.getStatus();
            relatedID = getarticle.getRelatedArtID();
            subscriber = getarticle.getSubscriber();
            source = StringUtil.gb2iso4View(getarticle.getSource());
            sortid = getarticle.getSortID();
            String tempbuf = getarticle.getPublishTime().toString();
            year = tempbuf.substring(0, 4);
            month = tempbuf.substring(5, 7);
            day = tempbuf.substring(8, 10);
            tempbuf = tempbuf.substring(tempbuf.indexOf(" ") + 1);
            // hour = Integer.parseInt(tempbuf.substring(0, 2));
            // minute = Integer.parseInt(tempbuf.substring(3, 5));
            modelID = getarticle.getModelID();
            //attechmentArticle = articleManager.getOneArticleRelatedArticles(articleid,0);
            articlepic = getarticle.getArticlepic();
            relArticle = referManager.getRefersArticleByColumn(articleid,columnID,siteid);
            urltype = getarticle.getUrltype();
            otherurl = getarticle.getOtherurl();

            //考虑双引号问题
            maintitle = StringUtil.replace(maintitle, "\"", "&quot;");
            if (vicetitle != null) vicetitle = StringUtil.replace(vicetitle, "\"", "&quot;");
            if (author != null) author = StringUtil.replace(author, "\"", "&quot;");
            if (summary != null) summary = StringUtil.replace(summary, "\"", "&quot;");
            if (keyword != null) keyword = StringUtil.replace(keyword, "\"", "&quot;");
            if (source != null) source = StringUtil.replace(source, "\"", "&quot;");
            if (filename != null)
                filename = StringUtil.gb2iso4View(filename);
            else
                filename = "";
        }
        /* } catch (Exception e) {
            out.write("no  article");
        }*/

        // System.out.println("mode="+model+"   chinese="+model+"   columnid="+columnid);
        String[] result = null;
        if (getarticle.getAuditFlag() == 2) {
            result = auditMgr.getArticleInfo(articleid, username, 1);
        }
%>
<html>
<head>
<script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
<script LANGUAGE="JavaScript" SRC="../toolbars/dhtmled.js"></script>

<script language=javascript>
function Editor(ename)
{
    window.open("editor.jsp?column=<%=columnID%>&ename=" + ename, "Editor", "width=870,height=600,left=0,top=0,status");
}

function upload_attrpic_onclick(attrname)
{
    window.open("../upload/upload_zhuanqu.jsp?column=<%=columnID%>&attr=" + attrname, "", 'width=600,height=450,left=200,top=200');
}

//双击选择文章分类
function addarticletype()
{
    window.open("selectarticletype.jsp?column=<%=columnID%>&articleid=<%=articleID%>", "", "top=100,left=100,width=420,height=360");
}

//增加相关文章
function AddRelatedArticleID(para)
{
    var winStr = "addRelatedArticle.jsp?from=1&param=" + para;
    //from=1 文章录入时调用相关文章
    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
    if (isMSIE) {
        var retval = showModalDialog(winStr, "AddRelatedArticleID", "font-family:Verdana; font-size:12; dialogWidth:55em; dialogHeight:35em; status=no");
        if (retval != "" && retval != undefined)
        {
            retval = retval.substring(0, retval.indexOf(","));
            if (para == 0)
                document.createForm.docLevel.value = retval;

            if (para == 1)
                document.createForm.viceDocLevel.value = retval;

            if (para == 2) {
                document.createForm.relatedID.value = retval;
                document.createForm.parentArticleTitleID.value = retval;
            }
        }
    } else {
        var wins = window.open(winStr, "", "width=700,height=500");
        wins.focus();
    }
}

//文章推荐
<%--function ArticleAttechment()
{
    var winStr = "addRelateListnew.jsp?articleid=<%=articleID%>";
    window.open(winStr, "", "width=800,height=600,left=200,top=200,scrollbars=yes");
}--%>

//增加相关文章
function AddArticleAttechment()
{
    var winStr = "addArticleAttechment.jsp";
    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
    if (isMSIE) {
        var retval = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:800px; dialogHeight:600px; status=no");
        if (retval != "" && retval != undefined)
        {
            var regexp = /\r\n/g;
            var rtnAry = retval.split(regexp);

            if (rtnAry != null) {
                var len = rtnAry.length;
                var i = 0;

                while (i < len) {
                    posi = rtnAry[i].indexOf("-");
                    value = rtnAry[i].substring(0, posi);
                    text = rtnAry[i].substring(posi + 1);
                    var oOption = document.createElement("OPTION");
                    oOption.value = rtnAry[i];
                    oOption.text = text;
                    document.createForm.attechmentID.add(oOption);
                    i++;
                }
            }
        }
    } else {
        var wins = window.open(winStr, "", "width=800,height=600");
        wins.focus();
    }
}

//向其他栏目发布文章
function AddPubColumns()
{
    var winStr = "addPubColumns.jsp";
    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
    if (isMSIE) {
        var retval = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:800px; dialogHeight:600px; status=no");
        if (retval != "" && retval != undefined)
        {
            var regexp = /\r\n/g;
            var rtnAry = retval.split(regexp);

            if (rtnAry != null) {
                var len = rtnAry.length;
                var i = 0;

                while (i < len) {
                    posi = rtnAry[i].indexOf("-");
                    value = rtnAry[i].substring(0, posi);
                    text = rtnAry[i].substring(posi + 1);
                    var oOption = document.createElement("OPTION");
                    oOption.value = rtnAry[i];
                    oOption.text = text;
                    document.createForm.pcolumnsID.add(oOption);
                    i++;
                }
            }
        }
    } else {
        var wins = window.open(winStr, "", "width=800,height=600");
        wins.focus();
    }
}

//选择文章模板
function selectArticleModel()
{
    var winStr = "selectModel.jsp?column=<%=columnID%>";
    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
    if (isMSIE) {
        var retval = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:800px; dialogHeight:600px; status=no");
        if (retval != "" && retval != undefined)
        {
            document.createForm.modelID.value = retval;
        }
    } else {
        var wins = window.open(winStr, "", "width=800,height=600");
        wins.focus();
    }
}

function update(createForm)
{
    if (createForm.maintitle.value == "")
    {
        alert("主标题不能为空！");
        createForm.maintitle.focus();
        return;
    }
<%if (isProduct == 1){if (modelcontent.indexOf("[TAG][PRODUCT_SALEPRICE]商品售价[/PRODUCT_SALEPRICE][/TAG]") != -1) {%>
    if (createForm.saleprice.value == "")
    {
        alert("商品售价不能为空！");
        createForm.saleprice.focus();
        return;
    }
    if (!checkFloat(createForm.saleprice.value))
    {
        alert("商品售价应为小数型数据！");
        createForm.saleprice.focus();
        return;
    }

<%}}%>
<%if (isDefine == 1){%>
    // if (!checkExtendAttr(createForm))
    //     return;
<%}%>

    /* for (var i = 0; i < createForm.attechmentID.length; i++) {
     createForm.attechmentID[i].selected = true;
     }*/

    /* for (var i = 0; i < createForm.pcolumnsID.length; i++) {
     createForm.pcolumnsID[i].selected = true;
     } */

    createForm.submit();
}

function checkFloat(str)
{
    var retstr = false;
    var regstr = /[^0-9.]/gi;
    if (regstr.exec(str) == null)
    {
        retstr = true;
    }
    return retstr;
}

function checkInt(str)
{
    var retstr = false;
    var regstr = /[^0-9]/gi;
    if (regstr.exec(str) == null)
    {
        retstr = true;
    }
    return retstr;
}

function fileUpload()
{
    window.open('fileUpload.jsp?column=<%=columnID%>&filename=<%=filename%>', '', 'left=300,top=200,width=400,height=200');
}

//双击选择历史图片
function history(attrname)
{
    window.open("../upload/historypic.jsp?column=<%=columnID%>&pictype=1&attr=" + attrname, "", "left=100,top=100,width=800,height=600");
}

function upload_turn_pic(){
    window.open("../upload/upload_turn_pic.jsp?column=<%=columnID%>", "", 'width=400,height=400,left=200,top=200');
}

function delItem()
{
    var select = false;
    for (var i = 0; i < createForm.attechment.length; i++)
    {
        if (createForm.attechment[i].selected)
        {
            select = true;
            break;
        }
    }

    if (select)
    {
        for (var i = 0; i < createForm.attechment.length; i++)
            if (createForm.attechment[i].selected)
                createForm.attechment[i] = null;
    }
    else
    {
        alert("请选择相关文章！");
    }
}

function useMyUrl(f)
{
    if (f == 1)
        createForm.aurl.disabled = 0;
    else
        createForm.aurl.disabled = 1;
}
</script>


</head>
<body>

<%

        out.write("<form action=editarticle.jsp?columnid="+columnID+" method=post name=createForm><input type=hidden value=1 name=flag><input type=hidden name=doCreate value=\"true\">\n" +
                "<input type=hidden name=columnid value="+columnID+">\n" +
                "<input type=hidden name=filename>\n" +
                "<input type=hidden name=modelSourceCodeFlag value=0>\n" +
                "<input type=hidden name=template_or_article_flag value=\"1\">\n" +
                "<input type=hidden name=markid id=\"markid\" value=\"-1\"><input type=hidden name=modelSourceCodeFlag value=0><input type=\"hidden\" name=\"lockstatus\" value=\"0\"><input type=\"hidden\" name=\"filename\" value=\""+filename+"\"><input type=\"hidden\" name=\"fromflag\" value=\""+from+"\"><input type=\"hidden\" name=\"start\" value=\""+start+"\"><input type=\"hidden\" name=\"range\" value=\""+range+"\"><input type=\"hidden\" name=\"articleid\" value=\""+articleid+"\"> <input type=\"hidden\" name=\"doUpdate\" value=\"true\">");

        //if (modelcontent.indexOf("[TAG][ARTICLE_MAINTITLE][/ARTICLE_MAINTITLE][/TAG]") != -1) {
        out.write("标题(<a href=\"javascript:upload_attrpic_onclick('mt');\">图</a>)<input class=tine name=maintitle value=\""+getarticle.getMainTitle()+"\" id=\"maintitle\"\n" +
                "                                                                        size=36 onDblClick=\"history('mt');\"\n" +
                "        title=\"双击鼠标左键即可选择历史图片\"><br>");
        // }
        if (modelcontent.indexOf("[TAG][ARTICLE_VICETITLE][/ARTICLE_VICETITLE][/TAG]") != -1) {
            out.write("副标题 副标题(<a href=\"javascript:upload_attrpic_onclick('vt')\">图</a>)<input class=tine name=vicetitle\n" +
                    "                                                                         id=\"vicetitle\" value=\""+getarticle.getViceTitle()+"\" size=30\n" +
                    "                                                                         onDblClick=\"history('vt');\"\n" +
                    "        title=\"双击鼠标左键即可选择历史图片\"><br>");
        }
        if (modelcontent.indexOf("[TAG][ARTICLE_CONTENT][/ARTICLE_CONTENT][/TAG]") != -1) {
            //    out.write("文章内容<input type=text><br>");
        }
        if (modelcontent.indexOf("[TAG][ARTICLE_AUTHOR][/ARTICLE_AUTHOR][/TAG]") != -1) {
            out.write("文章作者(<a href=\"javascript:upload_attrpic_onclick('au')\">图</a>)<input class=tine name=author value=\""+getarticle.getAuthor()+"\"  id=\"author\"\n" +
                    "                                                                        size=10 onDblClick=\"history('au');\"\n" +
                    "        title=\"双击鼠标左键即可选择历史图片\"><br>");
        }
        if (modelcontent.indexOf("[TAG][ARTICLE_SOURCE][/ARTICLE_SOURCE][/TAG]") != -1) {
            out.write("文章来源(<a href=\"javascript:upload_attrpic_onclick('sr')\">图</a>)<input class=tine value=\""+getarticle.getSource()+"\" name=source id=\"source\"\n" +
                    "                                                                        size=10 onDblClick=\"history('sr');\"\n" +
                    "        title=\"双击鼠标左键即可选择历史图片\"><br>");
        }
        if (modelcontent.indexOf("[TAG][ARTICLE_KEYWORD][/ARTICLE_KEYWORD][/TAG]") != -1) {
            out.write("文章关键字<input class=tine name=keyword size=30 value=\""+getarticle.getKeyword()+"\"><br>");
        }
        //if (modelcontent.indexOf("[TAG][ARTICLEPIC]文章图片[/ARTICLEPIC][/TAG]") != -1) {
        out.write("<font color=\"#FF0000\"><b>文章图片</b></font>(<a href=\"javascript:upload_attrpic_onclick('apic')\">图</a>)<input\n" +
                "            class=tine name=\"articlepic\" id=\"articlepic\" value=\""+getarticle.getArticlepic()+"\" size=20 onDblClick=\"history('apic');\"\n" +
                "        title=\"双击鼠标左键即可选择历史图片\">&nbsp;&nbsp;<br>");
        //  }
        if (modelcontent.indexOf("[TAG][ARTICLE_SUMMARY][/ARTICLE_SUMMARY][/TAG]") != -1) {
            out.write("文章概述<textarea cols=\"60\" rows=\"2\" id=\"summaryID\"   name=\"summary\">"+getarticle.getSummary()+"</textarea><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_BIGPIC]商品大图片[/PRODUCT_BIGPIC][/TAG]") != -1) {
            out.write("大图片(<a href=\"javascript:upload_attrpic_onclick('bigpic')\">图</a>)：<input class=tine value=\""+getarticle.getProductBigPic()+"\" name=bigpic id=\"bigpic\"\n" +
                    "                                                                              size=12 onDblClick=\"history('bigpic');\"\n" +
                    "        title=\"双击鼠标左键即可选择历史图片\"><br>");
        }

        if (modelcontent.indexOf("[TAG][PRODUCT_PIC]商品小图片[/PRODUCT_PIC][/TAG]") != -1) {
            out.write("商品小图片(<a href=\"javascript:upload_attrpic_onclick('pic')\">图</a>)：\n" +
                    "        <input class=tine name=pic id=\"pic\" value=\""+getarticle.getProductPic()+"\" size=12\n" +
                    "                                                                          onDblClick=\"history('pic');\"\n" +
                    "        title=\"双击鼠标左键即可选择历史图片\"><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_BRAND]商品生产商[/PRODUCT_BRAND][/TAG]") != -1) {
            out.write("商品生产商<input class=tine name=brand value=\""+getarticle.getBrand()+"\" size=12><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_WEIGHT]商品重量[/PRODUCT_WEIGHT][/TAG]") != -1) {
            out.write("商品重量<input class=tine name=weight value=\""+getarticle.getProductWeight()+"\" size=5><br>");
        }

        if (modelcontent.indexOf("[TAG][PRODUCT_STOCK]商品库存[/PRODUCT_STOCK][/TAG]") != -1) {
            out.write("商品库存<input class=tine name=inprice size=6 value=\""+getarticle.getStockNum()+"\"><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_MARKETPRICE]商品市场价[/PRODUCT_MARKETPRICE][/TAG]") != -1) {
            out.write("商品市场价<input class=tine name=marketprice value=\""+getarticle.getMarketPrice()+"\" size=6><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_INPRICE]商品进价[/PRODUCT_INPRICE][/TAG]") != -1) {
            out.write("商品进价<input class=tine name=inprice value=\""+getarticle.getInPrice()+"\" size=6><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_VIPPRICE]VIP售价[/PRODUCT_VIPPRICE][/TAG]") != -1) {
            out.write("vip售价<input class=tine name=vipprice  value=\""+getarticle.getVIPPrice()+"\" size=6><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_SALEPRICE]商品售价[/PRODUCT_SALEPRICE][/TAG]") != -1) {
            out.write("商品售价<input class=tine name=saleprice size=6 value=\""+getarticle.getSalePrice()+"\"><br>");
        }
        if (modelcontent.indexOf("[TAG][PRODUCT_SCORE]积分[/PRODUCT_SCORE][/TAG]") != -1) {
            out.write("积分<input class=tine name=score size=6  value=\""+getarticle.getScore()+"\"><br>");
        }

        if (modelcontent.indexOf("[TAG][PRODUCT_VOUCHER]消费卷[/PRODUCT_VOUCHER][/TAG]") != -1) {
            out.write("消费卷<input class=tine name=voucher size=6 value=\""+getarticle.getVoucher()+"\"><br>");
        }


        List list = umnpeer.getColumntemplatexml(column.getXMLTemplate());
        //  System.out.println("xml=" + column.getXMLTemplate()+"   list="+list.size());
        List textList = new ArrayList();
        List areaList = new ArrayList();
        for(int i=0;i<list.size();i++)
        {
            ExtendAttr ext=(ExtendAttr)list.get(i);
            String ename=ext.getEName();
            String cname=ext.getCName();
            if(modelcontent.indexOf("[TAGA]["+ename+"]"+cname+"[/"+ename+"][/TAGA]")!=-1)
            {
                out.write(extendMgr.getArticleTemplateExtendAttrForArticle(columnID,0,ext));
            }
            //System.out.println("ename="+ext.getEName());
        }
        List listnewscontent= umnpeer.getImgAllContent(modelcontent);
        String articlecontent=getarticle.getContent();
        for(int i=0;i<listnewscontent.size();i++)
        {
            String newcontent=(String)listnewscontent.get(i);
            if(modelcontent.indexOf(newcontent)!=-1)
            {
                // System.out.println(""+modelcontent);
                String str=newcontent.substring(newcontent.indexOf("[CHINESENAME]")+"[CHINESENAME]".length(),newcontent.indexOf("[/CHINESENAME]"));
                String width=newcontent.substring(newcontent.indexOf("[WIDTH]")+"[WIDTH]".length(),newcontent.indexOf("[/WIDTH]"));
                String height=newcontent.substring(newcontent.indexOf("[HEIGHT]")+"[HEIGHT]".length(),newcontent.indexOf("[/HEIGHT]"));
                // String articlevaluestr=articlecontent.substring(articlecontent.indexOf("[SRC]")+"[SRC]".length(),articlecontent.indexOf("[/SRC]"));
                // System.out.println("str="+str+  newcontent+"    0000");
                String orderstr=newcontent.substring(newcontent.indexOf("[ORDER]")+"[ORDER]".length(),newcontent.indexOf("[/ORDER]"));
                String temp="[ORDER]"+orderstr+"[/ORDER]";
                //System.out.println("temp="+temp);
                String articlevaluestr=articlecontent.substring(articlecontent.indexOf(temp+"[SRC]")+(temp+"[SRC]").length(),articlecontent.lastIndexOf("[/SRC]"+temp));
                out.write(str+":(<a href=\"javascript:upload_attrpic_onclick('"+orderstr+"')\">图</a>)<input\n" +
                        "            class=tine name=\""+orderstr+"\" id=\""+orderstr+"\" value=\""+articlevaluestr+"\" size=20 onDblClick=\"history('apic');\"\n" +
                        "        title=\"双击鼠标左键即可选择历史图片\">&nbsp;&nbsp;<br>");
            }
        }
        //匹配文字
        List listtextnewscontent= umnpeer.getTextAllContent(modelcontent);
        for(int i=0;i<listtextnewscontent.size();i++)
        {
            String newcontent=(String)listtextnewscontent.get(i);
            if(modelcontent.indexOf(newcontent)!=-1)
            {
                // System.out.println(""+modelcontent);
                String str=newcontent.substring(newcontent.indexOf("[CHINESENAME]")+"[CHINESENAME]".length(),newcontent.indexOf("[/CHINESENAME]"));
                // String width=newcontent.substring(newcontent.indexOf("[WIDTH]")+"[WIDTH]".length(),newcontent.indexOf("[/WIDTH]"));
                // String height=newcontent.substring(newcontent.indexOf("[HEIGHT]")+"[HEIGHT]".length(),newcontent.indexOf("[/HEIGHT]"));
                // System.out.println("newcontent"+newcontent);

                // System.out.println("str="+articlevaluestr+"    0000");
                String orderstr=newcontent.substring(newcontent.indexOf("[ORDER]")+"[ORDER]".length(),newcontent.indexOf("[/ORDER]"));
                String temp="[ORDER]"+orderstr+"[/ORDER]";
                //System.out.println("temp="+temp);
                String articlevaluestr=articlecontent.substring(articlecontent.indexOf(temp+"[AREA]")+(temp+"[AREA]").length(),articlecontent.lastIndexOf("[/AREA]"+temp));
                out.write(str+":<textarea\n" +
                        "            class=tine name=\""+orderstr+"\" id=\""+orderstr+"\" size=20  onDblClick=\"history('apic');\"\n" +
                        "        >"+articlevaluestr+"</textarea>&nbsp;&nbsp;<br>");
            }
        }



        out.write("<br><input class=tine type=button value=\"  保存  \" onclick=\"update(createForm);\">&nbsp;&nbsp;\n" +
                "            <input class=tine type=button value=\"  取消  \" onclick=\"cal();\">&nbsp;&nbsp;</form>");
    } catch (Exception e) {
        System.out.println(""+e.toString());
        out.write("no  article");
    }
%>
</body>
</html>