<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../../style/global.css">
<title>单行文本框</title>
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
    else
    {
        var e_name = ename.value.toLowerCase();
        retstr = "yes";
        var str = cname.value+"(_"+e_name+")<INPUT id='[BIZWINK_ID]"+cname.value+"--"+datatype.value+"[/BIZWINK_ID]' name='[BIZWINK_NAME]_"+e_name+"[/BIZWINK_NAME]' size=20>";
        if (key.checked)
            str = str + "[BIZWINK_SPACE]_" + e_name;
        else
            str = str + "[BIZWINK_SPACE]";

		window.returnValue = str;
        window.close();
    }
}

function checkEname(ename)
{
  var retstr = false;
  var regstr = /[^a-zA-Z0-9]/gi;
  
  if (regstr.exec(ename) == null)
  {
    retstr = true;
  }
  
  return retstr;
}

function cancel()
{
    if (retstr == "")  window.returnValue = "";
    window.close();
}
</script>
</head>

<body onunload="cancel()" bgcolor="#D6D3CE">
<table border="0" width="100%">
    <tr height=25>
      <td><p align="right">属性中文名称：</p></td>
      <td><input type="text" name="cname" size="14" maxlength=40></td>
    </tr>
    <tr height=25>
      <td width="40%"><p align="right">属性英文名称：</p></td>
      <td width="60%"><input type="text" name="ename" size="14" maxlength=40>不能重复</td>
    </tr>
    <tr height=25>
      <td><p align="right">属性数据类型：</p></td>
      <td><select name=datatype style="width:110"><option value=1>字符串型</option><option value=2>整数型</option><option value=3>文本型</option><option value=4>小数型</option>/td>
    </tr>
    <tr height=25>
      <td><p align="right">是否为主字段：</p></td>
      <td><input type="checkbox" name="key">&nbsp;(只允许一个主字段)</td>
    </tr>
    <tr height=25>
      <td colspan=2>注意：<br>
        一、在编辑器里双击该文本框，可添加默认值；<br>
        二、字符串型数据长度不超过255个字符；超过255个字符则用文本型。
      </td>
    </tr>
    <tr height=30>
      <td colspan=2 align="center">
        <input type="button" value="  OK  " name="ok" onclick="check()" style="font-family: Arial; font-size: 8pt; border-style: solid; border-width: 1">&nbsp;&nbsp;
        <input type="button" value="Cancel" name="cancel" onclick="cancel()" style="font-family: Arial; font-size: 8pt; border-style: solid; border-width: 1">
      </td>
    </tr>
</table>

</body>
</html>
