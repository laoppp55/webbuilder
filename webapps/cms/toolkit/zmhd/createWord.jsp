<%@page import="com.bizwink.cms.sjswsbs.IWsbsManager" contentType="text/html;charset=UTF-8"
        %>
<%@ page import="com.bizwink.cms.sjswsbs.WsbsPeer" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.HtmlToWord" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="com.bizwink.cms.sjswsbs.Letter" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
   // String path = application.getRealPath("/");
    String path="/wordfile/";
    String downloadurl = HtmlToWord.writeWordFile(id,path);

    if (downloadurl != null) {
        //新建一个SmartUpload对象
        SmartUpload su = new SmartUpload();
        //初始化
        su.initialize(pageContext);
        // 设定contentDisposition为null以禁止浏览器自动打开文件
        su.setContentDisposition(null);
        System.out.println(downloadurl);

        su.downloadFile(downloadurl);
    }
%>