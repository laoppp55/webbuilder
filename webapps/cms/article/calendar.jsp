<%@ page import = "java.sql.*,
                   java.util.*,
                   java.text.*,
                   com.bizwink.cms.util.*,
                   com.bizwink.calendar.*" contentType="text/html;charset=utf-8"%>
<%
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String d = ParamUtil.getParameter(request, "d",true);
    String ip = ParamUtil.getParameter(request, "ip",true);
    String formName = ParamUtil.getParameter(request, "form",true);

    java.util.Date dateSelected = new java.util.Date();
    SmartDate smartDate = new SmartDateImpl();

    String WEEK[] = { "日","一","二","三","四","五","六"};

    java.util.Date dateDebut = new java.util.Date();

    try
    {
         if(d != null && !d.equals("")){
             dateSelected = formatter.parse(d);
         }
    }catch(Exception e){
         e.printStackTrace();
    }

    dateDebut = smartDate.addMonth(dateSelected,-1);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet HREF="../style/global.css" style="text/css">
<script language=JavaScript1.2 src="../js/public.js"></script>
<script language=JavaScript1.2 src="../js/functions.js"></script>
</head>
<table width="100%" border="0" cellspacing="1" cellpadding="0">
    <tr align="center">
	<td bgcolor="#FFAD0C">
            <a href="calendar.jsp?d=<%=formatter.format(dateDebut)%>&form=<%=formName%>&ip=<%=ip%>"><img src="../images/previous_arrow.gif" width="15" height="18" align="absmiddle" border=0 alt="上个月"></a>
	    <%=smartDate.getYear(dateSelected)%>年<%=smartDate.getMonth(dateSelected)%>月
	    <% dateDebut = smartDate.addMonth(dateSelected, 1); %>
	    <a href="calendar.jsp?d=<%=formatter.format(dateDebut)%>&form=<%=formName%>&ip=<%=ip%>"><img src="../images/next_arrow.gif" width="15" height="18" align="absmiddle" border=0 alt="下个月"></a>
        </td>
    </tr>
    <tr align="center" bgcolor="#000000">
	<td height="5"></td>
    </tr>
    <tr align="center">
	<td>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#E6E6E6">
		<tr align="center">
		    <%
			for(int i = 0; i < WEEK.length; i++)
			{
			    %>
		    <TD><%=WEEK[i]%></TD>
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
		    Calendar tmpCal = Calendar.getInstance();
		    tmpCal.setTime(dateSelected);
		    tmpCal.set(Calendar.DAY_OF_MONTH, 1); //set to be the first day of the selected month
		    dateDebut = tmpCal.getTime(); // dateDebut is the first day of the month
		    int jourDebut = tmpCal.get(Calendar.DAY_OF_WEEK); //the weekday of dateDebut
		    int dateDiff = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH); //num of days in the selected month
		    java.util.Date dateFin = smartDate.addDay(smartDate.addMonth(dateDebut, 1), -1L);//the last date of the month
		    java.util.Date tmpDate = smartDate.addMonth(dateSelected, -1);
		    tmpCal.set(Calendar.MONTH, smartDate.getMonth(tmpDate) -1 ); //last date of last month
		    int maxDaysLastMonth = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		    tmpCal.set(Calendar.DAY_OF_MONTH,maxDaysLastMonth);
		    int jourOffset = 0;
		    int sundayLastMonth = 0;
		    int i_dayOfYear = 0;
		    int i_year = 0;
		    int i_month = 0;
		    int num = 0;
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
		      <TD align=center>
			    <%
			//if it's selected: highlight
			if(smartDate.getDay(dateDebut) == smartDate.getDay(dateSelected) && smartDate.getMonth(dateDebut) == smartDate.getMonth(dateSelected) && smartDate.getYear(dateDebut) == smartDate.getYear(dateSelected))
			{
			    %>
			        <b>
			    <%
			}

			if(formName != null && ip != null)
			{
			    %>
			    <a href="javascript:top.window.opener.pick('<%=formName %>','<%=ip%>','<%=formatter.format(dateDebut)%>');" ><%=i_day%></a>
			    <%
			}
			else
			{
			    %>
			    <%=i_day%>
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
</table>
</body>
</html>