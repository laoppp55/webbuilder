<html>
<head>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../../style/global.css">
<title>�༭��</title>
<script language=javascript>
var retstr = "";
function check()
{
    if (cname.value == "")
    {
    	alert("�����������������ƣ�");
    	cname.focus();
    }
    else if (ename.value == "")
    {
    	alert("����������Ӣ�����ƣ�");
    	ename.focus();
    }
    else if (!checkEname(ename.value))
    {
        alert("��������ȷ��Ӣ�����ƣ�");
        ename.focus();
    }
    else
    {
        var e_name = ename.value.toLowerCase();
        retstr = "yes";
        var str = cname.value+"(_"+e_name+")<textarea Editor id='[BIZWINK_ID]"+cname.value+"--"+datatype.value+"[/BIZWINK_ID]' name='[BIZWINK_NAME]_"+e_name+"[/BIZWINK_NAME]' rows=5 cols=20></textarea><input type=button value=�༭ onclick=Editor('_"+e_name+"');>";
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
      <td><p align="right">�����������ƣ�</p></td>
      <td><input type="text" name="cname" size="14" maxlength=40></td>
    </tr>
    <tr height=25>
      <td width="40%"><p align="right">����Ӣ�����ƣ�</p></td>
      <td width="60%"><input type="text" name="ename" size="14" maxlength=40>�����ظ�</td>
    </tr>
    <tr height=25>
      <td><p align="right">�����������ͣ�</p></td>
      <td><select name=datatype style="width:110"><option value=1>�ַ�����</option><option value=3>�ı���</option>/td>
    </tr>
    <tr height=25>
      <td><p align="right">�Ƿ�Ϊ���ֶΣ�</p></td>
      <td><input type="checkbox" name="key">&nbsp;(ֻ����һ�����ֶ�)</td>
    </tr>
    <tr height=25>
      <td colspan=2>ע�⣺<br>
        һ���ڱ༭����˫�����ı��򣬿�����Ĭ��ֵ��<br>
        �����ַ��������ݳ��Ȳ�����255���ַ�������255���ַ������ı��͡�
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