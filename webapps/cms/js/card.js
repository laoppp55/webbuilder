function checkcard()
{
    var gettouser = document.sendcardForm.touser.value;
    if(gettouser=="")
    {
        alert("请填写接收人姓名");
        document.sendcardForm.touser.focus();
        return false;
    }
    var gettoemail = document.sendcardForm.toemail.value;
    if(gettoemail=="")
    {
        alert("请填写接收人邮件地址");
        document.sendcardForm.toemail.focus();
        return false;
    }
    else
    {
        var regm = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if (!gettoemail.match(regm))
        {
            alert("email地址格式错误或含有非法字符!");
            document.sendcardForm.toemail.value = "";
            document.sendcardForm.toemail.focus();
            return false;
        }
    }
    var gettitle = document.sendcardForm.title.value;
    if(gettitle=="")
    {
        alert("请填写邮件标题");
        document.sendcardForm.title.focus();
        return false;
    }
    var getfromuser = document.sendcardForm.fromuser.value;
    if(getfromuser=="")
    {
        alert("请填写发送人姓名");
        document.sendcardForm.fromuser.focus();
        return false;
    }
    var getfromemail = document.sendcardForm.fromemail.value;
    if(getfromemail=="")
    {
        alert("请填写发送人邮件地址");
        document.sendcardForm.fromemail.focus();
        return false;
    }
    else
    {
        var regm = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if (!getfromemail.match(regm))
        {
            alert("email地址格式错误或含有非法字符!");
            document.sendcardForm.fromemail.value = "";
            document.sendcardForm.fromemail.focus();
            return false;
        }
    }
}