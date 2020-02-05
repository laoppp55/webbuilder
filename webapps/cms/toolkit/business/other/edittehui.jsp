<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.booyee.search.*" contentType="text/html;charset=gbk"
%>
<%
  long id = ParamUtil.getLongParameter(request, "id", 0);
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
  float price = ParamUtil.getFloatParameter(request, "price", 0);

  Search search = new Search();
  ISearchManager searchMgr = SearchPeer.getInstance();
  search = searchMgr.getABook(id);

  if(startflag == 1){
    float saleprice = search.getSale_Price();
    float youhui = saleprice - price;
    searchMgr.updateTeHui(search.getBookID(),youhui);
    out.println("<script language=\"javascript\">");
    out.println("opener.history.go(0);");
    out.println("window.close();");
    out.println("</script>");
  }
%>
<html>
<head>
<title>图书特惠</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../../images/pt9.css">
<link rel=stylesheet type=text/css href=../style/global.css>
</head>

<body bgcolor="#FFFFFF">
<center><br>
<form action="edittehui.jsp" method="post">
<input type="hidden" name="id" value="<%=id%>">
<input type="hidden" name="startflag" value="1">
        <table width="347" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">图书特惠</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
                <table width="100%" border="0" cellpadding="2" cellspacing="1">
                  <tr bgcolor="#FFFFFF">
                    <td align="center">书名：</td>
                    <td align="center"><%=new String(search.getBookName().getBytes("iso8859_1"),"GBK")%></td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td align="center">售价：</td>
                    <td align="center"><%=search.getSale_Price()%>&nbsp;元</td>
                  </tr>
                  <tr bgcolor="#FFFFFF">
                    <td align="center">特惠价：</td>
                    <td align="center"><input type="text" size=8 name="price">&nbsp;元
                    </td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
        </table>
        <p align="center"><input type="submit" name="Ok" value="修改"></p>
</form>
<p>&nbsp; </p>
</center>
</body>
</html>
