<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.feedback.FeedBack" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
      int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int searchflag = ParamUtil.getIntParameter(request,"searchflag",-1);


    IFeedbackManager feedMgr = FeedbackPeer.getInstance();

    List list = new ArrayList();
    int rows = 0;
    String jumpstr = "";
    String sqlstr = "select * from tbl_feedback where siteid = "+siteid;
    String sqlnum = "select count(id) from tbl_feedback where siteid = "+siteid;
    if(searchflag == -1){
        list = feedMgr.getAllFeedbackInfo(startrow,range,sqlstr,"","");
        rows = feedMgr.getAllFeedbackInfoNum(sqlnum,"","");
    }
    else{
        jumpstr = "&searchflag=1";

        String bgntime = ParamUtil.getParameter(request,"bgntime");
        String endtime = ParamUtil.getParameter(request,"endtime");

        list = feedMgr.getAllFeedbackInfo(startrow,range,sqlstr,bgntime,endtime);
        rows = feedMgr.getAllFeedbackInfoNum(sqlnum,bgntime,endtime);
        if (bgntime != null && !bgntime.equals("null") && !bgntime.equals("")) {
            jumpstr = jumpstr + "&bgntime="+bgntime;
        }
        if (endtime != null && !endtime.equals("null") && !endtime.equals("")) {
            jumpstr = jumpstr + "&bgntime="+endtime;
        }
    }

    int totalpages = 0;
    int currentpage = 0;
    if(rows < range){
        totalpages = 1;
        currentpage = 1;
    }else{
        if(rows%range == 0)
          totalpages = rows/range;
        else
          totalpages = rows/range + 1;
        currentpage = startrow/range + 1;
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
    <form action="index.jsp" method="post" name="form">
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
                  <td width="10%" align="center">用户名</td>
                    <td align="center" width="10%">标题</td>
                    <td align="center" width="30%">内容</td>
                    <td align="center" width="30%">回复内容</td>
                    <td align="center" width="5%">反馈时间</td>
                    <td align="center" width="5%">查看详情</td>
                    <td align="center" width="5%">是否显示</td>
                    <td align="center" width="5%">删除</td>
                </tr>
                  <%
                      for(int i = 0; i < list.size();i++){
                          FeedBack fd = (FeedBack)list.get(i);

                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="10%" align="center"><%=fd.getUserid()==null?"": StringUtil.gb2iso4View(fd.getUserid())%></td>
                    <td align="center" width="10%"><%=fd.getTitle()==null?"":StringUtil.gb2iso4View(fd.getTitle())%></td>
                    <td align="center" width="30%"><%=fd.getContent()==null?"":StringUtil.gb2iso4View(fd.getContent())%></td>
                    <td align="center" width="30%"><%=fd.getAnswer()==null?"":StringUtil.gb2iso4View(fd.getAnswer())%></td>
                    <td align="center" width="5%"><%=fd.getCreatetime().toString().substring(0,10)%></td>
                    <td align="center" width="5%"><a href="getfeedback.jsp?id=<%=fd.getId()%>">查看详情</a></td>
                    <td align="center" width="5%"><a href="javascript:upflag(<%=fd.getId()%>,<%=fd.getFlag()%>)"><%if(fd.getFlag()==0){%>不显示<%}else{%>显示<%}%></a></td>
                    <td align="center" width="5%"><a href="javascript:del(<%=fd.getId()%>)">删除</a></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
        </table>
      </td>
</tr>
</table>
<table width="70%" class="css_002">
<tr valign="bottom" width=100%>
<td>
 总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
</td>
<td>
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td class="css_002">
<%
    if((startrow-range)>=0){
%>
[<a href="index.jsp?startrow=<%=startrow-range%><%=jumpstr%>" class="css_002">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="index.jsp?startrow=<%=startrow+range%><%=jumpstr%>" class="css_002">下一页</a>]
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
  <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
  <%}%>
</td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
</tr>
</table>
        </form>
    <table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<form action="index.jsp" name="searchFom">
<input type="hidden" name="searchflag" value="1">
<tr>
  <td>
    <table width="100%" border="0" cellpadding="0">
      <tr bgcolor="#F4F4F4" align="center">
        <td height="30" valign="left" bgcolor="#F4F4F4" class="css_003">查询：</td>
      </tr>
      <tr bgcolor="#d4d4d4" align="right">
        <td>
          <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
              <tr bgcolor="#FFFFFF">
                <td align="center" class="txt">日期：</td>
                <td bgcolor="#FFFFFF" class="txt"> 从(开始日期)
                <input type="text" size="10" name="bgntime" onfocus="setday(this)" readonly>
                 到(结束日期)
                  <input type="text" size="10" name="endtime" onfocus="setday(this)" readonly>
                </td>
             </tr>
           </table>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
<td align=center><input type="submit" value="查询"></td>
</tr>
</form>
</table>
</center>
</body>
</html>