var lastMouseX;
var lastMouseY;
var curPopupWindow = null;
var helpWindow = null;

function setLastMousePosition(e) {
	if (navigator.appName.indexOf("Microsoft") != -1) e = window.event;
	lastMouseX = e.screenX;
	lastMouseY = e.screenY;
}

function openClickout(url) {
	Window.open(url, "_blank", 'width=640,height=480,dependent=no,resizable=yes,toolbar=yes,status=yes,directories=yes,menubar=yes,scrollbars=1', false);
}

function openPopup(url, name, pWidth, pHeight, features, snapToLastMousePosition) {
   closePopup();
	if (snapToLastMousePosition) {
		if (lastMouseX - pWidth < 0) {
			lastMouseX = pWidth;
		}
		if (lastMouseY + pHeight > screen.height) {
			lastMouseY -= (lastMouseY + pHeight + 50) - screen.height;
		}
                lastMouseX -= pWidth;
                lastMouseY += 10;
		features +=	"screenX=" + lastMouseX + ",left=" + lastMouseX + "screenY=" + lastMouseY + ",top=" + lastMouseY;
	}
	curPopupWindow = window.open(url, name, features, false);

}

function closePopup() {
	if (curPopupWindow != null) {   
		if (!curPopupWindow.closed) {
			curPopupWindow.close();
		}
		curPopupWindow = null;
	}
}

function openLookup(baseURL,modified,searchParam) {
	if (modified == '1') baseURL = baseURL + searchParam;
	openPopup(baseURL, "lookup", 350, 300, "width=350,height=300,toolbar=no,status=no,directories=no,menubar=no,resizable=yes,scrollable=no", true);
}

function pick(form,field,val) {
	//alert(val);
	//eval("document."+form+"[\""+field+"\"].value=" + "\""+val+"\"");
        var posi = val.indexOf("-");
        var year = val.substring(0,posi);
        var buf = val.substring(posi+1);
        posi = buf.indexOf("-");
        var month = buf.substring(0,posi);
        var day = buf.substring(posi+1);
        eval("document."+form+"[\"year\"].value=" + "\""+year+"\"");
        eval("document."+form+"[\"month\"].value=" + "\""+month+"\"");
        eval("document."+form+"[\"day\"].value=" + "\""+day+"\"");
        closePopup();
	return false;
}

function openCalendar(url) {
	openPopup(url, "Calendar", 193, 145, "width=193,height=145,dependent=yes,resizable=yes,toolbar=no,status=no,directories=no,menubar=no", true);
}

function openComboBox(url) {
	openPopup(replaceChar(url, ' ', '%'), "Select", 220, 270, "width=270,height=200,dependent=yes,resizable=yes,toolbar=no,status=no,directories=no,menubar=no,scrollbars=1", true);
}

function replaceChar(s, oldchar, newchar) {
	var retval = '';
	for (i = 0; i < s.length; i++) {
		if (s.charAt(i) == ' ') {
			retval = retval + '%';
		} else {
			retval = retval + s.charAt(i);
		}
	}	
	return retval;
}

function lookupPick(formName, parentIdElementName, parentEditElementName, id, display) {
    
	var parentIdElement = "document." + formName + "[\"" + parentIdElementName + "\"]";
	var parentEditElement = "document." + formName + "[\"" + parentEditElementName + "\"]";
	eval(parentIdElement + ".value = " + "\"" + id +"\"");    
	eval(parentEditElement + ".value = " + "\"" + display +"\"");
	closePopup();
  
	return false;
}

function findPick(formName, parentIdElementName, parentEditElementName, id, display, bClose) {
    
	var parentIdElement = "document." + formName + "[\"" + parentIdElementName + "\"]";
	var parentEditElement = "document." + formName + "[\"" + parentEditElementName + "\"]";
	eval(parentIdElement + ".value = " + "\"" + id +"\"");    
	eval(parentEditElement + ".value = " + "\"" + display +"\"");
	if (bClose == '1')
		closePopup();
  
	return false;
}

function update_check(theForm) {
	if (theForm.privates.checked)
		theForm.isprivate.value=1;
	else
		theForm.isprivate.value=0;
		
	if (theForm.accountID.value=="0")
		theForm.accountID.value="1";
}

function clearcols () {
    for (var frm = 0; frm < document.forms.length; frm++) {
        for (var fld = 0; fld < document.forms[frm].elements.length; fld++) {
            var elt = document.forms[frm].elements[fld];
            if (elt.name == "c" || elt.name.substring(0,2) == "c_") {
                elt.checked = false;
            }
        }
    }
}

function setcols () {
    for (var frm = 0; frm < document.forms.length; frm++) {
        for (var fld = 0; fld < document.forms[frm].elements.length; fld++) {
            var elt = document.forms[frm].elements[fld];
            if (elt.name == "c" || elt.name.substring(0,2) == "c_") {
                elt.checked = true;
            }
        }
    }
}


function changequarter(val) {
if (val == 'current') {
document.report.startDate.value = '2001-7-1';
document.report.endDate.value = '2001-9-30';
}
if (val == 'curnext1') {
document.report.startDate.value = '2001-7-1';
document.report.endDate.value = '2001-12-31';
}
if (val == 'next1') {
document.report.startDate.value = '2001-10-1';
document.report.endDate.value = '2001-12-31';
}
if (val == 'prev1') {
document.report.startDate.value = '2001-4-1';
document.report.endDate.value = '2001-6-30';
}
if (val == 'curnext3') {
document.report.startDate.value = '2001-7-1';
document.report.endDate.value = '2002-7-1';
}
if (val == 'curfy') {
document.report.startDate.value = '2001-1-1';
document.report.endDate.value = '2001-12-31';
}
if (val == 'prevfy') {
document.report.startDate.value = '2000-1-1';
document.report.endDate.value = '2001-12-31';
}
if (val == 'prev2fy') {
document.report.startDate.value = '1999-1-1';
document.report.endDate.value = '2000-12-31';
}
if (val == 'ago2fy') {
document.report.startDate.value = '1999-1-1';
document.report.endDate.value = '1999-12-31';
}
if (val == 'nextfy') {
document.report.startDate.value = '2002-1-1';
document.report.endDate.value = '2002-12-31';
}
if (val == 'prevcurfy') {
document.report.startDate.value = '2000-1-1';
document.report.endDate.value = '2001-12-31';
}
if (val == 'prevcur2fy') {
document.report.startDate.value = '1999-1-1';
document.report.endDate.value = '2001-12-31';
}
if (val == 'curnextfy') {
document.report.startDate.value = '2001-1-1';
document.report.endDate.value = '2002-12-31';
}
return true;
}

