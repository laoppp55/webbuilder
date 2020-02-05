var DHTMLSafe = eval("document.all.tbContentElement");
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
        QueryStatusToolbarButtons[0] = new QueryStatusItem(DECMD_BOLD, document.body.all["DECMD_BOLD"]);
        QueryStatusToolbarButtons[1] = new QueryStatusItem(DECMD_COPY, document.body.all["DECMD_COPY"]);
        QueryStatusToolbarButtons[2] = new QueryStatusItem(DECMD_CUT, document.body.all["DECMD_CUT"]);
        QueryStatusToolbarButtons[3] = new QueryStatusItem(DECMD_HYPERLINK, document.body.all["DECMD_HYPERLINK"]);
        QueryStatusToolbarButtons[4] = new QueryStatusItem(DECMD_INDENT, document.body.all["DECMD_INDENT"]);
        QueryStatusToolbarButtons[5] = new QueryStatusItem(DECMD_ITALIC, document.body.all["DECMD_ITALIC"]);
        QueryStatusToolbarButtons[6] = new QueryStatusItem(DECMD_JUSTIFYLEFT, document.body.all["DECMD_JUSTIFYLEFT"]);
        QueryStatusToolbarButtons[7] = new QueryStatusItem(DECMD_JUSTIFYCENTER, document.body.all["DECMD_JUSTIFYCENTER"]);
        QueryStatusToolbarButtons[8] = new QueryStatusItem(DECMD_JUSTIFYRIGHT, document.body.all["DECMD_JUSTIFYRIGHT"]);
        QueryStatusToolbarButtons[9] = new QueryStatusItem(DECMD_ORDERLIST, document.body.all["DECMD_ORDERLIST"]);
        QueryStatusToolbarButtons[10] = new QueryStatusItem(DECMD_OUTDENT, document.body.all["DECMD_OUTDENT"]);
        QueryStatusToolbarButtons[11] = new QueryStatusItem(DECMD_PASTE, document.body.all["DECMD_PASTE"]);
        QueryStatusToolbarButtons[12] = new QueryStatusItem(DECMD_REDO, document.body.all["DECMD_REDO"]);
        QueryStatusToolbarButtons[13] = new QueryStatusItem(DECMD_UNDERLINE, document.body.all["DECMD_UNDERLINE"]);
        QueryStatusToolbarButtons[14] = new QueryStatusItem(DECMD_UNDO, document.body.all["DECMD_UNDO"]);
        QueryStatusToolbarButtons[15] = new QueryStatusItem(DECMD_UNORDERLIST, document.body.all["DECMD_UNORDERLIST"]);
        QueryStatusToolbarButtons[16] = new QueryStatusItem(DECMD_INSERTTABLE, document.body.all["DECMD_INSERTTABLE"]);
        QueryStatusToolbarButtons[17] = new QueryStatusItem(DECMD_INSERTROW, document.body.all["DECMD_INSERTROW"]);
        QueryStatusToolbarButtons[18] = new QueryStatusItem(DECMD_DELETEROWS, document.body.all["DECMD_DELETEROWS"]);
        QueryStatusToolbarButtons[19] = new QueryStatusItem(DECMD_INSERTCOL, document.body.all["DECMD_INSERTCOL"]);
        QueryStatusToolbarButtons[20] = new QueryStatusItem(DECMD_DELETECOLS, document.body.all["DECMD_DELETECOLS"]);
        QueryStatusToolbarButtons[21] = new QueryStatusItem(DECMD_INSERTCELL, document.body.all["DECMD_INSERTCELL"]);
        QueryStatusToolbarButtons[22] = new QueryStatusItem(DECMD_DELETECELLS, document.body.all["DECMD_DELETECELLS"]);
        QueryStatusToolbarButtons[23] = new QueryStatusItem(DECMD_MERGECELLS, document.body.all["DECMD_MERGECELLS"]);
        QueryStatusToolbarButtons[24] = new QueryStatusItem(DECMD_SPLITCELL, document.body.all["DECMD_SPLITCELL"]);
        QueryStatusToolbarButtons[25] = new QueryStatusItem(DECMD_SETFORECOLOR, document.body.all["DECMD_SETFORECOLOR"]);
        QueryStatusToolbarButtons[26] = new QueryStatusItem(DECMD_SETBACKCOLOR, document.body.all["DECMD_SETBACKCOLOR"]);
        // Initialize the context menu arrays.
		GeneralContextMenu[0] = new ContextMenuItem("剪裁", DECMD_CUT);
		GeneralContextMenu[1] = new ContextMenuItem("拷贝", DECMD_COPY);
		GeneralContextMenu[2] = new ContextMenuItem("粘贴", DECMD_PASTE);
		GeneralContextMenu[3] = new ContextMenuItem(MENU_SEPARATOR, 0);
		GeneralContextMenu[4] = new ContextMenuItem("插入表格", DECMD_INSERTTABLE);

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


        docComplete = false;
        return(true);
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
        arr = showModalDialog( "../toolbars/instable.htm",
                             args,
                             "font-family:Verdana; font-size:12; dialogWidth:36em; dialogHeight:25em");
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
    if (menuStrings[i] != MENU_SEPARATOR) {
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

function tbContentElement_ContextMenuAction(itemIndex) {

  if (ContextMenu[itemIndex].cmdId == DECMD_INSERTTABLE) {
    InsertTable();
  } else {
    tbContentElement.ExecCommand(ContextMenu[itemIndex].cmdId, OLECMDEXECOPT_DODEFAULT);
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
  s = tbContentElement.QueryStatus(DECMD_GETFONTNAME);
  if (s == DECMDF_DISABLED || s == DECMDF_NOTSUPPORTED) {
    FontName.disabled = true;
  } else {
    FontName.disabled = false;
    FontName.value = tbContentElement.ExecCommand(DECMD_GETFONTNAME, OLECMDEXECOPT_DODEFAULT);
  }

  if (s == DECMDF_DISABLED || s == DECMDF_NOTSUPPORTED) {
    FontSize.disabled = true;
  } else {
    FontSize.disabled = false;
    FontSize.value = tbContentElement.ExecCommand(DECMD_GETFONTSIZE, OLECMDEXECOPT_DODEFAULT);
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
//          eval(formname).modelSourceCodeFlag.value = 1;
        } else {
          var sContents=tbContentElement.DOM.body.innerText;
          sContent = tbContentElement.FilterSourceCode(sContents);
          tbContentElement.DocumentHTML=sContents
          TBSetState(menuItem, "checked");
          TBSetState(TableToolbar, ToolbarTableState);
//          eval(formname).modelSourceCodeFlag.value = 0;
        }
        tbContentElement_DocumentComplete();
        TBRebuildMenu(menuItem.parentElement, true);
}


function DECMD_VISIBLEBORDERS_onclick() {
  tbContentElement.ShowBorders = !tbContentElement.ShowBorders;

}

function DECMD_UNORDERLIST_onclick() {
  tbContentElement.ExecCommand(DECMD_UNORDERLIST,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_UNDO_onclick() {
  tbContentElement.ExecCommand(DECMD_UNDO,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_UNDERLINE_onclick() {
  tbContentElement.ExecCommand(DECMD_UNDERLINE,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_SNAPTOGRID_onclick() {
  tbContentElement.SnapToGrid = !tbContentElement.SnapToGrid;

}

function DECMD_SHOWDETAILS_onclick() {
  tbContentElement.ShowDetails = !tbContentElement.ShowDetails;

}

function DECMD_SETFORECOLOR_onclick() {
  var arr = showModalDialog( "../toolbars/selcolor.htm",
                             "",
                             "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:34em" );

  if (arr != null) {
    tbContentElement.ExecCommand(DECMD_SETFORECOLOR,OLECMDEXECOPT_DODEFAULT, arr);
  }
}

function DECMD_SETBACKCOLOR_onclick() {
  var arr = showModalDialog( "../toolbars/selcolor.htm",
                             "",
                             "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:34em" );

  if (arr != null) {
    tbContentElement.ExecCommand(DECMD_SETBACKCOLOR,OLECMDEXECOPT_DODEFAULT, arr);
  }

}

function DECMD_SELECTALL_onclick() {
  tbContentElement.ExecCommand(DECMD_SELECTALL,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_REDO_onclick() {
  tbContentElement.ExecCommand(DECMD_REDO,OLECMDEXECOPT_DODEFAULT);

}

function DECMD_PASTE_onclick() {
  tbContentElement.ExecCommand(DECMD_PASTE,OLECMDEXECOPT_DODEFAULT);

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

function DECMD_IMAGE_onclick() {
     sel =tbContentElement.DOM.selection.createRange();
     returnvalue = showModalDialog( "../toolbars/insimg.htm","",
                         "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");
     if ((returnvalue !="")&&(returnvalue !=null)) {
	   dir = "/images/upload/";

	   var tempstr = returnvalue;

	   posi = tempstr.lastIndexOf("-");
	    var pich = tempstr.substr(posi+1);           //???\u00A1\u00C0\u00A1¤??¨°??????
	    tempstr = tempstr.substr(0,posi);

	    posi = tempstr.lastIndexOf("-");
	    var picw = tempstr.substr(posi+1);           //????\u00A1¤??¨°??????
	    tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a1 = tempstr.substr(posi+1);           //\u00B4\u00B9\u00D6±·\u00BD\u00CFò\u00B5\u00C4\u00BF\u00D5\u00BC\u00E4
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a2 = tempstr.substr(posi+1);           //\u00CB\u00AE\u00C6\u00BD·\u00BD\u00CFò\u00B5\u00C4\u00BF\u00D5\u00BC\u00E4
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a3 = tempstr.substr(posi+1);           //±\u00DF\u00BD\u00E7\u00BFí\u00B6\u00C8
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a4 = tempstr.substr(posi+1);           //\u00B6\u00D4\u00C6\u00EB·\u00BD\u00CFò
	   tempstr = tempstr.substr(0,posi);

	   posi = tempstr.lastIndexOf("-");
	   var a5 = tempstr.substr(posi+1);           //\u00CC\u00E6\u00BB\u00BB\u00CE\u00C4±\u00BE
	   tempstr = tempstr.substr(0,posi);

	   pos =tempstr.lastIndexOf("\\")+1;
	   var filename = tempstr.substr(pos);        //\u00C9\u00CF\u00B4\u00AB\u00CE\u00C4\u00BC\u00FE\u00C3\u00FB\u00B3\u00C6

	   pos = filename.lastIndexOf(".")+1;
           var extName = filename.substr(pos);
	   if (extName.toLowerCase() =="swf"){
           	returnvalue = "<OBJECT classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' ";
           	if (pich !=0){
           		returnvalue = returnvalue + " height="+pich;
           	}
           	if (picw !=0){
           		returnvalue = returnvalue + " width="+picw;
           	}
           	returnvalue = returnvalue + "><PARAM NAME=movie VALUE='"+dir +filename+"'>";
          	returnvalue = returnvalue + "<PARAM NAME=quality VALUE=autohigh>";
           	returnvalue = returnvalue + "<EMBED src='"+dir +filename+"' quality=autohigh   TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash'" ;
           	if (pich !=0){
           		returnvalue = returnvalue + " height="+pich;
           	}
           	if (picw !=0){
           		returnvalue = returnvalue + " width="+picw;
           	}

           	returnvalue = returnvalue + "></EMBED></OBJECT>";
       	   }else {
           	returnvalue = "<IMG alttag="+dir +filename+" src=" + tempstr + " hspace=" + a1 + " vspace=" + a2 + " border=" + a3;
           	if (pich !=0){
           		returnvalue = returnvalue + " height="+pich;
           	}
           	if (picw !=0){
           		returnvalue = returnvalue + " width="+picw;
           	}
           	returnvalue = returnvalue + " align=" + a4 + " alt=" + a5 + ">";
       	   }

	   sel.pasteHTML(returnvalue);
     }else {
	    return(false);
     }


     //var sContents=tbContentElement.DOM.body.innerHTML;
     //tbContentElement.DocumentHTML=sContents;

     return(true);
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
		    alert("is right");
			txtHTML="<A href="+iHref+" ";
			if(iTarget.length)
				txtHTML+="target="+iTarget+"  ";
			if(iStyle.length)
				txtHTML+="style="+iStyle+" ";
			if(iClass.length)
	            alert("I am go here"+ iClass);
				txtHTML+="class="+iClass+" ";
			if(iName.length)
				txtHTML+="name="+iName+" ";
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
/*
function DECMD_HYPERLINK_onclick() {
  //tbContentElement.ExecCommand(DECMD_HYPERLINK,OLECMDEXECOPT_PROMPTUSER);
  sel =tbContentElement.DOM.selection.createRange();
  winStr = "addlink.jsp";
  var returnvalue = showModalDialog( winStr,"","font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em");
  if ((returnvalue != "undefined")&&(returnvalue != null) &&(returnvalue != "")){
    //setFormat("CreateLink",returnvalue);
    //tbContentElement.ExecCommand(DECMD_HYPERLINK,OLECMDEXECOPT_PROMPTUSER);
    sel.execCommand("CreateLink", false, returnvalue);
  }
}*/

function replaceJS (data,find,replace)  {
	var flen = find.length;
	var dlen = data.length;
    var rlen = replace.length;
    var i =0;
	while  (i<dlen ) {
		var j = i + flen;
		if (data.substring(i,j) == find)  {
			data = data.substring(0,i) + replace + data.substring(j,dlen);
            i += rlen;
            findit =true;
		}else 
	    i++;
	}
	return data
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
     var targettext = replaceJS(srctext,source,target);
     if (findit) {
    	 tbContentElement.DocumentHTML = targettext;
         tbContentElement_DocumentComplete();
     }
	}
  
}



function DECMD_FINDTEXT_onclick() {
  tbContentElement.ExecCommand(DECMD_FINDTEXT,OLECMDEXECOPT_PROMPTUSER);

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

function TABLE_DELETECELL_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETECELLS,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_DELETECOL_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETECOLS,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_DELETEROW_onclick() {
  tbContentElement.ExecCommand(DECMD_DELETEROWS,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_INSERTCELL_onclick() {
  tbContentElement.ExecCommand(DECMD_INSERTCELL,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_INSERTCOL_onclick() {
  tbContentElement.ExecCommand(DECMD_INSERTCOL,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_INSERTROW_onclick() {
  tbContentElement.ExecCommand(DECMD_INSERTROW,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_INSERTTABLE_onclick() {
  InsertTable();

}

function TABLE_MERGECELL_onclick() {
  tbContentElement.ExecCommand(DECMD_MERGECELLS,OLECMDEXECOPT_DODEFAULT);

}

function TABLE_SPLITCELL_onclick() {
  tbContentElement.ExecCommand(DECMD_SPLITCELL,OLECMDEXECOPT_DODEFAULT);

}

function FORMAT_FONT_onclick() {
  tbContentElement.ExecCommand(DECMD_FONT,OLECMDEXECOPT_DODEFAULT);

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
function FontName_onchange() {
  tbContentElement.ExecCommand(DECMD_SETFONTNAME, OLECMDEXECOPT_DODEFAULT, FontName.value);

}

function FontSize_onchange() {
  tbContentElement.ExecCommand(DECMD_SETFONTSIZE, OLECMDEXECOPT_DODEFAULT, parseInt(FontSize.value));

}

function get_src(formname){

        if (tbContentElement.Busy == true){
          alert("模版初始化还未完成，请稍后提交");
          tbContentElement_DocumentComplete();
          return;
        }

        var tempVar = tbContentElement.DocumentHTML;
        var regexp1 = /textarea/ig;
        tempVar=tempVar.replace(regexp1,"cmstextarea");

        var  imgs = tbContentElement.DOM.body.all.tags("IMG");
        if (imgs !=null) {
          var lens = imgs.length;
                  var uploadURL = "<%=URL%>";
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
          eval(formname).content.value = tempVar;
          eval(formname).submit();
          return true;
   }

