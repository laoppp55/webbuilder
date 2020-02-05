<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.appointment.IAppointmentManager" %>
<%@ page import="com.bizwink.webapps.appointment.AppointmentPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Uregister ug = (Uregister) session.getAttribute("UserLogin");
    if (ug == null) {
        out.print("false");

    } else {
        String userid = ug.getMemberid();
        // String userid = "feixiang1";
        String code = ParamUtil.getParameter(request, "code");
        String date = ParamUtil.getParameter(request, "date");
        int siteid = ParamUtil.getIntParameter(request, "siteid", 0);
        IAppointmentManager aMgr = AppointmentPeer.getInstance();
        String outstr = "false";
        List list = aMgr.getAppointmentCodeByAppointmentTime(date, code, siteid);
        for (int i = 0; i < list.size(); i++) {
            String acode = (String) list.get(i);
            if (!aMgr.checkAppointmentCodeIsGet(date, acode, siteid)) {
                outstr = acode;
                break;
            }
        }
        if (outstr != null && !outstr.equals("") && !outstr.equals("null")) {
            //检查用户是否已经预约该业务
            boolean flag = aMgr.checkUserAppointment(date, userid, siteid);
            if (flag) {
                out.print("false");
            } else {

                out.print(outstr);
            }
        } else {
            out.print("false");
        }
    }
%>