<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.feedback.FeedBack" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    int siteid = 13;

    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    String sitename = request.getServerName();  //site name
    System.out.println("sitename=" + sitename);
    siteid = feedMgr.getSiteID(sitename);     //get siteid
    System.out.println("siteid=" + siteid);

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
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head xmlns="">
        <title>无标题文档</title><script src="/_sys_js/tanchuceng.js" type="text/javascript"></script>
        <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
        <link rel="stylesheet" type="text/css" href="/css/wzgstyle.css" />
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
    <body xmlns="">
        <table border="0" cellspacing="0" cellpadding="0" width="1000" background="/images/2010527wzg-bg.gif" align="center" style="background-repeat: repeat-y">
            <tbody>
                <tr>
                    <td valign="top" width="233" align="left">
                    <table border="0" cellspacing="0" cellpadding="0" width="233">
                        <tbody>
                            <tr>
                                <td valign="top" align="left"><img width="128" height="433" alt="" src="/images/2010527wag-logo2.jpg" /></td>
                                <td width="2"><img width="2" height="434" alt="" src="/images/2010527wzg-line2.gif" /></td>
                                <td valign="top" width="103" align="left">
                                <table border="0" cellspacing="0" cellpadding="0" width="103">
                                    <tbody>
                                        <tr>
                                            <td height="37">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td valign="top" align="left"><a href="#"><img border="0" width="103" height="21" alt="" src="/images/2010527wag-menu1.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu2.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu3.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu4.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu5.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu6.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu7.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu8.jpg" /></a><br />
                                            <img width="103" height="2" alt="" src="/images/2010527wag-menuxu.jpg" /><br />
                                            <a href="#"><img border="0" width="103" height="28" alt="" src="/images/2010527wag-menu9.jpg" /></a><br />
                                            <img width="103" height="136" alt="" src="/images/2010527wag-menulow2.jpg" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="233">
                        <tbody>
                            <tr>
                                <td valign="top" align="left"><img width="233" height="336" alt="" src="/images/2010527wag-wzg2.jpg" /></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td valign="top" width="767" align="left">
                    <table border="0" cellspacing="0" cellpadding="0" width="767" height="37">
                        <tbody>
                            <tr>
                                <td valign="bottom" width="581" align="left"><span style="line-height: 23px">珠海市今日天气情况：阴有小雨</span></td>
                                <td valign="bottom" width="186" align="left"><a href="#"><img border="0" width="28" height="21" alt="" src="/images/2010527wag-1.jpg" /></a><img width="16" height="21" alt="" src="/images/2010527wag-2.jpg" /><a href="#"><img border="0" width="58" height="21" alt="" src="/images/2010527wag-3.jpg" /></a><img width="19" height="21" alt="" src="/images/2010527wag-4.jpg" /><a href="#"><img border="0" width="42" height="21" alt="" src="/images/2010527wag-5.jpg" /></a></td>
                            </tr>
                        </tbody>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="767">
                        <tbody>
                            <tr>
                                <td valign="top" align="left"><img width="767" height="218" alt="" src="/images/2010527wag-gsjj7.jpg" /></td>
                            </tr>
                            <tr>
                                <td height="30">&nbsp;</td>
                            </tr>
                        </tbody>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="767">
                        <tbody>
                            <tr>
                                <td><img width="13" height="1" alt="" src="/images/space.gif" /></td>
                                <td height="18" valign="bottom" align="left"><span class="titlered">我要咨询</span> <span class="entitlered">Consult</span></td>
                            </tr>
                            <tr>
                                <td width="13"><img width="13" height="1" alt="" src="/images/space.gif" /></td>
                                <td><img width="740" height="8" alt="" src="/images/2010527wag-tbg.jpg" /></td>
                            </tr>
                        </tbody>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="767" background="/images/2010527wag-tbg2.jpg" style="background-repeat: no-repeat">
                        <tbody>
                            <tr>
                                <td width="13"><img width="13" height="1" alt="" src="/images/space.gif" /></td>
                                <td height="368" valign="top" width="754" align="left">
                                <table border="0" cellspacing="0" cellpadding="0" width="734">
                                    <tbody>
                                        <tr>
                                            <td><img height="12" alt="" src="/images/space.gif" /></td>
                                        </tr>
                                        <tr>
                                            <td>
                                            <div style="scrollbar-arrow-color: #daae7f; scrollbar-face-color: #98642b; scrollbar-base-color: #98642b; height: 336px; scrollbar-highlight-color: #98642b; scrollbar-shadow-color: #98642b; overflow: auto; scrollbar-dark-shadow-color: #98642B"><table    class="biz_table" >
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
<<a href='leavemessage.jsp?startrow=<%=startrow-range%>' class='css_002'>上一页</a>>
<%}
  if((startrow+range)<rows){
%>
<<a href='leavemessage.jsp?startrow=<%=startrow+range%>' class='css_002'>下一页</a>>
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
        <form name='form' action='leavemessage.jsp' onsubmit='return check();'>
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
</div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="767">
                        <tbody>
                            <tr>
                                <td height="91" valign="top" background="/images/2010527wag-lowbg.jpg" align="left" style="background-repeat: no-repeat"><br />
                                &nbsp;&nbsp;版权所有：王振国肿瘤<br />
                                &nbsp;&nbsp;医院电话：0756-2258246、0756-2220666 地址：珠海市香洲区海虹路27号 邮编：519000</td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>