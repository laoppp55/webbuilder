/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2010 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Definition of other commands that are not available internaly in the
 * browser (see FCKNamedCommand).
 */

// ### General Dialog Box Commands.
var FCKDialogCommand = function( name, title, url, width, height, getStateFunction, getStateParam, customValue )
{
	this.Name	= name ;
	this.Title	= title ;
	this.Url	= url ;
	this.Width	= width ;
	this.Height	= height ;
	this.CustomValue = customValue ;

	this.GetStateFunction	= getStateFunction ;
	this.GetStateParam		= getStateParam ;

	this.Resizable = false ;
}

FCKDialogCommand.prototype.Execute = function()
{
    alert(this.Name);
    alert(this.Url);
    if(this.Name=="Button"){
        alert('FCKDialog_' + this.Name);
        var oActiveEl = null;
        oActiveEl = FCKSelection.GetSelectedElement();
        var bname = "";
        var bval = "";
        if(oActiveEl){   //有对象被选择
            alert("oActiveEl=" + oActiveEl.name);
            if ( oActiveEl && oActiveEl.tagName.toUpperCase() == "INPUT" && ( oActiveEl.type == "button" || oActiveEl.type == "submit" || oActiveEl.type == "reset" ) )
            {
                bname = oActiveEl.name;
                bval = oActiveEl.value;
                if (bname.indexOf("[MARKID]") != -1 || bname.indexOf("[CHINESE_PATH]") != -1 || bname.indexOf("[ENGLISH_PATH]") != -1|| bname.indexOf("[NAVBAR]") != -1||bname.indexOf("[ARTICLE_COUNT]")!=-1||bname.indexOf("[SUBARTICLE_COUNT]")!=-1) {
                    //FCKFocusManager.Lock();
                    DECMD_EDITATTR_onclick(window,bname,bval);
                    //FCKFocusManager.Unlock();
                }
                else {
                    FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, this.Url, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
                }
            }
        } else {  //没有选择对象
           if(this.Name=="Button"){
           FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, "dialog/prog_button.jsp?id="+parent.window.document.createForm.id.value, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
           }else{
           FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, this.Url, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
           }
        }
    } else if (this.Name=="Image"){
           //FCKFocusManager.Lock();
           //alert("FCKDialog_" + this.Name);
           var G=DECMD_IMAGE_onclick("FCKDialog_" + this.Name,window);
           //FCKFocusManager.Unlock();
    } else if (this.Name=="Link"){
        this.Url = "dialog/fck_link.jsp?column=" + parent.window.document.createForm.column.value;
        FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, this.Url, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
    }
    //增加多选按钮
    else if(this.Name=="Checkbox")
    {
      FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, "dialog/prog_checkbox.jsp", this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
    }
    //增加判断单选按钮
    else if(this.Name=="Radio")
    {
      FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, "dialog/prog_radio.jsp", this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
    }
     //增加被判断的表单对象
    else if(this.Name=="Form")
    {
         //alert(parent.window.document.createForm.template);
        var templateid=0;
        var str=parent.window.document.createForm.template+"";
         if(str.indexOf("undefined")==-1) templateid= parent.window.document.createForm.template.value;

        //alert(parent.window.document.createForm.id.value);
        //传入ID判断是否是什么表单
      FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, "dialog/prog_form.jsp?id="+parent.window.document.createForm.id.value+"&templateid="+templateid, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
    }
    else if(this.Name=="TextField")
    {
        //传入ID判断是什么类型表单里面字段
        FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, "dialog/prog_textfield.jsp?id="+parent.window.document.createForm.id.value, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
    }
    else {
        FCKDialog.OpenDialog( 'FCKDialog_' + this.Name , this.Title, this.Url, this.Width, this.Height, this.CustomValue, null, this.Resizable ) ;
    }
}

FCKDialogCommand.prototype.GetState = function()
{
	if ( this.GetStateFunction )
		return this.GetStateFunction( this.GetStateParam ) ;
	else
		return FCK.EditMode == FCK_EDITMODE_WYSIWYG ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ;
}

// ### WebBuilder增加Open功能
var FCKOpenCommand = function()
{
    this.Name = 'Open' ;
}

FCKOpenCommand.prototype.Execute = function()
{
   selectModel();;
}

FCKOpenCommand.prototype.GetState = function()
{
    return FCK_TRISTATE_ON ;
}

// ### Multimedia
var FCKMultimediaCommand = function()
{
    this.Name = 'Multimedia' ;
}

