<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.Meettings" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int id = ParamUtil.getIntParameter(request,"id",0);

    ICompanyinfoManager meetingMgr = CompanyinfoPeer.getInstance();
    if(id == 0){
        List list = meetingMgr.getMeetingTime();
        String strout = "<option value=\"\" selected=\"selected\">«Î—°‘Ò</option>";
        for (int i = 0; i < list.size(); i++) {
            Meettings meettings = (Meettings)list.get(i);
            strout += "<option value=\""+ meettings.getID() +"\">"+meettings.getMeetingdatetime().toString().substring(0,10)+"</option>";
        }
        out.write(strout);
    }else{
        String str = meetingMgr.getMeetingaddress(id);
        out.write(str);
    }

%>
