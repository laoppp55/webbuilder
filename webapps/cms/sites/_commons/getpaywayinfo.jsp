<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    int siteid = ParamUtil.getIntParameter(request, "siteid", 0);     //get siteid
    int markID = ParamUtil.getIntParameter(request, "markid", 0);
    String payways = (String)session.getAttribute("payway");
    int selectpayway = 0;
    if(payways!=null&&!payways.equals("null")&&!payways.equals("")){
        selectpayway = Integer.parseInt(payways);
    }
//获得标记信息
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
        listStyle = properties.getProperty(properties.getName().concat(".PAYWAY"));//样式
        submit = properties.getProperty(properties.getName().concat(".PAYWAYSUBMIT"));
        submitimage = properties.getProperty(properties.getName().concat(".PAYWAYIMAGE"));
    }
    if (listStyle != null) {
        listStyle = listStyle.substring(0, listStyle.length() - 1);
    }

    IOrderManager oMgr = orderPeer.getInstance();
    List list = oMgr.getAllSendWayInfo(siteid);
   if(list.size()>0){
        for(int i = 0;i < list.size();i++){
            String checkstr = " >";
            SendWay payway = (SendWay)list.get(i);
            String name = payway.getCname()==null?"": StringUtil.gb2iso4View(payway.getCname());
            String notes = payway.getNotes()==null?"":"("+StringUtil.gb2iso4View(payway.getNotes())+")";

            if(i==0 && selectpayway==0){
                checkstr = " checked>";
                session.setAttribute("payway",String.valueOf(payway.getId()));
            }
            if(selectpayway>0){
                if(selectpayway == payway.getId()){
                    checkstr = " checked>";
                }
            }
            String ids = String.valueOf(payway.getId());
            String values = "<input type=radio name=payway value=" + payway.getId()+checkstr+name+":"+notes+ "<br>";
            listStyle = StringUtil.replace(listStyle, "<" + "%%" + ids + "%%" + ">", values);
            //outstr = outstr + "<tr><td align=left><input type=radio name=payway value="+payway.getId()+checkstr+name+":"+notes+"</td></tr>";
        }
       String submits = "";
        if (submit.equals("submit")) {
            submits = "                    <input type='button' name='button1' value='修改' onclick='javascript:submitPayway(" + markID + "," + siteid + ");'>&nbsp;\n";
        } else if (submit.equals("images")) {
            submits = "<a href=\"#\" onclick='submitPayway("+ markID + "," + siteid + ");'><img src=\"/_sys_images/buttons/" + submitimage + "\" border=0></a>";
        } else {
            submits = "<a href=\"#\" onclick='submitPayway("+ markID + "," + siteid + ");'>修改</a>";
        }
        listStyle  = StringUtil.replace(listStyle, "<" + "%%submitbutton%%" + ">", submits);
        //outstr = outstr + "<tr><td align='center' valign='top'><input type=button name=button value=提交 onClick='javascript:selectPayway();'></td></tr></table>";
        out.write(listStyle);
    }else{
        String outstr = "<table width='80%' border='0' cellpadding='0' cellspacing='0'>\n" +
            "<tr><td align=left>支付方式</td></tr><tr><td align=left><input type=radio name=payway value=0 checked>货到付款(免费)</td></tr>"+
        "<tr><td align='center' valign='top'><input type=button name=button value=提交 onClick='javascript:selectPayway();'></td></tr></table>";
        out.write(outstr);
    }
%>