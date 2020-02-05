var oPopup = window.createPopup();

function showmenuie5(id, rightid)
{
  var height = 183;

  parent.frames['cmsleft'].document.all('d01').onclick = "parent.go(" + id + "," + rightid + ",1);";
  parent.frames['cmsleft'].document.all('d02').onclick = "parent.go(" + id + "," + rightid + ",2);";
  parent.frames['cmsleft'].document.all('d03').onclick = "parent.go(" + id + "," + rightid + ",3);";
  parent.frames['cmsleft'].document.all('d04').onclick = "parent.go(" + id + "," + rightid + ",4);";
  parent.frames['cmsleft'].document.all('d05').onclick = "parent.go(" + id + "," + rightid + ",5);";
  parent.frames['cmsleft'].document.all('d06').onclick = "parent.go(" + id + "," + rightid + ",6);";
  parent.frames['cmsleft'].document.all('d07').onclick = "parent.go(" + id + "," + rightid + ",7);";
  parent.frames['cmsleft'].document.all('d08').onclick = "parent.go(" + id + "," + rightid + ",8);";
  parent.frames['cmsleft'].document.all('d09').onclick = "parent.go(" + id + "," + rightid + ",9);";

  var oPopDocument = oPopup.document;
  oPopDocument.open();
  oPopDocument.write("<head><link rel=stylesheet type=text/css href=../css/MenuArea.css></head><body scroll=no>" + parent.frames['cmsleft'].document.all('ie5menu').innerHTML);
  oPopDocument.close();
  oPopup.show(event.clientX, event.clientY, 120, height, treeRange);
  return false;
}

function go(columnID, rightid, flag)
{
  oPopup.hide();
  if (flag == 1) {
    parent.frames['cmsright'].location = "articles.jsp?rightid=" + rightid + "&column=" + columnID;
  } else if (flag == 2) {
    parent.frames['cmsright'].location = "../upload/listuploadfiles.jsp?column=" + columnID;
  } else if (flag == 3) {
    parent.frames['cmsright'].location = "../publish/articles.jsp?rightid=" + rightid + "&column=" + columnID;
  } else if (flag == 4) {
    parent.frames['cmsright'].location = "../publish/published.jsp?column=" + columnID;
  } else if (flag == 5) {
    parent.frames['cmsright'].location = "../publish/publishfailed.jsp?column=" + columnID;
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