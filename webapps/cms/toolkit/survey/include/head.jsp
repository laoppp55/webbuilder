<%@ page import="java.sql.Date" %>
<%
  Date todaydate = new Date(System.currentTimeMillis());
%>
<table width="900" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="527"><img src="../images/logo_01.jpg" width="527" height="122" /></td>
    <td valign="bottom" background="../images/bj_01.jpg"><table width="360" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="30" align="right" class="white12"><%=todaydate%></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td height="1"></td>
    <td></td>
  </tr>
</table>