<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefineException" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page contentType="text/html;charset=GBK" %>

<%
    int sid = ParamUtil.getIntParameter(request, "sid", -1);

    if (sid != -1) {
        ICompanyinfoManager meetingMgr = CompanyinfoPeer.getInstance();
        try {
            meetingMgr.deleteMeetings(sid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("index.jsp?success=2");
        return;
    } else {
        response.sendRedirect("index.jsp?success=-1");
        return;
    }
%>