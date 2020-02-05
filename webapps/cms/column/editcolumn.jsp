<%@ page import="java.sql.*,
                 java.util.List,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.viewFileManager.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.util.ArrayList" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean errors = false;
    boolean success = false;
    int siteid = authToken.getSiteID();

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    int parentID = ParamUtil.getIntParameter(request, "parentID", 0);
    String CName = ParamUtil.getParameter(request, "CName");
    String extfilename = ParamUtil.getParameter(request, "extfilename");
    String xmlTemplate = ParamUtil.getParameter(request, "xmlTemplate");
    int orderid = ParamUtil.getIntParameter(request, "orderid", 1);
    String desc = ParamUtil.getParameter(request, "desc");
    int isAudited = ParamUtil.getIntParameter(request, "isAudited", 0);
    int isProduct = ParamUtil.getIntParameter(request, "isProduct", 0);
    int isLocation = ParamUtil.getIntParameter(request, "isLocation", 0);
    int isDefineAttr = ParamUtil.getIntParameter(request, "isDefineAttr", 0);
    int isPublishMore = ParamUtil.getIntParameter(request, "isPublishMore", 0);
    int languageType = ParamUtil.getIntParameter(request, "languageType", 0);
    int isRss = ParamUtil.getIntParameter(request, "isRss", 0);
    int contentshowtype = ParamUtil.getIntParameter(request, "showtype", 0);
    int extattrscope = ParamUtil.getIntParameter(request, "extattrscope", 0);
    int useArticleType = ParamUtil.getIntParameter(request, "useArticleType", 0);
    int isType = ParamUtil.getIntParameter(request, "isType", 0);
    int userflag = ParamUtil.getIntParameter(request,"userflag",0);
    int userlevel = 0;
    int publicflag = ParamUtil.getIntParameter(request,"publicflag",0);
    if(userflag == 1){
        userlevel = ParamUtil.getIntParameter(request,"userlevel",0);
    }

    Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
    int rootID = colTree.getTreeRoot();
    session.setAttribute("Current_URL",request.getRequestURI());


    String titlepic = "";
    String vtitlepic = "";
    String sourcepic = "";
    String authorpic = "";
    String contentpic = "";
    String specialpic = "";
    String productpic = "";
    String productsmallpic = "";

    if (doUpdate) {
        if (CName == null) errors = true;
        if (desc != null)
            desc = desc.trim();
        else
            desc = "";
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
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(ID);
    List selectColumnsList = ParamUtil.getParameterValues(request, "selectcolumns");
    List selectTypesList = ParamUtil.getParameterValues(request, "selecttypes");
    String selectColumns = ",";

    if (!errors && doUpdate) {
        List sColumns = new ArrayList();
        for(int ii=0; ii<selectColumnsList.size();ii++) {
            int scolumnId = Integer.parseInt((String) selectColumnsList.get(ii));
            Column scol = columnManager.getColumn(scolumnId);
            scol.setScid(scolumnId);
            scol.setSsiteid(scol.getSiteID());
            sColumns.add(scol);
        }
        try {
            if (isDefineAttr == 0) {
                xmlTemplate = "";
            } else if (xmlTemplate == null || xmlTemplate.length() == 0) {
                xmlTemplate = column.getXMLTemplate();
                if (xmlTemplate == null) xmlTemplate = "";
                xmlTemplate = StringUtil.gb2iso4View(xmlTemplate);
            }
            column.setID(ID);
            column.setSiteID(siteid);
            column.setParentID(parentID);
            column.setCName(StringUtil.gb2isoindb(CName));
            column.setExtname(extfilename);
            column.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            String editor = authToken.getUserID();
            column.setOrderID(orderid);
            column.setEditor(editor);
            column.setDesc(StringUtil.gb2isoindb(desc));
            column.setXMLTemplate(StringUtil.gb2isoindb(xmlTemplate));
            column.setExtattrscope(extattrscope);                       //扩展属性的适用范围，1-本栏目及其以下子栏目 0-本栏目
            column.setIsAudited(isAudited);
            column.setIsProduct(isProduct);
            column.setIsPosition(isLocation);
            column.setDefineAttr(isDefineAttr);
            column.setIsPublishMoreArticleModel(isPublishMore);
            column.setPublicflag(publicflag);
            column.setLanguageType(languageType);
            column.setRss(isRss);
            column.setContentShowType(contentshowtype);
            column.setSelectColumns(sColumns);
            column.setUseArticleType(useArticleType);
            column.setIsType(isType);
            column.setUserflag(userflag);
            column.setUserlevel(userlevel);
            column.setTitlepic(titlepic);
            column.setVtitlepic(vtitlepic);
            column.setSourcepic(sourcepic);
            column.setAuthorpic(authorpic);
            column.setContentpic(contentpic);
            column.setSpecialpic(specialpic);
            column.setProductpic(productpic);
            column.setProductsmallpic(productsmallpic);
            columnManager.update(column,siteid);
            success = true;
        }
        catch (ColumnException e) {
            e.printStackTrace();
        }
    }

    if (success) {
        response.sendRedirect(response.encodeRedirectURL("index.jsp?rightid=1"));
        return;
    }

    //读出所有数据
    parentID = column.getParentID();
    CName = StringUtil.gb2iso4View(column.getCName());
    String EName = column.getEName();
    extfilename = column.getExtname();
    if (extfilename == null) extfilename = "";
    orderid = column.getOrderID();
    desc = column.getDesc();
    if (desc == null) desc = "";
    desc = StringUtil.gb2iso4View(desc);
    isProduct = column.getIsProduct();
    languageType = column.getLanguageType();
    isRss = column.getRss();
    useArticleType = column.getUseArticleType();
    String[] selectC = null;

    String titlepic_w = "";
    String titlepic_h = "";
    String vtitlepic_w = "";
    String vtitlepic_h = "";
    String sourcepic_w = "";
    String sourcepic_h = "";
    String authorpic_w = "";
    String authorpic_h = "";
    String contentpic_w = "";
    String contentpic_h = "";
    String specialpic_w = "";
    String specialpic_h = "";
    String productpic_w = "";
    String productpic_h = "";
    String productsmallpic_w = "";
    String productsmallpic_h = "";

    titlepic = column.getTitlepic();
    vtitlepic = column.getVtitlepic();
    sourcepic = column.getSourcepic();
    authorpic = column.getAuthorpic();
    contentpic = column.getContentpic();
    specialpic = column.getSpecialpic();
    productpic = column.getProductpic();
    productsmallpic = column.getProductsmallpic();

    String seperator = "*";
    int posi = -1;
    if (titlepic!=null) {
        posi = titlepic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = titlepic.indexOf(seperator);
        }
        if (posi>-1) {
            titlepic_w = titlepic.substring(0,posi);
            titlepic_h = titlepic.substring(posi+1);
        }
    }

    if (vtitlepic!=null) {
        posi = vtitlepic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = vtitlepic.indexOf(seperator);
        }
        if (posi>-1) {
            vtitlepic_w = vtitlepic.substring(0,posi);
            vtitlepic_h = vtitlepic.substring(posi+1);
        }
    }

    if (authorpic!=null) {
        posi = authorpic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = authorpic.indexOf(seperator);
        }
        if (posi>-1) {
            authorpic_w = authorpic.substring(0,posi);
            authorpic_h = authorpic.substring(posi+1);
        }
    }

    if (contentpic!=null) {
        posi = contentpic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = contentpic.indexOf(seperator);
        }
        if (posi>-1) {
            contentpic_w = contentpic.substring(0,posi);
            contentpic_h = contentpic.substring(posi+1);
        }
    }

    if (specialpic!=null) {
        posi = specialpic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = specialpic.indexOf(seperator);
        }
        if (posi>-1) {
            specialpic_w = specialpic.substring(0,posi);
            specialpic_h = specialpic.substring(posi+1);
        }
    }

    if (productpic!=null) {
        posi = productpic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = productpic.indexOf(seperator);
        }
        if (posi>-1) {
            productpic_w = productpic.substring(0,posi);
            productpic_h = productpic.substring(posi+1);
        }
    }

    if (productsmallpic!=null) {
        posi = productsmallpic.indexOf(seperator);
        if (posi == -1) {
            seperator = "X";
            posi = productsmallpic.indexOf(seperator);
        }
        if (posi>-1) {
            productsmallpic_w = productsmallpic.substring(0,posi);
            productsmallpic_h = productsmallpic.substring(posi+1);
        }
    }

    try {
        selectColumnsList = columnManager.getRefersColumnIds(ID, siteid);
        selectTypesList = columnManager.getReferTypesColumnIds(ID);

        for (int i = 0; i < selectColumnsList.size(); i++) {
            Column scolumn = (Column) selectColumnsList.get(i);
            selectColumns = selectColumns + scolumn.getScid() + ",";
        }

        if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 1)) {
            selectColumns = selectColumns.substring(1, selectColumns.length() - 1);
            selectC = selectColumns.split(",");
        }
    } catch (ColumnException e) {
        e.printStackTrace();
    }

    String parentName = "网站首页";
    if (parentID > 0) {
        columnManager = ColumnPeer.getInstance();
        Column parentColumn = columnManager.getColumn(parentID);
        if (parentColumn != null) {
            parentName = StringUtil.gb2iso4View(parentColumn.getCName());
        }
    }
