<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../../style/global.css">
<title>下拉列表框</title>
<script language=javascript>
var retstr = "";
function check()
{
    if (cname.value == "")
    {
    	alert("请输入属性中文名称！");
    	cname.focus();
    }
    else if (ename.value == "")
    {
    	alert("请输入属性英文名称！");
    	ename.focus();
    }
    else if (!checkEname(ename.value))
    {
        alert("请输入正确的英文名称！");
        ename.focus();
    }
    else if (size.value == "")
    {
    	alert("请输入下拉列表框高度！");
    	size.focus();
    }
    else if (options.value == "")
    {
    	alert("请输入下拉列表框选项！");
    	options.focus();
    }
    else
    {
        retstr = "yes";
        var error = false;
        var str = "";
        var tempstr = options.value;
        
        if (tempstr.length > 0)
        {
            if (tempstr.indexOf("/") == 0)
            	tempstr = tempstr.substring(1,tempstr.length);
            if (tempstr.lastIndexOf("/") == tempstr.length-1)
            	tempstr = tempstr.substring(0,tempstr.length-1);

            var arr = tempstr.split("/");
            for (var i=0; i<arr.length; i++)
            {
                if (!checkData(datatype.value, arr[i]))
                {
                   alert("请录入正确的菜单项！");
                   return;
                }
            	str = str + "<option value='"+arr[i]+"'>"+arr[i]+"</option>";
            }
        }
        
        var e_name = ename.value.toLowerCase();
        var res = cname.value+"(_"+e_name+")<select id='[BIZWINK_ID]"+cname.value+"--"+datatype.value+"[/BIZWINK_ID]' name='[BIZWINK_NAME]_"+e_name+"[/BIZWINK_NAME]' size="+size.value+">"+str+"</select>";
        
        if (key.checked)
            res = res + "[BIZWINK_SPACE]_" + e_name;
        else
            res = res + "[BIZWINK_SPACE]";
        
        window.returnValue = res;
        window.close();
    }
}

function checkEname(ename)
{
  var retstr = false;
  var regstr = /[^a-zA-Z]/gi;
  
  if (regstr.exec(ename) == null)
  {
    retstr = true;
  }
  
  return retstr;
}

function checkData(type, data)
{
  var retstr = false;
  if (type == 2)
  {
    var regstr = /[^0-9]/gi;
    if (regstr.exec(data) == null)
      retstr = true;
  }
  else if (type == 4)
  {
    var regstr = /[^0-9.]/gi;
    if (regstr.exec(data) == null)
      retstr = true;
  }
  else
  {
    retstr = true;
  }
  return retstr;
}

function cancel()
{
    if (retstr == "") window.returnValue = "";
    window.close();
}
</script>
</head>

<body onunload="cancel()" bgcolor="#D6D3CE">
<table border="0" width="100%">
    <tr>
      <td><p align="right">属性中文名称：</p></td>
      <td><input type="text" name="cname" size="14" maxlength=40></td>
    </tr>
    <tr>
      <td width="35%"><p align="right">属性英文名称：</p></td>
      <td width="65%"><input type="text" name="ename" size="14" maxlength=40>不能重复</td>
    </tr>
    <tr>
      <td><p align="right">下拉列表高度：</p></td>
      <td><input type="text" name="size" size="4" value=1 maxlength=3></td>
    </tr>
    <tr>
      <td valign=top><p align="right">下拉列表选项：（"/"隔开）</p></td>
      <td><textarea name="options" rows="2" cols="22"></textarea></td>
    </tr>
    <tr height=22>
      <td><p align="right">属性数据类型：</p></td>
      <td><select name=datatype style="width:110"><option value=1>字符串型</option><option value=2>整数型</option><option value=3>文本型</option><option value=4>小数型</option>/td>
    </tr>
    <tr height=22>
      <td><p align="right">是否为主字段：</p></td>
      <td><input type="checkbox" name="key">&nbsp;(只允许一个主字段)</td>
    </tr>
    <tr height=25>
      <td colspan=2>注意：<br>
        一、在编辑器里双击该下拉列表框，可设置某项为默认；<br>
        二、字符串型数据长度不超过255个字符；超过255个字符则用文本型。
      </td>
    </tr>
    <tr>
      <td colspan=2 align="center">
        <input type="button" value="  OK  " name="ok" onclick="check()" style="font-family: Arial; font-size: 8pt; border-style: solid; border-width: 1">&nbsp;&nbsp;
        <input type="button" value="Cancel" name="cancel" onclick="cancel()" style="font-family: Arial; font-size: 8pt; border-style: solid; border-width: 1">
      </td>
    </tr>
</table>

</body>
</html>