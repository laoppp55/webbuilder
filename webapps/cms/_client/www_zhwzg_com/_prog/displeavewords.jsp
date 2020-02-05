<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page contentType="text/html;charset=gbk" pageEncoding="gbk" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>用户留言</title>
<link href="/css/wzgstyle.css" rel="stylesheet" type="text/css" />
</head>

<body><table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" background="/images/2010527wzg-bg.gif" style=" background-repeat:repeat-y;">
  <tr>
    <td width="233" align="left" valign="top"><table width="233" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" valign="top"><img src="/images/2010527wag-logo2.jpg" width="128" height="433" /></td>
        <td width="2"><img src="/images/2010527wzg-line2.gif" width="2" height="434" /></td>
        <td width="103" align="left" valign="top"><%@include file="/www_zhwzg_com/inc/menu.shtml" %></td>
      </tr>
    </table>
      <%@include file="/www_zhwzg_com/inc/expert.shtml" %></td>
    <td width="767" align="left" valign="top"><%@include file="/www_zhwzg_com/inc/weather.shtml" %><table width="767" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" valign="top"><img src="/images/2010527wag-gsjj7.jpg" width="767" height="218" /></td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
        </tr>
      </table>
      <table width="767" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/space.gif" width="13" height="1" /></td>
          <td height="18" align="left" valign="bottom"><a href="/"><span class="titlered">我要咨询</span></a> <a href="/"><span class="entitlered">Consult</span></a></td>
        </tr>
        <tr>
          <td width="13"><img src="/images/space.gif" width="13" height="1" /></td>
          <td><img src="/images/2010527wag-tbg.jpg" width="740" height="8" /></td>
        </tr>
      </table>
      <table width="767" border="0" cellspacing="0" cellpadding="0" background="/images/2010527wag-tbg2.jpg" style="background-repeat:no-repeat;">
  <tr>
    <td width="13"><img src="/images/space.gif" width="13" height="1" /></td>
    <td width="754" height="368" align="left" valign="top">
    <table width="734" border="0" cellspacing="0" cellpadding="0">
              <tr>
            <td><img src="/images/space.gif" height="12" /></td>
          </tr>
          <tr>
            <td>
            <div style="overflow:auto; height:336px;scrollbar-face-color: #98642B;
    scrollbar-highlight-color: #98642B;
    scrollbar-shadow-color: #98642B;
    scrollbar-arrow-color: #DAAE7F;
    scrollbar-base-color: #98642B;
    scrollbar-dark-shadow-color: #98642B;}">

    <%
           int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
           int range = ParamUtil.getIntParameter(request, "range", 10);

           IWordManager wordMgr = LeaveWordPeer.getInstance();
           List list = new ArrayList();
           List rowsList = new ArrayList();

           int rows = 0;
           String sqlstr = "select * from tbl_leaveword where siteid = '1492' order by writedate desc";
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
    %>
    <script language="javascript">
        function golist(r){
          window.location = "displeavewords.jsp?startrow="+r;
        }</script>
        <form action="displeavewords.jsp" method="post" name="form">
     <%
        Word word = new Word();
        for(int i = 0 ; i < list.size(); i++){
            word = (Word)list.get(i);
            //判断是否为ip地址
            Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
            Matcher matcher  =  pattern.matcher(word.getUserid());
            %>
     <table width="710" border="0" cellspacing="0" cellpadding="0">
         <tr>
                <td height="25" align="left" valign="middle"><strong><%=matcher.matches() ? "游客" : word.getUserid()%></strong>  <%=word.getWritedate().toString().substring(0,10).replaceAll("-",".")%></td>
                </tr>

            <tr>
                <td align="left" valign="top">内容：<%=word.getContent()%></td>
                </tr>
         <% if (word.getRetcontent() == null || word.getRetcontent().trim().equals("")){
         }else{%>
                   <Tr>
                   <Td style="padding-left:15px;"><span class="smallred">回复：</span> <%=word.getRetcontent() %></Td>
                </Tr>
         <%}%>
                <tr>
                    <Td height="20"></Td>
                </tr>
                <tr>
                    <Td background="/images/2010527wzg-bgxu.gif"><img src="/images/space.gif" height="2" /></Td>
                </tr>
                <tr>
                    <Td height="20"></Td>
                </tr>
    </table>
    <%}%>
                </div>
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
            [<a href="displeavewords.jsp?startrow=<%=startrow-range%>" class="css_002">上一页</a>]
            <%}
              if((startrow+range)<rows){
            %>
            [<a href="displeavewords.jsp?startrow=<%=startrow+range%>" class="css_002">下一页</a>]
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
    </form>

          </td></tr>
      </table><%@include file="/www_zhwzg_com/inc/low.shtml" %>
    </td>
  </tr>
    </table>


    </body>
    </html>
