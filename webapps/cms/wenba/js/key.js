function GetXmlData() {
    var returnstr = "";

    var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
    xmlDoc.async = "false";
    xmlDoc.load("keylink.xml");
    if (xmlDoc.parseError.errorCode == 0)
    {
        var j = xmlDoc.documentElement.childNodes.length;

        for (var i = 0; i < j; i++) {
            var keyword = xmlDoc.documentElement.childNodes[i];
            var key = keyword.selectNodes("key")(0).text;
            var url = keyword.selectNodes("url")(0).text;
            var title = keyword.selectNodes("title")(0).text;
            returnstr = returnstr + key + "@" + url + "@" + title + "<!--key-->";
        }
        returnstr = returnstr.substring(0, returnstr.lastIndexOf("<!--key-->"));
    }
    return returnstr;
}

function Trim() {
    return this.replace(/\s+$|^\s+/g, "");
}
String.prototype.Trim = Trim;

var content = document.getElementById('biz_cms_content').innerHTML;
var keywordlist = GetXmlData();
keywordlist = keywordlist.Trim();
var keywords = keywordlist.split("<!--key-->");

var key,url,title;
for (var i = keywords.length - 1; i >= 0; i--) {
    var keystr = keywords[i];
    key = keystr.substring(0, keystr.indexOf("@"));
    url = keystr.substring(keystr.indexOf("@") + 1, keystr.lastIndexOf("@"));
    title = keystr.substring(keystr.lastIndexOf("@") + 1);

    //var replacestr = "<a href=" + url + " title=" + title + ">" + key + "</a>";
    var replacestr = "<a href=" + url + " target=_blank>" + key + "</a>";
    content = content.replace(new RegExp(key, "gm"), replacestr);
}
document.getElementById('biz_cms_content').innerHTML = content;