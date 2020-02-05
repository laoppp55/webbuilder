<%@page import="com.bizwink.cms.toolkit.addresslist.*,
                java.util.*,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=GBK"
%>
<%
  int id = ParamUtil.getIntParameter(request, "id", 0);
  IAddressListManager addresslistMgr = AddressListPeer.getInstance();
  addresslistMgr.deleteAddressList(id);
  response.sendRedirect("list.jsp"); 
%>