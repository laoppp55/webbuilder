<%@page import="com.bizwink.cms.util.*,
                com.bizwink.cms.business.Order.*" contentType="text/html;charset=gbk"
        %>
<%@ include file="../../../include/auth.jsp"%>
<%
    long orderid = ParamUtil.getLongParameter(request, "orderid", 0);
    int status = ParamUtil.getIntParameter(request,"status",-1);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);

    IOrderManager orderMgr = orderPeer.getInstance();
    String options= "";
    int oldstatus = 0;
    int numflag = -1;
    int moneyflag = -1;
    if(startflag==1){
        oldstatus = orderMgr.getStatus(orderid);
        if(orderid != -1 && status != -1)
        {
            orderMgr.updateStatus(orderid,status,authToken.getUserID());
        }
        out.println("<script language=\"javascript\">");
        out.println("opener.history.go(0);");
        out.println("window.close();");
        out.println("</script>");
    }else {
        oldstatus =orderMgr.getStatus(orderid);
        /*if(oldstatus==1)
            options = st1 + st2 + str6;
        else if(oldstatus==2)
           options = st2 +st4 +st5 + str6;
        else if(oldstatus==4)
            options = st3 + st4 + str6;
        else if(oldstatus==5)
           options = st2 + st5 + str6;
        else if(oldstatus==6)
            options =  str6 + st1 + st2 ;
        else*/
        System.out.println("oldstatus==" + oldstatus);
        if(oldstatus==7)
            options = "<option value=0 >请选择</option>" + "<option value=7 selected=\"selected\">等待付款</option>" +  "<option value=8 >已付款</option>" + "<option value=9 >超时取消</option>";
        else if (oldstatus==8)
            options = "<option value=0 >请选择</option>" + "<option value=7>等待付款</option>" +  "<option value=8 selected=\"selected\">已付款</option>" + "<option value=9 >超时取消</option>";
        else if (oldstatus==9)
            options = "<option value=0 >请选择</option>" + "<option value=7>等待付款</option>" +  "<option value=8>已付款</option>" + "<option value=9 selected=\"selected\">超时取消</option>";
        else
            options = "<option value=0 >请选择</option>" + "<option value=7>等待付款</option>" +  "<option value=8>已付款</option>" + "<option value=9>超时取消</option>";
    }
%>
<html>
<head>
    <title>修改订单状态</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" href="../../images/pt9.css">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <script language="javascript">
        function closewin(){
            window.close();
        }
    </script>
</head>

<body bgcolor="#FFFFFF">
<center><br>
    <form action="tostatus.jsp" method="post">
        <input type="hidden" name="orderid" value="<%=orderid%>">
        <input type="hidden" name="startflag" value="1">
        <table width="347" border="0" cellpadding="0">
            <tr bgcolor="#F4F4F4" align="center">
                <td class="moduleTitle"><font color="#48758C">修改订单状态</font></td>
            </tr>
            <tr bgcolor="#d4d4d4" align="right">
                <td>
                    <table width="100%" border="0" cellpadding="2" cellspacing="1">
                        <tr bgcolor="#FFFFFF">
                            <td align="center">订单ID：</td>
                            <td align="center"><%=orderid%></td>
                        </tr>
                        <tr bgcolor="#FFFFFF">
                            <td align="center">状态：</td>
                            <td align="center">
                                <select name="status">
                                    <%=options%>
                                </select>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
        </table>
        <p align="center"><input type="submit" name="Ok" value="修改">&nbsp;&nbsp;<input type="button" name="close" value="关闭" onclick="javascript:closewin();"></p>
    </form>
    <p>&nbsp; </p>
</center>
</body>
</html>

