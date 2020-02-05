<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
  String[] messages = request.getParameterValues("tehui");
  int flag = ParamUtil.getIntParameter(request, "flag", -1);
  int zhuanti = ParamUtil.getIntParameter(request, "zhuanti", 0);

  ISearchManager searchMgr = SearchPeer.getInstance();

  long bookid = 0;
  for(int i=0;i<messages.length;i++){
    bookid = Long.parseLong(messages[i]);
    if(flag == 1){
      searchMgr.updateZhuanti(bookid, zhuanti);
    }else if(flag == 0){
      searchMgr.updateZhuanti(bookid, 0);
    }
  }

  response.sendRedirect("zhuanti.jsp");
%>