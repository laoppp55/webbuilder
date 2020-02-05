<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    Uregister ug = (Uregister) session.getAttribute("UserLogin");
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    //获得标记信息
    IMarkManager markMgr = markPeer.getInstance();
    String listStyle = "";
    int siteid = 0;
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        listStyle = properties.getProperty(properties.getName().concat(".LOGININFO"));
        String siteids = properties.getProperty(properties.getName().concat(".SITEID"));
        if (siteids != null && !siteids.equalsIgnoreCase("null") && !siteids.equals("")) {
            siteid = Integer.parseInt(siteids);
        }
        listStyle = listStyle.substring(0, listStyle.length() - 1);
        String submits = properties.getProperty(properties.getName().concat(".SUBMITS"));
        String submitsimages = properties.getProperty(properties.getName().concat(".SUBMITISMAGE"));

        if (ug != null) {
            if (ug.getMemberid() != null) {
                listStyle = StringUtil.replace(listStyle, "<" + "%%username%%" + ">", ug.getMemberid());
                //处理提交按钮
                String submit = "";
                if (submits.equals("submit")) {
                    //提交
                    submit = "<input type=\"button\" name=\"submit\" value=\"退出\" onclick=\"javascript:window.loaction='/_commons/logout.jsp';\">";
                } else if (submits.equals("images")) {
                    //图片
                    submit = "<a href=\"/_commons/logout.jsp\"><img src=\"/_sys_images/buttons/" + submitsimages + "\" border=\"0\"></a>";
                } else if (submits.equals("links")) {
                    submit = "<a href=\"/_commons/logout.jsp\">退出</a>";
                }
                listStyle = StringUtil.replace(listStyle, "<" + "%%submits%%" + ">", submit);
                out.write(listStyle);
            } else
                out.write("{nologin}");
        } else
            out.write("{ug-null}");
    } else {
        if (ug != null) {
            if (ug.getMemberid() != null)
                out.write("欢迎<b>" + ug.getMemberid() + "</b>登录本网站<br>退出请点击<a href=/_commons/logout.jsp><font color=red>退出</font></a>");
            else
                out.write("{nologin}");
        } else {
            out.write("{ug-null}");
        }
    }
    out.flush();
%>