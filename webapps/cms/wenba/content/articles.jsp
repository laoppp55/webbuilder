<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.orderArticleListManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.server.CmsServer" contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.Map" %>
<%@ page import="com.bizwink.cms.refers.IRefersManager" %>
<%@ page import="com.bizwink.cms.refers.RefersPeer" %>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page import="com.bizwink.wenba.wenbaImpl" %>
<%@ page import="com.bizwink.wenba.Wenti" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
   
     IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
     wenbaImpl column=null;

    int count = 0;
    int zongpage = 0;
    int recordcount = 10;
    int ipage=0;

    count =columnMgr.count(columnID);
   
   String dpage=request.getParameter("dpage");
       zongpage = (count + recordcount - 1) / recordcount;

       try{
	     	ipage=Integer.parseInt(dpage);
	   }catch(Exception e){
			ipage=1;
       }

       if (ipage <= 0) ipage = 1;
       if (ipage >= zongpage) ipage = zongpage;
     List  list1=columnMgr.getWenttiList(ipage,columnID);
      // list = iarticle.listPage(ipage,id);
      // Iterator iterator = list.iterator();
       if(list1.isEmpty()) {
         //  System.out.println("is empty*********"+count);
       }
          
%>

<html>
<head>
<title>所有文章</title>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../../style/global.css">
<script language="javascript" src="../js/setday.js"></script>

</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>

<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
<form action="doArticleRSS.jsp" method=Post name=form1>

<tr class=itm bgcolor="#dddddd" height=20>
    
    <td align=center width="4%">IP</td>
    <td align=center width="33%">问题</td>
    <td align=center width="2%">是否是投票问题</td>
    <td align=center width="6%">悬赏数值</td>
    
    <td align=center width="10%">登陆者名字</td>
    <td align=center width="5%">提问者所在省份</td>
    <td align=center width="4%">提问者所在城市</td>
    <td align=center width="4%">提问者所在地区</td>
    <td align=center width="4%">问题创建日期</td>
    <td align=center width="4%">答案数量</td>
    <td align=center width="6%">修改</td>
    <td align=center width="6%">删除</td>
     <td align=center width="6%">禁止问题显示</td>
</tr>
       <%
       for(int j=0;j<list1.size();j++)
          {

                   Wenti w=(Wenti)list1.get(j);
                   String istoupiao="";
                   if(w.getVoteflag()==1)
                   {
                          istoupiao="是";
                   }
                   else{
                        istoupiao="否";
                    }
                    String name=null;
                    name=w.getUsername();
                    if(name==null)
                    {
                       name="&nbsp;";
                     }
                     String ip=null;
                     ip=w.getIpaddress();
                     if(ip==null)
                     {
                     ip=" &nbsp;";
                      }
                      String  pro=null;
                      pro=w.getProvince();
                      if(pro==null)
                      {
                        pro=" &nbsp;";
                      }
                      String city=w.getCity();
                      if(city==null)
                      {
                           city="&nbsp;";
                       }
                       String area=w.getArea();
                       if(area==null)
                       {
                       area="&nbsp;";
                        }
       %>
       <tr class=itm bgcolor="#dddddd" height=20>
           <td align=center width="4%"><%=ip%></td>
    <td align=center width="36%"><%=w.getQuestion()%></td>
    <td align=center width="2%"><%=istoupiao%></td>
    <td align=center width="6%"><%=w.getXuanshang()%></td>

    <td align=center width="10%"><%=name%></td>
    <td align=center width="5%"><%=pro%></td>
    <td align=center width="4%"><%=city%></td>
    <td align=center width="4%"><%=area%></td>
    <td align=center width="4%"><%=w.getCreatedate()%></td>
   <td align=center width="6%"><%=w.getAnswernum()%></td>
    <td align=center width="6%"><a href="answer.jsp?id=<%=w.getId()%>" target="_blank">查看答案</a></td>
      <td align=center width="6%"><%if(w.getIstop()==0){%><a href="istop.jsp?id=<%=w.getId()%>&column=<%=columnID%>&istop=1&dpage=<%=dpage%>" >置顶</a><%}else{%>&nbsp;<a href="istop.jsp?id=<%=w.getId()%>&column=<%=columnID%>&istop=0&dpage=<%=dpage%>" >取消置顶</a><%}%></td>
    <td align=center width="6%"><a href="delete.jsp?id=<%=w.getId()%>&dpage=<%=dpage%>&column=<%=columnID%>">删除</a></td>
    <td align=center width="6%"><%if(w.getStatus()==2){%><a href="status.jsp?id=<%=w.getId()%>&dpage=<%=dpage%>&status=1&column=<%=columnID%>">已禁止显示</a><%}else{%><a href="status.jsp?id=<%=w.getId()%>&dpage=<%=dpage%>&status=2&column=<%=columnID%>">显示</a><%}%></td>
       </tr>
    <%}%>
</table>

    <% if(zongpage>1)
  {

%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" align="center" valign="bottom" class="blues"><a href="?dpage=1&column=<%=columnID%>" class="lian1">第一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=ipage-1%>&column=<%=columnID%>" class="lian1" >上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=ipage+1%>&column=<%=columnID%>" class="lian1"  >下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="?dpage=<%=zongpage%>&column=<%=columnID%>" class="lian1"  > 最后一页</a></td>
  </tr>
</table>
     <%}%>


</BODY>
</html>
