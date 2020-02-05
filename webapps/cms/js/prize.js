function applicateprize(id)
{
    var objXml = new ActiveXObject("Microsoft.XMLHTTP");
    objXml.open("POST", "/checkprize.jsp?id="+id, false);
    objXml.Send();
    var valueid = objXml.responseText;
    if (valueid.indexOf("1") != -1)
    {
        alert("你的积分不足，申请失败！");
    }
    if(valueid.indexOf("2") != -1)
    {
        alert("感谢您的参与!");
    }
    if(valueid.indexOf("3") != -1)
    {
        alert("您还没有登录!");
    }
    if(valueid.indexOf("4") != -1)
    {
        alert("对不起，该抽奖已完毕！");
    }
}

function convertprize(id)
{
    var objXml = new ActiveXObject("Microsoft.XMLHTTP");
    objXml.open("POST", "/toprize.jsp?id="+id, false);
    objXml.Send();
    var valueid = objXml.responseText;
    if (valueid.indexOf("1") != -1)
    {
        alert("你的积分不足，换奖品失败！");
    }
    if(valueid.indexOf("2") != -1)
    {
        alert("感谢您的参与!");
    }
    if(valueid.indexOf("3") != -1)
    {
        alert("您还没有登录!");
    }
    if(valueid.indexOf("4") != -1)
    {
        alert("对不起，该奖品已兑换完毕！");
    }
}