FCKMultimediaCommand.prototype.Execute = function()
{
   //alert("Multimedia");
    FCKFocusManager.Lock();
    var G=DECMD_MM_UPLOAD_onclick(window);
    FCKFocusManager.Unlock();
}

FCKMultimediaCommand.prototype.GetState = function()
{
    return FCK_TRISTATE_ON ;
}

// ### Histric pictures
var FCKHispicCommand = function()
{
    this.Name = 'HisPic' ;
}

FCKHispicCommand.prototype.Execute = function()
{
   //alert("HisPic");
    FCKFocusManager.Lock();
    var G=DECMD_HISTORYPIC_onclick(window);
    FCKFocusManager.Unlock();
}

FCKHispicCommand.prototype.GetState = function()
{
    return FCK_TRISTATE_ON ;
}

// ### Htmlcode pictures
var FCKHtmlcodeCommand = function()
{
    this.Name = 'Htmlcode' ;
}

FCKHtmlcodeCommand.prototype.Execute = function()
{
    alert("Htmlcode");
    FCKFocusManager.Lock();
    var G=generateHtmlcode();
    FCKFocusManager.Unlock();
}

FCKHispicCommand.prototype.GetState = function()
{
    return FCK_TRISTATE_ON ;
}

// ### Insert File in a html page
var FCKInsertFileCommand = function()
{
    this.Name = 'InsertFile' ;
}

FCKInsertFileCommand.prototype.Execute = function()
{
    FCKFocusManager.Lock();
    var G=DECMD_ACCESSORY_onclick(window);
    FCKFocusManager.Unlock();
}

FCKInsertFileCommand.prototype.GetState = function()
{
    return FCK_TRISTATE_ON ;
}

// Generic Undefined command (usually used when a command is under development).
var FCKUndefinedCommand = function()
{
	this.Name = 'Undefined' ;
}

FCKUndefinedCommand.prototype.Execute = function()
{
	alert( FCKLang.NotImplemented ) ;
}

FCKUndefinedCommand.prototype.GetState = function()
{
	return FCK_TRISTATE_OFF ;
}


// ### FormatBlock
var FCKFormatBlockCommand = function()
{}

FCKFormatBlockCommand.prototype =
{
	Name : 'FormatBlock',

	Execute : FCKStyleCommand.prototype.Execute,

	GetState : function()
	{
		return FCK.EditorDocument ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ;
	}
};

// ### FontName

var FCKFontNameCommand = function()
{}

FCKFontNameCommand.prototype =
{
	Name		: 'FontName',
	Execute		: FCKStyleCommand.prototype.Execute,
	GetState	: FCKFormatBlockCommand.prototype.GetState
};

// ### FontSize
var FCKFontSizeCommand = function()
{}

FCKFontSizeCommand.prototype =
{
	Name		: 'FontSize',
	Execute		: FCKStyleCommand.prototype.Execute,
	GetState	: FCKFormatBlockCommand.prototype.GetState
};

// ### Preview
var FCKPreviewCommand = function()
{
	this.Name = 'Preview' ;
}

FCKPreviewCommand.prototype.Execute = function()
{
     FCK.Preview() ;
}

FCKPreviewCommand.prototype.GetState = function()
{
	return FCK_TRISTATE_OFF ;
}

// ### Save
var FCKSaveCommand = function()
{
	this.Name = 'Save' ;
}

FCKSaveCommand.prototype.Execute = function()
{
	// Get the linked field form.
	var oForm = FCK.GetParentForm() ;

	if ( typeof( oForm.onsubmit ) == 'function' )
	{
		var bRet = oForm.onsubmit() ;
		if ( bRet != null && bRet === false )
			return ;
	}

	// Submit the form.
	// If there's a button named "submit" then the form.submit() function is masked and
	// can't be called in Mozilla, so we call the click() method of that button.
	if ( typeof( oForm.submit ) == 'function' )
		oForm.submit() ;
	else
		oForm.submit.click() ;
}

FCKSaveCommand.prototype.GetState = function()
{
	return FCK_TRISTATE_OFF ;
}

// ### NewPage
var FCKNewPageCommand = function()
{
	this.Name = 'NewPage' ;
}

FCKNewPageCommand.prototype.Execute = function()
{
	FCKUndo.SaveUndoStep() ;
	FCK.SetData( '' ) ;
	FCKUndo.Typing = true ;
	FCK.Focus() ;
}

FCKNewPageCommand.prototype.GetState = function()
{
	return FCK_TRISTATE_OFF ;
}

// ### Source button
var FCKSourceCommand = function()
{
	this.Name = 'Source' ;
}

