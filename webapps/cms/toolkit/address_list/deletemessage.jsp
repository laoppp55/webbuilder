<%@page import="com.bizwink.cms.toolkit.addresslist.*,
                java.util.*,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=GBK"
%>
<%
  int id = ParamUtil.getIntParameter(request, "id", 0);
  int flag = ParamUtil.getIntParameter(request, "flag", 0);
  IAddressListManager addresslistMgr = AddressListPeer.getInstance();
  addresslistMgr.deleteMessage(id);
  if(flag == 1){
    response.sendRedirect("messagelist.jsp");
  }else{
    response.sendRedirect("sendmsg.jsp");
  }
%>