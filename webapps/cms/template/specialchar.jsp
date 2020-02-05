<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.Hand
{
	cursor: pointer ;
	cursor: hand ;
}
.Sample { font-size: 24px; }
</style>
<script type="text/javascript">
var oSample ;
function insertChar(charValue)
{
	oEditor.FCKUndo.SaveUndoStep() ;
	oEditor.FCK.InsertHtml( charValue || "" ) ;
	window.parent.Cancel() ;
}

function over(td)
{
	if ( ! oSample )
		return ;
	oSample.innerHTML = td.innerHTML ;
	td.className = 'LightBackground SpecialCharsOver Hand' ;
}

function out(td)
{
	if ( ! oSample )
		return ;
	oSample.innerHTML = "&nbsp;" ;
	td.className = 'DarkBackground SpecialCharsOut Hand' ;
}

</script>
</head>
<body>
<table cellpadding="0" cellspacing="0" width="100%" height="100%">
<tr>
<td width="100%">
<table cellpadding="1" cellspacing="1" align="center" border="0" width="100%" height="100%">
<script type="text/javascript">
var aChars = ["!","&quot;","#","$","%","&amp;","\\'","(",")","*","+","-",".","/","0","1","2","3","4","5","6","7","8","9",":",";","&lt;","=","&gt;","?","@","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","[","]","^","_","`","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","{","|","}","~","&euro;","&lsquo;","&rsquo;","&rsquo;","&ldquo;","&rdquo;","&ndash;","&mdash;","&iexcl;","&cent;","&pound;","&curren;","&yen;","&brvbar;","&sect;","&uml;","&copy;","&ordf;","&laquo;","&not;","&reg;","&macr;","&deg;","&plusmn;","&sup2;","&sup3;","&acute;","&micro;","&para;","&middot;","&cedil;","&sup1;","&ordm;","&raquo;","&frac14;","&frac12;","&frac34;","&iquest;","&Agrave;","&Aacute;","&Acirc;","&Atilde;","&Auml;","&Aring;","&AElig;","&Ccedil;","&Egrave;","&Eacute;","&Ecirc;","&Euml;","&Igrave;","&Iacute;","&Icirc;","&Iuml;","&ETH;","&Ntilde;","&Ograve;","&Oacute;","&Ocirc;","&Otilde;","&Ouml;","&times;","&Oslash;","&Ugrave;","&Uacute;","&Ucirc;","&Uuml;","&Yacute;","&THORN;","&szlig;","&agrave;","&aacute;","&acirc;","&atilde;","&auml;","&aring;","&aelig;","&ccedil;","&egrave;","&eacute;","&ecirc;","&euml;","&igrave;","&iacute;","&icirc;","&iuml;","&eth;","&ntilde;","&ograve;","&oacute;","&ocirc;","&otilde;","&ouml;","&divide;","&oslash;","&ugrave;","&uacute;","&ucirc;","&uuml;","&uuml;","&yacute;","&thorn;","&yuml;","&OElig;","&oelig;","&#372;","&#374","&#373","&#375;","&sbquo;","&#8219;","&bdquo;","&hellip;","&trade;","&#9658;","&bull;","&rarr;","&rArr;","&hArr;","&diams;","&asymp;"] ;
var cols = 20 ;
var i = 0 ;
while (i < aChars.length)
{
	document.write("<TR>") ;
	for(var j = 0 ; j < cols ; j++)
	{
		if (aChars[i])
		{
			document.write('<TD width="1%" class="DarkBackground SpecialCharsOut Hand" align="center" onclick="insertChar(\'' + aChars[i].replace(/&/g, "&amp;") + '\')" onmouseover="over(this)" onmouseout="out(this)">') ;
			document.write(aChars[i]) ;
		}
		else
			document.write("<TD class='DarkBackground SpecialCharsOut'>&nbsp;") ;
		document.write("<\/TD>") ;
		i++ ;
	}
	document.write("<\/TR>") ;
}
</script>
</table>
</td>
<td nowrap>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td valign="top">
<table width="40" cellpadding="0" cellspacing="0" border="0">
<tr>
<td id="SampleTD" width="40" height="40" align="center" class="DarkBackground SpecialCharsOut Sample">&nbsp;</td>
</tr>
</table>
</td>
</tr>
</table>
</body>
</html>