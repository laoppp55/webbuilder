<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.address.IAddressManager" %>
<%@ page import="com.bizwink.webapps.address.AddressPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.address.Province" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@ page import="com.bizwink.webapps.address.City" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String cityname = "";
    String pname = ParamUtil.getParameter(request,"pname");
    int id = ParamUtil.getIntParameter(request,"addressid",0);
    IOrderManager oMgr = orderPeer.getInstance();
    AddressInfo addressinfo = new AddressInfo();
    addressinfo = oMgr.getAAddresInfo(id);
    if(addressinfo != null){
         cityname = addressinfo.getCity()==null?"": StringUtil.gb2iso4View(addressinfo.getCity());
         pname = addressinfo.getProvinces()==null?"安徽省": StringUtil.gb2iso4View(addressinfo.getProvinces());
    }
    if(pname == null)
    {
        pname = "安徽省";
    }
    IAddressManager aMgr = AddressPeer.getInstance();
    List list = aMgr.getCityList(pname) ;
    String outstr = "<select id=\"city\" name=\"city\" onChange=\"selectcity(this.value);\">";
    for(int i = 0; i < list.size(); i++){
        City c = (City)list.get(i);
        String cname = c.getCityname()==null?"":StringUtil.gb2iso4View(c.getCityname());
        if(cname.equals(cityname)){
            outstr += "<option value=\""+cname+"\" selected>"+cname+"</option>";
        }else{
           outstr += "<option value=\""+cname+"\">"+cname+"</option>";
        }
    }
    outstr += "</select>";
    out.print(outstr);
%>