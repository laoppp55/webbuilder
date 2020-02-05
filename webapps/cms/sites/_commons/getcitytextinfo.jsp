<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.address.IAddressManager" %>
<%@ page import="com.bizwink.webapps.address.AddressPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.address.City" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int provid = ParamUtil.getIntParameter(request,"provid",0);
    IAddressManager aMgr = AddressPeer.getInstance();
    List list = aMgr.getCityListByProvid(provid) ;
    String outstr = "";
    for(int i = 0; i < list.size(); i++){
        City c = (City)list.get(i);
        String cname = c.getCityname()==null?"":StringUtil.gb2iso4View(c.getCityname());
        outstr = outstr + cname + "-" + c.getId() + "####";
    }
    out.print(outstr);
%>