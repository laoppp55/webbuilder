
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    int sendway = ParamUtil.getIntParameter(request,"sendway",-1);
    int sendtime= ParamUtil.getIntParameter(request,"sendtime",-1);
     int siteid = ParamUtil.getIntParameter(request, "siteid", 0);     //get siteid
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    session.setAttribute("sendway",String.valueOf(sendway));
    session.setAttribute("sendtime",String.valueOf(sendtime));
    IOrderManager oMgr = orderPeer.getInstance();
    Fee fee = new Fee();
    fee= oMgr.getAFeeInfo(sendway);
     IMarkManager markMgr = markPeer.getInstance();
    String listStyle = "";
        String submit = "";//确认按钮
        String submitimage = "";//确认按钮图片
        if (markID > 0) {
            String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
            str = StringUtil.replace(str, "[", "<");
            str = StringUtil.replace(str, "]", ">");
            str = StringUtil.replace(str, "{^", "[");
            str = StringUtil.replace(str, "^}", "]");

            XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
            listStyle = properties.getProperty(properties.getName().concat(".SENDWAYDISPLAY"));//样式
            submit = properties.getProperty(properties.getName().concat(".SENDWAYDISPLAYSUBMIT"));
            submitimage = properties.getProperty(properties.getName().concat(".SENDWAYDISPLAYIMAGE"));
        }
        if (listStyle != null) {
            listStyle = listStyle.substring(0, listStyle.length() - 1);
        }
    String trstr = fee.getCname()==null?"": StringUtil.gb2iso4View(fee.getCname());
    String submits = "";
        if (submit.equals("submit")) {
            submits = "                    <input type='button' name='button1' value='修改' onclick='javascript:getSendWay(" + markID + "," + siteid + ");'>&nbsp;\n";
        } else if (submit.equals("images")) {
            submits = "<a href=\"#\" onclick='getSendWay("+ markID + "," + siteid + ");'><img src=\"/_sys_images/buttons/" + submitimage + "\" border=0></a>";
        } else {
            submits = "<a href=\"#\" onclick='getSendWay("+ markID + "," + siteid + ");'>修改</a>";
        }
    String linktime = "";
    if(sendtime == 0){
        linktime = "工作日、双休日与假日均可送货";
    }
    else if(sendtime == 1){
        linktime = "只工作日送货(双休日、假日不用送)";
    }
    else{
         linktime = "只双休日、假日送货(工作日不用送)";
    }
    listStyle  = StringUtil.replace(listStyle, "<" + "%%sendwayname%%" + ">", trstr);
    listStyle  = StringUtil.replace(listStyle, "<" + "%%linktime%%" + ">", linktime);
    listStyle  = StringUtil.replace(listStyle, "<" + "%%editsubmit%%" + ">", submits);
    //String outstr = "送货方式:" +trstr+"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick='javascript:getSendWay("+markID+","+siteid+");'/>修改</a>";
    out.print(listStyle);
%>