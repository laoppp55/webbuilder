<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page contentType="text/html;charset=gbk" pageEncoding="gbk" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户留言</title>
<link href="/css/wzgstyle.css" rel="stylesheet" type="text/css" />
</head>

<body><table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/2010527wzg-bg.gif" style=" background-repeat:repeat-y;">
  <tr>
    <td width="233" align="left" valign="top"><table width="233" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top"><img src="/images/2010527wag-logo2.jpg" width="128" height="433" /></td>
        <td width="2"><img src="/images/2010527wzg-line2.gif" width="2" height="434" /></td>
        <td width="103" align="left" valign="top">
        <table cellspacing="0" cellpadding="0" width="103" border="0">
            <tbody>
                <tr>
                    <td height="37">&nbsp;</td>
                </tr>
                <tr>
                    <td valign="top" align="left"><a href="/"><img height="21" alt="" width="103" border="0" src="/images/2010527wag-menu1.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/yyjj/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu2.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/zjjs/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu3.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/tslf/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu4.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/dxbl/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu5.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/www_shwzg_com/_prog/displeavewords.jsp"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu6.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/mfzs/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu7.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/tszy/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu8.jpg" /></a><br />
                    <img height="2" alt="" width="103" src="/images/2010527wag-menuxu.jpg" /><br />
                    <a href="/lxwm/"><img height="28" alt="" width="103" border="0" src="/images/2010527wag-menu9.jpg" /></a><br />
                    <img height="136" alt="" width="103" src="/images/2010527wag-menulow2.jpg" /></td>
                </tr>
            </tbody>
        </table>
    </td>
      </tr>
    </table>
      <table width="233" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" valign="top"><a href="/zjjs/"><img src="/images/2010527wag-wzg2.jpg" width="233" height="336" border="0" /></a></td>
        </tr>
      </table></td>
    <td width="767" align="left" valign="top"><table width="767" height="37" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="581" align="left" valign="bottom" ><span style=" line-height:23px;"><iframe name="365myt" marginwidth="0" marginheight="0" src="http://tianqi.365myt.com/weather/59488.htm?s=1" frameborder="0" scrolling="no" height="16" allowtransparency="allowtransparency"></iframe></span></td>
        <td width="186" align="left" valign="bottom"><a href="#"><img src="/images/2010527wag-1.jpg" width="28" height="21" border="0" /></a><img src="/images/2010527wag-2.jpg" width="16" height="21" /><a href="#"><img src="/images/2010527wag-3.jpg" width="58" height="21" border="0" /></a><img src="/images/2010527wag-4.jpg" width="19" height="21" /><a href="#"><img src="/images/2010527wag-5.jpg" width="42" height="21" border="0" /></a></td>
      </tr>
    </table>
      <table width="767" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" valign="top"><img src="/images/2010527wag-gsjj7.jpg" width="767" height="218" /></td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
        </tr>
      </table>
      <table width="767" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/space.gif" width="13" height="1" /></td>
          <td height="18" align="left" valign="bottom"><a href="/"><span class="titlered">我要咨询</span></a> <a href="/"><span class="entitlered">Consult</span></a></td>
        </tr>
        <tr>
          <td width="13"><img src="/images/space.gif" width="13" height="1" /></td>
          <td><img src="/images/2010527wag-tbg.jpg" width="740" height="8" /></td>
        </tr>
      </table>
      <table width="767" border="0" cellspacing="0" cellpadding="0" background="/images/2010527wag-tbg2.jpg" style="background-repeat:no-repeat;">
  <tr>
    <td width="13"><img src="/images/space.gif" width="13" height="1" /></td>
    <td width="754" height="368" align="left" valign="top"><table width="734" border="0" cellspacing="0" cellpadding="0">
	      <tr>
        <td><img src="/images/space.gif" height="12" /></td>
      </tr>
      <tr>
        <td>
		<div style="overflow:auto; height:336px;scrollbar-face-color: #98642B;
scrollbar-highlight-color: #98642B;
scrollbar-shadow-color: #98642B;
scrollbar-arrow-color: #DAAE7F;
scrollbar-base-color: #98642B;
scrollbar-dark-shadow-color: #98642B;}">

<%
     IWordManager wordMgr = LeaveWordPeer.getInstance();
     List list = new ArrayList();
     list = wordMgr.getMassage();
    Word word = new Word();
    for(int i = 0 ; i < list.size(); i++){
        word = (Word)list.get(i);
        //判断是否为ip地址
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher  =  pattern.matcher(word.getUserid());
        %>
 <table width="710" border="0" cellspacing="0" cellpadding="0">
     <tr>
            <td height="25" align="left" valign="middle"><strong><%=matcher.matches() ? "游客" : word.getUserid()%></strong>  <%=word.getWritedate().toString().substring(0,10)%></td>
            </tr>

        <tr>
            <td align="left" valign="top">内容：<%=word.getContent()%></td>
            </tr>
     <% if (word.getRetcontent() == null || word.getRetcontent().trim().equals("")){
     }else{%>
               <Tr>
			   <Td style="padding-left:15px;"><span class="smallred">回复：</span> <%=word.getRetcontent() %></Td>
			</Tr>
     <%}%>
			<tr>
			    <Td height="20"></Td>
			</tr>
			<tr>
			    <Td background="/images/2010527wzg-bgxu.gif"><img src="/images/space.gif" height="2" /></Td>
			</tr>
			<tr>
			    <Td height="20"></Td>
			</tr>
</table>
<%}%>
            </div>
    </td>
  </tr>
</table>
      <table width="767" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="91" align="left" valign="top" background="/images/2010527wag-lowbg.jpg" style="background-repeat:no-repeat;"><br />
            &nbsp;&nbsp;版权所有：王振国肿瘤<br />
            &nbsp;&nbsp;医院电话：0756-2258246、0756-2220666   地址：珠海市香洲区海虹路27号  邮编：519000 </td>
        </tr>
      </table></td>
  </tr>
</table>

</td></tr></table>
</body>
</html>
