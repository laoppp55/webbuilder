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
    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
    Calendar cal = Calendar.getInstance(); //today
    StringBuffer url = new StringBuffer("");
    if((y != null) &&  d!= null)
    {
	cal.set(Calendar.YEAR,Integer.parseInt(y));
	cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(d));
	url.append(URLEncoder.encode("schedule.jsp?y=" + y + "&d=" + d + "&tasklist_mode="+ tasklist_mode));
    }
     else
    {
	if(y != null && m != null )
	{
	    cal.set(Calendar.YEAR,Integer.parseInt(y));
	    cal.set(Calendar.MONTH, Integer.parseInt(m));
	    url.append(URLEncoder.encode("schedule.jsp?y=" + y + "&m=" + m + "&tasklist_mode="+ tasklist_mode));
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
    System.out.println("tasks: " + tasks.size());
    String week[] = {"日","一","二","三","四","五","六"};
%>

<html>
<head><title>当天计划<%=formatter.format(cal.getTime())%></title>
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
        <TBODY>
        <TR>
          <TD vAlign=top width="60%">
            <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
              <TBODY>
              <TR>
                <TD colSpan=5>
                  <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
                    <TBODY>
                    <TR>
                  <TD align=left height=20 vAlign=top width="20%"><b><font size="+1">效率手册</font></b></TD>
                  <TD align=right width="80%"><A
                        href="schedule.jsp?yr=2001&amp;day=92"><IMG
                        alt="Day View" border=0 height=14
                        src="images/dayview_icon.gif"
                        width=14></A>&nbsp;<A
                        href="schedule.jsp?yr=2001&amp;wk=14"><IMG
                        alt="Week View" border=0 height=14
                        src="images/weekview_icon.gif"
                        width=14></A>&nbsp;<A
                        href="schedule.jsp?yr=2001&amp;mo=3"><IMG
                        alt="Month View" border=0 height=14
                        src="images/monthview_icon.gif"
                        width=14></A>&nbsp;<A
                        href="actlist.jsp"><IMG
                        alt="List View" border=0 height=14
                        src="images/listview_icon.gif"
                        width=14></A></TD>
                </TR></TBODY></TABLE></TD></TR>
              <TR>

            <TD width="49%">
              <table border=0 cellpadding=0 cellspacing=0 width="100%">
                <tbody>
                <tr>
                  <td align=middle class=calMotif colspan=5 height=20><a
                        href="schedule.jsp?yr=2001&day=91"><img
                        align=absMiddle alt=Previous border=0 height=20
                        src="images/parrow.gif"
                        width=18></a>&nbsp;&nbsp;&nbsp;&nbsp;<span
                        class=bodyBoldWhite><%=formatter.format(cal.getTime()).toString()%>
                    ,星期<%=week[(cal.get(Calendar.DAY_OF_WEEK)-1)]%> </span>&nbsp;&nbsp;&nbsp;&nbsp;<a
                        href="schedule.jsp?yr=2001&day=93"><img
                        align=absMiddle alt=Next border=0 height=20
                        src="images/narrow.gif" width=18></a></td>
                </tr>
                <tr>
                  <td>
                    <table border=0 cellpadding=0 cellspacing=0
width="100%">
                      <tbody> <%
			        String bgColor[] = {"#eeeeee", "#dddddd"};
				for(int i = 6; i <= 23; i++)
				{
				     %>
                      <tr bgcolor=<%=bgColor[i % 2]%>>
                        <td align=right class=bodyBold height=20 noWrap valign=top>
                          <a href="newEvent.jsp"><%=i%>:00</a> </td>
                        <td>&nbsp;</td>
                        <td width=1><img border=0 height=1 src="images/s.gif" width=1></td>
                        <td>&nbsp;</td>
                        <%
				//display events:
				    for(int j = 0; j < eventCount; j++)
				    {
					int eventID = ((EventImpl)events.elementAt(j)).getID();
					Event event = (EventImpl)eventManager.getEvent(eventID);
					Calendar tmpCal = Calendar.getInstance();
					tmpCal.setTime(event.getDuedate());
					int h = tmpCal.get(Calendar.HOUR);
					if(h == i) //fit the hour period
					{
					    if(event.getSubject() != null)
					    {
						StringBuffer duration = new StringBuffer(sdf1.format(tmpCal.getTime()) + " - ");
						if(event.getDurationHour() > 0)
						{
						    tmpCal.add(Calendar.HOUR, event.getDurationHour());
						    if(event.getDurationMinute() > 0)
						    {
							tmpCal.add(Calendar.MINUTE, event.getDurationMinute());
						    }
						    duration.append(sdf1.format(tmpCal.getTime()));
						}
						%>
                        <td width="90%" ><span class=bodySmallBold><%=duration%></span><br>
                          <a href="viewEvent.jsp?id=<%=eventID%>"><%=event.getSubject()%></a>
                          (<%=event.getLocation()%>) : <a href="viewContact.jsp?id=<%=event.getContactID()%>"><%=event.getContactName()%></a><br>
                        </td>
                        <%
					    }
					    else
					    {
						%>
                        <td width="90%">&nbsp;</td>
                        <%
					    }
					}
				    }
				    %></tr>
                      <%
				}
			    %>
                      <tr>
                        <td class=blackLine colspan=5><img border=0 height=1
                        src="salesforce_com - Calendar --- Day View_files/s.gif"
                        width=1></td>
                      </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
                </tbody>
              </table>
            </TD>  <TD vAlign=top width="51%">
              <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
              <TBODY>
              <TR>
                <TD align=left class=moduleTitle>任务&nbsp;
		<A href="newTask.jsp"><font size=-2>新任务</font></A></TD>
                <TD align=right>
                  <FORM action=schedule.jsp method=get name=hotlist>
		  <INPUT name=d type=hidden value=<%=cal.get(Calendar.DAY_OF_YEAR)%>><INPUT name=y type=hidden value=<%=cal.get(Calendar.YEAR)%>>
                  <TABLE border=0 cellPadding=0 cellSpacing=0 width="100%">
                    <TBODY>
                    <TR>
                      <TD align=right noWrap>&nbsp;<SPAN
                        class=bodySmall>选择:</SPAN>&nbsp;
			<SELECT name=tasklist_mode onchange=javascript:submit();>
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

			  </SELECT></TD></TR></TBODY></TABLE></FORM></TD></TR>
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
			      <TD noWrap>
			      <% if(t.getDuedate() == null)
				{
				    %>(未知)<%
				}
				else
				{ %>
			      <%=formatter.format(t.getDuedate())%>
			    <%  } %>
			      </TD>
			      <TD>&nbsp;<A href="viewTask.jsp?id=<%=t.getID()%>&url=<%=url%>"><%=t.getSubject()%></A>&nbsp;</TD>
			      <TD><%=t.getPriority()%></TD>
			      </TR>
			<%
			}
		      %>

		</TBODY></TABLE></TD></TR>
              <TR>
                <TD>&nbsp;</TD></TR></TBODY></TABLE></TD></TR>
		<%
		}
		%></TBODY></TABLE>
</TD>
</TR>
</tbody>
  </table>
</TD></TR>
</TBODY>
</TABLE>
</td>
</tr>
</TBODY></TABLE>
    </BODY></HTML>
