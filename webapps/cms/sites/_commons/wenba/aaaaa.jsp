<%@ page  import="java.util.*,com.bizwink.wenba.*,com.bizwink.util.*,java.sql.*,com.bizwink.user.*" contentType="text/html;charset=GBK"%>
<%@ page import="java.net.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParsePosition" %>
<html>
  <head>
  <%
  	int UserID = Integer.parseInt((String)request.getParameter("userid"));
  	IWenbaManager iwenba = wenbaManagerImpl.getInstance();
  	int classid = ParamUtil.getIntParameter(request,"cid",24);
  	out.print(classid);
  	wenti wen = null;
  	List list = null;
  %>
    <title></title>
<script type="text/JavaScript">
  function tiwen(){
  //USERID 回答该问题的专家的user_id
  	var url = "";
  	window.location.href=url;
  }
</script>
  </head>
  
  <body>
    <table>
      <tr>
        <td>最新回答的五个问题：</td><td></td>
      </tr>
  <%
  	list = iwenba.anszj_5(UserID);
  	for(int i=0;i<list.size();i++){
  		wen = (wenti)list.get(i);
  %>
      <tr>
        <td><%= wen.getTitles()%></td>
        <td><%= wen.getCreatedate()%></td>
      </tr>
    <%} %>
    </table>
    <table>
      <tr>
        <td><input type="button" value="向他提问" onclick="tiwen()"></td>
      </tr>
    </table>
  </body>
</html>
