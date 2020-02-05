onerror=displayError;   // Binds error event to "displayError" routine
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
var KeepbizWinkState;

function window_onload() {
  // Initialze QueryStatus tables. These tables associate a command id with the
  // corresponding button object. Must be done on window load, 'cause the buttons must exist.

  QueryStatusToolbarButtons[0] = new QueryStatusItem(DECMD_COPY, document.body.all["DECMD_COPY"]);
  QueryStatusToolbarButtons[1] = new QueryStatusItem(DECMD_CUT, document.body.all["DECMD_CUT"]);
  QueryStatusToolbarButtons[2] = new QueryStatusItem(DECMD_PASTE, document.body.all["DECMD_PASTE"]);
  QueryStatusToolbarButtons[3] = new QueryStatusItem(DECMD_REDO, document.body.all["DECMD_REDO"]);
  QueryStatusToolbarButtons[4] = new QueryStatusItem(DECMD_UNDO, document.body.all["DECMD_UNDO"]);
  QueryStatusToolbarButtons[5] = new QueryStatusItem(DECMD_HYPERLINK, document.body.all["DECMD_HYPERLINK"]);
  QueryStatusToolbarButtons[6] = new QueryStatusItem(DECMD_INSERTTABLE, document.body.all["DECMD_INSERTTABLE"]);
  QueryStatusToolbarButtons[7] = new QueryStatusItem(DECMD_INSERTROW, document.body.all["DECMD_INSERTROW"]);
  QueryStatusToolbarButtons[8] = new QueryStatusItem(DECMD_DELETEROWS, document.body.all["DECMD_DELETEROWS"]);
  QueryStatusToolbarButtons[9] = new QueryStatusItem(DECMD_INSERTCOL, document.body.all["DECMD_INSERTCOL"]);
  QueryStatusToolbarButtons[10] = new QueryStatusItem(DECMD_DELETECOLS, document.body.all["DECMD_DELETECOLS"]);
  QueryStatusToolbarButtons[11] = new QueryStatusItem(DECMD_INSERTCELL, document.body.all["DECMD_INSERTCELL"]);
  QueryStatusToolbarButtons[12] = new QueryStatusItem(DECMD_DELETECELLS, document.body.all["DECMD_DELETECELLS"]);
  QueryStatusToolbarButtons[13] = new QueryStatusItem(DECMD_MERGECELLS, document.body.all["DECMD_MERGECELLS"]);
  QueryStatusToolbarButtons[14] = new QueryStatusItem(DECMD_SPLITCELL, document.body.all["DECMD_SPLITCELL"]);
  QueryStatusToolbarButtons[15] = new QueryStatusItem(DECMD_SETFORECOLOR, document.body.all["DECMD_SETFORECOLOR"]);
  QueryStatusToolbarButtons[16] = new QueryStatusItem(DECMD_SETBACKCOLOR, document.body.all["DECMD_SETBACKCOLOR"]);

  QueryStatusTableMenu[0] = new QueryStatusItem(DECMD_INSERTTABLE, document.body.all["TABLE_INSERTTABLE"]);
  QueryStatusTableMenu[1] = new QueryStatusItem(DECMD_INSERTROW, document.body.all["TABLE_INSERTROW"]);
  QueryStatusTableMenu[2] = new QueryStatusItem(DECMD_DELETEROWS, document.body.all["TABLE_DELETEROW"]);
  QueryStatusTableMenu[3] = new QueryStatusItem(DECMD_INSERTCOL, document.body.all["TABLE_INSERTCOL"]);
  QueryStatusTableMenu[4] = new QueryStatusItem(DECMD_DELETECOLS, document.body.all["TABLE_DELETECOL"]);
  QueryStatusTableMenu[5] = new QueryStatusItem(DECMD_INSERTCELL, document.body.all["TABLE_INSERTCELL"]);
  QueryStatusTableMenu[6] = new QueryStatusItem(DECMD_DELETECELLS, document.body.all["TABLE_DELETECELL"]);
  QueryStatusTableMenu[7] = new QueryStatusItem(DECMD_MERGECELLS, document.body.all["TABLE_MERGECELL"]);
  QueryStatusTableMenu[8] = new QueryStatusItem(DECMD_SPLITCELL, document.body.all["TABLE_SPLITCELL"]);

  // Initialize the context menu arrays.
  GeneralContextMenu[0] = new ContextMenuItem("剪裁", DECMD_CUT);
  GeneralContextMenu[1] = new ContextMenuItem("拷贝", DECMD_COPY);
  GeneralContextMenu[2] = new ContextMenuItem("粘贴", DECMD_PASTE);
  GeneralContextMenu[3] = new ContextMenuItem(MENU_SEPARATOR, 0);
  GeneralContextMenu[4] = new ContextMenuItem("编辑标记属性", DECMD_EDITATTR);
  GeneralContextMenu[5] = new ContextMenuItem("生成HTML碎片", DECMD_ADDHTML);
  GeneralContextMenu[6] = new ContextMenuItem("编辑HTML碎片", DECMD_EDITHTML);
  GeneralContextMenu[7] = new ContextMenuItem(MENU_SEPARATOR, 0);
  GeneralContextMenu[8] = new ContextMenuItem("插入表格", DECMD_INSERTTABLE);

  TableContextMenu[0] = new ContextMenuItem(MENU_SEPARATOR, 0);
  TableContextMenu[1] = new ContextMenuItem("插入行", DECMD_INSERTROW);
  TableContextMenu[2] = new ContextMenuItem("删除行", DECMD_DELETEROWS);
  TableContextMenu[3] = new ContextMenuItem(MENU_SEPARATOR, 0);
  TableContextMenu[4] = new ContextMenuItem("插入列", DECMD_INSERTCOL);
  TableContextMenu[5] = new ContextMenuItem("删除列", DECMD_DELETECOLS);
  TableContextMenu[6] = new ContextMenuItem(MENU_SEPARATOR, 0);
  TableContextMenu[7] = new ContextMenuItem("插入单元格", DECMD_INSERTCELL);
  TableContextMenu[8] = new ContextMenuItem("删除单元个", DECMD_DELETECELLS);
  TableContextMenu[9] = new ContextMenuItem("合并单元个", DECMD_MERGECELLS);
  TableContextMenu[10] = new ContextMenuItem("拆分单元格", DECMD_SPLITCELL);

  //use the BizWinks new object
  KeepbizWinkState=new StateObject();

  docComplete = false;
  return(true);
}

