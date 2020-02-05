<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.feedback.FeedBack" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.WordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int siteid = 13;

    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    IWordManager wordMgr = WordPeer.getInstance();
    String sitename = request.getServerName();  //site name
    siteid = feedMgr.getSiteID(sitename);     //get siteid

    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    if(startflag == 1){
        int siteids = ParamUtil.getIntParameter(request,"siteid",0);
        String title = ParamUtil.getParameter(request,"title");
        String content = ParamUtil.getParameter(request,"content");
        String email = ParamUtil.getParameter(request,"email");
        String phone = ParamUtil.getParameter(request,"phone");
        String company = ParamUtil.getParameter(request,"company");
        String linkman = ParamUtil.getParameter(request,"linkman");
        String links = ParamUtil.getParameter(request,"links");
        String zip = ParamUtil.getParameter(request,"zip");
        String ip = request.getRemoteHost();

        Word word = new Word();
        word.setSiteid(siteids);
        word.setTitle(title);
        word.setContent(content);
        word.setEmail(email);
        word.setPhone(phone);
        word.setCompany(company);
        word.setLinkman(linkman);
        word.setLinks(links);
        word.setZip(zip);
        word.setUserid(ip);
        wordMgr.insertWord(word);
        response.sendRedirect("leavemessage.jsp");
        return;
    }

    List list = new ArrayList();
    List rowsList = new ArrayList();
    int rows = 0;

    String sqlstr = "select * from tbl_leaveword where flag = 1 and siteid = "+siteid +" order by writedate desc";

    list = wordMgr.getCurrentWord(sqlstr,startrow,range);
    rowsList = wordMgr.getAllWord(sqlstr);

    rows = rowsList.size();

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
        <title></title>
    <script type="text/javascript">
function golist(r){
      window.location = "index.jsp?startrow="+r;
    }
function check(){
    if(form.title.value == "" || form.title.value == null){
        alert("请输入留言的标题！");
        form.title.focus();
        return false;
    }
    if(form.content.value == "" || form.content.value == null){
        alert("请输入留言的内容！");
        form.content.focus();
        return false;
    }
    return true;
}
</script><style type="text/css">
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
    <body><table    class="biz_table" >
        <%
            for(int i = 0;i < list.size(); i++){
                Word word = (Word)list.get(i);
        %>
        <tr>
            <td valign='top'>
                标题：
            </td>
            <td valign='top'>
                <%=word.getTitle()==null?"": StringUtil.gb2iso4View(word.getTitle())%>
            </td>
        </tr>
         <tr>
            <td valign='top'>
                内容：
            </td>
            <td valign='top'>
                <%=word.getContent()==null?"": StringUtil.gb2iso4View(word.getContent())%>
            </td>
        </tr>
        <%}%>
    </table>
    <table width='70%'    class="biz_table" >
<tr valign='bottom' width=100%>
<td>
 总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
</td>
<td>
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td class='css_002'>
<%
    if((startrow-range)>=0){
%>
<<a href='index.jsp?startrow=<%=startrow-range%>' class='css_002'>上一页</a>>
<%}
  if((startrow+range)<rows){
%>
<<a href='index.jsp?startrow=<%=startrow+range%>' class='css_002'>下一页</a>>
<%}
  if(totalpages>1){%>
  &nbsp;&nbsp;第<input type='text' name='jump' value='<%=currentpage%>' size='3'>页&nbsp;
  <a href='#' class='css_002' onclick='golist((document.all('jump').value-1) * <%=range%>);'>GO</a>
  <%}%>
</td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td align='right'>&nbsp;&nbsp;&nbsp;&nbsp;</td>
</tr>
</table>
    <table    class="biz_table" >
        <form name='form' action='index.jsp' onsubmit='return check();'>
            <input type='hidden' name='siteid' value='<%=siteid%>'>
            <input type='hidden' name='startflag' value='1'>
        <tr>
            <td valign='top'>
                标题：
            </td>
            <td valign='top'>
                <input type='text' name='title' value=''>
            </td>
        </tr>
         <tr>
            <td valign='top'>
                内容：
            </td>
            <td valign='top'>
                <textarea rows='10' cols='50' name='content'></textarea>
            </td>
        </tr>
        <tr>
            <td valign='top'>
                Email：
            </td>
            <td valign='top'>
                <input type='text' name='email' value=''>
            </td>
        </tr>
        <tr>
            <td valign='top'>
                电话：
            </td>
            <td valign='top'>
                <input type='text' name='phone' value=''>
            </td>
        </tr>
            <tr>
            <td valign='top'>
                公司：
            </td>
            <td valign='top'>
                <input type='text' name='company' value=''>
            </td>
        </tr>
            <tr>
            <td valign='top'>
                联系人：
            </td>
            <td valign='top'>
                <input type='text' name='linkman' value=''>
            </td>
        </tr>
            <tr>
            <td valign='top'>
                联系方式：
            </td>
            <td valign='top'>
                <input type='text' name='links' value=''>
            </td>
        </tr>
            <tr>
            <td valign='top'>
                邮编：
            </td>
            <td valign='top'>
                <input type='text' name='zip' value=''>
            </td>
        </tr>
        <tr>
            <td valign='top' colspan='2'>
                <input type='submit' name='sub' value='提交'>
            </td>
        </tr>
            </form>
    </table>
</body>
</html>