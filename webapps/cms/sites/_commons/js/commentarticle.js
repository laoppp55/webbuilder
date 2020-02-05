function zuijinsee(articleid)
{
    
    var objXml;
    if (window.ActiveXObject)
    {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest)
    {
        objXml = new XMLHttpRequest();
    }
    objXml.open("POST", "/_commons/addseecookie.jsp?articleid="+articleid, false);
    objXml.send(null);
    var retstr = objXml.responseText;
   // alert(retstr);
}
function getCookie(viewid,num)
{
    var objXml;
   // alert("aaaa");
   if (window.ActiveXObject)
    {
        objXml = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest)
    {
        objXml = new XMLHttpRequest();
    }
    objXml.open("POST", "/_commons/seecookie.jsp?viewid="+viewid+"&num="+num, false);
    objXml.send(null);
    var retstr = objXml.responseText;
    //alert(retstr);
    if(retstr.length>0)
    {
        document.getElementById("getcookie").innerHTML=retstr;
    }
}