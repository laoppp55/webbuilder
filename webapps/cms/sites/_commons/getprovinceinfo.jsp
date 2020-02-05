<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.address.IAddressManager" %>
<%@ page import="com.bizwink.webapps.address.AddressPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.address.Province" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String provname = "";
    int id = ParamUtil.getIntParameter(request,"addressid",0);
    IOrderManager oMgr = orderPeer.getInstance();
    AddressInfo addressinfo = new AddressInfo();
    addressinfo = oMgr.getAAddresInfo(id);
    if(addressinfo != null){
         provname = addressinfo.getProvinces()==null?"": StringUtil.gb2iso4View(addressinfo.getProvinces());
    }
    IAddressManager aMgr = AddressPeer.getInstance();
    List list = aMgr.getProvinceList();
    String outstr = "<select id=\"province\" name=\"province\" onChange=\"selectprovince(this.value);\">";
    for(int i = 0; i < list.size(); i++){
        Province p = (Province)list.get(i);
        String pname = p.getProvincename()==null?"":StringUtil.gb2iso4View(p.getProvincename());
        if(pname.equals(provname)){
            outstr += "<option value=\""+pname+"\" selected>"+pname+"</option>";
        }else{
           outstr += "<option value=\""+pname+"\">"+pname+"</option>";
        }
    }
    outstr += "</select>";
    out.print(outstr);
%>