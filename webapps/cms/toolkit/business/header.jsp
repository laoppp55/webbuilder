<%@ page import="java.sql.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.security.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title></title>
    <link rel=stylesheet type=text/css href=style/global.css>
    <script language=javascript>
        function exit()
        {
            parent.window.location = "../../exit.jsp";
        }
    </script>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#B30121">
        <td height="45">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="../../images/opencms.gif" width="163" height="45"></td>
                    <td align="right" valign="center">
                        <a href=../../help/index.htm target=_blank><img src=../../images/icon-help.gif width=37 height=43 border=0></a>
                        <a href=javascript:exit();><img src=../../images/icon-exit.gif width=34 height=43 border=0 alt=退出></a>
                    </td>
                </tr>
            </table>
        </td>
        <td bgcolor="#B30121">&nbsp;</td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#000000">
        <td height="2"></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFAD0C">
    <tr>
        <td height="23">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="100%" align="left">&nbsp;&nbsp;
                        <%
                            if((authToken != null)){
                                out.println("<a href=member/index2.jsp target=main>客户管理</a>");
                            }else {
                                out.println("客户管理");
                            }

                            /*if((authToken != null)){
                                out.println(" | <a href=supplier/supplier.jsp target=main>供货商管理</a>");
                            }else {
                                out.println(" | 供货商管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=product/index.jsp target=main>商品管理</a>");
                            }else {
                                out.println(" | 商品管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=purchasemaster/purchasemaster.jsp target=main>进货管理</a>");
                            }else {
                                out.println(" | 进货管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=deliverymaster/deliverymaster.jsp target=main>出货管理</a>");
                            }else {
                                out.println(" | 出货管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=account/index.jsp target=main>退货管理</a>");
                            }else {
                                out.println(" | 退货管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=changemaster/changemaster.jsp target=main>库存管理</a>");
                            }else {
                                out.println(" | 库存管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=account/index.jsp target=main>应收帐管理</a>");
                            }else {
                                out.println(" | 应收帐管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=account/index.jsp target=main>应付帐管理</a>");
                            }else {
                                out.println(" | 应付帐管理");
                            }*/

                            if((authToken != null)){
                                out.println("<a href=order/index.jsp target=main>订单管理</a>");
                            }else {
                                out.println("订单管理");
                            }

                            if((authToken != null)){
                                out.println(" | <a href=message/index.jsp target=main>站内消息系统</a>");
                            }else {
                                out.println(" | 站内消息系统");
                            }


                            out.println(" | <a href=javascript:parent.window.location='../../index1.jsp';>返回</a>");

                        %>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>