<%@ page import="com.bizwink.cms.util.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.xml.XMLProperties" contentType="text/html;charset=GBK"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp?url=member/editGroup.jsp");
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    IRegisterManager regMgr = RegisterPeer.getInstance();
    IColumnManager columnManager = ColumnPeer.getInstance();

    boolean error = false;
    String errormsg = "";
    String extname;
    int pic;
    int tcflag;
    int wapflag;
    int pubflag;
    int bindflag;
    int tagSite = 0;
    int listShow = 0;
    int beRefered;
    int copyColumn;
    int beCopyColumn;
    int pushArticle;
    int moveArticle;
    int cssjsdir = 0;
    int errcode = 0;

    String titlepic = "";
    String vtitlepic = "";
    String sourcepic = "";
    String authorpic = "";
    String contentpic = "";
    String specialpic = "";
    String productpic = "";
    String productsmallpic = "";
    String mediasize = "";
    String mediapicsize= "";

    String ts_pic = null,ts_pic_h = null,ts_pic_w = null;
    String s_pic = null,s_pic_h = null,s_pic_w = null;
    String ms_pic = null,ms_pic_h = null,ms_pic_w = null;
    String m_pic = null,m_pic_h = null,m_pic_w = null;
    String ml_pic = null,ml_pic_h = null,ml_pic_w = null;
    String l_pic = null,l_pic_h = null,l_pic_w = null;
    String tl_pic = null,tl_pic_h = null,tl_pic_w = null;

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int doSearchFlag = ParamUtil.getIntParameter(request, "searchflag", 0);
    int siteID = ParamUtil.getIntParameter(request, "siteid", 0);

    if (doUpdate) {
        extname = ParamUtil.getParameter(request, "extname");
        pic = ParamUtil.getIntParameter(request, "pic", 0);
        tcflag = ParamUtil.getIntParameter(request, "tcflag", 0);
        wapflag = ParamUtil.getIntParameter(request, "wapflag", 0);
        pubflag = ParamUtil.getIntParameter(request, "pubflag", 0);
        bindflag = ParamUtil.getIntParameter(request, "bindflag", 0);
        tagSite = ParamUtil.getIntParameter(request, "tagSite", 0);
        listShow = ParamUtil.getIntParameter(request, "listShow", 0);
        beRefered = ParamUtil.getIntParameter(request, "beRefered", 0);
        copyColumn = ParamUtil.getIntParameter(request, "copyColumn", 0);
        beCopyColumn = ParamUtil.getIntParameter(request, "beCopyColumn", 0);
        pushArticle = ParamUtil.getIntParameter(request, "pushArticle", 0);
        moveArticle = ParamUtil.getIntParameter(request, "moveArticle", 0);
        cssjsdir =  ParamUtil.getIntParameter(request, "cssjsdir", 0);

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
        if(ParamUtil.getParameter(request,"mediaheight") != null && ParamUtil.getParameter(request,"mediawidth") !=null){
            mediasize = ParamUtil.getParameter(request,"mediaheight") + "X" + ParamUtil.getParameter(request,"mediawidth");
        }
        if(ParamUtil.getParameter(request,"mediapicheight") != null && ParamUtil.getParameter(request,"mediapicwidth") !=null){
            mediapicsize = ParamUtil.getParameter(request,"mediapicheight") + "X" + ParamUtil.getParameter(request,"mediapicwidth");
        }

        ts_pic_h = ParamUtil.getParameter(request,"ts_pic_h");
        ts_pic_w = ParamUtil.getParameter(request,"ts_pic_w");
        if (ts_pic_h!= null && ts_pic_w != null) ts_pic = ts_pic_h + "X" + ts_pic_w;
        s_pic_h = ParamUtil.getParameter(request,"s_pic_h");
        s_pic_w = ParamUtil.getParameter(request,"s_pic_w");
        if (s_pic_h!= null && s_pic_w != null) s_pic = s_pic_h + "X" + s_pic_w;
        ms_pic_h = ParamUtil.getParameter(request,"ms_pic_h");
        ms_pic_w = ParamUtil.getParameter(request,"ms_pic_w");
        if (ms_pic_h!= null && ms_pic_w != null) ms_pic = ms_pic_h + "X" + ms_pic_w;
        m_pic_h = ParamUtil.getParameter(request,"m_pic_h");
        m_pic_w = ParamUtil.getParameter(request,"m_pic_w");
        if (m_pic_h!= null && m_pic_w != null) m_pic = m_pic_h + "X" + m_pic_w;
        ml_pic_h = ParamUtil.getParameter(request,"ml_pic_h");
        ml_pic_w = ParamUtil.getParameter(request,"ml_pic_w");
        if (ml_pic_h!= null && ml_pic_w != null) ml_pic = ml_pic_h + "X" + ml_pic_w;
        l_pic_h = ParamUtil.getParameter(request,"l_pic_h");
        l_pic_w = ParamUtil.getParameter(request,"l_pic_w");
        if (l_pic_h!= null && l_pic_w != null) l_pic = l_pic_h + "X" + l_pic_w;
        tl_pic_h = ParamUtil.getParameter(request,"tl_pic_h");
        tl_pic_w = ParamUtil.getParameter(request,"tl_pic_w");
        if (tl_pic_h!= null && tl_pic_w != null) tl_pic = tl_pic_h + "X" + tl_pic_w;

        StringBuffer configInfo = new StringBuffer();
        configInfo.append("<Config>");
        configInfo.append("<GlobalTag>" + tagSite + "</GlobalTag>");
        configInfo.append("<PublishListShow>" + listShow + "</PublishListShow>");
        configInfo.append("</Config>");

        try {
            Register register = new Register();
            register.setSiteID(siteID);
            register.setImagesDir(pic);
            register.setTCFlag(tcflag);
            register.setWapFlag(wapflag);
            register.setPubFlag(pubflag);
            register.setBindFlag(bindflag);
            register.setExtName(extname);
            register.setCssjsDir(cssjsdir);
            register.setBeRefered(beRefered);
            register.setCopyColumn(copyColumn);
            register.setBeCopyColumn(beCopyColumn);
            register.setPushArticle(pushArticle);
            register.setMoveArticle(moveArticle);
            register.setConfigInfo(configInfo.toString());
            register.setTitlepic(titlepic);
            register.setVtitlepic(vtitlepic);
            register.setSourcepic(sourcepic);
            register.setAuthorpic(authorpic);
            register.setContentpic(contentpic);
            register.setSpecialpic(specialpic);
            register.setProductpic(productpic);
            register.setProductsmallpic(productsmallpic);
            register.setMediasize(mediasize);
            register.setMediapicsize(mediapicsize);
            register.setTs_pic(ts_pic);
            register.setS_pic(s_pic);
            register.setMs_pic(ms_pic);
            register.setM_pic(m_pic);
            register.setMl_pic(ml_pic);
            register.setL_pic(l_pic);
            register.setTl_pic(tl_pic);
            errcode = regMgr.update(register);
        } catch (Exception e) {
            e.printStackTrace();
            errcode = -1;
            error = true;
        }
    }

    //读出该站点信息
    Register register = regMgr.getSite(siteID);
    extname = columnManager.getIndexExtName(siteID);
    String configInfo = register.getConfigInfo();
    if (configInfo != null && configInfo.length() > 0) {
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + configInfo);
        tagSite = Integer.parseInt(properties.getProperty("GlobalTag"));
        listShow = Integer.parseInt(properties.getProperty("PublishListShow"));
    }
    copyColumn = register.getCopyColumn();
    beCopyColumn = register.getBeCopyColumn();
    pushArticle = register.getPushArticle();
    moveArticle = register.getMoveArticle();
    titlepic = register.getTitlepic();
    vtitlepic = register.getVtitlepic();
    sourcepic = register.getSourcepic();
    authorpic = register.getAuthorpic();
    contentpic = register.getContentpic();
    specialpic = register.getSpecialpic();
    productpic = register.getProductpic();
    productsmallpic = register.getProductsmallpic();
    mediasize = register.getMediasize();
    mediapicsize = register.getMediapicsize();
    ts_pic = register.getTs_pic();
    s_pic = register.getS_pic();
    ms_pic = register.getMs_pic();
    m_pic = register.getM_pic();
    ml_pic = register.getMl_pic();
    l_pic = register.getL_pic();
    tl_pic = register.getTl_pic();

    //判断高度和宽度的分隔符是什么字符
    String seperator = "";
    if (titlepic != null) {
        seperator = "*";
        int posi = titlepic.indexOf(seperator);
        if (posi == -1) {
            seperator = "x";
            posi = titlepic.indexOf(seperator);
            if (posi == -1) seperator = "X";
        }
    }

    String titlepic_h = (titlepic==null || titlepic=="")?"":titlepic.substring(0,titlepic.indexOf(seperator));
    String titlepic_w = (titlepic==null || titlepic=="")?"":titlepic.substring(titlepic.indexOf(seperator) + 1);
    String vtitlepic_h = (vtitlepic==null || vtitlepic=="")?"":vtitlepic.substring(0,vtitlepic.indexOf(seperator));
    String vtitlepic_w = (vtitlepic==null || vtitlepic=="")?"":vtitlepic.substring(vtitlepic.indexOf(seperator) + 1);
    String sourcepic_h = (sourcepic==null || sourcepic=="")?"":sourcepic.substring(0,sourcepic.indexOf(seperator));
    String sourcepic_w = (sourcepic==null || sourcepic=="")?"":sourcepic.substring(sourcepic.indexOf(seperator) + 1);
    String authorpic_h = (authorpic==null || authorpic=="")?"":authorpic.substring(0,authorpic.indexOf(seperator));
    String authorpic_w = (authorpic==null || authorpic=="")?"":authorpic.substring(authorpic.indexOf(seperator) + 1);
    String contentpic_h = (contentpic==null || contentpic=="")?"":contentpic.substring(0,contentpic.indexOf(seperator));
    String contentpic_w = (contentpic==null || contentpic=="")?"":contentpic.substring(contentpic.indexOf(seperator) + 1);
    String specialpic_h = (specialpic==null || specialpic=="")?"":specialpic.substring(0,specialpic.indexOf(seperator));
    String specialpic_w = (specialpic==null || specialpic=="")?"":specialpic.substring(specialpic.indexOf(seperator) + 1);
    String productpic_h = (productpic==null || productpic=="")?"":productpic.substring(0,productpic.indexOf(seperator));
    String productpic_w = (productpic==null || productpic=="")?"":productpic.substring(productpic.indexOf(seperator) + 1);
    String productsmallpic_h = (productsmallpic==null || productsmallpic=="")?"":productsmallpic.substring(0,productsmallpic.indexOf(seperator));
    String productsmallpic_w = (productsmallpic==null || productsmallpic=="")?"":productsmallpic.substring(productsmallpic.indexOf(seperator) + 1);
    String mediasize_h = (mediasize==null || mediasize=="")?"":mediasize.substring(0,mediasize.indexOf(seperator));
    String mediasize_w = (mediasize==null || mediasize=="")?"":mediasize.substring(mediasize.indexOf(seperator) + 1);
    String mediapicsize_h = (mediapicsize==null || mediapicsize=="")?"":mediapicsize.substring(0,mediapicsize.indexOf(seperator));
    String mediapicsize_w = (mediapicsize==null || mediapicsize=="")?"":mediapicsize.substring(mediapicsize.indexOf(seperator) + 1);