function displayError(msg, url, line){
   // Error handling routine
   alert("发生了以下错误:\n\n" + msg);
   return true;  // Suppresses Internet Explorer error
}
// Constructor for custom object that represents a QueryStatus command and
// corresponding toolbar element.
function QueryStatusItem(command, element) {
  this.command = command;
  this.element = element;
}
//
// Utility functions
//

// Constructor for custom object that represents an item on the context menu
function ContextMenuItem(string, cmdId) {
  this.string = string;
  this.cmdId = cmdId;
}

function tbContentElement_ShowContextMenu() {

  var menuStrings = new Array();
  var menuStates = new Array();
  var state;
  var i
  var idx = 0;
  // Rebuild the context menu.
  ContextMenu.length = 0;

  // Always show general menu

  for (i=0; i<GeneralContextMenu.length; i++) {
    ContextMenu[idx++] = GeneralContextMenu[i];
  }

  // Is the selection inside a table? Add table menu if so

  if (tbContentElement.QueryStatus(DECMD_INSERTROW) != DECMDF_DISABLED) {
    for (i=0; i<TableContextMenu.length; i++) {
      ContextMenu[idx++] = TableContextMenu[i];
    }
  }

  // Set up the actual arrays that get passed to SetContextMenu
  for (i=0; i<ContextMenu.length; i++) {
    menuStrings[i] = ContextMenu[i].string;
    if ((menuStrings[i] != MENU_SEPARATOR) && (menuStrings[i] !="编辑标记属性") && (menuStrings[i] != "生成HTML碎片") && (menuStrings[i] != "编辑HTML碎片")) {
      state = tbContentElement.QueryStatus(ContextMenu[i].cmdId);
    } else {
      state = DECMDF_ENABLED;
    }

    if (state == DECMDF_DISABLED || state == DECMDF_NOTSUPPORTED) {
      menuStates[i] = OLE_TRISTATE_GRAY;
    } else if (state == DECMDF_ENABLED || state == DECMDF_NINCHED) {
      menuStates[i] = OLE_TRISTATE_UNCHECKED;
    } else { // DECMDF_LATCHED
      menuStates[i] = OLE_TRISTATE_CHECKED;
    }
  }
  // Set the context menu
  tbContentElement.SetContextMenu(menuStrings, menuStates);
}

function tbContentElement_ContextMenuAction(itemIndex){

  if (ContextMenu[itemIndex].cmdId == DECMD_EDITATTR){
  	DECMD_EDITATTR_onclick();
  }
  else if (ContextMenu[itemIndex].cmdId == DECMD_EDITHTML) {
  	DECMD_EDITHTML_onclick();
  }
  else if (ContextMenu[itemIndex].cmdId == DECMD_ADDHTML) {
  	DECMD_ADDHTML_onclick();
  } else {
	  if (ContextMenu[itemIndex].cmdId == DECMD_INSERTTABLE) {
	    InsertTable();
	  } else {
	    tbContentElement.ExecCommand(ContextMenu[itemIndex].cmdId, OLECMDEXECOPT_DODEFAULT);
	  }
  }
}

