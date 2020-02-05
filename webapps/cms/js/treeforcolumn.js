var oPopup = window.createPopup();

function showmenuie5(id, rightid,parentid)
{
    var height = 183;

    parent.frames['cmsleft'].document.all('d01').onclick = "parent.go(" + id + "," + rightid + "," + parentid + ",1);";
    parent.frames['cmsleft'].document.all('d02').onclick = "parent.go(" + id + "," + rightid + "," + parentid + ",2);";
    parent.frames['cmsleft'].document.all('d03').onclick = "parent.go(" + id + "," + rightid + "," + parentid + ",3);";
    parent.frames['cmsleft'].document.all('d04').onclick = "parent.go(" + id + "," + rightid + "," + parentid +  ",4);";
    parent.frames['cmsleft'].document.all('d05').onclick = "parent.go(" + id + "," + rightid + "," + parentid +  ",5);";
    parent.frames['cmsleft'].document.all('d06').onclick = "parent.go(" + id + "," + rightid + "," + parentid +  ",6);";
    parent.frames['cmsleft'].document.all('d07').onclick = "parent.go(" + id + "," + rightid + "," + parentid +  ",7);";
    parent.frames['cmsleft'].document.all('d08').onclick = "parent.go(" + id + "," + rightid + "," + parentid +  ",8);";
    parent.frames['cmsleft'].document.all('d09').onclick = "parent.go(" + id + "," + rightid + "," + parentid +  ",9);";

    var oPopDocument = oPopup.document;
    oPopDocument.open();
    oPopDocument.write("<head><link rel=stylesheet type=text/css href=../images/MenuArea.css></head><body scroll=no>" + parent.frames['cmsleft'].document.all('ie5menu').innerHTML);
    oPopDocument.close();
    oPopup.show(event.clientX, event.clientY, 120, height, treeRange);
    return false;
}

function go(columnID, rightid,parentid, flag)
{
    oPopup.hide();
    if (flag == 1) {
        window.open("createcolumn.jsp?parentID=" + columnID + "&rightid=" + rightid,"createcolumn");
    } else if (flag == 2) {
        window.open("editcolumn.jsp?parentID=" + columnID + "&rightid=" + rightid,"updatecolumn");
    } else if (flag == 3) {
        window.open("removecolumn.jsp?ID=" + columnID + "&rightid=" + rightid,"deletecolumn");
    } else if (flag == 4) {
        window.open("editattr.jsp?from=1", "extendattributes", "width=600,height=400,left=200,right=200,scrollbars,status");
    } else if (flag == 5) {
        var winStr = "add_column_audit.jsp?columnID=" + columnID;
        var retval = showModalDialog(winStr, "column_audit_rules_add", "font-family:Verdana; font-size:12; dialogWidth:37em; dialogHeight:30em; status=no");
        //if (retval == "ok") history.go(0);
    } else if (flag == 6) {
        parent.frames['cmsright'].location = "auditarticle.jsp?column=" + columnID;
    } else if (flag == 7) {
        parent.frames['cmsright'].location = "returnarticle.jsp?column=" + columnID;
    } else if (flag == 8) {
        parent.frames['cmsright'].location = "archivearticle.jsp?column=" + columnID;
    } else if (flag == 9) {
        parent.frames['cmsright'].location = "unusedarticle.jsp?column=" + columnID;
    }
    return;
}