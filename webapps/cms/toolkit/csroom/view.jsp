<%@page import="com.bizwink.cms.toolkit.addresslist.*,
                java.util.*,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=GBK"
%>
<%
  int id = ParamUtil.getIntParameter(request, "id", 0);
  AddressList addresslist = new AddressList();
  IAddressListManager addresslistMgr = AddressListPeer.getInstance();
  addresslist = addresslistMgr.getA_AddressList(id);

  String username = addresslist.getName();
  String corporation = addresslist.getCorporation();
  String address = addresslist.getAddress();
  String postcode = addresslist.getPostCode();
  String mobilephone = addresslist.getMobilephone();
  String phone = addresslist.getPhone();
  String fax = addresslist.getFax();
  String email = addresslist.getEmail();
%>
<HTML><HEAD><TITLE>New Page 1</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<LINK href="images/common.css" type=text/css rel=stylesheet>
<LINK href="images/forum.css" type=text/css rel=stylesheet>
</HEAD>
<BODY>
<CENTER>
<TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=497
borderColorLight=#008000 border=1>
  <TBODY>
  <TR>
    <TD width=493 bgColor=#33ccff colSpan=2 height=32>
      <P align=center>联系人信息</P></TD></TR>
  <TR>
    <TD align=right width=113 height=32>联系人姓名：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=username==null?"--":username%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>公司名称：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=corporation==null?"--":corporation%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>地址：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=address==null?"--":address%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>邮编：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=postcode==null?"--":postcode%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>手机：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=mobilephone==null?"--":mobilephone%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>电话：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=phone==null?"--":phone%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>传真：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=fax==null?"--":fax%> </TD></TR>
  <TR>
    <TD align=right width=113 height=32>电子邮件：</TD>
    <TD align=left width=378 height=32>&nbsp;<%=email==null?"--":email%> </TD></TR>
</TBODY></TABLE></CENTER><BR><BR>
<CENTER><INPUT onclick=javascript:history.go(-1); type=button value=" 返 回 ">
</CENTER></BODY></HTML>
