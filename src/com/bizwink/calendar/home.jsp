<%@ page import="java.sql.*,
		 java.util.*,
		 java.text.*,
		 java.io.*,
		 java.net.URLEncoder,
                 com.etodaysoft.security.*,
		 com.etodaysoft.sales.*,
		 com.etodaysoft.resource.*,
		 com.etodaysoft.server.*,
		 com.etodaysoft.service.*,
		 com.etodaysoft.calendar.*,
		 com.etodaysoft.util.*" %>

<%
    String y = ParamUtil.getParameter(request,"y");
    String d = ParamUtil.getParameter(request,"d");
    String m = ParamUtil.getParameter(request, "m");
    int tasklist_mode = ParamUtil.getIntParameter(request,"tasklist_mode",0);
    EventManager eventManager = EventManagerImpl.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    StringBuffer url = new StringBuffer("");
    if((y != null) &&  d!= null)
    {
	cal.set(Calendar.YEAR,Integer.parseInt(y));
	cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(d));
	url.append(URLEncoder.encode("home.jsp?y=" + y + "&d=" + d + "&tasklist_mode="+ tasklist_mode));
    }
    else
    {
	if(y != null && m != null)
	{
	    cal.set(Calendar.YEAR,Integer.parseInt(y));
	    cal.set(Calendar.MONTH, Integer.parseInt(m));
	    url.append(URLEncoder.encode("home.jsp?y=" + y + "&m=" + m + "&tasklist_mode="+ tasklist_mode));
	}
	else
	{
	    url.append(URLEncoder.encode("home.jsp?tasklist_mode=" + tasklist_mode));
	}
    }

    Auth authToken = SessionUtil.getUserAuthorization(session);
    if (authToken == null) {
        response.sendRedirect("login.jsp?url="+url);
        return;
    }
    int ownerID = authToken.getUserID();

    Vector events = eventManager.getEventsByDate(ownerID, formatter.format(cal.getTime()).toString());
    //Vector events = eventManager.getEvents_Next7Days(ownerID);
    int eventCount = events.size();
    TaskManager taskManager = TaskManagerImpl.getInstance();
    Vector tasks = null;
    String modelist[] = {"","今天","明天","过期", "最近7天","本月","所有"};
    switch(tasklist_mode)
	{
	    case 0: // default: today
		    tasks = taskManager.getTasks_By_Date(ownerID,formatter.format(cal.getTime()));
		    break;
	    case 1: //today
		    tasks = taskManager.getTasks_Today(ownerID);
		    break;
	    case 2: //tomorrow
		    tasks = taskManager.getTasks_Tomorrow(ownerID, formatter.format(cal.getTime()));
		    break;
	    case 3: //overdue
		    tasks = taskManager.getTasks_OverDue(ownerID);
		    break;
	    case 4: //in next 7 days:
		    tasks = taskManager.getTask_Next7Days(ownerID, formatter.format(cal.getTime()));
		    break;
	    case 5: //this month:
		    tasks = taskManager.getTasks_ThisMonth(ownerID);
		    break;
	    case 6: //AllOpen:
		    tasks = taskManager.getTasks_AllOpen(ownerID);
		    break;
	    default:
		    tasks = taskManager.getTasks_By_Date(ownerID,formatter.format(cal.getTime()));
	}
    int taskCount = tasks.size();
%>

