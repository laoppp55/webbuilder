<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.gwcase.IGWcaseManager" %>
<%@ page import="com.bizwink.cms.toolkit.gwcase.GwcasePeer" %>
<%
    String[] sns = request.getParameterValues("sn");
    String allsns = ParamUtil.getParameter(request, "allsns");

    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 5);

    String Sns = "";
    if( (sns !=null) && (sns.length > 0)){
        for(int i = 0 ; i < sns.length; i++){
            Sns = Sns + "'"+sns[i] + "'"+",";
        }
    }

    if(Sns.indexOf(",") != -1)
        Sns = Sns.substring(0, (Sns.length()-1));
    
    if(allsns.indexOf(",") != -1)
        allsns = allsns.substring(0, (allsns.length()-1));


    IGWcaseManager GWMgr = GwcasePeer.getInstance();
    try{
        GWMgr.updateflag(allsns,Sns);
    } catch(Exception e){
        e.printStackTrace();
    }

     response.sendRedirect("index.jsp?startrow=" + startrow + "&range" + range);
%>