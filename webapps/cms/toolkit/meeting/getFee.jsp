<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.CnUpperCaser" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String fee  = ParamUtil.getParameter(request, "fee");
    if(fee!=null && fee.length()>0) {
        String strout = new CnUpperCaser(fee).getCnString();
        strout = strout.substring(0, strout.indexOf("Áã"));
        out.write(strout);
    }else{
        out.write("Ò¼Çª");
    }
%>