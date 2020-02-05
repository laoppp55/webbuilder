var oPopup = window.createPopup();

function showmenuie5(id, rightid)
{
  var height = 82;

  parent.frames['cmsleft'].document.all('d01').onclick = "parent.go(" + id + "," + rightid + ",1);";
  parent.frames['cmsleft'].document.all('d02').onclick = "parent.go(" + id + "," + rightid + ",2);";
  parent.frames['cmsleft'].document.all('d03').onclick = "parent.go(" + id + "," + rightid + ",3);";
  parent.frames['cmsleft'].document.all('d04').onclick = "parent.go(" + id + "," + rightid + ",4);";

  var oPopDocument = oPopup.document;
  oPopDocument.open();
  oPopDocument.write("<head><link rel=stylesheet type=text/css href=../images/MenuArea.css></head><body scroll=no>" + parent.frames['cmsleft'].document.all('ie5menu').innerHTML);
  oPopDocument.close();
  oPopup.show(event.clientX, event.clientY, 120, height, treeRange);
  return false;
}