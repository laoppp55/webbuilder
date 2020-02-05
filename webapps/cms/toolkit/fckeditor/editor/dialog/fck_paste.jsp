<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp?url=member/createnewsite.jsp");
        return;
    }

    Properties prop = new Properties(System.getProperties());
    System.out.println(authToken.getUserID());
    System.out.println(prop.getProperty("user.home"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="noindex, nofollow" />
<script src="common/fck_dialog_common.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
var dialog = window.parent ;
var oEditor = dialog.InnerDialogLoaded() ;
var FCK = oEditor.FCK;
var FCKTools	= oEditor.FCKTools ;
var FCKConfig	= oEditor.FCKConfig ;
var FCKBrowserInfo = oEditor.FCKBrowserInfo ;

window.onload = function ()
//function f1()
{
    // First of all, translate the dialog box texts
    oEditor.FCKLanguageManager.TranslatePage(document) ;

    var sPastingType = dialog.Args().CustomValue ;

    //alert(sPastingType);

    if ( sPastingType == 'Word' || sPastingType == 'Security' )
    {
        if ( sPastingType == 'Security' )
            document.getElementById( 'xSecurityMsg' ).style.display = '' ;

        // For document.domain compatibility (#123) we must do all the magic in
        // the URL for IE.

        var sFrameUrl = !oEditor.FCK_IS_CUSTOM_DOMAIN || !FCKBrowserInfo.IsIE ?
                        'javascript:void(0)' :
                        'javascript:void( (function(){' +
                        'document.open() ;' +
                        'document.domain=\'' + document.domain + '\' ;' +
                        'document.write(\'<html><head><script>window.onerror = function() { return true ; };<\/script><\/head><body><\/body><\/html>\') ;' +
                        'document.close() ;' +
                        'document.body.contentEditable = true ;' +
                        'window.focus() ;' +
                        '})() )' ;


        var eFrameSpace = document.getElementById( 'xFrameSpace');
        eFrameSpace.innerHTML = '<iframe id="frmData" src="' + sFrameUrl + '" ' +
                                'height="98%" width="99%" frameborder="0" style="border: #000000 1px; background-color: #ffffff"><\/iframe>' ;

        var oFrame = eFrameSpace.firstChild ;

        if ( !oEditor.FCK_IS_CUSTOM_DOMAIN || !FCKBrowserInfo.IsIE )
        {
            //访问剪裁板并获取剪裁板的内容
            //var sHTML = FCK.GetClipboardHTML();
            //document.uploadpic.calljs("jjjjjj");
            //alert(sHTML);
            // Avoid errors if the pasted content has any script that fails: #389
            var oDoc = oFrame.contentWindow.document ;
            oDoc.open() ;
            oDoc.write('<html><head><script>window.onerror = function() { return true ; };<\/script><\/head><body>');
            oDoc.write('<\/body><\/html>');
            oDoc.close() ;

            if ( FCKBrowserInfo.IsIE ) {
                oDoc.body.contentEditable = true ;
            } else {
                oDoc.designMode = 'on' ;
            }
            oFrame.contentWindow.focus();
        }
    } else {
        document.getElementById('txtData').style.display = '' ;
        SelectField( 'txtData' ) ;
    }

    if ( sPastingType != 'Word' )
        document.getElementById('oWordCommands').style.display = 'none' ;

    dialog.SetOkButton( true ) ;
    dialog.SetAutoSize( true ) ;
}

function Ok()
{
    // Before doing anything, save undo snapshot.
    oEditor.FCKUndo.SaveUndoStep() ;

    var sHtml ;

    var sPastingType = dialog.Args().CustomValue ;

    if ( sPastingType == 'Word' || sPastingType == 'Security' )
    {
        var oFrame = document.getElementById('frmData') ;
        var oBody ;

        if ( oFrame.contentDocument )
            oBody = oFrame.contentDocument.body ;
        else
            oBody = oFrame.contentWindow.document.body ;

        if ( sPastingType == 'Word' )
        {
            // If a plugin creates a FCK.CustomCleanWord function it will be called instead of the default one
            if ( typeof( FCK.CustomCleanWord ) == 'function' )
                sHtml = FCK.CustomCleanWord( oBody, document.getElementById('chkRemoveFont').checked, document.getElementById('chkRemoveStyles').checked ) ;
            else
                sHtml = CleanWord( oBody, document.getElementById('chkRemoveFont').checked, document.getElementById('chkRemoveStyles').checked ) ;
        }
        else
            sHtml = oBody.innerHTML ;

        // Fix relative anchor URLs (IE automatically adds the current page URL).
        var re = new RegExp( window.location + "#", "g" ) ;
        sHtml = sHtml.replace( re, '#');
    }
    else
    {
        sHtml = oEditor.FCKTools.HTMLEncode( document.getElementById('txtData').value )  ;
        sHtml = FCKTools.ProcessLineBreaks( oEditor, FCKConfig, sHtml ) ;

        // FCK.InsertHtml() does not work for us, since document fragments cannot contain node fragments. :(
        // Use the marker method instead. It's primitive, but it works.
        var range = new oEditor.FCKDomRange( oEditor.FCK.EditorWindow ) ;
        var oDoc = oEditor.FCK.EditorDocument ;
        dialog.Selection.EnsureSelection() ;
        range.MoveToSelection() ;
        range.DeleteContents() ;
        var marker = [] ;
        for ( var i = 0 ; i < 5 ; i++ )
            marker.push( parseInt(Math.random() * 100000, 10 ) ) ;
        marker = marker.join( "" ) ;
        range.InsertNode ( oDoc.createTextNode( marker ) ) ;
        var bookmark = range.CreateBookmark() ;

        // Now we've got a marker indicating the paste position in the editor document.
        // Find its position in the HTML code.
        var htmlString = oDoc.body.innerHTML ;
        var index = htmlString.indexOf( marker ) ;

        // Split it the HTML code up, add the code we generated, and put them back together.
        var htmlList = [] ;
        htmlList.push( htmlString.substr( 0, index ) ) ;
        htmlList.push( sHtml ) ;
        htmlList.push( htmlString.substr( index + marker.length ) ) ;
        htmlString = htmlList.join( "" ) ;

        if ( oEditor.FCKBrowserInfo.IsIE )
            oEditor.FCK.SetInnerHtml( htmlString ) ;
        else
            oDoc.body.innerHTML = htmlString ;

        range.MoveToBookmark( bookmark ) ;
        range.Collapse( false ) ;
        range.Select() ;
        range.Release() ;
        return true ;
    }

    oEditor.FCK.InsertHtml( sHtml ) ;

    return true ;
}

// This function will be called from the PasteFromWord dialog (fck_paste.html)
// Input: oNode a DOM node that contains the raw paste from the clipboard
// bIgnoreFont, bRemoveStyles booleans according to the values set in the dialog
// Output: the cleaned string
function CleanWord( oNode, bIgnoreFont, bRemoveStyles )
{
    var html = oNode.innerHTML ;

    html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
    html = html.replace(/<o:p>[\s\S]*?<\/o:p>/g, '&nbsp;') ;

    // Remove mso-xxx styles.
    html = html.replace( /\s*mso-[^:]+:[^;"]+;?/gi, '' ) ;

    // Remove margin styles.
    html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*;/gi, '' ) ;
    html = html.replace( /\s*MARGIN: 0cm 0cm 0pt\s*"/gi, "\"" ) ;

    html = html.replace( /\s*TEXT-INDENT: 0cm\s*;/gi, '' ) ;
    html = html.replace( /\s*TEXT-INDENT: 0cm\s*"/gi, "\"" ) ;

    html = html.replace( /\s*TEXT-ALIGN: [^\s;]+;?"/gi, "\"" ) ;

    html = html.replace( /\s*PAGE-BREAK-BEFORE: [^\s;]+;?"/gi, "\"" ) ;

    html = html.replace( /\s*FONT-VARIANT: [^\s;]+;?"/gi, "\"" ) ;

    html = html.replace( /\s*tab-stops:[^;"]*;?/gi, '' ) ;
    html = html.replace( /\s*tab-stops:[^"]*/gi, '' ) ;

    // Remove FONT face attributes.
    if ( bIgnoreFont )
    {
        html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
        html = html.replace( /\s*face=[^ >]*/gi, '' ) ;

        html = html.replace( /\s*FONT-FAMILY:[^;"]*;?/gi, '' ) ;
    }

    // Remove Class attributes
    html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3") ;

    // Remove styles.
    if ( bRemoveStyles )
        html = html.replace( /<(\w[^>]*) style="([^\"]*)"([^>]*)/gi, "<$1$3" ) ;

    // Remove style, meta and link tags
    html = html.replace( /<STYLE[^>]*>[\s\S]*?<\/STYLE[^>]*>/gi, '' ) ;
    html = html.replace( /<(?:META|LINK)[^>]*>\s*/gi, '' ) ;

    // Remove empty styles.
    html =  html.replace( /\s*style="\s*"/gi, '' ) ;

    html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/gi, '&nbsp;' ) ;

    html = html.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;

    // Remove Lang attributes
    html = html.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3") ;

    html = html.replace( /<SPAN\s*>([\s\S]*?)<\/SPAN>/gi, '$1' ) ;

    html = html.replace( /<FONT\s*>([\s\S]*?)<\/FONT>/gi, '$1' ) ;

    // Remove XML elements and declarations
    html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;

    // Remove w: tags with contents.
    html = html.replace( /<w:[^>]*>[\s\S]*?<\/w:[^>]*>/gi, '' ) ;

    // Remove Tags with XML namespace declarations: <o:p><\/o:p>
    html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;

    // Remove comments [SF BUG-1481861].
    html = html.replace(/<\!--[\s\S]*?-->/g, '' ) ;

    html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;

    html = html.replace( /<H\d>\s*<\/H\d>/gi, '' ) ;

    // Remove "display:none" tags.
    html = html.replace( /<(\w+)[^>]*\sstyle="[^"]*DISPLAY\s?:\s?none[\s\S]*?<\/\1>/ig, '' ) ;

    // Remove language tags
    html = html.replace( /<(\w[^>]*) language=([^ |>]*)([^>]*)/gi, "<$1$3") ;

    // Remove onmouseover and onmouseout events (from MS Word comments effect)
    html = html.replace( /<(\w[^>]*) onmouseover="([^\"]*)"([^>]*)/gi, "<$1$3") ;
    html = html.replace( /<(\w[^>]*) onmouseout="([^\"]*)"([^>]*)/gi, "<$1$3") ;

    if ( FCKConfig.CleanWordKeepsStructure )
    {
        // The original <Hn> tag send from Word is something like this: <Hn style="margin-top:0px;margin-bottom:0px">
        html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;

        // Word likes to insert extra <font> tags, when using MSIE. (Wierd).
        html = html.replace( /<(H\d)><FONT[^>]*>([\s\S]*?)<\/FONT><\/\1>/gi, '<$1>$2<\/$1>' );
        html = html.replace( /<(H\d)><EM>([\s\S]*?)<\/EM><\/\1>/gi, '<$1>$2<\/$1>' );
    }
    else
    {
        html = html.replace( /<H1([^>]*)>/gi, '<div$1><b><font size="6">' ) ;
        html = html.replace( /<H2([^>]*)>/gi, '<div$1><b><font size="5">' ) ;
        html = html.replace( /<H3([^>]*)>/gi, '<div$1><b><font size="4">' ) ;
        html = html.replace( /<H4([^>]*)>/gi, '<div$1><b><font size="3">' ) ;
        html = html.replace( /<H5([^>]*)>/gi, '<div$1><b><font size="2">' ) ;
        html = html.replace( /<H6([^>]*)>/gi, '<div$1><b><font size="1">' ) ;

        html = html.replace( /<\/H\d>/gi, '<\/font><\/b><\/div>' ) ;

        // Transform <P> to <DIV>
        var re = new RegExp( '(<P)([^>]*>[\\s\\S]*?)(<\/P>)', 'gi' ) ;	// Different because of a IE 5.0 error
        html = html.replace( re, '<div$2<\/div>' ) ;

        // Remove empty tags (three times, just to be sure).
        // This also removes any empty anchor
        html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
        html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
        html = html.replace( /<([^\s>]+)(\s[^>]*)?>\s*<\/\1>/g, '' ) ;
    }
    return html ;
}

function setText() {
    alert("父窗口函数");
}

function CreateInput(){
    var input = document.createElement("input");
    input.type = "text";
    input.id = "inputText";
    input.value = "这个文本框是JS创建的";
    input.className = "TextStyle";
    input.style.width = "300px";
    input.style.height = "16px";
    input.style.lineHeight = "16px";
    input.style.border = "1px solid #006699";
    input.style.font = "12px 'Microsoft Sans Serif'";
    input.style.padding = "2px";
    input.style.color = "#006699";
    document.getElementById("showText").appendChild(input);
}

if (window.Event)
    document.captureEvents(Event.MOUSEUP);

function nocontextmenu()
{
    event.cancelBubble = true
    event.returnValue = false;
    return false;
}

function norightclick(e)
{
    if (window.Event)
    {
        if (e.which == 2 || e.which == 3)
            return false;
    }
    else
    if (event.button == 2 || event.button == 3)
    {
        event.cancelBubble = true
        event.returnValue = false;
        return false;
    }
}

document.oncontextmenu = nocontextmenu;    // for IE5+
document.onmousedown = norightclick;       // for all others
//-->
</script>
</head>
<body>
<table cellspacing="0" cellpadding="0" width="100%" border="0" style="height: 98%">
    <form name="pasteform" id="pasteformid" method="post" enctype="multipart/form-data">
        <tr>
            <td colspan="2"><div id="showText" style.display="none"></div></td>
        </tr>
        <tr>
            <td>
                <div id="xSecurityMsg" style="display: none">
					<span fcklang="DlgPasteSec">Because of your browser security settings,
						the editor is not able to access your clipboard data directly. You are required
						to paste it again in this window.</span><br />
                    &nbsp;
                </div>
                <div>
					<span fcklang="DlgPasteMsg2">Please paste inside the following box using the keyboard
						(<strong>Ctrl+V</strong>) and hit <strong>OK</strong>.</span><br />
                    &nbsp;
                </div>
            </td>
        </tr>
        <tr>
            <td id="xFrameSpace" valign="top" height="100%" style="border: #000000 1px solid">
                <textarea id="txtData" name="sdata" cols="80" rows="5" style="border: #000000 1px; display: none;width: 99%; height: 98%" value="">hello word</textarea>
            </td>
        </tr>
        <tr id="oWordCommands">
            <td>
                <input type="hidden" name="cbdata" value="">
                <input id="chkRemoveFont" type="checkbox" checked="checked" />
                <label for="chkRemoveFont" fcklang="DlgPasteIgnoreFont">Ignore Font Face definitions</label>
                <br />
                <input id="chkRemoveStyles" type="checkbox" />
                <label for="chkRemoveStyles" fcklang="DlgPasteRemoveStyles">Remove Styles definitions</label>
            </td>
        </tr>
    </form>
</table>
<table border=0 width="100%" align=center>
<tr>
    <td align=center>
        <applet codebase = "/webbuilder/" name="uploadpic"
                code     = "com.bizwink.applets.SwingApplet.class"
                width    = "300"
                height   = "10"
                align    = "middle">
        </applet>
    </td>
</tr>
</table>
</body>
</html>
<script type="text/javascript">
    var sHTML = FCK.GetClipboardHTML();
    var s1 = sHTML.replace(/&nbsp;/g, "");
    s1 = s1.replace(/</g, "&lt;");
    s1 = s1.replace(/>/g, "&gt;");
    s1 = s1.replace(/"/g, "&quot;");
    alert(s1);
    document.uploadpic.setText(s1);
    document.uploadpic.calljs("hello word");
</script>