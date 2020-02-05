function logInputFaile(username) {

    //alert("密码错误");
    //var count = document.getElementById("userErrCoun").value;
    //var lockTim = document.getElementById("userPinLocTim").value;
    var uidErrCoun = username + "userErrCoun";
    var PinLoc = username + "userPinLocTim";
    var count = getCookie(uidErrCoun);
    var lockTim = getCookie(PinLoc);

    if (lockTim == null || lockTim == "") {
        var expireDate = new Date();
        expireDate.setTime(expireDate.getTime() + 24 * 60 * 60 * 1000);
        if (count == null || count=="") {
            //alert("密码错误 第1次");
            setCookie(uidErrCoun, 1, expireDate.toGMTString(), "/");
            return false;
        } else {
            var expireDate = new Date();
            expireDate.setTime(expireDate.getTime() + 24 * 60 * 60 * 1000);
            var count = getCookie(uidErrCoun);
            //alert("密码错误 第 "+ count +" 次");
            if (count <= 5) {
                setCookie(uidErrCoun, ++count, expireDate.toGMTString(), "/");
                return false;
            } else {
                alert("密码错误超过5次，30分钟后再试");
                var expireDate = new Date();
                expireDate.setTime(expireDate.getTime() + 30 * 60 * 1000);
                setCookie(PinLoc, "userPinLocTim", expireDate.toGMTString(), "/");
                deleteCookie(uidErrCoun, "/");
                return true;
            }
        }
    } else {
        return true;
    }
}

function getCookie(c_name)
{
if (document.cookie.length>0)
  {
  c_start=document.cookie.indexOf(c_name + "=")
  if (c_start!=-1)
    { 
    c_start=c_start + c_name.length+1 
    c_end=document.cookie.indexOf(";",c_start)
    if (c_end==-1) c_end=document.cookie.length
    return unescape(document.cookie.substring(c_start,c_end))
    } 
  }
return ""
}

function setCookie(c_name,value,expiredays)
{
var exdate=new Date()
exdate.setDate(exdate.getDate()+expiredays)
document.cookie=c_name+ "=" +escape(value)+
((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}