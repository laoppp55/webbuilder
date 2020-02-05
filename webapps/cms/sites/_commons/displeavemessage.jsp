<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int leavemessageid = ParamUtil.getIntParameter(request,"lid",0);
    IWordManager wMgr =  LeaveWordPeer.getInstance();
    Word word = new Word();
    word = wMgr.getAWord(leavemessageid);
%>
<html>
<head>
    <title>北京市无线管理局-局长信箱</title>
    <link href="/css/default.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#CCCCCC" leftmargin="0" topmargin="0">
<table width="778" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="1" bgcolor="#000000"></td>
    <td width="776"><img src="images/em001.gif" width="776" height="40"></td>
    <td width="1" bgcolor="#000000"></td>
  </tr>
</table>
<table width="778" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td width="1" bgcolor="#000000"></td>
    <td width="776">

      <table width="550" border="0" align="center" cellpadding="4" cellspacing="0">
        <tr>
          <td><strong><font color="#0000FF">·<%=word.getTitle()%></font></strong></td>
        </tr>
      </table>
      <table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td background="../pingyi/images/py002.gif"><img src="../pingyi/images/py002.gif" width="8" height="10"></td>
        </tr>
      </table>

      <br>
      <table width="550" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#CCCCCC">
        <tr>
          <td bgcolor="f5f5f5"><%=word.getContent()%> </td>
        </tr>
        <tr>
          <td align="right" bgcolor="f5f5f5">提交时间：<%=word.getWritedate().toString()%></td>
        </tr>
      </table>
      <br> <table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td background="../pingyi/images/py004.gif"><img src="../pingyi/images/py004.gif" width="12" height="8"></td>
        </tr>
      </table>
      <br>

      <table width="550" border="0" align="center" cellpadding="8" cellspacing="1" bgcolor="#CCCCCC">
        <tr>
          <td bgcolor="f5f5f5"><font color="#0000FF">回复：</font> <p><%=word.getRetcontent()%>
</p></td>
        </tr>
      </table>

      <br>
      <table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td background="../pingyi/images/py002.gif"><img src="../pingyi/images/py002.gif" width="8" height="10"></td>
        </tr>
      </table>
      <table width="200" border="0" align="center" cellpadding="8" cellspacing="0">
        <tr>
          <td><div align="center">[ <a href="javascript:window.close();">关闭窗口</a>
              ]</div></td>
        </tr>
      </table>
      <br>
      <br>
      <br>
      <table width="200" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>

    </td>
    <td width="1" bgcolor="#000000"></td>
  </tr>
</table>
<!-- #BeginLibraryItem "/Library/底.lbi" --><table width="778" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="596" height="44" rowspan="2" background="../images/rs008a.gif">
      　　 </td>
    <td width="90" height="20"><img src="../images/rs008f.gif" width="90" height="20" border="0"></td>
    <td width="2" rowspan="2"><img src="../images/rs008e.gif" width="2" height="44"></td>
    <td><a href="../zhengwu/zw-004.htm"><img src="../images/rs008g.gif" width="90" height="20" border="0"></a></td>
  </tr>
  <tr>
    <td height="24"><img src="../images/rs008b.gif" width="90" height="24"></td>
    <td><img src="../images/rs008c.gif" width="90" height="24"></td>
  </tr>
</table>
<table width="778" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><div align="center">版权所有：北京市无线电管理局</div></td>
  </tr>
</table>
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<!-- #EndLibraryItem -->
</body>
</html>