%>

<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script language=javascript>
function editAttr(){
    var iWidth = 500;
    var iHeight = 300;
    var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
    var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
    var win = window.open("confirm.jsp", "弹出窗口", "width=" + iWidth + ", height=" + iHeight + ",top=" + iTop + ",left=" + iLeft + ",toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no,alwaysRaised=yes,depended=yes");
    //var result = window.showModalDialog("confirm.jsp","","dialogHeight:100px;dialogWidth:280px;status:no; help:no;scroll:no");
    //if (result == "self")
    //    window.open("editattr.jsp?ID=<%=ID%>", "", "width=1000,height=600,left=200,right=200,top=10,scrollbars,status");;
}

function editRss()
{
    var bln = confirm("您要将本栏目发布RSS吗？");
    if (!bln)
        window.history.go(0);
    else
        window.open("editrss.jsp?ID=<%=ID%>", "", "width=400,height=300,left=200,right=200,scrollbars,status");
}

function Upload()
{
    window.open("../upload/upload.jsp?column=<%=rootID%>&attr=updateDesc", "Upload", "width=400,height=200,left=200,top=200");
}

function check(frm)
{
    if (frm.CName.value == "")
    {
        alert("栏目中文名称不能为空！");
        return false;
    }
    if (frm.CName.value.indexOf(",") > -1)
    {
        alert("栏目中文名称中不能含有逗号！");
        return false;
    }
    if (frm.userflag[1].checked) {
        var num = frm.userlevel.value;
        if (!isNumber(num)) {
            alert("用户级别必须是整数！");
            frm.userlevel.focus();
            return false;
        }
    }
    if(frm.titlepicheight.value != ""){
        if( !isNumber(frm.titlepicheight.value)){
            alert("请输入正整数 如:36");
            frm.titlepicheight.focus();
            return false;
        }
    }
    if(frm.titlepicwidth.value != ""){
        if( !isNumber(frm.titlepicwidth.value)){
            alert("请输入正整数 如:36");
            frm.titlepicwidth.focus();
            return false;
        }
    }
    if(frm.vtitlepicheight.value != ""){
        if( !isNumber(frm.vtitlepicheight.value)){
            alert("请输入正整数 如:36");
            frm.vtitlepicheight.focus();
            return false;
        }
    }
    if(frm.vtitlepicheight.value != ""){
        if( !isNumber(frm.vtitlepicheight.value)){
            alert("请输入正整数 如:36");
            frm.vtitlepicheight.focus();
            return false;
        }
    }
    if(frm.sourcepicheight.value != ""){
        if( !isNumber(frm.sourcepicheight.value)){
            alert("请输入正整数 如:36");
            frm.sourcepicheight.focus();
            return false;
        }
    }
    if(frm.sourcepicwidth.value != ""){
        if( !isNumber(frm.sourcepicwidth.value)){
            alert("请输入正整数 如:36");
            frm.sourcepicwidth.focus();
            return false;
        }
    }
    if(frm.authorpicheight.value != ""){
        if( !isNumber(frm.authorpicheight.value)){
            alert("请输入正整数 如:36");
            frm.authorpicheight.focus();
            return false;
        }
    }
    if(frm.authorpicwidth.value != ""){
        if( !isNumber(frm.authorpicwidth.value)){
            alert("请输入正整数 如:36");
            frm.authorpicwidth.focus();
            return false;
        }
    }
    if(frm.contentpicheight.value != ""){
        if( !isNumber(frm.contentpicheight.value)){
            alert("请输入正整数 如:36");
            frm.contentpicheight.focus();
            return false;
        }
    }
    if(frm.contentpicwidth.value != ""){
        if( !isNumber(frm.contentpicwidth.value)){
            alert("请输入正整数 如:36");
            frm.contentpicwidth.focus();
            return false;
        }
    }
    if(frm.specialpicheight.value != ""){
        if( !isNumber(frm.specialpicheight.value)){
            alert("请输入正整数 如:36");
            frm.specialpicheight.focus();
            return false;
        }
    }
    if(frm.specialpicwidth.value != ""){
        if( !isNumber(frm.specialpicwidth.value)){
            alert("请输入正整数 如:36");
            frm.specialpicwidth.focus();
            return false;
        }
    }
    if(frm.productpicheight.value != ""){
        if( !isNumber(frm.productpicheight.value)){
            alert("请输入正整数 如:36");
            frm.titlepicheight.focus();
            return false;
        }
    }
    if(frm.productpicwidth.value != ""){
        if( !isNumber(frm.productpicwidth.value)){
            alert("请输入正整数 如:36");
            frm.productpicwidth.focus();
            return false;
        }
    }
    if(frm.productsmallpicheight.value != ""){
        if( !isNumber(frm.productsmallpicheight.value)){
            alert("请输入正整数 如:36");
            frm.productsmallpicheight.focus();
            return false;
        }
    }
    if(frm.productsmallpicwidth.value != ""){
        if( !isNumber(frm.productsmallpicwidth.value)){
            alert("请输入正整数 如:36");
            frm.productsmallpicwidth.focus();
            return false;
        }
    }
    function isNumber(num) {
        strRef = "1234567890";
        for (i = 0; i < num.length; i++)
        {
            tempChar = num.substring(i, i + 1);
            if (strRef.indexOf(tempChar, 0) == -1) {
                return false;
            }
        }
        return true;
    }
    return true;
}

