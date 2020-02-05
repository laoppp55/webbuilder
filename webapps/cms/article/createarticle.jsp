<%@ page import="java.sql.*,
                 java.io.*,
                 java.util.*,
                 org.jdom.*,
                 org.jdom.input.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="com.bizwink.cms.multimedia.Multimedia" %>
<%@ page import="com.bizwink.cms.multimedia.Attechment" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.bizwink.images.Uploadimage" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="com.bizwink.service.DeptService" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
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
    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    String sitename = authToken.getSitename();
    String company = authToken.getDepartment();
    sitename = StringUtil.replace(sitename,".","_");
    String maintitle = null, vicetitle = null, content = null, summary = null, keyword = null, source = null,attechments=null,articlepic = null, author = null,indexnum=null,brand = null,
            s_publishtime=null,pic = null, bigpic = null, relatedID = null, filename = null,mediafilename=null,mediafile=null, otherurl = null,beidate=null,markid="-1";
    int docLevel = 0, viceLevel = 0, sortid = 0, status = 0, subscriber = 0, stocknum = 0, modelID = 0, urltype = 0,score=0,voucher=0,changepic=0;
    float saleprice = 0f, inprice = 0f, marketprice = 0f,istype=0f,vipprice=0f,weight = 0f;
    int notearticleid = 0,articleid = 0;

    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int currentPage = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    int ascdesc = ParamUtil.getIntParameter(request, "ascdesc", 0);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int arttypeval = ParamUtil.getIntParameter(request, "arttype", 0);
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));

    Calendar today = Calendar.getInstance();
    today.setTimeInMillis(System.currentTimeMillis());
    SimpleDateFormat dtformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp create_date = new Timestamp(System.currentTimeMillis());

    //获得栏目信息
    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String uploadPath = application.getRealPath("/") + "sites" + java.io.File.separator + sitename + column.getDirName() + "images" + java.io.File.separator;
    int isDefine = column.getDefineAttr();
    int isProduct = column.getIsProduct();
    int isAudited = column.getIsAudited();
    int isPosition = column.getIsPosition();                                   //是否包含地理位置信息
    markid=ParamUtil.getParameter(request,"markid");
    List mmfiles = new ArrayList();                       //内容中包含的多媒体文件列表
    List pubcolumns = new ArrayList();                    //定义信息发布的目的栏目
    List recommends = new ArrayList();                    //设置文章被推荐的位置
    String figures = null;                                //文章的附属图片信息，一个json字符串
    if (doCreate) {
        maintitle = filter.excludeHTMLCode(ParamUtil.getParameter(request, "maintitle"));
        maintitle = (maintitle != null) ? maintitle.trim() : maintitle;
        vicetitle = filter.excludeHTMLCode(ParamUtil.getParameter(request, "vicetitle"));
        content = ParamUtil.getParameter(request, "content");
        author = filter.excludeHTMLCode(ParamUtil.getParameter(request, "author"));
        summary = filter.excludeHTMLCode(ParamUtil.getParameter(request, "summary"));
        keyword = filter.excludeHTMLCode(ParamUtil.getParameter(request, "keyword"));
        source = filter.excludeHTMLCode(ParamUtil.getParameter(request, "source"));
        indexnum = ParamUtil.getParameter(request, "indexnum");
        relatedID = filter.excludeHTMLCode(ParamUtil.getParameter(request, "relatedID"));
        docLevel = ParamUtil.getIntParameter(request, "docLevel", 0);
        viceLevel = ParamUtil.getIntParameter(request, "viceDocLevel", 0);
        subscriber = ParamUtil.getIntParameter(request, "subscriber", 1);
        sortid = ParamUtil.getIntParameter(request, "sortid", 0);
        filename = ParamUtil.getParameter(request, "filename");
        modelID = ParamUtil.getIntParameter(request, "modelID", 0);
        status = ParamUtil.getIntParameter(request, "status", 1);
        figures = ParamUtil.getParameter(request, "picinfos");
        attechments = ParamUtil.getParameter(request, "attechments");
        notearticleid = ParamUtil.getIntParameter(request, "notesArticle", 1);
        mediafile = ParamUtil.getParameter(request, "media");                      //从视频对话框上传的视频文件
        mediafilename = ParamUtil.getParameter(request, "mediafilename");        //从工具棒点击上传视频文件按钮上传的视频文件
        if (mediafilename != null) {
            mediafilename = mediafilename.substring(0,mediafilename.length()-1);
            if (!mediafilename.endsWith(".flv")) status = 3;              //如果新创建的文章包含多媒体文件，并且对媒体文件扩展名不为flv,设置文件的状态为3，表示文章处于等待状态，由多媒体转换进程修改该状态
            Pattern p = Pattern.compile("\\|", Pattern.CASE_INSENSITIVE);
            String mmf[] = p.split(mediafilename);
            Multimedia mult = null;
            for(int i=0;i<mmf.length;i++) {
                mult = new Multimedia();
                mult.setSiteid(siteid);
                mult.setDirname(column.getDirName());
                mult.setFilepath(uploadPath);
                int posi = mmf[i].indexOf(":");
                mult.setOldfilename(mmf[i].substring(posi + 1).trim());
                mult.setNewfilename(mmf[i].substring(0,posi).trim());
                mult.setEncodeflag(0);
                mmfiles.add(mult);
            }
        }
        recommends = ParamUtil.getSelectParameterAllNamesAndValues(request, "recommend");
        pubcolumns = ParamUtil.getSelectParameterAllNamesAndValues(request, "pubcolumns");
        articlepic = ParamUtil.getParameter(request, "articlepic");
        urltype = ParamUtil.getIntParameter(request, "url", 0);
        otherurl = ParamUtil.getParameter(request, "aurl");
        beidate=ParamUtil.getParameter(request,"beidate");
        s_publishtime = ParamUtil.getParameter(request,"datetime");
        changepic = ParamUtil.getIntParameter(request,"changepic",0);
        pic = ParamUtil.getParameter(request, "pic");
        bigpic = ParamUtil.getParameter(request, "bigpic");

        if (isProduct == 1) {
            brand = ParamUtil.getParameter(request, "brand");
            weight = ParamUtil.getFloatParameter(request, "weight", 0);
            stocknum = ParamUtil.getIntParameter(request, "stocknum", 0);
            saleprice = ParamUtil.getFloatParameter(request, "saleprice", 0);
            inprice = ParamUtil.getFloatParameter(request, "inprice", 0);
            marketprice = ParamUtil.getFloatParameter(request, "marketprice", 0);
            vipprice = ParamUtil.getFloatParameter(request, "vipprice", 0);
            score = ParamUtil.getIntParameter(request, "score", 0);
            voucher = ParamUtil.getIntParameter(request, "voucher", 0);
        }

        if (content != null) {
            content = StringUtil.replace(content, "cmstextarea", "textarea").trim();
        }
        if (maintitle == null) errorTitle = true;
        if (content == null) errorContent = true;
        errors = errorTitle || errorContent;
    }

    IUserManager userManager = UserPeer.getInstance();
    source = userManager.getUserDepartmentName(username);
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    Article article = new Article();
    if (!errors && doCreate) {
        try {
            article.setSiteID(siteid);
            article.setDeptid(String.valueOf(authToken.getDeptid()));
            article.setColumnID(columnID);
            article.setMainTitle(maintitle);
            article.setViceTitle(vicetitle);
            article.setContent(content);
            if(content.indexOf("/_commons/FLVPlayer_Progressive.swf")>0)
                article.setMultimediatype(1);
            else
                article.setMultimediatype(0);
            article.setMediafile(mediafile);
            article.setAuthor(author);
            article.setSummary(summary);
            article.setKeyword(keyword);
            article.setSortID(sortid);
            article.setRelatedArtID(relatedID);
            article.setPubFlag(1);
            article.setSource(source);
            article.setEditor(username);
            article.setCreator(username);
            create_date = new Timestamp(System.currentTimeMillis());
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
            article.setNotes(notearticleid);
            article.setIndexnum(indexnum);
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
            if (s_publishtime == null)
                article.setPublishTime(new Timestamp(System.currentTimeMillis()));
            else {
                Timestamp publishtime = new Timestamp(dtformat.parse(s_publishtime).getTime());
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

                        if (value != null && value.trim().length() > 0) {
                            ExtendAttr extend = new ExtendAttr();
                            int dataType = Integer.parseInt(e.getAttributeValue("datatype"));
                            int controltype = Integer.parseInt(e.getAttributeValue("type"));

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
            }

            List<Attechment> attechmentList=null;
            if (attechments!=null && attechments!="") {
                String json_attechments = "[" +  attechments + "]";
                JSONArray json = JSONArray.fromObject(json_attechments);                   //json_attechments是json字符串
                attechmentList= (List<Attechment>) JSONArray.toCollection(json, Attechment.class);
            }

            //文章轮换图片
            List<Uploadimage> uploadimages = null;
            if (figures!=null && figures!="") {
                JSONObject jsonObject = JSONObject.fromObject(figures.substring(1,figures.length()-1));
                JSONArray json = JSONArray.fromObject(figures);
                uploadimages = (List<Uploadimage>)JSONArray.toCollection(json,Uploadimage.class);
            }
            //attechments   附件列表
            //pubcolumns    文章需要发布的栏目列表
            //recommends    文章被推荐的标记位置
            //auditlist     录入的文章审核信息列表
            List auditlist = (List)session.getAttribute("audit_article");
            articleid = extendMgr.create(attrList,attechmentList,article,pubcolumns,recommends,uploadimages,auditlist,mmfiles);

            session.removeAttribute("turn_pic");
            session.removeAttribute("articletypeids");
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            errors = true;
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>创建文章</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link REL="stylesheet" TYPE="text/css" HREF="../style/editor.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/themes/icon.css">

    <script language="JavaScript" src="../js/setday.js"></script>
    <script language="JavaScript" src="../js/cities.js"></script>
    <script type="text/javascript" src="../toolbars/btnclick.js"></script>
    <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script language=javascript>
        $(document).ready(function() {
            var success = <%=success%>;
            if (success == true) {
                var articleid = "<%=articleid%>";
                var maintitle = "<%=maintitle%>";
                var publishtime = "<%=dtformat.format(create_date)%>";
                var lastupdate = "<%=dtformat.format(create_date)%>";
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
                //columnid,currentPage,doSearchFlag,flag,ascdesc,auditflag,pubflag,mediatype,arttypeval
                //window.opener.createArticleListData(columnid,articleid,maintitle,publishtime, lastupdate,doclevel,vicedoclevel,editor,range,currentPage,doSearchFlag,flag,ascdesc);
                //alert(columnid + "==" + currentPage + "==" + doSearchFlag + "==" + flag + "==" + ascdesc  + "===" + arttypeval);
                window.opener.articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc,null,null,null,arttypeval);
                alert("文章创建成功！！！");
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

        //双击选择文章分类
        function addarticletype() {
            window.open("selectarticletype.jsp?column=<%=columnID%>", "", "top=100,left=100,width=420,height=360");
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

        //向其他栏目发布文章
        function AddPubColumns() {
            var winStr = "addPubColumnsnew.jsp";
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            var wins = window.open(winStr, "", "width=" + iWidth + ",height=" + iHeight);
        }

        //选择文章模板
        function selectArticleModel() {
            var winStr = "selectModel.jsp?column=<%=columnID%>";
            var wins = window.open(winStr, "", "width=800,height=600");
            wins.focus();
        }

        //选择文章的ID作为权重  jj
        function AddRelatedArticleID(para) {
            var winStr = "addRelatedArticle.jsp?from=1&param=" + para+"&column=<%=columnID%>";
            //from=1 文章录入调用相关文章
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            window.open(winStr, "width=" + iWidth +",height=" + iHeight +",left=" + iLeft + ",top=" + iTop + ",status,scrollbars");
            wins.focus();
        }

        function create(createForm) {
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

            //获取文章的索引号
            htmlobj=$.ajax({
                url:"generateIndexNumOfBjsjs.jsp?time=" + <%=System.currentTimeMillis()%>,
                data:{
                    column:<%=columnID%>
                },
                dataType:'json',
                async:false,
                success:function(jsondata){
                    if (jsondata.DATA != null) {
                        createForm.indexnum.value = jsondata.DATA;
                    }
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

            //for (var i = 0; i < createForm.pcolumnsID.length; i++) {
            //    createForm.pcolumnsID[i].selected = true;
            //}

            createForm.submit();
        }

        function checkFloat(str) {
            var retstr = false;
            var regstr = /[^0-9.]/gi;
            if (regstr.exec(str) == null)
            {
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

        //双击选择历史图片
        function history(attrname) {
            window.open("../upload/historypic.jsp?column=<%=columnID%>&pictype=1&attr=" + attrname, "", "left=100,top=100,width=800,height=600");
        }

        function cal() {
            top.close();
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

        function useMyUrl(f) {
            if (f == 1)
                createForm.aurl.disabled = 0;
            else
                createForm.aurl.disabled = 1;
        }

        function upload_turn_pic(){
            window.open("../upload/upload_turn_pic.jsp?column=<%=columnID%>", "", 'width=900,height=600,left=20,top=20,status,scrollbars');
        }
        function upload_batcbh_pic(){
            var retval = window.showModalDialog("../upload/picbatch.jsp?template_or_article="+ parent.window.document.createForm.template_or_article_flag.value, "", 'width=800,height=600,left=200,top=200,status,scrollbars');
            if (retval != "" && retval != undefined) {
                document.getElementById("batchpic").value=retval;
            }
        }
        function upload_media_onclick() {
            window.open("../upload/mmupload.jsp?column=<%=columnID%>&fromflag=1", "", 'width=600,height=400,left=200,top=200');
        }

        function fileUploadList(createForm) {
            window.open('fileUploadList.jsp?column=<%=columnID%>', '', 'left=300,top=200,width=400,height=200');
        }

        function article_audit(){
            window.open("article_audit.jsp", "", 'width=800,height=600,left=200,top=200,status,scrollbars');
        }
    </script>

</head>
<body>
<form action=createarticle.jsp method=post name=createForm>
    <input type=hidden name=doCreate value="true">
    <input type=hidden name=column value="<%=columnID%>">
    <input type=hidden name=range value="<%=range%>">
    <input type=hidden name=start value="<%=currentPage%>">
    <input type=hidden name=flag value="<%=flag%>">
    <input type=hidden name=ascdesc value="<%=ascdesc%>">
    <input type=hidden name=arttype value="<%=arttypeval%>">
    <input type=hidden name=searchFlag value="<%=doSearchFlag%>">
    <input type=hidden name=filename>
    <input type=hidden name=attechments value="">
    <input type=hidden name=picinfos value="">
    <input type=hidden name=modelSourceCodeFlag value=0>
    <input type=hidden name=template_or_article_flag value="1">
    <input type=hidden name=mediafilename value="">
    <input type=hidden name=markid id="markid" value="-1">
    <input type=hidden name=msword id="mswordid" value="">

    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr height=40>
            <td width="25%">&nbsp;<%=StringUtil.gb2iso4View(column.getCName())%>&nbsp;>&nbsp;创建文章</td>
            <td width="50%"><%
                if (!success && errors) {
                    out.println("<span class=cur>创建文章失败，请重新再试。</span>");
                }
            %></td>
            <td width="25%" align=right>
                <!--input class=tine type=button value="  附件上传  " onclick="fileUploadList(createForm);">&nbsp;&nbsp;-->
                <input class=tine type=button value="  保存  " onclick="create(createForm);">&nbsp;&nbsp;
                <input class=tine type=button value="  取消  " onclick="cal();">&nbsp;&nbsp;
            </td>
        </tr>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr>
            <td colspan=3 height=4></td>
        </tr>
    </table>

    <table border=0 borderColorDark="#ffffe0c" borderColorLight="#5e5e00" cellPadding=0 cellSpacing=0 width="100%">
        <tr bgcolor="#ffffff">
            <td class=line>
                <%=(isProduct==1)?"商品名称：":"标题："%>(<a href="javascript:upload_attrpic_onclick('mt');">图</a>)<input class=tine name=maintitle id="maintitle"
                                                                                                                size=50 onDblClick="history('mt');"
                                                                                                                title="双击鼠标左键即可选择历史图片">
                <%=(isProduct==1)?"商品简称：":"副标题："%>(<a href="javascript:upload_attrpic_onclick('vt')">图</a>)<input class=tine name=vicetitle
                                                                                                                id="vicetitle" size=40
                                                                                                                onDblClick="history('vt');"
                                                                                                                title="双击鼠标左键即可选择历史图片">
                作者：(<a href="javascript:upload_attrpic_onclick('au')">图</a>)<input class=tine name=author id="author"
                                                                                  size=25 onDblClick="history('au');"
                                                                                  title="双击鼠标左键即可选择历史图片">
                来源：(<a href="javascript:upload_attrpic_onclick('sr')">图</a>)<input class=tine name=source id="source" value="<%=(source!=null)?source:""%>" readonly="readonly"
                                                                                  size=25 onDblClick="history('sr');"
                                                                                  title="双击鼠标左键即可选择历史图片">
            </td>
        </tr>
        <tr bgcolor="#eeeeee">
            <td>
                关键字(;)<input class=tine name=keyword size=40>
                索引号<input class=tine name=indexnum   size=40 value="" readonly>
                <%=(isProduct==1)?"显示模板：":"文章模板："%><input class=tine name=modelID id="modelID" size=20 value=0 title="双击鼠标左键即可弹出模板选择页面" onDblClick="selectArticleModel();">
                <%if (isProduct == 0) {%>
                <!--font color="#FF0000"><b>使用</b></font><input type=radio checked name="status" value="1">是<input
                    type=radio name="status" value="0">否<input type=radio name="status" value="2">归档&nbsp;&nbsp;-->
                <%=(isProduct==1)?"商品状态：":"文章状态："%>：<select name="status" size="1" style="width:80px;font-size:9pt">
                <option value="1" selected>使用</option>
                <option value="2" >归档</option>
                <option value="6" >活动</option>
                <option value="0">未用</option>
            </select>
                <%} else {%>
                <%=(isProduct==1)?"商品状态：":"文章状态："%>：<select name="status" size="1" style="width:80px;font-size:9pt">
                <option value="1" selected>正常</option>
                <option value="4" >促销</option>
                <option value="5" >新品</option>
                <option value="6" >活动</option>
                <option value="2" >归档</option>
                <option value="0">下架</option>
            </select>
                <%}%>
            </td>
        </tr>
        <tr bgcolor="#ffffff">
            <td class=line>
                <%=(isProduct==1)?"商品摘要":"摘要"%><!--input class=tine name=summary size=80-->
                <textarea cols="120" rows="3" id="summaryID" name="summary"></textarea>
                <!--a href=javascript:AddPubColumns();>发布栏目</a>：
                <select size=3 name="pubcolumns" id="pcolumnsID" style="width:150px;" multiple></select-->
                <a href=javascript:recommendArticle();>推荐</a>:
                <select size=3 name="recommend" id="recommendID" style="width:150px;" multiple></select>
            </td>
        </tr>
        <%if (isProduct == 1) {%>
        <tr bgcolor="#eeeeee">
            <td class=line>
                售价：<input class=tine name=saleprice size=6>&nbsp;&nbsp;
                VIP价：<input class=tine name=vipprice size=6>&nbsp;&nbsp;
                进价：<input class=tine name=inprice size=6>&nbsp;&nbsp;
                市场价：<input class=tine name=marketprice size=6>&nbsp;&nbsp;
                积分：<input class=tine name=score size=6>&nbsp;&nbsp;
                购物卷：<input class=tine name=voucher size=6>&nbsp;&nbsp;
                库存：<input class=tine name=stocknum size=6>&nbsp;&nbsp;
                厂商：<input class=tine name=brand size=20>&nbsp;&nbsp;
                购买方式：<input class=tine name=buyway size=20><br/>&nbsp;&nbsp;
                重量：<input class=tine name=weight size=5>&nbsp;&nbsp;
                单位：<input class=tine name=uint size=5>&nbsp;&nbsp;
            </td>
        </tr>
        <%}%>
        <%if (isDefine == 1) {%>
        <%if ((isProduct == 0 && isPosition==0) || (isProduct == 1 && isPosition==1)) {%>
        <tr bgcolor="#eeeeee">
                <%} else { %>
        <tr bgcolor="#ffffff">
            <%}%>
            <td class=line><%=extendMgr.getExtendAttrForArticle(company,username,columnID, 0)%>
            </td>
        </tr>
        <%}%>
        <tr bgcolor="#eeeeee">
            <td class=line>
                发布日期：<input type=text  class="easyui-datetimebox" name="datetime" value="<%=dtformat.format(today.getTime())%>" style="width:200px">
                排序：<input class=tine name="sortid" size=4 value=0>&nbsp;&nbsp;
                主权重：<input class=tine id="docLevel" name="docLevel" size=4 value="0"
                           maxlength="6" title="双击鼠标左键即可选择某篇文章ID作为本篇文章的权重">&nbsp;&nbsp;
                次权重：<input class=tine id="viceDocLevel" name="viceDocLevel" size=4
                           value="0" maxlength="6" title="双击鼠标左键即可选择某篇文章ID作为本篇文章的次权重">&nbsp;&nbsp;
                <%=(isProduct==1)?"主商品":"父文章"%>：
                <input name="relatedID" size="10" value="" id="relatedID" onDblClick="AddRelatedArticleID(2);" title="双击鼠标左键即可弹出父文章选择页面">&nbsp;&nbsp;
                <!--订阅：<input type=radio checked name="subscriber" value="1">是<input
                    type=radio name="subscriber" value="0">否&nbsp;&nbsp;-->
                自定义URL<input type=radio checked name="url" value="0" onclick="useMyUrl(0);">否
                <input type=radio name="url"  value="1" onclick="useMyUrl(1);">是
                <%=(isProduct==1)?"商品URL":"文章URL"%><input type=text class=tine name=aurl size=31 disabled>&nbsp;&nbsp;
            </td>
        </tr>
        <tr bordercolor="#eeeeee" bgcolor="#ffffff">
            <td>
                特效图片(<a href="javascript:upload_attrpic_onclick('apic')">图</a>)<input
                    class=tine name="articlepic" id="articlepic" size=20 onDblClick="history('apic');"
                    title="双击鼠标左键即可选择历史图片">&nbsp;&nbsp;

                小图片(<a href="javascript:upload_attrpic_onclick('pic')">图</a>)：<input class=tine name=pic id="pic" size=12
                                                                                     onDblClick="history('pic');"
                                                                                     title="双击鼠标左键即可选择历史图片">&nbsp;&nbsp;

                大图片(<a href="javascript:upload_attrpic_onclick('bigpic')">图</a>)：<input class=tine name=bigpic id="bigpic"
                                                                                        size=12 onDblClick="history('bigpic');"
                                                                                        title="双击鼠标左键即可选择历史图片">&nbsp;&nbsp;

                视频文件(<a href="javascript:upload_media_onclick()">视频</a>)：<input class=tine name=media id="mediaid" value="" size=30>&nbsp;&nbsp;

                <!--input type=button name=articletype id=turnpic size=30 value="附图" onclick="javascript:upload_turn_pic();">&nbsp;&nbsp;
                <input type=button name=articletype id=attfiles size=30 value="附件" onclick="javascript:upload_attachments();"-->
            </td>
        </tr>
    </table>
    <table border="0" width="100%">
        <tr>
            <td>
                <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"></textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content',{ customConfig: 'configForArticle.js' });
                    //CKEDITOR.replace('content');
                </script>
            </td>
        </tr>
    </table>
</form>
<%
    if (errors) {
        out.println("<script lanagage='javascript'>");
        if (maintitle == null)
            out.println("createForm.maintitle.focus();");
        out.println("</script>");
    }
%>
</body>
</html>