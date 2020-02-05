<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Producttype" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    
    int parentID = ParamUtil.getIntParameter(request, "parentID", 0);
    int columnID = ParamUtil.getIntParameter(request, "columnID", 0);



    IColumnManager colMgr = ColumnPeer.getInstance();
    int id = colMgr.getTypeID(parentID,columnID);

    if(id != -1){
    Producttype pro = new Producttype();
    pro.setCname("");
    pro.setColumnID(columnID);
    pro.setParentid(0);
    pro.setReferid(id);
    colMgr.createType(pro);
    }

    out.print("");
%>