FCKSourceCommand.prototype.Execute = function()
{
	if ( FCKConfig.SourcePopup )	// Until v2.2, it was mandatory for FCKBrowserInfo.IsGecko.
	{
		var iWidth	= FCKConfig.ScreenWidth * 0.65 ;
		var iHeight	= FCKConfig.ScreenHeight * 0.65 ;
		FCKDialog.OpenDialog( 'FCKDialog_Source', FCKLang.Source, 'dialog/fck_source.html', iWidth, iHeight, null, true ) ;
	}
	else
	    FCK.SwitchEditMode() ;
}

FCKSourceCommand.prototype.GetState = function()
{
	return ( FCK.EditMode == FCK_EDITMODE_WYSIWYG ? FCK_TRISTATE_OFF : FCK_TRISTATE_ON ) ;
}

// ### Undo
var FCKUndoCommand = function()
{
	this.Name = 'Undo' ;
}

FCKUndoCommand.prototype.Execute = function()
{
	FCKUndo.Undo() ;
}

FCKUndoCommand.prototype.GetState = function()
{
	if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
		return FCK_TRISTATE_DISABLED ;
	return ( FCKUndo.CheckUndoState() ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ) ;
}

// ### Redo
var FCKRedoCommand = function()
{
	this.Name = 'Redo' ;
}

FCKRedoCommand.prototype.Execute = function()
{
	FCKUndo.Redo() ;
}

FCKRedoCommand.prototype.GetState = function()
{
	if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
		return FCK_TRISTATE_DISABLED ;
	return ( FCKUndo.CheckRedoState() ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ) ;
}

// ### Page Break
var FCKPageBreakCommand = function()
{
	this.Name = 'PageBreak' ;
}

FCKPageBreakCommand.prototype.Execute = function()
{
	// Take an undo snapshot before changing the document
	FCKUndo.SaveUndoStep() ;

//	var e = FCK.EditorDocument.createElement( 'CENTER' ) ;
//	e.style.pageBreakAfter = 'always' ;

	// Tidy was removing the empty CENTER tags, so the following solution has
	// been found. It also validates correctly as XHTML 1.0 Strict.
	var e = FCK.EditorDocument.createElement( 'DIV' ) ;
	e.style.pageBreakAfter = 'always' ;
	e.innerHTML = '<span style="DISPLAY:none">&nbsp;</span>' ;

	var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__PageBreak', e ) ;
	var oRange = new FCKDomRange( FCK.EditorWindow ) ;
	oRange.MoveToSelection() ;
	var oSplitInfo = oRange.SplitBlock() ;
	oRange.InsertNode( oFakeImage ) ;

	FCK.Events.FireEvent( 'OnSelectionChange' ) ;
}

FCKPageBreakCommand.prototype.GetState = function()
{
	if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
		return FCK_TRISTATE_DISABLED ;
	return 0 ; // FCK_TRISTATE_OFF
}

// FCKUnlinkCommand - by Johnny Egeland (johnny@coretrek.com)
var FCKUnlinkCommand = function()
{
	this.Name = 'Unlink' ;
}

FCKUnlinkCommand.prototype.Execute = function()
{
	// Take an undo snapshot before changing the document
	FCKUndo.SaveUndoStep() ;

	if ( FCKBrowserInfo.IsGeckoLike )
	{
		var oLink = FCK.Selection.MoveToAncestorNode( 'A' ) ;
		// The unlink command can generate a span in Firefox, so let's do it our way. See #430
		if ( oLink )
			FCKTools.RemoveOuterTags( oLink ) ;

		return ;
	}

	FCK.ExecuteNamedCommand( this.Name ) ;
}

FCKUnlinkCommand.prototype.GetState = function()
{
	if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
		return FCK_TRISTATE_DISABLED ;
	var state = FCK.GetNamedCommandState( this.Name ) ;

	// Check that it isn't an anchor
	if ( state == FCK_TRISTATE_OFF && FCK.EditMode == FCK_EDITMODE_WYSIWYG )
	{
		var oLink = FCKSelection.MoveToAncestorNode( 'A' ) ;
		var bIsAnchor = ( oLink && oLink.name.length > 0 && oLink.href.length == 0 ) ;
		if ( bIsAnchor )
			state = FCK_TRISTATE_DISABLED ;
	}

	return state ;
}

