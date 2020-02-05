<%@page import="java.io.*,
                java.util.*,
                java.sql.*,
                java.text.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.server.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  int forumid = ParamUtil.getIntParameter(request, "forumid", 0);
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
  String[] hiddenthread = request.getParameterValues("hiddenthread");

  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();

  if(startflag == 1){
    for(int i=0;i<hiddenthread.length;i++){
      bbsMgr.updateFlag(Integer.parseInt(hiddenthread[i]),5);
    }
    out.println("<script language=javascript>");
    out.println("opener.history.go(0);");
    out.println("</script>");
  }

  List list = new ArrayList();
  list = bbsMgr.getAllThread(forumid,2,0);
%>
<html>
<head>
<title>布衣书局</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="images/bbs.css">
<script language="javascript">
  function CheckAll(form){
     for (var i=0;i<form.elements.length;i++){
        var e = form.elements[i];
        if (e.name != 'chkAll')
          e.checked = form.chkAll.checked;
      }
  }

  function check(form){
    var flag = false;
    for (var i=0;i<form.elements.length;i++){
      if(form.elements[i].checked){
        flag = true;
      }
    }
    if(!flag){
      alert("请您选择要解除隐藏的帖子！");
      return false;
    }else{
      var val;
      val = confirm("您确定要解除隐藏这些帖子吗？");
      if(val)
        return true;
      else
        return false;
    }
}
</script>
</head>

<body bgcolor="#FFFFFF" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="hiddenform" method="post" action="hiddened.jsp">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="forumid" value="<%=forumid%>">
<table width="95%" border="0" cellspacing="1" cellpadding="4" class="txt" align="center">
<tr bgcolor="#0877af" align="center">
<td width="5%"></td>
<td width="5%"></td>
<td width="40%"><font color="#FFFFFF"><b>主题</b></font></td>
<td width="15%"><font color="#FFFFFF"><b>作者 </b></font></td>
<td width="15%"><font color="#FFFFFF"><b>回复/人气</b></font></td>
<td width="15%"><font color="#FFFFFF"><b>最后更新 </b></font></td>
<td><font color="#FFFFFF"><b>回复人</b></font></td>
</tr>
</table>
<table width="95%" border="0" cellspacing="1" cellpadding="4" class="txt" align="center">
<%
  String bgcolor = "";
  int threadid = 0;
  String threadname = "";
  String author = "";
  int answernum = 0;
  int hitnum = 0;
  String answertime = "";
  String answeruser = "";
  int typeflag = -1;
  int lockflag = -1;

  for(int i=0;i<list.size();i++)
  {
    bbs = (BBS)list.get(i);
    threadid = bbs.getThreadID();
    threadname = bbs.getThreadName();
    author = bbs.getAuthor();
    answernum = bbs.getAnswerNum();
    hitnum = bbs.getHitNum();
    answertime = bbs.getAnswerTime().toString().substring(0, 19);
    answeruser = bbs.getAnswerUser();
    typeflag = bbs.getTypeFlag();
    lockflag = bbs.getLockFlag();

    if(i % 2 == 0)
    {
      bgcolor = "#FFFFFF";
    }else{
      bgcolor = "#F4F4F4";
    }
%>
<tr>
<td bgcolor=<%=bgcolor%> width="5%"><input type="checkbox" value="<%=threadid%>" name="hiddenthread"></td>
<%
  if(typeflag == 0){
%>
<td bgcolor=<%=bgcolor%> width="5%"><img src="images/blue-face.GIF"></td>
<%}else if(typeflag == 1){%>
<td bgcolor=<%=bgcolor%> width="5%"><img src="images/red-face.GIF"></td>
<%}else{%>
<td bgcolor=<%=bgcolor%> width="5%"><img src="images/black-face.GIF"></td>
<%}%>
<td bgcolor=<%=bgcolor%> width="40%"><%if(lockflag == 1){%><b><font color=red></font></b><%}%><a href="updateHitnum.jsp?threadid=<%=threadid%>&forumid=<%=forumid%>" target=_blank><%=threadname%></a></td>
<td bgcolor=<%=bgcolor%> width="15%"><a href="user.jsp?userid=<%=author%>" target=_blank><%=author%></a></td>
<td bgcolor=<%=bgcolor%> width="15%"><%=answernum%>/<%=hitnum%></td>
<td bgcolor=<%=bgcolor%>><%=answertime%></td>
<td bgcolor=<%=bgcolor%>><a href="user.jsp?userid=<%=answeruser%>" target=_blank><%=answeruser%></a></td>
</tr>
<%
  }
%>
<tr><td>&nbsp;</td></tr>
<tr><td colspan=7><input type="checkbox" name="chkAll" value="on"  onclick="javascript:CheckAll(this.form);">全部选中</td></tr>
</table>
<br>
<center><input type="submit" value="解除隐藏" onclick="javascript:return check(this.form);"></center>
</form>
</body>
</html>

