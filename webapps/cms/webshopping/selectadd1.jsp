<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.business.Order.Fee" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK"%>
<%
    //获得站点id
    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    String sitename = request.getServerName();  //site name
    int siteid = feedMgr.getSiteID(sitename);     //get siteid

    //获得站点送货方式
    IOrderManager oMgr = orderPeer.getInstance();
    List list = new ArrayList();
    list = oMgr.getAllFeeInfo(siteid);
    if(list.size()>0){


        String outstr = "<table width='100%' border='0' cellpadding='0' cellspacing='0' style=\"font-size:12px\">\n" +
            "<tr><td align=left>送货方式</td></tr>";
        for(int i = 0;i < list.size();i++){
            String checkstr = " >";
            Fee fee = (Fee)list.get(i);
            String name = fee.getCname()==null?"": StringUtil.gb2iso4View(fee.getCname());
            String notes = fee.getNotes()==null?"":"("+StringUtil.gb2iso4View(fee.getNotes())+")";
            String feestr = "";
            if(fee.getFee()==0){
                feestr = "免费送货";
            }
            else{
                feestr = String.valueOf(fee.getFee());
            }
            if(i==0){
                checkstr = " checked>";
            }
            outstr = outstr + "<tr><td align=left><input type=radio name=sendway value="+fee.getId()+checkstr+name+":"+feestr+notes+"</td></tr>";
        }
        outstr = outstr + "<tr><td align='center' valign='top'><input type=button name=button value=提交 onClick='javascript:selectSendway();'></td></tr></table>";
        out.write(outstr);
    }else{
        String outstr = "<table width='100%' border='0' cellpadding='0' cellspacing='0'>\n" +
            "<tr><td align=left>送货方式</td></tr><tr><td align=left><input type=radio name=sendway value=0 checked>同城送货(免费)</td></tr>"+
        "<tr><td align='center' valign='top'><input type=button name=button value=提交 onClick='javascript:selectSendway();'></td></tr></table>";
        out.write(outstr);
    }
 %>