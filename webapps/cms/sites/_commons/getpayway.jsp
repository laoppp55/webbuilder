<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.business.Order.SendWay" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    String payways = (String)session.getAttribute("payway");
    int selectpayway = 0;
    if(payways!=null&&!payways.equals("null")&&!payways.equals("")){
        selectpayway = Integer.parseInt(payways);
    }
    //获得站点id
    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
     String sitename = request.getServerName();  //site name
    int siteid = feedMgr.getSiteID(sitename);     //get siteid

    IOrderManager oMgr = orderPeer.getInstance();
    List list = oMgr.getAllSendWayInfo(siteid);

   if(list.size()>0){


        String outstr = "<table width='80%' border='0' cellpadding='0' cellspacing='0' style=\"font-size:12px\">\n" +
            "<tr><td align=left>支付方式</td></tr>";
        for(int i = 0;i < list.size();i++){
            String checkstr = " >";
            SendWay payway = (SendWay)list.get(i);
            String name = payway.getCname()==null?"": StringUtil.gb2iso4View(payway.getCname());
            String notes = payway.getNotes()==null?"":"("+StringUtil.gb2iso4View(payway.getNotes())+")";

            if(i==0 && selectpayway==0){
                checkstr = " checked>";
            }
            if(selectpayway>0){
                if(selectpayway == payway.getId()){
                    checkstr = " checked>";
                }
            }
            outstr = outstr + "<tr><td align=left><input type=radio name=payway value="+payway.getId()+checkstr+name+":"+notes+"</td></tr>";
        }
        outstr = outstr + "<tr><td align='center' valign='top'><input type=button name=button value=提交 onClick='javascript:selectPayway();'></td></tr></table>";
        out.write(outstr);
    }else{
        String outstr = "<table width='80%' border='0' cellpadding='0' cellspacing='0'>\n" +
            "<tr><td align=left>支付方式</td></tr><tr><td align=left><input type=radio name=payway value=0 checked>货到付款(免费)</td></tr>"+
        "<tr><td align='center' valign='top'><input type=button name=button value=提交 onClick='javascript:selectPayway();'></td></tr></table>";
        out.write(outstr);
    }
%>