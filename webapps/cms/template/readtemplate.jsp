<%@page import="java.sql.*,
    java.util.*,
    com.bizwink.cms.news.*,
    com.bizwink.cms.modelManager.*,
    com.bizwink.cms.security.*,
    com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.cms.server.CmsServer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
    if( authToken == null ) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    if ( authToken != null && !SecurityCheck.hasPermission(authToken,"template") ) {
        response.sendRedirect(
            response.encodeRedirectURL("../login.jsp?msg=You have not right to access!")
        );
    }

    String URL = authToken.getURL();

    // error variables for parameters
    boolean errorContent = false;

    // overall error variable
    boolean errors = false;

    // creation success variable:
    boolean success = false;

    // get parameters
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");
    int columnID     = ParamUtil.getIntParameter(request, "column", 0);
    isArticle      = ParamUtil.getIntParameter(request, "isArticle",0);

    int partnerID=0, isArticle=1;
    String content =null, editor=null;

    partnerID      = 0;
    editor         = authToken.getUserID();

    // if there are no errors at this point, start the process of
    // creating the article

    ITemplateManager templateManager = null;

    // if a channel was successfully created, say so and return (to stop the
    // jsp from executing

    if( success ) {
        response.sendRedirect(
            response.encodeRedirectURL("templates.jsp?column="+ columnID +"&msg=成功的创建了新的模板!")
        );
        response.flushBuffer();
        return;
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String columnName = column.getCName();
%>
<html><head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type=text/css href="../style/global.css">

<!-- Script Functions and Event Handlers -->
<script LANGUAGE="JavaScript" SRC="../toolbars/dhtmled.js">
</script>
<script ID="clientEventHandlersJS" LANGUAGE="JavaScript">
<!--
var MENU_SEPARATOR = ""; // Context menu separator
var docComplete = false;
var initialDocComplete = false;

var QueryStatusToolbarButtons = new Array();
var QueryStatusEditMenu = new Array();
var QueryStatusFormatMenu = new Array();
var QueryStatusHTMLMenu = new Array();
var QueryStatusTableMenu = new Array();

var ContextMenu = new Array();
var GeneralContextMenu = new Array();
var TableContextMenu = new Array();
var AbsPosContextMenu = new Array();

//
// Utility functions
//

// Constructor for custom object that represents an item on the context menu
function ContextMenuItem(string, cmdId) {
  this.string = string;
  this.cmdId = cmdId;
}

// Constructor for custom object that represents a QueryStatus command and
// corresponding toolbar element.
function QueryStatusItem(command, element) {
  this.command = command;
  this.element = element;
}

//
// Event handlers
//
function window_onload() {
  // Initialze QueryStatus tables. These tables associate a command id with the
  // corresponding button object. Must be done on window load, 'cause the buttons must exist.

  QueryStatusToolbarButtons[0] = new QueryStatusItem(DECMD_COPY, document.body.all["DECMD_COPY"]);
  QueryStatusToolbarButtons[1] = new QueryStatusItem(DECMD_CUT, document.body.all["DECMD_CUT"]);
  QueryStatusToolbarButtons[2] = new QueryStatusItem(DECMD_PASTE, document.body.all["DECMD_PASTE"]);
  QueryStatusToolbarButtons[3] = new QueryStatusItem(DECMD_REDO, document.body.all["DECMD_REDO"]);
  QueryStatusToolbarButtons[4] = new QueryStatusItem(DECMD_UNDO, document.body.all["DECMD_UNDO"]);
  QueryStatusToolbarButtons[5] = new QueryStatusItem(DECMD_HYPERLINK, document.body.all["DECMD_HYPERLINK"]);

  // Initialize the context menu arrays.
  GeneralContextMenu[0] = new ContextMenuItem("Cut", DECMD_CUT);
  GeneralContextMenu[1] = new ContextMenuItem("Copy", DECMD_COPY);
  GeneralContextMenu[2] = new ContextMenuItem("Paste", DECMD_PASTE);
 // GeneralContextMenu[3] = new ContextMenuItem("Bizwink", DECMD_BIZWINK);
  docComplete = false;
  return(true);
}

function DECMD_HYPERLINK_onclick() {
  tbContentElement.ExecCommand(DECMD_HYPERLINK,OLECMDEXECOPT_PROMPTUSER);
}

function DECMD_IMAGE_onclick() {
     sel =tbContentElement.DOM.selection.createRange();
     returnvalue = showModalDialog( "../toolbars/insimg.htm","",
                         "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
     if ((returnvalue !="")&&(returnvalue !=null)) {
	   dir = "<%=URL%>/images/upload/";

	   var tempstr = returnvalue;

	   posi = tempstr.lastIndexOf("-");
	   var a1 = tempstr.substr(posi+1);           //垂直方向的空间
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a2 = tempstr.substr(posi+1);           //水平方向的空间
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a3 = tempstr.substr(posi+1);           //边界宽度
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a4 = tempstr.substr(posi+1);           //对齐方向
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a5 = tempstr.substr(posi+1);           //替换文本
	   tempstr = tempstr.substr(0,posi);

	   pos =tempstr.lastIndexOf("\\")+1;
	   var filename = tempstr.substr(pos);        //上传文件名称
	   returnvalue = "<IMG alttag="+dir +filename+" src=" + tempstr + " hspace=" + a1 + " vspace=" + a2 + " border=" + a3;
	   returnvalue = returnvalue + " align=" + a4 + " alt=" + a5 + ">";
	   sel.pasteHTML(returnvalue);
     }else {
	    return(false);
     }


     var sContents=tbContentElement.DOM.body.innerHTML;
     tbContentElement.DocumentHTML=sContents;

     return(true);
}

function tbContentElement_DocumentComplete() {
<!--
   if (!initialDocComplete){
       tbContentElement.DOM.body.innerText="";
      // tbContentElement.DOM.body.innerHTML="";
  }

  initialDocComplete = true;
  docComplete = true;

  return(true);
}
//-->
</script>

<script LANGUAGE="JavaScript" SRC="../toolbars/btnclick<%=CmsServer.lang%>.js">
</script>
<script LANGUAGE="javascript" FOR="tbContentElement" EVENT="DisplayChanged">
<!--
  return tbContentElement_DisplayChanged();
//-->
</script>

<script LANGUAGE="javascript" FOR="tbContentElement" EVENT="ShowContextMenu">
<!--
return tbContentElement_ShowContextMenu()
//-->
</script>

<script LANGUAGE="javascript" FOR="tbContentElement" EVENT="ContextMenuAction(itemIndex)">
<!--
return tbContentElement_ContextMenuAction(itemIndex)
//-->
</script>

<SCRIPT LANGUAGE=javascript FOR=tbContentElement EVENT=DocumentComplete>
<!--
 tbContentElement_DocumentComplete()
//-->
</SCRIPT>

<SCRIPT LANGUAGE=javascript >
<!--
    function get_src(){
        var tempVar = tbContentElement.DocumentHTML;
        var regexp = /<([^<>"]*)?"([^<>]*)?>/g;
        var rtnAry = tempVar.match(regexp);

        //repace all quote mark with null-string
        if (rtnAry !=null) {
	      var len = rtnAry.length;
	      var regexp1 = /[",']+/g;
	      var i = 0;
	      while (i < len) {
	      	temp = rtnAry[i].replace(regexp1,"");
	      	tempVar = tempVar.replace(rtnAry[i],temp);
	      	i++;
	      }
	  }

      var  imgs = tbContentElement.DOM.body.all.tags("IMG");
      if (imgs !=null) {
	  var lens = imgs.length;
          var uploadURL = "http://test.sinopecgroup.com";

	  var j = 0;
	  var tmp;
	  while (j < lens) {
	    doc = imgs[j].src;
	    tmp = imgs[j].alttag;
	    doc = doc.replace("file://","");
	    if ((tmp !="") &&(tmp !=null)) {
		tempVar =tempVar.replace("alttag="+tmp,"");
		tempVar = tempVar.replace(doc,tmp);
	    }
	    j++;
	  }
      }

        createForm.content.value = tempVar;

        if (createForm.content.value == null) {
           alert("模板内容不能为空");
           return false;
        }

        createForm.submit();
        return true;
   }
//-->
</script>

</head>
<body LANGUAGE="javascript" onload="return window_onload()">

<%
    String[][] titlebars = {
        { "模板管理", "templatesmain.jsp" },
        { columnName, "templates.jsp?column="+columnID },
        {"增加新模板", ""}
    };
    String[][] operations = {
         {"<input type=image src=../images/button_add.gif onclick='get_src()'>",""},
         {"<input type=image src=../images/button_cancel.gif onclick='javascript:history.back();return false;'>",""}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<p>
<%	// print error messages
    if( !success && errors ) {
%>
        <p><font class=cur>输入有错误。请检查下面字段，再试。</font><p>
<%
    }
%>
<table>
<tr><td>
<form action="readtemplate.jsp" method="post"  name=readForm >
<input type=hidden name=doCreate value=true>
<input type=hidden name=modelfilename value="">
<input type=hidden name=column value="<%=columnID%>">
<input type=radio <%= (isArticle==1)? "checked":""  %> name=isArticle  value=1>文章模板
<input type=radio <%= (isArticle==0)?"checked":"" %> name=isArticle value=0>栏目模板
<input type=hidden name=content value="">
</form>
</table>
</td></tr>
<tr><td ID=bottomofFld colspan=2></td></tr>

<!-- Toolbars -->

<div class="tbToolbar"   ID="StandardToolbar">
  <div class="tbButton" ID="MENU_FILE_OPEN" TITLE="Open File" LANGUAGE="javascript" onclick="return Add_Template_onclick()">
    <img class="tbIcon" src="../images/open.gif" WIDTH="23" HEIGHT="22">
  </div>

  <div class="tbButton" ID="DECMD_CUT" TITLE="剪切" LANGUAGE="javascript" onclick="return DECMD_CUT_onclick()">
    <img class="tbIcon" src="../images/cut.gif" WIDTH="23" HEIGHT="22">
  </div>
  <div class="tbButton" ID="DECMD_COPY" TITLE="复制" LANGUAGE="javascript" onclick="return DECMD_COPY_onclick()">
    <img class="tbIcon" src="../images/copy.gif" WIDTH="23" HEIGHT="22">
  </div>
  <div class="tbButton" ID="DECMD_PASTE" TITLE="粘贴" LANGUAGE="javascript" onclick="return DECMD_PASTE_onclick()">
    <img class="tbIcon" src="../images/paste.gif" WIDTH="23" HEIGHT="22">
  </div>
  <div class="tbButton" ID="DECMD_HYPERLINK" TITLE="超级链接" LANGUAGE="javascript" onclick="return DECMD_HYPERLINK_onclick()">
    <img class="tbIcon" src="../images/link.gif" WIDTH="23" HEIGHT="22">
  </div>
    <div class="tbButton" ID="DECMD_IMAGE" TITLE="上载图片" LANGUAGE="javascript" onclick="return DECMD_IMAGE_onclick()">
    <img class="tbIcon" src="../images/image.gif" WIDTH="23" HEIGHT="22">
  </div>

  <div class="tbSeparator"></div>

  <div class="tbButton" ID="DECMD_UNDO" TITLE="取消" LANGUAGE="javascript" onclick="return DECMD_UNDO_onclick()">
    <img class="tbIcon" src="../images/undo.gif" WIDTH="23" HEIGHT="22">
  </div>
  <div class="tbButton" ID="DECMD_REDO" TITLE="重做" LANGUAGE="javascript" onclick="return DECMD_REDO_onclick()">
    <img class="tbIcon" src="../images/redo.gif" WIDTH="23" HEIGHT="22">
  </div>

  <select ID="MarkName" class="tbGeneral" style="width:340" TITLE="字体" LANGUAGE="javascript" onchange="return MarkName_onchange()">
        <option value=NO_SELECT SELECTED>请选择模板标记</option>
        <option value=ARTICLE_LIST>文章列表</option>
        <option value=ARTICLE_CONTENT>文章内容</option>
        <option value=COLUMN_LIST>栏目列表</option>
        <option value=TOPIC_LIST>专题列表</option>
  </select>
</div>

<!--div ID="submittable" class="submittable" >
	<input type=image src=../images/button_modi.gif onclick="get_src()">&nbsp;
	<input type=image src=../images/button_cancel.gif onclick="javascript:history.back();return false;">
</div-->

<!-- DHTML Editing control Object. This will be the body object for the toolbars. -->

<object ID="tbContentElement" CLASS="tbContentElement"  CLASSID="clsid:2D360201-FFF5-11D1-8D03-00A0C959BC0A" CODEBASE="#Version=6,1,0,8594" VIEWASTEXT  >
  <param name=Scrollbars value=true>
</object>

<!-- unsafe CLASSID="clsid:2D360200-FFF5-11D1-8D03-00A0C959BC0A" -->
<!-- DEInsertTableParam Object -->
<object ID="ObjTableInfo" CLASSID="clsid:47B0DFC7-B7A3-11D1-ADC5-006008A5848C" VIEWASTEXT>
</object>

<!-- DEGetBlockFmtNamesParam Object -->
<object ID="ObjBlockFormatInfo" CLASSID="clsid:8D91090E-B955-11D1-ADC5-006008A5848C" VIEWASTEXT>
</object>

<!-- Toolbar Code File. Note: This must always be the last thing on the page -->
<script LANGUAGE="Javascript" SRC="../toolbars/toolbarsfortemplate.js">
</script>

</body>
</html>