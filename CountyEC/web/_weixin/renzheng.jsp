<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.bizwink.weixin.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018-12-02
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    AccessTokenResult accessTokenResult = HttpUtility.getToken("wx5c345dba1ac0184c","274bb2545802a0ac31db9e7c4df03209");
    response.getOutputStream().println(accessTokenResult.getAccess_token() + "<br />"+accessTokenResult.getExpires_in() + "<br />" + request.getQueryString());
    Enumeration enu=request.getParameterNames();
    while(enu.hasMoreElements()){
        String paraName=(String)enu.nextElement();
        System.out.println(paraName+": "+request.getParameter(paraName));
    }
    response.setContentType("text/html;charset=UTF-8");
    String wxMsgXml =IOUtils.toString(request.getInputStream(), "utf-8");
    System.out.println("wxMsgXml==" + wxMsgXml);

    boolean eventType =wxMsgXml.contains("Event") ;// 如果包含，则是触发事件
    RequestTextMessage textMsg =null ;
    String returnXml ="";
    StringBuffer replyMsg = new StringBuffer();
    if(!eventType){ //信息交互事件
        textMsg = ReplyMessage.getRequestTextMessage(wxMsgXml);//解析用户输入的信息
        String content = textMsg.getContent().trim();//用户发送信息
        String openId= textMsg.getFromUserName().trim();//发送方账号（OpenID）
        String creattime=textMsg.getCreateTime();
        String msgType=textMsg.getMessageType();// 发送类型
        String tousername=textMsg.getToUserName();//用户微信号
        boolean  flag= SignUtil.isContainsNumOrLetter(content);

        if(flag) {
            //将数据入库
            //Serial serial = new Serial();
            //serial.setAddtime(new Date());
            //serial.setOpen_id(openId);
            //serial.setName(tousername);
            //serial.setSn(content);
            //serial.setBt_mac_addr("");
            //serial.setWifi_mac_addr("");
            //serialService.insertSelective(serial);
            //serialService.insert(serial);
            replyMsg.append("返回信息！");
            returnXml = ReplyMessage.getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName(),textMsg.getToUserName());
        }else {
            replyMsg.append("输入字符不符合格式！");
            returnXml = ReplyMessage.getReplyTextMessage(replyMsg.toString(), textMsg.getFromUserName(),textMsg.getToUserName());
        }
    }
    PrintWriter pw = response.getWriter();
    pw.println(returnXml);
%>