<%@ page import="java.sql.*,
                 com.bizwink.cms.toolkit.companyinfo.*,
                 java.util.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=GBK"%>
<%
    int siteid = ParamUtil.getIntParameter(request, "siteid",0);
    boolean loginflag = ParamUtil.getBooleanParameter(request, "login");
    ICompanyinfoManager compMgr = CompanyinfoPeer.getInstance();
    StringBuffer buf = new StringBuffer();
    if (siteid > 0 && loginflag) {
        List compclass = compMgr.getCompanyClassList(siteid);

        for(int i=0;i<compclass.size(); i++) {
            companyClass companyclass = new companyClass();
            companyclass = (companyClass)compclass.get(i);
            buf.append(companyclass.getCname()).append(",");
        }
    }

    if (buf.length() > 0) {
        out.println(buf.substring(0,buf.length()-1).toString());
        out.flush();
    } else {
        out.println("error");
        out.flush();
    }
%>