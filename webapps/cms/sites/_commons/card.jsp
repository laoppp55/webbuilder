<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.business.Product.IProductManager" %>
<%@ page import="com.bizwink.cms.business.Order.IOrderManager" %>
<%@ page import="com.bizwink.cms.business.Product.Product" %>
<%@ page import="com.bizwink.cms.business.Product.productPeer" %>
<%@ page import="com.bizwink.cms.business.Order.orderPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.cms.business.Order.Card" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int pid = ParamUtil.getIntParameter(request,"pid",0);
    String cards = ParamUtil.getParameter(request,"cards");
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        String sitename = request.getServerName();  //site name
        IFeedbackManager feedbackMgr = FeedbackPeer.getInstance();
        int siteid = 0;
        siteid = feedbackMgr.getSiteID(sitename);
        String cardnum = ParamUtil.getParameter(request,"cardnum");
        String code = ParamUtil.getParameter(request,"code");
        IOrderManager corderMgr = orderPeer.getInstance();
        IProductManager productMgr = productPeer.getInstance();
        Product product = new Product();
        product = productMgr.getAProduct(pid);
        int voucher = 0;
        if(product != null){
            voucher = product.getVoucher();
        }
        //判断购物券是否可用
        int flag = corderMgr.checkCard(siteid,cardnum,code,voucher);
        if(flag == -1){
            //购物券不可用
            out.println("<script language=javascript>");
            out.println("alert(\"购物券不可用\");");
            out.println("</script>");
        }
        if(flag == 0){
            //此商品不允许使用大于voucher面额的购物券
            out.println("<script language=javascript>");
            out.println("alert(\"此商品不允许使用面额大于"+voucher+"的购物券\");");
            out.println("</script>");
        }
        if(flag>0){
            session.setAttribute(String.valueOf(pid),String.valueOf(flag));
            IOrderManager orderMgr = orderPeer.getInstance();
                //更新购物券信息
            orderMgr.updateCardUsed(flag);
            out.println("<script language=javascript>");
            out.print("var objXml1 = new ActiveXObject(\"Microsoft.XMLHTTP\");");
            out.print("objXml1.open(\"POST\", \"/_commons/productinfo.jsp\", false);");
            out.print("objXml1.send();");
            out.print("var retstr1 = objXml1.responseText;");
            out.print("if(retstr1 != null && retstr1.length > 0){");
            out.print("opener.document.getElementById(\"productinfo\").innerHTML = retstr1;");
            out.print("}");
            //out.println("opener.confirmform."+cards+".value="+denomination1+";");
            out.println("window.close();");
            out.println("</script>");
        }
    }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
  <title>输入购物券信息</title>
  <link href="images/drtv.css" rel="stylesheet" type="text/css"/>
</head>
<script type="text/javascript">
function check()
{
    if(document.cardform.cardnum.value == null || document.cardform.cardnum.value == ""){
        alert("请输入您的购物券序列号！");
        document.cardform.cardnum.focus();
        return false;
    }
    if(document.cardform.code.value == null || document.cardform.code.value == ""){
        alert("请输入您的购物券激活码！");
        document.cardform.code.focus();
        return false;
    }
    return true;
}
</script>
<body>
<center>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td align="center">
<form action="card.jsp" name="cardform" method="post" onsubmit="return check();">
<input name=startflag type=hidden value=1>
<input name=pid type=hidden value=<%=pid%>>
    <input name=cards type=hidden value=<%=cards%>>
  <table width="100%" border="0" cellspacing="1" cellpadding="4">
    <tr>
      <td width="50%" align="right">序列号：</td>
      <td width="50%" align="left"><input type="text" name="cardnum"> </td>
    </tr>
      <tr>
      <td width="50%" align="right">激活码：</td>
      <td width="50%" align="left"><input type="text" name="code"> </td>
    </tr>
    <tr>
        <td align=center colspan=4><input type="submit" name="sub" value="确定"></td>
    </tr>
  </table>
</form>
</table>
</center>
</body>
</html>