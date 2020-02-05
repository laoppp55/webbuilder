<table border=0 cellpadding=0 cellspacing=0 bgcolor="#006699">
  <tbody>
    <tr>
	<td>
	    <table border=0 cellpadding=2 cellspacing=0 width="100%">
		<tbody>
		    <tr class=calMotif>
			<td colspan=7>
<%
	int year = (ParamUtil.getParameter(request,"y") == null)? 0: Integer.parseInt(ParamUtil.getParameter(request,"y"));
	int day = (ParamUtil.getParameter(request,"d") == null)? 0: Integer.parseInt(ParamUtil.getParameter(request,"d"));
	int month = (ParamUtil.getParameter(request,"m") == null)? (-1): Integer.parseInt(ParamUtil.getParameter(request,"m"));
	String mode = (ParamUtil.getParameter(request,"mode") == null)? null: ParamUtil.getParameter(request,"mode");
	String formName = (ParamUtil.getParameter(request,"form") == null)? null: ParamUtil.getParameter(request,"form");
	String di = (ParamUtil.getParameter(request,"di") == null)? null: ParamUtil.getParameter(request,"di"); // the input name of duedate in this form
	MyCalendar myCal = new MyCalendarImpl();
	myCal.setDay(day);
	myCal.setMonth(month);
	myCal.setYear(year);
	SmartDate smartDate = new SmartDateImpl();
	//show calendar:
	int jourOffset = 0;
	java.util.Date now = new java.util.Date();
	Calendar tmpCal = Calendar.getInstance();
	java.util.Date dateSelected = myCal.getRequestedDate();
	tmpCal.setTime(dateSelected);
        tmpCal.set(Calendar.DAY_OF_MONTH, 1); //set to be the first day of the selected month
	java.util.Date dateDebut = tmpCal.getTime(); // dateDebut is the first day of the month
	int jourDebut = tmpCal.get(Calendar.DAY_OF_WEEK); //the weekday of dateDebut
        int dateDiff = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH); //num of days in the selected month
	java.util.Date dateFin = smartDate.addDay(smartDate.addMonth(dateDebut, 1), -1L);//the last day of the month
	java.util.Date tmpDate = smartDate.addMonth(dateSelected, -1);
	tmpCal.set(Calendar.MONTH, smartDate.getMonth(tmpDate) -1 );
	int maxDaysLastMonth = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
	tmpCal.set(Calendar.DAY_OF_MONTH,maxDaysLastMonth);
	int sundayLastMonth = 0;
	int i_dayOfYear = 0;
	int i_year = 0;
	int i_month = 0;
	if(tmpCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
	{
	    sundayLastMonth = maxDaysLastMonth;
	}
	else
	{
	    sundayLastMonth = smartDate.getDay(smartDate.addDay(dateDebut, -(jourDebut - 1)));
	    tmpCal.set(Calendar.DAY_OF_MONTH,sundayLastMonth);
	    i_dayOfYear = tmpCal.get(Calendar.DAY_OF_YEAR);
	    i_year = tmpCal.get(Calendar.YEAR);
	    i_month = tmpCal.get(Calendar.MONTH) + 1;
	}
	int num = 0;
	String jours[] = {
                "日","一","二","三","四","五","六"
            };
	%>
	<TABLE border=0 cellPadding=0 cellSpacing=0><TBODY><TR><TD>
	    <table cellpadding='0' cellspacing='0' border='0'>
		<TR><TD>
		    <TABLE width='100%' border='0' cellspacing='0' cellpadding='2'>
			<TR>
			    <TD colspan='7'>
				<table width='100%' cellpadding='0' cellspacing='0' border='0'>
				    <TR class=#cccccc>
					<td align='right'><a href="/sales/home.jsp?y=<%=smartDate.getYear(dateSelected)%>&m=<%=smartDate.getMonth(dateSelected) - 1%>"><img src='/sales/images/parrow.gif' border=0 alt="上个月"></a></td>;
					<TD align=middle class=calTitle><%=smartDate.formatDate(dateSelected,"yyyy")%>年<%=smartDate.formatDate(dateSelected,"MM")%>月</TD>
					<td align='right'><a href="/sales/home.jsp?y=<%=smartDate.getYear(dateSelected)%>&m=<%=smartDate.getMonth(dateSelected) + 1%>"><img src='/sales/images/narrow.gif' border=0 alt="下个月"></a></td>;
				    </TR>
				</table>
			    </TD>
			</TR>
		        <TR bgColor=#006699>
			<%
			    for(int i = 0; i < jours.length; i++)
			    {
				%>
				<TD align=right class=calDays><%=jours[(i + jourOffset) % 7]%></TD>
			    <%
			    }
			%>
			</TR>
			<%
			    if(smartDate.getWeek(dateDebut) == smartDate.getWeek(now))   //if it's this week:
			    {
				%>
			<TR bgColor=#cccccc>
				<%
			    }
			    else
			    {
				%>
			<TR>
				<%
			    }
			    if(jourDebut <= jourOffset)
			    {
				jourDebut += 7;
			    }
			    jourDebut -= jourOffset;
			    for(int i = 1; i < jourDebut; i++) //print those empty TDs of the first week before the first day of the month
			    {

				%>
			    <TD align=right class=calDays><FONT size=2>&nbsp;</FONT></TD>
				<%
			    }
			    for(int i = 0; i < dateDiff ; i++)
			    {
				int i_day = smartDate.getDay(dateDebut);
				i_dayOfYear = smartDate.getDayOfYear(dateDebut);
				i_month = smartDate.getMonth(dateDebut);
				i_year = smartDate.getYear(dateDebut);
				num = smartDate.getWeekDay(dateDebut);

				//if it's selected: highlight
				if(smartDate.getDay(dateDebut) == smartDate.getDay(dateSelected) && smartDate.getMonth(dateDebut) == smartDate.getMonth(dateSelected) && smartDate.getYear(dateDebut) == smartDate.getYear(dateSelected))
				{
				    %>
			      <TD bgcolor=LightCyan valign='center' align='middle'><FONT SIZE=1 COLOR=#888888 FACE=arial class=calToday >
				    <%
				}
				else
				{
				    %>
			      <TD bgcolor=LightGrey align='middle' align='center'><FONT SIZE=1 COLOR=#128DB9 FACE=arial>
				    <%
				}
				if(mode != null && (mode.equals("pick") && (formName != null && di != null)))
				{
				    %>
				    <a href=\"javascript: top.window.opener.pick('<%=formName %>','<%=di%>','<%=formatter.format(dateDebut)%> ');" class=calActive><%=i_day%></a>
				    <%
				}
				else
				{
				    %>
				    <a href='/sales/schedule.jsp?y=<%=i_year%>&d=<%=i_dayOfYear%>'><%=i_day%></a>
				    <%
				}
				%>
				    </FONT>
			       </TD>
				<%
				if(num == Calendar.SATURDAY)
				{
				    num = 0;
				    if(smartDate.getWeek(dateDebut) == smartDate.getWeek(now))   //if it's this week:
				    {
				    %>
		        <TR bgColor=#cccccc>
				    <%
				    }
				    else
				    {
					%>
			<TR>
					<%
				    }
				    %>
			</TR>
			<TR>
				    <%
				}
				else
				{
				    num++;
				}
			     dateDebut = smartDate.addDay(dateDebut,1L);
			    }
			    num = smartDate.getWeekDay(dateDebut);
			    if(num > Calendar.SUNDAY)
			    {
				for(int i = num; i <= Calendar.SATURDAY;i++)
				{
				    %>
			    <TD bgcolor='Azure'><FONT size=2>&nbsp;</FONT></TD>
				    <%
				}
			    }
			%>
			</TR>
		    </TABLE>
		</TD>
	    </TR>
	    <TR>
	        <TD align=middle>
		    <A  href='https://ssl.salesforce.com/sales/schedule.jsp?yr=2001&amp;day=87'><IMG alt='Day View' border=0 height=14 src='/sales/images/dayview_icon.gif' width=14></A>&nbsp
		    <A href='https://ssl.salesforce.com/sales/schedule.jsp?yr=2001&amp;wk=13'><IMG alt='Week View' border=0 height=14 src='/sales/images/weekview_icon.gif' width=14></A>&nbsp
		    <A href='https://ssl.salesforce.com/sales/schedule.jsp?yr=2001&amp;mo=2'><IMG alt='Month View' border=0 height=14 src='/sales/images/monthview_icon.gif' width=14></A>&nbsp
		    <A href='https://ssl.salesforce.com/sales/actlist.jsp'><IMG alt='List View' border=0 height=14 src='/sales/images/listview_icon.gif'width=14></A>
		</TD>
	    </TR>
	</TBODY>
    </TABLE>


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