<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.comment.webCommentPeer" %>
<%@ page import="com.bizwink.webapps.comment.IWebCommentManager" %>
<%@ page import="com.bizwink.webapps.comment.webComment" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int siteid = 13;

    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    IWebCommentManager cMgr = webCommentPeer.getInstance();
    String sitename = request.getServerName();  //site name
    siteid = feedMgr.getSiteID(sitename);     //get siteid
    int articleid = ParamUtil.getIntParameter(request, "id", 0);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        int siteids = ParamUtil.getIntParameter(request,"siteid",0);
        articleid = ParamUtil.getIntParameter(request,"articleid",0);
        String content = ParamUtil.getParameter(request,"content");
        String links = ParamUtil.getParameter(request,"link");
        String name = ParamUtil.getParameter(request,"name");
        String ip = request.getRemoteHost();

        webComment comment = new webComment();
        comment.setAbout(articleid);
        comment.setSiteid(siteids);
        comment.setContent(content);
        comment.setLink(links);
        comment.setName(name);
        comment.setIP(ip);
        comment.setUsrid(ip);
        int errcode = cMgr.createComment(comment);
        if(errcode == 0){
            response.sendRedirect("index.jsp");
        }else{
            response.sendRedirect("err.jsp");
        }
        return;
    }

    List list = new ArrayList();
    List rowsList = new ArrayList();
    int rows = 0;

    String sqlstr = "select * from tbl_comment where siteid = " + siteid + " and about = " + articleid + " order by createdate desc";

    list = cMgr.getAllcommentInfo(sqlstr,startrow,range);
    rows = cMgr.getAllCommentNum("select count(*) from tbl_comment where siteid = "+siteid+" and about = "+articleid);

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

</head>
<script type="text/javascript">
function golist(r){
      window.location = "index.jsp?startrow="+r;
    }
function check(){
    if(form.content.value == "" || form.content.value == null){
        alert("请输入评论的内容！");
        form.content.focus();
        return false;
    }
    return true;
}
</script>
<body>
<center>
    <table cellpadding="0" cellspacing="0" border="0">
        <%
            for(int i = 0;i < list.size(); i++){
                webComment comment = (webComment)list.get(i);
        %>
        <tr>
            <td valign="top">
                评论人：
            </td>
            <td valign="top">
                <%=comment.getUsrid()==null?"": StringUtil.gb2iso4View(comment.getUsrid())%>
            </td>
        </tr>
         <tr>
            <td valign="top">
                内容：
            </td>
            <td valign="top">
                <%=comment.getContent()==null?"": StringUtil.gb2iso4View(comment.getContent())%>
            </td>
        </tr>

        <%}%>
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
[<a href="index.jsp?startrow=<%=startrow-range%>" class="css_002">上一页</a>]
<%}
  if((startrow+range)<rows){
%>
[<a href="index.jsp?startrow=<%=startrow+range%>" class="css_002">下一页</a>]
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
  <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>);">GO</a>
  <%}%>
</td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
</tr>
</table>
    <table cellpadding="0" cellspacing="0" border="0">
        <form name="form" action="index.jsp" onsubmit="return check();">
            <input type="hidden" name="siteid" value="<%=siteid%>">
            <input type="hidden" name="aritcleid" value="0">
            <input type="hidden" name="startflag" value="1">
        <tr>
            <td valign="top">
                姓名：
            </td>
            <td valign="top">
                <input type="text" name="name" value="">
            </td>
        </tr>
             <tr>
            <td valign="top">
                联系方式：
            </td>
            <td valign="top">
                <input type="text" name="link" value="">
            </td>
        </tr>
         <tr>
            <td valign="top">
                内容：
            </td>
            <td valign="top">
                <textarea rows="10" cols="50" name="content"></textarea>
            </td>
        </tr>

        <tr>
            <td valign="top" colspan="2">
                <input type="submit" name="sub" value="提交">
            </td>
        </tr>
            </form>
    </table>
</center>
</body>
</html>