%>

<html>
<head>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <title>站点修改</title>
    <script src="../js/jquery-1.12.4.js" type="text/javascript"></script>
    <script language="javascript">
        $(document).ready(function(){
            var errcode=<%=errcode%>;
            if (errcode === 1) {
                alert("站点修改成功！");
                window.opener.userInfoList(<%=range%>,<%=currentpage%>,<%=doSearchFlag%>);
                window.close();
            } else if (errcode==-1) {
                alert("站点修改失败！");
                window.close();
            }
        });

        function check() {
            if(document.RegForm.titlepicheight.value != ""){
                var titlepicheight =  document.RegForm.titlepicheight.value;
                if( !IsNum(titlepicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.titlepicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.titlepicwidth.value != ""){
                var titlepicwidth =  document.RegForm.titlepicwidth.value;
                if( !IsNum(titlepicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.titlepicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.vtitlepicheight.value != ""){
                var vtitlepicheight =  document.RegForm.vtitlepicheight.value;
                if( !IsNum(vtitlepicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.vtitlepicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.vtitlepicwidth.value != ""){
                var vtitlepicwidth =  document.RegForm.vtitlepicwidth.value;
                if( !IsNum(vtitlepicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.vtitlepicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.sourcepicheight.value != ""){
                var sourcepicheight =  document.RegForm.sourcepicheight.value;
                if( !IsNum(sourcepicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.sourcepicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.sourcepicwidth.value != ""){
                var sourcepicwidth =  document.RegForm.sourcepicwidth.value;
                if( !IsNum(sourcepicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.sourcepicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.authorpicheight.value != ""){
                var authorpicheight =  document.RegForm.authorpicheight.value;
                if( !IsNum(authorpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.authorpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.authorpicwidth.value != ""){
                var authorpicwidth =  document.RegForm.authorpicwidth.value;
                if( !IsNum(authorpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.authorpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.contentpicheight.value != ""){
                var contentpicheight =  document.RegForm.contentpicheight.value;
                if( !IsNum(contentpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.contentpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.contentpicwidth.value != ""){
                var contentpicwidth =  document.RegForm.contentpicwidth.value;
                if( !IsNum(contentpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.contentpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.specialpicheight.value != ""){
                var specialpicheight =  document.RegForm.specialpicheight.value;
                if( !IsNum(specialpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.specialpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.specialpicwidth.value != ""){
                var specialpicwidth =  document.RegForm.specialpicwidth.value;
                if( !IsNum(specialpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.specialpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.productpicheight.value != ""){
                var productpicheight =  document.RegForm.productpicheight.value;
                if( !IsNum(productpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.productpicwidth.value != ""){
                var productpicwidth =  document.RegForm.productpicwidth.value;
                if( !IsNum(productpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.productsmallpicheight.value != ""){
                var productsmallpicheight =  document.RegForm.productsmallpicheight.value;
                if( !IsNum(productsmallpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productsmallpicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.productsmallpicwidth.value != ""){
                var productsmallpicwidth =  document.RegForm.productsmallpicwidth.value;
                if( !IsNum(productsmallpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.productsmallpicwidth.focus();
                    return false;
                }
            }

            if(document.RegForm.mediaheight.value != ""){
                var productpicheight =  document.RegForm.mediaheight.value;
                if( !IsNum(productpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediaheight.focus();
                    return false;
                }
            }

            if(document.RegForm.mediawidth.value != ""){
                var productpicwidth =  document.RegForm.mediawidth.value;
                if( !IsNum(productpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediawidth.focus();
                    return false;
                }
            }

            if(document.RegForm.mediapicheight.value != ""){
                var productsmallpicheight =  document.RegForm.mediapicheight.value;
                if( !IsNum(productsmallpicheight)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediapicheight.focus();
                    return false;
                }
            }

            if(document.RegForm.mediapicwidth.value != ""){
                var productsmallpicwidth =  document.RegForm.mediapicwidth.value;
                if( !IsNum(productsmallpicwidth)){
                    alert("请输入正整数！如：60");
                    document.RegForm.mediapicwidth.focus();
                    return false;
                }
            }

            return true;
        }

        function ltrim() {
            return this.replace(/ +/, "");
        }
        String.prototype.ltrim = ltrim;

        function rtrim() {
            return this.replace(/ +$/, "");
        }
        String.prototype.rtrim = rtrim;

        function IsNum(str) {
            var len = str.length;
            if (len == 0) return true;
            var bool = true;
            for (var i = 0; i < len; i++) {
                if (!(parseInt(str.substring(i, i + 1)) >= 0 && parseInt(str.substring(i, i + 1)) <= 9)) {
                    bool = false;
                    break;
                }
            }
            if (!bool || str == "")
                return false;
            else
                return true;
        }
    </script>
</head>

<body bgcolor=#cccccc>
<div align="center">
    <form method="POST" action="editSite.jsp" name="RegForm" onsubmit="javascript:return check();">
        <input type=hidden name="doUpdate" value="true">
        <input type=hidden name="siteid" value="<%=siteID%>">
        <input type="hidden" name="range" value="<%=range%>">
        <input type="hidden" name="pageno" value="<%=currentpage%>">
        <input type="hidden" name="searchflag" value="<%=doSearchFlag%>">
        <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00
               align=center>
            <tr>
                <td colspan=2 width="100%" bgcolor="#006699" align="center" height="16"><font color="#FFFFFF"><b>所有选项必须正确填写</b></font>
                </td>
            </tr>
            <tr height="32">
                <td width="35%" align="right">图像存储方式：</td>
                <td width="65%">
                    <input type="radio" value="0" name="pic" <%if(register.getImagesDir()==0){%>checked<%}%>>根目录images下
                    <input type="radio" value="1" name="pic" <%if(register.getImagesDir()==1){%>checked<%}%>>各栏目images下
                </td>
            </tr>
            <tr height=32>
                <td align="right">样式脚本文件存储方式：</td>
                <td>
                    <input type="radio" value="0" name="cssjsdir" <%=(register.getCssjsDir()==0)?"checked":""%>>根图片目录（存储在/images目录）
                    <input type="radio" value="1" name="cssjsdir" <%=(register.getCssjsDir()==1)?"checked":""%>>根独立目录(存储在/js目录和/css目录)
                    <input type="radio" value="2" name="cssjsdir" <%=(register.getCssjsDir()==2)?"checked":""%>>各栏目独立目录
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持繁体：</td>
                <td>
                    <input type="radio" value="0" name="tcflag" <%if(register.getTCFlag()==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="tcflag" <%if(register.getTCFlag()==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持WAP：</td>
                <td>
                    <input type="radio" value="0" name="wapflag" <%if(register.getWapFlag()==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="wapflag" <%if(register.getWapFlag()==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">文章发布方式：</td>
                <td><input type="radio" value="0" name="pubflag" <%if(register.getPubFlag()==0){%>checked<%}%>>手动&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="pubflag" <%if(register.getPubFlag()==1){%>checked<%}%>>自动
                </td>
            </tr>
            <tr height="32">
                <td align="right">发布列表显示规则：</td>
                <td><input type="radio" value="0" name="listShow" <%if(listShow==0){%>checked<%}%>>文章在前
                    <input type="radio" value="1" name="listShow" <%if(listShow==1){%>checked<%}%>>模板在前
                </td>
            </tr>
            <tr height="32">
                <td align="right">文章是否被其它站点引用：</td>
                <td><input type="radio" value="0" name="beRefered" <%if(register.getBeRefered()==0){%>checked<%}%>>禁止引用
                    <input type="radio" value="1" name="beRefered" <%if(register.getBeRefered()==1){%>checked<%}%>>支持引用
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间文章引用：</td>
                <td><input type="radio" value="0" name="tagSite" <%if(tagSite==0){%>checked<%}%>>不支持&nbsp;&nbsp;
                    <input type="radio" value="1" name="tagSite" <%if(tagSite==1){%>checked<%}%>>支持
                </td>
            </tr>
            <tr height="32">
                <td align="right">栏目是否被其它站点复制：</td>
                <td><input type="radio" value="0" name="beCopyColumn" <%if(beCopyColumn==0){%>checked<%}%>>禁止复制
                    <input type="radio" value="1" name="beCopyColumn" <%if(beCopyColumn==1){%>checked<%}%>>支持复制
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间栏目复制：</td>
                <td><input type="radio" value="0" name="copyColumn" <%if(copyColumn==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="copyColumn" <%if(copyColumn==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间文章推送：</td>
                <td><input type="radio" value="0" name="pushArticle" <%if(pushArticle==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="pushArticle" <%if(pushArticle==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间文章移动：</td>
                <td><input type="radio" value="0" name="moveArticle" <%if(moveArticle==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="moveArticle" <%if(moveArticle==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">站点状态：</td>
                <td><input type="radio" value="1" name="bindflag" <%if(register.getBindFlag()==1){%>checked<%}%>>激活&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="0" name="bindflag" <%if(register.getBindFlag()==0){%>checked<%}%>>暂停
                </td>
            </tr>
            <tr height=32>
                <td align="right">标题图片大小：</td>
                <td>&nbsp;高度：<input name="titlepicheight" type="text" size="3" value="<%=titlepic_h%>">px &nbsp; 宽度：<input name="titlepicwidth" type="text" size="3" value="<%=titlepic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">副标题图片大小：</td>
                <td>&nbsp;高度：<input name="vtitlepicheight" type="text" size="3" value="<%=vtitlepic_h%>">px &nbsp; 宽度：<input name="vtitlepicwidth" type="text" size="3" value="<%=vtitlepic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">来源图片大小：</td>
                <td>&nbsp;高度：<input name="sourcepicheight" type="text" size="3" value="<%=sourcepic_h%>">px &nbsp; 宽度：<input name="sourcepicwidth" type="text" size="3" value="<%=sourcepic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">作者图片大小：</td>
                <td>&nbsp;高度：<input name="authorpicheight" type="text" size="3" value="<%=authorpic_h%>">px &nbsp; 宽度：<input name="authorpicwidth" type="text" size="3" value="<%=authorpic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">内容图片大小：</td>
                <td>&nbsp;高度：<input name="contentpicheight" type="text" size="3" value="<%=contentpic_h%>">px &nbsp; 宽度：<input name="contentpicwidth" type="text" size="3" value="<%=contentpic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">特效图片大小：</td>
                <td>&nbsp;高度：<input name="specialpicheight" type="text" size="3" value="<%=specialpic_h%>">px &nbsp; 宽度：<input name="specialpicwidth" type="text" size="3" value="<%=specialpic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">商品大图片大小：</td>
                <td>&nbsp;高度：<input name="productpicheight" type="text" size="3" value="<%=productpic_h%>">px &nbsp; 宽度：<input name="productpicwidth" type="text" size="3" value="<%=productpic_w%>">px</td>
            </tr>
            <tr height=32>
                <td align="right">商品小图片大小：</td>
                <td>&nbsp;高度：<input name="productsmallpicheight" type="text" size="3" value="<%=productsmallpic_h%>">px &nbsp; 宽度：<input name="productsmallpicwidth" type="text" size="3" value="<%=productsmallpic_w%>">px</td>
            </tr>
            <tr height=20 id="mediafile_id">
                <td align=right class=line>视频文件:</td>
                <td class=tine>&nbsp;高度：<input name="mediaheight" type="text" size="3" value="<%=mediasize_h%>">px &nbsp; 宽度：<input name="mediawidth" type="text" size="3" value="<%=mediasize_w%>">px
                </td>
            </tr>
            <tr height=20 id="mediapic_id">
                <td align=right class=line>视频缩略图:</td>
                <td class=tine>&nbsp;高度：<input name="mediapicheight" type="text" size="3" value="<%=mediapicsize_h%>">px &nbsp; 宽度：<input name="mediapicwidth" type="text" size="3" value="<%=mediapicsize_w%>">px
                </td>
            </tr>

            <tr height="32">
                <td align="right">
                    首页扩展名：
                </td>
                <td>&nbsp;<select name=extname size=1 class=tine>
                    <option value="html"  <%=(extname.compareTo("html") == 0) ? "selected" : ""%>>html</option>
                    <option value="htm"   <%=(extname.compareTo("htm") == 0) ? "selected" : ""%>>htm</option>
                    <option value="jsp"   <%=(extname.compareTo("jsp") == 0) ? "selected" : ""%>>jsp</option>
                    <option value="asp"   <%=(extname.compareTo("asp") == 0) ? "selected" : ""%>>asp</option>
                    <option value="shtm"  <%=(extname.compareTo("shtm") == 0) ? "selected" : ""%>>shtm</option>
                    <option value="shtml" <%=(extname.compareTo("shtml") == 0) ? "selected" : ""%>>shtml</option>
                    <option value="php"   <%=(extname.compareTo("php") == 0) ? "selected" : ""%>>php</option>
                    <option value="wml"   <%=(extname.compareTo("wml") == 0) ? "selected" : ""%>>wml</option>
                </select>
                </td>
            </tr>
        </table>
        <p><input type="submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;
            <input type="button" value=" 取消 " onclick="window.close();"></p>
    </form>
</div>

</body>
</html>