function selectColumnTree() {
    window.open('selectColumnTree.jsp?ID=<%=ID%>', 'win', 'top=150,left=150,width=600,height=400 scrolling=yes');
}

function InheritanceType() {
    var objXml = new ActiveXObject("Microsoft.XMLHTTP");
    objXml.open("POST", "inheritancetype.jsp?parentID=<%=parentID%>&columnID=<%=ID%>", false);
    objXml.Send();
}

function refertype() {
    window.open('selectTypeTree.jsp?ID=<%=ID%>', 'win', 'top=150,left=150,width=600,height=400 scrolling=yes');
}

function displayProdPic() {
    var radio = document.getElementsByName("isProduct");
    var radioval = null;
    for (i=0; i<radio.length; i++) {
        if (radio[i].checked) {
            radioval = radio[i].value;
            break;
        }
    }
    if (parseInt(radioval,10) == 1) {
        var prodLargePicRow = document.getElementById("prod_largepic_id");
        prodLargePicRow.style.display = "block";
        var prodSmallPicRow = document.getElementById("prod_smallpic_id");
        prodSmallPicRow.style.display = "block";
    } else {
        var prodLargePicRow = document.getElementById("prod_largepic_id");
        prodLargePicRow.style.display = "none";
        var prodSmallPicRow = document.getElementById("prod_smallpic_id");
        prodSmallPicRow.style.display = "none";
    }
}
</script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"修改栏目", ""}
    };
    String[][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>
<center>
<form action="editcolumn.jsp" method="post" name="updateForm" onsubmit="javascript:return check(this);">
<input type=hidden name="doUpdate" value="true">
<input type=hidden name="ID" value="<%=ID%>">
<input type=hidden name="parentID" value="<%=parentID%>">
<input type=hidden name="useArticleType" value="<%=useArticleType%>">
<input type=hidden name="xmlTemplate" value="">
<input type=hidden name="extattrscope" value="0">

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="60%"
       align=center>
    <tr height=20>
        <td align=right class=line>父栏目名：</td>
        <td class=tine>&nbsp;<%= parentName %>
        </td>
    </tr>
    <tr height=28>
        <td align=right><font class=line <%= (errors) ? (" color=\"#ff0000\"") : "" %>>栏目名称：</font></td>
        <td>&nbsp;<input class=tine name=CName size=30 value="<%= (CName!=null)?CName:"" %>">*</td>
    </tr>
    <tr height=28>
        <td align=right><font class=line>栏目目录：</font></td>
        <td>&nbsp;<input class=tine name=EName size=30 value="<%= (EName!=null)?EName:"" %>" disabled>*</td>
    </tr>
    <tr height=28>
        <td align=right class=line>栏目排序：</td>
        <td>&nbsp;<input class=tine name=orderid size=30 value="<%= orderid %>">*</td>
    </tr>
    <tr height=28>
        <td class=line align=right>文件扩展名：</td>
        <td class=tine>&nbsp;<select name=extfilename size=1 class=tine>
            <option value="html" <%= (extfilename.compareTo("html") == 0) ? "selected" : "" %>>html</option>
            <option value="htm" <%= (extfilename.compareTo("htm") == 0) ? "selected" : "" %>>htm</option>
            <option value="jsp" <%= (extfilename.compareTo("jsp") == 0) ? "selected" : "" %>>jsp</option>
            <option value="asp" <%= (extfilename.compareTo("asp") == 0) ? "selected" : "" %>>asp</option>
            <option value="shtm" <%= (extfilename.compareTo("shtm") == 0) ? "selected" : "" %>>shtm</option>
            <option value="shtml" <%= (extfilename.compareTo("shtml") == 0) ? "selected" : "" %>>shtml</option>
            <option value="php" <%= (extfilename.compareTo("php") == 0) ? "selected" : "" %>>php</option>
            <option value="wml" <%= (extfilename.compareTo("wml") == 0) ? "selected" : "" %>>wml</option>
        </select>
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>栏目语言类型：</td>
        <td class=tine>&nbsp;<select name=languageType size=1 class=tine style="width:100">
            <option value="0" <%=(languageType == 0) ? "selected" : ""%>>简体中文</option>
            <option value="1" <%=(languageType == 1) ? "selected" : ""%>>繁体中文</option>
            <option value="2" <%=(languageType == 2) ? "selected" : ""%>>日文</option>
        </select>
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>地理位置信息：</td>
        <td class=tine>
            <input type=radio name="isLocation" value="0" <%if(isLocation==0){%>checked<%}%>>否
            <input type=radio name="isLocation" value="1" <%if(isLocation==1){%>checked<%}%>>是
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>是否需要审核：</td>
        <td class=tine>
            <input type=radio name="isAudited" <%if(column.getIsAudited()==0){%>checked<%}%> value="0">否&nbsp;&nbsp;
            <input type=radio name="isAudited" <%if(column.getIsAudited()==1){%>checked<%}%> value="1">是
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>是否需要扩展属性：</td>
        <td class=tine>
            <input type=radio name="isDefineAttr" value="0" <%if(column.getDefineAttr()==0){%>checked<%}%>>否&nbsp;&nbsp;
            <input type=radio name="isDefineAttr" value="1" <%if(column.getDefineAttr()==1){%>checked<%}%>
                   onclick="editAttr();">是
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>是否发布RSS：</td>
        <td class=tine>
            <input type=radio name="isRss" value="0" <%if(column.getRss()==0){%>checked<%}%>>否&nbsp;&nbsp;
            <input type=radio name="isRss" value="1" <%if(column.getRss()==1){%>checked<%}%> onclick="editRss();">是
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>发布多个文章模板：</td>
        <td class=tine>
            <input type=radio name="isPublishMore" value="0"
                   <%if(column.getIsPublishMoreArticleModel()==0){%>checked<%}%>>否&nbsp;&nbsp;
            <input type=radio name="isPublishMore" value="1"
                   <%if(column.getIsPublishMoreArticleModel()==1){%>checked<%}%>>是
        </td>
    </tr>
    <tr height=30>
        <td class=line align=right>是否接受网站注册用户输入信息：</td>
        <td class=tine>
            <input type=radio name="publicflag" value="0" <%if(column.getPublicflag() == 0){%>checked<%}%>>否&nbsp;&nbsp;
            <input type=radio name="publicflag" value="1" <%if(column.getPublicflag() == 1){%>checked<%}%>>是
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>商品或文章：</td>
        <td class=tine>
            <input type=radio name="isProduct" value="0" onclick="javascript:displayProdPic();" <%if(isProduct==0){%>checked<%}%>>文章
            <input type=radio name="isProduct" value="1" onclick="javascript:displayProdPic();" <%if(isProduct==1){%>checked<%}%>>商品
        </td>
    </tr>
    <tr height=28>
        <td class=line align=right>引用栏目文章：</td>
        <td class=tine>
            <table>
                <tr>
                    <td>&nbsp;<a href="javascript:selectColumnTree();">选择被引用栏目</a></td>
                    <td>
                        <span id="columns">
            <%
                if ((selectC != null) && (selectC.length > 0)) {
                    for (int i = 0; i < selectC.length; i++) {
                        column = columnManager.getColumn(Integer.parseInt(selectC[i]));
                        String cname = column.getCName();
                        cname = StringUtil.gb2iso4View(cname);
            %>
                <input type="checkbox" name="selectcolumns" value="<%=column.getID()%>" checked><%=cname%>
            <%
                    }
                    if (useArticleType == 0)
                        out.println("(引用文章链接)");
                    else
                        out.println("(引用文章内容)");
                }
            %>
            </span>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr height=30>
        <td class=line align=right>栏目文章权限控制：</td>
        <td class=tine>
            &nbsp;<input type=radio name="userflag" value="0" onclick='javascript:userlevel.readOnly=true;'
                         <%if(column.getUserflag()==0){%>checked<%}%>>所有用户可见
            <input type=radio name="userflag" value="1" onclick='javascript:userlevel.readOnly=false;' <%if(column.getUserflag()==1){%>checked<%}%>>只有注册用户可见&nbsp;&nbsp;
            用户级别： <select name="userlevel" id="userlevel">
            <option value="-1">---请选择---</option>
            <option value="0" <%=(column.getUserlevel()==0)?"selected":""%>>普通用户</option>
            <option value="1" <%=(column.getUserlevel()==1)?"selected":""%>>内部用户</option>
            <option value="2" <%=(column.getUserlevel()==2)?"selected":""%>>企业用户</option>
            <option value="3" <%=(column.getUserlevel()==3)?"selected":""%>>VIP用户</option>
        </select>
        </td>
    </tr>
    <tr>
        <td class=line align=right>栏目描述：</td>
        <td class=tine>&nbsp;<textarea class=tine rows="4" name="desc" cols="40"><%=desc%>
        </textarea><a href="javascript:Upload();">图片</a></td>
    </tr>
    <tr height=20>
        <td align=right class=line>标题图片大小：</td>
        <td class=tine>&nbsp;高度：<input name="titlepicheight" type="text" size="3" value="<%=titlepic_w%>">px &nbsp; 宽度：<input name="titlepicwidth" type="text" size="3" value="<%=titlepic_h%>">px
        </td>
    </tr>
    <tr height=20>
        <td align=right class=line>副标题图片大小：</td>
        <td class=tine>&nbsp;高度：<input name="vtitlepicheight" type="text" size="3" value="<%=vtitlepic_w%>">px &nbsp; 宽度：<input name="vtitlepicwidth" type="text" size="3" value="<%=vtitlepic_h%>">px
        </td>
    </tr>
    <tr height=20>
        <td align=right class=line>来源图片大小：</td>
        <td class=tine>&nbsp;高度：<input name="sourcepicheight" type="text" size="3" value="<%=sourcepic_w%>">px &nbsp; 宽度：<input name="sourcepicwidth" type="text" size="3" value="<%=sourcepic_h%>">px
        </td>
    </tr>
    <tr height=20>
        <td align=right class=line>作者图片大小：</td>
        <td class=tine>&nbsp;高度：<input name="authorpicheight" type="text" size="3" value="<%=authorpic_w%>">px &nbsp; 宽度：<input name="authorpicwidth" type="text" size="3" value="<%=authorpic_h%>">px
        </td>
    </tr>
    <tr height=20>
        <td align=right class=line>内容图片大小：</td>
        <td class=tine>&nbsp;高度：<input name="contentpicheight" type="text" size="3" value="<%=contentpic_w%>" >px &nbsp; 宽度：<input name="contentpicwidth" type="text" size="3" value="<%=contentpic_h%>">px
        </td>
    </tr>
    <tr height=20>
        <td align=right class=line>文章特效图片大小：</td>
        <td class=tine>&nbsp;高度：<input name="specialpicheight" type="text" size="3" value="<%=specialpic_w%>">px &nbsp; 宽度：<input name="specialpicwidth" type="text" size="3" value="<%=specialpic_h%>">px
        </td>
    </tr>
    <tr height=20 id="prod_largepic_id" style="display: <%=(isProduct==1)?"block":"none"%>">
        <td align=right class=line>商品大图片大小:</td>
        <td class=tine>&nbsp;高度：<input name="productpicheight" type="text" size="3" value="<%=productpic_w%>">px &nbsp; 宽度：<input name="productpicwidth" type="text" size="3" value="<%=productpic_h%>">px
        </td>
    </tr>
    <tr height=20 id="prod_smallpic_id" style="display: <%=(isProduct==1)?"block":"none"%>">
        <td align=right class=line>商品小图片大小:</td>
        <td class=tine>&nbsp;高度：<input name="productsmallpicheight" type="text" size="3" value="<%=productsmallpic_w%>">px &nbsp; 宽度：<input name="productsmallpicwidth" type="text" size="3" value="<%=productsmallpic_h%>">px
        </td>
    </tr>
    <tr height=20 id="mediafile_id" style="display: <%=(isProduct==1)?"block":"none"%>">
        <td align=right class=line>视频文件:</td>
        <td class=tine>&nbsp;高度：<input name="mediaheight" type="text" size="3" value="<%=productsmallpic_w%>">px &nbsp; 宽度：<input name="mediawidth" type="text" size="3" value="<%=productsmallpic_h%>">px
        </td>
    </tr>
    <tr height=20 id="mediapic_id" style="display: <%=(isProduct==1)?"block":"none"%>">
        <td align=right class=line>视频抽取图片:</td>
        <td class=tine>&nbsp;高度：<input name="mediapicheight" type="text" size="3" value="<%=productsmallpic_w%>">px &nbsp; 宽度：<input name="mediapicwidth" type="text" size="3" value="<%=productsmallpic_h%>">px
        </td>
    </tr>

</table>

<br>
<input type=submit value="  保存  ">&nbsp;&nbsp;&nbsp;&nbsp;
<input type=button value="  返回  " onclick="history.go(-1);">
</form>
</center>

</BODY>
</html>