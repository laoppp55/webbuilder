<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@page contentType="text/html;charset=utf-8"  %>
<%
   /* Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }*/
    String url = application.getRealPath("/") + "sites" + java.io.File.separator +  "images"+ File.separator + "buttons"+ File.separator;
   
    File file = new File(url);
     String[] f = file.list();
    int type = ParamUtil.getIntParameter(request,"type",0);
%>
<html>
<head>
<title></title>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
.TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
.FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
.FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
.FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
</style>
    <script language="javascript">
    function select(name)
    {
       /* <%if(type==0){%>
        window.opener.document.getElementById("okimage").value = name;
        <%}else if(type==1){%>
        window.opener.document.getElementById("cancelimage").value = name;
        <%}else if(type==2){%>
        window.opener.document.getElementById("addressimage").value = name;
    <%}else if(type==6){%>
        window.opener.document.getElementById("submitsimage").value = name;
    <%}else if(type==3){%>
        window.opener.document.getElementById("sendwayimage").value = name;
    <%}else if(type==4){%>
        window.opener.document.getElementById("paywayimage").value = name;
    <%}else if(type==5){%>
        window.opener.document.getElementById("orderimage").value = name;
    <%}else if(type==7){%>
        window.opener.document.getElementById("editsendwayimage").value = name;
    <%}else if(type==8){%>
        window.opener.document.getElementById("editpaywayimage").value = name;
    <%}else if(type==9){%>
        window.opener.document.getElementById("invoiceimage").value = name;
    <%}else if(type==10){%>
        window.opener.document.getElementById("editinvoiceimage").value = name;
    <%}%>*/
       window.returnValue = name;
        top.close();
    }
  </script>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
<table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">按钮小图片</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">

                  <%
                      int num = 0;
                      for(int i =0;i < f.length;i++){
                          File tf = new File(url + f[i]);
                          if(tf.exists()){
                              if(!tf.getName().equals(".svn") && !tf.getName().endsWith(".db")){
                                  num ++;
                  %>
                  <td align="center" width="15%"><a href="javascript:select('<%=tf.getName()%>');" title="点击选择图片"> <img src="/webbuilder/sites/images/buttons/<%=tf.getName()%>" border="0" ></a> </td>
                      <%}if(num % 6 == 0){%>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <%}if(i == f.length - 1){%>
                      </tr>
                  <%}}}%>

                <tr  bgcolor="#FFFFFF" class="css_001">

                </tr>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
</center>
</body>
</html>