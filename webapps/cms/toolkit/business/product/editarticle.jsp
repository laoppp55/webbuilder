<%@ page import="java.sql.*,
                 java.util.*,
                 java.text.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.extendAttr.*,
                 org.jdom.input.*,
                 com.fredck.FCKeditor.*,
                 java.io.*,
                 org.jdom.*" contentType="text/html;charset=GBK" %>

<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.Refers" %>
<%@ page import="java.sql.Date" %>
<%@ taglib uri="/FCKeditor" prefix="FCK" %>
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
    int siteid = authToken.getSiteID();

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String from = ParamUtil.getParameter(request, "fromflag");

    String maintitle = null, vicetitle = null, content = null, summary = null, keyword = null, source = null,
            maintitle1 = null, filename = null, content1 = null, year = null, month = null, day = null, author = null,
            brand = null, pic = null, bigpic = null, relatedID = null, articlepic = null, otherurl = null,beidate=null,markid="-1";
    int docLevel = 0, viceLevel = 0, status = 0, subscriber = 0, sortid = 0, hour = 0, minute = 0,  changepic = 0,
            stocknum = 0, modelID = 0, urltype = 0,score=0,voucher=0,salesnum=0;
    float saleprice = 0f, inprice = 0f, marketprice = 0f,vipprice=0f, weight = 0f;

    IAuditManager auditMgr = AuditPeer.getInstance();
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    IArticleManager articleManager = ArticlePeer.getInstance();
    IColumnManager columnManager = ColumnPeer.getInstance();
    IRefersManager referManager = RefersPeer.getInstance();

    Article article = articleManager.getArticle(articleID, username);
    int columnID = article.getColumnID();
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));
    Column column = columnManager.getColumn(columnID);
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isAudited = column.getIsAudited();
    List attechmentArticle = new ArrayList();
    List relArticle = new ArrayList();
    String baseURL = "http://" + request.getHeader("Host") + "/webbuilder/";
      markid=ParamUtil.getParameter(request,"markid");
    //修改文章数据 vv
    List ptypelist = new ArrayList();
    List pubcolumns = new ArrayList();
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
        //attechmentArticle = ParamUtil.getSelectParameterAllNamesAndValues(request, "attechment");
        pubcolumns = ParamUtil.getSelectParameterAllNamesAndValues(request, "pubcolumns");
        articlepic = ParamUtil.getParameter(request, "articlepic");
        relArticle = (List) session.getAttribute("rlist");
        urltype = ParamUtil.getIntParameter(request, "url", 0);
        otherurl = ParamUtil.getParameter(request, "aurl");
        beidate=ParamUtil.getParameter(request,"beidate");
        changepic = ParamUtil.getIntParameter(request,"changepic",0);
        if (isProduct == 1) {
            brand = ParamUtil.getParameter(request, "brand");
            pic = ParamUtil.getParameter(request, "pic");
            bigpic = ParamUtil.getParameter(request, "bigpic");
            weight = ParamUtil.getFloatParameter(request, "weight", 0);
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
        article.setAuditFlag(isAudited);
        article.setStatus(status);
        article.setSubscriber(subscriber);
        article.setLockStatus(0);
        article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        article.setSortID(sortid);
        article.setEditor(username);
        article.setSource(source);
        article.setRelatedArtID(relatedID);
        article.setSiteID(siteid);
        article.setFileName(filename);
        article.setModelID(modelID);
        article.setArticlepic(articlepic);
        article.setUrltype(urltype);
        article.setOtherurl(otherurl);
        article.setT1(0);
        article.setT2(0);
        article.setT3(0);
        article.setT4(0);
        article.setT5(0);
        article.setChangepic(changepic);
        if(beidate!=null){

            article.setBbdate(Date.valueOf(beidate)); 
        }
         article.setMarkid(markid);
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

         //审核意见
        List auditlist = (List)session.getAttribute("audit_article");

        extendMgr.update(attrList, attechmentArticle, relArticle, article,ptypelist, turnpic, auditlist);

        //extendMgr.update(attrList, attechmentArticle, relArticle, article,ptypelist);

        //如果该篇文章为退稿文章，则将以前的审核信息更新为过期信息
        if (article.getAuditFlag() == 2) {
            //auditMgr.updateAudit_Info("", articleID, "", 0);
        }
        success = true;
    }

    if (success) {
        session.removeAttribute("articletypeids");
        session.removeAttribute("turn_pic");
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id=" + articleID + "&column=" + columnID + "&start=" + start + "&range=" + range + "&fromflag=" + from));
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
        //attechmentArticle = articleManager.getOneArticleRelatedArticles(articleID,0);
        articlepic = article.getArticlepic();
        relArticle = referManager.getRefersArticleByColumn(articleID,columnID,siteid);
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
<title>修改商品</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link REL="stylesheet" TYPE="text/css" HREF="../../../style/editor.css">
<script language="JavaScript" src="../../../article/setday.js"></script>
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
    var winStr = "addRelatedArticle.jsp?from=1&param=" + para+"&column=<%=columnID%>";
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
    if (!checkFloat(createForm.vipprice.value))
    {
        alert("VIP售价应为小数型数据！");
        createForm.vipprice.focus();
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
    if (!checkInt(createForm.score.value))
    {
        alert("商品积分应为整型数据！");
        createForm.score.focus();
        return;
    }
    if (!checkInt(createForm.voucher.value))
    {
        alert("商品可用购物卷应为整型数据！");
        createForm.voucher.focus();
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
    if (!checkFloat(createForm.weight.value))
    {
        alert("商品重量应为浮点型数据！");
        createForm.weight.focus();
        return;
    }
<%}%>
<%if (isDefine == 1){%>
    if (!checkExtendAttr(createForm))
        return;
<%}%>

    //删除关联文章程序
    //for (var i = 0; i < createForm.attechmentID.length; i++) {
    //    createForm.attechmentID[i].selected = true;
    //}

    for (var i = 0; i < createForm.pcolumnsID.length; i++) {
        createForm.pcolumnsID[i].selected = true;
    }

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
    window.open("../upload/upload_turn_pic.jsp?column=<%=columnID%>&articleid=<%=articleID%>", "", "width=800,height=600,left=200,top=200,status,scrollbars");
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
function articleList(id,columnid)
{
    var winStr = "../template/articlelistFrame.jsp?articleid="+id;
    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
    if (isMSIE) {
        var retval = showModalDialog(winStr, window, "font-family:Verdana; font-size:12; dialogWidth:800px; dialogHeight:600px; status=no");
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
}   //图片轮换效果
    function changedata(selObj){
              if(selObj.options[selObj.selectedIndex].value == "100"){
                  window.open ('../upload/suijipic.html');
              }else if(selObj.options[selObj.selectedIndex].value == "101"){
                   window.open ('../upload/jianbianpic.html');
              }else if(selObj.options[selObj.selectedIndex].value == "102"){
                 window.open ('../upload/flashpic.html');
              }
        }
  function article_audit(){
    window.open("article_audit.jsp?articleid=<%=articleID%>", "", 'width=800,height=600,left=200,top=200,status,scrollbars');
}
</script>
</head>

<body>
<form action="editarticle.jsp" method="post" name="createForm">
<input type="hidden" name="doUpdate" value="true">
<input type=hidden name=column value="<%=columnID%>">
<input type="hidden" name="article" value="<%=articleID%>">
<input type="hidden" name="range" value="<%=range%>">
<input type="hidden" name="start" value="<%=start%>">
<input type="hidden" name="fromflag" value="<%=from%>">
<input type="hidden" name="filename" value="<%=filename%>">
<input type=hidden name=markid id="markid" value="-1">
<input type="hidden" name="lockstatus" value="0">
<input type=hidden name=modelSourceCodeFlag value=0>
<input type=hidden name=template_or_article_flag value="1">    
<table width="100%" border=0 cellspacing=0 cellpadding=0>
    <tr bgcolor=#003366>
        <td colspan=3 height=2></td>
    </tr>
    <tr height=40>
        <td width="25%">&nbsp;<%=StringUtil.gb2iso4View(column.getCName())%>&nbsp;>&nbsp;修改商品</td>
        <td width="50%"><%
            if (!success && errors) {
                out.println("<span class=cur>修改商品失败，请重新再试。</span>");
            }
        %></td>
        <td width="25%" align=right>
            <input class=tine type=button value="  保存  " onclick="update(createForm);">&nbsp;&nbsp;
            <input class=tine type=button value="  取消  " onclick="javascript:window.close();">&nbsp;&nbsp;
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
        <font color=red><b>退稿时间：</b></font><%=(result[2] != null) ? result[2] : ""%>
    </td>
</tr>
<%}%>
<tr>
    <td class=line>
        <font class=line <%=(errorTitle) ? (" color=\"#ff0000\"") : ""%>><%=(isProduct==1)?"商品名称":"标题"%>(<a
                href="javascript:upload_attrpic_onclick('mt')">图</a>)</font><input class=tine name=maintitle
                                                                                 id="maintitle" size=20
                                                                                 value="<%=(maintitle!=null)?maintitle:""%>"
                                                                                 onDblClick="history('mt');" title="双击鼠标左键即可选择历史图片">
        <%=(isProduct==1)?"商品简称":"副标题"%>(<a href="javascript:upload_attrpic_onclick('vt')">图</a>)<input class=tine name=vicetitle id="vicetitle"
                                                                         size=30
                                                                         value="<%=(vicetitle!=null)?vicetitle:""%>"
                                                                         onDblClick="history('vt');"
        title="双击鼠标左键即可选择历史图片">
        作者(<a href="javascript:upload_attrpic_onclick('au')">图</a>)<input class=tine name=author id="author" size=10
                                                                        value="<%=(author!=null)?author:""%>"
                                                                        onDblClick="history('au');"
        title="双击鼠标左键即可选择历史图片">
        来源(<a href="javascript:upload_attrpic_onclick('sr')">图</a>)<input class=tine name=source id="source" size=10
                                                                        value="<%=(source!=null)?source:""%>"
                                                                        onDblClick="history('sr');"
        title="双击鼠标左键即可选择历史图片">
        <!--(<a href="javascript:fileUpload();">文件上传</a>)-->
        <%=(isProduct==1)?"显示模板":"文章模板"%><input class=tine name=modelID id="modelID" size=8 value="<%=modelID%>" title="双击鼠标左键即可弹出模板选择页面"
                   onDblClick="selectArticleModel();">
    </td>
</tr>
<tr>
    <td class=line color="#eeeeee">
        <%=(isProduct==1)?"商品摘要":"摘要"%><!--input class=tine name=summary size=80 value="<%=(summary!=null)?summary:""%>"-->
        <textarea cols="60" rows="2" id="summaryID" name="summary"><%=(summary!=null)?summary:""%></textarea>

        &nbsp;&nbsp;<!--a href=javascript:AddArticleAttechment();><b><font color="#FF0000">附件</font></b></a>:
        <select size=2 name="attechment" id="attechmentID" style="width:150" multiple-->
            <!--%
                relatedArticle relatearticle = new relatedArticle();
                String value = "";
                for (int i = 0; i < attechmentArticle.size(); i++) {
                    relatearticle = (relatedArticle) attechmentArticle.get(i);
                    value = "a" + relatearticle.getJointedID() + "-" + relatearticle.getChineseName();
                    out.println("<option value='" + value + "'>" + StringUtil.gb2iso4View(relatearticle.getChineseName()) + "</option>");
                }
            %>
        </select-->

        <a href=javascript:AddPubColumns();><b>发布栏目</b></a><b>：</b>
        <select size=2 name="pubcolumns" id="pcolumnsID" style="width:150" multiple>
            <%
                Refers refer = new Refers();
                for (int i = 0; i < relArticle.size(); i++) {
                    refer = (Refers) relArticle.get(i);
                    String value = "c" + refer.getColumnid() + "-" + refer.getColumnname();
                    out.println("<option value='" + value + "'>" + StringUtil.gb2iso4View(refer.getColumnname()) + "</option>");
                }
            %>
        </select>
    </td>
</tr>
<%if (isProduct == 1) {%>
<tr bgcolor="#eeeeee">
    <td class=line>
        售价：<input class=tine name=saleprice size=6 value="<%=article.getSalePrice()%>">
        VIP价：<input class=tine name=vipprice size=6 value="<%=article.getVIPPrice()%>">
        进价：<input class=tine name=inprice size=6 value="<%=article.getInPrice()%>">
        市场价：<input class=tine name=marketprice size=6 value="<%=article.getMarketPrice()%>">
        积分：<input class=tine name=score size=6 value="<%=article.getScore()%>">
        购物卷：<input class=tine name=voucher size=6 value="<%=article.getVoucher()%>">
        库存：<input class=tine name=stocknum size=6 value="<%=article.getStockNum()%>">
        厂商：<input class=tine name=brand size=12
                  value="<%=(article.getBrand()==null)?"":StringUtil.replace(StringUtil.gb2iso4View(article.getBrand()),"\"","&quot;")%>">
        重量(克)：<input class=tine name=weight size=5 value="<%=article.getProductWeight()%>">
        图片(<a href="javascript:upload_attrpic_onclick('pic')">图</a>)：<input class=tine name=pic id="pic" size=12
                                                                          value="<%=(article.getProductPic()==null)?"":article.getProductPic()%>"
                                                                          onDblClick="history('pic');"
        title="双击鼠标左键即可选择历史图片">
        大图片(<a href="javascript:upload_attrpic_onclick('bigpic')">图</a>)：<input class=tine name=bigpic id="bigpic"
                                                                              size=12
                                                                              value="<%=(article.getProductBigPic()==null)?"":article.getProductBigPic()%>"
                                                                              onDblClick="history('bigpic');"
        title="双击鼠标左键即可选择历史图片">
    </td>
</tr>
<%}%>
<%if (isDefine == 1) {%>
<tr bgcolor="#eeeeee">
    <td class=line><%=extendMgr.getExtendAttrForArticle(columnID, articleID)%>
    </td>
</tr>
<%}%>
<tr>
    <td class=line>
        <font color="#FF0000"><b>关键字(;)</b></font><input class=tine name=keyword size=30
                                                         value="<%=(keyword!=null)?keyword:""%>">
        <font color="#FF0000"><b>排序</b></font><input class=tine name=sortid size=4 value="<%=sortid%>">
        <font color="#FF0000"><b>主权重</b></font><input class=tine name=docLevel id="docLevel" size=4
                                                      onDblClick="AddRelatedArticleID(0);" value="<%=docLevel%>"
                                                      maxlength="6" title="双击鼠标左键即可选择某篇文章ID作为本篇文章的权重">
        <font color="#FF0000"><b>次权重</b></font><input class=tine name=viceDocLevel id="viceDocLevel" size=4
                                                      onDblClick="AddRelatedArticleID(1);" value="<%=viceLevel%>"
                                                      maxlength="6" title="双击鼠标左键即可选择某篇文章ID作为本篇文章的次权重">
        <font color="#FF0000"><b>父文章</b></font>
        <input type="hidden" name="relatedID" id="relatedID" size="4" value=<%=(relatedID==null)?"":relatedID%>>
        <input name="parentArticleTitle" id="parentArticleTitleID" size="10" onDblClick="AddRelatedArticleID(2);"
               value="<%=(relatedID==null)?"":relatedID%>" title="双击鼠标左键即可弹出父文章选择页面">
        <font color="#FF0000"><b>特效图片</b></font>(<a href="javascript:upload_attrpic_onclick('apic')">图</a>)<input
            class=tine name="articlepic" id="articlepic" size=10 onDblClick="history('apic');" title="双击鼠标左键即可选择历史图片"
        value="<%=(articlepic != null) ? articlepic : ""%>">&nbsp;&nbsp;
        <font color="#FF0000"><b>订阅</b></font><input type=radio <%=(subscriber==1)?"checked":""%> name="subscriber"
                                                     value="1">是<input type=radio <%=(subscriber==0)?"checked":""%>
                                                                       name="subscriber" value="0">否
    </td>
</tr>
<tr bgcolor="#ffffff">
    <td class=line colspan=2>
        <font color="#FF0000"><b>日期</b></font><input class=tine type=text size=3 maxlength=4 name=year
                                                     value="<%=(year!=null)?year:""%>">年
        <select name=month size=1 class=tine>
            <%for (int i = 1; i < 13; i++) {%>
            <option value=<%=i%> <%=(Integer.parseInt(month) == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>月
        <select name=day size=1 class=tine>
            <%for (int i = 1; i < 32; i++) {%>
            <option value=<%=i%> <%=(Integer.parseInt(day) == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>日
        <select name=hour size=1 class=tine>
            <%for (int i = 1; i < 24; i++) {%>
            <option value=<%=i%> <%=(hour == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>时
        <select name=minute size=1 class=tine>
            <%for (int i = 1; i < 61; i++) {%>
            <option value=<%=i%> <%=(minute == i) ? "selected" : ""%>><%=i%>
            </option>
            <%}%>
        </select>分

        <!--(<a href="javascript:fileUpload();">文件上传</a>)-->
        自定义URL<input type=radio checked name="url" value="0" onclick="useMyUrl(0);" <%if(urltype==0){%>checked<%}%>>否
        <input type=radio name="url" value="1" onclick="useMyUrl(1);" <%if(urltype==1){%>checked<%}%>>
        是&nbsp;&nbsp;文章URL<input type=text class=tine name=aurl size=31 <%if(urltype==0){%>disabled<%}%>
                                 value="<%=otherurl==null?"":otherurl%>">


        <font color="#FF0000"><b>使用</b></font><input type=radio <%=(status==1)?"checked":""%> name="status"
                                                     value="1">是<input type=radio <%=(status==0)?"checked":""%>
                                                                       name="status" value="0">否<input
            type=radio <%=(status==2)?"checked":""%> name="status" value="2">归档&nbsp;&nbsp;
    </td>
</tr>
<tr>
    <td ID=bottomofFld></td>
</tr>
<tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
    <td>
        <input type=button name=articletype id=turnpic size=30 value="上传附图" onclick="javascript:upload_turn_pic();">&nbsp;
         <font color="#FF0000"><b>    选择多张图片轮换效果： </b></font>
                <select name="changepic" size="1" style="width:120;font-size:9pt" onchange="changedata(this)">
                    <option value="0" selected>没有选择</option>
                    
                    <option value="101" >渐变轮换效果</option>
                    <option value="102" >生成Flash效果</option>
                </select>
    </td>
</tr>  <tr bgcolor="#FFFFFF">

       <td colspan="2" bgcolor="#FFFFFF" class="txt"> 备用日期
           <input type="text" size="10" name="beidate" <%if(article.getBbdate()!=null){%>value="<%=article.getBbdate()%>"<%}%> onfocus="setday(this)" readonly> &nbsp;&nbsp;销售数量<input type="text" readonly name="salesnum" value="<%=article.getSalesnum()%>">&nbsp;&nbsp;<a href="javascript:articleList(<%=article.getID()%>,<%=columnID%>)">文章推荐到文章列表</a>

           <input type="button" name="articleaudit" id="articleaudit" size="30" value="编辑审核意见" onclick="javascript:article_audit();" >
       </td>
      </tr>
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
