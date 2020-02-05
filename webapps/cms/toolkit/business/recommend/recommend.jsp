<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
  long bookid = 0;
  String backsrc = (String)session.getAttribute("backsrc");
  String[] messages = request.getParameterValues("book");
  int recommendflag = ParamUtil.getIntParameter(request, "recommendflag", 0);
  ISearchManager searchMgr = SearchPeer.getInstance();
if(recommendflag>=0){
    for(int i=0;i<messages.length;i++){
    bookid = Integer.parseInt(messages[i]);
    searchMgr.updateRecommend(recommendflag,bookid);
  }
}

 response.sendRedirect(backsrc);
%>

