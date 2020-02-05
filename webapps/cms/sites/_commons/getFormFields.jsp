<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int markid = ParamUtil.getIntParameter(request, "markid", 0);
    //获得标记信息
    IMarkManager markMgr = markPeer.getInstance();
    if (markid > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markid));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        String content = properties.getProperty(properties.getName().concat(".CONTENT")); //显示的字段信息
        content = content.substring(0,content.length() -1);
        System.out.println(content);
        XMLProperties contentProperties = new XMLProperties(content);
        String b[] = contentProperties.getChildrenProperties("fields");
        String buf = "";
        int i=0;
        for(i=0; i<b.length; i++){
            System.out.println(b[i]);
            String chinesename = contentProperties.getProperty("fields." + b[i] + ".chinesename");
            buf = buf + chinesename + "~~!";
        }
        out.print(buf);
    } else {
        out.print("没有留言信息！");
    }
%>