package com.bizwink.calendar;

/**
 * Title:        sales
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Michelle
 * @version 1.0
 */
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import com.bizwink.cms.util.StringUtil;


import javax.servlet.http.HttpServletResponse;

public class SmartCalendarImpl implements SmartCalendar{

    //Fields:
    private ServletConfig m_config;
    private ServletContext m_application;
    private HttpServletRequest m_request;
    private HttpServletResponse m_response;
    private HttpSession m_session;
    private PrintWriter m_outServlet;
    private JspWriter m_outJsp;
    private PageContext m_pageContext;
    private Date m_datINPUT;
    private int m_initialize;
    private SmartDateImpl smartDate;
    private String m_strNOM_FONC;
    private Locale currentLocale;
    private Locale currentColor;
    private ResourceBundle colors;
    private ResourceBundle labels;
    private boolean weekend[] = {
            false, false, false, false, false, false, false
    };
    private int jourOffset;
    public SmartCalendarImpl() {
        currentLocale = new Locale(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
        currentColor = new Locale("", "");
        smartDate = new SmartDateImpl();
    }

    public void initialize(PageContext pageContext) throws IOException
    {
        m_initialize = 2;
        m_pageContext = pageContext;
        m_application = m_pageContext.getServletContext();
        m_session = pageContext.getSession();
        m_request = (HttpServletRequest)pageContext.getRequest();
        m_response = (HttpServletResponse)pageContext.getResponse();
        m_outJsp = pageContext.getOut();
        Calendar rightNow = Calendar.getInstance();
    }

    public final void initialize(ServletConfig config, HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        m_initialize = 1;
        m_config = config;
        m_application = config.getServletContext();
        m_outServlet = m_response.getWriter();
        m_request = request;
        m_response = response;
        Calendar rightNow = Calendar.getInstance();
    }


    public final void initialize(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response, JspWriter out)
            throws IOException
    {
        m_initialize = 3;
        m_application = application;
        m_session = session;
        m_request = request;
        m_response = response;
        m_outJsp = out;
        Calendar rightNow = Calendar.getInstance();
    }


    public void setJSEventHandler(String strValue)
    {
        m_strNOM_FONC = strValue;
    }

    public void setDateIn(String strDateInput)
    {
        m_datINPUT = smartDate.strToDate(strDateInput, "dd/MM/yyyy");
    }

    public void responseWrite(String str)
            throws IOException
    {
        if(m_initialize == 1)
            m_outServlet.print(str);
        else
            m_outJsp.print(str);
    }

    public void responseWriteLn(String str)
            throws IOException
    {
        if(m_initialize == 1)
            m_outServlet.println(str);
        else
            m_outJsp.println(str);
    }

    private Date getRequestedDate()
    {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        int y = 0;
        int d = 0;
        int m = 0;
        int i = 0; // 1: day of month 2: day of year
        //get requested year:
        if(m_request.getParameter("y") == null || m_request.getParameter("y") == "")
        {
            y = smartDate.getYear(now);
        }
        else
        {
            y = Integer.valueOf(m_request.getParameter("y")).intValue();
        }
        //get requested day:
        if(m_request.getParameter("d") == null ||  m_request.getParameter("d") == "")
        {
            if(m_request.getParameter("m") == null || m_request.getParameter("m") == "")
            {
                d = smartDate.getDayOfYear(now);
                i = 2;
            }
            else
            {
                d = smartDate.getDay(now);
                i = 1;
            }
        }
        else
        {
            d = Integer.valueOf(m_request.getParameter("d")).intValue();
            i = 2;
        }
        //get requested month:
        if(m_request.getParameter("m") == null || m_request.getParameter("m") == "")
        {
            //m = smartDate.getMonth(now);
            cal.set(Calendar.YEAR,y);
            if(i == 2)
            {
                cal.set(Calendar.DAY_OF_YEAR,d);
            }
            else
            {
                cal.set(Calendar.MONTH,smartDate.getMonth(now));
                cal.set(Calendar.DAY_OF_MONTH,d);
            }
            return cal.getTime();
        }
        else
        {
            m = Integer.valueOf(m_request.getParameter("m")).intValue();
            if(m == 13) //that's the next year
            {
                m = 1;
                y+=1;
            }
            if(m == 0)
            {
                m = 12;
                y-=1;
            }
            d = smartDate.getDay(now);
            cal.set(Calendar.YEAR,y);
            cal.set(Calendar.MONTH,m - 1);
            Calendar tmpCal = Calendar.getInstance();
            tmpCal.set(Calendar.YEAR,y);
            tmpCal.set(Calendar.MONTH,m - 1);
            tmpCal.set(Calendar.DAY_OF_MONTH,1);
//		System.out.println(tmpCal.getTime().toString());
            if(tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH) < d)
            {
                d = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            cal.set(Calendar.DAY_OF_MONTH,d);
            return cal.getTime();
        }
    }

    public void showCalendar(String mode, String formName) throws IOException
    {
        jourOffset = 0;
        Date now = new Date();
        Calendar tmpCal = Calendar.getInstance();
        Date dateSelected = getRequestedDate();
        tmpCal.setTime(dateSelected);
        tmpCal.set(Calendar.DAY_OF_MONTH, 1); //set to be the first day of the selected month
        Date dateDebut = tmpCal.getTime(); // dateDebut is the first day of the month
        int jourDebut = tmpCal.get(Calendar.DAY_OF_WEEK); //the weekday of dateDebut
        int dateDiff = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH); //num of days in the selected month
        Date dateFin = smartDate.addDay(smartDate.addMonth(dateDebut, 1), -1L);//the last day of the month

        Date tmpDate = smartDate.addMonth(dateSelected, -1);
        tmpCal.set(Calendar.MONTH, smartDate.getMonth(tmpDate) -1 );
        int maxDaysLastMonth = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // find out if the last day last month is a saturday
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
        //System.out.println(dateSelected.toString());
        String Mois = String.valueOf(new StringBuffer(smartDate.formatDate(dateSelected,"yyyy")).append(StringUtil.gb2iso("年")).append(smartDate.formatDate(dateSelected,"MM")).append(StringUtil.gb2iso("月")));
        responseWriteLn("<TABLE border=0 cellPadding=0 cellSpacing=0><TBODY><TR><TD>");
        responseWriteLn("<table cellpadding='0' cellspacing='0' border='0'>");
        responseWriteLn("<TR><TD>");
        responseWriteLn("<TABLE width='100%' border='0' cellspacing='0' cellpadding='2'>");
        responseWriteLn("<TR>");//the first row(titles)
        responseWriteLn("<TD colspan='7'>");
        responseWriteLn("<table width='100%' cellpadding='0' cellspacing='0' border='0'>");
        responseWriteLn("<TR class=#cccccc>");
        responseWriteLn(String.valueOf(new StringBuffer("<td align='right'><a href='/sales/schedule.jsp?y=").append(smartDate.getYear(dateSelected)).append("&m=").append(smartDate.getMonth(dateSelected) - 1).append("'><img src='/sales/images/parrow.gif' border=0 alt='").append(StringUtil.gb2iso("上个月")).append("'></a></td>")));
        responseWriteLn("<TD align=middle class=calTitle>");
        responseWriteLn(Mois);
        responseWriteLn("</TD>");
        responseWriteLn(String.valueOf(new StringBuffer("<td align='right'><a href='/sales/schedule.jsp?y=").append(smartDate.getYear(dateSelected)).append("&m=").append(smartDate.getMonth(dateSelected) + 1).append("'><img src='/sales/images/narrow.gif' border=0 alt='").append(StringUtil.gb2iso("下个月")).append("'></a></td>")));
        responseWriteLn("</TR>");
        responseWriteLn("</TABLE>");
        responseWriteLn("</TD>");
        responseWriteLn("</TR>");//end of the first row
        responseWriteLn("<TR bgColor=#006699>");//begin the second row: weekdays
        String jours[] = {
                StringUtil.gb2iso("日"), StringUtil.gb2iso("一"), StringUtil.gb2iso("二"), StringUtil.gb2iso("三"), StringUtil.gb2iso("四"), StringUtil.gb2iso("五"), StringUtil.gb2iso("六")
        };
        for(int i = 0; i < jours.length; i++)
        {
            responseWriteLn(String.valueOf(new StringBuffer("<TD align=right class=calDays>").append(jours[(i + jourOffset) % 7]).append("</FONT></TD>")));
        }
        responseWriteLn("</TR>");//end of the second row
        //begin to show the real days in this month:
        if(smartDate.getWeek(dateDebut) == smartDate.getWeek(now))   //if it's this week:
        {
            responseWrite("<TR bgColor=#cccccc>");
        }
        else
        {
            responseWrite("<TR>");
        }
        //writing the blank days of this week before this month:

        if(jourDebut <= jourOffset)
            jourDebut += 7;
        jourDebut -= jourOffset;
        for(int i = 1; i < jourDebut; i++) //print those empty TDs of the first week before the first day of the month
            if(weekend[((i - 1) + jourOffset) % 7])
                responseWrite(String.valueOf((new StringBuffer("<TD align=right class=calDays><FONT size=2>&nbsp;</FONT></TD>"))));
            else
                responseWrite("<TD bgcolor='Azure'><FONT size=2>&nbsp;</FONT></TD>");

        for(int i = 0; i < dateDiff ; i++)
        {
            int i_day = smartDate.getDay(dateDebut);
            i_dayOfYear = smartDate.getDayOfYear(dateDebut);
            i_month = smartDate.getMonth(dateDebut);
            i_year = smartDate.getYear(dateDebut);
            num = smartDate.getWeekDay(dateDebut);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //if it's selected: highlight
            if(smartDate.getDay(dateDebut) == smartDate.getDay(dateSelected) && smartDate.getMonth(dateDebut) == smartDate.getMonth(dateSelected) && smartDate.getYear(dateDebut) == smartDate.getYear(dateSelected))
            {
                responseWrite("<TD bgcolor=LightCyan valign='center' align='middle'><FONT SIZE=1 COLOR=#888888 FACE=arial class=calToday >");
            }
            else
            {
                responseWrite("<TD bgcolor=LightGrey align='middle' align='center'><FONT SIZE=1 COLOR=#128DB9 FACE=arial>");
            }
            if(mode.equals("pick"))
            {
                if(formName.equals("opportunity"))
                {
                    responseWrite(String.valueOf(new StringBuffer("<a href=\"javascript: top.window.opener.pick('"+ formName + "','closedate','" + formatter.format(dateDebut) +"');\" class=calActive>").append(String.valueOf(i_day)).append("</a>")));
                }
                else if(formName.equals("event"))
                {
                    responseWrite(String.valueOf(new StringBuffer("<a href=\"javascript: top.window.opener.pick('"+ formName + "','date','" + formatter.format(dateDebut) +"');\" class=calActive>").append(String.valueOf(i_day)).append("</a>")));
                }
                else
                {
                    responseWrite(String.valueOf(new StringBuffer("<a href=\"javascript: top.window.opener.pick('"+ formName + "','duedate','" + formatter.format(dateDebut) +"');\" class=calActive>").append(String.valueOf(i_day)).append("</a>")));
                }
            }
            else
            {
                responseWrite(String.valueOf(new StringBuffer("<a href='/sales/schedule.jsp?y=").append(String.valueOf(i_year)).append("&d=").append(String.valueOf(i_dayOfYear)).append("'>").append(String.valueOf(i_day)).append("</a>")));
            }
            responseWrite("</FONT></TD>\n");
            if(num == Calendar.SATURDAY)
            {
                num = 0;
                if(smartDate.getWeek(dateDebut) == smartDate.getWeek(now))   //if it's this week:
                {
                    responseWrite("<TR bgColor=#cccccc>");
                }
                else
                {
                    responseWrite("<TR>");
                }
                responseWriteLn("</TR>");
                responseWriteLn("<TR>");
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
                responseWriteLn("<TD bgcolor='Azure'><FONT size=2>&nbsp;</FONT></TD>");
            }
        }
//	System.out.println(num);

        responseWriteLn("<TD>");
        //responseWriteLn(String.valueOf(y));
        responseWriteLn("</TD>");
        responseWriteLn("</TR>");
        responseWriteLn("</TABLE>");
        responseWriteLn("</TD></TR>\n");
        responseWriteLn("</TBODY></TABLE>");
    }

}