<html>
<head><title>BizWink Sales 首页</title>
<meta content="text/html; charset=gb2312" http-equiv=content-type>
<link href="style/global.css" rel=stylesheet type=text/css>
<link href="style/ie_global.css" rel=stylesheet type=text/css>
<script language=JavaScript1.2 src="js/functions.js"></script>
</head>
<body aLink=#99cc00 bgColor=#ffffff leftMargin=0 link=#000000 topMargin=0 vLink=#000000 MARGINWIDTH="0" MARGINHEIGHT="0">
<table border=0 cellpadding=0 cellspacing=0 width="100%">
  <tbody>
  <%@ include file="inc/home_head.jsp" %>
  <tr>
    <td height=25 class=selectTab colspan=2>
    <table cellspacing=3 cellpadding=0 border=0>
    <tr>
      <td>
      <table border=0 cellPadding=0 cellSpacing=3>
        <tbody>
        <tr>
          <td><a class=subNavBlack href="newLead.jsp?url=leads.jsp">新建线索</a></td>
          <td class=subNavBlack>|</td>
          <td><a class=subNavBlack href="newAccount.jsp?url=contacts.jsp">新建客户</a></td>
          <td class=subNavBlack>|</td>
          <td><a class=subNavBlack href="newContact.jsp?url=contacts.jsp">新建联系人</a></td>
          <td class=subNavBlack>|</td>
          <td><a class=subNavBlack href="newOpportunity.jsp?url=contacts.jsp">新建机会</a></td>
          <td class=subNavBlack>|</td>
          <td><a class=subNavBlack href="newForecast.jsp?url=contacts.jsp">新建预测</a></td>
          <td class=subNavBlack>|</td>
          <td><a class=subNavBlack href="newTask.jsp?url=contacts.jsp">新建任务</a></td>
          <td class=subNavBlack>|</td>
          <td><A class=subNavBlack href="trashes.jsp?url=contacts.jsp">垃圾箱</a></td>
        </tr>
	</tbody>
      </table>
      </td>
    </tr>
    </table>
    </td>
  </tr>
  </tbody>
</table>

<table border=0 cellpadding=0 cellspacing=0>
  <tbody>
  <tr>
    <td colspan=7><img height=5 src="images/blank.gif"></td></tr>
  <tr>
    <td rowspan=2 width=10><img src="images/blank.gif" width=10></td>
    <td><img src="images/blank.gif"></td>
    <td rowspan=2 width=10><img src="images/blank.gif" width=10></td>
    <td bgcolor=#cccccc rowspan=2 width=1><img src="images/blank.gif"></td>
    <td rowspan=2 width=10><img src="images/blank.gif" width=10></td>
    <td><img src="images/blank.gif"></td>
    <td rowspan=2 width=10><img src="images/blank.gif" width=10></td></tr>
  <tr>
    <td valign=top width=160>
    <%@ include file="inc/left.jsp" %>
    </td>
    <td vAlign=top width="100%">
    <table border=0 cellpadding=0 cell=cellspacing=0 width="100%">
        <tbody>
        <tr>
          <td colspan=3 valign=bottom><span
          class=moduleTitle>效率手册</span></td>
        </tr>
        <tr>
          <td class=moduleLine colspan=3><img height=2
            src="images/s.gif" width=1></td>
        </tr>
        <tr>
          <td colspan=3><img height=5 src="images/s.gif"
            width=1></td>
        </tr>
        <tr>
          <td align=left valign=top>
            <table border=0 cellpadding=0 cellspacing=0>
              <tbody>
              <tr>
                <td class=bodyBold>今天  <%=formatter.format(cal.getTime()).toString()%></td>
              </tr>
	      <tr><td>   </td></tr>
          <% if(eventCount == 0)
		    { %>
		      <tr><td>您今天内没有事件.</td></tr>
	      <%
		    }
		    else
		    {
			for(int i = 0; i < eventCount; i++)
			{
			    int eventID = ((EventImpl)events.elementAt(i)).getID();
			    Event e = (EventImpl)eventManager.getEvent(eventID);
			    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

			    SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
			    cal.setTime(e.getDuedate());
			    int weekday = cal.get(Calendar.DAY_OF_WEEK) ;
			    String week[] = {"日","一","二","三","四","五","六"};
			    StringBuffer duration = new StringBuffer(formatter.format(e.getDuedate()) + " 星期" + week[weekday-1] + " " + sdf1.format(e.getDuedate()));
			    if(e.getDurationHour() > 0)
			    {
				cal.add(Calendar.HOUR_OF_DAY,e.getDurationHour());
			    }
			    if(e.getDurationMinute() > 0)
			    {
				cal.add(Calendar.MINUTE,e.getDurationMinute());
			    }
			    if(e.getDurationHour() > 0 || e.getDurationMinute() > 0)
			    {
				duration.append(" - ").append(sdf1.format(cal.getTime()));
			    }
			    %>
			<TR><TD class=bodyBold>  <%=duration %></TD></tr>
			<tr><td>

			<A href='viewEvent.jsp?id=<%=e.getID()%>'>
				<%=e.getSubject()%>
				<% if(e.getLocation() != null && e.getLocation().length() > 0)
				{ %>
				   ( <%=e.getLocation()%> )
			    <%  }


			    %> </a><A
                  href="viewContact.jsp?id=<%=e.getContactID()%>&url=<%=url%>">
		  <%=e.getContactName()%></A></TD></TR>
		    <%  }
		    }

	      %>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td class=bodySmallBold><a href="newEvent.jsp?url=home.jsp">
		  新事件&gt;&gt;</a>&nbsp;</td>
              </tr>
              </tbody>
            </table>
          </td>

                    <td align=right valign=top>
            <table border=0 cellpadding=0 cellspacing=0>
              <tbody>
              <tr>
                <td>
                  <table border=0 cellpadding=2 cellspacing=0 width="100%">
                    <tbody>
                    <tr class=calMotif>
                      <td colspan=7>


