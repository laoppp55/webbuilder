<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    ICompanyinfoManager comMgr= CompanyinfoPeer.getInstance();
    int id= ParamUtil.getIntParameter(request,"id",-1);
    int currPage=ParamUtil.getIntParameter(request,"currPage",-1);
    int startIndex=ParamUtil.getIntParameter(request,"startIndex",-1);
    comMgr.delCompany(id);
    response.sendRedirect("index.jsp?siteid="+siteid+"&currPage="+currPage+"&startIndex="+startIndex);
%>