var FCKVisitLinkCommand = function()
{
	this.Name = 'VisitLink';
}
FCKVisitLinkCommand.prototype =
{
	GetState : function()
	{
		if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
			return FCK_TRISTATE_DISABLED ;
		var state = FCK.GetNamedCommandState( 'Unlink' ) ;

		if ( state == FCK_TRISTATE_OFF )
		{
			var el = FCKSelection.MoveToAncestorNode( 'A' ) ;
			if ( !el.href )
				state = FCK_TRISTATE_DISABLED ;
		}

		return state ;
	},

	Execute : function()
	{
		var el = FCKSelection.MoveToAncestorNode( 'A' ) ;
		var url = el.getAttribute( '_fcksavedurl' ) || el.getAttribute( 'href', 2 ) ;

		// Check if it's a full URL.
		// If not full URL, we'll need to apply the BaseHref setting.
		if ( ! /:\/\//.test( url ) )
		{
			var baseHref = FCKConfig.BaseHref ;
			var parentWindow = FCK.GetInstanceObject( 'parent' ) ;
			if ( !baseHref )
			{
				baseHref = parentWindow.document.location.href ;
				baseHref = baseHref.substring( 0, baseHref.lastIndexOf( '/' ) + 1 ) ;
			}

			if ( /^\//.test( url ) )
			{
				try
				{
					baseHref = baseHref.match( /^.*:\/\/+[^\/]+/ )[0] ;
				}
				catch ( e )
				{
					baseHref = parentWindow.document.location.protocol + '://' + parentWindow.parent.document.location.host ;
				}
			}

			url = baseHref + url ;
		}

		if ( !window.open( url, '_blank' ) )
			alert( FCKLang.VisitLinkBlocked ) ;
	}
} ;

// FCKSelectAllCommand
var FCKSelectAllCommand = function()
{
	this.Name = 'SelectAll' ;
}

FCKSelectAllCommand.prototype.Execute = function()
{
	if ( FCK.EditMode == FCK_EDITMODE_WYSIWYG )
	{
		FCK.ExecuteNamedCommand( 'SelectAll' ) ;
	}
	else
	{
		// Select the contents of the textarea
		var textarea = FCK.EditingArea.Textarea ;
		if ( FCKBrowserInfo.IsIE )
		{
			textarea.createTextRange().execCommand( 'SelectAll' ) ;
		}
		else
		{
			textarea.selectionStart = 0 ;
			textarea.selectionEnd = textarea.value.length ;
		}
		textarea.focus() ;
	}
}

FCKSelectAllCommand.prototype.GetState = function()
{
	if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
		return FCK_TRISTATE_DISABLED ;
	return FCK_TRISTATE_OFF ;
}

// FCKPasteCommand
var FCKPasteCommand = function()
{
	this.Name = 'Paste' ;
}

FCKPasteCommand.prototype =
{
	Execute : function()
	{
		if ( FCKBrowserInfo.IsIE )
			FCK.Paste() ;
		else
			FCK.ExecuteNamedCommand( 'Paste' ) ;
	},

	GetState : function()
	{
		if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
			return FCK_TRISTATE_DISABLED ;
		return FCK.GetNamedCommandState( 'Paste' ) ;
	}
} ;

// FCKRuleCommand
var FCKRuleCommand = function()
{
	this.Name = 'Rule' ;
}

FCKRuleCommand.prototype =
{
	Execute : function()
	{
		FCKUndo.SaveUndoStep() ;
		FCK.InsertElement( 'hr' ) ;
	},

	GetState : function()
	{
		if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
			return FCK_TRISTATE_DISABLED ;
		return FCK.GetNamedCommandState( 'InsertHorizontalRule' ) ;
	}
} ;

// FCKCutCopyCommand
var FCKCutCopyCommand = function( isCut )
{
	this.Name = isCut ? 'Cut' : 'Copy' ;
}

FCKCutCopyCommand.prototype =
{
	Execute : function()
	{
		var enabled = false ;

		if ( FCKBrowserInfo.IsIE )
		{
			// The following seems to be the only reliable way to detect that
			// cut/copy is enabled in IE. It will fire the oncut/oncopy event
			// only if the security settings enabled the command to execute.

			var onEvent = function()
			{
				enabled = true ;
			} ;

			var eventName = 'on' + this.Name.toLowerCase() ;

			FCK.EditorDocument.body.attachEvent( eventName, onEvent ) ;
			FCK.ExecuteNamedCommand( this.Name ) ;
			FCK.EditorDocument.body.detachEvent( eventName, onEvent ) ;
		}
		else
		{
			try
			{
				// Other browsers throw an error if the command is disabled.
				FCK.ExecuteNamedCommand( this.Name ) ;
				enabled = true ;
			}
			catch(e){}
		}

		if ( !enabled )
			alert( FCKLang[ 'PasteError' + this.Name ] ) ;
	},

	GetState : function()
	{
		// Strangely, the Cut command happens to have the correct states for
		// both Copy and Cut in all browsers.
		return FCK.EditMode != FCK_EDITMODE_WYSIWYG ?
				FCK_TRISTATE_DISABLED :
				FCK.GetNamedCommandState( 'Cut' ) ;
	}
};

var FCKAnchorDeleteCommand = function()
{
	this.Name = 'AnchorDelete' ;
}

FCKAnchorDeleteCommand.prototype =
{
	Execute : function()
	{
		if (FCK.Selection.GetType() == 'Control')
		{
			FCK.Selection.Delete();
		}
		else
		{
			var oFakeImage = FCK.Selection.GetSelectedElement() ;
			if ( oFakeImage )
			{
				if ( oFakeImage.tagName == 'IMG' && oFakeImage.getAttribute('_fckanchor') )
					oAnchor = FCK.GetRealElement( oFakeImage ) ;
				else
					oFakeImage = null ;
			}

			//Search for a real anchor
			if ( !oFakeImage )
			{
				oAnchor = FCK.Selection.MoveToAncestorNode( 'A' ) ;
				if ( oAnchor )
					FCK.Selection.SelectNode( oAnchor ) ;
			}

			// If it's also a link, then just remove the name and exit
			if ( oAnchor.href.length != 0 )
			{
				oAnchor.removeAttribute( 'name' ) ;
				// Remove temporary class for IE
				if ( FCKBrowserInfo.IsIE )
					oAnchor.className = oAnchor.className.replace( FCKRegexLib.FCK_Class, '' ) ;
				return ;
			}

			// We need to remove the anchor
			// If we got a fake image, then just remove it and we're done
			if ( oFakeImage )
			{
				oFakeImage.parentNode.removeChild( oFakeImage ) ;
				return ;
			}
			// Empty anchor, so just remove it
			if ( oAnchor.innerHTML.length == 0 )
			{
				oAnchor.parentNode.removeChild( oAnchor ) ;
				return ;
			}
			// Anchor with content, leave the content
			FCKTools.RemoveOuterTags( oAnchor ) ;
		}
		if ( FCKBrowserInfo.IsGecko )
			FCK.Selection.Collapse( true ) ;
	},

	GetState : function()
	{
		if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
			return FCK_TRISTATE_DISABLED ;
		return FCK.GetNamedCommandState( 'Unlink') ;
	}
};

var FCKDeleteDivCommand = function()
{
}
FCKDeleteDivCommand.prototype =
{
	GetState : function()
	{
		if ( FCK.EditMode != FCK_EDITMODE_WYSIWYG )
			return FCK_TRISTATE_DISABLED ;

		var node = FCKSelection.GetParentElement() ;
		var path = new FCKElementPath( node ) ;
		return path.BlockLimit && path.BlockLimit.nodeName.IEquals( 'div' ) ? FCK_TRISTATE_OFF : FCK_TRISTATE_DISABLED ;
	},

	Execute : function()
	{
		// Create an undo snapshot before doing anything.
		FCKUndo.SaveUndoStep() ;

		// Find out the nodes to delete.
		var nodes = FCKDomTools.GetSelectedDivContainers() ;

		// Remember the current selection position.
		var range = new FCKDomRange( FCK.EditorWindow ) ;
		range.MoveToSelection() ;
		var bookmark = range.CreateBookmark() ;

		// Delete the container DIV node.
		for ( var i = 0 ; i < nodes.length ; i++)
			FCKDomTools.RemoveNode( nodes[i], true ) ;

		// Restore selection.
		range.MoveToBookmark( bookmark ) ;
		range.Select() ;
	}
} ;

// FCKRuleCommand
var FCKNbsp = function()
{
	this.Name = 'Non Breaking Space' ;
}

FCKNbsp.prototype =
{
	Execute : function()
	{
		FCK.InsertHtml( '&nbsp;' ) ;
	},

	GetState : function()
	{
		return ( FCK.EditMode != FCK_EDITMODE_WYSIWYG ? FCK_TRISTATE_DISABLED : FCK_TRISTATE_OFF ) ;
	}
} ;


//Webbuilder增加函数
function selectModel()
{
    //alert(parent.window.document.createForm.column.value);
    var winStr = "/webbuilder/template/selectModel.jsp?column=" + parent.window.document.createForm.column.value;
    returnvalue = showModalDialog( winStr,"", "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");

    if((returnvalue != null)&&(returnvalue != "")&&(returnvalue.length>0)){
        var objXml = new ActiveXObject("Microsoft.XMLHTTP");
        objXml.open("POST", "/webbuilder/template/getTemplateContent.jsp?path=" + returnvalue, false);
        objXml.Send();
        returnvalue = objXml.responseText;

        var oEditor = FCKeditorAPI.GetInstance('content') ;
        if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
        {
            oEditor.SetHTML( returnvalue ) ;
        }
    }
}

function DECMD_EDITATTR_onclick(F,bname,bval)
{
    alert(bname);	
    var oActiveEl = null;
    oActiveEl = FCKSelection.GetSelectedElement();
    var returnvalue = "";
    var str = bname;
    if (bname.indexOf("[ARTICLE_COUNT]") > -1)
    {
        var winStr = "/webbuilder/template/addArticleCountList.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:50em;dialogHeight:35em;status:no");
    }
    else if (bname.indexOf("[SUBARTICLE_COUNT]") > -1)
    {
        var winStr = "/webbuilder/template/addSubArticleCountList.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:50em;dialogHeight:35em;status:no");
    }
    else if (bname.indexOf("[CHINESE_PATH]") > -1)
    {
        var winStr = "/webbuilder/template/addPath.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][CHINESE_PATH]"+returnvalue+"[/CHINESE_PATH][/TAG]";
    }
    else if (bname.indexOf("[ENGLISH_PATH]") > -1)
    {
        var winStr = "/webbuilder/template/addPath.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][ENGLISH_PATH]"+returnvalue+"[/ENGLISH_PATH][/TAG]";
    }
    else if (bname.indexOf("[NAVBAR]") > -1)
    {
        //alert(parent.window.document.createForm.column.value);
        var winStr = "/webbuilder/template/addNavBar.jsp?str="+str + "&column=" + parent.window.document.createForm.column.value;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][NAVBAR]"+returnvalue+"-" + parent.window.document.createForm.column.value + "[/NAVBAR][/TAG]";
    }
    else if (bname.indexOf("[PREV_ARTICLE]") > -1)
    {
        var winStr = "/webbuilder/template/addNextArticle.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][PREV_ARTICLE]"+returnvalue+"[/PREV_ARTICLE][/TAG]";
    }
    else if (bname.indexOf("[NEXT_ARTICLE]") > -1)
    {
        var winStr = "/webbuilder/template/addNextArticle.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][NEXT_ARTICLE]"+returnvalue+"[/NEXT_ARTICLE][/TAG]";
    }
    else if (bname.indexOf("[ADV_POSITION]") > -1)
    {
        var winStr = "/webbuilder/template/addADV.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:50em;dialogHeight:30em;status:no");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][ADV_POSITION]"+returnvalue+"[/ADV_POSITION][/TAG]";
    }
    else if(bname.indexOf("[PAGINATION]") > -1)
    {
        var winStr = "/webbuilder/template/addNavBar.jsp?str="+str;
        returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no;");
        if (returnvalue != "" && returnvalue != "0")
            returnvalue = "[TAG][PAGINATION]"+returnvalue+"[/PAGINATION][/TAG]";
    }
    else if(bname.indexOf("[ARTICLE_PT]") > -1)
    {
        returnvalue = F.showModalDialog("/webbuilder/template/addArticlePt.jsp?str="+str,"","font-family:Verdana; font-size:12; dialogWidth:300px;dialogHeight:120px;status:no");
    }
    else if(bname.indexOf("[ARTICLE_TYPE]") > -1)
    {
        returnvalue = F.showModalDialog("/webbuilder/template/addArticleType.jsp?str="+str,"","font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
    }
    else if (bname.indexOf("[MARKID]") > -1)
    {
        var markID = str.substring(str.indexOf("[MARKID]")+8,str.indexOf("_"));
        var columnID = str.substring(str.indexOf("_")+1,str.indexOf("[/MARKID]"));
        //alert(markID);

        if (bval == "[文章列表]")
        {
            var winStr = "/webbuilder/template/addArticleList.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:72em;dialogHeight:54em;status:no");
            //alert(returnvalue);
        }
        if (bval == "[推荐文章列表]")
        {
            var winStr = "/webbuilder/template/commendarticle.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:70em; dialogHeight:55em;status:no");
        }
        if (bval == "[子文章列表]")
        {
            var winStr = "/webbuilder/template/addArticleList.jsp?type=1&column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:72em;dialogHeight:54em;status:no");
        }
        if (bval == "[兄弟文章列表]")
        {
            var winStr = "/webbuilder/template/addArticleList.jsp?type=2&column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:72em;dialogHeight:54em;status:no");
        }
        if (bval == "[栏目列表]")
        {
            var winStr = "/webbuilder/template/addColumnList.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:780px;dialogHeight:550px;status:no");
        }
        if (bval == "[子栏目列表]")
        {
            var winStr = "/webbuilder/template/addSubColumnList.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:56em;dialogHeight:35em;status:no");
        }
        if (bval == "[热点文章]")
        {
            var winStr = "/webbuilder/templatex/topStories.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:70em; dialogHeight:55em;status:no");
        }
        if (bval == "[相关文章]")
        {
            var winStr = "/webbuilder/template/addRelateList.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:578px;dialogHeight:385px;status:no");
        }
        if (bval == "[网页片段]")
        {
            var winStr = "/webbuilder/template/editHtmlcodeMarkFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"htmlcode","dialogWidth:950px; dialogHeight:600px");
        }
        if(bval=="[修改注册]")
        {
            var winStr = "/webbuilder/template/addUpdateRegFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[用户注册]")
        {
            var winStr = "/webbuilder/template/addUserRegisterFrame.jsp?column="+columnID+"&mark="+markID;
            //var winStr = "/webbuilder/template/addUserRegisterTest.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:600px;status:no");
        }
        if(bval=="[地图标注]")
        {
            var winStr = "/webbuilder/template/addMapmarkFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[用户登录显示]")
        {
            var winStr = "/webbuilder/template/addUserLoginFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[登录表单]")
        {
            var winStr = "/webbuilder/template/addUserLoginFormFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[程序模板登录表单]")
        {
            var winStr = "/webbuilder/template/addUserProgramLoginFormFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[调查表单]")
        {
            var winStr = "/webbuilder/template/addUserDefinenFormFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:400px; dialogHeight:500px;status:no");
        }
        if(bval=="[用户留言表单]")
        {
            var winStr = "/webbuilder/template/addUserLeavemessageFormFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:750px;status:no");
        }
        if(bval=="[用户留言列表]")
        {
            var winStr = "/webbuilder/template/addUserLeavemessageListFormFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:750px;status:no");
        }
        if(bval=="[信息反馈]")
        {
            var winStr = "/webbuilder/template/addFeedbackFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[用户留言]")
        {
            var winStr = "/webbuilder/template/addLeaveMessageFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[文章评论]")
        {
            var winStr = "/webbuilder/template/addArticleCommentFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[购物车]")
        {
            var winStr = "/webbuilder/template/addShoppingcarFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[订单生成]")
        {
            var winStr = "/webbuilder/template/addOrderGenerateFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:600px;status:no");
        }
        if(bval=="[订单回显]")
        {
            var winStr = "/webbuilder/template/addOrderDisplayFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[订单明细查询]")
        {
            var winStr = "/webbuilder/template/addOrderDetailSearchFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[订单查询]")
        {
            var winStr = "/webbuilder/template/addOrderSearchFrame.jsp?column="+columnID+"&mark="+markID;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
        }
        if(bval=="[信息检索]")
        {
            //alert(columnID);
            var winStr = "/webbuilder/template/addSearchFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:600px;status:no");
        }
        if(bval=="[图片特效]")
        {
            //alert(columnID);
            var winStr = "/webbuilder/template/addXuanImagesFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:800px; dialogHeight:600px;status:no");
        }
        if(bval=="[文章图片特效]")
        {
            //alert(columnID);
            var winStr = "/webbuilder/template/addArticleXuanImagesFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:800px; dialogHeight:600px;status:no");
        }
        if(bval=="[包含文件]")
        {
            //alert(columnID+"   markID="+markID);
           var winStr = "/webbuilder/template/add_include.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:800px; dialogHeight:600px;status:no");
        }
         if(bval=="[最近浏览]")
        {
            //alert(columnID+"   markID="+markID);
           var winStr = "/webbuilder/template/addSeeCookietag.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:800px; dialogHeight:600px;status:no");
        }
        //alert(bval);
    }
    if((returnvalue != null)&&(returnvalue != "")){
        oActiveEl.name = returnvalue;
    }
    return returnvalue;
}

