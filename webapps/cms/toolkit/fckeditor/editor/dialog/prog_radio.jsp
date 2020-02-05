<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%
     int id= ParamUtil.getIntParameter(request,"id",-1);
%>
<!--
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2009 Frederico Caldeira Knabben
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
 * Radio Button dialog window.
-->
<html>
	<head>
		<title>Radio Button Properties</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta content="noindex, nofollow" name="robots">
		<script src="common/fck_dialog_common.js" type="text/javascript"></script>
		<script type="text/javascript">

var dialog	= window.parent ;
var oEditor = dialog.InnerDialogLoaded() ;

// Gets the document DOM
var oDOM = oEditor.FCK.EditorDocument ;

var oActiveEl = dialog.Selection.GetSelectedElement() ;

window.onload = function()
{
	// First of all, translate the dialog box texts
	oEditor.FCKLanguageManager.TranslatePage(document) ;

	if ( oActiveEl && oActiveEl.tagName.toUpperCase() == 'INPUT' && oActiveEl.type == 'radio' )
	{
		GetE('txtName').value		= oActiveEl.name ;
		GetE('txtValue').value		= oEditor.FCKBrowserInfo.IsIE ? oActiveEl.value : GetAttribute( oActiveEl, 'value' ) ;
		GetE('txtSelected').checked	= oActiveEl.checked ;
	}
	else
		oActiveEl = null ;

	dialog.SetOkButton( true ) ;
	dialog.SetAutoSize( true ) ;
	SelectField( 'txtName' ) ;
}

function Ok()
{
    <%
       if(id==-1){
    %>
  //  alert(GetE('txtName').value);
       if(GetE('txtName').value=="")
       {
           alert("名称不能为空,如果要是单选按钮名称必须是一样的");
           return false;
       }
       if(/[^a-z\.\'\"]/i.test(GetE('txtName').value))
       {
           alert("名称必须为英文字符");
           return false;
       }
      if(GetE('txtValue').value=="")
       {
           alert("值不能为空");
           return false;
       }
      if(GetE('chinesename').value=="")
       {
           alert("请填写中文名称");
           return false;
       }
    <%}%>
    oEditor.FCKUndo.SaveUndoStep() ;

	oActiveEl = CreateNamedElement( oEditor, oActiveEl, 'INPUT', {name: GetE('txtName').value, type: 'radio' } ) ;
     SetAttribute( oActiveEl, 'alt', GetE('chinesename').value ) ;
	if ( oEditor.FCKBrowserInfo.IsIE ){
       // alert("aaaa");
        oActiveEl.value = GetE('txtValue').value ;
        oEditor.FCKeditorAPI.GetInstance("content").InsertHtml(GetE('txtValue').value);
    }else{
        //alert("bbbbb");
        SetAttribute( oActiveEl, 'value', GetE('txtValue').value ) ;
    }

    var bIsChecked = GetE('txtSelected').checked ;
	SetAttribute( oActiveEl, 'checked', bIsChecked ? 'checked' : null ) ;	// For Firefox
	oActiveEl.checked = bIsChecked ;

	return true ;
}

		</script>
	</head>
	<body style="OVERFLOW: hidden" scroll="no">
		<table height="100%" width="100%">
			<tr>
				<td align="center"> 如果要是单选按钮前您写同样的名称,名称最好是有意义的
					<table border="0" cellpadding="0" cellspacing="0" width="80%">
						<tr>
							<td>
								<span fckLang="DlgCheckboxName">Name</span><br>
								<input type="text" size="20" id="txtName" style="WIDTH: 100%">
							</td>
						</tr>
						<tr>
							<td>
								<span fckLang="DlgCheckboxValue">Value</span><br>
								<input type="text" size="20" id="txtValue" style="WIDTH: 100%">
							</td>
						</tr>
						<tr>
							<td><input type="checkbox" id="txtSelected"><label for="txtSelected" fckLang="DlgCheckboxSelected">Checked</label>&nbsp;&nbsp;&nbsp;中文名称<input type="text" name="chinesename" size="5"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
