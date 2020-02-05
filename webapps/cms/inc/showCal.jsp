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
<table width="100%" border="0" cellspacing="1" cellpadding="0">
    <tr align="center">
        <td bgcolor="#FFAD0C">
	    <a href="/sinopec/index.jsp?y=<%=smartDate.getYear(dateSelected)%>&m=<%=smartDate.getMonth(dateSelected) - 1%>"><img src="/sinopec/images/parrow.gif" width="18" height="20" align="absmiddle" border=0 alt="上个月"></a>
	    <%=smartDate.formatDate(dateSelected,"yyyy")%>年<%=smartDate.formatDate(dateSelected,"MM")%>月
	    <a href="/sinopec/index.jsp?y=<%=smartDate.getYear(dateSelected)%>&m=<%=smartDate.getMonth(dateSelected) + 1%>"><img src="/sinopec/images/narrow.gif" width="18" height="20" align="absmiddle" border=0 alt="下个月"></a>
	</td>
    </tr>
    <tr align="center" bgcolor="#000000"><td height="5"></td></tr>
    <tr align="center">
	<td>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#E6E6E6">
		<tr align="center">
		    <%
			for(int i = 0; i < jours.length; i++)
			{
			    %>
		    <TD><%=jours[(i + jourOffset) % 7]%></TD>
			<%
			}
		    %>
		</tr>
	    </table>
        </td>
    </tr>
    <tr align="center">
	<td bgcolor="#E6E6E6">
	    <table width="100%" border="0" cellspacing="1" cellpadding="0">
		<tr align="center" bgcolor="#FFFFFF">
		    <%
		    if(jourDebut <= jourOffset)
		    {
			jourDebut += 7;
		    }
		    jourDebut -= jourOffset;
		    for(int i = 1; i < jourDebut; i++) //print those empty TDs of the first week before the first day of the month
		    {

			%>
		    <TD>&nbsp;</TD>
			<%
		    }
		    for(int i = 0; i < dateDiff ; i++)
		    {
			int i_day = smartDate.getDay(dateDebut);
			i_dayOfYear = smartDate.getDayOfYear(dateDebut);
			i_month = smartDate.getMonth(dateDebut);
			i_year = smartDate.getYear(dateDebut);
			num = smartDate.getWeekDay(dateDebut);
			 %>
		      <TD>
			    <%
			//if it's selected: highlight
			if(smartDate.getDay(dateDebut) == smartDate.getDay(dateSelected) && smartDate.getMonth(dateDebut) == smartDate.getMonth(dateSelected) && smartDate.getYear(dateDebut) == smartDate.getYear(dateSelected))
			{
			    %>
			        <b>
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
			    <a href='search.jsp?y=<%=i_year%>&d=<%=i_dayOfYear%>'><%=i_day%></a>
			    <%
			}
			if(smartDate.getDay(dateDebut) == smartDate.getDay(dateSelected) && smartDate.getMonth(dateDebut) == smartDate.getMonth(dateSelected) && smartDate.getYear(dateDebut) == smartDate.getYear(dateSelected))
			{
			    %>
			        </b>
			    <%
			}
			%>
		       </TD>
			<%
			if(num == Calendar.SATURDAY)
			{
			    num = 0;
			    %>
		<TR >

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
		    <TD>&nbsp;</TD>
			    <%
			}
		    }
		    %>
		</tr>
	    </table>
	</td>
    </tr>
    <tr align="center" bgcolor="#000000">
	<td height="5"></td>
    </tr>
    <tr align="center" bgcolor="#FFAD0C">
	<td>
	    <A  href='/sinopec/schedule.jsp?y=<%=smartDate.getYear(dateSelected)%>&amp;d=<%=smartDate.getDayOfYear(dateSelected)%>'><img src="/sinopec/images/point-day.gif" width="14" height="14" align="absmiddle" alt="本日" border=0></A>
            <A href='/sinopec/schedule.jsp?y=<%=smartDate.getYear(dateSelected)%>&amp;wk=<%=smartDate.getWeek(dateSelected)%>&amp;tasklist_mode=4'><img src="images/point-week.gif" width="14" height="14" align="absmiddle" alt="本周" border=0></A>
	    <A href='/sinopec/schedule.jsp?y=<%=smartDate.getYear(dateSelected)%>&amp;m=<%=smartDate.getMonth(dateSelected)%>&amp;tasklist_mode=5'><img src="images/point-month.gif" width="14" height="14" align="absmiddle" alt="本月" border=0></a>
            <A href='/sinopec/actlist.jsp'><img src="images/point-all.gif" width="14" height="14" align="absmiddle" alt="全部列表" border=0></a>
	</td>
    </tr>
</table>