// DisplayChanged handler. Very time-critical routine; this is called
// every time a character is typed. QueryStatus those toolbar buttons that need
// to be in synch with the current state of the document and update.
function tbContentElement_DisplayChanged() {
  var i, s;

  for (i=0; i<QueryStatusToolbarButtons.length; i++) {
    s = tbContentElement.QueryStatus(QueryStatusToolbarButtons[i].command);
    if (s == DECMDF_DISABLED || s == DECMDF_NOTSUPPORTED) {
      TBSetState(QueryStatusToolbarButtons[i].element, "gray");
    } else if (s == DECMDF_ENABLED  || s == DECMDF_NINCHED) {
       TBSetState(QueryStatusToolbarButtons[i].element, "unchecked");
    } else { // DECMDF_LATCHED
       TBSetState(QueryStatusToolbarButtons[i].element, "checked");
    }
  }

}
function DECMD_EDITATTR_onclick(){
	sel =tbContentElement.DOM.selection;
	if ( "Text" == sel.type ){
		range = sel.createRange();
		var selStr = "\"" + range.htmlText + "\"";
		var winStr = "../toolbars/test.jsp?sel=" + selStr;
		  returnvalue = showModalDialog( winStr,"",
                         "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
		alert( range.htmlText );
	}
	else
		alert( "No text selected" );
}

function DECMD_HYPERLINK_onclick()
{
  winStr = "addlink.jsp";
  var returnvalue = showModalDialog( winStr,"","font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em");
  if ((returnvalue != "undefined") && (returnvalue != null) && (returnvalue != ""))
  {
    setFormat("CreateLink", returnvalue);
  }
}

function DECMD_HYPERLINK_onclick(rightid,columnID) {
	DHTMLSafe = eval("document.all.tbContentElement");
	DHTMLSafe.focus();
	DHTMLSafe.DOM.body.focus();
	if(DHTMLSafe.DOM.selection.type=="Control") {
		var el=DHTMLSafe.DOM.selection.createRange().commonParentElement();
		var tr = DHTMLSafe.DOM.body.createTextRange();
		tr.moveToElementText(el);
		tr.select();
	}
	if (typeof(ae_linkwin) == "undefined" || ae_linkwin.closed) { //short circuit eval
		var szURL="addlink.jsp?rightid" + rightid +"&column=" + columnID;
		ae_linkwin = window.open(szURL ,"ae_linkwin","scrollbars=auto,width=660,height=390, resizable=yes",true);
	}
	ae_linkwin.focus();
}

function ae_hyperlink(num, iHref, iTarget, iStyle, iClass, iName) {
	var DHTMLSafe = eval("document.all.tbContentElement");
	var uid="ae"+Math.random().toString();
	if(iHref=="" && !iName.length) { // Unlink
		if(DHTMLSafe.QueryStatus(DECMD_UNLINK)==DECMDF_ENABLED)
			DHTMLSafe.ExecCommand(DECMD_UNLINK);
	}else {
		var trSel=DHTMLSafe.DOM.selection.createRange();
		if(trSel.compareEndPoints("StartToEnd",trSel)==0) { // Need a brand new link
			txtHTML="<A href="+iHref+" ";
			if(iTarget.length)
				txtHTML+="target='"+iTarget+"' ";
			if(iStyle.length)
				txtHTML+="style='"+iStyle+"' ";
			if(iClass.length)
				txtHTML+="class='"+iClass+"' ";
			if(iName.length)
				txtHTML+="name='"+iName+"' ";
			txtHTML+=">"+iHref+"";
			trSel.pasteHTML(txtHTML);
		} else { //Update existing link or link plain text
			DHTMLSafe.ExecCommand(DECMD_HYPERLINK,OLECMDEXECOPT_DONTPROMPTUSER,uid);
			var coll=DHTMLSafe.DOM.all.tags("A");
			for(i=0;i<coll.length;i++) {
				if(coll[i].href==uid) {
					coll[i].href=iHref;
					if(iTarget.length) coll[i].target=iTarget;
					else coll[i].removeAttribute("TARGET",0);

					if(iStyle.length) coll[i].style.cssText=iStyle;
					else {
						//coll[i].style.cssText="";
					}
					if(iClass.length) coll[i].className=iClass;
					else {
						//coll[i].className="";
					}
				}
			}
		}
	}
	DHTMLSafe.focus();
	DHTMLSafe.DOM.body.focus();
}

function DECMD_EDITHTML_onclick(){
	alert( "No text selected here " );

}

function DECMD_ADDHTML_onclick(){
        setFormat("ADDHTMLMARK");
}

function MarkName_Add(){
    sel = tbContentElement.DOM.selection.createRange();
    var returnvalue=null;

    if (MarkName.value == "ARTICLE_LIST") {
        winStr = "addArticleList.jsp";
        returnvalue = showModalDialog( winStr,"",
                                       "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:58em");
    }

    else if(MarkName.value == "ARTICLE_CONTENT") {
        returnvalue = "[TAG][ARTICLE_CONTENT][/ARTICLE_CONTENT][/TAG]";
    }

    else if(MarkName.value == "ARTICLE_MAINTITLE") {
        returnvalue = "[TAG][ARTICLE_MAINTITLE][/ARTICLE_MAINTITLE][/TAG]";
    }

    else if(MarkName.value == "ARTICLE_VICETITLE") {
        returnvalue = "[TAG][ARTICLE_VICETITLE][/ARTICLE_VICETITLE][/TAG]";
    }

    else if(MarkName.value == "ARTICLE_PT") {
        returnvalue = "[TAG][ARTICLE_PT][/ARTICLE_PT][/TAG]"
    }

    else if(MarkName.value == "ARTICLE_AUTHOR") {
        returnvalue = "[TAG][ARTICLE_AUTHOR][/ARTICLE_AUTHOR][/TAG]";
    }

    else if(MarkName.value == "ARTICLE_SUMMARY") {
        returnvalue = "[TAG][ARTICLE_SUMMARY][/ARTICLE_SUMMARY][/TAG]";
    }

    else if(MarkName.value == "ARTICLE_SOURCE") {
        returnvalue = "[TAG][ARTICLE_SOURCE][/ARTICLE_SOURCE][/TAG]";
    }

    else if(MarkName.value == "COLUMN_LIST") {
        winStr = "addColumnList.jsp";
        returnvalue = showModalDialog( winStr,"",
                                       "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
    }
    else if(MarkName.value == "SUBCOLUMN_LIST") {
        winStr = "addColumnList.jsp";
        returnvalue = showModalDialog( winStr,"",
                                       "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
    }
    else if(MarkName.value == "CHINESE_PATH") {
        returnvalue = "[TAG][CHINESE_PATH][/CHINESE_PATH][/TAG]";
    }
    else if(MarkName.value == "ENGLISH_PATH") {
        returnvalue = "[TAG][ENGLISH_PATH][/ENGLISH_PATH][/TAG]";
    }
    else if(MarkName.value == "RELATED_ARTICLE") {
        winStr = "addRelateList.jsp";
        returnvalue = showModalDialog( winStr,"",
                                       "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:33em");
    }
    else if(MarkName.value == "TOPIC_LIST") {
        returnvalue = "[TAG][TOPIC_LIST][/TOPIC_LIST][/TAG]";
    }
    else if(MarkName.value == "ARTICLE_COMMENT") {
        var tempStr = document.all("tempURL").value;
        var tempStr1 = tempStr.substring(0, tempStr.indexOf("webbuilder")+11);
        var tempStr2 = tempStr.substring(tempStr.indexOf("-")+1, tempStr.length);
        returnvalue = "<a href=\"#\" onclick=\"javascript:window.open('"+tempStr1+"toolkit/comment/index.jsp?siteID="+tempStr2+"&href='+self.location,'comment_window','left=0,top=0,width=600,height=450,scrollbars=yes,status=yes')\">文章评论</a>";
    }
    else if(MarkName.value == "ARTICLE_COMMEND") {
        var tempStr = document.all("tempURL").value;
        var tempStr1 = tempStr.substring(0, tempStr.indexOf("webbuilder")+11);
        var tempStr2 = tempStr.substring(tempStr.indexOf("-")+1, tempStr.length);
        returnvalue = "<a href=\"#\" onclick=\"javascript:window.open('"+tempStr1+"toolkit/commend/index.jsp?siteID="+tempStr2+"&href='+self.location,'commend_window','left=200,top=150,width=400,height=200,scrollbars=yes')\">文章推荐</a>";
    }
    else if(MarkName.value == "MESSAGE_BOARD") {
        var tempStr = document.all("tempURL").value;
        var tempStr1 = tempStr.substring(0, tempStr.indexOf("webbuilder")+11);
        var tempStr2 = tempStr.substring(tempStr.indexOf("-")+1, tempStr.length);
        returnvalue = "<a href=\"#\" onclick=\"javascript:window.open('"+tempStr1+"toolkit/word/write.jsp?'"+tempStr2+",'word_window','left=100,top=100,width=600,height=420,scrollbars=yes')\">留言簿</a>";
    }
    else if(MarkName.value == "SITE_COUNTER") {
        var tempStr = document.all("tempURL").value;
        var tempStr1 = tempStr.substring(0, tempStr.indexOf("webbuilder")+11);
        var tempStr2 = tempStr.substring(tempStr.indexOf("-")+1, tempStr.length);
        returnvalue = "<img src='"+tempStr1+"toolkit/counter/index.jsp?"+tempStr2+"' border=0>";
    }else if (MarkName.value == "TOP_STORIES") {
        winStr = "../templatex/topStories.html";
        returnvalue = showModalDialog( winStr,"",
                                       "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:58em");
    }
    else if (MarkName.value == "ARTICLE_PROPERTIES") {
        winStr = "../templatex/articleAttribute.html";
        returnvalue = showModalDialog( winStr,"",
                                       "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:58em");
    }

    MarkName.value = "NO_SELECT";

    if (returnvalue != null) {
        var addMark = returnvalue;
        sel.pasteHTML(addMark);
    }
}

function MENU_FILE_OPEN_onclick(par) {
    returnvalue = showModalDialog( "../template/LoadModel.jsp" + par,"",
                         "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
}

function Add_Template_onclick(par) {
    returnvalue = showModalDialog( "../template/selectModel.jsp?column=" + par,"",
                         "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
    
    if (returnvalue != undefined && returnvalue != "")
    {
      	createForm.modelfilename.value = returnvalue;
      	createForm.doCreate.value = false;
      	createForm.submit();
    }
}

function MENU_FILE_SAVE_onclick() {

   alert("test");
 /* if (tbContentElement.CurrentDocumentPath != "") {
    var path;

    path = tbContentElement.CurrentDocumentPath;

    if (path.substring(0, 7) == "http://" || path.substring(0, 7) == "file://")
      tbContentElement.SaveDocument("", true);
    else
      tbContentElement.SaveDocument(tbContentElement.CurrentDocumentPath, false);
  }
  else {
    tbContentElement.SaveDocument("", true);
  }

  URL_VALUE.value = tbContentElement.CurrentDocumentPath;

  tbContentElement.focus();  */
}

function DECMD_UNDO_onclick() {
  tbContentElement.ExecCommand(DECMD_UNDO,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_REDO_onclick() {
  tbContentElement.ExecCommand(DECMD_REDO,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_PASTE_onclick() {
  tbContentElement.ExecCommand(DECMD_PASTE,OLECMDEXECOPT_DODEFAULT);
}

function DECMD_DELETE_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETE,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_CUT_onclick() {
  tbContentElement.ExecCommand(DECMD_CUT,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_COPY_onclick() {
  tbContentElement.ExecCommand(DECMD_COPY,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_BOLD_onclick() {
  tbContentElement.ExecCommand(DECMD_BOLD,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_OUTDENT_onclick() {
  tbContentElement.ExecCommand(DECMD_OUTDENT,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_ORDERLIST_onclick() {
  tbContentElement.ExecCommand(DECMD_ORDERLIST,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_JUSTIFYRIGHT_onclick() {
  tbContentElement.ExecCommand(DECMD_JUSTIFYRIGHT,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_JUSTIFYLEFT_onclick() {
  tbContentElement.ExecCommand(DECMD_JUSTIFYLEFT,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_JUSTIFYCENTER_onclick() {
  tbContentElement.ExecCommand(DECMD_JUSTIFYCENTER,OLECMDEXECOPT_DODEFAULT);

}


function DECMD_ITALIC_onclick() {
  tbContentElement.ExecCommand(DECMD_ITALIC,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_INDENT_onclick() {
  tbContentElement.ExecCommand(DECMD_INDENT,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_UNDERLINE_onclick() {
  tbContentElement.ExecCommand(DECMD_UNDERLINE,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_VISIBLEBORDERS_onclick() {
  tbContentElement.ShowBorders = !tbContentElement.ShowBorders;

}

function DECMD_UNORDERLIST_onclick() {
  tbContentElement.ExecCommand(DECMD_UNORDERLIST,OLECMDEXECOPT_DODEFAULT);
}

function OnMenuShow(QueryStatusArray, menu) {
  var i, s;

  for (i=0; i<QueryStatusArray.length; i++) {
    s = tbContentElement.QueryStatus(QueryStatusArray[i].command);
    if (s == DECMDF_DISABLED || s == DECMDF_NOTSUPPORTED) {
      TBSetState(QueryStatusArray[i].element, "gray");
    } else if (s == DECMDF_ENABLED  || s == DECMDF_NINCHED) {
       TBSetState(QueryStatusArray[i].element, "unchecked");
    } else { // DECMDF_LATCHED
       TBSetState(QueryStatusArray[i].element, "checked");
    }
  }
  // rebuild the menu so that menu item states will be reflected
  //TBRebuildMenu(menu, true);
}

function TOOLBARS_onclick(toolbar, menuItem) {
  if (toolbar.TBSTATE == "hidden") {
    TBSetState(toolbar, "dockedTop");
    TBSetState(menuItem, "checked");
  } else {
    TBSetState(toolbar, "hidden");
    TBSetState(menuItem, "unchecked");
  }

  TBRebuildMenu(menuItem.parentElement, false);


}

function get_src(formname)
{
  if (tbContentElement.Busy)
  {
    alert("模版初始化还未完成，请稍后提交");
    tbContentElement_DocumentComplete();
    return;
  }

  if (eval(formname).modelSourceCodeFlag.value == 1) 
  {
    alert("在浏览源码文件时不能保存模板，请返回到图形模式保存模板文件！！！");
    return;
  }

  var tempVar = tbContentElement.DocumentHTML;
  var regexp1 = /textarea/ig;
  tempVar = tempVar.replace(regexp1, "cmstextarea");

  var imgs = tbContentElement.DOM.body.all.tags("IMG");
  if (imgs != null)
  {
    var lens = imgs.length;
    var uploadURL = "<%=URL%>";
    var j = 0;
    var tmp;
    while (j < lens) 
    {
      doc = imgs[j].src;
      tmp = imgs[j].alttag;
      doc = doc.replace("file://","");
      if (tmp != "" && tmp != null) 
      {
	tempVar = tempVar.replace("alttag="+tmp,"");
	tempVar = tempVar.replace(doc,tmp);
      }
      j++;
    }
  }
  
  if (eval(formname).cname.value == "")
  {
    alert("请输入模板中文名称！");
    return;
  }
  
  eval(formname).content.value = tempVar;
  eval(formname).submit();
  return true;
}


//add by Jermy
function get_src_product(formname, type)
{

   if (tbContentElement.Busy == true)
   {
      alert("模版初始化还未完成，请稍后提交");
      tbContentElement_DocumentComplete();
      return;
   }
   
   if (eval(formname).modelSourceCodeFlag.value == 1) 
   {
      alert("在浏览源码文件时不能保存模板，请返回到图形模式保存模板文件！！！");
      return;
   }
   
   var tempVar = tbContentElement.DocumentHTML;
   var regexp1 = /textarea/ig;
   tempVar = tempVar.replace(regexp1,"cmstextarea");
   
   var imgs = tbContentElement.DOM.body.all.tags("IMG");
   if (imgs !=null) 
   {
      var lens = imgs.length;
      var uploadURL = "<%=URL%>";
      var j = 0;
      var tmp;
      while (j < lens) 
      {
	 doc = imgs[j].src;
	 tmp = imgs[j].alttag;
	 doc = doc.replace("file://","");
	 if ((tmp !="") &&(tmp !=null)) 
	 {
	     tempVar =tempVar.replace("alttag="+tmp,"");
	     tempVar = tempVar.replace(doc,tmp);
	 }
	 j++;
      }
   }
   
   if (type == 1 || type == "1")
   {
      var subname = eval(formname).subjectName.value;
      var subpath = eval(formname).subjectPath.value;
      if (subname == "" || subname.indexOf(".") < 1 || subname.lastIndexOf(".") == subname.length-1)
      {
          alert("专题模板文件名格式不正确！");
          return;
      }
      else if (subpath == "")
      {
          alert("专题模板发布路径不能为空！");
          return;
      }
   }
   
   eval(formname).content.value = tempVar;
   eval(formname).submit();
   return true;
}


//THis added by zhangyong on oct 13
function setFormat(command, value) 
{
  var sel=KeepbizWinkState.GetSelection();
  var type=sel.type;
  var target = (type == "None" ? tbContentElement.DOM : sel)
  var selHTML ="";
  if ( "Text" ==type ){
     selHTML=target.parentElement().outerHTML;
   }
   if ("Control"  == type ){
    selHTML=target.commonParentElement().outerHTML;
   }

  if (command =="ADDHTMLMARK" ){
      if (selHTML !="") {
      selHTML=clearQuoteInHTMLTag(selHTML)
      savewindow=window.open("EditBizMark.jsp?Markvalue="+selHTML,'savewindow');
    //  returnvalue = showModalDialog("EditBizMark.jsp?Markvalue='"+selHTML+"'","",
     //                    "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
      }
  }else{
    target.execCommand(command, false, value);
  }
  KeepbizWinkState.RestoreSelection();
  return true;
}

//
// KeepbizWinkState object

function StateObject() {
  this.name='KeepbizWinkState';
  this.selection=null;
  this.GetSelection=state_getSelection;
  this.SaveSelection=state_saveSelection;
  this.RestoreSelection=state_restoreSelection;
}

function state_getSelection() {
  var sel=this.selection;
  if (!sel) {
    sel=tbContentElement.DOM.selection.createRange();
    sel.type=tbContentElement.DOM.selection.type;
  }
  return sel;
}

function state_saveSelection() {
  this.selection=tbContentElement.DOM.selection.createRange();
  if (!this.selection || (this.selection.parentElement && this.selection.parentElement() &&
         !(this.selection.parentElement() == tbContentElement.DOM.body || tbContentElement.DOM.body.contains(this.selection.parentElement() ) ) ) ) {
    this.selection=tbContentElement.DOM.body.createTextRange();
    this.selection.collapse(false);
    this.selection.type="None";
  } else {
    this.selection.type=tbContentElement.DOM.selection.type;
  }

}

function state_restoreSelection() {
  if (this.selection) {
    this.selection.select();
  }
}

function setSelection(dir) {
  var tr=tbContentElement.DOM.createTextRange();
  tr.collapse(dir);
  tr.select();
  KeepbizWinkState.SaveSelection();
}

function getBlock(el) {
  var BlockElements="|H1|H2|H3|H4|H5|H6|P|PRE|LI|TD|DIV|BLOCKQUOTE|DT|DD|TABLE|HR|IMG|";
  while ((el!=null) && (BlockElements.indexOf("|"+el.tagName+"|")==-1)) {
    el=el.parentElement;
  }
  return el;
}

function clearQuoteInHTMLTag(htmlContent){
      var regexp = /<([^<>"]*)?([^<>]*)?>/g;
      var rtnAry = htmlContent.match(regexp);

      if (rtnAry !=null) {
	 var len = rtnAry.length;
	 var regexp1 = /[%]+/g;
	 var regexp2 = /[\"]+/g;
         var regexp3 = /[#]+/g;
         var regexp4 = /[&]+/g;


	 var i = 0;
	 while (i < len) {
	    temp = rtnAry[i].replace(regexp1,"%25");
            temp = temp.replace(regexp2,"%22");
            temp = temp.replace(regexp3,"%23");
            temp = temp.replace(regexp4,"%26");
	    htmlContent = htmlContent.replace(rtnAry[i],temp);
	    i++;
	 }
      }
      return htmlContent;
}

function TABLE_DELETECELL_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETECELLS,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_DELETECOL_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETECOLS,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_DELETEROW_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETEROWS,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_INSERTCELL_onclick() {
  tbContentElement.ExecCommand(DECMD_INSERTCELL,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_INSERTCOL_onclick() {
  tbContentElement.ExecCommand(DECMD_INSERTCOL,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_INSERTROW_onclick() {
  tbContentElement.ExecCommand(DECMD_INSERTROW,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_INSERTTABLE_onclick() {
  InsertTable();
  tbContentElement.focus();
}

function TABLE_MERGECELL_onclick() {
  tbContentElement.ExecCommand(DECMD_MERGECELLS,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function TABLE_SPLITCELL_onclick() {
  tbContentElement.ExecCommand(DECMD_SPLITCELL,OLECMDEXECOPT_DODEFAULT);
  tbContentElement.focus();
}

function DECMD_SETFORECOLOR_onclick() {
  var arr = showModalDialog( "/webbuilder/toolbars/selcolor.htm",
                             "",
                             "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:30em; status:no" );

  if (arr != null) {
    tbContentElement.ExecCommand(DECMD_SETFORECOLOR,OLECMDEXECOPT_DODEFAULT, arr);
  }
}

function DECMD_SETBACKCOLOR_onclick() {
  var arr = showModalDialog( "/webbuilder/toolbars/selcolor.htm",
                             "",
                             "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:30em; status:no" );

  if (arr != null) {
    tbContentElement.ExecCommand(DECMD_SETBACKCOLOR,OLECMDEXECOPT_DODEFAULT, arr);
  }
  tbContentElement.focus();
}

function FontName_onchange() {
  tbContentElement.ExecCommand(DECMD_SETFONTNAME, OLECMDEXECOPT_DODEFAULT, FontName.value);
  tbContentElement.focus();
}

function FontSize_onchange() {
  tbContentElement.ExecCommand(DECMD_SETFONTSIZE, OLECMDEXECOPT_DODEFAULT, parseInt(FontSize.value));
  tbContentElement.focus();
}
function InsertTable() {
  var pVar = ObjTableInfo;
  var args = new Array();
  var arr = null;

  // Display table information dialog
  args["NumRows"] = ObjTableInfo.NumRows;
  args["NumCols"] = ObjTableInfo.NumCols;
  args["TableAttrs"] = ObjTableInfo.TableAttrs;
  args["CellAttrs"] = ObjTableInfo.CellAttrs;
  args["Caption"] = ObjTableInfo.Caption;

  arr = null;

  arr = showModalDialog( "/webbuilder/toolbars/instable.htm",
                             args,
                             "font-family:Verdana; font-size:12; dialogWidth:36em; dialogHeight:25em; status:no");
  if (arr != null) {

    // Initialize table object
    for ( elem in arr ) {
      if ("NumRows" == elem && arr["NumRows"] != null) {
        ObjTableInfo.NumRows = arr["NumRows"];
      } else if ("NumCols" == elem && arr["NumCols"] != null) {
        ObjTableInfo.NumCols = arr["NumCols"];
      } else if ("TableAttrs" == elem) {
        ObjTableInfo.TableAttrs = arr["TableAttrs"];
      } else if ("CellAttrs" == elem) {
        ObjTableInfo.CellAttrs = arr["CellAttrs"];
      } else if ("Caption" == elem) {
        ObjTableInfo.Caption = arr["Caption"];
      }
    }

    tbContentElement.ExecCommand(DECMD_INSERTTABLE,OLECMDEXECOPT_DODEFAULT, pVar);
  }
}


function HTTPEscape (a) {
	a = escape(a);
	a = replaceJS(a,'+','%2B');
	a = replaceJS(a,'%20','+');
	a = replaceJS(a,'%A1','%B0');
	a = replaceJS(a,'%80','%C4');
	a = replaceJS(a,'%81','%C5');
	a = replaceJS(a,'%82','%C7');
	a = replaceJS(a,'%83','%C9');
	a = replaceJS(a,'%84','%D1');
	a = replaceJS(a,'%85','%D6');
	a = replaceJS(a,'%86','%DC');
	a = replaceJS(a,'%87','%E1');
	a = replaceJS(a,'%88','%E0');
	a = replaceJS(a,'%89','%E2');
	a = replaceJS(a,'%8A','%E4');
	a = replaceJS(a,'%8B','%E3');
	a = replaceJS(a,'%8C','%E5');
	a = replaceJS(a,'%8D','%E7');
	a = replaceJS(a,'%8E','%E9');
	a = replaceJS(a,'%8F','%E8');
	a = replaceJS(a,'%90','%EA');
	a = replaceJS(a,'%91','%EB');
	a = replaceJS(a,'%92','%ED');
	a = replaceJS(a,'%93','%EC');
	a = replaceJS(a,'%94','%EE');
	a = replaceJS(a,'%95','%EF');
	a = replaceJS(a,'%96','%F1');
	a = replaceJS(a,'%97','%F3');
	a = replaceJS(a,'%98','%F2');
	a = replaceJS(a,'%99','%F4');
	a = replaceJS(a,'%9A','%F6');
	a = replaceJS(a,'%9B','%F5');
	a = replaceJS(a,'%9C','%FA');
	a = replaceJS(a,'%9D','%F9');
	a = replaceJS(a,'%9E','%FB');
	a = replaceJS(a,'%9F','%FC');

	return a
}

function replaceJS (data,find,replace)  {
	var flen = find.length;
	var dlen = data.length;
	for (i=0; i<dlen; i++) {
		var j = i + flen;
		if (data.substring(i,j) == find)  {
			data = data.substring(0,i) + replace + data.substring(j,dlen);
		}
	}
	return data
}

function DECMD_FINDTEXT_onclick() {
  tbContentElement.ExecCommand(DECMD_FINDTEXT,OLECMDEXECOPT_PROMPTUSER);

}

function DECMD_REPLACETEXT_onclick() {
   var arr = null;
   var source=null;
   var target =null;
   arr = showModalDialog( "../toolbars/replace.htm",
                             "",
                             "font-family:Verdana; font-size:12; dialogWidth:20em; dialogHeight:13em");
  if (arr != null) {
  	  if (arr["para1"] != null) {
	    source = arr["para1"];
	  }
	 if (arr["para2"] != null) {
	    target = arr["para2"];
	  }
     var srctext  = tbContentElement.DocumentHTML;
     findit=false;
     var targettext = replaceJS(srctext,source,target);
     if (findit) {
    	 tbContentElement.DocumentHTML = targettext;
         tbContentElement_DocumentComplete();
     }
	}

}

function VIEW_HTML_onclick(menuItem,formname) {
	tbContentElement.DOM.selection.empty();
	if (menuItem.TBSTATE=="checked") {
			  var re=/((<br>)+)/ig;
			  var sContents=tbContentElement.DocumentHTML;
			tbContentElement.DOM.body.innerHTML = "";
			  tbContentElement.DOM.body.createTextRange().text = sContents;
			TBSetState(menuItem, "unchecked");
			  ToolbarTableState=TableToolbar.TBSTATE;
			  TBSetState(TableToolbar, "hidden");
			eval(formname).modelSourceCodeFlag.value = 1;
	  } else {
			  var sContents=tbContentElement.DOM.body.innerText;
			sContent = tbContentElement.FilterSourceCode(sContents);
			  tbContentElement.DocumentHTML=sContents
			TBSetState(menuItem, "checked");
			TBSetState(TableToolbar, ToolbarTableState);
			eval(formname).modelSourceCodeFlag.value = 0;
	  }

          tbContentElement_DocumentComplete();
	  TBRebuildMenu(menuItem.parentElement, true);
}

function checkoption(para,para1) {
  /*<option value=NO_SELECT>选择标记</option>
  */
  //用于记录该种莫版中标记的个数
  var selectlens = new Array;
  selectlens[0] = 6;
  selectlens[1] = 12;
  //用于存储option 中 option控件的显示名称
  var optionsname = new Array();
  //用于存储option中option的value
  var optionsvalue=new Array();

  optionsname[0] = new Array();
  optionsname[0][0] ="选择标记";
  optionsname[0][1] ="文章列表";
  optionsname[0][2] ="栏目列表";
  optionsname[0][3] ="中文路径";
  optionsname[0][4] ="英文路径";
  optionsname[0][5] ="热点文章";
  optionsname[0][6] ="文章属性";

  optionsvalue[0] = new Array();
  optionsvalue[0][0] ="NO_SELECT";
  optionsvalue[0][1] ="ARTICLE_LIST";
  optionsvalue[0][2] ="COLUMN_LIST";
  optionsvalue[0][3] ="CHINESE_PATH";
  optionsvalue[0][4] ="ENGLISH_PATH";
  optionsvalue[0][5] ="TOP_STORIES";
  optionsvalue[0][6] ="ARTICLE_PROPERTIES";


  optionsname[1] = new Array();
  optionsname[1][0] ="选择标记";
  optionsname[1][1] ="文章列表";
  optionsname[1][2] ="文章内容";
  optionsname[1][3] ="文章标题";
  optionsname[1][4] ="副标题";
  optionsname[1][5] ="文章作者";
  optionsname[1][6] ="发布时间";
  optionsname[1][7] ="文章概述";
  optionsname[1][8] ="文章来源";
  optionsname[1][9] ="栏目列表";
  optionsname[1][10] ="中文路径";
  optionsname[1][11] ="英文路径";
  optionsname[1][12] ="相关文章";

  optionsvalue[1] = new Array();
  optionsvalue[1][0] ="NO_SELECT";
  optionsvalue[1][1] ="ARTICLE_LIST";
  optionsvalue[1][2] ="ARTICLE_CONTENT";
  optionsvalue[1][3] ="ARTICLE_MAINTITLE";
  optionsvalue[1][4] ="ARTICLE_VICETITLE";
  optionsvalue[1][5] ="ARTICLE_AUTHOR";
  optionsvalue[1][6] ="ARTICLE_PT";
  optionsvalue[1][7] ="ARTICLE_SUMMARY";
  optionsvalue[1][8] ="ARTICLE_SOURCE";
  optionsvalue[1][9] ="COLUMN_LIST";
  optionsvalue[1][10] ="CHINESE_PATH";
  optionsvalue[1][11] ="ENGLISH_PATH";
  optionsvalue[1][12] ="RELATED_ARTICLE";

  var param = parseInt(para);
  if (param == 2) param =0;
  MarkName.length =0;
  for(x=0;x<=selectlens[param];x++){
	oOption= document.createElement("OPTION");
        oOption.text=optionsname[param][x];
        oOption.value=optionsvalue[param][x];
	MarkName.add(oOption);
  }
}
