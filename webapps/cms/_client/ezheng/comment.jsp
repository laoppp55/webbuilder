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
    int articleid = ParamUtil.getIntParameter(request, "articleid", 0);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        String content = ParamUtil.getParameter(request,"content");
        String links = ParamUtil.getParameter(request,"link");
        String name = ParamUtil.getParameter(request,"name");
        String ip = request.getRemoteHost();

        webComment comment = new webComment();
        comment.setAbout(articleid);
        comment.setSiteid(siteid);
        comment.setContent(content);
        comment.setLink(links);
        comment.setName(name);
        comment.setIP(ip);
        comment.setUsrid(ip);
        int errcode = cMgr.createComment(comment);
        if(errcode == 0){
            response.sendRedirect("comment.jsp?articleid=" + articleid);
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
%><html>
    <head>
        <title>我的电子商务网站</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <meta content="bzwink" name="author" />
        <meta content="商品销售" name="description" />
        <meta content="化妆品、十自绣、首饰、工艺品" name="keywords" />
        <link href="/css/link.css" rel="stylesheet" />
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
<style type="text/css">
<!--.biz_table{ border:1 dashed null;
 } 
.biz_table td{ font-size:12px; color:#000000; font-family:宋体 ; text-align:left;
}
.biz_table input{ font-size:18px;  size:12px;

}
biz_table img{ border:0;
}
-->
</style></head>
    <body leftmargin="0" topmargin="0" width="100%" marginwidth="0" marginheight="0">
        <%@include file="/www_chinabuy360_cn/include/top.shtml" %>
        <table cellspacing="0" cellpadding="0" width="1006" border="0">
            <tbody>
                <tr>
                    <td width="6" bgcolor="#ececec">&nbsp;</td>
                    <td width="32"><img alt="" src="/images/logo_leftgap02.gif" /></td>
                    <td align="center" width="220"><img height="23" alt="" width="210" border="0" src="/images/category_view.gif" /></td>
                    <td width="740" bgcolor="#ececec">
                    <table cellspacing="0" cellpadding="0" width="730" bgcolor="#ececec" border="0">
                        <tbody>
                            <tr>
                                <td width="10"><img height="30" alt="" width="10" border="0" src="/images/keyword_leftgap.gif" /></td>
                                <td width="730"><img height="10" alt="" width="2" align="absMiddle" border="0" src="/images/notice_bullet.gif" />&nbsp;<font style="FONT-SIZE: 12px"><strong>文章评论</strong></font></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td width="8" bgcolor="#ececec"></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr height="10">
                    <td></td>
                </tr>
            </tbody>
        </table>
        <a href="/www_chinabuy360_cn/_prog/ordersearch.jsp"></a>
        <table cellspacing="0" cellpadding="0" width="960" align="center" border="0">
            <tbody>
                <tr>
                    <td valign="top" width="230"></td>
                    <td valign="top" align="center" width="730">
                    <table cellspacing="1" cellpadding="1" width="500" summary="" border="1">
                        <tbody>
                            <tr>
                                <td><table    class="biz_table" border="0">
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
            &nbsp;&nbsp;&nbsp;<%=comment.getCreateDate()%></td>
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
<<a href="index.jsp?startrow=<%=startrow-range%>" class="css_002">上一页</a>>
<%}  if((startrow+range)<rows){
%>
<<a href="index.jsp?startrow=<%=startrow+range%>" class="css_002">下一页</a>>
<%}

  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
  <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>);">GO</a>
  <%}%>
</td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
</tr>
</table><table    class="biz_table">    <form name='form' action='/www_chinabuy360_cn/_prog/comment.jsp' onsubmit='return check();'><input type='hidden' name='siteid' value='36'><input type='hidden' name='articleid' value='<%=articleid%>'><input type='hidden' name='startflag' value='1'><tr><td valign='top'> &#22995;&#21517;&#65306;</td><td valign='top'><input type='text' name='name' value=''></td></tr><tr><td valign='top'>&#32852;&#31995;&#26041;&#24335;&#65306;</td><td valign='top'><input type='text' name='link' value=''></td></tr><tr><td valign='top'>&#20869;&#23481;&#65306;</td><td valign='top'><textarea rows='10' cols='50' name='content'></textarea></td></tr><tr><td valign='top' colspan='2'><input type='submit' name='sub' value='&#25552;&#20132;'></td></tr></form></table></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr height="30">
                    <td></td>
                </tr>
            </tbody>
        </table>
        <%@include file="/www_chinabuy360_cn/include/low.shtml" %>
    </body>
</html>