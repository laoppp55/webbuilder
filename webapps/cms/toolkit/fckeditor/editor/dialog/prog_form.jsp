<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.viewFileManager.*"
        contentType="text/html;charset=gbk"
        %>
<%
 Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    String sitename=authToken.getSitename();
    int id=ParamUtil.getIntParameter(request,"id",-1);
    int templateid=ParamUtil.getIntParameter(request,"templateid",-1);
    System.out.println("id======="+id);
    //id=0 是登陆表单
    String name="";
    String action="";
    if(id==0)
    {
      name="loginForm";
      action="/" + sitename +"/_prog/login.jsp";
    }
    if(id==1)
    {
        name="form";

    }
    if(id==2)
    {
        name="form1";
    }
    if(id==-1)
    {
        action="/_commons/submitform.jsp";
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
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
 * Form dialog window.
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="noindex, nofollow" name="robots" />
	<script src="common/fck_dialog_common.js" type="text/javascript"></script>
	<script type="text/javascript">

var dialog	= window.parent ;
var oEditor = dialog.InnerDialogLoaded() ;

// Gets the document DOM
var oDOM = oEditor.FCK.EditorDocument ;

var oActiveEl = dialog.Selection.GetSelection().MoveToAncestorNode( 'FORM' ) ;

window.onload = function()
{
    
    // First of all, translate the dialog box texts
	oEditor.FCKLanguageManager.TranslatePage(document) ;
   // alert("aa");
	if ( oActiveEl )
	{
		GetE('txtName').value	= oActiveEl.name ;
		GetE('txtAction').value	= oActiveEl.getAttribute( 'action', 2 ) ;
		GetE('txtMethod').value	= oActiveEl.method ;
	}
	else
		oActiveEl = null ;

   // alert("bbb");
    dialog.SetOkButton( true ) ;
     //  alert("ccc");
    dialog.SetAutoSize( true ) ;
    // alert("ddd");
    SelectField( 'txtName' ) ;
   // alert("eee");

}

function Ok()
{
    
    if ( !oActiveEl )
	{
		oActiveEl = oEditor.FCK.InsertElement( 'form' ) ;
		if ( oEditor.FCKBrowserInfo.IsGeckoLike )
			oEditor.FCKTools.AppendBogusBr( oActiveEl ) ;
	}

    oEditor.FCKUndo.SaveUndoStep() ;
    oActiveEl.name = GetE('txtName').value ;
	SetAttribute( oActiveEl, 'action', GetE('txtAction').value ) ;
	oActiveEl.method = GetE('txtMethod').value ;
    <%
       if(id==-1){
    %>

    var oEditor1 = oEditor.FCKeditorAPI.GetInstance("content");
    var content= oEditor1.GetXHTML(true);//oEditor.FCK.EditorDocument.body.innerHTML;
    //alert(content);
    var forms=content.split("</form>");
    var temp=<%=templateid%>+"_"+forms.length;
    //alert(temp);
    if(content.indexOf(temp)==-1) {
     //alert("aaaaa");
     oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<input type=\"hidden\" name=\"createform\" value=\"<%=templateid%>_"+forms.length+"\">");
    }

    <%}%>
    return true ;
}

	</script>
</head>
<body style="overflow: hidden">
	<table width="100%" style="height: 100%">
		<tr>
			<td align="center">
				<table cellspacing="0" cellpadding="0" width="80%" border="0">
					<tr>
						<td>
							<span fcklang="DlgFormName">Name</span><br />
							<input style="width: 100%" type="text" id="txtName" disabled="disabled" value="<%=name%>"  />
						</td>
					</tr>
					<tr>
						<td>
							<span fcklang="DlgFormAction">Action</span><br />
							<input style="width: 100%" type="text" id="txtAction" value="<%=action%>" disabled="disabled" />
						</td>
					</tr>
					<tr>
						<td>
							<span fcklang="DlgFormMethod">Method</span><br />
							<select id="txtMethod">
								<option value="get" >GET</option>
								<option value="post" selected="selected">POST</option>
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
