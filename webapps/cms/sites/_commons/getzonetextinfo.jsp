<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.address.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int cityid = ParamUtil.getIntParameter(request,"cityid",0);
    IAddressManager aMgr = AddressPeer.getInstance();
    List list = aMgr.getZoneListByCityid(cityid) ;
    String outstr = "";
    for(int i = 0; i < list.size(); i++){
        Zone z = (Zone)list.get(i);
        String zname = z.getZonename() ==null?"":StringUtil.gb2iso4View(z.getZonename());
        outstr = outstr + zname + "-" + z.getId() + "####";
    }
    out.print(outstr);
%>