var ColorHex=new Array('00','33','66','99','CC','FF')
var SpColorHex=new Array('FF0000','00FF00','0000FF','FFFF00','00FFFF','FF00FF')
var current=null

function intocolor()
{
    var colorTable=''
    for (i=0;i<2;i++)
    {
        for (j=0;j<6;j++)
        {
            colorTable=colorTable+'<tr height=12>'
            colorTable=colorTable+'<td width=11 style="background-color:#000000">'

            if (i==0){
                colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[j]+ColorHex[j]+ColorHex[j]+'">'}
            else{
                colorTable=colorTable+'<td width=11 style="background-color:#'+SpColorHex[j]+'">'}


            colorTable=colorTable+'<td width=11 style="background-color:#000000">'
            for (k=0;k<3;k++)
            {
                for (l=0;l<6;l++)
                {
                    colorTable=colorTable+'<td width=11 style="background-color:#'+ColorHex[k+i*3]+ColorHex[l]+ColorHex[j]+'">'
                }
            }
        }
    }
    colorTable='<table width=253 border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse" bordercolor="000000">'
            +'<tr height=30><td colspan=21 bgcolor=#cccccc>'
            +'<table cellpadding="0" cellspacing="1" border="0" style="border-collapse: collapse">'
            +'<tr><td width="3"><td><input type="text" name="DisColor" id="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00"></td>'
            +'<td width="3"><td><input type="text" name="HexColor" id="HexColor" size="7" style="border:inset 1px;font-family:Arial;" value="#000000"></td><td onclick="doclose()">关闭颜色</td></tr></table></td></table>'
            +'<table border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000" onmouseover="doOver()" onmouseout="doOut()" onclick="doclick()" style="cursor:hand;">'
            +colorTable+'</table>';
    colorpanel.innerHTML=colorTable
}


//将颜色值字母大写
function doOver() {
    if ((event.srcElement.tagName=="TD") && (current!=event.srcElement)) {
        if (current!=null){current.style.backgroundColor = current._background}
        event.srcElement._background = event.srcElement.style.backgroundColor
        document.getElementById('DisColor').style.backgroundColor = event.srcElement.style.backgroundColor
        document.getElementById('HexColor').value = event.srcElement.style.backgroundColor.toUpperCase();
        event.srcElement.style.backgroundColor = "white"
        current = event.srcElement
    }
}


//将颜色值字母大写
function doOut() {

    if (current!=null) current.style.backgroundColor = current._background.toUpperCase();
}


function doclick()
{
    if (event.srcElement.tagName == "TD")
    {
        var clr = event.srcElement._background;
        clr = clr.toUpperCase(); //将颜色值大写
        if (targetElement)
        {
            //给目标无件设置颜色值
            targetElement.value = clr;
        }
        DisplayClrDlg(false);
        return clr;
    }
}


function doclose()
{

    DisplayClrDlg(false);

}

//应用颜色对话框必须注意两点：
//颜色对话框 id : colorpanel 不能变
//触发颜色对话框显示的文本框（或其它）必须有 alt 属性，且值为 clrDlg（不能忽略大小写）

//如果需要在一个html多次运用颜色选择器,只需要增加以下改进部分

var targetElement = null; //接收颜色对话框返回值的元素

//当点下鼠标时，确定显示还是隐藏颜色对话框
//点击颜色对话框以外其它区域时，让对话框隐藏
//点击颜色对话框色区时，由 doclick 函数来隐藏对话框
function OnDocumentClick()
{
    var srcElement = event.srcElement;
    if (srcElement.alt == "clrDlg"||srcElement.alt=="color1"||srcElement.alt=="color2")
    {
        //显示颜色对话框
        targetElement = event.srcElement;
        DisplayClrDlg(true);
    }




    else
    {
        //是否是在颜色对话框上点击的
        while (srcElement && srcElement.id!="colorpanel")
        {
            srcElement = srcElement.parentElement;
        }
        if (!srcElement)
        {
            //不是在颜色对话框上点击的
            DisplayClrDlg(false);
        }
    }

}

//显示颜色对话框
//display 决定显示还是隐藏
//自动确定显示位置
function DisplayClrDlg(display)
{
    var clrPanel = document.getElementById("colorpanel");
    if (display)
    {
        var left = document.body.scrollLeft + event.clientX;
        var top = document.body.scrollTop + event.clientY;
        if (event.clientX+clrPanel.style.pixelWidth > document.body.clientWidth)
        {
            //对话框显示在鼠标右方时，会出现遮挡，将其显示在鼠标左方
            left -= clrPanel.style.pixelWidth;
        }
        if (event.clientY+clrPanel.style.pixelHeight > document.body.clientHeight)
        {
            //对话框显示在鼠标下方时，会出现遮挡，将其显示在鼠标上方
            top -= clrPanel.style.pixelHeight;
        }

        clrPanel.style.pixelLeft = left;
        clrPanel.style.pixelTop = top;
        clrPanel.style.display = "block";
    }
    else
    {
        clrPanel.style.display = "none";
    }
}
