<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="com.bizwink.po.CmsTemplate" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.axis.client.Service" %>
<%@ page import="org.apache.axis.client.Call" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="org.apache.axis.encoding.ser.BeanSerializerFactory" %>
<%@ page import="org.apache.axis.encoding.ser.BeanDeserializerFactory" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.apache.axis.encoding.XMLType" %>
<%@ page import="javax.xml.rpc.ParameterMode" %>
<%@ page import="java.rmi.RemoteException" %>
<%@ page import="javax.xml.rpc.ServiceException" %>
<%@ page import="java.net.MalformedURLException" %>
<%
    int siteid = 2991;
    List<CmsTemplate> modellist = null;
    try {
        //String url="http://localhost:8080/webbuilder/services/NjlUserWebService";
        String url="http://192.168.1.53/webbuilder/services/NjlUserWebService";
        Service serv = new Service();
        Call call = (Call)serv.createCall();

        QName qn = new QName("urn:NjlUserWebService","CmsTemplate");
        call.registerTypeMapping(CmsTemplate.class,qn,new BeanSerializerFactory(CmsTemplate.class,qn),new BeanDeserializerFactory(CmsTemplate.class,qn));
        call.setTargetEndpointAddress(new URL(url));
        call.setOperationName(new QName("NjlUserWebService","getTemplates"));
        call.setReturnClass(ArrayList.class);
        call.addParameter("siteid", XMLType.XSD_ANYTYPE, ParameterMode.IN);
        modellist = (ArrayList)call.invoke(new Object[] {siteid});
    } catch(RemoteException e) {
        e.printStackTrace();
    } catch(ServiceException exp1) {
        exp1.printStackTrace();
    } catch (MalformedURLException exp2) {
        exp2.printStackTrace();
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>周边旅游行网</title>
    <link href="css/case_zs.css" rel="stylesheet" type="text/css" />
    <script src="js/jquery-1.4.4.min.js" type="text/javascript"></script>
    <script language="javascript">
        function openwin(url)
        {
            window.open(url);
        }

        function select_the_template(form) {
            var value=0;
            var text1 = "";
            for(var i=0; i<form.tempno.length; i++) {
                if (form.tempno[i].checked) {
                    value = form.tempno[i].value;
                    text1=$("input[name='tempno']:checked")[0].nextSibling.nodeValue
                    break;
                }
            }

            if (value==0) {
                alert("请选择一套网站模板");
                return false;
            } else {
                alert(value);
                opener.document.regform.tempno.value = value;
                opener.document.regform.tempname.value = text1;
                window.close();
                return;
            }
        }
    </script>
</head>
<body>
<div class="box">
    <div class="top">
        <div class="logo"><img src="images/logo.png" /></div>
        <div class="menu"><a href="#">首页</a><a href="/index.shtml">农家乐</a><a href="/list_czy.shtml">观光采摘园</a><a href="/list_jq.shtml">景区</a></div>
        <div class="login"><a href="/_members/zhuce.jsp">注册</a> | <a href="/_members/login.jsp">登录</a></div>
        <div class="clear"></div>
    </div>
    <!-- top end  -->
    <div style="height:36px;width:954px;">&nbsp;</div>
</div>
<!-- top end  -->
<div class="box_mb">
    <div class="mb_1">case</div>
    <div class="mb_2">模板展示</div>
    <div class="mb_pic">
        <form name="select_model" onsubmit="javascript:select_the_template(select_model)">
            <%for(int ii=0;ii<modellist.size();ii++){
                CmsTemplate ct = (CmsTemplate)modellist.get(ii);
            %>
            <div class="mb_pic_1">
                <p><a href="javascript:openwin('http://njy01.coosite.com/index<%=ct.getTEMPNUM()%>.shtml')"><img src="images/20151229_0<%=(ii+1)%>.jpg" /></a></p>
                <p class="mb_tit"><input name="tempno" type="radio" value="<%=ct.getTEMPNUM()%>" /><%=ct.getCHNAME()%></p>
            </div>
            <%}%>
            <div class="clear"><input type="submit" value="确认" name="ok"></div>
        </form>
    </div>
</div>
<!-- fonnter  -->
<%@ include file="inc/tail.shtml" %>
</body>
</html>
