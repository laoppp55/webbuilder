<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../../style/global.css">
<title>文件上传</title>
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
    var str = cname.value+"(_"+e_name+")<input id='[BIZWINK_ID]"+cname.value+"--1[/BIZWINK_ID]' type=file name='[BIZWINK_NAME]_"+e_name+"[/BIZWINK_NAME]' size=20>宽<input name=_"+e_name+"_width size=4 value="+width.value+">高<input name=_"+e_name+"_height size=4 value="+height.value+">[BIZWINK_SPACE][BIZWINK_SPACE]";
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
<br>
<table border="0" width="100%">
    <tr height=30>
      <td><p align="right">属性中文名称：</p></td>
      <td><input type="text" name="cname" size="14" maxlength=40></td>
    </tr>
    <tr height=30>
      <td width="40%"><p align="right">属性英文名称：</p></td>
      <td width="60%"><input type="text" name="ename" size="14" maxlength=40>不能重复</td>
    </tr>
    <tr height=30>
      <td width="40%"><p align="right">宽度：</p></td>
      <td width="60%"><p align="left"><input type="text" name="width" size="8" maxlength=4>&nbsp;px</p></td>
    </tr>
    <tr height=30>
      <td width="40%"><p align="right">高度：</p></td>
      <td width="60%"><p align="left"><input type="text" name="height" size="8" maxlength=4>&nbsp;px</p></td>
    </tr>
    <tr height=55>
      <td colspan=2 align="center">
        <input type="button" value="  OK  " name="ok" onclick="check()" style="font-family: Arial; font-size: 8pt; border-style: solid; border-width: 1">&nbsp;&nbsp;
        <input type="button" value="Cancel" name="cancel" onclick="cancel()" style="font-family: Arial; font-size: 8pt; border-style: solid; border-width: 1">
      </td>
    </tr>
</table>

</body>
</html>
