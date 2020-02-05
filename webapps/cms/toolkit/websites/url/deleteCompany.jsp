<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.company.*" %>
<%@page language="java" contentType="text/html;charset=GBK" %>
<%
    ICompanyManager comMgr= CompanyPeer.getInstance();
    int id= ParamUtil.getIntParameter(request,"id",-1);
    int companytype=ParamUtil.getIntParameter(request,"companytype",-1);
    int currPage=ParamUtil.getIntParameter(request,"currPage",-1);
    int startIndex=ParamUtil.getIntParameter(request,"startIndex",-1);
    comMgr.delCompany(id);
    response.sendRedirect("index.jsp?companytype="+companytype+"&currPage="+currPage+"&startIndex="+startIndex);
%>