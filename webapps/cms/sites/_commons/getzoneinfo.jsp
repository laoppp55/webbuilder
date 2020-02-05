<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.AddressInfo" %>
<%@ page import="com.bizwink.webapps.address.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String zonename = "";
    int id = ParamUtil.getIntParameter(request,"addressid",0);
    String cname = ParamUtil.getParameter(request,"cityname");
    IOrderManager oMgr = orderPeer.getInstance();
    AddressInfo addressinfo = new AddressInfo();
    addressinfo = oMgr.getAAddresInfo(id);
    if(addressinfo != null){
         cname = addressinfo.getCity()==null?"合肥市": StringUtil.gb2iso4View(addressinfo.getCity());
         zonename = addressinfo.getZone()==null?"": StringUtil.gb2iso4View(addressinfo.getZone());
    }
    if(cname == null){
        cname = "合肥市";
    }
    IAddressManager aMgr = AddressPeer.getInstance();
    List list = aMgr.getZoneList(cname) ;
    String outstr = "<select id=\"zone\" name=\"zone\">";
    for(int i = 0; i < list.size(); i++){
        Zone z = (Zone)list.get(i);
        String zname = z.getZonename() ==null?"":StringUtil.gb2iso4View(z.getZonename());
        if(zname.equals(zonename)){
            outstr += "<option value=\""+zname+"\" selected>"+zname+"</option>";
        }else{
           outstr += "<option value=\""+zname+"\">"+zname+"</option>";
        }
    }
    outstr += "</select>";
    out.print(outstr);
%>