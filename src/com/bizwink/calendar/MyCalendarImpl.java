package com.bizwink.calendar;

/**
 * Title:        sales
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Michelle Liu
 * @version 1.0
 */
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;

public class MyCalendarImpl implements MyCalendar
{
    private int year = 0;
    private int month = -1;
    private int day = 0;
    private String mode;
    private String formName;
    private String DI;
    public void setYear(int y)
    {
	this.year = y;
	return;
    }

    public void setMonth(int m)
    {
	this.month = m;
	return;
    }

    public void setDay(int d)
    {
	this.day = d;
	return;
    }

    public int getYear()
    {
	return this.year;
    }

    public int getMonth()
    {
	return this.month;
    }

    public int getDay()
    {
	return this.day;
    }

    public void setFormName(String form)
    {
	this.formName = form;
    }

    public String getFormName()
    {
	return this.formName;
    }

    public String getDI()
    {
	return this.DI;
    }

    public void setDI(String di)
    {
	this.DI = di;
    }
    public void setMode(String mode)
    {
	this.mode = mode;
    }

    public String getMode()
    {
	return this.mode;
    }

    public Date getRequestedDate()
    {
	SmartDate smartDate = new SmartDateImpl();
	Date now = new Date();
	Calendar cal = Calendar.getInstance();
	int y = 0;
        int d = 0;
	int m = -1;
	int i = 0; // 1: day of month 2: day of year
	//get requested year:
	if( year == 0 )
	{
	    y = smartDate.getYear(now);
	}
	else
	{
	    y = year;
	}

	//get requested day:
	if(day == 0)
	{
	    if(month == -1)
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
	    d = day;
	    i = 2;
	}
	    //get requested month:
	    if(month == -1)
	    {
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
		m = month;
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
		if(tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH) < d)
		{
		    d = tmpCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		cal.set(Calendar.DAY_OF_MONTH,d);
	        return cal.getTime();
	}
    }

}