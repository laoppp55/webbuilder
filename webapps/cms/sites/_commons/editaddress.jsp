<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int id = ParamUtil.getIntParameter(request,"id",0);
    IOrderManager oMgr = orderPeer.getInstance();
    AddressInfo addressinfo = new AddressInfo();
    addressinfo = oMgr.getAAddresInfo(id);
    String name = addressinfo.getName()==null?"": StringUtil.gb2iso4View(addressinfo.getName());
    String prov = addressinfo.getProvinces()==null?"": StringUtil.gb2iso4View(addressinfo.getProvinces());
    String city = addressinfo.getCity()==null?"": StringUtil.gb2iso4View(addressinfo.getCity());
    String zone = addressinfo.getCity()==null?"": StringUtil.gb2iso4View(addressinfo.getZone());
    String address  = addressinfo.getAddress()==null?"": StringUtil.gb2iso4View(addressinfo.getAddress());
    String zip = addressinfo.getZip()==null?"": StringUtil.gb2iso4View(addressinfo.getZip());
    String mobile = addressinfo.getMobile()==null?"": StringUtil.gb2iso4View(addressinfo.getMobile());
    String phone = addressinfo.getPhone()==null?"": StringUtil.gb2iso4View(addressinfo.getPhone());
    String outstr = "<table width='100%' border='0' cellpadding='0' cellspacing='0' style=\"font-size:12px\">"
            +"<tr>"
            +"<td height='44' colspan='6' valign='middle' class='style1'>收货人信息</td>"
            +"</tr>"
            +"<tr>"
            +"<td width='20%' height='38' align='right' valign='top'>收货人：</td>"
            +"<td colspan='5' valign='top' align='left'><input name='connname' type='text' size='14' value='"+name+"'/></td>"
            +"</tr>"
            +"<tr>"
            +"<td width='15%' height='38' align='right' valign='top'>省份：</td>"
            +"<td width='15%' valign='top' align='left'><div id=\"provinceinfo\"></div>"
            +"</td>"
            +"<td  width='15%' valign='top' align='right'>区域：</td>"
            +"<td  width='15%' valign='top' align='left'><div id=\"cityinfo\"></div></</td>"
            +"<td  width='15%' valign='top' align='right'>地区：</td>"
            +"<td  width='15%' valign='top' align='left'><div id=\"zoneinfo\"></div></td>"
            +"</tr>"
            +"<tr>"
            +"<td width='20%' height='38' align='right' valign='top'>详细地址：</td>"
            +"<td colspan='5' valign='top' align='left'><input name='address' type='text' size='40' value='"+address+"'/></td>"
            +"</tr>"
            +"<tr>"
            +"<td width='20%' height='38' align='right' valign='top'>邮政编码：</td>"
            +"<td colspan='5' aling='left' valign='top'><input name='zip' type='text' size='14' value='"+zip+"'/>请正确填写邮编，以确保您的定单顺利送达</td>"
            +"</tr>"
            +"<tr>"
            +"<td height='38' align='right' valign='top'>移动电话：</td>"
            +"<td valign='top'><input name='mobile' type='text' size='14' value='"+mobile+"'/></td>"
            +"<td align='right' valign='top'>固定电话：</td>"
            +"<td valign='top'><input name='phone' type='text' size='14' value='"+phone+"'/></td>"
            +"<td colspan='2' valign='top'><!--DWLayoutEmptyCell-->&nbsp;</td>"
            +"</tr>"
            +"<tr>"
            +"<td height='52' colspan='6' align='center' valign='top'><input type=button name=button value=\"提交\" onClick='javascript:updateaddressinfo("+id+");'></td>"
            +"</tr>"
            +"</table>";
    out.print(outstr);
%>