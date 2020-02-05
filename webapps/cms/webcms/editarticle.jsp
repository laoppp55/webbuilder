<%@ page import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.extendAttr.*,
                 org.jdom.input.*,
                 java.io.*,
                 org.jdom.*" contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean errorTitle = false;
    boolean errorContent = false;
    boolean errors = false;
    boolean success = false;
    String username = authToken.getUserID();

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String from = ParamUtil.getParameter(request, "fromflag");

    String maintitle = null, vicetitle = null, content = null, summary = null, keyword = null, source = null,
            maintitle1 = null, filename = null, content1 = null, year = null, month = null, day = null, author = null,
            brand = null, pic = null, bigpic = null, relatedID = null, articlepic = null, otherurl = null;
    int docLevel = 0, viceLevel = 0, status = 0, subscriber = 0, sortid = 0, hour = 0, minute = 0, weight = 0,
            stocknum = 0, modelID = 0, urltype = 0;
    float saleprice = 0, inprice = 0, marketprice = 0;

    IAuditManager auditMgr = AuditPeer.getInstance();
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    IArticleManager articleManager = ArticlePeer.getInstance();
    IColumnManager columnManager = ColumnPeer.getInstance();

    Article article = articleManager.getArticle(articleID, username);
    int columnID = article.getColumnID();
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));
    Column column = columnManager.getColumn(columnID);
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    List attechmentArticle = new ArrayList();
    List relArticle = new ArrayList();
    String baseURL = "http://" + request.getHeader("Host") + "/webbuilder/";

    //获得栏目文章分类
    List inlist = columnManager.getInheritanceType(column.getParentID());//读取继承的分类
    List typeList  = columnManager.getAllTypeForColumn("select * from tbl_type where columnid = "+columnID+" and parentid = 0 and referid = 0 order by createdate desc");//读取自定义的分类

    //合并继承和自定义的分类
    for(int i = 0;i < inlist.size(); i++){
        Producttype pt = (Producttype)inlist.get(i);
        typeList.add(pt);
    }
    //读取该文章已定义的分类
    String typestr = columnManager.getTypeNames(articleID);

    //修改文章数据
    List ptypelist = new ArrayList();
    if (doUpdate) {
        maintitle = ParamUtil.getParameter(request, "maintitle");
        maintitle = (maintitle != null) ? maintitle.trim() : maintitle;
        maintitle1 = maintitle;
        vicetitle = ParamUtil.getParameter(request, "vicetitle");
        content = ParamUtil.getParameter(request, "content");
        content = StringUtil.replace(content, "cmstextarea", "textarea").trim();
        content1 = content;
        author = ParamUtil.getParameter(request, "author");
        summary = ParamUtil.getParameter(request, "summary");
        source = ParamUtil.getParameter(request, "source");
        keyword = ParamUtil.getParameter(request, "keyword");
        relatedID = ParamUtil.getParameter(request, "relatedID");
        docLevel = ParamUtil.getIntParameter(request, "docLevel", 0);
        viceLevel = ParamUtil.getIntParameter(request, "viceDocLevel", 0);
        status = ParamUtil.getIntParameter(request, "status", 1);
        subscriber = ParamUtil.getIntParameter(request, "subscriber", 1);
        sortid = ParamUtil.getIntParameter(request, "sortid", 0);
        year = ParamUtil.getParameter(request, "year");
        month = ParamUtil.getParameter(request, "month");
        day = ParamUtil.getParameter(request, "day");
        hour = ParamUtil.getIntParameter(request, "hour", 0);
        minute = ParamUtil.getIntParameter(request, "minute", 0);
        filename = ParamUtil.getParameter(request, "filename");
        modelID = ParamUtil.getIntParameter(request, "modelID", 0);
        attechmentArticle = ParamUtil.getSelectParameterAllNamesAndValues(request, "attechment");
        articlepic = ParamUtil.getParameter(request, "articlepic");
        relArticle = (List) session.getAttribute("rlist");
        urltype = ParamUtil.getIntParameter(request, "url", 0);
        otherurl = ParamUtil.getParameter(request, "aurl");

        if (isProduct == 1) {
            brand = ParamUtil.getParameter(request, "brand");
            pic = ParamUtil.getParameter(request, "pic");
            bigpic = ParamUtil.getParameter(request, "bigpic");
            weight = ParamUtil.getIntParameter(request, "weight", 0);
            stocknum = ParamUtil.getIntParameter(request, "stocknum", 0);
            saleprice = ParamUtil.getFloatParameter(request, "saleprice", 0);
            inprice = ParamUtil.getFloatParameter(request, "inprice", 0);
            marketprice = ParamUtil.getFloatParameter(request, "marketprice", 0);
        }

        //文章分类
        ptypelist = (List)session.getAttribute("articletypeids");

        if (maintitle == null) errorTitle = true;
        if (content == null) errorContent = true;
        errors = errorTitle || errorContent;
    }

    if (!errors && doUpdate) {
        article.setColumnID(columnID);
        article.setMainTitle(maintitle);
        article.setViceTitle(vicetitle);
        article.setContent(content);
        article.setAuthor(author);
        article.setSummary(summary);
        article.setKeyword(keyword);
        article.setDocLevel(docLevel);
        article.setViceDocLevel(viceLevel);
        article.setPubFlag(1);
        article.setStatus(status);
        article.setSubscriber(subscriber);
        article.setLockStatus(0);
        article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        article.setSortID(sortid);
        article.setEditor(username);
        article.setSource(source);
        article.setRelatedArtID(relatedID);
        article.setSiteID(authToken.getSiteID());
        article.setFileName(filename);
        article.setModelID(modelID);
        article.setArticlepic(articlepic);
        article.setUrltype(urltype);
        article.setOtherurl(otherurl);

        if (isProduct == 1) {
            article.setSalePrice(saleprice);
            article.setInPrice(inprice);
            article.setMarketPrice(marketprice);
            article.setBrand(brand);
            article.setProductPic(pic);
            article.setProductBigPic(bigpic);
            article.setProductWeight(weight);
            article.setStockNum(stocknum);
        }

        if (article.getAuditFlag() == 0)
            article.setAuditFlag(0);
        else
            article.setAuditFlag(1);
        if (year == null)
            article.setPublishTime(new Timestamp(System.currentTimeMillis()));
        else {
            Timestamp publishtime = new Timestamp(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, Integer.parseInt(day), hour, minute, 0, 0);
            article.setPublishTime(publishtime);
        }

        List attrList = new ArrayList();
        if (isDefine == 1) {
            String xmlTemplate = extendMgr.getXMLTemplate(columnID);
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

                    int dataType = Integer.parseInt(e.getAttributeValue("datatype"));
                    if (value == null) value = "";
                    if (value.trim().length() == 0 && (dataType == 2 || dataType == 4)) value = "0";

                    ExtendAttr extend = new ExtendAttr();
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
        //文章轮换图片
        List turnpic = (List)session.getAttribute("turn_pic");
        extendMgr.update(attrList, attechmentArticle, relArticle, article,ptypelist, turnpic);

        //extendMgr.update(attrList, attechmentArticle, relArticle, article,ptypelist);

        //如果该篇文章为退稿文章，则将以前的审核信息更新为过期信息
        if (article.getAuditFlag() == 2) {
            auditMgr.updateAudit_Info("", articleID, "", 0);
        }
        success = true;
    }

    if (success) {
        session.removeAttribute("articletypeids");
        session.removeAttribute("turn_pic");
         out.write("<script>window.close();</script>");
        //response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id=" + articleID + "&column=" + columnID + "&start=" + start + "&range=" + range + "&fromflag=" + from));
        return;
    }

    if (!doUpdate) {
        maintitle = StringUtil.gb2iso4View(article.getMainTitle());
        vicetitle = StringUtil.gb2iso4View(article.getViceTitle());
        content = StringUtil.gb2iso4View(article.getContent());
        content = StringUtil.replace(content, "textarea", "cmstextarea").trim();
        author = StringUtil.gb2iso4View(article.getAuthor());
        summary = StringUtil.gb2iso4View(article.getSummary());
        keyword = StringUtil.gb2iso4View(article.getKeyword());
        filename = article.getFileName();
        docLevel = article.getDocLevel();
        viceLevel = article.getViceDocLevel();
        status = article.getStatus();
        relatedID = article.getRelatedArtID();
        subscriber = article.getSubscriber();
        source = StringUtil.gb2iso4View(article.getSource());
        sortid = article.getSortID();
        String tempbuf = article.getPublishTime().toString();
        year = tempbuf.substring(0, 4);
        month = tempbuf.substring(5, 7);
        day = tempbuf.substring(8, 10);
        tempbuf = tempbuf.substring(tempbuf.indexOf(" ") + 1);
        hour = Integer.parseInt(tempbuf.substring(0, 2));
        minute = Integer.parseInt(tempbuf.substring(3, 5));
        modelID = article.getModelID();
        attechmentArticle = articleManager.getOneArticleRelatedArticles(articleID);
        articlepic = article.getArticlepic();
        relArticle = articleManager.getRelatedArticles(articleID);
        urltype = article.getUrltype();
        otherurl = article.getOtherurl();

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

    //发生退稿
    String[] result = null;
    if (article.getAuditFlag() == 2) {
        result = auditMgr.getArticleInfo(articleID, username, 1);
    }
%>

<html>
<head>
<title>修改文章</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link REL="stylesheet" TYPE="text/css" HREF="../style/editor.css">
<script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
<script LANGUAGE="JavaScript" SRC="../toolbars/dhtmled.js"></script>
<script language=javascript>
function Editor(ename)
{
    window.open("editor.jsp?column=<%=columnID%>&ename=" + ename, "Editor", "width=870,height=600,left=0,top=0,status");
}

function upload_attrpic_onclick(attrname)
{
    window.open("../upload/upload.jsp?column=<%=columnID%>&attr=" + attrname, "", 'width=400,height=400,left=200,top=200');
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
<%if (isProduct == 1){%>
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
    if (createForm.inprice.value != "" && !checkFloat(createForm.inprice.value))
    {
        alert("商品进价应为小数型数据！");
        createForm.inprice.focus();
        return;
    }
    if (createForm.marketprice.value != "" && !checkFloat(createForm.marketprice.value))
    {
        alert("商品市场价应为小数型数据！");
        createForm.marketprice.focus();
        return;
    }
    if (createForm.stocknum.value == "")
    {
        alert("商品库存不能为空！");
        createForm.stocknum.focus();
        return;
    }
    if (!checkInt(createForm.stocknum.value))
    {
        alert("商品库存应为整型数据！");
        createForm.stocknum.focus();
        return;
    }
    if (createForm.weight.value == "")
    {
        alert("商品重量不能为空！");
        createForm.weight.focus();
        return;
    }
    if (!checkInt(createForm.weight.value))
    {
        alert("商品重量应为整型数据！");
        createForm.weight.focus();
        return;
    }
<%}%>
<%if (isDefine == 1){%>
    if (!checkExtendAttr(createForm))
        return;
<%}%>

    /*for (var i = 0; i < createForm.attechmentID.length; i++) {
        createForm.attechmentID[i].selected = true;
    }*/

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
<form action="editarticle.jsp" method="post" name="createForm">
<input type="hidden" name="doUpdate" value="true">
<input type="hidden" name="article" value="<%=articleID%>">
<input type="hidden" name="range" value="<%=range%>">
<input type=hidden name=column value="<%=columnID%>">
<input type="hidden" name="start" value="<%=start%>">
<input type="hidden" name="fromflag" value="<%=from%>">
<input type="hidden" name="filename" value="<%=filename%>">
<input type="hidden" name="lockstatus" value="0">
<input type=hidden name=modelSourceCodeFlag value=0>

<table width="100%" border=0 cellspacing=0 cellpadding=0>
    <tr bgcolor=#003366>
        <td colspan=3 height=2></td>
    </tr>
    <tr height=40>
        <td width="25%">&nbsp;<%=StringUtil.gb2iso4View(column.getCName())%>&nbsp;>&nbsp;修改文章</td>
        <td width="50%"><%
            if (!success && errors) {
                out.println("<span class=cur>修改文章失败，请重新再试。</span>");
            }
        %></td>
        <td width="25%" align=right>
            <input class=tine type=button value="  保存  " onClick="update(createForm);">&nbsp;&nbsp;
            <input class=tine type=button value="  取消  "
                   onclick="window.close();">&nbsp;&nbsp;
        </td>
    </tr>
    <tr bgcolor=#003366>
        <td colspan=3 height=2></td>
    </tr>
    <tr>
        <td colspan=3 height=4></td>
    </tr>
</table>

<table border=1 borderColorDark="#ffffec" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
<%if (result != null) {%>
<tr>
    <td class=line bgcolor="#CECFCE">
        <font color=red><b>退稿人签名：</b></font><input class=tine size=16 value="<%=(result[0]!=null)?result[0]:""%>"
                                                   disabled>&nbsp;&nbsp;
        <font color=red><b>退稿意见：</b></font><input class=tine size=50
                                                  value="<%=(result[1]!=null)?StringUtil.gb2iso4View(result[1]):""%>"
                                                  disabled>&nbsp;&nbsp;
        <font color=red><b>退稿时间：</b></font><%=(result[2] != null) ? result[2] : ""%>    </td>
</tr>
<%}%>
<tr>
    <td class=line>
        <font class=line <%=(errorTitle) ? (" color=\"#ff0000\"") : ""%>>标题(<a
                href=javascript:upload_attrpic_onclick("mt")>图</a>)</font><input class=tine name=maintitle
                                                                                 id="maintitle" size=36
                                                                                 value="<%=(maintitle!=null)?maintitle:""%>"
                                                                                 onDblClick=history("mt");
        title="双击鼠标左键即可选择历史图片">
        副标题(<a href=javascript:upload_attrpic_onclick("vt")>图</a>)<input class=tine name=vicetitle id="vicetitle"
                                                                         size=30
                                                                         value="<%=(vicetitle!=null)?vicetitle:""%>"
                                                                         onDblClick=history("vt");
        title="双击鼠标左键即可选择历史图片">
        作者(<a href=javascript:upload_attrpic_onclick("au")>图</a>)<input class=tine name=author id="author" size=10
                                                                        value="<%=(author!=null)?author:""%>"
                                                                        onDblClick=history("au");
        title="双击鼠标左键即可选择历史图片">
        来源(<a href=javascript:upload_attrpic_onclick("sr")>图</a>)<input class=tine name=source id="source" size=10
                                                                        value="<%=(source!=null)?source:""%>"
                                                                        onDblClick=history("sr");
        title="双击鼠标左键即可选择历史图片">
        <!--(<a href="javascript:fileUpload();">文件上传</a>)--></td>
</tr>
<tr>
    <td class=line color="#eeeeee">
        摘要<!--input class=tine name=summary size=80 value="<%=(summary!=null)?summary:""%>"-->
        <textarea cols="60" rows="2" id="summaryID" name="summary"><%=(summary!=null)?summary:""%></textarea>

&nbsp;&nbsp;    </td>
</tr>
<%if (isProduct == 1) {%>
<tr bgcolor="#eeeeee">
    <td class=line>&nbsp;</td>
</tr>
<%}%>
<%if (isDefine == 1) {%>
<tr bgcolor="#eeeeee">
    <td class=line><%=extendMgr.getExtendAttrForArticle(columnID, articleID)%>    </td>
</tr>
<%}%>
<tr>
    <td class=line>
        <font color="#FF0000"><b>关键字(;)</b></font><input class=tine name=keyword size=30
                                                         value="<%=(keyword!=null)?keyword:""%>">
        <font color="#FF0000"><b>文章图片</b></font>(<a href="javascript:upload_attrpic_onclick('apic')">图</a>)
        <input
            class=tine name="articlepic" id="articlepic" size=10 onDblClick=history("apic"); title="双击鼠标左键即可选择历史图片"
        value="<%=(articlepic != null) ? articlepic : ""%>">&nbsp;&nbsp;</td>
</tr>
<tr bgcolor="#ffffff">
    <td class=line colspan=2>
        <font color="#FF0000"><b>日期</b></font><input class=tine type=text size=3 maxlength=4 name=year
                                                     value="<%=(year!=null)?year:""%>">年
        <select name=month size=1 class=tine>
            <%for (int i = 1; i < 13; i++) {%>
            <option value=<%=i%> <%=(Integer.parseInt(month) == i) ? "selected" : ""%>><%=i%>            </option>
            <%}%>
        </select>月
        <select name=day size=1 class=tine>
            <%for (int i = 1; i < 32; i++) {%>
            <option value=<%=i%> <%=(Integer.parseInt(day) == i) ? "selected" : ""%>><%=i%>            </option>
            <%}%>
        </select>日
        <select name=hour size=1 class=tine>
            <%for (int i = 1; i < 24; i++) {%>
            <option value=<%=i%> <%=(hour == i) ? "selected" : ""%>><%=i%>            </option>
            <%}%>
        </select>时
        <select name=minute size=1 class=tine>
            <%for (int i = 1; i < 61; i++) {%>
            <option value=<%=i%> <%=(minute == i) ? "selected" : ""%>><%=i%>            </option>
            <%}%>
        </select>分

        <!--(<a href="javascript:fileUpload();">文件上传</a>)--><!--a href=javascript:ArticleAttechment();><font color="#FF0000"><b>文章推荐</b></font></a>&nbsp;&nbsp;-->    </td>
</tr>
<!--栏目分类-->
</table>
<table border="0" width="100%">
    <tr>
        <td>
            <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"><%=content%>
            </textarea>
            <script type="text/javascript">
                var oFCKeditor = new FCKeditor('content') ;
                oFCKeditor.BasePath = "../fckeditor/";
                oFCKeditor.Height = 520;
                oFCKeditor.ToolbarSet = "ArticleDefault";
                oFCKeditor.ReplaceTextarea();
            </script>
        </td>
    </tr>
</table>
</form>
<%
    if (errors) {
        out.println("<script lanagage='javascript'>");
        if (maintitle1 == null)
            out.println("createForm.maintitle.focus();");
        if (content1 == null)
            out.println("tbContentElement.focus();");
        out.println("</script>");
    }
%>

</body>
</html>
