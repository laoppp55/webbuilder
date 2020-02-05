<%@page import="com.bizwink.cms.util.*,
                com.bizwink.bbs.bbs.*" contentType="text/html;charset=gbk"
%>
<%
  int threadid = ParamUtil.getIntParameter(request, "threadid", 0);
  int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
  IBBSManager bbsMgr = BBSPeer.getInstance();
  BBS bbs = new BBS();
  bbs = bbsMgr.getAThread(threadid);

  String threadname = bbs.getThreadName();
  String url = "";
  String threadcontent = bbs.getThreadContent();
  if(threadcontent.indexOf("<img src=") != -1){
    url = threadcontent.substring(threadcontent.indexOf("<img src=")+10,threadcontent.indexOf("\">"));
    threadcontent = threadcontent.substring(0, threadcontent.indexOf("<img src="));
  }

  if(startflag == 1){
    threadname = ParamUtil.getParameter(request, "threadname");
    threadcontent = ParamUtil.getParameter(request, "threadcontent");
    url = ParamUtil.getParameter(request, "url");

    if((url != "")&&(url != null)&&(!url.equals("null")))
      threadcontent = threadcontent + "<img src=\"" + url + "\">";
    bbsMgr.updateAThread(threadname,threadcontent,threadid);

    out.println("<script language=javascript>");
    out.println("opener.history.go(0);");
    out.println("window.close();");
    out.println("</script>");
  }
%>
<html>
<head>
<title><%=threadname%></title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet href="images/chengjian.css" style="text/css">
<script language="javascript">
function check(){
  var val = confirm("你确定要修改本帖子吗？");
  if(val == 1){
    return true;
  }else{
    return false;
  }
}
</script>
</head>
<body>
<table width="95%" border="0" cellspacing="0" cellpadding="4" class="txt">
<form name="postForm" method="post" action="edit.jsp">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="threadid" value="<%=threadid%>">
<tr>
<td>标题：
            <input type="text" name="threadname" size="60" value="<%=threadname%>">
</td>
</tr>
<tr>
<td>
<textarea name="threadcontent" cols="80" rows="10"><%=threadcontent%></textarea>
</td>
</tr>
<tr>
<td>图片链接：
<input type="text" name="url" size="50" value="<%=url%>">
<br>
(完整链接地址，如 http://www.booyee.com.cn/booyee.gif)
</td>
</tr>
<tr>
<td>
<input type="submit" name="Submit2" value="修改" onclick="javascript:return check();">
</td>
</tr>
</form>
</table>
<br>
<%@include file="tail.jsp"%>
</td>
</tr>
</table>
</body>
</html>