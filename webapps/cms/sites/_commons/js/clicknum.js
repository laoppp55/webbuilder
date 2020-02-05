function  updateonclicknum(articleid)
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
            objXml.open("POST", "/_commons/updatearticleclick.jsp?articleid="+articleid+"&flag=0", false);
            objXml.send(null);
            var retstr = objXml.responseText;
           
            if (retstr != null && retstr.length > 0) {
                document.getElementById("onclicknum").innerHTML = retstr;
            }
}
function  getonclicknum(articleid)
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
            objXml.open("POST", "/_commons/updatearticleclick.jsp?articleid="+articleid+"&flag=1", false);
            objXml.send(null);
            var retstr = objXml.responseText;

            if (retstr != null && retstr.length > 0) {
                document.getElementById("onclicknum").innerHTML = retstr;
            }
}