function DECMD_MM_UPLOAD_onclick(F) {
    var winStr = "/webbuilder/upload/mmupload.jsp";
    var win = window.open(winStr,"","width=800px,height=550px");
    win.focus();
}

function DECMD_HISTORYPIC_onclick(F){
    var winStr = "/webbuilder/upload/history.jsp";
    var win = window.open(winStr,"","width=800px,height=550px");
    win.focus();
}

function DECMD_ACCESSORY_onclick(){
    var winStr = "/webbuilder/upload/upload_file.jsp";
    var win = window.open(winStr,"","width=400px,height=260px");
    win.focus();
}

function DECMD_MULTIUPLOAD_onclick(F){
    var winStr = "/webbuilder/upload/muluploadpic.jsp";
    var win = window.open(winStr,"","width=400px,height=260px");
    win.focus();
}

function DECMD_IMAGE_onclick(A,F)
{
    if((A=="FCKDialog_Image")||(A=="FCKDialog_Flash")){
        var oActiveEl = FCKSelection.GetSelectedElement();
        if (oActiveEl) {
            if (oActiveEl && oActiveEl.tagName.toUpperCase() == "IMG")
            {
                bsrc = oActiveEl.src;
                bwidth = oActiveEl.width;
                bheight = oActiveEl.height;
                vspace = oActiveEl.vspace;
                hspace = oActiveEl.hspace;
                border = oActiveEl.border;
                align = oActiveEl.align;
                alt = oActiveEl.alt;

                var winStr = "/webbuilder/upload/editimgs.jsp?src="+bsrc+"&width="+bwidth+"&height="+bheight+"&vspace="+vspace+"&hspace="+hspace+"&border="+border+"&align="+align+"&alt="+alt;
                var win = window.open(winStr, "", "font-family:Verdana;font-size:12;dialogWidth:800px; dialogHeight:600px;status:yes;scrollbars=yes");
                win.focus();
            }else{
                top.close();
            }
        }else{
            var winStr = "/webbuilder/upload/picup.jsp?template_or_article=" + parent.window.document.createForm.template_or_article_flag.value;
            returnvalue = F.showModalDialog(winStr,"","font-family:Verdana;font-size:12;dialogWidth:800px; dialogHeight:600px;status:yes;scrollbars=yes");

            //如果是文章中录入图片，回写标题、副标题、文章图片等字段
            posi = returnvalue.indexOf("&");
            var pics = "";
            //alert(posi);
            if (posi > -1) {
                pics = returnvalue.substring(posi + 1);
                returnvalue = returnvalue.substring(0,posi);
            }
            //alert(pics);
            //alert(returnvalue);
            if (posi >-1 && parent.window.document.createForm.template_or_article_flag.value == 1) {
                var nextpic = pics.split("&");            //用&来划分
                for(var i=0;i<nextpic.length;i++){        //输出
                    var buf = nextpic[i];
                    posi = buf.indexOf("=");
                    if (posi > -1) {
                        name = buf.substring(0,posi);
                        value = buf.substring(posi + 1);
                        if (name.Equals("title_pic")) parent.window.document.createForm.maintitle.value = value;
                        if (name.Equals("vtitle_pic")) parent.window.document.createForm.vicetitle.value = value;
                        if (name.Equals("special_pic")) parent.window.document.createForm.articlepic.value = value;
                        if (name.Equals("prod_small_pic")) parent.window.document.createForm.pic.value = value;
                        if (name.Equals("prod_large_pic")) parent.window.document.createForm.bigpic.value = value;
                    }
                }
            }

            var oEditor = FCKeditorAPI.GetInstance('content') ;
            if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
            {
                oEditor.InsertHtml( returnvalue ) ;
            }
        }
    }
}

