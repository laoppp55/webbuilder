<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.address.IAddressManager" %>
<%@ page import="com.bizwink.webapps.address.AddressPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.address.Province" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String  code = ParamUtil.getParameter(request,"code");
    System.out.println("code=" + code);
    IAddressManager aMgr = AddressPeer.getInstance();
    List list = aMgr.getProvinceListByCountry(code);
    String outstr = "";
    for(int i = 0; i < list.size()-1; i++){
        Province p = (Province)list.get(i);
        outstr = outstr + StringUtil.gb2iso4View(p.getProvincename()) + "-" + p.getId() + "####";
    }
    out.print(outstr);
%>