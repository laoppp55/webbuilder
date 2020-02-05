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

  ISearchManager searchMgr = SearchPeer.getInstance();

  long bookid = 0;
  for(int i=0;i<messages.length;i++){
    bookid = Long.parseLong(messages[i]);
    if(flag == 1){
      searchMgr.updateTehui(bookid, 1);
      //searchMgr.updateShowFlag(bookid,1);
    }else if(flag == 0){
      searchMgr.updateTehui(bookid, 0);
      //searchMgr.updateShowFlag(bookid,0);
    }
  }

  response.sendRedirect("tehui.jsp");
%>