<jsp:useBean id="mySmartCalendar" scope="page" class="com.etodaysoft.calendar.SmartCalendarImpl" />
<%

	mySmartCalendar.initialize(pageContext);

	//mySmartCalendar.setJSEventHandler("opener.myFunction1");
	//mySmartCalendar.setDateIn(request.getParameter("Date"));

	//mySmartCalendar.setLanguage("en","");


//	*********************
//	Display the calendar
//	*********************
	mySmartCalendar.showCalendar("show","");

%>

</td></tr>
             </tbody>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan=3>&nbsp;</td>
        </tr>
        </tbody>
      </table>
    </TD>
  </TR></TBODY></TABLE>
</table>

<TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
        <TBODY>
        <TR>
	  <TD align=left class=moduleTitle width="18%">任务&nbsp;<A
		href="newTask.jsp?url=<%=url%>">新建任务</A></TD>
	  <TD align=right width="82%">
	   <FORM action=home.jsp method=get name=hotlist>
		  <INPUT name=d type=hidden value=<%=cal.get(Calendar.DAY_OF_YEAR)%>>
		  <INPUT name=y type=hidden value=<%=cal.get(Calendar.YEAR)%>>
	    <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
		  <TBODY>
		  <TR>
		    <TD align=right noWrap>&nbsp;<SPAN class=bodySmall>选择:</SPAN>&nbsp;
		    <SELECT name=tasklist_mode  onchange=javascript:submit();>
		      <%
			   for(int i = 1 ; i < modelist.length; i++)
			   {
			    if(tasklist_mode == i)
				{
				    %><OPTION value=<%=i%> selected><%=modelist[i]%></Option><%
				}
			    else
			    {
	    	    %>
			    <OPTION value=<%=i%>><%=modelist[i]%></Option>
			    <%
			    }
			   }
			%>
			</SELECT></TD> </TR></TBODY></TABLE>
	    </FORM></TD>
	  </TR>
	    <% if(taskCount == 0)
	        { %>
		<TR><TD>您今天没有任何任务.</TD></TR>
	    <%  }
	    else
	    {
	    %>
        <TR>
          <TD colSpan=2>
            <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
              <TBODY>
              <TR>
                <TD>
                  <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
                    <TBODY>
                    <TR class=calMotif height=20>
                      <TD align=middle class=columnHeadInactiveWhite noWrap width=60>状态</TD>
                      <TD class=columnHeadInactiveWhite noWrap>&nbsp;日期&nbsp;</TD>
                      <TD class=columnHeadInactiveWhite noWrap>&nbsp;主题&nbsp;</TD>
                      <TD class=columnHeadInactiveWhite noWrap>&nbsp;优先级&nbsp;</TD>
                      </TR>
		      <%
		        for(int i = 0; i < taskCount; i++)
			{
			   Task t = (TaskImpl) tasks.elementAt(i);
			   %>
			      <TR bgColor=#eeeeee height=20>
			      <TD align=middle noWrap vAlign=center width=60>&nbsp;
			     <A class=actionLink href="editTask.jsp?id=<%=t.getID()%>&url=<%=url%>"><%=t.getStatus()%></A>&nbsp;</TD>
			      <TD noWrap><%=t.getDuedate()%></TD>
			      <TD>&nbsp;<A href="editTask.jsp?id=<%=t.getID()%>&url=<%=url%>"><%=t.getSubject()%></A>&nbsp;</TD>
			      <TD><%=t.getPriority()%></TD>
			      </TR>
			<%
			}
		      %>


                    </TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR>
		<%
	        }%>
       </TBODY></TABLE>
</BODY></HTML>
