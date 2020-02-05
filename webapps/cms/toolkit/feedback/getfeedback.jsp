<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.FeedBack" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int id = ParamUtil.getIntParameter(request,"id",0);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);

    IFeedbackManager fMgr = FeedbackPeer.getInstance();

    FeedBack fd = fMgr.getAFeedbackInfo(id);

    if(startflag == 1){
        String answer = ParamUtil.getParameter(request,"answer");
        FeedBack afd = new FeedBack();
        afd.setAnswer(answer);
        afd.setAnswertime(new Timestamp(System.currentTimeMillis()));
        afd.setId(id);
        fMgr.answerAFeedbackInfo(afd);
    }

%>
<html>
<head>
<title>用户反馈</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<script language="JavaScript" src="include/setday.js" ></script>
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
    function golist(r,str){
      window.location = "index.jsp?startrow="+r+str;
    }




     function del(id){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "delete.jsp?startflag=1"+"&id="+id;
      }
    }

    function upflag(id,flag){
        var flags;
        if(flag == 0){
            flags = 1;
        }
        if(flag == 1){
            flags = 0;
        }
        var val;
        val = confirm("你确定更改显示方式？");
        if(val == 1){
             window.location = "flag.jsp?startflag=1"+"&flag="+flags+"&id="+id;
        }
    }

    function edit(id){
      window.location = "outlinecard.jsp?id="+id;
  }
   function add(id){
       window.open("add.jsp?id="+id,"","height=500,width=800");
   }
    function tj(id){
       window.open("tj.jsp?id="+id,"","height=500,width=800");
   }
  </script>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
    <form action="getfeedback.jsp" method="post" name="form">
        <input type="hidden" name="startflag" value="1">
        <input type="hidden" name="id" value="<%=id%>">
<table width="80%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">用户反馈</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">用户名</td>
                    <td align="left" width="50%"><%=fd.getUserid()==null?"": StringUtil.gb2iso4View(fd.getUserid())%></td>
                </tr>
                 <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">email</td>
                    <td align="left" width="50%"><%=fd.getEmail()==null?"": StringUtil.gb2iso4View(fd.getEmail())%></td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">电话</td>
                    <td align="left" width="50%"><%=fd.getPhone()==null?"": StringUtil.gb2iso4View(fd.getPhone())%></td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">标题</td>
                    <td align="left" width="50%"><%=fd.getTitle()==null?"": StringUtil.gb2iso4View(fd.getTitle())%></td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">内容</td>
                    <td align="left" width="50%"><%=fd.getContent()==null?"": StringUtil.gb2iso4View(fd.getContent())%></td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right">回复</td>
                    <td align="left" width="50%"><input type="text" name="answer" value="<%=fd.getAnswer()==null?"": StringUtil.gb2iso4View(fd.getAnswer())%>"></td>
                </tr>
                  <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="100%" align="center" colspan="2"><input type="submit" value="回 复"> </td>

                </tr>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
</form>
</center>
</body>
</html>