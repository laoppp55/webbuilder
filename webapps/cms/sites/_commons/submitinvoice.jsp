<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.Invoice" %>
<%@page contentType="text/html;charset=GBK"  %>
<%
    int invoicetype = ParamUtil.getIntParameter(request,"invoicetype",0);
    int title = ParamUtil.getIntParameter(request,"title",0);
    int content = ParamUtil.getIntParameter(request,"content",0);
    String companyname = ParamUtil.getParameter(request,"companyname");
    String identification = ParamUtil.getParameter(request,"identification");
    String registeraddress = ParamUtil.getParameter(request,"registeraddress");
    String phone = ParamUtil.getParameter(request,"phone");
    String bankname = ParamUtil.getParameter(request,"bankname");
    String bankaccount = ParamUtil.getParameter(request,"bankaccount");
    Invoice invoice = new Invoice();
    invoice.setInvoicetype(invoicetype);
    invoice.setTitle(title);
    invoice.setContent(content);
    invoice.setCompanyname(companyname);
    invoice.setIdentification(identification);
    invoice.setRegisteraddress(registeraddress);
    invoice.setPhone(phone);
    invoice.setBankaccount(bankaccount);
    invoice.setBankname(bankname);
    session.setAttribute("invoiceinfo",invoice);
%>