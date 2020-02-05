<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="org.apache.axis.client.Service" %>
<%@ page import="org.apache.axis.client.Call" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.rmi.RemoteException" %>
<%@ page import="javax.xml.rpc.ServiceException" %>
<%@ page import="java.net.MalformedURLException" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%
    boolean result = false;
    String email= ParamUtil.getParameter(request, "email");
    try {
        //String url="http://localhost:8080/webbuilder/services/NjlUserWebService";
        String url="http://192.168.1.53/webbuilder/services/NjlUserWebService";
        Service serv = new Service();
        Call call = (Call)serv.createCall();
        call.setTargetEndpointAddress(new URL(url));
        call.setOperationName(new QName("NjlUserWebService","checkEmail"));
        result = (Boolean)call.invoke(new Object[]{email});
        System.out.println("result=" + result);
    } catch(RemoteException e) {
        e.printStackTrace();
    } catch(ServiceException exp1) {
        exp1.printStackTrace();
    } catch (MalformedURLException exp2) {
        exp2.printStackTrace();
    }

    if (result == true)
        out.write("true");
    else
        out.write("false");
    out.flush();
%>