function selectModel()
{
    //alert(parent.window.document.createForm.column.value);
    var winStr = "/webbuilder/template/selectModel.jsp?column=" + parent.window.document.createForm.column.value;
    returnvalue = showModalDialog( winStr,"", "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:30em");

    if((returnvalue != null)&&(returnvalue != "")&&(returnvalue.length>0)){
        var objXml = new ActiveXObject("Microsoft.XMLHTTP");
        objXml.open("POST", "/webbuilder/template/getTemplateContent.jsp?path=" + returnvalue, false);
        objXml.Send();
        returnvalue = objXml.responseText;

        var oEditor = FCKeditorAPI.GetInstance('content') ;
        if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
        {
            oEditor.SetHTML( returnvalue ) ;
        }
    }
}

function  selfDefineForm(F) {
    winStr = "/webbuilder/template/selfdefineform.jsp?column="+parent.window.document.createForm.column.value;
    returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");
    if((returnvalue != null)&&(returnvalue != "")&&(returnvalue.length>0)){
        var oEditor = FCKeditorAPI.GetInstance('content');
        if ( oEditor.EditMode == FCK_EDITMODE_WYSIWYG )
        {
            parent.window.document.createForm.textvalues.value = returnvalue;
            oEditor.SetHTML( returnvalue );
            //alert(parent.window.document.createForm.prog.value);
        }
    }
}
