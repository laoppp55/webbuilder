<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int scores = ParamUtil.getIntParameter(request, "scores", -1);
     Uregister ug = (Uregister) session.getAttribute("UserLogin");
    int userid = 0;//获得用户id
    if(ug!=null){
        userid = ug.getId();
    }
    IUregisterManager uregMgr = UregisterPeer.getInstance();
    int pscores = uregMgr.getUserScores(userid);

    //pscores = 10000;

    String outstr = "";
    if (startflag == 1) {
        if (scores > pscores) {
            outstr = "false";
        } else {
            session.setAttribute("scores",String.valueOf(scores));
            outstr = "true";
        }
    }
    out.println(outstr);
%>
