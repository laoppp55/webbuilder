<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.ISearchWordManager" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.SearchWordPeer" %>
<%@page contentType="text/html;charset=utf-8" %>
<%

    int id = ParamUtil.getIntParameter(request,"id",-1);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int flag = ParamUtil.getIntParameter(request, "flag", 0);
    ISearchWordManager searchWordManager = SearchWordPeer.getInstance();
    searchWordManager.delSearchWord(id);
    response.sendRedirect("index.jsp?start="+start+"&flag="+flag);

%>
