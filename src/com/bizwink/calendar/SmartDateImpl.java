package com.bizwink.calendar;

/**
 * Title:        sales
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author xxb
 * @version 1.0
 */
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import java.io.PrintWriter;
import java.lang.String;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.lang.Exception;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class SmartDateImpl implements SmartDate
{
    public String formatDate(Date dDate, String sFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
        String dateString = formatter.format(dDate);
        return dateString;
    }

    public Date strToDate(String s, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date1;
        try
        {
            Date theDate = formatter.parse(s);
            Date date = theDate;
            return date;
        }
        catch(Exception ex)
        {
            date1 = null;
        }
        return date1;
    }

    public Date strToDate(String s)
    {
	Date date;
        try
        {
            DateFormat df = DateFormat.getDateInstance();
            Date theDate = df.parse(s);
            Date date1 = theDate;
            return date1;
        }
        catch(Exception ex)
        {
            date = null;
        }
        return date;
    }

    public Date addDay(Date dDate, long iNbDay)
    {

            Calendar cal = Calendar.getInstance();
            cal.setTime(dDate);
            cal.add(Calendar.DAY_OF_MONTH, (int)iNbDay);
            Date result = cal.getTime();
            return result;

    }

    public Date addWeek(Date dDate, long iNbWeek)
    {

            Calendar cal = Calendar.getInstance();
            cal.setTime(dDate);
            cal.add(Calendar.WEEK_OF_YEAR, (int)iNbWeek);
            Date result = cal.getTime();
            return result;

    }

    public Date addMonth(Date dDate, int iNbMonth)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dDate);
        int month = cal.get(Calendar.MONTH);
        month += iNbMonth;
        int year = month / 12;
        month %= 12;
        cal.set(Calendar.MONTH, month);
        if(year != 0)
        {
            int oldYear = cal.get(Calendar.YEAR);
            cal.set(Calendar.YEAR, year + oldYear);
        }
        return cal.getTime();
    }

     public Date addYear(Date dDate, int iNbYear)
    {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dDate);
            int oldYear = cal.get(1);
            cal.set(1, iNbYear + oldYear);
            return cal.getTime();
    }

    public int getDay(Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

     public String getDayName(Date vDate)
    {
        return formatDate(vDate, "EEEE");
    }

    public String getDayName(int iNDay) throws Exception
    {
       if(iNDay >= 1 && iNDay <= 7)
        {
            Calendar cal = Calendar.getInstance();
            cal.set(7, iNDay);
            return formatDate(cal.getTime(), "EEEE");
        } else
        {
            throw new Exception();
        }
    }

    public int getMonth(Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(2) + 1; //month: from 1 - 12;
    }

     public int getYear(Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(1);
        int era = cal.get(0);
        if(era == 0)
            return -1 * year;
        else
            return year;
    }

    public int getWeek(Date vDate)
    {
            Calendar cal = Calendar.getInstance();
            cal.setTime(vDate);
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            return week;
    }

    public int getWeekDay(Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public int getDayOfYear(Date d)
    {
	Calendar cal = Calendar.getInstance();
	cal.setTime(d);
	return cal.get(Calendar.DAY_OF_YEAR);
    }

    public int getDaysOfMonth(Date d)
    {
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(d);
	int result = 0;
	switch(cal.get(Calendar.MONTH))
	{
	    case Calendar.JANUARY:
		    result = 31;
	    case Calendar.FEBRUARY:
		    if(cal.isLeapYear(getYear(d)))
		    {
			result =  29;
		    }
		    else
		    {
			result =  28;
		    }
		    break;
	    case Calendar.MARCH:
		    result =  31;
		    break;
	    case Calendar.APRIL:
		    result =  30;
		    break;
	    case Calendar.MAY:
		    result =  31;
		    break;
	    case Calendar.JUNE:
		    result =  30;
		    break;
	    case Calendar.JULY:
		    result =  31;
		    break;
	    case Calendar.AUGUST:
		    result =  31;
		    break;
	    case Calendar.SEPTEMBER:
		    result =  30;
		    break;
	    case Calendar.OCTOBER:
		    result =  31;
		    break;
	    case Calendar.NOVEMBER:
		    result =  31;
		    break;
	    case Calendar.DECEMBER:
		    result =  31;
		    break;
	    default:
		    break;
	}
	return result;
    }
}