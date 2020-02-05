package com.bizwink.calendar;

/**
 * Title:        sales
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Michelle
 * @version 1.0
 */

import java.util.Date;
import java.lang.Exception;
import javax.servlet.http.HttpServletResponse;
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

public interface SmartDate
{
	//int formatMonth(String sMonth)  throws Exception;
	Date strToDate(String s);
	Date strToDate(String s, String pattern);
	String formatDate(Date dDate, String sFormat);
	Date addDay(Date dDate, long iNbDay);
	Date addWeek(Date dDate, long iNbWeek);
	Date addMonth(Date dDate, int iNbMonth);
	Date addYear(Date dDate, int iNbYear);
	int getDay(Date d);
	String getDayName(Date vDate);
	String getDayName(int iNDay) throws Exception;
	int getMonth(Date d);
	int getYear(Date d);
	int getWeekDay(Date d);
	int getDayOfYear(Date d);
	int getWeek(Date vDate);
	int getDaysOfMonth(Date d);
}