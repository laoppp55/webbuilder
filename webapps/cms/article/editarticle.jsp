<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.audit.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.refers.*,
                 org.jdom.input.*,
                 java.io.*,
                 org.jdom.*" contentType="text/html;charset=utf-8" %>

<%@ page import="java.sql.Date" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="com.bizwink.cms.multimedia.Multimedia" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="com.bizwink.cms.multimedia.Attechment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!--%@ taglib uri="/FCKeditor" prefix="FCK" %-->
<%
    request.setCharacterEncoding("utf-8");
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
    String company = authToken.getDepartment();
    String sitename = authToken.getSitename();
    sitename = StringUtil.replace(sitename,".","_");

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int flag = ParamUtil.getIntParameter(request,"flag",0);
    int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);
    int arttypeval = ParamUtil.getIntParameter(request, "arttype", 0);
    //被修改的数据在页面显示列表中的行号
    int rownum = ParamUtil.getIntParameter(request,"rownum",0);
    String from = ParamUtil.getParameter(request, "fromflag");

    String maintitle = null, vicetitle = null, content = null, summary = null, keyword = null, source = null,attechments=null,deptid=null,
            maintitle1 = null, filename = null,mediafilename=null,mediafile=null, content1 = null, author = null,indexnum=null,s_publishtime = null,
            brand = null, pic = null, bigpic = null, relatedID = null, articlepic = null, otherurl = null,beidate=null,markid="-1";
    int docLevel = 0, viceLevel = 0, status = 0, subscriber = 0, sortid = 0,changepic = 0, stocknum = 0, modelID = 0, urltype = 0,score=0,voucher=0,salesnum=0;
    float saleprice = 0f, inprice = 0f, marketprice = 0f,vipprice=0f, weight = 0f;

    IAuditManager auditMgr = AuditPeer.getInstance();
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    IArticleManager articleManager = ArticlePeer.getInstance();
    IColumnManager columnManager = ColumnPeer.getInstance();
    IRefersManager referManager = RefersPeer.getInstance();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Article article = articleManager.getArticle(articleID, username);
    int columnID = article.getColumnID();
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));
    Column column = columnManager.getColumn(columnID);
    String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename + column.getDirName() + "images" + java.io.File.separator;
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isAudited = column.getIsAudited();
    int isPosition = column.getIsPosition();                                   //是否包含地理位置信息
    String baseURL = "http://" + request.getHeader("Host") + "/webbuilder/";
    markid=ParamUtil.getParameter(request,"markid");
    //修改文章数据
    List recommends = new ArrayList();                    //设置文章被推荐的位置
    List mmfiles = new ArrayList();                       //内容中包含的多媒体文件列表
    List pubcolumns = new ArrayList();
    if (doUpdate) {
        maintitle = filter.excludeHTMLCode(ParamUtil.getParameter(request, "maintitle"));
        maintitle = (maintitle != null) ? maintitle.trim() : maintitle;
        maintitle1 = maintitle;
        vicetitle = filter.excludeHTMLCode(ParamUtil.getParameter(request, "vicetitle"));
        content = ParamUtil.getParameter(request, "content");
        content = StringUtil.replace(content, "cmstextarea", "textarea").trim();
        author = filter.excludeHTMLCode(ParamUtil.getParameter(request, "author"));
        deptid = filter.excludeHTMLCode(ParamUtil.getParameter(request, "deptid"));
        summary = filter.excludeHTMLCode(ParamUtil.getParameter(request, "summary"));
        source = filter.excludeHTMLCode(ParamUtil.getParameter(request, "source"));
        keyword = filter.excludeHTMLCode(ParamUtil.getParameter(request, "keyword"));
        relatedID = filter.excludeHTMLCode(ParamUtil.getParameter(request, "relatedID"));
        docLevel = ParamUtil.getIntParameter(request, "docLevel", 0);
        viceLevel = ParamUtil.getIntParameter(request, "viceDocLevel", 0);
        status = ParamUtil.getIntParameter(request, "status", 1);
        mediafilename = filter.excludeHTMLCode(ParamUtil.getParameter(request, "mediafilename"));     //点击工具棒上传的视频文件，可以有多个视频文件
        mediafile = filter.excludeHTMLCode(ParamUtil.getParameter(request, "media"));              //从视频文件输入框上传的多媒体文件
        if (mediafilename != null) {
            mediafilename = mediafilename.substring(0,mediafilename.length()-1);
            if (!mediafilename.endsWith(".flv")) status = 3;              //如果新创建的文章包含多媒体文件，并且对媒体文件扩展名不为flv,设置文件的状态为3，表示文章处于等待状态，由多媒体转换进程修改该状态
            Pattern p = Pattern.compile("\\|", Pattern.CASE_INSENSITIVE);
            String mmf[] = p.split(mediafilename);
            Multimedia mult = null;
            for(int i=0;i<mmf.length;i++) {
                mult = new Multimedia();
                mult.setSiteid(siteid);
                mult.setArticleid(articleID);
                mult.setDirname(column.getDirName());
                mult.setFilepath(uploadPath);
                int posi = mmf[i].indexOf(":");
                mult.setOldfilename(mmf[i].substring(posi + 1));
                mult.setNewfilename(mmf[i].substring(0,posi));
                mult.setEncodeflag(0);
                mmfiles.add(mult);
            }
        }

        subscriber = ParamUtil.getIntParameter(request, "subscriber", 1);
        sortid = ParamUtil.getIntParameter(request, "sortid", 0);
        filename = filter.excludeHTMLCode(ParamUtil.getParameter(request, "filename"));
        modelID = ParamUtil.getIntParameter(request, "modelID", 0);
        pubcolumns = ParamUtil.getSelectParameterAllNamesAndValues(request, "pubcolumns");
        recommends = ParamUtil.getSelectParameterAllNamesAndValues(request, "recommend");
        attechments = ParamUtil.getParameter(request, "attechments");
        articlepic = filter.excludeHTMLCode(ParamUtil.getParameter(request, "articlepic"));
        urltype = ParamUtil.getIntParameter(request, "url", 0);
        otherurl = filter.excludeHTMLCode(ParamUtil.getParameter(request, "aurl"));
        beidate=filter.excludeHTMLCode(ParamUtil.getParameter(request, "beidate"));
        s_publishtime = filter.excludeHTMLCode(ParamUtil.getParameter(request, "datetime"));
        changepic = ParamUtil.getIntParameter(request, "changepic", 0);
        pic = filter.excludeHTMLCode(ParamUtil.getParameter(request, "pic"));
        bigpic = filter.excludeHTMLCode(ParamUtil.getParameter(request, "bigpic"));
        //bigpic = ParamUtil.getParameter(request, "bigpic");

        if (isProduct == 1) {
            brand = filter.excludeHTMLCode(ParamUtil.getParameter(request, "brand"));
            weight = ParamUtil.getFloatParameter(request, "weight", 0);
            stocknum = ParamUtil.getIntParameter(request, "stocknum", 0);
            saleprice = ParamUtil.getFloatParameter(request, "saleprice", 0);
            inprice = ParamUtil.getFloatParameter(request, "inprice", 0);
            marketprice = ParamUtil.getFloatParameter(request, "marketprice", 0);
            vipprice = ParamUtil.getFloatParameter(request, "vipprice", 0);
            score = ParamUtil.getIntParameter(request, "score", 0);
            voucher = ParamUtil.getIntParameter(request, "voucher", 0);
        }

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
        article.setMediafile(mediafile);       //从视频文件输入框上传的多媒体文件
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
        article.setProductPic(pic);
        article.setProductBigPic(bigpic);
        article.setMarkid(markid);
        if (isProduct == 1) {
            article.setSalePrice(saleprice);
            article.setInPrice(inprice);
            article.setMarketPrice(marketprice);
            article.setBrand(brand);
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
        if (s_publishtime == null)
            article.setPublishTime(new Timestamp(System.currentTimeMillis()));
        else {
            Timestamp publishtime = new Timestamp(sf.parse(s_publishtime).getTime());
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
                    int controltype = Integer.parseInt(e.getAttributeValue("type"));
                    if (value == null) value = "";
                    if (value.trim().length() == 0 && (dataType == 2 || dataType == 4)) value = "0";

                    ExtendAttr extend = new ExtendAttr();
                    extend.setEName(ename);
                    extend.setDataType(dataType);
                    extend.setControlType(controltype);
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

        List<Attechment> attechmentList=null;
        if (attechments!=null && attechments!="") {
            String json_attechments = "[" +  attechments + "]";
            JSONArray json = JSONArray.fromObject(json_attechments);                   //json_attechments是json字符串
            attechmentList= (List<Attechment>) JSONArray.toCollection(json, Attechment.class);
        }

        //文章轮换图片
        List turnpic = (List)session.getAttribute("turn_pic");
        //审核意见
        List auditlist = (List)session.getAttribute("audit_article");
        extendMgr.update(attrList,attechmentList,article,pubcolumns,recommends,turnpic,auditlist,mmfiles);

        //如果该篇文章为退稿文章，则将以前的审核信息更新为过期信息
        if (article.getAuditFlag() == 2) {
            auditMgr.updateAudit_Info(username, articleID, "", 0,0);
        }
        success = true;
    }

    if (success) {
        session.removeAttribute("articletypeids");
        session.removeAttribute("turn_pic");
        //response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id=" + articleID + "&column=" + columnID + "&start=" + start + "&range=" + range + "&searchFlag=" + doSearchFlag + "&flag=" + flag + "&ascdesc=" + ascdesc + "&fromflag=" + from));
        //return;
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
        indexnum = article.getIndexnum();
        sortid = article.getSortID();
        s_publishtime = sf.format(article.getPublishTime());
        modelID = article.getModelID();
        articlepic = article.getArticlepic();
        pubcolumns = referManager.getRefersArticleByColumn(articleID,columnID,siteid);
        recommends = extendMgr.getRecommendMarks(articleID,columnID,siteid);
        urltype = article.getUrltype();
        otherurl = article.getOtherurl();
        deptid = article.getDeptid();

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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/editor.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">

    <script language="JavaScript" src="../js/setday.js"></script>
    <script language="JavaScript" src="../js/cities.js"></script>
    <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="../toolbars/btnclick.js"></script>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script language=javascript>
        $(document).ready(function() {
            var success = <%=success%>;
            if (success == true) {
                var articleid = <%=articleID%>;
                var maintitle = "<%=maintitle%>";
                var publishtime = "<%=sf.format(article.getPublishTime())%>";
                var lastupdate = "<%=sf.format(article.getLastUpdated())%>";
                var doclevel =<%=article.getDocLevel()%>;
                var vicedoclevel = <%=article.getViceDocLevel()%>;
                var editor = "<%=article.getEditor()%>";
                var columnid = <%=columnID%>;
                var range = <%=range%>;
                var currentPage = <%=currentPage%>;
                var doSearchFlag = <%=doSearchFlag%>;
                var flag = <%=flag%>;
                var ascdesc = <%=ascdesc%>;
                var arttypeval = <%=arttypeval%>;
                //window.opener.updateArticleListData(articleid,maintitle, publishtime, lastupdate, doclevel, vicedoclevel, editor);
                window.opener.articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                alert("文章修改成功！！！");
                window.close();
            } else
                init('province','city');
        })

        function Editor(ename) {
            window.open("editor.jsp?column=<%=columnID%>&ename=" + ename, "Editor", "width=870,height=600,left=0,top=0,status");
        }

        function upload_attrpic_onclick(attrname) {
            window.open("../upload/upload.jsp?column=<%=columnID%>&attr=" + attrname, "", 'width=400,height=400,left=200,top=200');
        }

        //在对媒体文件对话框中上传多媒体文件
        function upload_media_onclick() {
            window.open("../upload/mmupload.jsp?column=<%=columnID%>&fromflag=1", "", 'width=600,height=400,left=200,top=200');
        }

        //双击选择文章分类
        function addarticletype() {
            window.open("selectarticletype.jsp?column=<%=columnID%>&articleid=<%=articleID%>", "", "top=100,left=100,width=420,height=360");
        }

        //增加相关文章
        function upload_attachments(){
            var winStr = "addArticleAttechment.jsp?column=<%=columnID%>&article=<%=articleID%>";
            window.open(winStr, "", "width=800,height=600");
        }

        //向其他栏目发布文章
        function AddPubColumns() {
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
        function selectArticleModel() {
            var winStr = "selectModel.jsp?column=<%=columnID%>";
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                var retval = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:800px; dialogHeight:600px; status=no");
                if (retval != "" && retval != undefined) {
                    document.createForm.modelID.value = retval;
                }
            } else {
                var wins = window.open(winStr, "", "width=800,height=600");
                wins.focus();
            }
        }

        //向推荐文章列表推荐文章
        function recommendArticle() {
            var winStr = "recommendArticle.jsp?column=<%=columnID%>";
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open(winStr, "recommend_window", "width=" + iWidth +",height=" + iHeight +",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
        }

        function update(createForm) {
            if (createForm.maintitle.value == "") {
                alert("主标题不能为空！");
                createForm.maintitle.focus();
                return;
            }

            <%if (isProduct == 1){%>
            if (createForm.saleprice.value == "") {
                alert("商品售价不能为空！");
                createForm.saleprice.focus();
                return;
            }
            if (!checkFloat(createForm.saleprice.value)) {
                alert("商品售价应为小数型数据！");
                createForm.saleprice.focus();
                return;
            }
            if (!checkFloat(createForm.vipprice.value)) {
                alert("VIP售价应为小数型数据！");
                createForm.vipprice.focus();
                return;
            }
            if (createForm.inprice.value != "" && !checkFloat(createForm.inprice.value)) {
                alert("商品进价应为小数型数据！");
                createForm.inprice.focus();
                return;
            }
            if (createForm.marketprice.value != "" && !checkFloat(createForm.marketprice.value)) {
                alert("商品市场价应为小数型数据！");
                createForm.marketprice.focus();
                return;
            }
            if (!checkInt(createForm.score.value)) {
                alert("商品积分应为整型数据！");
                createForm.score.focus();
                return;
            }
            if (!checkInt(createForm.voucher.value)) {
                alert("商品可用购物卷应为整型数据！");
                createForm.voucher.focus();
                return;
            }
            if (createForm.stocknum.value == "") {
                alert("商品库存不能为空！");
                createForm.stocknum.focus();
                return;
            }
            if (!checkInt(createForm.stocknum.value)) {
                alert("商品库存应为整型数据！");
                createForm.stocknum.focus();
                return;
            }
            if (createForm.weight.value == "") {
                alert("商品重量不能为空！");
                createForm.weight.focus();
                return;
            }
            if (!checkFloat(createForm.weight.value)) {
                alert("商品重量应为浮点型数据！");
                createForm.weight.focus();
                return;
            }
            <%}%>

            <%if (isDefine == 1){%>
            if (!checkExtendAttr(createForm))
                return;
            <%}%>

            for (var i = 0; i < createForm.recommendID.length; i++) {
                createForm.recommendID[i].selected = true;
            }

            //for (var i = 0; i < createForm.pcolumnsID.length; i++) {
            //    createForm.pcolumnsID[i].selected = true;
            //}

            createForm.submit();
        }

        function delItem() {
            var select = false;
            for (var i = 0; i < createForm.attechment.length; i++) {
                if (createForm.attechment[i].selected) {
                    select = true;
                    break;
                }
            }

            if (select) {
                for (var i = 0; i < createForm.attechment.length; i++)
                    if (createForm.attechment[i].selected)
                        createForm.attechment[i] = null;
            } else {
                alert("请选择相关文章！");
            }
        }

        function checkFloat(str) {
            var retstr = false;
            var regstr = /[^0-9.]/gi;
            if (regstr.exec(str) == null) {
                retstr = true;
            }
            return retstr;
        }

        function checkInt(str) {
            var retstr = false;
            var regstr = /[^0-9]/gi;
            if (regstr.exec(str) == null) {
                retstr = true;
            }
            return retstr;
        }

        function fileUpload() {
            window.open('fileUpload.jsp?column=<%=columnID%>&filename=<%=filename%>', '', 'left=300,top=200,width=400,height=200');
        }

        //双击选择历史图片
        function history(attrname) {
            window.open("../upload/historypic.jsp?column=<%=columnID%>&pictype=1&attr=" + attrname, "", "left=100,top=100,width=800,height=600");
        }

        function upload_turn_pic(){
            window.open("../upload/upload_turn_pic.jsp?column=<%=columnID%>&articleid=<%=articleID%>", "", "width=800,height=600,left=200,top=200,status,scrollbars");
        }

        function useMyUrl(f) {
            if (f == 1)
                createForm.aurl.disabled = 0;
            else
                createForm.aurl.disabled = 1;
        }

        function article_audit(){
            window.open("article_audit.jsp?articleid=<%=articleID%>", "", 'width=800,height=600,left=200,top=200,status,scrollbars');
        }

        function closewin(articleid){
            htmlobj=$.ajax({
                url:"updateArticleStatus.jsp",
                data:{
                    article:articleid
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.result=="true")
                        window.close();
                    else
                        alert("未解锁该篇被编辑的文章");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    alert(jqXHR.readyState);
                    alert(jqXHR.statusText);
                    alert(textStatus);
                    alert(errorThrown);
                }
            });
        }
    </script>
</head>

<body>
<form action="editarticle.jsp" method="post" name="createForm">
    <input type="hidden" name="doUpdate" value="true">
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name="article" value="<%=articleID%>">
    <input type="hidden" name="range" value="<%=range%>">
    <input type="hidden" name="start" value="<%=currentPage%>">
    <input type="hidden" name="searchFlag" value="<%=doSearchFlag%>">
    <input type="hidden" name="flag" value="<%=flag%>">
    <input type="hidden" name="ascdesc" value="<%=ascdesc%>">
    <input type=hidden name=arttype value="<%=arttypeval%>">
    <input type="hidden" name="fromflag" value="<%=from%>">
    <input type="hidden" name="filename" value="<%=filename%>">
    <input type="hidden" name="rownum" value="<%=rownum%>">
    <input type="hidden" name=deptid value="<%=deptid%>">
    <input type="hidden" name=mediafilename value="">
    <input type="hidden" name=attechments value="">
    <input type="hidden" name=markid id="markid" value="-1">
    <input type="hidden" name="lockstatus" value="0">
    <input type="hidden" name=modelSourceCodeFlag value=0>
    <input type="hidden" name=template_or_article_flag value="1">
    <input type="hidden" name=msword id="mswordid" value="">

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
                <!--input class=tine type=button value="  附件上传  " onclick="attachement(createForm);">&nbsp;&nbsp;-->
                <input class=tine type=button value="  保存  " onclick="update(createForm);">&nbsp;&nbsp;
                <input class=tine type=button value="  取消  " onclick="closewin(<%=articleID%>);">&nbsp;&nbsp;
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
                <font class=line <%=(errorTitle) ? (" color=\"#ff0000\"") : ""%>><%=(isProduct==1)?"商品名称：":"标题："%>(<a
                        href="javascript:upload_attrpic_onclick('mt')">图</a>)</font><input class=tine name=maintitle
                                                                                           id="maintitle" size=50
                                                                                           value="<%=(maintitle!=null)?maintitle:""%>"
                                                                                           onDblClick="history('mt');" title="双击鼠标左键即可选择历史图片">
                <%=(isProduct==1)?"商品简称：":"副标题："%>(<a href="javascript:upload_attrpic_onclick('vt')">图</a>)<input class=tine name=vicetitle id="vicetitle"
                                                                                                                size=40
                                                                                                                value="<%=(vicetitle!=null)?vicetitle:""%>"
                                                                                                                onDblClick="history('vt');"
                                                                                                                title="双击鼠标左键即可选择历史图片">
                作者：(<a href="javascript:upload_attrpic_onclick('au')">图</a>)<input class=tine name=author id="author" size=25
                                                                                  value="<%=(author!=null)?author:""%>"
                                                                                  onDblClick="history('au');"
                                                                                  title="双击鼠标左键即可选择历史图片">
                来源：(<a href="javascript:upload_attrpic_onclick('sr')">图</a>)<input class=tine name=source id="source" size=25
                                                                                  value="<%=(source!=null)?source:""%>"  readonly="readonly"
                                                                                  onDblClick="history('sr');"
                                                                                  title="双击鼠标左键即可选择历史图片">
            </td>
        </tr>
        <tr  bgcolor="#eeeeee">
            <td>
                关键字(;)：<input class=tine name=keyword size=40 value="<%=(keyword!=null)?keyword:""%>">
                索引号：<input class=tine name=indexnum   size=40 value="<%=(indexnum!=null)?indexnum:""%>" readonly>
                <%=(isProduct==1)?"显示模板：":"文章模板："%><input class=tine name=modelID id="modelID" size=20 value="<%=modelID%>" title="双击鼠标左键即可弹出模板选择页面" onDblClick="selectArticleModel();">
                <%if (isProduct == 0) {%>
                <!--font color="#FF0000"><b>使用</b></font>
        <input type=radio <%=(status==1)?"checked":""%> name="status" value="1">是
        <input type=radio <%=(status==0)?"checked":""%> name="status" value="0">否
        <input type=radio <%=(status==2)?"checked":""%> name="status" value="2">归档&nbsp;&nbsp;-->
                文章状态：<select name="status" size="1" style="width:80px;font-size:9pt">
                <option value="1" <%=(status==1)?"selected":""%>>使用</option>
                <option value="2" <%=(status==2)?"selected":""%>>归档</option>
                <option value="6" <%=(status==6)?"selected":""%>>活动</option>
                <option value="0" <%=(status==0)?"selected":""%>>未用</option>
            </select>
                <%} else {%>
                商品状态：<select name="status" size="1" style="width:80px;font-size:9pt">
                <option value="1" <%=(status==1)?"selected":""%>>正常</option>
                <option value="4" <%=(status==4)?"selected":""%>>促销</option>
                <option value="5" <%=(status==5)?"selected":""%>>新品</option>
                <option value="6" <%=(status==6)?"selected":""%>>活动</option>
                <option value="0" <%=(status==0)?"selected":""%>>下架</option>
                <option value="2" <%=(status==2)?"selected":""%>>归档</option>
            </select>
                <%}%>
            </td>
        </tr>
        <tr>
            <td class=line color="#eeeeee">
                <%=(isProduct==1)?"商品摘要":"摘要"%>
                <textarea cols="120" rows="3" id="summaryID" name="summary"><%=(summary!=null)?summary:""%></textarea>

                <!--a href=javascript:AddPubColumns();><b>发布栏目</b></a><b>：</b>
                <select size=3 name="pubcolumns" id="pcolumnsID" style="width:150px;" multiple>
                    <!%
                        Refers refer = new Refers();
                        for (int i = 0; i < pubcolumns.size(); i++) {
                            refer = (Refers) pubcolumns.get(i);
                            String value = "c" + refer.getColumnid() + "-" + refer.getColumnname();
                            out.println("<option value='" + value + "'>" + StringUtil.gb2iso4View(refer.getColumnname()) + "</option>");
                        }
                    %>
                </select-->

                <a href=javascript:recommendArticle();>推荐</a>:
                <select size=3 name="recommend" id="recommendID" style="width:150px;" multiple>
                    <%
                        relatedArticle relatedArticle = null;
                        for (int i = 0; i < recommends.size(); i++) {
                            relatedArticle = (relatedArticle)recommends.get(i);
                            String value = "a" + relatedArticle.getJointedID() + "-" + relatedArticle.getChineseName();
                            out.println("<option value='" + value + "'>" + StringUtil.gb2iso4View(relatedArticle.getChineseName()) + "</option>");
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
                厂商：<input class=tine name=brand size=20
                          value="<%=(article.getBrand()==null)?"":StringUtil.replace(StringUtil.gb2iso4View(article.getBrand()),"\"","&quot;")%>"><br />
                购买方式：<input class=tine name=buyway size=20>
                重量：<input class=tine name=weight size=5 value="<%=article.getProductWeight()%>">
                单位：<input class=tine name=uint size=5>
            </td>
        </tr>
        <%}%>
        <%if (isDefine == 1) {%>
        <%if ((isProduct == 0 && isPosition==0) || (isProduct == 1 && isPosition==1)) {%>
        <tr bgcolor="#eeeeee">
                <%} else { %>
        <tr bgcolor="#ffffff">
            <%}%>
            <td class=line><%=extendMgr.getExtendAttrForArticle(company,username,columnID, articleID)%>
            </td>
        </tr>
        <%}%>
        <tr>
            <td class=line>
                发布日期：<input type=text  class="easyui-datetimebox" name="datetime" value="<%=s_publishtime%>" style="width:200px">
                排序：<input class=tine name=sortid size=4 value="<%=sortid%>">
                主权重：<input class=tine name=docLevel id="docLevel" size=4
                           onDblClick="AddRelatedArticleID(0);" value="<%=docLevel%>"
                           maxlength="6" title="双击鼠标左键即可选择某篇文章ID作为本篇文章的权重">
                次权重：<input class=tine name=viceDocLevel id="viceDocLevel" size=4
                           onDblClick="AddRelatedArticleID(1);" value="<%=viceLevel%>"
                           maxlength="6" title="双击鼠标左键即可选择某篇文章ID作为本篇文章的次权重">
                <%=(isProduct==1)?"主商品":"父文章"%>：
                <input type="hidden" name="relatedID" id="relatedID" size="4" value=<%=(relatedID==null)?"":relatedID%>>
                <input name="parentArticleTitle" id="parentArticleTitleID" size="10" onDblClick="AddRelatedArticleID(2);"
                       value="<%=(relatedID==null)?"":relatedID%>" title="双击鼠标左键即可弹出父文章选择页面">
                <!--订阅：<input type=radio <%=(subscriber==1)?"checked":""%> name="subscriber"
                          value="1">是<input type=radio <%=(subscriber==0)?"checked":""%>
                                            name="subscriber" value="0">否-->
                <!--(<a href="javascript:fileUpload();">文件上传</a>)-->
                自定义URL<input type=radio checked name="url" value="0" onclick="useMyUrl(0);" <%if(urltype==0){%>checked<%}%>>否
                <input type=radio name="url" value="1" onclick="useMyUrl(1);" <%if(urltype==1){%>checked<%}%>>是
                <%=(isProduct==1)?"商品URL":"文章URL"%><input type=text class=tine name=aurl size=31 <%if(urltype==0){%>disabled<%}%>
                                                          value="<%=otherurl==null?"":otherurl%>">
            </td>
        </tr>
        <tr>
            <td ID=bottomofFld></td>
        </tr>
        <tr bordercolor="#CCCCCC" bgcolor="#CCCCCC">
            <td>
                特效图片(<a href="javascript:upload_attrpic_onclick('apic')">图</a>)<input
                    class=tine name="articlepic" id="articlepic" size=10 onDblClick="history('apic');" title="双击鼠标左键即可选择历史图片"
                    value="<%=(articlepic != null) ? articlepic : ""%>">&nbsp;&nbsp;

                小图片(<a href="javascript:upload_attrpic_onclick('pic')">图</a>)：<input class=tine name=pic id="pic" size=12
                                                                                     value="<%=(article.getProductPic()==null)?"":StringUtil.gb2iso4View(article.getProductPic())%>"
                                                                                     onDblClick="history('pic');"
                                                                                     title="双击鼠标左键即可选择历史图片">
                大图片(<a href="javascript:upload_attrpic_onclick('bigpic')">图</a>)：<input class=tine name=bigpic id="bigpic"
                                                                                        size=12
                                                                                        value="<%=(article.getProductBigPic()==null)?"":StringUtil.gb2iso4View(article.getProductBigPic())%>"
                                                                                        onDblClick="history('bigpic');"
                                                                                        title="双击鼠标左键即可选择历史图片">

                视频文件(<a href="javascript:upload_media_onclick()">视频</a>)：<input class=tine name=media id="mediaid" value="<%=(article.getMediafile()==null)?"":StringUtil.gb2iso4View(article.getMediafile())%>" size=30>

                <!--input type=button name=articletype id=turnpic size=30 value="附图" onclick="javascript:upload_turn_pic();">&nbsp;&nbsp;
                <input type=button name=articletype id=attfiles size=30 value="附件" onclick="javascript:upload_attachments();"-->
            </td>
        </tr>
    </table>
    <table border="0" width="100%">
        <tr>
            <td>
            <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"><%=content%>
            </textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content',{ customConfig: 'configForArticle.js' });
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
