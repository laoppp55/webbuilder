<%@ page import="java.sql.*,
                 java.util.List,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.viewFileManager.*" contentType="text/html;charset=GBK"%>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page import="com.bizwink.wenba.wenbaImpl" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean errors = false;
    boolean success = false;
    int siteid = authToken.getSiteID();
   // System.out.println("siteid="+siteid);
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int ID = ParamUtil.getIntParameter(request, "ID", 0);
    int parentID = ParamUtil.getIntParameter(request, "parentID", 0);
   // System.out.println("parentID="+parentID+"     ID"+ ID);
    String CName = ParamUtil.getParameter(request, "CName");
    String extfilename = ParamUtil.getParameter(request, "extfilename");
    String xmlTemplate = ParamUtil.getParameter(request, "xmlTemplate");
    int orderid = ParamUtil.getIntParameter(request, "orderid", 1);
    String desc = ParamUtil.getParameter(request, "desc");
    int isAudited = ParamUtil.getIntParameter(request, "isAudited", 0);
    int isProduct = ParamUtil.getIntParameter(request, "isProduct", 0);
    int isDefineAttr = ParamUtil.getIntParameter(request, "isDefineAttr", 0);
    int isPublishMore = ParamUtil.getIntParameter(request, "isPublishMore", 0);
    int languageType = ParamUtil.getIntParameter(request, "languageType", 0);
    int isRss = ParamUtil.getIntParameter(request, "isRss", 0);
    int contentshowtype = ParamUtil.getIntParameter(request, "showtype", 0);
    List getselectColumns = ParamUtil.getParameterValues(request, "selectcolumns");
    List getselectTypes = ParamUtil.getParameterValues(request, "selecttypes");
    int useArticleType = ParamUtil.getIntParameter(request, "useArticleType", 0);
    int isType = ParamUtil.getIntParameter(request, "isType", 0);

    Tree colTree = TreeManager.getInstance().getSiteTree(siteid);
    int rootID = colTree.getTreeRoot();

    if (doUpdate) {
        if (CName == null) errors = true;
        if (desc != null)
            desc = desc.trim();
        else
            desc = "";
    }

    IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
    wenbaImpl column = columnMgr.getColumn(ID);
   
    String selectColumns = ",";
    List selectColumnsList = null;
    List selectTypesList = null;

    if (!errors && doUpdate) {

        try {
            if (isDefineAttr == 0) {
                xmlTemplate = "";
            } else if (xmlTemplate == null || xmlTemplate.length() == 0) {
              //  xmlTemplate = column.getXMLTemplate();
                if (xmlTemplate == null) xmlTemplate = "";
                xmlTemplate = StringUtil.gb2iso4View(xmlTemplate);
            }
           
            column.setID(ID);
            column.setSiteID(siteid);
            column.setParentID(parentID);
            column.setCName(StringUtil.gb2isoindb(CName));
        //    column.setExtname(extfilename);
          //  column.setLastUpdated(new Timestamp(System.currentTimeMillis()));
            String editor = authToken.getUserID();
            column.setOrderID(orderid);
         //   column.setEditor(editor);
        //    column.setDesc(StringUtil.gb2isoindb(desc));
        //    column.setXMLTemplate(StringUtil.gb2isoindb(xmlTemplate));
        //    column.setIsAudited(isAudited);
        //    column.setIsProduct(isProduct);
       //     column.setDefineAttr(isDefineAttr);
       //     column.setIsPublishMoreArticleModel(isPublishMore);
      //      column.setLanguageType(languageType);
     //       column.setRss(isRss);
       //     column.setContentShowType(contentshowtype);

        //    selectColumnsList = columnManager.getSourceSiteIdAndCID(getselectColumns);
        //    column.setSelectColumns(selectColumnsList);

        //    selectTypesList = columnManager.getReferArticleTypesColumn(getselectTypes);
        //    column.setSelectTypes(selectTypesList);
            
         //   column.setUseArticleType(useArticleType);
        //    column.setIsType(isType);

            columnMgr.update(column);
            success = true;
            System.out.println("success="+success);
        }
        catch (Exception e) {
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
  //  extfilename = column.getExtname();
    if (extfilename == null) extfilename = "";
    orderid = column.getOrderID();
  //  desc = column.getDesc();
    if (desc == null) desc = "";
    desc = StringUtil.gb2iso4View(desc);
 //   isProduct = column.getIsProduct();
 //   languageType = column.getLanguageType();
//    isRss = column.getRss();
 //   useArticleType = column.getUseArticleType();
    String[] selectC = null;

    try {
    //    selectColumnsList = columnManager.getRefersColumnIds(ID, siteid);
     //   selectTypesList = columnManager.getReferTypesColumnIds(ID);

        for (int i = 0; i < selectColumnsList.size(); i++) {
            Column scolumn = (Column) selectColumnsList.get(i);
            selectColumns = selectColumns + scolumn.getScid() + ",";
        }
        System.out.println("selectColumns="+selectColumns);
        if ((selectColumns != null) && (selectColumns.lastIndexOf(",") > 1)) {
            selectColumns = selectColumns.substring(1, selectColumns.length() - 1);
            selectC = selectColumns.split(",");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    String parentName = "网站首页";
    if (parentID > 0) {
       // IWenbaManager columnMgr1 = wenbaManagerImpl.getInstance();
        wenbaImpl parentColumn = columnMgr.getColumn(parentID);
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
        function editAttr()
        {
            var bln = confirm("您要继承父栏目的扩展属性吗？");
            if (!bln)
                window.open("editattr.jsp?ID=<%=ID%>", "", "width=600,height=400,left=200,right=200,scrollbars,status");
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
            return true;
        }

        function selectColumnTree() {
            window.open('selectColumnTree.jsp?ID=<%=ID%>', 'win', 'top=150,left=150,width=600,height=400 scrolling=yes');
        }

        function edittype()
        {
            var bln = confirm("您要继承父栏目并自定义分类吗？");
            if (bln)
                window.open("edittype.jsp?parentID=<%=parentID%>&columnID=<%=ID%>", "", "width=600,height=400,left=200,right=200,scrollbars,status");
        }
        function createtype()
        {
            var bln = confirm("您确定要自定义文章分类吗？");
            if (bln)
                window.open("createnewtype.jsp?columnID=<%=ID%>", "", "width=600,height=400,left=200,right=200,scrollbars,status");
        }

        function InheritanceType() {
            var objXml = new ActiveXObject("Microsoft.XMLHTTP");
            objXml.open("POST", "inheritancetype.jsp?parentID=<%=parentID%>&columnID=<%=ID%>", false);
            objXml.Send();
        }

        function refertype() {
            window.open('selectTypeTree.jsp?ID=<%=ID%>', 'win', 'top=150,left=150,width=600,height=400 scrolling=yes');
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
<%@ include file="../../inc/titlebar.jsp" %>
<center>
<form action="editcolumn.jsp" method="post" name="updateForm" onsubmit="javascript:return check(this);">
<input type=hidden name="doUpdate" value="true">
<input type=hidden name="ID" value="<%=ID%>">
<input type=hidden name="parentID" value="<%=parentID%>">
<input type=hidden name="useArticleType" value="<%=useArticleType%>">
<input type=hidden name="xmlTemplate">

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




</table>

<br>
<input type=submit value="  保存  ">&nbsp;&nbsp;&nbsp;&nbsp;
<input type=button value="  返回  " onclick="history.go(-1);">
</form>
</center>

</BODY>
</html>