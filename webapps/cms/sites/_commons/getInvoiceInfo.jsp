<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.cms.business.Order.Invoice" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    Uregister ug = (Uregister) session.getAttribute("UserLogin");
    int userid = 0;
    if (ug != null) {
        userid = ug.getId();
    }
    IOrderManager oMgr = orderPeer.getInstance();
    //获得session中保存的发票信息
    Invoice invoice = (Invoice) session.getAttribute("invoiceinfo");
    if (invoice == null) {
        //获得用户最近购买的订单中的发票信息
        Invoice uinvoice = oMgr.getInvoiceInfoForUser(userid);
        if (uinvoice != null) {
            invoice = new Invoice();
            invoice.setContent(uinvoice.getContent());
            invoice.setInvoicetype(uinvoice.getInvoicetype());
            invoice.setTitle(uinvoice.getTitle());
            invoice.setBankaccount(uinvoice.getBankaccount()==null?"":StringUtil.gb2iso4View(uinvoice.getBankaccount()));
            invoice.setBankname(uinvoice.getBankname()==null?"":StringUtil.gb2iso4View(uinvoice.getBankaccount()));
            invoice.setContentinfo(uinvoice.getContentinfo()==null?"":StringUtil.gb2iso4View(uinvoice.getContentinfo()));
            invoice.setIdentification(uinvoice.getIdentification()==null?"":StringUtil.gb2iso4View(uinvoice.getIdentification()));
            invoice.setPhone(uinvoice.getPhone()==null?"":StringUtil.gb2iso4View(uinvoice.getPhone()));
            invoice.setRegisteraddress(uinvoice.getRegisteraddress()==null?"":StringUtil.gb2iso4View(uinvoice.getRegisteraddress()));
            invoice.setCompanyname(uinvoice.getCompanyname() ==null?"":StringUtil.gb2iso4View(uinvoice.getCompanyname()));
            session.setAttribute("invoiceinfo", invoice);
        } else {
            Invoice ninvoice = new Invoice();
            ninvoice.setContent(0);//默认为明细
            ninvoice.setInvoicetype(0);//默认为普通发票
            ninvoice.setTitle(0);//默认为个人
            invoice = ninvoice;
            session.setAttribute("invoiceinfo", ninvoice);
        }
    }

    //获得标记信息
    IMarkManager markMgr = markPeer.getInstance();
    String listStyle = "";
    String submit = "";//确认按钮
    String submitimage = "";//确认按钮图片
    int siteid = 0;
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        listStyle = properties.getProperty(properties.getName().concat(".INVOICEDISPLAYINFO"));//样式

        String siteids = properties.getProperty(properties.getName().concat(".SITEID"));
        if (siteids != null && !siteids.equalsIgnoreCase("null") && !siteids.equals("")) {
            siteid = Integer.parseInt(siteids);
        }
        submit = properties.getProperty(properties.getName().concat(".EDITINVOICE"));
        submitimage = properties.getProperty(properties.getName().concat(".EDITINVOICEIMAGE"));
    }
    if (listStyle != null) {
        listStyle = listStyle.substring(0, listStyle.length() - 1);
    }
    //发票类型
    String inoicetype = "";
    if(invoice.getInvoicetype() == 0){
        inoicetype = "普通发票";
    }
    else{
        inoicetype = "增值税发票";
    }

    listStyle  = StringUtil.replace(listStyle, "<" + "%%invoicetype%%" + ">", inoicetype);
    //发票抬头
    String title = "";
    if(invoice.getTitle() == 0)
    {
        title = "个人";
    }
    else{
        if(invoice.getCompanyname() != null){
            title = StringUtil.gb2iso4View(invoice.getCompanyname());
        }
        else{
            title = "单位";
        }
    }

    listStyle  = StringUtil.replace(listStyle, "<" + "%%title%%" + ">", title);
    //发票内容
    String content = "";
    Invoice invoicecontent = oMgr.getInvoiceConenteById(invoice.getContent());
    if(invoicecontent == null){
        content = "明细";
    }
    else{
        content = StringUtil.gb2iso4View(invoicecontent.getContentinfo());
    }
    listStyle  = StringUtil.replace(listStyle, "<" + "%%content%%" + ">", content);
    String submits = "";
        if (submit.equals("submit")) {
            submits = "<input type='button' name='button1' value='修改' onclick='javascript:editinvoice(" + markID + ");'>&nbsp;\n";
        } else if (submit.equals("images")) {
            submits = "<a href=\"#\" onclick='editinvoice("+ markID + ");'><img src=\"/_sys_images/buttons/" + submitimage + "\" border=0></a>";
        } else {
            submits = "<a href=\"#\" onclick='editinvoice("+ markID + ");'>修改</a>";
        }
    listStyle  = StringUtil.replace(listStyle, "<" + "%%editsubmit%%" + ">", submits);
    out.write(listStyle);
%>